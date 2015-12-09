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

package com.appnativa.rare.viewer;

import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.RenderType;
import com.appnativa.rare.ui.iCollapsible;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPopup;
import com.appnativa.rare.ui.effects.iAnimator;
import com.appnativa.rare.ui.event.iExpandedListener;
import com.appnativa.rare.ui.print.iPageSetup;
import com.appnativa.rare.widget.iWidget;

/**
 * This interface defines the functionality necessary for all viewers. A viewer
 * is a widget that offers more complex interactions and functionality and can
 * also server as a container for other widgets.
 *
 * @author Don DeCoteau
 */
public interface iViewer extends iWidget {

  /**
   * The disable behavior for viewers
   */
  public static enum DisableBehavior { DISABLE_WIDGETS, DISABLE_CONTAINER, DISABLE_BOTH, }

  /**
   * Sets the animator to invoke whenever the viewer is first loaded
   *
   * @param wa
   *            the animator
   */
  public void setLoadAnimator(iAnimator wa);

  /**
   * Returns whether the Print action can be performed on this viewer
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canPrint();

  /**
   * Returns whether a Save action can be performed on this viewer
   *
   * @return true if the action can be performed; false otherwise
   */
  boolean canSave();

  /**
   * Configures the viewer
   *
   * @param vcfg
   *            the viewer configuration
   */
  void configure(Viewer vcfg);

  /**
   * Create a page setup for printing and print previewing
   *
   * @return a page setup instance
   */
  iPageSetup createPageSetup();

  /**
   * Scrolls down a unit in the viewer
   */
  void downArrow();

  /**
   * Scrolls left a unit in the viewer
   */
  void leftArrow();

  /**
   * Notifies the viewer that the device configuration has changed
   *
   * @param reset
   *            true to reset the viewers; false otherwise
   */
  void onConfigurationChanged(boolean reset);

  /**
   * Notifies the viewer that the device configuration will be changing
   *
   * @param newConfig the new configuration
   */
  void onConfigurationWillChange(Object newConfig);

  /**
   * Scrolls down a page in the viewer
   */
  void pageDown();

  /**
   * Scrolls to the bottom of the viewer
   */
  void pageEnd();

  /**
   * Scrolls to the top of the viewer
   */
  void pageHome();

  /**
   * Scrolls to the far right of the viewer
   */
  void pageEndHorizontal();

  /**
   * Scrolls to the far left of the viewer
   */
  void pageHomeHorizontal();

  /**
   * Scrolls left a page in the viewer
   */
  void pageLeft();

  /**
   * Scrolls right a page in the viewer
   */
  void pageRight();

  /**
   * Initiate a page setup action on the viewer
   *
   * @param ps a page setup instance
   */
  void pageSetup(iPageSetup ps);

  /**
   * Scrolls up a page in the viewer
   */
  void pageUp();

  /**
   * Registers the viewer with the window manager
   */
  void register();

  /**
   * Registers a named item
   *
   * @param name
   *            the name of the item to register
   * @param object
   *            the item being registered
   * @return the item previously associated with the specified name or null of
   *         no such item existed
   */
  Object registerNamedItem(String name, Object object);

  /**
   * Scrolls right a unit in the viewer
   */
  void rightArrow();

  /**
   * Initiates a Save action on the viewer
   *
   * @param w
   *            the writer to save the viewers contents to
   */
  void save(Writer w);

  /**
   * Shows the viewer as a dialog box. A new dialog box is created and the
   * viewer becomes the main component for the dialog box
   *
   * @param winoptions
   *            options for configuring the window
   * @return the associated window viewer
   */
  WindowViewer showAsDialog(Map winoptions);

  /**
   * Shows the viewer as a dialog box. A new dialog box is created and the
   * viewer becomes the main component for the dialog box
   *
   * @param title
   *            the window title
   * @param modal
   *            true for modal; false otherwise
   * @return the associated window viewer
   */
  WindowViewer showAsDialog(String title, boolean modal);

  /**
   * Shows the viewer as a popup. A new popup is created and the viewer
   * becomes the main component for the popup
   *
   * @param owner
   *            the owner of the popup
   * @param options
   *            options for configuring the popup
   * @return a handle to the popup
   */
  iPopup showAsPopup(iPlatformComponent owner, Map options);

  /**
   * Shows the viewer as a window. A new window is created and the viewer
   * becomes the main component for the window
   *
   * @param winoptions
   *            options for configuring the window
   *
   * @return the associated window viewer
   */
  WindowViewer showAsWindow(Map winoptions);

  /**
   * Notifies the viewer that it is being set into the specified target. This
   * will trigger a Load event
   *
   * @param target
   *            the target for the viewer
   */
  void targetAcquired(iTarget target);

  /**
   * Notifies the viewer that it is being removed from the specified target.
   * This will trigger an Unload event
   *
   * @param target
   *            the target that the viewer is being removed from
   */
  void targetLost(iTarget target);

  /**
   * Unregisters the viewer with the window manger
   *
   * @param disposing
   *            true
   */
  void unregister(boolean disposing);

  /**
   * Unregisters a previously registered named item
   *
   * @param name
   *            the name of the item to unregister
   * @return the item associated with the specified name or null of no such
   *         item exists
   */
  Object unregisterNamedItem(String name);

  /**
   * Scrolls up a unit in the viewer
   */
  void upArrow();

  /**
   * Sets the application context for the viewer
   *
   * @param context
   *            the application context for the viewer
   */
  void setAppContext(iPlatformAppContext context);

  /**
   * Sets whether the viewer should automatically be disposed when it
   * looses its target
   *
   * @param autoDispose
   *            true (the default) if it should automatically dispose of
   *            itself; false otherwise
   */
  void setAutoDispose(boolean autoDispose);

  /**
   * Sets whether the viewer should automatically unregister itself when it
   * looses its target
   *
   * @param autoUnregister
   *            true if it should automatically unregister itself; false
   *            otherwise
   */
  void setAutoUnregister(boolean autoUnregister);

  /**
   * Sets the title to use when a viewer's target has been collapsed
   *
   * @param title
   *            the title
   */
  void setCollapsedTitle(String title);

  /**
   * Sets the context URL for the viewer. This URL will be used to resolve
   * relative URLs referenced by the viewer
   *
   * @param url
   *            the context URL
   */
  void setContextURL(URL url);

  /**
   * Sets whether the viewer can been disposed. If the viewer can't be
   * disposed, calls toe its dispose method will be ignored
   *
   * @param disposable
   *            true if the viewer can be disposed; false otherwise
   */
  void setDisposable(boolean disposable);

  /**
   * Sets the action link that created the viewer
   *
   * @param link
   *            the action link that created the viewer
   */
  void setViewerActionLink(ActionLink link);

  /**
   * Get the base URL for the viewer. This is the source URL or context URL if
   * there is no source URL. If the viewer does not have a source or context
   * URL then the viewer chain is walked until a valid base URL is found
   *
   * @return the base URL
   */
  URL getBaseURL();

  /**
   * Gets the title to use when a viewer's target has been collapsed
   *
   * @return the title
   */
  String getCollapsedTitle();

  /**
   * Gets the collapsible pane that houses the viewer
   *
   * @return the collapsible pane or null
   */
  iCollapsible getCollapsiblePane();

  /**
   * Get the context URL for the viewer. This is the URL from which the viewer
   * was constructed. If the viewer does not have a context URL then the
   * viewer chain is walked until a valid URL is found
   *
   * @return the context URL or null
   *
   * @see #getViewerURL()
   */
  URL getContextURL();

  /**
   * Gets and expanded listener for the viewer Widgets/viewers that can
   * collapse themselves will send messages to this handler when they expand
   * or collapse
   *
   * @return the expanded listener for the viewer
   */
  iExpandedListener getExpandedListener();

  /**
   * Gets a named item within the viewer
   *
   * @param name
   *            the name of the item
   *
   * @return the item with the corresponding name
   */
  Object getNamedItem(String name);

  /**
   * Gets a list of named of registered named items
   *
   * @return a list of named of registered named items
   */
  List<String> getNames();

  /**
   * Gets the viewer's printable component
   *
   * @return the viewer's printable component
   */
  iPlatformComponent getPrintComponent();

  /**
   * Get the source URL for the viewer. This is the URL for which the viewer
   * data was loaded
   *
   * @return the URL
   */
  URL getSourceURL();

  /**
   * Gets the target in which this viewer currently resides
   *
   * @return the target in which this viewer currently resides or null if the
   *         viewer is not in a target
   */
  iTarget getTarget();

  /**
   * Gets the action link that created the viewer
   *
   * @return the action link that created the viewer or null if the viewer was
   *         not created via a link
   */
  ActionLink getViewerActionLink();

  /**
   * Get the URL for the viewer. This is the URL from which the viewer was
   * constructed.
   *
   * @return the viewer URL or null
   */
  URL getViewerURL();

  /**
   * Returns whether the viewer will automatically be disposed when it
   * looses its target (defaults to true)
   *
   * @return true if it should automatically dispose of itself; false
   *         otherwise
   */
  boolean isAutoDispose();

  /**
   * Returns whether the viewer will automatically unregister itself with the
   * when it looses its target
   *
   * @return true if it should automatically unregister itself; false
   *         otherwise
   */
  boolean isAutoUnregister();

  /**
   * Returns whether a view handled the back key being pressed
   *
   * @return true if a view handled the back key being pressed; false
   *         otherwise
   */
  boolean isBackPressedHandled();

  /**
   * Gets whether the viewer can been disposed. If the viewer can't be
   * disposed, calls to its dispose method will be ignored
   *
   * @return true if the viewer can be disposed; false otherwise
   */
  boolean isDisposable();

  /**
   * Test whether the viewer has been disposed. A disposed viewer can no
   * longer be placed into a target
   *
   * @return true if the viewer has been disposed; false otherwise
   */
  @Override
  boolean isDisposed();

  /**
   * Whether the viewer is an external viewer. An external viewers is a viewer
   * that cannot be embedded in a java window.
   *
   * @return true if the viewer is an external viewer; false otherwise
   */
  boolean isExternalViewer();

  /**
   * Returns whether the viewer is currently registered and attached to a valid target
   *
   * @return true if the viewer is currently registered; false otherwise
   */
  boolean isRegistered();

  /**
   * Returns whether this viewer is a tab pane viewer
   *
   * @return true if it is a tab pane; false otherwise
   */
  boolean isTabPaneViewer();

  /**
   * Returns whether this viewer is a window-only viewer. That is, it can only
   * be displayed in a new window or dialog
   *
   * @return true if the viewer is a window only viewer; false otherwise
   */
  boolean isWindowOnlyViewer();

  /**
   * Gets the render type for the viewer to use when it is installed in a standard target
   * @return the render type for the viewer to use when it is installed in a standard target
   */
  RenderType getRenderType();

  /**
   * Sets the render type for the viewer to use when it is installed in a standard target
   * @param rt the render type for the viewer to use when it is installed in a standard target
   */
  void setRenderType(RenderType rt);
}
