/*
 * @(#)BorderPanel.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.widget.iWidget;
import com.jgoodies.forms.layout.CellConstraints;

public class BorderPanel extends aBorderPanel {
  public BorderPanel() {
    super(new BorderLayoutView());
  }

  public BorderPanel(String encodedColumnSpecs, String encodedRowSpecs ) {
    super(new BorderLayoutView(encodedColumnSpecs,encodedRowSpecs));
  }

  public BorderPanel(iWidget context) {
    this();
  }

  public BorderPanel(Object view) {
    super(view);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c == null) {
      c = getComponentAt((Location) constraints);

      if (c != null) {
        remove(c);
      }

      return;
    }

    super.add(c, constraints, position);
  }

  @Override
  public void setHorizontal(boolean horizontal) {
    ((BorderLayoutView) view).setHorizontal(horizontal);
  }

  public void setPadding(UIInsets in) {
    ((BorderLayoutView) view).setPadding(in);
  }

  @Override
  public void setTopBottomPriority(boolean topBottomPriority) {
    ((BorderLayoutView) view).setTopBottomPriority(topBottomPriority);
  }

  @Override
  public CellConstraints getCellConstraints(iPlatformComponent component) {
    return ((BorderLayoutView) view).getCellConstraints(component);
  }

  @Override
  protected BorderLayout getBorderLayout() {
    return (BorderLayout) ((BorderLayoutView) view).getFormLayout();
  }

  @Override
  protected iPlatformComponent getComponentAt(Location location) {
    return ((BorderLayoutView) view).getComponentAt(location);
  }

  @Override
  protected CellConstraints getConstraints(Location location) {
    return ((BorderLayoutView) view).getConstraints(location);
  }
}
