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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformShape;

public class AndroidRect extends RectF implements iPlatformShape {
  public AndroidRect() {
    super();
  }

  public AndroidRect(float left, float top, float right, float bottom) {
    super(left, top, right, bottom);
  }

  public AndroidRect(Rect r) {
    super(r);
  }

  public AndroidRect(RectF r) {
    super(r);
  }

  public Path getPath() {
    return null;
  }

  public RectF getRectangle() {
    return this;
  }

  public UIRectangle getBounds() {
    return new UIRectangle(this.left, this.top, this.width(), this.height());
  }
}
