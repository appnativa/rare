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
 * The class represents the embedded media player for the
 * platform
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class MediaPlayer extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: whether the media player's control bar will be shown */
  public SPOTBoolean showControlBar = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether the media player will stretch to fit the available space */
  public SPOTBoolean stretchToFit = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>MediaPlayer</code> object.
   */
  public MediaPlayer() {
    this(true);
  }

  /**
   * Creates a new <code>MediaPlayer</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public MediaPlayer(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>MediaPlayer</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected MediaPlayer(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 2;
    super.spot_setElements();
    spot_addElement("showControlBar", showControlBar);
    spot_addElement("stretchToFit", stretchToFit);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
