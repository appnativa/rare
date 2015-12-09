//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/Platform.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREPlatform_H_
#define _RAREPlatform_H_

@class IOSClass;
@class IOSObjectArray;
@class JavaIoFile;
@class JavaLangThrowable;
@class JavaNetURL;
@class JavaUtilEventObject;
@class RAREActionLink;
@class RAREActionLink_ReturnDataTypeEnum;
@class RAREFunctions;
@class RARESPOTWidget;
@class RAREUIProperties;
@class RAREWindowViewer;
@protocol JavaLangRunnable;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiCancelable;
@protocol RAREiDataConverter;
@protocol RAREiExceptionHandler;
@protocol RAREiFunctionCallback;
@protocol RAREiFunctionHandler;
@protocol RAREiPlatform;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiScriptHandler;
@protocol RAREiTimer;
@protocol RAREiURLConnection;
@protocol RAREiViewer;
@protocol RAREiWeakReference;
@protocol RAREiWidget;

#import "JreEmulation.h"

@interface RAREPlatform : NSObject {
}

+ (NSString *)RARE_PACKAGE_NAME;
+ (NSString *)RARE_SPOT_PACKAGE_NAME;
+ (id<RAREiPlatform>)platform;
+ (void)setPlatform:(id<RAREiPlatform>)platform;
+ (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url;
+ (void)clearSessionCookies;
+ (void)closeOpenConnectionsWithBoolean:(BOOL)log;
+ (void)setTrackOpenConnectionsWithBoolean:(BOOL)trackOpenConnections;
+ (IOSObjectArray *)getOpenConnections;
+ (JavaIoFile *)createCacheFileWithNSString:(NSString *)name;
+ (id)createObjectWithNSString:(NSString *)className_;
+ (id<RAREiPlatformComponent>)createPlatformComponentWithId:(id)nativeComponent;
+ (id<RAREiTimer>)createTimerWithNSString:(NSString *)name;
+ (id<RAREiWeakReference>)createWeakReferenceWithId:(id)value;
+ (void)debugLogWithNSString:(NSString *)msg;
+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
      withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
                 withNSString:(NSString *)event
      withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
       withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
                  withNSString:(NSString *)event
       withJavaUtilEventObject:(JavaUtilEventObject *)e;
+ (id<RAREiPlatformComponent>)findPlatformComponentWithId:(id)source;
+ (id<RAREiWidget>)findWidgetForComponentWithId:(id)c;
+ (void)handlePlatformPropertiesWithRAREiWidget:(id<RAREiWidget>)widget
                             withRARESPOTWidget:(RARESPOTWidget *)cfg
                                withJavaUtilMap:(id<JavaUtilMap>)properties;
+ (void)ignoreExceptionWithNSString:(NSString *)msg
              withJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r;
+ (void)runOnUIThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r;
+ (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r
                                withInt:(int)delay;
+ (IOSClass *)loadClassWithNSString:(NSString *)className_;
+ (id<JavaUtilMap>)loadResourceIconsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                withJavaUtilMap:(id<JavaUtilMap>)appIcons
                                             withRAREActionLink:(RAREActionLink *)link
                                                    withBoolean:(BOOL)clear
                                                    withBoolean:(BOOL)defaultDeferred;
+ (id<JavaUtilMap>)loadResourceStringsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                  withJavaUtilMap:(id<JavaUtilMap>)appStrings
                                               withRAREActionLink:(RAREActionLink *)link
                                                      withBoolean:(BOOL)clear;
+ (void)loadUIPropertiesWithRAREiWidget:(id<RAREiWidget>)context
                     withRAREActionLink:(RAREActionLink *)link
                   withRAREUIProperties:(RAREUIProperties *)defs;
+ (BOOL)mailToWithNSString:(NSString *)uri;
+ (BOOL)mailToWithNSString:(NSString *)address
              withNSString:(NSString *)subject
              withNSString:(NSString *)body;
+ (id<RAREiURLConnection>)openConnectionWithRAREiWidget:(id<RAREiWidget>)context
                                         withJavaNetURL:(JavaNetURL *)url
                                           withNSString:(NSString *)mimeType;
+ (void)registerWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                     withRAREiWidget:(id<RAREiWidget>)context;
+ (id<RAREUTiCancelable>)sendFormDataWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREActionLink:(RAREActionLink *)link
                                     withJavaUtilMap:(id<JavaUtilMap>)data
                                         withBoolean:(BOOL)multipart
               withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (void)unregisterWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
+ (id<RAREUTiCancelable>)uploadDataWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                                            withId:(id)data
                                      withNSString:(NSString *)name
                                      withNSString:(NSString *)mimeType
                                      withNSString:(NSString *)fileName
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (void)setCookie2ValueWithJavaNetURL:(JavaNetURL *)url
                         withNSString:(NSString *)value;
+ (void)setCookieValueWithJavaNetURL:(JavaNetURL *)url
                        withNSString:(NSString *)value;
+ (void)setGlobalVariableWithNSString:(NSString *)name
                               withId:(id)value;
+ (void)setPlatformWithRAREiPlatform:(id<RAREiPlatform>)p;
+ (int)getAndroidVersion;
+ (id<RAREiPlatformAppContext>)getAppContext;
+ (JavaIoFile *)getCacheDir;
+ (id<RAREUTiCancelable>)getContentWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (id<RAREiViewer>)getContextRootViewer;
+ (id<JavaUtilList>)getCookieList;
+ (NSString *)getCookies;
+ (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)cls;
+ (IOSClass *)getDataConverterClassWithNSString:(NSString *)name;
+ (id<RAREiExceptionHandler>)getDefaultExceptionHandlerWithRAREiWidget:(id<RAREiWidget>)context;
+ (id<RAREiFunctionHandler>)getFunctionHandler;
+ (RAREFunctions *)getGlobalFunctions;
+ (double)getJavaVersion;
+ (NSString *)getOsType;
+ (float)getOsVersion;
+ (id<RAREiPlatform>)getPlatform;
+ (id<RAREiPlatformComponent>)getPlatformComponentWithId:(id)source;
+ (NSString *)getPlatformType;
+ (double)getPlatformVersion;
+ (NSString *)getPropertyWithNSString:(NSString *)key;
+ (NSString *)getPropertyWithNSString:(NSString *)key
                         withNSString:(NSString *)defaultValue;
+ (void)setPropertyWithNSString:(NSString *)key
                   withNSString:(NSString *)value;
+ (id<RAREiPlatformIcon>)getResourceAsIconWithNSString:(NSString *)name;
+ (id<RAREiPlatformIcon>)getResourceAsIconExWithNSString:(NSString *)name;
+ (NSString *)getResourceAsStringWithNSString:(NSString *)name;
+ (double)getRuntimeVersion;
+ (NSString *)getSPOTNameWithIOSClass:(IOSClass *)cls;
+ (RAREUIProperties *)getUIDefaults;
+ (NSString *)getUserAgent;
+ (id<RAREiWidget>)getWidgetForComponentWithId:(id)c;
+ (RAREWindowViewer *)getWindowViewer;
+ (RAREWindowViewer *)getWindowViewerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREWindowViewer *)getWindowViewerWithRAREiWidget:(id<RAREiWidget>)w;
+ (BOOL)isAndroid;
+ (BOOL)isDebugEnabled;
+ (BOOL)isDebuggingEnabled;
+ (BOOL)isDescendingFromWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)container;
+ (BOOL)isEmbedded;
- (BOOL)isHTMLSupportedInLabels;
+ (BOOL)isIOS;
+ (BOOL)isInSandbox;
+ (BOOL)isInitialized;
+ (BOOL)isJava;
+ (BOOL)isJavaFX;
- (BOOL)isLandscapeOrientation;
+ (BOOL)isLinux;
+ (BOOL)isMac;
+ (BOOL)isShuttingDown;
+ (BOOL)isSwing;
+ (BOOL)isTouchDevice;
+ (BOOL)isTouchableDevice;
+ (BOOL)hasPhysicalKeyboard;
+ (BOOL)hasPointingDevice;
+ (BOOL)isUIThread;
+ (BOOL)isUnix;
+ (BOOL)isUseFullScreen;
+ (BOOL)isWindows;
+ (void)setUseFullScreenWithBoolean:(BOOL)use;
- (id)init;
@end

typedef RAREPlatform ComAppnativaRarePlatform;

#endif // _RAREPlatform_H_
