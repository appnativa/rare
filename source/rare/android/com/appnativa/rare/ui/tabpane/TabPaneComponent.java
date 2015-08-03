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

package com.appnativa.rare.ui.tabpane;

import android.content.Context;

import com.appnativa.rare.platform.android.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.android.ui.view.FrameView;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.widget.iWidget;

public class TabPaneComponent extends aTabPaneComponent {
  public TabPaneComponent(Context context) {
    super(new TabBorderPane(context));

    TabStripComponent strip = new TabStripComponent(view.getContext());
    BorderPanel       th    = new BorderPanel();

    th.add(strip);

    ContainerPanel content = new ContainerPanel(new FrameView(view.getContext()));

    add(th, Location.TOP);
    add(content, Location.CENTER);

    TabPaneLayout l = createLayout();

    l.setComponents(this, th, strip, content);
    setLayout(l);
  }

  public TabPaneComponent(iWidget context) {
    this(context.getAppContext().getActivity());
  }

  @Override
  public void dispose() {
    super.dispose();

    if (tabsLayout != null) {
      tabsLayout.dispose();
    }
  }

  protected TabPaneLayout createLayout() {
    return new TabPaneLayout(this);
  }

  static class TabBorderPane extends BorderLayoutView {
    public TabBorderPane(Context context) {
      super(context);
    }
  }
}
