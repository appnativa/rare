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

package com.appnativa.rare.spot;

import com.appnativa.spot.*;

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * This class represents the configuration information of a
 * structure for holding name/value pairs.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class NameValuePair extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** the name to associate with the value */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 255, false);
//GENERATED_COMMENT{}

  /** the value */
  public SPOTPrintableString value = new SPOTPrintableString();

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>NameValuePair</code> object.
   */
  public NameValuePair() {
    this(true);
  }

  /**
   * Creates a new <code>NameValuePair</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public NameValuePair(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>NameValuePair</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected NameValuePair(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 2;
    this.attributeSizeHint += 3;
    super.spot_setElements();
    spot_defineAttribute("type", null);
    spot_defineAttribute("converterClass", null);
    spot_defineAttribute("converterContext", null);
    spot_addElement("name", name);
    spot_addElement("value", value);
  }

  //}GENERATED_METHODS
  public String getName() {
    return name.getValue();
  }

  public String getValue() {
    return value.getValue();
  }

  /**
   * Creates a new object
   *
   * @param name the name
   * @param value the value
   */
  public NameValuePair(String name, String value) {
    this(false);
    this.name.setValue(name);
    this.value.setValue(value);
  }

  @Override
  public String toString() {
    String s = name.getValue();
    String v = value.getValue();

    if ((v == null) && (s == null)) {
      return "";
    }

    if (v == null) {
      v = "";
    }

    if (s == null) {
      s = "";
    }

    return s + "=" + v;
  }

  /**
   * Converts the set of NameValuePair to a map
   *
   * @param set the set to convert
   *
   * @return the map of null (if set is empty)
   */
  public static java.util.Map toMap(SPOTSet set) {
    int len = (set == null)
              ? 0
              : set.size();

    if (len == 0) {
      return null;
    }

    NameValuePair p;
    java.util.Map map = new java.util.HashMap(len);

    for (int i = 0; i < len; i++) {
      p = (NameValuePair) set.getEx(i);
      map.put(p.getName(), p.getValue());
    }

    return map;
  }

  /**
   * Adds the values from the specified map
   * as NameValuePair objects to the specified set
   *
   * @param set the set to add to
   * @param map the map of values
   */
  public static void addToSet(SPOTSet set, java.util.Map map) {
    if ((set == null) || (map == null)) {
      return;
    }

    java.util.Iterator  it = map.entrySet().iterator();
    Object              value;
    Object              name;
    java.util.Map.Entry e;

    while(it.hasNext()) {
      e     = (java.util.Map.Entry) it.next();
      name  = e.getKey();
      value = e.getValue();

      if (name != null) {
        set.add(new NameValuePair(name.toString(), (value == null)
                ? null
                : value.toString()));
      }
    }
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
