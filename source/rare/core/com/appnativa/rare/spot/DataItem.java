/**************************************************************************
 * DataItem.java - Wed Feb 17 10:42:11 PST 2016
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
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.Utils;
import com.appnativa.spot.SPOTBoolean;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTInteger;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;

//GENERATED_COMMENT{}
/**
 * This class represents the configuration information for a
 * generic data item
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class DataItem extends SPOTSequence {
  //GENERATED_MEMBERS{

  /** the type of data the column represents */
  public CValueType valueType = new CValueType(null, null, CValueType.string_type, "string_type", false );

  /** the class name for a custom type */
  public SPOTPrintableString customTypeClass = new SPOTPrintableString();

  /** the item's value */
  public SPOTPrintableString value = new SPOTPrintableString();

  /** additional value describing the information (e.g. date format for dates) */
  public SPOTPrintableString valueContext = new SPOTPrintableString();

  /** data linked with the value */
  public SPOTPrintableString linkedData = new SPOTPrintableString();

  /** additional value describing the linked data */
  public SPOTPrintableString linkedDataContext = new SPOTPrintableString();

  /** horizontal alignment */
  public CHorizontalAlign horizontalAlign = new CHorizontalAlign(null, null, CHorizontalAlign.auto, "auto", true);

  /** vertical alignment */
  public CVerticalAlign verticalAlign = new CVerticalAlign(null, null, CVerticalAlign.auto, "auto", true);

  /** the position of the icon */
  public CIconPosition iconPosition = new CIconPosition(null, null, CIconPosition.auto, "auto", false );

  /** Behavior: whether the widget's icon is scaled based on the widget's size */
  public SPOTBoolean scaleIcon = new SPOTBoolean(null, false, false );

  /** how to scale the widget's icon */
  public COrientation orientation = new COrientation(null, null, COrientation.auto, "auto", false );

  /** whether the item is enabled */
  public SPOTBoolean enabled = new SPOTBoolean(null, true, false );

  /** whether the item is visible */
  public SPOTBoolean visible = new SPOTBoolean(null, true, false );

  /** link to activate when a an the item's action is initiated */
  protected Link actionLink = null;

  /** Appearance~icon: url to use to retrieve an icon associated with the item */
  public SPOTPrintableString icon = new SPOTPrintableString(null, 0, 255, true);

  /** Appearance~icon: url to use to retrieve a icon associated with the item's disabled state */
  public SPOTPrintableString disabledIcon = new SPOTPrintableString(null, 0, 255, true);

  /** Appearance~icon: url to use to retrieve an icon associated with the item's alternate state */
  public SPOTPrintableString alternateIcon = new SPOTPrintableString(null, 0, 255, true);

  /** the tooltip for the item */
  public SPOTPrintableString tooltip = new SPOTPrintableString(null, 0, 255, true);

  /** the name of a cursor for the item */
  public SPOTPrintableString cursorName = new SPOTPrintableString(null, 0, 64, true);

  /** Appearance~font: the font for the item */
  public Font font = new Font();

  /** Appearance~color: the foreground color for the item */
  public SPOTPrintableString fgColor = new SPOTPrintableString(null, 0, 64, true);

  /** Appearance~~reload: a cell description for the item */
  protected GridCell gridCell = null;

  /** the number of rows the item will span */
  public SPOTInteger rowSpan = new SPOTInteger(null, 0, null, 1, false);

  /** the number of columns the item will span */
  public SPOTInteger columnSpan = new SPOTInteger(null, -1, null, 1, false);

  /** the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString converterClass = new SPOTPrintableString(null, 0, 255, true);

  /** the programmatic class that converts the item's string value to an object */
  public SPOTPrintableString linkedDataconverterClass = new SPOTPrintableString(null, 0, 255, true);

  /** whether to allow the item to be dragged if the widget allows dragging */
  public SPOTBoolean draggingAllowed = new SPOTBoolean(null, true, false );

  /** data flavors that the item can import */
  protected SPOTSet importDataFlavors = null;

  /** data flavors that the item will export */
  protected SPOTSet exportDataFlavors = null;

  /** Design~~reload: name of an object template to use to customize the item */
  public SPOTPrintableString templateName = new SPOTPrintableString(null, 0, 64, true);

  /** sub items for this item */
  protected SPOTSet subItems = null;

  /** a set of custom name/value for the object. The default format is a set of name/value pairs separated by a semi-colon */
  public SPOTPrintableString customProperties = new SPOTPrintableString();

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>DataItem</code> object.
   */
  public DataItem()  {
    this(true);
  }

  /**
   * Creates a new <code>DataItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public DataItem( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>DataItem</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected DataItem( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=32;
    this.attributeSizeHint +=5;
    super.spot_setElements();
    spot_defineAttribute("expanded","false");
    spot_defineAttribute("selectable","true");
    spot_defineAttribute("selected","false");
    spot_defineAttribute("checked","false");
    spot_defineAttribute("editable",null);
    spot_addElement( "valueType", valueType );
    spot_addElement( "customTypeClass", customTypeClass );
    spot_addElement( "value", value );
    spot_addElement( "valueContext", valueContext );
    spot_addElement( "linkedData", linkedData );
    spot_addElement( "linkedDataContext", linkedDataContext );
    spot_addElement( "horizontalAlign", horizontalAlign );
    spot_addElement( "verticalAlign", verticalAlign );
    spot_addElement( "iconPosition", iconPosition );
    spot_addElement( "scaleIcon", scaleIcon );
    scaleIcon.spot_defineAttribute("percent",null);
    spot_addElement( "orientation", orientation );
    spot_addElement( "enabled", enabled );
    spot_addElement( "visible", visible );
    spot_addElement( "actionLink", actionLink );
    spot_addElement( "icon", icon );
    icon.spot_defineAttribute("alt",null);
    icon.spot_defineAttribute("slice",null);
    icon.spot_defineAttribute("size",null);
    icon.spot_defineAttribute("scaling",null);
    icon.spot_defineAttribute("density",null);
    spot_addElement( "disabledIcon", disabledIcon );
    disabledIcon.spot_defineAttribute("alt",null);
    disabledIcon.spot_defineAttribute("slice",null);
    disabledIcon.spot_defineAttribute("size",null);
    disabledIcon.spot_defineAttribute("scaling",null);
    disabledIcon.spot_defineAttribute("density",null);
    spot_addElement( "alternateIcon", alternateIcon );
    alternateIcon.spot_defineAttribute("alt",null);
    alternateIcon.spot_defineAttribute("slice",null);
    alternateIcon.spot_defineAttribute("size",null);
    alternateIcon.spot_defineAttribute("scaling",null);
    alternateIcon.spot_defineAttribute("density",null);
    spot_addElement( "tooltip", tooltip );
    spot_addElement( "cursorName", cursorName );
    cursorName.spot_defineAttribute("cursorDisplayType",null);
    spot_addElement( "font", font );
    spot_addElement( "fgColor", fgColor );
    spot_addElement( "gridCell", gridCell );
    spot_addElement( "rowSpan", rowSpan );
    spot_addElement( "columnSpan", columnSpan );
    spot_addElement( "converterClass", converterClass );
    spot_addElement( "linkedDataconverterClass", linkedDataconverterClass );
    spot_addElement( "draggingAllowed", draggingAllowed );
    spot_addElement( "importDataFlavors", importDataFlavors );
    spot_addElement( "exportDataFlavors", exportDataFlavors );
    spot_addElement( "templateName", templateName );
    templateName.spot_defineAttribute("context",null);
    spot_addElement( "subItems", subItems );
    spot_addElement( "customProperties", customProperties );
    customProperties.spot_defineAttribute("mimeType",null);
    customProperties.spot_defineAttribute("delimiter",null);
  }

  /**
   * Gets the actionLink element
   * 
   * @return the actionLink element or null if a reference was never created
   */
  public Link getActionLink() { return actionLink; }

  /**
   * Gets the reference to the actionLink element
   * A reference is created if necessary
   * 
   * @return the reference to the actionLink element
   */
  public Link getActionLinkReference() {
    if ( actionLink == null ) {
      actionLink = new Link(true);
      super.spot_setReference( "actionLink" , actionLink);
    }
    return actionLink;
  }

  /**
   * Sets the reference to the actionLink element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setActionLink(iSPOTElement reference) throws ClassCastException {
    actionLink = (Link)reference;
    spot_setReference( "actionLink" ,reference);
  }

  /**
   * Gets the gridCell element
   * 
   * @return the gridCell element or null if a reference was never created
   */
  public GridCell getGridCell() { return gridCell; }

  /**
   * Gets the reference to the gridCell element
   * A reference is created if necessary
   * 
   * @return the reference to the gridCell element
   */
  public GridCell getGridCellReference() {
    if ( gridCell == null ) {
      gridCell = new GridCell(true);
      super.spot_setReference( "gridCell" , gridCell);
    }
    return gridCell;
  }

  /**
   * Sets the reference to the gridCell element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setGridCell(iSPOTElement reference) throws ClassCastException {
    gridCell = (GridCell)reference;
    spot_setReference( "gridCell" ,reference);
  }

  /**
   * Gets the importDataFlavors element
   * 
   * @return the importDataFlavors element or null if a reference was never created
   */
  public SPOTSet getImportDataFlavors() { return importDataFlavors; }

  /**
   * Gets the reference to the importDataFlavors element
   * A reference is created if necessary
   * 
   * @return the reference to the importDataFlavors element
   */
  public SPOTSet getImportDataFlavorsReference() {
    if ( importDataFlavors == null ) {
      importDataFlavors = new SPOTSet( "flavor", new SPOTPrintableString(null, 0, 255, false), -1, -1, true);
      super.spot_setReference( "importDataFlavors" , importDataFlavors);
    }
    return importDataFlavors;
  }

  /**
   * Sets the reference to the importDataFlavors element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setImportDataFlavors(iSPOTElement reference) throws ClassCastException {
    importDataFlavors = (SPOTSet)reference;
    spot_setReference( "importDataFlavors" ,reference);
  }

  /**
   * Gets the exportDataFlavors element
   * 
   * @return the exportDataFlavors element or null if a reference was never created
   */
  public SPOTSet getExportDataFlavors() { return exportDataFlavors; }

  /**
   * Gets the reference to the exportDataFlavors element
   * A reference is created if necessary
   * 
   * @return the reference to the exportDataFlavors element
   */
  public SPOTSet getExportDataFlavorsReference() {
    if ( exportDataFlavors == null ) {
      exportDataFlavors = new SPOTSet( "flavor", new SPOTPrintableString(null, 0, 255, false), -1, -1, true);
      super.spot_setReference( "exportDataFlavors" , exportDataFlavors);
    }
    return exportDataFlavors;
  }

  /**
   * Sets the reference to the exportDataFlavors element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setExportDataFlavors(iSPOTElement reference) throws ClassCastException {
    exportDataFlavors = (SPOTSet)reference;
    spot_setReference( "exportDataFlavors" ,reference);
  }

  /**
   * Gets the subItems element
   * 
   * @return the subItems element or null if a reference was never created
   */
  public SPOTSet getSubItems() { return subItems; }

  /**
   * Gets the reference to the subItems element
   * A reference is created if necessary
   * 
   * @return the reference to the subItems element
   */
  public SPOTSet getSubItemsReference() {
    if ( subItems == null ) {
      subItems = new SPOTSet( "item", new DataItem(), -1, -1, true);
      super.spot_setReference( "subItems" , subItems);
      subItems.spot_defineAttribute("url",null);
      subItems.spot_defineAttribute("url",null);
    }
    return subItems;
  }

  /**
   * Sets the reference to the subItems element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSubItems(iSPOTElement reference) throws ClassCastException {
    subItems = (SPOTSet)reference;
    spot_setReference( "subItems" ,reference);
  }

  //}GENERATED_METHODS
  public DataItem(String value) {
    this(false);
    setValue(value);
  }

  public DataItem(String value, String data) {
    this(false);
    setValue(value);
    this.linkedData.setValue(data);
  }

  public DataItem(String value, String data, String icon) {
    this(false);
    setValues(value, data, icon);
  }

  public void setValue(String value) {
    this.value.setValue(value);
  }

  public void setLinkedObject(String data) {
    this.linkedData.setValue(data);
  }

  public void setValues(String value, String data, String icon) {
    this.value.setValue(value);
    this.linkedData.setValue(data);
    this.icon.setValue(icon);
  }

  public DataItem addSubItem() {
    return addSubItem(new DataItem(true));
  }

  public DataItem addSubItem(DataItem item) {
    if (subItems == null) {
      getSubItemsReference();
    }

    subItems.add(item);

    return item;
  }

  public DataItem addSubItem(String value) {
    return addSubItem(new DataItem(value));
  }

  public DataItem addSubItem(String value, String data) {
    return addSubItem(new DataItem(value, data));
  }

  public DataItem addSubItem(String value, String data, String icon) {
    return addSubItem(new DataItem(value, data, icon));
  }

  public RenderableDataItem.HorizontalAlign getHorizontalAlignment() {
    return Utils.getHorizontalAlignment(horizontalAlign.intValue());
  }

  public RenderableDataItem.VerticalAlign getVerticalAlignment() {
    return Utils.getVerticalAlignment(verticalAlign.intValue());
  }

  public RenderableDataItem.IconPosition getIconPosition() {
    return Utils.getIconPosition(iconPosition.intValue());
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>DataItem.valueType</code> ENUMERATED object
   */
  public static class CValueType extends SPOTEnumerated {

    /** type is determined by the widget and/or other configuration information */
    public final static int auto_type = 0;
    /** the column represents string items */
    public final static int string_type = 1;
    /** the column represents integer numbers */
    public final static int integer_type = 2;
    /** the column represents decimal numbers */
    public final static int decimal_type = 3;
    /** the column represents date and time items */
    public final static int date_time_type = 4;
    /** the column represents date items */
    public final static int date_type = 5;
    /** the column represents time items */
    public final static int time_type = 6;
    /** the column represents boolean items */
    public final static int boolean_type = 7;
    /** the column represents a byte array that will be base64 encoded */
    public final static int bytes_base64_type = 8;
    /** the column represents items that themselves can contain other items */
    public final static int struct_type = 9;
    /** the column represents array items (use ArrayTypeInformation to describe the array) */
    public final static int array_type = 10;
    /** the column represents widget items */
    public final static int widget_type = 11;
    /** items are of a custom type */
    public final static int custom_type = 99;
    /**
     * Creates a new <code>CValueType</code> object
     */
    public CValueType()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CValueType</code> object
     * 
     * @param val the value
     */
    public CValueType( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>valueType</code> object
     * the <code>DataItem.valueType</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CValueType( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"auto_type(0), "
      +"string_type(1), "
      +"integer_type(2), "
      +"decimal_type(3), "
      +"date_time_type(4), "
      +"date_type(5), "
      +"time_type(6), "
      +"boolean_type(7), "
      +"bytes_base64_type(8), "
      +"struct_type(9), "
      +"array_type(10), "
      +"widget_type(11), "
      +"custom_type(99) }";
    }
    private final static int    _nchoices[] = {
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
              99
             };


    private final static String _schoices[] = {
              "auto_type",
              "string_type",
              "integer_type",
              "decimal_type",
              "date_time_type",
              "date_type",
              "time_type",
              "boolean_type",
              "bytes_base64_type",
              "struct_type",
              "array_type",
              "widget_type",
              "custom_type"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>DataItem.horizontalAlign</code> ENUMERATED object
   */
  public static class CHorizontalAlign extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;
    /** the text is left aligned */
    public final static int left = 1;
    /** the text is right aligned */
    public final static int right = 2;
    /** the text is aligned along the leading edge */
    public final static int leading = 3;
    /** the text is aligned along the trailing edge */
    public final static int trailing = 4;
    /** the text is centered */
    public final static int center = 5;
    /**
     * Creates a new <code>CHorizontalAlign</code> object
     */
    public CHorizontalAlign()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CHorizontalAlign</code> object
     * 
     * @param val the value
     */
    public CHorizontalAlign( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>horizontalAlign</code> object
     * the <code>DataItem.horizontalAlign</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CHorizontalAlign( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"auto(0), "
      +"left(1), "
      +"right(2), "
      +"leading(3), "
      +"trailing(4), "
      +"center(5) }";
    }
    private final static int    _nchoices[] = {
              0,
              1,
              2,
              3,
              4,
              5
             };


    private final static String _schoices[] = {
              "auto",
              "left",
              "right",
              "leading",
              "trailing",
              "center"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>DataItem.verticalAlign</code> ENUMERATED object
   */
  public static class CVerticalAlign extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;
    /** the text is top aligned */
    public final static int top = 1;
    /** the text is bottom aligned */
    public final static int bottom = 2;
    /** the text is centered */
    public final static int center = 5;
    /**
     * Creates a new <code>CVerticalAlign</code> object
     */
    public CVerticalAlign()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CVerticalAlign</code> object
     * 
     * @param val the value
     */
    public CVerticalAlign( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>verticalAlign</code> object
     * the <code>DataItem.verticalAlign</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CVerticalAlign( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"auto(0), "
      +"top(1), "
      +"bottom(2), "
      +"center(5) }";
    }
    private final static int    _nchoices[] = {
              0,
              1,
              2,
              5
             };


    private final static String _schoices[] = {
              "auto",
              "top",
              "bottom",
              "center"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>DataItem.iconPosition</code> ENUMERATED object
   */
  public static class CIconPosition extends SPOTEnumerated {

    /** let the widget decide */
    public final static int auto = 0;
    /** the icon is positioned to the left of the text */
    public final static int left = 1;
    /** the icon is positioned the right of the text */
    public final static int right = 2;
    /** the icon is positioned along the leading edge */
    public final static int leading = 3;
    /** the icon is positioned along the trailing edge */
    public final static int trailing = 4;
    /** the icon is positioned above the text and is centered */
    public final static int top_center = 5;
    /** the icon is positioned to the top_left of the text */
    public final static int top_left = 6;
    /** the icon is positioned the top_right of the text */
    public final static int top_right = 7;
    /** the icon is positioned below the text and is centered */
    public final static int bottom_center = 8;
    /** the icon is positioned to the bottom_left of the text */
    public final static int bottom_left = 9;
    /** the icon is positioned the bottom_right of the text */
    public final static int bottom_right = 10;
    /** the icon is positioned to the far right of the text */
    public final static int right_justified = 11;
    /**
     * Creates a new <code>CIconPosition</code> object
     */
    public CIconPosition()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CIconPosition</code> object
     * 
     * @param val the value
     */
    public CIconPosition( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>iconPosition</code> object
     * the <code>DataItem.iconPosition</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CIconPosition( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"auto(0), "
      +"left(1), "
      +"right(2), "
      +"leading(3), "
      +"trailing(4), "
      +"top_center(5), "
      +"top_left(6), "
      +"top_right(7), "
      +"bottom_center(8), "
      +"bottom_left(9), "
      +"bottom_right(10), "
      +"right_justified(11) }";
    }
    private final static int    _nchoices[] = {
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
              11
             };


    private final static String _schoices[] = {
              "auto",
              "left",
              "right",
              "leading",
              "trailing",
              "top_center",
              "top_left",
              "top_right",
              "bottom_center",
              "bottom_left",
              "bottom_right",
              "right_justified"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>DataItem.orientation</code> ENUMERATED object
   */
  public static class COrientation extends SPOTEnumerated {

    public final static int auto = 0;
    public final static int horizontal = 1;
    public final static int vertical_up = 2;
    public final static int vertical_down = 3;
    /**
     * Creates a new <code>COrientation</code> object
     */
    public COrientation()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>COrientation</code> object
     * 
     * @param val the value
     */
    public COrientation( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>orientation</code> object
     * the <code>DataItem.orientation</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public COrientation( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"auto(0), "
      +"horizontal(1), "
      +"vertical_up(2), "
      +"vertical_down(3) }";
    }
    private final static int    _nchoices[] = {
              0,
              1,
              2,
              3
             };


    private final static String _schoices[] = {
              "auto",
              "horizontal",
              "vertical_up",
              "vertical_down"
             };

  }
  //}GENERATED_INNER_CLASSES
}
