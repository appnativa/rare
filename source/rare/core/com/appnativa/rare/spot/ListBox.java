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
 * that manages a list of data items.
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ListBox extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: the selection mode for the list */
  public CSelectionMode selectionMode = new CSelectionMode(null, null, CSelectionMode.single, "single", true);
//GENERATED_COMMENT{}

  /** Design: an optional description of the items that will populate the list */
  protected ItemDescription itemDescription = null;
//GENERATED_COMMENT{}

  /** Behavior: the index of the row that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, -1, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the widget should automatically handle the selection when the widget recieves focus for the first time */
  public SPOTBoolean handleFirstFocusSelection = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** value to use when submitting data via a form */
  public CSubmitValue submitValue = new CSubmitValue(null, null, CSubmitValue.selected_value_text,
                                      "selected_value_text", false);
//GENERATED_COMMENT{}

  /** Appearance:the desired minimum number of visible rows */
  public SPOTInteger minVisibleRowCount = new SPOTInteger(null, 1, false);
//GENERATED_COMMENT{}

  /** Appearance:the desired number of visible rows */
  public SPOTInteger visibleRowCount = new SPOTInteger();
//GENERATED_COMMENT{}

  /** Behavior: whether the selection color should change when the table looses focus (overrides the global default) */
  public SPOTBoolean changeSelColorOnLostFocus = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~color: the color to use for alternating row/column highlighting */
  public SPOTPrintableString alternatingHighlightColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: whether events should be fired when an item is deselected */
  public SPOTBoolean deselectEventsEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior:whether to support cursors for data items */
  public SPOTBoolean itemCursorsSupported = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether list rows should be automatically sized to fit their contents */
  public SPOTBoolean autoSizeRowsToFit = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** editing mode for the list */
  public CEditingMode editingMode = new CEditingMode(null, null, CEditingMode.none, "none", false);
//GENERATED_COMMENT{}

  /** Appearance: whether a line is drawn between rows */
  public SPOTBoolean showDividerLine = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Appearance: the style of line to use for drawing the grid */
  public CDividerLineStyle dividerLineStyle = new CDividerLineStyle(null, null, CDividerLineStyle.solid, "solid",
                                                false);
//GENERATED_COMMENT{}

  /** Appearance~color: the color of the divider line */
  public SPOTPrintableString dividerLineColor = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: whether a single click will trigger an action event */
  public SPOTBoolean singleClickActionEnabled = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** whether items should be indexed (by first character) to improve filtering performance */
  public SPOTBoolean indexForFiltering = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: painter for when an index item is automatically added to an indexed list */
  protected GridCell indexSectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: the height of rows */
  public SPOTPrintableString rowHeight = new SPOTPrintableString(null, 0, 32, "1ln", true);
//GENERATED_COMMENT{}

  /** Appearance: the width of a cell */
  public SPOTPrintableString cellWidth = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Design: the name of a selection group to tie this list to */
  public SPOTPrintableString selectionGroupName = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Behavior: whether auto-generated tooltips overlap existing text */
  public SPOTBoolean overlapAutoToolTips = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the widget is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when an item in the list is selected */
  protected GridCell selectionPainter = null;
//GENERATED_COMMENT{}

  /** Appearance: painter for when an item in the list is selected but the list does not have focus */
  protected GridCell lostFocusSelectionPainter = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ListBox</code> object.
   */
  public ListBox() {
    this(true);
  }

  /**
   * Creates a new <code>ListBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ListBox(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ListBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ListBox(boolean optional, boolean setElements) {
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
    spot_defineAttribute("onItemChanged", null);
    spot_defineAttribute("onItemDeleted", null);
    spot_addElement("selectionMode", selectionMode);
    spot_addElement("itemDescription", itemDescription);
    spot_addElement("selectedIndex", selectedIndex);
    spot_addElement("handleFirstFocusSelection", handleFirstFocusSelection);
    spot_addElement("submitValue", submitValue);
    spot_addElement("minVisibleRowCount", minVisibleRowCount);
    spot_addElement("visibleRowCount", visibleRowCount);
    spot_addElement("changeSelColorOnLostFocus", changeSelColorOnLostFocus);
    spot_addElement("alternatingHighlightColor", alternatingHighlightColor);
    spot_addElement("deselectEventsEnabled", deselectEventsEnabled);
    spot_addElement("itemCursorsSupported", itemCursorsSupported);
    itemCursorsSupported.spot_defineAttribute("whenNonNull", "true");
    itemCursorsSupported.spot_defineAttribute("script", null);
    spot_addElement("autoSizeRowsToFit", autoSizeRowsToFit);
    spot_addElement("editingMode", editingMode);
    editingMode.spot_defineAttribute("alwaysCreateToolbar", null);
    editingMode.spot_defineAttribute("autoEnd", null);
    editingMode.spot_defineAttribute("allowSwiping", null);
    spot_addElement("showDividerLine", showDividerLine);
    showDividerLine.spot_defineAttribute("showLastLine", null);
    spot_addElement("dividerLineStyle", dividerLineStyle);
    spot_addElement("dividerLineColor", dividerLineColor);
    spot_addElement("singleClickActionEnabled", singleClickActionEnabled);
    spot_addElement("indexForFiltering", indexForFiltering);
    indexForFiltering.spot_defineAttribute("showIndexUI", null);
    indexForFiltering.spot_defineAttribute("useNativeIndexUI", null);
    indexForFiltering.spot_defineAttribute("addIndexSectionsToList", null);
    indexForFiltering.spot_defineAttribute("showSectionHeader", null);
    indexForFiltering.spot_defineAttribute("sectionHeaderHeight", null);
    spot_addElement("indexSectionPainter", indexSectionPainter);
    spot_addElement("rowHeight", rowHeight);
    spot_addElement("cellWidth", cellWidth);
    spot_addElement("selectionGroupName", selectionGroupName);
    selectionGroupName.spot_defineAttribute("position", null);
    spot_addElement("overlapAutoToolTips", overlapAutoToolTips);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("selectionPainter", selectionPainter);
    spot_addElement("lostFocusSelectionPainter", lostFocusSelectionPainter);
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
   * Gets the indexSectionPainter element
   *
   * @return the indexSectionPainter element or null if a reference was never created
   */
  public GridCell getIndexSectionPainter() {
    return indexSectionPainter;
  }

  /**
   * Gets the reference to the indexSectionPainter element
   * A reference is created if necessary
   *
   * @return the reference to the indexSectionPainter element
   */
  public GridCell getIndexSectionPainterReference() {
    if (indexSectionPainter == null) {
      indexSectionPainter = new GridCell(true);
      super.spot_setReference("indexSectionPainter", indexSectionPainter);
      indexSectionPainter.spot_defineAttribute("foreground", null);
      indexSectionPainter.spot_defineAttribute("font", null);
    }

    return indexSectionPainter;
  }

  /**
   * Sets the reference to the indexSectionPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setIndexSectionPainter(iSPOTElement reference) throws ClassCastException {
    indexSectionPainter = (GridCell) reference;
    spot_setReference("indexSectionPainter", reference);
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
      lostFocusSelectionPainter.spot_defineAttribute("foreground", null);
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
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ListBox.selectionMode</code> ENUMERATED object
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

    /** Behavior: only a single item can be selected at a time, items will automatically be selected as the cursor moved over them. This is only valid if use in conjunction with singleClickAction */
    public final static int single_auto = 4;

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
     * the <code>ListBox.selectionMode</code> ENUMERATED object
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
      return "{" + "none(0), " + "single(1), " + "multiple(2), " + "block(3), " + "single_auto(4), " + "invisible(5) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "none", "single", "multiple", "block", "single_auto", "invisible"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ListBox.submitValue</code> ENUMERATED object
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

    /** submit the text of the checked rows */
    public final static int checked_value_text = 4;

    /** submit the indexes of the checked rows */
    public final static int checked_index = 5;

    /** submit the linked data of the checked rows */
    public final static int checked_linked_data = 6;

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
     * the <code>ListBox.submitValue</code> ENUMERATED object
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
             + "selected_index(3), " + "checked_value_text(4), " + "checked_index(5), " + "checked_linked_data(6) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6
    };
    private final static String _schoices[] = {
      "selected_value_text", "selected_value", "selected_linked_data", "selected_index", "checked_value_text",
      "checked_index", "checked_linked_data"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ListBox.editingMode</code> ENUMERATED object
   */
  public static class CEditingMode extends SPOTEnumerated {
    public final static int none                     = 0;
    public final static int reordering               = 1;
    public final static int selection                = 2;
    public final static int deleting                 = 3;
    public final static int reordering_and_selection = 4;
    public final static int reordering_and_deleting  = 5;

    /**
     * Creates a new <code>CEditingMode</code> object
     */
    public CEditingMode() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CEditingMode</code> object
     *
     * @param val the value
     */
    public CEditingMode(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>editingMode</code> object
     * the <code>ListBox.editingMode</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CEditingMode(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "none(0), " + "reordering(1), " + "selection(2), " + "deleting(3), "
             + "reordering_and_selection(4), " + "reordering_and_deleting(5) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 3;
      spot_defineAttribute("alwaysCreateToolbar", null);
      spot_defineAttribute("autoEnd", null);
      spot_defineAttribute("allowSwiping", null);
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5
    };
    private final static String _schoices[] = {
      "none", "reordering", "selection", "deleting", "reordering_and_selection", "reordering_and_deleting"
    };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>ListBox.dividerLineStyle</code> ENUMERATED object
   */
  public static class CDividerLineStyle extends SPOTEnumerated {
    public final static int dotted = 1;
    public final static int dashed = 2;
    public final static int solid  = 3;

    /**
     * Creates a new <code>CDividerLineStyle</code> object
     */
    public CDividerLineStyle() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDividerLineStyle</code> object
     *
     * @param val the value
     */
    public CDividerLineStyle(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>dividerLineStyle</code> object
     * the <code>ListBox.dividerLineStyle</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDividerLineStyle(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
  //}GENERATED_INNER_CLASSES
}
