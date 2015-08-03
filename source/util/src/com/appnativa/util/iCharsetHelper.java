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
 * This interface defines the methods necessary for classes that can convert
 * characters to bytes and vice-a-versa.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public interface iCharsetHelper {

  /**
   * Creates a copy of this characterset helper
   *
   * @return a copy of this characterset helper
   */
  public iCharsetHelper copy();

  /**
   * Returns the number of bytes that would be created by converting
   * the specified characters to bytes
   *
   * @param chars  the characters
   * @param pos    the first character to be converted
   * @param len    the number of characters to be converted.
   *
   * @return   the number of bytes
   */
  public int getByteLength(char[] chars, int pos, int len);

  /**
   * Converts a string to a byte array
   *
   * @param string  the string to convert.
   *
   * @return   the character byte array.
   */
  public byte[] getBytes(String string);

  /*
   * Converts characters to a byte array
   *
   * @param chars    the source character data to convert
   * @param charPos  the starting position within the character array
   * @param charLen  the number of characters to use
   */

  /**
   * Converts characters to a byte array
   *
   * @param chars    the source character data to convert
   * @param charPos  the starting position within the character array
   * @param charLen  the number of characters to use
   *
   * @return   the array of bytes
   */
  public byte[] getBytes(char[] chars, int charPos, int charLen);

  /**
   * Converts characters to a byte array
   *
   * @param chars    the source character data to convert
   * @param charPos  the starting position within the character array
   * @param charLen  the number of characters to use
   * @param bytes    the destination array
   * @param bytePos  the starting position within the destination array
   *
   * @return   the number of bytes
   */
  public int getBytes(char[] chars, int charPos, int charLen, byte[] bytes, int bytePos);

  /**
   * Converts characters to a byte array
   *
   * @param chars    the source character data to convert
   * @param charPos  the starting position within the character array
   * @param charLen  the number of characters to use
   * @param bytes    the destination array
   * @param bytePos  the starting position within the destination array
   *
   * @return   the number of bytes
   */
  public int getBytes(char[] chars, int charPos, int charLen, ByteArray bytes, int bytePos);

  /**
   * Returns the number of characters that would be created by converting
   * the specified bytes characters
   *
   * @param bytes  the bytes
   * @param pos    the starting position within the array
   * @param len    the number of array elements to use
   *
   * @return   the number of characters
   *
   * @throws   FormatException if an illegal UTF-8 sequence is encountered.
   */
  public int getCharLength(byte[] bytes, int pos, int len) throws FormatException;

  /**
   * Converts bytes to characters.
   *
   * @param bytes    the source byte data to convert
   * @param bytePos  the offset into the byte array at which to start the conversion
   * @param byteLen  the number of bytes to use
   * @param chars    the destination array
   * @param charPos  the offset into chars at which to begin the copy
   *
   * @return   the number of characters
   *
   * @throws   FormatException if an illegal character sequence is encountered.
   */
  public int getChars(byte[] bytes, int bytePos, int byteLen, char[] chars, int charPos) throws FormatException;

  /**
   * Converts bytes to characters.
   *
   * @param bytes    the source byte data to convert
   * @param bytePos  the offset into the byte array at which to start the conversion
   * @param byteLen  the number of bytes to use
   * @param chars    the destination array
   * @param charPos  the offset into chars at which to begin the copy
   *
   * @return   the number of characters
   *
   * @throws   FormatException if an illegal ISO-8859-1 sequence is encountered.
   */
  public int getChars(byte[] bytes, int bytePos, int byteLen, CharArray chars, int charPos) throws FormatException;

  /**
   * Retrieves the character encoding in use by the helper
   *
   * @return   the encoding
   */
  public String getEncoding();

  /**
   * Converts byte arrays into strings.
   *
   * @param bytes  the source byte data to convert
   *
   * @return   the string
   *
   * @throws   FormatException if an illegal character sequence is encountered.
   */
  public String getString(byte[] bytes) throws FormatException;

  /**
   * Converts bytes into a string.
   *
   * @param bytes   the source byte data to convert
   * @param offset  the offset into the byte array at which to start the conversion
   * @param length  the number of bytes to be converted.
   *
   * @return   the string.
   *
   * @throws   FormatException if an illegal character sequence is encountered.
   */
  public String getString(byte[] bytes, int offset, int length) throws FormatException;

  /**
   * Returns whether the getByteLength() method is supported
   *
   * @return whether the getByteLength() method is supported
   */
  public boolean isByteLenghthSupported();

  /**
   * Returns whether the getCharLength() method is supported
   *
   * @return whether the getCharLength() method is supported
   */
  public boolean isCharLengthSupported();
}
