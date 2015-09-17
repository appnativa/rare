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

package com.appnativa.rare.widget;

import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.ui.iProgressBar;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that displays the progress of some operation.
 *
 *  @author Don DeCoteau
 */
public class ProgressBarWidget extends aProgressBarWidget {

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public ProgressBarWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected iProgressBar createProgressBar(ProgressBar cfg) {
    return getAppContext().getComponentCreator().getProgressBar(this, cfg);
  }
}
