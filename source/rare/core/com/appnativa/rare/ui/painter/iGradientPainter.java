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

import com.appnativa.rare.ui.UIColor;

/**
 * An interface for components that support gradient painting.
 *
 * @author Don DeCoteau
 */
public interface iGradientPainter extends iBackgroundPainter {

  /** enumeration of paint directions */
  public static enum Direction {
    VERTICAL_TOP, VERTICAL_BOTTOM, HORIZONTAL_LEFT, HORIZONTAL_RIGHT, DIAGONAL_TOP_LEFT, DIAGONAL_TOP_RIGHT,
    DIAGONAL_BOTTOM_LEFT, DIAGONAL_BOTTOM_RIGHT, CENTER
  }

  /** enumeration of gradient paint types */
  public static enum Type {
    LINEAR, LINEAR_REPEAT, LINEAR_REFLECT, RADIAL
  }

  /**
   * Sets the the direction of the gradient
   *
   * @param direction the direction of the gradient
   */
  void setGradientDirection(Direction direction);

  /**
   * Sets the magnitude for the gradient
   *
   * @param magnitude the magnitude for the gradient
   */
  void setGradientMagnitude(int magnitude);

  /**
   * Sets the gradient paint options.
   * If the colors are null then the background color of the component will be utilized to
   * construct the start and end colors for the gradient
   *
   * @param start the start color
   * @param end the end color
   * @param direction the direction of gradient
   */
  void setGradientPaint(UIColor start, UIColor end, Direction direction);

  /**
   * Sets the gradient paint options.
   *
   * @param type the type of gradient color
   * @param direction the direction of gradient
   * @param colors the gradient colors
   * @param distribution the gradient color distribution
   * @param magnitude the magnitude
   */
  void setGradientPaint(Type type, Direction direction, UIColor[] colors, float[] distribution, int magnitude);

  /**
   * Sets whether gradient painting is enabled
   *
   * @param enabled true if gradient painting is enabled; false otherwise
   */
  void setGradientPaintEnabled(boolean enabled);

  /**
   * Sets the type of gradient
   *
   * @param type the type of gradient
   */
  void setGradientType(Type type);

  /**
   * Returns the number of gradient colors
   *
   * @return the number of gradient colors
   */
  int getGradientColorCount();

  /**
   * Returns the gradient colors. May c
   * The array may contain null values
   *
   * @return the gradient colors
   */
  UIColor[] getGradientColors();

  /**
   * Resolves and returns the gradient colors
   * The array values are guaranteed to be non-null
   *
   * @param bg the background color to use to resolve null color values
   *          if the gradient's background color was not set
   * @return the gradient colors
   */
  UIColor[] getGradientColors(UIColor bg);

  /**
   * Resolves and copies the gradient colors to the specified array
   * The array values are guaranteed to be non-null
   *
   * @param output a properly sized array to hold the colors
   * @param bg the background color to use to resolve null color values
   *          if the gradient's background color was not set
   */
  void getGradientColors(UIColor[] output, UIColor bg);

  /**
   * Returns the direction of the gradient
   *
   * @return the direction of the gradient
   */
  Direction getGradientDirection();

  /**
   * Returns the gradient distribution
   *
   * @return the gradient distribution
   */
  float[] getGradientDistribution();

  /**
   * Returns the gradient start color
   *
   * @param bg the background color
   *
   * @return the gradient start color
   */
  UIColor getGradientEndColor(UIColor bg);

  /**
   * Returns the magnitude for the gradient
   *
   * @return the magnitude for the gradient
   */
  int getGradientMagnitude();

  /**
   * Returns the gradient end color
   *
   * @param bg the background color
   *
   * @return the gradient end color
   */
  UIColor getGradientStartColor(UIColor bg);

  /**
   * Returns the type or gradient
   *
   * @return the type of gradient (normal, linear, radial)
   */
  Type getGradientType();

  /**
   * Returns whether gradient painting is enabled
   *
   * @return true if gradient painting is enabled; false otherwise
   */
  boolean isGradientPaintEnabled();

  /**
   * Sets whether the background is all filled prior to the gradient being painted
   *
   * @param alwaysFill true if  the background is all filled prior to the gradient being painted; false otherwise
   */
  void setAlwaysFill(boolean alwaysFill);

  /**
   * Sets the radius for a radial gradient
   * @param radius the radius for a radial gradient
   */
  void setGradientRadius(float radius);

  /**
   * Gets the radius for a radial gradient
   * @return the radius for a radial gradient
   */
  float getGradientRadius();

  /**
   * Returns whether the background is all filled prior to the gradient being painted
   *
   * @return true if  the background is all filled prior to the gradient being painted; false otherwise
   */
  boolean isAlwaysFill();
}
