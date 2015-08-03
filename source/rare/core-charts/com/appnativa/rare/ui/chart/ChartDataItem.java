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

package com.appnativa.rare.ui.chart;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.util.SubItemComparator;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

import java.util.Date;
import java.util.List;

/**
 * This class represents a chart specific data item.
 * In addition to its value (which represents the range or y-value in a plot) it
 * also holds a domain value (which represents the column or x- value in a plot)
 *
 * @author Don DeCoteau
 */
public class ChartDataItem extends RenderableDataItem {
  private int            domainType = TYPE_DECIMAL;
  private ItemType       itemType   = ItemType.POINT;
  private Object         chartLinkedData;
  private Object         domainContext;
  private boolean        domainConverted;
  private Class          domainConverterClass;
  private iDataConverter domainDataConverter;
  private Object         domainValue;
  private String         theTitle;
  private String         _domainString;
  private double         domainDouble = Double.MIN_VALUE;

  public static enum ItemType {
    SERIES, POINT, RANGE_MARKER, DOMAIN_MARKER;
  }

  /**
   * Constructs a new instance
   */
  public ChartDataItem() {
    super();
  }

  /**
   * Constructs a new instance
   * @param itemType the type of chart item
   */
  public ChartDataItem(ItemType itemType) {
    super();
    this.itemType = itemType;
  }

  /**
   * Constructs a new instance
   *
   * @param range the range value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   */
  public ChartDataItem(Object range, int type, Object data, iPlatformIcon icon) {
    super(range, type, data, icon);
  }

  /**
   * Constructs a new instance
   *
   * @param range the range value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon
   * @param c the color of the item
   * @param domain the domain value
   */
  public ChartDataItem(Object range, int type, Object data, iPlatformIcon icon, UIColor c, Object domain) {
    super(range, type, data, icon);
    this.fgColor = c;
    domainValue  = domain;
  }

  @Override
  public void clear() {
    super.clear();
    chartLinkedData = null;
    domainValue     = null;
    _domainString   = null;
    domainDouble    = Double.MIN_VALUE;
  }

  @Override
  public int compareTo(RenderableDataItem o) {
    if (!(o instanceof ChartDataItem)) {
      return 1;
    }

    return compareTo((ChartDataItem) o);
  }

  public int compareTo(ChartDataItem o) {
    if (subItems != null) {
      return super.compareTo(o);
    }

    return SubItemComparator.compareObjects(getDomainValue(), (o == null)
            ? null
            : o.getDomainValue());
  }

  @Override
  public void copy(RenderableDataItem item) {
    super.copyEx(item);

    List<RenderableDataItem> items = item.getItems();
    int                      count = (items == null)
                                     ? 0
                                     : items.size();

    if (count == 0) {
      this.clearSubItems();
    } else {
      List<RenderableDataItem> subs = this.emptySubItemsList(count);
      RenderableDataItem       di;
      ChartDataItem            ci;

      for (int i = 0; i < count; i++) {
        di = items.get(i);

        if (di instanceof ChartDataItem) {
          subs.add(di);
        } else {
          ci = new ChartDataItem();
          ci.copy(di);
          subs.add(ci);
        }
      }
    }
  }

  /**
   * Copies the contents of the specified item to this item.
   * Sub items are not copied;
   *
   * @param item the item to copy
   */
  public void copyEx(ChartDataItem item) {
    super.copyEx(item);
    _domainString        = null;
    domainValue          = item.domainValue;
    domainType           = item.domainType;;
    domainConverterClass = item.domainConverterClass;;
    domainContext        = item.domainContext;
    theTitle             = item.theTitle;
    domainConverted      = item.domainConverted;
  }

  /**
   * @param chartLinkedData the chartLinkedData to set
   */
  public void setChartLinkedData(Object chartLinkedData) {
    this.chartLinkedData = chartLinkedData;
  }

  /**
   * Sets the domain value context for the item.
   *
   * @param context the domain value context for the item
   */
  public void setDomainContext(Object context) {
    this.domainContext = context;
  }

  /**
   * Sets the domain value converter class for the item.
   *
   * @param cls the domain value converter class for the item
   */
  public void setDomainConverterClass(Class cls) {
    this.domainConverterClass = cls;
  }

  /**
   * Sets the domain value converter for the item.
   *
   * @param cvt the domain value converter for the item
   */
  public void setDomainDataConverter(iDataConverter cvt) {
    this.domainDataConverter = cvt;
  }

  /**
   * Set the item's domain information.
   *
   * @param value the domain value for the item
   * @param type the domain value type for the item
   * @param cvtclass the domain value converter class for the item
   * @param context the domain value context for the item
   */
  public void setDomainInformation(Object value, int type, Class cvtclass, Object context) {
    domainValue          = value;
    domainType           = type;
    domainConverterClass = cvtclass;
    domainContext        = context;
    _domainString        = null;
  }

  /**
   * Sets the domain value type for the item.
   *
   * @param type the domain value type for the item
   */
  public void setDomainType(int type) {
    this.domainType = type;
  }

  /**
   * Sets the domain value for the item.
   *
   * @param value the domain value for the item
   */
  public void setDomainValue(Object value) {
    this.domainValue = value;
    _domainString    = null;
  }

  /**
   * @param itemType the itemType to set
   */
  public void setItemType(ItemType itemType) {
    this.itemType = itemType;
  }

  /**
   * Sets the title for the item.
   *
   * @param title the item's title
   */
  public void setTitle(String title) {
    this.theTitle = title;
  }

  /**
   * @return the chartLinkedData
   */
  public Object getChartLinkedData() {
    return chartLinkedData;
  }

  /**
   * Gets the item's domain value context.
   *
   * @return the item's domain value context
   */
  public Object getDomainContext() {
    return domainContext;
  }

  /**
   * Gets the item's domain value converter class.
   *
   * @return the item's domain value converter class
   */
  public Class getDomainConverterClass() {
    return domainConverterClass;
  }

  /**
   * Gets the item's domain value converter.
   *
   * @return the item's domain value converter
   */
  public iDataConverter getDomainDataConverter() {
    return domainDataConverter;
  }

  public double getDomainDouble() {
    if (domainDouble == Double.MIN_VALUE) {
      Object o = getDomainValue();

      if (o instanceof Date) {
        domainDouble = ((Date) o).getTime();
      } else if (o instanceof Number) {
        domainDouble = ((Number) o).doubleValue();
      }
    }

    return domainDouble;
  }

  /**
   * Get the string representation of the item's domain value.
   *
   * @return the string representation of the item's domain value
   */
  public String getDomainString() {
    if (_domainString != null) {
      return _domainString;
    }

    Object o = getDomainValue();

    if ((o == null) || (o instanceof String)) {
      return (String) o;
    }

    if (domainDataConverter != null) {
      _domainString = domainDataConverter.objectToString(Platform.getContextRootViewer(), o, domainContext).toString();
    } else {
      _domainString = o.toString();
    }

    return _domainString;
  }

  /**
   * Gets the item's domain value type.
   *
   * @return the item's domain value type
   */
  public int getDomainType() {
    return domainType;
  }

  /**
   * Gets the item's domain value.
   *
   * @return the item's domain value
   */
  public Object getDomainValue() {
    if (!domainConverted) {
      if (domainValue instanceof String) {
        if (domainDataConverter == null) {
          domainConverterClass = (domainConverterClass == null)
                                 ? getDefaultConverterClass(domainType)
                                 : domainConverterClass;
          domainDataConverter  = Platform.getDataConverter(domainConverterClass);
        }

        domainValue = fromString(Platform.getContextRootViewer(), domainType, (String) domainValue,
                                 domainDataConverter, domainContext);
      }

      domainConverted = true;
    }

    return domainValue;
  }

  /**
   * Gets the item's domain value as a number
   *
   * @return the item's domain value as a number
   */
  public Number getDomainNumberValue() {
    Object o = getDomainValue();

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
   * @return the itemType
   */
  public ItemType getItemType() {
    return itemType;
  }

  /**
   * Get the string representation of the item's range value.
   *
   * @return the string representation of the item's range value
   */
  public String getRangeString() {
    return toString();
  }

  /**
   * Get the title for the item
   *
   * @return the item's title
   */
  public String getTitle() {
    return theTitle;
  }

  @Override
  protected Object fromString(iWidget widget, int type, String value, iDataConverter cvt, Object context) {
    if ((type == TYPE_DECIMAL) || (type == TYPE_INTEGER)) {
      if ((value == null) || (value.length() == 0)) {
        this.setConverted(true);

        return null;
      }
    }

    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    return super.fromString(widget, type, value, cvt, context);
  }
}
