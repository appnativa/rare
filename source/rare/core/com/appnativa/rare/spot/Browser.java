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
 * The class represents the embedded browser for the platform
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Browser extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: whether the status bar should be updated automatically (this will only happen if no change listener is installed) */
  public SPOTBoolean updateStatusBar = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether the browser's location bar should be shown (if the browser supports it) */
  public SPOTBoolean showLocationBar = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the browser's toolbar should be shown (if the browser supports it) */
  public SPOTBoolean showToolBar = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the browser's statusbar should be shown (if the browser supports it); if true then the updateStatusBar property is ignored */
  public SPOTBoolean showStatusBar = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Browser</code> object.
   */
  public Browser() {
    this(true);
  }

  /**
   * Creates a new <code>Browser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Browser(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Browser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Browser(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 4;
    this.attributeSizeHint += 3;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onStartedLoading", null);
    spot_defineAttribute("onFinishedLoading", null);
    spot_addElement("updateStatusBar", updateStatusBar);
    updateStatusBar.spot_defineAttribute("showCancel", "true");
    spot_addElement("showLocationBar", showLocationBar);
    spot_addElement("showToolBar", showToolBar);
    spot_addElement("showStatusBar", showStatusBar);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
