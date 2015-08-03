/*
 * @(#)DataItemTreeModelForTable.java   2012-01-30
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.table;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.TreeItemEx;
import com.appnativa.rare.ui.tree.iTreeItem;

public class DataItemTreeModelForTable extends DataItemTreeModel {
  protected boolean expandall;

  public DataItemTreeModelForTable(TreeTableView tree) {
    super();
    theTree = tree;
  }

  @Override
  public void setExpandAll(boolean expandAll) {
    this.expandall = expandAll;
  }

  @Override
  protected iTreeItem createTreeItem(RenderableDataItem item, iTreeItem parent) {
    return new TreeTableItem(item, (TreeItemEx) parent);
  }

  @Override
  protected void fireRootChanged() {
    if (expandall &&!sorting) {
      getRootTreeItem().expandAll();
    }

    super.fireRootChanged();
  }

  class TreeTableItem extends TreeItemEx {
    public TreeTableItem(RenderableDataItem di, TreeItemEx parent) {
      super(di, DataItemTreeModelForTable.this, parent);
    }

    @Override
    public boolean isLeaf() {
      RenderableDataItem di = getExpandableItemEx(item);

      return (di == null) || di.isEmpty();
    }
  }
}
