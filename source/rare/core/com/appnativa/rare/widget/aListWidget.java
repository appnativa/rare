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

package com.appnativa.rare.widget;

import com.appnativa.rare.Platform;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UISelectionModelGroup;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.iActionable;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListHandler;
import com.appnativa.rare.util.DataItemCollection;
import com.appnativa.rare.util.ListHelper;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.util.iFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * A widget that allows a user to select one or more choices from a scrollable
 * list of items.
 *
 * @author Don DeCoteau
 */
public abstract class aListWidget extends aPlatformWidget implements iActionable, iListHandler {

  /** the initial selected index */
  protected int     initiallySelectedIndex = -1;
  protected UIColor alternatingColor;

  /** the description of the list items */
  protected Column itemDescription;

  /** the list component */
  protected iPlatformListHandler listComponent;

  /** the submit value type */
  protected int submitValueType;

  /** temporary list for data added via background threads */
  protected List<RenderableDataItem> tempList;

  public aListWidget() {
    this(null);
  }

  public aListWidget(iContainer parent) {
    super(parent);
  }

  @Override
  public boolean add(RenderableDataItem item) {
    listComponent.add(item);

    return true;
  }

  @Override
  public void addActionListener(iActionListener l) {
    listComponent.addActionListener(l);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    return listComponent.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    return listComponent.addAll(index, c);
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
  public void addSelectionChangeListener(iItemChangeListener l) {
    listComponent.addSelectionChangeListener(l);
  }

  @Override
  public void addSelectionIndex(int index) {
    listComponent.addSelectionIndex(index);
  }

  @Override
  public void makeSelectionVisible() {
    listComponent.makeSelectionVisible();
  }

  @Override
  public void clear() {
    if (listComponent.getItemCount() > 0) {
      if (!Platform.isUIThread()) {
        ListHelper.runLater(this, ListHelper.RunType.CLEAR);

        return;
      }

      initiallySelectedIndex = -1;
      itemAttributes         = null;
      listComponent.clear();
    }
  }

  @Override
  public void clearContents() {
    clear();
  }

  /**
   * Clears/resets the temporary list used by background threads to update the
   * widget data
   *
   * @see #addEx(RenderableDataItem)
   * @see #refreshItems()
   */
  public void clearEx() {
    if (tempList != null) {
      tempList.clear();
    }

    tempList = null;
  }

  @Override
  public void clearContextMenuIndex() {
    listComponent.clearContextMenuIndex();
  }

  @Override
  public void clearSelection() {
    listComponent.clearSelection();
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
    if (listComponent != null) {
      listComponent.dispose();
    }

    super.dispose();
    listComponent   = null;
    itemDescription = null;
  }

  @Override
  public boolean filter(iFilter filter) {
    if (super.filter(filter)) {
      this.clearSelection();
      repaint();

      return true;
    }

    return false;
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    if (listComponent.filter(filter, contains, nullPasses, emptyPasses)) {
      this.clearSelection();
      repaint();

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

  public void upArrow() {
    if (getRowCount() > 0) {
      int row = getSelectedIndex();

      UISelectionModelGroup.selectPreviousRow(listComponent, row);
    }
  }

  public void downArrow() {
    if (getRowCount() > 0) {
      int row = getSelectedIndex();

      UISelectionModelGroup.selectNextRow(listComponent, row);
    }
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

    if (tempList != null) {
      listComponent.setAll(tempList);
      tempList.clear();
      tempList = null;
    } else {
      listComponent.refreshItems();
    }
  }

  @Override
  public RenderableDataItem remove(int index) {
    return listComponent.remove(index);
  }

  @Override
  public void repaintRow(int row) {
    listComponent.repaintRow(row);
  }

  @Override
  public boolean remove(Object item) {
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
  public void selectAll() {
    listComponent.selectAll();
  }

  @Override
  public void sizeRowsToFit() {
    listComponent.sizeRowsToFit();
  }

  @Override
  public void sort(boolean descending) {
    listComponent.sort(descending
                       ? SubItemComparator.getReverseOrderComparatorInstance()
                       : null);
  }

  @Override
  public void sort(Comparator comparator) {
    listComponent.sort(comparator);
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> c) {
    return listComponent.setAll(c);
  }

  /**
   * Sets the alternating hilight color for the list
   *
   * @param color
   *          the alternating hilight color for the list
   */
  public void setAlternatingHilightColor(UIColor color) {
    alternatingColor = color;
  }

  @Override
  public void setChangeEventsEnabled(boolean enabled) {
    listComponent.setChangeEventsEnabled(enabled);
  }

  @Override
  public void setChangeSelColorOnLostFocus(boolean change) {
    listComponent.setChangeSelColorOnLostFocus(change);
  }

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {
    listComponent.setDividerLine(c, stroke);
  }

  @Override
  public void setHandleFirstFocusSelection(boolean handle) {
    listComponent.setHandleFirstFocusSelection(handle);
  }

  @Override
  public void setListSelectable(boolean selectable) {
    listComponent.setListSelectable(selectable);
  }

  @Override
  public void setListSelectionType(SelectionType type) {
    if (listComponent != null) {
      listComponent.setListSelectionType(type);
    }
  }

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {
    listComponent.setSelectionMode(selectionMode);
  }

  @Override
  public void setMinRowHeight(int min) {
    listComponent.setMinRowHeight(min);
  }

  @Override
  public void setMinimumVisibleRowCount(int rows) {
    listComponent.setMinimumVisibleRowCount(rows);
  }

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
    listComponent.setSelectedIndexes(indices);
  }

  @Override
  public void setSelectedItem(RenderableDataItem value) {
    listComponent.setSelectedItem(value);
  }

  @Override
  public void setValue(Object value) {
    if (value == null) {
      clearSelection();
    } else if (value instanceof RenderableDataItem) {
      setSelectedItem((RenderableDataItem) value);
    } else {
      int n = indexOfValueEquals(value);

      if (n == -1) {
        n = indexOfLinkedDataEquals(value);
      }

      setSelectedIndex(n);
    }
  }

  @Override
  public void setVisibleRowCount(int rows) {
    listComponent.setVisibleRowCount(rows);
  }

  public UIColor getAlternatingHilightColor() {
    return alternatingColor;
  }

  @Override
  public int getAnchorSelectionIndex() {
    return listComponent.getAnchorSelectionIndex();
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

  public UIPoint getPopupLocation(int index) {
    UIRectangle rect = listComponent.getRowBounds(index, index);
    UIPoint     p    = getContainerComponent().getLocationOnScreen();

    p.y += rect.y + rect.height;

    return p;
  }

  @Override
  public int getContextMenuIndex() {
    return listComponent.getContextMenuIndex();
  }

  @Override
  public RenderableDataItem getContextMenuItem() {
    int n = listComponent.getContextMenuIndex();

    return (n == -1)
           ? null
           : listComponent.get(n);
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

  /**
   * Get the index of the row at the specified location within the list
   *
   * @param p
   *          the point
   * @return the row index or -1
   */
  public int getRowIndexAt(UIPoint p) {
    return getSelectedIndex();
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
    return getRowIndexAt(new UIPoint(x, y));
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
  public int[] getCheckedIndexes() {
    return listComponent.getCheckedIndexes();
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

    return listComponent.getSelectedItem();
  }

  @Override
  public String getSelectionAsString() {
    if (selectAllAllowed) {    // multiple selection
      return listComponent.getSelectionAsString();
    }

    RenderableDataItem item = listComponent.getSelectedItem();

    return (item == null)
           ? null
           : item.toString();
  }

  @Override
  public Object getSelectionData() {
    return DataItemCollection.getSelectionData(listComponent, selectAllAllowed, -1);
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
    return listComponent.hasSelection();
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
  protected void finishedLoadingEx() {
    if (isDisposed()) {
      return;
    }

    handleInitialStuff();
    super.finishedLoadingEx();
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

  @Override
  protected void initializeListeners(aWidgetListener listener) {
    super.initializeListeners(listener);

    if (actionListener == null) {
      actionListener = new iActionListener() {
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
          }
        }
      };
      listComponent.addActionListener(actionListener);
    }
  }

  /**
   * Returns the temporary list. If it does not exist then it is created
   *
   * @return the temporary list.
   */
  protected List<RenderableDataItem> getTempList() {
    if (tempList == null) {
      tempList = new ArrayList<RenderableDataItem>();
    }

    return tempList;
  }

  @Override
  protected String getWidgetAttribute(String name) {
    String s = ListHelper.getWidgetAttribute(this, listComponent, name);

    return (s == ListHelper.CALL_SUPER_METHOD)
           ? super.getWidgetAttribute(name)
           : s;
  }
}
