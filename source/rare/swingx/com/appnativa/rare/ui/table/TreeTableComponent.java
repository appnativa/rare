/*
 * @(#)TreeTableComponent.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.table;

import java.util.Comparator;
import java.util.List;

import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aTreeHandler;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iFilterableList;

public class TreeTableComponent extends TableComponent {
  SubItemComparator         itemComparator;
  aTreeHandler              treeHandler;
  DataItemTreeModelForTable treeModel;

  public TreeTableComponent(TreeTable cfg) {
    this(new TableScrollPane(new TreeTableView()), cfg);
  }

  public TreeTableComponent(TableScrollPane scrollpane, TreeTable cfg) {
    super(scrollpane, cfg);
  }

  public TreeTableComponent(TableView table, TreeTable cfg) {
    super(table, cfg);
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

    treeHandler = null;
    treeModel = null;
    itemComparator = null;
  }

  @Override
  public void sort(Comparator c) {
    treeHandler.sort(c);
  }

  @Override
  protected void initialize(TableView table, Table cfg) {
    super.initialize(table, cfg);

    TreeTable tt = (TreeTable) cfg;
    TreeTableView tv = (TreeTableView) tableView;
    tv.setShowRootHandles(tt.showRootHandles.booleanValue());
    treeModel.setExpandableColumn(tt.expandableColumn.intValue());
    treeModel.setExpandAll(tt.expandAll.booleanValue());
    tv.setParentItemsSelectable(tt.parentItemsSelectable.booleanValue());
  }

  @Override
  protected void resetTableEx(iFilterableList<Column> columns, iFilterableList<RenderableDataItem> rows) {
    tableHeader.setColumns(columns);
    tableModel.resetModel(columns, rows);
    treeModel.setAll(rows);
  }

  @Override
  public void sort(int col, boolean descending, boolean useLinkedData) {
    sortColumn = col;

    if (itemComparator == null) {
      itemComparator = new SubItemComparator(tableModel);
    }

    itemComparator.setOptions(col, descending);
    itemComparator.setUseLinkedData(useLinkedData);
    treeHandler.sort(itemComparator);
    this.descending = descending;
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
  protected void createTableModel() {
    super.createTableModel();

    final TreeTableView tv = (TreeTableView) tableView;

    tv.setShowRoot(false);
    treeModel = new DataItemTreeModelForTable(tv);
    treeModel.setListModel(tableModel);
    treeHandler = new aTreeHandler(tv, treeModel) {
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
        return tv.isAutoScrollOnExpansion();
      }

      @Override
      public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
        tv.setAutoScrollOnExpansion(autoScrollOnExpansion);
      }
    };
    tv.setTreeModel(treeModel);
  }
}
