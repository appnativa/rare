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
import com.appnativa.util.FormatException;
import com.appnativa.util.Helper;
import com.appnativa.util.NumberRange;
import com.appnativa.util.SNumber;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

/**
 *
 * @author Don DeCoteau
 */
public class NumberConverter extends aConverter {

  /** Creates a new instance of ColorConverter */
  public NumberConverter() {}

  @Override
  public Object createContext(iWidget widget, String value) {
    if (widget == null) {
      widget = Platform.getContextRootViewer();
    }

    return Conversions.createNumberContext(value, widget.getAppContext().getAutoLocalizeNumberFormats());
  }

  @Override
  public Object objectFromString(iWidget widget, String value, Object context) {
    if ((value == null) || (value.length() == 0)) {
      return null;
    }

    if (context == NumberContext.RANGE_CONTEXT) {
      return rangeFromString(null, widget, value);
    } else {
      NumberContext nc = null;

      if (context instanceof NumberContext) {
        nc = (NumberContext) context;

        if (nc.isRange()) {
          return rangeFromString(null, widget, value);
        }
      }

      Number num = (nc == null)
                   ? new SNumber(value).makeImmutable()
                   : objectFromString(nc, widget, value);

      if (((minValue != null) && (minValue.compareTo(num) > 0))
          || ((maxValue != null) && (maxValue.compareTo(num) < 0))) {
        throw new FormatException(getRangeError(minValue, maxValue));
      }

      return num;
    }
  }

  @Override
  public CharSequence objectToString(iWidget widget, Object object, Object context) {
    if (object instanceof BadValueNumber) {
      return object.toString();
    }

    if (object == null) {
      return null;
    }

    NumberFormat f = getFormat(context, false);

    if (object instanceof NumberRange) {
      return rangeToString(f, widget, (NumberRange) object);
    }

    if (f != null) {
      synchronized(f) {
        return f.format(object);
      }
    }

    return object.toString();
  }

  @Override
  public boolean objectsAreImmutable(Object context) {
    return true;
  }

  public Number rangeFromString(NumberFormat nf, iWidget widget, String value) {
    int    n = value.indexOf('/');
    Number low;
    Number hi = null;

    if (n == -1) {
      low = objectFromString(nf, widget, value);
    } else {
      low = objectFromString(nf, widget, value.substring(0, n));
      hi  = objectFromString(nf, widget, value.substring(n + 1));
    }

    if (((minValue != null) && (minValue.compareTo(low) > 0))
        || ((maxValue != null) && (hi != null) && (maxValue.compareTo(hi) < 0))) {
      throw new FormatException(getRangeError(minValue, maxValue));
    }

    return (hi == null)
           ? low
           : new NumberRange(low, hi);
  }

  public String rangeToString(NumberFormat f, iWidget widget, NumberRange object) {
    if (object == null) {
      return null;
    }

    if (f != null) {
      synchronized(f) {
        return f.format(object.getLowValue()) + "/" + f.format(object.getHighValue());
      }
    }

    return object.toString();
  }

  public static Number getBadNumber(String value) {
    return new BadValueNumber(value);
  }

  @Override
  public Class getObjectClass(Object context) {
    return Number.class;
  }

  protected Number objectFromString(NumberFormat f, iWidget widget, String value) {
    try {
      if (f == null) {
        return new SNumber(value).makeImmutable();
      }

      synchronized(f) {
        return f.parse(value);
      }
    } catch(ParseException ex) {
      if (widget == null) {
        widget = Platform.getContextRootViewer();
      }

      if (widget.getAppContext().ignoreFormatExceptions()) {
        return new BadValueNumber(value);
      }

      if (f instanceof DecimalFormat) {
        throw formatException(widget, ((DecimalFormat) f).toPattern(), value);
      } else {
        throw formatException(widget, value, Number.class);
      }
    }
  }

  protected Number objectFromString(NumberContext nc, iWidget widget, String value) {
    NumberFormat[]   formats = nc.getItemFormats();
    RuntimeException ex      = null;

    if ((formats == null) || (formats.length == 0)) {
      try {
        return new SNumber(value, true);
      } catch(RuntimeException e) {
        ex = e;
      }
    } else {
      Number        num = null;
      ParsePosition p   = new ParsePosition(0);

      for (NumberFormat nf : formats) {
        try {
          synchronized(nf) {
            num = nf.parse(value, p);

            if (p.getIndex() != 0) {
              return num;
            }
          }
        } catch(RuntimeException e) {}
      }

      if (widget == null) {
        widget = Platform.getContextRootViewer();
      }
    }

    if (widget.getAppContext().ignoreFormatExceptions()) {
      return new BadValueNumber(value);
    }

    if (ex != null) {
      throw ex;
    }

    throw formatException(widget, Helper.toString(formats, ";"), value);
  }

  private NumberFormat getFormat(Object context, boolean parse) {
    if (context instanceof NumberContext) {
      NumberFormat f = parse
                       ? ((NumberContext) context).getItemFormat()
                       : ((NumberContext) context).getDisplayFormat();

      if ((f == null) &&!parse) {
        f = ((NumberContext) context).getItemFormat();
      }

      return f;
    }

    return null;
  }

  /**
   * A bad number value
   *
   * @author     Don DeCoteau
   */
  public static class BadValueNumber extends Number {
    private String stringValue;

    /**
     * Constructs a new instance
     *
     * @param value {@inheritDoc}
     */
    public BadValueNumber(String value) {
      super();
      stringValue = value;
    }

    @Override
    public double doubleValue() {
      return 0;
    }

    @Override
    public float floatValue() {
      return 0;
    }

    @Override
    public int intValue() {
      return 0;
    }

    @Override
    public long longValue() {
      return 0;
    }

    @Override
    public String toString() {
      return stringValue;
    }
  }
}
