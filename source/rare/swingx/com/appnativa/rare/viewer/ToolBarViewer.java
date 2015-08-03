/*
 * @(#)ToolBarViewer.java   2010-09-27
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.viewer;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.view.SeparatorView;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.LinearPanel;
import com.appnativa.rare.widget.BeanWidget;

/**
 * A viewer that holds a set of commonly used actions that can be general or
 * context sensitive
 *
 * @author Don DeCoteau
 */
public class ToolBarViewer extends aToolBarViewer {
  public ToolBarViewer() {
    super();
  }

  public ToolBarViewer(iContainer parent) {
    super(parent);
  }

  @Override
  protected void createComponents(boolean horizontal) {
    formComponent = dataComponent = new LinearPanel(this, horizontal);

    if (isDesignMode()) {
      ((Container) formComponent).setDefaultMinimumSize(32, 24, true);
    }
  }

  @Override
  protected void setParentHorizontal(BeanWidget widget, boolean horizontal) {
    JComponent c = widget.getDataComponent().getView();
    if (c instanceof SeparatorView) {
      ((SeparatorView) c).setHorizontal(!horizontal);
    }
  }

}
