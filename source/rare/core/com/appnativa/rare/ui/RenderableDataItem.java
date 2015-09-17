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

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.BooleanConverter;
import com.appnativa.rare.converters.DateConverter;
import com.appnativa.rare.converters.DateTimeConverter;
import com.appnativa.rare.converters.NumberConverter;
import com.appnativa.rare.converters.StringConverter;
import com.appnativa.rare.converters.TimeConverter;
import com.appnativa.rare.converters.WidgetConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.scripting.FunctionHelper;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.ui.event.ScriptActionListener;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilterableList;
import com.appnativa.util.Helper;
import com.appnativa.util.IntList;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.SNumber;
import com.appnativa.util.aFilterableList;
import com.appnativa.util.iFilter;
import com.appnativa.util.iFilterableList;
import com.appnativa.util.iStringConverter;

import com.google.j2objc.annotations.Weak;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents an item that is meant to be rendered on a graphical
 * device. It serves as the base class for all widgets as well as the main item
 * that is utilized to populate list type widgets
 *
 * @author Don DeCoteau
 */
public class RenderableDataItem
        implements iFilterableList<RenderableDataItem>, Cloneable, iPainterSupport, Comparable<RenderableDataItem> {

  /** for internal use only */
  public static final String TREE_MODEL_DATA = "TREE_MODEL_DATA";

  /** value is a one-dimensional array */
  public static final int TYPE_ARRAY = 0x200;

  /** value represents a type that will be used in a context where the actual type will be derived from other contextual information */
  public static final int TYPE_AUTO = -1;

  /** value is a boolean type */
  public static final int TYPE_BOOLEAN = 0x40;

  /** value represents a set of bytes */
  public static final int TYPE_BYTES = 0x80;

  /** value represents a date */
  public static final int TYPE_DATE = 0x10;

  /** value represents a date and time */
  public static final int TYPE_DATETIME = 0x08;

  /** value represents a decimal numeric value */
  public static final int TYPE_DECIMAL = 0x04;

  /** value represents an integer numeric value */
  public static final int TYPE_INTEGER = 0x02;

  /** value represents a string */
  public static final int TYPE_STRING = 0x01;

  /** value represents a structure */
  public static final int TYPE_STRUCT = 0x100;

  /** value represents a time */
  public static final int TYPE_TIME = 0x20;

  /** value represents a unknown type */
  public static final int TYPE_UNKNOWN = 0x00;

  /** value is a widget */
  public static final int                       TYPE_WIDGET         = 0x400;
  private static short                          ITEM_DISABLED       = 1;
  private static short                          ITEM_EDITABLE       = 4;
  private static short                          ITEM_EDITABLE_SET   = 8;
  private static short                          ITEM_HIDDEN         = 16;
  private static short                          ITEM_MODIFIED       = 32;
  private static short                          ITEM_NOT_SELECTABLE = 2;
  private final static HashMap<String, Integer> typeMap             = new HashMap<String, Integer>(10);

  static {
    typeMap.put("STRING", Integer.valueOf(TYPE_STRING));
    typeMap.put("INTEGER", Integer.valueOf(TYPE_INTEGER));
    typeMap.put("REAL", Integer.valueOf(TYPE_DECIMAL));
    typeMap.put("DECIMAL", Integer.valueOf(TYPE_DECIMAL));
    typeMap.put("DATE", Integer.valueOf(TYPE_DATE));
    typeMap.put("DATETIME", Integer.valueOf(TYPE_DATETIME));
    typeMap.put("TIME", Integer.valueOf(TYPE_TIME));
    typeMap.put("ARRAY", Integer.valueOf(TYPE_ARRAY));
    typeMap.put("STRUCT", Integer.valueOf(TYPE_STRUCT));
    typeMap.put("BOOLEAN", Integer.valueOf(TYPE_BOOLEAN));
    typeMap.put("BYTES", Integer.valueOf(TYPE_BYTES));
  }

  /** used by data models to store additional data */
  public Object modelData;

  /** the number of columns the item will span */
  protected int columnSpan = 1;

  /** the number of rows the item will span */
  protected int rowSpan = 1;

  /** action for the item */
  protected iActionListener actionListener;

  /** the item's alternate icon */
  protected iPlatformIcon alternateIcon;

  /** the item's background color */
  protected UIColor bgColor;

  /** a handle to the data converter */
  protected iDataConverter dataConverter;

  /** the item's disabled icon */
  protected iPlatformIcon disabledIcon;

  /** the item's display icon */
  protected iPlatformIcon displayIcon;

  /** supported export data flavors */
  protected String[] exportDataFlavors;

  /** the item's foreground color */
  protected UIColor fgColor;

  /** supported import flavors */
  protected String[] importDataFlavors;

  /** a handle to the linked data converter */
  protected iDataConverter ldDataConverter;

  /** programmer specific data to link to this item */
  protected Object linkedData;

  /** programmer specific context information about the linked data */
  protected Object linkedDataContext;

  /** the parent of this item */
  @Weak()
  protected RenderableDataItem parentItem;

  /** used by widgets that support row/column spanning to store spanning data */
  protected Object spanData;

  /** flags identifying the item's state */
  protected short stateFlags;

  /** this value sub-values */
  protected iFilterableList<RenderableDataItem> subItems;

  /** the item's font */
  protected UIFont theFont;

  /** this item's data type */
  protected int theType;

  /** the item's value */
  protected volatile Object theValue;

  /** tooltip for the item */
  protected CharSequence tooltip;

  /** whether an identity hash code should be used for the item */
  protected boolean useIdentityHashCode;

  /** flags identifying the user specified item's state */
  protected byte userStateFlags;

  /** programmer specific context information about the value */
  protected Object valueContext;

  /** when the special named cursor is to be displayed */
  private CursorDisplayType cursorDisplayType = CursorDisplayType.HYPERLINKS_AND_ICON;
  private float             relativeFontSize  = UIFontHelper.getRelativeFontSize();

  /** the item's vertical alignment */
  protected VerticalAlign verticalAlign = VerticalAlign.AUTO;

  /** whether the type specific default converter is being used */
  protected boolean usingDefaultConverter = true;

  /** the item's Swing equivalent vertical alignment */
  protected int swingVertAlign = UIConstants.CENTER;

  /** the item's Swing equivalent horizontal alignment */
  protected int swingHorizAlign = UIConstants.LEADING;

  /** whether to the "this" as the parent of an added item */
  protected boolean setParentOnAdd = true;

  /** the item's icon position equivalent horizontal alignment */
  protected IconPosition iconPosition = IconPosition.AUTO;

  /** the item's horizontal alignment */
  protected HorizontalAlign horizontalAlign = HorizontalAlign.AUTO;

  /** whether dragging is allowed */
  protected boolean                 draggingAllowed = true;
  private CharSequence              _toString;
  private iPlatformComponentPainter componentPainter;
  private boolean                   converted;
  private String                    cursorName;

  /** custom attributes for the item */
  private Map                customProperties;
  private int                height;
  private boolean            linkedConverted;
  private ObjectHolder       oneProperty;
  private Orientation        orientation;
  private iPlatformComponent renderingComponent;
  private int                width;

  /**
   * CUrsor types
   */
  public enum CursorDisplayType {

    /** cursor displayed only when over the icon */
    ICON,

    /** cursor displayed only when over any text */
    TEXT,

    /** cursor displayed only when over an text or the icon */
    TEXT_AND_ICON,

    /** cursor displayed only when over hyper links */
    HYPERLINKS,

    /** cursor displayed only when over hyper links or the icon */
    HYPERLINKS_AND_ICON,

    /** cursor displayed when over the item's component */
    COMPONENT,

    /** cursor displayed when over  the item's component */
    ITEM
  }

  /**
   * Horizontal alignment choices
   *
   */
  public enum HorizontalAlign {

    /**
     * let the rendering component decide
     */
    AUTO,

    /**
     * the item should be centered horizontally
     */
    CENTER,

    /**
     * the item should be aligned to the left
     */
    LEFT,

    /**
     * the item should be aligned to the right
     */
    RIGHT,

    /** the item is positioned along the leading edge */
    LEADING,

    /** the item is positioned along the trailing edge */
    TRAILING,

    /** fill the available space */
    FILL
  }

  /**
   * iPlatformIcon position choices
   *
   */
  public enum IconPosition {

    /**
     * let the rendering component decide
     */
    AUTO,

    /**
     * the icon will be to the left of the item's value
     */
    LEFT,

    /**
     * the icon will be to the right of the item's value
     */
    RIGHT,

    /**
     * the icon will be centered above the item's value
     */
    TOP_CENTER,

    /**
     * the icon will be centered below the item's value
     */
    BOTTOM_CENTER,

    /**
     * the icon is positioned along the leading edge
     */
    LEADING,

    /**
     * the icon is positioned along the trailing edge
     */
    TRAILING,

    /**
     * the icon will be to the top left of the item's value
     */
    TOP_LEFT,

    /**
     * the icon will be to the top right of the item's value
     */
    TOP_RIGHT,

    /**
     * the icon will be to the bottomleft of the item's value
     */
    BOTTOM_LEFT,

    /**
     * the icon will be to the bottom right of the item's value
     */
    BOTTOM_RIGHT,

    /**
     * the icon will be to the far rightt of the item's value
     */
    RIGHT_JUSTIFIED
  }

  /**
   * Vertical alignment choices
   *
   */
  public enum Orientation {

    /**
     * let the rendering component decide
     */
    AUTO,

    /**
     * the item should oriented vertically
     */
    VERTICAL_DOWN,

    /**
     * the item should be oriented horizontally
     */
    HORIZONTAL,

    /**
     * the item should oriented vertically
     */
    VERTICAL_UP,

    /**
     * the item should be oriented horizontally flipped
     */
    HORIZONTAL_FLIPPED,
  }

  /**
   * Vertical alignment choices
   *
   */
  public enum VerticalAlign {

    /**
     * let the rendering component decide
     */
    AUTO,

    /**
     * the item should be centered vertically
     */
    CENTER,

    /**
     * the item should be aligned to the top
     */
    TOP,

    /**
     * the item should be aligned to the bottom
     */
    BOTTOM,

    /** fill the available space */
    FILL
  }

  /**
   * Constructs a new instance
   */
  public RenderableDataItem() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   */
  public RenderableDataItem(Object value) {
    this(value, (value instanceof String)
                ? TYPE_STRING
                : TYPE_UNKNOWN, null, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param type the item's data type
   */
  public RenderableDataItem(Object value, int type) {
    this(value, type, null, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param data the item's linked data
   */
  public RenderableDataItem(String value, Object data) {
    this(value, TYPE_STRING, data, null, null);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   */
  public RenderableDataItem(Object value, int type, Object data) {
    this(value, type, data, null, null);
  }

  /**
   * Constructs a new string type instance
   *
   * @param value the item's value
   * @param data the item's linked data
   * @param icon the item's display icon
   */
  public RenderableDataItem(String value, Object data, iPlatformIcon icon) {
    this(value, TYPE_STRING, data, icon, null);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   */
  public RenderableDataItem(Object value, int type, Object data, iPlatformIcon icon) {
    this(value, type, data, icon, null);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   * @param context the item's value context
   */
  public RenderableDataItem(Object value, int type, Object data, iPlatformIcon icon, Object context) {
    setValues(value, type, data, icon, context);
  }

  /**
   * Constructs a new instance
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   * @param fg the item's foreground color
   */
  public RenderableDataItem(Object value, int type, Object data, iPlatformIcon icon, UIColor fg) {
    setValues(value, type, data, icon, null);
    fgColor = fg;
  }

  /**
   * Appends the specified element to the end of this list (optional
   * operation).
   *
   * <p>Lists that support this operation may place limitations on what
   * elements may be added to this list.  In particular, some
   * lists will refuse to add null elements, and others will impose
   * restrictions on the type of elements that may be added.  List
   * classes should clearly specify in their documentation any restrictions
   * on what elements may be added.
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt>  if the item was added; <tt>false</tt> otherwise
   * @throws UnsupportedOperationException if the <tt>add</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of the specified element
   *         prevents it from being added to this list
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   * @throws IllegalArgumentException if some property of this element
   *         prevents it from being added to this list
   */
  @Override
  public boolean add(RenderableDataItem e) {
    if (e != null) {
      if (setParentOnAdd) {
        e.setParentItem(this);
      }

      e.useIdentityHashCode = true;
    }

    return this.subItemsList(1).add(e);
  }

  /**
   * Adds the specified string as a new sub-item
   *
   * @param s the string to add
   *
   * @return true if the object was added; false otherwise
   */
  public boolean add(String s) {
    return add(new RenderableDataItem(s));
  }

  /**
   * Inserts the specified element at the specified position in this list
   * (optional operation).  Shifts the element currently at that position
   * (if any) and any subsequent elements to the right (adds one to their
   * indices).
   *
   * @param index index at which the specified element is to be inserted
   * @param element element to be inserted
   * @throws UnsupportedOperationException if the <tt>add</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of the specified element
   *         prevents it from being added to this list
   * @throws NullPointerException if the specified element is null and
   *         this list does not permit null elements
   * @throws IllegalArgumentException if some property of the specified
   *         element prevents it from being added to this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt; size()</tt>)
   */
  @Override
  public void add(int index, RenderableDataItem element) {
    element.setParentItem(this);
    subItemsList(1).add(index, element);
  }

  /**
   * Adds the specified array of items as sub-items
   *
   * @param items the items to add
   * @param pos the starting position in the array
   * @param len the number of items to add
   *
   * @return true if the items were added; false otherwise
   */
  public boolean add(RenderableDataItem[] items, int pos, int len) {
    len += pos;

    boolean added = pos < len;

    while(pos < len) {
      add(items[pos++]);
    }

    return added;
  }

  /**
   * Appends all of the elements in the specified collection to the end of
   * this list, in the order that they are returned by the specified
   * collection's iterator (optional operation).  The behavior of this
   * operation is undefined if the specified collection is modified while
   * the operation is in progress.  (Note that this will occur if the
   * specified collection is this list, and it's nonempty.)
   *
   * @param c collection containing elements to be added to this list
   * @return <tt>true</tt> if this list changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of an element of the specified
   *         collection prevents it from being added to this list
   * @throws NullPointerException if the specified collection contains one
   *         or more null elements and this list does not permit null
   *         elements, or if the specified collection is null
   * @throws IllegalArgumentException if some property of an element of the
   *         specified collection prevents it from being added to this list
   */
  @Override
  public boolean addAll(Collection<? extends RenderableDataItem> c) {
    final int count = (c == null)
                      ? 0
                      : c.size();

    if (count > 0) {
      this.ensureCapacity(count + size());

      RenderableDataItem                     item;
      Iterator<? extends RenderableDataItem> it = c.iterator();

      while(it.hasNext()) {
        item = it.next();
        add(item);
      }
    }

    return count > 0;
  }

  /**
   * Adds all of the elements in the specified array to this collection
   *
   * @param items the array of items to add
   */
  public void addAll(RenderableDataItem[] items) {
    if (items != null) {
      add(items, 0, items.length);
    }
  }

  /**
   * Inserts all of the elements in the specified collection into this
   * list at the specified position (optional operation).  Shifts the
   * element currently at that position (if any) and any subsequent
   * elements to the right (increases their indices).  The new elements
   * will appear in this list in the order that they are returned by the
   * specified collection's iterator.  The behavior of this operation is
   * undefined if the specified collection is modified while the
   * operation is in progress.  (Note that this will occur if the specified
   * collection is this list, and it's nonempty.)
   *
   * @param index index at which to insert the first element from the
   *              specified collection
   * @param c collection containing elements to be added to this list
   * @return <tt>true</tt> if this list changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of an element of the specified
   *         collection prevents it from being added to this list
   * @throws NullPointerException if the specified collection contains one
   *         or more null elements and this list does not permit null
   *         elements, or if the specified collection is null
   * @throws IllegalArgumentException if some property of an element of the
   *         specified collection prevents it from being added to this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt; size()</tt>)
   */
  @Override
  public boolean addAll(int index, Collection<? extends RenderableDataItem> c) {
    final int count = (c == null)
                      ? 0
                      : c.size();

    if (count > 0) {
      this.ensureCapacity(count + size());

      RenderableDataItem                     item;
      Iterator<? extends RenderableDataItem> it = c.iterator();

      while(it.hasNext()) {
        item = it.next();
        add(index++, item);
      }
    }

    return count > 0;
  }

  /**
   * Adds all of the elements in the specified array to this collection
   *
   * @param index the index at which to add
   * @param items the array of items to add
   */
  public void addAll(int index, RenderableDataItem[] items) {
    final int count = (items == null)
                      ? 0
                      : items.length;

    if (count > 0) {
      this.ensureCapacity(count + size());

      for (int i = 0; i < count; i++) {
        add(index++, items[i]);
      }
    }
  }

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
  @Override
  public void addIndexToFilteredList(int index) {
    if (subItems != null) {
      subItems.addIndexToFilteredList(index);
    }
  }

  /**
   * Adds the specified item as a sub-item of this item
   *
   * @param item the item to add
   */
  public void addItem(RenderableDataItem item) {
    if (setParentOnAdd) {
      item.setParentItem(this);
    }

    item.useIdentityHashCode = true;
    subItemsList(1).add(item);
  }

  /**
   * Adds missing columns to a item that represents a row.
   * Items will be added until the specified count is reached
   *
   * @param count the number of columns that the row should have
   */
  public void addMissingColumns(int count) {
    int                len = count - size();
    RenderableDataItem item;

    while(len > 0) {
      add(item = new RenderableDataItem(null, RenderableDataItem.TYPE_STRING, null));
      item.setSelectable(false);
      len--;
    }
  }

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param e the item to add
   */
  @Override
  public void addToFilteredList(RenderableDataItem e) {
    if (setParentOnAdd) {
      e.setParentItem(this);
    }

    e.useIdentityHashCode = true;
    this.subItemsList(1).addToFilteredList(e);
  }

  /**
   * Adds an item to the list. If a filter is in place it is ignored
   * and the item is added to the filtered list
   *
   * @param index the index
   * @param e the item to add
   */
  @Override
  public void addToFilteredList(int index, RenderableDataItem e) {
    if (setParentOnAdd) {
      e.setParentItem(this);
    }

    e.useIdentityHashCode = true;
    this.subItemsList(1).addToFilteredList(index, e);
  }

  /**
   * Chops the number of sub-items for the end of the list
   *
   * @param len the number of sub-items to chop
   * @return the number of items remaining in the list
   */
  public int chop(int len) {
    return aFilterableList.chop(this, len);
  }

  /**
   * Removes all data and all of the sub-elements from this item
   *
   * @throws UnsupportedOperationException if the <tt>clear</tt> operation
   *         is not supported by this list
   */
  @Override
  public void clear() {
    theValue     = null;
    linkedData   = null;
    _toString    = null;
    stateFlags   = 0;
    valueContext = null;

    if (subItems != null) {
      subItems.clear();
    }
  }

  /**
   * Removes the value and linked data for all sub items
   */
  public void clearSubItemData() {
    if (subItems != null) {
      iFilterableList<RenderableDataItem> list = subItems;
      int                                 len  = list.size();

      for (int i = 0; i < len; i++) {
        RenderableDataItem e = list.get(i);

        if (e != null) {
          e.setValue(null);
          e.setLinkedData(null);
        }
      }
    }
  }

  /**
   * Removes the value for all sub items
   */
  public void clearSubItemParents() {
    if (subItems != null) {
      iFilterableList<RenderableDataItem> list = subItems;
      int                                 len  = list.size();

      for (int i = 0; i < len; i++) {
        RenderableDataItem e = list.get(i);

        if (e != null) {
          e.setParentItem(null);
          e.clearSubItemParents();
        }
      }
    }
  }

  /**
   * Removes the value for all sub items
   */
  public void clearSubItemValues() {
    if (subItems != null) {
      iFilterableList<RenderableDataItem> list = subItems;
      int                                 len  = list.size();

      for (int i = 0; i < len; i++) {
        RenderableDataItem e = list.get(i);

        if (e != null) {
          e.setValue(null);
        }
      }
    }
  }

  /**
   * Removes all sub-items that this item contains
   */
  public void clearSubItems() {
    if (subItems != null) {
      subItems.clear();
    }
  }

  /**
   * Calls the clear method on all sub items
   */
  public void clearSubItemsEx() {
    if (subItems != null) {
      iFilterableList<RenderableDataItem> list = subItems;
      int                                 len  = list.size();

      for (int i = 0; i < len; i++) {
        RenderableDataItem e = list.get(i);

        if (e != null) {
          e.clear();
        }
      }
    }
  }

  /**
   * Creates and returns a copy of this item
   *
   * @return a copy of this item
   */
  @Override
  public Object clone() {
    try {
      RenderableDataItem item = (RenderableDataItem) super.clone();

      if (customProperties != null) {
        item.customProperties = new HashMap<String, Object>(customProperties);
        item.oneProperty      = null;
      } else if (oneProperty != null) {
        item.oneProperty = new ObjectHolder(oneProperty);
      }

      if (exportDataFlavors != null) {
        item.exportDataFlavors = new String[exportDataFlavors.length];
        System.arraycopy(exportDataFlavors, 0, item.exportDataFlavors, 0, exportDataFlavors.length);
      }

      if (importDataFlavors != null) {
        item.importDataFlavors = new String[importDataFlavors.length];
        System.arraycopy(importDataFlavors, 0, item.importDataFlavors, 0, importDataFlavors.length);
      }

      item.spanData   = null;
      item.modelData  = null;
      item.parentItem = null;
      item.subItems   = null;

      if (this.subItems != null) {
        item.addAll(this.subItems);
      }

      return item;
    } catch(CloneNotSupportedException ex) {
      RenderableDataItem item = new RenderableDataItem();

      item.copy(this);

      return item;
    }
  }

  /**
   *  Compares this object with the specified object for order.  Returns a
   *  negative integer, zero, or a positive integer as this object is less
   *  than, equal to, or greater than the specified object.
   *
   *  <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
   *  -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
   *  implies that <tt>x.compareTo(y)</tt> must throw an exception iff
   *  <tt>y.compareTo(x)</tt> throws an exception.)
   *
   *  <p>The implementor must also ensure that the relation is transitive:
   *  <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
   *  <tt>x.compareTo(z)&gt;0</tt>.
   *
   *  <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
   *  implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
   *  all <tt>z</tt>.
   *
   *  <p>It is strongly recommended, but <i>not</i> strictly required that
   *  <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
   *  class that implements the <tt>Comparable</tt> interface and violates
   *  this condition should clearly indicate this fact.  The recommended
   *  language is "Note: this class has a natural ordering that is
   *  inconsistent with equals."
   *
   *  <p>In the foregoing description, the notation
   *  <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   *  <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   *  <tt>0</tt>, or <tt>1</tt> according to whether the value of
   *  <i>expression</i> is negative, zero or positive.
   *
   *  @param   o the object to be compared.
   *  @return  a negative integer, zero, or a positive integer as this object
   *           is less than, equal to, or greater than the specified object.
   *
   *  @throws ClassCastException if the specified object's type prevents it
   *          from being compared to this object.
   */
  @Override
  public int compareTo(RenderableDataItem o) {
    return SubItemComparator.compare(this, o, null, false);
  }

  /**
   *  Joins two or more lists and returns a new lists
   * @param e the lists to concatenate
   * @return the new list
   */
  @Override
  public List<RenderableDataItem> concat(List<RenderableDataItem>... e) {
    return this.subItemsList(1).concat(e);
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element.
   * More formally, returns <tt>true</tt> if and only if this list contains
   * at least one element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * @param o element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements (optional)
   */
  @Override
  public boolean contains(Object o) {
    return (subItems == null)
           ? false
           : subItems.contains(o);
  }

  /**
   * Returns <tt>true</tt> if this list contains all of the elements of the
   * specified collection.
   *
   * @param  c collection to be checked for containment in this list
   * @return <tt>true</tt> if this list contains all of the elements of the
   *         specified collection
   * @throws ClassCastException if the types of one or more elements
   *         in the specified collection are incompatible with this
   *         list (optional)
   * @throws NullPointerException if the specified collection contains one
   *         or more null elements and this list does not permit null
   *         elements (optional), or if the specified collection is null
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    return (subItems == null)
           ? false
           : subItems.containsAll(c);
  }

  /**
   * Converts this item's value using the specified converter
   * and value context.
   *
   * @param widget the widget context
   * @param type the value type
   * @param cvt the data converter
   * @param context the value context
   *
   * @return the converted value
   */
  public Object convert(iWidget widget, int type, iDataConverter cvt, Object context) {
    if (theType == TYPE_AUTO) {
      theType = type;
    }

    if (!converted) {
      if (dataConverter != null) {
        cvt     = dataConverter;
        context = valueContext;
      } else {
        dataConverter = cvt;
        valueContext  = context;
      }

      if ((cvt != null) && (theValue instanceof String)) {
        theValue = cvt.objectFromString(widget, (String) theValue, context);

        if (type == TYPE_DECIMAL) {
          if (theValue instanceof Long) {
            theValue = ((Long) theValue).doubleValue();
          }
        } else if (type == TYPE_INTEGER) {
          if (theValue instanceof Double) {
            theValue = ((Double) theValue).longValue();
          }
        }
      }

      if (theValue instanceof iWidget) {
        renderingComponent = ((iWidget) theValue).getContainerComponent();
      }

      converted = true;
    }

    return theValue;
  }

  /**
   * Creates a new item that is a copy of this item
   *
   * @return a copy of this item
   */
  public RenderableDataItem copy() {
    RenderableDataItem di = new RenderableDataItem();

    di.copy(this);

    return di;
  }

  /**
   * Copies the contents of the specified item to this item.
   * Model data is not copied
   *
   * @param item the item to copy
   */
  public void copy(RenderableDataItem item) {
    copyEx(item);

    List<RenderableDataItem> items = item.getItems();
    int                      count = (items == null)
                                     ? 0
                                     : items.size();

    if (count == 0) {
      this.clearSubItems();
    } else {
      this.emptySubItemsList(count).addAll(items);
    }
  }

  /**
   * Copies the contents of the specified item to this item.
   * Sub items and model.spanning data are not copied
   *
   * @param item the item to copy
   */
  public void copyEx(RenderableDataItem item) {
    _toString           = item._toString;
    actionListener      = item.actionListener;
    alternateIcon       = item.alternateIcon;
    bgColor             = item.bgColor;
    columnSpan          = item.columnSpan;
    componentPainter    = item.componentPainter;
    converted           = item.converted;
    dataConverter       = item.dataConverter;
    disabledIcon        = item.disabledIcon;
    displayIcon         = item.displayIcon;
    draggingAllowed     = item.draggingAllowed;
    exportDataFlavors   = item.exportDataFlavors;
    fgColor             = item.fgColor;
    height              = item.height;
    horizontalAlign     = item.horizontalAlign;
    iconPosition        = item.iconPosition;
    iconScaleFactor     = item.iconScaleFactor;
    scaleIcon           = item.scaleIcon;
    importDataFlavors   = item.importDataFlavors;
    ldDataConverter     = item.ldDataConverter;
    linkedConverted     = item.linkedConverted;
    linkedData          = item.linkedData;
    linkedDataContext   = item.linkedDataContext;
    rowSpan             = item.rowSpan;
    stateFlags          = item.stateFlags;
    swingHorizAlign     = item.swingHorizAlign;
    swingVertAlign      = item.swingVertAlign;
    theFont             = item.theFont;
    theType             = item.theType;
    theValue            = item.theValue;
    tooltip             = item.tooltip;
    useIdentityHashCode = item.useIdentityHashCode;
    valueContext        = item.valueContext;
    verticalAlign       = item.verticalAlign;
    width               = item.width;
    relativeFontSize    = item.relativeFontSize;
    oneProperty         = null;
    customProperties    = null;

    if (item.customProperties != null) {
      customProperties = new HashMap<String, Object>(item.customProperties);
    } else if (item.oneProperty != null) {
      oneProperty = new ObjectHolder(item.oneProperty);
    }

    if (exportDataFlavors != null) {
      exportDataFlavors = new String[exportDataFlavors.length];
      System.arraycopy(item.exportDataFlavors, 0, exportDataFlavors, 0, exportDataFlavors.length);
    }

    if (importDataFlavors != null) {
      importDataFlavors = new String[importDataFlavors.length];
      System.arraycopy(item.importDataFlavors, 0, importDataFlavors, 0, importDataFlavors.length);
    }
    // not copied
    // modelData=item.modelData;
    // parentItem=item.parentItem;
    // spanData=item.spanData;
  }

  /**
   * Copies the value of the specified item to this item.
   * Sub items, attributes and model.spanning data are not copied
   *
   * @param item the item to copy
   */
  public void copyValue(RenderableDataItem item) {
    _toString     = item._toString;
    converted     = item.converted;
    dataConverter = item.dataConverter;
    theType       = item.theType;
    theValue      = item.theValue;
    valueContext  = item.valueContext;
  }

  /**
   * Copies the contents of the specified item to this item
   * Copies are also made of all subitems
   *
   * @return the new array
   */
  public RenderableDataItem deepCopy() {
    RenderableDataItem di = new RenderableDataItem();

    di.deepCopy(this);

    return di;
  }

  /**
   * Copies the contents of the specified item to this item
   * Copies are also made of all subitems
   *
   * @param item the item to copy
   */
  public void deepCopy(RenderableDataItem item) {
    copyEx(item);

    List<RenderableDataItem> items = item.getItems();
    int                      count = (items == null)
                                     ? 0
                                     : items.size();

    if (count == 0) {
      this.clearSubItems();
    } else {
      List<RenderableDataItem> myitems = emptySubItemsList(count);

      for (int i = 0; i < count; i++) {
        myitems.add(item.get(i).deepCopy());
      }
    }
  }

  /**
   * Copies the contents of the specified array to a new array
   * making deep copies of all items
   *
   * @param b the array to copy
   * @return the new array
   */
  public static RenderableDataItem[] deepCopy(RenderableDataItem[] b) {
    int len = (b == null)
              ? 0
              : b.length;

    return deepCopy(b, 0, len);
  }

  /**
   * Copies the contents of the specified array to a new array
   * making deep copies of all items
   *
   * @param b the array to copy
   * @param pos the starting position to copy at
   * @param len the number of items to copy
   * @return the new array
   */
  public static RenderableDataItem[] deepCopy(RenderableDataItem[] b, int pos, int len) {
    RenderableDataItem[] a = new RenderableDataItem[len];

    for (int i = 0; i < len; i++) {
      a[i] = b[pos++].deepCopy();
    }

    return a;
  }

  public void dispose() {
    if (customProperties != null) {
      customProperties.clear();
    }

    if (subItems != null) {
      subItems.clear();
    }

    theValue           = null;
    linkedData         = null;
    _toString          = null;
    valueContext       = null;
    oneProperty        = null;
    dataConverter      = null;
    customProperties   = null;
    subItems           = null;
    modelData          = null;
    ldDataConverter    = null;
    linkedData         = null;
    linkedDataContext  = null;
    renderingComponent = null;
    parentItem         = null;
    componentPainter   = null;
    displayIcon        = null;
    disabledIcon       = null;
    alternateIcon      = null;
  }

  /**
   * Returns a <code>double</code> representation of this item's value.
   *
   * @return   the item's value as a <code>double</code>
   */
  public double doubleValue() {
    Object o = getValue();

    if (o instanceof Number) {
      return ((Number) o).doubleValue();
    }

    if (o instanceof Date) {
      return ((Date) o).getTime();
    }

    return (o == null)
           ? 0
           : SNumber.doubleValue(o.toString());
  }

  /**
   * Increases the capacity of this list instance, if necessary, to
   * ensure that it can hold at least the number of items specified by the
   * capacity argument.
   *
   * @param capacity  the desired minimum capacity.
   */
  @Override
  public void ensureCapacity(int capacity) {
    subItemsList(capacity);
  }

  /**
   * Tests the specified object with this item for equality
   *
   * @param o the object to test this item against
   *
   * @return <code>true</code> if they are equal; <code>false</code> otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    RenderableDataItem p = parentItem;
    RenderableDataItem e = p;

    while(e != null) {
      e = e.getParentItem();

      if (e != null) {
        p = e;
      }
    }

    if ((p != null) && (p.getModelData() == TREE_MODEL_DATA)) {
      return o == this;
    }

    if (!(o instanceof RenderableDataItem)) {
      return false;
    }

    return equals((RenderableDataItem) o);
  }

  /**
   * Tests the specified item with this item for equality
   *
   * @param o the item to test this item against
   *
   * @return <code>true</code> if they are equal; <code>false</code> otherwise
   */
  public boolean equals(RenderableDataItem o) {
    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
    }

    Object a = o.getValue();
    Object b = getValue();

    if (a == null) {
      if (b != null) {
        return false;
      }
    } else {
      if (b == null) {
        return false;
      }

      if (!a.equals(b)) {
        return false;
      }
    }

    a = o.getLinkedData();
    b = getLinkedData();

    if (a == null) {
      if (b != null) {
        return false;
      }
    } else {
      if (b == null) {
        return false;
      }

      if (!a.equals(b)) {
        return false;
      }
    }

    return (o.getItems() == getItems()) && (o.getItemCount() == getItemCount());
  }

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @return true if items were filtered out; false otherwise
   */
  @Override
  public boolean filter(iFilter filter) {
    return (subItems == null)
           ? false
           : subItems.filter(filter);
  }

  @Override
  public void setFilteredList(List<RenderableDataItem> list, iFilter lastFilter) {
    if (subItems != null) {
      subItems.setFilteredList(list, lastFilter);
    }
  }

  /**
   * Finds the index of the first item matching the specified filter.
   * The search is performed from the beginning of the list (an equality test is used)
   *
   * @return the index or -1 if no item was found
   * @param filter the filter
   */
  public boolean filter(String filter) {
    return filter(filter, false, true, true);
  }

  /**
   * Applies the specified filter to the list
   *
   * @param filter the filter
   * @param contains whether a 'contains' test should be performed. If false an equality test is used
   *
   * @return true if items were filtered out; false otherwise
   */
  @Override
  public boolean filter(String filter, boolean contains) {
    return filter(filter, contains, true, true);
  }

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
  @Override
  public boolean filter(String filter, boolean contains, boolean nullPasses, boolean emptyPasses) {
    return (subItems == null)
           ? false
           : subItems.filter(filter, contains, nullPasses, emptyPasses);
  }

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
  public int find(iFilter filter, int start) {
    return (subItems == null)
           ? -1
           : subItems.find(filter, start);
  }

  /**
   * Finds the index of the first item matching the specified filter.
   * The search is performed beginning at the specified start index (an equality test is used)
   *
   * @param filter the filter
   * @param start the starting point of the search
   * @return the index or -1 if no item was found
   */
  public int find(String filter, int start) {
    return find(filter, start, false);
  }

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
  public int find(String filter, int start, boolean contains) {
    return (subItems == null)
           ? -1
           : subItems.find(filter, start, contains);
  }

  /**
   * Finds the sub-item with the specified linked data
   *
   * @param val the value to search for
   *
   * @return the sub-item with the specified linked data or null if the value was not found
   */
  public RenderableDataItem findLinkedData(Object val) {
    RenderableDataItem di;
    int                len = this.getItemCount();
    int                i   = 0;
    Object             o;

    while(i < len) {
      di = getItem(i++);

      if (di == null) {
        continue;
      }

      o = di.getLinkedData();

      if ((o == val) || ((val != null) && val.equals(o))) {
        return di;
      }
    }

    return null;
  }

  /**
   * Finds the items in the specified list that has linked data
   * values that are equal to the specified values
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the index of the found item or -1
   */
  public static int[] findLinkedDataObjectsEx(List<RenderableDataItem> list, Object[] value) {
    if (value == null) {
      return null;
    }

    final int len  = list.size();
    final int vlen = value.length;
    IntList   ints = new IntList(len);

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = list.get(i);

      for (int j = 0; j < vlen; j++) {
        if ((di != null) && di.linkedDataEquals(value[j])) {
          ints.add(i);

          break;
        }
      }
    }

    return ints.toArray();
  }

  /**
   * Finds the item in the specified list that has a
   * linked data object that is equal to the specified value
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the found item or null
   */
  public static RenderableDataItem findLinkedObject(List<RenderableDataItem> list, Object value) {
    int                len = list.size();
    RenderableDataItem di;
    int                i = 0;

    while(i < len) {
      di = list.get(i);

      if ((di != null) && di.linkedDataEquals(value)) {
        return di;
      }

      i++;
    }

    return null;
  }

  /**
   * Finds the item in the specified list that has a
   * linked data object that is equal to the specified value
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the index of the found item or -1
   */
  public static int findLinkedObjectIndex(List<RenderableDataItem> list, Object value) {
    int                len = list.size();
    RenderableDataItem di;
    int                i = 0;

    while(i < len) {
      di = list.get(i);

      if ((di != null) && di.linkedDataEquals(value)) {
        return i;
      }

      i++;
    }

    return -1;
  }

  /**
   * Finds the sub-item with the specified value
   *
   * @param val the value to search for
   *
   * @return the sub-item with the specified value or null if the value was not found
   */
  public RenderableDataItem findValue(Object val) {
    RenderableDataItem di;
    int                len = this.getItemCount();
    int                i   = 0;
    Object             o;

    while(i < len) {
      di = getItem(i++);

      if (di == null) {
        continue;
      }

      o = di.getValue();

      if ((o == val) || ((val != null) && val.equals(o))) {
        return di;
      }
    }

    return null;
  }

  /**
   * Finds the item in the specified list that has a
   * value that is equal to the specified value
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the found item or null
   */
  public static RenderableDataItem findValue(List<RenderableDataItem> list, Object value) {
    int                len = list.size();
    RenderableDataItem di;
    int                i = 0;

    while(i < len) {
      di = list.get(i);

      if ((di != null) && di.valueEquals(value)) {
        return di;
      }

      i++;
    }

    return null;
  }

  /**
   * Finds the item in the specified list that has a
   * value that is equal to the specified value
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the index of the found item or -1
   */
  public static int findValueEx(List<RenderableDataItem> list, Object value) {
    int                len = list.size();
    RenderableDataItem di;
    int                i = 0;

    while(i < len) {
      di = list.get(i);

      if (di == value) {
        return i;
      }

      if ((di != null) && di.valueEquals(value)) {
        return i;
      }

      i++;
    }

    return -1;
  }

  /**
   * Finds the items in the specified list that has
   * values that are equal to the specified values
   *
   * @param list the list to search
   * @param value the value to search for
   *
   * @return the index of the found item or -1
   */
  public static int[] findValuesEx(List<RenderableDataItem> list, Object[] value) {
    if (value == null) {
      return null;
    }

    final int len  = list.size();
    final int vlen = value.length;
    IntList   ints = new IntList(len);

    for (int i = 0; i < len; i++) {
      RenderableDataItem di = list.get(i);

      for (int j = 0; j < vlen; j++) {
        if ((di != null) && di.valueEquals(value[j])) {
          ints.add(i);

          break;
        }
      }
    }

    return ints.toArray();
  }

  /**
   * Returns a <code>float</code> representation of this item's value.
   *
   * @return   the item's value as a <code>float</code>
   */
  public float floatValue() {
    Object o = getValue();

    if (o instanceof Number) {
      return ((Number) o).floatValue();
    }

    if (o instanceof Date) {
      return ((Date) o).getTime();
    }

    return (o == null)
           ? 0
           : SNumber.floatValue(o.toString());
  }

  /**
   * Returns the <code>RenderableDataItem</code>type for the specified
   * SPOT value type.
   *
   * @param type the SPOT value type
   *
   * @return the <code>RenderableDataItem</code>type
   */
  public static int fromSPOTType(int type) {
    switch(type) {
      case DataItem.CValueType.auto_type :
        type = RenderableDataItem.TYPE_AUTO;

        break;

      case DataItem.CValueType.string_type :
        type = RenderableDataItem.TYPE_STRING;

        break;

      case DataItem.CValueType.date_type :
        type = RenderableDataItem.TYPE_DATE;

        break;

      case DataItem.CValueType.date_time_type :
        type = RenderableDataItem.TYPE_DATETIME;

        break;

      case DataItem.CValueType.time_type :
        type = RenderableDataItem.TYPE_TIME;

        break;

      case DataItem.CValueType.array_type :
        type = RenderableDataItem.TYPE_ARRAY;

        break;

      case DataItem.CValueType.struct_type :
        type = RenderableDataItem.TYPE_STRUCT;

        break;

      case DataItem.CValueType.integer_type :
      case DataItem.CValueType.decimal_type :
        type = RenderableDataItem.TYPE_DECIMAL;

        break;

      case DataItem.CValueType.boolean_type :
        type = RenderableDataItem.TYPE_BOOLEAN;

        break;

      case DataItem.CValueType.widget_type :
        type = RenderableDataItem.TYPE_WIDGET;

        break;

      default :
        type = RenderableDataItem.TYPE_UNKNOWN;

        break;
    }

    return type;
  }

  @Override
  public int hashCode() {
    if (useIdentityHashCode) {
      return System.identityHashCode(this);
    } else if ((theValue != null) && (subItems == null)) {
      return theValue.hashCode();
    } else {
      return super.hashCode();
    }
  }

  /**
   * Returns the index of the first occurrence of the specified element
   * in this list, or -1 if this list does not contain the element.
   *
   * @param o element to search for
   * @return the index of the first occurrence of the specified element in
   *         this list, or -1 if this list does not contain the element
   */
  public int identityIndexOf(Object o) {
    if (subItems == null) {
      return -1;
    }

    int len = size();

    for (int i = 0; i < len; i++) {
      if (get(i) == o) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the first occurrence of the specified element
   * in this list, or -1 if this list does not contain the element.
   * More formally, returns the lowest index <tt>i</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param o element to search for
   * @return the index of the first occurrence of the specified element in
   *         this list, or -1 if this list does not contain the element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements (optional)
   */
  @Override
  public int indexOf(Object o) {
    return (subItems == null)
           ? -1
           : subItems.indexOf(o);
  }

  /**
   * Gets the index of the item whose linked data has  the specified value
   * An identity compare (==) is used to test for equality
   *
   * @param value the value to look for
   *
   * @return the index of the specified value of -1 if the value was not found
   */
  public int indexOfLinkedData(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getLinkedData();

      if (o == value) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Gets the index of the item whose linked data has  the specified value
   * The equals() method is used to test for equality
   *
   * @param value the value to look for
   *
   * @return the index of the specified value of -1 if the value was not found
   */
  public int indexOfLinkedDataEquals(Object value) {
    int    len = size();
    Object o;

    for (int i = 0; i < len; i++) {
      o = get(i).getLinkedData();

      if ((o != null) && ((o == value) || o.equals(value))) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the sub-item with the specified value.
   *
   * @param value the value to search for
   *
   * @return the index of the sub-item with the specified value or -1
   */
  public int indexOfValue(Object value) {
    List<RenderableDataItem> items = subItems;
    int                      len   = (items == null)
                                     ? 0
                                     : subItems.size();

    for (int i = 0; i < len; i++) {
      if (items.get(i).getValue() == value) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index of the sub-item with a value
   * that is equal to the specified value
   *
   * @param value the value to search for
   *
   * @return the index of the sub-item or -1
   */
  public int indexOfValueEquals(Object value) {
    List<RenderableDataItem> items = subItems;
    int                      len   = (items == null)
                                     ? 0
                                     : subItems.size();

    for (int i = 0; i < len; i++) {
      if (items.get(i).valueEquals(value)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns a <code>int</code> representation of this item's value.
   *
   * @return   the item's value as a <code>int</code>
   */
  public int intValue() {
    Object o = getValue();

    if (o instanceof Number) {
      return ((Number) o).intValue();
    }

    if (o instanceof Date) {
      return (int) ((Date) o).getTime();
    }

    return (o == null)
           ? 0
           : SNumber.intValue(o.toString());
  }

  /**
   * Returns the string representation of the list of sub-items this
   * item contains
   *
   * @return the string representation of the sub-items or an empty string if there are no sub-items
   */
  public String itemsToString() {
    if (subItems != null) {
      return Helper.toString(subItems, "\t");
    }

    return "";
  }

  /**
   * Returns an iterator over the elements in this list in proper sequence.
   *
   * @return an iterator over the elements in this list in proper sequence
   */
  @Override
  public Iterator<RenderableDataItem> iterator() {
    return subItemsList(0).iterator();
  }

  /**
   * Puts all the elements of an list into a string separated by a comma
   *
   * @return the joined elements
   */
  public String join() {
    return join(",");
  }

  /**
   * Puts all the elements of an list into a string separated by a specified delimiter
   *
   * @param sep the delimiter
   * @return the joined elements
   */
  @Override
  public String join(String sep) {
    if (subItems == null) {
      return "";
    }

    return subItems.join(sep);
  }

  /**
   * Returns the index of the last occurrence of the specified element
   * in this list, or -1 if this list does not contain the element.
   * More formally, returns the highest index <tt>i</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param o element to search for
   * @return the index of the last occurrence of the specified element in
   *         this list, or -1 if this list does not contain the element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements (optional)
   */
  @Override
  public int lastIndexOf(Object o) {
    return (subItems == null)
           ? -1
           : subItems.lastIndexOf(o);
  }

  /**
   * Returns whether this item's linked data is equal to
   * the specified value
   *
   * @param value the value to test
   *
   * @return true if this item's linked data is equal to the specified value; false otherwise
   */
  public boolean linkedDataEquals(Object value) {
    if (linkedData == value) {
      return true;
    }

    return (linkedData == null)
           ? false
           : linkedData.equals(value);
  }

  /**
   * Returns an iterator over the elements in this list in proper sequence.
   *
   * @return an iterator over the elements in this list in proper sequence
   */
  @Override
  public ListIterator<RenderableDataItem> listIterator() {
    return subItemsList(0).listIterator();
  }

  /**
   * Returns a list iterator of the elements in this list (in proper
   * sequence), starting at the specified position in this list.
   * The specified index indicates the first element that would be
   * returned by an initial call to next.
   * An initial call to  previous would
   * return the element with the specified index minus one.
   *
   * @param index index of first element to be returned from the
   *              list iterator (by a call to the <tt>next</tt> method)
   * @return a list iterator of the elements in this list (in proper
   *         sequence), starting at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt; size()</tt>)
   */
  @Override
  public ListIterator<RenderableDataItem> listIterator(int index) {
    return subItemsList(0).listIterator(index);
  }

  /**
   * Returns a <code>long</code> representation of this item's value.
   *
   * @return   the item's value as a <code>long</code>
   */
  public long longValue() {
    Object o = getValue();

    if (o instanceof Number) {
      return ((Number) o).longValue();
    }

    if (o instanceof Date) {
      return ((Date) o).getTime();
    }

    return (o == null)
           ? 0
           : SNumber.longValue(o.toString());
  }

  @Override
  public void move(int from, int to) {
    if (subItems != null) {
      subItems.move(from, to);
    }
  }

  /**
   * Returns a <code>Number</code> representation of this item's value.
   *
   * @return   the item's value as a <code>float</code>
   */
  public Number numberValue() {
    Object o = getValue();

    if (o instanceof Number) {
      return (Number) o;
    }

    if (o instanceof Date) {
      return new SNumber(((Date) o).getTime());
    }

    return (o == null)
           ? Integer.valueOf(0)
           : new SNumber(o.toString());
  }

  /**
   * Removes a value from the end of the list
   *
   * @return the removed value or null if the list is empty
   */
  @Override
  public RenderableDataItem pop() {
    return ((subItems == null) || (subItems.size() == 0))
           ? null
           : subItems.remove(subItems.size() - 1);
  }

  /**
   * Adds a one or more elements value to the end of the list
   *
   * @param e the elements to add
   */
  @Override
  public void push(RenderableDataItem... e) {
    if ((e != null) && (e.length > 0)) {
      if ((e.length == 1) && (e[0] instanceof iFilterableList)) {
        addAll(e[0]);
      } else {
        addAll(e);
      }
    }
  }

  /**
   * Refilters a previously filtered list.
   * Call this method if you change the underlying unfiltered list
   *
   * @return true if items were filtered out; false otherwise
   */
  @Override
  public boolean refilter() {
    return (subItems == null)
           ? false
           : subItems.refilter();
  }

  /**
   * Removes the element at the specified position in this list (optional
   * operation).  Shifts any subsequent elements to the left (subtracts one
   * from their indices).  Returns the element that was removed from the
   * list.
   *
   * @param index the index of the element to be removed
   * @return the element previously at the specified position
   * @throws UnsupportedOperationException if the <tt>remove</tt> operation
   *         is not supported by this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  @Override
  public RenderableDataItem remove(int index) {
    RenderableDataItem e = null;

    if (subItems != null) {
      e = subItems.remove(index);

      if (e != null) {
        e.setParentItem(null);
      }
    }

    return e;
  }

  /**
   * Removes the first occurrence of the specified element from this list,
   * if it is present (optional operation).  If this list does not contain
   * the element, it is unchanged.  More formally, removes the element with
   * the lowest index <tt>i</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
   * (if such an element exists).  Returns <tt>true</tt> if this list
   * contained the specified element (or equivalently, if this list changed
   * as a result of the call).
   *
   * @param o element to be removed from this list, if present
   * @return <tt>true</tt> if this list contained the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this list (optional)
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements (optional)
   * @throws UnsupportedOperationException if the <tt>remove</tt> operation
   *         is not supported by this list
   */
  @Override
  public boolean remove(Object o) {
    if (subItems != null) {
      if (subItems.remove(o)) {
        ((RenderableDataItem) o).setParentItem(null);

        return true;
      }
    }

    return false;
  }

  /**
   * Removes from this list all of its elements that are contained in the
   * specified collection (optional operation).
   *
   * @param c collection containing elements to be removed from this list
   * @return <tt>true</tt> if this list changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of an element of this list
   *         is incompatible with the specified collection (optional)
   * @throws NullPointerException if this list contains a null element and the
   *         specified collection does not permit null elements (optional),
   *         or if the specified collection is null
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    if (subItems != null) {
      return subItems.removeAll(c);
    }

    return false;
  }

  /**
   *  Removes a custom property for the item
   *  @param key the key for the property
   *
   *  @return the value for the property being removed (or null)
   */
  public Object removeCustomProperty(Object key) {
    if (customProperties == null) {
      if ((oneProperty != null) && oneProperty.typeEquals(key)) {
        final Object value = oneProperty.value;

        oneProperty.clear();
        oneProperty = null;

        return value;
      }

      return null;
    }

    return customProperties.remove(key);
  }

  @Override
  public void removeRows(int[] indexes) {
    if (subItems != null) {
      int len = (indexes == null)
                ? 0
                : indexes.length;

      if (len > 0) {
        RenderableDataItem e = null;

        Arrays.sort(indexes);

        while(len > 0) {
          e = remove(indexes[--len]);

          if (e != null) {
            e.setParentItem(null);
          }
        }
      }
    }
  }

  protected float   iconScaleFactor;
  protected boolean scaleIcon;
  protected boolean scaleHeaderIcon;

  /**
   * Gets whether of not the item's icon will be scaled
   * when the item is rendered
   * @return true if it will be scaled; false otherwise
   */
  public boolean isScaleIcon() {
    return scaleIcon && (iconScaleFactor > 0);
  }

  /**
   * Sets whether this item's icon is scaled when rendered
   * @param scale true to scale; false to use unscaled
   * @param scaleFactor the scale factor (0-1.0) to use to scale the icon proportionally
   * based on the size of the items display area
   */
  public void setScaleIcon(boolean scale, float scaleFactor) {
    scaleIcon            = scale;
    this.iconScaleFactor = scaleFactor;
  }

  /**
   * Get's the scale factor to use when scaling icons
   * @return the scale factor uses when scaling icons
   */
  public float getIconScaleFactor() {
    return iconScaleFactor;
  }

  /**
   * Resets the item to a prestine state
   */
  public void reset() {
    renderingComponent  = null;
    customProperties    = null;
    _toString           = null;
    linkedConverted     = false;
    converted           = false;
    componentPainter    = null;
    height              = 0;
    width               = 0;
    actionListener      = null;
    bgColor             = null;
    fgColor             = null;
    theFont             = null;
    horizontalAlign     = HorizontalAlign.AUTO;
    alternateIcon       = null;
    disabledIcon        = null;
    displayIcon         = null;
    iconPosition        = IconPosition.AUTO;
    valueContext        = null;
    linkedData          = null;
    linkedDataContext   = null;
    spanData            = null;
    theValue            = null;
    parentItem          = null;
    tooltip             = null;
    exportDataFlavors   = null;
    importDataFlavors   = null;
    verticalAlign       = VerticalAlign.AUTO;
    draggingAllowed     = true;
    useIdentityHashCode = false;
    stateFlags          = 0;
    dataConverter       = null;
    ldDataConverter     = null;
    subItems            = null;
    columnSpan          = 1;
    rowSpan             = 1;
    swingHorizAlign     = UIConstants.LEADING;
    swingVertAlign      = UIConstants.CENTER;
    theType             = TYPE_STRING;
    modelData           = null;
    oneProperty         = null;
  }

  /**
   * Retains only the elements in this list that are contained in the
   * specified collection (optional operation).  In other words, removes
   * from this list all the elements that are not contained in the specified
   * collection.
   *
   * @param c collection containing elements to be retained in this list
   * @return <tt>true</tt> if this list changed as a result of the call
   * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of an element of this list
   *         is incompatible with the specified collection (optional)
   * @throws NullPointerException if this list contains a null element and the
   *         specified collection does not permit null elements (optional),
   *         or if the specified collection is null
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    if (subItems != null) {
      return subItems.retainAll(c);
    }

    return false;
  }

  /**
   * Reverses the order of the elements in the list
   */
  @Override
  public List<RenderableDataItem> reverse() {
    if (subItems != null) {
      subItems.reverse();
    }

    return this;
  }

  /**
   * Removes a value from the begining of the list
   *
   * @return the removed value or null if the list is empty
   */
  @Override
  public RenderableDataItem shift() {
    return ((subItems == null) || (subItems.size() == 0))
           ? null
           : subItems.remove(0);
  }

  /**
   * Returns the number of elements in this list.  If this list contains
   * more than <tt>Integer.MAX_VALUE</tt> elements, returns
   * <tt>Integer.MAX_VALUE</tt>.
   *
   * @return the number of elements in this list
   */
  @Override
  public int size() {
    return (subItems == null)
           ? 0
           : subItems.size();
  }

  /**
   * Creates a new list from a selected section of this list
   * (from the specified position to the end of the list)
   *
   * @param start the starting position inclusive
   * @return the new list
   */
  @Override
  public List<RenderableDataItem> slice(int start) {
    return this.subItemsList(1).slice(start, size());
  }

  /**
   * Creates a new list from a selected section of this list
   *
   * @param start the starting position inclusive
   * @param end the end position exclusive
   * @return the new list
   */
  @Override
  public List<RenderableDataItem> slice(int start, int end) {
    return this.subItemsList(1).slice(start, end);
  }

  /**
   * Sorts the list's subitems in ascending order
   */
  public void sort() {
    sort(null);
  }

  /**
   * Sorts the list's subitems
   *
   * @param descending true to sort in descending order; false otherwise
   */
  public void sort(boolean descending) {
    if (subItems != null) {
      if (descending) {
        subItems.sort(SubItemComparator.getReverseOrderComparatorInstance());
      } else {
        subItems.sort(null);
      }
    }
  }

  /**
   * Sorts the current items using the specified comparator
   *
   * @param comparator the comparator
   */
  @Override
  public void sort(Comparator comparator) {
    if (subItems != null) {
      subItems.sort(comparator);
    }
  }

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   */
  @Override
  public List<RenderableDataItem> splice(int index, int howMany) {
    return splice(index, howMany, (RenderableDataItem[]) null);
  }

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   */
  @Override
  public List<RenderableDataItem> splice(int index, int howMany, RenderableDataItem... e) {
    return spliceList(index, howMany, (e == null)
                                      ? null
                                      : Arrays.asList(e));
  }

  /**
   * Adds and/or removes elements of an the list.
   *
   * @param index the index at which you wish to start removing elements
   * @param howMany the number to remove
   * @param e the elements to add
   */
  @Override
  public List<RenderableDataItem> spliceList(int index, int howMany, List<RenderableDataItem> e) {
    int len = size();

    if (index < 0) {
      index = len + index;
    }

    if (index < 0) {
      throw new IllegalArgumentException("index=" + index);
    }

    int i = index;

    if (len > i + howMany) {
      len = i + howMany;
    }

    List<RenderableDataItem> rlist = new FilterableList((i < len)
            ? len - i
            : 0);

    while(i < len) {
      rlist.add(get(index));
      remove(index);
      i++;
    }

    if (e != null) {
      if (index >= size()) {
        addAll(e);
      } else {
        addAll(index, e);
      }
    }

    return rlist;
  }

  /**
   * Returns a view of the portion of this list between the specified
   * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
   * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
   * empty.)  The returned list is backed by this list, so non-structural
   * changes in the returned list are reflected in this list, and vice-versa.
   * The returned list supports all of the optional list operations supported
   * by this list.<p>
   *
   * This method eliminates the need for explicit range operations (of
   * the sort that commonly exist for arrays).   Any operation that expects
   * a list can be used as a range operation by passing a subList view
   * instead of a whole list.  For example, the following idiom
   * removes a range of elements from a list:
   * <pre>
   *      list.subList(from, to).clear();
   * </pre>
   * Similar idioms may be constructed for <tt>indexOf</tt> and
   * <tt>lastIndexOf</tt>, and all of the algorithms in the
   * <tt>Collections</tt> class can be applied to a subList.<p>
   *
   * The semantics of the list returned by this method become undefined if
   * the backing list (i.e., this list) is <i>structurally modified</i> in
   * any way other than via the returned list.  (Structural modifications are
   * those that change the size of this list, or otherwise perturb it in such
   * a fashion that iterations in progress may yield incorrect results.)
   *
   * @param fromIndex low endpoint (inclusive) of the subList
   * @param toIndex high endpoint (exclusive) of the subList
   * @return a view of the specified range within this list
   * @throws IndexOutOfBoundsException for an illegal endpoint index value
   *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
   *         fromIndex &gt; toIndex</tt>)
   */
  @Override
  public List<RenderableDataItem> subList(int fromIndex, int toIndex) {
    return subItemsList(0).subList(fromIndex, toIndex);
  }

  /**
   * Swaps the values of the specified indexes
   *
   * @param index1 the first index
   * @param index2 the second index
   */
  @Override
  public void swap(int index1, int index2) {
    if (subItems != null) {
      subItems.swap(index1, index2);
    }
  }

  /**
   * Returns an array containing all of the elements in this list in proper
   * sequence (from first to last element).
   *
   * <p>The returned array will be "safe" in that no references to it are
   * maintained by this list.  (In other words, this method must
   * allocate a new array even if this list is backed by an array).
   * The caller is thus free to modify the returned array.
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all of the elements in this list in proper
   *         sequence
   */
  @Override
  public Object[] toArray() {
    return subItemsList(0).toArray();
  }

  /**
   * Returns an array containing all of the elements in this list in
   * proper sequence (from first to last element); the runtime type of
   * the returned array is that of the specified array.  If the list fits
   * in the specified array, it is returned therein.  Otherwise, a new
   * array is allocated with the runtime type of the specified array and
   * the size of this list.
   *
   * <p>If the list fits in the specified array with room to spare (i.e.,
   * the array has more elements than the list), the element in the array
   * immediately following the end of the list is set to <tt>null</tt>.
   * (This is useful in determining the length of the list <i>only</i> if
   * the caller knows that the list does not contain any null elements.)
   *
   * <p>Like the toArray() method, this method acts as bridge between
   * array-based and collection-based APIs.  Further, this method allows
   * precise control over the runtime type of the output array, and may,
   * under certain circumstances, be used to save allocation costs.
   *
   * <p>Suppose <tt>x</tt> is a list known to contain only strings.
   * The following code can be used to dump the list into a newly
   * allocated array of <tt>String</tt>:
   *
   * <pre>
   *     String[] y = x.toArray(new String[0]);</pre>
   *
   * Note that <tt>toArray(new Object[0])</tt> is identical in function to
   * <tt>toArray()</tt>.
   *
   * @param a the array into which the elements of this list are to
   *          be stored, if it is big enough; otherwise, a new array of the
   *          same runtime type is allocated for this purpose.
   * @return an array containing the elements of this list
   * @throws ArrayStoreException if the runtime type of the specified array
   *         is not a supertype of the runtime type of every element in
   *         this list
   * @throws NullPointerException if the specified array is null
   */
  @Override
  public <T> T[] toArray(T[] a) {
    return subItemsList(0).toArray(a);
  }

  /**
   * Returns a CharSequence representation of the item's value. If the item
   * does not have but has sub-items then the string representation of
   * the sub-items are returned
   *
   * @return a CharSequence representation of the item's value (or list of sub-items).
   */
  public CharSequence toCharSequence() {
    if ((theValue == null) && (subItems != null) && (subItems.size() > 0)) {
      return Helper.toString(subItems, "\t");
    }

    return toCharSequence(null);
  }

  /**
   * Returns a character sequence representation of the item
   *
   * @param widget the widget to use to use to perform any needed conversions
   *
   * @return  a character sequence representation of the item
   */
  public CharSequence toCharSequence(iWidget widget) {
    return toCharSequence(widget, null, null);
  }

  /**
   * Returns a character sequence representation of the item
   *
   * @param widget the widget to use to use to perform any needed conversions
   * @param defConverter a default converter to use if the item does not have its own
   * @param defContext a default converter context to use if the item does not have its own
   *
   * @return  a character sequence representation of the item
   */
  public CharSequence toCharSequence(iWidget widget, iDataConverter defConverter, Object defContext) {
    if (_toString != null) {
      return _toString;
    }

    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    iDataConverter cvt = ((dataConverter == null) || ((defContext != null) && usingDefaultConverter))
                         ? defConverter
                         : dataConverter;
    Object         ctx = (valueContext == null)
                         ? defContext
                         : valueContext;

    if (!converted && (valueContext == null) && (dataConverter == null) && (defConverter == null)
        && (defContext == null)) {
      converted = true;
    } else if (!converted) {
      if (theValue instanceof String) {
        theValue = fromString(widget, theType, (String) theValue, cvt, ctx);
      } else {
        getValue();
      }
    }

    if (cvt != null) {
      _toString = cvt.objectToString(widget, theValue, ctx);

      if (_toString == null) {
        _toString = "";
      }
    } else if (theValue instanceof CharSequence) {
      _toString = (CharSequence) theValue;
    } else {
      _toString = (theValue == null)
                  ? ""
                  : theValue.toString();
    }

    return _toString;
  }

  /**
   * Returns a character sequence representation of the passed in value
   * using the characteristics of this item to create/format hte string
   *
   * @param widget the widget to use to use to perform any needed conversions
   * @param defConverter a default converter to use if the item does not have its own
   * @param defContext a default converter context to use if the item does not have its own
   *
   * @return  a character sequence representation of the item
   */
  public CharSequence toCharSequence(iWidget widget, Object value, iDataConverter defConverter, Object defContext) {
    if (_toString != null) {
      if (value == theValue) {
        return _toString;
      }
    }

    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    iDataConverter cvt = ((dataConverter == null) || ((defContext != null) && usingDefaultConverter))
                         ? defConverter
                         : dataConverter;
    Object         ctx = (valueContext == null)
                         ? defContext
                         : valueContext;

    if (!converted && (valueContext == null) && (dataConverter == null) && (defConverter == null)
        && (defContext == null)) {
      converted = true;
    } else if (!converted) {
      if (theValue instanceof String) {
        theValue = fromString(widget, theType, (String) theValue, cvt, ctx);
      } else {
        getValue();
      }
    }

    CharSequence s;

    if (cvt != null) {
      s = cvt.objectToString(widget, theValue, ctx);
    } else if (theValue instanceof CharSequence) {
      s = (CharSequence) theValue;
    } else {
      s = (theValue == null)
          ? ""
          : theValue.toString();
    }

    if (value == theValue) {
      _toString = s;
    }

    return s;
  }

  /**
   * Converts the specified object to a renderable data item.
   * If the object is already a renderable data item then it is returned
   * otherwise a new item is created and the passed in object is its value
   * If the object is null then a null is returned
   *
   * @param o the object to convert
   * @return null or a  a renderable data item
   */
  public static RenderableDataItem toItem(Object o) {
    if (o == null) {
      return null;
    }

    if (o instanceof RenderableDataItem) {
      return (RenderableDataItem) o;
    } else {
      return new RenderableDataItem(o);
    }
  }

  /**
   *  Converts the specified list to a renderable data item.
   *  whose children are the specified list of items
   *
   *  @param children the children for the new item
   *  @return  a renderable data item
   */
  public static RenderableDataItem toParentItem(iFilterableList<RenderableDataItem> children) {
    RenderableDataItem row = new RenderableDataItem();

    row.subItems = children;

    return row;
  }

  /**
   * Returns a string representation of the item's value. If the item
   * does not have but has sub-items then the string representation of
   * the sub-items are returned
   *
   * @return a string representation of the item's value (or list of sub-items).
   */
  @Override
  public String toString() {
    if ((theValue == null) && (subItems != null) && (subItems.size() > 0)) {
      return Helper.toString(subItems, "\t");
    }

    return toString(null);
  }

  /**
   * Returns a string representation of the item
   *
   * @param widget the widget to use to use to perform any needed conversions
   *
   * @return  a string representation of the item
   */
  public String toString(iWidget widget) {
    if (_toString == null) {
      toCharSequence(widget, null, null);
    }

    return (_toString instanceof String)
           ? (String) _toString
           : _toString.toString();
  }

  /**
   * Converts the item to it's string representation
   *
   * @param sb a buffer to use to store the output
   * @param tab tabs (or spaces) to prefix lines by
   */
  public void toString(StringBuilder sb, String tab) {
    sb.append(tab);

    List<RenderableDataItem> items = this.getItems();
    int                      len   = this.getItemCount();

    sb.append(toString());

    if (len == 0) {
      return;
    }

    sb.append("{\r\n");

    int                i    = 1;
    String             stab = tab + " ";
    RenderableDataItem di   = items.get(0);

    if (di != null) {
      di.toString(sb, stab);
    }

    while(i < len) {
      sb.append("\r\n");
      di = items.get(i++);

      if (di != null) {
        di.toString(sb, stab);
      }
    }

    sb.append("\r\n").append(tab).append("}");
  }

  /**
   * Returns a string representation of the item
   *
   * @param widget the widget to use to use to perform any needed conversions
   * @param defConverter a default converter to use if the item does not have its own
   * @param defContext a default converter context to use if the item does not have its own
   *
   * @return  a string representation of the item
   */
  public String toString(iWidget widget, iDataConverter defConverter, Object defContext) {
    toCharSequence(widget, defConverter, defContext);

    return (_toString instanceof String)
           ? (String) _toString
           : _toString.toString();
  }

  /**
   * Trims the number of sub-items to match the specified size
   *
   * @param size the size to trim to
   */
  public void trimTo(int size) {
    chop(size() - size);
  }

  @Override
  public void trimToSize() {
    if (subItems != null) {
      subItems.trimToSize();
    }
  }

  /**
   * Return the integer value of the specified type name
   *
   * @param name the type name
   * @return the integer value corresponding to the type name
   */
  public static int typeOf(String name) {
    if (name != null) {
      name = name.toUpperCase(Locale.US);

      Integer in = typeMap.get(name);

      if (in != null) {
        return in;
      }
    }

    return TYPE_UNKNOWN;
  }

  /**
   * Removes an existing filters on the list
   *
   * @return whether there were any filters that were removed
   */
  @Override
  public boolean unfilter() {
    if (subItems != null) {
      return subItems.unfilter();
    }

    return false;
  }

  /**
   * Clears all user state flags
   */
  public void clearUseStateFlags() {
    userStateFlags = 0;
  }

  /*
   * Unsets a user state flag for the item
   *
   * @param flag the flag to unset set
   */
  public void unsetUserStateFlag(byte flag) {
    if ((userStateFlags & flag) != 0) {
      userStateFlags -= flag;
    }
  }

  /*
   * Unsets user state flag for the item
   *
   * @param flag the flag to unset set
   */
  public void unsetStateFlag(short flag) {
    if ((stateFlags & flag) != 0) {
      stateFlags -= flag;
    }
  }

  /**
   * Adds a value to the begining of the list
   *
   * @param value the value to add
   */
  @Override
  public void unshift(RenderableDataItem value) {
    add(0, value);
  }

  /**
   * Tests whether this item's value is equal to the specified value
   *
   * @param val the value to test
   *
   * @return true if they are equal; false otherwise
   */
  public boolean valueEquals(Object val) {
    if (theValue == val) {
      return true;
    }

    return (theValue == null)
           ? false
           : theValue.equals(val);
  }

  /**
   * Replaces the element at the specified position in this list with the
   * specified element (optional operation).
   *
   * @param index index of the element to replace
   * @param element element to be stored at the specified position
   * @return the element previously at the specified position
   * @throws UnsupportedOperationException if the <tt>set</tt> operation
   *         is not supported by this list
   * @throws ClassCastException if the class of the specified element
   *         prevents it from being added to this list
   * @throws NullPointerException if the specified element is null and
   *         this list does not permit null elements
   * @throws IllegalArgumentException if some property of the specified
   *         element prevents it from being added to this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  @Override
  public RenderableDataItem set(int index, RenderableDataItem element) {
    return setItem(index, element);
  }

  /**
   * Sets the code the for the item's associated action
   *
   * @param context the context
   * @param code the code to execute
   */
  public void setActionCode(iWidget context, String code) {
    this.actionListener = new ScriptActionListener(context, this, code);
  }

  /**
   * Sets the item's associated action
   *
   * @param al the item's associated action listener
   */
  public void setActionListener(iActionListener al) {
    this.actionListener = al;
  }

  /**
   * Sets the tree to be the contents of the specified collection
   *
   * @param collection the collection for the tree
   * @return true if the elements were added; false otherwise
   */
  @Override
  public boolean setAll(Collection<? extends RenderableDataItem> collection) {
    clear();

    return addAll(collection);
  }

  /**
   * Sets an alternate icon for the item
   *
   * @param icon the icon
   */
  public void setAlternateIcon(iPlatformIcon icon) {
    alternateIcon = icon;
  }

  /**
   * Sets the item's background color
   *
   * @param bg the background color
   */
  public void setBackground(UIColor bg) {
    bgColor = bg;

    if (bg instanceof UIColorShade) {
      UIColorShade cs = (UIColorShade) bg;

      if (cs.getBackgroundPainter() != null) {
        setBackgroundPainter(cs.getBackgroundPainter());
      }
    }
  }

  /**
   *  Sets the item's background painter
   *
   *  @param bp the background painter
   */
  public void setBackgroundOverlayPainter(iBackgroundPainter bp) {
    if (componentPainter == null) {
      if (bp == null) {
        return;
      }

      componentPainter = new UIComponentPainter();
    }

    componentPainter.setBackgroundOverlayPainter(bp);
  }

  /**
   *  Sets the item's background painter
   *
   *  @param bp the background painter
   */
  public void setBackgroundPainter(iBackgroundPainter bp) {
    if (componentPainter == null) {
      if (bp == null) {
        return;
      }

      componentPainter = new UIComponentPainter();
    }

    componentPainter.setBackgroundPainter(bp, false);
  }

  /**
   * @param itemBorder the itemBorder to set
   */
  public void setBorder(iPlatformBorder itemBorder) {
    if (componentPainter == null) {
      if (itemBorder == null) {
        return;
      }

      componentPainter = new UIComponentPainter();
    }

    componentPainter.setBorder(itemBorder);
  }

  /**
   * Sets the number of columns the item will span
   *
   * @param span the number of columns the item will span
   */
  public void setColumnSpan(int span) {
    if (span == -1) {
      span = -1;
    }

    columnSpan = span;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    componentPainter = cp;
  }

  /**
   * Sets whether the value has been converted
   *
   * @param converted true if the value has been converted; false otherwise
   */
  public void setConverted(boolean converted) {
    this.converted = converted;
  }

  /**
   * Sets a converter that converts a item to a string for filtering
   *
   * @param converter the converter
   */
  @Override
  public void setConverter(iStringConverter<RenderableDataItem> converter) {
    if (converter == null) {
      if (subItems != null) {
        subItems.setConverter(null);
      }
    } else {
      if (subItems == null) {
        subItems = new FilterableList<RenderableDataItem>(10);
      }

      subItems.setConverter(converter);
    }
  }

  /**
   * Sets the data value converter class
   *
   * @param cls the data value converter class
   */
  public void setConverterClass(Class cls) {
    if (cls != null) {
      dataConverter = Platform.getDataConverter(cls);
    } else {
      dataConverter = null;
    }
  }

  /**
   * Sets when the special named cursor is to be displayed for the item
   *
   * @param cursorDisplayType the cursorDisplayType to set
   * @see #setCursorName(java.lang.String)
   */
  public void setCursorDisplayType(CursorDisplayType cursorDisplayType) {
    this.cursorDisplayType = cursorDisplayType;
  }

  /**
   * Sets the name of the cursor for the item
   *
   * @param name the name of the cursor for the item
   */
  public void setCursorName(String name) {
    this.cursorName = name;
  }

  /**
   * Sets the custom property map
   *
   * @param map the map
   */
  public void setCustomProperties(Map map) {
    this.customProperties = map;

    if (oneProperty != null) {
      oneProperty.clear();
      oneProperty = null;
    }
  }

  /**
   * Sets a custom property for the item
   * @param key the key for the property
   * @param value the value for the property
   *
   * @return the previous value for the attribute
   */
  public Object setCustomProperty(Object key, Object value) {
    if (customProperties == null) {
      if (oneProperty == null) {
        oneProperty = new ObjectHolder(key, value);

        return null;
      } else if (oneProperty.typeEquals(key)) {
        final Object ovalue = oneProperty.value;

        oneProperty.value = value;

        return ovalue;
      } else {
        customProperties = new HashMap(5);
        customProperties.put(oneProperty.type, oneProperty.value);
        oneProperty.clear();
        oneProperty = null;
      }
    }

    return customProperties.put(key, value);
  }

  /**
   * Sets the data value converter
   *
   * @param cvt the data value converter
   */
  public void setDataConverter(iDataConverter cvt) {
    this.dataConverter    = cvt;
    usingDefaultConverter = false;
  }

  /**
   * Sets the disabled icon for the item
   *
   * @param icon the icon
   */
  public void setDisabledIcon(iPlatformIcon icon) {
    disabledIcon = icon;
  }

  /**
   * Sets whether the item supports dragging for drag and drop operations
   *
   * @param draggingAllowed true if the item supports; false otherwise
   */
  public void setDraggingAllowed(boolean draggingAllowed) {
    this.draggingAllowed = draggingAllowed;
  }

  /**
   * Sets the editable flag for the item
   *
   * @param editable true if it is editable; false otherwise
   */
  public void setEditable(boolean editable) {
    if (editable) {
      stateFlags |= ITEM_EDITABLE;
    } else if ((stateFlags & ITEM_EDITABLE) > 0) {
      stateFlags -= ITEM_EDITABLE;
    }

    stateFlags |= ITEM_EDITABLE_SET;
  }

  /**
   * Sets the item's enabled status
   *
   * @param enabled <CODE>true</CODE> for enabled; <CODE>false</CODE> otherwise
   */
  public void setEnabled(boolean enabled) {
    if (!enabled) {
      stateFlags |= ITEM_DISABLED;
    } else if ((stateFlags & ITEM_DISABLED) > 0) {
      stateFlags -= ITEM_DISABLED;
    }
  }

  /**
   * Sets the data flavors that the item will export
   * during drag and drop operations
   *
   * @param flavorNames the data flavors that the item can import
   */
  public void setExportDataFlavors(String[] flavorNames) {
    exportDataFlavors = flavorNames;
  }

  /**
   * Sets whether filtering should be done based on the item's linked data
   * instead of the item's value
   *
   * @param enable true to filter on the item's linked data; false otherwise
   */
  public void setFilterOnLinkedData(boolean enable) {
    if (enable) {
      setConverter(new iStringConverter<RenderableDataItem>() {
        @Override
        public String toString(RenderableDataItem element) {
          Object o = element.linkedData;

          return (o == null)
                 ? ""
                 : o.toString();
        }
      });
    } else {
      setConverter(null);
    }
  }

  /**
   * Sets the item's font
   *
   * @param font the font for the item
   */
  public void setFont(UIFont font) {
    theFont = font;
  }

  /**
   * Sets the item's foreground color
   *
   * @param fg the foreground color
   */
  public void setForeground(UIColor fg) {
    fgColor = fg;
  }

  /**
   * Sets the context specific height of the item
   *
   * @param height the context specific height of the item
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Sets the horizontal alignment for the item
   *
   * @param alignment the horizontal alignment for the item
   */
  public void setHorizontalAlignment(HorizontalAlign alignment) {
    horizontalAlign = alignment;

    if ((alignment == HorizontalAlign.AUTO) && ((theType == TYPE_DECIMAL) || (theType == TYPE_INTEGER))) {
      swingHorizAlign = getSwingHorizAlignment(HorizontalAlign.TRAILING);
    } else {
      swingHorizAlign = getSwingHorizAlignment(alignment);
    }
  }

  /**
   * Sets the icon for the item
   *
   * @param icon the icon
   */
  public void setIcon(iPlatformIcon icon) {
    displayIcon = icon;
  }

  /**
   * Sets the item's icon position
   *
   * @param position the position of the item's icon
   */
  public void setIconPosition(IconPosition position) {
    iconPosition = position;
  }

  /**
   * Sets the data flavors that the item can import
   * during drag and drop operations
   *
   * @param flavorNames the names of the data flavors that the item can import
   */
  public void setImportDataFlavors(String[] flavorNames) {
    importDataFlavors = flavorNames;
  }

  /**
   * Replaces the item at the specified position in this list with the specified item
   *
   * @param pos the position of the item to replace
   * @param item the item to be stored at the specified position
   *
   * @return the item previously at the specified position
   */
  public RenderableDataItem setItem(int pos, RenderableDataItem item) {
    item.setParentItem(this);

    return subItemsList(1).set(pos, item);
  }

  /**
   * Sets the count of items in the list
   * The item array will be filled will null values if
   * the specified count is greater that the existing count
   *
   * @param count the count
   */
  public void setItemCount(int count) {
    int len = size();
    int dif = count - len;

    if (dif > 0) {
      List<RenderableDataItem> l = subItemsList(dif);

      for (int i = len; i < count; i++) {
        l.add(null);
      }
    } else if (dif < 0) {
      List<RenderableDataItem> l = subItemsList(0);

      for (int i = len; i > count; i--) {
        l.remove(i - 1);
      }
    }
  }

  /**
   * Replaces all of the sub-items the items in the specified list
   *
   * @param items the items that will become the new list of sub-items
   */
  public void setItems(List<RenderableDataItem> items) {
    final int count = (items == null)
                      ? 0
                      : items.size();

    if (count == 0) {
      clearSubItems();
    } else {
      List<RenderableDataItem> subitems = emptySubItemsList(count);
      RenderableDataItem       item;

      for (int i = 0; i < count; i++) {
        item = items.get(i);
        item.setParentItem(this);
        subitems.add(item);
      }
    }
  }

  /**
   * Replaces all of the sub-items the items in the specified array
   *
   * @param items the items that will become the new list of sub-items
   */
  public void setItems(RenderableDataItem[] items) {
    setItems(items, (items == null)
                    ? 0
                    : items.length);
  }

  /**
   * Replaces all of the sub-items the items in the specified array
   *
   * @param items the items that will become the new list of sub-items
   * @param count the count of items to use from the specified list
   */
  public void setItems(RenderableDataItem[] items, int count) {
    List<RenderableDataItem> subitems = emptySubItemsList(count);
    RenderableDataItem       item;

    for (int i = 0; i < count; i++) {
      item = items[i];
      item.setParentItem(this);
      subitems.add(item);
    }
  }

  /**
   * Set additional data to be linked with this item
   *
   * @param data the data to link with this item
   */
  public void setLinkedData(Object data) {
    linkedData = data;
  }

  /**
   * Sets the context to use to interpret the item's linked data
   *
   * @param context the context
   */
  public void setLinkedDataContext(Object context) {
    this.linkedDataContext = context;
  }

  /**
   * Sets the linked data converter
   *
   * @param cvt the linked data converter
   */
  public void setLinkedDataConverter(iDataConverter cvt) {
    this.ldDataConverter = cvt;
  }

  /**
   * Sets the model specific data for the item.
   * This is data that a component's model can associate with the item
   *
   * @param modelData the model data
   */
  public void setModelData(Object modelData) {
    this.modelData = modelData;
  }

  /**
   * Sets the modified flag for the item
   *
   * @param modified true if it was modifed; false otherwise
   */
  public void setModified(boolean modified) {
    if (modified) {
      stateFlags |= ITEM_MODIFIED;
    } else if ((stateFlags & ITEM_MODIFIED) > 0) {
      stateFlags -= ITEM_MODIFIED;
    }
  }

  /**
   * Sets the item's orientation
   *
   * @param orientation the item's orientation
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  /**
   * Sets this item's parent item
   *
   * @param parent the parent of t his item
   */
  public void setParentItem(RenderableDataItem parent) {
    parentItem = parent;
  }

  /**
   * Sets the item specific rendering component.
   * This is the component that should be used to
   * render the item
   *
   * @param comp the component that should be used to
   *         render the item
   */
  public void setRenderingComponent(iPlatformComponent comp) {
    this.renderingComponent = comp;
  }

  /**
   * Sets the number of rows the item will span
   *
   * @param span the number of rows the item will span
   */
  public void setRowSpan(int span) {
    rowSpan = span;
  }

  /**
   * Sets the item's selectable status
   *
   * @param selectable <CODE>true</CODE> for selectable; <CODE>false</CODE> otherwise
   */
  public void setSelectable(boolean selectable) {
    if (!selectable) {
      stateFlags |= ITEM_NOT_SELECTABLE;
    } else if ((stateFlags & ITEM_NOT_SELECTABLE) > 0) {
      stateFlags -= ITEM_NOT_SELECTABLE;
    }
  }

  /**
   * Sets the context specific spanning data
   *
   * @param spandata the spanning data
   */
  public void setSpanningData(Object spandata) {
    this.spanData = spandata;
  }

  /**
   * Sets a state flag for the item
   *
   * @param flag the flag to set
   */
  public void setStateFlag(short flag) {
    stateFlags |= flag;
  }

  /**
   * Sets the string representation of the item
   *
   * @param value the value to be returned by the toString method
   *
   */
  public void setToStringValue(CharSequence value) {
    _toString = value;
  }

  /**
   * Sets the item's tooltip
   *
   * @param tooltip the tooltip
   */
  public void setTooltip(CharSequence tooltip) {
    this.tooltip = tooltip;
  }

  /**
   * Sets the item's type
   *
   * @param type the item's type
   */
  public void setType(int type) {
    boolean nt = type != theType;

    theType = type;

    if (nt) {
      converted = false;
      _toString = null;
      setupDefaultConverter();
    }
  }

  /**
   * Sets whether the hashCode function should return an identity hash code
   *
   * @param useIdentityHashCode <CODE>true</CODE> to use an identity hashcode; <CODE>false</CODE> otherwise
   */
  public void setUseIdentityHashCode(boolean useIdentityHashCode) {
    this.useIdentityHashCode = useIdentityHashCode;
  }

  /**
   * Sets a user state flag for the item
   *
   * @param flag the flag to set
   */
  public void setUserStateFlag(byte flag) {
    userStateFlags |= flag;
  }

  /**
   * Sets the item's value
   *
   * @param value the value for the item
   */
  public void setValue(Object value) {
    theValue  = value;
    _toString = null;

    if (value instanceof String) {
      converted = theType == TYPE_STRING;
      if(converted) {
        _toString=(String)value;
      }
    } else {
      converted = true;
    }
  }

  /**
   * Sets the context to use to interpret the item's value
   *
   * @param context the context
   */
  public void setValueContext(Object context) {
    valueContext = context;
  }

  /**
   * Sets the item's values
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   * @param context the item's value context
   */
  public void setValues(Object value, int type, Object data, iPlatformIcon icon, Object context) {
    _toString = null;

    if (value instanceof String) {
      converted = theType == TYPE_STRING;
      if(converted) {
        _toString=(String)value;
      }
    } else {
      converted = true;
    }

    theValue     = value;
    theType      = type;
    linkedData   = data;
    displayIcon  = icon;
    valueContext = context;

    if ((dataConverter == null) || usingDefaultConverter) {
      setupDefaultConverter();
    }
  }

  /**
   * Sets the vertical alignment for the item
   *
   * @param alignment the vertical alignment for the item
   */
  public void setVerticalAlignment(VerticalAlign alignment) {
    verticalAlign = alignment;

    if (alignment == VerticalAlign.AUTO) {
      swingVertAlign = getSwingVertAlignment(VerticalAlign.CENTER);
    } else {
      swingVertAlign = getSwingVertAlignment(alignment);
    }
  }

  /**
   * Sets the item's visible status
   *
   * @param visible <CODE>true</CODE> for visible; <CODE>false</CODE> otherwise
   */
  public void setVisible(boolean visible) {
    if (!visible) {
      stateFlags |= ITEM_HIDDEN;
    } else if ((stateFlags & ITEM_HIDDEN) > 0) {
      stateFlags -= ITEM_HIDDEN;
    }
  }

  /**
   *   Sets the context specific width of the item
   *
   *   @param width the context specific width of the item
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
   */
  @Override
  public RenderableDataItem get(int index) {
    return getItem(index);
  }

  /**
   * Gets the item's associated action listener
   *
   * @return the item's associated action listener
   */
  public iActionListener getActionListener() {
    return actionListener;
  }

  /**
   * Gets the alternate icon for the item
   *
   * @return the alternate icon for the item
   */
  public iPlatformIcon getAlternateIcon() {
    return alternateIcon;
  }

  /**
   * Gets the background color for the item
   *
   * @return the background color for the item
   */
  public UIColor getBackground() {
    return bgColor;
  }

  /**
   * @return the itemBorder
   */
  public iPlatformBorder getBorder() {
    return (componentPainter == null)
           ? null
           : componentPainter.getBorder();
  }

  /**
   * Gets the number of columns this item will span
   *
   * @return the number of columns this item will span
   */
  public int getColumnSpan() {
    return columnSpan;
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    return componentPainter;
  }

  /**
   * Gets the converter that converts a item to a string for filtering
   *
   * @return the converter that converts a item to a string for filtering
   */
  @Override
  public iStringConverter<RenderableDataItem> getConverter() {
    return (subItems == null)
           ? null
           : subItems.getConverter();
  }

  /**
   * Get the cursor display type
   *
   * @return the cursor display yype
   */
  public CursorDisplayType getCursorDisplayType() {
    return cursorDisplayType;
  }

  /**
   * Gets the name of the cursor for the item
   *
   * @return the name of the cursor for the item
   */
  public String getCursorName() {
    return cursorName;
  }

  /**
   * Gets the custom attribute map
   *
   * @return map the map
   */
  public Map getCustomProperties() {
    if ((customProperties == null) && (oneProperty != null)) {
      customProperties = new HashMap(5);
      customProperties.put(oneProperty.type, oneProperty.value);
      oneProperty.clear();
      oneProperty = null;
    }

    return customProperties;
  }

  /**
   * Gets a custom property for the item
   *
   * @param key the key for the property
   *
   * @return the value for the property
   */
  public Object getCustomProperty(Object key) {
    if ((oneProperty != null) && (customProperties == null)) {
      return oneProperty.typeEquals(key)
             ? oneProperty.value
             : null;
    }

    return (customProperties == null)
           ? null
           : customProperties.get(key);
  }

  /**
   * Gets the data value converter
   *
   * @return the data value converter
   */
  public iDataConverter getDataConverter() {
    return dataConverter;
  }

  /**
   * Gets the default data value converter class for the
   * specified value type
   *
   * @param type the data type
   *
   * @return the data value converter class
   */
  public static Class getDefaultConverterClass(int type) {
    switch(type) {
      case TYPE_DECIMAL :
      case TYPE_INTEGER :
        return NumberConverter.class;

      case TYPE_DATE :
        return DateConverter.class;

      case TYPE_DATETIME :
        return DateTimeConverter.class;

      case TYPE_TIME :
        return TimeConverter.class;

      case TYPE_BOOLEAN :
        return BooleanConverter.class;

      case TYPE_STRING :
        return StringConverter.class;

      case TYPE_WIDGET :
        return WidgetConverter.class;

      default :
        return null;
    }
  }

  /**
   * Gets the disabled icon for the item
   *
   * @return the disabled icon for the item
   */
  public iPlatformIcon getDisabledIcon() {
    return disabledIcon;
  }

  /**
   * Gets the data flavors that the item can export
   * during drag and drop operations
   *
   * @return the data flavors that the item can export
   */
  public final String[] getExportableDataFlavors() {
    return exportDataFlavors;
  }

  /**
   * Returns the filtered list
   *
   * @return the filtered list or null if the list is not filtered
   */
  @Override
  public List<RenderableDataItem> getFilteredList() {
    return (subItems == null)
           ? null
           : subItems.getFilteredList();
  }

  /**
   * Gets the font for the item
   *
   * @return the font for the item
   */
  public UIFont getFont() {
    float rs = UIFontHelper.getRelativeFontSize();

    if ((theFont != null) && (rs != relativeFontSize)) {
      theFont          = theFont.deriveFontSize((rs / relativeFontSize) * theFont.getSize2D());
      relativeFontSize = rs;
    }

    return theFont;
  }

  /**
   * Gets the foreground color for the item
   *
   * @return the foreground color for the item
   */
  public UIColor getForeground() {
    return fgColor;
  }

  /**
   * Gets the context specific height of the item
   *
   * @return the context specific height of the item
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the horizontal alignment for the item
   *
   * @return the horizontal alignment for the item
   */
  public HorizontalAlign getHorizontalAlignment() {
    return horizontalAlign;
  }

  /**
   * Gets the icon for the item
   *
   * @return the icon for the item
   */
  public iPlatformIcon getIcon() {
    return displayIcon;
  }

  /**
   * Gets the item's icon position
   *
   * @return the item's icon position
   */
  public IconPosition getIconPosition() {
    return iconPosition;
  }

  /**
   * Gets the Swing equivalent horizontal alignment for this item
   *
   * @param position a position to use if the items position is set to AUTO
   *
   * @return the item's icon position. If the item's icon position is set to AUTO then the passed in position is returned
   */
  public IconPosition getIconPosition(IconPosition position) {
    return (iconPosition == IconPosition.AUTO)
           ? position
           : iconPosition;
  }

  /**
   * Gets the data flavors that the item can import
   * during drag and drop operations
   *
   * @return the data flavors that the item can import
   */
  public final String[] getImportableDataFlavors() {
    return importDataFlavors;
  }

  /**
   * Gets the sub-item at the specified position
   *
   * @param pos the position of the item to retrieve
   * @return the sub-item at the specified position
   */
  public RenderableDataItem getItem(int pos) {
    if (subItems == null) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + ">=0");
    }

    return subItems.get(pos);
  }

  /**
   * Gets the number of sub-items that this item contains
   *
   * @return the number of sub-items that this item contains
   */
  public int getItemCount() {
    return (subItems == null)
           ? 0
           : subItems.size();
  }

  /**
   * Gets the sub-item at the specified position
   *
   * @param pos the position of the item to retrieve
   * @return the sub-item at the specified position or null if the position is out of bounds
   */
  public RenderableDataItem getItemEx(int pos) {
    if ((pos < 0) || (subItems == null) || (pos >= subItems.size())) {
      return null;
    }

    return subItems.get(pos);
  }

  /**
   * Gets the list of sub-items that this item contains
   *
   * @return the list of sub-items that this item contains or null if there list was never created;
   */
  public iFilterableList<RenderableDataItem> getItems() {
    return subItems;
  }

  /**
   * Gets the java class that represents the data type of this item
   *
   * @return the java class that represents the data type of this item
   */
  public Class getJavaClass() {
    if (this.dataConverter != null) {
      return dataConverter.getObjectClass(valueContext);
    }

    switch(theType) {
      case TYPE_STRING :
        return String.class;

      case TYPE_INTEGER :
        return Integer.class;

      case TYPE_DECIMAL :
        return Double.class;

      case TYPE_DATE :
      case TYPE_DATETIME :
        return Date.class;

      case TYPE_ARRAY :
        return Object[].class;

      case TYPE_BOOLEAN :
        return Boolean.class;

      default :
        break;
    }

    Object o = getValue();

    return (o == null)
           ? Object.class
           : o.getClass();
  }

  @Override
  public iFilter getLastFilter() {
    return (subItems == null)
           ? null
           : subItems.getLastFilter();
  }

  /**
   * Gets the data that is associated/linked with item
   *
   * @return the data that is associated/linked with item
   */
  public Object getLinkedData() {
    if (!linkedConverted) {
      if (ldDataConverter != null) {
        linkedData = fromString(Platform.getContextRootViewer(), TYPE_STRING, (String) linkedData, ldDataConverter,
                                linkedDataContext);
      }

      linkedConverted = true;
    }

    return linkedData;
  }

  /**
   * Gets the linked data context
   *
   * @return the linked data context
   */
  public Object getLinkedDataContext() {
    return linkedDataContext;
  }

  /**
   * Gets the linked data converter
   *
   * @return the linked data converter
   */
  public iDataConverter getLinkedDataConverter() {
    return ldDataConverter;
  }

  /**
   * Returns a list of public methods available for this widget.
   * The methods are separated by line feeds
   *
   * @return the list of available methods
   */
  public String getMethods() {
    StringBuilder sb = new StringBuilder();

    return getMethods(sb).toString();
  }

  /**
   *  Gets the model specific data for the item.
   *  This is data that a component's model can associate with the item
   *
   *  @return the model data
   */
  public Object getModelData() {
    return modelData;
  }

  /**
   * Gets the item's orientation
   *
   * @return the item's orientation
   */
  public Orientation getOrientation() {
    return orientation;
  }

  /**
   * Returns the item that is the parent of this item
   *
   * @return the item that is the parent of this item
   */
  public RenderableDataItem getParentItem() {
    return parentItem;
  }

  /**
   * Gets the item specific rendering component.
   * This is the component that should be used to
   * render the item
   *
   * @return the component that should be used to
   *         render the item
   */
  public iPlatformComponent getRenderingComponent() {
    if ((renderingComponent == null) && (theType == TYPE_WIDGET)) {
      iWidget w = (iWidget) getValue();

      if (w != null) {
        renderingComponent = w.getContainerComponent();
      }
    }

    float rs = UIFontHelper.getRelativeFontSize();

    if ((renderingComponent != null) && (renderingComponent.getFont() != null) && (rs != relativeFontSize)) {
      renderingComponent.updateUI();
      relativeFontSize = rs;
      // it doesn't make sense to have a font set for the item and to also have a component
      // so we can reset relativeFontSize and not worry about the item's theFont size
    }

    return renderingComponent;
  }

  @Override
  protected void finalize() throws Throwable {
    if (renderingComponent != null) {
      try {
        renderingComponent.dispose();
      } catch(Throwable ignore) {}
    }

    super.finalize();
  }

  /**
   * Gets the number of rows that this item will span
   *
   * @return the number of rows that this item will span
   */
  public int getRowSpan() {
    return rowSpan;
  }

  /**
   * Gets the context specific spanning data
   * that was set by a component that supports spanning
   *
   * @return the spanning data
   */
  public Object getSpanningData() {
    return spanData;
  }

  /**
   * Gets the internal state flags.
   *
   * @return the internal state flags.
   */
  public short getStateFlags() {
    return stateFlags;
  }

  /**
   * Gets the Swing equivalent horizontal alignment for this item
   *
   * @return the Swing equivalent horizontal alignment
   */
  public final int getSwingHorizAlignment() {
    return swingHorizAlign;
  }

  /**
   * Gets the Swing equivalent horizontal alignment
   *
   * @param alignment the alignment for which the Swing equivalent should be returned
   *
   * @return the Swing equivalent horizontal alignment
   */
  public static int getSwingHorizAlignment(HorizontalAlign alignment) {
    switch(alignment) {
      case RIGHT :
        return UIConstants.RIGHT;

      case CENTER :
        return UIConstants.CENTER;

      case TRAILING :
        return UIConstants.TRAILING;

      case LEFT :
        return UIConstants.LEFT;

      case LEADING :
      default :
        return UIConstants.LEADING;
    }
  }

  /**
   * Gets the Swing equivalent horizontal alignment for this item
   *
   * @param halign a Swing horizontal alignment to use as the default
   *
   * @return the Swing equivalent horizontal alignment. If the item's horizontal
   *          alignment is set to AUTO then the passed in alignment is returned
   */
  public final int getSwingHorizAlignment(int halign) {
    return (horizontalAlign == HorizontalAlign.AUTO)
           ? halign
           : swingHorizAlign;
  }

  /**
   * Gets the Swing equivalent vertical alignment for this item
   *
   *
   * @return the Swing equivalent vertical alignment
   */
  public final int getSwingVertAlignment() {
    return swingVertAlign;
  }

  /**
   * Gets the Swing equivalent vertical alignment for this item
   *
   * @param valign a Swing vertical alignment to use as the default
   * @return the Swing equivalent vertical alignment. If the item's vertical
   *         alignment is set to AUTO then the passed in alignment is returned
   */
  public final int getSwingVertAlignment(int valign) {
    return (verticalAlign == VerticalAlign.AUTO)
           ? valign
           : swingVertAlign;
  }

  /**
   * Gets the Swing equivalent vertical alignment
   *
   * @param alignment the alignment for which the Swing equivalent should be returned
   *
   * @return the Swing equivalent vertical alignment
   */
  public static int getSwingVertAlignment(VerticalAlign alignment) {
    switch(alignment) {
      case TOP :
        return UIConstants.TOP;

      case BOTTOM :
        return UIConstants.BOTTOM;

      case CENTER :
        return UIConstants.CENTER;

      case AUTO :
      default :
        return UIConstants.CENTER;
    }
  }

  /**
   * Gets the item's tooltip
   *
   * @return the item's tooltip
   */
  public final CharSequence getTooltip() {
    return tooltip;
  }

  /**
   * Gets the type of item
   *
   * @return the type of item
   */
  public int getType() {
    return theType;
  }

  /**
   * Gets the string name for the item's data type
   *
   * @return the string name for the item's data type
   */
  public String getTypeName() {
    return getTypeName(theType);
  }

  /**
   * Gets the name for the specified data type
   *
   * @param type the type for which to retrieve the name
   *
   * @return the name for the specified data type
   */
  public static String getTypeName(int type) {
    switch(type) {
      case TYPE_STRING :
        return "String";

      case TYPE_INTEGER :
        return "Integer";

      case TYPE_DECIMAL :
        return "Decimal";

      case TYPE_DATE :
        return "Date";

      case TYPE_DATETIME :
        return "DateTime";

      case TYPE_TIME :
        return "Time";

      case TYPE_ARRAY :
        return "Array";

      case TYPE_STRUCT :
        return "Struct";

      case TYPE_BOOLEAN :
        return "Boolean";

      case TYPE_BYTES :
        return "Bytes";

      default :
        break;
    }

    return "Unknown";
  }

  /**
   * Returns the underlying unfiltered list
   *
   * @return the underlying unfiltered list
   */
  @Override
  public List<RenderableDataItem> getUnfilteredList() {
    return this.subItemsList(0).getUnfilteredList();
  }

  /**
   * Gets the user state flags.
   *
   * @return the user state flags.
   */
  public byte getUserStateFlags() {
    return userStateFlags;
  }

  /**
   * Gets the item's value
   *
   * @return the item's value
   */
  public Object getValue() {
    return getValue(null);
  }

  /**
   * Gets the item's value
   *
   * @param context the calling context
   * @return the item's value
   */
  public Object getValue(iWidget context) {
    if (!converted) {
      if (theValue instanceof String) {
        theValue = fromString(context, theType, (String) theValue, dataConverter, valueContext);
      }

      converted = true;
    }

    return theValue;
  }

  /**
   * Gets the value context for the item
   *
   * @return the value context for the item
   */
  public Object getValueContext() {
    return valueContext;
  }

  /**
   * Gets the item's value. Unlike getValue() This method will not perform any
   * data type conversions and will not change the converted flag. The value is returned
   * as is in it's current form
   *
   * @return the item's value
   */
  public Object getValueEx() {
    return theValue;
  }

  /**
   * Gets the vertical alignment for the item
   *
   * @return the vertical alignment for the item
   */
  public VerticalAlign getVerticalAlignment() {
    return verticalAlign;
  }

  /**
   * Gets the context specific width of the item
   *
   * @return the context specific width of the item
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns whether or not the item has children
   * @return  whether or not the item has children
   */
  public boolean hasChildren() {
    return size() != 0;
  }

  /**
   * Returns whether the item has a custom property with the specified name
   *
   * @param name the name of the property
   *
   * @return true if it does; false otherwise
   */
  public boolean hasCustomProperty(String name) {
    if ((customProperties == null) && (oneProperty != null)) {
      return oneProperty.typeEquals(name);
    }

    return (customProperties == null)
           ? false
           : customProperties.containsKey(name);
  }

  /**
   * Returns whether the item has anu custom properties
   *
   * @return true if it does; false otherwise
   */
  public boolean hasCustomProperties() {
    if (customProperties != null) {
      return customProperties.size() > 0;
    }

    return (oneProperty == null)
           ? false
           : oneProperty.type != null;
  }

  /**
   * Returns whether the item spans more than one row or column
   *
   * @return true if the item spans more than one row or column; false otherwise
   */
  public final boolean hasSpan() {
    return (columnSpan > 1) || (rowSpan > 1);
  }

  public boolean hasValue() {
    return theValue != null;
  }

  /**
   * Returns whether the value has been converted
   *
   * @return true if the value has been converted; false otherwise
   */
  public boolean isConverted() {
    return converted;
  }

  /**
   * Returns whether the item supports dragging for drag and drop operations
   *
   * @return true if the item supports dragging; false otherwise
   */
  public boolean isDraggingAllowed() {
    return draggingAllowed;
  }

  /**
   * Returns whether the item is editable
   *
   * @return true if it is editable; false otherwise
   */
  public boolean isEditable() {
    return (stateFlags & ITEM_EDITABLE) > 0;
  }

  /**
   * Returns whether the item's editable flag has been set
   *
   * @return true if it has been set; false otherwise
   */
  public boolean isEditableSet() {
    return (stateFlags & ITEM_EDITABLE_SET) > 0;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns whether the item is enabled
   *
   * @return whether the item is enabled
   */
  public boolean isEnabled() {
    return (stateFlags & ITEM_DISABLED) == 0;
  }

  /**
   * Returns whether or not the list is currently filtered
   *
   * @return whether or not the list is currently filtered
   */
  @Override
  public boolean isFiltered() {
    return (subItems == null)
           ? false
           : subItems.isFiltered();
  }

  /**
   * Gets the modified flag for the item
   *
   * @return true if it was modifed; false otherwise
   */
  public boolean isModified() {
    return (stateFlags & ITEM_MODIFIED) > 0;
  }

  /**
   * Returns whether the item is selectable
   *
   * @return whether the item is enabled
   */
  public boolean isSelectable() {
    return (stateFlags & ITEM_NOT_SELECTABLE) == 0 && (stateFlags & ITEM_DISABLED) == 0;
  }

  /**
   * Returns whether the item's specified flag has been set
   *
   * @return true if it has been set; false otherwise
   */
  public boolean isStateFlagSet(short flag) {
    return (stateFlags & flag) != 0;
  }

  /**
   * Gets whether the hashCode function should return an identity hash code
   *
   * @return <CODE>true</CODE> if the hashCode function should return an
   *         identity hash code; <CODE>false</CODE> otherwise
   */
  public boolean isUseIdentityHashCode() {
    return useIdentityHashCode;
  }

  /**
   * Returns whether the item is visible
   *
   * @return whether the item is enabled
   */
  public boolean isVisible() {
    return (stateFlags & ITEM_HIDDEN) == 0;
  }

  /**
   * Converts the specified string to an object
   * @return the object the string represents or the string if there is no converter installed
   * @param value the value to convert
   */
  protected Object convert(String value) {
    return fromString(Platform.getContextRootViewer(), theType, value, dataConverter, valueContext);
  }

  /**
   * Returns an empty sub-items list with the specified capacity
   *
   * @param capacity the required list capacity
   *
   * @return an empty sub-items list with the specified capacity
   */
  protected List<RenderableDataItem> emptySubItemsList(int capacity) {
    if (subItems == null) {
      subItems = new FilterableList<RenderableDataItem>((capacity < 10)
              ? 10
              : capacity);
    } else {
      if (capacity > 1) {
        subItems.ensureCapacity(capacity);
      }

      subItems.clear();
    }

    return subItems;
  }

  /**
   * Converts the specified string value to the object it represents
   *
   * @param widget the widget context
   * @param type the type of object the string represents
   * @param value the value to be converted
   * @param cvt the data converter
   * @param context the value context
   *
   * @return the converted object
   */
  protected Object fromString(iWidget widget, int type, String value, iDataConverter cvt, Object context) {
    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    if (cvt != null) {
      return cvt.objectFromString(widget, value, context);
    } else {
      iPlatformAppContext app = widget.getAppContext();

      switch(type) {
        case TYPE_INTEGER :
        case TYPE_DECIMAL :
          return new SNumber(value, false).getNumber();

//        return Double.valueOf(new SNumber(value, false).doubleValue());
        case TYPE_DATE :
          cvt = Platform.getDataConverter(DateConverter.class);

          return cvt.objectFromString(widget, value, (context == null)
                  ? app.getDefaultDateContext()
                  : context);

        case TYPE_DATETIME :
          cvt = Platform.getDataConverter(DateTimeConverter.class);

          return cvt.objectFromString(widget, value, (context == null)
                  ? app.getDefaultDateTimeContext()
                  : context);

        case TYPE_TIME :
          cvt = Platform.getDataConverter(TimeConverter.class);

          return cvt.objectFromString(widget, value, (context == null)
                  ? app.getDefaultTimeContext()
                  : context);

        case TYPE_ARRAY :
          return new String[] { value };

        case TYPE_BOOLEAN :
          return Boolean.valueOf(SNumber.booleanValue(value));

        case TYPE_WIDGET :
          cvt = Platform.getDataConverter(WidgetConverter.class);

          return cvt.objectFromString(widget, value, (context == null)
                  ? app.getDefaultTimeContext()
                  : context);

        default :
          return value;
      }
    }
  }

  /**
   * Sets up the default data converter for the item
   * based on the item's data type
   */
  protected void setupDefaultConverter() {
    usingDefaultConverter = true;

    switch(theType) {
      case TYPE_INTEGER :
        dataConverter = Platform.getDataConverter(NumberConverter.class);

        break;

      case TYPE_BOOLEAN :
        dataConverter = Platform.getDataConverter(BooleanConverter.class);

        break;

      case TYPE_DECIMAL :
        dataConverter = Platform.getDataConverter(NumberConverter.class);

        break;

      case TYPE_DATE :
        dataConverter = Platform.getDataConverter(DateConverter.class);

        break;

      case TYPE_DATETIME :
        dataConverter = Platform.getDataConverter(DateTimeConverter.class);

        break;

      case TYPE_TIME :
        dataConverter = Platform.getDataConverter(TimeConverter.class);

        break;

      case TYPE_WIDGET :
        dataConverter = Platform.getDataConverter(WidgetConverter.class);

        break;

      default :
        dataConverter = null;

        break;
    }
  }

  /**
   * Returns an empty sub-items list with the specified capacity
   *
   * @param capacity the required list capacity
   *
   * @return an empty sub-items list with the specified capacity
   */
  protected iFilterableList<RenderableDataItem> subItemsList(int capacity) {
    if (subItems == null) {
      subItems = new FilterableList<RenderableDataItem>((capacity < 10)
              ? 10
              : capacity);
    } else {
      if (capacity > 1) {
        subItems.ensureCapacity(subItems.size() + capacity);
      }
    }

    return subItems;
  }

  /**
   * Converts the specified object to its string representation
   *  using this item's data converter and value context
   *
   * @param widget the widget to use to use to perform any needed conversions
   * @param value the value to convert
   * @param context the value context
   *
   * @return the string representation of the specified object
   */
  protected String toString(iWidget widget, Object value, Object context) {
    CharSequence s = null;

    if ((value instanceof String) || (value == null)) {
      s = (String) value;
    } else {
      iDataConverter cvt = this.dataConverter;

      if (cvt != null) {
        s = cvt.objectToString(widget, value, context);
      }
    }

    if (s == null) {
      s = (value == null)
          ? ""
          : value.toString();
    }

    return (s instanceof String)
           ? (String) s
           : s.toString();
  }

  /**
   * Sets the items sub-items list to the specified list
   *
   * @param subs the list to use for sub-items
   */
  protected void setSubItems(iFilterableList<RenderableDataItem> subs) {
    subItems = subs;
  }

  /**
   * Returns a string with a formatted list of methods for the widget.
   * This method is to support the scripting shell
   *
   * @param sb a buffer for the methods
   *
   * @return the passed in buffer
   */
  protected StringBuilder getMethods(StringBuilder sb) {
    return FunctionHelper.getMethods(this, sb);
  }
}
