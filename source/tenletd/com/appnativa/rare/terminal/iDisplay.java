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
 * An interface for handling displays
 *
 * @version    2.3
 * @author     Don DeCoteau
 */
public interface iDisplay {

  /**
   * Delete the character at the prior cursor position.
   */
  public void backspace() throws IOException;

  /**
   * Generates a beep
   */
  public void beep() throws IOException;

  /**
   * Clears the screen
   */
  public void clearDisplay() throws IOException;

  /**
   * Unselects any selected text
   */
  public void clearSelection() throws IOException;

  /**
   * Clears the tab stop at the current cursor position
   */
  public void clearTabStop() throws IOException;

  /**
   * Clears all tab stops
   */
  public void clearTabStops() throws IOException;

  /**
   * Moves the cursor backwards the specified number of positions
   *
   * @param n the number of positions
   */
  public void cursorBackward(int n) throws IOException;

  /**
   * Moves the cursor down the specified number of positions
   *
   * @param n the number of positions
   */
  public void cursorDown(int n) throws IOException;

  /**
   * Moves the cursor forwards the specified number of positions
   *
   * @param n the number of positions
   */
  public void cursorForward(int n) throws IOException;

  /**
   * Moves the cursor up the specified number of positions
   *
   * @param n the number of positions
   */
  public void cursorUp(int n) throws IOException;

  /**
   * Delete the character at the current cursor position.
   */
  public void deleteChar() throws IOException;

  /**
   * Delete the specified number of characters starting at the current cursor
   * position. All characters to the right of the deleted characters will be
   * shifted to the left.
   *
   * @param len the number of characters to delete
   */
  public void deleteChars(int len) throws IOException;

  /**
   * Deletes lines starting at the current cursor row. Subsequent lines will be
   * scrolled up to fill the space and blank lines are inserted at the end of
   * the bottom margin.
   *
   * @param num the number of lines to delete
   */
  public void deleteLine(int num) throws IOException;

  /**
   * Delete the character at the prior cursor position.
   */
  public void destructiveBackSpace() throws IOException;

  /**
   * Marks the whole display as dirty
   */
  public void dirty() throws IOException;

  /**
   * Enables mouse reporting
   *
   * @param flag <code>true</code> to enable; <code>false</code> otherwise
   */
  public void enableMouseReporting(boolean flag) throws IOException;

  /**
   * End a protected area
   */
  public void endProtectedArea() throws IOException;

  /**
   * Erases characters starting at the current cursor position
   *
   * @param len the number of characters
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseChars(int len, boolean all) throws IOException;

  /**
   * Erase the screen
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseDisplay(boolean all) throws IOException;

  /**
   * Erases the text an attributes within the current area
   *
   * @param type the type of erasure (0=erase to beginning of screen, 1=erase to end of screen, 2=erase all)
   */
  public void eraseInArea(int type) throws IOException;

  /**
   * Erases the text an attributes within the current area
   *
   * @param type the type of erasure (0=erase to beginning of field, 1=erase to end of field, 2=erase field)
   */
  public void eraseInField(int type) throws IOException;

  /**
   * Erase the line at the current cursor row
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseLine(boolean all) throws IOException;

  /**
   * Erases characters, starting from the current cursor position to the
   * beginning of the screen.
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseToBegOfDisplay(boolean all) throws IOException;

  /**
   * Delete characters starting from the current column to the beginning of the
   * line.
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseToBegOfLine(boolean all) throws IOException;

  /**
   * Erases characters, starting from the current cursor position to the end of
   * the screen.
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseToEndOfDisplay(boolean all) throws IOException;

  /**
   * Erases characters, starting from the current cursor position to the end of
   * the line.
   *
   * @param all <code>true</code> to erase protected text; <code>false</code>
   *        otherwise.
   */
  public void eraseToEndOfLine(boolean all) throws IOException;

  /**
   * Gets the value of the bottom margin of the display
   *
   * @return the value of the bottom margin of the display
   */
  public int getBottomMargin();

  /**
   * Gets the number of columns on the screen.
   *
   * @return the number of columns
   */
  public int getColumns();

  /**
   * Gets the value of the left margin
   *
   * @return the left margin
   */
  public int getLeftMargin();

  /**
   * Gets the value of the right margin
   *
   * @return the right margin
   */
  public int getRightMargin();

  /**
   * Get number of rows on the screen.
   *
   * @return the number of rows
   */
  public int getRows();

  /**
   * Gets the top margin
   *
   * @return the top margin
   */
  public int getTopMargin();

  /**
   * Inserts a character at the current cursor position. All characters right
   * of the insert position will be shifted to the right. This function
   * invokes the emulator.
   *
   * @param c the character
   */
  public void insert(char c) throws IOException;

  /**
   * Insert a string at the current cursor position. All characters right of
   * the insert position will be shifted to the right.
   *
   * @param s the string
   */
  public void insert(String s) throws IOException;

  /**
   * Inserts blank lines at the current cursor position. The current line and
   * all previous lines are scrolled one line up.
   *
   * @param num the number of lines to insert
   */
  public void insertLine(int num) throws IOException;

  /**
   * Insert a number of spaces at the current cursor position. All characters
   * right of the insert position will be shifted to the right.
   *
   * @param num the number of spaces to insert
   */
  public void insertSpaces(int num) throws IOException;

  /**
   * Moves to the next line of the display (scrolling if necessary). The cursor
   * column is set to that of the left margin
   */
  public void linefeed() throws IOException;

  /**
   * Moves to the next specified number of lines of the display (scrolling if
   * necessary)
   *
   * @param n the number of lines
   * @param bol <code>true</code> to move to the beginning of the line; <code>false</code> otherwise
   */
  public void nextLine(int n, boolean bol) throws IOException;

  /**
   * Moves to the next tab stop
   *
   * @param n the column from which to start the search
   */
  public void nextTabStop(int n) throws IOException;

  /**
   * Moves to the specified number of previous lines of the display (scrolling
   * if necessary)
   *
   * @param n the number of lines
   * @param bol <code>true</code> to move to the beginning of the line; <code>false</code> otherwise
   */
  public void previousLine(int n, boolean bol) throws IOException;

  /**
   * Moves to the previous tab stop
   *
   * @param n the column from which to start the search
   */
  public void previousTabStop(int n) throws IOException;

  /**
   * Resets the display. This method calculates the display's characteristics and
   * resets the display. It should be called after any material change is made
   * to the display object.
   */
  public void resetDisplay() throws IOException;

  /**
   * Resizes the screen without resetting the display
   *
   * @param w the width
   * @param h the height
   */
  public void resizeDisplay(int w, int h) throws IOException;

  /**
   * Restores a previously saved cursor state
   */
  public Object restoreCursor() throws IOException;

  /**
   * Saves the current cursor state
   *
   * @param obj an object to save with the cursor state
   */
  public void saveCursor(Object obj) throws IOException;

  /**
   * Scrolls down the specified number of lines
   *
   * @param n the number of lines
   */
  public void scrollDown(int n) throws IOException;

  /**
   * Scrolls up the specified number of lines.
   *
   * @param n the number of lines
   */
  public void scrollUp(int n) throws IOException;

  /**
   * Sets the value of the auto wrapping flag
   *
   * @param b the value
   */
  public void setAutoWrap(boolean b) throws IOException;

  /**
   * Sets the current foreground color
   *
   * @param color the color
   */
  public void setBackground(int color) throws IOException;

  /**
   * Method that sets blink attribute for writing on the terminal.
   * The final representation on the terminal might differ by the
   * terminal type.
   *
   * @param b Boolean that flags on/off
   */
  public void setBlink(boolean b) throws IOException;

  /**
   * Method that sets bold as attribute for writing on the terminal.
   * The final representation on the terminal might differ by the
   * terminal type.
   *
   * @param b Boolean that flags on/off
   */
  public void setBold(boolean b) throws IOException;

  /**
   * Sets the value of the <code>constrainCursor</code> flag. When
   * <code>true</code> the cursor will be constrained to the active scrolling
   * region an cannot be programmatically moved outside the bounds of the
   * region. Functions that automatically move the cursor always obey the
   * constraints of a scrolling region. However, if the
   * <code>constrainCursor</code> is false (the default value) then the cursor
   * can be moved outside a scrolling region be calling a method that
   * explicitly set the cursor position.
   *
   * @param b the value
   */
  public void setConstrainCursor(boolean b) throws IOException;

  /**
   * Moves the cursor to the specified position.
   *
   * @param x x-coordinate (column)
   */
  public void setCursorColumn(int x) throws IOException;

  /**
   * Moves the cursor to the specified position.
   *
   * @param x x-coordinate (column)
   * @param y y-coordinate (row)
   */
  public void setCursorPosition(int x, int y) throws IOException;

  /**
   * Moves the cursor to the specified row.
   *
   * @param y y-coordinate (row)
   */
  public void setCursorRow(int y) throws IOException;

  /**
   * Sets the current foreground color
   *
   * @param color the color
   */
  public void setForeground(int color) throws IOException;

  /**
   * Set the screen insert mode
   *
   * @param b <code>true</code> for insert; <code>false</code> otherwise.
   */
  public void setInsertMode(boolean b) throws IOException;

  /**
   * Method that sets italic as attribute for writing on the terminal.
   * The final representation on the terminal might differ by the
   * terminal type.
   *
   * @param b Boolean that flags on/off
   */
  public void setItalic(boolean b) throws IOException;

  /**
   * Sets the value of the right margin
   *
   * @param right the right margin
   *
   * @return <code>true</code> if the margin was set; <code>false</code>
   *         otherwise.
   */
  public boolean setRightMargin(int right) throws IOException;

  /**
   * Sets the selection end point to that of the current cursor position
   */
  public void setSelectionEnd() throws IOException;

  /**
   * Sets the selection starting point to that of the current cursor position
   */
  public void setSelectionStart() throws IOException;

  /**
   * Sets a tab stop at the current cursor position
   */
  public void setTabStop() throws IOException;

  /**
   * Method that sets underlined as attribute for writing on the terminal.
   * The final representation on the terminal might differ by the
   * terminal type.
   *
   * @param b Boolean that flags on/off
   */
  public void setUnderlined(boolean b) throws IOException;

  /**
   * Establishes a vertical scrolling region
   *
   * @param top the row at the top of the region
   * @param bottom the row at the bottom of the region
   */
  public void setVScrollRegion(int top, int bottom) throws IOException;

  /**
   * Sets whether the cursor is visible or not.
   *
   * @param doshow <code>true</code> to show the cursor; <code>false</code>
   *        otherwise
   */
  public void showCursor(boolean doshow) throws IOException;

  /**
   * Starts a protected area
   */
  public void startProtectedArea() throws IOException;

  /**
   * Synchronizes the display.
   */
  public void syncDisplay() throws IOException;

  /**
   * Toggles the insert mode flag
   */
  public void toggleInsertMode() throws IOException;

  /**
   * Write a character to the display at the current cursor position. The
   * character previously at that position will be overwritten. This function
   * invokes the emulator.
   *
   * @param c the character
   */
  public void write(char c) throws IOException;

  /**
   * Write a string to the display at the current cursor position. Any
   * characters previously at that position will be overwritten. This function
   * invokes the emulator.
   *
   * @param s the string
   */
  public void write(String s) throws IOException;

  /**
   * Write a set of characters to the display at the current cursor position.
   * The characters previously on that position will be overwritten. This
   * function invokes the emulator.
   *
   * @param chars the character array
   * @param offset the starting position within the array
   * @param len the number of characters in the array to use
   */
  public void write(char[] chars, int offset, int len) throws IOException;
}
