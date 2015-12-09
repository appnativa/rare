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

package com.appnativa.rare.ui;

import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSet;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the operations necessary for a window manager.
 * The window manager is responsible for creating and managing all the core visual
 * elements of the application
 *
 * @author Don DeCoteau
 *
 */
public interface iWindowManager extends iWindow {

  /**
   * Creates and activates the viewer for the specified Action link.
   *
   * @param link the Action link
   * @return an instance of the viewer that was created or null if the viewer could no be created
   *
   * @throws IOException if an I/O error occurs
   */
  iViewer activateViewer(ActionLink link) throws IOException;

  /**
   * Creates and activates the viewer for the specified Action link
   *
   * @param context the context (if specified it will override the link's context)
   * @param link the Action link
   * @return an instance of the viewer that was created or null if the viewer could no be created
   *
   * @throws IOException if an I/O error occurs
   */
  iViewer activateViewer(iWidget context, ActionLink link) throws IOException;

  /**
   * Creates and activates the viewer for the specified viewer configuration
   *
   * @param context the context
   * @param cfg the viewer configuration
   * @param target the target for the viewer
   * @return an instance of the viewer that was created or null if the viewer could no be created
   */
  iViewer activateViewer(iWidget context, Viewer cfg, String target);

  /**
   * Asynchronously creates and activates the viewer for the specified link
   *
   * @param link the Action link
   *
   */
  void asyncActivateViewer(ActionLink link);

  /**
   * Asynchronously creates and activates the viewer for the specified link
   *
   * @param context the context (if specified it will override the link's context)
   * @param link the Action link
   */
  void asyncActivateViewer(iWidget context, ActionLink link);

  /**
   * Changes the name of a target
   *
   * @param oldname the old name
   * @param newname the new name of the target
   * @param target the target whose name is to be changed
   */
  void changeTargetName(String oldname, String newname, iTarget target);

  /**
   * Clears the selected target removing any active viewers from it
   *
   * @param target the target to clear
   */
  void clearTarget(String target);

  /**
   * Configures the window manager
   *
   * @param cfg the main window configuration
   */
  void configure(MainWindow cfg);

  /**
   * Creates a new dialog
   *
   * @return a handle to the newly created dialogs
   * @param modal whether the dialog is modal
   * @param context the context
   * @param target the target name for the dialog's main display area
   * @param title the dialog's title
   */
  iWindow createDialog(iWidget context, String target, String title, boolean modal);

  /**
   * Creates a new dialog
   *
   * @return a handle to the newly created dialog
   * @param modal whether the dialog is modal
   * @param context the context
   * @param title the dialog's title
   */
  iWindow createDialog(iWidget context, String title, boolean modal);

  /**
   * Creates a new dialog
   *
   * @return a handle to the newly created dialog
   * @param options options for customizing the dialog
   * @param context the context for the dialog
   */
  iWindow createDialog(iWidget context, Map options);

  /**
   * Creates a new popup
   *
   * @param context the popup's context
   * @return a handle to the newly created popup
   */
  iPopup createPopup(iWidget context);

  /**
   * Creates a new popup menu from the specified URL
   *
   * @param context the widget context for the menu items
   * @param url the URL containing a definition the menu items
   * @param addTextItems whether the text menu items (copy, cut, paste) should be added to this menu
   * @return a handle to the popup menu
   *
   * @throws Exception if an error occurs
   */
  UIPopupMenu createPopupMenu(iWidget context, URL url, boolean addTextItems) throws Exception;

  /**
   * Creates a new popup menu from the specified set of menu items
   *
   * @param menu a menu object to use to hold the new items (can be null)
   * @param context the widget context for the menu items
   * @param menus the set of menu items
   * @param addTextItems whether the text menu items (copy, cut, paste) should be added to this menu
   * @return a handle to the popup menu
   */
  UIPopupMenu createPopupMenu(UIPopupMenu menu, iWidget context, SPOTSet menus, boolean addTextItems);

  /**
   * Creates a status bar for the specified configuration
   *
   * @return a handle to the newly created status bar
   * @param cfg the configuration
   */
  iStatusBar createStatusBar(StatusBar cfg);

  /**
   * Creates a new empty toolbar
   *
   * @param horizontal whether the toolbar should horizontal or vertical
   *
   * @return a handle to the newly created toolbar
   */
  iToolBar createToolBar(boolean horizontal);

  /**
   * Creates a toolbar for the specified configuration
   *
   * @param cfg the configuration
   *
   * @return a handle to the newly created toolbar
   */
  iToolBar createToolBar(ToolBar cfg);

  /**
   * Creates the viewer for the specified viewer Action link. The viewer is not activated
   *
   * @param link the link
   *
   * @return an instance of the viewer that was created or null if the viewer could no be created
   */
  iViewer createViewer(ActionLink link);

  /**
   * Creates the viewer for the specified viewer Action link. The viewer is not activated
   *
   * @param context the context (if specified it will override the link's context)
   * @param link the link
   *
   * @return an instance of the viewer that was created or null if the viewer could no be created
   */
  iViewer createViewer(iWidget context, ActionLink link);

  /**
   * Creates the viewer for the specified viewer configuration. The viewer is not activated
   *
   * @param context the context
   * @param cfg the viewer configuration
   * @param contextURL the context URL
   *
   * @return an instance of the viewer that was created or null if the viewer could no be created
   */
  iViewer createViewer(iWidget context, Viewer cfg, URL contextURL);

  /**
   * Creates the viewer for the specified viewer configuration. The viewer is not activated
   *
   * @param context the context
   * @param mimeType the MIME type (can be null)
   * @param cfg the viewer configuration
   * @param contextURL the context URL
   *
   * @return an instance of the viewer that was created or null if the viewer could no be created
   */
  iViewer createViewer(iWidget context, String mimeType, Viewer cfg, URL contextURL);

  /**
   * Creates a widget configuration object
   *
   * @param link the link representing the data for the configuration object
   *
   * @return the configuration object or null if the link is null or does not represent a configuration object
   */
  Widget createWidgetConfig(ActionLink link);

  /**
   * Creates a new window
   *
   * @return a handle to the newly created window
   * @param options options for customizing the window
   * @param context the context for the window
   */
  iWindow createWindow(iWidget context, Map options);

  /**
   * Creates a new window
   *
   * @param context the context for the window
   * @param target the target name for the window's main display area
   * @param title the window's title
   * @return a handle to the newly created window
   */
  iWindow createWindow(iWidget context, String target, String title);

  /**
   * Destroys the window manager which in most cases will shut down the application
   */
  @Override
  void dispose();

  /**
   * Handles an exception, displaying a message as appropriate
   *
   * @param e the throwable
   */
  void handleException(Throwable e);

  /**
   * Called when the device configuration has changed
   * @param reset whether the changes requires the window manager to reset itself
   */
  void onConfigurationChanged(boolean reset);

  /**
   * Called when the device configuration is about to change
   * @param newConfig the proposed new configuration (if known)
   */
  void onConfigurationWillChange(Object newConfig);

  /**
   * Creates and activates the viewer for the specified Action link in a new window
   *
   * @param link the Action link
   * @param viewerValue a value to set for the viewer represented by the link
   *                    the value can be retrieved vial a call to viewer.getValue()
   *
   * @return a handle to the window that is hosting the viewer
   * @throws IOException if an I/O error occurs
   */
  WindowViewer openViewerWindow(ActionLink link, Object viewerValue) throws IOException;

  /**
   * Registers a target that can host viewers
   *
   * @param name the name of the target
   * @param target the actual target being registered
   */
  void registerTarget(String name, iTarget target);

  /**
   * Registers a viewer such that it can be managed and is accessible via the scripting engine
   *
   * @param name the name of the viewer
   * @param viewer the viewer instance
   */
  void registerViewer(String name, iViewer viewer);

  /**
   * Removes a target making it unavailable to host viewers.
   * The target is NOT cleared prior to removal
   *
   * @param target the target to remove
   */
  void removeTarget(String target);

  /**
   * Resets the window manager causing its viewers to be reset or reloaded
   * and the window re-laid out and repainted
   *
   * @param reloadViewers true to reload viewers; false to just reset viewers
   */
  void reset(boolean reloadViewers);

  /**
   * Removes a target making it unavailable to host viewers.
   * The target is cleared prior
   *
   * @param name the name of the target
   */
  void unRegisterTarget(String name);

  /**
   * Unregisters a viewer such that it is no longer manager or accessible via the scripting engine
   *
   * @param name the name of the viewer
   * @param viewer the viewer being unregistered
   */
  void unRegisterViewer(String name,iViewer viewer);

  /**
   * Set the context URL for the window manager. This is the url that will be used
   * to resolve relative URLs.The default contextURL is the applications URL or the
   * URL specified in the application main window's configuration
   *
   * @param url the context URL
   */
  void setContextURL(URL url);

  /**
   * Sets the default font
   *
   * @param font the default font
   */
  void setDefaultFont(UIFont font);

  /**
   * Sets the relative font size
   *
   * @param size the relative (1.0 is the normal font size)
   * @return the relative size change
   */
  float setRelativeFontSize(float size);

  /**
   * Sets the status bar value
   *
   * @param status the status
   */
  void setStatus(String status);

  /**
   * Sets the specified viewer into the specified target (this activates the viewer)
   *
   * @return the passed in viewer
   * @param context the context
   * @param target the target
   * @param viewer the viewer
   * @param options target specific options
   */
  iViewer setViewer(String target, iWidget context, iViewer viewer, Map options);

  /**
   * Sets the specified viewer into the specified target (this activates the viewer).
   * The target has to be within the workspace (e.g. cannot be a new window or new dialog box, or toolbar or menu)
   *
   * @return the passed in viewer
   * @param context the context
   * @param target the target
   * @param viewer the viewer
   */
  iViewer setViewerEx(String target, iWidget context, iViewer viewer);

  /**
   * Gets the default font
   *
   * @return the default font
   */
  UIFont getDefaultFont();

  /**
   * Gets the default monospaced font
   *
   * @return the default monospaced font
   */
  UIFont getDefaultMonospacedFont();

  /**
   * Gets the application's root viewer
   *
   * @return the application's root viewer
   */
  iContainer getRootViewer();

  /**
   * Get the handle to the object responsible for handling script execution
   *
   * @return the handle to the object responsible for handling script execution
   */
  iScriptHandler getScriptHandler();

  /**
   * Gets the named target
   *
   * @param name the name of the target
   * @return the named target or null if the target does no exist
   */
  iTarget getTarget(String name);

  /**
   * Returns an array or all currently active targets
   *
   * @return an array or all currently active targets
   */
  iTarget[] getTargets();

  /**
   * Get the main window title
   *
   * @return the main window title
   */
  @Override
  String getTitle();

  /**
   * Creates a URL from the specified string. If the string represents a relative
   * URL then the window manager's contextURL will be used to resolve it
   *
   * @param url the string representation of the URL
   * @return a fully resolved URL
   *
   * @throws MalformedURLException if the url is invalid
   */
  URL getURL(String url) throws MalformedURLException;

  /**
   * Gets the usable screen height
   *
   * @return the usable screen height
   */
  int getUsableScreenHeight();

  /**
   * Gets the usable screen width
   *
   * @return the usable screen width
   */
  int getUsableScreenWidth();

  /**
   * Gets the named viewer
   *
   * @param name the name of the viewer
   * @return the named viewer or null if a viewer with the specified name does no exist
   */
  iViewer getViewer(String name);

  /**
   * Get the viewer that is currently active within the specified target
   *
   * @param target the target
   * @return the viewer that is currently active within the specified target
   */
  iViewer getViewerByTargetName(String target);

  /**
   * Returns an array or all currently active viewers
   *
   * @return an array or all currently active viewers
   */
  iViewer[] getViewers();

  /**
   * Gets the widget listener for the main window
   *
   * @return the widget listener
   */
  aWidgetListener getWidgetListener();

  /**
   * Gets the workspace viewer for the window
   *
   * @return the workspace viewer
   */
  iViewer getWorkspaceViewer();

  /**
   * Returns whether the window manage has been disposed
   *
   * @return true if the window manage has been disposed; false otherwise
   */
  boolean isDisposed();

  /**
   * Gets the list of icons for the window
   * @return the list of icons for the window
   */
  List<UIImageIcon> getWindowIcons();

  /**
   * Sets the list of icons for the window
   *
   * @param icons the list of icons for the window
   */
  void setWindowIcons(List<UIImageIcon> icons);

  /**
   * Gets the main window
   * @return the main window
   */
  iWindow getMainWindow();
  
  public interface iFrame extends iWindow {
    /**
     * Sets =the window viewer for the frame
     * 
     */
    void setWindowViewer(WindowViewer w);

    void setTitleWidget(iWidget w);

    void finishWindowSetup(Map options);
  }
}
