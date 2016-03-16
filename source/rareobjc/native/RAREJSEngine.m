//  RAREJSEngine.m
//  RareOSX
//
//  Created by Don DeCoteau on 6/18/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <java/util/Date.h>
#import <IOSDoubleArray.h>
#import <IOSFloatArray.h>
#import <IOSShortArray.h>
#import <IOSLongArray.h>
#import <IOSBooleanArray.h>
#import <javax/script/ScriptException.h>
#import <java/util/HashMap.h>
#import <com/appnativa/rare/util/NSDictionaryMap.h>
#import <com/appnativa/rare/util/NSArrayList.h>
#import "RAREJSEngine.h"
#import "AppleHelper.h"
#import "JSCocoa.h"
#import "java/lang/Boolean.h"
#import "com/appnativa/rare/iPlatformAppContext.h"
#import "com/appnativa/rare/platform/apple/AppContext.h"
#import "com/appnativa/rare/Platform.h"
#import "com/appnativa/rare/ui/Frame.h"
#import "com/appnativa/rare/scripting/ScriptManager.h"
#import "com/appnativa/rare/scripting/aScriptManager.h"
#import "com/appnativa/rare/scripting/iScriptHandler.h"
#import "com/appnativa/rare/spot/Application.h"
#import "com/appnativa/rare/ui/iWindow.h"
#import "com/appnativa/rare/widget/aPlatformWidget.h"
#import "com/appnativa/rare/viewer/FormViewer.h"
#import "RAREJSFunction.h"
#import "com/appnativa/rare/platform/iInterfaceProxy.h"
#import "IOSProtocolClass.h"
#import "RAREJSArray.h"
#import "com/appnativa/rare/ui/canvas/Image.h"
#import "com/appnativa/rare/scripting/JavaScriptEngineFactory.h"

static BOOL jsUnprotectOnMainThreadOnly_ = NO;
static JSContextGroupRef jscMainGroup=NULL;
void classListMethods(Class c, NSString *prefix, NSMutableArray *list, BOOL equals) {
  Class oc = c;
  const char *pre = [prefix UTF8String];
  while (c) {
    unsigned int numMethods = 0;
    Method *methods = class_copyMethodList(c, &numMethods);
    for (int i = 0; i < numMethods; i++) {
      const char *name = sel_getName(method_getName(methods[i]));
      if (strstr(name, pre) == name) {
        if (!equals || strlen(name) == strlen(pre)) {
          [list addObject:[[NSString alloc] initWithUTF8String:name]];
        }
      }
    }
    if (methods) free(methods);
    c = [c superclass];
  }
  c=oc;
  while (c) {
    unsigned int numMethods = 0;
    Method *methods = class_copyMethodList(object_getClass(c), &numMethods);
    for (int i = 0; i < numMethods; i++) {
      const char *name = sel_getName(method_getName(methods[i]));
      if (strstr(name, pre) == name) {
        if (!equals || strlen(name) == strlen(pre)) {
          [list addObject:[[NSString alloc] initWithUTF8String:name]];
        }
      }
    }
    if (methods) free(methods);
    c = [c superclass];
  }
  return;
}

NSString *protocolFirstMethod(Protocol *p) {
  unsigned int numMethods = 0;
  struct objc_method_description *methods = protocol_copyMethodDescriptionList(p, YES, YES, &numMethods);
  NSString *sig = nil;
  if (numMethods > 0) {
    sig = NSStringFromSelector(methods[0].name);
  }
  if (methods) free(methods);
  return sig;
}

NSArray *protocolMethods(Protocol *p) {
  unsigned int numMethods = 0;
  struct objc_method_description *methods = protocol_copyMethodDescriptionList(p, YES, YES, &numMethods);
  NSMutableArray *a = nil;
  if (numMethods > 0) {
    a=[NSMutableArray arrayWithCapacity:numMethods];
    for(int i=0;i<numMethods;i++) {
      [a addObject: NSStringFromSelector(methods[i].name)];
    }
  }
  if (methods) free(methods);
  return a;
}

@implementation RAREJSEngine {
  JSContextGroupRef jscGroup;
}

@synthesize engine = engine_;
- (id)init {
  self = [super init];
  if (self) {
    if(!jscMainGroup) {
      jscGroup=JSContextGroupCreate();
      jscGroup=JSContextGroupRetain(jscGroup);
      jscMainGroup=jscGroup;
      mainEngine_=YES;
    }
    else {
      jscGroup=jscMainGroup;
    }
    NSNumber *value = [NSNumber numberWithBool:YES];
    numberKeys = [NSDictionary dictionaryWithObjectsAndKeys:
        value, @"IntArray", value, @"FloatArray", value, @"DoubleArray", value, @"IntegerArray", value, @"NumberArray",
        value, @"Int", value, @"Float", value, @"Double", value, @"Integer", value, @"Number", value, @"LongInt", value, @"Long",nil];

    engineGlobalKeys = [NSDictionary dictionaryWithObjectsAndKeys:
        value, @"expandJSMacros", value, @"window", value, @"form", value, @"widget",
        value, @"__globalJSFunctionRepository__", value, @"dumpCallStack", value, @"String", value, @"RegExp",
        value, @"Array", value, @"Date", value, @"Number", value, @"Boolean", value, @"Math", nil];
  }
  JSCocoaController* jsc=[[JSCocoaController alloc] initWithGlobalContext:nil useGroup:jscGroup];
  [jsc setUseSplitCall:NO];
  [jsc setUseAutoCall:NO];
  [jsc setDelegate:self];
  jsc.callSelectorsMissingTrailingSemicolon = NO;
  controller_=jsc;
  context_=[jsc ctx];
  return self;
}
 -(void)dealloc {
 }
-(JSCocoaController*) getController
{
  return controller_;
}
-(void) dispose {
  if([JSCocoaController sharedController]!=controller_) {
    controller_=nil;
    jscGroup=nil;
  }
}


+(JSCocoaController*) getControllerForContext:(JSContextRef) ctx{
  id<JavaUtilList> engines=[RAREJavaScriptEngineFactory loadedEngines];
  @synchronized (engines) {
    for (RAREJavaScriptEngine * __strong e in engines) {
      RAREJSEngine* jse=(RAREJSEngine*)e->proxy_;
      if (jse->context_==ctx) {
        return jse->controller_;
      }
    }
  }
  return nil;
}
+(JSCocoaController*) getControllerInstance {
  if([NSOperationQueue currentQueue]==[NSOperationQueue mainQueue]) {
    return [JSCocoaController sharedController];
  }
  NSMutableDictionary* td=[[NSThread currentThread] threadDictionary];
  JSCocoaController* jsc=(JSCocoaController*)[td objectForKey:@"_rare_jsc_"];
  if(!jsc & jsUnprotectOnMainThreadOnly_) {
    NSLog(@"NO JSC FOR THREAD!!!!!!!!!");
  }
  return [JSCocoaController sharedController];
}
+(BOOL) jsUnprotectOnMainThreadOnly {
  return jsUnprotectOnMainThreadOnly_;
}
+(void) setJsUnprotectOnMainThreadOnly: (BOOL) only {
  jsUnprotectOnMainThreadOnly_=only;
}
- (NSObject *)eval:(NSString *)script asName:(NSString *)name {
  JSCocoaController* jsc=[self getController];
  if (!name) {
    return [jsc eval:script];
  }
  return [jsc toObject:[jsc evalJSString:script withScriptPath:name]];

}


- (void)setValue:(id)value withName:(NSString *)name {
    JSCocoaController* jsc=[self getController];
    [jsc setObject:value withName:name];

}
- (void)setConstantValue:(id)value withName:(NSString *)name {
  JSCocoaController* jsc=[self getController];
  [jsc setObject:value withName:name attributes:kJSPropertyAttributeReadOnly | kJSPropertyAttributeDontDelete];
}

- (id)getValue:(NSString *)name {
  JSCocoaController* jsc=[self getController];
  return [jsc objectWithName:name];
}

- (void)deleteValue:(NSString *)name {
  JSCocoaController* jsc=[self getController];
  [jsc removeObjectWithName:name];
}

- (bool)hasProperty:(NSString *)name {
  JSCocoaController* jsc=[self getController];
  JSValueRef value = [jsc evalJSString:name];
  return !JSValueIsUndefined(jsc.ctx, value);
}

- (NSArray *)getPropertyNames {
  JSCocoaController* jsc=[self getController];
  JSContextRef ctx = jsc.ctx;
  JSPropertyNameArrayRef names = JSObjectCopyPropertyNames(ctx, JSContextGetGlobalObject(ctx));
  NSInteger len = JSPropertyNameArrayGetCount(names);
  NSMutableArray *a = [[NSMutableArray alloc] initWithCapacity:len];
  for (NSInteger i = 0; i < len; i++) {
    JSStringRef sref = JSPropertyNameArrayGetNameAtIndex(names, i);
    NSString *s = CFBridgingRelease(JSStringCopyCFString(NULL, sref));
    [a addObject:s];
  }
  JSPropertyNameArrayRelease(names);
  return a;
}

- (void)getParams:(NSString *)sig outArray:(NSMutableArray *)a {
  NSUInteger n = 0;
  NSInteger len = [sig length];
  NSRange searchRange = NSMakeRange(0, len);
  NSRange range = [sig rangeOfString:@":" options:NSLiteralSearch range:searchRange];
  while (range.location != NSNotFound) {
    n = range.location;
    range.length = n - searchRange.location;
    range.location = searchRange.location;
    searchRange.location = n + 1;
    searchRange.length = len - n - 1;
    NSRange r = [sig rangeOfString:@"With" options:NSLiteralSearch | NSBackwardsSearch | NSCaseInsensitiveSearch range:range];
    if (r.location != NSNotFound) {
      n = r.location - range.location + 4;
      range.length -= n;
      range.location += n;
    }
    [a addObject:[[NSMutableString alloc] initWithString:[sig substringWithRange:range]]];
    if (searchRange.length == 0) {
      break;
    }
    range = [sig rangeOfString:@":" options:NSLiteralSearch range:searchRange];
  }
}
-(BOOL) isKindOrNil: (JSCocoaController *)controller type: (NSString*) type value: (JSValueRef) ref inContext:(JSContextRef)ctx {
  if (JSValueIsNull(ctx, ref)) return YES;
  if (JSValueIsUndefined(ctx, ref)) return NO;
  if (!JSValueIsObject(ctx, ref))  return NO;

  IOSClass *cls = [IOSClass classForIosName:type];
  if (!cls) return NO;
  
  NSObject *o = [controller toObject:ref];
  return [cls isInstance:o];
}
- (int)matchSignature:(JSCocoaController *)controller signature:(NSArray *)sig argumentCount:(size_t)argumentCount arguments:(JSValueRef *)arguments inContext:(JSContextRef)ctx {
  JSValueRef ref;
  NSString *type;
  int match = 0;
  int scount=(int)sig.count;
  for (int i = 0; i < argumentCount; i++) {
    ref = arguments[i];
    type = (NSString *) [sig objectAtIndex:i];
    if(i+1==scount && scount<argumentCount) {
      int n=[type indexOfString:@"Array"];
      if(n==type.length-5) {
        type=[type substring:0 endIndex:n];
        for(n=i; n < argumentCount; n++) {
          if(![self isKindOrNil:controller type:type value:arguments[i] inContext:ctx]) return 1;
        }
        return match;
      }
      return 1;
    }
    if ([type isEqual:@"Id"]) {
      continue;
    }
    if (JSValueIsString(ctx, ref)) {
      if (![type isEqual:@"NSString"] && ![type isEqual:@"JavaLangCharSequence"]) {
        match--;
      }
    }
    else if (JSValueIsNumber(ctx, ref)) {
      if (![numberKeys objectForKey:type]) {
        if ([type isEqual:@"Boolean"]) {
          match=-1;
        }
        else {
          match = 1;
          break;
        }
      }
    }
    else if (JSValueIsBoolean(ctx, ref)) {
      if (![type isEqual:@"Boolean"]) {
        match = 1;
        break;
      }
    }
    else if (JSValueIsNull(ctx, ref) || JSValueIsUndefined(ctx, ref)) {
      continue;
    }
    else if (JSValueIsObject(ctx, ref)) {
      IOSClass *cls = [IOSClass classForIosName:type];
      if (cls) {
        if ([cls isKindOfClass:[IOSProtocolClass class]]) {
          JSObjectRef jo = JSValueToObject(ctx, ref, NULL);
          if (jo && JSObjectIsFunction(ctx, jo)) {
            break;
          }
        }
        NSObject *o = [controller toObject:ref];

        if (![cls isInstance:o]) {
          JSObjectRef jo = JSValueToObject(ctx, ref, NULL);
          if (jo && JSObjectIsFunction(ctx, jo)) {
            break;
          }
          if ([type isEqual:@"NSString"] || [type isEqual:@"JavaLangCharSequence"]) {
            match = -20;
          }
          else {
            match = 1;
          }
          break;
        }
      }
      else {
        match=-20;
      }
    }
  }
  return match;
}

- (NSString *)JSCocoa:(JSCocoaController *)controller getMethod:(NSString *)methodName ofObject:(id)object argumentCount:(size_t)argumentCount arguments:(JSValueRef *)arguments inContext:(JSContextRef)ctx {
  NSMutableArray *a = controller->arrayA;
  if (!a) {
    a = [NSMutableArray new];
    controller->arrayA=a;
    controller->arrayB=[NSMutableArray new];
  }
  NSMutableArray *b = controller->arrayB;
  [a removeAllObjects];
  Class cls;
  if ([object isKindOfClass:[IOSClass class]]) {
    cls = ((IOSClass *) object).objcClass;
  }
  else {
    cls = [object class];
  }
  BOOL equals = YES;
  if ([methodName indexOfString:@"drawImage"]!=-1) {
     equals = YES;
  }
  if (argumentCount > 0) {
    equals = NO;
    methodName = [NSString stringWithFormat:@"%@With", methodName];
  }
  classListMethods(cls, methodName, a, equals);
  NSInteger len = [a count];
  int matchType = -9999;
  NSString *match = nil;
  for (int i = 0; i < len; i++) {
    [b removeAllObjects];
    NSString *s = (NSString *) [a objectAtIndex:i];
    [self getParams:s outArray:b];
    if ([b count] != argumentCount) {
      continue;
    }
    int n = [self matchSignature:controller signature:b argumentCount:argumentCount arguments:arguments inContext:ctx];
    if (n == 0) {
      match = s;
      break;
    }
    if (n < 0 && n > matchType) {
      match = s;
      matchType = n;
    }
  }
  controller->lastMethod=match;
  return match;
}

- (NSObject *)getParamForJSFunction:(NSString *)signature paramIndex:(int)index objectValue:(NSObject *)value  controller:(JSCocoaController*) controller{
  NSString *lastMethod = controller->lastMethod;
  if (!lastMethod || ![lastMethod isEqualToString:signature]) {
    return value;
  }
  NSMutableArray *b = controller->arrayB;
  do {
    if (b.count <= index) {
      break;
    }
    NSString *type = [b objectAtIndex:index];
    Protocol *p = NSProtocolFromString(type);
    if (!p) {
      //value=[self callJSFunction:(RAREJSFunction *)value arguments:nil thisObject:nil  controller: controller];
      break;
    }
    RAREAppContext *app = (RAREAppContext *) [RAREPlatform getAppContext];
    NSObject *o = [app getInterfaceProxyObjectWithNSString:type];
    if (o) {
      if ([o conformsToProtocol:@protocol(RAREiInterfaceProxy)]) {
        NSArray *a=protocolMethods(p);
        if (a) {
          NSInteger len=a.count;
          for (NSInteger i = 0; i < len; i++) {
            [((id <RAREiInterfaceProxy>) o) spar_addMethodWithNSString:(NSString*)[a objectAtIndex:i] withId:value];
          }
        }
      }
      value = o;
    }
  } while (false);
  return value;
}

- (NSObject *)getParam:(NSString *)signature paramIndex:(int)index objectValue:(NSObject *)value hadException:(BOOL*) hadException  controller:(JSCocoaController*) controller{
  NSString *lastMethod = controller->lastMethod;
  *hadException=NO;
  if (!value || !lastMethod || ![lastMethod isEqualToString:signature]) {
    return value;
  }

  NSMutableArray *b = controller->arrayB;
  do {
    if (b.count <= index) {
      break;
    }
    NSString *type = [b objectAtIndex:index];
    if ([type indexOfString:@"Array"] == type.length - 5) {
      if(![value isKindOfClass:[IOSArray class]]) {
        if([value isKindOfClass:[RAREJSArray class]]) {
          RAREJSArray* ja=(RAREJSArray*)value;
          value=[ja toArrayWithType:type hadException:hadException];
          [ja dispose];
        }
        else if([value isKindOfClass:[NSString class]]) {
          IOSObjectArray *o = [IOSObjectArray arrayWithLength:1 type:[IOSClass classWithClass:[NSString class]]];
          [o replaceObjectAtIndex:0 withObject:value];
          value = o;
        }
        else if([value isKindOfClass:[NSNumber class]]) {
          value= [RAREJSArray toIOSArrayFromNSNumber:(NSNumber*)value type:type];
        }
        else if([value isKindOfClass:[NSArray class]]) {
          value=[RAREJSArray toIOSArrayFromNSArray:(NSArray*)value type:type];
        }
        else {
          if(![type isEqualToString:@"ObjectArray"] && ![type isEqualToString:@"NSObjectArray"]) {
            [NSObject throwClassCastException];
          }
          IOSObjectArray *o = [IOSObjectArray arrayWithLength:1 type:[IOSClass classWithClass:[NSObject class]]];
          [o replaceObjectAtIndex:0 withObject:value];
          value = o;
        }
      }
    }
    else if ([type indexOfString:@"List"] == type.length - 4) {
      if(![value conformsToProtocol:@protocol(JavaUtilList)]) {
        if([value isKindOfClass:[RAREJSArray class]]) {
          RAREJSArray* ja=(RAREJSArray*)value;
          NSMutableArray* a=[ja toNSArrayHadException:hadException];
          value=[RARENSArrayList listWithBackingArrayWithId:a withBoolean:YES];
          [ja dispose];
        }
        else if([value isKindOfClass:[NSArray class]]) {
          NSArray* a=(NSArray*)value;
          value=[RARENSArrayList listWithBackingArrayWithId:a withBoolean:[a isKindOfClass:[NSMutableArray class]]];
        }
//        else {
//          value=[RARENSArrayList listWithBackingArrayWithId:[NSArray arrayWithObject:value] withBoolean:NO];
//        }
      }
    }
    else if ([value isKindOfClass:[NSDictionary class]]) {
      if (![type isEqualToString:@"NSDictionary"] == type.length - 5) {
        if(![value conformsToProtocol:@protocol(JavaUtilMap)]) {
          NSDictionary* d=(NSDictionary*)value;
          value=[RARENSDictionaryMap mapUsingBackingDictionaryWithId:d];
        }
      }
    }
  } while (false);
  return value;
}

- (id)callJSFunction:(RAREJSFunction *)function arguments:(NSArray *)args thisObject:(NSObject *)thisObject  controller:(JSCocoaController*) jsc{
  if (!args) {
    args = [NSArray array];
  }
  if(!jsc) {
    jsc = [self getController];
  }
  JSValueRef	exception = NULL;
  JSObjectRef jsFunction = JSValueToObject(jsc.ctx, function->function, &exception);
  
  JSValueRef ret = [jsc callJSFunction:jsFunction withArguments:args];
  if (JSValueIsNull(jsc.ctx, ret) || JSValueIsUndefined(jsc.ctx, ret)) {
    return nil;
  }
  return [jsc toObject:ret];
}

- (JSValueRef)callJSFunctionEx:(RAREJSFunction *)function arguments:(NSArray *)args thisObject:(NSObject *)thisObject  controller:(JSCocoaController*) jsc{
  if (!args) {
    args = [NSArray array];
  }
  if(!jsc) {
    jsc = [self getController];
  }
  JSValueRef	exception = NULL;
  JSObjectRef jsFunction = JSValueToObject(jsc.ctx, function->function, &exception);

  return [jsc callJSFunction:jsFunction withArguments:args];
}
- (id)callJSFunctionNamed:(NSString *)functionName arguments:(NSArray *)args thisObject:(NSObject *)thisObject  controller:(JSCocoaController*) jsc{
  if (!args) {
    args = [NSArray array];
  }
  if(!jsc) {
    jsc = [self getController];
  }
  JSValueRef ret = [jsc callJSFunctionNamed:functionName withArgumentsArray:args];
  if (JSValueIsNull(jsc.ctx, ret) || JSValueIsUndefined(jsc.ctx, ret)) {
    return nil;
  }
  return [jsc toObject:ret];
}

- (void)disposeJSObjectForScriptingContext:(id)sc {
  if (sc) {
    [[self getController] unprotectJSObjectForScriptingContext:sc];
  }
}

- (JSValueRef)JSCocoa:(JSCocoaController *)controller getGlobalProperty:(NSString *)propertyName inContext:(JSContextRef)ctx exception:(JSValueRef *)exception {
  if ([engineGlobalKeys valueForKey:propertyName]) {
    return NULL;
  }
  NSObject *o = [engine_ getGlobalJSValueWithNSString:propertyName];
  if (!o) {
    return JSValueMakeNull(ctx);
  }
  if (o == [RAREJavaScriptEngine Undefined]) {
    return NULL;
  }
  if ([o isKindOfClass:[JavaLangBoolean class]]) {
    return JSValueMakeBoolean(ctx, ((JavaLangBoolean *) o).booleanValue);
  }
  if ([o isKindOfClass:[RAREaPlatformWidget class]]) {
    return [controller boxObject:o];
  }
  return [controller toJS:o].value;
}

- (JSValueRef)JSCocoa:(JSCocoaController *)controller getProperty:(NSString *)propertyName ofObject:(id)object inContext:(JSContextRef)ctx exception:(JSValueRef *)exception {

  id o = [engine_ getObjectPropertyWithId:object withNSString:propertyName];
  if (!o) {
    return JSValueMakeNull(ctx);
  }
  if (o == [RAREJavaScriptEngine Undefined]) {
    unichar c = [propertyName characterAtIndex:0];
    if (![[NSCharacterSet lowercaseLetterCharacterSet] characterIsMember:c]) {
      SEL sel=NSSelectorFromString(propertyName);
      Class cls=[(NSObject*)object class];
      if ([cls respondsToSelector:sel]) {
        NSMethodSignature *sig = [cls methodSignatureForSelector:sel];
        NSInvocation *inv = [NSInvocation invocationWithMethodSignature:sig];
        [inv setTarget:cls];
        [inv setSelector:sel];
        [inv invoke];
        o = [AppleHelper getInvocationReturnValue:inv methodSignature:sig];
      }
      if(o) {
        return [controller RAREtoJS:o];
      }
      return JSValueMakeNull(ctx);
    }
    o = [self getProperty:propertyName ofObject:object prefix:@"get"];
    if (o == [RAREJavaScriptEngine Undefined]) {
      o = [self getProperty:propertyName ofObject:object prefix:@"is"];
    }
    if (o == [RAREJavaScriptEngine Undefined]) {
      return NULL;
    }
    if (!o) {
      return JSValueMakeNull(ctx);
    }
  }
  if ([o isKindOfClass:[RAREJSFunction class]]) {
    return ((RAREJSFunction *) o)->function;
  }
  return [controller RAREtoJS:o];
}

- (id)getProperty:(NSString *)propertyName ofObject:(id)object prefix:(NSString *)prefix {
  id o = [RAREJavaScriptEngine Undefined];
  NSMutableString *str = [NSMutableString stringWithFormat:@"%@%@", prefix, propertyName];
  unichar ch = [str characterAtIndex:[prefix length]];
  [str replaceCharactersInRange:NSMakeRange([prefix length], 1) withString:[[NSString stringWithCharacters:&ch length:1] uppercaseString]];
  SEL sel = NSSelectorFromString(str);
  if ([object isKindOfClass:[IOSClass class]]) {
    Class cls = ((IOSClass *) object).objcClass;
    if ([cls respondsToSelector:sel]) {
      NSMethodSignature *sig = [cls methodSignatureForSelector:sel];
      NSInvocation *inv = [NSInvocation invocationWithMethodSignature:sig];
      [inv setTarget:cls];
      [inv setSelector:sel];
      [inv invoke];
      o = [AppleHelper getInvocationReturnValue:inv methodSignature:sig];
    }
  }
  else {
    if ([object respondsToSelector:sel]) {
//        NSMethodSignature * sig = [[((NSObject*)object) class ] instanceMethodSignatureForSelector:sel];
//        NSInvocation * inv = [NSInvocation invocationWithMethodSignature:sig];
//        [inv setTarget:object];
//        [inv setSelector:sel];
//        [inv invoke];
//        o=[AppleHelper getInvocationReturnValue:inv methodSignature:sig];
      o = [AppleHelper invokeSelector:sel onTarget:object];
    }
  }
  return o;
}


- (BOOL)JSCocoa:(JSCocoaController *)controller setProperty:(NSString *)propertyName ofObject:(id)object toValue:(JSValueRef)value inContext:(JSContextRef)ctx exception:(JSValueRef *)exception {
  id o;
  if (JSValueIsBoolean(ctx, value)) {
    o = [controller toBool:value] ? [JavaLangBoolean getTRUE] : nil;
  }
  else {
    o = [controller toObject:value];
  }
  if ([engine_ setObjectPropertyWithId:object withNSString:propertyName withId:o]) {
    return YES;
  }
  unichar c = [propertyName characterAtIndex:0];
  if (![[NSCharacterSet lowercaseLetterCharacterSet] characterIsMember:c]) {
    if ([object isKindOfClass:[RAREaWidget class]]) {
      RAREaWidget *w = (RAREaWidget *) object;
      [w setCustomPropertyWithId:propertyName withId:o];
      return YES;
    }
    return NO;
  }
  NSMutableString *str = [NSMutableString stringWithFormat:@"set%@With", propertyName];
  unichar ch = [str characterAtIndex:3];
  [str replaceCharactersInRange:NSMakeRange(3, 1) withString:[[NSString stringWithCharacters:&ch length:1] uppercaseString]];
  controller->lastMethod=nil;
  NSMutableArray *a = controller->arrayA;
  if (!a) {
    a = [NSMutableArray new];
    controller->arrayA=a;
    controller->arrayB=[NSMutableArray new];
  }
  NSMutableArray *b = controller->arrayB;
  [a removeAllObjects];
  classListMethods([object class], str, a, NO);
  NSInteger len = [a count];
  int matchType = -9999;
  NSString *match = nil;
  for (int i = 0; i < len; i++) {
    [b removeAllObjects];
    NSString *s = (NSString *) [a objectAtIndex:i];
    [self getParams:s outArray:b];
    if ([b count] != 1) {
      continue;
    }
    int n = [self matchSignature:controller signature:b argumentCount:1 arguments:&value inContext:ctx];
    if (n == 0) {
      match = s;
      break;
    }
    if (n < 0 && n > matchType) {
      match = s;
      matchType = n;
    }
  }
  if (match) {
    BOOL hadException=NO;
    controller->lastMethod=match;
    o=[self getParam:match paramIndex:0 objectValue:o hadException: &hadException controller: controller];
    if(hadException) return NO;
    if([o isKindOfClass:[RAREJSFunction class]]) {
      o=[self getParamForJSFunction:controller->lastMethod paramIndex:0 objectValue:o controller:controller];
    }
    controller->lastMethod=nil;
    [AppleHelper invokeSelector:NSSelectorFromString(match) onTarget:object withArg:o];
//    [object performSelector:NSSelectorFromString(match) withObject:o];
    return YES;
  }
  else {
    if ([object isKindOfClass:[RARERenderableDataItem class]]) {
      NSString* errorMessage=[engine_ setCustomPropertyWithRARERenderableDataItem:(RARERenderableDataItem *)object withNSString:propertyName withId:o];
      if(!errorMessage) {
        return YES;
      }
      throwException(ctx,exception, errorMessage);
      
    }
    return NO;
  }
}

- (void)JSCocoa:(JSCocoaController *)controller hadError:(NSString *)error onLineNumber:(NSInteger)lineNumber atSourceURL:(id)url {
  NSString *filename = url ? ((NSObject *) url).description : @"";
  JavaxScriptScriptException *e = [[JavaxScriptScriptException alloc] initWithNSString:error withNSString:filename withInt:(int) lineNumber withInt:0];
  @throw e;
}

@end
@implementation RAREWidgetContext (ObjectiveC)
-(JSObjectRef) getJSObject {
  if(languageObject_) {
    return (__bridge JSObjectRef)(languageObject_);
  }
  return NULL;
}
-(void) setJSObject: (JSObjectRef) value {
  NSObject *o=value ? (__bridge NSObject *)(value) : nil;
  languageObject_=o;
}

@end
