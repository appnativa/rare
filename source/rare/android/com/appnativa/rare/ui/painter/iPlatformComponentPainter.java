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

import android.graphics.Canvas;

import android.view.View;

/**
 *
 * @author Don DeCoteau
 */
public interface iPlatformComponentPainter extends iComponentPainter {
  void setPainterHolder(PainterHolder painterHolder);

  PainterHolder getPainterHolder();

  /**
   * Performs the painting
   *
   * @param c
   *          the component being painted
   * @param g
   *          the graphics object
   * @param x
   *          the x position for the shape
   * @param y
   *          the y position for the shape
   * @param width
   *          the width of the area to get a shape for
   * @param height
   *          the height of the area to get a shape for
   * @param orientation
   *          the orientation. One of iPlatformPainter.HORIZONTAL, iPlatformPainter.VERTICAL,
   *          iPlatformPainter.UNKNOWN
   * @param end
   *          end if this is the end of the draw cycle
   */
  void paint(View c, Canvas g, float x, float y, float width, float height, int orientation, boolean end);
}
