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

package com.appnativa.rare.platform.android.ui.view;

import android.content.Context;

import android.graphics.Canvas;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class GlassView extends ViewGroupEx {
  boolean overlayContainer;

  public GlassView(Context context, boolean overlayContainer) {
    super(context);
    this.overlayContainer = overlayContainer;
  }

  @Override
  public void draw(Canvas canvas) {
    if (overlayContainer) {
      super.draw(canvas);
    }
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    return overlayContainer
           ? super.dispatchKeyEvent(event)
           : true;
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return overlayContainer
           ? super.dispatchTouchEvent(ev)
           : true;
  }

  @Override
  public boolean dispatchGenericMotionEvent(MotionEvent ev) {
    return overlayContainer
        ? super.dispatchGenericMotionEvent(ev)
        : true;
  }
  
  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {}
}
