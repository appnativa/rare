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
 * This class represents configuration information for
 * specifying the text to display whana widget is empty
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class EmptyText extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: optional text to display in the table when the table is empty */
  public SPOTPrintableString value = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance~font~reload: the font to use to display the specified empty table text */
  protected Font font = null;
//GENERATED_COMMENT{}

  /** Appearance~color: the color to use to display the specified empty table text */
  public SPOTPrintableString fgColor = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>EmptyText</code> object.
   */
  public EmptyText() {
    this(true);
  }

  /**
   * Creates a new <code>EmptyText</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public EmptyText(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>EmptyText</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected EmptyText(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 3;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("showBeforeLoad", null);
    spot_addElement("value", value);
    spot_addElement("font", font);
    spot_addElement("fgColor", fgColor);
  }

  /**
   * Gets the font element
   *
   * @return the font element or null if a reference was never created
   */
  public Font getFont() {
    return font;
  }

  /**
   * Gets the reference to the font element
   * A reference is created if necessary
   *
   * @return the reference to the font element
   */
  public Font getFontReference() {
    if (font == null) {
      font = new Font(true);
      super.spot_setReference("font", font);
    }

    return font;
  }

  /**
   * Sets the reference to the font element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setFont(iSPOTElement reference) throws ClassCastException {
    font = (Font) reference;
    spot_setReference("font", reference);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
