/*
 * @(#)XYPlotEx.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.chart.jfreechart;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.painter.iPainter;

public class XYPlotEx extends XYPlot {
  private SwingGraphics graphics;

  public XYPlotEx() {
    super();
  }

  public XYPlotEx(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
    super(dataset, domainAxis, rangeAxis, renderer);
  }

  @Override
  protected void fillBackground(Graphics2D g2, Rectangle2D area, PlotOrientation orientation) {
    Paint p = this.getBackgroundPaint();

    if (p == null) {
      return;
    }

    iPainter painter = ChartHelper.getPainter(p);

    if (painter == null) {
      super.fillBackground(g2, area, orientation);

      return;
    }

    Composite originalComposite = g2.getComposite();

    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getBackgroundAlpha()));
    graphics = SwingGraphics.fromGraphics(g2, null, graphics);

    float x = UIScreen.snapToPosition(area.getX());
    float y = UIScreen.snapToPosition(area.getY());
    float w = UIScreen.snapToSize(area.getWidth());
    float h = UIScreen.snapToSize(area.getHeight());

    painter.paint(graphics, x, y, w, h, iPainter.UNKNOWN);
    g2.setComposite(originalComposite);
  }
}
