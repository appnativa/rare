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

package com.appnativa.rare.ui.event;

import com.appnativa.rare.ui.UIPoint;

public class ScaleGestureObject {
  protected UIPoint currentSpan;
  protected float   focusX;
  protected float   focusY;
  protected UIPoint previousSpan;
  protected float   scaleFactor;
  protected float   velocity;

  public ScaleGestureObject() {
    currentSpan  = new UIPoint();
    previousSpan = new UIPoint();
    scaleFactor  = 1;
  }

  public void reset(float x, float y) {
    focusX = x;
    focusY = y;
    currentSpan.set(0, 0);
    previousSpan.set(0, 0);
    scaleFactor = 1;
    velocity    = 0;
  }

  public void setCurrentSpan(float x, float y) {
    previousSpan.set(currentSpan);
    currentSpan.set(x, y);
  }

  public void setScaleFactor(float scale) {
    scaleFactor = scale;
  }

  public float getCurrentSpan() {
    return Math.abs(currentSpan.x + currentSpan.y) / 2;
  }

  public float getCurrentSpanX() {
    return currentSpan.x;
  }

  public float getCurrentSpanY() {
    return currentSpan.y;
  }

  public float getFocusX() {
    return focusX;
  }

  public float getFocusY() {
    return focusY;
  }

  public float getPreviousSpan() {
    return Math.abs(currentSpan.x + currentSpan.y) / 2;
  }

  public float getScaleFactor() {
    return scaleFactor;
  }
}
