/*
 * @(#)StatusBarViewer.java   2010-07-04
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
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
    FormsView view = new FormsView();
    AStatusBar  sb   = new AStatusBar(view);

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
