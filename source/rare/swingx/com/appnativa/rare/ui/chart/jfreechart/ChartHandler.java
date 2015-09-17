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

package com.appnativa.rare.ui.chart.jfreechart;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JToolTipEx;
import com.appnativa.rare.platform.swing.ui.view.iView;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.chart.ChartAxis;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.ChartDefinition.ChartType;
import com.appnativa.rare.ui.chart.PlotInformation;
import com.appnativa.rare.ui.chart.PlotInformation.ShapeStyle;
import com.appnativa.rare.ui.chart.aChartHandler;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.DefaultPieDatasetEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.PieCollection;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.PieLabelGenerator;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.PieToolTipLabelGenerator;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.RareBarPainter;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYAreaRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYAreaSplineRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYClusteredBarRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYLine3DRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYLineAndShapeRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYRangeAreaRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYRangeBarRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYSeriesCollectionEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYSplineRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYStackedAreaRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYStackedBarRendererEx;
import com.appnativa.rare.ui.chart.jfreechart.ChartHelper.XYToolTipLabelGenerator;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPainterSupport;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.aChartViewer;
import com.appnativa.util.SNumber;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.XYAnnotationEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import java.security.InvalidParameterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.border.Border;

/**
 * Chart Handler class for JFreeChart
 *
 * @author Don DeCoteau
 */
public class ChartHandler extends aChartHandler {
  private ArrayList<Shape> baseShapes;
  private int              tickSize;

  /** Creates a new instance of JFreeChartHandler */
  public ChartHandler() {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
  }

  @Override
  public iPlatformComponent createChart(iPlatformComponent chartComponent, final ChartDefinition cd) {
    ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

    if (ci != null) {
      ci.dispose();
    }

    ci = new ChartInfo();
    cd.setChartHandlerInfo(ci);

    ChartPanelEx chartPanel = (ChartPanelEx) ((chartComponent == null)
            ? null
            : chartComponent.getView());

    if (chartPanel == null) {
      chartPanel     = createChartPanel(null, cd);
      chartComponent = new Container(chartPanel);

      if (chartForeground != null) {
        chartComponent.setForeground(chartForeground);
      }

      if (chartFont != null) {
        chartComponent.setFont(chartFont);
      }

      if (chartBackground != null) {
        chartComponent.setBackground(chartBackground);
      }
    }

    ci.popularSeriesDataAndCaluclateRanges(this, cd);

    JFreeChart chart = createCharts(chartPanel, cd);

    chartPanel.setChart(chart);
    chart.setAntiAlias(true);
    chart.setBackgroundPaint(null);
    chart.setBorderVisible(false);

    if (!cd.isAllowZooming()) {
      chartPanel.setRangeZoomable(false);
      chartPanel.setDomainZoomable(false);
    }

    ci.chart = chart;
    ChartHelper.setChartTitle(chart, cd.getTitle());

    if (cd.isShowLegends()) {
      LegendTitle l = new LegendTitle(chart.getPlot());

      l.setItemPaint(cd.getTextColor(legendLabelColor));
      l.setItemFont(cd.getTextFont(legendLabelFont));

      switch(cd.getLegendSide()) {
        case TOP :
          l.setPosition(RectangleEdge.TOP);

          break;

        case BOTTOM :
          l.setPosition(RectangleEdge.BOTTOM);

          break;

        case LEFT :
          l.setPosition(RectangleEdge.LEFT);

          break;

        default :
          l.setPosition(RectangleEdge.RIGHT);

          break;
      }

      chart.addSubtitle(l);
    }

    ChartFactory.getChartTheme().apply(chart);
    ((ChartInfo) cd.getChartHandlerInfo()).chartPanel = chartPanel;

    if ((cd.getSeriesCount() > 0) && (chartPanel.getHeight() > 0)) {
      chartPanel.updateTickmarks(chartPanel.getWidth(), chartPanel.getHeight());
    }

    return chartComponent;
  }

  public LabelData[] createNonCategoryLabels(ChartDefinition cd, double width, NumberAxis axis, boolean domain,
          double widthDivisor) {
    LabelData[] labels;
    double      startValue = axis.getLowerBound();
    double      endValue   = axis.getUpperBound();
    double      increment  = axis.getTickUnit().getSize();

    if (domain) {
      labels = createLabelData(cd, domain, width, startValue, endValue + increment, increment);

      if (labels.length > 0) {
        labels[labels.length - 1].label = "";
      }
    } else {
      labels = createNumericLabelsData(cd, width, startValue, endValue, increment, domain, widthDivisor);
    }

    return labels;
  }

  @Override
  public iPlatformComponent dataChanged(iPlatformComponent chartComponent, ChartDefinition cd) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();
    boolean   show = true;

    if ((info.seriesData != null) &&!info.seriesData.isEmpty()) {
      show = info.seriesData.get(0).showPointLabels;
    }

    iPlatformComponent c = createChart(chartComponent, cd);

    info = (ChartInfo) cd.getChartHandlerInfo();
    info.setShowPointLabels(show);

    return c;
  }

  @Override
  public UIImage getChartImage(iPlatformComponent chartPanel, ChartDefinition cd) {
    if (chartPanel == null) {
      return null;
    }

    ChartPanel cp = (ChartPanel) chartPanel.getView();

    return new UIImage(cp.getChart().createBufferedImage(cp.getWidth(), cp.getHeight()));
  }

  @Override
  public UIDimension getPlotAreaSize(iPlatformComponent chartPanel, ChartDefinition cd) {
    if (chartPanel == null) {
      return new UIDimension();
    }

    ChartPanelEx cp = (ChartPanelEx) chartPanel.getView();

    return cp.getPlotAreaSize();
  }

  @Override
  public void itemChanged(iPlatformComponent chartComponent, ChartDefinition cd, ChartDataItem item) {
    ChartInfo  info = (ChartInfo) cd.getChartHandlerInfo();
    SeriesData data = info.updateSeries(cd, item);
    Plot       plot = info.chart.getPlot();

    if ((data == null) || (plot instanceof PiePlot)) {
      createChart(chartComponent, cd);

      return;
    }

    int series = data.seriesIndex;

    try {
      if (plot instanceof XYPlot) {
        XYPlot    xyplot = (XYPlot) plot;
        int       sn     = series;
        XYDataset set;

        if (xyplot.getDatasetCount() == 1) {
          set = xyplot.getDataset(0);
        } else {
          set = xyplot.getDataset(series);
          sn  = 0;
        }

        if (set.getSeriesCount() > sn) {
          if (set instanceof XYSeriesCollection) {
            XYSeries s = ((XYSeriesCollection) set).getSeries(sn);

            if (s != null) {
              s.clear();
              ChartHelper.populateXYSeries(s, data);
            }
          }
        }
      }
    } catch(Exception e) {
      Platform.ignoreException("can't update chart series", e);
      createChart(chartComponent, cd);
    }
  }

  @Override
  public ChartDataItem itemFromLocation(ChartDefinition cd, int x, int y) {
    ChartDataItem item = null;

    if (cd != null) {
      ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

      if (info.chartPanel != null) {
        item = itemFromEntity(cd, info.chartPanel.getEntityForPoint(x, y), false);
      }
    }

    return item;
  }

  @Override
  public void setDomainLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    Axis a = getAxis(cd, false);

    if (a != null) {
      a.setLabel(cd.getDomainAxis().getLabel());
    }
  }

  @Override
  public void setDomainLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd) {
    setLabelAngel(chartPanel, cd, false);
  }

  @Override
  public void setRangeLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    Axis a = getAxis(cd, true);

    if (a != null) {
      a.setLabel(cd.getRangeAxis().getLabel());
    }
  }

  @Override
  public void setRangeLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd) {
    setLabelAngel(chartPanel, cd, true);
  }

  @Override
  public void setShowDomainLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    Axis a = getAxis(cd, false);

    if (a != null) {
      a.setTickLabelsVisible(cd.getDomainAxis().isLabelsVisible());
    }
  }

  @Override
  public void setShowPlotValues(iPlatformComponent chartPanel, ChartDefinition cd) {
    boolean   show = cd.isShowPlotLabels();
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    info.setShowPointLabels(show);

    Plot plot = info.chart.getPlot();

    if (plot instanceof XYPlot) {
      ((XYPlot) plot).getRenderer().setBaseItemLabelsVisible(show, true);
    } else if (plot instanceof PiePlot) {
      PiePlot p = (PiePlot) plot;

      if (show) {
        p.setLabelGenerator((PieSectionLabelGenerator) info.labelGenerator);
      } else {
        info.labelGenerator = p.getLabelGenerator();
        p.setLabelGenerator(null);
      }
    }
  }

  @Override
  public void setShowRangeLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    Axis a = getAxis(cd, true);

    if (a != null) {
      a.setTickLabelsVisible(cd.getRangeAxis().isLabelsVisible());
    }
  }

  @Override
  public void unzoom(iPlatformComponent chartComponent) {
    JComponent chartPanel = chartComponent.getView();

    ((ChartPanel) chartPanel).restoreAutoBounds();
  }

  @Override
  public void updateRangeAxis(iPlatformComponent chartComponent, ChartDefinition cd) {
    Axis a = getAxis(cd, true);

    customizeAxisEx(cd, cd.getRangeAxis(), a);
  }

  @Override
  public void updatesCompleted(iPlatformComponent chartComponent, ChartDefinition cd) {
    createChart(chartComponent, cd);
  }

  @Override
  public void updatesPending(iPlatformComponent chartComponent, ChartDefinition cd) {}

  protected ChartPanelEx createChartPanel(JFreeChart chart, final ChartDefinition cd) {
    ChartPanelEx cp = new ChartPanelEx(chart, cd.isScrollWheelZoomingAllowed()) {
      @Override
      protected JPopupMenu createPopupMenu(boolean properties, boolean copy, boolean save, boolean print,
              boolean zoom) {
        JPopupMenu   menu;
        aChartViewer cv = cd.getChartViewer();

        menu = super.createPopupMenu(false, cd.getChartViewer().canCopy(), cv.canSave(), cv.canPrint(),
                                     cd.isAllowZooming());

        UIPopupMenu sm = cv.getPopupMenu();

        if (sm != null) {
          menu.addSeparator();
          sm.copyTo(menu);
        }

        return menu;
      }
    };

    cp.maxZoom = cd.getMaxZoomFactor();

    if (cd.wantsMouseEvents()) {
      cp.addChartMouseListener(new ChartMouseHandler(cd));
    }

    return cp;
  }

  protected JFreeChart createCharts(ChartPanel chartPanel, ChartDefinition cd) {
    List<DataSetValue> datasets = createDataSets(cd);
    int                len      = datasets.size();
    UIFont             f        = getAxisLabelFont(cd.getRangeAxis());
    UIFontMetrics      fm       = UIFontMetrics.getMetrics(f);

    tickSize = (int) fm.getHeight();

    if (cd.getChartType() == ChartType.PIE) {
      PiePlot       plot;
      PieCollection pie = (PieCollection) datasets.get(0).dataset;

      if (cd.isDraw3D()) {
        plot = new PiePlot3D(pie);
      } else {
        plot = new PiePlot(pie);
      }

      customizePiePlot(cd, plot, pie);

      return new JFreeChart(null, getChartFont(), plot, false);
    }

    ChartInfo  ci     = (ChartInfo) cd.getChartHandlerInfo();
    XYPlot     xyplot = null;
    NumberAxis yAxis  = cd.isDraw3D()
                        ? new NumberAxis3DEx(cd, true, cd.getRangeLabel())
                        : new NumberAxisEx(cd, false, cd.getRangeLabel());

    yAxis.setAutoRangeIncludesZero(false);
    yAxis.setAutoRange(false);

    NumberAxis xAxis;

    if (ci.categoryDomain) {
      LabelData[] labels = ci.createLabelData(cd, fm, true);

      xAxis = cd.isDraw3D()
              ? new NumberAxis3DEx(cd, true, cd.getDomainLabel())
              : new NumberAxisEx(cd, labels, cd.getDomainLabel());
    } else {
      xAxis = cd.isDraw3D()
              ? new NumberAxis3DEx(cd, true, cd.getDomainLabel())
              : new NumberAxisEx(cd, true, cd.getDomainLabel());
    }

    xAxis.setAutoRangeIncludesZero(false);
    xAxis.setAutoRange(false);
    xyplot = new XYPlotEx(null, xAxis, yAxis, null);

    for (int i = 0; i < len; i++) {
      DataSetValue dv = datasets.get(i);

      xyplot.setDataset(i, (XYDataset) dv.dataset);

      switch(dv.chartType) {
        case SPLINE :
          xyplot.setRenderer(i, new XYSplineRendererEx(ci.seriesData));

          break;

        case LINE :
          xyplot.setRenderer(i, cd.isDraw3D()
                                ? new XYLine3DRendererEx(ci.seriesData)
                                : new XYLineAndShapeRendererEx(ci.seriesData));

          break;

        case BAR : {
          XYClusteredBarRendererEx renderer = new XYClusteredBarRendererEx(ci.seriesData);

          renderer.setShadowVisible(false);
          xyplot.setRenderer(i, renderer);
        }

        break;

        case STACKED_BAR : {
          StackedXYBarRenderer renderer = new XYStackedBarRendererEx(0.10, ci.seriesData);

          renderer.setDrawBarOutline(false);
          renderer.setShadowVisible(false);
          xyplot.setRenderer(i, renderer);
        }

        break;

        case RANGE_AREA :
          xyplot.setRenderer(i, new XYRangeAreaRendererEx(ci.seriesData));

          break;

        case RANGE_BAR :
          xyplot.setRenderer(i, new XYRangeBarRendererEx(ci.seriesData));

          break;

        case STACKED_AREA :
          XYStackedAreaRendererEx renderer = new XYStackedAreaRendererEx(ci.seriesData);

          renderer.setOutline(false);
          xyplot.setRenderer(i, renderer);

          break;

        case SPLINE_AREA :
        case AREA :
          XYItemRenderer r;

          if (dv.chartType == ChartType.SPLINE_AREA) {
            r = new XYAreaSplineRendererEx(ci.seriesData);
          } else {
            r = new XYAreaRendererEx(ci.seriesData);
          }

          xyplot.setRenderer(r);

          break;

        default :
          throw new InvalidParameterException("Unsupported chart type");
      }
    }

    customizeXYPlot(chartPanel, cd, xyplot);

    JFreeChart chart = new JFreeChart(null, getChartFont(), xyplot, false);

    return chart;
  }

  protected List<DataSetValue> createDataSets(ChartDefinition cd) {
    ChartInfo        ci         = (ChartInfo) cd.getChartHandlerInfo();
    List<SeriesData> seriesData = ci.seriesData;
    int              len        = (seriesData == null)
                                  ? 0
                                  : seriesData.size();
    ChartType        ct;

    if (len == 0) {
      ct = cd.getChartType();

      switch(ct) {
        case PIE :
          return Arrays.asList(new DataSetValue(ct, new DefaultPieDatasetEx()));

        case RANGE_BAR :
        case RANGE_AREA :
        case STACKED_BAR :
        case STACKED_AREA :
        case AREA :
        case BAR :
        case LINE :
        case SPLINE :
        case SPLINE_AREA : {
          return Arrays.asList(new DataSetValue(ct, new XYSeriesCollectionEx()));
        }

        default :
          throw new InvalidParameterException("Unsupported chart type:" + ct.toString());
      }
    }

    boolean              allSameType = areAllTheSameType(cd);
    List<DataSetValue>   list        = new ArrayList<DataSetValue>(len);
    XYSeriesCollectionEx xysc        = null;

    for (int i = 0; i < len; i++) {
      SeriesData data = seriesData.get(i);

      ct = data.chartType;

      switch(ct) {
        case PIE :
          list.add(new DataSetValue(ct, ChartHelper.createPieDataset(cd)));

          if (!allSameType) {
            throw new UnsupportedOperationException("Can include a Pie Chart with other chart types");
          }

          break;

        case RANGE_BAR :
        case RANGE_AREA :
          XYSeriesCollectionEx rxysc = new XYSeriesCollectionEx(true);

          list.add(new DataSetValue(ct, rxysc));
          rxysc.addSeries(ChartHelper.populateXYSeries(null, data));

          break;

        case STACKED_BAR :
        case STACKED_AREA :
        case AREA :
        case BAR :
        case LINE :
        case SPLINE :
        case SPLINE_AREA : {
          if ((xysc == null) ||!allSameType) {
            xysc = new XYSeriesCollectionEx();
            list.add(new DataSetValue(ct, xysc));
          }

          xysc.addSeries(ChartHelper.populateXYSeries(null, data));

          break;
        }

        default :
          throw new InvalidParameterException("Unsupported chart type:" + ct.toString());
      }
    }

    return list;
  }

  protected void customizeBasicPlot(final Plot plot, PlotInformation pi) {
    UIColor bg = null;

    if (pi != null) {
      bg = pi.getBackgroundColor();
      plot.setForegroundAlpha(pi.getForegroundAlpha());

      if (pi.getBackgroundImage() != null) {
        UIImage img = pi.getBackgroundImage();

        if (img.isLoaded()) {
          plot.setBackgroundImage(img.getImage());
        } else {
          iImageObserver io = new iImageObserver() {
            @Override
            public void imageLoaded(UIImage image) {
              try {
                plot.setBackgroundImage(image.getImage());
              } catch(Exception ignore) {}
            }
          };

          if (img.isLoaded(io)) {
            plot.setBackgroundImage(img.getImage());
          }
        }
      }

      UIInsets in = pi.getMargin();

      if (in != null) {
        plot.setInsets(new RectangleInsets(in.top, in.left, in.bottom, in.right));
      }
    }

    if (bg == null) {
      bg = plotBackground;
    }

    if (bg != null) {
      plot.setBackgroundPaint(ChartHelper.getPaint(bg));
    }

    plot.setInsets(new RectangleInsets(12, 12, 12, 12));
  }

  protected void customizeSeriesAttributes(ChartPanel chartPanel, ChartDefinition cd, Plot plot, boolean multiset) {
    List<RenderableDataItem> rows       = cd.getSeries();
    ChartInfo                ci         = (ChartInfo) cd.getChartHandlerInfo();
    List<SeriesData>         seriesData = ci.seriesData;
    int                      len        = (seriesData == null)
            ? 0
            : seriesData.size();

    if (len == 0) {
      return;
    }

    ChartDataItem    di;
    Paint            p = null;
    iPainter         painter;
    BasicStroke      stroke     = null;
    BasicStroke      ostroke    = null;
    RareBarPainter   barPainter = null;
    AbstractRenderer r;
    Object           o;
    UIColor          c;
    PlotInformation  pi = cd.getPlotInformation();

    if (pi != null) {
      float lt = pi.getLineThickness();
      float ot = pi.getOutlineThickness();

      if (lt > 1) {
        stroke = new BasicStroke(lt);
      }

      if (ot > 1) {
        ostroke = (ot == lt)
                  ? stroke
                  : new BasicStroke(ot);
      }
    }

    int n = 0;

    for (int i = 0; i < len; i++) {
      SeriesData data = seriesData.get(i);

      di = (ChartDataItem) rows.get(data.seriesIndex);

      if ((di == null) || di.isEmpty()) {
        continue;
      }

      p = null;
      o = ((XYPlot) plot).getRenderer(multiset
                                      ? i
                                      : 0);

      if (!(o instanceof AbstractRenderer)) {
        continue;
      }

      UIColor fc = data.fillColor;
      UIColor oc = data.outlineColor;

      r       = (AbstractRenderer) o;
      painter = di.getComponentPainter();

      if (painter == null) {
        c = di.getBackground();

        if (c instanceof UIColorShade) {
          painter = ((UIColorShade) c).getBackgroundPainter();

          if (painter instanceof UISimpleBackgroundPainter) {
            p       = painter.getBackgroundColor();
            painter = null;
          }
        } else {
          p = c;
        }
      }

      if ((painter == null) && (p == null)) {
        painter = cd.getRangeAxis().getComponentPainter();

        if (painter == null) {
          c = cd.getRangeAxis().getBackground();

          if (c instanceof UIColorShade) {
            painter = ((UIColorShade) c).getBackgroundPainter();

            if (painter instanceof UISimpleBackgroundPainter) {
              p       = painter.getBackgroundColor();
              painter = null;
            }
          } else {
            p = c;
          }
        }
      }

      if (painter != null) {
        if (r instanceof XYBarRenderer) {
          if (barPainter == null) {
            barPainter = new RareBarPainter(chartPanel);
          }

          XYBarRenderer xr = (XYBarRenderer) r;

          xr.setBarPainter(barPainter);
          p = ChartHelper.getPaint(painter);
        } else if (r instanceof BarRenderer) {
          if (barPainter == null) {
            barPainter = new RareBarPainter(chartPanel);
          }

          BarRenderer xr = (BarRenderer) r;

          xr.setItemMargin(0);
          xr.setBarPainter(barPainter);
          p = ChartHelper.getPaint(painter);
        } else {
          iPlatformPaint pp = painter.getPaint(50, 50);

          p = (pp == null)
              ? null
              : pp.getPaint();
        }
      }

      if (p == null) {
        p = di.getBackground();
      }

      if (p == null) {
        p = getDefaultColor(i);
      }

      n = multiset
          ? 0
          : i;
      r.setSeriesPaint(n, p);

      if (r instanceof XYAreaSplineRendererEx) {
        r.setSeriesFillPaint(n, p);
      }

      float lt = getSeriesLineThickness(di, -1);

      if (lt != -1) {
        r.setSeriesStroke(n, new BasicStroke(lt));
      } else if (stroke != null) {
        r.setSeriesStroke(n, stroke);
      }

      lt = getSeriesOutlineLineThickness(di, -1);

      if (lt != -1) {
        r.setSeriesOutlineStroke(n, ostroke);
      } else if (ostroke != null) {
        r.setSeriesOutlineStroke(n, ostroke);
      }

      if (r instanceof XYLineAndShapeRenderer) {
        ShapeStyle             ss       = getSeriesShapeStyle(pi, di);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;

        switch(ss) {
          case FILLED :
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setUseFillPaint(true);
            renderer.setSeriesFillPaint(n, (fc == null)
                                           ? p
                                           : fc.getPaint());

            break;

          case OUTLINED :
            renderer.setBaseShapesVisible(true);
            renderer.setUseOutlinePaint(true);
            renderer.setDrawOutlines(true);
            renderer.setSeriesOutlinePaint(n, (oc == null)
                                              ? p
                                              : oc.getPaint());

            break;

          case FILLED_AND_OUTLINED :
            renderer.setSeriesOutlinePaint(n, (oc == null)
                                              ? p
                                              : oc.getPaint());
            renderer.setSeriesFillPaint(n, (fc == null)
                                           ? UIColor.WHITE.getPaint()
                                           : fc.getPaint());
            renderer.setBaseShapesVisible(true);
            renderer.setDrawOutlines(true);
            renderer.setBaseShapesFilled(true);
            renderer.setUseFillPaint(true);
            renderer.setUseOutlinePaint(true);

            break;

          default :
            renderer.setBaseShapesVisible(false);

            break;
        }

        if (renderer.getBaseShapesVisible()) {
          renderer.setSeriesShape(n, getDefaultShape(i));
        }
      }
    }
  }

  protected void customizeXYLineAndShapeRenderer(ChartDefinition cd, XYPlot plot, PlotInformation pi) {
    AbstractXYItemRenderer renderer = (AbstractXYItemRenderer) plot.getRenderer();

    if (renderer instanceof XYLineAndShapeRenderer) {
      XYLineAndShapeRenderer xrenderer = (XYLineAndShapeRenderer) renderer;

      if (pi != null) {
        ShapeStyle fs = pi.getShapeStyle();

        if (renderer instanceof XYAreaSplineRendererEx) {
          fs = pi.getShapeStyleEx();
        }

        switch(fs) {
          case FILLED :
            xrenderer.setBaseShapesVisible(true);
            xrenderer.setBaseShapesFilled(true);
            xrenderer.setUseFillPaint(true);

            break;

          case OUTLINED :
            xrenderer.setBaseShapesVisible(true);
            xrenderer.setDrawOutlines(true);
            xrenderer.setUseOutlinePaint(true);

            break;

          case FILLED_AND_OUTLINED :
            xrenderer.setBaseShapesVisible(true);
            xrenderer.setDrawOutlines(true);
            xrenderer.setBaseShapesFilled(true);
            xrenderer.setUseOutlinePaint(true);
            xrenderer.setUseFillPaint(true);

            break;

          default :
            xrenderer.setBaseShapesVisible(false);

            break;
        }
      }

      if (xrenderer.getBaseShapesVisible()) {
        xrenderer.setBaseShape(getDefaultShape(0));
      }
    }

    StandardXYItemLabelGenerator lg = null;

    renderer.setBaseItemLabelsVisible(cd.isShowPlotLabels());
    lg = new XYToolTipLabelGenerator(((ChartInfo) cd.getChartHandlerInfo()).seriesData);

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

    renderer.setBaseItemLabelGenerator(lg);
    rangeAxis.setUpperMargin(0.25);

    if (cd.isShowToolTips()) {
      renderer.setBaseToolTipGenerator((XYToolTipLabelGenerator) lg);
    }
  }

  protected void customizeXYPlot(ChartPanel chartPanel, ChartDefinition cd, XYPlot plot) {
    PlotInformation pi = cd.getPlotInformation();

    customizeBasicPlot(plot, pi);
    plot.clearRangeMarkers();
    plot.clearDomainMarkers();

    PlotOrientation po = cd.isVertical()
                         ? PlotOrientation.VERTICAL
                         : PlotOrientation.HORIZONTAL;

    plot.setOrientation(po);

    boolean showGrid = (pi == null)
                       ? true
                       : pi.isShowGridLines();

    if (showGrid) {
      Color    c      = getGridColor(pi);
      UIStroke stroke = getGridStroke(pi);

      plot.setRangeGridlinePaint(c);
      plot.setDomainGridlinePaint(c);

      Stroke s = SwingHelper.getStroke(stroke);

      plot.setRangeGridlineStroke(s);
      plot.setDomainGridlineStroke(s);
    } else {
      plot.setRangeGridlinesVisible(false);
      plot.setDomainGridlinesVisible(false);
    }

    if (pi != null) {
      Color c = pi.getBorderColor();

      if (c != null) {
        plot.setOutlinePaint(c);
      }
    }

    int angle = cd.getDomainAxis().getAngle();

    if ((angle != 0) && (angle != 180)) {
      plot.getDomainAxis().setLabelAngle(((angle) / 180f) * Math.PI);
    } else {
      plot.getDomainAxis().setLabelAngle(0);
    }

    angle = cd.getRangeAxis().getAngle();

    if ((angle > 0) && (angle != 180)) {
      plot.getRangeAxis().setLabelAngle(((angle) / 180f) * Math.PI);
    } else {
      plot.getRangeAxis().setLabelAngle(0);
    }

    List<ChartDataItem> markers = cd.getRangeMarkers();
    int                 len     = (markers == null)
                                  ? 0
                                  : markers.size();

    for (int i = 0; i < len; i++) {
      plot.addRangeMarker(ChartHelper.createIntervalMarker(markers.get(i)));
    }

    markers = cd.getDomainMarkers();
    len     = (markers == null)
              ? 0
              : markers.size();

    IntervalMarker     marker;
    ChartAxis.TimeUnit tm   = cd.getDomainAxis().getTimeUnit();
    boolean            time = cd.isDateTimeType();

    customizeAxis(cd, cd.getRangeAxis(), (NumberAxis) plot.getRangeAxis());
    customizeAxis(cd, cd.getDomainAxis(), (NumberAxis) plot.getDomainAxis());

    for (int i = 0; i < len; i++) {
      marker = time
               ? ChartHelper.createIntervalMarker(markers.get(i), tm)
               : ChartHelper.createIntervalMarker(markers.get(i));
      plot.addDomainMarker(marker);
    }

    customizeXYLineAndShapeRenderer(cd, plot, pi);
    customizeSeriesAttributes(chartPanel, cd, plot, plot.getDatasetCount() > 1);
    plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
  }

  protected Axis getAxis(ChartDefinition cd, boolean range) {
    ChartInfo info = (ChartInfo) cd.getChartHandlerInfo();

    if (range) {
      return ((XYPlot) info.chart.getPlot()).getRangeAxis();
    } else {
      return ((XYPlot) info.chart.getPlot()).getDomainAxis();
    }
  }

  protected Shape getDefaultShape(int index) {
    if (baseShapes == null) {
      baseShapes = new ArrayList<Shape>();
    }

    return getDefaultShape(index, baseShapes);
  }

  protected Shape getDefaultShape(int index, ArrayList<Shape> markers) {
    Shape s;

    index = index % 4;

    while(markers.size() <= index) {
      switch(index) {
        case 0 :
          s = new RoundRectangle2D.Double(-3, -3, 6, 6, 6, 6);

          break;

        case 1 :
          s = new Rectangle2D.Double(-3, -3, 6, 6);

          break;

        case 2 : {
          s = ShapeUtilities.createDiamond(4);

          break;
        }

        default : {
          s = ShapeUtilities.createUpTriangle(4);
        }
      }

      markers.add(s);
    }

    s = markers.get(index);

    return s;
  }

  @SuppressWarnings("deprecation")
  protected ChartDataItem itemFromEntity(ChartDefinition cd, ChartEntity e, boolean setVisited) {
    if (e instanceof XYItemEntity) {
      XYItemEntity ie = (XYItemEntity) e;

      if (setVisited) {
        cd.setVisitedItem(ie.getSeriesIndex(), ie.getItem());

        return cd.getVisitedItem();
      } else {
        return cd.getSeriesItem(ie.getSeriesIndex(), ie.getItem());
      }
    }

    if (e instanceof PieSectionEntity) {
      PieSectionEntity ie = (PieSectionEntity) e;

      if (setVisited) {
        cd.setVisitedItem(ie.getSectionIndex(), 0);

        return cd.getVisitedItem();
      } else {
        return cd.getSeriesItem(ie.getSectionIndex(), 0);
      }
    }

    if (e instanceof LegendItemEntity) {
      LegendItemEntity ie = (LegendItemEntity) e;

      if (setVisited) {
        cd.setVisitedItem(-1, -1);
      }

      return cd.getSeries(ie.getSeriesIndex());
    }

    if (e instanceof XYAnnotationEntity) {
      XYAnnotationEntity ie = (XYAnnotationEntity) e;

      if (setVisited) {
        cd.setVisitedItem(-1, -1);
      }

      return cd.getAnnotation(ie.getRendererIndex());
    }

    if (setVisited) {
      cd.setVisitedItem(-1, -1);
    }

    return null;
  }

  protected void setLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd, boolean range) {
    Axis a     = getAxis(cd, range);
    int  angle = range
                 ? cd.getRangeAxis().getAngle()
                 : cd.getDomainAxis().getAngle();

    if (a != null) {
      float ang = (float) Math.toRadians(angle);

      if (!SNumber.isEqual(ang, a.getLabelAngle())) {
        a.setLabelAngle(ang);

        LabelData[] labels = null;

        if (a instanceof NumberAxisEx) {
          labels = ((NumberAxisEx) a).categoryLabels;
        } else if (a instanceof NumberAxis3DEx) {
          labels = ((NumberAxis3DEx) a).categoryLabels;
        }

        if (labels != null) {
          UIFont f = getAxisLabelFont(cd.getRangeAxis());

          remeasureLabels(labels, UIFontMetrics.getMetrics(f), angle);
        }
      }
    }
  }

  private void customizeAxis(ChartDefinition cd, ChartAxis ai, NumberAxis axis) {
    customizeAxisEx(cd, ai, axis);

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
      axis.setLowerBound(bounds[0]);
      axis.setUpperBound(bounds[1]);
      axis.setTickUnit(new NumberTickUnit(bounds[2]));
    } else {
      RenderableDataItem u   = ai.getUpperBounds();
      RenderableDataItem l   = ai.getLowerBounds();
      double             inc = ai.getIncrement();

      if (u != null) {
        axis.setUpperBound(u.doubleValue());
      }

      if (l != null) {
        axis.setLowerBound(l.doubleValue());
      }

      if (inc > 0) {
        axis.setTickUnit(new NumberTickUnit(inc));
      }
    }

    axis.setVisible(ai.isVisible());
  }

  private void customizeAxisEx(ChartDefinition cd, ChartAxis ai, Axis axis) {
    axis.setLabelPaint(getAxisTitleColor(ai));
    axis.setTickLabelPaint(getAxisLabelColor(ai));
    axis.setLabelFont(getAxisTitleFont(ai));
    axis.setTickLabelFont(getAxisLabelFont(ai));
    axis.setVisible(ai.isVisible());
    axis.setTickLabelsVisible(ai.isLabelsVisible());
  }

  private void customizePiePlot(ChartDefinition cd, PiePlot plot, PieCollection pie) {
    PlotInformation pi = cd.getPlotInformation();

    plot.setBackgroundPaint(null);
    plot.setOutlineVisible(false);
    customizeBasicPlot(plot, pi);

    List<RenderableDataItem> rows = cd.getSeries();
    int                      len  = (rows == null)
                                    ? 0
                                    : rows.size();
    ChartDataItem            di;
    iComponentPainter        bp;
    Paint                    p = null;

    if (len > 0) {
      rows = rows.get(0).getItems();
      len  = rows.size();
    }

    for (int i = 0; i < len; i++) {
      di = (ChartDataItem) rows.get(i);
      bp = di.getComponentPainter();

      iPlatformPaint pp;

      if (bp == null) {
        p = di.getBackground();
      } else if (bp.getBackgroundPainter() != null) {
        pp = bp.getBackgroundPainter().getPaint(100, 100);
        p  = (pp == null)
             ? null
             : pp.getPaint();
      }

      if (p == null) {
        p = getDefaultColor(i);
      }

      plot.setSectionPaint(di.getDomainValue().toString(), p);
    }

    if (pie.getExplodedPiece() != null) {
      plot.setExplodePercent(pie.getExplodedPiece(), 0.50);
    }

    plot.setLabelBackgroundPaint(null);
    plot.setLabelOutlinePaint(null);
    plot.setLabelShadowPaint(null);

    UIColor fg   = cd.getTextColor(plotLabelColor);
    UIFont  font = cd.getTextFont(plotLabelFont);

    plot.setLabelFont(font);
    plot.setLabelPaint(fg);

    if (cd.isShowPlotLabels()) {
      String format = (pi == null)
                      ? null
                      : pi.getLabelsFormat();

      plot.setLabelGenerator(new PieLabelGenerator(cd, format));
    }

    if (cd.isShowToolTips()) {
      plot.setToolTipGenerator(new PieToolTipLabelGenerator(cd, false));
    }
  }

  boolean areAllTheSameType(ChartDefinition cd) {
    List<RenderableDataItem> rows = cd.getSeries();
    int                      len  = (rows == null)
                                    ? 0
                                    : rows.size();

    if (len == 0) {
      return true;
    }

    ChartType oct = null;

    for (int i = 0; i < len; i++) {
      ChartDataItem series = (ChartDataItem) rows.get(i);
      ChartType     ct     = getSeriesChartType(cd, series);

      if (oct == null) {
        oct = ct;
      } else if (oct != ct) {
        return false;
      }
    }

    return true;
  }

  public static List refreshTicks(LabelData[] labels, TextAnchor anchor, TextAnchor rotationAnchor, double width,
                                  double angle) {
    Tick tick;
    int  len    = (labels == null)
                  ? 0
                  : labels.length;
    List result = new ArrayList((len == 0)
                                ? 1
                                : len);
    int  mod    = getLabelsMod(labels, (float) width, LABELS_PADDING);

    for (int i = 0; i < len; i++) {
      LabelData l = labels[i];

      if ((mod == 0) || (i % mod == 0)) {
        tick = new NumberTick(new Double(l.position), l.label, anchor, rotationAnchor, angle);
      } else {
        tick = new NumberTick(TickType.MINOR, new Double(l.position), "", TextAnchor.TOP_CENTER, TextAnchor.CENTER,
                              0.0);
      }

      result.add(tick);
    }

    return result;
  }

  protected class ChartPanelEx extends ChartPanel implements Scrollable, MouseWheelListener, iPainterSupport, iView {
    int                                 domainZoomScale = 1;
    int                                 maxZoom         = 5;
    int                                 rangeZoomScale  = 1;
    boolean                             paintComponentCalled;
    protected iPlatformComponentPainter componentPainter;
    protected SwingGraphics             graphics;
    protected boolean                   shapedBorder;
    protected AffineTransform           transform;
    private boolean                     trackWidth  = false;
    private boolean                     trackHeight = false;
    boolean                             checkSize   = true;
    double[]                            xAxisValues;
    double[]                            yAxisValues;

    protected ChartPanelEx(JFreeChart chart, boolean wheelscroll) {
      super(chart);

      if (wheelscroll) {
        addMouseWheelListener(this);
      }
    }

    @Override
    public JToolTip createToolTip() {
      return JToolTipEx.createNewToolTip(this);
    }

    public void decreaseZoom() {
      zoomChartAxis(false);
    }

    @Override
    public iPlatformComponentPainter getComponentPainter() {
      return componentPainter;
    }

    @Override
    public Dimension getMinimumSize() {
      return new Dimension(0, 0);
    }

    @Override
    public void getMinimumSize(UIDimension size) {
      size.width  = 0;
      size.height = 0;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
      return getPreferredSize();
    }

    @Override
    public void getPreferredSize(UIDimension size, int maxWidth) {
      size.width  = 0;
      size.height = 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
      int size = visibleRect.width / 4;

      return (size < 10)
             ? 10
             : size;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
      if (trackHeight) {
        return true;
      }

      Component parent = getParent();

      if (parent instanceof JViewport) {
        return parent.getHeight() > getPreferredSize().height;
      }

      return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
      if (trackWidth) {
        return true;
      }

      Component parent = getParent();

      if (parent instanceof JViewport) {
        return parent.getWidth() > getPreferredSize().width;
      }

      return false;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
      return 10;
    }

    @Override
    public AffineTransform getTransformEx() {
      return transform;
    }

    public void increaseZoom() {
      zoomChartAxis(true);
    }

    @Override
    public boolean isOpaque() {
      return false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      if (e.getScrollType() != MouseWheelEvent.WHEEL_UNIT_SCROLL) {
        return;
      }

      if (e.getWheelRotation() < 0) {
        increaseZoom();
      } else {
        decreaseZoom();
      }
    }

    @Override
    public void paint(Graphics g) {
      if ((getWidth() > 16) && (getHeight() > 16)) {
        paintComponentCalled = false;

        Graphics2D      g2 = (Graphics2D) g;
        AffineTransform tx = g2.getTransform();

        if (transform != null) {
          g2.transform(transform);
        }

        graphics = SwingGraphics.fromGraphics(g2, this, graphics);
        super.paint(g2);

        if (tx != null) {
          g2.setTransform(tx);
        }

        graphics.clear();
      }
    }

    @Override
    public void paintComponent(Graphics g) {
      paintComponentCalled = true;

      iPlatformComponentPainter cp     = getComponentPainter();
      float                     height = getHeight();
      float                     width  = getWidth();

      if (cp != null) {
        cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
      }

      super.paintComponent(g);
    }

    @Override
    public void restoreAutoBounds() {
      super.restoreAutoBounds();
      rangeZoomScale  = 1;
      domainZoomScale = 1;
    }

    @Override
    public void setBorder(Border border) {
      super.setBorder(border);
      shapedBorder = false;

      if (border instanceof iPlatformBorder) {
        shapedBorder = !((iPlatformBorder) border).isRectangular();
      }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
      setMaximumDrawHeight(height);
      setMaximumDrawWidth(width);
      setMinimumDrawWidth(width);
      setMinimumDrawHeight(height);
      updateTickmarks(width, height);
      super.setBounds(x, y, width, height);
    }

    @Override
    public void setComponentPainter(iPlatformComponentPainter cp) {
      this.componentPainter = cp;
    }

    @Override
    public void setTransformEx(AffineTransform tx) {
      transform = tx;
    }

    public UIDimension getPlotAreaSize() {
      Rectangle2D r = getScreenDataArea();

      return new UIDimension(r.getWidth(), r.getHeight());
    }

    public void updateTickmarks(int width, int height) {
      NumberAxis a = getNumberAxis(getChart().getPlot(), true);

      if (a instanceof NumberAxisEx) {
        ((NumberAxisEx) a).updateTickMarks(width, height);
      }

      if (a instanceof NumberAxis3DEx) {
        ((NumberAxis3DEx) a).updateTickMarks(width, height);
      }

      a = getNumberAxis(getChart().getPlot(), false);

      if (a instanceof NumberAxisEx) {
        ((NumberAxisEx) a).updateTickMarks(width, height);
      }

      if (a instanceof NumberAxis3DEx) {
        ((NumberAxis3DEx) a).updateTickMarks(width, height);
      }
    }

    @Override
    public void zoomInDomain(double x, double y) {
      if (domainZoomScale + 1 > maxZoom) {
        return;
      }

      domainZoomScale++;
      super.zoomInDomain(x, y);
    }

    @Override
    public void zoomInRange(double x, double y) {
      if (rangeZoomScale + 1 > maxZoom) {
        return;
      }

      rangeZoomScale++;
      super.zoomInRange(x, y);
    }

    @Override
    public void zoomOutDomain(double x, double y) {
      if (domainZoomScale - 1 < -maxZoom) {
        return;
      }

      domainZoomScale--;
      super.zoomOutDomain(x, y);
    }

    @Override
    public void zoomOutRange(double x, double y) {
      if (rangeZoomScale - 1 < -maxZoom) {
        return;
      }

      rangeZoomScale--;
      super.zoomOutRange(x, y);
    }

    @Override
    protected void paintBorder(Graphics g) {
      if (componentPainter == null) {
        super.paintBorder(g);
      }
    }

    @Override
    protected void paintChildren(Graphics g) {
      iPlatformComponentPainter cp     = getComponentPainter();
      float                     height = getHeight();
      float                     width  = getWidth();

      if (!paintComponentCalled && (cp != null)) {
        cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, false);
      }

      super.paintChildren(graphics.getGraphics());

      if (cp != null) {
        cp.paint(graphics, 0, 0, width, height, iPainter.HORIZONTAL, true);
      }
    }

    private void zoomChartAxis(boolean increase) {
      int width  = getMaximumDrawWidth() - getMinimumDrawWidth();
      int height = getMaximumDrawHeight() - getMinimumDrawWidth();

      if (increase) {
        zoomInBoth(width / 2, height / 2);
      } else {
        zoomOutBoth(width / 2, height / 2);
      }
    }

    NumberAxis getNumberAxis(Plot p, boolean range) {
      if (p instanceof XYPlot) {
        ValueAxis a = range
                      ? ((XYPlot) p).getRangeAxis()
                      : ((XYPlot) p).getDomainAxis();

        if (a instanceof NumberAxis) {
          return (NumberAxis) a;
        }
      }

      return null;
    }
  }


  protected static class DataSetValue {
    public ChartType chartType;
    public Object    dataset;

    DataSetValue(ChartType ct, Object set) {
      chartType = ct;
      dataset   = set;
    }
  }


  static class ChartInfo extends aChartInfo {
    JFreeChart chart;
    ChartPanel chartPanel;
    Object     labelGenerator;

    public ChartInfo(JFreeChart chart) {
      this.chart = chart;
    }

    /**
     * Constructs a new instance
     */
    ChartInfo() {}
  }


  class ChartMouseHandler implements ChartMouseListener {
    ChartDefinition chartDefinition;

    public ChartMouseHandler(ChartDefinition cd) {
      super();
      chartDefinition = cd;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
      ChartEntity   e    = event.getEntity();
      ChartDataItem item = itemFromEntity(chartDefinition, e, true);

      if (item != null) {
        chartDefinition.mouseClicked(EventHelper.createMouseEvent(event.getSource(), event.getTrigger()), item);
      }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
      if ((chartDefinition.getMouseHandler() != null) && chartDefinition.getMouseHandler().wantsMouseMoveEvents()) {
        ChartEntity   e    = event.getEntity();
        ChartDataItem item = itemFromEntity(chartDefinition, e, true);

        if (item != null) {
          chartDefinition.mouseMoved(EventHelper.createMouseEvent(event.getSource(), event.getTrigger()), item);
        }
      }
    }
  }


  class NumberAxis3DEx extends NumberAxis3D {
    ChartDefinition chartDefinition;
    boolean         domain;
    LabelData[]     categoryLabels;
    boolean         staticLabels;
    TickUpdater     tickUpdater;
    boolean         zeroAngle;

    public NumberAxis3DEx() {
      super();
    }

    public NumberAxis3DEx(ChartDefinition cd, boolean domain, String label) {
      super(label);
      this.domain          = domain;
      this.chartDefinition = cd;

      ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
      boolean   fixed = domain
                        ? ci.xIncrementFixed
                        : ci.yIncrementFixed;

      if (!fixed) {
        tickUpdater = new TickUpdater(domain);
      }

      setMinorTickMarksVisible(true);
      setTickMarkOutsideLength(5);
    }

    public NumberAxis3DEx(ChartDefinition cd, LabelData[] labels, String label) {
      super(label);
      this.domain          = true;
      this.chartDefinition = cd;

      ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
      boolean   fixed = domain
                        ? ci.xIncrementFixed
                        : ci.yIncrementFixed;

      if (!fixed) {
        tickUpdater = new TickUpdater(domain);
      }

      this.categoryLabels = labels;
      setTickMarkOutsideLength(5);
      setMinorTickMarksVisible(true);
    }

    @Override
    public double getLabelAngle() {
      return zeroAngle
             ? 0
             : super.getLabelAngle();
    }

    @Override
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
      if (chartDefinition.getSeriesCount() == 0) {
        ChartAxis ai = domain
                       ? chartDefinition.getDomainAxis()
                       : chartDefinition.getRangeAxis();

        if ((ai.getUpperBounds() == null) && (ai.getLowerBounds() == null)) {
          return new ArrayList(0);
        }
      }

      List result = null;

      if (RectangleEdge.isTopOrBottom(edge)) {
        result = refreshTicksHorizontal(g2, dataArea, edge);
      } else if (RectangleEdge.isLeftOrRight(edge)) {
        result = refreshTicksVertical(g2, dataArea, edge);
      }

      return (result == null)
             ? new ArrayList(0)
             : result;
    }

    @Override
    public void setLabelAngle(double angle) {
      super.setLabelAngle(angle);
      setVerticalTickLabels(angle != 0);
    }

    public void updateTickMarks(int width, int height) {
      if (tickUpdater != null) {
        tickUpdater.updateTickMarks(chartDefinition, this, width, height);
      }
    }

    @Override
    protected AxisState drawLabel(String label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea,
                                  RectangleEdge edge, AxisState state) {
      try {
        zeroAngle = true;

        return super.drawLabel(label, g2, plotArea, dataArea, edge, state);
      } finally {
        zeroAngle = false;
      }
    }

    @Override
    protected Rectangle2D getLabelEnclosure(Graphics2D g2, RectangleEdge edge) {
      try {
        zeroAngle = true;

        return super.getLabelEnclosure(g2, edge);
      } finally {
        zeroAngle = false;
      }
    }

    @Override
    protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
      double     width = dataArea.getWidth();
      TextAnchor anchor, rotationAnchor;
      double     angle = 0.0;

      if (isVerticalTickLabels()) {
        anchor         = TextAnchor.CENTER_RIGHT;
        rotationAnchor = TextAnchor.CENTER_RIGHT;
        angle          = getLabelAngle();
        // if (edge == RectangleEdge.TOP) {
        // angle = Math.PI / 2.0;
        // } else {
        // angle = -Math.PI / 2.0;
        // }
      } else {
        if (edge == RectangleEdge.TOP) {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
        } else {
          anchor         = TextAnchor.TOP_CENTER;
          rotationAnchor = TextAnchor.TOP_CENTER;
        }
      }

      LabelData[] labels = (categoryLabels != null)
                           ? categoryLabels
                           : createNonCategoryLabels(chartDefinition, width, this, domain, tickSize);

      return ChartHandler.refreshTicks(labels, anchor, rotationAnchor, width, angle);
    }

    @Override
    protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
      double     width = dataArea.getHeight();
      TextAnchor anchor;
      TextAnchor rotationAnchor;
      double     angle = 0.0;

      if (isVerticalTickLabels()) {
        if (edge == RectangleEdge.LEFT) {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
          angle          = -Math.PI / 2.0;
        } else {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
          angle          = Math.PI / 2.0;
        }

        angle = getLabelAngle();
      } else {
        if (edge == RectangleEdge.LEFT) {
          anchor         = TextAnchor.CENTER_RIGHT;
          rotationAnchor = TextAnchor.CENTER_RIGHT;
        } else {
          anchor         = TextAnchor.CENTER_LEFT;
          rotationAnchor = TextAnchor.CENTER_LEFT;
        }

        angle = getLabelAngle();
      }

      LabelData[] labels = (categoryLabels != null)
                           ? categoryLabels
                           : createNonCategoryLabels(chartDefinition, width, this, domain, tickSize);

      return ChartHandler.refreshTicks(labels, anchor, rotationAnchor, width, angle);
    }
  }


  class NumberAxisEx extends NumberAxis {
    ChartDefinition chartDefinition;
    boolean         domain;
    LabelData[]     categoryLabels;
    boolean         staticLabels;
    TickUpdater     tickUpdater;
    boolean         zeroAngle;

    public NumberAxisEx() {
      super();
    }

    public NumberAxisEx(ChartDefinition cd, boolean domain, String label) {
      super(label);
      this.domain          = domain;
      this.chartDefinition = cd;

      ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
      boolean   fixed = domain
                        ? ci.xIncrementFixed
                        : ci.yIncrementFixed;

      if (!fixed) {
        tickUpdater = new TickUpdater(domain);
      }

      setMinorTickMarksVisible(true);
      setTickMarkOutsideLength(5);
    }

    public NumberAxisEx(ChartDefinition cd, LabelData[] labels, String label) {
      super(label);
      this.domain          = true;
      this.chartDefinition = cd;

      ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
      boolean   fixed = domain
                        ? ci.xIncrementFixed
                        : ci.yIncrementFixed;

      if (!fixed) {
        tickUpdater = new TickUpdater(domain);
      }

      this.categoryLabels = labels;
      setTickMarkOutsideLength(5);
      setMinorTickMarksVisible(true);
    }

    @Override
    public double getLabelAngle() {
      return zeroAngle
             ? 0
             : super.getLabelAngle();
    }

    @Override
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
      if (chartDefinition.getSeriesCount() == 0) {
        ChartAxis ai = domain
                       ? chartDefinition.getDomainAxis()
                       : chartDefinition.getRangeAxis();

        if ((ai.getUpperBounds() == null) && (ai.getLowerBounds() == null)) {
          return new ArrayList(0);
        }
      }

      List result = null;

      if (RectangleEdge.isTopOrBottom(edge)) {
        result = refreshTicksHorizontal(g2, dataArea, edge);
      } else if (RectangleEdge.isLeftOrRight(edge)) {
        result = refreshTicksVertical(g2, dataArea, edge);
      }

      return (result == null)
             ? new ArrayList(0)
             : result;
    }

    @Override
    public void setLabelAngle(double angle) {
      super.setLabelAngle(angle);
      setVerticalTickLabels(angle != 0);
    }

    public void updateTickMarks(int width, int height) {
      if (tickUpdater != null) {
        tickUpdater.updateTickMarks(chartDefinition, this, width, height);
      }
    }

    @Override
    protected AxisState drawLabel(String label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea,
                                  RectangleEdge edge, AxisState state) {
      try {
        zeroAngle = true;

        return super.drawLabel(label, g2, plotArea, dataArea, edge, state);
      } finally {
        zeroAngle = false;
      }
    }

    @Override
    protected Rectangle2D getLabelEnclosure(Graphics2D g2, RectangleEdge edge) {
      try {
        zeroAngle = true;

        return super.getLabelEnclosure(g2, edge);
      } finally {
        zeroAngle = false;
      }
    }

    @Override
    protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
      double     width = dataArea.getWidth();
      TextAnchor anchor, rotationAnchor;
      double     angle = 0.0;

      if (isVerticalTickLabels()) {
        anchor         = TextAnchor.CENTER_RIGHT;
        rotationAnchor = TextAnchor.CENTER_RIGHT;

        if (edge == RectangleEdge.TOP) {
          angle = Math.PI / 2.0;
        } else {
          angle = -Math.PI / 2.0;
        }
      } else {
        if (edge == RectangleEdge.TOP) {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
        } else {
          anchor         = TextAnchor.TOP_CENTER;
          rotationAnchor = TextAnchor.TOP_CENTER;
        }
      }

      LabelData[] labels = (categoryLabels != null)
                           ? categoryLabels
                           : createNonCategoryLabels(chartDefinition, width, this, domain, tickSize);

      if (labels.length > 1) {
        width -= labels[0].width;
      }

      return ChartHandler.refreshTicks(labels, anchor, rotationAnchor, width, angle);
    }

    @Override
    protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
      double     width = dataArea.getHeight();
      TextAnchor anchor;
      TextAnchor rotationAnchor;
      double     angle = 0.0;

      if (isVerticalTickLabels()) {
        if (edge == RectangleEdge.LEFT) {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
          angle          = -Math.PI / 2.0;
        } else {
          anchor         = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
          angle          = Math.PI / 2.0;
        }
      } else {
        if (edge == RectangleEdge.LEFT) {
          anchor         = TextAnchor.CENTER_RIGHT;
          rotationAnchor = TextAnchor.CENTER_RIGHT;
        } else {
          anchor         = TextAnchor.CENTER_LEFT;
          rotationAnchor = TextAnchor.CENTER_LEFT;
        }
      }

      LabelData[] labels = (categoryLabels != null)
                           ? categoryLabels
                           : createNonCategoryLabels(chartDefinition, width, this, domain, tickSize);

      return ChartHandler.refreshTicks(labels, anchor, rotationAnchor, width, angle);
    }
  }


  class TickUpdater {
    boolean updatingTickMarks;
    int     oldHeight;
    int     oldWidth;
    boolean domain;

    public TickUpdater(boolean domain) {
      this.domain = domain;
    }

    protected void updateTickMarks(ChartDefinition chartDefinition, NumberAxis axis, int width, int height) {
      if (updatingTickMarks || ((oldHeight == height) && (oldWidth == width))) {
        return;
      }

      updatingTickMarks = true;
      oldHeight         = height;
      oldWidth          = width;

      ChartInfo ci = (ChartInfo) chartDefinition.getChartHandlerInfo();

      try {
        if ((domain && ci.xIncrementFixed) || (!domain && ci.yIncrementFixed)) {
          return;
        }

        double range[] = domain
                         ? ci.xAxisValues
                         : ci.yAxisValues;

        if (range != null) {
          double increment = calculateIncrement(domain
                  ? width
                  : height, tickSize, range[0], range[1], range[2]);

          if (!SNumber.isEqual(increment, axis.getTickUnit().getSize())) {
            axis.setTickUnit(new NumberTickUnit(increment));
          }
        }
      } finally {
        updatingTickMarks = false;
      }
    }
  }
}
