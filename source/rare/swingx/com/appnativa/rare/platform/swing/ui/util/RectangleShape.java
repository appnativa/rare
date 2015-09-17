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

package com.appnativa.rare.platform.swing.ui.util;

import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformShape;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends UIRectangle implements iPlatformShape {
  Rectangle2D shape;

  public RectangleShape() {}

  public RectangleShape(Rectangle r) {
    super(r);
  }

  public RectangleShape(UIDimension d) {
    super(d);
  }

  public RectangleShape(UIPoint p) {
    super(p);
  }

  public RectangleShape(UIRectangle r) {
    super(r);
  }

  public RectangleShape(float width, float height) {
    super(width, height);
  }

  public RectangleShape(UIPoint p, UIDimension d) {
    super(p, d);
  }

  public RectangleShape(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  @Override
  public UIRectangle getBounds() {
    return this;
  }

  @Override
  public Rectangle2D getRectangle() {
    if (shape == null) {
      shape = new Rectangle2D.Float(x, y, width, height);
    }

    return shape;
  }

  @Override
  public Shape getShape() {
    return getRectangle();
  }
}
