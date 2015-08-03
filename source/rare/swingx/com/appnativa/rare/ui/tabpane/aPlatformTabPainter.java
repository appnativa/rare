/*
 * @(#)aPlatformTabPainter.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.ui.UIAction;

public abstract class aPlatformTabPainter extends aTabPainter {
  
  @Override
  protected iTabLabel createNewRenderer(UIAction a) {
    return new TabLabel(a);
  }

  
}
