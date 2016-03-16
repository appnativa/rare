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

import com.appnativa.rare.Platform;
import com.appnativa.rare.widget.iWidget;

import java.text.DateFormat;

/**
 *
 * @author Don DeCoteau
 */
public class DateConverter extends DateTimeConverter {
  @Override
  public Object objectFromString(iWidget widget, String value, Object context, boolean ignoreExceptions) {
    if (value == null) {
      return null;
    }

    return super.objectFromString(widget, value, context, ignoreExceptions);
  }

  @Override
  protected DateContext getDateContext(Object context) {
    if (context instanceof DateContext) {
      return (DateContext) context;
    }

    return Platform.getAppContext().getDefaultDateContext();
  }

  @Override
  protected DateFormat getDateFormat(Object context, boolean display) {
    DateFormat df = null;

    if (context instanceof DateContext) {
      if (display) {
        df = ((DateContext) context).getDisplayFormat();
      } else {
        df = ((DateContext) context).getItemFormat();
      }
    }

    if (df == null) {
      if (display) {
        df = Platform.getAppContext().getDefaultDateContext().getDisplayFormat();
      } else {
        df = Platform.getAppContext().getDefaultDateContext().getItemFormat();
      }
    }

    return df;
  }

  @Override
  protected DateFormat[] getItemFormats(iWidget widget, DateContext context) {
    DateFormat[] formats = (context == null)
                           ? null
                           : context.getItemFormats();

    return (formats == null)
           ? Platform.getAppContext().getDefaultDateContext().getItemFormats()
           : formats;
  }
}
