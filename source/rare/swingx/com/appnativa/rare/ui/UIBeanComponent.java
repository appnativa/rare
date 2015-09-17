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

import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.widget.iBeanIntegrator;
import com.appnativa.rare.widget.iWidget;

public class UIBeanComponent extends Component implements iBeanIntegrator {
  public UIBeanComponent() {
    super(new JPanelEx());
  }

  @Override
  public void configure(iWidget w, Widget cfg) {}

  @Override
  public void disposing() {}

  @Override
  public void handleConnection(iURLConnection conn) {}

  @Override
  public void initializeListeners(aWidgetListener l) {}

  @Override
  public boolean wantsURLConnection() {
    return false;
  }

  @Override
  public void setFromHTTPFormValue(Object value) {}

  @Override
  public void setValue(Object value) {}

  @Override
  public iPlatformComponent getContainer() {
    return this;
  }

  @Override
  public iPlatformComponent getDataComponent() {
    return this;
  }

  @Override
  public Object getHTTPFormValue() {
    return null;
  }

  @Override
  public Object[] getSelectedObjects() {
    return null;
  }

  @Override
  public Object getSelection() {
    return null;
  }

  @Override
  public Object getValue() {
    return null;
  }
}
