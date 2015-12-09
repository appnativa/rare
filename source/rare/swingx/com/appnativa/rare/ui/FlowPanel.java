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

import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.widget.iWidget;

import javax.swing.JViewport;

public class FlowPanel extends aFlowPanel {
  public FlowPanel(iWidget context) {
    this(new FlowPanelView());
    needsMultitplePasses = true;
  }

  public FlowPanel(Object view) {
    super(view);
  }

  static class FlowPanelView extends UtilityPanel {
    @Override
    public void addNotify() {
      super.addNotify();

      if (getParent() instanceof JViewport) {
        FlowPanel p = (FlowPanel) Component.fromView(this);

        p.needsMultitplePasses = false;
      }
    }

    @Override
    public void getMinimumSize(UIDimension size, int maxWidth) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.getMinimumSize(size);
    }

    public UIDimension getPreferrredSize(UIDimension size, float maxWidth) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.getPreferredSize(size, maxWidth);

      return size;
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.layout(width, height);
    }
  }
}
