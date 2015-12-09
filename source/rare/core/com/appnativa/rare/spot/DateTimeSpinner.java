/**************************************************************************
 * DateTimeSpinner.java - Wed Nov 18 17:50:58 PST 2015
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
 * This class represents the configuration for a widget spins
 * through a date range and associated
 * <p>
 * time while also allowing a date or time to be manually
 * entered
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class DateTimeSpinner extends Spinner {
  //GENERATED_MEMBERS{

  /** Appearance: the value for the spinner */
  public SPOTDateTime value = new SPOTDateTime();

  /** Behavior: minimum value for the spinner */
  public SPOTDateTime minValue = new SPOTDateTime();

  /** Behavior: maximum value for the spinner */
  public SPOTDateTime maxValue = new SPOTDateTime();

  /** a format string for how the date/time should be displayed */
  public SPOTPrintableString format = new SPOTPrintableString(null, 0, 64, true);

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DateTimeSpinner</code> object.
   */
  public DateTimeSpinner()  {
    this(true);
  }

  /**
   * Creates a new <code>DateTimeSpinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DateTimeSpinner( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>DateTimeSpinner</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DateTimeSpinner( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=4;
    super.spot_setElements();
    spot_addElement( "value", value );
    spot_addElement( "minValue", minValue );
    spot_addElement( "maxValue", maxValue );
    spot_addElement( "format", format );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
