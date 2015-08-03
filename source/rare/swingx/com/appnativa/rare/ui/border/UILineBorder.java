/*
 * @(#)UILineBorder.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

/**
 *  A line border that supports non rectangular lines
 *
 *  @author Don DeCoteau
 */
public class UILineBorder extends aUILineBorder {
  public UILineBorder(UIColor color) {
    super(color);
  }

  public UILineBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UILineBorder(UIColor color, float thickness, boolean roundedCorners) {
    super(color, thickness, roundedCorners);
  }

  public UILineBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public UILineBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }

}
