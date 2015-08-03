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

/**
 *
 * @author Don DeCoteau
 */
@SuppressWarnings("serial")
public class NumberRange extends Number {
  protected Number highValue;
  protected Number lowValue;

  public NumberRange(Number lowValue, Number highValue) {
    this.highValue = highValue;
    this.lowValue  = lowValue;
  }

  @Override
  public double doubleValue() {
    return lowValue.doubleValue();
  }

  @Override
  public float floatValue() {
    return lowValue.floatValue();
  }

  @Override
  public int intValue() {
    return lowValue.intValue();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof NumberRange)) {
      return false;
    }

    NumberRange r = (NumberRange) obj;

    return r.highValue.equals(highValue) && r.lowValue.equals(lowValue);
  }

  @Override
  public int hashCode() {
    return highValue.intValue() ^ lowValue.intValue();
  }

  public boolean isInRange(double value) {
    return (value >= lowValue.doubleValue()) && (value <= highValue.doubleValue());
  }

  public boolean isInRange(float value) {
    return (value >= lowValue.floatValue()) && (value <= highValue.floatValue());
  }

  public boolean isInRange(int value) {
    return (value >= lowValue.intValue()) && (value <= highValue.intValue());
  }

  public boolean isInRange(long value) {
    return (value >= lowValue.longValue()) && (value <= highValue.longValue());
  }

  @Override
  public long longValue() {
    return lowValue.longValue();
  }

  @Override
  public String toString() {
    return lowValue.toString() + "/" + highValue.toString();
  }

  public static NumberRange valueOf(String range) {
    if (range == null) {
      return null;
    }

    try {
      int n = range.indexOf('/');

      return new NumberRange(new SNumber(range.substring(0, n)), new SNumber(range.substring(n + 1)));
    } catch(Exception e) {
      throw new NumberFormatException(range);
    }
  }

  public Number getHighValue() {
    return highValue;
  }

  public Number getLowValue() {
    return lowValue;
  }
}
