/**************************************************************************
 * DebugRequest.java - Mon Aug 10 16:51:49 PDT 2009
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

import com.appnativa.spot.SPOTAny;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTSequence;

//USER_IMPORTS_AND_COMMENTS_MARK{}


//GENERATED_COMMENT{}
/**
 * DebugMessage {
 * <p>
 * applicationID Integer
 * </p>
 * <p>
 * messageType Enumerated {
 * </p>
 * <p>
 * attach (0)
 * </p>
 * <p>
 * detach (1)
 * </p>
 * <p>
 * update (2)
 * </p>
 * <p>
 * breakpoint (3)
 * </p>
 * <p>
 * watchpoint (4)
 * </p>
 * <p>
 * error (5)
 * </p>
 * <p>
 * stop (6)
 * </p>
 * <p>
 * gloabl_variable_list (7)
 * </p>
 * <p>
 * engine_variable_list (8)
 * </p>
 * <p>
 * global_variable_list (7)
 * </p>
 * <p>
 * engine_variable_list (8)
 * </p>
 * <p>
 * watch_point_li
 * </p>
 * <p>
 * }
 * </p>
 * <p>
 * entryReference PrintableString Range(1..) Optional
 * </p>
 * <p>
 * lineNumber Integer Optional
 * </p>
 * <p>
 * commandNumber Integer Optional
 * </p>
 * <p>
 * variables Set {
 * </p>
 * <p>
 * variable NameValuePair
 * </p>
 * <p>
 * } Reference
 * </p>
 * <p>
 * }
 * </p>
 *
 * @author Don DeCoteau
 * @version 1.0
 */  
public class DebugRequest extends SPOTSequence {
  //GENERATED_MEMBERS{

  public SPOTInteger applicationID = new SPOTInteger();

  public SPOTInteger correlationID = new SPOTInteger(null, 0, false );

  public CMessageType messageType = new CMessageType();

  public SPOTAny value = new SPOTAny(true);

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DebugRequest</code> object.
   */
  public DebugRequest()  {
    this(true);
  }

  /**
   * Creates a new <code>DebugRequest</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DebugRequest( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>DebugRequest</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DebugRequest( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    super.spot_setElements();
    spot_addElement( "applicationID", applicationID );
    spot_addElement( "correlationID", correlationID );
    spot_addElement( "messageType", messageType );
    spot_addElement( "value", value );
  }

  //}GENERATED_METHODS

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>DebugRequest.messageType</code> ENUMERATED object
   */
  public static class CMessageType extends SPOTEnumerated {

    /** value is an integer containing application id */
    public final static int attach = 0;
    /** value is an integer containing application id */
    public final static int detach = 1;
    /** value is a set of DebugPoint */
    public final static int set_points = 2;
    /** value is a set of DebugPoint */
    public final static int add_point = 3;
    /** value is a set of DebugPoint */
    public final static int remove_point = 4;
    /** value is null */
    public final static int clear_all_breakpoints = 5;
    /** value is null */
    public final static int clear_all_watchpoints = 6;
    /** value is null */
    public final static int clear_all_points = 7;
    /** value is null */
    public final static int go = 8;
    /** value is an integer containing the step size */
    public final static int step_over = 9;
    /** value is an integer containing the step size */
    public final static int step_into = 10;
    /** value is an integer containing the step size */
    public final static int step_out = 11;
    /** value is an integer representing the context id for the stack frame */
    public final static int context_switch = 12;
    /** input value is the set of strings to evaluate */
    public final static int evaluate = 13;
    /** input value is a string containing the name of the variable (prepend asterik to designate a system variable); output is an octet string of length encoded name/value pairs */
    public final static int get_children = 15;
    /** input value is a string containing the url for the script; output value is a string */
    public final static int get_script = 16;
    /** value is null */
    public final static int stop = 20;
    /**
     * Creates a new <code>CMessageType</code> object
     */
    public CMessageType()
    {
      super();
      spot_setChoices();
    }

    /**
     * Creates a new <code>CMessageType</code> object
     * 
     *  @param optional <code>true</code> if the node the object represents is optional
     */
    public CMessageType(boolean optional)
    {
      super(optional);
      spot_setChoices();
    }

    /**
     * Creates a new <code>CMessageType</code> object
     * 
     * @param val the value
     */
    public CMessageType( int val )
    {
      super();
      spot_setChoices();
      setValue(val);
    }

    /**
     * Creates a new <code>CMessageType</code> object
     * 
     * @param val       the value
     * @param optional  <code>true</code> if the node the object represents is optional
     */
    public CMessageType( int val, boolean optional)
    {
      super(optional);
      spot_setChoices();
      setValue(val);
    }

    /**
     * Creates a new <code>CMessageType</code> object
     * 
     * @param val        the value
     * @param defaultval the default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CMessageType( String val, int defaultval, boolean optional)
    {
      super(optional);
      spot_setChoices();
      setValue(val);
      spot_setDefaultValue(defaultval);
    }

    /**
     * Creates a new <code>messageType</code> object
     * the <code>DebugRequest.messageType</code> ENUMERATED object
     * 
     * @param val        the value
     * @param defaultval the default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CMessageType( String val, String defaultval, boolean optional)
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
      +"attach(0), "
      +"detach(1), "
      +"set_points(2), "
      +"add_point(3), "
      +"remove_point(4), "
      +"clear_all_breakpoints(5), "
      +"clear_all_watchpoints(6), "
      +"clear_all_points(7), "
      +"go(8), "
      +"step_over(9), "
      +"step_into(10), "
      +"step_out(11), "
      +"context_switch(12), "
      +"evaluate(13), "
      +"get_children(15), "
      +"get_script(16), "
      +"stop(20) }";
    }

    private void spot_setChoices()
    {
      int    nchoices[] = {
              0,
              1,
              2,
              3,
              4,
              5,
              6,
              7,
              8,
              9,
              10,
              11,
              12,
              13,
              15,
              16,
              20
             };


      String schoices[] = {
              "attach",
              "detach",
              "set_points",
              "add_point",
              "remove_point",
              "clear_all_breakpoints",
              "clear_all_watchpoints",
              "clear_all_points",
              "go",
              "step_over",
              "step_into",
              "step_out",
              "context_switch",
              "evaluate",
              "get_children",
              "get_script",
              "stop"
             };

      spot_setChoices(schoices,nchoices);
    }
  }
  //}GENERATED_INNER_CLASSES
}
