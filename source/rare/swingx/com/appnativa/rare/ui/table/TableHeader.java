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

import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.platform.swing.ui.view.LabelRenderer;
import com.appnativa.rare.ui.BorderUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.aUIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.ui.renderer.ListItemRenderer;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.renderer.UITextAreaRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableHeader extends aTableHeader implements TableCellRenderer {
  public static final String USE_TEXTAREA_PROPERTY = "__RARE_TABLEHEADER_USE_TEXTAREA__";

  static {
    columnSizePad = ScreenUtils.PLATFORM_PIXELS_4;
  }

  ListItemRenderer        itemRenderer = new ListItemRenderer(null, true);
  RenderableDataItem      colItem      = new RenderableDataItem();
  Column                  colCol       = new Column();
  TableHeaderView         headerView;
  TableView               tableview;
  private boolean         columnSizing;
  private boolean         headerBgSet;
  private iTableComponent tableComponent;

  public TableHeader(TableView table) {
    super();
    tableview  = table;
    headerView = (TableHeaderView) table.getTableHeader();
    setView(headerView);
    renderingComponent = new UILabelRenderer(new ColumnLabelRenderer());
    this.addMouseListener(new MouseListener());
    itemRenderer.setInsets(new UIInsets(2));
  }

  @Override
  public boolean isColumnSelectionAllowed() {
    return headerView.getColumnModel().getColumnSelectionAllowed();
  }

  @Override
  public boolean handleMouseRelease(MouseEvent e) {
    if (!isEnabled()) {
      return true;
    }

    if (!columnSizing) {
      return true;
    }

    columnSizing = false;

    if (tableComponent.isAutoSizeRows()) {
      tableComponent.sizeRowsToFit();
    }

    return false;
  }

  @Override
  public int getSelectedColumn() {
    return tableview.getSelectedColumnIndex();
  }

  @Override
  public int[] getSelectedColumnIndices() {
    return tableview.getSelectedColumnIndices();
  }

  @Override
  public void setSelectedIndex(int index) {
    tableview.setSelectedColumnIndex(index);
  }

  @Override
  public void setSelectedIndices(int[] indices) {
    tableview.setSelectedIndices(indices);
  }

  public void moveColumn(int column, int targetColumn) {
    headerView.getColumnModel().moveColumn(column, targetColumn);
    columnMoved(column, targetColumn);
  }

  @Override
  public boolean sizeColumnsToFitTableData() {
    boolean sized = super.sizeColumnsToFitTableData();

    if (sized && (preferredSize != null)) {
      headerView.setPreferredSize(new Dimension(preferredSize.intWidth(), preferredSize.intHeight()));
    }

    if (sized) {
      updateColumnModel();
    }

    return sized;
  }

  @Override
  protected void updateGridColumnWidths(int width) {
    super.updateGridColumnWidths(width);
    updateColumnModel();
  }

  public void updateColumnModel() {
    TableColumnModel cm  = headerView.getColumnModel();
    int              len = cm.getColumnCount();

    for (int i = 0; i < len; i++) {
      TableColumn tc  = cm.getColumn(i);
      int         n   = tc.getModelIndex();
      Column      col = getColumnAt(n);

      if (col.isVisible()) {
        if (col.sizeFixed || (gridViewMode != null)) {
          tc.setMaxWidth(col.getWidth());
          tc.setMinWidth(col.getWidth());
          tc.setResizable(false);
        } else {
          n = (int) col.maxWidth;

          if (n == 0) {
            n = Short.MAX_VALUE;
          }

          tc.setMaxWidth(n);
          tc.setMinWidth((int) col.minWidth);
        }

        tc.setWidth(col.getWidth());
        tc.setPreferredWidth(col.getWidth());
        tc.setHeaderValue(col.getColumnTitle());
      } else {
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
        tc.setWidth(0);
        tc.setPreferredWidth(0);
        tc.setHeaderValue("");
      }
    }

    if (measuredHeight > 0) {
      Dimension d = headerView.getPreferredSize();

      d.height = Math.max(d.height, measuredHeight);
      headerView.setPreferredSize(d);
    }

    tableview.getJTable().repaint();
  }

  /**
   *
   * @param column
   *          the data index of the column to update
   */
  public void updateColumnModel(int column) {
    Column           col = getColumnAt(column);
    TableColumnModel cm  = headerView.getColumnModel();
    int              n   = tableview.getJTable().convertColumnIndexToView(column);
    TableColumn      tc  = cm.getColumn(n);

    if (col.isVisible()) {
      if (col.sizeFixed) {
        tc.setMaxWidth(col.getWidth());
        tc.setMinWidth(col.getWidth());
        tc.setResizable(false);
      } else {
        n = (int) col.maxWidth;

        if (n == 0) {
          n = Short.MAX_VALUE;
        }

        tc.setMaxWidth(n);
        tc.setMinWidth((int) col.minWidth);
      }

      tc.setPreferredWidth(col.getWidth());
      tc.setWidth(col.getWidth());
      tc.setHeaderValue(col.getColumnTitle());
    } else {
      tc.setMinWidth(0);
      tc.setMaxWidth(0);
      tc.setWidth(0);
      tc.setPreferredWidth(0);
      tc.setHeaderValue("");
    }

    if (measuredHeight > 0) {
      Dimension d = headerView.getPreferredSize();

      d.height = Math.max(d.height, measuredHeight);
      headerView.setPreferredSize(d);
    }

    tableview.resizeAndRepaint();
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    int index = tableview.getJTable().convertColumnIndexToView(col);

    if (index != -1) {
      Column c = columns[index];

      c.setHeaderIcon(icon);
      updateColumnModel(index);
    }
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    int index = tableview.getJTable().convertColumnIndexToView(col);

    if (index != -1) {
      Column c = columns[index];

      c.setColumnTitle(title);
      updateColumnModel(index);
    }
  }

  @Override
  protected void setColumnVisibleEx(int col, boolean visible) {
    Column c = columns[col];

    c.setVisible(visible);
    updateColumnModel(col);
  }

  public void setTableComponent(iTableComponent tc) {
    tableComponent = tc;
  }

  @Override
  public Column getColumnForViewAt(int viewColumn) {
    return columns[headerView.getColumnModel().getColumn(viewColumn).getModelIndex()];
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    return headerView.getColumnModel().getColumnIndexAtX((int) x);
  }

  @Override
  public int getSpanWidth(int start, int span) {
    int              width = 0;
    TableColumnModel cm    = headerView.getColumnModel();
    int              len   = cm.getColumnCount();    // columns.length;

    if (span == -1) {
      span = len;
    }

    span += start;

    if (span > len) {
      span = len;
    }

    int d = ScreenUtils.PLATFORM_PIXELS_1;

    while(start < span) {
      TableColumn col = cm.getColumn(start++);
      Column      c   = columns[col.getModelIndex()];

      if (c.isVisible()) {
        width += col.getPreferredWidth() + d;
      }
    }

    return width;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
          int row, int column) {
    TableColumn tc  = headerView.getColumnModel().getColumn(column);
    Column      col = getColumnAt(tc.getModelIndex());

    if (col.isVisible()) {
      colItem.setValue(col.getColumnTitle());
      colItem.setIcon(col.getHeaderIcon());

      if (column == pressedColumn) {
        PaintBucket p = col.getHeaderSelectionPainter();

        if (p == null) {
          p = getPressedPainter();
        }

        colCol.setItemPainter(p);
      } else {
        colCol.setItemPainter(col.getHeaderPainter());
      }

      colCol.wordWrap = col.headerWordWrap;
      colCol.setHorizontalAlignment(col.getHeaderHorizontalAlignment());
      colCol.setIconPosition(col.getHeaderIconPosition());
    } else {
      colItem.setValue("");
      colItem.setIcon(null);
      colCol.setItemPainter(null);
      colCol.setItemSelectionPainter(null);
      colItem.setBorder(null);
    }

    iPlatformRenderingComponent rc = col.getHeaderCellRenderer();

    if (rc == null) {
      rc = renderingComponent;

      ((ColumnLabelRenderer) rc.getComponent().getView()).dataIndex = tc.getModelIndex();
    }

    rc.prepareForReuse(row, column);
    // isSelected = pressedColumn == column;

    CharSequence cs = itemRenderer.configureRenderingComponent(this, rc, colItem, row, false, false, colCol, null);

    return rc.getComponent(cs, col).getView();
  }

  @Override
  public iTableComponent getTableComponent() {
    return tableComponent;
  }

  @Override
  protected boolean handleMousePress(MouseEvent e) {
    columnSizing = false;

    TableColumn col = headerView.getResizingColumn();

    if (col == null) {
      return true;
    }

    if ((e.getClickCount() > 1) && col.getResizable()) {
      int n = col.getModelIndex();

      ((TableComponent) tableComponent).sizeColumnToFit(n);
    } else {
      columnSizing = true;
    }

    return false;
  }

  @Override
  protected void tableHadInteraction() {
    hadInteraction = true;
    ((TableComponent) tableComponent).repaint();
  }

  @Override
  protected void setColumnPressed(int col, boolean pressed) {
    headerView.repaint(headerView.getHeaderRect(col));
  }

  @Override
  protected void setColumnsEx() {
    if (gridViewMode == null) {
      if ((componentPainter == null) &&!headerBgSet && (headerCellPainter == null)) {
        headerBgSet = true;

        PaintBucket pb = TableHelper.getDefaultPainter(getBackgroundEx());

        pb.install(this);
      }

      headerView.setDefaultRenderer(this);

      if (showHeaderMargin) {
        headerView.setMarginColor(marginColor);
      }

      headerView.setBottomMarginColor(bottomMarginColor);

      if ((headerCellPainter == null) || (headerCellPainter.getBorder() == null)) {
        itemRenderer.setInsets(((TableComponent) tableComponent).getTableView().getItemRenderer().getInsets());
      } else {
        itemRenderer.setInsets(null);
      }

      int len = columns.length;

      for (int i = 0; i < len; i++) {
        Boolean b = (Boolean) columns[i].getCustomProperty(TableHeader.USE_TEXTAREA_PROPERTY);

        if ((b != null) && b.booleanValue()) {
          columns[i].setHeaderCellRenderer(new ColumnTextAreaRenderer(i));
        }
      }
    }
  }

  public JComponent createCornerRenderer(boolean top) {
    return new CornerRenderer(top);
  }

  public class ColumnLabelRenderer extends LabelRenderer {
    int     dataIndex;

    protected void drawSortIcon(Graphics g, iPlatformIcon icon) {
      int w = icon.getIconWidth();
      int x = (getWidth() - w) / 2;
      int y = getY() + ScreenUtils.PLATFORM_PIXELS_1;

      icon.paintIcon(this, g, x, y);
    }

    public boolean isPressed() {
      return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
      PaintBucket pb     = null;
      Column      column = columns[dataIndex];

      if (isPressed()) {
        pb = column.getHeaderSelectionPainter();

        if (pb == null) {
          pb = getPressedPainter();
        }
      }

      if (pb == null) {
        pb = column.getHeaderPainter();
      }

      if (pb == null) {
        pb = headerCellPainter;
      }

      if (pb != null) {
        aUIComponentPainter.paint(graphics, 0, 0, getWidth(), getHeight(), pb, true);
      }

      if (sortColumn == dataIndex) {
        drawSortIcon(g, getSortIcon(descending));
      }

      super.paintComponent(g);
    }
  }


  public class ColumnTextAreaRenderer extends UITextAreaRenderer {
    int dataIndex;

    public ColumnTextAreaRenderer(int index) {
      dataIndex = index;
      setVerticalAlignment(SwingConstants.CENTER);
    }

    protected void drawSortIcon(Graphics g, iPlatformIcon icon) {
      int w = icon.getIconWidth();
      int x = (getWidth() - w) / 2;
      int y = getY() + ScreenUtils.PLATFORM_PIXELS_1;

      icon.paintIcon(this, g, x, y);
    }

    public boolean isPressed() {
      return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
      PaintBucket pb     = null;
      Column      column = columns[dataIndex];

      if (isPressed()) {
        pb = column.getHeaderSelectionPainter();

        if (pb == null) {
          pb = getPressedPainter();
        }
      }

      if (pb == null) {
        pb = column.getHeaderPainter();
      }

      if (pb == null) {
        pb = headerCellPainter;
      }

      if (pb != null) {
        aUIComponentPainter.paint(graphics, 0, 0, getWidth(), getHeight(), pb);
      }

      if (sortColumn == dataIndex) {
        drawSortIcon(g, getSortIcon(descending));
      }

      super.paintComponent(g);
    }
  }


  class CornerRenderer extends JPanelEx {
    boolean top;

    CornerRenderer(boolean top) {
      this.top = top;
      setFocusable(false);
      setBorder(BorderUtils.EMPTY_FOCUS_AWARE_BORDER);
    }

    @Override
    public void paint(Graphics g) {
      if (top) {
        if (componentPainter == null) {
          componentPainter = (headerCellPainter == null)
                             ? null
                             : headerCellPainter.getCachedComponentPainter();

          if (componentPainter == null) {
            componentPainter = headerView.getComponentPainter();
          }

          if (componentPainter != null) {
            componentPainter = (iPlatformComponentPainter) componentPainter.clone();
            componentPainter.setBorder(null);
          }
        }
      }

      super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
      graphics.setComponent(TableHeader.this);
      super.paintComponent(g);

      if (!top) {
        g.setColor(headerView.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
      }

      if (top && (bottomMarginColor != null)) {
        Graphics2D g2 = (Graphics2D) g;
        Color      oc = g2.getColor();
        Stroke     s  = g2.getStroke();

        g2.setStroke(SwingHelper.SOLID_STROKE);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(bottomMarginColor);
        g2.drawLine(0, h - 1, w - 1, h - 1);
        g2.setStroke(s);
        g2.setColor(oc);
      }
    }
  }
}
