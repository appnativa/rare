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

import java.util.regex.Pattern;

/**
 * A string consisting of characters ASCII 10, 13, and characters ranging from
 * ASCII value 32 to ASCII value 126.
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTPrintableString extends aSPOTElement implements Comparable {
  int      _nRangeMax;
  int      _nRangeMin;
  Object[] _sChoices;
  String   _sDefaultValue;
  String   _sValidRange;
  String   _sValue;

  /**
   * Creates a new <code>PrintableString</code> object with the
   * specification that the element represented by the object is mandatory.
   */
  public SPOTPrintableString() {
    this(null, -1, -1, true);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTPrintableString(boolean optional) {
    this(null, -1, -1, null, optional);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   */
  public SPOTPrintableString(String val) {
    this(val, -1, -1, null, false);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   * @param max The object's maximum acceptable value
   */
  public SPOTPrintableString(String val, int max) {
    this(val, -1, max, null, false);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   * @param min The object's minimum acceptable value
   * @param max The object's maximum acceptable value
   */
  public SPOTPrintableString(String val, int min, int max) {
    this(val, min, max, null, false);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTPrintableString(String val, String defaultval, boolean optional) {
    this(val, -1, -1, defaultval, optional);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   * @param min The object's minimum acceptable value
   * @param max The object's maximum acceptable value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTPrintableString(String val, int min, int max, boolean optional) {
    this(val, min, max, null, optional);
  }

  /**
   * Creates a new <code>PrintableString</code> object
   *
   * @param val the value
   * @param min The object's minimum acceptable value
   * @param max The object's maximum acceptable value
   * @param defaultval the default value
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTPrintableString(String val, int min, int max, String defaultval, boolean optional) {
    _nRangeMin     = min;
    _nRangeMax     = max;
    _sValue        = val;
    _sDefaultValue = defaultval;
    _isOptional    = optional;
  }

  public int compareTo(Object o) {
    return compareTo((SPOTPrintableString) o);
  }

  /**
   * Tests whether this object is equal to the specified object
   *
   * @param o the object to test
   *
   * @return <code>true</code> if this object is the same as the specified object; <code>false</code> otherwise
   */
  public int compareTo(SPOTPrintableString o) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;
    String t2 = (o._sValue != null)
                ? o._sValue
                : o._sDefaultValue;

    if ((t1 == null) || (t2 == null)) {
      return (t1 == t2)
             ? 0
             : ((t1 == null)
                ? -1
                : 1);
    }

    return t1.compareTo(t2);
  }

  /**
   * Compares this object to the specified object
   *
   * @param o the object to compare to
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
   */
  public int compareTo(String o) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;
    String t2 = o;

    if ((t1 == null) || (t2 == null)) {
      return (t1 == t2)
             ? 0
             : ((t1 == null)
                ? -1
                : 1);
    }

    return t1.compareTo(t2);
  }

  /**
   * Returns true if and only if this string contains the specified
   * string
   *
   * @param s the sequence to search for
   * @return true if this string contains specified string, false otherwise
   */
  public boolean contains(String s) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;

    if ((t1 == null) || (s == null)) {
      return false;
    }

    return t1.indexOf(s) != -1;
  }

  /**
   * Tests whether this object ends with the specified string
   *
   * @param s the string to test
   *
   * @return <code>true</code> if it doest; <code>false</code> otherwise
   */
  public boolean endsWith(String s) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;

    if ((t1 == null) || (s == null)) {
      return false;
    }

    return t1.endsWith(s);
  }

  /**
   * Tests whether this object is equal to the specified string
   *
   * @param s the string to test
   *
   * @return <code>true</code> if this object is the same as the specified object; <code>false</code> otherwise
   */
  public boolean equals(String s) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;
    String t2 = s;

    if ((t1 == null) || (t2 == null)) {
      return t1 == t2;
    }

    return t1.equals(t2);
  }

  public int hashCode() {
    String s = (_sValue != null)
               ? _sValue
               : _sDefaultValue;

    return (s != null)
           ? s.hashCode()
           : super.hashCode();
  }

  /**
   * Removes the existing value
   */
  public void spot_clear() {
    super.spot_clear();
    _sValue = null;
  }

  public Object[] spot_getRange() {
    if ((_nRangeMin < 0) && (_nRangeMax < 0)) {
      return null;
    }

    return new Object[] { _nRangeMin, _nRangeMax };
  }

  public final int spot_getType() {
    return SPOT_TYPE_PRINTABLESTRING;
  }

  /**
   * Retrieves the range of valid values for the object.
   *
   * @return The valid range as a displayable string
   */
  public String spot_getValidityRange() {
    if (_sChoices != null) {
      return getChoicesInBrackets();
    } else {
      if ((_nRangeMin != -1) || (_nRangeMax != -1)) {
        String s = "";

        s = (_nRangeMin != -1)
            ? (SNumber.toString(_nRangeMin) + "..")
            : "..";

        if (_nRangeMax != -1) {
          s += SNumber.toString(_nRangeMax);
        }

        return s;
      } else {
        return "";
      }
    }
  }

  public Object spot_getValue() {
    return getValue();
  }

  /**
   * Sets the choices
   *
   * @param val the choices
   */
  public void spot_setChoices(Object[] val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _sChoices = val;
  }

  /**
   * Sets the default value for the object.
   *
   * @param val The value to become the default (i.e. used when the object has
   *        no value )
   *
   */
  public void spot_setDefaultValue(String val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      _sDefaultValue = null;

      return;
    }

    int len = val.length();

    do {
      if ((_nRangeMin != -1) && (len < _nRangeMin)) {
        break;    // string to short
      }

      if ((_nRangeMax != -1) && (len > _nRangeMax)) {
        break;    // string to long
      }

      if (_sChoices != null) {
        _sDefaultValue = checkChoices(val, false);

        return;
      } else {
        _sDefaultValue = val;

        return;
      }
    } while(false);

    throw new SPOTException(Helper.expandString(STR_NOT_ONEOF, val, spot_getValidityRange()));
  }

  /**
   * Sets the valid range for the object
   *
   * @param min The object's minimum acceptable value
   * @param max The object's maximum acceptable value
   */
  public void spot_setRange(long min, long max) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _nRangeMin = (int) min;
    _nRangeMax = (int) max;
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

    if (min != null) {
      _nRangeMin = SNumber.intValue(min);
    }

    if (max != null) {
      _nRangeMax = SNumber.intValue(max);
    }
  }

  /**
   * Returns the value of the element as a string
   *
   * @return the value
   */
  public String spot_stringValue() {
    return getValue();
  }

  public String spot_stringValueEx() {
    return ((_sValue == null) &&!spot_attributesWereSet())
           ? null
           : getValue();
  }

  /**
   * Tests whether this object starts with the specified string
   *
   * @param s the string to test
   *
   * @return <code>true</code> if it doest; <code>false</code> otherwise
   */
  public boolean startsWith(String s) {
    String t1 = (_sValue != null)
                ? _sValue
                : _sDefaultValue;

    if ((t1 == null) || (s == null)) {
      return false;
    }

    return t1.startsWith(s);
  }

  /**
   * Sets the value
   *
   * @param val the value
   */
  public void setValue(SPOTPrintableString val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    String s = val.spot_stringValue();

    _sValue = (_sChoices != null)
              ? checkChoices(s, true)
              : s;
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

    _sValue = (_sChoices != null)
              ? checkChoices(val, true)
              : val;
  }

  /**
   * Retrieves the choice value
   *
   * @param val the value
   *
   * @return The choice value
   */
  public String getChoice(String val) {
    if (_sChoices == null) {
      return null;
    }

    return checkChoices(val, false);
  }

  /**
   * Retrieves the choices in brackets value
   *
   * @return The choices in brackets value
   */
  public String getChoicesInBrackets() {
    if (_sValidRange != null) {
      return _sValidRange;
    }

    if (_sChoices == null) {
      return "{ }";
    }

    StringBuilder s = new StringBuilder("{ ");
    int           i = 0;

    for (i = 0; i < (_sChoices.length - 1); i++) {
      s.append(_sChoices[i]);
      s.append(", ");
    }

    s.append(_sChoices[i]);
    s.append(" }");
    _sValidRange = s.toString();

    return _sValidRange;
  }

  /**
   * Retrieves the array of choices
   *
   * @return The choices
   */
  public String[] getCopyOfChoices() {
    if (_sChoices == null) {
      return null;
    }

    String s[] = new String[_sChoices.length];

    System.arraycopy(_sChoices, 0, s, 0, _sChoices.length);

    return s;
  }

  /**
   * Retrieves the value
   *
   * @return The value
   */
  public String getValue() {
    if ((_sValue == null) && (_sDefaultValue != null)) {
      return _sDefaultValue;
    }

    return _sValue;
  }

  String checkChoices(String val, boolean exception) {
    for (int i = 0; i < _sChoices.length; i++) {
      Object o = _sChoices[i];

      if (o instanceof Pattern) {
        return ((Pattern) o).matcher(val).matches()
               ? val
               : null;
      }

      if (val.equalsIgnoreCase((String) o)) {
        return (String) o;
      }
    }

    if (!exception) {
      return null;
    }

    throw new SPOTException(Helper.expandString(STR_NOT_ONEOF, val, spot_getValidityRange()));
  }

  protected int spot_checkRangeValidityEx() {
    if ((_sValue == null) && (_sDefaultValue != null)) {
      return VALUE_NULL_WITH_DEFAULT;
    }

    if ((_sValue == null) && _isOptional) {
      return VALUE_NULL_AND_OPTIONAL;
    }

    if (_sValue == null) {
      return VALUE_NULL;    // string to short
    }

    if ((_nRangeMin != -1) && (_sValue.length() < _nRangeMin)) {
      return VALUE_TO_SMALL;    // string to short
    }

    if ((_nRangeMax != -1) && (_sValue.length() > _nRangeMax)) {
      return VALUE_TO_BIG;    // string to long
    }

    return VALUE_OK;    // string just right
  }

  /**
   * Sets the values of the object.
   *
   * @param val the value.
   * @param min The object's minimum value range
   * @param max The object's maximum value range
   * @param optional Specifies if the element the object represents is optional
   */
  protected void setValues(String val, String min, String max, boolean optional) {
    _sValue     = val;
    _isOptional = optional;

    if (min != null) {
      _nRangeMin = SNumber.intValue(min);
    }

    if (max != null) {
      _nRangeMax = SNumber.intValue(max);
    }
  }
}
