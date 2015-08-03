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
 * This class represents the configuration information of a
 * font
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Font extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~fontnames: the font family */
  public SPOTPrintableString family = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: the font size (can prefix with '+' or '-' or '*' ) */
  public SPOTPrintableString size = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: the font style */
  public CStyle style = new CStyle(null, null, CStyle.normal, "normal", true);
//GENERATED_COMMENT{}

  /** Appearance: whether to use a monospace font (if true will ignore the family property) */
  public SPOTBoolean monospaced = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether to underline text */
  public SPOTBoolean underlined = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether to strike through text */
  public SPOTBoolean strikeThrough = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Font</code> object.
   */
  public Font() {
    this(true);
  }

  /**
   * Creates a new <code>Font</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Font(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Font</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Font(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 6;
    super.spot_setElements();
    spot_addElement("family", family);
    family.spot_defineAttribute("is_property", null);
    spot_addElement("size", size);
    spot_addElement("style", style);
    spot_addElement("monospaced", monospaced);
    spot_addElement("underlined", underlined);
    spot_addElement("strikeThrough", strikeThrough);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Font.style</code> ENUMERATED object
   */
  public static class CStyle extends SPOTEnumerated {

    /** normal font */
    public final static int normal = 0;

    /** bold font */
    public final static int bold = 1;

    /** italic font */
    public final static int italic = 2;

    /** italic and bold font */
    public final static int bold_italic = 3;

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
     * the <code>Font.style</code> ENUMERATED object
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
      return "{" + "normal(0), " + "bold(1), " + "italic(2), " + "bold_italic(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "normal", "bold", "italic", "bold_italic" };
  }
  //}GENERATED_INNER_CLASSES
}
