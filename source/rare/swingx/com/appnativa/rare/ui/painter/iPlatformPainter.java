/*
 * @(#)iPlatformPainter.java   2010-07-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.painter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * An interface for painters
 *
 * @author Don DeCoteau
 */
public interface iPlatformPainter extends iPainter {
	
  void paint(Component c, Graphics g, boolean hasValue);

  /**
   * Performs the painting
   *
   * @param c the component being painted
   * @param g the graphics object
   * @param x the x position for the shape
   * @param y the y position for the shape
   * @param width the width of the area to get a shape for
   * @param height the height of the area to get a shape for
   * @param hasValue true if the component has a value; false otherwise
   * @param orientation the orientation. One of iPainter.HORIZONTAL, iPainter.VERTICAL, iPainter.UNKNOWN
   */
  void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation);

}
