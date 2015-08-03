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

package com.appnativa.rare.util;

import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.util.Helper;
import com.appnativa.util.iStringConverter;

/**
 *
 * @author Don DeCoteau
 */
public class ItemStringConverter implements iStringConverter<RenderableDataItem> {
  private int column;

  /**
   * Creates a new instance of ItemStringConverter
   *
   * @param col the column of the item to use in the conversion
   */
  public ItemStringConverter(int col) {
    column = col;
  }

  /**
   *  Get the column of the item to use in the conversion
   *
   * @return the column of the item to use in the conversion
   */
  public int getColumn() {
    return column;
  }

  /**
   * Sets the column of the item to use in the conversion
   *
   * @param col the column
   */
  public void setColumn(int col) {
    this.column = col;
  }

  @Override
  public String toString(RenderableDataItem item) {
    String s = null;

    if (item != null) {
      if (column == -1) {
        s = Helper.toString(item.getItems(), "\t");
      } else {
        Object o = item.getItemEx(column);

        if (o != null) {
          s = o.toString();
        }
      }
    }

    return (s == null)
           ? ""
           : s;
  }
}
