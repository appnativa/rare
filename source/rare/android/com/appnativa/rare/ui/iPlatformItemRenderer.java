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

/**
 * An interface for item renderers
 *
 * @author Don DeCoteau
 */
public interface iPlatformItemRenderer extends iItemRenderer {

  /**
   * Sets the component that will do the actual rendering
   *
   * @param rc the component that will do the actual rendering
   */
  // void setRenderingComponent(iPlatformRenderingComponent rc);

  /**
   * Gets the component that does the actual rendering
   *
   * @return the component that does the actual rendering
   */
  // iPlatformRenderingComponent getRenderingComponent();

  /**
   * Configures an item for rendering
   * @param list the component
   * @param rc the rendering component
   * @param item the item to render
   * @param row the row for the item
   * @param isSelected true if the item is selected; false otherwise
   * @param hasFocus true if the item has focus; false otherwise
   * @param col the column description
   * @param rowItem the row the item belongs to
   * @return the value to display
   */
  CharSequence configureRenderingComponent(iPlatformComponent list, iPlatformRenderingComponent rc,
          RenderableDataItem item, int row, boolean isSelected, boolean hasFocus, Column col,
          RenderableDataItem rowItem);
}
