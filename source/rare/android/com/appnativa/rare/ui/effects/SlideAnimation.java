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

import android.view.animation.DecelerateInterpolator;

public class SlideAnimation extends aSlideAnimation implements AnimatorUpdateListener {
  public SlideAnimation() {
    this(true, false);
  }

  public SlideAnimation(boolean horizontal) {
    this(horizontal, false);
  }

  public SlideAnimation(boolean horizontal, boolean fromLeftOrTop) {
    super(horizontal, fromLeftOrTop);

    ValueAnimator a = ValueAnimator.ofFloat(0f, 1f);

    a.setInterpolator(new DecelerateInterpolator(.8f));
    setAnimator(a);
    ((ValueAnimator) a).addUpdateListener(this);
  }

  @Override
  public void onAnimationUpdate(ValueAnimator animation) {
    Float fraction = (Float) animation.getAnimatedValue();

    update(fraction.floatValue());
  }
}
