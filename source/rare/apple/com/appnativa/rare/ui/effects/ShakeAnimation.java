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

public class ShakeAnimation extends aPlatformAnimator {
  public ShakeAnimation() {}

  @Override
  protected native void startEx(Object viewProxy)
  /*-[
    #if TARGET_OS_IPHONE
      UIView* view=(UIView*)viewProxy;
    #else
      NSView* view=(NSView*)viewProxy;
    #endif
    [UIView animateKeyframesWithDuration:0.5 delay:0.0 options:0 animations:^{
        [UIView setAnimationCurve:UIViewAnimationCurveLinear];

        NSInteger repeatCount = 8;
        NSTimeInterval duration = 1.0 / (NSTimeInterval)repeatCount;

        for (NSInteger i = 0; i < repeatCount; i++) {
            [UIView addKeyframeWithRelativeStartTime:i * duration relativeDuration:duration animations:^{
                CGFloat dx = 5.0;
                if (i == repeatCount - 1) {
                    view.transform = CGAffineTransformIdentity;
                } else if (i % 2) {
                    view.transform = CGAffineTransformTranslate(CGAffineTransformIdentity, -dx, 0.0);
                } else {
                    view.transform = CGAffineTransformTranslate(CGAffineTransformIdentity, +dx, 0.0);
                }
            }];
        }
    } completion: ^(BOOL finished) {
       [self animationEndedWithRAREiPlatformAnimator: self];
      }];

 ]-*/
  ;
}
