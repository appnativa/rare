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
 * that displays and manages multiple rows and columns of data.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Table extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the column that the data will be initially sorted by when it is retrieved for the data source */
  public SPOTInteger sortedByColumn = new SPOTInteger(null, -1, null, true);
//GENERATED_COMMENT{}

  /** Behavior: the resize mode to utilized during user initiated column resizing */
  public CAutoResizeMode autoResizeMode = new CAutoResizeMode(null, null, CAutoResizeMode.resize_subsequent_columns,
                                            "resize_subsequent_columns", true);
//GENERATED_COMMENT{}

  /** Behavior: the selection mode for the table */
  public CSelectionMode selectionMode = new CSelectionMode(null, null, CSelectionMode.single, "single", true);
//GENERATED_COMMENT{}

  /** Behavior: whether column selection is allowed */
  public SPOTBoolean columnSelectionAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column sorting is allowed */
  public SPOTBoolean columnSortingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column resizing is allowed */
  public SPOTBoolean columnResizingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column reordering is allowed */
  public SPOTBoolean columnReorderingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column hiding is allowed */
  public SPOTBoolean columnHidingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether to support cursors for data items */
  public SPOTBoolean itemCursorsSupported = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior:whether the delete selection operation simply clears cells or actually removes rows */
  public SPOTBoolean deleteSelectionClearsCells = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether row spanning is allowed */
  public SPOTBoolean rowSpanningAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the selection color should change when the table looses focus (overrides the global default) */
  public SPOTBoolean changeSelColorOnLostFocus = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether events should be fired when an item is deselected */
  public SPOTBoolean deselectEventsEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether a single click will trigger an action event */
  public SPOTBoolean singleClickActionEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether column headers should be bolded */
  public SPOTBoolean boldColumnHeaders = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether to provide tracking support for dropping on a specific column (as opposed to just tracking for dropping on the row) */
  public SPOTBoolean columnDropTracking = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: the number of click it will take to start cell editing */
  public SPOTInteger clickCountToStartEditing = new SPOTInteger(null, 1, false);
//GENERATED_COMMENT{}

  /** Appearance: the type of line to use for drawing the grid */
  public CGridLineType gridLineType = new CGridLineType(null, null, CGridLineType.horizontal_lines, "horizontal_lines",
                                        false);
//GENERATED_COMMENT{}

  /** Appearance: the style of line to use for drawing the grid */
  public CGridLineStyle gridLineStyle = new CGridLineStyle(null, null, CGridLineStyle.solid, "solid", false);
//GENERATED_COMMENT{}

  /** Appearance~color: the color of the grid line */
  public SPOTPrintableString gridLineColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the height of the table header */
  public SPOTPrintableString headerHeight = new SPOTPrintableString(null, 0, 32, "-1", false);
//GENERATED_COMMENT{}

  /** Appearance~font~headerFont: the font for the item header */
  public Font headerFont = new Font();
//GENERATED_COMMENT{}

  /** Appearance~color~headerForeground: the foreground color for the header */
  public SPOTPrintableString headerFgColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~gradient~background: the background color header */
  public SPOTPrintableString headerBgColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~color~headerSeparatorColor: the color for the header column separator */
  public SPOTPrintableString headerSeparatorColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~color~headerBottomSeparatorColor: the color for the separator between the header and the table (defaults to the column separator) */
  public SPOTPrintableString headerBottomSeparatorColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for header cells (for painting purposes) */
  protected GridCell headerCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a roll over cell description for header cells (for painting purposes) */
  protected GridCell headerRolloverCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a selected cell description for header cells (for painting purposes) */
  protected GridCell headerSelectionCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: a cell description for any filler space on the right (for painting purposes) */
  protected GridCell headerRightFillerCell = null;
//GENERATED_COMMENT{}

  /** Appearance~~reload: Number of pixels to utilize as a hotspot on the column header */
  public SPOTInteger headerHotspotSize = new SPOTInteger(null, 0, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column header selections are painted when they are selected */
  public SPOTBoolean colHeaderSelectionPainted = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column header rollovers are painted */
  public SPOTBoolean colHeaderRolloverPainted = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether column header hotspots are supported */
  public SPOTBoolean colHeaderHotspotsSupported = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether row header/footer selections are painted when they are selected */
  public SPOTBoolean rowHeaderFooterSelectionPainted = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~color: the color to use for alternating row/column highlighting */
  public SPOTPrintableString alternatingHighlightColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: the type to use for alternating highlighting to perform */
  public CAlternatingHighlightType alternatingHighlightType = new CAlternatingHighlightType(null, null,
                                                                CAlternatingHighlightType.none, "none", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the sort column should be highlighted (this is independent of row/column highlighting) */
  public SPOTBoolean highlightSortColumn = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~color~runtime: the color to use for sort column highlighting */
  public SPOTPrintableString sortColumnHighlightColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Appearance: whether the table background rendering (grid, alternating colors, etc.) should be extended to fill any empty space) */
  public SPOTBoolean extendBackgroundRendering = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether table columns should be automatically sized to fit their contents */
  public SPOTBoolean autoSizeColumnsToFit = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether table rows should be automatically sized to fit their contents */
  public SPOTBoolean autoSizeRowsToFit = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether table rows should be automatically sized to fit their contents */
  public SPOTBoolean wordWrapByDefault = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether data items automatically word wrap */
  public SPOTBoolean resetColumnsOnFontChange = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Behavior: if this is a child table whether it should inherit its parent's columns */
  public SPOTBoolean inheritParentTableColumns = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: if this is a child table whether it should inherit its parent's style */
  public SPOTBoolean inheritParentTableStyle = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: if this is a child table whether its selections should be synchronized its parent's */
  public SPOTBoolean syncSelectionsWithParentTable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the height to use for each row in the table */
  public SPOTPrintableString rowHeight = new SPOTPrintableString(null, 0, 32, "1ln", false);
//GENERATED_COMMENT{}

  /** Appearance:the desired minimum number of visible rows */
  public SPOTInteger minVisibleRowCount = new SPOTInteger(null, 1, false);
//GENERATED_COMMENT{}

  /** Appearance: the desired number of visible rows */
  public SPOTInteger visibleRowCount = new SPOTInteger();
//GENERATED_COMMENT{}

  /** Behavior: what action should be taken when the enter key is pressed */
  public CEnterKeyAction enterKeyAction = new CEnterKeyAction(null, null, CEnterKeyAction.action_event, "action_event",
                                            false);
//GENERATED_COMMENT{}

  /** Behavior: what action should be taken when the enter key is pressed */
  public CTabKeyAction tabKeyAction = new CTabKeyAction(null, null, CTabKeyAction.next_component_unless_editable,
                                        "next_component_unless_editable", false);
//GENERATED_COMMENT{}

  /** Appearance~~runtime: the index of the row that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, -1, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget should automatically handle the selection when the widget recieves focus for the first time */
  public SPOTBoolean handleFirstFocusSelection = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: value to use when submitting data via a form */
  public CSubmitValue submitValue = new CSubmitValue(null, null, CSubmitValue.selected_value_text,
                                      "selected_value_text", false);
//GENERATED_COMMENT{}

  /** Behavior: the column to use when submitting data */
  public SPOTInteger submitColumn = new SPOTInteger(null, 0, null, 0, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the standard column header is shown */
  public SPOTBoolean showStandardColumnHeader = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the name of a selection group to tie this table's selection to */
  public SPOTPrintableString selectionGroupName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: whether auto-generated tooltips overlap existing text */
  public SPOTBoolean overlapAutoToolTips = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the widget is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Behavior: whether the column chooser should be enabled */
  public SPOTBoolean columnChooserEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~image~runtime: an overlay image to use to denote a modified item */
  public SPOTPrintableString modifiedItemImageURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the set of columns for the table */
  public SPOTSet columns = new SPOTSet("column", new ItemDescription(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Appearance~~runtime: popup menu for the widget. */
  protected SPOTSet columnPopupMenu = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when an item in the list is selected */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when an item in the list is selected but the list does not have focus */
  protected GridCell lostFocusSelectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: whether the table should be displayed as a grid view */
  public SPOTBoolean displayAsGridView = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the type of grid view when the table is configured as a grid view */
  public CGridViewType gridViewType = new CGridViewType(null, null, CGridViewType.vertical_wrap, "vertical_wrap",
                                        false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Table</code> object.
   */
  public Table() {
    this(true);
  }

  /**
   * Creates a new <code>Table</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Table(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Table</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Table(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 68;
    this.attributeSizeHint += 4;
    super.spot_setElements();
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onItemChanged", null);
    spot_defineAttribute("onItemDeleted", null);
    spot_addElement("sortedByColumn", sortedByColumn);
    sortedByColumn.spot_defineAttribute("descending", null);
    spot_addElement("autoResizeMode", autoResizeMode);
    spot_addElement("selectionMode", selectionMode);
    spot_addElement("columnSelectionAllowed", columnSelectionAllowed);
    spot_addElement("columnSortingAllowed", columnSortingAllowed);
    spot_addElement("columnResizingAllowed", columnResizingAllowed);
    spot_addElement("columnReorderingAllowed", columnReorderingAllowed);
    spot_addElement("columnHidingAllowed", columnHidingAllowed);
    spot_addElement("itemCursorsSupported", itemCursorsSupported);
    itemCursorsSupported.spot_defineAttribute("whenNonNull", "true");
    itemCursorsSupported.spot_defineAttribute("script", null);
    spot_addElement("deleteSelectionClearsCells", deleteSelectionClearsCells);
    spot_addElement("rowSpanningAllowed", rowSpanningAllowed);
    spot_addElement("changeSelColorOnLostFocus", changeSelColorOnLostFocus);
    spot_addElement("deselectEventsEnabled", deselectEventsEnabled);
    spot_addElement("singleClickActionEnabled", singleClickActionEnabled);
    spot_addElement("boldColumnHeaders", boldColumnHeaders);
    spot_addElement("columnDropTracking", columnDropTracking);
    spot_addElement("clickCountToStartEditing", clickCountToStartEditing);
    spot_addElement("gridLineType", gridLineType);
    spot_addElement("gridLineStyle", gridLineStyle);
    spot_addElement("gridLineColor", gridLineColor);
    spot_addElement("headerHeight", headerHeight);
    spot_addElement("headerFont", headerFont);
    spot_addElement("headerFgColor", headerFgColor);
    spot_addElement("headerBgColor", headerBgColor);
    headerBgColor.spot_defineAttribute("type", "linear");
    headerBgColor.spot_defineAttribute("direction", "vertical_top");
    headerBgColor.spot_defineAttribute("magnitude", null);
    headerBgColor.spot_defineAttribute("distribution", null);
    headerBgColor.spot_defineAttribute("radius", null);
    spot_addElement("headerSeparatorColor", headerSeparatorColor);
    spot_addElement("headerBottomSeparatorColor", headerBottomSeparatorColor);
    spot_addElement("headerCell", headerCell);
    spot_addElement("headerRolloverCell", headerRolloverCell);
    spot_addElement("headerSelectionCell", headerSelectionCell);
    spot_addElement("headerRightFillerCell", headerRightFillerCell);
    spot_addElement("headerHotspotSize", headerHotspotSize);
    headerHotspotSize.spot_defineAttribute("location", "right");
    headerHotspotSize.spot_defineAttribute("cursorName", "hand");
    headerHotspotSize.spot_defineAttribute("icon", null);
    headerHotspotSize.spot_defineAttribute("rolloverIcon", null);
    headerHotspotSize.spot_defineAttribute("onAction", null);
    spot_addElement("colHeaderSelectionPainted", colHeaderSelectionPainted);
    spot_addElement("colHeaderRolloverPainted", colHeaderRolloverPainted);
    spot_addElement("colHeaderHotspotsSupported", colHeaderHotspotsSupported);
    spot_addElement("rowHeaderFooterSelectionPainted", rowHeaderFooterSelectionPainted);
    spot_addElement("alternatingHighlightColor", alternatingHighlightColor);
    spot_addElement("alternatingHighlightType", alternatingHighlightType);
    spot_addElement("highlightSortColumn", highlightSortColumn);
    spot_addElement("sortColumnHighlightColor", sortColumnHighlightColor);
    spot_addElement("extendBackgroundRendering", extendBackgroundRendering);
    spot_addElement("autoSizeColumnsToFit", autoSizeColumnsToFit);
    spot_addElement("autoSizeRowsToFit", autoSizeRowsToFit);
    spot_addElement("wordWrapByDefault", wordWrapByDefault);
    spot_addElement("resetColumnsOnFontChange", resetColumnsOnFontChange);
    spot_addElement("inheritParentTableColumns", inheritParentTableColumns);
    spot_addElement("inheritParentTableStyle", inheritParentTableStyle);
    spot_addElement("syncSelectionsWithParentTable", syncSelectionsWithParentTable);
    spot_addElement("rowHeight", rowHeight);
    spot_addElement("minVisibleRowCount", minVisibleRowCount);
    spot_addElement("visibleRowCount", visibleRowCount);
    spot_addElement("enterKeyAction", enterKeyAction);
    spot_addElement("tabKeyAction", tabKeyAction);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("handleFirstFocusSelection", handleFirstFocusSelection);
    spot_addElement("submitValue", submitValue);
    spot_addElement("submitColumn", submitColumn);
    spot_addElement("showStandardColumnHeader", showStandardColumnHeader);
    spot_addElement("selectionGroupName", selectionGroupName);
    selectionGroupName.spot_defineAttribute("position", null);
    spot_addElement("overlapAutoToolTips", overlapAutoToolTips);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("columnChooserEnabled", columnChooserEnabled);
    spot_addElement("modifiedItemImageURL", modifiedItemImageURL);
    modifiedItemImageURL.spot_defineAttribute("opacity", "100%");
    modifiedItemImageURL.spot_defineAttribute("renderType", "upper_right");
    modifiedItemImageURL.spot_defineAttribute("renderSpace", "component");
    modifiedItemImageURL.spot_defineAttribute("composite", "src_over");
    modifiedItemImageURL.spot_defineAttribute("scaling", null);
    modifiedItemImageURL.spot_defineAttribute("density", null);
    modifiedItemImageURL.spot_defineAttribute("displayed", "always");
    spot_addElement("columns", columns);
    columns.spot_defineAttribute("onAction", null);
    spot_addElement("columnPopupMenu", columnPopupMenu);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("lostFocusSelectionPainter", lostFocusSelectionPainter);
    spot_addElement("displayAsGridView", displayAsGridView);
    spot_addElement("gridViewType", gridViewType);
    gridViewType.spot_defineAttribute("preferredColumnCount", null);
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
   * Gets the headerRightFillerCell element
   *
   * @return the headerRightFillerCell element or null if a reference was never created
   */
  public GridCell getHeaderRightFillerCell() {
    return headerRightFillerCell;
  }

  /**
   * Gets the reference to the headerRightFillerCell element
   * A reference is created if necessary
   *
   * @return the reference to the headerRightFillerCell element
   */
  public GridCell getHeaderRightFillerCellReference() {
    if (headerRightFillerCell == null) {
      headerRightFillerCell = new GridCell(true);
      super.spot_setReference("headerRightFillerCell", headerRightFillerCell);
    }

    return headerRightFillerCell;
  }

  /**
   * Sets the reference to the headerRightFillerCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setHeaderRightFillerCell(iSPOTElement reference) throws ClassCastException {
    headerRightFillerCell = (GridCell) reference;
    spot_setReference("headerRightFillerCell", reference);
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
   * Gets the columnPopupMenu element
   *
   * @return the columnPopupMenu element or null if a reference was never created
   */
  public SPOTSet getColumnPopupMenu() {
    return columnPopupMenu;
  }

  /**
   * Gets the reference to the columnPopupMenu element
   * A reference is created if necessary
   *
   * @return the reference to the columnPopupMenu element
   */
  public SPOTSet getColumnPopupMenuReference() {
    if (columnPopupMenu == null) {
      columnPopupMenu = new SPOTSet("menuItem", new MenuItem(), -1, -1, true);
      super.spot_setReference("columnPopupMenu", columnPopupMenu);
      columnPopupMenu.spot_defineAttribute("onConfigure", null);
    }

    return columnPopupMenu;
  }

  /**
   * Sets the reference to the columnPopupMenu element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setColumnPopupMenu(iSPOTElement reference) throws ClassCastException {
    columnPopupMenu = (SPOTSet) reference;
    spot_setReference("columnPopupMenu", reference);
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
   * Gets the lostFocusSelectionPainter element
   *
   * @return the lostFocusSelectionPainter element or null if a reference was never created
   */
  public GridCell getLostFocusSelectionPainter() {
    return lostFocusSelectionPainter;
  }

  /**
   * Gets the reference to the lostFocusSelectionPainter element
   * A reference is created if necessary
   *
   * @return the reference to the lostFocusSelectionPainter element
   */
  public GridCell getLostFocusSelectionPainterReference() {
    if (lostFocusSelectionPainter == null) {
      lostFocusSelectionPainter = new GridCell(true);
      super.spot_setReference("lostFocusSelectionPainter", lostFocusSelectionPainter);
      lostFocusSelectionPainter.spot_defineAttribute("foreground", null);
      lostFocusSelectionPainter.spot_defineAttribute("os", null);
      lostFocusSelectionPainter.spot_defineAttribute("foreground", null);
    }

    return lostFocusSelectionPainter;
  }

  /**
   * Sets the reference to the lostFocusSelectionPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setLostFocusSelectionPainter(iSPOTElement reference) throws ClassCastException {
    lostFocusSelectionPainter = (GridCell) reference;
    spot_setReference("lostFocusSelectionPainter", reference);
  }

  //}GENERATED_METHODS
  @Override
  public MenuItem createMenuItem(String value, String code) {
    return new MenuItem(value, code);
  }

  @Override
  public MenuItem createMenuItem(String value, String url, int target) {
    return new MenuItem(value, url, target);
  }

  @Override
  public MenuItem createMenuItem(String value, String url, String target) {
    return new MenuItem(value, url, target);
  }

  public ItemDescription getColumn(int index) {
    return (ItemDescription) columns.get(index);
  }

  public void setColumnTitle(int index, String title) {
    ((ItemDescription) columns.get(index)).title.setValue(title);
  }

  public void setColumnWidth(int index, String width) {
    ((ItemDescription) columns.get(index)).width.setValue(width);
  }

  public ItemDescription addColumn(ItemDescription cd) {
    if (cd != null) {
      columns.add(cd);
    }

    return cd;
  }

  public ItemDescription addColumn(String title) {
    return addColumn(new ItemDescription(title));
  }

  public ItemDescription addColumn(String title, String size) {
    return addColumn(new ItemDescription(title, size));
  }

  public ItemDescription addColumn(String title, String size, String columnName) {
    return addColumn(new ItemDescription(title, size, columnName));
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Table.autoResizeMode</code> ENUMERATED object
   */
  public static class CAutoResizeMode extends SPOTEnumerated {

    /** do not adjust column widths automatically; use a scrollbar. */
    public final static int none = 0;

    /** when a column is adjusted in the UI, adjust the next column the opposite way */
    public final static int resize_next_column = 1;

    /** during UI adjustment, change subsequent columns to preserve the total width; this is the default behavior. */
    public final static int resize_subsequent_columns = 2;

    /** during all resize operations, apply adjustments to the last column only */
    public final static int resize_last_column = 3;

    /** during all resize operations, proportionately resize all columns. */
    public final static int resize_all_columns = 4;

    /**
     * Creates a new <code>CAutoResizeMode</code> object
     */
    public CAutoResizeMode() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CAutoResizeMode</code> object
     *
     * @param val the value
     */
    public CAutoResizeMode(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>autoResizeMode</code> object
     * the <code>Table.autoResizeMode</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CAutoResizeMode(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "resize_next_column(1), " + "resize_subsequent_columns(2), "
             + "resize_last_column(3), " + "resize_all_columns(4) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 4 };
    private final static String _schoices[] = { "none", "resize_next_column", "resize_subsequent_columns",
            "resize_last_column", "resize_all_columns" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.selectionMode</code> ENUMERATED object
   */
  public static class CSelectionMode extends SPOTEnumerated {

    /** selection not allowed */
    public final static int none = 0;

    /** only a single item can be selected at a time */
    public final static int single = 1;

    /** multiple items can be selected at a time */
    public final static int multiple = 2;

    /** multiple items can be selected at a time but the items must be in a contiguous block */
    public final static int block = 3;

    /** selection not allowed but the standard selection indicator is not painted */
    public final static int invisible = 5;

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
     * the <code>Table.selectionMode</code> ENUMERATED object
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
      return "{" + "none(0), " + "single(1), " + "multiple(2), " + "block(3), " + "invisible(5) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3, 5 };
    private final static String _schoices[] = { "none", "single", "multiple", "block", "invisible" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.gridLineType</code> ENUMERATED object
   */
  public static class CGridLineType extends SPOTEnumerated {
    public final static int none             = 0;
    public final static int horizontal_lines = 1;
    public final static int vertical_lines   = 2;
    public final static int both             = 3;

    /**
     * Creates a new <code>CGridLineType</code> object
     */
    public CGridLineType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CGridLineType</code> object
     *
     * @param val the value
     */
    public CGridLineType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>gridLineType</code> object
     * the <code>Table.gridLineType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CGridLineType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "horizontal_lines(1), " + "vertical_lines(2), " + "both(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "none", "horizontal_lines", "vertical_lines", "both" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.gridLineStyle</code> ENUMERATED object
   */
  public static class CGridLineStyle extends SPOTEnumerated {
    public final static int dotted = 1;
    public final static int dashed = 2;
    public final static int solid  = 3;

    /**
     * Creates a new <code>CGridLineStyle</code> object
     */
    public CGridLineStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CGridLineStyle</code> object
     *
     * @param val the value
     */
    public CGridLineStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>gridLineStyle</code> object
     * the <code>Table.gridLineStyle</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CGridLineStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "dotted(1), " + "dashed(2), " + "solid(3) }";
    }

    private final static int    _nchoices[] = { 1, 2, 3 };
    private final static String _schoices[] = { "dotted", "dashed", "solid" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.alternatingHighlightType</code> ENUMERATED object
   */
  public static class CAlternatingHighlightType extends SPOTEnumerated {
    public final static int none   = 0;
    public final static int row    = 1;
    public final static int column = 3;

    /**
     * Creates a new <code>CAlternatingHighlightType</code> object
     */
    public CAlternatingHighlightType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CAlternatingHighlightType</code> object
     *
     * @param val the value
     */
    public CAlternatingHighlightType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>alternatingHighlightType</code> object
     * the <code>Table.alternatingHighlightType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CAlternatingHighlightType(Integer ival, String sval, Integer idefaultval, String sdefaultval,
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
      return "{" + "none(0), " + "row(1), " + "column(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 3 };
    private final static String _schoices[] = { "none", "row", "column" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.enterKeyAction</code> ENUMERATED object
   */
  public static class CEnterKeyAction extends SPOTEnumerated {

    /** no action */
    public final static int none = 0;

    /** triggers an action event */
    public final static int action_event = 1;

    /** move to the next row */
    public final static int next_row = 2;

    /** trigger and action event and then move to the next row */
    public final static int both = 3;

    /**
     * Creates a new <code>CEnterKeyAction</code> object
     */
    public CEnterKeyAction() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CEnterKeyAction</code> object
     *
     * @param val the value
     */
    public CEnterKeyAction(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>enterKeyAction</code> object
     * the <code>Table.enterKeyAction</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CEnterKeyAction(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "action_event(1), " + "next_row(2), " + "both(3) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2, 3 };
    private final static String _schoices[] = { "none", "action_event", "next_row", "both" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.tabKeyAction</code> ENUMERATED object
   */
  public static class CTabKeyAction extends SPOTEnumerated {

    /** moves to the next focusable component */
    public final static int next_component = 0;

    /** moves to the next focusable component unless the table is editable */
    public final static int next_component_unless_editable = 1;

    /** moves to the next cell */
    public final static int next_cell = 2;

    /**
     * Creates a new <code>CTabKeyAction</code> object
     */
    public CTabKeyAction() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CTabKeyAction</code> object
     *
     * @param val the value
     */
    public CTabKeyAction(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>tabKeyAction</code> object
     * the <code>Table.tabKeyAction</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CTabKeyAction(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "next_component(0), " + "next_component_unless_editable(1), " + "next_cell(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "next_component", "next_component_unless_editable", "next_cell" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.submitValue</code> ENUMERATED object
   */
  public static class CSubmitValue extends SPOTEnumerated {

    /** submit the text of the selected rows */
    public final static int selected_value_text = 0;

    /** use the actual selection value (useful for manual submission) */
    public final static int selected_value = 1;

    /** submit the linked of the selected rows */
    public final static int selected_linked_data = 2;

    /** submit the indexes of the selected rows */
    public final static int selected_index = 3;

    /** submit the text of the column identified as the submit column for all selected rows */
    public final static int selected_specific_column_value_text = 4;

    /** submit the linked data of the column identified as the submit column for all selected rows */
    public final static int selected_specific_column_linked_data = 5;

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
     * the <code>Table.submitValue</code> ENUMERATED object
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
             + "selected_index(3), " + "selected_specific_column_value_text(4), "
             + "selected_specific_column_linked_data(5) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "selected_value_text", "selected_value", "selected_linked_data", "selected_index",
      "selected_specific_column_value_text", "selected_specific_column_linked_data"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Table.gridViewType</code> ENUMERATED object
   */
  public static class CGridViewType extends SPOTEnumerated {

    /** a single row of data that wraps based on the width of the table */
    public final static int vertical_wrap = 0;

    /** a single row of data that wraps based on the height of the table */
    public final static int horizontal_wrap = 1;

    /**
     * Creates a new <code>CGridViewType</code> object
     */
    public CGridViewType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CGridViewType</code> object
     *
     * @param val the value
     */
    public CGridViewType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>gridViewType</code> object
     * the <code>Table.gridViewType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CGridViewType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "vertical_wrap(0), " + "horizontal_wrap(1) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("preferredColumnCount", null);
    }

    private final static int    _nchoices[] = { 0, 1 };
    private final static String _schoices[] = { "vertical_wrap", "horizontal_wrap" };
  }
  //}GENERATED_INNER_CLASSES
}
