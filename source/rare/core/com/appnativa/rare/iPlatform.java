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
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.dnd.iFlavorCreator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.iCancelable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.URL;

import java.util.EventObject;
import java.util.List;
import java.util.Map;

public interface iPlatform {
  void addJarURL(URL url);

  /**
   * Opens the specified url using the platforms native
   *  web browser application.
   *
   * @param url the URL to browse
   * @return true if the the browser application was able to be launched
   *              (or no failure status was reported by the platform); false if it was not
   */
  boolean browseURL(URL url);

  /**
   * Returns whether or not the installed scripting engine
   * can generate byte code
   *
   * @return true if it can; false otherwise
   */
  boolean canGenerateByteCode();

  /**
   * Creates a file that can be used for caching data
   * between application invocations.
   *
   * @param name the name for the file
   *
   * @return a file handle
   */
  File createCacheFile(String name);

  /**
   * Create the chart handler for the environment
   * @return the chart handler
   */
  Object createChartHandler();

  /**
   * Creates an object that is an instance of the the specified class
   * @param className the class name
   *
   * @return the new object or null if the object could not be created
   */
  Object createObject(String className);

  /**
   * Creates a platform component from a native component
   *
   * @param nativeComponent the native component
   *
   * @return the platform component
   */
  iPlatformComponent createPlatformComponent(Object nativeComponent);

  /**
   * Creates a timer with the given name
   * @param name the name for the timer
   *
   * @return a handle to the timer
   */
  iTimer createTimer(String name);

  /**
   * Writes a debug message to the console when running in debug mode
   *
   * @param msg
   *          the message to write
   */
  void debugLog(String msg);

  /**
   * Delete the specified directory.
   *
   * @param path the path to delete.
   * @return true if the directory was deleted; false otherwise
   */
  boolean deleteDirectory(File path);

  /**
   * Evaluates scripting code and returns the result
   *
   * @param w the widget context
   * @param sh the script handler to use
   * @param code the code to be evaluated
   * @param event the event
   * @param e the event object
   *
   * @return the result of the executed function
   */
  Object evaluate(iWidget w, iScriptHandler sh, Object code, String event, EventObject e);

  /**
   * Executes the specified code.
   *
   * @param w the widget context
   * @param sh the script handler to use
   * @param code the code to be evaluated
   * @param event the event for which the code is being invoked
   * @param e the the event object
   */
  void execute(iWidget w, iScriptHandler sh, Object code, String event, EventObject e);

  /**
   * Finds the lowest level platform component in the hierarchy for the given UI entity
   * @param source the source component
   * @return the component
   */
  iPlatformComponent findPlatformComponent(Object source);

  /**
   * Finds the lowest level widget in the specified component's hierarchy
   *
   * @param c the native component
   *
   * @return the lowest level widget in the specified component's hierarchy
   */
  iWidget findWidgetForComponent(Object c);

  /**
   * Handles platform specified properties configured for a widget
   *
   * @param widget the widget
   * @param cfg the widget's configuration
   * @param properties the properties
   */
  void handlePlatformProperties(iWidget widget, Widget cfg, Map<String, Object> properties);

  /**
   * Calls the default error handler ignoreException method.
   * The defaults behavior is to log the error to the error console
   * when in debug mode
   */
  void ignoreException(String msg, Throwable throwable);

  /**
   * Invokes the specified runnable on the UI thread
   * on the next execution pass of that thread.
   *
   * @param r the runnable to invoke
   */
  void invokeLater(Runnable r);

  /**
   * Invokes the specified runnable on the UI thread
   * on the next execution pass of that thread.
   *
   * @param r the runnable to invoke
   * @param delay number of milliseconds to delay before invoking the runnable
   */
  void invokeLater(Runnable r, int delay);

  /**
   * Loads the class with the specified name Use this method instead of
   * Class.forName when you want to ensure that supplemental JARs are searched
   * for the class and the ensure cross platform compatibility.
   *
   * @param name
   *          the name of the class to load
   *
   * @return the class with the specified name
   *
   * @throws ClassNotFoundException
   */
  Class loadClass(String name) throws ClassNotFoundException;

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
  Map<String, UIImageIcon> loadResourceIcons(iPlatformAppContext app, Map<String, UIImageIcon> appIcons,
          ActionLink link, boolean clear, boolean defaultDeferred)
          throws IOException;

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
  Map<String, String> loadResourceStrings(iPlatformAppContext app, Map<String, String> appStrings, ActionLink link,
          boolean clear)
          throws IOException;

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
  void loadUIProperties(iWidget context, ActionLink link, UIProperties defs) throws IOException;

  /**
   * Opens the system mail client
   *
   * @param uri
   *          the mailto: uri or email address (can be null)
   *
   * @return true if the mail client was activated; false otherwise
   */
  boolean mailTo(String uri);

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
  boolean mailTo(String address, String subject, String body);

  /**
   * Registers the component as belonging to the specified widget
   *
   * @param component
   *          the component to register
   * @param context
   *          the widget context
   */
  void registerWithWidget(iPlatformComponent component, iWidget context);

  /**
   * Sends the specified data as form data
   *
   * @param context
   *          the context
   * @param link
   *          the link representing the data to be retrieved
   * @param data
   *          the data
   * @param multipart
   *          true to send the data as multi-part content
   * @param returnType the type of data to return
   * @param cb
   *          the callback to call when complete
   * @return a handle to use to be able to cancel the operation;
   *
   * @see com.appnativa.util.ObjectHolder
   *
   */
  iCancelable sendFormData(final iWidget context, final ActionLink link, final Map<String, Object> data,
                           final boolean multipart, final ActionLink.ReturnDataType returnType,
                           final iFunctionCallback cb);

  /**
   * Unregisters the component as belonging to a specified widget
   *
   * @param component
   *          the component to unregister
   */
  void unregisterWithWidget(iPlatformComponent component);

  /**
   * Uploads the specified data as a file
   *
   * @param context to context widget
   * @param data
   *          the data (can be a string or {@link Reader} or {@link InputStream} or {@link File}
   * @param name
   *          the name of the field that the file data is associated with
   * @param mimeType
   *          the MIME type for the file (if null then the file's extension will
   *          be used to determine the mime-type)
   * @param fileName
   *          the file name (cannot be null)
   * @param returnType the type of data to return
   * @return a handle to use to be able to cancel the operation;
   *
   * @see com.appnativa.util.ObjectHolder
   *
   */
  iCancelable uploadData(final iWidget context, final ActionLink link, final Object data, final String name,
                         final String mimeType, final String fileName, final ActionLink.ReturnDataType returnType,
                         final iFunctionCallback cb);

  /**
   * Returns whether the version of Android
   *
   * @return an integer representing the android build version
   */
  int getAndroidVersion();

  /**
   * Gets the active application context
   *
   * @return the active application context
   */
  iPlatformAppContext getAppContext();

  int getAppInstanceCount();

  /**
   * Gets the directory where cache files are created
   * @return the directory where cache files are created
   */
  File getCacheDir();

  iCancelable getContent(final iWidget context, final ActionLink link, final ActionLink.ReturnDataType returnType,
                         final iFunctionCallback cb);

  List getCookieList();

  String getCookies();

  iDataConverter getDataConverter(Class cls);

  Class getDataConverterClass(String name) throws ClassNotFoundException;

  iFunctionHandler getFunctionHandler();

  /**
   * The version of the Java runtime
   * @return a double representing version of the Java runtime
   */
  double getJavaVersion();

  /**
   * Get the type of operating system that the application is running on (window, linux, etc.)
   *
   *
   * @return the type of operating system that the application is running on
   */
  String getOsType();

  /**
   * The version of the underlying operating system
   * @return a double representing version of the underlying operating system
   */
  float getOsVersion();

  /**
   * Gets the platform component for the source UI object
   *
   * @param source the source object
   * @return the platform component or null if the source is not tied to a platform component
   */
  iPlatformComponent getPlatformComponent(Object source);

  /**
   * Get the type of platform that the application is running on.
   * In some instances the os type and platform type will be the same (e.g. ios/ios)
   * on others the will be different(windows/swing or osx/swing)
   *
   * @return the type of operating system that the application is running on
   */
  String getPlatformType();

  /**
   * The version of the platform that the runtime is built for.
   *
   * @return a double representing version of the underlying operating system
   */
  double getPlatformVersion();

  iFlavorCreator getTransferFlavorCreator();

  /**
   * Gets the user agent string that will be sent with
   * all http/https requests
   *
   * @return the use agent string
   */
  String getUserAgent();

  /**
   * Returns the window viewer associated with the specified component.
   *
   * @param c
   *          the component
   * @return the window viewer associated with the component
   */
  WindowViewer getWindowViewerForComponent(iPlatformComponent c);

  /**
   * Checks if Android is hosting the runtime
   *
   * @return true if the OS is a version of Android; false otherwise
   */
  boolean isAndroid();

  /**
   * Checks if the runtime is running in debug mode
   *
   * @return true if the runtime is running in debug mode
   */
  boolean isDebugEnabled();

  /**
   * Checks if the runtime supports debugging
   *
   * @return true if the runtime supports debugging
   */
  boolean isDebuggingEnabled();

  /**
   * Checks if the specified component
   * is descended from the specified container
   *
   * @return true if is; false otherwise
   */
  boolean isDescendingFrom(iPlatformComponent c, iPlatformComponent container);

  /**
   * Checks if the runtime is embedded within a different UI
   * environment (e.g. like SWT)
   *
   * @return true if the runtime is embedded; false otherwise
   */
  boolean isEmbedded();

  /**
   * Checks whether the native UI labels support HTML natively in their labels
   * @return true if the do; false otherwise
   */
  boolean isHTMLSupportedInLabels();

  /**
   * Checks if iOS is hosting the runtime
   *
   * @return true if the OS is a version of iOS; false otherwise
   */
  boolean isIOS();

  /**
   * Checks if we are running in a sandbox environment.
   *
   * @return true if we are; false otherwise
   */
  boolean isInSandbox();

  /**
   * Checks if the platform has been initialized
   *
   * @return true if it has; false otherwise
   */
  boolean isInitialized();

  /**
   * Returns whether this is a JavaFX framework
   *
   * @return <tt>true</tt> if the application is running on a JavaFX framework, <tt>false</tt> otherwise.
   */
  boolean isJavaFX();

  /**
   * Returns whether or not this is a JVM
   *
   * @return <tt>true</tt> if the application is running in a JVM, <tt>false</tt> otherwise.
   */
  boolean isJava();

  /**
   * Returns whether the OS is a version of Linux
   *
   * @return true if the OS is a version of Linux; false otherwise
   */
  boolean isLinux();

  /**
   * Returns whether the OS is a Mac OS
   *
   * @return true if the OS is a Mac OS; false otherwise
   */
  boolean isMac();

  /**
   * Returns whether this is a Swing framework
   *
   * @return <tt>true</tt> if the application is running on a Swing framework, <tt>false</tt> otherwise.
   */
  boolean isSwing();

  /**
   * Checks to see if the device is a touch device.
   * A touch device is a device whose primary input mode is
   * touch.
   *
   * @return true if the device is a touch device; false otherwise
   */
  boolean isTouchDevice();

  /**
   * Checks to see if the device is a touchable device.
   * A touch device is a device that supports touch as a means
   * of input.
   *
   * @return true if the device is a touchable device; false otherwise
   */
  boolean isTouchableDevice();

  /**
   * Checks to see if the current thread is the UI thread
   *
   * @return true if the current thread is the UI thread; false otherwise
   */
  boolean isUIThread();

  /**
   * Returns whether the OS is a version of UNIX
   *
   * @return true if the OS is a version of UNIX; false otherwise
   */
  boolean isUnix();

  /**
   * Checks to see if windows should be sized to fit the full screen
   *
   * @return true if windows should be sized to fit the full screen false otherwise
   */
  boolean isUseFullScreen();

  /**
   * Returns whether the runtime is executing on a desktop class device
   *
   * @return true if the runtime is executing on a desktop class device; false otherwise
   */
  boolean isDesktop();

  /**
   * Returns whether the desktop OS is a version of windows
   *
   * @return true if the desktop OS is a version of windows; false otherwise
   */
  boolean isWindows();

  /**
   * Set whether the application is in full screen mode
   * @param use true for fullscreen; false otherwise
   */
  void setUseFullScreen(boolean use);
}
