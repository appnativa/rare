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

package com.appnativa.util;

/**
 * The Equality Filter object represents a filter that tests
 * whether the specified values is equal to the test value.
 *
 * @author Don DeCoteau
 * @version 1.0
 */
public class EqualityFilter implements iFilter {

  /** whether case should be ignored */
  private boolean ignoreCase;

  /** the value to be tested against */
  private String theValue;

  /**
   * Creates a new <b>EqualityFilter</b> object
   *
   */
  public EqualityFilter() {
    this(null, false);
  }

  /**
   * Creates a new <b>EqualityFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   */
  public EqualityFilter(String value) {
    this(value, false);
  }

  /**
   * Creates a new <b>EqualityFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   * @param ignorecase whether case should be ignored
   */
  public EqualityFilter(String value, boolean ignorecase) {
    theValue   = value;
    ignoreCase = ignorecase;
  }

  public String getValue() {
    return theValue;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean isIgnoreCase() {
    return ignoreCase;
  }

  public boolean passes(Object value, iStringConverter converter) {
    if ((converter != null) && (value != null)) {
      value = converter.toString(value);
    }

    if (theValue == value) {
      return true;
    }

    if ((value == null) || (theValue == null)) {
      return value == theValue;
    }

    String s = (value instanceof String)
               ? (String) value
               : value.toString();

    if (ignoreCase) {
      return theValue.equalsIgnoreCase(s);
    } else {
      return theValue.equals(s);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param ignoreCase {@inheritDoc}
   */
  public void setIgnoreCase(boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  public void setValue(String theValue) {
    this.theValue = theValue;
  }
}
