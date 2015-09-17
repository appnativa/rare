//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/scripting/ScriptManager.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREScriptManager_H_
#define _RAREScriptManager_H_

@class IOSObjectArray;
@class RAREErrorInformation;
@class RAREFrame;
@class RARESPOTApplication;
@class RAREWidgetContext;
@class RAREWindowViewer;
@protocol JavaxScriptBindings;
@protocol JavaxScriptScriptContext;
@protocol JavaxScriptScriptEngine;
@protocol RAREiPlatformAppContext;
@protocol RAREiScriptHandler;
@protocol RAREiScriptHandler_iScriptRunnable;
@protocol RAREiWindow;

#import "JreEmulation.h"
#include "com/appnativa/rare/scripting/aScriptManager.h"

@interface RAREScriptManager : RAREaScriptManager {
}

- (id)init;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
              withRARESPOTApplication:(RARESPOTApplication *)app;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
          withJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
              withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                        withRAREFrame:(RAREFrame *)frame
                         withNSString:(NSString *)scriptName
                         withNSString:(NSString *)source;
- (id)InvokeNativeScriptFunctionObjectWithId:(id)sparJSFunction
                           withNSObjectArray:(IOSObjectArray *)args;
- (id)InvokeNativeScriptFunctionObjectExWithId:(id)sparJSFunction
                                        withId:(id)nsArray;
+ (void)createEngineManager;
- (id)createVariableValueWithRAREWidgetContext:(RAREWidgetContext *)context
                                        withId:(id)javaobj;
- (id)unwrapWithId:(id)o;
- (void)setGlobalVariableWithNSString:(NSString *)name
                               withId:(id)value;
- (id<RAREiScriptHandler>)getRootHandlerWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                    withRAREiWindow:(id<RAREiWindow>)frame
                                                       withNSString:(NSString *)type
                                                       withNSString:(NSString *)name
                                                       withNSString:(NSString *)source
                                                        withBoolean:(BOOL)share;
- (id)InvokeNativeScriptFunctionObjectWithId:(id)function
                 withJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                                      withId:(id)scriptObject;
- (void)addLoadedScriptWithNSString:(NSString *)name;
- (void)configureDebuggingInfoWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                             withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)ctx
                                             withNSString:(NSString *)name
                                             withNSString:(NSString *)source;
- (id<JavaxScriptScriptContext>)createScriptContextWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e;
- (RAREWindowViewer *)createWindowViewerWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                                       withNSString:(NSString *)name
                                                             withId:(id)win
                                               withRAREWindowViewer:(RAREWindowViewer *)parent
                                             withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (void)initializeEngineWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                       withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context OBJC_METHOD_FAMILY_NONE;
- (void)invokeAndWaitWithRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)r;
- (void)loadDebugger;
- (RAREWidgetContext *)newWidgetContext OBJC_METHOD_FAMILY_NONE;
- (void)saveSourceForDebuggingWithNSString:(NSString *)scriptName
                              withNSString:(NSString *)source;
- (void)setupDebugger;
- (void)setupDynamicBindingsWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e
                           withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc;
- (void)setupScriptingShellWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine;
- (NSString *)spiClassNameForJavascripEngineFactory;
- (RAREErrorInformation *)getNativeScriptErrorInformationWithId:(id)error;
- (RAREErrorInformation *)getNativeScriptErrorInformationExWithId:(id)error;
- (BOOL)isNativeScriptFunctionObjectWithId:(id)function;
- (NSString *)getInitScriptWithNSString:(NSString *)name;
- (NSString *)getResourceStreamAsStringWithNSString:(NSString *)source;
@end

typedef RAREScriptManager ComAppnativaRareScriptingScriptManager;

#endif // _RAREScriptManager_H_
