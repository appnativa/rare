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
 * that provide the navigation
 * <p>
 * via a horizontal series of buttons that can identify a path
 * taken; or the current selection
 * </p>
 * <p>
 * or state of a certain option
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Navigator extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~~reload: the type of navigator */
  public CType type = new CType(null, null, CType.hiearchical, "hiearchical", false);
//GENERATED_COMMENT{}

  /** Appearance: the index of the button that is to be initially selected (when not a hierarchical type) */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, -1, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the back button should be shown */
  public SPOTBoolean showBackButton = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether a buttons text should be used a it's tool tip */
  public SPOTBoolean useTextForTooltip = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether to show only icons or toe show both icons and text */
  public SPOTBoolean showIconsOnly = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether all buttons should be the same size */
  public SPOTBoolean buttonsSameSize = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~color~reload: the color for the line that separates the buttons */
  public SPOTPrintableString separatorLineColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: painter for when a button is pressed */
  protected GridCell pressedPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when a button is selected */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: the horizontal alignment of the text */
  public CTextHAlignment textHAlignment = new CTextHAlignment(null, null, CTextHAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the vertical alignment of the text */
  public CTextVAlignment textVAlignment = new CTextVAlignment(null, null, CTextVAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Layout: the position of the icon */
  public CIconPosition iconPosition = new CIconPosition(null, null, CIconPosition.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean scaleIcon = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: a set of actions that will be used to create the navigation buttons */
  public SPOTSet actions = new SPOTSet("action", new ActionItem(), -1, -1, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Navigator</code> object.
   */
  public Navigator() {
    this(true);
  }

  /**
   * Creates a new <code>Navigator</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Navigator(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Navigator</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Navigator(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 14;
    super.spot_setElements();
    spot_addElement("type", type);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("showBackButton", showBackButton);
    spot_addElement("useTextForTooltip", useTextForTooltip);
    spot_addElement("showIconsOnly", showIconsOnly);
    spot_addElement("buttonsSameSize", buttonsSameSize);
    spot_addElement("separatorLineColor", separatorLineColor);
    spot_addElement("pressedPainter", pressedPainter);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("textHAlignment", textHAlignment);
    spot_addElement("textVAlignment", textVAlignment);
    spot_addElement("iconPosition", iconPosition);
    spot_addElement("scaleIcon", scaleIcon);
    scaleIcon.spot_defineAttribute("percent", null);
    spot_addElement("actions", actions);
  }

  /**
   * Gets the pressedPainter element
   *
   * @return the pressedPainter element or null if a reference was never created
   */
  public GridCell getPressedPainter() {
    return pressedPainter;
  }

  /**
   * Gets the reference to the pressedPainter element
   * A reference is created if necessary
   *
   * @return the reference to the pressedPainter element
   */
  public GridCell getPressedPainterReference() {
    if (pressedPainter == null) {
      pressedPainter = new GridCell(true);
      super.spot_setReference("pressedPainter", pressedPainter);
      pressedPainter.spot_defineAttribute("foreground", null);
      pressedPainter.spot_defineAttribute("font", null);
    }

    return pressedPainter;
  }

  /**
   * Sets the reference to the pressedPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPressedPainter(iSPOTElement reference) throws ClassCastException {
    pressedPainter = (GridCell) reference;
    spot_setReference("pressedPainter", reference);
  }

  /**
   * Gets the selectionPainter element
   *
   * @return the selectionPainter element or null if a reference was never created
   */
  public GridCell getSelectionPainter() {
    return selectionPainter;
  }

  /**
   * Gets the reference to the selectionPainter element
   * A reference is created if necessary
   *
   * @return the reference to the selectionPainter element
   */
  public GridCell getSelectionPainterReference() {
    if (selectionPainter == null) {
      selectionPainter = new GridCell(true);
      super.spot_setReference("selectionPainter", selectionPainter);
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("font", null);
    }

    return selectionPainter;
  }

  /**
   * Sets the reference to the selectionPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSelectionPainter(iSPOTElement reference) throws ClassCastException {
    selectionPainter = (GridCell) reference;
    spot_setReference("selectionPainter", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Navigator.type</code> ENUMERATED object
   */
  public static class CType extends SPOTEnumerated {
    public final static int hiearchical = 0;
    public final static int toggle      = 1;
    public final static int option      = 2;

    /**
     * Creates a new <code>CType</code> object
     */
    public CType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CType</code> object
     *
     * @param val the value
     */
    public CType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>type</code> object
     * the <code>Navigator.type</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "hiearchical(0), " + "toggle(1), " + "option(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "hiearchical", "toggle", "option" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Navigator.textHAlignment</code> ENUMERATED object
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
     * the <code>Navigator.textHAlignment</code> ENUMERATED object
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
   * the <code>Navigator.textVAlignment</code> ENUMERATED object
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
     * the <code>Navigator.textVAlignment</code> ENUMERATED object
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
   * the <code>Navigator.iconPosition</code> ENUMERATED object
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
     * the <code>Navigator.iconPosition</code> ENUMERATED object
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
             + "top_left(6), " + "top_right(7), " + "bottom_center(8), " + "bottom_left(9), " + "bottom_right(10) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };
    private final static String _schoices[] = {
      "auto", "left", "right", "leading", "trailing", "top_center", "top_left", "top_right", "bottom_center",
      "bottom_left", "bottom_right"
    };
  }
  //}GENERATED_INNER_CLASSES
}