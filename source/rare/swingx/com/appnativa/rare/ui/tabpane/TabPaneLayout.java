/*
 * @(#)TabPaneLayout.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;

public class TabPaneLayout extends aTabPaneLayout {
  Boolean oldWider;

  public TabPaneLayout(iContentManager cm) {
    super(cm);
  }

  @Override
  protected void adjustMoreButtonPopupLocation(UIRectangle bounds) {
    if (location == Location.LEFT) {
      UIDimension d = moreButton.getPreferredSize();

      if (bounds.x == 0) {
        bounds.x = d.height;
      }

      if (bounds.y == 0) {
        bounds.y = -(d.width / 2 + d.height);
      }
    }
  }
  
  @Override
  protected void setContentRenderType(RenderType type) {
    FrameView fv=(FrameView) contentArea.getView();
    fv.setViewRenderType(type);
  }

  @Override
  protected void configureRotation(int degrees) {}
}
