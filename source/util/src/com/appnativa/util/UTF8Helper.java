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

import java.nio.charset.Charset;

/**
 * Helper for the UTF-8 character set
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public final class UTF8Helper extends GenericCharsetHelper {
  public static Charset utf8Charset;

  static {
    utf8Charset = Charset.forName("utf-8");
  }

  private static ThreadLocal perThreadInstance = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new UTF8Helper();
    }
  };
  private static ThreadLocal perThreadCharArray = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray();
    }
  };
  private static ThreadLocal perThreadByteArray = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new ByteArray();
    }
  };

  private UTF8Helper() {
    super(utf8Charset);
  }

  public iCharsetHelper copy() {
    return new UTF8Helper();
  }

  /**
   * Takes a string converts it to bytes using utf-8 encoding
   * and then converts it back to ISO-88591 character set
   * 
   * @param value the value to convert
   * @return the converted string
   */
  public static String utf8String(String value) {
    if ((value == null) || (value.length() == 0)) {
      return value;
    }

    CharArray ca = (CharArray) perThreadCharArray.get();
    ByteArray ba = (ByteArray) perThreadByteArray.get();

    ca.set(value);
    ba._length = UTF8Helper.getInstance().getBytes(ca.A, 0, ca._length, ba, 0);
    ca._length = 0;
    ba.getChars(ca, ISO88591Helper.getInstance());

    String s = ca.toString();

    if (ca.A.length > 65538) {
      ca._length = 256;
      ca.trimToSize();
    }

    if (ba.A.length > 65538) {
      ba._length = 256;
      ba.trimToSize();
    }

    return s;
  }

  /**
   * Returns a valid instance of the helper
   * @return a valid instance of the helper
   */
  public static UTF8Helper getInstance() {
    return (UTF8Helper) perThreadInstance.get();
  }
}
