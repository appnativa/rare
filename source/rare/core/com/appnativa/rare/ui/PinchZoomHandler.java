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

import com.appnativa.util.SNumber;

public class PinchZoomHandler {

  /**
   * Code adapted from https://groups.google.com/forum/#!topic/phonegap/QpXAzVo_Fi8
   */
  float   baseScale;
  float   scale;
  float   centerPointStartX;
  float   centerPointStartY;
  float   percentageOfItemAtPinchPointX;
  float   percentageOfItemAtPinchPointY;
  float   currentOffsetX;
  float   currentOffsetY;
  float   newOffsetX;
  float   newOffsetY;
  float   currentWidth;
  float   currentHeight;
  float   baseWidth;
  float   baseHeight;
  float   newWidth;
  float   newHeight;
  float   startSpan;
  float   currentSpan;
  float   previousSpan;
  float   minScale;
  float   maxScale;
  boolean inProgress;

  public PinchZoomHandler(float min, float max) {
    minScale = min;
    maxScale = max;
  }

  public void resetRange(float min, float max) {
    minScale = min;
    maxScale = max;
  }

  public void resetBounds(UIRectangle currentBounds, float baseWidth, float baseHeight, float baseScale) {
    newOffsetX      = currentOffsetX = currentBounds.x;
    newOffsetY      = currentOffsetY = currentBounds.y;
    newWidth        = currentWidth = currentBounds.width;
    newHeight       = currentHeight = currentBounds.height;
    this.baseWidth  = baseWidth;
    this.baseHeight = baseHeight;
    this.baseScale  = baseScale;
    this.scale      = baseScale;
  }

  public float getScaledX() {
    return newOffsetX;
  }

  public float getScaledY() {
    return newOffsetX;
  }

  public float getScaledWidth() {
    return newWidth;
  }

  public float getScaledHeight() {
    return newHeight;
  }

  public float getScale() {
    return scale;
  }

  public void doubleTabScale(float x, float y, float scale) {
    scaleStart(x, y);
    scale(x, y, scale);
    scaleEnd(x, y);
  }

  public void scaleStart(float focusX, float focusY) {
    inProgress                    = true;
    centerPointStartX             = focusX;
    centerPointStartY             = focusY;
    percentageOfItemAtPinchPointX = (centerPointStartX - currentOffsetX) / currentWidth;
    percentageOfItemAtPinchPointY = (centerPointStartY - currentOffsetY) / currentHeight;
  }

  public boolean scale(float focusX, float focusY, float scale) {
    scale = baseScale * scale;

    if (scale < minScale) {
      scale = minScale;
    }

    if (scale > maxScale) {
      scale = maxScale;
    }

    if (SNumber.isEqual(scale, this.scale)) {
      return false;
    }

    this.scale = scale;

    // Get the point between the two touches, relative to upper-left corner of image
    float centerPointEndX = focusX;
    float centerPointEndY = focusY;

    newWidth  = baseWidth * scale;
    newHeight = baseHeight * scale;

    // This is the translation due to pinch-zooming
    float translateFromZoomingX = (currentWidth - newWidth) * percentageOfItemAtPinchPointX;
    float translateFromZoomingY = (currentHeight - newHeight) * percentageOfItemAtPinchPointY;
    // And this is the translation due to translation of the centerpoint between the two fingers
    float translateFromTranslatingX = centerPointEndX - centerPointStartX;
    float translateFromTranslatingY = centerPointEndY - centerPointStartY;
    // Total translation is from two components: (1) changing height and width from zooming and (2) from the two fingers translating in unity
    float translateTotalX = translateFromZoomingX + translateFromTranslatingX;
    float translateTotalY = translateFromZoomingY + translateFromTranslatingY;

    // the new offset is the old/current one plus the total translation component
    newOffsetX = currentOffsetX + translateTotalX;
    newOffsetY = currentOffsetY + translateTotalY;

    return true;
  }

  public void getBounds(UIRectangle rect) {
    rect.set(newOffsetX, newOffsetY, newWidth, newHeight);
  }

  public void scaleEnd(float focusX, float focusY) {
    inProgress = false;
  }

  public boolean isInProgress() {
    return inProgress;
  }
}
