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
 * A time converter
 *
 * @author Don DeCoteau
 */
public class TimeConverter extends DateTimeConverter {
  @Override
  protected DateContext getDateContext(iWidget widget, Object context) {
    if (context instanceof DateContext) {
      return (DateContext) context;
    }

    return Platform.getAppContext().getDefaultTimeContext();
  }

  @Override
  protected DateFormat getDateFormat(iWidget widget, Object context, boolean display) {
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
        df = Platform.getAppContext().getDefaultTimeContext().getDisplayFormat();
      } else {
        df = Platform.getAppContext().getDefaultTimeContext().getItemFormat();
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
           ? Platform.getAppContext().getDefaultTimeContext().getItemFormats()
           : formats;
  }
}
