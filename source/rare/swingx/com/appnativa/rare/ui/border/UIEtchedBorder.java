/*
 * @(#)UIEtchedBorder.java   2011-03-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;

public class UIEtchedBorder extends aUIEtchedBorder {
  public UIEtchedBorder() {
    super();
  }

  public UIEtchedBorder(int etchType) {
    super(etchType);
  }

  public UIEtchedBorder(UIColor highlightColor, UIColor shadowColor) {
    super(highlightColor, shadowColor);
  }

  public UIEtchedBorder(int etchType, UIColor highlightColor, UIColor shadowColor) {
    super(etchType, highlightColor, shadowColor);
  }
}
