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

package com.appnativa.rare.terminal;

import net.wimpi.telnetd.io.BasicTerminalIO;

import java.awt.event.KeyEvent;

import java.io.IOException;
import java.io.Reader;

/**
 * This class converts terminal character sequences to java key codes
 *
 * @author     Don DeCoteau
 */
public class TerminalReader extends Reader {
  static final int KEY_CODE_MASK = 0xffff;
  static final int MODE_BRACKET  = 2;
  static final int MODE_ESCAPE   = 1;
  static final int MODE_NORMAL   = 0;
  char             m_keyChars[]  = new char[16];
  int              m_keyCount    = 0;
  char             m_keyEsc[]    = { 27, '[' };
  int              m_mode        = 0;
  int              m_nextChar    = 0;
  StringBuilder    m_pushBack    = new StringBuilder();
  BasicTerminalIO  m_IO;

  /**
   * Creates a new <code>TerminalReader</code> object
   *
   *
   * @param io {@inheritDoc}
   */
  public TerminalReader(BasicTerminalIO io) {
    m_IO = io;
  }

  /**
   * {@inheritDoc}
   */
  public void close() {
    try {
      m_IO.close();
    } catch(IOException ex) {}
  }

  /**
   * Gets the java key for the specified terminal character
   * @param c the terminal character
   * @return the java key
   */
  public int getJavaKey(int c) {
    int key = 0;

    if (m_keyCount >= m_keyChars.length) {
      m_keyCount = 0;
      m_mode     = 0;
    }

    switch(m_mode) {
      case MODE_ESCAPE :
        if ((c == '[') && (m_keyCount == 0)) {
          m_mode = MODE_BRACKET;

          break;
        } else if (c == 27) {
          m_keyCount = 0;
          m_mode     = 0;
          key        = 27;
        }

        m_keyChars[m_keyCount++] = (char) c;
        key                      = getEscapeKey();

        break;

      case MODE_BRACKET :
        if (c == '~') {
          m_keyChars[m_keyCount++] = (char) c;
          key                      = getBracketKey();
        } else if (m_keyCount > 5) {
          m_keyCount = 0;
          m_mode     = 0;
        } else {
          m_keyChars[m_keyCount++] = (char) c;
          key                      = getBracketKey();
        }

        break;

      default :
        if (c == 27) {
          m_mode     = MODE_ESCAPE;
          m_keyCount = 0;

          break;
        } else if (c == 155) {
          m_mode = MODE_BRACKET;

          break;
        }

        key = ((c == 127) || (c == 8))
              ? KeyEvent.VK_BACK_SPACE
              : c;

        break;
    }

    return key;
  }

  /**
   * {@inheritDoc}
   */
  public int read() throws IOException {
    int c = m_nextChar;

    if (c != 0) {
      m_nextChar = 0;

      return c;
    }

    do {
      if (m_pushBack.length() > 0) {
        c = m_pushBack.charAt(0);
        m_pushBack.deleteCharAt(0);

        return c;
      }

      c = m_IO.readRaw() & 0xffff;

      if (c == 0xffff) {
        return -1;
      }

      if (c == 10) {
        return c;
      }

      if (c == 27) {
        if (m_IO.available() < 1) {
          return c;
        }
      }

      if (c == -1) {
        return c;
      }

      c = getJavaKey(c);
    } while(c == 0);

    return c;
  }

  /**
   * {@inheritDoc}
   */
  public int read(char[] cbuf, int off, int len) throws IOException {
    int i = 0;
    int c;

    while(i < len) {
      c = read();

      if (c == -1) {
        break;
      }

      cbuf[off++] = (char) c;
      i++;
    }

    return i;
  }

  private int getBracketKey() {
    int key = 0;

    if (m_keyCount == 1) {
      switch(m_keyChars[0]) {
        case 'A' :
          key = KeyEvent.VK_UP + KEY_CODE_MASK;

          break;

        case 'B' :
          key = KeyEvent.VK_DOWN + KEY_CODE_MASK;

          break;

        case 'C' :
          key = KeyEvent.VK_RIGHT + KEY_CODE_MASK;

          break;

        case 'D' :
          key = KeyEvent.VK_LEFT + KEY_CODE_MASK;

          break;
      }

      if (key != 0) {
        m_keyCount = 0;
        m_mode     = 0;
      }

      return key;
    } else if (m_keyCount == 2) {
      if (m_keyChars[m_keyCount - 1] != '~') {
        if (m_keyChars[m_keyCount - 1] > 64) {
          pushback();
        }

        return 0;
      }

      switch(m_keyChars[0]) {
        case '1' :
          key = KeyEvent.VK_HOME + KEY_CODE_MASK;

          break;

        case '2' :
          key = KeyEvent.VK_INSERT + KEY_CODE_MASK;

          break;

        case '3' :
          key = KeyEvent.VK_DELETE + KEY_CODE_MASK;

          break;

        case '4' :
          key = KeyEvent.VK_END + KEY_CODE_MASK;

          break;

        case '5' :
          key = KeyEvent.VK_PAGE_UP + KEY_CODE_MASK;

          break;

        case '6' :
          key = KeyEvent.VK_PAGE_DOWN + KEY_CODE_MASK;

          break;
      }

      if (key != 0) {
        m_keyCount = 0;
        m_mode     = 0;

        return key;
      }
    } else if (m_keyCount == 3) {
      switch(m_keyChars[0]) {
        case '1' :
          switch(m_keyChars[1]) {
            case '1' :
              key = KeyEvent.VK_F1 + KEY_CODE_MASK;

              break;

            case '2' :
              key = KeyEvent.VK_F2 + KEY_CODE_MASK;

              break;

            case '3' :
              key = KeyEvent.VK_F3 + KEY_CODE_MASK;

              break;

            case '4' :
              key = KeyEvent.VK_F4 + KEY_CODE_MASK;

              break;

            case '5' :
              key = KeyEvent.VK_F5 + KEY_CODE_MASK;

              break;

            case '7' :
              key = KeyEvent.VK_F6 + KEY_CODE_MASK;

              break;

            case '8' :
              key = KeyEvent.VK_F7 + KEY_CODE_MASK;

              break;

            case '9' :
              key = KeyEvent.VK_F8 + KEY_CODE_MASK;

              break;
          }

          break;

        case '2' :
          switch(m_keyChars[1]) {
            case '0' :
              key = KeyEvent.VK_F9 + KEY_CODE_MASK;

              break;

            case '1' :
              key = KeyEvent.VK_F10 + KEY_CODE_MASK;

              break;

            case '3' :
              key = KeyEvent.VK_F11 + KEY_CODE_MASK;

              break;

            case '4' :
              key = KeyEvent.VK_F12 + KEY_CODE_MASK;

              break;
          }

          break;
      }

      if (key != 0) {
        m_keyCount = 0;
        m_mode     = 0;

        return key;
      }
    }

    pushback();

    return key;
  }

  private int getEscapeKey() {
    int key = 0;

    if (m_keyCount == 1) {
      switch(m_keyChars[0]) {
        case 'A' :
          key = KeyEvent.VK_UP + KEY_CODE_MASK;

          break;

        case 'B' :
          key = KeyEvent.VK_DOWN + KEY_CODE_MASK;

          break;

        case 'C' :
          key = KeyEvent.VK_RIGHT + KEY_CODE_MASK;

          break;

        case 'D' :
          key = KeyEvent.VK_LEFT + KEY_CODE_MASK;

          break;
      }
    } else if (m_keyCount == 2) {
      if (m_keyChars[0] == 'O') {
        switch(m_keyChars[1]) {
          case 'A' :
            key = KeyEvent.VK_UP + KEY_CODE_MASK;

            break;

          case 'B' :
            key = KeyEvent.VK_DOWN + KEY_CODE_MASK;

            break;

          case 'C' :
            key = KeyEvent.VK_RIGHT + KEY_CODE_MASK;

            break;

          case 'D' :
            key = KeyEvent.VK_LEFT + KEY_CODE_MASK;

            break;

          case 'P' :
            key = KeyEvent.VK_F1 + KEY_CODE_MASK;

            break;

          case 'Q' :
            key = KeyEvent.VK_F2 + KEY_CODE_MASK;

            break;

          case 'R' :
            key = KeyEvent.VK_F3 + KEY_CODE_MASK;

            break;

          case 'S' :
            key = KeyEvent.VK_F4 + KEY_CODE_MASK;

            break;

          case 'T' :
            key = KeyEvent.VK_F5 + KEY_CODE_MASK;

            break;

          case 'U' :
            key = KeyEvent.VK_F6 + KEY_CODE_MASK;

            break;

          case 'V' :
            key = KeyEvent.VK_F7 + KEY_CODE_MASK;

            break;

          case 'W' :
            key = KeyEvent.VK_F8 + KEY_CODE_MASK;

            break;

          case 'X' :
            key = KeyEvent.VK_F9 + KEY_CODE_MASK;

            break;

          case 'Y' :
            key = KeyEvent.VK_F10 + KEY_CODE_MASK;

            break;

          case 'Z' :
            key = KeyEvent.VK_F11 + KEY_CODE_MASK;

            break;

          case '[' :
            key = KeyEvent.VK_F12 + KEY_CODE_MASK;

            break;

          case 'l' :
            key = KeyEvent.VK_ADD + KEY_CODE_MASK;

            break;

          case 'm' :
            key = KeyEvent.VK_SUBTRACT + KEY_CODE_MASK;

            break;

          case 'n' :
            key = KeyEvent.VK_DECIMAL + KEY_CODE_MASK;

            break;

          case 'p' :
          case 'q' :
          case 'r' :
          case 's' :
          case 't' :
          case 'u' :
          case 'v' :
          case 'w' :
          case 'x' :
          case 'y' :
            key = KeyEvent.VK_NUMPAD0 + (m_keyChars[1] - 'p');

            break;
        }
      }
    } else if (m_keyCount == 3) {
      switch(m_keyChars[1]) {
        case '1' :
          switch(m_keyChars[2]) {
            case '7' :
              key = KeyEvent.VK_F6 + KEY_CODE_MASK;

              break;

            case '8' :
              key = KeyEvent.VK_F7 + KEY_CODE_MASK;

              break;

            case '9' :
              key = KeyEvent.VK_F8 + KEY_CODE_MASK;

              break;
          }

          break;

        case '2' :
          switch(m_keyChars[2]) {
            case '0' :
              key = KeyEvent.VK_F9 + KEY_CODE_MASK;

              break;

            case '1' :
              key = KeyEvent.VK_F10 + KEY_CODE_MASK;

              break;

            case '3' :
              key = KeyEvent.VK_F11 + KEY_CODE_MASK;

              break;

            case '4' :
              key = KeyEvent.VK_F12 + KEY_CODE_MASK;

              break;
          }

          break;
      }

      if (key == 0) {
        m_pushBack.append(m_keyEsc, 0, 1);
        m_pushBack.append(m_keyChars, 0, m_keyCount);
        m_keyCount = 0;
        m_mode     = 0;
      }
    }

    if (key != 0) {
      m_keyCount = 0;
      m_mode     = 0;
    }

    return key;
  }

  private void pushback() {
    m_pushBack.append(m_keyEsc, 0, 2);;
    m_pushBack.append(m_keyChars, 0, m_keyCount);;
    m_keyCount = 0;
    m_mode     = 0;
  }
}
