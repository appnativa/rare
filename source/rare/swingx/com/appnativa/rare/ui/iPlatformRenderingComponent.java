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
 * Interface for components that can be used as renderers
 *
 * @author Don DeCoteau
 */
public interface iPlatformRenderingComponent extends iRenderingComponent {

  /**
   * Create and returns a new copy of the rendering component
   *
   * @return a new rendering component
   */
  iPlatformRenderingComponent newCopy();

  /**
   * Gets the actual component that will do the rending
   *
   * @param value the value to be rendered
   * @param item the item for the associated value (can be null)
   * @return the actual component that will do the rending
   */
  iPlatformComponent getComponent(CharSequence value, RenderableDataItem item);

  /**
   * Prepares the renderer for reuse
   * @param row the new row
   * @param column the new column
   */
  void prepareForReuse(int row, int column);

  /**
   * Gets the actual component that will do the rending for list-style components
   *
   * @param list the list
   * @param value the value to be rendered
   * @param item the item for the associated value (can be null)
   * @param row the row
   * @param isSelected true if the item is selected; false otherwise
   * @param hasFocus true if the item has focus; false otherwise
   * @param col the column description
   * @param rowItem the row the row description
   * @param handleAll true if the renderer should handle configuring every thing; false to only set the data
   * @return the actual component that will do the rending
   */
  iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
                                  boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem,
                                  boolean handleAll);

  void setColumnWidth(int width);
}
