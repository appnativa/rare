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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;

import com.google.j2objc.annotations.Weak;

import java.net.MalformedURLException;

import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;

public class ViewerCreator implements Runnable, iCancelable {
  protected iCallback        callback;
  protected volatile boolean cancelRunnableQueued;
  protected volatile boolean canceled;
  @Weak
  protected iWidget          contextWidget;
  protected boolean          createViewer;
  protected boolean          handleClosedExceptions;
  protected ActionLink       link;
  protected String           target;
  private Runnable           cancelRunnable;
  private boolean            done;
  private boolean            notifiedOfStatus;

  public ViewerCreator(iWidget context, ActionLink link, iCallback callback, boolean createViewer)
          throws MalformedURLException {
    this.contextWidget = context;
    this.link          = link;
    link.getURL(context);
    this.callback     = callback;
    this.createViewer = createViewer;

    if (callback == null) {
      target = link.getTargetName();
    }
  }

  public ViewerCreator(iWidget context, ActionLink link, final iFunctionCallback callback, boolean createViewer)
          throws MalformedURLException {
    this.contextWidget = context;
    this.link          = link;
    link.getURL(context);
    this.callback = new ViewerCreator.iCallback() {
      @Override
      public void viewerCreated(iWidget context, ActionLink link, iViewer viewer) {
        context.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, link);
        callback.finished(false, viewer);
      }
      @Override
      public void errorHappened(iWidget context, ActionLink link, Exception e) {
        context.getAppContext().getAsyncLoadStatusHandler().errorOccured(context, link, e);
        callback.finished(true, e);
      }
      @Override
      public void configCreated(iWidget context, ActionLink link, Viewer config) {
        context.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, link);
        callback.finished(false, config);
      }
      @Override
      public void startingOperation(iWidget context, ActionLink link) {
        context.getAppContext().getAsyncLoadStatusHandler().loadStarted(context, link, null);
      }
    };
    this.createViewer = createViewer;
  }

  public ViewerCreator(iWidget context, ActionLink link, String target, boolean createViewer)
          throws MalformedURLException {
    this.contextWidget = context;
    this.link          = link;
    link.getURL(context);
    this.target       = target;
    this.createViewer = createViewer;
  }

  public void dispose() {
    callback       = null;
    contextWidget  = null;
    link           = null;
    cancelRunnable = null;
  }

  @Override
  public void cancel(boolean canInterrupt) {
    if (!canceled) {
      canceled = true;
      link.cancel(canInterrupt);
      handleCancelRunnable();
    }
  }

  public static ViewerCreator createConfiguration(iWidget context, ActionLink link, iCallback callback)
          throws MalformedURLException {
    ViewerCreator vc = new ViewerCreator(context, link, callback, false);

    context.getAppContext().executeBackgroundTask(vc);
    vc.notifyOfStartIfNecessary();

    return vc;
  }

  public static ViewerCreator createConfiguration(iWidget context, ActionLink link, iFunctionCallback callback)
          throws MalformedURLException {
    ViewerCreator vc = new ViewerCreator(context, link, callback, false);

    vc.notifyOfStartIfNecessary();
    context.getAppContext().executeBackgroundTask(vc);

    return vc;
  }

  public static ViewerCreator createViewer(iWidget context, ActionLink link, iCallback callback)
          throws MalformedURLException {
    ViewerCreator vc = new ViewerCreator(context, link, callback, true);

    vc.notifyOfStartIfNecessary();
    context.getAppContext().executeBackgroundTask(vc);

    return vc;
  }

  public static ViewerCreator createViewer(iWidget context, ActionLink link, iFunctionCallback callback)
          throws MalformedURLException {
    ViewerCreator vc = new ViewerCreator(context, link, callback, true);

    vc.notifyOfStartIfNecessary();
    context.getAppContext().executeBackgroundTask(vc);

    return vc;
  }

  public static ViewerCreator createViewer(iWidget context, ActionLink link, String target)
          throws MalformedURLException {
    ViewerCreator vc = new ViewerCreator(context, link, target, true);

    vc.notifyOfStartIfNecessary();
    context.getAppContext().executeBackgroundTask(vc);

    return vc;
  }

  private void notifyOfStartIfNecessary() {
    if (callback == null) {
      contextWidget.getAppContext().getAsyncLoadStatusHandler().loadStarted(contextWidget, link, null);
      notifiedOfStatus = true;
    }
  }

  protected void clear() {
    cancelRunnableQueued = false;
    canceled             = false;
    done                 = false;
    link.reset();
  }

  @Override
  public void run() {
    try {
      if (noGood()) {
        if (callback == null) {
          dispose();
        }

        return;
      }

      try {
        if (callback != null) {
          callback.startingOperation(contextWidget, link);
        }

        Viewer cfg = (Viewer) contextWidget.getAppContext().getWindowManager().createWidgetConfig(link);

        if (createViewer) {
          createViewer(cfg);
        } else {
          callback(cfg);
        }
      } catch(Exception e) {
        error(e);
      }
    } finally {
      if ((callback == null) &&!createViewer) {
        dispose();
      }
    }
  }

  public void setCancelRunnable(Runnable r) {
    this.cancelRunnable = r;
  }

  public void setHandleClosedExceptions(boolean handle) {
    this.handleClosedExceptions = handle;
  }

  public Runnable getCancelRunnable() {
    return cancelRunnable;
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isDone() {
    return done || noGood();
  }

  public boolean isHandleClosedExceptions() {
    return handleClosedExceptions;
  }

  protected void callback(final Viewer cfg) {
    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (noGood()) {
          return;
        }

        callback.configCreated(contextWidget, link, cfg);
      }
    });
  }

  protected void createViewer(final Viewer cfg) {
    if (noGood()) {
      return;
    }

    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          if (noGood()) {
            return;
          }

          done = false;

          iViewer v = contextWidget.getAppContext().getWindowManager().createViewer(contextWidget, cfg,
                        link.getURL(contextWidget));

          v.setViewerActionLink(link);

          if (callback != null) {
            callback.viewerCreated(contextWidget, link, v);
          } else {
            contextWidget.getAppContext().getAsyncLoadStatusHandler().loadCompleted(contextWidget, link);

            iViewer ov = contextWidget.getAppContext().getWindowManager().setViewer(target, contextWidget, v,
                           link.getWindowOptions());

            if ((ov != null) && ov.isAutoDispose()) {
              ov.dispose();
            }
          }
        } catch(Exception e) {
          if (!(e instanceof ClosedByInterruptException) &&!(e instanceof ClosedChannelException)) {
            Platform.ignoreException(null, e);
          }

          errorEx(e);
        } finally {
          done = true;

          if (callback == null) {
            dispose();
          }
        }
      }
    });
  }

  protected void error(final Exception e) {
    if (canceled || link.isCanceled()) {
      handleCancelRunnable();

      return;
    }

    Platform.invokeLater(new Runnable() {
      @Override
      public void run() {
        errorEx(e);
      }
    });
  }

  protected void errorEx(final Exception e) {
    if (!contextWidget.isDisposed()) {
      if (callback != null) {
        callback.errorHappened(contextWidget, link, e);
      } else {
        contextWidget.getAppContext().getAsyncLoadStatusHandler().errorOccured(contextWidget, link, e);

        if (handleClosedExceptions) {
          if ((e instanceof ClosedByInterruptException) || (e instanceof ClosedChannelException)) {
            return;
          }
        }

        contextWidget.handleException(e);
      }
    }
  }

  protected synchronized void handleCancelRunnable() {
    if (!cancelRunnableQueued) {
      cancelRunnableQueued = true;

      if (cancelRunnable != null) {
        Platform.invokeLater(cancelRunnable);
      } else {}
    }
  }

  private boolean noGood() {
    boolean nope = ((callback == null) && (target == null)) || canceled || (contextWidget == null)
                   || contextWidget.isDisposed();

    if (nope && notifiedOfStatus) {
      notifiedOfStatus = false;
      contextWidget.getAppContext().getAsyncLoadStatusHandler().loadCompleted(contextWidget, link);
    }

    return nope;
  }

  public iWidget getContext() {
    return contextWidget;
  }

  public void setContext(iWidget context) {
    this.contextWidget = context;
  }

  public interface iCallback {
    void configCreated(iWidget context, ActionLink link, Viewer config);

    void errorHappened(iWidget context, ActionLink link, Exception e);

    void startingOperation(iWidget context, ActionLink link);

    void viewerCreated(iWidget context, ActionLink link, iViewer viewer);
  }
}
