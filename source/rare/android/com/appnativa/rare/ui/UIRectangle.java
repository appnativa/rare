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

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public class UIRectangle extends aUIRectangle implements iPlatformShape {
  public UIRectangle() {
    super();
  }

  public UIRectangle(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  public UIRectangle(float width, float height) {
    super(width, height);
  }

  public UIRectangle(UIDimension d) {
    super(d);
  }

  public UIRectangle(UIPoint p, UIDimension d) {
    super(p, d);
  }

  public UIRectangle(UIPoint p) {
    super(p);
  }

  public UIRectangle(UIRectangle r) {
    super(r);
  }

  public RectF getRectangle() {
    return new RectF((float) x, (float) y, (float) (x + width), (float) (y + height));
  }

  public Rect getRect() {
    return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
  }

  public Path getPath() {
    return null;
  }
}
