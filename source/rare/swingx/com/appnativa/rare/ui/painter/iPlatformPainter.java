/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
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
