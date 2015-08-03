/*
 * @(#)ReconcilingStrategy.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;

public class ReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
  protected static final int START_BRACE = 1;
  protected static final int START_PF    = 2;
  protected char             cLastNLChar = ' ';

  /** number of newLines found by {@link #classifyTag()} */
  protected int cNewLines = 0;

  /**
   * next character position - used locally and only valid while
   * {@link #calculatePositions()} is in progress.
   */
  protected int cNextPos = 0;

  /** holds the calculated positions */
  protected final ArrayList<Position> fPositions = new ArrayList<Position>();

  /** The offset of the next character to be read */
  protected int fOffset;

  /** The end offset of the range to be scanned */
  protected int     fRangeEnd;
  private RMLEditor editor;
  private IDocument fDocument;

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#
   * initialReconcile()
   */
  public void initialReconcile() {
    fOffset   = 0;
    fRangeEnd = fDocument.getLength();
    calculatePositions();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse
   * .jface.text.IRegion)
   */
  public void reconcile(IRegion partition) {
    initialReconcile();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse
   * .jface.text.reconciler.DirtyRegion, org.eclipse.jface.text.IRegion)
   */
  public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
    initialReconcile();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org.
   * eclipse.jface.text.IDocument)
   */
  public void setDocument(IDocument document) {
    this.fDocument = document;
  }

  public void setEditor(RMLEditor editor) {
    this.editor = editor;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#
   * setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void setProgressMonitor(IProgressMonitor monitor) {
    // TODO Auto-generated method stub
  }

  /**
   * @return Returns the editor.
   */
  public RMLEditor getEditor() {
    return editor;
  }

  /**
   * uses {@link #fDocument}, {@link #fOffset} and {@link #fRangeEnd} to
   * calculate {@link #fPositions}. About syntax errors: this method is not a
   * validator, it is useful.
   */
  protected void calculatePositions() {
    fPositions.clear();
    cNextPos = fOffset;

    try {
      recursiveTokens(0);
    } catch(BadLocationException e) {
      e.printStackTrace();
    }
    // Collections.sort(fPositions, new RangeTokenComparator());

    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        editor.updateFoldingStructure(fPositions);
      }
    });
  }

  protected int eatToEndOfLine() throws BadLocationException {
    if (cNextPos >= fRangeEnd) {
      return 0;
    }

    char ch = fDocument.getChar(cNextPos++);

    // 1. eat all spaces and tabs
    while((cNextPos < fRangeEnd) && ((' ' == ch) || ('\t' == ch))) {
      ch = fDocument.getChar(cNextPos++);
    }

    if (cNextPos >= fRangeEnd) {
      cNextPos--;

      return 0;
    }

    // now ch is a new line or a non-whitespace
    if ('\n' == ch) {
      if (cNextPos < fRangeEnd) {
        ch = fDocument.getChar(cNextPos++);

        if ('\r' != ch) {
          cNextPos--;
        }
      } else {
        cNextPos--;
      }

      return 1;
    }

    if ('\r' == ch) {
      if (cNextPos < fRangeEnd) {
        ch = fDocument.getChar(cNextPos++);

        if ('\n' != ch) {
          cNextPos--;
        }
      } else {
        cNextPos--;
      }

      return 1;
    }

    return 0;
  }

  protected void emitPosition(int startOffset, int length) {
    fPositions.add(new Position(startOffset, length));
  }

  /**
   * emits tokens to {@link #fPositions}.
   *
   * @return number of newLines
   * @throws BadLocationException
   */
  protected int recursiveTokens(int depth) throws BadLocationException {
    int newLines = 0;

    while(cNextPos < fRangeEnd) {
      char ch = fDocument.getChar(cNextPos++);

      switch(ch) {
        case '{' :
          int startOffset   = cNextPos - 1;
          int startNewLines = newLines;

          newLines += recursiveTokens(depth + 1);

          if (newLines > startNewLines + 4) {
            emitPosition(startOffset, cNextPos - startOffset);
          }

          break;

        case '}' :
          return newLines;

        case '\n' :
        case '\r' :
          if ((ch == cLastNLChar) || (' ' == cLastNLChar)) {
            newLines++;
            cLastNLChar = ch;
          }

          break;

        default :
          break;
      }
    }

    return newLines;
  }
}
