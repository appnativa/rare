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

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.util.FilterableList;
import com.appnativa.util.IntList;

import java.util.Comparator;
import java.util.List;

public class MultiTableTableComponent implements iTableComponent, iDataModelListener {
  protected iTableComponent mainTable;
  protected iTableComponent columnHeaderTable;
  protected iTableComponent columnFooterTable;
  protected iTableComponent rowHeaderTable;
  protected iTableComponent rowFooterTable;
  int                       rowHeaderColumnCount;
  int                       rowFooterColumnStart;
  int                       mainColumnCount;
  MultiDataItemTableModel   tableModel;
  private IntList           colList;
  iPlatformComponent        component;

  public MultiTableTableComponent(iPlatformComponent component, MultiDataItemTableModel model) {
    this.component = component;
    tableModel     = model;
    model.addDataModelListener(this);
  }

  @Override
  public int convertModelIndexToView(int index) {
    if (index < rowHeaderColumnCount) {
      return rowHeaderTable.convertModelIndexToView(index);
    }

    if (index < rowFooterColumnStart) {
      return mainTable.convertModelIndexToView(index - rowHeaderColumnCount);
    }

    return rowFooterTable.convertModelIndexToView(index - rowHeaderColumnCount - mainColumnCount);
  }

  @Override
  public int convertViewIndexToModel(int index) {
    Column c = null;

    if (index < rowHeaderColumnCount) {
      c = rowHeaderTable.getTableHeader().getColumnForViewAt(index);
    } else if (index < rowFooterColumnStart) {
      c = mainTable.getTableHeader().getColumnForViewAt(index - rowHeaderColumnCount);
    } else {
      c = rowFooterTable.getTableHeader().getColumnForViewAt(index - rowHeaderColumnCount - mainColumnCount);
    }

    return (c == null)
           ? -1
           : tableModel.getColumns().indexOf(c);
  }

  @Override
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return mainTable.createColumn(title, value, type, data, icon);
  }

  @Override
  public void dispose() {
    if (rowHeaderTable != null) {
      rowHeaderTable.dispose();
    }

    if (rowFooterTable != null) {
      rowFooterTable.dispose();
    }

    if (tableModel != null) {
      tableModel.dispose();
    }

    mainTable         = null;
    columnHeaderTable = null;
    columnFooterTable = null;
    rowHeaderTable    = null;
    rowFooterTable    = null;
    tableModel        = null;
    component         = null;
  }

  @Override
  public UIRectangle getCellRect(int row, int column, boolean includeMargin) {
    if (rowHeaderTable != null) {
      if (column < tableModel.getHeaderColumnCount()) {
        return rowHeaderTable.getCellRect(row, column, includeMargin);
      } else {
        column -= tableModel.getHeaderColumnCount();
      }
    }

    if (column < mainTable.getColumnCount()) {
      return mainTable.getCellRect(row, column, includeMargin);
    } else {
      column -= mainTable.getColumnCount();
    }

    if (rowFooterTable != null) {
      return rowFooterTable.getCellRect(row, column, includeMargin);
    }

    return null;
  }

  @Override
  public int getColumnCount() {
    return tableModel.getColumnCount();
  }

  @Override
  public iTableComponent getColumnFooterTable() {
    return columnFooterTable;
  }

  @Override
  public iTableComponent getColumnHeaderTable() {
    return columnHeaderTable;
  }

  @Override
  public int getColumnIndexAt(float x, float y) {
    int w;

    if (rowHeaderTable != null) {
      w = rowHeaderTable.getPlatformComponent().getWidth();

      if (x < w) {
        return rowHeaderTable.getColumnIndexAt(x, y);
      }

      x -= w;
    }

    w = mainTable.getPlatformComponent().getWidth();

    if (x < w) {
      int col = mainTable.getColumnIndexAt(x, y);

      return (col == -1)
             ? -1
             : col + tableModel.getHeaderColumnCount();
    }

    if (rowFooterTable != null) {
      x -= w;

      int col = rowFooterTable.getColumnIndexAt(x, y);

      if (col != -1) {
        return col + tableModel.getFooterColumnStart();
      }
    }

    return -1;
  }

  int getColumnIndexForModelColumn(int column) {
    if (column < tableModel.getHeaderColumnCount()) {
      return column;
    } else {
      column -= tableModel.getHeaderColumnCount();
    }

    if (column < mainTable.getColumnCount()) {
      return column;
    } else {
      column -= mainTable.getColumnCount();
    }

    return (rowFooterTable == null)
           ? -1
           : column;
  }

  @Override
  public int getFirstVisibleColumnIndex() {
    if (rowHeaderTable != null) {
      return rowHeaderTable.getFirstVisibleColumnIndex();
    }

    return mainTable.getFirstVisibleColumnIndex() + tableModel.getHeaderColumnCount();
  }

  @Override
  public GridViewType getGridViewType() {
    return null;
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return mainTable.getItemRenderer();
  }

  @Override
  public int getLastVisibleColumnIndex() {
    if (rowFooterTable != null) {
      return rowFooterTable.getLastVisibleColumnIndex() + tableModel.getFooterColumnStart();
    }

    return mainTable.getLastVisibleColumnIndex() + tableModel.getHeaderColumnCount();
  }

  @Override
  public iTableComponent getMainTable() {
    return mainTable;
  }

  @Override
  public iTableModel getModel() {
    return tableModel;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return component;
  }

  @Override
  public iTableComponent getRowFooterTable() {
    return rowFooterTable;
  }

  @Override
  public iTableComponent getRowHeaderTable() {
    return rowHeaderTable;
  }

  @Override
  public int getSelectedColumn() {
    int col = mainTable.getSelectedColumn();

    if (rowHeaderTable != null) {
      if (col != -1) {
        col += rowHeaderTable.getSelectedColumn();
      }

      col = rowHeaderTable.getSelectedColumn();
    }

    if ((col == -1) && (rowFooterTable != null)) {
      col = rowFooterTable.getSelectedColumn();

      if (col != -1) {
        col += tableModel.getFooterColumnStart();
      }
    }

    return col;
  }

  @Override
  public int getSelectedColumnCount() {
    int count = mainTable.getSelectedColumnCount();

    if (rowFooterTable != null) {
      count = rowFooterTable.getSelectedColumnCount();
    }

    if (rowHeaderTable != null) {
      count = rowHeaderTable.getSelectedColumnCount();
    }

    return count;
  }

  @Override
  public int[] getSelectedColumns() {
    int[] h = (rowHeaderTable == null)
              ? null
              : rowHeaderTable.getSelectedColumns();
    int[] m = mainTable.getSelectedColumns();
    int[] f = (rowFooterTable == null)
              ? null
              : rowFooterTable.getSelectedColumns();

    if ((h == null) || (h.length == 0) || (m == null) || (m.length == 0) || (f == null) || (f.length == 0)) {
      return null;
    }

    if (colList == null) {
      colList = new IntList();
    }

    if (h != null) {
      colList.add(h);
    }

    if (m != null) {
      colList.add(m);
    }

    if (f != null) {
      colList.add(f);
    }

    return colList.toArray();
  }

  @Override
  public int getSortColumn() {
    return tableModel.getSortColumn();
  }

  public iTableComponent getTableForModelColumn(int column) {
    if (column < tableModel.getHeaderColumnCount()) {
      return rowHeaderTable;
    } else {
      column -= tableModel.getHeaderColumnCount();
    }

    if (column < mainTable.getColumnCount()) {
      return mainTable;
    }

    return rowFooterTable;
  }

  @Override
  public aTableHeader getTableHeader() {
    return mainTable.getTableHeader();
  }

  @Override
  public UIRectangle getViewRect() {
    return mainTable.getViewRect();
  }

  @Override
  public int getVisibleColumnCount() {
    int count = mainTable.getVisibleColumnCount();

    if (rowFooterTable != null) {
      count = rowFooterTable.getVisibleColumnCount();
    }

    if (rowHeaderTable != null) {
      count = rowHeaderTable.getVisibleColumnCount();
    }

    return count;
  }

  @Override
  public boolean isAutoSizeRows() {
    return mainTable.isAutoSizeRows();
  }

  @Override
  public boolean isEditing() {
    return mainTable.isEditing();
  }

  @Override
  public boolean isSortDescending() {
    return tableModel.isSortDescending();
  }

  @Override
  public void moveColumn(int column, int targetColumn) {
    iTableComponent table = getTableForModelColumn(column);

    column       = getColumnIndexForModelColumn(column);
    targetColumn = getColumnIndexForModelColumn(targetColumn);

    if ((column != -1) && (targetColumn != -1)) {
      table.moveColumn(column, targetColumn);
    }
  }

  @Override
  public void resetTable(List<Column> columns, List<RenderableDataItem> rows) {
    tableModel.resetModel(columns, new FilterableList<RenderableDataItem>(rows));
    rowHeaderColumnCount = tableModel.headerColumnCount;
    rowFooterColumnStart = tableModel.footerColumnStart;
    mainColumnCount      = tableModel.columnCount - rowHeaderColumnCount
                           - (tableModel.columnCount - rowFooterColumnStart);
  }

  /**
   * @param columnFooterTable
   *          the columnFooterTable to set
   */
  public void setColumnFooterTable(iTableComponent columnFooterTable) {
    this.columnFooterTable = columnFooterTable;
  }

  /**
   * @param columnHeaderTable
   *          the columnHeaderTable to set
   */
  public void setColumnHeaderTable(iTableComponent columnHeaderTable) {
    this.columnHeaderTable = columnHeaderTable;
  }

  @Override
  public void setColumnIcon(int col, iPlatformIcon icon) {
    iTableComponent table = getTableForModelColumn(col);

    if (table != null) {
      table.setColumnIcon(getColumnIndexForModelColumn(col), icon);
    }
  }

  @Override
  public void setColumnTitle(int col, CharSequence title) {
    iTableComponent table = getTableForModelColumn(col);

    if (table != null) {
      table.setColumnTitle(getColumnIndexForModelColumn(col), title);
    }
  }

  @Override
  public void setColumnVisible(int col, boolean visible) {
    iTableComponent table = getTableForModelColumn(col);

    if (table != null) {
      table.setColumnVisible(getColumnIndexForModelColumn(col), visible);
    }
  }

  @Override
  public void setGridViewType(GridViewType type) {}

  @Override
  public void setHeaderBackground(PaintBucket pb) {
    mainTable.setHeaderBackground(pb);

    if (rowHeaderTable != null) {
      rowHeaderTable.setHeaderBackground(pb);
    }

    if (rowFooterTable != null) {
      rowFooterTable.setHeaderBackground(pb);
    }
  }

  /**
   * @param mainTable
   *          the mainTable to set
   */
  public void setMainTable(iTableComponent mainTable) {
    this.mainTable = mainTable;
    mainTable.setTableType(TableType.MAIN);
    mainTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());
  }

  /**
   * @param rowFooterTable
   *          the rowFooterTable to set
   */
  public void setRowFooterTable(iTableComponent rowFooterTable) {
    this.rowFooterTable = rowFooterTable;
    rowFooterTable.setTableType(TableType.FOOTER);
    rowFooterTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());
  }

  /**
   * @param rowHeaderTable
   *          the rowHeaderTable to set
   */
  public void setRowHeaderTable(iTableComponent rowHeaderTable) {
    this.rowHeaderTable = rowHeaderTable;
    rowHeaderTable.setTableType(TableType.HEADER);
    rowHeaderTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());
  }

  @Override
  public void setRowHeight(int row, int height) {
    mainTable.setRowHeight(row, height);

    if (rowHeaderTable != null) {
      rowHeaderTable.setRowHeight(row, height);
    }

    if (rowFooterTable != null) {
      rowFooterTable.setRowHeight(row, height);
    }
  }

  @Override
  public void setSelectedColumnIndex(int index) {
    iTableComponent table = getTableForModelColumn(index);

    if (table != null) {
      table.setSelectedColumnIndex(getColumnIndexForModelColumn(index));

      if ((rowFooterTable != null) && (table != rowFooterTable)) {
        rowFooterTable.setSelectedColumnIndex(-1);
      }

      if ((rowHeaderTable != null) && (table != rowHeaderTable)) {
        rowHeaderTable.setSelectedColumnIndex(-1);
      }

      if (table != mainTable) {
        mainTable.setSelectedColumnIndex(-1);
      }
    }
  }

  @Override
  public void setSelectedColumnIndices(int[] indices) {
    mainTable.setSelectedColumnIndices(null);
  }

  @Override
  public void setShowHorizontalLines(boolean show) {
    mainTable.setShowHorizontalLines(show);

    if (rowHeaderTable != null) {
      rowHeaderTable.setShowHorizontalLines(show);
    }

    if (rowFooterTable != null) {
      rowFooterTable.setShowHorizontalLines(show);
    }
  }

  @Override
  public void setShowVerticalLines(boolean show) {
    mainTable.setShowVerticalLines(show);

    if (rowHeaderTable != null) {
      rowHeaderTable.setShowVerticalLines(show);
    }

    if (rowFooterTable != null) {
      rowFooterTable.setShowVerticalLines(show);
    }
  }

  @Override
  public void setSortColumn(int col, boolean descending) {
    iTableComponent table = getTableForModelColumn(col);

    if (table != null) {
      table.setSortColumn(getColumnIndexForModelColumn(col), descending);

      if ((rowFooterTable != null) && (table != rowFooterTable)) {
        rowFooterTable.setSortColumn(-1, descending);
      }

      if ((rowHeaderTable != null) && (table != rowHeaderTable)) {
        rowHeaderTable.setSortColumn(-1, descending);
      }

      if (table != mainTable) {
        mainTable.setSortColumn(-1, descending);
      }
    }
  }

  @Override
  public void setStyle(TableStyle tableStyle) {
    mainTable.setStyle(tableStyle);

    if (rowHeaderTable != null) {
      rowHeaderTable.setStyle(tableStyle);
    }

    if (rowFooterTable != null) {
      rowFooterTable.setStyle(tableStyle);
    }
  }

  @Override
  public void setTable() {
    rowHeaderColumnCount = tableModel.headerColumnCount;
    rowFooterColumnStart = tableModel.footerColumnStart;
    mainColumnCount      = tableModel.columnCount - rowHeaderColumnCount
                           - (tableModel.columnCount - rowFooterColumnStart);
    mainTable.setTable();

    if (rowHeaderTable != null) {
      rowHeaderTable.setTable();
    }

    if (rowFooterTable != null) {
      rowFooterTable.setTable();
    }
  }

  @Override
  public void sizeRowsToFit() {
    mainTable.sizeRowsToFit();
  }

  @Override
  public void sort(Comparator c) {
    tableModel.sort(c);

    if (rowHeaderTable != null) {
      rowHeaderTable.getModel().refreshItems();
    }

    mainTable.getModel().refreshItems();

    if (rowFooterTable != null) {
      rowFooterTable.getModel().refreshItems();
    }
  }

  @Override
  public void sort(int col) {
    sort(col, isSortDescending(), false);
  }

  @Override
  public void sort(int col, boolean descending, boolean useLinkedData) {
    tableModel.sort(col, descending, useLinkedData);

    if (rowHeaderTable != null) {
      rowHeaderTable.getModel().refreshItems();

      if (col < rowHeaderColumnCount) {
        rowHeaderTable.setSortColumn(col, descending);
      } else {
        rowHeaderTable.setSortColumn(-1, descending);
      }
    }

    mainTable.getModel().refreshItems();

    if ((col >= rowHeaderColumnCount) && (col < rowFooterColumnStart)) {
      mainTable.setSortColumn(col - rowHeaderColumnCount, descending);
    } else {
      mainTable.setSortColumn(-1, descending);
    }

    if (rowFooterTable != null) {
      rowFooterTable.getModel().refreshItems();

      if ((col >= rowFooterColumnStart) && (col < getColumnCount())) {
        rowFooterTable.setSortColumn(col - rowFooterColumnStart, descending);
      } else {
        rowFooterTable.setSortColumn(-1, descending);
      }
    }
  }

  @Override
  public void stopEditing() {
    mainTable.stopEditing();
  }

  @Override
  public TableType getTableType() {
    return TableType.MULTI;
  }

  @Override
  public void setTableType(TableType type) {}

  @Override
  public boolean isMainTable() {
    return false;
  }

  @Override
  public void contentsChanged(Object source) {
    // TODO Auto-generated method stub
  }

  @Override
  public void contentsChanged(Object source, int index0, int index1) {
    // TODO Auto-generated method stub
  }

  @Override
  public void intervalAdded(Object source, int index0, int index1) {
    // TODO Auto-generated method stub
  }

  @Override
  public void intervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean isMultiTableComponent() {
    return true;
  }

  @Override
  public void structureChanged(Object source) {
    mainTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());

    if (rowHeaderTable != null) {
      rowHeaderTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());
    }

    if (rowFooterTable != null) {
      rowFooterTable.getTableHeader().setMultiTableColumns(tableModel.getColumns());
    }
  }
}
