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

//USER_IMPORTS_AND_COMMENTS_MARK{}
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.Utils;
import com.appnativa.spot.*;

//GENERATED_COMMENT{}

/**
 * This class represents the configuration information of a
 * structure for describing items
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ItemDescription extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the display name for the item */
  public SPOTPrintableString title = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the name to use to reference the item */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: the description of the item */
  public SPOTPrintableString description = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the category for items */
  public SPOTPrintableString category = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Design: the type of values items represent */
  public CValueType valueType = new CValueType(null, null, CValueType.string_type, "string_type", false);
//GENERATED_COMMENT{}

  /** Design: the class name for a custom type */
  public SPOTPrintableString customTypeClass = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance: an optional value for the item (context specific) */
  public SPOTPrintableString value = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: additional value describing a value (e.g. date format for dates) */
  public SPOTPrintableString valueContext = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: data linked with the value */
  public SPOTPrintableString linkedData = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design: additional value describing the linked data */
  public SPOTPrintableString linkedDataContext = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Appearance: how icons are positioned */
  public CIconPosition iconPosition = new CIconPosition(null, null, CIconPosition.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean scaleIcon = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Layout: vertical alignment */
  public CVerticalAlign verticalAlign = new CVerticalAlign(null, null, CVerticalAlign.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Layout: horizontal alignment */
  public CHorizontalAlign horizontalAlign = new CHorizontalAlign(null, null, CHorizontalAlign.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Appearance: how items should be rendered */
  public CRenderType renderType = new CRenderType(null, null, CRenderType.normal, "normal", false);
//GENERATED_COMMENT{}

  /** Appearance: the amount of detail to render about for an item */
  public CRenderDetail renderDetail = new CRenderDetail(null, null, CRenderDetail.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Design: the width for items(zero specifies a hidden column) */
  public SPOTPrintableString width = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Design: whether items are editable */
  public SPOTBoolean editable = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether items are visible */
  public SPOTBoolean visible = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether items can be hidden */
  public SPOTBoolean hideable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether items are sort-able (if the containing widget allows item sorting) */
  public SPOTBoolean sortable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether items are movable (if the containing widget allows item reordering) */
  public SPOTBoolean moveable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether items are showable */
  public SPOTBoolean showable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether word wrapping is supported or text is truncated in the c */
  public SPOTBoolean wordWrap = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the item's background color will override the selection background color */
  public SPOTBoolean overrideSelectionBackground = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: the programmatic class that can render the items */
  public SPOTPrintableString rendererClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString converterClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Other: the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString linkedDataconverterClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the programmatic class that can edit the items */
  public SPOTPrintableString editorClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design~widget: a widget to use to edit items */
  public SPOTAny editorWidget = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Appearance~font: the font for items */
  public Font font = new Font();
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for the item */
  protected GridCell gridCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for the item when it is selected */
  protected GridCell selectionGridCell = null;
//GENERATED_COMMENT{}

  /** Appearance~color: the foreground color for items */
  public SPOTPrintableString fgColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Design: link to activate when an item is double-clicked */
  protected Link actionLink = null;
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve the icon for items */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the name of a cursor for the item */
  public SPOTPrintableString cursorName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: whether word wrapping is supported or text is truncated in the header */
  public SPOTBoolean headerWordWrap = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~font: the font for the item header */
  public Font headerFont = new Font();
//GENERATED_COMMENT{}

  /** Appearance~icon: url to retrieve the icon to display on the item header */
  public SPOTPrintableString headerIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~color: the foreground color for the header */
  public SPOTPrintableString headerColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for the header */
  protected GridCell headerCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for the header rollover effect */
  protected GridCell headerRolloverCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for the header selected effect */
  protected GridCell headerSelectionCell = null;
//GENERATED_COMMENT{}

  /** Layout: the header's vertical alignment */
  public CHeaderHorizontalAlign headerHorizontalAlign = new CHeaderHorizontalAlign(null, null,
                                                          CHeaderHorizontalAlign.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Layout: the header's vertical alignment */
  public CHeaderVerticalAlign headerVerticalAlign = new CHeaderVerticalAlign(null, null, CHeaderVerticalAlign.auto,
                                                      "auto", true);
//GENERATED_COMMENT{}

  /** Layout: the position of the header's icon */
  public CHeaderIconPosition headerIconPosition = new CHeaderIconPosition(null, null, CHeaderIconPosition.auto, "auto",
                                                    false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean headerScaleIcon = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: how to scale the widget's icon */
  public COrientation orientation = new COrientation(null, null, COrientation.auto, "auto", false);
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the item */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** popup menu for items */
  protected SPOTSet popupMenu = null;
//GENERATED_COMMENT{}

  /** sub item description for a struct item type */
  protected SPOTSet subItems = null;
//GENERATED_COMMENT{}

  /** a set of custom name/value for the object. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString customProperties = new SPOTPrintableString();

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ItemDescription</code> object.
   */
  public ItemDescription() {
    this(true);
  }

  /**
   * Creates a new <code>ItemDescription</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ItemDescription(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ItemDescription</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ItemDescription(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 53;
    super.spot_setElements();
    spot_addElement("title", title);
    spot_addElement("name", name);
    spot_addElement("description", description);
    spot_addElement("category", category);
    spot_addElement("valueType", valueType);
    spot_addElement("customTypeClass", customTypeClass);
    spot_addElement("value", value);
    spot_addElement("valueContext", valueContext);
    valueContext.spot_defineAttribute("maximum", null);
    valueContext.spot_defineAttribute("minimum", null);
    spot_addElement("linkedData", linkedData);
    spot_addElement("linkedDataContext", linkedDataContext);
    spot_addElement("iconPosition", iconPosition);
    spot_addElement("scaleIcon", scaleIcon);
    scaleIcon.spot_defineAttribute("percent", null);
    spot_addElement("verticalAlign", verticalAlign);
    spot_addElement("horizontalAlign", horizontalAlign);
    spot_addElement("renderType", renderType);
    spot_addElement("renderDetail", renderDetail);
    spot_addElement("width", width);
    width.spot_defineAttribute("min", null);
    width.spot_defineAttribute("max", null);
    spot_addElement("editable", editable);
    spot_addElement("visible", visible);
    spot_addElement("hideable", hideable);
    spot_addElement("sortable", sortable);
    spot_addElement("moveable", moveable);
    spot_addElement("showable", showable);
    spot_addElement("wordWrap", wordWrap);
    wordWrap.spot_defineAttribute("supportHTML", null);
    spot_addElement("overrideSelectionBackground", overrideSelectionBackground);
    spot_addElement("rendererClass", rendererClass);
    rendererClass.spot_defineAttribute("options", null);
    spot_addElement("converterClass", converterClass);
    spot_addElement("linkedDataconverterClass", linkedDataconverterClass);
    spot_addElement("editorClass", editorClass);
    spot_addElement("editorWidget", editorWidget);
    spot_addElement("font", font);
    spot_addElement("gridCell", gridCell);
    spot_addElement("selectionGridCell", selectionGridCell);
    spot_addElement("fgColor", fgColor);
    spot_addElement("actionLink", actionLink);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("cursorName", cursorName);
    cursorName.spot_defineAttribute("cursorDisplayType", null);
    spot_addElement("headerWordWrap", headerWordWrap);
    headerWordWrap.spot_defineAttribute("supportHTML", null);
    spot_addElement("headerFont", headerFont);
    spot_addElement("headerIcon", headerIcon);
    headerIcon.spot_defineAttribute("alt", null);
    headerIcon.spot_defineAttribute("slice", null);
    headerIcon.spot_defineAttribute("size", null);
    headerIcon.spot_defineAttribute("scaling", null);
    headerIcon.spot_defineAttribute("density", null);
    spot_addElement("headerColor", headerColor);
    spot_addElement("headerCell", headerCell);
    spot_addElement("headerRolloverCell", headerRolloverCell);
    spot_addElement("headerSelectionCell", headerSelectionCell);
    spot_addElement("headerHorizontalAlign", headerHorizontalAlign);
    spot_addElement("headerVerticalAlign", headerVerticalAlign);
    spot_addElement("headerIconPosition", headerIconPosition);
    spot_addElement("headerScaleIcon", headerScaleIcon);
    headerScaleIcon.spot_defineAttribute("percent", null);
    spot_addElement("orientation", orientation);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
    spot_addElement("popupMenu", popupMenu);
    spot_addElement("subItems", subItems);
    spot_addElement("customProperties", customProperties);
    customProperties.spot_defineAttribute("mimeType", null);
    customProperties.spot_defineAttribute("delimiter", null);
  }

  /**
   * Gets the gridCell element
   *
   * @return the gridCell element or null if a reference was never created
   */
  public GridCell getGridCell() {
    return gridCell;
  }

  /**
   * Gets the reference to the gridCell element
   * A reference is created if necessary
   *
   * @return the reference to the gridCell element
   */
  public GridCell getGridCellReference() {
    if (gridCell == null) {
      gridCell = new GridCell(true);
      super.spot_setReference("gridCell", gridCell);
    }

    return gridCell;
  }

  /**
   * Sets the reference to the gridCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setGridCell(iSPOTElement reference) throws ClassCastException {
    gridCell = (GridCell) reference;
    spot_setReference("gridCell", reference);
  }

  /**
   * Gets the selectionGridCell element
   *
   * @return the selectionGridCell element or null if a reference was never created
   */
  public GridCell getSelectionGridCell() {
    return selectionGridCell;
  }

  /**
   * Gets the reference to the selectionGridCell element
   * A reference is created if necessary
   *
   * @return the reference to the selectionGridCell element
   */
  public GridCell getSelectionGridCellReference() {
    if (selectionGridCell == null) {
      selectionGridCell = new GridCell(true);
      super.spot_setReference("selectionGridCell", selectionGridCell);
      selectionGridCell.spot_defineAttribute("foreground", null);
    }

    return selectionGridCell;
  }

  /**
   * Sets the reference to the selectionGridCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSelectionGridCell(iSPOTElement reference) throws ClassCastException {
    selectionGridCell = (GridCell) reference;
    spot_setReference("selectionGridCell", reference);
  }

  /**
   * Gets the actionLink element
   *
   * @return the actionLink element or null if a reference was never created
   */
  public Link getActionLink() {
    return actionLink;
  }

  /**
   * Gets the reference to the actionLink element
   * A reference is created if necessary
   *
   * @return the reference to the actionLink element
   */
  public Link getActionLinkReference() {
    if (actionLink == null) {
      actionLink = new Link(true);
      super.spot_setReference("actionLink", actionLink);
    }

    return actionLink;
  }

  /**
   * Sets the reference to the actionLink element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setActionLink(iSPOTElement reference) throws ClassCastException {
    actionLink = (Link) reference;
    spot_setReference("actionLink", reference);
  }

  /**
   * Gets the headerCell element
   *
   * @return the headerCell element or null if a reference was never created
   */
  public GridCell getHeaderCell() {
    return headerCell;
  }

  /**
   * Gets the reference to the headerCell element
   * A reference is created if necessary
   *
   * @return the reference to the headerCell element
   */
  public GridCell getHeaderCellReference() {
    if (headerCell == null) {
      headerCell = new GridCell(true);
      super.spot_setReference("headerCell", headerCell);
      headerCell.spot_defineAttribute("marginColor", null);
    }

    return headerCell;
  }

  /**
   * Sets the reference to the headerCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setHeaderCell(iSPOTElement reference) throws ClassCastException {
    headerCell = (GridCell) reference;
    spot_setReference("headerCell", reference);
  }

  /**
   * Gets the headerRolloverCell element
   *
   * @return the headerRolloverCell element or null if a reference was never created
   */
  public GridCell getHeaderRolloverCell() {
    return headerRolloverCell;
  }

  /**
   * Gets the reference to the headerRolloverCell element
   * A reference is created if necessary
   *
   * @return the reference to the headerRolloverCell element
   */
  public GridCell getHeaderRolloverCellReference() {
    if (headerRolloverCell == null) {
      headerRolloverCell = new GridCell(true);
      super.spot_setReference("headerRolloverCell", headerRolloverCell);
      headerRolloverCell.spot_defineAttribute("foreground", null);
    }

    return headerRolloverCell;
  }

  /**
   * Sets the reference to the headerRolloverCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setHeaderRolloverCell(iSPOTElement reference) throws ClassCastException {
    headerRolloverCell = (GridCell) reference;
    spot_setReference("headerRolloverCell", reference);
  }

  /**
   * Gets the headerSelectionCell element
   *
   * @return the headerSelectionCell element or null if a reference was never created
   */
  public GridCell getHeaderSelectionCell() {
    return headerSelectionCell;
  }

  /**
   * Gets the reference to the headerSelectionCell element
   * A reference is created if necessary
   *
   * @return the reference to the headerSelectionCell element
   */
  public GridCell getHeaderSelectionCellReference() {
    if (headerSelectionCell == null) {
      headerSelectionCell = new GridCell(true);
      super.spot_setReference("headerSelectionCell", headerSelectionCell);
      headerSelectionCell.spot_defineAttribute("foreground", null);
    }

    return headerSelectionCell;
  }

  /**
   * Sets the reference to the headerSelectionCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setHeaderSelectionCell(iSPOTElement reference) throws ClassCastException {
    headerSelectionCell = (GridCell) reference;
    spot_setReference("headerSelectionCell", reference);
  }

  /**
   * Gets the popupMenu element
   *
   * @return the popupMenu element or null if a reference was never created
   */
  public SPOTSet getPopupMenu() {
    return popupMenu;
  }

  /**
   * Gets the reference to the popupMenu element
   * A reference is created if necessary
   *
   * @return the reference to the popupMenu element
   */
  public SPOTSet getPopupMenuReference() {
    if (popupMenu == null) {
      popupMenu = new SPOTSet("menuItem", new MenuItem(), -1, -1, true);
      super.spot_setReference("popupMenu", popupMenu);
      popupMenu.spot_defineAttribute("cache", null);
      popupMenu.spot_defineAttribute("onAction", null);
      popupMenu.spot_defineAttribute("icon", null);
      popupMenu.spot_defineAttribute("scrollable", null);
      popupMenu.spot_defineAttribute("onConfigure", null);
    }

    return popupMenu;
  }

  /**
   * Sets the reference to the popupMenu element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPopupMenu(iSPOTElement reference) throws ClassCastException {
    popupMenu = (SPOTSet) reference;
    spot_setReference("popupMenu", reference);
  }

  /**
   * Gets the subItems element
   *
   * @return the subItems element or null if a reference was never created
   */
  public SPOTSet getSubItems() {
    return subItems;
  }

  /**
   * Gets the reference to the subItems element
   * A reference is created if necessary
   *
   * @return the reference to the subItems element
   */
  public SPOTSet getSubItemsReference() {
    if (subItems == null) {
      subItems = new SPOTSet("item", new ItemDescription(), -1, -1, true);
      super.spot_setReference("subItems", subItems);
      subItems.spot_defineAttribute("url", null);
    }

    return subItems;
  }

  /**
   * Sets the reference to the subItems element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSubItems(iSPOTElement reference) throws ClassCastException {
    subItems = (SPOTSet) reference;
    spot_setReference("subItems", reference);
  }

  //}GENERATED_METHODS
  public ItemDescription(String title) {
    this();
    this.title.setValue(title);
  }

  public ItemDescription(String title, String size) {
    this();
    this.title.setValue(title);
    this.width.setValue(size);
  }

  public ItemDescription(String title, String size, String name) {
    this();
    this.name.setValue(name);
    this.title.setValue(title);
    this.width.setValue(size);
  }

  public ItemDescription(String name, String size, String title, int type) {
    this();
    this.name.setValue(name);
    this.title.setValue(title);
    this.valueType.setValue(type);
    this.width.setValue(size);
  }

  public void setColumnName(String name) {
    this.name.setValue(name);
  }

  public void setTitle(String title) {
    this.title.setValue(title);
  }

  public void setColumnType(int type) {
    valueType.setValue(type);
  }

  public void setColumnSize(int size) {
    valueType.setValue(size);
  }

  public void setActionLink(String url, int target) {
    getActionLinkReference();
    actionLink.url.setValue(url);
    actionLink.regionName.setValue(target);
  }

  public void setActionLink(String url, String target) {
    getActionLinkReference();
    actionLink.url.setValue(url);
    actionLink.regionName.setValue(target);
  }

  public void setHeaderIcon(String icon) {
    headerIcon.setValue(icon);
  }

  public void setItemIcon(String icon) {
    this.icon.setValue(icon);
  }

  public void setValueContext(String context) {
    valueContext.setValue(context);
  }

  public MenuItem addPopupMenu(MenuItem item) {
    getPopupMenuReference();
    popupMenu.add(item);

    return item;
  }

  public MenuItem addPopupMenu(String value, String code) {
    return addPopupMenu(new MenuItem(value, code));
  }

  public MenuItem addPopupMenu(String value, String url, int target) {
    return addPopupMenu(new MenuItem(value, url, target));
  }

  public MenuItem addPopupMenu(String value, String url, String target) {
    return addPopupMenu(new MenuItem(value, url, target));
  }

  public RenderableDataItem.HorizontalAlign getHorizontalAlignment() {
    return Utils.getHorizontalAlignment(horizontalAlign.intValue());
  }

  public RenderableDataItem.HorizontalAlign getHeaderHorizontalAlignment() {
    return Utils.getHorizontalAlignment(headerHorizontalAlign.intValue());
  }

  public RenderableDataItem.VerticalAlign getVerticalAlignment() {
    return Utils.getVerticalAlignment(verticalAlign.intValue());
  }

  public RenderableDataItem.VerticalAlign getHeaderVerticalAlignment() {
    return Utils.getVerticalAlignment(headerVerticalAlign.intValue());
  }

  public RenderableDataItem.IconPosition getIconPosition() {
    return Utils.getIconPosition(iconPosition.intValue());
  }

  public RenderableDataItem.IconPosition getHeaderIconPosition() {
    return Utils.getIconPosition(headerIconPosition.intValue());
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ItemDescription.valueType</code> ENUMERATED object
   */
  public static class CValueType extends SPOTEnumerated {

    /** items represent string values */
    public final static int string_type = 1;

    /** items represent integer numbers */
    public final static int integer_type = 2;

    /** items represent decimal numbers */
    public final static int decimal_type = 3;

    /** items represent date and time values */
    public final static int date_time_type = 4;

    /** items represent date values */
    public final static int date_type = 5;

    /** items represent time values */
    public final static int time_type = 6;

    /** items represent boolean values */
    public final static int boolean_type = 7;

    /** items represent a byte array that will be base64 encoded */
    public final static int bytes_base64_type = 8;

    /** items represent items that themselves can contain other items */
    public final static int struct_type = 9;

    /** items represent an array of items (use ArrayTypeInformation to describe the array) */
    public final static int array_type = 10;

    /** items represent widgets */
    public final static int widget_type = 11;

    /** items are of a custom type */
    public final static int custom_type = 99;

    /**
     * Creates a new <code>CValueType</code> object
     */
    public CValueType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CValueType</code> object
     *
     * @param val the value
     */
    public CValueType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>valueType</code> object
     * the <code>ItemDescription.valueType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CValueType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "string_type(1), " + "integer_type(2), " + "decimal_type(3), " + "date_time_type(4), "
             + "date_type(5), " + "time_type(6), " + "boolean_type(7), " + "bytes_base64_type(8), "
             + "struct_type(9), " + "array_type(10), " + "widget_type(11), " + "custom_type(99) }";
    }

    private final static int    _nchoices[] = {
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 99
    };
    private final static String _schoices[] = {
      "string_type", "integer_type", "decimal_type", "date_time_type", "date_type", "time_type", "boolean_type",
      "bytes_base64_type", "struct_type", "array_type", "widget_type", "custom_type"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ItemDescription.iconPosition</code> ENUMERATED object
   */
  public static class CIconPosition extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** icons are positioned to the left of the text */
    public final static int left = 1;

    /** icons are positioned the right of the text */
    public final static int right = 2;

    /** icons are positioned along the leading edge */
    public final static int leading = 3;

    /** icons are positioned along the trailing edge */
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
     * the <code>ItemDescription.iconPosition</code> ENUMERATED object
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
   * the <code>ItemDescription.verticalAlign</code> ENUMERATED object
   */
  public static class CVerticalAlign extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the text is top aligned */
    public final static int top = 1;

    /** the text is bottom aligned */
    public final static int bottom = 2;

    /** the text is centered */
    public final static int center = 5;

    /**
     * Creates a new <code>CVerticalAlign</code> object
     */
    public CVerticalAlign() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CVerticalAlign</code> object
     *
     * @param val the value
     */
    public CVerticalAlign(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>verticalAlign</code> object
     * the <code>ItemDescription.verticalAlign</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CVerticalAlign(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
   * the <code>ItemDescription.horizontalAlign</code> ENUMERATED object
   */
  public static class CHorizontalAlign extends SPOTEnumerated {

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
     * Creates a new <code>CHorizontalAlign</code> object
     */
    public CHorizontalAlign() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHorizontalAlign</code> object
     *
     * @param val the value
     */
    public CHorizontalAlign(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>horizontalAlign</code> object
     * the <code>ItemDescription.horizontalAlign</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHorizontalAlign(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
   * the <code>ItemDescription.renderType</code> ENUMERATED object
   */
  public static class CRenderType extends SPOTEnumerated {

    /** as a normal item */
    public final static int normal = 0;

    /** as a header item (does not scroll horizontally) */
    public final static int header = 1;

    /** as a footer item (does not scroll horizontally) */
    public final static int footer = 2;

    /** as a header index item (will render the index number of each row (1 relative) */
    public final static int header_index = 3;

    /** as a header index item (will render the index number of each row (1 relative) */
    public final static int footer_index = 4;

    /** as a header item that visually looks like a normal item (except it does not scroll horizontally) */
    public final static int header_normal = 5;

    /** as a footer item that visually looks like a normal item (except it does not scroll horizontally) */
    public final static int footer_normal = 6;

    /** as a header index item that visually looks like a normal item (except it does not scroll horizontally) */
    public final static int header_index_normal = 7;

    /** as a footer index item that visually looks like a normal item (except it does not scroll horizontally) */
    public final static int footer_index_normal = 8;

    /** as a normal index column (will render the index number of each row (1 relative) */
    public final static int normal_index = 9;

    /**
     * Creates a new <code>CRenderType</code> object
     */
    public CRenderType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CRenderType</code> object
     *
     * @param val the value
     */
    public CRenderType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>renderType</code> object
     * the <code>ItemDescription.renderType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CRenderType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "normal(0), " + "header(1), " + "footer(2), " + "header_index(3), " + "footer_index(4), "
             + "header_normal(5), " + "footer_normal(6), " + "header_index_normal(7), " + "footer_index_normal(8), "
             + "normal_index(9) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    private final static String _schoices[] = {
      "normal", "header", "footer", "header_index", "footer_index", "header_normal", "footer_normal",
      "header_index_normal", "footer_index_normal", "normal_index"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ItemDescription.renderDetail</code> ENUMERATED object
   */
  public static class CRenderDetail extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** render every detail */
    public final static int all = 1;

    /** only render the text and the icon */
    public final static int text_and_icon = 2;

    /** only render the text */
    public final static int text_only = 3;

    /** only render the icon */
    public final static int icon_only = 4;

    /** don't render any thing */
    public final static int none = 5;

    /**
     * Creates a new <code>CRenderDetail</code> object
     */
    public CRenderDetail() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CRenderDetail</code> object
     *
     * @param val the value
     */
    public CRenderDetail(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>renderDetail</code> object
     * the <code>ItemDescription.renderDetail</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CRenderDetail(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "all(1), " + "text_and_icon(2), " + "text_only(3), " + "icon_only(4), " + "none(5) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "auto", "all", "text_and_icon", "text_only", "icon_only", "none"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ItemDescription.headerHorizontalAlign</code> ENUMERATED object
   */
  public static class CHeaderHorizontalAlign extends SPOTEnumerated {

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
     * Creates a new <code>CHeaderHorizontalAlign</code> object
     */
    public CHeaderHorizontalAlign() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHeaderHorizontalAlign</code> object
     *
     * @param val the value
     */
    public CHeaderHorizontalAlign(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>headerHorizontalAlign</code> object
     * the <code>ItemDescription.headerHorizontalAlign</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHeaderHorizontalAlign(Integer ival, String sval, Integer idefaultval, String sdefaultval,
                                  boolean optional) {
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
   * the <code>ItemDescription.headerVerticalAlign</code> ENUMERATED object
   */
  public static class CHeaderVerticalAlign extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** the text is top aligned */
    public final static int top = 1;

    /** the text is bottom aligned */
    public final static int bottom = 2;

    /** the text is centered */
    public final static int center = 5;

    /**
     * Creates a new <code>CHeaderVerticalAlign</code> object
     */
    public CHeaderVerticalAlign() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHeaderVerticalAlign</code> object
     *
     * @param val the value
     */
    public CHeaderVerticalAlign(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>headerVerticalAlign</code> object
     * the <code>ItemDescription.headerVerticalAlign</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHeaderVerticalAlign(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
   * the <code>ItemDescription.headerIconPosition</code> ENUMERATED object
   */
  public static class CHeaderIconPosition extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;

    /** icons are positioned to the left of the text */
    public final static int left = 1;

    /** icons are positioned the right of the text */
    public final static int right = 2;

    /** icons are positioned along the leading edge */
    public final static int leading = 3;

    /** icons are positioned along the trailing edge */
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
     * Creates a new <code>CHeaderIconPosition</code> object
     */
    public CHeaderIconPosition() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CHeaderIconPosition</code> object
     *
     * @param val the value
     */
    public CHeaderIconPosition(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>headerIconPosition</code> object
     * the <code>ItemDescription.headerIconPosition</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHeaderIconPosition(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
   * the <code>ItemDescription.orientation</code> ENUMERATED object
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
     * the <code>ItemDescription.orientation</code> ENUMERATED object
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