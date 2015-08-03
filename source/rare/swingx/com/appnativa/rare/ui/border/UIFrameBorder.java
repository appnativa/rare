/*
 * @(#)UIFrameBorder.java   2007-07-10
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

/**
 *
 * @author Don DeCoteau
 */
public class UIFrameBorder extends aUIFrameBorder {
  public UIFrameBorder(int bevelType) {
    super(bevelType);
  }

  public UIFrameBorder(int bevelType, UIColor highlight, UIColor shadow) {
    super(bevelType, highlight, shadow);
  }

  public UIFrameBorder(int bevelType, UIColor highlightOuterColor, UIColor highlightInnerColor,
                       UIColor shadowOuterColor, UIColor shadowInnerColor) {
    super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
  }
}
