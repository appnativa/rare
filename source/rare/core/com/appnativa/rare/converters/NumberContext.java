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

import java.text.NumberFormat;

/**
 *
 * @author Don DeCoteau
 */
public class NumberContext extends ConverterContext {
  public static final NumberContext RANGE_CONTEXT = new NumberContext() {
    @Override
    public boolean isRange() {
      return true;
    }
    @Override
    public NumberFormat getDisplayFormat() {
      return null;
    }
    @Override
    public NumberFormat getItemFormat() {
      return null;
    }
  };

  /**  */
  protected NumberFormat displayFormat;

  /**  */
  protected NumberFormat[] itemFormats;
  private boolean          range;

  /** Creates a new instance of DateContext */
  public NumberContext() {
    super("NumberContext");
  }

  public NumberContext(NumberFormat iformat, NumberFormat dformat) {
    super("NumberContext");
    itemFormats   = new NumberFormat[] { iformat };
    displayFormat = dformat;
  }

  public NumberContext(NumberFormat[] iformats, NumberFormat dformat) {
    super("NumberContext");
    itemFormats   = iformats;
    displayFormat = dformat;
  }

  public void setRange(boolean range) {
    this.range = range;
  }

  public NumberFormat getDisplayFormat() {
    return displayFormat;
  }

  public NumberFormat getItemFormat() {
    NumberFormat a[] = getItemFormats();

    return (a == null)
           ? null
           : a[0];
  }

  public NumberFormat[] getItemFormats() {
    return itemFormats;
  }

  public boolean hasMultiplePattens() {
    return (itemFormats == null)
           ? false
           : itemFormats.length > 1;
  }

  public boolean isRange() {
    return range;
  }
}
