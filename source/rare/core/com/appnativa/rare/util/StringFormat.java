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

import com.appnativa.rare.platform.PlatformHelper;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class StringFormat extends Format {
  public StringFormat(String pattern) {
    super();
    this.pattern = pattern;
  }

  protected String pattern;

  @Override
  public StringBuffer format(Object o, StringBuffer sb, FieldPosition pos) {
    return sb.append(PlatformHelper.format(pattern, o));
  }

  @Override
  public Object parseObject(String string, ParsePosition pos) {
    return string;
  }

  @Override
  public Object parseObject(String string) {
    return string;
  }

  public void applyPattern(String pattern) {
    this.pattern = pattern;
  }
}
