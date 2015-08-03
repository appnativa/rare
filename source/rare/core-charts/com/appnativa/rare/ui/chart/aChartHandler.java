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
import com.appnativa.rare.converters.ConverterContext;
import com.appnativa.rare.converters.NumberConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.chart.ChartAxis.TimeUnit;
import com.appnativa.rare.ui.chart.ChartDefinition.ChartType;
import com.appnativa.rare.ui.chart.PlotInformation.LabelType;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.aChartViewer;
import com.appnativa.util.CharScanner;
import com.appnativa.util.NumberRange;
import com.appnativa.util.SNumber;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Abstract chart handler class
 *
 * @author Don DeCoteau
 */
public abstract class aChartHandler {
  public static final int  TYPE_DATE   = 1;
  public static final int  TYPE_NUMBER = 0;
  public static final int  TYPE_STRING = 2;
  private static UIColor[] defaultColors;
  protected UIFont         chartFont;
  protected UIColor        chartForeground;
  protected UIColor        chartBackground;
  protected UIColor        gridColor;
  protected UIColor        legendLabelColor;
  protected UIFont         legendLabelFont;
  protected UIColor        plotBackground;
  protected UIColor        plotLabelColor;
  protected UIFont         plotLabelFont;
  public static int        LABELS_PADDING = ScreenUtils.PLATFORM_PIXELS_8;

  public aChartHandler() {
    setupDefaults(false);
  }

  public void configureTitle(iActionComponent c, RenderableDataItem title) {
    c.setForeground(getTitleColor(title));
    c.setFont(getTitleFont(title));

    if (title.getBackground() != null) {
      c.setBackground(title.getBackground());
    }

    if (title.getBorder() != null) {
      c.setBorder(title.getBorder());
    }

    c.setText(title.toCharSequence());
  }

  /**
   * Creates a chart
   *
   * @param chartPanel
   *          the chart panel to rebuild
   * @param cd
   *          the chart definition
   *
   * @return a component representing the new or reconfigured chart panel
   */
  public abstract iPlatformComponent createChart(iPlatformComponent chartPanel, final ChartDefinition cd);

  /**
   * Creates label data from an axis's value
   *
   * @param cd
   *          the chart definition
   * @param domain
   *          true if the axis is a domain axis; false if it is a range axis
   * @param availableWidth
   *          the available width (or height if it is a range axis)
   * @param startValue
   *          the starting value to to use to create labels
   * @param endValue
   *          the ending value to to use to create labels
   * @param increment
   *          the increment of the axis
   *
   * @return labels for the axis
   */
  public LabelData[] createLabelData(ChartDefinition cd, boolean domain, double availableWidth, double startValue,
                                     double endValue, double increment) {
    com.appnativa.rare.ui.chart.ChartAxis ai        = domain
            ? cd.getDomainAxis()
            : cd.getRangeAxis();
    UIDimension                           size      = new UIDimension();
    UIFontMetrics                         fm        = UIFontMetrics.getMetrics(getAxisLabelFont(ai));
    int                                   textAngle = ai.getAngle();
    iDataConverter                        cvt       = ai.getDomainDataConverter();
    Object                                ctx       = ai.getDomainContext();
    boolean                               isDate    = getDataType(ai) == TYPE_DATE;
    Date                                  date      = isDate
            ? new Date()
            : null;

    if (isDate) {
      date.setTime((long) startValue);
    }

    aChartViewer cv    = cd.getChartViewer();
    int          count = (int) (Math.abs(endValue - startValue) / increment);

    if (count < 1) {
      count = 1;
    }

    double width      = 0;
    int    lineHeight = (int) fm.getHeight();
    String s;
    Object o = isDate
               ? date
               : startValue;

    if (cvt == null) {
      s = o.toString();
    } else {
      s = (String) cvt.objectToString(cv, o, ctx);
    }

    calculateTextSize(fm.stringWidth(s), lineHeight, textAngle, size);
    width += domain
             ? size.width
             : size.height;

    if (isDate) {
      date.setTime((long) endValue);
    }

    o = isDate
        ? date
        : endValue;

    if (cvt == null) {
      s = o.toString();
    } else {
      s = (String) cvt.objectToString(cv, o, ctx);
    }

    calculateTextSize(fm.stringWidth(s), lineHeight, textAngle, size);
    width += domain
             ? size.width
             : size.height;

    double midValue = increment * (count / 2) + startValue;

    if (isDate) {
      date.setTime((long) midValue);
    }

    o = isDate
        ? date
        : midValue;

    if (cvt == null) {
      s = o.toString();
    } else {
      s = (String) cvt.objectToString(cv, o, ctx);
    }

    calculateTextSize(fm.stringWidth(s), lineHeight, textAngle, size);
    width += domain
             ? size.width
             : size.height;
    width = width / 3;

    double inc           = calculateIncrement(availableWidth, width, startValue, endValue, increment);
    double startPosition = startValue;
    double val           = startPosition;

    if (count > 1000) {
      count = 5000;
    }

    List<String> list = new ArrayList<String>(count);

    list.add("");

    for (int i = 1; i < count; i++) {
      val += inc;

      if (val > endValue) {
        if (i > 1) {
          list.set(i - 1, "");
        }

        break;
      }

      if (isDate) {
        date.setTime((long) val);
      }

      o = isDate
          ? date
          : val;

      if (cvt == null) {
        s = o.toString();
      } else {
        s = (String) cvt.objectToString(cv, o, ctx);
      }

      list.add(s);
    }

    return createLabelsData(list, cv, null, null, true, fm, textAngle, startPosition, inc, false);
  }

  /**
   * Creates an array of label data objects for objects in the specified list.
   * The starting position is assumed to be zero (0) with and increment of one
   * (1)
   *
   * @param list
   *          the list of values
   * @param cv
   *          the chart viewer
   * @param cvt
   *          the data converter to use to convert the list values to strings
   * @param context
   *          the data converter context
   * @param domain
   *          true of the list represents a list of domain values; false for
   *          range values
   * @param fm
   *          the font metrics object to use to measure the labels
   * @param textAngle
   *          the angle of the of the text labels
   *
   * @return the array of label data
   */
  public LabelData[] createLabelsData(List list, aChartViewer cv, iDataConverter cvt, Object context, boolean domain,
          UIFontMetrics fm, int textAngle) {
    return createLabelsData(list, cv, cvt, context, domain, fm, textAngle, 0, 1, false);
  }

  public LabelData[] createNumericLabelsData(ChartDefinition cd, double width, double startValue, double endValue,
          double increment, boolean domain, double widthDivisor) {
    if (widthDivisor > 0) {
      increment = calculateIncrement(width, widthDivisor, startValue, endValue, increment);
    }

    ChartAxis      ai  = domain
                         ? cd.getDomainAxis()
                         : cd.getRangeAxis();
    iDataConverter cvt = domain
                         ? ai.getDomainDataConverter()
                         : ai.getDataConverter();
    Object         ctx = domain
                         ? ai.getDomainContext()
                         : ai.getValueContext();
    DecimalFormat  df  = null;

    if ((cvt == null) || ((cvt instanceof NumberConverter) && (ctx == null))) {
      cvt = null;
      df  = new DecimalFormat("#.##");
    }

    int len = (int) Math.max(1, (endValue - startValue) / increment);

    len = Math.min(1000, len);

    ArrayList list = new ArrayList();
    int       n    = 0;

    for (double d = startValue; d < endValue; d += increment) {
      list.add((df == null)
               ? d
               : df.format(d));
      n++;

      if (n == 1000) {    // just to protect against a messed up chart or bug in
        // the aiCharts causing a memory crash
        break;
      }
    }

    UIFontMetrics fm = UIFontMetrics.getMetrics(getAxisLabelFont(ai));

    return createLabelsData(list, cd.getChartViewer(), cvt, ctx, domain, fm, ai.getAngle(), startValue, increment,
                            false);
  }

  public SeriesData createSeriesData(int dataType, ChartDefinition cd, int row) {
    ChartDataItem series = cd.getSeries(row);
    LabelType     lt     = getSeriesLabelType(cd.getPlotInformation(), series);
    int           end    = cd.getEndColumn();

    if (end < 1) {
      end = series.size() - 1;
    }

    return createSeriesData(dataType, series, cd.getDomainValues(), cd.getStartColumn(), end, lt);
  }

  /**
   * Rebuilds the chart
   *
   * @param chartPanel
   *          the chart panel to rebuild
   * @param cd
   *          the chart definition
   *
   * @return a component representing the new or reconfigured chart panel
   */
  public abstract iPlatformComponent dataChanged(iPlatformComponent chartPanel, ChartDefinition cd);

  public void dispose(iPlatformComponent chartPanel, ChartDefinition cd) {}

  public void disposeChart(iPlatformComponent chartPanel) {}

  /**
   * Adjust the axis range to make sure there is a tick below the lowest value
   * and above the highest value
   *
   * @param values
   */
  public void ensurePlotFullyVisible(double[] values, double lowest, double highest) {
    double increment = values[2];
    double top       = highest + increment;

    if (top > values[1]) {
      values[1] += increment;
    }

    double bottom = lowest - increment;

    if (bottom < 0) {
      if (lowest >= .5) {
        bottom = 0;
      }
    } else if ((increment >= 2) && (int) bottom % 2 == 1) {
      bottom++;
    }

    if (bottom < values[0]) {
      values[0] = bottom;
    }
  }

  public UIColor getAxisLabelColor(ChartAxis ai) {
    UIColor c = ai.getLabelColor();

    if (c == null) {
      c = ai.getForeground();
    }

    return (c == null)
           ? chartForeground
           : c;
  }

  public UIFont getAxisLabelFont(ChartAxis ai) {
    UIFont f = ai.getLabelFont();

    if (f == null) {
      f = ai.getFont();
    }

    if (f == null) {
      f = chartFont;
    }

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    return f;
  }

  public UIColor getAxisTitleColor(ChartAxis ai) {
    return getAxisLabelColor(ai);
  }

  public UIFont getAxisTitleFont(ChartAxis ai) {
    return getAxisLabelFont(ai);
  }

  public UIFont getChartFont() {
    return chartFont;
  }

  public UIColor getChartForeground() {
    return chartForeground;
  }

  /**
   * Gets the image of the currently displayed chart
   *
   * @param chartPanel
   *          the chart panel to rebuild
   * @param cd
   *          the chart definition
   *
   * @return the image of the currently displayed chart
   */
  public abstract UIImage getChartImage(iPlatformComponent chartPanel, ChartDefinition cd);

  public iPlatformComponent getLegendComponent(iPlatformComponent chartPanel, ChartDefinition cd) {
    return null;
  }

  public abstract UIDimension getPlotAreaSize(iPlatformComponent chartPanel, ChartDefinition cd);

  public UIColor getTextColor(ChartDefinition cd) {
    return cd.getTextColor(plotLabelColor);
  }

  public UIFont getTextFont(ChartDefinition cd) {
    return cd.getTextFont(plotLabelFont);
  }

  public double getTimeInterval(com.appnativa.rare.ui.chart.ChartAxis.TimeUnit tm) {
    if (tm == null) {
      tm = com.appnativa.rare.ui.chart.ChartAxis.TimeUnit.DAY;
    }

    switch(tm) {
      case MILLISECOND :
        return 1;

      case SECOND :
        return 1000;

      case MINUTE :
        return 1000 * 60;

      case HOUR :
        return 1000 * 60 * 60;

      case DAY :
        return 1000 * 60 * 60 * 24;

      case MONTH :
        return 1000 * 60 * 60 * 24 * 30;

      case YEAR :
        return 1000 * 60 * 60 * 24 * 365;

      case WEEK :
        return 1000 * 60 * 60 * 24 * 7;

      default :
        return 1;
    }
  }

  public double getTimeInterval(double value, com.appnativa.rare.ui.chart.ChartAxis.TimeUnit tm) {
    double time = 0;

    if (value == 0) {
      value = 1;
    }

    if (tm == null) {
      tm = com.appnativa.rare.ui.chart.ChartAxis.TimeUnit.DAY;
    }

    if (tm != null) {
      switch(tm) {
        case MILLISECOND :
          time = value;

          break;

        case SECOND :
          time = value * 1000;

          break;

        case MINUTE :
          time = value * 1000 * 60;

          break;

        case HOUR :
          time = value * 1000 * 60 * 60;

          break;

        case DAY :
          time = value * 1000 * 60 * 60 * 24;

          break;

        case MONTH :
          time = value * 1000 * 60 * 60 * 24 * 30;

          break;

        case YEAR :
          time = value * 1000 * 60 * 60 * 24 * 365;

          break;

        default :
          break;
      }
    }

    return time / 1000f;
  }

  public UIColor getTitleColor(RenderableDataItem title) {
    UIColor c = title.getForeground();

    return (c == null)
           ? chartForeground
           : c;
  }

  public UIFont getTitleFont(RenderableDataItem title) {
    UIFont font = title.getFont();

    if (font == null) {
      font = chartFont;
    }

    if (font == null) {
      font = FontUtils.getDefaultFont();
    }

    return font;
  }

  public boolean isLegendSeperate() {
    return false;
  }

  public abstract void itemChanged(iPlatformComponent chartPanel, ChartDefinition cd, ChartDataItem item);

  /**
   * Finds the item at the specified location
   *
   * @param cd
   *          the chart definition
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   *
   * @return the chart item at the specified location or null
   */
  public abstract ChartDataItem itemFromLocation(ChartDefinition cd, int x, int y);

  /**
   * Recomputes axis range and tick
   *
   * Code from
   * http://stackoverflow.com/questions/4947682/intelligently-calculating
   * -chart-tick-positions and attributeds to Graphic Gems volume 1.
   *
   * @param values the values to use to recompute
   *
   */
  public void recomputeAxisRangeAndTick(double[] values) {
    double axisStart = values[0];
    double axisEnd   = values[1];
    double NumTicks  = values[2];
    double axisWidth;
    double niceRange;
    double niceTick;

    if (NumTicks < 2) {
      NumTicks = 2;
    }

    /* Check for special cases */
    axisWidth = axisEnd - axisStart;

    if (axisWidth == 0.0) {
      return;
    }

    /* Compute the new nice range and ticks */
    niceRange = niceNumber(axisWidth, false);
    niceTick  = niceNumber(niceRange / (NumTicks - 1), true);

    /* Compute the new nice start and end values */
    values[0] = Math.floor(axisStart / niceTick) * niceTick;
    values[1] = Math.ceil(axisEnd / niceTick) * niceTick;

    if (values[2] != 0) {
      values[2] = niceTick;
    }
  }

  public void setChartFont(UIFont chartFont) {
    this.chartFont     = chartFont;
    this.plotLabelFont = chartFont;
  }

  public void setChartForeground(UIColor chartForeground) {
    this.chartForeground = chartForeground;
    this.plotLabelColor  = chartForeground;
  }

  public abstract void setDomainLabel(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void setDomainLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd);

  public void setHorizontalScrollingEnabled(iPlatformComponent chartComponent, boolean enable) {}

  public abstract void setRangeLabel(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void setRangeLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void setShowDomainLabels(iPlatformComponent chartPanel, ChartDefinition cd);

  /**
   * Sets the visibility of plot values.
   * The actual value is extracted form the chart definition
   *
   * @param chartPanel
   * @param cd
   */
  public abstract void setShowPlotValues(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void setShowRangeLabels(iPlatformComponent chartPanel, ChartDefinition cd);

  public void setVerticalScrollingEnabled(iPlatformComponent chartComponent, boolean enable) {}

  /**
   * Unzooms the chart
   *
   * @param chartPanel
   *          the chart panel
   */
  public abstract void unzoom(iPlatformComponent chartPanel);

  public abstract void updateRangeAxis(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void updatesCompleted(iPlatformComponent chartPanel, ChartDefinition cd);

  public abstract void updatesPending(iPlatformComponent chartPanel, ChartDefinition cd);

  protected void calculateSeriesesRange(ChartDefinition cd, int domainType, double xrange[], double yrange[]) {
    if (xrange[0] == Double.MAX_VALUE) {
      xrange[0] = 0;
    }

    if (yrange[0] == Double.MAX_VALUE) {
      yrange[0] = 0;
    }

    double              d;
    List<ChartDataItem> markers = cd.getRangeMarkers();
    int                 len     = (markers == null)
                                  ? 0
                                  : markers.size();

    for (int i = 0; i < len; i++) {
      ChartDataItem item = markers.get(i);

      d = ((Number) item.getValue()).doubleValue();          // lower

      if (d < yrange[0]) {
        yrange[0] = d;
      }

      d = ((Number) item.getDomainValue()).doubleValue();    // upper

      if (d > yrange[1]) {
        yrange[1] = d;
      }
    }

    markers = cd.getDomainMarkers();
    len     = (markers == null)
              ? 0
              : markers.size();

    for (int i = 0; i < len; i++) {
      ChartDataItem item = markers.get(i);

      d = ((Number) item.getValue()).doubleValue();          // lower

      if (d < xrange[0]) {
        xrange[0] = d;
      }

      d = ((Number) item.getDomainValue()).doubleValue();    // upper

      if (d > yrange[1]) {
        xrange[1] = d;
      }
    }

    xrange[2] = 10;
    yrange[2] = 10;

    double xl = xrange[0];
    double xh = xrange[1];
    double yl = yrange[0];
    double yh = yrange[1];

    recomputeAxisRangeAndTick(xrange);
    recomputeAxisRangeAndTick(yrange);

    ChartAxis          ai        = cd.getRangeAxis();
    RenderableDataItem di        = ai.getLowerBounds();
    int                xrangeSet = 0;
    int                yrangeSet = 0;

    if (di != null) {
      d = di.doubleValue();

      if (d < yl) {
        yrangeSet = 1;
        yrange[0] = d;
        yl        = d;
      }
    }

    di = ai.getUpperBounds();

    if (di != null) {
      d = di.doubleValue();

      if (d > yh) {
        yrangeSet += 2;
        yrange[1] = d;
        yh        = d;
      }
    }

    double inc = ai.getIncrement();

    if (inc != 0) {
      yrange[2] = inc;
    }

    ai = cd.getDomainAxis();
    di = ai.getLowerBounds();

    if (di != null) {
      d = di.doubleValue();

      if (d < xl) {
        xrange[0] = d;
        xrangeSet = 1;
      }
    }

    di = ai.getUpperBounds();

    if (di != null) {
      d = di.doubleValue();

      if (d < xh) {
        xrange[1] = d;
        xrangeSet += 2;
      }
    }

    inc = ai.getIncrement();

    if (domainType == TYPE_DATE) {
      if (inc == 0) {
        inc = 1;
      }

      TimeUnit tu = ai.getTimeUnit();

      if ((tu != null) && (tu != TimeUnit.NONE)) {
        inc *= getTimeInterval(tu);
      }
    } else if (inc == 0) {
      inc = 1;
    }

    if (inc != 0) {
      xrange[2] = inc;
    }

    if (yrangeSet != 3) {
      double l = yrange[0];
      double h = yrange[1];

      ensurePlotFullyVisible(yrange, yl, yh);

      if (yrangeSet == 1) {
        yrange[0] = l;
      } else if (yrangeSet == 2) {
        yrange[1] = h;
      }
    }

    if (xrangeSet != 3) {
      double l = xrange[0];
      double h = xrange[1];

      ensurePlotFullyVisible(xrange, xl, xh);

      if (xrangeSet == 1) {
        xrange[0] = l;
      } else if (xrangeSet == 2) {
        xrange[1] = h;
      }
    }
  }

  protected UIColor getGridColor(PlotInformation plot) {
    UIColor c = (plot == null)
                ? null
                : plot.getGridColor();

    return (c == null)
           ? gridColor
           : c;
  }

  protected UIStroke getGridStroke(PlotInformation plot) {
    UIStroke stroke = (plot == null)
                      ? null
                      : plot.getGridStroke();

    return (stroke == null)
           ? UIStroke.HALF_DOTTED_STROKE
           : stroke;
  }

  protected void setupDefaults(boolean allowNull) {
    chartForeground = Platform.getUIDefaults().getColor("Rare.Chart.foreground");

    if ((chartForeground == null) &&!allowNull) {
      chartForeground = ColorUtils.getForeground();
    }

    chartBackground = Platform.getUIDefaults().getColor("Rare.Chart.background");

    if ((chartBackground == null) &&!allowNull) {
      chartBackground = ColorUtils.getBackground();
    }

    chartFont = Platform.getUIDefaults().getFont("Rare.Chart.font");

    if ((chartFont == null) &&!allowNull) {
      chartFont = UIFontHelper.getDefaultFont();
    }

    gridColor = Platform.getUIDefaults().getColor("Rare.Chart.gridColor");

    if ((gridColor == null) &&!allowNull) {
      gridColor = UIColor.LIGHTGRAY;
    }

    plotBackground = Platform.getUIDefaults().getColor("Rare.Chart.plotBackground");
    plotLabelColor = Platform.getUIDefaults().getColor("Rare.Chart.plotLabelColor");

    if ((plotLabelColor == null) &&!allowNull) {
      plotLabelColor = chartForeground;
    }

    plotLabelFont = Platform.getUIDefaults().getFont("Rare.Chart.plotLabelFont");

    if ((plotLabelFont == null) &&!allowNull) {
      plotLabelFont = chartFont;
    }

    legendLabelFont = Platform.getUIDefaults().getFont("Rare.Chart.legendLabelFont");

    if ((legendLabelFont == null) &&!allowNull) {
      legendLabelFont = plotLabelFont;
    }

    legendLabelColor = Platform.getUIDefaults().getColor("Rare.Chart.legendLabelColor");

    if ((legendLabelColor == null) &&!allowNull) {
      legendLabelColor = plotLabelColor;
    }
  }

  /**
   * Calculates the increment to use to layout labels given the available width
   * and the tick width
   *
   * @param availableWidth
   *          the available width (or height if it is a range axis)
   * @param tickWidth
   *          the width that a tick will take up
   * @param startValue
   *          the starting value to to use to create labels
   * @param endValue
   *          the ending value to to use to create labels
   * @param increment
   *          the increment of the axis
   * @return the layout increment
   */
  public static double calculateIncrement(double availableWidth, double tickWidth, double startValue, double endValue,
          double increment) {
    int count = (int) (Math.abs(endValue - startValue) / increment);

    count = (int) Math.min(availableWidth / tickWidth, count);

    if (count < 1) {
      count = 1;
    }

    double inc = Math.abs(endValue - startValue) / count;

    inc /= increment;

    int ratio = (int) Math.max(inc, 1);

    inc = ratio * increment;

    return inc;
  }

  /**
   * Calculates the range bounds for the specified series
   *
   * @param series
   *          the series
   * @param xrange
   *          the output array for the x-axis (use null if you don't this axis's
   *          range calculated)
   * @param yrange
   *          the output array for the y-axis (use null if you don't this axis's
   *          range calculated)
   * @param start
   *          the starting position of the series item to use for the chart
   * @param end
   *          the last position of the series to use for the chart
   * @param numberRange
   *          true if the y-axis represents number ranges; false otherwise
   */
  public static void calculateRangeBounds(ChartDataItem series, double[] xrange, double[] yrange, int start, int end,
          boolean numberRange) {
    if ((xrange == null) && (yrange == null)) {
      return;
    }

    double xmin = (xrange == null)
                  ? 0
                  : xrange[0];
    double xmax = (xrange == null)
                  ? 0
                  : xrange[1];
    double ymin = (yrange == null)
                  ? 0
                  : yrange[0];
    double ymax = (yrange == null)
                  ? 0
                  : yrange[1];
    double d;

    for (int i = start; i < end; i++) {
      ChartDataItem di = (ChartDataItem) series.getItemEx(i);

      if (numberRange) {
        NumberRange r = (NumberRange) di.getValue();

        d    = r.getLowValue().doubleValue();
        ymin = Math.min(d, ymin);
        ymax = Math.max(d, ymax);
        d    = r.getHighValue().doubleValue();
        ymin = Math.min(d, ymin);
        ymax = Math.max(d, ymax);
      } else {
        d    = di.doubleValue();
        ymin = Math.min(d, ymin);
        ymax = Math.max(d, ymax);
      }

      if (xrange != null) {
        d    = di.getDomainDouble();
        xmin = Math.min(d, xmin);
        xmax = Math.max(d, xmax);
      }
    }

    if (xrange != null) {
      xrange[0] = xmin;
      xrange[1] = xmax;
    }

    if (yrange != null) {
      yrange[0] = ymin;
      yrange[1] = ymax;
    }
  }

  /**
   * Calculates to the size of text that will be displayed at a given angle
   *
   * @param textWidth
   *          the width of the text
   * @param lineHeight
   *          the line height of the text
   * @param textAngle
   *          the angle the text will be displayed at (assumed not to be 0, 180,
   *          -90, 90 or 270)
   * @param size
   *          the object that will hold the size
   */
  public static void calculateTextSize(float textWidth, float lineHeight, float textAngle, UIDimension size) {
    lineHeight  += (float) (Math.sin(textAngle) * textWidth);
    textWidth   = (float) (Math.cos(textAngle) * textWidth);
    size.width  = (int) Math.ceil(textWidth);
    size.height = (int) Math.ceil(lineHeight);
  }

  /**
   * Calculates to the size of text that will be displayed at a given angle
   *
   * @param text
   *          the text to measure
   * @param fm
   *          the font metrics object to use to measure the text
   * @param textAngle
   *          the angle the text will be displayed at
   * @param size
   *          the object that will hold the size
   */
  public static void calculateTextSize(String text, UIFontMetrics fm, int textAngle, UIDimension size) {
    float angle      = 0;
    int   lineHeight = (int) Math.ceil(fm.getHeight());

    if (textAngle < 0) {
      textAngle = 360 + textAngle;
    }

    boolean has90Degree = (textAngle == 90) || (textAngle == -90) || (textAngle == 270);
    boolean hasAngle    = (textAngle != 0) && (textAngle != 360) && (textAngle != 180) &&!has90Degree;

    if (hasAngle &&!has90Degree) {
      angle = (float) Math.toRadians(textAngle);
    }

    int textWidth = fm.stringWidth(text);

    if (hasAngle) {
      calculateTextSize(textWidth, lineHeight, angle, size);
    } else if (has90Degree) {
      size.width  = lineHeight;
      size.height = textWidth;
    } else {
      size.width  = textWidth;
      size.height = lineHeight;
    }
  }

  /**
   * Creates an array of label data objects for objects in the specified list
   *
   * @param list
   *          the list of values
   * @param cv
   *          the chart viewer
   * @param cvt
   *          the data converter to use to convert the list values to strings
   * @param context
   *          the data converter context
   * @param domain
   *          true of the list represents a list of domain values; false for
   *          range values
   * @param fm
   *          the font metrics object to use to measure the labels
   * @param textAngle
   *          the angle of the of the text labels
   * @param startPosition
   *          the startPosition of the axis values that the first label will
   *          represent
   * @param increment
   *          the increment to use the position each subsequent label after the
   *          starting position
   * @param padEnds
   *          true to add a blank label before and after the other labels; false
   *          otherwise
   *
   * @return the array of label data
   */
  public static LabelData[] createLabelsData(List list, aChartViewer cv, iDataConverter cvt, Object context,
          boolean domain, UIFontMetrics fm, int textAngle, double startPosition, double increment, boolean padEnds) {
    int         len    = list.size();
    LabelData[] labels = new LabelData[padEnds
                                       ? len + 2
                                       : len];
    String      s;
    float       angle      = 0;
    int         lineHeight = (int) Math.ceil(fm.getHeight());
    UIDimension size       = new UIDimension();

    if (textAngle < 0) {
      textAngle = 360 + textAngle;
    }

    boolean has90Degree = (textAngle == 90) || (textAngle == -90) || (textAngle == 270);
    boolean hasAngle    = (textAngle != 0) && (textAngle != 360) && (textAngle != 180) &&!has90Degree;

    if (hasAngle &&!has90Degree) {
      angle = (float) Math.toRadians(textAngle);
    }

    int n = padEnds
            ? 1
            : 0;

    for (int i = 0; i < len; i++) {
      if (cvt != null) {
        s = cvt.objectToString(cv, list.get(i), context).toString();
      } else {
        s = list.get(i).toString();
      }

      int textWidth = fm.stringWidth(s);

      if (hasAngle) {
        calculateTextSize(textWidth, lineHeight, angle, size);
      } else if (has90Degree) {
        size.width  = lineHeight;
        size.height = textWidth;
      } else {
        size.width  = textWidth;
        size.height = lineHeight;
      }

      labels[n]          = new LabelData(s, size.width, size.height);
      labels[n].position = startPosition + (i * increment);
      n++;
    }

    if (padEnds) {
      labels[0]                = new LabelData("", 0, 0);
      labels[0].position       = startPosition - increment;
      labels[len + 1]          = new LabelData("", 0, 0);
      labels[len + 1].position = labels[len].position + increment;
    }

    return labels;
  }

  /**
   * Creates a series data object for the specified series
   *
   * @param dataType
   *          the the of data of the chart's domain
   * @param series
   *          the chart series
   * @param domainValues
   *          domain values associated with the data points (can be null)
   * @param start
   *          the starting position of the series item to use for the chart
   * @param end
   *          the last position of the series to use for the chart
   * @param lt
   *          the label type for data points
   *
   * @return the created series data or null if one was not created
   */
  public static SeriesData createSeriesData(int dataType, ChartDataItem series, List<RenderableDataItem> domainValues,
          int start, int end, LabelType lt) {
    end++;

    int len = end - start;

    if (len <= 0) {
      return null;
    }

    SeriesData data = new SeriesData(series.getDomainString(), len, lt);
    UIColor    c    = getSeriesFillColor(series);

    data.fillColor = c;
    c              = getSeriesOutlineColor(series);

    if (c != null) {
      data.outlineColor = c;
    }

    ChartDataItem di;
    int           domainIndex = 0;
    Number        range;
    Object        domain;
    String        label;
    int           clen = (domainValues == null)
                         ? 0
                         : domainValues.size();

    for (int i = start; i < end; i++) {
      di = (ChartDataItem) series.getItemEx(i);

      if (di != null) {
        range  = di.numberValue();
        domain = null;

        if (domainIndex < clen) {
          RenderableDataItem column = domainValues.get(domainIndex);

          if (column != null) {
            switch(dataType) {
              case TYPE_NUMBER :
                domain = column.doubleValue();

                break;

              case TYPE_DATE :
                domain = column.getValue();

                break;

              default :
                domain = column.toString();

                break;
            }
          }
        }

        domainIndex++;

        if (domain == null) {
          switch(dataType) {
            case TYPE_NUMBER :
              domain = di.getDomainNumberValue();

              break;

            case TYPE_DATE :
              domain = di.getDomainValue();

              break;

            default :
              domain = di.getDomainString();

              break;
          }
        }

        if ((domain != null) && (range != null)) {
          label = null;

          if (lt != null) {
            switch(lt) {
              case VALUES :
                label = di.toString();

                break;

              case LINKED_DATA :
                if (di.getLinkedData() != null) {
                  label = di.getLinkedData().toString();
                }

                break;

              case TOOLTIPS :
                if (di.getTooltip() != null) {
                  label = di.getTooltip().toString();
                }

                break;

              default :
                break;
            }
          }

          data.addValue(di, (Comparable) domain, range, label);
        }
      }
    }

    return data;
  }

  public static int getDataType(com.appnativa.rare.ui.chart.ChartAxis axis) {
    switch(axis.getDomainType()) {
      case Column.TYPE_DATE :
      case Column.TYPE_DATETIME :
      case Column.TYPE_TIME :
        return TYPE_DATE;

      case Column.TYPE_INTEGER :
      case Column.TYPE_DECIMAL :
        return TYPE_NUMBER;

      default :
        return TYPE_STRING;
    }
  }

  public static UIColor getDefaultColor(int index) {
    if (defaultColors == null) {
      defaultColors = createDefaultColors();
    }

    index = index % defaultColors.length;

    return defaultColors[index];
  }

  /**
   * Calculates the modulus to use for positioning labels. Given the array of
   * data and a pixel with of the area that the labels will be displayed in, a
   * modulus is calculated that can be used to decide which labels to show.
   *
   * @param list
   *          the list of labels
   * @param width
   *          the width of the display area
   * @param pad
   *          the padding between labels
   *
   * @return the modulus to use to decide which labels to show
   */
  public static int getLabelsMod(LabelData[] list, float width, float pad) {
    int len = list.length;
    int w   = 0;

    for (int i = 0; i < len; i++) {
      w += list[i].width + pad;
    }

    if (w <= width) {
      return 0;
    }

    int mod = 1;

    while((w > width) && (mod < len)) {
      mod++;
      w = getLabelsWidth(list, mod, pad);
    }

    return mod;
  }

  public static ChartDefinition.ChartType getSeriesChartType(ChartDefinition cd, ChartDataItem series) {
    Object           o   = series.getValueContext();
    ConverterContext ctx = null;

    if (o instanceof ConverterContext) {
      ctx = (ConverterContext) o;
      o   = ctx.getUserObject();
    }

    if (o instanceof ChartDefinition.ChartType) {
      return (ChartDefinition.ChartType) o;
    }

    if (ctx != null) {
      o = ctx.getName();
    }

    if (o instanceof String) {
      try {
        ChartDefinition.ChartType ct = ChartDefinition.ChartType.valueOf(((String) o).toUpperCase(Locale.US));

        if (ctx != null) {
          ctx.setUserObject(ct);
        }

        return ct;
      } catch(Exception ignore) {}
    }

    return cd.getChartType();
  }

  public static UIColor getSeriesFillColor(ChartDataItem series) {
    Object o = series.getCustomProperty("plot.shapes.fillColor");

    if (o instanceof UIColor) {
      return (UIColor) o;
    }

    if (o instanceof String) {
      return ColorUtils.getColor((String) o);
    }

    return null;
  }

  public static PlotInformation.LabelType getSeriesLabelType(PlotInformation pi, ChartDataItem series) {
    Object o = series.getCustomProperty("plot.labels");

    if (o instanceof PlotInformation.LabelType) {
      return (PlotInformation.LabelType) o;
    }

    if (o instanceof String) {
      try {
        return PlotInformation.LabelType.valueOf(((String) o).toUpperCase(Locale.US));
      } catch(Exception ignore) {}
    }

    return (pi == null)
           ? PlotInformation.LabelType.VALUES
           : pi.getLabelType();
  }

  public static float getSeriesLineThickness(ChartDataItem series, float def) {
    Object o = series.getCustomProperty("plot.lineThickness");

    if (o instanceof Number) {
      return ((Number) o).floatValue();
    }

    if (o instanceof String) {
      try {
        return SNumber.floatValue((String) o);
      } catch(Exception ignore) {}
    }

    return def;
  }

  public static UIColor getSeriesOutlineColor(ChartDataItem series) {
    Object o = series.getCustomProperty("plot.shapes.outlineColor");

    if (o instanceof UIColor) {
      return (UIColor) o;
    }

    if (o instanceof String) {
      return ColorUtils.getColor((String) o);
    }

    UIColor bg = series.getBackground();

    if (bg != null) {
      return bg;
    }

    iPlatformComponentPainter cp = series.getComponentPainter();

    return (cp == null)
           ? null
           : cp.getBackgroundColor();
  }

  public static float getSeriesOutlineLineThickness(ChartDataItem series, float def) {
    Object o = series.getCustomProperty("plot.outlineThickness");

    if (o instanceof Number) {
      return ((Number) o).floatValue();
    }

    if (o instanceof String) {
      try {
        return SNumber.floatValue((String) o);
      } catch(Exception ignore) {}
    }

    return def;
  }

  public static PlotInformation.ShapeStyle getSeriesShapeStyle(PlotInformation pi, ChartDataItem series) {
    Object o = series.getCustomProperty("plot.shapes");

    if (o instanceof PlotInformation.ShapeStyle) {
      return (PlotInformation.ShapeStyle) o;
    }

    if (o instanceof String) {
      try {
        return PlotInformation.ShapeStyle.valueOf(((String) o).toUpperCase(Locale.US));
      } catch(Exception ignore) {}
    }

    return (pi == null)
           ? PlotInformation.ShapeStyle.FILLED
           : pi.getShapeStyle();
  }

  /**
   * Re-measures the specified labels
   *
   * @param labels
   *          the labels to measure
   * @param fm
   *          the font metrics to use toe measure them
   * @param textAngle
   *          the text angle
   */
  public static void remeasureLabels(LabelData[] labels, UIFontMetrics fm, int textAngle) {
    float       angle      = 0;
    int         lineHeight = (int) Math.ceil(fm.getHeight());
    UIDimension size       = new UIDimension();

    if (textAngle < 0) {
      textAngle = 360 + textAngle;
    }

    boolean has90Degree = (textAngle == 90) || (textAngle == -90) || (textAngle == 270);
    boolean hasAngle    = (textAngle != 0) && (textAngle != 360) && (textAngle != 180) &&!has90Degree;

    if (hasAngle &&!has90Degree) {
      angle = (float) Math.toRadians(textAngle);
    }

    int len = labels.length;

    for (int i = 0; i < len; i++) {
      LabelData label     = labels[i];
      int       textWidth = fm.stringWidth(label.label);

      if (hasAngle) {
        calculateTextSize(textWidth, lineHeight, angle, size);
      } else if (has90Degree) {
        size.width  = lineHeight;
        size.height = textWidth;
      } else {
        size.width  = textWidth;
        size.height = lineHeight;
      }

      label.width  = size.width;
      label.height = size.height;
    }
  }

  protected static UIColor[] createDefaultColors() {
    String colors = Platform.getUIDefaults().getString("Rare.Chart.defaultColors");

    if ((colors != null) && (colors.indexOf(',') != -1)) {
      List<String> list = CharScanner.getTokens(colors, ',', true);
      final int    len  = list.size();
      UIColor      a[]  = new UIColor[len];

      for (int i = 0; i < len; i++) {
        a[i] = UIColorHelper.getColor(list.get(i));
      }

      return a;
    } else {
      UIColor a[] = new UIColor[8];

      a[0] = UIColor.RED;
      a[1] = UIColor.BLUE;
      a[2] = UIColor.GREEN;
      a[3] = UIColor.YELLOW;
      a[4] = UIColor.MAGENTA;
      a[5] = UIColor.ORANGE;
      a[6] = UIColor.CYAN;
      a[7] = UIColor.PINK;

      // a[0]=new UIColor(0xfff9d900);
      // a[1]=new UIColor(0xffa9e200);
      // a[2]=new UIColor(0xff22bad9);
      // a[3]=new UIColor(0xff0181e2);
      // a[4]=new UIColor(0xff2f357f);
      // a[5]=new UIColor(0xff860061);
      // a[6]=new UIColor(0xffc62b00);
      // a[7]=new UIColor(0xffff5700);
      return a;
    }
  }

  protected static int getCalendarField(ChartAxis.TimeUnit tu) {
    int field;

    switch(tu) {
      case MILLISECOND :
        field = Calendar.MILLISECOND;

        break;

      case SECOND :
        field = Calendar.SECOND;

        break;

      case MINUTE :
        field = Calendar.MINUTE;

        break;

      case HOUR :
        field = Calendar.HOUR;

        break;

      case WEEK :
        field = Calendar.WEEK_OF_YEAR;

        break;

      case MONTH :
        field = Calendar.MONTH;

        break;

      case YEAR :
        field = Calendar.YEAR;

        break;

      default :
        field = Calendar.DAY_OF_YEAR;

        break;
    }

    return field;
  }

  static int getLabelsWidth(LabelData[] list, int mod, float pad) {
    int len = list.length;
    int w   = 0;

    for (int i = 0; i < len; i++) {
      if (i % mod == 0) {
        w += list[i].width + pad;
      }
    }

    return w;
  }

  /**
   * Code from
   * http://stackoverflow.com/questions/4947682/intelligently-calculating
   * -chart-tick-positions and attributeds to Graphic Gems volume 1.
   */
  static double niceNumber(double Value, boolean Round) {
    int    exponent;
    double fraction;
    double niceFraction;

    exponent = (int) Math.floor(Math.log10(Value));
    fraction = Value / Math.pow(10, exponent);

    if (Round) {
      if (fraction < 1.5) {
        niceFraction = 1.0;
      } else if (fraction < 3.0) {
        niceFraction = 2.0;
      } else if (fraction < 7.0) {
        niceFraction = 5.0;
      } else {
        niceFraction = 10.0;
      }
    } else {
      if (fraction <= 1.0) {
        niceFraction = 1.0;
      } else if (fraction <= 2.0) {
        niceFraction = 2.0;
      } else if (fraction <= 5.0) {
        niceFraction = 5.0;
      } else {
        niceFraction = 10.0;
      }
    }

    return niceFraction * Math.pow(10, exponent);
  }

  public static class LabelData implements Comparable {
    public String label;
    public float  width;
    public float  height;
    public double position;

    public LabelData(String label, float width, float height) {
      this.label  = label;
      this.width  = width;
      this.height = height;
    }

    @Override
    public int compareTo(Object o) {
      return label.compareTo(((LabelData) o).label);
    }

    public String toString() {
      return label;
    }
  }


  public static class NoChartHandler extends aChartHandler {
    public NoChartHandler() {}

    @Override
    public iPlatformComponent createChart(iPlatformComponent chartPanel, ChartDefinition cd) {
      return getErrorPanel(chartPanel);
    }

    @Override
    public iPlatformComponent dataChanged(iPlatformComponent chartPanel, ChartDefinition cd) {
      return getErrorPanel(chartPanel);
    }

    @Override
    public UIImage getChartImage(iPlatformComponent chartPanel, ChartDefinition cd) {
      return null;
    }

    @Override
    public UIDimension getPlotAreaSize(iPlatformComponent chartPanel, ChartDefinition cd) {
      return new UIDimension();
    }

    @Override
    public void itemChanged(iPlatformComponent chartPanel, ChartDefinition cd, ChartDataItem item) {}

    @Override
    public ChartDataItem itemFromLocation(ChartDefinition cd, int x, int y) {
      return null;
    }

    @Override
    public void setChartForeground(UIColor chartForeground) {}

    @Override
    public void setDomainLabel(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void setDomainLabelAngel(iPlatformComponent chartComponent, ChartDefinition cd) {}

    @Override
    public void setRangeLabel(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void setRangeLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void setShowDomainLabels(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void setShowPlotValues(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void setShowRangeLabels(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void unzoom(iPlatformComponent chartPanel) {}

    @Override
    public void updateRangeAxis(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void updatesCompleted(iPlatformComponent chartPanel, ChartDefinition cd) {}

    @Override
    public void updatesPending(iPlatformComponent chartPanel, ChartDefinition cd) {}

    private iPlatformComponent getErrorPanel(iPlatformComponent chartPanel) {
      if (chartPanel == null) {
        chartPanel = new CartesianPanel();
      }

      if (chartFont != null) {
        chartPanel.setFont(chartFont);
      }

      return chartPanel;
    }
  }


  public static class SeriesData {
    public List<Number>        rangeValues;
    public List<Comparable>    domainValues;
    public boolean             showPointLabels = true;
    public List<ChartDataItem> dataItems;
    public HashSet             domainMap;
    public String              legend;
    public Object              linkedData;
    public boolean             isNumberRange;
    public ChartType           chartType;
    public int                 seriesIndex;
    public int                 dataIndex;
    public LabelType           labelType = LabelType.VALUES;
    public UIColor             outlineColor;
    public UIColor             fillColor;
    private double             pieTotal;
    private NumberFormat       percentFormat;
    private NumberFormat       totalFormat;

    public SeriesData(String legend, int size, LabelType lt) {
      rangeValues    = new ArrayList<Number>(size);
      domainValues   = new ArrayList<Comparable>(size);
      dataItems      = new ArrayList<ChartDataItem>(size);
      this.legend    = legend;
      domainMap      = new HashSet();
      this.labelType = (lt == null)
                       ? LabelType.VALUES
                       : lt;
    }

    public SeriesData(String legend, int size, LabelType lt, List<Comparable> domainValues, HashSet domains) {
      rangeValues       = new ArrayList<Number>(size);
      dataItems         = new ArrayList<ChartDataItem>(size);
      this.domainValues = domainValues;
      this.legend       = legend;
      this.domainMap    = domains;
      this.labelType    = (lt == null)
                          ? LabelType.VALUES
                          : lt;
    }

    public void addDomainValues(List<Comparable> values) {
      for (Comparable c : values) {
        if (!domainMap.contains(c)) {
          domainValues.add(c);
        }
      }
    }

    public int addValue(ChartDataItem item, Comparable domain, Number range, String label) {
      if (domainMap.add(domain)) {
        dataItems.add(item);
        rangeValues.add(range);
        domainValues.add(domain);

        return rangeValues.size() - 1;
      }

      return -1;
    }

    public void clearValues(boolean rangeOnly) {
      if (rangeValues != null) {
        rangeValues.clear();
      }

      if (!rangeOnly) {
        if (domainValues != null) {
          domainValues.clear();
        }

        if (domainMap != null) {
          domainMap.clear();
        }
      }
    }

    public String[] createLabels(aChartViewer cv, iDataConverter cvt, Object context, boolean domain) {
      int      len    = domainValues.size();
      String[] labels = new String[len];
      List     list   = domain
                        ? domainValues
                        : rangeValues;
      String   s;

      for (int i = 0; i < len; i++) {
        if (cvt != null) {
          s = cvt.objectToString(cv, list.get(i), context).toString();
        } else {
          s = list.get(i).toString();
        }

        labels[i] = s;
      }

      return labels;
    }

    public void dispose() {
      if (domainMap != null) {
        domainMap.clear();
      }

      if (dataItems != null) {
        dataItems.clear();
      }

      linkedData = null;
    }

    public ChartDataItem getDataItem(int index) {
      return dataItems.get(index);
    }

    /**
     * Creates a label if a pie pchart section
     * <p>
     * For the label format, use {0} where the pie section key should be inserted,
     * {1} for the absolute section value and {2} for the percent amount of the pie
     * section, {3} for the total value, e.g. <code>"{0} = {1} ({2})"</code> will display as
     * <code>apple = 120 (5%)</code>.
     * @param index the index of the chart section
     * @param format the format as described above
     */
    public String getPieChartLabel(int index, String format) {
      String label = getPointLabel(index, null);

      if (label.length() == 0) {
        return label;
      }

      double total = pieTotal;

      if (total == 0) {
        for (Number num : rangeValues) {
          total += num.doubleValue();
        }

        pieTotal = total;
      }

      Number num     = rangeValues.get(index);
      double val     = (num == null)
                       ? 0
                       : num.doubleValue();
      double percent = (val / total);

      if (percentFormat == null) {
        percentFormat = NumberFormat.getPercentInstance(Locale.getDefault());
        totalFormat   = NumberFormat.getNumberInstance(Locale.getDefault());
      }

      Object[] a = new Object[4];

      a[0] = dataItems.get(index).getDomainString();
      a[1] = label;
      a[2] = percentFormat.format(Double.valueOf(percent));
      a[3] = totalFormat.format(Double.valueOf(total));

      return MessageFormat.format(format, a);
    }

    public String getPointLabel(int index, String format) {
      String label = "";

      if (showPointLabels) {
        ChartDataItem di = dataItems.get(index);

        switch(labelType) {
          case VALUES :
            label = di.toString();

            break;

          case LINKED_DATA :
            if (di.getLinkedData() != null) {
              label = di.getLinkedData().toString();
            }

            break;

          case TOOLTIPS :
            if (di.getTooltip() != null) {
              label = di.getTooltip().toString();
            }

            break;

          default :
            break;
        }

        if (format != null) {
          label = PlatformHelper.format(format, label, legend);
        }
      }

      return label;
    }

    public String toString() {
      return (legend == null)
             ? super.toString()
             : legend;
    }
  }


  protected static abstract class aChartInfo {
    public int              domainType;
    public double[]         xAxisValues;
    public double[]         yAxisValues;
    public boolean          xIncrementFixed = true;
    public boolean          yIncrementFixed = true;
    public List<Comparable> domainValues;
    public HashSet          domainMap;
    public List<SeriesData> seriesData;
    public LabelData[]      labelData;
    public boolean          categoryDomain;

    public void addDomainValues(List<Comparable> list) {
      if ((domainValues == null) || domainValues.isEmpty()) {
        domainValues = new ArrayList<Comparable>(list);
        ;

        if (domainMap == null) {
          domainMap = new HashSet(list);
        } else {
          domainMap.clear();
          domainMap.addAll(list);
        }
      } else {
        if ((domainMap == null) || domainMap.isEmpty()) {
          domainMap    = new HashSet(domainValues);
          domainValues = new ArrayList<Comparable>(domainValues);
        }

        for (Comparable o : list) {
          if (domainMap.add(o)) {
            domainValues.add(o);
          }
        }
      }
    }

    public LabelData[] createLabelData(ChartDefinition cd, UIFontMetrics fm, boolean convert) {
      if (domainValues == null) {
        return null;
      }

      iDataConverter cvt       = convert
                                 ? cd.getDomainAxis().getDomainDataConverter()
                                 : null;
      Object         ctx       = convert
                                 ? cd.getDomainAxis().getDomainContext()
                                 : null;
      int            textAngle = cd.getDomainAxis().getAngle();

      labelData = createLabelsData(domainValues, cd.getChartViewer(), cvt, ctx, true, fm, textAngle, 0, 1, false);

      return labelData;
    }

    public void dispose() {
      reset();
    }

    public void popularSeriesDataAndCaluclateRanges(aChartHandler ch, ChartDefinition cd) {
      reset();

      List<RenderableDataItem> serieses = cd.getSeries();
      List<SeriesData>         sd       = null;
      int                      dataType = getDataType(cd.getDomainAxis());

      domainType = dataType;

      int     len      = (serieses == null)
                         ? 0
                         : serieses.size();
      boolean category = false;

      if (len > 0) {
        int       start = cd.getStartColumn();
        int       eend  = cd.getEndColumn();
        int       end   = 0;
        ChartAxis ai    = cd.getDomainAxis();

        switch(dataType) {
          case TYPE_DATE :
            TimeUnit tu = ai.getTimeUnit();

            if ((tu == null) || (tu == TimeUnit.NONE)) {
              category = true;
            }

            break;

          case TYPE_NUMBER :
            break;

          default :
            category = true;

            break;
        }

        categoryDomain = category;
        ai             = cd.getRangeAxis();

        PlotInformation          pi           = cd.getPlotInformation();
        List<RenderableDataItem> domainValues = cd.getDomainValues();

        sd = new ArrayList<aChartHandler.SeriesData>(len);

        for (int i = 0; i < len; i++) {
          ChartDataItem series = (ChartDataItem) serieses.get(i);

          if (series.isEmpty()) {
            continue;
          }

          LabelType lt = getSeriesLabelType(pi, series);

          if (eend != 0) {
            end = eend + 1;
          } else {
            end = series.size();
          }

          if (cd.isAutoSort()) {
            series.sort(new Comparator() {
              @Override
              public int compare(Object t, Object t1) {
                return ((Comparable) ((ChartDataItem) t).getDomainValue()).compareTo(
                    (((ChartDataItem) t1).getDomainValue()));
              }
            });
          }

          SeriesData data = createSeriesData(dataType, series, domainValues, start, end, lt);

          if ((data != null) && (data.domainValues != null)) {
            data.seriesIndex = i;
            data.dataIndex   = sd.size();
            data.chartType   = getSeriesChartType(cd, series);
            data.legend      = (String) series.toString();

            switch(data.chartType) {
              case RANGE_AREA :
              case RANGE_BAR :
                data.isNumberRange = true;

                break;

              default :
                break;
            }

            sd.add(data);

            if (category) {
              addDomainValues(data.domainValues);
            }
          }
        }
      }

      seriesData = ((sd == null) || sd.isEmpty())
                   ? null
                   : sd;

      if (seriesData != null) {
        updateRangeBounds(ch, cd);

        if ((domainMap != null) &&!domainMap.isEmpty()) {    // we had multiple
          // series
          if (domainType != TYPE_STRING) {
            Collections.sort(domainValues);
          }
        }

        if (category) {
          updateCategorDomainValues();
        }
      }
    }

    public void reset() {
      if (seriesData != null) {
        for (SeriesData d : seriesData) {
          d.dispose();
        }
      }

      seriesData      = null;
      domainValues    = null;
      domainMap       = null;
      xAxisValues     = null;
      yAxisValues     = null;
      xIncrementFixed = true;
      yIncrementFixed = true;
      categoryDomain  = false;
    }

    public void setShowPointLabels(boolean show) {
      if (seriesData != null) {
        for (SeriesData data : seriesData) {
          data.showPointLabels = show;
        }
      }
    }

    public SeriesData updateSeries(ChartDefinition cd, ChartDataItem series) {
      SeriesData data      = null;
      int        len       = (seriesData == null)
                             ? 0
                             : seriesData.size();
      int        index     = (len == 0)
                             ? -1
                             : cd.findSeriesIndex(series);
      int        dataIndex = -1;

      if (index != -1) {
        for (int i = 0; i < len; i++) {
          SeriesData sd = seriesData.get(i);

          if (sd.seriesIndex == index) {
            data      = sd;
            dataIndex = i;

            break;
          }
        }
      }

      if (data != null) {
        int       start = cd.getStartColumn();
        int       eend  = cd.getEndColumn();
        int       end   = 0;
        LabelType lt    = getSeriesLabelType(cd.getPlotInformation(), series);

        if (eend != 0) {
          end = eend + 1;
        } else {
          end = series.size();
        }

        if (cd.isAutoSort()) {
          series.sort(new Comparator() {
            @Override
            public int compare(Object t, Object t1) {
              return ((Comparable) ((ChartDataItem) t).getDomainValue()).compareTo(
                  (((ChartDataItem) t1).getDomainValue()));
            }
          });
        }

        data = createSeriesData(domainType, series, cd.getDomainValues(), start, end, lt);

        if ((data != null) && (data.domainValues != null)) {
          data.seriesIndex = index;
          data.dataIndex   = dataIndex;
          data.legend      = (String) series.getValue();
          seriesData.set(dataIndex, data);

          if (categoryDomain) {
            addDomainValues(data.domainValues);
          }
        } else {
          seriesData.remove(dataIndex);
        }
      }

      return data;
    }

    protected void updateCategorDomainValues() {
      List<Comparable> list = domainValues;
      int              len  = (seriesData == null)
                              ? 0
                              : seriesData.size();

      if ((len == 0) || (list == null) || list.isEmpty()) {
        return;
      }

      for (int i = 0; i < len; i++) {
        SeriesData       data = seriesData.get(i);
        List<Comparable> dv   = data.domainValues;

        if (dv == domainValues) {
          dv                = new ArrayList<Comparable>(dv);
          data.domainValues = dv;
        }

        int dlen = (dv == null)
                   ? 0
                   : dv.size();

        for (int n = 0; n < dlen; n++) {
          int j = list.indexOf(dv.get(n));

          if (j != -1) {
            dv.set(n, j);
          } else {
            System.out.println("!!!!BUG in updateCategorDomainValues!!!!!");
          }
        }
      }
    }

    protected void updateRangeBounds(aChartHandler ch, ChartDefinition cd) {
      double[] yrange = new double[3];
      double[] xrange = null;

      yrange[0] = Double.MAX_VALUE - 1;
      yrange[1] = 0;
      yrange[2] = 0;

      double ymin = yrange[0];
      double ymax = yrange[1];
      double xmin = Double.MAX_VALUE - 1;
      double xmax = 0;

      xrange    = new double[3];
      xrange[0] = Double.MAX_VALUE - 1;
      xrange[1] = 0;
      xrange[2] = 0;

      int    len = (seriesData == null)
                   ? 0
                   : seriesData.size();
      double d;

      for (int i = 0; i < len; i++) {
        SeriesData   data = seriesData.get(i);
        List<Number> nums = data.rangeValues;
        int          nlen = (nums == null)
                            ? 0
                            : nums.size();

        for (int n = 0; n < nlen; n++) {
          if (data.isNumberRange) {
            NumberRange r = (NumberRange) nums.get(n);

            d    = r.getLowValue().doubleValue();
            ymin = Math.min(d, ymin);
            ymax = Math.max(d, ymax);
            d    = r.getHighValue().doubleValue();
            ymin = Math.min(d, ymin);
            ymax = Math.max(d, ymax);
          } else {
            d    = nums.get(n).doubleValue();
            ymin = Math.min(d, ymin);
            ymax = Math.max(d, ymax);
          }
        }

        if (!categoryDomain) {
          List<Comparable> doms = data.domainValues;
          int              dlen = (doms == null)
                                  ? 0
                                  : doms.size();

          for (int n = 0; n < dlen; n++) {
            Comparable c = doms.get(n);

            if (c instanceof Date) {
              d = ((Date) c).getTime();
            } else {
              d = ((Number) c).doubleValue();
            }

            xmin = Math.min(d, xmin);
            xmax = Math.max(d, xmax);
          }
        }
      }

      yrange[0] = ymin;
      yrange[1] = ymax;

      if (!categoryDomain) {
        xrange[0] = xmin;
        xrange[1] = xmax;
      } else {
        xrange[0] = 0;
        xrange[1] = (domainValues == null)
                    ? 0
                    : domainValues.size();
      }

      ch.calculateSeriesesRange(cd, domainType, xrange, yrange);
      xIncrementFixed = cd.getDomainAxis().getIncrement() != 0;

      TimeUnit tu = cd.getDomainAxis().getTimeUnit();

      if ((tu != null) && (tu != TimeUnit.NONE)) {
        xIncrementFixed = true;
      }

      yIncrementFixed = cd.getRangeAxis().getIncrement() != 0;

      if (!xIncrementFixed && (xrange[2] > 1)) {
        xrange[2] = 1;
      }

      if (!yIncrementFixed && (yrange[2] > 1)) {
        yrange[2] = 1;
      }

      xAxisValues = xrange;
      yAxisValues = yrange;
    }
  }
}
