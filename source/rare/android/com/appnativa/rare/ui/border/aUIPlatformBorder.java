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

package com.appnativa.rare.ui.border;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIPlatformBorder extends aUIBorder implements iPlatformBorder {
  public Drawable getDrawable(View view) {
    BorderDrawable d  = new BorderDrawable();
    UIInsets       in = getBorderInsets(null);
    Rect           r  = new Rect();

    AndroidHelper.insetsToRect(in, r);
    d.setPadding(r);

    return d;
  }

  public UIRectangle getInteriorRectangle(final iPlatformComponent c, final int x, final int y, final int width,
          final int height) {
    return aUIPlatformBorder.getInteriorRectangle(c, this, x, y, width, height);
  }

  public static UIRectangle getInteriorRectangle(final iPlatformComponent component, final iPlatformBorder border,
          final int x, final int y, final int width, final int height) {
    UIRectangle result = new UIRectangle(x, y, width, height);

    if (border != null) {
      UIInsets insets = border.getBorderInsets(null);

      result.x      += insets.left;
      result.y      += insets.top;
      result.width  -= insets.left + insets.right;
      result.height -= insets.top + insets.bottom;
    }

    return result;
  }

  public boolean isPaintLast() {
    return false;
  }

  class BorderDrawable extends ShapeDrawable {
    AndroidGraphics graphics;
    boolean         painting;

    public void draw(Canvas canvas) {
      if (!painting) {
        try {
          painting = true;

          Rect r = getBounds();

          graphics = AndroidGraphics.fromGraphics(canvas, null, graphics);
          paint(graphics, r.left, r.top, r.width(), r.height(), isPaintLast());
        } finally {
          painting = false;
        }
      }
    }
  }
}
