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

package com.appnativa.rare;

import com.appnativa.rare.spot.DataItem;
import com.appnativa.rare.ui.RenderableDataItem;

/**
 * An interface for handling callbacks from a data item parser.
 *
 * @author Don DeCoteau
 */
public interface iDataItemParserCallback {

  /**
   * Add a parsed row
   *
   * @param row the row to be added
   */
  public void addParsedRow(RenderableDataItem row);

  /**
   * Creates a new renderable data item from the specified value
   *
   * @param value the value
   *
   * @return the newly created item
   */
  public RenderableDataItem createItem(Object value);

  /**
   * Creates a new renderable data item
   *
   * @param value the item's value
   * @param type the item's data type
   * @param data the item's linked data
   * @param icon the item's display icon reference
   * @param color the item's foreground color reference
   *
   * @return the newly created item
   */
  public RenderableDataItem createItem(Object value, int type, Object data, Object icon, Object color);

  /**
   * Creates a new renderable data item from the specified item configuration
   *
   * @param item the item configuration
   *
   * @return the newly created item
   */
  public RenderableDataItem createItemEx(DataItem item);

  /**
   * Specifies that parsing has been completed
   */
  public void finishedParsing();

  /**
   * Specifies that parsing has started
   */
  public abstract void startedParsing();

  /**
   * Sets the root item for the items being parsed
   *
   * @param item the item to set as the root
   */
  public void setRootItem(RenderableDataItem item);
}
