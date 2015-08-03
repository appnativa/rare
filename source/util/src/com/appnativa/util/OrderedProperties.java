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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A {@code Properties} object is a {@code LinkedHashMap} where the keys and values
 * must be {@code String}s. Each property can have a default
 * {@code Properties} list which specifies the default
 * values to be used when a given key is not found in this {@code Properties}
 * instance.
 *
 * @see LinkedHashMap
 * @see java.lang.System#getProperties
 * @since Android 1.0
 */
public class OrderedProperties extends LinkedHashMap {
  private static final long serialVersionUID = 4112578634029874840L;
  private static final int
    NONE                                     = 0,
    SLASH                                    = 1,
    UNICODE                                  = 2,
    CONTINUE                                 = 3,
    KEY_DONE                                 = 4,
    IGNORE                                   = 5;
  private static String lineSeparator;

  /**
   * The default values for keys not found in this {@code Properties}
   * instance.
   *
   * @since Android 1.0
   */
  protected OrderedProperties defaults;
  private boolean             preserveDuplicates;
  private boolean             slashComment;
  private CharArray           strBuffer;
  private boolean             stripLeadingSpaces;

  /**
   * Constructs a new {@code Properties} object.
   *
   * @since Android 1.0
   */
  public OrderedProperties() {
    super();
    defaults           = null;
    preserveDuplicates = false;
    slashComment       = false;
  }

  /**
   * Constructs a new {@code Properties} object using the specified default
   * {@code Properties}.
   *
   * @param properties
   *            the default {@code Properties}.
   * @since Android 1.0
   */
  public OrderedProperties(OrderedProperties properties) {
    defaults           = properties;
    preserveDuplicates = false;
    slashComment       = false;
  }

  /**
   * Loads properties from the specified {@code InputStream}. The encoding is
   * ISO-8859-1.
   * @param in the {@code InputStream}
   * @throws IOException
   */
  public synchronized void load(InputStream in) throws IOException {
    if (in == null) {
      throw new NullPointerException();
    }

    load(new InputStreamReader(in, "ISO-8859-1"));
  }

  /**
   * Loads properties from the specified {@code Reader}.
   * The properties file is interpreted according to the following rules:
   * <ul>
   * <li>Empty lines are ignored.</li>
   * <li>Lines starting with either a "#" or a "!" are comment lines and are
   * ignored.</li>
   * <li>A backslash at the end of the line escapes the following newline
   * character ("\r", "\n", "\r\n"). If there's whitespace after the
   * backslash it will just escape that whitespace instead of concatenating
   * the lines. This does not apply to comment lines.</li>
   * <li>A property line consists of the key, the space between the key and
   * the value, and the value. The key goes up to the first whitespace, "=" or
   * ":" that is not escaped. The space between the key and the value contains
   * either one whitespace, one "=" or one ":" and any amount of additional
   * whitespace before and after that character. The value starts with the
   * first character after the space between the key and the value.</li>
   * <li>Following escape sequences are recognized: "\ ", "\\", "\r", "\n",
   * "\!", "\#", "\t", "\b", "\f", and "&#92;uXXXX" (unicode character).</li>
   * </ul>
   *
   * @param in the {@code Reader}
   * @throws IOException
   * @since 1.6
   */
  @SuppressWarnings("fallthrough")
  public synchronized void load(Reader in) throws IOException {
    if (in == null) {
      throw new NullPointerException();
    }

    int            mode    = NONE,
                   unicode = 0,
                   count   = 0;
    char           nextChar;
    char           buf[]     = new char[40];
    int            offset    = 0,
                   keyLength = -1, intVal;
    boolean        firstChar = true;
    BufferedReader br        = new BufferedReader(in);

    while(true) {
      intVal = br.read();

      if (intVal == -1) {
        break;
      }

      nextChar = (char) intVal;

      if (offset == buf.length) {
        char[] newBuf = new char[buf.length * 2];

        System.arraycopy(buf, 0, newBuf, 0, offset);
        buf = newBuf;
      }

      if (mode == UNICODE) {
        int digit = Character.digit(nextChar, 16);

        if (digit >= 0) {
          unicode = (unicode << 4) + digit;

          if (++count < 4) {
            continue;
          }
        } else if (count <= 4) {
          throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
        }

        mode          = NONE;
        buf[offset++] = (char) unicode;

        if (nextChar != '\n') {
          continue;
        }
      }

      if (mode == SLASH) {
        mode = NONE;

        switch(nextChar) {
          case '\r' :
            mode = CONTINUE;           // Look for a following \n

            continue;
          case '\n' :
            mode = IGNORE;             // Ignore whitespace on the next line

            continue;
          case 'b' :
            nextChar = '\b';

            break;

          case 'f' :
            nextChar = '\f';

            break;

          case 'n' :
            nextChar = '\n';

            break;

          case 'r' :
            nextChar = '\r';

            break;

          case 't' :
            nextChar = '\t';

            break;

          case 'u' :
            mode    = UNICODE;
            unicode = count = 0;

            continue;
        }
      } else {
        switch(nextChar) {
          case '#' :
          case '!' :
            if (firstChar) {
              while(true) {
                intVal = br.read();

                if (intVal == -1) {
                  break;
                }

                nextChar = (char) intVal;

                if ((nextChar == '\r') || (nextChar == '\n')) {
                  break;
                }
              }

              continue;
            }

            break;

          case '\n' :
            if (mode == CONTINUE) {    // Part of a \r\n sequence
              mode = IGNORE;           // Ignore whitespace on the next line

              continue;
            }
          // fall into the next case
          case '\r' :
            mode      = NONE;
            firstChar = true;

            if ((offset > 0) || ((offset == 0) && (keyLength == 0))) {
              if (keyLength == -1) {
                keyLength = offset;
              }

              String temp = new String(buf, 0, offset);

              put(temp.substring(0, keyLength), temp.substring(keyLength));
            }

            keyLength = -1;
            offset    = 0;

            continue;
          case '\\' :
            if (mode == KEY_DONE) {
              keyLength = offset;
            }

            mode = SLASH;

            continue;
          case ':' :
          case '=' :
            if (keyLength == -1) {     // if parsing the key
              mode      = NONE;
              keyLength = offset;

              continue;
            }

            break;
        }

        if (Character.isWhitespace(nextChar)) {
          if (mode == CONTINUE) {
            mode = IGNORE;
          }

          // if key length == 0 or value length == 0
          if ((offset == 0) || (offset == keyLength) || (mode == IGNORE)) {
            continue;
          }

          if (keyLength == -1) {       // if parsing the key
            mode = KEY_DONE;

            continue;
          }
        }

        if ((mode == IGNORE) || (mode == CONTINUE)) {
          mode = NONE;
        }
      }

      firstChar = false;

      if (mode == KEY_DONE) {
        keyLength = offset;
        mode      = NONE;
      }

      buf[offset++] = nextChar;
    }

    if ((mode == UNICODE) && (count <= 4)) {
      throw new IllegalArgumentException("Invalid Unicode sequence");
    }

    if ((keyLength == -1) && (offset > 0)) {
      keyLength = offset;
    }

    if (keyLength >= 0) {
      String temp = new String(buf, 0, offset);
      String key  = temp.substring(0, keyLength);
      int    vo   = keyLength;

      if (stripLeadingSpaces) {
        while(vo < offset) {
          if (Character.isWhitespace(buf[vo])) {
            vo++;
          }
        }
      }

      String value = temp.substring(vo);

      if (mode == SLASH) {
        value += "\u0000";
      }

      put(key, value);
    }
  }

  public Object put(Object key, Object value) {
    Object o = super.put(key, value);

    if (isPreserveDuplicates() && (o != null)) {
      List l;

      if (o instanceof String) {
        l = new ArrayList();
        l.add((String) o);
      } else {
        l = (List) o;
      }

      l.add(value);
      super.put(key, l);
    }

    return o;
  }

  /**
   * Stores the mappings in this {@code Properties} to the specified {@code
   * OutputStream}, putting the specified comment at the beginning. The output
   * from this method is suitable for being read by the
   * {@link #load(InputStream)} method.
   *
   * @param out the {@code OutputStream} to write to.
   * @param comment the comment to put at the beginning.
   * @throws IOException if an error occurs during the write to the {@code
   *             OutputStream}.
   * @exception ClassCastException when the key or value of a mapping is not a
   *                {@code String}.
   * @since Android 1.0
   */
  public synchronized void store(OutputStream out, String comment) throws IOException {
    if (lineSeparator == null) {
      lineSeparator = System.getProperty("line.separator");    // $NON-NLS-1$
    }

    StringBuilder      buffer = new StringBuilder(200);
    OutputStreamWriter writer = new OutputStreamWriter(out, "ISO8859_1");    // $NON-NLS-1$

    if (comment != null) {
      writer.write("#");    // $NON-NLS-1$
      writer.write(comment);
      writer.write(lineSeparator);
    }

    writer.write("#");    // $NON-NLS-1$
    writer.write(new Date().toString());
    writer.write(lineSeparator);

    for (Iterator e = keySet().iterator(); e.hasNext(); ) {
      String key = (String) e.next();
      String val = (String) get(key);

      dumpString(buffer, key, true);
      buffer.append('=');
      dumpString(buffer, (String) val, false);
      buffer.append(lineSeparator);
      writer.write(buffer.toString());
      buffer.setLength(0);
    }

    writer.flush();
  }

  public String stripComment(String str) {
    if ((str == null) || ((int) str.length() == 0)) {
      return null;
    }

    if (strBuffer == null) {
      strBuffer = new CharArray();
    }

    strBuffer.set(str);
    strBuffer.trim();

    int n;

    if (!slashComment) {
      n = CharScanner.indexOf(strBuffer.A, 0, strBuffer._length, '#', true, false, '(', ')');
    } else {
      n = CharScanner.indexOf(strBuffer.A, 0, strBuffer._length, '/', true, false, '(', ')');

      if ((n != -1) && (n + 1) < strBuffer._length) {
        if (strBuffer.A[n + 1] != '/') {
          n = -1;
        }
      }
    }

    if (n == -1) {
      return str;
    }

    return str.substring(0, n).trim();
  }

  public String stripComment(Object o, iValidator validator) {
    if (o instanceof String) {
      return stripComment((String) o, validator);
    }

    if (o instanceof List) {
      List      list = (List) o;
      final int len  = list.size();

      for (int i = 0; i < len; i++) {
        String s = stripComment((String) list.get(i), validator);

        if (s != null) {
          return s;
        }
      }
    }

    return null;
  }

  public String stripComment(String str, iValidator validator) {
    final int len = (str == null)
                    ? 0
                    : str.length();

    if (len == 0) {
      return null;
    }

    if (strBuffer == null) {
      strBuffer = new CharArray();
    }

    strBuffer.set(str);
    strBuffer.trim();

    if (strBuffer._length == 0) {
      return null;
    }

    int n;

    if (!slashComment) {
      n = CharScanner.indexOf(strBuffer.A, 0, strBuffer._length, '#', true, false, '(', ')');
    } else {
      n = CharScanner.indexOf(strBuffer.A, 0, strBuffer._length, '/', true, false, '(', ')');

      if ((n != -1) && (n + 1) < strBuffer._length) {
        if (strBuffer.A[n + 1] != '/') {
          n = -1;
        }
      }
    }

    int p = -1;

    if (n == -1) {
      if (strBuffer.A[strBuffer._length - 1] == ']') {
        p = strBuffer.lastIndexOf('[');
      }
    } else if (n > 0) {
      strBuffer._length = n;
      strBuffer.rightTrim();

      if (strBuffer.A[strBuffer._length - 1] == ']') {
        p = strBuffer.lastIndexOf('[');
      }
    }

    if ((n == -1) && (p == -1)) {
      if ((validator != null) &&!validator.isValid(null, 0)) {
        return null;
      }

      return str;
    }

    if (p != -1) {
      if ((validator != null) &&!validator.isValid(strBuffer, p)) {
        return null;
      }

      n = p;
    }

    return (n == len)
           ? str
           : str.substring(0, n).trim();
  }

  /**
   * @param preserveDuplicates the preserveDuplicates to set
   */
  public void setPreserveDuplicates(boolean preserveDuplicates) {
    this.preserveDuplicates = preserveDuplicates;
  }

  /**
   * Maps the specified key to the specified value. If the key already exists,
   * the old value is replaced. The key and value cannot be {@code null}.
   *
   * @param name
   *            the key.
   * @param value
   *            the value.
   * @return the old value mapped to the key, or {@code null}.
   * @since Android 1.0
   */
  public Object setProperty(String name, String value) {
    return put(name, value);
  }

  /**
   * @param slashComment the slashComment to set
   */
  public void setSlashComment(boolean slashComment) {
    this.slashComment = slashComment;
  }

  /**
   * @param stripLeadingSpaces the stripLeadingSpaces to set
   */
  public void setStripLeadingSpaces(boolean stripLeadingSpaces) {
    this.stripLeadingSpaces = stripLeadingSpaces;
  }

  /**
   * Searches for the property with the specified name. If the property is not
   * found, the default {@code Properties} are checked. If the property is not
   * found in the default {@code Properties}, {@code null} is returned.
   *
   * @param name
   *            the name of the property to find.
   * @return the named property value, or {@code null} if it can't be found.
   * @since Android 1.0
   */
  public String getProperty(String name) {
    Object result   = super.get(name);
    String property = (result instanceof String)
                      ? (String) result
                      : null;

    if ((property == null) && (defaults != null)) {
      property = defaults.getProperty(name);
    }

    return property;
  }

  /**
   * Searches for the property with the specified name. If the property is not
   * found, it looks in the default {@code Properties}. If the property is not
   * found in the default {@code Properties}, it returns the specified
   * default.
   *
   * @param name
   *            the name of the property to find.
   * @param defaultValue
   *            the default value.
   * @return the named property value.
   * @since Android 1.0
   */
  public String getProperty(String name, String defaultValue) {
    Object result   = super.get(name);
    String property = (result instanceof String)
                      ? (String) result
                      : null;

    if ((property == null) && (defaults != null)) {
      property = defaults.getProperty(name);
    }

    if (property == null) {
      return defaultValue;
    }

    return property;
  }

  /**
   * @return the preserveDuplicates
   */
  public boolean isPreserveDuplicates() {
    return preserveDuplicates;
  }

  /**
   * @return the slashComment
   */
  public boolean isSlashComment() {
    return slashComment;
  }

  /**
   * @return the stripLeadingSpaces
   */
  public boolean isStripLeadingSpaces() {
    return stripLeadingSpaces;
  }

  private void dumpString(StringBuilder buffer, String string, boolean key) {
    int i = 0;

    if (!key && (i < string.length()) && (string.charAt(i) == ' ')) {
      buffer.append("\\ ");    // $NON-NLS-1$
      i++;
    }

    for (; i < string.length(); i++) {
      char ch = string.charAt(i);

      switch(ch) {
        case '\t' :
          buffer.append("\\t");      // $NON-NLS-1$

          break;

        case '\n' :
          buffer.append("\\n");      // $NON-NLS-1$

          break;

        case '\f' :
          buffer.append("\\f");      // $NON-NLS-1$

          break;

        case '\r' :
          buffer.append("\\r");      // $NON-NLS-1$

          break;

        default :
          if (("\\#!=:".indexOf(ch) >= 0) || (key && (ch == ' '))) {
            buffer.append('\\');
          }

          if ((ch >= ' ') && (ch <= '~')) {
            buffer.append(ch);
          } else {
            String hex = Integer.toHexString(ch);

            buffer.append("\\u");    // $NON-NLS-1$

            for (int j = 0; j < 4 - hex.length(); j++) {
              buffer.append("0");    // $NON-NLS-1$
            }

            buffer.append(hex);
          }
      }
    }
  }

  public interface iValidator {
    boolean isValid(CharArray ca, int start);
  }
}
