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

import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTableModel;

import java.util.Comparator;

/**
 * Class to compare sub-items
 *
 * @author Don DeCoteau
 */
public class SubItemComparator implements Comparator {
  private static SubItemComparator _reverseSorter;
  iTableModel                      tableHandler;
  protected int                    compareColumn = -1;
  private boolean                  descending    = false;
  private Comparator               comparator;
  private boolean                  useLinkedData;

  /**
   * Creates a new instance
   */
  public SubItemComparator() {}

  /**
   * Creates a new instance of SubItemComparator
   *
   * @param c an optional comparator
   */
  public SubItemComparator(Comparator c) {
    comparator = c;
  }

  /**
   * Creates a new instance
   *
   * @param th a table handler to use for table data conversions
   */
  public SubItemComparator(iTableModel th) {
    tableHandler = th;
  }

  /**
   * Creates a new instance
   *
   * @param th a table handler to use for table data conversions
   * @param descending true for a descending sort; false for ascending
   */
  public SubItemComparator(iTableModel th, boolean descending) {
    tableHandler    = th;
    this.descending = descending;
  }

  @Override
  public int compare(Object o1, Object o2) {
    int n = (compareColumn == -1)
            ? rowCompareEx((RenderableDataItem) o1, (RenderableDataItem) o2)
            : compareEx(o1, o2);

    return descending
           ? -n
           : n;
  }

  public int compare(RenderableDataItem o1, RenderableDataItem o2) {
    int n = (compareColumn == -1)
            ? rowCompareEx(o1, o2)
            : compareEx(o1, o2);

    return descending
           ? -n
           : n;
  }

  /**
   * Compares the value  data item with another.
   * Sub-items are not compared
   *
   * @param item1 the first item
   * @param item2 the second item
   * @param c an optional comparator
   *
   *   @return a negative integer, zero, or a positive integer as the
   *           first argument is less than, equal to, or greater than the
   *           second.
   *   @throws ClassCastException if the arguments types prevent them from
   *           being compared by this comparator.
   */
  public static int compare(RenderableDataItem item1, RenderableDataItem item2, Comparator c, boolean useLinkedData) {
    if (item1 == item2) {
      return 0;
    }

    if ((item1 == null) || (item2 == null)) {
      return (item1 == null)
             ? -1
             : 1;
    }

    Object v1 = useLinkedData
                ? item1.getLinkedData()
                : item1.getValue();
    Object v2 = useLinkedData
                ? item2.getLinkedData()
                : item2.getValue();

    if (v1 == v2) {
      if (v1 == null) {
        iPlatformIcon ic1 = item1.getIcon();
        iPlatformIcon ic2 = item2.getIcon();

        if (ic1 == null) {
          return (ic2 == null)
                 ? 0
                 : -1;
        }

        if (ic2 == null) {
          return 1;
        }
      }

      return 0;
    }

    if ((v1 == null) || (v2 == null)) {
      return (v1 == null)
             ? -1
             : 1;
    }

    if (c != null) {
      return c.compare(v1, v2);
    }

    if (!(v1 instanceof Comparable)) {
      return v1.toString().compareTo(v2.toString());
    }

    if (!v1.getClass().isInstance(v2)) {
      return 0;
    }

    try {
      return ((Comparable) v1).compareTo(v2);
    } catch(ClassCastException e) {
      return 0;
    }
  }

  public static int compareObjects(Object v1, Object v2) {
    if (v1 == v2) {
      return 0;
    }

    if ((v1 == null) || (v2 == null)) {
      return (v1 == null)
             ? -1
             : 1;
    }

    if (!(v1 instanceof Comparable)) {
      return 0;
    }

    if (!v1.getClass().isInstance(v2)) {
      return 0;
    }

    try {
      return ((Comparable) v1).compareTo(v2);
    } catch(ClassCastException e) {
      return 0;
    }
  }

  /**
   * Sets the column to use when comparing
   *
   * @param col the column
   */
  public void setColumn(int col) {
    compareColumn = col;
  }

  /**
   * Sets the comparator to use
   *
   * @param c an optional comparator
   */
  public void setComparator(Comparator c) {
    comparator = c;
  }

  /**
   * Sets the options
   *
   * @param col the column to compare by
   * @param desc true to sort descending; false otherwise
   */
  public void setOptions(int col, boolean desc) {
    compareColumn = col;
    descending    = desc;
  }

  /**
   * Sets the options
   *
   * @param c an optional comparator
   * @param col the column to compare by
   * @param desc true to sort descending; false otherwise
   */
  public void setOptions(Comparator c, int col, boolean desc) {
    comparator    = c;
    compareColumn = col;
    descending    = desc;
  }

  public void setUseLinkedData(boolean useLinkedData) {
    this.useLinkedData = useLinkedData;
  }

  public static Comparator getReverseOrderComparatorInstance() {
    if (_reverseSorter == null) {
      _reverseSorter = new SubItemComparator(null, true);
    }

    return _reverseSorter;
  }

  /**
   * Gets the column that will be compared
   *
   * @return the current compare column or -1 if no column was specified
   */
  public int getSortColumn() {
    return compareColumn;
  }

  /**
   * Returns if the current comparison is in descending order
   *
   * @return true if the current comparison is in descending order; false otherwise
   */
  public boolean isSortDescending() {
    return descending;
  }

  public boolean isUseLinkedData() {
    return useLinkedData;
  }

  protected int rowCompareEx(RenderableDataItem item1, RenderableDataItem item2) {
    if (item1 == item2) {
      return 0;
    }

    if ((item1 == null) || (item2 == null)) {
      return (item1 == null)
             ? -1
             : 1;
    }

    return compare(item1, item2, comparator, useLinkedData);
  }

  private int compareEx(Object o1, Object o2) {
    RenderableDataItem item1 = (RenderableDataItem) o1;
    RenderableDataItem item2 = (RenderableDataItem) o2;

    if (item1 == item2) {
      return 0;
    }

    if ((item1 == null) || (item2 == null)) {
      return (item1 == null)
             ? -1
             : 1;
    }

    item1 = item1.getItemEx(compareColumn);
    item2 = item2.getItemEx(compareColumn);

    if (item1 == item2) {
      return 0;
    }

    if ((item1 == null) || (item2 == null)) {
      return (item1 == null)
             ? -1
             : 1;
    }

    if (tableHandler != null) {
      if (!item1.isConverted()) {
        convert(item1, compareColumn);
      }

      if (!item2.isConverted()) {
        convert(item2, compareColumn);
      }
    }

    return compare(item1, item2, comparator, useLinkedData);
  }

  private void convert(RenderableDataItem di, int col) {
    Column         c   = tableHandler.getColumn(col);
    iDataConverter cvt = c.getDataConverter();

    if (cvt != null) {
      di.convert(tableHandler.getWidget().getViewer(), c.getType(), cvt, c.getValueContext());
    }
  }
}
