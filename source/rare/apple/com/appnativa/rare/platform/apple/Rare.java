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

package com.appnativa.rare.platform.apple;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iResourceFinder;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aRare;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.ScriptManager;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.NameValuePair;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIColorHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.WindowManager;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.viewer.ExternalBrowserViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.PropertyResourceBundle;
import com.appnativa.util.Streams.ISO88591Reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLConnection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rare extends aRare {
  private static long    startTime = System.currentTimeMillis();
  private static boolean debuggingEnabled;

  static {
    staticInitialize();
  }

  public Rare() {
    appContext = new AppContext(this);

    OrderedProperties p = new OrderedProperties();

    try {
      p.load(new FileInputStream(((AppContext) appContext).makeResourcePath("raw/rare_raw_bundle", "properties")));
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    resources = new PropertyResourceBundle(p);
    Platform.setPlatform(new PlatformImpl((AppContext) appContext));
    initialize();
  }

  @Override
  public void ignoreException(String msg, Throwable e) {
    if (isDebugEnabled()) {
      if ((msg != null) && (e == null)) {
        log(Level.FINE, msg, null);
      } else if (e != null) {
        if (msg == null) {
          msg = "ignoreException";
        }

        log(Level.FINE, msg, e);
      }
    }
  }

  /**
   * Logs a FINE error to the default log
   *
   * @param level the logging level
   * @param msg the message to log
   * @param throwable the error
   */
  public static void log(Level level, String msg, Throwable throwable) {
    AppContext app = AppContext.getContext();
    Logger     l   = app.getLogger();

    if (throwable == null) {
      if (msg != null) {
        l.log(level, msg);
      }
    } else {
      l.log(level, msg, throwable);
    }
  }

  /**
   * Logs a warning  message to the default error log
   *
   * @param msg the message to log
   * @param throwable the error
   */
  public static void logWarning(String msg, Throwable throwable) {
    log(Level.WARNING, msg, throwable);
  }

  public void setupApplicationObjectEx(iURLConnection ic, String local) throws Exception {
    String  mime   = null;
    boolean xml    = false;
    boolean sdf    = false;
    Reader  stream = null;

    ic.open();

    URL    url  = ic.getURL();
    String file = url.getFile();

    mime = ic.getContentType();

    if (mime == null) {
      mime = "text/x-sdf";
    }

    int n = mime.indexOf(';');

    if (n > 0) {
      mime = mime.substring(0, n);
    }

    if (mime.startsWith(iConstants.XML_MIME_TYPE)) {
      xml = true;
    } else if (mime.startsWith(iConstants.SDF_MIME_TYPE)) {
      sdf = true;
    } else if (file.endsWith(".xml") || file.endsWith(".sxdf")) {
      xml = true;
    } else if (file.endsWith(".sdf")) {
      sdf = true;
    }

    if (xml || sdf) {
      stream = ic.getReader();
    }

    SPOTSequence spot = null;

    setContextURL(url);

    if (xml) {
      throw new UnsupportedOperationException("XML format not supported");
    } else if (sdf) {
      spot = (SPOTSequence) DataParser.loadSPOTObjectSDF(getRootViewer(), stream, null, mime, url);
    }

    ic.dispose();
    setupApplicationObject(url, spot, mime);
  }

  /**
   * Retrieves a data converter instance for the specified class from the
   * converter pool.
   *
   * @param cls
   *          the data converter class
   *
   * @return an instance of the class
   */
  public static iDataConverter getDataConverter(Class cls) {
    if (dataConverters == null) {
      dataConverters = new ConcurrentHashMap<Class, iDataConverter>();
    }

    iDataConverter cvt = dataConverters.get(cls);

    if (cvt == null) {
      try {
        Object o = cls.newInstance();

        if (o instanceof iDataConverter) {
          cvt = (iDataConverter) o;
        }
      } catch(Exception ex) {
        AppContext.getDefaultExceptionHandler(null).handleException(ex);

        return null;
      }

      dataConverters.put(cls, cvt);
    }

    return cvt;
  }

  /**
   * Gets the class for a named data converter. The name is either a fully
   * qualified class or a short name of one of the built in converters.
   *
   * @param name
   *          the name of the converter
   *
   * @return the class for the specified name
   *
   * @throws ClassNotFoundException
   */
  public static Class getDataConverterClass(String name) throws ClassNotFoundException {
    if (name == null) {
      return null;
    }

    if (name.indexOf('.') == -1) {
      String s = aConverter.class.getName();
      int    n = s.lastIndexOf('.');

      if (n != -1) {
        s = s.substring(0, n);
      }

      if (!name.endsWith("Converter")) {
        name = s + "." + name + "Converter";
      } else {
        name = s + "." + name;
      }
    }

    return PlatformHelper.loadClass(name);
  }

  @Override
  public iFunctionHandler getFunctionHandler() {
    if (functionHandler == null) {
      functionHandler = new Functions();
    }

    return functionHandler;
  }

  /**
   * Returns a reader for the specified connection
   *
   * @param conn
   *          the connection
   * @return the reader for the specified URL
   * @throws IOException
   */
  public static Reader getReader(URLConnection conn) throws IOException {
    String type = conn.getContentType();

    try {
      return new InputStreamReader(conn.getInputStream(), JavaURLConnection.getCharset(type, null));
    } catch(UnsupportedEncodingException ex) {
      AppContext.ignoreException(null, ex);
    }

    return new ISO88591Reader(conn.getInputStream());
  }

  /**
   * Returns the time in milliseconds when the application was started
   * @return  the time in milliseconds when the application was started
   */
  public static long getStartTime() {
    return startTime;
  }

  /**
   * Returns the amount of time that has elapsed (in milliseconds) since the application was started
   * @return  the amount of time that has elapsed (in milliseconds) since the application was started
   */
  public static long getTimeSinceStarted() {
    return System.currentTimeMillis() - startTime;
  }

  public static String getUserAgent() {
    return RARE_USER_AGENT;
  }

  /**
   * Opens a connection to the object referenced by the URL argument
   *
   * @param url
   *          the URL
   *
   * @return the connection handler
   *
   * @throws IOException
   */

  /**
   * Whether the application was configured to run in debug mode
   *
   * @return true if the application was configured to run in debug mode; false
   *         otherwise
   */
  public static boolean isDebugEnabled() {
    return debugEnabled;
  }

  public static boolean isInSandbox() {
    return false;
  }

  protected void abort(final Throwable e) {
    handleFatalEexception(e);
    shuttingDown = true;
    appContext.dispose();
  }

  protected void argsInitialized() {
    ScriptManager.createEngineManager();

    String appName = Platform.getProperty("rare.userAgentAppName");

    if (appName == null) {
      appName = Platform.getProperty("jnlp.rare.userAgentAppName");
    }

    String s = Float.toString(Platform.getOsVersion());
    int    n = s.indexOf('.');

    if (n + 1 < s.length()) {
      s = s.substring(0, n + 1);
    }

    createUserAgentString(Platform.getOsType() + " " + s, appName);
  }

  @Override
  protected void configure(Application app, URL context) {
    super.configure(app, context);
    windowManager = new WindowManager(((AppContext) appContext));
    windowManager.setContextURL(context);
    windowManager.configure(sageApplication.getMainWindowReference());
    fireApplicationDidInit();
  }

  @Override
  protected void createCellRenderingDefaults() {}

  @Override
  protected iContainer createNullViewer() {
    return new NullViewer(appContext);
  }

  @Override
  protected iScriptHandler createScriptHandler(Application app) {
    return new ScriptManager(appContext, app);
  }

  @Override
  protected void exitEx() {
    try {
      if (!shuttingDown && listenersCanExit()) {
        shuttingDown = true;
        fireApplicationExiting();

        if (windowManager != null) {
          windowManager.dispose();
          windowManager = null;
        }

        appContext.dispose();
        appContext = null;
      }
    } catch(Throwable e) {
      logWarning("Shutdown Error", e);
    }
  }

  @Override
  protected void handleAuthFailure(final URL url, final int retries, final String local) {}

  protected void handleFatalEexception(final Throwable e) {
    e.printStackTrace();
  }

  @Override
  protected iURLConnection handleIfFileOrLibURL(URL url, String mimeType) throws IOException {
    return null;
  }

  @Override
  protected void initApplication(Application app) {
    super.initApplication(app);

    SPOTSet set = app.getSupplementalJars();

    this.registerJARs(set);
    set = resolveSet(app.getSupplementalFonts(), NameValuePair.class);

    int           len = (set == null)
                        ? 0
                        : set.getCount();
    NameValuePair pair;
    String        value, s;

    for (int i = 0; i < len; i++) {
      pair  = (NameValuePair) set.get(i);
      s     = pair.getName();
      value = pair.getValue();

      if ((value != null) && (value.length() > 0) && (s != null) && (s.length() > 0)) {
        try {
          FontUtils.loadFont(s, getURL(value), pair.spot_getAttribute("type"));
        } catch(Exception ex) {
          getDefaultExceptionHandler().ignoreException(null, ex);
        }
      }
    }
  }

  /**
   * Registers the specified set of JARs with the class loader
   *
   * @param set the JARS to register
   */
  @Override
  protected void registerJARs(SPOTSet set) {
    int len = (set == null)
              ? 0
              : set.getCount();

    if (len == 0) {
      return;
    }

    URL                 url;
    URL                 context = contextURL;
    SPOTPrintableString ss;
    String              ar = getApplicationRoot();

    for (int i = 0; i < len; i++) {
      try {
        ss = (SPOTPrintableString) set.get(i);

        if (okForOS(ss)) {
          String s = ss.getValue();

          if ((ar != null) && s.startsWith("/")) {
            s = ar + s;
          }

          url = new URL(context, s);
          Platform.getPlatform().addJarURL(url);
        }
      } catch(Throwable ex) {
        getDefaultExceptionHandler().handleException(ex);
      }
    }
  }

  @Override
  protected void setupOsSpecificInfo(Application app, URL url, String mime) {
    if (app == null) {
      if (mime != null) {
        Class cls = getWidgetHandler(mime);

        if (cls != null) {
          if (cls == ExternalBrowserViewer.class) {
            if (Platform.browseURL(url)) {
              System.exit(0);
            }
          } else {
            sageApplication = new Application();
            sageApplication.getMainWindowReference().viewer.spot_setAttribute("url",
                    JavaURLConnection.toExternalForm(url));
            app = sageApplication;
          }
        }
      }
    }

    if (app == null) {
      throw new ApplicationException(
          PlatformHelper.format(
            appContext.getResourceAsString("Rare.runtime.text.unknownApplication"),
            JavaURLConnection.toExternalForm(url)));
    }

    ((AppContext) appContext).setupUIDefaults();
  }

  @Override
  protected void setupSelectionPainter(GridCell gc) {
    if (gc != null) {
      selectionPainter = UIColorHelper.configure(getRootViewer(), gc, null);
    } else {
      selectionPainter = new SelectionPainter(false);
    }
  }

  @Override
  protected void start() {
    if ((sageApplication == null) && (info != null)) {
      if (info.dumpSDF || info.dumpXML) {
        System.exit(1);

        return;
      }

      promptForURL(info, info.applicationFile);

      return;
    }

    startApplication();
  }

  protected void startApplication() {
    configure(sageApplication, contextURL);

    if (!shuttingDown) {
      if (isDebugEnabled()) {
        System.err.println("Application initialized in " + (((double) getTimeSinceStarted()) / 1000f) + " secs");
      }

      if (sageApplication.getMainWindowReference().visible.booleanValue()) {
        windowManager.showWindow();
      }
    }

    sageApplication = null;
  }

  @Override
  protected boolean isImageLoaderUsefull() {
    return true;
  }

  private static void staticInitialize() {
    URLConnection.setFileNameMap(new MIMEMap());
    debugEnabled = isDebugBuild();
    PlatformHelper.initialize();
  }

  private String getApplicationRoot() {
    final iResourceFinder rf = appContext.getResourceFinder();

    return (rf != null)
           ? rf.getApplicationRoot()
           : applicationRoot;
  }

  private native static boolean isDebugBuild()
  /*-[
  #if DEBUG
        return YES;
  #else
        return NO;
  #endif
  ]-*/
  ;

  private static class NullViewer extends aContainer {
    NullViewer(iPlatformAppContext app) {
      super(null);
      appContext = app;
    }

    @Override
    public void clearContents() {}

    @Override
    public void configure(Viewer vcfg) {}

    @Override
    public void dispose() {
      disposed = true;
    }

    @Override
    public void imageLoaded(UIImage image) {}

    @Override
    public void repaint() {}

    @Override
    public void update() {}

    @Override
    public iPlatformComponent getContainerComponent() {
      return null;
    }

    @Override
    public iContainer getParent() {
      iWindowManager wm = appContext.getWindowManager();

      return (wm == null)
             ? null
             : wm.getRootViewer();
    }

    @Override
    public WindowViewer getWindow() {
      return appContext.getWindowViewer();
    }
  }
}
