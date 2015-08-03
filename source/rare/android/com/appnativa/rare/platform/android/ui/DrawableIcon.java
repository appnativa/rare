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

package com.appnativa.rare.platform.android.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.appnativa.rare.ui.iPlatformIcon;

public class DrawableIcon extends Drawable {
  iPlatformIcon icon;

  public DrawableIcon(iPlatformIcon icon) {
    this.icon = icon;
  }

  public void draw(Canvas canvas) {
    Rect r = getBounds();

    icon.paint(canvas, r.left, r.top, r.width(), r.height());
  }

  @Override
  public int getIntrinsicHeight() {
    return icon.getIconHeight();
  }

  @Override
  public int getIntrinsicWidth() {
    return icon.getIconWidth();
  }

  @Override
  public void setAlpha(int alpha) {}

  @Override
  public void setColorFilter(ColorFilter cf) {}

  @Override
  public int getOpacity() {
    return android.graphics.PixelFormat.TRANSPARENT;
  }
}
