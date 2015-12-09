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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;

public class ScrollBarView extends NonViewView {
  private boolean      showAlways;
  protected ScrollView scrollView;
  boolean              horizontal;

  public ScrollBarView(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public ScrollBarView(ScrollView sv, boolean horizontal) {
    scrollView      = sv;
    this.horizontal = horizontal;
  }

  @Override
  public void dispose() {
    scrollView = null;
    if(listenerList!=null) {
      listenerList.clear();
    }
    listenerList=null;
  }

  public boolean isShowAlways() {
    return showAlways;
  }

  public void setShowAlways(boolean showAlways) {
    if (scrollView != null) {
      if (horizontal) {
        scrollView.setShowsHorizontalScrollIndicator(showAlways);
      } else {
        scrollView.setShowsVerticalScrollIndicator(showAlways);
      }
    }

    this.showAlways = showAlways;
  }

  protected double            blockIncrement = 0;
  protected double            maximim        = 100;
  protected double            minimim        = 0;
  protected double            unitIncrement  = 0;
  protected double            value          = 0;
  protected double            visibleAmount  = 10;
  protected int               direction;
  protected EventListenerList listenerList;

  public void addChangeListener(iChangeListener l) {
    getEventListenerList().add(iChangeListener.class, l);
  }

  public void removeChangeListener(iChangeListener l) {
    if (hasListener()) {
      getEventListenerList().remove(iChangeListener.class, l);
    }
  }

  public void setBlockIncrement(double b) {
    blockIncrement = b;
  }

  public void setMaximum(double max) {
    this.maximim = max;
  }

  public void setMinimum(double min) {
    this.minimim = min;
  }

  public void setUnitIncrement(double u) {
    unitIncrement = u;
  }

  public void setValue(double v) {
    if (v != value) {
      direction = (v < getValue())
                  ? -1
                  : 1;
      v         = Math.min(maximim, v);
      v         = Math.max(minimim, v);
      value     = v;

      if (hasListener()) {
        notifyChangeListeners();
      }
    }
  }

  public void setVisibleAmount(double v) {
    visibleAmount = v;
  }

  public int getDirection() {
    return direction;
  }

  public double getBlockIncrement() {
    return blockIncrement;
  }

  public double getMaximum() {
    return maximim;
  }

  public double getMinimum() {
    return minimim;
  }

  public double getUnitIncrement() {
    return unitIncrement;
  }

  public double getValue() {
    return value;
  }

  public double getVisibleAmount() {
    return visibleAmount;
  }

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

  public void setTheme(boolean b) {}

  public void notifyChangeListeners() {
    if(changeEvent==null) {
      changeEvent=new ChangeEvent(scrollView);
    }
    Utils.fireChangeEvent(getEventListenerList(), changeEvent);
  }
}
