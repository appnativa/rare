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
 * This class represents the configuration for a toolbar
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ToolBar extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** the location of the pane */
  public CLocation location = new CLocation(null, null, CLocation.north, "north", false);
//GENERATED_COMMENT{}

  /** Design: the row for the toolbar (zero represents the top most position) */
  public SPOTInteger row = new SPOTInteger(null, 0, 3, 0, false);
//GENERATED_COMMENT{}

  /** Design: the position of the toolbar (zero represents left most position) in relation to other toolbars */
  public SPOTInteger column = new SPOTInteger(null, 0, 3, 0, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the toolbar is to be horizontally oriented */
  public SPOTBoolean horizontal = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the default for the showText property for pushbuttons when the value has no been explicitly set on the button */
  public SPOTBoolean buttonShowTextDefault = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Hidden: the widgets to be placed on the toolbar */
  public SPOTSet widgets = new SPOTSet("widget", new SPOTAny("com.appnativa.rare.spot.Widget"), -1, -1, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ToolBar</code> object.
   */
  public ToolBar() {
    this(true);
  }

  /**
   * Creates a new <code>ToolBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ToolBar(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ToolBar</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ToolBar(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 7;
    super.spot_setElements();
    spot_addElement("location", location);
    spot_addElement("row", row);
    spot_addElement("column", column);
    spot_addElement("horizontal", horizontal);
    spot_addElement("buttonShowTextDefault", buttonShowTextDefault);
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("widgets", widgets);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ToolBar.location</code> ENUMERATED object
   */
  public static class CLocation extends SPOTEnumerated {
    public final static int north = 1;
    public final static int south = 2;
    public final static int east  = 3;
    public final static int west  = 4;

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
     * the <code>ToolBar.location</code> ENUMERATED object
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
      return "{" + "north(1), " + "south(2), " + "east(3), " + "west(4) }";
    }

    private final static int    _nchoices[] = { 1, 2, 3, 4 };
    private final static String _schoices[] = { "north", "south", "east", "west" };
  }
  //}GENERATED_INNER_CLASSES
}
