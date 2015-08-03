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
 *
 * @author Don DeCoteau
 */
public interface iBackgroundPainter extends iPlatformPainter {

  /**
   * Gets the background color for the painter
   *
   * @return  the background color for the painter
   */
  @Override
  public UIColor getBackgroundColor();

  /**
   * Returns whether this painter is a single color painter
   *
   * @return true if this painter is a single color painter; false otherwise
   */
  public boolean isSingleColorPainter();

  /**
   * Returns a painter that paints the background using
   * the specified alpha
   *
   * @param alpha the alpha
   * @return the new painter
   */
  iBackgroundPainter alpha(int alpha);
}
