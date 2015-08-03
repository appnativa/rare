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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class ColorConverter extends aConverter {
  public static ConverterContext HEX_CONTEXT      = new ConverterContext("HEX_CONTEXT");
  public static ConverterContext RGB_CONTEXT      = new ConverterContext("RGB_CONTEXT");
  public static ConverterContext ICON_RGB_CONTEXT = new ConverterContext("ICON_RGB_CONTEXT");
  public static ConverterContext ICON_HEX_CONTEXT = new ConverterContext("ICON_HEX_CONTEXT");

  /** Creates a new instance of ColorConverter */
  public ColorConverter() {}

  @Override
  public Object createContext(iWidget widget, String value) {
    if ((value != null) && value.equalsIgnoreCase("rgb")) {
      return RGB_CONTEXT;
    }

    if ((value != null) && value.equalsIgnoreCase("rgb_icon")) {
      return ICON_RGB_CONTEXT;
    }

    if ((value != null) && value.equalsIgnoreCase("hex_icon")) {
      return ICON_HEX_CONTEXT;
    }

    return HEX_CONTEXT;
  }

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    if ((context == RGB_CONTEXT) || (context == ICON_RGB_CONTEXT)) {
      return Conversions.colorFromRGBString(value);
    }

    return UIColorHelper.getColor(value);
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object o, Object context) {
    if (!(o instanceof UIColor)) {
      return "";
    }

    UIColor c = (UIColor) o;

    if (context == RGB_CONTEXT) {
      return Conversions.colorToRGBString(c);
    }

    if (c instanceof UIColorShade) {
      return c.toString();
    }

    return Conversions.colorToHEXString(c);
  }

  @Override
  public boolean objectsAreImmutable(Object contex) {
    return true;
  }

  @Override
  public Class getObjectClass(Object context) {
    return UIColor.class;
  }
}
