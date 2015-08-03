//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/SimpleScriptContext.java
//
//  Created by decoteaud on 6/13/14.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "java/io/PrintStream.h"
#include "java/io/PrintWriter.h"
#include "java/io/Reader.h"
#include "java/io/Writer.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/Integer.h"
#include "java/lang/NullPointerException.h"
#include "java/lang/System.h"
#include "java/util/Arrays.h"
#include "java/util/Collections.h"
#include "java/util/List.h"
#include "javax/script/Bindings.h"
#include "javax/script/ScriptContext.h"
#include "javax/script/SimpleBindings.h"
#include "javax/script/SimpleScriptContext.h"

@implementation JavaxScriptSimpleScriptContext

static id<JavaUtilList> JavaxScriptSimpleScriptContext_SCOPES_;

+ (id<JavaUtilList>)SCOPES {
  return JavaxScriptSimpleScriptContext_SCOPES_;
}

- (id)init {
  return [self initJavaxScriptSimpleScriptContextWithJavaxScriptBindings:[[JavaxScriptSimpleBindings alloc] init]];
}

- (id)initJavaxScriptSimpleScriptContextWithJavaxScriptBindings:(id<JavaxScriptBindings>)engineScope {
  if (self = [super init]) {
    globalScope_ = nil;
    self->engineScope_ = engineScope;
    writer_ = [[JavaIoPrintWriter alloc] initWithJavaIoOutputStream:[JavaLangSystem out] withBoolean:YES];
    errorWriter_ = [[JavaIoPrintWriter alloc] initWithJavaIoOutputStream:[JavaLangSystem err] withBoolean:YES];
  }
  return self;
}

- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)engineScope {
  return [self initJavaxScriptSimpleScriptContextWithJavaxScriptBindings:engineScope];
}

- (void)checkNameWithNSString:(NSString *)name {
  if (name == nil) {
    @throw [[JavaLangNullPointerException alloc] initWithNSString:@"name must not be null"];
  }
  if ([((NSString *) nil_chk(name)) sequenceLength] == 0) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"name must not be an empty string"];
  }
}

- (id)getAttributeWithNSString:(NSString *)name {
  [self checkNameWithNSString:name];
  id engineObject = [((id<JavaxScriptBindings>) nil_chk(engineScope_)) getWithId:name];
  if (engineObject != nil) {
    return engineObject;
  }
  else if (globalScope_ != nil) {
    return [globalScope_ getWithId:name];
  }
  else {
    return nil;
  }
}

- (id)getAttributeWithNSString:(NSString *)name
                       withInt:(int)scope {
  [self checkNameWithNSString:name];
  switch (scope) {
    case JavaxScriptScriptContext_ENGINE_SCOPE:
    return [((id<JavaxScriptBindings>) nil_chk(engineScope_)) getWithId:name];
    case JavaxScriptScriptContext_GLOBAL_SCOPE:
    return globalScope_ != nil ? [globalScope_ getWithId:name] : nil;
    default:
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (int)getAttributesScopeWithNSString:(NSString *)name {
  [self checkNameWithNSString:name];
  if ([((id<JavaxScriptBindings>) nil_chk(engineScope_)) containsKeyWithId:name]) {
    return JavaxScriptScriptContext_ENGINE_SCOPE;
  }
  else if (globalScope_ != nil && [globalScope_ containsKeyWithId:name]) {
    return JavaxScriptScriptContext_GLOBAL_SCOPE;
  }
  return -1;
}

- (id<JavaxScriptBindings>)getBindingsWithInt:(int)scope {
  switch (scope) {
    case JavaxScriptScriptContext_ENGINE_SCOPE:
    return engineScope_;
    case JavaxScriptScriptContext_GLOBAL_SCOPE:
    return globalScope_;
    default:
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (id)removeAttributeWithNSString:(NSString *)name
                          withInt:(int)scope {
  [self checkNameWithNSString:name];
  switch (scope) {
    case JavaxScriptScriptContext_ENGINE_SCOPE:
    return [((id<JavaxScriptBindings>) nil_chk(engineScope_)) removeWithId:name];
    case JavaxScriptScriptContext_GLOBAL_SCOPE:
    return globalScope_ != nil ? [globalScope_ removeWithId:name] : nil;
    default:
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (void)setAttributeWithNSString:(NSString *)name
                          withId:(id)value
                         withInt:(int)scope {
  [self checkNameWithNSString:name];
  switch (scope) {
    case JavaxScriptScriptContext_ENGINE_SCOPE:
    (void) [((id<JavaxScriptBindings>) nil_chk(engineScope_)) putWithId:name withId:value];
    break;
    case JavaxScriptScriptContext_GLOBAL_SCOPE:
    if (globalScope_ != nil) {
      (void) [globalScope_ putWithId:name withId:value];
    }
    break;
    default:
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                                   withInt:(int)scope {
  switch (scope) {
    case JavaxScriptScriptContext_ENGINE_SCOPE:
    if (bindings == nil) {
      @throw [[JavaLangNullPointerException alloc] initWithNSString:@"binding is null for ENGINE_SCOPE scope"];
    }
    engineScope_ = bindings;
    break;
    case JavaxScriptScriptContext_GLOBAL_SCOPE:
    globalScope_ = bindings;
    break;
    default:
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"invalid scope"];
  }
}

- (id<JavaUtilList>)getScopes {
  return JavaxScriptSimpleScriptContext_SCOPES_;
}

- (JavaIoReader *)getReader {
  return reader_;
}

- (void)setReaderWithJavaIoReader:(JavaIoReader *)reader {
  self->reader_ = reader;
}

- (JavaIoWriter *)getWriter {
  return writer_;
}

- (void)setWriterWithJavaIoWriter:(JavaIoWriter *)writer {
  self->writer_ = writer;
}

- (JavaIoWriter *)getErrorWriter {
  return errorWriter_;
}

- (void)setErrorWriterWithJavaIoWriter:(JavaIoWriter *)writer {
  self->errorWriter_ = writer;
}

+ (void)initialize {
  if (self == [JavaxScriptSimpleScriptContext class]) {
    JavaxScriptSimpleScriptContext_SCOPES_ = [JavaUtilCollections unmodifiableListWithJavaUtilList:[JavaUtilArrays asListWithNSObjectArray:[IOSObjectArray arrayWithObjects:(id[]){ [[JavaLangInteger alloc] initWithInt:JavaxScriptScriptContext_ENGINE_SCOPE], [[JavaLangInteger alloc] initWithInt:JavaxScriptScriptContext_GLOBAL_SCOPE] } count:2 type:[IOSClass classWithClass:[JavaLangInteger class]]]]];
  }
}

- (void)copyAllFieldsTo:(JavaxScriptSimpleScriptContext *)other {
  [super copyAllFieldsTo:other];
  other->engineScope_ = engineScope_;
  other->errorWriter_ = errorWriter_;
  other->globalScope_ = globalScope_;
  other->reader_ = reader_;
  other->writer_ = writer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "checkNameWithNSString:", NULL, "V", 0x2, NULL },
    { "getAttributeWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "getAttributeWithNSString:withInt:", NULL, "LNSObject", 0x1, NULL },
    { "getBindingsWithInt:", NULL, "LJavaxScriptBindings", 0x1, NULL },
    { "removeAttributeWithNSString:withInt:", NULL, "LNSObject", 0x1, NULL },
    { "getScopes", NULL, "LJavaUtilList", 0x1, NULL },
    { "getReader", NULL, "LJavaIoReader", 0x1, NULL },
    { "getWriter", NULL, "LJavaIoWriter", 0x1, NULL },
    { "getErrorWriter", NULL, "LJavaIoWriter", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "globalScope_", NULL, 0x4, "LJavaxScriptBindings" },
    { "engineScope_", NULL, 0x4, "LJavaxScriptBindings" },
    { "reader_", NULL, 0x4, "LJavaIoReader" },
    { "writer_", NULL, 0x4, "LJavaIoWriter" },
    { "errorWriter_", NULL, 0x4, "LJavaIoWriter" },
    { "SCOPES_", NULL, 0x1a, "LJavaUtilList" },
  };
  static J2ObjcClassInfo _JavaxScriptSimpleScriptContext = { "SimpleScriptContext", "javax.script", NULL, 0x1, 9, methods, 6, fields, 0, NULL};
  return &_JavaxScriptSimpleScriptContext;
}

@end
