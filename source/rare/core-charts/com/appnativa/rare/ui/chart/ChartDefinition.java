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

import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.util.EmptyTextItem;
import com.appnativa.rare.viewer.aChartViewer;
import com.appnativa.util.FilterableList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the definition of a chart
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class ChartDefinition {

  /** set of chart annotations */
  protected List<ChartDataItem> annotations;

  /** the type of chart */
  protected ChartType chartType;

  /** the chart columns (domain values) */
  protected List<RenderableDataItem> columns;

  /** the chart viewer */
  protected aChartViewer contextWidget;

  /** the domain axis */
  protected ChartAxis domainAxis;

  /** set of domain markers */
  protected List<ChartDataItem> domainMarkers;

  /** the chart mouse handler */
  protected iMouseHandler mouseHandler;

  /** the plot information for the chart */
  protected PlotInformation plotInfo;

  /** the range axis */
  protected ChartAxis rangeAxis;

  /** a set or range markers */
  protected List<ChartDataItem> rangeMarkers;

  /** a set of series for the chart */
  protected List<RenderableDataItem> series;

  /** a set of sub-titles for the chart */
  protected List<ChartDataItem> subTitles;

  /** the chart title */
  protected RenderableDataItem title;

  /** the last column that was visited */
  protected int visitedColumn;

  /** the item that was last visited */
  protected ChartDataItem visitedItem;

  /** the last chart row that was visited */
  protected int visitedSeries;

  /** whether chart zooming is allowed */
  protected boolean     zoomingAllowed;
  private int           maxZoomFactor = 5;
  private Location      titleSide     = Location.TOP;
  private Location      legendSide    = Location.RIGHT;
  private boolean       autoSort;
  private UIColor       bgColor;
  private Object        chartHandlerInfo;
  private Object        currentDomainValue;
  private Object        currentRangeValue;
  private boolean       draw3D;
  private EmptyTextItem emptyTextItem;
  private int           endColumn;
  private UIColor       fgColor;
  private boolean       scrollWheelZoomingAllowed;
  private boolean       showLegends;
  private boolean       showToolTips;
  private int           startColumn;
  private boolean       vertical;
  private boolean       showPlotLabels;

  /**
   * Chart types
   */
  public static enum ChartType {
    LINE, BAR, STACKED_BAR, RANGE_BAR, PIE, AREA, STACKED_AREA, RANGE_AREA, STEP_AREA, STEP_LINE, BUBBLE, HI_LO,
    HI_LO_OPEN_CLOSE, CANDLESTICK, FUNNEL, SPLINE, SPLINE_AREA, POINT, ROSE, PYRAMID
  }

  /**
   * Constructs a new instance
   *
   * @param cv
   *          the chart viewer
   */
  public ChartDefinition(aChartViewer cv) {
    contextWidget = cv;
  }

  /**
   * Constructs a new instance
   *
   * @param cv
   *          the chart viewer
   * @param type
   *          the type of chart
   * @param vertical
   *          true for a vertical; false for a horizontal chart
   * @param domain
   *          the domain axis
   * @param range
   *          the range axis
   */
  public ChartDefinition(aChartViewer cv, ChartType type, boolean vertical, ChartAxis domain, ChartAxis range) {
    contextWidget   = cv;
    chartType       = type;
    this.vertical   = vertical;
    this.domainAxis = domain;
    this.rangeAxis  = range;
  }

  /**
   * Constructs a new instance
   *
   * @param cv
   *          the chart viewer
   * @param type
   *          the type of chart
   * @param vertical
   *          true for a vertical; false for a horizontal chart
   * @param domainLabel
   *          the domain axis label
   * @param rangeLabel
   *          the range axis label
   */
  public ChartDefinition(aChartViewer cv, ChartType type, boolean vertical, String domainLabel, String rangeLabel) {
    contextWidget   = cv;
    chartType       = type;
    this.vertical   = vertical;
    this.domainAxis = new ChartAxis(domainLabel, Column.TYPE_STRING, null, null);
    this.rangeAxis  = new ChartAxis(rangeLabel, Column.TYPE_DECIMAL, null, null);
  }

  /**
   * Adds an annotation to the chart
   *
   * @param annotation
   *          the annotation to add
   */
  public void addAnnotation(ChartDataItem annotation) {
    if (annotations == null) {
      annotations = new FilterableList<ChartDataItem>();
    }

    annotations.add(annotation);
  }

  /**
   * Adds a domain marker to the chart
   *
   * @param marker
   *          the marker to add
   */
  public void addDomainMarker(ChartDataItem marker) {
    if (domainMarkers == null) {
      domainMarkers = new FilterableList<ChartDataItem>();
    }

    marker.setItemType(ChartDataItem.ItemType.DOMAIN_MARKER);
    marker.setType(domainAxis.getAxisType());
    marker.setDataConverter(domainAxis.getDomainDataConverter());
    marker.setValueContext(domainAxis.getDomainContext());
    marker.setConverterClass(domainAxis.getDomainConverterClass());
    domainMarkers.add(marker);
  }

  /**
   * Adds a domain value to the chart
   *
   * @param value
   *          the value to add
   */
  public void addDomainValue(Comparable value) {
    if (value instanceof RenderableDataItem) {
      addDomainValue((RenderableDataItem) value);
    } else {
      addDomainValue(new RenderableDataItem(value));
    }
  }

  /**
   * Adds a domain value to the chart
   *
   * @param value
   *          the value to add
   */
  public void addDomainValue(RenderableDataItem value) {
    if (columns == null) {
      columns = new ArrayList<RenderableDataItem>();
    }

    value.setType(domainAxis.getAxisType());
    value.setDataConverter(domainAxis.getDomainDataConverter());
    value.setValueContext(domainAxis.getDomainContext());
    value.setConverterClass(domainAxis.getDomainConverterClass());
    columns.add(value);
  }

  /**
   * Adds a range marker to the chart
   *
   * @param marker
   *          the marker to add
   */
  public void addRangeMarker(ChartDataItem marker) {
    if (rangeMarkers == null) {
      rangeMarkers = new FilterableList<ChartDataItem>();
    }

    marker.setItemType(ChartDataItem.ItemType.RANGE_MARKER);
    marker.setType(rangeAxis.getAxisType());
    marker.setDataConverter(rangeAxis.getDomainDataConverter());
    marker.setValueContext(rangeAxis.getDomainContext());
    marker.setConverterClass(rangeAxis.getDomainConverterClass());
    rangeMarkers.add(marker);
  }

  /**
   * Adds a series to the chart
   *
   * @param series
   *          the series to add
   */
  public void addSeries(ChartDataItem series) {
    series.setItemType(ChartDataItem.ItemType.SERIES);

    if (this.series == null) {
      this.series = new FilterableList<RenderableDataItem>();
    }

    this.series.add(series);
  }

  /**
   * Adds a sub-title to the chart
   *
   * @param subtitle
   *          the sub-title to add
   */
  public void addSubtitle(ChartDataItem subtitle) {
    if (subTitles == null) {
      subTitles = new FilterableList<ChartDataItem>();
    }

    subTitles.add(subtitle);
  }

  /**
   * Invoked when an annotation is clicked on
   *
   * @param index
   *          the index position of the annotation
   * @param clickCount
   *          the mouse click count
   */
  public void annotationClicked(int index, int clickCount) {}

  /**
   * Clears the chart's data and domain values
   */
  public void clear() {
    clearData();

    if (columns != null) {
      columns.clear();
    }
  }

  /**
   * Clears the chart's data removing all series, annotations sub-titles, and
   * markers
   */
  public void clearData() {
    int len = (series == null)
              ? 0
              : series.size();

    if (len > 0) {
      for (int i = 0; i < len; i++) {
        RenderableDataItem di = series.get(i);

        di.clearSubItemParents();
        di.clear();
      }

      series.clear();
    }

    if (annotations != null) {
      annotations.clear();
    }

    if (rangeMarkers != null) {
      rangeMarkers.clear();
    }

    if (domainMarkers != null) {
      domainMarkers.clear();
    }

    if (subTitles != null) {
      subTitles.clear();
    }
  }

  /**
   * FInds the index of the series that contains the specified item
   *
   * @param item
   *          the item to look for
   * @return the index of the item's series or -1;
   */
  public int findSeriesIndex(ChartDataItem item) {
    if (series == null) {
      return -1;
    }

    List<RenderableDataItem> a   = series;
    int                      len = a.size();

    for (int i = 0; i < len; i++) {
      RenderableDataItem s = a.get(i);

      if (s.indexOf(item) != -1) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Invoked when a legend is clicked on
   *
   * @param index
   *          the index position of the legend
   * @param clickCount
   *          the mouse click count
   */
  public void legendClicked(int index, int clickCount) {}

  /**
   * Invoked when the mouse is clicked on a chart item
   *
   * @param event
   *          the mouse event
   * @param item
   *          the item that was clicked on
   */
  public void mouseClicked(MouseEvent event, ChartDataItem item) {
    if (mouseHandler != null) {
      RenderableDataItem parent = item.getParentItem();
      int                n      = -1;

      if (parent == null) {
        n = findSeriesIndex(item);
      } else {
        n = series.indexOf(parent);
      }

      if ((n != -1) && (parent == null)) {
        parent = series.get(n);
      }

      if (parent != null) {
        this.visitedSeries = n;
        visitedColumn      = parent.identityIndexOf(item);
        visitedItem        = item;
      }

      mouseHandler.mouseClicked(event, item);
    }
  }

  /**
   * Invoked when the mouse moves over chart item
   *
   * @param event
   *          the mouse event
   * @param item
   *          the item the mouse is over
   */
  public void mouseMoved(MouseEvent event, ChartDataItem item) {
    if (mouseHandler != null) {
      if (item != null) {
        setCurrentValues(item.getValue(), item.getDomainValue());
      } else {
        setCurrentValues(null, null);
      }

      mouseHandler.mouseMoved(event, item);
    }
  }

  /**
   * Returns whether chart mouse events are wanted
   *
   * @return true if chart mouse events are wanted; false otherwise
   */
  public boolean wantsMouseEvents() {
    return mouseHandler != null;
  }

  /**
   * Sets whether zooming should be allowed
   *
   * @param allow
   *          true to allow; false otherwise
   */
  public void setAllowZooming(boolean allow) {
    this.zoomingAllowed = allow;
  }

  /**
   * Sets the list of chart annotations
   *
   * @param annotations
   *          the annotations
   */
  public void setAnnotations(List<ChartDataItem> annotations) {
    this.annotations = annotations;
  }

  /**
   * Sets whether chart items should be automatically sorted
   *
   * @param sort
   *          true to have items automatically sorted; false otherwise
   */
  public void setAutoSort(boolean sort) {
    this.autoSort = sort;
  }

  /**
   * Sets the background color for the chart
   *
   * @param color
   *          the background color
   */
  public void setBackgroundColor(UIColor color) {
    this.bgColor = color;
  }

  /**
   * Invoked by the chart handler to set information specific to the chart
   * handler
   *
   * @param info
   *          the chart handler information
   */
  public void setChartHandlerInfo(Object info) {
    this.chartHandlerInfo = info;
  }

  /**
   * Sets the type of chart
   *
   * @param chartType
   *          the type of chart
   */
  public void setChartType(ChartType chartType) {
    this.chartType = chartType;
  }

  /**
   * Invoked by the chart handler to set the current values relative to the
   * current mouse position
   *
   * @param range
   *          the range value
   * @param domain
   *          the domain value
   */
  public void setCurrentValues(Object range, Object domain) {
    currentRangeValue  = range;
    currentDomainValue = domain;
  }

  /**
   * Sets the list of domain markers
   *
   * @param markers
   *          the markers
   */
  public void setDomainMarkers(List<ChartDataItem> markers) {
    this.domainMarkers = markers;
  }

  /**
   * Sets whether the chart should be drawn in 3D if the chart type supports 3D
   *
   * @param draw3D
   *          true to draw the chart in 3D; false otherwise
   */
  public void setDraw3D(boolean draw3D) {
    this.draw3D = draw3D;
  }

  /**
   * Sets the item that is displayed when there are no plot points.
   *
   * @param item
   *          the item
   */
  public void setEmptyTextItem(EmptyTextItem item) {
    this.emptyTextItem = item;
  }

  /**
   * Get the end column for series data. If not specified then the end column is
   * assumed to be the last column in the series.
   *
   * @param endColumn
   *          the ending column for series data
   */
  public void setEndColumn(int endColumn) {
    this.endColumn = endColumn;
  }

  /**
   * Sets the foreground color for the chart
   *
   * @param color
   *          the background color
   */
  public void setForegroundColor(UIColor color) {
    this.fgColor = color;
  }

  public void setLegendSide(Location legendSide) {
    this.legendSide = legendSide;
  }

  /**
   * @param maxZoomFactor
   *          the maxZoomFactor to set
   */
  public void setMaxZoomFactor(int maxZoomFactor) {
    this.maxZoomFactor = maxZoomFactor;
  }

  /**
   * Sets the mouse handler for the chart
   *
   * @param handler
   *          the handler
   */
  public void setMouseHandler(iMouseHandler handler) {
    this.mouseHandler = handler;
  }

  /**
   * Sets the orientation of the chart
   *
   * @param vertical
   *          true for vertical; false for horizontal
   */
  public void setOrientation(boolean vertical) {
    this.vertical = vertical;
  }

  /**
   * Sets the plot information for the chart
   *
   * @param plotInfo
   *          the plot information for the chart
   */
  public void setPlotInformation(PlotInformation plotInfo) {
    this.plotInfo = plotInfo;
  }

  /**
   * Sets the list of domain markers
   *
   * @param markers
   *          the markers
   */
  public void setRangeMarkers(List<ChartDataItem> markers) {
    this.rangeMarkers = markers;
  }

  /**
   * Sets whether scroll wheel zooming is allowed
   *
   * @param allowed
   *          true if it is; false otherwise
   */
  public void setScrollWheelZoomingAllowed(boolean allowed) {
    scrollWheelZoomingAllowed = allowed;
  }

  /**
   * Sets the list of series items
   *
   * @param series
   *          the list of series items
   */
  public void setSeries(List<RenderableDataItem> series) {
    this.series = series;
  }

  /**
   * Sets whether legends should be shown
   *
   * @param show
   *          true if they should be shown; false otherwise
   */
  public void setShowLegends(boolean show) {
    this.showLegends = show;
  }

  /**
   * Sets whether tooltips should be shown
   *
   * @param show
   *          true if they should be shown; false otherwise
   */
  public void setShowTooltips(boolean show) {
    this.showToolTips = show;
  }

  /**
   * Set the starting column for series data. If not specified then the starting
   * column is assumed to be the first column in the series.
   *
   * @param startColumn
   *          the starting column for series data
   */
  public void setStartColumn(int startColumn) {
    this.startColumn = startColumn;
  }

  /**
   * Sets the list of sub-titles
   *
   * @param subTitles
   *          the sub-titles
   */
  public void setSubTitles(List<ChartDataItem> subTitles) {
    this.subTitles = subTitles;
  }

  /**
   * Sets the chart's title
   *
   * @param title
   *          the title
   */
  public void setTitle(RenderableDataItem title) {
    this.title = title;
  }

  public void setTitleSide(Location titleSide) {
    this.titleSide = titleSide;
  }

  /**
   * Sets the item that was last visited by the user with the mouse
   *
   * @param series
   *          the index of the series
   * @param item
   *          the potition within the series
   *
   */
  public void setVisitedItem(int series, int item) {
    this.visitedSeries = series;
    visitedColumn      = item;

    if ((series == -1) || (item == -1)) {
      visitedItem = null;
    } else {
      visitedItem = getSeriesItem(series, item);
    }
  }

  /**
   * Gets the annotation at the specified index
   *
   * @param index
   *          the index
   *
   * @return the annotation at the specified index or null
   */
  public ChartDataItem getAnnotation(int index) {
    int len = (annotations == null)
              ? 0
              : annotations.size();

    return (index >= len)
           ? null
           : annotations.get(index);
  }

  /**
   * Gets the list of annotations
   *
   * @return the list of annotations or null
   */
  public List<ChartDataItem> getAnnotations() {
    return annotations;
  }

  /**
   * Get the chart's background color
   *
   * @return the chart's background color
   */
  public UIColor getBackgroundColor() {
    return bgColor;
  }

  /**
   * Gets the chart handler specific information
   *
   * @return the chart handler specific information
   */
  public Object getChartHandlerInfo() {
    return chartHandlerInfo;
  }

  /**
   * Gets the chart type
   *
   * @return the chart type
   */
  public ChartType getChartType() {
    return chartType;
  }

  /**
   * Gets the chart viewer for this chart
   *
   * @return the chart viewer for this chart
   */
  public aChartViewer getChartViewer() {
    return contextWidget;
  }

  /**
   * Returns the current domain value that the mouse is over
   *
   * @return the current domain value that the mouse is over
   */
  public Object getCurrentDomainValue() {
    return currentDomainValue;
  }

  /**
   * Returns the current range value that the mouse is over
   *
   * @return the current range value that the mouse is over
   */
  public Object getCurrentRangeValue() {
    return currentRangeValue;
  }

  /**
   * Gets the domain axis
   *
   * @return the domain axis
   */
  public ChartAxis getDomainAxis() {
    return domainAxis;
  }

  /**
   * Returns the label for the domain axis
   *
   * @return the label for the domain axis
   */
  public String getDomainLabel() {
    return domainAxis.getLabel();
  }

  /**
   * Gets the list of domain markers
   *
   * @return the list of domain markers
   */
  public List<ChartDataItem> getDomainMarkers() {
    return domainMarkers;
  }

  /**
   * Gets the domain value at the specified column
   *
   * @param col
   *          the column
   * @return the domain value at the specified column
   */
  public RenderableDataItem getDomainValue(int col) {
    if ((columns == null) || (columns.size() <= col)) {
      return null;
    }

    return columns.get(col);
  }

  /**
   * Gets the list of domain values
   *
   * @return the list of domain values
   */
  public List<RenderableDataItem> getDomainValues() {
    return columns;
  }

  /**
   * Gets the text that is displayed when there are no plot points.
   *
   * @return the message
   */
  public EmptyTextItem getEmptyTextItem() {
    return emptyTextItem;
  }

  /**
   * Get the end column for series data. If not specified then the end column is
   * assumed to be the last column in the series.
   *
   * @return the ending column for series data
   */
  public int getEndColumn() {
    return endColumn;
  }

  public Location getLegendSide() {
    return legendSide;
  }

  /**
   * @return the maxZoomFactor
   */
  public int getMaxZoomFactor() {
    return maxZoomFactor;
  }

  /**
   * Gets the mouse handler for the chart
   *
   * @return the mouse handler for the chart
   */
  public iMouseHandler getMouseHandler() {
    return mouseHandler;
  }

  /**
   * Gets the plot information for the chart
   *
   * @return the plot information for the chart
   */
  public PlotInformation getPlotInformation() {
    return plotInfo;
  }

  /**
   * Gets the range axis
   *
   * @return the range axis
   */
  public ChartAxis getRangeAxis() {
    return rangeAxis;
  }

  /**
   * Returns the label for the range axis
   *
   * @return the label for the range axis
   */
  public String getRangeLabel() {
    return rangeAxis.getLabel();
  }

  /**
   * Gets the set of range markers
   *
   * @return the set of range markers or null
   */
  public List<ChartDataItem> getRangeMarkers() {
    return rangeMarkers;
  }

  /**
   * Gets the list of series
   *
   * @return the list of series
   */
  public List<RenderableDataItem> getSeries() {
    return series;
  }

  /**
   * Gets the series at the specified index
   *
   *
   * @param index
   *          the index of the series to retrieve
   * @return the series at the specified index or null
   */
  public ChartDataItem getSeries(int index) {
    return ((series == null) || (index < 0) || (index >= series.size()))
           ? null
           : (ChartDataItem) series.get(index);
  }

  /**
   * Returns the series count for the chart
   *
   * @return the series count
   */
  public int getSeriesCount() {
    return (series == null)
           ? 0
           : series.size();
  }

  /**
   * Gets an item in a series
   *
   * @param series
   *          the index of the series
   * @param item
   *          the position within the series
   *
   * @return the specified series item or null
   */
  public ChartDataItem getSeriesItem(int series, int item) {
    ChartDataItem row = getSeries(series);

    return (row == null)
           ? null
           : (ChartDataItem) row.getItemEx(item);
  }

  /**
   * Get the starting column for series data. If not specified then the starting
   * column is assumed to be the first column in the series.
   *
   * @return the starting column for series data
   */
  public int getStartColumn() {
    return startColumn;
  }

  /**
   * Gets the list of sub-titles
   *
   * @return the list of sub-titles
   */
  public List<ChartDataItem> getSubTitles() {
    return subTitles;
  }

  public UIColor getTextColor(UIColor def) {
    UIColor               fg = null;
    final PlotInformation pi = plotInfo;

    if (pi != null) {
      fg = pi.getLabelsForeground();
    }

    if (fg == null) {
      fg = fgColor;
    }

    if (fg == null) {
      fg = getRangeAxis().getLabelColor();
    }

    if (fg == null) {
      fg = getRangeAxis().getForeground();
    }

    return (fg == null)
           ? def
           : fg;
  }

  public UIFont getTextFont(UIFont def) {
    UIFont                font = null;
    final PlotInformation pi   = plotInfo;

    if (pi != null) {
      font = pi.getLabelsFont();
    }

    if (font == null) {
      font = getRangeAxis().getLabelFont();
    }

    if (font == null) {
      font = getRangeAxis().getFont();
    }

    return (font == null)
           ? def
           : font;
  }

  /**
   * Gets the chart's title
   *
   * @return the chart's title
   */
  public RenderableDataItem getTitle() {
    return title;
  }

  public Location getTitleSide() {
    return titleSide;
  }

  /**
   * Gets the last item that the user visited with the mouse
   *
   * @return the last item that the user visited with the mouse
   */
  public ChartDataItem getVisitedItem() {
    return visitedItem;
  }

  /**
   * Gets the index of the last item that the user visited with the mouse
   *
   * @return the index of the last item that the user visited with the mouse
   */
  public int getVisitedItemIndex() {
    return visitedColumn;
  }

  /**
   * Gets the index of the last series that the user visited with the mouse
   *
   * @return the index of the last series that the user visited with the mouse
   */
  public int getVisitedSeriesIndex() {
    return visitedSeries;
  }

  /**
   * Returns whether zooming should be allowed
   *
   * @return true to allow; false otherwise
   */
  public boolean isAllowZooming() {
    return zoomingAllowed;
  }

  /**
   * Returns whether chart items should be automatically sorted
   *
   * @return true to automatically sort; false otherwise
   */
  public boolean isAutoSort() {
    return autoSort;
  }

  /**
   * Returns whether the chart is a category type chart
   *
   * @return true if the chart is a category chart; false otherwise
   */
  public boolean isCategoryType() {
    switch(getDomainAxis().getAxisType()) {
      case Column.TYPE_DATE :
      case Column.TYPE_DATETIME :
      case Column.TYPE_TIME :
      case Column.TYPE_INTEGER :
      case Column.TYPE_DECIMAL :
        return false;

      default :
        return true;
    }
  }

  /**
   * Returns whether the chart is a date/time chart
   *
   * @return true if the chart is a date/time chart; false otherwise
   */
  public boolean isDateTimeType() {
    switch(getDomainAxis().getAxisType()) {
      case Column.TYPE_DATE :
      case Column.TYPE_DATETIME :
      case Column.TYPE_TIME :
        return true;

      default :
        return false;
    }
  }

  /**
   * Gets whether the chart should be drawn in 3D if the chart type supports 3D
   *
   * @return true to draw the chart in 3D; false otherwise
   */
  public boolean isDraw3D() {
    return draw3D;
  }

  /**
   * Gets whether scroll wheel zooming is allowed
   *
   * @return true if it is; false otherwise
   */
  public boolean isScrollWheelZoomingAllowed() {
    return scrollWheelZoomingAllowed;
  }

  /**
   * Returns whether legends will be shown
   *
   * @return true if they will be shown; false otherwise
   */
  public boolean isShowLegends() {
    return showLegends;
  }

  /**
   * Returns whether tooltips will be shown
   *
   * @return true if they will be shown; false otherwise
   */
  public boolean isShowToolTips() {
    return showToolTips;
  }

  /**
   * Returns whether the chart is vertically oriented
   *
   * @return true for vertical; false for horizontal
   */
  public boolean isVertical() {
    return vertical;
  }

  /**
   * Gets whether plot labels should be shown
   * @return true if the will be shown; false for hidden
   */
  public boolean isShowPlotLabels() {
    return showPlotLabels;
  }

  /**
   * Sets whether plot labels should be shown
   * @param showPlotLabels true to show; false to hide
   */
  public void setShowPlotLabels(boolean showPlotLabels) {
    this.showPlotLabels = showPlotLabels;
  }
}
