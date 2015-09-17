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

import com.appnativa.rare.ui.listener.iTextChangeListener;

import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

/**
 * Guards a block against modification
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 * @author Don DeCoteau
 */
public class GuardFilter extends DocumentFilter {
  ArrayList                         positions        = new ArrayList();
  public GuardBlockHighlightPainter highlightPainter =
    new GuardBlockHighlightPainter(UIManager.getColor("Rare.background"));
  private boolean             allowBlockDelete;
  private iTextChangeListener changeListener;

  public void addGuardedBlock(Position start, Position end) throws BadLocationException {
    positions.add(new Position[] { start, end });
  }

  public void clear(JTextComponent comp) {
    positions.clear();
    highlightPainter.clear(comp);
  }

  @Override
  public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
    for (int i = 0; i < positions.size(); i++) {
      Position p[] = (Position[]) positions.get(i);

      if ((offset >= p[0].getOffset()) && (offset <= p[1].getOffset())) {
        return;
      }
    }

    Document doc = fb.getDocument();

    if (changeListener != null) {
      if (!changeListener.textChanging(doc, offset, offset, text)) {
        return;
      }
    }

    if (isOverwriteMode(doc)) {
      int n  = text.length();
      int ep = doc.getLength();

      if (offset + n > ep) {
        n = ep - offset;
      }

      fb.replace(offset, n, text, attr);
    } else {
      fb.insertString(offset, text, attr);
    }

    if (changeListener != null) {
      changeListener.textChanged(fb.getDocument());
    }
  }

  @Override
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
    Position[] p = getGuardedBlock(offset, length);

    if ((p != null) && allowBlockDelete) {
      int o1 = p[0].getOffset();
      int o2 = p[1].getOffset();
      int l  = o2 - o1;

      if (l < 0) {
        l  = -l;
        o1 = 02;
      }

      if (l > 0) {
        offset = o1;
        length = l;
      }
    }

    Document doc = fb.getDocument();

    if (changeListener != null) {
      if (!changeListener.textChanging(doc, offset, offset + length, "")) {
        return;
      }
    }

    if (changeListener != null) {
      changeListener.textChanged(fb.getDocument());
    }
  }

  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)
          throws BadLocationException {
    if (isGuarded(offset, length)) {
      return;
    }

    Document doc = fb.getDocument();

    if (isOverwriteMode(doc) && (length == 0)) {
      int     n  = text.length();
      Element e  = doc.getDefaultRootElement();
      int     ep = e.getElementIndex(offset);

      if (ep != -1) {
        e  = e.getElement(ep);
        ep = e.getEndOffset();

        if (offset + n >= ep) {    // do not replace the line feed
          n = ep - offset - 1;

          if (n < 0) {
            n = 0;
          }
        }

        length = n;
      }
    }

    if (changeListener != null) {
      if (!changeListener.textChanging(doc, offset, offset + length, text)) {
        return;
      }
    }

    fb.replace(offset, length, text, attr);

    if (changeListener != null) {
      changeListener.textChanged(fb.getDocument());
    }
  }

  /**
   * @param allowBlockDelete the allowBlockDelete to set
   */
  public void setAllowBlockDelete(boolean allowBlockDelete) {
    this.allowBlockDelete = allowBlockDelete;
  }

  public void setChangeListener(iTextChangeListener changeListener) {
    this.changeListener = changeListener;
  }

  public iTextChangeListener getChangeListener() {
    return changeListener;
  }

  /**
   * @return the allowBlockDelete
   */
  public boolean isAllowBlockDelete() {
    return allowBlockDelete;
  }

  boolean isOverwriteMode(Document doc) {
    Boolean b = (Boolean) doc.getProperty(TextEditor.OVERWRITE_PROPERTY);

    return (b == null)
           ? false
           : b.booleanValue();
  }

  private Position[] getGuardedBlock(int offset, int length) {
    final int len = positions.size();

    if (len == 0) {
      return null;
    }

    final ArrayList pos = positions;
    int             u1  = offset,
                    u2  = offset + length;

    for (int i = 0; i < len; i++) {
      Position p[] = (Position[]) pos.get(i);
      int      g1  = p[0].getOffset() - 1,
               g2  = p[1].getOffset() + 1;

      // u1, g1, u2, g2 --> guarded
      if ((g1 < u2) && (u2 < g2)) {
        return p;
      }

      // u1, g1, g2, u2 --> guarded
      if ((u1 < g1) && (g2 < u2)) {
        return p;
      }

      // u1/g1, g2, u2 --> guarded
      if ((u1 == g1) && (g2 < u2)) {
        return p;
      }

      // u1, g1, u2/g2 --> guarded
      if ((u1 < g1) && (u2 == g2)) {
        return p;
      }

      // g1, u1, g2, u2 --> guarded
      if ((g1 < u1) && (u1 < g2)) {
        return p;
      }

      // u1/g1, u2/g2 --> guarded
      if ((u1 == g1) && (u2 == g2)) {
        return p;
      }
    }

    return null;
  }

  private boolean isGuarded(int offset, int length) {
    return getGuardedBlock(offset, length) != null;
  }
}
