/*
 * @(#)UndoManagerEx.java   2010-06-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author Don DeCoteau
 */
public class UndoManagerEx extends UndoManager {
  CompoundEditEx         compoundEdit;
  private int          modCount = 0;
  private boolean      ignoreEdits;
  private iModNotifier modNotifier;
  private iWidget      theWidget;

  /**
   * Creates a new instance of UndoManagerEx
   */
  public UndoManagerEx() {}

  /**
   * Creates a new instance of UndoManagerEx
   *
   * @param w the widget
   */
  public UndoManagerEx(iWidget w) {
    theWidget = w;
  }

  @Override
  public synchronized boolean addEdit(UndoableEdit e) {
    if (this.isIgnoreNewEdits()) {
      return true;
    }

    if (compoundEdit != null) {
      return compoundEdit.addEdit(e);
    }

    if (super.addEdit(e)) {
      aFocusedAction a = ActionHelper.getUndoAction();

      if (!a.isEnabled()) {
        a.update();
      }

      modCount++;

      if (modNotifier != null) {
        modNotifier.modified(this, modCount, null);
      }

      return true;
    }

    return false;
  }

  public void cancelCompoundEdit() {
    compoundEdit = null;
  }

  @Override
  public synchronized void discardAllEdits() {
    compoundEdit = null;
    super.discardAllEdits();
  }

  public synchronized void finishCompoundEdit() {
    CompoundEditEx e = compoundEdit;

    compoundEdit = null;

    if ((e != null) && e.size()>0) {
      e.end();
      addEdit(e);
    }
  }

  @Override
  public synchronized void redo() throws CannotRedoException {
    UndoableEdit edit = this.editToBeRedone();

    super.redo();

    aFocusedAction a = ActionHelper.getUndoAction();

    a.update();
    a = ActionHelper.getRedoAction();
    a.update();
    modCount++;

    if (modNotifier != null) {
      modNotifier.modified(this, modCount, edit);
    }
  }

  public void resetModCount() {
    modCount = 0;
  }

  public synchronized void startCompoundEdit() {
    if (compoundEdit == null) {
      compoundEdit = new CompoundEditEx();
    }
  }

  @Override
  public synchronized void undo() throws CannotUndoException {
    compoundEdit = null;

    UndoableEdit edit = this.editToBeUndone();

    super.undo();

    aFocusedAction a = ActionHelper.getUndoAction();

    a.update();
    a = ActionHelper.getRedoAction();
    a.update();
    modCount--;

    if (modNotifier != null) {
      modNotifier.modified(this, modCount, edit);
    }
  }

  public void setIgnoreNewEdits(boolean ignore) {
    this.ignoreEdits = ignore;
  }

  public void setModNotifier(iModNotifier mn) {
    modNotifier = mn;
  }

  public int getModCount() {
    return modCount;
  }

  public iWidget getWidget() {
    return theWidget;
  }

  public boolean isIgnoreNewEdits() {
    return ignoreEdits;
  }

  /**
   * Modification notifier interface
   *
   * @author     Don DeCoteau
   */
  public static interface iModNotifier {
    void modified(UndoManagerEx um, int modCount, UndoableEdit edit);
  }


  /**
   * CompoundEdit extension to support
   */
  public static class CompoundEditEx extends CompoundEdit {
    @Override
    public UndoableEdit lastEdit() {
      return super.lastEdit();
    }

    public int size() {
      return (edits == null)
             ? 0
             : edits.size();
    }
  }
}
