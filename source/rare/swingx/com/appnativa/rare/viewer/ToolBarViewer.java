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

package com.appnativa.rare.viewer;

import com.appnativa.rare.platform.swing.ui.view.SeparatorView;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.LinearPanel;
import com.appnativa.rare.widget.BeanWidget;

import javax.swing.JComponent;

/**
 * A viewer that holds a set of commonly used actions that can be general or
 * context sensitive
 *
 * @author Don DeCoteau
 */
public class ToolBarViewer extends aToolBarViewer {
  public ToolBarViewer() {
    super();
  }

  public ToolBarViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected void createComponents(boolean horizontal) {
    formComponent = dataComponent = new LinearPanel(this, horizontal);

    if (isDesignMode()) {
      ((Container) formComponent).setDefaultMinimumSize(32, 24, true);
    }
  }

  @Override
  protected void setParentHorizontal(BeanWidget widget, boolean horizontal) {
    JComponent c = widget.getDataComponent().getView();

    if (c instanceof SeparatorView) {
      ((SeparatorView) c).setHorizontal(!horizontal);
    }
  }
}
