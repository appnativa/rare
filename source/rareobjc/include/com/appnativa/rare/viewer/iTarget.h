//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/iTarget.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiTarget_H_
#define _RAREiTarget_H_

@class RARERenderTypeEnum;
@class RAREUIDimension;
@protocol RAREiBackgroundPainter;
@protocol RAREiParentComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiTransitionAnimator;
@protocol RAREiViewer;

#import "JreEmulation.h"

@protocol RAREiTarget < NSObject, JavaObject >
- (void)activate;
- (void)disposeWithBoolean:(BOOL)disposeviewer;
- (void)reloadViewer;
- (id<RAREiViewer>)removeViewer;
- (void)setLinkedDataWithId:(id)data;
- (id<RAREiViewer>)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (id<RAREiParentComponent>)getContainerComponent;
- (id)getLinkedData;
- (NSString *)getName;
- (RAREUIDimension *)getTargetSize;
- (id<RAREiViewer>)getViewer;
- (BOOL)isHeadless;
- (BOOL)isPopupWindow;
- (BOOL)isVisible;
- (void)repaint;
- (void)update;
- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)painter;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)painter;
- (void)setLockedWithBoolean:(BOOL)lock;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (BOOL)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType;
- (RARERenderTypeEnum *)getRenderType;
- (BOOL)isLocked;
@end

@interface RAREiTarget : NSObject {
}
+ (NSString *)TARGET_MENUBAR;
+ (NSString *)TARGET_NEW_POPUP;
+ (NSString *)TARGET_NEW_WINDOW;
+ (NSString *)TARGET_NULL;
+ (NSString *)TARGET_PARENT;
+ (NSString *)TARGET_SELF;
+ (NSString *)TARGET_TOOLBAR;
+ (NSString *)TARGET_WORKSPACE;
@end

#define ComAppnativaRareViewerITarget RAREiTarget

@protocol RAREiTarget_iListener < NSObject, JavaObject >
- (void)viewerRemovedWithRAREiViewer:(id<RAREiViewer>)v;
- (void)viewerSetWithRAREiViewer:(id<RAREiViewer>)v;
@end

#endif // _RAREiTarget_H_
