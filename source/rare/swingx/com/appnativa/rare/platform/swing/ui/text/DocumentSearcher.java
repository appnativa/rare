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

package com.appnativa.rare.platform.swing.ui.text;

import com.appnativa.rare.Platform;
import com.appnativa.rare.util.aTextSearcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.View;

/**
 * A text searcher for JTextComponent documents
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class DocumentSearcher extends aTextSearcher implements DocumentListener {
  private int                          elementPos     = -1;
  private boolean                      highlightAll   = false;
  private int                          lastFoundIndex = -1;
  private ArrayList<Location>          locationList   = new ArrayList<Location>();
  private int                          searchPos      = -1;
  private JTextComponent               component;
  private int                          currentPos;
  private Document                     document;
  private Highlighter.HighlightPainter highlightAllPainter;
  private Location                     lastFoundLocation;
  private Position                     selectionEnd;
  private Position                     selectionStart;

  /**
   * Constructs a new instance
   *
   * @param comp the component
   */
  public DocumentSearcher(JTextComponent comp) {
    component           = comp;
    highlightAllPainter = new AHighlightPainter(getHighlightColor());
  }

  @Override
  public void changedUpdate(DocumentEvent e) {}

  @Override
  public void clear() {
    super.clear();
    clearHighlights();
    currentPos        = component.getCaretPosition();
    lastFoundLocation = null;
    lastFoundIndex    = -1;
  }

  @Override
  public boolean findAndSelectNext(String text) {
    searchString = text;

    boolean top = false;

    if (wrapSearches && (oldSearchFilter != null) && (lastFoundLocation == null)) {
      top = true;
    }

    Location loc;

    if (top || searchFromBegining) {
      loc = find(text, POSITION_TOP, true);
    } else {
      loc = find(text, POSITION_CURRENT, true);
    }

    if (loc != null) {
      int start = loc.getOffset();
      int end   = start + loc.length;

      component.select(start, end);
    }

    return loc != null;
  }

  @Override
  public boolean findAndSelectPrevious(String text) {
    searchString = text;

    boolean bottom = false;

    if (wrapSearches && (oldSearchFilter != null) && (lastFoundLocation == null)) {
      bottom = true;
    }

    Location loc;

    if (bottom || searchFromBegining) {
      loc = find(text, POSITION_BOTTOM, false);
    } else {
      loc = find(text, POSITION_CURRENT, false);
    }

    if (loc != null) {
      int start = loc.getOffset();
      int end   = start + loc.length;

      component.select(start, end);
    }

    return loc != null;
  }

  public JComponent getComponent() {
    return component;
  }

  public static Color getHighlightColor() {
    Color c = Platform.getUIDefaults().getColor("Rare.searchHighlightAll");

    if (c == null) {
      c = new Color(255, 255, 54);
    }

    return c;
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    if (!hasAllMatches) {
      return;
    }

    clearHighlights();
    oldSearchFilter = null;
  }

  @Override
  public boolean isHighlightAll() {
    return highlightAll;
  }

  @Override
  public boolean isHighlightAllSupported() {
    return true;
  }

  @Override
  public void removeAllHighlights() {
    Highlighter hilite = component.getHighlighter();
    int         len    = locationList.size();
    Location    loc;

    for (int i = 0; i < len; i++) {
      loc = locationList.get(i);

      if (loc.highlight != null) {
        hilite.removeHighlight(loc.highlight);
        loc.highlight = null;
      }
    }
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    if (!hasAllMatches) {
      return;
    }

    Highlighter hilite = component.getHighlighter();
    int         start  = e.getOffset();
    int         end    = e.getLength() + start;
    int         len    = locationList.size();
    Location    loc;
    int         lstart;
    int         lend;

    for (int i = 0; i < len; i++) {
      loc    = locationList.get(i);
      lstart = loc.getOffset();
      lend   = lstart + loc.length;

      if (intersects(start, end, lstart, lend)) {
        if (loc.highlight != null) {
          hilite.removeHighlight(loc.highlight);
        }

        locationList.remove(i);
        len--;
        i--;
      }
    }
  }

  @Override
  public void replace(String s) {
    Location loc = lastFoundLocation;

    if (loc != null) {
      int start = loc.getOffset();
      int end   = start + loc.length;

      if ((component.getSelectionStart() == start) && (component.getSelectionEnd() == end)) {
        component.replaceSelection(s);
        currentPos = component.getCaretPosition() + s.length();
      }
    }
  }

  @Override
  public void reset() {
    super.reset();
    highlightAll      = false;
    selectionEnd      = null;
    selectionStart    = null;
    lastFoundLocation = null;
    lastFoundIndex    = -1;
    clearHighlights();

    Document doc = component.getDocument();

    if (doc != document) {
      if (document != null) {
        document.removeDocumentListener(this);
      }

      document = doc;
      document.addDocumentListener(this);
    }

    currentPos = component.getCaretPosition();
  }

  @Override
  public void setHighlightAll(boolean on) {
    if (highlightAll != on) {
      highlightAll = on;

      if (on && (searchString != null) && (searchPattern != null)) {
        if (this.hasAllMatches) {
          reHighlight();
        } else {
          highlightAll();
        }
      } else {
        removeAllHighlights();
      }
    }
  }

  @Override
  public void setSearchSelection(boolean on) {
    if (selectionOnly != on) {
      selectionOnly = on;

      if (on) {
        try {
          Document doc = component.getDocument();

          selectionStart = doc.createPosition(component.getSelectionStart());
          elementPos     = doc.getDefaultRootElement().getElementIndex(selectionStart.getOffset()) - 1;
          selectionEnd   = doc.createPosition(component.getSelectionEnd());
        } catch(BadLocationException e) {
          Platform.ignoreException(null, e);
        }
      }
    }
  }

  @Override
  public boolean wasFound() {
    return lastFoundLocation != null;
  }

  protected Location find(String s, int position, boolean next) {
    Document document = component.getDocument();

    if (document.getLength() == 0) {
      return null;
    }

    if ((s == null) || (s.length() == 0)) {
      return null;
    }

    setupSearch(s, position, next);
    lastFoundLocation = next
                        ? findNextEx()
                        : findPreviousEx();

    return lastFoundLocation;
  }

  @Override
  protected void highlightAll() {
    if ((searchString == null) || (searchString.length() == 0)) {
      return;
    }

    clearHighlights();
    hasAllMatches = true;

    if (searchPattern == null) {
      setupSearch(searchString, POSITION_TOP, true);
    }

    SegmentSequence seg   = new SegmentSequence();
    Document        doc   = component.getDocument();
    int             start = 0;
    int             len   = doc.getLength();

    if (selectionOnly) {
      start = selectionStart.getOffset();
      len   = selectionEnd.getOffset() - start;

      if (len < 0) {
        len *= -1;
      }
    }

    if (len < 1) {
      return;
    }

    try {
      doc.getText(start, len, seg);
    } catch(BadLocationException ex) {
      return;
    }

    if (patternMatcher == null) {
      patternMatcher = searchPattern.matcher(seg);
    } else {
      patternMatcher.reset(seg);
    }

    Location loc;

    if (patternMatcher.find()) {
      loc = createLocation(doc, patternMatcher.start(), patternMatcher.end());

      if (loc != null) {
        locationList.add(loc);
      }

      while(patternMatcher.find()) {
        loc = createLocation(doc, patternMatcher.start(), patternMatcher.end());

        if (loc != null) {
          locationList.add(loc);
        }
      }
    }

    reHighlight();
  }

  protected void setupSearch(String s, int position, boolean next) {
    if ((s == null) || (s.length() == 0)) {
      return;
    }

    String  filter = createFilter(s);
    boolean mcase  = isCaseSensitive();

    if ((oldSearchFilter == null) ||!filter.equals(oldSearchFilter) || caseChanged() || selectionChanged()) {
      calculatePosition(position, next, true);
      searchPattern = null;
    } else {
      calculatePosition(position, next, false);
    }

    oldMatchCase          = isCaseSensitive();
    oldSearchFromBegining = isSearchFromBegining();
    oldSelectionOnly      = isSearchSelection();
    oldSearchFilter       = filter;

    if (searchPattern == null) {
      int pt = 0;

      if (!mcase) {
        pt |= Pattern.CASE_INSENSITIVE;
      }

      searchPattern  = Pattern.compile(filter, pt);
      patternMatcher = null;

      if (highlightAll) {
        highlightAll();
      }
    }
  }

  private void calculatePosition(int position, boolean next, boolean reset) {
    if (reset) {
      removeAllHighlights();
      hasAllMatches = false;
    }

    Document doc = component.getDocument();

    if (doc != document) {
      if (document != null) {
        document.removeDocumentListener(this);
      }

      document = doc;
      document.addDocumentListener(this);
    }

    Element root = doc.getDefaultRootElement();

    currentPos = component.getCaretPosition();

    switch(position) {
      case POSITION_TOP :
        if (selectionOnly && (selectionStart != null)) {
          searchPos  = selectionStart.getOffset();
          elementPos = root.getElementIndex(searchPos) - 1;
        } else {
          elementPos = -1;
          searchPos  = 0;
        }

        break;

      case POSITION_BOTTOM :
        if (selectionOnly && (selectionEnd != null)) {
          searchPos  = selectionEnd.getOffset();
          elementPos = root.getElementIndex(searchPos) - 1;
        } else {
          elementPos = root.getElementCount();

          if (elementPos > 0) {
            searchPos = root.getElement(elementPos - 1).getEndOffset();
          } else {
            searchPos = 0;
          }
        }

        break;

      default :
        searchPos  = currentPos;
        elementPos = root.getElementIndex(searchPos) + (next
                ? -1
                : 1);    // found list always increments before starting

        if (reset) {
          int spos = component.getSelectionStart();
          int epos = component.getSelectionEnd();

          if (epos - spos > 0) {
            searchPos = (spos < epos)
                        ? spos
                        : epos;

            if (!next) {
              searchPos = (searchPos == 0)
                          ? 0
                          : searchPos - 1;
            }
          }
        } else if (!next) {
          int spos = component.getSelectionStart();
          int epos = component.getSelectionEnd();

          if (epos - spos > 0) {
            searchPos = (spos < epos)
                        ? spos
                        : epos;
            searchPos = (searchPos == 0)
                        ? 0
                        : searchPos - 1;
          }
        }

        break;
    }
  }

  private void clearHighlights() {
    removeAllHighlights();
    locationList.clear();
    hasAllMatches = false;
  }

  private Location createLocation(Document doc, int start, int end) {
    try {
      return new Location(doc.createPosition(start), end - start);
    } catch(Exception e) {
      return null;
    }
  }

  private Location findLocation(int inc) {
    Document doc = component.getDocument();
    Element  e;
    Element  root  = doc.getDefaultRootElement();
    int      count = root.getElementCount();
    int      pos;
    int      start;
    Location loc = null;

    while(true) {
      elementPos += inc;

      if ((elementPos < 0) || (elementPos >= count)) {
        break;
      }

      e   = root.getElement(elementPos);
      pos = e.getStartOffset();

      int end = e.getEndOffset();

      if (selectionOnly && (selectionStart != null) && (selectionEnd != null)) {
        int n = selectionEnd.getOffset();

        if (n < end) {
          end = n;
        }

        n = selectionStart.getOffset();

        if (n > pos) {
          pos = n;
        }
      }

      if (end - pos < 1) {
        return null;
      }

      String s = null;

      try {
        s = doc.getText(pos, end - pos);
      } catch(BadLocationException ex) {
        return null;
      }

      if (s.length() == 0) {
        continue;
      }

      if (patternMatcher == null) {
        patternMatcher = searchPattern.matcher(s);
      } else {
        patternMatcher.reset(s);
      }

      if (patternMatcher.find()) {
        start = pos + patternMatcher.start();
        end   = pos + patternMatcher.end();

        if (inc > 0) {
          while((start < searchPos) && patternMatcher.find()) {
            start = pos + patternMatcher.start();
            end   = pos + patternMatcher.end();
          }

          if ((start >= searchPos)) {
            loc = createLocation(doc, start, end);
          }
        } else {
          while((start < searchPos) && patternMatcher.find()) {
            if (pos + patternMatcher.start() > searchPos) {
              break;
            }

            start = pos + patternMatcher.start();
            end   = pos + patternMatcher.end();
          }

          if (start < searchPos) {
            loc = createLocation(doc, start, end);
          }
        }

        if (loc != null) {
          return loc;
        }
      }
    }

    return null;
  }

  private Location findNextEx() {
    if (hasAllMatches) {
      return indexOfGTE(searchPos);
    } else {
      return findLocation(1);
    }
  }

  private Location findPreviousEx() {
    if (hasAllMatches) {
      return lastIndexOfLT(searchPos);
    } else {
      return findLocation(-1);
    }
  }

  /**
   * Searches for the first occurrence of a value that is greater than the given argument
   *
   * @param e     the value to search for.
   *
   * @return   the index of the first occurrence that matches; returns <tt>-1</tt>
   *           if no match was found.
   *
   */
  private Location indexOfGTE(int e) {
    int      len = locationList.size();
    int      n   = 0;
    Location loc;

    if ((lastFoundIndex > -1) && (lastFoundIndex < len)) {
      loc = locationList.get(lastFoundIndex);

      if (loc.getOffset() < e) {
        n = lastFoundIndex;
      }
    }

    for (int i = n; i < len; i++) {
      loc = locationList.get(i);

      if (loc.getOffset() >= e) {
        lastFoundIndex = i;

        return loc;
      }
    }

    return null;
  }

  private boolean intersects(int x1, int x2, int px1, int px2) {
    if ((px1 >= x1) && (px1 < x2)) {
      return true;
    }

    if ((px2 >= x1) && (px2 < x2)) {
      return true;
    }

    if ((x1 >= px1) && (x1 < px2)) {
      return true;
    }

    if ((x2 >= px1) && (x2 < px2)) {
      return true;
    }

    return false;
  }

  /**
   * Returns the index of the last occurrence of an integer in this list that is less than the specified integer.
   *
   * @param e  the desired integer.
   *
   * @return   the index of the last occurrence of the specified integer in this list;
   *           returns -1 if the integer is not found.
   */
  private Location lastIndexOfLT(int e) {
    int      len = locationList.size();
    Location loc;

    if ((lastFoundIndex > -1) && (lastFoundIndex < len)) {
      loc = locationList.get(lastFoundIndex);

      if (loc.getOffset() >= e) {
        len = lastFoundIndex;
      }
    }

    for (int i = len - 1; i >= 0; i--) {
      loc = locationList.get(i);

      if (loc.getOffset() < e) {
        lastFoundIndex = i;

        return loc;
      }
    }

    return null;
  }

  private void reHighlight() {
    Highlighter hiliter = component.getHighlighter();
    int         len     = locationList.size();
    Location    loc;

    for (int i = 0; i < len; i++) {
      loc = locationList.get(i);

      int start = loc.position.getOffset();
      int end   = start + loc.length;

      try {
        loc.highlight = hiliter.addHighlight(start, end, highlightAllPainter);
      } catch(BadLocationException e) {
        Platform.ignoreException(null, e);
      }
    }
  }

  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  static class AHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

    /**
     * Constructs a new instance
     *
     * @param color {@inheritDoc}
     */
    public AHighlightPainter(Color color) {
      super(color);
    }

    /**
     * {@inheritDoc}
     *
     * @param g {@inheritDoc}
     * @param offs0 {@inheritDoc}
     * @param offs1 {@inheritDoc}
     * @param bounds {@inheritDoc}
     * @param c {@inheritDoc}
     * @param view {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
      int start = c.getSelectionStart();
      int end   = c.getSelectionEnd();

      if ((offs0 == start) || (offs0 == end)) {
        return bounds;
      }

      return super.paintLayer(g, offs0, offs1, bounds, c, view);
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  static class Location {

    /**  */
    Object highlight;

    /**  */
    int length;

    /**  */
    Position position;

    /**
     * Constructs a new instance
     *
     * @param pos {@inheritDoc}
     * @param len {@inheritDoc}
     */
    Location(Position pos, int len) {
      position = pos;
      length   = len;
    }

    int getOffset() {
      return position.getOffset();
    }

    public boolean equals(Location loc) {
      return (loc != null) && (loc.getOffset() == getOffset());
    }
  }


  private class SegmentSequence extends Segment implements CharSequence {
    @Override
    public char charAt(int index) {
      if ((index < 0) || (index >= count)) {
        throw new StringIndexOutOfBoundsException(index);
      }

      return array[offset + index];
    }

    @Override
    public int length() {
      return count;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      if (start < 0) {
        throw new StringIndexOutOfBoundsException(start);
      }

      if (end > count) {
        throw new StringIndexOutOfBoundsException(end);
      }

      if (start > end) {
        throw new StringIndexOutOfBoundsException(end - start);
      }

      SegmentSequence segment = new SegmentSequence();

      segment.array  = this.array;
      segment.offset = this.offset + start;
      segment.count  = end - start;

      return segment;
    }
  }
}
