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

package com.appnativa.rare;

import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.ActionLink.ReturnDataType;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iMultipartMimeHandler;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;
import com.appnativa.util.iURLResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.EventObject;
import java.util.List;
import java.util.Map;

public class Platform {
  public final static String RARE_PACKAGE_NAME      = "com.appnativa.rare";
  public final static String RARE_SPOT_PACKAGE_NAME = "com.appnativa.rare.spot";
  private static iPlatform   platform;

  /**
   * Opens the specified url using the platforms native web browser application.
   *
   * @param url
   *          the URL to browse
   * @return true if the the browser application was able to be launched (or no
   *         failure status was reported by the platform); false if it was not
   */
  public static boolean browseURL(URL url) {
    return platform.browseURL(url);
  }

  /**
   * Clears any session cookies
   */
  public static void clearSessionCookies() {
    PlatformHelper.clearSessionCookies();
  }

  /**
   * Closes all previously opened connections that are still open. Does nothing
   * if connections are not being tracked.
   *
   * @param log
   *          true to log information about open connections; false otherwise
   */
  public static void closeOpenConnections(boolean log) {
    JavaURLConnection.closeOpenConnections(log);
  }

  /**
   * Enables/disable the tracking of connections.
   *
   * When enabled the opening and closing of connections are tracked
   *
   * @param trackOpenConnections
   *          true to track; false otherwise
   */
  public static void setTrackOpenConnections(boolean trackOpenConnections) {
    JavaURLConnection.setTrackOpenConnections(trackOpenConnections);
  }

  /**
   * Returns a list of open connections if connection tracking was enabled
   *
   * @return a list of open connections or null
   */
  public static JavaURLConnection[] getOpenConnections() {
    return JavaURLConnection.getOpenConnections();
  }

  /**
   * Creates a file that can be used for caching data between application
   * invocations.
   *
   * @param name
   *          the name for the file
   *
   * @return a file handle or null if not supported
   */
  public static File createCacheFile(String name) {
    return platform.createCacheFile(name);
  }

  /**
   * Creates an object that is an instance of the the specified class
   *
   * @param className
   *          the class name
   *
   * @return the new object or null if the object could not be created
   */
  public static Object createObject(String className) {
    try {
      Class cls = loadClass(className);

      return cls.newInstance();
    } catch(Exception e) {
      return null;
    }
  }

  /**
   * Creates a platform component from a native component
   *
   * @param nativeComponent
   *          the native component
   *
   * @return the platform component
   */
  public static iPlatformComponent createPlatformComponent(Object nativeComponent) {
    return platform.createPlatformComponent(nativeComponent);
  }

  public static iTimer createTimer(String name) {
    return platform.createTimer(name);
  }

  /**
   * Creates a weak reference to the specified object
   *
   * @param value
   *          the object to hold weakly
   * @return a weak reference to the specified object
   */
  public static iWeakReference createWeakReference(Object value) {
    return PlatformHelper.createWeakReference(value);
  }

  /**
   * Writes a debug message to the console when running in debug mode
   *
   * @param msg
   *          the message to write
   */
  public static void debugLog(String msg) {
    if (platform == null) {
      System.err.println(msg);
    } else {
      platform.debugLog(msg);
    }
  }

  /**
   * Evaluates scripting code and returns the result
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param e
   *          the event object
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iWidget w, iScriptHandler sh, Object code, EventObject e) {
    return evaluate(w, sh, code, null, e);
  }

  /**
   * Evaluates scripting code and returns the result
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param event
   *          the event
   * @param e
   *          the event object
   *
   * @return the result of the executed function
   */
  public static Object evaluate(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    return platform.evaluate(w, sh, code, event, e);
  }

  /**
   * Executes the specified code.
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param e
   *          the the event object
   */
  public static void execute(iWidget w, iScriptHandler sh, Object code, EventObject e) {
    execute(w, sh, code, null, e);
  }

  /**
   * Executes the specified code.
   *
   * @param w
   *          the widget context
   * @param sh
   *          the script handler to use
   * @param code
   *          the code to be evaluated
   * @param event
   *          the event for which the code is being invoked
   * @param e
   *          the the event object
   */
  public static void execute(iWidget w, iScriptHandler sh, Object code, String event, EventObject e) {
    platform.execute(w, sh, code, event, e);
  }

  /**
   * Finds the lowest level platform component in the hierarchy for the given UI
   * entity
   *
   * @param source
   *          the source component
   * @return the component
   */
  public static iPlatformComponent findPlatformComponent(Object source) {
    return platform.findPlatformComponent(source);
  }

  /**
   * Finds the lowest level widget in the specified component's hierarchy
   *
   * @param c
   *          the native component
   *
   * @return the lowest level widget in the specified component's hierarchy
   */
  public static iWidget findWidgetForComponent(Object c) {
    return platform.findWidgetForComponent(c);
  }

  /**
   * Handles platform specified properties configured for a widget
   *
   * @param widget
   *          the widget
   * @param cfg
   *          the widget's configuration
   * @param properties
   *          the properties
   */
  public static void handlePlatformProperties(iWidget widget, Widget cfg, Map<String, Object> properties) {
    platform.handlePlatformProperties(widget, cfg, properties);
  }

  /**
   * Calls the default error handler ignoreException method. The defaults
   * behavior is to log the error to the error console when in debug mode
   *
   * @param msg a message to associate with the error (can be null);
   * @param throwable the error
   */
  public static void ignoreException(String msg, Throwable throwable) {
    if (platform == null) {
      if (msg != null) {
        System.err.println(msg);
      }

      throwable.printStackTrace();
    } else {
      platform.ignoreException(msg, throwable);
    }
  }

  /**
   * Calls the default error handler ignoreException method. The defaults
   * behavior is to log the error to the error console when in debug mode
   *
   * @param throwable the error
   */
  public static void ignoreException(Throwable throwable) {
    if (platform == null) {
      throwable.printStackTrace();
    } else {
      platform.ignoreException(null, throwable);
    }
  }

  /**
   * Invokes the specified runnable on the UI thread on the next execution pass
   * of that thread.
   *
   * @param r
   *          the runnable to invoke
   */
  public static void invokeLater(Runnable r) {
    platform.invokeLater(r);
  }

  /**
   * Invokes the specified runnable on the UI thread
   *
   * @param r
   *          the runnable to invoke
   */
  public static void runOnUIThread(Runnable r) {
    if (platform.isUIThread()) {
      r.run();
    } else {
      platform.invokeLater(r);
    }
  }

  /**
   * Invokes the specified runnable on the UI thread on the next execution pass
   * of that thread.
   *
   * @param r
   *          the runnable to invoke
   * @param delay
   *          number of milliseconds to delay before invoking the runnable
   */
  public static void invokeLater(Runnable r, int delay) {
    platform.invokeLater(r, delay);
  }

  /**
   * Loads the class with the specified name Use this method instead of
   * Class.forName when you want to ensure that supplemental JARs are searched
   * for the class and the ensure cross platform compatibility.
   *
   * @param className
   *          the name of the class to load
   *
   * @return the class with the specified name
   *
   * @throws ClassNotFoundException
   */
  public static Class loadClass(String className) throws ClassNotFoundException {
    String defaultPackage = Platform.getUIDefaults().getString("Rare.class.defaultPackage");

    if ((defaultPackage != null)) {
      int    n    = className.indexOf('.');
      String name = null;

      if (n == 0) {
        name = defaultPackage + className;
      } else if (n == -1) {
        name = defaultPackage + "." + className;
      }

      if (name != null) {
        try {
          Class cls = platform.loadClass(name);

          if (cls != null) {
            return cls;
          }
        } catch(Exception ignore) {}
      }
    }

    return platform.loadClass(className);
  }

  /**
   * Loads icon resources from a properties files
   *
   * @param app
   *          the application context
   * @param appIcons
   *          the icons holder
   * @param link
   *          the link to the properties file
   * @param clear
   *          true to clear the existing application icon pool first; false
   *          otherwise
   *
   * @return the loaded icons
   * @throws IOException
   */
  public static Map<String, UIImageIcon> loadResourceIcons(iPlatformAppContext app, Map<String, UIImageIcon> appIcons,
          ActionLink link, boolean clear, boolean defaultDeferred)
          throws IOException {
    return platform.loadResourceIcons(app, appIcons, link, clear, defaultDeferred);
  }

  /**
   * Loads string resources from a properties file
   *
   * @param app
   *          the application context
   * @param appStrings
   *          the string holder (can be null)
   * @param link
   *          the link to the properties file
   * @param clear
   *          true to clear the existing application string pool first; false
   *          otherwise
   *
   * @return the loaded strings
   * @throws IOException
   */
  public static Map<String, String> loadResourceStrings(iPlatformAppContext app, Map<String, String> appStrings,
          ActionLink link, boolean clear)
          throws IOException {
    return platform.loadResourceStrings(app, appStrings, link, clear);
  }

  /**
   * Loads UI properties from a properties file
   *
   * @param context
   *          the context
   * @param link
   *          the link to the properties file
   * @param defs
   *          the holder for the defaults
   *
   * @throws IOException
   */
  public static void loadUIProperties(iWidget context, ActionLink link, UIProperties defs) throws IOException {
    platform.loadUIProperties(context, link, defs);
  }

  /**
   * Opens the system mail client
   *
   * @param uri
   *          the mailto: uri or email address (can be null)
   *
   * @return true if the mail client was activated; false otherwise
   */
  public static boolean mailTo(String uri) {
    return platform.mailTo(uri);
  }

  /**
   * Opens the system mail client
   *
   * @param address
   *          the address to mail to
   * @param subject
   *          the e-mail subject (can be null)
   * @param body
   *          the body of the message (can be null)
   *
   * @return true if the mail client was activated; false otherwise
   */
  public static boolean mailTo(String address, String subject, String body) {
    return platform.mailTo(address, subject, body);
  }

  /**
   * Opens a connection to the object referenced by the URL argument
   *
   * @param url
   *          the URL
   * @param mimeType
   *          a MIME type to use to override the one returned by the server
   *
   * @return the connection handler
   *
   * @throws IOException
   */
  public static iURLConnection openConnection(URL url, String mimeType) throws IOException {
    if ((url != null) && Platform.isUIThread() && url.getProtocol().startsWith("http")) {
      Platform.debugLog("Network IO via UI Thread:" + url.toString());
    }

    return platform.getAppContext().openConnection(url, mimeType);
  }

  /**
   * Registers the component as belonging to the specified widget
   *
   * @param component
   *          the component to register
   * @param context
   *          the widget context
   */
  public static void registerWithWidget(iPlatformComponent component, iWidget context) {
    platform.registerWithWidget(component, context);
  }

  /**
   * Sends the specified data as HTTP form data
   *
   * @param context
   *          the context
   * @param link
   *          the link representing the data to be retrieved
   * @param data
   *          the data
   * @param multipart
   *          true to send the data as multi-part content
   * @param type
   *          the type of data to return
   * @param cb
   *          the callback to call when complete
   * @return a handle to use to be able to cancel the operation;
   *
   * @see com.appnativa.util.ObjectHolder
   *
   */
  public static iCancelable sendFormData(iWidget context, ActionLink link, Map<String, Object> data, boolean multipart,
          ReturnDataType type, iFunctionCallback cb) {
    return platform.sendFormData(context, link, data, multipart, type, cb);
  }

  /**
   * Unregisters the component as belonging to a specified widget
   *
   * @param component
   *          the component to unregister
   */
  public static void unregisterWithWidget(iPlatformComponent component) {
    platform.unregisterWithWidget(component);
  }

  /**
   * Uploads the specified data as a file
   *
   * @param context
   *          to context widget
   * @param data
   *          the data (can be a string or {@link Reader} or {@link InputStream}
   *          or {@link File}
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @param type
   *          the type of data to return
   * @return a handle to use to be able to cancel the operation;
   *
   * @see com.appnativa.util.ObjectHolder
   *
   */
  public static iCancelable uploadData(iWidget context, ActionLink link, Object data, String name, String mimeType,
          String fileName, ReturnDataType type, iFunctionCallback cb) {
    return platform.uploadData(context, link, data, name, mimeType, fileName, type, cb);
  }

  /**
   *
   * Set a cookie-2 value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public static void setCookie2Value(URL url, String value) {
    PlatformHelper.setCookie("Cookie2", url, value);
  }

  /**
   * Set a cookie value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public static void setCookieValue(URL url, String value) {
    PlatformHelper.setCookie("Cookie", url, value);
  }

  /**
   * Sets a global variable into the scripting environment. Use this method
   * instead of the native scripting language in order to access globals
   * variables from background threads
   *
   * @param name
   *          the name of the variable
   * @param value
   *          the value
   */
  public static void setGlobalVariable(String name, Object value) {
    platform.getAppContext().getRootViewer().getScriptHandler().setGlobalVariable(name, value);
  }

  public static void setPlatform(iPlatform p) {
    if (platform != null) {
      throw new InternalError("Cannot change platform");
    }

    platform = p;
  }

  /**
   * Returns whether the version of Android
   *
   * @return an integer representing the android build version
   */
  public static int getAndroidVersion() {
    return platform.getAndroidVersion();
  }

  /**
   * Gets the active application context
   *
   * @return the active application context
   */
  public static iPlatformAppContext getAppContext() {
    return (platform == null)
           ? null
           : platform.getAppContext();
  }

  /**
   * Gets the directory where cache files are created
   *
   * @return the directory where cache files are created
   */
  public static File getCacheDir() {
    return platform.getCacheDir();
  }

  public static iCancelable getContent(iWidget context, ActionLink link, ReturnDataType type, iFunctionCallback cb) {
    return platform.getContent(context, link, type, cb);
  }

  public static iViewer getContextRootViewer() {
    iPlatformAppContext ctx = (platform == null)
                              ? null
                              : platform.getAppContext();

    return (ctx == null)
           ? null
           : ctx.getRootViewer();
  }

  public static List getCookieList() {
    return platform.getCookieList();
  }

  public static String getCookies() {
    return platform.getCookies();
  }

  public static iDataConverter getDataConverter(Class cls) {
    return platform.getDataConverter(cls);
  }

  public static Class getDataConverterClass(String name) throws ClassNotFoundException {
    return platform.getDataConverterClass(name);
  }

  public static iExceptionHandler getDefaultExceptionHandler(iWidget context) {
    return (context == null)
           ? platform.getAppContext().getDefaultExceptionHandler()
           : context.getAppContext().getDefaultExceptionHandler();
  }

  public static iFunctionHandler getFunctionHandler() {
    return platform.getFunctionHandler();
  }

  public static Functions getGlobalFunctions() {
    return platform.getFunctionHandler().getFunctions();
  }

  /**
   * The version of the java environment
   *
   * @return a double representing version of the java environment
   */
  public static double getJavaVersion() {
    return platform.getJavaVersion();
  }

  public static String getOsType() {
    return platform.getOsType();
  }

  /**
   * The version of the underlying operating system
   *
   * @return a double representing version of the underlying operating system
   */
  public static float getOsVersion() {
    return platform.getOsVersion();
  }

  /**
   * Gets a handle to the platform
   *
   * @return a handle to the platform
   */
  public static iPlatform getPlatform() {
    return platform;
  }

  /**
   * Gets the platform component for the source UI object
   *
   * @param source
   *          the source object
   * @return the platform component or null if the source is not tied to a
   *         platform component
   */
  public static iPlatformComponent getPlatformComponent(Object source) {
    return platform.getPlatformComponent(source);
  }

  /**
   * Get the type of platform that the application is running on. In some
   * instances the os type and platform type will be the same (e.g. ios/ios) on
   * others the will be different(windows/swing or osx/swing)
   *
   * @return the type of operating system that the application is running on
   */
  public static String getPlatformType() {
    return platform.getPlatformType();
  }

  /**
   * The version of the platform that the runtime is built for.
   *
   * @return a double representing version of the underlying operating system
   */
  public static double getPlatformVersion() {
    return platform.getPlatformVersion();
  }

  /**
   * Gets the system property.
   *
   * @param key
   *          the property key
   * @return the system property.
   */
  public static String getProperty(String key) {
    try {
      return System.getProperty(key, null);
    } catch(Exception e) {
      return null;
    }
  }

  /**
   * Gets a system property.
   *
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value for the property.
   * @return the system property.
   */
  public static String getProperty(String key, String defaultValue) {
    try {
      return System.getProperty(key, defaultValue);
    } catch(Exception e) {
      return defaultValue;
    }
  }

  /**
   * Sets a system property.
   *
   * @param key
   *          the property key
   * @param value
   *          the value for the property.
   */
  public static void setProperty(String key, String value) {
    try {
      System.setProperty(key, value);
    } catch(Exception e) {}
  }

  public static iPlatformIcon getResourceAsIcon(String name) {
    return platform.getAppContext().getResourceAsIcon(name);
  }

  public static iPlatformIcon getResourceAsIconEx(String name) {
    return platform.getAppContext().getResourceAsIconEx(name);
  }

  public static String getResourceAsString(String name) {
    return platform.getAppContext().getResourceAsString(name);
  }

  /**
   * The version of the RARE runtime
   *
   * @return a double representing version of the RARE runtime
   */
  public static double getRuntimeVersion() {
    return iConstants.APPLICATION_VERSION;
  }

  /**
   * Returns the SPOT name for the class
   *
   * @param cls
   *          the configuration object class (e.g. Table.class)
   *
   * @return the SPOT name for the class
   */
  public static String getSPOTName(Class cls) {
    return cls.getName();
  }

  /**
   * Get the UIProperties for the current context
   *
   * @return the UIProperties for the current context
   */
  public static UIProperties getUIDefaults() {
    iPlatformAppContext app = (platform == null)
                              ? null
                              : platform.getAppContext();

    if (app == null) {
      return new UIProperties();    //allows us to use ColorUtils before environment is setup
    }

    return app.getUIDefaults();
  }

  /**
   * Gets the use agent string that will be sent with all http/https requests
   *
   * @return the use agent string
   */
  public static String getUserAgent() {
    return platform.getUserAgent();
  }

  /**
   * Get the widget that the specified platform object is registered with
   *
   * @param c
   *          the component
   *
   * @return the widget that the specified UI object is registered with or null
   *         if it is not registered
   */
  public static iWidget getWidgetForComponent(Object c) {
    iPlatformComponent pc = platform.getPlatformComponent(c);

    return (pc == null)
           ? null
           : pc.getWidget();
  }

  /**
   * Get the topmost window viewer. The is the window viewer associated with the
   * main window.
   *
   * @return the topmost window viewer
   */
  public static WindowViewer getWindowViewer() {
    return platform.getAppContext().getMainWindowViewer();
  }

  /**
   * Returns the window viewer associated with the specified component.
   *
   * @param c
   *          the component
   * @return the window viewer associated with the component
   */
  public static WindowViewer getWindowViewer(iPlatformComponent c) {
    return platform.getWindowViewerForComponent(c);
  }

  /**
   * Returns the window viewer associated with the widget. If the widget isn't
   * currently associated with a window viewer then the main window viewer for
   * the focused widget will be returned
   *
   * @param w
   *          the widget
   * @return the window viewer associated with the widget
   */
  public static WindowViewer getWindowViewer(iWidget w) {
    if (w == null) {
      return platform.getAppContext().getWindowViewer();
    }

    return platform.getWindowViewerForComponent(w.getContainerComponent());
  }

  /**
   * Checks if Android is hosting the runtime
   *
   * @return true if the OS is a version of Android; false otherwise
   */
  public static boolean isAndroid() {
    return platform.isAndroid();
  }

  /**
   * Checks if the runtime is running in debug mode
   *
   * @return true if the runtime is running in debug mode
   */
  public static boolean isDebugEnabled() {
    return platform.isDebugEnabled();
  }

  /**
   * Checks if the runtime supports debugging
   *
   * @return true if the runtime supports debugging
   */
  public static boolean isDebuggingEnabled() {
    return platform.isDebuggingEnabled();
  }

  /**
   * Checks if the specified component is descended from the specified container
   *
   * @return true if is; false otherwise
   */
  public static boolean isDescendingFrom(iPlatformComponent c, iPlatformComponent container) {
    return platform.isDescendingFrom(c, container);
  }

  /**
   * Checks if the runtime is embedded within a different UI environment (e.g.
   * like SWT)
   *
   * @return true if the runtime is embedded; false otherwise
   */
  public static boolean isEmbedded() {
    return platform.isEmbedded();
  }

  /**
   * Checks whether the native UI labels support HTML natively.
   *
   * @return true if the do; false otherwise
   */
  public boolean isHTMLSupportedInLabels() {
    return platform.isHTMLSupportedInLabels();
  }

  /**
   * Checks if iOS is hosting the runtime
   *
   * @return true if the OS is a version of iOS; false otherwise
   */
  public static boolean isIOS() {
    return platform.isIOS();
  }

  /**
   * Checks if we are running in a sandbox environment.
   *
   * @return true if we are; false otherwise
   */
  public static boolean isInSandbox() {
    return platform.isInSandbox();
  }

  /**
   * Checks if the platform has been initialized
   *
   * @return true if it has; false otherwise
   */
  public static boolean isInitialized() {
    return (platform != null) && platform.isInitialized();
  }

  /**
   * Returns whether or not this is a JVM
   *
   * @return <tt>true</tt> if the application is running in a JVM,
   *         <tt>false</tt> otherwise.
   */
  public static boolean isJava() {
    return platform.isJava();
  }

  /**
   * Returns whether this is a JavaFX framework
   *
   * @return <tt>true</tt> if the application is running on a JavaFX framework,
   *         <tt>false</tt> otherwise.
   */
  public static boolean isJavaFX() {
    return platform.isJavaFX();
  }

  /**
   * Checks if the current orientation of the device is landscape.
   *
   * @return true if it is; false otherwise;
   */
  public boolean isLandscapeOrientation() {
    return ScreenUtils.isWider();
  }

  /**
   * Returns whether the OS is a version of Linux
   *
   * @return true if the OS is a version of Linux; false otherwise
   */
  public static boolean isLinux() {
    return platform.isLinux();
  }

  /**
   * Returns whether the OS is a Mac OS
   *
   * @return true if the OS is a Mac OS; false otherwise
   */
  public static boolean isMac() {
    return platform.isMac();
  }

  /**
   * Checks to see if the runtime is in the process of shutting down
   *
   * @return if the runtime is in the process of shutting down; false otehrwise
   */
  public static boolean isShuttingDown() {
    iAppContext ap = (platform == null)
                     ? null
                     : platform.getAppContext();

    return (ap == null)
           ? true
           : ap.isShuttingDown();
  }

  /**
   * Returns whether this is a Swing framework
   *
   * @return <tt>true</tt> if the application is running on a Swing framework,
   *         <tt>false</tt> otherwise.
   */
  public static boolean isSwing() {
    return platform.isSwing();
  }

  /**
   * Checks to see if the device is a touch device. A touch device is a device
   * whose primary input mode is touch.
   *
   * @return true if the device is a touch device; false otherwise
   */
  public static boolean isTouchDevice() {
    return platform.isTouchDevice();
  }

  /**
   * Checks to see if the device is a touchable device. A touch device is a
   * device that supports touch as a means of input.
   *
   * @return true if the device is a touchable device; false otherwise
   */
  public static boolean isTouchableDevice() {
    return platform.isTouchableDevice();
  }

  /**
   * Checks to see if the device has a physical keyboard
   *
   * @return true if it does; false otherwise
   */
  public static boolean hasPhysicalKeyboard() {
    return platform.hasPhysicalKeyboard();
  }

  /**
   * Checks to see if the device has a physical pointing device
   *
   * @return true if it does; false otherwise
   */
  public static boolean hasPointingDevice() {
    return platform.hasPointingDevice();
  }

  /**
   * Checks to see if the current thread is the UI thread
   *
   * @return true if the current thread is the UI thread; false otherwise
   */
  public static boolean isUIThread() {
    return platform.isUIThread();
  }

  /**
   * Returns whether the OS is a version of UNIX
   *
   * @return true if the OS is a version of UNIX; false otherwise
   */
  public static boolean isUnix() {
    return platform.isUnix();
  }

  /**
   * Checks to see if windows should be sized to fit the full screen
   *
   * @return true if windows should be sized to fit the full screen false
   *         otherwise
   */
  public static boolean isUseFullScreen() {
    return platform.isUseFullScreen();
  }

  /**
   * Returns whether the desktop OS is a version of windows
   *
   * @return true if the desktop OS is a version of windows; false otherwise
   */
  public static boolean isWindows() {
    return platform.isWindows();
  }

  /**
   * Set whether the application is in full screen mode
   *
   * @param use
   *          true for fullscreen; false otherwise
   */
  public static void setUseFullScreen(boolean use) {
    platform.setUseFullScreen(use);
  }

  /**
   * Get the interface for the object that handles multi-part mime data
   * @return the interface for the object that handles multi-part mime data
   */
  public static iMultipartMimeHandler getMultipartMimeHandler() {
    return (platform == null)
           ? null
           : platform.getAppContext().getMultipartMimeHandler();
  }

  /**
   * Creates a URL
   *
   * @param context
   *          the context URL
   * @param url
   *          the string representing the URL
   *
   * @return A context specific URL
   *
   * @throws MalformedURLException
   */
  public static URL createURL(URL context, String url) throws MalformedURLException {
    return (platform == null)
           ? new URL(context, url)
           : platform.getAppContext().createURL(context, url);
  }

  /**
   * Gets the default URL resolver for the application
   *
   * @return the default URL resolver for the application
   */
  public static iURLResolver getDefaultURLResolver() {
    iPlatformAppContext ctx = (platform == null)
                              ? null
                              : platform.getAppContext();

    return (ctx == null)
           ? null
           : (iURLResolver) ctx.getDefaultURLResolver();
  }

  /**
   * Sets the maximum number of background threads that can be used for background
   * tasks (spawned tasks). The system default is 2
   * @param max the maximum number of threads
   */
  public static void setMaxBackgroundThreadCount(int max) {
    PlatformHelper.setMaxBackgroundThreadCount(max);
  }
}
