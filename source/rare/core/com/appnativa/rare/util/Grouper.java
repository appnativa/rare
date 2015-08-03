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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.iCancelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class provides grouping functionality for list if items
 * The functionality is equivalent to the 'GROUP BY' SQL construct.
 *
 * @author Don DeCoteau
 */
public class Grouper {
  private static String[]                 ZERO_NONE = new String[0];
  private UIFont                          boldFont;
  private boolean                         boldGroupTitles;
  private int[]                           cols;
  private boolean                         flatFormat;
  private boolean                         forTable;
  private Map<String, RenderableDataItem> map;
  private String[]                        nulls;
  private boolean                         preserveFirst;
  private boolean                         preserveLinkedData;
  private boolean                         preserveRest;
  private SubItemComparator               rowComparator;
  private int                             sortOrder;
  private RenderableDataItem[]            titleItems;
  private boolean                         titleSelectable;
  private int                             groupHeaderColumnSpan = -1;

  /**
   * Creates a new instance
   */
  public Grouper() {}

  /**
   * Creates a new instance
   *
   * @param cols the columns
   * @param nones the array of string corresponding to the array of columns
   * @param preserveFirst true to preserve the value of the first grouping column; false otherwise
   * @param preserveRest true to preserve the value of subsequent grouping columns; false otherwise
   */
  public Grouper(int[] cols, String nones[], boolean preserveFirst, boolean preserveRest) {
    setCriteria(cols, nones, preserveFirst, preserveRest);
  }

  /**
   * Groups the specified list based on the grouper's criteria
   * on a background thread.
   * The resultValue will be an ObjectHolder with the list
   * as the source the context as the type and the value
   * as a {@link List}
   *
   * @param context the widget context
   * @param list the list to group
   *
   * @return the grouped list
   *
   * @see ObjectHolder
   */
  public iCancelable groupInBackground(final iWidget context, final List<RenderableDataItem> list,
          final iFunctionCallback cb) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        final boolean canceled = (context == null)
                                 ? false
                                 : context.isDisposed();;
        final Object  ret      = canceled
                                 ? null
                                 : new ObjectHolder(context, null, group(context, list));

        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            cb.finished(canceled, new ObjectHolder(list, context, ret));
          }
        });
      }
    };

    return Platform.getAppContext().executeBackgroundTask(r);
  }

  /**
   * Groups the specified list based on the grouper's criteria
   *
   * @param context the widget context
   * @param list the list to group
   *
   * @return the grouped list
   */
  public List<RenderableDataItem> group(iWidget context, List<RenderableDataItem> list) {
    if ((getColumns() == null) || (getColumns().length == 0) || (list.size() == 0)) {
      return list;
    }

    if (nulls == null) {
      nulls = ZERO_NONE;
    }

    boldFont = null;

    if (boldGroupTitles && (context != null)) {
      boldFont = context.getFont().deriveBold();
    }

    RenderableDataItem item = new RenderableDataItem();

    group(context, list, 0, item);
    list = item.getItems();

    if (isFormatForTable()) {
      fixForTable(list);
    }

    return (list == null)
           ? Collections.EMPTY_LIST
           : list;
  }

  public static List<RenderableDataItem> groupByRowInfo(List<RenderableDataItem> list, int expandableColumn) {
    RenderableDataItem             currentRow = null;
    LinkedList<RenderableDataItem> levelStack = new LinkedList<RenderableDataItem>();
    ArrayList<RenderableDataItem>  rows       = new ArrayList<RenderableDataItem>();
    final int                      len        = list.size();
    RenderableDataItem             row;

    for (int i = 0; i < len; i++) {
      row = list.get(i);

      Object o = row.getModelData();
      int    l = 1;

      if (o != null) {
        MutableInteger level = (MutableInteger) o;

        row.setModelData(null);
        l = level.intValue();

        if (l < 1) {
          l = 1;
        }
      }

      if ((l < 2) || (currentRow == null)) {
        rows.add(row);
        currentRow = row;

        if (levelStack != null) {
          levelStack.clear();
        }
      } else {
        RenderableDataItem ci = null;

        if (levelStack == null) {
          levelStack = new LinkedList<RenderableDataItem>();
        }

        int currentLevel = levelStack.size() + 1;

        if (currentLevel > l) {
          while(currentLevel > l) {
            levelStack.poll();
            currentLevel--;
          }

          levelStack.peek().add(row);
        } else if (currentLevel == l) {
          levelStack.peek().add(row);
        } else {
          ci = currentRow;
          ci = ci.getItem(expandableColumn);
          ci.add(row);

          if (levelStack.peek() != currentRow) {
            levelStack.add(0, ci);
          }
        }

        currentRow = row;
      }
    }

    return rows;
  }

  /**
   * Sets whether group titles should be bolded
   *
   * @param bold true to bold; false otherwise
   */
  public void setBoldGroupTitles(boolean bold) {
    this.boldGroupTitles = bold;
  }

  /**
   * Sets the columns that lists will be grouped by
   *
   * @param cols the columns
   */
  public void setColumns(int[] cols) {
    this.cols = cols;
  }

  /**
   * Sets the criteria for the grouper
   *
   * @param cols the columns
   * @param nones the array of string corresponding to the array of columns
   * @param preserveFirst true to preserve the value of the first grouping column; false otherwise
   * @param preserveRest true to preserve the value of subsequent grouping columns; false otherwise
   */
  public void setCriteria(int[] cols, String nones[], boolean preserveFirst, boolean preserveRest) {
    this.cols          = cols;
    this.nulls         = nones;
    this.preserveFirst = preserveFirst;
    this.preserveRest  = preserveRest;
  }

  /**
   * @param flatFormat the flatFormat to set
   */
  public void setFlatFormat(boolean flatFormat) {
    this.flatFormat = flatFormat;
  }

  /**
   * Sets whether the grouper should format it results for a table
   *
   * @param forTable true for a table (the default); false otherwise
   */
  public void setFormatForTable(boolean forTable) {
    this.forTable = forTable;
  }

  /**
   * Sets the items that will be used as a prototype for group titles (categories)
   * The color, font, .etc of the items will be used for group titles.(categories)
   *
   * @param groupItems the groups items array corresponding the the columns that will be used to create the group
   */
  public void setGroupTitleItems(RenderableDataItem[] groupItems) {
    this.titleItems = groupItems;
  }

  /**
   * Sets the string values to use when the value in a grouping column is null
   *
   * @param nones the array of string corresponding to the array of columns
   */
  public void setNulls(String[] nones) {
    this.nulls = nones;
  }

  /**
   * Sets whether the value of the first grouping column is preserved
   * If set to false the value of the column's value will be replaced by a null (the original
   * item is not modified). The value will then only exist as the header for the group
   *
   * @param preserveFirst true to preserve the value of the first grouping column; false otherwise
   */
  public void setPreserveFirst(boolean preserveFirst) {
    this.preserveFirst = preserveFirst;
  }

  /**
   * Sets whether the linked data is copied from a source item to create a group header item
   *
   * @param preserve true to preserve the linked data when creating a grouping column; false otherwise
   */
  public void setPreserveLinkedData(boolean preserve) {
    this.preserveLinkedData = preserve;
  }

  /**
   * Sets whether the value of subsequent grouping columns are  preserved
   * If set to false the value of the column's value will be replaced by a null (the original
   * item is not modified). The value will then only exist as the header for the group
   *
   * @param preserveRest true to preserve the value of subsequent grouping columns; false otherwise
   */
  public void setPreserveRest(boolean preserveRest) {
    this.preserveRest = preserveRest;
  }

  /**
   * Sets the sort order for grouping
   *
   * @param sortOrder the sort order -1 = descending,  0 = not sorting, 1 = ascending
   */
  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  /**
   * @param titleSelectable the titleSelectable to set
   */
  public void setTitlesSelectable(boolean titleSelectable) {
    this.titleSelectable = titleSelectable;
  }

  /**
   * Gets the columns that lists will be grouped by
   *
   * @return the columns
   */
  public int[] getColumns() {
    return cols;
  }

  /**
   * Gets the items that will be used as a prototype for group titles (categories)
   * The color, font, .etc of the items will be used for group titles.(categories)
   *
   * @return the groups items array corresponding the the columns that will be used to create the group
   */
  public RenderableDataItem[] getGroupTitleItems() {
    return titleItems;
  }

  /**
   * Gets the string values to use when the value in a grouping column is null
   *
   * @return the array of string corresponding to the array of columns
   */
  public String[] getNulls() {
    return nulls;
  }

  /**
   * Gets the sort order for grouping
   *
   * @return sortOrder the sort order -1 = descending,  0 = not sorting, 1 = ascending
   */
  public int getSortOrder() {
    return sortOrder;
  }

  /**
   * Gets whether group titles should be bolded
   *
   * @return true for bold; false toehrwise
   */
  public boolean isBoldGroupTitles() {
    return boldGroupTitles;
  }

  /**
   * @return the flatFormat
   */
  public boolean isFlatFormat() {
    return flatFormat;
  }

  /**
   * Gets whether the grouper should format it results for a table
   *
   * @return true for a table format (the default); false otherwise
   */
  public boolean isFormatForTable() {
    return forTable;
  }

  /**
   * Gets whether the value of the first grouping column is preserved
   *
   * @return true to preserve the value of the first grouping column; false otherwise
   */
  public boolean isPreserveFirst() {
    return preserveFirst;
  }

  /**
   * Gets whether the linked data is copied from a source item to create a group header item
   *
   * @return true to preserve the linked data when creating a grouping column; false otherwise
   */
  public boolean isPreserveLinkedData() {
    return preserveLinkedData;
  }

  /**
   * Gets whether the value of subsequent grouping columns are  preserved
   *
   * @return true to preserve the value of subsequent grouping columns; false otherwise
   */
  public boolean isPreserveRest() {
    return preserveRest;
  }

  /**
   * Performs the grouping
   *
   * @param context the context widget
   * @param list the list to be grouped
   * @param pos the positing within the columns array to group on
   * @param output the item to use to hold the results of the grouping
   */
  protected void group(iWidget context, List<RenderableDataItem> list, int pos, RenderableDataItem output) {
    int len = (list == null)
              ? 0
              : list.size();

    if (len == 0) {
      return;
    }

    if (map == null) {
      map = new LinkedHashMap<String, RenderableDataItem>();
    } else {
      map.clear();
    }

    String  none     = (pos < nulls.length)
                       ? nulls[pos]
                       : null;
    boolean preserve = (pos == 0)
                       ? isPreserveFirst()
                       : isPreserveRest();
    int     col      = cols[pos];

    if (none == null) {
      none = "(none)";
    }

    RenderableDataItem pitem;
    RenderableDataItem item;
    RenderableDataItem gitem;
    String             s;

    for (int i = 0; i < len; i++) {
      pitem = list.get(i);

      if (pitem == null) {
        continue;
      }

      gitem = pitem.getItemEx(col);
      s     = (gitem == null)
              ? ""
              : gitem.toString(context);

      if ("".equals(s)) {
        s = none;
      }

      if (!preserve) {
        item = (gitem == null)
               ? new RenderableDataItem()
               : gitem.copy();
        item.setValue(null);
        pitem.setItem(col, item);
      }

      item = map.get(s);

      if (item == null) {
        item = new RenderableDataItem();

        if ((titleItems != null) && (pos < titleItems.length)) {
          item.copyEx(titleItems[pos]);
          item.setValue(s);
        } else if (gitem != null) {
          item.copyValue(gitem);

          if (preserveLinkedData) {
            item.setLinkedData(gitem.getLinkedData());
            item.setLinkedDataConverter(gitem.getLinkedDataConverter());
            item.setLinkedDataContext(gitem.getLinkedDataContext());
          }
        } else {
          item.setValue(s);
        }

        if ((boldFont != null) && (item.getFont() == null)) {
          item.setFont(boldFont);
        }

        map.put(s, item);
        item.setColumnSpan(-1);
        item.setSelectable(titleSelectable);
      }

      item.add(pitem);
    }

    output.addAll(map.values());

    if (getSortOrder() != 0) {
      if (rowComparator == null) {
        rowComparator = new SubItemComparator();
      }

      rowComparator.setOptions(-1, sortOrder < 0);
      output.sort(rowComparator);
    }

    pos++;

    if (pos < getColumns().length) {
      list = output.getItems();
      len  = list.size();

      final boolean ft = isFormatForTable();

      for (int i = 0; i < len; i++) {
        pitem = new RenderableDataItem();
        item  = list.get(i);
        pitem.copyEx(item);
        group(context, item.getItems(), pos, pitem);

        if (ft) {
          fixForTable(pitem.getItems());
        }

        list.set(i, pitem);
      }
    }
  }

  private void fixForTable(List<RenderableDataItem> list) {
    int                len = (list == null)
                             ? 0
                             : list.size();
    RenderableDataItem item;
    RenderableDataItem row;

    for (int i = 0; i < len; i++) {
      row  = new RenderableDataItem();
      item = list.get(i);
      row.add(item);
      list.set(i, row);
    }
  }

  public int getGroupHeaderColumnSpan() {
    return groupHeaderColumnSpan;
  }

  public void setGroupHeaderColumnSpan(int groupHeaderColumnSpan) {
    this.groupHeaderColumnSpan = groupHeaderColumnSpan;
  }
}
