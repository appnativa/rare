//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/iTransitionAnimator.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiTransitionAnimator_H_
#define _RAREiTransitionAnimator_H_

@class RAREUIRectangle;
@protocol RAREiFunctionCallback;
@protocol RAREiParentComponent;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"

@protocol RAREiTransitionAnimator < NSObject, JavaObject >
- (void)animateWithRAREiParentComponent:(id<RAREiParentComponent>)target
                    withRAREUIRectangle:(RAREUIRectangle *)bounds
              withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)cancel;
- (void)dispose;
- (void)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (id<RAREiPlatformAnimator>)getInAnimator;
- (id<RAREiPlatformAnimator>)getOutAnimator;
- (BOOL)isRunning;
- (BOOL)isAutoDispose;
- (void)setAutoDisposeWithBoolean:(BOOL)autoDispose;
- (void)setCallbackWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (void)setOutgoingPersistsWithBoolean:(BOOL)persists;
- (void)setOutgoingHiddenWithBoolean:(BOOL)hidden;
@end

#define ComAppnativaRareUiEffectsITransitionAnimator RAREiTransitionAnimator

#endif // _RAREiTransitionAnimator_H_
