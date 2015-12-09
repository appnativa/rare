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

public class AbsolutePanel extends aAbsolutePanel {
  public AbsolutePanel(iWidget Context) {
    this(new AbsolutePanelView());
  }

  public AbsolutePanel(Object view) {
    super(view);
  }

  static class AbsolutePanelView extends UtilityPanel {
    @Override
    public void getMinimumSize(UIDimension size, int maxWidth) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.getMinimumSize(size);
    }

    public void getPreferredSize(UIDimension size) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.getPreferredSize(size);
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.layout(width, height);
    }
  }
}
