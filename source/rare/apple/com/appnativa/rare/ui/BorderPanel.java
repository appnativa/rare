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

import com.appnativa.jgoodies.forms.layout.CellConstraints;
import com.appnativa.rare.platform.apple.ui.view.BorderLayoutView;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.widget.iWidget;

public class BorderPanel extends aBorderPanel {
  public BorderPanel(iWidget context) {
    this();
  }

  public BorderPanel() {
    super(new BorderLayoutView());
  }

  public BorderPanel(String encodedColumnSpecs, String encodedRowSpecs) {
    super(new BorderLayoutView(encodedColumnSpecs, encodedRowSpecs));
  }

  protected BorderPanel(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c == null) {
      c = getComponentAt((Location) constraints);

      if (c != null) {
        remove(c);
      }

      return;
    }

    super.add(c, constraints, position);
  }

  @Override
  public void setUseCrossPattern(boolean useCrossPattern) {
    super.setUseCrossPattern(useCrossPattern);
    ((BorderLayoutView) view).setUseCrossPattern(useCrossPattern);
  }

  @Override
  public void setPadding(UIInsets in) {
    ((BorderLayoutView) view).setPadding(in);
  }

  @Override
  public void setTopBottomPriority(boolean topBottomPriority) {
    super.setTopBottomPriority(topBottomPriority);
    ((BorderLayoutView) view).setTopBottomPriority(topBottomPriority);
  }

  @Override
  protected iPlatformComponent getComponentAt(Location location) {
    return ((BorderLayoutView) view).getComponentAt(location);
  }

  @Override
  protected BorderLayout getBorderLayout() {
    return (BorderLayout) ((BorderLayoutView) view).getLayout();
  }

  @Override
  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((BorderLayoutView) view).getCellConstraints(component);
  }

}
