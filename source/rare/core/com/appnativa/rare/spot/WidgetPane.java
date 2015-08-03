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
 * viewer that wraps a widget
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class WidgetPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Behavior~~reload: whether the pane should resize the widget to fill available space */
  public SPOTBoolean autoResizeWidget = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Behavior~~reload: information specifying if the viewer is scrollable and how the associated scroll pane should be configured */
  protected ScrollPane scrollPane = null;
//GENERATED_COMMENT{}

  /** Behavior: name of the animator to use to animate transitions */
  public SPOTPrintableString transitionAnimator = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Hidden: the widget to be wrapped (if not specified that the dataURL is used) */
  public SPOTAny widget = new SPOTAny("com.appnativa.rare.spot.Widget", true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>WidgetPane</code> object.
   */
  public WidgetPane() {
    this(true);
  }

  /**
   * Creates a new <code>WidgetPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public WidgetPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>WidgetPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected WidgetPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint += 4;
    super.spot_setElements();
    spot_addElement("autoResizeWidget", autoResizeWidget);
    spot_addElement("scrollPane", scrollPane);
    spot_addElement("transitionAnimator", transitionAnimator);
    transitionAnimator.spot_defineAttribute("duration", null);
    transitionAnimator.spot_defineAttribute("direction", null);
    transitionAnimator.spot_defineAttribute("acceleration", null);
    transitionAnimator.spot_defineAttribute("deceleration", null);
    transitionAnimator.spot_defineAttribute("horizontal", null);
    transitionAnimator.spot_defineAttribute("fadeIn", null);
    transitionAnimator.spot_defineAttribute("fadeOut", null);
    transitionAnimator.spot_defineAttribute("diagonal", null);
    transitionAnimator.spot_defineAttribute("diagonalAnchor", null);
    transitionAnimator.spot_defineAttribute("autoOrient", null);
    transitionAnimator.spot_defineAttribute("customProperties", null);
    spot_addElement("widget", widget);
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

  //}GENERATED_METHODS

  /**
   * Creates a new <code>WidgetPane</code> object.
   *
   * @param widget  <code>true</code> if the widget to host in the pane
   */
  public WidgetPane(Widget widget) {
    this(true);
    this.widget.setValue(widget);
  }

  /**
   * Finds an returns the named widget (does not recurse)
   *
   * @param name the name of the widget to find
   * @param useNameMap true to use a name map to improve furture search performance; false otherwise
   *
   * @return the named widget or null
   */
  @Override
  public Widget findWidget(String name, boolean useNameMap) {
    iSPOTElement v = widget.getValue();

    if (v == null) {
      return null;
    }

    if (name.equals(((Widget) v).name.getValue())) {
      return (Widget) v;
    }

    if (v instanceof GroupBox) {
      Widget w = ((GroupBox) v).findWidget(name, useNameMap);

      if (w != null) {
        return w;
      }
    } else if (v instanceof GridPane) {
      Widget w = ((GridPane) v).findWidget(name, useNameMap);

      if (w != null) {
        return w;
      }
    } else if (v instanceof SplitPane) {
      Widget w = ((SplitPane) v).findWidget(name, useNameMap);

      if (w != null) {
        return w;
      }
    }

    return null;
  }
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}
