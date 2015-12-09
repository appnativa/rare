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

package com.appnativa.rare.platform.android.ui.view;

import android.graphics.Canvas;
import android.graphics.drawable.PaintDrawable;

import com.appnativa.rare.ui.UIColor;

/**
 *
 * @author Don DeCoteau
 */
public class BackgroundDrawable extends PaintDrawable {
  UIColor         color;
  private boolean dynamic;

  public BackgroundDrawable() {}

  public BackgroundDrawable(UIColor color) {
    super();
    this.color = color;
    dynamic    = color.isDynamic();
    this.getPaint().setColor(color.getColor());
  }

  @Override
  public void draw(Canvas canvas) {
    if (dynamic) {
      getPaint().setColor(color.getColor());
    }

    super.draw(canvas);
  }

  @Override
  public void setAlpha(int alpha) {}

  public void setColor(UIColor bg) {
    dynamic = false;

    if ((color == null) && (bg != null)) {
      setVisible(false, false);
    }

    color = bg;

    if (bg != null) {
      dynamic = color.isDynamic();
      getPaint().setColor(bg.getColor());
    }
  }

  public UIColor getColor() {
    return color;
  }
}
