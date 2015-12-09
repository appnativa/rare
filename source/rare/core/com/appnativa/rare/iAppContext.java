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

import com.appnativa.rare.converters.DateContext;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.iConnectionHandler;
import com.appnativa.rare.net.iMultipartMimeHandler;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.iConfigurationListener;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.UISound;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iPrintHandler;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.listener.iApplicationListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.iURLResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

public interface iAppContext extends iExecutionHandler {

  /**
   * Whether all label widgets should be draggable by default
   *
   * @return true if all label widgets should be draggable by default; false otherwise
   */
  public boolean areAllLabelsDraggable();

  /**
   * Whether all textfield widgets should be able to be dropped onto by default
   *
   * @return true if all textfield widgets should be able to be dropped onto by default; false otherwise
   */
  public boolean areAllTextFieldsDroppable();

  /**
   * Returns whether the specified element is valid for the current O/S
   * @param e the element
   * @return true if it is; false otherwise
   */
  public boolean okForOS(iSPOTElement e);

  /**
   * Returns whether the specified "os" string value is valid for the current O/S
   * @param value the value
   * @return true if it is; false otherwise
   */
  public boolean okForOS(String value);

  /**
   * Gets an animator that is a runtime resource
   *
   * @param animator the name of the animator
   *
   * @return the animator with the specified name
   */
  public iPlatformAnimator getResourceAsAnimator(String animator);

  /**
   * Rewrites a URL such that the returned string represents a URL that points to this engines proxy server. The string can be passed to any external component/application.
   * <p>
   * The proxy server will provide access to the passed in URL via the rewritten URL. The rewritten URL expires after a given amount of time. The amount of time is a system configurable parameter (defaults to 5 minutes)
   * </p>
   *
   * @param widget the widget requesting the rewrite
   * @param url the URL to be rewritten
   * @param code optional code to call to handle the request
   * @return a string representation of a URL that provides proxy access to the specified URL
   */
  public String rewriteURL(iWidget widget, URL url, Object code);

  /**
   * Returns is the color theme is that of the underlying platform.
   * That is, the Rare.foreground and Rare.background look and feel property were not set.
   * @return true if it is the platform color theme; false for a custom theme
   */
  public boolean isPlatformColorTheme();

  /**
   * Returns is the default background color is dark
   * @return true if it is dark false otherwise
   */
  public boolean isDefaultBackgroundDark();

  /**
   * Adds a listener for application events.
   *
   * @param listener
   *          the iApplicationListener to add
   */
  void addApplicationListener(iApplicationListener listener);

  /**
   * Adds a listener for configuration change events.
   *
   * @param listener
   *          the iConfigurationListener to add
   */
  void addConfigurationListener(iConfigurationListener listener);

  /**
   * Adds a url prefix mapping to the environment
   *
   * @param prefix the prefix
   * @param mapping the mapping
   */
  void addURLPrefixMapping(String prefix, String mapping);

  /**
   * Add a url for a jar to the class path
   *
   * @param url the url
   */
  void addJarURL(URL url);

  /**
   * Registers a focused action. This method is automatically called by the focused
   * action is question and should no be called otherwise.It will automatically be
   * unregistered when it is no longer referenced.
   *
   * @param action the action to register
   */
  void registerFocusedAction(aFocusedAction action);

  /**
   * Adds the specified string the to string pool
   *
   * @param name
   *          the name of the string
   * @param value
   *          the string value
   */
  void addResourceString(String name, String value);

  /**
   * Whether all widgets should be draggable by default
   *
   * @return true if all widgets should be draggable by default; false otherwise
   */
  boolean areAllWidgetsDraggable();

  /**
   * Returns whether viewers a local in scope by default
   *
   * @return true if they are; false otherwise
   */
  boolean areViewersLocalByDefault();

  /**
   * Gets the prefix for restricting custom properties on
   * Widgets and RenderableDataItems.
   *
   * @return the prefix
   */
  String getCustomPropertyPrefix();

  /**
   * Launches the default system browser to view the specified URL
   *
   * @param url
   *          the URL to browse
   * @return true if the browser was launched false otherwise
   */
  boolean browseURL(URL url);

  /**
   * Clears the applications data map;
   */
  void clearData();

  /*
   * Clears the applications data map of all keys with the specified prefix
   *
   * @param prefix the prefix of the keys to clear
   */
  void clearData(String prefix);

  /**
   * Clears the status bar
   */
  void clearStatusBar();

  /**
   * Closes all non-main windows for the application instance.
   * @param all true to close all windows ( dialogs and popups); false to only close popups.
   */
  void closePopupWindows(boolean all);

  /**
   * Returns whether or not there is currently a popup window showing.
   * @return true if there is false otherwise
   */
  boolean isPopupWindowShowing();

  /**
   * Returns whether or not there is currently a dialog window showing.
   * @return true if there is false otherwise
   */
  boolean isDialogWindowShowing();

  /**
   * Create a collection from the specified URL
   *
   * @param handler
   *          the collection handler
   * @param name
   *          the name of the collection to create
   * @param link
   *          the link to use to create the collection
   * @param attributes
   *          attributes for the collection
   * @param tabular
   *          true if the data is tabular; false otherwise
   * @param cb the callback to invoke when the collection has been loaded (passing null will load the collection inline)
   *
   * @return a collection
   */
  iDataCollection createCollection(String handler, String name, ActionLink link, Map attributes, boolean tabular,
                                   iFunctionCallback cb);

  /**
   * Creates a URL
   *
   * @param context
   *          the context for the URL
   * @param url
   *          the string representing the URL
   *
   * @return A context specific URL
   *
   * @throws MalformedURLException
   */
  URL createURL(iWidget context, String url) throws MalformedURLException;

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
  URL createURL(URL context, String url) throws MalformedURLException;

  /**
   * Disposes of the context
   */
  void dispose();

  /**
   * Returns whether or not the context has been disposed
   *
   * @return true if the context is disposed; false otherwise
   */
  boolean isDisposed();

  /**
   * Exits the application.
   */
  void exit();

  /**
   * Returns whether format exceptions should be ignored. If true then format
   * exceptions will be transparently handled where ever possible Number format
   * exceptions will return a number whose value is zero and date format
   * exceptions will return a date object whose toString() method returns the
   * string that cased the format exception
   *
   * @return true if format exceptions should be ignored; false otherwise
   */
  boolean ignoreFormatExceptions();

  /**
   * Load the source code for the script pointed to by the specified link
   *
   * @param link
   *          the link for the script
   * @param runOnce
   *          if the script is designated as run once (if debug was enabled on
   *          the command line then this value is overridden to be false always
   *
   * @return the code for the script on null if there is none or the script has
   *         previously been loader and ran and is designated as runOnce
   */
  String loadScriptCode(ActionLink link, boolean runOnce);

  /**
   * Call to lock the orientation of the device
   * @param landscape true to lock in landscape mode; false to lock in portrait mode; null to lock in current orientation
   */
  void lockOrientation(Boolean landscape);

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
  iURLConnection openConnection(URL url) throws IOException;

  /**
   *
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
  iURLConnection openConnection(URL url, String mimeType) throws IOException;

  /**
   * Get the application specific connection handler
   *
   * @return the application specific connection handler or null if one has not been set
   */
  iConnectionHandler getApplicationConnectionHandler();

  /**
   * Set the application specific connection handler.
   * This handler will be called to create connections.
   * If it returns null then the default connection handler will be used.
   *
   * @param h the application specific connection handler.
   */
  void setApplicationConnectionHandler(iConnectionHandler h);

  /**
   * Associates the specified value with the specified key. This is intended for
   * developers to place application specific data in.
   *
   * @param key
   *          the key to store the value in
   * @param value
   *          the value to associated with key
   *
   * @return the previous value;
   *
   * @see #getData(Object)
   */
  Object putData(Object key, Object value);

  /**
   * Populates the application specific data map with the passed in map
   *
   * @param data the data to add
   * @param clearFirst true to clear out the existing data; false to just add the new data
   */
  void putData(Map data, boolean clearFirst);

  /**
   * Register a collection handler
   *
   * @param name
   *          the name of the collection handler
   * @param ch
   *          the collection handler object
   */
  void registerCollectionHandler(String name, iDataCollectionHandler ch);

  /**
   * Registers the class for a specific mime type
   *
   * @param type
   *          the mime type or the SPOT name for configuration object
   * @param cls
   *          the class that implements the widget functionality
   * @see Platform#getSPOTName
   */
  void registerWidgetClass(String type, Class cls);

  /**
   * Registers a data collection
   *
   * @param dc
   *          the data collection to register
   */
  void registerDataCollection(iDataCollection dc);

  /**
   * Removes a listener for application events.
   *
   * @param listener
   *          the iApplicationListener to remove
   */
  void removeApplicationListener(iApplicationListener listener);

  /**
   * Removes a listener for configuration change events.
   *
   * @param listener
   *          the iConfigurationListener to remove
   */
  void removeConfigurationListener(iConfigurationListener listener);

  /**
   * Removes the value for the specified user key and returns
   * its associated value, if any
   *
   * @param key
   *          the key used to retrieve the specified value
   *
   * @return the value for the specified user key
   */
  Object removeData(Object key);

  /**
   * Resets the script runOnce status allowing it to be run again
   *
   * @param link
   *          the link for the script
   */
  void resetRunOnce(ActionLink link);

  void unlockOrientation();

  /**
   * Unregisters a data collection
   *
   * @param dc
   *          the data collection to unregister
   */
  void unregisterDataCollection(iDataCollection dc);

  /**
   * Sets the status handler for asynchronous loading
   *
   * @param handler the status handler for asynchronous loading
   */
  void setAsyncLoadStatusHandler(iAsyncLoadStatusHandler handler);

  /**
   * Set the application's base URL
   *
   * @param url
   *          the application's base URL
   */
  void setContextURL(URL url);

  /**
   * Set the application's base URL
   *
   * @param url
   *          the application's base URL
   * @param appRoot
   *          the application root
   */
  void setContextURL(URL url, String appRoot);

  /**
   * Sets the default exception handler
   *
   * @param eh
   *          the default exception handler
   */
  void setDefaultExceptionHandler(iExceptionHandler eh);

  /**
   * @param multipartMimeHandler
   *          the multipartMimeHandler to set
   */
  void setMultipartMimeHandler(iMultipartMimeHandler multipartMimeHandler);

  void setResourceFinder(iResourceFinder rf);

  void setSpeechEnabler(iSpeechEnabler speechEnabler);

  /**
   * Sets the info portion for urls.
   *
   * This is info will be supplied in the Authorization header for all
   *
   * @param mappings
   *          the path (String)/info mappings
   */
  void setURLUserInfo(Map mappings);

  /**
   * Sets the user information for a given url path
   * URL below this path without explicit user information will use
   * specified information.
   *
   * @param path the path
   * @param info the URL user info
   */
  void setURLUserInfo(URL path, String info);

  /**
   * Set a widget customizer
   * @param customizer the customizer
   */
  void setWidgetCustomizer(iWidgetCustomizer customizer);

  /**
   * Gets a previously registered action. Retrieves both permanent and transient
   * actions.
   *
   * @param name
   *          the name of the action
   *
   * @return the named action or null if the action is not registered
   */
  UIAction getAction(String name);

  /**
   * Returns the map of dynamically loaded actions. Only actions loaded via an
   * action properties URL are available via this map
   *
   * @return the map of application configured actions
   */
  Map<String, UIAction> getActions();

  /**
   * Get a list of the currently active windows objects (PopupWindow or Dialog)
   *
   * @return a map of the currently active windows
   */
  IdentityArrayList getActiveWindows();

  String getApplicationName();

  /**
   * Returns the URL that created the application
   *
   * @return the URL that created the application
   */
  URL getApplicationURL();

  /**
   * Gets the status handler for asynchronous loading
   *
   * @return the status handler for asynchronous loading
   */
  iAsyncLoadStatusHandler getAsyncLoadStatusHandler();

  /**
   * Gets the paint for auto highlighted items in list type components
   * @return the paint for auto highlighted items in list type components
   */
  PaintBucket getAutoHilightPainter();

  /**
   * Returns whether data/time formats are automatically localized
   *
   * @return true if they are automatically localized; false if they are left
   *         unchanged
   */
  boolean getAutoLocalizeDateFormats();

  /**
   * Returns whether number formats are automatically localized
   *
   * @return true if they are automatically localized; false if they are left
   *         unchanged
   */
  boolean getAutoLocalizeNumberFormats();

  /**
   * Gets the URL from whence this application was loaded
   * @return the URL from whence this application was loaded
   */
  URL getCodeBase();

  /**
   * Gets the content of the URL as a string
   *
   * @param url
   *          the URL
   *
   * @return a string representing the content of the connection
   * @throws IOException
   */
  String getContentAsString(URL url) throws IOException;

  /**
   * Returns the application's base URL
   *
   * @return the application's base URL
   */
  URL getContextURL();

  /**
   * Returns the value for the specified user key.
   *
   * @param key
   *          the key used to retrieve the specified value
   *
   * @return the value for the specified user key
   */
  Object getData(Object key);

  /**
   * Gets the data collection with the specified name
   *
   * @param name
   *          the name of the collection
   *
   * @return the data collection with the specified name or null
   */
  iDataCollection getDataCollection(String name);

  /**
   * Retrieves a data converter instance for the specified class from the
   * converter pool.
   *
   * @param cls
   *          the data converter class
   *
   * @return an instance of the class
   */
  iDataConverter getDataConverter(Class cls);

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
  Class getDataConverterClass(String name) throws ClassNotFoundException;

  /**
   * Gets the default date context. This value context is used to convert a
   * string representing a date to a date object and vice-a-versa
   *
   * @return the default time context
   */
  DateContext getDefaultDateContext();

  /**
   * Gets the default date/time context. This value context is used to convert a
   * string representing a date/time to a date object and vice-a-versa
   *
   * @return the default time context
   */
  DateContext getDefaultDateTimeContext();

  /**
   * Gets the default exception handler for the environment
   *
   * @return the default exception handler for the environment
   */
  iExceptionHandler getDefaultExceptionHandler();

  /**
   * Gets the defaulting scripting language. This is the language that will
   * handle code execution in the absence of a script of a particular language
   * type
   *
   * @return the defaulting scripting language
   */
  String getDefaultScrptingLanguage();

  /**
   * Gets the default time context. This value context is user to convert a
   * string representing a time to a date object and vice-a-versa
   *
   * @return the default time context
   */
  DateContext getDefaultTimeContext();

  /**
   * Gets the default URL resolver for the application
   *
   * @return the default URL resolver for the application
   */
  iURLResolver getDefaultURLResolver();

  /**
   * Returns the applet document base or the webstart code base URL
   *
   * @return the applet document base or webstart code base URL
   */
  URL getDocumentBase();

  /**
   * Gets the component that is the current focus owner
   *
   * @return the component that is the current focus owner
   */
  iPlatformComponent getFocusOwner();

  /**
   * Gets the int representing this application context
   * @return the int representing this application context
   */
  int getIdentityInt();

  /**
   * Returns an input stream for the specified URL
   *
   * @param url
   *          the URL
   *
   * @return the input stream for the specified URL
   *
   * @throws IOException
   */
  InputStream getInputStream(URL url) throws IOException;

  /**
   * Gets the padding height for list type items
   *
   * @return the padding height for list type items
   */
  int getItemPaddingHeight();

  /**
   * Gets the paint for focused items in list type components
   * @return the paint for focused items in list type components
   */
  PaintBucket getListItemFocusPainter();

  /**
   * Gets the paint for selected items in widgets that don't have focus
   * @return the paint for selected items in widgets that don't have focus
   */
  PaintBucket getLostFocusSelectionPainter();

  /**
   * Gets the paint for pressed items in widgets that don't have focus
   * @return the paint for pressed items (can be null)
   */
  PaintBucket getPressedPainter();

  /**
   * @return the multipartMimeHandler
   */
  iMultipartMimeHandler getMultipartMimeHandler();

  /**
   * Returns the name of the application.
   *
   * @return the name of the application
   */
  String getName();

  /**
   * Gets the component that currently has the focus ownership
   *
   * @return the component that currently has the focus ownership
   */
  iPlatformComponent getPermanentFocusOwner();

  /**
   * Get the application print handler
   *
   * @return the application print handler
   */
  iPrintHandler getPrintHandler();

  /**
   * Returns a reader for the specified URL
   *
   * @param url
   *          the URL
   *
   * @return the reader for the specified URL
   *
   * @throws IOException
   */
  Reader getReader(URL url) throws IOException;

  /**
   * Returns a reader for the specified connection
   *
   * @param conn
   *          the connection
   *
   * @return the reader for the specified URL
   * @throws IOException
   */
  Reader getReader(URLConnection conn) throws IOException;

  /**
   * Gets a sound
   *
   * @param sound the name of the sound or the sound file
   * @return the sound of null if the sound could not be loaded
   * @throws Exception
   */
  UISound getSound(String sound) throws Exception;

  /**
   * Gets the resource icon with the specified name
   *
   * @param name
   *          the name of the icon
   *
   * @return the resource icon with the specified name or a broken image icon
   */
  UIImageIcon getResourceAsIcon(String name);

  /**
   * Adds a resource icon with the specified name
   *
   * @param name
   *          the name of the icon
   * @param icon the icon to add
   *
   * @return the previous resource icon with the specified name or null
   */
  UIImageIcon addResourceIcon(String name, UIImageIcon icon);

  /**
   * Gets the resource icon with the specified name
   *
   * @param name
   *          the name of the icon
   *
   * @return the resource icon with the specified name or null
   */
  UIImageIcon getResourceAsIconEx(String name);

  /**
   * Gets the resource image with the specified name
   *
   * @param name
   *          the name of the image
   *
   * @return the resource image with the specified name
   */
  UIImage getResourceAsImage(String name);

  /**
   * Gets the resource string with the specified name
   *
   * @param name
   *          the name of the string
   *
   * @return the resource string
   */
  String getResourceAsString(String name);

  iResourceFinder getResourceFinder();

  /**
   * Returns the map of resource icons for the application
   * that were defined in the resource icons properties
   * of the application's configuration.
   * 
   * <p>
   * <b>Note:</b> Resources managed but the runtime are not returned in this map.
   * </p>
   *
   * @return the map of resource icons for the application
   */
  Map<String, UIImageIcon> getResourceIcons();

  /**
   * Returns the map of resource icons for the application
   *
   * @return the map of resource icons for the application
   */
  Map<String, String> getResourceStrings();

  /**
   * Get the URL for the given resource path
   *
   * @param path
   *          the resource path
   *
   * @return the URL for the given resource path
   */
  URL getResourceURL(String path);

  /**
   * Get the root viewer
   *
   * @return the root viewer widget
   */
  iContainer getRootViewer();

  /**
   * Get the language type for the script that the specified link points to
   *
   * @param link
   *          the link
   *
   * @return the script language type or null of the type could not be
   *         identified
   */
  String getScriptType(ActionLink link);

  /**
   * Get the application level script handler. All subsequent script handlers
   * are created from this one
   *
   * @return the application level script handler.
   */
  iScriptHandler getScriptingManager();

  PaintBucket getSelectionPainter();

  iSpeechEnabler getSpeechEnabler();

  /**
   * Gets the UI defaults for the context
   *
   * @return the UI defaults for the context
   */
  UIProperties getUIDefaults();

  /**
   * Converts the string representation of a URL to a URL object
   *
   * @param url
   *          the string representation of a URL
   * @return the URL
   * @throws MalformedURLException
   */
  URL getURL(String url) throws MalformedURLException;

  /**
   * Gets the viewer with the specified name
   *
   * @param name
   *          the name of the viewer
   *
   * @return the viewer with the specified name
   */
  iViewer getViewer(String name);

  /**
   * Returns the installed widget customizer
   *
   * @return the installed widget customizer
   */
  iWidgetCustomizer getWidgetCustomizer();

  /**
   * Gets the paint for denoting the currently focused widget
   * @return the paint for denoting the currently focused widget
   */
  PaintBucket getWidgetFocusPainter();

  /**
   * Retrieves the widget that is represented by the specified path name. A
   * forward slash used as a separator in path names. If the path does not start
   * with a forward slash then the current viewer and its children are used to
   * search for the widget. If the path starts with a forward slash the search
   * starts with the main window.
   *
   * @param path
   *          the path name of the widget
   *
   * @return the widget or null if no such widget exists
   */
  iWidget getWidgetFromPath(String path);

  /**
   * Gets the class that handles widgets for the specified mime type
   * @param mimeType the mime type
   * @return the class that handles widgets for the specified mime type
   */
  Class getWidgetHandler(String mimeType);

  /**
   * Gets the window manager for the context
   *
   * @return  the window manager for the context
   */
  iPlatformWindowManager getWindowManager();

  /**
   * Returns the current window viewer
   *
   * @return the current window viewer
   */
  WindowViewer getWindowViewer();

  /**
   * Returns the main window viewer
   *
   * @return the main window viewer
   */
  WindowViewer getMainWindowViewer();

  boolean isChangeSelColorOnLostFocus();

  /**
   * Whether the application was configured for debugging
   *
   * @return true if the application was configured for debugging; false
   *         otherwise
   */
  boolean isDebugEnabled();

  /**
   * Returns whether dynamic name lookup is enabled for this app
   *
   * @return true if dynamic name lookup is enabled for this app; false
   *         otherwise
   */
  boolean isDynamicNameLookupEnabled();

  boolean isInSandbox();

  boolean isOrientationLocked();

  boolean isShuttingDown();

  /**
   * Returns whether tooltips for elements in lists should overlap
   * this content
   * @return true to overlap; false to offset
   */
  boolean isOverlapAutoToolTips();

  /**
   * Returns whether the engine was launched via so web context
   *
   * @return true if the engine was launched via so web context; false otherwise
   */
  boolean isWebContext();

  /**
   * Returns whether the engine was launched via web start
   *
   * @return true if the engine was launched via web start; false otherwise
   */
  boolean isWebStart();

  /**
   * Sets a runnable to invoke when a low memory warning is received.
   * @param runnable a runnable to invoke when a low memory warning is received.
   */
  void setLowMemoryWarningHandler(Runnable runnable);
}
