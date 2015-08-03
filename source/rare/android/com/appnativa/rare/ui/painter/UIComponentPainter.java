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

package com.appnativa.rare.ui.painter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.PainterDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;

/**
 *
 * @author Don DeCoteau
 */
public class UIComponentPainter extends aUIComponentPainter implements iBackgroundPainter {
  AndroidGraphics graphics;

  public UIComponentPainter() {}

  public void paint(View v, Canvas canvas, float x, float y, float width, float height, int orientation) {
    paint(v, canvas, x, y, width, height, orientation, false);
    paint(v, canvas, x, y, width, height, orientation, true);
  }

  public void paint(View v, Canvas canvas, float x, float y, float width, float height, int orientation, boolean end) {
    graphics = AndroidGraphics.fromGraphics(canvas, v, graphics);
    paint(graphics, x, y, width, height, orientation, end);
  }

  public Drawable getDrawable(View view) {
    if ((border != null) || (overlayPainter != null) || (bgOverlayPainter != null)) {
      return new PainterDrawable(view, this);
    }

    if (backgroundPainter != null) {
      return backgroundPainter.getDrawable(view);
    }

    return NullDrawable.getInstance();
  }
}
