/*
 * @(#)BasicTabPainter.java   2014-01-03
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.platform.PlatformHelper;

public class BasicTabPainter extends aBasicTabPainter {

  public BasicTabPainter(int cornerSize) {
    super(cornerSize);
  }

  @Override
  protected TabShape createTabShape() {
    return new TabShape(PlatformHelper.createPath(), PlatformHelper.createPath());
  }

//  protected void paintLine(iPlatformGraphics g, int x, int y, int width, int height) {
//    g.setColor((selectedTabBorderColor == null)
//               ? tabBorderColor
//               : selectedTabBorderColor);
//
//    if (isHorizontal()) {
//      g.drawLine(x, y + height - 1f, x + width, y + height - 1f);
//    } else if (position == Location.LEFT) {
//      g.drawLine(x, y + width - 1f, x + height, y + width - 1f);
//    } else {
//      g.drawLine(x, y + width, x + height, y + width);
//    }
//  }

  @Override
  protected boolean isVerticalWhenFindingTabs() {
    return false;
  }
}
