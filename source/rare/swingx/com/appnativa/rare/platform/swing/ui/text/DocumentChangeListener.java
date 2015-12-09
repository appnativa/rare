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
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;

import java.lang.ref.WeakReference;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

public class DocumentChangeListener extends DocumentFilter implements DocumentListener {
  WeakReference<JTextComponent> source;
  private boolean               changeEventsDisabled;
  private static final String   EMPTY_STRING = "";

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
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
    if (fireChangeingEvent(offset, offset + length, EMPTY_STRING)) {
      super.remove(fb, offset, length);
    }
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
    if (changeEventsDisabled) {
      return;
    }

    JComponent c = source.get();

    if (c == null) {
      return;
    }

    final iPlatformComponent    pc        = Platform.findPlatformComponent(c);
    final iTextChangeListener[] listeners = c.getListeners(iTextChangeListener.class);

    if ((listeners != null) && (listeners.length > 0)) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          if (!changeEventsDisabled) {
            for (int i = listeners.length - 1; i >= 0; i -= 1) {
              listeners[i].textChanged(pc);
            }
          }
        }
      });
    }
  }

  protected boolean fireChangeingEvent(int startIndex, int endIndex, String replacementString) {
    if (changeEventsDisabled) {
      return true;
    }

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

  public void setChangeEventsEnabled(boolean enabled) {
    changeEventsDisabled = !enabled;
  }
}
