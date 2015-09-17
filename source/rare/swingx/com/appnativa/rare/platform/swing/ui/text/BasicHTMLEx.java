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

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.StyleSheet;

import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.ui.text.HTMLEditorKitEx.HTMLDocumentEx;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.util.CharArray;

/**
 * Support for providing html views for the swing components. This translates a
 * simple html string to a javax.swing.text.View implementation that can render
 * the html and provide the necessary layout semantics.
 *
 * NOTE: This is a COPY of BasicHTML for the JDK that has been modified to use a
 * custom HTMLEditor kit and custom HTMLDocument
 *
 * @author Timothy Prinzing
 * @version 1.27 04/07/06
 * @since 1.3
 */
public class BasicHTMLEx {

  /**
   * Key stored as a client property to indicate the base that relative
   * references are resolved against. For example, lets say you keep your images
   * in the directory resources relative to the code path, you would use the
   * following the set the base:
   *
   * <pre>
   * jComponent.putClientProperty(documentBaseKey, xxx.class.getResource(&quot;resources/&quot;));
   * </pre>
   */
  public static final String documentBaseKey = "html.base";

  /**
   * Key to use for the html renderer when stored as a client property of a
   * JComponent.
   */
  public static final String propertyKey = "html";

  /**
   * If this client property of a JComponent is set to Boolean.TRUE the
   * component's 'text' property is never treated as HTML.
   */
  private static final String htmlDisable = "rare.html.disable";

  /**
   * Overrides to the default stylesheet. Should consider just creating a
   * completely fresh stylesheet.
   */
  private static final String styleChanges =
    "p { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }"
    + "body { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }";

  /**
   * The source of the html renderers
   */
  private static BasicEditorKit basicHTMLFactory;

  /**
   * Creates the Views that visually represent the model.
   */
  private static ViewFactory basicHTMLViewFactory;

  /**
   * Create an html renderer for the given component and string of html.
   */
  @SuppressWarnings("resource")
  public static View createHTMLView(JComponent c, String html) {
    BasicEditorKit kit  = getFactory();
    Document       doc  = kit.createDefaultDocument(c.getFont(), c.getForeground());
    Object         base = c.getClientProperty(documentBaseKey);

    if (base instanceof URL) {
      ((HTMLDocument) doc).setBase((URL) base);
    }

    boolean center;

    if (c instanceof JLabel) {
      center = ((JLabel) c).getHorizontalAlignment() == SwingConstants.CENTER;
    } else if (c instanceof AbstractButton) {
      center = ((AbstractButton) c).getHorizontalAlignment() == SwingConstants.CENTER;
    } else {
      center = false;
    }

    if (html == null) {
      html = "";
    }

    Reader r;

    if (html.startsWith("<html>")) {
      r = new StringReader(html);
    } else {
      if (html.indexOf('\n') != -1) {
        String prefix = center
                        ? "<center>"
                        : null;
        String suffix = center
                        ? "</center>"
                        : null;

        r = new StringReader(Functions.tokenToHTMLBreak(html, "\n", true, prefix, suffix, -1));
      } else {
        CharArray ca = new CharArray(html.length() + 25);

        ca.append("<html><body>");

        if (center) {
          ca.append("<center>").append(html).append("</center>");
        } else {
          ca.append(html);
        }

        ca.append("</body></html>");
        r = ca;
      }
    }

    try {
      kit.read(r, doc, 0);
    } catch(Throwable e) {}

    ViewFactory f     = kit.getViewFactory();
    View        hview = f.create(doc.getDefaultRootElement());
    View        v     = new Renderer(c, f, hview);

    return v;
  }

  /**
   * Stash the HTML render for the given text into the client properties of the
   * given JComponent. If the given text is <em>NOT HTML</em> the property will
   * be cleared of any renderer.
   * <p>
   * This method is useful for ComponentUI implementations that are static (i.e.
   * shared) and get their state entirely from the JComponent.
   */
  public static void updateRenderer(JComponent c, String text) {
    updateRenderer(c, text, false);
  }

  public static void updateRenderer(JComponent c, String text, boolean forceHtml) {
    View    value        = null;
    View    oldValue     = (View) c.getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);
    Boolean htmlDisabled = (Boolean) c.getClientProperty(htmlDisable);

    if (forceHtml || ((htmlDisabled != Boolean.TRUE) && BasicHTMLEx.isHTMLString(text))) {
      value = BasicHTMLEx.createHTMLView(c, text);
    } else {
      c.putClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey, null);
    }

    if ((value != oldValue) && (oldValue != null)) {
      for (int i = 0; i < oldValue.getViewCount(); i++) {
        oldValue.getView(i).setParent(null);
      }
    }

    c.putClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey, value);
  }

  /**
   * Returns the baseline for the html renderer.
   *
   * @param view
   *          the View to get the baseline for
   * @param w
   *          the width to get the baseline for
   * @param h
   *          the height to get the baseline for
   * @throws IllegalArgumentException
   *           if width or height is &lt; 0
   * @return baseline or a value &lt; 0 indicating there is no reasonable
   *         baseline
   * @see java.awt.FontMetrics
   * @see javax.swing.JComponent#getBaseline(int,int)
   * @since 1.6
   */
  public static int getHTMLBaseline(View view, int w, int h) {
    if ((w < 0) || (h < 0)) {
      throw new IllegalArgumentException("Width and height must be >= 0");
    }

    if (view instanceof Renderer) {
      return getBaseline(view.getView(0), w, h);
    }

    return -1;
  }

  /**
   * Check the given string to see if it should trigger the html rendering logic
   * in a non-text component that supports html rendering.
   */
  public static boolean isHTMLString(String s) {
    if (s != null) {
      if ((s.length() >= 6) && (s.charAt(0) == '<') && (s.charAt(5) == '>')) {
        String tag = s.substring(1, 5);

        return tag.equalsIgnoreCase(propertyKey);
      }
    }

    return false;
  }

  /**
   * Gets the baseline for the specified View.
   */
  static int getBaseline(View view, int w, int h) {
    if (hasParagraph(view)) {
      view.setSize(w, h);

      return getBaseline(view, new Rectangle(0, 0, w, h));
    }

    return -1;
  }

  /**
   * Gets the baseline for the specified component. This digs out the View
   * client property, and if non-null the baseline is calculated from it.
   * Otherwise the baseline is the value <code>y + ascent</code>.
   */
  static int getBaseline(JComponent c, int y, int ascent, int w, int h) {
    View view = (View) c.getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    if (view != null) {
      int baseline = getHTMLBaseline(view, w, h);

      if (baseline < 0) {
        return baseline;
      }

      return y + baseline;
    }

    return y + ascent;
  }

  static BasicEditorKit getFactory() {
    if (basicHTMLFactory == null) {
      basicHTMLViewFactory = new BasicHTMLViewFactory();
      basicHTMLFactory     = new BasicEditorKit();
    }

    return basicHTMLFactory;
  }

  private static int getBaseline(View view, Shape bounds) {
    if (view.getViewCount() == 0) {
      return -1;
    }

    AttributeSet attributes = view.getElement().getAttributes();
    Object       name       = null;

    if (attributes != null) {
      name = attributes.getAttribute(StyleConstants.NameAttribute);
    }

    int index = 0;

    if ((name == HTML.Tag.HTML) && (view.getViewCount() > 1)) {
      // For html on widgets the header is not visible, skip it.
      index++;
    }

    bounds = view.getChildAllocation(index, bounds);

    if (bounds == null) {
      return -1;
    }

    View child = view.getView(index);

    if (view instanceof javax.swing.text.ParagraphView) {
      Rectangle rect;

      if (bounds instanceof Rectangle) {
        rect = (Rectangle) bounds;
      } else {
        rect = bounds.getBounds();
      }

      return rect.y + (int) (rect.height * child.getAlignment(View.Y_AXIS));
    }

    return getBaseline(child, bounds);
  }

  private static boolean hasParagraph(View view) {
    if (view instanceof javax.swing.text.ParagraphView) {
      return true;
    }

    if (view.getViewCount() == 0) {
      return false;
    }

    AttributeSet attributes = view.getElement().getAttributes();
    Object       name       = null;

    if (attributes != null) {
      name = attributes.getAttribute(StyleConstants.NameAttribute);
    }

    int index = 0;

    if ((name == HTML.Tag.HTML) && (view.getViewCount() > 1)) {
      // For html on widgets the header is not visible, skip it.
      index = 1;
    }

    return hasParagraph(view.getView(index));
  }

  /**
   * The subclass of HTMLDocument that is used as the model. getForeground is
   * overridden to return the foreground property from the Component this was
   * created for.
   */
  static class BasicDocument extends HTMLDocumentEx {

    /** The host, that is where we are rendering. */
    // private JComponent host;
    BasicDocument(StyleSheet s, Font defaultFont, Color foreground) {
      super(s);
      setPreservesUnknownTags(false);
      setFontAndColor(defaultFont, foreground);
    }

    /**
     * Sets the default font and default color. These are set by adding a rule
     * for the body that specifies the font and color. This allows the html to
     * override these should it wish to have a custom font or color.
     */
    private void setFontAndColor(Font font, Color fg) {
      getStyleSheet().addRule(StyleSheetEx.displayPropertiesToCSS(font, fg));
    }
  }


  /**
   * The views produced for the ComponentUI implementations aren't going to be
   * edited and don't need full html support. This kit alters the HTMLEditorKit
   * to try and trim things down a bit. It does the following:
   * <ul>
   * <li>It doesn't produce Views for things like comments, head, title, unknown
   * tags, etc.
   * <li>It installs a different set of css settings from the default provided
   * by HTMLEditorKit.
   * </ul>
   */
  static class BasicEditorKit extends HTMLEditorKitEx {

    /** Shared base style for all documents created by us use. */
    private static StyleSheet defaultStyles;

    /**
     * Sets the async policy to flush everything in one chunk, and to not
     * display unknown tags.
     */
    public Document createDefaultDocument(Font defaultFont, Color foreground) {
      StyleSheet styles = getStyleSheet();
      StyleSheet ss     = new StyleSheetEx();

      ss.addStyleSheet(styles);

      BasicDocument doc = new BasicDocument(ss, defaultFont, foreground);

      doc.setAsynchronousLoadPriority(Integer.MAX_VALUE);
      doc.setPreservesUnknownTags(false);

      return doc;
    }

    /**
     * Overriden to return our own slimmed down style sheet.
     */
    @Override
    public StyleSheet getStyleSheet() {
      if (defaultStyles == null) {
        defaultStyles = new StyleSheetEx();

        StringReader r = new StringReader(styleChanges);

        try {
          defaultStyles.loadRules(r, null);
        } catch(Throwable e) {
          // don't want to die in static initialization...
          // just display things wrong.
        }

        r.close();
        defaultStyles.addStyleSheet(super.getStyleSheet());
      }

      return defaultStyles;
    }

    /**
     * Returns the ViewFactory that is used to make sure the Views don't load in
     * the background.
     */
    @Override
    public ViewFactory getViewFactory() {
      return basicHTMLViewFactory;
    }
  }


  /**
   * BasicHTMLViewFactory extends HTMLFactory to force images to be loaded
   * synchronously.
   */
  static class BasicHTMLViewFactory extends HTMLEditorKit.HTMLFactory {
    @Override
    public View create(Element elem) {
      Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);

      if (o instanceof HTML.Tag) {
        HTML.Tag kind = (HTML.Tag) o;

        if (kind == HTML.Tag.IMG) {
          return new ImageView(elem) {

            /**
             * Return a URL for the image source, or null if it could not be
             * determined.
             */
            @Override
            public URL getImageURL() {
              String src = (String) getAttribute(getElement().getAttributes(), HTML.Attribute.SRC);

              if (src == null) {
                return null;
              }

              URL reference = ((HTMLDocument) getDocument()).getBase();

              if (reference == null) {
                reference = AppContext.getContext().getContextURL();
              }

              try {
                URL u = new URL(reference, src);

                return u;
              } catch(MalformedURLException e) {
                return null;
              }
            }
          };
//        } else if ((kind == HTML.Tag.MENU) || (kind == HTML.Tag.DIR) || (kind == HTML.Tag.UL)
//                   || (kind == HTML.Tag.OL)) {
        } else if (kind == HTML.Tag.CONTENT) {
          return new InlineViewEx(elem);
        }
      }

      return super.create(elem);
    }

    static Object getAttribute(AttributeSet set, Object key) {
      return set.isDefined(key)
             ? set.getAttribute(key)
             : null;
    }
  }


  static class InlineViewEx extends InlineView {
    private float minimumSpan;

    public InlineViewEx(Element elem) {
      super(elem);
    }

    @Override
    public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
      super.insertUpdate(e, a, f);
      minimumSpan = -1;
    }

    @Override
    public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
      super.removeUpdate(e, a, f);
      minimumSpan = -1;
    }

    @Override
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
      super.changedUpdate(e, a, f);
      minimumSpan = -1;
    }

    @Override
    public float getMinimumSpan(int axis) {
      super.getMinimumSpan(axis);    //call so super private variables are set;

      switch(axis) {
        case View.X_AXIS :
          if (minimumSpan < 0) {
            minimumSpan = 0;

            int p0 = getStartOffset();
            int p1 = getEndOffset();

            while(p1 > p0) {
              int breakSpot = getBreakSpot(p0, p1);

              if (breakSpot == BreakIterator.DONE) {
                // the rest of the view is non-breakable
                breakSpot = p0;
              }

              minimumSpan = Math.max(minimumSpan, getPartialSpan(breakSpot, p1));
              // Note: getBreakSpot returns the *last* breakspot
              p1 = breakSpot - 1;
            }
          }

          return minimumSpan;

        case View.Y_AXIS :
          return super.getMinimumSpan(axis);

        default :
          throw new IllegalArgumentException("Invalid axis: " + axis);
      }
    }

    @Override
    public int getBreakWeight(int axis, float pos, float len) {
      int bw = super.getBreakWeight(axis, pos, len);    //call so super private variables are set;

      if (axis == View.X_AXIS) {
        return View.GoodBreakWeight;
      }

      return bw;
    }

    /**
     * Returns a location to break at in the passed in region, or
     * BreakIterator.DONE if there isn't a good location to break at in the
     * specified region.
     */
    private int getBreakSpot(int p0, int p1) {
      return BreakIterator.DONE;
    }
  }


  /**
   * A simple whitespace-based BreakIterator implementation.
   *
   * @author Sergey Groznyh
   */
  static class WhitespaceBasedBreakIterator extends BreakIterator {
    private char[] text   = new char[0];
    private int[]  breaks = new int[] { 0 };
    private int    pos    = 0;

    /**
     * Calculate break positions eagerly parallel to reading text.
     */
    public void setText(CharacterIterator ci) {
      int begin = ci.getBeginIndex();

      text = new char[ci.getEndIndex() - begin];

      int[] breaks0 = new int[text.length + 1];
      int   brIx    = 0;

      breaks0[brIx++] = begin;

      int     charIx = 0;
      boolean inWs   = false;

      for (char c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
        text[charIx] = c;

        boolean ws = Character.isWhitespace(c) || (c == '/') || (c == '-');

        if (inWs &&!ws) {
          breaks0[brIx++] = charIx + begin;
        }

        inWs = ws;
        charIx++;
      }

      if (text.length > 0) {
        breaks0[brIx++] = text.length + begin;
      }

      System.arraycopy(breaks0, 0, breaks = new int[brIx], 0, brIx);
    }

    public CharacterIterator getText() {
      return new StringCharacterIterator(new String(text));
    }

    public int first() {
      return breaks[pos = 0];
    }

    public int last() {
      return breaks[pos = breaks.length - 1];
    }

    public int current() {
      return breaks[pos];
    }

    public int next() {
      return ((pos == breaks.length - 1)
              ? DONE
              : breaks[++pos]);
    }

    public int previous() {
      return ((pos == 0)
              ? DONE
              : breaks[--pos]);
    }

    public int next(int n) {
      return checkhit(pos + n);
    }

    public int following(int n) {
      return adjacent(n, 1);
    }

    public int preceding(int n) {
      return adjacent(n, -1);
    }

    private int checkhit(int hit) {
      if ((hit < 0) || (hit >= breaks.length)) {
        return DONE;
      } else {
        return breaks[pos = hit];
      }
    }

    private int adjacent(int n, int bias) {
      int hit    = Arrays.binarySearch(breaks, n);
      int offset = ((hit < 0)
                    ? ((bias < 0)
                       ? -1
                       : -2)
                    : 0);

      return checkhit(Math.abs(hit) + bias + offset);
    }
  }


  /**
   * Root text view that acts as an HTML renderer.
   */
  static class Renderer extends View {
    private ViewFactory factory;
    private JComponent  host;
    private View        view;
    private int         width;

    Renderer(JComponent c, ViewFactory f, View v) {
      super(null);
      host    = c;
      factory = f;
      view    = v;
      view.setParent(this);
      // initially layout to the preferred size
      width = (int) view.getPreferredSpan(X_AXIS);
      setSize(width, view.getPreferredSpan(Y_AXIS));
    }

    /**
     * Provides a mapping from the document model coordinate space to the
     * coordinate space of the view mapped to it.
     *
     * @param pos
     *          the position to convert
     * @param a
     *          the allocated region to render into
     * @return the bounding box of the given position
     */
    @Override
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
      return view.modelToView(pos, a, b);
    }

    /**
     * Provides a mapping from the document model coordinate space to the
     * coordinate space of the view mapped to it.
     *
     * @param p0
     *          the position to convert >= 0
     * @param b0
     *          the bias toward the previous character or the next character
     *          represented by p0, in case the position is a boundary of two
     *          views.
     * @param p1
     *          the position to convert >= 0
     * @param b1
     *          the bias toward the previous character or the next character
     *          represented by p1, in case the position is a boundary of two
     *          views.
     * @param a
     *          the allocated region to render into
     * @return the bounding box of the given position is returned
     * @exception BadLocationException
     *              if the given position does not represent a valid location in
     *              the associated document
     * @exception IllegalArgumentException
     *              for an invalid bias argument
     * @see View#viewToModel
     */
    @Override
    public Shape modelToView(int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a) throws BadLocationException {
      return view.modelToView(p0, b0, p1, b1, a);
    }

    /**
     * Renders the view.
     *
     * @param g
     *          the graphics context
     * @param allocation
     *          the region to render into
     */
    @Override
    public void paint(Graphics g, Shape allocation) {
      Rectangle alloc = allocation.getBounds();

      view.setSize(alloc.width, alloc.height);
      view.paint(g, allocation);
    }

    /**
     * Specifies that a preference has changed. Child views can call this on the
     * parent to indicate that the preference has changed. The root view routes
     * this to invalidate on the hosting component.
     * <p>
     * This can be called on a different thread from the event dispatching
     * thread and is basically unsafe to propagate into the component. To make
     * this safe, the operation is transferred over to the event dispatching
     * thread for completion. It is a design goal that all view methods be safe
     * to call without concern for concurrency, and this behavior helps make
     * that true.
     *
     * @param child
     *          the child view
     * @param width
     *          true if the width preference has changed
     * @param height
     *          true if the height preference has changed
     */
    @Override
    public void preferenceChanged(View child, boolean width, boolean height) {
      host.revalidate();
      host.repaint();
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * @param x
     *          x coordinate of the view location to convert
     * @param y
     *          y coordinate of the view location to convert
     * @param a
     *          the allocated region to render into
     * @return the location within the model that best represents the given
     *         point in the view
     */
    @Override
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
      return view.viewToModel(x, y, a, bias);
    }

    /**
     * Sets the view parent.
     *
     * @param parent
     *          the parent view
     */
    @Override
    public void setParent(View parent) {
      throw new Error("Can't set parent on root view");
    }

    /**
     * Sets the view size.
     *
     * @param width
     *          the width
     * @param height
     *          the height
     */
    @Override
    public void setSize(float width, float height) {
      view.setSize(width, height);
    }

    /**
     * Determines the desired alignment for this view along an axis.
     *
     * @param axis
     *          may be either X_AXIS or Y_AXIS
     * @return the desired alignment, where 0.0 indicates the origin and 1.0 the
     *         full span away from the origin
     */
    @Override
    public float getAlignment(int axis) {
      return view.getAlignment(axis);
    }

    /**
     * Fetches the attributes to use when rendering. At the root level there are
     * no attributes. If an attribute is resolved up the view hierarchy this is
     * the end of the line.
     */
    @Override
    public AttributeSet getAttributes() {
      return null;
    }

    /**
     * Fetches the container hosting the view. This is useful for things like
     * scheduling a repaint, finding out the host components font, etc. The
     * default implementation of this is to forward the query to the parent
     * view.
     *
     * @return the container
     */
    @Override
    public Container getContainer() {
      return host;
    }

    /**
     * Returns the document model underlying the view.
     *
     * @return the model
     */
    @Override
    public Document getDocument() {
      return view.getDocument();
    }

    /**
     * Gets the element that this view is mapped to.
     *
     * @return the view
     */
    @Override
    public Element getElement() {
      return view.getElement();
    }

    /**
     * Returns the ending offset into the model for this view.
     *
     * @return the ending offset
     */
    @Override
    public int getEndOffset() {
      return view.getEndOffset();
    }

    /**
     * Determines the maximum span for this view along an axis.
     *
     * @param axis
     *          may be either X_AXIS or Y_AXIS
     * @return the span the view would like to be rendered into. Typically the
     *         view is told to render into the span that is returned, although
     *         there is no guarantee. The parent may choose to resize or break
     *         the view.
     */
    @Override
    public float getMaximumSpan(int axis) {
      return Integer.MAX_VALUE;
    }

    /**
     * Determines the minimum span for this view along an axis.
     *
     * @param axis
     *          may be either X_AXIS or Y_AXIS
     * @return the span the view would like to be rendered into. Typically the
     *         view is told to render into the span that is returned, although
     *         there is no guarantee. The parent may choose to resize or break
     *         the view.
     */
    @Override
    public float getMinimumSpan(int axis) {
      return view.getMinimumSpan(axis);
    }

    /**
     * Determines the preferred span for this view along an axis.
     *
     * @param axis
     *          may be either X_AXIS or Y_AXIS
     * @return the span the view would like to be rendered into. Typically the
     *         view is told to render into the span that is returned, although
     *         there is no guarantee. The parent may choose to resize or break
     *         the view.
     */
    @Override
    public float getPreferredSpan(int axis) {
      if (axis == X_AXIS) {
        // width currently laid out to
        return width;
      }

      return view.getPreferredSpan(axis);
    }

    /**
     * Returns the starting offset into the model for this view.
     *
     * @return the starting offset
     */
    @Override
    public int getStartOffset() {
      return view.getStartOffset();
    }

    /**
     * Gets the n-th view in this container.
     *
     * @param n
     *          the number of the view to get
     * @return the view
     */
    @Override
    public View getView(int n) {
      return view;
    }

    /**
     * Returns the number of views in this view. Since this view simply wraps
     * the root of the view hierarchy it has exactly one child.
     *
     * @return the number of views
     * @see #getView
     */
    @Override
    public int getViewCount() {
      return 1;
    }

    /**
     * Fetches the factory to be used for building the various view fragments
     * that make up the view that represents the model. This is what determines
     * how the model will be represented. This is implemented to fetch the
     * factory provided by the associated EditorKit.
     *
     * @return the factory
     */
    @Override
    public ViewFactory getViewFactory() {
      return factory;
    }
  }
}
