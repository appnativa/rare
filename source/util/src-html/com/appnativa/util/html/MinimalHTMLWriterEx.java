/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util.html;

import java.io.IOException;
import java.io.Writer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.MinimalHTMLWriter;

/**
 *
 * @author Don DeCoteau
 */
public class MinimalHTMLWriterEx extends MinimalHTMLWriter {
  private static final String courier        = "font-family: Courier";
  private static final String monospace      = "font-family: monospace";
  private static Pattern      courierPattern = Pattern.compile("font-family: Courier", Pattern.LITERAL);
  protected boolean           replaceEntties = false;
  private String              footer;
  private String              header;
  private boolean             replaceCourier;

  /**
   * Temporary buffer.
   */
  private char[] tempChars;
  private String title;

  public MinimalHTMLWriterEx(Writer w, StyledDocument doc) {
    super(w, doc);
  }

  public MinimalHTMLWriterEx(Writer w, StyledDocument doc, int pos, int len) {
    super(w, doc, pos, len);
  }

  /**
   * @param footer the footer to set
   */
  public void setFooter(String footer) {
    this.footer = footer;
  }

  /**
   * @param header the header to set
   */
  public void setHeader(String header) {
    this.header = header;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the footer
   */
  public String getFooter() {
    return footer;
  }

  /**
   * @return the header
   */
  public String getHeader() {
    return header;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   *   This directly invokes super's <code>output</code> after converting
   *   <code>string</code> to a char[].
   */
  protected void output(String string) throws IOException {
    int length = string.length();

    if ((tempChars == null) || (tempChars.length < length)) {
      tempChars = new char[length];
    }

    string.getChars(0, length, tempChars, 0);
    super.output(tempChars, 0, length);
  }

  /**
   *    This method is overriden to map any character entities, such as
   *    &lt; to &amp;lt;. <code>super.output</code> will be invoked to
   *    write the content.
   *    @since 1.3
   */
  protected void output(char[] chars, int start, int length) throws IOException {
    if (!replaceEntties) {
      super.output(chars, start, length);

      return;
    }

    int last = start;

    length += start;

    for (int counter = start; counter < length; counter++) {
      // This will change, we need better support character level
      // entities.
      switch(chars[counter]) {
        // Character level entities.
        case '<' :
          if (counter > last) {
            super.output(chars, last, counter - last);
          }

          last = counter + 1;
          output("&lt;");

          break;

        case '>' :
          if (counter > last) {
            super.output(chars, last, counter - last);
          }

          last = counter + 1;
          output("&gt;");

          break;

        case '&' :
          if (counter > last) {
            super.output(chars, last, counter - last);
          }

          last = counter + 1;
          output("&amp;");

          break;

        case '"' :
          if (counter > last) {
            super.output(chars, last, counter - last);
          }

          last = counter + 1;
          output("&quot;");

          break;

        // Special characters
        case '\n' :
        case '\t' :
        case '\r' :
          break;

        default :
          if ((chars[counter] < ' ') || (chars[counter] > 127)) {
            if (counter > last) {
              super.output(chars, last, counter - last);
            }

            last = counter + 1;
            // If the character is outside of ascii, write the
            // numeric value.
            output("&#");
            output(String.valueOf((int) chars[counter]));
            output(";");
          }

          break;
      }
    }

    if (last < length) {
      super.output(chars, last, length - last);
    }
  }

  @Override
  protected void text(Element elem) throws IOException, BadLocationException {
    String contentStr = getText(elem);

    if ((contentStr.length() > 0) && (contentStr.charAt(contentStr.length() - 1) == NEWLINE)) {
      contentStr = contentStr.substring(0, contentStr.length() - 1);
    }

    if (contentStr.length() > 0) {
      replaceEntties = true;
      write(contentStr);
      replaceEntties = false;
    }
  }

  @Override
  protected void writeEndTag(String endTag) throws IOException {
    if ((footer != null) && (endTag == "</body>")) {
      output(footer);
    }

    super.writeEndTag(endTag);
  }

  @Override
  protected void writeNonHTMLAttributes(AttributeSet attr) throws IOException {
    replaceCourier = true;
    super.writeNonHTMLAttributes(attr);
    replaceCourier = false;
  }

  protected void writeStartTag(String tag) throws IOException {
    if (replaceCourier) {
      int n = tag.indexOf(courier);

      if (n != -1) {
        tag = courierPattern.matcher(tag).replaceAll(Matcher.quoteReplacement(monospace));
      }
    }

    super.writeStartTag(tag);

    if ((header != null) && (tag == "<body>")) {
      output(header);

      return;
    }

    if ((title != null) && (tag == "<title>")) {
      output("<title>");
      output(title);
      output("</title>");

      return;
    }
  }
}
