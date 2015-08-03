/*
 * @(#)DocumentChangeListener.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.lang.ref.WeakReference;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;

public class DocumentChangeListener extends DocumentFilter implements DocumentListener {
  WeakReference<JTextComponent> source;
  public DocumentChangeListener(JTextComponent source) {
    this.source = new WeakReference<JTextComponent>(source);
  }

  @Override
  public void changedUpdate(DocumentEvent e) {}

  @Override
  public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
          throws BadLocationException {
    if (fireChangeingEvent(offset, offset, string)) {
      super.insertString(fb, offset, string, attr);
    }
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    fireChangeEvent();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    fireChangeEvent();
  }

  @Override
  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr)
          throws BadLocationException {
    if (fireChangeingEvent(offset, offset + length, string)) {
      super.replace(fb, offset, length, string, attr);
    }
  }

  protected void fireChangeEvent() {
    JComponent c = source.get();

    if (c == null) {
      return;
    }

    iPlatformComponent    pc        = Platform.findPlatformComponent(c);
    iTextChangeListener[] listeners = c.getListeners(iTextChangeListener.class);

    for (int i = listeners.length - 1; i >= 0; i -= 1) {
      listeners[i].textChanged(pc);
    }
  }

  protected boolean fireChangeingEvent(int startIndex, int endIndex, String replacementString) {
    JComponent c = source.get();

    if (c == null) {
      return true;
    }

    iPlatformComponent    pc        = Platform.findPlatformComponent(c);
    iTextChangeListener[] listeners = c.getListeners(iTextChangeListener.class);

    for (int i = listeners.length - 1; i >= 0; i -= 1) {
      if (!listeners[i].textChanging(pc, startIndex, endIndex, replacementString)) {
        return false;
      }
    }

    return true;
  }
}
