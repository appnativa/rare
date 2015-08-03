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

import java.util.Calendar;

/**
 * This class caches strings for number/character to string conversions
 *
 * @version    0.3, 2007-05-04
 * @author     Don DeCoteau
 */
public final class StringCache {
  private static final String[] charsCache            = new String[256];
  private static final int      negNegNumbersCacheLen = -21;
  private static final int      negNumbersCacheLen    = 21;
  private static final int      numOfDays             = 365;
  private static final int      numbersCacheLen       = 256;
  private static final String[] numbersCache          = new String[numbersCacheLen];
  private static final String[] negNumbersCache       = new String[negNumbersCacheLen];
  private static final int      year                  = Calendar.getInstance().get(Calendar.YEAR);
  private static final String   yearString            = Integer.toString(year).intern();
  private static final String   numOfDaysString       = Integer.toString(365).intern();

  static {
    int i = 0;

    for (i = 0; i < numbersCacheLen; i++) {
      numbersCache[i] = Integer.toString(i).intern();
    }

    for (i = 0; i < negNumbersCacheLen; i++) {
      negNumbersCache[i] = Integer.toString(-i).intern();
    }

    for (i = 0; i < 256; i++) {
      charsCache[i] = String.valueOf((char) i).intern();
    }
  }

  private StringCache() {
    super();
  }

  /**
   * Returns the string representation of the specified character
   * @param c the character
   * @return the string representation of the specified character
   */
  public static String valueOf(char c) {
    return (c < 256)
           ? charsCache[(int) c]
           : String.valueOf(c);
  }

  /**
   * Returns the string representation of the specified number
   * @param num the number
   * @return the string representation of the specified number
   */
  public static String valueOf(double num) {
    return String.valueOf(num);
  }

  /**
   * Returns the string representation of the specified number
   * @param num the number
   * @return the string representation of the specified number
   */
  public static String valueOf(int num) {
    if (((num > -1) && (num < numbersCacheLen))) {
      return numbersCache[num];
    }

    if ((num > negNegNumbersCacheLen) && (num < 0)) {
      return negNumbersCache[-num];
    }

    if (num == numOfDays) {
      return numOfDaysString;
    }

    if (num == year) {
      return yearString;
    }

    return Integer.toString(num);
  }

  /**
   * Returns the string representation of the specified number
   * @param num the number
   * @return the string representation of the specified number
   */
  public static String valueOf(long num) {
    if (((num > -1) && (num < numbersCacheLen))) {
      return numbersCache[(int) num];
    }

    if ((num > negNegNumbersCacheLen) && (num < 0)) {
      return negNumbersCache[(int) -num];
    }

    if (num == numOfDays) {
      return numOfDaysString;
    }

    if (num == year) {
      return yearString;
    }

    return Long.toString(num);
  }
}
