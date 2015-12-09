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

import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public interface iBorder extends Cloneable{

  /**
   * Clips the specified graphics context based on the shape of the border
   * and the specified dimensions
   *
   * @param g the graphics context
   * @param x the x-position
   * @param y the y-position
   * @param width the width of the area
   * @param height the height of the area
   *
   */
  void clip(iPlatformGraphics g, float x, float y, float width, float height);

  /**
   * Returns whether this border clips it contents
   *
   * return true of contexts will be clipped; false otherwise
   */
  boolean clipsContents();

  /**
   * Paints the border
   * @param g the graphics context
   * @param x the x-position
   * @param y the y-position
   * @param width the width of the area
   * @param height the height of the area
   * @param end true if this is the end the painting; false if it is the beginning
   */
  void paint(iPlatformGraphics g, float x, float y, float width, float height, boolean end);

  /**
   * Sets whether the border pads insets to accommodate for arcs
   *
   * @param pad true if insets should be padded for arcs; false otherwise
   */
  void setPadForArc(boolean pad);

  /**
   * Gets the arc height for the border's shape
   *
   * @return the arc height for the border's shape
   */
  float getArcHeight();

  /**
   * Gets the arc width for the border's shape
   *
   * @return the arc width for the border's shape
   */
  float getArcWidth();

  /**
   * Returns the border's insets
   * @param insets optional insets object to set the results into
   * @return the border's insets
   */
  UIInsets getBorderInsets(UIInsets insets);

  /**
   * Returns the border's insets with for just the size of the border
   * with no extra padding
   *
   * @param insets optional insets object to set the results into
   * @return the border's insets
   */
  UIInsets getBorderInsetsEx(UIInsets insets);

  /**
   * Gets if the border pads the insets to accommodate for arcs
   *
   * @return true if insets are padded for arcs; false otherwise
   */
  boolean isPadForArc();

  /**
   * Returns whether the border should be painted last
   *
   * @return true if it should; false otherwise
   */
  boolean isPaintLast();

  /**
   * Returns whether the border is rectangular.
   *
   * @return true if the border is rectangular; false otherwise
   */
  boolean isRectangular();

  /**
   * Returns the fill shape for the border
   * NOTE: This method is meant to be called during painting and it return value should not be saved
   *
   * @param g the graphics context
   *
   * @return the fill shape or null if the shape is rectangular
   */
  iPlatformShape getShape(iPlatformGraphics g, float x, float y, float width, float height);

  /**
   * Gets the path for the border.
   *
   *
   * @param p a path object object reuse (can be null)
   * @param x the x-position
   * @param y the y-position
   * @param width the width for the path
   * @param height the height for the path
   * @param forClip true if the path is meant to be used for clipping; false for painting
   * @return the path for the border or null if the border cannot be defined by a path
   */
  iPlatformPath getPath(iPlatformPath p, float x, float y, float width, float height, boolean forClip);

  /**
   * Handles custom properties for the border.
   * The is use for configuring custom application borders
   * that are created at runtime
   *
   * @param map the map of properties
   */
  void handleCustomProperties(Map map);
  
  /**
   * Returns if the border is focus aware 
   * @return true if it is; false otherwise
   */
  boolean isFocusAware();
  
  /**
   * Returns if the border is enabled state aware 
   * @return true if it is; false otherwise
   */
  boolean isEnabledStateAware();
}
