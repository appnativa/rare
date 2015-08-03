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
 * This class represents a cell in a grid
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class GridCell extends Rectangle {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~gradient~background: the background color (use "transparent" for transparent backgrounds) */
  public SPOTPrintableString bgColor = new SPOTPrintableString(null, 0, 128, true);
//GENERATED_COMMENT{}

  /** Appearance~image~backgroundOverlayPainter: an background image for the cell */
  public SPOTPrintableString bgImageURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~borders~reload: borders for the cell */
  protected SPOTSet borders = null;
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the item */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>GridCell</code> object.
   */
  public GridCell() {
    this(true);
  }

  /**
   * Creates a new <code>GridCell</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public GridCell(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>GridCell</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected GridCell(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 4;
    super.spot_setElements();
    spot_addElement("bgColor", bgColor);
    bgColor.spot_defineAttribute("type", "linear");
    bgColor.spot_defineAttribute("direction", "vertical_top");
    bgColor.spot_defineAttribute("magnitude", null);
    bgColor.spot_defineAttribute("distribution", null);
    bgColor.spot_defineAttribute("radius", null);
    spot_addElement("bgImageURL", bgImageURL);
    bgImageURL.spot_defineAttribute("opacity", "100%");
    bgImageURL.spot_defineAttribute("renderType", "tiled");
    bgImageURL.spot_defineAttribute("waitForLoad", "false");
    bgImageURL.spot_defineAttribute("renderSpace", "within_border");
    bgImageURL.spot_defineAttribute("composite", "src_over");
    bgImageURL.spot_defineAttribute("scaling", null);
    bgImageURL.spot_defineAttribute("density", null);
    spot_addElement("borders", borders);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
  }

  /**
   * Gets the borders element
   *
   * @return the borders element or null if a reference was never created
   */
  public SPOTSet getBorders() {
    return borders;
  }

  /**
   * Gets the reference to the borders element
   * A reference is created if necessary
   *
   * @return the reference to the borders element
   */
  public SPOTSet getBordersReference() {
    if (borders == null) {
      borders = new SPOTSet("border", new CBorder(null, null, CBorder.standard, "standard", true), -1, -1, true);
      super.spot_setReference("borders", borders);
    }

    return borders;
  }

  /**
   * Sets the reference to the borders element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setBorders(iSPOTElement reference) throws ClassCastException {
    borders = (SPOTSet) reference;
    spot_setReference("borders", reference);
  }

  //}GENERATED_METHODS
  public static int getBorderType(String name) {
    CBorder bt = new CBorder();

    return bt.getChoiceIndexByName(name);
  }

  public CBorder addBorder(int border) {
    CBorder e = new CBorder(border);

    getBordersReference().add(e);

    return e;
  }

  public CBorder addBorder(String border) {
    CBorder e = new CBorder();

    e.setValue(border);
    getBordersReference().add(e);

    return e;
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>borders.border</code> ENUMERATED object
   */
  public static class CBorder extends SPOTEnumerated {
    public final static int none           = 0;
    public final static int standard       = 1;
    public final static int line           = 2;
    public final static int empty          = 3;
    public final static int raised         = 4;
    public final static int lowered        = 5;
    public final static int bevel_raised   = 6;
    public final static int bevel_lowered  = 7;
    public final static int etched_raised  = 8;
    public final static int etched_lowered = 9;
    public final static int frame_raised   = 10;
    public final static int frame_lowered  = 11;
    public final static int drop_shadow    = 12;
    public final static int shadow         = 13;
    public final static int group_box      = 14;
    public final static int icon           = 15;
    public final static int matte          = 16;
    public final static int back           = 17;
    public final static int balloon        = 18;
    public final static int titled         = 19;
    public final static int custom         = 31;

    /**
     * Creates a new <code>CBorder</code> object
     */
    public CBorder() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CBorder</code> object
     *
     * @param val the value
     */
    public CBorder(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>border</code> object
     * the <code>borders.border</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CBorder(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "none(0), " + "standard(1), " + "line(2), " + "empty(3), " + "raised(4), " + "lowered(5), "
             + "bevel_raised(6), " + "bevel_lowered(7), " + "etched_raised(8), " + "etched_lowered(9), "
             + "frame_raised(10), " + "frame_lowered(11), " + "drop_shadow(12), " + "shadow(13), " + "group_box(14), "
             + "icon(15), " + "matte(16), " + "back(17), " + "balloon(18), " + "titled(19), " + "custom(31) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 21;
      spot_defineAttribute("color", null);
      spot_defineAttribute("thickness", null);
      spot_defineAttribute("style", null);
      spot_defineAttribute("cornerArc", null);
      spot_defineAttribute("padForArc", "true");
      spot_defineAttribute("insets", null);
      spot_defineAttribute("title", null);
      spot_defineAttribute("titleLocation", null);
      spot_defineAttribute("titleFont", null);
      spot_defineAttribute("titleColor", null);
      spot_defineAttribute("icon", null);
      spot_defineAttribute("flatTop", null);
      spot_defineAttribute("flatBottom", null);
      spot_defineAttribute("noBottom", null);
      spot_defineAttribute("noTop", null);
      spot_defineAttribute("noLeft", null);
      spot_defineAttribute("noRight", null);
      spot_defineAttribute("class", null);
      spot_defineAttribute("renderType", null);
      spot_defineAttribute("opacity", null);
      spot_defineAttribute("customProperties", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 31
    };
    private final static String _schoices[] = {
      "none", "standard", "line", "empty", "raised", "lowered", "bevel_raised", "bevel_lowered", "etched_raised",
      "etched_lowered", "frame_raised", "frame_lowered", "drop_shadow", "shadow", "group_box", "icon", "matte", "back",
      "balloon", "titled", "custom"
    };
  }
  //}GENERATED_INNER_CLASSES
}