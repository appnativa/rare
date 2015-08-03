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

import com.appnativa.rare.Platform;
import com.appnativa.rare.spot.SplitPane;

/**
 * A viewer that splits its section of the screen
 * into multiple resizable regions.
 *
 * @author     Don DeCoteau
 */
public class SplitPaneViewer extends aSplitPaneViewer {

  /**
   * Constructs a new instance
   */
  public SplitPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public SplitPaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  public void onConfigurationWillChange(Object newConfig) {
    getSplitPanePanel().checkOrientation(newConfig);
    super.onConfigurationWillChange(newConfig);
  }

  protected static void registerForUse() {
    Platform.getAppContext().registerWidgetClass(Platform.getSPOTName(SplitPane.class), SplitPaneViewer.class);
  }
}
