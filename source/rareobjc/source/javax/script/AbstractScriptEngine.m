//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/AbstractScriptEngine.java
//
//  Created by decoteaud on 6/13/14.
//

#include "IOSClass.h"
#include "java/io/Reader.h"
#include "java/io/Writer.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/NullPointerException.h"
#include "javax/script/AbstractScriptEngine.h"
#include "javax/script/Bindings.h"
#include "javax/script/ScriptContext.h"
#include "javax/script/ScriptEngine.h"
#include "javax/script/ScriptEngineFactory.h"
#include "javax/script/ScriptException.h"
#include "javax/script/SimpleScriptContext.h"

@implementation JavaxScriptAbstractScriptEngine

- (id)initJavaxScriptAbstractScriptEngine {
  if (self = [super init]) {
    self->context_ = [[JavaxScriptSimpleScriptContext alloc] init];
  }
  return self;
}

- (id)init {
  return [self initJavaxScriptAbstractScriptEngine];
}

- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  if (self = [self initJavaxScriptAbstractScriptEngine]) {
    if (bindings == nil) {
      @throw [[JavaLangNullPointerException alloc] initWithNSString:@"bindings is null"];
    }
    [((id<JavaxScriptScriptContext>) nil_chk(context_)) setBindingsWithJavaxScriptBindings:bindings withInt:JavaxScriptScriptContext_ENGINE_SCOPE];
  }
  return self;
}

- (id)evalWithJavaIoReader:(JavaIoReader *)reader {
  return [self evalWithJavaIoReader:reader withJavaxScriptScriptContext:context_];
}

- (id)evalWithJavaIoReader:(JavaIoReader *)reader
   withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  return [self evalWithJavaIoReader:reader withJavaxScriptScriptContext:[self getScriptContextWithJavaxScriptBindings:bindings]];
}

- (id)evalWithNSString:(NSString *)script {
  return [self evalWithNSString:script withJavaxScriptScriptContext:context_];
}

- (id)evalWithNSString:(NSString *)script
withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  return [self evalWithNSString:script withJavaxScriptScriptContext:[self getScriptContextWithJavaxScriptBindings:bindings]];
}

- (id)getWithNSString:(NSString *)key {
  return [((id<JavaxScriptBindings>) nil_chk([self getBindingsWithInt:JavaxScriptScriptContext_ENGINE_SCOPE])) getWithId:key];
}

- (id<JavaxScriptBindings>)getBindingsWithInt:(int)scope {
  if (scope == JavaxScriptScriptContext_GLOBAL_SCOPE || scope == JavaxScriptScriptContext_ENGINE_SCOPE) {
    return [((id<JavaxScriptScriptContext>) nil_chk(context_)) getBindingsWithInt:scope];
  }
  else {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (id<JavaxScriptScriptContext>)getScriptContextWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings {
  if (bindings == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"ENGINE_SCOPE bindings cannot be null"];
  }
  id<JavaxScriptScriptContext> scriptContext = [[JavaxScriptSimpleScriptContext alloc] init];
  [scriptContext setBindingsWithJavaxScriptBindings:bindings withInt:JavaxScriptScriptContext_ENGINE_SCOPE];
  [scriptContext setBindingsWithJavaxScriptBindings:[self getBindingsWithInt:JavaxScriptScriptContext_GLOBAL_SCOPE] withInt:JavaxScriptScriptContext_GLOBAL_SCOPE];
  [scriptContext setReaderWithJavaIoReader:[((id<JavaxScriptScriptContext>) nil_chk(self->context_)) getReader]];
  [scriptContext setWriterWithJavaIoWriter:[self->context_ getWriter]];
  [scriptContext setErrorWriterWithJavaIoWriter:[self->context_ getErrorWriter]];
  return scriptContext;
}

- (void)putWithNSString:(NSString *)key
                 withId:(id)value {
  if (key == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"name is null"];
  }
  if ([((NSString *) nil_chk(key)) sequenceLength] == 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"name is empty"];
  }
  (void) [((id<JavaxScriptBindings>) nil_chk([self getBindingsWithInt:JavaxScriptScriptContext_ENGINE_SCOPE])) putWithId:key withId:value];
}

- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                                   withInt:(int)scope {
  if (scope == JavaxScriptScriptContext_GLOBAL_SCOPE || scope == JavaxScriptScriptContext_ENGINE_SCOPE) {
    [((id<JavaxScriptScriptContext>) nil_chk(context_)) setBindingsWithJavaxScriptBindings:bindings withInt:scope];
  }
  else {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (id<JavaxScriptScriptContext>)getContext {
  return self->context_;
}

- (void)setContextWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context {
  if (context == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"context is null"];
  }
  self->context_ = context;
}

- (id<JavaxScriptBindings>)createBindings {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)evalWithJavaIoReader:(JavaIoReader *)param0
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)evalWithNSString:(NSString *)param0
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<JavaxScriptScriptEngineFactory>)getFactory {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)copyAllFieldsTo:(JavaxScriptAbstractScriptEngine *)other {
  [super copyAllFieldsTo:other];
  other->context_ = context_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "evalWithJavaIoReader:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithJavaIoReader:withJavaxScriptBindings:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithNSString:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "evalWithNSString:withJavaxScriptBindings:", NULL, "LNSObject", 0x1, "JavaxScriptScriptException" },
    { "getWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getBindingsWithInt:", NULL, "LJavaxScriptBindings", 0x1, NULL },
    { "getScriptContextWithJavaxScriptBindings:", NULL, "LJavaxScriptScriptContext", 0x4, NULL },
    { "getContext", NULL, "LJavaxScriptScriptContext", 0x1, NULL },
    { "createBindings", NULL, "LJavaxScriptBindings", 0x401, NULL },
    { "evalWithJavaIoReader:withJavaxScriptScriptContext:", NULL, "LNSObject", 0x401, NULL },
    { "evalWithNSString:withJavaxScriptScriptContext:", NULL, "LNSObject", 0x401, NULL },
    { "getFactory", NULL, "LJavaxScriptScriptEngineFactory", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "context_", NULL, 0x4, "LJavaxScriptScriptContext" },
  };
  static J2ObjcClassInfo _JavaxScriptAbstractScriptEngine = { "AbstractScriptEngine", "javax.script", NULL, 0x401, 12, methods, 1, fields, 0, NULL};
  return &_JavaxScriptAbstractScriptEngine;
}

@end