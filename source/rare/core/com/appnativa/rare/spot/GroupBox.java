/**************************************************************************
 * GroupBox.java - Wed Feb 17 10:42:11 PST 2016
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
 * groups other widgets together
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class GroupBox extends Viewer {
  //GENERATED_MEMBERS{

  /** Hidden: the type of layout to use for the form */
  public CLayout layout = new CLayout(null, null, CLayout.table, "table", false );

  /** Hidden: the number of rows a table layout form will have */
  public SPOTPrintableString rows = new SPOTPrintableString(null, "1", false );

  /** Hidden: the number of columns a table layout form will have */
  public SPOTPrintableString columns = new SPOTPrintableString(null, "1", false );

  /** Hidden: the amount of space between columns */
  public SPOTInteger columnSpacing = new SPOTInteger(null, 0, 24, 4, false);

  /** Hidden: the amount of space between rows */
  public SPOTInteger rowSpacing = new SPOTInteger(null, 0, 24, 2, false);

  /** Hidden: layout specific column grouping information */
  protected SPOTSet columnGrouping = null;

  /** Hidden: layout specific row grouping information */
  protected SPOTSet rowGrouping = null;

  /** Appearance~~reload: painters for the cells in the form grid */
  protected SPOTSet cellPainters = null;

  /** Behavior: value to use when submitting data via a form */
  public CSubmitValue submitValue = new CSubmitValue(null, null, CSubmitValue.widget_values, "widget_values", false );

  /** Hidden: the set of widgets for the group box */
  public SPOTSet widgets = new SPOTSet( "widget", new SPOTAny( "com.appnativa.rare.spot.Widget"), -1, -1, true);

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>GroupBox</code> object.
   */
  public GroupBox()  {
    this(true);
  }

  /**
   * Creates a new <code>GroupBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public GroupBox( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>GroupBox</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected GroupBox( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=11;
    this.attributeSizeHint +=1;
    super.spot_setElements();
    spot_defineAttribute("onItemChanged",null);
    spot_addElement( "layout", layout );
    layout.spot_defineAttribute("layoutManager",null);
    layout.spot_defineAttribute("customInfo",null);
    layout.spot_defineAttribute("panelComponent",null);
    spot_addElement( "rows", rows );
    spot_addElement( "columns", columns );
    spot_addElement( "columnSpacing", columnSpacing );
    spot_addElement( "rowSpacing", rowSpacing );
    spot_addElement( "columnGrouping", columnGrouping );
    spot_addElement( "rowGrouping", rowGrouping );
    spot_addElement( "cellPainters", cellPainters );
    spot_addElement( "submitValue", submitValue );
    spot_addElement( "widgets", widgets );
    spot_addElement( "scrollPane", scrollPane );
  }

  /**
   * Gets the columnGrouping element
   * 
   * @return the columnGrouping element or null if a reference was never created
   */
  public SPOTSet getColumnGrouping() { return columnGrouping; }

  /**
   * Gets the reference to the columnGrouping element
   * A reference is created if necessary
   * 
   * @return the reference to the columnGrouping element
   */
  public SPOTSet getColumnGroupingReference() {
    if ( columnGrouping == null ) {
      columnGrouping = new SPOTSet( "group", new SPOTPrintableString(), -1, -1, true);
      super.spot_setReference( "columnGrouping" , columnGrouping);
    }
    return columnGrouping;
  }

  /**
   * Sets the reference to the columnGrouping element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setColumnGrouping(iSPOTElement reference) throws ClassCastException {
    columnGrouping = (SPOTSet)reference;
    spot_setReference( "columnGrouping" ,reference);
  }

  /**
   * Gets the rowGrouping element
   * 
   * @return the rowGrouping element or null if a reference was never created
   */
  public SPOTSet getRowGrouping() { return rowGrouping; }

  /**
   * Gets the reference to the rowGrouping element
   * A reference is created if necessary
   * 
   * @return the reference to the rowGrouping element
   */
  public SPOTSet getRowGroupingReference() {
    if ( rowGrouping == null ) {
      rowGrouping = new SPOTSet( "group", new SPOTPrintableString(), -1, -1, true);
      super.spot_setReference( "rowGrouping" , rowGrouping);
    }
    return rowGrouping;
  }

  /**
   * Sets the reference to the rowGrouping element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setRowGrouping(iSPOTElement reference) throws ClassCastException {
    rowGrouping = (SPOTSet)reference;
    spot_setReference( "rowGrouping" ,reference);
  }

  /**
   * Gets the cellPainters element
   * 
   * @return the cellPainters element or null if a reference was never created
   */
  public SPOTSet getCellPainters() { return cellPainters; }

  /**
   * Gets the reference to the cellPainters element
   * A reference is created if necessary
   * 
   * @return the reference to the cellPainters element
   */
  public SPOTSet getCellPaintersReference() {
    if ( cellPainters == null ) {
      cellPainters = new SPOTSet( "cell", new GridCell(), -1, -1, true);
      super.spot_setReference( "cellPainters" , cellPainters);
    }
    return cellPainters;
  }

  /**
   * Sets the reference to the cellPainters element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setCellPainters(iSPOTElement reference) throws ClassCastException {
    cellPainters = (SPOTSet)reference;
    spot_setReference( "cellPainters" ,reference);
  }

  /**
   * Gets the scrollPane element
   * 
   * @return the scrollPane element or null if a reference was never created
   */
  public ScrollPane getScrollPane() { return scrollPane; }

  /**
   * Gets the reference to the scrollPane element
   * A reference is created if necessary
   * 
   * @return the reference to the scrollPane element
   */
  public ScrollPane getScrollPaneReference() {
    if ( scrollPane == null ) {
      scrollPane = new ScrollPane(true);
      super.spot_setReference( "scrollPane" , scrollPane);
    }
    return scrollPane;
  }

  /**
   * Sets the reference to the scrollPane element
   * @param reference the reference ( can be null)
   * 
   * @throws ClassCastException if the parameter is not valid
   */
  public void setScrollPane(iSPOTElement reference) throws ClassCastException {
    scrollPane = (ScrollPane)reference;
    spot_setReference( "scrollPane" ,reference);
  }

  //}GENERATED_METHODS
  private java.util.Map<String, Widget> _widgetNameMap;

  /**
   * Finds an returns the named widget (does not recurse)
   *
   * @param name the name of the widget to find
   * @param useNameMap true to use a name map to improve future search performance; false otherwise
   *
   * @return the named widget or null
   */
  @Override
  public Widget findWidget(String name, boolean useNameMap) {
    if (useNameMap && (_widgetNameMap == null)) {
      _widgetNameMap = createWidgetMap();
    }

    if (_widgetNameMap != null) {
      return _widgetNameMap.get(name);
    }

    int    len = widgets.getCount();
    Widget w;

    for (int i = 0; i < len; i++) {
      w = (Widget) widgets.getEx(i);

      if (name.equals(w.name.getValue())) {
        return w;
      }
    }

    for (int i = 0; i < len; i++) {
      w = (Widget) widgets.getEx(i);

      if (w instanceof GroupBox) {
        w = ((GroupBox) w).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (w instanceof SplitPane) {
        w = ((SplitPane) w).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (w instanceof GridPane) {
        w = ((GridPane) w).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      } else if (w instanceof StackPane) {
        w = ((StackPane) w).findWidget(name, useNameMap);

        if (w != null) {
          return w;
        }
      }
    }

    return null;
  }

  /**
   * Finds an returns the named widget (does not recurse)
   *
   * @param name the name of the widget to find
   * @return the named widget or null
   */
  @Override
  public Widget findWidget(String name) {
    return findWidget(name, false);
  }

  /**
   * Creates a map of the named widgets
   *
   * @return  a map of the named widgets or null
   */
  @Override
  public java.util.Map<String, Widget> createWidgetMap() {
    int len = widgets.getCount();

    if (len == 0) {
      return null;
    }

    java.util.HashMap<String, Widget> map = new java.util.HashMap<String, Widget>(len);
    Widget                            w;

    for (int i = 0; i < len; i++) {
      w = (Widget) widgets.getEx(i);

      if (w.name.getValue() != null) {
        map.put(w.name.getValue(), w);
      }
    }

    return map;
  }

  /**
   * Gets the widget at the specified index
   *
   * @param index the index
   * @return the widget
   */
  public Widget getWidget(int index) {
    return (Widget) widgets.getEx(index);
  }

  /**
   * Removes the widget at the specified index
   *
   * @param index the index
   * @return the widget
   */
  public Widget removeWidgetAt(int index) {
    Widget w = (Widget) widgets.getEx(index);

    if (w != null) {
      widgets.remove(index);
    }

    return w;
  }

  /**
   * Gets the number of widgets
   *
   * @return  the number of widgets
   */
  public int getWidgetCount() {
    return widgets.getCount();
  }

  /**
   * Finds an returns the widget at the specified x,y position (does not recurse)
   *
   * @param x the x-position
   * @param y the y-position
   * @return the widget at the specified x,y position or null
   */
  public Widget findWidget(int x, int y) {
    int    len = widgets.getCount();
    Widget w;

    for (int i = 0; i < len; i++) {
      w = (Widget) widgets.getEx(i);

      if ((w.bounds.getX() == x) && (w.bounds.getY() == y)) {
        return w;
      }
    }

    return null;
  }

  /**
   * Adds the specifeid widget to the widget set
   *
   * @param w the widget to add
   */
  public void addWidget(Widget w) {
    widgets.add(w);
  }

  /**
   * Removes the specifeid widget from the widget set
   *
   * @param w the widget to remove
   */
  public void removeWidget(Widget w) {
    widgets.removeEx(w);
  }

  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>GroupBox.layout</code> ENUMERATED object
   */
  public static class CLayout extends SPOTEnumerated {

    /** use absolute screen coordinates for x, y */
    public final static int absolute = 1;
    /** use column, row for x, y positions */
    public final static int table = 2;
    /** use JGoodies forms layout */
    public final static int forms = 3;
    /** use a flow layout */
    public final static int flow = 4;
    /** use a custom layout manager */
    public final static int custom = 10;
    /**
     * Creates a new <code>CLayout</code> object
     */
    public CLayout()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CLayout</code> object
     * 
     * @param val the value
     */
    public CLayout( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>layout</code> object
     * the <code>GroupBox.layout</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CLayout( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
    {
      super(ival,sval,idefaultval,sdefaultval,optional);
      _sChoices=_schoices;
      _nChoices=_nchoices;
      spot_setAttributes();
    }

    /**
     * Retrieves the range of valid values for the object
     * 
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange()
    {
      return "{"
      +"absolute(1), "
      +"table(2), "
      +"forms(3), "
      +"flow(4), "
      +"custom(10) }";
    }

    private void spot_setAttributes()
    {
      this.attributeSizeHint +=3;
      spot_defineAttribute("layoutManager",null);
      spot_defineAttribute("customInfo",null);
      spot_defineAttribute("panelComponent",null);
    }
    private final static int    _nchoices[] = {
              1,
              2,
              3,
              4,
              10
             };


    private final static String _schoices[] = {
              "absolute",
              "table",
              "forms",
              "flow",
              "custom"
             };

  }
  /**
   * Class that defines the valid set of choices for
   * the <code>GroupBox.submitValue</code> ENUMERATED object
   */
  public static class CSubmitValue extends SPOTEnumerated {

    /** submit the individual widget values */
    public final static int widget_values = 0;
    /** submit the linked data for the viewer */
    public final static int viewer_linked_data = 1;
    /** submit the value of the viewer */
    public final static int viewer_value = 2;
    /**
     * Creates a new <code>CSubmitValue</code> object
     */
    public CSubmitValue()
    {
      this(null,null,null,null,true);
    }

    /**
     * Creates a new <code>CSubmitValue</code> object
     * 
     * @param val the value
     */
    public CSubmitValue( int val )
    {
      super();
      _sChoices=_schoices;
      _nChoices=_nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>submitValue</code> object
     * the <code>GroupBox.submitValue</code> ENUMERATED object
     * 
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CSubmitValue( Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional)
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
      +"widget_values(0), "
      +"viewer_linked_data(1), "
      +"viewer_value(2) }";
    }
    private final static int    _nchoices[] = {
              0,
              1,
              2
             };


    private final static String _schoices[] = {
              "widget_values",
              "viewer_linked_data",
              "viewer_value"
             };

  }
  //}GENERATED_INNER_CLASSES
}
