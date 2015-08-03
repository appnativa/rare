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

package com.appnativa.rare.ui.canvas;

import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.iPlatformComponent;

import java.io.IOException;

/**
 *
 * @author Don DeCoteau
 */
public interface iCanvas {

  /**
   * Gets the component that the canvas paints on
   * @return the component that the canvas paints on
   */
  public iPlatformComponent getCanvasComponent();

  /**
   * Disposes the canvas
   */
  void dispose();

  /**
   * Repaints the canvas
   */
  void repaint();

  /**
   * Clears the canvas;
   */
  void clear();

  /**
   * Creates a data URL for the image the canvas represents
   *
   * @param args the arguments for the URL
   * @return a data URL for the image the canvas represents
   *
   * @throws IOException
   */
  String toDataURL(Object... args) throws IOException;

  /**
   * Sets the height of the canvas. Cause the canvas to be cleared
   *
   * @param height the height
   */
  void setHeight(int height);

  /**
   * Sets the size of the canvas. Cause the canvas to be cleared
   *
   * @param width the width
   * @param height the height
   * @param clear true the clear; false otherwise
   */
  void setSize(int width, int height, boolean clear);

  /**
   * Sets the width of the canvas. Cause the canvas to be cleared
   *
   * @param width the width
   */
  void setWidth(int width);

  /**
   * Gets a rendering context
   *
   * @param type the type of context
   *
   * @return the rendering context
   */
  iContext getContext(String type);

  /**
   * Gets the height of the canvas
   * @return the height
   */
  int getHeight();

  /**
   * Get the image that backs the canvas
   *
   * @param copy true to return a copy; false to return the real image
   * @return the image backing the canvas
   */
  UIImage getImage(boolean copy);

  /**
   * Gets the width of the canvas
   * @return the width
   */
  int getWidth();
}
