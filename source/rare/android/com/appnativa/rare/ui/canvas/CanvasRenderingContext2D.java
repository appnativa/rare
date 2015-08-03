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

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.view.View;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.AppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.platform.android.ui.util.AndroidPaint;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CanvasRenderingContext2D extends aCanvasRenderingContext2D {
  AndroidGraphics graphics;

  public CanvasRenderingContext2D(iCanvas canvas) {
    super(canvas);
  }

  public iCanvasGradient createLinearGradient(float x0, float y0, float x1, float y1) {
    return new CanvasGradient(x0, y0, x1, y1);
  }

  public iCanvasPattern createPattern(iImageElement img, String repetition) {
    return new CanvasPattern(img, repetition);
  }

  public iCanvasGradient createRadialGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
    return new CanvasGradient(x0, y0, r0, x1, y1, r1);
  }

  @Override
  public void paint(View c, Canvas g, float x, float y, float width, float height, int orientation) {
    graphics = AndroidGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, width, height, orientation);
    graphics.clear();
  }

  @Override
  public Drawable getDrawable(View view) {
    return new BitmapDrawable(Platform.getAppContext().getActivity().getResources(), theImage.getBitmap());
  }

  @Override
  public Shader getShader(float width, float height) {
    return new BitmapShader(theImage.getBitmap(), TileMode.REPEAT, TileMode.REPEAT);
  }

  @Override
  public boolean isPointInPath(float x, float y) {
    float[] src = new float[] { x, y };
    float[] dst = new float[] { x, y };
    Matrix  m   = ((Matrix) currentState.getTransform().getPlatformTransform());

    m.mapPoints(dst, src);

    return currentPath.isPointInPath(dst[0], dst[1]);
  }

  @Override
  protected UIImage createImageIfNecessary(UIImage img, int width, int height) {
    Bitmap bi = ImageUtils.createImageIfNecessary((img == null)
            ? null
            : img.getBitmap(), width, height, Bitmap.Config.ARGB_8888);

    if ((img == null) || (bi != img.getBitmap())) {
      bi.setDensity(AppContext.getAndroidContext().getResources().getDisplayMetrics().densityDpi);
      img = new UIImage(bi);
    }

    return img;
  }

  public class CanvasGradient implements iCanvasGradient, Cloneable {
    TreeMap<Float, UIColor> stops = new TreeMap<Float, UIColor>();
    int[]                   colors;
    float[]                 distribution;
    iPlatformPaint          paint;
    boolean                 radial;
    float                   x0, y0, x1, y1, r0, r1;

    public CanvasGradient(float x0, float y0, float x1, float y1) {
      this.x0 = (float) x0;
      this.y0 = (float) y0;
      this.x1 = (float) x1;
      this.y1 = (float) y1;
    }

    public CanvasGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
      this.x0 = (float) x0;
      this.y0 = (float) y0;
      this.x1 = (float) x1;
      this.y1 = (float) y1;
      this.r0 = (float) r0;
      this.r1 = (float) r1;

      if ((r1 < 0) || (r0 < 0)) {
        throw new IllegalArgumentException("INDEX_SIZE_ERR");
      }

      radial = true;
    }

    public void addColorStop(float offset, String color) {
      addColorStop(offset, ColorUtils.getColor(color));
    }

    public void addColorStop(float offset, UIColor color) {
      if ((offset < 0) || (offset > 1)) {
        throw new IllegalArgumentException("INDEX_SIZE_ERR");
      }

      Float f = (Float) offset;

      if (stops.containsKey(f)) {
        f = new Float(offset + (offset / 1000f));
      }

      stops.put(f, color);
    }

    public Object clone() {
      try {
        return super.clone();
      } catch(CloneNotSupportedException ex) {
        throw new RuntimeException(ex);
      }
    }

    public iCanvasPaint cloneCopy() {
      return this;
    }

    public iPlatformPaint getPaint() {
      if (paint == null) {
        if ((x0 == x1) && (y0 == y1) && (r0 == r1)) {
          paint = ColorUtils.NULL_COLOR;
        } else {
          Iterator<Map.Entry<Float, UIColor>> it = stops.entrySet().iterator();
          Map.Entry<Float, UIColor>           e;

          colors       = new int[stops.size()];
          distribution = new float[stops.size()];

          int     i = 0;
          UIColor c;

          while(it.hasNext()) {
            e               = it.next();
            c               = e.getValue();
            distribution[i] = e.getKey();
            colors[i]       = c.getColor();
            i++;
          }

          Shader p;

          if (radial) {
            float r = (float) (r0 / Math.PI);

            p = new RadialGradient(x1 - r, y1 - r, r1 - r, colors, distribution, TileMode.CLAMP);
          } else {
            p = new LinearGradient(x0, y0, x1, y1, colors, distribution, TileMode.CLAMP);
          }

          paint = new AndroidPaint(p);
        }
      }

      return paint;
    }
  }


  public static class CanvasPattern implements Cloneable, iCanvasPattern {
    AndroidPaint  paint = null;
    iImageElement image;
    boolean       repeatY = true;
    boolean       repeatX = true;

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

    public Object clone() {
      try {
        return super.clone();
      } catch(CloneNotSupportedException ex) {
        throw new RuntimeException(ex);
      }
    }

    public iCanvasPaint cloneCopy() {
      return this;
    }

    public iPlatformPaint getPaint() {
      if (paint == null) {
        paint = new AndroidPaint(new BitmapShader(image.getImage().getBitmap(), getTileModeX(), getTileModeY()));
      }

      return paint;
    }

    public boolean isRepeatX() {
      return repeatX;
    }

    public boolean isRepeatXorY() {
      return repeatX || repeatY;
    }

    public boolean isRepeatY() {
      return repeatY;
    }

    TileMode getTileModeX() {
      return repeatX
             ? TileMode.REPEAT
             : TileMode.CLAMP;
    }

    TileMode getTileModeY() {
      return repeatY
             ? TileMode.REPEAT
             : TileMode.CLAMP;
    }
  }


  @Override
  protected aCompositingGraphics createCompositingGraphics(iPlatformGraphics g, UIImage image) {
    return new CompositingGraphics(g, image);
  }

  static class CompositingGraphics extends aCompositingGraphics {
    public CompositingGraphics(iPlatformGraphics g, UIImage img) {
      super(g, img);
    }

    @Override
    public void set(Canvas canvas, View view) {}

    @Override
    public Canvas getCanvas() {
      return null;
    }

    @Override
    public View getView() {
      return null;
    }

    @Override
    public UIImage getBackingImage() {
      return compositeImage;
    }
  }
}
