/*
 * @(#)HTMLEditorKitEx.java   2010-06-18
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.text;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.SizeRequirements;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLWriter;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.parser.ParserDelegator;

import com.appnativa.rare.Platform;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.EventHelper;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.listener.iHyperlinkListener;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iFormViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.iURLResolver;
import com.appnativa.util.html.MinimalHTMLWriterEx;

/**
 *
 * @author Don DeCoteau
 */
public class HTMLEditorKitEx extends HTMLEditorKit {
  static StyleSheet            defStyleSheet;
  static String                listBullet;
  protected static LinkAdapter linkAdapter = new LinkAdapter();

  /**  */
  iURLResolver          urlResolver;
  protected JEditorPane theEditor;

  /**
   * Constructs a new instance
   */
  public HTMLEditorKitEx() {
    this(null);
  }

  /**
   * Constructs a new instance
   *
   * @param ur the url resolver
   */
  public HTMLEditorKitEx(iURLResolver ur) {
    urlResolver = ur;

    if (defStyleSheet == null) {
      defStyleSheet = getCustomStyleSheet();

      if (defStyleSheet != null) {
        this.setStyleSheet(defStyleSheet);
      }
    }
  }

  public HTMLEditorKitEx copy() {
    return new HTMLEditorKitEx(urlResolver);
  }

  @Override
  public Document createDefaultDocument() {
    StyleSheet styles = getStyleSheet();
    StyleSheet ss     = new StyleSheetEx();

    updateStyleSheet(ss);
    ss.addStyleSheet(styles);

    HTMLDocument doc = new HTMLDocumentEx(ss);

    doc.setParser(getParser());

    if ((theEditor != null) && (theEditor.getClientProperty("synchronous.load") == Boolean.TRUE)) {
      doc.setAsynchronousLoadPriority(-1);
    } else {
      doc.setAsynchronousLoadPriority(4);
    }

    doc.setTokenThreshold(100);

    return doc;
  }

  @Override
  public void deinstall(JEditorPane c) {
    super.deinstall(c);
    c.removeMouseListener(linkAdapter);
    c.removeMouseMotionListener(linkAdapter);
    c.removeHyperlinkListener(linkAdapter);
    theEditor = null;
  }

  @Override
  public void install(JEditorPane c) {
    super.install(c);
    theEditor = c;

    MouseListener[] a   = c.getListeners(MouseListener.class);
    int             len = (a == null)
                          ? 0
                          : a.length;

    for (int i = 0; i < len; i++) {
      if (a[i] instanceof LinkController) {
        c.removeMouseListener(a[i]);
        c.removeMouseMotionListener((LinkController) a[i]);
      }
    }

    c.addMouseListener(linkAdapter);
    c.addMouseMotionListener(linkAdapter);
    c.addHyperlinkListener(linkAdapter);
  }

  @Override
  public void write(Writer out, Document doc, int pos, int len) throws IOException, BadLocationException {
    if (doc instanceof HTMLDocument) {
      HTMLWriter w = new HTMLWriterEx(out, (HTMLDocument) doc, pos, len);

      w.write();
    } else if (doc instanceof StyledDocument) {
      MinimalHTMLWriterEx w = new MinimalHTMLWriterEx(out, (StyledDocument) doc, pos, len);

      w.write();
    } else {
      super.write(out, doc, pos, len);
    }
  }

  /**
   * @return the formViewer
   */
  public iFormViewer getFormViewer() {
    if (theEditor == null) {
      return null;
    }

    iWidget w = Platform.findWidgetForComponent(theEditor);

    return (w == null)
           ? null
           : w.getFormViewer();
  }

  public int getHTMLFontIndex(float size) {

    return StyleSheet.getIndexOfSize(size);
  }
  public static StringBuilder removeMarkup(Reader reader,boolean html,StringBuilder sb) throws IOException {
    if(sb==null) {
      sb=new StringBuilder();
    }
    List<String> lines = removeMarkupEx(reader,html);
    for (String line : lines) {
      sb.append(line).append('\n');
    }
    return sb;
  }

  public static List<String> removeMarkupEx(Reader reader,boolean html) throws IOException {
    final ArrayList<String> list = new ArrayList<String>();

    ParserDelegator parserDelegator = new ParserDelegator();
    ParserCallback parserCallback = new AParserCallback(list,html);
    parserDelegator.parse(reader, parserCallback, true);
    return list;
  }
  private static class AParserCallback extends ParserCallback {
    final List list;
    int ignore;
    boolean html;
    final static HashSet<Tag> skipTags=new HashSet<Tag>();
    static {
      skipTags.add(Tag.STYLE);
      skipTags.add(Tag.SCRIPT);
      skipTags.add(Tag.SCRIPT);
      skipTags.add(Tag.HEAD);
    }
    AParserCallback(List list,boolean html) {
      this.list=list;
      this.html=html;
    }

      @Override
      public void handleText(final char[] data, final int pos) {
        if(ignore<1) {
          list.add(new String(data));
        }
      }
      @Override
      public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) {
        if(!html) {
          return;
        }
        if(skipTags.contains(tag)) {
          ignore++;
          return;
        }
      }
      @Override
      public void handleEndTag(Tag tag, final int pos) {
        if(!html) {
          return;
        }
        if(skipTags.contains(tag)) {
          ignore--;
          return;
        }
      }
  }
  public float getHTMLFontSize(int index) {
    StyleSheet s = getStyleSheet();

    return s.getPointSize(index);
  }

 
  @Override
  public ViewFactory getViewFactory() {
    return new HTMLFactoryX();
  }

  /**
   * @return the formViewer
   */
  public iWidget getWidget() {
    if (theEditor == null) {
      return null;
    }

    return Platform.findWidgetForComponent(theEditor);
  }

  static void updateStyleSheet(StyleSheet s) {
    Font f = Platform.getUIDefaults().getFont("Rare.html.font");

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Rare.richtext.font");
    }

    if (f == null) {
      f = FontUtils.getDefaultFont();
    }

    if ((f.getStyle() != Font.PLAIN)) {
      f = f.deriveFont(Font.PLAIN);
    }

    s.setBaseFontSize(StyleSheet.getIndexOfSize(f.getSize2D()));

    String rule = "body { font-family: \"" + f.getFamily() + "\"; }";

    s.addRule(rule);
    f = Platform.getUIDefaults().getFont("Rare.font.plaintext");

    if (f != null) {
      rule = "pre { font-family: \"" + f.getFamily() + "\"; " + "font-size: \"" + f.getSize() + "pt\"; }";
      s.addRule(rule);
    }

    URL url = (URL) Platform.getUIDefaults().get("Rare.html.listBullet");

    if (url != null) {
      listBullet = JavaURLConnection.toExternalForm(url);
      s.addRule("ul {list-style-image: \"" + JavaURLConnection.toExternalForm(url) + "\";}");
    }
  }

  static Object getAttribute(AttributeSet set, Object key) {
    return set.isDefined(key)
           ? set.getAttribute(key)
           : null;
  }

  private StyleSheet getCustomStyleSheet() {
    StyleSheet s = null;
    Reader     r = null;

    try {
      URL url = (URL) Platform.getUIDefaults().get("Rare.html.styleSheet");

      if (url != null) {
        s = getDefaultStyleSheet();
        r = new BufferedReader(new InputStreamReader(url.openStream(), "ISO-8859-1"));
        s.loadRules(r, url);
      }
    } catch(Exception e) {
      if (r != null) {
        try {
          r.close();
        } catch(Exception ex) {}
      }

      Platform.ignoreException("Loading custom styleSheet", e);
    }

    return s;
  }

  private StyleSheet getDefaultStyleSheet() {
    StyleSheetEx s = new StyleSheetEx();

    try {
      InputStream is = HTMLEditorKit.class.getResourceAsStream(DEFAULT_CSS);
      Reader      r  = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

      s.loadRules(r, null);
      r.close();
      is.close();
    } catch(Throwable e) {}

    return s;
  }

  public static interface iGroupView {
    void setContainerVisible(boolean visible);

    iContainer getViewer();
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  public static class LinkAdapter implements MouseListener, MouseMotionListener, Serializable, HyperlinkListener {
    LinkController linkController = new LinkController();
    MouseEvent     mouseEvent;

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
      Object o = e.getSource();

      if (o instanceof TextEditor) {
        TextEditor         pane         = (TextEditor) o;
        iHyperlinkListener linkListener = pane.getHyperLinkListener();

        if (linkListener != null) {
          HyperlinkEvent.EventType type = e.getEventType();
          URL                      url  = e.getURL();
          com.appnativa.rare.ui.event.MouseEvent me=EventHelper.createMouseEvent(o,mouseEvent);
          if (type == HyperlinkEvent.EventType.ENTERED) {
            linkListener.linkEntered(pane, url, e.getDescription(), me);
          } else if (type == HyperlinkEvent.EventType.EXITED) {
            linkListener.linkExited(pane, url, e.getDescription(), me);
          } else if (type == HyperlinkEvent.EventType.ACTIVATED) {
            linkListener.linkClicked(pane, url, e.getDescription(), me);
          }
        }
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
      try {
        mouseEvent = e;
        linkController.mouseDragged(e);
      } finally {
        mouseEvent = null;
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      try {
        mouseEvent = e;
        linkController.mouseEntered(e);
      } finally {
        mouseEvent = null;
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      try {
        mouseEvent = e;
        linkController.mouseExited(e);
      } finally {
        mouseEvent = null;
      }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      try {
        mouseEvent = e;
        linkController.mouseMoved(e);
      } finally {
        mouseEvent = null;
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
      try {
        mouseEvent = e;
        e          = new MouseEvent(e.getComponent(), MouseEvent.MOUSE_CLICKED, e.getWhen(),
                                    e.getModifiers() | e.getModifiersEx(), e.getX(), e.getY(), 1, e.isPopupTrigger());
        linkController.mouseClicked(e);
      } finally {
        mouseEvent = null;
      }
    }
  }

/**
 * An HTMLDocument extension
 */
 public static class HTMLDocumentEx extends HTMLDocument {
    HashMap<Element, iContainer> formsMap;
    ArrayList<ObjectHolder> scriptsList;

    /**
     * Creates a new instance
     */
    public HTMLDocumentEx() {
      this(new StyleSheetEx());
    }

    /**
     * Creates a new instance
     * @param styles styles-sheet
     */
    public HTMLDocumentEx(StyleSheet styles) {
      super(styles);
    }

    /**
     * Creates a new instance
     * @param c the content
     * @param styles styles-sheet
     */
    public HTMLDocumentEx(Content c, StyleSheet styles) {
      super(c, styles);
    }

    /**
     * Get the list of scripts that the document had
     * The objects in the list are of the type com.appnativa.util.ObjectHolder.
     * The key is the script type attribute, the value is the src attribute and flags=0 means that the src is a url
     *
     * @return the list of scripts that the document had
     *
     * @see com.appnativa.util.ObjectHolder
     */
    public ArrayList<ObjectHolder> getScripts() {
      return scriptsList;
    }

    @Override
    public HTMLEditorKit.ParserCallback getReader(int pos) {
      Object desc = getProperty(Document.StreamDescriptionProperty);

      if (desc instanceof URL) {
        setBase((URL) desc);
      }

      HTMLReader reader = new HTMLReaderEx(pos);

      return reader;
    }

    @Override
    public HTMLEditorKit.ParserCallback getReader(int pos, int popDepth, int pushDepth, HTML.Tag insertTag) {
      Object desc = getProperty(Document.StreamDescriptionProperty);

      if (desc instanceof URL) {
        setBase((URL) desc);
      }

      HTMLReader reader = new HTMLReaderEx(pos, popDepth, pushDepth, insertTag);

      return reader;
    }

    class HTMLReaderEx extends HTMLReader {
      private boolean inScript=false;
      public HTMLReaderEx(int offset) {
        super(offset);
        registerTag(HTML.Tag.SPAN, new SpecialAction());
        registerTag(HTML.Tag.SCRIPT, new ScriptAction());
      }

      public HTMLReaderEx(int offset, int popDepth, int pushDepth, Tag insertTag) {
        super(offset, popDepth, pushDepth, insertTag);
      }

      @Override
      public void handleComment(char[] data, int pos) {
        super.handleComment(data, pos);
        if(inScript && scriptsList!=null && scriptsList.size()>0) {
          scriptsList.get(scriptsList.size()-1).value=new String(data);
        }
      }

      class ScriptAction extends HiddenAction {

        @Override
        public void start(Tag t, MutableAttributeSet a) {
          Object src=a.getAttribute(HTML.Attribute.SRC);
          Object type=a.getAttribute(HTML.Attribute.TYPE);
          if(src!=null || type!=null) {
            if(scriptsList==null) {
              scriptsList=new ArrayList<ObjectHolder>();
            }
            scriptsList.add(new ObjectHolder(type,src,src==null ? 1 : 0));
            inScript=src==null;
          }
          super.start(t, a);
        }

        @Override
        public void end(Tag t) {
          inScript=false;
          super.end(t);
        }

      }
    }
  }


  /**
   *
   *
   * @version    0.3, 2007-05-14
   * @author     Don DeCoteau
   */
  class HTMLFactoryX extends HTMLFactory {
    iContainer spanParent;
    public javax.swing.text.View create(Element e) {
      View v = super.create(e); 
      if(v instanceof InlineView){ 
          return new InlineView(e){ 
              public int getBreakWeight(int axis, float pos, float len) { 
                  return GoodBreakWeight; 
              } 
              public View breakView(int axis, int p0, float pos, float len) { 
                  if(axis == View.X_AXIS) { 
                      checkPainter(); 
                      int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len); 
                      if(p0 == getStartOffset() && p1 == getEndOffset()) { 
                          return this; 
                      } 
                      return createFragment(p0, p1); 
                  } 
                  return this; 
                } 
            }; 
      } 
      else if (v instanceof ParagraphView) { 
          return new ParagraphView(e) { 
              protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) { 
                  if (r == null) { 
                        r = new SizeRequirements(); 
                  } 
                  float pref = layoutPool.getPreferredSpan(axis); 
                  float min = layoutPool.getMinimumSpan(axis); 
                  // Don't include insets, Box.getXXXSpan will include them. 
                    r.minimum = (int)min; 
                    r.preferred = Math.max(r.minimum, (int) pref); 
                    r.maximum = Integer.MAX_VALUE; 
                    r.alignment = 0.5f; 
                  return r; 
                } 

            }; 
        } 
      return v;       
    }

//    public View create(Element elem) {
//      Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
//
//      if (o instanceof HTML.Tag) {
//        HTML.Tag kind = (HTML.Tag) o;
//
//        if (kind == HTML.Tag.FORM) {
//          FormsView v = new FormsView(getWidget(), elem, View.Y_AXIS);
//
//          return v;
//        } else if (kind == HTML.Tag.IMG) {
//          return new ImageView(elem) {
//
//            /**
//             * Return a URL for the image source,
//             * or null if it could not be determined.
//             */
//            public URL getImageURL() {
//              String src = (String) getAttribute(getElement().getAttributes(), HTML.Attribute.SRC);
//
//              if (src == null) {
//                return null;
//              }
//
//              URL reference = ((HTMLDocument) getDocument()).getBase();
//
//              if (reference == null) {
//                reference = (urlResolver != null)
//                            ? urlResolver.getBaseURL()
//                            : null;
//              }
//
//              try {
//                URL u = new URL(reference, src);
//
//                return u;
//              } catch(MalformedURLException e) {
//                return null;
//              }
//            }
//          };
//        } else if ((kind == HTML.Tag.INPUT) || (kind == HTML.Tag.SELECT) || (kind == HTML.Tag.TEXTAREA)) {
//          return new WidgetView(elem, getParentContainer());
//        } else if ((kind == HTML.Tag.DIV) && elem.getAttributes().isDefined(HTML.Attribute.CLASS)) {
//          String cls = (String) getAttribute(elem.getAttributes(), HTML.Attribute.CLASS);
//
//          if ((cls != null) && (cls.startsWith("Form{") || cls.startsWith("GroupBox{"))) {
//            return new FormsView(getParentContainer(), elem, View.Y_AXIS);
//          }
//        } else if (kind == HTML.Tag.SPAN) {
//          String cls = (String) getAttribute(elem.getAttributes(), HTML.Attribute.CLASS);
//
//          if ((cls != null) && (cls.startsWith("Form{") || cls.startsWith("GroupBox{"))) {
//            SpanView v = new SpanView(getParentContainer(), elem);
//
//            spanParent = v.getViewer();
//
//            return v;
//          }
//
//          String name = (String) getAttribute(elem.getAttributes(), HTML.Attribute.NAME);
//
//          if ((name != null) && name.equals("rare-span-end")) {
//            spanParent = null;
//          }
//        } else if ((kind == HTML.Tag.MENU) || (kind == HTML.Tag.DIR) || (kind == HTML.Tag.UL)
//                   || (kind == HTML.Tag.OL)) {
//          if(!Platform.getUIDefaults().getBoolean("Rare.swing.html.useDefaultListView",false)) {
//            return new ListView(elem);
//          }
//        }
//      }
//
//      return super.create(elem);
//    }

    iContainer getParentContainer() {
      if (spanParent != null) {
        return spanParent;
      }

      iWidget w = getWidget();

      if (w.getLinkedData() instanceof iContainer) {
        return (iContainer) w.getLinkedData();
      }

      return w.getContainerViewer();
    }
  }


  static class HTMLWriterEx extends HTMLWriter {
    protected boolean ignoreLinefeed;

    public HTMLWriterEx(Writer w, HTMLDocument doc) {
      super(w, doc);
    }

    public HTMLWriterEx(Writer w, HTMLDocument doc, int pos, int len) {
      super(w, doc, pos, len);
    }

    @Override
    protected void emptyTag(Element elem) throws IOException, BadLocationException {
      super.emptyTag(elem);

      Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);

      if (o == HTML.Tag.SPAN) {
        write('<');
        write('/');
        write(elem.getName());
        write('>');

        return;
      }
    }

    @Override
    protected void startTag(Element elem) throws IOException, BadLocationException {
      Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);

      try {
        if (o == HTML.Tag.SELECT) {
          ignoreLinefeed = true;
        }

        super.startTag(elem);

        if (o == HTML.Tag.SPAN) {
          write('<');
          write('/');
          write(elem.getName());
          write('>');
        }
      } finally {
        ignoreLinefeed = false;
      }
    }

    @Override
    protected void writeLineSeparator() throws IOException {
      if (ignoreLinefeed) {
        ignoreLinefeed = false;

        return;
      }

      super.writeLineSeparator();
    }
  }
}
