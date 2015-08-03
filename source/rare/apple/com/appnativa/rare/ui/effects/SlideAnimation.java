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

public class SlideAnimation extends aSlideAnimation {
  public SlideAnimation() {
    this(true, false);
  }

  public SlideAnimation(boolean horizontal) {
    super(horizontal, false);
  }

  public SlideAnimation(boolean horizontal, boolean fromLeftOrTop) {
    super(horizontal, fromLeftOrTop);
  }

  @Override
  protected void startEx(Object viewProxy) {
    if (isComponentDisposed()) {
      cancel();

      return;
    }

    if ((outComponent == component) && (inComponent != null)) {
      startEx(viewProxy, inComponent.getProxy());
    } else {
      startEx(viewProxy, null);
    }
  }

  protected native void startEx(Object viewProxy, Object viewProxy2)
  /*-[
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
  ]-*/
  ;
}
