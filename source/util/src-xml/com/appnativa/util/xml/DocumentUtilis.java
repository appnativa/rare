package com.appnativa.util.xml;

import com.appnativa.util.ByteArray;
import com.appnativa.util.CharArray;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
import com.appnativa.util.io.ByteArrayOutputStreamEx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DocumentUtilis {
  private static DocumentBuilderFactory documentBuilderFactory;
  private static ThreadLocal            perThreadCharArray = new ThreadLocal() {
    @Override
    protected synchronized Object initialValue() {
      return new CharArray(32);
    }
  };

  private DocumentUtilis() {}

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
}
