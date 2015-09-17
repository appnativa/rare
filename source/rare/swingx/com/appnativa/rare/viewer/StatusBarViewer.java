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

import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.ui.ProgressBarComponent;
import com.appnativa.rare.ui.aStatusBar;
import com.appnativa.rare.ui.iProgressBar;

/**
 * A widget that displays status information, usually at the bottom
 * of a window. It has built-in support for displaying the current
 * time, the current memory usage information, a progress bar to
 * indicate task progress, and window resizing initiator.
 *
 * @author     Don DeCoteau
 */
public class StatusBarViewer extends aStatusBarViewer {
  public StatusBarViewer() {
    super();
  }

  public StatusBarViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected aStatusBar createStatusBarAndComponents(StatusBar cfg) {
    FormsView  view = new FormsView();
    AStatusBar sb   = new AStatusBar(view);

    formComponent = dataComponent = sb;
    sb.configure(this, (cfg == null)
                       ? 0
                       : cfg.maxHistoryItems.intValue());

    return sb;
  }

  static class AStatusBar extends aStatusBar {
    public AStatusBar(Object view) {
      super(view);
    }

    @Override
    protected iProgressBar createProgressBar() {
      return new ProgressBarComponent();
    }
  }
}
