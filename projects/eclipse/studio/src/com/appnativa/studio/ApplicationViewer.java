/*
 * @(#)ApplicationViewer.java   2010-06-23
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;

/**
 *
 * @author decoteaud
 */
public class ApplicationViewer extends aContainer {
  DesignWindowManager windowManager;
  public ApplicationViewer(iPlatformAppContext app) {
    super(null);
    appContext = app;
    actAsFormViewer=true;
    widgetType = WidgetType.Window;
    setDesignMode(true);
  }

  public void configure(Application app) {
    windowManager = new DesignWindowManager((AppContext) getAppContext(),app.application.getMainWindow());
    formComponent = dataComponent = windowManager.getDesignFrame();
    //windowManager.cleanup();
    if (app.contextURL.spot_hasValue()) {
      try {
        contextURL = getURL(app.contextURL.getValue());
        appContext.setContextURL(contextURL);
      } catch(MalformedURLException ex) {
        Logger.getLogger(ApplicationViewer.class.getName()).log(Level.WARNING, null, ex);
      }
    }

    if (app.application.contextURL.spot_hasValue()) {
      try {
        contextURL = getURL(app.application.contextURL.getValue());
      } catch(MalformedURLException ex) {
        Logger.getLogger(ApplicationViewer.class.getName()).log(Level.WARNING, null, ex);
      }
    }
  }

  public iViewer getMainViewer() {
    return windowManager.getWindowViewer();
  }
  public void configure(Viewer vcfg) {
    configure((Application) vcfg);
  }

  public void dispose() {
    if (!isDisposed()) {
      if (widgetList != null) {
        for (iWidget w : widgetList) {
          if (w != null) {
            try {
              w.dispose();
            } catch(Exception ignore) {}
          }
        }
      }

      super.dispose();
    }
  }

  @Override
  public void setContextURL(URL url) {
    super.setContextURL(url);
  }
}
