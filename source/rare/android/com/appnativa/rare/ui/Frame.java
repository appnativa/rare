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

import java.util.Map;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iAppContext;
import com.appnativa.rare.platform.android.ui.view.DialogEx;
import com.appnativa.rare.platform.android.ui.view.FormsView;
import com.appnativa.rare.ui.aWindowManager.WindowType;
import com.appnativa.rare.ui.iWindowManager.iFrame;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.WindowEvent;
import com.appnativa.rare.ui.event.iActionListener;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;
import com.jgoodies.forms.layout.FormLayout;

/**
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iFrame, View.OnSystemUiVisibilityChangeListener {
  boolean                    closed = false;
  DialogEx                   dialogWindow;
  WindowPane                 rootPane;
  iTarget                    target;
  Window                     window;
  protected iPlatformMenuBar menuBar;
  private UIPoint            partialLocation;
  protected UIDimension      partialSize;
  protected ViewGroup        rootView;
  protected boolean          sizeSet;
  protected String           title;
  protected WindowViewer     windowViewer;
  protected boolean          mainWindow;
  protected OverlayContainer overlayContainer;
  protected WindowType       windowType;
  boolean                    transparent;
  boolean                    undecorated;
  boolean                    runtimeDecorations;

  public Frame(DialogEx d, String targetName, WindowType type) {
    this(d.getWindow(), targetName, false, type);
    dialogWindow = d;
    d.setFrame(this);
  }

  public Frame(Window win, String targetName, boolean mainWindow, WindowType type) {
    super(win.getDecorView());
    this.window     = win;
    this.mainWindow = mainWindow;
    this.windowType = type;
    rootPane        = new WindowPane(new WindowFormsView(window.getContext(), WindowPane.createPaneFormLayout()));
    window.setContentView(rootPane.getView());

    if (!mainWindow) {
      if ((targetName == null) || (targetName.length() == 0)) {
        targetName = "Frame-" + Integer.toHexString(System.identityHashCode(this));
      }

      target = new WindowTarget(Platform.getAppContext(), targetName, this, true);
    } else {
      window.getDecorView().setOnSystemUiVisibilityChangeListener(this);
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

  @Override
  public void addWindowListener(iWindowListener l) {
    getEventListenerList().add(iWindowListener.class, l);
  }

  @Override
  public void center() {}

  @Override
  public void close() {
    WindowEvent e = Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.WillClose);

    closed = (e == null) ||!e.isConsumed();

    if (closed) {
      dispose();
    }
  }

  @Override
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

        Utils.fireWindowEvent(listenerList, this, WindowEvent.Type.Closed);
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

  @Override
  public void disposeOfWindow() {
    dispose();
  }

  public void finishWindowSetup(Map options) {
    if (!undecorated && (dialogWindow != null) && dialogWindow.isUndecorated()) {
      boolean show=Platform.getUIDefaults().getBoolean("Rare.Dialog.showCloseButton", true);
      String s=options!=null ? (String)options.get("showCloseButton") : null;
      if(s!=null) {
        show=SNumber.booleanValue(s);
      }
      iActionListener l=null;
      if(show) {
        l= new iActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            windowViewer.close();
          }
        };
      }
      rootPane.createDialogTitleBar(windowViewer,l);
      rootPane.setTitlePaneAsWindowDragger(windowViewer);
      rootPane.setCombineMenuBarAndTitle(true);
      runtimeDecorations=true;
    }

    Utils.setupWindowOptions(this, options);
  }

  public iAppContext getAppContext() {
    return (windowViewer == null)
           ? Platform.getAppContext()
           : windowViewer.getAppContext();
  }

  @Override
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

  @Override
  public iPlatformMenuBar getMenuBar() {
    return menuBar;
  }

  /**
   * Get the x-position of the window's current screen location
   *
   * @return the x-position of the window's current screen location
   */
  @Override
  public int getScreenX() {
    return window.getAttributes().x;
  }

  /**
   * Get the y-position of the window's current screen location
   *
   * @return the y-position of the window's current screen location
   */
  @Override
  public int getScreenY() {
    return window.getAttributes().y;
  }

  @Override
  public iStatusBar getStatusBar() {
    return rootPane.getStatusBar();
  }

  @Override
  public iTarget getTarget() {
    return target;
  }

  @Override
  public String getTargetName() {
    return target.getName();
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public iToolBarHolder getToolBarHolder() {
    return rootPane.getToolBarHolder();
  }

  @Override
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

  @Override
  public iContainer getViewer() {
    return windowViewer;
  }

  public WindowViewer getWindowViewer() {
    return windowViewer;
  }

  public WindowType getWindowType() {
    return windowType;
  }

  @Override
  public void hideWindow() {
    if (dialogWindow != null) {
      dialogWindow.dismiss();
    }
  }

  @Override
  public boolean isDisposed() {
    return window == null;
  }

  @Override
  public boolean isShowing() {
    if (dialogWindow != null) {
      return dialogWindow.isShowing();
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
  public boolean isVisible() {
    return isShowing();
  }

  @Override
  public void moveBy(float x, float y) {
    if ((x != 0) || (y != 0)) {
      android.view.WindowManager.LayoutParams params = window.getAttributes();

      params.x += x;
      params.y += y;
      window.setAttributes(params);
    }
  }

  @Override
  public void moveTo(float x, float y) {
    setLocation(x, y);
  }

  public void onConfigurationWillChange(Configuration newConfig) {}

  @Override
  public void onSystemUiVisibilityChange(int visibility) {
    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {    //The system bars are visible
    } else {}
  }

  @Override
  public void pack() {
    UIDimension d  = rootPane.getPreferredSize();
    UIDimension sd = ScreenUtils.getUsableScreenSize();

    d.width  = Math.min(sd.width - ScreenUtils.PLATFORM_PIXELS_10, d.width);
    d.height = Math.min(sd.height - (ScreenUtils.PLATFORM_PIXELS_10), d.height);

    LayoutParams lp = initRootPaneLayoutParams();

    lp.width  = d.intWidth();
    lp.height = d.intHeight();
  }

  @Override
  public void remove(iPlatformComponent c) {
    rootPane.remove(c);
  }

  @Override
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

  @Override
  public void removeWindowListener(iWindowListener l) {
    if (listenerList != null) {
      listenerList.remove(iWindowListener.class, l);
    }
  }

  public void reset(Window win) {
    if (window != win) {
      final View v = (rootView == null)
                     ? rootPane.getView()
                     : rootView;

      this.window = win;

      if (v.getParent() instanceof ViewGroup) {
        ((ViewGroup) v.getParent()).removeView(v);
      }

      win.setContentView(v);
    }
    
  }

  @Override
  public void setBackground(UIColor bg) {
    rootPane.setBackground(bg);
  }

  @Override
  public void setBorder(iPlatformBorder border) {
    rootPane.setBorder(border);
  }

  @Override
  public void setCanClose(boolean can) {}

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    rootPane.setComponentPainter(cp);
    
    UIInsets in = rootPane.getInsetsEx();

    if (in != null) {
      rootPane.getView().setPadding(in.intLeft(), in.intTop(), in.intRight(), in.intBottom());
    }
  }

  @Override
  public void setLocation(float x, float y) {
    android.view.WindowManager.LayoutParams params = window.getAttributes();
    params.x = Math.round(x);
    params.y = Math.round(y);
    window.setAttributes(params);
  }

  @Override
  public void setMenuBar(iPlatformMenuBar mb) {
    menuBar = mb;

    if ((mb != null) &&!mb.isNativeActionBar()) {
      rootPane.setMenuBar(mb);
    }

  }

  public void setMovable(boolean movable) {}

  public void setPartialLocation(UIPoint partialLocation) {
    this.partialLocation = partialLocation;
  }

  public void setPartialSize(UIDimension partialSize) {
    this.partialSize = partialSize;
  }

  @Override
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

  @Override
  public void setStatusBar(iStatusBar sb) {
    rootPane.setStatusBar(sb);
  }

  public void setTarget(iTarget target) {
    this.target = target;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;

    if (rootPane.getTitlePane(false) != null) {
      rootPane.setTitle(title);
    } else {
      window.setTitle(title);
    }
  }

  public void setTitleWidget(iWidget w) {
    rootPane.setTitileBar((w == null)
                          ? null
                          : w.getContainerComponent());
  }

  @Override
  public void setToolBarHolder(iToolBarHolder tbh) {
    rootPane.setToolBarHolder(tbh);
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

  @Override
  public void showWindow() {
    setVisible(true);
  }

  @Override
  public void showWindow(int x, int y) {
    setLocation(x, y);
    setVisible(true);
  }

  @Override
  public void toBack() {}

  @Override
  public void toFront() {
    if (window instanceof Window) {
      window.makeActive();
    }
  }

  @Override
  public void update() {
    rootPane.revalidate();
    rootPane.repaint();
  }

  protected Context getAndroidContext() {
    return window.getContext();
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

  OverlayContainer getOverlayContainer() {
    if (overlayContainer == null) {
      WindowPane         p  = rootPane;
      iPlatformComponent oc = p.getContent();

      overlayContainer = new OverlayContainer(oc);
      p.setContent(overlayContainer);
    }

    return overlayContainer;
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


  public void setWindowViewer(WindowViewer windowViewer) {
    this.windowViewer = windowViewer;
  }
}
