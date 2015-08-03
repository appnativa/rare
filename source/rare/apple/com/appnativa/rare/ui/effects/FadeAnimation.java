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

public class FadeAnimation extends aFadeAnimation {
  public FadeAnimation() {
    super();
  }

  @Override
  protected native void startEx(Object viewProxy)
  /*-[
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
 ]-*/
  ;
}
