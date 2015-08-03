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

package com.appnativa.rare.ui.dnd;

import java.util.List;

public interface iTransferSupport {

  /**
   * Returns whether a Copy action can be performed on this item.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canCopy();

  /**
   * Returns whether a Delete action can be performed on this item.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canDelete();

  /**
   * Returns whether the item can import any of the specified data flavors
   *
   * @param flavors the set of data flavors to test
   *
   * @return true if one of the data flavors can be imported; false otherwise
   */
  boolean canImport(List<TransferFlavor> flavors);

  /**
   * Returns whether a Move action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canMove();

  /**
   * Returns whether a Select All action can be performed on this widget.
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canSelectAll();

  /**
   * Copies the object's selected data to the clipboard
   */
  void performCopy();

  /**
   * Copies the object's selected data to the clipboard
   * and then deletes the selection
   */
  void performCut();

  /**
   * Deletes the currently selected data
   *
   * @param returnData true to return the deleted data; false otherwise
   *
   * @return the deleted data or null of no data was selected or the <b>returnData</b>
   *         flag was false
   */
  Object deleteSelectedData(boolean returnData);

  /**
   * Copies the contents of the clipboard
   */
  void performPaste();

  /**
   *  Performs a select all on the item's contents
   */
  void selectAll();

  /**
   * Gets the item's current selection
   *
   * @return the widget's selection
   */
  Object getSelection();

  /**
   *  Returns whether if the widget has a current selection
   *
   *  @return true if it has a selection; false otherwise
   */
  boolean hasSelection();
}
