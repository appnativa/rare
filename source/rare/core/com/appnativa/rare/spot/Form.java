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
 * This class represents the configuration information for a
 * form viewer.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Form extends GroupBox {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** a list of name/value pairs that are to be submitted along with widget values. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString submitAttributes = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: whether initial widget values should be retained so that the form can be reset to it's initial state */
  public SPOTBoolean retainInitialFieldValues = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** the name of the default button for use when the form is the main viewer for a dialog or window */
  public SPOTPrintableString defaultButtonName = new SPOTPrintableString();

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Form</code> object.
   */
  public Form() {
    this(true);
  }

  /**
   * Creates a new <code>Form</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Form(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Form</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Form(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 4;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onSubmit", null);
    spot_defineAttribute("onReset", null);
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("submitAttributes", submitAttributes);
    submitAttributes.spot_defineAttribute("mimeType", null);
    spot_addElement("retainInitialFieldValues", retainInitialFieldValues);
    spot_addElement("defaultButtonName", defaultButtonName);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
