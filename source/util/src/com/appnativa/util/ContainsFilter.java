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
 * The Contains Filter object represents a filter that tests
 * whether a specified value contains another value
 *
 * @author Don DeCoteau
 * @version 1.0
 */
public class ContainsFilter implements iFilter {

  /** whether case should be ignored */
  private boolean ignoreCase;
  private boolean startsWith;
  private boolean nullPasses;
  private boolean emptyPasses;
  private String  originalValue;

  /** the value to be tested against */
  private String theValue;

  /**
   * Creates a new <b>ContainsFilter</b> object
   *
   */
  public ContainsFilter() {
    this(null, false, false);
  }

  /**
   * Creates a new <b>ContainsFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   */
  public ContainsFilter(String value) {
    this(value, false, false);
  }

  /**
   * Creates a new <b>ContainsFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   * @param ignoreCase whether case should be ignored
   */
  public ContainsFilter(String value, boolean ignoreCase) {
    this(value, ignoreCase, false);
  }

  /**
   * Creates a new <b>ContainsFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   * @param ignoreCase whether case should be ignored
   * @param startsWith whether to check only if the value being tested starts with the filter value
   */
  public ContainsFilter(String value, boolean ignoreCase, boolean startsWith) {
    this(value, ignoreCase, startsWith, false, false);
  }

  /**
   * Creates a new <b>ContainsFilter</b> object that allows for the specification
   * of filter's criteria.
   *
   * @param value         The value to test
   * @param ignoreCase whether case should be ignored
   * @param startsWith whether to check only if the value being tested starts with the filter value
   * @param nullPasses true if a null value passes the filter; false otherwise
   * @param emptyPasses true if an empty string passes the filter; false otherwise
   */
  public ContainsFilter(String value, boolean ignoreCase, boolean startsWith, boolean nullPasses, boolean emptyPasses) {
    this.ignoreCase  = ignoreCase;
    this.startsWith  = startsWith;
    this.nullPasses  = nullPasses;
    this.emptyPasses = emptyPasses;
    originalValue    = value;
    this.startsWith  = startsWith;

    if (originalValue != null) {
      theValue = ignoreCase
                 ? originalValue.toLowerCase()
                 : originalValue;
    }
  }

  public boolean passes(Object value, iStringConverter converter) {
    if ((converter != null) && (value != null)) {
      value = converter.toString(value);
    }

    if ((value == null) || (theValue == null)) {
      return (value == theValue) || ((value == null) && nullPasses);
    }

    String s = (value instanceof String)
               ? (String) value
               : value.toString();

    if (emptyPasses && (s.length() == 0)) {
      return true;
    }

    if (ignoreCase) {
      s = s.toLowerCase();
    }

    if (startsWith) {
      return s.startsWith(theValue);
    } else {
      return s.contains(theValue);
    }
  }

  /**
   * Sets whether an empty string passes the filter
   *
   * @param passes true to pass; false to fail
   */
  public void setEmptyStringPasses(boolean passes) {
    emptyPasses = passes;
  }

  /**
   * {@inheritDoc}
   *
   * @param ignoreCase {@inheritDoc}
   */
  public void setIgnoreCase(boolean ignoreCase) {
    this.ignoreCase = ignoreCase;

    if (originalValue != null) {
      theValue = ignoreCase
                 ? originalValue.toLowerCase()
                 : originalValue;
    }
  }

  /**
   * Sets whether a null value passes the filter
   *
   * @param passes true to pass; false to fail
   */
  public void setNullPasses(boolean passes) {
    nullPasses = passes;
  }

  /**
   * {@inheritDoc}
   *
   * @param startsWith {@inheritDoc}
   */
  public void setStartsWith(boolean startsWith) {
    this.startsWith = startsWith;
  }

  /**
   * {@inheritDoc}
   *
   * @param theValue {@inheritDoc}
   */
  public void setValue(String theValue) {
    this.theValue = theValue;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getValue() {
    return theValue;
  }

  /**
   * Gets whether an empty string passes the filter
   *
   * @return true to pass; false to fail
   */
  public boolean isEmptyStringPasses() {
    return emptyPasses;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean isIgnoreCase() {
    return ignoreCase;
  }

  /**
   * Gets whether a null value passes the filter
   *
   * @return true to pass; false to fail
   */
  public boolean isNullPasses() {
    return nullPasses;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean isStartsWith() {
    return startsWith;
  }
}
