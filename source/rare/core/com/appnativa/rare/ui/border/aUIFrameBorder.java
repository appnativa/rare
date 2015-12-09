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

import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIFrameBorder extends UIBevelBorder {
  private static final UIFrameBorder sharedRaisedBorder  = new UIFrameBorder(RAISED);
  private static final UIFrameBorder sharedLoweredBorder = new UIFrameBorder(LOWERED);
  protected UIColor[]                colorArray          = new UIColor[8];

  /**
   * Creates a one-line bevel border with the specified type and whose
   * colors will be derived from the background color of the
   * component passed into the paintBorder method.
   * @param bevelType the type of bevel for the border
   */
  public aUIFrameBorder(int bevelType) {
    super(bevelType);
  }

  /**
   * Creates a one-line bevel border with the specified type, highlight and
   * shadow colors.
   * @param bevelType the type of bevel for the border
   * @param highlight the color to use for the bevel highlight
   * @param shadow the color to use for the bevel shadow
   */
  public aUIFrameBorder(int bevelType, UIColor highlight, UIColor shadow) {
    super(bevelType, highlight.brighter(), highlight, shadow, shadow.brighter());
  }

  /**
   * Creates a one-line bevel border with the specified type, highlight and
   * shadow colors.
   * <p>
   * Note: The shadow inner and outer colors are
   * switched for a lowered bevel border.
   *
   * @param bevelType the type of bevel for the border
   * @param highlightOuterColor the color to use for the bevel outer highlight
   * @param highlightInnerColor the color to use for the bevel inner highlight
   * @param shadowOuterColor the color to use for the bevel outer shadow
   * @param shadowInnerColor the color to use for the bevel inner shadow
   */
  public aUIFrameBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor,
                        UIColor shadowOuterColor, UIColor shadowInnerColor) {
    super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
  }

  public static UIFrameBorder createBorder(float bevelType) {
    if (bevelType == RAISED) {
      return sharedRaisedBorder;
    } else {
      return sharedLoweredBorder;
    }
  }

  @Override
  public UIInsets getBorderInsets(final UIInsets insets) {
    if (insets != null) {
      insets.top    = 4;
      insets.left   = 4;
      insets.right  = 4;
      insets.bottom = 4;

      return insets;
    } else {
      return new UIInsets(4, 4, 4, 4);
    }
  }

  @Override
  protected void paintLoweredBevel(iPlatformGraphics g, float x, float y, float width, float height) {
    colorArray[0] = getShadowOuterColor();
    colorArray[1] = getHighlightOuterColor();
    colorArray[2] = getShadowInnerColor();
    colorArray[3] = getHighlightInnerColor();
    colorArray[4] = getHighlightInnerColor();
    colorArray[5] = getShadowInnerColor();
    colorArray[6] = getHighlightInnerColor();
    colorArray[7] = getShadowOuterColor();
    paintBevel(g, x, y, width, height, colorArray);
  }

  protected void paintBevel(iPlatformGraphics g, float x, float y, float width, float height, UIColor[] colors) {
    UIColor oldColor = g.getColor();
    float   d1       = ScreenUtils.PLATFORM_PIXELS_1;
    float   d2       = ScreenUtils.PLATFORM_PIXELS_2;
    int     i        = 0;

    while(i < 8) {
      g.setColor(colors[i++]);
      g.fillRect(x, y, width, d1);
      g.fillRect(x, y, d1, height);
      g.setColor(colors[i++]);
      g.fillRect(x, y + height - d1, width, d1);
      g.fillRect(x + width - d1, y, d1, height);
      x      += d1;
      y      += d1;
      width  -= d2;
      height -= d2;
    }

    g.setColor(oldColor);
  }

  @Override
  protected void paintRaisedBevel(iPlatformGraphics g, float x, float y, float width, float height) {
    colorArray[0] = getHighlightOuterColor();
    colorArray[1] = getShadowOuterColor();
    colorArray[2] = getHighlightInnerColor();
    colorArray[3] = getShadowInnerColor();
    colorArray[4] = getShadowInnerColor();
    colorArray[5] = getHighlightInnerColor();
    colorArray[6] = getShadowOuterColor();
    colorArray[7] = getHighlightOuterColor();
    paintBevel(g, x, y, width, height, colorArray);
  }
}
