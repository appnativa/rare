//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/JavaScriptEngineFactory.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREJavaScriptEngineFactory_H_
#define _RAREJavaScriptEngineFactory_H_

@class IOSObjectArray;
@class JavaxScriptScriptEngineManager;
@protocol JavaUtilList;
@protocol JavaxScriptScriptEngine;

#import "JreEmulation.h"
#include "javax/script/ScriptEngineFactory.h"

@interface RAREJavaScriptEngineFactory : NSObject < JavaxScriptScriptEngineFactory > {
 @public
  BOOL initialized_;
}

+ (id<JavaUtilList>)extensions;
+ (void)setExtensions:(id<JavaUtilList>)extensions;
+ (id<JavaUtilList>)mimeTypes;
+ (void)setMimeTypes:(id<JavaUtilList>)mimeTypes;
+ (id<JavaUtilList>)names;
+ (void)setNames:(id<JavaUtilList>)names;
+ (id<JavaUtilList>)loadedEngines;
+ (void)setLoadedEngines:(id<JavaUtilList>)loadedEngines;
- (void)destroy;
- (void)initialize__ OBJC_METHOD_FAMILY_NONE;
- (void)registerWithEngineManagerWithJavaxScriptScriptEngineManager:(JavaxScriptScriptEngineManager *)manager;
- (NSString *)getEngineName;
- (NSString *)getEngineVersion;
- (id<JavaUtilList>)getExtensions;
- (NSString *)getLanguageName;
- (NSString *)getLanguageVersion;
- (NSString *)getMethodCallSyntaxWithNSString:(NSString *)obj
                                 withNSString:(NSString *)method
                            withNSStringArray:(IOSObjectArray *)args;
- (id<JavaUtilList>)getMimeTypes;
- (id<JavaUtilList>)getNames;
- (NSString *)getOutputStatementWithNSString:(NSString *)toDisplay;
- (id)getParameterWithNSString:(NSString *)key;
- (NSString *)getProgramWithNSStringArray:(IOSObjectArray *)statements;
- (void)disposingWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e;
- (id<JavaxScriptScriptEngine>)getScriptEngine;
- (id)init;
- (void)copyAllFieldsTo:(RAREJavaScriptEngineFactory *)other;
@end

typedef RAREJavaScriptEngineFactory ComAppnativaRareScriptingJavaScriptEngineFactory;

#endif // _RAREJavaScriptEngineFactory_H_
