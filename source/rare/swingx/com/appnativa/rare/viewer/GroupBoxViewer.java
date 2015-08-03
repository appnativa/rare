/*
 * @(#)GroupBoxViewer.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *  A widget that provides a visual grouping of other widgets.
 *
 *  @author Don DeCoteau
 */
public class GroupBoxViewer extends aGroupBoxViewer {

  /**
   *  Constructs a new instance
   */
  public GroupBoxViewer() {
    super();
  }

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public GroupBoxViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected iPlatformComponent createFormsPanel() {
    FormsPanel p= new FormsPanel();
    if (isDesignMode()) {
      p.setDefaultMinimumSize(100, 100, true);
    }

    return p;
  }

  @Override
  protected iPlatformComponent createFormsPanel(int rows, int cols) {
    FormsPanel p = new FormsPanel(this, rows, cols);

    if (isDesignMode()) {
      p.setDefaultMinimumSize(100, 100, true);
    }

    return p;
  }
}
