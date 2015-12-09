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

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.AbortOperationException;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iResourceFinder;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.ActionHelper;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aRare;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.ScriptManager;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.NameValuePair;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WindowManager;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharScanner;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.Streams.ISO88591Reader;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLConnection;

import java.security.Policy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.tree.ExpandVetoException;

/**
 *
 * @author Don DeCoteau
 */
public class Rare extends aRare implements AWTEventListener {
  static final String    resourcePath = "com/appnativa/rare/resources/drawable";
  static final String    rawResourcePath = "com/appnativa/rare/resources/raw";
  static boolean         anApplet;
  static boolean         debuggingEnabled;
  static boolean         inSandbox;
  static long            startTime;
  static boolean         webContext;
  private static boolean fontAdjusted;

  static {
    staticInitialize();
  }

  boolean                        shellEnabled;
  int                            shellPort;
  protected List<String>         commandLineArgs;
  protected Class<WindowManager> windowManagerClass;

  public Rare() {
    super();
    setupAppContextAndPlatform();

    if (resources == null) {
      resources = ResourceBundle.getBundle(getResourceBundleName());
    }

    initialize();
  }

  @Override
  public iContainer createNullViewer() {
    return new NullViewer(appContext);
  }

  @Override
  public void ignoreException(String msg, Throwable e) {
    Level l = Level.FINE;

    if (Platform.isDebugEnabled()) {
      l = Level.WARNING;
    }

    if ((msg != null) && (e == null)) {
      log(l, msg, null);
    } else if (e != null) {
      if (msg == null) {
        msg = "ignoreException";
      }

      log(l, msg, e);
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

  /**
   * Creates and returns a path to the resources location
   * from the specified relative path
   *
   * @param relativePath the relative resource path
   *
   * @return the absolute path corresponding to the relative path
   */
  public static String makeResourcePath(String relativePath) {
    return resourcePath + "/" + relativePath;
  }


  /**
   * Creates and returns a path to the resources location
   * from the specified relative path
   *
   * @param relativePath the relative resource path
   *
   * @return the absolute path corresponding to the relative path
   */
  public static String makeRawResourcePath(String relativePath) {
    return rawResourcePath + "/" + relativePath;
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
    } else if (mime.startsWith(iConstants.SDF_MIME_TYPE) || mime.startsWith(iConstants.RML_MIME_TYPE)) {
      sdf = true;
    } else if (file.endsWith(".xml")) {
      xml = true;
    } else if (file.endsWith(".sdf") || file.endsWith(".rml")) {
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

      if (cvt != null) {
        dataConverters.put(cls, cvt);
      }
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
      Platform.ignoreException(null, ex);
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
    return inSandbox;
  }

  void addLibs(String libs) {
    if ((libs == null) || (libs.length() == 0)) {
      return;
    }

    char c = ' ';

    if (libs.indexOf(File.pathSeparatorChar) != -1) {
      c = File.pathSeparatorChar;
    }

    List<String> list = CharScanner.getTokens(libs, c, true);

    if (list != null) {
      int  len = list.size();
      File f;

      for (int i = 0; i < len; i++) {
        try {
          f = new File(list.get(i).trim());

          if (f.exists()) {
            f = f.getCanonicalFile();
            Platform.getPlatform().addJarURL(f.toURI().toURL());
          }
        } catch(Exception e) {
          ignoreException(null, e);
        }
      }
    }
  }

  protected void abort(final Throwable e) {
    handleFatalEexception(e);
    shuttingDown = true;
    appContext.dispose();
  }

  protected void argsInitialized() {
    String appName = Platform.getProperty("rare.userAgentAppName");

    if (appName == null) {
      appName = Platform.getProperty("jnlp.rare.userAgentAppName");
    }

    createUserAgentString("swing " + Platform.getProperty("java.version"), appName);
    ScriptManager.createEngineManager(PlatformHelper.getApplicationClassLoader(), shellEnabled
            ? shellPort
            : 0);
    ScriptManager.registerEngineFactory("com.sun.phobos.script.javascript.RhinoScriptEngineFactory");
  }

  @Override
  protected void configure(Application app, URL context) {
    if (appContext.isEmbeddedInstance()) {
      fontAdjusted = true;
    }

    String s = app.desktopIconDensity.getValue();

    if (s != null) {
      ImageHelper.defaultIconDensity = s;
    }

    MainWindow mw = app.getMainWindow();

    if (!fontAdjusted &&!mw.font.spot_hasValue() && app.autoAdjustFontSize.booleanValue()) {
      fontAdjusted = true;

      float  screenDpi = Toolkit.getDefaultToolkit().getScreenResolution();
      UIFont f         = FontUtils.getSystemFont();
      float  size;

      if (Platform.isMac()) {
        size = Math.min(16, (float) Math.ceil(f.getSize2D() * (screenDpi / 96f)));
        size = f.getSize2D() + 1;
      } else {
        size = Math.min(16, (float) Math.ceil(f.getSize2D() * (screenDpi / 96f)));
      }

      if (size != f.getSize2D()) {
        f = f.deriveFont(size);
        UIManager.put("Label.font", f);
        FontUtils.setSystemFont(f);
      }
    }

    super.configure(app, context);
    createWindowManager(context, app.getMainWindowReference());
    fireApplicationDidInit();
  }

  /**
   * Configures the environment for property settings
   *
   * @param props a set of properties to use to configure the environment
   *
   * @throws Exception
   */
  protected void configureFromProperties(OrderedProperties props) throws Exception {
    String s = props.getProperty("windowManager");

    if (s != null) {
      try {
        windowManagerClass = PlatformHelper.loadClass(s);
      } catch(Throwable e) {
        abort(e);
      }
    }

    s = props.getProperty("printHandler");

    if ((s == null) ||!s.contains(".")) {
      printHandlerClassName = "com.appnativa.rare.print.PrintHandler";
    }

    s = props.getProperty("mimeTypes");

    if (s != null) {
      File f = new File(s);

      if (f.exists()) {
        loadMIMETypeMappings(new ActionLink(getRootViewer(), f.toURI().toURL()), false);
      }
    }

    s = props.getProperty("scriptEngineFactories");

    if ((s != null) && (s.length() > 0)) {
      ScriptManager.registerEngineFactories(s);
    }

    Iterator it = props.keySet().iterator();
    Object   value;

    while(it.hasNext()) {
      s = (String) it.next();

      if (s.startsWith("ui")) {
        value = props.get(s);
        s     = s.substring(2);
        value = Utils.resolveUIProperty(appContext.getRootViewer(), s, (String) value);

        if (value != null) {
          Platform.getUIDefaults().put(s, value);
        }
      }
    }
  }

  @Override
  protected void createCellRenderingDefaults() {}

  @Override
  protected iScriptHandler createScriptHandler(Application app) {
    return new ScriptManager(appContext, app);
  }

  protected void createWindowManager(URL context, MainWindow mainWindow) {
    if (windowManagerClass != null) {
      try {
        Constructor c = windowManagerClass.getConstructor(AppContext.class);

        windowManager = (iPlatformWindowManager) c.newInstance(appContext);
      } catch(Exception ex) {
        abort(ex);

        return;
      }
    } else {
      windowManager = new WindowManager(((AppContext) appContext));
    }

    windowManager.setContextURL(context);
    windowManager.configure(mainWindow);
  }

  @Override
  protected void handleAuthFailure(final URL url, final int retries, final String local) {
    AlertPanel d = AlertPanel.ok(getRootViewer(), appContext.getResourceAsString("Rare.runtime.text.error"),
                                 appContext.getResourceAsString("Rare.runtime.text.authFailure"), null);

    d.showDialog(new iFunctionCallback() {
      @Override
      public void finished(boolean canceled, Object returnValue) {
        try {
          createApplicationObjectEx(url, retries + 1, local);
        } catch(Exception e) {
          abort(e);
        }
      }
    });
  }

  protected void handleFatalEexception(final Throwable e) {
    if (e == null) {
      return;
    }

    try {
      if (!(e instanceof AbortOperationException)) {
        final ErrorInformation ei =
          new ErrorInformation(appContext.getResourceAsString("Rare.runtime.text.fatalError"), e);

        if (Platform.isUIThread()) {
          AlertPanel.showErrorDialog(ei);
        } else {
          Runnable r = new Runnable() {
            @Override
            public void run() {
              AlertPanel.showErrorDialog(ei);
            }
          };

          Platform.invokeLater(r);
        }
      } else {
        e.printStackTrace();
      }
    } catch(Throwable ex) {
      e.printStackTrace();
    }
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

  @Override
  protected void registerDefaultActions() {
    super.registerDefaultActions();

    UIAction a;
    int      mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    a = ActionHelper.getCutAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, mask));
    a = ActionHelper.getCopyAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, mask));
    a = ActionHelper.getPasteAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, mask));
    a = ActionHelper.getDeleteAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
    a = ActionHelper.getSelectAllAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, mask));
    a = ActionHelper.getUndoAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, mask));
    a = ActionHelper.getRedoAction();
    a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, mask));
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

  protected void setupAppContextAndPlatform() {
    appContext = new AppContext(this);
    Platform.setPlatform(new PlatformImpl((AppContext) appContext));
  }

  @Override
  protected void setupOsSpecificInfo(Application app, URL url, String mime) {
    if (app == null) {
      if (mime != null) {
        Class cls = getWidgetHandler(mime);

        if (cls != null) {
          sageApplication = new Application();
          sageApplication.getMainWindowReference().viewer.spot_setAttribute("url",
                  JavaURLConnection.toExternalForm(url));
          app = sageApplication;
        }
      }
    }

    if (app == null) {
      throw new ApplicationException(
          String.format(
            appContext.getResourceAsString("Rare.runtime.text.unknownApplication"),
            JavaURLConnection.toExternalForm(url)));
    }

    String manager = app.getMainWindowReference().spot_getAttribute("manager");

    if ((manager != null) && (manager.length() > 0)) {
      if (!manager.contains(".")) {
        windowManagerClass = WindowManager.class;
      } else {
        try {
          windowManagerClass = PlatformHelper.loadClass(manager);
        } catch(ClassNotFoundException e) {
          abort(e);
        }
      }
    }

    ((AppContext) appContext).setupUIDefaults();
  }

  /**
   * Sets up security for the environment based on the specified properties
   *
   * @param props a set of properties to use to for configuration information
   */
  protected void setupSecurity(OrderedProperties props) {
    String sp = props.getProperty("securityPolicy");

    if ((sp != null) && (sp.length() > 0)) {
      try {
        Policy.setPolicy(null);
        System.setProperty("java.security.policy", sp);

        Policy policy = Policy.getPolicy();

        if (policy == null) {
          throw new SecurityException(appContext.getResourceAsString("Rare.runtime.text.invalidSecPolicy"));
        }

        Policy.setPolicy(policy);
      } catch(Exception e) {
        abort(e);
      }
    }

    String sm = props.getProperty("securityManager");

    if ((sm != null) && (sm.length() > 0)) {
      try {
        if (sm.equals("default")) {
          System.setSecurityManager(new SecurityManager());
        } else {
          Class  cls = Class.forName(sm);
          Object ism = cls.newInstance();

          if (ism instanceof SecurityManager) {
            System.setSecurityManager((SecurityManager) ism);
          } else {
            throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.invalidSecManager"));
          }
        }
      } catch(Exception e) {
        abort(e);
      }
    }
  }

  @Override
  protected void start() {
    if (!EventQueue.isDispatchThread()) {
      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          startOnEDT();
        }
      });
    } else {
      startOnEDT();
    }
  }

  protected void startApplication() {
    configure(sageApplication, contextURL);

    if (!shuttingDown) {
      if (isDebugEnabled() &&!appContext.isEmbeddedInstance()) {
        System.err.println("Application initialized in " + (((double) getTimeSinceStarted()) / 1000f) + " secs");
      }

      if (sageApplication.getMainWindowReference().visible.booleanValue()) {
        windowManager.showWindow();

        if (windowManager.getWorkspaceViewer() != null) {
          Platform.invokeLater(new Runnable() {
            
            @Override
            public void run() {
              windowManager.getWorkspaceViewer().requestFocus();
            }
          });
        }
      }

      inactivityTimeout = Platform.getUIDefaults().getInt("Rare.inactivityTimeout", 0);

      if (inactivityTimeout > 0) {
        try {
          java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<Object>() {
            @Override
            public Object run() {
              Toolkit.getDefaultToolkit().addAWTEventListener(Rare.this,
                      AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
              Platform.invokeLater(new Runnable() {
                @Override
                public void run() {
                  checkIfInactive();
                }
              }, inactivityTimeout);

              return null;
            }
          });
        } catch(SecurityException e) {
          throw new RuntimeException(e);
        }
      }
    }

    sageApplication = null;
  }

  protected String getResourceBundleName() {
    return Platform.class.getPackage().getName() + ".resources.raw.rare_raw_bundle";
  }

  private void startOnEDT() {
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

  private static void staticInitialize() {
    // Don't call any other Rare methods within this class as Rare is not yet initialized
    try {
      Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    } catch(SecurityException e) {
      ignoreStartupException("Cannot set uncaught exception handler", e);
    }

    String s = Platform.getProperty("rare.sslCiphers", Platform.getProperty("jnlp.rare.sslCiphers", null));

    if ((s != null) && (s.length() > 0)) {
      setCiphers(s);
    }

    if ("true".equalsIgnoreCase(Platform.getProperty("rare.trustAllCerts",
            Platform.getProperty("jnlp.rare.trustAllCerts", null)))) {
      trustAllCerts();
    }

    try {
      URLConnection.setFileNameMap(new MIMEMap());
    } catch(Throwable e) {
      inSandbox = true;
    }

    debugEnabled     = "true".equalsIgnoreCase(Platform.getProperty("rare.debug", "false"));
    debuggingEnabled = Platform.getProperty("rare.debugger", null) != null;

    if (debuggingEnabled) {
      debugEnabled = true;
    }

    if (!Applet.isRunningAsApplet()) {
      try {
        Class  smcls = Class.forName("javax.jnlp.ServiceManager");
        Class  bscls = Class.forName("javax.jnlp.BasicService");
        Method lm    = smcls.getMethod("lookup", new Class[] { String.class });
        Object o     = lm.invoke(null, new Object[] { "javax.jnlp.BasicService" });

        webContext = bscls.isInstance(o);
      } catch(Throwable e) {
        webContext = false;
      }
    } else {
      webContext = false;
    }

    try {
      String agent = iConstants.APPLICATION_NAME_STRING + "/" + iConstants.APPLICATION_VERSION_STRING;

      agent += " Locale/" + Locale.getDefault().toString() + " JavaFX/" + getJavaFXVersion();

      if (webContext) {
        agent += "/WebApp";
      }

      try {
        s = Platform.getProperty("os.name", null);

        if (s != null) {
          agent += " ( ; ; " + s;
          s     = Platform.getProperty("os.version", null);

          if (s != null) {
            agent += "/" + s;
          }

          agent += ")";
        }
      } catch(Exception e) {
        ignoreStartupException("Cannot create proper agent string", e);
      }

      RARE_USER_AGENT = agent;
      System.setProperty("http.agent", agent);
    } catch(Exception e) {
      ignoreStartupException("Cannot set http.agent", e);
    }

    s = Platform.getProperty("rare.scriptEngineFactories",
                             Platform.getProperty("jnlp.rare.scriptEngineFactories", null));

    if ((s != null) && (s.length() > 0)) {
      ScriptManager.registerEngineFactories(s);
    }

    try {
      s = System.getProperty("os.name", "Windows");

      if (s.contains("Windows")) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    } catch(Exception ignore) {}
  }

  private static void trustAllCerts() {
    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      @Override
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }
      @Override
      public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
      @Override
      public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
    } };

    // Install the all-trusting trust manager
    try {
      SSLContext sc = SSLContext.getInstance("TLS");

      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch(Exception e) {
      Platform.ignoreException("trustAllCerts=true", e);
    }
  }

  private static void setCiphers(String s) {
    try {
      String[]                ciphers = null;
      javax.net.ssl.SSLEngine ssl     = javax.net.ssl.SSLContext.getInstance("Default").createSSLEngine();

      if (s.equalsIgnoreCase("anonymous")) {
        ArrayList<String> list = new ArrayList<String>();

        ciphers = ssl.getSupportedCipherSuites();

        for (String c : ciphers) {
          if ((c != null) && c.contains("_anon_")) {
            list.add(c);
          }
        }

        ciphers = list.toArray(new String[list.size()]);
      } else {
        ciphers = CharScanner.getTokens(s, ',', true).toArray(new String[0]);
      }

      if ((ciphers != null) && (ciphers.length > 0)) {
        ssl.setEnabledCipherSuites(ciphers);
      }
    } catch(Throwable e) {
      Platform.ignoreException("sslCiphers=" + s, e);
    }
  }

  private String getApplicationRoot() {
    final iResourceFinder rf = appContext.getResourceFinder();

    return (rf != null)
           ? rf.getApplicationRoot()
           : applicationRoot;
  }

  private static String getJavaFXVersion() {
    return System.getProperty("javafx.version");
  }

  public static class NullViewer extends aContainer {
    public NullViewer(iPlatformAppContext app) {
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


  private static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    Throwable throwable;

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
      if (throwable instanceof ThreadDeath) {
        return;
      }

      if ((throwable instanceof AbortOperationException) || (throwable instanceof ExpandVetoException)) {
        if (throwable instanceof AbortOperationException) {
          AbortOperationException ae = (AbortOperationException) throwable;

          if (ae.shutdown()) {
            ae.getAppContext().exit();
          }
        }

        return;
      }

      synchronized(this) {
        if (this.throwable != null) {
          // An exception has occurred while we're trying to display
          // the current exception, bale.
          this.throwable.printStackTrace();
          this.throwable = null;

          return;
        } else {
          this.throwable = throwable;
        }
      }

      if (Platform.isInitialized() && Platform.getAppContext().isShuttingDown()) {
        return;
      }

      if (Platform.isInitialized() && (throwable instanceof InternalError)) {
        Platform.getAppContext().exit();

        return;
      }

      final iWidget w = Platform.getContextRootViewer();

      if (!Platform.isUIThread()) {
        Platform.invokeLater(new Runnable() {
          @Override
          public void run() {
            AppContext.getDefaultExceptionHandler(w).handleException(throwable);
          }
        });
      } else {
        AppContext.getDefaultExceptionHandler(w).handleException(throwable);
      }
    }
  }


  public static boolean isWebStart() {
    return webContext;
  }

  protected boolean applicationPaused;
  protected long    lastActionTime;
  protected int     inactivityTimeout;

  protected void checkIfInactive() {
    long time = System.currentTimeMillis();

    if (lastActionTime + inactivityTimeout < time) {
      if (!applicationPaused) {
        applicationPaused = true;
        fireApplicationPaused();
      }
    } else {
      int timeleft = (int) (time - lastActionTime);

      Platform.invokeLater(new Runnable() {
        @Override
        public void run() {
          checkIfInactive();
        }
      }, inactivityTimeout - timeleft);
    }
  }

  protected void resumeApplication() {
    lastActionTime = System.currentTimeMillis();

    if (applicationPaused) {
      applicationPaused = false;
      fireApplicationResumed();
    }
  }

  @Override
  public void eventDispatched(AWTEvent event) {
    resumeApplication();
  }
}
