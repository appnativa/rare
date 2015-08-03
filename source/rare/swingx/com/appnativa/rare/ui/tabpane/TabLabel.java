/*
 * @(#)TabLabel.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIScreen;

public class TabLabel extends ActionComponent implements iTabLabel {
  public TabLabel() {
    super(new LabelView());
  }

  public TabLabel(UIAction a) {
    this();
    setAction(a);
  }

  @Override
  public void setIsSelectedTab(boolean selected) {
    setSelected(selected);
    revalidate();
  }
 
  @Override
  public void setMinHeight(int minTabHeight) {}

  @Override
  public UIDimension getPreferredSize(UIDimension size) {
    size       = super.getPreferredSize(size);
    size.width += UIScreen.platformPixels(4);

    return size;
  }
}
