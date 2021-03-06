/**
 * Copyright 2001-2004 The Apache Software Foundation.
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
 * Copyright 2001-2004 The Apache Software Foundation.
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.BitSet;

/**
 * <p>Implements the 'www-form-urlencoded' encoding scheme,
 * also misleadingly known as URL encoding.</p>
 *
 * <p>For more detailed information please refer to
 * <a href="http://www.w3.org/TR/html4/interact/forms.html#h-17.13.4.1">
 * Chapter 17.13.4 'Form content types'</a> of the
 * <a href="http://www.w3.org/TR/html4/">HTML 4.01 Specification<a></p>
 *
 * <p>
 * This codec is meant to be a replacement for standard Java classes
 * {@link java.net.URLEncoder} and {@link java.net.URLDecoder}
 * on older Java platforms, as these classes in Java versions below
 * 1.4 rely on the platform's default charset encoding.
 * </p>
 *
 * NOTICE: Original file modified to support direct encoding to Writer, OutputStream, and StringBuiler
 *
 * @author Apache Software Foundation
 * @since 1.2
 * @version $Id: URLEncoder.java,v 1.19 2004/03/29 07:59:00 ggregory Exp $
 */
public class URLEncoder {

  /**
   * The default charset used for string decoding and encoding.
   */
  private static String charset = "US-ASCII";

  /**
   * BitSet of www-form-url safe characters.
   */
  protected static final BitSet WWW_FORM_URL = new BitSet(256);

  /**
   * BitSet of www-form-url safe characters.
   */
  protected static final BitSet ALL_URL = new BitSet(256);

  // Static initializer for www_form_url
  static {
    // alpha characters
    for (int i = 'a'; i <= 'z'; i++) {
      WWW_FORM_URL.set(i);
      ALL_URL.set(i);
    }

    for (int i = 'A'; i <= 'Z'; i++) {
      WWW_FORM_URL.set(i);
      ALL_URL.set(i);
    }

    // numeric characters
    for (int i = '0'; i <= '9'; i++) {
      WWW_FORM_URL.set(i);
      ALL_URL.set(i);
    }

    // special chars
    ALL_URL.set('_');
    ALL_URL.set('.');
    WWW_FORM_URL.set('/');
    WWW_FORM_URL.set('-');
    WWW_FORM_URL.set('_');
    WWW_FORM_URL.set('.');
    WWW_FORM_URL.set('*');
    // blank to be replaced with +
    //WWW_FORM_URL.set(' '); commented out because it breaks file URLs
  }

  /**
   * Default constructor.
   */
  public URLEncoder() {
    super();
  }

  /**
   * Decodes an array of URL safe 7-bit characters into an array of
   * original bytes. Escaped characters are converted back to their
   * original representation.
   *
   * @param bytes array of URL safe characters
   * @return array of original bytes
   * @throws FormatException Thrown if URL decoding is unsuccessful
   */
  public static byte[] decode(byte[] bytes) throws FormatException {
    return decodeUrl(bytes);
  }

  /**
   * Decodes a URL safe object into its original form. Escaped
   * characters are converted back to their original representation.
   *
   * @param pObject URL safe object to convert into its original form
   * @return original object
   * @throws FormatException Thrown if URL decoding is not
   *                          applicable to objects of this type
   *                          if decoding is unsuccessful
   */
  public static Object decode(Object pObject) throws FormatException {
    if (pObject == null) {
      return null;
    } else if (pObject instanceof byte[]) {
      return decode((byte[]) pObject);
    } else if (pObject instanceof String) {
      return decode((String) pObject);
    } else {
      throw new FormatException("Objects of type " + pObject.getClass().getName() + " cannot be URL decoded");
    }
  }

  /**
   * Decodes a URL safe string into its original form using the default
   * string charset. Escaped characters are converted back to their
   * original representation.
   *
   * @param pString URL safe string to convert into its original form
   * @return original string
   * @throws FormatException Thrown if URL decoding is unsuccessful
   *
   * @see #getDefaultCharset()
   */
  public static String decode(String pString) throws FormatException {
    if (pString == null) {
      return null;
    }

    try {
      return decode(pString, getDefaultCharset());
    } catch(UnsupportedEncodingException e) {
      throw new FormatException(e.getMessage());
    }
  }

  /**
   * Decodes a URL safe string into its original form using the
   * specified encoding. Escaped characters are converted back
   * to their original representation.
   *
   * @param pString URL safe string to convert into its original form
   * @param charset the original string charset
   * @return original string
   * @throws FormatException Thrown if URL decoding is unsuccessful
   * @throws UnsupportedEncodingException Thrown if charset is not
   *                                      supported
   */
  public static String decode(String pString, String charset)
          throws FormatException, UnsupportedEncodingException {
    if (pString == null) {
      return null;
    }

    return new String(decode(pString.getBytes("US-ASCII")), charset);
  }

  /**
   * Decodes an array of URL safe 7-bit characters into an array of
   * original bytes. Escaped characters are converted back to their
   * original representation.
   *
   * @param bytes array of URL safe characters
   * @return array of original bytes
   * @throws FormatException Thrown if URL decoding is unsuccessful
   */
  public static final byte[] decodeUrl(byte[] bytes) throws FormatException {
    if (bytes == null) {
      return null;
    }

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    for (int i = 0; i < bytes.length; i++) {
      int b = bytes[i];

      if (b == '+') {
        buffer.write(' ');
      } else if (b == '%') {
        try {
          int u = Character.digit((char) bytes[++i], 16);
          int l = Character.digit((char) bytes[++i], 16);

          if ((u == -1) || (l == -1)) {
            throw new FormatException("Invalid URL encoding");
          }

          buffer.write((char) ((u << 4) + l));
        } catch(ArrayIndexOutOfBoundsException e) {
          throw new FormatException("Invalid URL encoding");
        }
      } else {
        buffer.write(b);
      }
    }

    return buffer.toByteArray();
  }

  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param urlsafe bitset of characters deemed URL safe
   * @param bytes array of bytes to convert to URL safe characters
   * @return array of bytes containing URL safe characters
   */
  public static final byte[] encode(BitSet urlsafe, byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    if (urlsafe == null) {
      urlsafe = ALL_URL;
    }

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    for (int i = 0; i < bytes.length; i++) {
      int b = bytes[i];

      if (b < 0) {
        b = 256 + b;
      }

      if (urlsafe.get(b)) {
        if (b == ' ') {
          b = '+';
        }

        buffer.write(b);
      } else {
        buffer.write('%');

        char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
        char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));

        buffer.write(hex1);
        buffer.write(hex2);
      }
    }

    return buffer.toByteArray();
  }

  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param urlsafe bitset of characters deemed URL safe
   * @param bytes array of bytes to convert to URL safe characters
   * @param buffer buffer to use to store output a (US-ASCII)
   */
  public static final void encode(BitSet urlsafe, byte[] bytes, OutputStream buffer) throws IOException {
    if (bytes == null) {
      return;
    }

    if (urlsafe == null) {
      urlsafe = ALL_URL;
    }

    for (int i = 0; i < bytes.length; i++) {
      int b = bytes[i];

      if (b < 0) {
        b = 256 + b;
      }

      if (urlsafe.get(b)) {
        if (b == ' ') {
          b = '+';
        }

        buffer.write(b);
      } else {
        buffer.write('%');

        char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
        char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));

        buffer.write(hex1);
        buffer.write(hex2);
      }
    }
  }

 
 
  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param urlsafe bitset of characters deemed URL safe
   * @param bytes array of bytes to convert to URL safe characters
   * @param buffer buffer to use to store output a (US-ASCII)
   */
  public static final void encode(BitSet urlsafe, byte[] bytes, StringBuilder buffer) {
    if (bytes == null) {
      return;
    }

    if (urlsafe == null) {
      urlsafe = ALL_URL;
    }

    for (int i = 0; i < bytes.length; i++) {
      int b = bytes[i];

      if (b < 0) {
        b = 256 + b;
      }

      if (urlsafe.get(b)) {
        if (b == ' ') {
          b = '+';
        }

        buffer.append((char) b);
      } else {
        buffer.append('%');

        char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
        char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));

        buffer.append(hex1);
        buffer.append(hex2);
      }
    }
  }

  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param urlsafe bitset of characters deemed URL safe
   * @param bytes array of bytes to convert to URL safe characters
   * @param buffer buffer to use to store output a (US-ASCII)
   */
  public static final void encode(BitSet urlsafe, byte[] bytes, Writer buffer) throws IOException {
    if (bytes == null) {
      return;
    }

    if (urlsafe == null) {
      urlsafe = WWW_FORM_URL;
    }

    for (int i = 0; i < bytes.length; i++) {
      int b = bytes[i];

      if (b < 0) {
        b = 256 + b;
      }

      if (urlsafe.get(b)) {
        if (b == ' ') {
          b = '+';
        }

        buffer.write((char) b);
      } else {
        buffer.write('%');

        char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
        char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));

        buffer.write(hex1);
        buffer.write(hex2);
      }
    }
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Unsafe characters are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static void encode(String pString, String charset, OutputStream buffer)
          throws UnsupportedEncodingException, IOException {
    if ((pString == null) || (pString.length() == 0)) {
      return;
    }

    encode(ALL_URL, pString.getBytes(charset), buffer);
  }

  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param bytes array of bytes to convert to URL safe characters
   * @return array of bytes containing URL safe characters
   */
  public static byte[] encodeComponent(byte[] bytes) {
    return encode(WWW_FORM_URL, bytes);
  }

  /**
   * Encodes a string into its URL safe form using the default string
   * character set. Characters deemed unsafe for use
   * with 'application/x-www-form-urlencoded' mime type are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @return URL safe string
   * @throws FormatException Thrown if URL encoding is unsuccessful
   *
   * @see #getDefaultCharset()
   */
  public static String encodeComponent(String pString) throws FormatException {
    if (pString == null) {
      return null;
    }

    try {
      return encodeComponent(pString, getDefaultCharset());
    } catch(UnsupportedEncodingException e) {
      throw new FormatException(e.getMessage());
    }
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Characters deemed unsafe for use
   * with 'application/x-www-form-urlencoded' mime type are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @return URL safe string
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static String encodeComponent(String pString, String charset) throws UnsupportedEncodingException {
    if ((pString == null) || (pString.length() == 0)) {
      return pString;
    }

    return new String(encode(WWW_FORM_URL,pString.getBytes(charset)), "US-ASCII");
  }
  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Characters deemed unsafe for use
   * with 'application/x-www-form-urlencoded' mime type are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static void encodeComponent(String pString, String charset, OutputStream buffer)
          throws UnsupportedEncodingException, IOException {
    if ((pString == null) || (pString.length() == 0)) {
      return;
    }

    encode(WWW_FORM_URL, pString.getBytes(charset), buffer);
  }

 
  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Characters deemed unsafe for use
   * with 'application/x-www-form-urlencoded' mime type are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static void encodeComponent(String pString, String charset, StringBuilder buffer) throws UnsupportedEncodingException {
    if (pString == null) {
      return;
    }

    encode(WWW_FORM_URL, pString.getBytes(charset), buffer);
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Characters deemed unsafe for use
   * with 'application/x-www-form-urlencoded' mime type are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if charset is not
   *                                      supported
   */
  public static void encodeComponent(String pString, String charset, Writer buffer)
          throws UnsupportedEncodingException, IOException {
    if ((pString == null) || (pString.length() == 0)) {
      return;
    }

    encode(WWW_FORM_URL, pString.getBytes(charset), buffer);
  }


  /**
   * Encodes an array of bytes into an array of URL safe 7-bit
   * characters. Unsafe characters are escaped.
   *
   * @param bytes array of bytes to convert to URL safe characters
   * @return array of bytes containing URL safe characters
   */
  public static byte[] encodeUrl(byte[] bytes) {
    return encode(ALL_URL, bytes);
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string charset. Unsafe characters are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @return URL safe string
   * @throws FormatException Thrown if URL encoding is unsuccessful
   *
   * @see #getDefaultCharset()
   */
  public static String encodeUrl(String pString) throws FormatException {
    if (pString == null) {
      return null;
    }

    try {
      return encodeUrl(pString, getDefaultCharset());
    } catch(UnsupportedEncodingException e) {
      throw new FormatException(e.getMessage());
    }
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Unsafe characters are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @return URL safe string
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static String encodeUrl(String pString, String charset) throws UnsupportedEncodingException {
    if ((pString == null) || (pString.length() == 0)) {
      return pString;
    }

    return new String(encode(ALL_URL,pString.getBytes(charset)), "US-ASCII");
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Unsafe characters are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static void encodeUrl(String pString, String charset, StringBuilder buffer) throws UnsupportedEncodingException {
    if (pString == null) {
      return;
    }

    encode(ALL_URL, pString.getBytes(charset), buffer);
  }

  /**
   * Encodes a string into its URL safe form using the specified
   * string character set. Unsafe characters are escaped.
   *
   * @param pString string to convert to a URL safe form
   * @param charset the character set for pString
   * @param buffer buffer to use to store output a (US-ASCII)
   * @throws UnsupportedEncodingException Thrown if character set is not
   *                                      supported
   */
  public static void encodeUrl(String pString, String charset, Writer buffer)
          throws UnsupportedEncodingException, IOException {
    if ((pString == null) || (pString.length() == 0)) {
      return;
    }

    encode(ALL_URL, pString.getBytes(charset), buffer);
  }

  /**
   * The default charset used for string decoding and encoding.
   *
   * @return the default string charset.
   */
  public static String getDefaultCharset() {
    return charset;
  }
}
