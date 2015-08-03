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

package com.appnativa.rare.viewer;

import android.text.Html;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import android.widget.EditText;

import com.appnativa.rare.platform.android.text.TextAttributes;
import com.appnativa.rare.platform.android.ui.view.EditTextEx;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.ui.text.iTextAttributes;
import com.appnativa.rare.widget.TextFieldWidget;

import java.util.HashMap;
import java.util.Map;

/**
 * A viewer the provides for the viewing of HTML, rich text, and plain documents
 *
 * @author Don DeCoteau
 */
public class DocumentPaneViewer extends aDocumentPaneViewer {
  protected static final HashMap<String, Integer> _typeMap     = TextFieldWidget._typeMap;
  protected static final HashMap<String, Integer> _actionMap   = TextFieldWidget._actionMap;
  private static CharacterStyle[]                 EMPTY_STYLES = new CharacterStyle[0];

  /**
   * Constructs a new instance
   */
  public DocumentPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param fv
   *          the widget's parent
   */
  public DocumentPaneViewer(iContainer parent) {
    super(parent);
  }

  public void handleCustomProperties(Map map) {
    final EditText et = (EditText) getDataView();
    String         s  = (String) map.get("imeActionLabel");
    String         name;
    int            n;

    if ((s != null) && s.startsWith("action")) {
      n = s.indexOf('=');

      if (n != -1) {
        name = s.substring(n + 1);
        s    = s.substring(0, n);
      } else {
        name = s.substring(6);
      }

      if (s.indexOf('|') == -1) {
        et.setImeActionLabel(name, _actionMap.get(s));
      } else {
        et.setImeActionLabel(name, Utils.getFlags(s, _actionMap));
      }
    }

    s = (String) map.get("inputType");

    if (s != null) {
      if (s.indexOf('|') == -1) {
        et.setInputType(_typeMap.get(s));
      } else {
        et.setInputType(Utils.getFlags(s, _typeMap));
      }
    }
  }

  /**
   * Creates a new HTML document
   */
  public void newHTMLDocument() {
    htmlDocument = true;
    setText("");
    modified = false;
  }

  /**
   * Creates a new plain text document
   */
  public void newPlainTextDocument() {
    htmlDocument = false;
    setText("");
    modified = false;
  }

  /**
   * Gets the attributes at the specified position
   *
   * @param pos the position to retrieve attributes for
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public iTextAttributes getAttributeSet(int pos, boolean paragraph) {
    return new TextAttributes(getSelectionStyles());
  }

  /**
   * Gets the attributes at the current cursor position
   *
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public iTextAttributes getAttributeSetAtCursor(boolean paragraph) {
    return new TextAttributes(getSelectionStyles());
  }

  /**
   * Gets the attributes at the current selection
   *
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public iTextAttributes getAttributeSetForSelection(boolean paragraph) {
    return new TextAttributes(getSelectionStyles());
  }

  public String getSelectionAsString() {
    EditTextEx e = getEditText();
    int        n = e.getSelectionStart();
    int        p = e.getSelectionEnd();

    if ((n == -1) || (p == -1) || (n == p)) {
      return null;
    }

    CharSequence cs = e.getEditableText().subSequence(n, p);

    if ((cs instanceof Spanned) && htmlDocument) {
      return Html.toHtml((Spanned) cs);
    }

    return cs.toString();
  }

  protected iPlatformTextEditor createEditor(DocumentPane cfg) {
    return getAppContext().getComponentCreator().getDocumentPane(this, cfg);
  }

  protected CharacterStyle[] getSelectionStyles() {
    EditTextEx e = getEditText();
    int        n = e.getSelectionStart();

    if (n == -1) {
      n = 0;
    }

    CharacterStyle[] a = null;

    if (n < e.length()) {
      int p = e.getSelectionEnd();

      a = e.getEditableText().getSpans(n, p, CharacterStyle.class);
    }

    return (a == null)
           ? EMPTY_STYLES
           : a;
  }

  private EditTextEx getEditText() {
    return (EditTextEx) getDataView();
  }
}
