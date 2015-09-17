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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Rare;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.ScrollPaneEx;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.rare.ui.dnd.DropInformation;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.ui.painter.iComponentPainter;
import com.appnativa.rare.util.iTextSearcher;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.ByteArray;
import com.appnativa.util.UnescapingReader;
import com.appnativa.util.iURLResolver;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.plaf.UIResource;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.Caret;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.JTextComponent;
import javax.swing.text.LabelView;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.ParagraphView;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Don DeCoteau
 */
public class TextEditor extends JTextPane implements FocusListener, iURLResolver {

  /**  */
  static String                           OVERWRITE_PROPERTY = "com.appnativa.rare.swing.text.overwriteMode";
  private static final SimpleAttributeSet plainAttributeSet  = new SimpleAttributeSet();
  iComponentPainter                       componentPainter;
  Cursor                                  linkCursor;
  iURLResolver                            urlResolver;
  protected boolean                       overwriteMode = false;
  protected CursorShown                   cursorShown;
  protected HTMLDocumentLoader            documentLoader;
  protected DocumentSearcher              documentSearcher;
  protected JScrollPane                   scrollPane;
  protected boolean                       showOverwriteCursor;

  /**  */
  protected UndoManager      undoManager;
  private ActionEvent        actionEvent;
  private iHyperlinkListener hyperLinkListener;
  private boolean            isHtml;
  // private StyledTextAction   textAction;
  private volatile boolean wordWrap;
  private boolean          zoomingSupported;
  private DocumentFilter   documentFilter;

  /**
   *
   */
  public static enum CursorShown {
    WHEN_EDITABLE, ALWAYS, ALWAYS_FOR_NON_HTML, NEVER
  }

  /**
   * Creates a new instance of TextEditor
   * The configure method must be used in conjunction with this constructor
   *
   * @see #configure
   */
  protected TextEditor() {
    this.addFocusListener(this);
    this.setCaret(new ACaret());
    putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
  }

  /**
   * Creates a new instance of TextEditor
   *
   * @param pane the scroll pane
   */
  public TextEditor(JScrollPane pane) {
    this();
    configure(pane, null);
  }

  /**
   * Creates a new instance of TextEditor
   *
   * @param pane the scroll pane
   * @param qfp the quick find panel
   */
  public TextEditor(JScrollPane pane, JPanel qfp) {
    this();
    configure(pane, qfp);
  }

  /**
   * {@inheritDoc}
   *
   * @param text the text to append
   * @param a attributes for the text
   */
  public void appendText(String text, AttributeSet a) {
    Document doc = getDocument();

    try {
      doc.insertString(doc.getLength(), text, a);
    } catch(BadLocationException e) {
      throw new ApplicationException(e);
    }
  }

  public void boldText() {
    fireAction("Rare.action.text.bold");
  }

  public void callSuperPaintComponent(Graphics g, Shape shape) {
    super.paintComponent(g);
  }

  /**
   * Tells the undo manager to cancel the pending compound edit
   */
  public void cancelCompoundEdit() {
    if (undoManager instanceof UndoManagerEx) {
      ((UndoManagerEx) undoManager).cancelCompoundEdit();
    }
  }

  public void centerText() {
    fireAction("Rare.action.text.center");
  }

  /**
   * Clones the specified document
   *
   * @param doc the document to clone
   * @return a clone of the specified document
   * @throws Exception if the document could not be cloned
   */
  public static Document clone(Document doc) throws Exception {
    if (!(doc instanceof Serializable)) {
      throw new NotSerializableException((doc == null)
                                         ? "null"
                                         : doc.getClass().getName());
    }

    int                   size = doc.getLength() + 14905;
    ByteArrayOutputStream bo   = new ByteArrayOutputStream(size);
    ObjectOutputStream    out  = new ObjectOutputStream(bo);

    out.writeObject(doc);

    ByteArray         in   = new ByteArray(bo.toByteArray());
    ObjectInputStream ois  = new ObjectInputStream(in);
    Document          ndoc = (Document) ois.readObject();

    ois.close();

    return ndoc;
  }

  public void configure(JScrollPane pane, JPanel qfp) {
    cursorShown = CursorShown.ALWAYS_FOR_NON_HTML;
    actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "triggerEvent");
    scrollPane  = (pane == null)
                  ? new ScrollPaneEx()
                  : pane;
    scrollPane.setViewportView(this);
    scrollPane.setOpaque(false);
    super.setBorder(new UIEmptyBorder(2, 2, 2, 4, true));

    if (!Platform.getUIDefaults().getBoolean("Rare.TextEditor.userOriginalInsertBreakAction", false)) {
      KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
      Object    name  = getInputMap().get(enter);
      Action    a     = getActionMap().get(name);

      if ((a != null) &&!(a instanceof InsertBreakActionEx)) {
        getInputMap().put(enter, "rare-insert-break");
        getActionMap().put("rare-insert-break", new InsertBreakActionEx(a));
      }
    }
  }

  public Document copyDocument() {
    Document doc = getDocument();

    try {
      if (getContentType().startsWith(iConstants.TEXT_MIME_TYPE)) {
        if (doc instanceof DefaultStyledDocument) {
          doc = new DefaultStyledDocument();
          doc.insertString(0, getText(), null);

          return doc;
        }

        if (doc instanceof PlainDocument) {
          doc = new PlainDocument();
          doc.insertString(0, getText(), null);

          return doc;
        }
      }

      if (getEditorKit() instanceof HTMLEditorKitEx) {
        HTMLEditorKit kit = (HTMLEditorKit) getEditorKit();
        int           len = doc.getLength();
        StringWriter  w   = new StringWriter(len);

        kit.write(w, doc, 0, len);
        doc = kit.createDefaultDocument();

        StringReader reader = new StringReader(w.toString());

        kit.read(reader, doc, 0);

        return doc;
      }

      return clone(doc);
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  public void decreaseIndent() {
    fireAction("Rare.action.text.decreaseIndent");
  }

  public void enlargeFont() {
    fireAction("Rare.action.text.enlargeFont");
  }

  /**
   * Tells the undo manager that the compound edit has finished
   */
  public void finishCompoundEdit() {
    if (undoManager instanceof UndoManagerEx) {
      ((UndoManagerEx) undoManager).finishCompoundEdit();
    }
  }

  public void focusComponent() {
    this.requestFocus();
  }

  @Override
  public void focusGained(FocusEvent e) {
    if (!e.isTemporary()) {
      switch(cursorShown) {
        case ALWAYS :
          this.getCaret().setVisible(true);

          break;

        case WHEN_EDITABLE :
          this.getCaret().setVisible(isEditable());

          break;

        case ALWAYS_FOR_NON_HTML :
          if (isHtml) {
            this.getCaret().setVisible(isEditable());
          } else {
            this.getCaret().setVisible(true);
          }

          break;

        case NEVER :
          this.getCaret().setVisible(false);

          break;

        default :
          break;
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    if (!e.isTemporary()) {
      this.getCaret().setVisible(false);
    }
  }

  public static void importText(JTextComponent comp, final Transferable t, final DropInformation drop) {
    UIPoint p   = drop.getDropPoint();
    int     pos = 0;

    if (p == null) {
      pos = comp.getCaretPosition();
    } else {
      pos = comp.viewToModel(new Point((int) p.x, (int) p.y));

      if (pos < 0) {
        pos = 0;
      }

      if (pos > comp.getDocument().getLength()) {
        pos = comp.getDocument().getLength();
      }
    }

    boolean same = (drop.getSourceWidget() != null) && (drop.getSourceWidget().getDataComponent() == comp);

    if (same) {    // do nothing is src=dest and dest is within the selection
      int s = comp.getSelectionStart();
      int e = comp.getSelectionStart();

      if (s > e) {
        int n = e;

        e = s;
        s = n;
      }

      if ((pos >= s) && (pos <= e)) {
        return;
      }
    }

    try {
      InputContext ic = comp.getInputContext();

      if (ic != null) {
        ic.endComposition();
      }

      Reader    in  = DataFlavor.stringFlavor.getReaderForText(t);
      EditorKit kit = comp.getUI().getEditorKit(comp);

      kit.read(in, comp.getDocument(), pos);

      if (same && drop.isMoveAction()) {
        comp.replaceSelection("");
      }
    } catch(Exception ex) {
      Platform.ignoreException("DnD import", ex);
    }
  }

  public void increaseIndent() {
    fireAction("Rare.action.text.increaseIndent");
  }

  public void insertHTML(int location, String html) throws IOException, BadLocationException {
    // assumes this is already set to iConstants.HTML_MIME_TYPE type
    HTMLEditorKit kit = (HTMLEditorKit) this.getEditorKit();
    Document      doc = this.getDocument();

    if ((location == 0) && (doc.getLength() > 0)) {
      location = 1;
    }

    StringReader reader = new StringReader(html);

    kit.read(reader, doc, location);
  }

  @Override
  public void insertIcon(Icon icon) {
    if (!(getDocument() instanceof StyledDocument)) {
      Toolkit.getDefaultToolkit().beep();
    } else {
      StyledEditorKit     kit             = (StyledEditorKit) getEditorKit();
      MutableAttributeSet inputAttributes = kit.getInputAttributes();

      inputAttributes.removeAttributes(inputAttributes);
      StyleConstants.setIcon(inputAttributes, icon);
      replaceSelection(" ", false);
      inputAttributes.removeAttributes(inputAttributes);
    }
  }

  public void insertText(String text, AttributeSet a, int location) {
    Document doc = getDocument();

    try {
      doc.insertString(location, text, a);
    } catch(BadLocationException e) {
      throw new ApplicationException(e);
    }
  }

  public void italicText() {
    fireAction("Rare.action.text.italic");
  }

  public void justifyText() {
    fireAction("Rare.action.text.justify");
  }

  public void leftAlignText() {
    fireAction("Rare.action.text.alignLeft");
  }

  public void newDocument() {
    setEditorKit(new StyledEditorKit());
    finishDocLoad(getDocument(), false);
  }

  public void newHTMLDocument() {
    setEditorKit(new HTMLEditorKitEx(null));
    setHTMLFont();

    HTMLDocument doc = (HTMLDocument) getDocument();

    doc.setPreservesUnknownTags(false);
    finishDocLoad(doc, false);
  }

  public void newPlainTextDocument() {
    Font f = Platform.getUIDefaults().getFont("Rare.font.plaintext");

    if (f != null) {
      this.setFont(f);
    }

    setEditorKit(new StyledEditorKit());
    this.setContentType(iConstants.TEXT_MIME_TYPE);
    finishDocLoad(getDocument(), false);
  }

  public void newRTFDocument() {
    this.setContentType(iConstants.RTF_MIME_TYPE);

    Font f = Platform.getUIDefaults().getFont("Rare.html.font");

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Rare.richtext.font");
    }

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Label.font");
    }

    setFont(f);
    finishDocLoad(getDocument(), false);
  }

  public void refresh() {
    EditorKit kit = getEditorKit();
    Document  doc = this.getDocument();

    this.setEditorKit(kit);
    this.setDocument(doc);
  }

  public void rightAlignText() {
    fireAction("Rare.action.text.alignRight");
  }

  public void setupPrintingCopy(TextEditor copy) {
    copy.setWordWrap(true);
    copy.setURLResolver(urlResolver);
    copy.setEditorKit(this.getEditorKit());

    Document doc;

    try {
      doc = copyDocument();
    } catch(Exception e) {
      Platform.ignoreException("Couldn't copy document for printing", e);
      doc = getDocument();
    }

    copy.setDocument(doc);
  }

  public void showOverwriteCursor(boolean show) {
    showOverwriteCursor = show;
  }

  public void shrinkFont() {
    fireAction("Rare.action.text.shrinkFont");
  }

  /**
   * Tells the undo manager that all future edits should be compounded (works with UndoManagerEx classes only).
   */
  public void startCompoundEdit() {
    if (undoManager instanceof UndoManagerEx) {
      ((UndoManagerEx) undoManager).startCompoundEdit();
    }
  }

  public void strikeThroughText() {
    fireAction("Rare.action.text.strikeThrough");
  }

  public void subscriptText() {
    if (!(getDocument() instanceof StyledDocument)) {
      UISoundHelper.errorSound();
    } else {
      new StyledTextAction().subscript();
    }
  }

  public void superscriptText() {
    if (!(getDocument() instanceof StyledDocument)) {
      UISoundHelper.errorSound();
    } else {
      new StyledTextAction().superscript();
    }
  }

  public void underlineText() {
    fireAction("Rare.action.text.underline");
  }

  @Override
  public void updateUI() {
    super.updateUI();

    Font f = Platform.getUIDefaults().getFont("Rare.font.plaintext");

    if ((f != null) && (getFont() instanceof UIResource)
        && iConstants.TEXT_MIME_TYPE.equalsIgnoreCase(getContentType())) {
      this.setFont(f);
    }
  }

  @Override
  public void setBackground(Color bg) {
    super.setBackground(bg);

    if (scrollPane != null) {
      scrollPane.setBackground(bg);
    }
  }

  public void setComponentPainter(iComponentPainter cp) {
    this.componentPainter = cp;
  }

  public void setCursorShown(CursorShown shown) {
    cursorShown = shown;
  }

  @Override
  public void setEditable(boolean editable) {
    super.setEditable(editable);
  }

  public void setEditorKit(HTMLEditorKit kit) {
    isHtml = true;
    super.setEditorKit(kit);
  }

  @Override
  public void setFont(Font f) {
    super.setFont(f);
  }

  public void setFontFamily(String family) {
    if (!(getDocument() instanceof StyledDocument)) {
      UISoundHelper.errorSound();
    } else {
      new FontFamilyAction("ffa", family).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, family));
    }
  }

  public void setFontSize(float size) {
    if (!(getDocument() instanceof StyledDocument)) {
      UISoundHelper.errorSound();
    } else {
      new FontSizeAction("fsa",
                         (int) size).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                           String.valueOf((int) size)));
    }
  }

  public void setHyperLinkListener(iHyperlinkListener l) {
    hyperLinkListener = l;
  }

  @Override
  public void setOpaque(boolean opaque) {}

  public void setOverwriteMode(boolean overwriteMode) {
    this.overwriteMode = overwriteMode;
    getDocument().putProperty(OVERWRITE_PROPERTY, Boolean.valueOf(overwriteMode));
  }

  public void setText(iURLConnection conn) throws IOException {
    setText(conn, false, false);
  }

  @Override
  public void setText(String text) {
    super.setText(text);
    this.setCaretPosition(0);
    ((AbstractDocument) this.getDocument()).setDocumentFilter(new GuardFilter());

    if (undoManager != null) {
      undoManager.discardAllEdits();
      this.getDocument().addUndoableEditListener(undoManager);
    }
  }

  public void setText(URL url) throws IOException {
    setText(url.openConnection(), false, false);
  }

  public void setText(String mimeType, Reader reader) throws IOException {
    if ((mimeType != null) && mimeType.startsWith(iConstants.HTML_MIME_TYPE)) {
      loadHTMLDocument(reader);
    } else {
      loadDocument(mimeType, reader);
    }
  }

  public void setText(iURLConnection conn, boolean unescape, boolean unicodeOnly) throws IOException {
    String ct = conn.getContentType();

    if ((ct != null) && ct.startsWith(iConstants.HTML_MIME_TYPE)) {
      loadHTMLDocument(conn);
    } else {
      Reader r = conn.getReader();

      if (unescape &&!(r instanceof UnescapingReader)) {
        r = new UnescapingReader(r, unicodeOnly);
      }

      loadDocument(ct, r);
    }
  }

  public void setText(URLConnection conn, boolean unescape, boolean unicodeOnly) throws IOException {
    String ct = conn.getContentType();

    if ((ct != null) && ct.startsWith(iConstants.HTML_MIME_TYPE)) {
      loadHTMLDocument(conn);
    } else {
      Reader r = Rare.getReader(conn);

      if (unescape) {
        r = new UnescapingReader(r, unicodeOnly);
      }

      loadDocument(ct, r);
    }
  }

  public void setTextColor(Color c) {
    if (!(getDocument() instanceof StyledDocument)) {
      UISoundHelper.errorSound();
    } else {
      new ForegroundAction("tca",
                           c).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "setForeground"));
    }
  }

  public void setURLResolver(iURLResolver ur) {
    urlResolver = ur;
  }

  public void setUndoManager(UndoManager undoManager) {
    if (this.undoManager != null) {
      getDocument().removeUndoableEditListener(this.undoManager);
    }

    this.undoManager = undoManager;

    if (undoManager != null) {
      this.getDocument().addUndoableEditListener(undoManager);
    }
  }

  public void setWordWrap(boolean wordWrap) {
    if (!isHTML()) {
      this.wordWrap = wordWrap;
      refresh();
    }
  }

  public void setZoomingSupported(boolean zoomingSupported) {
    this.zoomingSupported = zoomingSupported;
  }

  @Override
  public Object getApplicationContext() {
    return (urlResolver == null)
           ? null
           : urlResolver.getApplicationContext();
  }

  public AttributeSet getAttributeSet(int pos, boolean paragraph) {
    Document d = getDocument();

    if (!(d instanceof StyledDocument)) {
      return plainAttributeSet;
    }

    StyledDocument doc = (StyledDocument) d;
    Element        el;

    if (paragraph) {
      el = doc.getParagraphElement(pos);
    } else {
      el = doc.getCharacterElement(pos);
    }

    return el.getAttributes();
  }

  public AttributeSet getAttributeSetAtCursor(boolean paragraph) {
    return getAttributeSet(getCaretPosition(), paragraph);
  }

  public AttributeSet getAttributeSetForSelection(boolean paragraph) {
    Document d = getDocument();

    if (!(d instanceof StyledDocument)) {
      return plainAttributeSet;
    }

    StyledDocument doc = (StyledDocument) d;
    Element        el;

    if (paragraph) {
      el = doc.getParagraphElement(getSelectionStart());
    } else {
      el = doc.getCharacterElement(getSelectionStart());
    }

    return el.getAttributes();
  }

  @Override
  public URL getBaseURL() {
    return (urlResolver == null)
           ? null
           : urlResolver.getBaseURL();
  }

  public JComponent getComponent() {
    return this;
  }

  @Override
  public URLConnection getConnection(String file) throws IOException {
    return getURL(file).openConnection();
  }

  @Override
  public Color getForeground() {
    if (!isEnabled()) {
      Color c = (Color) getClientProperty(iConstants.RARE_DISABLEDCOLOR_PROPERTY);

      if (c != null) {
        return c;
      }
    }

    return super.getForeground();
  }

  public iHyperlinkListener getHyperLinkListener() {
    return hyperLinkListener;
  }

  /**
   * Gets the preferred non-wrapping size
   * @return the preferred non-wrapping size
   */
  public Dimension getNoWrapPreferredSize() {
    do {
      Font f = getFont();

      if (f == null) {
        break;
      }

      Segment  seg = new Segment();
      Document doc = getDocument();
      int      len = doc.getLength();

      if (len == 0) {
        break;
      }

      try {
        doc.getText(0, len, seg);
      } catch(BadLocationException ex) {
        break;    // should never happen
      }

      Insets      insets = getInsets();
      FontMetrics fm     = this.getFontMetrics(f);
      int         w      = 0;
      int         n      = 0;
      char        a[]    = seg.array;
      int         off    = seg.offset;
      char        c;
      int         lines = 1;

      for (int i = 0; i < len; i++) {
        c = a[i + off];

        if ((c == '\r') || (c == '\n')) {
          if (n > w) {
            w = n;
          }

          if (n != 0) {
            lines++;
          }

          n = 0;

          continue;
        } else if (c == '\t') {
          n += (fm.charWidth(' ') * 8);
        } else {
          n += fm.charWidth(c);
        }
      }

      if (n > w) {
        w = n;
      }

      w += insets.left + insets.right;
      n = insets.top + insets.bottom + ((fm.getHeight() + fm.getDescent()) * lines);

      return new Dimension(w, n);
    } while(false);

    return super.getPreferredSize();
  }

  public String getPlainText() {
    Document d = getDocument();

    if (d instanceof StyledDocument) {
      StyledDocument doc = (StyledDocument) d;

      try {
        return doc.getText(0, doc.getLength());
      } catch(BadLocationException ex) {}
    }

    return getText();
  }

  @Override
  public Reader getReader(String file) throws IOException {
    return AppContext.getContext().getReader(getURL(file));
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    if ((wordWrap || (getDocument().getLength() == 0)) &&!(getEditorKit() instanceof HTMLEditorKit)) {
      return true;
    }

    if (SwingHelper.isHorizontalScrollBarHiddenAlways(scrollPane)) {
      return true;
    }

    return super.getScrollableTracksViewportWidth();
  }

  @Override
  public InputStream getStream(String file) throws IOException {
    return getURL(file).openConnection().getInputStream();
  }

  public JEditorPane getTextComponent() {
    return this;
  }

  public iTextSearcher getTextSearcher() {
    if (documentSearcher == null) {
      documentSearcher = new DocumentSearcher(this);
    }

    return documentSearcher;
  }

  @Override
  public URL getURL(String file) throws MalformedURLException {
    return (urlResolver == null)
           ? new URL(file)
           : urlResolver.getURL(file);
  }

  public UndoManager getUndoManager() {
    return undoManager;
  }

  public boolean hasValue() {
    return getDocument().getLength() > 0;
  }

  public boolean isHTML() {
    return getDocument() instanceof HTMLDocument;
  }

  public boolean isHilightAllSupported() {
    return true;
  }

  @Override
  public boolean isOpaque() {
    return false;
  }

  public static boolean isOverText(JTextComponent comp, Point p) {
    int pos = comp.viewToModel(p);

    if (pos < 0) {
      return false;
    }

    try {
      Rectangle r = comp.modelToView(pos);

      r.x     -= 4;
      r.y     -= 4;
      r.width += 8;

      return r.contains(p);
    } catch(BadLocationException ex) {}

    return false;
  }

  public boolean isOverwriteMode() {
    return overwriteMode;
  }

  public boolean isWordWrap() {
    return wordWrap;
  }

  public boolean isZoomingSupported() {
    return zoomingSupported;
  }

  @Override
  protected EditorKit createDefaultEditorKit() {
    return new StyledEditorKitEx();
  }

  protected void loadDocument(String mime, Reader r) throws IOException {
    Document doc;

    if ((mime == null)
        || (!mime.startsWith(iConstants.RICHTEXT_MIME_TYPE) &&!mime.startsWith(iConstants.RTF_MIME_TYPE)
            &&!mime.startsWith(iConstants.XML_MIME_TYPE))) {
      mime = iConstants.TEXT_MIME_TYPE;

      Font f = Platform.getUIDefaults().getFont("Rare.font.plaintext");

      if ((f != null) && (getFont() instanceof UIResource)) {
        this.setFont(f);
      }
    } else {
      setHTMLFont();
      this.setContentType(mime);
    }

    if (mime == null) {
      mime = "text/plain";
    }

    this.setContentType(mime);
    doc = this.getEditorKit().createDefaultDocument();

    try {
      this.getEditorKit().read(r, doc, 0);
    } catch(BadLocationException e) {
      Platform.ignoreException(null, e);
    }

    finishDocLoad(doc, true);
  }

  protected void loadHTMLDocument(iURLConnection conn) throws IOException {
    setEditorKit(new HTMLEditorKitEx(this));
    setHTMLFont();

    if (documentLoader == null) {
      documentLoader = new HTMLDocumentLoader(this);
    }

    finishDocLoad(documentLoader.loadDocument(conn), true);
  }

  protected void loadHTMLDocument(Reader r) throws IOException {
    setEditorKit(new HTMLEditorKitEx(null));
    setHTMLFont();

    if (documentLoader == null) {
      documentLoader = new HTMLDocumentLoader(this);
    }

    finishDocLoad(documentLoader.loadDocument(r), true);
  }

  protected void loadHTMLDocument(URLConnection conn) throws IOException {
    setEditorKit(new HTMLEditorKitEx(this));
    setHTMLFont();

    if (documentLoader == null) {
      documentLoader = new HTMLDocumentLoader(this);
    }

    finishDocLoad(documentLoader.loadDocument(conn), true);
  }

  private void finishDocLoad(Document doc, boolean set) {
    if (set) {
      this.setDocument(doc);
    }

    if ((documentFilter != null) && (doc instanceof AbstractDocument)) {
      ((AbstractDocument) doc).setDocumentFilter(documentFilter);
    }

    if (undoManager != null) {
      undoManager.discardAllEdits();
      this.getDocument().addUndoableEditListener(undoManager);
    }

    ((AbstractDocument) doc).setDocumentFilter(new GuardFilter());
    this.setCaretPosition(0);

    if (getEditorKit() instanceof HTMLEditorKit) {
      HTMLEditorKit      kit = (HTMLEditorKit) this.getEditorKit();
      iHyperlinkListener l   = hyperLinkListener;

      if (l != null) {
        if (linkCursor != null) {
          kit.setLinkCursor(linkCursor);
        }
      } else {
        if (linkCursor == null) {
          linkCursor = kit.getLinkCursor();
        }

        kit.setLinkCursor(kit.getDefaultCursor());
      }
    }
  }

  private void fireAction(String name) {
    if (!(getDocument() instanceof StyledDocument)) {
      Toolkit.getDefaultToolkit().beep();
    } else {
      iWidget w = Platform.findWidgetForComponent(this);
      Action  a = w.getAppContext().getAction(name);

      if (a != null) {
        a.actionPerformed(actionEvent);
      }
    }
  }

  private void replaceSelection(String content, boolean checkEditable) {
    Document doc = getDocument();

    if ((checkEditable &&!isEditable()) ||!(doc instanceof StyledDocument)) {
      UISoundHelper.errorSound();

      return;
    }

    try {
      Caret        caret = getCaret();
      int          p0    = Math.min(caret.getDot(), caret.getMark());
      int          p1    = Math.max(caret.getDot(), caret.getMark());
      AttributeSet attr  = ((StyledEditorKit) getEditorKit()).getInputAttributes().copyAttributes();

      if (doc instanceof AbstractDocument) {
        ((AbstractDocument) doc).replace(p0, p1 - p0, content, attr);
      } else {
        if (p0 != p1) {
          doc.remove(p0, p1 - p0);
        }

        if ((content != null) && (content.length() > 0)) {
          doc.insertString(p0, content, attr);
        }
      }
    } catch(BadLocationException e) {
      UISoundHelper.errorSound();
    }
  }

  private Font setHTMLFont() {
    Font f = Platform.getUIDefaults().getFont("Rare.html.font");

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Rare.richtext.font");
    }

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Label.font");
    }

    if (f == null) {
      f = getFont();
    }

    if ((f != null) && (f.getStyle() != Font.PLAIN)) {
      f = f.deriveFont(Font.PLAIN);
    }

    if (!(getFont() instanceof UIResource)) {
      return getFont();
    }

    setFont(f);

    return f;
  }

  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  class ACaret extends DefaultCaret {

    /**
     * Constructs a new instance
     */
    ACaret() {
      int    blinkRate = 500;
      Object o         = Platform.getUIDefaults().get("TextArea.caretBlinkRate");

      if ((o != null) && (o instanceof Integer)) {
        Integer rate = (Integer) o;

        blinkRate = rate.intValue();
      }

      setBlinkRate(blinkRate);
    }

    @Override
    public void paint(Graphics g) {
      JTextComponent comp = getComponent();
      int            dot  = getDot();
      Rectangle      r    = null;
      char           c;

      try {
        r = comp.modelToView(dot);

        if (r == null) {
          return;
        }

        c = comp.getText(dot, 1).charAt(0);
      } catch(BadLocationException e) {
        return;
      }

      // erase provious caret
      if ((x != r.x) || (y != r.y)) {
        repaint();
        x      = r.x;
        y      = r.y;
        height = r.height;
      }

      g.setColor(comp.getCaretColor());
      g.setXORMode(comp.getBackground());

      if (overwriteMode && showOverwriteCursor) {
        width = g.getFontMetrics().charWidth(c);

        if ((c == '\t') || (c == '\n')) {
          width = g.getFontMetrics().charWidth(' ');
        }
      } else {
        width = 2;
      }

      if (isVisible()) {
        g.fillRect(r.x, r.y, width, r.height);
      }
    }
  }


  static class NoWrapParagraphView extends ParagraphView {
    public NoWrapParagraphView(Element elem) {
      super(elem);
    }

    @Override
    public void layout(int width, int height) {
      super.layout(Short.MAX_VALUE, height);
    }

    @Override
    public float getMinimumSpan(int axis) {
      return super.getPreferredSpan(axis);
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  class StyledTextAction extends StyledEditorKit.StyledTextAction {

    /**
     * Constructs a new instance
     */
    public StyledTextAction() {
      super("temp");
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    public void subscript() {
      JEditorPane        editor = TextEditor.this;
      StyledDocument     doc    = getStyledDocument(editor);
      Element            el     = doc.getCharacterElement(editor.getSelectionStart());
      boolean            flag   = StyleConstants.isSubscript(el.getAttributes());
      SimpleAttributeSet atts   = new SimpleAttributeSet();

      StyleConstants.setSubscript(atts, !flag);
      setCharacterAttributes(editor, atts, false);
    }

    public void superscript() {
      JEditorPane        editor = TextEditor.this;
      StyledDocument     doc    = getStyledDocument(editor);
      Element            el     = doc.getCharacterElement(editor.getSelectionStart());
      boolean            flag   = StyleConstants.isSuperscript(el.getAttributes());
      SimpleAttributeSet atts   = new SimpleAttributeSet();

      StyleConstants.setSuperscript(atts, !flag);
      setCharacterAttributes(editor, atts, false);
    }

    public void setColor(Color fg) {
      JEditorPane editor = TextEditor.this;

      if (fg != null) {
        MutableAttributeSet attr = new SimpleAttributeSet();

        StyleConstants.setForeground(attr, fg);
        setCharacterAttributes(editor, attr, false);
      } else {
        UISoundHelper.errorSound();
      }
    }

    public void setFont(Font f, boolean newdoc) {
      JEditorPane         editor = TextEditor.this;
      MutableAttributeSet attr   = new SimpleAttributeSet();

      StyleConstants.setFontFamily(attr, f.getFamily());
      StyleConstants.setFontSize(attr, f.getSize());

      if (newdoc) {
        setParagraphAttributes(editor, attr, true);
      } else {
        setCharacterAttributes(editor, attr, false);
      }
    }
  }


  class WrapColumnFactory implements ViewFactory {
    @Override
    public View create(Element elem) {
      String kind = elem.getName();

      if (kind != null) {
        if (kind.equals(AbstractDocument.ContentElementName)) {
          return new LabelView(elem);
        } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
          return wordWrap
                 ? new ParagraphView(elem)
                 : new NoWrapParagraphView(elem);
        } else if (kind.equals(AbstractDocument.SectionElementName)) {
          return new BoxView(elem, View.Y_AXIS);
        } else if (kind.equals(StyleConstants.ComponentElementName)) {
          return new ComponentView(elem);
        } else if (kind.equals(StyleConstants.IconElementName)) {
          return new IconView(elem);
        }
      }

      // default to text display
      return new LabelView(elem);
    }
  }


  private static class InsertBreakActionEx extends InsertBreakAction {
    Action originalAction;

    public InsertBreakActionEx(Action orig) {
      originalAction = orig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        JTextComponent c = getFocusedComponent();

        if ((c != null) && (c.getDocument() instanceof HTMLDocument)) {
          HTMLDocument doc  = (HTMLDocument) c.getDocument();
          TextEditor   te   = (TextEditor) c;
          Element      elem = doc.getParagraphElement(c.getCaretPosition());

          if (HTMLListAction.getListItem(elem) == HTML.Tag.LI) {
            HTMLListAction.insertLI(te, doc, elem);

            return;
          }
        }
      } catch(Exception ignore) {}

      originalAction.actionPerformed(e);
    }
  }


  private class StyledEditorKitEx extends StyledEditorKit {
    ViewFactory defaultFactory = new WrapColumnFactory();

    @Override
    public ViewFactory getViewFactory() {
      return defaultFactory;
    }
  }


  public boolean isFollowHyperlinks() {
    return hyperLinkListener == null;
  }

  public void setFollowHyperlinks(boolean follow) {}

  public DocumentFilter getDocumentFilter() {
    return documentFilter;
  }

  public void setDocumentFilter(DocumentFilter documentFilter) {
    this.documentFilter = documentFilter;
  }
}
