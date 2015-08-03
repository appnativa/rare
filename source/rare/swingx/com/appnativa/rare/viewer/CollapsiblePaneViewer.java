/*
 * @(#)CollapsiblePaneViewer.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.spot.CollapsiblePane;
import com.appnativa.rare.ui.Container;

/**
 *  A viewer that supports the expansion and collapsing of it contents.
 *
 *  @author     Don DeCoteau
 */
public class CollapsiblePaneViewer extends aCollapsiblePaneViewer {
  public CollapsiblePaneViewer() {
    super();
  }

  public CollapsiblePaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected void configureEx(CollapsiblePane cfg) {
    super.configureEx(cfg);
    if (isDesignMode()) {
      ((Container)formComponent).setDefaultMinimumSize(100, 100, true);
    }
  }
}
