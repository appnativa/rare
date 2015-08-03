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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.IntList;
import com.appnativa.util.iStringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class aDataItemListModel extends FilterableList<RenderableDataItem>
        implements iStringConverter, iPlatformListDataModel {
  protected boolean           eventsEnabled = true;
  protected Column            columnDescription;
  protected boolean           editing;
  protected IntList           editingMarks;
  protected EventListenerList listenerList;
  protected iWidget           theWidget;

  /** Creates a new instance of DataItemListModel */
  public aDataItemListModel() {
    super();
  }

  /**
   * Creates a new instance of DataItemComboBoxModel that is a copy of this one
   *
   * @param m
   *          the model
   */
  public aDataItemListModel(aDataItemListModel m) {
    this();
    this.addAll(m);
    this.theWidget         = m.theWidget;
    this.columnDescription = m.columnDescription;
  }

  /**
   * Creates a new instance of DataItemListModel
   *
   * @param widget
   *          {@inheritDoc}
   * @param column
   *          {@inheritDoc}
   */
  public aDataItemListModel(iWidget widget, Column column) {
    this();
    this.columnDescription = column;
    this.theWidget         = widget;
  }

  @Override
  public boolean add(RenderableDataItem e) {
    int len = size();

    if (super.add(e)) {
      this.fireIntervalAdded(this, len, len);
    }

    return true;
  }

  @Override
  public void add(int index, RenderableDataItem e) {
    int len = size();

    if ((index < 0) || (index > len)) {
      index = len;
    }

    super.add(index, e);
    this.fireIntervalAdded(this, index, index);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    boolean oenabled = eventsEnabled;

    eventsEnabled = false;

    int len1 = size();

    try {
      super.addAll(c);
    } finally {
      eventsEnabled = oenabled;
    }

    int len2 = size();

    if (len2 > len1) {
      this.fireIntervalAdded(this, len1, len2 - 1);
    }

    return c.size() > 0;
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    boolean oenabled = eventsEnabled;

    eventsEnabled = false;

    int len1 = size();

    try {
      super.addAll(index, c);
    } finally {
      eventsEnabled = oenabled;
    }

    int len2 = size();

    if (len1 != len2) {
      this.fireContentsChanged(this, index, index + (len2 - len1) - 1);
    }

    return len1 != len2;
  }

  public void addAllEx(Collection<? extends RenderableDataItem> c) {
    boolean oenabled = eventsEnabled;

    eventsEnabled = false;

    try {
      super.addAll(c);
    } finally {
      eventsEnabled = oenabled;
    }
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
    int len = size();

    super.addIndexToFilteredList(index);

    int len2 = size();

    if (index == -1) {
      if (len > 0) {
        fireIntervalRemoved(this, 0, len - 1, getUnfilteredList());
      }
    } else if (len != len2) {
      fireIntervalRemoved(this, index, index, Arrays.asList(get(index)));
    }
  }

  @Override
  public void addToFilteredList(RenderableDataItem e) {
    int len = size();

    super.addToFilteredList(e);
    this.fireIntervalAdded(this, len, len);
  }

  @Override
  public void addToFilteredList(int index, RenderableDataItem e) {
    int len = size();

    if ((index < 0) || (index > len)) {
      index = len;
    }

    super.addToFilteredList(index, e);
    this.fireIntervalAdded(this, index, index);
  }

  @Override
  public void clear() {
    int index1 = size() - 1;

    clearEx();

    if (index1 >= 0) {
      fireContentsChanged(this);
    }
  }

  public void clearEx() {
    super.clear();
  }

  @Override
  public void dispose() {
    clearEx();
    theWidget = null;

    if (listenerList != null) {
      listenerList.clear();
      listenerList = null;
    }
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

  /**
   * Gets the index of the item whose linked data has the specified value An
   * identity compare (==) is used to test for equality
   *
   * @param value
   *          the value to look for
   *
   * @return the index of the specified value of -1 if the value was not found
   */
  public int indexOfLinkedData(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getLinkedData();

      if (o == value) {
        return i;
      }
    }

    return -1;
  }

  public int indexOfLinkedDataEquals(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getLinkedData();

      if ((o != null) && ((o == value) || o.equals(value))) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Gets the index of the item with the specified value An identity compare
   * (==) is used to test for equality
   *
   * @param value
   *          the value to look for
   *
   * @return the index of the specified value of -1 if the value was not found
   */
  public int indexOfValue(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getValue();

      if (o == value) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Gets the index of the item with the specified value The equals() method is
   * used to test for equality
   *
   * @param value
   *          the value to look for
   *
   * @return the index of the specified value of -1 if the value was not found
   */
  public int indexOfValueEquals(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getValue();

      if ((o != null) && ((o == value) || o.equals(value))) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public void refreshItems() {
    fireContentsChanged(this);
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem di = super.remove(index);

    if (di != null) {
      this.fireIntervalRemoved(this, index, index, Arrays.asList(di));
    }

    return di;
  }

  @Override
  public boolean remove(Object o) {
    int n = indexOf(o);

    if (n == -1) {
      return false;
    }

    remove(n);

    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean modified = super.removeAll(c);

    if (modified) {
      this.fireContentsChanged(this);
    }

    return modified;
  }

  @Override
  public List<RenderableDataItem> reverse() {
    List<RenderableDataItem> list = super.reverse();

    if (size() > 0) {
      this.fireContentsChanged(this);
    }

    return list;
  }

  @Override
  public void removeDataModelListener(iDataModelListener l) {
    if (listenerList != null) {
      listenerList.remove(iDataModelListener.class, l);
    }
  }

  @Override
  public void removeRows(int[] indexes) {
    super.removeRows(indexes);

    if ((indexes != null) && (indexes.length > 0)) {
      this.fireContentsChanged(this, 0, size());
    }
  }

  @Override
  public void removeRows(int firstRow, int lastRow) {
    if (lastRow == -1) {
      lastRow = size() - 1;
    }

    int len = lastRow - firstRow + 1;

    if (len < 1) {
      throw new IllegalArgumentException("firstRow=" + firstRow + ", lastRow=" + lastRow);
    }

    if (len == 1) {
      remove(firstRow);

      return;
    }

    List list = new ArrayList(len);

    for (int i = firstRow; i <= lastRow; i++) {
      list.add(super.remove(i));
    }

    rowsDeleted(firstRow, lastRow, list);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    boolean modified = super.retainAll(c);

    if (modified) {
      this.fireContentsChanged(this);
    }

    return modified;
  }

  @Override
  public void rowChanged(int index) {
    if ((index > -1) && (index < size())) {
      fireContentsChanged(this, index, index);
    }
  }

  @Override
  public void rowChanged(RenderableDataItem item) {
    rowChanged(indexOf(item));
  }

  @Override
  public void rowsChanged(int... index) {
    for (int i : index) {
      rowChanged(i);
    }
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
    fireContentsChanged(this, firstRow, lastRow);
  }

  @Override
  public void rowsDeleted(int firstRow, int lastRow, List<RenderableDataItem> removed) {
    fireIntervalRemoved(this, firstRow, lastRow, removed);
  }

  @Override
  public void rowsInserted(int firstRow, int lastRow) {
    fireIntervalAdded(this, firstRow, lastRow);
  }

  @Override
  public void sort(Comparator comparator) {
    super.sort(comparator);
    fireContentsChanged(this);
  }

  public void sortEx(Comparator comparator) {
    super.sort(comparator);
  }

  public void structureChanged() {
    this.fireContentsChanged(this);
  }

  @Override
  public void swap(int index1, int index2) {
    super.swap(index1, index2);

    if (index2 == (index1 + 1)) {
      fireContentsChanged(this, index1, index2);
    } else if (index1 == (index2 + 1)) {
      fireContentsChanged(this, index2, index1);
    } else {
      fireContentsChanged(this, index1, index1);
      fireContentsChanged(this, index2, index2);
    }
  }

  @Override
  public String toString(Object item) {
    RenderableDataItem di = (RenderableDataItem) item;

    if ((columnDescription != null) &&!di.isConverted()) {
      columnDescription.convert(theWidget, di);
    }

    return di.toString();
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem e) {
    RenderableDataItem di = super.set(index, e);

    if (di != e) {
      this.fireContentsChanged(this, index, index);
    }

    return di;
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    super.clear();

    return addAll(collection);
  }

  @Override
  public void setColumnDescription(Column column) {
    this.columnDescription = column;
  }

  @Override
  public void setEditing(boolean editing) {
    if (this.editing != editing) {
      this.editing = editing;

      if (editing) {
        editingMarks = new IntList();
      }
    }
  }

  @Override
  public void setEventsEnabled(boolean enabled) {
    this.eventsEnabled = enabled;
  }

  @Override
  public void setItems(Collection<? extends RenderableDataItem> collection) {
    setAll(collection);
  }

  @Override
  public void setWidget(iWidget widget) {
    this.theWidget = widget;
  }

  @Override
  public RenderableDataItem get(int index) {
    RenderableDataItem di = super.get(index);

    if ((columnDescription != null) &&!di.isConverted()) {
      columnDescription.convert(theWidget, di);
    }

    return di;
  }

  @Override
  public Column getColumnDescription() {
    return columnDescription;
  }

  public Object getElementAt(int index) {
    return get(index);
  }

  public RenderableDataItem getRow(int index) {
    return get(index);
  }

  public iWidget getWidget() {
    return theWidget;
  }

  public boolean isEditing() {
    return editing;
  }

  @Override
  public boolean isEventsEnabled() {
    return eventsEnabled;
  }

  /**
   * Subclasses must call this method
   * <b>after</b> all the contents in the list has changed
   *
   * @param source
   *          the <code>ListModel</code> that changed, typically "this"
   */
  protected void fireContentsChanged(Object source) {
    editModeClearMarks();

    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).contentsChanged(source);
      }
    }
  }

  /**
   * Subclasses must call this method
   * <b>after</b> one or more elements of the list change. The changed elements
   * are specified by the closed interval index0, index1 -- the endpoints are
   * included. Note that index0 need not be less than or equal to index1.
   *
   * @param source
   *          the <code>ListModel</code> that changed, typically "this"
   * @param index0
   *          one end of the new interval
   * @param index1
   *          the other end of the new interval
   * @see EventListenerList
   */
  protected void fireContentsChanged(Object source, int index0, int index1) {
    editModeClearMarks();

    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).contentsChanged(source, index0, index1);
      }
    }
  }

  /**
   * Subclasses must call this method
   * <b>after</b> one or more elements are added to the model. The new elements
   * are specified by a closed interval index0, index1 -- the endpoints are
   * included. Note that index0 need not be less than or equal to index1.
   *
   * @param source
   *          the <code>ListModel</code> that changed, typically "this"
   * @param index0
   *          one end of the new interval
   * @param index1
   *          the other end of the new interval
   * @see EventListenerList
   */
  protected void fireIntervalAdded(Object source, int index0, int index1) {
    editModeClearMarks();

    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).intervalAdded(source, index0, index1);
      }
    }
  }

  /**
   * Subclasses must call this method
   * <b>after</b> one or more elements are removed from the model.
   * <code>index0</code> and <code>index1</code> are the end points of the
   * interval that's been removed. Note that <code>index0</code> need not be
   * less than or equal to <code>index1</code>.
   *
   * @param source
   *          the <code>ListModel</code> that changed, typically "this"
   * @param index0
   *          one end of the removed interval, including <code>index0</code>
   * @param index1
   *          the other end of the removed interval, including
   *          <code>index1</code>
   * @see EventListenerList
   */
  protected void fireIntervalRemoved(Object source, int index0, int index1, List<RenderableDataItem> removed) {
    editModeClearMarks();

    if (!eventsEnabled || (listenerList == null)) {
      return;
    }

    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iDataModelListener.class) {
        ((iDataModelListener) listeners[i + 1]).intervalRemoved(source, index0, index1, removed);
      }
    }
  }
}
