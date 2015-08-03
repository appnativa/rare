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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * The class provides generic functionality for reading and writing objects to a
 * stream. The generated output is not compatible with the java serialized format.
 * This format is compact and use variable-length encoding for specifying data
 * lengths.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public abstract class aStreamer implements iStreamable, Cloneable {

  /**  */
  static final long LONG_16BIT_VALUE = 0XFFFFL;

  /**  */
  static final long LONG_24BIT_VALUE = 0XFFFFFFL;

  /**  */
  static final long LONG_32BIT_VALUE = 0XFFFFFFFFL;

  /**  */
  static final long LONG_40BIT_VALUE = 0XFFFFFFFFFFL;

  /**  */
  static final long LONG_48BIT_VALUE = 0XFFFFFFFFFFFFL;

  /**  */
  static final long LONG_56BIT_VALUE = 0XFFFFFFFFFFFFFFL;

  /**  */
  static final long LONG_8BIT_VALUE = 0xFFL;

  /**  */
  static final char[] padding = Helper.getPadding();

  /**  */
  static final String lineSeparator = Helper.lineSeparator;

  /**  */
  static final String emptyString = "";

  /**  */
  static final boolean is64BitLong;

  static {
    int n = (int) (StrictMath.log(Long.MAX_VALUE) / StrictMath.log(16));

    if ((n % 2) > 0) {
      n++;
    }

    if (n > 16) {
      is64BitLong = true;
    } else {
      is64BitLong = false;
    }
  }

  /**
   * Decodes a 2-byte length encoded value from the specified bytes
   *
   * @param A the array to decode from
   * @param pos the starting position within the array
   * @return the length
   */
  public static final int decode2BLength(byte[] A, int pos) {
    int len = A[pos++] & 0xff;

    len <<= 8;
    len |= A[pos++] & 0xff;

    return (len >= 0xffff)
           ? -1
           : len;
  }

  /**
   * Decodes a 4-byte length encoded value from the specified bytes
   *
   * @param A the array to decode from
   * @param pos the starting position within the array
   * @return the length
   */
  public static final int decode4BLength(byte[] A, int pos) {
    int len = A[pos++] & 0xff;

    len <<= 8;
    len |= A[pos++] & 0xff;
    len <<= 8;
    len |= A[pos++] & 0xff;
    len <<= 8;
    len |= A[pos++] & 0xff;

    return (len < 0)
           ? -1
           : len;
  }

  /**
   * Encodes an integer as a length specifier using a 2 byte encoding.
   *
   * @param val the length value (use minus one (-1 ) for a null length)
   * @param out the output array
   * @param pos the starting position within the array
   * @see #decode2BLength
   */
  public static void encode2BLength(int val, byte[] out, int pos) {
    if (val < 0) {    // null
      val = 0xffff;
    }

    out[pos++] = (byte) ((val >> 8) & 0xff);
    out[pos++] = (byte) (val & 0xff);
  }

  /**
   * Encodes an integer as a length specifier using a 4 byte encoding.
   *
   * @param val  the length value (use minus one (-1 ) for a null length)
   * @param out  the output array
   * @param pos the starting position within the array
   *
   * @see   #decode4BLength
   */
  public static void encode4BLength(int val, byte[] out, int pos) {
    if (val < 0) {    // null
      val = 0xffffffff;
    }

    out[pos++] = (byte) ((val >> 24) & 0xff);
    out[pos++] = (byte) ((val >> 16) & 0xff);
    out[pos++] = (byte) ((val >> 8) & 0xff);
    out[pos++] = (byte) (val & 0xff);
  }

  /**
   * Encodes an integer as a length specifier using a variable length encoding.
   *
   * @param val the length value (use minus one (-1 ) for a null length)
   * @param out the output buffer
   * @see #readVarLength
   * @return the number of bytes used to encode the length
   */
  public static int encodeVarLength(int val, StringBuilder out) {
    int l;

    if (val < 0) {    // null
      out.append((char) 32768);

      return 1;
    }

    if (val < 32768) {
      out.append((char) val);

      return 1;
    }

    if (val >= (1L << 16)) {
      l = 2;
    } else {
      l = 1;
    }

    out.append((char) (l | 32768));

    if (l == 1) {
      out.append((char) val);
    } else {
      out.append((char) ((val >> 16) & 0xffff));
      out.append((char) (val & 0xffff));
    }

    return l + 1;
  }

  /**
   * Encodes an integer as a length specifier using a variable length encoding.
   *
   * @param val the length value (use minus one (-1 ) for a null length)
   * @param out the output buffer
   * @see #readVarLength
   * @return the number of bytes used to encode the length
   * @throws  IOException if an I/O error occurs
   */
  public static int encodeVarLength(int val, Writer out) throws IOException {
    int l;

    if (val < 0) {    // null
      out.append((char) 32768);

      return 1;
    }

    if (val < 32768) {
      out.append((char) val);

      return 1;
    }

    if (val >= (1L << 16)) {
      l = 2;
    } else {
      l = 1;
    }

    out.append((char) (l | 32768));

    if (l == 1) {
      out.append((char) val);
    } else {
      out.append((char) ((val >> 16) & 0xffff));
      out.append((char) (val & 0xffff));
    }

    return l + 1;
  }

  /**
   * Get the encoding length of an integer value
   *
   * @param val  the value
   * @return the number of bytes necessary to encode the value
   *
   */
  public static int getVarLength(int val) {
    int l;

    if (val >= (1L << 24)) {
      l = 4;
    } else if (val >= (1L << 16)) {
      l = 3;
    } else if (val >= (1L << 8)) {
      l = 2;
    } else {
      l = 1;
    }

    return l + 1;
  }

  /**
   * Get the encoding length of an long value
   *
   * @param val  the value
   * @return the number of bytes necessary to encode the value
   */
  public static int getVarLength(long val) {
    int l;

    if (is64BitLong) {
      if (val >= (1L << 56)) {
        l = 8;
      } else if (val >= (1L << 48)) {
        l = 7;
      } else if (val >= (1L << 40)) {
        l = 6;
      } else if (val >= (1L << 32)) {
        l = 5;
      } else if (val >= (1L << 24)) {
        l = 4;
      } else if (val >= (1L << 16)) {
        l = 3;
      } else if (val >= (1L << 8)) {
        l = 2;
      } else {
        l = 1;
      }
    } else {
      if (val >= (1L << 24)) {
        l = 4;
      } else if (val >= (1L << 16)) {
        l = 3;
      } else if (val >= (1L << 8)) {
        l = 2;
      } else {
        l = 1;
      }
    }

    return l + 1;
  }

  /**
   * Encodes an integer as a length specifier using a variable length encoding.
   *
   * @param val the length value (use minus one (-1 ) for a null length)
   * @param out the output array
   * @param pos the starting position within the array
   * @see #readVarLength
   * @return the number of bytes used to encode the length
   */
  public static int encodeVarLength(int val, byte[] out, int pos) {
    if (val < 0) {    // null
      out[pos] = ((byte) 128);

      return 1;
    }

    if (val < 128) {
      out[pos] = ((byte) val);

      return 1;
    }

    int l;

    if (val >= (1L << 24)) {
      l = 4;
    } else if (val >= (1L << 16)) {
      l = 3;
    } else if (val >= (1L << 8)) {
      l = 2;
    } else {
      l = 1;
    }

    out[pos++] = (byte) (128 | l);

    while(l > 0) {
      l--;
      out[pos++] = (byte) ((val >> (8 * l)) & 0xff);
    }

    return pos;
  }

  /**
   * Encodes an integer as a length specifier using a variable length encoding.
   *
   * @param val the length value (use minus one (-1 ) for a null length)
   * @param out the output array
   * @param pos the starting position within the array
   * @see #readVarLength
   * @return the number of bytes used to encode the length
   */
  public static int encodeVarLength(int val, char[] out, int pos) {
    if (val < 0) {    // null
      out[pos] = ((char) 128);

      return 1;
    }

    if (val < 128) {
      out[pos] = ((char) val);

      return 1;
    }

    int l;

    if (val >= (1L << 24)) {
      l = 4;
    } else if (val >= (1L << 16)) {
      l = 3;
    } else if (val >= (1L << 8)) {
      l = 2;
    } else {
      l = 1;
    }

    out[pos++] = (char) (128 | l);

    while(l > 0) {
      l--;
      out[pos++] = (char) ((val >> (8 * l)) & 0xff);
    }

    return pos;
  }

  /**
   * Populates the object's value from an input stream
   *
   * @param in  the input stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public void fromStream(InputStream in) throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * Reads a <code>boolean</code> value from an input stream
   *
   * @param val  this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *             being requested
   * @param in   the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static boolean fromStream(boolean val, InputStream in) throws IOException {
    return readBoolean(in);
  }

  /**
   * Reads a <code>booolean</code> array from an input stream
   *
   * @param a   this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *            being requested
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static boolean[] fromStream(boolean[] a, InputStream in) throws IOException {
    a = readBooleanArray(in);

    return a;
  }

  /**
   * Reads a <code>byte</code> array from an input stream
   *
   * @param a   this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *            being requested
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static byte[] fromStream(byte[] a, InputStream in) throws IOException {
    a = readBytes(in);

    return a;
  }

  /**
   * Reads an <code>in</code> value from an input stream
   *
   * @param val  this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *             being requested
   * @param in   the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static int fromStream(int val, InputStream in) throws IOException {
    return readInt(in);
  }

  /**
   * Reads an integer array from an input stream
   *
   * @param a   this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *            being requested
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static int[] fromStream(int[] a, InputStream in) throws IOException {
    a = readIntArray(in);

    return a;
  }

  /**
   * Reads a <code>iStreamable</code> object from an input stream
   *
   * @param what  the object to un-stream
   * @param in    the input stream
   *
   * @throws  IOException if and I/O error occurs
   */
  public static void fromStream(iStreamable what, InputStream in) throws IOException {
    what.fromStream(in);
  }

  /**
   * Reads a <code>long</code> value from an input stream
   *
   * @param val  this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *             being requested
   * @param in   the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static long fromStream(long val, InputStream in) throws IOException {
    return readLong(in);
  }

  /**
   * Reads a <code>long</code> array from an input stream
   *
   * @param a   this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *            being requested
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static long[] fromStream(long[] a, InputStream in) throws IOException {
    a = readLongArray(in);

    return a;
  }

  /**
   * Reads a <code>SNumber</code> value from an input stream
   *
   * @param num  the <code>SNumber</code> value to use to store the results
   * @param in   the input stream
   * @return   the <code>true</code> if a value was read; <code>false</code> otherwise
   *
   * @throws   IOException if an I/O error occurs
   */
  public static final boolean fromStream(SNumber num, InputStream in) throws IOException {
    if (!readBoolean(in)) {
      return false;
    }

    long man  = readLong(in);
    long frac = readLong(in);
    int  dec  = readInt(in);

    num.setValue(man, frac, dec);

    return true;
  }

  /**
   * Reads a <code>String</code> value from an input stream
   *
   * @param val  this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *             being requested
   * @param in   the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static String fromStream(String val, InputStream in) throws IOException {
    return readString(in);
  }

  /**
   * Reads a <code>String</code> array from an input stream
   *
   * @param a   this value is not referenced. It serves to identify the specific <code>fromStream()</code>
   *            being requested
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static String[] fromStream(String[] a, InputStream in) throws IOException {
    a = readStringArray(in, null);

    return a;
  }

  /**
   * Reads a set of bytes into the specified array
   *
   * @param in   the input stream
   * @param use  the byte array to use to store the results
   * @param len  the number of bytes to read
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int read(InputStream in, byte[] use, int len) throws IOException {
    int pos = 0;

    if (len > 0) {
      int n;

      n = in.read(use, 0, len);

      if (n == -1) {
        return 0;
      }

      len -= n;
      pos += n;

      while(len > 0) {
        n = in.read(use, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
        pos += n;
      }
    } else {
      if (len == -1) {
        return -1;
      }
    }

    return pos;
  }

  /**
   * Reads a set of bytes into the specified array
   *
   * @param in   the input stream
   * @param use  the byte array to use to store the results
   * @param len  the number of bytes to read
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int read(InputStream in, ByteArray use, int len) throws IOException {
    if (len > 0) {
      use.ensureCapacity(len);
      use._length = len;

      int n;
      int pos = 0;

      n = in.read(use.A, 0, use._length);

      if (n == -1) {
        return 0;
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(use.A, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
      }
    } else {
      if (len == -1) {
        return -1;
      }

      use._length = 0;
    }

    return use._length;
  }

  /**
   * Reads a array of bytes whose length is encoded as the first 2 bytes of the stream
   *
   * @param in   the input stream
   * @param use  the byte array to use to store the results
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int read2BBytes(InputStream in, ByteArray use) throws IOException {
    int len = aStreamer.read(in, use, 2);

    if (len != 2) {
      throw new EOFException("End of stream encountered");
    }

    len = aStreamer.decode2BLength(use.A, 0);

    if (len < 1) {
      return len;
    }

    if (len > 0) {
      use.ensureCapacity(len);
      use._length = len;

      int n;
      int pos = 0;

      n = in.read(use.A, pos, len);

      if (n == -1) {
        return 0;
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(use.A, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
      }
    } else {
      if (len == -1) {
        return -1;
      }

      use._length = 0;
    }

    return use._length;
  }

  /**
   * Reads a array of bytes whose length is encoded as the first 4 bytes of the stream
   *
   * @param in   the input stream
   * @param use  the byte array to use to store the results
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int read4BBytes(InputStream in, ByteArray use) throws IOException {
    int len = aStreamer.read(in, use, 4);

    if (len != 4) {
      throw new EOFException("End of stream encountered");
    }

    len = aStreamer.decode4BLength(use.A, 0);

    if (len < 1) {
      return len;
    }

    if (len > 0) {
      use.ensureCapacity(len);
      use._length = len;

      int n;
      int pos = 0;

      n = in.read(use.A, pos, len);

      if (n == -1) {
        return 0;
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(use.A, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
      }
    } else {
      if (len == -1) {
        return -1;
      }

      use._length = 0;
    }

    return use._length;
  }

  /**
   * Reads a <code>boolean</code> value from an input stream
   *
   * @param in  the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static boolean readBoolean(InputStream in) throws IOException {
    int val = (int) (in.read() & 0xff);

    return (val == 0)
           ? false
           : true;
  }

  /**
   * Reads a <code>boolean</code> array from an input stream
   *
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static boolean[] readBooleanArray(InputStream in) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    boolean[] a = new boolean[len];

    for (int i = 0; i < len; i++) {
      a[i] = readBoolean(in);
    }

    return a;
  }

  /**
   * Reads a <code>byte</code> array from an input stream
   *
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static byte[] readBytes(InputStream in) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    byte[] a = new byte[len];

    if (len > 0) {
      int n;
      int pos = 0;

      n = in.read(a, 0, len);

      if (n == -1) {
        throw new EOFException();
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(a, pos, len);

        if (n == -1) {
          throw new EOFException();
        }

        len -= n;
      }
    }

    return a;
  }

  /**
   * Reads a set of bytes into the specified array
   *
   * @param in   the input stream
   * @param use  the byte array to use to store the results
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int readBytes(InputStream in, ByteArray use) throws IOException {
    int len = readVarLength(in);

    if (len > 0) {
      use.ensureCapacity(len);
      use._length = len;

      int n;
      int pos = 0;

      n = in.read(use.A, pos, len);

      if (n == -1) {
        return 0;
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(use.A, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
      }
    } else {
      if (len == -1) {
        return -1;
      }

      use._length = 0;
    }

    return use._length;
  }

  /**
   * Reads a set of bytes and appends to the specified array
   *
   * @param in   the input stream
   * @param use  the byte array to use to append the results
   *
   * @return   the number of bytes read into the array
   *
   * @throws   IOException if an I/O error occurs
   */
  public static int readBytesEx(InputStream in, ByteArray use) throws IOException {
    int len = readVarLength(in);

    if (len > 0) {
      int pos = use._length;

      use.ensureCapacity(use._length + len);
      use._length += len;

      int n;

      n = in.read(use.A, pos, len);

      if (n == -1) {
        return 0;
      }

      len -= n;

      while(len > 0) {
        pos += n;
        n   = in.read(use.A, pos, len);

        if (n == -1) {
          return pos;
        }

        len -= n;
      }
    }

    return len;
  }

  /**
   * Reads a string from an input stream.
   *
   * @param out   the character array for the string stream
   * @param in   the input stream
   * @param csh  the character set helper
   *
   * @return   the string or <code>null</code>
   *
   * @throws   IOException if an I/O error occurs
   */
  public static final int readCharArray(CharArray out, InputStream in, iCharsetHelper csh) throws IOException {
    int len = readVarLength(in);

    out.clear();

    if (len == -1) {
      return -1;
    }

    if (len == 0) {
      return 0;
    }

    byte[] b = new byte[len];
    int    n;
    int    pos = 0;

    n = in.read(b, 0, b.length);

    if (n == -1) {
      return 0;
    }

    len -= n;

    while(len > 0) {
      pos += n;
      n   = in.read(b, pos, len);

      if (n == -1) {
        return pos;
      }

      len -= n;
    }

    if (csh == null) {
      out.set(b, 0, len, null);
    } else {
      out._length = csh.getChars(b, 0, b.length, out, 0);
    }

    return out._length;
  }

  /**
   * Reads an integer value from an input stream
   *
   * @param in  the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static final int readInt(InputStream in) throws IOException {
    int l = in.read();

    if (l == -1) {
      throw new EOFException("End of stream encountered");
    }

    l &= 0xff;

    boolean neg = false;

    if (l >= 128) {
      neg = true;
      l   -= 128;
    }

    if (l > 4) {
      throw new IOException("bad numeric value");
    }

    int num = 0;
    int n;

    for (int i = 0; i < l; i++) {
      num <<= 8;
      n   = in.read();

      if (n == -1) {
        throw new EOFException();
      }

      num |= (n & 0xff);
    }

    return neg
           ? (num * -1)
           : num;
  }

  /**
   * Reads an integer value from a reader
   *
   * @param in  the reader
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static final int readInt(Reader in) throws IOException {
    boolean neg = false;
    int     val = 0;
    int     n   = in.read();

    if (n > 128) {
      neg = true;
      n   -= 128;
    }

    if (n > 2) {
      throw new IOException("bad numeric value");
    }

    if (n == 1) {
      n = in.read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
    } else {
      n = in.read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
      val = val << 16;
      n   = in.read();

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
   * Reads an integer array from an input stream
   *
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static int[] readIntArray(InputStream in) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    int[] a = new int[len];

    for (int i = 0; i < len; i++) {
      a[i] = readInt(in);
    }

    return a;
  }

  /**
   * Reads a <code>long</code> value from an input stream
   *
   * @param in  the input stream
   *
   * @return   the value
   *
   * @throws   IOException if and I/O error occurs
   */
  public static long readLong(InputStream in) throws IOException {
    boolean neg = false;
    int     l   = in.read();

    if (l == -1) {
      throw new EOFException("End of stream encountered");
    }

    l &= 0xff;

    if (l >= 128) {
      l   -= 128;
      neg = true;
    }

    if (l > 8) {
      throw new IOException("bad numeric value");
    }

    long num = 0;
    int  n;

    for (int i = 0; i < l; i++) {
      num <<= 8;
      n   = in.read();

      if (n == -1) {
        throw new EOFException();
      }

      num |= (n & 0xff);
    }

    return neg
           ? (num * -1)
           : num;
  }

  /**
   * Reads a <code>long</code> array from an input stream
   *
   * @param in  the input stream
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static long[] readLongArray(InputStream in) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    long[] a = new long[len];

    for (int i = 0; i < len; i++) {
      a[i] = readLong(in);
    }

    return a;
  }

  /**
   * Reads a <code>SNumber</code> value from an input stream
   *
   * @param in   the input stream
   *
   * @return   the <code>SNumber</code> or <code>null</code>
   *
   * @throws   IOException if an I/O error occurs
   */
  public static final SNumber readSNumber(InputStream in) throws IOException {
    SNumber num = new SNumber();

    if (fromStream(num, in)) {
      return null;
    }

    return num;
  }

  /**
   * Reads a string from an input stream.
   *
   * @param in  the input stream
   *
   * @return   the string or <code>null</code>
   *
   * @throws   IOException if an I/O error occurs If an error occurs
   *
   * @see      #writeString
   */
  public static final String readString(InputStream in) throws IOException {
    return readString(in, null);
  }

  /**
   * Reads a string from a reader.
   *
   * @param in the reader
   * @return   the string or <code>null</code>
   * @throws   IOException if an I/O error occurs
   */
  public static final String readString(Reader in) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    if (len == 0) {
      return emptyString;
    }

    char[] chars = new char[len];
    int    n;
    int    pos = 0;

    n   = in.read(chars, 0, chars.length);
    len -= n;

    while(len > 0) {
      pos += n;
      n   = in.read(chars, pos, len);

      if (n == -1) {
        throw new EOFException();
      }

      len -= n;
    }

    return new String(chars);
  }

  /**
   * Reads a string from an input stream.
   *
   * @param in   the input stream
   * @param csh  the character set helper
   *
   * @return   the string or <code>null</code>
   *
   * @throws   IOException if an I/O error occurs
   */
  public static final String readString(InputStream in, iCharsetHelper csh) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    if (len == 0) {
      return emptyString;
    }

    byte[] b = new byte[len];
    int    n;
    int    pos = 0;

    n   = in.read(b, 0, b.length);
    len -= n;

    while(len > 0) {
      pos += n;
      n   = in.read(b, pos, len);

      if (n == -1) {
        throw new EOFException();
      }

      len -= n;
    }

    return (csh == null)
           ? new String(b)
           : csh.getString(b, 0, b.length);
  }

  /**
   * Reads a string from an input stream.
   * @param in the input stream
   * @param ba a <code>ByteArray</code> object to use to read in bytes
   * @param csh the character set helper
   * @return the string or <code>null</code>
   * @throws IOException if an I/O error occurs
   */
  public static final String readString(InputStream in, ByteArray ba, iCharsetHelper csh) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    if (len == 0) {
      return emptyString;
    }

    ba.ensureCapacity(len);
    ba._length = len;

    int n;
    int pos = 0;

    n   = in.read(ba.A, 0, ba._length);
    len -= n;

    while(len > 0) {
      pos += n;
      n   = in.read(ba.A, pos, len);

      if (n == -1) {
        throw new EOFException();
      }

      len -= n;
    }

    return (csh == null)
           ? ba.toString()
           : ba.toString(csh);
  }

  /**
   * Reads a <code>String</code> array from an input stream
   *
   * @param in   the input stream
   * @param csh  the character set helper
   *
   * @return   the array
   *
   * @throws   IOException if and I/O error occurs
   */
  public static String[] readStringArray(InputStream in, iCharsetHelper csh) throws IOException {
    int len = readVarLength(in);

    if (len == -1) {
      return null;
    }

    String[] a = new String[len];

    for (int i = 0; i < len; i++) {
      a[i] = readString(in, csh);
    }

    return a;
  }

  /**
   * Reads a variable length encoded value from a stream
   *
   * @param in  the input stream
   *
   * @return   the integer value. A value of minus one (-1) represents a null
   *
   * @throws   IOException if an I/O error occurs If an error occurs
   *
   * @see      #encodeVarLength
   */
  public static final int readVarLength(InputStream in) throws IOException {
    int len = in.read();

    if (len == -1) {
      throw new EOFException("End of stream encountered");
    }

    len &= 0xff;

    if (len < 128) {
      return len;
    }

    if (len == 128) {
      return -1;    // null
    }

    int l = (int) (len & 0x7f);

    if (l > 4) {
      throw new IOException("Improper length value encoding (" + l + ")");
    }

    len = 0;

    int n;

    for (int i = 0; i < l; i++) {
      len <<= 8;
      n   = in.read();

      if (n == -1) {
        throw new EOFException();
      }

      len |= (n & 0xff);
    }

    return len;
  }

  /**
   * Reads a variable length encoded value from a reader
   *
   * @param in  the reader
   *
   * @return   the integer value. A value of minus one (-1) represents a null
   *
   * @throws   IOException if an I/O error occurs If an error occurs
   *
   * @see      #encodeVarLength
   */
  public static final int readVarLength(Reader in) throws IOException {
    int val = 0;
    int n   = in.read();

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
      n = in.read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
    } else {
      n = in.read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val = n & 0xffff;
      val = val << 16;
      n   = in.read();

      if (n == -1) {
        throw new EOFException("End of stream encountered");
      }

      val += n & 0xffff;
    }

    return val;
  }

  /**
   * Reads a variable length encoded value from a stream
   *
   * @param in  the input stream
   * @param ba byte array to use for temporary byte storage
   *
   * @return   the integer value. A value of minus one (-1) represents a null
   *
   * @throws   IOException if an I/O error occurs If an error occurs
   *
   * @see      #encodeVarLength
   */
  public static final int readVarLength(InputStream in, ByteArray ba) throws IOException {
    int len = in.read();

    if (len == -1) {
      throw new EOFException("End of stream encountered");
    }

    len &= 0xff;

    if (len < 128) {
      return len;
    }

    if (len == 128) {
      return -1;    // null
    }

    int l = (int) (len & 0x7f);

    if (l > 4) {
      throw new IOException("Improper length value encoding (" + l + ")");
    }

    len = 0;

    int n;

    while((n = in.read(ba.A, 0, l)) != -1) {
      len += n;

      if (len == l) {
        break;
      }
    }

    len = 0;

    if (len != l) {
      throw new EOFException("End of stream encountered");
    }

    for (int i = 0; i < l; i++) {
      len <<= 8;
      len |= (ba.A[i] & 0xff);
    }

    return len;
  }

  /**
   * Writes the object's value out to an output stream
   *
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public void toStream(OutputStream out) throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * Outputs a <code>boolean</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(boolean val, OutputStream out) throws IOException {
    out.write(val
              ? 1
              : 0);
  }

  /**
   * Outputs a <code>boolean</code> array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(boolean[] a, OutputStream out) throws IOException {
    if (a == null) {
      writeVarLength(-1, out);

      return;
    }

    writeVarLength(a.length, out);

    for (int i = 0; i < a.length; i++) {
      toStream(a[i], out);
    }
  }

  /**
   * Outputs a <code>byte</code> array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(byte[] a, OutputStream out) throws IOException {
    if (a == null) {
      writeVarLength(-1, out);

      return;
    }

    writeVarLength(a.length, out);

    if (a.length > 0) {
      out.write(a);
    }
  }

  /**
   * Outputs a <code>int</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(int val, OutputStream out) throws IOException {
    writeInt(val, out);
  }

  /**
   * Outputs a integer array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(int[] a, OutputStream out) throws IOException {
    if (a == null) {
      writeVarLength(-1, out);

      return;
    }

    writeVarLength(a.length, out);

    for (int i = 0; i < a.length; i++) {
      toStream(a[i], out);
    }
  }

  /**
   * Outputs a <code>iStreamable</code> object to a stream
   *
   * @param what  the array
   * @param out   the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(iStreamable what, OutputStream out) throws IOException {
    what.toStream(out);
  }

  /**
   * Outputs a <code>long</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(long val, OutputStream out) throws IOException {
    boolean neg = false;

    if (val < 0) {
      neg = true;
      val *= -1;
    }

    int l;

    if (is64BitLong) {
      if (val >= (1L << 56)) {
        l = 8;
      } else if (val > LONG_48BIT_VALUE) {
        l = 7;
      } else if (val > LONG_40BIT_VALUE) {
        l = 6;
      } else if (val > LONG_32BIT_VALUE) {
        l = 5;
      } else if (val > LONG_24BIT_VALUE) {
        l = 4;
      } else if (val > LONG_16BIT_VALUE) {
        l = 3;
      } else if (val > LONG_8BIT_VALUE) {
        l = 2;
      } else {
        l = 1;
      }
    } else {
      if (val > LONG_24BIT_VALUE) {
        l = 4;
      } else if (val > LONG_16BIT_VALUE) {
        l = 3;
      } else if (val > LONG_8BIT_VALUE) {
        l = 2;
      } else {
        l = 1;
      }
    }

    if (neg) {
      out.write((byte) (l + 128));
    } else {
      out.write((byte) (l));
    }

    for (int i = l - 1; i >= 0; i--) {
      // buf[l - i] = (byte) ((val >> (8 * i)) & 0xff);
      out.write((byte) ((val >> (8 * i)) & 0xff));
    }
  }

  /**
   * Outputs a <code>long</code> array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(long[] a, OutputStream out) throws IOException {
    if (a == null) {
      writeVarLength(-1, out);

      return;
    }

    writeVarLength(a.length, out);

    for (int i = 0; i < a.length; i++) {
      toStream(a[i], out);
    }
  }

  /**
   * Outputs a <code>String</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(SNumber val, OutputStream out) throws IOException {
    if (val == null) {
      toStream(false, out);

      return;
    }

    toStream(true, out);
    toStream(val.longValue(), out);
    toStream(val.fractionalPart(), out);
    toStream(val.decimalPlaces(), out);
  }

  /**
   * Outputs a <code>String</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(String val, OutputStream out) throws IOException {
    if (val == null) {
      writeVarLength(-1, out);

      return;
    }

    byte[] b = val.getBytes();

    writeVarLength(b.length, out);
    out.write(b, 0, b.length);
  }

  /**
   * Outputs a <code>String</code> array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(String[] a, OutputStream out) throws IOException {
    toStream(a, out, null);
  }

  /**
   * Outputs a <code>String</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   * @param csh  the character set helper
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(String val, OutputStream out, iCharsetHelper csh) throws IOException {
    if (val == null) {
      writeVarLength(-1, out);

      return;
    }

    if (val.length() == 0) {
      writeVarLength(0, out);

      return;
    }

    byte[] b = (csh == null)
               ? val.getBytes()
               : csh.getBytes(val);

    writeVarLength(b.length, out);
    out.write(b, 0, b.length);
  }

  /**
   * Outputs a <code>String</code> array to a stream
   *
   * @param a    the array
   * @param out  the output stream
   * @param csh  the character set helper
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void toStream(String[] a, OutputStream out, iCharsetHelper csh) throws IOException {
    if (a == null) {
      writeVarLength(-1, out);

      return;
    }

    writeVarLength(a.length, out);

    for (int i = 0; i < a.length; i++) {
      writeString(a[i], out, csh);
    }
  }

  /**
   * Returns a string representation of the object
   *
   *
   * @param val {@inheritDoc}
   * @param depth  the depth of the padding to prepend to the string (1=two spaces)
   *
   * @return   the string representation of the object
   * public String toStringEx(int depth) {
   *   StringBuilder sb=new StringBuilder();
   *   writePadding(sb,depth);
   *  return sb.toString();
   * }
   */

  /**
   * Returns a string representation of the object
   *
   * @param depth  the padding depth
   *
   * @return   the string representation of the object
   * public static String toStringEx(int depth) {
   * return emptyString;
   * }
   */

  /**
   * Converts an array of integers to a single multi-lined string
   *
   * @param val    the strings
   * @param depth  the padding depth
   *
   * @return   the string
   */
  public static String toStringEx(int[] val, int depth) {
    if (val == null) {
      return emptyString;
    }

    StringBuilder s = new StringBuilder();

    for (int i = 0; i < val.length; i++) {
      writePadding(s, depth);
      s.append(val[i]);
      s.append(lineSeparator);
    }

    return s.toString();
  }

  /**
   * Converts an array of longs to a single multi-lined string
   *
   * @param val    the strings
   * @param depth  the padding depth
   *
   * @return   the string
   */
  public static String toStringEx(long[] val, int depth) {
    if (val == null) {
      return emptyString;
    }

    StringBuilder s = new StringBuilder();

    for (int i = 0; i < val.length; i++) {
      writePadding(s, depth);
      s.append(val[i]);
      s.append(lineSeparator);
    }

    return s.toString();
  }

  /**
   * Converts an array of strings to a single multi-lined string
   *
   * @param val    the strings
   * @param depth  the padding depth
   *
   * @return   the string
   */
  public static String toStringEx(String[] val, int depth) {
    if (val == null) {
      return emptyString;
    }

    StringBuilder s = new StringBuilder();

    for (int i = 0; i < val.length; i++) {
      writePadding(s, depth);
      s.append(val[i]);
      s.append(lineSeparator);
    }

    return s.toString();
  }

  /**
   * Outputs a set of bytes to an <code>ByteArray</code> object.
   *
   * @param out  the <code>ByteArray</code> object
   * @param b    the byte array
   *
   * @throws  IOException if an I/O error occurs
   */
  public static void writeBytes(ByteArray out, byte[] b) throws IOException {
    if (b == null) {
      encodeVarLength(-1, out.A, 0);

      return;
    }

    int len = b.length;

    out.ensureCapacity(len + 6);
    encodeVarLength(len, out.A, 0);
    out.add(b, 0, len);
  }

  /**
   * Outputs a set of bytes to an <code>ByteArray</code> object.
   *
   * @param out  the <code>ByteArray</code> object
   * @param b    the byte array
   * @param pos  the starting position
   * @param len  the number of bytes to write
   * @return the number of bytes written
   */
  public static int writeBytes(ByteArray out, byte[] b, int pos, int len) {
    if (b == null) {
      encodeVarLength(-1, out.A, 0);

      return 1;
    }

    out.ensureCapacity(len + 6);

    int n = encodeVarLength(len, out.A, 0);

    out.add(b, pos, len);

    return n + len;
  }

  /**
   * Outputs a set of bytes to an output stream.
   *
   * @param out  the output stream
   * @param b    the byte array
   * @param pos  the starting position
   * @param len  the number of bytes to write
   *
   * @return the number of bytes written
   * @throws  IOException if an I/O error occurs
   */
  public static int writeBytes(OutputStream out, byte[] b, int pos, int len) throws IOException {
    if (b == null) {
      writeVarLength(-1, out);

      return 1;
    }

    int n = writeVarLength(len, out);

    out.write(b, pos, len);

    return n + len;
  }

  /**
   * Write a integer value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @return the number of bytes written
   * @throws  IOException if an I/O error occurs
   *
   */
  public static int writeInt(int val, OutputStream out) throws IOException {
    boolean neg = false;

    if (val < 0) {
      neg = true;
      val *= -1;
    }

    int l;

    if (val >= (1L << 24)) {
      l = 4;
    } else if (val >= (1L << 16)) {
      l = 3;
    } else if (val >= (1L << 8)) {
      l = 2;
    } else {
      l = 1;
    }

    if (neg) {
      out.write((byte) (l + 128));
    } else {
      out.write((byte) l);
    }

    for (int i = l - 1; i >= 0; i--) {
      out.write((byte) ((val >> (8 * i)) & 0xff));
    }

    return l + 1;
  }

  /**
   * Write a integer value to a writer
   *
   * @param val  the value
   * @param out  the writer
   *
   * @return the number of characters written
   * @throws  IOException if an I/O error occurs
   */
  public static int writeInt(int val, Writer out) throws IOException {
    boolean neg = false;

    if (val < 0) {
      neg = true;
      val *= -1;
    }

    int l;

    if (val >= (1L << 16)) {
      l = 2;
    } else {
      l = 1;
    }

    if (neg) {
      out.append((char) (l + 128));
    } else {
      out.append((char) l);
    }

    if (l == 1) {
      out.append((char) val);
    } else {
      out.append((char) ((val >> 16) & 0xffff));
      out.append((char) (val & 0xffff));
    }

    return l + 1;
  }

  /**
   * Write a long value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @return the number of bytes written
   * @throws  IOException if an I/O error occurs
   */
  public static int writeLong(long val, OutputStream out) throws IOException {
    boolean neg = false;

    if (val < 0) {
      neg = true;
      val *= -1;
    }

    int l;

    if (is64BitLong) {
      if (val >= (1L << 56)) {
        l = 8;
      } else if (val >= (1L << 48)) {
        l = 7;
      } else if (val >= (1L << 40)) {
        l = 6;
      } else if (val >= (1L << 32)) {
        l = 5;
      } else if (val >= (1L << 24)) {
        l = 4;
      } else if (val >= (1L << 16)) {
        l = 3;
      } else if (val >= (1L << 8)) {
        l = 2;
      } else {
        l = 1;
      }
    } else {
      if (val >= (1L << 24)) {
        l = 4;
      } else if (val >= (1L << 16)) {
        l = 3;
      } else if (val >= (1L << 8)) {
        l = 2;
      } else {
        l = 1;
      }
    }

    if (neg) {
      out.write((byte) (l + 128));
    } else {
      out.write((byte) (l));
    }

    for (int i = l - 1; i >= 0; i--) {
      out.write((byte) ((val >> (8 * i)) & 0xff));
    }

    return l + 1;
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out    the buffer to output to
   * @param depth  the depth for the requested padding
   */
  public static void writePadding(CharArray out, int depth) {
    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth *= 2;

    while(depth > len) {
      out.append(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.append(padding, 0, depth);
    }
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out    the buffer to output to
   * @param depth  the depth for the requested padding
   */
  public static void writePadding(StringBuilder out, int depth) {
    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth *= 2;

    while(depth > len) {
      out.append(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.append(padding, 0, depth);
    }
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth unit.
   *
   * @param out    the writer to output to
   * @param depth  the depth for the requested padding
   *
   * @throws  IOException If an I/O error occurs
   */
  public static void writePadding(Writer out, int depth) throws IOException {
    if (depth == 0) {
      return;
    }

    int len = padding.length;

    depth *= 2;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  /**
   * Outputs a <code>String</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @throws  IOException if an I/O error occurs If an error occurs
   *
   * @see     #readString
   */
  public static void writeString(String val, OutputStream out) throws IOException {
    toStream(val, out);
  }

  /**
   * Outputs a <code>String</code> value to a writer
   *
   * @param val  the value
   * @param out  the writer
   *
   * @throws  IOException if an I/O error occurs If an error occurs
   *
   */
  public static void writeString(String val, Writer out) throws IOException {
    int len = (val == null)
              ? -1
              : (int) val.length();

    encodeVarLength(len, out);

    if (len > 0) {
      out.write(val);
    }
  }

  /**
   * Outputs a <code>String</code> value to a stream
   *
   * @param val  the value
   * @param out  the output stream
   * @param csh  the character set helper
   *
   * @throws  IOException if an I/O error occurs If an error occurs
   *
   * @see     #readString
   */
  public static void writeString(String val, OutputStream out, iCharsetHelper csh) throws IOException {
    toStream(val, out, csh);
  }

  /**
   * Outputs a <code>String</code> value to a writer
   *
   * @param val  the value
   * @param pos  the starting position
   * @param len  the number of characters to write
   * @param out  the writer
   *
   * @throws  IOException if an I/O error occurs If an error occurs
   *
   */
  public static void writeString(char val[], int pos, int len, Writer out) throws IOException {
    encodeVarLength(len, out);

    if (len > 0) {
      out.write(val, pos, len);
    }
  }

  /**
   * Writes an unsigned integer value out to a stream using a variable length
   * encoding. This function is used to encode length values (which are >=0)
   * A value less than zero (0) is encoded a null
   *
   * @param val  the value
   * @param out  the output stream
   *
   * @return the number of bytes written
   * @throws  IOException if an I/O error occurs If an error occurs
   *
   */
  public static int writeVarLength(int val, OutputStream out) throws IOException {
    if (val < 0) {    // null
      out.write((byte) 128);

      return 1;
    }

    if (val < 128) {
      out.write((byte) val);

      return 1;
    }

    int l;

    if (val >= (1L << 24)) {
      l = 4;
    } else if (val >= (1L << 16)) {
      l = 3;
    } else if (val >= (1L << 8)) {
      l = 2;
    } else {
      l = 1;
    }

    out.write((byte) (128 | l));

    for (int i = l - 1; i >= 0; i--) {
      // buf[l - i] = (byte) ((val >> (8 * i)) & 0xff);
      out.write((byte) ((val >> (8 * i)) & 0xff));
    }

    return l + 1;
  }
}
