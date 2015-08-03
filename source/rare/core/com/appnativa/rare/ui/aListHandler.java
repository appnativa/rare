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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.util.DataItemCollection;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.util.Helper;
import com.appnativa.util.IntList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class aListHandler extends AbstractList<RenderableDataItem>
        implements iPlatformListHandler, iActionListener, iItemChangeListener, iScrollerSupport {
  protected boolean                deselectEventsDisabled = true;
  protected boolean                changeEventsEnabled    = true;
  protected boolean                changeSelColorOnLostFocus;
  protected UILabelRenderer        computeSizeRenderer;
  protected boolean                handleFirstFocusSelection;
  protected iPlatformListDataModel listModel;
  protected iListView              listView;
  protected EventListenerList      listenerList;
  protected int                    minRowHeight;
  protected IntList                tmpList;
  protected int                    visibleRowCount;
  private iPlatformComponent       calcComponent;
  private iPlatformItemRenderer    calcRenderer;
  protected int                    minVisibleRowCount;

  public aListHandler(iListView view) {
    this.listView = view;
  }

  @Override
  public boolean isAtLeftEdge() {
    return listView.getScrollerSupport().isAtLeftEdge();
  }

  @Override
  public boolean isAtRightEdge() {
    return listView.getScrollerSupport().isAtRightEdge();
  }

  @Override
  public boolean isAtTopEdge() {
    return listView.getScrollerSupport().isAtTopEdge();
  }

  @Override
  public boolean isAtBottomEdge() {
    return listView.getScrollerSupport().isAtBottomEdge();
  }

  @Override
  public void scrollToBottomEdge() {
    listView.getScrollerSupport().scrollToBottomEdge();
  }

  @Override
  public void scrollToLeftEdge() {
    listView.getScrollerSupport().scrollToLeftEdge();
  }

  @Override
  public void scrollToRightEdge() {
    listView.getScrollerSupport().scrollToRightEdge();
  }

  @Override
  public void scrollToTopEdge() {
    listView.getScrollerSupport().scrollToTopEdge();
  }

  @Override
  public void moveUpDown(boolean up, boolean block) {
    listView.getScrollerSupport().moveUpDown(up, block);
  }

  @Override
  public void moveLeftRight(boolean left, boolean block) {
    listView.getScrollerSupport().moveLeftRight(left, block);
  }

  @Override
  public boolean isScrolling() {
    return listView.getScrollerSupport().isScrolling();
  }

  @Override
  public UIPoint getContentOffset() {
    return listView.getScrollerSupport().getContentOffset();
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {
    listView.getScrollerSupport().setScrollingEdgePainter(painter);
  }

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return listView.getScrollerSupport().getScrollingEdgePainter();
  }

  public boolean isAutoHilight() {
    return listView.isAutoHilight();
  }

  public aListHandler(iListView view, iPlatformListDataModel model) {
    this.listView  = view;
    this.listModel = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    fireAction(e);
  }

  @Override
  public boolean add(RenderableDataItem e) {
    return listModel.add(e);
  }

  @Override
  public boolean isEditing() {
    return listView.isEditing();
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    listModel.setFilteredList(list, lastFilter);
  }

  @Override
  public void removeDataModelListener(iDataModelListener l) {
    listModel.removeDataModelListener(l);
  }

  @Override
  public void addDataModelListener(iDataModelListener l) {
    listModel.addDataModelListener(l);
  }

  @Override
  public void add(int index, RenderableDataItem element) {
    listModel.add(index, element);
  }

  @Override
  public void addActionListener(iActionListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listView.setActionListener(this);
    listenerList.add(iActionListener.class, l);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> collection) {
    return listModel.addAll(collection);
  }

  @Override
  public boolean addAll(int location, Collection<? extends RenderableDataItem> collection) {
    return listModel.addAll(location, collection);
  }

  @Override
  public void addAll(int index, List<RenderableDataItem> items, boolean insertMode) {
    addAll(index, items);
  }

  @Override
  public void addIndexToFilteredList(int index) {
    listModel.addIndexToFilteredList(index);
  }

  @Override
  public void addSelectionChangeListener(iItemChangeListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    listView.setSelectionChangeListener(this);
    listenerList.add(iItemChangeListener.class, l);
  }

  @Override
  public void addSelectionIndex(int index) {
    listView.addSelectionIndex(index);
    selectionsChanges(null, index);
  }

  @Override
  public void addToFilteredList(RenderableDataItem item) {
    listModel.addToFilteredList(item);
  }

  @Override
  public void addToFilteredList(int index, RenderableDataItem item) {
    listModel.addToFilteredList(index, item);
  }

  @Override
  public void clear() {
    clearSelection();
    listModel.clear();
  }

  @Override
  public void clearPopupMenuIndex() {
    listView.clearPopupMenuIndex();
  }

  @Override
  public void scrollRowToBottom(int row) {
    listView.scrollRowToBottom(row);
  }

  @Override
  public void scrollRowToTop(int row) {
    listView.scrollRowToTop(row);
  }

  @Override
  public void scrollRowToVisible(int row) {
    listView.scrollRowToVisible(row);
  }

  @Override
  public void clearSelection() {
    setSelectedIndex(-1);
  }

  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return listModel.concat(e);
  }

  @Override
  public void copySelectedItems(int index, boolean insertMode, boolean delete) {
    ListHelper.copySelectedItems(this, index, insertMode, delete);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object deleteSelectedData(boolean returnData) {
    return ListHelper.deleteItems(this, this.getSelectedIndexes(), returnData);
  }

  /**
   * Deletes the currently selected data
   *
   * @param listComponent
   *          the component to delete from
   * @param returnData
   *          whether the data representing the currently selection should be
   *          returned
   * @return the currently selected data or null
   */
  public static Object deleteSelectedDataEx(iListHandler listComponent, boolean returnData) {
    int[] sels = listComponent.getSelectedIndexes();

    if (sels == null) {
      return null;
    }

    int                  len = sels.length;
    RenderableDataItem[] a   = returnData
                               ? new RenderableDataItem[len]
                               : null;

    if (returnData) {
      for (int i = 0; i < len; i++) {
        a[i] = listComponent.get(sels[i]);
      }
    }

    for (int i = len - 1; i >= 0; i--) {
      listComponent.remove(sels[i]);
    }

    return a;
  }

  @Override
  public void dispose() {
    if (listenerList != null) {
      listenerList.clear();
    }

    if (computeSizeRenderer != null) {
      computeSizeRenderer.dispose();
    }

    calcRenderer        = null;
    calcComponent       = null;
    computeSizeRenderer = null;
    listenerList        = null;
    listModel           = null;
    listView            = null;
  }

  @Override
  public void ensureCapacity(int capacity) {
    listModel.ensureCapacity(capacity);
  }

  @Override
  public boolean filter(iFilter filter) {
    return listModel.filter(filter);
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    return listModel.filter(filter, contains);
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return listModel.filter(filter, contains, nullPasses, emptyPasses);
  }

  @Override
  public int find(iFilter filter, int start) {
    return listModel.find(filter, start);
  }

  @Override
  public int find(String filter, int start, boolean contains) {
    return listModel.find(filter, start, contains);
  }

  public void fireAction(ActionEvent e) {
    if ((listenerList != null) && (getListComponent() != null)) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

  @Override
  public void fireActionForSelected() {
    if (this.hasSelection()) {
      ActionEvent e = new ActionEvent(getListComponent(), "fireActionForSelected");

      Utils.fireActionEvent(listenerList, e);
    }
  }

  /**
   * Process a focus event and returns whether the component should become the
   * focus owner
   *
   * @param listComponent
   *          the component
   * @param e
   *          the focus event
   * @param focusOwner
   *          true if the component is the current focus owner ; false otherwise
   * @return true if the component is the current focus owner; false otherwise
   */
  public static boolean focusEvent(iListHandler listComponent, FocusEvent e, boolean focusOwner) {
    if (e.getID() == FocusEvent.FOCUS_GAINED) {
      boolean hs = listComponent.hasSelection();

      if (!focusOwner && hs) {
        listComponent.getListComponent().repaint();
      } else if (listComponent.isHandleFirstFocusSelection() &&!hs && (listComponent.size() > 0)) {
        listComponent.setSelectedIndex(0);
      }

      focusOwner = true;
    } else {
      iPlatformComponent fc = Platform.getAppContext().getPermanentFocusOwner();

      if (fc == null) {
        focusOwner = false;
      }

      if (!e.isTemporary() || (e.getOppositeComponent() == null)) {
        focusOwner = false;
      }

      if (!focusOwner && listComponent.hasSelection()) {
        listComponent.getListComponent().repaint();
      }
    }

    return focusOwner;
  }

  public int indexOf(RenderableDataItem item) {
    return listModel.indexOf(item);
  }

  @Override
  public void itemChanged(ItemChangeEvent e) {
    if ((listenerList == null) ||!isChangeEventsEnabled()) {
      return;
    }

    Object ov = resolveChangeValue(e.getOldValue());
    Object nv = resolveChangeValue(e.getNewValue());

    selectionsChanges(ov, nv);
  }

  @Override
  public String join(String sep) {
    return listModel.join(sep);
  }

  @Override
  public void move(int from, int to) {
    listModel.move(from, to);
  }

  /**
   * Moves a row from one location to another
   *
   * @param from
   *          the existing location of the row
   * @param to
   *          the new location to move the row to
   */
  public void moveRow(int from, int to) {
    listModel.move(from, to);
  }

  @Override
  public RenderableDataItem pop() {
    return ((listModel == null) || (listModel.size() == 0))
           ? null
           : listModel.remove(listModel.size() - 1);
  }

  @Override
  public void push(RenderableDataItem... value) {
    listModel.push(value);
  }

  @Override
  public boolean refilter() {
    return listModel.refilter();
  }

  @Override
  public void refreshItems() {
    listModel.refreshItems();
  }

  public void registerActionListener(boolean single) {}

  @Override
  public RenderableDataItem remove(int index) {
    if (this.isRowSelected(index)) {
      this.removeSelection(index);
    }

    return listModel.remove(index);
  }

  @Override
  public boolean remove(Object item) {
    int n = indexOf(item);

    if (n == -1) {
      return false;
    }

    return remove(n) != null;
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    Object[] a = getSelections();

    this.clearSelection();

    boolean ok = listModel.removeAll(c);

    setSelections(this, a);

    return ok;
  }

  @Override
  public void removeSelectionChangeListener(iItemChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iItemChangeListener.class, l);
    }
  }

  @Override
  public void removeRows(int[] indexes) {
    if (indexes != null) {
      for (int index : indexes) {
        if (this.isRowSelected(index)) {
          this.removeSelection(index);
        }
      }

      listModel.removeRows(indexes);
    }
  }

  @Override
  public void removeSelection(int index) {
    if (getSelectedIndex() == index) {
      setSelectedIndex(-1);
    }
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    Object[] a = getSelections();

    this.clearSelection();

    boolean ok = listModel.retainAll(c);

    setSelections(this, a);

    return ok;
  }

  @Override
  public List<RenderableDataItem> reverse() {
    listModel.reverse();

    return this;
  }

  @Override
  public RenderableDataItem shift() {
    return ((listModel == null) || (listModel.size() == 0))
           ? null
           : listModel.remove(0);
  }

  @Override
  public int size() {
    return listModel.size();
  }

  @Override
  public void sizeRowsToFit() {}

  @Override
  public List<RenderableDataItem> slice(int start) {
    return listModel.slice(start, size());
  }

  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return listModel.slice(start, end);
  }

  @Override
  public void sort(Comparator comparator) {
    listModel.sort(comparator);
  }

  @Override
  public void trimToSize() {
    listModel.trimToSize();
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
    return listModel.spliceList(index, howMany, e);
  }

  @Override
  public void swap(int index1, int index2) {
    listModel.swap(index1, index2);

    if (isRowSelected(index1)) {
      setSelectedIndex(index2);
    } else if (isRowSelected(index2)) {
      setSelectedIndex(index1);
    }
  }

  @Override
  public boolean unfilter() {
    return listModel.unfilter();
  }

  @Override
  public void unshift(RenderableDataItem value) {
    add(0, value);
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem item) {
    return listModel.set(index, item);
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return listModel.setAll(collection);
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    listView.setAutoHilight(autoHilight);
  }

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    this.changeEventsEnabled = enabled;
  }

  @Override
  public void setChangeSelColorOnLostFocus(boolean change) {
    changeSelColorOnLostFocus = change;
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    listModel.setConverter(converter);
  }

  @Override
  public void setDataEventsEnabled(boolean enabled) {
    listModel.setEventsEnabled(enabled);
  }

  @Override
  public void setDeselectEventsDisabled(boolean deselectEventsDisabled) {
    this.deselectEventsDisabled = deselectEventsDisabled;
  }

  @Override
  public void setDividerLine(UIColor color, UIStroke stroke) {
    listView.setDividerLine(color, stroke);
  }

  @Override
  public void setHandleFirstFocusSelection(boolean handle) {
    handleFirstFocusSelection = handle;
  }

  @Override
  public void setMinRowHeight(int min) {
    this.minRowHeight = min;

    if (getRowHeight() < min) {
      setRowHeight(min);
    }
  }

  @Override
  public void setRowHeight(int height) {
    if (height < minRowHeight) {
      height = minRowHeight;
    }

    setRowHeightEx(height);
  }

  @Override
  public void setSelectedIndex(int index) {
    Object ov = null;

    if (wantsDeselectedEvents()) {
      ov = getSelectionObjectsForChangeEvent();
    }

    setSelectedIndexEx(index);

    if (wantsSelectedEvents()) {
      Object nv = getSelectionObjectsForChangeEvent();

      selectionsChanges(ov, nv);
    }
  }

  @Override
  public void setSelectedIndexes(int[] indices) {
    if ((indices == null) || (indices.length == 0)) {
      clearSelection();

      return;
    }

    if (isMultipleSelectionAllowed()) {
      Object ov = wantsDeselectedEvents()
                  ? getSelectionObjectsForChangeEvent()
                  : null;

      setSelectedIndexesEx(indices);

      if (tmpList == null) {
        tmpList = new IntList(indices);
      } else {
        tmpList.A       = indices;
        tmpList._length = indices.length;
      }

      selectionsChanges(ov, tmpList);
    } else {
      setSelectedIndex(indices[0]);
    }
  }

  @Override
  public void setSelectedItem(RenderableDataItem value) {
    int n = listModel.indexOf(value);

    if (n != -1) {
      setSelectedIndex(n);
    }
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    listView.setSelectionMode(selectionMode);
  }

  public static void setSelections(iListHandler listComponent, Object[] a) {
    int len = (a == null)
              ? 0
              : a.length;

    if (len == 0) {
      return;
    }

    IntList list = new IntList(len);
    int     n;

    for (int i = 0; i < len; i++) {
      n = listComponent.indexOf(a[i]);

      if (n > -1) {
        list.add(n);
      }
    }

    if (list.size() > 0) {
      listComponent.setSelectedIndexes(list.toArray());
    }
  }

  @Override
  public void setShowDivider(boolean show) {
    listView.setShowDivider(show);
  }

  @Override
  public void setSingleClickAction(boolean singleClickAction) {
    listView.setSingleClickAction(singleClickAction);
  }

  @Override
  public void makeSelectionVisible() {
    listView.makeSelectionVisible();
  }

  @Override
  public boolean isKeepSelectionVisible() {
    return listView.isKeepSelectionVisible();
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    listView.setKeepSelectionVisible(keepSelectionVisible);
  }

  @Override
  public void setVisibleRowCount(int rows) {
    visibleRowCount = rows;
    listView.setVisibleRowCount(rows);
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    minVisibleRowCount = rows;
    listView.setMinimumVisibleRowCount(rows);
  }

  @Override
  public int getMinimumVisibleRowCount() {
    return minVisibleRowCount;
  }

  @Override
  public RenderableDataItem get(int pos) {
    return listModel.get(pos);
  }

  public RenderableDataItem getEx(int pos) {
    if ((pos < 0) || (pos >= listModel.size())) {
      return null;
    }

    return listModel.get(pos);
  }

  @Override
  public int getAnchorSelectionIndex() {
    return getMaxSelectionIndex();
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    return listModel.getConverter();
  }

  @Override
  public List<RenderableDataItem> getFilteredList() {
    return listModel.getFilteredList();
  }

  @Override
  public int getFirstVisibleIndex() {
    return listView.getFirstVisibleIndex();
  }

  @Override
  public int getItemCount() {
    return listModel.size();
  }

  public static int getFirstSelectableIndex(List<RenderableDataItem> list) {
    int len = list.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem item = list.get(i);

      if ((item != null) && item.isEnabled() && item.isSelectable()) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns an array of the values for the specified rows
   *
   * @param list
   *          the list to retrieve the items from
   * @param rows
   *          the set of rows
   * @param col
   *          the column to retrieve or -1 for all columns
   * @return an array of the values for the specified rows
   */
  public static RenderableDataItem[] getItems(List<RenderableDataItem> list, int[] rows, int col) {
    int len = (list == null)
              ? 0
              : list.size();

    if (rows != null) {
      len = rows.length;
    }

    if (len == 0) {
      return null;
    }

    RenderableDataItem[] a = new RenderableDataItem[len];
    RenderableDataItem   di;

    for (int i = 0; i < len; i++) {
      di = list.get((rows == null)
                    ? i
                    : rows[i]);

      if ((col != -1) && (di != null)) {
        di = di.getItemEx(col);
      }

      if (di != null) {
        a[i] = di;
      }
    }

    return a;
  }

  @Override
  public iFilter getLastFilter() {
    return listModel.getLastFilter();
  }

  @Override
  public int getLastVisibleIndex() {
    return listView.getLastVisibleIndex();
  }

  @Override
  public iPlatformComponent getListComponent() {
    return listView.getListComponent();
  }

  @Override
  public int getMaxSelectionIndex() {
    return getSelectedIndex();
  }

  public int getMinRowHeight() {
    return minRowHeight;
  }

  @Override
  public int getMinSelectionIndex() {
    return getSelectedIndex();
  }

  @Override
  public int getPopupMenuIndex() {
    return listView.getPopupMenuIndex();
  }

  @Override
  public RenderableDataItem getPopupMenuItem() {
    final int n = getPopupMenuIndex();

    return (n == -1)
           ? null
           : get(n);
  }

  @Override
  public int getPreferredHeight(int row) {
    calcRenderer  = getItemRenderer();
    calcComponent = getListComponent();

    return getRowHeight();
  }

  @Override
  public UIDimension getPreferredSize() {
    calcRenderer  = getItemRenderer();
    calcComponent = getListComponent();

    UIDimension size   = new UIDimension();
    float       width  = 0;
    float       height = 0;
    int         len    = listModel.size();
    int         rh     = listView.isRowSizeFixed()
                         ? getRowHeight()
                         : 0;

    for (int i = 0; i < len; i++) {
      calculateRowSize(i, size);
      width = Math.max(width, size.width);

      if (rh == 0) {
        height += size.height + 1;
      } else {
        height += rh + 1;
      }
    }

    size.height = height;
    size.width  = width;

    return size;
  }

  @Override
  public float getPreferredWidth() {
    calcRenderer  = getItemRenderer();
    calcComponent = getListComponent();

    UIDimension size  = new UIDimension();
    float       width = 0;
    int         len   = listModel.size();

    for (int i = 0; i < len; i++) {
      calculateRowSize(i, size);
      width = Math.max(width, size.width);
    }

    return width;
  }

  @Override
  public UIRectangle getRowBounds(int row0, int row1) {
    return new UIRectangle(0, 0, 0, 0);
  }

  /**
   * Get the index of the row at the specified location within the table
   *
   * @param p
   *          the point
   * @return -1 always
   */
  public int getRowIndexAt(UIPoint p) {
    return getRowIndexAt(p.x, p.y);
  }

  @Override
  public List<RenderableDataItem> getRows() {
    return listModel;
  }

  @Override
  public int getSelectedIndex() {
    return listView.getSelectedIndex();
  }

  @Override
  public int getSelectedIndexCount() {
    return (getSelectedIndex() == -1)
           ? 0
           : 1;
  }

  @Override
  public int[] getSelectedIndexes() {
    final int n = getSelectedIndex();

    return (n == -1)
           ? new int[0]
           : new int[] { n };
  }

  @Override
  public RenderableDataItem getSelectedItem() {
    final int n = getSelectedIndex();

    return (n == -1)
           ? null
           : get(n);
  }

  public Object[] getSelectedObjects() {
    Object[] a = getSelections();

    if ((a == null) || (a.length == 0)) {
      return null;
    }

    return a;
  }

  @Override
  public Object getSelection() {
    return getSelectedItem();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSelectionAsString() {
    return Helper.toString(getSelectionsAsStrings(), "\r\n");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getSelections() {
    return DataItemCollection.getSelections(this, -1);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public String[] getSelectionsAsStrings() {
    return DataItemCollection.getValuesAsStrings(listModel, this.getSelectedIndexes(), -1, false, false, null);
  }

  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return listModel.getUnfilteredList();
  }

  @Override
  public int getVisibleRowCount() {
    if (visibleRowCount != 0) {
      return visibleRowCount;
    }

    int h  = UIScreen.snapToSize(listView.getListComponent().getHeight());
    int rh = getRowHeight();

    if (rh < 1) {
      rh = ScreenUtils.lineHeight(getListComponent());
    }

    return Math.max(h / rh, 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelection() {
    return getSelectedIndex() != -1;
  }

  public boolean hasValue() {
    return getItemCount() > 0;
  }

  @Override
  public boolean isChangeEventsEnabled() {
    return changeEventsEnabled;
  }

  @Override
  public boolean isChangeSelColorOnLostFocus() {
    return changeSelColorOnLostFocus;
  }

  @Override
  public boolean isDataEventsEnabled() {
    return listModel.isEventsEnabled();
  }

  /**
   * Returns whether deselect events are disabled
   *
   * @return true if deselect events are disabled; false otherwise
   */
  public boolean isDeselectEventsDisabled() {
    return deselectEventsDisabled;
  }

  @Override
  public boolean isFiltered() {
    return listModel.isFiltered();
  }

  @Override
  public boolean isHandleFirstFocusSelection() {
    return handleFirstFocusSelection;
  }

  @Override
  public boolean isListSelectable() {
    return listView.isSelectable();
  }

  public boolean isMultipleSelectionAllowed() {
    return false;
  }

  @Override
  public boolean isRowSelected(int row) {
    return getSelectedIndex() == row;
  }

  @Override
  public boolean isRowSelected(RenderableDataItem item) {
    int row = indexOf(item);

    return (row == -1)
           ? false
           : isRowSelected(row);
  }

  /**
   * Returns whether a single click generates an action event
   *
   * @return true if a single click generates an action event; false otherwise
   */
  public boolean isSingleClickAction() {
    return listView.isSingleClickAction();
  }

  @Override
  public boolean isTabular() {
    return false;
  }

  protected Object resolveChangeValue(Object cv) {
    if (cv instanceof IntList) {
      IntList l   = (IntList) cv;
      int     len = l.size();

      if ((len == 0) || ((len == 1) && (l.get(0) == -1))) {
        return null;
      }

      RenderableDataItem[] a = new RenderableDataItem[len];

      for (int i = 0; i < len; i++) {
        a[i] = getEx(l.get(i));
      }

      cv = a;
    } else if (cv instanceof int[]) {
      int[] l   = (int[]) cv;
      int   len = l.length;

      if ((len == 0) || ((len == 1) && (l[0] == -1))) {
        return null;
      }

      RenderableDataItem[] a = new RenderableDataItem[len];

      for (int i = 0; i < len; i++) {
        a[i] = getEx(l[i]);
      }

      cv = a;
    } else if (cv != null) {
      Integer n = (Integer) cv;

      if (n == -1) {
        cv = null;
      } else {
        cv = getEx(n);
      }
    }

    return cv;
  }

  protected void selectionsChanges(Object ov, Object nv) {
    if ((ov != null) &&!deselectEventsDisabled) {
      ItemChangeEvent ie = new ItemChangeEvent(listView.getListComponent(), ov, null);

      Utils.fireItemChanged(listenerList, ie);
    }

    if (nv != null) {
      ItemChangeEvent ie = new ItemChangeEvent(listView.getListComponent(), ov, nv);

      Utils.fireItemChanged(listenerList, ie);
    }
  }

  protected boolean wantsDeselectedEvents() {
    return !deselectEventsDisabled && (listenerList != null)
           && (listenerList.getListenerCount(iItemChangeListener.class) > 0);
  }

  protected boolean wantsSelectedEvents() {
    return isChangeEventsEnabled() && (listenerList != null)
           && (listenerList.getListenerCount(iItemChangeListener.class) > 0);
  }

  protected abstract void setRowHeightEx(int height);

  protected abstract void setSelectedIndexEx(int index);

  protected abstract void setSelectedIndexesEx(int[] indices);

  protected Object getSelectionObjectsForChangeEvent() {
    return getSelection();
  }

  private void calculateItemSize(Column col, RenderableDataItem item, int row, RenderableDataItem rowItem,
                                 UIDimension size) {
    iPlatformComponent c = item.getRenderingComponent();

    if (c != null) {
      c.getPreferredSize(size);
    } else {
      iPlatformRenderingComponent rc = (col == null)
                                       ? null
                                       : col.getCellRenderer();

      if (rc == null) {
        rc = computeSizeRenderer;

        if (rc == null) {
          rc = computeSizeRenderer = new UILabelRenderer();
        }
      }

      CharSequence text = calcRenderer.configureRenderingComponent(calcComponent, rc, item, row, false, false, col,
                            rowItem);

      rc.getComponent(text, item).getPreferredSize(size);
    }
  }

  private void calculateRowSize(int row, UIDimension size) {
    calculateItemSize(null, listModel.get(row), row, null, size);
  }
}
