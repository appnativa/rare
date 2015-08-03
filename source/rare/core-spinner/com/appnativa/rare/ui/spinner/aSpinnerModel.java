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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iChangeListener;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aSpinnerModel implements iSpinnerModel {
  protected EventListenerList listenerList = new EventListenerList();
  private ChangeEvent         changeEvent;
  boolean                     editable;

  @Override
  public void addChangeListener(iChangeListener l) {
    listenerList.add(iChangeListener.class, l);
  }

  @Override
  public void removeChangeListener(iChangeListener l) {
    listenerList.remove(iChangeListener.class, l);
  }

  @Override
  public boolean isEditable() {
    return editable;
  }

  public void dispose() {
    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  @Override
  public String toString(Object value) {
    return (value == null)
           ? null
           : value.toString();
  }

  @Override
  public Object fromString(String value) {
    throw new UnsupportedOperationException();
  }

  protected void fireStateChanged() {
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iChangeListener.class) {
        if (changeEvent == null) {
          changeEvent = new ChangeEvent(this);
        }

        ((iChangeListener) listeners[i + 1]).stateChanged(changeEvent);
      }
    }
  }
}
