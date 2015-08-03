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
 * This class represents the configuration for a bar that can
 * display the progress of an ongoing operation
 * <p>
 * Progress values range between 0 and 100
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ProgressBar extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the value for the slider */
  public SPOTInteger value = new SPOTInteger(null, 0, false);
//GENERATED_COMMENT{}

  /** Appearance~~orientation: whether the progress bar is to be horizontally oriented */
  public SPOTBoolean horizontal = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the progress state is indeterminate */
  public SPOTBoolean indeterminate = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether a progress text area should be shown */
  public SPOTBoolean showText = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** progress text to display */
  public SPOTPrintableString progressText = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** size of the progress graphic */
  public SPOTPrintableString graphicSize = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Layout: the location of the text label */
  public CLabelSide labelSide = new CLabelSide(null, null, CLabelSide.auto, "auto", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ProgressBar</code> object.
   */
  public ProgressBar() {
    this(true);
  }

  /**
   * Creates a new <code>ProgressBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ProgressBar(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ProgressBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ProgressBar(boolean optional, boolean setElements) {
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
    spot_addElement("value", value);
    spot_addElement("horizontal", horizontal);
    spot_addElement("indeterminate", indeterminate);
    indeterminate.spot_defineAttribute("useSpinner", null);
    spot_addElement("showText", showText);
    spot_addElement("progressText", progressText);
    spot_addElement("graphicSize", graphicSize);
    spot_addElement("labelSide", labelSide);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ProgressBar.labelSide</code> ENUMERATED object
   */
  public static class CLabelSide extends SPOTEnumerated {
    public final static int auto   = 0;
    public final static int left   = 1;
    public final static int right  = 2;
    public final static int top    = 3;
    public final static int bottom = 4;

    /**
     * Creates a new <code>CLabelSide</code> object
     */
    public CLabelSide() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CLabelSide</code> object
     *
     * @param val the value
     */
    public CLabelSide(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>labelSide</code> object
     * the <code>ProgressBar.labelSide</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CLabelSide(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "auto(0), " + "left(1), " + "right(2), " + "top(3), " + "bottom(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "auto", "left", "right", "top", "bottom" };
  }
  //}GENERATED_INNER_CLASSES
}
