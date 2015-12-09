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

package com.appnativa.rare.ui.chart.coreplot;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontMetrics;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.chart.ChartAxis;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.ChartDefinition.ChartType;
import com.appnativa.rare.ui.chart.aChartHandler;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iMouseListener;

import com.google.j2objc.annotations.Weak;
import com.google.j2objc.annotations.WeakOuter;

import java.util.List;

/*-[
 #import "RARECPTLegendView.h"
 ]-*/
public class ChartHandler extends aChartHandler {
  private float tickSize;

  public ChartHandler() {}

  @Override
  public iPlatformComponent createChart(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
    ChartView chart = (ci == null)
                      ? null
                      : ci.chart;

    if (chart == null) {
      chart = new ChartView(this);
      ci    = new ChartInfo(chart);
    } else {
      ci.reset(chart);
      chart.reset(cd, true);
    }

    ci.domainType = getDataType(cd.getDomainAxis());
    cd.setChartHandlerInfo(ci);

    ChartPanel panel = (ChartPanel) chartPanel;

    if (panel == null) {
      panel = new ChartPanel(cd, chart);
    } else {
      panel.setChart(cd, chart);
    }

    int len = cd.getSeriesCount();

    if (len > 0) {
      createPlots(chart, cd);

      int rowCount    = (int) Math.sqrt(len);
      int columnCount = rowCount;

      if (rowCount * columnCount < len) {
        columnCount++;
      }

      if (rowCount * columnCount < len) {
        rowCount++;
      }

      if (len < 5) {
        rowCount = 1;
      }

      if (cd.isShowLegends()) {
        Component c;

        switch(cd.getLegendSide()) {
          case TOP :
            c = createLegendComponent(panel, ci, cd);
            c.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.CENTER);
            panel.setTopView(c);

            break;

          case BOTTOM :
            c = createLegendComponent(panel, ci, cd);
            c.putClientProperty(iConstants.RARE_HALIGN_PROPERTY, RenderableDataItem.HorizontalAlign.CENTER);
            panel.setBottomView(c);

            break;

          case LEFT :
            c = createLegendComponent(panel, ci, cd);
            c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.CENTER);
            panel.setLeftView(c);

            break;

          default :
            c = createLegendComponent(panel, ci, cd);
            c.putClientProperty(iConstants.RARE_VALIGN_PROPERTY, RenderableDataItem.VerticalAlign.CENTER);
            panel.setRightView(c);

            break;
        }
      }
    }

    List<ChartDataItem> markers = cd.getRangeMarkers();

    len = (markers == null)
          ? 0
          : markers.size();

    for (int i = 0; i < len; i++) {
      createMarker(cd, chart, markers.get(i), true);
    }

    markers = cd.getDomainMarkers();
    len     = (markers == null)
              ? 0
              : markers.size();

    for (int i = 0; i < len; i++) {
      createMarker(cd, chart, markers.get(i), false);
    }

    return panel;
  }

  @Override
  public iPlatformComponent dataChanged(iPlatformComponent chartPanel, ChartDefinition cd) {
    iPlatformComponent c = createChart(chartPanel, cd);

    return c;
  }

  @Override
  public void dispose(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo ci = (cd == null)
                   ? null
                   : (ChartInfo) cd.getChartHandlerInfo();

    super.dispose(chartPanel, cd);

    if (ci != null) {
      ci.dispose();
    }
  }

  @Override
  public void itemChanged(iPlatformComponent chartPanel, ChartDefinition cd, ChartDataItem item) {
    int index = cd.getSeries().indexOf(item);

    if (index != -1) {
      ChartView chart = getChartView(chartPanel);

      if (chart != null) {
        chart.reloadSeries(index);
      }
    }
  }

  @Override
  public ChartDataItem itemFromLocation(ChartDefinition cd, int x, int y) {
    ChartInfo ci    = (ChartInfo) cd.getChartHandlerInfo();
    ChartView chart = (ci == null)
                      ? null
                      : ci.chart;

    if (chart != null) {
      // translate coordinates from chart panel to chart
      UIPoint p = chart.getLocation(null);

      if (p != null) {
        x -= p.x;
        y -= p.y;
      }

      chart.rowAndColumnAt(x, y, p);

      if ((p.y > -1) && (p.x > -1) && (p.y < cd.getSeriesCount())) {
        int           n      = (int) p.x + cd.getStartColumn();
        ChartDataItem series = cd.getSeries((int) p.y);

        if (series != null) {
          int end = cd.getEndColumn();

          if (end > 0) {
            end++;
          } else {
            end = series.size();
          }

          if (n < end) {
            return (ChartDataItem) cd.getSeries((int) p.y).get(n);
          }
        }
      }
    }

    return null;
  }

  @Override
  public void unzoom(iPlatformComponent chartPanel) {}

  @Override
  public void updateRangeAxis(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      ChartAxis          ai = cd.getRangeAxis();
      double             lo = 0;
      double             hi = 0;
      RenderableDataItem di = ai.getLowerBounds();

      if (di != null) {
        lo = di.doubleValue();
      }

      di = ai.getUpperBounds();

      if (di != null) {
        hi = di.doubleValue();
      }

      chart.setAxisRange(ai, lo, hi, 0);
    }
  }

  @Override
  public void updatesCompleted(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.reloadAllPlots();
    }
  }

  @Override
  public void updatesPending(iPlatformComponent chartPanel, ChartDefinition cd) {}

  @Override
  public void setDomainLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(false, true, false);
    }
  }

  @Override
  public void setDomainLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(false, false, true);
    }
  }

  @Override
  public void setRangeLabel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(true, true, false);
    }
  }

  @Override
  public void setRangeLabelAngel(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(true, false, true);
    }
  }

  @Override
  public void setShowDomainLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(false, false, false);
    }
  }

  @Override
  public void setShowPlotValues(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);
    boolean   show  = cd.isShowPlotLabels();

    ((ChartInfo) cd.getChartHandlerInfo()).setShowPointLabels(show);

    if (chart != null) {
      chart.setShowPlotValues(show);
    }
  }

  @Override
  public void setShowRangeLabels(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartView chart = getChartView(chartPanel);

    if (chart != null) {
      chart.updateAxis(true, false, false);
    }
  }

  @Override
  public UIImage getChartImage(iPlatformComponent chartPanel, ChartDefinition cd) {
    return null;
  }

  @Override
  public UIDimension getPlotAreaSize(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

    return (ci == null)
           ? new UIDimension()
           : ci.chart.getPlotAreaSize();
  }

  @Override
  public iPlatformComponent getLegendComponent(iPlatformComponent chartPanel, ChartDefinition cd) {
    ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

    return (ci == null)
           ? null
           : ci.legend;
  }

  @Override
  public boolean isLegendSeperate() {
    return true;
  }

  protected Component createLegendComponent(ChartPanel panel, ChartInfo ci, ChartDefinition cd) {
    if (ci.legend != null) {
      panel.remove(ci.legend);
      ci.legend.dispose();
    }

    ChartLegend c = new ChartLegend(ci.chart.createLegendView(), cd);

    ci.legend = c;

    return c;
  }

  protected void createMarker(ChartDefinition cd, ChartView view, ChartDataItem item, boolean rangeMarker) {
    UIColor fg = item.getForeground();

    if (fg == null) {
      fg = Platform.getUIDefaults().getColor("Rare.Chart.markerForeground");
    }

    if (fg == null) {
      fg = getTextColor(cd);
    }

    UIColor bg = Platform.getUIDefaults().getColor("Rare.Chart.markerBackground");

    if (bg == null) {
      bg = UIColor.GRAY.alpha(128);
    }

    view.addMarker(item, bg, fg, rangeMarker);
  }

  protected void createPlots(ChartView chart, ChartDefinition cd) {
    ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

    ci.popularSeriesDataAndCaluclateRanges(this, cd);
    ci.setShowPointLabels(cd.isShowPlotLabels());

    List<SeriesData> seriesData = ci.seriesData;
    int              len        = (seriesData == null)
                                  ? 0
                                  : seriesData.size();

    if (len > 0) {
      UIFont        f  = getAxisLabelFont(cd.getRangeAxis());
      UIFontMetrics fm = UIFontMetrics.getMetrics(f);

      tickSize = FontUtils.getFontHeight(f, true);

      for (int i = 0; i < len; i++) {
        SeriesData data = seriesData.get(i);
        ChartPlot  plot = chart.createPlot(data, ci.categoryDomain);

        if (plot == null) {
          continue;
        }

        chart.addPlot(plot);
        data.linkedData = plot;
      }

      LabelsManager dm;
      double        xrange[] = ci.xAxisValues;
      double        yrange[] = ci.yAxisValues;

      if (ci.categoryDomain) {
        LabelData[] data = ci.createLabelData(cd, fm, true);

        xrange[0] = -1;
        xrange[1] = (data == null)
                    ? 0
                    : data.length;
        xrange[2] = 1;
        dm        = new LabelsManager(chart, data);
      } else {
        dm = new LabelsManager(chart, true);
      }

      LabelsManager rm = new LabelsManager(chart, false);

      // hold onto them here so that they can be week in Host View
      ci.domainLabels = dm;
      ci.rangeLabels  = rm;
      chart.setAxisRanges(xrange[0], xrange[1], xrange[2], yrange[0], yrange[1], yrange[2], dm, rm);
    }
  }

  private ChartView getChartView(iPlatformComponent chartPanel) {
    return (ChartView) ((ChartPanel) chartPanel).getCenterView().getView();
  }

  static class ChartInfo extends aChartInfo {
    @Weak()
    public ChartView     chart;
    @Weak()
    public ChartType     chartType;
    @Weak()
    public Component     legend;
    public LabelsManager rangeLabels;
    public LabelsManager domainLabels;

    public ChartInfo(ChartView chart) {
      this.chart = chart;
    }

    /**
     * Constructs a new instance
     */
    ChartInfo() {}

    public void reset(ChartView chart) {
      if (legend != null) {
        legend.removeFromParent();
        legend.dispose();
      }

      if (seriesData != null) {
        for (SeriesData d : seriesData) {
          ChartPlot plot = (ChartPlot) d.linkedData;

          if (plot != null) {
            plot.dispose();
          }
        }
      }

      super.reset();
      this.chart   = chart;
      chartType    = null;
      legend       = null;
      domainLabels = null;
      rangeLabels  = null;
    }
  }


  class ChartLegend extends Component {
    int     legendRows;
    int     legendColumns;
    int     legendWidths;
    int     legendHeights;
    int     legendMaxWidth;
    int     legendHeight;
    int     legendCount;
    UIFont  legendFont;
    boolean horizontal;

    public ChartLegend(View view, ChartDefinition cd) {
      super(view);
      update(cd);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
      if (legendWidths > 0) {
        int cols = 0;
        int rows = 0;

        if (horizontal) {
          rows = (int) Math.floor((width + legendWidths - 5) / legendWidths);
        } else {
          cols = (int) Math.floor((height + legendHeights - 5) / legendHeights);
        }

        if ((legendColumns != cols) || (legendRows != rows)) {
          legendColumns = cols;
          legendRows    = rows;
          updateColumns(cols, rows);
        }
      }

      super.setBounds(x, y, width, height);
    }

    native void updateColumns(int columns, int rows)
    /*-[
      [(RARECPTLegendView*)view_->proxy_ updateColumns: columns andRows: rows];
    ]-*/
    ;

    @Override
    protected void getMinimumSizeEx(UIDimension size,float maxWidth) {
      size.width  = legendMaxWidth;
      size.height = legendHeight;
    }

    @Override
    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      if (horizontal) {
        size.height = legendHeight;
        size.width  = legendWidths;
      } else {
        size.height = legendHeights;
        size.width  = legendMaxWidth;
      }
    }

    public void update(ChartDefinition cd) {
      List<RenderableDataItem> rows = cd.getSeries();
      int                      len  = (rows == null)
                                      ? 0
                                      : rows.size();

      if (len == 0) {
        return;
      }

      boolean pieChart = cd.getChartType() == ChartType.PIE;

      if (pieChart) {
        rows = rows.get(0).getItems();
        len  = rows.size();
      }

      UIFont        f      = cd.getTextFont(legendLabelFont);
      UIFontMetrics fm     = UIFontMetrics.getMetrics(f);
      float         height = FontUtils.getFontHeight(f, true) * 1.5f;

      legendHeights = 0;
      legendWidths  = 0;
      legendHeight  = UIScreen.snapToSize(height);

      for (int i = 0; i < len; i++) {
        ChartDataItem ci = (ChartDataItem) rows.get(i);
        String        s  = pieChart
                           ? ci.getDomainString()
                           : ci.toString();

        if (s != null) {
          int w = (int) (height + 6 + fm.stringWidth(s));

          legendMaxWidth = Math.max(w, legendMaxWidth);
          legendWidths   += w;
          legendHeights  += height;
        }
      }

      switch(cd.getLegendSide()) {
        case TOP :
        case BOTTOM :
          horizontal = true;

          break;

        default :
          horizontal = false;

          break;
      }

      legendCount = len;
    }
  }


  @WeakOuter
  public class LabelsManager {
    @Weak
    ChartView   chart;
    LabelData[] labelData;
    boolean     isDate;
    boolean     domain;
    int         oldWidth;
    int         oldHeight;
    boolean     dynamic;
    int         oldAngle;

    public LabelsManager(ChartView chart, LabelData[] labelData) {
      this.chart     = chart;
      this.labelData = labelData;
      this.domain    = true;
    }

    public LabelsManager(ChartView chart, boolean domain) {
      this.chart   = chart;
      this.domain  = domain;
      this.dynamic = true;
    }

    public UIPoint getAxisSize(ChartDefinition cd) {
      float         size      = 0;
      LabelData[]   labels    = labelData;
      int           len       = (labels == null)
                                ? 0
                                : labels.length;
      UIDimension   d         = new UIDimension();
      ChartAxis     ai        = domain
                                ? cd.getDomainAxis()
                                : cd.getRangeAxis();
      int           textAngle = ai.getAngle();
      UIFont        f         = getAxisLabelFont(ai);
      UIFontMetrics fm        = UIFontMetrics.getMetrics(f);

      if (len == 0) {
        size = (int) Math.ceil(fm.getHeight());
      }

      for (int i = 0; i < len; i++) {
        calculateTextSize(labels[i].label, fm, textAngle, d);
        size = Math.max(size, domain
                              ? d.height
                              : d.width);
      }

      UIPoint p = new UIPoint(size, 0);

      if (ai.getLabel().length() > 0) {
        size += (int) Math.ceil(fm.getHeight());
        size += ScreenUtils.PLATFORM_PIXELS_8;
      }

      p.y = size;

      return p;
    }

    protected LabelData[] createNonCategoryLabels(ChartDefinition cd, int width) {
      LabelData[] labels     = labelData;
      ChartInfo   ci         = (ChartInfo) cd.getChartHandlerInfo();
      double      range[]    = domain
                               ? ci.xAxisValues
                               : ci.yAxisValues;
      double      startValue = range[0];
      double      endValue   = range[1];
      double      increment  = range[2];

      if (domain) {
        labels = createLabelData(cd, domain, width, startValue, endValue, increment);
      } else {
        labels = createNumericLabelsData(cd, width, startValue, endValue, increment, domain, domain
                ? 0
                : tickSize);
      }

      return labels;
    }

    public LabelData[] getLabels(ChartDefinition cd, int width, int height) {
      int angle = domain
                  ? cd.getDomainAxis().getAngle()
                  : cd.getRangeAxis().getAngle();

      if ((oldWidth != width) || (oldHeight != height) || (labelData == null) || (angle != oldAngle)) {
        updateTickMarks(cd, width, height);

        if (dynamic) {
          labelData = createNonCategoryLabels(cd, width);
        } else {
          if (angle != oldAngle) {
            UIFontMetrics fm = UIFontMetrics.getMetrics(getAxisLabelFont(cd.getDomainAxis()));

            remeasureLabels(labelData, fm, angle);
          }
        }

        oldAngle = angle;
      }

      oldWidth  = width;
      oldHeight = height;

      return labelData;
    }

    protected void updateTickMarks(ChartDefinition cd, int width, int height) {
      if (((oldHeight == height) && (oldWidth == width))) {
        return;
      }

      oldHeight = height;
      oldWidth  = width;

      ChartInfo ci = (ChartInfo) cd.getChartHandlerInfo();

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

        chart.updateAxisIncrements(domain
                                   ? increment
                                   : 0, domain
                                        ? 0
                                        : increment);
      }
    }
  }


  @WeakOuter
  class ChartPanel extends BorderPanel {
    @Weak
    ChartView       chart;
    Component       legend;
    MouseListener   mouseListener;
    ActionComponent title;
    int             oldWidth;
    int             oldHeight;
    SeriesData[]    seriesData;
    double[]        xAxisRange;
    double[]        yAxisRange[];

    public ChartPanel() {
      super();

      UIColor bg = Platform.getUIDefaults().getColor("Rare.Chart.background");

      if (bg != null) {
        setBackground(bg);
      }
    }

    public ChartPanel(ChartDefinition cd, ChartView cv) {
      this();
      setChart(cd, cv);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
      super.setBounds(x, y, width, height);
    }

    @Override
    public void add(iPlatformComponent c, Object constraints, int position) {
      super.add(c, constraints, position);

      if ((mouseListener != null) && (c != null)) {
        View v = c.getView();

        if (v.getMouseListener() == null) {
          v.setMouseListener(mouseListener);
        }
      }
    }

    @Override
    public void addMouseListener(iMouseListener l) {
      super.addMouseListener(l);
      addMouseListener();
    }

    @Override
    public void dispose() {
      super.dispose();

      if (legend != null) {
        legend.dispose();
      }

      if (title != null) {
        title.dispose();
      }

      legend = null;
      title  = null;
      chart  = null;
    }

    public void setChart(ChartDefinition cd, ChartView cv) {
      if (cv.getComponent() == null) {
        cv.setComponent(new Component(cv));
      }

      removeAll();

      if ((this.chart != null) && (this.chart != cv)) {
        this.chart.dispose();
      }

      if (mouseListener != null) {
        cv.setMouseListener(mouseListener);
      }

      chart = cv;
      setCenterView(cv.getComponent());

      if (title != null) {
        remove(title);
      }

      RenderableDataItem ti = cd.getTitle();

      if (ti != null) {
        if (title == null) {
          title = new ActionComponent(new LabelView());
          title.setAlignment(HorizontalAlign.CENTER, VerticalAlign.CENTER);
          configureTitle(title, ti);
        }

        switch(cd.getTitleSide()) {
          case BOTTOM :
            setBottomView(title);
            title.setOrientation(RenderableDataItem.Orientation.HORIZONTAL);

            break;

          case LEFT :
            setLeftView(title);
            title.setOrientation(RenderableDataItem.Orientation.VERTICAL_UP);

            break;

          case RIGHT :
            setRightView(title);
            title.setOrientation(RenderableDataItem.Orientation.VERTICAL_DOWN);

            break;

          default :
            setTopView(title);
            title.setOrientation(RenderableDataItem.Orientation.HORIZONTAL);

            break;
        }
      }
    }

    public ChartView getChart() {
      return chart;
    }

    protected void addMouseListener() {
      if (mouseListener == null) {
        mouseListener = new MouseListener(this);

        int len = getComponentCount();

        for (int i = 0; i < len; i++) {
          View v = getComponentAt(i).getView();

          if (v.getMouseListener() == null) {
            v.setMouseListener(mouseListener);
          }
        }
      }
    }
  }


  static class MouseListener implements iMouseListener {
    @Weak
    ChartPanel panel;

    MouseListener(ChartPanel panel) {
      this.panel = panel;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      if (l != null) {
        l.mouseEntered(EventHelper.retarget(e, panel.getView()));
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      if (l != null) {
        l.mouseExited(EventHelper.retarget(e, panel.getView()));
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      if (l != null) {
        l.mousePressed(EventHelper.retarget(e, panel.getView()));
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      if (l != null) {
        l.mouseReleased(EventHelper.retarget(e, panel.getView()));
      }
    }

    @Override
    public void pressCanceled(MouseEvent e) {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      if (l != null) {
        l.pressCanceled((e == null)
                        ? null
                        : EventHelper.retarget(e, panel.getView()));
      }
    }

    @Override
    public boolean wantsLongPress() {
      View           v = panel.getView();
      iMouseListener l = (v == null)
                         ? null
                         : v.getMouseListener();

      return (l == null)
             ? false
             : l.wantsLongPress();
    }
  }


  public void reloadCharts(iPlatformComponent chartPanel, ChartDefinition cd) {
    dataChanged(chartPanel, cd);
  }
}
