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

import com.appnativa.rare.platform.swing.ui.ListBoxListHandler;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.util.iFilter;

import java.util.Collection;
import java.util.Comparator;

public class TreeTableListHandler extends ListBoxListHandler {
  DataItemTreeModel treeModel;

  public TreeTableListHandler(iListView view, iPlatformListDataModel model, DataItemTreeModel tm) {
    super(view, model);
    treeModel = tm;
  }

  @Override
  public boolean add(RenderableDataItem child) {
    return treeModel.add(child);
  }

  @Override
  public void add(int index, RenderableDataItem element) {
    treeModel.add(index, element);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    return treeModel.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    return treeModel.addAll(index, c);
  }

  @Override
  public void clear() {
    treeModel.clear();
  }

  @Override
  public void dispose() {
    super.dispose();
    treeModel = null;
  }

  @Override
  public boolean filter(iFilter filter) {
    return treeModel.filter(filter);
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    return treeModel.filter(filter, contains);
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return treeModel.filter(filter, contains, nullPasses, emptyPasses);
  }

  @Override
  public void refreshItems() {
    treeModel.refreshItems();
  }

  @Override
  public void sort(Comparator comparator) {
    treeModel.sort(comparator);
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return treeModel.setAll(collection);
  }
}
