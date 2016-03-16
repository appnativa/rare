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

package com.appnativa.rare.platform.android.ui.view;

import android.content.Context;

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.aBorderPanel;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.layout.BorderLayout;

public class BorderLayoutView extends FormsView {
  protected static String[] specs             = { "f:d,f:d:g,f:d", "f:d,f:d:g,f:d" };
  boolean                   topBottomPriority = true;
  private UIInsets          padding;
  boolean                   useCrossPattern;

  public BorderLayoutView(Context context) {
    super(context, new BorderLayout(specs[0], specs[1]));
  }

  public BorderLayoutView(Context context, String encodedColumnSpecs, String encodedRowSpecs) {
    super(context, new BorderLayout(encodedColumnSpecs, encodedColumnSpecs));
  }

  public void add(iPlatformComponent c, Location constraints) {
    CellConstraints cc = aBorderPanel.getConstraints(constraints, useCrossPattern, topBottomPriority);

    cc = (CellConstraints) cc.clone();

    if (padding != null) {
      cc.insets = padding;
    }
    cc.hAlign = CellConstraints.FILL;
    cc.vAlign = CellConstraints.FILL;

    CellConstraints  occ = getConstraints(c);
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

    if ((oldc != c) && (c != null)) {
      addView(c.getView(), new CellConstraintsEx(cc));
    }
  }

  public iPlatformComponent getBottomView() {
    return getComponentAt(Location.BOTTOM);
  }

  public iPlatformComponent getCenterView() {
    return getComponentAt(Location.CENTER);
  }

  public iPlatformComponent getComponentAt(Location location) {
    CellConstraints cc = aBorderPanel.getConstraints(location,useCrossPattern,topBottomPriority);

    if (cc == null) {
      return null;
    }

    return getComponentAt(cc.gridX, cc.gridY);
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

  public void setBottomView(iPlatformComponent c) {
    add(c, Location.BOTTOM);
  }

  public void setCenterView(iPlatformComponent c) {
    add(c, Location.CENTER);
  }

  public void setLeftView(iPlatformComponent c) {
    add(c, Location.LEFT);
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

  public void setRightView(iPlatformComponent c) {
    add(c, Location.RIGHT);
  }

  public void setTopBottomPriority(boolean topBottomPriority) {
    this.topBottomPriority = topBottomPriority;
  }

  public void setTopView(iPlatformComponent c) {
    add(c, Location.TOP);
  }

  public void setUseCrossPattern(boolean useCrossPattern) {
    this.useCrossPattern = true;
  }
}
