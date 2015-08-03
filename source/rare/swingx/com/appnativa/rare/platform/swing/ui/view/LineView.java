/*
 * @(#)LineView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view;

import java.awt.Graphics;

import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.aLineHelper;

public class LineView extends JPanelEx {
  private LineHelper lineHelper;

  public LineView() {
    lineHelper = new LineHelper(true);
    setOpaque(false);
  }

  public aLineHelper getLineHelper() {
    return lineHelper;
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    int th = UIScreen.snapToSize(lineHelper.getThickness());

    size.width  = th;
    size.height = th;
  }

  @Override
  public void getPreferredSize(UIDimension size,int maxWidth) {
    int th = UIScreen.snapToSize(lineHelper.getThickness());

    size.width  = th;
    size.height = th;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    lineHelper.paint(graphics, 0, 0, getWidth(), getHeight());
  }

  class LineHelper extends aLineHelper {
    public LineHelper(boolean horizontal) {
      super(horizontal);
    }

    @Override
    protected void thicknessRecalculated() {}
  }
}
