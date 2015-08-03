/*
 * @(#)aPlatformIcon.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;

public abstract class aPlatformIcon implements iPlatformIcon, Cloneable {
  public aPlatformIcon() {}

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    SwingGraphics graphics = SwingGraphics.fromGraphics(g, c, null);
    paint(graphics, x, y, getIconWidth(), getIconHeight());
    graphics.clear();
    
  }
}
