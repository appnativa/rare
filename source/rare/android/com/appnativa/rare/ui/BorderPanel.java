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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.widget.iWidget;

import com.jgoodies.forms.layout.CellConstraints;

public class BorderPanel extends aBorderPanel {
  public BorderPanel() {
    super(new BorderLayoutView(Platform.getAppContext().getActivity()));
  }

  public BorderPanel(String encodedColumnSpecs, String encodedRowSpecs) {
    super(new BorderLayoutView(Platform.getAppContext().getActivity(), encodedColumnSpecs, encodedRowSpecs));
  }

  public BorderPanel(iWidget context) {
    super(new BorderLayoutView(context.getAppContext().getActivity()));
    this.widget = context;
  }

  protected BorderPanel(Object view) {
    super(view);
  }

  public void add(iPlatformComponent c, Object constraints, int position) {
    if (constraints instanceof Location) {
      if (c == null) {
        c = getComponentAt((Location) constraints);

        if (c != null) {
          remove(c);
        }

        return;
      }

      ((BorderLayoutView) view).add(c, (Location) constraints);

      return;
    }

    if (!(constraints instanceof CellConstraints)) {
      ((BorderLayoutView) view).add(c, Location.CENTER);

      return;
    }

    if (constraints instanceof CellConstraints) {
      constraints = ((FormsView) view).createLayoutParams((CellConstraints) constraints);
    }

    super.add(c, constraints, position);
  }

  public void setHorizontal(boolean horizontal) {
    ((BorderLayoutView) view).setHorizontal(horizontal);
  }

  public void setPadding(UIInsets in) {
    ((BorderLayoutView) view).setPadding(in);
  }

  public void setTopBottomPriority(boolean topBottomPriority) {
    ((BorderLayoutView) view).setTopBottomPriority(topBottomPriority);
  }

  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((BorderLayoutView) view).getCellConstraints(component.getView());
  }

  public Object getComponentConstraints(iPlatformComponent component) {
    return getCellConstraints((iPlatformComponent) component);
  }

  protected BorderLayout getBorderLayout() {
    return (BorderLayout) ((BorderLayoutView) view).getLayout();
  }

  protected iPlatformComponent getComponentAt(Location location) {
    return ((BorderLayoutView) view).getComponentAt(location);
  }

  protected CellConstraints getConstraints(Location location) {
    return ((BorderLayoutView) view).getConstraints(location);
  }

  protected void getMinimumSizeEx(UIDimension size) {
    if (view.isLayoutRequested()) {
      getBorderLayout().invalidateMinimumCache();
    }

    super.getMinimumSizeEx(size);
  }

  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    if (view.isLayoutRequested() && (maxWidth < 1)) {
      getBorderLayout().invalidatePreferredCache();
    }

    super.getPreferredSizeEx(size, maxWidth);
  }
}