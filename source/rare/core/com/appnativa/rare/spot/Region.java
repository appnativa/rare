/**************************************************************************
 * Region.java - Wed Nov 18 17:50:58 PST 2015
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
 * viewable region on the screen
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class Region extends GridCell {
  //GENERATED_MEMBERS{

  /** Design: the name of the region (used to target the region) */
  public SPOTPrintableString name = new SPOTPrintableString(null, 0, 64, true);

  /** Appearance: how the region should fill/allocate space horizontally */
  public CHorizontalFill horizontalFill = new CHorizontalFill(null, null, CHorizontalFill.maximum, "maximum", false );

  /** Appearance: how the region should fill/allocate space vertically */
  public CVerticalFill verticalFill = new CVerticalFill(null, null, CVerticalFill.maximum, "maximum", false );

  /** Design: whether the region is initially visible */
  public SPOTBoolean visible = new SPOTBoolean(null, true, false );

  /** Layout~insets: the margin between the region's content and it's border */
  protected Margin contentPadding = null;

  /** Design: a viewer for the region */
  public SPOTAny viewer = new SPOTAny( "com.appnativa.rare.spot.Widget", true);

  /** Design~url: a URL to use to construct a viewer */
  public SPOTPrintableString dataURL = new SPOTPrintableString();

  /** Appearance: information specifying if the viewer is collapsible and how the expand/collapse functionality should be configured */
  protected CollapsibleInfo collapsibleInfo = null;

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Region</code> object.
   */
  public Region()  {
    this(true);
  }

  /**
   * Creates a new <code>Region</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Region( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>Region</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Region( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=8;
    super.spot_setElements();
    spot_addElement( "name", name );
    spot_addElement( "horizontalFill", horizontalFill );
    spot_addElement( "verticalFill", verticalFill );
    spot_addElement( "visible", visible );
    spot_addElement( "contentPadding", contentPadding );
    spot_addElement( "viewer", viewer );
    spot_addElement( "dataURL", dataURL );
    dataURL.spot_defineAttribute("mimeType",null);
    dataURL.spot_defineAttribute("deferred","true");
    spot_addElement( "collapsibleInfo", collapsibleInfo );
  }

  /**
   * Gets the contentPadding element
   * 
   * @return the contentPadding element or null if a reference was never created
   */
  public Margin getContentPadding() { return contentPadding; }

  /**
   * Gets the reference to the contentPadding element
   * A reference is created if necessary
   * 
   * @return the reference to the contentPadding element
   */
  public Margin getContentPaddingReference() {
    if ( contentPadding == null ) {
      contentPadding = new Margin(true);
      super.spot_setReference( "contentPadding" , contentPadding);
    }
    return contentPadding;
  }

  /**
   * Sets the reference to the contentPadding element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setContentPadding(iSPOTElement reference) throws ClassCastException {
    contentPadding = (Margin)reference;
    spot_setReference( "contentPadding" ,reference);
  }

  /**
   * Gets the collapsibleInfo element
   * 
   * @return the collapsibleInfo element or null if a reference was never created
   */
  public CollapsibleInfo getCollapsibleInfo() { return collapsibleInfo; }

  /**
   * Gets the reference to the collapsibleInfo element
   * A reference is created if necessary
   * 
   * @return the reference to the collapsibleInfo element
   */
  public CollapsibleInfo getCollapsibleInfoReference() {
    if ( collapsibleInfo == null ) {
      collapsibleInfo = new CollapsibleInfo(true);
      super.spot_setReference( "collapsibleInfo" , collapsibleInfo);
    }
    return collapsibleInfo;
  }

  /**
   * Sets the reference to the collapsibleInfo element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setCollapsibleInfo(iSPOTElement reference) throws ClassCastException {
    collapsibleInfo = (CollapsibleInfo)reference;
    spot_setReference( "collapsibleInfo" ,reference);
  }

  //}GENERATED_METHODS
  public CBorder setBorder(int border) {
    CBorder e = new CBorder(border);

    getBordersReference().clear();
    getBordersReference().add(e);

    return e;
  }

  public CBorder setBorder(String border) {
    CBorder e = new CBorder();

    e.setValue(border);
    getBordersReference().clear();
    getBordersReference().add(e);

    return e;
  }

  public CBorder getBorder() {
    if ((borders == null) || (borders.getCount() == 0)) {
      SPOTEnumerated e = new CBorder(CBorder.standard);

      getBordersReference().add(e);
    }

    return (CBorder) borders.get(0);
  }

  public void setBorderAttribute(String name, String value) {
    if ((borders == null) || (borders.getCount() == 0)) {
      SPOTEnumerated e = new CBorder(CBorder.standard);

      getBordersReference().add(e);
    }

    borders.getEx(0).spot_setAttribute(name, value);
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Region.horizontalFill</code> ENUMERATED object
   */
  public static class CHorizontalFill extends SPOTEnumerated {

    /** use all available space */
    public final static int maximum = 1;
    /** use enough space to accommodate the viewers preferred size */
    public final static int preferred = 2;
    /**
     * Creates a new <code>CHorizontalFill</code> object
     */
    public CHorizontalFill()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CHorizontalFill</code> object
     * 
     * @param val the value
     */
    public CHorizontalFill( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>horizontalFill</code> object
     * the <code>Region.horizontalFill</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHorizontalFill( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"maximum(1), "
      +"preferred(2) }";
    }
    private final static int    _nchoices[] = {
              1,
              2
             };


    private final static String _schoices[] = {
              "maximum",
              "preferred"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>Region.verticalFill</code> ENUMERATED object
   */
  public static class CVerticalFill extends SPOTEnumerated {

    /** use all available space */
    public final static int maximum = 1;
    /** use enough space to accommodate the viewers preferred size */
    public final static int preferred = 2;
    /**
     * Creates a new <code>CVerticalFill</code> object
     */
    public CVerticalFill()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CVerticalFill</code> object
     * 
     * @param val the value
     */
    public CVerticalFill( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>verticalFill</code> object
     * the <code>Region.verticalFill</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CVerticalFill( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"maximum(1), "
      +"preferred(2) }";
    }
    private final static int    _nchoices[] = {
              1,
              2
             };


    private final static String _schoices[] = {
              "maximum",
              "preferred"
             };

  }
  //}GENERATED_INNER_CLASSES
}
