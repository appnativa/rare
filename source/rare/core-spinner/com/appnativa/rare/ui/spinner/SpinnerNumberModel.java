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

import com.appnativa.util.SNumber;

import java.text.DecimalFormat;

/**
 *
 * @author Don DeCoteau
 */
public class SpinnerNumberModel extends aSpinnerModel {
  protected SNumber       mainValue   = new SNumber();
  protected SNumber       workValue   = new SNumber();
  protected SNumber       stepSize    = SNumber.ONE;
  protected SNumber       returnValue = new SNumber();
  protected DecimalFormat format;
  protected boolean       circular;
  protected SNumber       maximum = new SNumber(Integer.MAX_VALUE);
  protected SNumber       minimum = SNumber.ZERO;
  protected boolean       supportDecimalValues;
  protected int           maximumDecimalPlaces;

  public SpinnerNumberModel(boolean circular) {
    this.circular = circular;
  }

  @Override
  public Object fromString(String value) {
    return numberValue(value);
  }

  @Override
  public String toString(Object value) {
    if (value == null) {
      return null;
    }

    if (format != null) {
      return format.format(value);
    }

    return value.toString();
  }

  public void setFormat(DecimalFormat format) {
    this.format = format;
  }

  public void setMaximum(double maximum) {
    this.setMaximum(new SNumber(maximum));
  }

  public void setMaximum(SNumber maximum) {
    if ((maximum == null)
        ? (this.maximum != null)
        : !maximum.equals(this.maximum)) {
      this.maximum = maximum;
      fireStateChanged();
    }
  }

  public void setMinimum(double minimum) {
    this.setMinimum(new SNumber(minimum));
  }

  public void setMinimum(SNumber minimum) {
    if ((minimum == null)
        ? (this.minimum != null)
        : !minimum.equals(this.minimum)) {
      this.minimum = minimum;
      fireStateChanged();
    }
  }

  public SNumber getStepSize() {
    return stepSize;
  }

  public void setStepSize(Number stepSize) {
    if (stepSize == null) {
      throw new IllegalArgumentException("null stepSize");
    }

    if (!stepSize.equals(this.stepSize)) {
      this.stepSize = numberValue(stepSize);
      fireStateChanged();
    }
  }

  public void setSupportDecimalValues(boolean supportDecimalValues) {
    this.supportDecimalValues = supportDecimalValues;
  }

  @Override
  public void setValue(Object value) {
    SNumber val = numberValue(value);

    if (!val.equals(mainValue)) {
      workValue.setValue(val);

      if (!supportDecimalValues) {
        workValue.setScale(0);
      }

      if ((minimum != null) && (workValue.compareTo(minimum) < 0)) {
        if (circular && (maximum != null)) {
          workValue.setValue(maximum);
        } else {
          return;
        }
      }

      if ((maximum != null) && (workValue.compareTo(maximum) > 0)) {
        if (circular && (minimum != null)) {
          workValue.setValue(minimum);
        } else {
          return;
        }
      }

      mainValue.setValue(workValue);
      this.fireStateChanged();
    }
  }

  public DecimalFormat getFormat() {
    return format;
  }

  public SNumber getMaximum() {
    return maximum;
  }

  public SNumber getMinimum() {
    return minimum;
  }

  @Override
  public Object getNextValue() {
    workValue.setValue(mainValue);
    workValue.add(stepSize);

    if ((maximum != null) && (workValue.compareTo(maximum) > 0)) {
      if (circular && (minimum != null)) {
        workValue.setValue(minimum);

        return workValue;
      }

      return null;
    }

    return workValue;
  }

  public SNumber getNumber() {
    returnValue.setValue(mainValue);

    return returnValue;
  }

  @Override
  public Object getPreviousValue() {
    workValue.setValue(mainValue);
    workValue.subtract(stepSize);

    if ((minimum != null) && (workValue.compareTo(minimum) < 0)) {
      if (circular && (maximum != null)) {
        workValue.setValue(maximum);

        return workValue;
      }

      return null;
    }

    return workValue;
  }

  @Override
  public Object getValue() {
    returnValue.setValue(mainValue);

    return returnValue;
  }

  public boolean isSupportDecimalValues() {
    return supportDecimalValues;
  }

  @Override
  public boolean isCircular() {
    return circular;
  }

  public SNumber numberValue(Object value) {
    if (value == null) {
      return SNumber.ZERO;
    }

    if (value instanceof SNumber) {
      return (SNumber) value;
    }

    if (value instanceof Double) {
      return new SNumber((Double) value);
    }

    if (value instanceof Float) {
      return new SNumber((Float) value);
    }

    if (value instanceof Number) {
      return new SNumber(((Number) value).longValue());
    }

    return new SNumber(value.toString());
  }

  public int getMaximumDecimalPlaces() {
    return maximumDecimalPlaces;
  }

  public void setMaximumDecimalPlaces(int maximumPlaces) {
    this.maximumDecimalPlaces = maximumPlaces;
  }

  @Override
  protected void fireStateChanged() {
    if (supportDecimalValues && (maximumDecimalPlaces > 0) && (workValue.decimalPlaces() > maximumDecimalPlaces)) {
      workValue.setScale(maximumDecimalPlaces);
      returnValue.setScale(maximumDecimalPlaces);
    }

    super.fireStateChanged();
  }
}
