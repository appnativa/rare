/**************************************************************************
 * DataCollection.java - Wed Feb 17 10:42:11 PST 2016
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
 * This class represents configuration information for a data
 * collection
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class DataCollection extends SPOTSequence {
  //GENERATED_MEMBERS{

  /** the name of the collection */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, false);

  /** the name or class that handles the collection. If not specified the default handler is used */
  public SPOTPrintableString handler = new SPOTPrintableString(null, 0, 255, true);

  /** a URL to use to retrieve data for the collection */
  public SPOTPrintableString dataURL = new SPOTPrintableString();

  /** a set of attributes for the collection. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString attributes = new SPOTPrintableString();

  /** whether the data is tabular */
  public SPOTBoolean tabular = new SPOTBoolean(null, true, false );

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DataCollection</code> object.
   */
  public DataCollection()  {
    this(true);
  }

  /**
   * Creates a new <code>DataCollection</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DataCollection( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>DataCollection</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DataCollection( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=5;
    super.spot_setElements();
    spot_addElement( "name", name );
    spot_addElement( "handler", handler );
    spot_addElement( "dataURL", dataURL );
    dataURL.spot_defineAttribute("mimeType",null);
    dataURL.spot_defineAttribute("deferred","true");
    dataURL.spot_defineAttribute("columnSeparator","|");
    dataURL.spot_defineAttribute("inline",null);
    dataURL.spot_defineAttribute("ldSeparator",null);
    dataURL.spot_defineAttribute("riSeparator",null);
    dataURL.spot_defineAttribute("unescape","false");
    dataURL.spot_defineAttribute("aggregate",null);
    dataURL.spot_defineAttribute("parser",null);
    spot_addElement( "attributes", attributes );
    attributes.spot_defineAttribute("mimeType",null);
    spot_addElement( "tabular", tabular );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
