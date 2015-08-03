/*
 * @(#)UIIconBorder.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformPainter;

/**
 *
 *  @author Don DeCoteau
 */
public class UIIconBorder extends aUIIconBorder {
  public UIIconBorder(UIInsets insets) {
    super(insets);
  }

  public UIIconBorder(int top, int left, int bottom, int right) {
    super(top, left, bottom, right);
  }

  @Override
  protected void paintIcon(iPlatformIcon icon, iPlatformGraphics g, float x, float y) {
    icon.paintIcon(g.getView(),g.getGraphics(), UIScreen.snapToPosition(x), UIScreen.snapToPosition(y));
  }

  @Override
  protected void paintPainter(iPlatformPainter p, iPlatformGraphics g, float x, float y, float width, float height) {
    p.paint(g, x, y, width, height, iPainter.UNKNOWN);
  }

  @Override
  protected void paintPattern(UIImage image, iPlatformGraphics g, float x, float y, float width, float height) {}
}
