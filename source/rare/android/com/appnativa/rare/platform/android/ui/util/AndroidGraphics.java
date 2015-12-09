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

package com.appnativa.rare.platform.android.ui.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.GraphicsComposite;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformImage;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iTransform;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

/**
 *
 * @author Don DeCoteau
 */
public class AndroidGraphics implements iPlatformGraphics {
  UIColor                    color;
  boolean                    disposed;
  UIFont                     font;
  Canvas                     graphics;
  Paint                      paint;
  int                        saveCount;
  private Bitmap             bitmap;
  private iPlatformComponent component;
  private boolean            componentPainterClipped;
  private iComposite         composite;
  private UIImage            image;
  private UIStroke           lineStroke;
  private iPlatformPaint     platformPaint;
  private int                rotation;
  private iTransform         transform;
  private View               view;

  public AndroidGraphics(Bitmap bitmap) {
    this(new Canvas(bitmap), (View) null);
    this.bitmap = bitmap;
  }

  public AndroidGraphics(Canvas g, View view) {
    paint = new Paint();
    paint.setAntiAlias(true);
    set(g, view);
  }

  public AndroidGraphics(Canvas g, Component c) {
    paint = new Paint();
    paint.setAntiAlias(true);
    set(g, c.getView());
    component = c;
  }

  public AndroidGraphics(Canvas g, View view, Paint p) {
    paint = p;
    set(g, view);
  }

  public void clear() {
    if ((graphics != null) && (graphics.getSaveCount() > saveCount)) {
      graphics.restoreToCount(saveCount);
    }

    graphics  = null;
    view      = null;
    component = null;
  }

  public void clearRect(float x, float y, float width, float height) {
    clearRect(null, x, y, width, height);
  }

  public void clearRect(UIColor bg, float x, float y, float width, float height) {
    Canvas g = graphics;

    g.save(Canvas.CLIP_SAVE_FLAG);
    g.clipRect((int) x, (int) y, (int) (x + width), (int) (y + height));

    if (bg == null) {
      g.drawColor(0, Mode.CLEAR);
    } else if (bg.isSimpleColor()) {
      g.drawColor(bg.getColor());
    } else {
      Drawable d = bg.getDrawable();

      d.setBounds((int) x, (int) y, (int) (x + width), (int) (y + height));
      d.draw(g);
    }

    g.restore();
  }

  public void clip(Path path) {
    graphics.clipPath(path);
  }

  public void clipRect(float x, float y, float width, float height) {
    graphics.clipRect((int) x, (int) y, (int) (x + width), (int) (y + height));
  }

  public void clipRect(float x, float y, float width, float height, Op op) {
    graphics.clipRect((int) x, (int) y, (int) (x + width), (int) (y + height), getRegionOp(op));
  }

  public void clipShape(iPlatformShape shape) {
    RectF r = shape.getRectangle();

    if (r != null) {
      graphics.clipRect(r);
    } else {
      graphics.clipPath(shape.getPath());
    }
  }

  public void clipShape(iPlatformShape shape, Op op) {
    RectF r = shape.getRectangle();

    if (r != null) {
      graphics.clipRect(r, getRegionOp(op));
    } else {
      graphics.clipPath(shape.getPath(), getRegionOp(op));
    }
  }

  public boolean didComponentPainterClip() {
    return componentPainterClipped;
  }

  public void dispose() {
    graphics = null;
    disposed = true;
  }

  public void drawChars(char[] data, int offset, int length, float x, float y, float height) {
    y += height;
    paint.setStyle(Paint.Style.FILL);
    graphics.drawText(data, offset, length, (float) x, (float) y, paint);
  }

  public void drawImage(iPlatformImage img, float x, float y) {
    Bitmap bmp = img.getBitmap();

    if (bmp == null) {
      return;
    }

    paint.setStyle(Paint.Style.STROKE);
    graphics.drawBitmap((Bitmap) bmp, (float) x, (float) y, paint);
  }

  public void drawImage(iPlatformImage img, float x, float y, iComposite composite) {
    Bitmap bmp = img.getBitmap();

    if (bmp == null) {
      return;
    }

    int      oa = paint.getAlpha();
    Xfermode xm = paint.getXfermode();

    if (composite != null) {
      setCompositeEx(composite);
    }

    paint.setStyle(Paint.Style.STROKE);
    graphics.drawBitmap(bmp, (float) x, (float) y, paint);
    paint.setXfermode(xm);
    paint.setAlpha(oa);
  }

  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iComposite composite) {
    drawImage(img, src, dst, null, composite);
  }

  public void drawImage(iPlatformImage img, float x, float y, float width, float height) {
    Bitmap bmp = img.getBitmap();

    if (bmp == null) {
      return;
    }

    UIRectangle src = new UIRectangle(0, 0, bmp.getWidth(), bmp.getHeight());
    UIRectangle dst = new UIRectangle(x, y, width, height);

    drawImage(img, src, dst, null, null);
  }

  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, ScalingType scalingType,
                        iComposite composite) {
    Bitmap bmp = img.getBitmap();

    if (bmp == null) {
      return;
    }

    int      oa    = paint.getAlpha();
    int      flags = paint.getFlags();
    Xfermode xm    = paint.getXfermode();

    if (composite != null) {
      setCompositeEx(composite);
    }

    paint.setStyle(Paint.Style.STROKE);

    if (scalingType != null) {
      setImageInterpolation(scalingType, flags);
    }

    if (img.getBitmap() == null) {
      return;
    }

    graphics.drawBitmap(bmp, src.getRect(), dst.getRectangle(), paint);
    paint.setXfermode(xm);
    paint.setAlpha(oa);
    paint.setFlags(flags);
  }

  public void drawLine(float x1, float y1, float x2, float y2) {
    paint.setStyle(Paint.Style.STROKE);
//    if(x1==x2) {
//      if(y2>y1) {
//              y2++;
//      }
//      else {
//              y2--;
//      }
//    }
//    else if(y1==y2) {
//      if(x2>x1) {
//              x2++;
//      }
//      else{
//              x2--;
//      }
//    }
//    else {
//      if(x2>x1) {
//              x2++;
//      }
//      else{
//              x2--;
//      }
//      if(y2>y1) {
//              y2++;
//      }
//      else {
//              y2--;
//      }
//    }
    graphics.drawLine((float) x1, (float) y1, (float) x2, (float) y2, paint);
  }

  public void drawRect(float x, float y, float width, float height) {
    paint.setStyle(Paint.Style.STROKE);
    graphics.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), paint);
  }

  public void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    paint.setStyle(Paint.Style.STROKE);
    graphics.drawRoundRect(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)),
                           (float) arcWidth, (float) arcHeight, paint);
  }

  public void drawShape(iPlatformShape shape) {
    handleShape(shape, 0, 0, true);
  }

  public void drawShape(Path path) {
    paint.setStyle(Paint.Style.STROKE);
    graphics.drawPath(path, paint);
  }

  public void drawShape(iPlatformShape shape, float x, float y) {
    handleShape(shape, x, y, true);
  }

  public void drawString(String str, float x, float y, float height) {
    y += height;
    paint.setStyle(Paint.Style.FILL);
    graphics.drawText(str, (float) x, (float) y, paint);
  }

  public void fill(Path path) {
    paint.setStyle(Paint.Style.FILL);
    graphics.drawPath(path, paint);
  }

  public void fillRect(float x, float y, float width, float height) {
    paint.setStyle(Paint.Style.FILL);
    graphics.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), paint);
  }

  public void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    paint.setStyle(Paint.Style.FILL);
    graphics.drawRoundRect(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)),
                           (float) arcWidth, (float) arcHeight, paint);
  }

  public void fillShape(iPlatformShape shape) {
    handleShape(shape, 0, 0, false);
  }

  public void fillShape(iPlatformShape shape, float x, float y) {
    handleShape(shape, x, y, false);
  }

  public static AndroidGraphics fromGraphics(Canvas g, View c, AndroidGraphics graphics) {
    if (graphics == null) {
      graphics = new AndroidGraphics(g, c);
    } else {
      graphics.set(g, c);
    }

    return graphics;
  }

  public void restoreState() {
    if (saveCount < graphics.getSaveCount()) {
      graphics.restore();
    } else {
      graphics.getSaveCount();
    }
  }

  public void restoreToState(int state) {
    graphics.restoreToCount(state);
  }

  public void rotate(int degrees) {
    graphics.rotate(degrees);
  }

  public int saveState() {
    return graphics.save();
  }

  public void scale(float sx, float sy) {
    graphics.scale(sx, sy);
  }

  public float stringWidth(String text) {
    return (float) Math.ceil(paint.measureText(text));
  }

  public String toString() {
    return graphics.toString();
  }

  public void translate(float x, float y) {
    graphics.translate(x, y);
  }

  public void set(Canvas g, View view) {
    this.view     = view;
    graphics      = g;
    component     = null;
    font          = null;
    color         = null;
    platformPaint = null;
    lineStroke    = null;
    component     = null;

    if (g != null) {
      saveCount               = g.getSaveCount();
      componentPainterClipped = false;
    }
  }

  public void setColor(UIColor c) {
    if (color != c) {
      color = c;
      paint.setColor(c.getColor());

      if ((platformPaint != null) && (platformPaint.getShader() == paint.getShader())) {
        paint.setShader(null);
      }

      platformPaint = null;
    }
  }

  public void setComponentPainterClipped(boolean b) {
    componentPainterClipped = b;
  }

  @Override
  public void setComposite(iComposite composite) {
    if (composite == null) {
      paint.setAlpha(255);
      paint.setXfermode(null);
    } else {
      setCompositeEx(composite);
    }

    this.composite = composite;
  }

  public void setFont(UIFont f) {
    if (font != f) {
      font = f;

      if (f != null) {
        f.setupPaint(paint);
      }
    }
  }

  public void setPaint(iPlatformPaint p) {
    platformPaint = p;
    color         = null;

    if (p != null) {
      if (p.isColor()) {
        paint.setShader(null);
        paint.setColor(p.getColor());
      } else {
        paint.setShader(p.getShader());
      }
    }
  }

  public void setRenderingOptions(boolean anti_aliasing, boolean speed) {
    paint.setAntiAlias(anti_aliasing);

    int flags = paint.getFlags();

    if (speed) {
      paint.setFlags(flags | Paint.FILTER_BITMAP_FLAG);
    } else if ((flags & Paint.FILTER_BITMAP_FLAG) != 0) {
      paint.setFlags(flags - Paint.FILTER_BITMAP_FLAG);
    }
  }

  public void setRotation(int rotation) {
    this.rotation = rotation;
    graphics.rotate(rotation);
  }

  public void setStroke(UIStroke stroke) {
    lineStroke = stroke;
    AndroidHelper.setLineStroke(stroke, paint);
  }

  public void setStrokeWidth(float f) {
    paint.setStrokeWidth((float) f);
  }

  public void setTransform(iTransform transform) {
    if (transform == null) {
      transform = new Transform();
    }

    graphics.setMatrix((Transform) transform.getPlatformTransform());
  }

  public UIImage getBackingImage() {
    if (bitmap != null) {
      if (image == null) {
        image = new UIImage(bitmap);
      }
    }

    return image;
  }

  public Canvas getCanvas() {
    return graphics;
  }

  @Override
  public iPlatformShape getClip() {
    return getClipBounds();
  }

  public UIRectangle getClipBounds() {
    Rect r = graphics.getClipBounds();

    return new UIRectangle(r.left, r.right, r.width(), r.height());
  }

  public UIColor getColor() {
    if (color == null) {
      color = UIColor.BLACK;
    }

    return color;
  }

  public iPlatformComponent getComponent() {
    if ((component == null) && (view != null)) {
      component = Component.findFromView(view);
    }

    return component;
  }

  @Override
  public iComposite getComposite() {
    return composite;
  }

  public UIFont getFont() {
    if (font == null) {
      return UIFontHelper.getDefaultFont();
    }

    return font;
  }

  public float getFontAscent() {
    return (float) Math.ceil(-paint.ascent());
  }

  public float getFontDescent() {
    return (float) Math.ceil(paint.descent());
  }

  public float getFontHeight() {
    return (float) Math.ceil(-paint.ascent() + paint.descent());
  }

  public iPlatformPaint getPaint() {
    if (!(platformPaint instanceof AndroidPaint)) {
      platformPaint = new AndroidPaint();
    }

    AndroidPaint p = (AndroidPaint) platformPaint;
    Shader       s = paint.getShader();

    if (s != null) {
      p.setShader(s);
    } else {
      p.setColor(paint.getColor());
    }

    return platformPaint;
  }

  public Paint getPaintEx() {
    return paint;
  }

  public int getRotation() {
    return rotation;
  }

  public UIStroke getStroke() {
    if (lineStroke != null) {
      return lineStroke;
    }

    UIStroke.Join join;
    UIStroke.Cap  cap;

    switch(paint.getStrokeJoin()) {
      case BEVEL :
        join = UIStroke.Join.BEVEL;

        break;

      case ROUND :
        join = UIStroke.Join.ROUND;

        break;

      default :
        join = UIStroke.Join.MITER;

        break;
    }

    switch(paint.getStrokeCap()) {
      case SQUARE :
        cap = UIStroke.Cap.SQUARE;

        break;

      case ROUND :
        cap = UIStroke.Cap.ROUND;

        break;

      default :
        cap = UIStroke.Cap.BUTT;

        break;
    }

    return new UIStroke(paint.getStrokeWidth(), cap, join, paint.getStrokeMiter());
  }

  public float getStrokeWidth() {
    return paint.getStrokeWidth();
  }

  @SuppressWarnings("deprecation")
  public iTransform getTransform() {
    if (this.transform == null) {
      if(view!=null) {
        Matrix m=view.getMatrix();
        if(m!=null) {
          transform=new Transform(m);
        }
      }
      else {
        this.transform = new Transform();
        graphics.getMatrix((Transform) transform);
      }
    }

    return transform;
  }

  public View getView() {
    return view;
  }

  public boolean isDisposed() {
    return disposed;
  }

  void setImageInterpolation(ScalingType scalingType, int flags) {
    switch(scalingType) {
      case BICUBIC :
      case BICUBIC_CACHED :
      case PROGRESSIVE_BICUBIC :
      case BILINEAR :
      case BILINEAR_CACHED :
      case PROGRESSIVE_BILINEAR :
        paint.setFlags(flags | Paint.FILTER_BITMAP_FLAG);

        break;

      case NEAREST_NEIGHBOR :
      case PROGRESSIVE_NEAREST_NEIGHBOR :
      case PROGRESSIVE_NEAREST_NEIGHBOR_CACHED :
      default :
        if ((flags & Paint.FILTER_BITMAP_FLAG) != 0) {
          paint.setFlags(flags - Paint.FILTER_BITMAP_FLAG);
        }

        break;
    }
  }

  protected Region.Op getRegionOp(Op op) {
    switch(op) {
      case DIFFERENCE :
        return Region.Op.DIFFERENCE;

      case REPLACE :
        return Region.Op.REPLACE;

      case UNION :
        return Region.Op.UNION;

      default :
        return Region.Op.INTERSECT;
    }
  }

  private void handleShape(iPlatformShape shape, float x, float y, boolean draw) {
    if ((x != 0) || (y != 0)) {
      graphics.translate(x, y);
    }

    if (draw) {
      paint.setStyle(Paint.Style.STROKE);
    } else {
      paint.setStyle(Paint.Style.FILL);
    }

    RectF r = shape.getRectangle();

    if (r != null) {
      graphics.drawRect(r, paint);
    } else {
      graphics.drawPath(shape.getPath(), paint);
    }

    if ((x != 0) || (y != 0)) {
      graphics.translate(-x, -y);
    }
  }

  private void setCompositeEx(iComposite composite) {
    PorterDuffXfermode pdm = null;

    if (composite instanceof GraphicsComposite) {
      GraphicsComposite gc = (GraphicsComposite) composite;

      pdm = (PorterDuffXfermode) gc.getPlatformComposite();

      if (pdm == null) {
        if (gc.getCompositeType() == CompositeType.COPY) {
          Rect r = graphics.getClipBounds();

          clearRect(r.left, r.top, r.width(), r.height());
          pdm = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        } else {
          pdm = new PorterDuffXfermode(PainterUtils.getPorterDuffMode(composite.getCompositeType()));
          gc.setPlatformComposite(pdm);
        }
      }
    } else {
      pdm = null;
    }

    paint.setXfermode(pdm);
    //paint.setAlpha(((int) composite.getAlpha() * 255) % 256);
  }
}
