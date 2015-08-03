/*
 * @(#)UIBackBorder.java   2011-07-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

/**
 *
 * @author Don DeCoteau
 */
public class UIBackBorder extends aUIBackBorder {
  public UIBackBorder(UIColor color) {
    super(color);
  }

  public UIBackBorder(UIColor color, float thickness) {
    super(color, thickness);
  }

  public UIBackBorder(UIColor color, float thickness, float arc) {
    super(color, thickness, arc);
  }

  public UIBackBorder(UIColor color, float thickness, float arcWidth, float arcHeight) {
    super(color, thickness, arcWidth, arcHeight);
  }
}
