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
 * This class represents the configuration for a label widget
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Label extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the label */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: the text for the label's value */
  public SPOTPrintableString value = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: the name of the widget that the label is for */
  public SPOTPrintableString labelFor = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: the horizontal alignment */
  public CTextHAlignment textHAlignment = new CTextHAlignment(null, null, CTextHAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the vertical alignment */
  public CTextVAlignment textVAlignment = new CTextVAlignment(null, null, CTextVAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the position of the icon */
  public CIconPosition iconPosition = new CIconPosition(null, null, CIconPosition.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean scaleIcon = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: how to scale the widget's icon */
  public COrientation orientation = new COrientation(null, null, COrientation.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Design: whether hyperlinks are supported in the label */
  public SPOTBoolean supportHyperLinks = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether word wrapping is supported or text is truncated */
  public SPOTBoolean wordWrap = new SPOTBoolean(null, false, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Label</code> object.
   */
  public Label() {
    this(true);
  }

  /**
   * Creates a new <code>Label</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Label(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Label</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Label(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 10;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onAction", null);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("value", value);
    spot_addElement("labelFor", labelFor);
    spot_addElement("textHAlignment", textHAlignment);
    spot_addElement("textVAlignment", textVAlignment);
    spot_addElement("iconPosition", iconPosition);
    spot_addElement("scaleIcon", scaleIcon);
    scaleIcon.spot_defineAttribute("percent", null);
    spot_addElement("orientation", orientation);
    spot_addElement("supportHyperLinks", supportHyperLinks);
    spot_addElement("wordWrap", wordWrap);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Label.textHAlignment</code> ENUMERATED object
   */
  public static class CTextHAlignment extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the text is left aligned */
    public final static int left = 1;

    /** the text is right aligned */
    public final static int right = 2;

    /** the text is aligned along the leading edge */
    public final static int leading = 3;

    /** the text is aligned along the trailing edge */
    public final static int trailing = 4;

    /** the text is centered */
    public final static int center = 5;

    /**
     * Creates a new <code>CTextHAlignment</code> object
     */
    public CTextHAlignment() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTextHAlignment</code> object
     *
     * @param val the value
     */
    public CTextHAlignment(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>textHAlignment</code> object
     * the <code>Label.textHAlignment</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTextHAlignment(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "left(1), " + "right(2), " + "leading(3), " + "trailing(4), " + "center(5) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "auto", "left", "right", "leading", "trailing", "center"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Label.textVAlignment</code> ENUMERATED object
   */
  public static class CTextVAlignment extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the text is top aligned */
    public final static int top = 1;

    /** the text is bottom aligned */
    public final static int bottom = 2;

    /** the text is centered */
    public final static int center = 5;

    /**
     * Creates a new <code>CTextVAlignment</code> object
     */
    public CTextVAlignment() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTextVAlignment</code> object
     *
     * @param val the value
     */
    public CTextVAlignment(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>textVAlignment</code> object
     * the <code>Label.textVAlignment</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTextVAlignment(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "top(1), " + "bottom(2), " + "center(5) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 5 };
    private final static String _schoices[] = { "auto", "top", "bottom", "center" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Label.iconPosition</code> ENUMERATED object
   */
  public static class CIconPosition extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the icon is positioned to the left of the text */
    public final static int left = 1;

    /** the icon is positioned the right of the text */
    public final static int right = 2;

    /** the icon is positioned along the leading edge */
    public final static int leading = 3;

    /** the icon is positioned along the trailing edge */
    public final static int trailing = 4;

    /** the icon is positioned above the text and is centered */
    public final static int top_center = 5;

    /** the icon is positioned to the top_left of the text */
    public final static int top_left = 6;

    /** the icon is positioned the top_right of the text */
    public final static int top_right = 7;

    /** the icon is positioned below the text and is centered */
    public final static int bottom_center = 8;

    /** the icon is positioned to the bottom_left of the text */
    public final static int bottom_left = 9;

    /** the icon is positioned the bottom_right of the text */
    public final static int bottom_right = 10;

    /** the icon is positioned to the far right of the text */
    public final static int right_justified = 11;

    /**
     * Creates a new <code>CIconPosition</code> object
     */
    public CIconPosition() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CIconPosition</code> object
     *
     * @param val the value
     */
    public CIconPosition(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>iconPosition</code> object
     * the <code>Label.iconPosition</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CIconPosition(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "left(1), " + "right(2), " + "leading(3), " + "trailing(4), " + "top_center(5), "
             + "top_left(6), " + "top_right(7), " + "bottom_center(8), " + "bottom_left(9), " + "bottom_right(10), "
             + "right_justified(11) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
    };
    private final static String _schoices[] = {
      "auto", "left", "right", "leading", "trailing", "top_center", "top_left", "top_right", "bottom_center",
      "bottom_left", "bottom_right", "right_justified"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Label.orientation</code> ENUMERATED object
   */
  public static class COrientation extends SPOTEnumerated {
    public final static int auto          = 0;
    public final static int horizontal    = 1;
    public final static int vertical_up   = 2;
    public final static int vertical_down = 3;

    /**
     * Creates a new <code>COrientation</code> object
     */
    public COrientation() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>COrientation</code> object
     *
     * @param val the value
     */
    public COrientation(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>orientation</code> object
     * the <code>Label.orientation</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public COrientation(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "horizontal(1), " + "vertical_up(2), " + "vertical_down(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "auto", "horizontal", "vertical_up", "vertical_down" };
  }
  //}GENERATED_INNER_CLASSES
}
