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
 * This class represents the configuration information for
 * plotting information for a chart widget
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Plot extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** a background image for the plot area */
  public SPOTPrintableString bgImageURL     = new SPOTPrintableString(null, 0, 255, true);
  protected Margin           contentPadding = null;
//GENERATED_COMMENT{}

  /** Appearance~gradient: the background color can be "transparent" for transparent backgrounds */
  public SPOTPrintableString bgColor = new SPOTPrintableString(null, 0, 128, true);
//GENERATED_COMMENT{}

  /** Appearance~color: the color for the border around the plot */
  public SPOTPrintableString borderColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: the type of grid line to use for the plot */
  public CGridLine gridLine = new CGridLine(null, null, CGridLine.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: how shapes representing data points are drawn */
  public CShapes shapes = new CShapes(null, null, CShapes.filled, "filled", false);
//GENERATED_COMMENT{}

  /** Appearance: how data points are labeled */
  public CLabels labels = new CLabels(null, null, CLabels.values, "values", false);
//GENERATED_COMMENT{}

  /** Appearance: color alpha transparency value for chart foreground colors */
  public SPOTInteger fgAlpha = new SPOTInteger(null, 0, 255, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: color alpha transparency value for chart foreground colors */
  public SPOTReal lineThickness = new SPOTReal(null, "0", "255", true);
//GENERATED_COMMENT{}

  /** Appearance: color alpha transparency value for chart foreground colors */
  public SPOTReal outlineThickness = new SPOTReal(null, "0", "20", "1", true);
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the plot */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Plot</code> object.
   */
  public Plot() {
    this(true);
  }

  /**
   * Creates a new <code>Plot</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Plot(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Plot</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Plot(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 11;
    super.spot_setElements();
    spot_addElement("bgImageURL", bgImageURL);
    spot_addElement("contentPadding", contentPadding);
    spot_addElement("bgColor", bgColor);
    bgColor.spot_defineAttribute("type", "linear");
    bgColor.spot_defineAttribute("direction", "vertical_top");
    bgColor.spot_defineAttribute("magnitude", null);
    bgColor.spot_defineAttribute("distribution", null);
    bgColor.spot_defineAttribute("radius", null);
    spot_addElement("borderColor", borderColor);
    spot_addElement("gridLine", gridLine);
    gridLine.spot_defineAttribute("color", null);
    spot_addElement("shapes", shapes);
    shapes.spot_defineAttribute("outlineColor", null);
    shapes.spot_defineAttribute("filledColor", null);
    spot_addElement("labels", labels);
    labels.spot_defineAttribute("format", null);
    labels.spot_defineAttribute("fgColor", null);
    labels.spot_defineAttribute("bgColor", null);
    labels.spot_defineAttribute("border", null);
    labels.spot_defineAttribute("offset", null);
    labels.spot_defineAttribute("font", null);
    spot_addElement("fgAlpha", fgAlpha);
    spot_addElement("lineThickness", lineThickness);
    spot_addElement("outlineThickness", outlineThickness);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
  }

  /**
   * Gets the contentPadding element
   *
   * @return the contentPadding element or null if a reference was never created
   */
  public Margin getContentPadding() {
    return contentPadding;
  }

  /**
   * Gets the reference to the contentPadding element
   * A reference is created if necessary
   *
   * @return the reference to the contentPadding element
   */
  public Margin getContentPaddingReference() {
    if (contentPadding == null) {
      contentPadding = new Margin(true);
      super.spot_setReference("contentPadding", contentPadding);
    }

    return contentPadding;
  }

  /**
   * Sets the reference to the contentPadding element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setContentPadding(iSPOTElement reference) throws ClassCastException {
    contentPadding = (Margin) reference;
    spot_setReference("contentPadding", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Plot.gridLine</code> ENUMERATED object
   */
  public static class CGridLine extends SPOTEnumerated {
    public final static int auto   = 0;
    public final static int solid  = 1;
    public final static int dashed = 2;
    public final static int dotted = 3;
    public final static int none   = 4;

    /**
     * Creates a new <code>CGridLine</code> object
     */
    public CGridLine() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CGridLine</code> object
     *
     * @param val the value
     */
    public CGridLine(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>gridLine</code> object
     * the <code>Plot.gridLine</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CGridLine(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "solid(1), " + "dashed(2), " + "dotted(3), " + "none(4) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("color", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "auto", "solid", "dashed", "dotted", "none" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Plot.shapes</code> ENUMERATED object
   */
  public static class CShapes extends SPOTEnumerated {

    /** no shapes are drawn */
    public final static int none = 0;

    /** a filled-in shape is drawn for each data point */
    public final static int filled = 1;

    /** an outlined shape is drawn for each data point */
    public final static int outlined = 2;

    /** a filled-in and outlined shape is drawn for each data point */
    public final static int filled_and_outlined = 3;

    /**
     * Creates a new <code>CShapes</code> object
     */
    public CShapes() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CShapes</code> object
     *
     * @param val the value
     */
    public CShapes(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>shapes</code> object
     * the <code>Plot.shapes</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CShapes(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "filled(1), " + "outlined(2), " + "filled_and_outlined(3) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 2;
      spot_defineAttribute("outlineColor", null);
      spot_defineAttribute("filledColor", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "none", "filled", "outlined", "filled_and_outlined" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Plot.labels</code> ENUMERATED object
   */
  public static class CLabels extends SPOTEnumerated {

    /** data points are labeled with the value of the data point */
    public final static int values = 0;

    /** data points are labeled with the tooltip of the data point */
    public final static int tooltips = 1;

    /** data points are labeled with the linked data of the data point */
    public final static int linked_data = 2;

    /**
     * Creates a new <code>CLabels</code> object
     */
    public CLabels() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CLabels</code> object
     *
     * @param val the value
     */
    public CLabels(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>labels</code> object
     * the <code>Plot.labels</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CLabels(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "values(0), " + "tooltips(1), " + "linked_data(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 6;
      spot_defineAttribute("format", null);
      spot_defineAttribute("fgColor", null);
      spot_defineAttribute("bgColor", null);
      spot_defineAttribute("border", null);
      spot_defineAttribute("offset", null);
      spot_defineAttribute("font", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "values", "tooltips", "linked_data" };
  }
  //}GENERATED_INNER_CLASSES
}