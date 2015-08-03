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

import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;

/**
 * A interface for all top level windows.
 *
 * @author Don DeCoteau
 */
public interface iWindow {

  /**
   * Add a listener for window events
   *
   * @param l
   *          the listener
   */
  void addWindowListener(iWindowListener l);

  /**
   * Centers the window on the screen
   */
  void center();

  /**
   * Closes the window
   */
  void close();

  /**
   * Disposes of the window
   */
  void dispose();

  /**
   * Disposes of the window
   */
  void disposeOfWindow();

  /**
   * Hides the window
   */
  void hideWindow();

  /**
   * Moves the window by the specified amount
   *
   * @param x
   *          the amount to add to/subtract from the window's x-position
   * @param y
   *          the amount to add to/subtract from the window's y-position
   */
  void moveBy(float x, float y);

  /**
   * Moves the window to the specified location
   *
   * @param x
   *          the new x-position
   * @param y
   *          the new y-position
   */
  void moveTo(float x, float y);

  /**
   * Causes this Window to be sized to fit the preferred size and layouts of its
   * subcomponents.
   */
  void pack();

  /**
   * Removes a listener for window events. Does nothing if the listener was
   * never added
   *
   * @param l
   *          the listener
   */
  void removeWindowListener(iWindowListener l);

  /**
   * Shows the window
   */
  void showWindow();

  /**
   * Shows the window at the specified location
   *
   * @param x
   *          the x position
   * @param y
   *          the y position
   */
  void showWindow(int x, int y);

  /**
   * Sends the window to the back
   */
  void toBack();

  /**
   * Brings the window to the front
   */
  void toFront();

  /**
   * Updates the window (by revalidating and repainting its contents)
   */
  void update();

  void setBounds(float x, float y, float width, float height);

  /**
   * Sets whether the window can be closed This is meant to be called during an
   * <CODE>onWillClose</CODE> event for the window Calling
   * <CODE>setCanClose(false)</CODE> will prevent the window from closing
   *
   * @param can
   *          true if the window can be closed; false otherwise.
   */
  void setCanClose(boolean can);

  void setLocation(float x, float y);

  /**
   * Sets the window's menu bar
   *
   * @param mb
   *          the menu bar
   * @return a handle to the previous menu bar or null if there was not one
   */
  iPlatformMenuBar setMenuBar(iPlatformMenuBar mb);

  /**
   * Sets whether the window is resizable by the user.
   *
   * @param resizable
   *          true if the window is resizable; false otherwise.
   */
  void setResizable(boolean resizable);

  void setSize(float width, float height);

  /**
   * Sets the status bar for the window
   *
   * @param sb
   *          the status bar
   * @return a handle to the previous status bar or null if there was not one
   */
  iStatusBar setStatusBar(iStatusBar sb);

  /**
   * Sets the window's title
   *
   * @param title
   *          the title
   */
  void setTitle(String title);

  /**
   * Sets the window's toolbar holder
   *
   * @param tbh
   *          the toolbar holder
   * @return a handle to the previous toolbar holder or null if there was not
   *         one
   */
  iToolBarHolder setToolBarHolder(iToolBarHolder tbh);

  /**
   * Get the bounds of the window
   *
   * @return the bounds of the window
   */
  UIRectangle getBounds();

  /**
   * Gets the platform component associated with the window
   *
   * @return the platform component associated with the window
   */
  iPlatformComponent getComponent();

  /**
   * Gets the height of the window
   *
   * @return the height of the window
   */
  int getHeight();

  /**
   * Get the window's current menu bar
   *
   * @return the window's current menu bar
   */
  iPlatformMenuBar getMenuBar();

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  int getScreenX();

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  int getScreenY();

  /**
   * Gets the size of the window
   *
   * @return the size of the window
   */
  UIDimension getSize();

  /**
   * Get the window's current status bar
   *
   * @return the window's current status bar
   */
  iStatusBar getStatusBar();

  /**
   * Gets the target for the window's main display area
   *
   * @return the windows target
   */
  iTarget getTarget();

  /**
   * Gets the name of the window's target
   *
   * @return the name of the window's target
   */
  String getTargetName();

  /**
   * Gets the title of the window
   *
   * @return the title of the window
   */
  String getTitle();

  /**
   * Get the window's current toolbar holder
   *
   * @return the window's current toolbar holder
   */
  iToolBarHolder getToolBarHolder();

  /**
   * Get a handle to this windows associated AWT window
   *
   * @return a handle to the associated OS window
   */
  Object getUIWindow();

  /**
   * Get the window's associated viewer. The is a viewer for the window itself
   * and not the contents of the display area. This viewer is the actual window
   * object that is access via scripting languages
   *
   * @return the window viewer
   */
  iContainer getViewer();

  /**
   * Gets the width of the window
   *
   * @return the width of the window
   */
  int getWidth();

  /**
   * Returns the size of a window's content area
   *
   * @return the size of a window's content area
   */
  UIDimension getInnerSize(UIDimension size);
}
