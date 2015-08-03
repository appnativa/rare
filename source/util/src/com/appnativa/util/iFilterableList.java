/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * A class representing a filterable list of items
 *
 * @author Don DeCoteau
 */
public interface iFilterableList<E> extends List<E> {

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param index the index
   * @param e the item to add
   */
  public void addToFilteredList(int index, E e);

  /**
   * Adds the the item at the specified index to the filtered list
   * The index should be the index of and item in the unfiltered list.
   * No check are done to see if the item already exists in the filtered list
   * and no events should be generated
   *
   * @param index the index of the item to add to the filtered list. if index==-1
   *              then an empty filtered list is created added to with calls to addToFilteredList()
   *
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; -1 || index &gt;= size()</tt>)
   *
   * @see #getUnfilteredList
   */
  void addIndexToFilteredList(int index);

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param e the item to add
   */
  void addToFilteredList(E e);

  /**
   *  Joins two or more lists and returns a new lists
   * @param e the lists to concatenate
   * @return the new list
   */
  List<E> concat(List<E>... e);

  /**
   * Increases the capacity of this list instance, if necessary, to
   * ensure that it can hold at least the number of items specified by the
   * capacity argument.
   *
   * @param capacity  the desired minimum capacity.
   */
  void ensureCapacity(int capacity);

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(iFilter filter);

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @param contains whether a 'contains' test should be performed. If false an equality test is used
   *
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(String filter, boolean contains);

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @param contains whether a 'contains' test should be performed. If false an equality test is used
   * @param nullPasses true if a null value passes the filter; false otherwise
   * @param emptyPasses true if an empty string passes the filter; false otherwise
   *
   * @return true if items were filtered out; false otherwise
   */
  boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses);

  /**
   * Set the filtered list for this list. Use this method when the filtering code
   * cannot be expressed as a simple filter and has to be performed external to the list
   *
   * @param list the list
   * @param lastFilter the filter to be returned via the {@link #getLastFilter()} method.
   */
  void setFilteredList(List<E> list, iFilter lastFilter);

  /**
   * Finds the index of the first item matching the specified filter.
   * The search is performed beginning at the specified start index
   *
   * @param start the starting point of the search
   * @param filter the filter
   *
   * @return the index or -1 if no item was found
   */
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
  int find(String filter, int start, boolean contains);

  /**
   * Puts all the elements of an list into a string separated by a specified delimiter
   *
   * @param sep the delimiter
   * @return the joined elements
   */
  String join(String sep);

  /**
   * Moves an element from one location to another
   *
   * @param from the existing location of the element
   * @param to the new location to move the element to
   */
  void move(int from, int to);

  /**
   * Removes a value from the end of the list
   *
   * @return the removed value or null if the list is empty
   */
  E pop();

  /**
   * Adds a one or more elements value to the end of the list
   *
   * @param e the elements to add
   */
  void push(E... e);

  /**
   * Refilters a previously filtered list.
   * Call this method if you change the underlying unfiltered list
   *
   * @return true if items were filtered out; false otherwise
   */
  boolean refilter();

  /**
   * Removes the specified set of rows from the list
   * @param indexes the rows to remove
   */
  void removeRows(int[] indexes);

  /**
   * Reverses the order of the elements in the list
   * @return this list reversed
   */
  List<E> reverse();

  /**
   * Removes a value from the beginning of the list
   *
   * @return the removed value or null if the list is empty
   */
  E shift();

  /**
   * Creates a new list from a selected section of this list
   * (from the specified position to the end of the list)
   *
   * @param start the starting position inclusive
   * @return the new list
   */
  List<E> slice(int start);

  /**
   * Creates a new list from a selected section of this list
   *
   * @param start the starting position inclusive
   * @param end the end position exclusive
   * @return the new list
   */
  List<E> slice(int start, int end);

  /**
   * Sorts the current items using the specified comparator
   *
   * @param comparator the comparator
   *
   */
  void sort(Comparator comparator);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   *
   * @return the elements removed from the list
   */
  List<E> splice(int index, int howMany);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   *
   * @return the elements removed from the list
   */
  List<E> splice(int index, int howMany, E... e);

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   *
   * @return the elements removed from the list
   */
  List<E> spliceList(int index, int howMany, List<E> e);

  /**
   * Swaps the values of the specified indexes
   *
   * @param index1 the first index
   * @param index2 the second index
   */
  void swap(int index1, int index2);

  /**
   * Removes an existing filters on the list
   *
   * @return whether there were any filters that were removed
   */
  boolean unfilter();

  /**
   * Adds a value to the begining of the list
   *
   * @param value the value to add
   */
  void unshift(E value);

  /**
   * Sets the list to contain the specified items.
   * All previous items will be removed.
   *
   * @param collection
   * @return true if the elements were added; false otherwise
   */
  boolean setAll(Collection<? extends E> collection);

  /**
   * Sets a converter that converts a item to a string for filtering
   *
   * @param converter the converter
   */
  void setConverter(iStringConverter<E> converter);

  /**
   * Gets the converter that converts a item to a string for filtering
   *
   * @return the converter that converts a item to a string for filtering
   */
  iStringConverter<E> getConverter();

  /**
   * Returns the filtered list
   *
   * @return the filtered list or null if the list is not filtered
   */
  List<E> getFilteredList();

  /**
   * Gets the filter that is applied to the list
   *
   * @return the filter that is applied to the list or null if there is no filter
   */
  iFilter getLastFilter();

  /**
   * Returns the underlying unfiltered list
   *
   * @return the underlying unfiltered list
   */
  List<E> getUnfilteredList();

  /**
   * Returns whether or not the list is currently filtered
   *
   * @return whether or not the list is currently filtered
   */
  boolean isFiltered();

  /**
   * Trims the internal storage to match the size of the list
   */
  void trimToSize();
}
