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
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.swing.ui.view.TextFieldView;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.text.iPlatformTextEditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class TextEditorComponent extends Container implements iPlatformTextEditor, ActionListener {
  JTextComponent textComponent;

  public TextEditorComponent(JTextComponent textComponent) {
    super(textComponent);
    this.textComponent = textComponent;
  }

  public TextEditorComponent(JScrollPane sp, JTextComponent textComponent) {
    super(sp);
    sp.getVerticalScrollBar().setFocusable(false);
    sp.getHorizontalScrollBar().setFocusable(false);
    this.textComponent = textComponent;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (listenerList != null) {
      com.appnativa.rare.ui.event.ActionEvent ae = new com.appnativa.rare.ui.event.ActionEvent(this);

      Utils.fireActionEvent(listenerList, ae);
    }
  }

  @Override
  public boolean isFocusOwner() {
    return (textComponent != null) && textComponent.isFocusOwner();
  }

  @Override
  public void addActionListener(iActionListener listener) {
    if (textComponent instanceof JTextField) {
      ((JTextField) textComponent).removeActionListener(this);
      ((JTextField) textComponent).addActionListener(this);
      getEventListenerList().add(iActionListener.class, listener);
    }
  }

  @Override
  public void appendText(String text) {
    Document doc = textComponent.getDocument();

    try {
      doc.insertString(doc.getLength(), text, null);
    } catch(BadLocationException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void boldText() {}

  @Override
  public void decreaseIndent() {}

  @Override
  public void deleteSelection() {
    deleteSelection(textComponent);
  }

  public static void deleteSelection(JTextComponent textComponent) {
    int start = textComponent.getSelectionStart();
    int end   = textComponent.getSelectionEnd();

    if (start != end) {
      if (start > end) {
        int n = start;

        start = end;
        end   = n;
      }

      try {
        textComponent.getDocument().remove(start, end - start);
      } catch(BadLocationException e) {
        UISoundHelper.beep();
        Platform.ignoreException(null, e);
      }
    }
  }

  @Override
  public void enlargeFont() {}

  @Override
  public void increaseIndent() {}

  @Override
  public void insertHTML(int pos, String html) {
    insertText(pos, html);
  }

  @Override
  public void insertText(int pos, String text) {
    try {
      textComponent.getDocument().insertString(pos, text, null);
    } catch(BadLocationException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void italicText() {}

  @Override
  public void pasteText(String text) {
    int start = textComponent.getSelectionStart();
    int end   = textComponent.getSelectionEnd();

    if (start > end) {
      int n = start;

      start = end;
      end   = n;
    }

    try {
      if (start != end) {
        textComponent.getDocument().remove(start, end - start);
      }

      textComponent.getDocument().insertString(start, text, null);
    } catch(BadLocationException e) {
      UISoundHelper.beep();
      Platform.ignoreException(null, e);
    }
  }

  @Override
  public void removeActionListener(iActionListener listener) {
    if (listenerList != null) {
      listenerList.remove(iActionListener.class, listener);
    }
  }

  @Override
  public void selectAll() {
    textComponent.selectAll();
  }

  @Override
  public void shrinkFont() {}

  @Override
  public void strikeThroughText() {}

  @Override
  public void subscriptText() {}

  @Override
  public void superscriptText() {}

  @Override
  public void underlineText() {}

  @Override
  public void setCaretPosition(int position) {
    textComponent.setCaretPosition(position);
  }

  @Override
  public void setEditable(boolean editable) {
    textComponent.setEditable(editable);
  }

  @Override
  public void setEmptyFieldColor(UIColor color) {
    if (textComponent instanceof TextFieldView) {
      ((TextFieldView) textComponent).setEmptyFieldColor(color);
    }
  }

  @Override
  public void setEmptyFieldFont(UIFont font) {
    if (textComponent instanceof TextFieldView) {
      ((TextFieldView) textComponent).setEmptyFieldFont(font);
    }
  }

  @Override
  public void setEmptyFieldText(String text) {
    if (textComponent instanceof TextFieldView) {
      ((TextFieldView) textComponent).setEmptyFieldText(text);
    }
  }

  @Override
  public void setFollowHyperlinks(boolean follow) {}

  @Override
  public void setSelection(int beginIndex, int endIndex) {
    textComponent.select(beginIndex, endIndex);
  }

  @Override
  public void setText(CharSequence text) {
    textComponent.setText((text == null)
                          ? ""
                          : text.toString());
  }

  @Override
  public void setText(String data, boolean htmlDocument) {
    textComponent.setText(data);
  }

  @Override
  public void setTextFontFamily(String family) {
    Font f = textComponent.getFont();

    if (!f.getFamily().equals(family)) {
      textComponent.setFont(new UIFont(family, f.getStyle(), f.getSize2D()));
    }
  }

  @Override
  public void setTextFontSize(int size) {
    Font f = textComponent.getFont();

    if (f.getSize() != size) {
      textComponent.setFont(new UIFont(f, size));
    }
  }

  @Override
  public void setTextForeground(UIColor c) {
    textComponent.setForeground(c);
  }

  @Override
  public int getCaretPosition() {
    return textComponent.getCaretPosition();
  }

  @Override
  public iPlatformComponent getComponent() {
    return Component.fromView(textComponent);
  }

  @Override
  public iPlatformComponent getContainer() {
    return this;
  }

  @Override
  public String getHtmlText() {
    return textComponent.getText();
  }

  @Override
  public String getPlainText() {
    return textComponent.getText();
  }

  @Override
  public int getSelectionEnd() {
    return textComponent.getSelectionEnd();
  }

  @Override
  public int getSelectionStart() {
    return textComponent.getSelectionStart();
  }

  @Override
  public String getSelectionString() {
    return textComponent.getSelectedText();
  }

  @Override
  public String getText(int start, int end) {
    try {
      return textComponent.getText(start, end - start);
    } catch(BadLocationException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public int getTextLength() {
    return textComponent.getDocument().getLength();
  }

  @Override
  public boolean hasSelection() {
    return getSelectionStart() != getSelectionEnd();
  }

  @Override
  public boolean isEditable() {
    return textComponent.isEditable();
  }

  @Override
  public boolean isFollowHyperlinks() {
    return false;
  }
}
