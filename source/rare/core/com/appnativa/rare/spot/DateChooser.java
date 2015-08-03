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
 * allows a date and optionally a time to be selected.
 * <p>
 * Unlike a spinner this widget utilizes a calendar metaphor.
 * Multiple calendars can be displayed by choosing a
 * </p>
 * <p>
 * multiple' display type and specifying the number of columns
 * and or rows that the chooser will use.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class DateChooser extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: whether the combo box will be editable */
  public SPOTBoolean editable = new SPOTBoolean(null, false, true);
//GENERATED_COMMENT{}

  /** Appearance: a initial value to select */
  public SPOTDateTime value = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** Behavior~~minValue: minimum date that can be selected */
  public SPOTDateTime minValue = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** maximum date that can be selected */
  public SPOTDateTime maxValue = new SPOTDateTime();
//GENERATED_COMMENT{}

  /** Layout: the type of chooser */
  public CDisplayType displayType = new CDisplayType(null, null, CDisplayType.combo_box, "combo_box", false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the popup button should be shown */
  public SPOTBoolean showPopupButton = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the popup should be shown as a dialog box */
  public SPOTBoolean showPopupAsDialog = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: a painter for the popup background */
  protected GridCell popupPainter = null;
//GENERATED_COMMENT{}

  /** a format string for how the date/time should be displayed for drop downs */
  public SPOTPrintableString format = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Layout: the number of month display columns to show */
  public SPOTInteger monthDisplayCols = new SPOTInteger(null, 0, 12, 1, false);
//GENERATED_COMMENT{}

  /** Layout: the number of month display rows to show */
  public SPOTInteger monthDisplayRows = new SPOTInteger(null, 0, 12, 1, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: whether the chooser should automatically adjust the number of columns and rows based on available space */
  public SPOTBoolean autoResizeRowsColumns = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the Ok button is displayed */
  public SPOTBoolean showOkButton = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the None button is displayed */
  public SPOTBoolean showNoneButton = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the Today button is displayed */
  public SPOTBoolean showTodayButton = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether a time selection component is displayed */
  public SPOTBoolean showTime = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether month navigation buttons are displayed */
  public SPOTBoolean showNavigationButtons = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: the selection mode for the chooser */
  public CSelectionMode selectionMode = new CSelectionMode(null, null, CSelectionMode.single, "single", true);
//GENERATED_COMMENT{}

  /** Behavior: the selection type for the chooser */
  public CSelectionType selectionType = new CSelectionType(null, null, CSelectionType.all, "all", true);
//GENERATED_COMMENT{}

  /** the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString converterClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** additional value describing the information */
  public SPOTPrintableString valueContext = new SPOTPrintableString();

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DateChooser</code> object.
   */
  public DateChooser() {
    this(true);
  }

  /**
   * Creates a new <code>DateChooser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DateChooser(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>DateChooser</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DateChooser(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 21;
    this.attributeSizeHint += 3;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_addElement("editable", editable);
    spot_addElement("value", value);
    spot_addElement("minValue", minValue);
    spot_addElement("maxValue", maxValue);
    spot_addElement("displayType", displayType);
    spot_addElement("showPopupButton", showPopupButton);
    showPopupButton.spot_defineAttribute("icon", null);
    showPopupButton.spot_defineAttribute("pressedIcon", null);
    showPopupButton.spot_defineAttribute("disabledIcon", null);
    showPopupButton.spot_defineAttribute("border", null);
    showPopupButton.spot_defineAttribute("scaleIcons", null);
    showPopupButton.spot_defineAttribute("bgColor", null);
    showPopupButton.spot_defineAttribute("pressedPainter", null);
    showPopupButton.spot_defineAttribute("disabledPainter", null);
    spot_addElement("showPopupAsDialog", showPopupAsDialog);
    showPopupAsDialog.spot_defineAttribute("dialogTitle", null);
    spot_addElement("popupPainter", popupPainter);
    spot_addElement("format", format);
    spot_addElement("monthDisplayCols", monthDisplayCols);
    spot_addElement("monthDisplayRows", monthDisplayRows);
    spot_addElement("autoResizeRowsColumns", autoResizeRowsColumns);
    spot_addElement("showOkButton", showOkButton);
    spot_addElement("showNoneButton", showNoneButton);
    spot_addElement("showTodayButton", showTodayButton);
    spot_addElement("showTime", showTime);
    showTime.spot_defineAttribute("timeOnlyChooser", null);
    showTime.spot_defineAttribute("ampmFormat", "true");
    spot_addElement("showNavigationButtons", showNavigationButtons);
    spot_addElement("selectionMode", selectionMode);
    spot_addElement("selectionType", selectionType);
    spot_addElement("converterClass", converterClass);
    spot_addElement("valueContext", valueContext);
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
   * the <code>DateChooser.displayType</code> ENUMERATED object
   */
  public static class CDisplayType extends SPOTEnumerated {

    /** use a drop down (combo box style) for the chooser */
    public final static int combo_box = 0;

    /** display a single calendar as the chooser */
    public final static int single_calendar = 2;

    /** display a multiple calendar as the chooser */
    public final static int multiple_calendar = 3;

    /** display a button as the chooser */
    public final static int button = 4;

    /**
     * Creates a new <code>CDisplayType</code> object
     */
    public CDisplayType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDisplayType</code> object
     *
     * @param val the value
     */
    public CDisplayType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>displayType</code> object
     * the <code>DateChooser.displayType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDisplayType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "combo_box(0), " + "single_calendar(2), " + "multiple_calendar(3), " + "button(4) }";
    }

    private final static int    _nchoices[] = { 0, 2, 3, 4 };
    private final static String _schoices[] = { "combo_box", "single_calendar", "multiple_calendar", "button" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>DateChooser.selectionMode</code> ENUMERATED object
   */
  public static class CSelectionMode extends SPOTEnumerated {

    /** selection not allowed */
    public final static int none = 0;

    /** only a single date can be selected at a time */
    public final static int single = 1;

    /** multiple dates can be selected at a time */
    public final static int multiple = 2;

    /** multiple dates can be selected at a time but the dates must be contiguous */
    public final static int block = 3;

    /**
     * Creates a new <code>CSelectionMode</code> object
     */
    public CSelectionMode() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CSelectionMode</code> object
     *
     * @param val the value
     */
    public CSelectionMode(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>selectionMode</code> object
     * the <code>DateChooser.selectionMode</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CSelectionMode(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "single(1), " + "multiple(2), " + "block(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "none", "single", "multiple", "block" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>DateChooser.selectionType</code> ENUMERATED object
   */
  public static class CSelectionType extends SPOTEnumerated {

    /** all days are selectable */
    public final static int all = 0;

    /** only week days are selectable */
    public final static int weekdays = 1;

    /** only weekend days are selectable */
    public final static int weekend = 2;

    /**
     * Creates a new <code>CSelectionType</code> object
     */
    public CSelectionType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CSelectionType</code> object
     *
     * @param val the value
     */
    public CSelectionType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>selectionType</code> object
     * the <code>DateChooser.selectionType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CSelectionType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "all(0), " + "weekdays(1), " + "weekend(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "all", "weekdays", "weekend" };
  }
  //}GENERATED_INNER_CLASSES
}
