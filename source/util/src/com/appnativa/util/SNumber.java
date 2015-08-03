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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.text.DecimalFormatSymbols;

import java.util.List;
import java.util.Locale;

/**
 * The primary purpose of this class is to provide for the efficient conversion of
 * numbers to string and vice-versa. This class also supports 20 digits of
 * precision for mathematical operations.
 * <p>By default, the class will accept as input, strings that contain trailing
 * non-numeric values. When this occurs all characters following the first
 * occurrence of a non-numeric character is discarded. Therefore the strings "<b>4</b>",
 * "<b>4 apples</b>", and "<b>4 apples and 10 oranges</b>"
 * would all have a numeric value of four (4). Certain methods and constructors
 * allow this behavior to be overridden. In which case, the strings "<b>4
 * apples</b>", and "<b>4 apples and 10 oranges</b>" would both have
 * a numeric value of zero (0). This mode is called the Java compatibility mode, as
 * it the way the strings would be treated by Java classes that convert strings to
 * numbers.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
@SuppressWarnings("serial")
public final class SNumber extends Number implements Comparable, Cloneable {

  /** allow numbers with exponents */
  public static final int ALLOW_EXPONENT = 1;

  /** allow multiple operators to precede a string */
  public static final int ALLOW_OPS = 8;

  /** allow a number with a trailing e (exponent places holder) */
  public static final int ALLOW_TRAILING_E = 2;

  /** allow insignificant zeros in strings */
  public static final int ALLOW_ZEROS = 4;

  /** the number zero */
  public static final SNumber ZERO = new SNumber(0).makeImmutable();

  /** the number one */
  public static final SNumber ONE = new SNumber(1).makeImmutable();
  public static final char    ZERO_DIGIT;
  public static final char    MINUS_SIGN;
  public static final char    PLUS_SIGN;
  public static final char    DECIMAL_POINT;
  private static final char   EXPONENT_LOWERCASE;
  private static final char   EXPONENT_UPPERCASE;
  private static final char   EXPONENT_CHARS_LOWERCASE[];
  private static final char   EXPONENT_CHARS_UPPERCASE[];
  private static int          maxDigits = 19;
  private static final int    tenlen    = 11;
  private static final double fractens[];
  private static final long   tens[];
  public static String        NO_POWER_FRACTION_MSG = "fractional power not supported for numbers 2**-63 <= >= 2**63";

  static {
    int i = 0;

    tens = new long[tenlen];

    while(i < tenlen) {
      tens[i] = (long) StrictMath.pow(10, i);
      i++;
    }

    fractens = new double[tenlen];
    i        = 0;

    while(i < tenlen) {
      fractens[i] = StrictMath.pow(10, -i);
      i++;
    }

    DecimalFormatSymbols fs;

    if ("true".equals(System.getProperty("snumber.usSymbols"))) {
      fs = new DecimalFormatSymbols(Locale.US);
    } else {
      fs = new DecimalFormatSymbols();
    }

    ZERO_DIGIT    = fs.getZeroDigit();
    MINUS_SIGN    = fs.getMinusSign();
    PLUS_SIGN     = '+';
    DECIMAL_POINT = fs.getDecimalSeparator();

    String s = "E";

    try {
      Method m = DecimalFormatSymbols.class.getDeclaredMethod("getExponentSeparator");

      if (m != null) {
        s = (String) m.invoke(fs);
      }
    } catch(Throwable ignore) {}

    s = s.toLowerCase();

    char   c     = s.charAt(0);
    char[] chars = (s.length() == 1)
                   ? null
                   : s.toCharArray();

    EXPONENT_CHARS_LOWERCASE = chars;
    EXPONENT_LOWERCASE       = c;
    s                        = s.toUpperCase();
    c                        = s.charAt(0);
    chars                    = (s.length() == 1)
                               ? null
                               : s.toCharArray();
    EXPONENT_CHARS_UPPERCASE = chars;
    EXPONENT_UPPERCASE       = c;
  }

  public final static String POINT_ONE                = DECIMAL_POINT + "1";
  public final static String POINT_ZERO_ONE           = DECIMAL_POINT + ZERO_DIGIT + "1";
  public final static String POINT_ZERO_ZERO_ONE      = DECIMAL_POINT + ZERO_DIGIT + ZERO_DIGIT + "1";
  public final static String POINT_ZERO_ZERO_ZERO_ONE = DECIMAL_POINT + ZERO_DIGIT + ZERO_DIGIT + ZERO_DIGIT + "1";

  /**  */
  final static char[] DigitOnes = {
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9',
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9',
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9',
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9',
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9',
  };

  /**  */
  final static char[] DigitTens = {
    ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT, ZERO_DIGIT,
    ZERO_DIGIT, '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3',
    '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5',
    '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7',
    '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
  };

  /**  */
  static final char[] digits = {
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
  };

  /**  */
  static final String divideByZero = "divide by zero";

  /** hexadecimal digits */
  static final char[]        hexDigits       = {
    ZERO_DIGIT, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
  };
  private static ThreadLocal perThreadNumber = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new SNumber(0);
    }
  };
  private static ThreadLocal perThreadBuffer = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };

  /** big sdecimal object representing the number */
  protected SDecimal bigNumber;

  /** the number of sdecimal places */
  protected int decplaces;

  /** the fractional component */
  protected long fraction;

  /** the mantissa */
  protected long mantissa;

  /**  */
  private CharArray        strBuffer;
  private boolean          immutable;
  private SDecimal         sdecimal;
  private static final int dot      = 1;
  private static final int exponent = 2;

  /**
   * Objective-C code for subclassing NSNumber class cluster
   *
   * -(id)initWithBytes:(const void *)value objCType:(const char *)type {
   *   self = [super init];
   *   if (self) {
   *       NSNumber* num= [[NSNumber alloc] initWithBytes:value objCType:type];
   *       [self setValue: [num stringValue]];
   *   }
   *   return self;
   * }
   *
   * +(NSValue *)valueWithBytes:(const void *)value objCType:(const char *)type {
   *   NSNumber* num= [[NSNumber alloc] initWithBytes:value objCType:type];
   *   return [[SPUTSNumber alloc] initWithNSString:[num stringValue]];
   * }
   *
   * -(void)getValue:(void *)value {
   *   NSNumber* num;
   *   if(bigNumber_ || decplaces_>0) {
   *       num=[NSNumber numberWithDouble:[[self description] doubleValue]];
   *   }
   *   else if(mantissa_ < INT_MAX && mantissa_> INT_MIN) {
   *      num=[NSNumber numberWithLong:mantissa_];
   *   }
   *   else {
   *       num=[NSNumber numberWithLongLong:mantissa_];
   *   }
   *   [num getValue:value];
   * }
   *
   * -(const char *)objCType {
   *   if(decplaces_==0) {
   *       if(abs(mantissa_) < INT_MAX) {
   *           return @encode(int);
   *       }
   *       return @encode(long long);
   *   }
   *   else if (bigNumber_) {
   *       return @encode(double);
   *  }
   * return decplaces_ < 10 ? @encode(float) : @encode(double);
   * }
   */

  /**
   * Creates a new <code>SNumber</code> object with a value of zero.
   */
  public SNumber() {}

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param bd  the big sdecimal value
   */
  public SNumber(BigDecimal bd) {
    bigNumber = new SDecimal(bd);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param bd  the big sdecimal value
   */
  public SNumber(BigInteger bd) {
    bigNumber = new SDecimal(bd);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param num  the value
   */
  public SNumber(double num) {
    setValue(num);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param num  the value
   */
  public SNumber(int num) {
    mantissa = num;
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param num  the value
   */
  public SNumber(long num) {
    mantissa = num;
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param snum  the value
   */
  public SNumber(SNumber snum) {
    mantissa  = snum.mantissa;
    fraction  = snum.fraction;
    decplaces = snum.decplaces;
    bigNumber = (snum.bigNumber == null)
                ? null
                : new SDecimal(snum.bigNumber);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param str  the value
   */
  public SNumber(String str) {
    setValue(str, false);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   */
  public SNumber(String str, boolean javaparsecompat) {
    setValue(str, javaparsecompat);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   */
  public SNumber(char[] chars, int pos, int len) {
    setValueEx(chars, pos, len, false, false);
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param mantissa   the mantissa
   * @param fraction   the fraction
   * @param decplaces  the number of sdecimal places
   */
  public SNumber(long mantissa, long fraction, int decplaces) {
    this.mantissa  = mantissa;
    this.fraction  = fraction;
    this.decplaces = decplaces;
  }

  /**
   * Creates a new <code>SNumber</code> object.
   *
   * @param chars            the character array
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   */
  public SNumber(char[] chars, int pos, int len, boolean javaparsecompat) {
    setValue(chars, pos, len, javaparsecompat);
  }

  public static void setMaxDigits(int max) {
    if ((max > 1) && (max < 20)) {
      maxDigits = max;
    }
  }

  public static int getMaxDigits() {
    return maxDigits;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber add(double num) {
    if (immutable) {
      return new SNumber(this).add(num);
    }

    SNumber snum = new SNumber(num);

    return add(snum);
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber add(int num) {
    return add((long) num);
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber add(long num) {
    if (immutable) {
      return new SNumber(this).add(num);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.add(valueOf(num));

      return this;
    }

    if ((mantissa >= Integer.MAX_VALUE) || (mantissa >= Integer.MIN_VALUE) || (num >= Integer.MAX_VALUE)
        || (num >= Integer.MIN_VALUE)) {
      return add(SDecimal.valueOf(num));
    }

    mantissa += num;

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber add(SNumber snum) {
    if (immutable) {
      return new SNumber(this).add(snum);
    }

    if ((bigNumber != null) || (snum.bigNumber != null)) {
      SDecimal bd2 = (snum.bigNumber == null)
                     ? SDecimal.valueOf(snum)
                     : snum.bigNumber;
      SDecimal bd1 = (bigNumber == null)
                     ? new SDecimal(this)
                     : bigNumber;

      bd1 = bd1.add(bd2);
      fromSDecimal(bd1);

      return this;
    }

    if ((snum.mantissa == 0) && (snum.fraction == 0)) {
      return this;
    }

    if ((mantissa == 0) && (fraction == 0)) {
      mantissa  = snum.mantissa;
      fraction  = snum.fraction;
      decplaces = snum.decplaces;

      return this;
    }

    if ((mantissa >= Integer.MAX_VALUE) || (mantissa < Integer.MIN_VALUE) || (snum.mantissa >= Integer.MAX_VALUE)
        || (snum.mantissa <= Integer.MIN_VALUE)) {
      return add(SDecimal.valueOf(snum));
    }

    do {
      long num;
      long base;
      long n;

      if (((mantissa <= 0) && (fraction <= 0) && ((snum.mantissa <= 0) && (snum.fraction <= 0)))
          || ((mantissa >= 0) && (fraction >= 0) && ((snum.mantissa >= 0) && (snum.fraction >= 0)))) {
        num = snum.fraction;
        n   = decplaces - snum.decplaces;

        if (n < 0) {
          fraction  *= tenpow(-n);
          decplaces = snum.decplaces;
        } else if (n > 0) {
          num = snum.fraction * tenpow(n);
        }

        mantissa += snum.mantissa;
        fraction += num;

        if (fraction != 0) {
          base     = tenpow(decplaces);
          mantissa += (fraction / base);
          fraction = fraction % base;
        }

        break;
      }

      if ((mantissa == 0) && (fraction == 0)) {
        mantissa = snum.mantissa;
        fraction = snum.fraction;

        break;
      }

      long    man;
      long    frac;
      int     dec;
      boolean ineg    = (mantissa < 0) || (fraction < 0);
      boolean hneg    = (snum.mantissa < 0) || (snum.fraction < 0);
      boolean ilarger = true;

      man  = snum.mantissa;
      frac = snum.fraction;
      dec  = snum.decplaces;
      n    = decplaces - dec;

      if (n < 0) {
        fraction  *= tenpow(-n);
        decplaces = dec;
      } else if (n > 0) {
        frac = frac * tenpow(n);
      }

      if (ineg || hneg) {
        mantissa *= ((mantissa < 0)
                     ? (-1)
                     : 1);
        fraction *= ((fraction < 0)
                     ? (-1)
                     : 1);
        man      *= ((man < 0)
                     ? (-1)
                     : 1);
        frac     *= ((frac < 0)
                     ? (-1)
                     : 1);

        do {
          if ((man < mantissa) || ((frac < fraction) && (man == mantissa))) {
            if (hneg || ineg) {
              man  *= -1;
              frac *= -1;
            }

            break;
          }

          num      = man;
          man      = mantissa;
          mantissa = num;
          num      = frac;
          frac     = fraction;
          fraction = num;
          ilarger  = false;
          man      *= -1;
          frac     *= -1;
        } while(false);
      }

      if (mantissa != 0) {    // borrow one to gurantee that fraction is bigger than snum.fraction
        mantissa--;
        fraction += tenpow(decplaces);
      }

      mantissa += man;
      fraction += frac;

      if (fraction != 0) {
        base     = tenpow(decplaces);
        mantissa += (fraction / base);
        fraction = fraction % base;
        n        = (fraction < 0)
                   ? (fraction * -1)
                   : fraction;

        while((n > 0) && ((n % 10) == 0)) {
          n /= 10;
          decplaces--;
        }

        fraction = (fraction < 0)
                   ? (n * -1)
                   : n;

        if ((mantissa > 0) && (fraction < 0)) {
          fraction *= -1;
        }
      }

      if (ilarger) {
        if (ineg) {
          mantissa *= ((mantissa > 0)
                       ? (-1)
                       : 1);
          fraction *= ((fraction > 0)
                       ? (-1)
                       : 1);
        }
      } else {
        if (hneg) {
          mantissa *= ((mantissa > 0)
                       ? (-1)
                       : 1);
          fraction *= ((fraction > 0)
                       ? (-1)
                       : 1);
        }
      }
    } while(false);

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber add(String str) {
    if (immutable) {
      return new SNumber(this).add(str);
    }

    SNumber snum = new SNumber(str);

    return add(snum);
  }

  /**
   * Performs an <b>AND</b> operation on this number using the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber and(SNumber snum) {
    if (immutable) {
      return new SNumber(this).and(snum);
    }

    if (snum.bigNumber != null) {
      bigNumber = toSDecimal();
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.and(snum.toSDecimal());

      return this;
    }

    mantissa  = mantissa & snum.mantissa;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Performs an <b>AND</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber and(String str) {
    if (immutable) {
      return new SNumber(this).and(str);
    }

    SNumber snum = new SNumber(str);

    return and(snum);
  }

  /**
   * Returns a <code>boolean</code> representation the specified value.
   *
   * @param bol  the value
   *
   * @return   the string as a <code>boolean</code>
   */
  public static boolean booleanValue(Object bol) {
    if (bol == null) {
      return false;
    }

    if (bol instanceof Boolean) {
      return ((Boolean) bol).booleanValue();
    }

    if (bol instanceof Number) {
      return ((Number) bol).intValue() != 0;
    }

    return booleanValue(bol.toString());
  }

  /**
   * Returns a <code>boolean</code> representation the specified value.
   *
   * @param str  the value
   *
   * @return   the string as a <code>boolean</code>
   */
  public static boolean booleanValue(String str) {
    if ((str == null) || (str.length() == 0)) {
      return false;
    }

    if (str.equalsIgnoreCase("true")) {
      return true;
    }

    return longValue(str) != 0;
  }

  /**
   * Converts an array of bytes to hexadecimal digits
   *
   * @param b    the byte array
   *
   * @return   a hexadecimal string representing the byte array
   */
  public static String bytesToHexString(byte[] b) {
    return bytesToHexString(b, 0, b.length);
  }

  /**
   * Converts an array of bytes to hexadecimal digits
   *
   * @param b    the byte array
   * @param pos  the starting position within the array
   * @param len  the number of characters in the array to use
   *
   * @return   a hexadecimal string representing the byte array
   */
  public static String bytesToHexString(byte[] b, int pos, int len) {
    char[] buf = new char[len * 2];

    for (int i = 0; i < len; i++) {
      buf[i * 2]       = hexDigits[(b[i + pos] & 0xff) >> 4];
      buf[(i * 2) + 1] = hexDigits[b[i + pos] & 0xf];
    }

    return new String(buf);
  }

  /**
   * Converts an array of chars to hexadecimal digits
   *
   * @param b    the character array
   *
   * @return   a hexadecimal string representing the byte array
   */
  public static String charsToHexString(char[] b) {
    return charsToHexString(b, 0, b.length);
  }

  /**
   * Converts an array of chars to hexadecimal digits
   *
   * @param b    the character array
   * @param pos  the starting position within the array
   * @param len  the number of characters in the array to use
   *
   * @return   a hexadecimal string representing the byte array
   */
  public static String charsToHexString(char[] b, int pos, int len) {
    char[] buf = new char[len * 2];

    for (int i = 0; i < len; i++) {
      buf[i * 2]       = hexDigits[(b[i + pos] & 0xff) >> 4];
      buf[(i * 2) + 1] = hexDigits[b[i + pos] & 0xf];
    }

    return new String(buf);
  }

  /**
   * Returns a copy of this number
   *
   * @return   a clone of this number.
   */
  public Object clone() {
    return new SNumber(this);
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
    SNumber snum = null;

    if (obj instanceof SNumber) {
      snum = (SNumber) obj;
    } else if (obj instanceof Number) {
      snum = new SNumber(((Number) obj).doubleValue());
    } else {
      snum = new SNumber(obj.toString());
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
  public final int compareTo(SNumber num) {
    if (bigNumber != null) {
      return bigNumber.compareTo(SDecimal.valueOf(num));
    }

    final double d = (doubleValue() - num.doubleValue());

    if (d == 0) {
      return 0;
    }

    return (d < 0)
           ? -1
           : 1;
  }

  /**
   * Specifies that this object will be used for doing number to string conversions
   * and should create and internal buffer for doing those operations (otherwise a
   * thread local buffer is used)
   *
   * @return   this <code>SNumber</code>
   */
  public SNumber createReusableInternalBuffers() {
    strBuffer = new CharArray();

    return this;
  }

  /**
   * Returns an <code>int</code> representing the number of sdecimal places of the
   * fractional part of this number
   *
   * @return   the number of sdecimal places
   */
  public final int decimalPlaces() {
    if (bigNumber != null) {
      return bigNumber.decimalPlaces();
    }

    return decplaces;
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divide(double num) {
    if (immutable) {
      return new SNumber(this).divide(num);
    }

    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    SNumber snum = new SNumber(num);

    return divide(snum);
  }

  /**
   *
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divide(int num) {
    if (immutable) {
      return new SNumber(this).divide(num);
    }

    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    SNumber snum = new SNumber(num);

    return divide(snum);
  }

  /**
   * Divides this number by the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divide(long num) {
    if (immutable) {
      return new SNumber(this).setValue(num);
    }

    if (num == 0) {
      throw new ArithmeticException(divideByZero);
    }

    SNumber snum = new SNumber(num);

    return divide(snum);
  }

  /**
   *  Divides this number by the specified value
   *
   *  @param snum  the value
   *
   *  @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divide(SDecimal snum) {
    if (immutable) {
      return new SNumber(this).divide(snum);
    }

    if (snum.isZero()) {
      throw new ArithmeticException(divideByZero);
    }

    if (isZero()) {
      return this;
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.divide(snum);
    fromSDecimal(bd1);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divide(SNumber snum) {
    if (immutable) {
      return new SNumber(this).divide(snum);
    }

    if ((bigNumber == null) && (snum.fraction == 0) && (snum.bigNumber == null)) {
      long snummantissa = snum.mantissa;

      if (snummantissa == 0) {
        throw new ArithmeticException(divideByZero);
      }

      if ((mantissa == 0) && (fraction == 0)) {
        return this;
      }

      if (snummantissa == 1) {
        return this;
      }

      if (snummantissa == 10) {
        return shiftDecimal(-1);
      }

      if (snummantissa == 100) {
        return shiftDecimal(-2);
      }

      if (snummantissa == 1000) {
        return shiftDecimal(-3);
      }

      if (snummantissa == 10000) {
        return shiftDecimal(-4);
      }

      if (snummantissa == 2) {
        return multiply(SDecimal.bd2);
      }

      if (snummantissa == 4) {
        return multiply(SDecimal.bd4);
      }

      if (snummantissa == 24) {
        return multiply(SDecimal.bd24);
      }

      if (snummantissa == 60) {
        return multiply(SDecimal.bd60);
      }

      if (snummantissa == 128) {
        return multiply(SDecimal.bd128);
      }

      if (snummantissa == 256) {
        return multiply(SDecimal.bd256);
      }

      if (snummantissa == 365) {
        return multiply(SDecimal.bd365);
      }

      if (snummantissa == 512) {
        return multiply(SDecimal.bd512);
      }

      if (snummantissa == 1024) {
        return multiply(SDecimal.bd1024);
      }

      if (snummantissa == 2048) {
        return multiply(SDecimal.bd2048);
      }

      if (snummantissa == 3600) {
        return multiply(SDecimal.bd3600);
      }

      if (snummantissa == 4096) {
        return multiply(SDecimal.bd4096);
      }

      if (snummantissa == 8192) {
        return multiply(SDecimal.bd8192);
      }

      if (snummantissa == 32768) {
        return multiply(SDecimal.bd32768);
      }

      if (snummantissa == 65536) {
        return multiply(SDecimal.bd65536);
      }
    }

    if (bigNumber != null) {
      if (bigNumber.isZero()) {
        return this;
      }
    } else {
      if ((mantissa == 0) && (fraction == 0)) {
        return this;
      }
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.divide(snum);
    fromSDecimal(bd1);

    return this;
  }

  /**
   * Divides this number by the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber divide(String str) {
    if (immutable) {
      return new SNumber(this).divide(str);
    }

    SNumber snum = new SNumber(str);

    return divide(snum);
  }

  /**
   * Divides this number by the specified value truncating any fractional protion
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber divideInteger(SNumber snum) {
    if (immutable) {
      return new SNumber(this).divideInteger(snum);
    }

    if ((bigNumber == null) && (snum.fraction == 0)) {
      long snummantissa = snum.mantissa;

      if (snummantissa == 0) {
        throw new ArithmeticException(divideByZero);
      }

      if ((mantissa == 0) && (fraction == 0)) {
        return this;
      }

      if (snummantissa == 1) {
        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 10) {
        shiftDecimal(-1);
        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 100) {
        shiftDecimal(-2);
        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 1000) {
        shiftDecimal(-3);
        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 10000) {
        shiftDecimal(-4);
        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 2) {
        multiply(SDecimal.bd2);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 4) {
        multiply(SDecimal.bd4);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 24) {
        multiply(SDecimal.bd24);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 60) {
        multiply(SDecimal.bd60);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 128) {
        multiply(SDecimal.bd128);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 256) {
        multiply(SDecimal.bd256);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 365) {
        multiply(SDecimal.bd365);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 512) {
        multiply(SDecimal.bd512);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 1024) {
        multiply(SDecimal.bd1024);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 2048) {
        multiply(SDecimal.bd2048);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 3600) {
        multiply(SDecimal.bd3600);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 4096) {
        multiply(SDecimal.bd4096);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 8192) {
        multiply(SDecimal.bd8192);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 32768) {
        multiply(SDecimal.bd32768);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }

      if (snummantissa == 65536) {
        multiply(SDecimal.bd65536);

        if (bigNumber != null) {
          bigNumber.round(0, false);
        }

        fraction  = 0;
        decplaces = 0;

        return this;
      }
    }

    if (bigNumber != null) {
      if (bigNumber.isZero()) {
        return this;
      }
    } else {
      if ((mantissa == 0) && (fraction == 0)) {
        return this;
      }
    }

    SDecimal bd2 = (snum.bigNumber == null)
                   ? SDecimal.valueOf(snum)
                   : snum.bigNumber;
    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bigNumber = bd1.divideInteger(bd2);
    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Returns a <code>double</code> representation of this number
   *
   * @return   the number as a <code>double</code>
   */
  public final double doubleValue() {
    if (bigNumber != null) {
      return bigNumber.doubleValue();
    }

    double d   = mantissa;
    double dec = (fraction != 0)
                 ? (negtenpow(decplaces) * fraction)
                 : 0;

    d += dec;

    return d;
  }

  /**
   * Returns a <code>double</code> representation of the specified value. Trailing
   * non-numeric characters are allowed in the string
   *
   * @param str  the value
   *
   * @return   the number as a <code>double</code>
   */
  public static double doubleValue(String str) {
    return doubleValue(str, false);
  }

  /**
   * Returns a <code>double</code> representation the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as a <code>double</code>
   */
  public static double doubleValue(String str, boolean javaparsecompat) {
    SNumber snum = (SNumber) perThreadNumber.get();

    snum.setValue(str, javaparsecompat);

    return snum.doubleValue();
  }

  /**
   * Creates a double from a set of numeric parts
   *
   * @param mantissa   the mantissa
   * @param fraction   the fraction
   * @param decplaces  the number of sdecimal places
   *
   * @return   the double
   */
  public static double doubleValue(long mantissa, long fraction, int decplaces) {
    double d   = mantissa;
    double dec = StrictMath.pow(10, -decplaces) * fraction;

    d += dec;

    return d;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(double num) {
    if (bigNumber != null) {
      return bigNumber.equals(valueOf(num));
    }

    if (doubleValue() == num) {
      return true;
    }

    return false;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(int num) {
    return equals((long) num);
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param num  the value
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(long num) {
    if (bigNumber != null) {
      return bigNumber.equals(valueOf(num));
    }

    if ((mantissa == num) && (fraction == 0) && (decplaces == 0)) {
      return true;
    }

    return false;
  }

  /**
   * Compares this object with the specified object for order for equality
   *
   * @param obj  the object
   *
   * @return   <code>true</code> if they are equal; <code>false</code> otherwise
   */
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    SNumber snum = null;

    if (obj instanceof SNumber) {
      snum = (SNumber) obj;
    } else {
      snum = new SNumber(obj.toString());
    }

    return equals(snum);
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param snum  the number
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(SNumber snum) {
    if (snum == null) {
      return false;
    }

    if (snum.bigNumber != null) {
      bigNumber = toSDecimal();
    }

    if (bigNumber != null) {
      return bigNumber.equals(snum.toSDecimal());
    }

    if ((mantissa == snum.mantissa) && (fraction == snum.fraction) && (decplaces == snum.decplaces)) {
      return true;
    }

    return false;
  }

  /**
   * Tests this number with the specified value for equality
   *
   * @param mantissa   the mantissa
   * @param fraction   the fractional value
   * @param decplaces  the number of sdecimal places
   *
   * @return   <code>true</code> if they are; <code>false</code> otherwise
   */
  public boolean equals(long mantissa, long fraction, int decplaces) {
    if (bigNumber != null) {
      SNumber num = (SNumber) perThreadNumber.get();

      num.setValue(mantissa, fraction, decplaces);

      return bigNumber.equals(SDecimal.valueOf(num));
    }

    if ((this.mantissa == mantissa) && (this.fraction == fraction) && (this.decplaces == decplaces)) {
      return true;
    }

    return false;
  }

  /**
   * Returns a <code>float</code> representation of this number
   *
   * @return   the number as a <code>float</code>
   */
  public float floatValue() {
    return (float) doubleValue();
  }

  /**
   * Returns a <code>float</code> representation of the specified value. Trailing
   * non-numeric characters are allowed in the string
   *
   * @param str  the value
   *
   * @return   the number as a <code>float</code>
   */
  public static float floatValue(String str) {
    return floatValue(str, false);
  }

  /**
   * Returns a <code>float</code> representation the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as a <code>float</code>
   */
  public static float floatValue(String str, boolean javaparsecompat) {
    SNumber snum = (SNumber) perThreadNumber.get();

    snum.setValue(str, javaparsecompat);

    return snum.floatValue();
  }

  /**
   * Returns a <code>float</code> representation the specified value
   *
   * @param chars            the character array
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as a <code>int</code>
   *
   */
  public static float floatValue(char[] chars, int pos, int len, boolean javaparsecompat) {
    SNumber snum = (SNumber) perThreadNumber.get();

    snum.setValue(chars, pos, len, javaparsecompat);

    return snum.floatValue();
  }

  /**
   * Converts a fraction to a string
   *
   * @param fraction   the fraction
   * @param decplaces  the sdecimal places
   * @param chars      the character array
   * @param pos        the starting position within the array
   *
   * @return   a integer representing the number of characters in the string
   */
  public static int fractionToString(long fraction, int decplaces, char[] chars, int pos) {
    if (fraction == 0) {
      return 0;
    }

    if ((fraction < 10) && (decplaces == 1)) {
      chars[pos] = (char) (ZERO_DIGIT + (char) fraction);

      return 1;
    }

    long frac = (fraction < 0)
                ? -fraction
                : fraction;
    int  len  = stringSize(fraction);
    int  cl   = decplaces - len;

    if (cl > 0) {
      int i = 0;

      while(i < cl) {
        chars[pos++] = ZERO_DIGIT;
        i++;
      }
    }

    getChars(frac, pos + len, chars);

    return decplaces;
  }

  /*
   * Returns a <code>long</code> representation of the fractional part of this number
   * adjusted for the appropriate sdecimal places
   *
   * @return   the number as a <code>long</code>
   */
  public final long fractionValue() {
    if (bigNumber != null) {
      return bigNumber.fractionValue();
    }

    long f = fraction;

    if (f != 0) {
      int fsize = stringSize(f);
      int cl    = decplaces - fsize;

      if (cl > 0) {
        f *= tenpow(cl);
      }
    }

    return f;
  }

  /**
   * Returns a string representation of the fractional part (not including the sdecimal) of this number
   *
   * @return   the fraction value as a string
   */
  public String fractionValueString() {
    if (bigNumber != null) {
      return bigNumber.fractionValueString();
    }

    CharArray out = strBuffer;

    if ((decplaces == 0) || (fraction == 0)) {
      return "";
    }

    if (out == null) {
      out = (CharArray) perThreadBuffer.get();
    }

    toString(out, false);

    int n = out.indexOf(DECIMAL_POINT);

    return (n == -1)
           ? ""
           : out.substring(n + 1);
  }

  /**
   * Returns a <code>long</code> representation of the fractional part of this number
   *
   * @return   the number as a <code>long</code>
   */
  public final long fractionalPart() {
    if (bigNumber != null) {
      return bigNumber.fractionValue();
    }

    return fraction;
  }

  /**
   * Reads a <code>SNumber</code> value from an input stream
   *
   * @param val  this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *             being requested
   * @param in   the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static SNumber fromStream(SNumber val, InputStream in) throws IOException {
    return readSNumber(in);
  }

  /**
   * Returns the most appropriate java Number object that represents this SNumber
   *
   * @return the number
   */
  public Number getNumber() {
    if (bigNumber != null) {
      return bigNumber.getBigDecimal();
    }

    if (decplaces == 0) {
      return Long.valueOf(mantissa);
    }

    return Double.valueOf(doubleValue());
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(double num) {
    if (doubleValue() > num) {
      return true;
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
  public final boolean gt(int num) {
    return gt((long) num);
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean gt(long num) {
    if (bigNumber != null) {
      return bigNumber.gt(valueOf(num));
    }

    if (mantissa > num) {
      return true;
    }

    if (mantissa < num) {
      return false;
    }

    if (fraction == 0) {
      return false;
    }

    if (fraction < 0) {    // fraction<0 means both numbers negative
      return false;
    }

    return true;    // fraction>0 means both numbers positive
  }

  /**
   * Test this number to see if it is greater than the specified value
   *
   * @param snum  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public final boolean gt(SNumber snum) {
    return doubleValue() > snum.doubleValue();
  }

  /**
   * Returns the hash code for this object
   *
   * @return   the hash code for the number
   */
  public int hashCode() {
    if (bigNumber != null) {
      return bigNumber.hashCode();
    }

    return (int) (mantissa ^ fraction) * decplaces;
  }

  /**
   * Converts a hexadecimal string to its binary equivalent and returns the value as
   * an array of bytes.
   *
   * @param str  the string that contains character array
   *
   * @return   a set of bytes representing the value of the hexadecimal string
   */
  public static byte[] hexStringToBytes(String str) {
    char[] b = str.toCharArray();

    return hexStringToBytes(b, 0, b.length);
  }

  /**
   * Converts a hexadecimal string to its binary equivalent and returns the value as
   * an array of bytes.
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   a set of bytes representing the value of the hexadecimal string
   */
  public static byte[] hexStringToBytes(char[] chars, int pos, int len) {
    len = len / 2;

    byte[] nb = new byte[len];

    for (int i = 0; i < len; i++) {
      nb[i] = (byte) ((Character.digit(chars[(i * 2) + pos], 16) << 4) & 0xff);
      nb[i] += (byte) ((Character.digit(chars[(i * 2) + pos + 1], 16)) & 0xff);
    }

    return nb;
  }

  /**
   * Converts a hexadecimal string to its binary equivalent and returns the value as
   * an array of chars.
   *
   * @param str  the string that contains character array
   *
   * @return   a set of chars representing the value of the hexadecimal string
   */
  public static char[] hexStringToChars(String str) {
    char[] b = str.toCharArray();

    return hexStringToChars(b, 0, b.length);
  }

  /**
   * Converts a hexadecimal string to its binary equivalent and returns the value as
   * an array of chars.
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   a set of chars representing the value of the hexadecimal string
   */
  public static char[] hexStringToChars(char[] chars, int pos, int len) {
    len = len / 2;

    char[] nb = new char[len];

    for (int i = 0; i < len; i++) {
      nb[i] = (char) ((Character.digit(chars[(i * 2) + pos], 16) << 4) & 0xff);
      nb[i] += (char) ((Character.digit(chars[(i * 2) + pos + 1], 16)) & 0xff);
    }

    return nb;
  }

  /**
   * Converts a hexadecimal string to a long value
   * a long
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   the long value
   */
  public static long hexStringToLong(char[] chars, int pos, int len) {
    long num = 0;
    int  n;

    len += pos;

    while(pos < len) {
      n = Character.getNumericValue(chars[pos++]);

      if ((n < 0) || (n > 15)) {
        n = 0;
      }

      num <<= 4;
      num |= (n & 0xf);
    }

    return num;
  }

  /**
   * Returns an integer representation of this number
   *
   * @return   the number as an integer
   */
  public int intValue() {
    if (bigNumber != null) {
      return (int) bigNumber.longValue();
    }

    return (int) mantissa;
  }

  /**
   * Returns an integer representation the specified value. Trailing non-numeric
   * characters are allowed in the string
   *
   * @param str  the value
   *
   * @return   the string as an integer
   */
  public static int intValue(String str) {
    return ((SNumber) perThreadNumber.get()).setValue(str, false).intValue();
  }

  /**
   * Returns an <code>int</code> representation the specified value.
   *
   * @param str  the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as an integer
   */
  public static int intValue(String str, boolean javaparsecompat) {
    return ((SNumber) perThreadNumber.get()).setValue(str, javaparsecompat).intValue();
  }

  /**
   * Returns an <code>int</code> representation the specified value.
   *
   * @param chars            the character array
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as a <code>int</code>
   *
   */
  public static int intValue(char[] chars, int pos, int len, boolean javaparsecompat) {
    return ((SNumber) perThreadNumber.get()).setValue(chars, pos, len, javaparsecompat).intValue();
  }

  public final static int intValueEx(SNumber snum, char[] chars, int len, char[] ops, int oppos, int oplen) {
    if (ops == null) {
      oplen = 0;
    }

    if (len < 2) {
      int mantissa = 0;

      mantissa = (len == 0)
                 ? 0
                 : Character.getNumericValue(chars[0]);

      if ((mantissa < 0) || (mantissa > 9)) {
        mantissa = 0;
      }

      return (oplen == 0)
             ? mantissa
             : multiOperation(mantissa, ops, oppos, oplen);
    }

    snum.setValue(chars, oppos, len, false);

    if (oplen > 0) {
      snum.multiOperation(ops, oppos, oplen);
    }

    if ((snum.bigNumber == null) && (snum.fraction == 0) && (Math.abs(snum.mantissa) < Integer.MAX_VALUE)) {
      return (int) snum.mantissa;
    }

    return Integer.MAX_VALUE;
  }

  /**
   * Returns an integer representation of this number.
   * <p>If the value is larger than <code>Integer.MAX_VALUE</code> then the <code>Integer.MAX_VALUE</code>
   * is returned
   *
   * @return   the number as an integer
   */
  public int intValueMax() {
    if (bigNumber != null) {
      boolean n = bigNumber.isNegative();
      int     v = (int) bigNumber.longValue();

      if (n && (v < 0)) {
        return v;
      }

      if (!n && (v > 0)) {
        return v;
      }

      return Integer.MAX_VALUE;
    }

    return (mantissa > Integer.MAX_VALUE)
           ? Integer.MAX_VALUE
           : (int) mantissa;
  }

  /**
   * Returns an integer representation the specified value. Trailing non-numeric
   * characters are allowed in the string.
   * <p>If the value is larger than <code>Integer.MAX_VALUE</code> then the <code>Integer.MAX_VALUE</code>
   * is returned
   *
   * @param str  the value
   *
   * @return   the string as an integer
   */
  public static int intValueMax(String str) {
    if (str == null) {
      return 0;
    }

    SNumber num = (SNumber) perThreadNumber.get();

    num.setValue(str, false);

    return (num.mantissa > Integer.MAX_VALUE)
           ? Integer.MAX_VALUE
           : (int) num.mantissa;
  }

  /**
   * Returns <code>true</code> if the number is most likely greater outside the
   * 64-bit range
   *
   * @return   <code>true</code> if the number is most likely greater outside the
   *           64-bit range
   */
  public boolean isBigNumber() {
    return bigNumber != null;
  }

  /**
   * Test this number to see if it is an integer value (that is,
   * it does not have a fractional component).
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public final boolean isInteger() {
    if (bigNumber != null) {
      return bigNumber.isInteger();
    }

    if (decplaces == 0) {
      return true;
    }

    return false;
  }

  /**
   * Test this number to see if it has a value of less than zero
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public final boolean isNegative() {
    if (bigNumber != null) {
      return bigNumber.isNegative();
    }

    if ((mantissa < 0) || (fraction < 0)) {
      return true;
    }

    return false;
  }

  /**
   * Test to see if a string contains only valid numeric digits
   *
   * @param str  the string
   *
   * @return   <code>true</code> if the string is numeric; <code>false</code> otherwise
   */
  public static boolean isNumeric(String str) {
    return isNumeric(str, ALLOW_EXPONENT);
  }

  /**
   * Test to see if a string contains only valid numeric digits
   *
   * @param str    the string
   * @param flags  flags that control the operation
   *
   * @return   <code>true</code> if the string is numeric; <code>false</code> otherwise
   */
  public static boolean isNumeric(String str, int flags) {
    if (str == null) {
      return false;
    }

    final CharArray ca = ((CharArray) perThreadBuffer.get()).set(str);

    return isNumeric(ca.A, 0, ca._length, flags);
  }

  /**
   * Test to see if a string contains only valid numeric digits
   *
   * @param chars  the character array
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   * @param flags  flags that control the operation
   *
   * @return   <code>true</code> if the string is numeric; <code>false</code> otherwise
   */
  public static boolean isNumeric(char[] chars, int pos, int len, int flags) {
    if ((chars == null) || (len == 0)) {
      return false;
    }

    boolean aexponent = (flags & ALLOW_EXPONENT) > 0;
    boolean trailinge = (flags & ALLOW_TRAILING_E) > 0;
    boolean zeroany   = (flags & ALLOW_ZEROS) > 0;
    boolean esign     = false;
    boolean decimal   = false;
    boolean e         = false;
    int     state     = 0;
    int     i         = 0;
    char    firstchar = 0;
    char    lastchar  = 0;
    boolean ret       = true;
    char    c         = chars[pos];

    if ((len == 1) && isNumeric(c)) {    // quick test
      return true;
    }

    if (c == MINUS_SIGN) {
      pos++;
      len--;
    }

    if ((flags & ALLOW_OPS) > 0) {
      i = 0;

      while(i < len) {
        if (((c = chars[pos]) == MINUS_SIGN) || (c == PLUS_SIGN) || (c == '\'')) {
          pos++;
          len--;
        }

        i++;
      }
    }

    if (len == 0) {
      return false;
    }

    lastchar = chars[(pos + len) - 1];

    for (i = 0; i < len; i++) {
      c = chars[i + pos];

      if (state == dot) {
        if (!isNumeric(c)) {
          if (isExponentSymbol(c, chars, i + pos, len - i)) {
            if (aexponent) {
              if ((chars[(i + pos) - 1] != DECIMAL_POINT) && (firstchar != 0)) {
                if (chars[(i + pos) - 1] != DECIMAL_POINT) {
                  state = exponent;
                  e     = true;

                  if (EXPONENT_CHARS_LOWERCASE != null) {
                    i += EXPONENT_CHARS_LOWERCASE.length - 1;
                  }

                  continue;
                }
              }
            }
          }

          ret = false;

          break;
        }
      } else if (state == exponent) {
        if (!isNumeric(c)) {
          if (!esign && ((c == PLUS_SIGN) || (c == MINUS_SIGN))) {
            esign = true;

            continue;
          }

          ret = false;

          break;
        }
      } else {
        if (!isNumeric(c)) {
          if (c == DECIMAL_POINT) {
            if (firstchar == 0) {
              firstchar = DECIMAL_POINT;
            }

            state   = dot;
            decimal = true;

            continue;
          }

          if (isExponentSymbol(c, chars, i + pos, len - i)) {
            if (aexponent) {
              if (firstchar != 0) {
                state = exponent;
                e     = true;

                if (EXPONENT_CHARS_LOWERCASE != null) {
                  i += EXPONENT_CHARS_LOWERCASE.length - 1;
                }

                continue;
              }
            }
          }

          ret = false;

          break;
        } else if (firstchar == 0) {
          firstchar = c;

          if ((firstchar == ZERO_DIGIT) &&!zeroany) {
            ret = false;

            break;
          }
        } else if (!zeroany && (i == 1) && (firstchar == ZERO_DIGIT) && (c == ZERO_DIGIT)) {
          ret = false;

          break;
        }
      }
    }

    if (ret) {
      if (!isNumeric(lastchar)) {
        if (!e ||!trailinge ||!isTrailingExponent(lastchar)) {
          ret = false;
        }
      } else if (!zeroany && (lastchar == ZERO_DIGIT) && decimal) {
        ret = false;
      }
    }

    return ret;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final boolean isRational() {
    if (bigNumber != null) {
      return bigNumber.scale() != 0;
    }

    return fraction != 0;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final boolean isRationalOrBigNumber() {
    if (bigNumber != null) {
      return true;
    }

    return fraction != 0;
  }

  /**
   * Test this number to see if it has a value of zero (0)
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public final boolean isZero() {
    if (bigNumber != null) {
      return bigNumber.isZero();
    }

    if ((mantissa == 0) && (fraction == 0)) {
      return true;
    }

    return false;
  }

  /**
   * Returns a <code>long</code> representation of this number
   *
   * @return   the number as a <code>long</code>
   */
  public final long longValue() {
    return (bigNumber != null)
           ? bigNumber.longValue()
           : mantissa;
  }

  /**
   * Returns a <code>long</code> representation the specified value. Trailing
   * non-numeric characters are allowed in the string
   *
   * @param str  the value
   *
   * @return   the string as a <code>long</code>
   */
  public static long longValue(String str) {
    return ((SNumber) perThreadNumber.get()).setValue(str, false).longValue();
  }

  /**
   * Returns a <code>long</code> representation the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   the string as a <code>long</code>
   */
  public static long longValue(String str, boolean javaparsecompat) {
    return ((SNumber) perThreadNumber.get()).setValue(str, javaparsecompat).longValue();
  }

  /**
   *  Returns a <code>long</code> representation the specified value.
   *
   *  @param chars            the character array
   *  @param pos              the starting position within the array
   *  @param len              the number of characters in the array to use
   *  @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                          characters are not supported and will cause <code>NumberFormatException</code>
   *                          exception will be thrown
   *
   *  @return   the string as a <code>long</code>
   *
   */
  public static long longValue(char[] chars, int pos, int len, boolean javaparsecompat) {
    return ((SNumber) perThreadNumber.get()).setValue(chars, pos, len, javaparsecompat).longValue();
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(double num) {
    if (doubleValue() < num) {
      return true;
    }

    return false;
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(int num) {
    return lt((long) num);
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param num  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(long num) {
    if (bigNumber != null) {
      return bigNumber.lt(valueOf(num));
    }

    if (mantissa < num) {
      return true;
    }

    if (mantissa > num) {
      return false;
    }

    if (fraction < 0) {
      return true;
    }

    return false;
  }

  /**
   * Test this number to see if it is less than the specified value
   *
   * @param snum  the value
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean lt(SNumber snum) {
    return doubleValue() < snum.doubleValue();
  }

  /**
   * Makes this SNumber immutable
   * @return this SNumber
   */
  public SNumber makeImmutable() {
    immutable = true;

    return this;
  }

  /**
   * Divides this number by the specified value and returns only the remainder as the
   * result. The sign of result is the same as the sign of this number.
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber mod(SNumber snum) {
    return mod(snum, false);
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
  public SNumber mod(SNumber snum, boolean special) {
    if (immutable) {
      return new SNumber(this).mod(snum, special);
    }

    if ((bigNumber == null) && (snum.bigNumber == null) && (snum.fraction == 0) && (mantissa > -1)
        && (snum.mantissa > -1)) {
      do {
        if (snum.mantissa == 0) {
          throw new ArithmeticException(divideByZero);
        }

        if ((mantissa == 0) || (mantissa == snum.mantissa)) {
          mantissa = 0;

          break;
        }

        if (mantissa > snum.mantissa) {
          mantissa -= (mantissa / snum.mantissa) * snum.mantissa;

          break;
        }
      } while(false);

      return this;
    }

    SDecimal bd2 = (snum.bigNumber == null)
                   ? new SDecimal(snum)
                   : snum.bigNumber;
    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bigNumber = bd1.mod(bd2, special);
    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param ops {@inheritDoc}
   * @param pos {@inheritDoc}
   * @param len {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final SNumber multiOperation(char[] ops, int pos, int len) {
    if (immutable) {
      return new SNumber(mantissa, fraction, decplaces).multiOperation(ops, pos, len);
    }

    int  i = pos + len - 1;
    char c;

    while(i >= pos) {
      c = ops[i--];

      if (c == MINUS_SIGN) {
        if (bigNumber != null) {
          bigNumber = bigNumber.negate();

          return this;
        }

        mantissa *= -1;
        fraction *= -1;
      } else if (c == '\'') {
        if (bigNumber != null) {
          mantissa  = fraction = bigNumber.isZero()
                                 ? 0
                                 : 1;
          bigNumber = null;
        }

        if ((mantissa == 0) && (fraction == 0)) {
          mantissa  = 1;
          fraction  = 0;
          decplaces = 0;
        } else {
          mantissa  = 0;
          fraction  = 0;
          decplaces = 0;
          bigNumber = null;
        }
      } else if (c != PLUS_SIGN) {
        throw new ArithmeticException("The specified operation '" + new String(ops, pos, len)
                                      + "' is Invalid or unsupported");
      }
    }

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber multiply(double num) {
    if (immutable) {
      return new SNumber(this).multiply(num);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.multiply(valueOf(num));

      return this;
    }

    SNumber snum = new SNumber(num);

    return multiply(snum);
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber multiply(int num) {
    return multiply((long) num);
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber multiply(long num) {
    if (immutable) {
      return new SNumber(this).multiply(num);
    }

    if (num == 1) {
      return this;
    }

    if ((num == 0) || ((mantissa == 0) && (fraction == 0))) {
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;
      bigNumber = null;

      return this;
    }

    if (bigNumber != null) {
      multiply(valueOf(num));

      return this;
    }

    if ((mantissa >= Integer.MAX_VALUE) || (mantissa <= Integer.MIN_VALUE) || (fraction >= Integer.MAX_VALUE)
        || (fraction <= Integer.MIN_VALUE)) {
      bigNumber = toSDecimal();
      bigNumber = bigNumber.multiply(valueOf(num));

      return this;
    }

    boolean neg  = false;
    long    mant = mantissa;
    long    frac = (decplaces == 0)
                   ? 0
                   : fraction;

    if (frac < 0) {
      frac *= -1;
      neg  = true;
    }

    if (mant < 0) {
      neg  = true;
      mant *= -1;
    }

    if (num < 0) {
      neg = !neg;
      num *= -1;
    }

    mant     *= num;
    frac     *= num;
    mantissa = mant;
    fraction = frac;

    if (decplaces != 0) {
      num = tenpow(decplaces);

      if (num <= fraction) {
        mantissa += (fraction / num);
        fraction = fraction % num;
      }

      while((fraction > 0) && ((fraction % 10) == 0)) {
        fraction = fraction / 10;
        decplaces--;
      }

      if (decplaces < 0) {
        decplaces = 0;
      }
    }

    if (neg) {
      fraction *= -1;
      mantissa *= -1;
    }

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber multiply(SNumber snum) {
    if (immutable) {
      return new SNumber(this).multiply(snum);
    }

    if ((bigNumber != null) || (snum.bigNumber != null)) {
      if ((snum.bigNumber == null) && (snum.fraction == 0) && (snum.mantissa == 1)) {
        return this;
      }

      if ((bigNumber == null) && (fraction == 0) && (mantissa == 1)) {
        mantissa  = 0;
        fraction  = 0;
        decplaces = 0;
        setValue(snum.bigNumber);

        return this;
      }

      SDecimal bd2 = (snum.bigNumber == null)
                     ? SDecimal.valueOf(snum)
                     : snum.bigNumber;
      SDecimal bd1 = (bigNumber == null)
                     ? new SDecimal(this)
                     : bigNumber;

      bigNumber = bd1.multiply(bd2);
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;

      return this;
    }

    if (snum.decplaces == 0) {
      return (snum.mantissa == 1)
             ? this
             : multiply(snum.mantissa);
    }

    if (decplaces == 0) {
      long m = mantissa;

      mantissa  = snum.mantissa;
      fraction  = snum.fraction;
      decplaces = snum.decplaces;

      return (m == 1)
             ? this
             : multiply(m);
    } else {
      SNumber num = new SNumber(this);

      num.multiply(snum.fraction);
      num.shiftDecimal(-snum.decplaces);
      multiply(snum.mantissa);
      add(num);
    }

    return this;
  }

  /**
   * Multiplies this number with the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber multiply(String str) {
    if (immutable) {
      return new SNumber(this).multiply(str);
    }

    SNumber snum = new SNumber(str);

    return multiply(snum);
  }

  /**
   * Negates this number
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public final SNumber negate() {
    if (immutable) {
      return new SNumber(this).negate();
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.negate();

      return this;
    }

    mantissa *= -1;
    fraction *= -1;

    return this;
  }

  /**
   * Creates a new big sdecimal from the specified string
   *
   * @param s  the string
   *
   * @return   a new big sdecimal
   */
  public static BigDecimal newBigDecimal(String s) {
    return new BigDecimal(s);
  }

  /**
   * Creates a new big integer from the specified string
   *
   * @param s      the string
   * @param radix  the radix
   *
   * @return   a new big integer
   */
  public static BigInteger newBigInteger(String s, int radix) {
    return new BigInteger(s, radix);
  }

  /**
   * Creates a new SDecimal from the specified string
   *
   * @param s  the string
   *
   * @return   a new big sdecimal
   */
  public static SDecimal newSDecimal(String s) {
    return new SDecimal(s);
  }

  /**
   * Performs an <b>NOT</b> operation on this number using the specified value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public final SNumber not() {
    if (immutable) {
      return new SNumber(this).not();
    }

    if (bigNumber != null) {
      mantissa  = fraction = bigNumber.isZero()
                             ? 0
                             : 1;
      bigNumber = null;
    }

    if ((mantissa == 0) && (fraction == 0)) {
      mantissa  = 1;
      fraction  = 0;
      decplaces = 0;
    } else {
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;
      bigNumber = null;
    }

    return this;
  }

  /**
   * Returns an SNumber that is equivalent to the number one (1) divided by this number
   * @return an SNumber that is equivalent to the number one (1) divided by this number
   */
  public SNumber oneOver() {
    if (immutable) {
      return new SNumber(this).oneOver();
    }

    SNumber num = (SNumber) perThreadNumber.get();

    num.setValue(1, 0, 0);
    num.bigNumber = null;
    num           = num.divide(this);
    bigNumber     = num.bigNumber;
    mantissa      = num.mantissa;
    fraction      = num.fraction;
    decplaces     = num.decplaces;

    return this;
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber or(double num) {
    if (immutable) {
      return new SNumber(this).or(num);
    }

    SNumber snum = new SNumber(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber or(int num) {
    if (immutable) {
      return new SNumber(this).or(num);
    }

    SNumber snum = new SNumber(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber or(long num) {
    SNumber snum = new SNumber(num);

    return or(snum);
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber or(SNumber snum) {
    if (immutable) {
      return new SNumber(this).or(snum);
    }

    if (snum.bigNumber != null) {
      bigNumber = toSDecimal();
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.or(snum.toSDecimal());

      return this;
    }

    mantissa  = mantissa | snum.mantissa;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Performs an <b>OR</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber or(String str) {
    if (immutable) {
      return new SNumber(this).or(str);
    }

    SNumber snum = new SNumber(str);

    return or(snum);
  }

  /**
   * Parses a string as a signed sdecimal <code>double</code>. Trailing non-numeric
   * characters are allowed in the string
   *
   * @param str  the string
   *
   * @return   the <code>double</code> represented by the string
   */
  public static double parseDouble(String str) {
    return doubleValue(str);
  }

  /**
   * Creates an double aray from a comma separated list of numbers
   *
   * @param s a comma separated list of numbers
   *
   * @return an double array or null if the string is null
   */
  public static double[] parseDoubles(String s) {
    if (s == null) {
      return null;
    }

    if (s.length() == 0) {
      return new double[0];
    }

    List   list = CharScanner.getTokens(s, ',', true);
    int    len  = list.size();
    double a[]  = new double[len];

    for (int i = 0; i < len; i++) {
      a[i] = doubleValue((String) list.get(i));
    }

    return a;
  }

  /**
   * Creates an float aray from a comma separated list of numbers
   *
   * @param s a comma separated list of numbers
   *
   * @return an float array or null if the string is null
   */
  public static float[] parseFloats(String s) {
    if (s == null) {
      return null;
    }

    if (s.length() == 0) {
      return new float[0];
    }

    List  list = CharScanner.getTokens(s, ',', true);
    int   len  = list.size();
    float a[]  = new float[len];

    for (int i = 0; i < len; i++) {
      a[i] = floatValue((String) list.get(i));
    }

    return a;
  }

  /**
   * Parses a string as a signed sdecimal integer. Trailing non-numeric characters are
   * allowed in the string
   *
   * @param str  the string
   *
   * @return   a integer representing the string
   */
  public static int parseInt(String str) {
    return (int) longValue(str);
  }

  /**
   * Creates an integer aray from a comma separated list of numbers
   *
   * @param s a comma separated list of numbers
   *
   * @return an integer array or null if the string is null
   */
  public static int[] parseIntegers(String s) {
    if (s == null) {
      return null;
    }

    if (s.length() == 0) {
      return new int[0];
    }

    List list = CharScanner.getTokens(s, ',', true);
    int  len  = list.size();
    int  a[]  = new int[len];

    for (int i = 0; i < len; i++) {
      a[i] = intValue((String) list.get(i));
    }

    return a;
  }

  /**
   * Raises this number by the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber pow(SDecimal snum) {
    if (immutable) {
      return new SNumber(this).pow(snum);
    }

    if (snum.isZero()) {
      mantissa  = 1;
      fraction  = 0;
      decplaces = 0;
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.pow(snum);
    fromSDecimal(bd1);

    return this;
  }

  /**
   * Raises this number by the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber pow(SNumber snum) {
    if (immutable) {
      return new SNumber(this).pow(snum);
    }

    if (snum.isZero()) {
      if (isZero()) {
        throw new ArithmeticException("Illegal Value");
      }

      mantissa  = 1;
      fraction  = 0;
      decplaces = 0;

      return this;
    }

    if ((snum.fractionalPart() != 0) || snum.isNegative()) {
      double d  = doubleValue();
      double d2 = snum.doubleValue();

      if (d2 == 0) {
        if (d == 0) {
          throw new ArithmeticException("Illegal Value");
        }

        mantissa  = 1;
        fraction  = 0;
        decplaces = 0;
      } else {
        d = StrictMath.pow(d, d2);
        setValue(d);
      }

      return this;
    }

    if ((snum.fraction != 0) && (snum.mantissa == 0)) {
      if (isNegative() || ((snum.fraction < 0) && isZero())) {
        throw new ArithmeticException("Illegal Value");
      }
    }

    SDecimal bd2 = (snum.bigNumber == null)
                   ? new SDecimal(snum)
                   : snum.bigNumber;

    if (bd2.scale() > 0) {
      throw new ArithmeticException(NO_POWER_FRACTION_MSG);
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bigNumber = bd1.pow(bd2);
    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Reads a <code>SNumber</code> value from an input stream
   *
   * @param in  the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static SNumber readSNumber(InputStream in) throws IOException {
    long m = 0;
    long f = 0;
    int  d = 0;

    m = aStreamer.readLong(in);
    f = aStreamer.readLong(in);
    d = aStreamer.readInt(in);

    return new SNumber(m, f, d);
  }

  /**
   * Reads a <code>SNumber</code> value from an input stream
   *
   * @param use  the <code>SNumber</code> to use to store the results
   * @param in   the input stream
   *
   * @throws  IOException if and I/O error occurs
   */
  public static void readSNumber(SNumber use, InputStream in) throws IOException {
    long m = 0;
    long f = 0;
    int  d = 0;

    m = aStreamer.readLong(in);
    f = aStreamer.readLong(in);
    d = aStreamer.readInt(in);
    use.setValue(m, f, d);
  }

  /**
   * Reads a <code>SNumber</code> array from an input stream
   *
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static SNumber[] readSNumberArray(InputStream in) throws IOException {
    int len = aStreamer.readVarLength(in);

    if (len == -1) {
      return null;
    }

    SNumber[] a = new SNumber[len];

    for (int i = 0; i < len; i++) {
      a[i] = readSNumber(in);
    }

    return a;
  }

  /**
   * Rounds the number to the specified number of sdecimal places
   *
   * @param places  the number of sdecimal places
   * @param up      <code>true</code> to round up; <code>false</code> to round down
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber round(int places, boolean up) {
    if (immutable) {
      return new SNumber(this).round(places, up);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.round(places, up);

      return this;
    }

    if (places == 0) {
      if (up && (fraction != 0)) {
        long num  = tenpow(decplaces - places);
        long test = fraction % num;

        test *= ((test < 0)
                 ? (-1)
                 : 1);

        if (up && (test > ((num - 1) / 2))) {
          mantissa += ((fraction < 0)
                       ? (-1)
                       : 1);
        }
      }

      fraction  = 0;
      decplaces = 0;
    } else if (places < decplaces) {
      long num  = tenpow(decplaces - places);
      long frac = fraction / num;
      long test = fraction % num;

      test *= ((test < 0)
               ? (-1)
               : 1);

      if (up && (test > ((num - 1) / 2))) {
        frac += ((fraction < 0)
                 ? (-1)
                 : 1);
      }

      fraction  = frac;
      decplaces = places;
      frac      *= ((frac < 0)
                    ? (-1)
                    : 1);
      num       = tenpow(places);

      if (frac >= num) {
        mantissa  += ((fraction < 0)
                      ? (-1)
                      : 1);
        decplaces = 0;
        fraction  = 0;
      }
    }

    return this;
  }

  /**
   * Returns an <code>int</code> representing the number of sdecimal places of the
   * fractional part of this number
   *
   * @return   the number of sdecimal places
   */
  public final int scale() {
    if (bigNumber != null) {
      return bigNumber.scale();
    }

    return decplaces;
  }

  /**
   * Sets the number of decimal places of the fractional part of this number
   *
   * @param places  the number of places
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setScale(int places) {
    if (immutable) {
      return new SNumber(this).setScale(places);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.moveDecimalPoint(places);
    } else if ((places < -18) || (places > 18)) {
      bigNumber = toSDecimal().moveDecimalPoint(places);
    } else {
      decplaces = places;

      if (places == 0) {
        fraction = 0;
      }
    }

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(BigDecimal num) {
    if (immutable) {
      return new SNumber(num);
    }

    if (bigNumber == null) {
      bigNumber = new SDecimal(num);
    } else {
      bigNumber = bigNumber.setValue(num);
    }

    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(BigInteger num) {
    if (immutable) {
      return new SNumber(num);
    }

    if (bigNumber == null) {
      bigNumber = new SDecimal(num);
    } else {
      bigNumber = bigNumber.setValue(num);
    }

    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(double num) {
    return setValue(num, maxDigits);
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @param maxDecPlaces the maximum number of decimals places to preserve;
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(double num, int maxDecPlaces) {
    if (immutable) {
      return new SNumber(num);
    }

    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    if ((num < Long.MIN_VALUE) || (num > Long.MAX_VALUE)) {
      if (bigNumber == null) {
        bigNumber = new SDecimal(num);
      } else {
        bigNumber = bigNumber.setValue(num);
      }
    } else {
      bigNumber = null;
      mantissa  = (long) num;

      if ((mantissa > 0) && (mantissa > num)) {
        mantissa--;
      } else if ((mantissa < 0) && (mantissa < num)) {
        mantissa++;
      }

      num       = num % 1;
      decplaces = 0;

      while(((num % 1) != 0) && (decplaces < 16)) {
        if (decplaces == maxDecPlaces) {
          break;
        }

        num *= 10;
        decplaces++;
      }

      fraction = (long) num;

      if (fraction > num) {
        fraction--;
      }
    }

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(int num) {
    if (immutable) {
      return new SNumber(num);
    }

    bigNumber = null;
    mantissa  = num;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(long num) {
    if (immutable) {
      return new SNumber(num);
    }

    bigNumber = null;
    mantissa  = num;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(SNumber num) {
    if (immutable) {
      return new SNumber(num);
    }

    bigNumber = (num.bigNumber == null)
                ? null
                : new SDecimal(num.bigNumber);
    mantissa  = num.mantissa;
    fraction  = num.fraction;
    decplaces = num.decplaces;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(String str) {
    setValue(str, false);

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  public SNumber setValue(String str, boolean javaparsecompat) {
    if (!setValueEx(str, javaparsecompat, false)) {
      throw new NumberFormatException();
    }

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param chars  the character array containing the value
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(char[] chars, int pos, int len) {
    if (immutable) {
      return new SNumber(chars, pos, len);
    }

    setValueEx(chars, pos, len, false, false);

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param mantissa   the mantissa
   * @param fraction   the fraction
   * @param decplaces  the number of sdecimal places
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber setValue(long mantissa, long fraction, int decplaces) {
    if (immutable) {
      return new SNumber(mantissa, fraction, decplaces);
    }

    bigNumber      = null;
    this.mantissa  = mantissa;
    this.fraction  = fraction;
    this.decplaces = decplaces;

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param chars            the character array containing the value
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   *
   * @throws   NumberFormatException if <code>javaparsecompat</code> is <code>true</code> and the string
   *           contains invalid characters
   */
  public SNumber setValue(char[] chars, int pos, int len, boolean javaparsecompat) throws NumberFormatException {
    if (immutable) {
      return new SNumber(chars, pos, len, javaparsecompat);
    }

    if (!setValueEx(chars, pos, len, javaparsecompat, false)) {
      throw new NumberFormatException();
    }

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param str              the value
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause the method to return <code>false</code>
   * @param trailinge        <code>true</code> specifies strings containing s single trailing 'e' (or
   *                         'Object') are supported; <code>false</code> otherwise
   *
   * @return   <code>true</code> if the characters represented a valid number; <code>false</code>
   *           otherwise
   */
  public boolean setValueEx(String str, boolean javaparsecompat, boolean trailinge) {
    if (immutable) {
      throw new IllegalStateException("immutable object");
    }

    if (str == null) {
      bigNumber = null;
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;

      return true;
    }

    CharArray ca = strBuffer;

    if (ca == null) {
      ca = (CharArray) perThreadBuffer.get();
    }

    ca.set(str);

    if (ca._length == 0) {
      if (javaparsecompat) {
        return false;
      }

      bigNumber = null;
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;

      return true;
    }

    return setValueEx(ca.A, 0, ca._length, javaparsecompat, trailinge);
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param chars            the character array containing the value
   * @param pos              the starting position within the array
   * @param len              the number of characters in the array to use
   * @param javaparsecompat  <code>true</code> specifies strings containing trailing non-numeric
   *                         characters are not supported and will cause <code>NumberFormatException</code>
   *                         exception will be thrown
   *
   * @param trailinge        <code>true</code> specifies strings containing s single trailing 'e' (or
   *                         'Object') are supported; <code>false</code> otherwise
   *
   * @return   <code>true</code> if the characters represented a valid number; <code>false</code>
   *           otherwise
   *
   */
  public boolean setValueEx(char[] chars, int pos, int len, boolean javaparsecompat, boolean trailinge) {
    if (immutable) {
      throw new IllegalStateException("Immutable object");
    }

    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;
    bigNumber = null;

    if (len == 0) {
      return javaparsecompat
             ? false
             : true;
    }

    if (len == 1) {
      mantissa = Character.getNumericValue(chars[pos]);

      if ((mantissa < 0) || (mantissa > 9)) {
        mantissa = 0;

        return javaparsecompat
               ? false
               : true;
      }

      return true;
    }

    if ((len == 3) && (chars[pos] == DECIMAL_POINT) && (chars[pos + 1] == ZERO_DIGIT) && (chars[pos + 2] == '1')) {
      fraction  = 1;
      decplaces = 2;

      return true;
    }

    if ((chars[pos] == '0') && (chars[pos + 1] == 'x')) {
      try {
        mantissa = Long.parseLong(new String(chars, pos + 2, len - 2), 16);

        return true;
      } catch(NumberFormatException e) {
        return false;
      }
    }

    boolean neg     = false;
    int     opos    = pos;
    int     olen    = len;
    int     n       = pos + len;
    int     exp     = 0;
    char    expchar = PLUS_SIGN;
    char    c       = 0;
    int     d;

    // remove signs and leading zeros
    while(pos < n) {
      c = chars[pos];

      if ((c == MINUS_SIGN) || (c == PLUS_SIGN)) {
        if (c == MINUS_SIGN) {
          neg = !neg;
        }

        pos++;
      } else if (c == ZERO_DIGIT) {
        pos++;
      } else {
        break;
      }
    }

    // get mantissa
    while(pos < n) {
      c = chars[pos++];
      d = Character.getNumericValue(c);

      if ((d < 0) || (d > 9)) {
        break;
      }

      mantissa *= 10;
      mantissa += d;

      if (mantissa < 0) {    // we  exceeded our bounds and wrapped around
        return setBNValueEx(chars, opos, olen, javaparsecompat);
      }
    }

    if (c == DECIMAL_POINT) {
      // get fraction
      while(pos < n) {
        c = chars[pos++];
        d = Character.getNumericValue(c);

        if ((d < 0) || (d > 9)) {
          break;
        }

        fraction *= 10;
        fraction += d;
        decplaces++;

        if ((fraction < 0) || (decplaces == maxDigits)) {
          return setBNValueEx(chars, opos, olen, javaparsecompat);
        }
      }
    }

    if (isExponentSymbol(c, chars, pos - 1, n - pos)) {
      if (EXPONENT_CHARS_LOWERCASE != null) {
        pos += EXPONENT_CHARS_LOWERCASE.length - 1;
      }

      // get exponents
      if (pos == n) {
        if (!trailinge && javaparsecompat) {
          return false;
        }
      } else {
        c = chars[pos++];

        if ((c == PLUS_SIGN) || (c == MINUS_SIGN)) {
          expchar = c;
        } else {
          pos--;
        }

        while(pos < n) {
          c = chars[pos++];
          d = Character.getNumericValue(c);

          if ((d < 0) || (d > 9)) {
            break;
          }

          exp *= 10;
          exp += d;

          if (exp < 0) {
            return setBNValueEx(chars, opos, olen, javaparsecompat);
          }
        }
      }
    }

    if ((c != DECIMAL_POINT) &&!isNumeric(c)) {
      if (javaparsecompat) {
        return false;
      }
    }

    while((decplaces > 0) && ((fraction % 10) == 0)) {
      fraction /= 10;
      decplaces--;
    }

    if (neg) {
      mantissa *= -1;
      fraction *= -1;
    }

    if (exp > 0) {
      exp *= ((expchar == MINUS_SIGN)
              ? (-1)
              : 1);
      shiftDecimal(exp);
    }

    if (fraction == 0) {
      decplaces = 0;
    }

    return true;
  }

  /**
   * Shifts the sdecimal point the specified number of places. A negative value shifts
   * to the left, a positive to the right.
   *
   * @param places  the number of places to shift
   *
   * @return   his <code>SNumber</code> object representing the result of the operation
   */
  public SNumber shiftDecimal(int places) {
    if (immutable) {
      return new SNumber(this).shiftDecimal(places);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.moveDecimalPoint(places);

      return this;
    }

    if ((places == 0) || ((fraction == 0) && (mantissa == 0))) {
      return this;
    }

    if (((places < 0) && ((decplaces - places) >= maxDigits))
        || ((places > 0) && ((decplaces + places) >= maxDigits))) {
      bigNumber = toSDecimal();
      bigNumber = bigNumber.moveDecimalPoint(places);
      mantissa  = 0;
      fraction  = 0;
      decplaces = 0;

      return this;
    }

    if (places > 0) {
      return multiply(tenpow(places));
    }

    long n;
    long num = tenpow(decplaces);

    while(places < 0) {
      n        = mantissa % 10;
      n        *= num;
      fraction += n;
      mantissa /= 10;
      num      *= 10;
      decplaces++;
      places++;
    }

    while((decplaces > 0) && ((fraction % 10) == 0)) {
      fraction /= 10;
      decplaces--;
    }

    if (fraction == 0) {
      decplaces = 0;
    }

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber subtract(double num) {
    if (immutable) {
      return new SNumber(this).subtract(num);
    }

    SNumber snum = new SNumber(num);

    return subtract(snum);
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber subtract(int num) {
    return subtract((long) num);
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber subtract(long num) {
    if (immutable) {
      return new SNumber(this).subtract(num);
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.subtract(valueOf(num));

      return this;
    }

    mantissa -= num;

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber subtract(SNumber snum) {
    if (immutable) {
      return new SNumber(this).subtract(snum);
    }

    if ((bigNumber != null) || (snum.bigNumber != null)) {
      SDecimal bd2 = (snum.bigNumber == null)
                     ? SDecimal.valueOf(snum)
                     : snum.bigNumber;
      SDecimal bd1 = (bigNumber == null)
                     ? new SDecimal(this)
                     : bigNumber;

      bd1 = bd1.subtract(bd2);
      fromSDecimal(bd1);

      return this;
    }

    SNumber snum2 = snum.negate();

    add(snum2);

    if (snum2 == snum) {
      snum.negate();
    }

    return this;
  }

  /**
   * Subtracts the specified value from this number
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber subtract(String str) {
    if (immutable) {
      return new SNumber(this).subtract(str);
    }

    SNumber snum = new SNumber(str);

    return subtract(snum);
  }

  /**
   * Returs a long represent the number ten raised to the specified power
   *
   * @param p  the power
   *
   * @return   a long represent the number ten raised to the specified power
   */
  public static long tenpow(long p) {
    if (p < tenlen) {
      return tens[(int) p];
    }

    int i = 0;

    p--;

    int  num = 10;
    long l   = num;

    while(i < p) {
      l *= num;
      i++;
    }

    return (p < 0)
           ? 1
           : l;
  }

  /**
   * Returns the string representation of this number
   *
   * @param format  the format
   * @param places  the number of sdecimal places
   *
   * @return   a string representing the number
   */
  public String toFormattedString(String format, int places) {
    if (bigNumber != null) {
      return bigNumber.toFormattedString(format, places);
    }

    char[]  chars = new char[(maxDigits * 2) + 20];
    int     mpos  = chars.length / 2;
    int     pos   = mpos;
    int     dpos  = mpos;
    int     len   = 0;
    int     i;
    long    mant = mantissa;
    long    frac = fraction;
    long    num;
    long    n;
    boolean neg    = false;
    boolean comma  = format.indexOf(',') != -1;
    boolean paren  = format.indexOf('P') != -1;
    boolean trail  = format.indexOf('T') != -1;
    int     comman = 0;

    if (places > 0) {
      n = mpos + 2 + places;
      n = (n >= (chars.length - 2))
          ? (chars.length - 3)
          : n;

      for (i = mpos + 1; i < n; i++) {
        chars[i] = ZERO_DIGIT;
      }

      places = (int) (n - (mpos + 2));
    }

    if ((mant == 0) && (frac == 0)) {
      chars[--pos] = ZERO_DIGIT;
      len++;
    } else {
      if ((mant < 0) || (frac < 0)) {
        neg  = true;
        mant = (mant < 0)
               ? (-mant)
               : mant;
        frac = (frac < 0)
               ? (-frac)
               : frac;
      }

      i = 0;

      while((mant > 0) && (i < maxDigits)) {
        n            = mant % 10;
        chars[--pos] = (char) (ZERO_DIGIT + n);
        len++;
        i++;
        mant -= n;
        mant = mant / 10;

        if (comma) {
          comman++;

          if (comman == 3) {
            chars[--pos] = ',';
            comman       = 0;
            len++;
          }
        }
      }

      if (mantissa == 0) {
        if (places > -1) {
          chars[--pos] = ZERO_DIGIT;
        }
      } else if (chars[pos] == ',') {
        pos++;
        len--;
      }

      if ((frac != 0) && (places != 0)) {
        chars[dpos++] = DECIMAL_POINT;
        len++;
        num = tenpow(decplaces - 1);

        while((num > 0) && (dpos < chars.length)) {
          n             = (int) ((frac / num) % 10);
          chars[dpos++] = (char) (n + ZERO_DIGIT);
          len++;
          i++;

          if (num < 2) {
            break;
          }

          num = num / 10;
        }

        for (i = dpos; i < chars.length; i++) {
          chars[i] = ZERO_DIGIT;
        }

        while((dpos > 20) && (chars[--dpos] == ZERO_DIGIT)) {
          len--;
        }
      }
    }

    if ((fraction != 0) && (places > -1)) {
      len = mpos - pos + places;

      if (places > 0) {
        len++;
      }
    } else if ((fraction == 0) && (places > 0)) {
      len           += (places + 1);
      chars[dpos++] = DECIMAL_POINT;
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
        chars[pos + len] = ((format.indexOf(MINUS_SIGN) == -1) && ((mantissa != 0) || (fraction != 0)))
                           ? MINUS_SIGN
                           : ' ';
        len++;
      } else {
        chars[pos + len] = ((format.indexOf(PLUS_SIGN) != -1) && ((mantissa != 0) || (fraction != 0)))
                           ? PLUS_SIGN
                           : ' ';
        len++;
      }
    } else {
      if (neg) {
        if ((format.indexOf(MINUS_SIGN) == -1) && ((mantissa != 0) || (fraction != 0))) {
          chars[--pos] = MINUS_SIGN;
          len++;
        }
      } else {
        if ((format.indexOf(PLUS_SIGN) != -1) && ((mantissa != 0) || (fraction != 0))) {
          chars[--pos] = PLUS_SIGN;
          len++;
        }
      }
    }

    return new String(chars, pos, len);
  }

  /**
   * Outputs this <code>SNumber</code> value to a stream
   *
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public void toStream(OutputStream out) throws IOException {
    aStreamer.toStream(longValue(), out);
    aStreamer.toStream(fractionalPart(), out);
    aStreamer.toStream(decimalPlaces(), out);
  }

  // toString duplicated to squeeze out every ounce of perfromance

  /**
   * Returns the string representation of this number
   *
   * @return   a string representing the number
   */
  public String toString() {
    if (bigNumber != null) {
      return bigNumber.toString();
    }

    if (fraction == 0) {
      return StringCache.valueOf(mantissa);
    }

    if ((mantissa == 0) && (fraction == 1)) {
      if (decplaces == 1) {
        return ".1";
      }

      if (decplaces == 2) {
        return ".01";
      }

      if (decplaces == 3) {
        return ".001";
      }

      if (decplaces == 4) {
        return ".0001";
      }
    }

    CharArray out = strBuffer;

    if (out == null) {
      out = (CharArray) perThreadBuffer.get();
    }

    int msize = 0;

    if ((mantissa != 0) || (fraction == 0)) {
      msize = (mantissa < 0)
              ? stringSize(-mantissa) + 1
              : stringSize(mantissa);
    }

    int  fsize = 0;
    long frac  = (fraction < 0)
                 ? -fraction
                 : fraction;
    int  cl    = 0;

    if (fraction != 0) {
      fsize = stringSize(fraction);
      cl    = decplaces - fsize;
    }

    out.ensureCapacity(fsize + msize + cl + 1);

    if (msize > 0) {
      getChars(mantissa, msize, out.A);
    }

    if (fsize > 0) {
      if ((mantissa == 0) && (fraction < 0)) {
        out.A[msize++] = MINUS_SIGN;
      }

      out.A[msize] = DECIMAL_POINT;

      if (cl > 0) {
        int i = 0;

        while(i < cl) {
          out.A[++msize] = ZERO_DIGIT;
          i++;
        }
      }

      out._length = fsize + msize + 1;
      getChars(frac, out._length, out.A);

      if (cl < 0) {
        out._length += cl;
      }
    } else {
      out._length = msize;
    }

    return out.toString();
  }

  /**
   * Returns the string representation of the specified boolean
   *
   * @param val  the value
   *
   * @return   a string representing the boolean
   */
  public static String toString(boolean val) {
    return val
           ? "true"
           : "false";
  }

  /**
   * Returns the string representation of this number
   *
   * @param out  the output array
   *
   * @return   the outpur array
   */
  public CharArray toString(CharArray out) {
    if (bigNumber != null) {
      return bigNumber.toString(out, false);
    }

    int msize = 0;

    if ((mantissa != 0) || (fraction == 0)) {
      msize = (mantissa < 0)
              ? stringSize(-mantissa) + 1
              : stringSize(mantissa);
    }

    int  fsize = 0;
    long frac  = (fraction < 0)
                 ? -fraction
                 : fraction;
    int  cl    = 0;

    if (fraction != 0) {
      fsize = stringSize(fraction);
      cl    = decplaces - fsize;
    }

    out.ensureCapacity(fsize + msize + cl + 1);

    if (msize > 0) {
      getChars(mantissa, msize, out.A);
    }

    if (fsize > 0) {
      if ((mantissa == 0) && (fraction < 0)) {
        out.A[msize++] = MINUS_SIGN;
      }

      out.A[msize] = DECIMAL_POINT;

      if (cl > 0) {
        int i = 0;

        while(i < cl) {
          out.A[++msize] = ZERO_DIGIT;
          i++;
        }
      }

      out._length = fsize + msize + 1;
      getChars(frac, out._length, out.A);

      if (cl < 0) {
        out._length += cl;
      }
    } else {
      out._length = msize;
    }

    return out;
  }

  /**
   * Returns the string representation of the specified number
   *
   * @param num  the value
   *
   * @return   a string representing the number
   */
  public static String toString(double num) {
    SNumber snum = (SNumber) perThreadNumber.get();

    snum.setValue(num);

    return snum.toString();
  }

  /**
   * Returns the string representation of the specified number
   *
   * @param num  the value
   *
   * @return   a string representing the number
   */
  public static String toString(int num) {
    return StringCache.valueOf(num);
  }

  /**
   * Returns the string representation of the specified number
   *
   * @param num  the value
   *
   * @return   a string representing the number
   */
  public static String toString(long num) {
    return StringCache.valueOf(num);
  }

  /**
   * Returns the string representation of this number
   *
   * @param out     the output array
   * @param append  boolean <code>true</code> to append to the output; <code>false</code>
   *                otherwise
   *
   * @return   the output array
   */
  public CharArray toString(CharArray out, boolean append) {
    if (bigNumber != null) {
      return bigNumber.toString(out, append);
    }

    if (!append) {
      out._length = 0;
    }

    int msize = 0;

    if ((mantissa != 0) || (fraction == 0)) {
      msize = (mantissa < 0)
              ? stringSize(-mantissa) + 1
              : stringSize(mantissa);
    }

    int  fsize = 0;
    int  cl    = 0;
    long frac  = (fraction < 0)
                 ? -fraction
                 : fraction;

    if (fraction != 0) {
      fsize = stringSize(fraction);
      cl    = decplaces - fsize;
    }

    int len = out._length;

    msize += len;
    out.ensureCapacity(msize + fsize + cl + 1);

    if (msize != len) {
      getChars(mantissa, msize, out.A);
    }

    if (fsize > 0) {
      if ((mantissa == 0) && (fraction < 0)) {
        out.A[msize++] = MINUS_SIGN;
      }

      out.A[msize] = DECIMAL_POINT;

      if (cl > 0) {
        int i = 0;

        while(i < cl) {
          out.A[++msize] = ZERO_DIGIT;
          i++;
        }
      }

      out._length = fsize + msize + 1;
      getChars(frac, out._length, out.A);

      if (cl < 0) {
        out._length += cl;
      }
    } else {
      out._length = msize;
    }

    return out;
  }

  /**
   * Returns the string representation of this number
   *
   * @param tmp  the array to use for temporary storage
   *
   * @return   a string representing the number
   */
  public String toStringEx(CharArray tmp) {
    if (bigNumber != null) {
      return bigNumber.toString(tmp).toString();
    }

    if (fraction == 0) {
      return StringCache.valueOf(mantissa);
    }

    if ((mantissa == 0) && (fraction == 1)) {
      if (decplaces == 1) {
        return POINT_ONE;
      }

      if (decplaces == 2) {
        return POINT_ZERO_ONE;
      }

      if (decplaces == 3) {
        return POINT_ZERO_ZERO_ONE;
      }

      if (decplaces == 4) {
        return POINT_ZERO_ZERO_ZERO_ONE;
      }
    }

    int msize = 0;

    if ((mantissa != 0) || (fraction == 0)) {
      msize = (mantissa < 0)
              ? stringSize(-mantissa) + 1
              : stringSize(mantissa);
    }

    int  fsize = 0;
    long frac  = (fraction < 0)
                 ? -fraction
                 : fraction;
    int  cl    = 0;

    if (fraction != 0) {
      fsize = stringSize(fraction);
      cl    = decplaces - fsize;
    }

    tmp.ensureCapacity(fsize + msize + cl + 1);

    char[] A = tmp.A;

    if (msize > 0) {
      getChars(mantissa, msize, A);
    }

    if (fsize > 0) {
      if ((mantissa == 0) && (fraction < 0)) {
        A[msize++] = MINUS_SIGN;
      }

      A[msize] = DECIMAL_POINT;

      if (cl > 0) {
        int i = 0;

        while(i < cl) {
          A[++msize] = ZERO_DIGIT;
          i++;
        }
      }

      msize = fsize + msize + 1;
      getChars(frac, msize, A);

      if (cl < 0) {
        msize += cl;
      }
    }

    return new String(A, 0, msize);
  }

  /**
   * Specialized version of toUnsignedString()
   *
   * @param i      a <code>long </code>to be converted to a string.
   * @param buf  the output array. must be a minimum of 65 characters)
   * @param pos    the starting position within the array. The string is from that position
   *               to the 64th character of the array
   * @return the number of characters generated
   *
   * @see #toUnsignedStringEx
   */
  public static int toUnsignedString(long i, char[] buf, int pos) {
    if (i < 0) {
      i = -i;
    }

    int len = stringSize(i);

    getChars(i, pos + len, buf);

    return len;
  }

  /**
   * Returns a string representation of the first argument in the radix specified by
   * the second argument.
   *
   * @param i      a <code>long </code>to be converted to a string.
   * @param radix  the radix to use in the string representation.
   * @param ca     the output array
   * @param pos    the starting position in the output array
   *
   * @return   the length of the string.
   */
  public static int toUnsignedString(long i, int radix, CharArray ca, int pos) {
    if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX)) {
      radix = 10;
    }

    ca.setLength(pos + 65);

    char[] buf     = ca.A;
    int    charPos = pos + 64;

    if (i > 0) {
      i = -i;
    }

    while(i <= -radix) {
      buf[charPos--] = digits[(int) (-(i % radix))];
      i              = i / radix;
    }

    buf[charPos] = digits[(int) (-i)];
    ca.remove(pos, charPos);

    return (ca._length - pos);
  }

  /**
   * Specialized version of toUnsignedString()
   *
   * @param i      a <code>long </code>to be converted to a string.
   * @param radix  the radix to use in the string representation.
   * @param buf   the output array. must be a minimum of 65 characters)
   * @return the number of characters generated
   *
   * @see #toUnsignedString
   */
  public static int toUnsignedStringEx(long i, int radix, char[] buf) {
    if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX)) {
      radix = 10;
    }

    int charPos = 64;

    if (i > 0) {
      i = -i;
    }

    while(i <= -radix) {
      buf[charPos--] = digits[(int) (-(i % radix))];
      i              = i / radix;
    }

    buf[charPos] = digits[(int) (-i)];

    return charPos;
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber xor(int num) {
    if (immutable) {
      return new SNumber(this).xor(num);
    }

    SNumber snum = new SNumber(num);

    return xor(snum);
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber xor(long num) {
    if (immutable) {
      return new SNumber(this).xor(num);
    }

    SNumber snum = new SNumber(num);

    return xor(snum);
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param snum  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber xor(SNumber snum) {
    if (immutable) {
      return new SNumber(this).xor(snum);
    }

    if (snum.bigNumber != null) {
      bigNumber = toSDecimal();
    }

    if (bigNumber != null) {
      bigNumber = bigNumber.xor(snum.toSDecimal());

      return this;
    }

    mantissa  = mantissa ^ snum.mantissa;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Performs an <b>XOR</b> operation on this number using the specified value
   *
   * @param str  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public SNumber xor(String str) {
    if (immutable) {
      return new SNumber(this).xor(str);
    }

    SNumber snum = new SNumber(str);

    return xor(snum);
  }

  /**
   * Zeros this number
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  public final SNumber zero() {
    if (immutable) {
      return new SNumber(this).zero();
    }

    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;
    bigNumber = null;

    return this;
  }

  /**
   * Places characters representing the integer i into the character array buf.
   * The characters are placed into the buffer backwards starting with the least
   * significant digit at the specified index (exclusive), and working backwards
   * from there. Will fail if i == Long.MIN_VALUE
   *
   * @param i long
   * @param index int
   * @param buf char[]
   */
  static void getChars(long i, int index, char[] buf) {
    long q;
    int  r;
    int  charPos = index;
    char sign    = 0;

    if (i < 0) {
      sign = MINUS_SIGN;
      i    = -i;
    }

    // Get 2 digits/iteration using longs until quotient fits into an int
    while(i > Integer.MAX_VALUE) {
      q = i / 100;
      // really: r = i - (q * 100);
      r              = (int) (i - ((q << 6) + (q << 5) + (q << 2)));
      i              = q;
      buf[--charPos] = DigitOnes[r];
      buf[--charPos] = DigitTens[r];
    }

    // Get 2 digits/iteration using ints
    int q2;
    int i2 = (int) i;

    while(i2 >= 65536) {
      q2 = i2 / 100;
      // really: r = i2 - (q * 100);
      r              = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
      i2             = q2;
      buf[--charPos] = DigitOnes[r];
      buf[--charPos] = DigitTens[r];
    }

    // Fall thru to fast mode for smaller numbers
    // assert(i2 <= 65536, i2);
    for (;;) {
      q2             = (i2 * 52429) >>> (16 + 3);
      r              = i2 - ((q2 << 3) + (q2 << 1));    // r = i2-(q2*10) ...
      buf[--charPos] = digits[r];
      i2             = q2;

      if (i2 == 0) {
        break;
      }
    }

    if (sign != 0) {
      buf[--charPos] = sign;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param mantissa {@inheritDoc}
   * @param fraction {@inheritDoc}
   * @param decplaces {@inheritDoc}
   * @param num {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  static long multiply(long mantissa, long fraction, int decplaces, long num) {
    if (decplaces == 0) {
      mantissa *= num;
    } else {
      mantissa *= num;
      fraction *= num;

      boolean neg = false;

      if (fraction < 0) {
        fraction *= -1;
        neg      = true;
      }

      if (mantissa < 0) {
        neg      = true;
        mantissa *= -1;
      }

      num = tenpow(decplaces);

      if (num <= fraction) {
        mantissa += (fraction / num);
      }

      if (neg) {
        mantissa *= -1;
      }
    }

    return mantissa;
  }

  /**
   * {@inheritDoc}
   *
   * @param p {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  static double negtenpow(long p) {
    if (p < 1) {
      return 0;
    }

    if (p < tenlen) {
      return fractens[(int) p];
    }

    return StrictMath.pow(10, -p);
  }

//Requires positive x

  /**
   * {@inheritDoc}
   *
   * @param x {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  final static int stringSize(long x) {
    long p = 10;
    int  i = 1;

    if (x < 0) {
      x = -x;
    }

    while(i < maxDigits) {
      if (x < p) {
        return i;
      }

      p = 10 * p;
      i++;
    }

    return maxDigits;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  protected SNumber add(SDecimal num) {
    if (immutable) {
      return new SNumber(this).add(num);
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.add(num);
    fromSDecimal(bd1);

    return this;
  }

  /**
   *  Divides this number by the specified value truncating any fractional protion
   *
   *  @param snum  the value
   *
   *  @return   this <code>SNumber</code> object representing the result of the operation
   *
   */
  protected SNumber divideInteger(SDecimal snum) {
    if (immutable) {
      return new SNumber(this).divideInteger(snum);
    }

    if (snum.isZero()) {
      throw new ArithmeticException(divideByZero);
    }

    if (bigNumber != null) {
      if (bigNumber.isZero()) {
        return this;
      }
    } else {
      if ((mantissa == 0) && (fraction == 0)) {
        return this;
      }
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.divideInteger(snum);
    fromSDecimal(bd1);

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
  protected SNumber mod(SDecimal snum, boolean special) {
    if (immutable) {
      return new SNumber(this).mod(snum, special);
    }

    if (snum.isZero()) {
      throw new ArithmeticException(divideByZero);
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.mod(snum, special);
    fromSDecimal(bd1);

    return this;
  }

  /**
   *   Multiplies this number with the specified value
   *
   *   @param num  the value
   *
   *   @return   this <code>SNumber</code> object representing the result of the operation
   */
  protected SNumber multiply(SDecimal num) {
    if (immutable) {
      return new SNumber(this).multiply(num);
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.multiply(num);
    fromSDecimal(bd1);

    return this;
  }

  /**
   * Sets the <code>SNumber</code> to the specified value
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  protected SNumber setValue(SDecimal num) {
    if (immutable) {
      return new SNumber(0).setValue(num);
    }

    bigNumber = new SDecimal(num);
    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;

    return this;
  }

  /**
   * Adds a value to this number
   *
   * @param num  the value
   *
   * @return   this <code>SNumber</code> object representing the result of the operation
   */
  protected SNumber subtract(SDecimal num) {
    if (immutable) {
      return new SNumber(this).subtract(num);
    }

    SDecimal bd1 = (bigNumber == null)
                   ? new SDecimal(this)
                   : bigNumber;

    bd1 = bd1.subtract(num);
    fromSDecimal(bd1);

    return this;
  }

  private void fromSDecimal(SDecimal num) {
    mantissa  = 0;
    fraction  = 0;
    decplaces = 0;
    bigNumber = num;
  }

  private static int multiOperation(int sval, char[] ops, int pos, int len) {
    int  i = pos + len - 1;
    char c;

    while(i >= pos) {
      c = ops[i--];

      if (c == MINUS_SIGN) {
        sval = -sval;
      } else if (c == '\'') {
        sval = (sval != 0)
               ? 0
               : 1;
      } else if (c == PLUS_SIGN) {}
      else {
        throw new ArithmeticException("The specified operation '" + new String(ops, pos, len)
                                      + "' is Invalid or unsupported");
      }
    }

    return sval;
  }

  private boolean setBNValueEx(char[] chars, int pos, int len, boolean javaparsecompat) {
    bigNumber = new SDecimal(0);

    if (bigNumber.setValueEx(chars, pos, len, javaparsecompat)) {
      return true;
    }

    bigNumber = null;

    return false;
  }

  private SDecimal toSDecimal() {
    if (bigNumber != null) {
      return bigNumber;
    }

    return new SDecimal(this);
  }

  public static SNumber valueOf(Integer number) {
    if (number == null) {
      return null;
    }

    switch(number.intValue()) {
      case 0 :
        return ZERO;

      case 1 :
        return ONE;

      default :
        return new SNumber(number.intValue());
    }
  }

  public static SNumber valueOf(Long number) {
    if (number == null) {
      return null;
    }

    final long l = number.longValue();

    if (l == 0) {
      return ZERO;
    }

    if (l == 1) {
      return ONE;
    }

    return new SNumber(l);
  }

  public static SNumber valueOf(Double number) {
    if (number == null) {
      return null;
    }

    return new SNumber(number.doubleValue());
  }

  public static SNumber valueOf(Float number) {
    if (number == null) {
      return null;
    }

    return new SNumber(number.floatValue());
  }

  public static SNumber valueOf(Number number) {
    if (number == null) {
      return null;
    }

    if (number instanceof Integer) {
      return valueOf((Integer) number);
    }

    if (number instanceof Long) {
      return valueOf((Long) number);
    }

    return new SNumber(number.doubleValue());
  }

  private SDecimal valueOf(double val) {
    if (val == 0) {
      return SDecimal.zeroDecimal;
    }

    if (val == 1) {
      return SDecimal.oneDecimal;
    }

    if (sdecimal == null) {
      sdecimal = new SDecimal(val);
    } else {
      sdecimal = sdecimal.setValue(val);
    }

    return sdecimal;
  }

  public static boolean isNumeric(char c) {
    final int n = Character.getNumericValue(c);

    return (n > -1) && (n < 10);
  }

  public static boolean isEqual(float a, float b) {
    return Math.abs(a - b) < .0000001;
  }

  public static boolean isEqual(double a, double b) {
    return Math.abs(a - b) < .0000001;
  }

  private static boolean isExponentSymbol(char c, char[] chars, int pos, int len) {
    if (EXPONENT_CHARS_LOWERCASE == null) {
      return (c == EXPONENT_LOWERCASE) || (c == EXPONENT_UPPERCASE);
    }

    if (len < EXPONENT_CHARS_LOWERCASE.length) {
      return false;
    }

    if (c == EXPONENT_CHARS_UPPERCASE[0]) {
      return CharArray.indexOf(chars, pos, EXPONENT_CHARS_UPPERCASE.length, EXPONENT_CHARS_UPPERCASE, 0,
                               EXPONENT_CHARS_UPPERCASE.length, 0) == 0;
    }

    if (c == EXPONENT_CHARS_LOWERCASE[0]) {
      return CharArray.indexOf(chars, pos, EXPONENT_CHARS_LOWERCASE.length, EXPONENT_CHARS_LOWERCASE, 0,
                               EXPONENT_CHARS_LOWERCASE.length, 0) == 0;
    }

    return false;
  }

  private static boolean isTrailingExponent(char c) {
    if (EXPONENT_CHARS_LOWERCASE == null) {
      return (c == EXPONENT_LOWERCASE) || (c == EXPONENT_UPPERCASE);
    }

    return false;
  }

  public static BigDecimal toBigDecimal(byte[] bytes, int pos, int len) {
    int exp = bytes[pos] & 0xff;

    if (exp == 0x80) {
      return BigDecimal.ZERO;
    }

    CharArray ca = (CharArray) perThreadBuffer.get();
    char[]    a  = ca.A;

    return new BigDecimal(a, 0, encodedBytesToString(bytes, pos, len, a, 0, ca));
  }

  /**
   * Converts the number the a set collatable encoded bytes
   * @param value the value as a BigDecimal
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outDecimalPlaces the number of decimals to output (use -1 to output the maximum)
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public static int toEncodedBytes(BigDecimal value, byte[] out, int offset, int outDecimalPlaces, ByteArray outa) {
    return toEncodedBytes(value.toPlainString(), out, offset, outDecimalPlaces, outa);
  }

  /**
   * Converts the number the a set collatable encoded bytes
   * @param value the value as a string
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outDecimalPlaces the number of decimals to output (use -1 to output the maximum)
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public static int toEncodedBytes(double value, byte[] out, int offset, int outDecimalPlaces, ByteArray outa) {
    if ((value > Long.MAX_VALUE) || (value < Long.MIN_VALUE)) {
      return toEncodedBytes(new BigDecimal(value).toPlainString(), out, offset, outDecimalPlaces, outa);
    }

    long mantissa  = 0;
    long fraction  = 0;
    int  decplaces = 0;

    mantissa = (long) value;

    if ((mantissa > 0) && (mantissa > value)) {
      mantissa--;
    } else if ((mantissa < 0) && (mantissa < value)) {
      mantissa++;
    }

    value     = value % 1;
    decplaces = 0;

    while(((value % 1) != 0) && (decplaces < 16)) {
      value *= 10;
      decplaces++;
    }

    fraction = (long) value;

    if (fraction > value) {
      fraction--;
    }

    if (outDecimalPlaces > -1) {
      decplaces = outDecimalPlaces;
    }

    return toEncodedBytes(mantissa, fraction, decplaces, out, offset, outa);
  }

  /**
   * Converts the number the a set collatable encoded bytes
   * @param value the value as a string
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outDecimalPlaces the number of decimals to output (use -1 to output the maximum)
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public static int toEncodedBytes(String value, byte[] out, int offset, int outDecimalPlaces, ByteArray outa) {
    return toEncodedBytes(value.toCharArray(), 0, (int) value.length(), out, offset, outDecimalPlaces, outa);
  }

  /**
   * Converts the number the a set collatable encoded bytes
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public int toEncodedBytes(byte[] out, int offset, ByteArray outa) {
    if (bigNumber != null) {
      CharArray ca = (CharArray) perThreadBuffer.get();

      bigNumber.toString(ca, false);

      return toEncodedBytes(ca.A, 0, ca._length, out, offset, -1, outa);
    }

    return toEncodedBytes(mantissa, fraction, decplaces, out, offset, null);
  }

  /**
   * Converts the number the a set collatable encoded bytes
   *
   * @param mantissa the mantissa
   * @param fraction the fraction
   * @param decplaces the number of sdecimal places that the fraction represents
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public static int toEncodedBytes(long mantissa, long fraction, int decplaces, byte[] out, int offset,
                                   ByteArray outa) {
    if ((mantissa == 0) && ((fraction == 0) || (decplaces == 0))) {
      if (outa != null) {
        outa.ensureCapacity(offset + 2);
        out = outa.A;
      }

      out[offset] = (byte) 0x80;

      return 1;
    }

    if (decplaces < 1) {
      fraction  = 0;
      decplaces = 0;
    }

    boolean neg = mantissa < 0;

    if (!neg) {
      neg = fraction < 0;
    }

    mantissa = neg
               ? -mantissa
               : mantissa;
    fraction = (fraction < 0)
               ? -fraction
               : fraction;

    int fdigits = (fraction == 0)
                  ? 0
                  : stringSize(fraction);
    int mdigits = (mantissa == 0)
                  ? 0
                  : stringSize(mantissa);
    int exp     = mdigits - 1;

    if (mdigits == 0) {
      exp       = fdigits - (decplaces + 1);
      decplaces = fdigits;
    } else if (decplaces == 0) {
      while(mantissa % 10 == 0) {
        mantissa /= 10;
        mdigits--;
      }
    }

    exp += 0x3f;
    exp |= 0x80;

    if (neg) {
      exp ^= 0xff;
    }

    boolean nibble = false;    // true for left nibble, false for right nibble.
    int     length = mdigits + decplaces;

    if (length % 2 == 1) {
      length++;
      nibble = true;
    }

    length = length / 2;

    if (outa != null) {
      outa.ensureCapacity(length + offset + 3);
      out = outa.A;
    }

    out[offset] = (byte) exp;
    offset++;

    int  bpos = offset + length - 1;
    byte b    = 0;
    int  nextDigit;

    while(decplaces-- > 0) {
      nextDigit = (int) (fraction % 10);

      if (nibble) {
        b &= (byte) (0x000F);
        b |= ((byte) nextDigit << 4);
        b++;

        if (neg) {
          b ^= 0xff;
        }

        out[bpos--] = b;
        b           = 0;
      } else {
        b &= (byte) (0x00F0);
        b |= (byte) nextDigit;
      }

      nibble   = !nibble;
      fraction /= 10;
    }

    while(mantissa > 0) {
      nextDigit = (int) (mantissa % 10);

      if (nibble) {
        b &= (byte) (0x000F);
        b |= ((byte) nextDigit << 4);
        b++;

        if (neg) {
          b ^= 0xff;
        }

        out[bpos--] = b;
        b           = 0;
      } else {
        b &= (byte) (0x00F0);
        b |= (byte) nextDigit;
      }

      nibble   = !nibble;
      mantissa /= 10;
    }

    if (neg) {
      // Fix the sign.
      bpos      = offset + length;
      out[bpos] = (byte) 0xff;
      length++;
    }

    return length + 1;
  }

  public static int toEncodedBytes(char[] chars, int pos, int len, byte[] out, int offset, int outDecimalPlaces,
                                   ByteArray outa) {
    boolean neg = false;

    if (chars[pos] == MINUS_SIGN) {
      neg = true;
      pos++;
      len--;
    }

    int end = pos + len;

    while((pos < end) && (chars[pos] == ZERO_DIGIT)) {
      pos++;
    }

    len = end - pos;

    if (len == 0) {
      if (outa != null) {
        outa.ensureCapacity(offset + 2);
        out = outa.A;
      }

      out[offset] = (byte) 0x80;

      return 1;
    }

    int scale = 0;

    while((end > pos) && (chars[end - 1] == ZERO_DIGIT)) {
      scale++;
      end--;
    }

    len = end - pos;

    int mdigits = end - pos;
    int fdigits = 0;
    int i       = pos;
    int decpos  = end;
    int oo      = offset;

    while(i < end) {
      if (chars[i] == DECIMAL_POINT) {
        decpos  = i;
        mdigits = i - pos;

        if (outDecimalPlaces == 0) {
          end = i;

          break;
        }

        while((end > i) && (chars[end - 1] == ZERO_DIGIT)) {
          end--;
        }

        scale   = end - i - 1;
        fdigits = scale;

        if (mdigits == 0) {
          pos++;

          while((pos < end) && (chars[pos] == ZERO_DIGIT)) {
            pos++;
            fdigits--;
          }
        }

        break;
      }

      i++;
    }

    int exp = mdigits - 1;

    if (mdigits == 0) {
      exp = fdigits - (scale + 1);
    } else if (fdigits == 0) {
      exp = mdigits + scale - 1;
    }

    exp += 0x3f;
    exp |= 0x80;

    if (neg) {
      exp ^= 0xff;
    }

    final int length = mdigits + fdigits;
    boolean   odd    = length % 2 == 1;

    if (outa != null) {
      outa.ensureCapacity((length / 2) + offset + 3);
      out = outa.A;
    }

    out[offset] = (byte) exp;
    offset++;

    int b = 0;

    if (odd) {
      end--;
    }

    while(pos < end) {
      if (pos == decpos) {
        pos++;

        if (pos == end) {
          break;
        }
      }

      b = (chars[pos++] & 0x000F) << 4;

      if (pos == decpos) {
        pos++;
      }

      b += chars[pos++] & 0x000F;
      b++;

      if (neg) {
        b ^= 0xff;
      }

      out[offset++] = (byte) (b);
    }

    if (odd) {
      b = (chars[pos++] & 0x000F) << 4;
      b++;

      if (neg) {
        b ^= 0xff;
      }

      out[offset++] = (byte) (b);
    }

    if (neg) {
      out[offset++] = (byte) 0xff;
    }

    return offset - oo;
  }

  /**
   * Converts the number the a set collatable encoded characters
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public int toEncodedChars(char[] out, int offset, CharArray outa) {
    if (bigNumber != null) {
      CharArray ca = (CharArray) perThreadBuffer.get();

      bigNumber.toString(ca, false);

      return toEncodedChars(ca.A, 0, ca._length, out, offset, -1, outa);
    }

    return toEncodedChars(mantissa, fraction, decplaces, out, offset, outa);
  }

  /**
   * Converts the number the a set collatable encoded characters
   * @param out The array to receive the data
   * @return The number of bytes added to the output
   */
  public int toEncodedChars(CharArray out) {
    if (bigNumber != null) {
      CharArray ca = (CharArray) perThreadBuffer.get();

      bigNumber.toString(ca, false);

      return toEncodedChars(ca.A, 0, ca._length, out.A, out._length, -1, out);
    }

    return toEncodedChars(mantissa, fraction, decplaces, out.A, out._length, out);
  }

  /**
   * Converts the number the a set collatable encoded bytes
   *
   * @param mantissa the mantissa
   * @param fraction the fraction
   * @param decplaces the number of sdecimal places that the fraction represents
   * @param out The array to receive the data
   * @param offset The offset into the array
   * @param outa the array holder for the output. If specified it will be used to size the output array appropriately
   * @return The number of bytes added to the output
   */
  public static int toEncodedChars(long mantissa, long fraction, int decplaces, char[] out, int offset,
                                   CharArray outa) {
    if ((mantissa == 0) && ((fraction == 0) || (decplaces == 0))) {
      if (outa != null) {
        outa.ensureCapacity(offset + 2);
        out = outa.A;
      }

      out[offset] = (char) 0x80;

      return 1;
    }

    if (decplaces < 1) {
      fraction  = 0;
      decplaces = 0;
    }

    boolean neg = mantissa < 0;

    if (!neg) {
      neg = fraction < 0;
    }

    mantissa = neg
               ? -mantissa
               : mantissa;
    fraction = (fraction < 0)
               ? -fraction
               : fraction;

    int fdigits = (fraction == 0)
                  ? 0
                  : stringSize(fraction);
    int mdigits = (mantissa == 0)
                  ? 0
                  : stringSize(mantissa);
    int exp     = mdigits - 1;

    if (mdigits == 0) {
      exp       = fdigits - (decplaces + 1);
      decplaces = fdigits;
    } else if (decplaces == 0) {
      while(mantissa % 10 == 0) {
        mantissa /= 10;
        mdigits--;
      }
    }

    exp += 0x3f;
    exp |= 0x80;

    if (neg) {
      exp ^= 0xff;
    }

    boolean nibble = false;    // true for left nibble, false for right nibble.
    int     length = mdigits + decplaces;

    if (length % 2 == 1) {
      length++;
      nibble = true;
    }

    length = length / 2;

    if (outa != null) {
      outa.ensureCapacity(length + offset + 3);
      out = outa.A;
    }

    out[offset] = (char) exp;
    offset++;

    int  bpos = offset + length - 1;
    char b    = 0;
    int  nextDigit;

    while(decplaces-- > 0) {
      nextDigit = (int) (fraction % 10);

      if (nibble) {
        b &= (byte) (0x000F);
        b |= ((byte) nextDigit << 4);
        b++;

        if (neg) {
          b ^= 0xff;
        }

        out[bpos--] = b;
        b           = 0;
      } else {
        b &= (byte) (0x00F0);
        b |= (byte) nextDigit;
      }

      nibble   = !nibble;
      fraction /= 10;
    }

    while(mantissa > 0) {
      nextDigit = (int) (mantissa % 10);

      if (nibble) {
        b &= (byte) (0x000F);
        b |= ((byte) nextDigit << 4);
        b++;

        if (neg) {
          b ^= 0xff;
        }

        out[bpos--] = b;
        b           = 0;
      } else {
        b &= (byte) (0x00F0);
        b |= (byte) nextDigit;
      }

      nibble   = !nibble;
      mantissa /= 10;
    }

    if (neg) {
      // Fix the sign.
      bpos      = offset + length;
      out[bpos] = (char) 0xff;
      length++;
    }

    return length + 1;
  }

  public static int toEncodedChars(char[] chars, int pos, int len, char[] out, int offset, int outDecimalPlaces,
                                   CharArray outa) {
    boolean neg = false;

    if (chars[pos] == MINUS_SIGN) {
      neg = true;
      pos++;
      len--;
    }

    int end = pos + len;

    while((pos < end) && (chars[pos] == ZERO_DIGIT)) {
      pos++;
    }

    len = end - pos;

    if (len == 0) {
      if (outa != null) {
        outa.ensureCapacity(offset + 2);
        out = outa.A;
      }

      out[offset] = (char) 0x80;

      return 1;
    }

    int scale = 0;

    while((end > pos) && (chars[end - 1] == ZERO_DIGIT)) {
      scale++;
      end--;
    }

    len = end - pos;

    int mdigits = end - pos;
    int fdigits = 0;
    int i       = pos;
    int decpos  = end;
    int oo      = offset;

    while(i < end) {
      if (chars[i] == DECIMAL_POINT) {
        decpos  = i;
        mdigits = i - pos;

        if (outDecimalPlaces == 0) {
          end = i;

          break;
        }

        while((end > i) && (chars[end - 1] == ZERO_DIGIT)) {
          end--;
        }

        scale   = end - i - 1;
        fdigits = scale;

        if (mdigits == 0) {
          pos++;

          while((pos < end) && (chars[pos] == ZERO_DIGIT)) {
            pos++;
            fdigits--;
          }
        }

        break;
      }

      i++;
    }

    int exp = mdigits - 1;

    if (mdigits == 0) {
      exp = fdigits - (scale + 1);
    } else if (fdigits == 0) {
      exp = mdigits + scale - 1;
    }

    exp += 0x3f;
    exp |= 0x80;

    if (neg) {
      exp ^= 0xff;
    }

    final int length = mdigits + fdigits;
    boolean   odd    = length % 2 == 1;

    if (outa != null) {
      outa.ensureCapacity((length / 2) + offset + 3);
      out = outa.A;
    }

    out[offset] = (char) exp;
    offset++;

    int b = 0;

    if (odd) {
      end--;
    }

    while(pos < end) {
      if (pos == decpos) {
        pos++;

        if (pos == end) {
          break;
        }
      }

      b = (chars[pos++] & 0x000F) << 4;

      if (pos == decpos) {
        pos++;
      }

      b += chars[pos++] & 0x000F;
      b++;

      if (neg) {
        b ^= 0xff;
      }

      out[offset++] = (char) (b);
    }

    if (odd) {
      b = (chars[pos++] & 0x000F) << 4;
      b++;

      if (neg) {
        b ^= 0xff;
      }

      out[offset++] = (char) (b);
    }

    if (neg) {
      out[offset++] = (char) 0xff;
    }

    return offset - oo;
  }

  public static SNumber fromEncodedBytes(byte[] bytes, int pos, int len) {
    return fromEncodedBytes(bytes, pos, len, null);
  }

  public static SNumber fromEncodedBytes(byte[] bytes, int pos, int len, SNumber out) {
    int exp = bytes[pos] & 0xff;

    if (exp == 0x80) {
      return (out == null)
             ? SNumber.ZERO
             : out.setValue(0, 0, 0);
    }

    boolean neg = (exp & 0x80) == 0;

    if (neg) {
      exp ^= 0xff;
      len--;
    }

    exp &= 0x7f;
    exp -= 0x3f;

    long mantissa  = 0;
    long fraction  = 0;
    int  decplaces = 0;
    int  end       = len + pos;

    pos++;
    exp++;

    if (exp < 0) {
      decplaces = -exp;
    }

    byte b;

    while((pos < end) && (exp > 0)) {
      b = bytes[pos++];
      b--;

      if (neg) {
        b ^= 0xff;
      }

      mantissa = mantissa * 10 + ((b & 0xf0) >>> 4);    // high nybble.
      exp--;

      if (exp == 0) {
        if ((pos != end) || (b & 0x0f) != 0) {
          fraction = fraction * 10 + (b & 0x0f);        // low nybble.
          decplaces++;
        }

        break;
      }

      mantissa = mantissa * 10 + (b & 0x0f);            // low nybble.
      exp--;
    }

    while(exp > 0) {
      mantissa *= 10;
      exp--;
    }

    while(pos < end) {
      b = bytes[pos++];
      b--;

      if (neg) {
        b ^= 0xff;
      }

      fraction = fraction * 10 + ((b & 0xf0) >>> 4);    // high nybble.
      decplaces++;

      if ((pos != end) || (b & 0x0f) != 0) {
        fraction = fraction * 10 + (b & 0x0f);          // low nybble.
        decplaces++;
      }
    }

    return (out == null)
           ? new SNumber(mantissa, fraction, decplaces)
           : out.setValue(mantissa, fraction, decplaces);
  }

  public static SNumber fromEncodedChars(char[] chars, int pos, int len) {
    return fromEncodedChars(chars, pos, len, null);
  }

  public static SNumber fromEncodedChars(char[] chars, int pos, int len, SNumber out) {
    int exp = chars[pos] & 0xff;

    if (exp == 0x80) {
      return (out == null)
             ? SNumber.ZERO
             : out.setValue(0, 0, 0);
    }

    boolean neg = (exp & 0x80) == 0;

    if (neg) {
      exp ^= 0xff;
      len--;
    }

    exp &= 0x7f;
    exp -= 0x3f;

    long mantissa  = 0;
    long fraction  = 0;
    int  decplaces = 0;
    int  end       = len + pos;

    pos++;
    exp++;

    if (exp < 0) {
      decplaces = -exp;
    }

    char b;

    while((pos < end) && (exp > 0)) {
      b = chars[pos++];
      b--;

      if (neg) {
        b ^= 0xff;
      }

      mantissa = mantissa * 10 + ((b & 0xf0) >>> 4);    // high nybble.
      exp--;

      if (exp == 0) {
        if ((pos != end) || (b & 0x0f) != 0) {
          fraction = fraction * 10 + (b & 0x0f);        // low nybble.
          decplaces++;
        }

        break;
      }

      mantissa = mantissa * 10 + (b & 0x0f);            // low nybble.
      exp--;
    }

    while(exp > 0) {
      mantissa *= 10;
      exp--;
    }

    while(pos < end) {
      b = chars[pos++];
      b--;

      if (neg) {
        b ^= 0xff;
      }

      fraction = fraction * 10 + ((b & 0xf0) >>> 4);    // high nybble.
      decplaces++;

      if ((pos != end) || (b & 0x0f) != 0) {
        fraction = fraction * 10 + (b & 0x0f);          // low nybble.
        decplaces++;
      }
    }

    return (out == null)
           ? new SNumber(mantissa, fraction, decplaces)
           : out.setValue(mantissa, fraction, decplaces);
  }

  public static String encodedBytesToString(byte[] bytes, int pos, int len) {
    int exp = bytes[pos] & 0xff;

    if (exp == 0x80) {
      return "0";
    }

    CharArray ca = (CharArray) perThreadBuffer.get();
    char[]    a  = ca.A;

    return new String(a, 0, encodedBytesToString(bytes, pos, len, a, 0, ca));
  }

  public static String encodedCharsToString(char[] chars, int pos, int len) {
    int exp = chars[pos] & 0xff;

    if (exp == 0x80) {
      return "0";
    }

    CharArray ca = (CharArray) perThreadBuffer.get();
    char[]    a  = ca.A;

    return new String(a, 0, encodedCharsToString(chars, pos, len, a, 0, ca));
  }

  public static int encodedBytesToString(byte[] bytes, int pos, int len, char[] out, int offset, CharArray ca) {
    int exp = bytes[pos] & 0xff;

    if (exp == 0x80) {
      out[offset] = ZERO_DIGIT;

      return 1;
    }

    int     oo  = offset;
    boolean neg = (exp & 0x80) == 0;

    if (neg) {
      exp ^= 0xff;
      len--;
      out[offset++] = MINUS_SIGN;
    }

    exp &= 0x7f;
    exp -= 0x3f;

    int end = len + pos;

    pos++;

    byte b = 0;

    if (ca != null) {
      ca.ensureCapacity(offset + len + exp + 4);
      out = ca.A;
    }

    if (exp < 0) {
      out[offset++] = DECIMAL_POINT;

      while(exp < -1) {
        out[offset++] = ZERO_DIGIT;
        exp++;
      }

      while(pos < end) {
        b = bytes[pos++];
        b--;

        if (neg) {
          b ^= 0xff;
        }

        out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));      // high nybble.
        out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
      }

      if (out[offset - 1] == ZERO_DIGIT) {
        offset--;
      }
    } else {
      boolean nibble = false;

      while((pos < end) && (exp > -1)) {
        if (nibble) {
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        } else {
          b = bytes[pos++];
          b--;

          if (neg) {
            b ^= 0xff;
          }

          out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));    // high nybble.
        }

        nibble = !nibble;
        exp--;
      }

      if (pos >= end) {
        if (nibble) {
          if (exp == -1) {
            if ((b & 0x0f) != 0) {
              out[offset++] = DECIMAL_POINT;
              out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
            }
          } else {
            out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
          }
        }

        while(exp > 0) {
          out[offset++] = ZERO_DIGIT;
          exp--;
        }
      } else {
        out[offset++] = DECIMAL_POINT;

        if (nibble) {
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        }

        while(pos < end) {
          b = bytes[pos++];
          b--;

          if (neg) {
            b ^= 0xff;
          }

          out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));    // high nybble.
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        }

        if (out[offset - 1] == ZERO_DIGIT) {
          offset--;
        }
      }
    }

    return offset - oo;
  }

  public static int encodedCharsToString(char[] chars, int pos, int len, char[] out, int offset, CharArray ca) {
    int exp = chars[pos] & 0xff;

    if (exp == 0x80) {
      if (ca != null) {
        ca.ensureCapacity(offset + 2);
        out = ca.A;
      }

      out[offset] = ZERO_DIGIT;

      return 1;
    }

    int     oo  = offset;
    boolean neg = (exp & 0x80) == 0;

    if (neg) {
      exp ^= 0xff;
      len--;
    }

    exp &= 0x7f;
    exp -= 0x3f;

    int end = len + pos;

    pos++;

    char b = 0;

    if (ca != null) {
      ca.ensureCapacity(offset + len + exp + 4);
      out = ca.A;
    }

    if (neg) {
      out[offset++] = MINUS_SIGN;
    }

    if (exp < 0) {
      out[offset++] = DECIMAL_POINT;

      while(exp < -1) {
        out[offset++] = ZERO_DIGIT;
        exp++;
      }

      while(pos < end) {
        b = chars[pos++];
        b--;

        if (neg) {
          b ^= 0xff;
        }

        out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));      // high nybble.
        out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
      }

      if (out[offset - 1] == ZERO_DIGIT) {
        offset--;
      }
    } else {
      boolean nibble = false;

      while((pos < end) && (exp > -1)) {
        if (nibble) {
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        } else {
          b = chars[pos++];
          b--;

          if (neg) {
            b ^= 0xff;
          }

          out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));    // high nybble.
        }

        nibble = !nibble;
        exp--;
      }

      if (pos >= end) {
        if (nibble) {
          if (exp == -1) {
            if ((b & 0x0f) != 0) {
              out[offset++] = DECIMAL_POINT;
              out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
            }
          } else {
            out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
          }
        }

        while(exp > 0) {
          out[offset++] = ZERO_DIGIT;
          exp--;
        }
      } else {
        out[offset++] = DECIMAL_POINT;

        if (nibble) {
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        }

        while(pos < end) {
          b = chars[pos++];
          b--;

          if (neg) {
            b ^= 0xff;
          }

          out[offset++] = (char) (ZERO_DIGIT + ((b & 0xf0) >>> 4));    // high nybble.
          out[offset++] = (char) (ZERO_DIGIT + (b & 0x0f));
        }

        if (out[offset - 1] == ZERO_DIGIT) {
          offset--;
        }
      }
    }

    return offset - oo;
  }
}
