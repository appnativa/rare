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

import com.appnativa.rare.iPlatformAppContext;

public class WindowTarget extends UITarget {
  private boolean compFocusPainted;

  public WindowTarget(iPlatformAppContext app, String name, boolean register, iListener listener) {
    super(app, name, register, listener);
  }

  public WindowTarget(iPlatformAppContext app, String name, iListener listener) {
    super(app, name, listener);
  }

  public WindowTarget(iPlatformAppContext app, String name, iParentComponent container, boolean register,
                      iListener listener) {
    super(app, name, container, register, listener);
  }

  public WindowTarget(iPlatformAppContext app, String name, iParentComponent container, boolean register) {
    super(app, name, container, register);
  }

  public WindowTarget(iPlatformAppContext app, String name, iParentComponent container) {
    super(app, name, container);
  }

  public WindowTarget(iPlatformAppContext app, String name) {
    super(app, name);
  }

  @Override
  protected void setComponentEx(iPlatformComponent comp) {
    super.setComponentEx(comp);
    compFocusPainted = (comp != null) && comp.isFocusPainted();

    if (compFocusPainted) {
      comp.setFocusPainted(false);
    }
  }

  @Override
  protected void removeComponentEx(iPlatformComponent comp) {
    super.removeComponentEx(comp);

    if ((comp != null) && compFocusPainted) {
      comp.setFocusPainted(compFocusPainted);
    }

    compFocusPainted = false;
  }
}
