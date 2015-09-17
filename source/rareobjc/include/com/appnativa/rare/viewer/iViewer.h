//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/iViewer.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiViewer_H_
#define _RAREiViewer_H_

@class JavaIoWriter;
@class JavaNetURL;
@class RAREActionLink;
@class RARERenderTypeEnum;
@class RARESPOTViewer;
@class RAREWindowViewer;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiAnimator;
@protocol RAREiCollapsible;
@protocol RAREiExpandedListener;
@protocol RAREiPageSetup;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPopup;
@protocol RAREiTarget;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Enum.h"

@protocol RAREiViewer < RAREiWidget, NSObject, JavaObject >
- (void)setLoadAnimatorWithRAREiAnimator:(id<RAREiAnimator>)wa;
- (BOOL)canPrint;
- (BOOL)canSave;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (id<RAREiPageSetup>)createPageSetup;
- (void)downArrow;
- (void)leftArrow;
- (void)onConfigurationChangedWithBoolean:(BOOL)reset;
- (void)onConfigurationWillChangeWithId:(id)newConfig;
- (void)pageDown;
- (void)pageEnd;
- (void)pageHome;
- (void)pageEndHorizontal;
- (void)pageHomeHorizontal;
- (void)pageLeft;
- (void)pageRight;
- (void)pageSetupWithRAREiPageSetup:(id<RAREiPageSetup>)ps;
- (void)pageUp;
- (void)register__;
- (id)registerNamedItemWithNSString:(NSString *)name
                             withId:(id)object;
- (void)rightArrow;
- (void)saveWithJavaIoWriter:(JavaIoWriter *)w;
- (RAREWindowViewer *)showAsDialogWithJavaUtilMap:(id<JavaUtilMap>)winoptions;
- (RAREWindowViewer *)showAsDialogWithNSString:(NSString *)title
                                   withBoolean:(BOOL)modal;
- (id<RAREiPopup>)showAsPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                        withJavaUtilMap:(id<JavaUtilMap>)options;
- (RAREWindowViewer *)showAsWindowWithJavaUtilMap:(id<JavaUtilMap>)winoptions;
- (void)targetAcquiredWithRAREiTarget:(id<RAREiTarget>)target;
- (void)targetLostWithRAREiTarget:(id<RAREiTarget>)target;
- (void)unregisterWithBoolean:(BOOL)disposing;
- (id)unregisterNamedItemWithNSString:(NSString *)name;
- (void)upArrow;
- (void)setAppContextWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)context;
- (void)setAutoDisposeWithBoolean:(BOOL)autoDispose;
- (void)setAutoUnregisterWithBoolean:(BOOL)autoUnregister;
- (void)setCollapsedTitleWithNSString:(NSString *)title;
- (void)setContextURLWithJavaNetURL:(JavaNetURL *)url;
- (void)setDisposableWithBoolean:(BOOL)disposable;
- (void)setViewerActionLinkWithRAREActionLink:(RAREActionLink *)link;
- (JavaNetURL *)getBaseURL;
- (NSString *)getCollapsedTitle;
- (id<RAREiCollapsible>)getCollapsiblePane;
- (JavaNetURL *)getContextURL;
- (id<RAREiExpandedListener>)getExpandedListener;
- (id)getNamedItemWithNSString:(NSString *)name;
- (id<JavaUtilList>)getNames;
- (id<RAREiPlatformComponent>)getPrintComponent;
- (JavaNetURL *)getSourceURL;
- (id<RAREiTarget>)getTarget;
- (RAREActionLink *)getViewerActionLink;
- (JavaNetURL *)getViewerURL;
- (BOOL)isAutoDispose;
- (BOOL)isAutoUnregister;
- (BOOL)isBackPressedHandled;
- (BOOL)isDisposable;
- (BOOL)isDisposed;
- (BOOL)isExternalViewer;
- (BOOL)isRegistered;
- (BOOL)isTabPaneViewer;
- (BOOL)isWindowOnlyViewer;
- (RARERenderTypeEnum *)getRenderType;
- (void)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)rt;
@end

#define ComAppnativaRareViewerIViewer RAREiViewer

typedef enum {
  RAREiViewer_DisableBehavior_DISABLE_WIDGETS = 0,
  RAREiViewer_DisableBehavior_DISABLE_CONTAINER = 1,
  RAREiViewer_DisableBehavior_DISABLE_BOTH = 2,
} RAREiViewer_DisableBehavior;

@interface RAREiViewer_DisableBehaviorEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_WIDGETS;
+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_CONTAINER;
+ (RAREiViewer_DisableBehaviorEnum *)DISABLE_BOTH;
+ (IOSObjectArray *)values;
+ (RAREiViewer_DisableBehaviorEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREiViewer_H_
