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

/**
 * Interface for text searching
 *
 * @author Don DeCoteau
 */
public interface iTextSearcher {

  /**
   * Clears the current search state
   */
  void clear();

  /**
   * Finds and select the next match of the existing search text
   *
   * @return true if a match was found; false otherwise
   */
  boolean findAndSelectNext();

  /**
   * Finds and select the next match of the specified search text
   *
   * @param text the text to find
   *
   * @return true if a match was found; false otherwise
   */
  boolean findAndSelectNext(String text);

  /**
   * Finds and select the previous match of the existing search text
   *
   * @return true if a match was found; false otherwise
   */
  boolean findAndSelectPrevious();

  /**
   * Finds and select the previous match of the specified search text
   *
   * @param text the text to find
   *
   * @return true if a match was found; false otherwise
   */
  boolean findAndSelectPrevious(String text);

  /**
   * Get the current search string
   *
   * @return the current search string
   */
  String getSearchString();

  /**
   * Returns whether searching is case sensitive
   *
   * @return true if searching is case sensitive; false otherwise
   */
  boolean isCaseSensitive();

  /**
   * Returns whether highlight all is enabled
   *
   * @return true if highlight all is enabled; false otherwise
   */
  boolean isHighlightAll();

  /**
   * Test is the searched supports the highlight all option
   *
   * @return true if highlight all is supported; false otherwise
   */
  boolean isHighlightAllSupported();

  /**
   * Returns whether searching is done on word boundaries
   *
   * @return true if searching is done on word boundaries; false otherwise
   */
  boolean isMatchWholeWords();

  /**
   * Returns whether searching will start from the beginning (or end depending on the search direction)
   *
   * @return true if searching from the beginning (or end); false otherwise
   */
  boolean isSearchFromBegining();

  /**
   * Returns whether searching is restricted to the current selection
   *
   * @return true if searching is restricted to the current selection; false otherwise
   */
  boolean isSearchSelection();

  /**
   * Returns whether searching is being done using regular expressions
   *
   * @return true if searching is being done using regular expressions; false otherwise
   */
  boolean isUseRegularExpressions();

  /**
   * Returns whether wildcard searching is enabled
   *
   * @return true if wildcard searching is enabled; false otherwise
   */
  boolean isWildcardEnabled();

  /**
   * Returns whether searches will wrap around
   *
   * @return true if searches will wrap around; false otherwise
   */
  boolean isWrapSearches();

  /**
   * Replaces the currently selected item with the specified string
   *
   * @param newValue the new value
   */
  void replace(String newValue);

  /**
   * Replaces all matching items with the specified string
   *
   * @param oldValue the old value
   * @param newValue the new value
   * @return the number of replacements
   */
  int replaceAll(String oldValue, String newValue);

  /**
   * Resets the searcher to its initial state
   */
  void reset();

  /**
   * Sets whether searching is case sensitive
   *
   * @param on true if searching is case sensitive; false otherwise
   */
  void setCaseSensitive(boolean on);

  /**
   * Sets whether highlight all is enabled
   *
   * @param on true if highlight all is enabled; false otherwise
   */
  void setHighlightAll(boolean on);

  /**
   * Sets whether searching is done on word boundaries
   *
   * @param on true if searching is done on word boundaries; false otherwise
   */
  void setMatchWholeWords(boolean on);

  /**
   * Sets whether searching will start from the beginning (or end depending on the search direction)
   *
   * @param on true if searching from the beginning (or end); false otherwise
   */
  void setSearchFromBegining(boolean on);

  /**
   * Sets whether searching is restricted to the current selection
   *
   * @param on true if searching is restricted to the current selection; false otherwise
   */
  void setSearchSelection(boolean on);

  /**
   * Sets the search string that will be utilized to perform searches
   *
   * @param text the text to search for
   * @return the previous search string
   */
  String setSearchString(String text);

  /**
   * Sets whether searching is being done using regular expressions
   *
   * @param on true if searching is being done using regular expressions; false otherwise
   */
  void setUseRegularExpressions(boolean on);

  /**
   * Sets whether wildcard searching is enabled
   *
   * @param on true if wildcard searching is enabled; false otherwise
   */
  void setWildcardEnabled(boolean on);

  /**
   * Sets whether searches will wrap around
   *
   * @param wrap true if searches will wrap around; false otherwise
   */
  void setWrapSearches(boolean wrap);

  /**
   * Returns whether the last search was successful
   *
   * @return true if the search text was found; false otherwise
   */
  boolean wasFound();

  /**
   * Test whether a search has been performed.
   * Both <CODE>clear()</CODE> and <CODE>reset()</CODE> will reset this value to <B>false</B>
   *
   * @return true if a search was performed; false otherwise
   */
  boolean wasSearchPerformed();
}
