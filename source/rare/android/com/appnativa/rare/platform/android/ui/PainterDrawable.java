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
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;

import android.view.View;

import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class PainterDrawable extends ShapeDrawable {
  iPlatformPainter painter;
  View             view;
  private boolean  allowInvalidateDrawable = true;

  public PainterDrawable(View view, iPlatformPainter painter) {
    this.view    = view;
    this.painter = painter;
  }

  public void draw(Canvas canvas) {
    Rect r = getBounds();

    painter.paint(view, canvas, r.left, r.top, r.width(), r.height(), iPainter.UNKNOWN);
  }

  public void invalidateSelf() {
    if (allowInvalidateDrawable) {
      super.invalidateSelf();
    }
  }

  public void setAllowInvalidateDrawable(boolean allowInvalidateDrawable) {
    this.allowInvalidateDrawable = allowInvalidateDrawable;
  }

  public boolean isAllowInvalidateDrawable() {
    return allowInvalidateDrawable;
  }
}
