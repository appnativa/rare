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
 * This class represents configuration information for a widget
 * that manages a list of data items.
 * <p>
 * Each item is also preceded by a check box that can
 * independently selected
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class CheckBoxList extends ListBox {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: whether checkboxes and the list selection are automatically linked */
  public SPOTBoolean linkSelection = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether the checkbox is leading or trailing the item */
  public SPOTBoolean checkboxTrailing = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>CheckBoxList</code> object.
   */
  public CheckBoxList() {
    this(true);
  }

  /**
   * Creates a new <code>CheckBoxList</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public CheckBoxList(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>CheckBoxList</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected CheckBoxList(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 2;
    super.spot_setElements();
    spot_addElement("linkSelection", linkSelection);
    spot_addElement("checkboxTrailing", checkboxTrailing);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
