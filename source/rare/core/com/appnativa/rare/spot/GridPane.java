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
 * This class represents the configuration information for a
 * Grid viewer utilizing a table layout. Each cell in the grid
 * is a region.
 * <p>
 * The region's configuration defines the type of viewer, the
 * viewer's location and how the viewer will be laid out within
 * the cell.
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class GridPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Hidden: the number of rows the grid will have */
  public SPOTInteger rows = new SPOTInteger(null, 0, 24, 1, false);
//GENERATED_COMMENT{}

  /** Hidden: the number of columns the grid will have */
  public SPOTInteger columns = new SPOTInteger(null, 0, 80, 1, false);
//GENERATED_COMMENT{}

  /** Layout: the amount of space between columns */
  public SPOTInteger columnSpacing = new SPOTInteger(null, 0, 24, 4, false);
//GENERATED_COMMENT{}

  /** Layout: the amount of space between rows */
  public SPOTInteger rowSpacing = new SPOTInteger(null, 0, 24, 2, false);
//GENERATED_COMMENT{}

  /** Design: layout specific column grouping information */
  protected SPOTSet columnGrouping = null;
//GENERATED_COMMENT{}

  /** Design: layout specific row grouping information */
  protected SPOTSet rowGrouping = null;
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Design: the set of regions */
  public SPOTSet regions = new SPOTSet("region", new Region(), -1, -1, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>GridPane</code> object.
   */
  public GridPane() {
    this(true);
  }

  /**
   * Creates a new <code>GridPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public GridPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>GridPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected GridPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 9;
    super.spot_setElements();
    spot_addElement("rows", rows);
    spot_addElement("columns", columns);
    spot_addElement("columnSpacing", columnSpacing);
    spot_addElement("rowSpacing", rowSpacing);
    spot_addElement("columnGrouping", columnGrouping);
    spot_addElement("rowGrouping", rowGrouping);
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("regions", regions);
  }

  /**
   * Gets the columnGrouping element
   *
   * @return the columnGrouping element or null if a reference was never created
   */
  public SPOTSet getColumnGrouping() {
    return columnGrouping;
  }

  /**
   * Gets the reference to the columnGrouping element
   * A reference is created if necessary
   *
   * @return the reference to the columnGrouping element
   */
  public SPOTSet getColumnGroupingReference() {
    if (columnGrouping == null) {
      columnGrouping = new SPOTSet("group", new SPOTPrintableString(), -1, -1, true);
      super.spot_setReference("columnGrouping", columnGrouping);
    }

    return columnGrouping;
  }

  /**
   * Sets the reference to the columnGrouping element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setColumnGrouping(iSPOTElement reference) throws ClassCastException {
    columnGrouping = (SPOTSet) reference;
    spot_setReference("columnGrouping", reference);
  }

  /**
   * Gets the rowGrouping element
   *
   * @return the rowGrouping element or null if a reference was never created
   */
  public SPOTSet getRowGrouping() {
    return rowGrouping;
  }

  /**
   * Gets the reference to the rowGrouping element
   * A reference is created if necessary
   *
   * @return the reference to the rowGrouping element
   */
  public SPOTSet getRowGroupingReference() {
    if (rowGrouping == null) {
      rowGrouping = new SPOTSet("group", new SPOTPrintableString(), -1, -1, true);
      super.spot_setReference("rowGrouping", rowGrouping);
    }

    return rowGrouping;
  }

  /**
   * Sets the reference to the rowGrouping element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setRowGrouping(iSPOTElement reference) throws ClassCastException {
    rowGrouping = (SPOTSet) reference;
    spot_setReference("rowGrouping", reference);
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

  //}GENERATED_METHODS

  /**
   * Finds an returns the named widget (does not recurse)
   *
   * @param name the name of the widget to find
   * @param useNameMap true to use a name map to improve furture search performance; false otherwise
   *
   * @return the named widget or null
   */
  @Override
  public Widget findWidget(String name, boolean useNameMap) {
    final int len = regions.size();

    for (int i = 0; i < len; i++) {
      Region       r = (Region) regions.get(i);
      iSPOTElement v = r.viewer.getValue();

      if (v instanceof GroupBox) {
        Widget w = ((GroupBox) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (v instanceof GridPane) {
        Widget w = ((GridPane) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (v instanceof SplitPane) {
        Widget w = ((SplitPane) v).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      }
    }

    return null;
  }

  /**
   * Finds an returns the widget at the specified x,y position  (does not recurse)
   *
   * @param x the x-position
   * @param y the y-position
   * @return the widget at the specified x,y position or null
   */
  public Widget findWidget(int x, int y) {
    int    len = regions.getCount();
    Region r;

    for (int i = 0; i < len; i++) {
      r = (Region) regions.getEx(i);

      if ((r.getX() == x) && (r.getY() == y)) {
        return (Widget) r.viewer.getValue();
      }
    }

    return null;
  }

  /**
   * Finds an returns the named region (does not recurse)
   *
   * @param name the name of the region to find
   * @return the named region or null
   */
  public Region findRegion(String name) {
    int    len = regions.getCount();
    Region r;

    for (int i = 0; i < len; i++) {
      r = (Region) regions.getEx(i);

      if (name.equals(r.name.getValue())) {
        return r;
      }
    }

    return null;
  }

  /**
   * Finds an returns the region at the specified x,y position  (does not recurse)
   *
   * @param x the x-position
   * @param y the y-position
   * @return the region at the specified x,y position or null
   */
  public Region findRegion(int x, int y) {
    int    len = regions.getCount();
    Region r;

    for (int i = 0; i < len; i++) {
      r = (Region) regions.getEx(i);

      if ((r.getX() == x) && (r.getY() == y)) {
        return r;
      }
    }

    return null;
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
