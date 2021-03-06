//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aFadeAnimation.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaFadeAnimation_H_
#define _RAREaFadeAnimation_H_

@class RAREUIImage;
@class RAREiTransitionSupport_TransitionTypeEnum;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"

@interface RAREaFadeAnimation : RAREaPlatformAnimator < RAREiTransitionSupport > {
 @public
  id<RAREiPlatformComponent> inComponent_;
  id<RAREiPlatformComponent> outComponent_;
  float startingAlpha_;
  float endingAlpha_;
}

- (id)init;
- (void)setFadeInWithBoolean:(BOOL)fade;
- (void)setFadeOutWithBoolean:(BOOL)fade;
- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage;
- (void)setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type;
- (RAREiTransitionSupport_TransitionTypeEnum *)getTransitionType;
- (void)clear;
- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)updateWithFloat:(float)value;
- (BOOL)isComponentDisposed;
- (void)restoreComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (float)getStartingAlpha;
- (void)setStartingAlphaWithFloat:(float)alpha;
- (float)getEndingAlpha;
- (void)setEndingAlphaWithFloat:(float)endingAlpha;
- (void)copyAllFieldsTo:(RAREaFadeAnimation *)other;
@end

J2OBJC_FIELD_SETTER(RAREaFadeAnimation, inComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaFadeAnimation, outComponent_, id<RAREiPlatformComponent>)

typedef RAREaFadeAnimation ComAppnativaRareUiEffectsAFadeAnimation;

#endif // _RAREaFadeAnimation_H_
