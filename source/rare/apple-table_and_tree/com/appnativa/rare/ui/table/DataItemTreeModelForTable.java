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

import com.appnativa.rare.platform.apple.ui.view.TreeView;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.tree.DataItemTreeModel;
import com.appnativa.rare.ui.tree.TreeItemEx;
import com.appnativa.rare.ui.tree.iTreeItem;

import java.util.Comparator;

public class DataItemTreeModelForTable extends DataItemTreeModel {
  protected boolean expandall;
  private boolean   sorting;

  public DataItemTreeModelForTable(TreeView tree) {
    super();
    theTree = tree;
  }

  @Override
  public void sort(Comparator comparator) {
    try {
      sorting = true;
      super.sort(comparator);
    } finally {
      sorting = false;
    }
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
