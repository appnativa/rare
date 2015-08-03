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

import com.appnativa.rare.ui.layout.BorderLayout;

import com.jgoodies.forms.layout.CellConstraints;

public abstract class aBorderPanel extends XPContainer {
  public aBorderPanel() {
    super();
  }

  public aBorderPanel(Object view) {
    super(view);
  }

  public void setBottomView(iPlatformComponent c) {
    if (c != null) {
      if (getBottomView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.BOTTOM);
  }

  public void setCenterView(iPlatformComponent c) {
    if (c != null) {
      if (getCenterView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.CENTER);
  }

  public abstract void setHorizontal(boolean horizontal);

  public void setLeftView(iPlatformComponent c) {
    if (c != null) {
      if (getLeftView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.LEFT);
  }

  public void setRightView(iPlatformComponent c) {
    if (c != null) {
      if (getRightView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.RIGHT);
  }

  public abstract void setTopBottomPriority(boolean topBottomPriority);

  public void setTopView(iPlatformComponent c) {
    if (c != null) {
      if (getTopView() == c) {
        return;
      }

      remove(c);
    }

    add(c, Location.TOP);
  }

  public iPlatformComponent getBottomView() {
    return getComponentAt(Location.BOTTOM);
  }

  public abstract CellConstraints getCellConstraints(iPlatformComponent component);

  public iPlatformComponent getCenterView() {
    return getComponentAt(Location.CENTER);
  }

  public iPlatformComponent getLeftView() {
    return getComponentAt(Location.LEFT);
  }

  public iPlatformComponent getRightView() {
    return getComponentAt(Location.RIGHT);
  }

  public iPlatformComponent getTopView() {
    return getComponentAt(Location.TOP);
  }

  protected abstract BorderLayout getBorderLayout();

  protected abstract iPlatformComponent getComponentAt(Location location);

  protected abstract CellConstraints getConstraints(Location location);

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    getBorderLayout().invalidateMinimumCache();
    getBorderLayout().getMinimumSize(this, size);

    UIInsets in = margin;

    if (margin != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    getBorderLayout().getPreferredSize(this, size, maxWidth);

    UIInsets in = margin;

    if (margin != null) {
      size.width  += in.left + in.right;
      size.height += in.top + in.bottom;
    }
  }
}
