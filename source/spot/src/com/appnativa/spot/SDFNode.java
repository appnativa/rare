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

package com.appnativa.spot;

import com.appnativa.util.CharArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.FileResolver;
import com.appnativa.util.iFileResolver;
import com.appnativa.util.iStructuredNode;

import com.google.j2objc.annotations.Weak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing an SDF node
 *
 * @author Don DeCoteau
 */
public class SDFNode implements Cloneable, iStructuredNode {
  public static final int NODETYPE_BLOCK   = 2;
  public static final int NODETYPE_COMMAND = 5;
  public static final int NODETYPE_COMMENT = 4;
  public static final int NODETYPE_NORMAL  = 3;

  /** The node types */
  public static final int NODETYPE_ROOT = 1;

  /** OS line separator */
  public static final String lineSeparator = System.getProperty("line.separator");
  private static char[]      padding;
  protected ArrayList        childNodes;
  protected boolean          isACopy;
  protected Object           linkedData;
  protected Map              nameMap;
  protected Map              nodeAttributes;
  protected String           nodeComment;
  protected String           nodeName;
  protected int              nodeType;
  protected String           nodeValue;
  @Weak
  protected SDFNode          parentNode;
  protected String           preformattedTag;
  protected boolean          valuePreformatted;

  /**
   * Creates a new instance of SDFNode
   *
   * @param name
   *          the name of the node
   */
  public SDFNode(String name) {
    nodeType          = NODETYPE_NORMAL;
    childNodes        = null;
    isACopy           = false;
    linkedData        = null;
    nameMap           = null;
    nodeAttributes    = null;
    nodeComment       = null;
    parentNode        = null;
    preformattedTag   = null;
    valuePreformatted = false;
    nodeValue         = null;
    nodeName          = name;
  }

  /**
   * Constructs a new instance
   *
   * @param name
   *          the name of the node
   * @param value
   *          the node's value
   */
  public SDFNode(String name, String value) {
    nodeType          = NODETYPE_NORMAL;
    childNodes        = null;
    isACopy           = false;
    linkedData        = null;
    nameMap           = null;
    nodeAttributes    = null;
    nodeComment       = null;
    parentNode        = null;
    preformattedTag   = null;
    valuePreformatted = false;
    nodeName          = name;
    nodeValue         = value;
  }

  public void addAttribute(String name, String value) {
    if (nodeAttributes == null) {
      nodeAttributes = new LinkedHashMap();
    }

    nodeAttributes.put(name, value);
  }

  public void addAttributes(Map attributes) {
    int len = (attributes == null)
              ? 0
              : attributes.size();

    if (len == 0) {
      return;
    }

    if (nodeAttributes == null) {
      nodeAttributes = new LinkedHashMap();
    }

    nodeAttributes.putAll(attributes);
  }

  public void addChildren(List children) {
    int len = (children == null)
              ? 0
              : children.size();

    if (len == 0) {
      return;
    }

    if (childNodes == null) {
      childNodes = new ArrayList();
    }

    childNodes.addAll(children);
  }

  public void addNode(SDFNode node) {
    if (childNodes == null) {
      childNodes = new ArrayList();
    }

    childNodes.add(node);
    node.parentNode = this;

    if (node.getNodeName() != null) {
      if (nameMap == null) {
        nameMap = new HashMap();
      }

      nameMap.put(node.getNodeName(), node);
    }
  }

  public Object clone() {
    return copy();
  }

  public SDFNode copy() {
    SDFNode node = new SDFNode(nodeName, nodeValue);

    node.nodeType          = nodeType;
    node.valuePreformatted = valuePreformatted;

    if (nodeAttributes != null) {
      node.nodeAttributes = new LinkedHashMap(nodeAttributes);
    }

    if (childNodes != null) {
      ArrayList list = childNodes;
      int       len  = list.size();
      int       i    = 0;

      while(i < len) {
        node.addNode(((SDFNode) list.get(i++)).copy());
      }
    }

    node.isACopy = true;

    return node;
  }

  public void copyAttributes(Map destination) {
    if (nodeAttributes != null) {
      destination.putAll(nodeAttributes);
    }
  }

  public SDFNode createBlockFromThis(String name, String subname) {
    SDFNode node = new SDFNode(name);
    SDFNode c    = this.copy();

    node.nodeType = NODETYPE_BLOCK;
    c.nodeName    = subname;
    node.addNode(c);

    return node;
  }

  public static SDFNode createRootNode() {
    SDFNode node = new SDFNode(null);

    node.setNodeType(NODETYPE_ROOT);

    return node;
  }

  public static SDFNode parse(Reader r) throws IOException {
    SDFNode       node        = new SDFNode(null);
    iFileResolver urlResolver = new FileResolver(new File("/"));

    node.setNodeType(NODETYPE_ROOT);

    ParserCallback pc = new ParserCallback(node, urlResolver);
    SDFParser      p  = new SDFParser(r);

    p.parse(pc);

    return node;
  }

  public static void parse(Reader r, SDFParser.iCallback pc) throws IOException {
    SDFParser p = new SDFParser(r);

    p.parse(pc);
  }

  public static SDFNode parse(Reader r, iFileResolver urlResolver, String fileName, boolean keepComments)
          throws IOException {
    SDFNode node = new SDFNode(null);

    node.setNodeType(NODETYPE_ROOT);

    ParserCallback pc = new ParserCallback(node, urlResolver);
    SDFParser      p  = new SDFParser(r);

    p.setFileName(fileName);

    if (keepComments) {
      p.setIgnoreComments(false);
    }

    p.parse(pc);

    return node;
  }

  public static void main(String[] args) {
    try {
      int    len  = (args == null)
                    ? 0
                    : args.length;
      String file = (len == 0)
                    ? null
                    : args[0];

      if (file == null) {
        file = "/Code/Dev/appNativa/applications/BellaVista/BellaVista-android/assets/careteam.rml";
      }

      File       f = new File(file);
      FileReader r = new FileReader(f);
      // SDFNode node =parse(r,new SimpleURLResolver(f.toURI().toURL()));
      SDFNode node = parseForReformat(new BufferedReader(r), new FileResolver(f), file);

      System.out.println(node);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public static SDFNode parseForReformat(Reader r, iFileResolver urlResolver, String fileName) throws IOException {
    SDFNode node = new SDFNode(null);

    node.setNodeType(NODETYPE_ROOT);

    FormattingCallback pc = new FormattingCallback(node, urlResolver);

    pc.setIgnoreImports(true);
    pc.setIgnoreReferences(true);

    SDFParser p = new SDFParser(r);

    p.setFileName(fileName);
    p.setIgnoreComments(false);
    p.parse(pc);

    return node;
  }

  public String toString() {
    StringWriter sw = new StringWriter();

    try {
      toString(sw, 0);
    } catch(IOException ex) {}

    return sw.toString();
  }

  public void toString(Writer w, int depth) throws IOException {
    int    len;
    String name = getNodeName();

    switch(getNodeType()) {
      case NODETYPE_ROOT :
        len = (childNodes == null)
              ? 0
              : childNodes.size();

        for (int i = 0; i < len; i++) {
          ((SDFNode) childNodes.get(i)).toString(w, depth);
        }

        break;

      case NODETYPE_BLOCK :
        int size = getPackedSize() + (depth * 2);

        if ((size > 0) && (size < 80)) {
          toPackedString(w, depth);

          return;
        } else {
          if ((name == null) || (name.length() == 0)) {
            name = "{";
          } else {
            name = name + " {";
          }

          writeName(w, name, depth);
          len = (childNodes == null)
                ? 0
                : childNodes.size();

          if (len == 0) {
            w.write("}");
          } else {
            w.write(lineSeparator);

            for (int i = 0; i < len; i++) {
              ((SDFNode) childNodes.get(i)).toString(w, depth + 1);
            }

            writeName(w, "}", depth);
          }
        }

        break;

      case NODETYPE_COMMAND :
        if (getNodeValue() == null) {
          writeName(w, "@" + name, depth);
        } else {
          writeName(w, "@" + name + " " + getNodeValue(), depth);
        }

        break;

      case NODETYPE_COMMENT :
        writeName(w, name, depth);

        break;

      default :
        if (isValuePreformatted()) {
          if (name == null) {
            name = "";
          }

          if (getNodeValue() != null) {
            if (preformattedTag == null) {
              writeName(w, name + ":<<\n", depth);
            } else {
              writeName(w, name + ":<<" + preformattedTag + "\n", depth);
            }
          } else {
            writeName(w, name + ": ", depth);
          }
        } else {
          if (name == null) {
            writePadding(w, depth);
          } else {
            writeName(w, name + ": ", depth);
          }
        }

        if (getNodeValue() != null) {
          writeValue(w, getNodeValue(), isValuePreformatted(), depth);

          if (isValuePreformatted()) {
            w.write((preformattedTag == null)
                    ? ">>"
                    : preformattedTag);
          }
        }
    }

    writeAttributes(w, depth, nodeAttributes);

    if (nodeComment != null) {
      w.write(" ");
      w.write(this.nodeComment);
    }

    w.write(lineSeparator);
  }

  public static void writeAttributes(Writer out, int depth, Map attributes) throws IOException {
    if ((attributes == null) || (attributes.size() == 0)) {
      return;
    }

    Iterator      it = attributes.entrySet().iterator();
    StringBuilder sb = new StringBuilder();
    Map.Entry     me;

    out.write(" [ ");

    String  val;
    boolean first = true;
    int     count = 0;

    while(it.hasNext()) {
      me    = (Map.Entry) it.next();
      count += sb.length();
      sb.setLength(0);

      if (!first) {
        sb.append(", ");

        if (count > 80) {
          out.write(",");
          out.write(lineSeparator);
          writePadding(out, depth + 2);
          sb.setLength(0);
          count = 0;
        }
      } else {
        first = false;
      }

      sb.append((String) me.getKey());
      val = (String) me.getValue();

      if (val != null) {
        sb.append("=\"");
        sb.append(val);
        sb.append('"');
      }

      out.write(sb.toString());
    }

    out.write(" ]");
  }

  public static void writeName(Writer w, String name, int depth) throws IOException {
    writePadding(w, depth);
    w.write(name);
  }

  /**
   * Writes space padding for formatted output. Padding is 2 spaces per depth
   * unit.
   *
   * @param out
   *          the writer to output to
   * @param depth
   *          the depth for the requested padding
   *
   * @throws IOException
   *           If an I/O error occurs
   */
  public static void writePadding(Writer out, int depth) throws IOException {
    if (depth == 0) {
      return;
    }

    if (padding == null) {
      padding = new char[255];

      int len = padding.length;

      for (int i = 0; i < len; i++) {
        padding[i] = ' ';
      }
    }

    int len = padding.length;

    depth *= 2;

    while(depth > len) {
      out.write(padding, 0, len);
      depth -= len;
    }

    if (depth > 0) {
      out.write(padding, 0, depth);
    }
  }

  @SuppressWarnings("resource")
  public static void writeValue(Writer w, String value, boolean pre, int depth) throws IOException {
    if (pre) {
      w.write(value);
    } else {
      CharArray ca = requote(value);
      int       n  = ca.indexOf('\n');

      if (n == -1) {
        w.write(ca.A, 0, ca._length);
      } else {
        CharScanner sc = new CharScanner(ca.A, 0, ca._length, false);
        String      s;

        s = sc.nextToken('\n');
        w.write(s);

        while((s = sc.nextToken('\n')) != null) {
          w.write('\n');
          writePadding(w, depth);
          w.write(s);
        }
      }
    }
  }

  public void setLinkedData(Object linkedData) {
    this.linkedData = linkedData;
  }

  public void setNodeAttributes(Map nodeAttributes) {
    this.nodeAttributes = nodeAttributes;
  }

  public void setNodeComment(String comment) {
    nodeComment = comment;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public void setNodeType(int nodeType) {
    this.nodeType = nodeType;
  }

  public void setNodeValue(String nodeValue) {
    this.nodeValue = nodeValue;
  }

  public void setPreformattedTag(String preformattedTag) {
    this.preformattedTag = preformattedTag;
  }

  public void setValuePreformatted(boolean valuePreformatted, String preformattedTag) {
    this.valuePreformatted = valuePreformatted;

    if (valuePreformatted) {
      this.preformattedTag = preformattedTag;
    }
  }

  public Object getAttribute(String name) {
    return (nodeAttributes == null)
           ? null
           : (String) nodeAttributes.get(name);
  }

  public Map getAttributes() {
    return nodeAttributes;
  }

  public iStructuredNode getChild(int index) {
    return (iStructuredNode) childNodes.get(index);
  }

  public int getChildCount() {
    return (this.childNodes == null)
           ? 0
           : childNodes.size();
  }

  public SDFNode getChildNode(int index) {
    return (SDFNode) childNodes.get(index);
  }

  public List getChildNodes() {
    return childNodes;
  }

  public String getComment() {
    if (nodeType == NODETYPE_COMMENT) {
      return nodeName;
    }

    return nodeComment;
  }

  public SDFNode getFirstNode() {
    int len = (childNodes == null)
              ? 0
              : childNodes.size();

    return (len > 0)
           ? (SDFNode) childNodes.get(0)
           : null;
  }

  public SDFNode getFirstBlockNode() {
    int len = (childNodes == null)
              ? 0
              : childNodes.size();

    for (int i = 0; i < len; i++) {
      SDFNode node = (SDFNode) childNodes.get(i);

      if (node.nodeType == NODETYPE_BLOCK) {
        return node;
      }
    }

    return null;
  }

  public SDFNode getFirstDataNode() {
    int len = (childNodes == null)
              ? 0
              : childNodes.size();

    for (int i = 0; i < len; i++) {
      SDFNode node = (SDFNode) childNodes.get(i);

      if ((node.nodeType != NODETYPE_COMMENT) && (node.nodeType != NODETYPE_COMMAND)) {
        return node;
      }
    }

    return null;
  }

  public iStructuredNode getFirstSignificantChild() {
    return getFirstDataNode();
  }

  public SDFNode getLastNode() {
    int len = (childNodes == null)
              ? 0
              : childNodes.size();

    return (len > 0)
           ? (SDFNode) childNodes.get(len - 1)
           : null;
  }

  public Object getLinkedData() {
    return linkedData;
  }

  public String getName() {
    return nodeName;
  }

  public iStructuredNode getNextSibling() {
    if (parentNode != null) {
      final int len = parentNode.childNodes.size();
      final int n   = parentNode.childNodes.indexOf(this);

      return (iStructuredNode) (((n == -1) || (n + 1 == len))
                                ? null
                                : parentNode.childNodes.get(n + 1));
    }

    return null;
  }

  public iStructuredNode getChild(String name) {
    return getNode(name);
  }

  public SDFNode getNode(String name) {
    return (nameMap == null)
           ? null
           : (SDFNode) nameMap.get(name);
  }

  public String getNodeAttribute(String name) {
    return (nodeAttributes == null)
           ? null
           : (String) nodeAttributes.get(name);
  }

  public Map getNodeAttributes() {
    return nodeAttributes;
  }

  public String getNodeComment() {
    if (nodeType == NODETYPE_COMMENT) {
      return nodeName;
    }

    return nodeComment;
  }

  public String getNodeName() {
    return nodeName;
  }

  public int getNodeType() {
    return nodeType;
  }

  public String getNodeValue() {
    return nodeValue;
  }

  public String getNodeValue(String name) {
    SDFNode node = getNode(name);

    return (node == null)
           ? null
           : node.nodeValue;
  }

  public SDFNode getParentNode() {
    return parentNode;
  }

  public String getPreformattedTag() {
    return preformattedTag;
  }

  public Object getValue() {
    return nodeValue;
  }

  public String getValueAsString() {
    return nodeValue;
  }

  public boolean hasAttribute(String name) {
    return (nodeAttributes == null)
           ? false
           : nodeAttributes.containsKey(name);
  }

  public boolean hasAttributes() {
    return (this.nodeAttributes == null)
           ? false
           : nodeAttributes.size() > 0;
  }

  public boolean hasChildren() {
    return (this.childNodes == null)
           ? false
           : childNodes.size() > 0;
  }

  public boolean isPreformattedData() {
    return valuePreformatted;
  }

  public boolean isValuePreformatted() {
    return valuePreformatted;
  }

  static CharArray requote(String s) {
    CharArray ca = new CharArray(s.length() + 2);

    if ((s.indexOf('\"') != -1) && (s.indexOf('\'') == -1)) {
      ca.append('\'');
      CharScanner.escape(s, false, ca);
      ca.append('\'');
    } else {
      ca.append('\"');
      CharScanner.escape(s, true, ca);
      ca.append('\"');
    }

    return ca;
  }

  static int getUnitLen(CharArray ca, int len) {
    int     cnt  = 0;
    boolean bang = false;

    do {
      if (len == 0) {
        break;
      }

      char lc = ca.A[len - 1];

      if (lc == '!') {
        len--;

        if (len == 0) {
          break;
        }

        lc   = ca.A[len - 1];
        bang = true;
      }

      if (lc == '%') {
        cnt = 1;

        break;
      }

      if (len == 1) {
        break;
      }

      switch(ca.A[len - 2]) {
        case 'c' :
          if (lc == 'h') {}
          else if (lc == 'm') {
            cnt = 2;
          }

          break;

        case 'i' :
          if (lc == 'n') {
            cnt = 2;
          }

          break;

        case 'p' :
          if (lc == 'x') {
            cnt = 2;
          } else if (lc == 't') {
            cnt = 2;
          } else if (lc == 'c') {
            cnt = 2;
          }

          break;

        case 'l' :
          if (lc == 'n') {
            cnt = 2;
          }

          break;

        default :
          break;
      }
    } while(false);

    if (bang) {
      cnt++;
    }

    return cnt;
  }

  protected void toPackedString(Writer w, int depth) throws IOException {
    int  len;
    List list = childNodes;

    len = (list == null)
          ? 0
          : list.size();

    String name = getNodeName();

    if (len == 0) {
      if ((name == null) || (name.length() == 0)) {
        writeName(w, "{}", depth);
      } else {
        writeName(w, name, depth);
        w.write(" {}");
      }
    } else {
      if ((name != null) && (name.length() > 0)) {
        writeName(w, name, depth);
        w.write(" { ");
      } else {
        writeName(w, "{", depth);
      }

      SDFNode node;
      int     n = len - 1;

      for (int i = 0; i < len; i++) {
        node = (SDFNode) list.get(i);
        name = node.getNodeName();

        if ((name != null) && (name.length() > 0)) {
          w.write(name);
          w.write(": ");
        }

        name = node.getNodeValue();

        if ((name != null) && (name.length() > 0)) {
          CharArray ca = requote(name);

          w.write(ca.A, 0, ca._length);
        }

        if (i != n) {
          w.write("; ");
        }
      }

      w.write(" }");
    }

    w.write(lineSeparator);
  }

  protected int getPackedSize() {
    if (this.hasAttributes()) {
      return Integer.MAX_VALUE;
    }

    int    size = 0;
    List   list = childNodes;
    String name = getNodeName();
    int    n;

    if (name == null) {
      size += 2;
    } else {
      size += name.length() + 2;
    }

    int len = (list == null)
              ? 0
              : list.size();

    size += 2;

    SDFNode node;

    for (int i = 0; i < len; i++) {
      node = (SDFNode) list.get(i);

      if ((node.getNodeType() == NODETYPE_BLOCK) || node.isValuePreformatted() || node.hasChildren()
          || node.hasAttributes()) {
        return Integer.MAX_VALUE;
      }

      name = node.getNodeName();

      if (name != null) {
        n = name.length();

        if (n > 0) {
          size += n;
          size += 2;
        }
      }

      name = node.getNodeValue();

      if (name != null) {
        if (name.indexOf('\n') != -1) {
          return Integer.MAX_VALUE;
        }

        size += name.length();
      }

      size += 2;
    }

    return size;
  }

  private static String exceptionString(Exception e) {
    String s = e.getMessage();

    if ((s == null) || (s.length() == 0)) {
      s = e.toString();
    }

    return s;
  }

  /**
   *
   *
   * @version 0.3, 2007-07-10
   * @author Don DeCoteau
   */
  public static class FormattingCallback extends ParserCallback {

    /**  */
    public boolean ignoreImports;

    /**  */
    public boolean ignoreReferences;

    /**
     * Constructs a new instance
     *
     * @param root
     *          {@inheritDoc}
     * @param urlResolver
     *          {@inheritDoc}
     */
    public FormattingCallback(SDFNode root, iFileResolver urlResolver) {
      super(root, urlResolver);
      this.setIgnoreMissingReferences(true);
    }

    public void handleCommand(String cmd, String params) {
      if (ignoreImports ||!handleCommandEx(cmd, params)) {
        params = ((params == null) || (params.length() == 0))
                 ? null
                 : params;

        SDFNode node = new SDFNode(cmd);

        node.nodeType  = SDFNode.NODETYPE_COMMAND;
        node.nodeValue = params;
        currentNode.addNode(node);
      }
    }

    public SDFNode startBlock(String name, String refName, String refValue) {
      boolean addnode = true;
      SDFNode node    = null;

      if ((refValue != null) &&!ignoreReferences) {
        node = getReference(refValue, false);

        if (node != null) {
          refValue      = null;
          node.nodeName = name;

          if ((name != null) && currentNode.isACopy && (name.length() > 0)) {
            SDFNode node2 = currentNode.getNode(name);

            if (node2 != null) {
              node    = merge(node2, node);
              addnode = false;
            }
          }
        }
      }

      if (node == null) {
        if ((name != null) && currentNode.isACopy && (name.length() > 0)) {
          node = currentNode.getNode(name);
        }

        if (node == null) {
          node = new SDFNode(name);
        } else {
          addnode = false;
        }
      }

      node.setNodeType(SDFNode.NODETYPE_BLOCK);
      // nodeStack.push(currentNode);
      nodeStack.add(0, currentNode);    // java 1.5 support

      if (addnode) {
        currentNode.addNode(node);
      }

      currentNode = node;

      if (refName != null) {
        if (referenceMap == null) {
          referenceMap = new HashMap();
        }

        referenceMap.put(refName, node);
      }

      rebuildName(node, name, refValue, refName);

      return node;
    }

    public void setIgnoreImports(boolean ignoreImports) {
      this.ignoreImports = ignoreImports;
    }

    public void setIgnoreReferences(boolean ignoreReferences) {
      this.ignoreReferences = ignoreReferences;
    }

    public boolean isIgnoreImports() {
      return ignoreImports;
    }

    public boolean isIgnoreReferences() {
      return ignoreReferences;
    }

    void rebuildName(SDFNode node, String name, String refValue, String refName) {
      if ((refValue != null) || (refName != null)) {
        StringBuilder sb = new StringBuilder(name);

        if (refValue != null) {
          sb.append(":$").append(refValue);
        }

        if (refName != null) {
          sb.append(":@").append(refName);
        }

        name = sb.toString();
      }

      node.nodeName = name;
    }
  }


  /**
   *
   *
   * @version 0.3, 2007-07-10
   * @author Don DeCoteau
   */
  public static class ParserCallback implements SDFParser.iCallback {
    String              urlPrefix;
    protected SDFNode   currentNode;
    protected ArrayList nodeStack;

    /**  */
    protected HashMap referenceMap;

    /**  */
    protected iFileResolver urlResolver;
    private boolean         ignoreMissingReferences;
    private SDFNode         rootNode;

    public ParserCallback(SDFNode root, iFileResolver urlResolver) {
      this.urlResolver = urlResolver;
      currentNode      = root;
      rootNode         = root;
      nodeStack        = new ArrayList();
    }

    public void addComment(String comment) {
      SDFNode node = new SDFNode(comment);

      node.nodeType = NODETYPE_COMMENT;
      currentNode.addNode(node);
    }

    public SDFNode addValue(String name, String value, boolean preformatted, String pretag, Map attributes) {
      SDFNode node;

      if ((name != null) && currentNode.isACopy) {
        node = currentNode.getNode(name);

        if (node != null) {
          if (value != null) {
            node.nodeValue = value;
            node.setValuePreformatted(preformatted, pretag);
          }

          if (attributes != null) {
            if (node.nodeAttributes == null) {
              node.nodeAttributes = attributes;
            } else {
              node.nodeAttributes.putAll(attributes);
            }
          }

          node.setValuePreformatted(preformatted, pretag);
        }

        if (node != null) {
          return null;
        }
      }

      currentNode.addNode(node = new SDFNode(name, value));
      node.setValuePreformatted(preformatted, pretag);
      node.nodeAttributes = attributes;

      return node;
    }

    public SDFNode endBlock(Map attributes) {
      if (attributes != null) {
        if (currentNode.nodeAttributes == null) {
          currentNode.nodeAttributes = attributes;
        } else {
          currentNode.nodeAttributes.putAll(attributes);
        }
      }

      SDFNode node = currentNode;

      currentNode = nodeStack.isEmpty()
                    ? null
                    : (SDFNode) nodeStack.remove(0);

      if (currentNode == null) {
        currentNode = rootNode;
      }

      return node;
    }

    public void handleCommand(String cmd, String params) {
      handleCommandEx(cmd, params);
    }

    public boolean handleCommandEx(String cmd, String params) {
      int len;

      if ((urlResolver != null) && cmd.equalsIgnoreCase("import") && (params != null)
          && ((len = params.length()) > 0)) {
        try {
          char c = params.charAt(0);

          if ((c == '\'') || ((c == '\"') && (len > 1))) {
            if (params.charAt(len - 1) == c) {
              params = params.substring(1, len - 1);
            } else {
              params = params.substring(1);
            }
          }

          if ((urlPrefix != null) &&!params.startsWith("/")) {
            params = urlPrefix + params;
          }

          Reader  r    = urlResolver.getReader(params);
          SDFNode node = new SDFNode(null);

          node.setNodeType(NODETYPE_ROOT);

          ParserCallback pc = new ParserCallback(node, urlResolver);

          if (this.isIgnoreMissingReferences()) {
            pc.setIgnoreMissingReferences(true);
          }

          int n = params.lastIndexOf('/');

          if (n != -1) {
            pc.urlPrefix = params.substring(0, n + 1);
          }

          if (referenceMap == null) {
            referenceMap = new HashMap();
          }

          pc.referenceMap = referenceMap;

          SDFParser p = new SDFParser(r);

          p.parse(pc);

          return true;
        } catch(Exception ex) {
          throw new RuntimeException("Error occurred parsing import file (" + params + ")\r\n" + exceptionString(ex));
        }
      }

      if (cmd.equalsIgnoreCase("ignoreMissingReferences")) {
        this.setIgnoreMissingReferences(true);
      }

      return false;
    }

    public SDFNode startBlock(String name, String refName, String refValue) {
      SDFNode node    = null;
      boolean addnode = true;

      if (refValue != null) {
        node = getReference(refValue, !ignoreMissingReferences);

        if (node == null) {
          refValue = null;
        } else {
          node.nodeName = name;

          if ((name != null) && currentNode.isACopy) {
            SDFNode node2 = currentNode.getNode(name);

            if (node2 != null) {
              node    = merge(node2, node);
              addnode = false;
            }
          }
        }
      }

      if (refValue == null) {
        if ((name != null) && currentNode.isACopy && (name.length() > 0)) {
          node = currentNode.getNode(name);
        }

        if (node == null) {
          node = new SDFNode(name);
        } else {
          addnode = false;
        }
      }

      node.setNodeType(SDFNode.NODETYPE_BLOCK);
      // nodeStack.push(currentNode);
      nodeStack.add(0, currentNode);    // java 1.5 support

      if (addnode) {
        currentNode.addNode(node);
      }

      currentNode = node;

      if (refName != null) {
        if (referenceMap == null) {
          referenceMap = new HashMap();
        }

        referenceMap.put(refName, node);
      }

      return node;
    }

    public void setIgnoreMissingReferences(boolean ignoreMissingReferences) {
      this.ignoreMissingReferences = ignoreMissingReferences;
    }

    public void setRootNode(SDFNode root) {
      this.rootNode = root;
      currentNode   = root;
    }

    public SDFNode getRootNode() {
      return rootNode;
    }

    public boolean isIgnoreMissingReferences() {
      return ignoreMissingReferences;
    }

    SDFNode loadNode(String url) {
      int len;

      if ((urlResolver != null) && (url != null) && ((len = url.length()) > 0)) {
        try {
          char c = url.charAt(0);

          if ((c == '\'') || ((c == '\"') && (len > 1))) {
            if (url.charAt(len - 1) == c) {
              url = url.substring(1, len - 1);
            } else {
              url = url.substring(1);
            }
          }

          Reader  r    = urlResolver.getReader(url);
          SDFNode node = new SDFNode(null);

          node.setNodeType(NODETYPE_ROOT);

          ParserCallback pc = new ParserCallback(node, urlResolver);

          pc.setIgnoreMissingReferences(this.isIgnoreMissingReferences());

          SDFParser p = new SDFParser(r);

          p.parse(pc);

          return node.getFirstNode();
        } catch(Exception ex) {
          throw new RuntimeException("Error occurred parsing referenced file (" + url + ")\r\n" + exceptionString(ex));
        }
      }

      return null;
    }

    SDFNode merge(SDFNode node, SDFNode withNode) {
      if (withNode.hasAttributes()) {
        node.addAttributes(withNode.nodeAttributes);
      }

      if (withNode.hasChildren()) {
        node.addChildren(withNode.childNodes);
      }

      return node;
    }

    SDFNode getReference(String name, boolean throwException) {
      SDFNode node  = null;
      String  oname = name;

      if (name != null) {
        if (name.startsWith("'")) {
          node = loadNode(name);
        } else if (referenceMap != null) {
          node = (SDFNode) referenceMap.get(name);
        }
      }

      if (node == null) {
        if (!throwException) {
          return null;
        }

        throw new RuntimeException("the referenced name '" + oname + "' does not exist");
      }

      return node.copy();
    }
  }


  /**
   * Returns whether the node is a comment
   *
   * @return true if the node is a comment node; false otherwise
   */
  public boolean isComment() {
    return nodeType == NODETYPE_COMMENT;
  }
}
