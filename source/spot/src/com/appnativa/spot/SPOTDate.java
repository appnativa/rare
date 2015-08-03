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

/**
 * Date represents a date value. The format is the same as the date
 * component of UTCTime
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTDate extends SPOTDateTime {

  /**
   * Creates a new <code>Date</code> object with the specification that the
   * element represented by the object is mandatory.
   */
  public SPOTDate() {
    this(true);
  }

  /**
   * Creates a new <code>Date</code> object
   *
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTDate(boolean optional) {
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
  public SPOTDate(Calendar val) throws SPOTException {
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
  public SPOTDate(SNumber val) throws SPOTException {
    _isOptional = false;
    setValue(val);
  }

  /**
   * Creates a new <code>Date</code> object
   *
   * @param val the value
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDate(String val) throws SPOTException {
    setValues(val, null, null, false);
  }

  /**
   * Creates a new <code>Date</code> object
   *
   * @param val the value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDate(String val, String defaultval, boolean optional) throws SPOTException {
    setValues(val, null, null, optional);
    setDefaultValue(defaultval);
  }

  /**
   * Creates a new <code>Date</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDate(String val, String min, String max) throws SPOTException {
    setValues(val, min, max, false);
  }

  /**
   * Creates a new <code>Date</code> object
   *
   * @param val the value
   * @param min The minimum acceptable value
   * @param max The maximum acceptable value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   *
   * @throws SPOTException if the value is invalid.
   */
  public SPOTDate(String val, String min, String max, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
  }

  /**
   * Creates a new <code>Date</code> object
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
  public SPOTDate(String val, String min, String max, String defaultval, boolean optional) throws SPOTException {
    setValues(val, min, max, optional);
    setDefaultValue(defaultval);
  }

  @Override
  public boolean equals(aSPOTElement o) {
    if (o instanceof SPOTDate) {
      return super.equals(o);
    }

    return false;
  }

  @Override
  public long longValue() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    if (t1 == null) {
      throw new NumberFormatException(STR_NULL_VALUE);
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
    return new SPOTDate().toString();
  }

  /**
   * Get the current date and time
   *
   * @return the current date and time as a string
   */
  public static SPOTDate nowDate() {
    SPOTDate d = new SPOTDate();

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

    long date = t1.get(Calendar.YEAR);

    date -= 1700;
    date *= 10000;
    date += (100 * ((t1.get(Calendar.MONTH) + 1)));
    date += t1.get(Calendar.DAY_OF_MONTH);

    return numValueNumber().setValue(date);
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
    long date = val.get(Calendar.YEAR);

    date -= 1700;
    date *= 10000;
    date += (100 * ((val.get(Calendar.MONTH) + 1)));
    date += val.get(Calendar.DAY_OF_MONTH);
    num  = (num == null)
           ? new SNumber(date)
           : num.setValue(date);

    return num;
  }

  public final int spot_getType() {
    return SPOT_TYPE_DATE;
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
           : toString(t1, true, false);
  }

  /**
   * Converts the object to a string
   *
   * @return The object
   */
  public String toString() {
    Calendar t1 = (_cValue != null)
                  ? _cValue
                  : _cDefValue;

    return (t1 == null)
           ? null
           : toString(t1, true, false);
  }

  /**
   * Converts the object to a string in "dd MMM yyyy" format
   *
   * @return The time
   */
  public String toStringEx() {
    Calendar                   t1 = (_cValue != null)
                                    ? _cValue
                                    : _cDefValue;
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd MMM yyyy");

    return df.format(t1.getTime());
  }
}
