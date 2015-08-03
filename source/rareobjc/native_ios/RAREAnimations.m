//
// Created by Don DeCoteau on 9/9/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RAREAnimations.h"

@implementation CAAnimation (RAREAnimation)
- (void)setDeceleration:(CGFloat)deceleration {

}

- (void)setAcceleration:(CGFloat)acceleration {

}

@end

@implementation RAREAnimations {

}
+ (void)shakeAnimation:(UIView *)view {
  CABasicAnimation *animation = [CABasicAnimation animationWithKeyPath:@"position"];
  [animation setDuration:0.07];
  [animation setRepeatCount:4];
  [animation setAutoreverses:YES];
  [animation setFromValue:[NSValue valueWithCGPoint:
      CGPointMake([view center].x - 10.0f, [view center].y)]];
  [animation setToValue:[NSValue valueWithCGPoint:
      CGPointMake([view center].x + 10.0f, [view center].y)]];
  [view.layer addAnimation:animation forKey:@"shakeAnimation"];
}

+ (void)shakeAddRotateAnimation:(UIView *)view {
  CAKeyframeAnimation *shakeAnimation = [CAKeyframeAnimation animationWithKeyPath:@"transform.rotation.z"];
  NSArray *transformValues = [NSArray arrayWithObjects:
      [NSNumber numberWithFloat:((M_PI) / 64)],
      [NSNumber numberWithFloat:(-((M_PI) / 64))],
      [NSNumber numberWithFloat:((M_PI) / 64)],
      [NSNumber numberWithFloat:(-((M_PI) / 64))],
      [NSNumber numberWithFloat:((M_PI) / 64)],
      [NSNumber numberWithFloat:(-((M_PI) / 64))],
      [NSNumber numberWithFloat:0],
      nil];

  [shakeAnimation setValues:transformValues];

  NSArray *times = [NSArray arrayWithObjects:
      [NSNumber numberWithFloat:0.14f],
      [NSNumber numberWithFloat:0.28f],
      [NSNumber numberWithFloat:0.42f],
      [NSNumber numberWithFloat:0.57f],
      [NSNumber numberWithFloat:0.71f],
      [NSNumber numberWithFloat:0.85f],
      [NSNumber numberWithFloat:1.0f],
      nil];

  [shakeAnimation setKeyTimes:times];

  shakeAnimation.fillMode = kCAFillModeForwards;
  shakeAnimation.removedOnCompletion = NO;
  shakeAnimation.duration = 0.6f;
  [view.layer addAnimation:shakeAnimation forKey:@"shakeAddRotateAnimation"];
}

+ (void)pullAnimation:(UIView *)view left:(BOOL)left {

}

+ (void)slideAnimationLeftView:(UIView *)leftView rightView:(UIView *)rightView left:(BOOL)leftSlide {

}

+ (void)slideAnimationTopView:(UIView *)topView bottomView:(UIView *)bottomView left:(BOOL)topSlide {

}

@end