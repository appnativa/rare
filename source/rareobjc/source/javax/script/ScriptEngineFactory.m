//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/ScriptEngineFactory.java
//
//  Created by decoteaud on 6/13/14.
//

#include "IOSObjectArray.h"
#include "java/util/List.h"
#include "javax/script/ScriptEngine.h"
#include "javax/script/ScriptEngineFactory.h"


@interface JavaxScriptScriptEngineFactory : NSObject
@end

@implementation JavaxScriptScriptEngineFactory

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getScriptEngine", NULL, "LJavaxScriptScriptEngine", 0x401, NULL },
    { "getEngineName", NULL, "LNSString", 0x401, NULL },
    { "getEngineVersion", NULL, "LNSString", 0x401, NULL },
    { "getLanguageName", NULL, "LNSString", 0x401, NULL },
    { "getLanguageVersion", NULL, "LNSString", 0x401, NULL },
    { "getExtensions", NULL, "LJavaUtilList", 0x401, NULL },
    { "getMimeTypes", NULL, "LJavaUtilList", 0x401, NULL },
    { "getNames", NULL, "LJavaUtilList", 0x401, NULL },
    { "getParameterWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getMethodCallSyntaxWithNSString:withNSString:withNSStringArray:", NULL, "LNSString", 0x401, NULL },
    { "getOutputStatementWithNSString:", NULL, "LNSString", 0x401, NULL },
    { "getProgramWithNSStringArray:", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _JavaxScriptScriptEngineFactory = { "ScriptEngineFactory", "javax.script", NULL, 0x201, 12, methods, 0, NULL, 0, NULL};
  return &_JavaxScriptScriptEngineFactory;
}

@end
