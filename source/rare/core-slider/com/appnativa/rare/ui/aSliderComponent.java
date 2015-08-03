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

import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;

import java.util.EventObject;

public abstract class aSliderComponent extends XPComponent implements iChangeable, iSlider, iChangeListener {
  protected iSlider slider;

  public aSliderComponent(Object view) {
    super(view);
  }

  @Override
  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    getEventListenerList().remove(iChangeListener.class, l);
  }

  @Override
  public void stateChanged(EventObject e) {
    if (listenerList != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      Utils.fireChangeEvent(listenerList, changeEvent);
    }
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    slider.setHorizontal(horizontal);
  }

  @Override
  public void setMajorTickSpacing(int value) {
    slider.setMajorTickSpacing(value);
  }

  @Override
  public void setMaximum(int maximum) {
    slider.setMaximum(maximum);
  }

  @Override
  public void setMinimum(int minimum) {
    slider.setMinimum(minimum);
  }

  @Override
  public void setMinorTickSpacing(int value) {
    slider.setMinorTickSpacing(value);
  }

  @Override
  public void setShowTicks(boolean show) {
    slider.setShowTicks(show);
  }

  @Override
  public void setThumbOffset(int off) {
    slider.setThumbOffset(off);
  }

  @Override
  public void setValue(int value) {
    slider.setValue(value);
  }

  @Override
  public int getMaximum() {
    return slider.getMaximum();
  }

  @Override
  public int getMinimum() {
    return slider.getMinimum();
  }

  @Override
  public int getValue() {
    return slider.getValue();
  }

  @Override
  public boolean isHorizontal() {
    return slider.isHorizontal();
  }
}
