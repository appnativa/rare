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

import com.appnativa.spot.*;

//USER_IMPORTS_AND_COMMENTS_MARK{}
//GENERATED_COMMENT{}

/**
 * The class represents a viewer the presents a sets of items
 * <p>
 * as a carousel
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Carousel extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: the type of data for the carousel */
  public CDataType dataType = new CDataType(null, null, CDataType.data_items, "data_items", false);
//GENERATED_COMMENT{}

  /** Design: whether the linked data for an item is used to populate the viewer */
  public SPOTBoolean useLinkedData = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the aspect ratio of images are preserved */
  public SPOTBoolean preserveAspectRatio = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: the maximum number of side items to display */
  public SPOTInteger maxSideItems = new SPOTInteger();
//GENERATED_COMMENT{}

  /** Appearance: the opacity of the scroll bar */
  public SPOTReal scrollbarOpacity = new SPOTReal(null, "0", "1", "1", false);
//GENERATED_COMMENT{}

  /** Appearance: whether to displa the images flat (instead of using a perspective) */
  public SPOTBoolean flatList = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the gap between images */
  public SPOTInteger imageGap = new SPOTInteger(null, 0, false);
//GENERATED_COMMENT{}

  /** Appearance: whether to use animation when selecting */
  public SPOTBoolean animateSelection = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: the fraction of the size to use when creating the prespective transform */
  public SPOTReal perspectiveFraction = new SPOTReal(null, "0", "1", true);
//GENERATED_COMMENT{}

  /** Appearance: the fraction of the height to use when creating the reflection */
  public SPOTReal reflectionFraction = new SPOTReal(null, "0", "1", true);
//GENERATED_COMMENT{}

  /** Appearance: the fraction of the height to use for the selected item */
  public SPOTReal selectedFraction = new SPOTReal(null, "0", "1", "1", false);
//GENERATED_COMMENT{}

  /** Appearance: the fraction of the height of the selected item to use for side items */
  public SPOTReal sideFraction = new SPOTReal(null, "0", "1", ".5", false);
//GENERATED_COMMENT{}

  /** Design: whether strings representing urls in data items need to be encoded */
  public SPOTBoolean encodeParsedURLs = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the title of the selected item is rendered */
  public SPOTBoolean renderTitles = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the title of the items right next to the selected item are rendered */
  public SPOTBoolean renderSecondaryTitles = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: the size of the cache to use for caching images */
  public SPOTInteger cacheSize = new SPOTInteger(null, 0, false);
//GENERATED_COMMENT{}

  /** Appearance: the margin betwqeen the items and the edge of the carousel area */
  protected Margin areaMargin = null;
//GENERATED_COMMENT{}

  /** Behavior: the type of scaling to perform */
  public CScaling scaling = new CScaling(null, null, CScaling.bilinear, "bilinear", false);
//GENERATED_COMMENT{}

  /** Appearance: cell to use to paint the selection */
  protected GridCell selectionCell = null;
//GENERATED_COMMENT{}

  /** Appearance: cell to use to paint items */
  protected GridCell itemCell = null;
//GENERATED_COMMENT{}

  /** Design: an optional description of the items that will populate the carousel */
  protected ItemDescription itemDescription = null;
//GENERATED_COMMENT{}

  /** Design: whether a special loader is used to load images */
  public SPOTBoolean useSeparateLoader = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design~url: the icon to use when image loading is deferred for this widget */
  public SPOTPrintableString deferredImageIcon = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Carousel</code> object.
   */
  public Carousel() {
    this(true);
  }

  /**
   * Creates a new <code>Carousel</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Carousel(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Carousel</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Carousel(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 23;
    this.attributeSizeHint += 2;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onAction", null);
    spot_addElement("dataType", dataType);
    dataType.spot_defineAttribute("usesImagesAlways", null);
    spot_addElement("useLinkedData", useLinkedData);
    spot_addElement("preserveAspectRatio", preserveAspectRatio);
    spot_addElement("maxSideItems", maxSideItems);
    spot_addElement("scrollbarOpacity", scrollbarOpacity);
    spot_addElement("flatList", flatList);
    flatList.spot_defineAttribute("gap", null);
    spot_addElement("imageGap", imageGap);
    spot_addElement("animateSelection", animateSelection);
    spot_addElement("perspectiveFraction", perspectiveFraction);
    spot_addElement("reflectionFraction", reflectionFraction);
    spot_addElement("selectedFraction", selectedFraction);
    spot_addElement("sideFraction", sideFraction);
    spot_addElement("encodeParsedURLs", encodeParsedURLs);
    spot_addElement("renderTitles", renderTitles);
    spot_addElement("renderSecondaryTitles", renderSecondaryTitles);
    spot_addElement("cacheSize", cacheSize);
    cacheSize.spot_defineAttribute("strongReference", "true");
    spot_addElement("areaMargin", areaMargin);
    spot_addElement("scaling", scaling);
    spot_addElement("selectionCell", selectionCell);
    spot_addElement("itemCell", itemCell);
    spot_addElement("itemDescription", itemDescription);
    spot_addElement("useSeparateLoader", useSeparateLoader);
    useSeparateLoader.spot_defineAttribute("maxThreads", null);
    spot_addElement("deferredImageIcon", deferredImageIcon);
    deferredImageIcon.spot_defineAttribute("alt", null);
    deferredImageIcon.spot_defineAttribute("slice", null);
    deferredImageIcon.spot_defineAttribute("size", null);
    deferredImageIcon.spot_defineAttribute("scaling", null);
    deferredImageIcon.spot_defineAttribute("density", null);
  }

  /**
   * Gets the areaMargin element
   *
   * @return the areaMargin element or null if a reference was never created
   */
  public Margin getAreaMargin() {
    return areaMargin;
  }

  /**
   * Gets the reference to the areaMargin element
   * A reference is created if necessary
   *
   * @return the reference to the areaMargin element
   */
  public Margin getAreaMarginReference() {
    if (areaMargin == null) {
      areaMargin = new Margin(true);
      super.spot_setReference("areaMargin", areaMargin);
    }

    return areaMargin;
  }

  /**
   * Sets the reference to the areaMargin element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setAreaMargin(iSPOTElement reference) throws ClassCastException {
    areaMargin = (Margin) reference;
    spot_setReference("areaMargin", reference);
  }

  /**
   * Gets the selectionCell element
   *
   * @return the selectionCell element or null if a reference was never created
   */
  public GridCell getSelectionCell() {
    return selectionCell;
  }

  /**
   * Gets the reference to the selectionCell element
   * A reference is created if necessary
   *
   * @return the reference to the selectionCell element
   */
  public GridCell getSelectionCellReference() {
    if (selectionCell == null) {
      selectionCell = new GridCell(true);
      super.spot_setReference("selectionCell", selectionCell);
    }

    return selectionCell;
  }

  /**
   * Sets the reference to the selectionCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setSelectionCell(iSPOTElement reference) throws ClassCastException {
    selectionCell = (GridCell) reference;
    spot_setReference("selectionCell", reference);
  }

  /**
   * Gets the itemCell element
   *
   * @return the itemCell element or null if a reference was never created
   */
  public GridCell getItemCell() {
    return itemCell;
  }

  /**
   * Gets the reference to the itemCell element
   * A reference is created if necessary
   *
   * @return the reference to the itemCell element
   */
  public GridCell getItemCellReference() {
    if (itemCell == null) {
      itemCell = new GridCell(true);
      super.spot_setReference("itemCell", itemCell);
    }

    return itemCell;
  }

  /**
   * Sets the reference to the itemCell element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setItemCell(iSPOTElement reference) throws ClassCastException {
    itemCell = (GridCell) reference;
    spot_setReference("itemCell", reference);
  }

  /**
   * Gets the itemDescription element
   *
   * @return the itemDescription element or null if a reference was never created
   */
  public ItemDescription getItemDescription() {
    return itemDescription;
  }

  /**
   * Gets the reference to the itemDescription element
   * A reference is created if necessary
   *
   * @return the reference to the itemDescription element
   */
  public ItemDescription getItemDescriptionReference() {
    if (itemDescription == null) {
      itemDescription = new ItemDescription(true);
      super.spot_setReference("itemDescription", itemDescription);
    }

    return itemDescription;
  }

  /**
   * Sets the reference to the itemDescription element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setItemDescription(iSPOTElement reference) throws ClassCastException {
    itemDescription = (ItemDescription) reference;
    spot_setReference("itemDescription", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>Carousel.dataType</code> ENUMERATED object
   */
  public static class CDataType extends SPOTEnumerated {
    public final static int data_items  = 0;
    public final static int image_urls  = 1;
    public final static int widget_urls = 2;

    /**
     * Creates a new <code>CDataType</code> object
     */
    public CDataType() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CDataType</code> object
     *
     * @param val the value
     */
    public CDataType(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
      setValue(val);
    }

    /**
     * Creates a new <code>dataType</code> object
     * the <code>Carousel.dataType</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CDataType(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
      spot_setAttributes();
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "data_items(0), " + "image_urls(1), " + "widget_urls(2) }";
    }

    private void spot_setAttributes() {
      this.attributeSizeHint += 1;
      spot_defineAttribute("usesImagesAlways", null);
    }

    private final static int    _nchoices[] = { 0, 1, 2 };
    private final static String _schoices[] = { "data_items", "image_urls", "widget_urls" };
  }


  /**
   * Class that defines the valid set of choices for
   * the <code>Carousel.scaling</code> ENUMERATED object
   */
  public static class CScaling extends SPOTEnumerated {
    public final static int nearest_neighbor            = 0;
    public final static int bilinear                    = 1;
    public final static int bicubic                     = 2;
    public final static int bilinear_cached             = 3;
    public final static int bicubic_cached              = 4;
    public final static int progressive_bilinear        = 5;
    public final static int progressive_bicubic         = 6;
    public final static int progressive_bilinear_cached = 7;
    public final static int progressive_bicubic_cached  = 8;

    /**
     * Creates a new <code>CScaling</code> object
     */
    public CScaling() {
      this(null, null, null, null, true);
    }

    /**
     * Creates a new <code>CScaling</code> object
     *
     * @param val the value
     */
    public CScaling(int val) {
      super();
      _sChoices = _schoices;
      _nChoices = _nchoices;
      setValue(val);
    }

    /**
     * Creates a new <code>scaling</code> object
     * the <code>Carousel.scaling</code> ENUMERATED object
     *
     * @param ival         the integer value
     * @param sval         the string  value
     * @param idefaultval  the integer default value
     * @param sdefaultval  the string default value
     * @param optional   <code>true</code> if the node the object represents is optional
     */
    public CScaling(Integer ival, String sval, Integer idefaultval, String sdefaultval, boolean optional) {
      super(ival, sval, idefaultval, sdefaultval, optional);
      _sChoices = _schoices;
      _nChoices = _nchoices;
    }

    /**
     * Retrieves the range of valid values for the object
     *
     * @return the valid range as a displayable string
     */
    public String spot_getValidityRange() {
      return "{" + "nearest_neighbor(0), " + "bilinear(1), " + "bicubic(2), " + "bilinear_cached(3), "
             + "bicubic_cached(4), " + "progressive_bilinear(5), " + "progressive_bicubic(6), "
             + "progressive_bilinear_cached(7), " + "progressive_bicubic_cached(8) }";
    }

    private final static int    _nchoices[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8
    };
    private final static String _schoices[] = {
      "nearest_neighbor", "bilinear", "bicubic", "bilinear_cached", "bicubic_cached", "progressive_bilinear",
      "progressive_bicubic", "progressive_bilinear_cached", "progressive_bicubic_cached"
    };
  }
  //}GENERATED_INNER_CLASSES
}
