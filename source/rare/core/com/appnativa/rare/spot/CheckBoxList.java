/**************************************************************************
 * CheckBoxList.java - Wed Feb 17 10:42:11 PST 2016
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
 * This class represents configuration information for a widget
 * that manages a list of data items.
 * <p>
 * Each item is also preceded by a check box that can
 * independently selected
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class CheckBoxList extends ListBox {
  //GENERATED_MEMBERS{

  /** Design: whether checkboxes and the list selection are automatically linked */
  public SPOTBoolean linkSelection = new SPOTBoolean(null, true, false );

  /** Design: whether the checkbox is leading or trailing the item */
  public SPOTBoolean checkboxTrailing = new SPOTBoolean(null, false, false );

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>CheckBoxList</code> object.
   */
  public CheckBoxList()  {
    this(true);
  }

  /**
   * Creates a new <code>CheckBoxList</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public CheckBoxList( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>CheckBoxList</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected CheckBoxList( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=2;
    super.spot_setElements();
    spot_addElement( "linkSelection", linkSelection );
    spot_addElement( "checkboxTrailing", checkboxTrailing );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
