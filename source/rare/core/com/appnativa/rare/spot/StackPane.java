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
 * viewer that treats a set of viewers as a stack (like cards).
 * <p>
 * Only one viewer can be active at any given time. The viewers
 * can be referenced by name or index
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class StackPane extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Design: whether viewers should be loaded when they are first activated or when the pane is loaded */
  public SPOTBoolean loadOnActivation = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Design: whether viewers should be reloaded every time they are activated */
  public SPOTBoolean reloadOnActivation = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** // Behavior: the index of the viewer that is to be initially selected */
  public SPOTInteger selectedIndex = new SPOTInteger(null, -1, null, 0, false);
//GENERATED_COMMENT{}

  /** Behavior: name of the animator to use to animate transitions */
  public SPOTPrintableString transitionAnimator = new SPOTPrintableString();
//GENERATED_COMMENT{}

  /** Behavior: whether the pane should act as a form viewer (if false widgets will be registered with the next higher up form viewer) */
  public SPOTBoolean actAsFormViewer = new SPOTBoolean(null, false, false);
//GENERATED_COMMENT{}

  /** Hidden: the set of viewers for the stack pane */
  protected SPOTSet viewers = null;
//GENERATED_COMMENT{}

  /** Design~urlset: a the set of URLs representing viewer configurations */
  protected SPOTSet viewerURLs = null;

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>StackPane</code> object.
   */
  public StackPane() {
    this(true);
  }

  /**
   * Creates a new <code>StackPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public StackPane(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>StackPane</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected StackPane(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 7;
    this.attributeSizeHint += 1;
    super.spot_setElements();
    spot_defineAttribute("onChange", null);
    spot_addElement("loadOnActivation", loadOnActivation);
    spot_addElement("reloadOnActivation", reloadOnActivation);
    spot_addElement("selectedIndex", selectedIndex);
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
    spot_addElement("actAsFormViewer", actAsFormViewer);
    spot_addElement("viewers", viewers);
    spot_addElement("viewerURLs", viewerURLs);
    disableBehavior.spot_setDefaultValue(0, "disable_container");
    disableBehavior.spot_setOptional(false);
  }

  /**
   * Gets the viewers element
   *
   * @return the viewers element or null if a reference was never created
   */
  public SPOTSet getViewers() {
    return viewers;
  }

  /**
   * Gets the reference to the viewers element
   * A reference is created if necessary
   *
   * @return the reference to the viewers element
   */
  public SPOTSet getViewersReference() {
    if (viewers == null) {
      viewers = new SPOTSet("viewer", new SPOTAny("com.appnativa.rare.spot.Viewer"), -1, -1, true);
      super.spot_setReference("viewers", viewers);
    }

    return viewers;
  }

  /**
   * Sets the reference to the viewers element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setViewers(iSPOTElement reference) throws ClassCastException {
    viewers = (SPOTSet) reference;
    spot_setReference("viewers", reference);
  }

  /**
   * Gets the viewerURLs element
   *
   * @return the viewerURLs element or null if a reference was never created
   */
  public SPOTSet getViewerURLs() {
    return viewerURLs;
  }

  /**
   * Gets the reference to the viewerURLs element
   * A reference is created if necessary
   *
   * @return the reference to the viewerURLs element
   */
  public SPOTSet getViewerURLsReference() {
    if (viewerURLs == null) {
      viewerURLs = new SPOTSet("url", new SPOTPrintableString(), -1, -1, true);
      super.spot_setReference("viewerURLs", viewerURLs);
    }

    return viewerURLs;
  }

  /**
   * Sets the reference to the viewerURLs element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setViewerURLs(iSPOTElement reference) throws ClassCastException {
    viewerURLs = (SPOTSet) reference;
    spot_setReference("viewerURLs", reference);
  }

  //}GENERATED_METHODS

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
    final int len = (viewers == null)
                    ? 0
                    : viewers.size();

    for (int i = 0; i < len; i++) {
      Viewer v = (Viewer) viewers.get(i);

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
      } else if (v instanceof StackPane) {
        Widget w = ((StackPane) v).findWidget(name, useNameMap);

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
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}