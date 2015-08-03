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
 * that displays and manages multiple rows and columns of data
 * <p>
 * as well as providing functionality for expanding and
 * contracting rows.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class TreeTable extends Table {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the column that is expandable */
  public SPOTInteger expandableColumn = new SPOTInteger(null, 0, null, 0, false);
//GENERATED_COMMENT{}

  /** Design~expandAll: whether all of the table rows should be initially expanded */
  public SPOTBoolean expandAll = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether root node handles should be shown */
  public SPOTBoolean showRootHandles = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether parent items are selectable */
  public SPOTBoolean parentItemsSelectable = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the number of pixels to use to indent a child node from a parent */
  public SPOTInteger indentBy = new SPOTInteger(null, -1, 100, 16, false);
//GENERATED_COMMENT{}

  /** Behavior: whether to automatically scroll rows of a newly expanded branch into view */
  public SPOTBoolean autoScrollOnExpansion = new SPOTBoolean(null, true, false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TreeTable</code> object.
   */
  public TreeTable() {
    this(true);
  }

  /**
   * Creates a new <code>TreeTable</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TreeTable(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>TreeTable</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TreeTable(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 6;
    this.attributeSizeHint += 4;
    super.spot_setElements();
    spot_defineAttribute("onHasCollapsed", null);
    spot_defineAttribute("onHasExpanded", null);
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_addElement("expandableColumn", expandableColumn);
    spot_addElement("expandAll", expandAll);
    spot_addElement("showRootHandles", showRootHandles);
    spot_addElement("parentItemsSelectable", parentItemsSelectable);
    spot_addElement("indentBy", indentBy);
    spot_addElement("autoScrollOnExpansion", autoScrollOnExpansion);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
