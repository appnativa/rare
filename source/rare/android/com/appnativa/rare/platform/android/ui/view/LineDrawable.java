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
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.UIColor;

/**
 *
 * @author Don DeCoteau
 */
public class LineDrawable extends Drawable {
  int             color = 0xff00000;
  DashPathEffect  pathEffect;
  private boolean horizontal = true;
  private float   thickness  = 1;

  public LineDrawable() {
    this(null, null);
  }

  public LineDrawable(UIColor c) {
    this(c, null);
  }

  public LineDrawable(UIColor c, String style) {
    this(c, style, 1);
  }

  public LineDrawable(UIColor c, String style, int thickness) {
    super();
    this.thickness = thickness;

    if (c == null) {
      c = Platform.getUIDefaults().getColor("Rare.LineBorder.color");

      if (c == null) {
        c = Platform.getUIDefaults().getColor("Rare.backgroundShadow");
      }

      if (c == null) {
        c = UIColor.BLACK;
      }
    }

    setLineColor(c);

    if (style != null) {
      setLineStyle(style);
    }
  }

  public void draw(Canvas canvas) {
    Rect  bounds = getBounds();
    float d      = 0;

    //if (thickness > 1) {
    d = thickness / 2;
    //}

    Paint p = new Paint();

    p.setAntiAlias(false);
    p.setStyle(Paint.Style.STROKE);
    p.setStrokeWidth(thickness);
    p.setColor(color);

    if (pathEffect != null) {
      p.setPathEffect(pathEffect);
    }

    if (isHorizontal()) {
      canvas.drawLine(bounds.left, bounds.top + d, bounds.right, bounds.top + d, p);
    } else {
      canvas.drawLine(bounds.left + d, bounds.top, bounds.left + d, bounds.bottom, p);
    }
  }

  public void setAlpha(int alpha) {}

  public void setColorFilter(ColorFilter cf) {}

  /**
   * @param horizontal the horizontal to set
   */
  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public void setLineColor(UIColor c) {
    color = c.getColor();
  }

  /**
   *   Sets the line style
   *
   *  @param style the line stroke (dotted, dashed, solid)
   */
  public void setLineStyle(String style) {
    if ("dotted".equalsIgnoreCase(style)) {
      pathEffect = new DashPathEffect(new float[] { 1f, 1f }, 0f);
    } else if ("dashed".equalsIgnoreCase(style)) {
      pathEffect = new DashPathEffect(new float[] { 2f, 1f }, 0f);
    }
  }

  /**
   * @param thickness the thickness to set
   */
  public void setThickness(float thickness) {
    this.thickness = thickness;
  }

  public int getIntrinsicHeight() {
    return (int) (isHorizontal()
                  ? getThickness()
                  : -1);
  }

  public int getIntrinsicWidth() {
    return (int) (isHorizontal()
                  ? -1
                  : getThickness());
  }

  // Always want it to be opaque.
  public int getOpacity() {
    return PixelFormat.OPAQUE;
  }

  /**
   * @return the thickness
   */
  public float getThickness() {
    return thickness;
  }

  /**
   * @return the horizontal
   */
  public boolean isHorizontal() {
    return horizontal;
  }
}
