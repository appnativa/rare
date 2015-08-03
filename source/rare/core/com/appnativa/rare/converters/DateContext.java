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

package com.appnativa.rare.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.regex.Pattern;

/**
 *
 * @author Don DeCoteau
 */
public class DateContext extends ConverterContext {
  private volatile static Pattern monthOnlyPattern;
  protected DateFormat            displayFormat;
  protected DateFormat            itemFormats[];

  /** Creates a new instance of DateContext */
  public DateContext() {
    super("DateContext");
  }

  /**
   * Constructs a new instance
   *
   * @param iformat {@inheritDoc}
   * @param dformat {@inheritDoc}
   */
  public DateContext(DateFormat iformat, DateFormat dformat) {
    super("DateContext");
    itemFormats   = (iformat == null)
                    ? null
                    : new DateFormat[] { iformat };
    displayFormat = dformat;
  }

  public DateContext(DateFormat[] iformat, DateFormat dformat) {
    super("DateContext");
    itemFormats   = iformat;
    displayFormat = dformat;
  }

  public DateFormat[] getItemFormats() {
    return itemFormats;
  }

  public boolean hasMultiplePattens() {
    return (itemFormats == null)
           ? false
           : itemFormats.length > 1;
  }

  public DateFormat getDisplayFormat() {
    return displayFormat;
  }

  public DateFormat getItemFormat() {
    DateFormat a[] = getItemFormats();

    return (a == null)
           ? null
           : a[0];
  }

  /**
   * Returns whether the format is for displaying month only
   *
   * @return true if it is; false otherwise
   */
  public boolean isMonthOnly() {
    DateFormat f = (itemFormats == null)
                   ? displayFormat
                   : itemFormats[0];

    if ((f == null) ||!(f instanceof SimpleDateFormat)) {
      return false;
    }

    if (monthOnlyPattern == null) {
      monthOnlyPattern = Pattern.compile("[^SsdKkEeWwZzmHhaF]+");
    }

    return monthOnlyPattern.matcher(((SimpleDateFormat) f).toPattern()).matches();
  }
}
