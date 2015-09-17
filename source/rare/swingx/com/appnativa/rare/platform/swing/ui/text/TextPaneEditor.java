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

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.UIColor;

import java.io.IOException;
import java.io.StringReader;

public class TextPaneEditor extends TextEditorComponent {
  TextEditor textEditor;

  public TextPaneEditor(TextEditor te) {
    super(te.getScrollPane(), te);
    textEditor = te;
  }

  @Override
  public void boldText() {
    textEditor.boldText();
  }

  public void centerText() {
    textEditor.centerText();
  }

  @Override
  public void decreaseIndent() {
    textEditor.decreaseIndent();
  }

  @Override
  public void increaseIndent() {
    textEditor.increaseIndent();
  }

  @Override
  public void insertHTML(int pos, String html) {
    try {
      textEditor.insertHTML(pos, html);
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void italicText() {
    textEditor.italicText();
  }

  @Override
  public void shrinkFont() {
    textEditor.shrinkFont();
  }

  @Override
  public void strikeThroughText() {
    textEditor.strikeThroughText();
  }

  @Override
  public void subscriptText() {
    textEditor.subscriptText();
  }

  @Override
  public void superscriptText() {
    textEditor.superscriptText();
  }

  @Override
  public void underlineText() {
    textEditor.underlineText();
  }

  @Override
  public void setFollowHyperlinks(boolean follow) {
    textEditor.setFollowHyperlinks(follow);
  }

  @Override
  public void setText(String data, boolean htmlDocument) {
    String mime = htmlDocument
                  ? iConstants.HTML_MIME_TYPE
                  : iConstants.TEXT_MIME_TYPE;

    if (data == null) {
      data = "";
    }

    try {
      textEditor.setText(mime, new StringReader(data));
    } catch(IOException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void setTextFontFamily(String family) {
    textEditor.setFontFamily(family);
  }

  @Override
  public void setTextFontSize(int size) {
    textEditor.setFontSize(size);
  }

  @Override
  public void setTextForeground(UIColor fg) {
    textEditor.setTextColor(fg);
  }

  @Override
  public String getPlainText() {
    return textEditor.getPlainText();
  }

  @Override
  public boolean isFollowHyperlinks() {
    return textEditor.isFollowHyperlinks();
  }
}
