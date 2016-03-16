//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/aPlatformAnimator.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaPlatformAnimator_H_
#define _RAREaPlatformAnimator_H_

@protocol RAREiPlatformAnimator;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"

@interface RAREaPlatformAnimator : RAREaAnimator {
 @public
  int duration_;
  float acceleration_;
  BOOL autoReverse_;
  BOOL canceled_;
  __weak id<RAREiPlatformComponent> component_;
  float deceleration_;
  NSString *key_;
  id proxy_;
  int repeatCount_;
}

- (id)init;
- (id)initWithId:(id)animation;
- (id)initWithNSString:(NSString *)key;
- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction;
- (void)cancel;
- (void)dispose;
- (void)stop;
- (void)setAccelerationWithFloat:(float)acceleration;
- (void)setAutoReverseWithBoolean:(BOOL)autoReverse;
- (void)setDecelerationWithFloat:(float)deceleration;
- (void)setDurationWithInt:(int)duration;
- (void)setRepeatCountWithInt:(int)repeatCount;
- (id)getProxy;
- (BOOL)isRunning;
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
+ (id)createBasicProxyWithNSString:(NSString *)key;
+ (id)createTransitionProxy;
- (void)startExWithId:(id)viewProxy;
- (void)setDelegate;
- (int)getIOSAnimationOptions;
- (void)setValuesWithId:(id)proxy;
- (void)copyAllFieldsTo:(RAREaPlatformAnimator *)other;
@end

J2OBJC_FIELD_SETTER(RAREaPlatformAnimator, key_, NSString *)
J2OBJC_FIELD_SETTER(RAREaPlatformAnimator, proxy_, id)

typedef RAREaPlatformAnimator ComAppnativaRareUiEffectsAPlatformAnimator;

#endif // _RAREaPlatformAnimator_H_
