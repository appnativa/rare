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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.platform.swing.plaf.SkinableSliderUI;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iSlider;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderView extends JSlider implements iSlider, ChangeListener {
  com.appnativa.rare.ui.event.ChangeEvent ChangeEvent;
  private iChangeListener                 changeListener;

  public SliderView() {}

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
    setOrientation(horizontal
                   ? HORIZONTAL
                   : VERTICAL);
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
