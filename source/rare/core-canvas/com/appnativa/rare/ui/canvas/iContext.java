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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 * HTML 5 compatible context
 *
 * see  http://dev.w3.org/html5/canvas-api/canvas-2d-api.html
 *
 * @author Don DeCoteau
 */
public interface iContext {
  public static enum TextAlign {
    left, start, right, end, center
  }

  public static enum TextBaseline {
    top, hanging, middle, alphabetic, ideographic, bottom,
  }

  void arc(float x, float y, float radius, float startAngle, float endAngle, boolean antiClockwise);

  void arcTo(float x0, float y0, float x1, float y1, float radius);

  void beginPath();

  void bezierCurveTo(float cp1x, float cp1y, float cp2x, float cp2y, float x, float y);

  void blur();

  void clear();

  void clearRect(float x, float y, float width, float height);

  void clip();

  void closePath();

  iImageData createImageData(iImageData imagedata);

  iImageData createImageData(int sw, int sh);

  iCanvasGradient createLinearGradient(float x0, float y0, float x1, float y1);

  iCanvasPattern createPattern(iImageElement img, String repetition);

  iCanvasGradient createRadialGradient(float x0, float y0, float r0, float x1, float y1, float r1);

  void createReflection(int y, int height, float opacity, int gap);

  void dispose();

  void drawImage(iImageElement img, float x, float y);

  void drawImage(UIImage img, float x, float y);

  void drawImage(iImageElement img, float x, float y, float width, float height);

  void drawImage(UIImage img, float x, float y, float width, float height);

  void drawImage(iImageElement img, float sx, float sy, float swidth, float sheight, float dx, float dy, float dwidth,
                 float dheight);

  void drawImage(UIImage img, float sx, float sy, float swidth, float sheight, float dx, float dy, float dwidth,
                 float dheight);

  void fill();

  void fill(iPlatformShape shape);

  void fillRect(float x, float y, float width, float height);

  void fillRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

  void fillText(String text, float x, float y);

  void fillText(String text, float x, float y, int maxWidth);

  void lineTo(float x, float y);

  iTextMetrics measureText(String text);

  void moveTo(float x, float y);

  void putImageData(iImageData imagedata, int dx, int dy);

  void putImageData(iImageData imagedata, int dx, int dy, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight);

  void quadraticCurveTo(float cpx, float cpy, float x, float y);

  void rect(float startX, float startY, float width, float height);

  void render(iPlatformGraphics g);

  void restore();

  void rotate(float angle);

  void save();

  void scale(float x, float y);

  void stroke();

  void stroke(iPlatformShape shape);

  void strokeRect(float x, float y, float width, float height);

  void strokeRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

  void strokeText(String text, float x, float y);

  void strokeText(String text, float x, float y, int maxWidth);

  void transform(float m11, float m12, float m21, float m22, float dx, float dy);

  void translate(float x, float y);

  void setFillStyle(iCanvasGradient grad);

  void setFillStyle(String UIColor);

  void setFillStyle(UIColor color);

  void setFont(String font);

  void setGlobalAlpha(float alpha);

  void setGlobalCompositeOperation(String globalCompositeOperation);

  void setLineCap(String lineCap);

  void setLineJoin(String lineJoin);

  void setLineWidth(float width);

  void setMiterLimit(float miterLimit);

  /**
   * @param scalingType the scalingType to set
   */
  void setScalingType(ScalingType scalingType);

  void setSize(int width, int height);

  void setStrokeStyle(iCanvasGradient grad);

  void setStrokeStyle(String color);

  void setStrokeStyle(UIColor color);

  void setTextAlign(String value);

  void setTextBaseline(String value);

  void setTransform(float m11, float m12, float m21, float m22, float dx, float dy);

  iCanvas getCanvas();

  String getFont();

  float getGlobalAlpha();

  String getGlobalCompositeOperation();

  UIImage getImage(boolean copy);

  iImageData getImageData(int sx, int sy, int sw, int sh);

  String getLineCap();

  String getLineJoin();

  float getLineWidth();

  float getMiterLimit();

  iPlatformPainter getPainter();

  /**
   * @return the scalingType
   */
  ScalingType getScalingType();

  String getTextAlign();

  String getTextBaseline();

  boolean isPointInPath(float x, float y);

  public static interface iCanvasGradient extends iCanvasPaint {
    void addColorStop(float offset, UIColor UIColor);
  }


  public static interface iCanvasPaint {
    iCanvasPaint cloneCopy();

    iPlatformPaint getPaint();
  }


  public static interface iCanvasPattern extends iCanvasPaint {
    boolean isRepeatX();

    boolean isRepeatXorY();

    boolean isRepeatY();
  }


  public static interface iImageData {
    void updateImage(UIImage image, int dx, int dy, int dirtyWidth, int dirtyHeight);

    Uint8ClampedArray getData();

    int getHeight();

    int getWidth();
  }


  public static interface iImageElement {
    UIImage getImage();

    UIImage getImage(int x, int y, int width, int height);
  }


  public static interface iTextMetrics {
    int getWidth();
  }
}
