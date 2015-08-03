/*
 * @(#)UITitledBorder.java   2011-03-21
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformGraphics;

public class UITitledBorder extends aUITitledBorder {

  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition, UIFont titleFont,
      UIColor titleColor) {
    super(border, title, titleJustification, titlePosition, titleFont, titleColor);
  }

  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition, UIFont titleFont) {
    super(border, title, titleJustification, titlePosition, titleFont);
  }

  public UITitledBorder(iPlatformBorder border, String title, int titleJustification, int titlePosition) {
    super(border, title, titleJustification, titlePosition);
  }

  public UITitledBorder(iPlatformBorder border, String title) {
    super(border, title);
  }

  public UITitledBorder(iPlatformBorder border) {
    super(border);
  }

  public UITitledBorder(String title) {
    super(title);
  }

  @Override
  protected void paintIinnerBorder(iPlatformBorder b, iPlatformGraphics g, float x, float y, float width, float height, boolean last) {
    b.paint(g, x, y, width, height, last);
  }
}
