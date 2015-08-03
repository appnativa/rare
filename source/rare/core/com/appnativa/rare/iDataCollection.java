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

package com.appnativa.rare;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;

import java.io.IOException;

import java.util.Collection;
import java.util.List;

/**
 * An interface representing data collections.
 *
 * @author Don DeCoteau
 */
public interface iDataCollection {

  /**
   * Clears the contents of the collection without disposing of it
   */
  void clearCollection();

  /**
   * Disposes of the collection
   */
  void disposeCollection();

  /**
   * Sets the data for the collection
   *
   * @param list
   *          the data
   */
  void setCollectionData(List<RenderableDataItem> list);

  /**
   * Refreshes the data in the collection
   *
   * @param context
   *          the context
   */
  void refresh(iWidget context) throws IOException;

  /**
   * Sets the value of a attribute associated with the collection
   *
   * @param context
   *          the context
   * @param name
   *          the name of the property
   * @param value
   *          the value of a property associated with the collection
   */
  void setAttribute(iWidget context, String name, Object value);

  /**
   * Gets the value of a attribute associated with the collection
   *
   * @param context
   *          the context
   * @param name
   *          the name of the property
   *
   * @return the value of a property associated with the collection
   */
  Object getAttribute(iWidget context, String name);

  /**
   * Returns the mime type of the string that is returned by
   * <b>getCollectionAsString()</b>
   *
   * @param context
   *          the context
   *
   * @return the mime type of the string that is returned by
   *         <b>getCollectionAsString()</b>
   *
   * @see #getCollectionStringMimeType
   */
  String getCollectionStringMimeType(iWidget context);

  /**
   * Gets the collection data
   *
   * @param context
   *          the context
   *
   * @return the collection data
   */
  Collection getCollection(iWidget context);

  /**
   * Returns the collection as a string
   *
   * @param context
   *          the context
   *
   * @return the collection as a string
   */
  String getCollectionAsString(iWidget context);

  /**
   * Gets the collection data as renderable data items
   *
   * @param context
   *          the context
   * @param copy
   *          true to make a copy of the items; false otherwise
   *
   * @return the collection data
   */
  Collection<RenderableDataItem> getItemData(iWidget context, boolean copy);

  /**
   * Gets the name of the collection
   *
   * @return the name of the collection
   */
  String getCollectionName();

  /**
   * Gets the text to display when the collection is empty
   *
   * @return the text to display when the collection is empty
   */
  CharSequence getEmptyCollectionText();

  /**
   * sets the text to display when the collection is empty
   *
   * @param text the text to display when the collection is empty
   */
  void setEmptyCollectionText(CharSequence text);

  /**
   * Returns a named sub-collection
   *
   * @param context
   *          the context
   * @param name
   *          the name of the sub-collection
   * @param copy
   *          true to make a copy of the items; false otherwise
   *
   * @return the sub-collection
   */
  Collection getSubCollectionData(iWidget context, String name, boolean copy);

  /**
   * Returns a named sub-collection
   *
   * @param context
   *          the context
   * @param name
   *          the name of the sub-collection
   * @param copy
   *          true to make a copy of the items; false otherwise
   *
   * @return the sub-collection
   */
  Collection<RenderableDataItem> getSubItemData(iWidget context, String name, boolean copy);

  /**
   * Returns a view of the portion of this collection between the specified
   * fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are
   * equal, the returned collection is empty.)
   *
   * @param context
   *          the context
   * @param fromIndex
   *          low endpoint (inclusive) of the rows (use -1 for all rows)
   * @param toIndex
   *          the high endpoint (exclusive) of the rows (use -1 for the end of
   *          the list or rows)
   * @param fromColumn
   *          low endpoint (inclusive) of the columns (use -1 for all columns)
   * @param toColumn
   *          the high endpoint (exclusive) of the columns (use -1 the end of
   *          the list columns, use zero to represent a singular value in
   *          non-tabular form)
   * @param copy
   *          true to make a copy of the items; false otherwise
   *
   * @return the sub-collection
   */
  Collection<RenderableDataItem> getSubItemData(iWidget context, int fromIndex, int toIndex, int fromColumn,
          int toColumn, boolean copy);

  /**
   * Converts a collection to a list If the collection is already a list then
   * the collection is returned
   *
   * @param coll
   *          the collection to convert
   * @return the list representing the collection
   */
  List toList(Collection coll);

  /**
   * Returns whether the loading of the collection has completed
   *
   * @return true is the collections is loaded; false otherwise
   */
  boolean isLoaded();

  /**
   * Gets the size of the collection
   *
   * @return the size of the collection
   */
  int size();

  /**
   * Returns whether the collection is empty
   *
   * @return true is the collections is empty; false otherwise
   */
  boolean isEmpty();

  /**
   * Sets the name of the collection
   *
   * @param name
   *          the name
   */
  void setCollectionName(String name);

  /**
   * Returns whether the collection should be refreshed when it is accessed via
   * a URL connection.
   *
   * @return true if it should; false otherwise.
   */
  boolean isRefreshOnURLConnection();

  /**
   * Sets whether the collection should be refreshed when it is accessed via a
   * URL connection.
   *
   * @param refresh
   *          true if it should; false otherwise.
   */
  void setRefreshOnURLConnection(boolean refresh);
}
