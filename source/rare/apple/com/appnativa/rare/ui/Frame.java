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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.ui.view.ButtonView;
import com.appnativa.rare.platform.apple.ui.view.Window;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * Main window frame
 *
 * @author Don DeCoteau
 */
public class Frame extends Container implements iWindow {
  boolean                       located;
  boolean                       sized;
  protected int                 menuOffset  = 0;
  boolean                       undecorated = false;
  boolean                       transparent = false;
  protected iPlatformAppContext appContext;
  protected iPlatformMenuBar    menuBar;
  protected iStatusBar          statusBar;
  protected iTarget             target;
  protected iWidget             titleWidget;
  protected iToolBarHolder      toolbarHolder;
  protected Window              window;
  protected WindowViewer        windowViewer;
  private boolean               autoDispose;
  private String                defaultButton;
  private boolean               dialog;
  private boolean               mainWindow;
  private UIPoint               partialLocation;
  private UIDimension           partialSize;
  private boolean               popup;
  OverlayContainer              overlayContainer;

  public Frame(iPlatformAppContext app, Window win) {
    super(win);
    this.window = win;
    appContext  = app;
    mainWindow  = true;
    setNeedsHiearachyInvalidated(false);
  }

  public Frame(iPlatformAppContext app, Window win, String targetName) {
    super(win);
    this.window = win;
    appContext  = app;

    if (targetName == null) {
      targetName = "_new_window_" + Integer.toHexString((int)hashCode()); //int cast for java->objc
    }

    target = new WindowTarget(app, targetName, this);
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
    window.addWindowListener(l);
  }

  @Override
  public void center() {
    window.centerOnScreen();
  }

  @Override
  public void close() {
    window.close();
  }

  @Override
  public void dispose() {
    if (window != null) {
      window.close();

      if (overlayContainer != null) {
        overlayContainer.dispose();
      }

      if (target != null) {
        target.dispose(true);
      }

      if (menuBar != null) {
        menuBar.dispose();
      }

      if (statusBar != null) {
        statusBar.dispose();
      }

      if (toolbarHolder != null) {
        toolbarHolder.dispose();
      }

      if (titleWidget != null) {
        titleWidget.dispose();
      }

      super.dispose();
      titleWidget      = null;
      toolbarHolder    = null;
      statusBar        = null;
      window           = null;
      windowViewer     = null;
      menuBar          = null;
      target           = null;
      window           = null;
      overlayContainer = null;
      appContext       = null;
    }
  }

  @Override
  public void disposeOfWindow() {
    dispose();
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
    return menuBar;
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
    return statusBar;
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
    return toolbarHolder;
  }

  @Override
  public Object getUIWindow() {
    return window;
  }

  @Override
  public iContainer getViewer() {
    return windowViewer;
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
    return dialog;
  }

  public boolean isMainWindow() {
    return mainWindow;
  }

  public boolean isPopup() {
    return popup;
  }

  public boolean isTransparent() {
    return undecorated;
  }

  public boolean isUndecorated() {
    return undecorated;
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
      UIDimension d = getPreferredSize();

      if (!isUndecorated()) {
        UIDimension dd = PlatformHelper.getWindowDecorationSize();

        d.width  += dd.width;
        d.height += dd.height;
      }

      UIDimension sd = ScreenUtils.getSize();

      d.width  = Math.min(sd.width - 4, d.width);
      d.height = Math.min(sd.height - 24, d.height);
      setSize(d.width, d.height);
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
    window.removeWindowListener(l);
  }

  public void setAutoDispose(boolean autoDispose) {
    this.autoDispose = autoDispose;
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
  public iPlatformMenuBar setMenuBar(iPlatformMenuBar mb) {
    iPlatformMenuBar omb = menuBar;

    window.setMenuBar(mb);
    menuBar = mb;

    return omb;
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
  public iStatusBar setStatusBar(iStatusBar sb) {
    iStatusBar osb = statusBar;

    window.setStatusBar(sb);
    statusBar = sb;

    return osb;
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
  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    iToolBarHolder otbh = toolbarHolder;

    toolbarHolder = tbh;
    window.setToolBar(tbh);

    return otbh;
  }

  protected void setupWindow(WindowViewer parent, Map options, boolean emulate) {
    if (parent == null) {
      parent = appContext.getWindowViewer();
    }

    Frame              main          = (Frame) appContext.getWindowManager().getMainWindow().getComponent();
    iPlatformComponent mainComponent = main.getComponent();
    iScriptHandler     sh            = parent.getScriptHandler();

    windowViewer = new WindowViewer(appContext, target.getName(), this, parent, sh);
    sh.setScriptingContext(windowViewer, null, null, null, true);

    if (emulate) {
      PaintBucket pb = (PaintBucket) mainComponent.getClientProperty(iConstants.RARE_PAINTBUCKET_PROPERTY);

      pb.install(this);

      ActionLink link = (ActionLink) mainComponent.getClientProperty(iConstants.RARE_WINDOW_TITLELINK);

      if (link != null) {
        iWidget w = aContainer.createWidget(windowViewer, link);

        if (w != null) {
          setTitleWidget(w);
        }
      }
    }

    if (options == null) {
      return;
    }

    Object          o = options.get("cpborder");
    iPlatformBorder b = null;

    if (o instanceof iPlatformBorder) {
      b = (iPlatformBorder) o;
    } else if (o != null) {
      b = UIBorderHelper.createBorder(o.toString());
    }

    if (b != null) {
      setBorder(b);
    }

    String s = (String) options.get("status");

    if ((s != null) && (s.length() > 0)) {
      s = s.trim();

      if (statusBar == null) {
        StatusBar sb = null;

        if (SNumber.booleanValue((String) options.get("resizable"))
            &&!"false".equalsIgnoreCase((String) options.get("resizeCorner"))) {
          sb = new StatusBar();
          sb.showMemoryUsage.setValue(false);
          sb.showInsertOverwrite.setValue(false);
          sb.showTime.setValue(false);
          sb.showResizeCorner.setValue(true);
        }

        setStatusBar(appContext.getWindowManager().createStatusBar(sb));
      }

      statusBar.showMessage(s);
    }

    s = (String) options.get("keystrokes");

    if (s != null) {
      PlatformHelper.configureKeystrokes(appContext.getRootViewer(), this, s);
    }

    Map map = WidgetListener.createEventMap(options);

    if (map != null) {
      windowViewer.setWidgetListener(new WidgetListener(windowViewer, map, windowViewer.getScriptHandler()));
    }

    defaultButton = (String) options.get("defaultButton");
    Utils.setupWindowOptions(this, options);
  }

  public void setViewer(WindowViewer wv) {
    windowViewer = wv;
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

  @Override
  public void revalidate() {
    window.revalidate();
  }

  public static Frame create(iWidget context, String title, String target, boolean modal, boolean decorated) {
    Map map = new HashMap();

    if (title != null) {
      map.put("title", title);
    }

    map.put("modal", modal);
    map.put("decorated", decorated);

    return createFromOptions(context, target, map);
  }

  public static Frame createFromOptions(iWidget context, String targetName, Map options) {
    iPlatformAppContext app    = context.getAppContext();
    boolean             modal  = true;
    boolean             dialog = false;
    boolean             popup  = false;
    String              type   = "frame";
    Object              o;

    o = options.get("modal");

    if (o instanceof Boolean) {
      modal = (Boolean) o;
    } else if (o instanceof String) {
      modal = SNumber.booleanValue((String) o);
    }

    type = (String) options.get("windowtype");

    if (type == null) {
      type = (String) options.get("dialog");

      if ("true".equalsIgnoreCase(type) || Platform.isIOS()) {
        type = "dialog";
      } else {
        type = "frame";
      }
    }

    Window win;

    o = options.get("emulateMainWindow");

    if (o == null) {
      o = app.getUIDefaults().get("Rare.emulateMainWindow");
    }

    boolean emulate     = ((o == Boolean.TRUE) || "true".equals(o));
    boolean decorated   = true;
    boolean transparent = false;

    if (emulate) {
      Frame main = (Frame) app.getWindowManager().getMainWindow().getComponent();

      decorated   = !main.isUndecorated();
      transparent = main.isTransparent();
    }

    o = options.get("decorated");

    if (o != null) {
      decorated = "true".equals(o) || (o == Boolean.TRUE);

      if (!emulate) {
        transparent = !decorated;
      }
    }

    o = options.get("opaque");

    if (o != null) {
      transparent = "false".equals(o) || (o == Boolean.FALSE);
    }

    if (type.equalsIgnoreCase("dialog")) {
      dialog = true;
      win    = PlatformHelper.createDialog(context.getDataComponent().getView().getWindow(), transparent, decorated,
              modal);
      popup = true;
    } else if (type.equalsIgnoreCase("popup")) {
      win   = PlatformHelper.createPopup(true, transparent);
      popup = true;
    } else if (type.equalsIgnoreCase("popup_orphan")) {
      win = PlatformHelper.createPopup(false, transparent);
    } else {
      win = PlatformHelper.createWindow(modal, transparent, decorated);
    }

    Frame        frame  = new Frame(app, win, targetName);
    WindowViewer parent = Platform.getWindowViewer(context);

    frame.dialog      = dialog;
    frame.popup       = popup;
    frame.undecorated = !decorated;

    if (decorated) {
      win.setTitle(Platform.getAppContext().getWindowManager().getTitle());
      win.setIcon(Platform.getAppContext().getWindowManager().getWindowIcons().get(0));
    }

    frame.setupWindow(parent, options, emulate);

    return frame;
  }
}
