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

import java.io.IOException;

/**
 * This class provides the functionality for a line-oriented editor. It is
 * specifically geared towards command line editing. All editing is done via
 * special instructions represented as control characters. To perform a specific
 * editing function simply add the control character for the specific function to
 * the editor's stream via the <code>addChar()</code> method.
 * <p>All the control characters are defined as static integers with the name of
 * the integer offering a description of the function that the character performs.
 *
 * @author Don DeCoteau
 */
public class LineEditor {

  /** instruction to move to the beginning of the line */
  public static final int ACT_BEGN_OF_LINE = 1;

  /** instruction to delete the current character */
  public static final int ACT_DEL_CURRENT_CHAR = 4;

  /** instruction to delete the line */
  public static final int ACT_DEL_LINE = 24;

  /** instruction to delete the next word */
  public static final int ACT_DEL_NEXT_WORD = 23;

  /** instruction to delete the previous character */
  public static final int ACT_DEL_PREVIOUS_CHAR = 8;

  /** instruction to delete the previous word */
  public static final int ACT_DEL_PREVIOUS_WORD = 7;

  /** instruction to delete to the beginning of the line */
  public static final int ACT_DEL_TO_BEGN_OF_LINE = 12;

  /** instruction to delete to the end of the line */
  public static final int ACT_DEL_TO_END_OF_LINE = 21;

  /** instruction to move to the end of the line */
  public static final int ACT_END_OF_LINE = 5;

  /** instruction to move to the next character */
  public static final int ACT_NEXT_CHAR = 11;

  /** instruction to move to the next word */
  public static final int ACT_NEXT_WORD = 6;

  /** instruction to move to the previous character */
  public static final int ACT_PREVIOUS_CHAR = 18;

  /** instruction to move to the previous word */
  public static final int ACT_PREVIOUS_WORD = 2;
  int                     cursorPosition    = 0;
  StringBuilder           theBuffer         = new StringBuilder(255);
  boolean                 insertMode        = false;
  boolean                 changedFlag       = false;
  iDisplay                theDisplay;

  /**
   * Creates a new <code>LineEditor</code> object.
   */
  public LineEditor() {}

  /**
   * Creates a new <code>LineEditor</code> object.
   *
   * @param display the line editor's display
   */
  public LineEditor(iDisplay display) {
    theDisplay = display;
  }

  /**
   * Adds a character to the buffer. This method will return <code>false</code> if
   * the specified is a control character that does not have a predefined action.
   *
   * @param c  the character
   * @param echo true to echo the character to the display; false otherwise
   * @return   <code>true</code> if the character was added <code>false</code> otherwise
   */
  public boolean addChar(int c, boolean echo) throws IOException {
    int     i   = 0;
    boolean ret = false;

    if (!echo) {
      addCharEx((char) c, echo);
    } else {
      switch(c) {
        case ACT_DEL_PREVIOUS_CHAR :
          if (cursorPosition > 0) {
            cursorPosition--;
            theBuffer.deleteCharAt(cursorPosition);
            changedFlag = true;
            ret         = true;

            if (theDisplay != null) {
              theDisplay.destructiveBackSpace();
            }
          }

          break;

        case ACT_DEL_CURRENT_CHAR :
          if (cursorPosition < theBuffer.length()) {
            theBuffer.deleteCharAt(cursorPosition);
            changedFlag = true;
            ret         = true;

            if (theDisplay != null) {
              theDisplay.deleteChar();
            }
          }

          break;

        case ACT_DEL_PREVIOUS_WORD :
          if (cursorPosition > 0) {
            deletePreviousWord();
            ret = true;
          }

          break;

        case ACT_DEL_NEXT_WORD :
          if (cursorPosition < theBuffer.length()) {
            deleteNextWord();
            ret = true;
          }

          break;

        case ACT_DEL_TO_BEGN_OF_LINE :
          if (cursorPosition > 0) {
            if (cursorPosition > (i = theBuffer.length())) {
              cursorPosition = i;
            }

            theBuffer.delete(0, cursorPosition);

            int cp = cursorPosition;

            cursorPosition = 0;
            changedFlag    = true;

            if (theDisplay != null) {
              theDisplay.cursorBackward(cp);
              theDisplay.deleteChars(cp);
            }

            ret = true;
          }

          break;

        case ACT_DEL_TO_END_OF_LINE :
          if (cursorPosition < theBuffer.length()) {
            int n = theBuffer.length() - cursorPosition;

            theBuffer.setLength(cursorPosition);
            changedFlag = true;
            ret         = true;

            if (theDisplay != null) {
              theDisplay.deleteChars(n);
            }
          }

          break;

        case ACT_DEL_LINE :
          if (theBuffer.length() > 0) {
            theBuffer.setLength(0);
            changedFlag = true;
            ret         = true;

            if (theDisplay != null) {
              theDisplay.cursorBackward(cursorPosition);
              theDisplay.deleteChars(cursorPosition);
            }
          }

          cursorPosition = 0;

          break;

        case ACT_BEGN_OF_LINE :
          if (cursorPosition > 0) {
            ret         = true;
            changedFlag = true;

            if (theDisplay != null) {
              theDisplay.cursorBackward(cursorPosition);
            }

            cursorPosition = 0;
          }

          break;

        case ACT_PREVIOUS_CHAR :
          if (cursorPosition > 0) {
            cursorPosition--;
            ret         = true;
            changedFlag = true;

            if (theDisplay != null) {
              theDisplay.backspace();
            }
          }

          break;

        case ACT_PREVIOUS_WORD :
          if (cursorPosition > 0) {
            int cp = cursorPosition;

            moveToPreviousWord();
            ret         = true;
            changedFlag = true;

            if (theDisplay != null) {
              cp = cp - cursorPosition;

              if (cp > 0) {
                theDisplay.cursorBackward(cp);
              }
            }
          }

          break;

        case ACT_END_OF_LINE :
          if (cursorPosition < theBuffer.length()) {
            int cp = cursorPosition;

            cursorPosition = theBuffer.length();
            ret            = true;
            changedFlag    = true;

            if (theDisplay != null) {
              theDisplay.cursorForward(cursorPosition - cp);
              changedFlag = true;
            }
          }

          break;

        case ACT_NEXT_CHAR :
          if (cursorPosition < theBuffer.length()) {
            cursorPosition++;
            ret         = true;
            changedFlag = true;

            if (theDisplay != null) {
              theDisplay.write(theBuffer.charAt(cursorPosition - 1));
            }
          }

          break;

        case ACT_NEXT_WORD :
          if (cursorPosition < theBuffer.length()) {
            int cp = cursorPosition;

            moveToNextWord();
            ret         = true;
            changedFlag = true;

            if (theDisplay != null) {
              cp = cursorPosition - cp;

              if (cp > 0) {
                theDisplay.cursorForward(cp);
              }
            }
          }

          break;

        default :
          if (c > 31) {
            addCharEx((char) c, echo);
            ret = true;
          }

          break;
      }
    }

    return ret;
  }

  /**
   * Adds a character directly to the buffer, no interpretation is performed.
   *
   * @param c  the character
   * @param echo true to echo the character to the display; false otherwise
   */
  public void addCharEx(int c, boolean echo) throws IOException {
    if (cursorPosition >= theBuffer.length()) {
      theBuffer.append((char) c);
      cursorPosition = theBuffer.length();

      if ((theDisplay != null) && echo) {
        theDisplay.write((char) c);
      }
    } else if (insertMode) {
      theBuffer.insert(cursorPosition++, (char) c);

      if ((theDisplay != null) && echo) {
        theDisplay.insert((char) c);
      }
    } else {
      theBuffer.setCharAt(cursorPosition++, (char) c);

      if ((theDisplay != null) && echo) {
        theDisplay.write((char) c);
      }
    }

    changedFlag = true;
  }

  /**
   * Appends the specified text to the contents of the editor
   *
   * @param text  the text
   * @param echo true to echo the text to the display; false otherwise
   */
  public void appendTextEx(String text, boolean echo) throws IOException {
    int len = 0;

    if ((text == null) || (len = text.length()) == 0) {
      return;
    }

    if (cursorPosition >= theBuffer.length()) {
      theBuffer.append(text);

      if ((theDisplay != null) && echo) {
        theDisplay.write(text);
      }
    } else if (insertMode) {
      theBuffer.insert(cursorPosition++, text);

      if ((theDisplay != null) && echo) {
        theDisplay.insert(text);
      }
    } else {
      theBuffer.insert(cursorPosition + 1, text);
      theBuffer.delete(cursorPosition + 1, cursorPosition + len + 1);

      if ((theDisplay != null) && echo) {
        theDisplay.write(text);
      }
    }

    cursorPosition += len;
    changedFlag    = true;
  }

  /**
   * Gets the number of available characters in the editors buffer
   *
   * @return   a integer representing the number of available characters in the editor
   */
  public int available() {
    return theBuffer.length();
  }

  /**
   * Empties the contents of the editors buffer
   * @param echo true to echo the clearing to the display; false otherwise
   */
  public void clear(boolean echo) throws IOException {
    int cp = cursorPosition;

    theBuffer.setLength(0);
    cursorPosition = 0;
    changedFlag    = true;

    if (echo && (theDisplay != null)) {
      theDisplay.cursorBackward(cp);
      theDisplay.eraseToEndOfLine(true);
      theDisplay.syncDisplay();
    }
  }

  /**
   * Tests whether the contents of the editor has changed since the last call to the <code>resetChangeFlag()</code>
   * method
   *
   * @return   <code>true</code> if the contents have changed; <code>false</code>
   *           otherwise
   *
   * @see      #resetChangeFlag
   */
  public boolean getChangedFlag() {
    return changedFlag;
  }

  /**
   * Gets the current cursor position within the editor
   *
   * @return   a integer representing the current cursor position
   */
  public int getCursorPosition() {
    return cursorPosition;
  }

  /**
   * Gets the insert mode for the editor
   *
   * @return   <code>true</code> for insert; <code>false</code> for overwrite
   */
  public boolean getInsertMode() {
    return insertMode;
  }

  /**
   * Gets the contents of the editor
   *
   * @param clear  <code>true</code> to clear the editor's contents <code>false</code> to
   *               leave the contents unchanged
   *
   * @return   a string representing the editor's contents
   */
  public String getLine(boolean clear) throws IOException {
    String s = null;

    s = theBuffer.toString();

    if (clear) {
      clear(false);
    }

    return s;
  }

  /**
   * Resets the changed flag to <code>false</code>
   */
  public void resetChangeFlag() {
    changedFlag = false;
  }

  /**
   * Sets the current cursor position within the editor
   *
   * @param pos  the position
   */
  public void setCursorPosition(int pos) {
    if ((pos > -1) && (pos <= theBuffer.length()) && (pos != cursorPosition)) {
      cursorPosition = pos;
      changedFlag    = true;
    }
  }

  /**
   * Sets the insert mode for the editor
   *
   * @param mode  the mode, <code>true</code> for insert <code>false</code> for overwrite
   *
   * @return   a boolean representing the pervious mode, <code>true</code> for insert <code>false</code>
   *           for overwrite
   */
  public boolean setInsertMode(boolean mode) {
    boolean b = insertMode;

    insertMode = mode;

    return b;
  }

  /**
   * Sets the contents of the editor
   *
   * @param line  the line
   * @param echo true to echo the line to the display; false otherwise
   */
  public void setLine(String line, boolean echo) throws IOException {
    theBuffer.setLength(0);
    cursorPosition = line.length();
    theBuffer.append(line);
    changedFlag = true;

    if ((theDisplay != null) && echo) {
      theDisplay.write(line);
    }
  }

  /**
   * Gets the contents of the editor
   *
   * @return   a string representing the editor's contents
   */
  public String toString() {
    return theBuffer.toString();
  }

  /**
   * Toggles the editor's insert mode
   *
   * @return   a boolean representing the pervious mode, <code>true</code> for insert <code>false</code>
   *           for overwrite
   */
  public boolean toggleInsertMode() {
    insertMode = !insertMode;

    return insertMode;
  }

  /**
   * Deletes to the start of the next word
   */
  protected void deleteNextWord() throws IOException {
    int len = theBuffer.length();
    int n   = cursorPosition;

    if (cursorPosition == len) {
      return;
    }

    moveToNextWord();

    if (cursorPosition > n) {
      theBuffer.delete(n, cursorPosition);
      changedFlag = true;

      if (theDisplay != null) {
        theDisplay.deleteChars(cursorPosition - n);
      }
    }

    cursorPosition = n;
  }

  /**
   * Deletes to the start of the previous word
   */
  protected void deletePreviousWord() throws IOException {
    int len = theBuffer.length();
    int n   = cursorPosition;

    if (cursorPosition == 0) {
      return;
    }

    moveToPreviousWord();

    if (cursorPosition < n) {
      theBuffer.delete(cursorPosition, n);
      changedFlag = true;
      n           = n - cursorPosition;

      if ((theDisplay != null) && (n > 0)) {
        theDisplay.cursorBackward(n);
        theDisplay.deleteChars(n);
      }

      len = theBuffer.length();

      if (cursorPosition > len) {
        cursorPosition = len;
      }
    }
  }

  /**
   * Moves the editors internal cursor backward to the first non-space character
   * encountered
   */
  protected void moveBackwardToNextNonSpace() {
    int len = theBuffer.length();

    if (len == 0) {
      return;
    }

    if (cursorPosition == len) {
      cursorPosition--;
    }

    while(cursorPosition > -1) {
      if (theBuffer.charAt(cursorPosition) > 32) {
        break;
      }

      cursorPosition--;
    }

    if (cursorPosition < 0) {
      cursorPosition = 0;
    }
  }

  /**
   * Moves the editors internal cursor backward to the first space character
   * encountered
   */
  protected void moveBackwardToNextSpace() {
    int len = theBuffer.length();

    if (len == 0) {
      return;
    }

    if (cursorPosition == len) {
      cursorPosition--;
    }

    while(cursorPosition > -1) {
      if (theBuffer.charAt(cursorPosition) < 33) {
        break;
      }

      cursorPosition--;
    }

    if (cursorPosition < 0) {
      cursorPosition = 0;
    }
  }

  /**
   * Moves the editors internal cursor forward to the first non-space character
   * encountered
   */
  protected void moveForwardToNextNonSpace() {
    int len = theBuffer.length();

    while(cursorPosition < len) {
      if (theBuffer.charAt(cursorPosition) > 32) {
        break;
      }

      cursorPosition++;
    }
  }

  /**
   * Moves the editors internal cursor forward to the first space character
   * encountered
   */
  protected void moveForwardToNextSpace() {
    int len = theBuffer.length();

    while(cursorPosition < len) {
      if (theBuffer.charAt(cursorPosition) < 33) {
        break;
      }

      cursorPosition++;
    }
  }

  /**
   * Moves the editors internal cursor to the start of the next word
   */
  protected void moveToNextWord() {
    int len = theBuffer.length();

    if (cursorPosition == len) {
      return;
    }

    if (theBuffer.charAt(cursorPosition) < 33) {
      moveForwardToNextNonSpace();
    }

    moveForwardToNextSpace();
    cursorPosition++;

    if (cursorPosition > len) {
      cursorPosition = len;
    }
  }

  /**
   * Moves the editors internal cursor to the start of the previous word
   */
  protected void moveToPreviousWord() {
    int len = theBuffer.length();

    if (cursorPosition == 0) {
      return;
    }

    cursorPosition--;

    if ((cursorPosition < len) && (theBuffer.charAt(cursorPosition) < 33)) {
      moveBackwardToNextNonSpace();
    }

    moveBackwardToNextSpace();

    if (cursorPosition != 0) {
      cursorPosition++;
    }

    if (cursorPosition > len) {
      cursorPosition = len;
    }
  }
}
