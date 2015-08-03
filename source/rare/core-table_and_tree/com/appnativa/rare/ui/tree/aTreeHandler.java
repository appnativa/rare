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

import com.appnativa.rare.exception.ExpandVetoException;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.rare.ui.iTreeHandler;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class aTreeHandler implements iTreeHandler, iExpansionHandler {
  protected boolean           hasExpandedListener;
  protected boolean           hasExpansionListener;
  protected iTree             theTree;
  protected DataItemTreeModel treeModel;

  /**
   * Creates a new instance
   *
   * @param list the tree list
   * @param lm the tree model
   */
  public aTreeHandler(iTree list, DataItemTreeModel lm) {
    treeModel = lm;
    theTree   = list;
    theTree.setExpansionHandler(this);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    return treeModel.addAll(c);
  }

  @Override
  public void addChild(RenderableDataItem child) {
    treeModel.add(child);
  }

  @Override
  public void addChild(int row, RenderableDataItem child) {
    RenderableDataItem root = get(row);

    treeModel.addNode(child, root);
  }

  @Override
  public boolean isExpandableStateLocked() {
    return theTree.isExpandableStateLocked();
  }

  @Override
  public void setExpandableStateLocked(boolean locked) {
    theTree.setExpandableStateLocked(locked);
  }

  /**
   * Adds a list of children to an item
   *
   * @param item
   *          the item
   * @param children
   *          the children
   */
  public void addChild(RenderableDataItem item, List<RenderableDataItem> children) {
    treeModel.addNodes(children, item);
  }

  @Override
  public void addChild(RenderableDataItem item, RenderableDataItem child) {
    treeModel.addNode(child, item);
  }

  public void addChildEx(RenderableDataItem child) {
    treeModel.addEx(child);
  }

  /**
   * Adds a list of children to a row
   *
   * @param row
   *          the row
   * @param children
   *          the children
   */
  @Override
  public void addChildren(int row, List<RenderableDataItem> children) {
    RenderableDataItem root = get(row);

    treeModel.addNodes(children, root);
  }

  @Override
  public void addChildren(RenderableDataItem row, List<RenderableDataItem> children) {
    treeModel.addNodes(children, row);
  }

  @Override
  public void addExpandedListener(iExpandedListener l) {
    hasExpandedListener = true;
    getEventListenerList().add(iExpandedListener.class, l);
  }

  @Override
  public void addExpansionListener(iExpansionListener l) {
    hasExpansionListener = true;
    getEventListenerList().add(iExpansionListener.class, l);
  }

  @Override
  public void clearRootNode() {
    treeModel.clear();
  }

  @Override
  public void collapseAll() {
    treeModel.collapseAll();
  }

  @Override
  public void collapseRow(int row) {
    treeModel.collapseRow(row);
  }

  @Override
  public void collapseRow(RenderableDataItem item) {
    treeModel.collapseRow(item);
  }

  public void dispose() {
    theTree   = null;
    treeModel = null;
  }

  @Override
  public void expandAll() {
    treeModel.expandAll();
  }

  @Override
  public void expandRow(int row) {
    treeModel.expandRow(row);
  }

  @Override
  public void expandRow(RenderableDataItem item) {
    treeModel.expandRow(item);
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

  public void refreshItems() {
    treeModel.refreshItems();
  }

  @Override
  public void removeExpandedListener(iExpandedListener l) {
    if (hasListeners()) {
      getEventListenerList().remove(iExpandedListener.class, l);
    }
  }

  @Override
  public void removeExpansionListener(iExpansionListener l) {
    if (hasListeners()) {
      getEventListenerList().remove(iExpansionListener.class, l);
    }
  }

  @Override
  public void sort(Comparator comparator) {
    treeModel.sort(comparator);
  }

  @Override
  public void sortEx(Comparator comparator) {
    treeModel.sortEx(comparator);
  }

  @Override
  public int toggleExpansion(iTree view, iTreeItem ti, int listModelIndex) {
    int scrollTo = 0;

    do {
      RenderableDataItem item = ti.getData();

      if (!ti.isLeaf()) {
        if (hasExpansionListener) {
          ExpansionEvent e = new ExpansionEvent(view, ti);

          if (ti.isExpanded()) {
            fireExpansion(e, false);
          } else {
            fireExpansion(e, true);
          }

          if (e.isConsumed()) {
            break;
          }
        }

        treeModel.toggleRow(listModelIndex);

        if (ti.isExpanded()) {
          int                col = treeModel.getExpandableColumn();
          RenderableDataItem di  = item;

          if (col > -1) {
            di = item.getItemEx(col);
          }

          scrollTo = (di == null)
                     ? 0
                     : listModelIndex + di.size();
        }

        if (hasExpandedListener) {
          ExpansionEvent e = new ExpansionEvent(view, ti);

          if (ti.isExpanded()) {
            fireExpanded(e, true);
          } else {
            fireExpanded(e, true);
          }
        }
      }
    } while(false);

    return scrollTo;
  }

  @Override
  public void toggleRow(int row) {
    treeModel.toggleRow(row);
  }

  @Override
  public boolean unfilter() {
    return treeModel.unfilter();
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    return treeModel.setAll(collection);
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    treeModel.setConverter(converter);
  }

  @Override
  public void setEditingMode(EditingMode valueOf) {}

  public void setExpandableColumn(int expandableColumn) {
    treeModel.setExpandableColumn(expandableColumn);
  }

  @Override
  public void setIndentBy(int indent) {
    theTree.setIndentBy(indent);
  }

  @Override
  public void setRootNodeCollapsible(boolean collapsible) {
    theTree.setRootNodeCollapsible(collapsible);
  }

  @Override
  public void setShowRootHandles(boolean show) {
    theTree.setShowRootHandles(show);
  }

  @Override
  public void setShowRootNode(boolean show) {
    theTree.setShowRoot(show);
  }

  @Override
  public void setSingleClickToggle(boolean singleClickToggle) {
    theTree.setSingleClickToggle(singleClickToggle);
  }

  @Override
  public void setToggleOnTwistyOnly(boolean twistyOnly) {
    theTree.setToggleOnTwistyOnly(twistyOnly);
  }

  @Override
  public void setTwistyMarginOfError(int twistyMarginOfError) {
    theTree.setTwistyMarginOfError(twistyMarginOfError);
  }

  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    return treeModel.getConverter();
  }

  @Override
  public int getExpandableColumn() {
    return treeModel.getExpandableColumn();
  }

  @Override
  public RenderableDataItem getParent(int index) {
    RenderableDataItem item = get(index);
    iTreeItem          ti   = treeModel.getTreeItem(item);

    ti = (ti == null)
         ? null
         : ti.getParentItem();

    return (ti == null)
           ? null
           : ti.getData();
  }

  @Override
  public RenderableDataItem getRootItem() {
    return treeModel.getRoot();
  }

  @Override
  public int getTwistyMarginOfError() {
    return theTree.getTwistyMarginOfError();
  }

  @Override
  public boolean isFiltered() {
    return treeModel.isFiltered();
  }

  @Override
  public boolean isItemEditable(RenderableDataItem item) {
    return false;
  }

  @Override
  public boolean isLeafItem(int index) {
    RenderableDataItem item = get(index);
    iTreeItem          ti   = treeModel.getTreeItem(item);

    return (ti == null)
           ? true
           : ti.isLeaf();
  }

  @Override
  public boolean isRootNodeCollapsible() {
    return theTree.isRootNodeCollapsible();
  }

  @Override
  public boolean isRowExpanded(int row) {
    return isRowExpanded(get(row));
  }

  @Override
  public boolean isRowExpanded(RenderableDataItem item) {
    iTreeItem ti = (item == null)
                   ? null
                   : treeModel.getTreeItem(item);

    return (ti == null)
           ? false
           : ti.isExpanded();
  }

  @Override
  public boolean isSingleClickToggle() {
    return theTree.isSingleClickToggle();
  }

  public boolean isTabular() {
    return false;
  }

  @Override
  public boolean isToggleOnTwistyOnly() {
    return theTree.isToggleOnTwistyOnly();
  }

  protected abstract EventListenerList getEventListenerList();

  protected abstract boolean hasListeners();

  @Override
  public List<RenderableDataItem> getRawRows() {
    return treeModel.getRawRows();
  }

  private void fireExpanded(ExpansionEvent e, boolean expand) {
    Object[] listeners = getEventListenerList().getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (e.isConsumed()) {
        break;
      }

      if (listeners[i] == iExpandedListener.class) {
        if (expand) {
          ((iExpandedListener) listeners[i + 1]).itemHasExpanded(e);
        } else {
          ((iExpandedListener) listeners[i + 1]).itemHasCollapsed(e);
        }
      }
    }
  }

  private void fireExpansion(ExpansionEvent e, boolean expand) throws ExpandVetoException {
    Object[] listeners = getEventListenerList().getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (e.isConsumed()) {
        break;
      }

      if (listeners[i] == iExpansionListener.class) {
        if (expand) {
          ((iExpansionListener) listeners[i + 1]).itemWillExpand(e);
        } else {
          ((iExpansionListener) listeners[i + 1]).itemWillCollapse(e);
        }
      }
    }
  }

  public int indexOfInList(RenderableDataItem item) {
    return treeModel.getListModel().indexOf(item);
  }

  private RenderableDataItem get(int row) {
    return treeModel.getListModel().get(row);
  }

  public boolean isEventsEnabled() {
    return treeModel.isEventsEnabled();
  }

  @Override
  public void setTreeEventsEnabled(boolean enabled) {
    treeModel.setEventsEnabled(enabled);
  }
}
