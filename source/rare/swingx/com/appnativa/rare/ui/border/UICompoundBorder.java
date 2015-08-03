/*
 * @(#)UICompoundBorder.java   2010-08-17
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.iPlatformBorder;

/**
 *
 * @author Don DeCoteau
 */
public class UICompoundBorder extends aUICompoundBorder {

  public UICompoundBorder() {
  	super();
  }

  public UICompoundBorder(iPlatformBorder firstBorder, iPlatformBorder secondBorder) {
    super(firstBorder,secondBorder);
  }

  public UICompoundBorder(iPlatformBorder[] borders) {
    super(borders);
  }

 
}
