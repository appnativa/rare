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
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;

/**
 * An object that can paint a rectangular cell
 *
 * @author Don DeCoteau
 */
public class UICellPainter extends aUICellPainter {

  /** a cell painter that does nothing */
  public static UICellPainter NULL_CELLPAINTER = new UICellPainter() {
    public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {}
  };
  AndroidGraphics graphics;

  /**
   * Creates a new instance
   */
  public UICellPainter() {}

  public void paint(View v, Canvas g, float x, float y, float width, float height, int orientation) {
    graphics = AndroidGraphics.fromGraphics(g, v, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  public Drawable getDrawable(View view) {
    return null;
  }

  public Shader getShader(float width, float height) {
    return null;
  }
}
