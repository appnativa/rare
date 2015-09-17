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

import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformShape;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class ShapeShape implements iPlatformShape {
  Shape shape;

  public ShapeShape(Shape shape) {
    super();
    this.shape = shape;
  }

  @Override
  public UIRectangle getBounds() {
    Rectangle2D r = shape.getBounds2D();

    return new UIRectangle((float) r.getX(), (float) r.getY(), (float) r.getWidth(), (float) r.getHeight());
  }

  @Override
  public Rectangle2D getRectangle() {
    return shape.getBounds2D();
  }

  @Override
  public Shape getShape() {
    return shape;
  }
}
