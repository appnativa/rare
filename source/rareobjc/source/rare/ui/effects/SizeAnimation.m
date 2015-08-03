//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/SizeAnimation.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/effects/SizeAnimation.h"
#include "com/appnativa/rare/ui/effects/aAnimator.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/aSizeAnimation.h"
#include "com/appnativa/rare/ui/effects/iAnimator.h"
#include "com/appnativa/rare/ui/effects/iPlatformAnimator.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#import "AppleHelper.h"
#import "APView+Component.h"

@implementation RARESizeAnimation

- (id)init {
  return [super init];
}

- (id)initWithBoolean:(BOOL)horizontal {
  return [super initWithBoolean:horizontal];
}

- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)diagonal
withRARERenderTypeEnum:(RARERenderTypeEnum *)anchor {
  return [super initWithBoolean:horizontal withBoolean:diagonal withRARERenderTypeEnum:anchor];
}

- (id<RAREiPlatformComponent>)setIncommingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)inComponent {
  self->inComponent_ = inComponent;
  return inComponent;
}

- (void)startExWithId:(id)viewProxy {
  if ([self isComponentDisposed]) {
    [self cancel];
    return;
  }
  id otherView = nil;
  float otherAlpha = 0;
  BOOL small = direction_ == [RAREiAnimator_DirectionEnum BACKWARD];
  if (component_ != nil) {
    [self setAnimatingPropertyWithRAREiPlatformComponent:component_ withBoolean:YES];
  }
  BOOL fade = (fadeIn_ && !small) || (fadeOut_ && small);
  if (inComponent_ != nil) {
    if (small) {
      viewProxy = [((id<RAREiPlatformComponent>) nil_chk(outComponent_)) getProxy];
      if (fadeIn_) {
        otherView = [inComponent_ getProxy];
        otherAlpha = 1;
      }
    }
    else {
      viewProxy = [inComponent_ getProxy];
      if (fadeOut_) {
        otherView = [((id<RAREiPlatformComponent>) nil_chk(outComponent_)) getProxy];
        otherAlpha = 0;
      }
    }
  }
  [self startExWithId:viewProxy withRAREUIRectangle:((RAREaAnimator_BoundsChanger *) nil_chk(boundsChanger_))->from_ withRAREUIRectangle:boundsChanger_->to_ withFloat:fade ? (small ? 0 : 1) : -1];
  if (otherView != nil) {
    [self fadeViewWithId:otherView withFloat:otherAlpha];
  }
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
    options|=UIViewAnimationOptionCurveLinear;
  }
  if(autoReverse_) {
    options|=UIViewAnimationOptionAutoreverse;
  }
  if(repeatCount_<0) {
    options|=UIViewAnimationOptionRepeat;
  }
  return options;
}

- (void)startExWithId:(id)viewProxy
  withRAREUIRectangle:(RAREUIRectangle *)start
  withRAREUIRectangle:(RAREUIRectangle *)end
            withFloat:(float)alpha {
  #if TARGET_OS_IPHONE
  UIView* view=(UIView*)viewProxy;
  CGRect srect=[start toRect];
  CGRect frame=[end toRect];
  if (view.window && [UIScreen osVersionAsFloat]<8 && view.window.rootViewController.view==view) {
    [view.window.screen adjustRectForOrientation: srect];
    [view.window.screen adjustRectForOrientation: frame];
  }
  [view.superview bringSubviewToFront:view];
  view.frame=srect;
  UIViewAnimationOptions options=[self getIOSAnimationOptions];
  [self animationStartedWithRAREiPlatformAnimator: self];
  if(alpha>-1) {
    view.alpha=1.0f-alpha;
    [UIView animateWithDuration:duration_/1000.0f delay:0 options:options animations:^{
      view.frame=frame;
      view.alpha=alpha;
    } completion:^(BOOL finished) {
      [self animationEndedWithRAREiPlatformAnimator: self];
    }];
  }
  else {
    [UIView animateWithDuration:duration_/1000.0f delay:0 options:options animations:^{
      view.frame=frame;
    } completion:^(BOOL finished) {
      [self animationEndedWithRAREiPlatformAnimator: self];
    }];
  }
  #else
  #endif
}

- (void)fadeViewWithId:(id)viewProxy
             withFloat:(float)endAlpha {
  #if TARGET_OS_IPHONE
  UIView* view=(UIView*)viewProxy;
  view.alpha=1.0f-endAlpha;
  #else
  NSView* view=(NSView*)viewProxy;
  #endif
  #if TARGET_OS_IPHONE
  UIViewAnimationOptions options=[self getIOSAnimationOptions];
  [self animationStartedWithRAREiPlatformAnimator: self];
  [UIView animateWithDuration:duration_/1000.0f delay:0 options:options animations:^{
    view.alpha=endAlpha;
  } completion: nil];
  #else
  #endif
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setIncommingComponentWithRAREiPlatformComponent:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "startExWithId:", NULL, "V", 0x4, NULL },
    { "getIOSAnimationOptions", NULL, "I", 0x104, NULL },
    { "startExWithId:withRAREUIRectangle:withRAREUIRectangle:withFloat:", NULL, "V", 0x104, NULL },
    { "fadeViewWithId:withFloat:", NULL, "V", 0x102, NULL },
  };
  static J2ObjcClassInfo _RARESizeAnimation = { "SizeAnimation", "com.appnativa.rare.ui.effects", NULL, 0x1, 5, methods, 0, NULL, 0, NULL};
  return &_RARESizeAnimation;
}

@end
@implementation RARESizeAnimation_Listener

- (void)animationEndedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source {
  [this$0_ animationEndedWithRAREiPlatformAnimator:this$0_];
}

- (void)animationStartedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)source {
  [this$0_ animationStartedWithRAREiPlatformAnimator:this$0_];
}

- (void)valueChangedWithRAREiPlatformAnimator:(id<RAREiPlatformAnimator>)animator
                                    withFloat:(float)value {
  [this$0_ updateWithFloat:value];
}

- (id)initWithRARESizeAnimation:(RARESizeAnimation *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARESizeAnimation" },
  };
  static J2ObjcClassInfo _RARESizeAnimation_Listener = { "Listener", "com.appnativa.rare.ui.effects", "SizeAnimation", 0x0, 0, NULL, 1, fields, 0, NULL};
  return &_RARESizeAnimation_Listener;
}

@end
