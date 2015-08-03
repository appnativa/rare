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

import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.aPlatformDataModel;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.rare.util.ItemStringConverter;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharacterIndex;
import com.appnativa.util.FilterableList;
import com.appnativa.util.IntList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public abstract class aDataItemTableModel extends aPlatformDataModel implements iTableModel {
  ItemStringConverter columnConverter;

  /**  */
  protected int activeColumn = 0;

  /**  */
  protected int columnCount = 0;

  /**  */
  protected int expandableColumn = 0;

  /**  */
  protected int           rowCount       = 0;
  protected boolean       sortingAllowed = true;
  protected SelectionType selectionType  = SelectionType.ROW_ON_BOTTOM;
  protected List<Column>  columnData;
  protected IntList       editingMarks;
  protected boolean       hasModifiedValues;

  /**  */
  protected SubItemComparator                   itemComparator;
  protected EventListenerList                   listenerList;
  protected iFilterableList<RenderableDataItem> rowData;
  protected iWidget                             theWidget;
  private boolean                               eventsEnabled = true;
  private iFilterableList<RenderableDataItem>   filterableList;

  @Override
  public boolean add(RenderableDataItem e) {
    rowData.add(e);
    updateForInsertionOrDeletion();

    return true;
  }

  @Override
  public void add(int i, RenderableDataItem e) {
    rowData.add(i, e);

    if (i == -1) {
      updateForInsertionOrDeletion();
    } else {
      rowsInserted(i, i);
    }

    rowCount = rowData.size();
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> collection) {
    rowData.addAll(collection);
    updateForInsertionOrDeletion();

    return true;
  }

  @Override
  public boolean addAll(int i, Collection<? extends RenderableDataItem> collection) {
    int csize = collection.size();

    if (csize == 0) {
      return false;
    }

    rowData.addAll(i, collection);

    if (i == -1) {
      i = rowCount;
    }

    rowsInserted(i, i + csize - 1);
    rowCount = rowData.size();

    return true;
  }

  @Override
  public void addDataModelListener(iDataModelListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listenerList.add(iDataModelListener.class, l);
  }

  @Override
  public void addIndexToFilteredList(int index) {
    rowData.addIndexToFilteredList(index);
    updateForInsertionOrDeletion();
  }

  @Override
  public void addRow(RenderableDataItem row) {
    rowData.add(row);
    updateForInsertionOrDeletion();
  }

  @Override
  public void addRow(int index, RenderableDataItem row) {
    add(index, row);
  }

  @Override
  public void addRowEx(RenderableDataItem row) {
    rowData.add(row);
  }

  @Override
  public void addRowEx(int index, RenderableDataItem row) {
    rowData.add(index, row);
  }

  @Override
  public void addRows(Collection<? extends RenderableDataItem> rows) {
    addAll(rows);
  }

  @Override
  public void addRows(int index, Collection<? extends RenderableDataItem> rows) {
    addAll(index, rows);
  }

  @Override
  public void addRowsEx(Collection<? extends RenderableDataItem> rows) {
    rowData.addAll(rows);
  }

  @Override
  public void addRowsEx(int index, Collection<? extends RenderableDataItem> rows) {
    rowData.addAll(index, rows);
  }

  @Override
  public void addToFilteredList(RenderableDataItem row) {
    rowData.addToFilteredList(row);
    updateForInsertionOrDeletion();
  }

  @Override
  public void addToFilteredList(int index, RenderableDataItem row) {
    rowData.addToFilteredList(index, row);
    updateForInsertionOrDeletion();
  }

  @Override
  public void clear() {
    if (rowCount > 0) {
      clearRows();
      fireTableDataChanged();
    }
  }

  public void clearEx() {
    clearRows();
  }

  @Override
  public void clearTable() {
    columnData.clear();
    clearRows();
    tableChanged();
  }

  @Override
  public void clearTableData() {
    clearRows();
    tableDataChanged();
  }

  @Override
  public void clearTableDataEx() {
    clearRows();
    hasModifiedValues = false;
  }

  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return rowData.concat(e);
  }

  @Override
  public boolean contains(Object o) {
    return rowData.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> clctn) {
    return rowData.containsAll(clctn);
  }

  @Override
  public void dispose() {
    clearRows();

    if (listenerList != null) {
      listenerList.clear();
    }

    itemComparator  = null;
    columnData      = null;
    rowData         = null;
    rowCount        = 0;
    theWidget       = null;
    filterableList  = null;
    listenerList    = null;
    columnConverter = null;
  }

  @Override
  public void editModeChangeAllMarks(boolean mark) {
    if (editingMarks != null) {
      if (mark) {
        int len = size();

        editingMarks.ensureCapacity(len);

        int[] a = editingMarks.A;

        for (int i = 0; i < len; i++) {
          a[i] = i;
        }

        editingMarks._length = len;
      } else {
        editingMarks.clear();
      }
    }
  }

  @Override
  public void editModeChangeMark(int index, boolean mark) {
    if (editingMarks != null) {
      int i = editingMarks.indexOf(index);

      if (mark) {
        if (i == -1) {
          editingMarks.add(index);
        }
      } else if (i != -1) {
        editingMarks.removeAt(i);
      }
    }
  }

  @Override
  public void editModeClearMarks() {
    if (editingMarks != null) {
      editingMarks._length = 0;
    }
  }

  @Override
  public int editModeGetMarkCount() {
    return (editingMarks == null)
           ? 0
           : editingMarks._length;
  }

  @Override
  public int[] editModeGetMarkedIndices() {
    if ((editingMarks == null) || (editingMarks._length == 0)) {
      return null;
    }

    return editingMarks.toArray();
  }

  @Override
  public RenderableDataItem[] editModeGetMarkedItems() {
    if ((editingMarks == null) || (editingMarks._length == 0)) {
      return null;
    }

    int                  len   = editingMarks._length;
    int[]                a     = editingMarks.A;
    RenderableDataItem[] items = new RenderableDataItem[len];

    for (int i = 0; i < len; i++) {
      items[i] = get(a[i]);
    }

    return items;
  }

  @Override
  public boolean editModeIsItemMarked(int index) {
    if ((editingMarks == null) || (editingMarks._length == 0)) {
      return false;
    }

    return editingMarks.indexOf(index) != -1;
  }

  @Override
  public void editModeToggleMark(int index) {
    if (editingMarks != null) {
      int i = editingMarks.indexOf(index);

      if (i == -1) {
        editingMarks.add(index);
      } else {
        editingMarks.removeAt(i);
      }
    }
  }

  @Override
  public void ensureCapacity(int capacity) {
    rowData.ensureCapacity(capacity);
  }

  @Override
  public boolean filter(iFilter filter) {
    boolean changed = false;

    if (filter == null) {
      changed = rowData.unfilter();
    } else {
      rowData.setConverter(getConverter());
      changed = rowData.filter(filter);
    }

    if (changed) {
      tableDataChanged();
    }

    return changed;
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    rowData.setFilteredList(list, lastFilter);
    hasModifiedValues = false;
    tableDataChanged();
  }

  @Override
  public void filter(int index) {
    rowData.addIndexToFilteredList(index);
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    return filter(filter, contains, false, false);
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    boolean changed = false;

    if ((filter == null) || (filter.length() == 0)) {
      changed = rowData.unfilter();
    } else {
      rowData.setConverter(getConverter());
      changed = rowData.filter(filter, contains, nullPasses, emptyPasses);
    }

    if (changed) {
      tableDataChanged();
    }

    return changed;
  }

  @Override
  public int find(iFilter filter, int start) {
    rowData.setConverter(getConverter());

    return rowData.find(filter, start);
  }

  @Override
  public int find(String filter, int start, boolean contains) {
    rowData.setConverter(getConverter());

    return rowData.find(filter, start, contains);
  }

  @Override
  public int indexForRow(RenderableDataItem item) {
    return rowData.indexOf(item);
  }

  @Override
  public int indexOf(Object o) {
    return rowData.indexOf(o);
  }

  public void insertRow(int row, RenderableDataItem item) {
    rowData.add(row, item);
    rowsInserted(row, row);
  }

  @Override
  public Iterator<RenderableDataItem> iterator() {
    return rowData.iterator();
  }

  public String join() {
    return join(null);
  }

  @Override
  public String join(String sep) {
    return rowData.join(sep);
  }

  @Override
  public int lastIndexOf(Object o) {
    return rowData.lastIndexOf(o);
  }

  @Override
  public ListIterator<RenderableDataItem> listIterator() {
    return rowData.listIterator();
  }

  @Override
  public ListIterator<RenderableDataItem> listIterator(int i) {
    return rowData.listIterator(i);
  }

  @Override
  public void move(int from, int to) {
    rowData.move(from, to);
    tableDataChanged();
  }

  @Override
  public void moveRow(int from, int to) {
    if (from != to) {
      RenderableDataItem di = rowData.get(from);

      if (di != null) {
        rowData.remove(from);
        this.rowsDeleted(from, from);

        if (from < to) {
          to--;    // because we deleted a from
        }

        rowData.add(to, di);
        this.rowsInserted(to, to);
      }
    }
  }

  @Override
  public RenderableDataItem pop() {
    RenderableDataItem item = rowData.pop();

    updateForInsertionOrDeletion();

    return item;
  }

  @Override
  public void push(RenderableDataItem... e) {
    if (e != null) {
      for (RenderableDataItem v : e) {
        addRow(v);
      }
    }
  }

  @Override
  public boolean refilter() {
    if (rowData.refilter()) {
      tableDataChanged();

      return true;
    }

    return false;
  }

  @Override
  public void refreshItems() {
    tableDataChanged();
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem di = rowData.remove(index);

    if (di != null) {
      this.rowsDeleted(index, index, Arrays.asList(di));
    }

    return di;
  }

  @Override
  public boolean remove(Object o) {
    int n = indexOf(o);

    if (n != -1) {
      removeRow(n);

      return true;
    }

    return false;
  }

  @Override
  public boolean removeAll(Collection<?> clctn) {
    final int size = rowData.size();

    rowData.removeAll(clctn);

    if (size != rowData.size()) {
      tableDataChanged();

      return true;
    }

    return false;
  }

  @Override
  public void removeDataModelListener(iDataModelListener l) {
    if (listenerList != null) {
      listenerList.remove(iDataModelListener.class, l);
    }
  }

  @Override
  public void removeRow(int row) {
    RenderableDataItem di = rowData.remove(row);

    if (di != null) {
      this.rowsDeleted(row, row, Arrays.asList(di));
    }
  }

  @Override
  public void removeRows(int[] indexes) {
    final int size = rowData.size();

    rowData.removeRows(indexes);

    if (size != rowData.size()) {
      tableDataChanged();
    }
  }

  @Override
  public void removeRows(int firstRow, int lastRow) {
    if (lastRow == -1) {
      lastRow = rowCount - 1;
    }

    int len = lastRow - firstRow + 1;

    if (len < 1) {
      throw new IllegalArgumentException("firstRow=" + firstRow + ", lastRow=" + lastRow);
    }

    if (len == 1) {
      removeRow(firstRow);

      return;
    }

    List list = new ArrayList(len);

    for (int i = firstRow; i <= lastRow; i++) {
      list.add(rowData.remove(i));
    }

    rowsDeleted(firstRow, lastRow, list);
  }

  @Override
  public void resetModel(List<Column> columns, iFilterableList<RenderableDataItem> rows) {
    columnData        = columns;
    rowData           = rows;
    hasModifiedValues = false;
    tableChanged();
  }

  @Override
  public void resetModified() {
    hasModifiedValues = false;

    int                len = (rowData == null)
                             ? 0
                             : rowData.size();
    RenderableDataItem row, item;

    for (int i = 0; i < len; i++) {
      row = rowData.get(i);
      row.setModified(false);

      for (int n = 0; n < columnCount; n++) {
        item = row.getItemEx(n);

        if (item != null) {
          item.setModified(false);
        }
      }
    }
  }

  @Override
  public void resetRows(iFilterableList<RenderableDataItem> rows) {
    clearRows();
    rowData           = rows;
    hasModifiedValues = false;
    tableDataChanged();
  }

  @Override
  public boolean retainAll(Collection<?> clctn) {
    if (rowData.retainAll(clctn)) {
      tableDataChanged();

      return true;
    }

    return false;
  }

  @Override
  public List<RenderableDataItem> reverse() {
    rowData.reverse();
    tableDataChanged();

    return rowData;
  }

  @Override
  public void rowChanged(int row) {
    this.fireTableRowsUpdated(row, row);
  }

  @Override
  public void rowChanged(RenderableDataItem item) {
    int n = indexForRow(item);

    if (n != -1) {
      rowChanged(n);
    }
  }

  @Override
  public void rowsChanged(int... index) {
    for (int i : index) {
      rowChanged(i);
    }
  }

  @Override
  public void rowsChanged(int firstRow, int lastRow) {
    this.fireTableRowsUpdated(firstRow, lastRow);
  }

  public void rowsDeleted(int firstRow, int lastRow) {
    rowCount = rowData.size();
    this.fireTableRowsDeleted(firstRow, lastRow);
  }

  @Override
  public void rowsDeleted(int firstRow, int lastRow, List<RenderableDataItem> removed) {
    rowCount = rowData.size();
    this.fireTableRowsDeleted(firstRow, lastRow, removed);
  }

  @Override
  public void rowsInserted(int firstRow, int lastRow) {
    rowCount = rowData.size();
    this.fireTableRowsInserted(firstRow, lastRow);
  }

  @Override
  public RenderableDataItem shift() {
    RenderableDataItem item = rowData.shift();

    updateForInsertionOrDeletion();

    return item;
  }

  @Override
  public int size() {
    return (rowData == null)
           ? 0
           : rowData.size();
  }

  @Override
  public List<RenderableDataItem> slice(int start) {
    return rowData.slice(start, getRowCount());
  }

  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return rowData.slice(start, end);
  }

  @Override
  public void sort(Comparator comparator) {
    rowData.sort(comparator);
    tableDataChanged();
  }

  @Override
  public void sort(int col, boolean desc, boolean useLinkedData) {
    if ((rowData != null) &&!rowData.isEmpty()) {
      if (itemComparator == null) {
        itemComparator = new SubItemComparator(this);
      }

      itemComparator.setOptions(col, desc);
      itemComparator.setUseLinkedData(useLinkedData);
      sortEx(itemComparator);
      tableDataChanged();
    }
  }

  public void sortEx(Comparator comparator) {
    rowData.sort(comparator);
  }

  public void sortEx(int col, boolean desc, boolean useLinkedData) {
    if ((rowData != null) &&!rowData.isEmpty()) {
      if (itemComparator == null) {
        itemComparator = new SubItemComparator(this);
      }

      itemComparator.setOptions(col, desc);
      itemComparator.setUseLinkedData(useLinkedData);
      sortEx(itemComparator);
    }
  }

  @Override
  public List<RenderableDataItem> sortEx(int col, boolean desc, boolean force, boolean useLinkedData) {
    if ((rowData != null) &&!rowData.isEmpty()) {
      if (itemComparator == null) {
        itemComparator = new SubItemComparator(this);
      }

      if (force || (itemComparator.getSortColumn() != col) || (itemComparator.isSortDescending() != desc)
          || (itemComparator.isUseLinkedData() != useLinkedData)) {
        itemComparator.setOptions(col, desc);
        itemComparator.setUseLinkedData(useLinkedData);
        sortEx(itemComparator);
      }
    }

    return rowData;
  }

  @Override
  public List<RenderableDataItem> splice(int index, int howMany) {
    return splice(index, howMany, (RenderableDataItem[]) null);
  }

  @Override
  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return spliceList(index, howMany, (e == null)
                                      ? null
                                      : Arrays.asList(e));
  }

  @Override
  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    rowData.spliceList(index, howMany, e);
    tableDataChanged();

    return rowData;
  }

  @Override
  public List<RenderableDataItem> subList(int i, int i1) {
    return rowData.subList(i, i1);
  }

  @Override
  public void swap(int index1, int index2) {
    rowData.swap(index1, index2);
    rowsChanged(index1, index1);
  }

  @Override
  public void tableChanged() {
    columnCount       = columnData.size();
    rowCount          = rowData.size();
    hasModifiedValues = false;
    fireTableStructureChanged();
  }

  @Override
  public void tableDataChanged() {
    rowCount = rowData.size();
    fireTableDataChanged();
  }

  @Override
  public void tableItemsModified() {
    hasModifiedValues = true;
  }

  @Override
  public Object[] toArray() {
    return rowData.toArray();
  }

  @Override
  public void trimToSize() {
    rowData.trimToSize();
  }

  @Override
  public <T> T[] toArray(T[] ts) {
    return rowData.toArray(ts);
  }

  @Override
  public boolean unfilter() {
    if (rowData.unfilter()) {
      tableDataChanged();

      return true;
    }

    return false;
  }

  @Override
  public void unshift(RenderableDataItem value) {
    rowData.unshift(value);
    updateForInsertionOrDeletion();
  }

  @Override
  public void updateForInsertionOrDeletion() {
    int start = rowCount;

    rowCount = rowData.size();

    int end = rowCount;

    if (end > start) {
      this.rowsInserted(start, end - 1);
    } else if (start > end) {
      this.rowsDeleted(end, start - 1);
    }
  }

  @Override
  public RenderableDataItem set(int row, RenderableDataItem item) {
    RenderableDataItem di = rowData.set(row, item);

    if (di != item) {
      this.rowChanged(row);
    }

    return di;
  }

  @Override
  public void setActiveColumn(int col) {
    activeColumn = col;
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    clearRows();
    rowData = new FilterableList<RenderableDataItem>(collection);
    tableDataChanged();

    return true;
  }

  @Override
  public void setAllowSorting(boolean sortingAllowed) {
    this.sortingAllowed = sortingAllowed;
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    rowData.setConverter(converter);
  }

  @Override
  public void setEventsEnabled(boolean enabled) {
    eventsEnabled = enabled;
  }

  @Override
  public void setExpandableColumn(int col) {
    this.expandableColumn = col;
  }

  @Override
  public void setUseIndexForFiltering(boolean booleanValue) {}

  @Override
  public CharacterIndex getFilteringIndex() {
    return null;
  }

  @Override
  public void setItems(Collection<? extends RenderableDataItem> collection) {
    setAll(collection);
  }

  @Override
  public void setRow(int row, RenderableDataItem item) {
    rowData.set(row, item);
    this.rowChanged(row);
  }

  /**
   * @param selectionType the selectionType to set
   */
  public void setSelectionType(SelectionType selectionType) {
    this.selectionType = selectionType;
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    RenderableDataItem di       = rowData.get(row);
    RenderableDataItem item     = di.getItemEx(col);
    Object             oldValue = null;

    if (item == null) {
      di.setItemCount(getColumnCount());
      item = new RenderableDataItem();
      di.set(col, item);
    } else {
      oldValue = item.getValue(getWidget());
    }

    if (value instanceof RenderableDataItem) {
      item.copy((RenderableDataItem) value);
    } else {
      item.setValue(value);
    }

    this.fireTableRowsUpdated(row, row);
    value = item.getValue(getWidget());

    if (("".equals(value)) && (oldValue == null)) {
      return;
    }

    if ((value != oldValue) && (value != null) &&!value.equals(oldValue)) {
      item.setModified(true);
      di.setModified(true);
      hasModifiedValues = true;
    }
  }

  @Override
  public void setWidget(iWidget w) {
    theWidget = w;
  }

  @Override
  public RenderableDataItem get(int index) {
    return rowData.get(index);
  }

  @Override
  public Column getColumn(int col) {
    return columnData.get(col);
  }

  public Class<?> getColumnClass(int col) {
    return RenderableDataItem.class;
  }

  @Override
  public int getColumnCount() {
    return columnCount;
  }

  @Override
  public Column getColumnDescription() {
    return null;
  }

  @Override
  public List<Column> getColumns() {
    return columnData;
  }

  @Override
  public Comparator getComparator() {
    return itemComparator;
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    iStringConverter<RenderableDataItem> cvt = rowData.getConverter();

    if (cvt == null) {
      if (columnConverter == null) {
        columnConverter = new ItemStringConverter(activeColumn) {
          @Override
          public String toString(RenderableDataItem item) {
            if ((item != null) &&!item.isConverted()) {
              convert(item, activeColumn);
            }

            return super.toString(item);
          }
        };
      } else {
        columnConverter.setColumn(activeColumn);
      }

      cvt = columnConverter;
    } else if (cvt == columnConverter) {
      columnConverter.setColumn(activeColumn);
    }

    return cvt;
  }

  @Override
  public int getExpandableColumn() {
    return expandableColumn;
  }

  @Override
  public iFilterableList<RenderableDataItem> getFilterableList() {
    if (filterableList == null) {
      filterableList = new TableModelFilterableList(this);
    }

    return filterableList;
  }

  @Override
  public List<RenderableDataItem> getFilteredList() {
    return rowData.getFilteredList();
  }

  @Override
  public RenderableDataItem getItemAt(int row, int col) {
    RenderableDataItem item = getItemAtEx(row, col);

    if (item == null) {
      RenderableDataItem di = getRow(row);

      di.setItemCount(columnCount);
      item = new RenderableDataItem(null, getColumn(col).getType(), null);
      item.setSelectable(false);
      di.set(col, item);
    }

    return item;
  }

  @Override
  public Column getItemDescription(int row, int col) {
    return columnData.get(col);
  }

  @Override
  public int getOperatingColumn() {
    return activeColumn;
  }

  public iFilterableList<RenderableDataItem> getOriginalList() {
    return rowData;
  }

  @Override
  public RenderableDataItem getRow(int row) {
    return rowData.get(row);
  }

  @Override
  public int getRowCount() {
    return rowCount;
  }

  @Override
  public iFilterableList<RenderableDataItem> getRowsEx() {
    return rowData;
  }

  /**
   * @return the selectionType
   */
  public SelectionType getSelectionType() {
    return selectionType;
  }

  @Override
  public int getSortColumn() {
    return itemComparator.getSortColumn();
  }

  @Override
  public CharSequence getTooltip(int row, int col) {
    RenderableDataItem item = getItemAtEx(row, col);

    return (item == null)
           ? null
           : item.getTooltip();
  }

  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return rowData.getUnfilteredList();
  }

  @Override
  public Object getValueAt(int row, int col) {
    RenderableDataItem di = getItemEx(row, col);

    if (di == null) {
      return null;
    }

    return di.getValue(getWidget());
  }

  @Override
  public iWidget getWidget() {
    return theWidget;
  }

  @Override
  public boolean isAllowSorting() {
    return sortingAllowed;
  }

  public boolean isCellEditable(int row, int col) {
    if (!columnData.get(col).isEditable()) {
      return false;
    }

    RenderableDataItem item = getItemEx(row, col);

    if ((item != null) && item.isEditableSet() &&!item.isEditable()) {
      return false;
    }

    return true;
  }

  @Override
  public boolean isEmpty() {
    return rowCount == 0;
  }

  @Override
  public boolean isEventsEnabled() {
    return eventsEnabled;
  }

  @Override
  public boolean isFiltered() {
    return rowData.isFiltered();
  }

  @Override
  public boolean isModified() {
    return hasModifiedValues;
  }

  @Override
  public boolean isSortDescending() {
    return (itemComparator == null)
           ? true
           : itemComparator.isSortDescending();
  }

  protected void convert(RenderableDataItem di, int col) {
    Column         c   = columnData.get(col);
    iDataConverter cvt = c.getDataConverter();

    if (cvt != null) {
      di.convert(getWidget().getViewer(), c.getType(), cvt, c.getValueContext());
    }
  }

  protected void fireTableDataChanged() {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).contentsChanged(this);
      }
    }
  }

  protected void fireTableRowsDeleted(int index0, int index1) {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).intervalRemoved(this, index0, index1, null);
      }
    }
  }

  protected void fireTableRowsDeleted(int index0, int index1, List<RenderableDataItem> removed) {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).intervalRemoved(this, index0, index1, removed);
      }
    }
  }

  protected void fireTableRowsInserted(int index0, int index1) {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).intervalAdded(this, index0, index1);
      }
    }
  }

  protected void fireTableRowsUpdated(int index0, int index1) {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).contentsChanged(this, index0, index1);
      }
    }
  }

  protected void fireTableStructureChanged() {
    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).structureChanged(this);
      }
    }
  }

  protected RenderableDataItem getItemAtEx(int row, int col) {
    RenderableDataItem di = rowData.get(row).getItemEx(col);

    if (di == null) {
      return null;
    }

    if (!di.isConverted()) {
      convert(di, col);
    }

    return di;
  }

  protected RenderableDataItem getItemEx(int row, int col) {
    if ((row < 0) || (row >= rowCount)) {
      return null;
    }

    return rowData.get(row).getItemEx(col);
  }

  private void clearRows() {
    if (rowData != null) {
      rowData.clear();
    }

    rowCount = 0;

    if (filterableList != null) {
      filterableList.clear();
    }
  }
}
