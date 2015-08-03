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

import com.appnativa.rare.ui.RenderType;

public class SizeAnimation extends aSizeAnimation {
  public SizeAnimation() {
    this(true, true, RenderType.CENTERED);
  }

  public SizeAnimation(boolean horizontal) {
    this(horizontal, true, RenderType.CENTERED);
  }

  public SizeAnimation(boolean horizontal, boolean diagonal, RenderType anchor) {
    super(horizontal, diagonal, anchor);

    ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);

    va.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        if (isComponentDisposed()) {
          cancel();

          return;
        }

        Float fv = (Float) animation.getAnimatedValue();

        update(fv);

        if ((inComponent == null) && (component != null) &&!component.isVisible()) {
          component.setVisible(true);
        }
      }
    });
    setAnimator(va);
  }
}
