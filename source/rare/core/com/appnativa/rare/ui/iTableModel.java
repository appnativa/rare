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

import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Interface for table models.
 *
 * @author Don DeCoteau
 */
public interface iTableModel extends iPlatformListDataModel {

  /**
   * Filters the specified index.
   * The causes the specified index to be hidden
   *
   * @param index
   */
  public void filter(int index);

  /**
   * Resets the modified flag for the model
   */
  public void resetModified();

  /**
   * Gets the number of columns
   * @return the number of columns
   */
  public int getColumnCount();

  /**
   * Returns whether items in the model were modified
   *
   * @return true if there are modified items; false otherwise
   */
  public boolean isModified();

  /**
   *  Adds the the item at the specified index to the filtered list
   *  The index should be the index of and item in the unfiltered list.
   *  No check are done to see if the item already exists in the filtered list
   *  and no events should be generated
   *
   *  @param index the index of the item to add to the filtered list. if index==-1
   *               then an empty filtered list is created added to with calls to addToFilteredList()
   *
   *  @throws IndexOutOfBoundsException if the index is out of range
   *          (<tt>index &lt; -1 || index &gt;= size()</tt>)
   *
   *  @see #getUnfilteredList
   */
  @Override
  void addIndexToFilteredList(int index);

  /**
   * Adds the specified row to the table
   *
   * @param row the row to add
   */
  void addRow(RenderableDataItem row);

  /**
   * Inserts the specified row into the given position
   *
   * @param index the position
   * @param row the row to insert
   */
  void addRow(int index, RenderableDataItem row);

  /**
   * Adds the specified rows to the table without triggering any notification events.
   * After rows have been added with this function one of the table update functions should be called
   *
   * @param row the row to add
   */
  void addRowEx(RenderableDataItem row);

  /**
   * Adds the specified rows to the table without triggering any notification events.
   * After rows have been added with this function one of the table update functions should be called
   *
   * @param index the index at which to add the rows;
   * @param row the row to add
   */
  void addRowEx(int index, RenderableDataItem row);

  /**
   * Adds the specified rows to the table
   *
   * @param rows the rows to add
   */
  void addRows(Collection<? extends RenderableDataItem> rows);

  /**
   * Adds the specified rows to the table
   *
   * @param index the index at which to add the rows;
   * @param rows the rows to add
   */
  void addRows(int index, Collection<? extends RenderableDataItem> rows);

  /**
   * Adds the specified rows to the table
   *
   * @param rows the rows to add
   */
  void addRowsEx(Collection<? extends RenderableDataItem> rows);

  /**
   * Adds the specified rows to the table
   *
   * @param index the index at which to add the rows;
   * @param rows the rows to add
   */
  void addRowsEx(int index, Collection<? extends RenderableDataItem> rows);

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param row the item to add
   */
  @Override
  void addToFilteredList(RenderableDataItem row);

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param index the index
   * @param row the item to add
   */
  @Override
  void addToFilteredList(int index, RenderableDataItem row);

  /**
   * Clears the table removing all rows and columns
   */
  void clearTable();

  /**
   * Clears the table removing all rows (columns are not affected)
   */
  void clearTableData();

  /**
   * Clears the table removing all rows (columns are not affected)
   * Does not fire any events
   */
  void clearTableDataEx();

  /**
   *  Joins two or more lists and returns a new lists
   * @param e the lists to concatenate
   * @return the new list
   */
  @Override
  List<RenderableDataItem> concat(List<RenderableDataItem>... e);

  /**
   * Creates an empty copy of this model
   *
   * @return an empty copy of this model
   */
  iTableModel createEmptyCopy();

  /**
   * Disposes of the model
   */
  @Override
  void dispose();

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @return true if items were filtered out; false otherwise
   */
  @Override
  boolean filter(iFilter filter);

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @param contains whether a 'contains' test should be performed. If false an equality test is used
   *
   * @return true if items were filtered out; false otherwise
   */
  @Override
  boolean filter(String filter, boolean contains);

  /**
   *   Applies the specified filter to the list
   *
   *   @param filter the filter
   *   @param contains whether a 'contains' test should be performed. If false an equality test is used
   *   @param nullPasses true if a null value passes the filter; false otherwise
   *   @param emptyPasses true if an empty string passes the filter; false otherwise
   *
   *   @return true if items were filtered out; false otherwise
   */
  @Override
  boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses);

  /**
   * Finds the index of the first item matching the specified filter.
   * The search is performed beginning at the specified start index
   *
   * @param start the starting point of the search
   * @param filter the filter
   *
   * @return the index or -1 if no item was found
   */
  @Override
  int find(iFilter filter, int start);

  /**
   * Finds the index of the first item matching the specified filter.
   * The search is performed beginning at the specified start index
   *
   * @param start the starting point of the search
   * @param filter the filter
   * @param contains whether a 'contains' test should be performed. If false an equality test is used
   *
   * @return the index or -1 if no item was found
   */
  @Override
  int find(String filter, int start, boolean contains);

  /**
   * Gets the index of the specified item
   *
   * @param item the item to find the index of
   * @return the index of the specified item or -1 if the item is not in the model
   */
  int indexForRow(RenderableDataItem item);

  /**
   * Puts all the elements of an list into a string separated by a specified delimiter
   *
   * @param sep the delimiter
   * @return the joined elements
   */
  @Override
  String join(String sep);

  /**
   * Moves a row from one location to another
   *
   * @param from the existing location of the row
   * @param to the new location to move the row to
   */
  void moveRow(int from, int to);

  /**
   * Refilters a previously filtered list.
   * Call this method if you change the underlying unfiltered list
   *
   * @return true if items were filtered out; false otherwise
   */
  @Override
  boolean refilter();

  /**
   * Removes the row at the specified position
   *
   * @param pos the position
   */
  void removeRow(int pos);

  /**
   *  Resets the table model
   *
   *  @param columns the new columns for the table
   *  @param rows the new rows for the table
   *
   */
  void resetModel(List<Column> columns, iFilterableList<RenderableDataItem> rows);

  /**
   * Resets the table row while maintaining the current column model
   *
   * @param rows the new rows
   */
  void resetRows(iFilterableList<RenderableDataItem> rows);

  /**
   * Reverses the order of the elements in the list
   * @return the reversed items
   */
  @Override
  List<RenderableDataItem> reverse();

  /**
   * Creates a new list from a selected section of this list
   * (from the specified position to the end of the list)
   *
   * @param start the starting position inclusive
   * @return the new list
   */
  @Override
  List<RenderableDataItem> slice(int start);

  /**
   * Creates a new list from a selected section of this list
   *
   * @param start the starting position inclusive
   * @param end the end position exclusive
   * @return the new list
   */
  @Override
  List<RenderableDataItem> slice(int start, int end);

  /**
   * Sorts the current items using the specified comparator
   *
   * @param comparator the comparator
   */
  @Override
  void sort(Comparator comparator);

  /**
   * Sorts the table by the specified column
   *
   * @param col the column to sort by
   * @param descending true to sort in descending order; false otherwise
   * @param useLinkedData true to sort using the item's linked data; false to use the item's value
   */
  void sort(int col, boolean descending, boolean useLinkedData);

  /**
   * Sorts the rows by the specified column.
   * This method does not fire any events.
   *
   * @param col the column to sort by
   * @param desc true to sort descending; false otherwise
   * @param force true to force sorting; false to only sort if the current sort is not on the specified column
   * @param useLinkedData true to sort using the item's linked data; false to use the item's value
   * @return the sorted items
   */
  List<RenderableDataItem> sortEx(int col, boolean desc, boolean force, boolean useLinkedData);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @return the spliced items
   */
  @Override
  List<RenderableDataItem> splice(int index, int howMany);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   * @return the spliced items
   */
  @Override
  List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   * @return the spliced items
   */
  @Override
  List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e);

  /**
   * Notifies the handler that table's column model and data model have both changed
   */
  void tableChanged();

  /**
   * Notifies the handler that the table's data has changed
   */
  void tableDataChanged();

  /**
   * Notifies the handler that the table's data has changed
   */
  void tableItemsModified();

  /**
   * Removes an existing filters on the list
   *
   * @return whether there were any filters that were removed
   */
  @Override
  boolean unfilter();

  /**
   * Updates the table for insertion or deletion. Call this function after external
   * insertions or deletions have been made to the table
   */
  void updateForInsertionOrDeletion();

  /**
   * Sets the column that will be operated on when filtering, searching, etc.
   *
   * @param col the column that will be operated on, use -1 to indicate the whole row
   */
  void setActiveColumn(int col);

  /**
   * Sets whether sorting is allowed
   *
   * @param sortingAllowed if sorting is allowed; false otherwise
   */
  void setAllowSorting(boolean sortingAllowed);

  /**
   * Sets a converter that converts a item to a string for filtering
   *
   * @param converter the converter
   */
  @Override
  void setConverter(iStringConverter<RenderableDataItem> converter);

  /**
   * Sets the column that is expandable/collapsible
   *
   * @param col the column that is expandable/collapsible
   */
  void setExpandableColumn(int col);

  /**
   * Replace the row at the given position with the specified row
   *
   * @param pos the position
   * @param row the new row
   *
   */
  void setRow(int pos, RenderableDataItem row);

  void setValueAt(Object value, int row, int col);

  /**
   * Gets the column definition for the specified column
   * @param col the column
   * @return the column definition for the specified column
   */
  Column getColumn(int col);

  /**
   * Gets the list of columns
   *
   * @return the list of columns
   */
  List<Column> getColumns();

  /**
   * Gets the comparator used for sorting
   * @return the comparator used for sorting
   */
  Comparator getComparator();

  /**
   * Gets the converter that converts a item to a string for filtering
   *
   * @return the converter that converts a item to a string for filtering
   */
  @Override
  iStringConverter<RenderableDataItem> getConverter();

  /**
   * Gets the column that is expandable/collapsible
   * @return the column that is expandable/collapsible
   */
  int getExpandableColumn();

  /**
   * Gets the filterable list for the tableModel
   *
   * @return the filterable list for the tableModel
   */
  iFilterableList<RenderableDataItem> getFilterableList();

  /**
   * Gets the item at the specified location based on the current display order
   * of rows and columns
   *
   * @param row the item's row
   * @param col the item's column
   *
   * @return the item at the specified location
   */
  RenderableDataItem getItemAt(int row, int col);

  /**
   * Gets the column to use as a descriptor for the specified item
   * This is usually the same value returned by getColumn(col)
   *
   * @param row the row
   * @param col the column
   * @return the column definition for the specified column
   */
  Column getItemDescription(int row, int col);

  /**
   * Gets the column that will be operated on when filtering, searching, etc.
   *
   * @return col the column that will be operated on, -1 indicates the whole row
   */
  int getOperatingColumn();

  /**
   *  Gets the row at the specified location based on the current display order
   *  of rows
   *
   *  @param row the item's row
   *  @return the row at the specified location
   */
  RenderableDataItem getRow(int row);

  int getRowCount();

  /**
   * Gets the list of rows in their current display order
   *
   * @return the list of rows in their current display order
   */
  iFilterableList<RenderableDataItem> getRowsEx();

  /**
   * Gets the column that the data is currently sorted by
   *
   * @return the current sort column or -1 if the table is not sorted
   */
  int getSortColumn();

  /**
   * Gets the tooltip for the item at the specified location
   *
   * @param row the item's row
   * @param col the item's column
   *
   * @return the tooltip for the item at the specified location
   */
  CharSequence getTooltip(int row, int col);

  /**
   * Returns the underlying unfiltered list
   *
   * @return the underlying unfiltered list
   */
  @Override
  List<RenderableDataItem> getUnfilteredList();

  Object getValueAt(int row, int col);

  /**
   * Gets the widget that the model is for
   * @return the widget that the model is for
   */
  iWidget getWidget();

  /**
   * Returns whether sorting is allowed
   *
   * @return true if sorting is allowed; false otherwise
   */
  boolean isAllowSorting();

  /**
   * Returns whether or not the list is currently filtered
   *
   * @return whether or not the list is currently filtered
   */
  @Override
  boolean isFiltered();

  /**
   * Returns if the current sort is descending
   *
   * @return true if the current sort is descending; false otherwise
   */
  boolean isSortDescending();
}
