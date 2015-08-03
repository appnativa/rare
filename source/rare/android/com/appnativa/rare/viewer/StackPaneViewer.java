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

package com.appnativa.rare.viewer;

/**
 * A viewer that treats a set of viewers as a stack (like cards). Only one
 * viewer can be active at any given time. The viewers can be referenced by name
 * or index
 *
 * @author Don DeCoteau
 */
public class StackPaneViewer extends aStackPaneViewer {

  /**
   * Constructs a new instance
   */
  public StackPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv
   *          the widget's parent
   */
  public StackPaneViewer(iContainer parent) {
    super(parent);
  }
}
