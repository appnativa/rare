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
 * A viewer that supports the expansion and collapsing of it contents.
 *
 * @author     Don DeCoteau
 */
public class CollapsiblePaneViewer extends aCollapsiblePaneViewer {

  /**
   * Constructs a new instance
   */
  public CollapsiblePaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv the widget's parent
   */
  public CollapsiblePaneViewer(iContainer parent) {
    super(parent);
  }
}
