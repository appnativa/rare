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

import java.util.Date;

/**
 *
 * @author Don DeCoteau
 */
public class DateContext extends ConverterContext {
  protected DateFormat displayFormat;
  protected DateFormat itemFormats[];

  /** Creates a new instance of DateContext */
  public DateContext() {
    super("DateContext");
  }

  /**
   * Constructs a new instance
   *
   * @param iformat the internal format
   * @param dformat the display format
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

  public DateContext create(DateFormat iformat, DateFormat dformat) {
    return new DateContext(iformat, dformat);
  }

  public DateContext create(DateFormat[] iformat, DateFormat dformat) {
    return new DateContext(iformat, dformat);
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

  public boolean isCustomConverter() {
    return false;
  }

  /**
   * Creates an object from its string representation.
   * <p>
   * This method will only get called by a converter if the
   * {@link #isCustomConverter()} method is overridden to return true
   * </p>
   * @param value the string representation of the object
   * @return the date represented by the specified string
   * @see #isCustomConverter()
   */
  public Date dateFromString(String value) {
    throw new UnsupportedOperationException();
  }

  /**
   * Converts an object to a string
   *
   * @param date the date to convert
   *
   * @return a string representation of the object
   * @see #isCustomConverter()
   */
  public String dateToString(Date date) {
    return displayFormat.format(date);
  }
}
