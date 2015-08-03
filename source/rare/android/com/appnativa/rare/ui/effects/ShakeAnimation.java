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

import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.iPlatformComponent;

public class ShakeAnimation extends aPlatformAnimator {
  public ShakeAnimation() {
    super(new TranslateAnimation(0, ScreenUtils.PLATFORM_PIXELS_5, 0, 0));
    animation.setInterpolator(new CycleInterpolator(4));
    animation.setDuration(500);
  }

  @Override
  public void animate(iPlatformComponent c, Object postAnimateAction) {
    this.setPostAnimateAction(postAnimateAction);
    component = c;
    ((View) c.getView()).startAnimation(animation);
  }
}
