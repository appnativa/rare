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

/**
 * This class represent a performance sensitive version of the <code>java.util.ArrayList
 * </code>and <code>java.io.ByteInputStream</code> classes specifically designed
 * for bytes. Both the internal array (<code>A</code>) and the content's length (<code>_length</code>)
 * are exposed as public variables. This allows direct manipulation of the arrays
 * contents. As such, care should be taken when directly modifying either of these
 * fields.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class ByteArray extends InputStream implements Cloneable {

  /** the array that holds the contents */
  public byte[] A;

  /** the length of the array's contents */
  public int _length;

  /** the character set helper */
  protected iCharsetHelper charsetHelper;

  /** the read mark */
  protected int theMark;

  /** the read position */
  protected int thePos;

  /**
   * Constructs an empty array with an initial capacity of 16 bytes.
   */
  public ByteArray() {
    this(16);
  }

  /**
   * Constructs an empty array using the specified array as the backing store
   *
   * @param a the backing array
   */
  public ByteArray(byte[] a) {
    A       = a;
    _length = a.length;
  }

  /**
   * Constructs an empty array with the specified initial capacity.
   *
   * @param len  the initial capacity of the array.
   */
  public ByteArray(int len) {
    A = new byte[len];
  }

  /**
   * Appends the specified byte to the end of this array.
   *
   * @param e  byte to be inserted.
   */
  public void add(byte e) {
    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    A[_length++] = e;
  }

  /**
   * Appends the specified array of bytes to the end of this array.
   *
   * @param e  byte to be inserted.
   */
  public void add(byte[] e) {
    add(e, 0, e.length);
  }

  /**
   * Appends the specified array of bytes to the end of this array.
   *
   * @param e  byte to be inserted.
   */
  public void add(ByteArray e) {
    add(e.A, 0, e._length);
  }

  /**
   * Appends the specified array of bytes to the end of this array.
   *
   * @param e  byte to be inserted.
   */
  public void add(ByteArrayHolder e) {
    add(e.bytes, e.position, e.length);
  }

  /**
   * Adds the contents of the byte array to the bytes in the specified string
   *
   * @param e  the string
   *
   * @return   a reference to this byte array.
   */
  public ByteArray add(String e) {
    if (charsetHelper == null) {
      add(e.getBytes());
    } else {
      add(charsetHelper.getBytes(e));
    }

    return this;
  }

  /**
   * Inserts the specified byte at the specified position in this array. Shifts the
   * byte currently at that position (if any) and any subsequent bytes to the right
   * (adds one to their indices).
   *
   * @param pos  index at which the specified byte is to be inserted.
   * @param e    byte to be inserted.
   *
   */
  public void add(int pos, byte e) {
    if (pos > _length) {
      throw new IndexOutOfBoundsException(pos + ">" + _length);
    }

    if (pos < 0) {
      throw new IndexOutOfBoundsException(pos + "<0");
    }

    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    if (pos == _length) {
      A[_length++] = e;

      return;
    }

    System.arraycopy(A, pos, A, pos + 1, _length - pos);
    A[pos] = e;
    _length++;
  }

  /**
   * Appends the specified array of bytes to the end of this array.
   * @param e bytes to be append.
   * @param pos the starting position within the array
   * @param len the number of array elements to use
   */
  public void add(byte[] e, int pos, int len) {
    if (len == 0) {
      return;
    }

    if (len == -1) {
      len = e.length - pos;
    }

    int nlen = len + _length;

    if (nlen > A.length) {
      ensureCapacity(nlen);
    }

    if (len == 1) {
      add(e[pos]);
    } else {
      System.arraycopy(e, pos, A, _length, len);
      _length = nlen;
    }
  }

  /**
   * Appends the specified array of bytes to the end of this array.
   *
   * @param e  bytes to append.
   */
  public void addAll(ByteArray e) {
    add(e.A, 0, e._length);
  }

  /**
   * Returns the number of bytes that can be read from this input stream without
   * blocking. The value returned is <code>count&nbsp;- pos</code>, which is the
   * number of bytes remaining to be read from the input array.
   *
   * @return   the number of bytes that can be read from the input stream without
   *           blocking.
   */
  public int available() {
    return _length - thePos;
  }

  /**
   * Returns the current capacity of this byte array.
   *
   * @return   the current capacity
   */
  public int capacity() {
    return A.length;
  }

  /**
   * Removes all of the bytes from this array. The array will be empty after this
   * call returns.
   */
  public void clear() {
    _length = 0;
    thePos  = 0;
    theMark = 0;
    A[0]    = 0;
  }

  /**
   * Returns a shallow copy of this list. (The elements themselves are not copied.)
   *
   * @return   a clone of this list.
   */
  public Object clone() {
    try {
      ByteArray v = (ByteArray) super.clone();

      v.A = new byte[A.length];
      System.arraycopy(A, 0, v.A, 0, A.length);

      return v;
    } catch(CloneNotSupportedException e) {
      // this shouldn't happen, since we are Cloneable
      throw new InternalError();
    }
  }

  /**
   * Closing a <tt>ByteArrayInputStream</tt> has no effect. The methods in this class
   * can be called after the stream has been closed without generating an <tt>IOException</tt>.
   *
   * @throws  IOException never
   */
  public void close() throws IOException {
    thePos = _length;
  }

  /**
   * Compares this byte array to another object.
   *
   * @param array  the array to be compared.
   *
   * @return   the value <code>0</code> if the argument is a byte array lexicographically
   *           equal to this byte array; a value less than <code>0</code> if the argument is a
   *           byte array lexicographically greater than this byte array; and a value greater
   *           than <code>0</code> if the argument is a byte array lexicographically less than
   *           this byte array.
   */
  public int compareTo(ByteArray array) {
    return (this == array)
           ? 0
           : Helper.compareTo(A, 0, _length, array.A, 0, array._length);
  }

  /**
   * Compares this byte array to another object. If the object is a byte array, this
   * function behaves like <code>compareTo(ByteArray)</code>. Otherwise, it throws a <code>ClassCastException</code>
   *
   * @param o  the <code>Object</code> to be compared.
   *
   * @return   the value <code>0</code> if the argument is a byte array lexicographically
   *           equal to this byte array; a value less than <code>0</code> if the argument is a
   *           byte array lexicographically greater than this byte array; and a value greater
   *           than <code>0</code> if the argument is a byte array lexicographically less than
   *           this byte array.
   */
  public int compareTo(Object o) {
    return compareTo((ByteArray) o);
  }

  /**
   * Returns <tt>true</tt> if this array contains the specified byte.
   *
   * @param e  byte whose presence in this List is to be tested.
   *
   * @return   <code>true</code> if the specified byte is present; <code>false</code>
   *           otherwise.
   */
  public boolean contains(byte e) {
    return indexOf(e) != -1;
  }

  /**
   * Copies the contents of this array into the specified array. The array must be
   * big enough to hold all the objects in this array, otherwise an <code>IndexOutOfBoundsException</code>
   * is thrown.
   *
   * @param a  the array into which the bytes get copied.
   */
  public void copyInto(byte[] a) {
    System.arraycopy(A, 0, a, 0, _length);
  }

  /**
   * Copies the specified number bytes of this array into the specified array.
   *
   * @param a    the array into which the bytes get copied.
   * @param pos  the starting position within the array
   * @param len  the number of bytes to copy
   *
   * @return   the actual number of bytes copied. This will differ from <code>len,</code>
   *           if <code>len</code> is greater than the number of bytes in the array.
   */
  public int copyInto(byte[] a, int pos, int len) {
    if (len > _length) {
      len = _length;
    }

    System.arraycopy(A, 0, a, pos, len);

    return len;
  }

  /**
   * Increases the capacity of this <tt>ByteArray</tt> instance, if necessary, to
   * ensure that it can hold at least the number of bytes specified by the minimum
   * capacity argument.
   *
   * @param len  the desired minimum capacity.
   */
  public void ensureCapacity(int len) {
    int olen = A.length;

    if (len > olen) {
      byte a[]         = A;
      int  newCapacity = (olen * 3) / 2 + 1;

      if (newCapacity < len) {
        newCapacity = len;
      }

      A = new byte[newCapacity];
      System.arraycopy(a, 0, A, 0, olen);
    }
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
  public boolean equals(ByteArray array) {
    return (this == array)
           ? true
           : ((array != null) && (array._length == _length) && Helper.regionMatches(A, 0, array.A, 0, _length));
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
    if (a instanceof ByteArray) {
      return equals((ByteArray) a);
    }

    return false;
  }

  /**
   * Returns the byte at the specified position in this array.
   *
   * @param pos  index of byte to return.
   *
   * @return   the byte at the specified position in this array.
   *
   * @throws   IndexOutOfBoundsException if index is out of range
   */
  public byte get(int pos) throws IndexOutOfBoundsException {
    checkRange(pos);

    return A[pos];
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
    if (charsetHelper != null) {
      out._length += charsetHelper.getChars(A, 0, _length, out, out._length);
    } else {
      out._length += ISO88591Helper.getInstance().getChars(A, 0, _length, out, out._length);
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
    out._length += csh.getChars(A, 0, _length, out, out._length);

    return out;
  }

  /**
   * Returns the hash code.
   *
   * @return   a hash code value for this object.
   */
  public int hashCode() {
    int    h   = 0;
    int    len = _length;
    int    i   = 0;
    byte[] a   = A;

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
   * @return   the index of the first occurrence of the byte ; returns <tt>-1</tt> if the
   *           byte is not found.
   */
  public int indexOf(byte b) {
    for (int i = 0; i < _length; i++) {
      if (A[i] == b) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Tests if this array is empty.
   *
   * @return   <tt>true</tt> if this array has no bytes; <tt>false</tt> otherwise.
   */
  public boolean isEmpty() {
    return _length == 0;
  }

  /**
   * Returns the index of the last occurrence of the specified byte in this array.
   *
   * @param b  the desired byte.
   *
   * @return   the index of the last occurrence of the specified byte in this array;
   *           returns -1 if the object is not found.
   */
  public int lastIndexOf(byte b) {
    for (int i = _length - 1; i >= 0; i--) {
      if (A[i] == b) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Set the current marked position in the stream. Buffer objects are marked at
   * position zero by default when constructed. They may be marked at another
   * position within the array by this method.
   * <p>If no mark has been set, then the value of the mark is the offset passed to
   * the constructor (or 0 if the offset was not supplied).
   *
   * @param readAheadLimit  the read ahead limit
   */
  public void mark(int readAheadLimit) {
    theMark = thePos;
  }

  /**
   * Tests if array supports mark/reset.
   *
   * @return   <code>true</code> if mark is supported; <code>false</code> otherwise
   */
  public boolean markSupported() {
    return true;
  }

  /**
   * Returns the next byte of data from this input stream without advancing the read pointer.
   * The value byte is returned as an <code>int</code> in the range <code>0</code> to <code>255</code>.
   * if no byte is available because the end of the stream has been reached, the value <code>-1</code>
   * is returned.
   *
   * @return   the next byte of data, or <code>-1</code> if the end of the stream has been
   *           reached.
   */
  public int peek() {
    return (thePos < _length)
           ? (A[thePos] & 0xff)
           : (-1);
  }

  /**
   * Returns the current position within the stream
   *
   * @return   the current position within the stream
   */
  public int position() {
    return thePos;
  }

  /**
   * Adds the specified byte to the end of the array
   *
   * @param e  the byte
   */
  public void push(byte e) {
    if (_length == A.length) {
      ensureCapacity(_length + 1);
    }

    A[_length++] = e;
  }

  /**
   * Reads the next byte of data from this input stream. The value byte is returned
   * as an <code>int</code> in the range <code>0</code> to <code>255</code>. If no
   * byte is available because the end of the stream has been reached, the value <code>-1</code>
   * is returned.
   *
   * @return   the next byte of data, or <code>-1</code> if the end of the stream has been
   *           reached.
   */
  public int read() {
    return (thePos < _length)
           ? (A[thePos++] & 0xff)
           : (-1);
  }

  /**
   * Returns an array containing all of the bytes starting from the current
   * stream position
   *
   * @param len the number of bytes to read
   *
   * @return an array containing all of the bytes starting from the current
   *   stream position
   */
  public byte[] read(int len) {
    if (len > (_length - thePos)) {
      len = _length - thePos;
    }

    if (len <= 0) {
      return new byte[0];
    }

    if (A.length == len) {
      return A;
    }

    byte[] b = new byte[len];

    System.arraycopy(A, thePos, b, 0, len);

    return b;
  }

  /**
   * Reads up to <code>len</code> bytes of data into an array of bytes from this
   * input stream. If <code>pos</code> equals <code>count</code>, then <code>-1</code>
   * is returned to indicate end of file. Otherwise, the number <code>k</code> of
   * bytes read is equal to the smaller of <code>len</code> and <code>count-pos</code>.
   * If <code>k</code> is positive, then bytes <code>buf[pos]</code> through <code>buf[pos+k-1]</code>
   * are copied into <code>b[off]</code> through <code>b[off+k-1]</code> in the
   * manner performed by <code>System.arraycopy</code>. The value <code>k</code> is
   * added into <code>pos</code> and <code>k</code> is returned.
   *
   * @param b    the array into which the data is read.
   * @param off  the start offset of the data.
   * @param len  the maximum number of bytes read.
   *
   * @return   the total number of bytes read into the array, or <code>-1</code> if there
   *           is no more data because the end of the stream has been reached.
   *
   */
  public int read(byte[] b, int off, int len) {
    if (b == null) {
      throw new NullPointerException();
    } else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
      throw new IndexOutOfBoundsException();
    }

    if (thePos >= _length) {
      return -1;
    }

    if ((thePos + len) > _length) {
      len = _length - thePos;
    }

    if (len <= 0) {
      return 0;
    }

    System.arraycopy(A, thePos, b, off, len);
    thePos += len;

    return len;
  }

  /**
   * Removes the specified byte from this array. Shifts any subsequent bytes to the
   * left (subtracts one from their indices).
   *
   * @param obj  the byte to remove
   */
  public void remove(byte obj) {
    int i = indexOf(obj);

    if (i != -1) {
      remove(i);
    }
  }

  /**
   * Removes the byte at the specified position in this array. Shifts any subsequent
   * bytes to the left (subtracts one from their indices).
   *
   * @param pos  the index of the byte to removed.
   *
   * @return   the byte that was removed from the array.
   */
  public byte remove(int pos) {
    checkRange(pos);

    if (pos == (_length - 1)) {
      _length--;

      return A[pos];
    } else {
      byte s = A[pos];

      _length--;
      System.arraycopy(A, pos + 1, A, pos, _length - pos);

      return s;
    }
  }

  /**
   * Removes the specified number of bytes starting at the specified position in this
   * array. Shifts any subsequent bytes to the left (subtracts one from their
   * indices).
   *
   * @param pos  the starting position within the array
   * @param len  the number of bytes to remove
   */
  public void remove(int pos, int len) {
    if (len < 1) {
      return;
    }

    checkRange(pos);

    if ((pos + len) == _length) {
      _length = 0;

      return;
    }

    if ((pos + len) >= _length) {
      _length = pos;
    } else {
      System.arraycopy(A, pos + len, A, pos, _length - (pos + len));
      _length -= len;
    }
  }

  /**
   * Resets the array to the marked position. The marked position is 0 unless another
   * position was marked or an offset was specified in the constructor.
   */
  public void reset() {
    thePos = theMark;
  }

  /**
   * Sets the contents of the byte array to the specified byte. The result is a
   * one-byte array that contains the specified byte.
   *
   * @param e  the byte
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(byte e) {
    _length = 1;
    A[0]    = e;
    theMark = 0;
    thePos  = 0;

    return this;
  }

  /**
   * Sets the contents of the byte array to the specified bytes
   *
   * @param e  the bytes
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(byte[] e) {
    return set(e, 0, (e == null)
                     ? 0
                     : e.length);
  }

  /**
   * Sets the contents of the byte array to the specified bytes
   *
   * @param e  the bytes
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(ByteArray e) {
    return set(e.A, 0, e._length);
  }

  /**
   * Sets the contents of the byte array to the specified bytes
   *
   * @param e  the bytes
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(ByteArrayHolder e) {
    return set(e.bytes, e.position, e.length);
  }

  /**
   * Sets the contents of the byte array to the bytes in the specified string
   *
   * @param e  the string
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(CharArray e) {
    theMark = 0;
    thePos  = 0;
    _length = 0;

    if (charsetHelper != null) {
      _length = charsetHelper.getBytes(e.A, 0, e._length, this, 0);
    } else {
      _length = ISO88591Helper.getInstance().getBytes(e.A, 0, e._length, this, 0);
    }

    return this;
  }

  /**
   * Sets the contents of the byte array to the bytes in the specified string
   *
   * @param e  the string
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(String e) {
    theMark = 0;
    thePos  = 0;
    _length = 0;

    if ((e != null) && (e.length() > 0)) {
      if (charsetHelper == null) {
        add(e.getBytes());
      } else {
        add(charsetHelper.getBytes(e));
      }
    }

    return this;
  }

  /**
   * Sets the contents of the byte array to the bytes in the specified string
   *
   * @param e    the string
   * @param csh  the character set helper
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(CharArray e, iCharsetHelper csh) {
    theMark = 0;
    thePos  = 0;
    _length = csh.getBytes(e.A, 0, e._length, this, 0);

    return this;
  }

  /**
   * Replaces the byte at the specified position in this array with the specified
   * byte.
   *
   * @param pos  index of byte to replace.
   * @param e    byte to be stored at the specified position.
   *
   * @return   the byte previously at the specified position.
   */
  public byte set(int pos, byte e) {
    checkRange(pos);

    byte o = A[pos];

    A[pos] = e;

    return o;
  }

  /**
   * Sets the contents of the byte array to the bytes in the specified string
   *
   * @param e    the string
   * @param csh  the character set helper
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(String e, iCharsetHelper csh) {
    theMark = 0;
    thePos  = 0;
    _length = 0;

    if ((e != null) && (e.length() > 0)) {
      add(csh.getBytes(e));
    }

    return this;
  }

  /**
   * Sets the contents of the byte array to the specified bytes
   *
   * @param e    the bytes
   * @param pos  the starting position within the array
   * @param len  the number of byte to copy
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(byte[] e, int pos, int len) {
    if (len > 0) {
      len = (len == -1)
            ? (e.length - pos)
            : len;
      ensureCapacity(len);
      System.arraycopy(e, pos, A, 0, len);
      _length = len;
    } else {
      _length = 0;
    }

    theMark = 0;
    thePos  = 0;

    return this;
  }

  /**
   * Sets the contents of the byte array to the specified characters
   *
   * @param e    the characters
   * @param pos  the starting position within the array
   * @param len  the number of characters to copy
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(char[] e, int pos, int len) {
    theMark = 0;
    thePos  = 0;

    if (charsetHelper != null) {
      _length = charsetHelper.getBytes(e, pos, len, this, 0);
    } else {
      _length = ISO88591Helper.getInstance().getBytes(e, pos, len, this, 0);
    }

    return this;
  }

  /**
   * Sets the contents of the byte array to the specified characters
   *
   * @param e    the characters
   * @param pos  the starting position within the array
   * @param len  the number of characters to copy
   * @param csh  the character set helper
   *
   * @return   a reference to this byte array.
   */
  public ByteArray set(char[] e, int pos, int len, iCharsetHelper csh) {
    theMark = 0;
    thePos  = 0;

    if (csh != null) {
      _length = csh.getBytes(e, pos, len, this, 0);
    } else {
      _length = ISO88591Helper.getInstance().getBytes(e, pos, len, this, 0);
    }

    return this;
  }

  /**
   * Set the encoding to use when converting to/from a string;
   *
   * @param csh  the character set helper
   */
  public void setCharsetHelper(iCharsetHelper csh) {
    charsetHelper = csh;
  }

  /**
   * Replaces the this array's array with the specified array
   *
   * @param e    the new array to use for the array
   * @param len  the length of the array's contents
   *
   * @return the previous byte array
   */
  public byte[] setEx(byte[] e, int len) {
    byte[] oa = A;

    A       = e;
    _length = (len == -1)
              ? (e.length - thePos)
              : len;
    theMark = 0;
    thePos  = 0;

    return oa;
  }

  /**
   * Returns the number of bytes in this array.
   *
   * @return   the number of bytes in this array.
   */
  public int size() {
    return _length;
  }

  /**
   * Skips <code>n</code> bytes of input from this input stream. Fewer bytes might be
   * skipped if the end of the input stream is reached. The actual number <code>k</code>
   * of bytes to be skipped is equal to the smaller of <code>n</code> and <code>count-pos</code>.
   * The value <code>k</code> is added into <code>pos</code> and <code>k</code> is
   * returned.
   *
   * @param n  the number of bytes to be skipped.
   *
   * @return   the actual number of bytes skipped.
   */
  public long skip(long n) {
    if ((thePos + n) > _length) {
      n = _length - thePos;
    }

    if (n < 0) {
      return 0;
    }

    thePos += n;

    return n;
  }

  /**
   * Tests to see if this object starts with the specified array
   *
   * @param ba  the array of bytes
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean startsWith(ByteArray ba) {
    if (_length < ba._length) {
      return false;
    }

    return Helper.regionMatches(A, 0, ba.A, 0, ba._length);
  }

  /**
   * Tests to see if this object starts with the specified array
   *
   * @param bah  the array of bytes
   *
   * @return   <code>true</code> if it does; <code>false</code> otherwise
   */
  public boolean startsWith(ByteArrayHolder bah) {
    if (_length < bah.length) {
      return false;
    }

    return Helper.regionMatches(A, 0, bah.bytes, bah.position, bah.length);
  }

  /**
   * Returns an array containing all of the bytes in this array in the correct order.
   *
   * @return   an array containing all of the bytes in this array in the correct order.
   */
  public byte[] toArray() {
    int len = _length;

    if (len == 0) {
      return new byte[0];
    }

    if (A.length == len) {
      return A;
    }

    byte[] b = new byte[len];

    System.arraycopy(A, thePos, b, 0, len);

    return b;
  }

  /**
   * Returns an array containing all of the bytes starting from the current stream position
   *
   * @return   an array containing all of the bytes starting from the current stream position
   */
  public byte[] toArrayEx() {
    int len = _length - thePos;

    if (len == 0) {
      return new byte[0];
    }

    if (A.length == len) {
      return A;
    }

    byte[] b = new byte[len];

    System.arraycopy(A, thePos, b, 0, len);

    return b;
  }

  /**
   * Returns a byte array holder containing all of the bytes in this array in the
   * correct order.
   *
   * @return   an array containing all of the bytes in this array in the correct order.
   */
  public ByteArrayHolder toByteArrayHolder() {
    return new ByteArrayHolder(toArray());
  }

  /**
   * Returns a string representation of the bytes in this array
   *
   * @return   a string representation of the bytes in this array
   */
  public String toString() {
    if (_length == 0) {
      return "";
    }

    if (charsetHelper == null) {
      return new String(A, 0, _length);
    }

    return charsetHelper.getString(A, 0, _length);
  }

  /**
   * Returns a string representation of the bytes in this array
   *
   * @param csh  the character set helper to use to convert bytes to characters
   *
   * @return   a string representation of the bytes in this array
   */
  public String toString(iCharsetHelper csh) {
    if (csh == null) {
      return new String(A, 0, _length);
    }

    return csh.getString(A, 0, _length);
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
    if (_length == 0) {
      return "";
    }

    ca.ensureCapacity(_length);

    char[] chars   = ca.A;
    int    charLen = csh.getChars(A, 0, _length, chars, 0);

    return new String(chars, 0, charLen);
  }

  /**
   * Trims the capacity of this <tt>ByteArray</tt> instance to be the array's current
   * size. An application can use this operation to minimize the storage of a <tt>ByteArray</tt>
   * instance.
   */
  public void trimToSize() {
    A = toArray();
  }

  /**
   * Un-reads a previously read character
   */
  public void unread() {
    if (thePos > 0) {
      thePos--;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param pos {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  private void checkRange(int pos) throws IndexOutOfBoundsException {
    if (pos >= _length) {
      throw new IndexOutOfBoundsException(pos + ">=" + _length);
    }

    if (pos < 0) {
      throw new IndexOutOfBoundsException(pos + "<0");
    }
  }
}
