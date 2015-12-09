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

import java.util.Comparator;
import java.util.List;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.platform.android.ui.view.ListViewEx;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.TreeViewEx;
import com.appnativa.rare.ui.tree.aTreeHandler;
import com.appnativa.rare.ui.tree.iTreeItem;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iFilterableList;

/**
 *
 * @author Don DeCoteau
 */
public class TreeTableComponent extends TableComponent {
  aTreeHandler                treeHandler;
  DataItemTreeModelForTable   treeModel;
  protected SubItemComparator itemComparator;

  public TreeTableComponent(ListViewEx table, Table cfg) {
    super(table, cfg, true);

    TreeTable  tt = (TreeTable) cfg;
    treeModel.setExpandableColumn(tt.expandableColumn.intValue());
    setTreeIcons(new PainterUtils.TwistyIcon(this,false), new PainterUtils.TwistyIcon(this,true));
  }
  
  public void setTreeIcons(iPlatformIcon expanded, iPlatformIcon collapsed) {
    TreeViewEx tv = (TreeViewEx) getListView();
    tv.setTreeIcons(expanded, collapsed);
  }

  public void dispose() {
    
    super.dispose();

    try {
      if (treeHandler != null) {
        treeHandler.dispose();
      }

      if (treeModel != null) {
        treeModel.dispose();
      }
    } catch(Throwable e) {
      Platform.ignoreException("dispose exception", e);
    }

    treeHandler = null;
    treeModel   = null;
  }

  public void sort(Comparator c) {
    treeHandler.sort(c);
  }

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

  protected void createTableModel() {
    super.createTableModel();

    final TreeViewEx tv = (TreeViewEx) getListView();

    tv.setShowRoot(false);
    treeModel = new DataItemTreeModelForTable(tv);
    treeModel.setListModel(tableModel);
    treeHandler = new aTreeHandler(tv, treeModel) {
      protected boolean hasListeners() {
        return listenerList != null;
      }
      protected EventListenerList getEventListenerList() {
        return TreeTableComponent.this.getEventListenerList();
      }
      public boolean isAutoScrollOnExpansion() {
        return tv.isAutoScrollOnExpansion();
      }
      public void setAutoScrollOnExpansion(boolean autoScrollOnExpansion) {
        tv.setAutoScrollOnExpansion(autoScrollOnExpansion);
      }
    };
    treeHandler.addExpansionListener(new iExpansionListener() {
      @Override
      public void itemWillExpand(ExpansionEvent event) throws ExpandVetoException {}
      @Override
      public void itemWillCollapse(ExpansionEvent event) throws ExpandVetoException {
        Object o = event.getItem();

        if (o instanceof iTreeItem) {
          iTreeItem ti = (iTreeItem) o;
          int       n  = ((aTreeHandler) treeHandler).indexOfInList(ti.getData());

          if (n != -1) {
            tv.clearSelectionsInRange(n + 1, ti.getChildCount());
          }
        }
      }
    });
    tv.setTreeModel(treeModel);
  }

  protected void resetTableEx(iFilterableList<Column> columns, iFilterableList<RenderableDataItem> rows) {
    tableHeader.setColumns(columns);
    tableModel.resetModel(columns, rows);
    treeModel.setAll(rows);
  }
}
