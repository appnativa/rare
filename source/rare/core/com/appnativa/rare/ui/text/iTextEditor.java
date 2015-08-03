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

package com.appnativa.rare.ui.text;

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.listener.iTextChangeListener;

public interface iTextEditor {
  public void deleteSelection();

  public void setText(CharSequence text);

  public int getCaretPosition();

  public String getSelectionString();

  public boolean hasSelection();

  void addActionListener(iActionListener listener);

  void addTextChangeListener(iTextChangeListener changeListener);

  void appendText(String text);

  void boldText();

  void decreaseIndent();

  void enlargeFont();

  void increaseIndent();

  void insertHTML(int pos, String html);

  void insertText(int pos, String text);

  void italicText();

  void pasteText(String text);

  void removeActionListener(iActionListener listener);

  void removeTextChangeListener(iTextChangeListener changeListener);

  void selectAll();

  void shrinkFont();

  void strikeThroughText();

  void subscriptText();

  void superscriptText();

  void underlineText();

  void setCaretPosition(int position);

  void setEditable(boolean editable);

  void setEmptyFieldColor(UIColor color);

  void setEmptyFieldFont(UIFont font);

  void setEmptyFieldText(String text);

  void setFollowHyperlinks(boolean follow);

  void setSelection(int beginIndex, int endIndex);

  void setText(String data, boolean htmlDocument);

  void setTextFontFamily(String family);

  void setTextFontSize(int size);

  void setTextForeground(UIColor fg);

  String getHtmlText();

  String getPlainText();

  int getSelectionEnd();

  int getSelectionStart();

  String getText(int start, int end);

  int getTextLength();

  boolean isEditable();

  boolean isFollowHyperlinks();
}
