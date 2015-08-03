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
import com.appnativa.util.SNumber;
import com.appnativa.util.aStreamer;
import com.appnativa.util.iStructuredNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Set represents an unordered collection of zero or more occurrences of a given
 * type. This can be thought of as and array of _aElements.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class SPOTSet extends aSPOTElement implements List {

  /** the maximum range of the set */
  protected long _nRangeMax = -1;

  /** the minimum range of the set */
  protected long _nRangeMin = -1;

  /** the elements in the set */
  protected IdentityArrayList _theElements = new IdentityArrayList();

  /** the class for the elements in the set */
  protected Class _clsDefinedBy;

  /** the name for the elements in the set */
  protected String _elementName;

  /** the attributes defined for the elements in the set */
  protected Map _elementsDefinedAtributes;

  /** the string representation class for the elements in the set */
  protected String _strElementType;
  private boolean  _isAnySet = false;
  private SPOTAny  _anyPrototype;

  /**
   * Creates a new <code>Set</code> object with the specification that the
   * element represented by the object is mandatory.
   */
  public SPOTSet() {
    this(true);
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param optional
   *          <code>true</code> if the element the object represents is optional
   */
  public SPOTSet(boolean optional) {
    _isOptional = optional;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param cls
   *          The class of object
   */
  public SPOTSet(String name, Class cls) {
    setType(name, cls);
    _isOptional = false;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param sclass
   *          The class name of object
   */
  public SPOTSet(String name, String sclass) {
    setType(name, sclass);
    _isOptional = false;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param sclass
   *          The class name of object
   * @param optional
   *          <code>true</code> if the element the object represents is optional
   */
  public SPOTSet(String name, String sclass, boolean optional) {
    setType(name, sclass);
    _isOptional = optional;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param sclass
   *          The class name of object
   * @param max
   *          The object's maximum acceptable value
   */
  public SPOTSet(String name, String sclass, long max) {
    _nRangeMax = max;
    setType(name, sclass);
    _isOptional = false;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param sclass
   *          The class name of object
   * @param max
   *          The object's maximum acceptable value
   * @param optional
   *          <code>true</code> if the element the object represents is optional
   */
  public SPOTSet(String name, String sclass, long max, boolean optional) {
    _nRangeMax = max;
    setType(name, sclass);
    _isOptional = optional;
  }

  /**
   * Creates a new <code>Set</code> object
   *
   * @param name
   *          The name of the object/element
   * @param sclass
   *          The class name of object
   * @param min
   *          The object's minimum acceptable value
   * @param max
   *          The object's maximum acceptable value
   * @param optional
   *          <code>true</code> if the element the object represents is optional
   */
  public SPOTSet(String name, iSPOTElement sclass, int min, int max, boolean optional) {
    if (min > -2) {
      _nRangeMin = min;
    }

    if (max > -2) {
      _nRangeMax = max;
    }

    setType(name, sclass);
    _isOptional = optional;
  }

  /**
   * Adds the values to the set.
   *
   * @param val
   *          the value
   *
   */
  public void add(double val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(String.valueOf(val));
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Adds the values to the set.
   *
   * @param val
   *          the value
   *
   */
  public void add(int val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(String.valueOf(val));
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Adds an element to this<code>Set</code> object
   *
   * @param element
   *          The element (specified as an interface)
   */
  public boolean add(iSPOTElement element) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (this._isAnySet) {
      element = this.createAnyElement(element);
    } else if ((_clsDefinedBy != null) &&!_clsDefinedBy.isInstance(element)) {
      throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), element.getClass().getName());
    }

    element.spot_setParent(this);
    _theElements.add(element);

    return true;
  }

  /**
   * Adds the values to the set.
   *
   * @param val
   *          the value
   *
   */
  public void add(long val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(String.valueOf(val));
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Adds an element to this<code>Set</code> object
   *
   * @param element
   *          The element (specified as an interface)
   */
  public boolean add(Object element) {
    return add((iSPOTElement) element);
  }

  /**
   * Add the value to the set
   *
   * @param val
   *          the value
   *
   */
  public void add(SNumber val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(val.toString());
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Add the value to the set
   *
   * @param val
   *          the value
   *
   */
  public void add(String val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      itype.spot_setValue(val);
      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  public void add(int index, iSPOTElement element) {
    if (this._isAnySet) {
      element = this.createAnyElement(element);
    } else if ((_clsDefinedBy != null) &&!_clsDefinedBy.isInstance(element)) {
      throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), element.getClass().getName());
    }

    element.spot_setParent(this);

    if ((index < 0) || (index >= _theElements.size())) {
      _theElements.add(element);
    } else {
      _theElements.add(index, element);
    }
  }

  public void add(int index, Object element) {
    add(index, (iSPOTElement) element);
  }

  public boolean addAll(Collection c) {
    if (c != null) {
      Iterator it = c.iterator();

      while(it.hasNext()) {
        add(it.next());
      }
    }

    return true;
  }

  public boolean addAll(int index, Collection c) {
    if (c != null) {
      Iterator it = c.iterator();

      while(it.hasNext()) {
        add(it.next());
      }
    }

    return true;
  }

  /**
   * Returns a SPOTSet for holding SPOTAny elements
   *
   * @param name
   *          the name of this element
   *
   * @return the SPOTSet
   */
  public static SPOTSet anySet(String name) {
    return new SPOTSet(name, new SPOTAny(), -1, -1, true);
  }

  /**
   * Returns a SPOTSet for holding SPOTAny elements
   *
   * @param name
   *          the name of this element
   * @param anyclass
   *          the name of the class that the SPOTAny object will use to restrict
   *          its content
   * @return the SPOTSet
   */
  public static SPOTSet anySet(String name, String anyclass) {
    return new SPOTSet(name, new SPOTAny(anyclass), -1, -1, true);
  }

  /**
   * Returns a SPOTSet for holding SPOTAny elements
   *
   * @param name
   *          the name of this element
   * @param anyclass
   *          the name of the class that the SPOTAny object will use to restrict
   *          its content
   * @param min
   *          the minimum number of elements that the set must contain
   * @param max
   *          the maximum number of elements that the set must contain
   * @return the SPOTSet
   */
  public static SPOTSet anySet(String name, String anyclass, int min, int max) {
    SPOTSet set = new SPOTSet(name, new SPOTAny(anyclass), -1, -1, true);

    set.spot_setRange(min, max);

    return set;
  }

  /**
   * Returns the value of the element at the specified position as a boolean
   *
   * @param position
   *          the position
   *
   * @return the value
   */
  public boolean booleanValueAt(int position) {
    iSPOTElement ti = (iSPOTElement) _theElements.get(position);

    return (ti instanceof aSPOTElement)
           ? ((aSPOTElement) ti).booleanValue()
           : SNumber.booleanValue(ti.spot_stringValue());
  }

  /**
   * Retrieves the set's values as an array of booleans
   *
   * @return The array of booleans
   */
  public boolean[] booleanValues() {
    int          n  = _theElements.size();
    boolean[]    b  = new boolean[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = (iSPOTElement) _theElements.get(i);
      b[i] = (ti instanceof aSPOTElement)
             ? ((aSPOTElement) ti).booleanValue()
             : SNumber.booleanValue(ti.spot_stringValue());
    }

    return b;
  }

  public void clear() {
    _theElements.clear();
  }

  public Object clone() {
    SPOTSet e = (SPOTSet) super.clone();

    e._theElements = new IdentityArrayList();
    e.deepCopy(this);

    return e;
  }

  public boolean contains(Object o) {
    return _theElements.contains(o);
  }

  public boolean containsAll(Collection c) {
    return _theElements.containsAll(c);
  }

  /**
   * Copies the members of the specified set into this one
   *
   * @param set
   *          The set to copy from
   */
  public void copy(SPOTSet set) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();
    _theElements.addAll(set._theElements);
  }

  /**
   * Copies the members of the specified set into this one
   *
   * @param set
   *          The set to copy from
   */
  public void deepCopy(SPOTSet set) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    int          len = set.size();
    iSPOTElement e;

    for (int i = 0; i < len; i++) {
      e = set.getEx(i);

      if (e != null) {
        e = (iSPOTElement) e.clone();
      }

      add(e);
    }
  }

  /**
   * Returns the value of the element at the specified position as a
   * <code>double</code>
   *
   * @param position
   *          the position
   *
   * @return the value
   */
  public double doubleValueAt(int position) {
    iSPOTElement ti = (iSPOTElement) _theElements.get(position);

    return (ti instanceof aSPOTElement)
           ? ((aSPOTElement) ti).doubleValue()
           : SNumber.doubleValue(ti.spot_stringValue());
  }

  /**
   * Retrieves the set's values as an array of doubles
   *
   * @return The array of doubles
   */
  public double[] doubleValues() {
    int          n  = _theElements.size();
    double[]     d  = new double[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = (iSPOTElement) _theElements.get(i);
      d[i] = (ti instanceof aSPOTElement)
             ? ((aSPOTElement) ti).doubleValue()
             : SNumber.doubleValue(ti.spot_stringValue());
    }

    return d;
  }

  /**
   * Returns a SPOTSet for holding an SPOT element
   *
   * @param name
   *          the name of this element
   * @return the SPOTSet
   */
  public static SPOTSet elementSet(String name, iSPOTElement type) {
    return new SPOTSet(name, type, -1, -1, true);
  }

  public boolean equals(aSPOTElement element) {
    if (element == this) {
      return true;
    }

    if (!(element instanceof SPOTSet)) {
      return false;
    }

    IdentityArrayList elements = ((SPOTSet) element)._theElements;
    int               len      = elements.size();

    if (len != _theElements.size()) {
      return false;
    }

    if (!spot_attributesEqual(this, element)) {
      return false;
    }

    for (int i = 0; i < len; i++) {
      if (!elements.get(i).equals(_theElements.get(i))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Retrieves the set's values as an array of doubles
   *
   * @return The array of doubles
   */
  public float[] floatValues() {
    int          n  = _theElements.size();
    float[]      d  = new float[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = (iSPOTElement) _theElements.get(i);
      d[i] = (ti instanceof aSPOTElement)
             ? ((aSPOTElement) ti).floatValue()
             : SNumber.floatValue(ti.spot_stringValue());
    }

    return d;
  }

  public boolean fromSDF(SDFNode node) throws SPOTException {
    if (node == null) {
      return false;
    }

    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    spot_clear();

    if (node.hasChildren() || (node.getNodeValue() != null)) {
      String name;

      if (_strElementType == null) {
        name = node.getNodeAttribute("type");

        if ((name == null) && (_attributes != null)) {
          name = (String) _attributes.get("type");
        }

        _strElementType = aSPOTElement.spot_resolveClassName(this, (name == null)
                ? "SPOTPrintableString"
                : name);
      }
    }

    if (node.hasAttributes()) {
      _attributes = new NoNullLinkedHashMap();
      _attributes.putAll(node.getNodeAttributes());
    }

    Map defatt = ((_elementsDefinedAtributes == null) || _elementsDefinedAtributes.isEmpty())
                 ? null
                 : _elementsDefinedAtributes;

    if (node.hasChildren()) {
      iSPOTElement ti    = null;
      List         nodes = node.getChildNodes();
      int          len   = nodes.size();
      List         list  = null;

      for (int i = 0; i < len; i++) {
        node = (SDFNode) nodes.get(i);

        if (node.getNodeType() == SDFNode.NODETYPE_COMMENT) {
          if (list == null) {
            list = new ArrayList();
          }

          list.add(node.getNodeName());

          continue;
        }

        try {
          ti = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();
        } catch(Exception e) {
          if (e instanceof SPOTException) {
            throw(SPOTException) e;
          }

          throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
        }

        ti.spot_setParent(this);

        if (defatt != null) {
          ti.spot_defineAttributes(defatt);
        }

        if (!ti.fromSDF(node)) {
          return false;
        }

        if ((list != null) && (list.size() > 0)) {
          ti.spot_setHeaderComments((String[]) list.toArray(new String[list.size()]));
          list.clear();
        }

        add(ti);
      }
    } else if (node.getNodeValue() != null) {
      iSPOTElement ti;

      try {
        ti = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();
      } catch(Exception e) {
        if (e instanceof SPOTException) {
          throw(SPOTException) e;
        }

        throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
      }

      if (!ti.fromSDF(node)) {
        return false;
      }

      if (defatt != null) {
        ti.spot_defineAttributes(defatt);
      }

      add(ti);
    }

    return true;
  }

  public void fromStream(InputStream in) throws IOException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    int len = aStreamer.readInt(in);

    _theElements.clear();

    iSPOTElement ti = null;

    try {
      for (int i = 0; i < len; i++) {
        ti = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();
        ti.fromStream(in);
        _theElements.add(ti);
      }
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  public boolean fromStructuredNode(iStructuredNode node) throws SPOTException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    spot_clear();

    if (node.hasAttributes()) {
      _attributes = new NoNullLinkedHashMap();
      node.copyAttributes(_attributes);
    }

    String name;

    if (_strElementType == null) {
      name = null;

      if (_attributes != null) {
        name = (String) _attributes.get("type");
      }

      _strElementType = aSPOTElement.spot_resolveClassName(this, (name == null)
              ? "SPOTPrintableString"
              : name);
    }

    iSPOTElement ti = null;

    node = node.getFirstSignificantChild();

    do {
      name = node.getName();

      if (!name.equalsIgnoreCase(_elementName)) {
        throw new SPOTException(INVALID_ELEMENT, STR_INVALID_ELEMENT, _elementName, name);
      }

      try {
        ti = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();
      } catch(Exception e) {
        if (e instanceof SPOTException) {
          throw(SPOTException) e;
        }

        throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
      }

      ti.spot_setParent(this);

      if (!ti.fromStructuredNode(node)) {
        return false;
      }

      add(ti);
    } while((node = node.getNextSibling()) != null);

    return true;
  }

  @Override
  public int hashCode() {
    return _theElements.hashCode();
  }

  public int indexOf(Object o) {
    return _theElements.indexOf(o);
  }

  /**
   * Retrieves the index of element in the set This will cause SPOTAny objects
   * to be unwrapped and testedto match the spacified object (if the set ais a
   * <b>Any</b> set)
   *
   * @param e
   *          the element to look for
   * @return the index of the specified element of -1 if the element is not
   *         found
   */
  public int indexOfEx(iSPOTElement e) {
    int n = _theElements.size();

    for (int i = 0; i < n; i++) {
      if (e == ((iSPOTElement) _theElements.get(i)).spot_elementValue()) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Retrieves the index of element in the set whose string value matches the
   * specified value. This will cause SPOTAny objects to be unwrapped and tested
   * to match the spacified object (if the set is an <b>Any</b> set)
   *
   * @param value
   *          the value to look for
   * @return the index of the specified element of -1 if the element is not
   *         found
   */
  public int indexOfStringValueEx(String value) {
    int n = _theElements.size();

    for (int i = 0; i < n; i++) {
      iSPOTElement e = ((iSPOTElement) _theElements.get(i)).spot_elementValue();

      if ((e != null) && value.equals(e.spot_stringValue())) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Returns a SPOTSet for holding SPOTInteger elements
   *
   * @param name
   *          the name of this element
   * @return the SPOTSet
   */
  public static SPOTSet integerSet(String name) {
    return new SPOTSet(name, new SPOTInteger(), -1, -1, true);
  }

  /**
   * Returns a SPOTSet for holding SPOTInteger elements
   *
   * @param name
   *          the name of this element
   * @param min
   *          the minimum number of elements that the set must contain
   * @param max
   *          the maximum number of elements that the set must contain
   * @return the SPOTSet
   */
  public static SPOTSet integerSet(String name, int min, int max) {
    SPOTSet set = new SPOTSet(name, new SPOTInteger(), -1, -1, true);

    set.spot_setRange(min, max);

    return set;
  }

  public Iterator iterator() {
    return _theElements.iterator();
  }

  public int lastIndexOf(Object o) {
    return _theElements.lastIndexOf(o);
  }

  public ListIterator listIterator() {
    return _theElements.listIterator();
  }

  public ListIterator listIterator(int index) {
    return _theElements.listIterator(index);
  }

  /**
   * Returns the value of the element at the specified position as a
   * <code>long</code>
   *
   * @param position
   *          the position
   *
   * @return the value
   */
  public long longValueAt(int position) {
    iSPOTElement ti = (iSPOTElement) _theElements.get(position);

    return (ti instanceof aSPOTElement)
           ? ((aSPOTElement) ti).longValue()
           : SNumber.longValue(ti.spot_stringValue());
  }

  /**
   * Retrieves the set's values as an array of longs
   *
   * @return The array of longs
   */
  public long[] longValues() {
    int          n  = _theElements.size();
    long[]       l  = new long[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = (iSPOTElement) _theElements.get(i);
      l[i] = (ti instanceof aSPOTElement)
             ? ((aSPOTElement) ti).longValue()
             : SNumber.longValue(ti.spot_stringValue());
    }

    return l;
  }

  /**
   * Retrieves the set's values as an array of objects
   *
   * @return The array of objects
   */
  public Object[] objectValues() {
    int          n  = _theElements.size();
    Object[]     a  = new Object[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = getEx(i);
      a[i] = ti.spot_getValue();
    }

    return a;
  }

  /**
   * Returns a SPOTSet for holding SPOTReal elements
   *
   * @param name
   *          the name of this element
   * @return the SPOTSet
   */
  public static SPOTSet realSet(String name) {
    return new SPOTSet(name, new SPOTReal(), -1, -1, true);
  }

  /**
   * Returns a SPOTSet for holding SPOTReal elements
   *
   * @param name
   *          the name of this element
   * @param min
   *          the minimum number of elements that the set must contain
   * @param max
   *          the maximum number of elements that the set must contain
   * @return the SPOTSet
   */
  public static SPOTSet realSet(String name, int min, int max) {
    SPOTSet set = new SPOTSet(name, new SPOTReal(), -1, -1, true);

    set.spot_setRange(min, max);

    return set;
  }

  public Object remove(int index) {
    return _theElements.remove(index);
  }

  public boolean remove(Object o) {
    return _theElements.remove(o);
  }

  public boolean removeAll(Collection c) {
    return _theElements.removeAll(c);
  }

  /**
   * Removes the specified element from the set This will cause SPOTAny objects
   * to be unwrapped and tested to match the specified object (if the set is an
   * <b>Any</b> set)
   *
   * @param e
   *          the element to remove for
   * @return true if the element was removed; false otherwise
   */
  public int removeEx(iSPOTElement e) {
    int n = this.indexOfEx(e);

    if (n != -1) {
      _theElements.remove(n);
    }

    return n;
  }

  public boolean retainAll(Collection c) {
    return _theElements.retainAll(c);
  }

  public int size() {
    return _theElements.size();
  }

  public void spot_addAll(List list) {
    int len = (list == null)
              ? 0
              : list.size();

    for (int i = 0; i < len; i++) {
      add((String) list.get(i));
    }
  }

  public void spot_addAll(String[] list) {
    int len = (list == null)
              ? 0
              : list.length;

    for (int i = 0; i < len; i++) {
      add(list[i]);
    }
  }

  public String spot_checkRangeValidityStr() {
    int n = _theElements.size();

    if (n == 0) {
      return _isOptional
             ? null
             : Helper.expandString(STR_NULL, spot_getName());
    }

    if ((n < _nRangeMin) && (_nRangeMin != -1)) {
      return Helper.expandString(STR_TOFEW_ELEMENTS, spot_getName(), SNumber.toString(n), SNumber.toString(_nRangeMin));
    }

    if ((n > _nRangeMax) && (_nRangeMax != -1)) {
      return Helper.expandString(STR_TOMANY_ELEMENTS, spot_getName(), SNumber.toString(n),
                                 SNumber.toString(_nRangeMax));
    }

    iSPOTElement ti;

    for (int i = 0; i < n; i++) {
      ti = (iSPOTElement) _theElements.get(i);

      if ((_clsDefinedBy != null) &&!_clsDefinedBy.isInstance(ti)) {
        return Helper.expandString(STR_BAD_ELEMENT, SNumber.toString(i), spot_getName(),
                                   ti.spot_checkRangeValidityStr());
      }

      if (ti.spot_checkRangeValidity() != 0) {
        return Helper.expandString(STR_BAD_ELEMENT, SNumber.toString(i), spot_getName(),
                                   ti.spot_checkRangeValidityStr());
      }
    }

    return null;
  }

  public void spot_clear() {
    super.spot_clear();
    _theElements.clear();
  }

  public void spot_copy(iSPOTElement element, boolean newinstance) {
    if (!newinstance) {
      if (!OPTIMIZE_RUNTIME) {
        checkReadOnly();
      }

      spot_clear();
    }

    spot_copyEx(element);

    if (element instanceof SPOTSet) {
      deepCopy((SPOTSet) element);
    } else if (element instanceof aSPOTElement) {
      setValue(element.spot_stringValue());
    }
  }

  /**
   * Defines an attribute for that will be supported for elements in the set
   *
   * @param name
   *          The name of the attribute
   * @param defaultValue
   *          the defaultValue
   */
  public void spot_defineElementAttribute(String name, String defaultValue) {
    if (_elementsDefinedAtributes == null) {
      _elementsDefinedAtributes = new LinkedHashMap();
    }

    _elementsDefinedAtributes.put(name, defaultValue);
  }

  public void spot_ensureCapacity(int capacity) {
    _theElements.ensureCapacity(capacity);
  }

  /**
   * Returns a new instance of the sets array type
   *
   * @return a new instance of the sets array type
   */
  public iSPOTElement spot_getArrayClassInstance() {
    try {
      return spot_getArrayClassInstanceEx();
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  public String spot_getArrayClassShortName() {
    String s = _strElementType;

    if (this._anyPrototype != null) {
      s = _anyPrototype.spot_getDefinedByType();

      if (s == null) {
        return "Any";
      }
    }

    if (s == null) {
      return "";
    }

    int n = s.lastIndexOf('.');

    s = (n == -1)
        ? s
        : s.substring(n + 1);

    if (s.startsWith("SPOT")) {
      s = s.substring(4);
    }

    return s;
  }

  /**
   * Returns the name of the element the set contains
   *
   * @return The name
   */
  public String spot_getElementName() {
    return _elementName;
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

  public Object[] spot_getRange() {
    if ((_nRangeMin < 0) && (_nRangeMax < 0)) {
      return null;
    }

    return new Object[] { Long.valueOf(_nRangeMin), Long.valueOf(_nRangeMax) };
  }

  /**
   * Retrieves the named object as a/an <code>Sequence</code> object
   *
   * @param position
   *          The position of the element/object
   *
   * @return The <code>Sequence</code> object; otherwise <code>null</code>
   */
  public SPOTSequence spot_getSequenceElement(int position) {
    return (SPOTSequence) _theElements.get(position);
  }

  /**
   * Retrieves the named object as a/an <code>Set</code> object
   *
   * @param position
   *          The position of the element/object
   *
   * @return The <code>Set</code> object; otherwise <code>null</code>
   */
  public SPOTSet spot_getSetElement(int position) {
    return (SPOTSet) _theElements.get(position);
  }

  /**
   * Retrieves an unmodifiable list of the supported attributes for elements in
   * the set
   *
   * @return The supported attributes
   */
  public Map spot_getSupportedElementAttributes() {
    return (_elementsDefinedAtributes == null)
           ? null
           : Collections.unmodifiableMap(_elementsDefinedAtributes);
  }

  public final int spot_getType() {
    return SPOT_TYPE_SET;
  }

  /**
   * Retrieves the range of valid values for the object.
   *
   * @return The valid range as a displayable string
   */
  public String spot_getValidityRange() {
    if ((_nRangeMin != -1) || (_nRangeMax != -1)) {
      String s = "";

      s = (_nRangeMin != -1)
          ? (SNumber.toString(_nRangeMin) + "..")
          : "..";

      if (_nRangeMax != -1) {
        s += SNumber.toString(_nRangeMax);
      }

      return s;
    } else {
      return "";
    }
  }

  public Object spot_getValue() {
    return objectValues();
  }

  public boolean spot_isAnySet() {
    return _strElementType.endsWith("SPOTAny");
  }

  public boolean spot_isContainer() {
    return true;
  }

  public boolean spot_isSequenceSet() {
    if (spot_isAnySet()) {
      return false;
    }

    if (_clsDefinedBy != null) {
      return SPOTSequence.class.isAssignableFrom(_clsDefinedBy);
    }

    return !_strElementType.contains("SPOT");
  }

  public void spot_makeReadOnly() {
    if (_canMakeReadOnly &&!_isReadOnly) {
      _isReadOnly = true;

      iSPOTElement ti  = null;
      int          len = _theElements.size();

      for (int i = 0; i < len; i++) {
        ti = (iSPOTElement) _theElements.get(i);
        ti.spot_makeReadOnly();
      }
    }
  }

  /**
   * Sets the default value for the object.
   *
   * @param val
   *          The value to become the default (i.e. used when the object has no
   *          value ) <code>true</code> if the operation was successful;
   *          <code>false</code> otherwise
   *
   */
  public void spot_setDefaultValue(String val) {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, this.getClass().getName());
  }

  public void spot_setName(String name) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theName = name;
  }

  /**
   * Sets the valid range (the number of elements) for the set
   *
   * @param min
   *          The object's minimum acceptable value
   * @param max
   *          The object's maximum acceptable value
   */
  public void spot_setRange(long min, long max) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _nRangeMin = min;
    _nRangeMax = max;

    if (_nRangeMin < -1) {
      _nRangeMin = -1;
    }

    if (_nRangeMax < -1) {
      _nRangeMax = -1;
    }
  }

  /**
   * Sets the valid range (the number of elements) for the set
   *
   * @param min
   *          The object's minimum acceptable value
   * @param max
   *          The object's maximum acceptable value
   */
  public void spot_setRange(String min, String max) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (min != null) {
      _nRangeMin = SNumber.longValue(min);
    }

    if (max != null) {
      _nRangeMax = SNumber.longValue(max);
    }

    if (_nRangeMin < -1) {
      _nRangeMin = -1;
    }

    if (_nRangeMax < -1) {
      _nRangeMax = -1;
    }
  }

  public String spot_stringValue() {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, "Set");
  }

  public String spot_stringValueEx() {
    throw new SPOTException(NOT_SUPPORTED, STR_NOT_SUPPORTED, "Set");
  }

  /**
   * Returns a SPOTSet that contains the specified element
   *
   * @param name
   *          the name of this element
   * @param e
   *          the element to place in the set
   *
   * @return the SPOTSet
   */
  public static SPOTSet spot_toSet(String name, iSPOTElement e) {
    SPOTSet set = new SPOTSet(name, e, -1, -1, true);

    set.add(e);

    return set;
  }

  /**
   * Returns a SPOTSet for holding SPOTPrintableString elements
   *
   * @param name
   *          the name of this element
   * @return the SPOTSet
   */
  public static SPOTSet stringSet(String name) {
    return new SPOTSet(name, new SPOTPrintableString(), -1, -1, true);
  }

  /**
   * Returns a SPOTSet for holding SPOTPrintableString elements
   *
   * @param name
   *          the name of this element
   * @param min
   *          the minimum number of elements that the set must contain
   * @param max
   *          the maximum number of elements that the set must contain
   * @return the SPOTSet
   */
  public static SPOTSet stringSet(String name, int min, int max) {
    SPOTSet set = new SPOTSet(name, new SPOTPrintableString(), -1, -1, true);

    set.spot_setRange(min, max);

    return set;
  }

  /**
   * Returns the value of the element at the specified position as a string
   *
   * @param position
   *          the position
   *
   * @return the value
   */
  public String stringValueAt(int position) {
    iSPOTElement e = getEx(position);

    return e.spot_stringValue();
  }

  /**
   * Retrieves the set's values as an array of strings
   *
   * @return The array of strings
   */
  public String[] stringValues() {
    int          n  = _theElements.size();
    String[]     s  = new String[n];
    iSPOTElement ti = null;

    for (int i = 0; i < n; i++) {
      ti   = getEx(i);
      s[i] = ti.spot_stringValue();
    }

    return s;
  }

  public List subList(int fromIndex, int toIndex) {
    return _theElements.subList(fromIndex, toIndex);
  }

  public Object[] toArray() {
    return _theElements.toArray();
  }

  /**
   * Returns an array containing all of the elements in this set in the correct
   * order; the runtime type of the returned array is that of the specified
   * array. If the list fits in the specified array, it is returned therein.
   * Otherwise, a new array is allocated with the runtime type of the specified
   * array and the size of this list.
   * <p>
   *
   * If the list fits in the specified array with room to spare (i.e., the array
   * has more elements than the list), the element in the array immediately
   * following the end of the collection is set to <tt>null</tt>. This is useful
   * in determining the length of the list <i>only</i> if the caller knows that
   * the list does not contain any <tt>null</tt> elements.
   *
   * @param a
   *          the array into which the elements of the list are to be stored, if
   *          it is big enough; otherwise, a new array of the same runtime type
   *          is allocated for this purpose.
   * @return an array containing the elements of the list.
   */
  public Object[] toArray(Object a[]) {
    return _theElements.toArray(a);
  }

  public String toSDF() {
    StringWriter sw = new StringWriter();

    try {
      toSDF(sw, null, 0, false, true);
    } catch(IOException ignore) {}

    return sw.toString();
  }

  public boolean toSDF(Writer out) throws IOException {
    return toSDF(out, spot_getName(), 0, false, true);
  }

  public boolean toSDF(Writer out, String tag, int depth, boolean outputempty, boolean outputComments)
          throws IOException {
    if ((_headerComment != null) && outputComments) {
      int len = _headerComment.length;

      for (int i = 0; i < len; i++) {
        writeSDFName(out, _headerComment[i], depth);
        out.write("\n");
      }
    }

    int n = _theElements.size();

    if (n > 0) {
      iSPOTElement ti   = null;
      iSPOTElement prot = null;

      if (tag == null) {
        tag = "{\n";
      } else {
        tag += " {\n";
      }

      aSPOTElement.writeSDFName(out, tag, depth);

      if (n > 0) {
        for (int i = 0; i < n; i++) {
          ti = (iSPOTElement) _theElements.get(i);

          if (ti == null) {
            continue;
          }

          prot = ti;

          if (!ti.toSDF(out, null, depth + 1, outputempty, outputComments)) {
            Helper.writePadding(out, depth + 1);
            out.write("{}\n");
          }
        }

        Helper.writePadding(out, depth);
        out.write("}");

        String type = null;

        if ((spot_getParent() instanceof SPOTAny) && (prot != null)) {
          type = spot_getRelativeClassName(prot);
        }

        if (_attributes != null) {
          if (type != null) {
            _attributes.put("type", type);
          }

          aSPOTElement.writeAttributes(out, _attributes, _defAttributes, depth);

          if (type != null) {
            _attributes.remove("type");
          }
        } else if (type != null) {
          out.append(" [ type=\"");
          out.append(type);
          out.append("\" ]");
        }

        if ((_footerComment != null) && outputComments) {
          out.write(" ");
          out.write(_footerComment);
        }

        out.write("\n");
      }

      return true;
    } else if (outputempty || _attributeSet) {
      if (_clsDefinedBy != null) {
        try {
          iSPOTElement ti = spot_getArrayClassInstanceEx();

          if (tag == null) {
            tag = "{\n";
          } else {
            tag += " {\n";
          }

          aSPOTElement.writeSDFName(out, tag, depth);
          ti.toSDF(out, null, depth + 1, outputempty, outputComments);
          Helper.writePadding(out, depth);
          out.write("}");

          if (_attributes != null) {
            aSPOTElement.writeAttributes(out, _attributes, _defAttributes, depth);
          }

          out.write("\n");

          return true;
        } catch(Exception ex) {
          ex.printStackTrace(new PrintWriter(out));
        }
      }

      if (tag == null) {
        tag = "{}";
      } else {
        tag += " {}";
      }

      if (_attributes != null) {
        aSPOTElement.writeAttributes(out, _attributes, _defAttributes, depth);
      }

      aSPOTElement.writeSDFName(out, tag, depth);
      out.write("\n");

      return true;
    }

    return false;
  }

  public void toStream(OutputStream out) throws IOException {
    iSPOTElement ti  = null;
    int          len = _theElements.size();

    aStreamer.toStream(len, out);

    for (int i = 0; i < len; i++) {
      ti = (iSPOTElement) _theElements.get(i);
      ti.toStream(out);
    }
  }

  /**
   * Converts the object to a <code>String</code> object
   *
   * @return The object
   */
  public String toString() {
    java.io.StringWriter sw = new java.io.StringWriter();

    try {
      toSDF(sw);
    } catch(IOException ex) {}

    sw.flush();

    return sw.toString();
  }

  /**
   * This method returns the underlying list of objects It is prefixed with
   * "unsafe" to caution users that the integrity of the set can be compromised
   * if changes are made to the list. If the set is marked as read-only then a
   * copy is returned
   *
   * @return the underlying list of objects
   */
  public List unsafeGetObjectList() {
    if (_isReadOnly) {
      return Collections.unmodifiableList(_theElements);
    }

    return _theElements;
  }

  public iSPOTElement set(int index, iSPOTElement element) {
    if (this._isAnySet) {
      element = this.createAnyElement(element);
    } else if ((_clsDefinedBy != null) &&!_clsDefinedBy.isInstance(element)) {
      throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), element.getClass().getName());
    }

    element.spot_setParent(this);

    return (iSPOTElement) _theElements.set(index, element);
  }

  public Object set(int index, Object element) {
    return set(index, (iSPOTElement) element);
  }

  /**
   * Sets the name and type of elements that make up the <code>Set</code>
   *
   * @param name
   *          The name of the object/element
   * @param type
   *          The type of object (specified as an interface)
   */
  public void setType(String name, Class type) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _elementName              = name;
    _strElementType           = type.getName();
    _clsDefinedBy             = type;
    _elementsDefinedAtributes = null;
    _isAnySet                 = _clsDefinedBy.equals(SPOTAny.class);
  }

  /**
   * Sets the name and type of elements that make up the <code>Set</code>
   *
   * @param name
   *          The name of the object/element
   * @param itype
   *          The type of object (specified as an interface)
   */
  public void setType(String name, iSPOTElement itype) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _elementName = name;

    if (itype != null) {
      _strElementType           = itype.getClass().getName();
      _clsDefinedBy             = itype.getClass();
      _elementsDefinedAtributes = itype.spot_getSupportedAttributes();

      if (_elementsDefinedAtributes != null) {
        _elementsDefinedAtributes = new LinkedHashMap(_elementsDefinedAtributes);
      }

      this._isAnySet = _clsDefinedBy.equals(SPOTAny.class);

      if (this._isAnySet) {
        _anyPrototype = new SPOTAny((SPOTAny) itype);
      }
    }
  }

  /**
   * Sets the name and type of elements that make up the <code>Set</code>
   *
   * @param name
   *          The name of the object/element
   * @param itype
   *          The type of object (specified as an interface)
   */
  public void setType(String name, String itype) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _elementName              = name;
    _strElementType           = aSPOTElement.spot_resolveClassName(this, itype);
    _elementsDefinedAtributes = null;

    if (_strElementType != null) {
      try {
        _clsDefinedBy  = SPOTHelper.loadClass(_strElementType);
        this._isAnySet = _clsDefinedBy.equals(SPOTAny.class);
      } catch(ClassNotFoundException ex) {}
    }
  }

  /**
   * Sets the value of the set. Removes any existing values.
   *
   * @param val
   *          the value
   *
   */
  public void setValue(boolean val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    try {
      iSPOTElement itype = spot_getArrayClassInstanceEx();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(String.valueOf(val));
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Sets the values of the set. Removes any existing values.
   *
   * @param val
   *          the value
   *
   */
  public void setValue(double val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    try {
      iSPOTElement itype = spot_getArrayClassInstanceEx();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(String.valueOf(val));
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Sets the values of the object. Removes any existing values.
   *
   * @param val
   *          The values
   *
   * @throws SPOTException
   *           if an error occurs
   */
  public void setValue(double[] val) throws SPOTException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      return;
    }

    _theElements.clear();
    _theElements.ensureCapacity(val.length);

    try {
      for (int i = 0; i < val.length; i++) {
        iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

        if (itype instanceof aSPOTElement) {
          ((aSPOTElement) itype).setValue(val[i]);
        } else {
          itype.spot_setValue(String.valueOf(val[i]));
        }

        _theElements.add(itype);
      }
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Add the value to the set
   *
   * @param val
   *          the value
   *
   */
  public void setValue(iSPOTElement val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if ((_clsDefinedBy != null) &&!_clsDefinedBy.isInstance(val)) {
      throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), val.getClass().getName());
    }

    _theElements.add(val);
  }

  /**
   * Sets the values of the object. Removes any existing values.
   *
   * @param val
   *          The array of <code>Interface</code> objects to place in the set
   */
  public void setValue(iSPOTElement[] val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      return;
    }

    _theElements.clear();

    int len = val.length;

    _theElements.ensureCapacity(len);

    if (_clsDefinedBy == null) {
      for (int i = 0; i < len; i++) {
        _theElements.add(val[i]);
      }
    } else {
      iSPOTElement e;

      for (int i = 0; i < len; i++) {
        e = val[i];

        if (!_clsDefinedBy.isInstance(e)) {
          throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), e.getClass().getName());
        }

        _theElements.add(e);
      }
    }
  }

  /**
   * Sets the values of the object. Removes any existing values.
   *
   * @param val
   *          The array of <code>Interface</code> objects to place in the set
   */
  public void setValue(List val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      return;
    }

    _theElements.clear();

    int len = val.size();

    _theElements.ensureCapacity(len);

    if (_clsDefinedBy == null) {
      for (int i = 0; i < len; i++) {
        _theElements.add(val.get(i));
      }
    } else {
      iSPOTElement e;

      for (int i = 0; i < len; i++) {
        e = (iSPOTElement) val.get(i);

        if (!_clsDefinedBy.isInstance(e)) {
          throw new SPOTException(NOT_CLASS, STR_NOT_CLASS, _clsDefinedBy.getName(), e.getClass().getName());
        }

        _theElements.add(e);
      }
    }
  }

  /**
   * Sets the values of the set. Removes any existing values.
   *
   * @param val
   *          the value
   *
   */
  public void setValue(long val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    try {
      iSPOTElement itype = spot_getArrayClassInstanceEx();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(SNumber.toString(val));
      }
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Sets the values of the object. Removes any existing values.
   *
   * @param val
   *          The values
   *
   * @throws SPOTException
   *           if an error occurs
   */
  public void setValue(long[] val) throws SPOTException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      return;
    }

    _theElements.clear();
    _theElements.ensureCapacity(val.length);

    try {
      for (int i = 0; i < val.length; i++) {
        iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

        if (itype instanceof aSPOTElement) {
          ((aSPOTElement) itype).setValue(val[i]);
        } else {
          itype.spot_setValue(SNumber.toString(val[i]));
        }

        _theElements.add(itype);
      }
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Add the value to the set
   *
   * @param val
   *          the value
   *
   */
  public void setValue(SNumber val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      if (itype instanceof aSPOTElement) {
        ((aSPOTElement) itype).setValue(val);
      } else {
        itype.spot_setValue(val.toString());
      }

      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Sets the value of the set. Removes any existing values.
   *
   * @param val
   *          the value
   *
   */
  public void setValue(String val) {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    _theElements.clear();

    try {
      iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

      itype.spot_setValue(val);
      add(itype);
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Sets the values of the set. Removes any existing values.
   *
   * @param val
   *          The values
   *
   * @throws SPOTException
   *           if an error occurs
   */
  public void setValue(String[] val) throws SPOTException {
    if (!OPTIMIZE_RUNTIME) {
      checkReadOnly();
    }

    if (val == null) {
      return;
    }

    _theElements.clear();
    _theElements.ensureCapacity(val.length);

    try {
      for (int i = 0; i < val.length; i++) {
        iSPOTElement itype = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();

        itype.spot_setValue(val[i]);
        _theElements.add(itype);
      }
    } catch(Exception e) {
      if (e instanceof SPOTException) {
        throw(SPOTException) e;
      }

      throw new SPOTException(NO_CREATE, String.format(iSPOTConstants.STR_NO_CREATE, _strElementType), e);
    }
  }

  /**
   * Retrieves the object at the specified position
   *
   * @param position
   *          The position
   *
   * @return The object; otherwise <code>null</code>
   */
  public Object get(int position) {
    if ((position < 0) || (position > _theElements.size())) {
      return null;
    }

    return (iSPOTElement) _theElements.get(position);
  }

  /**
   * Returns the number of objects present/contained within the set
   *
   * @return The number of objects in the set
   */
  public int getCount() {
    return _theElements.size();
  }

  /**
   * Retrieves the element value at the specified position. This will cause
   * SPOTAny objects to be unwrapped and the wrapped element to be returned
   *
   * @param position
   *          The position
   *
   * @return The object; otherwise <code>null</code>
   */
  public iSPOTElement getEx(int position) {
    if ((position < 0) || (position > _theElements.size())) {
      return null;
    }

    return ((iSPOTElement) _theElements.get(position)).spot_elementValue();
  }

  /**
   * Retrieves the array of object in the set
   *
   * @return The array of objects
   */
  public iSPOTElement[] getValues() {
    return (iSPOTElement[]) _theElements.toArray(new iSPOTElement[_theElements.size()]);
  }

  /**
   * Retrieves the array of object in the set This will cause SPOTAny objects to
   * be unwrapped and the wrapped element to be returned
   *
   * @return The array of objects
   */
  public iSPOTElement[] getValuesEx() {
    int            n = _theElements.size();
    iSPOTElement[] a = new iSPOTElement[n];

    for (int i = 0; i < n; i++) {
      a[i] = ((iSPOTElement) _theElements.get(i)).spot_elementValue();
    }

    return a;
  }

  public boolean isEmpty() {
    return _theElements.isEmpty();
  }

  iSPOTElement createAnyElement(iSPOTElement e) {
    if (e instanceof SPOTAny) {
      return e;
    }

    SPOTAny a = new SPOTAny(_anyPrototype);

    a.setValue(e);

    return a;
  }

  protected int spot_checkRangeValidityEx() {
    int n = _theElements.size();

    if (n == 0) {
      return _isOptional
             ? VALUE_NULL_AND_OPTIONAL
             : VALUE_NULL;
    }

    if ((n < _nRangeMin) && (_nRangeMin != -1)) {
      return VALUE_TO_SMALL;
    }

    if ((n > _nRangeMax) && (_nRangeMax != -1)) {
      return VALUE_TO_BIG;
    }

    int          ret = 0;
    iSPOTElement ti;

    for (int i = 0; i < n; i++) {
      ti  = (iSPOTElement) _theElements.get(i);
      ret = ti.spot_checkRangeValidity();

      if (ret > 0) {
        return VALUE_INVALID_CHILD;
      } else if ((ret < 0) && (ret > -3)) {
        return VALUE_INVALID_CHILD;
      }
    }

    return VALUE_OK;
  }

  /**
   * Returns a new instance of the sets array type
   *
   * @return a new instance of the sets array type
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private iSPOTElement spot_getArrayClassInstanceEx() throws Exception {
    iSPOTElement e;

    if (_clsDefinedBy != null) {
      e = (iSPOTElement) _clsDefinedBy.newInstance();
    } else {
      e = (iSPOTElement) SPOTHelper.loadClass(_strElementType).newInstance();
    }

    if (_elementsDefinedAtributes != null) {
      Iterator<Entry> it = _elementsDefinedAtributes.entrySet().iterator();

      while(it.hasNext()) {
        Entry en = it.next();

        e.spot_defineAttribute((String) en.getKey(), (String) en.getValue());
      }
    }

    return e;
  }
}
