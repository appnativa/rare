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

package com.appnativa.rare.ui.border;

import android.content.res.ColorStateList;

import android.graphics.Rect;

import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;

/**
 * A line border that supports non rectangular lines
 *
 * @author Don DeCoteau
 */
public class UILineBorder extends aUILineBorder {
  protected ColorStateList stateColors;

  /**
   * Constructs a new instance
   */
  public UILineBorder() {
    this(getDefaultLineColor(), ScreenUtils.PLATFORM_PIXELS_1, false);
  }

  /**
   * Constructs a new instance
   *
   * @param color {@inheritDoc}
   */
  public UILineBorder(UIColor color) {
    this(color, ScreenUtils.PLATFORM_PIXELS_1, false);
  }

  /**
   * Creates a line border with the specified color and thickness.
   * @param color the color of the border
   * @param thickness the thickness of the border
   */
  public UILineBorder(UIColor color, float thickness) {
    this(color, thickness, false);
  }

  /**
   * Creates a line border with the specified color, thickness,
   * and corner shape.
   * @param color the color of the border
   * @param thickness the thickness of the border
   * @param roundedCorners whether or not border corners should be round
   * @since 1.3
   */
  public UILineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super(color, thickness, roundedCorners);
  }

  /**
   * Creates a line border with the specified color, thickness,
   * and corner shape.
   * @param color the color of the border
   * @param thickness the thickness of the border
   * @param arc the arc
   * @since 1.3
   */
  public UILineBorder(UIColor color, float thickness, float arc) {
    this(color, thickness, arc, arc);
  }

  /**
   * Creates a line border with the specified color, thickness,
   * and corner shape.
   * @param color the color of the border
   * @param thickness the thickness of the border
   * @param arcWidth the arc height
   * @param arcHeight the arc width
   */
  public UILineBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }

  public boolean getPadding(Rect padding) {
    if (this.insets != null) {
      AndroidHelper.insetsToRect(this.insets, padding);

      return true;
    }

    calculateInsets(padding);

    return true;
  }

  public boolean isStateful() {
    return stateColors != null;
  }

  protected void calculateInsets(Rect rect) {
    int tw = (int) thickness;
    int th = tw;
    int tb = tw;

    if (padForArc) {
      tw += (arcWidth / 3);
      th += (arcHeight / 3);

      if (!flatBottom) {
        tb += (arcWidth / 3);
      }
    }

    if (flatTop) {
      th = noTop
           ? 0
           : th;
    }

    if (flatBottom) {
      tb = noBottom
           ? 0
           : tb;
    }

    if (rect != null) {
      rect.left   = noLeft
                    ? 0
                    : tw;
      rect.right  = noRight
                    ? 0
                    : tw;
      rect.top    = noTop
                    ? 0
                    : th;
      rect.bottom = noBottom
                    ? 0
                    : tb;
    }
  }
}
