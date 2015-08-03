//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/aAppContextImpl.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaAppContextImpl_H_
#define _RAREaAppContextImpl_H_

@class JavaLangBoolean;
@class JavaLangThrowable;
@class JavaNetURL;
@class JavaUtilHashMap;
@class JavaUtilLoggingLogger;
@class RAREErrorInformation;
@class RAREUIImage;
@class RAREUIImageIcon;
@class RAREUIProperties;
@class RAREWindowViewer;
@class RAREaRare;
@protocol RAREiFunctionHandler;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentFactory;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/aAppContext.h"

@interface RAREaAppContextImpl : RAREaAppContext {
 @public
  JavaUtilHashMap *interfaceProxies_;
  JavaUtilLoggingLogger *logger_;
  BOOL useIOSResourceNamingConvention_;
}

- (id)initWithRAREaRare:(RAREaRare *)instance;
- (BOOL)isPopupWindowShowing;
- (BOOL)isDialogWindowShowing;
- (BOOL)browseURLWithJavaNetURL:(JavaNetURL *)url;
- (void)clearStatusBar;
- (void)closePopupWindowsWithBoolean:(BOOL)all;
+ (void)debugLogWithNSString:(NSString *)msg;
- (void)dispose;
+ (void)ignoreExceptionWithNSString:(NSString *)msg
              withJavaLangThrowable:(JavaLangThrowable *)throwable;
- (BOOL)islandscapeMode;
- (void)lockOrientationWithJavaLangBoolean:(JavaLangBoolean *)landscape;
- (JavaNetURL *)makeResourcePathWithNSString:(NSString *)file;
- (NSString *)makeResourcePathWithNSString:(NSString *)name
                              withNSString:(NSString *)ext;
+ (NSString *)makeResourcePathWithNSString:(NSString *)dir
                              withNSString:(NSString *)name
                              withNSString:(NSString *)ext;
- (JavaNetURL *)makeResourceURLWithNSString:(NSString *)name
                               withNSString:(NSString *)ext;
- (void)oneLineErrorMessageWithNSString:(NSString *)title
                           withNSString:(NSString *)msg;
- (void)registerInterfaceProxyWithNSString:(NSString *)interfaceName
                              withNSString:(NSString *)proxyClassName;
- (void)unlockOrientation;
- (void)setLoggerWithJavaUtilLoggingLogger:(JavaUtilLoggingLogger *)l;
+ (RAREaAppContextImpl *)getAppContextWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREaAppContextImpl *)getAppContextWithRAREiWidget:(id<RAREiWidget>)w;
- (NSString *)getApplicationName;
- (id<RAREiPlatformComponentFactory>)getComponentCreator;
- (RAREErrorInformation *)getErrorInformationWithId:(id)nserror;
- (id<RAREiFunctionHandler>)getFunctionHandler;
- (NSString *)getInterfaceProxyClassNameWithNSString:(NSString *)interfaceName;
- (id)getInterfaceProxyObjectWithNSString:(NSString *)interfaceName;
- (JavaUtilLoggingLogger *)getLogger;
- (RAREUIImage *)getManagedResourceWithNSString:(NSString *)name;
- (RAREUIImageIcon *)getResourceAsIconExWithNSString:(NSString *)name;
- (RAREUIImage *)getResourceAsImageWithNSString:(NSString *)name;
- (NSString *)getResourceAsStringWithNSString:(NSString *)name;
- (JavaNetURL *)getResourceURLWithNSString:(NSString *)path;
+ (RAREUIProperties *)getUIDefaultsWithRAREiWidget:(id<RAREiWidget>)w;
- (RAREWindowViewer *)getWindowViewer;
- (BOOL)isInitialized;
- (void)setFocusOwnerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setPermanentFocusOwnerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)setupUIDefaults;
- (NSString *)getDefaultManagedResourcePath;
- (id<RAREiPlatformAnimator>)getResourceAsAnimatorExWithNSString:(NSString *)animator;
- (BOOL)hasResourceDirectoryWithNSString:(NSString *)path;
- (void)copyAllFieldsTo:(RAREaAppContextImpl *)other;
@end

J2OBJC_FIELD_SETTER(RAREaAppContextImpl, interfaceProxies_, JavaUtilHashMap *)
J2OBJC_FIELD_SETTER(RAREaAppContextImpl, logger_, JavaUtilLoggingLogger *)

typedef RAREaAppContextImpl ComAppnativaRarePlatformAppleAAppContextImpl;

#endif // _RAREaAppContextImpl_H_
