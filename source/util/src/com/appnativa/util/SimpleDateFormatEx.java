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

import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

@SuppressWarnings("serial")
public class SimpleDateFormatEx extends SimpleDateFormat {
  public static boolean checkForISOPattern = true;
  boolean               isISOPattern;

  public SimpleDateFormatEx(String pattern, DateFormatSymbols symbols) {
    super(pattern, symbols);
    checkPattern(pattern);
  }

  public SimpleDateFormatEx(String pattern, Locale arg1) {
    super(pattern, arg1);
    checkPattern(pattern);
  }

  public SimpleDateFormatEx(String pattern) {
    super(pattern);
    checkPattern(pattern);
  }

  public SimpleDateFormatEx() {}

  @Override
  public void applyLocalizedPattern(String pattern) {
    super.applyLocalizedPattern(pattern);
    checkPattern(pattern);
  }

  @Override
  public void applyPattern(String pattern) {
    super.applyPattern(pattern);
    checkPattern(pattern);
  }

  private void checkPattern(String pattern) {
    if (checkForISOPattern) {
      isISOPattern=false;
      if (pattern.startsWith("yyyy-MM-dd'T'HH:mm:ss") || pattern.startsWith("yyyy-MM-dd'T'HH:mm:ss")) {
        if (pattern.endsWith("Z") || pattern.endsWith("z")) {
          isISOPattern = true;
        }
      }
    }
  }

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
    StringBuffer sb = super.format(date, toAppendTo, pos);

    if (isISOPattern) {
      int n = sb.lastIndexOf("+");

      if (n == -1) {
        n = sb.lastIndexOf("-");
      }

      if ((n > 18) && (sb.indexOf(":", n) == -1)) {
        sb.insert(n + 3, ':');
      }
    }

    return sb;
  }
}
