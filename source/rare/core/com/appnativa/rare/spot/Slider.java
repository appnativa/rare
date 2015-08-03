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
 * This class represents the configuration for a widget that
 * allows an integer value to be selected for a fixed range
 * using a slider
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Slider extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the value for the slider */
  public SPOTReal value = new SPOTReal(null, "0", false);
//GENERATED_COMMENT{}

  /** Behavior~spinner: minimum value for the slider */
  public SPOTReal minValue = new SPOTReal(null, "0", false);
//GENERATED_COMMENT{}

  /** Behavior~spinner: maximum value for the slider */
  public SPOTReal maxValue = new SPOTReal(null, "100", false);
//GENERATED_COMMENT{}

  /** Appearance~~orientation: whether the slider is to be horizontally oriented */
  public SPOTBoolean horizontal = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior: whether the slider should snap to tick marks */
  public SPOTBoolean snapToTicks = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~spinner: the amount of space between minor ticks */
  public SPOTReal minorTickSpacing = new SPOTReal(null, "0", null, "0", false);
//GENERATED_COMMENT{}

  /** Appearance~spinner: the amount of space between major ticks */
  public SPOTReal majorTickSpacing = new SPOTReal(null, "0", null, "10", false);
//GENERATED_COMMENT{}

  /** Appearance: whether labels should be shown for the tick marks */
  public SPOTBoolean showLabels = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the tick marks should be shown */
  public SPOTBoolean showTicks = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance: whether the track should be shown */
  public SPOTBoolean showTrack = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Appearance~~leftText: label for the left/top side of the slider */
  public SPOTPrintableString leftLabel = new SPOTPrintableString(null, 0, 80, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for the left/top side of the slider */
  public SPOTPrintableString leftIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~~rightText: label for the right/bottom side of the slider */
  public SPOTPrintableString rightLabel = new SPOTPrintableString(null, 0, 80, true);
//GENERATED_COMMENT{}

  /** Appearance~icon: url to use to retrieve an icon for ththe right/bottom side of the slider */
  public SPOTPrintableString rightIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~icon~reload: url to use to retrieve an icon for the label */
  public SPOTPrintableString thumbIcon = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Appearance~~reload:whether thicks shoud be painted inside the track */
  public SPOTBoolean paintInnerTicks = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Appearance~~reload:a painter for the track */
  protected GridCell trackPainter = null;
//GENERATED_COMMENT{}

  /** Appearance~spinner~reload: the amount to offset to slider when there are left/right labels or icons */
  public SPOTInteger sliderOffset = new SPOTInteger(null, 0, false);
//GENERATED_COMMENT{}

  /** Appearance~~labels: custom labels for the tick marks */
  protected SPOTSet tickLabels = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Slider</code> object.
   */
  public Slider() {
    this(true);
  }

  /**
   * Creates a new <code>Slider</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Slider(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Slider</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Slider(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 19;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("value", value);
    spot_addElement("minValue", minValue);
    spot_addElement("maxValue", maxValue);
    spot_addElement("horizontal", horizontal);
    spot_addElement("snapToTicks", snapToTicks);
    spot_addElement("minorTickSpacing", minorTickSpacing);
    spot_addElement("majorTickSpacing", majorTickSpacing);
    spot_addElement("showLabels", showLabels);
    spot_addElement("showTicks", showTicks);
    spot_addElement("showTrack", showTrack);
    spot_addElement("leftLabel", leftLabel);
    spot_addElement("leftIcon", leftIcon);
    leftIcon.spot_defineAttribute("alt", null);
    leftIcon.spot_defineAttribute("slice", null);
    leftIcon.spot_defineAttribute("size", null);
    leftIcon.spot_defineAttribute("scaling", null);
    leftIcon.spot_defineAttribute("density", null);
    spot_addElement("rightLabel", rightLabel);
    spot_addElement("rightIcon", rightIcon);
    rightIcon.spot_defineAttribute("alt", null);
    rightIcon.spot_defineAttribute("slice", null);
    rightIcon.spot_defineAttribute("size", null);
    rightIcon.spot_defineAttribute("scaling", null);
    rightIcon.spot_defineAttribute("density", null);
    spot_addElement("thumbIcon", thumbIcon);
    thumbIcon.spot_defineAttribute("alt", null);
    thumbIcon.spot_defineAttribute("slice", null);
    thumbIcon.spot_defineAttribute("size", null);
    thumbIcon.spot_defineAttribute("scaling", null);
    thumbIcon.spot_defineAttribute("density", null);
    spot_addElement("paintInnerTicks", paintInnerTicks);
    paintInnerTicks.spot_defineAttribute("tickType", "minor");
    paintInnerTicks.spot_defineAttribute("offset", null);
    paintInnerTicks.spot_defineAttribute("color", null);
    paintInnerTicks.spot_defineAttribute("tickStyle", "line");
    paintInnerTicks.spot_defineAttribute("paintArea", "value");
    spot_addElement("trackPainter", trackPainter);
    spot_addElement("sliderOffset", sliderOffset);
    spot_addElement("tickLabels", tickLabels);
  }

  /**
   * Gets the trackPainter element
   *
   * @return the trackPainter element or null if a reference was never created
   */
  public GridCell getTrackPainter() {
    return trackPainter;
  }

  /**
   * Gets the reference to the trackPainter element
   * A reference is created if necessary
   *
   * @return the reference to the trackPainter element
   */
  public GridCell getTrackPainterReference() {
    if (trackPainter == null) {
      trackPainter = new GridCell(true);
      super.spot_setReference("trackPainter", trackPainter);
    }

    return trackPainter;
  }

  /**
   * Sets the reference to the trackPainter element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTrackPainter(iSPOTElement reference) throws ClassCastException {
    trackPainter = (GridCell) reference;
    spot_setReference("trackPainter", reference);
  }

  /**
   * Gets the tickLabels element
   *
   * @return the tickLabels element or null if a reference was never created
   */
  public SPOTSet getTickLabels() {
    return tickLabels;
  }

  /**
   * Gets the reference to the tickLabels element
   * A reference is created if necessary
   *
   * @return the reference to the tickLabels element
   */
  public SPOTSet getTickLabelsReference() {
    if (tickLabels == null) {
      tickLabels = new SPOTSet("label", new SPOTPrintableString(null, 0, 80, false), -1, -1, true);
      super.spot_setReference("tickLabels", tickLabels);
    }

    return tickLabels;
  }

  /**
   * Sets the reference to the tickLabels element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setTickLabels(iSPOTElement reference) throws ClassCastException {
    tickLabels = (SPOTSet) reference;
    spot_setReference("tickLabels", reference);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}