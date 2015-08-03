/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.spot;

import com.appnativa.spot.SPOTBoolean;
import com.appnativa.spot.SPOTEnumerated;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * This class represents configuration information for a widget
 * that displays and manages multiple rows of name and value
 * properties
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class PropertyTable extends TreeTable {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance~~reload: whether the table should be enclosed in a pane that contains controls for sorting and a description ares */
  public SPOTBoolean usePane = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload: actions for the property pane toolbar */
  protected SPOTSet paneToolbarActions = null;
//GENERATED_COMMENT{}

  /** Design~~reload: the initial order for properties */
  public CPropertiesOrder propertiesOrder = new CPropertiesOrder(null, null, CPropertiesOrder.categorized,
                                              "categorized", false);
//GENERATED_COMMENT{}

  /** Design~~reload: the sort order for categories. 0 means unsorted 1 means ascending and -1 means descending */
  public CCategorySortOrder categorySortOrder = new CCategorySortOrder(null, null, CCategorySortOrder.ascending,
                                                  "ascending", false);
//GENERATED_COMMENT{}

  /** Design: whether the margin background should be painted */
  public SPOTBoolean paintMargin = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: the name of the miscellaneous category (the category for items with no explicit category) */
  public SPOTPrintableString miscCategoryName = new SPOTPrintableString(null, 0, 32, true);
//GENERATED_COMMENT{}

  /** Design~~reload: a semi-colon separated list of categories to automatically expand */
  public SPOTPrintableString expandedCategories = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>PropertyTable</code> object.
   */
  public PropertyTable() {
    this(true);
  }

  /**
   * Creates a new <code>PropertyTable</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public PropertyTable(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>PropertyTable</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected PropertyTable(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  @Override
  protected void spot_setElements() {
    this.elementsSizeHint += 7;
    super.spot_setElements();
    spot_addElement("usePane", usePane);
    usePane.spot_defineAttribute("tableBackground", null);
    usePane.spot_defineAttribute("tableBorder", null);
    usePane.spot_defineAttribute("displayAreaBorder", null);
    usePane.spot_defineAttribute("displayAreaBackground", null);
    usePane.spot_defineAttribute("toolbarBackground", null);
    usePane.spot_defineAttribute("toolbarBorder", null);
    spot_addElement("paneToolbarActions", paneToolbarActions);
    spot_addElement("propertiesOrder", propertiesOrder);
    spot_addElement("categorySortOrder", categorySortOrder);
    spot_addElement("paintMargin", paintMargin);
    paintMargin.spot_defineAttribute("color", null);
    spot_addElement("miscCategoryName", miscCategoryName);
    spot_addElement("expandedCategories", expandedCategories);
  }

  /**
   * Gets the paneToolbarActions element
   *
   * @return the paneToolbarActions element or null if a reference was never created
   */
  public SPOTSet getPaneToolbarActions() {
    return paneToolbarActions;
  }

  /**
   * Gets the reference to the paneToolbarActions element
   * A reference is created if necessary
   *
   * @return the reference to the paneToolbarActions element
   */
  public SPOTSet getPaneToolbarActionsReference() {
    if (paneToolbarActions == null) {
      paneToolbarActions = new SPOTSet("action", new ActionItem(), -1, -1, true);
      super.spot_setReference("paneToolbarActions", paneToolbarActions);
    }

    return paneToolbarActions;
  }

  /**
   * Sets the reference to the paneToolbarActions element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setPaneToolbarActions(iSPOTElement reference) throws ClassCastException {
    paneToolbarActions = (SPOTSet) reference;
    spot_setReference("paneToolbarActions", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>PropertyTable.propertiesOrder</code> ENUMERATED object
   */
  public static class CPropertiesOrder extends SPOTEnumerated {
    public final static int unsorted    = 0;
    public final static int sorted      = 1;
    public final static int categorized = 2;

    /**
     * Creates a new <code>CPropertiesOrder</code> object
     */
    public CPropertiesOrder() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CPropertiesOrder</code> object
     *
     * @param val the value
     */
    public CPropertiesOrder(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>propertiesOrder</code> object
     * the <code>PropertyTable.propertiesOrder</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CPropertiesOrder(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    @Override
    public String spot_getValidityRange() {
      return "{" + "unsorted(0), " + "sorted(1), " + "categorized(2) }";
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "unsorted", "sorted", "categorized" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>PropertyTable.categorySortOrder</code> ENUMERATED object
   */
  public static class CCategorySortOrder extends SPOTEnumerated {
    public final static int descending = -1;
    public final static int unsorted   = 0;
    public final static int ascending  = 1;

    /**
     * Creates a new <code>CCategorySortOrder</code> object
     */
    public CCategorySortOrder() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CCategorySortOrder</code> object
     *
     * @param val the value
     */
    public CCategorySortOrder(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>categorySortOrder</code> object
     * the <code>PropertyTable.categorySortOrder</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CCategorySortOrder(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    @Override
    public String spot_getValidityRange() {
      return "{" + "descending(-1), " + "unsorted(0), " + "ascending(1) }";
    }

    private final static int    _nchoices[] = { -1, 0, 1 };
    private final static String _schoices[] = { "descending", "unsorted", "ascending" };
  }
  //}GENERATED_INNER_CLASSES
}
