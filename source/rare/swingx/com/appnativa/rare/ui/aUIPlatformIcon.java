/*
 * @(#)aUIPlatformIcon.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import java.awt.Component;
import java.awt.Graphics;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;

public abstract class aUIPlatformIcon implements iPlatformIcon {
  SwingGraphics graphics;

  public aUIPlatformIcon() {}

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    graphics = SwingGraphics.fromGraphics(g, c, graphics);
    paint(graphics, x, y, c.getWidth(), c.getHeight());
  }

  @Override
  public iPlatformIcon getDisabledVersion() {
    return this;
  }
}
