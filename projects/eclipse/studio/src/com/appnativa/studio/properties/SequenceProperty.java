/*
 * @(#)SequenceProperty.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.appnativa.studio.Studio;
import com.appnativa.studio.Utilities;
import com.appnativa.rare.spot.Font;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharArray;

public class SequenceProperty implements IPropertySource, IPropertySource2 {
  public static final Object        REFERENCE_MARKER = new Object();
  List<String>                      categories;
  IPropertyDescriptor[]             descriptors;
  SPOTSequence                      element;
  SequenceProperty                  parentProperty;
  HashMap<String, SequenceProperty> subProperties;
  HashMap<String,EditorValue>       editorValues;
  private boolean                   sorted;

  public SequenceProperty(SPOTSequence element) {
    this.element = element;
  }

  public SequenceProperty(SPOTSequence element, IPropertyDescriptor[] descriptors) {
    this.element     = element;
    this.descriptors = descriptors;
  }
  public EditorValue getEditorValue(String name, iSPOTElement element,boolean canBeNull) {
    if(editorValues==null) {
      if(canBeNull) {
        return null;
      }
      editorValues=new HashMap<String, SequenceProperty.EditorValue>();
    }
    EditorValue v=editorValues.get(name);
    if(v==null) {
      if(canBeNull) {
        return null;
      }
      v=new EditorValue(element);
      editorValues.put(name, v);
    }
    return v;
  }
  public void dispose() {
    element     = null;
    descriptors = null;

    try {
      if (subProperties != null) {
        for (SequenceProperty p : subProperties.values()) {
          p.dispose();
        }

        subProperties.clear();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    subProperties = null;
  }

  @Override
  public void resetPropertyValue(Object name) {
    iSPOTElement e = getElement(name);

    if (e == null) {
      return;
    }

    if (e != null) {
      e.spot_clear();
    }
  }

  public String toString() {
    CharArray ca  = Utilities.toStringEx(element);
    int       len = ca._length;

    if ((len > 0) && (ca.A[len - 1] == '}')) {
      len--;
    }

    if ((len > 0) && (ca.A[len - 1] == ';')) {
      len--;
    }

    ca.setLength(len);
    ca.trim();

    int n = 0;

    if ((n < len) && (ca.A[n] == '{')) {
      n++;
    }

    if ((n < len) && (ca.A[n] == ' ')) {
      n++;
    }

    return ca.substring(n, len);
  }

  public static String toString(SPOTSet set) {
    int    len = set.size();
    String s   = set.spot_getArrayClassShortName();
    int    n   = s.indexOf("$C");

    if (n != -1) {
      s = s.substring(n + 2);
    }

    if (len == 0) {
      return "<0 " + s + "s>";
    } else if (len == 1) {
      return "<1 " + s + ">";
    }

    return "<" + len + " " + s + ">";
  }

  @Override
  public void setPropertyValue(Object name, Object value) {
    iSPOTElement e = getElement(name);

    if (e == null) {
      e = getReferenceProperty(name);

      if (e != null) {
        element.spot_setReferenceVariable((String) name, e);
      }
    }

    if (e == null) {
      return;
    }

    switch(e.spot_getType()) {
//      case iSPOTConstants.SPOT_TYPE_BOOLEAN :
//        if(value instanceof SPOTBoolean) {
//         value=((SPOTBoolean)value).booleanValue(); 
//        }
//        else {
//          value = (Integer) value == 0;
//        }
//
//        break;
//
//      case iSPOTConstants.SPOT_TYPE_ENUMERATED :
//        if(value instanceof SPOTEnumerated) {
//          if()
//          value=((SPOTEnumerated) value).intValue();
//        }
//        else {
//          value = ((SPOTEnumerated) e).getChoiceByIndex((Integer) value);
//        }
//
//        break;
      default :
        EditorValue v = getEditorValue((String)name,e,true);

        if (v != null) {
          if ((v.value instanceof SPOTSequence) &&!(v.value instanceof Font)) {
            return;
          }

          if (v.isDirty(value)) {
            v.setValue(value);
          } else {
            return;
          }
        }

        break;
    }

    valueChanged(e, value);
  }

  public void setSorted(boolean sorted) {
    if (this.sorted != sorted) {
      this.sorted = sorted;

      if (descriptors != null) {
        updateCategoryForSort();
      }
    }
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if (descriptors == null) {
      List<PropertyDescriptor> list = EditorHelper.getPropertyDescriptor(element, true, null);

      descriptors = list.toArray(new IPropertyDescriptor[list.size()]);

      if (sorted) {
        updateCategoryForSort();
      }
    }

    return descriptors;
  }

  @Override
  public Object getPropertyValue(Object name) {
    iSPOTElement e = getElement(name);

    if (e == null) {
      e = getReferenceProperty(name);
    }

    if (e == null) {
      return "";
    }

    switch(e.spot_getType()) {
      case iSPOTConstants.SPOT_TYPE_SEQUENCE :

        return getSubProperty((SPOTSequence) e);

      case iSPOTConstants.SPOT_TYPE_SET :
        return getEditorValue((String)name, e, false);

      case iSPOTConstants.SPOT_TYPE_ANY :
        return e;

      case iSPOTConstants.SPOT_TYPE_PRINTABLESTRING :
        if (!e.spot_hasValue()) {
          return "";
        }

        if ("bgColor".equalsIgnoreCase(e.spot_getName())) {
          return Utilities.toString(e);
        }

        return e.spot_stringValue();

      default :
        return e.spot_hasValue()
               ? e.spot_stringValue()
               : "";
    }
  }

  public SPOTSequence getSequence() {
    return element;
  }

  @Override
  public boolean isPropertyResettable(Object name) {
    return true;
  }

  @Override
  public boolean isPropertySet(Object name) {
    iSPOTElement e = getElement(name);

    if (e == null) {
      return false;
    }

    return e.spot_valueWasSet();
  }

  public boolean isSorted() {
    return sorted;
  }

  protected void valueChanged(iSPOTElement e, Object value) {
    if (parentProperty != null) {
      parentProperty.valueChanged(e, value);
    }
  }

  protected SequenceProperty getSubProperty(SPOTSequence seq) {
    String           name = seq.spot_getName();
    SequenceProperty p    = (subProperties == null)
                            ? null
                            : subProperties.get(name);

    if (p == null) {
      if (subProperties == null) {
        subProperties = new HashMap<String, SequenceProperty>();
      }

      p                = new SequenceProperty(seq);
      p.parentProperty = this;
      subProperties.put(name, p);
    }

    return p;
  }

  private void createCategoriesList(IPropertyDescriptor[] a) {
    categories = new ArrayList<String>(a.length);

    for (IPropertyDescriptor pd : a) {
      categories.add(pd.getCategory());
    }
  }

  private void updateCategoryForSort() {
    String s = Studio.getResourceAsString("Studio.text.sorted");

    if (categories == null) {
      createCategoriesList(descriptors);
    }

    int len = descriptors.length;

    for (int i = 0; i < len; i++) {
      PropertyDescriptor pd = (PropertyDescriptor) descriptors[i];

      pd.setCategory(sorted
                     ? s
                     : categories.get(i));
    }
  }

  private iSPOTElement getElement(Object name) {
    if (name instanceof iSPOTElement) {
      return (iSPOTElement) name;
    }

    return element.spot_elementFor(name.toString());
  }

  private iSPOTElement getReferenceProperty(Object name) {
    IPropertyDescriptor[] a = getPropertyDescriptors();

    for (IPropertyDescriptor pd : a) {
      if ((pd instanceof ReferencePropertyDescriptor) && pd.getId().equals(name)) {
        return ((ReferencePropertyDescriptor) pd).getElement();
      }
    }

    return null;
  }

  static class EditorValue {
    boolean isSet;
    String  string;
    Object  value;

    public EditorValue(iSPOTElement value) {
      this.value = value;

      if (value instanceof SPOTSet) {
        isSet = true;
      }
    }

    public String toString() {
      if (string == null) {
        if (isSet) {
          string = SequenceProperty.toString((SPOTSet) value);
        } else if (value == null) {
          string = "";
        } else {
          string = value.toString();
        }
      }

      return string;
    }

    public void setValue(Object value) {
      this.value = value;

      if (value instanceof SPOTSet) {
        isSet = true;
      }

      string = null;
    }

    public boolean isDirty(Object value) {
      return true;    //this.value != value;
    }
  }
}
