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

package com.appnativa.rare.ui.tree;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.util.iStringConverter;

import java.util.IdentityHashMap;
import java.util.Iterator;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class DataItemTreeModel extends aDataItemTreeModel {
  protected IdentityHashMap<RenderableDataItem, TreeItemEx> itemMap = new IdentityHashMap<RenderableDataItem,
                                                                        TreeItemEx>();
  protected iPlatformListDataModel listModel;
  protected iTree                  theTree;

  /**
   * Constructs a new instance
   */
  public DataItemTreeModel() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param root {@inheritDoc}
   */
  public DataItemTreeModel(RenderableDataItem root) {
    super(root);
  }

  @Override
  public void clearEx() {
    super.clearEx();

    Iterator<TreeItemEx> it = itemMap.values().iterator();

    while(it.hasNext()) {
      it.next().dispose();
    }

    itemMap.clear();
  }

  /**
   * Creates a tree specifying whether any node can have children,
   * or whether only certain nodes can have children.
   *
   * @param root a RenderableDataItem object that is the root of the tree
   * @param asksAllowsChildren a boolean, false if any node can
   *        have children, true if each node is asked to see if
   *        it can have children
   * @see #asksAllowsChildren
   */
  public DataItemTreeModel(RenderableDataItem root, boolean asksAllowsChildren) {
    super(root, asksAllowsChildren);
  }

  @Override
  public void collapseAll() {
    TreeItemEx root = getRootTreeItem();

    root.collapseAll();

    if ((theTree != null) &&!theTree.isRootNodeCollapsible()) {
      root.setExpanded(true);
    }

    boolean enabled = listModel.isEventsEnabled();

    listModel.setEventsEnabled(false);
    listModel.clear();

    if ((theTree == null) || theTree.isShowRoot()) {
      listModel.add(root.getData());
    }

    root.addChildren(listModel);
    listModel.setEventsEnabled(enabled);

    if (enabled) {
      listModel.refreshItems();
    }
  }

  public void collapseRow(int row) {
    RenderableDataItem item = listModel.get(row);

    if ((item == rootNode) && (theTree != null) &&!theTree.isRootNodeCollapsible()) {
      return;
    }

    setExpanded(false, row + 1, item);
  }

  public void collapseRow(RenderableDataItem item) {
    if ((item == rootNode) && (theTree != null) &&!theTree.isRootNodeCollapsible()) {
      return;
    }

    int n = listModel.indexOf(item);

    if (n != -1) {
      setExpanded(false, n + 1, item);
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    if (listModel != null) {
      listModel.dispose();
    }

    listModel = null;
    theTree   = null;
  }

  @Override
  public iStringConverter getConverter() {
    return (listModel == null)
           ? null
           : listModel.getConverter();
  }

  @Override
  public void expandAll() {
    TreeItemEx root = getRootTreeItem();

    root.expandAll();

    boolean enabled = listModel.isEventsEnabled();

    listModel.setEventsEnabled(false);
    listModel.clear();

    if ((theTree == null) || theTree.isShowRoot()) {
      listModel.add(root.getData());
    }

    root.addChildren(listModel);
    listModel.setEventsEnabled(enabled);

    if (enabled) {
      listModel.refreshItems();
    }
  }

  public void expandRow(int row) {
    RenderableDataItem item = listModel.get(row);

    setExpanded(true, row + 1, item);
  }

  public void expandRow(RenderableDataItem item) {
    int n = listModel.indexOf(item);

    if (n != -1) {
      setExpanded(true, n + 1, item);
    }
  }

  public void toggleRow(int row) {
    RenderableDataItem item = listModel.get(row);

    if ((item == rootNode) && (theTree != null) &&!theTree.isRootNodeCollapsible()) {
      return;
    }

    setExpanded(!getTreeItem(item).isExpanded(), row + 1, item);
  }

  public void toggleRow(RenderableDataItem item) {
    if ((item == rootNode) && (theTree != null) &&!theTree.isRootNodeCollapsible()) {
      return;
    }

    int n = listModel.indexOf(item);

    if (n != -1) {
      setExpanded(!getTreeItem(item).isExpanded(), n + 1, item);
    }
  }

  @Override
  public boolean unfilter() {
    final boolean filtered = lastFilter != null;

    lastFilter = null;

    TreeItemEx root = getRootTreeItem();

    if (root != null) {
      root.unfilter();

      if (filtered) {
        structureChanged();
      }
    }

    return filtered;
  }

  public void setListModel(iPlatformListDataModel listModel) {
    this.listModel = listModel;

    if (rootItem == null) {
      setupRootItem();
    }
  }

  public void setTree(iTree tree) {
    theTree = tree;
  }

  public iPlatformListDataModel getListModel() {
    return listModel;
  }

  public TreeItemEx getRootTreeItem() {
    return (TreeItemEx) rootItem;
  }

  @Override
  public iTreeItem getTreeItem(RenderableDataItem item, iTreeItem parent, boolean create) {
    TreeItemEx ti = getTreeItem(item);

    if ((ti == null) && create) {
      ti = new TreeItemEx(item, this, (TreeItemEx) parent);
      itemMap.put(item, ti);
    }

    return ti;
  }

  @Override
  protected iTreeItem createTreeItem(RenderableDataItem item, iTreeItem parent) {
    return new TreeItemEx(item, this, (TreeItemEx) parent);
  }

  @Override
  protected void fireRootChanged() {
    TreeItemEx root = getRootTreeItem();

    if (root != null) {
      boolean enabled = listModel.isEventsEnabled();

      listModel.setEventsEnabled(false);
      listModel.clear();

      if (expandAll &&!sorting) {
        getRootTreeItem().expandAll();
      }

      if ((theTree == null) || theTree.isShowRoot()) {
        listModel.add(root.getData());
      }

      root.addChildren(listModel);

      if (expandAll &&!sorting) {
        getRootTreeItem().expandAll();
      }

      listModel.setEventsEnabled(enabled);

      if (enabled) {
        listModel.refreshItems();
      }
    }
  }

  @Override
  protected void fireTreeNodesChanged(RenderableDataItem parent, int[] childIndices) {
    fireTreeStructureChanged(parent);
  }

  @Override
  protected void fireTreeNodesInserted(RenderableDataItem parent, int[] childIndices) {
    fireTreeStructureChanged(parent);
  }

  @Override
  protected void fireTreeNodesRemoved(RenderableDataItem parent, int[] childIndices, Object[] children) {
    final TreeItemEx pti = getTreeItem(parent);

    if (pti != null) {
      for (Object o : children) {
        TreeItemEx ti = itemMap.remove(o);

        if (ti != null) {
          ti.dispose();
        }
      }

      fireTreeStructureChanged(parent);
    }
  }

  @Override
  protected void fireTreeStructureChanged(RenderableDataItem parent) {
    if (parent == rootNode) {
      fireRootChanged();

      return;
    }

    final TreeItemEx ti = getTreeItem(parent);

    if (ti != null) {
      int n = listModel.indexOf(parent);

      if (n != -1) {
        boolean enabled = listModel.isEventsEnabled();

        listModel.setEventsEnabled(false);
        n++;
        ti.removeChildren(listModel, n);
        ti.addChildren(listModel, n);
        listModel.setEventsEnabled(enabled);

        if (enabled) {
          listModel.refreshItems();
        }
      }
    }
  }

  @Override
  public iStringConverter<RenderableDataItem> getFilteringConverter() {
    return listModel.getConverter();
  }

  @Override
  protected void needsFiltering() {
    getRootTreeItem().needsFiltering(false);
  }

  @Override
  protected void needsSorting() {
    boolean myself = true;

    if (comparator instanceof SubItemComparator) {
      SubItemComparator c = (SubItemComparator) comparator;

      if (c.getSortColumn() != expandableColumn) {
        myself = false;
      }
    }

    getRootTreeItem().needsSorting(myself);
  }

  private void setExpanded(boolean expanded, int n, RenderableDataItem item) {
    if (n != -1) {
      TreeItemEx ti = getTreeItem(item);

      if (ti != null) {
        boolean enabled = listModel.isEventsEnabled();

        listModel.setEventsEnabled(false);

        try {
          if (expanded) {
            ti.expand(listModel, n);
          } else {
            ti.collapse(listModel, n);
          }
        } finally {
          listModel.setEventsEnabled(enabled);

          int p = ti.getItems(true).size();

          if (p > 0) {
            if (expanded) {
              listModel.rowsInserted(n, n + p - 1);
            } else {
              listModel.rowsDeleted(n, n + p - 1, ti.getItems(true));
            }
          }
        }
      }
    }
  }

  @Override
  public TreeItemEx getTreeItem(RenderableDataItem item) {
    if (item == rootNode) {
      return (TreeItemEx) rootItem;
    }

    return itemMap.get(item);
  }
}
