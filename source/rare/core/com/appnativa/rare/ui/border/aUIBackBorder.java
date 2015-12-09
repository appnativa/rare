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

import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIBackBorder extends UILineBorder {
  public aUIBackBorder(UIColor color) {
    super(color);
    roundedCorners = true;
  }

  public aUIBackBorder(UIColor color, float thickness) {
    super(color, thickness, ScreenUtils.platformPixels(6));
    roundedCorners = true;
  }

  public aUIBackBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
    roundedCorners = true;
  }

  public aUIBackBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
    roundedCorners = true;
  }

  @Override
  public void setCornerArc(float arc) {
    super.setCornerArc(arc);
    roundedCorners = true;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end) {
    if (end != isPaintLast()) {
      return;
    }

    UIColor color = getPaintColor(g.getComponent());

    if (color.getAlpha() == 0) {
      return;
    }

    roundedCorners = true;

    if (lineStroke == null) {
      lineStroke = new UIStroke(thickness, UIStroke.Cap.ROUND, UIStroke.Join.ROUND);
    }

    UIStroke stroke = g.getStroke();
    UIColor  c      = g.getColor();

    g.setColor(color);
    g.setStroke(lineStroke);

    if (paintShape != null) {
      paintShape.reset();
    }

    iPlatformPath p = getPath(paintShape, x, y, width, height, false);

    g.drawShape(p, 0, 0);
    g.setStroke(stroke);
    g.setColor(c);
  }

  @Override
  public aUILineBorder setThickness(float thickness) {
    lineStroke = null;

    return super.setThickness(thickness);
  }

  @Override
  protected iPlatformPath createBorderPath(iPlatformPath p, float x, float y, float width, float height, float aw,
          float ah, boolean clip) {
    return BorderUtils.createBackBorderPath(p, x, y, width, height, aw, ah, clip
            ? false
            : noLeft);
  }

  @Override
  protected UIInsets calculateInsets(UIInsets insets, boolean pad) {
    insets = super.calculateInsets(insets, pad);

    if (noLeft) {
      insets.right += ScreenUtils.platformPixels(5);
    } else {
      insets.left += ScreenUtils.platformPixels(5);
    }

    return insets;
  }
}
