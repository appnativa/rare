/*
 * @(#)UIBackgroundPainter.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformGraphics;

public class UIBackgroundPainter extends aUIBackgroundPainter implements Cloneable {

  /**
   * default painter that paints the gradient using the component's background
   * color using -45/+45 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_DK    = new UIBackgroundPainter(null, null,
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
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_LT    = new UIBackgroundPainter(null, null,
                                                                             Direction.VERTICAL_TOP);

  /**
   * default painter that paints the gradient using the component's background
   * color using -30/+30 light adjustment
   */
  public static final UIBackgroundPainter BGCOLOR_GRADIENT_PAINTER_MID   = new UIBackgroundPainter(null, null,
                                                                             Direction.VERTICAL_TOP);
  static {
    BGCOLOR_GRADIENT_PAINTER_DK.bgColorType = BGCOLOR_TYPE.DARK;
    BGCOLOR_GRADIENT_PAINTER_DK_DK.bgColorType = BGCOLOR_TYPE.DARK_DARK;
    BGCOLOR_GRADIENT_PAINTER_LT.bgColorType = BGCOLOR_TYPE.LITE;
    BGCOLOR_GRADIENT_PAINTER_MID.bgColorType = BGCOLOR_TYPE.MIDDLE;
  }

  Paint                                   paint;
  private Rectangle                       drawRect;
  private GradientPaintEx                 gPaint;
  private Rectangle                       paintRect;

  /**
   * Creates a background gradient painter
   */
  public UIBackgroundPainter() {
  }

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

  /**
   * Creates and returns a copy of this object.
   *
   * @return a copy of this object.
   */
  @Override
  public Object clone() {
    UIBackgroundPainter bp = (UIBackgroundPainter) super.clone();

    bp.clearCache();
    bp.setGradientColors(gradientColors);

    return bp;
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

  public Paint createGradient(float x, float y, float width, float height) {
    UIColor[] colors = getGradientColors(getBackgroundColor());
    Paint paint;
    try {
      if (gradientType == Type.RADIAL) {

        float radius = calculateRadius(width, height);
        UIPoint p = calculateRadialCenter(width, height);
        paint = new RadialGradientPaint(p.x, p.y, radius, gradientDistribution, colors);
      } else {
        CycleMethod cycleMethod = CycleMethod.NO_CYCLE;

        switch (gradientType) {
          case LINEAR_REPEAT:
            cycleMethod = CycleMethod.REPEAT;

            break;

          case LINEAR_REFLECT:
            cycleMethod = CycleMethod.REFLECT;

            break;

          default:
            break;
        }

        UIRectangle r = calculateLinearRect(width, height);
        paint = new LinearGradientPaint(r.x+x, r.y+y, r.x + r.width+x, r.y + r.height+y, gradientDistribution, colors, cycleMethod);
      }

    } catch (Exception e) {
      Platform.ignoreException("createGradient failed", e);
      paint = colors[0];
    }

    return paint;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    paint = getPaint(x, y, width, height);

    Graphics2D g2 = ((SwingGraphics) g).getGraphics();
    Paint p = g2.getPaint();
    Object o = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

    g2.setPaint(paint);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.fillRect(x, y, width, height);
    g2.setPaint(p);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, o);
  }

  public GradientPaintEx getGradientPaint(float width, float height) {
    if ((width != 0) && (height != 0)) {
      if (drawRect == null) {
        drawRect = new Rectangle();
      }

      if (paintRect == null) {
        paintRect = new Rectangle();
      }

      paintRect.setRect(0, 0, width, height);

      if ((gPaint == null) || (drawRect.width != paintRect.width) || (drawRect.height != paintRect.height)) {
        UIRectangle r = calculateLinearRect(width, height);
        UIColor[] colors = getGradientColors(getBackgroundColor());
        boolean cyclic = false;

        switch (gradientType) {
          case LINEAR_REPEAT:
          case LINEAR_REFLECT:
            cyclic = true;
            break;

          default:
            break;
        }

        gPaint = new GradientPaintEx(r.x, r.y, colors[0], r.x + r.width, r.y + r.height, colors[1], cyclic);
        drawRect.setBounds(paintRect);
      }

      return gPaint;
    }

    return null;
  }

  public Paint getPaint() {
    if (isGradientPaintEnabled()) {
      if (paintRect == null) {
        paintRect = new Rectangle(0, 0, 50, 50);
      }

      return createGradient(0, 0, 50, 50);
    }

    return getBackgroundColor();
  }

  public Paint getPaint(float x, float y, float width, float height) {
    if (isGradientPaintEnabled() && (width != 0) && (height != 0)) {
      if (drawRect == null) {
        drawRect = new Rectangle();
      }
      if (paintRect == null) {
        paintRect = new Rectangle();
      }
      paintRect.setRect(x, y, width, height);

      if ((paint == null) || !paintRect.equals(drawRect)) {
        paint = createGradient(x, y, width, height);
        drawRect.setBounds(paintRect);
      }

      return paint;
    }

    return getBackgroundColor();
  }

  @Override
  public Paint getPaintEx(float width, float height) {
    return getPaint(0, 0, width, height);
  }

  @Override
  protected void clearCache() {
    super.clearCache();
    paint = null;
    gPaint=null;
  }

  public static class GradientPaintEx extends GradientPaint {
    public Object pointer;

    public GradientPaintEx(float x1, float y1, Color color1, float x2, float y2, Color color2, boolean cyclic) {
      super(x1, y1, color1, x2, y2, color2, cyclic);
    }

    public GradientPaintEx(float x1, float y1, Color color1, float x2, float y2, Color color2) {
      super(x1, y1, color1, x2, y2, color2);
    }

    public GradientPaintEx(Point2D pt1, Color color1, Point2D pt2, Color color2, boolean cyclic) {
      super(pt1, color1, pt2, color2, cyclic);
    }

    public GradientPaintEx(Point2D pt1, Color color1, Point2D pt2, Color color2) {
      super(pt1, color1, pt2, color2);
    }

  }
}
