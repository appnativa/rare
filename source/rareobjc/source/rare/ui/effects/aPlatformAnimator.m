//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/aPlatformAnimator.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#import <QuartzCore/QuartzCore.h>
#import "RAREAnimations.h"
#import "java/lang/Boolean.h"

@implementation RAREaPlatformAnimator

- (id)init {
  if (self = [super init]) {
    duration_ = 250;
  }
  return self;
}

- (id)initWithId:(id)animation {
  if (self = [super init]) {
    duration_ = 250;
    proxy_ = animation;
  }
  return self;
}

- (id)initWithNSString:(NSString *)key {
  if (self = [super init]) {
    duration_ = 250;
    proxy_ = [RAREaPlatformAnimator createBasicProxyWithNSString:key];
    self->key_ = key;
  }
  return self;
}

- (void)animateWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                   withId:(id)postAnimateAction {
  self->postAnimateAction_ = postAnimateAction;
  self->component_ = c;
  canceled_ = NO;
  [self startExWithId:[((id<RAREiPlatformComponent>) nil_chk(c)) getProxy]];
}

- (void)cancel {
  canceled_ = YES;
  [self stop];
}

- (void)dispose {
  if (component_ != nil) {
    [self cancel];
  }
  [super dispose];
  component_ = nil;
  proxy_ = nil;
}

- (void)stop {
  if(component_ ) {
    #if TARGET_OS_IPHONE
    UIView* view=(UIView*)[component_ getProxy];
    #else
    NSView* view=(NSView*)[component_ getProxy];
    #endif
    if(key_) {
      [view.layer removeAnimationForKey:key_];
    }
    else {
      CALayer *layer = view.layer;
      [layer removeAllAnimations];
      for (CALayer* sublayer in layer.sublayers) {
        [sublayer removeAllAnimations];
      }
    }
    component_=nil;
    [self animationEndedWithRAREiPlatformAnimator:self];
  }
}

- (void)setAccelerationWithFloat:(float)acceleration {
  self->acceleration_ = acceleration;
}

- (void)setAutoReverseWithBoolean:(BOOL)autoReverse {
  self->autoReverse_ = autoReverse;
}

- (void)setDecelerationWithFloat:(float)deceleration {
  self->deceleration_ = deceleration;
}

- (void)setDurationWithInt:(int)duration {
  self->duration_ = duration;
}

- (void)setRepeatCountWithInt:(int)repeatCount {
  self->repeatCount_ = repeatCount;
}

- (id)getProxy {
  return proxy_;
}

- (BOOL)isRunning {
  return component_ != nil;
}

- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator {
  if (component_ != nil) {
    [self setAnimatingPropertyWithRAREiPlatformComponent:component_ withBoolean:NO];
  }
  [self notifyListenersWithRAREiPlatformAnimator:animator withBoolean:YES];
  component_ = nil;
}

- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator {
  [self notifyListenersWithRAREiPlatformAnimator:animator withBoolean:NO];
}

+ (id)createBasicProxyWithNSString:(NSString *)key {
  return [CABasicAnimation animationWithKeyPath: key];
}

+ (id)createTransitionProxy {
  CATransition* ta=[CATransition animation];
  ta.startProgress = 0;
  ta.endProgress = 1.0;
  return ta;
}

- (void)startExWithId:(id)viewProxy {
  
  if(proxy_) {
    [self setValuesWithId: proxy_];
    #if TARGET_OS_IPHONE
    UIView* view=(UIView*)viewProxy;
    #else
    NSView* view=(NSView*)viewProxy;
    #endif
    CAAnimation *animation=(CAAnimation*)proxy_;
    [view.layer addAnimation:animation forKey: key_];
  }
}

- (void)setDelegate {
  if(proxy_) {
    CAAnimation *animation=(CAAnimation*)proxy_;
    animation.delegate=self;
  }
}

- (void)animationDidStart:(CAAnimation *)theAnimation {
  [self animationStartedWithRAREiPlatformAnimator:self];
}
- (void)animationDidStop:(CAAnimation *)theAnimation finished:(BOOL)flag {
  [self animationEndedWithRAREiPlatformAnimator:self];
}

- (int)getIOSAnimationOptions {
  UIViewAnimationOptions options=0;
  if(deceleration_>0 && acceleration_>0) {
    options|=UIViewAnimationOptionCurveEaseInOut;
  }
  else if(acceleration_>0) {
    options|=UIViewAnimationOptionCurveEaseIn;
  }
  else {
    options|=UIViewAnimationOptionCurveEaseOut;
  }
  if(autoReverse_) {
    options|=UIViewAnimationOptionAutoreverse;
  }
  if(repeatCount_<0) {
    options|=UIViewAnimationOptionRepeat;
  }
  return options;
}

- (void)setValuesWithId:(id)proxy {
  CAAnimation *animation=(CAAnimation*)proxy;
  if(repeatCount_>1) {
    [animation setRepeatCount: repeatCount_];
  }
  if(deceleration_>0) {
    [animation setDeceleration: deceleration_];
  }
  if(acceleration_>0) {
    [animation setAcceleration: acceleration_];
  }
  [animation setAutoreverses: autoReverse_];
  [animation setDuration: (double)duration_/1000.0];
  
}

- (void)copyAllFieldsTo:(RAREaPlatformAnimator *)other {
  [super copyAllFieldsTo:other];
  other->acceleration_ = acceleration_;
  other->autoReverse_ = autoReverse_;
  other->canceled_ = canceled_;
  other->component_ = component_;
  other->deceleration_ = deceleration_;
  other->duration_ = duration_;
  other->key_ = key_;
  other->proxy_ = proxy_;
  other->repeatCount_ = repeatCount_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "stop", NULL, "V", 0x101, NULL },
    { "getProxy", NULL, "LNSObject", 0x1, NULL },
    { "isRunning", NULL, "Z", 0x1, NULL },
    { "animationEndedWithRAREiPlatformAnimator:", NULL, "V", 0x4, NULL },
    { "animationStartedWithRAREiPlatformAnimator:", NULL, "V", 0x4, NULL },
    { "createBasicProxyWithNSString:", NULL, "LNSObject", 0x10c, NULL },
    { "createTransitionProxy", NULL, "LNSObject", 0x10c, NULL },
    { "startExWithId:", NULL, "V", 0x104, NULL },
    { "setDelegate", NULL, "V", 0x104, NULL },
    { "getIOSAnimationOptions", NULL, "I", 0x104, NULL },
    { "setValuesWithId:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "duration_", NULL, 0x4, "I" },
    { "acceleration_", NULL, 0x4, "F" },
    { "autoReverse_", NULL, 0x4, "Z" },
    { "canceled_", NULL, 0x4, "Z" },
    { "component_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "deceleration_", NULL, 0x4, "F" },
    { "key_", NULL, 0x4, "LNSString" },
    { "proxy_", NULL, 0x4, "LNSObject" },
    { "repeatCount_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREaPlatformAnimator = { "aPlatformAnimator", "com.appnativa.rare.ui.effects", NULL, 0x401, 11, methods, 9, fields, 0, NULL};
  return &_RAREaPlatformAnimator;
}

@end
