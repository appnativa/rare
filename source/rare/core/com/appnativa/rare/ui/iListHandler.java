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

import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iItemChangeListener;
import com.appnativa.util.iFilterableList;

import java.util.List;

/**
 * An interface for components that manage a list of items.
 *
 * @author Don DeCoteau
 */
public interface iListHandler extends iFilterableList<RenderableDataItem> {
  public static enum SelectionMode {
    SINGLE, MULTIPLE, BLOCK, NONE, INVISIBLE
  }

  public static enum SelectionType {
    CHECKED_LEFT, CHECKED_RIGHT, ROW_ON_TOP, ROW_ON_BOTTOM,
  }

  /**
   * Adds the specified items to the list
   *
   * @param index the index at which to copy the items
   * @param items
   * @param insertMode whether the items should be inserted at the specified index or added to the item at the index
   */
  public void addAll(int index, List<RenderableDataItem> items, boolean insertMode);

  /**
   * Copies the currently selected items
   *
   * @param index the index where the items should be copied to
   * @param insertMode whether the items should be inserted at the specified index or added to the item at the index
   * @param delete whether the selected items should be deleted after they are copied
   */
  public void copySelectedItems(int index, boolean insertMode, boolean delete);

  /**
   * Deletes the currently selected data
   *
   * @param returnData whether the data representing the currently selection should be returned
   * @return the currently selected data or null
   */
  public Object deleteSelectedData(boolean returnData);

  /**
   * Removes the rows at the specified indexes
   *
   * @param indexes the rows to remove
   */
  @Override
  public void removeRows(int[] indexes);

  /**
   * Sets the selection type for the handler
   *
   * @param type the selection type for the handler
   */
  public void setListSelectionType(SelectionType type);

  /**
   * Get the count of items being handled
   * @return  the count of items being handled
   */
  public int getItemCount();

  /**
   * Gets the selection type for the handler
   *
   * @return the selection type for the handler
   */
  public SelectionType getListSelectionType();

  /**
   * Gets the bounds for the specified range
   * @param firstRow the first row
   * @param lastRow the last row
   * @return the bounds
   */
  public UIRectangle getRowBounds(int firstRow, int lastRow);

  /**
   * Get the index of the row at the specified location within the table
   * @param x x-position
   * @param y y-position
   * @return the row index or -1
   */
  public int getRowIndexAt(float x, float y);

  /**
   * Returns the number of selected rows.
   *
   * @return the number of selected rows, 0 if no rows are selected
   */
  public int getSelectedIndexCount();

  /**
   * Adds an action listener
   *
   * @param l the listener to add
   */
  void addActionListener(iActionListener l);

  /**
   * Adds a listener to receive item events when the state of an item is
   * changed by the user. Item events are not sent when an item's
   * state is set programmatically.  If <code>l</code> is
   * <code>null</code>, no exception is thrown and no action is performed.
   *
   * @param    l the listener to receive events
   */
  void addSelectionChangeListener(iItemChangeListener l);

  /**
   * Adds the specified index as a member of the selection set.
   *
   * @param index the index to add
   */
  void addSelectionIndex(int index);

  /**
   * Clears the popup menu index
   */
  void clearContextMenuIndex();

  /**
   * Clears the selection
   */
  void clearSelection();

  /**
   * Called to dispose of the handler
   */
  void dispose();

  /**
   * Fires an action event for the currently selected
   * item
   */
  void fireActionForSelected();

  /**
   * Refreshes the displayed items. Call this method
   * after significant changes have been made to data items.
   *
   * This method is guaranteed to always run on the event dispatch thread
   * and is therefore safe to call from a background thread (created via window.spawn())
   */
  void refreshItems();

  /**
   * Removes an action listener
   *
   * @param l the listener to remove
   */
  void removeActionListener(iActionListener l);

  /**
   * Removes an item listener.
   * If <code>l</code> is <code>null</code>,
   * no exception is thrown and no action is performed.
   *
   * @param   l the listener being removed
   */
  void removeSelectionChangeListener(iItemChangeListener l);

  /**
   * Removes the specified index from the current selection.
   * Does nothing if a given index is not selected
   *
   * @param index the index to remove
   */
  void removeSelection(int index);

  /**
   *  Scrolls the component such that the specified row becomes visible
   *
   *  @param row the row to make visible
   */
  void scrollRowToVisible(int row);

  /**
   *  Scrolls the current selection such that it becomes visible
   */
  void makeSelectionVisible();

  /**
   * Gets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @return true to keep visible; false otherwise
   */
  boolean isKeepSelectionVisible();

  /**
   * Sets whether the table should keep it's selection visible
   * when the size of the table is changed.
   *
   * @param keepSelectionVisible true to keep visible; false otherwise
   */
  void setKeepSelectionVisible(boolean keepSelectionVisible);

  /**
   * Performs a select all on the component's contents
   */
  void selectAll();

  /**
   * Sizes all rows the fit their contents
   */
  void sizeRowsToFit();

  /**
   * Sets the color to use to paint alternate rows
   *
   * @param color the color to use to paint alternate rows
   */
  void setAlternatingRowColor(UIColor color);

  /**
   * Sets whether the items in the list automatically highlighted when the
   * mouse moves over them
   * @param autoHilight true to auto hilight; false otherwise
   */
  void setAutoHilight(boolean autoHilight);

  /**
   * Sets whether selection changes will cause events to be fired
   *
   * @param enabled true to enable; false to disable
   */
  void setChangeEventsEnabled(boolean enabled);

  /**
   * Sets whether data changes will cause events to be fired
   *
   * @param enabled true to enable; false to disable
   */
  void setDataEventsEnabled(boolean enabled);

  void setDeselectEventsDisabled(boolean disabled);

  void setChangeSelColorOnLostFocus(boolean change);

  /**
   * Returns whether the widget should automatically handle the selection when
   * the widget receives focus for the first time
   *
   * @param handle true if it should be handled; false otherwise
   */
  void setHandleFirstFocusSelection(boolean handle);

  /**
   * Returns whether the list is selectable
   *
   * @param selectable true if the list is selectable
   */
  void setListSelectable(boolean selectable);

  /**
   *  Sets the minimum allowable row height.
   * THis is useful when uniform heights are used
   *
   *  @param min the minimum allowable row height
   */
  void setMinRowHeight(int min);

  /**
   * Sets the row height
   *
   * @param height the row height
   */
  void setRowHeight(int height);

  /**
   * Selects a the item at the specified index.
   * Does nothing if the given index is greater than or equal to the list size.
   *
   * @param index the index of the item to select
   */
  void setSelectedIndex(int index);

  /**
   * Selects a set of items.
   * Does nothing if a given index is greater than or equal to the list size.
   *
   * @param indices the indices of the items to select
   */
  void setSelectedIndexes(int[] indices);

  /**
   * Selects the specified item
   *
   * @param value the object to select
   */
  void setSelectedItem(RenderableDataItem value);

  void setShowDivider(boolean show);

  void setSingleClickAction(boolean singleClick);

  /**
   * Sets the minimum visible row count. The component will adjust it size to try to
   * ensure that the specified minimum number of rows are always visible
   *
   * @param rows the number of rows
   */
  void setMinimumVisibleRowCount(int rows);

  /**
   * Gets the minimum visible row count. The component will adjust it size to try to
   * ensure that the specified minimum number of rows are always visible
   *
   * @return rows the number of rows
   */
  int getMinimumVisibleRowCount();

  /**
   * Sets the visible row count. The component will adjust it size to try to
   * ensure that the specified number of rows are always visible
   *
   * @param rows the number of rows
   */
  void setVisibleRowCount(int rows);

  /**
   * Gets the alternating hilight color for the component
   *
   * @return the alternating hilight color for the component
   */
  UIColor getAlternatingRowColor();

  /**
   * Return the index that anchors the selections
   * This is generally the index that the cursor is at
   *
   * @return  the index that anchors the selections
   */
  int getAnchorSelectionIndex();

  /**
   * Gets the component that contain all the visual
   * elements necessary the display and manage the list
   *
   * @return the container component
   */
  iPlatformComponent getContainerComponent();

  /**
   * Gets the item renderer for the list
   *
   * @return the item renderer for the list
   */
  iPlatformItemRenderer getItemRenderer();

  /**
   * Gets the component that displays the list
   *
   * @return the component that displays the list
   */
  iPlatformComponent getListComponent();

  /**
   * Returns the last selected index or -1 if the selection is empty
   *
   * @return the last selected index or -1 if the selection is empty
   */
  int getMaxSelectionIndex();

  /**
   * Returns the first selected index or -1 if the selection is empty
   *
   * @return the first selected index or -1 if the selection is empty
   */
  int getMinSelectionIndex();

  /**
   * Returns the index of item that was clicked on to trigger the popup menu.
   * This value is only available while the mouse is still pressed.
   *
   * @return the index of item that was clicked on to trigger the popup menu
   */
  int getContextMenuIndex();

  /**
   * Returns the item that was clicked on to trigger the popup menu.
   * This value is available while the mouse is still pressed.
   *
   * @return the item that was clicked on to trigger the popup menu
   */
  RenderableDataItem getContextMenuItem();

  /**
   * Gets the preferred height of the specified row
   *
   * @param row the row to get the height for
   *
   * @return the preferred height of the specified row
   */
  int getPreferredHeight(int row);

  /**
   *  Gets the default height for rows
   *
   * @return the default height for rows
   */
  int getRowHeight();

  /**
   * Gets the list of rows
   *
   * @return the list of rows
   */
  List<RenderableDataItem> getRows();

  /**
   * Returns the last selected index or -1 if the selection is empty
   *
   * @return the last selected index or -1 if the selection is empty
   */
  int getSelectedIndex();

  /**
   * Returns an array of all of the selected rows, in increasing order.
   *
   * @return an array of all of the selected rows, or null if there are no selections
   */
  int[] getSelectedIndexes();
  
  /**
   * Returns an array of all of the checked rows, in increasing order.
   *
   * @return an array of all of the checked rows, or null if there are no checked rows of the list is not checkable
   */
  int[] getCheckedIndexes();

  /**
   * Returns the value for the selected row
   *
   * @return the value for the selected row or null of nor row is selected
   */
  RenderableDataItem getSelectedItem();

  /**
   * Returns the value for the selected row
   *
   * @return the value for the selected row or null of nor row is selected
   */
  Object getSelection();

  /**
   * Returns the value for the selected row
   *
   * @return a string representation of the value for the selected or null of nor row is selected
   */
  String getSelectionAsString();

  /**
   * Returns an array of the values of all of the selected rows, in increasing order.
   *
   * @return an array of the values of all of the selected rows, or null if there are no selections
   */
  Object[] getSelections();

  /**
   * Returns an array of the values of all of the selected rows, in increasing order.
   *
   * @return an String array of the values of all of the selected rows, or null if there are no selections
   */
  String[] getSelectionsAsStrings();

  /**
   * Gets the visible row count.
   *
   * @return the number of visible rows
   */
  int getVisibleRowCount();

  /**
   * Returns whether the list has any selections
   *
   * @return true if the list has selections; false otherwise
   */
  boolean hasSelection();

  /**
   * Gets whether selection changes will cause events to be fired
   *
   * @return true for enabled; false for disabled
   */
  boolean isChangeEventsEnabled();

  /**
   * Returns whether the color of selected items should change
   * when the component loses focus
   *
   * @return true to change color on lost focus; false otherwise
   */
  boolean isChangeSelColorOnLostFocus();

  /**
   * Gets whether data changes will cause events to be fired
   *
   * @return true for enabled; false for disabled
   */
  boolean isDataEventsEnabled();

  /**
   * Returns whether the widget should automatically handle the selection when
   * the widget receives focus for the first time
   *
   * @return true if it should be handled; false otherwise
   */
  boolean isHandleFirstFocusSelection();

  /**
   * Returns whether the list is selectable
   *
   * @return true if the list is selectable
   */
  boolean isListSelectable();

  /**
   * Returns whether the specified row is currently selected
   *
   * @param row the row to test
   *
   * @return true if the specified row is currently selected
   */
  boolean isRowSelected(int row);

  /**
   * Returns whether the specified row is currently selected
   *
   * @param item the item that represents the row
   *
   * @return true if the specified row is currently selected
   */
  boolean isRowSelected(RenderableDataItem item);

  /**
   * Returns whether the list is tabular
   *
   * @return true if the list is tabular
   */
  boolean isTabular();

  /**
   * Sets the color and stroke for the divider line
   * @param c the line color
   * @param  stroke the line stroke
   */
  void setDividerLine(UIColor c, UIStroke stroke);

  /**
   * Get the last visible index in the list
   * @return the last visible index in the list or -1 if the list is empty
   */
  int getLastVisibleIndex();

  /**
   * Get the first visible index in the list
   * @return the first visible index in the list or -1 if the list is empty
   */
  int getFirstVisibleIndex();

 
  /**
   *  Scrolls the component such that the specified row is at the top
   *
   *  @param row the row to scroll to the top
   */
  public void scrollRowToTop(int row);

  /**
   *  Scrolls the component such that the specified row is at the bottom
   *
   *  @param row the row to scroll to the bottom
   */
  public void scrollRowToBottom(int row);

  /**
   * Sets the selection mode for the list
   *
   * @param selectionMode the selection mode
   */
  void setSelectionMode(SelectionMode selectionMode);

  public void repaintRow(int row);
}
