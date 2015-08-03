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
 * This class represents configuration information for a data
 * collection
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class DataCollection extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** the name of the collection */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, false);
//GENERATED_COMMENT{}

  /** the name or class that handles the collection. If not specified the default handler is used */
  public SPOTPrintableString handler = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** a URL to use to retrieve data for the collection */
  public SPOTPrintableString dataURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** a set of attributes for the collection. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString attributes = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** whether the data is tabular */
  public SPOTBoolean tabular = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DataCollection</code> object.
   */
  public DataCollection() {
    this(true);
  }

  /**
   * Creates a new <code>DataCollection</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DataCollection(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>DataCollection</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DataCollection(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 5;
    super.spot_setElements();
    spot_addElement("name", name);
    spot_addElement("handler", handler);
    spot_addElement("dataURL", dataURL);
    dataURL.spot_defineAttribute("mimeType", null);
    dataURL.spot_defineAttribute("deferred", "true");
    dataURL.spot_defineAttribute("columnSeparator", "|");
    dataURL.spot_defineAttribute("inline", null);
    dataURL.spot_defineAttribute("ldSeparator", null);
    dataURL.spot_defineAttribute("riSeparator", null);
    dataURL.spot_defineAttribute("unescape", "false");
    dataURL.spot_defineAttribute("aggregate", null);
    dataURL.spot_defineAttribute("parser", null);
    spot_addElement("attributes", attributes);
    attributes.spot_defineAttribute("mimeType", null);
    spot_addElement("tabular", tabular);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
