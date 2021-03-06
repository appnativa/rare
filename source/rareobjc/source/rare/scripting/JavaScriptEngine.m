//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/JavaScriptEngine.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/scripting/JSCocoaBindings.h"
#include "com/appnativa/rare/scripting/JavaPackage.h"
#include "com/appnativa/rare/scripting/JavaScriptEngine.h"
#include "com/appnativa/rare/scripting/JavaScriptEngineFactory.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/viewer/aContainer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/Helper.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/Streams.h"
#include "java/io/IOException.h"
#include "java/io/Reader.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/util/List.h"
#include "java/util/Map.h"
#include "javax/script/Bindings.h"
#include "javax/script/ScriptContext.h"
#include "javax/script/ScriptEngine.h"
#include "javax/script/ScriptEngineFactory.h"
#include "javax/script/ScriptException.h"
#include "javax/script/SimpleScriptContext.h"
#import "RAREJSEngine.h"
#import "AppleHelper.h"

@implementation RAREJavaScriptEngine

static id RAREJavaScriptEngine_Undefined_;

+ (id)Undefined {
  return RAREJavaScriptEngine_Undefined_;
}

- (id)initWithRAREJavaScriptEngineFactory:(RAREJavaScriptEngineFactory *)factory {
  if (self = [super init]) {
    packages_ = [[NSObject alloc] init];
    self->proxy_ = [RAREJavaScriptEngine createProxyWithRAREJavaScriptEngine:self];
    self->factory_ = factory;
    self->context_ = [[JavaxScriptSimpleScriptContext alloc] init];
    executionContext_ = context_;
    [context_ setBindingsWithJavaxScriptBindings:[self createBindings] withInt:JavaxScriptScriptContext_ENGINE_SCOPE];
    customPropertyPrefix_ = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getCustomPropertyPrefix];
  }
  return self;
}

- (id)InvokeNativeScriptFunctionObjectWithId:(id)sparJSFunction
                           withNSObjectArray:(IOSObjectArray *)args {
  executionContext_=context_;
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  NSArray* a=nil;
  if(args) {
    a=[AppleHelper toNSArray: args];
  }
  return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: a thisObject: nil controller: nil];
}

- (id)InvokeNativeScriptFunctionObjectWithNSString:(NSString *)functionName
                                 withNSObjectArray:(IOSObjectArray *)args {
  executionContext_=context_;
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  NSArray* a=nil;
  if(args) {
    a=[AppleHelper toNSArray: args];
  }
  return [e callJSFunctionNamed:functionName arguments: a thisObject: nil controller: nil];
}

- (id)InvokeNativeScriptFunctionObjectWithId:(id)sparJSFunction
                 withJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                                      withId:(id)scriptObject {
  if(!context) {
    context=context_;
  }
  executionContext_=context;
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: nil thisObject: scriptObject controller: nil];
}

- (id)InvokeNativeScriptFunctionObjectExWithId:(id)sparJSFunction
                                        withId:(id)nsArray {
  executionContext_=context_;
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  NSArray* a=(NSArray*)nsArray;
  return [e callJSFunction:(RAREJSFunction *) sparJSFunction arguments: a thisObject: nil controller: nil];
}

- (id<JavaxScriptBindings>)createBindings {
  return [[RAREJSCocoaBindings alloc] initWithId:proxy_];
}

- (void)disposeJSObjectForScriptingContextWithRAREWidgetContext:(RAREWidgetContext *)wc {
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  [e disposeJSObjectForScriptingContext: wc];
}

- (void)dispose {
  if (proxy_ != nil) {
    @try {
      [self disposeEx];
    }
    @catch (JavaLangException *e) {
      [RAREPlatform ignoreExceptionWithJavaLangThrowable:e];
    }
    [((RAREJavaScriptEngineFactory *) nil_chk(factory_)) disposingWithJavaxScriptScriptEngine:self];
    factory_ = nil;
    proxy_ = nil;
    executionContext_ = nil;
    tmpContext_ = nil;
    context_ = nil;
  }
}

- (id)evalWithJavaIoReader:(JavaIoReader *)reader {
  @try {
    return [self evalWithNSString:[RAREUTStreams readerToStringWithJavaIoReader:reader]];
  }
  @catch (JavaIoIOException *e) {
    @throw [[JavaxScriptScriptException alloc] initWithJavaLangException:e];
  }
}

- (id)evalWithNSString:(NSString *)script {
  return [self evalWithNSString:script withJavaxScriptScriptContext:context_];
}

- (id)evalWithJavaIoReader:(JavaIoReader *)reader
   withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  @try {
    return [self evalWithNSString:[RAREUTStreams readerToStringWithJavaIoReader:reader] withJavaxScriptBindings:bindings];
  }
  @catch (JavaIoIOException *e) {
    @throw [[JavaxScriptScriptException alloc] initWithJavaLangException:e];
  }
}

- (id)evalWithJavaIoReader:(JavaIoReader *)reader
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context {
  @try {
    return [self evalWithNSString:[RAREUTStreams readerToStringWithJavaIoReader:reader] withJavaxScriptScriptContext:context];
  }
  @catch (JavaIoIOException *e) {
    @throw [[JavaxScriptScriptException alloc] initWithJavaLangException:e];
  }
}

- (id)evalWithNSString:(NSString *)script
withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  if (tmpContext_ == nil) {
    tmpContext_ = [[JavaxScriptSimpleScriptContext alloc] init];
    [tmpContext_ setBindingsWithJavaxScriptBindings:[((id<JavaxScriptScriptContext>) nil_chk([self getContext])) getBindingsWithInt:JavaxScriptScriptContext_GLOBAL_SCOPE] withInt:JavaxScriptScriptContext_GLOBAL_SCOPE];
  }
  [((id<JavaxScriptScriptContext>) nil_chk(tmpContext_)) setBindingsWithJavaxScriptBindings:bindings withInt:JavaxScriptScriptContext_ENGINE_SCOPE];
  @try {
    return [self evalWithNSString:script withJavaxScriptScriptContext:tmpContext_];
  }
  @finally {
    [self setContextWithJavaxScriptScriptContext:context_];
  }
}

- (id)evalWithNSString:(NSString *)script
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context {
  if (context == nil) {
    context = [self getContext];
  }
  @try {
    return [self evalExWithNSString:script withJavaxScriptScriptContext:context withNSString:(NSString *) check_class_cast([((id<JavaxScriptScriptContext>) nil_chk(context)) getAttributeWithNSString:[JavaxScriptScriptEngine FILENAME] withInt:JavaxScriptScriptContext_GLOBAL_SCOPE], [NSString class])];
  }
  @catch (JavaLangException *e) {
    @throw [[JavaxScriptScriptException alloc] initWithJavaLangException:e];
  }
}

- (void)putWithNSString:(NSString *)key
                 withId:(id)value {
  (void) [((id<JavaxScriptBindings>) nil_chk([self getBindingsWithInt:JavaxScriptScriptContext_ENGINE_SCOPE])) putWithId:key withId:value];
}

- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                                   withInt:(int)scope {
  [((id<JavaxScriptScriptContext>) nil_chk(context_)) setBindingsWithJavaxScriptBindings:bindings withInt:scope];
}

- (void)setConstantValueWithNSString:(NSString *)name
                              withId:(id)value {
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  [e setConstantValue: value withName: name];
}

- (void)setContextWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context {
  self->context_ = context;
  executionContext_ = context;
}

- (BOOL)setObjectPropertyWithId:(id)o
                   withNSString:(NSString *)property
                         withId:(id)value {
  if ([RAREUTSNumber isNumericWithNSString:property withInt:0]) {
    RAREUTSNumber *num = [[RAREUTSNumber alloc] initWithNSString:property];
    if (([num decimalPlaces] > 0) || [num isNegative]) {
      return NO;
    }
    return [self setNumericPropertyWithId:property withInt:[num intValue] withId:value];
  }
  if ([o conformsToProtocol: @protocol(JavaUtilMap)]) {
    id<JavaUtilMap> m = (id<JavaUtilMap>) check_protocol_cast(o, @protocol(JavaUtilMap));
    if (![((id<JavaUtilMap>) nil_chk(m)) containsKeyWithId:property]) {
      return NO;
    }
    (void) [m putWithId:property withId:value];
    return YES;
  }
  if ([o isKindOfClass:[RAREaViewer class]]) {
    id ow = [((RAREaViewer *) check_class_cast(o, [RAREaViewer class])) getNamedItemWithNSString:property];
    if ([ow isKindOfClass:[RARERenderableDataItem class]]) {
      [((RARERenderableDataItem *) check_class_cast(ow, [RARERenderableDataItem class])) setValueWithId:value];
      return YES;
    }
  }
  if ([o isKindOfClass:[RAREaWidget class]]) {
    RAREaWidget *w = (RAREaWidget *) check_class_cast(o, [RAREaWidget class]);
    if ([((NSString *) nil_chk(property)) hasPrefix:@"on"]) {
      [((RAREaWidget *) nil_chk(w)) setEventHandlerWithNSString:property withId:value withBoolean:NO];
      return YES;
    }
    if ([property isEqual:@"value"]) {
      [((RAREaWidget *) nil_chk(w)) setValueWithId:value];
      return YES;
    }
  }
  return NO;
}

- (void)setPopulatingConstantsWithBoolean:(BOOL)populatingConstants {
  self->populatingConstants_ = populatingConstants;
}

- (id)getWithNSString:(NSString *)key {
  return [((id<JavaxScriptScriptContext>) nil_chk(executionContext_)) getAttributeWithNSString:key];
}

- (id<JavaxScriptBindings>)getBindingsWithInt:(int)scope {
  return [((id<JavaxScriptScriptContext>) nil_chk(context_)) getBindingsWithInt:scope];
}

- (id<JavaxScriptScriptContext>)getContext {
  return context_;
}

- (id<JavaxScriptScriptEngineFactory>)getFactory {
  return factory_;
}

- (id)getGlobalJSValueWithNSString:(NSString *)name {
  if (populatingConstants_) {
    return RAREJavaScriptEngine_Undefined_;
  }
  id o = [((id<JavaxScriptScriptContext>) nil_chk(executionContext_)) getAttributeWithNSString:name withInt:JavaxScriptScriptContext_GLOBAL_SCOPE];
  if (o == nil) {
    o = [RAREJavaPackage getPackageWithNSString:name];
  }
  if ((o == nil) && [((NSString *) nil_chk(name)) isEqual:@"Packages"]) {
    o = packages_;
  }
  if ((o == nil) && ![((id<JavaxScriptBindings>) nil_chk([executionContext_ getBindingsWithInt:JavaxScriptScriptContext_GLOBAL_SCOPE])) containsKeyWithId:name]) {
    return RAREJavaScriptEngine_Undefined_;
  }
  return o;
}

- (id)getObjectPropertyWithId:(id)o
                 withNSString:(NSString *)property {
  if (o == packages_) {
    return [RAREJavaPackage importPackageWithNSString:property];
  }
  if ([o isKindOfClass:[RAREJavaPackage class]]) {
    return [((RAREJavaPackage *) check_class_cast(o, [RAREJavaPackage class])) getPropertyWithNSString:property];
  }
  if ([o isKindOfClass:[SPOTSequence class]]) {
    o = [((SPOTSequence *) check_class_cast(o, [SPOTSequence class])) spot_elementForWithNSString:property];
    return (o == nil) ? RAREJavaScriptEngine_Undefined_ : o;
  }
  if ([RAREUTSNumber isNumericWithNSString:property withInt:0]) {
    RAREUTSNumber *num = [[RAREUTSNumber alloc] initWithNSString:property];
    if (([num decimalPlaces] > 0) || [num isNegative]) {
      return nil;
    }
    return [self getNumericPropertyWithId:o withInt:[num intValue]];
  }
  if ([o conformsToProtocol: @protocol(JavaUtilMap)]) {
    id<JavaUtilMap> m = (id<JavaUtilMap>) check_protocol_cast(o, @protocol(JavaUtilMap));
    return [((id<JavaUtilMap>) nil_chk(m)) getWithId:property];
  }
  id value = nil;
  if (([o conformsToProtocol: @protocol(JavaUtilList)]) && [@"length" isEqual:property]) {
    return [JavaLangInteger valueOfWithInt:[((id<JavaUtilList>) check_protocol_cast(o, @protocol(JavaUtilList))) size]];
  }
  if ([o isKindOfClass:[RAREaContainer class]]) {
    value = [((RAREaContainer *) check_class_cast(o, [RAREaContainer class])) getWidgetWithNSString:property];
    if (value != nil) {
      return value;
    }
  }
  if ([o isKindOfClass:[RAREaWidget class]]) {
    RAREaWidget *w = (RAREaWidget *) check_class_cast(o, [RAREaWidget class]);
    value = [((RAREaWidget *) nil_chk(w)) getEventHandlerWithNSString:property];
    if (value == nil) {
      value = [w getAttributeWithNSString:property];
    }
    if (value == nil) {
      id<JavaUtilMap> map = [w getCustomProperties];
      if ((map != nil) && [map containsKeyWithId:property]) {
        return nil;
      }
    }
    if (value != nil) {
      return value;
    }
  }
  return RAREJavaScriptEngine_Undefined_;
}

- (NSString *)setCustomPropertyWithRARERenderableDataItem:(RARERenderableDataItem *)item
                                             withNSString:(NSString *)property
                                                   withId:(id)value {
  if ((customPropertyPrefix_ != nil) && ![((NSString *) nil_chk(property)) hasPrefix:customPropertyPrefix_]) {
    NSString *s = [RAREPlatform getResourceAsStringWithNSString:@"Rare.runtime.text.customPropertyRestriction"];
    return [RAREUTHelper expandStringWithNSString:s withNSStringArray:[IOSObjectArray arrayWithObjects:(id[]){ property, customPropertyPrefix_ } count:2 type:[IOSClass classWithClass:[NSString class]]]];
  }
  if ([item isKindOfClass:[RAREaWidget class]]) {
    [((RAREaWidget *) check_class_cast(item, [RAREaWidget class])) setAttributeWithNSString:property withId:value];
  }
  else {
    (void) [((RARERenderableDataItem *) nil_chk(item)) setCustomPropertyWithId:property withId:value];
  }
  return nil;
}

+ (id)createProxyWithRAREJavaScriptEngine:(RAREJavaScriptEngine *)e {
  RAREJSEngine* se=[RAREJSEngine new];
  se.engine=e;
  return se;
}

- (id)evalExWithNSString:(NSString *)script
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
            withNSString:(NSString *)filename {
  if(!context) {
    context=context_;
  }
  executionContext_=context;
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  return [e eval: script asName: filename];
}

- (void)disposeEx {
  RAREJSEngine* e=(RAREJSEngine*)proxy_;
  return [e dispose];
}

- (BOOL)setNumericPropertyWithId:(id)o
                         withInt:(int)n
                          withId:(id)value {
  if ([o conformsToProtocol: @protocol(JavaUtilList)]) {
    id<JavaUtilList> l = (id<JavaUtilList>) check_protocol_cast(o, @protocol(JavaUtilList));
    if ([((id<JavaUtilList>) nil_chk(l)) size] > n) {
      (void) [l setWithInt:n withId:value];
    }
    return YES;
  }
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:o]) {
    IOSObjectArray *a = (IOSObjectArray *) check_class_cast(o, [IOSObjectArray class]);
    if ((int) [((IOSObjectArray *) nil_chk(a)) count] > n) {
      (void) IOSObjectArray_Set(a, n, value);
    }
    return YES;
  }
  return NO;
}

- (id)getNumericPropertyWithId:(id)o
                       withInt:(int)n {
  if ([o conformsToProtocol: @protocol(JavaUtilList)]) {
    id<JavaUtilList> l = (id<JavaUtilList>) check_protocol_cast(o, @protocol(JavaUtilList));
    return ([((id<JavaUtilList>) nil_chk(l)) size] > n) ? [l getWithInt:n] : nil;
  }
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:o]) {
    IOSObjectArray *a = (IOSObjectArray *) check_class_cast(o, [IOSObjectArray class]);
    return ((int) [((IOSObjectArray *) nil_chk(a)) count] > n) ? IOSObjectArray_Get(a, n) : nil;
  }
  if ([[IOSObjectArray iosClassWithType:[IOSClass classWithClass:[NSObject class]]] isInstance:o]) {
    IOSObjectArray *a = (IOSObjectArray *) check_class_cast(o, [IOSObjectArray class]);
    return ((int) [((IOSObjectArray *) nil_chk(a)) count] > n) ? IOSObjectArray_Get(a, n) : nil;
  }
  return nil;
}

+ (void)initialize {
  if (self == [RAREJavaScriptEngine class]) {
    RAREJavaScriptEngine_Undefined_ = [[NSObject alloc] init];
  }
}

- (void)copyAllFieldsTo:(RAREJavaScriptEngine *)other {
  [super copyAllFieldsTo:other];
  other->context_ = context_;
  other->customPropertyPrefix_ = customPropertyPrefix_;
  other->executionContext_ = executionContext_;
  other->factory_ = factory_;
  other->packages_ = packages_;
  other->populatingConstants_ = populatingConstants_;
  other->proxy_ = proxy_;
  other->tmpContext_ = tmpContext_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "InvokeNativeScriptFunctionObjectWithId:withNSObjectArray:", NULL, "LNSObject", 0x181, NULL },
    { "InvokeNativeScriptFunctionObjectWithNSString:withNSObjectArray:", NULL, "LNSObject", 0x181, NULL },
    { "InvokeNativeScriptFunctionObjectWithId:withJavaxScriptScriptEngine:withJavaxScriptScriptContext:withId:", NULL, "LNSObject", 0x101, NULL },
    { "InvokeNativeScriptFunctionObjectExWithId:withId:", NULL, "LNSObject", 0x101, NULL },
    { "createBindings", NULL, "LJavaxScriptBindings", 0x1, NULL },
    { "disposeJSObjectForScriptingContextWithRAREWidgetContext:", NULL, "V", 0x101, NULL },
    { "evalWithJavaIoReader:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithNSString:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithJavaIoReader:withJavaxScriptBindings:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithJavaIoReader:withJavaxScriptScriptContext:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithNSString:withJavaxScriptBindings:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithNSString:withJavaxScriptScriptContext:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "setConstantValueWithNSString:withId:", NULL, "V", 0x101, NULL },
    { "setObjectPropertyWithId:withNSString:withId:", NULL, "Z", 0x1, NULL },
    { "getWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getBindingsWithInt:", NULL, "LJavaxScriptBindings", 0x1, NULL },
    { "getContext", NULL, "LJavaxScriptScriptContext", 0x1, NULL },
    { "getFactory", NULL, "LJavaxScriptScriptEngineFactory", 0x1, NULL },
    { "getGlobalJSValueWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getObjectPropertyWithId:withNSString:", NULL, "LNSObject", 0x1, NULL },
    { "setCustomPropertyWithRARERenderableDataItem:withNSString:withId:", NULL, "LNSString", 0x4, NULL },
    { "createProxyWithRAREJavaScriptEngine:", NULL, "LNSObject", 0x10a, NULL },
    { "evalExWithNSString:withJavaxScriptScriptContext:withNSString:", NULL, "LNSObject", 0x102, "JavaLangException" },
    { "disposeEx", NULL, "V", 0x102, NULL },
    { "setNumericPropertyWithId:withInt:withId:", NULL, "Z", 0x2, NULL },
    { "getNumericPropertyWithId:withInt:", NULL, "LNSObject", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "Undefined_", NULL, 0x19, "LNSObject" },
    { "packages_", NULL, 0x0, "LNSObject" },
    { "context_", NULL, 0x0, "LJavaxScriptScriptContext" },
    { "executionContext_", NULL, 0x0, "LJavaxScriptScriptContext" },
    { "factory_", NULL, 0x0, "LRAREJavaScriptEngineFactory" },
    { "proxy_", NULL, 0x0, "LNSObject" },
    { "tmpContext_", NULL, 0x0, "LJavaxScriptScriptContext" },
  };
  static J2ObjcClassInfo _RAREJavaScriptEngine = { "JavaScriptEngine", "com.appnativa.rare.scripting", NULL, 0x1, 26, methods, 7, fields, 0, NULL};
  return &_RAREJavaScriptEngine;
}

@end
