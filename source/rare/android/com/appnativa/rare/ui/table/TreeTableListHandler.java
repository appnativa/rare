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
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.util.iFilter;

import java.util.Collection;
import java.util.Comparator;

public class TreeTableListHandler extends TableListHandler {
  DataItemTreeModel treeModel;

  public TreeTableListHandler(TreeTableComponent table, iPlatformListDataModel model, DataItemTreeModel tm) {
    super(table, model);
    treeModel = tm;
  }

  @Override
  public void makeSelectionVisible() {
    tableComponent.makeSelectionVisible();
  }

  public boolean add(RenderableDataItem child) {
    return treeModel.add(child);
  }

  public void add(int index, RenderableDataItem element) {
    treeModel.add(index, element);
  }

  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    return treeModel.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    return treeModel.addAll(index, c);
  }

  public void clear() {
    final boolean empty = isEmpty();

    treeModel.clear();

    if (!empty) {
      getListView().setAdapter(getListView().getAdapter());
    }
  }

  public boolean filter(iFilter filter) {
    return treeModel.filter(filter);
  }

  public boolean filter(String filter, boolean contains) {
    return treeModel.filter(filter, contains);
  }

  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return treeModel.filter(filter, contains, nullPasses, emptyPasses);
  }

  public void refreshItems() {
    treeModel.refreshItems();
  }

  public void sort(Comparator comparator) {
    treeModel.sort(comparator);
  }

  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return treeModel.setAll(collection);
  }
}
