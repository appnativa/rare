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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This class is a version of the <code>SNumber</code> designed specifically to handle
 * decimal number exceeding 64-bits an/or where decimal precision is required
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class SDecimal implements Comparable, Cloneable {

  /**  */
  static final String divideByZero = "divide by zero";

  /**  */
  static final String numberToLarge = "number to large";

  /**  */
  static final MathContext mathContext = new MathContext(30, RoundingMode.HALF_UP);
  /*
   *   static final MathContext mathContext   = new MathContext(30, MathContext.PLAIN, false,
   *   MathContext.ROUND_HALF_UP); for IBM
   */

  /** an immutable SDecimal representation of zero(0) */
  public static final SDecimal zeroDecimal = new SDecimal(0).makeImmutable();

  /** an immutable SDecimal representation of thirty six hundred (3600) */
  public static final SDecimal thirtySixHundredDecimal = new SDecimal(3600).makeImmutable();

  /** an immutable SDecimal representation of ten (10) */
  public static final SDecimal tenDecimal = new SDecimal(10).makeImmutable();
  static final SDecimal        bd8192     = new SDecimal(8192).oneOver().makeImmutable();
  static final SDecimal        bd65536    = new SDecimal(65536).oneOver().makeImmutable();
  static final SDecimal        bd60       = new SDecimal(60).oneOver().makeImmutable();
  static final SDecimal        bd512      = new SDecimal(512).oneOver().makeImmutable();
  static final SDecimal        bd4096     = new SDecimal(4096).oneOver().makeImmutable();
  static final SDecimal        bd4        = new SDecimal(4).oneOver().makeImmutable();
  static final SDecimal        bd365      = new SDecimal(365).oneOver().makeImmutable();
  static final SDecimal        bd3600     = new SDecimal(3600).oneOver().makeImmutable();
  static final SDecimal        bd32768    = new SDecimal(32768).oneOver().makeImmutable();
  static final SDecimal        bd256      = new SDecimal(256).oneOver().makeImmutable();
  static final SDecimal        bd24       = new SDecimal(24).oneOver().makeImmutable();
  static final SDecimal        bd2048     = new SDecimal(2048).oneOver().makeImmutable();
  static final SDecimal        bd2        = new SDecimal(2).oneOver().makeImmutable();
  static final SDecimal        bd128      = new SDecimal(128).oneOver().makeImmutable();
  static final SDecimal        bd1024     = new SDecimal(1024).oneOver().makeImmutable();

  /** an immutable SDecimal representation of one-hundredth (.01) */
  public final static SDecimal ptZeroOneDecimal = new SDecimal(".01").makeImmutable();
  private static ThreadLocal   perThreadNumber  = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new SNumber(0);
    }
  };
  private static ThreadLocal perThreadCA = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray();
    }
  };
  private static final BigDecimal minLong = BigDecimal.valueOf(Long.MIN_VALUE + 100);
  private static final BigDecimal maxLong = BigDecimal.valueOf(Long.MAX_VALUE - 100);

  /** an immutable SDecimal representation of one hundred (100) */
  public static final SDecimal oneHundredDecimal = new SDecimal(100).makeImmutable();

  /** an immutable SDecimal representation of one (1) */
  public static final SDecimal oneDecimal = new SDecimal(1).makeImmutable();

  /**  */
  CharArray charBuf;

  /** the number */
  protected BigDecimal     theNumber;
  private boolean          immutable;
  private static final int dot      = 1;
  private static final int exponent = 2;

  /**
   * Creates a new <code>SDecimal</code> object with a value of zero.
   */
  public SDecimal() {
    theNumber = BigDecimal.ZERO;
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param num  the value
   */
  public SDecimal(BigDecimal num) {
    theNumber = num;
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param num  the value
   */
  public SDecimal(BigInteger num) {
    theNumber = new BigDecimal(num);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param num  the value
   */
  public SDecimal(double num) {
    theNumber = BigDecimal.valueOf(num);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param num  the value
   */
  public SDecimal(int num) {
    theNumber = BigDecimal.valueOf(num);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param num  the value
   */
  public SDecimal(long num) {
    theNumber = BigDecimal.valueOf(num);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param snum  the value
   */
  public SDecimal(SDecimal snum) {
    theNumber = snum.theNumber;
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param snum  the value
   */
  public SDecimal(SNumber snum) {
    theNumber = new BigDecimal(snum.toString());
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param str  the value
   */
  public SDecimal(String str) {
    setValue(str, false);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   */
  public SDecimal(String str, boolean javaparsecompat) {
    setValue(str, javaparsecompat);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   */
  public SDecimal(char[] chars, int pos, int len) {
    setValueEx(chars, pos, len, false);
  }

  /**
   * Creates a new <code>SDecimal</code> object.
   *
   * @param chars            the character array
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   */
  public SDecimal(char[] chars, int pos, int len, boolean javaparsecompat) {
    setValue(chars, pos, len, javaparsecompat);
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(BigDecimal num) {
    if (immutable) {
      return new SDecimal(this).add(num);
    }

    theNumber = theNumber.add(num, mathContext);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(double num) {
    if (immutable) {
      return new SDecimal(this).add(num);
    }

    theNumber = theNumber.add(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(int num) {
    if (immutable) {
      return new SDecimal(this).add(num);
    }

    theNumber = theNumber.add(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(long num) {
    if (immutable) {
      return new SDecimal(this).add(num);
    }

    theNumber = theNumber.add(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(SDecimal num) {
    if (immutable) {
      return new SDecimal(this).add(num);
    }

    theNumber = theNumber.add(num.theNumber, mathContext);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal add(String str) {
    if (immutable) {
      return new SDecimal(this).add(str);
    }

    SDecimal snum = new SDecimal(str);

    return add(snum);
  }

  /**
   * Performs an <b>AND</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal and(SDecimal num) {
    if (immutable) {
      return new SDecimal(this).and(num);
    }

    theNumber = new BigDecimal(theNumber.toBigInteger().and(num.theNumber.toBigInteger()));

    return this;
  }

  /**
   * Performs an <b>AND</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal and(String str) {
    if (immutable) {
      return new SDecimal(this).and(str);
    }

    SDecimal snum = new SDecimal(str);

    return and(snum);
  }

  /**
   *  Returns a copy of this number
   *
   *  @return   a clone of this number.
   */
  public Object clone() {
    return new SDecimal(this);
  }

  /**
   * Compares this object with the specified object for order. Returns a negative
   * integer, zero, or a positive integer, as this object is less than, equal to, or
   * greater than the specified object.
   *
   * @param obj  the object
   *
   * @return   a negative integer, zero, or a positive integer as this object is less
   *           than, equal to, or greater than the specified object.
   */
  public int compareTo(Object obj) {
    SDecimal snum = null;

    if (obj instanceof SDecimal) {
      snum = (SDecimal) obj;
    } else {
      snum = new SDecimal(obj.toString());
    }

    return compareTo(snum);
  }

  /**
   * Compares this object with the specified number for order. Returns a negative
   * integer, zero, or a positive integer, as this object is less than, equal to, or
   * greater than the specified object.
   *
   * @param num  the number
   *
   * @return   a negative integer, zero, or a positive integer as this object is less
   *           than, equal to, or greater than the specified object.
   */
  public int compareTo(SDecimal num) {
    return theNumber.compareTo(num.theNumber);
  }

  /**
   * Returns an <code>int</code> representing the number of decimal places of the
   * fractional part of this number
   *
   * @return   the number of decimal places
   */
  public int decimalPlaces() {
    return theNumber.scale();
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal divide(double num) {
    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    if (immutable) {
      return new SDecimal(this).divide(num);
    }

    theNumber = theNumber.divide(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal divide(int num) {
    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    if (immutable) {
      return new SDecimal(this).divide(num);
    }

    theNumber = theNumber.divide(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal divide(long num) {
    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    if (immutable) {
      return new SDecimal(this).divide(num);
    }

    theNumber = theNumber.divide(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal divide(SDecimal num) {
    if (num.isZero()) {
      throw new ArithmeticException(divideByZero);
    }

    if (immutable) {
      return new SDecimal(this).divide(num);
    }

    theNumber = theNumber.divide(num.theNumber, mathContext);

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param num {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public SDecimal divide(SNumber num) {
    if (num.isZero()) {
      throw new ArithmeticException(divideByZero);
    }

    if (immutable) {
      return new SDecimal(this).divide(num);
    }

    theNumber = theNumber.divide(toBigDecimal(num), mathContext);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal divide(String str) {
    SDecimal snum = new SDecimal(str);

    return divide(snum);
  }

  /**
   * {@inheritDoc}
   *
   * @param num1 {@inheritDoc}
   * @param num2 {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static String divide(BigDecimal num1, BigDecimal num2) {
    num1 = num1.divide(num2, mathContext);

    return toString(num1);
  }

  /**
   * {@inheritDoc}
   *
   * @param val {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public SDecimal divideInteger(SDecimal val) {
    if (immutable) {
      return new SDecimal(this).divideInteger(val);
    }

    theNumber = theNumber.divideToIntegralValue(val.theNumber, mathContext);

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param val {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public SDecimal divideInteger(SNumber val) {
    if (immutable) {
      return new SDecimal(this).divideInteger(val);
    }

    theNumber = theNumber.divideToIntegralValue(toBigDecimal(val), mathContext);

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param num1 {@inheritDoc}
   * @param num2 {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static BigDecimal divideInteger(BigDecimal num1, BigDecimal num2) {
    return num1.divideToIntegralValue(num2, mathContext);
  }

  /**
   * Returns a <code>double</code> representation of this number
   *
   * @return   the number as a <code>double</code>
   */
  public double doubleValue() {
    return theNumber.doubleValue();
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(double num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) == 0;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(int num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) == 0;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(long num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) == 0;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param obj  the object
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(Object obj) {
    return equals((SDecimal) obj);
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the number
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(SDecimal num) {
    return theNumber.compareTo(num.theNumber) == 0;
  }

  /**
   * Returns a <code>long</code> representation of the fractional part of this number
   *
   * @return   the fraction value as a <code>long</code>
   */
  public long fractionValue() {
    BigDecimal d = theNumber.remainder(BigDecimal.ONE, mathContext);

    d = d.movePointRight(d.scale());

    return d.longValue();
  }

  /**
   * Returns a string representation of the fractional part (not including the decimal) of this number
   *
   * @return   the fraction value as a string
   */
  public String fractionValueString() {
    if (theNumber.scale() == 0) {
      return "";
    }

    String s = theNumber.toPlainString();
    int    n = s.indexOf('.');

    return (n == -1)
           ? ""
           : s.substring(n + 1);
  }

  /**
   * Returns the <code>BigDecimal</code> for the number
   *
   * @return   the <code>BigDecimal</code> for the number
   */
  public BigDecimal getBigDecimal() {
    return theNumber;
  }

  /**
   * Copies the SNumber representation of this SDecimal into the specified SNumber
   * @param out the SNumber
   * @return true if the SDecimal value was small enough toe be represented as an SNumber
   */
  public boolean getSNumber(SNumber out) {
    if (theNumber.scale() < 19) {
      if ((theNumber.compareTo(minLong) > 0) && (theNumber.compareTo(maxLong) < 0)) {
        CharArray ca = (CharArray) perThreadCA.get();

        ca.set(theNumber.toPlainString());

        char[] chars = ca.A;
        int    len   = ca._length;

        return out.setValueEx(chars, 0, len, false, false);
      }
    }

    return false;
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(double num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) > 0;
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(int num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) > 0;
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(long num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) > 0;
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param snum  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(SDecimal snum) {
    return theNumber.compareTo(snum.theNumber) > 0;
  }

  /**
   * Returns the hash code for this object
   *
   * @return   the hash code for the number
   */
  public int hashCode() {
    return theNumber.hashCode();
  }

  /**
   * Returns a hexadecimal string representing the mantissa component of the number
   * @param sign whether the string should contain the sign of the number
   *
   * @return the string
   */
  public String hexMantissaValueString(boolean sign) {
    BigInteger bi;

    if ((theNumber.signum() < 0) &&!sign) {
      bi = theNumber.negate(mathContext).toBigInteger();
    } else {
      bi = theNumber.toBigInteger();
    }

    return bi.toString(16);
  }

  /**
   * Returns an integer representation of this number
   *
   * @return   the number as an integer
   */
  public int intValue() {
    return theNumber.intValue();
  }

  /**
   * Returns an integer representation of this number.
   * <p>If the value is larger than <code>Integer.MAX_VALUE</code> then the <code>Integer.MAX_VALUE</code>
   * is returned
   *
   * @return   the number as an integer
   */
  public int intValueMax() {
    long n = theNumber.longValue();

    return (n > Integer.MAX_VALUE)
           ? Integer.MAX_VALUE
           : (int) n;
  }

  /**
   *  Test this number to see if it is an integer value (that is,
   *  it does not have a fractional component).
   *
   *  @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean isInteger() {
    return theNumber.scale() == 0;
  }

  /**
   * Test this number to see if it has a value of less than zero
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean isNegative() {
    return theNumber.signum() < 0;
  }

  /**
   * Test this number to see if it has a value of zero (0)
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean isZero() {
    return theNumber.signum() == 0;
  }

  /**
   * Returns a <code>long</code> representation of this number
   *
   * @return   the number as a <code>long</code>
   */
  public long longValue() {
    return theNumber.longValue();
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(double num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) < 0;
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(int num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) < 0;
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(long num) {
    return theNumber.compareTo(BigDecimal.valueOf(num)) < 0;
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param snum  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(SDecimal snum) {
    return lt(snum.doubleValue());
  }

  /**
   * Makes this SDecimal immutable
   * @return this SDecimal
   */
  public SDecimal makeImmutable() {
    immutable = true;

    return this;
  }

  /**
   * Divides this number by the specified value and returns only the remainder as the
   * result. The sign of result is the same as the sign of this number.
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal mod(SDecimal num) {
    if (immutable) {
      return new SDecimal(this).mod(num);
    }

    theNumber = theNumber.remainder(num.theNumber, mathContext);

    return this;
  }

  /**
   * Divides this number by the specified value and returns only the remainder as the
   * result. The sign of result is the same as the sign of this number.
   *
   * @param snum  the value
   * @param special should be <code>false</code> unless
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SDecimal mod(SDecimal snum, boolean special) {
    if (immutable) {
      return new SDecimal(this).mod(snum, special);
    }

    boolean    neg = theNumber.signum() != snum.theNumber.signum();
    BigDecimal bd  = theNumber.remainder(snum.theNumber);

    if (neg && special && (bd.signum() != 0)) {
      bd = bd.add(snum.theNumber);
    }

    theNumber = bd;

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param num1 {@inheritDoc}
   * @param num2 {@inheritDoc}
   * @param special {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static BigDecimal mod(BigDecimal num1, BigDecimal num2, boolean special) {
    boolean    neg = num1.signum() != num2.signum();
    BigDecimal bd  = num1.remainder(num2);

    if (neg && special && (bd.signum() != 0)) {
      bd = bd.add(num2);
    }

    return bd;
  }

  /**
   * Moves the decimal point the specified number of places. A negative value shifts
   * to the left, a positive to the right.
   *
   * @param places  the number of places to shift
   *
   * @return   his <code>SDecimal</code> object representing the result of the operation
   */
  public SDecimal moveDecimalPoint(int places) {
    if (immutable) {
      return new SDecimal(this).moveDecimalPoint(places);
    }

    if (places < 0) {
      theNumber = theNumber.movePointLeft(-places);
    } else if (places > 0) {
      theNumber = theNumber.movePointRight(places);
    }

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(double num) {
    if (immutable) {
      return new SDecimal(this).multiply(num);
    }

    theNumber = theNumber.multiply(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(int num) {
    if (immutable) {
      return new SDecimal(this).multiply(num);
    }

    theNumber = theNumber.multiply(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(long num) {
    if (immutable) {
      return new SDecimal(this).multiply(num);
    }

    theNumber = theNumber.multiply(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(SDecimal num) {
    if (immutable) {
      return new SDecimal(this).multiply(num);
    }

    theNumber = theNumber.multiply(num.theNumber, mathContext);

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(SNumber num) {
    if (immutable) {
      return new SDecimal(this).multiply(num);
    }

    theNumber = theNumber.multiply(toBigDecimal(num), mathContext);

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal multiply(String str) {
    if (immutable) {
      return new SDecimal(this).multiply(str);
    }

    SDecimal snum = new SDecimal(str);

    return multiply(snum);
  }

  /**
   * {@inheritDoc}
   *
   * @param num1 {@inheritDoc}
   * @param num2 {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
    return num1.multiply(num2, mathContext);
  }

  /**
   * Negates this number
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal negate() {
    if (immutable) {
      return new SDecimal(this).negate();
    }

    theNumber = theNumber.negate(mathContext);

    return this;
  }

  /**
   * Performs an <b>NOT</b> operation on this number using the specified value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal not() {
    if (immutable) {
      return new SDecimal(this).not();
    }

    if (theNumber.compareTo(BigDecimal.ZERO) != 0) {
      theNumber = BigDecimal.valueOf(0);
    } else {
      theNumber = BigDecimal.valueOf(1);
    }

    return this;
  }

  /**
   * Returns an SDecimal that is equivalent to the number one (1) divided by this number
   * @return an SDecimal that is equivalent to the number one (1) divided by this number
   */
  public SDecimal oneOver() {
    if (immutable) {
      return new SDecimal(this).oneOver();
    }

    theNumber = BigDecimal.ONE.divide(theNumber, mathContext);

    return this;
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal or(double num) {
    if (immutable) {
      return new SDecimal(this).or(num);
    }

    SDecimal snum = new SDecimal(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal or(int num) {
    if (immutable) {
      return new SDecimal(this).or(num);
    }

    SDecimal snum = new SDecimal(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal or(long num) {
    if (immutable) {
      return new SDecimal(this).or(num);
    }

    SDecimal snum = new SDecimal(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal or(SDecimal snum) {
    if (immutable) {
      return new SDecimal(this).or(snum);
    }

    theNumber = new BigDecimal(theNumber.toBigInteger().or(snum.theNumber.toBigInteger()));

    return this;
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal or(String str) {
    if (immutable) {
      return new SDecimal(this).or(str);
    }

    SDecimal snum = new SDecimal(str);

    return or(snum);
  }

  /**
   * Raises this number by the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal pow(SDecimal snum) {
    if (immutable) {
      return new SDecimal(this).pow(snum);
    }

    theNumber = theNumber.pow(snum.intValue(), mathContext);

    return this;
  }

  /**
   * Raises this number by the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal pow(SNumber snum) {
    if (immutable) {
      return new SDecimal(this).pow(snum);
    }

    theNumber = theNumber.pow(snum.intValue(), mathContext);

    return this;
  }

  /**
   * Rounds the number to the specified number of decimal places
   *
   * @param places  the number of decimal places
   * @param up      <code>true</code> to round up; <code>false</code> to round down
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal round(int places, boolean up) {
    if (immutable) {
      return new SDecimal(this).round(places, up);
    }

    theNumber = theNumber.setScale(places, up
            ? BigDecimal.ROUND_HALF_UP
            : BigDecimal.ROUND_DOWN);

    return this;
  }

  /**
   * Gets the scale of the number
   * @return the scale of the number
   */
  public int scale() {
    return theNumber.scale();
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(BigDecimal num) {
    if (immutable) {
      return new SDecimal(num);
    }

    theNumber = num;

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SDecimal setValue(BigInteger num) {
    if (immutable) {
      return new SDecimal(num);
    }

    theNumber = new BigDecimal(num);

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param val  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(double val) {
    if (immutable) {
      return new SDecimal(val);
    }

    theNumber = BigDecimal.valueOf(val);

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param val  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(long val) {
    if (immutable) {
      return new SDecimal(val);
    }

    theNumber = BigDecimal.valueOf(val);

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(SDecimal num) {
    if (immutable) {
      return new SDecimal(num);
    }

    theNumber = num.theNumber;

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(SNumber num) {
    if (immutable) {
      return new SDecimal(num);
    }

    theNumber = new BigDecimal(num.toString());

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal setValue(String str) {
    if (immutable) {
      return new SDecimal(str);
    }

    setValue(str, false);

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal setValue(String str, boolean javaparsecompat) {
    if (immutable) {
      return new SDecimal(str, javaparsecompat);
    }

    if (!setValueEx(str, javaparsecompat)) {
      throw new NumberFormatException();
    }

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param chars  the character array containing the value
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   */
  public SDecimal setValue(char[] chars, int pos, int len) {
    if (immutable) {
      return new SDecimal(chars, pos, len, false);
    }

    if (!setValueEx(chars, pos, len, false)) {
      throw new NumberFormatException();
    }

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param mantissa   the mantissa
   * @param fraction   the fraction
   * @param decplaces  the number of decimal places
   *
   * @return   this <code>SDecimal</code> object representing the result of the operation
   */
  public SDecimal setValue(long mantissa, long fraction, int decplaces) {
    SNumber num = (SNumber) perThreadNumber.get();

    num.setValue(mantissa, fraction, decplaces);

    if (immutable) {
      return new SDecimal(num);
    }

    return setValue(num);
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param chars            the character array containing the value
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   *
   * @throws   NumberFormatException if <code>javaparsecompat</code> is <code>true</code> and the string
   *           contains invalid characters
   */
  public SDecimal setValue(char[] chars, int pos, int len, boolean javaparsecompat) throws NumberFormatException {
    if (immutable) {
      return new SDecimal(chars, pos, len, javaparsecompat);
    }

    if (!setValueEx(chars, pos, len, javaparsecompat)) {
      throw new NumberFormatException();
    }

    return this;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param chars            the character array containing the value
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause the method to return <code>false</code>
   *
   * @return   <code>true</code> if the characters represented a valid number; <code>false</code>
   *           otherwise
   */
  public boolean setValueEx(char[] chars, int pos, int len, boolean javaparsecompat) {
    if (immutable) {
      throw new IllegalStateException("Immutable object");
    }

    boolean neg = false;

    if ((chars == null) || (len == 0)) {
      return false;
    }

    int     esign     = 0;
    boolean not       = false;
    int     notval    = 0;
    int     state     = 0;
    int     i         = 0;
    char    firstchar = 0;
    char    oschar    = 0;
    boolean ret       = true;

    if ((len == 1) && (chars[pos] > 47) && (chars[pos] < 58)) {    // quick test
      theNumber = BigDecimal.valueOf((int) (chars[pos] - 48));
      theNumber = theNumber.add(BigDecimal.ZERO, mathContext);

      return true;
    }

    for (i = 0; i < len; i++) {
      if ((chars[pos + i] == '-') || (chars[pos + i] == '+') || (chars[pos + i] == '\'')) {
        if (chars[pos + i] == '-') {
          neg = !neg;
        } else if (chars[pos + i] == '\'') {
          notval = 1 - notval;

          if (!not) {
            not = true;
          }
        }
      } else {
        break;
      }
    }

    pos += i;
    len -= i;

    if (len == 0) {
      if (!javaparsecompat) {
        theNumber = BigDecimal.ZERO.multiply(BigDecimal.ZERO, mathContext);
      }

      return !javaparsecompat;
    }

    if (neg) {
      oschar         = chars[pos - 1];
      chars[pos - 1] = '-';
    }

forloop:
    for (i = 0; i < len; i++) {
      switch(state) {
        case dot :
          if ((chars[i + pos] < 48) || (chars[i + pos] > 57)) {
            if ((chars[i + pos] == 'e') || (chars[i + pos] == 'E')) {
              if ((chars[(i + pos) - 1] != '.') && (firstchar != 0)) {
                if (chars[(i + pos) - 1] != '.') {
                  state = exponent;

                  break;
                }
              }
            }

            ret = false;

            break forloop;
          }

          break;

        case exponent :
          if ((chars[i + pos] < 48) || (chars[i + pos] > 57)) {
            if ((esign == 0) && ((chars[i + pos] == '+') || (chars[i + pos] == '-'))) {
              esign = i;

              break;
            }

            ret = false;

            break forloop;
          }

          break;

        default :
          if ((chars[i + pos] < 48) || (chars[i + pos] > 57)) {
            if (chars[i + pos] == '.') {
              if (firstchar == 0) {
                firstchar = '.';
              }

              state = dot;

              break;
            }

            if ((chars[i + pos] == 'e') || (chars[i + pos] == 'E')) {
              if (firstchar != 0) {
                state = exponent;

                break;
              }
            }

            ret = false;

            break forloop;
          } else if (firstchar == 0) {
            firstchar = chars[i + pos];
          }

          break;
      }
    }

    if (ret ||!javaparsecompat) {
      if (!ret && (esign > 0) && (i == (esign + 1))) {
        i = esign - 1;
      }

      if ((i > 0) && ((chars[(pos + i) - 1] < 48) || (chars[(pos + i) - 1] > 57))) {
        if (javaparsecompat) {
          ret = false;
        } else {
          i--;
        }
      }
    }

    if (ret ||!javaparsecompat) {
      len = i;

      if (oschar != 0) {
        pos--;
        len++;
      }

      if (len < 1) {
        theNumber = BigDecimal.valueOf(0);
      } else if ((len == 1) && ((chars[pos] < 48) || (chars[pos] > 57))) {
        theNumber = BigDecimal.valueOf(0);
      } else {
        theNumber = new BigDecimal(chars, pos, len);
      }

      theNumber = theNumber.add(BigDecimal.ZERO, mathContext);
      ret       = true;
    }

    if (oschar != 0) {
      chars[pos] = oschar;
    }

    if (not && (theNumber != null)) {
      if (theNumber.compareTo(BigDecimal.ZERO) == 0) {
        theNumber = BigDecimal.valueOf(notval);
      } else {
        theNumber = BigDecimal.valueOf(1 - notval);
      }
    }

    return ret;
  }

  /**
   * Calculates the square root of a number
   *
   * @param num  the value to calculate the square root of
   *
   * @return   a <code>SDecimal</code> object representing the result of the operation
   */
  public static BigDecimal sqrt(BigDecimal num) {
    BigDecimal a    = new BigDecimal(".5");
    BigDecimal prev = num.multiply(a);

    for (int i = 0; i < 25; i++) {
      prev = a.multiply(prev.add(num.divide(prev, 30, BigDecimal.ROUND_HALF_EVEN)));
    }

    return prev;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(BigDecimal num) {
    if (immutable) {
      return new SDecimal(this).subtract(num);
    }

    theNumber = theNumber.subtract(num, mathContext);

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(double num) {
    if (immutable) {
      return new SDecimal(this).subtract(num);
    }

    theNumber = theNumber.subtract(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(int num) {
    if (immutable) {
      return new SDecimal(this).subtract(num);
    }

    theNumber = theNumber.subtract(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(long num) {
    if (immutable) {
      return new SDecimal(this).subtract(num);
    }

    theNumber = theNumber.subtract(BigDecimal.valueOf(num), mathContext);

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param snum  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(SDecimal snum) {
    if (immutable) {
      return new SDecimal(this).subtract(snum);
    }

    theNumber = theNumber.subtract(snum.theNumber, mathContext);

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal subtract(String str) {
    if (immutable) {
      return new SDecimal(this).subtract(str);
    }

    SDecimal snum = new SDecimal(str);

    return subtract(snum);
  }

  /**
   * {@inheritDoc}
   *
   * @param snum {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static BigDecimal toBigDecimal(SNumber snum) {
    if (snum.bigNumber != null) {
      return snum.bigNumber.theNumber;
    }

    BigDecimal num = BigDecimal.valueOf(snum.mantissa);

    if (snum.fraction != 0) {
      num = num.movePointRight(snum.decplaces);
      num = num.add(BigDecimal.valueOf(snum.fraction));
      num = num.movePointLeft(snum.decplaces);
    }

    return num;
  }

  /**
   * Copies the string representation of this number to a byte array
   *
   * @param out  the output array
   *
   * @return   the number of bytes generated
   */
  public int toBytes(byte[] out) {
    return ISO88591Helper.getInstance().getBytes(toString(theNumber).toCharArray(), out, 0);
  }

  /**
   * Returns the string representation of this number
   *
   * @param out  character buffer to use to store the collate-able string
   *
   * @return   a character buffer representing the number
   */
  public CharArray toCharBuffer(CharArray out) {
    return out.set(toString());
  }

  /**
   * Returns the string representation of this number
   *
   * @param format  the format
   * @param places  the number of decimal places
   *
   * @return   a string representing the number
   */
  public String toFormattedString(String format, int places) {
    boolean    neg     = false;
    boolean    comma   = format.indexOf(',') != -1;
    boolean    paren   = format.indexOf('P') != -1;
    boolean    trail   = format.indexOf('T') != -1;
    boolean    docomma = false;
    char[]     chars;
    char[]     nchars;
    int        i;
    int        n   = 0;
    int        len = 0;
    int        pos = 0;
    int        num = 0;
    BigDecimal tn  = theNumber;

    if (places > -1) {
      tn = theNumber.setScale(places, BigDecimal.ROUND_HALF_UP);
    }

    int       sc = tn.scale();
    CharArray ca = (CharArray) perThreadCA.get();

    ca.set(tn.toPlainString());
    nchars = ca.A;

    int nlen = ca._length;

    chars = new char[nlen * 2];

    int endpos = chars.length - 3;

    if (sc == 0) {
      docomma = true;
    }

    for (i = nlen - 1; i > -1; i--) {
      chars[endpos - n] = nchars[i];

      if (nchars[i] == '-') {
        neg = true;

        break;
      }

      n++;
      num++;

      if (comma && docomma) {
        if (num == 3) {
          chars[endpos - n] = ',';
          n++;
          num = 0;
        }
      } else if (nchars[i] == '.') {
        docomma = true;
        num     = 0;
      }
    }

    n--;
    pos = endpos - n;
    len = endpos - pos + 1;

    if (chars[pos] == ',') {
      pos++;
      len--;
    } else if (chars[pos] == '0') {
      pos++;
      len--;
    }

    if (paren) {
      if (neg) {
        chars[pos + len] = ')';
        chars[--pos]     = '(';
        len              += 2;
      } else {
        chars[pos + len] = ' ';
        chars[--pos]     = ' ';
        len              += 2;
      }
    } else if (trail) {
      if (neg) {
        chars[pos + len] = ((format.indexOf('-') == -1) && neg)
                           ? '-'
                           : ' ';
        len++;
      } else {
        chars[pos + len] = ((format.indexOf('+') != -1) &&!neg)
                           ? '+'
                           : ' ';
        len++;
      }
    } else {
      if (neg) {
        if ((format.indexOf('-') == -1) && neg) {
          chars[--pos] = '-';
          len++;
        }
      } else {
        if ((format.indexOf('+') != -1) &&!neg) {
          chars[--pos] = '+';
          len++;
        }
      }
    }

    return new String(chars, pos, len);
  }

  /**
   * Returns the string representation of this number
   *
   * @return   a string representing the number
   */
  public String toString() {
    return toString(theNumber);
  }

  /**
   * {@inheritDoc}
   *
   * @param num {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static String toString(BigDecimal num) {
    String s;

    if (num.scale() > 0) {
      CharArray ca = (CharArray) perThreadCA.get();

      ca.set(num.toPlainString());

      char[]  chars = ca.A;
      int     len   = ca._length;
      boolean neg   = false;
      int     pos   = 0;

      while((len > 0) && (chars[len - 1] != '.') && (chars[len - 1] < 49)) {
        len--;
      }

      if ((len > 0) && (chars[len - 1] == '.')) {
        len--;
      }

      if (chars[0] == '-') {
        neg = true;
        pos = 1;
      }

      while((pos < len) && (chars[pos] != '.') && (chars[pos] < 49)) {
        pos++;
        len--;
      }

      if (neg) {
        chars[--pos] = '-';
      }

      s = (len == 0)
          ? "0"
          : new String(chars, pos, len);
    } else {
      s = num.toPlainString();
    }

    return s;
  }

  /**
   * Returns the string representation of this number
   *
   * @param out     the output array
   *
   * @return   a string representing the number
   */
  public CharArray toString(CharArray out) {
    return toString(out, false);
  }

  /**
   * Returns the string representation of this number
   *
   * @param out     the output array
   * @param append  boolean <code>true</code> to append to the output; <code>false</code>
   *                otherwise
   *
   * @return   a string representing the number
   */
  public CharArray toString(CharArray out, boolean append) {
    if (!append) {
      out._length = 0;
    }

    if (theNumber.scale() > 0) {
      CharArray ca = (CharArray) perThreadCA.get();

      ca.set(theNumber.toPlainString());

      char[]  chars = ca.A;
      int     len   = ca._length;
      boolean neg   = false;
      int     pos   = 0;

      while((len > 0) && (chars[len - 1] != '.') && (chars[len - 1] < 49)) {
        len--;
      }

      if ((len > 0) && (chars[len - 1] == '.')) {
        len--;
      }

      if (chars[0] == '-') {
        neg = true;
        pos = 1;
      }

      while((pos < len) && (chars[pos] != '.') && (chars[pos] < 49)) {
        pos++;
        len--;
      }

      if (neg) {
        chars[--pos] = '-';
      }

      if (len == 0) {
        out.append('0');
      } else {
        out.append(chars, pos, len);
      }
    } else {
      out.append(theNumber.toPlainString());
    }

    return out;
  }

  /**
   *  Returns a string representation of the object
   *
   *  @param tab  tab prefix to prepend to the string
   *
   *  @return   the string representation of the object
   */
  public String toStringEx(String tab) {
    return tab + toString();
  }

  /**
   * Returns the SDecimal representation of the <code>double</code> argument.
   *
   * @param  num   a <code>double</code>.
   * @return  a  SDecimal representation of the <code>double</code> argument.
   */
  public static SDecimal valueOf(double num) {
    if (num == 0) {
      return zeroDecimal;
    }

    if (num == 1) {
      return oneDecimal;
    }

    if (num == 10) {
      return tenDecimal;
    }

    if (num == 100) {
      return oneHundredDecimal;
    }

    if (num == 3600) {
      return thirtySixHundredDecimal;
    }

    return new SDecimal(num);
  }

  /**
   * Returns the SDecimal representation of the <code>long</code> argument.
   *
   * @param  num   a <code>long</code>.
   * @return  a  SDecimal representation of the <code>long</code> argument.
   */
  public static SDecimal valueOf(long num) {
    if (num == 0) {
      return zeroDecimal;
    }

    if (num == 1) {
      return oneDecimal;
    }

    if (num == 10) {
      return tenDecimal;
    }

    if (num == 100) {
      return oneHundredDecimal;
    }

    if (num == 3600) {
      return thirtySixHundredDecimal;
    }

    return new SDecimal(num);
  }

  /**
   * {@inheritDoc}
   *
   * @param val {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public static SDecimal valueOf(SNumber val) {
    if (val.isBigNumber()) {
      return new SDecimal(val);
    }

    int  decplaces = val.decimalPlaces();
    long mantissa  = val.longValue();
    long fraction  = val.fractionalPart();

    if (decplaces == 0) {
      if (mantissa == 0) {
        return zeroDecimal;
      }

      if (mantissa == 1) {
        return oneDecimal;
      }

      if (mantissa == 10) {
        return tenDecimal;
      }

      if (mantissa == 100) {
        return oneHundredDecimal;
      }

      if (mantissa == 3600) {
        return thirtySixHundredDecimal;
      }
    } else if ((mantissa == 0) && (fraction == 1) && (decplaces == 2)) {
      return ptZeroOneDecimal;
    }

    return new SDecimal(val);
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal xor(int num) {
    if (immutable) {
      return new SDecimal(this).xor(num);
    }

    SDecimal snum = new SDecimal(num);

    return xor(snum);
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal xor(long num) {
    if (immutable) {
      return new SDecimal(this).xor(num);
    }

    SDecimal snum = new SDecimal(num);

    return xor(snum);
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal xor(SDecimal snum) {
    if (immutable) {
      return new SDecimal(this).xor(snum);
    }

    theNumber = new BigDecimal(theNumber.toBigInteger().xor(snum.theNumber.toBigInteger()));

    return this;
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal xor(String str) {
    if (immutable) {
      return new SDecimal(this).xor(str);
    }

    SDecimal snum = new SDecimal(str);

    return xor(snum);
  }

  /**
   * Zeros this number
   *
   * @return   this <code>SDecimal</code> object representing the result of the
   *           operation
   */
  public SDecimal zero() {
    if (immutable) {
      return new SDecimal(BigDecimal.ZERO);
    }

    theNumber = BigDecimal.ZERO;

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param num {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  final boolean isPowerOfTen(long num) {
    num *= ((num < 0)
            ? (-1)
            : 1);

    while((num % 10) == 0) {
      num /= 10;
    }

    return (num == 1)
           ? true
           : false;
  }

  /**
   * Sets the <code>SDecimal</code> to the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause the method to return <code>false</code>
   *
   * @return   <code>true</code> if the characters represented a valid number; <code>false</code>
   *           otherwise
   */
  private boolean setValueEx(String str, boolean javaparsecompat) {
    if (immutable) {
      throw new IllegalStateException("Immutable object");
    }

    if (str == null) {
      return false;
    }

    int len = (int) str.length();

    if (len == 0) {
      if (javaparsecompat) {
        return false;
      }

      theNumber = BigDecimal.ZERO.multiply(BigDecimal.ZERO, mathContext);

      return true;
    }

    if (charBuf == null) {
      charBuf = new CharArray(len);
    } else {
      charBuf.ensureCapacity(len);
    }

    str.getChars(0, len, charBuf.A, 0);

    return setValueEx(charBuf.A, 0, len, javaparsecompat);
  }
}
