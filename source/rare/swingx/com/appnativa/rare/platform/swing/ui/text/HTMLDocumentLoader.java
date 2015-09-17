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
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.util.iURLResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URLConnection;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Don DeCoteau
 */
public class HTMLDocumentLoader {

  /**  */
  protected HTMLEditorKit kit;

  /**  */
  protected static HTMLEditorKit.Parser parser;

  public HTMLDocumentLoader(iURLResolver ur) {
    kit = new HTMLEditorKitEx(ur);
  }

  // Methods that allow customization of the parser and the callback

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public synchronized HTMLEditorKit.Parser getParser() {
    if (parser == null) {
      try {
        Class c = Class.forName("javax.swing.text.html.parser.ParserDelegator");

        parser = (HTMLEditorKit.Parser) c.newInstance();
      } catch(Throwable e) {
        Platform.ignoreException(null, e);
      }
    }

    return parser;
  }

  public synchronized HTMLEditorKit.ParserCallback getParserCallback(HTMLDocument doc) {
    return doc.getReader(0);
  }

  public HTMLDocument loadDocument(iURLConnection conn) throws IOException {
    return loadDocument(conn, null);
  }

  public HTMLDocument loadDocument(Reader reader) throws IOException {
    return loadDocument((HTMLDocument) kit.createDefaultDocument(), reader);
  }

  public HTMLDocument loadDocument(URLConnection conn) throws IOException {
    return loadDocument(conn, null);
  }

  public HTMLDocument loadDocument(HTMLDocument doc, Reader reader) throws IOException {
    try {
      // Remove any document content
      doc.remove(0, doc.getLength());

      HTMLEditorKit.Parser         parser     = getParser();
      HTMLEditorKit.ParserCallback htmlReader = getParserCallback(doc);

      parser.parse(reader, htmlReader, true);
      htmlReader.flush();
      reader.close();
    } catch(BadLocationException ex) {
      // Should not happen - throw an IOException
      throw new IOException(ex.getMessage());
    }

    return doc;
  }

  public HTMLDocument loadDocument(iURLConnection conn, String charSet) throws IOException {
    return loadDocument((HTMLDocument) kit.createDefaultDocument(), conn, charSet);
  }

  public HTMLDocument loadDocument(URLConnection conn, String charSet) throws IOException {
    return loadDocument((HTMLDocument) kit.createDefaultDocument(), conn, charSet);
  }

  public HTMLDocument loadDocument(HTMLDocument doc, iURLConnection conn, String charSet) throws IOException {
    doc.putProperty(Document.StreamDescriptionProperty, conn.getURL());

    /*
     * This loop allows the document read to be retried if the character
     * encoding changes during processing.
     */
    InputStream in            = null;
    boolean     ignoreCharSet = true;

    for (;;) {
      try {
        // Remove any document content
        doc.remove(0, doc.getLength());
        in = conn.getInputStream();

        Reader                       reader     = (charSet == null)
                ? new InputStreamReader(in)
                : new InputStreamReader(in, charSet);
        HTMLEditorKit.Parser         parser     = getParser();
        HTMLEditorKit.ParserCallback htmlReader = getParserCallback(doc);

        parser.parse(reader, htmlReader, ignoreCharSet);
        htmlReader.flush();

        // All done
        break;
      } catch(BadLocationException ex) {
        // Should not happen - throw an IOException
        throw new IOException(ex.getMessage());
      } catch(ChangedCharSetException e) {
        // The character set has changed - restart
        charSet = getNewCharSet(e);
        // Prevent recursion by suppressing further exceptions
        ignoreCharSet = true;

        // Close original input stream
        if (in != null) {
          in.close();
        }
        // Continue the loop to read with the correct encoding
      }
    }

    return doc;
  }

  public HTMLDocument loadDocument(HTMLDocument doc, URLConnection conn, String charSet) throws IOException {
    doc.putProperty(Document.StreamDescriptionProperty, conn.getURL());

    /*
     * This loop allows the document read to be retried if the character
     * encoding changes during processing.
     */
    InputStream in            = null;
    boolean     ignoreCharSet = true;

    for (;;) {
      try {
        // Remove any document content
        doc.remove(0, doc.getLength());
        in = conn.getInputStream();

        Reader                       reader     = (charSet == null)
                ? new InputStreamReader(in)
                : new InputStreamReader(in, charSet);
        HTMLEditorKit.Parser         parser     = getParser();
        HTMLEditorKit.ParserCallback htmlReader = getParserCallback(doc);

        parser.parse(reader, htmlReader, ignoreCharSet);
        htmlReader.flush();

        // All done
        break;
      } catch(BadLocationException ex) {
        // Should not happen - throw an IOException
        throw new IOException(ex.getMessage());
      } catch(ChangedCharSetException e) {
        // The character set has changed - restart
        charSet = getNewCharSet(e);
        // Prevent recursion by suppressing further exceptions
        ignoreCharSet = true;

        // Close original input stream
        if (in != null) {
          in.close();
        }
        // Continue the loop to read with the correct encoding
      }
    }

    return doc;
  }

  protected String getNewCharSet(ChangedCharSetException e) {
    String spec = e.getCharSetSpec();

    if (e.keyEqualsCharSet()) {
      // The event contains the new CharSet
      return spec;
    }

    // The event contains the content type
    // plus ";" plus qualifiers which may
    // contain a "charset" directive. First
    // remove the content type.
    int index = spec.indexOf(";");

    if (index != -1) {
      spec = spec.substring(index + 1);
    }

    // Force the string to lower case
    spec = spec.toLowerCase(Locale.ENGLISH);

    StringTokenizer st           = new StringTokenizer(spec, " \t=", true);
    boolean         foundCharSet = false;
    boolean         foundEquals  = false;

    while(st.hasMoreTokens()) {
      String token = st.nextToken();

      if (token.equals(" ") || token.equals("\t")) {
        continue;
      }

      if ((foundCharSet == false) && (foundEquals == false) && token.equals("charset")) {
        foundCharSet = true;

        continue;
      } else if ((foundEquals == false) && token.equals("=")) {
        foundEquals = true;

        continue;
      } else if ((foundEquals == true) && (foundCharSet == true)) {
        return token;
      }

      // Not recognized
      foundCharSet = false;
      foundEquals  = false;
    }

    // No charset found - return a guess
    return "8859_1";
  }
}
