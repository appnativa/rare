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

package com.appnativa.util.xml;

import com.appnativa.util.ByteArray;
import com.appnativa.util.CharArray;
import com.appnativa.util.Helper;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
import com.appnativa.util.io.ByteArrayOutputStreamEx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class provides some utilities for dealing with XML documents
 *
 * @author Don DeCoteau
 * @version 2.3
 */
public class XMLUtils {

  /** line separator */
  public static final String lineSeparator = "\r\n";

  /**  */
  private static final char[] amplt = "lt".toCharArray();

  /**  */
  private static final char[] ampgt = "gt".toCharArray();

  /**  */
  private static final char[] ampquot = "quot".toCharArray();

  /**  */
  private static final char[] ampapos = "apos".toCharArray();

  /**  */
  private static final char[] ampamp = "amp".toCharArray();

  /**  */
  private static final char[] padding            = Helper.getPadding();
  private static ThreadLocal  perThreadCharArray = new ThreadLocal() {
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };
  private static DocumentBuilderFactory documentBuilderFactory;

  /**
   * Remove escape sequences from a value
   *
   * @param value  The value to be un-escaped
   *
   * @return   The un-escaped string
   */
  public static String decode(CharArray value) {
    CharArray out = (CharArray) perThreadCharArray.get();

    out._length = 0;

    return decode(value, 0, value._length, out).toString();
  }

  /**
   * Remove escape sequences from a value
   *
   * @param value  The value to be un-escaped
   *
   * @return   The un-escaped string
   */
  public static String decode(String value) {
    CharArray v   = new CharArray(value);
    CharArray out = (CharArray) perThreadCharArray.get();

    out._length = 0;

    return decode(v, 0, v._length, out).toString();
  }

  /**
   * Gets the value of the special escaped XML characters
   *
   * @param ca the character array
   * @param pos    the starting position
   * @param len    the number of characters
   * @return the value of the converted encoding
   */
  public static char decodeCharacter(CharArray ca, int pos, int len) {
    if (ca.A[pos] == '#') {
      int n = 0;

      len--;
      pos++;
      len += pos;

      char c;

      while(pos < len) {
        c = ca.A[pos++];

        if ((c < 48) || (c > 57)) {
          break;
        }

        n *= 10;
        n += (int) (c - 48);
      }

      return (char) n;
    }

    switch(len) {
      case 2 :
        if (ca.regionMatches(false, pos, amplt, 0, 2)) {
          return '<';
        }

        if (ca.regionMatches(false, pos, ampgt, 0, 2)) {
          return '>';
        }

        break;

      case 3 :
        if (ca.regionMatches(false, pos, ampamp, 0, 3)) {
          return '<';
        }

        if (ca.regionMatches(false, pos, ampgt, 0, 3)) {
          return '>';
        }

        break;

      case 4 :
        if (ca.regionMatches(false, pos, ampquot, 0, 4)) {
          return '\"';
        }

        if (ca.regionMatches(false, pos, ampapos, 0, 4)) {
          return '\'';
        }

        break;

      default :
        break;
    }

    return 0;
  }

  public static Document documentFromFile(String file) throws Exception {
    return getDocumentBuilderFactory().newDocumentBuilder().parse(new FileInputStream(file));
  }

  public static Document documentFromHref(String href) throws Exception {
    return getDocumentBuilderFactory().newDocumentBuilder().parse(href);
  }

  public static Document documentFromStream(InputStream in) throws Exception {
    return getDocumentBuilderFactory().newDocumentBuilder().parse(in);
  }

  public static Document documentFromString(String s) throws Exception {
    return getDocumentBuilderFactory().newDocumentBuilder().parse(new InputSource(new StringReader(s)));
  }

  /**
   * Dumps XML attributes out to the specified writer
   *
   * @param out the writer
   * @param attributes the attributes
   * @throws IOException
   */
  public static void dumpAttributes(Writer out, Map attributes) throws IOException {
    if ((attributes == null) || (attributes.isEmpty())) {
      return;
    }

    Iterator  it = attributes.entrySet().iterator();
    Map.Entry me;
    String    value;

    while(it.hasNext()) {
      me    = (Map.Entry) it.next();
      value = (String) me.getValue();

      if (value != null) {
        out.write(' ');
        out.write((String) me.getKey());
        out.write("=\"");
        out.write(value);
        out.write('"');
      }
    }
  }

  /**
   * Checks is the specified node is an Element and if it is
   * the node is cast to an element and returned otherwise a null is returned
   *
   * @param node to convert
   * @return the element or null if the node does no represent and element
   */
  public static Element elementFromNode(Node node) {
    if (node.getNodeType() == Node.ELEMENT_NODE) {
      return (Element) node;
    }

    return null;
  }

  /**
   * Encodes characters larger than 7-bit to thier html representation
   *
   * @param value  the characters
   *
   * @return   The encoded string
   */
  public static String encode(String value) {
    if (value == null) {
      return null;
    }

    CharArray out = (CharArray) perThreadCharArray.get();

    out._length = 0;

    return encode(value, out).toString();
  }

  /**
   * Encodes characters larger than 7-bit to thier html representation
   *
   * @param value  the characters
   * @param out    the character array to use to store the output
   *
   * @return   The encoded string
   */
  public static CharArray encode(String value, CharArray out) {
    if (value == null) {
      return null;
    }

    char[] b = value.toCharArray();

    return encode(b, 0, b.length, out);
  }

  /**
   * Encodes characters larger than 7-bit to thier html representation
   *
   * @param chars  the characters to be escape
   * @param pos    the starting position
   * @param len    the number of characters
   * @param out    the character array to use to store the output
   *
   * @return   the passed in string builder with the escaped string appended to it
   */
  public static CharArray encode(char[] chars, int pos, int len, CharArray out) {
    if (out == null) {
      out = new CharArray(len + 16);
    }

    len += pos;

    for (int i = pos; i < len; i++) {
      encodeChararacter(chars[i], out);
    }

    return out;
  }

  /**
   * Add escape sequences to a value
   *
   * @param value  the characters
   *
   * @return   The escaped string
   */
  public static String escape(String value) {
    return escape(value, false);
  }

  /**
   * Add escape sequences to a value
   *
   * @param value  the characters
   * @param whitespace true to handle whitespace; false otherwise
   *
   * @return   The escaped string
   */
  public static String escape(String value, boolean whitespace) {
    if (value == null) {
      return null;
    }

    char[] b = value.toCharArray();

    return escape(b, 0, b.length, whitespace);
  }

  /**
   * Add escape sequences to a value
   *
   * @param value  the characters
   * @param whitespace true to handle whitespace; false otherwise
   * @param out    the character array to use to store the output
   *
   * @return   The escaped string
   */
  public static CharArray escape(String value, boolean whitespace, CharArray out) {
    if (value == null) {
      return null;
    }

    char[] b = value.toCharArray();

    return escape(b, 0, b.length, whitespace, out);
  }

  /**
   * Add escape sequences to a value
   *
   * @param chars  the characters to be escape
   * @param pos    the starting position
   * @param len    the number of characters
   * @param whitespace true to handle whitespace; false otherwise
   *
   * @return   The escaped string
   */
  public static String escape(char[] chars, int pos, int len, boolean whitespace) {
    CharArray out = (CharArray) perThreadCharArray.get();

    out._length = 0;

    return escape(chars, pos, len, whitespace, out).toString();
  }

  /**
   * Add escape sequences to a value
   *
   * @param chars  the characters to be escape
   * @param pos    the starting position
   * @param len    the number of characters
   * @param whitespace true to handle whitespace; false otherwise
   * @param out    the character array to use to store the output
   *
   * @return   the passed in string builder with the escaped string appended to it
   */
  public static CharArray escape(char[] chars, int pos, int len, boolean whitespace, CharArray out) {
    if (out == null) {
      out = new CharArray(len + 16);
    }

    len += pos;

    for (int i = pos; i < len; i++) {
      escapeChararacter(chars[i], out, whitespace);
    }

    return out;
  }

  /**
   * Utility function for making an XML tagged expression.
   *
   * @param out    the buffer to output to
   * @param name   the name of the element's tag
   * @param value  the value for the tag
   * @param depth  the padding depth
   */
  public static void makeElementString(StringBuilder out, String name, String value, int depth) {
    Helper.writePadding(out, depth);

    if (value == null) {
      out.append('<');
      out.append(name);
      out.append("/>");
      out.append(lineSeparator);

      return;
    }

    out.append('<');
    out.append(name);
    out.append('>');

    if ((value.length() > 60)) {
      out.append(lineSeparator);
      Helper.writePadding(out, depth + 1);
      out.append(value);
      out.append(lineSeparator);
      Helper.writePadding(out, depth);
    } else {
      out.append(value);
    }

    int n = name.indexOf(' ');

    if (n != -1) {
      name = name.substring(0, n);
    }

    out.append('<');
    out.append('/');
    out.append(name);
    out.append('>');
    out.append(lineSeparator);
  }

  /**
   * Utility function for making an XML tagged expression.
   *
   * @param out the buffer to output to
   * @param name the name of the element's tag
   * @param value the value for the tag
   * @param depth the padding depth
   * @param attributes element attributes
   * @throws IOException
   */
  public static void makeElementString(Writer out, String name, String value, int depth, Map attributes)
          throws IOException {
    Helper.writePadding(out, depth);

    if (value == null) {
      out.write('<');
      out.write(name);
      dumpAttributes(out, attributes);
      out.write("/>");
      out.write(lineSeparator);

      return;
    }

    out.write('<');
    out.write(name);
    dumpAttributes(out, attributes);
    out.write('>');

    if ((value.length() > 60)) {
      out.write(lineSeparator);
      Helper.writePadding(out, depth + 1);
      out.write(value);
      out.write(lineSeparator);
      Helper.writePadding(out, depth);
    } else {
      out.write(value);
    }

    int n = name.indexOf(' ');

    if (n != -1) {
      name = name.substring(0, n);
    }

    out.write('<');
    out.write('/');
    out.write(name);
    out.write('>');
    out.write(lineSeparator);
  }

  /**
   * Sends a new-line sequence to the specified writer
   *
   * @param out  the writer
   *
   * @throws  IOException If an error occurs
   */
  public static void newLine(Writer out) throws IOException {
    out.write(lineSeparator);
  }

  /**
   * Converts a node to a string
   * @param node the node
   * @return the string representation
   * @throws Exception
   */
  public static String toString(Node node) throws Exception {
    ByteArrayOutputStreamEx bo = new ByteArrayOutputStreamEx(1024);

    toString(node, bo, 2);

    ByteArray ba = new ByteArray(bo.getArray());

    ba._length = bo.size();

    return Streams.streamToString(ba);
  }

  public static void toString(Node node, OutputStream out, int indent) throws Exception {
    TransformerFactory tfactory = TransformerFactory.newInstance();
    Transformer        serializer;

    try {
      serializer = tfactory.newTransformer();

      if (indent > 0) {
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", SNumber.toString(indent));
      }

      serializer.transform(new DOMSource(node), new StreamResult(out));
    } catch(TransformerException e) {
      // this is fatal, just dump the stack and throw a runtime exception
      e.printStackTrace();

      throw new RuntimeException(e);
    }
  }

  /**
   * Writes spaces for formatted output
   *
   * @param out    the writer to output to
   * @param spaces  the number of spaces to write
   *
   * @throws  IOException If an I/O error occurs
   */
  public static void writeSpaces(Writer out, int spaces) throws IOException {
    if (spaces == 0) {
      return;
    }

    int len = padding.length;

    while(spaces > len) {
      out.write(padding, 0, len);
      spaces -= len;
    }

    if (spaces > 0) {
      out.write(padding, 0, spaces);
    }
  }

  public static DocumentBuilderFactory getDocumentBuilderFactory() {
    if (documentBuilderFactory == null) {
      documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setCoalescing(false);
      documentBuilderFactory.setIgnoringComments(true);
    }

    return documentBuilderFactory;
  }

  /**
   * Gets the value of an xml element
   *
   * @param e the element
   * @return the value of an xml element
   */
  public static String getElementValue(Element e) {
    return getElementValue(e, (CharArray) perThreadCharArray.get());
  }

  public static String getElementValue(String name, NodeList list) {
    int len = list.getLength();

    for (int i = 0; i < len; i++) {
      Node node = list.item(i);

      if (name.equals(node.getNodeName()) && (node instanceof Element)) {
        return getElementValue((Element) node);
      }
    }

    return null;
  }

  /**
   * Gets the value of an xml element
   *
   * @param e the element
   * @param cb a buffer to use to construct the value
   * @return the value of an xml element
   */
  public static String getElementValue(Element e, CharArray cb) {
    String value = null;
    Node   node;

    if (e.hasChildNodes()) {
      NodeList list = e.getChildNodes();
      int      len  = list.getLength();

      if (len == 1) {
        node  = list.item(0);
        value = node.getNodeValue().trim();
      } else {
        cb._length = 0;

        for (int i = 0; i < len; i++) {
          list.item(i).getNodeType();
          cb.append(list.item(i).getNodeValue());
        }

        cb    = cb.trim();
        value = cb.toString();
      }
    }

    return value;
  }

  /**
   * Get the root element from the XML document represented by the specified stream
   *
   * @param stream the stream
   *
   * @return the root element
   */
  public static Element getRootElement(InputStream stream) {
    try {
      DocumentBuilderFactory dbf = getDocumentBuilderFactory();
      Document               doc = dbf.newDocumentBuilder().parse(stream);

      return doc.getDocumentElement();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the root element from the XML document represented by the specified reader
   *
   * @param reader the reader
   *
   * @return the root element
   */
  public static Element getRootElement(Reader reader) {
    try {
      DocumentBuilderFactory dbf = getDocumentBuilderFactory();
      Document               doc = dbf.newDocumentBuilder().parse(new InputSource(reader));

      return doc.getDocumentElement();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns whether the specified node is an element
   *
   * @param node to test
   * @return true if the specified node is an element; false otherwise
   */
  public static boolean isElement(Node node) {
    return node.getNodeType() == Node.ELEMENT_NODE;
  }

  /**
   * {@inheritDoc}
   *
   * @param value {@inheritDoc}
   * @param start {@inheritDoc}
   * @param len {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  static CharArray decode(CharArray value, int start, int len, CharArray out) {
    int pos;

    if (out == null) {
      out = new CharArray(len);
    }

    if ((pos = indexOf(value.A, start, len, '&')) == -1) {
      out.append(value.A, start, start + len);

      return out;
    }

    int  n;
    char c;

    do {
      n = pos - start;
      out.append(value.A, start, n);
      start = pos + 1;
      len   -= (n + 1);
      n     = indexOf(value.A, start, len, ';');

      if (n == -1) {
        out.append(value.A, start, len);

        break;
      }

      c = decodeCharacter(value, start, n - start);

      if (c != 0) {
        out.append(c);
      }

      len   -= ((n + 1) - start);
      start = n + 1;
      pos   = indexOf(value.A, start, len, '&');

      if (pos == -1) {
        out.append(value.A, start, len);
        len = 0;
      }
    } while(len > 0);

    return out;
  }

  /**
   * {@inheritDoc}
   *
   * @param chars {@inheritDoc}
   * @param pos {@inheritDoc}
   * @param len {@inheritDoc}
   * @param c {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  static int indexOf(char[] chars, int pos, int len, char c) {
    char    d        = 0;
    boolean bInQuote = false;
    int     i        = pos;
    int     n        = pos + len;

    if (pos >= n) {
      return -1;
    }

    while(i < n) {
      d = chars[i];

      if ((d == c) &&!bInQuote) {
        return i;
      }

      if ((d == '\"')) {
        if (bInQuote) {
          if ((i + 1) < n) {
            if (chars[i + 1] == '\"') {
              i++;
            } else {
              bInQuote = false;
            }
          }
        } else {
          bInQuote = true;
        }
      }

      i++;
    }

    return -1;
  }

  private static void encodeChararacter(char c, CharArray out) {
    if ((c < 40) || (c > 126)) {
      out.append('&');
      out.append('#');
      out.append((int) c);
      out.append(';');
    } else {
      out.append(c);
    }
  }

  private static void escapeChararacter(char c, CharArray out, boolean whitespace) {
    if (c == '<') {
      out.append("&lt;");
    } else if (c == '>') {
      out.append("&gt;");
    } else if (c == '\"') {
      out.append("&quot;");
    } else if (c == '&') {
      out.append("&amp;");
    } else if (c == ' ') {
      if (!whitespace) {
        out.append(' ');
      } else if (out._length == 0) {
        out.append("&nbsp;");
      } else if ((out.A[out._length - 1] == ' ') || (out.A[out._length - 1] == '>')) {
        out.append("&nbsp;");
      } else {
        out.append(' ');
      }
    } else if (c == '\t') {
      if (whitespace) {
        out.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      } else {
        out.append('\t');
      }
    } else if (c == '\n') {
      if (whitespace) {
        out.append("<br/>");
      } else {
        out.append('\n');
      }
    } else {
      if ((c < 40) || (c > 126)) {
        out.append('&');
        out.append('#');
        out.append((int) c);
        out.append(';');
      } else {
        out.append(c);
      }
    }
  }
}
