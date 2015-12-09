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

import android.util.AttributeSet;

import android.view.View;

import android.widget.ProgressBar;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iProgressBar;

/**
 *
 * @author Don DeCoteau
 */
public class ProgressBarView extends ProgressBar implements iProgressBar {
  int             lastValue;
  private boolean horizontal;
  private int     maxValue;
  private int     minValue;
  int             graphicSize;

  public ProgressBarView(Context context) {
    super(context);
  }

  public ProgressBarView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * @param horizontal
   *          the horizontal to set
   */
  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  @Override
  public void setMaximum(int maximum) {
    maxValue = maximum;

    float max = Math.abs(maxValue - minValue);

    setMax((int) max);
  }

  @Override
  public void setMinimum(int minimum) {
    minValue = minimum;
  }

  @Override
  public void setValue(int value) {
    int val = Math.max(0, value - minValue);

    setProgress(val);
  }

  @Override
  public iPlatformComponent getComponent() {
    Component c = Component.fromView(this);

    if (c == null) {
      c = new Component(this);
    }

    return c;
  }

  public int getMaximum() {
    return maxValue;
  }

  public int getMinimum() {
    return minValue;
  }

  @Override
  public int getValue() {
    return getProgress() + minValue;
  }

  /**
   * @return the horizontal
   */
  public boolean isHorizontal() {
    return horizontal;
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
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    ViewHelper.onSizeChanged(this, w, h, oldw, oldh);
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    ViewHelper.onVisibilityChanged(this, changedView, visibility);
  }

  @Override
  public void setGraphicSize(int size) {
    graphicSize = size;
  }

  @Override
  protected int getSuggestedMinimumWidth() {
    return (graphicSize == 0)
           ? super.getSuggestedMinimumWidth()
           : graphicSize;
  }

  @Override
  protected int getSuggestedMinimumHeight() {
    return (graphicSize == 0)
           ? super.getSuggestedMinimumHeight()
           : graphicSize;
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (graphicSize > 0) {
      setMeasuredDimension(resolveSizeAndState(graphicSize, widthMeasureSpec, 0),
                           resolveSizeAndState(graphicSize, heightMeasureSpec, 0));
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
