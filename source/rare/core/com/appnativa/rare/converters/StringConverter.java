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
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FormatException;

import java.text.ParseException;

/**
 *
 * @author Don DeCoteau
 */
public class StringConverter extends aConverter {
  public static ConverterContext        EXPANDER_CONTEXT         = new ConverterContext("");
  public static ConverterContext        PASSWORD_CONTEXT         = new ConverterContext("password");
  public static ConverterContext        TITLECASE_CONTEXT        = new ConverterContext("titlecase");
  public static ConverterContext        TITLECASE_CLEAN_CONTEXT  = new ConverterContext("titlecase-clean");
  public static ConverterContext        RESOURCE_CONTEXT         = new ConverterContext("resource");
  public static ConverterContext        HTML_CONTEXT             = new ConverterContext("html");
  public static ConverterContext        CAPITALIZE_CONTEXT       = new ConverterContext("capitalize");
  public static ConverterContext        CAPITALIZE_CLEAN_CONTEXT = new ConverterContext("capitalize-clean");
  private static ThreadLocal<CharArray> perThreadCharArray       = new ThreadLocal<CharArray>() {
    @Override
    protected synchronized CharArray initialValue() {
      return new CharArray(32);
    }
  };

  /** Creates a new instance of StringConverter */
  public StringConverter() {}

  @Override
  public Object createContext(iWidget widget, String value) {
    if (value == null) {
      return null;
    }

    if (value.length() == 0) {
      return EXPANDER_CONTEXT;
    }

    if (value.equalsIgnoreCase("password")) {
      return PASSWORD_CONTEXT;
    }

    if (value.equalsIgnoreCase("titlecase")) {
      return TITLECASE_CONTEXT;
    }

    if (value.equalsIgnoreCase("titlecase-clean")) {
      return TITLECASE_CLEAN_CONTEXT;
    }

    if (value.equalsIgnoreCase("capitalize")) {
      return CAPITALIZE_CONTEXT;
    }

    if (value.equalsIgnoreCase("capitalize-clean")) {
      return CAPITALIZE_CLEAN_CONTEXT;
    }

    if (value.equalsIgnoreCase("html")) {
      return HTML_CONTEXT;
    }

    if (value.equalsIgnoreCase("html")) {
      return HTML_CONTEXT;
    }

    if (value.startsWith("html")) {
      int n = value.indexOf(' ');

      if (n == -1) {
        return HTML_CONTEXT;
      }

      value = value.substring(n + 1);

      if (value.trim().length() != 0) {
        return new ConverterContext("html", value);
      } else {
        return HTML_CONTEXT;
      }
//    int len = value.length() - 1;
//
//    if (value.charAt(len) == '}') {
//      value = value.substring(n + 1, len);
//    } else {
//      value = value.substring(n + 1);
//    }
//
//    try {
//      StringReader r = new StringReader(value);
//      StyleSheet   s = new StyleSheet();
//
//      s.loadRules(r, null);
//
//      return new ConverterContext("html", s);
//    } catch(Throwable e) {
//      return HTML_CONTEXT;
//    }
    }

    if (value.equalsIgnoreCase("resource")) {
      return RESOURCE_CONTEXT;
    }

    return new ConverterContext(value);
  }

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    if (context == TITLECASE_CONTEXT) {
      value = ((value == null) || (value.length() == 0))
              ? value
              : perThreadCharArray.get().set(value).toTitleCase().toString();
    } else if (context == TITLECASE_CLEAN_CONTEXT) {
      if ((value != null) && (value.length() > 0)) {
        CharArray ca = perThreadCharArray.get();

        try {
          CharScanner.cleanQuoted(value, ca);
        } catch(ParseException ex) {
          ca.set(value);
        }

        value = ca.toTitleCase().toString();
      }
    } else if ((context != null) && ((ConverterContext) context).getName().equals("html")) {
      String style = (String) ((ConverterContext) context).getUserObject();

      if (value == null) {
        return (style == null)
               ? null
               : "<html></html>";
      }

      return (style == null)
             ? "<html>" + value + "</html>"
             : "<html>" + style + value + "</html>";
    } else if (context == CAPITALIZE_CONTEXT) {
      value = ((value == null) || (value.length() == 0))
              ? value
              : perThreadCharArray.get().set(value).toTitleCase().toString();
    } else if (context == CAPITALIZE_CLEAN_CONTEXT) {
      if ((value != null) && (value.length() > 0)) {
        CharArray ca = perThreadCharArray.get();

        try {
          CharScanner.cleanQuoted(value, ca);
        } catch(ParseException ex) {
          ca.set(value);
        }

        ca.toLowerCase();
        ca.A[0] = Character.toUpperCase(ca.A[0]);
        value   = ca.toString();
      }
    } else if ((context instanceof ConverterContext) && (context != PASSWORD_CONTEXT)) {
      ConverterContext sc = (ConverterContext) context;

      try {
        value = CharScanner.cleanQuoted(value);
      } catch(ParseException e) {
        Platform.ignoreException(null, e);
      }

      if (sc == RESOURCE_CONTEXT) {
        String s = widget.getAppContext().getResourceAsString(value);

        if ((s != null) && (s.length() > 0)) {
          value = s;
        }
      } else if (sc != EXPANDER_CONTEXT) {
        String format = sc.getName();

        if ((format != null) && (format.indexOf("%s") != -1)) {
          value = PlatformHelper.format(format, value);

          int min = (minValue instanceof Number)
                    ? ((Number) minValue).intValue()
                    : -1;
          int max = (maxValue instanceof Number)
                    ? ((Number) maxValue).intValue()
                    : Integer.MAX_VALUE;

          if ((value.length() < min) || (value.length() > max)) {
            throw new FormatException(Utils.makeInvalidRangeString(min, max));
          }
        }
      }
    }

    return value;
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object object, Object context) {
    if (object instanceof CharSequence) {
      return (CharSequence) object;
    }

    return (object == null)
           ? null
           : object.toString();
  }

  public boolean objectsAreImmutable() {
    return false;
  }

  @Override
  public Class getObjectClass(Object context) {
    return String.class;
  }
}
