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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.Platform;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Don DeCoteau
 */
public class SpinnerDateModel extends aSpinnerModel {
  protected Calendar              calendar;
  protected Date                  date;
  protected DateFormat            format;
  protected int                   incrementField;
  protected boolean               isCircular;
  protected Date                  maximum;
  protected Date                  minimum;
  protected int                   stepSize;
  private iIncrementFiedlCallback incrementFiedlCallback;
  private boolean                 showTime;
  private boolean                 showTimeOnly;

  public SpinnerDateModel() {
    this(Platform.getAppContext().getDefaultDateContext().getDisplayFormat(), Calendar.DAY_OF_MONTH, 1, false);
  }

  public SpinnerDateModel(DateFormat format, boolean circular) {
    this(format, Calendar.DAY_OF_MONTH, 1, circular);
  }

  public SpinnerDateModel(DateFormat format, int incrementField) {
    this(Platform.getAppContext().getDefaultDateContext().getDisplayFormat(), incrementField, 1, false);
  }

  public SpinnerDateModel(DateFormat format, int incrementField, int stepSize, boolean circular) {
    this.format         = format;
    this.incrementField = incrementField;
    this.stepSize       = stepSize;
    this.isCircular     = circular;
    this.calendar       = Calendar.getInstance();
    this.date           = calendar.getTime();
  }

  @Override
  public Object fromString(String value) {
    try {
      return format.parse(value);
    } catch(ParseException ex) {
      return null;
    }
  }

  @Override
  public String toString(Object value) {
    return format.format((Date) value);
  }

  public void setFormat(DateFormat format) {
    this.format = format;
  }

  public void setIncrementFiedlCallback(iIncrementFiedlCallback cb) {
    incrementFiedlCallback = cb;
  }

  /**
   * @param incrementField the incrementField to set
   */
  public void setIncrementField(int incrementField) {
    this.incrementField = incrementField;
  }

  public void setMaximum(Date maximum) {
    long time1 = (maximum == null)
                 ? 0
                 : maximum.getTime();
    long time2 = (this.maximum == null)
                 ? 0
                 : this.maximum.getTime();

    if (time1 != time2) {
      this.maximum = maximum;

      if ((maximum != null) && date.after(maximum)) {
        date = maximum;
      }

      fireStateChanged();
    }
  }

  public void setMinimum(Date minimum) {
    long time1 = (minimum == null)
                 ? 0
                 : minimum.getTime();
    long time2 = (this.minimum == null)
                 ? 0
                 : this.minimum.getTime();

    if (time1 != time2) {
      this.minimum = minimum;

      if ((minimum != null) && date.before(minimum)) {
        date = minimum;
      }

      fireStateChanged();
    }
  }

  public void setStepSize(int stepSize) {
    if (stepSize != this.stepSize) {
      this.stepSize = stepSize;
      fireStateChanged();
    }
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof Calendar) {
      value = ((Calendar) value).getTime();
    }

    if (value instanceof Date) {
      Date d = (Date) value;

      if (!d.equals(date)) {
        if ((minimum != null) && (minimum.compareTo(d) > 0)) {
          return;
        }

        if ((minimum != null) && (maximum.compareTo(d) < 0)) {
          return;
        }

        date = d;
        calendar.setTime(d);
        fireStateChanged();
      }
    } else {
      throw new IllegalArgumentException("illegal value");
    }
  }

  public DateFormat getFormat() {
    return format;
  }

  /**
   * @return the incrementField
   */
  public int getIncrementField() {
    return incrementField;
  }

  public Date getMaximum() {
    return maximum;
  }

  public Date getMinimum() {
    return minimum;
  }

  @Override
  public Object getNextValue() {
    calendar.setTime(date);
    calendar.add(getIncrementFieldEx(), stepSize);

    Date d = calendar.getTime();

    if ((maximum != null) && d.after(maximum)) {
      d = null;
    }

    if ((d == null) && isCircular && (minimum != null)) {
      d = minimum;
    }

    return d;
  }

  @Override
  public Object getPreviousValue() {
    calendar.setTime(date);
    calendar.add(getIncrementFieldEx(), -stepSize);

    Date d = calendar.getTime();

    if ((minimum != null) && d.before(minimum)) {
      d = null;
    }

    if ((d == null) && isCircular && (maximum != null)) {
      d = maximum;
    }

    return d;
  }

  @Override
  public Object getValue() {
    return date;
  }

  protected int getIncrementFieldEx() {
    if (incrementFiedlCallback != null) {
      incrementField = incrementFiedlCallback.getIncrementField(this);
    }

    return incrementField;
  }

  public boolean isShowTimeOnly() {
    return showTimeOnly;
  }

  public void setShowTimeOnly(boolean showTimeOnly) {
    this.showTimeOnly = showTimeOnly;
  }

  public boolean isShowTime() {
    return showTime;
  }

  public void setShowTime(boolean showTime) {
    this.showTime = showTime;
  }

  public interface iIncrementFiedlCallback {
    int getIncrementField(SpinnerDateModel model);
  }


  @Override
  public boolean isCircular() {
    return isCircular;
  }
}
