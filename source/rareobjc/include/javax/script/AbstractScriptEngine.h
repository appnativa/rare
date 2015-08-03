//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/AbstractScriptEngine.java
//
//  Created by decoteaud on 6/13/14.
//

#ifndef _JavaxScriptAbstractScriptEngine_H_
#define _JavaxScriptAbstractScriptEngine_H_

@class JavaIoReader;
@protocol JavaxScriptBindings;
@protocol JavaxScriptScriptContext;
@protocol JavaxScriptScriptEngineFactory;

#import "JreEmulation.h"
#include "javax/script/ScriptEngine.h"

@interface JavaxScriptAbstractScriptEngine : NSObject < JavaxScriptScriptEngine > {
 @public
  id<JavaxScriptScriptContext> context_;
}

- (id)init;
- (id)initWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)evalWithJavaIoReader:(JavaIoReader *)reader;
- (id)evalWithJavaIoReader:(JavaIoReader *)reader
   withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)evalWithNSString:(NSString *)script;
- (id)evalWithNSString:(NSString *)script
withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)getWithNSString:(NSString *)key;
- (id<JavaxScriptBindings>)getBindingsWithInt:(int)scope;
- (id<JavaxScriptScriptContext>)getScriptContextWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (void)putWithNSString:(NSString *)key
                 withId:(id)value;
- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                                   withInt:(int)scope;
- (id<JavaxScriptScriptContext>)getContext;
- (void)setContextWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (id<JavaxScriptBindings>)createBindings;
- (id)evalWithJavaIoReader:(JavaIoReader *)param0
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)param1;
- (id)evalWithNSString:(NSString *)param0
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)param1;
- (id<JavaxScriptScriptEngineFactory>)getFactory;
- (void)copyAllFieldsTo:(JavaxScriptAbstractScriptEngine *)other;
@end

J2OBJC_FIELD_SETTER(JavaxScriptAbstractScriptEngine, context_, id<JavaxScriptScriptContext>)

#endif // _JavaxScriptAbstractScriptEngine_H_
