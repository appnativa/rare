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

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.widget.iWidget;

/**
 * Interface for toolbars
 *
 * @author Don DeCoteau
 */
public interface iToolBar {

  /**
   * Add a component to the toolbar
   *
   * @param comp
   *          the component to add
   *
   * @return a BeanWidget representing the added component
   */
  public iWidget add(iPlatformComponent comp);

  /**
   * Add a widget to the toolbar
   *
   * @param widget
   *          the widget to add
   *
   */
  public void add(iWidget widget);

  /**
   *
   * Add an action to the toolbar
   *
   * @param a
   *          the action to add
   *
   * @return a PushButton widget representing the added action
   */
  public iWidget add(UIAction a);

  /**
   *
   * Add a widget to the toolbar
   *
   * @param cfg
   *          the widget configuration
   *
   * @return a widget representing the specified configuration
   */
  public iWidget add(Widget cfg);

  /**
   * Add a component to the toolbar
   *
   * @param name
   *          the name to assign to the created widget
   * @param comp
   *          the component to add
   *
   * @return a Bean widget representing the added component
   */
  public iWidget add(String name, iPlatformComponent comp);

  /**
   * Add an action to the toolbar
   *
   * @param name
   *          the name to assign to the created widget
   * @param a
   *          the action to add
   *
   * @return a widget representing the added action
   */
  public iWidget add(String name, UIAction a);

  /**
   * Adds a separator to the toolbar
   */
  public void addSeparator();

  /**
   * Disposes of the toolbar
   */
  public void dispose();

  /**
   * Sets whether the toolbar is enabled
   *
   * @param enabled
   *          true to enable; false to disable
   */
  public void setEnabled(boolean enabled);

  /**
   * Sets if the toolbar is oriented horizontally or vertically
   *
   * @param horizontal
   *          true for a horizontal orientation; false for a vertical
   */
  public void setHorizontal(boolean horizontal);

  /**
   * Sets whether or not the toolbar will stretch it buttons to fill the space
   * available in the toolbar. This is useful for toolbars with text.
   *
   * @param stretch
   *          true to stretch; false otherwise
   */
  public void setSretchButtonsToFillSpace(boolean stretch);

  /**
   * Sets the name of the toolbar
   *
   * @param name
   *          the toolbar's name
   */
  public void setToolbarName(String name);

  /**
   * Gets the component that represents the toolbar
   *
   * @return the component that represents the toolbar
   */
  public iPlatformComponent getComponent();

  /**
   * Gets the name of the toolbar
   *
   * @return the name of the toolbar
   */
  public String getToolbarName();

  /**
   * Get the named toolbar widget
   *
   * @param name
   *          the name of the widget
   *
   * @return the named widget or null if a widget with that name does not exist
   */
  public iWidget getWidget(String name);

  /**
   * Returns whether the toolbar is also a toolbar holder
   *
   * @return true if is a toolbar holder; false otherwise
   */
  public boolean isHolder();

  /**
   * Removes the named toolbar widget
   *
   * @param name
   *          the name of the widget
   *
   * @return the named widget or null if a widget with that name does not exist
   */
  public iWidget removeWidget(String name);

  /**
   * Removes the specified toolbar widget
   *
   * @param widget
   *          the widget to remove
   *
   */
  public void removeWidget(iWidget widget);

  /**
   * Removes all widgets from the toolbar
   *
   */
  public void removeAllWidgets();
}
