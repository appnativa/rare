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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.renderer.aListItemRenderer;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.ui.table.TableStyle;
import com.appnativa.rare.ui.table.TableStyle.BackgroundHighlight;
import com.appnativa.rare.ui.table.aTableHeader;
import com.appnativa.rare.ui.table.iTableComponent;
import com.appnativa.rare.ui.table.iTableComponent.GridViewType;
import com.appnativa.rare.ui.table.iTableComponent.TableType;
import com.appnativa.rare.util.DataItemCollection;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.SNumber;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * A widget that allows a user to select one or more choices from a scrollable
 * list of items.
 *
 * @author Don DeCoteau
 */
public abstract class aTableViewer extends aListViewer implements iTreeHandler {
  protected boolean                        columnSelectionAllowed;
  protected RenderableDataItem             currentRow;
  protected GridViewType                   gridViewType;
  protected LinkedList<RenderableDataItem> levelStack;
  protected iTableComponent                tableHandler;

  /** the list model */
  protected iTableModel  tableModel;
  protected iTreeHandler treeHandler;

  /**
   * Constructs a new instance
   */
  public aTableViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aTableViewer(iContainer parent) {
    super(parent);
    widgetType             = WidgetType.Table;
    initiallySelectedIndex = -1;
  }

  @Override
  public void addChild(RenderableDataItem child) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.addChild(child);
  }

  @Override
  public Object getSelection() {
    if (selectAllAllowed) {    // multiple selection
      return getSelections();
    }

    return getSelectedItem();
  }

  @Override
  public void addChild(int row, RenderableDataItem child) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.addChild(row, child);
  }

  @Override
  public void addChild(RenderableDataItem item, RenderableDataItem child) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.addChild(item, child);
  }

  @Override
  public void addChildren(int row, List<RenderableDataItem> children) {
    if (treeHandler != null) {
      treeHandler.addChildren(row, children);
    }
  }

  @Override
  public void addChildren(RenderableDataItem row, List<RenderableDataItem> children) {
    if (treeHandler != null) {
      treeHandler.addChildren(row, children);
    }
  }

  @Override
  public void addExpandedListener(iExpandedListener l) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.addExpandedListener(l);
  }

  @Override
  public void addExpansionListener(iExpansionListener l) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.addExpansionListener(l);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    synchronized(widgetType) {
      if (!isDisposed()) {
        if ((gridViewType == null) && (columnCount == 1) && (row.getItemCount() == 0)) {
          RenderableDataItem item = new RenderableDataItem();

          item.add(row);
          row = item;
        }

        fixColumns(row);

        if (treeHandler != null) {
          this.addTreeRow(row);
        } else {
          row.setModelData(null);
          getTempList().add(row);
        }
      }
    }
  }

  /**
   * Clears the currently selected table cells
   *
   * @param rows
   *          the rows
   * @param cols
   *          the columns
   * @param valueOnly
   *          true to only clear the value of a cell; false to clear the cell's
   *          value and other associated data
   */
  public void clearCells(int rows[], int cols[], boolean valueOnly) {
    RenderableDataItem di;
    RenderableDataItem row;
    boolean            cells = false;
    int                len   = (rows == null)
                               ? 0
                               : rows.length;
    int                clen  = (cols == null)
                               ? 0
                               : cols.length;

    for (int i = 0; i < len; i++) {
      row = tableModel.getRow(rows[i]);

      if (row == null) {
        continue;
      }

      if (!cells) {
        if (valueOnly) {
          row.clearSubItemValues();
        } else {
          row.clearSubItemsEx();
        }

        continue;
      }

      for (int n = 0; n < clen; n++) {
        di = row.getItemEx(cols[n]);

        if (di != null) {
          if (valueOnly) {
            di.setValue(null);
          } else {
            di.clear();
          }
        }
      }
    }

    refreshItems();
  }

  @Override
  public void clearRootNode() {
    if (treeHandler != null) {
      treeHandler.clearRootNode();
    } else {
      clear();
    }
  }

  /**
   * Clears the currently selected table cells
   *
   * @param valueOnly
   *          true to only clear the value of a cell; false to clear the cell's
   *          value and other associated data
   */
  public void clearSelectedCells(boolean valueOnly) {
    clearCells(getSelectedIndexes(), null, valueOnly);
  }

  @Override
  public void collapseAll() {
    if (treeHandler != null) {
      treeHandler.collapseAll();
    }
  }

  @Override
  public void collapseRow(int row) {
    if (treeHandler != null) {
      treeHandler.collapseRow(row);
    }
  }

  @Override
  public void collapseRow(RenderableDataItem item) {
    if (treeHandler != null) {
      treeHandler.collapseRow(item);
    }
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((Table) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);

    aTableHeader th = tableHandler.getTableHeader();

    if (th.isVisible()) {
      UIColor c = th.getBackground();

      if (c != null) {
        getContainerComponent().putClientProperty(iConstants.RARE_FILLCOLOR_PROPERTY, c);
      }
    }

    handleDataURL(vcfg);
  }

  /**
   * Creates a new column based on the specified item
   *
   * @param item
   *          the item
   * @return a new column based on the specified item
   */
  public Column createColumn(RenderableDataItem item) {
    if (item instanceof Column) {
      return (Column) item.clone();
    }

    Column c = new Column();

    c.copyEx(item);

    if (item.getValue() != null) {
      c.setColumnTitle(item.getValue().toString());
    }

    return c;
  }

  /**
   * Creates a new column
   *
   * @param title
   *          the column's title
   * @return the newly created column
   */
  @Override
  public Column createColumn(String title) {
    return createColumn((title == null)
                        ? ""
                        : title, RenderableDataItem.TYPE_STRING, (iPlatformIcon) null);
  }

  /**
   * Creates a new column
   *
   * @param title
   *          the column's title
   * @param type
   *          the column's type
   * @param icon
   *          the icon for the column
   *
   * @return the newly created column
   */
  public Column createColumn(String title, int type, iPlatformIcon icon) {
    return createColumn((title == null)
                        ? ""
                        : title, null, type, null, icon);
  }


  /**
   * Converts from a model based index to a view based index.
   * The view index in based on the order the columns is displayed in.
   * The model index is based on the order the columns are stored in the model
   * 
   * @param index the view index to convert
   * @return the model index or -1 if an error
   */
  public int convertColumnModelIndexToView(int index) {
    return tableHandler.convertModelIndexToView(index);
  }

  /**
   * Converts from a view based index to a model based index.
   * The view index in based on the order the columns is displayed in.
   * The model index is based on the order the columns are stored in the model
   * 
   * @param index the view index to convert
   * @return the model index or -1 if an error
   */
  public int convertColumnViewIndexToModel(int index) {
    return tableHandler.convertViewIndexToModel(index);
  }
/**
   * Creates a new column
   *
   * @param title
   *          the column's title
   * @param type
   *          the column's type
   * @param icon
   *          a reference to the icon for the column
   *
   * @return the newly created column
   */
  public Column createColumn(String title, int type, String icon) {
    iPlatformIcon ic = null;

    if (icon != null) {
      ic = getIcon(icon, null);
    }

    return createColumn((title == null)
                        ? ""
                        : title, null, type, null, ic);
  }

  @Override
  public Column createColumn(String title, Object value, int type, Object data, iPlatformIcon icon) {
    return tableHandler.createColumn(title, value, type, data, icon);
  }

  /**
   * Create a list of columns from the specified data array
   *
   * @param data
   *          the data for the columns
   *
   * @return the list of columns
   */
  public List<Column> createColumns(String[] data) {
    int len = (data == null)
              ? 0
              : data.length;

    return createColumns(data, 0, len);
  }

  /**
   * Create a list of columns from the specified data array
   *
   * @param data
   *          the data for the columns
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of elements to use
   *
   * @return the list of columns
   */
  public List<Column> createColumns(String[] data, int pos, int len) {
    List<Column> list = new FilterableList<Column>(len);
    Column       col;

    len += pos;

    for (int i = 0; i < len; i++) {
      if (pos < len) {
        col = new Column(data[pos + i]);
      } else {
        col = new Column("");
      }

      list.add(col);
    }

    return list;
  }

  /**
   * Create a new row from the specified data array
   *
   * @param data
   *          the data for the row
   *
   * @return an item that can be added as a row to this table
   */
  public RenderableDataItem createRow(String[] data) {
    return createRow(data, 0, data.length);
  }

  /**
   * Create a new row from the specified data array
   *
   * @param data
   *          the data for the row
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of elements to use
   *
   * @return an item that can be added as a row to this table
   */
  public RenderableDataItem createRow(Object[] data, int pos, int len) {
    RenderableDataItem col;
    RenderableDataItem row = new RenderableDataItem();

    len += pos;

    Object item;

    for (int i = 0; i < columnCount; i++) {
      if (pos < len) {
        item = data[pos + i];

        if (item instanceof String) {
          item = new RenderableDataItem(item);
        } else if (!(item instanceof RenderableDataItem)) {
          item = new RenderableDataItem(null, item);
        }

        col = (RenderableDataItem) item;
      } else {
        col = new RenderableDataItem("");
      }

      row.add(col);
    }

    return row;
  }

  /**
   * Create a new row from the specified data array
   *
   * @param data
   *          the data for the row
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of elements to use
   *
   * @return an item that can be added as a row to this table
   */
  public RenderableDataItem createRow(String[] data, int pos, int len) {
    RenderableDataItem row = new RenderableDataItem();
    RenderableDataItem col;
    int                clen = len;

    if (clen < columnCount) {
      clen = columnCount;
    }

    len += pos;

    for (int i = 0; i < clen; i++) {
      if (pos < len) {
        col = new RenderableDataItem(data[pos + i]);
      } else {
        col = new RenderableDataItem("");
      }

      row.add(col);
    }

    return row;
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    if (tableModel != null) {
      tableModel.dispose();
    }

    if (tableHandler != null) {
      tableHandler.dispose();
    }

    if (levelStack != null) {
      levelStack.clear();
    }

    super.dispose();
    treeHandler  = null;
    levelStack   = null;
    tableModel   = null;
    tableHandler = null;
  }

  /**
   * Starts editing a cell using that standard editing mechanism for the
   * platform. Does nothing if cell editing is not supported on the platform.
   *
   * @param row
   *          the row
   * @param column
   *          the column
   */
  public void editCell(int row, int column) {}

  @Override
  public void expandAll() {
    if (treeHandler == null) {
      return;
    }

    treeHandler.expandAll();
  }

  @Override
  public void expandRow(int row) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.expandRow(row);
  }

  @Override
  public void expandRow(RenderableDataItem item) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.expandRow(item);
  }

  @Override
  public boolean filter(iFilter filter) {
    boolean filtered = false;

    if (treeHandler != null) {
      filtered = treeHandler.filter(filter);
    } else {
      filtered = listComponent.filter(filter);
    }

    if (filtered) {
      this.clearSelection();
      update();

      return true;
    }

    return false;
  }

  /**
   * Applies the specified filter to the list
   *
   * @param col
   *          the column to filter
   * @param filter
   *          the filter
   *
   * @return true if items were filtered out; false otherwise
   */
  public boolean filter(int col, iFilter filter) {
    setActiveColumn(col);

    return filter(filter);
  }

  /**
   * Applies the specified filter to the list
   *
   * @param col
   *          the column to filter
   * @param filter
   *          the filter
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   *
   * @return true if items were filtered out; false otherwise
   */
  public boolean filter(int col, String filter, boolean contains) {
    setActiveColumn(col);

    return filter(filter, contains, true, true);
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    if (treeHandler != null) {
      if (treeHandler.filter(filter, contains, nullPasses, emptyPasses)) {
        clearSelection();
        repaint();

        return true;
      }
    } else if (listComponent.filter(filter, contains, nullPasses, emptyPasses)) {
      tableModel.tableDataChanged();
      clearSelection();
      repaint();

      return true;
    }

    return false;
  }

  /**
   * Finds the index of the first item matching the specified filter. The search
   * is performed beginning at the specified start index (an equality test is
   * used)
   *
   * @param col
   *          the column to search
   * @param filter
   *          the filter
   * @param start
   *          the starting point of the search
   *
   * @return the index or -1 if no item was found
   */
  public int find(int col, iFilter filter, int start) {
    setActiveColumn(col);

    return super.find(filter, start);
  }

  /**
   * Finds the index of the first item matching the specified filter. The search
   * is performed beginning at the specified start index (an equality test is
   * used)
   *
   * @param col
   *          the column to search
   * @param filter
   *          the filter
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   *
   * @return the index or -1 if no item was found
   */
  public int find(int col, String filter, boolean contains) {
    return find(col, filter, 0, contains);
  }

  /**
   * Finds the index of the first item matching the specified filter. The search
   * is performed beginning at the specified start index (an equality test is
   * used)
   *
   * @param col
   *          the column to search
   * @param start
   *          the starting point of the search
   * @param filter
   *          the filter
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   *
   * @return the index or -1 if no item was found
   */
  public int find(int col, String filter, int start, boolean contains) {
    setActiveColumn(col);

    return super.find(filter, start, contains);
  }

  @Override
  public void finishedParsing() {
    super.finishedParsing();

    if (levelStack != null) {
      levelStack.clear();
    }

    levelStack = null;
    currentRow = null;
  }

  /**
   * Populates the table with the specified collection. The collection is
   * assumed to be properly laid out for the table and the rows will be added
   * directly to the table without any preprocessing. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param collection
   *          the collection
   */
  public void handleGroupedCollection(Collection<RenderableDataItem> collection) {
    this.handleGroupedCollection(collection, false);
  }

  /**
   * Populates the table with the specified collection. The collection is
   * assumed to be properly laid out for the table and the rows will be added
   * directly to the table without any preprocessing. For list type widgets
   * (lists, trees, tables, etc), the data will be appended to any existing data
   *
   * @param collection
   *          the collection
   * @param appendCounts
   *          true to append the count of items in a group to the group title;
   *          false otherwise
   */
  public void handleGroupedCollection(Collection<RenderableDataItem> collection, boolean appendCounts) {
    if (this.treeHandler == null) {
      this.handleCollection(collection, 0);

      return;
    }

    if (collection == null) {
      return;
    }

    Iterator<RenderableDataItem> it = collection.iterator();
    RenderableDataItem           row;

    try {
      this.startedParsing();
      this.startedLoading();

      List<RenderableDataItem> list = getTempList();

      while(it.hasNext()) {
        row = it.next();

        if (appendCounts) {
          String s = row.get(0).toString();

          if ((s == null) || (s.length() == 0)) {
            s = "(" + row.get(0).size() + ")";
          } else {
            s += " (" + row.get(0).size() + ")";
          }

          row.get(0).setValue(s);
        }

        fixColumns(row);
        list.add(row);
      }

      listComponent.setDataEventsEnabled(false);
      treeHandler.setTreeEventsEnabled(false);
      treeHandler.setAll(list);
      treeHandler.setTreeEventsEnabled(true);
      list.clear();
      tempList = null;
    } finally {
      listComponent.setDataEventsEnabled(true);
      this.finishedParsing();
      this.finishedLoading();
    }
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
  public void moveColumn(int column, int targetColumn) {
    tableHandler.moveColumn(column, targetColumn);
  }

  @Override
  public void refreshItems() {
    if (!Platform.isUIThread()) {
      ListHelper.runLater(this, ListHelper.RunType.REFRESH);

      return;
    }

    try {
      setDataEventsEnabled(false);

      if (tempList != null) {
        listComponent.setAll(tempList);
        tempList.clear();
        tempList = null;

        if (sorter != null) {
          listComponent.sort(sorter);

          if (sorter instanceof SubItemComparator) {
            SubItemComparator c = (SubItemComparator) sorter;

            if (c.getSortColumn() != -1) {
              setSortColumn(c.getSortColumn(), c.isSortDescending());
            }
          }
        }
      }
    } finally {
      setDataEventsEnabled(true);
      listComponent.refreshItems();
    }
  }

  @Override
  public void removeExpandedListener(iExpandedListener l) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.removeExpandedListener(l);
  }

  @Override
  public void removeExpansionListener(iExpansionListener l) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.removeExpansionListener(l);
  }

  /**
   * Resets the table data replacing the existing rows
   *
   * @param rows
   *          the new rows
   */
  public void resetData(final List<RenderableDataItem> rows) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        if (treeHandler != null) {
          treeHandler.setAll(rows);
        } else {
          tableModel.setAll(rows);
        }
      }
    };

    if (Platform.isUIThread()) {
      r.run();
    } else {
      Platform.invokeLater(r);
    }
  }

  /**
   * Resets the table replacing the existing columns with the specified columns
   * and deleting all rows
   *
   * @param columns
   *          the new columns
   */
  public void resetTable(List<Column> columns) {
    resetTable(columns, null);
  }

  /**
   * Resets the table replacing the existing columns and rows
   *
   * @param columns
   *          the new columns
   * @param rows
   *          the new rows
   */
  public void resetTable(List<Column> columns, List<RenderableDataItem> rows) {
    columnCount = columns.size();
    tableHandler.resetTable(columns, rows);
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param index
   *          the index of the row that changed
   */
  @Override
  public void rowChanged(int index) {
    if (tableModel != null) {
      tableModel.rowChanged(index);
    }
  }

  /**
   * Notifies the list that the contents of the specified grid view position has
   * changed
   *
   * @param index
   *          the position of the item that changed
   */
  public void gridViewItemChanged(int index) {
    if (tableModel != null) {
      if (tableHandler.getGridViewType() != null) {
        index = (int) Math.floor(index / tableHandler.getColumnCount());
      }

      tableModel.rowChanged(index);
    }
  }

  /**
   * Notifies the list that the contents of the specified item in a grid view
   * has changed
   *
   * @param item
   *          the item that changed
   */
  public void gridViewItemChanged(RenderableDataItem item) {
    if (tableModel != null) {
      if (tableHandler.getGridViewType() != null) {
        RenderableDataItem parent = item.getParentItem();

        if (parent != null) {
          item = parent;
        }
      }

      tableModel.rowChanged(item);
    }
  }

  /**
   * Notifies the list that the contents of the specified item has changed
   *
   * @param item
   *          the instance of the row that changed
   */
  @Override
  public void rowChanged(RenderableDataItem item) {
    if (tableModel != null) {
      tableModel.rowChanged(item);
    }
  }

  /**
   * Notifies the list that the contents of the set of rows have
   * changed
   *
   * @param rows the rows
   */
  public void rowsChanged(int... rows) {
    tableModel.rowsChanged(rows);
  }

  /**
   * Notifies the list that the contents of the specified range of rows have
   * changed
   *
   * @param firstRow
   *          the first row
   * @param lastRow
   *          the last row
   */
  @Override
  public void rowsChanged(int firstRow, int lastRow) {
    tableModel.rowsChanged(firstRow, lastRow);
  }

  public abstract void sizeColumnToFit(int col);

  public abstract void sizeColumnsToFit();

  @Override
  public void sort(Comparator comparator) {
    if (tempList != null) {
      sorter = comparator;

      return;
    }

    if (treeHandler != null) {
      treeHandler.sort(comparator);
    } else {
      tableModel.sort(comparator);
    }
  }

  /**
   * Sorts the table by the specified column
   *
   * @param col
   *          the column to sort by
   * @param descending
   *          true to sort in descending order; false otherwise
   */
  public void sort(int col, boolean descending) {
    sort(col, descending, false);
  }

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
  public void sort(int col, boolean descending, boolean useLinkedData) {
    col = tableHandler.convertViewIndexToModel(col);

    if (tempList != null) {
      sorter = getComparator(col, descending);

      return;
    }

    tableHandler.sort(col, descending, useLinkedData);
  }

  @Override
  public void sortEx(Comparator comparator) {
    boolean enabled = isDataEventsEnabled();

    setDataEventsEnabled(false);

    try {
      sort(comparator);
    } finally {
      setDataEventsEnabled(enabled);
    }
  }

  /**
   * Sorts the table by the specified column. Sorts without triggering and
   * events
   *
   * @param col
   *          the column to sort by
   * @param descending
   *          true to sort in descending order; false otherwise
   */
  public void sortEx(int col, boolean descending) {
    sortEx(col, descending, false);
  }

  /**
   * Sorts the table by the specified column. Sorts without triggering any
   * events. This method can be called from a background thread
   *
   * @param col
   *          the column to sort by
   * @param descending
   *          true to sort in descending order; false otherwise
   * @param useLinkedData
   *          true to sort using the item's linked data; false to use the item's
   *          value
   */
  public void sortEx(int col, boolean descending, boolean useLinkedData) {
    boolean enabled = isDataEventsEnabled();

    setDataEventsEnabled(false);

    try {
      sort(col, descending, useLinkedData);
    } finally {
      setDataEventsEnabled(enabled);
    }
  }

  @Override
  public void startedParsing() {
    currentRow = null;
    super.startedParsing();
  }

  @Override
  public void toggleRow(int row) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.toggleRow(row);
  }

  @Override
  public boolean unfilter() {
    if (treeHandler != null) {
      if (treeHandler.unfilter()) {
        if (tableHandler.isAutoSizeRows()) {
          tableHandler.sizeRowsToFit();
          update();
        }

        return true;
      }
    } else if (super.unfilter()) {
      if (tableHandler.isAutoSizeRows()) {
        tableHandler.sizeRowsToFit();
        update();
      }

      return true;
    }

    return false;
  }

  /**
   * Sets the column that will be operated on when filtering, searching, etc.
   *
   * @param col
   *          the column that will be operated on, use -1 to indicate the whole
   *          row
   */
  public void setActiveColumn(int col) {
    col = tableHandler.convertViewIndexToModel(col);
    tableModel.setActiveColumn(col);
  }

  @Override
  public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
    if (treeHandler != null) {
      treeHandler.setAutoScrollOnExpansion(autoScrollOnExpansion);
    }
  }

  /**
   * Sets the icon of the specified column
   *
   * @param col
   *          the view column to set the icon for
   * @param icon
   *          the new icon
   */
  public void setColumnIcon(int col, iPlatformIcon icon) {
    col = tableHandler.convertViewIndexToModel(col);
    tableHandler.setColumnIcon(col, icon);
  }

  /**
   * Sets the name of the specified column
   *
   * @param col
   *          the view column to set the title for
   * @param name
   *          the new title
   */
  public void setColumnName(int col, String name) {
    col = tableHandler.convertViewIndexToModel(col);

    Column cc = tableModel.getColumn(col);

    cc.columnName = name;
  }

  /**
   * Sets the title of the specified column
   *
   * @param col
   *          the view column to set the title for
   * @param title
   *          the new title
   */
  public void setColumnTitle(int col, String title) {
    col = tableHandler.convertViewIndexToModel(col);
    tableHandler.setColumnTitle(col, title);
  }

  /**
   * Sets the title of the specified column
   *
   * @param col
   *          the view column to set the title for
   * @param visible
   *          true for visible; false otherwise
   */
  public void setColumnVisible(int col, boolean visible) {
    col = tableHandler.convertViewIndexToModel(col);
    tableHandler.setColumnVisible(col, visible);
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    if (treeHandler != null) {
      treeHandler.setConverter(converter);
    } else {
      super.setConverter(converter);
    }
  }

  @Override
  public void setEditingMode(EditingMode mode) {}

  @Override
  public void setExpandableStateLocked(boolean locked) {
    if (treeHandler != null) {
      treeHandler.setExpandableStateLocked(locked);
    }
  }

  @Override
  public void setFromHTTPFormValue(Object value) {
    switch(submitValueType) {
      case Table.CSubmitValue.selected_index :
        if (value == null) {
          setSelectedIndex(-1);

          return;
        }

        if (value instanceof int[]) {
          setSelectedIndexes((int[]) value);

          break;
        }

        int n = -1;

        if (value instanceof Number) {
          n = ((Number) value).intValue();
        } else {
          String s = value.toString();

          if ((s.length() > 0) && Character.isDigit(s.charAt(0))) {
            n = SNumber.intValue(s);
          }
        }

        if ((n < -1) || (n >= size())) {
          n = -1;
        }

        setSelectedIndex(n);

        break;

      case Table.CSubmitValue.selected_linked_data :
        if (value != null) {
          if (value instanceof Object[]) {
            setSelectedIndexes(RenderableDataItem.findLinkedDataObjectsEx(listComponent, (Object[]) value));
          } else {
            setSelectedIndex(indexOfLinkedData(value));
          }
        } else {
          setSelectedIndex(-1);
        }

        break;

      default :
        setValue(value);

        break;
    }
  }

  /**
   * Sets the type of grid view. This call is only valid if the table was
   * configured to display its items in a grid.
   *
   * @param type
   *          can by the GridViewType specified in the {@code iTableComponent}
   *          interface or the string representation (DYNAMIC_COLUMNS,
   *          FIXED_COLUMNS, SINGLE_ROW_WRAPPED)
   *
   * @see iTableComponent
   * @see #refreshItems()
   */
  public void setGridViewType(Object type) {
    if (gridViewType != null) {    // can only change if it has been set internally
      if (type instanceof String) {
        type = GridViewType.valueOf(((String) type).toUpperCase(Locale.US));
      }

      if (type instanceof GridViewType) {
        tableHandler.setGridViewType((GridViewType) type);
        gridViewType = tableHandler.getGridViewType();
      }
    }
  }

  public void setHideFilteredEmptyBranches(boolean hide) {}

  @Override
  public void setIndentBy(int indent) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.setIndentBy(indent);
  }

  /**
   * Sets the rows in the table in the same form as they are stored internally
   * within the viewer.
   *
   * @param rows
   *          the raw rows retrieved from a call to getRawRows
   * @see #getRawRows()
   */
  public void setRawRows(List<RenderableDataItem> rows) {
    if (treeHandler != null) {
      treeHandler.setAll(rows);
    } else {
      tableModel.setAll(rows);
    }

    refreshItems();
  }

  @Override
  public void setRootNodeCollapsible(boolean collapsible) {}

  public void setRowHeight(int row, int height) {
    tableHandler.setRowHeight(row, height);
  }

  public void setSelectedColumnIndex(int index) {
    tableHandler.setSelectedColumnIndex(index);
  }

  @Override
  public void setSelectedIndex(int index) {
    super.setSelectedIndex(index);
    tableHandler.setSelectedColumnIndex(0);
  }

  /**
   * Gets the index of the selected item of a grid view based on its position
   * within the total list of items
   *
   * @return the position based index of the selected item
   */
  public int getGridViewSelectedPoistion() {
    int index = getSelectedIndex();

    if (tableHandler.getGridViewType() != null) {
      int col = getSelectedColumn();

      index = (index * getRowCount()) + col;
    }

    return index;
  }

  /**
   * Sets the index of the selected item of a grid view based on its position
   * within the total list of items
   *
   * @param index
   *          the position based index of the selected item
   */
  public void setGridViewSelectedPosition(int index) {
    if (tableHandler.getGridViewType() != null) {
      int n   = tableHandler.getColumnCount();
      int row = (int) Math.floor(index / n);
      int col = index % n;

      super.setSelectedIndex(row);
      tableHandler.setSelectedColumnIndex(col);
    } else {
      super.setSelectedIndex(index);
      tableHandler.setSelectedColumnIndex(0);
    }
  }

  @Override
  public void setSelectedItem(RenderableDataItem value) {
    if (!columnSelectionAllowed || (value == null)) {
      super.setSelectedItem(value);
    } else {
      RenderableDataItem parent = value.getParentItem();

      if (parent == null) {
        super.setSelectedItem(value);

        return;
      }

      int n = indexOf(parent);

      if (n != -1) {
        int c = parent.indexOf(value);

        if (c != -1) {
          super.setSelectedIndex(n);
          tableHandler.setSelectedColumnIndex(c);
        }
      }
    }
  }

  public void setSelectedColumnIndices(int[] indices) {
    tableHandler.setSelectedColumnIndices(indices);
  }

  @Override
  public void setShowRootHandles(boolean show) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.setShowRootHandles(show);
  }

  @Override
  public void setShowRootNode(boolean show) {
    if (treeHandler == null) {
      return;
    }

    treeHandler.setShowRootNode(show);
  }

  @Override
  public void setSingleClickToggle(boolean singleClickToggle) {
    if (treeHandler != null) {
      treeHandler.setSingleClickToggle(singleClickToggle);
    }
  }

  /**
   * Set the currently sortBy column and the sort order
   *
   * @param col
   *          the sortBy column
   * @param descending
   *          true if items are sorted in descending order; false for ascending
   */
  public void setSortColumn(int col, boolean descending) {
    tableHandler.setSortColumn(col, descending);
  }

  @Override
  public void setToggleOnTwistyOnly(boolean twistyOnly) {
    if (treeHandler != null) {
      treeHandler.setToggleOnTwistyOnly(twistyOnly);
    }
  }

  @Override
  public void setParentItemsSelectable(boolean parentItemsSelectable) {
    if (treeHandler != null) {
      treeHandler.setParentItemsSelectable(parentItemsSelectable);
    }
  }

  @Override
  public boolean isParentItemsSelectable() {
    if (treeHandler != null) {
      return treeHandler.isParentItemsSelectable();
    }

    return true;
  }

  @Override
  public void setExpandAll(boolean expandAll) {
    if (treeHandler != null) {
      treeHandler.setExpandAll(expandAll);
    }
  }

  @Override
  public void setTreeEventsEnabled(boolean enabled) {
    if (treeHandler != null) {
      treeHandler.setTreeEventsEnabled(enabled);
    }
  }

  @Override
  public void setTwistyMarginOfError(int twistyMarginOfError) {
    if (treeHandler != null) {
      treeHandler.setTwistyMarginOfError(twistyMarginOfError);
    }
  }

  /**
   * Sets the value at the specified location
   *
   * @param row
   *          the row
   * @param col
   *          the column position
   * @param value
   *          the value for the specified location
   */
  public void setValueAt(int row, int col, Object value) {
    col = tableHandler.convertViewIndexToModel(col);

    RenderableDataItem item = getItemAt(row, col);

    item.setValue(value);
    tableModel.rowChanged(row);
  }

  /**
   * Returns the list of child rows for a tree table or all rows if not a tree
   * table
   *
   * @param list
   *          the list to use to store the items (can be null)
   * @return the list of child rows
   */
  public List<RenderableDataItem> getChildRows(List<RenderableDataItem> list) {
    if (isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    List<RenderableDataItem> rows = getRawRows();
    int                      ecol = getExpandableColumn();

    if (list == null) {
      list = new ArrayList<RenderableDataItem>();
    }

    final int len = rows.size();

    for (int i = 0; i < len; i++) {
      iFilterableList<RenderableDataItem> items = rows.get(i).get(ecol).getItems();

      if (items != null) {
        list.addAll(items);
      }
    }

    return list;
  }

  /**
   * Get the index of the column at the specified location
   *
   * @param p
   *          the point
   * @return the column index or -1
   */
  public int getColumnIndexAt(UIPoint p) {
    return (p == null)
           ? -1
           : getColumnIndexAt(p.x, p.y);
  }

  /**
   * Gets the at the specified position
   *
   * @param col
   *          the column position; if -1 is specified then the currently
   *          selected column is used
   *
   * @return the the specified column
   */
  public Column getColumn(int col) {
    if (col < 0) {
      return null;
    }

    col = tableHandler.convertViewIndexToModel(col);

    return tableModel.getColumn(col);
  }

  /**
   * Returns the number of columns in the table.
   *
   * @return the number of columns in the table
   * @see #getRowCount
   */
  public int getColumnCount() {
    return tableModel.getColumnCount();
  }

  /**
   * Get the index of the column at the specified location
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   * @return the column index or -1
   */
  public int getColumnIndexAt(float x, float y) {
    return tableHandler.getColumnIndexAt(x, y);
  }

  /**
   * Gets the name of the column at the specified position
   *
   * @param col
   *          the column position; if -1 is specified then the currently
   *          selected column is used
   *
   * @return the name of the specified column
   */
  public String getColumnName(int col) {
    if (col < 0) {
      return null;
    }

    col = tableHandler.convertViewIndexToModel(col);

    Column c = tableModel.getColumn(col);

    return (c.columnName == null)
           ? null
           : c.columnName;
  }

  /**
   * Gets the title of the column at the specified position
   *
   * @param col
   *          the column position; if -1 is specified then the currently
   *          selected column is used
   *
   * @return the title of the specified column
   */
  public CharSequence getColumnTitle(int col) {
    if (col < 0) {
      return null;
    }

    col = tableHandler.convertViewIndexToModel(col);

    Column c = tableModel.getColumn(col);

    return (c == null)
           ? null
           : c.getColumnTitle();
  }

  /**
   * Returns a list of columns as they were originally specified
   *
   * @return a list of columns as they were originally specified
   */
  public List<Column> getColumns() {
    return Collections.unmodifiableList(tableModel.getColumns());
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    if (treeHandler != null) {
      return treeHandler.getConverter();
    } else {
      return super.getConverter();
    }
  }

  /**
   * Returns a list of the columns in display order
   *
   * @return a list of the columns in display order
   * @see #getRowCount
   */
  public List<Column> getDisplayColumns() {
    return tableModel.getColumns();
  }

  /**
   * Gets the current column being edited
   *
   * @return the current column being edited or -1 if no row is being edited
   */
  public int getEditingColumn() {
    return -1;
  }

  @Override
  public int getExpandableColumn() {
    if (treeHandler == null) {
      return -1;
    }

    return treeHandler.getExpandableColumn();
  }

  /**
   * Gets the index of the first column visible in the view
   *
   * @return the index of the first column visible in the view
   */
  public int getFirstVisibleColumnIndex() {
    return tableHandler.getFirstVisibleColumnIndex();
  }

  /**
   * Gets the index of the last column visible in the view
   *
   * @return the index of the last column visible in the view
   */
  public int getLastVisibleColumnIndex() {
    return tableHandler.getLastVisibleColumnIndex();
  }

  /**
   * Gets the type of grid view.
   *
   * @return the type or null if the table was not configured to display its
   *         items in a grid
   */
  public GridViewType getGridViewType() {
    return gridViewType;
  }

  @Override
  public Object getHTTPFormValue() {
    if (!hasSelection()) {
      return null;
    }

    switch(submitValueType) {
      case Table.CSubmitValue.selected_index :
        if (!selectAllAllowed) {
          return getSelectedIndex();
        }

        return getSelectedIndexes();

      case Table.CSubmitValue.selected_linked_data :
        if (!selectAllAllowed) {
          return this.getSelectionDataAsString();
        }

        return getSelectionsDataAsStrings();

      case Table.CSubmitValue.selected_specific_column_linked_data :
        if (!selectAllAllowed) {
          return this.getSelectionDataAsString(submitColumn);
        }

        return toString(this.getSelectionData(submitColumn));

      case Table.CSubmitValue.selected_specific_column_value_text :
        if (!selectAllAllowed) {
          return this.getSelectionAsString(submitColumn);
        }

        return toString(this.getSelection(submitColumn));

      default :
        if (!selectAllAllowed) {
          return this.getSelection();
        }

        return getSelections();
    }
  }

  /**
   * Gets the item at the specified model location
   *
   * @param row
   *          the item's row
   * @param col
   *          the item's model column
   *
   * @return the item at the specified location
   */
  public RenderableDataItem getItem(int row, int col) {
    return getItemAt(row, col);
  }

  /**
   * Gets the item at the specified location based on the current display order
   * of rows and columns
   *
   * @param row
   *          the item's row
   * @param col
   *          the item's display column
   *
   * @return the item at the specified location
   */
  public RenderableDataItem getItemAt(int row, int col) {
    col = tableHandler.convertViewIndexToModel(col);

    return tableModel.getItemAt(row, col);
  }

  public UIRectangle getItemBounds(int row, int col) {
    return tableHandler.getCellRect(row, col, true);
  }

  /**
   * Gets the parent row item of the item at the specified index
   *
   * @param index
   *          the index of the item to get the parent of
   *
   * @return the parent item or null if the item is a top level item
   */
  @Override
  public RenderableDataItem getParent(int index) {
    final int n = getParentIndex(index);

    return (n == -1)
           ? null
           : get(n);
  }

  /**
   * Gets the parent row index of the item at the specified index
   *
   * @param index
   *          the index of the item to get the parent of
   *
   * @return the parent item index or -1 if the item is a top level item
   */
  public int getParentIndex(int index) {
    if (treeHandler == null) {
      return -1;
    }

    RenderableDataItem item = treeHandler.getParent(index);

    if (item == null) {
      return -1;
    }

    while(index > 0) {
      index--;

      if (get(index) == item) {
        return index;
      }
    }

    return -1;
  }

  /**
   * Returns a list of rows as they are stored within the table model
   *
   * @return a list of rows as they are stored within the table model
   * @see #setRawRows(List)
   */
  @Override
  public List<RenderableDataItem> getRawRows() {
    if (treeHandler != null) {
      return treeHandler.getRawRows();
    }

    return tableModel.getRowsEx();
  }

  @Override
  public RenderableDataItem getRootItem() {
    if (treeHandler != null) {
      return treeHandler.getRootItem();
    }

    return null;
  }

  /**
   * Returns the index of the first selected column, -1 if no column is
   * selected.
   *
   * @return the index of the first selected column
   */
  public int getSelectedColumn() {
    return tableHandler.getSelectedColumn();
  }

  @Override
  public RenderableDataItem getSelectedItem() {
    if (!columnSelectionAllowed) {
      return super.getSelectedItem();
    }

    int col = getSelectedColumn();

    if (col == -1) {
      return super.getSelectedItem();
    }

    RenderableDataItem item = super.getSelectedItem();

    return (item == null)
           ? null
           : item.getItemEx(col);
  }

  /**
   * Gets the table's current selection. Only the item at the specified column
   * will be returned.
   * <p>
   * If the widget allows multiple selections, then this method will return an
   * array containing all the selected items.
   *
   * @param col
   *          the column to return the selection for. If -1 is specified for the
   *          column the currently selected column is used
   * @return the table's selection
   */
  public Object getSelection(int col) {
    if (!hasSelection()) {
      return null;
    }

    if (col == -1) {
      col = getSelectedColumn();

      if (col == -1) {
        col = 0;
      }
    }

    col = tableHandler.convertViewIndexToModel(col);

    if (!selectAllAllowed) {
      return tableModel.getItemAt(getSelectedIndex(), col);
    }

    int                  sels[] = getSelectedIndexes();
    final int            len    = sels.length;
    RenderableDataItem[] a      = new RenderableDataItem[len];

    for (int i = 0; i < len; i++) {
      a[i] = tableModel.getItemAt(sels[i], col);
    }

    return a;
  }

  /**
   * Gets the table's current selection. Only the item at the specified column
   * will be returned.
   * <p>
   * If the widget allows multiple selections, then this method will return an
   * array containing all the selected items.
   *
   * @param col
   *          the column to return the selections for. If -1 is specified for
   *          the column the currently selected column is used
   * @return a string representation of the table's selection
   */
  public String getSelectionAsString(int col) {
    Object o = getSelection(col);

    if (o instanceof Object[]) {
      return Helper.toString((Object[]) o, "\t");
    }

    return (o == null)
           ? null
           : o.toString();
  }

  /**
   * Gets the linked data associated with the table's current selection Only the
   * item at the specified column will be returned.
   * <p>
   * If the widget allows multiple selections, then this method will return an
   * array containing all the selected items.
   *
   * @param col
   *          the column to return the selections for. If -1 is specified for
   *          the column the currently selected column is used
   * @return the selection's linked data
   */
  public Object getSelectionData(int col) {
    col = tableHandler.convertViewIndexToModel(col);

    return DataItemCollection.getSelectionData(this, selectAllAllowed, col);
  }

  /**
   * Gets the linked data associated with the table's current selection Only the
   * item at the specified column will be returned.
   * <p>
   * If the widget allows multiple selections, then this method will return an
   * array containing all the selected items.
   *
   * @param col
   *          the column to return the selection for. If -1 is specified for the
   *          column the currently selected column is used
   * @return a string representation of the selection's linked data
   */
  public String getSelectionDataAsString(int col) {
    Object o = getSelectionData(col);

    if (o instanceof Object[]) {
      return Helper.toString((Object[]) o, "\t");
    }

    return (o == null)
           ? null
           : o.toString();
  }

  @Override
  public Object[] getSelections() {
    RenderableDataItem[] b    = null;
    int[]                sels = getSelectedIndexes();

    if (sels != null) {
      final int len = sels.length;

      b = new RenderableDataItem[len];

      for (int i = 0; i < len; i++) {
        b[i] = getConvertedRow(sels[i]);
      }
    }

    return b;
  }

  @Override
  public String[] getSelectionsAsStrings() {
    return getSelectionsAsStrings(false);
  }

  /**
   * Returns a string array of the linked data of all of the selected rows, in
   * increasing order.
   *
   * @return a string array of the linked data of all of the selected rows, or
   *         null if there are no selections
   */
  @Override
  public String[] getSelectionsDataAsStrings() {
    return getSelectionsAsStrings(true);
  }

  /**
   * Gets the index of the column the table is currently sorted by
   *
   * @return the index of the column or -1
   */
  public int getSortColumn() {
    return tableHandler.getSortColumn();
  }

  /**
   * Gets the table component associated with the viewer
   * @return the table component associated with the viewer
   */
  public iTableComponent getTableComponent() {
    return tableHandler;
  }

  /**
   * Gets the tree handler associated with the viewer
   * @return the tree handler associated with the viewer or null if not a tree table
   */
  public iTreeHandler getTreeHandler() {
    return treeHandler;
  }

  @Override
  public int getTwistyMarginOfError() {
    if (treeHandler != null) {
      return treeHandler.getTwistyMarginOfError();
    }

    return 0;
  }

  /**
   * Gets the value at the specified location
   *
   * @param row
   *          the row
   * @param col
   *          the column
   *
   * @return the value at the specified location
   */
  public Object getValueAt(int row, int col) {
    RenderableDataItem item = getItemAt(row, col);

    return (item == null)
           ? null
           : item.getValue();
  }

  /**
   * Converts the specified item using the specified column's data type converters
   *
   * @param col the column
   * @param item
   *          the item to convert
   */
  public void convert(int col, RenderableDataItem item) {
    Column c = getColumn(col);

    c.convert(this, item);
  }

  @Override
  public boolean isAutoScrollOnExpansion() {
    if (treeHandler != null) {
      return treeHandler.isAutoScrollOnExpansion();
    }

    return false;
  }

  @Override
  public boolean isExpandableStateLocked() {
    if (treeHandler != null) {
      return treeHandler.isExpandableStateLocked();
    }

    return false;
  }

  @Override
  public boolean isFiltered() {
    if (treeHandler != null) {
      return treeHandler.isFiltered();
    }

    return false;
  }

  public boolean isHideFilteredEmptyBranches() {
    return false;
  }

  @Override
  public boolean isItemEditable(RenderableDataItem item) {
    if (treeHandler != null) {
      return treeHandler.isItemEditable(item);
    }

    return false;
  }

  @Override
  public boolean isLeafItem(int index) {
    if (treeHandler != null) {
      return treeHandler.isLeafItem(index);
    }

    return false;
  }

  @Override
  public boolean isRootNodeCollapsible() {
    return false;
  }

  @Override
  public boolean isRowExpanded(int row) {
    if (treeHandler == null) {
      return false;
    }

    return treeHandler.isRowExpanded(row);
  }

  @Override
  public boolean isRowExpanded(RenderableDataItem item) {
    if (treeHandler == null) {
      return false;
    }

    return treeHandler.isRowExpanded(item);
  }

  @Override
  public boolean isSingleClickToggle() {
    if (treeHandler != null) {
      return treeHandler.isSingleClickToggle();
    }

    return false;
  }

  /**
   * Gets whether the current sort is in descending order. Only meaningful if
   * getSortColumn() returns something other than -1
   *
   * @return true if it is; false otherwise
   * @see #getSortColumn()
   */
  public boolean isSortDescending() {
    return tableHandler.isSortDescending();
  }

  @Override
  public boolean isTabular() {
    return true;
  }

  @Override
  public boolean isToggleOnTwistyOnly() {
    if (treeHandler != null) {
      return treeHandler.isToggleOnTwistyOnly();
    }

    return false;
  }

  /**
   * Returns whether or not this is a tree table
   *
   * @return true if it is a tree table; false otherwise
   */
  public boolean isTreeTable() {
    return treeHandler != null;
  }

  protected void addTreeRow(RenderableDataItem row) {
    Object o = row.getModelData();
    int    l = 1;

    if (o instanceof MutableInteger) {
      MutableInteger level = (MutableInteger) o;

      row.setModelData(null);
      l = level.intValue();

      if (l < 1) {
        l = 1;
      }
    }

    if ((l < 2) || (currentRow == null)) {
      getTempList().add(row);
      currentRow = row;

      if (levelStack != null) {
        levelStack.clear();
      }
    } else {
      RenderableDataItem ci = null;

      if (levelStack == null) {
        levelStack = new LinkedList<RenderableDataItem>();
      }

      int currentLevel = levelStack.size() + 1;

      if (currentLevel > l) {
        while(currentLevel > l) {
          levelStack.poll();
          currentLevel--;
        }

        levelStack.peek().add(row);
      } else if (currentLevel == l) {
        levelStack.peek().add(row);
      } else {
        ci = currentRow;
        ci = ci.getItem(treeHandler.getExpandableColumn());
        ci.add(row);

        if (levelStack.peek() != currentRow) {
          levelStack.add(0, ci);
        }
      }

      currentRow = row;
    }
  }

  protected void configureEx(Table cfg) {
    createModelAndComponents(cfg);
    configureEx(cfg, true, true, true);

    if (!cfg.focusPainted.spot_hasValue() ||!cfg.focusPainted.booleanValue()) {
      formComponent.setFocusPainted(true);
    }

    iPlatformListHandler comp  = listComponent;
    iTableComponent      table = tableHandler;

    this.setSubItems(tableModel);
    comp.setDeselectEventsDisabled(!cfg.deselectEventsEnabled.booleanValue());
    if (cfg.singleClickActionEnabled.spot_valueWasSet()) {
      comp.setSingleClickAction(cfg.singleClickActionEnabled.booleanValue());
    }
    else if(Platform.isTouchDevice()) {
      comp.setSingleClickAction(true);
    }

    switch(cfg.selectionMode.intValue()) {
      case ListBox.CSelectionMode.multiple :
        setSelectionMode(SelectionMode.MULTIPLE);

        break;

      case ListBox.CSelectionMode.block :
        setSelectionMode(SelectionMode.BLOCK);

        break;

      case ListBox.CSelectionMode.invisible :
        setSelectionMode(SelectionMode.INVISIBLE);

        break;

      case ListBox.CSelectionMode.none :
        setSelectionMode(SelectionMode.NONE);

        break;

      default :
        setSelectionMode(SelectionMode.SINGLE);

        break;
    }

    TableStyle tableStyle = new TableStyle();

    if (cfg.visibleRowCount.spot_valueWasSet()) {
      int n = cfg.visibleRowCount.intValue();

      comp.setVisibleRowCount(n);
    } else {
      listComponent.setVisibleRowCount(TableHelper.getDefaultPreferredRows());
    }

    listComponent                              = comp;
    tableStyle.columnReorderingAllowed         = cfg.columnReorderingAllowed.booleanValue();
    tableStyle.columnResizingAllowed           = cfg.columnResizingAllowed.booleanValue();
    tableStyle.columnSortingAllowed            = cfg.columnSortingAllowed.booleanValue();
    tableStyle.columnSelectionAllowed          = cfg.columnSelectionAllowed.booleanValue();
    tableStyle.extendBackgroundRendering       = cfg.extendBackgroundRendering.booleanValue();
    tableStyle.colHeaderHotspotsSupported      = cfg.colHeaderHotspotsSupported.booleanValue();
    tableStyle.sortColumnHiliteColor           = getColor(cfg.sortColumnHighlightColor.getValue());
    tableStyle.hiliteSortColumn                = cfg.highlightSortColumn.booleanValue();
    tableStyle.rowHeaderFooterSelectionPainted = cfg.rowHeaderFooterSelectionPainted.booleanValue();
    tableStyle.backgroundHiliteColor           = getColor(cfg.alternatingHighlightColor.getValue());
    tableStyle.headerForeground                = getColor(cfg.headerFgColor.getValue());
    tableStyle.headerFont                      = getFont(cfg.headerFont);

    if (cfg.boldColumnHeaders.booleanValue()) {
      if (tableStyle.headerFont == null) {
        tableStyle.headerFont = getFont().deriveBold();
      } else {
        tableStyle.headerFont = tableStyle.headerFont.deriveBold();
      }
    }

    if (cfg.headerBgColor.spot_hasValue()) {
      table.setHeaderBackground(UIColorHelper.getPaintBucket(cfg.headerBgColor));
    }

    tableStyle.headerCellPainter   = ColorUtils.configure(this, cfg.getHeaderCell(), null);
    tableStyle.headerFillerPainter = ColorUtils.configure(this, cfg.getHeaderRightFillerCell(), null);

    if ((tableStyle.sortColumnHiliteColor == null) && tableStyle.hiliteSortColumn) {
      tableStyle.sortColumnHiliteColor = new UIColor(0, 0, 0, 10);
    }

    switch(cfg.alternatingHighlightType.intValue()) {
      case Table.CAlternatingHighlightType.row :
        tableStyle.backgroundHilite = BackgroundHighlight.ROW;

        break;

      case Table.CAlternatingHighlightType.column :
        tableStyle.backgroundHilite = BackgroundHighlight.COLUMN;

        break;

      default :
        break;
    }

    if ((tableStyle.backgroundHiliteColor == null) && (tableStyle.backgroundHilite != null)) {
      tableStyle.backgroundHiliteColor = new UIColor(247, 247, 247);
    }

    if (cfg.displayAsGridView.booleanValue() &&!cfg.gridLineType.spot_valueWasSet()) {
      tableStyle.showHorizontalLines = false;
      tableStyle.showVerticalLines   = false;
    } else {
      switch(cfg.gridLineType.intValue()) {
        case Table.CGridLineType.both :
          tableStyle.showHorizontalLines = true;
          tableStyle.showVerticalLines   = true;

          break;

        case Table.CGridLineType.horizontal_lines :
          tableStyle.showHorizontalLines = true;

          break;

        case Table.CGridLineType.vertical_lines :
          tableStyle.showVerticalLines = true;

          break;

        default :
          break;
      }
    }

    tableStyle.gridColor = getColor(cfg.gridLineColor.getValue());

    if ((tableStyle.gridColor == null) && (tableStyle.showVerticalLines || tableStyle.showHorizontalLines)) {
      tableStyle.gridColor = ColorUtils.getListDividerColor();
    }

    switch(cfg.gridLineStyle.intValue()) {
      case Table.CGridLineStyle.dotted :
        tableStyle.gridLineStroke = UIStroke.DOTTED_STROKE;

        break;

      case Table.CGridLineStyle.dashed :
        tableStyle.gridLineStroke = UIStroke.DASHED_STROKE;

        break;

      default :
        tableStyle.gridLineStroke = UIStroke.SOLID_STROKE;

        break;
    }

    tableStyle.headerMarginColor       = getColor(cfg.headerSeparatorColor.getValue());
    tableStyle.headerBottomMarginColor = getColor(cfg.headerBottomSeparatorColor.getValue());

    boolean wordWrap = cfg.wordWrapByDefault.booleanValue();

    if (!cfg.autoSizeColumnsToFit.booleanValue() && cfg.autoSizeRowsToFit.booleanValue()) {
      wordWrap = true;
    }

    int               len     = cfg.columns.size();
    ArrayList<Column> columns = new ArrayList<Column>((len == 0)
            ? 10
            : len);
    Column            col;
    UIFont            headerFont = tableStyle.headerFont;

    for (int i = 0; i < len; i++) {
      ItemDescription id = (ItemDescription) cfg.columns.getEx(i);

      col = createColumn(id);

      if (wordWrap &&!id.wordWrap.spot_valueWasSet()) {
        col.wordWrap = true;
      }

      col.setWidth(col.calculatePreferedWidth(dataComponent, 400));

      if (col.getHeaderFont() == null) {
        col.setHeaderFont(headerFont);
      }

      columns.add(col);
    }

    if (len == 0) {
      col = new Column(" ");

      if (wordWrap) {
        col.wordWrap = true;
      }

      col.setHeaderFont(headerFont);
    }

    columnCount = len;
    tableModel.resetModel(columns, new FilterableList<RenderableDataItem>());
    configureSelectionModelGroup(cfg.selectionGroupName, new Object());
    this.setSubItems(tableModel);

    String s = null;

    if (cfg.rowHeight.spot_valueWasSet()) {
      s = cfg.rowHeight.getValue();
    }

    if (s == null) {
      s = PlatformHelper.getDefaultRowHeight();
    }

    setRowHeight(UIScreen.toPlatformPixelHeight(s, dataComponent, 400, true));

    PaintBucket pb = ColorUtils.configure(this, cfg.getSelectionPainter(), null);

    if (pb != null) {
      listComponent.getItemRenderer().setSelectionPaint(pb);
    }

    if (cfg.changeSelColorOnLostFocus.booleanValue()) {
      listComponent.setChangeSelColorOnLostFocus(true);
      pb = ColorUtils.configure(this, cfg.getLostFocusSelectionPainter(), null);

      if (pb != null) {
        listComponent.getItemRenderer().setSelectionPaint(pb);
      }
    }

    int initialSortColumn = -1;

    if (cfg.sortedByColumn.spot_hasValue()) {
      initialSortColumn = cfg.sortedByColumn.intValue();
    }

    if (initialSortColumn != -1) {
      tableHandler.setSortColumn(initialSortColumn, "true".equals(cfg.sortedByColumn.spot_getAttribute("descending")));
    }

    listComponent.setHandleFirstFocusSelection(cfg.handleFirstFocusSelection.booleanValue());
    initiallySelectedIndex = cfg.selectedIndex.intValue();
    submitValueType        = cfg.submitValue.intValue();
    submitColumn           = cfg.submitColumn.intValue();

    if ((submitColumn < 0) || (submitColumn > columnCount)) {
      submitColumn = 0;
    }

    gridViewType           = tableHandler.getGridViewType();
    columnSelectionAllowed = tableStyle.columnSelectionAllowed || (gridViewType != null);

    if (treeHandler != null) {
      TreeTable tt = (TreeTable) cfg;

      treeHandler.setParentItemsSelectable(tt.parentItemsSelectable.booleanValue());
      treeHandler.setIndentBy(UIScreen.platformPixels(tt.indentBy.intValue()));
      treeHandler.setExpandAll(tt.expandAll.booleanValue());
      treeHandler.setShowRootHandles(tt.showRootHandles.booleanValue());

      if (tt.toggleOnTwistyOnly.spot_hasValue()) {
        treeHandler.setToggleOnTwistyOnly(tt.toggleOnTwistyOnly.booleanValue());
      } else {
        treeHandler.setToggleOnTwistyOnly(!Platform.isTouchDevice());
      }
    }

    table.setStyle(tableStyle);
    table.setTable();
  }

  protected void convertRows(int[] sels) {
    int len = (sels == null)
              ? 0
              : sels.length;

    for (int i = 0; i < len; i++) {
      getConvertedRow(i);
    }
  }

  protected void fixColumns(RenderableDataItem item) {
    int len = item.getItemCount();

    if (len < columnCount) {
      item.setItemCount(columnCount);
    }
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      if ((treeHandler != null) && l.isExpandedEventsEnabled()) {
        treeHandler.addExpandedListener(l);
      }

      if ((treeHandler != null) && l.isExpansionEventsEnabled()) {
        treeHandler.addExpansionListener(l);
      }
    }
  }

  protected RenderableDataItem getConvertedRow(int row) {
    RenderableDataItem item = tableModel.get(row);

    if ((item != null) &&!item.isStateFlagSet((byte) 64)) {
      item.setStateFlag((byte) 64);

      int len = tableModel.getColumnCount();

      for (int i = 0; i < len; i++) {
        tableModel.getItemAt(row, i);
      }
    }

    return item;
  }

  protected String[] getSelectionsAsStrings(boolean data) {
    int[] sels = getSelectedIndexes();

    if ((sels == null) || (sels.length == 0)) {
      return null;
    }

    convertRows(sels);

    List<RenderableDataItem> rows = tableModel.getRowsEx();

    if (data) {
      return DataItemCollection.getValuesAsStrings(rows, sels, -1, true, true, null);
    } else {
      return DataItemCollection.getValuesAsStrings(rows, sels, -1, false, true, null);
    }
  }

  @SuppressWarnings("resource")
  @Override
  protected String getWidgetAttribute(String name) {
    int n   = name.indexOf('[');
    int row = -1;
    int col = -1;

    if (n != -1) {
      CharScanner sc = new CharScanner(name);

      sc.trim();
      sc.chop(1);    // remove ']'
      name = sc.nextToken('[');
      col  = SNumber.intValue(resolvePotentialVariableOrFunction(sc.nextToken(',', true)));

      if (sc.foundDelimiter == ',') {
        row = col;
        col = SNumber.intValue(resolvePotentialVariableOrFunction(sc.nextToken(',', true)));
      }
    }

    if (row == -1) {
      if (name.equals(iConstants.WIDGET_ATT_SELECTION_DATA)) {
        return this.getSelectionDataAsString(col);
      }

      if (name.equals(iConstants.WIDGET_ATT_SELECTION_VALUE)) {
        return this.getSelectionAsString(col);
      }

      if (name.equals(iConstants.WIDGET_ATT_DATA)) {
        RenderableDataItem di = getItemEx(col);
        Object             o  = (di == null)
                                ? null
                                : di.getLinkedData();

        return (o == null)
               ? null
               : o.toString();
      }

      if (name.equals(iConstants.WIDGET_ATT_VALUE)) {
        RenderableDataItem di = getItemEx(col);

        return (di == null)
               ? null
               : Helper.toString(di.getItems(), ",");
      }

      if (name.equals(iConstants.WIDGET_ATT_COLUMN_NAME)) {
        return this.getColumnName(col);
      }

      return super.getWidgetAttribute(name);
    } else {
      RenderableDataItem di = tableModel.getItemAt(row, col);

      if (name.equals(iConstants.WIDGET_ATT_VALUE)) {
        return (di == null)
               ? null
               : di.toString();
      }

      if (name.equals(iConstants.WIDGET_ATT_DATA)) {
        if (di != null) {
          Object o = di.getLinkedData();

          return (o == null)
                 ? null
                 : o.toString();
        }
      }

      return null;
    }
  }

  protected void adjustMultiTableRenderer(aListItemRenderer lr, TableType type) {
    PaintBucket pb = lr.getSelectionPaint();

    if ((pb != null) && (pb.getBorder() instanceof UIMatteBorder)) {
      pb = (PaintBucket) pb.clone();

      UIMatteBorder b = (UIMatteBorder) ((UIMatteBorder) pb.getBorder()).clone();

      pb.setBorder(b);

      UIInsets in = b.getBorderInsetsEx(null);

      switch(type) {
        case HEADER :
          in.right = 0;

          break;

        case FOOTER :
          in.left = 0;

          break;

        default :
          in.left  = 0;
          in.right = 0;

          break;
      }

      b.setInsets(in);
      lr.setSelectionPaint(pb);
    }
  }

  protected int getMiltiTableConfigurationType(Table cfg) {
    int type = 0;
    int len  = cfg.columns.size();

    for (int i = 0; i < len; i++) {
      ItemDescription id = (ItemDescription) cfg.columns.getEx(i);

      switch(id.renderType.intValue()) {
        case ItemDescription.CRenderType.header :
        case ItemDescription.CRenderType.header_index :
        case ItemDescription.CRenderType.header_index_normal :
        case ItemDescription.CRenderType.header_normal :
          type |= 1;

          break;

        case ItemDescription.CRenderType.footer_normal :
        case ItemDescription.CRenderType.footer :
        case ItemDescription.CRenderType.footer_index :
        case ItemDescription.CRenderType.footer_index_normal :
          type |= 2;

          break;

        default :
          break;
      }
    }

    return type;
  }

  private String[] toString(Object o) {
    if (!(o instanceof Object[])) {
      if (o == null) {
        return null;
      }

      return new String[] { o.toString() };
    }

    Object[] a   = (Object[]) o;
    int      len = a.length;
    String[] s   = new String[len];

    for (int i = 0; i < len; i++) {
      s[i] = (a[i] == null)
             ? ""
             : a[i].toString();
    }

    return s;
  }

  private Comparator getComparator(int col, boolean descending) {
    SubItemComparator c = new SubItemComparator(tableModel, descending);

    c.setColumn(col);

    return c;
  }
}
