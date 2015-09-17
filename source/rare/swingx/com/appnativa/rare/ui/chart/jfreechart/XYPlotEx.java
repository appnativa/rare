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

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.painter.iPainter;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

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
