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
 * An interface for menu bars
 *
 * @author Don DeCoteau
 */
public interface iMenuBar {

  /**
   * Disposes of the menu bar
   */
  public void dispose();

  /**
   * Gets the menu with the specified name
   *
   * @param name the name of the menu
   *
   * @return the menu with the specified name
   */
  public UIMenu getMenu(String name);

  /**
   * Creates a new checkbox menu item
   *
   * @param text the text for the item
   * @param icon the icon for the item
   * @param data data to associate with the item
   *
   * @return a new checkbox menu item
   */
  UIMenuItem createCheckboxMenuItem(String text, iPlatformIcon icon, Object data);

  /**
   * Creates a new menu
   *
   * @param text the text for the menu
   * @param icon the icon for the menu
   * @param data data to associate with the menu
   *
   * @return a new menu
   */
  UIMenu createMenu(String text, iPlatformIcon icon, Object data);

  /**
   * Creates a new menu item
   *
   * @param text the text for the item
   * @param icon the icon for the item
   * @param data data to associate with the item
   *
   * @return a new menu item
   */
  UIMenuItem createMenuItem(String text, iPlatformIcon icon, Object data);

  /**
   * Toggles the visibility of the menu bar
   */
  void toggleVisibility();

  void setEnabled(boolean enabled);

  void setItemEnabled(String name, boolean enabled);

  void setItemSelected(String name, boolean selected);

  void setItemVisible(String name, boolean visible);

  /**
   * Sets the visibility of the menu bar
   *
   * @param visible true to make the menu bar visible; false otherwise
   */
  void setVisible(boolean visible);

  int getItemCount();

  boolean isEnabled();

  boolean isItemEnabled(String name);

  boolean isItemSelected(String name);

  boolean isItemVisible(String name);

  boolean isVisible();

  iPlatformComponent getContainerComponent();

  iMenuBarComponent getMenuBarComponent();
}
