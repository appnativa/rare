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
 * This class represents the configuration information for a
 * viewer that displays and image and provides tools for
 * manipulating the image
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class ImagePane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior: whether image zooming should be allowed */
  public SPOTBoolean zoomingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether image zooming via a the mouse scroll wheel should be allowed */
  public SPOTBoolean scrollWheelZoomingAllowed = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether image moving should be allowed */
  public SPOTBoolean movingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether image rotating should be allowed */
  public SPOTBoolean rotatingAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: the type of scaling to perform */
  public CScaling scaling = new CScaling(null, null, CScaling.bilinear, "bilinear", false);
//GENERATED_COMMENT{}

  /** Behavior: whether the image should be automatically scaled to fit the size of the pane (if enabled zooming/paning/moving will be disabled) */
  public SPOTBoolean autoScale = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the image should be automatically centered when first displayed */
  public SPOTBoolean centerImage = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Design: whether the aspect ratio of images are preserved */
  public SPOTBoolean preserveAspectRatio = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the user can select parts of the image (for image types that support it) */
  public SPOTBoolean userSelectionAllowed = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the loading status of the image should be shown */
  public SPOTBoolean showLoadStatus = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Appearance~font~reload: text to show when the widget is empty */
  protected EmptyText emptyText = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>ImagePane</code> object.
   */
  public ImagePane() {
    this(true);
  }

  /**
   * Creates a new <code>ImagePane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public ImagePane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>ImagePane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected ImagePane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 12;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("zoomingAllowed", zoomingAllowed);
    zoomingAllowed.spot_defineAttribute("minimum", "10%");
    zoomingAllowed.spot_defineAttribute("maximum", "200%");
    zoomingAllowed.spot_defineAttribute("increment", "10%");
    spot_addElement("scrollWheelZoomingAllowed", scrollWheelZoomingAllowed);
    spot_addElement("movingAllowed", movingAllowed);
    spot_addElement("rotatingAllowed", rotatingAllowed);
    spot_addElement("scaling", scaling);
    spot_addElement("autoScale", autoScale);
    spot_addElement("centerImage", centerImage);
    spot_addElement("preserveAspectRatio", preserveAspectRatio);
    preserveAspectRatio.spot_defineAttribute("fill", null);
    spot_addElement("userSelectionAllowed", userSelectionAllowed);
    userSelectionAllowed.spot_defineAttribute("selectionColor", null);
    userSelectionAllowed.spot_defineAttribute("lineThickness", "1.5");
    userSelectionAllowed.spot_defineAttribute("strokeType", "dotted");
    spot_addElement("showLoadStatus", showLoadStatus);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("emptyText", emptyText);
  }

  /**
   * Gets the scrollPane element
   *
   * @return the scrollPane element or null if a reference was never created
   */
  public ScrollPane getScrollPane() {
    return scrollPane;
  }

  /**
   * Gets the reference to the scrollPane element
   * A reference is created if necessary
   *
   * @return the reference to the scrollPane element
   */
  public ScrollPane getScrollPaneReference() {
    if (scrollPane == null) {
      scrollPane = new ScrollPane(true);
      super.spot_setReference("scrollPane", scrollPane);
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
    scrollPane = (ScrollPane) reference;
    spot_setReference("scrollPane", reference);
  }

  /**
   * Gets the emptyText element
   *
   * @return the emptyText element or null if a reference was never created
   */
  public EmptyText getEmptyText() {
    return emptyText;
  }

  /**
   * Gets the reference to the emptyText element
   * A reference is created if necessary
   *
   * @return the reference to the emptyText element
   */
  public EmptyText getEmptyTextReference() {
    if (emptyText == null) {
      emptyText = new EmptyText(true);
      super.spot_setReference("emptyText", emptyText);
    }

    return emptyText;
  }

  /**
   * Sets the reference to the emptyText element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setEmptyText(iSPOTElement reference) throws ClassCastException {
    emptyText = (EmptyText) reference;
    spot_setReference("emptyText", reference);
  }

  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{

  /**
   * Class that defines the valid set of choices for
   * the <code>ImagePane.scaling</code> ENUMERATED object
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
     * the <code>ImagePane.scaling</code> ENUMERATED object
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