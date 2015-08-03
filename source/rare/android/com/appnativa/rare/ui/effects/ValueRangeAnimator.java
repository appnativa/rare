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

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

public class ValueRangeAnimator extends aPlatformAnimator implements AnimatorUpdateListener {
  private boolean intValue;

  public ValueRangeAnimator(float start, float end) {
    this(ValueAnimator.ofFloat(start, end), false);
  }

  public ValueRangeAnimator(int start, int end) {
    this(ValueAnimator.ofInt(start, end), true);
  }

  protected ValueRangeAnimator(ValueAnimator a, boolean intValues) {
    super(a);
    a.addUpdateListener(this);
    this.intValue = intValues;
  }

  public static ValueRangeAnimator ofFloat(float start, float end) {
    return new ValueRangeAnimator(ValueAnimator.ofFloat(start, end), false);
  }

  public static ValueRangeAnimator ofInt(int start, int end) {
    return new ValueRangeAnimator(ValueAnimator.ofInt(start, end), true);
  }

  public void addValueListener(iAnimatorValueListener l) {
    super.addValueListener(l);
  }

  @Override
  public void removeValueListener(iAnimatorValueListener l) {
    super.removeValueListener(l);
  }

  @Override
  public void onAnimationUpdate(ValueAnimator animation) {
    if (intValue) {
      Integer i = (Integer) animation.getAnimatedValue();

      notifyValueListeners(this, i.floatValue());
    } else {
      Float i = (Float) animation.getAnimatedValue();

      notifyValueListeners(this, i.floatValue());
    }
  }

  public void start() {
    ValueAnimator a = (ValueAnimator) animator;

    a.setRepeatMode(autoReverse
                    ? Animation.REVERSE
                    : Animation.RESTART);
    a.setDuration((duration < 0)
                  ? Long.MAX_VALUE - 1
                  : duration);
    a.setRepeatCount(repeatCount);

    if ((acceleration != 0) && (deceleration != 0)) {
      a.setInterpolator(new AccelerateDecelerateInterpolator());
    } else {
      if (acceleration != 0) {
        a.setInterpolator(new AccelerateInterpolator(acceleration));
      } else if (deceleration != 0) {
        a.setInterpolator(new DecelerateInterpolator(deceleration));
      } else {
        a.setInterpolator(null);
      }
    }

    animator.start();
  }

  public void setRange(float start, float end) {
    animator = ValueAnimator.ofFloat(start, end);
    intValue = false;
  }

  public void setRange(int start, int end) {
    animator = ValueAnimator.ofInt(start, end);
    intValue = true;
  }
}
