//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/effects/iAnimatorListener.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiAnimatorListener_H_
#define _RAREiAnimatorListener_H_

@protocol RAREiPlatformAnimator;

#import "JreEmulation.h"

@protocol RAREiAnimatorListener < NSObject, JavaObject >
- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator;
@end

#define ComAppnativaRareUiEffectsIAnimatorListener RAREiAnimatorListener

#endif // _RAREiAnimatorListener_H_
