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
 * This class provides a mutable encapsulates an <code>int</code> value
 *
 * @author Don DeCoteau
 * @version 1.0
 */
public class MutableInteger implements Comparable, Cloneable {

  /**  */
  protected

  /** the value */
  int value;

  /**
   * Constructs a new mutable encapsulated <code>int</code>
   *
   * @param value the value to encapsulate
   */
  public MutableInteger(int value) {
    this.value = value;
  }

  /**
   * Adds a value to this objects value
   * @param v the value to add
   * @return this object
   */
  public MutableInteger add(int v) {
    value += v;

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public Object clone() {
    return new MutableInteger(value);
  }

  /**
   * Compares this object with the specified object for order. Returns a negative
   * integer, zero, or a positive integer, as this object is less than, equal to, or
   * greater than the specified object.
   *
   * @param o  the object
   *
   * @return   a negative integer, zero, or a positive integer as this object is less
   *           than, equal to, or greater than the specified object.
   */
  public int compareTo(Object o) {
    if (o instanceof Integer) {
      return value - ((Integer) o).intValue();
    }

    return value - ((MutableInteger) o).intValue();
  }

  /**
   * Compares this object to the specified object.  The result is
   * <code>true</code> if and only if the argument is not
   * <code>null</code> and is an <code>Integer</code> object that
   * contains the same <code>int</code> value as this object.
   *
   * @param   obj   the object to compare with.
   * @return  <code>true</code> if the objects are the same;
   *          <code>false</code> otherwise.
   */
  public boolean equals(Object obj) {
    if (obj instanceof MutableInteger) {
      return value == ((MutableInteger) obj).intValue();
    }

    return false;
  }

  /**
   * Gets and increments the value
   * @return the value
   */
  public final int getAndIncrement() {
    return value++;
  }

  /**
   * Increments and get the value
   * @return the value
   */
  public final int getAndDecrement() {
    return ++value;
  }

  /**
   * Gets the value
   * @return the value
   */
  public final int getValue() {
    return value;
  }

  /**
   * Gets the value
   * @return the value
   */
  public final int get() {
    return value;
  }

  /**
   * Returns a hash code for this <code>Integer</code>.
   *
   * @return  a hash code value for this object, equal to the
   *          primitive <code>int</code> value represented by this
   *          <code>Integer</code> object.
   */
  public final int hashCode() {
    return value;
  }

  /**
   * Increments and gets the value
   * @return the value
   */
  public final int incrementAndGet() {
    return ++value;
  }

  /**
   * Gets the value
   * @return the value
   */
  public final int intValue() {
    return value;
  }

  /**
   * Sets the value
   *
   * @param v the value
   */
  public final void setValue(int v) {
    value = v;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String toString() {
    return String.valueOf(value);
  }

  public int decrementAndGet() {
    return --value;
  }

  public void set(int value) {
    this.value = value;
  }
}
