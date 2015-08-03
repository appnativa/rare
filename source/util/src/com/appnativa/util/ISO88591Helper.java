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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Helper for the ISO 8859-1 character set
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public final class ISO88591Helper implements iCharsetHelper {
  private static byte[]      ZERO_LENGTH_BYTES  = {};
  private static String      ZERO_LENGTH_STRING = "";
  private static ThreadLocal perThreadCABuffer  = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };
  private static ISO88591Helper instance;

  // Per-thread buffer for string/stringbuffer conversion
  private ISO88591Helper() {}

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public iCharsetHelper copy() {
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param pos {@inheritDoc}
   * @param len {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final int getByteLength(char[] chars, int pos, int len) {
    return len;
  }

  /**
   * {@inheritDoc}
   *
   * @param string {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public byte[] getBytes(String string) {
    if (string == null) {
      return null;
    }

    if (string.length() == 0) {
      return ZERO_LENGTH_BYTES;
    }

    try {
      return string.getBytes("ISO-8859-1");
    } catch(UnsupportedEncodingException ex) {
      return string.getBytes();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param string {@inheritDoc}
   * @param os {@inheritDoc}
   *
   * @throws IOException
   */
  public void getBytes(String string, OutputStream os) throws IOException {
    if (string == null) {
      return;
    }

    CharArray ca = (CharArray) perThreadCABuffer.get();

    ca.set(string);

    int charLen = ca.length();

    if (charLen == 0) {
      return;
    }

    int    i     = 0;
    char[] chars = ca.A;

    while(i < charLen) {
      os.write((byte) chars[i++]);
    }
  }

  /**
   * Converts characters to a byte array
   *
   * @param chars    the source character data to convert
   * @param bytes    the destination array
   * @param bytePos  the starting position within the destination array
   *
   * @return   the number of bytes
   */
  public int getBytes(char[] chars, byte[] bytes, int bytePos) {
    if (chars == null) {
      return 0;
    }

    return getBytes(chars, 0, chars.length, bytes, bytePos);
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   * @param charLen {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public byte[] getBytes(char[] chars, int charPos, int charLen) {
    if (chars == null) {
      return null;
    }

    if (charLen == 0) {
      return ZERO_LENGTH_BYTES;
    }

    byte[] bytes = new byte[charLen];
    int    i     = 0;

    while(i < charLen) {
      bytes[i++] = (byte) chars[charPos++];
    }

    return bytes;
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   * @param charLen {@inheritDoc}
   * @param os {@inheritDoc}
   *
   * @throws IOException
   */
  public void getBytes(char[] chars, int charPos, int charLen, OutputStream os) throws IOException {
    if (chars == null) {
      return;
    }

    if (charLen == 0) {
      return;
    }

    int i = 0;

    while(i < charLen) {
      os.write((byte) chars[charPos++]);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   * @param charLen {@inheritDoc}
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int getBytes(char[] chars, int charPos, int charLen, byte[] bytes, int bytePos) {
    if ((chars == null) || (charLen == 0)) {
      return 0;
    }

    int olen = charLen;

    charLen += charPos;

    while(charPos < charLen) {
      bytes[bytePos++] = (byte) chars[charPos++];
    }

    return olen;
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   * @param charLen {@inheritDoc}
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int getBytes(char[] chars, int charPos, int charLen, ByteArray bytes, int bytePos) {
    if ((chars == null) || (charLen == 0)) {
      return 0;
    }

    int olen = charLen;

    charLen += charPos;
    bytes.ensureCapacity(bytePos + olen);

    byte[] A = bytes.A;

    while(charPos < charLen) {
      A[bytePos++] = (byte) chars[charPos++];
    }

    return olen;
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param pos {@inheritDoc}
   * @param len {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public int getCharLength(byte[] bytes, int pos, int len) {
    return len;
  }

  /**
   * Converts bytes to characters.
   *
   * @param bytes    the source byte data to convert
   * @param chars    the destination array
   * @param charPos  the offset into chars at which to begin the copy
   *
   * @return   the number of characters
   *
   * @throws   FormatException if an illegal character sequence is encountered.
   */
  public int getChars(byte[] bytes, char[] chars, int charPos) throws FormatException {
    if (bytes == null) {
      return 0;
    }

    int len = bytes.length;
    int i   = 0;

    while(i < len) {
      chars[charPos++] = (char) (bytes[i++] & 0xff);
    }

    return len;
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   * @param byteLen {@inheritDoc}
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  public int getChars(byte[] bytes, int bytePos, int byteLen, char[] chars, int charPos) throws FormatException {
    if ((bytes == null) || (byteLen == 0)) {
      return 0;
    }

    int olen = byteLen;

    byteLen += bytePos;

    while(bytePos < byteLen) {
      chars[charPos++] = (char) (bytes[bytePos++] & 0xff);
    }

    return olen;
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   * @param byteLen {@inheritDoc}
   * @param chars {@inheritDoc}
   * @param charPos {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  public int getChars(byte[] bytes, int bytePos, int byteLen, CharArray chars, int charPos) throws FormatException {
    if ((bytes == null) || (byteLen == 0)) {
      return 0;
    }

    int olen = byteLen;

    byteLen += bytePos;
    chars.ensureLength(charPos + olen);

    char[] A = chars.A;

    while(bytePos < byteLen) {
      A[charPos++] = (char) (bytes[bytePos++] & 0xff);
    }

    return olen;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getEncoding() {
    return "ISO-8859-1";
  }

  /**
   * Returns a valid instance of the helper
   *
   * @return a valid instance of the helper
   */
  public static ISO88591Helper getInstance() {
    if (instance == null) {
      instance = new ISO88591Helper();
    }

    return instance;
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  public String getString(byte[] bytes) throws FormatException {
    if (bytes == null) {
      return null;
    }

    return getString(bytes, 0, bytes.length);
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param offset {@inheritDoc}
   * @param length {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  public String getString(byte[] bytes, int offset, int length) throws FormatException {
    if (length == 0) {
      return (bytes == null)
             ? null
             : ZERO_LENGTH_STRING;
    }

    CharArray ca = (CharArray) perThreadCABuffer.get();

    ca._length = getChars(bytes, offset, length, ca, 0);

    return ca.toString();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final boolean isByteLenghthSupported() {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public final boolean isCharLengthSupported() {
    return true;
  }
}
