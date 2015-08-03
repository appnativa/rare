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
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;

public class aAdjustable implements iAdjustable {
  protected double            blockIncrement = 1;
  protected double            maximim        = 100;
  protected double            minimim        = 0;
  protected double            unitIncrement  = 1;
  protected double            value          = 0;
  protected double            visibleAmount  = 1;
  protected ChangeEvent       changeEvent;
  protected int               direction;
  protected EventListenerList listenerList;

  public aAdjustable() {}

  @Override
  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    if (hasListener()) {
      getEventListenerList().remove(iChangeListener.class, l);
    }
  }

  @Override
  public void setBlockIncrement(double b) {
    blockIncrement = b;
  }

  @Override
  public void setMaximum(double max) {
    this.maximim = max;
  }

  @Override
  public void setMinimum(double min) {
    this.minimim = min;
  }

  @Override
  public void setUnitIncrement(double u) {
    unitIncrement = u;
  }

  @Override
  public void setValue(double v) {
    if (v != value) {
      direction = (v < getValue())
                  ? -1
                  : 1;
      value     = v;

      if (hasListener()) {
        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }

        notifyListeners(changeEvent);
      }
    }
  }

  @Override
  public void setVisibleAmount(double v) {
    visibleAmount = v;
  }

  public int getDirection() {
    return direction;
  }

  @Override
  public double getBlockIncrement() {
    return blockIncrement;
  }

  @Override
  public double getMaximum() {
    return maximim;
  }

  @Override
  public double getMinimum() {
    return minimim;
  }

  @Override
  public double getUnitIncrement() {
    return unitIncrement;
  }

  @Override
  public double getValue() {
    return value;
  }

  @Override
  public double getVisibleAmount() {
    return visibleAmount;
  }

  @Override
  public boolean isAdjusting() {
    return false;
  }

  protected EventListenerList getEventListenerList() {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    return listenerList;
  }

  protected boolean hasListener() {
    return listenerList != null;
  }

  protected void notifyListeners(ChangeEvent e) {
    Utils.fireChangeEvent(getEventListenerList(), changeEvent);
  }
}
