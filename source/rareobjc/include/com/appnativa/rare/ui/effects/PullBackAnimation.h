//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/PullBackAnimation.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREPullBackAnimation_H_
#define _RAREPullBackAnimation_H_

@protocol RAREiPlatformAnimator;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/effects/aPullBackAnimation.h"
#include "com/appnativa/rare/ui/effects/iAnimatorListener.h"

@interface RAREPullBackAnimation : RAREaPullBackAnimation < RAREiAnimatorListener > {
}

- (id)init;
- (id)initWithBoolean:(BOOL)horizontal;
- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)fromLeftOrTop;
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
@end

typedef RAREPullBackAnimation ComAppnativaRareUiEffectsPullBackAnimation;

#endif // _RAREPullBackAnimation_H_
