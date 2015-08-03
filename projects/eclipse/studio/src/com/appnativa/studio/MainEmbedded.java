/*
 * @(#)Main.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.jdesktop.swinghelper.debug.CheckThreadViolationRepaintManager;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.exception.AbortOperationException;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.rare.platform.swing.Main;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.UITarget;
import com.appnativa.rare.ui.WindowManager;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.studio.composite.DesignComposite;
import com.appnativa.util.OrderedProperties;

public class MainEmbedded extends Main {
  private final static OrderedProperties _properties = new OrderedProperties();
  Dialog                                 dialog;
  DesignComposite                        frame;
  private boolean                        forPreview;

  public MainEmbedded(URL url) throws Exception {
    super();
    Application a = new Application();
    a.getMainWindowReference().visible.setValue(false);
    configureFromProperties(_properties);
    commandLineArgs = Collections.EMPTY_LIST;
    info = new StartupInfo(JavaURLConnection.toExternalForm(url), null, null, false, false, commandLineArgs);
    argsInitialized();
    sageApplication = a;
  }

  public MainEmbedded(DesignComposite frame, URL url, Application app) throws Exception {
    super();
    this.frame = frame;
    configureFromProperties(_properties);
    commandLineArgs = Collections.EMPTY_LIST;
    info = new StartupInfo(JavaURLConnection.toExternalForm(url), null, null, false, false, commandLineArgs);
    argsInitialized();
    if (app == null) {
      createApplicationObject(new URL[] { url }, info.local);
    } else {
      setupApplicationObject(url, app, "text/x-sdf");
    }
  }

  @Override
  protected void registerDefaultViewers() {
    super.registerDefaultViewers();
    String name = Platform.getSPOTName(Browser.class);
    registerWidgetClass(name, BrowserViewer.class);
  }

  protected void checkMainViewerElements(iWidget context, Viewer viewer, URL contextURL) throws Exception {
    DataParser.INLINE_REGION_VIEWER_URLS = true;
    DataParser.INLINE_SELECTED_STACKPANE_VIEWER_URLS = true;
    URL url=contextURL;
    Object o=viewer.spot_getLinkedData();
    if(o instanceof URL) {
      url=(URL)o;
    }
    DataParser.checkElement(context, viewer, url);
    DataParser.INLINE_REGION_VIEWER_URLS = false;
    DataParser.INLINE_SELECTED_STACKPANE_VIEWER_URLS = false;
  }

  @Override
  public void handleException(final Throwable e) {
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        ErrorInformation ei = new ErrorInformation(e);

        ConsoleManager.getDefault().eprintln(ei.toString());
        ConsoleManager.getDefault().eprintln("");
      }
    });
  }

  public void startApplication(Project p) {
    if (p != null) {
      Application a = p.getEmptyApplication();
      if (a != null) {
        sageApplication = a;
        p.setupDesign(appContext, a);
      }
    }
    startApplication();

    if (!shuttingDown) {
      DesignPlatformImpl.switchContext((DesignAppContext) appContext);
    }

    sageApplication = null;
  }

  public static void setResources(ResourceBundle rareResourceBundle) {
    resources = rareResourceBundle;
  }

  @Override
  protected void createWindowManager(URL context, MainWindow mainWindow) {
    iWindow window;
    if (frame == null) {
      DesignFrame hwindow = new DesignFrame(appContext);
      hwindow.setTarget(new UITarget(appContext, iTarget.TARGET_WORKSPACE, hwindow, false));
      window = hwindow;
    } else {
      HostingFrame hwindow = new HostingFrame(appContext, frame);
      hwindow.setForPreview(forPreview);
      hwindow.setTarget(new UITarget(appContext, iTarget.TARGET_WORKSPACE, hwindow, false));
      window = hwindow;
      frame = null;
    }
    windowManager = new WindowManager((AppContext) appContext, window);
    windowManager.setContextURL(context);
    windowManager.configure(mainWindow);
  }

  @Override
  protected void handleFatalEexception(Throwable e) {
    if (!(e instanceof AbortOperationException)) {
      e.printStackTrace();
    }

    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        if (dialog != null) {
          dialog.close();
          dialog = null;
        }
      }
    });
  }

  protected void handleFatalException(Throwable e) {
    e.printStackTrace();
  }

  protected void setupAppContextAndPlatform() {
    appContext = new DesignAppContext(this);
    if (DesignPlatformImpl.instance == null) {
      TemplateHandler.TAG = new Object();
      Platform.setPlatform(new DesignPlatformImpl((AppContext) appContext));
      ((DesignAppContext) appContext).initializeSWING();
      try {
        FileInputStream f = new FileInputStream("rare.properties");

        _properties.load(f);
        f.close();
      } catch (Exception ignore) {
      }

      javax.swing.RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
    } else {
      DesignPlatformImpl.switchContext((DesignAppContext) appContext);
    }
  }

  public boolean isForPreview() {
    return forPreview;
  }

  public void setForPreview(boolean forPreview) {
    this.forPreview = forPreview;
  }
}
