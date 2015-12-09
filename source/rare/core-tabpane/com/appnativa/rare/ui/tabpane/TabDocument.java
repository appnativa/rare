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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.HeadlessTarget;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iTabDocument;
import com.appnativa.rare.ui.listener.iTabPaneListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.iTabPaneViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;

import com.google.j2objc.annotations.Weak;
import com.google.j2objc.annotations.WeakOuter;

import java.net.MalformedURLException;

/**
 *
 * @author Don DeCoteau
 */
public class TabDocument implements iTabDocument, ViewerCreator.iCallback {
  boolean                                 canceled;
  ActionLink                              httpLink;
  @Weak()
  iTabPaneViewer                          parentViewer;
  boolean                                 reloadOnActivation;
  iTarget                                 theTarget;
  boolean                                 wasReset;
  protected boolean                       _canClose = true;
  protected iPlatformIcon                 alternateIcon;
  protected iPlatformIcon                 disabledIcon;
  protected iPlatformIcon                 displayIcon;
  protected iDocumentListener             manager;
  protected volatile boolean              showingCursor;
  private iTabDocument.iCanChangeCallback callback;
  private Object                          linkedData;
  private boolean                         loading;
  private PaintBucket                     tabColors;
  private String                          tabTitle;
  private iFunctionCallback               waitingCallback;
  private long                            timeReloaded;
  private long                            reloadTimeout;
  private boolean                         deactivated;

  /**
   * Creates a new instance of TabDocument
   *
   * @param app
   *          the application context
   * @param name
   *          the tab name
   * @param listener
   *          the listener
   */
  public TabDocument(iPlatformAppContext app, String name, iDocumentListener listener) {
    this(app, name, null, null, listener);
  }

  /**
   * Creates a new instance of TabDocument
   *
   * @param app
   *          the application context
   * @param name
   *          the tab name
   * @param listener
   *          the listener
   * @param title
   *          the tab title
   * @param icon
   *          the tab icon
   */
  public TabDocument(iPlatformAppContext app, String name, String title, iPlatformIcon icon,
                     iDocumentListener listener) {
    if (app.alwaysUseHeavyTargets()) {
      theTarget = new UITarget(app, name, false, listener);
    } else {
      theTarget = new DocumentTarget(app, name, listener);
    }

    manager     = listener;
    tabTitle    = title;
    displayIcon = icon;
  }

  @Override
  public boolean asyncCanChange(iWidget context, iCanChangeCallback cb) {
    if (cb == null) {
      callback = null;

      return false;
    }

    if ((httpLink != null) && httpLink.isDeferred(context)) {
      if (getViewer() == null) {
        callback = cb;

        if (!loading) {
          loadStarted(context);
          wasReset = false;
          loading  = true;

          try {
            ViewerCreator.createViewer(context, httpLink, this);
          } catch(MalformedURLException e) {
            throw new ApplicationException(e);
          }
        }

        return true;
      }
    }

    return false;
  }

  public void cancel(boolean canInterrupt) {
    canceled = true;

    if (httpLink != null) {
      httpLink.cancel(canInterrupt);
    }

    waitingCallback = null;
    callback        = null;
  }

  @Override
  public void configCreated(iWidget context, ActionLink link, Viewer config) {
    // will only get called on a reload
    iViewer v = theTarget.getViewer();

    if (v != null) {
      theTarget.removeViewer();
      v.dispose();
    }

    try {
      v = context.getAppContext().getWindowManager().createViewer(context, config, link.getURL(context));
      viewerCreated(context, link, v);
    } catch(MalformedURLException e) {
      errorHappened(context, link, e);
    }
  }

  @Override
  public void errorHappened(iWidget context, ActionLink link, Exception e) {
    loadCompleted(context, e);

    if (callback != null) {
      callback.errorHappened(context, this, e);
    } else {
      context.handleException(e);
    }
  }

  public static void fireTabActivated(iTabDocument doc, EventListenerList listenerList) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iTabPaneListener.class) {
        ((iTabPaneListener) listeners[i + 1]).tabActivated(doc);
      }
    }
  }

  public static void fireTabClosed(iTabDocument doc, EventListenerList listenerList) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iTabPaneListener.class) {
        ((iTabPaneListener) listeners[i + 1]).tabClosed(doc);
      }
    }
  }

  public static void fireTabDeactivated(iTabDocument doc, EventListenerList listenerList) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iTabPaneListener.class) {
        ((iTabPaneListener) listeners[i + 1]).tabDeactivated(doc);
      }
    }
  }

  public static void fireTabOpened(iTabDocument doc, EventListenerList listenerList) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iTabPaneListener.class) {
        ((iTabPaneListener) listeners[i + 1]).tabOpened(doc);
      }
    }
  }

  public static void fireTabWillClose(iTabDocument doc, EventListenerList listenerList) {
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == iTabPaneListener.class) {
        ((iTabPaneListener) listeners[i + 1]).tabWillClose(doc);
      }
    }
  }

  @Override
  public void reload(iFunctionCallback cb) {
    iViewer v = theTarget.getViewer();

    if ((httpLink != null) && ((v == null) ||!v.isExternalViewer())) {
      iWidget context = (parentViewer == null)
                        ? Platform.getContextRootViewer()
                        : parentViewer.getViewer();

      if (!httpLink.isDeferred(context)) {
        v = context.getAppContext().getWindowViewer().createViewer(httpLink);
        viewerCreated(context, httpLink, v);
      } else {
        loadStarted(context);
        wasReset        = false;
        loading         = true;
        waitingCallback = cb;

        try {
          ViewerCreator.createConfiguration(context, httpLink, this);
        } catch(MalformedURLException e) {
          throw new ApplicationException(e);
        }
      }
    } else if (v != null) {
      v.reload(true);
    }
  }

  @Override
  public void reset() {
    wasReset = true;
  }

  @Override
  public void startingOperation(iWidget context, ActionLink link) {
    loadStarted(context);
  }

  @Override
  public void tabActivated() {
    if (deactivated && (reloadTimeout != 0) && (timeReloaded + reloadTimeout < System.currentTimeMillis())) {
      unloadViewer();
    }

    deactivated = false;

    iViewer v      = theTarget.getViewer();
    boolean reload = false;

    if (wasReset) {
      reload   = true;
      wasReset = false;
    }

    if (reload || (v == null)) {
      if (httpLink != null) {
        reload(null);
      } else if (v != null) {
        v.reload(true);
      }
    }
  }

  @Override
  public void dispose() {
    if (httpLink != null) {
      httpLink.cancel(true);
    }

    if (theTarget != null) {
      theTarget.dispose(true);
    }

    theTarget       = null;
    httpLink        = null;
    alternateIcon   = null;
    disabledIcon    = null;
    displayIcon     = null;
    manager         = null;
    callback        = null;
    linkedData      = null;
    tabColors       = null;
    tabTitle        = null;
    waitingCallback = null;
  }

  @Override
  public void tabClosed() {}

  @Override
  public void tabDeactivated() {
    deactivated = true;

    if (reloadOnActivation) {
      unloadViewer();
    }
  }

  private void unloadViewer() {
    if ((theTarget != null) && (httpLink != null)) {
      iViewer v = theTarget.removeViewer();

      if (v != null) {
        v.getContainerComponent().removeFromParent();
        v.dispose();

        if (!httpLink.isInlineURL()) {
          httpLink.setViewerConfiguration(null);
        }
      } else {
        httpLink.cancel(true);
      }
    }
  }

  @Override
  public void tabOpened() {}

  @Override
  public void viewerCreated(iWidget context, ActionLink link, iViewer viewer) {
    if (context.isDisposed()) {
      viewer.dispose();

      return;
    }

    loading = false;
    loadCompleted(context, null);
    theTarget.setViewer(viewer);

    iCanChangeCallback ccb = callback;

    callback = null;

    if (ccb != null) {
      ccb.canChange(context, this);
    }

    iFunctionCallback cb = waitingCallback;

    waitingCallback = null;

    if (cb != null) {
      cb.finished(false, viewer);
    }
  }

  @Override
  public void setActionLink(ActionLink link) {
    httpLink = link;
  }

  @Override
  public void setAlternateIcon(iPlatformIcon icon) {
    alternateIcon = icon;
  }

  @Override
  public void setCanClose(boolean can) {
    _canClose = can;
  }

  @Override
  public void setDisabledIcon(iPlatformIcon icon) {
    disabledIcon = icon;
    manager.documentChanged(this);
  }

  @Override
  public void setIcon(iPlatformIcon icon) {
    displayIcon = icon;
    manager.documentChanged(this);
  }

  public void setIconEx(iPlatformIcon icon) {
    displayIcon = icon;
  }

  @Override
  public void setLinkedData(Object obj) {
    linkedData = obj;
  }

  @Override
  public void setReloadOnActivation(boolean reload) {
    reloadOnActivation = reload;
  }

  @Override
  public void setTabColors(PaintBucket tabColors) {
    this.tabColors = tabColors;
  }

  @Override
  public void setTabName(String name) {
    iWidget context = (parentViewer == null)
                      ? Platform.getContextRootViewer()
                      : parentViewer.getViewer();

    context.getAppContext().getWindowManager().changeTargetName(theTarget.getName(), name, theTarget);

    if (theTarget instanceof DocumentTarget) {
      ((DocumentTarget) theTarget).setName(name);
    } else if (theTarget instanceof UITarget) {
      ((UITarget) theTarget).setName(name);
    }
  }

  @Override
  public void setTabPaneViewer(iTabPaneViewer parent) {
    parentViewer = parent;
  }

  @Override
  public void setTitle(String title) {
    this.tabTitle = title;
    manager.documentChanged(this);
  }

  public void setTitleEx(String title) {
    this.tabTitle = title;
  }

  @Override
  public iPlatformIcon getAlternateIcon() {
    return alternateIcon;
  }

  @Override
  public iPlatformIcon getDisabledIcon() {
    return disabledIcon;
  }

  @Override
  public iPlatformComponent getDocComponent() {
    return getTabContent().getContainerComponent();
  }

  @Override
  public iPlatformIcon getIcon() {
    return displayIcon;
  }

  @Override
  public Object getLinkedData() {
    return linkedData;
  }

  @Override
  public PaintBucket getTabColors() {
    return tabColors;
  }

  public iViewer getTabContent() {
    if (theTarget.getViewer() == null) {
      tabActivated();
    }

    return theTarget.getViewer();
  }

  @Override
  public int getTabIndex() {
    if (parentViewer != null) {
      return parentViewer.indexOf(this);
    }

    return -1;
  }

  @Override
  public String getTabName() {
    return theTarget.getName();
  }

  @Override
  public iTabPaneViewer getTabPaneViewer() {
    return parentViewer;
  }

  @Override
  public iTarget getTarget() {
    return theTarget;
  }

  @Override
  public String getTitle() {
    return tabTitle;
  }

  @Override
  public iViewer getViewer() {
    return theTarget.getViewer();
  }

  @Override
  public iViewer getViewer(iFunctionCallback cb) {
    if (cb == null) {
      throw new NullPointerException("callback cannot be null");
    }

    return getViewerEx(cb);
  }

  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isClosingAllowed() {
    boolean can = _canClose;

    _canClose = true;

    return can;
  }

  protected void loadCompleted(iWidget context, Throwable error) {
    timeReloaded = System.currentTimeMillis();

    if (showingCursor) {
      showingCursor = false;

      if (error == null) {
        context.getAppContext().getAsyncLoadStatusHandler().loadCompleted(context, httpLink);
      } else {
        context.getAppContext().getAsyncLoadStatusHandler().errorOccured(context, httpLink, error);
      }
    }
  }

  protected void loadStarted(iWidget context) {
    if (!showingCursor) {
      showingCursor = true;
      context.getAppContext().getAsyncLoadStatusHandler().loadStarted(context, httpLink, null);
    }
  }

  protected iViewer getViewerEx(final iFunctionCallback cb) {
    iWidget context = (parentViewer == null)
                      ? Platform.getContextRootViewer()
                      : parentViewer.getViewer();
    iViewer v       = getViewer();

    if ((v != null) || (httpLink == null)) {
      final iViewer vv = v;

      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          cb.finished(false, vv);
        }
      });

      return v;
    }

    if (loading) {
      waitingCallback = cb;
    } else {
      if ((cb == null) ||!httpLink.isDeferred(context)) {
        v = context.getAppContext().getWindowViewer().createViewer(httpLink);
        viewerCreated(context, httpLink, v);
      } else {
        wasReset        = false;
        loading         = true;
        waitingCallback = cb;

        try {
          ViewerCreator.createViewer(context, httpLink, this);
        } catch(MalformedURLException e) {
          throw new ApplicationException(e);
        }
      }
    }

    return v;
  }

  @Override
  public long getReloadTimeout() {
    return reloadTimeout;
  }

  @Override
  public void setReloadTimeout(long reloadTimeout) {
    this.reloadTimeout = reloadTimeout;
  }

  @WeakOuter
  class DocumentTarget extends HeadlessTarget {
    public DocumentTarget(iPlatformAppContext appContext, String targetName, iListener listener) {
      super(appContext, targetName, listener);
    }

    @Override
    public void reloadViewer() {
      reload(null);
    }
  }
}
