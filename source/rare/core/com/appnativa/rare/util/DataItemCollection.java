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

package com.appnativa.rare.util;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.iFilterableList;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class DataItemCollection implements iDataCollection {
  private static String              MIME_TYPE = iConstants.TEXT_MIME_TYPE + ";type=csv";
  protected Map<String, Object>      attributeMap;
  protected iListHandler             listComponent;
  protected boolean                  shallowCopy;
  protected List<RenderableDataItem> theList;
  protected String                   theName;
  private boolean                    componentSelection;
  protected boolean                  loaded;
  private boolean                    refreshOnConnection;
  private CharSequence               emptyCollectionText;

  public DataItemCollection(String name, iListHandler listComponent, boolean selection) {
    theName            = name;
    this.listComponent = listComponent;
    componentSelection = selection;
    loaded             = listComponent != null;
  }

  public DataItemCollection(String name, List<RenderableDataItem> list) {
    theName = name;
    theList = list;
    loaded  = list != null;
  }

  @Override
  public void clearCollection() {
    if (theList != null) {
      theList.clear();
    }

    loaded = false;
  }

  @Override
  public void disposeCollection() {
    clearCollection();

    if (attributeMap != null) {
      attributeMap.clear();
    }

    theList       = null;
    listComponent = null;
    attributeMap  = null;
    loaded        = false;
  }

  @Override
  public Object getAttribute(iWidget context, String name) {
    return (attributeMap == null)
           ? null
           : attributeMap.get(name);
  }

  @Override
  public Collection getCollection(iWidget context) {
    return getSubItemData(context, -1, -1, -1, -1, false);
  }

  @Override
  public String getCollectionAsString(iWidget context) {
    List<RenderableDataItem> list    = getList(context);
    boolean                  tabular = (listComponent != null) && listComponent.isTabular();

    return getValuesAsString(list, false, tabular, "|");
  }

  @Override
  public String getCollectionName() {
    return theName;
  }

  @Override
  public String getCollectionStringMimeType(iWidget context) {
    return MIME_TYPE;
  }

  @Override
  public CharSequence getEmptyCollectionText() {
    return emptyCollectionText;
  }

  @Override
  public Collection<RenderableDataItem> getItemData(iWidget context, boolean copy) {
    return getSubItemData(context, -1, -1, -1, -1, copy);
  }

  public List<RenderableDataItem> getListEx() {
    return theList;
  }

  @Override
  public Collection getSubCollectionData(iWidget context, String name, boolean copy) {
    return getSubItemData(context, name, false);
  }

  public Collection<RenderableDataItem> getSubItemCollection(iWidget context, int fromIndex, int toIndex,
          boolean copy) {
    return getSubItemData(context, fromIndex, toIndex, -1, -1, copy);
  }

  @Override
  public Collection<RenderableDataItem> getSubItemData(iWidget context, int fromIndex, int toIndex, int fromColumn,
          int toColumn, boolean copy) {
    List<RenderableDataItem> list = getList(context);

    if (list.size() == 0) {
      return list;
    }

    if (!copy) {
      return Collections.unmodifiableCollection(create(list, fromIndex, toIndex, fromColumn, toColumn));
    }

    return copy(list, fromIndex, toIndex, fromColumn, toColumn, shallowCopy);
  }

  @Override
  public Collection<RenderableDataItem> getSubItemData(iWidget context, String name, boolean copy) {
    if (name == null) {
      return getSubItemData(context, -1, -1, -1, -1, copy);
    }

    return Collections.EMPTY_LIST;
  }

  public Collection group(iWidget context, Grouper g) {
    List<RenderableDataItem> list = getList(context);

    list = copy(list, -1, -1, -1, -1, shallowCopy);

    return g.group(context, list);
  }

  public Collection groupBy(iWidget context, int col, boolean flat) {
    int cols[] = new int[] { col };

    return groupBy(context, cols, flat);
  }

  public Collection groupBy(iWidget context, int col1, int col2, boolean flat) {
    int cols[] = new int[] { col1, col2 };

    return groupBy(context, cols, flat);
  }

  public Collection groupBy(iWidget context, int[] cols, boolean flat) {
    Grouper gp = new Grouper();

    gp.setColumns(cols);
    gp.setPreserveFirst(true);
    gp.setPreserveRest(true);
    gp.setFlatFormat(flat);

    return group(context, gp);
  }

  @Override
  public boolean isEmpty() {
    return loaded
           ? ((theList == null)
              ? true
              : theList.isEmpty())
           : true;
  }

  @Override
  public boolean isLoaded() {
    return loaded;
  }

  @Override
  public boolean isRefreshOnURLConnection() {
    return refreshOnConnection;
  }

  @Override
  public void refresh(iWidget context) throws IOException {}

  @Override
  public void setAttribute(iWidget context, String name, Object value) {
    if (attributeMap == null) {
      attributeMap = new HashMap<String, Object>();
    }

    attributeMap.put(name, value);
  }

  public void setCollectionData(List<RenderableDataItem> list) {
    if (theList != null) {
      theList.clear();
      theList.addAll(list);
    } else {
      theList = new ArrayList<RenderableDataItem>(list);
    }

    loaded = true;
  }

  public void setCollectionName(String name) {
    theName = name;
  }

  @Override
  public void setEmptyCollectionText(CharSequence text) {
    emptyCollectionText = text;
  }

  @Override
  public void setRefreshOnURLConnection(boolean refresh) {
    refreshOnConnection = refresh;
  }

  @Override
  public int size() {
    return loaded
           ? ((theList == null)
              ? 0
              : theList.size())
           : 0;
  }

  @Override
  public List toList(Collection coll) {
    if (coll == null) {
      return null;
    }

    if (coll instanceof List) {
      return (List) coll;
    }

    return new FilterableList(coll);
  }

  @Override
  public String toString() {
    return this.getCollectionAsString(null);
  }

  protected List<RenderableDataItem> getList(iWidget context) {
    List<RenderableDataItem> list = theList;

    if (listComponent != null) {
      if (componentSelection) {
        RenderableDataItem[] a = getItems(listComponent, componentSelection
                ? listComponent.getSelectedIndexes()
                : null, -1);

        list = (a == null)
               ? Collections.EMPTY_LIST
               : Arrays.asList(a);
      } else {
        list = listComponent.getRows();
      }
    }

    return (list == null)
           ? Collections.EMPTY_LIST
           : list;
  }

  public static DataItemCollection createCollection(String name, List list) {
    return new DataItemCollection(name, createItemList(list));
  }

  public static DataItemCollection createCollection(String name, Object[] list) {
    return new DataItemCollection(name, createItemList(Arrays.asList(list)));
  }

  /**
   * Returns an array of the values for the specified rows
   *
   * @param list
   *          the list to retrieve the items from
   * @param rows
   *          the set of rows
   * @param col
   *          the column to retrieve or -1 for all columns
   * @return an array of the values for the specified rows
   */
  public static RenderableDataItem[] getItems(List<RenderableDataItem> list, int[] rows, int col) {
    int len = (list == null)
              ? 0
              : list.size();

    if (rows != null) {
      len = rows.length;
    }

    if (len == 0) {
      return null;
    }

    RenderableDataItem[] a = new RenderableDataItem[len];
    RenderableDataItem   di;

    for (int i = 0; i < len; i++) {
      di = list.get((rows == null)
                    ? i
                    : rows[i]);

      if ((col != -1) && (di != null)) {
        di = di.getItemEx(col);
      }

      if (di != null) {
        a[i] = di;
      }
    }

    return a;
  }

  /**
   * Get the selection from a component
   *
   * @param listComponent
   *          the component to get the selection from
   * @param multiple
   *          true to delete the selection; false otherwise
   * @param col
   *          the column to get the selection for
   * @return the selection form a component
   */
  public static Object getSelection(iListHandler listComponent, boolean multiple, int col) {
    if (multiple) {    // multiple selection
      return getSelections(listComponent, col);
    }

    RenderableDataItem di = listComponent.getSelectedItem();

    return ((di == null) || (col == -1))
           ? di
           : di.getItemEx(col);
  }

  /**
   * Get the data associated with the selection from a component
   *
   * @param listComponent
   *          the component to get the selection from
   * @param multiple
   *          true to delete the selection; false otherwise
   * @param col
   *          the column to get the selection for
   * @return the selection form a component
   */
  public static Object getSelectionData(iListHandler listComponent, boolean multiple, int col) {
    if (multiple) {    // multiple selection
      return getValues(listComponent, listComponent.getSelectedIndexes(), col, true);
    }

    RenderableDataItem di = listComponent.getSelectedItem();

    di = ((di == null) || (col == -1))
         ? di
         : di.getItemEx(col);

    return (di == null)
           ? null
           : di.getLinkedData();
  }

  /**
   * Get the selections from a component
   *
   * @param listComponent
   *          the component to get the selection from
   * @param col
   *          the column to get the selection for
   * @return the selections form a component
   */
  public static RenderableDataItem[] getSelections(iListHandler listComponent, int col) {
    return getItems(listComponent, listComponent.getSelectedIndexes(), col);
  }

  /**
   * Returns a string array of the linked data for all of the selected rows, in
   * increasing order.
   *
   * @param listComponent
   *          the component
   * @param col
   *          the column to get the selection data for
   * @return a string array of the linked data for all of the selected rows, or
   *         null if there are no selections
   */
  public static String[] getSelectionsDataAsStrings(iListHandler listComponent, int col) {
    return getValuesAsStrings(listComponent, listComponent.getSelectedIndexes(), col, true, false, null);
  }

  /**
   * Returns a array of the values for the specified rows
   *
   * @param list
   *          the list to retrieve the items from
   * @param rows
   *          the set of rows or null to use the whole list
   * @param col
   *          the column to retrieve or -1 for all columns
   * @param data
   *          true to return the associated data; false to return the actual
   *          value
   * @return a string array of the values for the specified rows
   */
  public static Object[] getValues(List<RenderableDataItem> list, int[] rows, int col, boolean data) {
    int len = (list == null)
              ? 0
              : list.size();

    if (rows != null) {
      len = rows.length;
    }

    if (len == 0) {
      return null;
    }

    Object[]           a = new Object[len];
    RenderableDataItem di;

    for (int i = 0; i < len; i++) {
      di = list.get((rows == null)
                    ? i
                    : rows[i]);

      if ((col != -1) && (di != null)) {
        di = di.getItemEx(col);
      }

      if (di == null) {
        continue;
      }

      a[i] = data
             ? di.getLinkedData()
             : di;
    }

    return a;
  }

  /**
   * Returns a string representing all the values of the specified component
   *
   * @param list
   *          the list to retrieve the items from
   * @param data
   *          true to return the associated data; false to return the actual
   *          value
   * @param row
   *          true if the values should be treated as a multi-column row; false
   *          otherwise
   * @param sep
   *          the separator to use to separate columns in a row
   * @return a string representing all the values of the specified component
   */
  public static String getValuesAsString(List<RenderableDataItem> list, boolean data, boolean row, String sep) {
    return getValuesAsString(null, list, null, -1, data, row, sep, null).toString();
  }

  /**
   * Returns a string representing all the values of the specified component
   *
   * @param sb
   *          a string builder object to use to store the data
   * @param list
   *          the list to retrieve the items from
   * @param rows
   *          the set of rows or null to use the whole list
   * @param col
   *          the column to retrieve or -1 for all columns
   * @param data
   *          true to return the associated data; false to return the actual
   *          value
   * @param row
   *          true if the values should be treated as a multi-column row; false
   *          otherwise
   * @param csep
   *          the separator to use to separate columns in a row
   * @param rsep
   *          the separator to use to separate rows
   * @return a string representing all the values of the specified component
   */
  public static StringBuilder getValuesAsString(StringBuilder sb, List<RenderableDataItem> list, int[] rows, int col,
          boolean data, boolean row, String csep, String rsep) {
    if (rsep == null) {
      rsep = "\r\n";
    }

    return Helper.toString(sb, Arrays.asList(getValuesAsStrings(list, rows, col, data, row, csep)), rsep);
  }

  /**
   * Returns a string array of the values for the specified rows
   *
   * @param list
   *          the list to retrieve the items from
   * @param rows
   *          the set of rows or null to use the whole list
   * @param col
   *          the column to retrieve or -1 for all columns
   * @param data
   *          true to return the associated data; false to return the actual
   *          value
   * @param row
   *          true if the values should be treated as a multi-column row; false
   *          otherwise
   * @param sep
   *          the separator to use to separate columns in a row
   * @return a string array of the values for the specified rows
   */
  public static String[] getValuesAsStrings(List<RenderableDataItem> list, int[] rows, int col, boolean data,
          boolean row, String sep) {
    int len = (list == null)
              ? 0
              : list.size();

    if (rows != null) {
      len = rows.length;
    }

    if (len == 0) {
      return null;
    }

    if (sep == null) {
      sep = "\t";
    }

    String[]           a = new String[len];
    RenderableDataItem di;
    Object             o;
    StringBuilder      sb = null;

    if (row && (col != -1)) {
      row = false;
    } else if (!row) {
      col = -1;
    }

    if (row) {
      sb = new StringBuilder();
    }

    for (int i = 0; i < len; i++) {
      di = list.get((rows == null)
                    ? i
                    : rows[i]);

      if ((col != -1) && (di != null)) {
        di = di.getItemEx(col);
      }

      if (di == null) {
        continue;
      }

      if (row && (di.size() > 0)) {
        sb.setLength(0);
        a[i] = getValuesAsString(sb, di.getItems(), null, -1, data, false, null, sep).toString();
      } else {
        o    = data
               ? di.getLinkedData()
               : di;
        a[i] = (o == null)
               ? null
               : o.toString();
      }
    }

    return a;
  }

  private static iFilterableList<RenderableDataItem> copy(List<RenderableDataItem> list, int fromIndex, int toIndex,
          int fromCol, int toCol, boolean shallow) {
    int len = (list == null)
              ? 0
              : list.size();

    if ((toIndex > len) || (toIndex == -1)) {
      toIndex = len;
    }

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    FilterableList a = new FilterableList<RenderableDataItem>(toIndex - fromIndex);

    for (int i = fromIndex; i < toIndex; i++) {
      a.add(copy(list.get(i), fromCol, toCol, shallow));
    }

    return a;
  }

  private static RenderableDataItem copy(RenderableDataItem row, int from, int to, boolean shallow) {
    if ((to == 0) && (from > -1)) {
      row = row.getItemEx(from);
    }

    if ((from < 0) || (to == 0)) {
      if (row == null) {
        return null;
      }

      return shallow
             ? row.copy()
             : row.deepCopy();
    }

    return RenderableDataItem.toParentItem(copy(row.getItems(), from, to, -1, -1, shallow));
  }

  private static List<RenderableDataItem> create(List<RenderableDataItem> list, int fromIndex, int toIndex,
          int fromCol, int toCol) {
    int len = (list == null)
              ? 0
              : list.size();

    if ((toIndex > len) || (toIndex == -1)) {
      toIndex = len;
    }

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (fromCol == -1) {
      return ((fromIndex == 0) && (toIndex == len))
             ? list
             : subList(list, fromIndex, toIndex);
    }

    FilterableList a = new FilterableList<RenderableDataItem>(toIndex - fromIndex);

    for (int i = fromIndex; i < toIndex; i++) {
      a.add(create(list.get(i), fromCol, toCol));
    }

    return a;
  }

  private static RenderableDataItem create(RenderableDataItem row, int fromIndex, int toIndex) {
    if ((toIndex == 0) && (fromIndex > -1)) {
      row = row.getItemEx(fromIndex);
    }

    if (row == null) {
      return null;
    }

    if ((fromIndex < 0) || (toIndex == 0)) {
      return row;
    }

    int len = row.getItemCount();

    if ((toIndex > len) || (toIndex == -1)) {
      toIndex = len;
    }

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    return RenderableDataItem.toParentItem((subList(row.getItems(), fromIndex, toIndex)));
  }

  private static List<RenderableDataItem> createItemList(List list) {
    int            len = (list == null)
                         ? 0
                         : list.size();
    FilterableList a   = new FilterableList<RenderableDataItem>(len);

    for (int i = 0; i < len; i++) {
      a.add((RenderableDataItem.toItem(list.get(i))));
    }

    return a;
  }

  private static iFilterableList<RenderableDataItem> subList(List<RenderableDataItem> list, int fromIndex,
          int toIndex) {
    FilterableList a = new FilterableList<RenderableDataItem>(toIndex - fromIndex);

    for (int i = fromIndex; i < toIndex; i++) {
      a.add(list.get(i));
    }

    return a;
  }
}
