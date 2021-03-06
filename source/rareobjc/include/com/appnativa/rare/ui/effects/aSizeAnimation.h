//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/aSizeAnimation.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaSizeAnimation_H_
#define _RAREaSizeAnimation_H_

@class RAREAnimationComponent;
@class RARELocationEnum;
@class RARERenderTypeEnum;
@class RAREUIImage;
@class RAREUIRectangle;
@class RAREaAnimator_BoundsChanger;
@class RAREiTransitionSupport_TransitionTypeEnum;
@protocol JavaUtilMap;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iTransitionSupport.h"

@interface RAREaSizeAnimation : RAREaPlatformAnimator < RAREiTransitionSupport > {
 @public
  BOOL horizontal_;
  float percent_;
  RARERenderTypeEnum *diagonalAnchor_;
  BOOL diagonal_;
  RAREAnimationComponent *animationComponent_;
  float height_;
  __weak id<RAREiPlatformComponent> inComponent_;
  float originalSize_;
  __weak id<RAREiPlatformComponent> outComponent_;
  float width_;
  RAREaAnimator_BoundsChanger *boundsChanger_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)horizontal;
- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)diagonal
withRARERenderTypeEnum:(RARERenderTypeEnum *)anchor;
+ (RARERenderTypeEnum *)getComplementarySideAnchorWithRARERenderTypeEnum:(RARERenderTypeEnum *)anchor
                                                    withRARELocationEnum:(RARELocationEnum *)parentLocation;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction;
- (void)animateToWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRAREUIRectangle:(RAREUIRectangle *)to;
- (void)dispose;
- (void)handlePropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)reset;
- (void)setDiagonalWithBoolean:(BOOL)diagonal;
- (void)setDiagonalAnchorWithId:(id)anchor;
- (void)setFadeInWithBoolean:(BOOL)fade;
- (void)setFadeOutWithBoolean:(BOOL)fade;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent;
- (void)setOutgoingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)outComponent
                                       withRAREUIImage:(RAREUIImage *)outImage;
- (void)setPercentWithFloat:(float)percent;
- (void)setTransitionTypeWithRAREiTransitionSupport_TransitionTypeEnum:(RAREiTransitionSupport_TransitionTypeEnum *)type;
- (RARERenderTypeEnum *)getDiagonalAnchor;
- (RAREiTransitionSupport_TransitionTypeEnum *)getTransitionType;
- (BOOL)isDiagonal;
- (BOOL)isHorizontal;
- (BOOL)isSizingAnimation;
- (void)clear;
+ (void)updateBoundsForAnchorWithRAREaAnimator_BoundsChanger:(RAREaAnimator_BoundsChanger *)bc
                                      withRARERenderTypeEnum:(RARERenderTypeEnum *)da;
- (void)updateWithFloat:(float)fraction;
- (void)updateComponentWithFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height
                     withBoolean:(BOOL)small
                       withFloat:(float)fraction;
- (BOOL)isComponentDisposed;
- (void)setSizingValuesWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)copyAllFieldsTo:(RAREaSizeAnimation *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSizeAnimation, diagonalAnchor_, RARERenderTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaSizeAnimation, animationComponent_, RAREAnimationComponent *)
J2OBJC_FIELD_SETTER(RAREaSizeAnimation, boundsChanger_, RAREaAnimator_BoundsChanger *)

typedef RAREaSizeAnimation ComAppnativaRareUiEffectsASizeAnimation;

#endif // _RAREaSizeAnimation_H_
