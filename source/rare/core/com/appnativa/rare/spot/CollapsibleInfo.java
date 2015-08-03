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
 * This class represents the configuration information of
 * collapsible information for use with collapsible viewers
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class CollapsibleInfo extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~icon~reload: url to use to retrieve an icon associated with the value (if not defined, the icon of the widget is used) */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Behavior: whether the parent container should be initially collapsed */
  public SPOTBoolean initiallyCollapsed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: title for the collapsible pane (if not defined, the title of the widget is used) */
  public SPOTPrintableString title = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance: title to use if the parent container is collapsed */
  public SPOTPrintableString collapsedTitle = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: tooltip to use when the parent container is collapsed */
  public SPOTPrintableString collapseTip = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design:tooltip to use when the parent container is expanded */
  public SPOTPrintableString expandTip = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload: the background for the title */
  protected GridCell titleCell = null;
//GENERATED_COMMENT{}

  /** Appearance~font~reload: the font for the title */
  public Font titleFont = new Font();
//GENERATED_COMMENT{}

  /** Behavior: whether the parent container is automatically expanded on a drag-over operation */
  public SPOTBoolean expandOnDragover = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the type of expander/collapser to use */
  public CExpander expander = new CExpander(null, null, CExpander.twisty, "twisty", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the collapse/expand operations are animated using the sizing animation */
  public SPOTBoolean animateTransitions = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior:whether to toggle when the title is single clicked by default a double-click is required */
  public SPOTBoolean toggleOnTitleSingleClick = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the user can collapse/expand (other collapsing/expanding can only be done programmatically) */
  public SPOTBoolean userControllable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the title bar is shown */
  public SPOTBoolean showTitleBar = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the title bar is opaque */
  public SPOTBoolean opaqueTitleBar = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design~~reload: name of an object template to use to customize the item */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>CollapsibleInfo</code> object.
   */
  public CollapsibleInfo() {
    this(true);
  }

  /**
   * Creates a new <code>CollapsibleInfo</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public CollapsibleInfo(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>CollapsibleInfo</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected CollapsibleInfo(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 16;
    this.attributeSizeHint += 4;
    super.spot_setElements();
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_defineAttribute("onHasCollapsed", null);
    spot_defineAttribute("onHasExpanded", null);
    spot_addElement("icon", icon);
    icon.spot_defineAttribute("alt", null);
    icon.spot_defineAttribute("slice", null);
    icon.spot_defineAttribute("size", null);
    icon.spot_defineAttribute("scaling", null);
    icon.spot_defineAttribute("density", null);
    spot_addElement("initiallyCollapsed", initiallyCollapsed);
    spot_addElement("title", title);
    spot_addElement("collapsedTitle", collapsedTitle);
    spot_addElement("collapseTip", collapseTip);
    spot_addElement("expandTip", expandTip);
    spot_addElement("titleCell", titleCell);
    spot_addElement("titleFont", titleFont);
    spot_addElement("expandOnDragover", expandOnDragover);
    spot_addElement("expander", expander);
    expander.spot_defineAttribute("expandIcon", null);
    expander.spot_defineAttribute("collapseIcon", null);
    expander.spot_defineAttribute("iconOnTheLeft", null);
    spot_addElement("animateTransitions", animateTransitions);
    animateTransitions.spot_defineAttribute("duration", null);
    animateTransitions.spot_defineAttribute("acceleration", null);
    animateTransitions.spot_defineAttribute("deceleration", null);
    animateTransitions.spot_defineAttribute("diagonal", null);
    animateTransitions.spot_defineAttribute("diagonalAnchor", null);
    animateTransitions.spot_defineAttribute("fade", null);
    spot_addElement("toggleOnTitleSingleClick", toggleOnTitleSingleClick);
    spot_addElement("userControllable", userControllable);
    spot_addElement("showTitleBar", showTitleBar);
    spot_addElement("opaqueTitleBar", opaqueTitleBar);
    spot_addElement("templateName", templateName);
    templateName.spot_defineAttribute("context", null);
  }

  /**
   * Gets the titleCell element
   *
   * @return the titleCell element or null if a reference was never created
   */
  public GridCell getTitleCell() {
    return titleCell;
  }

  /**
   * Gets the reference to the titleCell element
   * A reference is created if necessary
   *
   * @return the reference to the titleCell element
   */
  public GridCell getTitleCellReference() {
    if (titleCell == null) {
      titleCell = new GridCell(true);
      super.spot_setReference("titleCell", titleCell);
      titleCell.spot_defineAttribute("foreground", null);
    }

    return titleCell;
  }

  /**
   * Sets the reference to the titleCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTitleCell(iSPOTElement reference) throws ClassCastException {
    titleCell = (GridCell) reference;
    spot_setReference("titleCell", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>CollapsibleInfo.expander</code> ENUMERATED object
   */
  public static class CExpander extends SPOTEnumerated {
    public final static int twisty  = 0;
    public final static int chevron = 1;
    public final static int custom  = 2;

    /**
     * Creates a new <code>CExpander</code> object
     */
    public CExpander() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CExpander</code> object
     *
     * @param val the value
     */
    public CExpander(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>expander</code> object
     * the <code>CollapsibleInfo.expander</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CExpander(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
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
      return "{" + "twisty(0), " + "chevron(1), " + "custom(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 3;
      spot_defineAttribute("expandIcon", null);
      spot_defineAttribute("collapseIcon", null);
      spot_defineAttribute("iconOnTheLeft", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "twisty", "chevron", "custom" };
  }
  //}GENERATED_INNER_CLASSES
}
