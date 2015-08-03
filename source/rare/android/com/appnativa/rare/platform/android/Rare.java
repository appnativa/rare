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

package com.appnativa.rare.platform.android;

import android.app.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.graphics.Point;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Display;
import android.view.View;
import android.view.Window;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.StreamURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aRare;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.platform.android.ui.util.AsyncFuture;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.ScriptManager;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.WindowManager;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSet;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams.ISO88591Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Don DeCoteau
 */
public class Rare extends aRare implements android.view.ViewTreeObserver.OnGlobalFocusChangeListener {
  static {
    debugEnabled = "android_id".equals(android.provider.Settings.Secure.ANDROID_ID);
    staticInitialize();
  }

  Context       androidContext;
  Configuration deviceConfiguration;
  Activity      mainActivity;
  Activity      startupActivity;
  boolean       useThemeColors;

  public Rare(Context context, Activity activity, boolean useThemeColors) {
    super();
    androidContext      = context;
    startupActivity     = activity;
    appContext          = new AppContext(this);
    this.useThemeColors = useThemeColors;

    try {
      Integer id = AppContext.getResourceId(activity, "raw/rare_raw_bundle");

      resources = new PropertyResourceBundle(AppContext.getAndroidContext().getResources().openRawResource(id));
    } catch(IOException ex) {
      Log.e("Rare", "resourcebundle", ex);
    }

    Platform.setPlatform(new PlatformImpl((AppContext) appContext));
    initialize();

    String app = null;

    try {
      PackageManager manager = activity.getPackageManager();
      PackageInfo    info    = manager.getPackageInfo(activity.getPackageName(), 0);

      app = info.packageName + "/" + info.versionCode;
    } catch(Exception ignore) {}

    Display        d  = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics dm = new DisplayMetrics();

    d.getMetrics(dm);

    Point p = new Point();

    d.getSize(p);

    String s = "; Size/" + p.x + 'x' + p.y + "; Density/" + SNumber.toString(dm.density);

    createUserAgentString("Android " + android.os.Build.VERSION.RELEASE + s, app);
    ScriptManager.createEngineManager(PlatformHelper.getApplicationClassLoader(), 0);
    ScriptManager.registerEngineFactory("com.sun.phobos.script.javascript.RhinoScriptEngineFactory");
  }

  public void ignoreException(String msg, Throwable e) {
    if ((msg != null) && (e == null)) {
      Log.d("Rare", msg);
    } else {
      if (msg == null) {
        msg = "ignoreException";
      }

      Log.d("Rare", msg, e);
    }
  }

  public void onGlobalFocusChanged(View oldFocus, View newFocus) {
    if (newFocus != null) {
      ((AppContext) appContext).setPermanentFocusOwner(Component.fromView(newFocus));
    }

    ((AppContext) appContext).setFocusOwner((newFocus == null)
            ? null
            : appContext.getPermanentFocusOwner());
  }

  public Activity getActivity() {
    return (mainActivity == null)
           ? startupActivity
           : mainActivity;
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

  public static String getUserAgent() {
    return RARE_USER_AGENT;
  }

  public boolean isBackPressedHandled() {
    if (windowManager == null) {
      return false;
    }

    return windowManager.isBackPressedHandled();
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

  void configure(Activity a, Application app, URL context) {
    deviceConfiguration = new Configuration(a.getResources().getConfiguration());
    mainActivity        = a;
    applicationName     = a.getApplication().getApplicationInfo().name;

    if (app.enableHTTPResponseCacheing.booleanValue()) {
      int     size         = SNumber.intValue(app.enableHTTPResponseCacheing.spot_getAttribute("mbMaxCacheSize"));
      boolean deleteOnExit = SNumber.booleanValue(app.enableHTTPResponseCacheing.spot_getAttribute("deleteOnExit"));
      boolean usePlatform  = SNumber.booleanValue(app.enableHTTPResponseCacheing.spot_getAttribute("usePlatformCache"));
      String  name         = app.enableHTTPResponseCacheing.spot_getAttribute("cacheName");

      if ((name == null) || (name.length() == 0)) {
        name = "http";
      }

      if (size == 0) {
        size = 10;
      }

      if (size > 0) {
        ((MainActivity) a).enableHttpResponseCache(name, size, deleteOnExit, usePlatform);
      }
    }

    if (app.autoAdjustFontSize.booleanValue()) {
      AndroidHelper.adjustFontSizeForScreen();
    }

    super.configure(app, context);
    windowManager = new WindowManager(((AppContext) appContext), a);

    Window win = (Window) windowManager.getMainWindow().getUIWindow();

    win.getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(this);
    windowManager.setContextURL(context);
    windowManager.configure(sageApplication.getMainWindowReference());
    ((MainActivity) a).applicationConfigured();
    fireApplicationDidInit();
  }

  void setContentView(MainActivity a) throws Exception {
    if (mainActivity == null) {
      configure(a, sageApplication, contextURL);
    } else {
      mainActivity = a;

      if (a instanceof MainActivity) {
        ((MainActivity) a).handleConfigurationChanged(a.getResources().getConfiguration());
      }
    }
  }

  iURLConnection getAssetsConnection(URL url) throws IOException {
    String file = url.getFile();

    file = file.substring("/android_asset/".length());

    InputStream in  = androidContext.getAssets().open(file);
    int         len = Math.max(in.available(), 8192);

    return new StreamURLConnection(url, in, (len > 255)
            ? len
            : -1);
  }

  protected void createCellRenderingDefaults() {}

  protected iContainer createNullViewer() {
    return new NullViewer(appContext);
  }

  protected iScriptHandler createScriptHandler(Application app) {
    return new ScriptManager(appContext, app);
  }

  protected void handleAuthFailure(final URL url, final int retries, final String local) {
    iFunctionCallback cb = new iFunctionCallback() {
      public void finished(boolean canceled, Object returnValue) {
        try {
          createApplicationObjectEx(url, retries + 1, local);
        } catch(Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    };

    AndroidHelper.errorMessage(appContext, appContext.getResourceAsString("Rare.text.error"),
                               appContext.getResourceAsString("Rare.runtime.text.authFailure"), cb);
  }

  protected iURLConnection handleIfFileOrLibURL(URL url, String mimeType) throws IOException {
    if (url.getProtocol().equals("file") && url.getFile().startsWith("/android_asset/")) {
      return getAssetsConnection(url);
    }

    return null;
  }

  /**
   * Registers the specified set of JARs with the class loader
   *
   * @param set
   *          the JARS to register
   */
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

    for (int i = 0; i < len; i++) {
      try {
        ss = (SPOTPrintableString) set.get(i);

        if (okForOS(ss)) {
          String s = ss.getValue();

          url = new URL(context, s);
          StandardClassLoader.addJAR(url);
        }
      } catch(Throwable ex) {
        getDefaultExceptionHandler().handleException(ex);
      }
    }

    StandardClassLoader.waitForJars();
  }

  protected void setupOsSpecificInfo(Application app, URL url, String mime) {
    String s = app.getMainWindowReference().customProperties.getValue();

    if (s != null) {
      Map<String, String> map = CharScanner.parseOptionStringEx(s, ',');

      s = map.get("rare:mobile.orientation");

      if (s != null) {
        if (s.equals("landscape")) {
          startupActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (s.equals("portrait")) {
          startupActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
      }

      s = map.get("rare:mobile.theme");
    }

    ((MainActivity) appContext.getActivity()).applicationObjectCreated(app, url);
    ((AppContext) appContext).setupUIDefaults();
  }

  @Override
  protected void start() {}

  protected void stopBackgroundThreads() {
    AsyncFuture.cancelAllPending();
    super.stopBackgroundThreads();
  }

  protected URL setApplicationURL(URL url) {
    URL u = super.setApplicationURL(url);

    if (startupActivity instanceof iActivity) {
      u = ((iActivity) startupActivity).validateApplicationURL(appContext, url);
    } else if (mainActivity instanceof iActivity) {
      u = ((iActivity) mainActivity).validateApplicationURL(appContext, url);
    }

    return u;
  }

  private static void staticInitialize() {
    URLConnection.setFileNameMap(new MIMEMap());
  }

  private static class NullViewer extends aContainer {
    NullViewer(iPlatformAppContext app) {
      super(null);
      appContext = app;
    }

    public void clearContents() {}

    public void configure(Viewer vcfg) {}

    public void dispose() {
      disposed = true;
    }

    @Override
    public void imageLoaded(UIImage image) {}

    @Override
    public void repaint() {}

    @Override
    public void update() {}

    public Context getAndroidContext() {
      return AppContext.getAndroidContext();
    }

    public iPlatformComponent getContainerComponent() {
      return null;
    }

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
