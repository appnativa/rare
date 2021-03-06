//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/ScriptEngine.java
//
//  Created by decoteaud on 5/30/14.
//

#ifndef _JavaxScriptScriptEngine_H_
#define _JavaxScriptScriptEngine_H_

@class JavaIoReader;
@protocol JavaxScriptBindings;
@protocol JavaxScriptScriptContext;
@protocol JavaxScriptScriptEngineFactory;

#import "JreEmulation.h"

@protocol JavaxScriptScriptEngine < NSObject, JavaObject >
- (id<JavaxScriptBindings>)createBindings;
- (id)evalWithJavaIoReader:(JavaIoReader *)reader;
- (id)evalWithJavaIoReader:(JavaIoReader *)reader
   withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)evalWithJavaIoReader:(JavaIoReader *)reader
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (id)evalWithNSString:(NSString *)script;
- (id)evalWithNSString:(NSString *)script
withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (id)evalWithNSString:(NSString *)script
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (id)getWithNSString:(NSString *)key;
- (id<JavaxScriptScriptEngineFactory>)getFactory;
- (id<JavaxScriptBindings>)getBindingsWithInt:(int)scope;
- (void)putWithNSString:(NSString *)key
                 withId:(id)value;
- (void)setBindingsWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                                   withInt:(int)scope;
- (id<JavaxScriptScriptContext>)getContext;
- (void)setContextWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
@end

@interface JavaxScriptScriptEngine : NSObject {
}
+ (NSString *)ARGV;
+ (NSString *)FILENAME;
+ (NSString *)ENGINE;
+ (NSString *)ENGINE_VERSION;
+ (NSString *)LANGUAGE;
+ (NSString *)LANGUAGE_VERSION;
+ (NSString *)NAME;
@end

#endif // _JavaxScriptScriptEngine_H_
