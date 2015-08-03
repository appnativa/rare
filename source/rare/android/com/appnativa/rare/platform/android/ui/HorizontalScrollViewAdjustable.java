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

package com.appnativa.rare.platform.android.ui;

import android.widget.HorizontalScrollView;

import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.iAdjustable;

public class HorizontalScrollViewAdjustable implements iAdjustable {
  HorizontalScrollView view;
  double               min;
  double               max;

  public HorizontalScrollViewAdjustable(HorizontalScrollView hsv) {
    view = hsv;
  }

  public void setMinimum(double min) {
    this.min = min;
  }

  public double getMinimum() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setMaximum(double max) {
    this.max = max;
  }

  public double getMaximum() {
    return max;
  }

  public void setUnitIncrement(double u) {}

  public double getUnitIncrement() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setBlockIncrement(double b) {
    // TODO Auto-generated method stub
  }

  public double getBlockIncrement() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setVisibleAmount(double v) {
    // TODO Auto-generated method stub
  }

  public double getVisibleAmount() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setValue(double v) {
    // TODO Auto-generated method stub
  }

  public double getValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean isAdjusting() {
    // TODO Auto-generated method stub
    return false;
  }

  public void addChangeListener(iChangeListener l) {
    // TODO Auto-generated method stub
  }

  public void removeChangeListener(iChangeListener l) {
    // TODO Auto-generated method stub
  }
}
