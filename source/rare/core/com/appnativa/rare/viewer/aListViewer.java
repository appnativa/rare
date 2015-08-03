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
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.Margin;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UISelectionModelGroup;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.border.UIMatteBorder;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iTransferable;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iListView.EditingMode;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.ui.iScrollerSupport;
import com.appnativa.rare.ui.iToolBar;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.table.TableHelper;
import com.appnativa.rare.util.DataItemCollection;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.util.CharacterIndex;
import com.appnativa.util.FilterableList;
import com.appnativa.util.IntList;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.SNumber;
import com.appnativa.util.iFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A widget that allows a user to select one or more choices from a scrollable
 * list of items.
 *
 * @author Don DeCoteau
 */
public abstract class aListViewer extends aPlatformViewer implements iActionable, iListHandler {

  /** the initial selected index */
  protected int initiallySelectedIndex = -1;

  /** whether the filtering index charters should be added to the list */
  protected boolean addIndexToList;

  /** the description of the list items */
  protected Column itemDescription;

  /** the list component */
  protected iPlatformListHandler listComponent;

  /** the list model */
  protected iPlatformListDataModel listModel;
  protected RenderableDataItem     prototypeSectionItem;
  protected SelectionMode          selectionMode;
  protected Object                 selectionModel;
  protected UISelectionModelGroup  selectionModelGroup;

  /**
   * temporary storage for a sort operation called when temporary list is in use
   * (meant for sub classes that can't just sort the temp list)
   */
  protected Comparator sorter;

  /** the column to use for submitting data */
  protected int submitColumn;

  /** the submit value type */
  protected int submitValueType;

  /** temporary list for data added via background threads */
  protected List<RenderableDataItem> tempList;

  public aListViewer() {
    this(null);
  }

  public aListViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected void handleViewerConfigurationChanged(boolean reset) {
    super.handleViewerConfigurationChanged(reset);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    iActionListener    l   = null;
    RenderableDataItem row = getSelectedItem();

    l = (row == null)
        ? null
        : row.getActionListener();

    if (l != null) {
      if (l instanceof ActionLink) {
        ((ActionLink) l).setPopupLocation(getPopupLocation(getSelectedIndex()));
      }

      l.actionPerformed(e);
    } else {
      super.actionPerformed(e);
    }
  }

  @Override
  public boolean add(RenderableDataItem item) {
    listComponent.add(item);

    return true;
  }

  @Override
  public void add(int index, RenderableDataItem item) {
    listComponent.add(index, item);
  }

  @Override
  public void addActionListener(iActionListener l) {
    listComponent.addActionListener(l);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    listComponent.addAll(c);

    return true;
  }

  @Override
  public void addAll(RenderableDataItem[] a) {
    add(a, 0, (a == null)
              ? 0
              : a.length);
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    listComponent.addAll(index, c);

    return true;
  }

  @Override
  public void addAll(int index, List<RenderableDataItem> items, boolean insertMode) {
    listComponent.addAll(index, items, insertMode);
  }

  /**
   * Add a row to a temporary list. Call refresh items to update the main list
   *
   * @param row
   *          the row to add
   * @see #refreshItems()
   */
  public void addEx(RenderableDataItem row) {
    getTempList().add(row);
  }

  @Override
  public void addIndexToFilteredList(int index) {
    listComponent.addIndexToFilteredList(index);
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    addEx(row);
  }

  @Override
  public void addSelectionChangeListener(iItemChangeListener l) {
    listComponent.addSelectionChangeListener(l);
  }

  @Override
  public void addSelectionIndex(int index) {
    listComponent.addSelectionIndex(index);
  }

  @Override
  public boolean canImport(TransferFlavor[] flavors, DropInformation drop) {
    if (draggingAllowed && (drop != null) && (drop.getSourceWidget() == this) && isEditing()) {
      return true;
    }

    return super.canImport(flavors, drop);
  }

  public UISelectionModelGroup getSelectionModelGroup() {
    return selectionModelGroup;
  }

  @Override
  public void clear() {
    if (!Platform.isUIThread()) {
      ListHelper.runLater(this, ListHelper.RunType.CLEAR);

      return;
    }

    initiallySelectedIndex = -1;
    itemAttributes         = null;

    if (listComponent != null) {
      unfilter();
      listComponent.clear();
    }
  }

  @Override
  public void clearContents() {
    clear();
  }

  @Override
  public void clearPopupMenuIndex() {
    listComponent.clearPopupMenuIndex();
  }

  @Override
  public void clearSelection() {
    listComponent.clearSelection();
  }

  /**
   * Clears/resets the temporary list used by background threads to update the
   * widget data
   *
   * @see #addEx(RenderableDataItem)
   * @see #refreshItems()
   */
  public void clearTempList() {
    if (tempList != null) {
      tempList.clear();
    }

    tempList = null;
  }

  @Override
  public void convert(RenderableDataItem item) {
    if (itemDescription != null) {
      itemDescription.convert(this, item);
    }
  }

  @Override
  public void copySelectedItems(int index, boolean insertMode, boolean delete) {
    listComponent.copySelectedItems(index, insertMode, delete);
  }

  @Override
  public Object deleteSelectedData(boolean returnData) {
    return listComponent.deleteSelectedData(returnData);
  }

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    if ((selectionModelGroup != null) && (selectionModel != null)) {
      selectionModelGroup.remove(selectionModel);
    }

    if (listModel != null) {
      listModel.dispose();
    }

    super.dispose();

    if (listComponent != null) {
      listComponent.dispose();
    }

    listModel           = null;
    listComponent       = null;
    selectionModelGroup = null;
    itemDescription     = null;
    selectionModel      = null;
    selectionMode       = null;
  }

  /**
   * Gets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @return true to keep visible; false otherwise
   */
  @Override
  public boolean isKeepSelectionVisible() {
    return listComponent.isKeepSelectionVisible();
  }

  /**
   * Sets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @param keepSelectionVisible true to keep visible; false otherwise
   */
  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {
    listComponent.setKeepSelectionVisible(keepSelectionVisible);
  }

  @Override
  public boolean filter(iFilter filter) {
    if (listComponent.filter(filter)) {
      this.clearSelection();
      listComponent.refreshItems();

      return true;
    }

    return false;
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    if (listComponent.filter(filter, contains, nullPasses, emptyPasses)) {
      this.clearSelection();
      listComponent.refreshItems();

      return true;
    }

    return false;
  }

  /**
   * Finds and selects an item in the list given a starting point and a search
   * filter
   *
   * @param filter
   *          the filter for the search
   * @param start
   *          the starting position in the list
   *
   * @return the in index of the item that was found and selected
   */
  public int findAndSelect(iFilter filter, int start) {
    int n = find(filter, start);

    if (n != -1) {
      this.setSelectedIndex(n);
    }

    return n;
  }

  /**
   * Finds and selects an item in the list given a starting point and a search
   * filter The method performs a case insensitive search
   *
   * @param filter
   *          the filter for the search
   * @param start
   *          the starting position in the list
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   *
   * @return the in index of the item that was found and selected
   */
  public int findAndSelect(String filter, int start, boolean contains) {
    int n = find(filter, start, contains);

    if (n != -1) {
      this.setSelectedIndex(n);
    }

    return n;
  }

  @Override
  public void fireActionForSelected() {
    listComponent.fireActionForSelected();
  }

  @Override
  public void move(int from, int to) {
    listComponent.move(from, to);
  }

  /**
   * Refreshes the displayed items. Call this method after significant changes
   * have been made to data items. If items were populated into a temporary list
   * via the {@link #addEx} method then the data in the temporary list is used
   * to populate the component
   *
   * This method is guaranteed to always run on the event dispatch thread and is
   * therefore safe to call from a background thread (created via
   * window.spawn())
   */
  @Override
  public void refreshItems() {
    if (!Platform.isUIThread()) {
      ListHelper.runLater(this, ListHelper.RunType.REFRESH);

      return;
    }

    if (isDisposed()) {
      return;
    }

    try {
      listComponent.setDataEventsEnabled(false);

      if (tempList != null) {
        if (sorter != null) {
          Collections.sort(tempList, sorter);
        }

        if (addIndexToList) {
          List<RenderableDataItem> list = updateListWithFilteringIndex((FilterableList<RenderableDataItem>) tempList);

          listComponent.setAll(list);
          list.clear();
        } else {
          listComponent.setAll(tempList);
        }

        tempList.clear();
        tempList = null;
      }
    } finally {
      listComponent.setDataEventsEnabled(true);
      listComponent.refreshItems();
    }
  }

  @Override
  public RenderableDataItem remove(int index) {
    return listComponent.remove(index);
  }

  @Override
  public boolean remove(Object item) {
    if (item instanceof Number) {
      listComponent.remove(((Number) item).intValue());

      return true;
    }

    return listComponent.remove(item);
  }

  @Override
  public void removeActionListener(iActionListener l) {
    if (listComponent != null) {
      listComponent.removeActionListener(l);
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return listComponent.removeAll(c);
  }

  public boolean removeData(iTransferable t) {
    if (t.isTransferFlavorSupported(TransferFlavor.itemFlavor)) {
      try {
        List<RenderableDataItem> list = (List<RenderableDataItem>) t.getTransferData(TransferFlavor.itemFlavor);
        int                      len  = list.size();

        if (isEventEnabled(iConstants.EVENT_ITEM_DELETED)) {
          DataEvent e = new DataEvent(this, list);

          getWidgetListener().evaluate(iConstants.EVENT_ITEM_DELETED, e, false);

          if (e.isConsumed()) {
            return false;
          }
        }

        iPlatformListDataModel lm = listModel;

        if (len == lm.size()) {
          clear();
        } else {
          IntList indexes = new IntList(len);

          for (RenderableDataItem item : list) {
            int i = lm.indexOf(item);

            if (i != -1) {
              indexes.add(i);
            }
          }

          if (indexes.size() == len) {
            listComponent.removeRows(indexes.A);
          } else {
            listComponent.removeRows(indexes.toArray());
          }
        }

        return true;
      } catch(Exception ignore) {
        Platform.ignoreException(null, ignore);
      }
    }

    return false;
  }

  @Override
  public void removeRows(int[] indexes) {
    listComponent.removeRows(indexes);
  }

  @Override
  public void removeSelection(int index) {
    listComponent.removeSelection(index);
  }

  @Override
  public void removeSelectionChangeListener(iItemChangeListener l) {
    if (listComponent != null) {
      listComponent.removeSelectionChangeListener(l);
    }
  }

  /**
   * Repaints a row
   *
   * @param row
   *          the row to repaint
   */
  public void repaintRow(int row) {
    rowChanged(row);
  }

  @Override
  public void reset() {
    if (widgetDataLink != null) {
      super.reload(true);
    } else {
      clearSelection();

      if ((initiallySelectedIndex > -1) && (initiallySelectedIndex < this.getItemCount())) {
        this.setSelectedIndex(initiallySelectedIndex);
      }
    }
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return listComponent.retainAll(c);
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param index
   *          the index of the row that changed
   */
  public void rowChanged(int index) {
    if (listModel != null) {
      listModel.rowChanged(index);
    }
  }

  /**
   * Notifies the list that the contents of the specified row has changed
   *
   * @param item
   *          the instance of the row that changed
   */
  public void rowChanged(RenderableDataItem item) {
    if (listModel != null) {
      listModel.rowChanged(item);
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
  public void rowsChanged(int firstRow, int lastRow) {
    if (listModel != null) {
      listModel.rowsChanged(firstRow, lastRow);
    }
  }

  @Override
  public void scrollRowToTop(int row) {
    listComponent.scrollRowToTop(row);
  }

  @Override
  public void scrollRowToBottom(int row) {
    listComponent.scrollRowToBottom(row);
  }

  @Override
  public void scrollRowToVisible(int row) {
    listComponent.scrollRowToVisible(row);
  }

  @Override
  public void makeSelectionVisible() {
    listComponent.makeSelectionVisible();
  }

  @Override
  public void selectAll() {
    listComponent.selectAll();
  }

  @Override
  public void sizeRowsToFit() {
    listComponent.sizeRowsToFit();
  }

  @Override
  public void sort(boolean descending) {
    if (tempList != null) {
      Collections.sort(tempList, descending
                                 ? SubItemComparator.getReverseOrderComparatorInstance()
                                 : null);
    } else {
      listComponent.sort(descending
                         ? SubItemComparator.getReverseOrderComparatorInstance()
                         : null);
    }
  }

  @Override
  public void sort(Comparator comparator) {
    if (tempList != null) {
      Collections.sort(tempList, comparator);
    } else {
      listComponent.sort(comparator);
    }
  }

  /**
   * Starts editing of the lists allowing items to be added, deleted, or
   * reordered (based on the configured support for these actions)
   *
   * @param actions
   *          a set of custom actions for the editing toolbar. Use null to get
   *          the default toolbar. Use and empty array to have no toolbar
   * @param animate
   *          true to animate (if supported on the platform); false otherwise
   */
  public void startEditing(boolean animate, UIAction... actions) {}

  /**
   * Stop the list or row editing
   *
   * @param animate
   *          true to animate (if supported on the platform); false otherwise
   */
  public void stopEditing(boolean animate) {}

  @Override
  public boolean unfilter() {
    if (listComponent.unfilter()) {
      listComponent.refreshItems();

      return true;
    }

    return false;
  }

  @Override
  public boolean setAll(final Collection<? extends RenderableDataItem> rows) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        if (!isDisposed()) {
          listComponent.setAll(rows);
        }
      }
    };

    if (Platform.isUIThread()) {
      r.run();
    } else {
      Platform.invokeLater(r);
    }

    return true;
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {
    listComponent.setAlternatingRowColor(color);
  }

  @Override
  public void setAutoHilight(boolean autoHilight) {
    listComponent.setAutoHilight(autoHilight);
  }

  public abstract void setAutoSizeRowsToFit(boolean autoSize);

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    listComponent.setChangeEventsEnabled(enabled);
  }

  @Override
  public void setChangeSelColorOnLostFocus(boolean change) {
    listComponent.setChangeSelColorOnLostFocus(change);
  }

  @Override
  public void setDataEventsEnabled(boolean enabled) {
    listComponent.setDataEventsEnabled(enabled);
  }

  @Override
  public void setDeselectEventsDisabled(boolean disabled) {
    listComponent.setDeselectEventsDisabled(disabled);
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    listComponent.setDividerLine(c, stroke);
  }

  /**
   * Sets a callback to be called when edit modes starts and stops. When editing
   * starts the callback will be called with canceled set to false and the
   * return value of this widget. WHen editing stops the canceled value will be
   * true
   *
   * @param cb
   *          the callback to be called to be notified about editing
   *          starts/stops
   */
  public void setEditModeNotifier(iFunctionCallback cb) {}

  @Override
  public void setFromHTTPFormValue(Object value) {
    switch(submitValueType) {
      case ListBox.CSubmitValue.selected_index :
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

      case ListBox.CSubmitValue.selected_linked_data :
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

  @Override
  public void setHandleFirstFocusSelection(boolean handle) {
    listComponent.setHandleFirstFocusSelection(handle);
  }

  /**
   * Sets a hyperlink listener on list that supports hyperlinks
   *
   * @param l
   *          the listener
   */
  public void setHyperlinkListener(iHyperlinkListener l) {
    ListHelper.installItemLinkListener(listComponent.getListComponent(), l);
  }

  public void setItemDescription(Column itemDescription) {
    this.itemDescription = itemDescription;
    getItemRenderer().setItemDescription(itemDescription);

    if ((itemDescription != null) && (itemDescription.getFont() != null)) {
      dataComponent.setFont(itemDescription.getFont());
      formComponent.setFont(itemDescription.getFont());
    }

    if (listModel != null) {
      listModel.setColumnDescription(itemDescription);
    }
  }

  public void setItems(final Collection<? extends RenderableDataItem> items) {
    if (listComponent != null) {
      if (!Platform.isUIThread()) {
        Runnable r = new Runnable() {
          @Override
          public void run() {
            if ((listComponent != null) &&!isDisposed()) {
              listComponent.setAll(items);
            }
          }
        };

        Platform.invokeLater(r);

        return;
      }

      if (listComponent != null) {
        listComponent.setAll(items);
      }
    }
  }

  @Override
  public void setListSelectable(boolean selectable) {
    listComponent.setListSelectable(selectable);
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    listComponent.setListSelectionType(type);
  }

  @Override
  public void setMinRowHeight(int min) {
    listComponent.setMinRowHeight(min);
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    listComponent.setMinimumVisibleRowCount(rows);
  }

  /**
   * Sets a widget to use to edit row items. This widget will be displayed when
   * the user swipes on a row. THe widget can be a button or a container
   * containing buttons (e.g. a toolbar or form)
   *
   * @param widget
   *          the widget to use as a row item editor
   * @param centerVertically
   *          true to center the component vertically; false to have the
   *          component fill the rows vertical space
   */
  public void setRowEditingWidget(iWidget widget, boolean centerVertically) {}

  @Override
  public void setRowHeight(int height) {
    listComponent.setRowHeight(height);
  }

  @Override
  public void setSelectedIndex(int index) {
    listComponent.setSelectedIndex(index);
  }

  @Override
  public void setSelectedIndexes(int[] indices) {
    if (indices == null) {
      clearSelection();
    } else {
      listComponent.setSelectedIndexes(indices);
    }
  }

  @Override
  public void setSelectedItem(RenderableDataItem value) {
    listComponent.setSelectedItem(value);
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    this.selectionMode = selectionMode;
    selectAllAllowed   = false;

    boolean selectable = true;

    switch(selectionMode) {
      case BLOCK :
      case MULTIPLE :
        selectAllAllowed = true;

        break;

      case NONE :
        selectable = false;

        break;

      default :
        break;
    }

    setListSelectable(selectable);
    listComponent.setSelectionMode(selectionMode);
  }

  @Override
  public void setShowDivider(boolean show) {
    listComponent.setShowDivider(show);
  }

  public abstract void setShowLastDivider(boolean show);

  @Override
  public void setSingleClickAction(boolean singleClick) {
    listComponent.setSingleClickAction(singleClick);
  }

  @Override
  public void setValue(Object value) {
    if (listComponent == null) {
      super.setValue(value);

      return;
    }

    if (value == null) {
      clearSelection();
    } else if (value instanceof RenderableDataItem) {
      setSelectedItem((RenderableDataItem) value);
    } else if (value instanceof int[]) {
      setSelectedIndexes((int[]) value);
    } else if (value instanceof Object[]) {
      setSelectedIndexes(RenderableDataItem.findValuesEx(listComponent, (Object[]) value));
    } else {
      setSelectedIndex(RenderableDataItem.findValueEx(listComponent, value));
    }
  }

  @Override
  public void setVisibleRowCount(int rows) {
    listComponent.setVisibleRowCount(rows);
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return listComponent.getAlternatingRowColor();
  }

  @Override
  public int getAnchorSelectionIndex() {
    return listComponent.getAnchorSelectionIndex();
  }

  public int getEditModeMarkCount() {
    return listModel.editModeGetMarkCount();
  }

  public int[] getEditModeMarkedIndices() {
    return listModel.editModeGetMarkedIndices();
  }

  public RenderableDataItem[] getEditModeMarkedItems() {
    return listModel.editModeGetMarkedItems();
  }

  /**
   * Gets the current row being edited
   *
   * @return the current row being edited or -1 if no row is being edited
   */
  public int getEditingRow() {
    return -1;
  }

  @Override
  public int getFirstVisibleIndex() {
    return listComponent.getFirstVisibleIndex();
  }

  @Override
  public Object getHTTPFormValue() {
    if (!hasSelection()) {
      return null;
    }

    switch(submitValueType) {
      case ListBox.CSubmitValue.selected_index :
      case ListBox.CSubmitValue.checked_index :
        if (!selectAllAllowed) {
          return getSelectedIndex();
        }

        return getSelectedIndexes();

      case ListBox.CSubmitValue.selected_linked_data :
      case ListBox.CSubmitValue.checked_linked_data :
        return this.getSelectionData();

      case ListBox.CSubmitValue.selected_value_text :
        if (!selectAllAllowed) {
          return this.getSelectionAsString();
        }

        return getSelectionAsString();

      default :
        if (!selectAllAllowed) {
          return this.getSelection();
        }

        return getSelections();
    }
  }

  @Override
  public iPlatformIcon getIcon() {
    if (displayIcon != null) {
      return displayIcon;
    }

    RenderableDataItem item = this.getSelectedItem();

    if ((item != null) && (item.getIcon() != null)) {
      return item.getIcon();
    }

    if (this.itemDescription != null) {
      return itemDescription.getIcon();
    }

    return null;
  }

  /**
   * Gets the item at the specified location
   *
   * @param p
   *          the point
   *
   * @return the item at the specified location
   */
  public RenderableDataItem getItemAt(UIPoint p) {
    int row = this.getRowIndexAt(p);

    return (row == -1)
           ? null
           : getItem(row);
  }

  @Override
  public int getItemCount() {
    return listComponent.size();
  }

  public Column getItemDescription() {
    return itemDescription;
  }

  @Override
  public iPlatformItemRenderer getItemRenderer() {
    return listComponent.getItemRenderer();
  }

  /**
   * Gets the last row that was edited. This value is preserver as long as
   * changes have not been made to the data model. Use this method when you
   * actions that are triggered from a custom row editor
   *
   * @return the last row that was edited or the current row being edited (of a
   *         row is currently being edited)
   */
  public int getLastEditedRow() {
    return -1;
  }

  @Override
  public int getLastVisibleIndex() {
    return listComponent.getLastVisibleIndex();
  }

  @Override
  public iPlatformComponent getListComponent() {
    return listComponent.getListComponent();
  }

  @Override
  public SelectionType getListSelectionType() {
    return listComponent.getListSelectionType();
  }

  @Override
  public int getMaxSelectionIndex() {
    return listComponent.getMaxSelectionIndex();
  }

  @Override
  public int getMinSelectionIndex() {
    return listComponent.getMinSelectionIndex();
  }

  @Override
  public int getMinimumVisibleRowCount() {
    return listComponent.getMinimumVisibleRowCount();
  }

  /**
   * Gets the location for a component overlay. The location is calculated to be
   * left aligned
   *
   * @param row
   *          the row to get the overlay location for
   * @param top
   *          true for the overlay to be at the top of the component; false for
   *          at the bottom
   *
   * @return the location for a popup
   */
  public UIPoint getOverlayLocation(int row, boolean top) {
    UIRectangle rect = listComponent.getRowBounds(row, row);
    UIPoint     p    = getDataComponent().getLocationOnScreen();

    p.x += rect.x;
    p.y = (int) rect.y;

    return p;
  }

  public UIPoint getPopupLocation(int index) {
    UIRectangle rect = listComponent.getRowBounds(index, index);
    UIPoint     p    = getDataComponent().getLocationOnScreen();

    p.x += rect.x;
    p.y = (int) (rect.y + rect.height);

    return p;
  }

  @Override
  public int getPopupMenuIndex() {
    return listComponent.getPopupMenuIndex();
  }

  @Override
  public RenderableDataItem getPopupMenuItem() {
    return getSelectedItem();
  }

  @Override
  public int getPreferredHeight(int row) {
    return listComponent.getPreferredHeight(row);
  }

  @Override
  public UIRectangle getRowBounds(int row0, int row1) {
    return listComponent.getRowBounds(row0, row1);
  }

  /**
   * Gets the number of rows in the list
   *
   * @return the number of rows in the list
   */
  public int getRowCount() {
    return listComponent.size();
  }

  @Override
  public int getRowHeight() {
    return listComponent.getRowHeight();
  }

  /**
   * Get the index of the row at the specified location within the list
   *
   * @param p
   *          the point
   * @return the row index or -1
   */
  public int getRowIndexAt(UIPoint p) {
    return (p == null)
           ? -1
           : getRowIndexAt(p.x, p.y);
  }

  /**
   * Get the index of the row at the specified location within the list
   *
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   * @return the row index or -1
   */
  @Override
  public int getRowIndexAt(float x, float y) {
    return listComponent.getRowIndexAt(x, y);
  }

  @Override
  public List<RenderableDataItem> getRows() {
    return listComponent.getRows();
  }

  @Override
  public int getSelectedIndex() {
    return listComponent.getSelectedIndex();
  }

  @Override
  public int getSelectedIndexCount() {
    return listComponent.getSelectedIndexCount();
  }

  @Override
  public int[] getSelectedIndexes() {
    return listComponent.getSelectedIndexes();
  }

  @Override
  public RenderableDataItem getSelectedItem() {
    return listComponent.getSelectedItem();
  }

  @Override
  public Object getSelection() {
    if (selectAllAllowed) {    // multiple selection
      return getSelections();
    }

    return getSelectedItem();
  }

  @Override
  public String getSelectionAsString() {
    RenderableDataItem item = getSelectedItem();

    return (item == null)
           ? null
           : item.toString();
  }

  @Override
  public Object getSelectionData() {
    RenderableDataItem item = getSelectedItem();

    return (item == null)
           ? null
           : item.getLinkedData();
  }

  public SelectionMode getSelectionMode() {
    return selectionMode;
  }

  @Override
  public Object[] getSelections() {
    return DataItemCollection.getSelections(listComponent, -1);
  }

  @Override
  public String[] getSelectionsAsStrings() {
    return listComponent.getSelectionsAsStrings();
  }

  /**
   * Returns a string array of the linked data for all of the selected rows, in
   * increasing order.
   *
   * @return a string array of the linked data for all of the selected rows, or
   *         null if there are no selections
   */
  public String[] getSelectionsDataAsStrings() {
    return DataItemCollection.getSelectionsDataAsStrings(listComponent, -1);
  }

  @Override
  public int getVisibleRowCount() {
    return listComponent.getVisibleRowCount();
  }

  @Override
  public boolean hasSelection() {
    return listComponent.hasSelection();
  }

  @Override
  public boolean hasValue() {
    return listComponent.size() > 0;
  }

  @Override
  public boolean isChangeEventsEnabled() {
    return listComponent.isChangeEventsEnabled();
  }

  @Override
  public boolean isChangeSelColorOnLostFocus() {
    return listComponent.isChangeSelColorOnLostFocus();
  }

  @Override
  public boolean isDataEventsEnabled() {
    return listComponent.isDataEventsEnabled();
  }

  /**
   * Gets whether or not is the list is currently in editing mode
   *
   * @return true if editing; false otherwise
   */
  @Override
  public boolean isEditing() {
    return listComponent.isEditing();
  }

  @Override
  public boolean isHandleFirstFocusSelection() {
    return listComponent.isHandleFirstFocusSelection();
  }

  @Override
  public boolean isListSelectable() {
    return listComponent.isListSelectable();
  }

  @Override
  public boolean isRowSelected(int row) {
    return listComponent.isRowSelected(row);
  }

  @Override
  public boolean isRowSelected(RenderableDataItem item) {
    return listComponent.isRowSelected(item);
  }

  @Override
  public boolean isSubmittable() {
    return listComponent.hasSelection();
  }

  @Override
  public boolean isTabular() {
    return false;
  }

  @Override
  protected iScrollerSupport getScrollerSupport() {
    return listComponent;
  }

  protected RenderableDataItem createDefaultSectionProrotype() {
    RenderableDataItem di = new RenderableDataItem();

    di.setSelectable(false);
    di.setFont(getFont().deriveBold());
    di.setBackground(new UIColor(222, 222, 222, 128));

    return di;
  }

  /**
   * Configures the listbox
   *
   * @param cfg
   *          the listbox's configuration
   */
  protected void configureEx(ListBox cfg) {
    createModelAndComponents(cfg);
    configureEx(cfg, true, true, false);

    if (!cfg.focusPainted.spot_hasValue() ||!cfg.focusPainted.booleanValue()) {
      formComponent.setFocusPainted(true);
    }

    this.setSubItems(listModel);
    listComponent.setDeselectEventsDisabled(!cfg.deselectEventsEnabled.booleanValue());
    listComponent.setMinimumVisibleRowCount(cfg.minVisibleRowCount.intValue());

    if (cfg.visibleRowCount.spot_valueWasSet()) {
      listComponent.setVisibleRowCount(cfg.visibleRowCount.intValue());
    } else {
      listComponent.setVisibleRowCount(TableHelper.getDefaultPreferredRows());
    }

    if (cfg.getItemDescription() != null) {
      Column c = createColumn(cfg.getItemDescription());

      if (cfg.autoSizeRowsToFit.booleanValue() &&!cfg.getItemDescription().wordWrap.spot_valueWasSet()) {
        c.wordWrap = true;
      }

      setItemDescription(c);
    }

    listModel.setWidget(this);

    if (cfg.alternatingHighlightColor.spot_valueWasSet()) {
      setAlternatingRowColor(ColorUtils.getColor(cfg.alternatingHighlightColor.getValue()));
    }

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

    if (cfg.indexForFiltering.booleanValue()) {
      listModel.setUseIndexForFiltering(cfg.indexForFiltering.booleanValue());
      addIndexToList = "true".equals(cfg.indexForFiltering.spot_getAttribute("addIndexSectionsToList"));
      pb             = ColorUtils.configure(this, cfg.getIndexSectionPainter(), null);

      if (pb != null) {
        prototypeSectionItem = new RenderableDataItem();
        prototypeSectionItem.setSelectable(false);
        prototypeSectionItem.setComponentPainter(pb.getCachedComponentPainter());
        prototypeSectionItem.setFont((pb.getFont() != null)
                                     ? pb.getFont()
                                     : getFont());
        prototypeSectionItem.setForeground(pb.getForegroundColor());
      } else {
        prototypeSectionItem = createDefaultSectionProrotype();
      }

      float h = FontUtils.getFontHeight(prototypeSectionItem.getFont(), true);

      if (prototypeSectionItem.getComponentPainter() != null) {
        iPlatformBorder b = prototypeSectionItem.getComponentPainter().getBorder();

        if (b != null) {
          UIInsets in = b.getBorderInsets(new UIInsets());

          h += in.top + in.bottom;
        }
      }

      h += ScreenUtils.PLATFORM_PIXELS_4;
      prototypeSectionItem.setHeight(UIScreen.snapToSize(h));
    }

    boolean showDivider = Platform.isTouchableDevice();

    if (cfg.showDividerLine.spot_valueWasSet()) {
      showDivider = cfg.showDividerLine.booleanValue();
    }

    if (showDivider) {
      listComponent.setShowDivider(true);

      UIColor c = UIColorHelper.getColor(cfg.dividerLineColor.getValue());

      if (c == null) {
        c = ColorUtils.getListDividerColor();
      }

      setDividerLine(c, UIStroke.getStroke(cfg.dividerLineStyle.stringValue()));

      if ("false".equals(cfg.showDividerLine.spot_getAttribute("showLastLine"))) {
        setShowLastDivider(false);
      }
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

      case ListBox.CSelectionMode.single_auto :
        setSelectionMode(SelectionMode.SINGLE);
        listComponent.setAutoHilight(true);

        break;

      default :
        setSelectionMode(SelectionMode.SINGLE);

        break;
    }

    configureSelectionModelGroup(cfg.selectionGroupName, new Object());

    if (cfg.singleClickActionEnabled.spot_valueWasSet()) {
      listComponent.setSingleClickAction(cfg.singleClickActionEnabled.booleanValue());
    }

    String s = null;

    if (cfg.rowHeight.spot_valueWasSet()) {
      s = cfg.rowHeight.getValue();
    }

    if (s == null) {
      s = PlatformHelper.getDefaultRowHeight();
    }

    boolean autoEnd      = "true".equals(cfg.editingMode.spot_getAttribute("autoEnd"));
    boolean allowSwiping = "true".equals(cfg.editingMode.spot_getAttribute("allowSwiping"));

    switch(cfg.editingMode.intValue()) {
      case ListBox.CEditingMode.reordering :
        setEditingMode(EditingMode.REORDERING, autoEnd, allowSwiping);

        break;

      case ListBox.CEditingMode.selection :
        setEditingMode(EditingMode.SELECTION, autoEnd, allowSwiping);

        break;

      case ListBox.CEditingMode.deleting :
        setEditingMode(EditingMode.DELETEING, autoEnd, allowSwiping);

        break;

      case ListBox.CEditingMode.reordering_and_selection :
        setEditingMode(EditingMode.REORDERING_AND_SELECTION, autoEnd, allowSwiping);

        break;

      case ListBox.CEditingMode.reordering_and_deleting :
        setEditingMode(EditingMode.REORDERING_AND_DELETEING, autoEnd, allowSwiping);

        break;

      default :
        setEditingMode(EditingMode.NONE, autoEnd, allowSwiping);

        break;
    }

    Margin m = cfg.getContentPadding();

    if (m != null) {
      listComponent.getItemRenderer().setInsets(m.getInsets());
    }

    setRowHeight(ScreenUtils.toPlatformPixelHeight(s, dataComponent, 400, true));

    if (cfg.autoSizeRowsToFit.booleanValue()) {
      setAutoSizeRowsToFit(true);
    }

    listComponent.setHandleFirstFocusSelection(cfg.handleFirstFocusSelection.booleanValue());
    initiallySelectedIndex = cfg.selectedIndex.intValue();
    submitValueType        = cfg.submitValue.intValue();
  }

  protected void configureSelectionModelGroup(SPOTPrintableString selectionGroupName, Object selectionModel) {
    String s = selectionGroupName.getValue();

    if ((s != null) && (s.length() > 0)) {
      int    pos = -1;
      String p   = selectionGroupName.spot_getAttribute("position");

      if (p != null) {
        pos = SNumber.intValue(p);
      }

      if (getFormViewer() == null) {
        selectionModelGroup = getAppContext().getWindowViewer().addToSelectionGroup(s, this, selectionModel, pos);
      } else {
        selectionModelGroup = getFormViewer().addToSelectionGroup(s, this, selectionModel, pos);
      }

      this.selectionModel = selectionModel;
    }
  }

  protected iToolBar createEditingToolbarIfNecessary(ListBox cfg) {
    boolean editable = false;

    switch(cfg.editingMode.intValue()) {
      case ListBox.CEditingMode.selection :
      case ListBox.CEditingMode.reordering_and_selection :
        editable = true;

        break;

      default :
        break;
    }

    if (!editable) {
      editable = "true".equals(cfg.editingMode.spot_getAttribute("alwaysCreateToolbar"));
    }

    iToolBar tb = null;

    if (editable) {
      tb = getAppContext().getWindowManager().createToolBar(true);
      tb.setSretchButtonsToFillSpace(true);

      BorderPanel bp;

      if (formComponent instanceof BorderPanel) {
        bp = (BorderPanel) formComponent;

        iPlatformComponent c = bp.getBottomView();

        if (c != null) {
          BorderPanel bp2 = new BorderPanel(this);

          bp2.setCenterView(c);
          bp = bp2;
        }

        bp.setBottomView(tb.getComponent());
      } else {
        bp = new BorderPanel(this);
        bp.setCenterView(formComponent);
        bp.setBottomView(tb.getComponent());
        formComponent = bp;
      }

      tb.getComponent().setBorder(new UIMatteBorder(ScreenUtils.PLATFORM_PIXELS_1, 0, 0, 0,
              UILineBorder.getDefaultDisabledColor()));
    }

    return tb;
  }

  protected abstract void createModelAndComponents(Viewer vcfg);

  @Override
  protected void finishedLoadingEx() {
    if (isDisposed()) {
      return;
    }

    handleInitialStuff();
    super.finishedLoadingEx();
  }

  @Override
  protected void handleCustomProperties(Widget cfg, Map<String, Object> properties) {
    super.handleCustomProperties(cfg, properties);

    Object o = properties.get("wholeViewFling");

    if (Boolean.TRUE.equals(o) || "true".equals(o)) {
      setWholeViewFling(true);
    }

    o = properties.get("flingThreshold");

    if (o != null) {
      setFlingThreshold((o instanceof Number)
                        ? ((Number) o).intValue()
                        : SNumber.intValue(o.toString()));
    }

    o = properties.get("selectFlinged");

    if (Boolean.TRUE.equals(o) || "true".equals(o)) {
      setSelectFlinged(true);
    }
  }

  /**
   * Handles initial selection after the widget has ben configured and populated
   */
  protected void handleInitialStuff() {
    refreshItems();

    if (itemAttributes != null) {
      handleSelections(listComponent, itemAttributes);

      if ((itemAttributes.check == null)
          && ((getFormViewer() == null) ||!getFormViewer().isRetainInitialWidgetValues())) {
        itemAttributes = null;
      }
    }

    if (getSelectedIndex() == -1) {
      if ((initiallySelectedIndex > -1) && (initiallySelectedIndex < this.getItemCount())) {
        this.setSelectedIndex(initiallySelectedIndex);
      }
    }
  }

  /**
   * Handles data import when a drop event handler is not installed.
   *
   * @param t
   *          the transferable
   * @param drop
   *          the drop information
   *
   * @return true if data was imported; false otherwise
   */
  @Override
  protected boolean importDataEx(final iTransferable t, final DropInformation drop) {
    return ListHelper.importData(this, this, t, drop);
  }

  @Override
  protected void initializeListeners(aWidgetListener l) {
    super.initializeListeners(l);

    if (l != null) {
      if (l.isChangeEventEnabled()) {
        listComponent.addSelectionChangeListener(l);
      }
    }
  }

  protected List<RenderableDataItem> updateListWithFilteringIndex(FilterableList<RenderableDataItem> list) {
    CharacterIndex          ci        = list.getFilteringIndex();
    Map<Character, Integer> indexMap  = ci.getIndexMap();
    Iterator                it        = indexMap.entrySet().iterator();
    int                     len       = indexMap.size();
    int                     i         = 0;
    ObjectHolder            a[]       = new ObjectHolder[len];
    RenderableDataItem      prototype = prototypeSectionItem;

    while(it.hasNext()) {
      Entry e = (Entry) it.next();

      a[i++] = new ObjectHolder(e.getKey(), e.getValue());
    }

    Arrays.sort(a, new Comparator() {
      @Override
      public int compare(Object o1, Object o2) {
        return ((Integer) ((ObjectHolder) o1).value).compareTo((Integer) ((ObjectHolder) o2).value);
      }
    });
    list.setUseIndexForFiltering(false);    // disable index update

    while(len > 0) {
      ObjectHolder       oh   = a[--len];
      RenderableDataItem item = prototype.copy();

      item.setValue(oh.type);
      list.add((Integer) oh.value, item);
    }

    list.setUseIndexForFiltering(true);

    return list;
  }

  protected void setEditingMode(EditingMode mode, boolean autoEnd, boolean allowSwiping) {}

  ;
  protected void setFlingThreshold(int threshold) {}

  /**
   * Sets the list component for the widget
   *
   * @param comp
   *          the list component for the widget
   */
  protected void setListComponent(iPlatformListHandler comp) {
    formComponent = comp.getContainerComponent();
    dataComponent = comp.getListComponent();
    listComponent = comp;
  }

  protected void setSelectFlinged(boolean select) {}

  protected void setWholeViewFling(boolean wholeViewFling) {}

  /**
   * Returns the temporary list. If it does not exist then it is created
   *
   * @return the temporary list.
   */
  protected List<RenderableDataItem> getTempList() {
    if (tempList == null) {
      if (addIndexToList) {
        FilterableList<RenderableDataItem> list = new FilterableList<RenderableDataItem>();

        list.setUseIndexForFiltering(true);
        tempList = list;
      } else {
        tempList = new ArrayList<RenderableDataItem>();
      }
    }

    return tempList;
  }

  @Override
  protected String getWidgetAttribute(String name) {
    String s = ListHelper.getWidgetAttribute(listComponent, name);

    return (s == ListHelper.CALL_SUPER_METHOD)
           ? super.getWidgetAttribute(name)
           : s;
  }
}
