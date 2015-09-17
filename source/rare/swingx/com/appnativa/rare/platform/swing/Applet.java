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

package com.appnativa.rare.platform.swing;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.ui.Frame;

import java.awt.Window;

import javax.swing.JApplet;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * Applet class for running applications as an applet
 *
 * @author Don DeCoteau
 */
public class Applet extends JApplet {
  private static boolean appletRunning;
  Main                   sageMain;
  boolean                wasStarted;
  private Frame          frame;

  /**
   * Creates a new applet instance
   */
  public Applet() {
    frame         = null;
    appletRunning = true;
  }

  @Override
  protected JRootPane createRootPane() {
    return new JRootPaneEx();
  }

  public Frame getFrame() {
    if (frame == null) {
      Window w = SwingUtilities.windowForComponent(this);

      frame = new Frame(Platform.getAppContext(), w, this);
    }

    return frame;
  }

  /**
   * Returns whether the environment is part of an applet
   *
   * @return true if the environment is part of an applet; false otherwise
   */
  public static boolean isRunningAsApplet() {
    return appletRunning;
  }
//  /**
  //   * {@inheritDoc}
//   */
//  public void dispose() {}
//
//  /**
//   * Evaluates the specified code within the context of this applet
//   *
//   * @param code the code to evaluate
//   * @return the result of the evaluation
//   */
//  public Object eval(String code) {
//    if (sageMain == null) {
//      return null;
//    }
//
//    iViewer context = sageMain.getRootViewer();
//    Object  o       = WidgetListener.evaluate(context, context.getScriptHandler(), code, null);
//
//    return (o == null)
//           ? null
//           : o.toString();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public synchronized void init() {
//    LFInstaller.initialize();
//    initInstance();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public synchronized void start() {
//    startInstance();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public synchronized void stop() {
//    Runnable r = new Runnable() {
//      public void run() {
//        Main m = sageMain;
//
//        sageMain   = null;
//        wasStarted = false;
//        getContentPane().removeAll();
//
//        try {
//          if (m != null) {
//            m.exit();
//          }
//        } catch(Throwable e) {
//          AppContext.ignoreException(null, e);
//        }
//      }
//    };
//
//    Platform.invokeLater(r);
//    super.stop();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public void toBack() {
//    getFrame().toBack();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public void toFront() {
//    getFrame().toFront();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public void setTitle(String title) {
//    setName(title);
//  }
//
//  /**
//   * Gets the application context for the applet
//   *
//   * @return the application context for the applet or null if the applet has
//   * been destroyed
//   */
//  public AppContext getAppContext() {
//    return (sageMain == null)
//           ? null
//           : sageMain.getAppContext();
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//
//  /**
//   * Get the main frain handle for the applet
//   * @return
//   */
//  public iMainFrame getMainFrame() {
//    return this;
//  }
//
//  /**
  //   * {@inheritDoc}
//   */
//  public String getTitle() {
//    return getName();
//  }
//
//
//  /**
//   * Initializes the applet instance
//   */
//  protected void initInstance() {
//    try {
//      if (sageMain != null) {
//        return;
//      }
//
//      sageMain = new Main(this);
//
//      double ver = SNumber.doubleValue(SystemInfo.getJavaVersion());
//
//      if (ver < 1.5) {
//        sageMain.abortException =
//          new ApplicationException("Rare requires Java version 1.5.9 or later\r\nYour version is Java "
//                                   + SystemInfo.getJavaVersion());
//
//        return;
//      }
//
//      Platform.invokeAndWait(sageMain);
//    } catch(Exception ex) {
//      if ((sageMain != null) && (sageMain.abortException == null)) {
//        sageMain.abortException = ex;
//      }
//    }
//  }
//
//  /**
//   * Starts the applet instance
//   */
//  protected void startInstance() {
//    if (!wasStarted) {
//      try {
//        if (sageMain.abortException != null) {
//          appletFailure(sageMain.appContext, sageMain.abortException);
//        } else if ((sageMain != null) && (sageMain.getApplicationURL() == null)) {
//          appletFailure(sageMain.appContext, null);
//        } else {
//          sageMain.start();
//        }
//      } catch(Exception e) {
//        appletFailure((sageMain == null)
//                      ? null
//                      : sageMain.appContext, e);
//      } finally {
//        wasStarted = true;
//      }
//    }
//  }
//
//  private void appletFailure(iPlatformAppContext app, Throwable ex) {
//    getContentPane().add(new ComponentFactory.ErrorComponent(app, app.getResourceAsString("Rare.runtime.text.appletFailure"),
//            app.getResourceAsIcon("Rare.icon.missingJar"), ex));
//  }
//
//  private static class Main extends com.appnativa.rare.platform.swing.Main implements Runnable {
//    Throwable abortException;
//    Applet    theApplet;
//
//    Main(Applet applet) {
//      theApplet = applet;
//    }
//
//    /**
  //     * {@inheritDoc}
//     */
//    public void abort(Throwable e) {
//      if (theApplet.wasStarted) {
//        if (e != null) {
//          ErrorPanel.showDialog(appContext, appContext.getResourceAsString("Rare.runtime.text.fatalError"), e, false);
//        }
//      } else {
//        abortException = e;
//      }
//    }
//
//    public boolean canReload() {
//      return false;
//    }
//
//    /**
//     * Initializes the application
//     *
//     * @param args the application arguments
//     *
//     * @throws Exception
//     */
//    public void initialize() throws Exception {
//      String  file = null;
//      URL     url  = null;
//      JApplet app  = theApplet;
//
//      file = app.getParameter("application");
//
//      if (file == null) {
//        file = app.getParameter("applicationURL");
//      }
//
//      if (file == null) {
//        return;
//      }
//
//      OrderedProperties props = new OrderedProperties();
//
//      props.put("windowManager", app.getParameter("windowManager"));
//      props.put("functionHandler", app.getParameter("functionHandler"));
//      props.put("exceptionHandler", app.getParameter("exceptionHandler"));
//      props.put("printHandler", app.getParameter("printHandler"));
//      props.put("printHandler", app.getParameter("printHandler"));
//      props.put("mimeTypes", app.getParameter("mimeTypes"));
//      configureFromProperties(props);
//
//      if (file.startsWith(iConstants.LIB_PREFIX)) {
//        url = this.resolveLibURL(file);
//      } else {
//        url = new URL(appContext.getDocumentBase(), file);
//      }
//
//      validateWebStartURL(url);
//      loadApplication(url, false);
//
//      try {
//        String border = app.getParameter("appletBorder");
//
//        if (border != null) {
//          Border b = BorderHelper.createBorder(defaultRootViewer, border, null);
//
//          if (b != null) {
//            getApplet().getRootPane().setBorder(b);
//          }
//        }
//      } catch(Throwable ignore) {}
//    }
//
//    /**
  //     * {@inheritDoc}
//     */
//    public void reload() {}
//
//    public void run() {
//      if (reloadingApp) {
//        try {
//          exit();
//        } catch(Throwable e) {
//          AppContext.ignoreException(null, e);
//        }
//      } else {
//        try {
//          initialize();
//        } catch(Exception e) {
//          abort(e);
//        }
//      }
//    }
//
//    public Applet getApplet() {
//      return theApplet;
//    }
//
//    /**
  //     * {@inheritDoc}
//     */
//    protected void exiting() {
//      if (windowManager != null) {
//        windowManager.dispose();
//        windowManager = null;
//      }
//
//      clearInstance();
//    }
//
//    /**
//     * Loads the application from the specified URL
//     *
//     * @param url the application's URL
//     * @param reloading true if the application is reloading; false otherwise
//     */
//    protected boolean loadApplication(URL url, boolean reloading) {
//      if (url == null) {
//        throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.unknownApplication"));
//      }
//
//      try {
//        synchronized(STARTUP_LOCK) {
//          String  mime   = null;
//          String  file   = url.getFile();
//          boolean xml    = false;
//          boolean sdf    = false;
//          Reader  stream = null;
//
//          url = setApplicationURL(url);
//
//          iURLConnection ic  = openConnection(url);
//          URLConnection  con = (URLConnection) ic.getConnectionObject();
//
//          if (con instanceof HttpURLConnection) {
//            try {
//              int code = ((HttpURLConnection) con).getResponseCode();
//
//              if (code == 401) {
//                throw new AuthFailureException(url);
//              }
//            } catch(Exception ex) {
//              throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.unknownApplication"), ex);
//            }
//          }
//
//          mime = con.getContentType();
//
//          if (mime == null) {
//            mime = "text/x-sdf";
//          }
//
//          int n = mime.indexOf(';');
//
//          if (n > 0) {
//            mime = mime.substring(0, n - 1);
//          }
//
//          if (mime.startsWith(iConstants.XML_MIME_TYPE)) {
//            xml = true;
//          } else if (mime.startsWith(iConstants.SDF_MIME_TYPE)) {
//            sdf = true;
//          } else if (file.endsWith(".xml") || file.endsWith(".sxdf")) {
//            xml = true;
//          } else if (file.endsWith(".sdf")) {
//            sdf = true;
//          }
//
//          if (xml || sdf) {
//            stream = getReader(con);
//          }
//
//          iSPOTElement spot = null;
//
//          setContextURL(url);
//
//          if (xml) {
//            throw new UnsupportedOperationException();
//          } else if (sdf) {
//            spot = DataParser.loadSPOTObjectSDF(getRootViewer(), stream, null, mime,url);
//          }
//
//          if (!(spot instanceof Application)) {
//            sageApplication = new Application();
//
//            if (spot instanceof Widget) {
//              sageApplication.mainWindow.viewer.setValue(spot);
//
//              if (!customWindowManager) {
//                windowManagerClass = loadClass("com.appnativa.rare.viewer.WindowManager");
//              }
//            } else {
//              throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.unknownApplication"));
//            }
//          } else {
//            sageApplication = (Application) spot;
//          }
//
//          ic.dispose();
//
//          String title = sageApplication.mainWindow.title.getValue();
//
//          if ((title == null) || (title.length() == 0)) {
//            title = sageApplication.name.getValue();
//          }
//
//          initApplication(sageApplication);
//          installLookAndFeel();
//          configure(sageApplication, url);
//
//          return true;
//        }
//      } catch(Exception e) {
//        throw ApplicationException.runtimeException(e);
//      }
//    }
//
//    protected void postInit() {
//      super.postInit();
//    }
//
//    private URL resolveLibURL(String file) throws UnsupportedEncodingException, MalformedURLException {
//      return appContext.getResourceURL(file.substring(iConstants.LIB_PREFIX_LENGTH));
//    }
//  }
}
