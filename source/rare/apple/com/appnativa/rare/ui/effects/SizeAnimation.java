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

import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;

import com.google.j2objc.annotations.WeakOuter;
/*-[
#import "AppleHelper.h"
#import "APView+Component.h"
 ]-*/

public class SizeAnimation extends aSizeAnimation {
  public SizeAnimation() {
    super();
  }

  public SizeAnimation(boolean horizontal) {
    super(horizontal);
  }

  public SizeAnimation(boolean horizontal, boolean diagonal, RenderType anchor) {
    super(horizontal, diagonal, anchor);
  }

  @Override
  public iPlatformComponent setIncommingComponent(iPlatformComponent inComponent) {
    this.inComponent = inComponent;

    return inComponent;
  }

  @Override
  protected void startEx(Object viewProxy) {
    if (isComponentDisposed()) {
      cancel();

      return;
    }

    Object  otherView  = null;
    float   otherAlpha = 0;
    boolean small      = direction == Direction.BACKWARD;

    if (component != null) {
      setAnimatingProperty(component, true);
    }

    boolean fade = (fadeIn &&!small) || (fadeOut && small);

    if (inComponent != null) {
      if (small) {
        viewProxy = outComponent.getProxy();

        if (fadeIn) {
          otherView  = inComponent.getProxy();
          otherAlpha = 1;
        }
      } else {
        viewProxy = inComponent.getProxy();

        if (fadeOut) {
          otherView  = outComponent.getProxy();
          otherAlpha = 0;
        }
      }
    }

    startEx(viewProxy, boundsChanger.from, boundsChanger.to, fade
            ? (small
               ? 0
               : 1)
            : -1);

    if (otherView != null) {
      fadeView(otherView, otherAlpha);
    }
  }

  @Override
  protected native int getIOSAnimationOptions()
  /*-[
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
  ]-*/
  ;

  protected native void startEx(Object viewProxy, UIRectangle start, UIRectangle end, float alpha)
  /*-[
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
 ]-*/
  ;

  private native void fadeView(Object viewProxy, float endAlpha)
  /*-[
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
]-*/
  ;

  @WeakOuter
  class Listener implements iAnimatorListener, iAnimatorValueListener {
    @Override
    public void animationEnded(iPlatformAnimator source) {
      SizeAnimation.this.animationEnded(SizeAnimation.this);
    }

    @Override
    public void animationStarted(iPlatformAnimator source) {
      SizeAnimation.this.animationStarted(SizeAnimation.this);
    }

    @Override
    public void valueChanged(iPlatformAnimator animator, float value) {
      update(value);
    }
  }
}
