/*
 * @(#)iPlatformItemRenderer.java   2008-10-18
 * 
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;


/**
 * An interface for item renderers
 *
 * @author Don DeCoteau
 */
public interface iPlatformItemRenderer extends iItemRenderer{
	
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
