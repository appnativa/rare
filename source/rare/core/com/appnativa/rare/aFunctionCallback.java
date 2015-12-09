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

package com.appnativa.rare;

import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;

/**
 * This is a base class for function callbacks
 * that provides some useful methods especially
 * when working in scenarios where the screen
 * that initiates the call back can go away before
 * the callback finished method is called
 *
 * @author Don DeCcoteau
 *
 */
public abstract class aFunctionCallback implements iFunctionCallback {

  /** the optional context widget widget */
  @Weak
  protected iWidget contextWidget;

  /** flag denoting the done state of the task */
  volatile protected boolean done;

  /** the optional update widget widget */
  @Weak
  protected iWidget updateWidget;

  /** the window for the context widget */
  @Weak
  protected WindowViewer window;
  private boolean        contextWidgetSet;

  public aFunctionCallback() {
    this(null, null);
  }

  public aFunctionCallback(iWidget contextWidget) {
    this(contextWidget, null);
  }

  public aFunctionCallback(iWidget contextWidget, iWidget updateWidget) {
    this.contextWidget = contextWidget;
    this.updateWidget  = updateWidget;

    if (contextWidget != null) {
      window           = Platform.getWindowViewer(contextWidget);
      contextWidgetSet = true;
    }

    if (window == null) {
      window = Platform.getWindowViewer();
    }
  }

  public void hideProgressPopup() {
    if (window != null) {
      window.hideProgressPopup();
    }
  }

  public void hideWaitCursor() {
    if (window != null) {
      window.hideWaitCursor();
    }
  }

  public void showProgressPopup(CharSequence message) {
    if (window != null) {
      window.showProgressPopup(message);
    }
  }

  public void showWaitCursor() {
    if (window != null) {
      window.showWaitCursor();
    }
  }

  /**
   * Checks to see if the initiating context and window is still valid
   * @return true if it is; false otherwise
   */
  public boolean isContextAndWindowValid() {
    if (contextWidget != null) {    // null check because we use a weak reference in iOS
      return (window != null) &&!contextWidget.isDisposed();
    }

    return (window != null) &&!contextWidgetSet;
  }

  /**
   * Checks to see if the initiating context is still valid
   * @return true if it is; false otherwise
   */
  public boolean isContextValid() {
    if (contextWidget != null) {    // null check because we use a weak reference in iOS
      return !contextWidget.isDisposed();
    }

    return !contextWidgetSet;
  }
}
