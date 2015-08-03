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
 * This interface defines the operations necessary for a window manager.
 * The window manager is responsible for creating and managing all the core visual
 * elements of the application
 *
 * @author Don DeCoteau
 *
 */
public interface iPlatformWindowManager extends iWindowManager {

  /**
   * Gets the handle to the object responsible for creating components
   *
   * @return the handle to the object responsible for creating components
   */
  iPlatformComponentFactory getComponentCreator();

  /**
   * Gets the main window for the window manager
   *
   * @return the main window for the window manager
   */
  iWindow getMainWindow();

  /**
   * Returns whether a view handled the back key being pressed
   * @return true if a view handled the back key being pressed; false otherwise
   */
  boolean isBackPressedHandled();
}
