/**************************************************************************
 * DebugUpdate.java - Mon Aug 10 16:51:49 PDT 2009
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

import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTOctetString;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;

//USER_IMPORTS_AND_COMMENTS_MARK{}

public class DebugUpdate extends SPOTSequence {
  //GENERATED_MEMBERS{

  public CUpdateType updateType = new CUpdateType(null, CUpdateType.change, false );

  public DebugPoint point = new DebugPoint();

//GENERATED_COMMENT{}
  /** other context specific information (if point.pointType==watchpoint it will be the name of the variable) */ 
  public SPOTPrintableString otherInfo = new SPOTPrintableString();

  public SPOTSet stackFrames = new SPOTSet( "frame", new Frame(), -1, -1, true);

//GENERATED_COMMENT{}
  /** context variables */ 
  public SPOTSet contextVariables = new SPOTSet( "variable", new Variable(), -1, -1, true);

//GENERATED_COMMENT{}
  /** context variables */ 
  public SPOTSet systemVariables = new SPOTSet( "variable", new Variable(), -1, -1, true);

//GENERATED_COMMENT{}
  /** source for a point that does not have a valid url */ 
  public SPOTOctetString unknownSource = new SPOTOctetString();

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DebugUpdate</code> object.
   */
  public DebugUpdate()  {
    this(true);
  }

  /**
   * Creates a new <code>DebugUpdate</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DebugUpdate( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>DebugUpdate</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DebugUpdate( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    super.spot_setElements();
    spot_addElement( "updateType", updateType );
    spot_addElement( "point", point );
    spot_addElement( "otherInfo", otherInfo );
    spot_addElement( "stackFrames", stackFrames );
    stackFrames.spot_defineAttribute("activeID",null);
    spot_addElement( "contextVariables", contextVariables );
    spot_addElement( "systemVariables", systemVariables );
    spot_addElement( "unknownSource", unknownSource );
    unknownSource.spot_defineAttribute("language",null);
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>DebugUpdate.updateType</code> ENUMERATED object
   */
  public static class CUpdateType extends SPOTEnumerated {

    public final static int hit = 1;
    public final static int change = 2;
    public final static int error = 3;
    /**
     * Creates a new <code>CUpdateType</code> object
     */
    public CUpdateType()
    {
      super();
      spot_setChoices();
    }

    /**
     * Creates a new <code>CUpdateType</code> object
     * 
     *  @param optional <code>true</code> if the node the object represents is optional
     */
    public CUpdateType(boolean optional)
    {
      super(optional);
      spot_setChoices();
    }

    /**
     * Creates a new <code>CUpdateType</code> object
     * 
     * @param val the value
     */
    public CUpdateType( int val )
    {
      super();
      spot_setChoices();
      setValue(val);
    }

    /**
     * Creates a new <code>CUpdateType</code> object
     * 
     * @param val       the value
     * @param optional  <code>true</code> if the node the object represents is optional
     */
    public CUpdateType( int val, boolean optional)
    {
      super(optional);
      spot_setChoices();
      setValue(val);
    }

    /**
     * Creates a new <code>CUpdateType</code> object
     * 
     * @param val        the value
     * @param defaultval the default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CUpdateType( String val, int defaultval, boolean optional)
    {
      super(optional);
      spot_setChoices();
      setValue(val);
      spot_setDefaultValue(defaultval);
    }

    /**
     * Creates a new <code>updateType</code> object
     * the <code>DebugUpdate.updateType</code> ENUMERATED object
     * 
     * @param val        the value
     * @param defaultval the default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CUpdateType( String val, String defaultval, boolean optional)
    {
      super(optional);
      spot_setChoices();
      setValue(val);
      spot_setDefaultValue(defaultval);
    }

    /**
     * Retrieves the range of valid values for the object
     * 
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange()
    {
      return "{"
      +"hit(1), "
      +"change(2), "
      +"error(3) }";
    }

    private void spot_setChoices()
    {
      int    nchoices[] = {
              1,
              2,
              3
             };


      String schoices[] = {
              "hit",
              "change",
              "error"
             };

      spot_setChoices(schoices,nchoices);
    }
  }
  //}GENERATED_INNER_CLASSES
}