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

import android.graphics.Paint;

public class UIFontMetrics {
  private UIFont      font;
  private final Paint paint;

  public UIFontMetrics(UIFont font) {
    this.font = font;
    paint     = new Paint();
    font.setupPaint(paint);
  }

  public boolean equals(Object o) {
    if (o instanceof UIFontMetrics) {
      if (o == this) {
        return true;
      }

      return ((UIFontMetrics) o).font.equals(font);
    }

    return false;
  }

  public int stringWidth(String string) {
    return (int) (paint.measureText(string) + .99f);
  }

  public void setFont(UIFont font) {
    this.font = font;
    font.setupPaint(paint);
  }

  public float getAscent() {
    return -paint.ascent();
  }

  public float getDescent() {
    return paint.descent();
  }

  public UIFont getFont() {
    return font;
  }

  public float getHeight() {
    return -paint.ascent() + paint.descent();
  }

  public float getLeading() {
    return 0;
  }

  public static UIFontMetrics getMetrics(UIFont font) {
    return new UIFontMetrics(font);
  }
}
