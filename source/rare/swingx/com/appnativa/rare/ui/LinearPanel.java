/*
 * @(#)LinearPanel.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.FormsView;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.widget.iWidget;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class LinearPanel extends aLinearPanel {
  public LinearPanel(boolean horizontal) {
    this(null, horizontal, null, null);
  }

  public LinearPanel(iWidget context, boolean horizontal) {
    this(context, horizontal, null, null);
  }

  public LinearPanel(iPlatformComponent context, boolean horizontal) {
    this(null, horizontal, null, null);
  }

  public LinearPanel(iWidget context, boolean horizontal, String rspec, String cspec) {
    super(horizontal);
    setSpecs(horizontal, rspec, cspec);
    setView(new FormsView(new FormLayout(colSpec, rowSpec)));
  }

  @Override
  public void setCellPainters(iPlatformPainter[] painters) {
    ((FormsView) view).setCellPainters(painters);
  }

  @Override
  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((FormsView) view).getCellConstraints(component);
  }

  @Override
  public FormLayout getFormLayout() {
    return ((FormsView) view).getFormLayout();
  }
  
  @Override
  protected void updateFormLayout() {
    ((FormsView) view).setFormLayout(new FormLayout(colSpec, rowSpec));
  }
}
