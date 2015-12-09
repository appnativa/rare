//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/effects/SlideAnimation.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/effects/SlideAnimation.h"
#include "com/appnativa/rare/ui/effects/aPlatformAnimator.h"
#include "com/appnativa/rare/ui/effects/aSlideAnimation.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"

@implementation RARESlideAnimation

- (id)init {
  return [self initRARESlideAnimationWithBoolean:YES withBoolean:NO];
}

- (id)initWithBoolean:(BOOL)horizontal {
  return [super initWithBoolean:horizontal withBoolean:NO];
}

- (id)initRARESlideAnimationWithBoolean:(BOOL)horizontal
                            withBoolean:(BOOL)fromLeftOrTop {
  return [super initWithBoolean:horizontal withBoolean:fromLeftOrTop];
}

- (id)initWithBoolean:(BOOL)horizontal
          withBoolean:(BOOL)fromLeftOrTop {
  return [self initRARESlideAnimationWithBoolean:horizontal withBoolean:fromLeftOrTop];
}

- (void)startExWithId:(id)viewProxy {
  if ([self isComponentDisposed]) {
    [self cancel];
    return;
  }
  if ((outComponent_ == component_) && (inComponent_ != nil)) {
    [self startExWithId:viewProxy withId:[inComponent_ getProxy]];
  }
  else {
    [self startExWithId:viewProxy withId:nil];
  }
}

- (void)startExWithId:(id)viewProxy
               withId:(id)viewProxy2 {
  #if TARGET_OS_IPHONE
  UIView* view=(UIView*)viewProxy;
  CGPoint center=CGPointMake(locationChanger_.getToCenterX, locationChanger_.getToCenterY);
  UIView* view2=(UIView*)viewProxy2;
  CGPoint center2;
  if(view2) {
    center2=view.center;
  }
  #else
  NSView* view=(NSView*)viewProxy;
  NSRect sframe=view.frame;
  NSRect frame=view.frame;
  frame.origin.x=NSMidX(frame);
  frame.origin.y=NSMidY(frame);
  NSPoint center=frame.origin;
  #endif
  
  #if TARGET_OS_IPHONE
  UIViewAnimationOptions options=[self getIOSAnimationOptions];
  [self animationStartedWithRAREiPlatformAnimator: self];
  [UIView animateWithDuration:duration_/1000.0f delay:0 options:options animations:^{
    view.center=center;
    if(view2) {
      view2.center=center2;
    }
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
    { "startExWithId:", NULL, "V", 0x4, NULL },
    { "startExWithId:withId:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcClassInfo _RARESlideAnimation = { "SlideAnimation", "com.appnativa.rare.ui.effects", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARESlideAnimation;
}

@end
