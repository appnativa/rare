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

import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.ScrollView;
/*-[
 #import "RAREAPTableView.h"
 #import "RAREAPTableColumn.h"
 #import "com/appnativa/rare/ui/RenderableDataItem.h"
 ]-*/
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public abstract class aTableComponent extends Container implements iTableComponent, iScrollerSupport {
  protected int          sortColumn = -1;
  protected boolean      descending = true;
  protected boolean      autoSizeRowsToFit;
  protected aTableHeader tableHeader;
  protected boolean      horizontalScroll;
  protected iTableModel  tableModel;
  protected TableStyle   tableStyle;
  protected TableView    tableView;
  protected int          oldWidth;
  protected TableType    tableType;

  public aTableComponent(ParentView view) {
    super(view);
  }

  protected aTableComponent() {
    super();
  }

  @Override
  public native int convertModelIndexToView(int index)
  /*-[
    RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
    NSArray* columns=table.tableColumns;
    NSInteger len=columns.count;
    RAREAPTableColumn* col;
    for(NSInteger i=0;i<len;i++) {
      col=(RAREAPTableColumn*)[columns objectAtIndex:i];
      if(col->modelIndex==index) {
        return (int)i;
      }
    }
    return -1;
  ]-*/
  ;

  @Override
  public native int convertViewIndexToModel(int index)
  /*-[
    return ((RAREAPTableColumn*)[self getAPTableColumnWithInt: index])->modelIndex;
  ]-*/
  ;

  @Override
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return new Column(title, value, type, data, icon);
  }

  @Override
  public void dispose() {
    if (tableModel != null) {
      tableModel.dispose();
    }

    super.dispose();

    if (tableHeader != null) {
      tableHeader.dispose();
    }

    tableStyle  = null;
    tableModel  = null;
    tableHeader = null;
    tableView   = null;
    tableStyle  = null;
  }

  /**
   * Moves the column column to the position currently occupied by the column
   * targetColumn in the view. The old column at targetColumn is shifted left or
   * right to make room.
   *
   * @param column
   *          the index of column to be moved
   * @param targetColumn
   *          the new index of the column
   *
   */
  @Override
  public native void moveColumn(int column, int targetColumn)
  /*-[
    [((RAREAPTableView*)tableView_->proxy_) moveColumn:column toColumn:targetColumn];
  ]-*/
  ;

  @Override
  public void repaint() {
    super.repaint();
    tableHeader.repaint();
  }
  
  public void repaintVisibleRows() {
    tableHeader.repaint();
    tableView.repaintVisibleRows();
  }
  
  @Override
  public void resetTable(List<Column> columns, List<RenderableDataItem> rows) {
    FilterableList<RenderableDataItem> l;

    if ((rows == null) || (rows.size() < 10)) {
      l = new FilterableList<RenderableDataItem>();
    } else {
      l = new FilterableList<RenderableDataItem>(rows.size());
    }

    if (rows != null) {
      l.addAll(rows);
    }

    iFilterableList<Column> cols = new FilterableList<Column>(columns.size());

    cols.addAll(columns);
    resetTableEx(cols, l);
  }

  @Override
  public void revalidate() {
    tableHeader.getView().revalidate();
    tableView.revalidate();
    super.revalidate();
  }

  public void sizeColumnsToFit() {
    tableView.sizeColumnsToFit();
  }

  @Override
  public void sizeRowsToFit() {
    tableView.sizeRowsToFit();
  }

  @Override
  public void sort(Comparator c) {
    tableModel.sort(c);
  }

  @Override
  public void sort(int col) {
    if (sortColumn == col) {
      descending = !descending;
    } else {
      descending = false;
    }

    sort(col, descending, false);
  }

  @Override
  public void sort(int col, boolean descending, boolean useLinkedData) {
    sortColumn      = col;
    this.descending = descending;
    tableModel.sort(col, descending, useLinkedData);
    tableHeader.setSortColumn(col, descending);
  }

  @Override
  public boolean isMultiTableComponent() {
    return false;
  }

  public void sortEx(Comparator c) {
    tableModel.sort(c);
  }

  public void sortEx(int col, boolean descending, boolean useLinkedData) {
    sortColumn      = col;
    this.descending = descending;
    tableModel.sort(col, descending, useLinkedData);
    tableHeader.setSortColumn(col, descending);
  }

  @Override
  public void stopEditing() {}

  @Override
  public void setBackground(UIColor bg) {
    super.setBackground(bg);

    if (tableView != view) {
      getTable().setBackground(bg);
    }
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    col = convertModelIndexToView(col);
    tableHeader.setColumnIcon(col, icon);
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    col = convertModelIndexToView(col);
    tableHeader.setColumnTitle(col, title);
  }

  @Override
  public void setColumnVisible(int col, boolean visible) {
    col = convertModelIndexToView(col);

    if (tableHeader.isColumnVisible(col) != visible) {
      tableHeader.setColumnVisible(col, visible);
      tableView.sizeToFit();
    }
  }

  @Override
  public boolean isAtBottomEdge() {
    return tableView.isAtBottomEdge();
  }

  @Override
  public boolean isAtLeftEdge() {
    return tableView.isAtLeftEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return tableView.isAtRightEdge();
  }

  @Override
  public boolean isAtTopEdge() {
    return tableView.isAtTopEdge();
  }

  @Override
  public void setFont(UIFont f) {
    getTable().setFont(f);
  }

  @Override
  public void setForeground(UIColor fg) {
    getTable().setForeground(fg);
  }

  @Override
  public void setGridViewType(GridViewType type) {
    tableHeader.setGridViewType(type);

    if (type == GridViewType.VERTICAL_WRAP) {
      horizontalScroll = false;
    } else if (view instanceof ScrollView) {
      horizontalScroll = true;
    }
  }

  @Override
  public void setHeaderBackground(PaintBucket pb) {
    tableHeader.setComponentPainter(null);
    pb.install(tableHeader, true);
  }

  public void setRowHeight(int height) {
    tableView.setRowHeight(height);
  }

  @Override
  public void setRowHeight(int row, int height) {
    tableModel.get(row).setHeight(height);
    tableView.rowChanged(row);
  }

  @Override
  public void setShowHorizontalLines(boolean b) {}

  @Override
  public void setShowVerticalLines(boolean b) {}

  @Override
  public void setSortColumn(int sortColumn, boolean descending) {
    this.sortColumn = sortColumn;
    this.descending = descending;
    tableHeader.setSortColumn(sortColumn, descending);
  }

  @Override
  public void setStyle(TableStyle style) {
    tableStyle = style;

    if (style.headerCellPainter != null) {
      tableHeader.setHeaderCellPainter(style.headerCellPainter);
    }

    if (style.headerFont != null) {
      tableHeader.setFont(style.headerFont);
    }

    if (style.headerForeground != null) {
      tableHeader.setForeground(style.headerForeground);
    }

    tableHeader.setSortingAllowed(style.columnSortingAllowed);

    if (style.backgroundHilite != null) {
      tableView.setAlternatingColor((style.backgroundHiliteColor == null)
                                    ? new UIColor(247, 247, 247)
                                    : style.backgroundHiliteColor);

      if (style.backgroundHilite == TableStyle.BackgroundHighlight.COLUMN) {
        tableView.setAlternatingColumns(true);
      }
    }

    if (style.showHorizontalLines || style.showVerticalLines) {
      tableView.setShowGridLines(style.showHorizontalLines, style.showVerticalLines, style.gridColor,
                                 style.gridLineStroke);
    }

    if (style.headerMarginColor != null) {
      tableHeader.setMarginColor(style.headerMarginColor);
    }

    if (style.headerBottomMarginColor != null) {
      tableHeader.setBottomMarginColor(style.headerBottomMarginColor);
    }
  }

  @Override
  public void setTable() {
    tableHeader.setColumns(tableModel.getColumns());
    tableView.setListModel(tableModel);
  }

  @Override
  public void setWidget(iWidget widget) {
    super.setWidget(widget);
    tableView.getComponent().setWidget(widget);
    tableHeader.setWidget(widget);
    tableModel.setWidget(widget);
  }

  @Override
  public UIRectangle getCellRect(int row, int column, boolean includeMargin) {
    return tableView.getCellRect(row, column, includeMargin);
  }

  public Column getColumnAt(int index) {
    return tableHeader.getColumnAt(index);
  }

  @Override
  public int getColumnCount() {
    return tableModel.getColumnCount();
  }

  @Override
  public UIFont getFontEx() {
    return (tableView == null)
           ? null
           : tableView.getFont();
  }

  @Override
  public GridViewType getGridViewType() {
    return tableHeader.getGridViewType();
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return tableView.getItemRenderer();
  }

  @Override
  public iTableModel getModel() {
    return tableModel;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }

  public int getRowHeight() {
    return tableView.getRowHeight();
  }

  @Override
  public int getSelectedColumn() {
    return tableHeader.getSelectedColumn();
  }

  @Override
  public int getSelectedColumnCount() {
    return tableHeader.getSelectedColumnCount();
  }

  @Override
  public int[] getSelectedColumns() {
    return tableHeader.getSelectedColumnIndices();
  }

  @Override
  public int getSortColumn() {
    return sortColumn;
  }

  @Override
  public aTableHeader getTableHeader() {
    return tableHeader;
  }

  public iTableModel getTableModel() {
    return tableModel;
  }

  public TableView getTableView() {
    return tableView;
  }

  @Override
  public int getVisibleColumnCount() {
    return tableHeader.getVisibleColumnCount();
  }

  @Override
  public boolean hasHadInteraction() {
    return view.hasHadInteraction() || tableView.hasHadInteraction();
  }

  @Override
  public boolean isAutoSizeRows() {
    return autoSizeRowsToFit;
  }

  @Override
  public boolean isEditing() {
    return false;
  }

  @Override
  public boolean isSortDescending() {
    return descending;
  }

  protected void createTableModel() {
    tableModel = new DataItemTableModel();
  }

  protected void initialize(TableView table, Table cfg) {
    tableView   = table;
    tableHeader = table.getHeader();

    boolean grid = cfg.displayAsGridView.booleanValue();

    if (grid) {
      setGridViewType(GridViewType.valueOf(cfg.gridViewType.stringValue().toUpperCase(Locale.US)));
      tableHeader.setVisible(false);
    } else if (!cfg.showStandardColumnHeader.booleanValue()) {
      tableHeader.setVisible(false);
    }

    if (cfg.headerHeight.spot_valueWasSet()) {
      tableHeader.setHeaderHeight(cfg.headerHeight.getValue());
    }

    table.setExtendBackgroundRendering(cfg.extendBackgroundRendering.booleanValue());

    if (grid) {
      tableHeader.setColumnSelectionAllowed(true);
    } else {
      table.setAutoSizeColumns(cfg.autoSizeColumnsToFit.booleanValue());
    }

    table.setAutoSizeRows(autoSizeRowsToFit = cfg.autoSizeRowsToFit.booleanValue());
    createTableModel();
  }

  protected void resetTableEx(iFilterableList<Column> columns, iFilterableList<RenderableDataItem> rows) {
    tableModel.resetModel(columns, rows);
  }

  protected native Object getAPTableColumn(int index)
  /*-[
    return [((RAREAPTableView*)tableView_->proxy_).tableColumns objectAtIndex:index];
  ]-*/
  ;

  protected native Object getAPTableColumnForModelIndex(int index)
  /*-[
    RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
    NSArray* columns=table.tableColumns;
    NSInteger len=columns.count;
    RAREAPTableColumn* col;
    for(NSInteger i=0;i<len;i++) {
      col=(RAREAPTableColumn*)[columns objectAtIndex:i];
      if(col->modelIndex==index) {
        return col;
      }
    }
    return nil;
  ]-*/
  ;

  @Override
  protected iPlatformComponentPainter getComponentPainterEx() {
    return getTable().getComponentPainter();
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size) {
    tableHeader.getMinimumSize(size);

    float w = size.width;
    float h = size.height;

    tableView.getMinimumSize(size);
    size.width = Math.max(w, size.width);

    if (tableHeader.isVisible()) {
      size.height += h;
    }

    if (horizontalScroll) {
      size.width = 100;
    }
  }

  @Override
  protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
    oldWidth = 0;

    if (maxWidth > 0) {
      tableHeader.getSize(size, true, maxWidth);
    } else {
      tableHeader.getPreferredSize(size);
    }

    float w = size.width;
    float h = size.height;

    tableView.getPreferredSize(size, 0);
    size.width = Math.max(w, size.width);

    if (tableHeader.isVisible()) {
      size.height += h;
    }
  }

  protected abstract iPlatformComponent getTable();

  public void makeSelectionVisible() {
    int index = tableView.getSelectedIndex();

    if (index != -1) {
      if (horizontalScroll && tableView.isColumnSelectionAllowed()) {
        int col = getSelectedColumn();

        if (col == -1) {
          col = 0;
        }

        UIRectangle r  = getViewRect();
        UIRectangle r2 = getCellRect(index, col, true);

        r2.height = Math.min(r2.height, (int) tableView.getHeight());

        if (!r.contains(r2)) {
          float vex = r.x + r.width;
          float vey = r.y + r.height;
          float cex = r2.x + r2.width;
          float cey = r2.y + r2.height;
          float x   = cex - vex;
          float y   = cey - vey;

          if (y < 0) {
            y = -r.y;
          }

          if (x < 0) {
            x = -r.x;
          }

          if ((x != 0) || (y != 0)) {
            scrollBy(x, y);
          }
        }

        tableView.repaintRow(index);
      } else {
        tableView.scrollRowToVisible(index);
        tableView.repaintRow(index);
      }
    }
  }

  protected abstract void scrollBy(float x, float y);

  protected static boolean isHorizontalScollEnabled(Table cfg) {
    if (cfg.displayAsGridView.booleanValue()) {
      return true;
    }

    if (cfg.autoResizeMode.intValue() == Table.CAutoResizeMode.none) {
      return true;
    }

    if ((cfg.getScrollPane() != null) && cfg.getScrollPane().isHorizontalScrollEnabled()) {
      return true;
    }

    return false;
  }

  @Override
  public iTableComponent getMainTable() {
    return this;
  }

  @Override
  public iTableComponent getRowHeaderTable() {
    return null;
  }

  @Override
  public iTableComponent getRowFooterTable() {
    return null;
  }

  @Override
  public iTableComponent getColumnHeaderTable() {
    return null;
  }

  @Override
  public iTableComponent getColumnFooterTable() {
    return null;
  }

  @Override
  public boolean isMainTable() {
    TableType tt = getTableType();

    return (tt == null) || (tt == TableType.MAIN);
  }

  @Override
  public TableType getTableType() {
    return tableView.getTableType();
  }

  @Override
  public void setTableType(TableType type) {
    tableView.setTableType(type);
  }
}
