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
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import com.appnativa.jgoodies.forms.layout.FormLayout;

import java.util.HashMap;

/**
 *
 * @author Don DeCoteau
 */
public class ToolBarHolder extends FormsPanel implements iToolBarHolder {
  HashMap<String, iToolBar> toolbarMap = new HashMap<String, iToolBar>();
  private iToolBar          mainToolbar;

  public ToolBarHolder(iWidget context) {
    super(context, new FormLayout("FILL:DEFAULT:GROW(1.0)"));
  }

  /**
   * Creates a new instance of ToolBarHolder
   * @param cols the number of columns
   * @param rows the number of rows
   */
  public ToolBarHolder(iWidget context, int cols, int rows) {
    this(context);
    ensureGrid(cols, rows, false, true, 0, 0);
  }

  public void addToolBar(int col, int row, iToolBar tb) {
    addComponent(tb.getComponent(), col, row);

    String name = tb.getToolbarName();

    if (name != null) {
      toolbarMap.put(name, tb);
    }

    name = makeName(col, row);
    toolbarMap.put(name, tb);

    if (mainToolbar == null) {
      mainToolbar = tb;
    }
  }

  public static ToolBarHolder create(iContainer context, SPOTSet set) {
    ToolBar tc;
    int     len  = set.getCount();
    int     i    = 0;
    int     rows = 1;
    int     cols = 1;
    int     n;

    for (i = 0; i < len; i++) {
      tc = (ToolBar) set.getEx(i);
      n  = tc.row.intValue();

      if (n > rows) {
        rows = n;
      }

      n = tc.column.intValue();

      if (n > cols) {
        cols = n;
      }
    }

    ToolBarHolder p = new ToolBarHolder(context, cols, rows);
    ToolBarViewer tb;
    int           col, row;

    for (i = 0; i < len; i++) {
      tc = (ToolBar) set.getEx(i);

      if (!Platform.getAppContext().okForOS(tc)) {
        continue;
      }

      row = tc.row.intValue();
      col = tc.column.intValue();
      tb  = (ToolBarViewer) context.createWidget(tc);
      tb.setParent(context);
      p.addToolBar(col, row, tb);
    }

    return p;
  }

  @Override
  public void dispose() {
    if (toolbarMap != null) {
      toolbarMap.clear();
      toolbarMap = null;
    }

    super.dispose();
  }

  @Override
  public iToolBar removeToolBar(int col, int row) {
    String   name = makeName(col, row);
    iToolBar tb   = toolbarMap.remove(name);

    if (tb != null) {
      remove(tb.getComponent());
    }

    return tb;
  }

  @Override
  public void removeToolbars() {
    toolbarMap.clear();
    this.removeAll();
  }

  @Override
  public void toggleVisibility() {
    setVisible(!isVisible());
    revalidate();
  }

  @Override
  public void toggleVisibility(int row, int col) {
    String   name = makeName(col, row);
    iToolBar tb   = toolbarMap.get(name);

    if (tb != null) {
      tb.getComponent().setVisible(!tb.getComponent().isVisible());
      revalidate();
    }
  }

  public void update() {
    revalidate();
    repaint();
  }

  @Override
  public void setToolBar(iToolBar tb) {
    this.mainToolbar = tb;
    addToolBar(0, 0, tb);
    revalidate();
  }

  @Override
  public iToolBar setToolBar(int col, int row, iToolBar tb) {
    iToolBar rtb = removeToolBar(col, row);

    addToolBar(col, row, tb);
    update();

    return rtb;
  }

  @Override
  public void setVisible(boolean visible, int row, int col) {
    String   name = makeName(col, row);
    iToolBar tb   = toolbarMap.get(name);

    if (tb != null) {
      tb.getComponent().setVisible(visible);
      revalidate();
    }
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public iToolBar getToolBar() {
    return mainToolbar;
  }

  @Override
  public iToolBar getToolBar(int col, int row) {
    String name = makeName(col, row);

    return toolbarMap.get(name);
  }

  private String makeName(int col, int row) {
    return "(" + col + "," + row + ")";
  }
}
