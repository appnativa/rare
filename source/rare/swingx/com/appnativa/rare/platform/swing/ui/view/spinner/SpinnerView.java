/*
 * @(#)SpinnerView.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view.spinner;

import java.awt.Graphics;

import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.aSpinnerComponent;

public class SpinnerView extends JPanelEx {
  UIRectangle rect;

  public SpinnerView() {
    super();
  }

  @Override
  protected void layoutContainerEx(int width, int height) {
    ((aSpinnerComponent) getComponentEx()).layout(width, height);
  }
 
  @Override
  protected void paintChildren(Graphics g) {
    aSpinnerComponent sp = (aSpinnerComponent) getComponentEx();

    if (rect == null) {
      rect = new UIRectangle();
    }

    rect.setBounds(0, 0, getWidth(), getHeight());
    sp.paintButtons(graphics, rect);
    super.paintChildren(g);
  }
}
