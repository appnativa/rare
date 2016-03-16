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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UIImagePainter;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CanvasRenderingContext2D extends aCanvasRenderingContext2D {
  public CanvasRenderingContext2D(iCanvas canvas) {
    super(canvas);
  }

  @Override
  public boolean canUseLayer() {
    return false;
  }

  @Override
  public boolean canUseMainLayer() {
    return false;
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
  public boolean isPointInPath(float x, float y) {
    UIPoint p = ((Transform) currentState.getTransform().getPlatformTransform()).transform(x, y);

    return currentPath.isPointInPath(p.x, p.y);
  }

  @Override
  public void updateModCount() {}

  @Override
  protected aCompositingGraphics createCompositingGraphics(iPlatformGraphics g, UIImage image) {
    return new CompositingGraphics(g, image);
  }

  @Override
  protected UIImage createImageIfNecessary(UIImage img, int width, int height) {
    if ((img == null) || (img.getWidth() != width) || (img.getHeight() != height)) {
      img = ImageUtils.createCompatibleImage(width, height);
    }

    return img;
  }

  public class CanvasGradient extends UIBackgroundPainter implements iCanvasGradient, Cloneable {
    TreeMap<Float, UIColor> stops = new TreeMap<Float, UIColor>();
    UIColor[]               colors;
    float[]                 distribution;
    float                   r0, r1;
    boolean                 radial;
    UIPoint                 start, end;

    public CanvasGradient(float x0, float y0, float x1, float y1) {
      start = new UIPoint(x0, y0);
      end   = new UIPoint(x1, y1);
    }

    public CanvasGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
      this.r0 = r0;
      this.r1 = r1;

      if ((r1 < 0) || (r0 < 0)) {
        throw new IllegalArgumentException("INDEX_SIZE_ERR");
      }

      start  = new UIPoint(x0, y0);
      end    = new UIPoint(x1, y1);
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

      Float f = Float.valueOf(offset);

      if (stops.containsKey(f)) {
        f = new Float(offset + (offset / 1000f));
      }

      stops.put(f, color);
      proxy = null;
    }

    @Override
    public iCanvasPaint cloneCopy() {
      return (iCanvasPaint) clone();
    }

    @Override
    public iPlatformPaint getPaint() {
      return this;
    }

    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
      if (start.equals(end) && (r0 == r1)) {
        return;
      } else {
        if (proxy == null) {
          Iterator<Map.Entry<Float, UIColor>> it = stops.entrySet().iterator();
          Map.Entry<Float, UIColor>           e;

          colors       = new UIColor[stops.size()];
          distribution = new float[stops.size()];

          int     i = 0;
          UIColor c;

          while(it.hasNext()) {
            e               = it.next();
            c               = e.getValue();
            distribution[i] = e.getKey();
            colors[i]       = c;
            i++;
          }

          proxy = createProxy(colors, distribution);
        }

        if (radial) {
          drawGradient(proxy, g.getContextRef(), start, r0, end, r1);
        } else {
          drawGradient(proxy, g.getContextRef(), start, end);
        }
      }
    }
  }


  public static class CanvasPattern implements Cloneable, iCanvasPattern {
    protected UIImagePainter paint   = null;
    boolean                  repeatY = true;
    boolean                  repeatX = true;
    protected iImageElement  image;

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
        paint = new UIImagePainter(image.getImage(), 255, getRenderType());
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

    RenderType getRenderType() {
      if (repeatX && repeatY) {
        return RenderType.TILED;
      }

      if (repeatX) {
        return RenderType.HORIZONTAL_TILE;
      }

      if (repeatX) {
        return RenderType.VERTICAL_TILE;
      }

      return RenderType.UPPER_LEFT;
    }
  }


  static class CompositingGraphics extends aCompositingGraphics {
    public CompositingGraphics(iPlatformGraphics g, UIImage img) {
      super(g, img);
    }

    @Override
    public Object getContextRef() {
      return null;
    }

    @Override
    public View getView() {
      return null;
    }
  }
}
