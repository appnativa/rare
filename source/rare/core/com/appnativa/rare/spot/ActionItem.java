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
 * structure for specifying action items.
 * <p>
 * The value of the item can contain embedded data by
 * surrounding them with curly braces.
 * </p>
 * <p>
 * The data embedded will be resolved prior to the action being
 * visually presented
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ActionItem extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the name of the item */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: the action's text. If not specified then the menu is a separator */
  public SPOTPrintableString value = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: the action's tool tip. */
  public SPOTPrintableString tooltip = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the action */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: link to use to retrieve the disabled icon for the item */
  public SPOTPrintableString disabledIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: link to use to retrieve selected state icon for the item */
  public SPOTPrintableString selectedIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: whether the action is enabled */
  public SPOTBoolean enabled = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether the action represents a focused action (will only be enabled when a supported component is focused) */
  public SPOTBoolean focusedAction = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the action is only enabled when there is a selection in a supported component */
  public SPOTBoolean enabledOnSelectionOnly = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the action is only enabled when there is data in a supported component */
  public SPOTBoolean enabledIfHasValueOnly = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: the name of the group this action belongs to */
  public SPOTPrintableString groupName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Design: the name of a key stroke that will be a short cut for the action */
  public SPOTPrintableString shortcutKeystroke = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Design: data to link with the action */
  public SPOTPrintableString linkedData = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: link to activate when the action is performed */
  protected Link actionLink = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ActionItem</code> object.
   */
  public ActionItem() {
    this(true);
  }

  /**
   * Creates a new <code>ActionItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ActionItem(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ActionItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ActionItem(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 14;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_addElement("name", name);
    spot_addElement("value", value);
    spot_addElement("tooltip", tooltip);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("disabledIcon", disabledIcon);
    disabledIcon.spot_defineAttribute("alt", null);
    disabledIcon.spot_defineAttribute("slice", null);
    disabledIcon.spot_defineAttribute("size", null);
    disabledIcon.spot_defineAttribute("scaling", null);
    disabledIcon.spot_defineAttribute("density", null);
    spot_addElement("selectedIcon", selectedIcon);
    selectedIcon.spot_defineAttribute("alt", null);
    selectedIcon.spot_defineAttribute("slice", null);
    selectedIcon.spot_defineAttribute("size", null);
    selectedIcon.spot_defineAttribute("scaling", null);
    selectedIcon.spot_defineAttribute("density", null);
    spot_addElement("enabled", enabled);
    spot_addElement("focusedAction", focusedAction);
    spot_addElement("enabledOnSelectionOnly", enabledOnSelectionOnly);
    spot_addElement("enabledIfHasValueOnly", enabledIfHasValueOnly);
    spot_addElement("groupName", groupName);
    spot_addElement("shortcutKeystroke", shortcutKeystroke);
    spot_addElement("linkedData", linkedData);
    spot_addElement("actionLink", actionLink);
  }

  /**
   * Gets the actionLink element
   *
   * @return the actionLink element or null if a reference was never created
   */
  public Link getActionLink() {
    return actionLink;
  }

  /**
   * Gets the reference to the actionLink element
   * A reference is created if necessary
   *
   * @return the reference to the actionLink element
   */
  public Link getActionLinkReference() {
    if (actionLink == null) {
      actionLink = new Link(true);
      super.spot_setReference("actionLink", actionLink);
    }

    return actionLink;
  }

  /**
   * Sets the reference to the actionLink element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setActionLink(iSPOTElement reference) throws ClassCastException {
    actionLink = (Link) reference;
    spot_setReference("actionLink", reference);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
