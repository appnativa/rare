/*
 * @(#)NavigatorWidget.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.widget;

import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.ui.NavigatorPanel;
import com.appnativa.rare.ui.aNavigatorPanel;
import com.appnativa.rare.viewer.iContainer;

/**
 *  A widget that provides the navigation of a hierarchy
 *  as a horizontal series of  click-able buttons that identify
 *  the path taken.
 *  <p>
 *  A home and back button can be provided to allow the user to back
 *  up or go directly back to the beginning.
 *  </p>
 *
 *  @author Don DeCoteau
 */
public class NavigatorWidget extends aNavigatorWidget {

  /**
   *  Constructs a new instance
   *
   *  @param parent the widget's parent
   */
  public NavigatorWidget(iContainer parent) {
    super(parent);
  }

  @Override
  protected Object createBorderLayoutView() {
    return new BorderLayoutView() {
    };
  }

  @Override
  protected aNavigatorPanel createNavigatorPanel() {
    return new NavigatorPanel(this, getAppContext().getResourceAsIcon("Rare.icon.navigatorBack"));
  }
}
