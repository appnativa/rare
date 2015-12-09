//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aSlideAnimation.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREaSlideAnimation_H_
#define _RAREaSlideAnimation_H_

@class RAREUIImage;
@class RAREaAnimator_BoundsChanger;
@class RAREiTransitionSupport_TransitionTypeEnum;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"

@interface RAREaSlideAnimation : RAREaPlatformAnimator < RAREiTransitionSupport > {
 @public
  BOOL horizontal_;
  float percent_;
  __weak id<RAREiPlatformComponent> outComponent_;
  RAREaAnimator_BoundsChanger *locationChanger_;
  id<RAREiPlatformComponent> inComponent_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)horizontal;
- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)fromLeftOrTop;
- (void)clear;
- (void)animateToWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                  withFloat:(float)x
                                  withFloat:(float)y;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction;
- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)setFadeInWithBoolean:(BOOL)fade;
- (void)setFadeOutWithBoolean:(BOOL)fade;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage;
- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent;
- (void)setPercentWithFloat:(float)percent;
- (void)setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type;
- (RAREiTransitionSupport_TransitionTypeEnum *)getTransitionType;
- (BOOL)isHorizontal;
- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)restoreComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (BOOL)isComponentDisposed;
- (void)updateWithFloat:(float)fraction;
- (void)copyAllFieldsTo:(RAREaSlideAnimation *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSlideAnimation, locationChanger_, RAREaAnimator_BoundsChanger *)
J2OBJC_FIELD_SETTER(RAREaSlideAnimation, inComponent_, id<RAREiPlatformComponent>)

typedef RAREaSlideAnimation ComAppnativaRareUiEffectsASlideAnimation;

#endif // _RAREaSlideAnimation_H_
