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

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.DrawableIcon;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;

public abstract class aPlatformIcon implements iPlatformIcon {
  protected Drawable        drawable;
  protected AndroidGraphics graphics;
  
  public aPlatformIcon() {}

  @Override
  public void paint(Canvas g, float x, float y, float width, float height) {
    graphics = AndroidGraphics.fromGraphics(g, null, graphics);
    paint(graphics, x, y, width, height);
    graphics.clear();
  }

  @Override
  public Drawable getDrawable(View view) {
    if (drawable == null) {
      drawable = new DrawableIcon(this);
    }

    return drawable;
  }
  
  @Override
  public Drawable createDrawable(View view) {
      return  new DrawableIcon(this);
  }
  
}
