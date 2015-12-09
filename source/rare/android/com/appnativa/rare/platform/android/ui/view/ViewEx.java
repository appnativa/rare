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
    this.setBackground(NullDrawable.getInstance());
  }

  public ViewEx(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ViewEx(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void dispose() {
    if (graphics != null) {
      graphics.dispose();
      graphics = null;
    }
  }

  @Override
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

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  @Override
  public int getSuggestedMinimumHeight() {
    return super.getSuggestedMinimumHeight();
  }

  @Override
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

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ViewHelper.onAttachedToWindow(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ViewHelper.onDetachedFromWindow(this);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(getDefaultSizeEx(getSuggestedMinimumWidth(), widthMeasureSpec),
                         getDefaultSizeEx(getSuggestedMinimumHeight(), heightMeasureSpec));
  }

  public static int getDefaultSizeEx(int size, int measureSpec) {
    int result   = size;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);

    switch(specMode) {
      case MeasureSpec.AT_MOST :
      case MeasureSpec.UNSPECIFIED :
        result = size;

        break;

      case MeasureSpec.EXACTLY :
        result = specSize;

        break;
    }

    return result;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }
}
