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

import com.appnativa.util.Helper;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.aStreamer;
import com.appnativa.util.iStructuredNode;

import com.google.j2objc.annotations.Weak;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Sequence represents an ordered collection of one or more types, some of
 * which can be optional.
 *
 * @author Don DeCoteau
 * @version   2.0
 */
public class SPOTSequence implements iSPOTElement, iSPOTConstants, Cloneable {
  private static HashMap       EMPTY_MAP = new HashMap(1);
  private static final boolean OPTIMIZE_RUNTIME;

  static {
    boolean opt = false;

    try {
      String s = System.getProperty("spot.optimize", null);

      if (s == null) {
        s = System.getProperty("jnlp.spot.optimize", null);
      }

      if (s != null) {
        opt = "true".equals(s);
      }
    } catch(Throwable e) {}

    OPTIMIZE_RUNTIME = opt;
  }

  /** map of null elements */
  HashMap _nullMap;

  /** map of null elements */
  HashMap _refClassMap;

  /** list of elements */
  protected IdentityArrayList _theElements = new IdentityArrayList();

  /** <code>true</code> if the empty XML tags are generated when the element is null (<code>false</code> by default) */
  protected boolean _outputEmptyXML = false;

  /** map of elements */
  protected HashMap _nameMap          = EMPTY_MAP;
  protected int     attributeSizeHint = 0;
  protected int     elementsSizeHint  = 0;

  /** element attributes */
  protected NoNullLinkedHashMap _attributes;

  /** <code>true</code> if the element can be made read-only (<code>true</code> by default) */
  protected boolean _canMakeReadOnly;

  /** defined element attributes */
  protected LinkedHashMap _defAttributes;
  protected String        _footerComment;
  protected String[]      _headerComment;

  /** <code>true</code> if the element is optional (<code>false</code> by default) */
  protected boolean _isOptional;

  /** <code>true</code> if the element is read-only (<code>false</code> by default) */
  protected boolean _isReadOnly;

  /** parent element */
  @Weak()
  protected iSPOTElement _parentElement;

  /** protected element attributes */
  protected Set _requiredAttributes;

  /** the name of the element */
  protected String _theName;

  /** the name of the package the element belongs to */
  protected String             _thePackageName;
  private boolean              _allowInvalidAttributes;
  private boolean              _attributeSet;
  private Object               _linkedData;
  private IdentityArrayList    _references;
  private iSPOTTemplateHandler _templateHandler;

  /**
   * Creates a new <code>Sequence</code> object with the specification that
   * the element represented by the object is mandatory.
   */
  public SPOTSequence() {
    this(true);
  }

  /**
   * Creates a new <code>Sequence</code> object
   *
   * @param optional <code>true</code> if the element the object represents is
   *        optional
   */
  public SPOTSequence(boolean optional) {
    _isOptional = optional;
  }

  /**
   * Creates a new <code>Sequence</code> object. Called by sub-classes
   *
   * @param optional <code>true</code> if the element the object represents
   *   is optional
   * @param setelements <code>true</code> if spot_setElements() should be called
   */
  protected SPOTSequence(boolean optional, boolean setelements) {
    _isOptional = optional;
  }

  public Object clone() {
    try {
      SPOTSequence e = (SPOTSequence) getClass().newInstance();

      e._isReadOnly             = false;
      e._isOptional             = _isOptional;
      e._theName                = _theName;
      e._footerComment          = _footerComment;
      e._headerComment          = _headerComment;
      e._allowInvalidAttributes = _allowInvalidAttributes;
      e._outputEmptyXML         = _outputEmptyXML;

      if (_refClassMap != null) {
        e._refClassMap = (HashMap) _refClassMap.clone();
      }

      if (_attributes != null) {
        e._attributes = new NoNullLinkedHashMap(_attributes);
      }

      if (_defAttributes != null) {
        e._defAttributes = new LinkedHashMap(_defAttributes);
      }

      e._attributeSet = this._attributeSet;
      e.spot_copySharedMemberValues(this);

      return e;
    } catch(Exception ex) {
      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      }

      throw new RuntimeException(ex);
    }
  }

  public boolean equals(Object element) {
    if (element == this) {
      return true;
    }

    if (!(element instanceof SPOTSequence)) {
      return false;
    }

    SPOTSequence seq = (SPOTSequence) element;

    if (!seq.spot_getClassName().equals(spot_getClassName())) {
      return false;
    }

    IdentityArrayList elements = ((SPOTSequence) element)._theElements;
    int               len      = elements.size();

    if (len != _theElements.size()) {
      return false;
    }

    if (!aSPOTElement.spot_attributesEqual(this, (iSPOTElement) element)) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      iSPOTElement o1 = (iSPOTElement) elements.get(i);
      iSPOTElement o2 = (iSPOTElement) _theElements.get(i);

      if ((o1 == null) || (o2 == null)) {
        if (o1 != o2) {
          return false;
        }
      } else {
        if ((o2.spot_valueWasSet() || o1.spot_valueWasSet()) &&!o1.equals(o2)) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean fromSDF(SDFNode node) throws SPOTException {
    if (node == null) {
      return false;
    }

    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _linkedData    = node.getLinkedData();
    _footerComment = node.nodeComment;

    iSPOTTemplateHandler th = spot_getTemplateHandler();

    if (th != null) {
      th.applyTemplate(this, node);
    }

    try {
      if (node.hasAttributes()) {
        if (_attributes == null) {
          createAttributesMap();
        }

        _attributes.putAll(node.getNodeAttributes());
        _attributeSet = true;
      }

      if (node.hasChildren()) {
        String       name;
        iSPOTElement ti    = null;
        List         nodes = node.getChildNodes();
        int          len   = nodes.size();
        int          n;
        String       subname = null;
        List         list    = null;

        for (int i = 0; i < len; i++) {
          node = (SDFNode) nodes.get(i);

          if (node.getNodeType() == SDFNode.NODETYPE_COMMENT) {
            if (list == null) {
              list = new ArrayList();
            }

            list.add(node.getNodeName());

            continue;
          }

          name = node.getNodeName();
          n    = (name == null)
                 ? -1
                 : name.indexOf('-');

          if (n != -1) {
            subname = name.substring(n + 1);
            name    = name.substring(0, n);
          }

          ti = spot_elementFromName(name);

          if (n != -1) {
            node = node.createBlockFromThis(name, subname);
          }

          if (ti != null) {
            if (!ti.fromSDF(node)) {
              return false;
            }

            if ((list != null) && (list.size() > 0)) {
              ti.spot_setHeaderComments((String[]) list.toArray(new String[list.size()]));
              list.clear();
            }
          }
        }
      }

      return true;
    } finally {
      if (th != null) {
        th.popContextTemplate(this);
      }
    }
  }

  public void fromStream(InputStream in) throws IOException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int          n  = _theElements.size();
    iSPOTElement ti = null;

    if (n > 0) {
      for (int i = 0; i < n; i++) {
        if (aStreamer.readBoolean(in)) {
          ti = (iSPOTElement) _theElements.get(i);
          ti.fromStream(in);
        }
      }
    }
  }

  public boolean fromStructuredNode(iStructuredNode node) throws SPOTException {
    if (node == null) {
      return false;
    }

    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _linkedData    = node.getLinkedData();
    _footerComment = node.getComment();

    iSPOTTemplateHandler th = spot_getTemplateHandler();

    if (th != null) {
      th.applyTemplate(this, node);
    }

    try {
      if (node.hasAttributes()) {
        if (_attributes == null) {
          createAttributesMap();
        }

        node.copyAttributes(_attributes);
        _attributeSet = true;
      }

      _theName = node.getName();

      String       name = null;
      iSPOTElement ti   = null;

      node = node.getFirstSignificantChild();

      if (node != null) {
        do {
          name = node.getName();
          ti   = spot_elementFromName(name);

          if (ti != null) {
            if (!ti.fromStructuredNode(node)) {
              return false;
            }
          }
        } while((node = node.getNextSibling()) != null);
      }

      return true;
    } finally {
      if (th != null) {
        th.popContextTemplate(this);
      }
    }
  }

  @Override
  public int hashCode() {
    return _theElements.hashCode();
  }

  /**
   *  Adds all of the attributes in the specified map to this element attributes
   *
   *  @param map the map of attributes to add
   */
  public void spot_addAttributes(Map map) {
    if ((map != null) && (map.size() > 0)) {
      if (_attributes == null) {
        createAttributesMap();
      }

      _attributes.putAll(map);
      _attributeSet = true;
    }
  }

  /**
   * Adds an element to this <code>Sequence</code> object
   *
   * @param name the name of the element to add
   * @param element The element (specified as an interface)
   */
  public void spot_addElement(String name, iSPOTElement element) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (_nameMap == EMPTY_MAP) {
      _nameMap = new HashMap((elementsSizeHint > 5)
                             ? elementsSizeHint * 2
                             : 10, 1);
    }

    if (element == null) {
      Integer in = Integer.valueOf(_theElements.size());

      _nameMap.put(name, in);
      _theElements.add(null);

      if (_nullMap == null) {
        _nullMap = new HashMap();
      }

      _nullMap.put(in, name);
    } else {
      element.spot_setParent(this);

      if (_nameMap.put(name, element) != element) {
        _theElements.add(element);
        element.spot_setName(name);
      }
    }
  }

  /**
   * Copies the member values of the specified sequence that have the same names as
   * members of this sequence
   *
   * @param seq The sequence to copy from
   */
  public void spot_applyTemplate(SPOTSequence seq) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int          n    = seq._theElements.size();
    String       name = null;
    iSPOTElement ti   = null;
    iSPOTElement ti2  = null;

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) seq._theElements.get(i);

      if (ti != null) {
        name = ti.spot_getName();
        ti2  = spot_elementFor(name);

        if (ti2 != null) {
          ti2.spot_copy(ti, true);
        } else if (_nameMap.containsKey(name)) {
          this.spot_setReferenceVariable(name, (iSPOTElement) ti.clone());
        }
      }
    }
  }

  public final boolean spot_attributesWereSet() {
    return _attributeSet;
  }

  public int spot_checkRangeValidity() {
    int               n   = _theElements.size();
    int               ret = 0;
    iSPOTElement      ti;
    boolean           hasNonOptionNull = false;
    boolean           hasFieldSet      = false;
    IdentityArrayList refs             = _references;
    boolean           reference;

    for (int i = 0; i < n; i++) {
      ti        = (iSPOTElement) _theElements.get(i);
      reference = (ti != null) && (refs != null) && refs.contains(ti);
      ret       = (ti == null)
                  ? VALUE_NULL_AND_OPTIONAL
                  : ti.spot_checkRangeValidity();

      switch(ret) {
        case VALUE_TO_BIG :
        case VALUE_TO_SMALL :
        case VALUE_INVALID_CHILD :
        case VALUE_MISSING_REQUIRED_ATTTRIBUTES :
          return ret;

        case VALUE_NULL :                 // this means that the value is null
          if (!reference) {
            if (hasFieldSet) {
              return VALUE_NULL;
            }

            hasNonOptionNull = true;

            break;
          }
        //$FALL-THROUGH$
        case VALUE_NULL_AND_OPTIONAL :    // this means that the value is null and optional
        case VALUE_NULL_WITH_DEFAULT :    // this means that the value is null and has a default
          if (!reference) {
            continue;
          }
        //$FALL-THROUGH$
        default :                         // this means that the value is good

          /**
           * if we have any valid values and we previously encountered an
           * non-optional value that was null then the recor is invalid;
           */
          if (hasNonOptionNull) {
            return VALUE_NULL;
          }

          hasFieldSet = true;

          break;
      }
    }

    if (spot_attributesWereSet()) {
      hasFieldSet = true;
    }

    if (_isOptional &&!hasFieldSet) {
      return VALUE_NULL_AND_OPTIONAL;
    }

    return (!hasFieldSet)
           ? VALUE_NULL
           : VALUE_OK;
  }

  public String spot_checkRangeValidityStr() {
    if (!aSPOTElement.spot_requiredAttributesWereSet(_attributes, _requiredAttributes)) {
      return Helper.expandString(STR_MISSING_ATTRIBUTES, spot_getName(),
                                 aSPOTElement.spot_getMissingReqiredAttributes(_attributes, _requiredAttributes));
    }

    int          n   = _theElements.size();
    int          ret = 0;
    iSPOTElement ti;
    boolean      hasNonOptionalNull      = false;
    boolean      hasFieldSet             = false;
    int          hasNonOptionalNullIndex = -1;

    for (int i = 0; i < n; i++) {
      ti  = (iSPOTElement) _theElements.get(i);
      ret = (ti == null)
            ? VALUE_NULL_AND_OPTIONAL
            : ti.spot_checkRangeValidity();

      switch(ret) {
        case VALUE_TO_SMALL :
        case VALUE_TO_BIG :
        case VALUE_INVALID_CHILD :
        case VALUE_MISSING_REQUIRED_ATTTRIBUTES :
          return ti.spot_checkRangeValidityStr();

        case VALUE_NULL :                 // this means that the value is null
          if (hasFieldSet) {
            if (hasNonOptionalNullIndex == -1) {
              hasNonOptionalNullIndex = i;
            }

            ti = (iSPOTElement) _theElements.get(hasNonOptionalNullIndex);

            return ti.spot_checkRangeValidityStr();
          }

          hasNonOptionalNull      = true;
          hasNonOptionalNullIndex = i;

          break;

        case VALUE_NULL_AND_OPTIONAL :    // this means that the value is null and optional
        case VALUE_NULL_WITH_DEFAULT :    // this means that the value is null with a default
          continue;
        default :                         // this means that the value is good
          /*
           *  if we have any valid values and we previously encountered an non-optional value
           *       that was null then the record is invalid;
           */
          if (hasNonOptionalNull) {
            ti = (iSPOTElement) _theElements.get(hasNonOptionalNullIndex);

            return ti.spot_checkRangeValidityStr();
          }

          hasFieldSet = true;

          break;
      }
    }

    if (_isOptional &&!hasFieldSet) {
      return null;
    }

    return (!hasFieldSet)
           ? Helper.expandString(STR_NULL, spot_getName())
           : null;
  }

  public void spot_cleanAttributes() {
    if (_attributes != null) {
      if (Helper.valuesEquals(_attributes, _defAttributes)) {
        _attributeSet = false;
      }
    }
  }

  public void spot_clear() {
    iSPOTElement ti = null;

    if ((_references != null) && (_references.size() > 0)) {
      int len = _references.size() - 1;

      for (int i = len; i >= 0; i--) {
        iSPOTElement e = (iSPOTElement) _references.get(i);

        spot_setReferenceVariable(e.spot_getName(), null);
      }
    }

    int len = _theElements.size();

    for (int i = 0; i < len; i++) {
      ti = (iSPOTElement) _theElements.get(i);

      if (ti != null) {
        ti.spot_clear();
      }
    }

    spot_resetAttributes();
  }

  public void spot_clearAttributes() {
    if (_attributes != null) {
      _attributes.clear();
    }

    _attributes   = null;
    _attributeSet = false;
  }

  public void spot_clearReferenceVariable(iSPOTElement e) {
    if ((e == null) || (_references == null) || _references.isEmpty()) {
      return;
    }

    int n = _references.indexOf(e);

    if (n != -1) {
      spot_setReferenceVariable(e.spot_getName(), null);
    }
  }

  public void spot_copy(iSPOTElement element) {
    spot_copy(element, false);
  }

  public void spot_copy(iSPOTElement element, boolean newinstance) {
    if (!(element instanceof SPOTSequence)) {
      return;
    }

    if (!newinstance) {
      if (!OPTIMIZE_RUNTIME) {
        checkReadOnly();
      }

      spot_clear();
    }

    SPOTSequence e = (SPOTSequence) element;

    spot_copySharedMemberValuesEx(e);
    this._isReadOnly             = false;
    this._isOptional             = e._isOptional;
    this._theName                = e._theName;
    this._footerComment          = e._footerComment;
    this._headerComment          = e._headerComment;
    this._linkedData             = e._linkedData;
    this._allowInvalidAttributes = e._allowInvalidAttributes;
    this._outputEmptyXML         = e._outputEmptyXML;

    if (e._attributes != null) {
      this._attributes = new NoNullLinkedHashMap(e._attributes);
    }

    if (!newinstance && (e._defAttributes != null)) {
      this._defAttributes = new LinkedHashMap(e._defAttributes);
    }

    this._attributeSet = e._attributeSet;
  }

  /**
   * Copies the member values of the specified list that have the same names as
   * members of this sequence
   *
   * @param list The list to copy from
   */
  public void spot_copySharedMemberValues(List list) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int          n    = (list == null)
                        ? 0
                        : list.size();
    String       name = null;
    iSPOTElement ti   = null;
    iSPOTElement ti2  = null;

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) list.get(i);

      if (ti != null) {
        name = ti.spot_getName();
        ti2  = spot_elementFor(name);

        if (ti2 != null) {
          ti2.spot_copy(ti, true);
        } else if (_nameMap.containsKey(name)) {
          this.spot_setReferenceVariable(name, (iSPOTElement) ti.clone());
        }
      }
    }
  }

  /**
   * Copies the member values of the specified sequence that have the same names as
   * members of this sequence
   *
   * @param seq The sequence to copy from
   */
  public void spot_copySharedMemberValues(SPOTSequence seq) {
    spot_copySharedMemberValues(seq._theElements);
  }

  /**
   * Copies the member values of the specified list that have the same names as
   * members of this sequence. Only elements that have been explicitly set are copied
   *
   * @param list the list to output the values to
   */
  public void spot_copySharedMemberValuesEx(List list) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int          n    = (list == null)
                        ? 0
                        : list.size();
    String       name = null;
    iSPOTElement ti   = null;
    iSPOTElement ti2  = null;

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) list.get(i);

      if ((ti != null) && ti.spot_valueWasSet()) {
        name = ti.spot_getName();
        ti2  = spot_elementFor(name);

        if (ti2 != null) {
          ti2.spot_copy(ti, true);
        } else if (_nameMap.containsKey(name)) {
          this.spot_setReferenceVariable(name, (iSPOTElement) ti.clone());
        }
      }
    }
  }

  /**
   * Copies the member values of the specified sequence that have the same names as
   * members of this sequence. Only elements that have been explicitly set are copied
   *
   * @param seq The sequence to copy from
   */
  public void spot_copySharedMemberValuesEx(SPOTSequence seq) {
    spot_copySharedMemberValuesEx(seq._theElements);
  }

  public void spot_defineAttributes(Map attributes) {
    if (!OPTIMIZE_RUNTIME) {
      if (_defAttributes == null) {
        _defAttributes = new NoNullLinkedHashMap((attributeSizeHint > 5)
                ? attributeSizeHint
                : 10);
      }
    }

    if (_attributes == null) {
      _attributes = new NoNullLinkedHashMap();
    }

    Iterator<Entry> it = attributes.entrySet().iterator();

    while(it.hasNext()) {
      Entry  e     = it.next();
      Object key   = e.getKey();
      Object value = e.getValue();

      if (_defAttributes != null) {
        _defAttributes.put(key, value);
      }

      if (value != null) {
        _attributes.put(key, value);
      }
    }
  }

  public void spot_defineAttribute(String name, String defaultValue) {
    if (!OPTIMIZE_RUNTIME || (defaultValue != null)) {
      if (_defAttributes == null) {
        _defAttributes = new LinkedHashMap((attributeSizeHint > 5)
                                           ? attributeSizeHint
                                           : 10);
      }

      name = spot_handleRequiredAttribute(name);
      _defAttributes.put(name, defaultValue);
    }

    if (defaultValue != null) {
      if (_attributes == null) {
        _attributes = new NoNullLinkedHashMap();
      }

      _attributes.put(name, defaultValue);
    }
  }

  /**
   * Retrieves the object at the specified position
   *
   * @param pos The position
   *
   * @return The object; otherwise <code>null</code>
   */
  public iSPOTElement spot_elementAt(int pos) {
    return (iSPOTElement) _theElements.get(pos);
  }

  /**
   * Retrieves the element value
   *
   * @param name The name of the object/element
   *
   * @return The element
   */
  public iSPOTElement spot_elementFor(String name) {
    Object o = _nameMap.get(name);

    if ((o == null) || (o instanceof Integer)) {
      return null;
    }

    return (iSPOTElement) o;
  }

  /**
   * Retrieves the element value. If the element name refers to a reference value that is null
   * a new instance will be created and assigned to the specified name and that value will be returned
   *
   * @param name The name of the object/element
   *
   * @return The element
   */
  public iSPOTElement spot_elementForEx(String name) {
    return spot_elementFromName(name);
  }

  public iSPOTElement spot_elementValue() {
    return this;
  }

  public boolean spot_equals(iSPOTElement e) {
    return e == this;
  }

  public String spot_getAttribute(String attribute) {
    if (_attributes == null) {
      return null;
    }

    return (String) _attributes.get(attribute);
  }

  public int spot_getAttributeCount() {
    return (_attributes == null)
           ? 0
           : _attributes.size();
  }

  public String spot_getAttributeDefaultValue(String name) {
    return (String) ((_defAttributes == null)
                     ? null
                     : _defAttributes.get(name));
  }

  public Map spot_getAttributes() {
    return (_attributes == null)
           ? null
           : Collections.unmodifiableMap(_attributes);
  }

  public Map spot_getAttributesEx() {
    return _attributes;
  }

  public String spot_getClassName() {
    return getClass().getName();
  }

  /**
   * Retrieves the short name of this class
   *
   * @return the short name of this class
   */
  public String spot_getClassShortName() {
    return aSPOTElement.spot_getClassShortName(this.getClass());
  }

  /**
   *  Returns the number of sub-elements (child objects) present/contained
   *  within this object
   *
   *  @return the number of sub-elements(child objects)
   */
  public int spot_getCount() {
    return _theElements.size();
  }

  public String spot_getFooterComment() {
    return _footerComment;
  }

  public String[] spot_getHeaderComments() {
    return _headerComment;
  }

  public Object spot_getLinkedData() {
    return _linkedData;
  }

  public String spot_getName() {
    if (_theName == null) {
      _theName = this.getClass().getName();

      int i = _theName.lastIndexOf('.');

      if (i != -1) {
        StringBuilder buf = new StringBuilder(_theName);

        buf.setCharAt(i, ':');
        _theName = buf.toString();
      }
    }

    return _theName;
  }

  public String spot_getNameAt(int pos) {
    iSPOTElement x = (iSPOTElement) _theElements.get(pos);

    if (x != null) {
      return x.spot_getName();
    }

    Entry    e;
    Iterator it = _nameMap.entrySet().iterator();

    while(it.hasNext()) {
      e = (Entry) it.next();

      if (e.getValue() instanceof Integer) {
        if (((Integer) e.getValue() == pos)) {
          return (String) e.getKey();
        }
      }
    }

    return null;
  }

  /**
   * Sets the name of the package the element belongs to
   *
   * @param name the name
   */
  public String spot_getPackageName() {
    if (_thePackageName == null) {
      _thePackageName = SPOTHelper.getPackageName(getClass());
    }

    return _thePackageName;
  }

  public iSPOTElement spot_getParent() {
    return _parentElement;
  }

  public Object[] spot_getRange() {
    return null;
  }

  public List spot_getSortedElements() {
    IdentityArrayList list = new IdentityArrayList(_theElements);
    Comparator        c    = new Comparator() {
      public int compare(Object o1, Object o2) {
        iSPOTElement e1 = (iSPOTElement) o1;
        iSPOTElement e2 = (iSPOTElement) o2;
        String       s1 = (e1 == null)
                          ? null
                          : e1.spot_getName();
        String       s2 = (e2 == null)
                          ? null
                          : e2.spot_getName();

        if ((s1 == null) || (s2 == null)) {
          if (s1 == s2) {
            return 0;
          }

          return (s1 == null)
                 ? -1
                 : 1;
        }

        return s1.compareTo(s2);
      }
    };

    Collections.sort(list, c);

    return list;
  }

  public Map spot_getSupportedAttributes() {
    return (_defAttributes == null)
           ? null
           : Collections.unmodifiableMap(_defAttributes);
  }

  public iSPOTTemplateHandler spot_getTemplateHandler() {
    if (_templateHandler != null) {
      return _templateHandler;
    }

    return (_parentElement == null)
           ? null
           : _parentElement.spot_getTemplateHandler();
  }

  public final int spot_getType() {
    return SPOT_TYPE_SEQUENCE;
  }

  public String spot_getValidityRange() {
    return "";
  }

  public Object spot_getValue() {
    return null;
  }

  /**
   * Gets the value of the element with the specifeid name
   * @param name the name of the element whose value is to be returned
   *
   * @return the value for the specified named element or null if the element does not exits
   */
  public Object spot_getValue(String name) {
    iSPOTElement e = spot_elementFor(name);

    return (e == null)
           ? null
           : e.spot_getValue();
  }

  /**
   * Copies the member values that have been explicitly set to the specified list
   *
   * @param list the list to output the values to
   */
  public void spot_getValuesThatWereSet(List list) {
    int          n  = _theElements.size();
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) _theElements.get(i);

      if ((ti != null) && ti.spot_valueWasSet()) {
        list.add(ti);
      }
    }
  }

  public boolean spot_hasAttributes() {
    return ((_attributes != null) && (_attributes.size() > 0));
  }

  public boolean spot_hasDefinedAttributes() {
    return ((_defAttributes != null) && (_defAttributes.size() > 0));
  }

  /**
   * Returns whether the sequence has an element with the specified name
   *
   * @param name The name of the object/element
   *
   * @return true if the sequence contains an element with the specified name; false otherwise
   */
  public boolean spot_hasNamedElement(String name) {
    return _nameMap.containsKey(name);
  }

  public boolean spot_hasValue() {
    int n = spot_checkRangeValidity();

    return (n == VALUE_NULL_WITH_DEFAULT) || (n == VALUE_OK);
  }

  public boolean spot_isAllowInvalidAttributes() {
    return _allowInvalidAttributes;
  }

  public boolean spot_isAttributeSupported(String name) {
    return ((_defAttributes == null) || _defAttributes.containsKey(name));
  }

  public boolean spot_isContainer() {
    return true;
  }

  public boolean spot_isOptional() {
    return _isOptional;
  }

  public boolean spot_isReadOnly() {
    return _isReadOnly;
  }

  public boolean spot_isRequiredAttribute(String name) {
    return (_requiredAttributes == null)
           ? false
           : _requiredAttributes.contains(name);
  }

  public void spot_makeReadOnly() {
    if (_canMakeReadOnly &&!_isReadOnly) {
      _isReadOnly = true;

      int          n  = _theElements.size();
      iSPOTElement ti = null;

      for (int i = 0; i < n; i++) {
        ti = (iSPOTElement) _theElements.get(i);

        if (ti != null) {
          ti.spot_makeReadOnly();
        }
      }
    }
  }

  /**
   * Retrieves the name of the object at the specified position.
   *
   * @param pos The position of the element/object
   *
   * @return The name of the object/element.
   */
  public String spot_nameAt(int pos) {
    iSPOTElement lasn = (iSPOTElement) _theElements.get(pos);

    if (lasn == null) {
      return ((_nullMap == null) || (pos >= spot_getCount()))
             ? null
             : (String) _nullMap.get(Integer.valueOf(pos));
    }

    return lasn.spot_getName();
  }

  public void spot_removeAttribute(String name) {
    if (_attributes != null) {
      _attributes.remove(name);
    }
  }

  public void spot_resetAttribute(String name, boolean clean) {
    if (_attributes != null) {
      if (_defAttributes != null) {
        _attributes.put(name, _defAttributes.get(name));

        if (clean && Helper.valuesEquals(_attributes, _defAttributes)) {
          _attributeSet = false;
        }
      }
    }
  }

  public void spot_resetAttributes() {
    if (_attributes != null) {
      _attributes.clear();

      if (_defAttributes != null) {
        _attributes.putAll(_defAttributes);
      }
    }

    _attributeSet = false;
  }

  /**
   * Retrieves the named object as a/an <code>Sequence</code> object
   *
   * @param name The name of the object/element
   *
   * @return The <code>Sequence</code> object; otherwise <code>null</code>
   */
  public SPOTSequence spot_sequenceFor(String name) {
    return (SPOTSequence) spot_elementFor(name);
  }

  public void spot_setAllowInvalidAttributes(boolean allow) {
    this._allowInvalidAttributes = allow;
  }

  public void spot_setAttribute(String name, String value) {
    if (!OPTIMIZE_RUNTIME &&!_allowInvalidAttributes && (_defAttributes != null) &&!_defAttributes.containsKey(name)) {
      throw new SPOTException(INVALID_ELEMENT, STR_INVALID_ATTRIBUTE, (_theName == null)
              ? getClass().getName()
              : _theName, name);
    }

    if (_attributes == null) {
      createAttributesMap();
    }

    _attributeSet = true;
    _attributes.put(name, value);
  }

  /**
   * Sets whether empty XML tags are generated when the element is null (<code>false</code> by default)
   *
   * @param flag <code>true</code> to output empty tags; <code>false</code> otherwise
   */
  public void spot_setEmptyXMLOutput(boolean flag) {
    _outputEmptyXML = flag;
  }

  public void spot_setFooterComment(String comment) {
    _footerComment = comment;
  }

  public void spot_setHeaderComments(String[] comments) {
    _headerComment = comments;
  }

  public Object spot_setLinkedData(Object data) {
    Object o = _linkedData;

    _linkedData = data;

    return o;
  }

  public void spot_setName(String name) {
    _theName = name;
  }

  public void spot_setOptional(boolean b) {}

  /**
   * Sets the name of the package the element belongs to
   *
   * @param name the name
   */
  public void spot_setPackageName(String name) {
    _thePackageName = name;
  }

  public void spot_setParent(iSPOTElement element) {
    _parentElement = element;
  }

  /**
   * Sets the value of a referenced variable
   *
   * @param name the name of the variable
   * @param element iSPOTElement the element representing the field
   */
  public void spot_setReferenceVariable(String name, iSPOTElement element) {
    SPOTHelper.setReferenceVariable(this, name, element);
  }

  public void spot_setTemplateHandler(iSPOTTemplateHandler templateHandler) {
    this._templateHandler = templateHandler;
  }

  public void spot_setValue(String val) {}

  public String spot_stringValue() {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, "Sequence");
  }

  public String spot_stringValueEx() {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, "Sequence");
  }

  public boolean spot_valueWasSet() {
    int n = spot_checkRangeValidity();

    return (n == VALUE_OK);
  }

  public String toSDF() {
    return toString(null);
  }

  public boolean toSDF(Writer out) throws IOException {
    return toSDF(out, getClass().getName(), 0, true, false, true);
  }

  public boolean toSDF(Writer out, String tag) throws IOException {
    return toSDF(out, tag, 0, true, false, true);
  }

  public boolean toSDF(Writer out, String tag, int depth, boolean outputempty, boolean outputComments)
          throws IOException {
    return toSDF(out, tag, depth, true, outputempty, outputComments);
  }

  public boolean toSDF(Writer out, String classname, int depth, boolean validate, boolean outputempty,
                       boolean outputComments)
          throws IOException {
    if ((_headerComment != null) && outputComments) {
      int len = _headerComment.length;

      for (int i = 0; i < len; i++) {
        aSPOTElement.writeSDFName(out, _headerComment[i], depth);
        out.write("\n");
      }
    }

    int n = _theElements.size();

    if ((n == 0) &&!outputempty &&!_attributeSet) {
      if ((depth == 0) && (classname != null)) {
        out.write(classname);
        out.write("{}\n");
      }

      return true;
    }

    String       name = null;
    iSPOTElement ti   = null;

    if (outputempty) {
      validate = false;
    }

    int check = validate
                ? spot_checkRangeValidity()
                : 0;

    if ((check < VALUE_NULL) && (check != VALUE_INVALID_CHILD)) {
      if ((depth == 0) && (classname != null)) {
        out.write(classname);
        out.write("{}\n");

        return true;
      }

      return false;
    }

    if (classname == null) {
      classname = "{\n";
    } else {
      classname += " {\n";
    }

    if (check != 0) {
      aSPOTElement.writeSDFName(out, classname, depth);
      Helper.writePadding(out, depth + 1);
      out.write(spot_checkRangeValidityStr());
      out.write("\n");
      Helper.writePadding(out, depth);
      out.write("}\n");

      return true;
    }

    aSPOTElement.writeSDFName(out, classname, depth);

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) _theElements.get(i);

      if (ti == null) {
        continue;
      }

      name = ti.spot_getName();

      if (!ti.toSDF(out, name, depth + 1, outputempty, outputComments) && (_references != null)
          && _references.contains(ti)) {
        Helper.writePadding(out, depth + 1);
        out.write(name);
        out.write("{}\n");
      }
    }

    Helper.writePadding(out, depth);
    out.write("}");
    aSPOTElement.writeAttributes(out, _attributes, _defAttributes, depth);

    if ((_footerComment != null) && outputComments) {
      out.write(" ");
      out.write(_footerComment);
    }

    out.write("\n");

    return true;
  }

  public void toStream(OutputStream out) throws IOException {
    int          n     = _theElements.size();
    iSPOTElement ti    = null;
    int          check = spot_checkRangeValidity();

    if (check == VALUE_NULL_AND_OPTIONAL) {
      return;
    }

    if (check != 0) {
      throw new IOException(spot_checkRangeValidityStr());
    }

    if (n > 0) {
      for (int i = 0; i < n; i++) {
        ti    = (iSPOTElement) _theElements.get(i);
        check = (ti == null)
                ? VALUE_NULL_AND_OPTIONAL
                : ti.spot_checkRangeValidity();

        if (check == VALUE_NULL_AND_OPTIONAL) {
          aStreamer.toStream(false, out);
        } else {
          aStreamer.toStream(true, out);
          ti.toStream(out);
        }
      }
    }
  }

  /**
   * Converts the object to a <code>String</code> object
   *
   * @return the string representation of the object
   */
  public String toString() {
    return toString(null);
  }

  /**
   * Converts the object to a <code>String</code> object
   *
   * @param classname the name of the class to use as the SDF element name
   * @return the string representation of the object
   */
  public String toString(String classname) {
    java.io.StringWriter sw = new java.io.StringWriter();

    try {
      if (classname == null) {
        classname = aSPOTElement.spot_getClassShortName(getClass());

        int n = classname.lastIndexOf('.');

        if (n != -1) {
          classname = classname.substring(n + 1);
        }
      }

      toSDF(sw, classname, 0, false, true);
    } catch(IOException ex) {}

    return sw.toString();
  }

  String spot_makeInvalidMessage(String element, String value, String range, String type) {
    String[] s = { element, value, type, range };

    return Helper.expandString(STR_INVALID, s);
  }

  /**
   * Checks is the element is read-only and throws an exception if it is
   * @throws SPOTException if the element is read only
   */
  protected void checkReadOnly() throws SPOTException {
    if (_isReadOnly) {
      throw new SPOTException(READ_ONLY, STR_READ_ONLY, (_theName == null)
              ? getClass().getName()
              : _theName);
    }
  }

  /**
   * Gets the SPOT element for the specified java field name
   *
   * @param name the name
   *
   * @return the associated element
   */
  protected iSPOTElement spot_elementFromName(String name) {
    Object o = _nameMap.get(name);

    if (o == null) {
      return null;
    }

    if (o instanceof iSPOTElement) {
      return (iSPOTElement) o;
    }

    if (_refClassMap == null) {
      _refClassMap = new HashMap();
    }

    return SPOTHelper.elementFromName(_refClassMap, this, name);
  }

  protected String spot_handleRequiredAttribute(String name) {
    if ((name != null) && name.endsWith("!")) {
      name = name.substring(0, name.length() - 1);

      if (_requiredAttributes == null) {
        _requiredAttributes = new HashSet();
      }
    }

    return name;
  }

  /**
   * Initialized the object
   */
  protected void spot_initialize() {}

  protected void spot_setElements() {}

  /**
   * Sets the value of a named referenced
   *
   * @param name the name of the reference
   * @param element iSPOTElement the element representing the field
   *
   * @return the previous element
   */
  protected iSPOTElement spot_setReference(String name, iSPOTElement element) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    iSPOTElement x = null;
    Object       o = _nameMap.get(name);

    if (o == null) {
      throw new IllegalStateException("Setting a reference for an element that has not yet been defined");
    }

    if (o instanceof iSPOTElement) {
      x = (iSPOTElement) o;

      if (element == null) {
        Integer in = Integer.valueOf(_theElements.indexOf(x));

        if (in.intValue() == -1) {    // should never happen unless _nameMap or _theElements are modified outside this class
          throw new IllegalStateException("The names map is out of sync with the elements list");
        }

        x.spot_setParent(null);
        _nameMap.put(name, in);
        _theElements.set(in.intValue(), null);

        if (_references != null) {
          _references.remove(x);
        }
      } else {
        _nameMap.put(name, element);
        element.spot_setName(name);
        element.spot_setParent(this);
      }
    } else {
      if (element != null) {
        Integer in = (Integer) o;

        _nameMap.put(name, element);
        element.spot_setName(name);
        element.spot_setParent(this);
        _theElements.set(in.intValue(), element);
      }
    }

    if (element != null) {
      if (_references == null) {
        _references = new IdentityArrayList();
      }

      if (!_references.contains(element)) {
        _references.add(element);
      }
    }

    return x;
  }

  private void createAttributesMap() {
    if (_defAttributes != null) {
      _attributes = new NoNullLinkedHashMap(_defAttributes.size());
      _attributes.putAll(_defAttributes);
    } else {
      _attributes = new NoNullLinkedHashMap();
    }
  }
}
