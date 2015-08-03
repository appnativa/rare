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

package com.appnativa.rare.util;

import com.appnativa.util.FormatException;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import java.util.regex.Pattern;

/**
 * A regular expression format that can be used to validate
 * field input
 *
 * @author Don DeCoteau
 */
public class RegExpressionFormat extends Format {
  private Pattern pattern;

  /**
   * Creates a new instance
   *   @param pattern the regular expression pattern
   */
  public RegExpressionFormat(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    if (obj != null) {
      toAppendTo.append(obj.toString());
    }

    return toAppendTo;
  }

  @Override
  public Object parseObject(String source) {
    return parseObject(source, null);
  }

  @Override
  public Object parseObject(String source, ParsePosition pos) {
    if (source != null) {
      int n = 0;

      if (pos != null) {
        n      = pos.getIndex();
        source = source.substring(n);
      }

      if (!pattern.matcher(source).matches()) {
        throw new FormatException("Invalid value");
      }

      if (pos != null) {
        pos.setIndex(n + source.length());
      }
    }

    return source;
  }

  /**
   * Sets the regular expression pattern
   * @param pattern the regular expression pattern
   */
  public void setPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  /**
   * Gets the regular expression pattern
   * @return the regular expression pattern
   */
  public Pattern getPattern() {
    return pattern;
  }
}
