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
 * This class provides the base configuration for widgets. A
 * widget represents a visual component that can be utilized
 * <p>
 * to display and manipulate data items. A widget must be
 * contained within a viewer in order to be realized on the
 * screen.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Widget extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the name to use to programmatically address the widget */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~title~reload: title/prompt for the widget */
  public SPOTPrintableString title = new SPOTPrintableString(null, 0, 80, true);
//GENERATED_COMMENT{}

  /** Appearance~title~reload: where the title/prompt is to be displayed */
  public CTitleLocation titleLocation = new CTitleLocation(null, null, CTitleLocation.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Appearance~borders~border: borders for the widget */
  protected SPOTSet borders = null;
//GENERATED_COMMENT{}

  /** Layout: number of columns to span if used in a table-based layout */
  public SPOTInteger columnSpan = new SPOTInteger(null, -1, null, 1, true);
//GENERATED_COMMENT{}

  /** Layout: number of rows to span if used in a table-based layout */
  public SPOTInteger rowSpan = new SPOTInteger(null, -1, null, 1, true);
//GENERATED_COMMENT{}

  /** Layout: the vertical alignment for the widget */
  public CVerticalAlign verticalAlign = new CVerticalAlign(null, null, CVerticalAlign.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Layout: the horizontal alignment for the widget */
  public CHorizontalAlign horizontalAlign = new CHorizontalAlign(null, null, CHorizontalAlign.auto, "auto", true);
//GENERATED_COMMENT{}

  /** Appearance~font~font: the font */
  public Font font = new Font();
//GENERATED_COMMENT{}

  /** Appearance~color~foreground: the foreground color */
  public SPOTPrintableString fgColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~gradient~background: the background color (use "transparent" for transparent backgrounds ) */
  public SPOTPrintableString bgColor = new SPOTPrintableString(null, 0, 128, true);
//GENERATED_COMMENT{}

  /** Layout: the size and location of the widget (based on the layout of it's parent viewer) */
  public Rectangle bounds = new Rectangle();
//GENERATED_COMMENT{}

  /** Layout~insets: the margin between the widget's content and it's border */
  protected Margin contentPadding = null;
//GENERATED_COMMENT{}

  /** Layout~insets: the margin between the widget's border and it's cell bounds when in a form or grid */
  protected Margin cellPadding = null;
//GENERATED_COMMENT{}

  /** Behavior: whether the widget is required to have a value for form submission */
  public SPOTBoolean required = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the widget is enabled */
  public SPOTBoolean enabled = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the widget is visible */
  public SPOTBoolean visible = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget can obtain focus */
  public SPOTBoolean focusable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether a visual indicator of focus is painted */
  public SPOTBoolean focusPainted = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's is submitted when an enclosing form is submitted */
  public SPOTBoolean submitable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget allows the copying of its contents */
  public SPOTBoolean copyingAllowed = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Behavior: whether the widget allows pasting */
  public SPOTBoolean pastingAllowed = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Behavior: whether the widget allows items to be dragged */
  public SPOTBoolean draggingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget allows deleting */
  public SPOTBoolean deletingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget's should show a default context menu */
  public SPOTBoolean defaultContextMenu = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~runtime: the tool tip for the widget */
  public SPOTPrintableString tooltip = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: link to activate for an action event */
  protected Link actionLink = null;
//GENERATED_COMMENT{}

  /** Behavior: a comma separated list of events to invoke standard handlers for. When a specified event occurs the function with the name in the form of <event>_<widgetName> will be invoked */
  public SPOTPrintableString standardHandlers = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: a comma separated list of property names/events to invoke when the named property changes */
  public SPOTPrintableString propertyChangeHandlers = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: a set of keystroke names and code fragments (predefined actions can be used by prefacing the action name with 'action:') to execute for the keystroke */
  public SPOTPrintableString keystrokeMappings = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Design~url~reload: a URL to use to retrieve data for the widget */
  public SPOTPrintableString dataURL = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: drop mode tracking explicitItemImport=true means that the item must explicitly import the dataflavor being dropped for the 'on' mode to be supported by that item */
  public CDropTracking dropTracking = new CDropTracking(null, null, CDropTracking.none, "none", false);
//GENERATED_COMMENT{}

  /** Design: data flavors that this widget can import */
  protected SPOTSet importDataFlavors = null;
//GENERATED_COMMENT{}

  /** Design: data flavors that this widget can export */
  protected SPOTSet exportDataFlavors = null;
//GENERATED_COMMENT{}

  /** Design: registered actions that this widget supports */
  protected SPOTSet supportedActions = null;
//GENERATED_COMMENT{}

  /** Design: popup menu for the widget. */
  protected SPOTSet popupMenu = null;
//GENERATED_COMMENT{}

  /** Appearance~image~backgroundOverlayPainter: an background image for the widget (not all widgets support background images) */
  public SPOTPrintableString bgImageURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~image~overlayPainter: an overlay image for the widget (not all widgets support overlay images) */
  public SPOTPrintableString overlayImageURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Hidden~~reload: widget to use as an overlay for this widget */
  protected OverlayInfo overlayWidget = null;
//GENERATED_COMMENT{}

  /** accessibility information */
  protected AccessibleContext accessibleContext = null;
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the widget */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Design: the name of a cursor for the item */
  public SPOTPrintableString cursorName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** a set of custom name/value for the object. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString customProperties = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: the direction of text */
  public CTextDirection textDirection = new CTextDirection(null, null, CTextDirection.auto, "auto", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Widget</code> object.
   */
  public Widget() {
    this(true);
  }

  /**
   * Creates a new <code>Widget</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Widget(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Widget</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Widget(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 44;
    this.attributeSizeHint += 36;
    super.spot_setElements();
    spot_defineAttribute("onBlur", null);
    spot_defineAttribute("onClick", null);
    spot_defineAttribute("onConfigure", null);
    spot_defineAttribute("onCreated", null);
    spot_defineAttribute("onDblClick", null);
    spot_defineAttribute("onDrag", null);
    spot_defineAttribute("onDragEnd", null);
    spot_defineAttribute("onDragEnter", null);
    spot_defineAttribute("onDragExit", null);
    spot_defineAttribute("onDragOver", null);
    spot_defineAttribute("onDragStart", null);
    spot_defineAttribute("onDrop", null);
    spot_defineAttribute("onDropEnd", null);
    spot_defineAttribute("onError", null);
    spot_defineAttribute("onStartedLoading", null);
    spot_defineAttribute("onFinishedLoading", null);
    spot_defineAttribute("onFocus", null);
    spot_defineAttribute("onHidden", null);
    spot_defineAttribute("onKeyDown", null);
    spot_defineAttribute("onKeyPress", null);
    spot_defineAttribute("onKeyUp", null);
    spot_defineAttribute("onMouseDown", null);
    spot_defineAttribute("onMouseDragged", null);
    spot_defineAttribute("onMouseIn", null);
    spot_defineAttribute("onMouseMoved", null);
    spot_defineAttribute("onMouseOut", null);
    spot_defineAttribute("onMouseUp", null);
    spot_defineAttribute("onScroll", null);
    spot_defineAttribute("onResize", null);
    spot_defineAttribute("onShown", null);
    spot_defineAttribute("onScale", null);
    spot_defineAttribute("onFling", null);
    spot_defineAttribute("onContextMenu", null);
    spot_defineAttribute("onRotate", null);
    spot_defineAttribute("formValueFunction", null);
    spot_defineAttribute("submitable", null);
    spot_addElement("name", name);
    spot_addElement("title", title);
    spot_addElement("titleLocation", titleLocation);
    spot_addElement("borders", borders);
    spot_addElement("columnSpan", columnSpan);
    spot_addElement("rowSpan", rowSpan);
    spot_addElement("verticalAlign", verticalAlign);
    spot_addElement("horizontalAlign", horizontalAlign);
    spot_addElement("font", font);
    spot_addElement("fgColor", fgColor);
    spot_addElement("bgColor", bgColor);
    bgColor.spot_defineAttribute("type", "linear");
    bgColor.spot_defineAttribute("direction", "vertical_top");
    bgColor.spot_defineAttribute("magnitude", null);
    bgColor.spot_defineAttribute("distribution", null);
    bgColor.spot_defineAttribute("radius", null);
    spot_addElement("bounds", bounds);
    bounds.spot_defineAttribute("constraints", null);
    spot_addElement("contentPadding", contentPadding);
    spot_addElement("cellPadding", cellPadding);
    spot_addElement("required", required);
    spot_addElement("enabled", enabled);
    spot_addElement("visible", visible);
    spot_addElement("focusable", focusable);
    spot_addElement("focusPainted", focusPainted);
    spot_addElement("submitable", submitable);
    spot_addElement("copyingAllowed", copyingAllowed);
    spot_addElement("pastingAllowed", pastingAllowed);
    spot_addElement("draggingAllowed", draggingAllowed);
    spot_addElement("deletingAllowed", deletingAllowed);
    spot_addElement("defaultContextMenu", defaultContextMenu);
    spot_addElement("tooltip", tooltip);
    spot_addElement("actionLink", actionLink);
    spot_addElement("standardHandlers", standardHandlers);
    spot_addElement("propertyChangeHandlers", propertyChangeHandlers);
    spot_addElement("keystrokeMappings", keystrokeMappings);
    spot_addElement("dataURL", dataURL);
    dataURL.spot_defineAttribute("mimeType", null);
    dataURL.spot_defineAttribute("deferred", null);
    dataURL.spot_defineAttribute("columnSeparator", "|");
    dataURL.spot_defineAttribute("inline", null);
    dataURL.spot_defineAttribute("ldSeparator", null);
    dataURL.spot_defineAttribute("riSeparator", null);
    dataURL.spot_defineAttribute("unescape", "false");
    dataURL.spot_defineAttribute("aggregate", null);
    dataURL.spot_defineAttribute("parser", null);
    spot_addElement("dropTracking", dropTracking);
    dropTracking.spot_defineAttribute("explicitItemImport", null);
    spot_addElement("importDataFlavors", importDataFlavors);
    spot_addElement("exportDataFlavors", exportDataFlavors);
    spot_addElement("supportedActions", supportedActions);
    spot_addElement("popupMenu", popupMenu);
    spot_addElement("bgImageURL", bgImageURL);
    bgImageURL.spot_defineAttribute("opacity", "100%");
    bgImageURL.spot_defineAttribute("renderType", "tiled");
    bgImageURL.spot_defineAttribute("waitForLoad", "false");
    bgImageURL.spot_defineAttribute("renderSpace", "within_border");
    bgImageURL.spot_defineAttribute("composite", "src_over");
    bgImageURL.spot_defineAttribute("scaling", null);
    bgImageURL.spot_defineAttribute("density", null);
    spot_addElement("overlayImageURL", overlayImageURL);
    overlayImageURL.spot_defineAttribute("opacity", "10%");
    overlayImageURL.spot_defineAttribute("renderType", "tiled");
    overlayImageURL.spot_defineAttribute("waitForLoad", "false");
    overlayImageURL.spot_defineAttribute("renderSpace", "within_border");
    overlayImageURL.spot_defineAttribute("composite", "src_over");
    overlayImageURL.spot_defineAttribute("scaling", null);
    overlayImageURL.spot_defineAttribute("displayed", "always");
    overlayImageURL.spot_defineAttribute("density", null);
    spot_addElement("overlayWidget", overlayWidget);
    spot_addElement("accessibleContext", accessibleContext);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
    spot_addElement("cursorName", cursorName);
    spot_addElement("customProperties", customProperties);
    customProperties.spot_defineAttribute("mimeType", null);
    customProperties.spot_defineAttribute("delimiter", null);
    spot_addElement("textDirection", textDirection);
  }

  /**
   * Gets the borders element
   *
   * @return the borders element or null if a reference was never created
   */
  public SPOTSet getBorders() {
    return borders;
  }

  /**
   * Gets the reference to the borders element
   * A reference is created if necessary
   *
   * @return the reference to the borders element
   */
  public SPOTSet getBordersReference() {
    if (borders == null) {
      borders = new SPOTSet("border", new CBorder(null, null, CBorder.standard, "standard", true), -1, -1, true);
      super.spot_setReference("borders", borders);
    }

    return borders;
  }

  /**
   * Sets the reference to the borders element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setBorders(iSPOTElement reference) throws ClassCastException {
    borders = (SPOTSet) reference;
    spot_setReference("borders", reference);
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

  /**
   * Gets the cellPadding element
   *
   * @return the cellPadding element or null if a reference was never created
   */
  public Margin getCellPadding() {
    return cellPadding;
  }

  /**
   * Gets the reference to the cellPadding element
   * A reference is created if necessary
   *
   * @return the reference to the cellPadding element
   */
  public Margin getCellPaddingReference() {
    if (cellPadding == null) {
      cellPadding = new Margin(true);
      super.spot_setReference("cellPadding", cellPadding);
    }

    return cellPadding;
  }

  /**
   * Sets the reference to the cellPadding element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setCellPadding(iSPOTElement reference) throws ClassCastException {
    cellPadding = (Margin) reference;
    spot_setReference("cellPadding", reference);
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
   * Gets the importDataFlavors element
   *
   * @return the importDataFlavors element or null if a reference was never created
   */
  public SPOTSet getImportDataFlavors() {
    return importDataFlavors;
  }

  /**
   * Gets the reference to the importDataFlavors element
   * A reference is created if necessary
   *
   * @return the reference to the importDataFlavors element
   */
  public SPOTSet getImportDataFlavorsReference() {
    if (importDataFlavors == null) {
      importDataFlavors = new SPOTSet("flavor", new SPOTPrintableString(null, 0, 255, false), -1, -1, true);
      super.spot_setReference("importDataFlavors", importDataFlavors);
    }

    return importDataFlavors;
  }

  /**
   * Sets the reference to the importDataFlavors element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setImportDataFlavors(iSPOTElement reference) throws ClassCastException {
    importDataFlavors = (SPOTSet) reference;
    spot_setReference("importDataFlavors", reference);
  }

  /**
   * Gets the exportDataFlavors element
   *
   * @return the exportDataFlavors element or null if a reference was never created
   */
  public SPOTSet getExportDataFlavors() {
    return exportDataFlavors;
  }

  /**
   * Gets the reference to the exportDataFlavors element
   * A reference is created if necessary
   *
   * @return the reference to the exportDataFlavors element
   */
  public SPOTSet getExportDataFlavorsReference() {
    if (exportDataFlavors == null) {
      exportDataFlavors = new SPOTSet("flavor", new SPOTPrintableString(null, 0, 255, false), -1, -1, true);
      super.spot_setReference("exportDataFlavors", exportDataFlavors);
    }

    return exportDataFlavors;
  }

  /**
   * Sets the reference to the exportDataFlavors element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setExportDataFlavors(iSPOTElement reference) throws ClassCastException {
    exportDataFlavors = (SPOTSet) reference;
    spot_setReference("exportDataFlavors", reference);
  }

  /**
   * Gets the supportedActions element
   *
   * @return the supportedActions element or null if a reference was never created
   */
  public SPOTSet getSupportedActions() {
    return supportedActions;
  }

  /**
   * Gets the reference to the supportedActions element
   * A reference is created if necessary
   *
   * @return the reference to the supportedActions element
   */
  public SPOTSet getSupportedActionsReference() {
    if (supportedActions == null) {
      supportedActions = new SPOTSet("action", new SPOTPrintableString(null, 0, 64, false), -1, -1, true);
      super.spot_setReference("supportedActions", supportedActions);
    }

    return supportedActions;
  }

  /**
   * Sets the reference to the supportedActions element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSupportedActions(iSPOTElement reference) throws ClassCastException {
    supportedActions = (SPOTSet) reference;
    spot_setReference("supportedActions", reference);
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
   * Gets the overlayWidget element
   *
   * @return the overlayWidget element or null if a reference was never created
   */
  public OverlayInfo getOverlayWidget() {
    return overlayWidget;
  }

  /**
   * Gets the reference to the overlayWidget element
   * A reference is created if necessary
   *
   * @return the reference to the overlayWidget element
   */
  public OverlayInfo getOverlayWidgetReference() {
    if (overlayWidget == null) {
      overlayWidget = new OverlayInfo(true);
      super.spot_setReference("overlayWidget", overlayWidget);
    }

    return overlayWidget;
  }

  /**
   * Sets the reference to the overlayWidget element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setOverlayWidget(iSPOTElement reference) throws ClassCastException {
    overlayWidget = (OverlayInfo) reference;
    spot_setReference("overlayWidget", reference);
  }

  /**
   * Gets the accessibleContext element
   *
   * @return the accessibleContext element or null if a reference was never created
   */
  public AccessibleContext getAccessibleContext() {
    return accessibleContext;
  }

  /**
   * Gets the reference to the accessibleContext element
   * A reference is created if necessary
   *
   * @return the reference to the accessibleContext element
   */
  public AccessibleContext getAccessibleContextReference() {
    if (accessibleContext == null) {
      accessibleContext = new AccessibleContext(true);
      super.spot_setReference("accessibleContext", accessibleContext);
    }

    return accessibleContext;
  }

  /**
   * Sets the reference to the accessibleContext element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setAccessibleContext(iSPOTElement reference) throws ClassCastException {
    accessibleContext = (AccessibleContext) reference;
    spot_setReference("accessibleContext", reference);
  }

  //}GENERATED_METHODS
  public void setBounds(String x, String y, String width, String height) {
    bounds.x.setValue(x);
    bounds.y.setValue(y);
    this.bounds.width.setValue(width);
    this.bounds.height.setValue(height);
  }

  public void setLocation(int x, int y) {
    this.bounds.x.setValue(x);
    this.bounds.y.setValue(y);
  }

  public void setSize(String width, String height) {
    this.bounds.width.setValue(width);
    this.bounds.height.setValue(height);
  }

  public CBorder setBorder(int border) {
    CBorder e = new CBorder(border);

    getBordersReference().clear();
    getBordersReference().add(e);

    return e;
  }

  public CBorder setBorder(String border) {
    CBorder e = new CBorder();

    e.setValue(border);
    getBordersReference().clear();
    getBordersReference().add(e);

    return e;
  }

  public CBorder getBorder() {
    if ((borders == null) || (borders.getCount() == 0)) {
      SPOTEnumerated e = new CBorder(CBorder.standard);

      getBordersReference().add(e);
    }

    return (CBorder) borders.get(0);
  }

  public void setBorderAttribute(String name, String value) {
    if ((borders == null) || (borders.getCount() == 0)) {
      SPOTEnumerated e = new CBorder(CBorder.standard);

      getBordersReference().add(e);
    }

    borders.getEx(0).spot_setAttribute(name, value);
  }

  public void setTitle(String title) {
    this.title.setValue(title);
  }

  public void setDataURL(String url) {
    this.dataURL.setValue(url);
  }

  public void setContentPadding(int top, int left, int bottom, int right) {
    getContentPaddingReference().setValues(top, left, bottom, right);
  }

  public void setName(String name) {
    this.name.setValue(name);
  }

  public void setVerticalAlignment(int align) {
    this.verticalAlign.setValue(align);
  }

  public void setStandardHandlers(String events) {
    this.standardHandlers.setValue(events);
  }

  public void setEventHandler(String event, String code) {
    this.spot_setAttribute(event, code);
  }

  public void setHorizontalAlignment(int align) {
    this.horizontalAlign.setValue(align);
  }

  public void setColumnSpan(int span) {
    this.columnSpan.setValue(span);
  }

  public void setRowSpan(int span) {
    this.rowSpan.setValue(span);
  }

  public MenuItem createMenuItem(String value, String code) {
    return new MenuItem(value, code);
  }

  public MenuItem createMenuItem(String value, String url, int target) {
    return new MenuItem(value, url, target);
  }

  public MenuItem createMenuItem(String value, String url, String target) {
    return new MenuItem(value, url, target);
  }

  public DataItem createDataItem(String value) {
    return new DataItem(value);
  }

  public DataItem createDataItem(String value, String data) {
    return new DataItem(value, data);
  }

  public DataItem createDataItem(String value, String data, String icon) {
    return new DataItem(value, data, icon);
  }

  public void copy(SPOTSequence s) {
    this.spot_copy(s);
  }

  /**
   *  Finds an returns the named widget (does not recurse)
   * 
   *  @param name the name of the widget to find
   *  @param useNameMap true to use a name map to improve furture search performance; false otherwise
   *
   *  @return the named widget or null
   */
  public Widget findWidget(String name, boolean useNameMap) {
    return null;
  }

  /**
   *  Finds an returns the named widget (does not recurse)
   *  Do
   * 
   *  @param name the name of the widget to find
   *  @return the named widget or null
   */
  public Widget findWidget(String name) {
    return findWidget(name, false);
  }

  /**
   * Creates a map of the named widgets
   *
   * @return  a map of the named widgets or null
   */
  public java.util.Map<String, Widget> createWidgetMap() {
    return null;
  }

  public static SPOTSet getBordersSet() {
    return new SPOTSet("border", new CBorder(null, null, CBorder.standard, "standard", true), -1, -1, true);
  }

  public static int getBorderType(String name) {
    CBorder bt = new CBorder();

    return bt.getChoiceIndexByName(name);
  }

  public CBorder addBorder(int border) {
    CBorder e = new CBorder(border);

    getBordersReference().add(e);

    return e;
  }

  public CBorder addBorder(String border) {
    CBorder e = new CBorder();

    e.setValue(border);
    getBordersReference().add(e);

    return e;
  }

  public java.util.Map<String, String> getSPOTAttributes() {
    return spot_getAttributesEx();
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Widget.titleLocation</code> ENUMERATED object
   */
  public static class CTitleLocation extends SPOTEnumerated {
    public final static int not_displayed        = 0;
    public final static int auto                 = 1;
    public final static int top_left             = 2;
    public final static int top_right            = 3;
    public final static int top_center           = 4;
    public final static int bottom_left          = 5;
    public final static int bottom_center        = 6;
    public final static int bottom_right         = 7;
    public final static int frame_top_left       = 8;
    public final static int frame_top_center     = 9;
    public final static int frame_top_right      = 10;
    public final static int frame_bottom_left    = 11;
    public final static int frame_bottom_center  = 12;
    public final static int frame_bottom_right   = 13;
    public final static int inside_top_left      = 14;
    public final static int inside_top_right     = 15;
    public final static int inside_top_center    = 16;
    public final static int inside_bottom_left   = 17;
    public final static int inside_bottom_center = 18;
    public final static int inside_bottom_right  = 19;
    public final static int center_left          = 20;
    public final static int collapsible_frame    = 21;

    /**
     * Creates a new <code>CTitleLocation</code> object
     */
    public CTitleLocation() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTitleLocation</code> object
     *
     * @param val the value
     */
    public CTitleLocation(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>titleLocation</code> object
     * the <code>Widget.titleLocation</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTitleLocation(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "not_displayed(0), " + "auto(1), " + "top_left(2), " + "top_right(3), " + "top_center(4), "
             + "bottom_left(5), " + "bottom_center(6), " + "bottom_right(7), " + "frame_top_left(8), "
             + "frame_top_center(9), " + "frame_top_right(10), " + "frame_bottom_left(11), "
             + "frame_bottom_center(12), " + "frame_bottom_right(13), " + "inside_top_left(14), "
             + "inside_top_right(15), " + "inside_top_center(16), " + "inside_bottom_left(17), "
             + "inside_bottom_center(18), " + "inside_bottom_right(19), " + "center_left(20), "
             + "collapsible_frame(21) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21
    };
    private final static String _schoices[] = {
      "not_displayed", "auto", "top_left", "top_right", "top_center", "bottom_left", "bottom_center", "bottom_right",
      "frame_top_left", "frame_top_center", "frame_top_right", "frame_bottom_left", "frame_bottom_center",
      "frame_bottom_right", "inside_top_left", "inside_top_right", "inside_top_center", "inside_bottom_left",
      "inside_bottom_center", "inside_bottom_right", "center_left", "collapsible_frame"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>borders.border</code> ENUMERATED object
   */
  public static class CBorder extends SPOTEnumerated {
    public final static int none           = 0;
    public final static int standard       = 1;
    public final static int line           = 2;
    public final static int empty          = 3;
    public final static int raised         = 4;
    public final static int lowered        = 5;
    public final static int bevel_raised   = 6;
    public final static int bevel_lowered  = 7;
    public final static int etched_raised  = 8;
    public final static int etched_lowered = 9;
    public final static int frame_raised   = 10;
    public final static int frame_lowered  = 11;
    public final static int drop_shadow    = 12;
    public final static int shadow         = 13;
    public final static int group_box      = 14;
    public final static int icon           = 15;
    public final static int matte          = 16;
    public final static int back           = 17;
    public final static int balloon        = 18;
    public final static int titled         = 19;
    public final static int custom         = 31;

    /**
     * Creates a new <code>CBorder</code> object
     */
    public CBorder() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CBorder</code> object
     *
     * @param val the value
     */
    public CBorder(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>border</code> object
     * the <code>borders.border</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CBorder(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "standard(1), " + "line(2), " + "empty(3), " + "raised(4), " + "lowered(5), "
             + "bevel_raised(6), " + "bevel_lowered(7), " + "etched_raised(8), " + "etched_lowered(9), "
             + "frame_raised(10), " + "frame_lowered(11), " + "drop_shadow(12), " + "shadow(13), " + "group_box(14), "
             + "icon(15), " + "matte(16), " + "back(17), " + "balloon(18), " + "titled(19), " + "custom(31) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 21;
      spot_defineAttribute("color", null);
      spot_defineAttribute("thickness", null);
      spot_defineAttribute("style", null);
      spot_defineAttribute("cornerArc", null);
      spot_defineAttribute("padForArc", "true");
      spot_defineAttribute("insets", null);
      spot_defineAttribute("title", null);
      spot_defineAttribute("titleLocation", null);
      spot_defineAttribute("titleFont", null);
      spot_defineAttribute("titleColor", null);
      spot_defineAttribute("icon", null);
      spot_defineAttribute("flatTop", null);
      spot_defineAttribute("flatBottom", null);
      spot_defineAttribute("noBottom", null);
      spot_defineAttribute("noTop", null);
      spot_defineAttribute("noLeft", null);
      spot_defineAttribute("noRight", null);
      spot_defineAttribute("class", null);
      spot_defineAttribute("renderType", null);
      spot_defineAttribute("opacity", null);
      spot_defineAttribute("customProperties", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 31
    };
    private final static String _schoices[] = {
      "none", "standard", "line", "empty", "raised", "lowered", "bevel_raised", "bevel_lowered", "etched_raised",
      "etched_lowered", "frame_raised", "frame_lowered", "drop_shadow", "shadow", "group_box", "icon", "matte", "back",
      "balloon", "titled", "custom"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Widget.verticalAlign</code> ENUMERATED object
   */
  public static class CVerticalAlign extends SPOTEnumerated {

    /** let the container decide */
    public final static int auto = 0;

    /** center vertically */
    public final static int center = 1;

    /** align on top */
    public final static int top = 2;

    /** align on the bottom */
    public final static int bottom = 3;

    /** full justify */
    public final static int full = 4;

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
     * the <code>Widget.verticalAlign</code> ENUMERATED object
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
      return "{" + "auto(0), " + "center(1), " + "top(2), " + "bottom(3), " + "full(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "auto", "center", "top", "bottom", "full" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Widget.horizontalAlign</code> ENUMERATED object
   */
  public static class CHorizontalAlign extends SPOTEnumerated {

    /** let the container decide */
    public final static int auto = 0;

    /** center horizontally */
    public final static int center = 1;

    /** align left */
    public final static int left = 2;

    /** align right */
    public final static int right = 3;

    /** full justify */
    public final static int full = 4;

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
     * the <code>Widget.horizontalAlign</code> ENUMERATED object
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
      return "{" + "auto(0), " + "center(1), " + "left(2), " + "right(3), " + "full(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "auto", "center", "left", "right", "full" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Widget.dropTracking</code> ENUMERATED object
   */
  public static class CDropTracking extends SPOTEnumerated {

    /** drop support not enabled */
    public final static int none = 0;

    /** drop support will be configured based on system defaults */
    public final static int auto = 1;

    /** track for dropping on an item */
    public final static int on_item = 2;

    /** track for inserting an item */
    public final static int insert_item = 3;

    /** track for dropping on and item or inserting an item */
    public final static int on_or_insert = 4;

    /**
     * Creates a new <code>CDropTracking</code> object
     */
    public CDropTracking() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDropTracking</code> object
     *
     * @param val the value
     */
    public CDropTracking(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>dropTracking</code> object
     * the <code>Widget.dropTracking</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDropTracking(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "auto(1), " + "on_item(2), " + "insert_item(3), " + "on_or_insert(4) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("explicitItemImport", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "none", "auto", "on_item", "insert_item", "on_or_insert" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Widget.textDirection</code> ENUMERATED object
   */
  public static class CTextDirection extends SPOTEnumerated {
    public final static int auto = 0;
    public final static int ltr  = 1;
    public final static int rtl  = 2;

    /**
     * Creates a new <code>CTextDirection</code> object
     */
    public CTextDirection() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTextDirection</code> object
     *
     * @param val the value
     */
    public CTextDirection(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>textDirection</code> object
     * the <code>Widget.textDirection</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTextDirection(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "auto(0), " + "ltr(1), " + "rtl(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "auto", "ltr", "rtl" };
  }
  //}GENERATED_INNER_CLASSES
}
