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
 * This class represents the configuration of a status bar
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class StatusBar extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the time of the day should be shown on the status bar */
  public SPOTBoolean showTime = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether memory usage should be shown on the status bar */
  public SPOTBoolean showMemoryUsage = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the insert/overwrite button should be shown on the status bar */
  public SPOTBoolean showInsertOverwrite = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether a resizing corner should be shown */
  public SPOTBoolean showResizeCorner = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: a set of actions to place on the status bar */
  public SPOTSet actions = new SPOTSet("action", new ActionItem(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Appearance~~status: default status bar message */
  public SPOTPrintableString defaultMessage = new SPOTPrintableString(null, 0, 80, "Ready", false);
//GENERATED_COMMENT{}

  /** Design: the maximum number of items to keep in the stausbar history */
  public SPOTInteger maxHistoryItems = new SPOTInteger(null, 0, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>StatusBar</code> object.
   */
  public StatusBar() {
    this(true);
  }

  /**
   * Creates a new <code>StatusBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public StatusBar(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>StatusBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected StatusBar(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 7;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("os", null);
    spot_addElement("showTime", showTime);
    spot_addElement("showMemoryUsage", showMemoryUsage);
    spot_addElement("showInsertOverwrite", showInsertOverwrite);
    spot_addElement("showResizeCorner", showResizeCorner);
    spot_addElement("actions", actions);
    spot_addElement("defaultMessage", defaultMessage);
    spot_addElement("maxHistoryItems", maxHistoryItems);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
