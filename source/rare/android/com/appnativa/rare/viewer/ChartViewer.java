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

import com.appnativa.rare.spot.Chart;
import com.appnativa.rare.spot.ScrollPane;
import com.appnativa.rare.ui.chart.ChartDataItem;

/**
 * A viewer that provides for the management and display of charts
 *
 * @author Don DeCoteau
 */
public class ChartViewer extends aChartViewer {
  protected boolean horizontalScrollingEnabled;
  protected boolean verticalScrollingEnabled;

  /**
   * Constructs a new instance
   */
  public ChartViewer() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param fv
   *          the widget's parent
   */
  public ChartViewer(iContainer parent) {
    super(parent);
  }

  public ChartDataItem addSeries(ChartDataItem series) {
    ChartDataItem aseries = super.addSeries(series);

    chartHandler.setHorizontalScrollingEnabled(chartComponent, horizontalScrollingEnabled);
    chartHandler.setVerticalScrollingEnabled(chartComponent, verticalScrollingEnabled);

    return aseries;
  }

  public void setHorizontalScrollingEnabled(boolean enable) {
    horizontalScrollingEnabled = enable;
    chartHandler.setHorizontalScrollingEnabled(chartComponent, enable);
  }

  public void setVerticalScrollingEnabled(boolean enable) {
    verticalScrollingEnabled = enable;
    chartHandler.setVerticalScrollingEnabled(chartComponent, enable);
  }

  protected void configureEx(Chart cfg) {
    super.configureEx(cfg);

    ScrollPane sp = cfg.getScrollPane();

    if (sp != null) {
      switch(sp.horizontalScrollbar.intValue()) {
        case ScrollPane.CHorizontalScrollbar.show_always :
        case ScrollPane.CHorizontalScrollbar.show_as_needed :
          setHorizontalScrollingEnabled(true);

          break;

        default :
          break;
      }

      switch(sp.verticalScrollbar.intValue()) {
        case ScrollPane.CVerticalScrollbar.show_always :
        case ScrollPane.CVerticalScrollbar.show_as_needed :
          setVerticalScrollingEnabled(true);

          break;

        default :
          break;
      }
    }
  }
}


;
