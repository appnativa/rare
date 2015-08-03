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
 * This class represents the configuration for an application's
 * main window.
 * <p>
 * The main window consists of zero or more frames and a
 * workspace.
 * </p>
 * <p>
 * It can also contains a menu bar, a set of toolbars and a
 * status bar
 * </p>
 *
 * @author Don DeCoteau
 * @version 2.0
 */
public class MainWindow extends Viewer {
  //GENERATED_MEMBERS{
//GENERATED_COMMENT{}

  /** Appearance: the applications menu bar */
  protected MenuBar menuBar = null;
//GENERATED_COMMENT{}

  /** Design: whether to show the title bar when one would not be normally visible */
  public SPOTBoolean showTitleBar = new SPOTBoolean();
//GENERATED_COMMENT{}

  /** Appearance: a set of toolbars for the main window */
  protected SPOTSet toolbars = null;
//GENERATED_COMMENT{}

  /** Design: popup menu to display when the user right-clicks on a command bar */
  protected SPOTSet toolbarsContextMenu = null;
//GENERATED_COMMENT{}

  /** Appearance: the status bar for the main window */
  protected StatusBar statusBar = null;
//GENERATED_COMMENT{}

  /** Design: whether the main window is decorated */
  public SPOTBoolean decorated = new SPOTBoolean(null, true, false);
//GENERATED_COMMENT{}

  /** Hidden: a widget to use as the title pane for an undecorated window */
  public SPOTAny titlePane = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Hidden: a viewer for the window's workspace */
  public SPOTAny viewer = new SPOTAny("com.appnativa.rare.spot.Widget", true);
//GENERATED_COMMENT{}

  /** Design: the name of a class to instantiate and add as a window listener */
  public SPOTPrintableString windowListenerClass = new SPOTPrintableString(null, 0, 255, true);

  //}GENERATED_MEMBERS
  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>MainWindow</code> object.
   */
  public MainWindow() {
    this(true);
  }

  /**
   * Creates a new <code>MainWindow</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public MainWindow(boolean optional) {
    super(optional, false);
    spot_setElements();
  }

  /**
   * Creates a new <code>MainWindow</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected MainWindow(boolean optional, boolean setElements) {
    super(optional, setElements);
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements() {
    this.elementsSizeHint  += 9;
    this.attributeSizeHint += 7;
    super.spot_setElements();
    spot_defineAttribute("onOpened", null);
    spot_defineAttribute("onWillClose", null);
    spot_defineAttribute("onResize", null);
    spot_defineAttribute("onHasCollapsed", null);
    spot_defineAttribute("onHasExpanded", null);
    spot_defineAttribute("onError", null);
    spot_defineAttribute("style", null);
    spot_addElement("menuBar", menuBar);
    spot_addElement("showTitleBar", showTitleBar);
    showTitleBar.spot_defineAttribute("os", null);
    spot_addElement("toolbars", toolbars);
    spot_addElement("toolbarsContextMenu", toolbarsContextMenu);
    spot_addElement("statusBar", statusBar);
    spot_addElement("decorated", decorated);
    decorated.spot_defineAttribute("os", null);
    spot_addElement("titlePane", titlePane);
    titlePane.spot_defineAttribute("url", null);
    titlePane.spot_defineAttribute("os", null);
    spot_addElement("viewer", viewer);
    viewer.spot_defineAttribute("url", null);
    spot_addElement("windowListenerClass", windowListenerClass);
  }

  /**
   * Gets the menuBar element
   *
   * @return the menuBar element or null if a reference was never created
   */
  public MenuBar getMenuBar() {
    return menuBar;
  }

  /**
   * Gets the reference to the menuBar element
   * A reference is created if necessary
   *
   * @return the reference to the menuBar element
   */
  public MenuBar getMenuBarReference() {
    if (menuBar == null) {
      menuBar = new MenuBar(true);
      super.spot_setReference("menuBar", menuBar);
      menuBar.spot_defineAttribute("url", null);
      menuBar.spot_defineAttribute("installAsActionBar", null);
      menuBar.spot_defineAttribute("actionButtonInsets", null);
    }

    return menuBar;
  }

  /**
   * Sets the reference to the menuBar element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setMenuBar(iSPOTElement reference) throws ClassCastException {
    menuBar = (MenuBar) reference;
    spot_setReference("menuBar", reference);
  }

  /**
   * Gets the toolbars element
   *
   * @return the toolbars element or null if a reference was never created
   */
  public SPOTSet getToolbars() {
    return toolbars;
  }

  /**
   * Gets the reference to the toolbars element
   * A reference is created if necessary
   *
   * @return the reference to the toolbars element
   */
  public SPOTSet getToolbarsReference() {
    if (toolbars == null) {
      toolbars = new SPOTSet("toolbar", new ToolBar(), -1, -1, true);
      super.spot_setReference("toolbars", toolbars);
      toolbars.spot_defineAttribute("url", null);
    }

    return toolbars;
  }

  /**
   * Sets the reference to the toolbars element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setToolbars(iSPOTElement reference) throws ClassCastException {
    toolbars = (SPOTSet) reference;
    spot_setReference("toolbars", reference);
  }

  /**
   * Gets the toolbarsContextMenu element
   *
   * @return the toolbarsContextMenu element or null if a reference was never created
   */
  public SPOTSet getToolbarsContextMenu() {
    return toolbarsContextMenu;
  }

  /**
   * Gets the reference to the toolbarsContextMenu element
   * A reference is created if necessary
   *
   * @return the reference to the toolbarsContextMenu element
   */
  public SPOTSet getToolbarsContextMenuReference() {
    if (toolbarsContextMenu == null) {
      toolbarsContextMenu = new SPOTSet("menuItem", new MenuItem(), -1, -1, true);
      super.spot_setReference("toolbarsContextMenu", toolbarsContextMenu);
      toolbarsContextMenu.spot_defineAttribute("onConfigure", null);
    }

    return toolbarsContextMenu;
  }

  /**
   * Sets the reference to the toolbarsContextMenu element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setToolbarsContextMenu(iSPOTElement reference) throws ClassCastException {
    toolbarsContextMenu = (SPOTSet) reference;
    spot_setReference("toolbarsContextMenu", reference);
  }

  /**
   * Gets the statusBar element
   *
   * @return the statusBar element or null if a reference was never created
   */
  public StatusBar getStatusBar() {
    return statusBar;
  }

  /**
   * Gets the reference to the statusBar element
   * A reference is created if necessary
   *
   * @return the reference to the statusBar element
   */
  public StatusBar getStatusBarReference() {
    if (statusBar == null) {
      statusBar = new StatusBar(true);
      super.spot_setReference("statusBar", statusBar);
      statusBar.spot_defineAttribute("os", null);
    }

    return statusBar;
  }

  /**
   * Sets the reference to the statusBar element
   * @param reference the reference ( can be null)
   *
   * @throws ClassCastException if the parameter is not valid
   */
  public void setStatusBar(iSPOTElement reference) throws ClassCastException {
    statusBar = (StatusBar) reference;
    spot_setReference("statusBar", reference);
  }
  //}GENERATED_METHODS
  //GENERATED_INNER_CLASSES{
  //}GENERATED_INNER_CLASSES
}