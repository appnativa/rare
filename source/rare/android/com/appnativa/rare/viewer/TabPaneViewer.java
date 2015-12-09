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

import com.appnativa.rare.spot.TabPane;

/**
 * A viewer that arranges a section of the screen into
 * a set of regions only one of which can be selected at a given time.
 * A region can be selected by clicking on its tab.
 *
 * @author Don DeCoteau
 */
public class TabPaneViewer extends aTabPaneViewer {

  /**
   * Constructs a new instance
   */
  public TabPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv the widget's parent
   */
  public TabPaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected int getDefaultTabStyle() {
    return TabPane.CTabStyle.box;
  }
}
