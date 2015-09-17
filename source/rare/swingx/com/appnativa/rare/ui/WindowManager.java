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

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Applet;
import com.appnativa.rare.platform.swing.ui.util.MacUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.JFrameEx;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;

import java.awt.Image;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Don DeCoteau
 */
public class WindowManager extends aWindowManager {
  public WindowManager(final AppContext app) {
    this(app, null);
  }

  public WindowManager(final AppContext app, iWindow window) {
    super(app);

    if (window == null) {
      window = createMainFrame();
    }

    mainFrame       = window;
    workspaceTarget = window.getTarget();
    theTargets.put(iTarget.TARGET_WORKSPACE, workspaceTarget);

    if (componentCreator == null) {
      componentCreator = new ComponentFactory();
      componentCreator.setAppContext(appContext);
    }
  }

  @Override
  public void dispose() {
    iScriptHandler sh = scriptHandler;

    super.dispose();

    if (sh != null) {
      sh.dispose();
    }
  }

  @Override
  public void configure(MainWindow cfg) {
    if (!cfg.decorated.booleanValue()) {
      ((JFrame) mainFrame.getUIWindow()).setUndecorated(true);
    }

    createScriptHandler(cfg);

    DataEvent event = new DataEvent(this, cfg);

    fireEvent(iConstants.EVENT_CREATED, event, true);
    configureStandardStuff(cfg);

    if (!cfg.decorated.booleanValue()) {
      iWidget w = createTitleWidget(cfg);

      if (w != null) {
        ((Frame) mainFrame).setTitleWidget(w);
      }
    }

    if (cfg.keystrokeMappings.getValue() != null) {
      SwingHelper.configureKeystrokes(getRootViewer(), getRootPane(), cfg.keystrokeMappings.getValue(),
                                      JComponent.WHEN_IN_FOCUSED_WINDOW);

      if (!"true".equalsIgnoreCase(cfg.keystrokeMappings.spot_getAttribute("allWindows"))) {
        getRootPane().putClientProperty(iConstants.RARE_KEYSTROKES_PROPERTY, null);
      }
    }

    Rectangle r = cfg.bounds;
    int       x = r.getXPixels();
    int       y = r.getYPixels();
    int       w = r.getWidthPixels((iPlatformComponent) mainFrame, true);
    int       h = r.getHeightPixels((iPlatformComponent) mainFrame, true);

    if (h == -1) {
      h = r.getHeightMinPixels((iPlatformComponent) mainFrame, true);
    }

    if (w == -1) {
      w = r.getWidthMinPixels((iPlatformComponent) mainFrame, true);
    }

    if (!((AppContext) appContext).isEmbeddedInstance()) {
      ScreenUtils.centerOnScreenAndSize(mainFrame, x, y, w, h);
    }

    fireEvent(iConstants.EVENT_CONFIGURE, event, true);
  }

  public iPlatformMenuBar createMenuBar(MenuBar cfg) {
    MenuBarViewer v = new MenuBarViewer(getRootViewer());

    v.configure(cfg);

    return v;
  }

  @Override
  public iPopup createPopup(iWidget context) {
    iPopup p = new PopupWindow((Window) mainFrame.getUIWindow(), context);

    return p;
  }

  @Override
  public iWindow createWindow(iWidget context, Map options) {
    if (options == null) {
      options = Collections.EMPTY_MAP;
    }

    Frame f = Frame.createFromOptions(context, null, options);

    return f;
  }

  @Override
  public void onConfigurationChanged(boolean reset) {
    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationChanged(reset);
      getWorkspaceViewer().update();
    }
  }

  @Override
  public void onConfigurationWillChange(Object newConfig) {
    if ((getWorkspaceViewer() != null)) {
      getWorkspaceViewer().onConfigurationWillChange(newConfig);
    }
  }

  public JComponent getRootPane() {
    return ((Frame) mainFrame).getRootPaneContainer().getRootPane();
  }

  protected iWindow createMainFrame() {
    Frame ff;

    if (Applet.isRunningAsApplet()) {
      ff = ((AppContext) appContext).getApplet().getFrame();
    } else {
      JFrameEx f = new JFrameEx();

      ff = new Frame(appContext, f, f);
      f.addComponentListener(new ComponentAdapter() {
        Boolean oldWider = null;
        @Override
        public void componentResized(ComponentEvent e) {
          if (!isDisposed() && e.getComponent().isVisible()) {
            boolean wider = (getWidth() >= getHeight());

            if ((oldWider == null) ||(!oldWider.booleanValue() == wider)) {
              if (oldWider == null) {
                oldWider = wider;
              } else {
                final WindowDeviceConfiguration cfg = new WindowDeviceConfiguration(getSize());

                ((aAppContext) getAppContext()).handleConfigurationWillChange(cfg);
                ((aAppContext) getAppContext()).handleConfigurationChanged(cfg);
              }
            }
          }
        }
      });

      try {
        Class  util     = Class.forName("com.apple.eawt.FullScreenUtilities");
        Class  params[] = new Class[] { Window.class, Boolean.TYPE };
        Method method   = util.getMethod("setWindowCanFullScreen", params);

        method.invoke(util, f, true);
      } catch(ClassNotFoundException e1) {}
      catch(Exception e) {}
    }

    ff.setTarget(new UITarget(appContext, iTarget.TARGET_WORKSPACE, ff, false));

    return ff;
  }

  void setMacFullScreenEnabled(boolean enabled) {}

  @Override
  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  @Override
  protected void showErrorDialog(Throwable e) {
    WaitCursorHandler.stopWaitCursor(true);

    ErrorInformation ei = new ErrorInformation(e);

    ei.setTitle("Unhandler Error");
    AlertPanel.showErrorDialog(ei);
  }

  @Override
  protected void setWindowIconsEx(final List<UIImageIcon> icons) {
    if (appContext.isEmbeddedInstance()) {
      return;
    }

    iImageObserver is = new iImageObserver() {
      @Override
      public void imageLoaded(UIImage image) {
        setWindowIconsEx(icons);
      }
    };

    for (UIImageIcon ic : icons) {
      if (!ic.isImageLoaded(is)) {
        return;
      }
    }

    List<Image> images = new ArrayList<Image>(icons.size());

    for (UIImageIcon ic : icons) {
      images.add(ic.getUIImage().getBufferedImage());
    }

    ((Window) ((Frame) mainFrame).getUIWindow()).setIconImages(images);

    Object b = UIManager.get("Rare.updateMacDockIcon");

    if (Platform.isMac() && ((b == Boolean.TRUE) || (b == null))) {
      Image img = images.get(0);
      int   h   = img.getHeight(null);

      for (Image i : images) {
        if (i.getHeight(null) > h) {
          img = i;
          h   = i.getHeight(null);
        }
      }

      MacUtils.setDockIconImage(img);
    }
  }
}
