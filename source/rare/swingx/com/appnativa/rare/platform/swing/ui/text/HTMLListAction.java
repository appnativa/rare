/*
 * @(#)HTMLListAction.java   2010-06-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.appnativa.rare.platform.swing.ui.text.HTMLEditorKitEx.HTMLWriterEx;

/**
 * A class for managing HTML lists
 *
 * @author Don DeCoteau
 */
public class HTMLListAction extends HTMLEditorKit.InsertHTMLTextAction {
  private String   attributes;
  private HTML.Tag listType;

  /**
   * Creates a new instance
   *
   * @param name the name
   * @param listType type of the list (OL or UL)
   */
  public HTMLListAction(String name, HTML.Tag listType) {
    super(name, "", listType, HTML.Tag.LI);
    this.listType = listType;
  }

  /**
   * Creates a new instance
   *
   * @param name the name
   * @param listType type of the list (OL or UL)
   * @param attributes attributes for the list (e.g.
   */
  public HTMLListAction(String name, HTML.Tag listType, String attributes) {
    super(name, "", listType, HTML.Tag.LI);
    this.listType   = listType;
    this.attributes = attributes;
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    try {
      JEditorPane e = getEditor(ae);

      if (e == null) {
        return;
      }

      Element      selem;
      Element      eelem;
      HTMLDocument doc  = (HTMLDocument) (e.getDocument());
      int          pos  = e.getCaretPosition();
      int          spos = e.getSelectionStart();
      int          epos = e.getSelectionEnd();
      int          len  = epos - spos;
      Element      elem = doc.getParagraphElement(pos);

      if (len > 0) {
        if (pos < spos) {
          spos = pos;
        } else if (pos >= epos) {
          epos = pos - 1;
        }

        selem = doc.getParagraphElement(spos);
        eelem = doc.getParagraphElement(epos);
      } else {
        selem = elem;
        eelem = elem;
      }

      HTML.Tag sin      = getList(selem);
      HTML.Tag ein      = getList(eelem);
      boolean  change   = false;
      String   from     = null;
      String   to       = null;
      String   toatttrs = null;

      if ((sin != null) && (sin != listType)) {
        from     = sin.toString();
        to       = listType.toString();
        toatttrs = attributes;
        change   = true;
      }

      if ((ein != null) && (ein != listType)) {
        from     = ein.toString();
        to       = listType.toString();
        toatttrs = attributes;
        change   = true;
      }

      if (!change && (ein != null) && (ein == sin)) {
        change = true;
        from   = ein.toString();
      }

      if (change) {
        elem = getListElement(elem);

        if (elem == null) {
          return;
        }

        selem = elem;
        eelem = elem;

        if (to != null) {
          sin = null;
          ein = null;
        }
      }

      spos = selem.getStartOffset();
      epos = eelem.getEndOffset();

      StringWriter  sw = new StringWriter();
      final boolean li = (sin == null) && (ein == null);

      if (li &&!change) {
        sw.write('<');
        sw.write(listType.toString());

        if (attributes != null) {
          sw.write(' ');
          sw.write(attributes);
        }

        sw.write('>');
      }

      LIWriter w = new LIWriter(sw, doc, spos, epos - spos, li);

      w.fromTag    = from;
      w.toTag      = to;
      w.attributes = toatttrs;
      w.write();

      if (li &&!change) {
        sw.write("</");
        sw.write(listType.toString());
        sw.write('>');
      }

      if ((e instanceof TextEditor)) {
        ((TextEditor) e).startCompoundEdit();
      }

      try {
        doc.remove(spos, epos - spos);
        insertHTML(e, doc, spos, sw.toString(), 0, 0, null);
      } finally {
        if ((e instanceof TextEditor)) {
          ((TextEditor) e).finishCompoundEdit();
        }
      }
    } catch(Exception ble) {}
  }

  public static void insertLI(TextEditor e, HTMLDocument doc, Element elem) throws Exception {
    int     pos    = e.getCaretPosition();
    int     spos   = e.getSelectionStart();
    int     epos   = e.getSelectionEnd();
    int     len    = epos - spos;
    Element parent = getListElement(elem);

    if (len > 0) {
      if (pos < spos) {
        spos = pos;
      } else if (pos >= epos) {
        epos = pos;
      }
    }

    int index = parent.getElementIndex(elem.getStartOffset());
    int count = parent.getElementCount();

    e.startCompoundEdit();

    try {
      if (len == 0) {
        ((HTMLEditorKit) e.getEditorKit()).insertHTML(doc, spos, "<li></li>", 2, 0, HTML.Tag.LI);
      } else {
        StringWriter sw = new StringWriter();
        LIWriter     w  = new LIWriter(sw, doc, spos, epos - spos, true);

        w.write();
        e.startCompoundEdit();
        doc.remove(spos, epos - spos);
        ((HTMLEditorKit) e.getEditorKit()).insertHTML(doc, spos, "<li></li>", 2, 0, HTML.Tag.LI);
      }

      if ((parent.getElementCount() - count > 1) && (index < count)) {
        elem = parent.getElement(index + 1);
        spos = elem.getStartOffset();
        epos = elem.getEndOffset();

        if (epos - spos == 1) {
          doc.remove(spos, epos - spos);
        }
      }
    } finally {
      e.finishCompoundEdit();
    }
  }

  public static HTML.Tag getList(Element e) {
    String ol = HTML.Tag.OL.toString();
    String ul = HTML.Tag.UL.toString();
    String name;

    while(e != null) {
      name = e.getName();

      if (name.equalsIgnoreCase(ol)) {
        return HTML.Tag.OL;
      }

      if (name.equalsIgnoreCase(ul)) {
        return HTML.Tag.UL;
      }

      e = e.getParentElement();
    }

    return null;
  }

  public static Element getListElement(Element e) {
    String ol = HTML.Tag.OL.toString();
    String ul = HTML.Tag.UL.toString();
    String name;

    while(e != null) {
      name = e.getName();

      if (name.equalsIgnoreCase(ol)) {
        return e;
      }

      if (name.equalsIgnoreCase(ul)) {
        return e;
      }

      e = e.getParentElement();
    }

    return null;
  }

  public static HTML.Tag getListItem(Element e) {
    String ol = HTML.Tag.OL.toString();
    String ul = HTML.Tag.UL.toString();
    String name;

    while(e != null) {
      name = e.getName();

      if (name.equalsIgnoreCase(ol)) {
        return HTML.Tag.OL;
      }

      if (name.equalsIgnoreCase(ul)) {
        return HTML.Tag.UL;
      }

      if (name.equalsIgnoreCase("li")) {
        return HTML.Tag.LI;
      }

      e = e.getParentElement();
    }

    return null;
  }

  /**
   * Handles performing transformations of the list and items
   * as the HTML is being written out
   */
  public static class LIWriter extends HTMLWriterEx {
    private String  attributes;
    private String  fromTag;
    private boolean li;
    private String  toTag;

    public LIWriter(Writer w, HTMLDocument doc) {
      super(w, doc);
    }

    public LIWriter(Writer w, HTMLDocument doc, int pos, int len, boolean li) {
      super(w, doc, pos, len);
      this.li = li;
    }

    @Override
    protected void endTag(Element elem) throws IOException {
      final String name = elem.getName();

      if (name.equalsIgnoreCase("html") || name.equalsIgnoreCase("body")) {
        return;
      }

      if (li) {
        if (name.equalsIgnoreCase("p")) {
          write("</li>");
          writeLineSeparator();

          return;
        }
      } else {
        if (name.equalsIgnoreCase("li")) {
          write("</p>");
          writeLineSeparator();

          return;
        }
      }

      if (fromTag != null) {
        if (name.equalsIgnoreCase(fromTag)) {
          if (toTag != null) {
            write("</");
            write(toTag);
            write('>');
            writeLineSeparator();
          }

          return;
        }
      }

      super.endTag(elem);
    }

    @Override
    protected void startTag(Element elem) throws IOException, BadLocationException {
      final String name = elem.getName();

      if (name.equalsIgnoreCase("html") || name.equalsIgnoreCase("body")) {
        return;
      }

      if (li) {
        if (name.equalsIgnoreCase("p")) {
          write("<li>");
          writeLineSeparator();

          return;
        }
      } else {
        if (name.equalsIgnoreCase("li")) {
          write("<p style=\"margin-top: 0\">");
          writeLineSeparator();

          return;
        }
      }

      if (fromTag != null) {
        if (name.equalsIgnoreCase(fromTag)) {
          if (toTag != null) {
            write('<');
            write(toTag);

            if (attributes != null) {
              write(' ');
              write(attributes);
            }

            write('>');
            writeLineSeparator();
          }

          return;
        }
      }

      super.startTag(elem);
    }
  }
}
