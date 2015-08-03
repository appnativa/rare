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

import android.content.Context;
import android.content.res.Configuration;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iAppContext;
import com.appnativa.rare.platform.android.ui.view.DialogEx;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.ui.border.UIDropShadowBorder;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.event.ExpansionEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;

import com.jgoodies.forms.layout.FormLayout;

/**
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iWindow {
  boolean                  closed = false;
  DialogEx                 dialogWindow;
  WindowPane               rootPane;
  iTarget                  target;
  Window                   window;
  private iPlatformMenuBar menuBar;
  private UIPoint          partialLocation;
  private UIDimension      partialSize;
  private ViewGroup        rootView;
  private boolean          sizeSet;
  private iStatusBar       statusBar;
  private String           title;
  private iToolBarHolder   toolbarHolder;
  private WindowViewer     windowViewer;
  private boolean          mainWindow;
  private OverlayContainer overlayContainer;

  public Frame(DialogEx d, String targetName) {
    this(d.getWindow(), targetName, false);
    dialogWindow = d;
    d.setFrame(this);

    if (!d.isUndecorated()) {
      rootPane.setBackground(ColorUtils.getBackground());
    } else if (d.isUsRuntimeDecorations()) {
      rootPane.setAutoCreateTitlePane(true);
      rootPane.setCombineMenuBarAndTitle(true);
      rootPane.setTitle(Platform.getAppContext().getWindowManager().getTitle());
      rootPane.setIcon(Platform.getAppContext().getWindowManager().getWindowIcons().get(0));
      rootPane.setBackground(ColorUtils.getBackground());
      rootPane.setBorder(new UIDropShadowBorder());
    }
  }

  public Frame(Window win, String targetName, boolean mainWindow) {
    super(win.getDecorView());
    this.window     = win;
    this.mainWindow = mainWindow;
    rootPane        = new WindowPane(new WindowFormsView(window.getContext(), WindowPane.createPaneFormLayout()));
    win.setContentView(rootPane.getView());

    if (!mainWindow) {
      if ((targetName == null) || (targetName.length() == 0)) {
        targetName = "Frame-" + Integer.toHexString(System.identityHashCode(this));
      }

      target = new WindowTarget(Platform.getAppContext(), targetName, this, true);
    }

    initRootPaneLayoutParams();
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    rootPane.setContent(c);
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

  OverlayContainer getOverlayContainer() {
    if (overlayContainer == null) {
      WindowPane         p  = rootPane;
      iPlatformComponent oc = p.getContent();

      overlayContainer = new OverlayContainer(oc);
      p.setContent(overlayContainer);
    }

    return overlayContainer;
  }

  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  public void center() {}

  public void close() {
    WindowEvent e = fireEvent(listenerList, this, WindowEvent.Type.WillClose);

    closed = (e == null) ||!e.isConsumed();

    if (closed) {
      dispose();
    }
  }

  public void dispose() {
    if (isDisposed()) {
      return;
    }

    if (window != null) {
      if (overlayContainer != null) {
        overlayContainer.dispose();
      }

      try {
        if (dialogWindow != null) {
          dialogWindow.dispose();
        }

        if (target != null) {
          target.dispose(false);
        }

        fireEvent(listenerList, this, WindowEvent.Type.Closed);
        removeAll();
        super.dispose();
      } finally {
        target           = null;
        window           = null;
        windowViewer     = null;
        rootView         = null;
        dialogWindow     = null;
        overlayContainer = null;
      }
    }
  }

  public void disposeOfWindow() {
    dispose();
  }

  protected Context getAndroidContext() {
    return window.getContext();
  }

  public iAppContext getAppContext() {
    return (windowViewer == null)
           ? Platform.getAppContext()
           : windowViewer.getAppContext();
  }

  public iPlatformComponent getComponent() {
    return this;
  }

  public iPlatformComponent getComponent(int index) {
    if (index == 0) {
      return rootPane.getContent();
    }

    throw new ArrayIndexOutOfBoundsException(index);
  }

  @Override
  public UIRectangle getInnerBounds(UIRectangle rect) {
    return rootPane.getInnerBounds(rect);
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

  public iPlatformMenuBar getMenuBar() {
    return menuBar;
  }

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  public int getScreenX() {
    return window.getAttributes().x;
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  public int getScreenY() {
    return window.getAttributes().y;
  }

  public iStatusBar getStatusBar() {
    return null;
  }

  public iTarget getTarget() {
    return target;
  }

  public String getTargetName() {
    return target.getName();
  }

  public String getTitle() {
    return title;
  }

  public iToolBarHolder getToolBarHolder() {
    return null;
  }

  public Object getUIWindow() {
    return window;
  }

  public int getUsableScreenHeight() {
    int        h = ScreenUtils.getHeight();
    final View v = rootPane.getView();

    return Math.min(v.getHeight(), h);
  }

  public int getUsableScreenWidth() {
    int        w = ScreenUtils.getWidth();
    final View v = rootPane.getView();

    return Math.min(v.getWidth(), w);
  }

  public iContainer getViewer() {
    return windowViewer;
  }

  public void hideWindow() {
    if (dialogWindow != null) {
      dialogWindow.dismiss();
    }
  }

  protected LayoutParams initRootPaneLayoutParams() {
    LayoutParams lp = rootPane.getView().getLayoutParams();

    if (lp == null) {
      lp = ((ViewGroup) rootPane.getView().getParent()).generateLayoutParams(null);
      rootPane.getView().setLayoutParams(lp);
    }

    lp.width  = LayoutParams.WRAP_CONTENT;
    lp.height = LayoutParams.WRAP_CONTENT;

    if (lp instanceof FrameLayout.LayoutParams) {
      ((FrameLayout.LayoutParams) lp).gravity = Gravity.FILL;
    } else if (lp instanceof LinearLayout.LayoutParams) {
      ((LinearLayout.LayoutParams) lp).weight  = 1;
      ((LinearLayout.LayoutParams) lp).gravity = Gravity.FILL;
    }

    return lp;
  }

  public boolean isDisposed() {
    return window == null;
  }

  public boolean isShowing() {
    if (dialogWindow != null) {
      return dialogWindow.isShowing();
    }

    return !closed && super.isShowing();
  }

  public boolean isUndecorated() {
    if (dialogWindow != null) {
      return dialogWindow.isUndecorated();
    }

    return true;
  }

  public boolean isVisible() {
    return isShowing();
  }

  public void moveBy(float x, float y) {
    if ((x != 0) || (y != 0)) {
      android.view.WindowManager.LayoutParams params = window.getAttributes();

      params.x += x;
      params.y += y;
      window.setAttributes(params);
    }
  }

  public void moveTo(float x, float y) {
    setLocation(x, y);
  }

  public void onConfigurationWillChange(Configuration newConfig) {
    // final View v = (rootView == null)
    // ? rootPane.getView()
    // : rootView;
    //
    // tempView = new SpacerView(getAndroidContext(), v.getWidth(),
    // v.getHeight());
    // window.setContentView(tempView);
  }

  public void pack() {
    UIDimension d  = rootPane.getPreferredSize();
    UIDimension sd = ScreenUtils.getUsableScreenSize();

    d.width  = Math.min(sd.width - ScreenUtils.PLATFORM_PIXELS_10, d.width);
    d.height = Math.min(sd.height - (ScreenUtils.PLATFORM_PIXELS_10), d.height);

    LayoutParams lp = initRootPaneLayoutParams();

    lp.width  = d.intWidth();
    lp.height = d.intHeight();
  }

  public void remove(iPlatformComponent c) {
    rootPane.remove(c);
  }

  public void removeAll() {
    rootPane.setContent(null);

    if (rootView != null) {
      rootView.removeAllViews();
      window.setContentView(rootPane.getView());
    }

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

  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  public void reset(Window win, Animation a) {
    if (window != win) {
      final View v = (rootView == null)
                     ? rootPane.getView()
                     : rootView;

      this.window = win;

      if (v.getParent() instanceof ViewGroup) {
        ((ViewGroup) v.getParent()).removeView(v);
      }

      win.setContentView(v);

      if (a != null) {
        v.startAnimation(a);
      }
    }
  }

  public void setBackground(UIColor bg) {
    rootPane.setBackground(bg);
  }

  public void setBorder(iPlatformBorder border) {
    rootPane.setBorder(border);
  }

  public void setCanClose(boolean can) {}

  public void setComponentPainter(iPlatformComponentPainter cp) {
    rootPane.setComponentPainter(cp);

    UIInsets in = rootPane.getInsetsEx();

    if (in != null) {
      rootPane.getView().setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }
  }

  public void setLocation(float x, float y) {
    android.view.WindowManager.LayoutParams params = window.getAttributes();

    params.x = Math.round(x);
    params.y = Math.round(y);
    window.setAttributes(params);
  }

  public iPlatformMenuBar setMenuBar(iPlatformMenuBar mb) {
    iPlatformMenuBar omb = menuBar;

    menuBar = mb;

    if ((mb != null) &&!mb.isNativeActionBar()) {
      rootPane.setMenuBar(mb);
    }

    return omb;
  }

  public void setMovable(boolean movable) {}

  public void setPartialLocation(UIPoint partialLocation) {
    this.partialLocation = partialLocation;
  }

  public void setPartialSize(UIDimension partialSize) {
    this.partialSize = partialSize;
  }

  public void setResizable(boolean resizable) {}

  /**
   * Set the root view for the frame. Must be called before content is added
   *
   * @param the
   *          root view
   */
  public void setRootView(ViewGroup view) {
    if (this.rootView != null) {
      this.rootView.removeView(rootPane.getView());
    }

    this.rootView = view;

    if (view != null) {
      this.rootView.addView(rootPane.getView());
      window.setContentView(view);
    } else {
      window.setContentView(rootPane.getView());
    }
  }

  public void setSize(int width, int height) {
    sizeSet = true;
    window.setLayout(width, height);
  }

  public iStatusBar setStatusBar(iStatusBar sb) {
    iStatusBar osb = statusBar;

    statusBar = sb;
    rootPane.setStatusBar(sb);

    return osb;
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  public void setTitle(String title) {
    this.title = title;

    if (dialogWindow != null) {
      if (dialogWindow.isUndecorated() && dialogWindow.isUsRuntimeDecorations()) {
        rootPane.setTitle(title);
      } else {
        dialogWindow.setTitle(title);
      }
    } else {
      window.setTitle(title);
    }
  }

  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    iToolBarHolder otbh = toolbarHolder;

    toolbarHolder = tbh;
    rootPane.setToolBar(tbh);

    return otbh;
  }

  public void setViewer(WindowViewer wv) {
    windowViewer = wv;

    if ((wv != null) && (dialogWindow != null) && dialogWindow.isUsRuntimeDecorations()) {
      rootPane.setTitlePaneAsWindowDragger(wv);
    }
  }

  public void setVisible(boolean visible) {
    if (visible) {
      Platform.getAppContext().closePopupWindows(false);

      if ((partialSize != null) || (partialLocation != null)) {
        Utils.setWindowSizeAndLocationFromPartial(this, partialLocation, partialSize);
        partialLocation = null;
        partialSize     = null;
      }
    }

    if (dialogWindow != null) {
      if (visible) {
        if (!sizeSet) {
          pack();
        }

        dialogWindow.show();
        Platform.getAppContext().getActiveWindows().addIfNotPresent(this);
      } else {
        dialogWindow.hide();
        Platform.getAppContext().getActiveWindows().remove(this);
      }
    }
  }

  public void showWindow() {
    setVisible(true);
  }

  public void showWindow(int x, int y) {
    setLocation(x, y);
    setVisible(true);
  }

  public void toBack() {}

  public void toFront() {
    if (window instanceof Window) {
      ((Window) window).makeActive();
    }
  }

  public void update() {
    rootPane.revalidate();
    rootPane.repaint();
  }

  public static void fireEvent(EventListenerList listenerList, Object source, boolean visible, boolean canceled) {
    ExpansionEvent e = null;

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      // Process the listeners last to first, notifying
      // those that are interested in this event
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iPopupMenuListener.class) {
          if (e == null) {
            e = new ExpansionEvent(source);
          }

          if (canceled) {
            ((iPopupMenuListener) listeners[i + 1]).popupMenuCanceled(e);
          } else if (visible) {
            ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeVisible(e);
          } else {
            ((iPopupMenuListener) listeners[i + 1]).popupMenuWillBecomeInvisible(e);
          }
        }
      }
    }
  }

  public static WindowEvent fireEvent(EventListenerList listenerList, Object source, WindowEvent.Type type) {
    WindowEvent e = null;

    if (listenerList != null) {
      Object[] listeners = listenerList.getListenerList();

      // Process the listeners last to first, notifying
      // those that are interested in this event
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == iWindowListener.class) {
          if (e == null) {
            e = new WindowEvent(source, type);
          }

          ((iWindowListener) listeners[i + 1]).windowEvent(e);

          if (e.isConsumed()) {
            break;
          }
        }
      }
    }

    return e;
  }

  class WindowFormsView extends FormsView {
    public WindowFormsView(Context context, FormLayout fl) {
      super(context, fl);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      if (mainWindow) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        if ((w > 0) && (h > 0)) {
          setMeasuredDimension(w, h);

          return;
        }
      }

      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
