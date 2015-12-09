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
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.aTreeHandler;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iFilterableList;

import java.util.Comparator;
import java.util.List;

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

    treeHandler    = null;
    treeModel      = null;
    itemComparator = null;
  }

  @Override
  public void sort(Comparator c) {
    treeHandler.sort(c);
  }

  @Override
  protected void initialize(TableView table, Table cfg) {
    super.initialize(table, cfg);

    TreeTable     tt = (TreeTable) cfg;
    treeModel.setExpandableColumn(tt.expandableColumn.intValue());
    setTreeIcons(new PainterUtils.TwistyIcon(this,false),new PainterUtils.TwistyIcon(this,true));
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
  
  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    TreeTableView tv = (TreeTableView) tableView;
    tv.setTreeIcons(expanded, collapsed);
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
