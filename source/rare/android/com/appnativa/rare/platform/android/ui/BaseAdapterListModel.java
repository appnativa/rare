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

import android.database.DataSetObserver;

import android.graphics.Paint;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.SectionIndexer;

import com.appnativa.rare.ui.CheckListManager;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.event.iDataModelListener;
import com.appnativa.rare.ui.iListHandler.SelectionType;
import com.appnativa.rare.ui.iPlatformItemRenderer;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharacterIndex;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BaseAdapterListModel extends BaseAdapter implements iPlatformListDataModel, SectionIndexer, Cloneable {
  DataItemListModel model;

  public UIColor getAlternatingColor() {
    return model.getAlternatingColor();
  }

  public BaseAdapterListModel(DataItemListModel listModel) {
    model = listModel;
  }

  public boolean add(RenderableDataItem object) {
    return model.add(object);
  }

  public void add(int location, RenderableDataItem object) {
    model.add(location, object);
  }

  public boolean addAll(Collection<? extends RenderableDataItem> collection) {
    return model.addAll(collection);
  }

  public boolean addAll(RenderableDataItem... a) {
    return model.addAll(a);
  }

  public boolean addAll(int location, Collection<? extends RenderableDataItem> collection) {
    return model.addAll(location, collection);
  }

  public boolean addAll(RenderableDataItem[] a, int count) {
    return model.addAll(a, count);
  }

  public void addAllEx(Collection<? extends RenderableDataItem> c) {
    model.addAllEx(c);
  }

  public void addDataModelListener(iDataModelListener l) {
    model.addDataModelListener(l);
  }

  public void addIndexToFilteredList(int index) {
    model.addIndexToFilteredList(index);
  }

  public void addToFilteredList(RenderableDataItem e) {
    model.addToFilteredList(e);
  }

  public void addToFilteredList(int index, RenderableDataItem e) {
    model.addToFilteredList(index, e);
  }

  public boolean areAllItemsEnabled() {
    return model.areAllItemsEnabled();
  }

  public int chop(int len) {
    return model.chop(len);
  }

  public void clear() {
    model.clear();
  }

  public void clearEx() {
    model.clearEx();
  }

  public Object clone() {
    return new BaseAdapterListModel((DataItemListModel) model.clone());
  }

  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return model.concat(e);
  }

  public boolean contains(Object object) {
    return model.contains(object);
  }

  public boolean containsAll(Collection<?> collection) {
    return model.containsAll(collection);
  }

  public DataItemListModel copy() {
    return model.copy();
  }

  public void dispose() {
    model.dispose();
  }

  public void editModeChangeAllMarks(boolean mark) {
    model.editModeChangeAllMarks(mark);
  }

  public void editModeChangeMark(int index, boolean mark) {
    model.editModeChangeMark(index, mark);
  }

  public void editModeClearMarks() {
    model.editModeClearMarks();
  }

  public int editModeGetMarkCount() {
    return model.editModeGetMarkCount();
  }

  public int[] editModeGetMarkedIndices() {
    return model.editModeGetMarkedIndices();
  }

  public RenderableDataItem[] editModeGetMarkedItems() {
    return model.editModeGetMarkedItems();
  }

  public boolean editModeIsItemMarked(int index) {
    return model.editModeIsItemMarked(index);
  }

  public void editModeToggleMark(int index) {
    model.editModeToggleMark(index);
  }

  public void ensureCapacity(int capacity) {
    model.ensureCapacity(capacity);
  }

  public boolean equals(Object object) {
    return model.equals(object);
  }

  public boolean filter(iFilter filter) {
    return model.filter(filter);
  }

  public boolean filter(String filter, boolean contains) {
    return model.filter(filter, contains);
  }

  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return model.filter(filter, contains, nullPasses, emptyPasses);
  }

  public int find(iFilter filter, int start) {
    return model.find(filter, start);
  }

  public int find(String filter, int start, boolean contains) {
    return model.find(filter, start, contains);
  }

  public int hashCode() {
    return model.hashCode();
  }

  public int identityIndexOf(RenderableDataItem o) {
    return model.identityIndexOf(o);
  }

  public int indexOf(Object object) {
    return model.indexOf(object);
  }

  public int indexOfLinkedData(Object value) {
    return model.indexOfLinkedData(value);
  }

  public int indexOfLinkedDataEquals(Object value) {
    return model.indexOfLinkedDataEquals(value);
  }

  public int indexOfValue(Object value) {
    return model.indexOfValue(value);
  }

  public int indexOfValueEquals(Object value) {
    return model.indexOfValueEquals(value);
  }

  public Iterator<RenderableDataItem> iterator() {
    return model.iterator();
  }

  public String join() {
    return model.join();
  }

  public String join(String sep) {
    return model.join(sep);
  }

  public int lastIndexOf(Object object) {
    return model.lastIndexOf(object);
  }

  public ListIterator<RenderableDataItem> listIterator() {
    return model.listIterator();
  }

  public ListIterator<RenderableDataItem> listIterator(int location) {
    return model.listIterator(location);
  }

  public void move(int from, int to) {
    model.move(from, to);
  }

  public void notifyDataSetChanged() {
    model.notifyDataSetChanged();
  }

  public RenderableDataItem pop() {
    return model.pop();
  }

  public void push(List<RenderableDataItem> list) {
    model.push(list);
  }

  public void push(RenderableDataItem... e) {
    model.push(e);
  }

  public boolean refilter() {
    return model.refilter();
  }

  public void refreshItems() {
    model.refreshItems();
  }

  public void registerDataSetObserver(DataSetObserver observer) {
    model.registerDataSetObserver(observer);
  }

  public RenderableDataItem remove(int location) {
    return model.remove(location);
  }

  public boolean remove(Object object) {
    return model.remove(object);
  }

  public boolean removeAll(Collection<?> collection) {
    return model.removeAll(collection);
  }

  public void removeDataModelListener(iDataModelListener l) {
    model.removeDataModelListener(l);
  }

  public void removeRows(int[] indexes) {
    model.removeRows(indexes);
  }

  public void removeRows(int firstRow, int lastRow) {
    model.removeRows(firstRow, lastRow);
  }

  public boolean retainAll(Collection<?> collection) {
    return model.retainAll(collection);
  }

  public List<RenderableDataItem> reverse() {
    return model.reverse();
  }

  public void rowChanged(int index) {
    model.rowChanged(index);
  }

  public void rowChanged(RenderableDataItem item) {
    model.rowChanged(item);
  }

  public void rowsChanged(int... index) {
    model.rowsChanged(index);
  }

  public void rowsChanged(int firstRow, int lastRow) {
    model.rowsChanged(firstRow, lastRow);
  }

  public void rowsDeleted(int firstRow, int lastRow, List<RenderableDataItem> removed) {
    model.rowsDeleted(firstRow, lastRow, removed);
  }

  public void rowsInserted(int firstRow, int lastRow) {
    model.rowsInserted(firstRow, lastRow);
  }

  public RenderableDataItem shift() {
    return model.shift();
  }

  public int size() {
    return model.size();
  }

  public List<RenderableDataItem> slice(int start) {
    return model.slice(start);
  }

  public List<RenderableDataItem> slice(int start, int end) {
    return model.slice(start, end);
  }

  public void sort() {
    model.sort();
  }

  public void sort(Comparator comparator) {
    model.sort(comparator);
  }

  public void sortEx(Comparator comparator) {
    model.sortEx(comparator);
  }

  public List<RenderableDataItem> splice(int index, int howMany) {
    return model.splice(index, howMany);
  }

  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return model.splice(index, howMany, e);
  }

  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    return model.spliceList(index, howMany, e);
  }

  public void structureChanged() {
    model.structureChanged();
  }

  public List<RenderableDataItem> subList(int start, int end) {
    return model.subList(start, end);
  }

  public void swap(int index1, int index2) {
    model.swap(index1, index2);
  }

  public Object[] toArray() {
    return model.toArray();
  }

  public <T> T[] toArray(T[] array) {
    return model.toArray(array);
  }

  public String toString() {
    return model.toString();
  }

  public String toString(Object item) {
    return model.toString(item);
  }

  public void trimTo(int size) {
    model.trimTo(size);
  }

  public void trimToSize() {
    model.trimToSize();
  }

  public boolean unfilter() {
    return model.unfilter();
  }

  public void unregisterDataSetObserver(DataSetObserver observer) {
    model.unregisterDataSetObserver(observer);
  }

  public void unshift(RenderableDataItem value) {
    model.unshift(value);
  }

  public RenderableDataItem set(int location, RenderableDataItem object) {
    return model.set(location, object);
  }

  public void setAccessoryType(String type) {
    model.setAccessoryType(type);
  }

  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return model.setAll(collection);
  }

  public void setAlternatingColor(UIColor alternatingColor) {
    model.setAlternatingColor(alternatingColor);
  }

  public void setAutoSizeRows(boolean autoSizeRows) {
    model.setAutoSizeRows(autoSizeRows);
  }

  public void setCheckListManager(CheckListManager clm) {
    model.setCheckListManager(clm);
  }

  public void setColumnDescription(Column itemDescription) {
    model.setColumnDescription(itemDescription);
  }

  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    model.setConverter(converter);
  }

  public void setEditing(boolean editing) {
    model.setEditing(editing);
  }

  public void setEventsEnabled(boolean enabled) {
    model.setEventsEnabled(enabled);
  }

  public void setFilteringEnabled(boolean filteringEnabled) {
    model.setFilteringEnabled(filteringEnabled);
  }

  public void setForegroundColor(UIColor foregroundColor) {
    model.setForegroundColor(foregroundColor);
  }

  public void setHasCustomRenderers(boolean hasCustomRenderers) {
    model.setHasCustomRenderers(hasCustomRenderers);
  }

  public void setItemRenderer(iPlatformItemRenderer lr) {
    model.setItemRenderer(lr);
  }

  public void setItems(Collection<? extends RenderableDataItem> collection) {
    model.setItems(collection);
  }

  public void setMinRowHeight(int min) {
    model.setMinRowHeight(min);
  }

  public void setRowHeight(int rowHeight) {
    model.setRowHeight(rowHeight);
  }

  public void setSelectable(boolean selectable) {
    model.setSelectable(selectable);
  }

  public void setSelectionType(SelectionType type) {
    model.setSelectionType(type);
  }

  public void setShowLastDivider(boolean showLastDivider) {
    model.setShowLastDivider(showLastDivider);
  }

  public void setUseIndexForFiltering(boolean useIndex) {
    model.setUseIndexForFiltering(useIndex);
  }

  public void setWidget(iWidget widget) {
    model.setWidget(widget);
  }

  public RenderableDataItem get(int location) {
    return model.get(location);
  }

  public Paint getAlternatingColorPaint() {
    return model.getAlternatingColorPaint();
  }

  public Column getColumnDescription() {
    return model.getColumnDescription();
  }

  public iStringConverter<RenderableDataItem> getConverter() {
    return model.getConverter();
  }

  public int getCount() {
    return model.getCount();
  }

  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return model.getDropDownView(position, convertView, parent);
  }

  public Object getElementAt(int index) {
    return model.getElementAt(index);
  }

  public Filter getFilter() {
    return model.getFilter();
  }

  public List<RenderableDataItem> getFilteredList() {
    return model.getFilteredList();
  }

  public CharacterIndex getFilteringIndex() {
    return model.getFilteringIndex();
  }

  public UIColor getForegroundColor() {
    return model.getForegroundColor();
  }

  public Object getItem(int position) {
    return model.getItem(position);
  }

  public long getItemId(int position) {
    return model.getItemId(position);
  }

  public iPlatformItemRenderer getItemRenderer() {
    return model.getItemRenderer();
  }

  public int getItemViewType(int position) {
    return model.getItemViewType(position);
  }

  public iFilter getLastFilter() {
    return model.getLastFilter();
  }

  public int getMinRowHeight() {
    return model.getMinRowHeight();
  }

  public int getPositionForSection(int section) {
    return model.getPositionForSection(section);
  }

  public UIDimension getPreferredSize(int row, UIDimension d) {
    return model.getPreferredSize(row, d);
  }

  public RenderableDataItem getRow(int index) {
    return model.getRow(index);
  }

  public int getRowHeight() {
    return model.getRowHeight();
  }

  public int getSectionForPosition(int position) {
    return model.getSectionForPosition(position);
  }

  public Object[] getSections() {
    return model.getSections();
  }

  public SelectionType getSelectionType() {
    return model.getSelectionType();
  }

  public int getSize() {
    return model.getSize();
  }

  public List<RenderableDataItem> getUnfilteredList() {
    return model.getUnfilteredList();
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    return model.getView(position, convertView, parent);
  }

  public int getViewTypeCount() {
    return model.getViewTypeCount();
  }

  public iWidget getWidget() {
    return model.getWidget();
  }

  public boolean hasStableIds() {
    return model.hasStableIds();
  }

  public boolean isAutoSizeRows() {
    return model.isAutoSizeRows();
  }

  public boolean isEditing() {
    return model.isEditing();
  }

  public boolean isEmpty() {
    return model.isEmpty();
  }

  public boolean isEnabled(int position) {
    return model.isEnabled(position);
  }

  public boolean isEventsEnabled() {
    return model.isEventsEnabled();
  }

  public boolean isFiltered() {
    return model.isFiltered();
  }

  public boolean isFilteringEnabled() {
    return model.isFilteringEnabled();
  }

  public boolean isIndexForFiltering() {
    return model.isIndexForFiltering();
  }

  public boolean isSelectable() {
    return model.isSelectable();
  }

  public boolean isShowLastDivider() {
    return model.isShowLastDivider();
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    model.setFilteredList(list, lastFilter);
  }

  @Override
  public void setRowEditingSupported(boolean supported) {
    model.setRowEditingSupported(supported);
  }

}
