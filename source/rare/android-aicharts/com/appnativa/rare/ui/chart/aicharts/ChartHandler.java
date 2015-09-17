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

package com.appnativa.rare.ui.chart.aicharts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.android.ui.NullDrawable;
import com.appnativa.rare.platform.android.ui.PainterDrawable;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.ImageUtils;
import com.appnativa.rare.platform.android.ui.util.PainterUtils;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.platform.android.ui.view.LinearLayoutEx;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.border.UILineBorder;
import com.appnativa.rare.ui.chart.ChartAxis.TimeUnit;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.PlotInformation;
import com.appnativa.rare.ui.chart.PlotInformation.LabelType;
import com.appnativa.rare.ui.chart.aChartHandler;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.util.NumberRange;
import com.artfulbits.aiCharts.Base.ChartArea;
import com.artfulbits.aiCharts.Base.ChartAxis;
import com.artfulbits.aiCharts.Base.ChartAxis.Label;
import com.artfulbits.aiCharts.Base.ChartAxis.LabelsAdapter;
import com.artfulbits.aiCharts.Base.ChartAxisScale;
import com.artfulbits.aiCharts.Base.ChartAxisStripLine;
import com.artfulbits.aiCharts.Base.ChartCollection;
import com.artfulbits.aiCharts.Base.ChartEngine;
import com.artfulbits.aiCharts.Base.ChartLayoutElement.Dock;
import com.artfulbits.aiCharts.Base.ChartLegend;
import com.artfulbits.aiCharts.Base.ChartLegend.LayoutMode;
import com.artfulbits.aiCharts.Base.ChartLegendAdapter;
import com.artfulbits.aiCharts.Base.ChartLegendItemsBinder;
import com.artfulbits.aiCharts.Base.ChartPoint;
import com.artfulbits.aiCharts.Base.ChartPointCollection;
import com.artfulbits.aiCharts.Base.ChartSeries;
import com.artfulbits.aiCharts.Base.ChartType;
import com.artfulbits.aiCharts.Enums.Alignment;
import com.artfulbits.aiCharts.Types.ChartPieType;
import com.artfulbits.aiCharts.Types.ChartTypes;

/**
 *
 * @author Don DeCoteau
 */
public class ChartHandler extends aChartHandler implements ChartCollection.IChangeListener {
  ArrayList<Marker>        filledMarkers;
  ArrayList<Marker>        outlineFillMarkers;
  ArrayList<Marker>        markers;
  private int              tickSize;
  private List<SeriesData> seriesData;

  public ChartHandler() {
    super();
  }

  public ChartSeries createCategorySeries(ChartViewEx chart, ChartDefinition cd, String type) {
    List<RenderableDataItem> rows = cd.getSeries();
    int                      len  = (rows == null)
                                    ? 0
                                    : rows.size();

    if (len == 0) {
      return null;
    }

    ChartSeries          series = new ChartSeries(ChartTypes.get(type));
    ChartPointCollection points = series.getPoints();
    ChartPoint           point;
    ChartDataItem        di;
    String               s;
    Number               num;
    PlotInformation      pi    = cd.getPlotInformation();
    LabelType            lt    = (pi == null)
                                 ? PlotInformation.LabelType.VALUES
                                 : pi.getLabelType();
    boolean              isPie = false;

    if (type == ChartTypes.PieName) {
      rows  = rows.get(0).getItems();
      len   = rows.size();
      isPie = true;
    }

    ChartDataItem categories = new ChartDataItem();

    categories.addAll(rows);

    UIColor fg          = cd.getTextColor(plotLabelColor);
    UIFont  font        = cd.getTextFont(plotLabelFont);
    String  labelFormat = (pi == null)
                          ? null
                          : pi.getLabelsFormat();

    if (isPie && ((labelFormat == null) || (labelFormat.length() == 0))) {
      labelFormat = "{2}";
    }

    List<RenderableDataItem> columns      = cd.getDomainValues();
    SeriesData               data         = aChartHandler.createSeriesData(TYPE_STRING, categories, columns, 0,
                                              len - 1, lt);
    List<Number>             rangeValues  = data.rangeValues;
    List<Comparable>         domainValues = data.domainValues;
    List<ChartDataItem>      dataItems    = data.dataItems;

    len = dataItems.size();

    for (int i = 0; i < len; i++) {
      di    = dataItems.get(i);
      num   = rangeValues.get(i);
      s     = domainValues.get(i).toString();
      point = points.addXY(i, num.doubleValue());
      point.setAxisLabel(s);

      if (isPie) {
        s = data.getPieChartLabel(i, labelFormat);
      } else {
        s = data.getPointLabel(i, labelFormat);
      }

      if (s != null) {
        point.setLabel(s);
        point.setShowLabel(true);
        point.getTextPaint().setAntiAlias(true);

        if (fg != null) {
          point.getTextPaint().setColor(fg.getColor());
        }

        font.setupPaint(point.getTextPaint());
      }

      configurePoint(chart, point, di);

      if (di.getVerticalAlignment() == ChartDataItem.VerticalAlign.TOP) {}
    }

    return series;
  }

  @Override
  public iPlatformComponent createChart(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo   ci    = (ChartInfo) cd.getChartHandlerInfo();
    ChartViewEx chart = null;

    if (ci != null) {
      chart = ci.chart;
      ci.dispose();
    }

    if (chart == null) {
      chart = new ChartViewEx(cd.getChartViewer().getAndroidContext());
    } else {
      chart.setChart(new ChartEngine());
    }

    ci = new ChartInfo(chart);
    cd.setChartHandlerInfo(ci);

    return createChartEx(chartPanel, cd);
  }

  protected iPlatformComponent createChartEx(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo   ci    = (ChartInfo) cd.getChartHandlerInfo();
    ChartViewEx chart = ci.chart;

    ci.popularSeriesDataAndCaluclateRanges(this, cd);
    ci.setShowPointLabels(cd.isShowPlotLabels());

    int       domainType  = ci.domainType;
    ChartArea area        = new ChartArea();
    UIColor   plotbg      = plotBackground;
    UIColor   c           = chartForeground;
    Integer   lineColor   = (c == null)
                            ? UIColor.GRAY.getColor()
                            : c.getColor();
    boolean   fixedLabels = true;

    switch(domainType) {
      case TYPE_DATE :
        TimeUnit tu = cd.getDomainAxis().getTimeUnit();

        if ((tu != null) && (tu != TimeUnit.NONE)) {
          fixedLabels = false;
        }

        if (fixedLabels) {
          customizeAxisEx(cd.getDomainAxis(), area.getDefaultXAxis(), lineColor);
        } else {
          customizeNumberAxis(cd, cd.getDomainAxis(), area.getDefaultXAxis(), lineColor, false);
        }

        break;

      case TYPE_NUMBER :
        customizeNumberAxis(cd, cd.getDomainAxis(), area.getDefaultXAxis(), lineColor, false);
        fixedLabels = false;

        break;

      default :
        customizeAxisEx(cd.getDomainAxis(), area.getDefaultXAxis(), lineColor);

        break;
    }

    UIFont        f  = getAxisLabelFont(cd.getRangeAxis());
    UIFontMetrics fm = UIFontMetrics.getMetrics(f);

    tickSize = (int) fm.getHeight();
    customizeNumberAxis(cd, cd.getRangeAxis(), area.getDefaultYAxis(), lineColor, false);
    c = cd.getBackgroundColor();

    if (c != null) {
      area.setBackground(c.getDrawable());
    }

    PlotInformation    plot        = cd.getPlotInformation();
    UIColor            pc          = null;
    UIColor            borderColor = null;
    boolean            showGrid    = true;
    UIComponentPainter cp          = new UIComponentPainter();

    if (plot != null) {
      UIImage img = plot.getBackgroundImage();

      c = plot.getBackgroundColor();

      if (c == null) {
        c = plotbg;
      }

      if (img != null) {
        cp.setBackgroundOverlayPainter(new UIImagePainter(img));
      }

      if (c != null) {
        cp.setBackgroundColor(c);
      }

      showGrid = plot.isShowGridLines();

      if (showGrid) {
        pc = plot.getGridColor();

        if (pc == null) {
          pc = gridColor;
        }
      }

      borderColor = plot.getBorderColor();
    } else {
      if (plotbg != null) {
        cp.setBackgroundColor(plotbg);
      }

      pc = gridColor;
    }

    if (borderColor == null) {
      borderColor = UIColor.GRAY;
    }

    if (borderColor.getAlpha() != 0) {
      cp.setBorder(new UILineBorder(borderColor));
    }

    if (cp.isBackgroundPaintEnabled() || (cp.getBorder() != null)) {
      area.setGridBackground(cp.getDrawable(chart));
    }

    if (showGrid) {
      area.getDefaultXAxis().setGridVisible(cd.getDomainAxis().isShowGridLine());
      area.getDefaultYAxis().setGridVisible(cd.getRangeAxis().isShowGridLine());

      UIStroke stroke = getGridStroke(plot);

      if (stroke != null) {
        AndroidHelper.setLineStroke(stroke, area.getDefaultXAxis().getGridLinePaint());
        AndroidHelper.setLineStroke(stroke, area.getDefaultYAxis().getGridLinePaint());
      }

      if (pc != null) {
        area.getDefaultXAxis().setGridLineColor(pc.getColor());
        area.getDefaultYAxis().setGridLineColor(pc.getColor());
      }
    } else {
      area.getDefaultXAxis().setGridVisible(false);
      area.getDefaultYAxis().setGridVisible(false);
    }

    chart.getAreas().add(area);
    ci.domainType = domainType;

    if (chartPanel == null) {
      chartPanel = new ChartPanelComponent(cd, chart, cd.getTitle());
    } else {
      ((ChartPanelComponent) chartPanel).set(cd, chart, cd.getTitle());
    }

    if (cd.getRangeAxis().getIncrement() != 0) {
      ci.yAxisValues = null;
    }

    if (cd.getDomainAxis().getIncrement() != 0) {
      ci.xAxisValues = null;
    }

    createChart(chart, area, cd);

    CustomAdapter a;

    if (fixedLabels) {
      LabelData[] data = ((ChartInfo) cd.getChartHandlerInfo()).createLabelData(cd, fm, true);

      a = new CustomAdapter(chart, cd, data);
    } else {
      a = new CustomAdapter(chart, cd, true);
    }

    area.getDefaultXAxis().setLabelsAdapter(a);
    area.getDefaultYAxis().setLabelsAdapter(new CustomAdapter(chart, cd, false));

    return chartPanel;
  }

  public ChartSeries createSeries(SeriesData data, ChartViewEx chart, ChartArea area, ChartDefinition cd,
                                  float thickness) {
    String                     sname = data.legend;
    Number                     num;
    Number                     dval;
    String                     label;
    ChartPoint                 point       = null;
    Marker                     pointMarker = null;
    ChartInfo                  ci          = (ChartInfo) cd.getChartHandlerInfo();
    int                        index       = data.seriesIndex;
    ChartDataItem              row         = cd.getSeries(index);
    UIFont                     font        = cd.getTextFont(plotLabelFont);
    UIColor                    fg          = cd.getTextColor(plotLabelColor);
    PlotInformation            pi          = cd.getPlotInformation();
    PlotInformation.ShapeStyle ss          = PlotInformation.ShapeStyle.NONE;
    String                     labelFormat = (pi == null)
            ? null
            : pi.getLabelsFormat();
    String                     type        = getChartType(cd, getSeriesChartType(cd, row));
    ChartSeries                series      = new ChartSeries(ChartTypes.get(type));

    series.setLineWidth((int) getSeriesLineThickness(row, thickness));

    ChartPointCollection points = series.getPoints();

    row.setChartLinkedData(new ItemInfo(series));

    if ((type == ChartTypes.LineName) || (type == ChartTypes.SplineName)) {
      ss = getSeriesShapeStyle(cd.getPlotInformation(), row);
    }

    if ((type == ChartTypes.RangeAreaName) || (type == ChartTypes.RangeColumnName)) {
      series.setVLabelAlignment(Alignment.Far);
    } else {
      series.setVLabelAlignment(Alignment.Near);
    }

    iPlatformComponentPainter cp = row.getComponentPainter();
    UIColor                   bg = PaintBucket.getBackgroundColor(row);

    if ((cp == null) && (bg == null)) {
      cp = cd.getRangeAxis().getComponentPainter();
      bg = PaintBucket.getBackgroundColor(cd.getRangeAxis());
    }

    if (bg == null) {
      bg = getDefaultColor(index);
    }

    Drawable d;

    if (cp != null) {
      d = cp.getDrawable(chart);
    } else {
      d = bg.getDrawable();
    }

    series.setBackColor(bg.getColor());
    series.setBackDrawable(checkDrawable(d));

    UIColor fc = data.fillColor;
    UIColor oc = data.outlineColor;

    if (oc == null) {
      oc = bg;
    }

    switch(ss) {
      case FILLED :
        pointMarker             = getMarker(index);
        pointMarker.fillColor   = (fc == null)
                                  ? bg
                                  : fc;
        pointMarker.strokeColor = null;

        break;

      case OUTLINED :
        pointMarker             = getMarker(index);
        pointMarker.strokeColor = oc;
        pointMarker.fillColor   = null;

        break;

      case FILLED_AND_OUTLINED :
        pointMarker             = getMarker(index);
        pointMarker.strokeColor = oc;
        pointMarker.fillColor   = (fc == null)
                                  ? UIColor.WHITE
                                  : fc;

        break;

      default :
        break;
    }

    if (pointMarker != null) {
      pointMarker.getPaint().setColor(bg.getColor());
    }

    if (sname != null) {
      series.setName(sname.toString());
    }

    List<Number>            rangeValues  = data.rangeValues;
    List<Comparable>        domainValues = data.domainValues;
    int                     domainType   = ci.domainType;
    int                     len          = rangeValues.size();
    HashMap<UIColor, Paint> paints       = null;

    font.setupPaint(series.getTextPaint());

    if (fg != null) {
      series.getTextPaint().setColor(fg.getColor());
    }

    for (int i = 0; i < len; i++) {
      num = rangeValues.get(i);

      Object o = domainValues.get(i);

      if (o instanceof Date) {
        dval = ((Date) o).getTime();
      } else {
        dval = (Number) o;
      }

      switch(domainType) {
        case TYPE_NUMBER :
          if (num instanceof NumberRange) {
            point = points.addXY(dval.doubleValue(), ((NumberRange) num).getLowValue().doubleValue(),
                                 ((NumberRange) num).getHighValue().doubleValue());
          } else {
            point = points.addXY(dval.doubleValue(), num.doubleValue());
          }

          break;

        case TYPE_DATE :
          if (num instanceof NumberRange) {
            point = points.addXY(dval.doubleValue(), ((NumberRange) num).getLowValue().doubleValue(),
                                 ((NumberRange) num).getHighValue().doubleValue());
          } else {
            point = points.addXY(dval.doubleValue(), num.doubleValue());
          }

          break;

        case TYPE_STRING :
          if (num instanceof NumberRange) {
            point = points.addXY(i, ((NumberRange) num).getLowValue().doubleValue(),
                                 ((NumberRange) num).getHighValue().doubleValue());
          } else {
            point = points.addXY(i, num.doubleValue());
          }

          break;

        default :
          break;
      }

      ChartDataItem di = data.getDataItem(i);

      label = data.getPointLabel(i, labelFormat);

      if (label.length() != 0) {
        point.setLabel(label);
        point.setShowLabel(true);
        point.getTextPaint().setAntiAlias(true);

        UIColor dfg = di.getForeground();

        if (dfg != null) {
          if (paints == null) {
            paints = new HashMap<UIColor, Paint>();
          }

          Paint p = paints.get(dfg);

          if (p == null) {
            p = new Paint(series.getTextPaint());
            p.setColor(dfg.getColor());
            paints.put(dfg, p);
          }

          point.setTextPaint(p);
        }
      }

      configurePoint(chart, point, di);
    }

    if (pointMarker != null) {
      float size = ScreenUtils.PLATFORM_PIXELS_6;

      if (pi != null) {
        size = ScreenUtils.PLATFORM_PIXELS_4 * cd.getPlotInformation().getLineThickness();
      }

      if (size < ScreenUtils.PLATFORM_PIXELS_6) {
        size = ScreenUtils.PLATFORM_PIXELS_6;
      }

      pointMarker.setIntrinsicHeight((int) Math.ceil(size));
      pointMarker.setIntrinsicWidth((int) Math.ceil(size));
      series.setMarkerDrawable(pointMarker);
    } else {
      series.setMarkerDrawable(NullDrawable.getInstance());
    }

    return series;
  }

  @Override
  public iPlatformComponent dataChanged(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

    if ((ci == null) || (ci.chart == null)) {
      return createChart(chartPanel, cd);
    }

    ci.reset();
    ci.chart.setChart(new ChartEngine());

    return createChartEx(chartPanel, cd);
  }

  @Override
  public void dispose(iPlatformComponent chartPanel, ChartDefinition cd) {
    super.dispose(chartPanel, cd);
    filledMarkers      = null;
    outlineFillMarkers = null;
    markers            = null;

    if (cd != null) {
      ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

      if (ci != null) {
        ci.dispose();
      }
    }
  }

  public UIDimension getPlotAreaSize(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    if ((info == null) || (info.chart == null)) {
      return chartPanel.getSize();
    }

//get series bounds causes the axis labels to disappear on smaller charts
//    ChartViewEx chart = info.chart;
//    ChartArea area = chart.getAreas().get(0);
//    RectF rx=area.getDefaultXAxis().getBounds();
//    RectF ry=area.getDefaultYAxis().getBounds();
//    Rect r=area.getSeriesBounds();
//  return new UIDimension(r.width(),r.height());
    return chartPanel.getSize();
  }

  @Override
  public void itemChanged(iPlatformComponent chartComponent, ChartDefinition cd, ChartDataItem item) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    if ((info == null) || (info.chart == null)) {
      return;
    }

    ItemInfo ii = (ItemInfo) item.getChartLinkedData();

    if (ii == null) {
      return;
    }

    ChartViewEx   cp    = info.chart;
    final boolean block = cp.blockPaint;

    cp.blockPaint = true;

    try {
      switch(item.getItemType()) {
        case SERIES :
          UIFont          font        = cd.getTextFont(plotLabelFont);
          UIColor         fg          = cd.getTextColor(plotLabelColor);
          PlotInformation pi          = cd.getPlotInformation();
          String          labelFormat = (pi == null)
                                        ? null
                                        : pi.getLabelsFormat();
          SeriesData      data        = info.updateSeries(cd, item);

          updateSeries(info, (ChartSeries) ii.chartItem, data, labelFormat, font, fg);

          break;

        case DOMAIN_MARKER :
        case RANGE_MARKER :
          updateStripLine(cp, (ChartAxisStripLine) ii.chartItem, item);

          break;

        case POINT :
          break;

        default :
          break;
      }
    } finally {
      if (!block) {
        cp.unblockPainting();
      }
    }
  }

  @Override
  public ChartDataItem itemFromLocation(ChartDefinition cd, int x, int y) {
    ChartDataItem item = null;

    if (cd != null) {
      ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

      if (info.chart != null) {
        // item = itemFromEntity(cd, info.chartPanel.getEntityForPoint(x, y),
        // false);
      }
    }

    return item;
  }

  @Override
  public void onChanged(Object e, Object e1, int i) {}

  @Override
  public void unzoom(iPlatformComponent chartPanel) {}

  @Override
  public void updateRangeAxis(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    if (info.chart == null) {
      return;
    }

    ChartViewEx chart = info.chart;

    if (chart.getChart().getAreas().size() == 0) {
      return;
    }

    ChartAxis a = chart.getChart().getAreas().get(0).getDefaultYAxis();

    if (a.getValueType() == ChartAxis.ValueType.Double) {
      customizeNumberAxis(cd, cd.getRangeAxis(), a, null, true);
    } else if (a.getValueType() == ChartAxis.ValueType.Date) {
      customizeAxisEx(cd.getRangeAxis(), a, null);
    }
  }

  protected void updateSeries(ChartInfo ci, ChartSeries series, SeriesData data, String labelFormat, UIFont font,
                              UIColor fg) {
    ChartPointCollection points = series.getPoints();

    points.beginUpdate();
    points.clear();

    try {
      if (data != null) {
        List<Number>     rangeValues  = data.rangeValues;
        List<Comparable> domainValues = data.domainValues;
        int              domainType   = ci.domainType;
        int              len          = rangeValues.size();
        Number           dval;
        Number           num;
        ChartPoint       point;
        ChartViewEx      chart = ci.chart;
        String           label;

        for (int i = 0; i < len; i++) {
          num   = rangeValues.get(i);
          dval  = (Number) domainValues.get(i);
          point = null;

          switch(domainType) {
            case TYPE_NUMBER :
              if (num instanceof NumberRange) {
                point = points.addXY(dval.doubleValue(), ((NumberRange) num).getLowValue().doubleValue(),
                                     ((NumberRange) num).getHighValue().doubleValue());
              } else {
                point = points.addXY(dval.doubleValue(), num.doubleValue());
              }

              break;

            case TYPE_DATE :
              if (num instanceof NumberRange) {
                point = points.addXY(dval.doubleValue(), ((NumberRange) num).getLowValue().doubleValue(),
                                     ((NumberRange) num).getHighValue().doubleValue());
              } else {
                point = points.addXY(dval.doubleValue(), num.doubleValue());
              }

              break;

            case TYPE_STRING :
              if (num instanceof NumberRange) {
                point = points.addXY(i, ((NumberRange) num).getLowValue().doubleValue(),
                                     ((NumberRange) num).getHighValue().doubleValue());
              } else {
                point = points.addXY(i, num.doubleValue());
              }

              break;

            default :
              break;
          }

          if (point != null) {
            label = data.getPointLabel(i, labelFormat);

            ChartDataItem di = data.getDataItem(i);

            if (label.length() != 0) {
              if (labelFormat != null) {
                label = PlatformHelper.format(labelFormat, label);
              }

              point.setLabel(label);
              point.setShowLabel(true);
              point.getTextPaint().setAntiAlias(true);

              UIColor dfg = di.getForeground();

              if (dfg != null) {
                point.getTextPaint().setColor(dfg.getColor());
              } else if (fg != null) {
                point.getTextPaint().setColor(fg.getColor());
              }

              font.setupPaint(point.getTextPaint());
            }

            configurePoint(chart, point, di);
          }
        }
      }
    } finally {
      points.endUpdate();
    }
  }

  @Override
  public void updatesCompleted(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartViewEx chart = ((ChartPanelComponent) chartPanel).getChart();

    for (ChartSeries s : chart.getSeries()) {
      s.getPoints().endUpdate();
    }
  }

  @Override
  public void updatesPending(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartViewEx chart = ((ChartPanelComponent) chartPanel).getChart();

    for (ChartSeries s : chart.getSeries()) {
      s.getPoints().beginUpdate();
    }
  }

  @Override
  public void setDomainLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, true);

    if (a != null) {
      a.setTitle(cd.getDomainLabel());
    }
  }

  @Override
  public void setDomainLabelAngel(iPlatformComponent chartComponent, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, false);

    if (a != null) {
      int angle = cd.getDomainAxis().getAngle();

      if (angle != (int) a.getLabelAngle()) {
        a.setLabelAngle(angle);
        a.getScale().setInterval(a.getScale().getInterval());
      }
    }
  }

  @Override
  public void setHorizontalScrollingEnabled(iPlatformComponent chartComponent, boolean enable) {
    ChartViewEx chart = ((ChartPanelComponent) chartComponent).getChart();

    if (chart != null) {
      for (ChartArea a : ((ChartPanelComponent) chartComponent).getChart().getAreas()) {
        a.getDefaultXAxis().setScrollBarEnabled(true);
      }
    }
  }

  @Override
  public void setRangeLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, true);

    if (a != null) {
      a.setTitle(cd.getRangeLabel());
    }
  }

  @Override
  public void setRangeLabelAngel(iPlatformComponent chartComponent, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, true);

    if (a != null) {
      int angle = cd.getRangeAxis().getAngle();

      if (angle != (int) a.getLabelAngle()) {
        a.setLabelAngle(angle);
        a.getScale().setInterval(a.getScale().getInterval());
      }
    }
  }

  @Override
  public void setShowDomainLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, false);

    if (a != null) {
      a.setShowLabels(cd.getDomainAxis().isLabelsVisible());
    }
  }

  @Override
  public void setShowPlotValues(iPlatformComponent chartPanel, ChartDefinition cd) {
    createChart(chartPanel, cd);
  }

  @Override
  public void setShowRangeLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartAxis a = getAxis(cd, true);

    if (a != null) {
      a.setShowLabels(cd.getRangeAxis().isLabelsVisible());
    }
  }

  @Override
  public void setVerticalScrollingEnabled(iPlatformComponent chartComponent, boolean enable) {
    ChartViewEx chart = ((ChartPanelComponent) chartComponent).getChart();

    if (chart != null) {
      for (ChartArea a : ((ChartPanelComponent) chartComponent).getChart().getAreas()) {
        a.getDefaultYAxis().setScrollBarEnabled(true);
      }
    }
  }

  @Override
  public UIImage getChartImage(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartViewEx chart = ((ChartPanelComponent) chartPanel).getChart();

    if (chart != null) {
      return ImageUtils.createImage(chart);
    }

    return null;
  }

  public static ChartAxisScale.IntervalType getTimePeriod(com.appnativa.rare.ui.chart.ChartAxis.TimeUnit tm) {
    ChartAxisScale.IntervalType tp = null;

    if (tm != null) {
      switch(tm) {
        case DAY :
          tp = ChartAxisScale.IntervalType.Days;

          break;

        case MILLISECOND :
          tp = ChartAxisScale.IntervalType.Milliseconds;

          break;

        case SECOND :
          tp = ChartAxisScale.IntervalType.Seconds;

          break;

        case MINUTE :
          tp = ChartAxisScale.IntervalType.Minutes;

          break;

        case HOUR :
          tp = ChartAxisScale.IntervalType.Hours;

          break;

        case MONTH :
          tp = ChartAxisScale.IntervalType.Month;

          break;

        case YEAR :
          tp = ChartAxisScale.IntervalType.Years;

          break;

        default :
          break;
      }
    }

    return tp;
  }

  protected void createChart(ChartViewEx chart, ChartArea area, ChartDefinition cd) {
    final String    chartType = getChartType(cd, cd.getChartType());
    ChartSeries     series;
    ChartInfo       ci         = (ChartInfo) cd.getChartHandlerInfo();
    int             domainType = ci.domainType;
    int             len;
    PlotInformation plot      = cd.getPlotInformation();
    final float     thickness = (plot == null)
                                ? ScreenUtils.PLATFORM_PIXELS_2
                                : plot.getLineThickness();

    if (chartType == ChartTypes.PieName) {
      series = createCategorySeries(chart, cd, ChartTypes.PieName);

      if (series != null) {
        chart.getSeries().add(series);
        series.setShowLabel(cd.isShowPlotLabels());
        series.setAttribute(ChartPieType.LABEL_STYLE, ChartPieType.LabelStyle.Inside);

        if (cd.isShowLegends()) {
          ChartLegend            legend      = new ChartLegend();
          ChartLegendItemsBinder itemsBinder = new ChartLegendItemsBinder("{AXISLABEL}");

          legend.setAdapter(new ChartLegendAdapter.PointItemsAdapter(itemsBinder));

          switch(cd.getLegendSide()) {
            case TOP :
              legend.setDock(Dock.Top);

              break;

            case BOTTOM :
              legend.setDock(Dock.Bottom);

              break;

            case LEFT :
              legend.setDock(Dock.Left);

              break;

            default :
              legend.setDock(Dock.Right);

              break;
          }

          legend.setLayoutMode(LayoutMode.Vertical);
          legend.getTextPaint().setAntiAlias(true);
          legend.getTextPaint().setColor(cd.getTextColor(legendLabelColor).getColor());
          cd.getTextFont(legendLabelFont).setupPaint(legend.getTextPaint());
          chart.getLegends().add(legend);
        }
      }
    } else {
      seriesData = ci.seriesData;
      len        = (seriesData == null)
                   ? 0
                   : seriesData.size();

      if (cd.isShowLegends()) {
        ChartLegend legend = new ChartLegend(new ChartLegendAdapter.SeriesItemsAdapter());

        switch(cd.getLegendSide()) {
          case TOP :
            legend.setDock(Dock.Top);

            break;

          case BOTTOM :
            legend.setDock(Dock.Bottom);

            break;

          case LEFT :
            legend.setDock(Dock.Left);

            break;

          default :
            legend.setDock(Dock.Right);

            break;
        }

        legend.setLayoutMode(LayoutMode.Vertical);
        legend.getTextPaint().setAntiAlias(true);
        legend.getTextPaint().setColor(cd.getTextColor(legendLabelColor).getColor());
        cd.getTextFont(legendLabelFont).setupPaint(legend.getTextPaint());
        chart.getLegends().add(legend);
      }

      for (int i = 0; i < len; i++) {
        SeriesData data = seriesData.get(i);

        series = createSeries(data, chart, area, cd, thickness);
        chart.getSeries().add(series);
      }

      if (cd.getBackgroundColor() != null) {
        Utils.setBackgroundColor(chart, cd.getBackgroundColor());
      }

      if (cd.getTitle() != null) {}
    }

    List<ChartDataItem> markers = cd.getRangeMarkers();

    len = (markers == null)
          ? 0
          : markers.size();

    for (int i = 0; i < len; i++) {
      area.getDefaultYAxis().getStripLines().add(createRangeMarker(chart, markers.get(i)));
    }

    markers = cd.getDomainMarkers();
    len     = (markers == null)
              ? 0
              : markers.size();

    for (int i = 0; i < len; i++) {
      area.getDefaultXAxis().getStripLines().add(createDomainMarker(chart, markers.get(i), domainType == TYPE_DATE));
    }
  }

  protected Marker getMarker(int index, ArrayList<Marker> markers) {
    int    oindex = index;
    Marker s;

    index = index % 4;

    while(markers.size() <= index) {
      switch(index) {
        case 0 :
          s = new Marker(new OvalShape());

          break;

        case 1 :
          s = new Marker(new RectShape());

          break;

        case 2 : {
          Path p = new Path();

          p.moveTo(0, 2);
          p.lineTo(2, 4);
          p.lineTo(4, 2);
          p.lineTo(2, 0);
          p.lineTo(0, 2);
          s = new Marker(new PathShape(p, 4, 4));

          break;
        }

        default : {
          Path p = new Path();

          p.moveTo(0, 1);
          p.lineTo(2, 4);
          p.lineTo(4, 1);
          p.lineTo(0, 1);
          s = new Marker(new PathShape(p, 4, 4));
        }
      }

      markers.add(s);
    }

    s = markers.get(index);

    if (oindex > index) {
      s = new Marker(s.getShape());
    }

    return s;
  }

  protected Marker getMarker(int index) {
    if (markers == null) {
      markers = new ArrayList<Marker>();
    }

    return getMarker(index, markers);
  }

  private Drawable checkDrawable(Drawable d) {
    if (d instanceof PainterDrawable) {
      // do this so the the drawable doesn't cause and infinite repaint loop
      ((PainterDrawable) d).setAllowInvalidateDrawable(false);
    }

    return d;
  }

  private void configurePoint(ChartViewEx chart, ChartPoint point, ChartDataItem item) {
    if ((item.getComponentPainter() != null) || (item.getBackground() != null)) {
      point.setBackDrawable(PainterUtils.getDrawable(item, chart));
    }
  }

  private ChartAxisStripLine createDomainMarker(ChartViewEx chart, ChartDataItem item, boolean dateType) {
    ChartAxisStripLine line = new ChartAxisStripLine();

    item.setChartLinkedData(new ItemInfo(line));

    return updateStripLine(chart, line, item);
  }

  private ChartAxisStripLine createRangeMarker(ChartViewEx chart, ChartDataItem item) {
    ChartAxisStripLine line = new ChartAxisStripLine();

    item.setChartLinkedData(new ItemInfo(line));

    return updateStripLine(chart, line, item);
  }

  private void customizeAxisEx(com.appnativa.rare.ui.chart.ChartAxis ai, ChartAxis axis, Integer lineColor) {
    if (ai.getForeground() != null) {
      axis.getLinePaint().setColor(ai.getForeground().getColor());
    } else if (lineColor != null) {
      axis.getLinePaint().setColor(lineColor);
    }

    axis.getTitlePaint().setColor(getAxisTitleColor(ai).getColor());
    axis.getLabelPaint().setColor(getAxisLabelColor(ai).getColor());
    getAxisTitleFont(ai).setupPaint(axis.getTitlePaint());
    getAxisLabelFont(ai).setupPaint(axis.getLabelPaint());
    axis.setVisible(ai.isVisible());
    axis.setTitle(ai.getLabel());
    axis.setShowLabels(ai.isLabelsVisible());

    if (ai.getAngle() != 0) {
      axis.setLabelAngle(ai.getAngle());
    }

    if (ai.getIncrement() > 0) {
      axis.getScale().setInterval(ai.getIncrement());
    }
  }

  private void customizeNumberAxis(ChartDefinition cd, com.appnativa.rare.ui.chart.ChartAxis ai, ChartAxis axis,
                                   Integer lineColor, boolean rangeOnly) {
    if (!rangeOnly) {
      customizeAxisEx(ai, axis, lineColor);
    }

    ChartInfo ci     = (ChartInfo) cd.getChartHandlerInfo();
    double[]  bounds = null;

    if (cd.getSeriesCount() > 0) {
      if (ai.isRangeAxis()) {
        bounds = ci.yAxisValues;
      } else {
        bounds = ci.xAxisValues;
      }
    }

    if (bounds != null) {
      axis.getScale().setRange(bounds[0], bounds[1]);
      axis.getScale().setInterval(bounds[2]);
    } else {
      RenderableDataItem u   = ai.getUpperBounds();
      RenderableDataItem l   = ai.getLowerBounds();
      double             inc = ai.getIncrement();

      if (u != null) {
        if (l != null) {
          axis.getScale().setRange(l.doubleValue(), u.doubleValue());
        } else {
          axis.getScale().setMaximum(u.doubleValue());
        }
      } else if (l != null) {
        axis.getScale().setMinimum(l.doubleValue());
      }

      if (inc > 0) {
        axis.getScale().setInterval(inc);
      }
    }
  }

  private ChartAxisStripLine updateStripLine(ChartViewEx chart, ChartAxisStripLine line, ChartDataItem item) {
    double lower = item.doubleValue();
    double upper = item.getDomainDouble();

    line.setStart(lower);
    line.setEnd(upper);

    if ((item.getComponentPainter() == null) && (item.getBorder() == null)) {
      if (item.getBackground() != null) {
        line.setBackground(item.getBackground().getDrawable());
      }

      UIColor c = Platform.getUIDefaults().getColor("Rare.Chart.markerBackground");

      if (c != null) {
        line.setBackground(c.getDrawable());
      }
    } else {
      Drawable d = PainterUtils.getDrawable(item, chart);

      if (d != null) {
        line.setBackground(d);
      }
    }

    String s = item.getTitle();

    if (s != null) {
      line.setText(s);
    }

    return line;
  }

  private ChartAxis getAxis(ChartDefinition cd, boolean range) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    if (info.chart == null) {
      return null;
    }

    ChartViewEx chart = info.chart;

    if (chart.getChart().getAreas().size() == 0) {
      return null;
    }

    if (range) {
      return chart.getChart().getAreas().get(0).getDefaultYAxis();
    } else {
      return chart.getChart().getAreas().get(0).getDefaultXAxis();
    }
  }

  private String getChartType(ChartDefinition cd, ChartDefinition.ChartType type) {
    switch(type) {
      case LINE :
        return ChartTypes.LineName;

      case SPLINE :
        return ChartTypes.SplineName;

      case SPLINE_AREA :
        return ChartTypes.SplineAreaName;

      case BAR :
        return cd.isVertical()
               ? ChartTypes.ColumnName
               : ChartTypes.BarName;

      case STACKED_BAR :
        return cd.isVertical()
               ? ChartTypes.StackedColumnName
               : ChartTypes.StackedBarName;

      case RANGE_AREA :
        return ChartTypes.RangeAreaName;

      case RANGE_BAR :
        return ChartTypes.RangeColumnName;

      case AREA :
        return ChartTypes.AreaName;

      case PIE :
        return ChartTypes.PieName;

      default :
        throw new IllegalArgumentException("unsupported chart type");
    }
  }

  public class ChartPanelComponent extends Container {
    ChartViewEx    chart;
    LinearLayoutEx panel;
    LabelView      titleView;
    int            checkSize;

    public ChartPanelComponent(ChartDefinition cd, ChartViewEx chart, RenderableDataItem title) {
      set(cd, chart, title);
    }

    @Override
    public void setBounds(float x, float y, float w, float h) {
      checkSize++;
      super.setBounds(x, y, w, h);
    }

    public void set(ChartDefinition cd, ChartViewEx chart, RenderableDataItem title) {
      checkSize  = 1;
      this.chart = chart;

      if ((title == null) || (title.getValue() == null)) {
        titleView = null;
        panel     = null;
        setView(chart);
      } else {
        LabelView tv = titleView;

        if (tv == null) {
          tv = titleView = new LabelView(chart.getContext());

          ActionComponent ac = new ActionComponent(tv);

          configureTitle(ac, title);
        }

        tv.setText(title.toCharSequence());

        if (panel == null) {
          panel = new LinearLayoutEx(chart.getContext());
        }

        panel.removeAllViews();
        tv.setGravity(Gravity.CENTER);

        switch(cd.getTitleSide()) {
          case BOTTOM :
            panel.setOrientation(LinearLayoutEx.VERTICAL);
            panel.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            panel.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            break;

          case LEFT :
            tv.setOrientation(Orientation.VERTICAL_UP);
            panel.setOrientation(LinearLayoutEx.HORIZONTAL);
            panel.addView(tv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            panel.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            break;

          case RIGHT :
            tv.setOrientation(Orientation.VERTICAL_DOWN);
            panel.setOrientation(LinearLayoutEx.HORIZONTAL);
            panel.addView(tv);
            panel.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            break;

          default :
            panel.setOrientation(LinearLayoutEx.VERTICAL);
            panel.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            panel.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            break;
        }

        setView(panel);
      }
    }

    @Override
    public void setBackground(UIColor bg) {
      super.setBackground(bg);

      if ((panel != null) && (bg != null)) {
        if (bg.isSimpleColor()) {
          panel.setBackgroundColor(bg.getColor());
        } else {
          panel.setBackgroundDrawable(bg.getDrawable());
        }
      }
    }

    public ChartViewEx getChart() {
      return chart;
    }
  }


  static class ChartInfo extends aChartInfo {
    ChartViewEx chart;
    ChartType   chartType;
    boolean     showPointLabels;

    public ChartInfo(ChartViewEx chart) {
      this.chart = chart;
    }

    public void setShowPointLabels(boolean show) {
      super.setShowPointLabels(show);
      showPointLabels = show;
    }

    @Override
    public void reset() {
      super.reset();
      chartType = null;
    }

    /**
     * Constructs a new instance
     */
    ChartInfo() {}
  }


  static class ItemInfo {
    final Object chartItem;
    int          end;
    int          start;

    public ItemInfo(Object chartItem) {
      this.chartItem = chartItem;
    }
  }


  public class CustomAdapter implements LabelsAdapter {
    ChartViewEx     chart;
    LabelData[]     labelData;
    ChartDefinition chartDefinition;
    boolean         isNumber;
    boolean         domain;
    boolean         updatingTickMarks;
    boolean         labelsUpdated;
    int             requestRelayoutCount;
    int             oldHeight;
    int             oldWidth;
    int             oldAngle;

    public CustomAdapter(ChartViewEx chart, ChartDefinition cd, LabelData[] labelData) {
      this.chart           = chart;
      this.labelData       = labelData;
      this.domain          = true;
      this.isNumber        = false;
      this.chartDefinition = cd;
      this.oldAngle        = cd.getDomainAxis().getAngle();
    }

    public CustomAdapter(ChartViewEx chart, ChartDefinition cd, boolean domain) {
      this.chart           = chart;
      this.chartDefinition = cd;
      this.domain          = domain;
      labelsUpdated        = !getAxis(chartDefinition, !domain).isVisible();
      isNumber             = getDataType(domain
                                         ? cd.getDomainAxis()
                                         : cd.getRangeAxis()) == TYPE_NUMBER;
    }

    protected void updateNonCategoryLabelsEx(int width, ChartAxis axis, List<Label> output) {
      ChartAxisScale scale      = axis.getScale();
      double         startValue = scale.getRealMinimum();
      double         endValue   = scale.getRealMaximum();
      double         increment  = scale.getVisibleInterval();
      LabelData[]    labels;

      if (isNumber) {
        labels = createNumericLabelsData(chartDefinition, width, startValue, endValue, increment, domain, tickSize);
      } else {
        labels = createLabelData(chartDefinition, domain, width, startValue, endValue, increment);
      }

      updateLabels(width, labels, output);
    }

    @Override
    public void updateLabels(ChartAxis axis, List<Label> output) {
      ChartArea area   = chart.getAreas().get(0);
      Rect      r      = area.getSeriesBounds();
      int       width  = r.width();
      int       height = r.height();

      if (width > 10) {
        if (labelData == null) {
          updateNonCategoryLabelsEx(domain
                                    ? width
                                    : height, axis, output);
        } else {
          int angle = chartDefinition.getDomainAxis().getAngle();

          if (angle != oldAngle) {
            oldAngle = angle;

            UIFontMetrics fm = UIFontMetrics.getMetrics(getAxisLabelFont(chartDefinition.getDomainAxis()));

            remeasureLabels(labelData, fm, angle);
          }

          updateLabels(width, labelData, output);
        }

        labelsUpdated = true;
      }
    }

    protected boolean updateTickMarks(ChartArea area, int width, int height) {
      Rect r = area.getBounds();

      width  = r.width();
      height = r.height();

      if (updatingTickMarks || ((oldHeight == height) && (oldWidth == width))) {
        return false;
      }

      updatingTickMarks = true;
      oldHeight         = height;
      oldWidth          = width;

      boolean   updated = false;
      ChartInfo ci      = (ChartInfo) chartDefinition.getChartHandlerInfo();

      try {
        if ((domain && ci.xIncrementFixed) || (!domain && ci.yIncrementFixed)) {
          return false;
        }

        double range[] = domain
                         ? ci.xAxisValues
                         : ci.yAxisValues;

        if (range != null) {
          double    increment = calculateIncrement(domain
                  ? width
                  : height, tickSize, range[0], range[1], range[2]);
          ChartAxis axis      = domain
                                ? area.getDefaultXAxis()
                                : area.getDefaultYAxis();

          axis.getScale().setInterval(increment);
          updated = true;
        }
      } finally {
        updatingTickMarks = false;
      }

      return updated;
    }

    protected void updateLabels(float width, LabelData[] input, List<Label> output) {
      output.clear();

      int len = input.length;

      if (len > 0) {
        int padding = LABELS_PADDING;

        if (domain) {
          if (chartDefinition.getDomainAxis().getAngle() == 0) {
            padding += ScreenUtils.PLATFORM_PIXELS_16;
            padding += ScreenUtils.PLATFORM_PIXELS_4;
          } else {
            padding = Math.max(padding - ScreenUtils.PLATFORM_PIXELS_8, 0);
          }
        }

        int    mod = getLabelsMod(input, width, padding);
        double pos = input[0].position;

        output.add(new Label(input[0].label, pos));

        for (int i = 1; i < len; i++) {
          pos = input[i].position;

          if ((mod == 0) || (i % mod == 0)) {
            output.add(new Label(input[i].label, pos));
          } else if (!domain) {
            output.add(new Label("", pos));
          }
        }
      }
    }

    public void layoutChanged() {
      if ((chart == null) || (chart.getAreas() == null) || chart.getAreas().isEmpty()) {
        return;
      }

      if (labelsUpdated) {
        int width  = chart.getWidth();
        int height = chart.getHeight();

        if ((width < 10) || (height < 10)) {
          return;
        }

        ChartArea area = chart.getAreas().get(0);

        if (chartDefinition != null) {
          if (updateTickMarks(area, width, height)) {
            return;
          }
        }

        ChartAxis axis = domain
                         ? area.getDefaultXAxis()
                         : area.getDefaultYAxis();

        axis.getScale().setInterval(axis.getScale().getInterval());
      } else {
        requestRelayoutCount++;

        if (requestRelayoutCount < 5) {
          Platform.invokeLater(new Runnable() {
            @Override
            public void run() {
              if (chart != null) {
                chart.requestLayout();
              }
            }
          });
        }
      }
    }
  }


  static class Marker extends ShapeDrawable {
    UIColor fillColor;
    int     oldHeight;
    int     oldWidth;
    Shape   shape;
    int     size;
    UIColor strokeColor;
    int     type;

    public Marker(Shape s) {
      super(s);
    }

    @Override
    public void draw(Canvas canvas) {
      Paint p = getPaint();

      if (fillColor != null) {
        p.setColor(fillColor.getColor());
        p.setStyle(Paint.Style.FILL);
        super.draw(canvas);
      }

      if (strokeColor != null) {
        p.setColor(strokeColor.getColor());
        p.setStyle(Paint.Style.STROKE);
        super.draw(canvas);
      }
    }
  }
}
