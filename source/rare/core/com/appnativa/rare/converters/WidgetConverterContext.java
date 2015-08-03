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

package com.appnativa.rare.converters;

import com.appnativa.rare.viewer.iContainer;

/**
 *
 * @author Don DeCoteau
 */
public class WidgetConverterContext extends ConverterContext {

  /** Creates a new instance of WidgetConverterContext */
  public WidgetConverterContext() {
    super("WidgetConverterContext");
  }

  /**
   * Constructs a new instance
   *
   * @param parent the widget's parent
   */
  public WidgetConverterContext(iContainer parent) {
    super("WidgetConverterContext");
    this.setUserObject(parent);
  }
}
