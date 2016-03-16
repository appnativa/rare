/**************************************************************************
 * Bean.java - Wed Feb 17 10:42:11 PST 2016
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
 * This class represents the configuration information for a
 * bean (arbitrary/component) that can be
 * <p>
 * specified as a class name or the url for the bean
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class Bean extends Widget {
  //GENERATED_MEMBERS{

  /** Design: the class name for the widget's component */
  public SPOTPrintableString beanClass = new SPOTPrintableString(null, 0, 255, true);

  /** Design: the url for the jar containing the bean class files */
  public SPOTPrintableString beanJAR = new SPOTPrintableString(null, 0, 255, true);

  /** Design: the url for the serialized bean */
  public SPOTPrintableString beanURL = new SPOTPrintableString(null, 0, 255, true);

  /** Design: custom bean configuration object */
  public SPOTAny beanProperties = new SPOTAny( "com.appnativa.spot.SPOTSequence", true);

  /** the string to display when the bean cannot be instantiated */
  public SPOTPrintableString failureMessage = new SPOTPrintableString();

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Bean</code> object.
   */
  public Bean()  {
    this(true);
  }

  /**
   * Creates a new <code>Bean</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Bean( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>Bean</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Bean( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=5;
    this.attributeSizeHint +=7;
    super.spot_setElements();
    spot_defineAttribute("onItemAdded",null);
    spot_defineAttribute("onAction",null);
    spot_defineAttribute("onChange",null);
    spot_defineAttribute("onWillExpand",null);
    spot_defineAttribute("onWillCollapse",null);
    spot_defineAttribute("onHasCollapsed",null);
    spot_defineAttribute("onHasExpanded",null);
    spot_addElement( "beanClass", beanClass );
    spot_addElement( "beanJAR", beanJAR );
    spot_addElement( "beanURL", beanURL );
    spot_addElement( "beanProperties", beanProperties );
    spot_addElement( "failureMessage", failureMessage );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
