//
// Created by Don DeCoteau on 9/9/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>
#import <QuartzCore/CAAnimation.h>

@interface  CAAnimation (RAREUtils)
-(void) setDeceleration: (CGFloat)deceleration;
-(void) setAcceleration: (CGFloat)acceleration;

@end

@interface RAREAnimations : NSObject
+(void) shakeAnimation:(UIView*) view;
+(void) shakeAddRotateAnimation:(UIView*) view;
+(void) pullAnimation: (UIView*) view left: (BOOL) left;
+(void) slideAnimationLeftView: (UIView*) leftView rightView:(UIView*) rightView left: (BOOL) leftSlide;
+(void) slideAnimationTopView: (UIView*) topView bottomView:(UIView*) bottomView left: (BOOL) topSlide;

@end