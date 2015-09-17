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

package com.appnativa.rare.ui.table;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.JViewportEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneLayoutEx;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.JScrollBar;

public class TableScrollPane extends ScrollPaneEx {
  TableView table;

  public TableScrollPane() {
    this(new TableView());
  }

  public TableScrollPane(TableView table) {
    this.table = table;
    setViewportView(table);
    getVerticalScrollBar().setFocusable(false);
    getHorizontalScrollBar().setFocusable(false);
    setBackground(Platform.getUIDefaults().getColor("Rare.List.background"));
    setForeground(Platform.getUIDefaults().getColor("Rare.List.foreground"));
    setCorner(ScrollPaneLayoutEx.UPPER_RIGHT_CORNER, table.getHeader().createCornerRenderer(true));
    setCorner(ScrollPaneLayoutEx.RARE_SCROLLBAR_LR_CORNER, table.getHeader().createCornerRenderer(false));
    setCorner(ScrollPaneLayoutEx.LOWER_RIGHT_CORNER, table.getHeader().createCornerRenderer(false));
  }

  @Override
  public void disableScrolling() {
    super.disableScrolling();
    setCorner(ScrollPaneLayoutEx.RARE_SCROLLBAR_UR_CORNER, null);
    setCorner(ScrollPaneLayoutEx.RARE_SCROLLBAR_LR_CORNER, null);
  }

  @Override
  public void setRowHeaderView(Component view) {
    super.setRowHeaderView(view);

    if (getCorner(ScrollPaneLayoutEx.RARE_SCROLLBAR_LL_CORNER) == null) {
      setCorner(ScrollPaneLayoutEx.RARE_SCROLLBAR_LL_CORNER, table.getHeader().createCornerRenderer(false));
    }

    if (view instanceof TableScrollPane) {
      ((JViewportEx) rowHeader).setFixedViewPosition(true);
      syncTableScrollPane(view);
    }
  }

  @Override
  public void setRowFooterView(Component view) {
    super.setRowFooterView(view);
    syncTableScrollPane(view);

    if (view instanceof TableScrollPane) {
      syncTableScrollPane(view);
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if ((getHorizontalScrollBarPolicy() == ScrollPane.SCROLLBARS_NEVER) || table.getHeader().isGridView()) {
      JScrollBar sb = getVerticalScrollBar();

      if ((sb != null) && sb.isVisible()) {
        width -= sb.getWidth();
      }

      sb = getHorizontalScrollBar();

      if ((sb != null) && sb.isVisible()) {
        height -= sb.getHeight();
      }
    }

    table.updateColumnSizes(width, height);

    if (table.isKeepSelectionVisible()) {
      int n = table.getSelectedIndex();

      if (n != -1) {
        Rectangle r = table.getCellRect(n, 0, true);

        if ((r == null) ||!getViewport().getViewRect().contains(r)) {
          table.makeSelectionVisible();
        }
      }
    }
  }

  void syncTableScrollPane(final Component view) {
    if (view instanceof TableScrollPane) {
      final TableScrollPane sp = (TableScrollPane) view;
      final JViewportEx     vp = (JViewportEx) sp.getViewport();

      addSlaveViewport(vp, false);
    }
  }
}
