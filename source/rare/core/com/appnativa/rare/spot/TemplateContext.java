/**************************************************************************
 * TemplateContext.java - Wed Nov 18 17:50:58 PST 2015
 *
 * Copyright (c) appNativa
 *
 * All rights reserved.
 *
 * Generated by the Sparse Notation(tm) To Java Compiler v1.0
 * Note 1: Code entered after the "//USER_IMPORTS_AND_COMMENTS_MARK{}" comment and before the class declaration will be preserved.
 * Note 2: Code entered out side of the other   comment blocks will be preserved
 * Note 3: If you edit the automatically generated comments and want to preserve your edits remove the //GENERATED_COMMENT{} tags
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

  /** Design: the name of the template context */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);

  /** Design: true to automatically widgets that do not have a templateName defined */
  public SPOTBoolean autoSkin = new SPOTBoolean(null, false, false );

  /** Design: the set of widgets for the group box */
  public SPOTSet widgets = new SPOTSet( "widget", new SPOTAny( "com.appnativa.rare.spot.Widget"), -1, -1, true);

  /** Design: the set of columns for the table */
  public SPOTSet columns = new SPOTSet( "column", new ItemDescription(), -1, -1, true);

  /** Design: painters for the cells in the form grid */
  public SPOTSet cellPainters = new SPOTSet( "cell", new GridCell(), -1, -1, true);

  /** Design: data items */
  public SPOTSet dataItems = new SPOTSet( "item", new DataItem(), -1, -1, true);

  /** Design: collapsible definitions */
  public SPOTSet collapsibles = new SPOTSet( "collapsibleInfo", new CollapsibleInfo(), -1, -1, true);

  /** Design: collapsible definitions */
  public SPOTSet regions = new SPOTSet( "region", new Region(), -1, -1, true);

  /** Design: scrollPane definitions */
  public SPOTSet scrollPanes = new SPOTSet( "scrollPane", new ScrollPane(), -1, -1, true);

  /** Design: chart plot definitions */
  public SPOTSet plots = new SPOTSet( "plot", new Plot(), -1, -1, true);

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>TemplateContext</code> object.
   */
  public TemplateContext()  {
    this(true);
  }

  /**
   * Creates a new <code>TemplateContext</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public TemplateContext( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>TemplateContext</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected TemplateContext( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=10;
    super.spot_setElements();
    spot_addElement( "name", name );
    spot_addElement( "autoSkin", autoSkin );
    spot_addElement( "widgets", widgets );
    widgets.spot_defineAttribute("url",null);
    spot_addElement( "columns", columns );
    columns.spot_defineAttribute("url",null);
    spot_addElement( "cellPainters", cellPainters );
    cellPainters.spot_defineAttribute("url",null);
    spot_addElement( "dataItems", dataItems );
    dataItems.spot_defineAttribute("url",null);
    spot_addElement( "collapsibles", collapsibles );
    collapsibles.spot_defineAttribute("url",null);
    spot_addElement( "regions", regions );
    regions.spot_defineAttribute("url",null);
    spot_addElement( "scrollPanes", scrollPanes );
    scrollPanes.spot_defineAttribute("url",null);
    spot_addElement( "plots", plots );
    plots.spot_defineAttribute("url",null);
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
