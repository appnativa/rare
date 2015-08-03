/*
 * @(#)CanvasRenderingContext2D.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.canvas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingPaint;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

public class CanvasRenderingContext2D extends aCanvasRenderingContext2D {
  SwingGraphics graphics;

  public CanvasRenderingContext2D(iCanvas canvas) {
    super(canvas);
  }

  @Override
  public iCanvasGradient createLinearGradient(float x0, float y0, float x1, float y1) {
    return new CanvasGradient(x0, y0, x1, y1);
  }

  @Override
  public iCanvasPattern createPattern(iImageElement img, String repetition) {
    return new CanvasPattern(img, repetition);
  }

  @Override
  public iCanvasGradient createRadialGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
    return new CanvasGradient(x0, y0, r0, x1, y1, r1);
  }

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    graphics = SwingGraphics.fromGraphics(g, c, graphics);
    paint(graphics, 0, 0, c.getWidth(), c.getHeight(), UNKNOWN);
    graphics.clear();
  }

  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    graphics = SwingGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  @Override
  public boolean isPointInPath(float x, float y) {
    Point2D p = new Point2D.Double(x, y);

    ((AffineTransform) currentState.getTransform().getPlatformTransform()).transform(p, p);

    return currentPath.getPath().contains(p);
  }

  @Override
  protected aCompositingGraphics createCompositingGraphics(iPlatformGraphics g, UIImage image) {
    return new CompositingGraphics(g, image);
  }

  @Override
  protected UIImage createImageIfNecessary(UIImage img, int width, int height) {
    BufferedImage bi = (img == null)
                       ? null
                       : img.getBufferedImage();

    bi = Java2DUtils.createImageIfNecessary(bi, width, height);    //BufferedImage.TYPE_4BYTE_ABGR);

    if ((img == null) || (bi != img.getBufferedImage())) {
      img = new UIImage(bi);
    }

    return img;
  }

  public class CanvasGradient implements iCanvasGradient, Cloneable {
    TreeMap<Float, Color> stops = new TreeMap<Float, Color>();
    Color[]               colors;
    float[]               distribution;
    iPlatformPaint        paint;
    boolean               radial;
    float                 x0, y0, x1, y1, r0, r1;

    public CanvasGradient(float x0, float y0, float x1, float y1) {
      this.x0 = x0;
      this.y0 = y0;
      this.x1 = x1;
      this.y1 = y1;
    }

    public CanvasGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
      this.x0 = x0;
      this.y0 = y0;
      this.x1 = x1;
      this.y1 = y1;
      this.r0 = r0;
      this.r1 = r1;

      if ((r1 < 0) || (r0 < 0)) {
        throw new IllegalArgumentException("INDEX_SIZE_ERR");
      }

      radial = true;
    }

    public void addColorStop(float offset, String color) {
      addColorStop(offset, ColorUtils.getColor(color));
    }

    @Override
    public void addColorStop(float offset, UIColor color) {
      if ((offset < 0) || (offset > 1)) {
        throw new IllegalArgumentException("INDEX_SIZE_ERR");
      }

      Float f = offset;

      if (stops.containsKey(f)) {
        f = new Float(offset + (offset / 1000f));
      }

      stops.put(f, color);
    }

    @Override
    public Object clone() {
      try {
        return super.clone();
      } catch(CloneNotSupportedException ex) {
        throw new RuntimeException(ex);
      }
    }

    @Override
    public iCanvasPaint cloneCopy() {
      return this;
    }

    @Override
    public iPlatformPaint getPaint() {
      if (paint == null) {
        if ((x0 == x1) && (y0 == y1) && (r0 == r1)) {
          paint = ColorUtils.NULL_COLOR;
        } else {
          Iterator<Map.Entry<Float, Color>> it = stops.entrySet().iterator();
          Map.Entry<Float, Color>           e;

          colors       = new Color[stops.size()];
          distribution = new float[stops.size()];

          int   i = 0;
          Color c;

          while(it.hasNext()) {
            e               = it.next();
            c               = e.getValue();
            distribution[i] = e.getKey();
            colors[i]       = c;
            i++;
          }

          Paint p;

          if (radial) {
            float r = (float) (r0 / Math.PI);

            p = new RadialGradientPaint(x1 - r, y1 - r, r1 - r, x0, y0, distribution, colors, CycleMethod.NO_CYCLE);
          } else {
            p = new LinearGradientPaint(x0, y0, x1, y1, distribution, colors, CycleMethod.NO_CYCLE);
          }

          paint = new SwingPaint(p);
        }
      }

      return paint;
    }
  }


  public static class CanvasPattern implements Cloneable, iCanvasPattern {
    SwingPaint    paint = null;
    iImageElement image;
    boolean                  repeatY = true;
    boolean                  repeatX = true;

    public CanvasPattern(iImageElement img, String repetition) {
      image = img;
      if ("no-repeat".equals(repetition)) {
        repeatX = false;
        repeatY = false;
      } else if ("repeat-x".equals(repetition)) {
        repeatX = true;
      } else if ("repeat-y".equals(repetition)) {
        repeatY = true;
      }
    }

    @Override
    public Object clone() {
      try {
        return super.clone();
      } catch(CloneNotSupportedException ex) {
        throw new RuntimeException(ex);
      }
    }

    @Override
    public iCanvasPaint cloneCopy() {
      return this;
    }

    @Override
    public iPlatformPaint getPaint() {
      if (paint == null) {
        Rectangle2D tr = new Rectangle2D.Double(0, 0, image.getImage().getWidth(), image.getImage().getHeight());

        paint = new SwingPaint(new TexturePaint(image.getImage().getBufferedImage(), tr));
      }

      return paint;
    }

    @Override
    public boolean isRepeatX() {
      return repeatX;
    }

    @Override
    public boolean isRepeatXorY() {
      return repeatX || repeatY;
    }

    @Override
    public boolean isRepeatY() {
      return repeatY;
    }
  }


  static class CompositingGraphics extends aCompositingGraphics {
    public CompositingGraphics(iPlatformGraphics g, UIImage img) {
      super(g, img);
    }

    @Override
    public void set(Graphics g, iPlatformComponent c) {}

    @Override
    public void setComponent(iPlatformComponent c) {}

    @Override
    public Graphics2D getGraphics() {
      return null;
    }

    @Override
    public Component getView() {
      return null;
    }
  }
}
