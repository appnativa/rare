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

import com.appnativa.rare.platform.apple.ui.view.View;

/**
 * Interface for components that can be used as renderers
 *
 * @author Don DeCoteau
 */
public interface iPlatformRenderingComponent extends iRenderingComponent {
  Object createNewNativeView();

  /**
   * Disposes of the renderer
   */
  @Override
  void dispose();

  /**
   * Returns a new copy of the rendering component
   * @return  a new copy of the rendering component
   */
  iPlatformRenderingComponent newCopy();

  void prepareForReuse(int row, int column);

  void setColumnWidth(int width);

  void setNativeView(Object proxy);

  void setRenderingView(View view);

  /**
   * Gets the actual component that will do the rending.
   * This method must be called in order to render the content
   * into the component.
   *
   * @param value the value to be rendered
   * @param item the item for the associated value (can be null)
   * @return the actual component that will do the rending
   */
  iPlatformComponent getComponent(CharSequence value, RenderableDataItem item);

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
}
