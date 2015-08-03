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

import android.widget.SeekBar;

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iSlider;

import java.util.EventObject;

/**
 *
 * @author Don DeCoteau
 */
public class SliderView extends SeekBar implements SeekBar.OnSeekBarChangeListener, iSlider {
  protected iChangeListener changeListener;
  protected boolean         horizontal;
  protected int             lastValue;
  protected int             maxValue;
  protected int             minValue;

  public SliderView(Context context) {
    super(context);
  }

  public SliderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
    if ((progress != lastValue) && (changeListener != null)) {
      lastValue = progress;
      changeListener.stateChanged(new EventObject(sb));
    }
  }

  public void onStartTrackingTouch(SeekBar seekBar) {}

  public void onStopTrackingTouch(SeekBar seekBar) {}

  public void setChangeListener(iChangeListener l) {
    changeListener = l;
    setOnSeekBarChangeListener((l == null)
                               ? null
                               : this);
  }

  /**
   * @param horizontal the horizontal to set
   */
  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public void setMajorTickSpacing(int value) {
    setKeyProgressIncrement(value);
  }

  public void setMaximum(int maximum) {
    maxValue = maximum;

    float max = Math.abs(maxValue - minValue);

    setMax((int) max);
  }

  public void setMinimum(int minimum) {
    minValue = minimum;
  }

  public void setMinorTickSpacing(int value) {}

  public void setShowTicks(boolean show) {
    // TODO Auto-generated method stub
  }

  public void setValue(int value) {
    int val = Math.max(0, value - minValue);

    setProgress(val);
  }

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

  public int getValue() {
    return getProgress() + minValue;
  }

  /**
   * @return the horizontal
   */
  public boolean isHorizontal() {
    return horizontal;
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
