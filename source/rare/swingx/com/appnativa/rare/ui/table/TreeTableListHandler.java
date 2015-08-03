/*
 * @(#)TreeTableListHandler.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.table;

import java.util.Collection;
import java.util.Comparator;

import com.appnativa.rare.platform.swing.ui.ListBoxListHandler;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iListView;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.util.iFilter;

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
