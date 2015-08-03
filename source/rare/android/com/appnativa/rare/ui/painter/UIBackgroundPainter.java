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
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

import android.view.View;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;

/**
 * The default background painter that handles painting gradients
 *
 * @author Don DeCoteau
 */
public final class UIBackgroundPainter extends aUIBackgroundPainter implements Cloneable {

  /**
   * default painter that paints the gradient using the component's background
   * color using -45/+45 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_DK = new UIBackgroundPainter(null, null,
                                                                          Direction.VERTICAL_TOP);

  /**
   * default painter that paints the gradient using the component's background
   * color using -60/+60 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_DK_DK = new UIBackgroundPainter(null, null,
                                                                             Direction.VERTICAL_TOP);

  /**
   * default painter that paints the gradient using the component's background
   * color using -15/+15 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_LT = new UIBackgroundPainter(null, null,
                                                                          Direction.VERTICAL_TOP);

  /**
   * default painter that paints the gradient using the component's background
   * color using -30/+30 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_MID = new UIBackgroundPainter(null, null,
                                                                           Direction.VERTICAL_TOP);

  static {
    BGCOLOR_GRADIENT_PAINTER_DK.bgColorType    = BGCOLOR_TYPE.DARK;
    BGCOLOR_GRADIENT_PAINTER_DK_DK.bgColorType = BGCOLOR_TYPE.DARK_DARK;
    BGCOLOR_GRADIENT_PAINTER_LT.bgColorType    = BGCOLOR_TYPE.LITE;
    BGCOLOR_GRADIENT_PAINTER_MID.bgColorType   = BGCOLOR_TYPE.MIDDLE;
  }

  Paint  paint;
  Shader shader;
  float  shaderHeight;
  float  shaderWidth;

  /** Creates a new instance of BackgroundRenderer */
  public UIBackgroundPainter() {}

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param bg
   *          the background color
   */
  public UIBackgroundPainter(UIColor bg) {
    super(bg);
  }

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param start
   *          the start color
   * @param end
   *          the end color
   */
  public UIBackgroundPainter(UIColor start, UIColor end) {
    super(start, end);
  }

  /**
   * Creates a new instance of BackgroundRenderer
   *
   * @param colors
   *          the gradient colors
   * @param direction
   *          the direction
   */
  public UIBackgroundPainter(UIColor[] colors, Direction direction) {
    super(colors, direction);
  }

  /**
   * Creates a background gradient painter
   *
   * @param start
   *          the start color
   * @param end
   *          the end color
   * @param direction
   *          the direction
   */
  public UIBackgroundPainter(UIColor start, UIColor end, Direction direction) {
    super(start, end, direction);
  }

  /**
   * Creates a background gradient painter
   *
   * @param type
   *          the type of gradient
   * @param colors
   *          the colors
   * @param direction
   *          the direction
   * @param distribution
   *          the distribution
   * @param magnitude
   *          the magnitude
   */
  public UIBackgroundPainter(Type type, Direction direction, UIColor[] colors, float[] distribution, int magnitude) {
    super(type, direction, colors, distribution, magnitude);
  }

  /**
   * Creates and returns a copy of this object.
   *
   * @return a copy of this object.
   */
  public Object clone() {
    try {
      UIBackgroundPainter bp = (UIBackgroundPainter) super.clone();

      bp.clearCache();
      bp.setGradientColors(gradientColors);

      return bp;
    } catch(Exception ex) {
      throw new InternalError();
    }
  }

  /**
   * Creates a background gradient painter
   *
   * @param sc
   *          the start color
   * @param ec
   *          the end color
   * @param direction
   *          the direction
   *
   * @return the new painter
   */
  public static UIBackgroundPainter create(UIColor sc, UIColor ec, Direction direction) {
    return new UIBackgroundPainter(sc, ec, direction);
  }

  public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {
    if (paint == null) {
      paint = new Paint();
      paint.setStyle(Style.FILL);
    }

    paint.setShader(getShader(width, height));
    // g.drawRect(x, y, x + width + 1, y + height + 1, paint);
    g.translate(x, y);
    g.drawRect(0, 0, width + 1, height + 1, paint);
    g.translate(-x, -y);
  }

  public Shader getShader(float width, float height) {
    if ((shader != null) && (width == shaderHeight) && (height == shaderHeight)) {
      return shader;
    }

    int     len      = gradientColors.length;
    int     colors[] = new int[len];
    UIColor a[]      = getGradientColors(getBackgroundColor());

    for (int i = 0; i < len; i++) {
      colors[i] = a[i].getColor();
    }

    Shader shader = null;

    if (gradientType == Type.RADIAL) {
      float   radius = calculateRadius(width, height);
      UIPoint p      = calculateRadialCenter(width, height);

      shader = new RadialGradient(p.x, p.y, radius, colors, gradientDistribution, TileMode.CLAMP);
    } else {
      UIRectangle r = calculateLinearRect(width, height);

      shader = new LinearGradient(r.x, r.y, r.x + r.width, r.y + r.height, colors, gradientDistribution,
                                  TileMode.CLAMP);
    }

    this.shader = shader;

    return shader;
  }

  protected void clearCache() {
    super.clearCache();
    shader = null;
    ipaint = null;
  }
}
