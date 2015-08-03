/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.util;

import com.appnativa.util.CharArray;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Don DeCoteau
 */
public class JSONHelper {
  static char[]                         hex               = "0123456789ABCDEF".toCharArray();
  private static ThreadLocal<CharArray> perThreadCABuffer = new ThreadLocal<CharArray>() {
    @Override
    protected synchronized CharArray initialValue() {
      return new CharArray(32);
    }
  };

  private JSONHelper() {}

  public static Writer getWiter(Writer w) {
    return new JSONStringWriter(w);
  }

  /**
   * Encodes a string into its JSON representation
   *
   * @param pString string to convert to a JSON format
   * @param buffer buffer to use to store output
   */
  public static void encode(String pString, Writer buffer) throws IOException {
    if ((pString == null) || (pString.length() == 0)) {
      return;
    }

    CharArray ca  = perThreadCABuffer.get().set(pString);
    int       len = ca._length;
    char[]    a   = ca.A;
    char      c;
    int       i = 0;

    buffer.write('"');

    while(i < len) {
      c = a[i++];

      if (c == '"') {
        buffer.write("\\\"");
      } else if (c == '\\') {
        buffer.write("\\\\");
      } else if (c == '/') {
        buffer.write("\\/");
      } else if (c == '\b') {
        buffer.write("\\b");
      } else if (c == '\f') {
        buffer.write("\\f");
      } else if (c == '\n') {
        buffer.write("\\n");
      } else if (c == '\r') {
        buffer.write("\\r");
      } else if (c == '\t') {
        buffer.write("\\t");
      } else if (Character.isISOControl(c)) {
        unicode(c, buffer);
      } else {
        buffer.write(c);
      }
    }

    if (a.length > 4096) {    //prevent one large string from hogging memory
      ca._length = 4096;
      ca.trimToSize();
    }

    buffer.write('"');
  }

  /**
   *   Encodes a set of characters into its JSON string representation
   *
   *   @param a the characters to encode
   *   @param pos the starting position within the array
   *   @param len the number of characters to encode
   *   @param buffer buffer to use to store output
   *   @param quote true to add the surrounding quotes; false otherwise
   */
  public static void encode(char[] a, int pos, int len, Writer buffer, boolean quote) throws IOException {
    if ((a == null) || (len == 0)) {
      return;
    }

    char c;
    int  i = pos;

    len += pos;

    if (quote) {
      buffer.write('"');
    }

    while(i < len) {
      c = a[i++];

      if (c == '"') {
        buffer.write("\\\"");
      } else if (c == '\\') {
        buffer.write("\\\\");
      } else if (c == '/') {
        buffer.write("\\/");
      } else if (c == '\b') {
        buffer.write("\\b");
      } else if (c == '\f') {
        buffer.write("\\f");
      } else if (c == '\n') {
        buffer.write("\\n");
      } else if (c == '\r') {
        buffer.write("\\r");
      } else if (c == '\t') {
        buffer.write("\\t");
      } else if (Character.isISOControl(c)) {
        unicode(c, buffer);
      } else {
        buffer.write(c);
      }
    }

    if (quote) {
      buffer.write('"');
    }
  }

  /**
   * Represent as unicode
   * @param c character to be encoded
   */
  private static void unicode(char c, Writer buffer) throws IOException {
    buffer.write("\\u");

    int n = c;

    for (int i = 0; i < 4; ++i) {
      int digit = (n & 0xf000) >> 12;

      buffer.write(hex[digit]);
      n <<= 4;
    }
  }

  public static class JSONStringWriter extends Writer {
    Writer writer;

    public JSONStringWriter(Writer writer) {
      this.writer = writer;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
      encode(cbuf, off, len, writer, false);
    }

    @Override
    public void flush() throws IOException {
      writer.flush();
    }

    @Override
    public void close() throws IOException {
      writer.close();
    }
  }
}
