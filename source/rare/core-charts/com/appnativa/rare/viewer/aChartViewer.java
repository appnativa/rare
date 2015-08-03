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

package com.appnativa.rare.viewer;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.NumberContext;
import com.appnativa.rare.converters.NumberConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.spot.Chart;
import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.spot.ItemDescription;
import com.appnativa.rare.spot.Plot;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIBorderHelper;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.chart.ChartAxis;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.ChartDefinition.ChartType;
import com.appnativa.rare.ui.chart.PlotInformation;
import com.appnativa.rare.ui.chart.aChartHandler;
import com.appnativa.rare.ui.chart.iMouseHandler;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.EmptyTextItem;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FilterableList;
import com.appnativa.util.NumberRange;
import com.appnativa.util.SNumber;
import com.appnativa.util.iFilterableList;

import java.text.ParseException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * A viewer that provides for the management and display of charts
 *
 * @author Don DeCoteau
 */
public abstract class aChartViewer extends aPlatformViewer {

  /** the chart component */
  protected iPlatformComponent chartComponent;

  /** the chart definition */
  protected ChartDefinition chartDefinition;

  /** the chart handler */
  protected aChartHandler chartHandler;
  private boolean         aggregate;

  /**
   * Constructs a new instance
   */
  public aChartViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aChartViewer(iContainer parent) {
    super(parent);
    widgetType  = WidgetType.Chart;
    columnCount = -1;
  }

  @Override
  public boolean add(RenderableDataItem e) {
    ChartDataItem di = (ChartDataItem) e;

    di.setItemType(ChartDataItem.ItemType.SERIES);

    return super.add(e);
  }

  /**
   * Adds an annotation to the chart
   *
   * @param annotation
   *          a token delimited string representing the annotation. (domain
   *          &lt;separator&gt; range &lt;separator&gt; title)
   * @param separator
   *          the token separating the annotation elements
   *
   * @return the chart item representing the added annotation
   */
  public ChartDataItem addAnnotation(String annotation, char separator) {
    CharScanner sc = perThreadScanner.get();

    sc.reset(annotation);

    String        domain = sc.nextToken(separator, true, false);
    SNumber       vnum   = new SNumber(sc.nextToken(separator, true, false)).makeImmutable();
    ChartDataItem di     = new ChartDataItem(vnum, ChartDataItem.TYPE_DECIMAL, null, null, null, domain);
    String        s      = sc.getLeftOver();

    if ((s != null) && (s.length() > 0)) {
      di.setTitle(s);
    }

    if (chartDefinition != null) {
      chartDefinition.addAnnotation(di);
    }

    return di;
  }

  /**
   * Adds an domain marker to the chart
   *
   * @param marker
   *          a token delimited string representing the marker. (lower
   *          &lt;separator&gt; upper &lt;separator&gt; title)
   * @param separator
   *          the token separating the marker elements
   *
   * @return the chart item representing the added marker
   */
  public ChartDataItem addDomainMarker(String marker, char separator) {
    CharScanner sc = perThreadScanner.get();

    sc.reset(marker);

    String        lnum = sc.nextToken(separator, true, false);
    String        unum = sc.nextToken(separator, true, false);
    ChartDataItem di   = new ChartDataItem(lnum, ChartDataItem.TYPE_DECIMAL, null, null, null, unum);
    String        s    = sc.getLeftOver();

    if ((s != null) && (s.length() > 0)) {
      di.setTitle(s);
    }

    if (chartDefinition != null) {
      chartDefinition.addDomainMarker(di);
    }

    return di;
  }

  /**
   * Adds a domain marker to the chart
   *
   * @param lower
   *          the lower bound of the mark
   * @param upper
   *          the upper bound of the mark
   * @param title
   *          the marker title
   *
   * @return the chart item representing the added marker
   */
  public ChartDataItem addDomainMarker(Object lower, Object upper, String title) {
    ChartDataItem di = new ChartDataItem(lower, ChartDataItem.TYPE_DECIMAL, null, null, null, upper);

    di.setTitle(title);

    if (chartDefinition != null) {
      chartDefinition.addDomainMarker(di);
    }

    return di;
  }

  /**
   * Adds a domain value (column) to the chart
   *
   * @param value
   *          the value to add
   * @return the item representing the added value
   */
  public RenderableDataItem addDomainValue(Object value) {
    if (chartDefinition != null) {
      RenderableDataItem di;

      if (value instanceof RenderableDataItem) {
        di = (RenderableDataItem) value;
      } else {
        di = createItem(value);
      }

      chartDefinition.addDomainValue(di);

      return di;
    }

    return null;
  }

  /**
   * Adds a domain value (column) to the chart
   *
   * @param item
   *          the item to add
   * @return the item representing the added value
   */
  public RenderableDataItem addDomainValue(RenderableDataItem item) {
    if (chartDefinition != null) {
      chartDefinition.addDomainValue(item);
    }

    return item;
  }

  @Override
  public void addParsedRow(RenderableDataItem row) {
    synchronized(widgetType) {
      if (isDisposed()) {
        return;
      }

      RenderableDataItem di;
      int                len = row.getItemCount();

      if (aggregate) {
        if (len == 0) {
          return;
        }

        di = row.getItem(0);
        row.copyEx(di);
        row.remove(0);
      }

      fixSeriesItems(row);
      add(row);
    }
  }

  /**
   * Adds an range marker to the chart
   *
   * @param marker
   *          a token delimited string representing the marker. (lower
   *          &lt;separator&gt; upper &lt;separator&gt; title)
   * @param separator
   *          the token separating the marker elements
   * @return the chart item representing the added marker
   */
  public ChartDataItem addRangeMarker(String marker, String separator) {
    return addRangeMarker(marker, separator.charAt(0));
  }

  /**
   * Adds an range marker to the chart
   *
   * @param marker
   *          a token delimited string representing the marker. (lower
   *          &lt;separator&gt; upper &lt;separator&gt; title)
   * @param separator
   *          the token separating the marker elements
   * @return the chart item representing the added marker
   */
  public ChartDataItem addRangeMarker(String marker, char separator) {
    CharScanner sc = perThreadScanner.get();

    sc.reset(marker);

    SNumber       lnum = new SNumber(sc.nextToken(separator, true, false, true)).makeImmutable();
    SNumber       unum = new SNumber(sc.nextToken(separator, true, false, true)).makeImmutable();
    ChartDataItem di   = new ChartDataItem(lnum, ChartDataItem.TYPE_DECIMAL, null, null, null, unum);
    String        s    = sc.getLeftOver();

    if ((s != null) && (s.length() > 0)) {
      di.setTitle(s.trim());
    }

    if (chartDefinition != null) {
      chartDefinition.addRangeMarker(di);
    }

    return di;
  }

  /**
   * Adds a range marker to the chart
   *
   * @param lower
   *          the lower bound of the marker
   * @param upper
   *          the upper bound of the marker
   * @param title
   *          the marker title
   *
   * @return the chart item representing the added marker
   */
  public ChartDataItem addRangeMarker(double lower, double upper, String title) {
    ChartDataItem di = new ChartDataItem(Double.valueOf(lower), ChartDataItem.TYPE_DECIMAL, null, null, null,
                         Double.valueOf(upper));

    di.setTitle(title);
    di.setDomainType(ChartDataItem.TYPE_DECIMAL);

    if (chartDefinition != null) {
      chartDefinition.addRangeMarker(di);
    }

    return di;
  }

  /**
   * Adds a series to the chart. Items within a series that have a null range
   * value or a null domain value and no corresponding value in the domain
   * values list, will be ignored
   *
   * @param series
   *          the series to add
   *
   * @return the added series
   */
  public ChartDataItem addSeries(ChartDataItem series) {
    fixSeriesItems(series);
    add(series);

    return series;
  }

  /**
   * Adds a sub-title to the chart
   *
   * @param title
   *          the sub-title
   * @return the item representing the added sub-title
   */
  public ChartDataItem addSubTitle(RenderableDataItem title) {
    ChartDataItem di;

    if (title instanceof ChartDataItem) {
      di = (ChartDataItem) title;
    } else {
      di = new ChartDataItem();
      di.copyEx(title);
    }

    if (chartDefinition != null) {
      chartDefinition.addSubtitle(di);
    }

    return di;
  }

  /**
   * Adds a sub-title to the chart
   *
   * @param title
   *          the sub-title
   *
   * @return the item representing the added sub-title
   */
  public ChartDataItem addSubTitle(String title) {
    ChartDataItem di = new ChartDataItem(title, ChartDataItem.TYPE_STRING, null, null, null, null);

    di.setDomainType(ChartDataItem.TYPE_DECIMAL);

    if (chartDefinition != null) {
      chartDefinition.addSubtitle(di);
    }

    return di;
  }

  /**
   * Clears the chart remove both domain an range values
   */
  public void clearChart() {
    if (chartDefinition != null) {
      chartDefinition.clear();
    }
  }

  /**
   * Clears the chart data removing all range values
   */
  public void clearChartData() {
    if (chartDefinition != null) {
      chartDefinition.clearData();
    }
  }

  /**
   * Clears the chart remove both domain an range values
   */
  @Override
  public void clearContents() {
    clearChart();
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((Chart) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(vcfg);
  }

  public ChartDefinition createChartDefinition(Chart cfg) {
    ChartAxis                 domainAxis;
    ChartAxis                 rangeAxis;
    ChartDefinition.ChartType chartType;
    String                    s = cfg.chartType.stringValue();

    s         = s.toUpperCase(Locale.ENGLISH);
    chartType = ChartDefinition.ChartType.valueOf(s);

    try {
      rangeAxis = createAxisInfo(true, cfg.rangeAxis);
      rangeAxis.handleAttributes(this, cfg.rangeAxis);
      domainAxis = createAxisInfo(false, cfg.domainAxis);
      domainAxis.handleAttributes(this, cfg.domainAxis);

      switch(chartType) {
        case RANGE_AREA :
        case RANGE_BAR :
          if (rangeAxis.getDomainContext() == null) {
            rangeAxis.setDomainConverterClass(NumberConverter.class);
            rangeAxis.setDomainContext(NumberContext.RANGE_CONTEXT);
          }

          break;

        default :
          break;
      }
    } catch(ParseException ex) {
      throw new ApplicationException(ex);
    }

    boolean         vertical = !cfg.horizontal.booleanValue();
    ChartDefinition cd       = new ChartDefinition(this, chartType, vertical, domainAxis, rangeAxis);

    cd.setShowLegends(cfg.showLegends.booleanValue());
    cd.setShowTooltips(cfg.showTooltips.booleanValue());
    cd.setAutoSort(cfg.autoSort.booleanValue());
    cd.setAllowZooming(cfg.zoomingAllowed.booleanValue());
    cd.setShowPlotLabels(cfg.showPlotLabels.booleanValue());

    if (cfg.bgColor.spot_valueWasSet()) {
      cd.setBackgroundColor(ColorUtils.getBackgroundColor(cfg.bgColor));
    }

    if (cfg.fgColor.spot_valueWasSet()) {
      cd.setForegroundColor(ColorUtils.getColor(cfg.fgColor.getValue()));
    }

    SPOTSet set  = DataParser.resolveSet(this, cfg.domainAxis.getSubItems(), DataItem.class);
    int     len  = (set == null)
                   ? 0
                   : set.getCount();
    int     type = domainAxis.getType();

    for (int i = 0; i < len; i++) {
      cd.addDomainValue(this.createColumn((DataItem) set.getEx(i), type));
    }

    s = cfg.dataURL.spot_getAttribute("aggregate");

    if ((s != null) && (s.length() > 0)) {
      aggregate = s.equalsIgnoreCase("true");
    }

    if (cfg.getEmptyText() != null) {
      cd.setEmptyTextItem(new EmptyTextItem(this, cfg.getEmptyText()));
    }

    cd.setPlotInformation(createPlotInfo(cfg.getPlot()));
    cd.setAnnotations(createList(cfg.getAnnotations()));
    cd.setDomainMarkers(createList(cfg.getDomainMarkers()));
    cd.setRangeMarkers(createList(cfg.getRangeMarkers()));
    cd.setSubTitles(createList(cfg.getSubTitles()));

    if (cfg.getChartTitle() != null) {
      cd.setTitle(super.createItem(cfg.getChartTitle()));
    }

    cd.setDraw3D(cfg.draw3D.booleanValue());
    cd.setMouseHandler(new iMouseHandler() {
      @Override
      public boolean wantsMouseMoveEvents() {
        aWidgetListener wl = getWidgetListener();

        return (wl != null) && wl.isChangeEventEnabled();
      }
      @Override
      public void mouseMoved(MouseEvent event, ChartDataItem item) {
        aWidgetListener wl = getWidgetListener();

        if ((wl != null) && wl.isChangeEventEnabled()) {
          wl.execute(iConstants.EVENT_CHANGE, event);
        }
      }
      @Override
      public void mouseClicked(MouseEvent event, ChartDataItem item) {
        aWidgetListener wl = getWidgetListener();

        if ((wl != null) && wl.isActionEventEnabled()) {
          wl.actionPerformed(new ActionEvent(chartComponent, event));
        }
      }
    });

    return cd;
  }

  /**
   * Creates a renderable data item for the specified value
   *
   * @param value
   *          the value to create the item for
   *
   * @return the newly created item
   */
  @Override
  public RenderableDataItem createItem(Object value) {
    if (value instanceof ChartDataItem) {
      return (ChartDataItem) value;
    }

    ChartDataItem di = new ChartDataItem();

    if (value instanceof RenderableDataItem) {
      di.copyEx((RenderableDataItem) value);
    } else if (value instanceof DataItem) {
      populateItem(this, (DataItem) value, di);
    } else if (value instanceof ItemDescription) {
      Column c = createColumn((ItemDescription) value);

      di.copyEx(c);
      di.setTitle((String) c.getColumnTitle());

      Object      val   = di.getValue();
      NumberRange range = null;

      if (val instanceof NumberRange) {
        range = (NumberRange) val;
      } else if (val instanceof String) {
        range = NumberRange.valueOf((String) val);
      }

      if (range != null) {
        di.setDomainValue(range.getLowValue());
        di.setValue(range.getHighValue());
      }
    } else {
      di.setValue(value);
    }

    di.setType(ChartDataItem.TYPE_DECIMAL);

    return di;
  }

  @Override
  public RenderableDataItem createItem(Object value, int type, Object data, Object icon, Object color) {
    String s      = (String) value;
    String domain = null;

    if (s != null) {
      int n = s.lastIndexOf(',');

      if (n != -1) {
        domain = s.substring(0, n);
        s      = s.substring(n + 1);
      }
    }

    iPlatformIcon ic = null;

    if (icon instanceof iPlatformIcon) {
      ic = (iPlatformIcon) icon;
    } else if (icon instanceof String) {
      ic = getIcon((String) icon, null);
    }

    UIColor c = null;

    if (color instanceof UIColor) {
      c = (UIColor) color;
    } else if (color instanceof String) {
      c = getColor((String) color);
    }

    return new ChartDataItem(s, RenderableDataItem.TYPE_DECIMAL, data, ic, c, domain);
  }

  @Override
  public RenderableDataItem createItemEx(DataItem item) {
    ChartDataItem ci = new ChartDataItem();

    populateItem(this, item, ci);

    String s = (String) ci.getValue();

    ci.setType(RenderableDataItem.TYPE_DECIMAL);

    String domain = null;

    if (s != null) {
      int n = s.lastIndexOf(',');

      if (n != -1) {
        domain = s.substring(0, n);
        s      = s.substring(n + 1);
        ci.setValue(s);
        ci.setDomainValue(domain);
      }
    }

    return ci;
  }

  /**
   * Creates a new renderable data item that is configured to hold the specified
   * number of sub-items
   *
   * @param capacity
   *          the initial capacity
   * @param populate
   *          whether the sub-items array should be populated
   * @return the newly created row item
   */
  @Override
  public RenderableDataItem createRow(int capacity, boolean populate) {
    ChartDataItem row = new ChartDataItem();

    row.ensureCapacity(capacity);

    if (populate) {
      for (int i = 0; i < capacity; i++) {
        ChartDataItem di = new ChartDataItem();

        row.add(di);
      }
    }

    return row;
  }

  /**
   * Creates a new series with the specified name. Use
   * {@link #createSeriesValue} to create new series values and then add then
   * add then to the series by calling the <code>add() </code> method on an item
   * returned by this function
   *
   *
   * @param name
   *          the name of the series
   * @return an item that can hold series values
   */
  public static ChartDataItem createSeries(String name) {
    ChartDataItem di = new ChartDataItem();

    di.setValue(name);
    di.setType(RenderableDataItem.TYPE_STRING);

    return di;
  }

  /**
   * Creates a series value
   *
   * @param domain
   *          the domain value. If the domain is null and the domains values
   *          have been defined then the domain value corresponding to the
   *          position of this item within its series will be used
   * @param range
   *          the range value
   *
   * @return an item
   */
  public static ChartDataItem createSeriesValue(Object domain, Object range) {
    ChartDataItem ci = new ChartDataItem();
    Object        o;

    ci.setType(RenderableDataItem.TYPE_DECIMAL);

    if (range instanceof RenderableDataItem) {
      RenderableDataItem r = (RenderableDataItem) range;

      ci.setTooltip(r.getTooltip());
      ci.setLinkedData(r.getLinkedData());
      o = r.getValue();

      if (o instanceof Number) {
        ci.setValue(o);
      } else {
        ci.setValue(range.toString());
      }
    } else {
      ci.setValue(range);
    }

    if (domain instanceof RenderableDataItem) {
      o = ((RenderableDataItem) domain).getValue();
      ci.setDomainValue(o);
    } else {
      ci.setDomainValue(domain);
    }

    return ci;
  }

  @Override
  public void dispose() {
    if (chartHandler != null) {
      chartHandler.dispose(chartComponent, chartDefinition);

      if (chartDefinition != null) {
        chartDefinition.clear();
      }
    }

    super.dispose();
    chartComponent  = null;
    chartHandler    = null;
    chartDefinition = null;
  }

  public void itemChanged(ChartDataItem item) {
    if ((chartHandler != null) && (chartDefinition != null)) {
      chartHandler.itemChanged(chartComponent, chartDefinition, item);
    }
  }

  /**
   * Gets the item at the specified location
   *
   * @param x
   *          the x position
   * @param y
   *          the y position
   *
   * @return the item at the specified location or null
   */
  public ChartDataItem itemFromLocation(int x, int y) {
    return chartHandler.itemFromLocation(chartDefinition, x, y);
  }

  public void rebuildChart() {
    if (!Platform.isUIThread()) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          refreshItems(true);
        }
      });

      return;
    }

    refreshItems(true);
  }

  /**
   * Refreshes the displayed items. Call this method after changes have been
   * made to data items
   */
  public void refreshItems() {
    if (!Platform.isUIThread()) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          refreshItems(false);
        }
      });

      return;
    }

    refreshItems(false);
  }

  /**
   * Refreshes the displayed items. Call this method after changes have been
   * made to data items
   *
   * @param rebuild
   *          true to rebuild the chart; false to just update the data points
   */
  public void refreshItems(boolean rebuild) {
    if (chartDefinition != null) {
      chartDefinition.setSeries(this.getItems());

      if (rebuild) {
        chartHandler.createChart(chartComponent, chartDefinition);
      } else {
        chartHandler.dataChanged(chartComponent, chartDefinition);
      }

      if (formComponent != chartComponent) {
        formComponent.revalidate();
        formComponent.repaint();
      }
    }
  }

  public void sortSeriesDatapoints(final boolean descending) {
    final int len = size();

    if (len < 2) {
      return;
    }

    final Comparator<RenderableDataItem> c = new Comparator<RenderableDataItem>() {
      @Override
      public int compare(RenderableDataItem o1, RenderableDataItem o2) {
        if ((o1 == null) || (o2 == null)) {
          return 0;
        }

        final int n = ((ChartDataItem) o1).compareTo((ChartDataItem) o2);

        return descending
               ? n * -1
               : n;
      }
    };

    for (int i = 0; i < len; i++) {
      get(i).sort(c);
    }
  }

  /**
   * Converts a <code>RenderableDataItem</code> to a <code>ChartDataItem</code>
   * that represents a series. The items children will make up the series
   * elements. This method is useful for converting a row from a table into a
   * chart series.
   *
   * @param item
   *          the item to convert
   * @param firstIsSeriesName
   *          true if the first element represents the series name; false
   *          otherwise
   *
   * @return the series item
   */
  public ChartDataItem toChartSeries(RenderableDataItem item, boolean firstIsSeriesName) {
    if (item instanceof ChartDataItem) {
      return (ChartDataItem) item;
    }

    ChartDataItem di  = new ChartDataItem();
    int           len = item.getItemCount();
    int           pos = 0;

    if (firstIsSeriesName) {
      if (len == 0) {
        return di;
      }

      RenderableDataItem rdi = item.getItem(0);

      di.copyEx(rdi);
      pos = 1;
    } else {
      di = new ChartDataItem();
      di.copyEx(item);
    }

    di.setType(RenderableDataItem.TYPE_STRING);
    di.setValueContext(null);
    di.setConverterClass(null);

    if (len > 0) {
      List<RenderableDataItem> items = item.getItems();

      for (int i = pos; i < len; i++) {
        di.add(createItem(items.get(i)));
      }
    }

    return di;
  }

  public void updatesCompleted() {
    if ((chartHandler != null) && (chartDefinition != null)) {
      chartHandler.updatesCompleted(chartComponent, chartDefinition);
    }
  }

  public void updatesPending() {
    if ((chartHandler != null) && (chartDefinition != null)) {
      chartHandler.updatesPending(chartComponent, chartDefinition);
    }
  }

  public void setChartDefinition(ChartDefinition cd) {
    chartDefinition = cd;
    chartComponent  = chartHandler.createChart(chartComponent, chartDefinition);
  }

  /**
   * Sets the domain label for the chart
   *
   * @param label
   *          the domain label for the chart
   */
  public void setDomainLabel(String label) {
    if (chartDefinition != null) {
      chartDefinition.getDomainAxis().setLabel(label);
    }

    if ((chartHandler != null) && (chartComponent != null)) {
      chartHandler.setDomainLabel(chartComponent, chartDefinition);
    }
  }

  /**
   * Sets the angle of domain labels for the chart
   *
   * @param angle the angle (in degrees) for domain labels
   */
  public void setDomainLabelAngel(int angle) {
    if ((chartDefinition != null) && (chartDefinition.getDomainAxis().getAngle() != angle)) {
      chartDefinition.getDomainAxis().setAngle(angle);
    }

    if (chartComponent != null) {
      chartHandler.setDomainLabelAngel(chartComponent, chartDefinition);
    }
  }

  /**
   * Sets whether domain labels are shown
   *
   * @param visible
   *          true to show labels; false otherwise
   */
  public void setDomainLabelsVisible(boolean visible) {
    if (chartDefinition != null) {
      chartDefinition.getDomainAxis().setLabelsVisible(visible);
      chartHandler.setShowDomainLabels(chartComponent, chartDefinition);
    }
  }

  public void setHorizontalScrollingEnabled(boolean enable) {
    chartHandler.setHorizontalScrollingEnabled(chartComponent, enable);
  }

  /**
   * Sets the orientation of the chart
   *
   * @param vertical
   *          true for vertical; false for horizontal
   */
  public void setOrientation(boolean vertical) {
    chartDefinition.setOrientation(vertical);
  }

  /**
   * Sets the visibility of plot values. The plot must have been initially
   * configured to show values in order for this method to have any affect
   *
   * @param visible
   *          true to show; false to hide
   */
  public void setPlotValuesVisible(boolean visible) {
    if (chartDefinition != null) {
      if (chartDefinition.isShowPlotLabels() != visible) {
        chartDefinition.setShowPlotLabels(visible);
        chartHandler.setShowPlotValues(chartComponent, chartDefinition);
      }
    }
  }

  public void setRangeBounds(Object lower, Object upper, Double increment) {
    if (chartDefinition != null) {
      chartDefinition.getRangeAxis().setBounds(lower, upper, increment);

      if (chartComponent != null) {
        chartHandler.updateRangeAxis(chartComponent, chartDefinition);
      }
    }
  }

  /**
   * Sets the range label for the chart
   *
   * @param label
   *          the range label for the chart
   */
  public void setRangeLabel(String label) {
    if (chartDefinition != null) {
      chartDefinition.getRangeAxis().setLabel(label);
    }
  }

  /**
   * Sets whether range labels are shown
   *
   * @param visible
   *          true to show labels; false otherwise
   */
  public void setRangeLabelsVisible(boolean visible) {
    if (chartDefinition != null) {
      chartDefinition.getRangeAxis().setLabelsVisible(visible);
      chartHandler.setShowRangeLabels(chartComponent, chartDefinition);
    }
  }

  public void setVerticalScrollingEnabled(boolean enable) {
    chartHandler.setVerticalScrollingEnabled(chartComponent, enable);
  }

  /**
   * Gets the chart definition object
   *
   * @return the chart definition
   */
  public ChartDefinition getChartDefinition() {
    return chartDefinition;
  }

  /**
   * Gets the domain axis object
   *
   * @return the domain axis object
   */
  public ChartAxis getDomainAxis() {
    return (chartDefinition == null)
           ? null
           : chartDefinition.getDomainAxis();
  }

  /**
   * Gets the domain value at the specified column
   *
   * @param col
   *          the domain column
   *
   * @return the domain value at the specified column or null if one does not
   *         exist
   */
  public RenderableDataItem getDomainValue(int col) {
    if (chartDefinition != null) {
      return chartDefinition.getDomainValue(col);
    }

    return null;
  }

  /**
   * Gets the range axis object
   *
   * @return the range axis object
   */
  public ChartAxis getRangeAxis() {
    return (chartDefinition == null)
           ? null
           : chartDefinition.getRangeAxis();
  }

  @Override
  public Object getSelection() {
    if (chartDefinition != null) {
      return chartDefinition.getVisitedItem();
    }

    return null;
  }

  /**
   * Gets the index of last column that was clicked on
   *
   * @return the index of last column that was clicked on
   */
  public int getSelectionColumn() {
    if (chartDefinition != null) {
      return chartDefinition.getVisitedItemIndex();
    }

    return -1;
  }

  /**
   * Gets the index of last row that was clicked on
   *
   * @return the index of last row that was clicked on
   */
  public int getSelectionRow() {
    if (chartDefinition != null) {
      return chartDefinition.getVisitedSeriesIndex();
    }

    return -1;
  }

  /**
   * Gets the visibility of plot values.
   *
   * @return true to show; false to hide
   */
  public boolean getShowPlotValues() {
    if (chartDefinition != null) {
      return chartDefinition.isShowPlotLabels();
    }

    return false;
  }

  /**
   * Returns  the size of the plot area
   * @return  the size of the plot area
   */
  public UIDimension getPlotAreaSize() {
    if (chartDefinition != null) {
      chartHandler.getPlotAreaSize(chartComponent, chartDefinition);
    }

    return getSize();
  }

  /**
   * Configures the chart
   *
   * @param cfg
   *          the chart's configuration
   */
  protected void configureEx(Chart cfg) {
    if (chartHandler == null) {
      chartHandler = createChartHandler();
    }

    chartDefinition = createChartDefinition(cfg);
    chartComponent  = chartHandler.createChart(null, chartDefinition);
    dataComponent   = formComponent = chartComponent;
    configureEx(cfg, true, false, true);
    deletingAllowed = false;
    pastingAllowed  = false;
    configureMenus(formComponent, cfg, false);

    if (cfg.font.spot_hasValue()) {
      chartHandler.setChartFont(getFont());
    }

    if (cfg.fgColor.spot_hasValue()) {
      chartHandler.setChartForeground(getForeground());
    }
    // this.configureGenericDnD(dataComponent, cfg);
  }

  /**
   * Creates axis information from a data item configuration
   *
   * @param raxis
   *          true for a range axis; false for vertical
   * @param di
   *          the data item configuration
   *
   * @return the axis information
   */
  protected ChartAxis createAxisInfo(boolean raxis, DataItem di) {
    ChartAxis ai   = new ChartAxis();
    String    ctx  = di.valueContext.getValue();
    int       type = di.valueType.intValue();

    di.valueType.setValue(DataItem.CValueType.string_type);
    di.valueContext.setValue((String) null);
    populateItem(this, di, ai);
    di.valueType.setValue(type);
    ai.setRangeAxis(raxis);

    if (raxis) {
      ai.setDomainType(RenderableDataItem.TYPE_DECIMAL);
    } else {
      ai.setDomainType(RenderableDataItem.fromSPOTType(type));
    }

    di.valueContext.setValue(ctx);

    String s = di.converterClass.getValue();

    try {
      Class cvtclass = (s != null)
                       ? getAppContext().getDataConverterClass(s)
                       : RenderableDataItem.getDefaultConverterClass(ai.getDomainType());

      if (cvtclass != null) {
        iDataConverter cvt = getAppContext().getDataConverter(cvtclass);

        if (raxis) {
          ai.setConverterClass(cvtclass);
          ai.setDataConverter(cvt);
          ai.setValueContext(cvt.createContext(this, ctx));
        } else {
          ai.setDomainConverterClass(cvtclass);
          ai.setDomainDataConverter(cvt);
          ai.setDomainContext(cvt.createContext(this, ctx));
        }
      }
    } catch(ClassNotFoundException ex) {
      getAppContext().getDefaultExceptionHandler().handleException(ex);
    }

    s = di.spot_getAttribute("label");

    if (s != null) {
      ai.setLabel(s);
    }

    return ai;
  }

  /**
   * Creates a chart handler. Defaults to calling the platform to get
   * a new chart handler
   *
   * @return a chart handler
   */
  protected aChartHandler createChartHandler() {
    Object o = Platform.getUIDefaults().get("Rare.Chart.handler");

    if (o instanceof String) {
      return (aChartHandler) Platform.createObject((String) o);
    }

    return (aChartHandler) Platform.getPlatform().createChartHandler();
  }

  /**
   * Creates a column from a data item configuration
   *
   * @param di
   *          the data item configuration
   * @param type
   *          the type of column
   *
   * @return the column
   */
  protected ChartDataItem createColumn(DataItem di, int type) {
    ChartDataItem ci = new ChartDataItem();

    populateItem(this, di, ci);
    ci.setType(type);

    return ci;
  }

  /**
   * Creates plot information from a plot configuration
   *
   * @param plot
   *          the configuration
   *
   * @return the plot information
   */
  protected PlotInformation createPlotInfo(Plot plot) {
    if (plot == null) {
      return null;
    }

    UIColor  gridColor     = null;
    UIInsets margin        = null;
    UIImage  image         = null;
    UIStroke gridStroke    = null;
    boolean  showGridLines = true;
    String   s             = plot.bgColor.getValue();

    if (s != null) {
      bgColor = getColor(s);
    }

    switch(plot.gridLine.intValue()) {
      case Plot.CGridLine.solid :
        gridStroke = UIStroke.SOLID_STROKE;

        break;

      case Plot.CGridLine.dashed :
        gridStroke = UIStroke.DASHED_STROKE;

        break;

      case Plot.CGridLine.dotted :
        gridStroke = UIStroke.DOTTED_STROKE;

        break;

      case Plot.CGridLine.none :
        showGridLines = false;

        break;

      default :
        break;
    }

    s = plot.gridLine.spot_getAttribute("color");

    if ((s != null) && (s.length() > 0)) {
      gridColor = getColor(s);
    }

    s = plot.bgImageURL.getValue();

    if (s != null) {
      image = getImage(s);
    }

    if (plot.getContentPadding() != null) {
      margin = plot.getContentPadding().createInsets();
    }

    s = plot.shapes.stringValue().toUpperCase(Locale.ENGLISH);

    PlotInformation.ShapeStyle ss = plot.shapes.spot_valueWasSet()
                                    ? PlotInformation.ShapeStyle.valueOf(s)
                                    : null;
    PlotInformation            pi = new PlotInformation(bgColor, gridColor, margin, gridStroke, image, ss);

    s = plot.borderColor.getValue();

    if ((s != null) && (s.length() > 0)) {
      pi.setBorderColor(getColor(s));
    }

    s = plot.labels.stringValue().toUpperCase(Locale.ENGLISH);
    pi.setLabelType(PlotInformation.LabelType.valueOf(s));
    pi.setLabelsFormat(plot.labels.spot_getAttribute("format"));
    s = plot.labels.spot_getAttribute("fgColor");

    if ((s != null) && (s.length() > 0)) {
      pi.setLabelsForeground(ColorUtils.getColor(s));
    }

    s = plot.labels.spot_getAttribute("bgColor");

    if ((s != null) && (s.length() > 0)) {
      pi.setLabelsBackground(ColorUtils.getColor(s));
    }

    s = plot.labels.spot_getAttribute("border");

    if ((s != null) && (s.length() > 0)) {
      pi.setLabelsBorder(UIBorderHelper.createBorder(s));
    }

    s = plot.labels.spot_getAttribute("font");

    if ((s != null) && (s.length() > 0)) {
      pi.setLabelsFont(FontUtils.parseFont(this, null, s));
    }

    pi.setShowGridLines(showGridLines);

    if (plot.fgAlpha.spot_valueWasSet()) {
      pi.setForegroundAlpha((plot.fgAlpha.getValue()) / 255f);
    }

    if (plot.lineThickness.spot_valueWasSet()) {
      pi.setLineThickness(plot.lineThickness.floatValue());
    }

    if (plot.outlineThickness.spot_valueWasSet()) {
      pi.setOutlineThickness(plot.outlineThickness.floatValue());
    }

    if (plot.shapes.spot_valueWasSet()) {
      s = plot.shapes.spot_getAttribute("outlineColor");

      if ((s != null) && (s.length() > 0)) {
        pi.setOutlineColor(ColorUtils.getColor(s));
      }

      s = plot.shapes.spot_getAttribute("fillColor");

      if ((s != null) && (s.length() > 0)) {
        pi.setFillColor(ColorUtils.getColor(s));
      }
    }

    return pi;
  }

  @Override
  protected void finishedLoadingEx() {
    if (isDisposed()) {
      return;
    }

    refreshItems();
    super.finishedLoadingEx();
  }

  @Override
  protected String getWidgetAttribute(String name) {
    if ("%rangeValue".equals(name)) {
      Object o = chartDefinition.getCurrentRangeValue();

      return (o == null)
             ? ""
             : o.toString();
    } else if ("%domainValue".equals(name)) {
      Object o = chartDefinition.getCurrentDomainValue();

      return (o == null)
             ? ""
             : o.toString();
    }

    return super.getWidgetAttribute(name);
  }

  private iFilterableList<ChartDataItem> createList(SPOTSet set) {
    int len = (set == null)
              ? 0
              : set.getCount();

    if (len == 0) {
      return null;
    }

    FilterableList<ChartDataItem> list = new FilterableList<ChartDataItem>(len);

    for (int i = 0; i < len; i++) {
      list.add((ChartDataItem) createItem(set.getEx(i)));
    }

    return list;
  }

  private void fixSeriesItems(RenderableDataItem series) {
    if ((chartDefinition == null) || series.isEmpty()) {
      return;
    }

    ChartDataItem sitem = (ChartDataItem) series;

    sitem.setType(RenderableDataItem.TYPE_STRING);
    sitem.setDomainType(chartDefinition.getDomainAxis().getAxisType());
    sitem.setDomainDataConverter(chartDefinition.getDomainAxis().getDomainDataConverter());
    sitem.setDomainContext(chartDefinition.getDomainAxis().getDomainContext());
    sitem.setDomainConverterClass(chartDefinition.getDomainAxis().getDomainConverterClass());

    iDataConverter cvt = chartDefinition.getRangeAxis().getDomainDataConverter();
    Object         vc  = chartDefinition.getRangeAxis().getDomainContext();
    ChartType      ct  = aChartHandler.getSeriesChartType(chartDefinition, (ChartDataItem) series);

    if ((ct == ChartType.RANGE_AREA) || (ct == ChartType.RANGE_BAR)) {
      vc = NumberContext.RANGE_CONTEXT;
    }

    for (RenderableDataItem di : series.getItems()) {
      ChartDataItem item = (ChartDataItem) di;

      if (item == null) {
        continue;
      }

      item.setType(chartDefinition.getRangeAxis().getAxisType());

      if (cvt != null) {
        item.setDataConverter(cvt);
      }

      item.setValueContext(vc);
      item.setDomainType(chartDefinition.getDomainAxis().getAxisType());
      item.setDomainDataConverter(chartDefinition.getDomainAxis().getDomainDataConverter());
      item.setDomainContext(chartDefinition.getDomainAxis().getDomainContext());
      item.setDomainConverterClass(chartDefinition.getDomainAxis().getDomainConverterClass());
    }
  }
}
