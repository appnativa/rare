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
 * Interface for components that can hold and manage window level toolbars.
 *
 * @author Don DeCoteau
 */
public interface iToolBarHolder {

  /**
   * Disposes the toolbar holder
   */
  public void dispose();

  /**
   * Removes the toolbar at the specified position
   *
   * @param row the row
   * @param col the column
   * @return the toolbar at the specified position
   */
  iToolBar removeToolBar(int row, int col);

  /**
   * Removes all toolbars
   */
  void removeToolbars();

  /**
   * Toggles the visibility of the main toolbar
   */
  void toggleVisibility();

  /**
   * Toggles the visibility of the toolbar at the specified location
   *
   * @param row the row
   * @param col the column
   */
  void toggleVisibility(int row, int col);

  /**
   * Sets the main toolbar
   *
   * @param mainToolbar the main toolbar
   */
  void setToolBar(iToolBar mainToolbar);

  /**
   * Sets a toolbar to the given row and column
   *
   * @param row the row to add the toolbar to
   * @param col the column to add the toolbar to
   * @param tb the toolbar to add
   * @return the current toolbar at the specified location or null
   */
  iToolBar setToolBar(int row, int col, iToolBar tb);

  /**
   * Sets the visibility of the main toolbar
   * @param visible true for visible; false otherwise
   */
  void setVisible(boolean visible);

  /**
   * Sets the visibility of the toolbar at the specified location
   *
   * @param visible true for visible; false otherwise
   * @param row the row
   * @param col the column
   */
  void setVisible(boolean visible, int row, int col);

  /**
   * Gets the component that holds the toolbars
   *
   * @return the component that holds the toolbars
   */
  iPlatformComponent getComponent();

  /**
   * Gets the main toolbar
   *
   * @return the main toolbar
   */
  iToolBar getToolBar();

  /**
   * Gets the toolbar at the specified position
   *
   * @param row the row
   * @param col the column
   * @return the toolbar at the specified position
   */
  iToolBar getToolBar(int row, int col);
}
