//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/CompoundAnimation.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RARECompoundAnimation_H_
#define _RARECompoundAnimation_H_

@class IOSObjectArray;
@protocol JavaUtilList;
@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iAnimatorListener.h"

@interface RARECompoundAnimation : RAREaPlatformAnimator < RAREiAnimatorListener > {
 @public
  id<JavaUtilList> animations_;
  int runCount_;
  BOOL running_;
  BOOL serial_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)serial;
- (id)initWithBoolean:(BOOL)serial
withRAREiPlatformAnimatorArray:(IOSObjectArray *)animations;
- (void)addAnimationWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction;
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source;
- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source;
- (void)dispose;
- (void)removeAnimationWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)setSerialWithBoolean:(BOOL)serial;
- (id<JavaUtilList>)getAnimations;
- (BOOL)isRunning;
- (BOOL)isSerial;
- (void)copyAllFieldsTo:(RARECompoundAnimation *)other;
@end

J2OBJC_FIELD_SETTER(RARECompoundAnimation, animations_, id<JavaUtilList>)

typedef RARECompoundAnimation ComAppnativaRareUiEffectsCompoundAnimation;

#endif // _RARECompoundAnimation_H_
