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

import com.appnativa.rare.ui.print.iPageSetup;

/**
 * An interface for print handlers.
 *
 * @author Don DeCoteau
 */
public interface iPrintHandler {

  /**
   * Returns whether print preview is supported
   *
   * @return true if print preview is supported; false otherwise
   */
  public boolean isPrintPreviewSupported();

  /**
   * Creates an object that can be used for performing page setup operations for the specified component
   *
   * @param comp the component
   *
   * @return an object that can be used for performing page setup operations for the specified component
   */
  iPageSetup createPageSetup(iPlatformComponent comp);

  /**
   *
   * Performs page setup operations for the specified component
   *
   * @param comp the component
   * @param ps the existing page setup information
   */
  void pageSetup(iPlatformComponent comp, iPageSetup ps);

  /**
   * Prints the specified component
   *
   * @param comp the component
   * @param ps the page setup information
   */
  void print(iPlatformComponent comp, iPageSetup ps);

  /**
   * Print previews the specified component
   *
   * @param comp the component
   * @param ps the page setup information
   */
  void printPreview(iPlatformComponent comp, iPageSetup ps);
}
