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

import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Helper for all character sets
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class GenericCharsetHelper implements iCharsetHelper {
  private static ThreadLocal perThreadBABuffer = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new ByteArray(32);
    }
  };
  int            cbLen = 1024;
  ByteBuffer     bBuffer;
  CharBuffer     cBuffer;
  String         charEncoding;
  CharsetDecoder decoder;
  CharsetEncoder encoder;

  /**
   * Creates a new GenericCharsetHelper object.
   *
   * @param cs  the character set
   *
   * @throws  UnsupportedEncodingException if the character set is not supporter
   */
  public GenericCharsetHelper(String cs) throws UnsupportedEncodingException {
    this(Charset.forName(cs));
  }

  /**
   * Creates a new GenericCharsetHelper object.
   *
   * @param charset  the character set
   *
   * @throws  UnsupportedEncodingException if the character set is not supporter
   */
  public GenericCharsetHelper(Charset charset) {
    charEncoding = charset.name();
    encoder      = charset.newEncoder();
    decoder      = charset.newDecoder();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public iCharsetHelper copy() {
    try {
      return new GenericCharsetHelper(charEncoding);
    } catch(UnsupportedEncodingException ex) {
      return null;
    }
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
  public int getByteLength(char[] chars, int pos, int len) {
    throw new java.lang.UnsupportedOperationException();
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

    try {
      return string.getBytes(charEncoding);
    } catch(UnsupportedEncodingException ex) {
      return null;
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
    ByteArray ba = (ByteArray) perThreadBABuffer.get();

    ba._length = getBytes(chars, charPos, charLen, ba, 0);

    return ba.toArray();
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
    if (chars == null) {
      return 0;
    }

    charLen = encode(chars, charPos, charLen);
    bBuffer.get(bytes, bytePos, charLen);

    return charLen;
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
    if (chars == null) {
      return 0;
    }

    charLen = encode(chars, charPos, charLen);
    bytes.ensureCapacity(bytePos + charLen);
    bBuffer.get(bytes.A, bytePos, charLen);

    return charLen;
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
    throw new java.lang.UnsupportedOperationException();
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

    return getChars(bytes, 0, bytes.length, chars, charPos);
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
    if (bytes == null) {
      return 0;
    }

    byteLen = decode(bytes, bytePos, byteLen);
    cBuffer.get(chars, charPos, byteLen);

    return byteLen;
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
    if (bytes == null) {
      return 0;
    }

    byteLen = decode(bytes, bytePos, byteLen);
    chars.ensureCapacity(charPos + byteLen);
    cBuffer.get(chars.A, charPos, byteLen);

    return byteLen;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String getEncoding() {
    return charEncoding;
  }

  public Charset getCharset() {
    return encoder.charset();
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

    int byteLen = bytes.length;

    if ((cBuffer == null) || (byteLen > cbLen)) {
      cbLen   = (byteLen > cbLen)
                ? (byteLen + 128)
                : cbLen;
      cBuffer = CharBuffer.allocate(cbLen);
      bBuffer = ByteBuffer.allocate(cbLen * 2);
    }

    bBuffer.clear();
    cBuffer.clear();
    bBuffer.put(bytes, 0, byteLen);
    bBuffer.flip();
    decoder.decode(bBuffer, cBuffer, true);
    cBuffer.flip();

    return cBuffer.toString();
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   * @param byteLen {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  public String getString(byte[] bytes, int bytePos, int byteLen) throws FormatException {
    if (bytes == null) {
      return null;
    }

    if ((cBuffer == null) || (byteLen > cbLen)) {
      cbLen   = (byteLen > cbLen)
                ? (byteLen + 128)
                : cbLen;
      cBuffer = CharBuffer.allocate(cbLen);
      bBuffer = ByteBuffer.allocate(cbLen * 2);
    }

    bBuffer.clear();
    cBuffer.clear();
    bBuffer.put(bytes, bytePos, byteLen);
    bBuffer.flip();
    decoder.decode(bBuffer, cBuffer, true);
    cBuffer.flip();

    return cBuffer.toString();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean isByteLenghthSupported() {
    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public boolean isCharLengthSupported() {
    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @param bytes {@inheritDoc}
   * @param bytePos {@inheritDoc}
   * @param byteLen {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws FormatException {@inheritDoc}
   */
  int decode(byte[] bytes, int bytePos, int byteLen) throws FormatException {
    if ((cBuffer == null) || (byteLen > cbLen)) {
      cbLen   = (byteLen > cbLen)
                ? (byteLen + 128)
                : cbLen;
      cBuffer = CharBuffer.allocate(cbLen);
      bBuffer = ByteBuffer.allocate(cbLen * 2);
    }

    bBuffer.clear();
    cBuffer.clear();
    bBuffer.put(bytes, bytePos, byteLen);
    bBuffer.flip();
    decoder.decode(bBuffer, cBuffer, true);
    cBuffer.flip();

    return cBuffer.remaining();
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
  int encode(char[] chars, int charPos, int charLen) {
    if ((cBuffer == null) || (charLen > cbLen)) {
      cbLen   = (charLen > cbLen)
                ? (charLen + 128)
                : cbLen;
      cBuffer = CharBuffer.allocate(cbLen);
      bBuffer = ByteBuffer.allocate(cbLen * 2);
    }

    bBuffer.clear();
    cBuffer.clear();
    cBuffer.put(chars, charPos, charLen);
    cBuffer.flip();

    while(encoder.encode(cBuffer, bBuffer, true).isOverflow()) {
      cbLen   += charLen;
      cBuffer = CharBuffer.allocate(cbLen);
      bBuffer = ByteBuffer.allocate(cbLen * 2);
      cBuffer.clear();
      bBuffer.clear();
      cBuffer.put(chars, charPos, charLen);
      cBuffer.flip();
    }

    bBuffer.flip();

    return bBuffer.remaining();
  }
}
