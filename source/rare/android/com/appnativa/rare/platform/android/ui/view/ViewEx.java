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
import android.graphics.Matrix;

import android.util.AttributeSet;

import android.view.View;

import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.iComponentView;
import com.appnativa.rare.platform.android.ui.util.AndroidGraphics;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 * @author Don DeCoteau
 */
public class ViewEx extends View implements iPainterSupport, iComponentView {
  protected boolean                   blockRequestLayout;
  protected iPlatformComponentPainter componentPainter;
  protected AndroidGraphics           graphics;
  protected Matrix                    matrix;

  public enum MeasureType { HORIZONTAL, VERTICAL, UNKNOWN }

  public ViewEx(Context context) {
    super(context);
    this.setBackgroundDrawable(NullDrawable.getInstance());
  }

  public ViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ViewEx(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  public void draw(Canvas canvas) {
    graphics = AndroidGraphics.fromGraphics(canvas, this, graphics);

    final iPlatformComponentPainter cp = componentPainter;

    if (cp == null) {
      super.draw(canvas);
    } else {
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, false);
      super.draw(canvas);
      cp.paint(graphics, getScrollX(), getScrollY(), getWidth(), getHeight(), iPlatformPainter.UNKNOWN, true);
    }

    graphics.clear();
  }

  @Override
  public void requestLayout() {
    if (!blockRequestLayout) {
      super.requestLayout();
    }
  }

  public void setBlockRequestLayout(boolean blockRequestLayout) {
    this.blockRequestLayout = blockRequestLayout;
  }

  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  public int getSuggestedMinimumWidth() {
    return super.getSuggestedMinimumWidth();
  }

  public final View getView() {
    return this;
  }

  public boolean isBlockRequestLayout() {
    return blockRequestLayout;
  }

  protected void callSuperDraw(Canvas canvas) {
    super.draw(canvas);
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }
}
