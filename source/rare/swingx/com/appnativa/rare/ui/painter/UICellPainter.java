/*
 * @(#)CellPainter.java   2012-04-28
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 * An object that can paint a rectangular cell
 *
 * @author Don DeCoteau
 */
public class UICellPainter extends aUICellPainter {

  /** a cell painter that does nothing */
  public static UICellPainter NULL_CELLPAINTER = new UICellPainter() {
    @Override
    public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {}
  };

  /**
   * Creates a new instance
   */
  public UICellPainter() {}

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    paint(c, (Graphics2D) g, c.getX(), c.getY(), c.getWidth(), c.getHeight(), hasValue, iPainter.UNKNOWN);
  }


  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    SwingGraphics sg = new SwingGraphics(g, c);

    paint(sg, x, y, width, height, orientation);
  }

  public iPlatformPaint getPaint(float x, float y, float width, float height) {
    return null;
  }
}
