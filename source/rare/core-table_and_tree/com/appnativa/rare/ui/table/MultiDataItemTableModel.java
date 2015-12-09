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
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiDataItemTableModel extends DataItemTableModel {
  public MultiDataItemTableModel(iTableModel main) {
    mainModel = main;
  }

  protected int              headerColumnCount;
  protected int              footerColumnStart;
  protected int              totalColumns;
  protected iTableModel      headerModel;
  protected iTableModel      footerModel;
  protected iTableModel      mainModel;
  protected static final int HEADER = 0;
  protected static final int MAIN   = 1;
  protected static final int FOOTER = 2;
  private Column             columnsArray[];
 
  public int getHeaderColumnCount() {
    return headerColumnCount;
  }

  public Column[] getColumnsEx() {
    if (columnsArray == null) {
      columnsArray = columnData.toArray(new Column[columnCount]);
    }

    return columnsArray;
  }

  @Override
  public void resetModel(List<Column> columns, iFilterableList<RenderableDataItem> rows) {
    int          len    = columns.size();
    List<Column> header = null;
    List<Column> footer = null;
    List<Column> main   = new ArrayList<Column>(len);

    for (int i = 0; i < len; i++) {
      Column c = columns.get(i);

      if (c.isHeaderColumn()) {
        if (header == null) {
          header = new ArrayList<Column>();
        }

        header.add(c);
      } else if (c.isFooterColumn()) {
        if (footer == null) {
          footer = new ArrayList<Column>();
        }

        footer.add(c);
      } else {
        main.add(c);
      }
    }

    columnData = new ArrayList<Column>(len);

    if (header != null) {
      columnData.addAll(header);
    }

    columnData.addAll(main);

    if (footer != null) {
      columnData.addAll(footer);
    }

    columns = columnData;
    setColumnCounts(len, (header == null)
                         ? 0
                         : header.size(), (footer == null)
            ? 0
            : footer.size());
    mainModel.resetModel(main, new FilterableList<RenderableDataItem>());

    if (header != null) {
      headerModel.resetModel(header, new FilterableList<RenderableDataItem>());
    }

    if (footer != null) {
      footerModel.resetModel(footer, new FilterableList<RenderableDataItem>());
    }

    this.columnsArray = null;
    super.resetModel(columns, rows);
  }

  public void setHeaderColumnCount(int headerColumnCount) {
    this.headerColumnCount = headerColumnCount;
  }

  public int getFooterColumnStart() {
    return footerColumnStart;
  }

  public void setHeaderModel(iTableModel header) {
    headerModel = header;
  }

  public void setFooterModel(iTableModel footer) {
    footerModel = footer;
  }

  public void setColumnCounts(int totalColumns, int headerColumns, int footerColumns) {
    this.totalColumns = totalColumns;
    headerColumnCount = headerColumns;
    footerColumnStart = totalColumns - footerColumns;
  }

  @Override
  public boolean add(RenderableDataItem e) {
    add(-1, e);

    return true;
  }

  @Override
  public boolean isFiltered() {
    return true;
  }

  @Override
  public void clear() {
    if (footerModel != null) {
      footerModel.clear();
    }

    if (headerModel != null) {
      headerModel.clear();
    }

    if (mainModel != null) {
      mainModel.clear();
    }

    super.clear();
  }

  ;
  @Override
  public void swap(int index1, int index2) {
    super.swap(index1, index2);

    if (headerModel != null) {
      headerModel.swap(index1, index2);
    }

    if (footerModel != null) {
      footerModel.swap(index1, index2);
    }

    mainModel.swap(index1, index2);
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem element) {
    RenderableDataItem item = super.set(index, element);

    if (headerModel != null) {
      setRow(index, element, HEADER);
    }

    if (footerModel != null) {
      setRow(index, element, FOOTER);
    }

    setRow(index, element, MAIN);

    return item;
  }

  @Override
  public void removeRow(int row) {
    remove(row);
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem item = super.remove(index);

    if (headerModel != null) {
      headerModel.remove(index);
    }

    if (footerModel != null) {
      footerModel.remove(index);
    }

    mainModel.remove(index);

    return item;
  }

  @Override
  public void removeRows(int firstRow, int lastRow) {
    super.removeRows(firstRow, lastRow);

    if (headerModel != null) {
      headerModel.removeRows(firstRow, lastRow);
    }

    if (footerModel != null) {
      footerModel.removeRows(firstRow, lastRow);
    }

    mainModel.removeRows(firstRow, lastRow);
  }

  @Override
  public void add(int index, RenderableDataItem element) {
    if (index == -1) {
      super.add(element);
    } else {
      super.add(index, element);
    }

    if (headerModel != null) {
      addRow(index, element, HEADER);
    }

    if (footerModel != null) {
      addRow(index, element, FOOTER);
    }

    addRow(index, element, MAIN);
  }

  void addRow(int index, RenderableDataItem item, int type) {
    int                                 capacity;
    int                                 start;
    iFilterableList<RenderableDataItem> list;
    int                                 end;

    switch(type) {
      case HEADER :
        end      = headerColumnCount - 1;
        capacity = headerColumnCount;
        start    = 0;
        list     = headerModel;

        break;

      case FOOTER :
        end      = totalColumns - 1;
        capacity = totalColumns - footerColumnStart;
        start    = footerColumnStart;
        list     = footerModel;

        break;

      default :
        end      = totalColumns - (totalColumns - footerColumnStart);
        start    = headerColumnCount;
        capacity = end - start + 1;
        list     = mainModel;

        break;
    }

    RenderableDataItem row = new RenderableDataItem();

    row.ensureCapacity(capacity);

    for (int i = start; i <= end; i++) {
      row.add(item.getItemEx(i));
    }

    if (index == -1) {
      list.add(row);
    } else {
      list.add(index, row);
    }
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    super.setAll(collection);
    updateModels(true);

    return true;
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> collection) {
    super.addAll(collection);
    updateModels(true);

    return true;
  }

  @Override
  public void refreshItems() {
    super.refreshItems();

    if (headerModel != null) {
      headerModel.refreshItems();
    }

    mainModel.refreshItems();

    if (footerModel != null) {
      footerModel.refreshItems();
    }
  }

  @Override
  public void tableDataChanged() {
    updateModels(false);
    super.tableDataChanged();
    mainModel.tableDataChanged();

    if (headerColumnCount > 0) {
      headerModel.tableDataChanged();
    }

    if (footerColumnStart < totalColumns) {
      footerModel.tableDataChanged();
    }
  }

  @Override
  public boolean addAll(int i, Collection<? extends RenderableDataItem> collection) {
    super.addAll(i, collection);
    updateModels(true);

    return true;
  }

  void updateModels(boolean notify) {
    boolean trim = (mainModel.size() > 500) && (size() < 400);

    if (headerColumnCount > 0) {
      headerModel.getRowsEx().clear();
      TableHelper.getSubList(getRowsEx(), 0, headerColumnCount - 1, headerModel.getRowsEx());

      if (trim) {
        headerModel.trimToSize();
      }
    }

    mainModel.getRowsEx().clear();
    TableHelper.getSubList(getRowsEx(), headerColumnCount, footerColumnStart - 1, mainModel.getRowsEx());

    if (trim) {
      mainModel.trimToSize();
    }

    if (footerColumnStart < totalColumns) {
      footerModel.getRowsEx().clear();
      TableHelper.getSubList(getRowsEx(), footerColumnStart, totalColumns - 1, footerModel.getRowsEx());

      if (trim) {
        footerModel.trimToSize();
      }
    }

    if (notify) {
      mainModel.tableDataChanged();

      if (headerColumnCount > 0) {
        headerModel.tableDataChanged();
      }

      if (footerColumnStart < totalColumns) {
        footerModel.tableDataChanged();
      }
    }
  }

  void setRow(int index, RenderableDataItem item, int type) {
    int         start;
    iTableModel list;
    int         end;

    switch(type) {
      case HEADER :
        end   = headerColumnCount - 1;
        start = 0;
        list  = headerModel;

        break;

      case FOOTER :
        end   = totalColumns - 1;
        start = footerColumnStart;
        list  = footerModel;

        break;

      default :
        end   = totalColumns - (totalColumns - footerColumnStart);
        start = headerColumnCount;
        list  = mainModel;

        break;
    }

    RenderableDataItem row = list.get(index);

    row.clearSubItems();

    for (int i = start; i <= end; i++) {
      row.add(item.getItemEx(i));
    }
  }
}
