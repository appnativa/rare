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

package com.appnativa.rare.platform.android.ui;

import android.app.ProgressDialog;

import android.content.Context;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;

import java.util.concurrent.Future;

/**
 *
 * @author Don DeCoteau
 */
public class ProgressPopup {
  ProgressDialog progressBar;
  iWidget        widget;
  private Object cancelAction;
  private Object completeAction;

  public ProgressPopup(iWidget widget, Context context) {
    progressBar = new ProgressDialog(context);
    this.widget = widget;
  }

  public ProgressDialog getProgressDialog() {
    return progressBar;
  }

  public void cancelPerformed() {
    Object action = this.cancelAction;

    try {
      if (widget.isDisposed()) {
        return;
      }

      if (action instanceof Future) {
        ((Future) action).cancel(true);
      } else if (action instanceof iCancelable) {
        ((iCancelable) action).cancel(true);
      } else if (action instanceof iActionListener) {
        ((iActionListener) action).actionPerformed(new ActionEvent(widget.getContainerComponent(),
                "pregressBarCancelButton"));
      } else if (action != null) {
        aWidgetListener.evaluate(widget, widget.getScriptHandler(), action, null);
      }
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);
    } finally {
      action = null;
    }
  }

  public void completePerformed() {
    Object action = this.completeAction;

    progressBar.dismiss();

    try {
      if (action instanceof Future) {
        ((Future) action).cancel(true);
      } else if (action instanceof iCancelable) {
        ((iCancelable) action).cancel(true);
      } else if (action instanceof iActionListener) {
        ((iActionListener) action).actionPerformed(new ActionEvent(widget.getContainerComponent(),
                "pregressBarComplete"));
      } else if (action != null) {
        aWidgetListener.evaluate(widget, widget.getScriptHandler(), action, null);
      }
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);
    } finally {
      action = null;
    }
  }

  public void progressAbort() {
    cancelPerformed();
  }

  public void progressComplete() {
    completePerformed();
  }

  public void progressStart(boolean showCancelButton) {
    progressStart(false, null, null, null);
  }

  public void progressStart(boolean indeterminate, String message, Object cancelAction) {
    progressStart(indeterminate, message, cancelAction, null);
  }

  public void progressStart(boolean indeterminate, String message, Object cancelAction, Object completeAction) {
    this.cancelAction   = cancelAction;
    this.completeAction = completeAction;
    progressBar.setIndeterminate(indeterminate);

    if (message != null) {
      progressBar.setMessage(message);
    }
  }

  public void progressStartIndeterminate(boolean showCancelButton) {
    progressStart(true, null, null, null);
  }

  public void setCancelAction(Object action) {
    cancelAction = action;
  }

  public void setCompleteAction(Object action) {
    completeAction = action;
  }

  public void setIndeterminate(boolean b) {
    progressBar.setIndeterminate(b);
  }

  public void setProgress(int value) {
    progressBar.setProgress(value);
  }

  public void setStatus(String status) {
    cancelAction   = null;
    completeAction = null;
    progressBar.dismiss();
  }

  public void setProgressStatus(String status) {
    progressBar.setMessage(status);
  }
}
