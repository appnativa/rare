/**************************************************************************
 * Frame.java - Mon Aug 10 16:51:49 PDT 2009
 *
 * Copyright (c) appNativa 2006
 *
 * All rights reserved.
 *
 * Generated by the Sparse Notation(tm) To Java Compiler v1.0
 * Note 1: Code entered after the "//USER_IMPORTS_AND_COMMENTS_MARK{}" comment and before the class declaration will be preserved.
 * Note 2: Code entered out side of the other   comment blocks will be preserved
 * Note 3: If you edit the automatically generated comments and want to preserve your edits remove the //GENERATED_COMMENT{} tags
 */

package com.appnativa.studio.spot;

import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;

//USER_IMPORTS_AND_COMMENTS_MARK{}

public class Frame extends SPOTSequence {
  //GENERATED_MEMBERS{

//GENERATED_COMMENT{}
  /** line number for a break point */ 
  public SPOTPrintableString contextID = new SPOTPrintableString();

//GENERATED_COMMENT{}
  /** line number for a break point */ 
  public SPOTInteger lineNumber = new SPOTInteger();

//GENERATED_COMMENT{}
  /**  */ 
  public SPOTPrintableString reference = new SPOTPrintableString();

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Frame</code> object.
   */
  public Frame()  {
    this(true);
  }

  /**
   * Creates a new <code>Frame</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Frame( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>Frame</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Frame( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    super.spot_setElements();
    spot_addElement( "contextID", contextID );
    spot_addElement( "lineNumber", lineNumber );
    spot_addElement( "reference", reference );
  }

  //}GENERATED_METHODS

    public Frame( int lineNumber,String ref) {
      this(true);
      this.lineNumber.setValue(lineNumber);
      this.reference.setValue(ref);
    }

    public Frame( int lineNumber,String ref, int contextID) {
      this(true);
      this.lineNumber.setValue(lineNumber);
      this.reference.setValue(ref);
      this.contextID.setValue(contextID);
    }

    public Frame( int lineNumber,String ref, String contextID) {
      this(true);
      this.lineNumber.setValue(lineNumber);
      this.reference.setValue(ref);
      this.contextID.setValue(contextID);
    }

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
