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
 * Convenience abstract class for creating worker tasks
 *
 * @author Don DeCoteau
 *
 */
public abstract class aWorkerTask implements iWorkerTask {

  /** flag denoting the canceled state of the task */
  volatile protected boolean canceled;

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

  /** and optional callback for the task */
  protected iFunctionCallback callback;
  private boolean             contextWidgetSet;

  public aWorkerTask() {
    this(null, (iWidget) null);
  }

  public aWorkerTask(iWidget contextWidget) {
    this(contextWidget, (iWidget) null);
  }

  public aWorkerTask(iWidget contextWidget, iWidget updateWidget) {
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

  public aWorkerTask(iWidget contextWidget, iFunctionCallback callback) {
    this(contextWidget, (iWidget) null);
    this.callback = callback;
  }

  @Override
  public void cancel(boolean canInterrupt) {
    canceled = true;
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

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  public boolean isContextAndWindowValid() {
    if (contextWidget != null) {    // null check because we use a weak reference in iOS
      return (window != null) &&!contextWidget.isDisposed();
    }

    return (window != null) &&!contextWidgetSet;
  }

  public boolean isContextValid() {
    if (contextWidget != null) {    // null check because we use a weak reference in iOS
      return !contextWidget.isDisposed();
    }

    return !contextWidgetSet;
  }

  @Override
  public boolean isDone() {
    return done;
  }
}
