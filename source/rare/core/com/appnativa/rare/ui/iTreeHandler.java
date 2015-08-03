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

package com.appnativa.rare.ui;

import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.event.iExpansionListener;
import com.appnativa.util.iFilter;
import com.appnativa.util.iStringConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * An interface for widgets that manage tree like data structures.
 *
 * @author Don DeCoteau
 */
public interface iTreeHandler {
  public static enum EditingMode {
    NONE, LEAVES, BRANCHES, LEAVES_AND_BRANCHES
  }

  /**
   * Adds the specified collection to the tree
   *
   * @param collection the collection for the tree
   * @return true if the elements were added; false otherwise
   */
  public boolean addAll(Collection<? extends RenderableDataItem> collection);

  /**
   * Adds the specified child to the specified row
   *
   * @param child
   *          the child to add
   */
  public void addChild(RenderableDataItem child);

  /**
   * Adds the specified child to the specified row
   *
   * @param row
   *          the row the child is to be added to
   * @param child
   *          the child to add
   */
  public void addChild(int row, RenderableDataItem child);

  /**
   * Adds the specified child to the specified row
   *
   * @param row
   *          the item the child is to be added to
   * @param child
   *          the child to add
   */
  public void addChild(RenderableDataItem row, RenderableDataItem child);

  /**
   * Adds the specified list of children to the specified row
   *
   * @param row
   *          the item the child is to be added to
   * @param children
   *          the children to add
   */
  public void addChildren(int row, List<RenderableDataItem> children);

  /**
   * Adds the specified list of children to the specified row
   *
   * @param row
   *          the item the child is to be added to
   * @param children
   *          the children to add
   */
  public void addChildren(RenderableDataItem row, List<RenderableDataItem> children);

  /**
   * Adds an expanded listener. The listener will be notified when a row is
   * expanded or collapsed
   *
   * @param l
   *          the listener to add
   */
  public void addExpandedListener(iExpandedListener l);

  /**
   * Adds an expanded listener. The listener will be notified when a row is
   * about to be expanded or collapsed
   *
   * @param l
   *          the listener to add
   */
  public void addExpansionListener(iExpansionListener l);

  /**
   * Clear the root node removing its value and all of its children
   */
  public void clearRootNode();

  /**
   * Collapses all rows
   */
  public void collapseAll();

  /**
   * Collapses the specified row
   *
   * @param row
   *          the row to collapse
   */
  public void collapseRow(int row);

  /**
   * Collapses the specified item
   *
   * @param item
   *          the item to collapse
   */
  public void collapseRow(RenderableDataItem item);

  /**
   * Expands all rows
   */
  public void expandAll();

  /**
   * Expands the specified row
   *
   * @param row
   *          the row to expand
   */
  public void expandRow(int row);

  /**
   * Expands the specified item
   *
   * @param item
   *          the item to expand
   */
  public void expandRow(RenderableDataItem item);

  /**
   * Removes the specified listener
   *
   * @param l
   *          the listener to remove
   */
  public void removeExpandedListener(iExpandedListener l);

  /**
   * Removes the specified listener
   *
   * @param l
   *          the listener to remove
   */
  public void removeExpansionListener(iExpansionListener l);

  /**
   * Sorts the tree
   *
   * @param comparator
   *          the sorting comparator
   */
  public void sort(Comparator comparator);

  /**
   * Sorts the tree without generation any notifications
   *
   * @param comparator
   *          the sorting comparator
   */
  public void sortEx(Comparator comparator);

  /**
   * Toggles the specified row. If the row is expanded it will be collapsed and
   * vice-a-versa
   *
   * @param row
   *          the row to toggle
   */
  public void toggleRow(int row);

  /**
   * Sets the tree to be the contents of the specified collection
   *
   * @param collection
   *          the collection for the tree
   * @return true if the elements were added; false otherwise
   */
  public boolean setAll(Collection<? extends RenderableDataItem> collection);

  /**
   * Sets the editing mode for the tree
   *
   * @param mode
   *          the editing mode for the tree
   */
  public void setEditingMode(EditingMode mode);

  /**
   * Sets the number of pixels to use to indent a child node from a parent
   *
   * @param indent
   *          the number of pixels
   */
  public void setIndentBy(int indent);

  /**
   * Sets whether the root node of the tree is collapsible
   *
   * @param collapsible
   *          true if is is; false otherwise
   */
  public void setRootNodeCollapsible(boolean collapsible);

  /**
   * Sets whether the root node handles are shown
   *
   * @param show
   *          true to show; false to hide
   */
  public void setShowRootHandles(boolean show);

  /**
   * Sets whether the root node is show
   *
   * @param show
   *          true to show the root node; false otherwise
   */
  public void setShowRootNode(boolean show);

  /**
   * Gets the column that is expandable/collapsible
   *
   * @return the column that is expandable/collapsible or -1
   */
  public int getExpandableColumn();

  /**
   * Gets the root item for the tree
   *
   * @return the root item for the tree
   */
  public RenderableDataItem getRootItem();

  /**
   * Returns the specified item is editable
   *
   * @param item
   *          the item
   * @return true if it is editable; false otherwise
   */
  public boolean isItemEditable(RenderableDataItem item);

  /**
   * Returns whether the root node can be collapsed
   *
   * @return true if the root node can be collapsed; false otherwise
   */
  public boolean isRootNodeCollapsible();

  /**
   * Returns whether the specified row is currently expanded
   *
   * @param row
   *          the row to test
   *
   * @return true if the specified row is currently expanded
   */
  public boolean isRowExpanded(int row);

  /**
   * Returns whether the specified row is currently expanded
   *
   * @param item
   *          the item that represents the row
   *
   * @return true if the specified row is currently expanded
   */
  public boolean isRowExpanded(RenderableDataItem item);

  /**
   * Applies the specified filter to the list
   *
   * @param filter
   *          the filter
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(iFilter filter);

  /**
   * Applies the specified filter to the list
   *
   * @param filter
   *          the filter
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   *
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(String filter, boolean contains);

  /**
   * Applies the specified filter to the list
   *
   * @param filter
   *          the filter
   * @param contains
   *          whether a 'contains' test should be performed. If false an
   *          equality test is used
   * @param nullPasses
   *          true if a null value passes the filter; false otherwise
   * @param emptyPasses
   *          true if an empty string passes the filter; false otherwise
   *
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses);

  /**
   * Removes an existing filters on the list
   *
   * @return whether there were any filters that were removed
   */
  boolean unfilter();

  /**
   * Sets whether the newly exposed items are automatically scrolled into view
   *
   * @param autoScrollOnExpansion true if newly exposed items should be automatically scrolled into
   *         view; false otherwise
   */
  void setAutoScrollOnExpansion(boolean autoScrollOnExpansion);

  /**
   * Sets a converter that converts a item to a string for filtering
   *
   * @param converter
   *          the converter
   */
  void setConverter(iStringConverter<RenderableDataItem> converter);

  /**
   * Sets whether changes will cause events to be fired
   *
   * @param enabled true to enable; false to disable
   */
  void setTreeEventsEnabled(boolean enabled);

  /**
   * Sets whether the expandable state of the tree is locked
   *
   * @param locked
   *          true if the user can't expand or collapse nodes; false otherwise
   */
  void setExpandableStateLocked(boolean locked);

  /**
   * Sets whether a single click toggles node collapse/expansion. If true then
   * clicking anywhere on an expandable node toggles its expansion (unless
   * toggle on twisty only is also true).
   *
   * The default behavior is a double click toggles on non-touch devices and a
   * single click on touch devices.
   *
   * @param singleClickToggle
   *          true if the node is to be toggled on a single click; false for
   *          adouble click
   */
  void setSingleClickToggle(boolean singleClickToggle);

  /**
   * Sets whether to toggle node collapse/expansion only when the twisty is
   * clicked. If true then clicking anywhere other than the twisty on an
   * expandable node will result if the same behavior as a non expandable node.
   * The default is false
   *
   * @param twistyOnly
   *          true if the node is to be toggled only when the twisty is clicked;
   *          false otherwise
   */
  void setToggleOnTwistyOnly(boolean twistyOnly);

  /**
   * Sets the margin of error for detecting touches on the twisty when using a
   * touch device. Touches extending from the right edge of the twisty to the
   * margin of error amount are considered to be touching on the twisty.
   *
   * @param twistyMarginOfError
   *          the twisty margin of error.
   */
  void setTwistyMarginOfError(int twistyMarginOfError);

  /**
   * Gets a converter that converts a item to a string for filtering
   *
   * return the converter or null if non was set
   */
  iStringConverter<RenderableDataItem> getConverter();

  /**
   * Gets the parent of the child at the specified index
   *
   * @param index
   * @return the parent item
   */
  RenderableDataItem getParent(int index);

  /**
   * Returns a list of rows as they are stored within the table model
   *
   * @return a list of rows as they are stored within the table model
   */
  List<RenderableDataItem> getRawRows();

  /**
   * Gets the margin of error for detecting touches on the twisty when using a
   * touch device. Touches extending from the right edge of the twisty to the
   * margin of error amount are considered to be touching on the twisty.
   *
   * @return the twisty margin of error.
   */
  int getTwistyMarginOfError();

  /**
   * Returns whether the newly exposed items are automatically scrolled into
   * view
   *
   * @return true if newly exposed items are automatically scrolled into view;
   *         false otherwise
   */
  boolean isAutoScrollOnExpansion();

  /**
   * Gets whether the expandable state of the tree is locked
   *
   * @return true if the user can't expand or collapse nodes; false otherwise
   */
  boolean isExpandableStateLocked();

  /**
   * Returns whether or not the list is currently filtered
   *
   * @return whether or not the list is currently filtered
   */
  boolean isFiltered();

  /**
   * Returns whether the specified item is a leaf item
   *
   * @param index
   *          the index of the item
   * @return true if it is a leaf item; false if it is a branch
   */
  boolean isLeafItem(int index);

  /**
   * Gets whether a single click toggles node collapse/expansion.
   *
   * @return true if the node is to be toggled on a single click; false for
   *         adouble click
   */
  boolean isSingleClickToggle();

  /**
   * Gets whether to toggle node collapse/expansion only when the twisty is
   * clicked. If true then clicking anywhere other than the twisty on an
   * expandable node will result if the same behavior as a non expandable node
   *
   * @return true if the node is toggled only when the twisty is clicked; false
   *         otherwise
   */
  boolean isToggleOnTwistyOnly();
}
