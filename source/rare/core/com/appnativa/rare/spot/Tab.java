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
 * This class represents the configuration information of a tab
 * in a tab viewer
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Tab extends Region {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the tab's title */
  public SPOTPrintableString title = new SPOTPrintableString(null, 0, 355, "Tab", false);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the tab */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: url to use to retrieve a icon associated with the item's disabled state */
  public SPOTPrintableString disabledIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** url to use to retrieve an alternate icon for the tab */
  public SPOTPrintableString alternateIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Behavior: whether the tab's data should be loaded when it is activated or when the viewer is loaded */
  public SPOTBoolean loadOnActivation = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the tab's data should be reloaded every time the tab is activated */
  public SPOTBoolean reloadOnActivation = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the foreground color of the tab */
  public SPOTPrintableString fgColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~color: the foreground color for the tab */
  public SPOTPrintableString tooltip = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: data linked to the tab */
  public SPOTPrintableString linkedData = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: whether the tab is enabled */
  public SPOTBoolean enabled = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Tab</code> object.
   */
  public Tab() {
    this(true);
  }

  /**
   * Creates a new <code>Tab</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Tab(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Tab</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Tab(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 10;
    super.spot_setElements();
    spot_addElement("title", title);
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
    spot_addElement("alternateIcon", alternateIcon);
    alternateIcon.spot_defineAttribute("alt", null);
    alternateIcon.spot_defineAttribute("slice", null);
    alternateIcon.spot_defineAttribute("size", null);
    alternateIcon.spot_defineAttribute("scaling", null);
    alternateIcon.spot_defineAttribute("density", null);
    spot_addElement("loadOnActivation", loadOnActivation);
    spot_addElement("reloadOnActivation", reloadOnActivation);
    spot_addElement("fgColor", fgColor);
    spot_addElement("tooltip", tooltip);
    spot_addElement("linkedData", linkedData);
    spot_addElement("enabled", enabled);
  }

  //}GENERATED_METHODS
  public Tab(String title, String targetName, String icon, String data) {
    this(false);
    setValues(title, targetName, icon, data);
  }

  public void setValues(String title, String name, String icon) {
    this.icon.setValue(icon);
    this.name.setValue(name);
    this.title.setValue(title);
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
