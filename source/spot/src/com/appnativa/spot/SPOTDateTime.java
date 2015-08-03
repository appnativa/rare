/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.spot;

import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * SPOTDateTime represents date and time element.
 * The format is:
 * <blockquote>
 * YYYY-MM-DDThh:mm:ssZ<br> YYYY-MM-DDThh:mm:ss+hh'mm'<br>
 * YYYY-MM-DDThh:mm:ss-hh'mm'<br>
 * o        <code>YYYY</code> represents the four digit year<br>
 * o        <code>MM</code> represents the two digit month<br>
 * o        <code>DD</code> represents the two digit day of the month<br>
 * o        <code>Z</code> indicates that local time is GMT<br>
 * o        <code>+</code>(plus) indicates that local time is later than GMT<br>
 * o        <code>-</code>(minus) indicates that local time is earlier than GMT<br>
 * o        <code>hh'</code> is the absolute value of the offset from GMT in hours<br>
 * o        <code>mm'</code> is the absolute value of the offset from GMT in
 * minutes<br>
 * </blockquote>
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTDateTime extends aSPOTElement {

  /** DOCUMENT ME */
  static final int DAY_MASK = 0x1f;

  /** DOCUMENT ME */
  static final int HHMM_MASK = 0xfff;

  /** DOCUMENT ME */
  static final int MILL_MASK = 0x3ff;

  /** DOCUMENT ME */
  static final int MIN_MASK = 0x3f;

  /** DOCUMENT ME */
  static final int MONTH_MASK = 0xf;

  /** DOCUMENT ME */
  static final int SEC_MASK = 0x3f;

  /** DOCUMENT ME */
  static final int TIMERR_MASK = 0xfffffff;

  /** DOCUMENT ME */
  static final int YEAR_MASK = 0xfff;

  /** three letter months of the year array */
  static final String months[] = {
    "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
  };

  /** DOCUMENT ME */
  static final double LN_10      = Math.log(10);
  Calendar            _cDefValue = null;
  Calendar            _cRangeMax = null;
  Calendar            _cRangeMin = null;
  Calendar            _cValue    = null;

  /**
   * Creates a new <code>DateTime</code> object with the specification that
   * the element represented by the object is mandatory.
   */
  public SPOTDateTime() {
    this(true);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTDateTime(boolean optional) {
    _isOptional = optional;
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(Calendar val) throws SPOTException {
    _isOptional = false;
    setValue(val);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(SNumber val) throws SPOTException {
    _isOptional = false;
    setValue(val);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(String val) throws SPOTException {
    setValues(val, null, null, false);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(Calendar val, boolean optional) throws SPOTException {
    _isOptional = optional;
    setValue(val);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(String val, boolean optional) throws SPOTException {
    _isOptional = optional;
    setValues(val, null, null, false);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTDateTime(String val, String defaultval, boolean optional) throws SPOTException {
    _isOptional = optional;
    setDefaultValue(defaultval);
    setValues(val, null, null, false);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDateTime(String val, String min, String max) throws SPOTException {
    setValues(val, min, max, false);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDateTime(String val, String min, String max, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
  }

  /**
   * Creates a new <code>DateTime</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDateTime(String val, String min, String max, String defaultval, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
    setDefaultValue(defaultval);
  }

  /**
   * Adds the specified (signed) amount of time to the given time field, based
   * on the calendar's rules. For example, to subtract 5 days from the current
   * time of the calendar, you can achieve it by calling:
   *
   * <P>
   * add(Calendar.DATE, -5).
   * </p>
   *
   * @param field the field to add to
   * @param amount the amount to add
   */
  public void add(int field, int amount) {
    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.add(field, amount);
  }

  /**
   * Returns the value of the element as a boolean
   *
   * @return the value
   *
   */
  public boolean booleanValue() {
    throw new NumberFormatException(STR_ILLEGAL_VALUE);
  }

  public Object clone() {
    SPOTDateTime dt = (SPOTDateTime) super.clone();

    if (_cValue != null) {
      dt._cValue = (Calendar) _cValue.clone();
    }

    return dt;
  }

  public int compareTo(Object o) {
    return compareTo((SPOTDateTime) o);
  }

  public int compareTo(SPOTDateTime o) {
    if (o == null) {
      return 1;
    }

    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;
    Calendar t2 = (o._cValue != null)
                  ? o._cValue
                  : o._cDefValue;

    if ((t1 == null) || (t2 == null)) {
      return (t1 == t2)
             ? 0
             : ((t1 != null)
                ? 1
                : -1);
    }

    return (int) (longValue() - o.longValue());
  }

  /**
   * Returns the value of the element as a <code>double</code>
   *
   * @return the value
   */
  public double doubleValue() {
    SNumber num = numberValue();

    if (num == null) {
      throw new SPOTException(STR_NULL_VALUE, (_theName == null)
              ? getClass().getName()
              : _theName);
    }

    return num.doubleValue();
  }

  public boolean equals(aSPOTElement e) {
    if (!(e instanceof SPOTDateTime)) {
      return false;
    }

    SPOTDateTime o  = (SPOTDateTime) e;
    Calendar     t1 = (_cValue != null)
                      ? _cValue
                      : _cDefValue;
    Calendar     t2 = (o._cValue != null)
                      ? o._cValue
                      : o._cDefValue;

    if ((t1 == null) || (t2 == null)) {
      if (t1 != t2) {
        return false;
      }
    } else if (longValue() != o.longValue()) {
      return false;
    }

    return spot_attributesEqual(this, o);
  }

  public int hashCode() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    return (t1 != null)
           ? t1.hashCode()
           : super.hashCode();
  }

  /**
   * Returns the value of the element as a <code>int</code>
   *
   * @return the value
   *
   */
  public int intValue() {
    return (int) longValue();
  }

  /**
   * Returns the value of the element as a <code>long</code>
   *
   * @return the value
   */
  public long longValue() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    if (t1 == null) {
      throw new SPOTException(STR_NULL_VALUE, (_theName == null)
              ? getClass().getName()
              : _theName);
    }

    long date = t1.get(Calendar.YEAR);

    date -= 1700;
    date *= 10000;
    date += (100 * ((t1.get(Calendar.MONTH) + 1)));
    date += t1.get(Calendar.DAY_OF_MONTH);

    return date;
  }

  /**
   * Get the current date and time
   *
   * @return the current date and time as a string
   */
  public static String now() {
    return SPOTDateTime.toString(Calendar.getInstance());
  }

  /**
   * Get the current date and time
   *
   * @return the current date and time as a string
   */
  public static SPOTDateTime nowDateTime() {
    SPOTDateTime d = new SPOTDateTime();

    d.setToCurrentTime();

    return d;
  }

  /**
   * Returns the value of the element as a <code>SNumber</code>
   *
   * @return the value
   */
  public SNumber numberValue() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    if (t1 == null) {
      return null;
    }

    return numberValue(t1, numValueNumber());
  }

  /**
   * Returns the value of the specified date as a <code>SNumber</code>
   *
   * @param val the value
   * @param num an optional SNumber object to use
   *
   * @return the value
   */
  public static SNumber numberValue(Calendar val, SNumber num) {
    Calendar c    = val;
    long     date = c.get(Calendar.YEAR);

    date -= 1700;
    date *= 10000;
    date += (100 * ((c.get(Calendar.MONTH) + 1)));
    date += c.get(Calendar.DAY_OF_MONTH);

    long time = (c.get(Calendar.HOUR_OF_DAY));

    time = time * 10000;
    time += (c.get(Calendar.MINUTE) * 100);
    time += c.get(Calendar.SECOND);

    int dec = (int) (Math.log(time) / LN_10);

    dec++;

    return (num == null)
           ? new SNumber(date, time, dec)
           : num.setValue(date, time, dec);
  }

  /**
   * Returns the value of the specified date as a <code>SNumber</code>
   *
   * @param val the value
   * @param num an optional SNumber object to use
   *
   * @return the value
   */
  public static SNumber numberValue(Date val, SNumber num) {
    Calendar c = Calendar.getInstance();

    c.setTime(val);

    return numberValue(c, num);
  }

  /**
   * Returns the value of the specified date as a <code>SNumber</code>
   *
   * @param val the value
   * @param num an optional SNumber object to use
   *
   * @return the value
   */
  public static SNumber numberValue(String val, SNumber num) {
    Calendar c = Calendar.getInstance();

    setValue(val, c);

    return numberValue(c, num);
  }

  /**
   * Removes the existing value
   */
  public void spot_clear() {
    super.spot_clear();
    _cValue = null;
  }

  public Object[] spot_getRange() {
    if ((_cRangeMin == null) && (_cRangeMax == null)) {
      return null;
    }

    return new Object[] { _cRangeMin, _cRangeMax };
  }

  public int spot_getType() {
    return SPOT_TYPE_DATETIME;
  }

  /**
   * Retrieves the range of valid values for the object.
   *
   * @return The valid range as a displayable string
   */
  public String spot_getValidityRange() {
    if ((_cRangeMin != null) || (_cRangeMax != null)) {
      String s = "";

      s = (_cRangeMin != null)
          ? (toString(_cRangeMin) + "..")
          : "..";

      if (_cRangeMax != null) {
        s += toString(_cRangeMax);
      }

      return s;
    } else {
      return "";
    }
  }

  public Object spot_getValue() {
    return getValue();
  }

  /**
   * Sets the valid range for the object
   *
   * @param min The object's minimum acceptable value
   * @param max The object's maximum acceptable value
   */
  public void spot_setRange(String min, String max) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if ((min != null) && (min.length() > 0)) {
      _cRangeMin = Calendar.getInstance();
      setValue(min, _cRangeMin);
    } else {
      _cRangeMin = null;
    }

    if ((max != null) && (max.length() > 0)) {
      _cRangeMax = Calendar.getInstance();
      setValue(max, _cRangeMax);
    } else {
      _cRangeMax = null;
    }
  }

  /**
   * Returns the value of the element as a string
   *
   * @return the value
   */
  public String spot_stringValue() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    return (t1 == null)
           ? null
           : toString(t1);
  }

  public String spot_stringValueEx() {
    return ((_cValue == null) &&!spot_attributesWereSet())
           ? null
           : spot_stringValue();
  }

  /**
   * Converts calendar instance to a date string formatted as (YYYY-1700)MMDD  to a java calendar object
   *
   * @param val the date
   * @param time whether the time should be included
   *
   * @return a date object
   */
  public static String to1700Date(Calendar val, boolean time) {
    SNumber num = (SNumber) perThreadNumber.get();

    numberValue(val, num);

    if (!time) {
      num.setValue(num.longValue());
    }

    return num.toString();
  }

  /**
   * Converts the object to its string representation
   *
   * @return The string
   */
  public String toString() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    return (t1 == null)
           ? ""
           : toString(t1);
  }

  /**
   * Converts the object to its string representation
   *
   * @param date the date
   *
   * @return The object
   */
  public static String toString(Calendar date) {
    return toString(date, false, false);
  }

  public static String toString(Calendar date, boolean dateonly, boolean timeonly) {
    StringBuilder sb = new StringBuilder();
    int           y;

    if (!timeonly) {
      sb.append(date.get(Calendar.YEAR));
      sb.append('-');
      y = (date.get(Calendar.MONTH) + 1);

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
      sb.append('-');
      y = date.get(Calendar.DAY_OF_MONTH);

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
    }

    if (dateonly) {
      return sb.toString();
    }

    sb.append('T');
    y = date.get(Calendar.HOUR_OF_DAY);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    sb.append(':');
    y = date.get(Calendar.MINUTE);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    sb.append(':');
    y = date.get(Calendar.SECOND);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    y = date.getTimeZone().getRawOffset() / 3600000;    // 60*60*1000

    if (y == 0) {
      sb.append('Z');
    } else {
      if (y > 0) {
        sb.append('+');
      } else {
        sb.append('-');
        y *= -1;
      }

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
      sb.append(":00");
    }

    return sb.toString();
  }

  /**
   * Converts the object to a string in "dd MMM yyyy hh:mma" format
   *
   * @return The data and time
   *
   */
  public String toStringEx() {
    Calendar date = (_cValue == null)
                    ? _cDefValue
                    : _cValue;

    if (date == null) {
      throw new SPOTException(STR_NULL_VALUE, (_theName == null)
              ? getClass().getName()
              : _theName);
    }

    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd MMM yyyy hh:mma");

    return df.format(date.getTime());
  }

  /**
   * Converts the object to a <code>String</code> object
   *
   * @param format The format to represent the DateTime object as
   *
   * @return The date and time
   *
   */
  public String toStringEx(String format) {
    Calendar date = (_cValue == null)
                    ? _cDefValue
                    : _cValue;

    if (date == null) {
      throw new SPOTException(STR_NULL_VALUE, (_theName == null)
              ? getClass().getName()
              : _theName);
    }

    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);

    return df.format(date.getTime());
  }

  public String toStringFM(boolean dateonly, boolean timeonly, boolean ampm) {
    if (_cValue == null) {
      return "";
    }

    return toStringFM(new StringBuilder(), _cValue, dateonly, timeonly, ampm).toString();
  }

  public static StringBuilder toStringFM(StringBuilder sb, Calendar date, boolean dateonly, boolean timeonly,
          boolean ampm) {
    int y;

    if (!timeonly) {
      y = date.get(Calendar.MONTH);
      sb.append(months[y]);
      sb.append(' ');
      y = date.get(Calendar.DAY_OF_MONTH);

      if (y < 10) {
        sb.append('0');
      }

      sb.append(y);
      sb.append(", ");
      sb.append(date.get(Calendar.YEAR));
    }

    if (dateonly) {
      return sb;
    }

    sb.append('@');
    y = ampm
        ? date.get(Calendar.HOUR)
        : date.get(Calendar.HOUR_OF_DAY);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);
    sb.append(':');
    y = date.get(Calendar.MINUTE);

    if (y < 10) {
      sb.append('0');
    }

    sb.append(y);

    if (ampm) {
      sb.append((date.get(Calendar.AM_PM) == Calendar.AM)
                ? "AM"
                : "PM");
    }

    return sb;
  }

  /**
   * Sets the default value for the object.
   *
   * @param val The value to become the default (i.e. used when the object has
   *        no value )
   */
  public void setDefaultValue(String val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      _cDefValue = null;

      return;
    } else {
      _cDefValue = Calendar.getInstance();
      setValue(val, _cDefValue);

      return;
    }
  }

  /**
   * Sets the time zone value
   *
   * @param s The string value
   */
  public void setTimeZone(String s) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    TimeZone tz = TimeZone.getTimeZone(s);

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.setTimeZone(tz);
  }

  /**
   * Sets the to current time value
   */
  public void setToCurrentTime() {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _cValue = Calendar.getInstance();
  }

  /**
   * Sets the value
   *
   * @param val the value
   *
   */
  public void setValue(boolean val) {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, "Sequence");
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(Calendar val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      spot_clear();

      return;
    }

    _cValue = (Calendar) val.clone();
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(Date val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      spot_clear();

      return;
    }

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.setTime(val);
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(double val) {
    setValue(new SNumber(val));
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(long val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int y = (int) ((val >> 12) & YEAR_MASK);
    int m = (int) ((val >> 5) & MONTH_MASK);
    int d = (int) (val & DAY_MASK);

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.set(y, m, d);
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(SNumber val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    long date = val.longValue();
    long time = val.fractionalPart();
    int  y    = (int) (date / 10000);

    y    += 1700;
    date = date % 10000;

    int m  = (int) (date / 100) - 1;
    int d  = (int) (date % 100);
    int hm = (int) (time / 10000);

    if (hm > 23) {
      hm = hm / 10;
    }

    time = time % 10000;

    int min = (int) (time / 100);
    int sec = (int) (time % 100);

    if (min > 59) {
      min = min / 10;
    }

    if (sec > 59) {
      sec = sec / 10;
    }

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.set(y, m, d, hm, min, sec);
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(SPOTDateTime val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    Calendar cal = val._cValue;

    if (cal == null) {
      cal = val._cDefValue;
    }

    if (cal == null) {
      spot_clear();

      return;
    }

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.setTimeInMillis(cal.getTimeInMillis());
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(String val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      spot_clear();

      return;
    }

    if (val.length() == 0) {
      setValue(new Date());
    } else {
      char ch = val.charAt(0);

      ch = Character.toUpperCase(ch);

      switch(ch) {
        case 'T' :
        case 'Y' :
        case 'N' :
        case 'M' :
        case 'W' :
          _cValue = Helper.createCalendar(val);

          break;

        default :
          if (_cValue == null) {
            _cValue = Calendar.getInstance();
          }

          setValue(val, _cValue);

          break;
      }
    }
  }

  /**
   * Sets the value of the specified calendar object
   *
   * @param val the value to the the calendar object to
   * @param cal the calendar object
   *
   */
  public static void setValue(String val, Calendar cal) {
    int    i    = 0;
    String time = null;
    String date = null;
    int    n    = val.indexOf('T');

    if (n == -1) {
      n = val.indexOf(' ');
    }

    if (n != -1) {
      date = val.substring(0, n);
      time = val.substring(n + 1);
    } else {
      date = val;
    }

    char[] data = date.toCharArray();

    if (data.length != 10) {
      throw new SPOTException("Invalid date format: " + val);
    }

    i = 0;

    int y = (int) SNumber.longValue(data, i, 4, true);

    i += 5;

    int m = ((data[i] - '0') * 10) + (data[i + 1] - '0');

    i += 3;

    int d = ((data[i] - '0') * 10) + (data[i + 1] - '0');

    if (time != null) {
      data = time.toCharArray();
      i    = 0;

      int h = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int mm = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int sec      = 0;
      int tzOffset = 0;
      int mil      = 0;

      if (i + 1 < data.length) {
        sec = ((data[i] - '0') * 10) + (data[i + 1] - '0');
        i   += 2;

        if ((i < data.length) && (data[i] == '.')) {    // milliseconds
          i++;
          mil = ((data[i] - '0') * 100) + ((data[i + 1] - '0') * 10) + (data[i + 2] - '0');
          i   += 3;
        }
      }

      if ((i < data.length) && (data[i] != 'Z')) {
        tzOffset = 1;
        n        = time.indexOf('-', i);

        if (n == -1) {
          n = time.indexOf('+', i);
        }

        if ((n == -1) || (n + 6) > data.length) {
          throw new SPOTException("Invalid date format date: " + val);
        }

        i = n;

        if (data[i++] == '-') {
          tzOffset = -1;
        }

        int hrOff = ((data[i] - '0') * 10) + (data[i + 1] - '0');

        i += 3;

        int minOff = ((data[i] - '0') * 10) + (data[i + 1] - '0');

        tzOffset *= (((hrOff * 60) + minOff) * 60 * 1000);
      }

      if (tzOffset != 0) {
        tzOffset -= TimeZone.getDefault().getRawOffset();
        cal.set(y, m - 1, d, h, mm, sec);
        cal.set(Calendar.MILLISECOND, mil);
        cal.add(Calendar.MILLISECOND, tzOffset);
      } else if ((i < data.length) && (data[i] == 'Z')) {
        cal.set(y, m - 1, d, h, mm, sec);
        cal.set(Calendar.MILLISECOND, mil);
        cal.add(Calendar.MILLISECOND, -TimeZone.getDefault().getRawOffset());
      } else {
        cal.set(y, m - 1, d, h, mm, sec);
        cal.add(Calendar.MILLISECOND, mil);
      }
    } else {
      cal.set(y, m - 1, d);
    }
  }

  /**
   * Sets the value of the specified calendar object
   *
   * @param val the value as a 1700-based datetime
   *
   */
  public void setValueEx(String val) {
    if ((val != null) && (val.length() > 0)) {
      setValue(numValueNumber(val));
    }
  }

  /**
   * Retrieves the calendar value
   *
   * @return The time zone value
   */
  public Calendar getCalendar() {
    return _cValue;
  }

  /**
   * Retrieves the calendar value
   *
   * @return The time zone value
   */
  public Date getDate() {
    return (_cValue == null)
           ? null
           : _cValue.getTime();
  }

  /**
   * Retrieves the default time zone value
   *
   * @return The default time zone value
   */
  public static String getDefaultTimeZone() {
    return TimeZone.getDefault().getID();
  }

  /**
   * Retrieves the time zone value
   *
   * @return The time zone value
   */
  public String getTimeZone() {
    return (_cValue == null)
           ? getDefaultTimeZone()
           : _cValue.getTimeZone().getID();
  }

  /**
   * Retrieves the value
   *
   * @return The value
   */
  public Calendar getValue() {
    if (_cValue == null) {
      return (_cDefValue != null)
             ? _cDefValue
             : null;
    }

    return _cValue;
  }

  /**
   * Returns whether this date represents the current date
   *
   * @return <code>true</code> if this date represents current date; <code>false </code> otherwise
   */
  public boolean isToday() {
    if (_cValue == null) {
      return false;
    }

    Calendar c = Calendar.getInstance();

    if (c.get(Calendar.YEAR) != _cValue.get(Calendar.YEAR)) {
      return false;
    }

    if (c.get(Calendar.MONTH) != _cValue.get(Calendar.MONTH)) {
      return false;
    }

    if (c.get(Calendar.DAY_OF_MONTH) != _cValue.get(Calendar.DAY_OF_MONTH)) {
      return false;
    }

    return true;
  }

  protected int spot_checkRangeValidityEx() {
    if ((_cValue == null) && (_cDefValue != null)) {
      return VALUE_NULL_WITH_DEFAULT;    // optional string don't need a value
    }

    if ((_cValue == null) && _isOptional) {
      return VALUE_NULL_AND_OPTIONAL;    // optional string don't need a value
    }

    if (_cValue == null) {
      return VALUE_NULL;
    }

    if ((_cRangeMin != null) && _cValue.before(_cRangeMin)) {
      return VALUE_TO_SMALL;    // to early
    }

    if ((_cRangeMax != null) && _cValue.after(_cRangeMax)) {
      return VALUE_TO_BIG;    // to late
    }

    return VALUE_OK;    // just right
  }

  /**
   * Sets the values of the object.
   *
   * @param val the value.
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   * @param optional Specifies if the element the object represents is optional
   */
  protected void setValues(String val, String min, String max, boolean optional) {
    _isOptional = optional;
    spot_setRange(min, max);
    setValue(val);
  }
}
