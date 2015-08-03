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

import com.appnativa.rare.ui.Displayed;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;

/**
 * An interface for painters
 *
 * @author Don DeCoteau
 */
public interface iPainter {

  /** horizontal orientation */
  public static int HORIZONTAL = 2;

  /** unknown orientation */
  public static int UNKNOWN = 0;

  /** vertical orientation */
  public static int VERTICAL = 1;

  /**
   * Disposes of the painter if it is disposable
   */
  void dispose();

  void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation);

  /**
   * References this painter.
   *
   * @return a handle to this painter if it can be shared or copied
   */
  iPlatformPainter reference();

  /**
   * Sets the display criteria for the painter
   *
   * @param displayed the display criteria for the painter
   */
  void setDisplayed(Displayed displayed);

  /**
   * Sets whether or not this painter can be disposed of
   *
   * @param disposable true if the painter is disposable; false otherwise
   */
  void setDisposable(boolean disposable);

  /**
   * Sets whether the painter is enabled
   *
   * @param enabled true if the overlay is enabled; false otherwise
   */
  void setEnabled(boolean enabled);

  /**
   * Sets the render space for the painter
   *
   * @param space the render space for the painter
   */
  void setRenderSpace(RenderSpace space);

  /**
   * Sets the render type for the painter
   *
   * @param type the render type for the painter
   */
  void setRenderType(RenderType type);

  /**
   * Gets the background color for the painter
   *
   * @return  the background color for the painter
   */
  UIColor getBackgroundColor();

  /**
   * Gets the display criteria for the image
   *
   * @return the display criteria for the image
   */
  Displayed getDisplayed();

  /**
   * Returns a platform paint object that can be passed to a graphics context
   * @param width
   * @param height
   * @return the paint object of null if the painter cannot be represented as a paint object
   */
  iPlatformPaint getPaint(float width, float height);

  /**
   * Gets the render space for the painter
   *
   * @return the render space for the painter
   */
  RenderSpace getRenderSpace();

  /**
   * Gets the render type for the painter
   *
   * @return the render type for the painter
   */
  RenderType getRenderType();

  /**
   * Gets whether or not this painter can be disposed of
   *
   * @return true if the painter is disposable; false otherwise
   */
  boolean isDisposable();

  /**
   * Gets whether or not this painter has been disposed
   *
   * @return true if the painter has been disposed; false otherwise
   */
  boolean isDisposed();

  /**
   * Gets whether the painter is enabled
   *
   * @return true if the overlay is visible; otherwise
   */
  boolean isEnabled();
}
