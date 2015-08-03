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

//USER_IMPORTS_AND_COMMENTS_MARK{}
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.spot.*;
import com.appnativa.util.SNumber;

//GENERATED_COMMENT{}

/**
 * This class represents the configuration information of a
 * rectangular view area.
 * <p>
 * The elements are strings in order to allow units to be
 * specified.
 * </p>
 * <p>
 * If no units are specified then absolute device pixels are
 * assumed
 * </p>
 * <p>
 * The supported units are:<ul>
 * </p>
 * <p>
 * <li>px - for 96 dpi-based relative pixels</li>
 * </p>
 * <p>
 * <li>pt - for point</li>
 * </p>
 * <p>
 * <li>pc - for pica</li>
 * </p>
 * <p>
 * <li>in - for inch</li>
 * </p>
 * <p>
 * <li>cm - for centimeter </li>
 * </p>
 * <p>
 * <li>mm - for millimeter</li>
 * </p>
 * <p>
 * <li>ch - for character (will use the size of the 'W'
 * character </li>
 * </p>
 * <p>
 * <li>ln - for line (will use the line height of the
 * font)</li>
 * </p>
 * <p>
 * <li>ex - same as 'ln' (should behave the same as in
 * CSS)</li>
 * </p>
 * <p>
 * <li>em - equivalent to %/100 (should behave the same as in
 * CSS)</li>
 * </p>
 * <p>
 * <li>% - for percent</li>
 * </p>
 * <p>
 * </ul>
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Rectangle extends SPOTSequence {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Layout: the x-position (the default of minus one (-1) tells the viewer decide */
  public SPOTPrintableString x = new SPOTPrintableString(null, 0, 32, "-1", false);
//GENERATED_COMMENT{}

  /** Layout: the y-position (the default of minus one (-1) tells the viewer decide */
  public SPOTPrintableString y = new SPOTPrintableString(null, 0, 32, "-1", false);
//GENERATED_COMMENT{}

  /** Layout: the width (the default of minus one (-1) tells the viewer decide */
  public SPOTPrintableString width = new SPOTPrintableString(null, 0, 32, "-1", false);
//GENERATED_COMMENT{}

  /** Layout: the height (the default of minus one (-1) tells the viewer decide */
  public SPOTPrintableString height = new SPOTPrintableString(null, 0, 32, "-1", false);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Rectangle</code> object.
   */
  public Rectangle() {
    this(true);
  }

  /**
   * Creates a new <code>Rectangle</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Rectangle(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Rectangle</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Rectangle(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 4;
    super.spot_setElements();
    spot_addElement("x", x);
    spot_addElement("y", y);
    spot_addElement("width", width);
    width.spot_defineAttribute("min", null);
    spot_addElement("height", height);
    height.spot_defineAttribute("min", null);
  }

  //}GENERATED_METHODS
  public void setValues(int x, int y, int width, int height) {
    this.x.setValue(x);
    this.y.setValue(y);
    this.width.setValue(width);
    this.height.setValue(height);
  }

  public UIRectangle getRectangle() {
    return new UIRectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue());
  }

  public void setLocation(int x, int y) {
    this.x.setValue(x);
    this.y.setValue(y);
  }

  public void setSize(int width, int height) {
    this.width.setValue(width);
    this.height.setValue(height);
  }

  public void setValues(String x, String y, String width, String height) {
    this.x.setValue(x);
    this.y.setValue(y);
    this.width.setValue(width);
    this.height.setValue(height);
  }

  public void setValues(UIRectangle rect) {
    this.x.setValue(rect.x);
    this.y.setValue(rect.y);
    this.width.setValue(rect.width);
    this.height.setValue(rect.height);
  }

  public void setValues(Rectangle rect) {
    this.x.setValue(rect.x);
    this.y.setValue(rect.y);
    this.width.setValue(rect.width);
    this.height.setValue(rect.height);
  }

  public void setLocation(String x, String y) {
    this.x.setValue(x);
    this.y.setValue(y);
  }

  public void setSize(String width, String height) {
    this.width.setValue(width);
    this.height.setValue(height);
  }

  public int getXPixels() {
    if (x.spot_valueWasSet()) {
      String           s    = x.getValue();
      float            n    = SNumber.floatValue(s);
      ScreenUtils.Unit unit = ScreenUtils.getUnit(s);

      if (unit != null) {
        n = ScreenUtils.toPlatformPixels(n, unit, true);
      }

      return (int) n;
    } else {
      return -1;
    }
  }

  public int getYPixels() {
    if (x.spot_valueWasSet()) {
      String           s    = y.getValue();
      float            n    = SNumber.floatValue(s);
      ScreenUtils.Unit unit = ScreenUtils.getUnit(s);

      if (unit != null) {
        n = ScreenUtils.toPlatformPixels(n, unit, false);
      }

      return (int) n;
    } else {
      return -1;
    }
  }

  public int getWidthPixels(iPlatformComponent c) {
    return getWidthPixels(c, false);
  }

  public int getHeightPixels(iPlatformComponent c) {
    return getHeightPixels(c, false);
  }

  public int getWidthMinPixels(iPlatformComponent c, boolean charAdjust) {
    String s = width.spot_getAttribute("min");

    return (s == null)
           ? -1
           : ScreenUtils.toPlatformPixelWidth(s, c, -1, charAdjust);
  }

  public int getHeightMinPixels(iPlatformComponent c, boolean charAdjust) {
    String s = height.spot_getAttribute("min");

    return (s == null)
           ? -1
           : ScreenUtils.toPlatformPixelWidth(s, c, -1, charAdjust);
  }

  public int getWidthPixels(iPlatformComponent c, boolean charAdjust) {
    if (width.spot_valueWasSet()) {
      return ScreenUtils.toPlatformPixelWidth(width.getValue(), c, -1, charAdjust);
    } else {
      return -1;
    }
  }

  public int getHeightPixels(iPlatformComponent c, boolean charAdjust) {
    if (height.spot_valueWasSet()) {
      return ScreenUtils.toPlatformPixelHeight(height.getValue(), c, -1, charAdjust);
    } else {
      return -1;
    }
  }

  public boolean isHeightInLines() {
    return height.spot_valueWasSet() && (ScreenUtils.getUnit(height.getValue()) == ScreenUtils.Unit.LINES);
  }

  public boolean isWidthInCharacters() {
    return width.spot_valueWasSet() && (ScreenUtils.getUnit(width.getValue()) == ScreenUtils.Unit.CHARACTERS);
  }

  public int getX() {
    if (x.spot_valueWasSet()) {
      return SNumber.intValue(x.getValue());
    } else {
      return -1;
    }
  }

  public int getY() {
    if (y.spot_valueWasSet()) {
      return SNumber.intValue(y.getValue());
    } else {
      return -1;
    }
  }

  public int getWidth() {
    if (width.spot_valueWasSet()) {
      return SNumber.intValue(width.getValue());
    } else {
      return -1;
    }
  }

  public int getHeight() {
    if (height.spot_valueWasSet()) {
      return SNumber.intValue(height.getValue());
    } else {
      return -1;
    }
  }

  public ScreenUtils.Unit getXUnit() {
    ScreenUtils.Unit unit = ScreenUtils.getUnit(x.getValue());

    return (unit == null)
           ? ScreenUtils.Unit.PIXELS
           : unit;
  }

  public ScreenUtils.Unit getYUnit() {
    ScreenUtils.Unit unit = ScreenUtils.getUnit(y.getValue());

    return (unit == null)
           ? ScreenUtils.Unit.PIXELS
           : unit;
  }

  public ScreenUtils.Unit getWidthUnit() {
    ScreenUtils.Unit unit = ScreenUtils.getUnit(width.getValue());

    return (unit == null)
           ? ScreenUtils.Unit.PIXELS
           : unit;
  }

  public ScreenUtils.Unit getHeightUnit() {
    ScreenUtils.Unit unit = ScreenUtils.getUnit(height.getValue());

    return (unit == null)
           ? ScreenUtils.Unit.PIXELS
           : unit;
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
