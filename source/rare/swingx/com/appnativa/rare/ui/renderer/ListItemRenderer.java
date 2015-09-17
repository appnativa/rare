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

package com.appnativa.rare.ui.renderer;

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformListView;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.iTableModel;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * An item render for List type components
 *
 * @author Don DeCoteau
 */
public class ListItemRenderer extends aListItemRenderer implements ListCellRenderer, TableCellRenderer {
  iPlatformListView                     listView;
  protected iPlatformRenderingComponent renderingComponent;
  protected JComponent                  blankRenderingComponent;

  /**
   * Constructs a new instance
   */
  public ListItemRenderer(iPlatformListView listView, boolean handleSelction) {
    super(handleSelction);
    this.listView       = listView;
    alwaysCallSetBorder = true;
  }

  /**
   * Constructs a new instance
   */
  public ListItemRenderer(iPlatformListView listView) {
    super(false);
    this.listView       = listView;
    alwaysCallSetBorder = true;
  }

  @Override
  public void setItemDescription(Column itemDescription) {
    super.setItemDescription(itemDescription);

    if (itemDescription != null) {
      iPlatformRenderingComponent rc = itemDescription.getCellRenderer();

      if (rc != null) {
        setRenderingComponent(rc);
      }
    }
  }

  public void setRenderingComponent(iPlatformRenderingComponent rc) {
    renderingComponent = rc;

    if ((rc != null) && (listView != null) &&!listView.isRowSizeFixed()) {
      rc.setWordWrap(true);
    }
  }

  @Override
  public java.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
          boolean cellHasFocus) {
    if (renderingComponent == null) {
      setRenderingComponent(new UILabelRenderer());
    }

    RenderableDataItem item = (RenderableDataItem) value;
    iPlatformComponent c    = item.getRenderingComponent();

    if (c != null) {
      return c.getView();
    }

    iPlatformRenderingComponent rc = renderingComponent;

    if (listView != null) {
      rc = listView.prepareRendererForReuse(rc, index, 0);
    }

    iPlatformComponent pc = Component.fromView(list);
    CharSequence       cs = configureRenderingComponent(pc, rc, (RenderableDataItem) value, index, isSelected,
                              cellHasFocus, null, null);

    return rc.getComponent(cs, (RenderableDataItem) value).getView();
  }

  public iPlatformRenderingComponent getRenderingComponent() {
    return renderingComponent;
  }

  @Override
  public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
    RenderableDataItem rowItem = (value == null)
                                 ? defaultItem
                                 : ((iTableModel) table.getModel()).getRow(row);
    Column             col     = ((iTableModel) table.getModel()).getColumn(column);

    if (renderingComponent == null) {
      renderingComponent = new UILabelRenderer();
    }

    iPlatformRenderingComponent rc = col.getCellRenderer();

    if (rc == null) {
      rc = renderingComponent;
    }

    if (listView != null) {
      rc = listView.prepareRendererForReuse(rc, row, column);
    }

    if (!col.isVisible()) {
      value = defaultItem;
    }

    RenderableDataItem item = (RenderableDataItem) value;

    if (!item.isVisible()) {
      return getBlankRenderingComponent();
    }

    iPlatformComponent comp = item.getRenderingComponent();

    if (comp == null) {
      iPlatformComponent pc = Component.fromView(table);
      CharSequence       cs = configureRenderingComponent(pc, rc, item, row, isSelected, hasFocus, col, rowItem);

      comp = rc.getComponent(cs, (RenderableDataItem) value);
    }

    return comp.getView();
  }

  JComponent getBlankRenderingComponent() {
    if (blankRenderingComponent == null) {
      blankRenderingComponent = new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
        }
        @Override
        public void revalidate() {}
        @Override
        public void invalidate() {}
      };
    }

    return blankRenderingComponent;
  }
}
