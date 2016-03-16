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

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

/*-[
 #import "AppleHelper.h"
 ]-*/
public class UIBackgroundPainter extends aUIBackgroundPainter implements Cloneable, iPlatformPaint {

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

  UIPoint          end;
  UIPoint          start;
  protected Object proxy;

  /**
   * Creates a background gradient painter
   */
  public UIBackgroundPainter() {}

  /**
   * Creates a background gradient painter
   *
   * @param bg
   *          the background color
   */
  public UIBackgroundPainter(UIColor bg) {
    super(bg);
  }

  /**
   * Creates a background gradient painter
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
   * Creates a background gradient painter
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

  @Override
  public boolean canUseLayer() {
    return (displayed == Displayed.ALWAYS) && (gradientType != Type.RADIAL);
  }

  @Override
  public boolean canUseMainLayer() {
    return (displayed == Displayed.ALWAYS) && (gradientType != Type.RADIAL);
  }

  /**
   * Creates and returns a copy of this object.
   *
   * @return a copy of this object.
   */
  @Override
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

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    if (proxy == null) {
      proxy = createProxy(getGradientColors(getBackgroundColor()), gradientDistribution);
    }

    // we need to clip otherwise gradients fills context
    g.saveState();
    g.clipRect(x, y, width, height);

    if (gradientType == Type.RADIAL) {
      float   radius = calculateRadius(width, height);
      UIPoint p      = calculateRadialCenter(width, height);

      drawRadialGradient(proxy, g.getContextRef(), width, height, p.x + x, p.y + y, radius);
    } else {
      UIRectangle r = calculateLinearRect(width, height);

      if (start == null) {
        start = new UIPoint();
        end   = new UIPoint();
      }

      start.x = r.x + x;
      start.y = r.y + y;
      end.x   = r.x + x + r.width;
      end.y   = r.y + y + r.height;
      drawGradient(proxy, g.getContextRef(), start, end);
    }

    g.restoreState();
  }

  //
  // private native void drawLinearGradientInRect(Object nativeGradient, Object
  // context, float x,
  // float y, float width, float height, float angle)
  // /*-[
  // [AppleHelper drawLinearGradient:nativeGradient context:context x:x y:y
  // width:width height:height angle:angle];
  // ]-*/;
  //
  @Override
  public iPlatformPaint getPaint(float width, float height) {
    return this;
  }

  @Override
  public UIColor getPlatformPaintColor() {
    return getBackgroundColor();
  }

  public iBackgroundPainter getPlatformPaintPainter() {
    return this;
  }

  @Override
  public boolean isPainter() {
    return true;
  }

  @Override
  protected void clearCache() {
    super.clearCache();
    proxy = null;
  }

  @Override
  public void updateModCount() {
    clearCache();
  }

  protected native Object createProxy(UIColor[] gradientColors, float[] gradientDistribution)
  /*-[
    return [AppleHelper createGradientWithColors: gradientColors distribution: gradientDistribution];
  ]-*/
  ;

  protected native void drawGradient(Object nativeGradient, Object context, UIPoint start, UIPoint end)
  /*-[
    [AppleHelper drawGradient:nativeGradient context:context fromPoint:start toPoint:end];
  ]-*/
  ;

  protected native void drawRadialGradient(Object nativeGradient, Object context, float width, float height, float cx,
          float cy, float radius)
  /*-[
    [AppleHelper drawRadialGradient:nativeGradient context:context width:width height:height cx:cx cy:cy radius: radius];
  ]-*/
  ;

  protected native void drawGradient(Object nativeGradient, Object context, UIPoint start, float startr, UIPoint end,
                                     float endr)
  /*-[
    [AppleHelper drawGradient:nativeGradient context:context fromCenter:start radius:startr toCenter:end radius:endr];
  ]-*/
  ;
}
