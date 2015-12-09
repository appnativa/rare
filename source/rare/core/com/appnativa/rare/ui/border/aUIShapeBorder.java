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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iShapeCreator;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIShapeBorder extends UILineBorder {
  private iShapeCreator shapeCreator;

  /**
   * Constructs a new instance
   *
   * @param shapeCreator the shape creator
   */
  public aUIShapeBorder(iShapeCreator shapeCreator) {
    this(Platform.getUIDefaults().getColor("controlShadow"), shapeCreator, null, 1);
  }

  /**
   * Constructs a new instance
   *
   * @param color the color of the border
   * @param shapeCreator the shape creator
   * @param thickness the thickness of the border
   */
  public aUIShapeBorder(UIColor color, iShapeCreator shapeCreator, float thickness) {
    this(color, shapeCreator, new UIInsets(thickness, thickness, thickness, thickness), thickness);
  }

  /**
   * Creates a line border with the specified color and thickness.
   * @param color the color of the border
   * @param shapeCreator the shape creator
   * @param insets the insets
   * @param thickness the thickness of the border
   */
  public aUIShapeBorder(UIColor color, iShapeCreator shapeCreator, UIInsets insets, float thickness) {
    super(color, thickness, false);
    this.insets       = insets;
    this.shapeCreator = shapeCreator;
  }

  @Override
  public void clip(iPlatformGraphics g, float x, float y, float width, float height) {
    iPlatformPath p = shapeCreator.createClipShape(null, width, height, false);

    if (p != null) {
      g.translate(x, y);
      g.clipShape(p);
      g.translate(-x, -y);
    } else {
      if (clipInsets == null) {
        clipInsets = new UIInsets();
      }

      UIInsets in = getBorderInsets(clipInsets);

      g.clipRect(x + in.left, y + in.top, width - in.right - in.left, height - in.bottom - in.top);
    }
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

    iPlatformShape shape = shapeCreator.createShape(width, height);

    if (shape == null) {
      return;
    }

    UIColor oldColor = g.getColor();

    g.setColor(color);
    g.translate(x, y);

    float sw = g.getStrokeWidth();

    g.setStrokeWidth(thickness);
    g.drawShape(shape, x, y);
    g.setStrokeWidth(sw);
    g.setColor(oldColor);
  }

  @Override
  public void setPadForArc(boolean padForArc) {}

  public void setShapeCreator(iShapeCreator shapeCreator) {
    this.shapeCreator = shapeCreator;
  }

  @Override
  public iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height) {
    iPlatformPath p = shapeCreator.createClipShape(null, width, height, false);

    if (p != null) {
      p.translate(x, y);
    }

    return p;
  }

  /**
   * @return the shapeCreator
   */
  public iShapeCreator getShapeCreator() {
    return shapeCreator;
  }

  @Override
  public boolean isPadForArc() {
    return false;
  }

  @Override
  public boolean isRectangular() {
    return false;
  }
}
