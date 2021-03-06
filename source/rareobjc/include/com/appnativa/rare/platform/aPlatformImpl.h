//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/aPlatformImpl.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaPlatformImpl_H_
#define _RAREaPlatformImpl_H_

@class IOSClass;
@class JavaIoFile;
@class JavaNetURL;
@class RAREUIFont;
@class RAREUTCharScanner;
@class RAREWindowViewer;
@class RAREaAppContextImpl;
@protocol JavaLangRunnable;
@protocol RAREUTObjectCache_iCacheReference;
@protocol RAREiDataConverter;
@protocol RAREiFunctionHandler;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiTimer;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/aPlatform.h"
#include "com/appnativa/util/ObjectCache.h"
#include "java/lang/ThreadLocal.h"

@interface RAREaPlatformImpl : RAREaPlatform {
 @public
  JavaIoFile *cacheDir_;
  NSString *platformName_;
  RAREaAppContextImpl *appContext_;
  int menubarHeight_;
  BOOL platformIsIOS_;
  BOOL platformIsIPad_;
  BOOL platformIsMAC_;
  float platformVersion_;
  NSString *platformVersionString_;
}

+ (JavaLangThreadLocal *)perThreadScanner;
+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner;
- (id)initWithRAREaAppContextImpl:(RAREaAppContextImpl *)context;
- (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url;
- (BOOL)canGenerateByteCode;
- (id)createChartHandler;
- (BOOL)createDirectoryWithJavaIoFile:(JavaIoFile *)file;
- (id<RAREiPlatformComponent>)createErrorComponentWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                           withNSString:(NSString *)message;
- (id<RAREiPlatformComponent>)createPlatformComponentWithId:(id)nativeComponent;
- (id<RAREiTimer>)createTimerWithNSString:(NSString *)name;
+ (void)defaultFontUpdatedWithRAREUIFont:(RAREUIFont *)font;
- (BOOL)deleteDirectoryWithJavaIoFile:(JavaIoFile *)path;
- (id<RAREiPlatformComponent>)findPlatformComponentWithId:(id)o;
- (id<RAREiWidget>)findWidgetForComponentWithId:(id)o;
- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r;
- (void)invokeLaterWithJavaLangRunnable:(id<JavaLangRunnable>)r
                                withInt:(int)delay;
- (IOSClass *)loadClassWithNSString:(NSString *)name;
- (BOOL)mailToWithNSString:(NSString *)uri;
- (BOOL)mailToWithNSString:(NSString *)address
              withNSString:(NSString *)subject
              withNSString:(NSString *)body;
- (void)registerWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                                     withRAREiWidget:(id<RAREiWidget>)context;
- (void)unregisterWithWidgetWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component;
- (int)getAndroidVersion;
- (id<RAREiPlatformAppContext>)getAppContext;
- (int)getAppInstanceCount;
- (JavaIoFile *)getCacheDir;
- (id<RAREiDataConverter>)getDataConverterWithIOSClass:(IOSClass *)cls;
- (IOSClass *)getDataConverterClassWithNSString:(NSString *)name;
- (id<RAREiFunctionHandler>)getFunctionHandler;
- (NSString *)getOsType;
- (float)getOsVersion;
- (id<RAREiPlatformComponent>)getPlatformComponentWithId:(id)source;
- (NSString *)getPlatformType;
- (double)getPlatformVersion;
- (RAREWindowViewer *)getWindowViewerForComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (BOOL)isAndroid;
- (BOOL)isDebugEnabled;
- (BOOL)isDebuggingEnabled;
- (BOOL)isDescendingFromWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)container;
- (BOOL)isHTMLSupportedInLabels;
- (BOOL)isIOS;
- (BOOL)isIPad;
- (BOOL)isIPhone;
- (BOOL)isInitialized;
- (BOOL)isJava;
- (BOOL)isJavaFX;
- (BOOL)isLinux;
- (BOOL)isMac;
- (BOOL)isShuttingDown;
- (BOOL)isSwing;
- (BOOL)isTouchDevice;
- (BOOL)isTouchableDevice;
- (BOOL)isUIThread;
- (BOOL)isUnix;
- (BOOL)isWindows;
- (BOOL)createDirectoryWithNSString:(NSString *)path;
+ (BOOL)deleteDirectoryWithNSString:(NSString *)dir;
- (NSString *)getCacheDirName;
- (void)getPlatformInfo;
- (void)copyAllFieldsTo:(RAREaPlatformImpl *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformImpl, cacheDir_, JavaIoFile *)
J2OBJC_FIELD_SETTER(RAREaPlatformImpl, platformName_, NSString *)
J2OBJC_FIELD_SETTER(RAREaPlatformImpl, appContext_, RAREaAppContextImpl *)
J2OBJC_FIELD_SETTER(RAREaPlatformImpl, platformVersionString_, NSString *)

typedef RAREaPlatformImpl ComAppnativaRarePlatformAPlatformImpl;

@interface RAREaPlatformImpl_$1 : JavaLangThreadLocal {
}

- (RAREUTCharScanner *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREaPlatformImpl_$2 : NSObject < RAREUTObjectCache_iCacheReferenceCreator > {
}

- (id<RAREUTObjectCache_iCacheReference>)createCacheReferenceWithId:(id)key
                                                             withId:(id)value;
- (id)init;
@end

#endif // _RAREaPlatformImpl_H_
