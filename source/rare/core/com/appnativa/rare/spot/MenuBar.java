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

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * This class represents the configuration for a window's menu
 * bar. The menu items are defined in the popupMenu property.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class MenuBar extends Viewer {
  //GENERATED_MEMBERS{
  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>MenuBar</code> object.
   */
  public MenuBar() {
    this(true);
  }

  /**
   * Creates a new <code>MenuBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public MenuBar(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>MenuBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected MenuBar(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
