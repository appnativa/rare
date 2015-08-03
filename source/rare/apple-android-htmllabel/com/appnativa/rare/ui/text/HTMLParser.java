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

import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.Node;
import android.text.Html.TagHandler;
import android.text.Html.Typeface;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.iNodeBuilder;
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
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;

import com.google.j2objc.annotations.Weak;
/*-[
#import <CoreText/CoreText.h>
 ]-*/

import java.util.ArrayList;

public class HTMLParser implements iNodeBuilder {
  private static HTMLParser _instance = new HTMLParser();

  public HTMLParser() {}

  /**
   * Returns an HTMLCharSequence that can display text wit a font that
   * has strikethrough of underline
   */
  public static HTMLCharSequence fromFont(String source, UIFont font) {
    Object astring = createAttributedString(source);

    font.addToAttributedString(astring, 0, source.length());

    return new HTMLCharSequence(source, astring);
  }

  /**
   * Returns an HTMLCharSequence that can display text containing simple HTML markup
   */
  public static HTMLCharSequence fromHtml(String source, StringBuilder plaintTextOutput, UIFont base) {
    return fromHtml(source, null, plaintTextOutput, base);
  }

  /**
   * Returns a pane that can display text containing simple HTML markup If the
   * <code>plaintTextOutput</code> is set then hte plain text of the string is
   * appended to the buffer.
   */
  public static HTMLCharSequence fromHtml(String source, TagHandler tagHandler, StringBuilder plaintTextOutput,
          UIFont base) {
    if (source.startsWith("<html>")) {
      if (source.endsWith("</html>")) {
        source = source.substring(6, source.length() - 7);
      } else {
        source = source.substring(6);
      }
    }

    if (base == null) {
      base = FontUtils.getDefaultFont();
    }

    ANode node = new ANode();

    node.font = base;

    Spanned spanned = Html.fromHtml(source, null, tagHandler);

    _instance.withinHtml(node, spanned);

    if (plaintTextOutput == null) {
      plaintTextOutput = new StringBuilder();
    }

    nodeToString(node, plaintTextOutput);

    String s       = plaintTextOutput.toString();
    Object astring = createAttributedString(s);

    updateAttributes(node, astring);

    if (spanned != null) {
      spanned.dispose();
    }

    return new HTMLCharSequence(s, astring);
  }

  @Override
  public void withinHtml(Node out, Spanned text) {
    int   len = text.length();
    int   next;
    ANode p = (ANode) out;

    for (int i = 0; i < text.length(); i = next) {
      next = text.nextSpanTransition(i, len, ParagraphStyle.class);

      ParagraphStyle[] style   = text.getSpans(i, next, ParagraphStyle.class);
      boolean          needDiv = false;

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof AlignmentSpan) {
          Alignment align = ((AlignmentSpan) style[j]).getAlignment();

          needDiv = true;

          if (align == Alignment.CENTER) {
            p.alignment = 2;
          } else if (align == Alignment.RIGHT) {
            p.alignment = 3;
          } else {
            p.alignment = 1;
          }
        }
      }

      if (needDiv) {
        p = (ANode) out.paragraph();
      }

      withinDiv(p, text, i, next);
    }
  }

  public String getPlainText(String source) {
    return getPlainText(source, null, null, new StringBuilder()).toString();
  }

  public static StringBuilder getPlainText(String source, ImageGetter imageGetter, TagHandler tagHandler,
          StringBuilder plaintTextOutput) {
    if (source.startsWith("<html>")) {
      if (source.endsWith("</html>")) {
        source = source.substring(6, source.length() - 7);
      } else {
        source = source.substring(6);
      }
    }

    Spanned spanned = Html.fromHtml(source, imageGetter, tagHandler);
    Node    node    = Html.toNode(spanned, _instance);

    nodeToString((ANode) node, plaintTextOutput);

    if (spanned != null) {
      spanned.dispose();
    }

    return plaintTextOutput;
  }

  static StringBuilder nodeToString(ANode node, StringBuilder sb) {
    node.offset = sb.length();

    if (node.children == null) {
      if (node.text != null) {
        sb.append(node.text);
      }
    } else {
      for (Node n : node.children) {
        nodeToString((ANode) n, sb);
      }
    }

    for (int i = 0; i < node.linefeed; i++) {
      sb.append("\n");
    }

    node.length = sb.length() - node.offset;

    return sb;
  }

  static void updateAttributes(ANode node, Object astring) {
    node.updateAttributedString(astring);

    if (node.children != null) {
      for (Node n : node.children) {
        updateAttributes((ANode) n, astring);
      }
    }
  }

  protected void withinBlockquote(Node out, Spanned text, int start, int end) {
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

  protected void withinDiv(Node out, Spanned text, int start, int end) {
    int  next;
    Node bq;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, QuoteSpan.class);

      QuoteSpan[] quotes = text.getSpans(i, next, QuoteSpan.class);

      bq = out.blockQuote(quotes.length);
      withinBlockquote(bq, text, i, next);
    }
  }

  protected void withinParagraph(Node parent, Spanned text, int start, int end, int nl, boolean last) {
    int   next;
    ANode out = null;

    for (int i = start; i < end; i = next) {
      next = text.nextSpanTransition(i, end, CharacterStyle.class);

      CharacterStyle[] style = text.getSpans(i, next, CharacterStyle.class);

      out = (ANode) parent.newChild();

      for (int j = 0; j < style.length; j++) {
        if (style[j] instanceof StyleSpan) {
          int s = ((StyleSpan) style[j]).getStyle();

          if ((s & Typeface.BOLD) != 0) {
            out.font = out.getFont().deriveBold();
          }

          if ((s & Typeface.ITALIC) != 0) {
            out.font = out.getFont().deriveItalic();
          }
        } else if (style[j] instanceof TypefaceSpan) {
          String s = ((TypefaceSpan) style[j]).getFamily();

          if (s.equals("monospace")) {
            out.font = FontUtils.getMonospacedFont((int) FontUtils.getDefaultFontSize() - 1);
          }
        } else if (style[j] instanceof CSSStyleSpan) {}
        else if (style[j] instanceof SuperscriptSpan) {
          out.subSuperScript = 1;
        } else if (style[j] instanceof SubscriptSpan) {
          out.subSuperScript = -1;
        } else if (style[j] instanceof UnderlineSpan) {
          out.font = out.getFont().deriveUnderline();
        } else if (style[j] instanceof CenterSpan) {
          out.alignment = 2;
        } else if (style[j] instanceof StrikethroughSpan) {
          out.font = out.getFont().deriveStrikethrough();
        } else if (style[j] instanceof URLSpan) {
          out.href(((URLSpan) style[j]).getURL());
        } else if (style[j] instanceof ImageSpan) {
          out.image(((ImageSpan) style[j]).getSource());
          // Don't output the dummy character underlying the image.
          i = next;
        } else if (style[j] instanceof AbsoluteSizeSpan) {
          out.font = out.getFont().deriveFontSize(((AbsoluteSizeSpan) style[j]).getSize(out.getFont().getSize2D()));
        } else if (style[j] instanceof ForegroundColorSpan) {
          String s = ((ForegroundColorSpan) style[j]).getForegroundColor();

          out.color = ColorUtils.getColor(s);
        }
      }

      withinStyle((out == null)
                  ? parent
                  : out, text, i, next);
    }

    parent.newChild().linefeed = nl;
  }

  protected void withinStyle(Node out, Spanned text, int start, int end) {
    if (out.text != null) {
      out.text += text.subSequence(start, end);
    } else {
      out.text = text.subSequence(start, end).toString();
    }
  }

  private static native void addAlignment(Object astring, int val, int offset, int length)    /*-[
    NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
    NSMutableParagraphStyle* p=  [[NSParagraphStyle defaultParagraphStyle]mutableCopy];
#if TARGET_OS_IPHONE
    if(val==1) {
      [p setAlignment:NSTextAlignmentLeft];
    }
    else if(val==2) {
      [p setAlignment:NSTextAlignmentCenter];
    }
    else if(val==3) {
      [p setAlignment:NSTextAlignmentRight];
    }
#else
    if(val==1) {
      [p setAlignment:NSLeftTextAlignment];
    }
    else if(val==2) {
      [p setAlignment:NSCenterTextAlignment];
    }
    else if(val==3) {
      [p setAlignment:NSRightTextAlignment];
    }
#endif
    [s addAttribute: NSParagraphStyleAttributeName
      value: p
      range: NSMakeRange(offset,length)];
  ]-*/
  ;

  private static native void addSubSuperScript(Object astring, int val, int offset, int length)
  /*-[
    NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
#if TARGET_OS_IPHONE
    [s addAttribute: (NSString*)kCTSuperscriptAttributeName
      value: (1 ? @"1" : @"-1" )
      range: NSMakeRange(offset,length)];
#else
    [s addAttribute: NSSuperscriptAttributeName
      value: (1 ? @"1" : @"-1" )
      range: NSMakeRange(offset,length)];
#endif
  ]-*/
  ;

  private static native Object createAttributedString(String text)
  /*-[
    NSMutableAttributedString* s=[NSMutableAttributedString new];
    [s.mutableString appendString: text];
    return s;
  ]-*/
  ;

  static class ANode extends Node {
    public int subSuperScript;
    int        alignment;
    UIColor    color;
    UIFont     font;
    int        length;
    int        offset;
    @Weak
    ANode      parent;

    @Override
    public ANode newChild() {
      ANode node = new ANode();

      if (children == null) {
        children = new ArrayList<Node>();
      }

      children.add(node);
      node.parent = this;

      return node;
    }

    public void updateAttributedString(Object astring) {
      if (length == 0) {
        return;
      }

      if (font != null) {
        font.addToAttributedString(astring, offset, length);
      }

      if (color != null) {
        color.addToAttributedString(astring, offset, length);
      }

      if (subSuperScript != 0) {
        addSubSuperScript(astring, subSuperScript, offset, length);
      }

      if (alignment > 0) {
        addAlignment(astring, alignment, offset, length);
      }
    }

    int textLength() {
      int n = (text == null)
              ? 0
              : text.length();

      if (children != null) {
        for (Node node : children) {
          n += ((ANode) node).textLength();
        }
      }

      return n;
    }

    UIFont getFont() {
      if (font == null) {
        return parent.getFont();
      }

      return font;
    }
  }
}
