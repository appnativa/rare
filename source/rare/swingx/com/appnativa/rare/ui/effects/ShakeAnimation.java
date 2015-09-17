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

public class ShakeAnimation extends CompoundAnimation {
  public ShakeAnimation() {
    super(true);

    int            duration = 50;
    SlideAnimation fleft    = new SlideAnimation(true, false);

    fleft.setPercent(0.05f);
    fleft.setDuration(duration);

    SlideAnimation left = new SlideAnimation(true, false);

    left.setPercent(0.1f);
    left.setDuration(duration);

    SlideAnimation right = new SlideAnimation(true, true);

    right.setPercent(0.1f);
    right.setDuration(duration);

    SlideAnimation lright = new SlideAnimation(true, true);

    lright.setPercent(0.05f);
    lright.setDuration(duration);
    addAnimation(fleft);
    addAnimation(right);
    addAnimation(left);
    addAnimation(right);
    addAnimation(left);
    addAnimation(lright);
  }
}
