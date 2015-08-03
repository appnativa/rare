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
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformImage;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iTransform;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

public abstract class aCompositingGraphics implements iPlatformGraphics {
  protected iPlatformGraphics graphics;
  protected iPlatformGraphics cgraphics;
  protected UIImage           compositeImage;
  protected int               height;
  protected int               width;

  public aCompositingGraphics(iPlatformGraphics g, UIImage img) {
    graphics       = g;
    cgraphics      = img.createGraphics();
    compositeImage = img;
    width          = img.getWidth();
    height         = img.getHeight();
    graphics.setRenderingOptions(true, false);
    cgraphics.setRenderingOptions(true, false);
  }

  @Override
  public void clearRect(float x, float y, float width, float height) {
    cgraphics.clearRect(x, y, width, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void clearRect(UIColor bg, float x, float y, float width, float height) {
    cgraphics.clearRect(bg, x, y, width, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public iPlatformComponent getComponent() {
    return graphics.getComponent();
  }

  @Override
  public void clipRect(float x, float y, float width, float height) {
    graphics.clipRect(x, y, width, height);
  }

  @Override
  public void clipRect(float x, float y, float width, float height, Op op) {
    graphics.clipRect(x, y, width, height, op);
  }

  @Override
  public void clipShape(iPlatformShape shape) {
    graphics.clipShape(shape);
  }

  @Override
  public void clipShape(iPlatformShape shape, Op op) {
    graphics.clipShape(shape, op);
  }

  @Override
  public boolean didComponentPainterClip() {
    return graphics.didComponentPainterClip();
  }

  @Override
  public void dispose() {
    graphics = null;
    cgraphics.dispose();
    cgraphics      = null;
    compositeImage = null;
  }

  public iPlatformGraphics release() {
    iPlatformGraphics g = graphics;

    dispose();

    return g;
  }

  @Override
  public void drawChars(char[] data, int offset, int length, float x, float y, float height) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawChars(data, offset, length, x, y, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawImage(img, x, y);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y, iComposite composite) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawImage(img, x, y, composite);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iComposite composite) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawImage(img, src, dst, composite);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawImage(iPlatformImage img, float x, float y, float width, float height) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawImage(img, x, y, width, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, ScalingType scalingType,
                        iComposite composite) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawImage(img, src, dst, scalingType, composite);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawLine(float x1, float y1, float x2, float y2) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawLine(x1, y1, x2, y2);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawRect(float x, float y, float width, float height) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawRect(x, y, width, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawShape(iPlatformShape path, float x, float y) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawShape(path, x, y);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void drawString(String str, float x, float y, float height) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.drawString(str, x, y, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void fillRect(float x, float y, float width, float height) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.fillRect(x, y, width, height);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void fillShape(iPlatformShape path, float x, float y) {
    cgraphics.clearRect(0, 0, this.width, this.height);
    cgraphics.fillShape(path, x, y);
    graphics.drawImage(compositeImage, 0, 0);
  }

  @Override
  public void restoreState() {
    graphics.restoreState();
    cgraphics.restoreState();
  }

  @Override
  public void restoreToState(int state) {
    graphics.restoreToState(state);
    cgraphics.restoreToState(state);
  }

  @Override
  public void rotate(int degrees) {
    graphics.rotate(degrees);
  }

  @Override
  public int saveState() {
    return graphics.saveState();
  }

  @Override
  public void scale(float sx, float sy) {
    graphics.scale(sx, sy);
  }

  @Override
  public void translate(float x, float y) {
    graphics.translate(x, y);
  }

  @Override
  public void setColor(UIColor c) {
    graphics.setColor(c);
    cgraphics.setColor(c);
  }

  @Override
  public void setComponentPainterClipped(boolean clipped) {
    graphics.setComponentPainterClipped(clipped);
    cgraphics.setComponentPainterClipped(clipped);
  }

  @Override
  public void setComposite(iComposite composite) {
    graphics.setComposite(composite);
  }

  @Override
  public void setFont(UIFont f) {
    graphics.setFont(f);
    cgraphics.setFont(f);
  }

  @Override
  public void setPaint(iPlatformPaint p) {
    graphics.setPaint(p);
    cgraphics.setPaint(p);
  }

  @Override
  public void setRenderingOptions(boolean anti_aliasing, boolean speed) {
    graphics.setRenderingOptions(anti_aliasing, speed);
    cgraphics.setRenderingOptions(anti_aliasing, speed);
  }

  @Override
  public void setRotation(int rotation) {
    graphics.setRotation(rotation);
  }

  @Override
  public void setStroke(UIStroke stroke) {
    graphics.setStroke(stroke);
    cgraphics.setStroke(stroke);
  }

  @Override
  public void setStrokeWidth(float width) {
    graphics.setStrokeWidth(width);
    cgraphics.setStrokeWidth(width);
  }

  @Override
  public void setTransform(iTransform transform) {
    graphics.setTransform(transform);
  }

  @Override
  public iPlatformShape getClip() {
    return graphics.getClip();
  }

  @Override
  public UIRectangle getClipBounds() {
    return graphics.getClipBounds();
  }

  @Override
  public UIColor getColor() {
    return graphics.getColor();
  }

  @Override
  public iComposite getComposite() {
    return graphics.getComposite();
  }

  @Override
  public UIFont getFont() {
    return graphics.getFont();
  }

  @Override
  public iPlatformPaint getPaint() {
    return graphics.getPaint();
  }

  @Override
  public int getRotation() {
    return graphics.getRotation();
  }

  @Override
  public UIStroke getStroke() {
    return graphics.getStroke();
  }

  @Override
  public float getStrokeWidth() {
    return graphics.getStrokeWidth();
  }

  @Override
  public iTransform getTransform() {
    return graphics.getTransform();
  }

  @Override
  public boolean isDisposed() {
    return graphics.isDisposed();
  }
}
