/*
 * @(#)JFreeChartHelper.java   2009-01-14
 *
 * Copyright (c) SparseWare Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.chart.jfreechart;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.text.AttributedString;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer2;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLine3DRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.renderer.xy.YIntervalRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.Week;
import org.jfree.data.time.Year;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.chart.ChartAxis;
import com.appnativa.rare.ui.chart.ChartDataItem;
import com.appnativa.rare.ui.chart.ChartDefinition;
import com.appnativa.rare.ui.chart.aChartHandler.SeriesData;
import com.appnativa.rare.ui.chart.jfreechart.ChartHandler.ChartInfo;
import com.appnativa.rare.ui.painter.UIBackgroundPainter;
import com.appnativa.rare.ui.painter.UIBackgroundPainter.GradientPaintEx;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.NumberRange;

/**
 * Helper
 *
 * @author Don DeCoteau
 */
public class ChartHelper {

  /** Creates a new instance of JFreeChartHelper */
  private ChartHelper() {
  }

  public static IntervalMarker createIntervalMarker(ChartDataItem item) {
    Number lower = (Number) item.getValue();
    Number upper = (Number) item.getDomainValue();
    IntervalMarker marker = new IntervalMarker(lower.doubleValue(), upper.doubleValue());
    String s = item.getTitle();

    if (s != null) {
      marker.setLabel(s);
    }

    marker.setLabelAnchor(getRectangleAnchor(item));
    marker.setLabelTextAnchor(getTextAnchor(item));
    updateMarker(marker, item);

    return marker;
  }

  public static IntervalMarker createIntervalMarker(ChartDataItem item, ChartAxis.TimeUnit tm) {
    RegularTimePeriod lower = getTimePeriod((Date) item.getValue(), tm);
    RegularTimePeriod upper = getTimePeriod((Date) item.getDomainValue(), tm);
    IntervalMarker marker = new IntervalMarker(lower.getFirstMillisecond(), upper.getFirstMillisecond());
    String s = item.getTitle();

    if (s != null) {
      marker.setLabel(s);
    }

    marker.setLabelAnchor(getRectangleAnchor(item));
    marker.setLabelTextAnchor(getTextAnchor(item));
    updateMarker(marker, item);
    return marker;
  }

  public static PieCollection createPieDataset(ChartDefinition cd) {

    List<RenderableDataItem> rows = cd.getSeries();
    int len = (rows == null) ? 0 : rows.size();
    DefaultPieDatasetEx set = new DefaultPieDatasetEx();
    ChartDataItem di;
    Comparable c;
    Number num;

    if (len > 0) {
      rows = rows.get(0).getItems();
      len = rows.size();

      for (int i = 0; i < len; i++) {
        di = (ChartDataItem) rows.get(i);

        if (di != null) {
          c = (Comparable) di.getDomainValue();
          num = (Number) di.getValue();

          if ((c != null) && (num != null)) {
            set.setValue(c, num);

            if (di.getVerticalAlignment() == ChartDataItem.VerticalAlign.TOP) {
              set.setExplodedPiece((Comparable) di.getDomainValue());
            }
          }
        }
      }
    }

    return set;
  }

  public static Paint getPaint(iPainter p) {
    iPainter pp = p;

    if (p instanceof UIComponentPainter) {
      pp = ((UIComponentPainter) p).getBackgroundPainter();
    }

    if (pp instanceof UIBackgroundPainter) {
      GradientPaintEx gp = ((UIBackgroundPainter) pp).getGradientPaint(50, 50);

      if (gp != null) {
        gp.pointer = p;

        return gp;
      }
    }

    return new PainterColor(p);
  }

  public static Paint getPaint(UIColor c) {
    iPainter pp = ColorUtils.getPainter(c);

    if (pp instanceof UIBackgroundPainter) {
      GradientPaintEx gp = ((UIBackgroundPainter) pp).getGradientPaint(50, 50);

      if (gp != null) {
        gp.pointer = pp;

        return gp;
      }
    }

    return c.getPaint();
  }

  public static RectangleAnchor getRectangleAnchor(ChartDataItem item) {
    RectangleAnchor ta;

    switch (item.getIconPosition()) {
      case LEADING:
      case LEFT:
        ta = RectangleAnchor.LEFT;

        break;

      case TRAILING:
      case RIGHT:
        ta = RectangleAnchor.RIGHT;

        break;

      case TOP_CENTER:
        ta = RectangleAnchor.TOP;

        break;

      case BOTTOM_CENTER:
        ta = RectangleAnchor.BOTTOM;

        break;

      default:
        ta = RectangleAnchor.CENTER;

        break;
    }

    return ta;
  }

  public static String getString(ChartDefinition cd, boolean linkeddata, ChartDataItem di) {
    if (di != null) {
      cd.setCurrentValues(di.getValue(), di.getDomainValue());

      iWidget w = cd.getChartViewer();
      Object o;

      if (linkeddata) {
        o = di.getLinkedData();

        return w.expandString((o == null) ? "" : o.toString(), false);
      }

      o = di.getTooltip();

      if (o != null) {
        return w.expandString(o.toString(), false);
      }
    }

    return "";
  }

  public static TextAnchor getTextAnchor(ChartDataItem item) {
    TextAnchor ta;

    switch (item.getHorizontalAlignment()) {
      case LEFT:
        switch (item.getVerticalAlignment()) {
          case TOP:
            ta = TextAnchor.TOP_LEFT;

            break;

          case BOTTOM:
            ta = TextAnchor.BOTTOM_LEFT;

            break;

          default:
            ta = TextAnchor.CENTER_LEFT;

            break;
        }

        break;

      case RIGHT:
        switch (item.getVerticalAlignment()) {
          case TOP:
            ta = TextAnchor.TOP_RIGHT;

            break;

          case BOTTOM:
            ta = TextAnchor.BOTTOM_RIGHT;

            break;

          default:
            ta = TextAnchor.CENTER_RIGHT;

            break;
        }

        break;

      default:
        switch (item.getVerticalAlignment()) {
          case TOP:
            ta = TextAnchor.TOP_CENTER;

            break;

          case BOTTOM:
            ta = TextAnchor.BOTTOM_CENTER;

            break;

          default:
            ta = TextAnchor.CENTER;

            break;
        }

        break;
    }

    return ta;
  }

  public static RegularTimePeriod getTimePeriod(Date date, ChartAxis.TimeUnit tm) {
    RegularTimePeriod tp;

    switch (tm) {
      case MILLISECOND:
        tp = new Millisecond(date);

        break;

      case SECOND:
        tp = new Second(date);

        break;

      case MINUTE:
        tp = new Minute(date);

        break;

      case HOUR:
        tp = new Hour(date);

        break;

      case WEEK:
        tp = new Week(date);

        break;

      case MONTH:
        tp = new Month(date);

        break;

      case YEAR:
        tp = new Year(date);

        break;

      default:
        tp = new Day(date);

        break;
    }

    return tp;
  }

  public static XYSeries populateXYSeries(XYSeries series, SeriesData data) {
    if (series == null) {
      series = new XYSeries(data.legend, false, true);
    }
    List<Number> rangeValues = data.rangeValues;
    List<Comparable> domainValues = data.domainValues;
    Number num, dnum;
    int len = rangeValues.size();
    for (int i = 0; i < len; i++) {
      num = rangeValues.get(i);
      Object o = domainValues.get(i);
      if (o instanceof Date) {
        dnum = ((Date) o).getTime();
      } else {
        dnum = (Number) o;
      }
      series.add(dnum, num);
    }

    return series;
  }

  public static void setChartTitle(JFreeChart chart, RenderableDataItem title) {
    if (title == null) {
      return;
    }

    TextTitle tt = new TextTitle(title.toString());

    if (title.getFont() != null) {
      tt.setFont(title.getFont());
    }

    if (title.getForeground() != null) {
      tt.setPaint(title.getForeground());
    }

    switch (title.getIconPosition()) {
      case TOP_CENTER:
        tt.setPosition(RectangleEdge.TOP);

        break;

      case RIGHT:
        tt.setPosition(RectangleEdge.RIGHT);

        break;

      case BOTTOM_CENTER:
        tt.setPosition(RectangleEdge.BOTTOM);

        break;

      case LEFT:
        tt.setPosition(RectangleEdge.LEFT);

        break;

      default:
        break;
    }

    chart.setTitle(tt);
  }

  public static void updateMarker(Marker marker, ChartDataItem item) {

    if (item.getFont() != null) {
      marker.setLabelFont(item.getFont());
    } else {
      UIFont f = Platform.getUIDefaults().getFont("Rare.Chart.markerFont");
      if (f != null) {
        marker.setLabelFont(f);
      }
    }

    if (item.getForeground() != null) {
      marker.setLabelPaint(item.getForeground());
    } else {
      UIColor c = Platform.getUIDefaults().getColor("Rare.Chart.markerForeground");
      if (c != null) {
        marker.setLabelPaint(c.getPaint());
      }
    }
    if (item.getBackground() != null) {
      marker.setPaint(item.getBackground());
    } else {
      UIColor c = Platform.getUIDefaults().getColor("Rare.Chart.markerBackground");
      if (c != null) {
        marker.setPaint(c.getPaint());
      }
    }

  }

  protected static iPainter getPainter(Paint p) {
    if (p instanceof PainterColor) {
      return ((PainterColor) p).painter;
    }

    if (p instanceof GradientPaintEx) {
      GradientPaintEx gp = (GradientPaintEx) p;

      if (gp.pointer instanceof iPainter) {
        return (iPainter) gp.pointer;
      }
    }

    return null;
  }

  public static class DefaultPieDatasetEx extends DefaultPieDataset implements PieCollection {
    Comparable explodedPiece;

    @Override
    public Comparable getExplodedPiece() {
      return explodedPiece;
    }

    @Override
    public void setExplodedPiece(Comparable piece) {
      explodedPiece = piece;
    }
  }

  public static class PainterColor extends UIColor {
    private iPainter painter;

    public PainterColor(iPainter p) {
      super(getColor(p));
      painter = p;
    }

    public Paint getPaint(float width, float height) {
      iPlatformPaint p = painter.getPaint(width, height);

      return (p == null) ? null : p.getPaint();
    }

    private static UIColor getColor(iPainter p) {
      if (p instanceof UIComponentPainter) {
        p = ((UIComponentPainter) p).getBackgroundPainter();
      }

      UIColor c = p.getBackgroundColor();

      if (c == null) {
        c = UIColor.WHITE;
      }

      return c;
    }
  }

  public static interface PieCollection extends PieDataset {
    Comparable getExplodedPiece();

    void setExplodedPiece(Comparable piece);

    void setValue(java.lang.Comparable key, double value);

    void setValue(java.lang.Comparable key, java.lang.Number value);
  }

  public static class RareBarPainter implements BarPainter, XYBarPainter {
    JComponent           component;
    SwingGraphics        graphics;
    GradientBarPainter   shadowPainter;
    GradientXYBarPainter xyShadowPainter;

    public RareBarPainter(JComponent c) {
      component = c;
    }

    @Override
    public Object clone() {
      return new RareBarPainter(component);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof RareBarPainter) {
        return ((RareBarPainter) obj).component == component;
      }

      return false;
    }

    @Override
    public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
      Paint p = renderer.getItemPaint(row, column);

      paintBar(g2, p, bar, base);

      if (renderer.isDrawBarOutline()) {
        Stroke stroke = renderer.getItemOutlineStroke(row, column);
        Paint paint = renderer.getItemOutlinePaint(row, column);

        if ((stroke != null) && (paint != null)) {
          g2.setStroke(stroke);
          g2.setPaint(paint);
          g2.draw(bar);
        }
      }
    }

    public void paintBar(Graphics2D g2, Paint p, RectangularShape bar, RectangleEdge base) {
      iPainter painter = getPainter(p);

      if (painter != null) {
        float x = UIScreen.snapToPosition(bar.getX());
        float y = UIScreen.snapToPosition(bar.getY());
        float w = UIScreen.snapToSize(bar.getWidth());
        float h = UIScreen.snapToSize(bar.getHeight());

        // g2.translate(x, y);
        graphics = SwingGraphics.fromGraphics(g2, component, graphics);

        painter.paint(graphics, x, y, w, h, iPainter.UNKNOWN);
        graphics.clear();
        // g2.translate(-x, -y);
      } else if (p != null) {
        g2.setPaint(p);
        g2.fill(bar);
      }
    }

    @Override
    public void paintBar(Graphics2D g2, XYBarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
      Paint p = renderer.getItemPaint(row, column);

      paintBar(g2, p, bar, base);

      if (renderer.isDrawBarOutline()) {
        Stroke stroke = renderer.getItemOutlineStroke(row, column);
        Paint paint = renderer.getItemOutlinePaint(row, column);

        if ((stroke != null) && (paint != null)) {
          g2.setStroke(stroke);
          g2.setPaint(paint);
          g2.draw(bar);
        }
      }
    }

    @Override
    public void paintBarShadow(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base,
        boolean pegShadow) {
      try {
        if (shadowPainter == null) {
          shadowPainter = new GradientBarPainter();
        }

        shadowPainter.paintBarShadow(g2, renderer, row, column, bar, base, pegShadow);
      } catch (Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    @Override
    public void paintBarShadow(Graphics2D g2, XYBarRenderer renderer, int row, int column, RectangularShape bar,
        RectangleEdge base, boolean pegShadow) {
      try {
        if (xyShadowPainter == null) {
          xyShadowPainter = new GradientXYBarPainter();
        }

        xyShadowPainter.paintBarShadow(g2, renderer, row, column, bar, base, pegShadow);
      } catch (Exception e) {
        Platform.ignoreException(null, e);
      }
    }
  }

  public static class XYAreaRendererEx extends XYAreaRenderer2 {
    Rectangle2D      paintRect;
    List<SeriesData> seriesData;

    public XYAreaRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
        ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
      paintRect = dataArea;
      super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
      paintRect = null;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }

    @Override
    public Paint lookupSeriesPaint(int series) {
      Paint p = super.lookupSeriesPaint(series);

      if ((paintRect != null) && (p instanceof PainterColor)) {
        Paint pp = ((PainterColor) p).getPaint((float) paintRect.getWidth(), (float) paintRect.getHeight());

        if (pp != null) {
          p = pp;
        }
      }

      return p;
    }
  }

  public static interface XYCollection extends IntervalXYDataset {
    void addSeries(XYSeries series);
  }

  public static class XYSeriesCollectionEx extends XYSeriesCollection implements XYCollection {

    public XYSeriesCollectionEx() {
      super();
    }

    public XYSeriesCollectionEx(boolean b) {
      super();
    }

    @Override
    public Number getEndY(int series, int item) {
      Number n = super.getEndY(series, item);
      if (n instanceof NumberRange) {
        n = ((NumberRange) n).getHighValue();
      }
      return n;
    }

    @Override
    public Number getStartY(int series, int item) {
      Number n = super.getStartY(series, item);
      if (n instanceof NumberRange) {
        n = ((NumberRange) n).getLowValue();
      }
      return n;
    }
  }

  public static class XYSeriesEx extends XYSeries {
    private ChartDataItem seriesItem;

    public XYSeriesEx(Comparable key) {
      super(key);
    }

    public XYSeriesEx(Comparable key, boolean autoSort) {
      super(key, autoSort);
    }

    public XYSeriesEx(Comparable key, boolean autoSort, boolean allowDuplicateXValues) {
      super(key, autoSort, allowDuplicateXValues);
    }

    public ChartDataItem getSeriesItem() {
      return seriesItem;
    }

    public void setSeriesItem(ChartDataItem seriesItem) {
      this.seriesItem = seriesItem;
    }

  }

  public static class XYTableCollectionEx extends DefaultTableXYDataset implements XYCollection {
  }

  static class PieToolTipLabelGenerator extends StandardPieSectionLabelGenerator implements PieToolTipGenerator {
    ChartDefinition chartDefinition;
    boolean         useLinkedData;

    public PieToolTipLabelGenerator(ChartDefinition cd, boolean linkedData) {
      super();
      chartDefinition = cd;
      useLinkedData = linkedData;
    }

    @Override
    public String generateSectionLabel(PieDataset dataset, Comparable key) {
      int series = dataset.getIndex(key);
      ChartDataItem di = chartDefinition.getSeriesItem(series, 0);

      if (di == null) {
        return super.generateSectionLabel(dataset, key);
      }

      return getString(chartDefinition, useLinkedData, di);
    }

    @Override
    public String generateToolTip(PieDataset dataset, Comparable key) {
      return generateSectionLabel(dataset, key);
    }
  }

  public static class PieLabelGenerator implements PieSectionLabelGenerator {
    ChartDefinition chartDefinition;
    String          labelFormat;

    public PieLabelGenerator(ChartDefinition cd) {
      this(cd,null);
    }

    public PieLabelGenerator(ChartDefinition cd, String format) {
      chartDefinition = cd;
      if(format==null || format.length()==0) {
        format="{2}";
      }
      this.labelFormat = format;
    }

    @Override
    public String generateSectionLabel(PieDataset dataset, Comparable key) {
      ChartInfo ci = (ChartInfo) chartDefinition.getChartHandlerInfo();
      int index = dataset.getIndex(key);
      return ci.seriesData.get(0).getPieChartLabel(index,labelFormat);
    }

    @Override
    public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable key) {
      return null;
    }

  }

  static class XYAreaSplineRendererEx extends XYSplineRendererEx {
    public XYAreaSplineRendererEx(List<SeriesData> seriesData) {
      super(seriesData);
      setFillType(XYSplineRenderer.FillType.TO_ZERO);
      setBaseShapesVisible(false);
      setBaseShapesFilled(false);
    }
  }

  static class XYClusteredBarRendererEx extends ClusteredXYBarRenderer {
    private List<SeriesData> seriesData;

    public XYClusteredBarRendererEx(double margin, boolean centerBarAtStartValue, List<SeriesData> seriesData) {
      super(margin, centerBarAtStartValue);
      this.seriesData = seriesData;
    }

    public XYClusteredBarRendererEx(List<SeriesData> seriesData) {
      this(0.1, false, seriesData);
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }
  }

  static class XYLine3DRendererEx extends XYLine3DRenderer {
    private List<SeriesData> seriesData;

    public XYLine3DRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }
  }

  static class XYLineAndShapeRendererEx extends XYLineAndShapeRenderer {
    private List<SeriesData> seriesData;

    public XYLineAndShapeRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }
  }

  static class XYRangeAreaRendererEx extends YIntervalRenderer {
    List<SeriesData> seriesData;

    public XYRangeAreaRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getSeriesPaint(row) : p;
    }
  }

  static class XYRangeBarRendererEx extends ClusteredXYBarRenderer {
    List<SeriesData> seriesData;

    public XYRangeBarRendererEx(List<SeriesData> seriesData) {
      super(0.25f, false);
      this.seriesData = seriesData;
      setUseYInterval(true);
      setShadowVisible(false);

    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }

  }

  static class XYSplineRendererEx extends XYSplineRenderer {
    private List<SeriesData> seriesData;

    public XYSplineRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }
  }

  static class XYStackedAreaRendererEx extends StackedXYAreaRenderer2 {
    private List<SeriesData> seriesData;

    public XYStackedAreaRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }
  }

  static class XYStackedBarRendererEx extends StackedXYBarRenderer {
    List<SeriesData> seriesData;

    public XYStackedBarRendererEx(double margin, List<SeriesData> seriesData) {
      super(margin);
      this.seriesData = seriesData;
    }

    public XYStackedBarRendererEx(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public Paint getItemFillPaint(int row, int column) {
      Paint p = seriesData.get(row).fillColor;
      return p == null ? super.getItemFillPaint(row, column) : p;
    }

    @Override
    public Paint getItemLabelPaint(int row, int column) {
      ChartDataItem item = seriesData.get(row).dataItems.get(column);
      UIColor c = item.getForeground();
      return c == null ? super.getItemLabelPaint(row, column) : c;
    }

    @Override
    public Paint getItemOutlinePaint(int row, int column) {
      Paint p = seriesData.get(row).outlineColor;
      return p == null ? super.getItemOutlinePaint(row, column) : p;
    }
  }

  static class XYToolTipLabelGenerator extends StandardXYItemLabelGenerator implements XYToolTipGenerator {
    List<SeriesData> seriesData;

    public XYToolTipLabelGenerator(List<SeriesData> seriesData) {
      super();
      this.seriesData = seriesData;
    }

    @Override
    public String generateLabelString(XYDataset dataset, int series, int item) {
      SeriesData data = seriesData.get(series);
      return data.getPointLabel(item, null);
    }

    @Override
    public String generateToolTip(XYDataset dataset, int series, int item) {
      SeriesData data = seriesData.get(series);
      ChartDataItem di = data.dataItems.get(item);
      CharSequence s = di.getTooltip();
      return s == null ? "" : s.toString();
    }
  }
}
