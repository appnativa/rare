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
 * This class represents a set of templates for object
 * definitions
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class TemplateContext extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the name of the template context */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);
//GENERATED_COMMENT{}

  /** Design: true to automatically widgets that do not have a templateName defined */
  public SPOTBoolean autoSkin = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: the set of widgets for the group box */
  public SPOTSet widgets = new SPOTSet("widget", new SPOTAny("com.appnativa.rare.spot.Widget"), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: the set of columns for the table */
  public SPOTSet columns = new SPOTSet("column", new ItemDescription(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: painters for the cells in the form grid */
  public SPOTSet cellPainters = new SPOTSet("cell", new GridCell(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: data items */
  public SPOTSet dataItems = new SPOTSet("item", new DataItem(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: collapsible definitions */
  public SPOTSet collapsibles = new SPOTSet("collapsibleInfo", new CollapsibleInfo(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: collapsible definitions */
  public SPOTSet regions = new SPOTSet("region", new Region(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: scrollPane definitions */
  public SPOTSet scrollPanes = new SPOTSet("scrollPane", new ScrollPane(), -1, -1, true);
//GENERATED_COMMENT{}

  /** Design: chart plot definitions */
  public SPOTSet plots = new SPOTSet("plot", new Plot(), -1, -1, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TemplateContext</code> object.
   */
  public TemplateContext() {
    this(true);
  }

  /**
   * Creates a new <code>TemplateContext</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TemplateContext(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>TemplateContext</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TemplateContext(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 10;
    super.spot_setElements();
    spot_addElement("name", name);
    spot_addElement("autoSkin", autoSkin);
    spot_addElement("widgets", widgets);
    widgets.spot_defineAttribute("url", null);
    spot_addElement("columns", columns);
    columns.spot_defineAttribute("url", null);
    spot_addElement("cellPainters", cellPainters);
    cellPainters.spot_defineAttribute("url", null);
    spot_addElement("dataItems", dataItems);
    dataItems.spot_defineAttribute("url", null);
    spot_addElement("collapsibles", collapsibles);
    collapsibles.spot_defineAttribute("url", null);
    spot_addElement("regions", regions);
    regions.spot_defineAttribute("url", null);
    spot_addElement("scrollPanes", scrollPanes);
    scrollPanes.spot_defineAttribute("url", null);
    spot_addElement("plots", plots);
    plots.spot_defineAttribute("url", null);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
