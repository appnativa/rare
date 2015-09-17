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

package com.appnativa.rare.platform.swing.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.platform.swing.ui.text.HTMLEditorKitEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.text.iPlatformTextEditor;

import javafx.embed.swing.JFXPanel;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;

import org.w3c.dom.Document;

import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.text.StyledDocument;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class HTMLTextEditor extends Component implements iPlatformTextEditor {
  HTMLEditor      htmlEditor;
  private boolean handleWaitCursor;

  public HTMLTextEditor() {
    super(new JFXPanel());
    javafx.application.Platform.runLater(new Runnable() {
      @Override
      public void run() {
        initFX();
      }
    });
  }

  @Override
  public void addActionListener(iActionListener listener) {}

  @Override
  public void addTextChangeListener(iTextChangeListener changeListener) {}

  @Override
  public void appendText(String text) {}

  @Override
  public void boldText() {}

  @Override
  public void decreaseIndent() {}

  @Override
  public void deleteSelection() {}

  @Override
  public void dispose() {
    htmlEditor = null;
  }

  @Override
  public void enlargeFont() {}

  @Override
  public void increaseIndent() {}

  @Override
  public void insertHTML(int pos, String html) {}

  @Override
  public void insertText(int pos, String text) {}

  @Override
  public void italicText() {}

  @Override
  public void pasteText(String text) {}

  @Override
  public void removeActionListener(iActionListener listener) {}

  @Override
  public void removeTextChangeListener(iTextChangeListener changeListener) {}

  @Override
  public void selectAll() {}

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
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);

    if (htmlEditor != null) {
      htmlEditor.resize(width, height);
    }
  }

  @Override
  public void setCaretPosition(int position) {}

  @Override
  public void setEditable(boolean editable) {}

  @Override
  public void setEmptyFieldColor(UIColor color) {}

  @Override
  public void setEmptyFieldFont(UIFont font) {}

  @Override
  public void setEmptyFieldText(String text) {}

  @Override
  public void setFollowHyperlinks(boolean follow) {}

  public void setHandleWaitCursor(boolean handleWaitCursor) {
    this.handleWaitCursor = handleWaitCursor;
  }

  @Override
  public void setSelection(int beginIndex, int endIndex) {}

  @Override
  public void setText(CharSequence text) {
    String s = (text == null)
               ? ""
               : text.toString();

    if (s.startsWith("<html>") || s.startsWith("<HTML>") || s.startsWith("<!DOCTYPE")) {
      setText(s, true);
    } else {
      setText(s, false);
    }
  }

  @Override
  public void setText(String data, boolean htmlDocument) {
    if (!htmlDocument) {
      data = "<html><body><pre>" + data + "</pre></body></html>";
    }

    htmlEditor.setHtmlText(data);
  }

  @Override
  public void setTextFontFamily(String family) {}

  @Override
  public void setTextFontSize(int size) {}

  @Override
  public void setTextForeground(UIColor fg) {}

  @Override
  public int getCaretPosition() {
    return 0;
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public iPlatformComponent getContainer() {
    return this;
  }

  public static String getHTML(Document doc) {
    if (doc == null) {
      return "";
    }

    StringWriter w = new StringWriter();

    try {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();

      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

      StreamResult sr = new StreamResult(w);

      transformer.transform(new DOMSource(doc), sr);

      return w.toString();
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  @Override
  public String getHtmlText() {
    return htmlEditor.getHtmlText();
  }

  @Override
  public String getPlainText() {
    String s = getHtmlText();

    if (s.length() > 0) {
      HTMLEditorKitEx kit = new HTMLEditorKitEx();
      StyledDocument  doc = (StyledDocument) kit.createDefaultDocument();

      try {
        kit.read(new StringReader(s), doc, 0);

        return doc.getText(0, doc.getLength());
      } catch(Exception ex) {
        Platform.ignoreException(null, ex);
      }
    }

    return s;
  }

  @Override
  public int getSelectionEnd() {
    return 0;
  }

  @Override
  public int getSelectionStart() {
    return 0;
  }

  @Override
  public String getSelectionString() {
    return null;
  }

  @Override
  public String getText(int start, int end) {
    String s = getPlainText();

    if (end >= s.length()) {
      return (start == 0)
             ? s
             : s.substring(start);
    }

    return s.substring(start, end + 1);
  }

  @Override
  public int getTextLength() {
    return this.getPlainText().length();
  }

  @Override
  public boolean hasSelection() {
    return false;
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isFollowHyperlinks() {
    return false;
  }

  public boolean isHandleWaitCursor() {
    return handleWaitCursor;
  }

  private void initFX() {
    Group group = new Group();
    Scene scene = new Scene(group);

    ((JFXPanel) view).setScene(scene);
    htmlEditor = new HTMLEditor();
    group.getChildren().add(htmlEditor);
  }
}
