//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/JSScriptContext.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/scripting/JSScriptContext.h"
#include "javax/script/Bindings.h"
#include "javax/script/ScriptContext.h"

@implementation RAREJSScriptContext

- (id)init {
  return [super init];
}

- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)engineScope {
  return [super initWithJavaxScriptBindings:engineScope];
}

- (void)setAttributeWithNSString:(NSString *)name
                          withId:(id)value
                         withInt:(int)scope {
  if ([((NSString *) nil_chk(name)) indexOf:'.'] != -1) {
    scope = JavaxScriptScriptContext_GLOBAL_SCOPE;
  }
  [super setAttributeWithNSString:name withId:value withInt:scope];
}

- (id)getAttributeWithNSString:(NSString *)name
                       withInt:(int)scope {
  if ([((NSString *) nil_chk(name)) indexOf:'.'] != -1) {
    scope = JavaxScriptScriptContext_GLOBAL_SCOPE;
  }
  return [super getAttributeWithNSString:name withInt:scope];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getAttributeWithNSString:withInt:", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREJSScriptContext = { "JSScriptContext", "com.appnativa.rare.scripting", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREJSScriptContext;
}

@end
