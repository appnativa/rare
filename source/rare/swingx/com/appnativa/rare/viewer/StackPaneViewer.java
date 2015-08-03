/*
 * @(#)StackPaneViewer.java   2012-01-03
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 * A viewer that treats a set of viewers as a stack (like cards). Only one
 * viewer can be active at any given time. The viewers can be referenced by name
 * or index
 *
 * @author Don DeCoteau
 */
public class StackPaneViewer extends aStackPaneViewer {

  /**
   * Constructs a new instance
   */
  public StackPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public StackPaneViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected iPlatformComponent createPanel() {
    ContainerPanel p = new ContainerPanel();

    if (isDesignMode()) {
      p.setDefaultMinimumSize(100, 100, true);
    }

    return p;
  }
}
