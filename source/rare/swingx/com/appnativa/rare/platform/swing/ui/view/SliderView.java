/*
 * @(#)SliderView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.appnativa.rare.platform.swing.plaf.SkinableSliderUI;
import com.appnativa.rare.ui.iSlider;
import com.appnativa.rare.ui.event.iChangeListener;

public class SliderView extends JSlider implements iSlider, ChangeListener {
  com.appnativa.rare.ui.event.ChangeEvent ChangeEvent;
  private iChangeListener                 changeListener;

  public SliderView() {
  }

  public SliderView(BoundedRangeModel brm) {
    super(brm);
  }

  public SliderView(int orientation) {
    super(orientation);
  }

  public SliderView(int min, int max) {
    super(min, max);
  }

  public SliderView(int min, int max, int value) {
    super(min, max, value);
  }

  public SliderView(int orientation, int min, int max, int value) {
    super(orientation, min, max, value);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (changeEvent == null) {
      changeEvent = new ChangeEvent(this);
    }

    if (changeListener != null) {
      changeListener.stateChanged(changeEvent);
    }
  }

  public void setChangeListener(iChangeListener l) {
    removeChangeListener(this);
    addChangeListener(this);
    changeListener = l;
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    setOrientation(horizontal ? HORIZONTAL : VERTICAL);
  }

  @Override
  public void setShowTicks(boolean show) {
    setPaintTicks(show);
  }

  @Override
  public void setThumbOffset(int off) {
    if (getUI() instanceof SkinableSliderUI) {
      ((SkinableSliderUI) getUI()).setThumbOffset(off);
    }
  }

  @Override
  public boolean isHorizontal() {
    return getOrientation() == HORIZONTAL;
  }
}
