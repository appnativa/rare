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

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;

import com.google.j2objc.annotations.Weak;

import java.util.Comparator;
import java.util.List;

public class TreeItemEx implements iTreeItem {
  protected RenderableDataItem item;
  protected boolean            needsFiltering;
  protected boolean            needsSorting;
  @Weak
  protected TreeItemEx         parent;
  private boolean              expanded;
  @Weak
  private aDataItemTreeModel   treeModel;

  public TreeItemEx(RenderableDataItem di, aDataItemTreeModel tm, TreeItemEx parent) {
    this.item           = di;
    this.treeModel      = tm;
    this.parent         = parent;
    this.needsFiltering = parent != null;
    this.needsSorting   = parent != null;
  }

  public void addChildren(List<RenderableDataItem> list) {
    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;
    final boolean                       hide  = tm.isHideBarenBranchesWhenFiltered();

    for (int i = 0; i < len; i++) {
      TreeItemEx         ti;
      RenderableDataItem di = items.get(i);

      ti = (TreeItemEx) tm.getTreeItem(di, this, true);
      ti.pefromPendingActions();

      if (hide && ti.isEmptyFilteredBranch()) {
        continue;
      }

      list.add(di);

      if (ti.expanded) {
        ti.addChildren(list);
      }
    }
  }

  public int addChildren(List<RenderableDataItem> list, int index) {
    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;
    final boolean                       hide  = tm.isHideBarenBranchesWhenFiltered();

    if (expanded && (len > 0)) {
      for (int i = 0; i < len; i++) {
        TreeItemEx         ti;
        RenderableDataItem di = items.get(i);

        ti = (TreeItemEx) tm.getTreeItem(di, this, true);

        if (hide && ti.isEmptyFilteredBranch()) {
          continue;
        }

        list.add(index++, di);

        if (ti.expanded) {
          index = ti.addChildren(list, index);
        }
      }
    }

    return index;
  }

  public void collapse(List<RenderableDataItem> list, int index) {
    expanded = false;
    removeChildren(list, index);
  }

  public void collapseAll() {
    expanded = false;

    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = items.get(i);
      TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, false);

      if (ti != null) {
        ti.collapseAll();
      }
    }
  }

  @Override
  public void dispose() {
    if ((item != null) && (treeModel != null)) {
      try {
        final aDataItemTreeModel            tm    = treeModel;
        iFilterableList<RenderableDataItem> items = getItems(false);
        final int                           len   = (items == null)
                ? 0
                : items.size();

        for (int i = 0; i < len; i++) {
          RenderableDataItem di = items.get(i);
          TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, false);

          if (ti != null) {
            ti.dispose();
          }
        }
      } catch(Throwable e) {
        Platform.ignoreException("dispose exception", e);
      }

      item      = null;
      parent    = null;
      treeModel = null;
    }
  }

  public int expand(List<RenderableDataItem> list, int index) {
    if (!expanded) {
      expanded = true;
      index    = addChildren(list, index);
    }

    return index;
  }

  public void expandAll() {
    expanded = true;

    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = items.get(i);
      TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, true);

      ti.expandAll();
    }
  }

  public boolean filter() {
    iFilterableList<RenderableDataItem> items = getItems(true);

    needsFiltering = false;
    items.setConverter(treeModel.getFilteringConverter());

    return items.filter(treeModel.getLastFilter());
  }

  public void needsFiltering(boolean myself) {
    needsFiltering = myself;

    if (myself) {
      filter();
    }

    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = items.get(i);
      TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, false);

      if (ti != null) {
        ti.unfilter();

        if (!ti.isLeaf()) {
          ti.needsFiltering(true);
        }
      }
    }
  }

  public void needsSorting(boolean myself) {
    needsSorting = myself;

    if (myself && expanded) {
      sort();
    }

    iFilterableList<RenderableDataItem> items = getItems(true);
    final int                           len   = (items == null)
            ? 0
            : items.size();
    final aDataItemTreeModel            tm    = treeModel;

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = items.get(i);
      TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, false);

      if ((ti != null) &&!ti.isLeaf()) {
        ti.needsSorting(true);
      }
    }
  }

  public void removeChildren(List<RenderableDataItem> list, int index) {
    int                      len = list.size();
    int                      i   = index;
    final aDataItemTreeModel tm  = treeModel;

    while(i < len) {
      RenderableDataItem di = list.get(i);
      TreeItemEx         ti = (TreeItemEx) tm.getTreeItem(di, this, false);

      if (ti != null) {
        if (isMyChild(ti)) {
          list.remove(index);
          len--;
        } else {
          break;
        }
      }
    }
  }

  public void sort() {
    Comparator c = treeModel.getLastComparator();

    if (c != null) {
      getItems(true).sort(c);
    }

    needsSorting = false;
  }

  public boolean unfilter() {
    needsFiltering = false;

    iFilterableList<RenderableDataItem> items = getItems(false);

    if (items == null) {
      return false;
    }

    boolean   filtered = items.unfilter();
    final int len      = items.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = items.get(i);
      TreeItemEx         ti = (TreeItemEx) treeModel.getTreeItem(di, this, false);

      if (ti != null) {
        if (ti.unfilter()) {
          filtered = true;
        }
      }
    }

    return filtered;
  }

  @Override
  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
  }

  public void setItem(RenderableDataItem item) {
    this.item = item;
  }

  public void setParent(TreeItemEx parent) {
    this.parent = parent;
  }

  @Override
  public int getChildCount() {
    iFilterableList<RenderableDataItem> items = getItems(false);

    return (items == null)
           ? 0
           : items.size();
  }

  @Override
  public RenderableDataItem getData() {
    return item;
  }

  @Override
  public int getDepth() {
    int        n = 0;
    TreeItemEx p = parent;

    while(p != null) {
      n++;
      p = p.parent;
    }

    return n;
  }

  @Override
  public iTreeItem getParentItem() {
    return parent;
  }

  public boolean isEmptyFilteredBranch() {
    iFilterableList<RenderableDataItem> items = getItems(false);

    return (items != null) && items.isFiltered() && items.isEmpty();
  }

  @Override
  public boolean isExpanded() {
    return expanded;
  }

  @Override
  public boolean isLeaf() {
    iFilterableList<RenderableDataItem> items = getItems(false);

    return (items == null) || items.isEmpty();
  }

  @Override
  public boolean isMyChild(RenderableDataItem item) {
    return isMyChild((TreeItemEx) treeModel.getTreeItem(item, null, false));
  }

  public boolean isMyChild(TreeItemEx ti) {
    TreeItemEx p = (ti == null)
                   ? null
                   : ti.parent;

    while(p != null) {
      if (p == this) {
        return true;
      }

      p = p.parent;
    }

    return false;
  }

  protected void pefromPendingActions() {
    iFilterableList<RenderableDataItem> items = getItems(false);

    if (items == null) {
      return;
    }

    if (needsFiltering) {
      needsFiltering = false;

      final iFilter f = treeModel.getLastFilter();

      if (f != null) {
        items.filter(f);
      }
    }

    if (expanded) {
      if (needsSorting) {
        needsSorting = false;

        final Comparator c = treeModel.getLastComparator();

        if (c != null) {
          items.sort(c);
        }
      }
    }
  }

  protected iFilterableList<RenderableDataItem> getItems(boolean create) {
    final int col = treeModel.getExpandableColumn();

    if (col == -1) {
      return item.getItems();
    }

    RenderableDataItem di = item.getItemEx(col);

    if ((di == null) && create) {
      di = new RenderableDataItem();

      if (col <= item.size()) {
        item.setItemCount(col + 1);
      }

      item.set(col, di);
    }

    return di;
  }
}
