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

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

import java.util.HashSet;
import java.util.Iterator;

/**
 * This class represent a performance sensitive version of
 * <code>java.lang.StringBuilder</code> specifically designed to handle
 * character sequences. It also incorporates some convenience methods from the
 * <code>java.lang.String</code> object Both the internal array (<b>A</b>) and
 * the list's length (<b>_length</b>) are exposed as public variables. This
 * allows direct manipulation of the lists contents. As such, care should be
 * taken when directly modifying either of these fields.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public final class CharArray extends Reader implements CharSequence, Cloneable {

  /**  */
  static final int expandBy = 16;

  /**  */
  static final int expandByHalf = 16;    // expandBy/2;

  /** the array that holds the contents */
  public char[] A;

  /** the length of the contents */
  public int _length;

  /** the read mark */
  private int theMark;

  /** the read position */
  private int                           thePos;
  private static HashSet                small_words             = new HashSet();
  private static char                   title_case_word_chars[] = {
    ',', '/', '|', '^', '&', '+', '=', '.'
  };
  private static int                    longest_small_word      = 3;
  private static ThreadLocal<CharArray> perThreadCharArray      = new ThreadLocal<CharArray>() {
    protected synchronized CharArray initialValue() {
      return new CharArray(16);
    }
  };
  private static ThreadLocal perThreadNumber = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new SNumber();
    }
  };

  static {
    small_words.add(new CharArray("a"));
    small_words.add(new CharArray("an"));
    small_words.add(new CharArray("and"));
    small_words.add(new CharArray("as"));
    small_words.add(new CharArray("but"));
    small_words.add(new CharArray("by"));
    small_words.add(new CharArray("en"));
    small_words.add(new CharArray("for"));
    small_words.add(new CharArray("if"));
    small_words.add(new CharArray("in"));
    small_words.add(new CharArray("is"));
    small_words.add(new CharArray("of"));
    small_words.add(new CharArray("on"));
    small_words.add(new CharArray("or"));
    small_words.add(new CharArray("the"));
    small_words.add(new CharArray("to"));
    small_words.add(new CharArray("v"));
    small_words.add(new CharArray("v."));
    small_words.add(new CharArray("vs"));
    small_words.add(new CharArray("vs."));
    small_words.add(new CharArray("via"));
    small_words.add(new CharArray("ii"));
    small_words.add(new CharArray("iii"));
    small_words.add(new CharArray("vi"));
    small_words.add(new CharArray("vii"));
    small_words.add(new CharArray("iv"));
    small_words.add(new CharArray("do"));
  }

  /**
   * Constructs a character array with no characters in it and an initial
   * capacity of 16 characters.
   */
  public CharArray() {
    A = new char[expandBy];
  }

  /**
   * Constructs a character array that it represents the sequence of characters
   * currently contained in the character array argument. The contents of the
   * character array are copied; subsequent modification of the character array
   * does not affect the newly created string.
   *
   * @param e
   *          the initial value of the string.
   */
  public CharArray(char[] e) {
    _length = e.length;
    A       = new char[_length];

    if (_length < 15) {
      int    i   = 0;
      int    len = _length;
      char[] a   = A;

      while(i < len) {
        a[i] = e[i];
        i++;
      }
    } else {
      System.arraycopy(e, 0, A, 0, _length);
    }
  }

  /**
   * Constructs a character array that contains characters from the specified
   * character array. The contents of the array are copied; subsequent
   * modification of the character array does not affect the newly created
   * string.
   *
   * @param e
   *          array that is the source of characters.
   */
  public CharArray(CharArray e) {
    _length = e._length;
    A       = new char[_length];

    if (_length < 15) {
      int    i   = 0;
      int    len = _length;
      char[] a   = A;
      char[] ea  = e.A;

      while(i < len) {
        a[i] = ea[i];
        i++;
      }
    } else {
      System.arraycopy(e.A, 0, A, 0, _length);
    }
  }

  /**
   * Constructs a character array with no characters in it and an initial
   * capacity specified by the <code>length</code> argument.
   *
   * @param len
   *          the initial capacity.
   */
  public CharArray(int len) {
    A = new char[len];
  }

  /**
   * Constructs a character array so that it represents the same sequence of
   * characters as the string argument; in other words, the initial contents of
   * the character array is a copy of the argument string.
   *
   * @param e
   *          the initial contents of the array.
   */
  public CharArray(String e) {
    _length = (e == null)
              ? 0
              : e.length();
    A       = new char[_length];

    if (e != null) {
      e.getChars(0, _length, A, 0);
    }
  }

  /**
   * Constructs a character array that it represents the sequence of characters
   * currently contained in the character array argument. The contents of the
   * character array are copied; subsequent modification of the character array
   * does not affect the newly created string.
   *
   * @param e
   *          array that is the source of characters.
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   */
  public CharArray(char[] e, int pos, int len) {
    _length = len;
    A       = new char[_length];

    if (_length < 9) {
      int    i = 0;
      char[] a = A;

      len += pos;

      while(pos < len) {
        a[i++] = e[pos++];
      }
    } else {
      System.arraycopy(e, pos, A, 0, len);
    }
  }

  private CharArray(char[] e, int len) {
    A       = e;
    _length = len;
  }

  /**
   * Gets the set of small words that will not be capitalized by the
   * toTitleCase() function
   *
   * @return the set of words(not synchronized)
   */
  public static HashSet getTitleCaseSmallWords() {
    return small_words;
  }

  /**
   * Sets the set of small words that will not be capitalized by the
   * toTitleCase() function
   *
   * @param words
   *          the has set of words (the set will be used as the new set and
   *          should nt be modified once passed in)
   */
  public static void setTitleCaseSmallWords(HashSet words) {
    small_words = words;

    int      len = 0;
    Iterator it  = small_words.iterator();

    while(it.hasNext()) {
      CharArray ca = (CharArray) it.next();

      if (ca.length() > len) {
        len = ca.length();
      }
    }

    longest_small_word = len;
  }

  /**
   * Sets the characters other than whitespace that will be used as word
   * separators
   *
   * @param chars
   *          the characters
   */
  public static void setTitleCaseWordSeparators(char[] chars) {
    title_case_word_chars = chars;
  }

  /**
   * Gets the characters other than whitespace that will be used as word
   * separators
   *
   * @param chars
   *          the characters
   */
  public static char[] getTitleCaseWordSeparators(char[] chars) {
    return title_case_word_chars;
  }

  /**
   * Appends the string representation of the <code>int</code> argument to this
   * character array. The argument is converted to a string and the characters
   * of that string are then appended to this character array.
   *
   * @param e
   *          an integer
   *
   * @return a reference to this character array.
   */
  public CharArray append(boolean e) {
    return append(String.valueOf(e));
  }

  /**
   * Appends the specified character to this character array.
   *
   * @param e
   *          the character
   *
   * @return a reference to this character array.
   */
  public CharArray append(char e) {
    if (_length == A.length) {
      expandCapacity(_length + 1);
    }

    A[_length++] = e;

    return this;
  }

  /**
   * Appends the specified character to this character array.
   *
   * @param e
   *          the character as an int
   *
   * @return a reference to this character array.
   */
  public CharArray appendChar(int e) {
    if (_length == A.length) {
      expandCapacity(_length + 1);
    }

    A[_length++] = (char) e;

    return this;
  }

  /**
   * Appends the specified characters to this character array.
   *
   * @param e
   *          the characters
   *
   * @return a reference to this character array.
   */
  public CharArray append(char[] e) {
    return append(e, 0, e.length);
  }

  /**
   * Appends the contents of the specified character array to this character
   * array.
   *
   * @param e
   *          the character array
   *
   * @return a reference to this object.
   */
  public CharArray append(CharArray e) {
    return append(e.A, 0, e._length);
  }

  /**
   * Appends the string representation of the <code>double</code> argument to
   * this character array. The argument is converted to a string and the
   * characters of that string are then appended to this character array.
   *
   * @param e
   *          a <code>double</code>
   *
   * @return a reference to this character array.
   */
  public CharArray append(double e) {
    return ((SNumber) perThreadNumber.get()).setValue(e).toString(this, true);
  }

  /**
   * Appends the string representation of the <code>int</code> argument to this
   * character array. The argument is converted to a string and the characters
   * of that string are then appended to this character array.
   *
   * @param e
   *          an <code>int</code>
   *
   * @return a reference to this character array.
   */
  public CharArray append(int e) {
    return ((SNumber) perThreadNumber.get()).setValue(e).toString(this, true);
  }

  /**
   * Appends the string representation of the <code>long</code> argument to this
   * character array. The argument is converted to a string and the characters
   * of that string are then appended to this character array.
   *
   * @param e
   *          a <code>long</code>
   *
   * @return a reference to this character array.
   */
  public CharArray append(long e) {
    return ((SNumber) perThreadNumber.get()).setValue(e).toString(this, true);
  }

  /**
   * Appends the specified object to this character array. The argument is
   * converted to a string and the characters of that string are then appended
   * to this character array.
   *
   * @param o
   *          the object
   *
   * @return a reference to this character array.
   */
  public CharArray append(Object o) {
    return append(o.toString());
  }

  /**
   * Appends the string to this character array.
   *
   * @param e
   *          a string.
   *
   * @return a reference to this character array.
   */
  public CharArray append(String e) {
    int len;

    if ((e != null) && (len = e.length()) != 0) {
      char A[]     = this.A;
      int  _length = this._length;
      int  nlen    = len + _length;

      if (nlen > A.length) {
        expandCapacity(nlen);
        A = this.A;
      }

      if (len < 3) {
        A[_length++] = e.charAt(0);

        if (len == 2) {
          A[_length++] = e.charAt(1);
        }
      } else {
        e.getChars(0, len, A, _length);
        _length = nlen;
      }

      this._length = _length;
    }

    return this;
  }

  /**
   * Appends the specified characters to this character array.
   *
   * @param e
   *          the characters
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this character array.
   */
  public CharArray append(char[] e, int pos, int len) {
    if (len > 0) {
      if (len == -1) {
        len = e.length - pos;
      }

      char A[]  = this.A;
      int  nlen = len + _length;

      if (nlen > A.length) {
        expandCapacity(nlen);
        A = this.A;
      }

      if (len < 9) {
        int i = _length;

        len += pos;

        while(pos < len) {
          A[i++] = e[pos++];
        }
      } else {
        System.arraycopy(e, pos, A, _length, len);
      }

      _length = nlen;
    }

    return this;
  }

  /**
   * Appends the contents of the specified character array to this character
   * array.
   *
   * @param e
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this object.
   */
  public CharArray append(CharArray e, int pos, int len) {
    return append(e.A, pos, len);
  }

  /**
   * Appends the string to this character array.
   *
   * @param e
   *          a string.
   * @param pos
   *          the starting position within the string
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this character array.
   */
  public CharArray append(String e, int pos, int len) {
    if ((e != null) && (len != 0)) {
      if (len == -1) {
        len = e.length() - pos;
      }

      if (len != 0) {
        char A[]     = this.A;
        int  _length = this._length;
        int  nlen    = len + _length;

        if (nlen > A.length) {
          expandCapacity(nlen);
          A = this.A;
        }

        if (len < 3) {
          A[_length++] = e.charAt(pos);

          if (len == 2) {
            A[_length++] = e.charAt(++pos);
          }
        } else {
          e.getChars(pos, pos + len, A, _length);
          _length = nlen;
        }

        this.A       = A;
        this._length = _length;
      }
    }

    return this;
  }

  /**
   * Appends characters starting at the specified position. The current
   * characters after that position are deleted
   *
   * @param index
   *          the starting position
   * @param e
   *          the characters to append
   *
   * @return a reference to this character array.
   */
  public CharArray appendAt(int index, CharArray e) {
    return appendAt(index, e.A, 0, e._length);
  }

  /**
   * Appends characters starting at the specified position. The current
   * characters after that position are deleted
   *
   * @param index
   *          the starting position
   * @param e
   *          the characters to append
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this character array.
   */
  public CharArray appendAt(int index, char[] e, int pos, int len) {
    _length = index;

    if (len == -1) {
      len = e.length - pos;
    }

    int nlen = len + _length;

    if (nlen > A.length) {
      expandCapacity(_length + len);
    }

    if (len == 1) {
      A[_length++] = e[pos];
    } else {
      System.arraycopy(e, pos, A, _length, len);
      _length = nlen;
    }

    return this;
  }

  /**
   * Appends the string representation of the <code>int</code> argument to this
   * character array. The argument is converted to a string and the characters
   * of that string are then appended to this character array.
   *
   * @param e
   *          an integer
   *
   * @return a reference to this character array.
   */
  public CharArray appendNum(int e) {
    return ((SNumber) perThreadNumber.get()).setValue(e).toString(this, true);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int available() {
    return (thePos < _length)
           ? (_length - thePos)
           : (0);
  }

  /**
   * Returns the current capacity of this character array.
   *
   * @return the current capacity
   */
  public int capacity() {
    return A.length;
  }

  /**
   * Removes the specified number of elements from the array
   *
   * @param num
   *          the number of elements to remove
   * @return the current length
   */
  public int chop(int num) {
    if (num > 0) {
      _length -= num;
    }

    if (_length < 0) {
      _length = 0;
    }

    return _length;
  }

  /**
   * Returns the character at the specified position within the array
   *
   * @param pos
   *          the index of the desired character.
   *
   * @return the character at the specified position of this character array.
   *
   * @throws IndexOutOfBoundsException
   *           If an error occurs
   */
  public char charAt(int pos) throws IndexOutOfBoundsException {
    checkRange(pos);

    return A[pos];
  }

  /*
   * Copies the specified array, truncating or padding with null characters (if
   * necessary) so the copy has the specified length. For all indices that are
   * valid in both the original array and the copy, the two arrays will contain
   * identical values. For any indices that are valid in the copy but not the
   * original, the copy will contain <tt>'\\u000'</tt>. Such indices will exist
   * if and only if the specified length is greater than that of the original
   * array.
   *
   * @param original the array to be copied
   *
   * @param newLength the length of the copy to be returned
   *
   * @return a copy of the original array, truncated or padded with null
   * characters to obtain the specified length
   *
   * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
   *
   * @throws NullPointerException if <tt>original</tt> is null
   */
  public static char[] copyOf(char[] original, int newLength) {
    char[] copy = new char[newLength];

    System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));

    return copy;
  }

  /**
   * Sets the lenth to zero and resets the read mark and position
   */
  public void clear() {
    _length = 0;
    thePos  = 0;
    theMark = 0;
  }

  /**
   * Returns a shallow copy of this list. (The elements themselves are not
   * copied.)
   *
   * @return a clone of this list.
   */
  public Object clone() {
    return new CharArray(A);
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  public void close() throws IOException {
    thePos = _length;
  }

  /**
   * Compares two character arrays lexicographically. The comparison is based on
   * the Unicode value of each character in the array.
   *
   * @param ca
   *          the <code>CharArray</code> to be compared.
   *
   * @return the value <code>0</code> if the argument string is equal to this
   *         string; a value less than <code>0</code> if this string is
   *         lexicographically less than the string argument; and a value
   *         greater than <code>0</code> if this string is lexicographically
   *         greater than the string argument.
   */
  public int compareTo(CharArray ca) {
    int    len1 = _length;
    int    len2 = ca._length;
    int    lim  = Math.min(len1, len2);
    char[] v1   = A;
    char[] v2   = ca.A;
    int    k    = 0;

    while(k < lim) {
      char c1 = v1[k];
      char c2 = v2[k];

      if (c1 != c2) {
        return c1 - c2;
      }

      k++;
    }

    return len1 - len2;
  }

  /**
   * Compares this character array to another object. If the object is a
   * character array, this function behaves like
   * <code>compareTo(CharArray)</code>. Otherwise, it throws a
   * <code>ClassCastException</code>
   *
   * @param o
   *          the <code>Object</code> to be compared.
   *
   * @return the value <code>0</code> if the argument is a character array
   *         lexicographically equal to this character array; a value less than
   *         <code>0</code> if the argument is a character array
   *         lexicographically greater than this character array; and a value
   *         greater than <code>0</code> if the argument is a character array
   *         lexicographically less than this character array.
   */
  public int compareTo(Object o) {
    return compareTo((CharArray) o);
  }

  /**
   * Removes the characters in a substring of this <code>CharArray</code>. The
   * substring begins at the specified <code>start</code> and extends to the
   * character at index <code>end - 1</code> or to the end of the
   * <code>CharArray</code> if no such character exists. If <code>start</code>
   * is equal to <code>end</code>, no changes are made.
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return This character array.
   */
  public CharArray remove(int start, int end) {
    checkRange(start);

    if ((end == -1) || (end > _length)) {
      end = _length;
    }

    int len = end - start;

    if (len > 0) {
      System.arraycopy(A, start + len, A, start, _length - end);
      _length -= len;
    }

    return this;
  }

  /**
   * Removes the character at the specified position in this
   * <code>CharArray</code> (shortening the <code>CharArray</code> by one
   * character).
   *
   * @param pos
   *          Index of character to remove
   *
   * @return This character array.
   */
  public CharArray deleteCharAt(int pos) {
    checkRange(pos);

    if (pos == (_length - 1)) {
      _length--;
    } else {
      _length--;
      System.arraycopy(A, pos + 1, A, pos, _length - pos);
    }

    return this;
  }

  /**
   * Encodes a integer and adds it to this array
   *
   * @param val
   *          the value
   */
  public void encodeVarInt(int val) {
    boolean neg = false;

    if (val < 0) {
      neg = true;
      val *= -1;
    }

    int l;

    if (val >= (1 << 16)) {
      l = 2;
    } else {
      l = 1;
    }

    int i = _length;

    if (i + l + 1 >= A.length) {
      expandCapacity(_length + l + 1);
    }

    if (neg) {
      A[i++] = (char) (l + 128);
    } else {
      A[i++] = (char) l;
    }

    if (l == 1) {
      A[i++] = (char) val;
    } else {
      A[i++] = (char) ((val >> 16) & 0xffff);
      A[i++] = (char) (val & 0xffff);
    }

    _length = i;
  }

  /**
   * Encodes an integer as a length specifier using a variable length encoding
   * and adds it to this array
   *
   * @param val
   *          the length value (use minus one (-1 ) for a null length)
   */
  public void encodeVarLength(int val) {
    int l;

    if (val < 0) {    // null
      if (_length + 1 >= A.length) {
        expandCapacity(_length + 1);
      }

      A[_length++] = (char) 32768;

      return;
    }

    if (val < 32768) {
      if (_length + 1 >= A.length) {
        expandCapacity(_length + 1);
      }

      A[_length++] = (char) val;

      return;
    }

    if (val >= (1 << 16)) {
      l = 2;
    } else {
      l = 1;
    }

    int i = _length;

    if (i + l + 1 >= A.length) {
      expandCapacity(_length + 1 + l);
    }

    A[i++] = (char) (l | 32768);

    if (l == 1) {
      A[i++] = (char) val;
    } else {
      A[i++] = (char) ((val >> 16) & 0xffff);
      A[i++] = (char) (val & 0xffff);
    }

    _length = i;
  }

  /**
   * Encodes a string and adds it to this array
   *
   * @param val
   *          the value
   */
  public void encodeVarString(String val) {
    if (val == null) {
      encodeVarLength(-1);
    } else {
      int len = val.length();

      encodeVarLength(len);

      if (len > 0) {
        append(val);
      }
    }
  }

  /**
   * Tests if this string ends with the specified suffix.
   *
   * @param suffix
   *          the suffix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a suffix of the character sequence represented by this
   *         object; <code>false</code> otherwise. Note that the result will be
   *         <code>true</code> if the argument is the empty string or is equal
   *         to this <code>String</code> object as determined by the
   *         {@link #equals(Object)} method.
   */
  public boolean endsWith(char[] suffix) {
    return startsWith(suffix, _length - suffix.length, 0, suffix.length);
  }

  /**
   * Tests if this string ends with the specified suffix.
   *
   * @param suffix
   *          the suffix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a suffix of the character sequence represented by this
   *         object; <code>false</code> otherwise. Note that the result will be
   *         <code>true</code> if the argument is the empty string or is equal
   *         to this <code>String</code> object as determined by the
   *         {@link #equals(Object)} method.
   */
  public boolean endsWith(CharArray suffix) {
    return startsWith(suffix.A, _length - suffix._length, 0, suffix._length);
  }

  /**
   * Tests if this string ends with the specified suffix.
   *
   * @param suffix
   *          the suffix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a suffix of the character sequence represented by this
   *         object; <code>false</code> otherwise. Note that the result will be
   *         <code>true</code> if the argument is the empty string or is equal
   *         to this <code>String</code> object as determined by the
   *         {@link #equals(Object)} method.
   */
  public boolean endsWith(String suffix) {
    return startsWith(suffix.toCharArray(), _length - suffix.length(), 0, suffix.length());
  }

  /**
   * Ensures that the capacity of the array is at least equal to the specified
   * minimum. If the current capacity of this character array is less than the
   * argument, then a new internal array is allocated with greater capacity.
   *
   * @param minimumCapacity
   *          the minimum desired capacity.
   */
  public void ensureCapacity(int minimumCapacity) {
    if (minimumCapacity > A.length) {
      expandCapacity(minimumCapacity);
    }
  }

  /**
   * Ensures that the capacity of the array is at least equal to the specified
   * length. If the current capacity of this character array is less than the
   * argument, then a new internal array is allocated with a capacity equal to
   * the specified length
   *
   * @param length
   *          the minimum desired capacity.
   */
  public void ensureLength(int length) {
    int len = A.length;

    if (length <= len) {
      return;
    }

    char newValue[] = new char[length];

    System.arraycopy(A, 0, newValue, 0, len);
    A = newValue;
  }

  /**
   * Compares this character array to another character array. The two are
   * considered equal if they are of the same length, and corresponding
   * characters in the two are identical.
   *
   * @param array
   *          the character array
   *
   * @return <code>true</code> if the argument is not <code>null</code> and the
   *         character arrays are equal; <code>false</code> otherwise.
   */
  public boolean equals(CharArray array) {
    if (array._length != _length) {
      return false;
    }

    int  i    = 0;
    char A2[] = array.A;

    while(i < _length) {
      if (A[i] != A2[i]) {
        return false;
      }

      i++;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param o
   *          {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean equals(Object o) {
    if (!(o instanceof CharArray)) {
      return false;
    }

    return equals((CharArray) o);
  }

  /**
   * Compares this character array to a set of characters. The two are
   * considered equal if they are of the same length, and corresponding
   * characters in the two are identical.
   *
   * @param e
   *          the characters
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to compare
   *
   * @return <code>true</code> if the argument is not <code>null</code> and the
   *         characters are equal; <code>false</code> otherwise.
   */
  public boolean equals(char[] e, int pos, int len) {
    if (len != _length) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (A[i] != e[i + pos]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Compares this character array to another character array, ignoring case
   * considerations. The two are considered equal ignoring case if they are of
   * the same length, and corresponding characters in the two strings are equal
   * ignoring case.
   *
   * @param otherBuffer
   *          the character array
   *
   * @return <code>true</code> if the argument is not <code>null</code> and the
   *         character array are equal, ignoring case; <code>false</code>
   *         otherwise.
   */
  public boolean equalsIgnoreCase(CharArray otherBuffer) {
    return (this == otherBuffer)
           ? true
           : ((otherBuffer != null) && (otherBuffer._length == _length)
              && regionMatches(true, 0, otherBuffer.A, 0, _length));
  }

  /**
   * Compares this character array to a string, ignoring case considerations.
   * The two character are considered equal ignoring case if they are of the
   * same length, and corresponding characters in the two strings are equal
   * ignoring case.
   *
   * @param aString
   *          the character array
   *
   * @return <code>true</code> if the argument is not <code>null</code> and the
   *         character array are equal, ignoring case; <code>false</code>
   *         otherwise.
   */
  public boolean equalsIgnoreCase(String aString) {
    return this.toString().equalsIgnoreCase(aString);
  }

  /**
   * Converts the character array to an array of bytes using the provided
   * character set handler
   *
   * @param csh
   *          the character set handler
   *
   * @return the array of bytes
   */
  public byte[] getBytes(iCharsetHelper csh) {
    ByteArray ba = new ByteArray(_length);

    ba._length = csh.getBytes(A, 0, _length, ba, 0);

    return ba.toArray();
  }

  /**
   * Converts the character array to an array of bytes using the provided
   * character set handler
   *
   * @param ba
   *          the byte array to use to store the results
   * @param csh
   *          the character set handler
   *
   * @return the number of bytes
   */
  public int getBytes(ByteArray ba, iCharsetHelper csh) {
    return csh.getBytes(A, 0, _length, ba, 0);
  }

  /**
   * Gets a piece of a string. The pieces are denoted by the specified token
   *
   * @param tok
   *          the token to use as a delimiter
   * @param piece
   *          the piece to retrieve (starting from one)
   *
   * @return the piece or an empty string if the piece does not exists
   */
  public final String getPiece(char tok, int piece) {
    int i  = 0;
    int n  = 1;
    int oi = 0;
    int pos;
    int tl = 1;

    if ((piece < 1) || (tl == 0)) {
      return null;
    }

    while((n < piece) && ((i = indexOf(tok, i)) != -1)) {
      i += tl;
      n++;
    }

    if ((n < piece) || (i == -1)) {
      return null;
    }

    oi = i;
    i  = indexOf(tok, i);

    if (i == -1) {
      return substring(oi);
    }

    pos = oi;
    i   += tl;

    while((n < piece) && ((i = indexOf(tok, i)) != -1)) {
      n++;
      i += tl;
    }

    if (i == -1) {
      return substring(pos);
    }

    return substring(pos, i - tl);
  }

  /**
   * Gets a token contained within this character array
   *
   * @param c
   *          the token
   * @param out
   *          the destination for the token
   * @param start
   *          the starting position within this array
   *
   * @return -1 if the token is not found or the position of the character
   *         following the token
   */
  public int getToken(char c, CharArray out, int start) {
    int n = indexOf(c, start);

    if (n == -1) {
      return -1;
    }

    out._length = 0;
    out.append(A, 0, n);

    return (n + 1);
  }

  /**
   * Gets a token contained within this character array
   *
   * @param s
   *          the token
   * @param out
   *          the destination for the token
   * @param start
   *          the starting position within this array
   *
   * @return -1 if the token is not found or the position of the character
   *         following the token
   */
  public int getToken(String s, CharArray out, int start) {
    int n = indexOf(s, start);

    if (n == -1) {
      return -1;
    }

    out._length = 0;
    out.append(A, 0, n);

    return (n + s.length());
  }

  /**
   * Returns the hash code.
   *
   * @return a hash code value for this object.
   */
  public final int hashCode() {
    int    h   = 0;
    int    i   = 0;
    int    len = _length;
    char[] a   = A;

    while(i < len) {
      h = (31 * h) + a[i++];
    }

    return h;
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character.
   *
   * @param e
   *          the character for which to search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified character, or -1 if the character is not found.
   */
  public int indexOf(char e) {
    final char[] v   = A;
    final int    len = _length;
    int          pos = 0;

    while(pos < len) {
      if (v[pos] == e) {
        return pos;
      }

      pos++;
    }

    return -1;
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character array, starting at the specified index.
   *
   * @param e
   *          the character for which to search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOf(CharArray e) {
    return indexOf(A, 0, _length, e.A, 0, e._length, 0);
  }

  /**
   * Returns the index, within this character array of the first occurrence of
   * the specified string.
   *
   * @param e
   *          the string for which to search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, or -1 if the string is not found.
   */
  public int indexOf(String e) {
    return indexOf(A, 0, _length, e.toCharArray(), 0, e.length(), 0);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character, starting at the specified index.
   *
   * @param e
   *          the character for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified character, starting at the specified index or -1 if the
   *         character is not found.
   */
  public int indexOf(char e, int fromIndex) {
    int max = _length;

    if (fromIndex < 0) {
      fromIndex = 0;
    } else if (fromIndex >= _length) {
      // Note: fromIndex might be near -1>>>1.
      return -1;
    }

    for (int i = fromIndex; i < max; i++) {
      if (A[i] == e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified set of characters, starting at the specified index.
   *
   * @param e
   *          the set of character for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOf(char[] e, int fromIndex) {
    return indexOf(A, 0, _length, e, 0, e.length, fromIndex);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character array, starting at the specified index.
   *
   * @param e
   *          the string for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOf(CharArray e, int fromIndex) {
    return indexOf(A, 0, _length, e.A, 0, e._length, fromIndex);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified string, starting at the specified index.
   *
   * @param e
   *          the string for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOf(String e, int fromIndex) {
    return indexOf(A, 0, _length, e.toCharArray(), 0, e.length(), fromIndex);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character, starting at the specified index.
   *
   * @param source
   *          the characters being searched.
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to insert
   * @param e
   *          the character for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified character, starting at the specified index or -1 if the
   *         character is not found.
   */
  public static int indexOf(char[] source, int pos, int len, char e, int fromIndex) {
    int max = pos + len;

    if (fromIndex < pos) {
      fromIndex = pos;
    } else if (fromIndex >= len) {
      return -1;
    }

    for (int i = fromIndex; i < max; i++) {
      if (source[i] == e) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Code shared by String and StringBuffer to do searches. The source is the
   * character array being searched, and the target is the string being searched
   * for.
   *
   * @param source
   *          the characters being searched.
   * @param sourceOffset
   *          offset of the source string.
   * @param sourceCount
   *          count of the source string.
   * @param target
   *          the characters being searched for.
   * @param targetOffset
   *          offset of the target string.
   * @param targetCount
   *          count of the target string.
   * @param fromIndex
   *          the index to begin searching from.
   *
   * @return the index of the first occurrence of the character in the character
   *         sequence represented by this object, or <code>-1</code> if the
   *         character does not occur.
   */
  public static int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset,
                            int targetCount, int fromIndex) {
    if (fromIndex >= sourceCount) {
      return ((targetCount == 0)
              ? sourceCount
              : (-1));
    }

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (targetCount == 0) {
      return fromIndex;
    }

    char first = target[targetOffset];
    int  i     = sourceOffset + fromIndex;
    int  max   = sourceOffset + (sourceCount - targetCount);

startSearchForFirstChar:
    while(true) {
      while((i <= max) && (source[i] != first)) {
        i++;
      }

      if (i > max) {
        return -1;
      }

      int j   = i + 1;
      int end = (j + targetCount) - 1;
      int k   = targetOffset + 1;

      while(j < end) {
        if (source[j++] != target[k++]) {
          i++;

          continue startSearchForFirstChar;
        }
      }

      return i - sourceOffset;    /* Found whole string. */
    }
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified set of characters (case-insensitive), starting at the
   * specified index.
   *
   * @param e
   *          the set of character for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOfIgnoreCase(char[] e, int fromIndex) {
    return indexOfIgnoreCase(A, 0, _length, e, 0, e.length, fromIndex);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified character array (case-insensitive), starting at the specified
   * index.
   *
   * @param e
   *          the string for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOfIgnoreCase(CharArray e, int fromIndex) {
    return indexOfIgnoreCase(A, 0, _length, e.A, 0, e._length, fromIndex);
  }

  /**
   * Returns the index, within this character array, of the first occurrence of
   * the specified string (case-insensitive), starting at the specified index.
   *
   * @param e
   *          the string for which to search.
   * @param fromIndex
   *          the index from which to start the search.
   *
   * @return the index within this string of the first occurrence of the
   *         specified substring, starting at the specified index or -1 if the
   *         string is not found.
   */
  public int indexOfIgnoreCase(String e, int fromIndex) {
    return indexOfIgnoreCase(A, 0, _length, e.toCharArray(), 0, e.length(), fromIndex);
  }

  /**
   * Code shared by String and StringBuffer to do searches. The source is the
   * character array being searched, and the target is the string being searched
   * for.
   *
   * @param source
   *          the characters being searched.
   * @param sourceOffset
   *          offset of the source string.
   * @param sourceCount
   *          count of the source string.
   * @param target
   *          the characters being searched for.
   * @param targetOffset
   *          offset of the target string.
   * @param targetCount
   *          count of the target string.
   * @param fromIndex
   *          the index to begin searching from.
   *
   * @return the index of the first occurrence of the character in the character
   *         sequence represented by this object, or <code>-1</code> if the
   *         character does not occur.
   */
  public static int indexOfIgnoreCase(char[] source, int sourceOffset, int sourceCount, char[] target,
          int targetOffset, int targetCount, int fromIndex) {
    if (fromIndex >= sourceCount) {
      return ((targetCount == 0)
              ? sourceCount
              : (-1));
    }

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (targetCount == 0) {
      return fromIndex;
    }

    char first = Character.toLowerCase(target[targetOffset]);
    int  i     = sourceOffset + fromIndex;
    int  max   = sourceOffset + (sourceCount - targetCount);

startSearchForFirstChar:
    while(true) {
      while((i <= max) && (Character.toLowerCase(source[i]) != first)) {
        i++;
      }

      if (i > max) {
        return -1;
      }

      int j   = i + 1;
      int end = (j + targetCount) - 1;
      int k   = targetOffset + 1;

      while(j < end) {
        if (Character.toLowerCase(source[j++]) != Character.toLowerCase(target[k++])) {
          i++;

          continue startSearchForFirstChar;
        }
      }

      return i - sourceOffset;    /* Found whole string. */
    }
  }

  /**
   * Inserts the specified character into this character array.
   *
   * @param index
   *          the position at which to insert the character
   * @param e
   *          the character.
   *
   * @return a reference to this character array.
   *
   */
  public CharArray insert(int index, char e) {
    if (index > _length) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + ">" + String.valueOf(_length));
    }

    if (index < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(index) + "<0");
    }

    if (_length == A.length) {
      expandCapacity(_length + 1);
    }

    if (index == _length) {
      A[_length++] = e;

      return this;
    }

    System.arraycopy(A, index, A, index + 1, _length - index);
    A[index] = e;
    _length++;

    return this;
  }

  /**
   * Inserts the specified set of characters into this character array.
   *
   * @param index
   *          the position at which to insert the characters
   * @param e
   *          the characters.
   *
   * @return a reference to this character array.
   */
  public CharArray insert(int index, char[] e) {
    return insert(index, e, 0, e.length);
  }

  /**
   * Inserts the specified set of characters into this character array.
   *
   * @param index
   *          the position at which to insert the characters
   * @param e
   *          the string containing the characters.
   *
   * @return a reference to this character array.
   */
  public CharArray insert(int index, String e) {
    if (e != null) {
      char[] chars = e.toCharArray();

      insert(index, chars, 0, chars.length);
    }

    return this;
  }

  /**
   * Inserts the specified set of characters into this character array.
   *
   * @param index
   *          the position at which to insert the characters
   * @param e
   *          the characters.
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to insert
   *
   * @return a reference to this character array.
   *
   */
  public CharArray insert(int index, char[] e, int pos, int len) {
    if (len != 0) {
      if ((index < 0) || (index > _length)) {
        throw new StringIndexOutOfBoundsException();
      }

      if ((pos < 0) || ((pos + len) < 0) || ((pos + len) > e.length)) {
        throw new StringIndexOutOfBoundsException(pos);
      }

      if (len < 0) {
        throw new StringIndexOutOfBoundsException(len);
      }

      int nlen = _length + len;

      if (nlen > A.length) {
        expandCapacity(nlen);
      }

      System.arraycopy(A, index, A, index + len, _length - index);
      System.arraycopy(e, pos, A, index, len);
      _length = nlen;
    }

    return this;
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character. That is, the index returned is the largest value
   * <i>k</i> such that: <blockquote>
   *
   * <pre>
   *      this.charAt(<i>k</i>) == ch
   * </pre>
   *
   * </blockquote> is true. The String is searched backwards starting at the
   * last character.
   *
   * @param ch
   *          a character.
   *
   * @return the index of the last occurrence of the character in the character
   *         sequence represented by this object, or <code>-1</code> if the
   *         character does not occur.
   */
  public int lastIndexOf(int ch) {
    final char[] v   = A;
    int          pos = _length;

    while(pos > 0) {
      if (v[--pos] == ch) {
        return pos;
      }
    }

    return -1;
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character, searching backward starting at the specified index.
   * That is, the index returned is the largest value <i>k</i> such that:
   * <blockquote>
   *
   * <pre>
   *      this.charAt(k) == ch) &amp;&amp; (k <= fromIndex)
   * </pre>
   *
   * </blockquote> is true.
   *
   * @param ch
   *          a character.
   * @param fromIndex
   *          the index to start the search from. There is no restriction on the
   *          value of <code>fromIndex</code>. If it is greater than or equal to
   *          the length of this string, it has the same effect as if it were
   *          equal to one less than the length of this string: this entire
   *          string may be searched. If it is negative, it has the same effect
   *          as if it were -1: -1 is returned.
   *
   * @return the index of the last occurrence of the character in the character
   *         sequence represented by this object that is less than or equal to
   *         <code>fromIndex</code>, or <code>-1</code> if the character does
   *         not occur before that point.
   */
  public int lastIndexOf(int ch, int fromIndex) {
    int    min = 0;
    char[] v   = A;

    for (int i = 0 + ((fromIndex >= _length)
                      ? (_length - 1)
                      : fromIndex); i >= min; i--) {
      if (v[i] == ch) {
        return i - 0;
      }
    }

    return -1;
  }

  /**
   * Removes leading white spaces from the array
   *
   * @return a string representation of the character array.
   */
  public CharArray leftTrim() {
    int start = 0;

    while((start < _length) && (A[start] <= ' ')) {
      start++;
    }

    if (start == _length) {
      _length = 0;

      return this;
    }

    int end = _length - 1;

    _length = end - start + 1;
    System.arraycopy(A, start, A, 0, _length);

    return this;
  }

  /**
   * Returns the length (character count) of this character array.
   *
   * @return the length of the sequence of characters currently represented by
   *         this character array.
   */
  public int length() {
    return _length;
  }

  /**
   * {@inheritDoc}
   *
   * @param readAheadLimit
   *          {@inheritDoc}
   */
  public void mark(int readAheadLimit) {
    theMark = thePos;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean markSupported() {
    return true;
  }

  /**
   * Returns the next character of data from this reader without advancing the
   * read pointer. The value char is returned as an <code>int</code>. If no
   * character is available because the end of the stream has been reached, the
   * value <code>-1</code> is returned.
   *
   * @return the next character, or <code>-1</code> if the end of the stream has
   *         been reached.
   */
  public int peek() {
    return (thePos < _length)
           ? (A[thePos])
           : (-1);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int read() {
    return (thePos < _length)
           ? (A[thePos++])
           : (-1);
  }

  /**
   * {@inheritDoc}
   *
   * @param c
   *          {@inheritDoc}
   * @param off
   *          {@inheritDoc}
   * @param len
   *          {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int read(char[] c, int off, int len) {
    if (c == null) {
      throw new NullPointerException();
    } else if ((off < 0) || (off > c.length) || (len < 0) || ((off + len) > c.length) || ((off + len) < 0)) {
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

    System.arraycopy(A, thePos, c, off, len);
    thePos += len;

    return len;
  }

  /**
   * Reads a regular string, starting at the current reader position and
   * continuing to the end of the array.
   *
   * @return the string or <code>null</code>
   */
  public String readAll() {
    int len = _length - thePos;

    if (len == -1) {
      return null;
    }

    if (len == 0) {
      return "";
    }

    String s = new String(A, thePos, len);

    thePos += len;

    return s;
  }

  /**
   * Returns the current read position
   *
   * @return the current read position;
   */
  public int readPoisition() {
    return thePos;
  }

  /**
   * Reads a regular string starting at the current reader position.
   *
   * @param len
   *          the number of characters to read
   *
   * @return the string or <code>null</code>
   */
  public String readString(int len) {
    if (len + thePos > _length) {
      len = _length - thePos;

      if (len == -1) {
        return null;
      }

      if (len == 0) {
        return "";
      }
    }

    String s = new String(A, thePos, len);

    thePos += len;

    return s;
  }

  /**
   * Extracts a variable length encoded integer from this array
   *
   * @return the integer
   * @throws IOException
   *           if an I/O error occurs
   */
  public int readVarInt() throws IOException {
    boolean neg = false;
    int     val = 0;
    int     n   = read();

    if (n > 128) {
      neg = true;
      n   -= 128;
    }

    if (n > 2) {
      throw new IOException("bad numeric value");
    }

    if (n == 1) {
      n = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
    } else {
      n = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
      val = val << 16;
      n   = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val += n & 0xffff;
    }

    if (neg) {
      val = -val;
    }

    return val;
  }

  /**
   * Extracts a variable length encoding from this array
   *
   * @return the integer representing the length valie
   * @throws IOException
   *           if an I/O error occurs
   */
  public int readVarLength() throws IOException {
    int val = 0;
    int n   = read();

    if (n == -1) {
      throw new EOFException("End of stream encountered");
    }

    if (n < 32768) {
      return n;
    }

    if (n == 32768) {
      return -1;
    }

    n -= 32768;

    if (n > 2) {
      throw new IOException("bad numeric value");
    }

    if (n == 1) {
      n = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
    } else {
      n = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
      val = val << 16;
      n   = read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val += n & 0xffff;
    }

    return val;
  }

  /**
   * Extracts a variable length encoded string from this array
   *
   * @return the string or <code>null</code>
   * @throws IOException
   *           if an I/O error occurs
   */
  public String readVarString() throws IOException {
    int len = aStreamer.readVarLength(this);

    if (len == -1) {
      return null;
    }

    if (len == 0) {
      return "";
    }

    if (len + thePos > _length) {
      len = _length - thePos;

      if (len == -1) {
        return null;
      }

      if (len == 0) {
        return "";
      }
    }

    String s = new String(A, thePos, len);

    thePos += len;

    return s;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean ready() {
    return thePos < _length;
  }

  /**
   * Tests if two character array regions are equal.
   *
   * @param ignoreCase
   *          if <code>true</code>, ignore case when comparing characters.
   * @param toffset
   *          the starting offset of the subregion in this string.
   * @param other
   *          the other character array.
   * @param ooffset
   *          the starting offset of the subregion in the string argument.
   * @param len
   *          the number of characters to compare.
   *
   * @return <code>true</code> if the specified subregion of this character
   *         array matches the specified subregion of the other character array;
   *         <code>false</code> otherwise. Whether the matching is exact or case
   *         insensitive depends on the <code>ignoreCase</code> argument.
   */
  public boolean regionMatches(boolean ignoreCase, int toffset, char[] other, int ooffset, int len) {
    char[] ta = A;
    int    to = +toffset;
    char[] pa = other;
    int    po = ooffset;

    // Note: toffset, ooffset, or len might be near -1>>>1.
    if ((ooffset < 0) || (toffset < 0) || (toffset > ((long) _length - len))
        || (ooffset > ((long) other.length - len))) {
      return false;
    }

    char c1;
    char c2;

    while(len-- > 0) {
      c1 = ta[to++];
      c2 = pa[po++];

      if (c1 == c2) {
        continue;
      }

      if (ignoreCase) {
        // If characters don't match but case may be ignored,
        // try converting both characters to uppercase.
        // If the results match, then the comparison scan should
        // continue.
        char u1 = Character.toUpperCase(c1);
        char u2 = Character.toUpperCase(c2);

        if (u1 == u2) {
          continue;
        }

        // Unfortunately, conversion to uppercase does not work properly
        // for the Georgian alphabet, which has strange rules about case
        // conversion. So we need to make one last check before
        // exiting.
        if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
          continue;
        }
      }

      return false;
    }

    return true;
  }

  /**
   * Removes the characters in a substring of this <code>CharArray</code>. The
   * substring begins at the specified <code>start</code> and extends to the
   * character at index <code>end - 1</code> or to the end of the
   * <code>CharArray</code> if no such character exists. If <code>start</code>
   * is equal to <code>end</code>, no changes are made.
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return This removed characters represented as a string.
   */
  public String removeString(int start, int end) {
    checkRange(start);

    String s = null;

    if ((end == -1) || (end > _length)) {
      end = _length;
    }

    int len = end - start;

    if (len > 0) {
      s = new String(A, start, len);
      System.arraycopy(A, start + len, A, start, _length - end);
      _length -= len;
    } else {
      s = "";
    }

    return s;
  }

  /**
   * Removes a token contained within this character array
   *
   * @param c
   *          the token
   * @param out
   *          the destination for the token
   *
   * @return <code>true</code> if the token was found and removed;
   *         <code>false</code> otherwise
   */
  public boolean removeToken(char c, CharArray out) {
    int n = indexOf(c);

    if (n == -1) {
      return false;
    }

    out._length = 0;
    out.append(A, 0, n);
    remove(0, n + 1);

    return true;
  }

  /**
   * Removes a token contained within this character array
   *
   * @param s
   *          the token
   * @param out
   *          the destination for the token
   *
   * @return <code>true</code> if the token was found and removed;
   *         <code>false</code> otherwise
   */
  public boolean removeToken(String s, CharArray out) {
    int n = indexOf(s);

    if (n == -1) {
      return false;
    }

    out._length = 0;
    out.append(A, 0, n);
    remove(0, n + s.length());

    return true;
  }

  /**
   * Replaces a specified character with another
   *
   * @param what
   *          the character to be replaced
   * @param with
   *          the new character
   *
   * @return a reference to this character array.
   */
  public CharArray replace(char what, char with) {
    for (int i = 0; i < _length; i++) {
      if (A[i] == what) {
        A[i] = with;
      }
    }

    return this;
  }

  /**
   * Removes linefeeds from the specified string
   *
   * @param str the string to remove the linefeeds from
   * @return a reference to this character array.
   */
  public static String removeLinefeeds(String str) {
    if ((str == null) || str.equals("")) {
      return str;
    }

    return perThreadCharArray.get().set(str).removeLinefeeds().toString();
  }

  /**
   * Removes linefeeds from the string
   *
   * @return a reference to this character array.
   */
  public CharArray removeLinefeeds() {
    int n = 0;

    for (int i = 0; i < _length; i++) {
      char c = A[i];

      if ((c != '\n') && (c != '\r')) {
        A[n++] = c;
      }
    }

    _length = n;

    return this;
  }

  public void reset() {
    thePos = theMark;
  }

  /**
   * The character sequence contained in this character array is replaced by the
   * reverse of the sequence.
   *
   * @return a reference to this character array.
   */
  public CharArray reverse() {
    int  n = _length - 1;
    char c;

    for (int i = (n - 1) >> 1; i >= 0; --i) {
      c        = A[i];
      A[i]     = A[n - i];
      A[n - i] = c;
    }

    return this;
  }

  /**
   * Removes trailing white spaces from the array
   *
   * @return a string representation of the character array.
   */
  public CharArray rightTrim() {
    int end = _length - 1;

    while((end > 0) && (A[end] <= ' ')) {
      end--;
    }

    _length = end + 1;
    System.arraycopy(A, 0, A, 0, _length);

    return this;
  }

  /**
   * Sets the contents of the character array to the specified character. The
   * result is a one-character length array that contains the specified
   * character.
   *
   * @param e
   *          the character
   *
   * @return a reference to this character array.
   */
  public CharArray set(char e) {
    _length = 1;
    A[0]    = e;
    thePos  = 0;
    theMark = 0;

    return this;
  }

  /**
   * Sets the contents of the character array to the specified characters
   *
   * @param e
   *          the characters
   *
   * @return a reference to this character array.
   */
  public CharArray set(char[] e) {
    _length = 0;
    thePos  = 0;
    theMark = 0;
    append(e, 0, e.length);

    return this;
  }

  /**
   * Sets the contents of the character array to the characters in the specified
   * string
   *
   * @param e
   *          the string
   *
   * @return a reference to this character array.
   */
  public CharArray set(CharArray e) {
    int len = 0;

    if ((e != null) && (len = e._length) != 0) {
      char A[] = this.A;

      if (len > A.length) {
        expandCapacity(len);
        A = this.A;
      }

      if (len < 3) {
        A[0] = e.A[0];

        if (len == 2) {
          A[1] = e.A[1];
        }
      } else {
        System.arraycopy(e.A, 0, A, 0, len);
      }
    }

    thePos  = 0;
    theMark = 0;
    _length = len;

    return this;
  }

  /**
   * Sets the contents of the character array to the characters in the specified
   * string
   *
   * @param e
   *          the string
   *
   * @return a reference to this character array.
   */
  public CharArray set(String e) {
    int len = 0;

    if ((e != null) && (len = e.length()) != 0) {
      char A[] = this.A;

      if (len > A.length) {
        expandCapacity(len);
        A = this.A;
      }

      if (len < 4) {
        A[0] = e.charAt(0);

        if (len == 2) {
          A[1] = e.charAt(1);
        } else if (len == 3) {
          A[1] = e.charAt(1);
          A[2] = e.charAt(2);
        }
      } else {
        e.getChars(0, len, A, 0);
      }
    }

    thePos  = 0;
    theMark = 0;
    _length = len;

    return this;
  }

  /**
   * Sets the contents of the character array to the specified characters
   *
   * @param e
   *          the characters
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this character array.
   */
  public CharArray set(char[] e, int pos, int len) {
    if ((e != null) && (len != 0)) {
      char A[] = this.A;

      if (len > A.length) {
        expandCapacity(len);
        A = this.A;
      }

      if (len < 3) {
        A[0] = e[pos];

        if (len == 2) {
          A[1] = e[pos + 1];
        }
      } else {
        System.arraycopy(e, pos, A, 0, len);
      }
    }

    thePos  = 0;
    theMark = 0;
    _length = len;

    return this;
  }

  /**
   * Sets the contents of the character array to the characters in the specified
   *
   * @param e
   *          the string
   * @param pos
   *          the starting position within the string
   * @param len
   *          the number of characters to copy
   *
   * @return a reference to this character array.
   */
  public CharArray set(String e, int pos, int len) {
    _length = 0;
    thePos  = 0;
    theMark = 0;

    if ((e != null) && (len != 0)) {
      if (len == -1) {
        len = e.length() - pos;
      }

      if (len != 0) {
        char A[]     = this.A;
        int  _length = this._length;
        int  nlen    = len + _length;

        if (nlen > A.length) {
          expandCapacity(nlen);
          A = this.A;
        }

        if (len < 3) {
          A[_length++] = e.charAt(pos);

          if (len == 2) {
            A[_length++] = e.charAt(++pos);
          }
        } else {
          e.getChars(pos, pos + len, A, _length);
          _length = nlen;
        }

        this.A       = A;
        this._length = _length;
      }
    }

    return this;
  }

  /**
   * Sets the contents of the character array to the specified bytes
   *
   * @param e
   *          the bytes
   *
   * @return a reference to this character array.
   */
  public CharArray set(byte[] e) {
    return set(e, 0, e.length, ISO88591Helper.getInstance());
  }

  /**
   * Sets the contents of the character array to the specified bytes
   *
   * @param e
   *          the bytes
   *
   * @param csh
   *          the character set handler
   * @return a reference to this character array.
   */
  public CharArray set(byte[] e, iCharsetHelper csh) {
    return set(e, 0, e.length, ISO88591Helper.getInstance());
  }

  /**
   * Sets the contents of the character array to the specified bytes
   *
   * @param e
   *          the bytes
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to copy
   * @param csh
   *          the character set handler
   *
   * @return a reference to this character array.
   */
  public CharArray set(byte[] e, int pos, int len, iCharsetHelper csh) {
    _length = csh.getChars(e, pos, len, this, 0);
    thePos  = 0;
    theMark = 0;

    return this;
  }

  /**
   * The character at the specified index of this character array is set to the
   * newly specified character.
   *
   * @param pos
   *          the position of the character to modify.
   * @param e
   *          the new character.
   *
   * @return a reference to this character array.
   */
  public CharArray setCharAt(int pos, char e) {
    checkRange(pos);
    A[pos] = e;

    return this;
  }

  /**
   * Sets the length of this character array. This character array is altered to
   * represent a new character sequence whose length is specified by the
   * argument. For every nonnegative index <i>k</i> less than
   * <code>newLength</code>, the character at index <i>k</i> in the new
   * character sequence is the same as the character at index <i>k</i> in the
   * old sequence if <i>k</i> is less than the length of the old character
   * sequence; otherwise, it is the null character <code>'\u0000'</code>. In
   * other words, if the <code>newLength</code> argument is less than the
   * current length of the character array, the character array is truncated to
   * contain exactly the number of characters given by the
   * <code>newLength</code> argument.
   * <p>
   * If the <code>newLength</code> argument is greater than or equal to the
   * current length, sufficient null characters (<code>'\u0000'</code>) are
   * appended to the character array so that length becomes the
   * <code>newLength</code> argument.
   * <p>
   * The <code>newLength</code> argument must be greater than or equal to
   * <code>0</code>.
   *
   * @param newLength
   *          the new length of the array.
   *
   * @return a reference to this character array.
   */
  public CharArray setLength(int newLength) {
    if (newLength > _length) {
      expandCapacity(newLength);
    }

    _length = newLength;

    return this;
  }

  /**
   * Shrinks the character array such that it contains the characters from
   * <code>start</code> to <code>end - 1</code>
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return a reference to this character array.
   */
  public CharArray shrinkTo(int start, int end) {
    checkRange(start);

    if ((end == -1) || (end > _length)) {
      end = _length;
    }

    int len = end - start;

    if (len > 0) {
      System.arraycopy(A, start, A, 0, len);
      _length = len;
    }

    return this;
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(char[] prefix) {
    return startsWith(prefix, 0, 0, prefix.length);
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(CharArray prefix) {
    return startsWith(prefix.A, 0, 0, prefix._length);
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(String prefix) {
    return startsWith(prefix.toCharArray(), 0, 0, prefix.length());
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   * @param toffset
   *          where to begin looking in the string.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(char[] prefix, int toffset) {
    return startsWith(prefix, toffset, 0, prefix.length);
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   * @param toffset
   *          where to begin looking in the string.
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(String prefix, int toffset) {
    return startsWith(prefix.toCharArray(), toffset, 0, prefix.length());
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   *
   * @param prefix
   *          the prefix.
   * @param toffset
   *          where to begin looking in the string.
   * @param poffset
   *          where to begin in the prefix.
   * @param plength
   *          number of characters of the prefix to use
   *
   * @return <code>true</code> if the character sequence represented by the
   *         argument is a prefix of the substring of this object starting at
   *         index <code>toffset</code>; <code>false</code> otherwise. The
   *         result is <code>false</code> if <code>toffset</code> is negative or
   *         greater than the length of this <code>String</code> object;
   *         otherwise the result is the same as the result of the expression
   *         <code>this.subString(toffset).startsWith(prefix)
   *           </code>
   */
  public boolean startsWith(char[] prefix, int toffset, int poffset, int plength) {
    char[] ta = A;
    int    to = toffset;
    int    po = poffset;
    int    pc = plength;

    // Note: toffset might be near -1>>>1.
    if ((toffset < 0) || (toffset > (_length - pc))) {
      return false;
    }

    while(--pc >= 0) {
      if (ta[to++] != prefix[po++]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Strips the specified bad characters from both ends of the array
   *
   * @param bad
   *          the characters to strip
   *
   * @return this array
   */
  public CharArray strip(char[] bad) {
    return strip(bad, true, true);
  }

  /**
   * Strips the specified bad characters from both ends of the array
   *
   * @param bad
   *          the characters to strip
   * @param left
   *          whether to strip from the left side
   * @param right
   *          whether to strip from the right side
   * @return this array
   */
  public CharArray strip(char[] bad, boolean left, boolean right) {
    char[] chars = A;
    int    pos   = 0;
    int    len   = _length;

    if ((len < 1) || (chars == null)) {
      return null;
    }

    int     i = pos;
    int     n;
    int     tok0 = 0;
    int     tok1;
    boolean badFound;
    int     max = pos + len;

    if (left) {
      for (i = pos; i < max; i++) {
        badFound = false;

        for (n = 0; n < bad.length; n++) {
          if (chars[i] == bad[n]) {
            badFound = true;

            break;
          }
        }

        if (!badFound && (chars[i] > 32)) {
          break;
        }
      }
    }

    tok0 = i;
    i    = max - 1;

    if (right) {
      for (i = max - 1; i > pos; i--) {
        badFound = false;

        for (n = 0; n < bad.length; n++) {
          if (chars[i] == bad[n]) {
            badFound = true;

            break;
          }
        }

        if (!badFound && (chars[i] > 32)) {
          break;
        }
      }
    }

    tok1 = (i + 1) - tok0;

    if (tok0 != 0) {
      remove(0, tok0);
    }

    _length = tok1;

    return this;
  }

  /**
   * Returns a new character sequence that contains a subsequence of characters
   * currently contained in this character array. The substring begins at the
   * specified <code>start</code> and extends to the character at index
   * <code>end - 1</code>.
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return The new character array .
   *
   */
  public CharSequence subSequence(int start, int end) {
    if (start < 0) {
      throw new StringIndexOutOfBoundsException(start);
    }

    if (end > _length) {
      throw new StringIndexOutOfBoundsException(end);
    }

    if (start > end) {
      throw new StringIndexOutOfBoundsException(end - start);
    }

    return new CharArray(A, start, end - start);
  }

  /**
   * Returns a new character array that contains a subsequence of characters
   * currently contained in this character array. The substring begins at the
   * specified <code>start</code> and extends to the character at index
   * <code>end - 1</code>.
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return The new character array .
   *
   */
  public CharArray subarray(int start, int end) {
    if (start < 0) {
      throw new StringIndexOutOfBoundsException(start);
    }

    if (end > _length) {
      throw new StringIndexOutOfBoundsException(end);
    }

    if (start > end) {
      throw new StringIndexOutOfBoundsException(end - start);
    }

    return new CharArray(A, start, end - start);
  }

  /**
   * Returns a new string that contains a subsequence of characters currently
   * contained in this character array. The substring begins at the specified
   * index and extends to the end of the character array.
   *
   * @param start
   *          The beginning index, inclusive.
   *
   * @return The new string.
   */
  public String substring(int start) {
    return substring(start, _length);
  }

  /**
   * Returns a new string that contains a subsequence of characters currently
   * contained in this character array. The substring begins at the specified
   * <code>start</code> and extends to the character at index
   * <code>end - 1</code>.
   *
   * @param start
   *          The beginning index, inclusive.
   * @param end
   *          The ending index, exclusive.
   *
   * @return The new string.
   *
   */
  public String substring(int start, int end) {
    if (start < 0) {
      throw new StringIndexOutOfBoundsException(start);
    }

    if (end > _length) {
      throw new StringIndexOutOfBoundsException(end);
    }

    if (start > end) {
      throw new StringIndexOutOfBoundsException(end - start);
    }

    int len = end - start;

    if (len == 0) {
      return "";
    }

    return (len == 1)
           ? StringCache.valueOf(A[start])
           : new String(A, start, len);
  }

  /**
   * Returns an array containing all of the characters in the array
   *
   * @return an array containing all of the characters in the array
   */
  public char[] toCharArray() {
    if (A.length == _length) {
      return A;
    }

    char[] b = new char[_length];

    System.arraycopy(A, 0, b, 0, _length);

    return b;
  }

  /**
   * Converts all of the characters in this character array to lower case using
   * the rules of the default locale.
   *
   * @return a string representation of the character array.
   */
  public CharArray toLowerCase() {
    for (int i = 0; i < _length; i++) {
      A[i] = Character.toLowerCase(A[i]);
    }

    return this;
  }

  /**
   * Converts the specified string to mixed case
   *
   * @param str
   *          the string to convert
   *
   * @return this converted string
   */
  public static String toMixedCase(String str) {
    if (str == null) {
      return null;
    }

    return perThreadCharArray.get().set(str).toTitleCase(title_case_word_chars).toString();
  }

  /**
   * Converts the contents to mixed case
   *
   * @return this array
   */
  public CharArray toMixedCase() {
    boolean next = true;

    for (int i = 0; i < _length; i++) {
      if (Character.isWhitespace(A[i])) {
        next = true;

        continue;
      }

      if (!Character.isLetter(A[i])) {
        if (next) {
          next = false;
          A[i] = Character.toUpperCase(A[i]);
        } else {
          A[i] = Character.toLowerCase(A[i]);
        }
      } else {
        if (next) {
          next = false;
          A[i] = Character.toUpperCase(A[i]);
        } else {
          A[i] = Character.toLowerCase(A[i]);
        }
      }
    }

    return this;
  }

  /*
   * For IOS porting
   */
  public String sequenceDescription() {
    return toString();
  }

  /**
   * Converts to a string representing the data in this character array.
   *
   * @return a string representation of the character array.
   */
  public String toString() {
    if (_length == 0) {
      return "";
    }

    return (_length == 1)
           ? StringCache.valueOf(A[0])
           : new String(A, 0, _length);
  }

  /**
   * Converts the character array to title case using the rules of the default
   * locale.
   *
   * @return a string representation of the character array.
   */
  public CharArray toTitleCase() {
    return toTitleCase(title_case_word_chars);
  }

  /**
   * Converts the specified string to title case using the rules of the default
   * locale.
   *
   * @param str
   *          the string to convert
   * @return the converted string
   */
  public static String toTitleCase(String str) {
    return perThreadCharArray.get().set(str).toTitleCase(title_case_word_chars).toString();
  }

  /**
   * Converts the character array to title case using the rules of the default
   * locale.
   *
   * @param wordchars
   *          characters other than whitespace to use as word separators
   * @return a string representation of the character array.
   */
  public CharArray toTitleCase(final char[] wordchars) {
    if (_length == 0) {
      return this;
    }

    CharArray ca    = (CharArray) perThreadCharArray.get();
    int       llen  = longest_small_word + 1;
    int       start = 0;
    char      ch;
    final int wordcharslen = (wordchars == null)
                             ? 0
                             : wordchars.length;
    int       len;
    int       calen = _length;

    for (int i = 0; i < calen; i++) {
      ch   = A[i];
      A[i] = Character.toLowerCase(ch);

      if ((i == start) &&!Character.isLetterOrDigit(ch)) {
        start++;

        continue;
      }

      if (Character.isWhitespace(ch)
          || ((wordcharslen > 0) && CharScanner.isTokenChar(0, ch, wordchars, wordcharslen))) {
        len = i - start;

        if ((len < llen) && (start != 0)) {
          ca.set(A, start, len);

          if (small_words.contains(ca)) {
            start = i + 1;

            continue;
          }
        }

        A[start] = Character.toTitleCase(A[start]);

        if (len == 2) {
          A[start + 1] = Character.toTitleCase(A[start + 1]);
        }

        start = i + 1;
      }
    }

    if (start < calen) {
      A[start] = Character.toTitleCase(A[start]);
    }

    return this;
  }

  public CharArray smart() {
    toLowerCase();

    if (_length > 0) {
      A[0] = Character.toUpperCase(A[0]);
    }

    return this;
  }

  public CharArray capatilize() {
    toLowerCase();

    if (_length > 0) {
      A[0] = Character.toUpperCase(A[0]);
    }

    return this;
  }

  /**
   * Converts all of the characters in this character array to upper case using
   * the rules of the default locale.
   *
   * @return a string representation of the character array.
   */
  public CharArray toUpperCase() {
    for (int i = 0; i < _length; i++) {
      A[i] = Character.toUpperCase(A[i]);
    }

    return this;
  }

  /**
   * Removes leading and trailing white spaces from the array
   *
   * @return a string representation of the character array.
   */
  public CharArray trim() {
    int start = 0;

    while((start < _length) && (A[start] <= ' ')) {
      start++;
    }

    if (start == _length) {
      _length = 0;

      return this;
    }

    int end = _length - 1;

    while((end > 0) && (A[end] <= ' ')) {
      end--;
    }

    _length = end - start + 1;
    System.arraycopy(A, start, A, 0, _length);

    return this;
  }

  /**
   * Trims the capacity of this <tt>CharArray</tt> instance to be the list's
   * current size. An application can use this operation to minimize the storage
   * of a <tt>CharArray</tt> instance.
   */
  public void trimToSize() {
    A = toCharArray();
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
   * Unescapes the string represented by the array
   *
   * @return this array
   */
  public CharArray unescape() {
    _length = CharScanner.unescapeStringEx(A, 0, _length);

    return this;
  }

  /**
   * Unescapes the quoteds string represented by the array
   *
   * @return thie array
   */
  public CharArray unescapeQuoted() {
    _length = CharScanner.cleanQuotedEx(A, 0, _length);

    return this;
  }

  /**
   * Wraps the specified array in a new CharArray object
   *
   * @param e
   *          the characters
   * @param len
   *          the number of characters to use
   * @return the new CharArray
   */
  public static CharArray wrap(char[] e, int len) {
    return new CharArray(e, len);
  }

  /**
   * This implements the expansion semantics of ensureCapacity with no size
   * check or synchronization.
   *
   * @param minimumCapacity
   *          the minimum capacity
   */
  void expandCapacity(int minimumCapacity) {
    int len         = A.length;
    int newCapacity = (len + 1) * 2;

    if (newCapacity < 0) {
      newCapacity = Integer.MAX_VALUE;
    } else if (minimumCapacity > newCapacity) {
      newCapacity = minimumCapacity;
    }

    char newValue[] = new char[newCapacity];

    System.arraycopy(A, 0, newValue, 0, len);
    A = newValue;
  }

  private void checkRange(int pos) throws IndexOutOfBoundsException {
    if (pos >= _length) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + ">" + String.valueOf(_length));
    }

    if (pos < 0) {
      throw new IndexOutOfBoundsException(String.valueOf(pos) + "<0");
    }
  }
}
