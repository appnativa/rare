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
 * This class represents the configuration for a widget spins
 * through a set of data items
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Spinner extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~~reload: number of characters columns to size the field for (use zero to let the widget decide) */
  public SPOTInteger visibleCharacters = new SPOTInteger(null, 0, null, true);
//GENERATED_COMMENT{}

  /** Behavior: whether the combo box will be editable */
  public SPOTBoolean editable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the spinner is circular (must has a maximum and minimum value if isCircular is true) */
  public SPOTBoolean isCircular = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the default buttons should be hidden */
  public SPOTBoolean buttonsVisible = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~runtime: the index of the viewer that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, -1, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the value is autoselected when the spinner's edit field gains focus */
  public SPOTBoolean autoSelect = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether to use a desktop style spinner */
  public SPOTBoolean useDesktopStyle = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Spinner</code> object.
   */
  public Spinner() {
    this(true);
  }

  /**
   * Creates a new <code>Spinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Spinner(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Spinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Spinner(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 7;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("visibleCharacters", visibleCharacters);
    spot_addElement("editable", editable);
    spot_addElement("isCircular", isCircular);
    spot_addElement("buttonsVisible", buttonsVisible);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("autoSelect", autoSelect);
    spot_addElement("useDesktopStyle", useDesktopStyle);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
