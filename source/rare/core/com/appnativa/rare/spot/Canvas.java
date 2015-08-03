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
 * The class represents a drawing surface compatible with the
 * HTML5 Canvas specification
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Canvas extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the type of context */
  public SPOTPrintableString context = new SPOTPrintableString(null, "2d", false);
//GENERATED_COMMENT{}

  /** Behavior: the type of scaling to perform */
  public CScaling scaling = new CScaling(null, null, CScaling.bilinear, "bilinear", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Canvas</code> object.
   */
  public Canvas() {
    this(true);
  }

  /**
   * Creates a new <code>Canvas</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Canvas(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Canvas</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Canvas(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 2;
    super.spot_setElements();
    spot_addElement("context", context);
    spot_addElement("scaling", scaling);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Canvas.scaling</code> ENUMERATED object
   */
  public static class CScaling extends SPOTEnumerated {
    public final static int nearest_neighbor            = 0;
    public final static int bilinear                    = 1;
    public final static int bicubic                     = 2;
    public final static int bilinear_cached             = 3;
    public final static int bicubic_cached              = 4;
    public final static int progressive_bilinear        = 5;
    public final static int progressive_bicubic         = 6;
    public final static int progressive_bilinear_cached = 7;
    public final static int progressive_bicubic_cached  = 8;

    /**
     * Creates a new <code>CScaling</code> object
     */
    public CScaling() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CScaling</code> object
     *
     * @param val the value
     */
    public CScaling(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>scaling</code> object
     * the <code>Canvas.scaling</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CScaling(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "nearest_neighbor(0), " + "bilinear(1), " + "bicubic(2), " + "bilinear_cached(3), "
             + "bicubic_cached(4), " + "progressive_bilinear(5), " + "progressive_bicubic(6), "
             + "progressive_bilinear_cached(7), " + "progressive_bicubic_cached(8) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8
    };
    private final static String _schoices[] = {
      "nearest_neighbor", "bilinear", "bicubic", "bilinear_cached", "bicubic_cached", "progressive_bilinear",
      "progressive_bicubic", "progressive_bilinear_cached", "progressive_bicubic_cached"
    };
  }
  //}GENERATED_INNER_CLASSES
}
