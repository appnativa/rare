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
 * that manages a popup for which selections can be made.
 * <p>
 * It can also allow the user to type in a value if no of the
 * predefined values are applicable
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ComboBox extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve the button's icon */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Behavior: the combo box will be editable */
  public SPOTBoolean editable = new SPOTBoolean(null, false, true);
//GENERATED_COMMENT{}

  /** Design: the maximum number of items to display in list type popups */
  public SPOTInteger visibleRowCount = new SPOTInteger();
//GENERATED_COMMENT{}

  /** an optional description of the items that will populate the list */
  protected ItemDescription itemDescription = null;
//GENERATED_COMMENT{}

  /** Appearance: the value for an editable combobox or the value to use when a non-editable combobox is empty and no item matches the value and selectedIndex is not set */
  public SPOTPrintableString value = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: the index of the row that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, -1, false);
//GENERATED_COMMENT{}

  /** Behavior: value to use when submitting data via a form */
  public CSubmitValue submitValue = new CSubmitValue(null, null, CSubmitValue.selected_value_text,
                                      "selected_value_text", false);
//GENERATED_COMMENT{}

  /** Behavior: whether events should be fired when an item is deselected */
  public SPOTBoolean deselectEventsEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the type of popup component that the combo box will display */
  public CComponentType componentType = new CComponentType(null, null, CComponentType.listbox, "listbox", false);
//GENERATED_COMMENT{}

  /** Behavior: whether items should be indexed (by first character) to improve filtering performance */
  public SPOTBoolean indexForFiltering = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the popup is resizable */
  public SPOTBoolean popupResizable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the popup should be shown as a dialog box */
  public SPOTBoolean showPopupAsDialog = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the popup buton should be shown */
  public SPOTBoolean showPopupButton = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: a painter for the popup background */
  protected GridCell popupPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when an item in the list is selected */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** Behavior: whether the popup should use the same border as the combobox */
  public SPOTBoolean useSameBorderForPopup = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the height to use for each row in the combobox */
  public SPOTPrintableString rowHeight = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Appearance:the desired minimum number of visible rows */
  public SPOTInteger minVisibleRowCount = new SPOTInteger(null, 2, false);
//GENERATED_COMMENT{}

  /** Appearance:the desired maximim number of visible rows */
  public SPOTInteger maxVisibleRowCount = new SPOTInteger();
//GENERATED_COMMENT{}

  /** Appearance~~reload: the class name of a custom combobox derived from AbstractComboBox */
  public SPOTPrintableString customClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString converterClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** additional value describing the information */
  public SPOTPrintableString valueContext = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: a popup widget for the widget componentType */
  public SPOTAny popupWidget = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Behavior: use to customize the scroll pane for listbox componentType */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: text to show when the widget is empty */
  protected EmptyText emptyText = null;
//GENERATED_COMMENT{}

  /** Behavior: whether the speech input is supported */
  public SPOTBoolean speechInputSupported = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ComboBox</code> object.
   */
  public ComboBox() {
    this(true);
  }

  /**
   * Creates a new <code>ComboBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ComboBox(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ComboBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ComboBox(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 26;
    this.attributeSizeHint += 4;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("editable", editable);
    spot_addElement("visibleRowCount", visibleRowCount);
    spot_addElement("itemDescription", itemDescription);
    spot_addElement("value", value);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("submitValue", submitValue);
    spot_addElement("deselectEventsEnabled", deselectEventsEnabled);
    spot_addElement("componentType", componentType);
    componentType.spot_defineAttribute("buttonOnly", null);
    spot_addElement("indexForFiltering", indexForFiltering);
    spot_addElement("popupResizable", popupResizable);
    spot_addElement("showPopupAsDialog", showPopupAsDialog);
    spot_addElement("showPopupButton", showPopupButton);
    showPopupButton.spot_defineAttribute("icon", null);
    showPopupButton.spot_defineAttribute("pressedIcon", null);
    showPopupButton.spot_defineAttribute("disabledIcon", null);
    showPopupButton.spot_defineAttribute("border", null);
    showPopupButton.spot_defineAttribute("scaleIcons", null);
    showPopupButton.spot_defineAttribute("fgColor", null);
    showPopupButton.spot_defineAttribute("disabledFgColor", null);
    showPopupButton.spot_defineAttribute("bgColor", null);
    showPopupButton.spot_defineAttribute("pressedBgColor", null);
    showPopupButton.spot_defineAttribute("rolloverBgColor", null);
    showPopupButton.spot_defineAttribute("disabledBgColor", null);
    spot_addElement("popupPainter", popupPainter);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("useSameBorderForPopup", useSameBorderForPopup);
    spot_addElement("rowHeight", rowHeight);
    spot_addElement("minVisibleRowCount", minVisibleRowCount);
    spot_addElement("maxVisibleRowCount", maxVisibleRowCount);
    spot_addElement("customClass", customClass);
    spot_addElement("converterClass", converterClass);
    spot_addElement("valueContext", valueContext);
    spot_addElement("popupWidget", popupWidget);
    popupWidget.spot_defineAttribute("closeOnAction", "true");
    popupWidget.spot_defineAttribute("okWidget", null);
    popupWidget.spot_defineAttribute("cancelWidget", null);
    popupWidget.spot_defineAttribute("valueWidget", null);
    popupWidget.spot_defineAttribute("valueAttribute", null);
    popupWidget.spot_defineAttribute("horizontalAlignment", null);
    popupWidget.spot_defineAttribute("url", null);
    popupWidget.spot_defineAttribute("deferred", null);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("emptyText", emptyText);
    spot_addElement("speechInputSupported", speechInputSupported);
  }

  /**
   * Gets the itemDescription element
   *
   * @return the itemDescription element or null if a reference was never created
   */
  public ItemDescription getItemDescription() {
    return itemDescription;
  }

  /**
   * Gets the reference to the itemDescription element
   * A reference is created if necessary
   *
   * @return the reference to the itemDescription element
   */
  public ItemDescription getItemDescriptionReference() {
    if (itemDescription == null) {
      itemDescription = new ItemDescription(true);
      super.spot_setReference("itemDescription", itemDescription);
    }

    return itemDescription;
  }

  /**
   * Sets the reference to the itemDescription element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setItemDescription(iSPOTElement reference) throws ClassCastException {
    itemDescription = (ItemDescription) reference;
    spot_setReference("itemDescription", reference);
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
   * Gets the scrollPane element
   *
   * @return the scrollPane element or null if a reference was never created
   */
  public ScrollPane getScrollPane() {
    return scrollPane;
  }

  /**
   * Gets the reference to the scrollPane element
   * A reference is created if necessary
   *
   * @return the reference to the scrollPane element
   */
  public ScrollPane getScrollPaneReference() {
    if (scrollPane == null) {
      scrollPane = new ScrollPane(true);
      super.spot_setReference("scrollPane", scrollPane);
    }

    return scrollPane;
  }

  /**
   * Sets the reference to the scrollPane element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setScrollPane(iSPOTElement reference) throws ClassCastException {
    scrollPane = (ScrollPane) reference;
    spot_setReference("scrollPane", reference);
  }

  /**
   * Gets the emptyText element
   *
   * @return the emptyText element or null if a reference was never created
   */
  public EmptyText getEmptyText() {
    return emptyText;
  }

  /**
   * Gets the reference to the emptyText element
   * A reference is created if necessary
   *
   * @return the reference to the emptyText element
   */
  public EmptyText getEmptyTextReference() {
    if (emptyText == null) {
      emptyText = new EmptyText(true);
      super.spot_setReference("emptyText", emptyText);
    }

    return emptyText;
  }

  /**
   * Sets the reference to the emptyText element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setEmptyText(iSPOTElement reference) throws ClassCastException {
    emptyText = (EmptyText) reference;
    spot_setReference("emptyText", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ComboBox.submitValue</code> ENUMERATED object
   */
  public static class CSubmitValue extends SPOTEnumerated {
    public final static int selected_value_text = 0;

    /** use the actual selection value (useful for manual submission) */
    public final static int selected_value       = 1;
    public final static int selected_linked_data = 2;
    public final static int selected_index       = 3;

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
     * the <code>ComboBox.submitValue</code> ENUMERATED object
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
      return "{" + "selected_value_text(0), " + "selected_value(1), " + "selected_linked_data(2), "
             + "selected_index(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "selected_value_text", "selected_value", "selected_linked_data",
            "selected_index" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ComboBox.componentType</code> ENUMERATED object
   */
  public static class CComponentType extends SPOTEnumerated {

    /** a list box */
    public final static int listbox = 0;

    /** the widget defined in the popupWidget property */
    public final static int widget = 1;

    /** a predefined component for choosing a file */
    public final static int file_chooser = 2;

    /** a predefined component for choosing a folder */
    public final static int folder_chooser = 3;

    /** a predefined component for choosing a font */
    public final static int font_chooser = 4;

    /** a predefined component for choosing a font */
    public final static int fontname_chooser = 5;

    /** a custom combobox specified buy the custom class property */
    public final static int custom = 99;

    /**
     * Creates a new <code>CComponentType</code> object
     */
    public CComponentType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CComponentType</code> object
     *
     * @param val the value
     */
    public CComponentType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>componentType</code> object
     * the <code>ComboBox.componentType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CComponentType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "listbox(0), " + "widget(1), " + "file_chooser(2), " + "folder_chooser(3), " + "font_chooser(4), "
             + "fontname_chooser(5), " + "custom(99) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("buttonOnly", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 99
    };
    private final static String _schoices[] = {
      "listbox", "widget", "file_chooser", "folder_chooser", "font_chooser", "fontname_chooser", "custom"
    };
  }
  //}GENERATED_INNER_CLASSES
}
