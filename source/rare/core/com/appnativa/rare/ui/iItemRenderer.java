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

import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.util.iStringConverter;

/**
 * An interface for item renderers
 *
 * @author Don DeCoteau
 */
public interface iItemRenderer {

  /**
   * Sets the converter to use to convert items to strings
   *
   * @param converter the converter to use to convert items to strings
   */
  void setConverter(iStringConverter converter);

  /**
   * sets the insets for items
   *
   * @param insets the insets for items
   */
  void setInsets(UIInsets insets);

  /**
   * Sets a description to use to render items
   * @param itemDescription the item description
   */
  void setItemDescription(Column itemDescription);

  /**
   * Sets the custom paint to use for non-focused selected items
   *
   * @param pb the custom paint to use for non-selected
   */
  void setLostFocusSelectionPaint(PaintBucket pb);

  /**
   * Sets the custom paint to use for selected items
   *
   * @param pb the custom paint to use for selected
   */
  void setSelectionPaint(PaintBucket pb);

  /**
   * Sets whether selections are painted
   *
   * @param painted true if selections are painted; false otherwise
   */
  void setSelectionPainted(boolean painted);

  /**
   * Gets the converter to use to convert items to strings
   *
   * @return the converter that is used to convert items to strings
   */
  iStringConverter getConverter();

  /**
   * Gets the insets for items
   *
   * @return the insets for items
   */
  UIInsets getInsets();

  /**
   * Sets a description to use to render items
   * @return the item description or null;
   */
  Column getItemDescription();

  /**
   * Gets the custom paint that is use for non-focused selected items
   *
   * @return the custom paint that is used for non selected items or null if selection painting
   * is disabled
   *
   * @see #setLostFocusSelectionPaint(com.appnativa.rare.ui.painter.PaintBucket)
   */
  PaintBucket getLostFocusSelectionPaint();

  /**
   * Gets the custom paint that is use for selected items
   *
   * @return the custom paint that is used for selected items or null if selection painting
   * is disabled
   *
   * @see #setSelectionPaint(com.appnativa.rare.ui.painter.PaintBucket)
   */
  PaintBucket getSelectionPaint();

  /**
   * Gets the custom paint that is use for selected items fo use by
   * a painter that handles selection painting
   *
   * @return the custom paint that is used for selected items or null if selection painting
   * is disabled or the render is set to handle selection painting
   *
   * @see #setSelectionPaint(com.appnativa.rare.ui.painter.PaintBucket)
   */
  PaintBucket getSelectionPaintForExternalPainter(boolean focused);

  /**
   * Gets the custom paint that is use for pressed items
   *
   * @return the custom paint that is used for pressed items or null if non
   *         was explicitly set
   *
   */
  PaintBucket getPressedPaint();

  /**
   * Sets the custom paint to use for pressed items
   *
   * @param pb the custom paint to use for pressed items
   */
  void setPressedPaint(PaintBucket pb);

  /**
   * Gets whether selections are painted
   *
   * @return true if selections are painted; false otherwise
   */
  boolean isSelectionPainted();
}
