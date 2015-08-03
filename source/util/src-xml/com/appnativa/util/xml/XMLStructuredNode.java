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

import com.appnativa.util.CharArray;
import com.appnativa.util.iStructuredNode;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class XMLStructuredNode implements iStructuredNode {
  private Map               attributes;
  private Element           element;
  private XMLStructuredNode firstChild;
  private String            value;

  public XMLStructuredNode(Element element) {
    this.element = element;
  }

  public void copyAttributes(Map destination) {
    NamedNodeMap map = element.getAttributes();
    Node         node;
    int          len = map.getLength();
    int          i   = 0;

    if (len == 0) {
      return;
    }

    while(i < len) {
      node = map.item(i++);
      destination.put(node.getNodeName(), node.getNodeValue());
    }
  }

  public Object getAttribute(String name) {
    NamedNodeMap map = element.getAttributes();

    return (map == null)
           ? null
           : map.getNamedItem(name);
  }

  public Map getAttributes() {
    if (attributes == null) {
      NamedNodeMap map = element.getAttributes();
      int          len = map.getLength();

      if (len > 0) {
        attributes = new HashMap();
        copyAttributes(attributes);
      }
    }

    return attributes;
  }

  public iStructuredNode getChild(int index) {
    return new XMLStructuredNode((Element) element.getChildNodes().item(index));
  }

  public int getChildCount() {
    return element.getChildNodes().getLength();
  }

  public String getComment() {
    return null;
  }

  public iStructuredNode getFirstSignificantChild() {
    if (firstChild == null) {
      NodeList list = element.getChildNodes();
      int      len  = list.getLength();

      for (int i = 0; i < len; i++) {
        Node node = list.item(i);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
          firstChild = new XMLStructuredNode((Element) node);

          break;
        }
      }
    }

    return firstChild;
  }

  public Object getLinkedData() {
    return null;
  }

  public String getName() {
    return element.getNodeName();
  }

  public String getPreformattedTag() {
    return "![CDATA[";
  }

  public iStructuredNode getChild(String name) {
    NodeList list = element.getChildNodes();
    int      len  = list.getLength();
    Node     node;

    for (int i = 0; i < len; i++) {
      node = list.item(i);

      if ((node instanceof Element) && name.equals(node.getNodeName())) {
        return new XMLStructuredNode((Element) node);
      }
    }

    return null;
  }

  @SuppressWarnings("resource")
  public Object getValue() {
    if (value == null) {
      if (element.hasChildNodes()) {
        NodeList list = element.getChildNodes();
        int      len  = list.getLength();

        if (len == 1) {
          Node node = list.item(0);

          value = node.getNodeValue().trim();
        } else {
          CharArray sb = new CharArray();

          for (int i = 0; i < len; i++) {
            list.item(i).getNodeType();
            sb.append(list.item(i).getNodeValue());
          }

          sb    = sb.trim();
          value = sb.toString();
        }
      }
    }

    return value;
  }

  public String getValueAsString() {
    return element.getNodeValue();
  }

  public boolean hasAttribute(String name) {
    return getAttribute(name) != null;
  }

  public boolean hasAttributes() {
    return element.hasAttributes();
  }

  public boolean hasChildren() {
    return element.hasChildNodes();
  }

  public boolean isPreformattedData() {
    return element.getNodeType() == Element.CDATA_SECTION_NODE;
  }

  public iStructuredNode getNextSibling() {
    Element e = (Element) element.getNextSibling();

    return (e == null)
           ? null
           : new XMLStructuredNode(e);
  }
}
