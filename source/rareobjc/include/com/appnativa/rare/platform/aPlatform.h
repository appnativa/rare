//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/platform/aPlatform.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaPlatform_H_
#define _RAREaPlatform_H_

@class IOSClass;
@class JavaIoFile;
@class JavaLangThrowable;
@class JavaNetURL;
@class JavaUtilEventObject;
@class RAREActionLink;
@class RAREActionLink_ReturnDataTypeEnum;
@class RARESPOTWidget;
@class RAREUIProperties;
@class RAREValidator;
@class RAREWindowViewer;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiCancelable;
@protocol RAREiDataConverter;
@protocol RAREiFlavorCreator;
@protocol RAREiFunctionCallback;
@protocol RAREiFunctionHandler;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiScriptHandler;
@protocol RAREiTimer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iPlatform.h"
#include "java/lang/Runnable.h"

@interface RAREaPlatform : NSObject < RAREiPlatform > {
 @public
  RAREValidator *validator_;
  double javaVersion_;
}

- (void)addJarURLWithJavaNetURL:(JavaNetURL *)url;
- (JavaIoFile *)createCacheFileWithNSString:(NSString *)name;
- (void)debugLogWithNSString:(NSString *)msg;
- (BOOL)deleteDirectoryWithJavaIoFile:(JavaIoFile *)path;
- (BOOL)isDesktop;
- (void)errorMessageWithNSString:(NSString *)title
                    withNSString:(NSString *)message;
- (id)evaluateWithRAREiWidget:(id<RAREiWidget>)w
       withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                       withId:(id)code
                 withNSString:(NSString *)event
      withJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)executeWithRAREiWidget:(id<RAREiWidget>)w
        withRAREiScriptHandler:(id<RAREiScriptHandler>)sh
                        withId:(id)code
                  withNSString:(NSString *)event
       withJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)handlePlatformPropertiesWithRAREiWidget:(id<RAREiWidget>)widget
                             withRARESPOTWidget:(RARESPOTWidget *)cfg
                                withJavaUtilMap:(id<JavaUtilMap>)properties;
- (void)ignoreExceptionWithNSString:(NSString *)msg
              withJavaLangThrowable:(JavaLangThrowable *)throwable;
- (void)infoMessageWithNSString:(NSString *)title
                   withNSString:(NSString *)message;
- (void)infoMessageWithNSString:(NSString *)title
                   withNSString:(NSString *)message
      withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<JavaUtilMap>)loadResourceIconsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                withJavaUtilMap:(id<JavaUtilMap>)appIcons
                                             withRAREActionLink:(RAREActionLink *)link
                                                    withBoolean:(BOOL)clear
                                                    withBoolean:(BOOL)defaultDeferred;
- (id<JavaUtilMap>)loadResourceStringsWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                                                  withJavaUtilMap:(id<JavaUtilMap>)appStrings
                                               withRAREActionLink:(RAREActionLink *)link
                                                      withBoolean:(BOOL)clear;
- (void)loadUIPropertiesWithRAREiWidget:(id<RAREiWidget>)context
                     withRAREActionLink:(RAREActionLink *)link
                   withRAREUIProperties:(RAREUIProperties *)defs;
- (id)resolveUIPropertyWithRAREiWidget:(id<RAREiWidget>)context
                          withNSString:(NSString *)name
                          withNSString:(NSString *)propvalue;
- (id<RAREUTiCancelable>)sendFormDataWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREActionLink:(RAREActionLink *)link
                                     withJavaUtilMap:(id<JavaUtilMap>)data
                                         withBoolean:(BOOL)multipart
               withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                           withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)uploadDataWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
                                            withId:(id)data
                                      withNSString:(NSString *)name
                                      withNSString:(NSString *)mimeType
                                      withNSString:(NSString *)fileName
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<RAREUTiCancelable>)getContentWithRAREiWidget:(id<RAREiWidget>)context
                                withRAREActionLink:(RAREActionLink *)link
             withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type
                         withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<JavaUtilList>)getCookieList;
- (NSString *)getCookies;
- (double)getJavaVersion;
- (BOOL)isEmbedded;
- (BOOL)isInSandbox;
- (BOOL)isTouchDevice;
- (BOOL)isTouchableDevice;
- (BOOL)isUseFullScreen;
- (void)handleUIPropertyWithRAREiWidget:(id<RAREiWidget>)context
                   withRAREUIProperties:(RAREUIProperties *)defs
                           withNSString:(NSString *)property
                                 withId:(id)value;
- (id)getLinkContentWithRAREiWidget:(id<RAREiWidget>)w
                 withRAREActionLink:(RAREActionLink *)link
withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)type;
- (RAREValidator *)getValidator;
- (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)param0;
- (BOOL)canGenerateByteCode;
- (id)createChartHandler;
- (id)createObjectWithNSString:(NSString *)param0;
- (id<RAREiPlatformComponent>)createPlatformComponentWithId:(id)param0;
- (id<RAREiTimer>)createTimerWithNSString:(NSString *)param0;
- (id<RAREiPlatformComponent>)findPlatformComponentWithId:(id)param0;
- (id<RAREiWidget>)findWidgetForComponentWithId:(id)param0;
- (int)getAndroidVersion;
- (id<RAREiPlatformAppContext>)getAppContext;
- (int)getAppInstanceCount;
- (JavaIoFile *)getCacheDir;
- (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)param0;
- (IOSClass *)getDataConverterClassWithNSString:(NSString *)param0;
- (id<RAREiFunctionHandler>)getFunctionHandler;
- (NSString *)getOsType;
- (float)getOsVersion;
- (id<RAREiPlatformComponent>)getPlatformComponentWithId:(id)param0;
- (NSString *)getPlatformType;
- (double)getPlatformVersion;
- (id<RAREiFlavorCreator>)getTransferFlavorCreator;
- (NSString *)getUserAgent;
- (RAREWindowViewer *)getWindowViewerForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0;
- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)param0;
- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)param0
                                withInt:(int)param1;
- (BOOL)isAndroid;
- (BOOL)isDebugEnabled;
- (BOOL)isDebuggingEnabled;
- (BOOL)isDescendingFromWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)param1;
- (BOOL)isHTMLSupportedInLabels;
- (BOOL)isIOS;
- (BOOL)isInitialized;
- (BOOL)isJava;
- (BOOL)isJavaFX;
- (BOOL)isLinux;
- (BOOL)isMac;
- (BOOL)isSwing;
- (BOOL)isUIThread;
- (BOOL)isUnix;
- (BOOL)isWindows;
- (IOSClass *)loadClassWithNSString:(NSString *)param0;
- (BOOL)mailToWithNSString:(NSString *)param0;
- (BOOL)mailToWithNSString:(NSString *)param0
              withNSString:(NSString *)param1
              withNSString:(NSString *)param2;
- (void)registerWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0
                                     withRAREiWidget:(id<RAREiWidget>)param1;
- (void)setUseFullScreenWithBoolean:(BOOL)param0;
- (void)unregisterWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)param0;
- (id)init;
- (void)copyAllFieldsTo:(RAREaPlatform *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform, validator_, RAREValidator *)

typedef RAREaPlatform ComAppnativaRarePlatformAPlatform;

@interface RAREaPlatform_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform *this$0_;
  id<RAREiWidget> val$context_;
  RAREActionLink *val$link_;
  id<JavaUtilMap> val$data_;
  BOOL val$multipart_;
  RAREActionLink_ReturnDataTypeEnum *val$type_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREaPlatform:(RAREaPlatform *)outer$
            withRAREiWidget:(id<RAREiWidget>)capture$0
         withRAREActionLink:(RAREActionLink *)capture$1
            withJavaUtilMap:(id<JavaUtilMap>)capture$2
                withBoolean:(BOOL)capture$3
withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)capture$4
  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$5;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$1, this$0_, RAREaPlatform *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1, val$context_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1, val$link_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1, val$data_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1, val$type_, RAREActionLink_ReturnDataTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1, val$cb_, id<RAREiFunctionCallback>)

@interface RAREaPlatform_$1_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform_$1 *this$0_;
  BOOL val$b_;
  id val$r_;
}

- (void)run;
- (id)initWithRAREaPlatform_$1:(RAREaPlatform_$1 *)outer$
                   withBoolean:(BOOL)capture$0
                        withId:(id)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$1_$1, this$0_, RAREaPlatform_$1 *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$1_$1, val$r_, id)

@interface RAREaPlatform_$2 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform *this$0_;
  id<RAREiWidget> val$context_;
  RAREActionLink *val$link_;
  id val$data_;
  NSString *val$name_;
  NSString *val$mimeType_;
  NSString *val$fileName_;
  RAREActionLink_ReturnDataTypeEnum *val$type_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREaPlatform:(RAREaPlatform *)outer$
            withRAREiWidget:(id<RAREiWidget>)capture$0
         withRAREActionLink:(RAREActionLink *)capture$1
                     withId:(id)capture$2
               withNSString:(NSString *)capture$3
               withNSString:(NSString *)capture$4
               withNSString:(NSString *)capture$5
withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)capture$6
  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$7;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$2, this$0_, RAREaPlatform *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$context_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$link_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$data_, id)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$name_, NSString *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$mimeType_, NSString *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$fileName_, NSString *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$type_, RAREActionLink_ReturnDataTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2, val$cb_, id<RAREiFunctionCallback>)

@interface RAREaPlatform_$2_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform_$2 *this$0_;
  BOOL val$b_;
  id val$r_;
}

- (void)run;
- (id)initWithRAREaPlatform_$2:(RAREaPlatform_$2 *)outer$
                   withBoolean:(BOOL)capture$0
                        withId:(id)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$2_$1, this$0_, RAREaPlatform_$2 *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$2_$1, val$r_, id)

@interface RAREaPlatform_$3 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform *this$0_;
  id<RAREiWidget> val$context_;
  RAREActionLink *val$link_;
  RAREActionLink_ReturnDataTypeEnum *val$type_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREaPlatform:(RAREaPlatform *)outer$
            withRAREiWidget:(id<RAREiWidget>)capture$0
         withRAREActionLink:(RAREActionLink *)capture$1
withRAREActionLink_ReturnDataTypeEnum:(RAREActionLink_ReturnDataTypeEnum *)capture$2
  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$3;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$3, this$0_, RAREaPlatform *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$3, val$context_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREaPlatform_$3, val$link_, RAREActionLink *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$3, val$type_, RAREActionLink_ReturnDataTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$3, val$cb_, id<RAREiFunctionCallback>)

@interface RAREaPlatform_$3_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaPlatform_$3 *this$0_;
  BOOL val$b_;
  id val$r_;
}

- (void)run;
- (id)initWithRAREaPlatform_$3:(RAREaPlatform_$3 *)outer$
                   withBoolean:(BOOL)capture$0
                        withId:(id)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREaPlatform_$3_$1, this$0_, RAREaPlatform_$3 *)
J2OBJC_FIELD_SETTER(RAREaPlatform_$3_$1, val$r_, id)

#endif // _RAREaPlatform_H_