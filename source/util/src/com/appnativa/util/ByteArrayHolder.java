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
 * A container container for an array of bytes
 * @author Don DeCoteau
 * @version 2.3
 */
public class ByteArrayHolder {
  private static byte[] ZERO_LENGTH_BYTES = {};

  /** the array of bytes */
  public byte[] bytes;

  /** the character set helper */
  public iCharsetHelper csHelper;

  /** the number of bytes */
  public int length;

  /** the starting position within the array */
  public int position;

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   */
  public ByteArrayHolder() {
    position = 0;
    length   = 0;
    bytes    = null;
  }

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   *
   * @param val  the value
   */
  public ByteArrayHolder(byte[] val) {
    bytes    = val;
    position = 0;
    length   = (bytes != null)
               ? bytes.length
               : 0;
  }

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   *
   * @param val   the value
   * @param copy  <code>true</code> to copy the array; <code>false</code> otherwise.
   */
  public ByteArrayHolder(byte[] val, boolean copy) {
    if (copy && (val != null)) {
      int len = val.length;

      bytes = new byte[len];
      System.arraycopy(val, 0, bytes, 0, len);
    } else {
      bytes = val;
    }

    position = 0;
    length   = (bytes != null)
               ? bytes.length
               : 0;
  }

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   *
   * @param val   the value
   * @param copy  <code>true</code> to copy the array; <code>false</code> otherwise.
   */
  public ByteArrayHolder(ByteArrayHolder val, boolean copy) {
    set(val, copy);
  }

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   *
   * @param val  the value
   * @param pos  the starting position within the array
   * @param len  the number of bytes in the array to use
   */
  public ByteArrayHolder(byte[] val, int pos, int len) {
    bytes    = val;
    position = pos;
    length   = len;
  }

  /**
   * Creates a new <code>ByteArrayHolder</code> object
   *
   * @param val  the value
   * @param pos  the starting position within the array
   * @param len  the number of bytes in the array to use
   * @param copy  <code>true</code> to copy the array; <code>false</code> otherwise.
   */
  public ByteArrayHolder(byte[] val, int pos, int len, boolean copy) {
    if (copy) {
      bytes  = new byte[len];
      length = len;
      System.arraycopy(val, pos, bytes, 0, len);
    } else {
      bytes    = val;
      position = pos;
      length   = len;
    }
  }

  /**
   * Compares this byte array holder to another byte array holder.
   *
   * @param array  the array holder to be compared.
   *
   * @return   the value <code>0</code> if the argument is a byte array holder
   *           lexicographically equal to this byte array; a value less than <code>0</code> if
   *           the argument is a byte array holder lexicographically greater than this byte
   *           array holder; and a value greater than <code>0</code> if the argument is a byte
   *           array holder lexicographically less than this byte array holder.
   */
  public int compareTo(ByteArrayHolder array) {
    if ((length == 0) && (array.length == 0)) {
      return 0;
    }

    if ((bytes == null) || (array.bytes == null)) {
      if (bytes == array.bytes) {
        return 0;
      }

      return (bytes == null)
             ? -1
             : 1;
    }

    return Helper.compareTo(bytes, position, length, array.bytes, array.position, array.length);
  }

  /**
   * Compares this byte array holder to another object. If the object is a byte array
   * holder, this function behaves like <code>compareTo(ByteArrayHolder)</code>.
   * Otherwise, it throws a <code>ClassCastException</code>
   *
   * @param o  the <code>Object</code> to be compared.
   *
   * @return   the value <code>0</code> if the argument is a byte array holder
   *           lexicographically equal to this byte array; a value less than <code>0</code> if
   *           the argument is a byte array holder lexicographically greater than this byte
   *           array holder; and a value greater than <code>0</code> if the argument is a byte
   *           array holder lexicographically less than this byte array holder.
   */
  public int compareTo(Object o) {
    return compareTo((ByteArrayHolder) o);
  }

  /**
   * Compares this byte array to another byte array. The two are considered equal if
   * they are of the same length, and corresponding characters in the two are
   * identical.
   *
   * @param array  the byte array
   *
   * @return   <code>true</code> if the argument is not <code>null</code> and the byte
   *           arrays are equal; <code>false</code> otherwise.
   */
  public boolean equals(ByteArrayHolder array) {
    if ((length == 0) && (array.length == 0)) {
      return true;
    }

    if (length != array.length) {
      return false;
    }

    if ((bytes == null) || (array.bytes == null)) {
      return bytes == array.bytes;
    }

    return Helper.regionMatches(bytes, position, array.bytes, array.position, length);
  }

  /**
   * Compares this byte array to an object.
   *
   * @param a  the object
   *
   * @return   <code>true</code> if the argument is not <code>null</code> and the byte
   *           arrays are equal; <code>false</code> otherwise.
   */
  public boolean equals(Object a) {
    if (a instanceof ByteArrayHolder) {
      return equals((ByteArrayHolder) a);
    }

    return false;
  }

  /**
   * Converts the contents of the byte array to characters
   *
   * @return   the characters
   */
  public char[] getChars() {
    return getChars(new CharArray()).toCharArray();
  }

  /**
   * Converts the contents of the byte array to characters
   *
   * @param out  the character array to output the results to
   *
   * @return   the passed in output array
   */
  public CharArray getChars(CharArray out) {
    if (csHelper != null) {
      out._length += csHelper.getChars(bytes, position, length, out, out._length);
    } else {
      out._length += ISO88591Helper.getInstance().getChars(bytes, position, length, out, out._length);
    }

    return out;
  }

  /**
   * Converts the contents of the byte array to characters
   *
   * @param csh  the character set helper
   *
   * @return   the characters
   */
  public char[] getChars(iCharsetHelper csh) {
    return getChars(new CharArray(), csh).toCharArray();
  }

  /**
   * Converts the contents of the byte array to characters
   *
   * @param out  the character array to output the results to
   * @param csh  the character set helper
   *
   * @return   the passed in output array
   */
  public CharArray getChars(CharArray out, iCharsetHelper csh) {
    out._length += csh.getChars(bytes, position, length, out, out._length);

    return out;
  }

  /**
   * Returns the hash code.
   *
   * @return   a hash code value for this object.
   */
  public int hashCode() {
    if (bytes == null) {
      return 0;
    }

    int    h   = 0;
    int    len = position + length;
    int    i   = position;
    byte[] a   = bytes;

    while(i < len) {
      h = (31 * h) + a[i++];
    }

    return h;
  }

  /**
   * Searches for the first occurrence of the given byte
   *
   * @param b  the byte
   *
   * @return   the index of the first occurrence of the byte (relative to zero) ; returns <tt>-1</tt>
   *           if the byte is not found.
   */
  public int indexOf(byte b) {
    return Helper.indexOf(bytes, position, length, b);
  }

  /**
   * Returns the index of the last occurrence of the specified byte in this buffer.
   *
   * @param b  the desired byte.
   *
   * @return   the index of the last occurrence of the specified byte in this buffer
   *           (relative to zero); returns -1 if the object is not found.
   */
  public int lastIndexOf(byte b) {
    return Helper.lastIndexOf(bytes, position, length, b);
  }

  /**
   * Sets the value
   *
   * @param val  the value
   */
  public void set(byte[] val) {
    bytes    = val;
    position = 0;
    length   = (val == null)
               ? 0
               : val.length;
  }

  /**
   * Sets the value
   *
   * @param val  the value
   */
  public void set(ByteArray val) {
    bytes    = val.A;
    position = 0;
    length   = val._length;
  }

  /**
   * Sets the value
   *
   * @param val  the value
   */
  public void set(ByteArrayHolder val) {
    bytes    = val.bytes;
    position = val.position;
    length   = val.length;
  }

  /**
   * Sets the value
   *
   * @param val  the value
   */
  public void set(String val) {
    bytes    = null;
    position = 0;
    length   = 0;

    if (val == null) {
      return;
    }

    if (val.length() == 0) {
      bytes = ZERO_LENGTH_BYTES;

      return;
    }

    if (csHelper == null) {
      bytes = val.getBytes();
    } else {
      bytes = csHelper.getBytes(val);
    }

    length = bytes.length;
  }

  /**
   * Sets the value. If copy is specified then the complete array
   * is copied otherwise the array is just referenced
   *
   * @param val   the value
   * @param copy  <code>true</code> to copy the array; <code>false</code> otherwise.
   */
  public void set(ByteArrayHolder val, boolean copy) {
    if (copy && (val.bytes != null)) {
      int len = val.bytes.length;

      bytes = new byte[len];
      System.arraycopy(val.bytes, 0, bytes, 0, len);
    } else {
      bytes = val.bytes;
    }

    position = val.position;
    length   = val.length;
  }

  /**
   * Set the values
   *
   * @param val  the value
   * @param pos  the starting position within the array
   * @param len  the number of bytes in the array to use
   */
  public void set(byte[] val, int pos, int len) {
    bytes    = val;
    position = pos;
    length   = len;
  }

  /**
   * Set the encoding to use when converting to/from a string;
   *
   * @param csh  the character set helper
   */
  public void setCharsetHelper(iCharsetHelper csh) {
    csHelper = csh;
  }

  /**
   * Sets the value. If copy is specified then the valid portion of
   * the array is copied otherwise the array is just referenced
   *
   * @param val  the value
   * @param copy  <code>true</code> to copy the array; <code>false</code> otherwise.
   */
  public void setEx(ByteArrayHolder val, boolean copy) {
    if (copy && (val.bytes != null)) {
      int len = val.length;

      if ((bytes == null) || (bytes.length < len)) {
        bytes = new byte[len];
      }

      System.arraycopy(val.bytes, val.position, bytes, 0, len);
      position = 0;
    } else {
      bytes    = val.bytes;
      position = val.position;
    }

    length = val.length;
  }

  /**
   * Skips the specified number of bytes. This increases the position by the
   * specified amount and decrease the length by the same amount
   *
   * @param len  the number of bytes to skip;
   */
  public void skip(int len) {
    position += len;
    length   -= len;

    if (length < 0) {
      length = 0;
    }
  }

  /**
   * Tests to see if this object starts with the specified array
   *
   * @param ba  the array of bytes
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean startsWith(ByteArray ba) {
    if (length < ba._length) {
      return false;
    }

    return Helper.regionMatches(bytes, position, ba.A, 0, ba._length);
  }

  /**
   * Tests to see if this object starts with the specified array
   *
   * @param bah  the array of bytes
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean startsWith(ByteArrayHolder bah) {
    if (length < bah.length) {
      return false;
    }

    return Helper.regionMatches(bytes, position, bah.bytes, bah.position, bah.length);
  }

  /**
   * Returns the array of bytes
   *
   * @return   the array of bytes
   */
  public byte[] toBytes() {
    if (bytes == null) {
      return bytes;
    }

    if ((position == 0) && (bytes.length == length)) {
      return bytes;
    }

    byte[] b = new byte[length];

    if (length < 20) {
      for (int i = 0; i < length; i++) {
        b[i] = bytes[position + i];
      }
    } else {
      System.arraycopy(bytes, position, b, 0, length);
    }

    return b;
  }

  /**
   * Returns the string representation of the array of bytes.
   *
   * @return   the string representing the array of bytes
   */
  public String toString() {
    if (length == 0) {
      return "";
    }

    if (csHelper != null) {
      return csHelper.getString(bytes, position, length);
    }

    return new String(bytes, position, length);
  }

  /**
   * Returns the string representation of the array of bytes.
   *
   * @param csh  the character set helper
   *
   * @return   the string representing the array of bytes
   */
  public String toString(iCharsetHelper csh) {
    if (length == 0) {
      return "";
    }

    return csh.getString(bytes, position, length);
  }

  /**
   * Returns the string representation of the array of bytes.
   *
   * @param csh  the character set helper
   * @param ca  an optional character array object to use to do the conversion
   *
   * @return   the string representing the array of bytes
   */
  public String toString(iCharsetHelper csh, CharArray ca) {
    if (length == 0) {
      return "";
    }

    ca.ensureCapacity(length);

    char[] chars   = ca.A;
    int    charLen = csh.getChars(bytes, position, length, chars, 0);

    return new String(chars, 0, charLen);
  }
}
