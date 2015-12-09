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

import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.ui.painter.UIScrollingEdgePainter;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class aNonListListHandler implements iPlatformListHandler {
  public aNonListListHandler() {}

  @Override
  public boolean isScrolling() {
    return false;
  }

  @Override
  public boolean isAtLeftEdge() {
    return false;
  }

  @Override
  public boolean isAtRightEdge() {
    return false;
  }

  @Override
  public boolean isAtTopEdge() {
    return false;
  }

  @Override
  public boolean isAtBottomEdge() {
    return false;
  }
  
  @Override
  public void repaintRow(int row) {
    // TODO Auto-generated method stub
  }
  @Override
  public UIPoint getContentOffset() {
    return null;
  }
  
  @Override
  public void setContentOffset(float x, float y) {
  }

  @Override
  public void setScrollingEdgePainter(UIScrollingEdgePainter painter) {}

  @Override
  public UIScrollingEdgePainter getScrollingEdgePainter() {
    return null;
  }

  @Override
  public void scrollToBottomEdge() {}

  @Override
  public void scrollToLeftEdge() {}

  @Override
  public void scrollToRightEdge() {}

  @Override
  public void scrollToTopEdge() {}

  @Override
  public void moveUpDown(boolean up, boolean block) {}

  @Override
  public void moveLeftRight(boolean left, boolean block) {}

  @Override
  public boolean add(RenderableDataItem arg0) {
    return false;
  }

  @Override
  public void add(int arg0, RenderableDataItem arg1) {}

  @Override
  public void addActionListener(iActionListener l) {}

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> arg0) {
    return false;
  }

  public boolean isSingleClickAction() {
    return false;
  }

  @Override
  public boolean addAll(int arg0, Collection<? extends RenderableDataItem> arg1) {
    return false;
  }

  @Override
  public void addAll(int index, List<RenderableDataItem> items, boolean insertMode) {}

  @Override
  public void addDataModelListener(iDataModelListener l) {}

  @Override
  public boolean isKeepSelectionVisible() {
    return false;
  }

  @Override
  public void setKeepSelectionVisible(boolean keepSelectionVisible) {}

  @Override
  public void addIndexToFilteredList(int index) {}

  @Override
  public void addSelectionChangeListener(iItemChangeListener l) {}

  @Override
  public void addSelectionIndex(int index) {}

  @Override
  public void addToFilteredList(RenderableDataItem e) {}

  @Override
  public void addToFilteredList(int index, RenderableDataItem e) {}

  @Override
  public void clear() {}

  @Override
  public void clearContextMenuIndex() {}

  @Override
  public void clearSelection() {}

  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return null;
  }

  @Override
  public boolean contains(Object arg0) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> arg0) {
    return false;
  }

  @Override
  public void copySelectedItems(int index, boolean insertMode, boolean delete) {}

  @Override
  public Object deleteSelectedData(boolean returnData) {
    return null;
  }

  @Override
  public void dispose() {}

  @Override
  public void ensureCapacity(int capacity) {}

  @Override
  public boolean filter(iFilter filter) {
    return false;
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    return false;
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return false;
  }

  @Override
  public int find(iFilter filter, int start) {
    return -1;
  }

  @Override
  public int find(String filter, int start, boolean contains) {
    return -1;
  }

  @Override
  public void fireActionForSelected() {}

  @Override
  public int indexOf(Object arg0) {
    return 0;
  }

  @Override
  public Iterator<RenderableDataItem> iterator() {
    return null;
  }

  @Override
  public String join(String sep) {
    return "";
  }

  @Override
  public int lastIndexOf(Object arg0) {
    return 0;
  }

  @Override
  public ListIterator<RenderableDataItem> listIterator() {
    return null;
  }

  @Override
  public ListIterator<RenderableDataItem> listIterator(int location) {
    return null;
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {}

  @Override
  public void move(int from, int to) {}

  @Override
  public RenderableDataItem pop() {
    return null;
  }

  @Override
  public void push(RenderableDataItem... e) {}

  @Override
  public boolean refilter() {
    return false;
  }

  @Override
  public void refreshItems() {}

  @Override
  public RenderableDataItem remove(int location) {
    return null;
  }

  @Override
  public boolean remove(Object object) {
    return false;
  }

  @Override
  public void makeSelectionVisible() {}

  @Override
  public void removeActionListener(iActionListener l) {}

  @Override
  public boolean removeAll(Collection<?> arg0) {
    return false;
  }

  @Override
  public void removeDataModelListener(iDataModelListener l) {}

  @Override
  public void removeRows(int[] indexes) {}

  @Override
  public void removeSelection(int index) {}

  @Override
  public void removeSelectionChangeListener(iItemChangeListener l) {}

  @Override
  public boolean retainAll(Collection<?> arg0) {
    return false;
  }

  @Override
  public List<RenderableDataItem> reverse() {
    return null;
  }

  @Override
  public void scrollRowToTop(int row) {}

  @Override
  public void scrollRowToBottom(int row) {}

  @Override
  public void scrollRowToVisible(int row) {}

  @Override
  public void selectAll() {}

  @Override
  public RenderableDataItem shift() {
    return null;
  }

  public void setShowLastDivider(boolean show) {}

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void sizeRowsToFit() {}

  @Override
  public List<RenderableDataItem> slice(int start) {
    return null;
  }

  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return null;
  }

  @Override
  public void sort(Comparator comparator) {}

  @Override
  public List<RenderableDataItem> splice(int index, int howMany) {
    return null;
  }

  @Override
  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return null;
  }

  @Override
  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    return null;
  }

  @Override
  public List<RenderableDataItem> subList(int start, int end) {
    return null;
  }

  @Override
  public void swap(int index1, int index2) {}

  @Override
  public Object[] toArray() {
    return null;
  }

  @Override
  public <T> T[] toArray(T[] array) {
    return null;
  }

  @Override
  public boolean unfilter() {
    return false;
  }

  @Override
  public void unshift(RenderableDataItem value) {}

  @Override
  public RenderableDataItem set(int location, RenderableDataItem object) {
    return null;
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return false;
  }

  @Override
  public void setAlternatingRowColor(UIColor color) {}

  @Override
  public void setAutoHilight(boolean autoHilight) {}

  public void setAutoSelect(boolean autoSelect) {}

  @Override
  public void setChangeEventsEnabled(boolean enabled) {}

  @Override
  public void setChangeSelColorOnLostFocus(boolean change) {}

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {}

  @Override
  public void setDataEventsEnabled(boolean enabled) {}

  @Override
  public void setDeselectEventsDisabled(boolean disabled) {}

  @Override
  public void setDividerLine(UIColor c, UIStroke stroke) {}

  @Override
  public void setHandleFirstFocusSelection(boolean handle) {}

  @Override
  public void setListSelectable(boolean selectable) {}

  @Override
  public void setListSelectionType(SelectionType type) {}

  @Override
  public void setMinRowHeight(int min) {}

  @Override
  public void setMinimumVisibleRowCount(int rows) {}

  @Override
  public void setRowHeight(int height) {}

  @Override
  public void setSelectedIndex(int index) {}

  @Override
  public void setSelectedIndexes(int[] indices) {}

  @Override
  public void setSelectedItem(RenderableDataItem value) {}

  @Override
  public void setShowDivider(boolean show) {}

  @Override
  public void setSingleClickAction(boolean singleClick) {}

  @Override
  public void setSelectionMode(SelectionMode selectionMode) {}

  @Override
  public void setVisibleRowCount(int rows) {}

  @Override
  public RenderableDataItem get(int arg0) {
    return null;
  }

  @Override
  public UIColor getAlternatingRowColor() {
    return null;
  }

  @Override
  public int getAnchorSelectionIndex() {
    return -1;
  }

  @Override
  public iPlatformComponent getContainerComponent() {
    return getListComponent();
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    return null;
  }

  @Override
  public List<RenderableDataItem> getFilteredList() {
    return null;
  }

  @Override
  public int getFirstVisibleIndex() {
    return -1;
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public Column getItemDescription() {
    return null;
  }

  @Override
  public iFilter getLastFilter() {
    return null;
  }

  @Override
  public int getLastVisibleIndex() {
    return -1;
  }

  @Override
  public SelectionType getListSelectionType() {
    return null;
  }

  @Override
  public int getMaxSelectionIndex() {
    return -1;
  }

  @Override
  public int getMinSelectionIndex() {
    return -1;
  }

  @Override
  public int getMinimumVisibleRowCount() {
    return 0;
  }

  @Override
  public int getContextMenuIndex() {
    return -1;
  }

  @Override
  public RenderableDataItem getContextMenuItem() {
    return null;
  }

  @Override
  public int getPreferredHeight(int row) {
    return 0;
  }

  @Override
  public UIRectangle getRowBounds(int row0, int row1) {
    return null;
  }

  @Override
  public int getRowIndexAt(float x, float y) {
    return 0;
  }

  @Override
  public List<RenderableDataItem> getRows() {
    return null;
  }

  @Override
  public int getSelectedIndex() {
    return -1;
  }

  @Override
  public int getSelectedIndexCount() {
    return 0;
  }

  @Override
  public int[] getSelectedIndexes() {
    return null;
  }

  @Override
  public RenderableDataItem getSelectedItem() {
    return null;
  }

  @Override
  public Object getSelection() {
    return null;
  }

  @Override
  public String getSelectionAsString() {
    return null;
  }

  @Override
  public Object[] getSelections() {
    return null;
  }

  @Override
  public String[] getSelectionsAsStrings() {
    return null;
  }

  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return null;
  }

  @Override
  public int getVisibleRowCount() {
    return 0;
  }

  @Override
  public boolean hasSelection() {
    return false;
  }

  @Override
  public boolean isChangeEventsEnabled() {
    return false;
  }

  @Override
  public boolean isChangeSelColorOnLostFocus() {
    return false;
  }

  @Override
  public boolean isDataEventsEnabled() {
    return false;
  }

  @Override
  public boolean isEditing() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean isFiltered() {
    return false;
  }

  @Override
  public boolean isHandleFirstFocusSelection() {
    return false;
  }

  @Override
  public boolean isListSelectable() {
    return false;
  }

  @Override
  public boolean isRowSelected(int row) {
    return false;
  }

  @Override
  public boolean isRowSelected(RenderableDataItem item) {
    return false;
  }

  @Override
  public boolean isTabular() {
    return false;
  }

  @Override
  public void trimToSize() {}
}
