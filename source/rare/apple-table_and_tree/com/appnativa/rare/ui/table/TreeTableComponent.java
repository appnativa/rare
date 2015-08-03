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

import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aTreeHandler;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilterableList;

import com.google.j2objc.annotations.WeakOuter;

import java.util.Comparator;
import java.util.List;

public class TreeTableComponent extends TableComponent {
  aTreeHandler              treeHandler;
  DataItemTreeModelForTable treeModel;
  SubItemComparator         itemComparator;

  public TreeTableComponent(TreeTable cfg) {
    this(new TreeTableView(), cfg);
  }

  public TreeTableComponent(TableView table, TreeTable cfg) {
    super(table, cfg, true);
  }

  @Override
  protected void initialize(TableView table, Table cfg) {
    super.initialize(table, cfg);

    TreeTable     tt = (TreeTable) cfg;
    TreeTableView tv = (TreeTableView) tableView;

    tv.setShowRootHandles(tt.showRootHandles.booleanValue());
    tv.setParentItemsSelectable(tt.parentItemsSelectable.booleanValue());
    tv.setToggleOnTwistyOnly(tt.parentItemsSelectable.booleanValue());
    treeModel.setExpandableColumn(tt.expandableColumn.intValue());
    treeModel.setExpandAll(tt.expandAll.booleanValue());
  }

  @Override
  public void dispose() {
    super.dispose();

    if (treeHandler != null) {
      treeHandler.dispose();
    }

    if (treeModel != null) {
      treeModel.dispose();
    }

    treeHandler    = null;
    treeModel      = null;
    itemComparator = null;
  }

  @Override
  public void sort(Comparator c) {
    treeHandler.sort(c);
  }

  @Override
  public void sort(int col, boolean descending, boolean useLinkedData) {
    sortColumn = col;

    if (itemComparator == null) {
      itemComparator = new SubItemComparator(tableModel);
    }

    itemComparator.setOptions(col, descending);
    itemComparator.setUseLinkedData(useLinkedData);
    this.descending = descending;
    treeHandler.sort(itemComparator);
    tableHeader.setSortColumn(col, descending);
  }

  public void setExpandAll(boolean expandAll) {
    treeModel.setExpandAll(expandAll);
  }

  public void setExpandableColumn(int col) {
    treeModel.setExpandableColumn(col);
  }

  @Override
  public void setWidget(iWidget widget) {
    super.setWidget(widget);
    treeModel.setWidget(widget);
  }

  public List<RenderableDataItem> getRawRows() {
    return treeModel.getRawRows();
  }

  public iTreeHandler getTreeHandler() {
    return treeHandler;
  }

  public DataItemTreeModel getTreeModel() {
    return treeModel;
  }

  @Override
  protected void resetTableEx(iFilterableList<Column> columns, iFilterableList<RenderableDataItem> rows) {
    tableHeader.setColumns(columns);
    tableModel.resetModel(columns, new FilterableList<RenderableDataItem>());
    treeModel.setListModel(tableModel);
    treeModel.setAll(rows);
  }

  @Override
  protected void createTableModel() {
    super.createTableModel();

    final TreeTableView tv = (TreeTableView) tableView;

    tv.setShowRoot(false);
    treeModel = new DataItemTreeModelForTable(tv);
    treeModel.setListModel(tableModel);
    treeHandler = new TreeHandler(tv, treeModel);
    tv.setTreeModel(treeModel);
  }

  @WeakOuter
  class TreeHandler extends aTreeHandler {
    public TreeHandler(TreeTableView tv, DataItemTreeModelForTable treeModel) {
      super(tv, treeModel);
    }

    @Override
    protected boolean hasListeners() {
      return listenerList != null;
    }

    @Override
    protected EventListenerList getEventListenerList() {
      return TreeTableComponent.this.getEventListenerList();
    }

    @Override
    public boolean isAutoScrollOnExpansion() {
      return ((TreeTableView) theTree).isAutoScrollOnExpansion();
    }

    @Override
    public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
      ((TreeTableView) theTree).setAutoScrollOnExpansion(autoScrollOnExpansion);
    }
  }
}
