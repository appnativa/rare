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

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iTableModel;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class TableModelFilterableList extends AbstractList<RenderableDataItem>
        implements iFilterableList<RenderableDataItem> {
  iTableModel model;

  public TableModelFilterableList(iTableModel tm) {
    model = tm;
  }

  @Override
  public boolean add(RenderableDataItem item) {
    model.addRow(item);

    return true;
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    int len = size();

    model.addRows(c);

    return len != size();
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    int len = size();

    model.addRows(index, c);

    return len != size();
  }

  @Override
  public void addIndexToFilteredList(int index) {
    model.filter(index);
  }

  @Override
  public void addToFilteredList(RenderableDataItem item) {
    model.addToFilteredList(item);
  }

  @Override
  public void addToFilteredList(int index, RenderableDataItem item) {
    model.addToFilteredList(index, item);
  }

  @Override
  public void clear() {
    if (size() > 0) {
      model.clearTableData();
    }
  }

  @Override
  public void move(int from, int to) {
    model.move(from, to);
  }

  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return model.concat(e);
  }

  @Override
  public void ensureCapacity(int capacity) {}

  @Override
  public boolean filter(iFilter filter) {
    return model.filter(filter);
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    return model.filter(filter, contains);
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return model.filter(filter, contains, nullPasses, emptyPasses);
  }

  @Override
  public int find(iFilter filter, int start) {
    return model.find(filter, start);
  }

  @Override
  public int find(String filter, int start, boolean contains) {
    return model.find(filter, start, contains);
  }

  public String join() {
    return join(null);
  }

  @Override
  public String join(String sep) {
    return model.join(sep);
  }

  @Override
  public RenderableDataItem pop() {
    int n = (model == null)
            ? -1
            : model.getRowCount() - 1;

    if (n < 0) {
      return null;
    }

    RenderableDataItem item = model.getRow(n);

    model.removeRow(n);

    return item;
  }

  @Override
  public void push(RenderableDataItem... e) {
    if (e != null) {
      for (RenderableDataItem v : e) {
        add(v);
      }
    }
  }

  @Override
  public boolean refilter() {
    return model.refilter();
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem row = model.getRow(index);

    model.removeRow(index);

    return row;
  }

  @Override
  public List<RenderableDataItem> reverse() {
    return model.reverse();
  }

  @Override
  public RenderableDataItem shift() {
    if ((model == null) || (model.getRowCount() == 0)) {
      return null;
    }

    RenderableDataItem item = model.getRow(0);

    model.removeRow(0);

    return item;
  }

  @Override
  public int size() {
    return model.getRowCount();
  }

  @Override
  public List<RenderableDataItem> slice(int start) {
    return model.slice(start, size());
  }

  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return model.slice(start, end);
  }

  @Override
  public void sort(Comparator comparator) {
    model.sort(comparator);
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
    return model.spliceList(index, howMany, e);
  }

  @Override
  public void swap(int index1, int index2) {
    RenderableDataItem a = get(index1);
    RenderableDataItem b = get(index2);

    set(index1, b);
    set(index2, a);

    if (index2 == (index1 + 1)) {
      model.rowsChanged(index1, index2);
    } else if (index1 == (index2 + 1)) {
      model.rowsChanged(index2, index1);
    } else {
      model.rowChanged(index1);
      model.rowChanged(index2);
    }
  }

  @Override
  public boolean unfilter() {
    return model.unfilter();
  }

  @Override
  public void unshift(RenderableDataItem value) {
    add(0, value);
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem item) {
    RenderableDataItem row = model.getRow(index);

    model.setRow(index, item);

    return row;
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    model.setConverter(converter);
  }

  @Override
  public RenderableDataItem get(int index) {
    return model.getRow(index);
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    return model.getConverter();
  }

  @Override
  public List<RenderableDataItem> getFilteredList() {
    return this;
  }

  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return model.getUnfilteredList();
  }

  @Override
  public boolean isFiltered() {
    return model.isFiltered();
  }

  @Override
  public void removeRows(int[] indexes) {
    model.removeRows(indexes);
  }

  @Override
  public iFilter getLastFilter() {
    return model.getLastFilter();
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return model.setAll(collection);
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    model.setFilteredList(list, lastFilter);
  }

  @Override
  public void trimToSize() {
    model.trimToSize();
  }
}
