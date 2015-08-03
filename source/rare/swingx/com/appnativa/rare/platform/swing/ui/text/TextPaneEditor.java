/*
 * @(#)TextPaneEditor.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.io.IOException;
import java.io.StringReader;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.UIColor;

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
