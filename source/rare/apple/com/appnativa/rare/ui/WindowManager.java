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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.apple.AppContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.Rectangle;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.iWidget;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public class WindowManager extends aWindowManager {
  public WindowManager(final AppContext app) {
    super(app);

    if (componentCreator == null) {
      componentCreator = new ComponentFactory();
      componentCreator.setAppContext(appContext);
    }

    mainFrame       = new Frame(app, app.getPlatformMainWindow());
    workspaceTarget = new WorkspaceTarget(app, (Frame) mainFrame);
    ((Frame) mainFrame).setTarget(workspaceTarget);
    theTargets.put(iTarget.TARGET_WORKSPACE, workspaceTarget);
  }

  @Override
  protected boolean supportsMultipleWindowIcons() {
    return !Platform.isIOS();
  }

  @Override
  public void configure(MainWindow cfg) {
    createScriptHandler(cfg);

    Frame     frame = (Frame) mainFrame;
    DataEvent event = new DataEvent(this, cfg);

    fireEvent(iConstants.EVENT_CREATED, event, true);
    configureStandardStuff(cfg);

    if (!cfg.decorated.booleanValue() || Platform.isTouchDevice()) {
      iWidget w = createTitleWidget(cfg);

      if (w != null) {
        ((Frame) mainFrame).setTitleWidget(w);
      }
    }

    Rectangle r = cfg.bounds;
    int       x = r.getXPixels();
    int       y = r.getYPixels();
    int       w = r.getWidthPixels((iPlatformComponent) mainFrame, true);
    int       h = r.getHeightPixels((iPlatformComponent) mainFrame, true);

    if (!Platform.isIOS()) {
      ScreenUtils.centerOnScreenAndSize(mainFrame, x, y, w, h);
    } else {
      if (cfg.showTitleBar.spot_hasValue() && cfg.showTitleBar.booleanValue()
          && Platform.getAppContext().okForOS(cfg.showTitleBar) && (menuBar == null)) {
        if (frame.getTitleWidget() == null) {
          BeanWidget b = new BeanWidget(getRootViewer(), new TitlePane(getViewer()));

          frame.setTitleWidget(b);
        }
      }
    }

    fireEvent(iConstants.EVENT_CONFIGURE, event, true);
  }

  @Override
  public iPopup createPopup(iWidget context) {
    iPopup p = PlatformHelper.createPopup(false, true);

    p.setPopupOwner(context.getContainerComponent());

    return p;
  }

  @Override
  public iWindow createWindow(iWidget context, Map options) {
    Frame f = Frame.createFromOptions(context, null, options);

    return f;
  }

  @Override
  public iWindow createWindow(iWidget context, String target, String title) {
    Frame f = Frame.create(context, title, target, true, true);

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

  @Override
  protected aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler) {
    return new WidgetListener(widget, map, scriptHandler);
  }

  @Override
  protected void handleCustomProperties(MainWindow cfg, Map<String, Object> properties) {
    PlatformHelper.handleCustomProperties(cfg, properties);
    super.handleCustomProperties(cfg, properties);
  }

  @Override
  protected void showErrorDialog(Throwable e) {
    AlertPanel.showErrorDialog(e);
  }

  @Override
  protected void setWindowIconsEx(List<UIImageIcon> icons) {}

  static class WorkspaceTarget extends UITarget {
    public WorkspaceTarget(iPlatformAppContext app, Frame f) {
      super(app, iTarget.TARGET_WORKSPACE, f, false);
    }
  }
}