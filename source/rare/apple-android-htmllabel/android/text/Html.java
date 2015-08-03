/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.text;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.AlignmentSpan.Alignment;
import android.text.style.CSSStyleSpan;
import android.text.style.CenterSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * This class processes HTML strings into displayable styled text. Not all HTML tags are supported.
 */
public class Html {
  private Html() {}

  /**
   * Returns displayable styled text from the provided HTML string. Any &lt;img&gt; tags in the HTML
   * will display as a generic replacement image which your program can then go through and replace
   * with real images.
   *
   * <p>This uses TagSoup to handle real HTML, including all of the brokenness found in the wild.
   */
  public static Spanned fromHtml(String source) {
    return fromHtml(source, null, null);
  }

  /**
   * Returns displayable styled text from the provided HTML string. Any &lt;img&gt; tags in the HTML
   * will use the specified ImageGetter to request a representation of the image (use null if you
   * don't want this) and the specified TagHandler to handle unknown tags (specify null if you don't
   * want this).
   *
   * <p>This uses TagSoup to handle real HTML, including all of the brokenness found in the wild.
   */
  public static Spanned fromHtml(String source, ImageGetter imageGetter, TagHandler tagHandler) {
    Parser parser = new Parser();

    try {
      parser.setProperty(Parser.schemaProperty, HtmlParser.schema);
    } catch(org.xml.sax.SAXNotRecognizedException e) {

      // Should not happen.
      throw new RuntimeException(e);
    } catch(org.xml.sax.SAXNotSupportedException e) {

      // Should not happen.
      throw new RuntimeException(e);
    }

    HtmlToSpannedConverter converter = new HtmlToSpannedConverter(source, imageGetter, tagHandler, parser);

    Spanned s=converter.convert();
    converter.dispose();
    return s;
  }

  /**
   * Returns an HTML representation of the provided Spanned text.
   */
  public static String toHtml(Spanned text) {
    StringBuilder out = new StringBuilder();

    withinHtml(out, text);

    return out.toString();
  }

  /**
   * Returns an Node representation of the provided Spanned text.
   */
  public static Node toNode(Spanned text) {
    return toNode(text, null);
  }

  /**
   * Returns an Node representation of the provided Spanned text.
   */
  public static Node toNode(Spanned text, iNodeBuilder nodeBuilder) {
    Node root = new Node();

    if (nodeBuilder == null) {
      withinHtml(root, text);
    } else {
      nodeBuilder.withinHtml(root, text);
    }

    return root;
  }

  private static void withinBlockquote(Node out, Spanned text, int start, int end) {
    int next;

    for (int i = start; i < end; i = next) {
      next = TextUtils.indexOf(text, '\n', i, end);

      if (next < 0) {
        next = end;
      }

      int nl = 0;

      while((next < end) && (text.charAt(next) == '\n')) {
        nl++;
        next++;
      }

      withinParagraph(out, text, i, next - nl, nl, next == end);
    }
  }

  private static void withinBlockquote(StringBuilder out, Spanned text, int start, int end) {
    out.append("<p>");

    int next;

    for (int i = start; i < end; i = next) {
      next = TextUtils.indexOf(text, '\n', i, end);

      if (next < 0) {
        next = end;
      }

      int nl = 0;

      while((next < end) && (text.charAt(next) == '\n')) {
        nl++;
        next++;
      }

      withinParagraph(out, text, i, next - nl, nl, next == end);
    }

    out.append("</p>\n");
  }

  private static void withinDiv(Node out, Spanned text, int start, int end) {
    int  next;
    Node bq;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, QuoteSpan.class);

      QuoteSpan[] quotes = text.getSpans(i, next, QuoteSpan.class);

      bq = out.blockQuote(quotes.length);
      withinBlockquote(bq, text, i, next);
    }
  }

  @SuppressWarnings("unused")
	private static void withinDiv(StringBuilder out, Spanned text, int start, int end) {
    int next;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, QuoteSpan.class);

      QuoteSpan[] quotes = text.getSpans(i, next, QuoteSpan.class);

      for (QuoteSpan quote : quotes) {
        out.append("<blockquote>");
      }

      withinBlockquote(out, text, i, next);

      for (QuoteSpan quote : quotes) {
        out.append("</blockquote>\n");
      }
    }
  }

  private static void withinHtml(Node out, Spanned text) {
    int  len = text.length();
    int  next;
    Node p = out;

    for (int i = 0; i < text.length(); i = next) {
      next = text.nextSpanTransition(i, len, ParagraphStyle.class);

      ParagraphStyle[] style    = text.getSpans(i, next, ParagraphStyle.class);
      String           elements = " ";
      boolean          needDiv  = false;

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof AlignmentSpan) {
          Alignment align = ((AlignmentSpan) style[j]).getAlignment();

          needDiv = true;

          if (align == Alignment.CENTER) {
            elements = "align=\"center\" " + elements;
          } else if (align == Alignment.RIGHT) {
            elements = "align=\"right\" " + elements;
          } else {
            elements = "align=\"left\" " + elements;
          }
        }
      }

      if (needDiv) {
        p = out.paragraph();
      }

      withinDiv(p, text, i, next);
    }
  }

  private static void withinHtml(StringBuilder out, Spanned text) {
    int len = text.length();
    int next;

    for (int i = 0; i < text.length(); i = next) {
      next = text.nextSpanTransition(i, len, ParagraphStyle.class);

      ParagraphStyle[] style    = text.getSpans(i, next, ParagraphStyle.class);
      String           elements = " ";
      boolean          needDiv  = false;

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof AlignmentSpan) {
          Alignment align = ((AlignmentSpan) style[j]).getAlignment();

          needDiv = true;

          if (align == Alignment.CENTER) {
            elements = "align=\"center\" " + elements;
          } else if (align == Alignment.RIGHT) {
            elements = "align=\"right\" " + elements;
          } else {
            elements = "align=\"left\" " + elements;
          }
        }
      }

      if (needDiv) {
        out.append("<div " + elements + ">");
      }

      withinDiv(out, text, i, next);

      if (needDiv) {
        out.append("</div>");
      }
    }
  }

  private static void withinParagraph(Node parent, Spanned text, int start, int end, int nl, boolean last) {
    int  next;
    Node out = null;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, CharacterStyle.class);

      CharacterStyle[] style = text.getSpans(i, next, CharacterStyle.class);

      out = parent.newChild();

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof StyleSpan) {
          int s = ((StyleSpan) style[j]).getStyle();

          if ((s & Typeface.BOLD) != 0) {
            out.append("font-weight:bold");
          }

          if ((s & Typeface.ITALIC) != 0) {
            out.append("font-style:italic");
          }
        }

        if (style[j] instanceof TypefaceSpan) {
          String s = ((TypefaceSpan) style[j]).getFamily();

          if (s.equals("monospace")) {
            out.append("font-family: monospace");
          }
        }

        if (style[j] instanceof CSSStyleSpan) {
          out.append(((CSSStyleSpan) style[j]).getStyle());
        }

        if (style[j] instanceof SuperscriptSpan) {
          out.append("vertical-align:sup");
        }

        if (style[j] instanceof SubscriptSpan) {
          out.append("vertical-align:sub");
        }

        if (style[j] instanceof UnderlineSpan) {
          out.append("text-decoration: underline");
        }

        if (style[j] instanceof CenterSpan) {
          out.append("text-align: center");
        }

        if (style[j] instanceof StrikethroughSpan) {
          out.append("text-decoration: line-through");
        }

        if (style[j] instanceof URLSpan) {
          out.href(((URLSpan) style[j]).getURL());
        }

        if (style[j] instanceof ImageSpan) {
          out.image(((ImageSpan) style[j]).getSource());

          // Don't output the dummy character underlying the image.
          i = next;
        }

        if (style[j] instanceof AbsoluteSizeSpan) {
          out.append("font-size:" + ((AbsoluteSizeSpan) style[j]).getCSSSize());
        }

        if (style[j] instanceof ForegroundColorSpan) {
          out.append("color:" + ((ForegroundColorSpan) style[j]).getForegroundColor());
        }
      }

      withinStyle((out == null)
                  ? parent
                  : out, text, i, next);
    }

    parent.newChild().linefeed = nl;
  }

  private static void withinParagraph(StringBuilder out, Spanned text, int start, int end, int nl, boolean last) {
    int next;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, CharacterStyle.class);

      CharacterStyle[] style = text.getSpans(i, next, CharacterStyle.class);

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof StyleSpan) {
          int s = ((StyleSpan) style[j]).getStyle();

          if ((s & Typeface.BOLD) != 0) {
            out.append("<b>");
          }

          if ((s & Typeface.ITALIC) != 0) {
            out.append("<i>");
          }
        }

        if (style[j] instanceof TypefaceSpan) {
          String s = ((TypefaceSpan) style[j]).getFamily();

          if (s.equals("monospace")) {
            out.append("<tt>");
          }
        }

        if (style[j] instanceof SuperscriptSpan) {
          out.append("<sup>");
        }

        if (style[j] instanceof SubscriptSpan) {
          out.append("<sub>");
        }

        if (style[j] instanceof UnderlineSpan) {
          out.append("<u>");
        }

        if (style[j] instanceof CenterSpan) {
          out.append("<center>");
        }
        if (style[j] instanceof StrikethroughSpan) {
          out.append("<strike>");
        }

        if (style[j] instanceof URLSpan) {
          out.append("<a href=\"");
          out.append(((URLSpan) style[j]).getURL());
          out.append("\">");
        }

        if (style[j] instanceof CSSStyleSpan) {
          out.append("<span  style=\"");
          out.append(((CSSStyleSpan) style[j]).getStyle());
          out.append("\">");
        }

        if (style[j] instanceof ImageSpan) {
          out.append("<img src=\"");
          out.append(((ImageSpan) style[j]).getSource());
          out.append("\">");

          // Don't output the dummy character underlying the image.
          i = next;
        }

        if (style[j] instanceof AbsoluteSizeSpan) {
          out.append("<font size =\"");
          out.append(((AbsoluteSizeSpan) style[j]).getSize() / 6);
          out.append("\">");
        }

        if (style[j] instanceof ForegroundColorSpan) {
          out.append("<font color =\"");
          out.append(((ForegroundColorSpan) style[j]).getForegroundColor());
          out.append("\">");
        }
      }

      withinStyle(out, text, i, next);

      for (int j = style.length - 1; j >= 0; j--) {
        if (style[j] instanceof ForegroundColorSpan) {
          out.append("</font>");
        }

        if (style[j] instanceof AbsoluteSizeSpan) {
          out.append("</font>");
        }

        if (style[j] instanceof URLSpan) {
          out.append("</a>");
        }

        if (style[j] instanceof CSSStyleSpan) {
          out.append("</span>");
        }

        if (style[j] instanceof StrikethroughSpan) {
          out.append("</strike>");
        }

        if (style[j] instanceof UnderlineSpan) {
          out.append("</u>");
        }

        if (style[j] instanceof SubscriptSpan) {
          out.append("</sub>");
        }

        if (style[j] instanceof SuperscriptSpan) {
          out.append("</sup>");
        }

        if (style[j] instanceof CenterSpan) {
          out.append("</center>");
        }
        if (style[j] instanceof TypefaceSpan) {
          String s = ((TypefaceSpan) style[j]).getFamily();

          if (s.equals("monospace")) {
            out.append("</tt>");
          }
        }

        if (style[j] instanceof StyleSpan) {
          int s = ((StyleSpan) style[j]).getStyle();

          if ((s & Typeface.BOLD) != 0) {
            out.append("</b>");
          }

          if ((s & Typeface.ITALIC) != 0) {
            out.append("</i>");
          }
        }
      }
    }

    String p = last
               ? ""
               : "</p>\n<p>";

    if (nl == 1) {
      out.append("<br>\n");
    } else if (nl == 2) {
      out.append(p);
    } else {
      for (int i = 2; i < nl; i++) {
        out.append("<br>");
      }

      out.append(p);
    }
  }

  private static void withinStyle(Node out, Spanned text, int start, int end) {
    if (out.text != null) {
      out.text += text.subSequence(start, end);
    } else {
      out.text = text.subSequence(start, end).toString();
    }
  }

  private static void withinStyle(StringBuilder out, Spanned text, int start, int end) {
    for (int i = start; i < end; i++) {
      char c = text.charAt(i);

      if (c == '<') {
        out.append("&lt;");
      } else if (c == '>') {
        out.append("&gt;");
      } else if (c == '&') {
        out.append("&amp;");
      } else if ((c > 0x7E) || (c < ' ')) {
        out.append("&#" + ((int) c) + ";");
      } else if (c == ' ') {
        while((i + 1 < end) && (text.charAt(i + 1) == ' ')) {
          out.append("&nbsp;");
          i++;
        }

        out.append(' ');
      } else {
        out.append(c);
      }
    }
  }

  /**
   * Retrieves images for HTML &lt;img&gt; tags.
   */
  public static interface ImageGetter {

    /**
     * This methos is called when the HTML parser encounters an &lt;img&gt; tag. The
     * <code>source</code> argument is the string from the "src" attribute; the return value should
     * be a Drawable representation of the image or
     * <code>null</code> for a generic replacement image. Make sure you call setBounds() on your
     * Drawable if it doesn't already have its bounds set.
     */
    public Object getDrawable(String source);
  }


  /**
   * Is notified when HTML tags are encountered that the parser does not know how to interpret.
   */
  public static interface TagHandler {

    /**
     * This method will be called whenn the HTML parser encounters a tag that it does not know how
     * to interpret.
     */
    public void handleTag(boolean opening, String tag, SpannableStringBuilder output, Attributes attributes);
  }


  public static class Node {
    public ArrayList<Node> children;
    public boolean         container;
    public String          href;
    public String          image;
    public int             indent;
    public int             linefeed;
    public StringBuilder   style;
    public String          text;
    
    public Node append(String s) {
      if ((s == null) || (s.length() == 0)) {
        return this;
      }

      if (style == null) {
        style = new StringBuilder();
      }

      style.append(s).append(';');

      return this;
    }

    public Node blockQuote(int indent) {
      Node node = newChild();

      container   = true;
      node.indent = indent;

      return node;
    }

    public Node close(String s) {
      if (style == null) {
        return this;
      }

      int n = style.lastIndexOf(s);

      if (n == -1) {
        return this;
      }

      Node node = new Node();

      node.style = new StringBuilder();
      node.style.append(style, 0, n);
      n += s.length() + 1;

      if (n < style.length()) {
        node.style.append(style, n, style.length() - n);
      }

      return node;
    }

    public Node href(String href) {
      this.href = href;

      return this;
    }

    public Node image(String href) {
      image = href;

      return this;
    }

    public Node newChild() {
      Node node = new Node();

      if (style != null) {
        node.style = new StringBuilder(style);
      }

      if (children == null) {
        children = new ArrayList<Node>();
      }

      children.add(node);

      return node;
    }

    public Node paragraph() {
      container = true;

      Node node = newChild();

      return node;
    }

    @Override
    public String toString() {
      return toString(new StringBuilder()).toString();
    }

    public StringBuilder toString(StringBuilder sb) {
      if ((text == null) && (image == null)) {
        return sb;
      }

      if (href != null) {
        sb.append("<a href=\"");
        sb.append(href).append('"');
      } else {
        sb.append("<span");
      }

      if (style != null) {
        sb.append(" style=\"").append(style);
        sb.setCharAt(sb.length() - 1, '"');
      }

      sb.append('>');

      if (text != null) {
        sb.append(text);
      }

      if (href != null) {
        sb.append("</a>");
      } else {
        sb.append("</span>");
      }

      return sb;
    }
  }


  public static class Typeface {
    public final static int BOLD   = 1;
    public final static int ITALIC = 2;
  }


  /**
   * Lazy initialization holder for HTML parser. This class will a) be preloaded by the zygote, or
   * b) not loaded until absolutely necessary.
   */
  private static class HtmlParser {
    private static final HTMLSchema schema = new HTMLSchema();
  }
}


class HtmlToSpannedConverter implements ContentHandler {
  private static final float[]            HEADER_SIZES = {
    1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1f,
  };
  private Html.ImageGetter                mImageGetter;
  private XMLReader                       mReader;
  private String                          mSource;
  private SpannableStringBuilder          mSpannableStringBuilder;
  private Html.TagHandler                 mTagHandler;

  public HtmlToSpannedConverter(String source, Html.ImageGetter imageGetter, Html.TagHandler tagHandler,
                                Parser parser) {
    mSource                 = source;
    mSpannableStringBuilder = new SpannableStringBuilder();
    mImageGetter            = imageGetter;
    mTagHandler             = tagHandler;
    mReader                 = parser;
  }
  
  public void dispose() {
  	mSource=null;
  	mTagHandler=null;
  	mSpannableStringBuilder=null;
  	mReader=null;
  	mImageGetter=null;
  }
  
  @Override
  public void characters(char ch[], int start, int length) throws SAXException {
    StringBuilder sb = new StringBuilder();

    /*
     * Ignore whitespace that immediately follows other whitespace; newlines count as spaces.
     */
    for (int i = 0; i < length; i++) {
      char c = ch[i + start];

      if ((c == ' ') || (c == '\n')) {
        char pred;
        int  len = sb.length();

        if (len == 0) {
          len = mSpannableStringBuilder.length();

          if (len == 0) {
            pred = '\n';
          } else {
            pred = mSpannableStringBuilder.charAt(len - 1);
          }
        } else {
          pred = sb.charAt(len - 1);
        }

        if ((pred != ' ') && (pred != '\n')) {
          sb.append(' ');
        }
      } else {
        sb.append(c);
      }
    }

    mSpannableStringBuilder.append(sb);
  }

  public Spanned convert() {
    mReader.setContentHandler(this);

    try {
      mReader.parse(new InputSource(new StringReader(mSource)));
    } catch(IOException e) {

      // We are reading from a string. There should not be IO problems.
      throw new RuntimeException(e);
    } catch(SAXException e) {

      // TagSoup doesn't throw parse exceptions.
      throw new RuntimeException(e);
    }

    // Fix flags and range for paragraph-type markup.
    Object[] obj = mSpannableStringBuilder.getSpans(0, mSpannableStringBuilder.length(), ParagraphStyle.class);

    for (int i = 0; i < obj.length; i++) {
      int start = mSpannableStringBuilder.getSpanStart(obj[i]);
      int end   = mSpannableStringBuilder.getSpanEnd(obj[i]);

      // If the last line of the range is blank, back off by one.
      if (end - 2 >= 0) {
        if ((mSpannableStringBuilder.charAt(end - 1) == '\n') && (mSpannableStringBuilder.charAt(end - 2) == '\n')) {
          end--;
        }
      }

      if (end == start) {
        mSpannableStringBuilder.removeSpan(obj[i]);
      } else {
        mSpannableStringBuilder.setSpan(obj[i], start, end, Spannable.SPAN_PARAGRAPH);
      }
    }

    return mSpannableStringBuilder;
  }

  @Override
  public void endDocument() throws SAXException {}

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    handleEndTag(localName);
  }

  @Override
  public void endPrefixMapping(String prefix) throws SAXException {}

  @Override
  public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {}

  @Override
  public void processingInstruction(String target, String data) throws SAXException {}

  @Override
  public void skippedEntity(String name) throws SAXException {}

  @Override
  public void startDocument() throws SAXException {}

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    handleStartTag(localName, attributes);
  }

  @Override
  public void startPrefixMapping(String prefix, String uri) throws SAXException {}

  @Override
  public void setDocumentLocator(Locator locator) {}

  
  private static void end(SpannableStringBuilder text, Class kind, Object repl) {
    int    len   = text.length();
    Object obj   = getLast(text, kind);
    int    where = text.getSpanStart(obj);

    text.removeSpan(obj);

    if (where != len) {
      text.setSpan(repl, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    return;
  }

  private static void endA(SpannableStringBuilder text) {
    int    len   = text.length();
    Object obj   = getLast(text, Href.class);
    int    where = text.getSpanStart(obj);

    text.removeSpan(obj);

    if (where != len) {
      Href h = (Href) obj;

      if (h.mHref != null) {
        text.setSpan(new URLSpan(h.mHref), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }

  private static void endFont(SpannableStringBuilder text) {
    int    len   = text.length();
    Object obj   = getLast(text, Font.class);
    int    where = text.getSpanStart(obj);

    text.removeSpan(obj);

    if (where != len) {
      Font f = (Font) obj;

      if (!TextUtils.isEmpty(f.mColor)) {
        text.setSpan(new ForegroundColorSpan(f.mColor), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }

      if (f.mFace != null) {
        text.setSpan(new TypefaceSpan(f.mFace), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }

  private static void endHeader(SpannableStringBuilder text) {
    int    len   = text.length();
    Object obj   = getLast(text, Header.class);
    int    where = text.getSpanStart(obj);

    text.removeSpan(obj);

    // Back off not to change only the text, not the blank line.
    while((len > where) && (text.charAt(len - 1) == '\n')) {
      len--;
    }

    if (where != len) {
      Header h = (Header) obj;

      text.setSpan(new RelativeSizeSpan(HEADER_SIZES[h.mLevel]), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      text.setSpan(new StyleSpan(Font.BOLD), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
  }

  private static void endSpan(SpannableStringBuilder text) {
    int    len   = text.length();
    Object obj   = getLast(text, Style.class);
    int    where = text.getSpanStart(obj);

    text.removeSpan(obj);

    if (where != len) {
      Style s = (Style) obj;

      if (s.mStyle != null) {
        text.setSpan(new CSSStyleSpan(s.mStyle), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }

  private static void handleBr(SpannableStringBuilder text) {
    text.append('\n');
  }

  private void handleEndTag(String tag) {
    if (tag.equalsIgnoreCase("br")) {
      handleBr(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("p")) {
      handleP(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("div")) {
      handleP(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("em")) {
      end(mSpannableStringBuilder, Bold.class, new StyleSpan(Font.BOLD));
    } else if (tag.equalsIgnoreCase("b")) {
      end(mSpannableStringBuilder, Bold.class, new StyleSpan(Font.BOLD));
    } else if (tag.equalsIgnoreCase("strong")) {
      end(mSpannableStringBuilder, Italic.class, new StyleSpan(Font.ITALIC));
    } else if (tag.equalsIgnoreCase("cite")) {
      end(mSpannableStringBuilder, Italic.class, new StyleSpan(Font.ITALIC));
    } else if (tag.equalsIgnoreCase("dfn")) {
      end(mSpannableStringBuilder, Italic.class, new StyleSpan(Font.ITALIC));
    } else if (tag.equalsIgnoreCase("i")) {
      end(mSpannableStringBuilder, Italic.class, new StyleSpan(Font.ITALIC));
    } else if (tag.equalsIgnoreCase("big")) {
      end(mSpannableStringBuilder, Big.class, new RelativeSizeSpan(1.25f));
    } else if (tag.equalsIgnoreCase("small")) {
      end(mSpannableStringBuilder, Small.class, new RelativeSizeSpan(0.8f));
    } else if (tag.equalsIgnoreCase("font")) {
      endFont(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("blockquote")) {
      handleP(mSpannableStringBuilder);
      end(mSpannableStringBuilder, Blockquote.class, new QuoteSpan());
    } else if (tag.equalsIgnoreCase("tt")) {
      end(mSpannableStringBuilder, Monospace.class, new TypefaceSpan("monospace"));
    } else if (tag.equalsIgnoreCase("a")) {
      endA(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("span")) {
      endSpan(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("u")) {
      end(mSpannableStringBuilder, Underline.class, new UnderlineSpan());
    } else if (tag.equalsIgnoreCase("center")) {
      end(mSpannableStringBuilder, Center.class, new CenterSpan());
    } else if (tag.equalsIgnoreCase("sup")) {
      end(mSpannableStringBuilder, Super.class, new SuperscriptSpan());
    } else if (tag.equalsIgnoreCase("sub")) {
      end(mSpannableStringBuilder, Sub.class, new SubscriptSpan());
    } else if ((tag.length() == 2) && (Character.toLowerCase(tag.charAt(0)) == 'h') && (tag.charAt(1) >= '1')
               && (tag.charAt(1) <= '6')) {
      handleP(mSpannableStringBuilder);
      endHeader(mSpannableStringBuilder);
    } else if (mTagHandler != null) {
      mTagHandler.handleTag(false, tag, mSpannableStringBuilder, null);
    }
  }

  private static void handleP(SpannableStringBuilder text) {
    int len = text.length();

    if ((len >= 1) && (text.charAt(len - 1) == '\n')) {
      if ((len >= 2) && (text.charAt(len - 2) == '\n')) {
        return;
      }

      text.append('\n');

      return;
    }

    if (len != 0) {
      text.append('\n');
      text.append('\n');
    }
  }

  private void handleStartTag(String tag, Attributes attributes) {
    if (tag.equalsIgnoreCase("br")) {

      // We don't need to handle this. TagSoup will ensure that there's a </br> for each <br>
      // so we can safely emite the linebreaks when we handle the close tag.
    } else if (tag.equalsIgnoreCase("p")) {
      handleP(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("div")) {
      handleP(mSpannableStringBuilder);
    } else if (tag.equalsIgnoreCase("em")) {
      start(mSpannableStringBuilder, newBold());
    } else if (tag.equalsIgnoreCase("b")) {
      start(mSpannableStringBuilder, newBold());
    } else if (tag.equalsIgnoreCase("strong")) {
      start(mSpannableStringBuilder, newItalic());
    } else if (tag.equalsIgnoreCase("cite")) {
      start(mSpannableStringBuilder, newItalic());
    } else if (tag.equalsIgnoreCase("dfn")) {
      start(mSpannableStringBuilder, newItalic());
    } else if (tag.equalsIgnoreCase("i")) {
      start(mSpannableStringBuilder, newItalic());
    } else if (tag.equalsIgnoreCase("big")) {
      start(mSpannableStringBuilder, newBig());
    } else if (tag.equalsIgnoreCase("small")) {
      start(mSpannableStringBuilder, newSmall());
    } else if (tag.equalsIgnoreCase("font")) {
      startFont(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("blockquote")) {
      handleP(mSpannableStringBuilder);
      start(mSpannableStringBuilder, newBlockquote());
    } else if (tag.equalsIgnoreCase("tt")) {
      start(mSpannableStringBuilder, newMonospace());
    } else if (tag.equalsIgnoreCase("a")) {
      startA(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("span")) {
      startSpan(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("u")) {
      start(mSpannableStringBuilder, newUnderline());
    } else if (tag.equalsIgnoreCase("center")) {
      start(mSpannableStringBuilder, newCenter());
    } else if (tag.equalsIgnoreCase("sup")) {
      start(mSpannableStringBuilder, newSuper());
    } else if (tag.equalsIgnoreCase("sub")) {
      start(mSpannableStringBuilder, newSub());
    } else if ((tag.length() == 2) && (Character.toLowerCase(tag.charAt(0)) == 'h') && (tag.charAt(1) >= '1')
               && (tag.charAt(1) <= '6')) {
      handleP(mSpannableStringBuilder);
      start(mSpannableStringBuilder, new Header(tag.charAt(1) - '1'));
    } else if (tag.equalsIgnoreCase("img")) {
      startImg(mSpannableStringBuilder, attributes, mImageGetter);
    } else if (mTagHandler != null) {
      mTagHandler.handleTag(true, tag, mSpannableStringBuilder, attributes);
    }
  }

  private static void start(SpannableStringBuilder text, Object mark) {
    int len = text.length();

    text.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);
  }

  private static void startA(SpannableStringBuilder text, Attributes attributes) {
    String href = attributes.getValue("", "href");
    int    len  = text.length();

    text.setSpan(new Href(href), len, len, Spannable.SPAN_MARK_MARK);
  }

  private static void startFont(SpannableStringBuilder text, Attributes attributes) {
    String color = attributes.getValue("", "color");
    String face  = attributes.getValue("", "face");
    int    len   = text.length();

    text.setSpan(new Font(color, face), len, len, Spannable.SPAN_MARK_MARK);
  }

  private static void startImg(SpannableStringBuilder text, Attributes attributes, Html.ImageGetter img) {
    String src = attributes.getValue("", "src");
    int    len = text.length();

    text.append("\uFFFC");
    text.setSpan(new ImageSpan(src), len, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
  }

  private static void startSpan(SpannableStringBuilder text, Attributes attributes) {
    String style = attributes.getValue("", "style");
    int    len   = text.length();

    text.setSpan(new Style(style), len, len, Spannable.SPAN_MARK_MARK);
  }

 
  private static Object getLast(Spanned text, Class kind) {

    /*
     * This knows that the last returned object from getSpans() will be the most recently added.
     */
    Object[] objs = text.getSpans(0, text.length(), kind);

    if (objs.length == 0) {
      return null;
    } else {
      return objs[objs.length - 1];
    }
  }

  private static class Big {}


  private static class Blockquote {}


  private static class Bold {}


  private static class Font {
    public static int BOLD   = 1;
    public static int ITALIC = 2;
    public String     mColor;
    public String     mFace;

    public Font(String color, String face) {
      mColor = color;
      mFace  = face;
    }
  }


  private static class Header {
    private int mLevel;

    public Header(int level) {
      mLevel = level;
    }
  }


  private static class Href {
    public String mHref;

    public Href(String href) {
      mHref = href;
    }
  }


  private static class Italic {}


  private static class Monospace {}


  private static class Small {}


  private static class Style {
    public String mStyle;

    public Style(String style) {
      mStyle = style;
    }
  }


  private static class Sub {}


  private static class Super {}


  private static class Underline {}
  private static class Center {}
  private static Center center;
  private static Sub sub;
  private static Super ssuper;
  private static Bold bold;
  private static Italic italic;
  private static Small  small;
  private static Big big;
  private static Blockquote blockquote;
  private static Monospace monospace;
  private static Underline underline;
  private static Center newCenter() {if(center==null)  {center=new Center();} return center;}
  private static Sub newSub() {if(sub==null)  {sub=new Sub();} return sub;}
  private static Super newSuper() {if(ssuper==null)  {ssuper=new Super();} return ssuper;}
  private static Bold newBold() {if(bold==null)  {bold=new Bold();} return bold;}
  private static Italic newItalic() {if(italic==null)  {italic=new Italic();} return italic;}
  private static Small  newSmall() {if(small==null)  {small=new Small();} return small;}
  private static Big newBig() {if(big==null)  {big=new Big();} return big;}
  private static Blockquote newBlockquote() {if(blockquote==null)  {blockquote=new Blockquote();} return blockquote;}
  private static Monospace newMonospace() {if(monospace==null)  {monospace=new Monospace();} return monospace;}
  private static Underline newUnderline() {if(underline==null)  {underline=new Underline();} return underline;}
  

}
