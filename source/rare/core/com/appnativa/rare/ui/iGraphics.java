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

import com.appnativa.rare.ui.painter.iImagePainter;

public interface iGraphics {
  public enum Op {
    DIFFERENCE, INTERSECT, REPLACE, UNION,
  }

  void clearRect(float x, float y, float width, float height);

  void clearRect(UIColor bg, float x, float y, float width, float height);

  void clipRect(float x, float y, float width, float height);

  void clipRect(float x, float y, float width, float height, Op op);

  void clipShape(iPlatformShape shape);

  void clipShape(iPlatformShape shape, Op op);

  boolean didComponentPainterClip();

  void dispose();

  void drawChars(char[] data, int offset, int length, float x, float y, float height);

  void drawImage(iPlatformImage img, float x, float y);

  void drawImage(iPlatformImage img, float x, float y, iComposite composite);

  void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iComposite composite);

  void drawImage(iPlatformImage img, float x, float y, float width, float height);

  void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iImagePainter.ScalingType scalingType,
                 iComposite composite);

  void drawLine(float x1, float y1, float x2, float y2);

  void drawRect(float x, float y, float width, float height);

  void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

  void drawShape(iPlatformShape path, float x, float y);

  void drawString(String str, float x, float y, float height);

  void fillRect(float x, float y, float width, float height);

  void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

  void fillShape(iPlatformShape path, float x, float y);

  void restoreState();

  void restoreToState(int state);

  void rotate(int degrees);

  int saveState();

  void scale(float sx, float sy);

  void translate(float x, float y);

  void setColor(UIColor c);

  void setComponentPainterClipped(boolean clipped);

  void setComposite(iComposite composite);

  void setFont(UIFont f);

  void setPaint(iPlatformPaint p);

  void setRenderingOptions(boolean anti_aliasing, boolean speed);

  void setRotation(int rotation);

  void setStroke(UIStroke stroke);

  void setStrokeWidth(float width);

  void setTransform(iTransform transform);

  iPlatformShape getClip();

  UIRectangle getClipBounds();

  UIColor getColor();

  iPlatformComponent getComponent();

  iComposite getComposite();

  UIFont getFont();

  iPlatformPaint getPaint();

  int getRotation();

  UIStroke getStroke();

  float getStrokeWidth();

  iTransform getTransform();

  boolean isDisposed();
}
