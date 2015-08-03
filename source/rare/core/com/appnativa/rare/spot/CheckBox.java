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
 * This class represents the configuration for a check box
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class CheckBox extends Button {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the checkbox style */
  public CStyle style = new CStyle(null, null, CStyle.standard, "standard", false);
//GENERATED_COMMENT{}

  /** Appearance~~state: the initial state of the checkbox */
  public CInitialState initialState = new CInitialState(null, null, CInitialState.deselected, "deselected", false);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the checkbox when it's in a state other than selected or deselected (if it is a tri-state checkbox) */
  public SPOTPrintableString indeterminateIcon = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>CheckBox</code> object.
   */
  public CheckBox() {
    this(true);
  }

  /**
   * Creates a new <code>CheckBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public CheckBox(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>CheckBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected CheckBox(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 3;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("style", style);
    spot_addElement("initialState", initialState);
    spot_addElement("indeterminateIcon", indeterminateIcon);
    indeterminateIcon.spot_defineAttribute("alt", null);
    indeterminateIcon.spot_defineAttribute("slice", null);
    indeterminateIcon.spot_defineAttribute("size", null);
    indeterminateIcon.spot_defineAttribute("scaling", null);
    indeterminateIcon.spot_defineAttribute("density", null);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>CheckBox.style</code> ENUMERATED object
   */
  public static class CStyle extends SPOTEnumerated {
    public final static int standard      = 0;
    public final static int on_off_switch = 1;
    public final static int tri_state     = 2;

    /**
     * Creates a new <code>CStyle</code> object
     */
    public CStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CStyle</code> object
     *
     * @param val the value
     */
    public CStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>style</code> object
     * the <code>CheckBox.style</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "standard(0), " + "on_off_switch(1), " + "tri_state(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "standard", "on_off_switch", "tri_state" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>CheckBox.initialState</code> ENUMERATED object
   */
  public static class CInitialState extends SPOTEnumerated {
    public final static int deselected    = 0;
    public final static int selected      = 1;
    public final static int indeterminate = 2;

    /**
     * Creates a new <code>CInitialState</code> object
     */
    public CInitialState() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CInitialState</code> object
     *
     * @param val the value
     */
    public CInitialState(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>initialState</code> object
     * the <code>CheckBox.initialState</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CInitialState(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "deselected(0), " + "selected(1), " + "indeterminate(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "deselected", "selected", "indeterminate" };
  }
  //}GENERATED_INNER_CLASSES
}