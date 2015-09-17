//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/aScriptManager.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaScriptManager_H_
#define _RAREaScriptManager_H_

@class IOSObjectArray;
@class JavaLangRuntimeException;
@class JavaLangThrowable;
@class JavaUtilConcurrentConcurrentHashMap;
@class JavaUtilEventObject;
@class JavaUtilHashMap;
@class JavaUtilHashSet;
@class JavaxScriptScriptEngineManager;
@class JavaxScriptSimpleBindings;
@class RAREErrorInformation;
@class RAREEventHandlerInterface;
@class RAREMultiScript;
@class RARESPOTApplication;
@class RAREScriptingEvent;
@class RAREWidgetContext;
@class RAREWindowViewer;
@class RAREaWindowViewer;
@protocol JavaxScriptBindings;
@protocol JavaxScriptCompilable;
@protocol JavaxScriptScriptContext;
@protocol JavaxScriptScriptEngine;
@protocol RAREiDebugHandler;
@protocol RAREiEventHandler;
@protocol RAREiFormViewer;
@protocol RAREiPlatformAppContext;
@protocol RAREiViewer;
@protocol RAREiWidget;
@protocol RAREiWindow;

#import "JreEmulation.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "java/lang/Runnable.h"
#include "java/util/concurrent/Callable.h"
#include "javax/script/CompiledScript.h"

@interface RAREaScriptManager : NSObject < RAREiScriptHandler > {
 @public
  id<RAREiPlatformAppContext> appContext_;
  NSString *executingScriptName_;
  id<JavaxScriptBindings> mainWindowBindings_;
  id<JavaxScriptCompilable> scriptCompiler_;
  id<JavaxScriptScriptContext> scriptContext_;
  id<JavaxScriptScriptEngine> scriptEngine_;
  NSString *scriptName_;
  id selfObject_;
  RAREWindowViewer *theWindow_;
  BOOL tracingEnabled_;
}

+ (JavaUtilHashMap *)loadedEngines;
+ (BOOL)_constants_populated;
+ (BOOL *)_constants_populatedRef;
+ (JavaxScriptSimpleBindings *)nullBindings;
+ (void)setNullBindings:(JavaxScriptSimpleBindings *)nullBindings;
+ (JavaUtilHashSet *)protocols;
+ (id<RAREiDebugHandler>)_debugger;
+ (void)set_debugger:(id<RAREiDebugHandler>)_debugger;
+ (BOOL)_rhinoInitialized;
+ (BOOL *)_rhinoInitializedRef;
+ (BOOL)debuggerLoaded;
+ (BOOL *)debuggerLoadedRef;
+ (JavaxScriptScriptEngineManager *)engineManager;
+ (void)setEngineManager:(JavaxScriptScriptEngineManager *)engineManager;
+ (id<JavaxScriptBindings>)globalBindings;
+ (void)setGlobalBindings:(id<JavaxScriptBindings>)globalBindings;
+ (JavaUtilConcurrentConcurrentHashMap *)nameMappings;
+ (void)setNameMappings:(JavaUtilConcurrentConcurrentHashMap *)nameMappings;
+ (id)postExecuteCode;
+ (void)setPostExecuteCode:(id)postExecuteCode;
+ (id)preExecuteCode;
+ (void)setPreExecuteCode:(id)preExecuteCode;
+ (int)shellPort;
+ (int *)shellPortRef;
- (id)init;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
              withRARESPOTApplication:(RARESPOTApplication *)app;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
              withRARESPOTApplication:(RARESPOTApplication *)app
              withJavaxScriptBindings:(id<JavaxScriptBindings>)globals;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
          withJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
              withJavaxScriptBindings:(id<JavaxScriptBindings>)bindings
                               withId:(id)frame
                         withNSString:(NSString *)scriptName
                         withNSString:(NSString *)source;
- (id)callFunctionWithRAREWidgetContext:(RAREWidgetContext *)context
                           withNSString:(NSString *)name
                      withNSObjectArray:(IOSObjectArray *)args;
+ (void)clearGlobalConstants;
- (id)compileWithRAREWidgetContext:(RAREWidgetContext *)context
                      withNSString:(NSString *)name
                      withNSString:(NSString *)code;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithRAREWidgetContext:(RAREWidgetContext *)context
                                                                     withId:(id)compiled
                                                     withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithRAREWidgetContext:(RAREWidgetContext *)context
                                                               withNSString:(NSString *)source
                                                     withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithRAREWidgetContext:(RAREWidgetContext *)context
                                                               withNSString:(NSString *)code
                                                               withNSString:(NSString *)language
                                                     withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (RAREWidgetContext *)createScriptingContextWithId:(id)javaobj;
- (void)dispose;
- (id)evaluateWithRAREWidgetContext:(RAREWidgetContext *)context
                             withId:(id)compiled
             withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id)evaluateWithRAREWidgetContext:(RAREWidgetContext *)context
                       withNSString:(NSString *)code
             withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id)evaluateWithRAREWidgetContext:(RAREWidgetContext *)context
                       withNSString:(NSString *)code
                       withNSString:(NSString *)language
             withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (void)executeWithRAREWidgetContext:(RAREWidgetContext *)context
                              withId:(id)compiled
              withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (void)executeWithRAREWidgetContext:(RAREWidgetContext *)context
                        withNSString:(NSString *)code
              withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (void)executeWithRAREWidgetContext:(RAREWidgetContext *)context
                        withNSString:(NSString *)code
                        withNSString:(NSString *)language
              withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (void)loadScriptWithNSString:(NSString *)name
                  withNSString:(NSString *)code
                  withNSString:(NSString *)language;
+ (void)registerEngineFactoriesWithNSString:(NSString *)stringList;
+ (void)registerEngineFactoryWithNSString:(NSString *)className_;
- (void)restoreSaveWindowWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc
                                withRAREWidgetContext:(RAREWidgetContext *)wc;
- (void)runTaskWithRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)r;
- (void)submitTaskWithRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)r;
- (void)setGlobalVariableWithNSString:(NSString *)name
                               withId:(id)value;
- (RAREWidgetContext *)setScriptingContextWithRAREiViewer:(id<RAREiViewer>)viewer
                                             withNSString:(NSString *)type
                                             withNSString:(NSString *)name
                                             withNSString:(NSString *)source
                                              withBoolean:(BOOL)shared;
- (void)setScriptingVariableWithRAREWidgetContext:(RAREWidgetContext *)context
                                     withNSString:(NSString *)name
                                           withId:(id)scriptobj;
- (id<RAREiPlatformAppContext>)getAppContext;
- (RAREWindowViewer *)getCurrentWindowViewer;
- (id<JavaxScriptScriptEngine>)getEngineWithNSString:(NSString *)type
                                         withBoolean:(BOOL)reuse
                        withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (RAREErrorInformation *)getErrorInformationWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                                  withId:(id)error;
- (id<RAREiFormViewer>)getFormViewer;
- (NSString *)getFunctionCallWithNSString:(NSString *)function
                        withNSStringArray:(IOSObjectArray *)args;
- (id<JavaxScriptBindings>)getGlobalBindings;
+ (id<JavaxScriptScriptEngine>)getJavaScriptEngine;
- (NSString *)getMethodCallWithNSString:(NSString *)obj
                           withNSString:(NSString *)method
                      withNSStringArray:(IOSObjectArray *)args;
+ (JavaxScriptScriptEngineManager *)getScriptEngineManager;
- (id)getScriptingContext;
- (id<JavaxScriptScriptEngine>)getScriptingEngine;
- (NSString *)getScriptingName;
- (id)getScriptingVariableWithRAREWidgetContext:(RAREWidgetContext *)context
                                   withNSString:(NSString *)name;
- (id<RAREiWidget>)getWidget;
- (RAREaWindowViewer *)getWindowViewer;
+ (BOOL)isInlineScriptWithNSString:(NSString *)name;
+ (BOOL)isRhinoInitialized;
- (id<JavaxScriptScriptContext>)getScriptContextWithRAREWidgetContext:(RAREWidgetContext *)context;
- (id)InvokeNativeScriptFunctionObjectWithId:(id)function
                 withJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                                      withId:(id)scriptObject;
- (void)addLoadedScriptWithNSString:(NSString *)name;
- (id)compileWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e
                   withRAREWidgetContext:(RAREWidgetContext *)context
                            withNSString:(NSString *)name
                            withNSString:(NSString *)code;
- (void)configureDebuggingInfoWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                             withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)ctx
                                             withNSString:(NSString *)name
                                             withNSString:(NSString *)source;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithRAREWidgetContext:(RAREWidgetContext *)context
                                                                     withId:(id)code
                                                                withBoolean:(BOOL)string
                                                     withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id<RAREiScriptHandler_iScriptRunnable>)createRunnerWithRAREWidgetContext:(RAREWidgetContext *)context
                                                               withNSString:(NSString *)source
                                                               withNSString:(NSString *)language
                                                                withBoolean:(BOOL)string
                                                     withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id<JavaxScriptScriptContext>)createScriptContextWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e;
- (RAREWindowViewer *)createWindowViewerWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)ctx
                                                       withNSString:(NSString *)name
                                                             withId:(id)win
                                               withRAREWindowViewer:(RAREWindowViewer *)parent
                                             withRAREiScriptHandler:(id<RAREiScriptHandler>)sh;
- (void)handleScriptTracingWithNSString:(NSString *)source;
- (void)initializeCompiler OBJC_METHOD_FAMILY_NONE;
- (void)initializeEngineExWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context OBJC_METHOD_FAMILY_NONE;
- (NSString *)getInitScriptWithNSString:(NSString *)name;
- (void)initializeEngineWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
                       withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context OBJC_METHOD_FAMILY_NONE;
- (void)initializeMappings OBJC_METHOD_FAMILY_NONE;
- (void)invokeAndWaitWithRAREiScriptHandler_iScriptRunnable:(id<RAREiScriptHandler_iScriptRunnable>)r;
- (void)loadDebugger;
- (RAREWidgetContext *)newWidgetContext OBJC_METHOD_FAMILY_NONE;
- (void)saveCurrentWindowWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc
                                withRAREWidgetContext:(RAREWidgetContext *)wc;
- (void)saveSourceForDebuggingWithNSString:(NSString *)scriptName
                              withNSString:(NSString *)source;
- (void)setupDebugger;
- (void)setupDynamicBindingsWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)e
                           withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc;
- (void)setupScriptingShellWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine;
- (NSString *)spiClassNameForJavascripEngineFactory;
- (void)setWindowWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc
                                       withId:(id)window;
- (RAREErrorInformation *)getNativeScriptErrorInformationWithId:(id)error;
- (BOOL)isNativeScriptFunctionObjectWithId:(id)function;
- (id)createVariableValueWithRAREWidgetContext:(RAREWidgetContext *)param0
                                        withId:(id)param1;
- (id<RAREiScriptHandler>)getRootHandlerWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)param0
                                                    withRAREiWindow:(id<RAREiWindow>)param1
                                                       withNSString:(NSString *)param2
                                                       withNSString:(NSString *)param3
                                                       withNSString:(NSString *)param4
                                                        withBoolean:(BOOL)param5;
- (id)unwrapWithId:(id)param0;
- (void)copyAllFieldsTo:(RAREaScriptManager *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager, appContext_, id<RAREiPlatformAppContext>)
J2OBJC_FIELD_SETTER(RAREaScriptManager, executingScriptName_, NSString *)
J2OBJC_FIELD_SETTER(RAREaScriptManager, mainWindowBindings_, id<JavaxScriptBindings>)
J2OBJC_FIELD_SETTER(RAREaScriptManager, scriptCompiler_, id<JavaxScriptCompilable>)
J2OBJC_FIELD_SETTER(RAREaScriptManager, scriptContext_, id<JavaxScriptScriptContext>)
J2OBJC_FIELD_SETTER(RAREaScriptManager, scriptEngine_, id<JavaxScriptScriptEngine>)
J2OBJC_FIELD_SETTER(RAREaScriptManager, scriptName_, NSString *)
J2OBJC_FIELD_SETTER(RAREaScriptManager, selfObject_, id)
J2OBJC_FIELD_SETTER(RAREaScriptManager, theWindow_, RAREWindowViewer *)

typedef RAREaScriptManager ComAppnativaRareScriptingAScriptManager;

@interface RAREaScriptManager_aCompiledScript : JavaxScriptCompiledScript {
 @public
  __weak id<JavaxScriptScriptContext> context_;
  __weak id<JavaxScriptScriptEngine> engine_;
  __weak RAREaScriptManager *scriptManager_;
  NSString *scriptName_;
  __weak RAREWidgetContext *widgetContext_;
  BOOL usesBindings_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (void)cancelWithBoolean:(BOOL)interrupt;
- (id)eval;
- (id)evalWithJavaxScriptBindings:(id<JavaxScriptBindings>)bindings;
- (void)setEnvironmentWithRAREaScriptManager:(RAREaScriptManager *)sm
                       withRAREWidgetContext:(RAREWidgetContext *)wc
                      withRAREScriptingEvent:(RAREScriptingEvent *)event;
- (id<JavaxScriptScriptEngine>)getEngine;
- (void)copyAllFieldsTo:(RAREaScriptManager_aCompiledScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_aCompiledScript, scriptName_, NSString *)

@interface RAREaScriptManager_CompiledMultiScript : RAREaScriptManager_aCompiledScript {
 @public
  RAREMultiScript *theScript_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                  withRAREMultiScript:(RAREMultiScript *)ms;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (NSString *)description;
- (id)compileWithNSString:(NSString *)code
withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)sc;
- (void)copyAllFieldsTo:(RAREaScriptManager_CompiledMultiScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_CompiledMultiScript, theScript_, RAREMultiScript *)

@interface RAREaScriptManager_CompiledScriptEx : RAREaScriptManager_aCompiledScript {
 @public
  NSString *theCode_;
  JavaxScriptCompiledScript *theScript_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                         withNSString:(NSString *)name
        withJavaxScriptCompiledScript:(JavaxScriptCompiledScript *)script;
- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                         withNSString:(NSString *)name
                         withNSString:(NSString *)code;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREaScriptManager_CompiledScriptEx *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_CompiledScriptEx, theCode_, NSString *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_CompiledScriptEx, theScript_, JavaxScriptCompiledScript *)

@interface RAREaScriptManager_EvalRunner : NSObject < RAREiScriptHandler_iScriptRunnable, JavaUtilConcurrentCallable > {
 @public
  BOOL disposed_;
  long long int runTime_;
  BOOL handleException_;
  id cancelRunner_;
  RAREScriptingEvent *event_;
  JavaLangRuntimeException *executionException_;
  RAREWidgetContext *runContext_;
  __weak id<RAREiScriptHandler> scriptHandler_;
  id theResult_;
  id theScript_;
  NSString *theSource_;
  BOOL canceled_;
  BOOL cancelerOnEventThread_;
  BOOL done_;
  BOOL notifierOnEventThread_;
  id notifierRunner_;
}

+ (NSString *)DISPOSE_EVENT_TYPE;
- (id)initWithRAREiScriptHandler:(id<RAREiScriptHandler>)sh
           withRAREWidgetContext:(RAREWidgetContext *)context
                    withNSString:(NSString *)source
          withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id)initWithRAREiScriptHandler:(id<RAREiScriptHandler>)sh
           withRAREWidgetContext:(RAREWidgetContext *)context
                          withId:(id)script
                     withBoolean:(BOOL)string
          withRAREScriptingEvent:(RAREScriptingEvent *)e;
- (id)call;
- (void)cancelWithBoolean:(BOOL)canInterrupt;
- (void)dispose;
- (void)run;
- (void)setCancelRunnerWithId:(id)code
                  withBoolean:(BOOL)runOnEventThread;
- (void)setHandleExceptionWithBoolean:(BOOL)handle;
- (void)setNotifierRunnerWithId:(id)code
                    withBoolean:(BOOL)runOnEventThread;
- (id)getCancelRunner;
- (JavaLangThrowable *)getExecutionException;
- (id)getNotifierRunner;
- (id)getResult;
- (NSString *)getResultString;
- (BOOL)isCanceled;
- (BOOL)isDisposed;
- (BOOL)isDone;
- (id)executeCodeWithRAREWindowViewer:(RAREWindowViewer *)win
                      withRAREiWidget:(id<RAREiWidget>)w;
- (void)notifyAllWithId:(id)o;
- (id<JavaLangRunnable>)getRunnableWithId:(id)o;
- (void)copyAllFieldsTo:(RAREaScriptManager_EvalRunner *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, cancelRunner_, id)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, event_, RAREScriptingEvent *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, executionException_, JavaLangRuntimeException *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, runContext_, RAREWidgetContext *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, theResult_, id)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, theScript_, id)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, theSource_, NSString *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner, notifierRunner_, id)

@interface RAREaScriptManager_EvalRunner_$1 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiWidget> val$ww_;
  JavaLangThrowable *val$ex_;
}

- (void)run;
- (id)initWithRAREiWidget:(id<RAREiWidget>)capture$0
    withJavaLangThrowable:(JavaLangThrowable *)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner_$1, val$ww_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EvalRunner_$1, val$ex_, JavaLangThrowable *)

@interface RAREaScriptManager_JSFunctionScript : RAREaScriptManager_aCompiledScript {
 @public
  id _this_;
  id function_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                               withId:(id)f;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (NSString *)description;
- (void)setEnvironmentWithRAREaScriptManager:(RAREaScriptManager *)sm
                       withRAREWidgetContext:(RAREWidgetContext *)wc
                      withRAREScriptingEvent:(RAREScriptingEvent *)event;
- (void)copyAllFieldsTo:(RAREaScriptManager_JSFunctionScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_JSFunctionScript, _this_, id)
J2OBJC_FIELD_SETTER(RAREaScriptManager_JSFunctionScript, function_, id)

@interface RAREaScriptManager_RunnableScript : RAREaScriptManager_aCompiledScript {
 @public
  id<JavaLangRunnable> runnable_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
                 withJavaLangRunnable:(id<JavaLangRunnable>)r;
- (void)cancelWithBoolean:(BOOL)interrupt;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (NSString *)description;
- (void)copyAllFieldsTo:(RAREaScriptManager_RunnableScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_RunnableScript, runnable_, id<JavaLangRunnable>)

@interface RAREaScriptManager_EventHandlerInterfaceScript : RAREaScriptManager_aCompiledScript {
 @public
  RAREEventHandlerInterface *handler_;
  id<RAREiEventHandler> eventHandler_;
  JavaUtilEventObject *nativeEvent_;
  NSString *eventName_;
}

- (id)initWithJavaxScriptScriptEngine:(id<JavaxScriptScriptEngine>)engine
         withJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context
        withRAREEventHandlerInterface:(RAREEventHandlerInterface *)handler;
- (void)setEnvironmentWithRAREaScriptManager:(RAREaScriptManager *)sm
                       withRAREWidgetContext:(RAREWidgetContext *)wc
                      withRAREScriptingEvent:(RAREScriptingEvent *)event;
- (id)evalWithJavaxScriptScriptContext:(id<JavaxScriptScriptContext>)context;
- (void)copyAllFieldsTo:(RAREaScriptManager_EventHandlerInterfaceScript *)other;
@end

J2OBJC_FIELD_SETTER(RAREaScriptManager_EventHandlerInterfaceScript, handler_, RAREEventHandlerInterface *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EventHandlerInterfaceScript, eventHandler_, id<RAREiEventHandler>)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EventHandlerInterfaceScript, nativeEvent_, JavaUtilEventObject *)
J2OBJC_FIELD_SETTER(RAREaScriptManager_EventHandlerInterfaceScript, eventName_, NSString *)

#endif // _RAREaScriptManager_H_
