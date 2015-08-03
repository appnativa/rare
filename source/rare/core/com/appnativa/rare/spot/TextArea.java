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
 * allows one or more lines of text to be entered and/or edited
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class TextArea extends TextField {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: number of lines to size the area for (use zero to let the widget decide) */
  public SPOTInteger visibleLines = new SPOTInteger(null, 0, null, true);
//GENERATED_COMMENT{}

  /** Behavior: whether the field will word wrap if is a multiline field */
  public SPOTBoolean wordWrap = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the text area supports scrolling */
  public SPOTBoolean supportScrolling = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TextArea</code> object.
   */
  public TextArea() {
    this(true);
  }

  /**
   * Creates a new <code>TextArea</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TextArea(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>TextArea</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TextArea(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 3;
    super.spot_setElements();
    spot_addElement("visibleLines", visibleLines);
    spot_addElement("wordWrap", wordWrap);
    spot_addElement("supportScrolling", supportScrolling);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
