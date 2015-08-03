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

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.apple.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.apple.ui.view.FrameView;
import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.widget.iWidget;

public class TabPaneComponent extends aTabPaneComponent {
  View contentView;
  View headerView;

  public TabPaneComponent() {
    super(new TabBorderPane());

    TabStripComponent strip = new TabStripComponent();
    BorderPanel       th    = new BorderPanel();

    th.add(strip);

    Container content = new Container(new FrameView());

    add(th, Location.TOP);
    add(content, Location.CENTER);

    TabPaneLayout l = new TabPaneLayout(this);

    l.setComponents(this, th, strip, content);
    setLayout(l);
    headerView  = th.getView();
    contentView = content.getView();
  }

  public TabPaneComponent(iWidget context) {
    this();
  }

  @Override
  public void addLongPressListener(iGestureListener l) {
    ((Component) headerView.getComponent()).addLongPressListener(l);
  }

  @Override
  public void dispose() {
    super.dispose();
    headerView  = null;
    contentView = null;
  }

  @Override
  public void revalidate() {
    super.revalidate();

    if (headerView != null) {
      headerView.revalidate();
    }

    if (contentView != null) {
      contentView.revalidate();
    }
  }

  @Override
  protected boolean needsHiearachyInvalidated() {
    return false;
  }

  protected BorderLayout getLayout() {
    return (BorderLayout) ((TabBorderPane) view).getLayout();
  }

  static class TabBorderPane extends BorderLayoutView {
    public TabBorderPane() {
      super();
    }

    @Override
    public void layout(ParentView view, float width, float height) {
      super.layout(view, width, height);

      TabPaneComponent tpc = (TabPaneComponent) Component.fromView(this);

      if ((tpc == null) || Platform.isIOS() || tpc.isHorizontal()
          || tpc.tabsLayout.getTabPainter().isHandlesRightLeftRotation()) {
        return;
      }

      int tabsPadding = tpc.tabsLayout.getTabPainter().getTabsPadding();

      switch(tpc.tabsLayout.getTabPlacement()) {
        case LEFT : {
          float    h  = tpc.headerView.getWidth();
          UIInsets in = tpc.getInsets(null);

          height -= (in.top + in.bottom);
          tpc.headerView.setBounds(tabsPadding + in.left, height + in.top, height, h);

          break;
        }

        case RIGHT : {
          UIRectangle b  = tpc.headerView.getBounds();;
          UIInsets    in = tpc.getInsets(null);
          float       h  = b.width;

          height -= (in.top + in.bottom);
          tpc.headerView.setBounds(b.x + h, b.y, height, h);

          break;
        }

        default :
          break;
      }
    }
  }
}
