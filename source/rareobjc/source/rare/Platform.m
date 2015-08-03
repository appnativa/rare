//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/Platform.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/converters/iDataConverter.h"
#include "com/appnativa/rare/iAppContext.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iExceptionHandler.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/iFunctionHandler.h"
#include "com/appnativa/rare/iPlatform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/iTimer.h"
#include "com/appnativa/rare/iWeakReference.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/JavaURLConnection.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/scripting/Functions.h"
#include "com/appnativa/rare/scripting/iScriptHandler.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/io/File.h"
#include "java/io/IOException.h"
#include "java/lang/ClassNotFoundException.h"
#include "java/lang/Exception.h"
#include "java/lang/InternalError.h"
#include "java/lang/Runnable.h"
#include "java/lang/System.h"
#include "java/lang/Throwable.h"
#include "java/net/URL.h"
#include "java/util/EventObject.h"
#include "java/util/List.h"
#include "java/util/Map.h"

@implementation RAREPlatform

static NSString * RAREPlatform_RARE_PACKAGE_NAME_ = @"com.appnativa.rare";
static NSString * RAREPlatform_RARE_SPOT_PACKAGE_NAME_ = @"com.appnativa.rare.spot";
static id<RAREiPlatform> RAREPlatform_platform_;
static NSString * RAREPlatform_defaultPackage_;

+ (NSString *)RARE_PACKAGE_NAME {
  return RAREPlatform_RARE_PACKAGE_NAME_;
}

+ (NSString *)RARE_SPOT_PACKAGE_NAME {
  return RAREPlatform_RARE_SPOT_PACKAGE_NAME_;
}

+ (id<RAREiPlatform>)platform {
  return RAREPlatform_platform_;
}

+ (void)setPlatform:(id<RAREiPlatform>)platform {
  RAREPlatform_platform_ = platform;
}

+ (NSString *)defaultPackage {
  return RAREPlatform_defaultPackage_;
}

+ (void)setDefaultPackage:(NSString *)defaultPackage {
  RAREPlatform_defaultPackage_ = defaultPackage;
}

+ (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) browseURLWithJavaNetURL:url];
}

+ (void)clearSessionCookies {
  [RAREPlatformHelper clearSessionCookies];
}

+ (void)closeOpenConnectionsWithBoolean:(BOOL)log {
  [RAREJavaURLConnection closeOpenConnectionsWithBoolean:log];
}

+ (void)setTrackOpenConnectionsWithBoolean:(BOOL)trackOpenConnections {
  [RAREJavaURLConnection setTrackOpenConnectionsWithBoolean:trackOpenConnections];
}

+ (IOSObjectArray *)getOpenConnections {
  return [RAREJavaURLConnection getOpenConnections];
}

+ (JavaIoFile *)createCacheFileWithNSString:(NSString *)name {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) createCacheFileWithNSString:name];
}

+ (id)createObjectWithNSString:(NSString *)className_ {
  if ((className_ != nil) && ([className_ indexOf:'.'] == -1)) {
    RAREPlatform_defaultPackage_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getStringWithNSString:@"Rare.class.defaultPackage"];
    if (RAREPlatform_defaultPackage_ != nil) {
      className_ = [NSString stringWithFormat:@"%@.%@", RAREPlatform_defaultPackage_, className_];
    }
  }
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) createObjectWithNSString:className_];
}

+ (id<RAREiPlatformComponent>)createPlatformComponentWithId:(id)nativeComponent {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) createPlatformComponentWithId:nativeComponent];
}

+ (id<RAREiTimer>)createTimerWithNSString:(NSString *)name {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) createTimerWithNSString:name];
}

+ (id<RAREiWeakReference>)createWeakReferenceWithId:(id)value {
  return [RAREaPlatformHelper createWeakReferenceWithId:value];
}

+ (void)debugLogWithNSString:(NSString *)msg {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) debugLogWithNSString:msg];
}

+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
      withJavaUtilEventObject:(JavaUtilEventObject *)e {
  return [RAREPlatform evaluateWithRAREiWidget:w withRAREiScriptHandler:sh withId:code withNSString:nil withJavaUtilEventObject:e];
}

+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
                 withNSString:(NSString *)event
      withJavaUtilEventObject:(JavaUtilEventObject *)e {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) evaluateWithRAREiWidget:w withRAREiScriptHandler:sh withId:code withNSString:event withJavaUtilEventObject:e];
}

+ (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
       withJavaUtilEventObject:(JavaUtilEventObject *)e {
  [RAREPlatform executeWithRAREiWidget:w withRAREiScriptHandler:sh withId:code withNSString:nil withJavaUtilEventObject:e];
}

+ (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
                  withNSString:(NSString *)event
       withJavaUtilEventObject:(JavaUtilEventObject *)e {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) executeWithRAREiWidget:w withRAREiScriptHandler:sh withId:code withNSString:event withJavaUtilEventObject:e];
}

+ (id<RAREiPlatformComponent>)findPlatformComponentWithId:(id)source {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) findPlatformComponentWithId:source];
}

+ (id<RAREiWidget>)findWidgetForComponentWithId:(id)c {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) findWidgetForComponentWithId:c];
}

+ (void)handlePlatformPropertiesWithRAREiWidget:(id<RAREiWidget>)widget
                             withRARESPOTWidget:(RARESPOTWidget *)cfg
                                withJavaUtilMap:(id<JavaUtilMap>)properties {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) handlePlatformPropertiesWithRAREiWidget:widget withRARESPOTWidget:cfg withJavaUtilMap:properties];
}

+ (void)ignoreExceptionWithNSString:(NSString *)msg
              withJavaLangThrowable:(JavaLangThrowable *)throwable {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) ignoreExceptionWithNSString:msg withJavaLangThrowable:throwable];
}

+ (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) invokeLaterWithJavaLangRunnable:r];
}

+ (void)runOnUIThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r {
  if ([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isUIThread]) {
    [((id<JavaLangRunnable>) nil_chk(r)) run];
  }
  else {
    [RAREPlatform_platform_ invokeLaterWithJavaLangRunnable:r];
  }
}

+ (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r
                                withInt:(int)delay {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) invokeLaterWithJavaLangRunnable:r withInt:delay];
}

+ (IOSClass *)loadClassWithNSString:(NSString *)className_ {
  if ((className_ != nil) && ([className_ indexOf:'.'] == -1)) {
    RAREPlatform_defaultPackage_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getStringWithNSString:@"Rare.class.defaultPackage"];
    if (RAREPlatform_defaultPackage_ != nil) {
      className_ = [NSString stringWithFormat:@"%@.%@", RAREPlatform_defaultPackage_, className_];
    }
  }
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) loadClassWithNSString:className_];
}

+ (id<JavaUtilMap>)loadResourceIconsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                withJavaUtilMap:(id<JavaUtilMap>)appIcons
                                             withRAREActionLink:(RAREActionLink *)link
                                                    withBoolean:(BOOL)clear
                                                    withBoolean:(BOOL)defaultDeferred {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) loadResourceIconsWithRAREiPlatformAppContext:app withJavaUtilMap:appIcons withRAREActionLink:link withBoolean:clear withBoolean:defaultDeferred];
}

+ (id<JavaUtilMap>)loadResourceStringsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                  withJavaUtilMap:(id<JavaUtilMap>)appStrings
                                               withRAREActionLink:(RAREActionLink *)link
                                                      withBoolean:(BOOL)clear {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) loadResourceStringsWithRAREiPlatformAppContext:app withJavaUtilMap:appStrings withRAREActionLink:link withBoolean:clear];
}

+ (void)loadUIPropertiesWithRAREiWidget:(id<RAREiWidget>)context
                     withRAREActionLink:(RAREActionLink *)link
                   withRAREUIProperties:(RAREUIProperties *)defs {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) loadUIPropertiesWithRAREiWidget:context withRAREActionLink:link withRAREUIProperties:defs];
}

+ (BOOL)mailToWithNSString:(NSString *)uri {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) mailToWithNSString:uri];
}

+ (BOOL)mailToWithNSString:(NSString *)address
              withNSString:(NSString *)subject
              withNSString:(NSString *)body {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) mailToWithNSString:address withNSString:subject withNSString:body];
}

+ (id<RAREiURLConnection>)openConnectionWithRAREiWidget:(id<RAREiWidget>)context
                                         withJavaNetURL:(JavaNetURL *)url
                                           withNSString:(NSString *)mimeType {
  if ((url != nil) && [RAREPlatform isUIThread] && [((NSString *) nil_chk([url getProtocol])) hasPrefix:@"http"]) {
    [RAREPlatform debugLogWithNSString:[NSString stringWithFormat:@"Network IO via UI Thread:%@", [url description]]];
  }
  if (context != nil) {
    return [((id<RAREiPlatformAppContext>) nil_chk([context getAppContext])) openConnectionWithJavaNetURL:url withNSString:mimeType];
  }
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) openConnectionWithJavaNetURL:url withNSString:mimeType];
}

+ (void)registerWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                     withRAREiWidget:(id<RAREiWidget>)context {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) registerWithWidgetWithRAREiPlatformComponent:component withRAREiWidget:context];
}

+ (id<RAREUTiCancelable>)sendFormDataWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREActionLink:(RAREActionLink *)link
                                     withJavaUtilMap:(id<JavaUtilMap>)data
                                         withBoolean:(BOOL)multipart
               withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) sendFormDataWithRAREiWidget:context withRAREActionLink:link withJavaUtilMap:data withBoolean:multipart withRAREActionLink_ReturnDataTypeEnum:type withRAREiFunctionCallback:cb];
}

+ (void)unregisterWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) unregisterWithWidgetWithRAREiPlatformComponent:component];
}

+ (id<RAREUTiCancelable>)uploadDataWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                                            withId:(id)data
                                      withNSString:(NSString *)name
                                      withNSString:(NSString *)mimeType
                                      withNSString:(NSString *)fileName
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) uploadDataWithRAREiWidget:context withRAREActionLink:link withId:data withNSString:name withNSString:mimeType withNSString:fileName withRAREActionLink_ReturnDataTypeEnum:type withRAREiFunctionCallback:cb];
}

+ (void)setCookie2ValueWithJavaNetURL:(JavaNetURL *)url
                         withNSString:(NSString *)value {
  [RAREaPlatformHelper setCookieWithNSString:@"Cookie2" withJavaNetURL:url withNSString:value];
}

+ (void)setCookieValueWithJavaNetURL:(JavaNetURL *)url
                        withNSString:(NSString *)value {
  [RAREaPlatformHelper setCookieWithNSString:@"Cookie" withJavaNetURL:url withNSString:value];
}

+ (void)setGlobalVariableWithNSString:(NSString *)name
                               withId:(id)value {
  [((id<RAREiScriptHandler>) nil_chk([((id<RAREiContainer>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getRootViewer])) getScriptHandler])) setGlobalVariableWithNSString:name withId:value];
}

+ (void)setPlatformWithRAREiPlatform:(id<RAREiPlatform>)p {
  if (RAREPlatform_platform_ != nil) {
    @throw [[JavaLangInternalError alloc] initWithNSString:@"Cannot change platform"];
  }
  RAREPlatform_platform_ = p;
}

+ (int)getAndroidVersion {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAndroidVersion];
}

+ (id<RAREiPlatformAppContext>)getAppContext {
  return (RAREPlatform_platform_ == nil) ? nil : [RAREPlatform_platform_ getAppContext];
}

+ (JavaIoFile *)getCacheDir {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getCacheDir];
}

+ (id<RAREUTiCancelable>)getContentWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getContentWithRAREiWidget:context withRAREActionLink:link withRAREActionLink_ReturnDataTypeEnum:type withRAREiFunctionCallback:cb];
}

+ (id<RAREiViewer>)getContextRootViewer {
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getRootViewer];
}

+ (id<JavaUtilList>)getCookieList {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getCookieList];
}

+ (NSString *)getCookies {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getCookies];
}

+ (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)cls {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getDataConverterWithIOSClass:cls];
}

+ (IOSClass *)getDataConverterClassWithNSString:(NSString *)name {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getDataConverterClassWithNSString:name];
}

+ (id<RAREiExceptionHandler>)getDefaultExceptionHandlerWithRAREiWidget:(id<RAREiWidget>)context {
  return (context == nil) ? [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getDefaultExceptionHandler] : [((id<RAREiPlatformAppContext>) nil_chk([context getAppContext])) getDefaultExceptionHandler];
}

+ (id<RAREiFunctionHandler>)getFunctionHandler {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getFunctionHandler];
}

+ (RAREFunctions *)getGlobalFunctions {
  return [((id<RAREiFunctionHandler>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getFunctionHandler])) getFunctions];
}

+ (double)getJavaVersion {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getJavaVersion];
}

+ (NSString *)getOsType {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getOsType];
}

+ (float)getOsVersion {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getOsVersion];
}

+ (id<RAREiPlatform>)getPlatform {
  return RAREPlatform_platform_;
}

+ (id<RAREiPlatformComponent>)getPlatformComponentWithId:(id)source {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getPlatformComponentWithId:source];
}

+ (NSString *)getPlatformType {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getPlatformType];
}

+ (double)getPlatformVersion {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getPlatformVersion];
}

+ (NSString *)getPropertyWithNSString:(NSString *)key {
  @try {
    return [JavaLangSystem getPropertyWithNSString:key withNSString:nil];
  }
  @catch (JavaLangException *e) {
    return nil;
  }
}

+ (NSString *)getPropertyWithNSString:(NSString *)key
                         withNSString:(NSString *)defaultValue {
  @try {
    return [JavaLangSystem getPropertyWithNSString:key withNSString:defaultValue];
  }
  @catch (JavaLangException *e) {
    return defaultValue;
  }
}

+ (void)setPropertyWithNSString:(NSString *)key
                   withNSString:(NSString *)value {
  @try {
    [JavaLangSystem setPropertyWithNSString:key withNSString:value];
  }
  @catch (JavaLangException *e) {
  }
}

+ (id<RAREiPlatformIcon>)getResourceAsIconWithNSString:(NSString *)name {
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getResourceAsIconWithNSString:name];
}

+ (id<RAREiPlatformIcon>)getResourceAsIconExWithNSString:(NSString *)name {
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getResourceAsIconExWithNSString:name];
}

+ (NSString *)getResourceAsStringWithNSString:(NSString *)name {
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getResourceAsStringWithNSString:name];
}

+ (double)getRuntimeVersion {
  return RAREiConstants_APPLICATION_VERSION;
}

+ (NSString *)getSPOTNameWithIOSClass:(IOSClass *)cls {
  return [((IOSClass *) nil_chk(cls)) getName];
}

+ (RAREUIProperties *)getUIDefaults {
  id<RAREiPlatformAppContext> app = (RAREPlatform_platform_ == nil) ? nil : [RAREPlatform_platform_ getAppContext];
  if (app == nil) {
    return [[RAREUIProperties alloc] init];
  }
  return [app getUIDefaults];
}

+ (NSString *)getUserAgent {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getUserAgent];
}

+ (id<RAREiWidget>)getWidgetForComponentWithId:(id)c {
  id<RAREiPlatformComponent> pc = [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getPlatformComponentWithId:c];
  return (pc == nil) ? nil : [pc getWidget];
}

+ (RAREWindowViewer *)getWindowViewer {
  return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getTopWindowViewer];
}

+ (RAREWindowViewer *)getWindowViewerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getWindowViewerForComponentWithRAREiPlatformComponent:c];
}

+ (RAREWindowViewer *)getWindowViewerWithRAREiWidget:(id<RAREiWidget>)w {
  if (w == nil) {
    return [((id<RAREiPlatformAppContext>) nil_chk([((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getAppContext])) getWindowViewer];
  }
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) getWindowViewerForComponentWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk(w)) getContainerComponent]];
}

+ (BOOL)isAndroid {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isAndroid];
}

+ (BOOL)isDebugEnabled {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isDebugEnabled];
}

+ (BOOL)isDebuggingEnabled {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isDebuggingEnabled];
}

+ (BOOL)isDescendingFromWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)container {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isDescendingFromWithRAREiPlatformComponent:c withRAREiPlatformComponent:container];
}

+ (BOOL)isEmbedded {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isEmbedded];
}

- (BOOL)isHTMLSupportedInLabels {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isHTMLSupportedInLabels];
}

+ (BOOL)isIOS {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isIOS];
}

+ (BOOL)isInSandbox {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isInSandbox];
}

+ (BOOL)isInitialized {
  return (RAREPlatform_platform_ != nil) && [RAREPlatform_platform_ isInitialized];
}

+ (BOOL)isJava {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isJava];
}

+ (BOOL)isJavaFX {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isJavaFX];
}

- (BOOL)isLandscapeOrientation {
  return [RAREScreenUtils isWider];
}

+ (BOOL)isLinux {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isLinux];
}

+ (BOOL)isMac {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isMac];
}

+ (BOOL)isShuttingDown {
  id<RAREiAppContext> ap = (RAREPlatform_platform_ == nil) ? nil : [RAREPlatform_platform_ getAppContext];
  return (ap == nil) ? YES : [ap isShuttingDown];
}

+ (BOOL)isSwing {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isSwing];
}

+ (BOOL)isTouchDevice {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isTouchDevice];
}

+ (BOOL)isTouchableDevice {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isTouchableDevice];
}

+ (BOOL)isUIThread {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isUIThread];
}

+ (BOOL)isUnix {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isUnix];
}

+ (BOOL)isUseFullScreen {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isUseFullScreen];
}

+ (BOOL)isWindows {
  return [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) isWindows];
}

+ (void)setUseFullScreenWithBoolean:(BOOL)use {
  [((id<RAREiPlatform>) nil_chk(RAREPlatform_platform_)) setUseFullScreenWithBoolean:use];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "browseURLWithJavaNetURL:", NULL, "Z", 0x9, NULL },
    { "getOpenConnections", NULL, "LIOSObjectArray", 0x9, NULL },
    { "createCacheFileWithNSString:", NULL, "LJavaIoFile", 0x9, NULL },
    { "createObjectWithNSString:", NULL, "LNSObject", 0x9, NULL },
    { "createPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x9, NULL },
    { "createTimerWithNSString:", NULL, "LRAREiTimer", 0x9, NULL },
    { "createWeakReferenceWithId:", NULL, "LRAREiWeakReference", 0x9, NULL },
    { "evaluateWithRAREiWidget:withRAREiScriptHandler:withId:withJavaUtilEventObject:", NULL, "LNSObject", 0x9, NULL },
    { "evaluateWithRAREiWidget:withRAREiScriptHandler:withId:withNSString:withJavaUtilEventObject:", NULL, "LNSObject", 0x9, NULL },
    { "findPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x9, NULL },
    { "findWidgetForComponentWithId:", NULL, "LRAREiWidget", 0x9, NULL },
    { "loadClassWithNSString:", NULL, "LIOSClass", 0x9, "JavaLangClassNotFoundException" },
    { "loadResourceIconsWithRAREiPlatformAppContext:withJavaUtilMap:withRAREActionLink:withBoolean:withBoolean:", NULL, "LJavaUtilMap", 0x9, "JavaIoIOException" },
    { "loadResourceStringsWithRAREiPlatformAppContext:withJavaUtilMap:withRAREActionLink:withBoolean:", NULL, "LJavaUtilMap", 0x9, "JavaIoIOException" },
    { "loadUIPropertiesWithRAREiWidget:withRAREActionLink:withRAREUIProperties:", NULL, "V", 0x9, "JavaIoIOException" },
    { "mailToWithNSString:", NULL, "Z", 0x9, NULL },
    { "mailToWithNSString:withNSString:withNSString:", NULL, "Z", 0x9, NULL },
    { "openConnectionWithRAREiWidget:withJavaNetURL:withNSString:", NULL, "LRAREiURLConnection", 0x9, "JavaIoIOException" },
    { "sendFormDataWithRAREiWidget:withRAREActionLink:withJavaUtilMap:withBoolean:withRAREActionLink_ReturnDataTypeEnum:withRAREiFunctionCallback:", NULL, "LRAREUTiCancelable", 0x9, NULL },
    { "uploadDataWithRAREiWidget:withRAREActionLink:withId:withNSString:withNSString:withNSString:withRAREActionLink_ReturnDataTypeEnum:withRAREiFunctionCallback:", NULL, "LRAREUTiCancelable", 0x9, NULL },
    { "getAppContext", NULL, "LRAREiPlatformAppContext", 0x9, NULL },
    { "getCacheDir", NULL, "LJavaIoFile", 0x9, NULL },
    { "getContentWithRAREiWidget:withRAREActionLink:withRAREActionLink_ReturnDataTypeEnum:withRAREiFunctionCallback:", NULL, "LRAREUTiCancelable", 0x9, NULL },
    { "getContextRootViewer", NULL, "LRAREiViewer", 0x9, NULL },
    { "getCookieList", NULL, "LJavaUtilList", 0x9, NULL },
    { "getCookies", NULL, "LNSString", 0x9, NULL },
    { "getDataConverterWithIOSClass:", NULL, "LRAREiDataConverter", 0x9, NULL },
    { "getDataConverterClassWithNSString:", NULL, "LIOSClass", 0x9, "JavaLangClassNotFoundException" },
    { "getDefaultExceptionHandlerWithRAREiWidget:", NULL, "LRAREiExceptionHandler", 0x9, NULL },
    { "getFunctionHandler", NULL, "LRAREiFunctionHandler", 0x9, NULL },
    { "getGlobalFunctions", NULL, "LRAREFunctions", 0x9, NULL },
    { "getOsType", NULL, "LNSString", 0x9, NULL },
    { "getPlatform", NULL, "LRAREiPlatform", 0x9, NULL },
    { "getPlatformComponentWithId:", NULL, "LRAREiPlatformComponent", 0x9, NULL },
    { "getPlatformType", NULL, "LNSString", 0x9, NULL },
    { "getPropertyWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "getPropertyWithNSString:withNSString:", NULL, "LNSString", 0x9, NULL },
    { "getResourceAsIconWithNSString:", NULL, "LRAREiPlatformIcon", 0x9, NULL },
    { "getResourceAsIconExWithNSString:", NULL, "LRAREiPlatformIcon", 0x9, NULL },
    { "getResourceAsStringWithNSString:", NULL, "LNSString", 0x9, NULL },
    { "getSPOTNameWithIOSClass:", NULL, "LNSString", 0x9, NULL },
    { "getUIDefaults", NULL, "LRAREUIProperties", 0x9, NULL },
    { "getUserAgent", NULL, "LNSString", 0x9, NULL },
    { "getWidgetForComponentWithId:", NULL, "LRAREiWidget", 0x9, NULL },
    { "getWindowViewer", NULL, "LRAREWindowViewer", 0x9, NULL },
    { "getWindowViewerWithRAREiPlatformComponent:", NULL, "LRAREWindowViewer", 0x9, NULL },
    { "getWindowViewerWithRAREiWidget:", NULL, "LRAREWindowViewer", 0x9, NULL },
    { "isAndroid", NULL, "Z", 0x9, NULL },
    { "isDebugEnabled", NULL, "Z", 0x9, NULL },
    { "isDebuggingEnabled", NULL, "Z", 0x9, NULL },
    { "isDescendingFromWithRAREiPlatformComponent:withRAREiPlatformComponent:", NULL, "Z", 0x9, NULL },
    { "isEmbedded", NULL, "Z", 0x9, NULL },
    { "isHTMLSupportedInLabels", NULL, "Z", 0x1, NULL },
    { "isIOS", NULL, "Z", 0x9, NULL },
    { "isInSandbox", NULL, "Z", 0x9, NULL },
    { "isInitialized", NULL, "Z", 0x9, NULL },
    { "isJava", NULL, "Z", 0x9, NULL },
    { "isJavaFX", NULL, "Z", 0x9, NULL },
    { "isLandscapeOrientation", NULL, "Z", 0x1, NULL },
    { "isLinux", NULL, "Z", 0x9, NULL },
    { "isMac", NULL, "Z", 0x9, NULL },
    { "isShuttingDown", NULL, "Z", 0x9, NULL },
    { "isSwing", NULL, "Z", 0x9, NULL },
    { "isTouchDevice", NULL, "Z", 0x9, NULL },
    { "isTouchableDevice", NULL, "Z", 0x9, NULL },
    { "isUIThread", NULL, "Z", 0x9, NULL },
    { "isUnix", NULL, "Z", 0x9, NULL },
    { "isUseFullScreen", NULL, "Z", 0x9, NULL },
    { "isWindows", NULL, "Z", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "RARE_PACKAGE_NAME_", NULL, 0x19, "LNSString" },
    { "RARE_SPOT_PACKAGE_NAME_", NULL, 0x19, "LNSString" },
    { "platform_", NULL, 0xa, "LRAREiPlatform" },
    { "defaultPackage_", NULL, 0xa, "LNSString" },
  };
  static J2ObjcClassInfo _RAREPlatform = { "Platform", "com.appnativa.rare", NULL, 0x1, 69, methods, 4, fields, 0, NULL};
  return &_RAREPlatform;
}

@end