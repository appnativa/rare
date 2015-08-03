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

import java.util.regex.Pattern;

/**
 *
 * @author Don DeCoteau
 */
public class RegularExpressionFilter implements iFilter {

  /** pattern characters that need to be escaped */
  static final String regexpEscapeChars = "$^*()+?.{}[]\\-|";

  /** regular expression pattern group identifier */
  static final char[] patternGroup = "(?:".toCharArray();
  boolean             emptyPasses  = false;
  boolean             nullPasses   = false;

  /** the regular expression pattern */
  Pattern thePattern;

  /**
   * Creates a new instance of RegularExpressionFilter
   *
   * @param pattern the regular expression pattern
   */
  public RegularExpressionFilter(Pattern pattern) {
    thePattern = pattern;
  }

  /**
   * Creates a new instance of RegularExpressionFilter
   *
   * @param pattern the regular expression pattern
   */
  public RegularExpressionFilter(String pattern) {
    thePattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
  }

  /**
   * Creates a new filter from the specified string
   *
   * @param s the string to use to create the filter
   *
   * @return the filter
   */
  public static RegularExpressionFilter createFilter(String s) {
    return new RegularExpressionFilter(createPattern(s));
  }

  /**
   * Creates a regular expression pattern from a string
   * If there string starts and ends with a forward slash (/) then the string is treated as a regular expression pattern otherwise the string is treated as a wildcard expression
   *
   * @param s the string to use
   *
   * @return the filter
   */
  public static Pattern createPattern(String s) {
    if (s.startsWith("/") && s.endsWith("/")) {
      s = s.substring(1, s.length() - 1);

      return Pattern.compile(s, Pattern.CASE_INSENSITIVE);
    } else {
      s = parseWildcardFilter(s);

      return Pattern.compile(s, Pattern.CASE_INSENSITIVE);
    }
  }

  /**
   * Creates a regular expression pattern from a string
   *
   * @param s  the string
   * @param wholeword true for a whole word search; false otherwise
   *
   * @return   a regular expression representing the filter
   */
  public static String parseStringFilter(String s, boolean wholeword) {
    char[] a = s.toCharArray();

    return parseStringFilter(a, 0, a.length, wholeword);
  }

  /**
   * Parses a wild-cards based filter
   *
   * @param filter  the filter
   *
   * @return   a regular expression representing the filter
   */
  public static String parseWildcardFilter(String filter) {
    char[] a = filter.toCharArray();

    return parseWildcardFilter(a, 0, a.length);
  }

  /**
   * Parses a wild-cards based filter
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   a string representing the parsed pattern as a regular expression string
   *           or <code>null</code> if the pattern is invalid
   */
  @SuppressWarnings("resource")
  public static String parseWildcardFilter(char[] chars, int pos, int len) {
    CharArray cb  = new CharArray();
    CharArray cb2 = null;

    len += pos;

    char c;

    while(pos < len) {
      c = chars[pos++];

      if (c == '*') {
        cb.append('.');
        cb.append('*');
      } else if ((c == ';') || (c == '|')) {
        if (cb2 == null) {
          cb2 = new CharArray();
        } else {
          cb2.append('|');
        }

        cb2.append(patternGroup);
        cb2.append(cb);
        cb2.append(')');
        cb._length = 0;
      } else if (regexpEscapeChars.indexOf(c) != -1) {
        cb.append('\\');
        cb.append(c);
      } else {
        cb.append(c);
      }
    }

    if (cb2 != null) {
      if (cb._length > 0) {
        cb2.append('|');
        cb2.append(patternGroup);
        cb2.append(cb);
        cb2.append(')');
      }

      cb = cb2;
    }

    return cb.toString();
  }

  public boolean passes(Object value, iStringConverter converter) {
    if ((converter != null) && (value != null)) {
      value = converter.toString(value);
    }

    if (value == null) {
      return nullPasses;
    }

    String s = (value instanceof String)
               ? (String) value
               : value.toString();

    if (emptyPasses && (s.length() == 0)) {
      return true;
    }

    return (value != null) && thePattern.matcher(s).matches();
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
   * Sets whether a null value passes the filter
   *
   * @param passes true to pass; false to fail
   */
  public void setNullPasses(boolean passes) {
    nullPasses = passes;
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
   * Gets whether a null value passes the filter
   *
   * @return true to pass; false to fail
   */
  public boolean isNullPasses() {
    return nullPasses;
  }

  /**
   * Creates a regular expression pattern from a string
   *
   * @param chars the characters
   * @param pos   the starting position within the array
   * @param len   the number of characters to copy
   * @param wholeword true for a whole word search; false otherwise
   *
   * @return   a regular expression representing the filter
   */
  @SuppressWarnings("resource")
  private static String parseStringFilter(char[] chars, int pos, int len, boolean wholeword) {
    CharArray cb = new CharArray();

    len += pos;

    char c;

    if (wholeword) {
      cb.append("\\b");
    }

    while(pos < len) {
      c = chars[pos++];

      if (regexpEscapeChars.indexOf(c) != -1) {
        cb.append('\\');
        cb.append(c);
      } else {
        cb.append(c);
      }
    }

    if (wholeword) {
      cb.append("\\b");
    }

    return cb.toString();
  }
}
