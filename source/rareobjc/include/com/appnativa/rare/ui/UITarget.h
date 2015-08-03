//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/UITarget.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUITarget_H_
#define _RAREUITarget_H_

@class RARERenderTypeEnum;
@class RAREUIColor;
@class RAREUIDimension;
@protocol RAREiBackgroundPainter;
@protocol RAREiParentComponent;
@protocol RAREiPlatformAppContext;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiTarget_iListener;
@protocol RAREiTransitionAnimator;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/viewer/iTarget.h"

@interface RAREUITarget : NSObject < RAREiTarget > {
 @public
  BOOL partialOutAnimation_;
  id<RAREiTransitionAnimator> animator_;
  id<RAREiPlatformAppContext> appContext_;
  BOOL ignoreViewerRenderType_;
  id linkedData_;
  id<RAREiTarget_iListener> listener_;
  BOOL manualUpdate_;
  BOOL popupWindow_;
  id<RAREiParentComponent> targetContainer_;
  NSString *targetName_;
  id<RAREiViewer> theViewer_;
  id<RAREiTransitionAnimator> transitionAnimator_;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener;
- (void)activate;
- (void)disposeWithBoolean:(BOOL)disposeviewer;
- (void)reloadViewer;
- (id<RAREiViewer>)removeViewer;
- (void)repaint;
- (void)update;
- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)painter;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)painter;
- (void)setIgnoreViewerRenderTypeWithBoolean:(BOOL)ignoreViewerRenderType;
- (void)setLinkedDataWithId:(id)data;
- (void)setLockedWithBoolean:(BOOL)lock;
- (void)setLockedColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setManualUpdateWithBoolean:(BOOL)manual;
- (void)setNameWithNSString:(NSString *)name;
- (void)setPopupWindowWithBoolean:(BOOL)popupWindow;
- (BOOL)setRenderTypeWithRARERenderTypeEnum:(RARERenderTypeEnum *)renderType;
- (void)setTransitionAnimatorWithRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator;
- (id<RAREiViewer>)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer;
- (void)setViewerWithRAREiViewer:(id<RAREiViewer>)viewer
     withRAREiTransitionAnimator:(id<RAREiTransitionAnimator>)animator
       withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (id<RAREiParentComponent>)getContainerComponent;
- (id)getLinkedData;
- (NSString *)getName;
- (RARERenderTypeEnum *)getRenderType;
- (RAREUIDimension *)getTargetSize;
- (id<RAREiTransitionAnimator>)getTransitionAnimator;
- (id<RAREiViewer>)getViewer;
- (BOOL)isDisposed;
- (BOOL)isHeadless;
- (BOOL)isIgnoreViewerRenderType;
- (BOOL)isLocked;
- (BOOL)isManualUpdate;
- (BOOL)isPopupWindow;
- (BOOL)isVisible;
- (void)removeComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)removeViewerEx;
- (void)setComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiViewer>)setViewerExWithRAREiViewer:(id<RAREiViewer>)viewer;
- (BOOL)isChildOfTargetContainerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)copyAllFieldsTo:(RAREUITarget *)other;
@end

J2OBJC_FIELD_SETTER(RAREUITarget, animator_, id<RAREiTransitionAnimator>)
J2OBJC_FIELD_SETTER(RAREUITarget, appContext_, id<RAREiPlatformAppContext>)
J2OBJC_FIELD_SETTER(RAREUITarget, linkedData_, id)
J2OBJC_FIELD_SETTER(RAREUITarget, listener_, id<RAREiTarget_iListener>)
J2OBJC_FIELD_SETTER(RAREUITarget, targetContainer_, id<RAREiParentComponent>)
J2OBJC_FIELD_SETTER(RAREUITarget, targetName_, NSString *)
J2OBJC_FIELD_SETTER(RAREUITarget, theViewer_, id<RAREiViewer>)
J2OBJC_FIELD_SETTER(RAREUITarget, transitionAnimator_, id<RAREiTransitionAnimator>)

typedef RAREUITarget ComAppnativaRareUiUITarget;

@interface RAREUITarget_DelegatingTarget : RAREUITarget {
 @public
  id<RAREiParentComponent> delegateComponent_;
}

- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_;
- (id)initWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app
                         withNSString:(NSString *)name
             withRAREiParentComponent:(id<RAREiParentComponent>)container
                          withBoolean:(BOOL)register_
            withRAREiTarget_iListener:(id<RAREiTarget_iListener>)listener;
- (void)removeComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setComponentExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)copyAllFieldsTo:(RAREUITarget_DelegatingTarget *)other;
@end

J2OBJC_FIELD_SETTER(RAREUITarget_DelegatingTarget, delegateComponent_, id<RAREiParentComponent>)

@interface RAREUITarget_$1 : NSObject < RAREiFunctionCallback > {
 @public
  id<RAREiFunctionCallback> val$cb_;
  id<RAREiViewer> val$v_;
}

- (void)finishedWithBoolean:(BOOL)canceled
                     withId:(id)returnValue;
- (id)initWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$0
                    withRAREiViewer:(id<RAREiViewer>)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREUITarget_$1, val$cb_, id<RAREiFunctionCallback>)
J2OBJC_FIELD_SETTER(RAREUITarget_$1, val$v_, id<RAREiViewer>)

#endif // _RAREUITarget_H_
