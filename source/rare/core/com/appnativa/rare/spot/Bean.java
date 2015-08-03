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
 * bean (arbitrary/component) that can be
 * <p>
 * specified as a class name or the url for the bean
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class Bean extends Widget {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: the class name for the widget's component */
  public SPOTPrintableString beanClass = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the url for the jar containing the bean class files */
  public SPOTPrintableString beanJAR = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: the url for the serialized bean */
  public SPOTPrintableString beanURL = new SPOTPrintableString(null, 0, 255, true);
//GENERATED_COMMENT{}

  /** Design: custom bean configuration object */
  public SPOTAny beanProperties = new SPOTAny("com.appnativa.spot.SPOTSequence", true);
//GENERATED_COMMENT{}

  /** the string to display when the bean cannot be instantiated */
  public SPOTPrintableString failureMessage = new SPOTPrintableString();

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Bean</code> object.
   */
  public Bean() {
    this(true);
  }

  /**
   * Creates a new <code>Bean</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Bean(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>Bean</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Bean(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 5;
    this.attributeSizeHint += 7;
    super.spot_setElements();
    spot_defineAttribute("onItemAdded", null);
    spot_defineAttribute("onAction", null);
    spot_defineAttribute("onChange", null);
    spot_defineAttribute("onWillExpand", null);
    spot_defineAttribute("onWillCollapse", null);
    spot_defineAttribute("onHasCollapsed", null);
    spot_defineAttribute("onHasExpanded", null);
    spot_addElement("beanClass", beanClass);
    spot_addElement("beanJAR", beanJAR);
    spot_addElement("beanURL", beanURL);
    spot_addElement("beanProperties", beanProperties);
    spot_addElement("failureMessage", failureMessage);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
