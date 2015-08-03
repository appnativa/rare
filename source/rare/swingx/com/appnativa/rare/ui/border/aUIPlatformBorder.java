/*
 * @(#)UIAbstractBorder.java   2011-03-25
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.border;

import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aUIPlatformBorder extends aUIBorder implements iPlatformBorder {
  @Override
  public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
    iPlatformComponent pc = Component.fromView((JComponent) c);
    iPlatformGraphics  pg = (pc == null)
                            ? new SwingGraphics(g, c)
                            : pc.graphicsWrap(g);

    paint(pg, x, y, width, height, false);
    paint(pg, x, y, width, height, true);

    if (pc == null) {
      ((SwingGraphics) pg).clear();
    } else {
      pc.graphicsUnwrap(pg);
    }
  }

  @Override
  public Insets getBorderInsets(java.awt.Component c) {
    return SwingHelper.setInsets(null, getBorderInsets((UIInsets) null));
  }

 @Override
  public boolean isBorderOpaque() {
    return false;
  }
}
