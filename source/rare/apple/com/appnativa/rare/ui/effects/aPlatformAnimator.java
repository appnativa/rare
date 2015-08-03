/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui.effects;

import com.appnativa.rare.ui.iPlatformComponent;

/*-[
#import <QuartzCore/QuartzCore.h>
#import "RAREAnimations.h"
#import "java/lang/Boolean.h"
]-*/
import com.google.j2objc.annotations.Weak;

public abstract class aPlatformAnimator extends aAnimator {
  protected int                duration = 250;
  protected float              acceleration;
  protected boolean            autoReverse;
  protected boolean            canceled;
  @Weak
  protected iPlatformComponent component;
  protected float              deceleration;
  protected String             key;
  protected Object             proxy;
  protected int                repeatCount;

  public aPlatformAnimator() {}

  public aPlatformAnimator(Object animation) {
    proxy = animation;
  }

  public aPlatformAnimator(String key) {
    proxy    = createBasicProxy(key);
    this.key = key;
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.postAnimateAction = postAnimateAction;
    this.component         = c;
    canceled               = false;
    startEx(c.getProxy());
  }

  @Override
  public void cancel() {
    canceled = true;
    stop();
  }

  @Override
  public void dispose() {
    if (component != null) {
      cancel();
    }

    super.dispose();
    component = null;
    proxy     = null;
  }

  @Override
  public native void stop()
  /*-[
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
]-*/
  ;

  @Override
  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
  }

  @Override
  public void setAutoReverse(boolean autoReverse) {
    this.autoReverse = autoReverse;
  }

  @Override
  public void setDeceleration(float deceleration) {
    this.deceleration = deceleration;
  }

  @Override
  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }

  @Override
  public Object getProxy() {
    return proxy;
  }

  @Override
  public boolean isRunning() {
    return component != null;
  }

  protected void animationEnded(iPlatformAnimator animator) {
    if (component != null) {
      setAnimatingProperty(component, false);
    }

    notifyListeners(animator, true);
    component = null;
  }

  protected void animationStarted(iPlatformAnimator animator) {
    notifyListeners(animator, false);
  }

  protected native static Object createBasicProxy(String key)
  /*-[
   return [CABasicAnimation animationWithKeyPath: key];
  ]-*/
  ;

  protected native static Object createTransitionProxy()
  /*-[
    CATransition* ta=[CATransition animation];
    ta.startProgress = 0;
    ta.endProgress = 1.0;
    return ta;
  ]-*/
  ;

  protected native void startEx(Object viewProxy)
  /*-[

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
  ]-*/
  ;

  protected native void setDelegate()
  /*-[
   if(proxy_) {
       CAAnimation *animation=(CAAnimation*)proxy_;
       animation.delegate=self;
    }
   ]-*/
  ;

  ///////////////////////////////
  //Animation delegate methods
  ///////////////////////////////
  /*-[
   - (void)animationDidStart:(CAAnimation *)theAnimation {
      [self animationStartedWithRAREiPlatformAnimator:self];
   }
   - (void)animationDidStop:(CAAnimation *)theAnimation finished:(BOOL)flag {
    [self animationEndedWithRAREiPlatformAnimator:self];
   }
  ]-*/
  ///////////////////////////////
  protected native int getIOSAnimationOptions()    /*-[
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
     ]-*/
  ;

  protected native void setValues(Object proxy)
  /*-[
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

  ]-*/
  ;
}
