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

import java.io.IOException;

import java.util.Stack;

/**
 * Telnet display handler
 *
 * @author Don DeCoteau
 */
public class Display implements iDisplay {
  private boolean         m_insertMode      = false;
  private char[]          m_transKey        = new char[6];    // most characters we will send back
  private boolean         m_localEcho       = true;
  private boolean         m_applicationMode = false;
  private boolean         m_appCursorKeys   = false;
  private BasicTerminalIO m_IO;
  private int             m_rightMargin;
  private Stack           m_stateStack;

  /**
   * Creates a new instance of Display
   *
   * @param io {@inheritDoc}
   *
   * @throws IOException {@inheritDoc}
   */
  public Display(BasicTerminalIO io) throws IOException {
    m_IO = io;
  }

  /**
   * {@inheritDoc}
   */
  public void backspace() throws IOException {
    m_IO.write((byte) 8);
  }

  /**
   * {@inheritDoc}
   */
  public void beep() throws IOException {
    m_IO.bell();
  }

  /**
   * {@inheritDoc}
   */
  public void clearDisplay() throws IOException {
    m_IO.eraseScreen();
    m_IO.setCursor(0, 0);
  }

  /**
   * {@inheritDoc}
   */
  public void clearSelection() throws IOException {}

  /**
   * {@inheritDoc}
   */
  public void clearTabStop() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('g');
  }

  /**
   * {@inheritDoc}
   */
  public void clearTabStops() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('3');
    m_IO.write('g');
  }

  /**
   * {@inheritDoc}
   */
  public void cursorBackward(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('D');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void cursorDown(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('B');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void cursorForward(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('C');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void cursorUp(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('A');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void deleteChar() throws IOException {
    deleteChars(1);
  }

  /**
   * {@inheritDoc}
   */
  public void deleteChars(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('P');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void deleteLine(int n) throws IOException {
    if (n > 0) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      writeNumber(n);
      m_IO.write('M');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void destructiveBackSpace() throws IOException {
    m_IO.write((byte) 8);
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('P');
  }

  /**
   * {@inheritDoc}
   */
  public void dirty() throws IOException {}

  /**
   * {@inheritDoc}
   */
  public void enableMouseReporting(boolean flag) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('?');
    m_IO.write('1');
    m_IO.write('0');
    m_IO.write('0');
    m_IO.write('0');
    m_IO.write(flag
               ? 'h'
               : 'l');
  }

  /**
   * {@inheritDoc}
   */
  public void endProtectedArea() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('W');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseChars(int len, boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    writeNumber(len);
    m_IO.write('X');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseDisplay(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('2');
    m_IO.write('J');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseInArea(int type) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(type);
    m_IO.write('O');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseInField(int type) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(type);
    m_IO.write('N');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseLine(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('2');
    m_IO.write('K');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseToBegOfDisplay(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('1');
    m_IO.write('J');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseToBegOfLine(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('1');
    m_IO.write('K');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseToEndOfDisplay(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('J');
  }

  /**
   * {@inheritDoc}
   */
  public void eraseToEndOfLine(boolean all) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');

    if (!all) {
      m_IO.write('?');
    }

    m_IO.write('K');
  }

  /**
   * {@inheritDoc}
   */
  public int getBottomMargin() {
    return m_IO.getRows() - 1;
  }

  /**
   * {@inheritDoc}
   */
  public int getColumns() {
    return m_IO.getColumns();
  }

  /**
   * {@inheritDoc}
   */
  public int getLeftMargin() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getRightMargin() {
    return m_rightMargin;
  }

  /**
   * {@inheritDoc}
   */
  public int getRows() {
    return m_IO.getRows();
  }

  /**
   * {@inheritDoc}
   */
  public int getTopMargin() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public void insert(char c) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('@');
    m_IO.write(c);
  }

  /**
   * {@inheritDoc}
   */
  public void insert(String s) throws IOException {
    if (!m_insertMode) {
      m_IO.write((byte) 27);
      m_IO.write('[');
      m_IO.write('4');
      m_IO.write('h');
      m_IO.write(s);
      m_IO.write((byte) 27);
      m_IO.write('[');
      m_IO.write('4');
      m_IO.write('l');
    } else {
      m_IO.write(s);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void insertLine(int n) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('L');
  }

  /**
   * {@inheritDoc}
   */
  public void insertSpaces(int n) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('@');
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAppCursorKeys() {
    return m_appCursorKeys;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isApplicationMode() {
    return m_applicationMode;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isLocalEcho() {
    return m_localEcho;
  }

  /**
   * {@inheritDoc}
   */
  public void linefeed() throws IOException {
    m_IO.write('\r');
    m_IO.write('\n');
  }

  /**
   * {@inheritDoc}
   */
  public void nextLine(int n, boolean bol) throws IOException {
    m_IO.write((byte) 27);

    if (bol) {
      m_IO.write('E');
    } else {
      m_IO.write('D');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void nextTabStop(int n) throws IOException {
    if (n < 1) {
      n = 1;
    }

    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('I');
  }

  /**
   * {@inheritDoc}
   */
  public void previousLine(int n, boolean bol) throws IOException {
    m_IO.write((byte) 27);

    if (bol) {
      m_IO.write('F');
    } else {
      m_IO.write('M');
    }
  }

  /**
   * {@inheritDoc}
   */
  public void previousTabStop(int n) throws IOException {
    if (n < 1) {
      n = 1;
    }

    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('Z');
  }

  /**
   * {@inheritDoc}
   */
  public void redraw() throws IOException {
    syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void resetDisplay() throws IOException {
    m_IO.resetTerminal();
  }

  /**
   * {@inheritDoc}
   */
  public void resetStyleToDefault() throws IOException {
    m_IO.resetAttributes();
  }

  /**
   * {@inheritDoc}
   */
  public void resizeDisplay(int w, int h) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('?');
    m_IO.write('3');
    m_IO.write((w > 80)
               ? 'h'
               : 'l');
  }

  /**
   * {@inheritDoc}
   */
  public Object restoreCursor() throws IOException {
    m_IO.restoreCursor();

    if ((m_stateStack == null) || (m_stateStack.size() == 0)) {
      return null;
    } else {
      return m_stateStack.pop();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void saveCursor(Object obj) throws IOException {
    if (m_stateStack == null) {
      m_stateStack = new Stack();
    }

    m_stateStack.push(obj);
    m_IO.storeCursor();
  }

  /**
   * {@inheritDoc}
   */
  public void scrollDown(int n) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('T');
  }

  /**
   * {@inheritDoc}
   */
  public void scrollUp(int n) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    writeNumber(n);
    m_IO.write('S');
  }

  /**
   * {@inheritDoc}
   */
  public void setAppCursorKeys(boolean appCursorKeys) {
    this.m_appCursorKeys = appCursorKeys;
  }

  /**
   * {@inheritDoc}
   */
  public void setApplicationMode(boolean applicationMode) {
    this.m_applicationMode = applicationMode;
  }

  /**
   * {@inheritDoc}
   */
  public void setAutoWrap(boolean flag) throws IOException {
    m_IO.setLinewrapping(true);
  }

  /**
   * {@inheritDoc}
   */
  public void setBackground(int color) throws IOException {
    m_IO.setBackgroundColor(color);
  }

  /**
   * {@inheritDoc}
   */
  public void setBlink(boolean b) throws IOException {
    m_IO.setBlink(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setBold(boolean b) throws IOException {
    m_IO.setBold(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setConstrainCursor(boolean b) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('?');
    m_IO.write('6');
    m_IO.write(b
               ? 'h'
               : 'l');
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorColumn(int x) throws IOException {
    m_IO.setCursorColumn(x);
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorPosition(int x, int y) throws IOException {
    m_IO.setCursor(y, x);
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorRow(int y) throws IOException {
    m_IO.setCursorRow(y);
  }

  /**
   * {@inheritDoc}
   */
  public void setForeground(int color) throws IOException {
    m_IO.setForegroundColor(color);
  }

  /**
   * {@inheritDoc}
   */
  public void setInsertMode(boolean b) throws IOException {
    m_insertMode = b;
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('4');
    m_IO.write(b
               ? 'h'
               : 'l');
    m_IO.write('[');
  }

  /**
   * {@inheritDoc}
   */
  public void setItalic(boolean b) throws IOException {
    m_IO.setItalic(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setLocalEcho(boolean localEcho) {
    this.m_localEcho = localEcho;
  }

  /**
   * {@inheritDoc}
   */
  public boolean setRightMargin(int right) throws IOException {
    if (right < 0) {
      return false;
    }

    m_rightMargin = (right == 0)
                    ? m_IO.getRows()
                    : right;

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectionEnd() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('G');
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectionStart() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('F');
  }

  /**
   * {@inheritDoc}
   */
  public void setTabStop() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('H');
  }

  /**
   * {@inheritDoc}
   */
  public void setUnderlined(boolean b) throws IOException {
    m_IO.setUnderlined(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setVScrollRegion(int top, int bottom) throws IOException {
    m_IO.defineScrollRegion(top, bottom);
  }

  /**
   * {@inheritDoc}
   */
  public void showCursor(boolean show) throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('[');
    m_IO.write('?');
    m_IO.write('2');
    m_IO.write('5');
    m_IO.write(show
               ? 'h'
               : 'l');
  }

  /**
   * {@inheritDoc}
   */
  public void startProtectedArea() throws IOException {
    m_IO.write((byte) 27);
    m_IO.write('V');
  }

  /**
   * {@inheritDoc}
   */
  public void syncDisplay() throws IOException {
    m_IO.flush();
  }

  /**
   * {@inheritDoc}
   */
  public void toggleInsertMode() throws IOException {
    m_insertMode = !m_insertMode;
    setInsertMode(m_insertMode);
  }

  /**
   * {@inheritDoc}
   */
  public void write(char c) throws IOException {
    m_IO.write(c);
  }

  /**
   * {@inheritDoc}
   */
  public void write(String s) throws IOException {
    m_IO.write(s);
  }

  /**
   * {@inheritDoc}
   */
  public void write(char[] chars, int offset, int len) throws IOException {
    m_IO.write(new String(chars, offset, len));
  }

  private void writeNumber(int num) throws IOException {
    int    n;
    byte[] b   = new byte[4];
    int    pos = 4;

    while(num >= 0) {
      n        = num % 10;
      b[--pos] = (byte) (48 + n);

      if (num < 10) {
        break;
      }

      num -= n;
      num /= 10;
    }

    while(pos < 4) {
      m_IO.write(b[pos++]);
    }

    System.out.println(new String(b, pos, 4 - pos));
  }
}
