/*
 * @(#)JSONStructuredNode.java
 *
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.util.json;

import com.appnativa.util.iStructuredNode;

import com.google.j2objc.annotations.Weak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JSONStructuredNode implements iStructuredNode {
  int                             myPosition = -1;
  int                             childCount;
  List                            listChildren;
  Map                             mapChildren;
  Map                             nodeAttributes;
  String                          nodeComment;
  Object                          nodeLinkedData;
  String                          nodeName;
  Object                          nodeValue;
  ArrayList<JSONStructuredNode>   sChildren;
  Map<String, JSONStructuredNode> sMap;
  @Weak
  JSONStructuredNode              parent;

  public JSONStructuredNode(String name, Object value) {
    this.nodeName = name;
    setValue(value);
  }

  @Override
  public void copyAttributes(Map destination) {
    if (nodeAttributes != null) {
      destination.putAll(nodeAttributes);
    }
  }

  @Override
  public Object getAttribute(String name) {
    return (nodeAttributes == null) ? null : nodeAttributes.get(name);
  }

  @Override
  public Map getAttributes() {
    return nodeAttributes;
  }

  @Override
  public iStructuredNode getChild(int index) {
    if (sChildren == null) {
      createChildren();
    }

    return (sChildren == null) ? null : sChildren.get(index);
  }

  @Override
  public iStructuredNode getChild(String name) {
    if (sMap == null) {
      createNameMap();
    }

    return (sMap == null) ? null : sMap.get(name);
  }

  @Override
  public int getChildCount() {
    return childCount;
  }

  @Override
  public String getComment() {
    return nodeComment;
  }

  @Override
  public iStructuredNode getFirstSignificantChild() {
    if ((sChildren == null) && (childCount > 0)) {
      createChildren();
    }

    return (sChildren == null) ? null : sChildren.get(0);
  }

  @Override
  public Object getLinkedData() {
    return nodeLinkedData;
  }

  @Override
  public String getName() {
    return nodeName;
  }

  @Override
  public iStructuredNode getNextSibling() {
    if ((parent != null) && (parent.getChildCount() > myPosition + 1)) {
      return parent.getChild(myPosition + 1);
    }

    return null;
  }

  @Override
  public String getPreformattedTag() {
    return null;
  }

  @Override
  public Object getValue() {
    return nodeValue;
  }

  @Override
  public String getValueAsString() {
    return (nodeValue == null) ? null : nodeValue.toString();
  }

  @Override
  public boolean hasAttribute(String name) {
    return (nodeAttributes != null) && nodeAttributes.containsKey(name);
  }

  @Override
  public boolean hasAttributes() {
    return (nodeAttributes != null) && (nodeAttributes.size() > 0);
  }

  @Override
  public boolean hasChildren() {
    return childCount > 0;
  }

  @Override
  public boolean isPreformattedData() {
    return false;
  }

  protected void createChildren() {
    if (mapChildren != null) {
      createChildren(mapChildren);
    } else if (listChildren != null) {
      createChildren(listChildren);
    }
  }

  protected void createChildren(List children) {
    sChildren = new ArrayList<JSONStructuredNode>(childCount);

    for (Object o : children) {
      sChildren.add(new JSONStructuredNode("no_name", o));
    }
  }

  protected void createChildren(Map children) {
    Iterator<Entry> it = children.entrySet().iterator();

    sChildren = new ArrayList<JSONStructuredNode>(childCount);

    JSONStructuredNode child;
    int i = 0;

    while (it.hasNext()) {
      Entry e = it.next();
      String name = (String) e.getKey();
      Object v = e.getValue();

      if (v instanceof Map) {
        child = new JSONStructuredNode(name, v);
      } else if (v instanceof List) {
        child = new JSONStructuredNode(name, v);
      } else {
        child = new JSONStructuredNode(name, v);
      }

      sChildren.add(child);
      child.parent = this;
      child.myPosition = i++;
    }
  }

  protected void createNameMap() {
    if (mapChildren != null) {
      if (sChildren == null) {
        createChildren();
      }

      sMap = new HashMap<String, JSONStructuredNode>();

      for (JSONStructuredNode node : sChildren) {
        sMap.put(node.getName(), node);
      }
    }
  }

  protected void populate(Map object) {
    if (object instanceof JSONObject) {
      object = ((JSONObject) object).getObjectMap();
    }

    this.mapChildren = object;

    Map o = (Map) mapChildren.remove("_attributes");

    if (o instanceof JSONObject) {
      o = ((JSONObject) o).getObjectMap();
    }

    this.nodeAttributes = o;

    this.nodeValue = mapChildren.remove("_value");
    this.nodeLinkedData = mapChildren.remove("_linkedData");

    this.nodeComment = (String) mapChildren.remove("_comment");

    if (nodeValue instanceof List) {
      if (nodeValue instanceof JSONArray) {
        nodeValue = ((JSONArray) object).getObjectList();
      }

      listChildren = (List) nodeValue;
      childCount = listChildren.size();
      nodeValue = null;
      mapChildren = null;
    } else {
      childCount = object.size();
    }
  }

  protected void setValue(Object value) {
    if (value instanceof Map) {
      populate((Map) value);
    } else if (value instanceof List) {
      if (value instanceof JSONArray) {
        listChildren = ((JSONArray) value).getObjectList();
      } else {
        listChildren = (List) value;
      }
    } else {
      this.nodeValue = value;
    }
  }
}
