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

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.layout.BorderLayout;

import com.jgoodies.forms.layout.CellConstraints;

public class BorderLayoutView extends FormsView {
  protected static String[] specs             = { "f:d,f:d:g,f:d", "f:d,f:d:g,f:d" };
  static CellConstraints    tbTopCell         = new CellConstraints(1, 1, 3, 1);
  static CellConstraints    tbRightCell       = new CellConstraints(3, 2, 1, 1);
  static CellConstraints    tbLeftCell        = new CellConstraints(1, 2, 1, 1);
  static CellConstraints    tbBottomCell      = new CellConstraints(1, 3, 3, 1);
  static CellConstraints    lrTopCell         = new CellConstraints(2, 1, 1, 1);
  static CellConstraints    lrRightCell       = new CellConstraints(3, 1, 1, 3);
  static CellConstraints    lrLeftCell        = new CellConstraints(1, 1, 1, 3);
  static CellConstraints    lrBottomCell      = new CellConstraints(2, 3, 1, 1);
  static CellConstraints    centerCell        = new CellConstraints(2, 2, 1, 1);
  boolean                   topBottomPriority = true;
  UIInsets                  padding;

  public BorderLayoutView() {
    super(new BorderLayout(specs[0], specs[1]));
  }

  public BorderLayoutView(Object proxy) {
    super(proxy, new BorderLayout(specs[0], specs[1]));
  }

  public BorderLayoutView(String encodedColumnSpecs, String encodedRowSpecs) {
    super(new BorderLayout(encodedColumnSpecs, encodedColumnSpecs));
  }

  public BorderLayoutView(Object proxy, String encodedColumnSpecs, String encodedRowSpecs) {
    super(proxy, new BorderLayout(encodedColumnSpecs, encodedColumnSpecs));
  }

  public void add(iPlatformComponent c, Location constraints) {
    CellConstraints cc;

    switch(constraints) {
      case TOP :
        cc = topBottomPriority
             ? new CellConstraints(1, 1, 3, 1)
             : new CellConstraints(2, 1, 1, 1);

        break;

      case BOTTOM :
        cc = topBottomPriority
             ? new CellConstraints(1, 3, 3, 1)
             : new CellConstraints(2, 3, 1, 1);

        break;

      case LEFT :
        cc = topBottomPriority
             ? new CellConstraints(1, 2, 1, 1)
             : new CellConstraints(1, 1, 1, 3);

        break;

      case RIGHT :
        cc = topBottomPriority
             ? new CellConstraints(3, 2, 1, 1)
             : new CellConstraints(3, 1, 1, 3);

        break;

      default :
        cc = new CellConstraints(2, 2, 1, 1);

        if (padding != null) {
          cc.insets = padding;
        }
    }

    cc.hAlign = CellConstraints.FILL;
    cc.vAlign = CellConstraints.FILL;

    CellConstraints  occ = (CellConstraints) getConstraints(c);
    iParentComponent pc  = (iParentComponent) Component.fromView(this);

    if ((occ != null) && (occ.gridX == cc.gridX) && (occ.gridY == cc.gridY)
        && ((c.getParent() != null) && (c.getParent() == pc))) {
      return;
    }

    iPlatformComponent oldc = getComponentAt(cc.gridX, cc.gridY);

    if (oldc != null) {
      occ = null;
    }

    if ((oldc != null) && (oldc != c)) {
      pc.remove(oldc);
    }

    if (oldc != c) {
      super.add(c, cc, -1);
    }
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (constraints instanceof Location) {
      add(c, (Location) constraints);
    } else if (constraints == null) {
      add(c, Location.CENTER);
    } else {
      super.add(c, constraints, position);
    }
  }

  public void removeAtLocation(Location loc) {
    iPlatformComponent c = getComponentAt(loc);

    if (c != null) {
      remove(c);
    }
  }

  public void setBottomView(iPlatformComponent c) {
    if (c == null) {
      removeAtLocation(Location.BOTTOM);
    } else {
      add(c, Location.BOTTOM);
    }
  }

  public void setCenterView(iPlatformComponent c) {
    if (c == null) {
      removeAtLocation(Location.CENTER);
    } else {
      add(c, Location.CENTER);
    }
  }

  public void setHorizontal(boolean horizontal) {}

  public void setLeftView(iPlatformComponent c) {
    if (c == null) {
      removeAtLocation(Location.LEFT);
    } else {
      add(c, Location.LEFT);
    }
  }

  public void setPadding(UIInsets in) {
    if (in == null) {
      padding = null;
    } else {
      if (padding == null) {
        padding = new UIInsets();
      }

      padding.set(in);
    }
  }

  public void adjustSizeForPadding(UIDimension size) {
    if (padding != null) {
      size.width  += padding.left + padding.right;
      size.height += padding.top + padding.bottom;
    }
  }

  public void setRightView(iPlatformComponent c) {
    if (c == null) {
      removeAtLocation(Location.RIGHT);
    } else {
      add(c, Location.RIGHT);
    }
  }

  public void setTopBottomPriority(boolean topBottomPriority) {}

  public void setTopView(iPlatformComponent c) {
    if (c == null) {
      removeAtLocation(Location.TOP);
    } else {
      add(c, Location.TOP);
    }
  }

  public iPlatformComponent getBottomView() {
    return getComponentAt(Location.BOTTOM);
  }

  public iPlatformComponent getCenterView() {
    return getComponentAt(Location.CENTER);
  }

  public iPlatformComponent getComponentAt(Location location) {
    CellConstraints cc = getConstraints(location);

    if (cc == null) {
      return null;
    }

    return getComponentAt(cc.gridX, cc.gridY);
  }

  public CellConstraints getConstraints(Location location) {
    CellConstraints cc;

    switch(location) {
      case TOP :
        cc = topBottomPriority
             ? tbTopCell
             : lrTopCell;

        break;

      case BOTTOM :
        cc = topBottomPriority
             ? tbBottomCell
             : lrBottomCell;

        break;

      case LEFT :
        cc = topBottomPriority
             ? tbLeftCell
             : lrLeftCell;

        break;

      case RIGHT :
        cc = topBottomPriority
             ? tbRightCell
             : lrRightCell;

        break;

      default :
        cc = centerCell;
    }

    return cc;
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
}