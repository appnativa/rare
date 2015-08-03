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

package com.appnativa.rare.widget;

import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.aWidgetListener;

/**
 * An abstract class that can be extended to provide bean integration
 * that maximizes backward compatability. Whenever possible, changes
 * to the iBeanIntegrator interface will be implemented in this abstract class
 *
 * @author Don DeCoteau
 */
public abstract class aBeanIntegrator implements iBeanIntegrator {
  @Override
  public void disposing() {}

  @Override
  public void handleConnection(iURLConnection conn) {
    conn.dispose();
  }

  @Override
  public void initializeListeners(aWidgetListener l) {}

  public boolean shouldCallSizeMethods() {
    return false;
  }

  @Override
  public boolean wantsURLConnection() {
    return true;
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    setValue(value);
  }

  @Override
  public Object getHTTPFormValue() {
    return getValue();
  }

  public void getMinimumSize(UIDimension Size) {}

  public void getPreferedSize(UIDimension Size) {}

  @Override
  public Object[] getSelectedObjects() {
    Object v = getSelection();

    return (v == null)
           ? null
           : new Object[] { v };
  }

  @Override
  public Object getSelection() {
    return getValue();
  }
}
