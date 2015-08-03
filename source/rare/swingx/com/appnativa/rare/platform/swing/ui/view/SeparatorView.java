/*
 * @(#)SeparatorView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.aLineHelper.Line;

public class SeparatorView extends LineView {
  boolean forToolbar;

  public SeparatorView(boolean forToolbar) {
    super();
    this.forToolbar = forToolbar;

    Line l = getLineHelper().createLine();

    l.setLeftOffset(2);
    l.setRightOffset(2);
    getLineHelper().addLine(l);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    if (!forToolbar) {
      setHorizontal(width > height);
    }
  }

  public void setHorizontal(boolean horizontal) {
    getLineHelper().setHorizontal(horizontal);
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    size.width = 8;
    size.height = 8;
  }

  @Override
  public void getPreferredSize(UIDimension size, int maxView) {
    size.width = 8;
    size.height = 8;
  }
}
