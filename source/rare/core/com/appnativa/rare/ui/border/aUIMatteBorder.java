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
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 *  @author Don DeCoteau
 */
public abstract class aUIMatteBorder extends aUIPlatformBorder {
  protected UIInsets insets     = new UIInsets(ScreenUtils.PLATFORM_PIXELS_1);
  protected UIStroke lineStroke = UIStroke.SOLID_STROKE;
  protected UIColor  lineColor;

  /**
   * Creates a matte border with the specified insets and color.
   * @param borderInsets the insets of the border
   * @param matteColor the color rendered for the border
   */
  public aUIMatteBorder(UIInsets borderInsets, UIColor matteColor) {
    if (borderInsets != null) {
      insets.set(borderInsets);
    }

    setLineColor(matteColor);
  }

  /**
   *   Creates a matte border with the specified insets and color.
   *   @param top the top inset of the border
   *   @param right the right inset of the border
   *   @param bottom the bottom inset of the border
   *   @param left the left inset of the border
   *   @param matteColor the color rendered for the border
   */
  public aUIMatteBorder(float top, float right, float bottom, float left, UIColor matteColor) {
    insets.set(top, right, bottom, left);
    setLineColor(matteColor);
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    g.clipRect(x + insets.left, y + insets.top, x + width - insets.right - 1, y + height - insets.bottom - 1);
  }

  @Override
  public Object clone() {
    aUIMatteBorder b = (aUIMatteBorder) super.clone();

    b.insets = new UIInsets(ScreenUtils.PLATFORM_PIXELS_1);

    return b;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    if (!last) {
      return;
    }

    UIStroke stroke = g.getStroke();
    UIColor  c      = g.getColor();

    g.setStroke(lineStroke);
    g.setColor(lineColor);
    UILineBorder.paintLines(g, this.insets, x, y, x + width, y + height);
    g.setStroke(stroke);
    g.setColor(c);
  }

  public void setInsets(UIInsets insets) {
    this.insets.set(insets);
  }

  public void setInsets(int top, int right, int bottom, int left) {
    insets.set(top, right, bottom, left);
  }

  public void setLineColor(UIColor c) {
    if (c == null) {
      c = aUILineBorder.getDefaultLineColor();
    }

    lineColor = c;
  }

  /**
   *  Sets the line style
   *
   * @param style the line stroke (dotted, dashed, solid)
   */
  public void setLineStyle(String style) {
    lineStroke = UIStroke.getStroke(style);
  }

  @Override
  public UIInsets getBorderInsets(UIInsets insets) {
    if (insets == null) {
      insets = new UIInsets();
    }

    insets.set(this.insets);

    return insets;
  }

  public UIColor getLineColor() {
    return lineColor;
  }

  @Override
  public boolean isPaintLast() {
    return true;
  }

  public void setLineStroke(UIStroke stroke) {
    lineStroke = stroke;
  }

  public UIStroke getLineStroke() {
    return lineStroke;
  }
}
