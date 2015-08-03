/*
 * @(#)ComponentPainter.java   2011-02-10
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import com.appnativa.rare.ui.ContainerPanel;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 * @author Don DeCoteau
 */
public class UIComponentPainter extends aUIComponentPainter {
  iComposite paintComposite;

  public UIComponentPainter() {
  }

  public static boolean isOkToPaint(iPlatformComponent c, iPlatformBorder border) {
    iParentComponent p = c == null ? null : c.getParent();
    if ((p instanceof ContainerPanel) && ((ContainerPanel) p).isBorderPanel()) {
      return false;
    }

    return true;
  }

  public void setComposite(iComposite composite) {
    paintComposite = composite;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation, boolean end) {
    if (paintComposite != null) {
      g.setComposite(paintComposite);
    }
    super.paint(g, x, y, width, height, orientation, end);
  }
}
