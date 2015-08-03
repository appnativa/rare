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

package com.appnativa.rare.ui;

public interface iPath extends iPlatformShape {
  iPath arc(float x, float y, float radius, float startAngle, float endAngle, boolean counterClockwise);

  iPath arcTo(float x1, float y1, float x2, float y2, float radius);

  /**
   * Close the current contour. If the current point is not equal to the
   * first point of the contour, a line segment is automatically added.
   */
  void close();

  /**
   * Add a cubic bezier from the last point, approaching control points
   * (x1,y1) and (x2,y2), and ending at (x3,y3). If no moveTo() call has been
   * made for this contour, the first point is automatically set to (0,0).
   *
   * @param x1 The x-coordinate of the 1st control point on a cubic curve
   * @param y1 The y-coordinate of the 1st control point on a cubic curve
   * @param x2 The x-coordinate of the 2nd control point on a cubic curve
   * @param y2 The y-coordinate of the 2nd control point on a cubic curve
   * @param x3 The x-coordinate of the end point on a cubic curve
   * @param y3 The y-coordinate of the end point on a cubic curve
   */
  iPath cubicTo(float x1, float y1, float x2, float y2, float x3, float y3);

  void drawLine(float x1, float y1, float x2, float y2);

  void drawRect(float x, float y, float width, float height);

  void drawRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight);

  /**
   * Add a line from the last point to the specified point (x,y).
   * If no moveTo() call has been made for this contour, the first point is
   * automatically set to (0,0).
   *
   * @param x The x-coordinate of the end of a line
   * @param y The y-coordinate of the end of a line
   */
  iPath lineTo(float x, float y);

  /**
   * Set the beginning of the next contour to the point (x,y).
   *
   * @param x The x-coordinate of the start of a new contour
   * @param y The y-coordinate of the start of a new contour
   */
  iPath moveTo(float x, float y);

  /**
   * Add a quadratic bezier from the last point, approaching control point
   * (x1,y1), and ending at (x2,y2). If no moveTo() call has been made for
   * this contour, the first point is automatically set to (0,0).
   *
   * @param x1 The x-coordinate of the control point on a quadratic curve
   * @param y1 The y-coordinate of the control point on a quadratic curve
   * @param x2 The x-coordinate of the end point on a quadratic curve
   * @param y2 The y-coordinate of the end point on a quadratic curve
   */
  iPath quadTo(float x1, float y1, float x2, float y2);

  /**
   *    Clear any lines and curves from the path, making it empty.
   *    This does NOT change the fill-type setting.
   */
  void reset();

  /**
   * Rewinds the path: clears any lines and curves from the path but
   * keeps the internal data structure for faster reuse.
   */
  void rewind();

  iPath startLineDrawing(float x, float y, boolean move);

  void translate(float x, float y);

  /**
   * Returns true if the path is empty (contains no lines or curves)
   *
   * @return true if the path is empty (contains no lines or curves)
   */
  boolean isEmpty();

  boolean isPointInPath(float x, float y);
}
