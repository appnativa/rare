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

package com.appnativa.rare.ui.text;

import com.appnativa.rare.ui.UIColor;

/**
 * Platform independent interface to text attributes
 *
 * @author Don DeCoteau
 *
 */
public interface iTextAttributes {

  /**
   * Get the platform specific attributes object
   *
   * @return the platform specific attributes object
   */
  Object getPlatformAttributes();

  /**
   * Checks whether the bold attribute is set.
   *
   * @return true if set else false
   */
  boolean isBold();

  /**
   * Checks whether the italic attribute is set.
   *
   * @return true if set else false
   */
  boolean isItalic();

  /**
   * Checks whether the underline attribute is set.
   *
   * @return true if set else false
   */
  boolean isUnderline();

  /**
   * Checks whether the superscript attribute is set.
   *
   * @return true if set else false
   */
  boolean isSuperscript();

  /**
   * Checks whether the subscript attribute is set.
   *
   * @return true if set else false
   */
  boolean isSubscript();

  /**
   * Checks whether the strike-through attribute is set.
   *
   * @return true if set else false
   */
  boolean isStrikeThrough();

  /**
   * Gets the font family setting from the attribute list.
   *
   * @return the font family
   */
  String getFontFamily();

  /**
   * Gets the font size setting from the attribute list.
   *
   * @return the font size, the system font size is the default
   */
  float getFontSize();

  /**
   * Gets the foreground color setting
   *
   * @return the color or null if the set does no contain a foreground color
   */
  UIColor getForegroundColor();

  /**
   * Gets the background color setting
   *
   * @return the color or null if the set does no contain a background color
   */
  UIColor getBackgroundColor();

  /**
   * Determines if the two attribute sets are equivalent.
   *
   * @param attr
   *          an attribute set
   * @return true if the sets are equivalent
   */
  boolean isEqual(iTextAttributes attr);
}
