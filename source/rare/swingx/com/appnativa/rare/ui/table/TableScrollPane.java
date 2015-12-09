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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.JTableHeader;

import com.appnativa.rare.platform.swing.ui.view.JViewportEx;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;

public class TableScrollPane extends ScrollPaneEx {
  TableView table;
  Component columnHeaderView;

  public TableScrollPane() {
    this(new TableView());
  }

  public TableScrollPane(TableView table) {
    this.table = table;
    setViewportView(table);
    getVerticalScrollBar().setFocusable(false);
    getHorizontalScrollBar().setFocusable(false);
    setCorner(ScrollPaneLayout.UPPER_RIGHT_CORNER, table.getHeader().createCornerRenderer(true));
  }

  @Override
  public void disableScrolling() {
    super.disableScrolling();
  }

  @Override
  public void setRowHeaderView(Component view) {
    super.setRowHeaderView(view);

    if (view instanceof TableScrollPane) {
      ((JViewportEx) rowHeader).setFixedViewPosition(true);
      syncTableScrollPane(view);
    } else {
      setCorner(ScrollPaneLayout.UPPER_LEFT_CORNER, table.getHeader().createCornerRenderer(false));
    }
  }

  @Override
  public void setRowFooterView(Component view) {
    super.setRowFooterView(view);

    if (view instanceof TableScrollPane) {
      ((JViewportEx) rowFooter).setFixedViewPosition(true);
      syncTableScrollPane(view);
    } else {
      setCorner(ScrollPaneLayout.UPPER_RIGHT_CORNER, table.getHeader().createCornerRenderer(false));
    }
  }

  @Override
  public void setColumnHeaderView(Component view) {
    if (!(view instanceof JTableHeader)) {
      if (columnHeader == null) {
        columnHeaderView = view;
      } else {
        updateCompositeHeaderView(view);
      }

      return;
    }

    super.setColumnHeaderView(view);

    if (columnHeaderView != null) {
      updateCompositeHeaderView(columnHeaderView);
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

  private void updateCompositeHeaderView(Component view) {
    Component c = columnHeader.getView();

    if (c instanceof JTableHeader) {
      if (view != null) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(c, BorderLayout.CENTER);
        panel.add(view, BorderLayout.NORTH);
        columnHeader.setView(panel);
      }
    } else if (c instanceof JPanel) {
      JPanel panel = (JPanel) c;

      if (columnHeaderView != null) {
        panel.remove(columnHeaderView);
      }

      if (view != null) {
        panel.add(view, BorderLayout.NORTH);
      }
    }

    columnHeaderView = view;
  }

  void syncTableScrollPane(final Component view) {
    if (view instanceof TableScrollPane) {
      final TableScrollPane sp = (TableScrollPane) view;
      final JViewportEx     vp = (JViewportEx) sp.getViewport();

      addSlaveViewport(vp, false);
    }
  }
}
