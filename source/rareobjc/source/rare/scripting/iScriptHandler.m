//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/iScriptHandler.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSObjectArray.h"
#include "com/appnativa/rare/ErrorInformation.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/scripting/ScriptingEvent.h"
#include "com/appnativa/rare/scripting/WidgetContext.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/ui/iWindow.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/aWindowViewer.h"
#include "com/appnativa/rare/viewer/iFormViewer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Throwable.h"


@interface RAREiScriptHandler : NSObject
@end

@implementation RAREiScriptHandler

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "callFunctionWithRAREWidgetContext:withNSString:withNSObjectArray:", NULL, "LNSObject", 0x401, NULL },
    { "compileWithRAREWidgetContext:withNSString:withNSString:", NULL, "LNSObject", 0x401, NULL },
    { "createRunnerWithRAREWidgetContext:withId:withRAREScriptingEvent:", NULL, "LRAREiScriptHandler_iScriptRunnable", 0x401, NULL },
    { "createRunnerWithRAREWidgetContext:withNSString:withRAREScriptingEvent:", NULL, "LRAREiScriptHandler_iScriptRunnable", 0x401, NULL },
    { "createRunnerWithRAREWidgetContext:withNSString:withNSString:withRAREScriptingEvent:", NULL, "LRAREiScriptHandler_iScriptRunnable", 0x401, NULL },
    { "createVariableValueWithRAREWidgetContext:withId:", NULL, "LNSObject", 0x401, NULL },
    { "createScriptingContextWithId:", NULL, "LRAREWidgetContext", 0x401, NULL },
    { "setScriptingContextWithRAREiViewer:withNSString:withNSString:withNSString:withBoolean:", NULL, "LRAREWidgetContext", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "evaluateWithRAREWidgetContext:withId:withRAREScriptingEvent:", NULL, "LNSObject", 0x401, NULL },
    { "evaluateWithRAREWidgetContext:withNSString:withRAREScriptingEvent:", NULL, "LNSObject", 0x401, NULL },
    { "evaluateWithRAREWidgetContext:withNSString:withNSString:withRAREScriptingEvent:", NULL, "LNSObject", 0x401, NULL },
    { "executeWithRAREWidgetContext:withId:withRAREScriptingEvent:", NULL, "V", 0x401, NULL },
    { "executeWithRAREWidgetContext:withNSString:withRAREScriptingEvent:", NULL, "V", 0x401, NULL },
    { "executeWithRAREWidgetContext:withNSString:withNSString:withRAREScriptingEvent:", NULL, "V", 0x401, NULL },
    { "loadScriptWithNSString:withNSString:withNSString:", NULL, "V", 0x401, NULL },
    { "runTaskWithRAREiScriptHandler_iScriptRunnable:", NULL, "V", 0x401, NULL },
    { "submitTaskWithRAREiScriptHandler_iScriptRunnable:", NULL, "V", 0x401, NULL },
    { "unwrapWithId:", NULL, "LNSObject", 0x401, NULL },
    { "setScriptingVariableWithRAREWidgetContext:withNSString:withId:", NULL, "V", 0x401, NULL },
    { "setGlobalVariableWithNSString:withId:", NULL, "V", 0x401, NULL },
    { "getCurrentWindowViewer", NULL, "LRAREWindowViewer", 0x401, NULL },
    { "getErrorInformationWithRAREiPlatformAppContext:withId:", NULL, "LRAREErrorInformation", 0x401, NULL },
    { "getFormViewer", NULL, "LRAREiFormViewer", 0x401, NULL },
    { "getFunctionCallWithNSString:withNSStringArray:", NULL, "LNSString", 0x401, NULL },
    { "getMethodCallWithNSString:withNSString:withNSStringArray:", NULL, "LNSString", 0x401, NULL },
    { "getRootHandlerWithRAREiPlatformAppContext:withRAREiWindow:withNSString:withNSString:withNSString:withBoolean:", NULL, "LRAREiScriptHandler", 0x401, NULL },
    { "getScriptingName", NULL, "LNSString", 0x401, NULL },
    { "getScriptingVariableWithRAREWidgetContext:withNSString:", NULL, "LNSObject", 0x401, NULL },
    { "getWidget", NULL, "LRAREiWidget", 0x401, NULL },
    { "getWindowViewer", NULL, "LRAREaWindowViewer", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiScriptHandler = { "iScriptHandler", "com.appnativa.rare.scripting", NULL, 0x201, 31, methods, 0, NULL, 0, NULL};
  return &_RAREiScriptHandler;
}

@end

@interface RAREiScriptHandler_iScriptRunnable : NSObject
@end

@implementation RAREiScriptHandler_iScriptRunnable

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setHandleExceptionWithBoolean:", NULL, "V", 0x401, NULL },
    { "getResult", NULL, "LNSObject", 0x401, NULL },
    { "setCancelRunnerWithId:withBoolean:", NULL, "V", 0x401, NULL },
    { "setNotifierRunnerWithId:withBoolean:", NULL, "V", 0x401, NULL },
    { "getExecutionException", NULL, "LJavaLangThrowable", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "isDisposed", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiScriptHandler_iScriptRunnable = { "iScriptRunnable", "com.appnativa.rare.scripting", "iScriptHandler", 0x209, 7, methods, 0, NULL, 0, NULL};
  return &_RAREiScriptHandler_iScriptRunnable;
}

@end
