//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/FadeAnimation.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/effects/FadeAnimation.h"

@implementation RAREFadeAnimation

- (id)init {
  return [super init];
}

- (void)startExWithId:(id)viewProxy {
  #if TARGET_OS_IPHONE
  UIView* view=(UIView*)viewProxy;
  #else
  NSView* view=(NSView*)viewProxy;
  #endif
  
  BOOL back=direction_==[RAREiAnimator_DirectionEnum BACKWARD];
  CGFloat start=startingAlpha_;
  CGFloat end=endingAlpha_;
  if(back) {
    start=endingAlpha_;
    end=startingAlpha_;
  }
  #if TARGET_OS_IPHONE
  UIViewAnimationOptions options=[self getIOSAnimationOptions];
  view.alpha=start;
  [self animationStartedWithRAREiPlatformAnimator: self];
  [UIView animateWithDuration:duration_/1000.0f delay:0 options:options animations:^{
    view.alpha=end;
  } completion:^(BOOL finished) {
    [self animationEndedWithRAREiPlatformAnimator: self];
  }];
  #else
  animation.fromValue=[NSValue valueWithPoint:sframe.origin];
  animation.toValue=[NSValue valueWithPoint:eframe.origin];
  [view.layer addAnimation:animation forKey:key_];
  #endif
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "startExWithId:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcClassInfo _RAREFadeAnimation = { "FadeAnimation", "com.appnativa.rare.ui.effects", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREFadeAnimation;
}

@end