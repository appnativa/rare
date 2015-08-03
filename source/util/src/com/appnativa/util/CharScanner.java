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
import java.io.Reader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the functionality for scanning character arrays and
 * extracting tokens. If provides for smart scanning, in that, token delimiters
 * found within quotations are ignored (if the <code>checkQuote</code> flag is
 * set to <code>true</code>). In addition, if the <code>checkParam</code> flag
 * is set to <code>true</code> and it encounters a starting parameter blocks it
 * will ignore any token delimiters until a closing parameter blocks is
 * encountered.
 * <p>
 * The scanner works with tokens, which consist of a two-element integer array
 * with the first element representing the starting position of the token, and
 * the second element representing the length of the token.
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class CharScanner extends Reader {
  private static final char   DQT                = '\"';
  private static final char   SQT                = '\'';
  private static final String emptyString        = "";
  private static ThreadLocal  perThreadCharArray = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };
  private static ThreadLocal perThreadCharArray2 = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };
  private static ThreadLocal perThreadScanner = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharScanner();
    }
  };

  /** line feed characters */
  static final char[] lfChars = "\r\n".toCharArray();

  /** The last delimiter found or zero if no delimiter was encountered */
  public char foundDelimiter = 0;

  /**  */
  int currentLen = 0;

  /**  */
  int currentPos = 0;

  /**  */
  char[] oContent = null;

  /**  */
  int originalPos = 0;

  /**  */
  char paramEndChar = ')';

  /**  */
  char paramStartChar = '(';

  /**  */
  char[] theContent = null;

  /**  */
  char[] theDelims = null;

  /**  */
  char[] trimChars = null;

  /**  */
  boolean rightTrim = true;

  /**  */
  boolean       leftTrim = true;
  protected int markPos  = 0;
  protected int markLen;
  private char  QT = DQT;

  /**
   * Creates a new <code>Scanner</code> object.
   */
  public CharScanner() {}

  /**
   * Creates a new Scanner object.
   *
   * @param chars
   *          the character array
   */
  public CharScanner(char[] chars) {
    reset(chars, 0, chars.length, false);
  }

  /**
   * Creates a new <code>Scanner</code> object.
   *
   * @param str
   *          the contents
   */
  public CharScanner(String str) {
    reset(str);
  }

  /**
   * Creates a new <code>Scanner</code> object.
   *
   * @param chars
   *          the character array
   * @param copy
   *          <code>true</code> specifies that scanner should make a copy of the
   *          array.
   */
  public CharScanner(char[] chars, boolean copy) {
    reset(chars, 0, chars.length, copy);
  }

  /**
   * Creates a new <code>Scanner</code> object.
   *
   * @param str
   *          the contents
   * @param copy
   *          <code>true</code> specifies that scanner should make a copy of the
   *          array.
   */
  public CharScanner(CharArray str, boolean copy) {
    reset(str, copy);
  }

  /**
   * Creates a new <code>Scanner</code> object.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   */
  public CharScanner(char[] chars, int pos, int len) {
    reset(chars, pos, len, false);
  }

  /**
   * Creates a new <code>Scanner</code> object.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param copy
   *          <code>true</code> specifies that scanner should make a copy of the
   *          array.
   */
  public CharScanner(char[] chars, int pos, int len, boolean copy) {
    reset(chars, pos, len, copy);
  }

  /**
   * Backups one position within the current array
   *
   * @return <code>true</code> if the scanner backed up;
   */
  public boolean backup() {
    if (currentPos <= originalPos) {
      return false;
    }

    currentPos--;
    currentLen++;

    return true;
  }

  public static void charToHTMLString(char c, CharArray out) {
    out.append('&');
    out.append('#');
    out.append((int) c);
    out.append(';');
  }

  public static CharArray charToHexString(char c, CharArray ca) {
    final int radix = 16;
    int       pos   = ca._length;
    int       i     = c;

    i = -i;
    ca.setLength(pos + 4);

    char[] buf     = ca.A;
    int    charPos = pos + 4;

    buf[pos]     = '0';
    buf[pos + 1] = '0';
    buf[pos + 2] = '0';

    while(i <= -radix) {
      buf[charPos--] = SNumber.digits[(int) (-(i % radix))];
      i              = i / radix;
    }

    buf[charPos] = SNumber.digits[(int) (-i)];

    return ca;
  }

  public static CharArray charToUnicodeString(char c, CharArray ca, boolean add_u) {
    final int radix = 16;
    int       pos   = ca._length;
    int       i     = c;

    i = -i;
    ca.setLength(pos + (add_u
                        ? 6
                        : 4));

    char[] buf = ca.A;

    if (add_u) {
      buf[pos++] = '\\';
      buf[pos++] = 'u';
    }

    int charPos = pos + 3;

    buf[pos]     = '0';
    buf[pos + 1] = '0';
    buf[pos + 2] = '0';

    while(i <= -radix) {
      buf[charPos--] = SNumber.digits[(int) (-(i % radix))];
      i              = i / radix;
    }

    buf[charPos] = SNumber.digits[(int) (-i)];

    return ca;
  }

  /**
   * Chops characters from the end of the content
   *
   * @param num
   *          the number of characters to chop off
   */
  public void chop(int num) {
    currentLen -= num;

    if (currentLen < 0) {
      currentLen = 0;
    }
  }

  /**
   * Unquotes a quoted string and resolves escaped characters.
   *
   * @param str
   *          the string
   *
   * @return the cleaned string
   *
   * @throws java.text.ParseException
   *           if an error occurs
   */
  public static String cleanQuoted(String str) throws java.text.ParseException {
    if (str == null) {
      return str;
    }

    CharArray ca  = (CharArray) perThreadCharArray.get();
    CharArray ret = (CharArray) perThreadCharArray2.get();

    ca.set(str);

    char[] chars = ca.A;
    int    len   = ca._length;

    ret._length = 0;
    cleanQuoted(chars, 0, len, ret);

    return ret.toString();
  }

  /**
   * Unquotes a quoted string and resolves escaped characters.
   *
   * @param str
   *          the string removed
   * @param ret
   *          the return buffer
   *
   * @return the cleaned string
   *
   * @throws java.text.ParseException
   *           if an error occurs
   */
  public static CharArray cleanQuoted(String str, CharArray ret) throws java.text.ParseException {
    if (str != null) {
      CharArray ca = (CharArray) perThreadCharArray.get();

      ca.set(str);

      char[] chars = ca.A;
      int    len   = ca._length;

      ret = cleanQuoted(chars, 0, len, ret);
    }

    return ret;
  }

  /**
   * Unquotes a quoted string and resolves escaped characters.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   *
   * @return the cleaned string
   *
   * @throws java.text.ParseException
   *           if an error occurs
   */
  public static final String cleanQuoted(char[] chars, int pos, int len) throws java.text.ParseException {
    CharArray ret = (CharArray) perThreadCharArray.get();

    ret._length = 0;
    cleanQuoted(chars, pos, len, ret);

    return ret.toString();
  }

  /**
   * Unquotes a quoted string and resolves escaped characters.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param ret
   *          the return buffer
   *
   * @return the cleaned string
   *
   * @throws java.text.ParseException
   *           if an error occurs
   */
  public static final CharArray cleanQuoted(char[] chars, int pos, int len, CharArray ret)
          throws java.text.ParseException {
    ret.ensureCapacity(len);

    int n = 0;

    if (len > 0) {
      char lc = 0;
      char c  = 0;

      c = chars[pos];

      if ((c == DQT) || (c == SQT)) {
        if (chars[(pos + len) - 1] != c) {
          throw new java.text.ParseException("unterminated string", pos + len);
        }

        pos++;
        len -= 2;
      }

      char A[] = ret.A;

      for (int i = 0; i < len; i++) {
        lc = c;
        c  = chars[pos + i];

        if (lc == '\\') {
          n--;

          switch(c) {
            case '\\' :
              A[n] = '\\';

              break;

            case 'r' :
              A[n - 1] = '\r';

              break;

            case 'n' :
              A[n++] = '\n';

              break;

            case 't' :
              A[n++] = '\t';

              break;

            case 'u' :
              if (i + 4 < len) {
                if (chars[pos + i + 1] == 'u') {
                  c = '\\';

                  break;
                }

                A[n++] = unicodeStringToChar(chars, pos + i + 1);
                i      += 4;
              } else {
                A[n++] = 'u';
              }

              break;

            default :
              A[n++] = c;

              break;
          }
        } else {
          A[n++] = c;
        }
      }
    }

    ret._length += n;

    return ret;
  }

  /**
   * Unquotes a quoted string and resolves escaped characters, rewriting the
   * array values as necessary
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   *
   * @return the length of the cleaned string
   *
   */
  public static final int cleanQuotedEx(char[] chars, int pos, int len) {
    int n    = pos;
    int opos = pos;

    if (len > 0) {
      char lc = 0;
      char c  = 0;

      c = chars[pos];

      if ((c == DQT) || (c == SQT)) {
        if (chars[(pos + len) - 1] != c) {
          throw new FormatException("unterminated string (" + (pos + len) + ")");
        }

        pos++;
        len -= 2;
      }

      for (int i = 0; i < len; i++) {
        lc = c;
        c  = chars[pos + i];

        if (lc == '\\') {
          n--;

          switch(c) {
            case '\\' :
              chars[n] = '\\';

              break;

            case 'r' :
              chars[n++] = '\r';

              break;

            case 'n' :
              chars[n++] = '\n';

              break;

            case 't' :
              chars[n++] = '\t';

              break;

            case 'u' :
              if (i + 4 < len) {
                if (chars[pos + i + 1] == 'u') {
                  c = '\\';

                  break;
                }

                chars[n++] = unicodeStringToChar(chars, pos + i + 1);
                i          += 4;
              } else {
                chars[n++] = 'u';
              }

              break;

            case '\"' :
              chars[n++] = '\"';

              break;

            default :
              chars[n++] = c;

              break;
          }
        } else {
          chars[n++] = c;
        }
      }

      len = n - opos;
    }

    return (len < 0)
           ? 0
           : len;
  }

  /**
   * Unquotes a quoted string and resolves escaped characters and flattens
   * whitespace, rewriting the array values as necessary
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param flatten
   *          {@inheritDoc}
   *
   * @return the length of the cleaned and flattened string
   *
   * @throws java.text.ParseException
   *           if an error occurs
   */
  public static final int cleanQuotedEx(char[] chars, int pos, int len, boolean flatten)
          throws java.text.ParseException {
    int n    = pos;
    int opos = pos;

    if (len > 0) {
      char lc = 0;
      char c  = 0;

      c = chars[pos];

      if ((c == DQT) || (c == SQT)) {
        if (chars[(pos + len) - 1] != c) {
          throw new java.text.ParseException("unterminated string:" + new String(chars, opos, len), pos + len);
        }

        pos++;
        len -= 2;
      }

      for (int i = 0; i < len; i++) {
        lc = c;
        c  = chars[pos + i];

        if (lc == '\\') {
          n--;

          switch(c) {
            case '\\' :
              chars[n] = '\\';

              break;

            case 'r' :
              chars[n++] = '\r';

              break;

            case 'n' :
              chars[n++] = '\n';

              break;

            case 't' :
              chars[n++] = '\t';

              break;

            case 'u' :
              if (i + 4 < len) {
                if (chars[pos + i + 1] == 'u') {
                  c = '\\';

                  break;
                }

                chars[n++] = unicodeStringToChar(chars, pos + i + 1);
                i          += 4;
              } else {
                chars[n++] = 'u';
              }

              break;

            case '\"' :
              chars[n++] = '\"';

              break;

            default :
              chars[n++] = c;

              break;
          }
        } else if (Character.isWhitespace(c) && (Character.isWhitespace(lc))) {
          if (!flatten) {
            chars[n++] = c;
          }
        } else if (Character.isWhitespace(c) && flatten) {
          chars[n++] = ' ';
        } else {
          chars[n++] = c;
        }
      }

      len = n - opos;
    }

    return (len < 0)
           ? 0
           : len;
  }

  /**
   * Clears the scanner's contents
   */
  public void clear() {
    oContent   = null;
    theContent = null;
    theDelims  = null;
    currentLen = 0;
    currentPos = 0;
  }

  /**
   * Closes the scanner
   */
  public void close() {}

  /**
   * Consumes the specified number of characters starting at the current scan
   * position.
   *
   * @param len
   *          the number of characters
   *
   * @return the actual number of characters consumed
   */
  public int consume(int len) {
    if (currentLen < 1) {
      return 0;
    }

    if (len > currentLen) {
      len = currentLen;
    }

    currentLen -= len;
    currentPos += len;

    return len;
  }

  /**
   * Counts the number of tokens in the specified string
   *
   * @param s
   *          the string
   * @param c
   *          the token character separator
   *
   * @return the number of tokens
   */
  public static int countTokens(String s, char c) {
    int p = 0;
    int n = s.indexOf(c);

    if (n == -1) {
      return 1;
    }

    int cnt = 0;

    do {
      cnt++;
      p = n + 1;
    } while((n = s.indexOf(c, p)) != -1);

    return cnt + 1;
  }

  public static String encode(String str) {
    return encode(str, null).toString();
  }

  public static CharArray encode(String str, CharArray out) {
    final int len = (str == null)
                    ? 0
                    : str.length();

    if (out == null) {
      out         = (CharArray) perThreadCharArray.get();
      out._length = 0;
    }

    if (len > 0) {
      encode(str.toCharArray(), 0, len, out);
    }

    return out;
  }

  public static void encode(char[] chars, int pos, int len, CharArray out) {
    char c;

    for (int i = 0; i < len; i++) {
      c = chars[pos + i];

      if (c > 126) {
        charToUnicodeString(c, out, true);
      } else {
        out.append(c);
      }
    }
  }

  public static final String escape(String s) {
    if ((s == null) || (s.length() == 0)) {
      return s;
    }

    CharArray out = (CharArray) perThreadCharArray.get();

    out._length = 0;

    return escape(s, false, out).toString();
  }

  /**
   * Escapes a string
   *
   * @param s
   *          the string
   * @param do_quote
   *          true to escape quotes the result; false otherwise
   * @param out
   *          the array to use to store the result
   * @return the passed in array
   */
  public static CharArray escape(String s, boolean do_quote, CharArray out) {
    if ((s != null) && (s.length() > 0)) {
      char[] a = s.toCharArray();

      escape(a, 0, a.length, do_quote, out);
    }

    return out;
  }

  /**
   * Escapes a string
   *
   * @param chars
   *          the characters
   * @param pos
   *          the starting position within the characters array
   * @param len
   *          the number of characters
   * @param do_quote
   *          true to escape quotes the result; false otherwise
   * @param out
   *          the array to use to store the result
   * @return the passed in array
   */
  public static CharArray escape(char[] chars, int pos, int len, boolean do_quote, CharArray out) {
    char c;

    for (int i = 0; i < len; i++) {
      c = chars[pos + i];

      switch(c) {
        case '\\' :
          out.append('\\');

          break;

        case '\r' :
          out.append('\\');
          c = 'r';

          break;

        case '\n' :
          out.append('\\');
          c = 'n';

          break;

        case '\t' :
          out.append('\\');
          c = 't';

          break;

        case '\b' :
          out.append('\\');
          c = 'b';

          break;

        case '\f' :
          out.append('\\');
          c = 'f';

          break;

        case '\"' :
          if (do_quote) {
            out.append('\\');
          }

          c = '\"';

          break;

        default :
          if ((c < 32) || (c > 126)) {
            //out.append("&#");
            charToUnicodeString(c, out, true);
            //out.append(';');

            continue;
          }

          break;
      }

      out.append(c);
    }

    return out;
  }

  /**
   * Finds the next token delimited by the specified character. This method
   * consumes characters as it scans.
   *
   * @param c
   *          the character delimiter
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public int[] findToken(char c) {
    int[] tok = findToken(theContent, currentPos, currentLen, c);

    if (tok != null) {
      currentPos     += tok[1];
      currentLen     -= tok[1];
      foundDelimiter = (char) tok[2];

      if (foundDelimiter != 0) {
        currentPos++;
        currentLen--;
      }
    }

    return tok;
  }

  /**
   * Finds the next token delimited by the delimiters specified with the
   * <code>setTokenDelimiters()</code> method. This method consumes characters
   * as it scans.
   *
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   *
   * @see #setTokenDelimiters
   */
  public final int[] findToken(boolean checkQuote, boolean checkParam) {
    int[] tok = findToken(currentPos, currentLen, checkQuote, checkParam);

    if (tok != null) {
      currentPos += tok[1];
      currentLen -= tok[1];

      if (foundDelimiter != 0) {
        currentPos++;
        currentLen--;
      }
    }

    return tok;
  }

  /**
   * Finds the next token delimited by the specified character. This method
   * consumes characters as it scans.
   *
   * @param c
   *          the character
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public final int[] findToken(char c, boolean checkQuote, boolean checkParam) {
    int[] tok = findToken(theContent, currentPos, currentLen, c, checkQuote, checkParam);

    if (tok != null) {
      currentPos     += tok[1];
      currentLen     -= tok[1];
      foundDelimiter = (char) tok[2];

      if (foundDelimiter != 0) {
        currentPos++;
        currentLen--;
      }
    }

    return tok;
  }

  /**
   * Finds the next token delimited by the specified character. This method
   * consumes characters as it scans.
   *
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public int[] findToken(int pos, int len, char c) {
    int[] tok = findToken(theContent, pos, len, c);

    if (tok != null) {
      foundDelimiter = (char) tok[2];
    }

    return tok;
  }

  /**
   * Finds the next token delimited by the specified character.
   *
   * @param chars
   *          the character array
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public static final int[] findToken(char[] chars, char c, boolean checkQuote, boolean checkParam) {
    return findToken(chars, 0, chars.length, c, checkQuote, checkParam);
  }

  /**
   * Finds the next token delimited by the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public static final int[] findToken(char[] chars, int pos, int len, char c) {
    int   d;
    int   i   = pos;
    int[] tok = null;
    int   n   = pos + len;
    char  ft  = 0;

    if (pos >= n) {
      return null;
    }

    while(i < n) {
      d = chars[i];

      if (d == c) {
        ft = c;

        break;
      }

      i++;
    }

    tok    = new int[3];
    tok[0] = pos;
    tok[1] = i - pos;
    tok[2] = ft;

    return tok;
  }

  /**
   * Finds the next token delimited by the delimiters specified with the
   * <code>setTokenDelimiters()</code> method. This method consumes characters
   * as it scans.
   *
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   *
   * @see #setTokenDelimiters
   */
  public final int[] findToken(int pos, int len, boolean checkQuote, boolean checkParam) {
    char    d;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int[]   tok          = null;
    int     i            = pos;
    int     n            = pos + len;
    char[]  delims       = theDelims;
    char[]  chars        = theContent;
    int     dlen         = theDelims.length;
    char    lparen       = paramStartChar;
    char    rparen       = paramEndChar;
    char    qt           = QT;
    char    delim1       = (dlen > 0)
                           ? delims[0]
                           : 0;
    char    delim2       = (dlen > 1)
                           ? delims[1]
                           : 0;

    foundDelimiter = 0;

    if (pos >= n) {
      return null;
    }

    while(i < n) {
      d = chars[i];

      if (dlen < 3) {
        if (!bInQuote && (nInFuncIndex == 0) && ((d == delim1) || (d == delim2))) {
          foundDelimiter = d;

          break;
        }
      } else if (!bInQuote && (nInFuncIndex == 0) && isTokenChar(0, d, delims, dlen)) {
        foundDelimiter = d;

        break;
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == lparen) &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == rparen) &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i++;
    }

    tok    = new int[2];
    tok[0] = pos;
    tok[1] = i - pos;

    return tok;
  }

  /**
   * Finds the next token delimited by the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public static final int[] findToken(char[] chars, int pos, int len, char c, boolean checkQuote, boolean checkParam) {
    char    d;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int[]   tok          = null;
    char    ft           = 0;
    int     i            = pos;
    int     n            = pos + len;
    char    qt           = 0;

    if (pos >= n) {
      return tok;
    }

    while(i < n) {
      d = chars[i];

      if ((d == c) &&!bInQuote && (nInFuncIndex == 0)) {
        ft = d;

        break;
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == '(') &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == ')') &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i++;
    }

    tok    = new int[3];
    tok[0] = pos;
    tok[1] = i - pos;
    tok[2] = ft;

    return tok;
  }

  /**
   * Finds the next token delimited by any of the specified characters.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param delims
   *          the set of character delimiters
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public static final int[] findToken(char[] chars, int pos, int len, char[] delims, boolean checkQuote,
          boolean checkParam) {
    char    d;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int[]   tok          = null;
    char    ft           = 0;
    int     i            = pos;
    int     n            = pos + len;
    int     dlen         = delims.length;
    char    qt           = 0;

    if (pos >= n) {
      return tok;
    }

    while(i < n) {
      d = chars[i];

      if (isTokenChar(0, d, delims, dlen) &&!bInQuote && (nInFuncIndex == 0)) {
        ft = d;

        break;
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == '(') &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == ')') &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i++;
    }

    tok    = new int[3];
    tok[0] = pos;
    tok[1] = i - pos;
    tok[2] = ft;

    return tok;
  }

  /**
   * Finds the next token delimited by the specified character. This method
   * consumes characters as it scans. The token is trimmed of whitespace before
   * it is returned
   *
   * @param c
   *          the character delimiter
   *
   * @return <code>null</code> or a 3 element integer array representing the
   *         starting position of the token within the array, the length of the
   *         token, and the character that terminated the token.
   */
  public int[] findTokenAndTrim(char c) {
    int[] tok = findToken(c);

    if (tok != null) {
      trim(tok);
    }

    return tok;
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   * This method does NOT affect the scan position (that is, no characters are
   * consumed)
   *
   * @param c
   *          the character
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public int indexOf(char c) {
    return indexOf(theContent, currentPos, currentLen, c);
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param c
   *          the character
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public int indexOf(char c, boolean checkQuote, boolean checkParam) {
    return indexOf(theContent, currentPos, currentLen, c, checkQuote, checkParam, paramStartChar, paramEndChar);
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the characters to find
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param ignorecase
   *          <code>true</code> specifies that case should be ignored
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public int indexOf(char chars[], boolean checkQuote, boolean checkParam, boolean ignorecase) {
    return indexOf(theContent, currentPos, currentLen, chars, checkQuote, checkParam, paramStartChar, paramEndChar,
                   ignorecase);
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to search
   * @param c
   *          the character
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int indexOf(char[] chars, int pos, int len, char c) {
    int ret = -1;

    len += pos;

    while(pos < len) {
      if (chars[pos] == c) {
        ret = pos;

        break;
      }

      pos++;
    }

    return ret;
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int indexOf(char[] chars, int pos, int len, char c, boolean checkQuote, boolean checkParam) {
    return indexOf(chars, pos, len, c, checkQuote, checkParam, '(', ')');
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param a
   *          the character array delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param ignorecase
   *          <code>true</code> to ignore case; <code>false</code> otherwise
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int indexOf(char[] chars, int pos, int len, char[] a, boolean checkQuote, boolean ignorecase) {
    return indexOf(chars, pos, len, a, checkQuote, false, '\0', '\0', ignorecase);
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param ps
   *          parameter start character
   * @param pe
   *          parameter end character
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int indexOf(char[] chars, int pos, int len, char c, boolean checkQuote, boolean checkParam, char ps,
                            char pe) {
    char    d            = 0;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int     i            = pos;
    int     n            = pos + len;
    char    qt           = 0;

    if (pos >= n) {
      return -1;
    }

    while(i < n) {
      d = chars[i];

      if ((d == c) &&!bInQuote && (nInFuncIndex == 0)) {
        return i;
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == ps) &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == pe) &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i++;
    }

    return -1;
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param a
   *          the character array delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param ps
   *          parameter start character
   * @param pe
   *          parameter end character
   * @param ignorecase
   *          <code>true</code> to ignore case; <code>false</code> otherwise
   *
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int indexOf(char[] chars, int pos, int len, char[] a, boolean checkQuote, boolean checkParam, char ps,
                            char pe, boolean ignorecase) {
    char    d            = 0;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int     i            = pos;
    int     n            = pos + len;
    char    qt           = 0;

    if (pos >= n) {
      return -1;
    }

    char c = a[0];
    char dd, cc;

    if (ignorecase) {
      c = Character.toLowerCase(c);
    }

    int     clen = a.length;
    int     p;
    boolean found = false;

    while(i < n) {
      d = chars[i];

      if (ignorecase) {
        d = Character.toLowerCase(d);
      }

      if ((d == c) &&!bInQuote && (nInFuncIndex == 0)) {
        if (i + clen <= n) {
          p     = 1;
          found = true;

          while(p < clen) {
            dd = chars[i + p];
            cc = a[p++];

            if (ignorecase) {
              cc = Character.toLowerCase(cc);
              dd = Character.toLowerCase(dd);
            }

            if (cc != dd) {
              found = false;

              break;
            }
          }

          if (found) {
            return i;
          }
        }
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == ps) &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == pe) &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i++;
    }

    return -1;
  }

  /**
   * Get the index of the next non-whitespace character
   *
   * @param start
   *          the position to start the search from
   *
   * @return the index of the next non-whitespace character or -1
   */
  public int indexOfNonWhiteSpace(int start) {
    int    len   = currentLen + currentPos;
    char[] chars = theContent;

    while(start < len) {
      if (!Character.isWhitespace(chars[start++])) {
        return start - 1;
      }
    }

    return -1;
  }

  /**
   * Returns the position of the first occurrence of the specified character.
   *
   * @param c
   *          the charactes to find
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public int lastIndexOf(char c, boolean checkQuote, boolean checkParam) {
    return lastIndexOf(theContent, currentPos, currentLen, c, checkQuote, checkParam, paramStartChar, paramEndChar);
  }

  /**
   * Returns the index of the last occurrence of the specified character in the
   * specified array.
   *
   * @param chars
   *          the array of characters
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters to search
   * @param c
   *          the byte the character to find
   *
   * @return the index of the last occurrence of the specified byte; returns -1
   *         if the object is not found.
   */
  public static int lastIndexOf(char[] chars, int pos, int len, char c) {
    len = pos + len - 1;

    if (len < pos) {
      return -1;
    }

    pos--;

    while(len > pos) {
      if (c == chars[len]) {
        return len;
      }

      len--;
    }

    return -1;
  }

  /**
   * Returns the index of the last occurrence of the specified character in the
   * specified array.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int lastIndexOf(char[] chars, int pos, int len, char c, boolean checkQuote, boolean checkParam) {
    return lastIndexOf(chars, pos, len, c, checkQuote, checkParam, '(', ')');
  }

  /**
   * Returns the index of the last occurrence of the specified character in the
   * specified array.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param ps
   *          parameter start character
   * @param pe
   *          parameter end character
   * @return a integer representing the index of the first occurrence of the
   *         character, or -1 if the character does not occur
   */
  public static int lastIndexOf(char[] chars, int pos, int len, char c, boolean checkQuote, boolean checkParam,
                                char ps, char pe) {
    char    d            = 0;
    boolean bInQuote     = false;
    int     nInFuncIndex = 0;
    int     i            = pos + len - 1;
    char    qt           = 0;

    if (i < pos) {
      return -1;
    }

    pos--;

    while(i > pos) {
      d = chars[i];

      if ((d == c) &&!bInQuote && (nInFuncIndex == 0)) {
        return i;
      }

      if (checkQuote && ((d == DQT) || (d == SQT))) {
        if ((i == pos) || (chars[i - 1] != '\\')) {
          if (bInQuote) {
            bInQuote = qt != d;
          } else {
            bInQuote = true;
            qt       = d;
          }
        }
      } else if (checkParam && (d == ps) &&!bInQuote) {
        nInFuncIndex++;
      } else if (checkParam && (d == pe) &&!bInQuote && (nInFuncIndex > 0)) {
        nInFuncIndex--;
      }

      i--;
    }

    return -1;
  }

  public void mark(int readAheadLimit) {
    markPos = currentPos;
    markLen = currentLen;
  }

  public boolean markSupported() {
    return true;
  }

  /**
   * Gets the next valid token
   *
   * @return a string representing the value
   */
  public String nextToken() {
    int[] tok = this.findToken(false, false);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param c
   *          the character delimiter
   *
   * @return a string representing the value
   */
  public String nextToken(char c) {
    int[] tok = this.findToken(c);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public String nextToken(boolean checkQuote, boolean checkParam) {
    int[] tok = this.findToken(checkQuote, checkParam);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param c
   *          the character delimiter
   * @param trim
   *          <code>true</code> specifies white space should be trimed from both
   *          ends of the returned string
   *
   * @return a string representing the value
   */
  public String nextToken(char c, boolean trim) {
    int[] tok = this.findToken(c);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (trim) {
      trim(tok);
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public String nextToken(char c, boolean checkQuote, boolean checkParam) {
    int[] tok = this.findToken(c, checkQuote, checkParam);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          <code>true</code> specifies white space should be trimed from both
   *          ends of the returned string
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public String nextToken(char c, boolean checkQuote, boolean checkParam, boolean trim) {
    int[] tok = this.findToken(c, checkQuote, checkParam);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (trim) {
      trim(tok);
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the next valid token
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          <code>true</code> specifies that white space should be trimed from
   *          both ends of the returned string
   * @param unquote
   *          <code>true</code> specifies that surrounding quotes should be
   *          removed from the returned string
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public String nextToken(char c, boolean checkQuote, boolean checkParam, boolean trim, boolean unquote) {
    int[] tok = this.findToken(c, checkQuote, checkParam);

    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (trim) {
      trim(tok);
    }

    if (unquote && (tok[1] > 1)) {
      int pos = tok[0];

      c = 0;

      if ((theContent[pos] == SQT) || (theContent[pos] == DQT)) {
        c = theContent[pos];
        tok[0]++;
        tok[1]--;
      }

      pos = tok[0] + tok[1] - 1;

      if ((theContent[pos] == c)) {
        tok[1]--;
      }
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Parses and options string of name/value pairs separated by the specified
   * token
   *
   * @param value
   *          the value to parse
   * @param c
   *          the token separating the pairs
   *
   * @return the map of options
   */
  public static Map<String, Object> parseOptionString(String value, char c) {
    return parseOptionString(value, null, c, true);
  }

  /**
   * Parses and options string of name/value pairs separated by the specified
   * token
   *
   * @param value
   *          the value to parse
   * @param out
   *          the map to hold the parameters
   * @param c
   *          the token separating the pairs
   * @param unquote
   *          true to unquote; false otherwise
   *
   *
   * @return {@inheritDoc}
   */
  public static Map<String, Object> parseOptionString(String value, Map<String, Object> out, char c, boolean unquote) {
    if ((value == null) || (value.length() == 0)) {
      return out;
    }

    CharScanner sc = (CharScanner) perThreadScanner.get();

    sc.reset(value);

    return parseOptionStringEx(sc, out, c, unquote);
  }

  /**
   * Parses and options string of name/value pairs separated by the specified
   * token
   *
   * @param value
   *          the value to parse
   * @param c
   *          the token separating the pairs
   *
   * @return the map of options
   */
  public static Map<String, String> parseOptionStringEx(String value, char c) {
    return parseOptionStringEx(value, c, true);
  }

  /**
   * Parses and options string of name/value pairs separated by the specified
   * token
   *
   * @param value
   *          the value to parse
   * @param c
   *          the token separating the pairs
   * @param unquote
   *          true to unquote; false otherwise
   *
   * @return the map of options
   */
  public static Map<String, String> parseOptionStringEx(String value, char c, boolean unquote) {
    if ((value == null) || (value.length() == 0)) {
      return null;
    }

    LinkedHashMap out = new LinkedHashMap<String, String>();

    parseOptionString(value, out, c, unquote);

    return out;
  }

  /**
   * Gets the character at the current scan position. The character is consumed
   *
   * @return a integer representing the current character or -1 if there are no
   *         characters
   */
  public int read() {
    if (currentLen < 1) {
      return -1;
    }

    int n = theContent[currentPos++];

    currentLen--;

    return n;
  }

  /**
   * Read characters into a portion of an array
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the maximum number of characters to read
   *
   * @return The actual number of characters read, or -1 if the end of the
   *         stream has been reached
   */
  public int read(char[] chars, int pos, int len) {
    if (currentLen < 1) {
      return -1;
    }

    if (len > currentLen) {
      len = currentLen;
    }

    System.arraycopy(theContent, currentPos, chars, pos, len);
    currentLen -= len;
    currentPos += len;

    return len;
  }

  /**
   * Reads the specified number of characters starting at the current scan
   * position. The characters are consumed
   *
   * @param len
   *          the number of characters
   *
   * @return the read characters or <code>null</code> if there are no characters
   */
  public char[] readChars(int len) {
    if (currentLen < 1) {
      return null;
    }

    if (len > currentLen) {
      len = currentLen;
    }

    char[] chars = new char[len];

    System.arraycopy(theContent, currentPos, chars, 0, len);
    currentLen -= len;
    currentPos += len;

    return chars;
  }

  /**
   * Reads the specified number of characters starting at the current scan
   * position. The characters are consumed
   *
   * @param len
   *          the number of characters
   *
   * @return the read characters or <code>null</code> if there are no characters
   */
  public String readString(int len) {
    if (currentLen < 1) {
      return null;
    }

    if (len > currentLen) {
      len = currentLen;
    }

    String s = new String(theContent, currentPos, len);

    currentLen -= len;
    currentPos += len;

    return s;
  }

  public void reset() throws IOException {
    setPosAndLength(markPos, markLen);
  }

  /**
   * Resets the contents of the scanner
   *
   * @param chars
   *          the character array
   */
  public void reset(char[] chars) {
    reset(chars, 0, chars.length, false);
  }

  /**
   * Resets the contents of the scanner
   *
   * @param str
   *          the new contents
   */
  public void reset(String str) {
    reset(str, 0, (str != null)
                  ? str.length()
                  : 0);
  }

  /**
   * Resets the contents of the scanner
   *
   * @param ca
   *          the character array
   * @param copy
   *          <code>true</code> specifies that scanner should make a copy of the
   *          array.
   */
  public void reset(CharArray ca, boolean copy) {
    reset(ca.A, 0, ca._length, copy);
  }

  /**
   * Resets the contents of the scanner
   *
   * @param str
   *          the new contents
   * @param pos
   *          the starting position within the string
   * @param len
   *          the number of characters in the string to use
   */
  public void reset(String str, int pos, int len) {
    if (oContent != null) {
      theContent = oContent;
      oContent   = null;
    }

    if ((theContent == null) || (len > theContent.length)) {
      theContent = new char[len];
    }

    if (str != null) {
      str.getChars(pos, pos + len, theContent, 0);
    }

    currentPos  = 0;
    currentLen  = len;
    originalPos = 0;
    mark(-1);
  }

  /**
   * Resets the contents of the scanner
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param copy
   *          <code>true</code> specifies that scanner should make a copy of the
   *          array.
   */
  public void reset(char[] chars, int pos, int len, boolean copy) {
    if (copy) {
      if (oContent != null) {    // prevents the passed in array from being overwritten on subsequent resets
        theContent = oContent;
        oContent   = null;
      }

      if ((theContent == null) || (len > theContent.length)) {
        theContent = new char[len];
      }

      System.arraycopy(chars, pos, theContent, 0, len);
      currentPos  = 0;
      currentLen  = len;
      originalPos = 0;
    } else {
      if (oContent == null) {
        oContent = theContent;
      }

      theContent  = chars;
      currentPos  = pos;
      currentLen  = len;
      originalPos = pos;
    }

    mark(-1);
  }

  /**
   * {@inheritDoc}
   *
   * @param s
   *          {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public long skip(long s) {
    int n = (int) s;

    if (currentLen < n) {
      n = currentLen;
    }

    currentPos += n;
    currentLen -= n;

    return n;
  }

  /**
   * Skips over the the next valid token
   *
   * @param c
   *          the character delimiter
   *
   * @return true if a valid token was skipped; false otherwise
   */
  public boolean skipToken(char c) {
    int[] tok = this.findToken(c);

    if ((tok == null) || (theContent == null)) {
      return false;
    }

    return true;
  }

  /**
   * Tests if the current scanner position starts with the specified prefix
   *
   * @param prefix
   *          the prefix.
   *
   * @return <code>true</code> if is does; false otherwise
   */
  public boolean startsWith(char[] prefix) {
    if ((currentLen < 1) || (prefix == null)) {
      return false;
    }

    return CharArray.indexOf(theContent, currentPos, currentLen, prefix, 0, prefix.length, 0) == 0;
  }

  /**
   * Tests if the current scanner position starts with the specified prefix
   *
   * @param prefix
   *          the prefix.
   *
   * @return <code>true</code> if is does; false otherwise
   */
  public boolean startsWith(String prefix) {
    return (prefix == null)
           ? false
           : startsWith(prefix.toCharArray());
  }

  /**
   * Removes bad characters from both ends of the specified array. The actual
   * content of the underlying array is not affected.
   *
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   * @param bad
   *          the bad characters
   *
   * @return a token representing the cleaned up array.
   */
  public int[] strip(int[] tok, char[] bad) {
    if ((tok == null) || (bad == null)) {
      return null;
    }

    return strip(theContent, tok[0], tok[1], bad, leftTrim, rightTrim);
  }

  /**
   * Removes bad characters from both ends of the specified array. The actual
   * content of the underlying array is not affected.
   *
   * @param chars
   *          the character array
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   * @param bad
   *          the bad characters
   *
   * @return a token representing the cleaned up array.
   */
  public int[] strip(char[] chars, int[] tok, char[] bad) {
    if ((tok == null) || (bad == null)) {
      return null;
    }

    return strip(chars, tok[0], tok[1], bad, leftTrim, rightTrim);
  }

  /**
   * Removes bad characters from both ends of the specified array. The actual
   * content of the underlying array is not affected.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param bad
   *          the bad characters
   * @param left
   *          the strip from the left side
   * @param right
   *          the strip from the right side
   *
   * @return a token representing the cleaned up array
   */
  public static int[] strip(char[] chars, int pos, int len, char[] bad, boolean left, boolean right) {
    int tok[] = new int[] { 0, 0 };

    strip(tok, chars, pos, len, bad, left, right);

    return (tok[1] == 0)
           ? null
           : tok;
  }

  /**
   * Converts all of the characters for the current token to lower case using
   * the rules of the default locale.
   *
   * @return the current scanner object
   */
  public CharScanner toLowerCase() {
    int        i   = currentPos;
    final int  len = currentLen;
    final char a[] = theContent;

    while(i < len) {
      a[i] = Character.toLowerCase(a[i]);
      i++;
    }

    return this;
  }

  /**
   * Converts all of the characters for the specified token to lower case using
   * the rules of the default locale.
   *
   * @param tok
   *          the token
   *
   * @return the current scanner object
   */
  public CharScanner toLowerCase(int tok[]) {
    int        i   = tok[0];
    final int  len = tok[1];
    final char a[] = theContent;

    while(i < len) {
      a[i] = Character.toLowerCase(a[i]);
      i++;
    }

    return this;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public String toString() {
    return this.getLeftOver();
  }

  /**
   * Converts all of the characters for the current token to upper case using
   * the rules of the default locale.
   *
   * @return the current scanner object
   */
  public CharScanner toUpperCase() {
    int        i   = currentPos;
    final int  len = currentLen;
    final char a[] = theContent;

    while(i < len) {
      a[i] = Character.toUpperCase(a[i]);
      i++;
    }

    return this;
  }

  /**
   * Converts all of the characters for the specified token to upper case using
   * the rules of the default locale.
   *
   * @param tok
   *          the token
   * @return the current scanner object
   */
  public CharScanner toUpperCase(int tok[]) {
    int        i   = tok[0];
    final int  len = tok[1];
    final char a[] = theContent;

    while(i < len) {
      a[i] = Character.toUpperCase(a[i]);
      i++;
    }

    return this;
  }

  /**
   * Removes white space from both ends of the current position of the scanner;
   * it trims all ASCII control characters as well. The actual content of the
   * underlying array is not affected.
   *
   * @return the current scanner object
   */
  public CharScanner trim() {
    if (theContent == null) {
      return this;
    }

    int[] tok;

    if (trimChars != null) {
      tok = strip(theContent, currentPos, currentLen, trimChars, leftTrim, rightTrim);
    } else {
      tok = trim(theContent, currentPos, currentLen, leftTrim, rightTrim);
    }

    if (tok != null) {
      currentPos = tok[0];
      currentLen = tok[1];
    }

    return this;
  }

  /**
   * Removes white space from both ends of the specified token; it trims all
   * ASCII control characters as well. The actual content of the underlying
   * array is not affected.
   *
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @return a token representing the trimmed array.
   */
  public int[] trim(int[] tok) {
    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (trimChars != null) {
      strip(tok, theContent, tok[0], tok[1], trimChars, leftTrim, rightTrim);
    } else {
      trim(tok, theContent, tok[0], tok[1], leftTrim, rightTrim);
    }

    return (tok[1] == 0)
           ? null
           : tok;
  }

  /**
   * Removes white space from both ends of the specified array; it trims all
   * ASCII control characters as well. The actual content of the underlying
   * array is not affected.
   *
   * @param chars
   *          the character array
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @return a token representing the trimmed array
   */
  public int[] trim(char[] chars, int[] tok) {
    if ((tok == null) || (chars == null)) {
      return null;
    }

    return trim(chars, tok[0], tok[1], leftTrim, rightTrim);
  }

  /**
   * Removes white space from both ends of the specified array; it trims all
   * ASCII control characters as well. The actual content of the underlying
   * array is not affected.
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @param left
   *          the strip from the left side
   * @param right
   *          the strip from the right side
   *
   * @return a token representing the trimmed array.
   */
  public static int[] trim(char[] chars, int pos, int len, boolean left, boolean right) {
    int tok[] = new int[] { 0, 0 };

    trim(tok, chars, pos, len, left, right);

    return (tok[1] == 0)
           ? null
           : tok;
  }

  /**
   * Removes white space from both ends of the current position of the scanner;
   * it trims all ASCII control characters as well. The actual content of the
   * underlying array is not affected.
   *
   * @return a token representing the trimmed array.
   */
  public int[] trimEx() {
    if (theContent == null) {
      return null;
    }

    if (trimChars != null) {
      return strip(theContent, currentPos, currentLen, trimChars, leftTrim, rightTrim);
    } else {
      return trim(theContent, currentPos, currentLen, leftTrim, rightTrim);
    }
  }

  /**
   * Resolves escaped characters in a string, rewriting the content array values
   * as necessary
   *
   * @return the current scanner object
   */
  public CharScanner unesacpe() {
    currentLen = unescapeStringEx(theContent, currentPos, currentLen);

    return this;
  }

  /**
   * Resolves escaped characters in a string
   *
   * @param s
   *          the string to unescape
   * @return the unescaped string
   */
  public static final String unescape(String s) {
    if ((s == null) || (s.length() == 0)) {
      return s;
    }

    CharArray out = (CharArray) perThreadCharArray.get();

    out.set(s);

    return unescapeEx(out.A, 0, out._length);
  }

  /**
   * Resolves escaped characters in a string, rewriting the array values as
   * necessary
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   *
   * @return the cleaned string
   *
   */
  public static final String unescapeEx(char[] chars, int pos, int len) {
    len = unescapeStringEx(chars, pos, len);

    return new String(chars, pos, len);
  }

  /**
   * Resolves escaped characters in a string, rewriting the array values as
   * necessary
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   * @return the length of the cleaned string
   *
   */
  public static final int unescapeStringEx(char[] chars, int pos, int len) {
    int n    = pos;
    int opos = pos;

    if (len > 0) {
      char lc = 0;
      char c  = 0;

      for (int i = 0; i < len; i++) {
        lc = c;
        c  = chars[pos + i];

        if (lc == '\\') {
          n--;

          switch(c) {
            case '\\' :
              chars[n] = '\\';

              break;

            case 'r' :
              chars[n++] = '\r';

              break;

            case 'n' :
              chars[n++] = '\n';

              break;

            case 't' :
              chars[n++] = '\t';

              break;

            case 'u' :
              if (i + 4 < len) {
                if (chars[pos + i + 1] == 'u') {
                  c = '\\';

                  break;
                }

                chars[n++] = unicodeStringToChar(chars, pos + i + 1);
                i          += 4;
              } else {
                chars[n++] = 'u';
              }

              break;

            default :
              chars[n++] = c;

              break;
          }
        } else if ((c == '&') && (i + 1 < len) && (chars[pos + i + 1] == '#')) {
          if ((i + 7 < len) && (chars[pos + i + 2] == 'x') && (chars[pos + i + 7] == ';')) {
            chars[n++] = unicodeStringToChar(chars, pos + i + 3);
            i          += 7;
            lc         = 0;
          } else {
            c = htmlStringToCharacter(chars, pos + i + 1, len - (i + 1));

            if (c > 0) {
              chars[n++] = c;

              while(chars[pos + i] != ';') {
                i++;
              }

              lc = 0;
            } else {
              c          = lc;
              chars[n++] = c;
            }
          }
        } else {
          chars[n++] = c;
        }
      }

      len = n - opos;
    }

    return (len < 0)
           ? 0
           : len;
  }

  /**
   * Converts a 4 character unicode hexadecimal string to a java character
   *
   * @param chars
   *          the character array
   * @param pos
   *          the starting position within the array
   *
   * @return a set of bytes representing the value of the hexadecimal string
   */
  public static char unicodeStringToChar(char[] chars, int pos) {
    char c = 0;
    int  n;
    int  len = pos + 4;

    while(pos < len) {
      n = chars[pos++];

      if (n > 64) {
        n -= 55;
      } else if (n > 96) {
        n -= 87;
      } else if ((n > 47) && (n < 58)) {
        n -= 48;
      } else {
        n = 0;
      }

      c <<= 4;
      c |= (n & 0xf);
    }

    return c;
  }

  /**
   * Optionally trims whitespace and them removes single or double quotes from
   * the ends of a string
   *
   * @param trim
   *          true to trim the string befor it is unquoted; false otherwise
   * @return the current scanner object
   */
  public CharScanner unquote(boolean trim) {
    if (theContent == null) {
      return this;
    }

    int[] tok = null;

    if (trim) {
      if (trimChars != null) {
        tok = strip(theContent, currentPos, currentLen, trimChars, leftTrim, rightTrim);
      } else {
        tok = trim(theContent, currentPos, currentLen, leftTrim, rightTrim);
      }
    }

    if (tok != null) {
      currentPos = tok[0];
      currentLen = tok[1];
    }

    if (currentLen > 1) {
      char c = theContent[currentPos];

      if ((c == SQT) || (c == DQT)) {
        currentPos++;
        currentLen--;

        if ((currentLen > 1) && (theContent[currentPos + currentLen - 1] == c)) {
          currentLen--;
        }
      }
    }

    return this;
  }

  /**
   * Optionally trims whitespace and them removes single or double quotes from
   * the ends of a string
   *
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @param trim
   *          true to trim the string befor it is unquoted; false otherwise
   * @return a token representing the unquoted array.
   */
  public int[] unquote(int[] tok, boolean trim) {
    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (trim) {
      tok = trim(tok);
    }

    if ((tok[1] > 1)) {
      int  pos = tok[0];
      char c   = 0;

      if ((theContent[pos] == SQT) || (theContent[pos] == DQT)) {
        c = theContent[pos];
        tok[0]++;
        tok[1]--;
        pos = tok[0] + tok[1] - 1;

        if ((theContent[pos] == c)) {
          tok[1]--;
        }
      }
    }

    return tok;
  }

  /**
   * Optionally trims whitespace and them removes single or double quotes from
   * the ends of a string
   *
   * @param str
   *          the string to unqote
   * @param trim
   *          true to trim the string befor it is unquoted; false otherwise
   * @return the unquoted string
   */
  public String unquote(String str, boolean trim) {
    final int len = (str == null)
                    ? 0
                    : str.length();

    if (len != 0) {
      final char c = str.charAt(0);

      if ((c == SQT) || (c == DQT) || Character.isWhitespace(c) || Character.isWhitespace(str.charAt(len - 1))) {
        final CharScanner sc = (CharScanner) perThreadScanner.get();

        sc.reset(str, 0, len);
        str = sc.unquote(str, trim);
      }
    }

    return str;
  }

  /**
   * Unconsumes the specified token
   *
   * @param tok
   *          the token
   */
  public void unread(int[] tok) {
    currentLen += tok[1];
    currentPos -= tok[1];
    // compensate for the token character
    currentPos--;
    currentLen++;

    if (currentPos < 0) {
      currentPos = 0;
    }

    if ((currentPos + currentLen) > theContent.length) {
      currentLen = theContent.length - currentPos;
    }

    if (currentLen < 0) {
      currentLen = 0;
    }
  }

  /**
   * Sets whether trim operations will trim the right side
   *
   * @param trim
   *          true to trimg the right side; false otherwise
   */
  public void setLeftTrim(boolean trim) {
    leftTrim = trim;
  }

  /**
   * Sets parameter characters used when checking if tokens are contained within
   * a parameter block
   *
   * @param left
   *          the left paren character
   * @param right
   *          the right paren character
   */
  public void setParameternCharacters(char left, char right) {
    paramStartChar = left;
    paramEndChar   = right;
  }

  /**
   * Sets/Resets the position and length of scan-able contents within the
   * current internal content array
   *
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   */
  public void setPosAndLength(int pos, int len) {
    currentPos = pos;
    currentLen = len;
  }

  /**
   * Set the character to use as the quote character when scanning
   *
   * @param c
   *          the quote character
   */
  public void setQuoteChar(char c) {
    QT = c;
  }

  /**
   * Sets whether trim operations will trim the right side
   *
   * @param trim
   *          true to trimg the right side; false otherwise
   */
  public void setRightTrim(boolean trim) {
    rightTrim = trim;
  }

  /**
   * Sets the delimiters to use to identify tokens
   *
   * @param delims
   *          the delimiters
   */
  public void setTokenDelimiters(char[] delims) {
    theDelims = delims;
  }

  /**
   * Sets the characters to strip when trimming. ASCII chars less that 0x21 will
   * also be stripped
   *
   * @param chars
   *          the chars
   */
  public void setTrimChars(char[] chars) {
    trimChars = chars;
  }

  /**
   * Gets the character at the specified position
   *
   * @param pos
   *          the position
   *
   * @return a integer representing the character or -1 if the position is out
   *         of range
   */
  public int getChar(int pos) {
    if ((pos < 0) || (pos >= theContent.length)) {
      return -1;
    }

    return theContent[pos];
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @return a string representing the left over contents
   */
  public String getConsumed() {
    if ((theContent == null) || (currentPos == 0)) {
      return null;
    }

    return new String(theContent, 0, currentPos);
  }

  /**
   * Gets the contents of the scanner
   *
   * @return the content array
   */
  public char[] getContent() {
    return theContent;
  }

  /**
   * Gets the character at the current scan position. The character is NOT
   * consumed
   *
   * @return a integer representing the current character or -1 if there are no
   *         characters
   */
  public int getCurrentChar() {
    if (currentLen < 1) {
      return -1;
    }

    return theContent[currentPos];
  }

  /**
   * Gets the character at the end of the scan-able set. The character is NOT
   * consumed
   *
   * @return a integer representing the last character or -1 if there are no
   *         characters
   */
  public int getLastChar() {
    if (currentLen < 1) {
      return -1;
    }

    return theContent[(currentPos + currentLen) - 1];
  }

  /**
   * Gets the last character for the specified token
   *
   * @param tok
   *          the token (a array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @return a integer representing the last character or -1 if there are no
   *         characters
   */
  public int getLastChar(int[] tok) {
    int n = (tok[0] + tok[1]) - 1;

    if ((n < 0) || (n >= theContent.length)) {
      return -1;
    }

    return theContent[n];
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @return a string representing the left over contents
   */
  public String getLeftOver() {
    if ((currentLen == 0) || (theContent == null)) {
      return null;
    }

    return new String(theContent, currentPos, currentLen);
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @return a character buffer representing the left over contents
   */
  public CharArray getLeftOverCB() {
    if ((currentLen == 0) || (theContent == null)) {
      return null;
    }

    return new CharArray(theContent, currentPos, currentLen);
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @param out
   *          the output array
   */
  public void getLeftOverCB(CharArray out) {
    if ((currentLen == 0) || (theContent == null)) {
      return;
    }

    out.set(theContent, currentPos, currentLen);
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @return a set of characters representing the left over contents
   */
  public char[] getLeftOverChars() {
    if ((currentLen == 0) || (theContent == null)) {
      return null;
    }

    char[] c = new char[currentLen];

    System.arraycopy(theContent, currentPos, c, 0, currentLen);

    return c;
  }

  /**
   * Gets the left over contents (contents not yet scanned) of the scanner
   *
   * @return a token representing the left over contents
   */
  public int[] getLeftOverToken() {
    if ((currentLen == 0) || (theContent == null)) {
      return null;
    }

    int[] tok = new int[2];

    tok[0] = currentPos;
    tok[1] = currentLen;

    return tok;
  }

  /**
   * Gets the length of the current scan-able contents
   *
   * @return a integer representing the length
   */
  public int getLength() {
    return currentLen;
  }

  /**
   * Gets the number of characters of the longest line in the specified string
   *
   * @param str
   *          the string to check
   * @return the number of characters of the longest line in the specified
   *         string;
   */
  public static int getLongestLineCharCount(String str) {
    if (str == null) {
      return 0;
    }

    int len = str.length();
    int n   = 0;
    int p   = 0;
    int cnt = 0;

    n = str.indexOf('\n');

    while(n != -1) {
      if (n - p > cnt) {
        cnt = n - p;
      }

      p = n + 1;
      n = str.indexOf('\n', p);
    }

    if ((p > 0) && (len - p) > cnt) {
      cnt = len - p;
    }

    return (cnt == 0)
           ? len
           : cnt;
  }

  /**
   * Parses the contents of the scanner as an options string of name/value pairs
   * separated by the specified token
   *
   * @param c
   *          the token separating the pairs
   * @param unquote
   *          true to unquote; false otherwise
   *
   *
   * @return {@inheritDoc}
   */
  public Map<String, Object> getOptions(char c, boolean unquote) {
    return parseOptionStringEx(this, null, c, unquote);
  }

  /**
   * Parses the contents of the scanner as an options string of name/value pairs
   * separated by the specified token
   *
   * @param out
   *          the map to hold the parameters
   * @param c
   *          the token separating the pairs
   * @param unquote
   *          true to unquote; false otherwise
   *
   *
   * @return {@inheritDoc}
   */
  public Map<String, Object> getOptions(Map<String, Object> out, char c, boolean unquote) {
    return parseOptionStringEx(this, out, c, unquote);
  }

  /**
   * Gets a single token delimited piece of a string
   *
   * @param s
   *          the string
   * @param tok
   *          the token used to delimit the pieces
   * @param pos
   *          the piece to retrieve (pieces are 1-relative)
   *
   * @return the piece
   */
  public static final String getPiece(String s, char tok, int pos) {
    return getPiece(s, tok, pos, pos);
  }

  /**
   * Gets a single token delimited piece of a string
   *
   * @param s
   *          the string
   * @param tok
   *          the token used to delimit the pieces
   * @param pos
   *          the piece to retrieve (pieces are 1-relative)
   *
   * @return the piece
   */
  public static final String getPiece(String s, String tok, int pos) {
    return getPiece(s, tok, pos, pos);
  }

  /**
   * Gets a single token delimited piece of a string
   *
   * @param s
   *          the string
   * @param tok
   *          the token used to delimit the pieces
   * @param start
   *          the starting piece (inclusive)
   * @param end
   *          the ending piece (inclusive)
   * @return the pieces
   */
  public static final String getPiece(String s, char tok, int start, int end) {
    int i  = 0;
    int n  = 1;
    int oi = 0;
    int pos;
    int tl = 1;

    if ((end < 1) || (end < start) || (tl == 0)) {
      return null;
    }

    while((n < start) && ((i = s.indexOf(tok, i)) != -1)) {
      i += tl;
      n++;
    }

    if ((n < start) || (i == -1)) {
      return null;
    }

    oi = i;
    i  = s.indexOf(tok, i);

    if (i == -1) {
      return s.substring(oi);
    }

    if (start == end) {
      return s.substring(oi, i);
    }

    pos = oi;
    i   += tl;

    while((n < end) && ((i = s.indexOf(tok, i)) != -1)) {
      n++;
      i += tl;
    }

    if (i == -1) {
      return s.substring(pos);
    }

    return s.substring(pos, i - tl);
  }

  /**
   * Gets a single token delimited piece of a string
   *
   * @param s
   *          the string
   * @param tok
   *          the token used to delimit the pieces
   * @param start
   *          the starting piece (inclusive)
   * @param end
   *          the ending piece (inclusive)
   * @return the pieces
   */
  public static final String getPiece(String s, String tok, int start, int end) {
    int i  = 0;
    int n  = 1;
    int oi = 0;
    int pos;
    int tl = tok.length();

    if ((end < 1) || (end < start) || (tl == 0)) {
      return null;
    }

    while((n < start) && ((i = s.indexOf(tok, i)) != -1)) {
      i += tl;
      n++;
    }

    if ((n < start) || (i == -1)) {
      return null;
    }

    oi = i;
    i  = s.indexOf(tok, i);

    if (i == -1) {
      return s.substring(oi);
    }

    if (start == end) {
      return s.substring(oi, i);
    }

    pos = oi;
    i   += tl;

    while((n < end) && ((i = s.indexOf(tok, i)) != -1)) {
      n++;
      i += tl;
    }

    if (i == -1) {
      return s.substring(pos);
    }

    return s.substring(pos, i - tl);
  }

  /**
   * Gets the current position of the scanner
   *
   * @return a integer representing the scan position
   */
  public int getPosition() {
    return currentPos;
  }

  /**
   * Gets the current position of the scanner relative to the starting position
   * of the specified content;
   *
   * @return a integer representing the scan position
   */
  public int getRelPosition() {
    int pos = currentPos - originalPos;

    return (pos < 0)
           ? 0
           : pos;
  }

  /**
   * Gets the string for the specified relative position within the source
   * array;
   *
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   *
   * @return a string representing the value
   */
  public String getRelString(int pos, int len) {
    if (len == 0) {
      return emptyString;
    }

    return new String(theContent, originalPos + pos, len);
  }

  /**
   * Gets the string for the specified absolute position within the source
   * array;
   *
   * @param pos
   *          the starting position within the array
   * @param len
   *          the number of characters in the array to use
   *
   * @return a string representing the value
   */
  public String getString(int pos, int len) {
    if (len == 0) {
      return emptyString;
    }

    return new String(theContent, pos, len);
  }

  /**
   * Gets the value pointed to by a token
   *
   * @param tok
   *          the token (an array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @return a string representing the value
   */
  public String getToken(int[] tok) {
    if ((tok == null) || (theContent == null)) {
      return null;
    }

    if (tok[1] == 0) {
      return emptyString;
    }

    return new String(theContent, tok[0], tok[1]);
  }

  /**
   * Gets the value pointed to by a token
   *
   * @param tok
   *          the token (an array of integers where the first two elements point
   *          to the position and length respectively)
   * @param ca
   *          the CharArray that will hold the token;
   * @param trim
   *          <code>true</code> specifies white space should be trimed from both
   *          ends of the returned string
   *
   * @return <code>true</code> a a token was returned
   */
  public boolean getToken(int[] tok, CharArray ca, boolean trim) {
    if ((tok == null) || (theContent == null)) {
      return false;
    }

    if (trim) {
      trim(tok);
    }

    ca.set(theContent, tok[0], tok[1]);

    return true;
  }

  /**
   * Gets the value pointed to by a token
   *
   * @param tok
   *          the token (an array of integers where the first two elements point
   *          to the position and length respectively)
   *
   * @return a set of characters representing the value
   */
  public char[] getTokenChars(int[] tok) {
    if ((tok == null) || (theContent == null)) {
      return null;
    }

    char[] c = new char[tok[1]];

    System.arraycopy(theContent, tok[0], c, 0, tok[1]);

    return c;
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param c
   *          the character delimiter
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public List<String> getTokens(char c) {
    return getTokens(c, false, null);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param c
   *          the character delimiter
   * @param trim
   *          true to trim white space; false otherwise
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public List<String> getTokens(char c, boolean trim) {
    return getTokens(c, trim, null);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param c
   *          the character delimiter
   * @param trim
   *          true to trim white space; false otherwise
   * @param list
   *          list to use to store results
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public List<String> getTokens(char c, boolean trim, List<String> list) {
    int[]   tok;
    boolean found = false;

    if (list == null) {
      list = new ArrayList<String>();
    }

    while((tok = findToken(c)) != null) {
      if (trimChars != null) {
        tok = trim(tok);

        if ((tok == null) || (tok[1] == 0)) {
          list.add(emptyString);
          found = (foundDelimiter != 0);

          continue;
        }
      }

      list.add(getToken(tok));
      found = (foundDelimiter != 0);
    }

    if (currentLen > 0) {
      list.add(getLeftOver());
    } else if (found) {
      list.add(emptyString);
    }

    return list;
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param str
   *          the string in which to search for tokens
   * @param c
   *          the character delimiter
   * @param trim
   *          true to trim white space; false otherwise
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public static List<String> getTokens(String str, char c, boolean trim) {
    return getTokens(str, c, trim, null);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          true to trim white space; false otherwise
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public List<String> getTokens(char c, boolean checkQuote, boolean checkParam, boolean trim) {
    return getTokens(c, checkQuote, checkParam, trim, null);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param str
   *          the string in which to search for tokens
   * @param c
   *          the character delimiter
   * @param trim
   *          true to trim white space; false otherwise
   * @param list
   *          list to use to store results
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  @SuppressWarnings("resource")
  public static List<String> getTokens(String str, char c, boolean trim, List<String> list) {
    CharScanner sc = new CharScanner(str);

    sc.setTrimChars(lfChars);

    return sc.getTokens(c, trim, list);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          true to trim white space; false otherwise
   * @param list
   *          list to use to store results
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public List<String> getTokens(char c, boolean checkQuote, boolean checkParam, boolean trim, List<String> list) {
    int[]        tok;
    boolean      found = false;
    List<String> olist = list;

    if (list == null) {
      list = new ArrayList<String>();
    }

    while((tok = findToken(c, checkQuote, checkParam)) != null) {
      if ((trimChars != null) || trim) {
        tok = trim(tok);

        if ((tok == null) || (tok[1] == 0)) {
          list.add(emptyString);
          found = (foundDelimiter != 0);

          continue;
        }
      }

      list.add(getToken(tok));
      found = (foundDelimiter != 0);
    }

    if (currentLen > 0) {
      list.add(getLeftOver());
    } else if (found) {
      list.add(emptyString);
    }

    return (list.size() == 0)
           ? olist
           : list;
  }

  /**
   * Returns the number of tokens available in the scanner
   *
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   *
   * @return the number of tokens that were found
   */
  public int getTokenCount(char c, boolean checkQuote, boolean checkParam) {
    int count = 0;
    int pos   = currentPos;
    int len   = currentLen;

    while(findToken(c, checkQuote, checkParam) != null) {
      count++;
    }

    currentPos = pos;
    currentLen = len;

    return count;
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param str
   *          the string in which to search for tokens
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          true to trim white space; false otherwise
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  public static List<String> getTokens(String str, char c, boolean checkQuote, boolean checkParam, boolean trim) {
    return getTokens(str, c, checkQuote, checkParam, trim, null);
  }

  /**
   * Finds and retrieves all tokens delimited by the specified character
   *
   * @param str
   *          the string in which to search for tokens
   * @param c
   *          the character delimiter
   * @param checkQuote
   *          <code>true</code> specifies that delimiters within double quotes
   *          are to be ignored
   * @param checkParam
   *          <code>true</code> specifies that delimiters within parameter
   *          blocks are to be ignored
   * @param trim
   *          true to trim white space; false otherwise
   * @param list
   *          list to use to store results
   *
   * @return an array of strings representing the found tokens or
   *         <code>null</code> if none were found
   */
  @SuppressWarnings("resource")
  public static List<String> getTokens(String str, char c, boolean checkQuote, boolean checkParam, boolean trim,
          List<String> list) {
    CharScanner sc = new CharScanner(str);

    return sc.getTokens(c, checkQuote, checkParam, trim, list);
  }

  /**
   * {@inheritDoc}
   *
   * @param c
   *          {@inheritDoc}
   * @param a
   *          {@inheritDoc}
   * @param len
   *          {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  final static boolean isTokenChar(int i, char c, char[] a, int len) {
    while(i < len) {
      if (a[i++] == c) {
        return true;
      }
    }

    return false;
  }

  private static char htmlStringToCharacter(char[] chars, int pos, int len) {
    int  n = 0;
    char c;
    char rc = 0;

    len += pos;

    if (chars[pos] == '#') {
      pos++;

      while(pos < len) {
        c = chars[pos++];

        if ((c < 48) || (c > 57)) {
          if (c == ';') {
            rc = (char) n;
          }

          break;
        }

        n *= 10;
        n += (int) (c - 48);
      }
    }

    return rc;
  }

  /**
   * Parses and options string of name/value pairs separated by the specified
   * token
   *
   * @param sc
   *          the scanner containing the value to parse to parse
   * @param out
   *          the map to hold the parameters
   * @param c
   *          the token separating the pairs
   * @param unquote
   *          true to unquote; false otherwise
   *
   *
   * @return {@inheritDoc}
   */
  public static Map parseOptionStringEx(CharScanner sc, Map out, char c, boolean unquote) {
    if (out == null) {
      out = new LinkedHashMap<String, Object>();
    }

    char[] chars = sc.getContent();
    int[]  tok;
    int    n;
    int    end;
    String s, val;

    while((tok = sc.findToken(c, true, true)) != null) {
      sc.trim(tok);
      n = CharArray.indexOf(chars, tok[0], tok[1], '=', 0);

      if (n == -1) {
        out.put(sc.getToken(tok), null);
      } else {
        end    = tok[0] + tok[1];
        tok[1] = n - tok[0];
        sc.trim(tok);
        s      = sc.getToken(tok);
        tok[0] = n + 1;
        tok[1] = end - tok[0];
        sc.trim(tok);

        if (unquote) {
          tok[1] = cleanQuotedEx(chars, tok[0], tok[1]);
        }

        val = sc.getToken(tok);
        out.put(s, val);
      }
    }

    return out;
  }

  private static void strip(int tok[], char[] chars, int pos, int len, char[] bad, boolean left, boolean right) {
    if ((len < 1) || (chars == null)) {
      return;
    }

    int     i = pos;
    int     n;
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

    tok[0] = i;
    i      = max - 1;

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

    tok[1] = (i + 1) - tok[0];
  }

  private static void trim(int tok[], char[] chars, int pos, int len, boolean left, boolean right) {
    int n = pos + len;

    tok[0] = n - 1;

    if (left) {
      while((pos < n) && (Character.isWhitespace(chars[pos]))) {
        pos++;
        len--;
      }
    }

    if (right) {
      while((n > pos) && (Character.isWhitespace(chars[--n]))) {
        len--;
      }
    }

    tok[1] = len;

    if (len != 0) {
      tok[0] = pos;
    }
  }
}
