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
 * through a date range and associated
 * <p>
 * time while also allowing a date or time to be manually
 * entered
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class DateTimeSpinner extends Spinner {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the value for the spinner */
  public SPOTDateTime value = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** Behavior: minimum value for the spinner */
  public SPOTDateTime minValue = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** Behavior: maximum value for the spinner */
  public SPOTDateTime maxValue = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** a format string for how the date/time should be displayed */
  public SPOTPrintableString format = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DateTimeSpinner</code> object.
   */
  public DateTimeSpinner() {
    this(true);
  }

  /**
   * Creates a new <code>DateTimeSpinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DateTimeSpinner(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>DateTimeSpinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DateTimeSpinner(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 4;
    super.spot_setElements();
    spot_addElement("value", value);
    spot_addElement("minValue", minValue);
    spot_addElement("maxValue", maxValue);
    spot_addElement("format", format);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
