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

import com.appnativa.rare.ui.event.iActionListener;

/**
 * An interface for entities that support action listeners
 *
 * @author Don DeCoteau
 */
public interface iActionable {

  /**
   * Adds an action listener
   *
   * @param l the listener to add
   */
  void addActionListener(iActionListener l);

  /**
   * Removes an action listener
   *
   * @param l the listener to remove
   */
  void removeActionListener(iActionListener l);
}
