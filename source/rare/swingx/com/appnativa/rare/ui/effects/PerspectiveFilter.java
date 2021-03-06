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

package com.appnativa.rare.ui.effects;
/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.awt.Rectangle;

/**
 * A filter which performs a perspective distortion on an image.
 */
public class PerspectiveFilter extends aTransformFilter {
  private float A, B, C, D, E, F, G, H, I;
  private float dx1, dy1, dx2, dy2, dx3, dy3;
  private float x0, y0, x1, y1, x2, y2, x3, y3;

  /**
   * Construct a PerspectiveFilter.
   */
  public PerspectiveFilter() {
    this(0, 0, 100, 0, 100, 100, 0, 100);
  }

  /**
   * Construct a PerspectiveFilter.
   * @param x0 the new position of the top left corner
   * @param y0 the new position of the top left corner
   * @param x1 the new position of the top right corner
   * @param y1 the new position of the top right corner
   * @param x2 the new position of the bottom right corner
   * @param y2 the new position of the bottom right corner
   * @param x3 the new position of the bottom left corner
   * @param y3 the new position of the bottom left corner
   */
  public PerspectiveFilter(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
    setCorners(x0, y0, x1, y1, x2, y2, x3, y3);
  }

  @Override
  public String toString() {
    return "Distort/Perspective...";
  }

  /**
   * Set the new positions of the image corners.
   * @param x0 the new position of the top left corner
   * @param y0 the new position of the top left corner
   * @param x1 the new position of the top right corner
   * @param y1 the new position of the top right corner
   * @param x2 the new position of the bottom right corner
   * @param y2 the new position of the bottom right corner
   * @param x3 the new position of the bottom left corner
   * @param y3 the new position of the bottom left corner
   */
  public final void setCorners(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.x3 = x3;
    this.y3 = y3;
    dx1     = x1 - x2;
    dy1     = y1 - y2;
    dx2     = x3 - x2;
    dy2     = y3 - y2;
    dx3     = x0 - x1 + x2 - x3;
    dy3     = y0 - y1 + y2 - y3;

    float a11, a12, a13, a21, a22, a23, a31, a32;

    if ((dx3 == 0) && (dy3 == 0)) {
      a11 = x1 - x0;
      a21 = x2 - x1;
      a31 = x0;
      a12 = y1 - y0;
      a22 = y2 - y1;
      a32 = y0;
      a13 = a23 = 0;
    } else {
      a13 = (dx3 * dy2 - dx2 * dy3) / (dx1 * dy2 - dy1 * dx2);
      a23 = (dx1 * dy3 - dy1 * dx3) / (dx1 * dy2 - dy1 * dx2);
      a11 = x1 - x0 + a13 * x1;
      a21 = x3 - x0 + a23 * x3;
      a31 = x0;
      a12 = y1 - y0 + a13 * y1;
      a22 = y3 - y0 + a23 * y3;
      a32 = y0;
    }

    A = a22 - a32 * a23;
    B = a31 * a23 - a21;
    C = a21 * a32 - a31 * a22;
    D = a32 * a13 - a12;
    E = a11 - a31 * a13;
    F = a31 * a12 - a11 * a32;
    G = a12 * a23 - a22 * a13;
    H = a21 * a13 - a11 * a23;
    I = a11 * a22 - a21 * a12;
  }

  /**
   * Get the origin of the output image. Use this for working out where to draw your new image.
   * @return the X origin.
   */
  public final float getOriginX() {
    return x0 - (int) Math.min(Math.min(x0, x1), Math.min(x2, x3));
  }

  /**
   * Get the origin of the output image. Use this for working out where to draw your new image.
   * @return the Y origin.
   */
  public final float getOriginY() {
    return y0 - (int) Math.min(Math.min(y0, y1), Math.min(y2, y3));
  }

  @Override
  protected final void transformInverse(final int x, final int y, final float[] out) {
    out[0] = originalSpace.width * (A * x + B * y + C) / (G * x + H * y + I);
    out[1] = originalSpace.height * (D * x + E * y + F) / (G * x + H * y + I);
  }

  @Override
  protected void transformSpace(final Rectangle rect) {
    rect.x      = (int) Math.min(Math.min(x0, x1), Math.min(x2, x3));
    rect.y      = (int) Math.min(Math.min(y0, y1), Math.min(y2, y3));
    rect.width  = (int) Math.max(Math.max(x0, x1), Math.max(x2, x3)) - rect.x;
    rect.height = (int) Math.max(Math.max(y0, y1), Math.max(y2, y3)) - rect.y;
  }
}
