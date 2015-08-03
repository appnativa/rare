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
 * This class represents configuration information for a widget
 * overlay
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class OverlayInfo extends SPOTSequence {
  //GENERATED_MEMBERS{
  public SPOTAny    widget    = new SPOTAny("com.appnativa.rare.spot.Widget");
  public CLocation  location  = new CLocation(null, null, CLocation.lower_right, "lower_right", false);
  public CDisplayed displayed = new CDisplayed(null, null, CDisplayed.always, "always", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>OverlayInfo</code> object.
   */
  public OverlayInfo() {
    this(true);
  }

  /**
   * Creates a new <code>OverlayInfo</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public OverlayInfo(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>OverlayInfo</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected OverlayInfo(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 3;
    super.spot_setElements();
    spot_addElement("widget", widget);
    spot_addElement("location", location);
    spot_addElement("displayed", displayed);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>OverlayInfo.location</code> ENUMERATED object
   */
  public static class CLocation extends SPOTEnumerated {

    /** widget is placed in the upper left */
    public final static int upper_left = 1;

    /** widget is placed in the middle on the top */
    public final static int upper_middle = 2;

    /** widget is placed in the upper right */
    public final static int upper_right = 3;

    /** widget is placed in the middle on the upper left */
    public final static int left_middle = 4;

    /** widget is centered */
    public final static int centered = 5;

    /** widget is placed in the middle on the right */
    public final static int right_middle = 6;

    /** widget is placed in the upper left */
    public final static int lower_left = 7;

    /** widget is placed in the middle on the bottom */
    public final static int lower_middle = 8;

    /** widget is placed in the lower right */
    public final static int lower_right = 9;

    /**
     * Creates a new <code>CLocation</code> object
     */
    public CLocation() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CLocation</code> object
     *
     * @param val the value
     */
    public CLocation(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>location</code> object
     * the <code>OverlayInfo.location</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CLocation(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "upper_left(1), " + "upper_middle(2), " + "upper_right(3), " + "left_middle(4), " + "centered(5), "
             + "right_middle(6), " + "lower_left(7), " + "lower_middle(8), " + "lower_right(9) }";
    }

    private final static int    _nchoices[] = {
      1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    private final static String _schoices[] = {
      "upper_left", "upper_middle", "upper_right", "left_middle", "centered", "right_middle", "lower_left",
      "lower_middle", "lower_right"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>OverlayInfo.displayed</code> ENUMERATED object
   */
  public static class CDisplayed extends SPOTEnumerated {
    public final static int always             = 1;
    public final static int when_not_focused   = 2;
    public final static int when_empty         = 3;
    public final static int before_interaction = 4;

    /**
     * Creates a new <code>CDisplayed</code> object
     */
    public CDisplayed() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDisplayed</code> object
     *
     * @param val the value
     */
    public CDisplayed(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>displayed</code> object
     * the <code>OverlayInfo.displayed</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDisplayed(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "always(1), " + "when_not_focused(2), " + "when_empty(3), " + "before_interaction(4) }";
    }

    private final static int    _nchoices[] = { 1, 2, 3, 4 };
    private final static String _schoices[] = { "always", "when_not_focused", "when_empty", "before_interaction" };
  }
  //}GENERATED_INNER_CLASSES
}
