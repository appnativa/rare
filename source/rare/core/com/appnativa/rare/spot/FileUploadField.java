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
 * This class represents the configuration for a widget that
 * allows a file to be selected and subsequently
 * <p>
 * uploaded when the associated form is submitted
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class FileUploadField extends TextField {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design:the title to use for the file selection dialog box */
  public SPOTPrintableString dialogTitle = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance~~fileUploadButtonText: the text for the button that will initiate the file selection */
  public SPOTPrintableString buttonText = new SPOTPrintableString(null, "Browse...", false);
//GENERATED_COMMENT{}

  /** Appearance~icon~fileUploadButtonIcon: the icon for the button that will initiate the file selection */
  public SPOTPrintableString buttonIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: comma separated list of file extension */
  public SPOTPrintableString fileExtensions = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>FileUploadField</code> object.
   */
  public FileUploadField() {
    this(true);
  }

  /**
   * Creates a new <code>FileUploadField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public FileUploadField(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>FileUploadField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected FileUploadField(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 4;
    super.spot_setElements();
    spot_addElement("dialogTitle", dialogTitle);
    spot_addElement("buttonText", buttonText);
    spot_addElement("buttonIcon", buttonIcon);
    buttonIcon.spot_defineAttribute("alt", null);
    buttonIcon.spot_defineAttribute("slice", null);
    buttonIcon.spot_defineAttribute("size", null);
    buttonIcon.spot_defineAttribute("scaling", null);
    buttonIcon.spot_defineAttribute("density", null);
    spot_addElement("fileExtensions", fileExtensions);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
