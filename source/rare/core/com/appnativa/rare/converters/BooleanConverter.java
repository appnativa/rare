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

import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

/**
 *
 * @author Don DeCoteau
 */
public class BooleanConverter extends aConverter {

  /** Creates a new instance of BooleanConverter */
  public BooleanConverter() {}

  @Override
  public Class getObjectClass(Object context) {
    return Boolean.class;
  }

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    return Boolean.valueOf(SNumber.booleanValue(value));
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object object, Object context) {
    return (object == null)
           ? null
           : object.toString();
  }
}
