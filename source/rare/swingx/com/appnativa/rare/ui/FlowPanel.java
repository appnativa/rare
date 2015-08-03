/*
 * @(#)FlowPanel.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.JViewport;

import com.appnativa.rare.platform.swing.ui.view.UtilityPanel;
import com.appnativa.rare.widget.iWidget;

public class FlowPanel extends aFlowPanel {
  public FlowPanel(iWidget context) {
    this(new FlowPanelView());
    needsMultitplePasses = true;
  }

  public FlowPanel(Object view) {
    super(view);
  }

  static class FlowPanelView extends UtilityPanel {
    @Override
    public void addNotify() {
      super.addNotify();

      if (getParent() instanceof JViewport) {
        FlowPanel p = (FlowPanel) Component.fromView(this);

        p.needsMultitplePasses = false;
      }
    }

    @Override
    public void getMinimumSize(UIDimension size) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.getMinimumSize(size);
    }

    public UIDimension getPreferrredSize(UIDimension size, float maxWidth) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.getPreferredSize(size, maxWidth);

      return size;
    }

    @Override
    protected void layoutContainerEx(int width, int height) {
      FlowPanel p = (FlowPanel) Component.fromView(this);

      p.layout(width, height);
    }
  }
}
