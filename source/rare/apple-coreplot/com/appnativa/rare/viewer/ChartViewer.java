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

import com.appnativa.rare.ui.chart.coreplot.ChartHandler;
import com.appnativa.rare.viewer.aChartViewer;
import com.appnativa.rare.viewer.iFormViewer;

/**
 * A viewer that provides for the management and display of charts
 *
 * @author Don DeCoteau
 */
public class ChartViewer extends aChartViewer {

  /**
   * Constructs a new instance
   */
  public ChartViewer() {
    super();
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public ChartViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    if (chartHandler != null) {
      ((ChartHandler) chartHandler).reloadCharts(chartComponent, chartDefinition);
    }

    super.onConfigurationChanged(reset);
  }
}
