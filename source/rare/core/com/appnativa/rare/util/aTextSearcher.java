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

import com.appnativa.util.RegularExpressionFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract text searching class
 *
 * @author Don DeCoteau
 */
public abstract class aTextSearcher implements iTextSearcher {
  protected final static int POSITION_BOTTOM       = 1;
  protected final static int POSITION_CURRENT      = 2;
  protected final static int POSITION_TOP          = 0;
  protected String           oldSearchFilter       = null;
  protected boolean          wholeWordsOnly        = false;
  protected boolean          useWildcards          = false;
  protected boolean          useRegularExpression  = false;
  protected boolean          selectionOnly         = false;
  protected boolean          searchFromBegining    = true;
  protected boolean          oldSelectionOnly      = false;
  protected boolean          oldSearchFromBegining = true;
  protected boolean          oldMatchCase          = false;
  protected boolean          matchCase             = false;
  protected boolean          hasAllMatches         = false;
  protected boolean          wrapSearches          = true;
  protected Matcher          patternMatcher;
  protected Pattern          searchPattern;
  protected String           searchString;

  @Override
  public void clear() {
    patternMatcher        = null;
    selectionOnly         = false;
    oldSelectionOnly      = false;
    searchFromBegining    = true;
    oldSearchFromBegining = true;
    oldSearchFilter       = null;
    hasAllMatches         = false;
  }

  @Override
  public boolean findAndSelectNext() {
    return findAndSelectNext(searchString);
  }

  @Override
  public boolean findAndSelectPrevious() {
    return findAndSelectPrevious(searchString);
  }

  public void removeAllHighlights() {}

  @Override
  public void replace(String newvalue) {}

  @Override
  public int replaceAll(String oldValue, String newvalue) {
    if (!wasFound()) {
      if (!findAndSelectNext(oldValue)) {
        reset();

        return 0;
      }
    }

    int count = 1;

    replace(newvalue);

    boolean ob = searchFromBegining;

    try {
      searchFromBegining = false;

      while(findAndSelectNext(oldValue)) {
        count++;
        replace(newvalue);
      }

      searchFromBegining = true;

      if (findAndSelectNext(oldValue)) {
        searchFromBegining = false;
        replace(newvalue);
        count++;

        while(findAndSelectNext(oldValue)) {
          count++;
          replace(newvalue);
        }
      }

      reset();

      return count;
    } finally {
      searchFromBegining = ob;
    }
  }

  @Override
  public void reset() {
    patternMatcher        = null;
    wholeWordsOnly        = false;
    oldMatchCase          = false;
    useRegularExpression  = false;
    selectionOnly         = false;
    oldSelectionOnly      = false;
    searchFromBegining    = true;
    oldSearchFromBegining = true;
    oldSearchFilter       = null;
    hasAllMatches         = false;
  }

  @Override
  public boolean wasSearchPerformed() {
    return oldSearchFilter != null;
  }

  @Override
  public void setCaseSensitive(boolean on) {
    if (matchCase != on) {
      matchCase = on;
      flagChanged();
    }
  }

  @Override
  public void setHighlightAll(boolean on) {}

  @Override
  public void setMatchWholeWords(boolean on) {
    if (wholeWordsOnly != on) {
      wholeWordsOnly = on;
      flagChanged();
    }
  }

  @Override
  public void setSearchFromBegining(boolean on) {
    searchFromBegining = on;
  }

  @Override
  public void setSearchSelection(boolean on) {
    if (selectionOnly != on) {
      selectionOnly = on;
      clear();
      flagChanged();
    }
  }

  @Override
  public String setSearchString(String text) {
    String s = searchString;

    searchString = text;

    return s;
  }

  @Override
  public void setUseRegularExpressions(boolean on) {
    if (useRegularExpression != on) {
      useRegularExpression = on;
      flagChanged();
    }
  }

  @Override
  public void setWildcardEnabled(boolean on) {
    if (useWildcards != on) {
      useWildcards = on;
      flagChanged();
    }
  }

  @Override
  public void setWrapSearches(boolean wrapSearches) {
    this.wrapSearches = wrapSearches;
  }

  @Override
  public String getSearchString() {
    return searchString;
  }

  @Override
  public boolean isCaseSensitive() {
    return matchCase;
  }

  @Override
  public boolean isHighlightAll() {
    return false;
  }

  @Override
  public boolean isHighlightAllSupported() {
    return false;
  }

  @Override
  public boolean isMatchWholeWords() {
    return wholeWordsOnly;
  }

  @Override
  public boolean isSearchFromBegining() {
    return searchFromBegining;
  }

  @Override
  public boolean isSearchSelection() {
    return selectionOnly;
  }

  @Override
  public boolean isUseRegularExpressions() {
    return useRegularExpression;
  }

  @Override
  public boolean isWildcardEnabled() {
    return useWildcards;
  }

  @Override
  public boolean isWrapSearches() {
    return wrapSearches;
  }

  protected boolean caseChanged() {
    return oldMatchCase != this.isCaseSensitive();
  }

  protected String createFilter(String s) {
    String filter;

    if (isWildcardEnabled()) {
      filter = RegularExpressionFilter.parseWildcardFilter(s);

      if (wholeWordsOnly) {
        filter = "\\b" + filter + "\\b";
      }
    } else if (useRegularExpression) {
      filter = s;
    } else {
      filter = RegularExpressionFilter.parseStringFilter(s, wholeWordsOnly);
    }

    return filter;
  }

  protected void flagChanged() {
    if (searchPattern != null) {
      oldSearchFilter = null;
      searchPattern   = null;

      if (hasAllMatches) {
        hasAllMatches = false;

        if (isHighlightAll()) {
          highlightAll();
        }
      }
    }
  }

  protected void highlightAll() {}

  protected boolean selectionChanged() {
    return this.selectionOnly != this.isSearchSelection();
  }
}
