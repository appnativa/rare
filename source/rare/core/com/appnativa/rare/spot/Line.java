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
 * This class represents the configuration information for a
 * horizontal or vertical line
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Line extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Layout: whether the line is to be horizontally oriented */
  public SPOTBoolean horizontal = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~leftText: label for the left/top side of the line */
  public SPOTPrintableString leftLabel = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the left/top side of the line */
  public SPOTPrintableString leftIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~~~rightText: label for the right/bottom side of the line */
  public SPOTPrintableString rightLabel = new SPOTPrintableString(null, 0, 80, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the right/bottom side of the line */
  public SPOTPrintableString rightIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~lines~reload: the line style; if not specified than a standard line will be drawn */
  protected SPOTSet lines = null;
//GENERATED_COMMENT{}

  /** Layout: the position of the line */
  public CPosition position = new CPosition(null, null, CPosition.center, "center", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Line</code> object.
   */
  public Line() {
    this(true);
  }

  /**
   * Creates a new <code>Line</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Line(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Line</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Line(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 7;
    super.spot_setElements();
    spot_addElement("horizontal", horizontal);
    spot_addElement("leftLabel", leftLabel);
    spot_addElement("leftIcon", leftIcon);
    leftIcon.spot_defineAttribute("alt", null);
    leftIcon.spot_defineAttribute("slice", null);
    leftIcon.spot_defineAttribute("size", null);
    leftIcon.spot_defineAttribute("scaling", null);
    leftIcon.spot_defineAttribute("density", null);
    spot_addElement("rightLabel", rightLabel);
    spot_addElement("rightIcon", rightIcon);
    rightIcon.spot_defineAttribute("alt", null);
    rightIcon.spot_defineAttribute("slice", null);
    rightIcon.spot_defineAttribute("size", null);
    rightIcon.spot_defineAttribute("scaling", null);
    rightIcon.spot_defineAttribute("density", null);
    spot_addElement("lines", lines);
    spot_addElement("position", position);
  }

  /**
   * Gets the lines element
   *
   * @return the lines element or null if a reference was never created
   */
  public SPOTSet getLines() {
    return lines;
  }

  /**
   * Gets the reference to the lines element
   * A reference is created if necessary
   *
   * @return the reference to the lines element
   */
  public SPOTSet getLinesReference() {
    if (lines == null) {
      lines = new SPOTSet("lineStyle", new CLineStyle(null, null, CLineStyle.separator, "separator", false), -1, -1,
                          true);
      super.spot_setReference("lines", lines);
    }

    return lines;
  }

  /**
   * Sets the reference to the lines element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setLines(iSPOTElement reference) throws ClassCastException {
    lines = (SPOTSet) reference;
    spot_setReference("lines", reference);
  }

  //}GENERATED_METHODS
  public CLineStyle addLine(int line) {
    CLineStyle e = new CLineStyle(line);

    getLinesReference().add(e);

    return e;
  }

  public CLineStyle addLine(String line) {
    CLineStyle e = new CLineStyle();

    e.setValue(line);
    getLinesReference().add(e);

    return e;
  }

  public static CLineStyle createLine(String line) {
    CLineStyle e = new CLineStyle();

    e.setValue(line);

    return e;
  }

  public CLineStyle setLine(int line) {
    CLineStyle e = new CLineStyle(line);

    getLinesReference().clear();
    getLinesReference().add(e);

    return e;
  }

  public CLineStyle setLine(String line) {
    CLineStyle e = new CLineStyle();

    e.setValue(line);
    getLinesReference().clear();
    getLinesReference().add(e);

    return e;
  }

  public void setLineAttribute(String name, String value) {
    if ((lines == null) || (lines.getCount() == 0)) {
      SPOTEnumerated e = new CLineStyle(CLineStyle.separator);

      getLinesReference().add(e);
    }

    lines.getEx(0).spot_setAttribute(name, value);
  }

  public CLineStyle getLine() {
    if ((lines == null) || (lines.getCount() == 0)) {
      SPOTEnumerated e = new CLineStyle(CLineStyle.separator);

      getLinesReference().add(e);
    }

    return (CLineStyle) lines.get(0);
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>lines.lineStyle</code> ENUMERATED object
   */
  public static class CLineStyle extends SPOTEnumerated {
    public final static int separator = 1;
    public final static int solid     = 2;
    public final static int dashed    = 3;
    public final static int dotted    = 4;

    /**
     * Creates a new <code>CLineStyle</code> object
     */
    public CLineStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CLineStyle</code> object
     *
     * @param val the value
     */
    public CLineStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>lineStyle</code> object
     * the <code>lines.lineStyle</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CLineStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "separator(1), " + "solid(2), " + "dashed(3), " + "dotted(4) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 4;
      spot_defineAttribute("color", null);
      spot_defineAttribute("thickness", null);
      spot_defineAttribute("leftOffset", null);
      spot_defineAttribute("rightOffset", null);
    }

    private final static int    _nchoices[] = { 1, 2, 3, 4 };
    private final static String _schoices[] = { "separator", "solid", "dashed", "dotted" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Line.position</code> ENUMERATED object
   */
  public static class CPosition extends SPOTEnumerated {
    public final static int top    = 1;
    public final static int center = 2;
    public final static int bottom = 3;

    /**
     * Creates a new <code>CPosition</code> object
     */
    public CPosition() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CPosition</code> object
     *
     * @param val the value
     */
    public CPosition(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>position</code> object
     * the <code>Line.position</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CPosition(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "top(1), " + "center(2), " + "bottom(3) }";
    }

    private final static int    _nchoices[] = { 1, 2, 3 };
    private final static String _schoices[] = { "top", "center", "bottom" };
  }
  //}GENERATED_INNER_CLASSES
}
