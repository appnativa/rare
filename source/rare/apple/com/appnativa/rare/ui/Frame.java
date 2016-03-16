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
import com.appnativa.rare.iAppContext;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.ui.view.ButtonView;
import com.appnativa.rare.platform.apple.ui.view.Window;
import com.appnativa.rare.ui.aWindowManager.WindowType;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.iWindowManager.iFrame;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

import java.util.Map;

/**
 * Main window frame
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iFrame {
  boolean                closed = false;
  boolean                located;
  boolean                sized;
  protected int          menuOffset  = 0;
  boolean                undecorated = false;
  boolean                transparent = false;
  protected iTarget      target;
  protected iWidget      titleWidget;
  protected Window       window;
  protected WindowViewer windowViewer;
  private boolean        autoDispose;
  private String         defaultButton;
  private boolean        mainWindow;
  private UIPoint        partialLocation;
  private UIDimension    partialSize;
  OverlayContainer       overlayContainer;
  protected WindowType   windowType;
  private boolean        runtimeDecorations;

  public Frame(iPlatformAppContext app, Window win, String targetName, WindowType type) {
    super(win);
    this.windowType = type;
    this.window     = win;

    if (targetName == null) {
      targetName = "_new_window_" + Integer.toHexString((int) hashCode());    //int cast for java->objc
    }

    target = new WindowTarget(Platform.getAppContext(), targetName, this);
  }

  public Frame(iPlatformAppContext app, Window win, WindowType type) {
    super(win);
    this.window     = win;
    this.windowType = type;
    this.mainWindow = true;
    setNeedsHiearachyInvalidated(false);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c != null) {
      components.clear();
      components.add(c);

      if (overlayContainer != null) {
        overlayContainer.setMainComponent(c);
      } else {
        window.setContent(c);
      }
    }

    if ((defaultButton != null) && (c != null) && (c.getWidget() instanceof iContainer)) {
      iWidget w = ((iContainer) c.getWidget()).getWidget(defaultButton);

      if ((w != null) && (w.getDataComponent() instanceof ButtonView)) {
        ((ButtonView) w.getDataComponent().getView()).setDefaultButton(true);
      }
    }
  }

  public void addManagedOverlay(iPlatformComponent c) {
    if (c != null) {
      getOverlayContainer().addManagedOverlay(c);
    }
  }

  public void addOverlay(iPlatformComponent c) {
    if (c != null) {
      getOverlayContainer().addOverlay(c);
    }
  }

  @Override
  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  @Override
  public void center() {
    window.centerOnScreen();
  }

  @Override
  public void close() {
    if (!closed) {
      WindowEvent e = Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.WillClose);

      if ((e == null) ||!e.isConsumed()) {
        if ((windowViewer == null) || windowViewer.isClosingAllowed()) {
          dispose();
        }
      }
    }
  }

  @Override
  public void dispose() {
    closed = true;

    if (window != null) {
      try {
        window.close();

        if (overlayContainer != null) {
          overlayContainer.dispose();
        }

        if (target != null) {
          target.dispose(true);
        }

        if (titleWidget != null) {
          titleWidget.dispose();
        }

        Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.Closed);
        super.dispose();
      } finally {
        titleWidget      = null;
        window           = null;
        windowViewer     = null;
        target           = null;
        window           = null;
        overlayContainer = null;
      }
    }
  }

  @Override
  public void disposeOfWindow() {
    dispose();
  }

  public void finishWindowSetup(Map options) {
    if (!undecorated && (titleWidget == null)) {
      boolean show = Platform.getUIDefaults().getBoolean("Rare.Dialog.showCloseButton", true);
      String  s    = (options != null)
                     ? (String) options.get("showCloseButton")
                     : null;

      if (s != null) {
        show = SNumber.booleanValue(s);
      }

      window.createDialogTitleBar(windowViewer, show);
      runtimeDecorations = true;
    }

    String s = (String) options.get("keystrokes");

    if ((s != null) &&!Platform.isIOS()) {
      PlatformHelper.configureKeystrokes(windowViewer, this, s);
    }

    defaultButton = (String) options.get("defaultButton");
    Utils.setupWindowOptions(this, options);
  }

  public iAppContext getAppContext() {
    return Platform.getAppContext();
  }

  @Override
  public iPlatformComponent getComponent() {
    return this;
  }

  @Override
  public UIRectangle getInnerBounds(UIRectangle rect) {
    return window.getInnerBounds(rect);
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    UIRectangle rect = getInnerBounds(null);

    if (size == null) {
      size = new UIDimension();
    }

    size.width  = UIScreen.snapToSize(rect.width);
    size.height = UIScreen.snapToSize(rect.height);

    return size;
  }

  @Override
  public iPlatformMenuBar getMenuBar() {
    return (window == null)
           ? null
           : window.getMenuBar();
  }

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  @Override
  public int getScreenX() {
    return (int) window.getX();
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  @Override
  public int getScreenY() {
    return (int) window.getY();
  }

  @Override
  public iStatusBar getStatusBar() {
    return (window == null)
           ? null
           : window.getStatusBar();
  }

  @Override
  public iTarget getTarget() {
    return target;
  }

  @Override
  public String getTargetName() {
    return (target == null)
           ? null
           : target.getName();
  }

  @Override
  public String getTitle() {
    if (titleWidget != null) {
      return titleWidget.getValueAsString();
    }

    return window.getTitle();
  }

  public iWidget getTitleWidget() {
    return titleWidget;
  }

  @Override
  public iToolBarHolder getToolBarHolder() {
    return (window == null)
           ? null
           : window.getiToolBarHolder();
  }

  @Override
  public Object getUIWindow() {
    return window;
  }

  @Override
  public iContainer getViewer() {
    return windowViewer;
  }

  public WindowType getWindowType() {
    return windowType;
  }

  public WindowViewer getWindowViewer() {
    return windowViewer;
  }

  @Override
  public void hideWindow() {
    window.setVisible(false);
  }

  public boolean isAutoDispose() {
    return autoDispose;
  }

  public boolean isDialog() {
    return windowType == WindowType.DIALOG;
  }

  public boolean isMainWindow() {
    return mainWindow;
  }

  public boolean isPopup() {
    return windowType == WindowType.POPUP;
  }

  @Override
  public boolean isShowing() {
    if (window != null) {
      return window.isShowing();
    }

    return !closed && super.isShowing();
  }

  public boolean isTransparent() {
    return transparent;
  }

  public boolean isUndecorated() {
    return undecorated;
  }

  public boolean isUsesRuntimeDecorations() {
    return runtimeDecorations;
  }

  @Override
  public void moveBy(float x, float y) {
    if ((x != 0) && (y != 0)) {
      window.moveBy(x, y);
    }
  }

  @Override
  public void moveTo(float x, float y) {
    setLocation(x, y);
  }

  @Override
  public void pack() {
    if (!mainWindow) {
      window.pack();
    }
  }

  @Override
  public void remove(iPlatformComponent c) {
    if ((components.size() == 1) && (components.get(0) == c)) {
      super.remove(c);

      if (overlayContainer != null) {
        overlayContainer.setMainComponent(null);
      } else {
        window.setContent(null);
      }
    }
  }

  @Override
  public void removeAll() {
    super.removeAll();
    window.setContent(null);

    if (overlayContainer != null) {
      overlayContainer.dispose();
      overlayContainer = null;
    }
  }

  public void removeOverlay(iPlatformComponent c) {
    if (overlayContainer != null) {
      overlayContainer.removeOverlay(c);
    }
  }

  @Override
  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  public void repackAndCenter() {
    if (!mainWindow) {
      window.repackAndCenter();
    }
  }

  @Override
  public void revalidate() {
    window.revalidate();
  }

  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    window.setBorder(border);
  }

  public void setCancelable(boolean cancelable) {
    window.setCancelable(cancelable);
  }

  @Override
  public void setCanClose(boolean can) {
    if (windowViewer != null) {
      windowViewer.setCanClose(can);
    }
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    window.setComponentPainter(cp);
  }

  public void setDefaultButton(iPlatformComponent button) {}

  public void setLocation(int x, int y) {
    located = true;
    window.setLocation(x, y);
  }

  @Override
  public void setMenuBar(iPlatformMenuBar mb) {
    window.setMenuBar(mb);
  }

  public void setMovable(boolean movable) {
    window.setMovable(movable);
  }

  public void setPartialLocation(UIPoint partialLocation) {
    this.partialLocation = partialLocation;
  }

  public void setPartialSize(UIDimension partialSize) {
    this.partialSize = partialSize;
  }

  @Override
  public void setResizable(boolean resizable) {
    window.setResizable(resizable);
  }

  @Override
  public void setSize(int width, int height) {
    sized = true;
    window.setSize(width, height);
  }

  @Override
  public void setStatusBar(iStatusBar sb) {
    window.setStatusBar(sb);
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  @Override
  public void setTitle(String title) {
    if (titleWidget != null) {
      titleWidget.setValue(title);
    } else {
      window.setTitle(title);
    }
  }

  public void setTitleWidget(iWidget widget) {
    titleWidget = widget;
    window.setTitleView((widget == null)
                        ? null
                        : widget.getContainerComponent());
  }

  @Override
  public void setToolBarHolder(iToolBarHolder tbh) {
    window.setToolBarHolder(tbh);
  }

  @Override
  public void setVisible(boolean visible) {
    if (visible) {
      Platform.getAppContext().closePopupWindows(false);

      if ((partialSize != null) || (partialLocation != null)) {
        Utils.setWindowSizeAndLocationFromPartial(this, partialLocation, partialSize);
        partialLocation = null;
        partialSize     = null;
      }

      if (!sized) {
        pack();
      }

      if (!located) {
        center();
      }

      Platform.getAppContext().getActiveWindows().addIfNotPresent(this);
    }

    window.setVisible(visible);
  }

  public void setWindowViewer(WindowViewer windowViewer) {
    this.windowViewer = windowViewer;
  }

  @Override
  public void showWindow() {
    setVisible(true);
    window.requestFocus();
  }

  @Override
  public void showWindow(int x, int y) {
    setLocation(x, y);
    setVisible(true);
    window.requestFocus();
  }

  @Override
  public void toBack() {
    window.toBack();
  }

  @Override
  public void toFront() {
    window.toFront();
  }

  @Override
  public void update() {
    window.revalidate();
  }

  OverlayContainer getOverlayContainer() {
    if (overlayContainer == null) {
      WindowPane         p  = window.getWindowPane();
      iPlatformComponent oc = p.getContent();

      p.setContent(null);
      overlayContainer = new OverlayContainer(oc);
      p.setContent(overlayContainer);
    }

    return overlayContainer;
  }
}
