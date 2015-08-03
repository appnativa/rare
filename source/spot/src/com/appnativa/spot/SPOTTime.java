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

import com.appnativa.util.SNumber;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Time represents the time of day. The format is the same as the time
 * component of DateTime
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTTime extends SPOTDateTime {

  /**
   * Creates a new <code>Time</code> object with the specification that the
   * element represented by the object is mandatory.
   */
  public SPOTTime() {
    this(true);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTTime(boolean optional) {
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
  public SPOTTime(Calendar val) throws SPOTException {
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
  public SPOTTime(SNumber val) throws SPOTException {
    _isOptional = false;
    setValue(val);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param val the value
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTTime(String val) throws SPOTException {
    setValues(val, null, null, false);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param val the value
   * @param max The object's maximum acceptable value
   *
   * @throws SPOTException if the value of the object or a child object
   *         is invalid. The exception will contain information on the invalid
   *         object
   */
  public SPOTTime(String val, String max) throws SPOTException {
    setValues(val, null, max, false);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param val the value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTTime(String val, String defaultval, boolean optional) throws SPOTException {
    setValues(val, null, null, optional);
    setDefaultValue(defaultval);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTTime(String val, String min, String max) throws SPOTException {
    setValues(val, min, max, false);
  }

  /**
   * Creates a new <code>Time</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTTime(String val, String min, String max, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
  }

  /**
   * Creates a new <code>Time</code> object
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
  public SPOTTime(String val, String min, String max, String defaultval, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
    setDefaultValue(defaultval);
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

  @Override
  public boolean equals(aSPOTElement element) {
    if (element instanceof SPOTTime) {
      return super.equals(element);
    }

    return false;
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
      throw new NumberFormatException(STR_NULL_VALUE);
    }

    long time = (t1.get(Calendar.HOUR_OF_DAY));

    time = time * 10000;
    time += (t1.get(Calendar.MINUTE) * 100);
    time += t1.get(Calendar.SECOND);

    return time;
  }

  /**
   * Get the current date and time
   *
   * @return the current date and time as a string
   */
  public static String now() {
    return new SPOTTime().toString();
  }

  /**
   * Get the current date and time
   *
   * @return the current date and time as a string
   */
  public static SPOTTime nowTime() {
    SPOTTime d = new SPOTTime();

    d.setToCurrentTime();

    return d;
  }

  /**
   * Returns the value of the element as a <code>SNumber</code>
   *
   * @return the value
   *
   */
  public SNumber numberValue() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    if (t1 == null) {
      return null;
    }

    long time = (t1.get(Calendar.HOUR_OF_DAY));

    time = time * 10000;
    time += (t1.get(Calendar.MINUTE) * 100);
    time += t1.get(Calendar.SECOND);

    return numValueNumber().setValue(time);
  }

  /**
   * Returns the value of the specified time as a <code>SNumber</code>
   *
   * @param val the value
   * @param num an optional SNumber object to use
   *
   * @return the value
   */
  public static SNumber numberValue(Calendar val, SNumber num) {
    long time = (val.get(Calendar.HOUR_OF_DAY));

    time = time * 10000;
    time += (val.get(Calendar.MINUTE) * 100);
    time += val.get(Calendar.SECOND);
    num  = (num == null)
           ? new SNumber(time)
           : num.setValue(time);

    return num;
  }

  public final int spot_getType() {
    return SPOT_TYPE_TIME;
  }

  /**
   * Returns the value of the element as a string
   *
   * @return the value
   */
  public String spot_stringValue() {
    return (_cValue == null)
           ? null
           : toString(_cValue, false, true);
  }

  /**
   * Converts the object to a string
   *
   * @return The object
   */
  public String toString() {
    return (_cValue == null)
           ? ""
           : toString(_cValue, false, true);
  }

  /**
   * Converts the object to a string in the format "hh:mm:ss a"
   *
   * @return The object
   */
  public String toStringEx() {
    Calendar date = _cValue;

    if (_cDefValue != null) {
      date = _cDefValue;
    }

    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("hh:mm:ss a");

    return df.format(date.getTime());
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(double val) {
    setValue((long) val);
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

    int hm  = (int) ((val >> 16) & HHMM_MASK);
    int s   = (int) ((val >> 10) & SEC_MASK);
    int mil = (int) (val & MILL_MASK);

    if (_cValue == null) {
      _cValue = Calendar.getInstance();
    }

    _cValue.set(Calendar.HOUR_OF_DAY, hm / 100);
    _cValue.set(Calendar.MINUTE, hm % 100);
    _cValue.set(Calendar.SECOND, s);
    _cValue.set(Calendar.MILLISECOND, mil);
  }

  /**
   * Sets the value
   *
   * @param val the value
   *
   */
  public void setValue(SNumber val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    long time = val.fractionalPart();

    if (time == 0) {
      time = val.longValue();
    }

    setValue(time);
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
    char[] data = val.toCharArray();

    if (data.length != 5) {
      throw new SPOTException("Unparseable date: " + val);
    }

    try {
      i = 0;

      int h = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int mm = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 3;

      int sec = ((data[i] - '0') * 10) + (data[i + 1] - '0');

      i += 2;

      int tzOffset = 0;
      int mil      = 0;

      if ((i < data.length) && (data[i] == '.')) {    // milliseconds
        i++;
        mil = ((data[i] - '0') * 100) + ((data[i + 1] - '0') * 10) + (data[i + 2] - '0');
        i   += 3;
      }

      if ((i < data.length) && (data[i] != 'Z')) {
        tzOffset = 1;

        if (data[i++] == '-') {
          tzOffset = -1;
        }

        int hrOff = ((data[i] - '0') * 10) + (data[i + 1] - '0');

        i += 3;

        int minOff = ((data[i] - '0') * 10) + (data[i + 1] - '0');

        tzOffset *= (((hrOff * 60) + minOff) * 60 * 1000);
      }

      TimeZone tz = (TimeZone) TimeZone.getDefault().clone();

      tz.setRawOffset(tzOffset);
      cal.setTimeZone(tz);
      cal.set(Calendar.HOUR_OF_DAY, h);
      cal.set(Calendar.MINUTE, mm);
      cal.set(Calendar.SECOND, sec);
      cal.set(Calendar.MILLISECOND, mil);
    } catch(Exception e) {
      throw new SPOTException("Unparseable date: " + val);
    }
  }
}
