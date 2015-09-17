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

package com.appnativa.rare.ui;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class UIRectangle extends aUIRectangle implements iPlatformShape {
  public UIRectangle() {
    super();
  }

  public UIRectangle(Rectangle r) {
    super(r.x, r.y, r.width, r.height);
  }

  public UIRectangle(UIDimension d) {
    super(d);
  }

  public UIRectangle(UIPoint p) {
    super(p);
  }

  public UIRectangle(UIRectangle r) {
    super(r);
  }

  public UIRectangle(float width, float height) {
    super(width, height);
  }

  public UIRectangle(UIPoint p, UIDimension d) {
    super(p, d);
  }

  public UIRectangle(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  @Override
  public Rectangle2D getRectangle() {
    return new Rectangle2D.Float(x, y, width, height);
  }

  @Override
  public Shape getShape() {
    return getRectangle();
  }
}
