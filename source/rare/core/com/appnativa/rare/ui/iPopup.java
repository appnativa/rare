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

import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.iViewer;

import java.util.Map;

/**
 * An interface for popup windows.
 *
 * @author Don DeCoteau
 */
public interface iPopup {

  /**
   * Adds a popup menu listener
   *
   * @param l
   *          the listener to add
   */
  void addPopupMenuListener(iPopupMenuListener l);

  /**
   * Cancels the popup
   */
  void cancelPopup(boolean useAnimation);

  /**
   * Disposes of the popup
   */
  void dispose();

  /**
   * Hides the popup
   */
  void hidePopup();

  /**
   * Removes a popup menu listener
   *
   * @param l
   *          the listener to remove
   */
  void removePopupMenuListener(iPopupMenuListener l);

  /**
   * Displays the popup
   */
  void showPopup();

  /**
   * Displays the popup at the specified location
   *
   * @param x
   *          the x coordinate
   * @param y
   *          the y coordinate
   */
  void showPopup(float x, float y);

  /**
   * Displays the popup at the specified location relative to the specified
   * component reference
   *
   * @param ref
   *          the component whose location the x and y coordinates are relative
   *          to
   * @param x
   *          the x coordinate
   * @param y
   *          the y coordinate
   */
  void showPopup(iPlatformComponent ref, float x, float y);

  /**
   * Sets a component painter for the popup
   *
   * @param painter
   *          the painter
   */
  void setComponentPainter(iPlatformComponentPainter painter);

  /**
   * Set the content of the popup
   *
   * @param component
   *          the content of the popup
   */
  void setContent(iPlatformComponent component);

  /**
   * Sets whether the popup is focusable
   *
   * @param focusable
   *          true if the popup is focusable; false otherwise
   */
  void setFocusable(boolean focusable);

  /**
   * Sets whether the popup is moveable
   *
   * @param moveble
   *          true if the popup is moveable; false otherwise
   */
  void setMovable(boolean moveble);

  void setOptions(Map<String, String> options);

  /**
   * Sets the display location for the popup
   *
   * @param x
   *          the x coordinate
   * @param y
   *          the y coordinate
   */
  void setPopupLocation(float x, float y);

  /**
   * Sets the owner of the popup
   *
   * @param component
   *          the owner of the popup
   */
  void setPopupOwner(iPlatformComponent component);

  /**
   * Sets the size of the popup the popup at the specified location
   *
   * @param width
   *          the width
   * @param height
   *          the height;
   */
  void setSize(float width, float height);

  /**
   * Sets the timeout for the popup. This is how long the popup will be
   * displayed before it automatically closed
   *
   * @param timeout
   *          the timeout in milliseconds
   */
  void setTimeout(int timeout);

  /**
   * Sets the pop-up's title
   *
   * @param title
   *          the title
   */
  void setTitle(String title);

  /**
   * Sets whether the popup is transient
   *
   * @param istransient
   *          true if the popup is transient; false otherwise
   */
  void setTransient(boolean istransient);

  /**
   * Set the content of the popup to be the specified viewer
   *
   * @param viewer
   *          the viewer
   *
   * @return the previous viewer
   */
  iViewer setViewer(iViewer viewer);

  void getPreferredSize(UIDimension size);

  /**
   * Returns whether the popup window is focusable
   *
   * @return true if the popup is focusable; false otherwise
   */
  boolean isFocusable();

  /**
   * Returns whether the popup is currently showing
   *
   * @return true if the popup is currently showing; false otherwise
   */
  boolean isShowing();

  /**
   * Returns whether the popup is transient
   *
   * @return true if the popup is transient; false otherwise
   */
  boolean isTransient();

  /**
   * Shows the popup modally (centered with the background slightly obscured)
   */
  void showModalPopup();

  /**
   * Gets the window pane associated with the popup
   *
   * @return the window pane associated with the popup
   */
  WindowPane getWindowPane();

  /**
   * Sets the animator for the popup
   *
   * @param animator
   *          the animator for the popup
   */
  void setAnimator(iPlatformAnimator animator);

  /**
   * Sets the background color for the popup
   * @param bg the background color for the popup
   */
  void setBackgroundColor(UIColor bg);
}
