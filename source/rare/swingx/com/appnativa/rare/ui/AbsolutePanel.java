/*
 * @(#)AbsolutePanel.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.widget.iWidget;

public class AbsolutePanel extends aAbsolutePanel {
  public AbsolutePanel(iWidget Context) {
    this(new AbsolutePanelView());
  }

  public AbsolutePanel(Object view) {
    super(view);
  }

  static class AbsolutePanelView extends UtilityPanel {
    @Override
    public void getMinimumSize(UIDimension size) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.getMinimumSize(size);
    }

    public void getPreferredSize(UIDimension size) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.getPreferredSize(size);
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      AbsolutePanel p = (AbsolutePanel) Component.fromView(this);

      p.layout(width, height);
    }
  }
}
