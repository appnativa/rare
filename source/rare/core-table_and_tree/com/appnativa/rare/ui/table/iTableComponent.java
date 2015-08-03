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
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.painter.PaintBucket;

import java.util.Comparator;
import java.util.List;

public interface iTableComponent /*extends iPlatformComponent */ {
  public enum GridViewType { VERTICAL_WRAP, HORIZONTAL_WRAP }

  public enum TableType {
    HEADER, MAIN, FOOTER, MULTI
  }

  /**
   * Gets whether the current sort is in descending order. Only meaningful if
   * getSortColumn() returns something other than -1
   *
   * @return true if it is; false otherwise
   * @see #getSortColumn()
   */
  public boolean isSortDescending();

  int convertModelIndexToView(int index);

  int convertViewIndexToModel(int index);

  /**
   * Gets the index of the first column visible in the view
   *
   * @return the index of the first column visible in the view
   */
  int getFirstVisibleColumnIndex();

  /**
   * Gets the index of the last column visible in the view
   *
   * @return the index of the last column visible in the view
   */
  int getLastVisibleColumnIndex();

  aTableHeader getTableHeader();

  Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon);

  /**
   * Moves the column column to the position currently occupied by the column
   * targetColumn in the view. The old column at targetColumn is shifted left or
   * right to make room.
   *
   * @param column
   *          the view index of column to be moved
   * @param targetColumn
   *          the new view index of the column
   *
   */
  void moveColumn(int column, int targetColumn);

  /**
   * Resets the table replacing the existing columns and rows
   *
   * @param columns
   *          the new columns
   * @param rows
   *          the new rows
   */
  void resetTable(List<Column> columns, List<RenderableDataItem> rows);

  void sizeRowsToFit();

  /**
   * Sorts the table using the specified comparator
   *
   * @param c
   *          the comparator
   */
  void sort(Comparator c);

  /**
   * Sorts the table by the specified column
   *
   * @param col
   *          the column to sort by
   */
  void sort(int col);

  /**
   * Sorts the table by the specified column
   *
   * @param col
   *          the column to sort by
   * @param descending
   *          true to sort in descending order; false otherwise
   * @param useLinkedData
   *          true to sort using the item's linked data; false to use the item's
   *          value
   */
  void sort(int col, boolean descending, boolean useLinkedData);

  void stopEditing();

  /**
   * Sets the icon for the specified column
   *
   * @param col
   *          the column
   * @param icon
   *          the icon
   */
  void setColumnIcon(int col, iPlatformIcon icon);

  /**
   * Sets the title of the specified column
   *
   * @param col
   *          the column to set the title for
   * @param title
   *          the new title
   */
  void setColumnTitle(int col, CharSequence title);

  /**
   * Sets the title of the specified column
   *
   * @param col
   *          the column
   * @param visible
   *          true for visible; false otherwise
   */
  void setColumnVisible(int col, boolean visible);

  void setGridViewType(GridViewType type);

  void setHeaderBackground(PaintBucket pb);

  void setRowHeight(int row, int h);

  void setSelectedColumnIndex(int index);

  void setSelectedColumnIndices(int[] indices);

  void setShowHorizontalLines(boolean b);

  void setShowVerticalLines(boolean b);

  /**
   * Set the currently sortBy column and the sort order
   *
   * @param col
   *          the sortBy column
   * @param descending
   *          true if items are sorted in descending order; false for ascending
   */
  void setSortColumn(int col, boolean descending);

  void setStyle(TableStyle tableStyle);

  void setTable();

  UIRectangle getCellRect(int row, int column, boolean includeMargin);

  int getColumnCount();

  int getColumnIndexAt(float x, float y);

  GridViewType getGridViewType();

  iPlatformItemRenderer getItemRenderer();

  iTableModel getModel();

  iPlatformComponent getPlatformComponent();

  int getSelectedColumn();

  int getSelectedColumnCount();

  int[] getSelectedColumns();

  int getSortColumn();

  int getVisibleColumnCount();

  boolean isAutoSizeRows();

  boolean isEditing();

  UIRectangle getViewRect();

  iTableComponent getMainTable();

  iTableComponent getRowHeaderTable();

  iTableComponent getRowFooterTable();

  iTableComponent getColumnHeaderTable();

  iTableComponent getColumnFooterTable();

  TableType getTableType();

  void setTableType(TableType type);

  void dispose();

  boolean isMainTable();

  boolean isMultiTableComponent();
}
