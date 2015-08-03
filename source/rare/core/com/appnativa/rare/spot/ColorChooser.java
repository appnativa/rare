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
 * This class represents the configuration for a widget that
 * allows a color to be selected from a specified palette
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ColorChooser extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: the combo box will be editable */
  public SPOTBoolean editable = new SPOTBoolean(null, true, true);
//GENERATED_COMMENT{}

  /** Appearance~color: an initial color to select */
  public SPOTPrintableString value = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Layout: the type of drop-down to use */
  public CDropDownType dropDownType = new CDropDownType(null, null, CDropDownType.combo_box, "combo_box", false);
//GENERATED_COMMENT{}

  /** Behavior: whether events should be fired when an item is deselected */
  public SPOTBoolean deselectEventsEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the popup should be shown as a dialog box */
  public SPOTBoolean showPopupAsDialog = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the Ok button is displayed */
  public SPOTBoolean showOkButton = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether a none button should be shown */
  public SPOTBoolean showNoneButton = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the hex values of the color should be displayed when using a combo box */
  public SPOTBoolean showValueAsHex = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the color palette to use */
  public CColorPalette colorPalette = new CColorPalette(null, null, CColorPalette.color_40, "color_40", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the popup buton should be shown */
  public SPOTBoolean showPopupButton = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: a painter for the popup background */
  protected GridCell popupPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the button style drop down */
  public SPOTPrintableString buttonIcon = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ColorChooser</code> object.
   */
  public ColorChooser() {
    this(true);
  }

  /**
   * Creates a new <code>ColorChooser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ColorChooser(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ColorChooser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ColorChooser(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 12;
    this.attributeSizeHint += 3;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_addElement("editable", editable);
    spot_addElement("value", value);
    spot_addElement("dropDownType", dropDownType);
    spot_addElement("deselectEventsEnabled", deselectEventsEnabled);
    spot_addElement("showPopupAsDialog", showPopupAsDialog);
    showPopupAsDialog.spot_defineAttribute("dialogTitle", null);
    spot_addElement("showOkButton", showOkButton);
    spot_addElement("showNoneButton", showNoneButton);
    spot_addElement("showValueAsHex", showValueAsHex);
    spot_addElement("colorPalette", colorPalette);
    colorPalette.spot_defineAttribute("useList", null);
    spot_addElement("showPopupButton", showPopupButton);
    showPopupButton.spot_defineAttribute("icon", null);
    showPopupButton.spot_defineAttribute("pressedIcon", null);
    showPopupButton.spot_defineAttribute("disabledIcon", null);
    showPopupButton.spot_defineAttribute("border", null);
    showPopupButton.spot_defineAttribute("scaleIcons", null);
    showPopupButton.spot_defineAttribute("bgColor", null);
    spot_addElement("popupPainter", popupPainter);
    spot_addElement("buttonIcon", buttonIcon);
    buttonIcon.spot_defineAttribute("alt", null);
    buttonIcon.spot_defineAttribute("slice", null);
    buttonIcon.spot_defineAttribute("size", null);
    buttonIcon.spot_defineAttribute("scaling", null);
    buttonIcon.spot_defineAttribute("density", null);
  }

  /**
   * Gets the popupPainter element
   *
   * @return the popupPainter element or null if a reference was never created
   */
  public GridCell getPopupPainter() {
    return popupPainter;
  }

  /**
   * Gets the reference to the popupPainter element
   * A reference is created if necessary
   *
   * @return the reference to the popupPainter element
   */
  public GridCell getPopupPainterReference() {
    if (popupPainter == null) {
      popupPainter = new GridCell(true);
      super.spot_setReference("popupPainter", popupPainter);
    }

    return popupPainter;
  }

  /**
   * Sets the reference to the popupPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPopupPainter(iSPOTElement reference) throws ClassCastException {
    popupPainter = (GridCell) reference;
    spot_setReference("popupPainter", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ColorChooser.dropDownType</code> ENUMERATED object
   */
  public static class CDropDownType extends SPOTEnumerated {

    /** don't use a drop-down, simply display the color palette */
    public final static int none = 0;

    /** use a combo box style drop down for the chooser */
    public final static int combo_box = 1;

    /** use a button with a popup down for the chooser */
    public final static int button = 2;

    /**
     * Creates a new <code>CDropDownType</code> object
     */
    public CDropDownType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDropDownType</code> object
     *
     * @param val the value
     */
    public CDropDownType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>dropDownType</code> object
     * the <code>ColorChooser.dropDownType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDropDownType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "combo_box(1), " + "button(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "none", "combo_box", "button" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ColorChooser.colorPalette</code> ENUMERATED object
   */
  public static class CColorPalette extends SPOTEnumerated {
    public final static int color_40 = 0;
    public final static int color_16 = 1;
    public final static int gray_16  = 2;
    public final static int custom   = 3;

    /**
     * Creates a new <code>CColorPalette</code> object
     */
    public CColorPalette() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CColorPalette</code> object
     *
     * @param val the value
     */
    public CColorPalette(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>colorPalette</code> object
     * the <code>ColorChooser.colorPalette</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CColorPalette(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "color_40(0), " + "color_16(1), " + "gray_16(2), " + "custom(3) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("useList", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "color_40", "color_16", "gray_16", "custom" };
  }
  //}GENERATED_INNER_CLASSES
}