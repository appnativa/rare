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
 * structure for specifying menu items.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class MenuItem extends ActionItem {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: a set of sub-menu items for this menu */
  protected SPOTSet subMenu = null;
//GENERATED_COMMENT{}

  /** Design: whether the menu item should be a check box item */
  public SPOTBoolean checkbox = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether checkbox menu item is to be initially selected */
  public SPOTBoolean selected = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>MenuItem</code> object.
   */
  public MenuItem() {
    this(true);
  }

  /**
   * Creates a new <code>MenuItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public MenuItem(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>MenuItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected MenuItem(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 3;
    super.spot_setElements();
    spot_addElement("subMenu", subMenu);
    spot_addElement("checkbox", checkbox);
    spot_addElement("selected", selected);
  }

  /**
   * Gets the subMenu element
   *
   * @return the subMenu element or null if a reference was never created
   */
  public SPOTSet getSubMenu() {
    return subMenu;
  }

  /**
   * Gets the reference to the subMenu element
   * A reference is created if necessary
   *
   * @return the reference to the subMenu element
   */
  public SPOTSet getSubMenuReference() {
    if (subMenu == null) {
      subMenu = new SPOTSet("menuItem", new MenuItem(), -1, -1, true);
      super.spot_setReference("subMenu", subMenu);
    }

    return subMenu;
  }

  /**
   * Sets the reference to the subMenu element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSubMenu(iSPOTElement reference) throws ClassCastException {
    subMenu = (SPOTSet) reference;
    spot_setReference("subMenu", reference);
  }

  //}GENERATED_METHODS
  public MenuItem(String value, String code) {
    this(false);
    setValue(value);
    setOnActionCode(code);
  }

  public MenuItem(String name) {
    this(false);
    setName(name);
  }

  public MenuItem(String value, String url, int target) {
    this(false);
    setValue(value);
    setActionLink(url, target);
  }

  public MenuItem(String value, String url, String target) {
    this(false);
    setValue(value);
    setActionLink(url, target);
  }

  public void setActionLink(String url, int target) {
    getActionLinkReference();
    actionLink.url.setValue(url);
    actionLink.target.setValue(target);
  }

  public void setActionLink(String url, String target) {
    getActionLinkReference();
    actionLink.url.setValue(url);
    actionLink.regionName.setValue(target);
  }

  public void setName(String name) {
    this.name.setValue(name);
  }

  public void setValue(String value) {
    this.value.setValue(value);
  }

  public void setOnActionCode(String code) {
    this.spot_setAttribute("onAction", code);
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
