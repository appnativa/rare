/**************************************************************************
 * PasswordField.java - Wed Nov 18 17:50:58 PST 2015
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
 * This class represents the configuration for a widget that
 * allows a password to be entered.
 * <p>
 * The password can be hashed prior to submission. If the
 * password is hashed, it will be transmitted as a base64
 * encoded string
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class PasswordField extends TextField {
  //GENERATED_MEMBERS{

  /** Behavior: the character to echo */
  public SPOTInteger echoChar = new SPOTInteger(null, 0, 65535, true);

  /** Design:the type of hashing to do on the password */
  public CHashAlgorithm hashAlgorithm = new CHashAlgorithm(null, null, CHashAlgorithm.none, "none", true);

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>PasswordField</code> object.
   */
  public PasswordField()  {
    this(true);
  }

  /**
   * Creates a new <code>PasswordField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public PasswordField( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>PasswordField</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected PasswordField( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=2;
    super.spot_setElements();
    spot_addElement( "echoChar", echoChar );
    spot_addElement( "hashAlgorithm", hashAlgorithm );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>PasswordField.hashAlgorithm</code> ENUMERATED object
   */
  public static class CHashAlgorithm extends SPOTEnumerated {

    public final static int none = 0;
    public final static int md5 = 1;
    public final static int sha = 2;
    /**
     * Creates a new <code>CHashAlgorithm</code> object
     */
    public CHashAlgorithm()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CHashAlgorithm</code> object
     * 
     * @param val the value
     */
    public CHashAlgorithm( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>hashAlgorithm</code> object
     * the <code>PasswordField.hashAlgorithm</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHashAlgorithm( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
    {
      super(ival,sval,idefaultval,sdefaultval,optional);
      _sChoices=_schoices;
      _nChoices=_nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     * 
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange()
    {
      return "{"
      +"none(0), "
      +"md5(1), "
      +"sha(2) }";
    }
    private final static int    _nchoices[] = {
              0,
              1,
              2
             };


    private final static String _schoices[] = {
              "none",
              "md5",
              "sha"
             };

  }
  //}GENERATED_INNER_CLASSES
}
