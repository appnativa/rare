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
 * This class represents the configuration for an abstract
 * button widget
 * <p>
 * it is mean to be used as a base for button type widgets
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Button extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the text for the button's value */
  public SPOTPrintableString value = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve the button's icon */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for a toggle style button that is in the selected state */
  public SPOTPrintableString selectedIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon~runtime: url to use to retrieve an icon for a button that is in the pressed state */
  public SPOTPrintableString pressedIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for a button that is in the disabled state */
  public SPOTPrintableString disabledIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for a button that is in the disabled/selected state */
  public SPOTPrintableString disabledSelectedIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: whether the button text should be shown (or used only as a tool tip) */
  public SPOTBoolean showText = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the horizontal alignment of the text */
  public CTextHAlignment textHAlignment = new CTextHAlignment(null, null, CTextHAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the vertical alignment of the text */
  public CTextVAlignment textVAlignment = new CTextVAlignment(null, null, CTextVAlignment.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Appearance: the position of the icon */
  public CIconPosition iconPosition = new CIconPosition(null, null, CIconPosition.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean scaleIcon = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: value to use when submitting data via a form */
  public CSubmitValue submitValue = new CSubmitValue(null, null, CSubmitValue.state_on_off, "state_on_off", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the rollover effect should be enabled */
  public SPOTBoolean rolloverEnabled = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the name of the group the button belongs to */
  public SPOTPrintableString groupName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: whether the button is initially selected */
  public SPOTBoolean selected = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether word wrapping is supported or text is truncated */
  public SPOTBoolean wordWrap = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: painter for when a button is pressed */
  protected GridCell pressedPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when a button is selected */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when the button is rolled over */
  protected GridCell rolloverPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when a button is disabled */
  protected GridCell disabledPainter = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Button</code> object.
   */
  public Button() {
    this(true);
  }

  /**
   * Creates a new <code>Button</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Button(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Button</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Button(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 20;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_addElement("value", value);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("selectedIcon", selectedIcon);
    selectedIcon.spot_defineAttribute("alt", null);
    selectedIcon.spot_defineAttribute("slice", null);
    selectedIcon.spot_defineAttribute("size", null);
    selectedIcon.spot_defineAttribute("scaling", null);
    selectedIcon.spot_defineAttribute("density", null);
    spot_addElement("pressedIcon", pressedIcon);
    pressedIcon.spot_defineAttribute("alt", null);
    pressedIcon.spot_defineAttribute("slice", null);
    pressedIcon.spot_defineAttribute("size", null);
    pressedIcon.spot_defineAttribute("scaling", null);
    pressedIcon.spot_defineAttribute("density", null);
    spot_addElement("disabledIcon", disabledIcon);
    disabledIcon.spot_defineAttribute("alt", null);
    disabledIcon.spot_defineAttribute("slice", null);
    disabledIcon.spot_defineAttribute("size", null);
    disabledIcon.spot_defineAttribute("scaling", null);
    disabledIcon.spot_defineAttribute("density", null);
    spot_addElement("disabledSelectedIcon", disabledSelectedIcon);
    disabledSelectedIcon.spot_defineAttribute("alt", null);
    disabledSelectedIcon.spot_defineAttribute("slice", null);
    disabledSelectedIcon.spot_defineAttribute("size", null);
    disabledSelectedIcon.spot_defineAttribute("scaling", null);
    disabledSelectedIcon.spot_defineAttribute("density", null);
    spot_addElement("showText", showText);
    spot_addElement("textHAlignment", textHAlignment);
    spot_addElement("textVAlignment", textVAlignment);
    spot_addElement("iconPosition", iconPosition);
    spot_addElement("scaleIcon", scaleIcon);
    scaleIcon.spot_defineAttribute("percent", null);
    spot_addElement("submitValue", submitValue);
    spot_addElement("rolloverEnabled", rolloverEnabled);
    spot_addElement("groupName", groupName);
    groupName.spot_defineAttribute("allowDeselection", "false");
    spot_addElement("selected", selected);
    spot_addElement("wordWrap", wordWrap);
    wordWrap.spot_defineAttribute("os", null);
    spot_addElement("pressedPainter", pressedPainter);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("rolloverPainter", rolloverPainter);
    spot_addElement("disabledPainter", disabledPainter);
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
      pressedPainter.spot_defineAttribute("foreground", null);
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
      selectionPainter.spot_defineAttribute("foreground", null);
      selectionPainter.spot_defineAttribute("foreground", null);
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

  /**
   * Gets the rolloverPainter element
   *
   * @return the rolloverPainter element or null if a reference was never created
   */
  public GridCell getRolloverPainter() {
    return rolloverPainter;
  }

  /**
   * Gets the reference to the rolloverPainter element
   * A reference is created if necessary
   *
   * @return the reference to the rolloverPainter element
   */
  public GridCell getRolloverPainterReference() {
    if (rolloverPainter == null) {
      rolloverPainter = new GridCell(true);
      super.spot_setReference("rolloverPainter", rolloverPainter);
      rolloverPainter.spot_defineAttribute("foreground", null);
    }

    return rolloverPainter;
  }

  /**
   * Sets the reference to the rolloverPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setRolloverPainter(iSPOTElement reference) throws ClassCastException {
    rolloverPainter = (GridCell) reference;
    spot_setReference("rolloverPainter", reference);
  }

  /**
   * Gets the disabledPainter element
   *
   * @return the disabledPainter element or null if a reference was never created
   */
  public GridCell getDisabledPainter() {
    return disabledPainter;
  }

  /**
   * Gets the reference to the disabledPainter element
   * A reference is created if necessary
   *
   * @return the reference to the disabledPainter element
   */
  public GridCell getDisabledPainterReference() {
    if (disabledPainter == null) {
      disabledPainter = new GridCell(true);
      super.spot_setReference("disabledPainter", disabledPainter);
      disabledPainter.spot_defineAttribute("foreground", null);
    }

    return disabledPainter;
  }

  /**
   * Sets the reference to the disabledPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setDisabledPainter(iSPOTElement reference) throws ClassCastException {
    disabledPainter = (GridCell) reference;
    spot_setReference("disabledPainter", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Button.textHAlignment</code> ENUMERATED object
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
     * the <code>Button.textHAlignment</code> ENUMERATED object
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
   * the <code>Button.textVAlignment</code> ENUMERATED object
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
     * the <code>Button.textVAlignment</code> ENUMERATED object
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
   * the <code>Button.iconPosition</code> ENUMERATED object
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
     * the <code>Button.iconPosition</code> ENUMERATED object
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
   * the <code>Button.submitValue</code> ENUMERATED object
   */
  public static class CSubmitValue extends SPOTEnumerated {

    /** submit the state of the button using on/off */
    public final static int state_on_off = 0;

    /** submit the state of the button using selected/deselected */
    public final static int state_selected_deselected = 1;

    /** submit the state of the button using 0/1 */
    public final static int state_zero_one = 2;

    /** submit the text of the button */
    public final static int text = 3;

    /** submit the custom property by the name of 'value' (html forms compability) */
    public final static int value_property = 4;

    /**
     * Creates a new <code>CSubmitValue</code> object
     */
    public CSubmitValue() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CSubmitValue</code> object
     *
     * @param val the value
     */
    public CSubmitValue(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>submitValue</code> object
     * the <code>Button.submitValue</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CSubmitValue(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "state_on_off(0), " + "state_selected_deselected(1), " + "state_zero_one(2), " + "text(3), "
             + "value_property(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "state_on_off", "state_selected_deselected", "state_zero_one", "text",
            "value_property" };
  }
  //}GENERATED_INNER_CLASSES
}
