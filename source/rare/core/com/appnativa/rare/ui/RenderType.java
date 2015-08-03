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

import java.util.Locale;

/**
 * The render type for identifying how things get rendered
 * within the context of a container
 */
public enum RenderType {

  /** renders the image as at the passed in x,y coordinates */
  XY,

  /** item is tiled to fit in the available space */
  TILED,

  /** item is tiled to fit in the available horizontal space */
  HORIZONTAL_TILE,

  /** item is tiled to fit in the available vertical space */
  VERTICAL_TILE,

  /** item is centered */
  CENTERED,

  /** item is stretched to fit in the available space */
  STRETCHED,

  /** item is stretched to fit the available width */
  STRETCH_WIDTH,

  /** item is stretched to fit the available height */
  STRETCH_HEIGHT,

  /** item is placed in the upper left */
  UPPER_LEFT,

  /** item is placed in the upper right */
  UPPER_RIGHT,

  /** item is placed in the upper left */
  LOWER_LEFT,

  /** item is placed in the lower right */
  LOWER_RIGHT,

  /** item is placed in the middle on the top */
  UPPER_MIDDLE,

  /** item is placed in the middle on the bottom */
  LOWER_MIDDLE,

  /** item is placed in the middle on the upper left */
  LEFT_MIDDLE,

  /** item is placed in the middle on the right */
  RIGHT_MIDDLE,

  /** item is stretched to fit the available height and placed in the middle */
  STRETCH_HEIGHT_MIDDLE,

  /** item is stretched to fit the available height and placed in the middle */
  STRETCH_WIDTH_MIDDLE;

  /**
   * Returns the render type for the specified name
   * @param name the name
   * @return the render type for the specified name or null
   */
  public static RenderType valueOfEx(String name) {
    if (name == null) {
      return null;
    }

    try {
      return valueOf(name.toUpperCase(Locale.US));
    } catch(Exception e) {
      return null;
    }
  }
}
