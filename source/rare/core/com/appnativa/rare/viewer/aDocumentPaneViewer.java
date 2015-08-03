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

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.FormHelper;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.text.iPlatformTextEditor;
import com.appnativa.rare.ui.text.iTextAttributes;
import com.appnativa.rare.ui.text.iTextEditor;
import com.appnativa.rare.util.JSONHelper;
import com.appnativa.util.Streams;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.net.URL;

public abstract class aDocumentPaneViewer extends aPlatformViewer {
  protected boolean             htmlDocument;
  protected boolean             modified;
  protected iPlatformTextEditor textEditor;

  /**
   * Constructs a new instance
   */
  public aDocumentPaneViewer() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param parent
   *          the widget's parent
   */
  public aDocumentPaneViewer(iContainer parent) {
    super(parent);
    widgetType = WidgetType.DocumentPane;
  }

  /**
   * Appends text to the end of the current document
   *
   * @param text
   *          the text to append
   */
  public void appendText(String text) {
    textEditor.appendText(text);
  }

  /**
   * Toggles the bold state of the currently selected text or cursor position
   */
  public void boldText() {
    textEditor.boldText();
  }

  @Override
  public boolean canCut() {
    return super.canCut() && isEditable();
  }

  @Override
  public boolean canDelete() {
    return super.canDelete() && isEditable();
  }

  @Override
  public boolean canPaste() {
    return super.canPaste() && isEditable();
  }

  @Override
  public void clearContents() {
    super.clearContents();
    setText("", false);
  }

  @Override
  public void configure(Viewer vcfg) {
    configureEx((DocumentPane) vcfg);
    fireConfigureEvent(vcfg, iConstants.EVENT_CONFIGURE);
    handleDataURL(vcfg);
  }

  /**
   * Decreases the indent of the paragraph at the cursor position
   */
  public void decreaseIndent() {
    textEditor.decreaseIndent();
  }

  @Override
  public void dispose() {
    super.dispose();
    textEditor = null;
  }

  /**
   * Returns whether the editing view has it own editing toolbar
   * @return true is the editing view has it own editing toolbar; false otherwise
   */
  public boolean editorHasEditingToolbar() {
    return false;
  }

  /**
   * Enlarges the font of the selected text
   */
  public void enlargeFont() {
    textEditor.enlargeFont();
  }

  @Override
  public void handleActionLink(ActionLink link, boolean deferred) {
    if (isDisposed()) {
      return;
    }

    if (!deferred) {
      startedLoading();
    }

    try {
      sourceURL      = null;
      widgetDataLink = link;
      sourceURL      = link.getURL(this);
      activeLink     = link;

      String s = link.getContentType();

      if (s == null) {
        s = "";
      }

      htmlDocument = s.startsWith(iConstants.HTML_MIME_TYPE);

      final String data = link.getContentAsString();

      if (Platform.isUIThread()) {
        textEditor.setText(data, htmlDocument);
      } else {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            if (!isDisposed()) {
              textEditor.setText(data, htmlDocument);
            }
          }
        });
      }
    } catch(IOException ex) {
      throw ApplicationException.runtimeException(ex);
    } finally {
      activeLink = null;
      link.close();
    }
  }

  @Override
  public void handleReader(String mimeType, Reader reader) {
    try {
      String s = (mimeType == null)
                 ? ""
                 : mimeType;

      htmlDocument = s.startsWith(iConstants.HTML_MIME_TYPE);
      textEditor.setText(Streams.readerToString(reader), htmlDocument);
    } catch(Exception ex) {
      handleException(ex);
    }
  }

  /**
   * Increases the indent of the paragraph at the cursor position
   */
  public void increaseIndent() {
    textEditor.increaseIndent();
  }

  /**
   * Inserts a block of HTML at specified position in the document. The document
   * must be an HTML document
   *
   * @param pos
   *          the insert position
   * @param html
   *          the HTML to insert
   *
   */
  public void insertHTML(int pos, String html) {
    textEditor.insertHTML(pos, html);
  }

  /**
   * Inserts a block of text at specified position in the document.
   * @param pos
   *          the insert position
   * @param text
   *          the HTML to insert
   *
   */
  public void insertText(int pos, String text) {
    textEditor.insertText(pos, text);
  }

  /**
   * Toggles the italic state of the currently selected text or cursor position
   */
  public void italicText() {
    textEditor.italicText();
  }

  /**
   * Creates a new HTML document
   */
  public abstract void newHTMLDocument();

  /**
   * Creates a new plain text document
   */
  public abstract void newPlainTextDocument();

  /**
   * Inserts text or html at the current caret position
   *
   * @param text
   *          the text to insert
   */
  public void pasteText(String text) throws Exception {
    textEditor.pasteText(text);
  }

  /**
   * Selects the text from the starting index to the end of the string
   *
   * @param beginIndex
   *          the starting index
   */
  public void select(int beginIndex) {
    select(beginIndex, getTextLength());
  }

  /**
   * Selects the text between the specified start and end positions. The
   * character at the endIndex is not selected
   *
   * @param beginIndex
   *          the starting index
   * @param endIndex
   *          the ending index
   */
  public void select(int beginIndex, int endIndex) {
    textEditor.setSelection(beginIndex, endIndex);
  }

  @Override
  public void selectAll() {
    textEditor.selectAll();
  }

  /**
   * Shrinks the font of the selected text.
   */
  public void shrinkFont() {
    textEditor.shrinkFont();
  }

  /**
   * Toggles the strike-through state of the currently selected text or cursor
   * position.
   */
  public void strikeThroughText() {
    textEditor.strikeThroughText();
  }

  /**
   * Toggles the subscript state of the currently selected text or cursor
   * position.
   */
  public void subscriptText() {
    textEditor.subscriptText();
  }

  /**
   * Toggles the superscript state of the currently selected text or cursor
   * position.
   */
  public void superscriptText() {
    textEditor.superscriptText();
  }

  /**
   * Toggles the underline state of the currently selected text or cursor
   * position.
   */
  public void underlineText() {
    textEditor.underlineText();
  }

  @Override
  public void writeHTTPContent(boolean first, Writer writer, String boundary) {
    try {
      String name = getName();
      String text;
      String type;
      int    len = getTextLength();

      if ((name == null) ||!isEnabled() || (len == 0)) {
        return;
      }

      if (htmlDocument) {
        type = "text/html";
        text = getHTMLText();
      } else {
        type = "text/plain";
        text = getPlainText();
      }

      FormHelper.writeFieldHeader(first, writer, boundary, name, type);

      try {
        writer.write(text);
        writer.write("\r\n");
      } catch(IOException ex) {
        handleException(ex);
      }
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  @Override
  public boolean writeJSONValue(boolean first, Writer writer) {
    try {
      String name = getName();
      int    len  = getTextLength();

      if ((name == null) ||!isEnabled() || (len == 0)) {
        return false;
      }

      if (!first) {
        writer.write(",\n\t");
      }

      String text;

      if (htmlDocument) {
        text = getHTMLText();
      } else {
        text = getPlainText();
      }

      JSONHelper.encode(name, writer);
      writer.write(": \"");

      try {
        writer.write(text);
      } catch(IOException ex) {
        handleException(ex);
      }

      writer.write('\"');

      return true;
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  /**
   * Sets the position of the caret
   *
   * @param position
   *          the position of the caret
   */
  public void setCaretPosition(int position) {
    iTextEditor e = textEditor;

    e.setCaretPosition(position);
  }

  /**
   * Sets the document editable state.
   *
   * @param editable
   *          true to make the document editable; false otherwise
   */
  @Override
  public void setEditable(boolean editable) {
    iTextEditor e = textEditor;

    e.setEditable(editable);
  }

  /**
   * Sets whether hyperlinks should be followed
   *
   * @param follow
   *          true to follow; false otherwise
   */
  public void setFollowHyperlinks(boolean follow) {
    textEditor.setFollowHyperlinks(follow);
  }

  /**
   * Sets the text for the viewer
   *
   * @param text
   *          the HTML text to set
   */
  public void setHTML(String text) {
    htmlDocument = true;
    textEditor.setText(text, true);
  }

  public void setPlainText(String text) {
    setText(text, false);
  }

  /**
   * Sets the text for the viewer
   *
   * @param text
   *          the text to set
   */
  public void setText(String text) {
    htmlDocument = false;
    textEditor.setText(text, false);
  }

  /**
   * Sets the text for the viewer
   *
   * @param text
   *          the text to set
   * @param html
   *          true if the text is html; false otherwise
   */
  public void setText(String text, boolean html) {
    if (html) {
      setHTML(text);
    } else {
      setText(text);
    }
  }

  /**
   * Sets the text color of the currently selected text or establishes the color
   * for the current cursor position.
   *
   * @param c
   *          the color
   */
  public void setTextColor(UIColor c) {
    if (c != null) {
      textEditor.setTextForeground(c);
    }
  }

  /**
   * Sets the font of the currently selected text or establishes the font for
   * the current cursor position.
   *
   * @param family
   *          the font family
   */
  public void setTextFontFamily(String family) {
    if (family != null) {
      textEditor.setTextFontFamily(family);
    }
  }

  /**
   * Sets the font size of the currently selected text or establishes the font
   * size for the current cursor position.
   *
   * @param size
   *          the font size
   */
  public void setTextFontSize(int size) {
    if (size > 0) {
      textEditor.setTextFontSize(size);
    }
  }

  /**
   * Sets the document as being unmodified.
   */
  public void setUnModified() {}

  @Override
  public void setValue(Object value) {
    if (value instanceof URL) {
      setDataLink(new ActionLink((URL) value, null));

      return;
    }

    String s = "";

    if (value instanceof String) {
      s = (String) value;
    } else if (value != null) {
      s = toString(this, value, null);
    }

    setText(s);
  }

  /**
   * Sets whether the viewer is in word-wrap mode
   *
   * @param wrap
   *          true to enable word-wrap mode; false otherwise
   */
  public void setWordWrap(boolean wrap) {}

  /**
   * Gets the attributes at the specified position
   *
   * @param pos the position to retrieve attributes for
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public abstract iTextAttributes getAttributeSet(int pos, boolean paragraph);

  /**
   * Gets the attributes at the current cursor position
   *
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public abstract iTextAttributes getAttributeSetAtCursor(boolean paragraph);

  /**
   * Gets the attributes at the current selection
   *
   * @param paragraph true to retrieve paragraph attributes; false for character attributes
   *
   * @return the requested attributes
   */
  public abstract iTextAttributes getAttributeSetForSelection(boolean paragraph);

  /**
   * Gets the position of the caret
   *
   * @return the position of the caret
   */
  public int getCaretPosition() {
    iTextEditor e = textEditor;
    int         n = e.getSelectionStart();

    return (n == -1)
           ? 0
           : n;
  }

  /**
   * Gets the component's HTML text
   *
   * @return the component's HTMLtext
   */
  public String getHTMLText() {
    return textEditor.getHtmlText();
  }

  /**
   * Gets the component's text with all HTML markup removed
   *
   * @return the component's plain text
   */
  public String getPlainText() {
    return getText();
  }

  @Override
  public Object getSelection() {
    iTextEditor e = textEditor;
    int         n = e.getSelectionStart();
    int         p = e.getSelectionEnd();

    if ((n == -1) || (p == -1) || (n == p)) {
      return null;
    }

    return e.getText(n, p);
  }

  /**
   * Gets the component's text
   *
   * @return the component's text
   */
  public String getText() {
    return textEditor.getPlainText();
  }

  /**
   * Gets the length of the widget's text
   *
   * @return the length of the widget's text
   */
  public int getTextLength() {
    return textEditor.getTextLength();
  }

  @Override
  public Object getValue() {
    return getText();
  }

  @Override
  public String getValueAsString() {
    return getText();
  }

  /**
   * Returns a writer that can be used to append text to this viewer
   *
   * @return a writer that can be used to append text to this viewer
   */
  public Writer getWriter() {
    return new Writer() {
      @Override
      public void write(char[] cbuf, int off, int len) throws IOException {
        if (dataComponent != null) {
          iTextEditor e = textEditor;

          if (e != null) {
            synchronized(aDocumentPaneViewer.this) {
              e.appendText(new String(cbuf, off, len));
            }
          }
        }
      }
      @Override
      public void flush() throws IOException {}
      @Override
      public void close() throws IOException {}
    };
  }

  /**
   * Whether hyperlinks should be followed
   *
   * @return true to follow; false otherwise
   */
  public boolean isFollowHyperlinks() {
    return textEditor.isFollowHyperlinks();
  }

  @Override
  public boolean isMultipartContent() {
    return true;
  }

  /**
   * Returns whether the document is in overwrite mode
   *
   * @return true if the document is in overwrite mode; false otherwise
   */
  public boolean isOverwriteMode() {
    return false;
  }

  /**
   * Returns whether the document is a plain text document
   *
   * @return true if the document is a plain text document; false otherwise
   */
  public boolean isPlainTextDocument() {
    return !htmlDocument;
  }

  /**
   * Gets whether the viewer is in word-wrap mode
   *
   * @return true if viewer is in word-wrap mode; false otherwise
   */
  public boolean isWordWrap() {
    return true;
  }

  protected void configureEx(DocumentPane cfg) {
    selectAllAllowed = true;
    deletingAllowed  = true;
    pastingAllowed   = true;
    copyingAllowed   = true;
    textEditor       = createEditor(cfg);
    dataComponent    = textEditor.getComponent();
    dataComponent.setForeground(UIColor.BLACK);
    dataComponent.setBackground(UIColor.WHITE);
    formComponent = textEditor.getContainer();
    configure(cfg, true, true, true, true);

    if (!cfg.focusPainted.spot_hasValue() ||!cfg.focusPainted.booleanValue()) {
      formComponent.setFocusPainted(true);
    }

    iSpeechEnabler sp = getAppContext().getSpeechEnabler();

    if ((sp != null) && cfg.speechInputSupported.booleanValue()) {
      formComponent = (iPlatformComponent) sp.configure(this, formComponent, cfg);
    }

    if (!cfg.editable.booleanValue()) {
      setEditable(false);
    }
  }

  protected abstract iPlatformTextEditor createEditor(DocumentPane cfg);
}
