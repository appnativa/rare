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

import java.awt.event.KeyEvent;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * Telnet terminal handler
 *
 * @author Don DeCoteau
 */
public final class Terminal {
  static final char ACTION_REQUEST   = KeyEvent.KEY_LAST;
  static final int  CHAR_READ_MODE   = 1;
  static final int  CR               = 13;
  static final int  DEL              = 127;
  static final int  LF               = 10;
  static final int  INS              = KeyEvent.VK_INSERT;
  static final int  LINE_READ_MODE   = 2;
  static final int  STATE_KEYCODE    = 1;
  static final int  STATE_NORMAL     = 0;
  int               historyDownInc   = 0;
  List              historyList      = null;
  int               historyListPos   = 0;
  int               keyCodeModifiers = 0;
  char              lastKey          = 0;
  LineEditor        lineReader       = null;
  int               maxHistoryLen    = 50;
  int               readWaitTime     = 100;
  char              termStatus       = 0;
  iDisplay          theDisplay       = null;
  char[]            twoChar          = new char[2];
  boolean           localEcho        = true;
  boolean           historyEnabled   = true;
  boolean           typeAheadEnabled = true;
  int               theState         = STATE_NORMAL;
  StringBuilder     theBuffer        = new StringBuilder();
  boolean           isPaused         = false;
  boolean           inReadWait       = false;
  Thread            ownerThread;

  /**
   * Creates a new <code>Terminal</code> object.
   *
   *
   * @param display  the video display
   * @param owner {@inheritDoc}
   *
   * @throws IOException {@inheritDoc}
   */
  public Terminal(iDisplay display, Thread owner) throws IOException {
    super();
    ownerThread = owner;
    theDisplay  = display;
    lineReader  = new LineEditor(display);
    lineReader.setInsertMode(true);
    historyList = new ArrayList(maxHistoryLen);
    theDisplay.setInsertMode(false);
  }

  /**
   * {@inheritDoc}
   */
  public void backspace() throws IOException {
    theDisplay.backspace();
  }

  /**
   * {@inheritDoc}
   */
  public void bell() throws IOException {
    theDisplay.beep();
  }

  /**
   * {@inheritDoc}
   */
  public void clearDisplay() throws IOException {
    theDisplay.clearDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void formfeed() throws IOException {
    theDisplay.clearDisplay();
  }

  /**
   * Get the display object associated wit the device
   *
   * @return   the display object
   */
  public iDisplay getDisplay() {
    return theDisplay;
  }

  /**
   * getLineFeedString
   *
   * @return String
   */
  public String getLineFeedString() {
    return "\r\n";
  }

  /**
   * {@inheritDoc}
   */
  public int getReadPos() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public Reader getReader() throws IOException {
    return new TermReader();
  }

  /**
   * {@inheritDoc}
   */
  public Writer getWriter() throws IOException {
    return new TermWriter();
  }

  /**
   * {@inheritDoc}
   */
  public void interruptReader() {
    if (ownerThread != null) {
      ownerThread.interrupt();
    }
  }

  /**
   * Tests a character to see if it is a normal read terminator
   *
   * @param c  the character
   *
   * @return   <code>true</code> if it is; <code>false</code> otherwise
   */
  public boolean isTerminator(int c) {
    if (c < 32) {
      return true;
    }

    if ((c > 126) && (c < 161)) {
      return true;
    }

    if (c > 223) {
      return true;
    }

    return false;
  }

  /**
   * Accepts keyboard input with the character array consisting of modifier/character
   * pairs
   *
   * @param chars  the character array to receive as input
   */
  public void keyboardWrite(char[] chars) {
    keyboardWrite(chars, 0, chars.length);
  }

  /**
   * Accepts keyboard input
   *
   * @param modifier  the character to receive as input
   * @param c         the character to receive as input
   */
  public void keyboardWrite(int modifier, char c) {
    if ((modifier == 0) && (c == 3)) {
      interruptReader();
    } else {
      twoChar[0] = (char) modifier;
      twoChar[1] = c;
      keyboardWrite(twoChar, 0, 2);
    }
  }

  /**
   * Accepts keyboard input with the character array consisting of modifier/character
   * pairs
   *
   * @param chars  the character array to receive as input
   * @param pos    the starting position within the array
   * @param len    the number of characters in the array to use
   */
  public void keyboardWrite(char[] chars, int pos, int len) {
    boolean ws = isPaused;

    synchronized(theBuffer) {
      for (int i = pos; i < len; i++) {
        if (chars[i] == iConstants.KEY_CTRL_S) {
          isPaused = true;
        } else if (chars[i] == iConstants.KEY_CTRL_Q) {
          isPaused = false;
        } else if (chars[i] == 10) {
          theBuffer.append((char) 13);
        } else {
          theBuffer.append((char) chars[i]);
        }
      }

      if (!isPaused) {
        theBuffer.notify();
      } else {}
    }

    if (!isPaused && (ws != isPaused)) {
      synchronized(theDisplay) {
        theDisplay.notify();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void linefeed() throws IOException {
    linefeed(1);
  }

  /**
   * {@inheritDoc}
   */
  public void linefeed(int num) throws IOException {
    while(num-- > 0) {
      theDisplay.linefeed();
    }

    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public int read() throws IOException {
    return read(-1);
  }

  /**
   * {@inheritDoc}
   */
  public int read(int timeout) throws IOException {
    long gone = 0;
    long time = System.currentTimeMillis();
    int  mod  = 0;
    int  c    = -1;

    lastKey = 0;
    theDisplay.syncDisplay();

    synchronized(theBuffer) {
      if (!typeAheadEnabled) {
        flushInput();
      }

      while(theBuffer.length() == 0) {
        if (timeout != -1) {
          gone = System.currentTimeMillis() - time;

          if ((timeout - gone) < 1) {
            break;
          }

          try {
            inReadWait = true;
            theBuffer.wait(timeout - gone);
          } catch(InterruptedException ie) {
            interruptReader();

            return -1;
          } finally {
            inReadWait = false;
          }
        } else {
          try {
            inReadWait = true;
            theBuffer.wait();
          } catch(InterruptedException ie) {
            interruptReader();

            return -1;
          } finally {
            inReadWait = false;
          }
        }
      }

      if ((timeout != -1) && ((timeout - gone) < 1)) {
        termStatus = iConstants.STATUS_TIMEDOUT;
      }

      if (theBuffer.length() > 1) {
        mod = theBuffer.charAt(0);
        c   = theBuffer.charAt(1);
        theBuffer.delete(0, 2);
        lastKey = (char) c;
      }
    }

    return c;
  }

  /**
   * {@inheritDoc}
   */
  public String readChars(int len) throws IOException {
    return readChars(len, -1);
  }

  /**
   * {@inheritDoc}
   */
  public String readChars(int len, int timeout) throws IOException {
    if (len < 1) {
      return null;
    }

    return readLine(timeout, len);
  }

  /**
   * {@inheritDoc}
   */
  public String readLine() throws IOException {
    return readLine(-1, -1);
  }

  /**
   * {@inheritDoc}
   */
  public String readLine(int timeout) throws IOException {
    return readLine(timeout, -1);
  }

  /**
   * {@inheritDoc}
   */
  public void setBold(boolean b) throws IOException {
    theDisplay.setBold(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorColumn(int c) throws IOException {
    theDisplay.setCursorColumn(c);
    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorPosition(int x, int y) throws IOException {
    theDisplay.setCursorPosition(x, y);
    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void setCursorRow(int r) throws IOException {
    theDisplay.setCursorRow(r);
    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void setItalic(boolean b) throws IOException {
    theDisplay.setItalic(b);
  }

  /**
   * {@inheritDoc}
   */
  public void setReadPollingTime(int time) {
    if (time < 10) {
      time = 10;
    }

    readWaitTime = time;
  }

  /**
   * {@inheritDoc}
   */
  public void setUnderlined(boolean b) throws IOException {
    theDisplay.setUnderlined(b);
  }

  /**
   * {@inheritDoc}
   */
  public void syncDisplay() throws IOException {
    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void togglePause() {
    isPaused = !isPaused;
  }

  /**
   * {@inheritDoc}
   */
  public void unreadChar(char c) throws IOException {
    synchronized(theBuffer) {
      twoChar[0] = 0;
      twoChar[c] = c;
      theBuffer.insert(0, twoChar);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void write(int c) throws IOException {
    if (isPaused) {
      synchronized(theDisplay) {
        try {
          theDisplay.wait();
        } catch(InterruptedException ex) {
          interruptReader();

          return;
        }
      }
    }

    theDisplay.write((char) c);
  }

  /**
   * {@inheritDoc}
   */
  public void write(String s) throws IOException {
    write(s, true);
  }

  /**
   * Writes a portion of an array of characters.
   *
   * @param sync   <code>true</code> to synchronize the display; <code>false</code> otherwise
   *
   * @throws  IOException if an execution error occurs
   */
  public void write(String s, boolean sync) throws IOException {
    if (isPaused) {
      synchronized(theDisplay) {
        try {
          theDisplay.wait();
        } catch(InterruptedException ex) {
          interruptReader();

          return;
        }
      }
    }

    if (s != null) {
      theDisplay.write(s);
    }

    if (sync) {
      theDisplay.syncDisplay();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void writeAt(String s, int x, int y) throws IOException {
    if (isPaused) {
      synchronized(theDisplay) {
        try {
          theDisplay.wait();
        } catch(InterruptedException ex) {
          interruptReader();

          return;
        }
      }
    }

    theDisplay.setCursorPosition(x, y);
    theDisplay.write(s);
    theDisplay.syncDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void writeLine(String s) throws IOException {
    if (isPaused) {
      synchronized(theDisplay) {
        try {
          theDisplay.wait();
        } catch(InterruptedException ex) {
          interruptReader();

          return;
        }
      }
    }

    write(s, false);
    theDisplay.linefeed();
    theDisplay.syncDisplay();
  }

  /**
   * Adds a string to the command line history buffer
   *
   * @param s  the string
   */
  void addToHistory(String s) {
    int len = historyList.size();

    if (len >= maxHistoryLen) {
      historyList.remove(0);
      len--;
    }

    if (len > 0) {
      if (!s.equals((String) historyList.get(len - 1))) {
        historyList.add(s);
      }
    } else {
      historyList.add(s);
    }

    historyListPos = historyList.size();
    historyDownInc = 0;
  }

  /**
   * Appends a character to the line editor's buffer without doing any translation
   *
   * @param c  the character
   *
   * @return   zero or a termination character
   */
  protected char appendChar(char c) throws IOException {
    if (isTerminator(c)) {
      return c;
    }

    lineReader.addChar(c, localEcho);

    return 0;
  }

  /**
   * Flushes the input buffer, removing all pending characters
   */
  protected void flushInput() {
    theBuffer.setLength(0);
  }

  /**
   * Reads a line of text. A line is considered to be terminated by any one of a line
   * feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately
   * by a linefeed. The read will also terminate if the end of file is reached or the
   * read timed out.
   *
   * @param timeout   the timeout
   * @param maxChars  the maximum number of characters to read
   *
   * @return   a string containing the contents of the line, or null if the end of the
   *           stream has been reached
   *
   * @throws   IOException if an execution error occurs
   */
  protected String readLine(int timeout, int maxChars) throws IOException {
    long    gone = 0;
    long    time = System.currentTimeMillis();
    int     c    = -1;
    int     len;
    int     i;
    boolean done = false;
    String  line = "";

    termStatus = iConstants.STATUS_OK;
    lastKey    = 0;
    theDisplay.syncDisplay();

    synchronized(theBuffer) {
      if (!typeAheadEnabled) {
        flushInput();
      }

      while(true) {
        while(theBuffer.length() == 0) {
          if (timeout != -1) {
            gone = System.currentTimeMillis() - time;

            if ((timeout - gone) < 1) {
              termStatus = iConstants.STATUS_TIMEDOUT;
              done       = true;
              line       = lineReader.getLine(true);

              break;
            }

            try {
              inReadWait = true;
              theBuffer.wait(timeout - gone);
            } catch(InterruptedException ie) {
              interruptReader();

              return "";
            } finally {
              inReadWait = false;
            }
          } else {
            try {
              inReadWait = true;
              theBuffer.wait();
            } catch(InterruptedException ie) {
              interruptReader();

              return "";
            } finally {
              inReadWait = false;
            }
          }

          if ((timeout != -1) && ((timeout - gone) < 1)) {
            termStatus = iConstants.STATUS_TIMEDOUT;
            done       = true;
            line       = lineReader.getLine(true);

            break;
          }
        }

        len = theBuffer.length();

        for (i = 0; i < len; i++) {
          c         = theBuffer.charAt(0);
          theBuffer = theBuffer.deleteCharAt(0);
          c         = handleCharacter((char) c);

          if (c == (char) -1) {    // can only be generated programmatically; used to force termination so an action ca be taken
            line       = lineReader.getLine(true);
            done       = true;
            termStatus = iConstants.STATUS_EOF;

            break;
          }

          if ((c != 0) || ((maxChars != -1) && (lineReader.available() >= maxChars))) {    // char not consumed it is a terminator
            lastKey = (char) c;
            line    = lineReader.getLine(true);
            done    = true;

            if (line.length() > 0) {
              addToHistory(line);
            }

            break;
          }
        }

        if (done) {
          break;
        }
      }
    }

    return line;
  }

  /**
   * Translates a key code character
   *
   * @param modifiers  the keycode modifiers
   * @param c          the character to translate
   *
   * @return   a set of characters representing the translated character or <code>null</code>
   */
  protected char translateKeyCode(int modifiers, char c) {
    char rc = 0;

    switch(c) {
      case KeyEvent.VK_F1 :
      case KeyEvent.VK_F2 :
      case KeyEvent.VK_F3 :
      case KeyEvent.VK_F4 :
      case KeyEvent.VK_F5 :
      case KeyEvent.VK_F6 :
      case KeyEvent.VK_F7 :
      case KeyEvent.VK_F8 :
      case KeyEvent.VK_F9 :
      case KeyEvent.VK_F10 :
      case KeyEvent.VK_F11 :
      case KeyEvent.VK_F12 :
        break;

      case KeyEvent.VK_UP :
        rc = iConstants.KEY_CTRL_P;

        break;

      case KeyEvent.VK_DOWN :
        rc = iConstants.KEY_CTRL_N;

        break;

      case KeyEvent.VK_LEFT :
        if ((modifiers & KeyEvent.CTRL_MASK) > 0) {
          rc = iConstants.KEY_CTRL_B;
        } else {
          rc = iConstants.KEY_CTRL_R;
        }

        break;

      case KeyEvent.VK_RIGHT :
        if ((modifiers & KeyEvent.CTRL_MASK) > 0) {
          rc = iConstants.KEY_CTRL_F;
        } else {
          rc = iConstants.KEY_CTRL_K;
        }

        break;

      case KeyEvent.VK_PAGE_DOWN :
        // rc=iConstants.KEY_CTRL_A;
        break;

      case KeyEvent.VK_PAGE_UP :
        // rc=iConstants.KEY_CTRL_A;
        break;

      case KeyEvent.VK_INSERT :
        lineReader.toggleInsertMode();

        break;

      case KeyEvent.VK_DELETE :
        rc = iConstants.KEY_CTRL_D;

        break;

      case KeyEvent.VK_BACK_SPACE :
        rc = iConstants.KEY_CTRL_H;

        break;

      case KeyEvent.VK_HOME :
        rc = iConstants.KEY_CTRL_A;

        break;

      case KeyEvent.VK_END :
        rc = iConstants.KEY_CTRL_E;

        break;

      case KeyEvent.VK_NUMPAD0 :
        rc = '0';

        break;

      case KeyEvent.VK_NUMPAD1 :
        rc = '1';

        break;

      case KeyEvent.VK_NUMPAD2 :
        rc = '2';

        break;

      case KeyEvent.VK_NUMPAD3 :
        rc = '3';

        break;

      case KeyEvent.VK_NUMPAD4 :
        rc = '4';

        break;

      case KeyEvent.VK_NUMPAD5 :
        rc = '5';

        break;

      case KeyEvent.VK_NUMPAD6 :
        rc = '6';

        break;

      case KeyEvent.VK_NUMPAD7 :
        rc = '7';

        break;

      case KeyEvent.VK_NUMPAD8 :
        rc = '8';

        break;

      case KeyEvent.VK_NUMPAD9 :
        rc = '9';

        break;

      case KeyEvent.VK_SUBTRACT :
        rc = '-';

        break;

      case KeyEvent.VK_ADD :
        rc = '+';

        break;

      case KeyEvent.VK_DECIMAL :
        rc = '.';

        break;

      default :
        break;
    }

    return rc;
  }

  private char handleCharacter(char c) {
    char rc = 0;

    try {
      switch(theState) {
        case STATE_NORMAL :
          if (c == ACTION_REQUEST) {
            return (char) -1;
          }

          if ((c & iConstants.KEYCODE_MASK) > 0) {
            theState         = STATE_KEYCODE;
            keyCodeModifiers = c;

            return 0;
          }

          break;

        case STATE_KEYCODE :
          theState = STATE_NORMAL;
          c        = translateKeyCode(keyCodeModifiers % iConstants.KEYCODE_MASK, c);

          break;

        default :
          break;
      }

      if (c == 0) {
        return 0;
      }

      // new block to replace old block
      lineReader.resetChangeFlag();

      if ((c < 32) || (c > 126)) {
        rc = handleSpecialChar(c);
      } else {
        rc = appendChar(c);
      }

      theDisplay.syncDisplay();
    } catch(Exception e) {}

    return rc;
  }

  private char handleSpecialChar(char c) throws IOException {
    String s;

    if (c == LF) {
      return (char) CR;
    }

    switch(c) {
      case iConstants.KEY_CTRL_H :
        // case BSP:
        lineReader.addChar(LineEditor.ACT_DEL_PREVIOUS_CHAR, localEcho);

        break;

      case iConstants.KEY_CTRL_D :
      case DEL :
        lineReader.addChar(LineEditor.ACT_DEL_CURRENT_CHAR, localEcho);

        break;

      case iConstants.KEY_CTRL_B :
        lineReader.addChar(LineEditor.ACT_PREVIOUS_WORD, localEcho);

        break;

      case iConstants.KEY_CTRL_G :
        lineReader.addChar(LineEditor.ACT_DEL_PREVIOUS_WORD, localEcho);

        break;

      case iConstants.KEY_CTRL_F :
        lineReader.addChar(LineEditor.ACT_NEXT_WORD, localEcho);

        break;

      case iConstants.KEY_CTRL_W :
        lineReader.addChar(LineEditor.ACT_DEL_NEXT_WORD, localEcho);

        break;

      case iConstants.KEY_CTRL_L :
        lineReader.addChar(LineEditor.ACT_DEL_TO_END_OF_LINE, localEcho);

        break;

      case iConstants.KEY_CTRL_U :
        lineReader.addChar(LineEditor.ACT_DEL_TO_BEGN_OF_LINE, localEcho);

        break;

      case iConstants.KEY_CTRL_X :
        lineReader.addChar(LineEditor.ACT_DEL_LINE, localEcho);

        break;

      case INS :
        theDisplay.setInsertMode(lineReader.toggleInsertMode());

        break;

      case iConstants.KEY_CTRL_P :
        // case UA:
        if (historyListPos == 0) {
          break;
        }

        historyListPos--;
        historyDownInc = 1;

        if (historyListPos >= historyList.size()) {
          historyListPos = historyList.size() - 1;
        }

        s = null;
        s = (String) historyList.get(historyListPos);

        if ((s != null) && (s.length() > 0)) {
          lineReader.clear(true);
          lineReader.setLine(s, true);
        }

        break;

      case iConstants.KEY_CTRL_N :
        // case DA:
        historyListPos += historyDownInc;
        historyDownInc = 1;

        if (historyListPos >= historyList.size()) {
          historyListPos = historyList.size();
          lineReader.clear(true);

          break;
        }

        s = (String) historyList.get(historyListPos);

        if ((s != null) && (s.length() > 0)) {
          lineReader.clear(true);
          lineReader.setLine(s, true);
        }

        break;

      case iConstants.KEY_CTRL_A :
        // case HOME:
        lineReader.addChar(LineEditor.ACT_BEGN_OF_LINE, localEcho);

        break;

      case iConstants.KEY_CTRL_R :
        // case LA:
        lineReader.addChar(LineEditor.ACT_PREVIOUS_CHAR, localEcho);

        break;

      case iConstants.KEY_CTRL_E :
        // case END:
        lineReader.addChar(LineEditor.ACT_END_OF_LINE, localEcho);

        break;

      case iConstants.KEY_CTRL_K :
        // case RA:
        lineReader.addChar(LineEditor.ACT_NEXT_CHAR, localEcho);

        break;

      default :
        if (isTerminator(c)) {
          return c;
        }

        lineReader.addChar((char) c, localEcho);

        break;
    }

    theDisplay.syncDisplay();

    return 0;
  }

  private void keyboardWrite(char c) {
    synchronized(theBuffer) {
      theBuffer.append(c);
      theBuffer.notify();
    }
  }

  private void unread(char[] chars, int pos, int len) {
    if (len < 1) {
      return;
    }

    char[] ch = new char[len * 2];

    for (int i = 0; i < len; i++) {
      if (chars[pos + i] == 0) {
        len = i;

        break;
      }

      ch[i * 2]       = 0;
      ch[(i * 2) + 1] = chars[pos + i];
    }

    theBuffer.insert(0, ch, 0, (len * 2));
  }

  class TermReader extends Reader {

    /**
     * {@inheritDoc}
     */
    public void close() throws IOException {}

    /**
     * {@inheritDoc}
     */
    public int read(char[] chars, int pos, int len) throws IOException {
      int n = -1;

      try {
        String s = Terminal.this.readChars(len);

        if (s != null) {
          n = s.length();
          s.getChars(0, n, chars, pos);
        }
      } catch(IOException e) {
        throw new IOException(e.getMessage());
      }

      return n;
    }
  }


  class TermWriter extends Writer {

    /**
     * Constructs a new instance
     */
    public TermWriter() {}

    /**
     * {@inheritDoc}
     */
    public void close() throws IOException {}

    /**
     * {@inheritDoc}
     */
    public void flush() throws IOException {
      try {
        Terminal.this.syncDisplay();
      } catch(IOException e) {
        throw new IOException(e.getMessage());
      }
    }

    /**
     * {@inheritDoc}
     */
    public void write(char[] chars, int pos, int len) throws IOException {
      if (isPaused) {
        synchronized(theDisplay) {
          try {
            theDisplay.wait();
          } catch(InterruptedException ex) {
            interruptReader();

            return;
          }
        }
      }

      theDisplay.write(chars, pos, len);
      theDisplay.syncDisplay();
    }
  }
}
