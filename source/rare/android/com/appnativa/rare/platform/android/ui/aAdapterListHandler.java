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

package com.appnativa.rare.platform.android.ui;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import android.widget.AdapterView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.FocusEvent;
import com.appnativa.rare.ui.event.ItemChangeEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
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

/**
 * A base class for list style components
 *
 * @author Don DeCoteau
 */
public abstract class aAdapterListHandler extends AbstractList<RenderableDataItem>
        implements iPlatformListHandler, iActionListener, AdapterView.OnItemSelectedListener,
                   AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {
  public static final String       CALL_SUPER_METHOD      = "CALL_SUPER_METHOD";
  protected boolean                deselectEventsDisabled = true;
  protected RenderableDataItem     lastSelected;
  protected iPlatformListDataModel listModel;
  protected EventListenerList      listenerList;
  protected int                    minRowHeight;
  protected boolean                singleClickAction;
  private boolean                  changeEventsEnabled = true;
  private int                      rowHeight           = 0;
  private final AdapterView        adapterView;
  private iPlatformComponent       calcComponent;
  private iPlatformItemRenderer    calcRenderer;
  private boolean                  changeSelColorOnLostFocus;
  private UILabelRenderer          computeSizeRenderer;
  private int                      doubleTapTimeout;
  private boolean                  handleFirstFocusSelection;
  private int                      minVisibleRowCount;
  private int                      visibleRowCount;
  private boolean                  keepSelectionVisible;

  public aAdapterListHandler(AdapterView view) {
    this.adapterView = view;
  }

  public aAdapterListHandler(AdapterView view, iPlatformListDataModel model) {
    this.adapterView = view;
    this.listModel   = model;
  }

  public void actionPerformed(ActionEvent e) {
    fireAction(e);
  }

  public boolean add(RenderableDataItem e) {
    return listModel.add(e);
  }

  public void add(int index, RenderableDataItem element) {
    listModel.add(index, element);
  }

  public void addActionListener(iActionListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    adapterView.setOnItemClickListener(this);
    listenerList.add(iActionListener.class, l);
  }

  public boolean addAll(Collection<? extends RenderableDataItem> collection) {
    return listModel.addAll(collection);
  }

  public boolean addAll(int location, Collection<? extends RenderableDataItem> collection) {
    return listModel.addAll(location, collection);
  }

  public void addAll(int index, List<RenderableDataItem> items, boolean insertMode) {
    addAll(index, items);
  }

  public void addDataModelListener(iDataModelListener l) {
    listModel.addDataModelListener(l);
  }

  public void addIndexToFilteredList(int index) {
    listModel.addIndexToFilteredList(index);
  }

  public void addSelectionChangeListener(iItemChangeListener l) {
    if (listenerList == null) {
      listenerList = new EventListenerList();
    }

    adapterView.setOnItemSelectedListener(this);
    listenerList.add(iItemChangeListener.class, l);
  }

  public void addSelectionIndex(int index) {
    setSelectedIndex(index);
  }

  public void addToFilteredList(RenderableDataItem item) {
    listModel.addToFilteredList(item);
  }

  public void addToFilteredList(int index, RenderableDataItem item) {
    listModel.addToFilteredList(index, item);
  }

  public void clear() {
    clearSelection();
    listModel.clear();
  }

  public abstract void clearContextMenuIndex();

  public void clearSelection() {
    lastSelected = null;
    setSelectedIndex(-1);
  }

  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return listModel.concat(e);
  }

  public void copySelectedItems(int index, boolean insertMode, boolean delete) {
    ListHelper.copySelectedItems(this, index, insertMode, delete);
  }

  /**
   * {@inheritDoc}
   */
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

  public void dispose() {
    if (listenerList != null) {
      Object[] l = listenerList.getListenerList();

      if (l != null) {
        for (int i = 0; i < l.length; i++) {
          l[i] = null;
        }
      }
    }

    listenerList = null;
    listModel    = null;
  }

  public void ensureCapacity(int capacity) {
    listModel.ensureCapacity(capacity);
  }

  public boolean filter(iFilter filter) {
    return listModel.filter(filter);
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    listModel.setFilteredList(list, lastFilter);
  }

  public boolean filter(String filter, boolean contains) {
    return listModel.filter(filter, contains);
  }

  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return listModel.filter(filter, contains, nullPasses, emptyPasses);
  }

  public int find(iFilter filter, int start) {
    return listModel.find(filter, start);
  }

  public int find(String filter, int start, boolean contains) {
    return listModel.find(filter, start, contains);
  }

  public void fireAction(ActionEvent e) {
    if ((listenerList != null) && (getListComponent() != null)) {
      Utils.fireActionEvent(listenerList, e);
    }
  }

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
    if (e.wasFocusGained()) {
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

  public static void installItemLinkListener(iPlatformComponent c, iHyperlinkListener l) {
    // if (c != null) {
    // c.putClientProperty(HYPERLINK_LISTENER_KEY, l);
    // }
  }

  public String join(String sep) {
    return listModel.join(sep);
  }

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

  public void onItemClick(AdapterView<?> list, View view, int position, long id) {
    if (!singleClickAction && (list instanceof ListViewEx)) {
      if (!((ListViewEx) list).wasDoubleClick()) {
        return;
      }
    }

    ActionEvent ae = new ActionEvent(Component.fromView(adapterView), "click");

    Utils.fireActionEvent(listenerList, ae);
  }

  public boolean onItemLongClick(AdapterView<?> list, View view, int position, long id) {
    ActionEvent ae = new ActionEvent(Component.fromView(list), "long-click");

    Utils.fireActionEvent(listenerList, ae);

    return true;
  }

  public void onItemSelected(AdapterView<?> list, View view, int position, long id) {
    if ((listenerList == null) ||!isChangeEventsEnabled()) {
      return;
    }

    RenderableDataItem o = listModel.get(position);

    if (o.isEnabled()) {
      if ((lastSelected != null) &&!deselectEventsDisabled) {
        ItemChangeEvent ie = new ItemChangeEvent(Component.fromView(list), lastSelected, null);

        Utils.fireItemChanged(listenerList, ie);
      }

      ItemChangeEvent ie = new ItemChangeEvent(Component.fromView(list), lastSelected, o);

      lastSelected = o;
      Utils.fireItemChanged(listenerList, ie);
    }
  }

  public boolean onKey(View view, int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      switch(keyCode) {
        case KeyEvent.KEYCODE_SPACE :
        case KeyEvent.KEYCODE_DPAD_CENTER :
        case KeyEvent.KEYCODE_ENTER :
          ActionEvent ae = new ActionEvent(Component.fromView(view), event.toString());

          Utils.fireActionEvent(listenerList, ae);

          return true;
      }
    }

    return false;
  }

  public void onNothingSelected(AdapterView<?> list) {
    try {
      if ((listenerList == null) ||!isChangeEventsEnabled()) {
        return;
      }

      if ((lastSelected != null) &&!deselectEventsDisabled) {
        ItemChangeEvent ie = new ItemChangeEvent(Component.fromView(list), lastSelected, null);

        Utils.fireItemChanged(listenerList, ie);
      }
    } finally {
      lastSelected = null;
    }
  }

  public RenderableDataItem pop() {
    return ((listModel == null) || (listModel.size() == 0))
           ? null
           : listModel.remove(listModel.size() - 1);
  }

  public void push(RenderableDataItem... value) {
    listModel.push(value);
  }

  public boolean refilter() {
    return listModel.refilter();
  }

  public void refreshItems() {
    listModel.refreshItems();
  }

  public void registerActionListener(boolean single) {}

  public RenderableDataItem remove(int index) {
    if (this.isRowSelected(index)) {
      this.removeSelection(index);
    }

    return listModel.remove(index);
  }

  public boolean remove(Object item) {
    int n = indexOf(item);

    if (n == -1) {
      return false;
    }

    return remove(n) != null;
  }

  public void removeActionListener(iActionListener l) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, l);
    }
  }

  public boolean removeAll(Collection<?> c) {
    Object[] a = getSelections();

    this.clearSelection();

    boolean ok = super.removeAll(c);

    setSelections(this, a);

    return ok;
  }

  public void removeDataModelListener(iDataModelListener l) {
    listModel.removeDataModelListener(l);
  }

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

  public void removeSelection(int index) {
    if (isRowSelected(index)) {
      setSelectedIndex(-1);
    }
  }

  public void removeSelectionChangeListener(iItemChangeListener l) {
    if (listenerList != null) {
      listenerList.remove(iItemChangeListener.class, l);
    }
  }

  public boolean retainAll(Collection<?> c) {
    Object[] a = getSelections();

    this.clearSelection();

    boolean ok = super.retainAll(c);

    setSelections(this, a);

    return ok;
  }

  public List<RenderableDataItem> reverse() {
    listModel.reverse();

    return this;
  }

  public void scrollRowToVisible(int row) {
    if (adapterView instanceof ListViewEx) {
      ((ListViewEx) adapterView).scrollRowToVisible(row);
    }
  }

  @Override
  public void makeSelectionVisible() {
    int n = getSelectedIndex();

    if (n != -1) {
      if (adapterView instanceof ListViewEx) {
        ((ListViewEx) adapterView).scrollRowToVisible(n);
      }
    }
  }

  public boolean isKeepSelectionVisible() {
    return keepSelectionVisible;
  }

  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    this.keepSelectionVisible = keepSelectionVisible;
  }

  public void selectAll() {}

  public RenderableDataItem shift() {
    return ((listModel == null) || (listModel.size() == 0))
           ? null
           : listModel.remove(0);
  }

  public int size() {
    return listModel.size();
  }

  public void sizeRowsToFit() {
    getAdapterView().requestLayout();
  }

  public List<RenderableDataItem> slice(int start) {
    return listModel.slice(start, size());
  }

  public List<RenderableDataItem> slice(int start, int end) {
    return listModel.slice(start, end);
  }

  public void sort(Comparator comparator) {
    listModel.sort(comparator);
  }

  public List<RenderableDataItem> splice(int index, int howMany) {
    return splice(index, howMany, (RenderableDataItem[]) null);
  }

  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return spliceList(index, howMany, (e == null)
                                      ? null
                                      : Arrays.asList(e));
  }

  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    return listModel.spliceList(index, howMany, e);
  }

  public void swap(int index1, int index2) {
    listModel.swap(index1, index2);

    if (isRowSelected(index1)) {
      setSelectedIndex(index2);
    } else if (isRowSelected(index2)) {
      setSelectedIndex(index1);
    }
  }

  @Override
  public void trimToSize() {
    listModel.trimToSize();
  }

  public boolean unfilter() {
    return listModel.unfilter();
  }

  public static void uninstallItemLinkListener(iPlatformComponent c) {}

  public void unshift(RenderableDataItem value) {
    add(0, value);
  }

  public RenderableDataItem set(int index, RenderableDataItem item) {
    return listModel.set(index, item);
  }

  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return listModel.setAll(collection);
  }

  public void setAlternatingRowColor(UIColor color) {
    listModel.setAlternatingColor(color);

    if (adapterView instanceof ListViewEx) {
      ((ListViewEx) adapterView).setAlternateRowColor(color);
    }
  }

  public void setAutoHilight(boolean autoHilight) {}

  public void setChangeEventsEnabled(boolean enabled) {
    this.changeEventsEnabled = enabled;
  }

  public void setChangeSelColorOnLostFocus(boolean change) {
    changeSelColorOnLostFocus = change;
  }

  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    listModel.setConverter(converter);
  }

  public void setDataEventsEnabled(boolean enabled) {
    listModel.setEventsEnabled(enabled);
  }

  public void setDeselectEventsDisabled(boolean deselectEventsDisabled) {
    this.deselectEventsDisabled = deselectEventsDisabled;
  }

  public void setDividerLine(UIColor c, UIStroke stroke) {
    if (adapterView instanceof ListViewEx) {
      ((ListViewEx) adapterView).setDividerLine(c, stroke);
    }
  }

  public void setHandleFirstFocusSelection(boolean handle) {
    handleFirstFocusSelection = handle;
  }

  public void setListSelectable(boolean selectable) {}

  public void setListSelectionType(SelectionType type) {
    listModel.setSelectionType(type);
  }

  public void setMinRowHeight(int min) {
    if (adapterView instanceof ListViewEx) {
      ((ListViewEx) adapterView).setMinRowHeight(min);
    }

    this.minRowHeight = min;

    if (visibleRowCount > 0) {
      setVisibleRowCount(visibleRowCount);
    }
  }

  public void setMinimumVisibleRowCount(int rows) {
    AdapterView lv = getAdapterView();

    if (lv instanceof ListViewEx) {
      ((ListViewEx) lv).setMinimumVisibleRowCount(rows);
    } else {
      int rh = getRowHeight();

      if (rh < 1) {
        rh = ScreenUtils.lineHeight(Component.fromView(lv));
      }

      lv.setMinimumHeight(rh * rows);
      lv.requestLayout();
    }

    minVisibleRowCount = rows;
  }

  public void setRowHeight(int height) {
    final View v = getAdapterView();

    if (v instanceof ListViewEx) {
      ((ListViewEx) v).setRowHeight(height);
    }

    rowHeight = height;
    listModel.setRowHeight(height);
  }

  public void setSelectedIndexes(int[] indices) {
    int n = ((indices == null) || (indices.length == 0))
            ? -1
            : indices[0];

    setSelectedIndex(n);
  }

  public void setSelectedItem(RenderableDataItem value) {
    int n = listModel.indexOf(value);

    if (n != -1) {
      setSelectedIndex(n);
    }
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

  public void setShowDivider(boolean show) {
    if (adapterView instanceof ListViewEx) {
      ((ListViewEx) adapterView).setShowDivider(show);
    }
  }

  public void setSingleClickAction(boolean singleClickAction) {
    if (doubleTapTimeout == 0) {
      Integer i = Platform.getUIDefaults().getInteger("Rare.Pointer.doubleClickThreshold");

      doubleTapTimeout = (i == null)
                         ? Math.max(ViewConfiguration.getDoubleTapTimeout(), 500)
                         : i;
    }

    this.singleClickAction = singleClickAction;
  }

  public void setVisibleRowCount(int rows) {
    View lv = getAdapterView();

    if (lv instanceof ListViewEx) {
      ((ListViewEx) lv).setVisibleRowCount(rows);
    } else if (minVisibleRowCount == 0) {
      int rh = getRowHeight();

      if (rh < 1) {
        rh = ScreenUtils.lineHeight(Component.fromView(lv));
      }

      lv.setMinimumHeight(rh * rows);
      lv.requestLayout();
    }

    visibleRowCount = rows;
  }

  public RenderableDataItem get(int pos) {
    return listModel.get(pos);
  }

  public UIColor getAlternatingRowColor() {
    return listModel.getAlternatingColor();
  }

  public int getAnchorSelectionIndex() {
    return getMaxSelectionIndex();
  }

  public iPlatformComponent getContainerComponent() {
    return getListComponent();
  }

  public iStringConverter<RenderableDataItem> getConverter() {
    return listModel.getConverter();
  }

  public List<RenderableDataItem> getFilteredList() {
    return listModel.getFilteredList();
  }

  public int getFirstVisibleIndex() {
    if (adapterView instanceof ListViewEx) {
      return ((ListViewEx) adapterView).getFirstVisiblePosition();
    }

    return -1;
  }

  public int getItemCount() {
    return listModel.size();
  }

  public Column getItemDescription() {
    return null;
  }

  public static iHyperlinkListener getItemLinkListener(iPlatformComponent c) {
    // if (c != null) {
    // return (iHyperlinkListener) c.getClientProperty(HYPERLINK_LISTENER_KEY);
    // }
    return null;
  }

  public iPlatformItemRenderer getItemRenderer() {
    return listModel.getItemRenderer();
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

  public iFilter getLastFilter() {
    return listModel.getLastFilter();
  }

  public int getLastVisibleIndex() {
    if (adapterView instanceof ListViewEx) {
      return ((ListViewEx) adapterView).getLastVisiblePosition();
    }

    return -1;
  }

  public iPlatformComponent getListComponent() {
    return (iPlatformComponent) Component.fromView(getAdapterView());
  }

  public SelectionType getListSelectionType() {
    return listModel.getSelectionType();
  }

  public int getMaxSelectionIndex() {
    return getSelectedIndex();
  }

  public int getMinRowHeight() {
    return minRowHeight;
  }

  public int getMinSelectionIndex() {
    return getSelectedIndex();
  }

  public int getMinimumVisibleRowCount() {
    return minVisibleRowCount;
  }

  public abstract int getContextMenuIndex();

  public RenderableDataItem getContextMenuItem() {
    final int n = getContextMenuIndex();

    return (n == -1)
           ? null
           : get(n);
  }

  public int getPreferredHeight(int row) {
    throw new UnsupportedOperationException();
  }

  public UIDimension getPreferredSize() {
    calcRenderer  = getItemRenderer();
    calcComponent = getListComponent();

    UIDimension size   = new UIDimension();
    float       width  = 0;
    float       height = 0;
    int         len    = listModel.size();

    for (int i = 0; i < len; i++) {
      calculateRowSize(i, size);
      width  = Math.max(width, size.width);
      height += size.height;
    }

    size.height = height;
    size.width  = width;

    return size;
  }

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

  public UIRectangle getRowBounds(int row0, int row1) {
    throw new UnsupportedOperationException();
  }

  public int getRowHeight() {
    final View v = getAdapterView();

    if (v instanceof ListViewEx) {
      return ((ListViewEx) v).getRowHeight();
    }

    return rowHeight;
  }

  public int getRowIndexAt(float x, float y) {
    return -1;
  }

  public List<RenderableDataItem> getRows() {
    return listModel;
  }

  public int getSelectedIndexCount() {
    return (getSelectedIndex() == -1)
           ? 0
           : 1;
  }

  public int[] getSelectedIndexes() {
    final int n = getSelectedIndex();

    return (n == -1)
           ? new int[0]
           : new int[] { n };
  }

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

  public Object getSelection() {
    return getSelectedItem();
  }

  /**
   * {@inheritDoc}
   */
  public String getSelectionAsString() {
    return Helper.toString(getSelectionsAsStrings(), "\r\n");
  }

  /**
   * {@inheritDoc}
   */
  public Object[] getSelections() {
    return DataItemCollection.getSelections(this, -1);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String[] getSelectionsAsStrings() {
    return DataItemCollection.getValuesAsStrings(listModel, this.getSelectedIndexes(), -1, false, false, null);
  }

  public List<RenderableDataItem> getUnfilteredList() {
    return listModel.getUnfilteredList();
  }

  public int getVisibleRowCount() {
    return visibleRowCount;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasSelection() {
    return getSelectedIndex() != -1;
  }

  public boolean hasValue() {
    return getItemCount() > 0;
  }

  public boolean isChangeEventsEnabled() {
    return changeEventsEnabled;
  }

  public boolean isChangeSelColorOnLostFocus() {
    return changeSelColorOnLostFocus;
  }

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
  public boolean isEditing() {
    if (adapterView instanceof ListViewEx) {
      return ((ListViewEx) adapterView).isEditing();
    }

    return false;
  }

  public boolean isFiltered() {
    return listModel.isFiltered();
  }

  public boolean isHandleFirstFocusSelection() {
    return handleFirstFocusSelection;
  }

  public boolean isListSelectable() {
    return true;
  }

  public boolean isRowSelected(int row) {
    return getSelectedIndex() == row;
  }

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
    return singleClickAction;
  }

  public boolean isTabular() {
    return false;
  }

  protected AdapterView getAdapterView() {
    return adapterView;
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
