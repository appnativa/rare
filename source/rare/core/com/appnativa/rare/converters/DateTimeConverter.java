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
import com.appnativa.util.Helper;

import java.text.DateFormat;
import java.text.ParsePosition;

import java.util.Calendar;
import java.util.Date;

/**
 * A date/time converter
 *
 * @author Don DeCoteau
 */
public class DateTimeConverter extends aConverter {

  /** Creates a new instance of ColorConverter */
  public DateTimeConverter() {}

  @Override
  public Object createContext(iWidget widget, String value) {
    return Conversions.createDateContext(value, Platform.getAppContext().getAutoLocalizeDateFormats());
  }

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    return objectFromString(widget, value, context, Platform.getAppContext().ignoreFormatExceptions());
  }

  public Object objectFromString(iWidget widget, String value, Object context, boolean ignoreExceptions) {
    int len = value.length();

    if (len > 1) {
      if (Character.isLetter(value.charAt(0))) {
        char c = value.charAt(1);

        if ((c == '-') || (c == '+') || (c == '@')) {
          return Helper.createDate(value);
        }
      }
    } else {
      if (len == 1) {
        return Helper.createDate(value);
      }

      return null;
    }

    DateContext   dc      = getDateContext(widget, context);
    DateFormat[]  formats = getItemFormats(widget, dc);
    Date          date    = null;
    ParsePosition p       = new ParsePosition(0);

    if (formats == null) {
      DateFormat df = getDateFormat(widget, dc, false);

      try {
        synchronized(df) {
          p.setIndex(0);
          date = df.parse(value, p);

          if (p.getIndex() != 0) {
            return date;
          }
        }
      } catch(RuntimeException e) {}
    } else {
      for (DateFormat df : formats) {
        try {
          synchronized(df) {
            p.setIndex(0);
            date = df.parse(value, p);

            if (p.getIndex() != 0) {
              return date;
            }
          }
        } catch(RuntimeException e) {}
      }
    }

    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    if (ignoreExceptions) {
      return new BadValueDate(value);
    }

    throw formatException(widget, Helper.toString(formats, ";"), value);
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object object, Object context) {
    Date date = null;

    if (object instanceof Calendar) {
      date = ((Calendar) object).getTime();
    } else if (object instanceof BadValueDate) {
      return object.toString();
    } else if (object instanceof Date) {
      date = (Date) object;
    } else if (object instanceof Number) {
      date = new Date(((Number) object).longValue());
    }

    if (date == null) {
      return "";
    }

    DateFormat df = getDateFormat(widget, context, true);

    synchronized(df) {
      return df.format(date);
    }
  }

  @Override
  public boolean objectsAreImmutable(Object context) {
    return true;
  }

  @Override
  public Class getObjectClass(Object context) {
    return Date.class;
  }

  protected DateContext getDateContext(iWidget widget, Object context) {
    if (context instanceof DateContext) {
      return (DateContext) context;
    }

    return Platform.getAppContext().getDefaultDateTimeContext();
  }

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
      if (widget == null) {
        widget = Platform.getContextRootViewer();
      }

      if (display) {
        df = widget.getAppContext().getDefaultDateTimeContext().getDisplayFormat();
      } else {
        df = widget.getAppContext().getDefaultDateTimeContext().getItemFormat();
      }
    }

    return df;
  }

  protected DateFormat[] getItemFormats(iWidget widget, DateContext context) {
    DateFormat[] formats = (context == null)
                           ? null
                           : context.getItemFormats();

    return (formats == null)
           ? Platform.getAppContext().getDefaultDateTimeContext().getItemFormats()
           : formats;
  }

  static class BadValueDate extends Date {
    private String stringValue;

    public BadValueDate(String value) {
      super(0);
      stringValue = value;
    }

    @Override
    public String toString() {
      return stringValue;
    }
  }
}
