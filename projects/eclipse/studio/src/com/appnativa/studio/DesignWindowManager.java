/*
 * @(#)DesignWindowManager.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.net.URL;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.WindowManager;
import com.appnativa.rare.ui.iParentComponent;
import com.appnativa.rare.viewer.aPlatformViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;

public class DesignWindowManager extends WindowManager {
  DesignFrame designFrame;

  public DesignWindowManager(AppContext app, MainWindow cfg) {
    super(app);
    scriptHandler  = app.getWindowManager().getScriptHandler();
    widgetListener = app.getWindowManager().getWidgetListener();

    DesignWindowViewer wv = new DesignWindowViewer(this, appContext, iTarget.TARGET_WORKSPACE, designFrame, null,
                              scriptHandler);

    designFrame.setWindowViewer(wv);
    ((aPlatformViewer) getRootViewer()).setDesignMode(true);
    configureStandardStuff(cfg);

    if (!cfg.decorated.booleanValue()) {
      iWidget w = createTitleWidget(cfg);

      if (w != null) {
        ((DesignFrame) mainFrame).setTitleWidget(w);
      }
    }
  }

  public static iWidget createApplicationWindowViewer(iPlatformAppContext app, MainWindow cfg, URL contextURL) {
    DesignWindowManager dm = new DesignWindowManager((AppContext) app, cfg);
    iContainer          wv = dm.getWindowViewer();

    if (contextURL != null) {
      wv.setContextURL(contextURL);
    }

    wv.setLinkedData(cfg);

    return wv;
  }

  public DesignFrame getDesignFrame() {
    return designFrame;
  }

  @Override
  public iContainer getRootViewer() {
    return designFrame.getWindowViewer();
  }

  public iContainer getWindowViewer() {
    return designFrame.getWindowViewer();
  }

  @Override
  public boolean isDesignMode() {
    return true;
  }
  @Override
  public void dispose() {
    scriptHandler=null;
    super.dispose();
  }
  @Override
  protected iFrame createMainFrame() {
    designFrame = new DesignFrame(appContext);
    designFrame.setTarget(new DesignWorkspaceTarget(appContext, iTarget.TARGET_WORKSPACE, designFrame, false));

    return designFrame;
  }

  static class DesignWorkspaceTarget extends UITarget {
    public DesignWorkspaceTarget(iPlatformAppContext app, String name, iParentComponent container, boolean register) {
      super(app, name, container, register);
    }

    public void clear() {
      theViewer       = null;
      targetContainer = null;
      appContext      = null;
    }
  }
}
