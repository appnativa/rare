/*
 * @(#)Utilities.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import com.appnativa.util.MutableInteger;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class EditorUtils {
  public static int findCharacter(IDocument doc, int offset, int lineStart, char find, char pair)
          throws BadLocationException {
    char    qc      = 0;
    char    lc      = 0;
    boolean inQuote = false;
    int     depth   = 0;

    while(offset >= lineStart) {
      final char c = doc.getChar(offset);

      if ((c == '\'') || (c == '\"')) {
        if (!inQuote) {
          inQuote = true;
          qc      = c;
        } else if (qc == c) {
          inQuote = false;
        }

        lc = c;
        offset--;

        continue;
      }

      if (c == '\\') {
        if ((lc == qc) &&!inQuote) {
          inQuote = true;
          lc      = c;
          offset--;

          continue;
        }
      }

      if (!inQuote) {
        qc = 0;

        if (c == find) {
          if (depth < 1) {
            return offset;
          }

          depth--;
        } else if (c == pair) {
          depth++;
        }
      }

      lc = c;
      offset--;
    }

    return -1;
  }

  public static String findName(IDocument doc, int offset, MutableInteger rpos) throws BadLocationException {
    offset = skipPreviousWhiteSpace(doc, offset, 0);

    int end = offset + 1;

    while(offset > 0) {
      offset = findCharacter(doc, offset, 0, '{', '}');

      if (offset == -1) {
        break;
      }

      offset = skipPreviousWhiteSpace(doc, offset - 1, 0);

      if (doc.getChar(offset) == '{') {
        offset = skipPreviousWhiteSpace(doc, offset - 1, 0);
      }

      end    = offset + 1;
      offset = getWordStartOffset(doc, offset, 0);

      if (rpos != null) {
        rpos.set(offset);
      }

      return doc.get(offset, end - offset);
    }

    return null;
  }

  public static int skipPreviousWhiteSpace(IDocument doc, int offset, int lineStart) throws BadLocationException {
    while(offset > lineStart) {
      if (!Character.isWhitespace(doc.getChar(offset))) {
        return offset;
      }

      offset--;
    }

    return lineStart;
  }

  public static int getWordStartOffset(IDocument doc, int offset, int lineStart) throws BadLocationException {
    while(offset >= lineStart) {
      final char c = doc.getChar(offset);

      if (Character.isWhitespace(c)) {
        return offset + 1;
      }

      offset--;
    }

    return lineStart;
  }
}
