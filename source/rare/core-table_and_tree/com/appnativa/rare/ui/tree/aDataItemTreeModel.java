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
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public abstract class aDataItemTreeModel extends AbstractList<RenderableDataItem>
        implements iFilterableList<RenderableDataItem>, iStringConverter {
  protected int                expandableColumn   = -1;
  protected RenderableDataItem rootNode           = new RenderableDataItem("Root Node");
  protected EventListenerList  listenerList       = new EventListenerList();
  protected boolean            eventsEnabled      = true;
  protected boolean            asksAllowsChildren = true;
  protected Column             columnDescription;
  protected Comparator         comparator;
  protected iStringConverter   converter;
  protected boolean            expandAll;
  protected iFilter            lastFilter;
  protected iTreeItem          rootItem;
  protected boolean            sorting;
  protected iWidget            theWidget;
  private boolean              hideBarenBranchesWhenFiltered = true;

  /**
   * Constructs a new instance
   */
  public aDataItemTreeModel() {
    rootNode.setConverter(this);
  }

  /**
   * Constructs a new instance
   *
   * @param root {@inheritDoc}
   */
  public aDataItemTreeModel(RenderableDataItem root) {
    this();

    if (root != null) {
      rootNode.copy(root);
      rootNode.setConverter(this);
    }
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
  public aDataItemTreeModel(RenderableDataItem root, boolean asksAllowsChildren) {
    this();

    if (root != null) {
      RenderableDataItem list = getExpandableItem(rootNode);

      list.copy(root);
      list.setConverter(this);
    }

    this.asksAllowsChildren = asksAllowsChildren;
  }

  @Override
  public boolean add(RenderableDataItem child) {
    addNode(child, rootNode);

    return true;
  }

  @Override
  public void add(int index, RenderableDataItem child) {
    insertNodeInto(index, child, rootNode);
  }

  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    int len1 = size();

    synchronized(rootNode) {
      getExpandableItem(rootNode).addAll(c);
    }

    int len2 = size();

    if (len2 > len1) {
      nodesWereInserted(rootNode, indices(len1, len2));
    }

    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    int len1 = size();

    synchronized(rootNode) {
      getExpandableItem(rootNode).addAll(index, c);
    }

    int len2 = size();

    if (len1 != len2) {
      nodesWereInserted(rootNode, indices(len1, len2));

      return true;
    }

    return false;
  }

  public void addEx(RenderableDataItem child) {
    getExpandableItem(rootNode).add(child);
  }

  @Override
  public void addIndexToFilteredList(int index) {
    int len = size();

    if (index == -1) {
      synchronized(rootNode) {
        getExpandableItem(rootNode).addIndexToFilteredList(index);
      }

      if (len > 0) {
        this.nodeStructureChanged(rootNode);
      }

      return;
    }

    if ((index < 0) || (index >= len)) {
      return;
    }

    this.addToFilteredList(get(index));
  }

  public void addItemChangeListener(iItemChangeListener l) {
    listenerList.add(iItemChangeListener.class, l);
  }

  public void addNode(RenderableDataItem child, RenderableDataItem parent) {
    RenderableDataItem list = getExpandableItem(parent);
    int                len  = list.getItemCount();

    synchronized(parent) {
      list.add(child);
    }

    int[] changed = new int[] { len };

    nodesWereInserted(parent, changed);
  }

  public void addNodes(List<RenderableDataItem> children, RenderableDataItem parent) {
    if (children == null) {
      return;
    }

    int   count   = children.size();
    int[] changed = new int[count];
    int   len     = parent.getItemCount();

    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);

      for (int i = 0; i < count; i++) {
        changed[i] = len + i;
        list.add(children.get(i));
      }
    }

    nodesWereInserted(parent, changed);
  }

  public void addNodes(int index, List<RenderableDataItem> children, RenderableDataItem parent) {
    if (children == null) {
      return;
    }

    int   count   = children.size();
    int[] changed = new int[count];

    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);

      for (int i = 0; i < count; i++) {
        changed[i] = index + i;
        list.add(index + i, children.get(i));
      }
    }

    nodesWereInserted(parent, changed);
  }

  public void addNodes(RenderableDataItem[] child, int count, RenderableDataItem parent) {
    int[] changed = new int[count];

    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);
      int                len  = list.getItemCount();

      for (int i = 0; i < count; i++) {
        changed[i] = len + i;
        list.add(child[i]);
      }
    }

    nodesWereInserted(parent, changed);
  }

  @Override
  public void addToFilteredList(RenderableDataItem child) {
    addFilteredListNode(child, rootNode, -1);
  }

  @Override
  public void addToFilteredList(int index, RenderableDataItem child) {
    addFilteredListNode(child, rootNode, index);
  }

  /**
   * Tells how leaf nodes are determined.
   *
   * @return true if only nodes which do not allow children are
   *         leaf nodes, false if nodes which have no children
   *         (even if allowed) are leaf nodes
   * @see #asksAllowsChildren
   */
  public boolean asksAllowsChildren() {
    return asksAllowsChildren;
  }

  @Override
  public void clear() {
    clearEx();
    structureChanged();
  }

  /**
   *  Clears the tree without firing
   */
  public void clearEx() {
    if (rootNode != null) {
      RenderableDataItem list = getExpandableItemEx(rootNode);

      if (list != null) {
        list.clearSubItemParents();

        if (list != rootNode) {
          list.clear();
        } else {
          list.clearSubItems();
        }
      }
    }
  }

  public abstract void collapseAll();

  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return getExpandableItem(rootNode).concat(e);
  }

  public void dispose() {
    try {
      if (listenerList != null) {
        listenerList.clear();
        eventsEnabled = false;
      }

      clearEx();

      if (rootItem != null) {
        rootItem.dispose();
      }
    } catch(Throwable e) {
      Platform.ignoreException("dispose exception", e);
    }

    lastFilter        = null;
    comparator        = null;
    columnDescription = null;
    listenerList      = null;
    theWidget         = null;
    rootNode          = null;
    converter         = null;
    rootItem          = null;
  }

  @Override
  public void ensureCapacity(int capacity) {
    getExpandableItem(rootNode).ensureCapacity(capacity);
  }

  public abstract void expandAll();

  @Override
  public boolean filter(iFilter filter) {
    lastFilter = filter;
    needsFiltering();
    structureChanged();

    return true;
  }

  @Override
  public boolean filter(String filter, boolean contains) {
    FilterableList l = new FilterableList<RenderableDataItem>(new ArrayList<RenderableDataItem>(1));

    l.filter(filter, contains);

    return filter(l.getLastFilter());
  }

  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    FilterableList l = new FilterableList<RenderableDataItem>(new ArrayList<RenderableDataItem>(1));

    l.filter(filter, contains, nullPasses, emptyPasses);

    return filter(l.getLastFilter());
  }

  @Override
  public int find(iFilter filter, int start) {
    return getExpandableItem(rootNode).find(filter, start);
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    throw new UnsupportedOperationException("not supported on tree models");
  }

  @Override
  public int find(String filter, int start, boolean contains) {
    return getExpandableItem(rootNode).find(filter, start, contains);
  }

  /**
   * Invoked this to insert newChild at location index in parents children.
   * This will then message nodesWereInserted to create the appropriate
   * event. This is the preferred way to add children as it will create
   * the appropriate event.
   *
   * @param index the index
   * @param child the child
   * @param parent the parent
   */
  public void insertNodeInto(int index, RenderableDataItem child, RenderableDataItem parent) {
    synchronized(parent) {
      RenderableDataItem list  = getExpandableItem(parent);
      int                count = list.getItemCount();

      if (count == 0) {
        list.add(child);
      } else {
        list.add(index, child);
      }
    }

    int[] changed = new int[] { index };

    nodesWereInserted(parent, changed);
  }

  @Override
  public String join(String sep) {
    return getExpandableItem(rootNode).join(sep);
  }

  @Override
  public void move(int from, int to) {
    getExpandableItem(rootNode).move(from, to);
    nodeStructureChanged(rootNode);
  }

  /**
   * Invoke this method after you've changed how node is to be
   * represented in the tree.
   *
   * @param node the node that changed
   */
  public void nodeChanged(RenderableDataItem node) {
    if (eventsEnabled && (listenerList != null) && (node != null)) {
      RenderableDataItem parent = node.getParentItem();

      if (parent != null) {
        RenderableDataItem list    = getExpandableItem(parent);
        int                anIndex = list.indexOf(node);

        if (anIndex != -1) {
          int[] cIndexs = new int[1];

          cIndexs[0] = anIndex;
          nodesChanged(parent, cIndexs);
        }
      } else if (node == getRoot()) {
        nodesChanged(node, null);
      }
    }
  }

  /**
   * Invoke this method if you've totally changed the children of
   * node and its childrens children. This will post a
   * treeStructureChanged event.
   *
   * @param node the node that changed
   */
  public void nodeStructureChanged(RenderableDataItem node) {
    if (eventsEnabled && (node != null)) {
      fireTreeStructureChanged(node);
    }
  }

  /**
   * Invoke this method after you've changed how the children identified by
   * childIndicies are to be represented in the tree.
   *
   * @param node the node that changed
   * @param childIndices the indices of the children that changed
   */
  public void nodesChanged(RenderableDataItem node, int[] childIndices) {
    if (eventsEnabled && (node != null)) {
      fireTreeNodesChanged(node, childIndices);
    }
  }

  /**
   * Invoke this method after you've inserted some TreeNodes into
   * node.  childIndices should be the index of the new elements and
   * must be sorted in ascending order.
   *
   * @param node the node that changed
   * @param childIndices the indices of the children that changed
   */
  public void nodesWereInserted(RenderableDataItem node, int[] childIndices) {
    if (eventsEnabled && (listenerList != null) && (node != null) && (childIndices != null)
        && (childIndices.length > 0)) {
      int cCount = childIndices.length;

      if (cCount == node.size()) {
        this.nodeStructureChanged(node);
      } else {
        fireTreeNodesInserted(node, childIndices);
      }
    }
  }

  /**
   * Invoke this method after you've removed some TreeNodes from
   * node.  childIndices should be the index of the removed elements and
   * must be sorted in ascending order. And removedChildren should be
   * the array of the children objects that were removed.
   *
   * @param node the node that changed
   * @param childIndices the indices of the children that changed
   * @param removedChildren the removed children
   */
  public void nodesWereRemoved(RenderableDataItem node, int[] childIndices, Object[] removedChildren) {
    if (eventsEnabled && (node != null) && (childIndices != null)) {
      if (node.size() == 0) {
        nodeStructureChanged(node);
      } else {
        fireTreeNodesRemoved(node, childIndices, removedChildren);
      }
    }
  }

  @Override
  public RenderableDataItem pop() {
    RenderableDataItem list = getExpandableItem(rootNode);

    return (list.isEmpty())
           ? null
           : list.remove(list.size() - 1);
  }

  public void populate(RenderableDataItem[] child, int count, RenderableDataItem parent) {
    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);

      list.setItems(child, count);
    }

    this.nodeStructureChanged(parent);
  }

  public void populateRoot(RenderableDataItem[] child, int count) {
    populate(child, count, rootNode);
  }

  @Override
  public void push(RenderableDataItem... value) {
    if (value != null) {
      addNodes(value, value.length, rootNode);
    }
  }

  @Override
  public boolean refilter() {
    return filter(lastFilter);
  }

  public void refreshItems() {
    structureChanged();
  }

  /**
   * Invoke this method if you've modified a tree node upon which this
   * model depends.  The model will notify all of its listeners that the
   * model has changed below the node <code>node</code>.
   *
   * @param node the modified node
   */
  public void reload(RenderableDataItem node) {
    if (eventsEnabled && (node != null)) {
      fireTreeStructureChanged(node);
    }
  }

  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem di = get(index);

    removeNode(index, rootNode);

    return di;
  }

  public void removeItemChangeListener(iItemChangeListener l) {
    listenerList.remove(iItemChangeListener.class, l);
  }

  public void removeNode(int index, RenderableDataItem parent) {
    RenderableDataItem removed[] = null;

    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);
      int                len  = list.getItemCount();

      if ((index < 0) || (index >= len)) {
        return;
      }

      removed    = new RenderableDataItem[1];
      removed[0] = list.remove(index);
    }

    int[] changed = new int[] { index };

    nodesWereRemoved(parent, changed, removed);
  }

  /**
   * Remove a node from its parent. This will message
   * nodesWereRemoved to create the appropriate event. This is the
   * preferred way to remove a node as it handles the event creation
   * for you.
   *
   * @param node the node
   */
  public void removeNodeFromParent(RenderableDataItem node) {
    RenderableDataItem parent = node.getParentItem();

    if (parent == null) {
      throw new IllegalArgumentException("node does not have a parent.");
    }

    RenderableDataItem list  = getExpandableItem(parent);
    int                index = list.indexOf(node);

    if (index > -1) {
      int[]    childIndex = new int[] { index };
      Object[] removed    = new Object[] { node };

      synchronized(parent) {
        list.remove(index);
      }

      nodesWereRemoved(parent, childIndex, removed);
    }
  }

  @Override
  public void removeRows(int[] indexes) {
    int len = (indexes == null)
              ? 0
              : indexes.length;

    if (len == 0) {
      return;
    }

    int copy[] = new int[len];

    System.arraycopy(indexes, 0, copy, 0, len);
    indexes = copy;
    Arrays.sort(indexes);

    RenderableDataItem removed[] = new RenderableDataItem[len];

    synchronized(rootNode) {
      RenderableDataItem list = getExpandableItem(rootNode);

      for (int i = 0; i < len; i++) {
        removed[i] = list.get(indexes[i]);
      }

      list.removeRows(indexes);
    }

    nodesWereRemoved(rootNode, indexes, removed);
  }

  @Override
  public List<RenderableDataItem> reverse() {
    RenderableDataItem list = getExpandableItem(rootNode);

    list.reverse();

    if (list.size() > 0) {
      nodeStructureChanged(rootNode);
    }

    return this;
  }

  @Override
  public RenderableDataItem shift() {
    RenderableDataItem list = getExpandableItem(rootNode);

    return (list.isEmpty())
           ? null
           : list.remove(0);
  }

  @Override
  public int size() {
    final RenderableDataItem item = getExpandableItemEx(rootNode);

    return (item == null)
           ? 0
           : item.size();
  }

  @Override
  public List<RenderableDataItem> slice(int start) {
    return getExpandableItem(rootNode).slice(start, size());
  }

  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return getExpandableItem(rootNode).slice(start, end);
  }

  @Override
  public void sort(Comparator comparator) {
    this.comparator = comparator;
    sorting         = true;

    try {
      needsSorting();
      structureChanged();
    } finally {
      sorting = false;
    }
  }

  public List<RenderableDataItem> sortEx(Comparator comparator) {
    this.comparator = comparator;
    sorting         = true;

    try {
      needsSorting();
    } finally {
      sorting = false;
    }

    return this;
  }

  @Override
  public List<RenderableDataItem> splice(int index, int howMany) {
    return splice(index, howMany, (RenderableDataItem[]) null);
  }

  @Override
  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return spliceList(index, howMany, (e == null)
                                      ? null
                                      : Arrays.asList(e));
  }

  @Override
  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    getExpandableItem(rootNode).spliceList(index, howMany, e);
    nodeStructureChanged(rootNode);

    return this;
  }

  public void structureChanged() {
    if (eventsEnabled) {
      fireRootChanged();
    }
  }

  @Override
  public void swap(int index1, int index2) {
    getExpandableItem(rootNode).swap(index1, index2);
    nodesChanged(rootNode, new int[] { index1, index2 });
  }

  @Override
  public void trimToSize() {}

  @Override
  public String toString(Object item) {
    RenderableDataItem di = (RenderableDataItem) item;

    if ((columnDescription != null) &&!di.isConverted() && (theWidget != null)) {
      convert(theWidget, columnDescription, di);
    }

    return di.toString();
  }

  @Override
  public void unshift(RenderableDataItem value) {
    add(0, value);
  }

  @Override
  public RenderableDataItem set(int index, RenderableDataItem element) {
    RenderableDataItem item = getExpandableItem(rootNode).set(index, element);

    nodesChanged(rootNode, new int[] { index });

    return item;
  }

  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> c) {
    int len1 = size();

    clearEx();

    synchronized(rootNode) {
      getExpandableItem(rootNode).addAll(c);
    }

    int len2 = size();

    if (len2 != len1) {
      structureChanged();
    }

    return false;
  }

  /**
   * Sets whether or not to test leafness by asking getAllowsChildren()
   * or isLeaf() to the TreeNodes.  If ask is true, getAllowsChildren()
   * is messaged, otherwise isLeaf() is messaged.
   *
   * @param ask true to ask; false otherwise
   */
  public void setAsksAllowsChildren(boolean ask) {
    asksAllowsChildren = ask;
  }

  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    this.converter = converter;

    if (rootNode != null) {
      rootNode.setConverter((converter == null)
                            ? this
                            : converter);
    }
  }

  public void setEventsEnabled(boolean enabled) {
    this.eventsEnabled = enabled;
  }

  public void setExpandAll(boolean expandAll) {
    this.expandAll = expandAll;
  }

  public void setExpandableColumn(int expandableColumn) {
    this.expandableColumn = expandableColumn;
  }

  public void setHideBarenBranchesWhenFiltered(boolean hideBarenBranchesWhenFiltered) {
    this.hideBarenBranchesWhenFiltered = hideBarenBranchesWhenFiltered;
  }

  public void setItemDescription(Column column) {
    this.columnDescription = column;
  }

  /**
   * Sets the root to <code>root</code>. A null <code>root</code> implies
   * the tree is to display nothing, and is legal.
   *
   * @param root the root
   */
  public void setRoot(RenderableDataItem root) {
    clearEx();
    this.rootNode.clear();    // = new RenderableDataItem("");

    RenderableDataItem list = getExpandableItem(rootNode);

    if (root != null) {
      list.copy(root);
      list.setConverter((converter == null)
                        ? this
                        : converter);
      rootNode.copyEx(root);
    }

    boolean expanded = true;

    if (rootItem != null) {
      expanded = rootItem.isExpanded();
      rootItem.dispose();
    }

    rootItem = createTreeItem(rootNode, null);
    rootItem.setExpanded(expanded);
    structureChanged();
  }

  public void setWidget(iWidget widget) {
    theWidget = widget;
  }

  @Override
  public RenderableDataItem get(int index) {
    RenderableDataItem di = getExpandableItem(rootNode).get(index);

    if ((columnDescription != null) &&!di.isConverted() && (theWidget != null)) {
      convert(theWidget, columnDescription, di);
    }

    return di;
  }

  public Object getChild(RenderableDataItem parent, int index) {
    if (parent == rootNode) {
      return get(index);
    }

    return getExpandableItem(parent).getItem(index);
  }

  public int getChildCount(RenderableDataItem parent) {
    return getExpandableItem(parent).getItemCount();
  }

  @Override
  public iStringConverter getConverter() {
    return converter;
  }

  public int getExpandableColumn() {
    return expandableColumn;
  }

  @Override
  public List<RenderableDataItem> getFilteredList() {
    return getExpandableItem(rootNode).getFilteredList();
  }

  public abstract iStringConverter<RenderableDataItem> getFilteringConverter();

  public int getIndexOfChild(RenderableDataItem parent, RenderableDataItem child) {
    return getExpandableItem(parent).indexOf(child);
  }

  public Column getItemDescription() {
    return columnDescription;
  }

  public Comparator getLastComparator() {
    return comparator;
  }

  @Override
  public iFilter getLastFilter() {
    return lastFilter;
  }

  /**
   * Returns an array of all the objects currently registered
   * as <code><em>Foo</em>Listener</code>s
   * upon this model.
   * <code><em>Foo</em>Listener</code>s are registered using the
   * <code>add<em>Foo</em>Listener</code> method.
   *
   * <p>
   *
   * You can specify the <code>listenerType</code> argument
   * with a class literal,
   * such as
   * <code><em>Foo</em>Listener.class</code>.
   * For example, you can query a
   * <code>DefaultTreeModel</code> <code>m</code>
   * for its tree model listeners with the following code:
   *
   * <pre>TreeModelListener[] tmls = (TreeModelListener[])(m.getListeners(TreeModelListener.class));</pre>
   *
   * If no such listeners exist, this method returns an empty array.
   *
   * @param listenerType the type of listeners requested; this parameter
   *          should specify an interface that descends from
   *          <code>java.util.EventListener</code>
   * @return an array of all objects registered as
   *          <code><em>Foo</em>Listener</code>s on this component,
   *          or an empty array if no such
   *          listeners have been added
   *
   *
   */
  public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
    return listenerList.getListeners(listenerType);
  }

  public List<RenderableDataItem> getRawRows() {
    return getExpandableItem(rootNode);
  }

  /**
   * Get the root node of the tree
   *
   * @return the root node of the tree
   */
  public RenderableDataItem getRoot() {
    return rootNode;
  }

  public abstract iTreeItem getTreeItem(RenderableDataItem item);

  public abstract iTreeItem getTreeItem(RenderableDataItem item, iTreeItem parent, boolean create);

  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return getExpandableItem(rootNode).getUnfilteredList();
  }

  public boolean isEventsEnabled() {
    return eventsEnabled;
  }

  @Override
  public boolean isFiltered() {
    return getExpandableItem(rootNode).isFiltered();
  }

  public boolean isHideBarenBranchesWhenFiltered() {
    return hideBarenBranchesWhenFiltered;
  }

  /**
   * Returns whether the specified item is a leaf item
   *
   * @param item the item
   *
   * @return true if the item is a leaf; false if it is a branch
   */
  public boolean isLeaf(RenderableDataItem item) {
    int col = getExpandableColumn();

    if (col == -1) {
      return item.isEmpty();
    }

    if (item.size() <= col) {
      return true;
    }

    item = item.getItemEx(col);

    return (item != null) &&!item.isEmpty();
  }

  void addFilteredListNode(RenderableDataItem child, RenderableDataItem parent, int index) {
    int len = parent.getItemCount();

    synchronized(parent) {
      RenderableDataItem list = getExpandableItem(parent);

      if (index > -1) {
        list.addToFilteredList(index, child);
        len = (index >= len)
              ? list.getItemCount() - 1
              : index;
      } else {
        list.addToFilteredList(child);
      }
    }

    int[] changed = new int[] { len };

    nodesWereInserted(parent, changed);
  }

  protected abstract iTreeItem createTreeItem(RenderableDataItem item, iTreeItem parent);

  protected abstract void fireRootChanged();

  /**
   * Notifies all listeners that have registered interest for
   * notification on this event type.
   *
   * @param parent the parent node
   * @param childIndices the indices of the changed elements
   */
  protected abstract void fireTreeNodesChanged(RenderableDataItem parent, int[] childIndices);

  /**
   * Notifies all listeners that have registered interest for
   * notification on this event type.
   *
   * @param parent the parent node
   * @param childIndices the indices of the new elements
   */
  protected abstract void fireTreeNodesInserted(RenderableDataItem parent, int[] childIndices);

  /**
   * Notifies all listeners that have registered interest for
   * notification on this event type.
   *
   * @param parent the parent node
   * @param childIndices the indices of the new elements
   * @param children the removed elements
   */
  protected abstract void fireTreeNodesRemoved(RenderableDataItem parent, int[] childIndices, Object[] children);

  /**
   * Notifies all listeners that have registered interest for
   * notification on this event type.
   *
   * @param parent the parent node
   */
  protected abstract void fireTreeStructureChanged(RenderableDataItem parent);

  protected abstract void needsFiltering();

  protected abstract void needsSorting();

  /**
   * Call after model is created to setup root item which holds a reference to the model
   * and should no be created in the constructor.
   */
  protected void setupRootItem() {
    rootItem = createTreeItem(rootNode, null);
    rootItem.setExpanded(true);
  }

  protected RenderableDataItem getExpandableItem(RenderableDataItem item) {
    if (expandableColumn == -1) {
      return item;
    }

    RenderableDataItem di = item.getItemEx(expandableColumn);

    if (di == null) {
      di = new RenderableDataItem();

      if (expandableColumn <= item.size()) {
        item.setItemCount(expandableColumn + 1);
      }

      item.set(expandableColumn, di);
    }

    return di;
  }

  protected RenderableDataItem getExpandableItemEx(RenderableDataItem item) {
    if (expandableColumn == -1) {
      return item;
    }

    return item.getItemEx(expandableColumn);
  }

  private void convert(iWidget w, Column col, RenderableDataItem di) {
    col.convert(w, di);

    if (di.size() > 0) {
      final int len = di.size();

      for (int i = 0; i < len; i++) {
        col.convert(w, di.get(i));
      }
    }
  }

  private int[] indices(int len1, int len2) {
    int len = len2 - len1;

    if (len < 0) {
      len = 0;
    }

    int n[] = new int[len];

    for (int i = 0; i < len; i++) {
      n[i] = len1++;
    }

    return n;
  }
}
