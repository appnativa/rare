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

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.util.IdentityArrayList;

public class OverlayContainer extends XPContainer {
  protected IdentityArrayList<iPlatformComponent> managedOverlays;
  private iPlatformComponent                      mainComponent;

  OverlayContainer(iPlatformComponent mainComponent) {
    super(PlatformHelper.createGlassView(true));
    managedOverlays = new IdentityArrayList<iPlatformComponent>(2);
    setBounds(mainComponent.getBounds());
    setMainComponent(mainComponent);
  }

  public void addManagedOverlay(iPlatformComponent c) {
    if (c != null) {
      addOverlay(c);
      managedOverlays.addIfNotPresent(c);

      UIDimension size = getSize();

      c.setBounds(0, 0, size.width, size.height);
    }
  }

  public void addOverlay(iPlatformComponent c) {
    if (c != null) {
      add(c);
      c.bringToFront();
    }
  }

  public void updateManagedOverlays() {
    UIDimension size = getSize();

    if (mainComponent != null) {
      mainComponent.setBounds(0, 0, size.width, size.height);
    }

    if (!managedOverlays.isEmpty()) {
      for (iPlatformComponent c : managedOverlays) {
        c.setBounds(0, 0, size.width, size.height);
      }
    }
  }

  public iPlatformComponent getMainComponent() {
    return mainComponent;
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    updateManagedOverlays();
  }

  public void removeOverlay(iPlatformComponent c) {
    remove(c);
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      managedOverlays.remove(c);
      super.remove(c);
    }
  }

  @Override
  public void removeAll() {
    super.removeAll();
    managedOverlays.clear();
  }

  public void setMainComponent(iPlatformComponent mainComponent) {
    if (this.mainComponent != mainComponent) {
      if (this.mainComponent != null) {
        remove(this.mainComponent);
      }

      this.mainComponent = mainComponent;

      if (mainComponent != null) {
        UIDimension size = getSize();

        mainComponent.setBounds(0, 0, size.width, size.height);
        mainComponent.removeFromParent();
        add(mainComponent);
      }
    }
  }
}
