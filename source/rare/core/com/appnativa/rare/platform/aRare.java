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

package com.appnativa.rare.platform;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iAsyncLoadStatusHandler;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.iDataCollectionHandler;
import com.appnativa.rare.iExceptionHandler;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.iWidgetCustomizer;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.converters.Conversions;
import com.appnativa.rare.converters.DateContext;
import com.appnativa.rare.converters.aConverter;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.AbortOperationException;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.ActionLink.iErrorHandler;
import com.appnativa.rare.net.CollectionURLConnection;
import com.appnativa.rare.net.HTTPException;
import com.appnativa.rare.net.InlineURLConnection;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.ScriptURLConnection;
import com.appnativa.rare.net.aNetHelper;
import com.appnativa.rare.net.iConnectionHandler;
import com.appnativa.rare.net.iMultipartMimeHandler;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.spot.Bean;
import com.appnativa.rare.spot.Browser;
import com.appnativa.rare.spot.Canvas;
import com.appnativa.rare.spot.Carousel;
import com.appnativa.rare.spot.Chart;
import com.appnativa.rare.spot.CheckBox;
import com.appnativa.rare.spot.CheckBoxList;
import com.appnativa.rare.spot.CheckBoxTree;
import com.appnativa.rare.spot.CollapsiblePane;
import com.appnativa.rare.spot.ColorChooser;
import com.appnativa.rare.spot.ComboBox;
import com.appnativa.rare.spot.DataCollection;
import com.appnativa.rare.spot.DateChooser;
import com.appnativa.rare.spot.DateSpinner;
import com.appnativa.rare.spot.DateTimeSpinner;
import com.appnativa.rare.spot.DocumentPane;
import com.appnativa.rare.spot.Form;
import com.appnativa.rare.spot.GridCell;
import com.appnativa.rare.spot.GridPane;
import com.appnativa.rare.spot.GroupBox;
import com.appnativa.rare.spot.ImagePane;
import com.appnativa.rare.spot.Label;
import com.appnativa.rare.spot.Line;
import com.appnativa.rare.spot.ListBox;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.NameValuePair;
import com.appnativa.rare.spot.Navigator;
import com.appnativa.rare.spot.NumberSpinner;
import com.appnativa.rare.spot.PasswordField;
import com.appnativa.rare.spot.ProgressBar;
import com.appnativa.rare.spot.PropertyTable;
import com.appnativa.rare.spot.PushButton;
import com.appnativa.rare.spot.RadioButton;
import com.appnativa.rare.spot.Slider;
import com.appnativa.rare.spot.Spinner;
import com.appnativa.rare.spot.SplitPane;
import com.appnativa.rare.spot.StackPane;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.spot.TabPane;
import com.appnativa.rare.spot.Table;
import com.appnativa.rare.spot.TextArea;
import com.appnativa.rare.spot.TextField;
import com.appnativa.rare.spot.TimeSpinner;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Tree;
import com.appnativa.rare.spot.TreeTable;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.PainterUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iPrintHandler;
import com.appnativa.rare.ui.listener.iApplicationListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.UISimpleBackgroundPainter;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.viewer.CheckBoxListViewer;
import com.appnativa.rare.viewer.FormViewer;
import com.appnativa.rare.viewer.GridPaneViewer;
import com.appnativa.rare.viewer.GroupBoxViewer;
import com.appnativa.rare.viewer.ImagePaneViewer;
import com.appnativa.rare.viewer.ListBoxViewer;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.StackPaneViewer;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.WebBrowser;
import com.appnativa.rare.viewer.WidgetPaneViewer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.CheckBoxWidget;
import com.appnativa.rare.widget.LabelWidget;
import com.appnativa.rare.widget.LineWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.RadioButtonWidget;
import com.appnativa.rare.widget.TextAreaWidget;
import com.appnativa.rare.widget.TextFieldWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTPrintableString;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.MutableInteger;
import com.appnativa.util.ObjectHolder;
import com.appnativa.util.OrderedProperties;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams.ISO88591Reader;
import com.appnativa.util.iCancelable;
import com.appnativa.util.json.JSONObject;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aRare implements iExceptionHandler, iAsyncLoadStatusHandler {
  protected static String                              RARE_USER_AGENT;
  protected static volatile Map<Class, iDataConverter> dataConverters;
  protected static boolean                             debugEnabled;
  protected static iFunctionHandler                    functionHandler;
  protected static String                              osType;
  protected static ResourceBundle                      resources;
  String                                               contextURLBaseString;
  protected String                                     defaultScriptingLanguage = "ECMAScript";
  protected int                                        itemPaddingHeight        = 3;
  protected final HashMap<String, Object>              widgetClasses            = new HashMap<String, Object>();
  protected Map<Object, Object>                        appData                  = new ConcurrentHashMap<Object,
                                                                                    Object>();
  protected HashMap<String, UIAction>                  actionMap;
  protected boolean                                    allLabelsDraggable;
  protected boolean                                    allTextFieldsDraggable;
  protected boolean                                    allTextFieldsDroppable;
  protected boolean                                    allWidgetsDraggable;
  protected iPlatformAppContext                        appContext;
  protected List<iApplicationListener>                 appListeners;
  protected String                                     applicationName;
  protected String                                     applicationRoot;
  protected URL                                        applicationURL;
  protected PaintBucket                                autoHilightPainter;
  protected boolean                                    autoLocalizeDateFormats;
  protected boolean                                    autoLocalizeNumberFormats;
  protected boolean                                    changeSelColorOnLostFocus;
  protected HashMap<String, Object>                    collectionHandlers;
  protected URL                                        contextURL;
  protected String                                     customPropertyPrefix;
  protected HashMap<String, iDataCollection>           dataCollections;
  protected DateContext                                defaultDateContext;
  protected DateContext                                defaultDateTimeContext;
  protected iContainer                                 defaultRootViewer;
  protected DateContext                                defaultTimeContext;
  protected iExceptionHandler                          exceptionHandler;
  protected ArrayList<iWeakReference>                  focusedActions;
  protected boolean                                    ignoreFormatExceptions;
  protected StartupInfo                                info;
  protected PaintBucket                                listItemFocusPainter;
  protected PaintBucket                                lostFocusSelectionPainter;
  protected boolean                                    manageFocusedActions;
  protected boolean                                    mediumScreenSupported;
  protected boolean                                    multiScreenAutoFallback;
  protected boolean                                    multiScreenSupport;
  protected iMultipartMimeHandler                      multipartMimeHandler;
  protected boolean                                    overlapAutoToolTips;
  protected PaintBucket                                pressedPainter;
  protected String                                     printHandlerClassName;
  protected Application                                sageApplication;
  protected iScriptHandler                             scriptHandler;
  protected PaintBucket                                selectionPainter;
  protected boolean                                    shuttingDown;
  protected String[]                                   urlPrefixesKeys;
  protected String[]                                   urlPrefixesValues;
  protected boolean                                    useHeavyTargets;
  protected Map                                        userInfo;
  protected boolean                                    viewersLocalByDefault;
  protected PaintBucket                                widgetFocusPainter;
  protected iPlatformWindowManager                     windowManager;
  private final HashMap<String, Long>                  runOnceScripts    = new HashMap<String, Long>();
  protected boolean                                    dynamicNameLookup = true;
  protected iPrintHandler                              printHandler;
  protected iConnectionHandler                         applicationConnectionHandler;

  public aRare() {}

  public void createApplicationObject(URL[] url, String local) throws Exception {
    final int len = url.length;

    for (int i = 0; i < len; i++) {
      try {
        createApplicationObjectEx(url[i], 0, (i == len - 1)
                ? local
                : null);

        break;
      } catch(Exception e) {
        if (i == len - 1) {
          throw e;
        }
      }
    }
  }

  public void fireApplicationPaused() {
    if (appListeners != null) {
      for (iApplicationListener listener : appListeners) {
        listener.applicationPaused(appContext);
      }
    }
  }

  public void fireApplicationResumed() {
    if (appListeners != null) {
      for (iApplicationListener listener : appListeners) {
        listener.applicationResumed(appContext);
      }
    }
  }

  @Override
  public void handleException(Throwable e) {
    if (!Platform.isUIThread()) {
      throw ApplicationException.runtimeException(e);
    }

    e = Helper.pealException(e);

    if (e instanceof AbortOperationException) {
      throw(AbortOperationException) e;
    }

    if ((getWindowManager() != null) && (appContext != null) &&!shuttingDown) {
      try {
        ignoreException("Unhandled exception", e);
        showErrorDialog(e, true);
      } catch(Throwable t) {
        t.printStackTrace();
        exit();
      }
    } else {
      ignoreException("Unhandled exception", e);

      throw new AbortOperationException();
    }
  }

  @Override
  public void handleScriptException(Throwable e) {
    e = Helper.pealException(e);

    if (e instanceof AbortOperationException) {
      throw(AbortOperationException) e;
    }

    if ((getWindowManager() != null) && (appContext != null) &&!shuttingDown) {
      appContext.getWindowViewer().alert(e);
    } else {
      ignoreException(null, e);
    }
  }

  @Override
  public abstract void ignoreException(String msg, Throwable e);

  @Override
  public void errorOccured(iWidget context, ActionLink link, Throwable error) {
    WaitCursorHandler.stopWaitCursor((context == null)
                                     ? null
                                     : context.getContainerComponent(), false);
  }

  @Override
  public void loadCompleted(iWidget context, ActionLink link) {
    WaitCursorHandler.stopWaitCursor((context == null)
                                     ? null
                                     : context.getContainerComponent(), false);
  }

  @Override
  public void loadStarted(iWidget context, ActionLink link, iCancelable cancelable) {
    WaitCursorHandler.startWaitCursor((context == null)
                                      ? null
                                      : context.getContainerComponent(), cancelable,
                                      Platform.getUIDefaults().getInt("Rare.asyncLoadStatusHandler.delay", 200));
  }

  /**
   * Loads MIME type information from a properties file
   *
   * @param link
   *          the link to the properties file
   * @param clear
   *          true to clear the existing MIME mappings first; false otherwise
   *
   * @throws IOException
   */
  public static void loadMIMETypeMappings(ActionLink link, boolean clear) throws IOException {
    try {
      InputStream       stream = link.getInputStream();
      OrderedProperties props  = new OrderedProperties();

      props.load(stream);
      MIMEMap.addMappings(props, clear);
    } finally {
      link.close();
    }
  }

  /**
   *
   * Opens a connection to the object referenced by the URL argument
   *
   * @param url
   *          the URL
   *
   * @return the connection handler
   *
   * @throws IOException
   */
  public iURLConnection openConnection(URL url) throws IOException {
    return openConnection(url, null);
  }

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
  public iURLConnection openConnection(URL url, String mimeType) throws IOException {
    if (url == null) {
      throw new ApplicationException("null URL");
    }

    iURLConnection uc = handleIfFileOrLibURL(url, mimeType);

    if ((uc == null) && (applicationConnectionHandler != null)) {
      uc = applicationConnectionHandler.openConnection(url, mimeType);
    }

    if (uc != null) {
      return uc;
    }

    if (!aNetHelper.hasStreamHandlerPermission()) {
      String host = url.getHost();

      if (iConstants.INLINE_PROTOCOL_HOSTSTRING.equals(host)) {
        return new InlineURLConnection(url, mimeType);
      }

      if (iConstants.SCRIPT_PROTOCOL_HOSTSTRING.equals(host)) {
        return new ScriptURLConnection(url);
      }

      if (iConstants.COLLECTION_PROTOCOL_HOSTSTRING.equals(host)) {
        return new CollectionURLConnection(url);
      }
    }

    URLConnection conn = url.openConnection();

    if (conn instanceof iURLConnection) {
      return (iURLConnection) conn;
    }

    return new JavaURLConnection(conn, getUserInfo(url), mimeType);
  }

  /**
   * Sets the user information for a given url path URL below this path without
   * explicit user information will use specified information.
   *
   * @param path
   *          the path
   * @param info
   *          the URL user info
   */
  public void setURLUserInfo(URL path, String info) {
    if (info == null) {
      if (userInfo != null) {
        userInfo.clear();
      }

      userInfo = null;

      return;
    }

    if (userInfo == null) {
      userInfo = new LinkedHashMap();
    }

    userInfo.put(JavaURLConnection.toExternalForm(path), info);
  }

  public iPlatformAppContext getAppContext() {
    return appContext;
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
        Platform.getDefaultExceptionHandler(null).handleException(ex);

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
      String s = PlatformHelper.getPackageName(aConverter.class);

      if (!name.endsWith("Converter")) {
        name = s + "." + name + "Converter";
      } else {
        name = s + "." + name;
      }
    }

    return PlatformHelper.loadClass(name);
  }

  @Override
  public Writer getErrorWriter() {
    return new PrintWriter(System.err);
  }

  public abstract iFunctionHandler getFunctionHandler();

  public String getName() {
    return applicationName;
  }

  public iPrintHandler getPrintHandler() {
    if ((printHandler == null) && (printHandlerClassName != null)) {
      printHandler = (iPrintHandler) Platform.createObject(printHandlerClassName);
    }

    return printHandler;
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

  public String getRelativeLocation(URL url) {
    String s = JavaURLConnection.toExternalForm(url);

    if (s.startsWith(contextURLBaseString)) {
      s = s.substring(contextURLBaseString.length());
    }

    return s;
  }

  /**
   * Returns the ResourceBundle for the application. The ResourceBundle is
   * loaded using the value returned from getResourceBundleName.
   *
   * @return the ResourceBundle for the application
   */
  public ResourceBundle getResourceBundle() {
    return resources;
  }

  public static String getUserAgent() {
    return RARE_USER_AGENT;
  }

  /**
   * Gets the viewer with the specified name
   *
   * @param name
   *          the name of the viewer
   *
   * @return the viewer with the specified name
   */
  public iViewer getViewer(String name) {
    return getWindowManager().getViewer(name);
  }

  public Class getWidgetHandler(String type) {
    Object o = widgetClasses.get(type);

    if (o instanceof String) {
      Class cls;

      try {
        cls = PlatformHelper.loadClass((String) o);
      } catch(ClassNotFoundException e) {
        throw new ApplicationException(e);
      }

      widgetClasses.put(type, cls);
      o = cls;
    }

    return (Class) o;
  }

  public iPlatformWindowManager getWindowManager() {
    return windowManager;
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

  protected void configure(Application app, URL context) {
    initApplication(app);
    scriptHandler = createScriptHandler(app);

    Class  cls;
    String s = app.name.getValue();

    if (s != null) {
      s = s.trim();

      if (s.length() > 0) {
        applicationName = s;
      }
    }

    setupUrlPrefixes();
    autoLocalizeDateFormats   = app.autoLocalizeDateFormats.booleanValue();
    autoLocalizeNumberFormats = app.autoLocalizeNumberFormats.booleanValue();
    ignoreFormatExceptions    = app.ignoreFormatExceptions.booleanValue();
    allWidgetsDraggable       = app.allWidgetsDraggable.booleanValue();
    allLabelsDraggable        = app.allLabelsDraggable.booleanValue();
    allTextFieldsDraggable    = app.allTextFieldsDraggable.booleanValue();
    allTextFieldsDroppable    = app.allTextFieldsDroppable.booleanValue();
    changeSelColorOnLostFocus = app.changeSelColorOnLostFocus.booleanValue();
    overlapAutoToolTips       = app.overlapAutoToolTips.booleanValue();
    manageFocusedActions      = app.manageFocusedActions.booleanValue();
    defaultScriptingLanguage  = app.defaultScriptingLanguage.getValue();
    viewersLocalByDefault     = app.viewersLocalByDefault.booleanValue();
    customPropertyPrefix      = app.customPropertyPrefix.getValue();

    if ((customPropertyPrefix != null) && (customPropertyPrefix.length() == 0)) {
      customPropertyPrefix = null;
    }

    setContextURL(context);
    s = app.applicationListenerClass.getValue();

    if ((s != null) &&!((aAppContext) appContext).isDesignContext()) {
      s = s.trim();

      if (s.length() > 0) {
        try {
          cls = PlatformHelper.loadClass(s);
          appContext.addApplicationListener((iApplicationListener) cls.newInstance());
        } catch(Exception e) {
          throw new ApplicationException(e);
        }
      }
    }

    iViewer v = getRootViewer();

    s = app.contextURL.getValue();

    if ((s != null) && (s.length() > 0)) {
      try {
        URL u = this.createURL(appContext, context, s);

        if (u != null) {
          context = u;
          setContextURL(context);
        }
      } catch(MalformedURLException ex) {
        Platform.ignoreException(null, ex);
      }
    }

    ActionLink link = ActionLink.getActionLink(v, app.lookAndFeelPropertiesURL, 0);

    if (link != null) {
      try {
        Platform.loadUIProperties(getRootViewer(), link, appContext.getUIDefaults());
      } catch(IOException ex) {
        Platform.ignoreException("loading UI Properties URL", ex);
      }
    }

    useHeavyTargets = Platform.getUIDefaults().getBoolean("Rare.alwaysUseHeavyTargets", false);

    try {
      uiPropertiesLoaded();
      widgetFocusPainter   = ColorUtils.configure(v, app.getWidgetFocusPainter(), null);
      listItemFocusPainter = ColorUtils.configure(v, app.getListItemFocusPainter(), null);
      setupSelectionPainter(app.getSelectionPainter());
      pressedPainter = ColorUtils.configure(getRootViewer(), app.getPressedPainter(), null);

      if (!Platform.isTouchDevice()) {
        setupLostFocusSelectionPainter(app.getLostFocusSelectionPainter());
      }

      setupAutoHilightPainter(app.getAutoHilightPainter());
      createCellRenderingDefaults();

      String iformat = null,
             dformat = null;

      // get date/time context
      dformat = app.defaultDisplayDateTimeFormat.getValue();

      if (dformat == null) {
        dformat = "MM/dd/yyyy hh:mm a";
      }

      iformat = app.defaultItemDateTimeFormat.getValue();

      if (iformat == null) {
        iformat = "yyyy-MM-dd HH:mm";
      }

      defaultDateTimeContext = Conversions.createDateContext(iformat + "|" + dformat, autoLocalizeDateFormats);
      // get date context
      dformat = app.defaultDisplayDateFormat.getValue();

      if (dformat == null) {
        dformat = "MM/dd/yyyy";
      }

      iformat = app.defaultItemDateFormat.getValue();

      if (iformat == null) {
        iformat = "yyyy-MM-dd";
      }

      defaultDateContext = Conversions.createDateContext(iformat + "|" + dformat, autoLocalizeDateFormats);
      // get time context
      dformat = app.defaultDisplayTimeFormat.getValue();

      if (dformat == null) {
        dformat = "hh:mm a";
      }

      iformat = app.defaultItemTimeFormat.getValue();

      if (iformat == null) {
        iformat = "HH:mm";
      }

      defaultTimeContext = Conversions.createDateContext(iformat + "|" + dformat, autoLocalizeDateFormats);

      SPOTSet set;

      link = ActionLink.getActionLink(v, app.attributesURL, 0);

      if (link != null) {
        if (link.getContentType().contains("json")) {
          JSONObject o = new JSONObject(link.getContentAsString());

          appData.putAll(o);
        } else {
          set = DataParser.loadSPOTSet(getRootViewer(), link, NameValuePair.class);

          if (set != null) {
            Utils.nameValuePairSetToMap(getRootViewer(), set, appData);
          }
        }
      }

      link = ActionLink.getActionLink(v, app.resourceStringsURL, 0);

      if (link != null) {
        Platform.loadResourceStrings(appContext, appContext.getResourceStrings(), link, false);
        link = ActionLink.getActionLink(v, app.resourceStringsURL, 1);

        if (link != null) {
          try {
            Map<String, String> map = Platform.loadResourceStrings(appContext, null, link, false);

            appContext.getResourceStrings().putAll(map);
          } catch(Exception ignore) {}
        }
      }

      String value;

      link = ActionLink.getActionLink(v, app.resourceIconsURL, 0);

      if (link != null) {
        boolean deferred = !"false".equalsIgnoreCase(app.resourceIconsURL.spot_getAttribute("deferred"));

        Platform.loadResourceIcons(appContext, appContext.getResourceIcons(), link, false, deferred);
        link = ActionLink.getActionLink(v, app.resourceIconsURL, 1);

        if (link != null) {
          try {
            Map<String, UIImageIcon> map = Platform.loadResourceIcons(appContext, null, link, false, deferred);

            appContext.getResourceIcons().putAll(map);
          } catch(Exception ignore) {}
        }
      }

      registerDefaultActions();

      UIImageIcon ic = appContext.getResourceAsIconEx("Rare.icon.list.editorCheckedIcon");

      if (ic == null) {
        ic = new UIImageIcon(UIImageHelper.createImage(new PainterUtils.ListEditorIcon(true)));
        appContext.addResourceIcon("Rare.icon.list.editorCheckedIcon", ic);
      }

      ic = appContext.getResourceAsIconEx("Rare.icon.list.editorUncheckedIcon");

      if (ic == null) {
        ic = new UIImageIcon(UIImageHelper.createImage(new PainterUtils.ListEditorIcon(false)));
        appContext.addResourceIcon("Rare.icon.list.editorUncheckedIcon", ic);
      }

      int len;

      link = ActionLink.getActionLink(v, app.actionItemsURL, 0);

      if (link != null) {
        set = DataParser.loadSPOTSet(getRootViewer(), link, ActionItem.class);
        len = (set == null)
              ? 0
              : set.getCount();

        ActionItem item;
        UIAction   sa;

        for (int i = 0; i < len; i++) {
          item = (ActionItem) set.get(i);
          sa   = UIAction.createAction(null, item);
          registerAction(sa);
        }
      }

      set = resolveSet(app.getWidgetHandlers(), NameValuePair.class);
      len = (set == null)
            ? 0
            : set.getCount();

      NameValuePair pair;

      for (int i = 0; i < len; i++) {
        pair = (NameValuePair) set.get(i);

        if (okForOS(pair)) {
          s     = pair.getName();
          value = pair.getValue();

          if ((value != null) && (value.length() > 0) && (s != null) && (s.length() > 0)) {
            if (s.indexOf('.') == -1) {
              s = Platform.RARE_PACKAGE_NAME + ".viewer." + s;
            }

            cls = PlatformHelper.loadClass(s);
            registerWidgetClass(value, cls);
          }
        }
      }

      set = resolveSet(app.getCollectionHandlers(), NameValuePair.class);
      len = (set == null)
            ? 0
            : set.getCount();

      for (int i = 0; i < len; i++) {
        pair  = (NameValuePair) set.get(i);
        s     = pair.getName();
        value = pair.getValue();
        cls   = PlatformHelper.loadClass(value);
        registerCollectionHandlerClass(s, cls);
      }

      link = ActionLink.getActionLink(v, app.dataCollectionsURL, 0);

      if (link != null) {
        set = DataParser.loadSPOTSet(getRootViewer(), link, DataCollection.class);
      }

      len = (set == null)
            ? 0
            : set.getCount();

      for (int i = 0; i < len; i++) {
        registerDataCollection(createDataCollection((DataCollection) set.get(i)));
      }
    } catch(Exception ex) {
      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      }

      throw new RuntimeException(ex);
    }

    if (app.dynamicNameLookup.spot_valueWasSet()) {
      dynamicNameLookup = app.dynamicNameLookup.booleanValue();
    }

    s = appContext.getUIDefaults().getString("Rare.applicationConnectionHandler");

    if (s != null) {
      applicationConnectionHandler = (iConnectionHandler) Platform.createObject(s);
    }

    UIFontHelper.setDefaultFont(UIFontHelper.getSystemFont());
  }

  protected void createApplicationObjectEx(URL url, int retries, String local) throws Exception {
    iURLConnection ic = null;

    setApplicationURL(url);

    try {
      ic = openConnection(url);
      ic.open();

      int code = 200;

      do {
        if (ic.getConnectionObject() instanceof HttpURLConnection) {
          code = ic.getResponseCode();

          if (code != 401) {
            break;
          }

          ic.dispose();

          if (retries > 2) {
            throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.authFailure"));
          }

          ic.dispose();
          handleAuthFailure(url, retries, local);

          return;
        }
      } while(false);

      if (code != 200) {
        throw new ApplicationException(
            PlatformHelper.format(
              appContext.getResourceAsString("Rare.runtime.text.unknownApplication"), JavaURLConnection.toExternalForm(
                ic.getURL())), new HTTPException((HttpURLConnection) ic.getConnectionObject()));
      }
    } catch(Exception e) {
      ic = null;

      if (local != null) {
        // try to switch to a cached jar version of the application
        try {
          URL u = appContext.getResourceURL(local);

          if (u != null) {
            ic = openConnection(u);
          }
        } catch(Exception ex) {
          ignoreException(null, e);
          ic = null;
        }
      }

      if (ic == null) {
        throw new ApplicationException(
            PlatformHelper.format(
              appContext.getResourceAsString("Rare.runtime.text.unknownApplication"),
              JavaURLConnection.toExternalForm(url)), e);
      }
    }

    boolean xml    = false;
    boolean sdf    = false;
    Reader  stream = null;

    url = ic.getURL();

    String file = url.getFile();
    String mime = ic.getContentType();

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

    setApplicationURL(url);
    setContextURL(url);

    if (xml) {
      throw new UnsupportedOperationException("XML format not supported");
    } else if (sdf) {
      spot = (SPOTSequence) DataParser.loadSPOTObjectSDF(getRootViewer(), stream, null, mime, url);
    }

    ic.dispose();
    setupApplicationObject(url, spot, mime);
  }

  protected abstract void createCellRenderingDefaults();

  protected iDataCollection createDataCollection(DataCollection dc) {
    iFunctionCallback cb = new iFunctionCallback() {
      @Override
      public void finished(boolean canceled, Object returnValue) {
        if (returnValue instanceof Throwable) {
          handleException((Throwable) returnValue);
        }
      }
    };

    if (dc.handler.getValue() == null) {
      return DataItemParserHandler.createCollection(appContext, dc, cb);
    }

    return getCollectionHandler(dc.handler.getValue()).createCollection(appContext, dc, cb);
  }

  protected abstract iContainer createNullViewer();

  protected abstract iScriptHandler createScriptHandler(Application app);

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
  protected URL createURL(iWidget context, String url) throws MalformedURLException {
    if (url.startsWith(iConstants.LIB_PREFIX)) {
      return context.getAppContext().getResourceURL(url.substring(iConstants.LIB_PREFIX_LENGTH));
    } else if (url.startsWith(iConstants.COLLECTION_PREFIX)) {
      return CollectionURLConnection.createURL(context, url.substring(iConstants.COLLECTION_PREFIX_LENGTH));
    }

    if (urlPrefixesKeys != null) {
      url = handlePrefixMapping(url);
    }

    if (url.startsWith("/") && (applicationRoot != null)) {
      url = applicationRoot + url;
    }

    URL curl = (context == null)
               ? contextURL
               : context.getViewer().getBaseURL();

    return new URL(curl, url);
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
  protected URL createURL(iPlatformAppContext app, URL context, String url) throws MalformedURLException {
    if (url.startsWith(iConstants.LIB_PREFIX)) {
      return app.getResourceURL(url.substring(iConstants.LIB_PREFIX_LENGTH));
    }

    if (url.startsWith(iConstants.COLLECTION_PREFIX)) {
      return CollectionURLConnection.createURL(getRootViewer(), url.substring(iConstants.COLLECTION_PREFIX_LENGTH));
    }

    if (url.startsWith(iConstants.INLINE_PREFIX)) {
      return InlineURLConnection.createURL(url.substring(iConstants.INLINE_PREFIX_LENGTH));
    }
    if (context == null) {
      context = this.contextURL;
    }

    if (urlPrefixesKeys != null) {
      url = handlePrefixMapping(url);
    }

    if (url.startsWith("/") && (applicationRoot != null)) {
      url = applicationRoot + url;
    }

    return new URL(context, url);
  }

  protected void createUserAgentString(String platform, String app) {
    try {
      String agent = iConstants.APPLICATION_NAME_STRING + "/" + iConstants.APPLICATION_VERSION_STRING;

      agent += " (" + platform;
      agent += "; Locale/" + Locale.getDefault().toString();
      agent += ")";

      if (app != null) {
        agent += " (" + app + ")";
      }

      RARE_USER_AGENT = agent;
      System.setProperty("http.agent", agent);
    } catch(Exception e) {
      ignoreStartupException("Cannot set http.agent", e);
    }
  }

  /**
   * Executes a generic background task
   *
   * @param callable
   *          the runnable to be executed
   *
   * @return a Future representing pending completion of the task
   */
  protected iCancelableFuture executeBackgroundTask(Callable callable) {
    return PlatformHelper.executeBackgroundTask(callable, shuttingDown);
  }

  /**
   * Executes a generic background task
   *
   * @param runnable
   *          the runnable to be executed
   *
   * @return a Future representing pending completion of the task
   */
  protected iCancelableFuture executeBackgroundTask(Runnable runnable) {
    return PlatformHelper.executeBackgroundTask(runnable, shuttingDown);
  }

  /**
   * Executes a Swing worker task
   *
   * @param task
   *          the task to be executed
   *
   * @return a Future representing pending completion of the task
   */
  protected iCancelableFuture executeSwingWorkerTask(iWorkerTask task) {
    return PlatformHelper.executeSwingWorkerTask(task, shuttingDown);
  }

  protected void exit() {
    exitEx();
  }

  protected void exitEx() {
    try {
      if (!shuttingDown && listenersCanExit()) {
        shuttingDown = true;
        stopBackgroundThreads();
        fireApplicationExiting();

        if (windowManager != null) {
          windowManager.dispose();
          windowManager = null;
        }

        if (widgetClasses != null) {
          widgetClasses.clear();
        }

        if (runOnceScripts != null) {
          runOnceScripts.clear();
        }

        if (scriptHandler != null) {
          scriptHandler.dispose();
        }

        appContext.dispose();
      }
    } catch(Throwable e) {
      Platform.ignoreException("Shutdown Error", e);
    }

    appData                = null;
    actionMap              = null;
    appContext             = null;
    appListeners           = null;
    autoHilightPainter     = null;
    collectionHandlers     = null;
    dataCollections        = null;
    defaultDateContext     = null;
    defaultDateTimeContext = null;
    defaultRootViewer      = null;
    defaultTimeContext     = null;
    exceptionHandler       = null;
    appContext             = null;
    info                   = null;
    listItemFocusPainter   = null;
    multipartMimeHandler   = null;
    sageApplication        = null;
    scriptHandler          = null;
    windowManager          = null;
    printHandler           = null;
  }

  protected void fireApplicationDidInit() {
    if (appListeners != null) {
      for (iApplicationListener listener : appListeners) {
        listener.applicationInitialized(appContext);
      }
    }
  }

  protected void fireApplicationExiting() {
    if (appListeners != null) {
      for (iApplicationListener listener : appListeners) {
        listener.applicationClosing(appContext);
      }
    }
  }

  protected void handleAuthFailure(final URL url, final int retries, final String local) {
    throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.authFailure"));
  }

  protected abstract iURLConnection handleIfFileOrLibURL(URL url, String mimeType) throws IOException;

  protected void addPrefixMapping(String prefix, String value) {
    if (urlPrefixesKeys == null) {
      urlPrefixesKeys   = new String[] { prefix };
      urlPrefixesValues = new String[] { value };
    } else {
      String a[] = new String[urlPrefixesKeys.length + 1];

      System.arraycopy(urlPrefixesKeys, 0, a, 0, urlPrefixesKeys.length);
      urlPrefixesKeys = a;
      a               = new String[urlPrefixesValues.length + 1];
      System.arraycopy(urlPrefixesValues, 0, a, 0, urlPrefixesValues.length);
      urlPrefixesValues                               = a;
      urlPrefixesKeys[urlPrefixesKeys.length - 1]     = prefix;
      urlPrefixesValues[urlPrefixesValues.length - 1] = value;
    }
  }

  protected String handlePrefixMapping(String path) throws MalformedURLException {
    if (urlPrefixesKeys != null) {
      final int    len = urlPrefixesKeys.length;
      final String a[] = urlPrefixesKeys;

      for (int i = 0; i < len; i++) {
        String s = a[i];

        if (path.startsWith(s)) {
          path = urlPrefixesValues[i] + path.substring(s.length());

          break;
        }
      }
    }

    return path;
  }

  /**
   * Returns whether format exceptions should be ignored. If true then format
   * exceptions will be transparently handled where ever possible Number format
   * exceptions will return a number whose value is zero and date format
   * exceptions will return a date object whose toString() method returns the
   * string that cased the format exception
   *
   * @return true if format exceptions should be ignored; false otherwise
   */
  protected boolean ignoreFormatExceptions() {
    return ignoreFormatExceptions;
  }

  // Can be called prior to envionment being initialized
  protected static void ignoreStartupException(String msg, Throwable e) {
    if (debugEnabled) {
      if (msg != null) {
        System.err.println(msg);
      }

      if (e != null) {
        e.printStackTrace(System.err);
      }
    }
  }

  protected void initApplication(Application app) {
    String s = app.applicationRoot.getValue();

    if (s != null) {
      s = s.trim();

      if (s.length() > 0) {
        if (s.equals(".")) {
          s = contextURLBaseString;
        } else if (s.equals("..")) {
          s = contextURL.getFile();

          int n = s.lastIndexOf('/');

          if (n != -1) {
            int p = s.lastIndexOf('/', n - 1);

            if (p != -1) {
              s = s.substring(0, p);
            }
          }
        }

        s               = s.replace(" ", "%20");
        applicationRoot = s;
      }
    }
  }

  protected void initialize() {
    applicationName = "Rare Application";
    osType          = Platform.getOsType();
    registerDefaultViewers();

    int max = SNumber.intValue(Platform.getProperty("rare.maxThreads",
                Platform.getProperty("jnlp.rare.maxThreads", "2")));

    if (max < 0) {
      max = 1;
    } else if (max > 30) {
      max = 30;
    }

    int imageMax = 0;

    if (isImageLoaderUsefull()) {
      imageMax = SNumber.intValue(Platform.getProperty("rare.imageLoaderThreads",
              Platform.getProperty("jnlp.rare.imageLoaderThreads", "2")));
    }

    PlatformHelper.initializeThreadingService(max, imageMax);
  }

  protected boolean listenersCanExit() {
    if (appListeners != null) {
      for (iApplicationListener listener : appListeners) {
        if (!listener.allowClosing(appContext)) {
          return false;
        }
      }
    }

    return true;
  }

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
  protected String loadScriptCode(ActionLink link, boolean runOnce) {
    String code = null;

    try {
      if (link.isInlineURL()) {
        code = link.getContentAsString();

        if (runOnce && (code != null)) {
          String s = Functions.sha1(code);

          if ((s != null) && runOnceScripts.containsKey(s)) {
            code = null;
          } else if (runOnce) {
            runOnceScripts.put(s, Long.valueOf(1));
          }
        }
      } else {
        URL url = link.getURL(getRootViewer());

        if (url != null) {
          String s    = url.toExternalForm();
          Long   date = runOnceScripts.get(s);

          link.getContentType();    // force content type to be set

          Long nd = link.getConnection().getLastModified();

          if ((date == null) ||!nd.equals(date)) {
            code = link.getContentAsString();

            if (runOnce) {
              runOnceScripts.put(s, nd);
            }
          }
        }
      }
    } catch(IOException ex) {
      throw ApplicationException.runtimeException(ex);
    } finally {
      link.close();
    }

    if ((code != null) && (code.length() > 0)) {
      return code;
    }

    return null;
  }

  protected void loadWidgetHandlers(Map map) {
    try {
      Iterator it = map.entrySet().iterator();
      Entry    e;
      String   s;
      String   value;
      Class    cls;

      while(it.hasNext()) {
        e     = (Entry) it.next();
        s     = (String) e.getKey();
        value = (String) e.getValue();

        if ((value != null) && (value.length() > 0) && (s != null) && (s.length() > 0)) {
          if (s.indexOf('.') == -1) {
            s = Platform.RARE_PACKAGE_NAME + ".viewer." + s;
          }

          cls = PlatformHelper.loadClass(s);
          registerWidgetClass(value, cls);
        }
      }
    } catch(Exception ex) {
      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      }

      throw new RuntimeException(ex);
    }
  }

  protected boolean okForOS(iSPOTElement e) {
    return okForOS(e.spot_getAttribute("os"), null);
  }

  @SuppressWarnings("resource")
  protected boolean okForOS(String os, CharScanner sc) {
    if ((os == null) || (os.length() == 0)) {
      return true;
    }

    List<String> oses = null;
    int          n    = os.indexOf(',');

    if (n == -1) {
      n = os.indexOf('-');

      if (n == -1) {
        if (os.startsWith("not_")) {
          return !isOSThingEquals(os, os.substring(4));
        } else {
          return isOSThingEquals(os, os);
        }
      }

      oses = Arrays.asList(os);

      if (sc == null) {
        sc = new CharScanner();
      }
    } else {
      if (sc == null) {
        sc = new CharScanner(os);
      } else {
        sc.reset(os);
      }

      oses = sc.getTokens(',', true);
    }

    String ss;

    for (String s : oses) {
      sc.reset(s);

      while((ss = sc.nextToken('-', true)) != null) {
        if (ss.startsWith("not_")) {
          if (isOSThingEquals(s, ss.substring(4))) {
            return false;
          }
        } else {
          if (isOSThingEquals(s, ss)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  protected void promptForURL(final StartupInfo info, String url) {
    try {
      String     prompt = getResourceBundle().getString("Rare.runtime.text.urlSelectionPrompt");
      String     title  = getResourceBundle().getString("Rare.runtime.text.urlSelectionTitle");
      AlertPanel d      = AlertPanel.prompt(Platform.getContextRootViewer(), title, prompt, url, null);

      d.showDialog(new iFunctionCallback() {
        @Override
        public void finished(boolean canceled, Object returnValue) {
          if (!canceled && (returnValue instanceof String)) {
            String s = (String) returnValue;

            s = s.trim();

            if (s.length() > 0) {
              tryAgain(info, s);

              return;
            }
          }

          System.exit(0);
        }
      });
    } catch(Throwable e) {}
  }

  /**
   * Registers a action for later retrieval via the <code>getAction()</code>
   * method The action must have a valid unique name that can be retrieved via a
   * call to <code>UIAction.getActionName()</code>
   *
   * @param a
   *          the action to register
   * @see #getAction
   */
  protected void registerAction(UIAction a) {
    String name = a.getActionName();

    if ((name != null) && (name.length() > 0)) {
      if (actionMap == null) {
        actionMap = new HashMap<String, UIAction>();
      }

      actionMap.put(name, a);

      if (a instanceof aFocusedAction) {
        registerFocusedAction((aFocusedAction) a);
      }
    }
  }

  /**
   * Register a collection handler
   *
   * @param name
   *          the name of the collection handler
   * @param ch
   *          the collection handler object
   */
  protected void registerCollectionHandler(String name, iDataCollectionHandler ch) {
    if (collectionHandlers == null) {
      collectionHandlers = new HashMap<String, Object>();
    }

    collectionHandlers.put(name, ch);
  }

  /**
   * Registers the class for a data collection handler
   *
   * @param name the
   *          name of the handler
   * @param cls
   *          the class that implements the handler functionality
   */
  protected void registerCollectionHandlerClass(String name, Class cls) {
    if (collectionHandlers == null) {
      collectionHandlers = new HashMap<String, Object>();
    }

    collectionHandlers.put(name, cls);
  }

  protected void registerDataCollection(iDataCollection dc) {
    if ((dc != null) && (dc.getCollectionName() != null)) {
      if (dataCollections == null) {
        dataCollections = new HashMap<String, iDataCollection>();
      }

      dataCollections.put(dc.getCollectionName(), dc);
    }
  }

  protected void registerDefaultActions() {
    UIAction a;
    String   s;

    a = ActionHelper.getCutAction();
    s = appContext.getResourceAsString("Rare.action.cut");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.cut"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getCopyAction();
    s = appContext.getResourceAsString("Rare.action.copy");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.copy"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getPasteAction();
    s = appContext.getResourceAsString("Rare.action.paste");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.paste"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getDeleteAction();
    s = appContext.getResourceAsString("Rare.action.delete");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.delete"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getSelectAllAction();
    s = appContext.getResourceAsString("Rare.action.selectAll");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.empty"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getUndoAction();
    s = appContext.getResourceAsString("Rare.action.undo");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.undo"));
    a.setEnabled(false);
    this.registerAction(a);
    a = ActionHelper.getRedoAction();
    s = appContext.getResourceAsString("Rare.action.redo");
    a.setActionText(s);
    a.setIcon(appContext.getResourceAsIcon("Rare.icon.redo"));
    a.setEnabled(false);
    this.registerAction(a);
  }

  /**
   * Registers default viewers
   */

  /**
   * Registers default viewers
   */
  protected void registerDefaultViewers() {
    String name;
    String viewerPrefix = aViewer.class.getPackage().getName() + ".";
    String widgetPrefix = aWidget.class.getPackage().getName() + ".";

    registerWidgetClass(iConstants.HTML_MIME_TYPE, WebBrowser.class);
    registerWidgetClass(iConstants.TEXT_MIME_TYPE, WebBrowser.class);
    registerWidgetClass(iConstants.RICHTEXT_MIME_TYPE, WebBrowser.class);
    name = Platform.getSPOTName(GridPane.class);
    registerWidgetClass(name, GridPaneViewer.class);
    name = Platform.getSPOTName(Form.class);
    registerWidgetClass(name, FormViewer.class);
    name = Platform.getSPOTName(WidgetPane.class);
    registerWidgetClass(name, WidgetPaneViewer.class);
    name = Platform.getSPOTName(ImagePane.class);
    registerWidgetClass(name, ImagePaneViewer.class);
    name = Platform.getSPOTName(MenuBar.class);
    registerWidgetClass(name, MenuBarViewer.class);
    name = Platform.getSPOTName(ToolBar.class);
    registerWidgetClass(name, ToolBarViewer.class);
    name = Platform.getSPOTName(StackPane.class);
    registerWidgetClass(name, StackPaneViewer.class);
    name = Platform.getSPOTName(Browser.class);
    registerWidgetClass(name, WebBrowser.class);
    name = Platform.getSPOTName(PushButton.class);
    registerWidgetClass(name, PushButtonWidget.class);
    name = Platform.getSPOTName(Label.class);
    registerWidgetClass(name, LabelWidget.class);
    name = Platform.getSPOTName(ListBox.class);
    registerWidgetClass(name, ListBoxViewer.class);
    name = Platform.getSPOTName(TextField.class);
    registerWidgetClass(name, TextFieldWidget.class);
    name = Platform.getSPOTName(TextArea.class);
    registerWidgetClass(name, TextAreaWidget.class);
    name = Platform.getSPOTName(PasswordField.class);
    registerWidgetClass(name, TextFieldWidget.class);
    name = Platform.getSPOTName(CheckBox.class);
    registerWidgetClass(name, CheckBoxWidget.class);
    name = Platform.getSPOTName(RadioButton.class);
    registerWidgetClass(name, RadioButtonWidget.class);
    name = Platform.getSPOTName(GroupBox.class);
    registerWidgetClass(name, GroupBoxViewer.class);
    name = Platform.getSPOTName(Line.class);
    registerWidgetClass(name, LineWidget.class);
    name = Platform.getSPOTName(Bean.class);
    registerWidgetClass(name, BeanWidget.class);
    name = Platform.getSPOTName(CheckBoxList.class);
    registerWidgetClass(name, CheckBoxListViewer.class);
    registerWidgetClass(iConstants.JPEG_MIME_TYPE, ImagePaneViewer.class);
    registerWidgetClass(iConstants.GIF_MIME_TYPE, ImagePaneViewer.class);
    registerWidgetClass(iConstants.PNG_MIME_TYPE, ImagePaneViewer.class);
    registerWidgetClass(iConstants.SVG_MIME_TYPE, ImagePaneViewer.class);
    registerWidgetClass(iConstants.SVGXML_MIME_TYPE, ImagePaneViewer.class);
    name = Platform.getSPOTName(Table.class);
    registerWidgetClass(name, viewerPrefix + "TableViewer");
    name = Platform.getSPOTName(TreeTable.class);
    registerWidgetClass(name, viewerPrefix + "TableViewer");
    name = Platform.getSPOTName(PropertyTable.class);
    registerWidgetClass(name, viewerPrefix + "TableViewer");
    registerWidgetClass(iConstants.CSV_MIME_TYPE, viewerPrefix + "TableViewer");
    name = Platform.getSPOTName(SplitPane.class);
    registerWidgetClass(name, viewerPrefix + "SplitPaneViewer");
    name = Platform.getSPOTName(TabPane.class);
    registerWidgetClass(name, viewerPrefix + "TabPaneViewer");
    name = Platform.getSPOTName(Tree.class);
    registerWidgetClass(name, viewerPrefix + "TreeViewer");
    name = Platform.getSPOTName(Carousel.class);
    registerWidgetClass(name, viewerPrefix + "CarouselViewer");
    name = Platform.getSPOTName(Canvas.class);
    registerWidgetClass(name, viewerPrefix + "CanvasViewer");
    name = Platform.getSPOTName(StatusBar.class);
    registerWidgetClass(name, viewerPrefix + "StatusBarViewer");
    name = Platform.getSPOTName(Chart.class);
    registerWidgetClass(name, viewerPrefix + "ChartViewer");
    name = Platform.getSPOTName(DocumentPane.class);
    registerWidgetClass(name, viewerPrefix + "DocumentPaneViewer");
    name = Platform.getSPOTName(CollapsiblePane.class);
    registerWidgetClass(name, viewerPrefix + "CollapsiblePaneViewer");
    name = Platform.getSPOTName(CheckBoxTree.class);
    registerWidgetClass(name, viewerPrefix + "CheckBoxTreeViewer");
    name = Platform.getSPOTName(Slider.class);
    registerWidgetClass(name, widgetPrefix + "SliderWidget");
    name = Platform.getSPOTName(ProgressBar.class);
    registerWidgetClass(name, widgetPrefix + "ProgressBarWidget");
    name = Platform.getSPOTName(Spinner.class);
    registerWidgetClass(name, widgetPrefix + "SpinnerWidget");
    name = Platform.getSPOTName(NumberSpinner.class);
    registerWidgetClass(name, widgetPrefix + "SpinnerWidget");
    name = Platform.getSPOTName(DateSpinner.class);
    registerWidgetClass(name, widgetPrefix + "SpinnerWidget");
    name = Platform.getSPOTName(DateTimeSpinner.class);
    registerWidgetClass(name, widgetPrefix + "SpinnerWidget");
    name = Platform.getSPOTName(TimeSpinner.class);
    registerWidgetClass(name, widgetPrefix + "SpinnerWidget");
    name = Platform.getSPOTName(ComboBox.class);
    registerWidgetClass(name, widgetPrefix + "ComboBoxWidget");
    name = Platform.getSPOTName(DateChooser.class);
    registerWidgetClass(name, widgetPrefix + "DateChooserWidget");
    name = Platform.getSPOTName(ColorChooser.class);
    registerWidgetClass(name, widgetPrefix + "ColorChooserWidget");
    name = Platform.getSPOTName(Navigator.class);
    registerWidgetClass(name, widgetPrefix + "NavigatorWidget");
  }

  /**
   * Registers a focused action. This method is automatically called by the
   * focused action is question and should no be called otherwise.It will
   * automatically be unregistered when it is no longer referenced
   *
   * @param action
   *          the action to register
   */
  protected void registerFocusedAction(aFocusedAction action) {
    if (focusedActions == null) {
      focusedActions = new ArrayList<iWeakReference>(1);
    }

    focusedActions.add(PlatformHelper.createWeakReference(action));
  }

  /**
   * Registers the specified set of JARs with the class loader
   *
   * @param set
   *          the JARS to register
   */
  protected abstract void registerJARs(SPOTSet set);

  /**
   * Registers the class for a specific mime type
   *
   * @param type
   *          the mime type of
   * @param cls
   *          the class that implements the widget functionality
   */
  protected void registerWidgetClass(String type, Object cls) {
    if (type.indexOf(',') == -1) {
      widgetClasses.put(type, cls);

      return;
    }

    List<String> list = CharScanner.getTokens(type, ',', true);
    int          len  = list.size();

    for (int i = 0; i < len; i++) {
      widgetClasses.put(list.get(i), cls);
    }
  }

  /**
   * Resets the script runOnce status allowing it to be run again
   *
   * @param link
   *          the link for the script
   */
  protected void resetRunOnce(ActionLink link) {
    String code = null;

    try {
      if (link.isInlineURL()) {
        code = link.getContentAsString();

        if (code != null) {
          String s = Functions.sha1(code);

          if (s != null) {
            runOnceScripts.remove(s);
          }
        }
      } else {
        URL url = link.getURL(getRootViewer());

        if (url != null) {
          String s = url.toExternalForm();

          runOnceScripts.remove(s);
        }
      }
    } catch(IOException ex) {
      Platform.ignoreException(null, ex);
    } finally {
      link.close();
    }
  }

  protected URL resolveApplicationURL(String s) throws MalformedURLException {
    if (s != null) {
      if (s.startsWith(iConstants.LIB_PREFIX)) {
        return getAppContext().getResourceURL(s.substring(iConstants.LIB_PREFIX_LENGTH));
      } else {
        return new URL(s);
      }
    }

    return null;
  }

  protected SPOTSet resolveSet(SPOTSet set, Class cls) {
    return DataParser.resolveSet(getRootViewer(), set, cls);
  }

  protected void setupApplicationObject(URL url, SPOTSequence seq, String mime) throws Exception {
    setContextURL(url);

    if (seq instanceof Application) {
      sageApplication = (Application) seq;

      if (sageApplication.contextURL.spot_hasValue()) {
        String s = sageApplication.contextURL.spot_getAttribute("redirect");

        if ("true".equalsIgnoreCase(s)) {
          URL u = new URL(url, sageApplication.contextURL.getValue());

          if (!u.equals(url)) {
            createApplicationObject(new URL[] { u }, (String) null);

            return;
          }
        }
      }
    } else if (seq instanceof Widget) {
      sageApplication = new Application();
      sageApplication.getMainWindowReference().viewer.setValue(seq);
      sageApplication.getMainWindowReference().title.setValue(((Widget) seq).title.stringValue());
    } else if (seq instanceof MainWindow) {
      sageApplication = new Application();
      sageApplication.getMainWindowReference().spot_copy(seq);
    }

    handleConfigurationURLs(sageApplication);
    setupOsSpecificInfo(sageApplication, url, mime);
  }

  protected void setupAutoHilightPainter(GridCell gc) {
    if (gc != null) {
      autoHilightPainter = ColorUtils.configure(getRootViewer(), gc, null);
    } else {
      PaintBucket pb = (PaintBucket) selectionPainter.clone();

      if (pb.getBackgroundColor() != null) {
        pb.setBackgroundColor(pb.getBackgroundColor().alpha(25));

        if (pb.getBackgroundPainter() != null) {
          pb.setBackgroundPainter(pb.getBackgroundPainter().alpha(25));
        }
      }

      autoHilightPainter = pb;
    }
  }

  protected void setupLostFocusSelectionPainter(GridCell gc) {
    if ((gc != null) && okForOS(gc)) {
      lostFocusSelectionPainter = ColorUtils.configure(getRootViewer(), gc, null);
    }
  }

  protected void setupOsSpecificInfo(Application app, URL url, String mime) {}

  protected void setupSelectionPainter(GridCell gc) {
    if (gc != null) {
      selectionPainter = ColorUtils.configure(getRootViewer(), gc, null);
    } else {
      selectionPainter = new SelectionPainter(false);
    }
  }

  protected void setupUrlPrefixes() {
    String s = appContext.getUIDefaults().getString("Rare.urlPrefixes");

    if (s == null) {
      return;
    }

    Map<String, String> map = CharScanner.parseOptionStringEx(s, ',');

    urlPrefixesKeys   = map.keySet().toArray(new String[map.size()]);
    urlPrefixesValues = map.values().toArray(new String[map.size()]);
  }

  protected void showErrorDialog(Throwable e, boolean abort) {
    WaitCursorHandler.stopWaitCursor(null, true);

    ErrorInformation ei;

    if ((scriptHandler != null) && (appContext != null)) {
      ei = scriptHandler.getErrorInformation(appContext, e);
    } else {
      ei = new ErrorInformation(e);
    }

    if (abort) {
      AlertPanel.showErrorDialog(ei, new iFunctionCallback() {
        @Override
        public void finished(boolean canceled, Object returnValue) {
          appContext.exit();
        }
      });
    } else {
      AlertPanel.showErrorDialog(ei);
    }
  }

  protected void showStartupError(final String url, Throwable e, final boolean fatal) {
    try {
      WaitCursorHandler.stopWaitCursor(null, true);

      String msg   = url;
      String title = appContext.getResourceAsString(fatal
              ? "fatalError"
              : "loadError");

      if ((msg != null) && (e != null)) {
        msg = ApplicationException.getMessageEx(e) + "\r\nURL:" + msg;
      } else if (e != null) {
        msg = ApplicationException.getMessageEx(e);
      }

      ErrorInformation ei = new ErrorInformation(e, title, msg);

      AlertPanel.showErrorDialog(ei, new iFunctionCallback() {
        @Override
        public void finished(boolean canceled, Object returnValue) {
          if (!fatal) {
            promptForURL(info, url);
          }
        }
      });
    } catch(Throwable ex) {
      e.printStackTrace();
    }
  }

  protected abstract void start();

  protected void stopBackgroundThreads() {
    PlatformHelper.stopBackgroundThreads();
  }

  protected void tryAgain(StartupInfo info, String file) {
    info.applicationFile = file;

    URL url = null;

    try {
      url = resolveApplicationURL(info.applicationFile);
      createApplicationObject(new URL[] { url }, info.local);
    } catch(Exception e) {
      showStartupError(file, e, false);

      return;
    }

    start();
  }

  protected void uiPropertiesLoaded() throws Exception {
    iWidgetCustomizer wc = appContext.getWidgetCustomizer();

    if (wc == null) {
      try {
        String s = (String) appContext.getUIDefaults().get("Rare.WidgetCustomizer");

        if ((s != null) && (s.length() > 0)) {
          appContext.setWidgetCustomizer((iWidgetCustomizer) Platform.createObject(s));
        }
      } catch(Exception e) {
        Platform.ignoreException("trying to load iWidgetCustomizer", e);
      }
    }
  }

  protected void unregisterDataCollection(iDataCollection dc) {
    if ((dc != null) && (dataCollections != null) && (dc.getCollectionName() != null)) {
      dataCollections.remove(dc.getCollectionName());
    }
  }

  protected URL setApplicationURL(URL url) {
    applicationURL = url;

    String info = url.getUserInfo();

    if (info != null) {
      try {
        url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile());
      } catch(MalformedURLException ignore) {}

      userInfo = new LinkedHashMap();
      userInfo.put(JavaURLConnection.parenToExternalForm(url), info);
    }

    return url;
  }

  protected void setContextURL(URL url) {
    contextURL = url;

    if (getRootViewer() != null) {
      getRootViewer().setContextURL(url);
    }

    if (getWindowManager() != null) {
      getWindowManager().setContextURL(url);
    }

    contextURLBaseString = JavaURLConnection.toExternalForm(url);

    int n = contextURLBaseString.lastIndexOf('/');

    if (n != -1) {
      contextURLBaseString = contextURLBaseString.substring(0, n);
    }

    if (applicationRoot == null) {
      applicationRoot = contextURLBaseString;
    }
  }

  protected void setDefaultExceptionHandler(iExceptionHandler eh) {
    exceptionHandler = eh;
  }

  /**
   * Gets a previously registered action. Retrieves both permanent and transient
   * actions.
   *
   * @param name
   *          the name of the action
   *
   * @return the named action or null if the action is not registered
   *
   */
  protected UIAction getAction(String name) {
    return (actionMap == null)
           ? null
           : actionMap.get(name);
  }

  protected URL getApplicationURL() {
    return applicationURL;
  }

  /**
   * Gets the data collection handler with the given name
   *
   * @param name
   *          the name of the handler
   *
   * @return the data collection handler with the given name
   */
  protected iDataCollectionHandler getCollectionHandler(String name) {
    Object o = null;

    if ((name != null) && (collectionHandlers != null)) {
      o = collectionHandlers.get(name);
    }

    if ((o == null) && (name.indexOf('.') != -1)) {
      try {
        o = PlatformHelper.loadClass(name);
      } catch(Throwable e) {
        Platform.ignoreException(null, e);
      }
    }

    if (o instanceof Class) {
      if (((Class) o).isAssignableFrom(iDataCollectionHandler.class)) {
        try {
          o = ((Class) o).newInstance();
          collectionHandlers.put(name, o);
        } catch(Exception ex) {
          o = null;
        }
      }
    }

    if (o == null) {
      throw new ApplicationException(
          Helper.expandString(appContext.getResourceAsString("Rare.runtime.text.noSuchCollectionHandler"), name));
    }

    return (iDataCollectionHandler) o;
  }

  protected iExceptionHandler getDefaultExceptionHandler() {
    return (exceptionHandler == null)
           ? this
           : exceptionHandler;
  }

  protected String getOSType() {
    return osType;
  }

  protected iContainer getRootViewer() {
    iContainer rv = (windowManager == null)
                    ? null
                    : windowManager.getRootViewer();

    if (rv == null) {
      if (defaultRootViewer == null) {
        defaultRootViewer = createNullViewer();
      }

      return defaultRootViewer;
    }

    return rv;
  }

  /**
   * Get the language type for a script from the the link
   *
   * @param link
   *          the link
   * @return the script language type or null of the type could not be
   *         identified
   */
  protected String getScriptType(ActionLink link) {
    try {
      String  type    = link.getMimeType();
      iWidget context = link.getContext();

      if (context == null) {
        context = getRootViewer();
      }

      if ((type == null) || iConstants.TEXT_MIME_TYPE.equals(type) || iConstants.OCTET_MIME_TYPE.equals(type)) {
        String s = link.getURL(context).toExternalForm();
        int    n = s.lastIndexOf('.');

        if (n > -1) {
          type = s.substring(n + 1);
        }
      }

      return type;
    } catch(IOException ex) {
      return null;
    }
  }

  /**
   * Gets the painter to use for a selected item to when the item's widget has
   * focus
   *
   * @return the color
   */
  protected PaintBucket getSelectionPainter() {
    return selectionPainter;
  }

  /**
   * Converts the string representation of a URL to a URL object
   *
   * @param url
   *          the string representation of a URL
   * @return the URL
   * @throws MalformedURLException
   */
  protected URL getURL(String url) throws MalformedURLException {
    return getRootViewer().getURL(url);
  }

  protected String getUserInfo(URL url) {
    if ((userInfo == null) || (url.getUserInfo() != null)) {
      return null;
    }

    String   u  = JavaURLConnection.toExternalForm(url);
    Iterator it = userInfo.keySet().iterator();

    while(it.hasNext()) {
      String path = (String) it.next();

      if (u.startsWith(path)) {
        return (String) userInfo.get(path);
      }
    }

    return null;
  }

  protected boolean isImageLoaderUsefull() {
    return true;
  }

  protected boolean isOSThingEquals(String value, String segment) {
    if (segment.equals("touch")) {
      return Platform.isTouchDevice();
    }

    if (segment.equals("touchable")) {
      return Platform.isTouchableDevice();
    }

    if (segment.equals("android")) {
      return Platform.isAndroid();
    }

    if (segment.equals("ios")) {
      return Platform.isIOS();
    }

    if (segment.equals("mac") || segment.equals("osx") || segment.equals("os x")) {
      return Platform.isMac();
    }

    if (segment.equals("windows")) {
      return Platform.isWindows();
    }

    if (segment.equals("apple")) {
      return Platform.isMac() || Platform.isIOS();
    }

    if (segment.equals("java")) {
      return Platform.isJava();
    }

    if (segment.equals("linux")) {
      return Platform.isLinux();
    }

    if (segment.equals("swing")) {
      return Platform.isSwing();
    }

    if (segment.equals("mdpi")) {
      return ScreenUtils.isMediumDensity();
    }

    if (segment.equals("xhdpi")) {
      return ScreenUtils.isHighDensity();
    }

    if (segment.equals("ldpi")) {
      return ScreenUtils.isHighDensity();
    }

    if (segment.equals("small")) {
      return ScreenUtils.isSmallScreen();
    }

    if (segment.equals("large")) {
      return ScreenUtils.isLargeScreen();
    }

    if (segment.equals("medium")) {
      return ScreenUtils.isMediumScreen();
    }

    if (segment.equals("dark")) {
      return ColorUtils.getForeground().isDarkColor();
    }

    if (segment.equals("light")) {
      return !ColorUtils.getForeground().isDarkColor();
    }

    if (segment.equals("v")) {
      float n = SNumber.floatValue(segment.substring(1));

      if (value.contains("java")) {
        return Platform.getJavaVersion() >= n;
      }

      return Platform.getOsVersion() >= n;
    }

    if (segment.startsWith("lang_")) {
      segment = segment.substring(5);

      String lang = Locale.getDefault().getLanguage();

      return (lang != null) && lang.equalsIgnoreCase(segment);
    }

    return false;
  }

  private void handleConfigurationLocaleURLs(Application app, iWidget context, SPOTPrintableString url)
          throws Exception {
    ActionLink link = null;

    if ("true".equalsIgnoreCase(url.spot_getAttribute("localeSensitive"))) {
      String   s  = url.spot_getAttribute("locale");
      String[] lp = Helper.getLocalResourcePostfix(Locale.getDefault());

      if ((s != null) && (s.length() > 0)) {    // check to see if we already have
        // the right file
        for (int i = 0; i < lp.length; i++) {
          if (lp[i].equalsIgnoreCase(s)) {
            lp = null;

            break;
          }
        }
      }

      if ((lp != null) && (lp.length > 0)) {
        String ext = null;

        s = url.getValue();

        int n = s.lastIndexOf('.');

        if (n != -1) {
          ext = s.substring(n + 1);
          s   = s.substring(0, n);
        }

        StringBuilder sb = new StringBuilder(s.length() + 10);

        for (int i = 0; i < lp.length; i++) {
          sb.setLength(0);
          sb.append(s).append(lp[i]);

          if (ext != null) {
            sb.append('.').append(ext);
          }

          link = new ActionLink(context, sb.toString(), null);

          try {
            link = new ActionLink(link.getContentAsString(), link.getContentType());
          } catch(Exception e) {
            link = null;
          }
        }
      }
    }

    if (link == null) {
      link = ActionLink.createInlineLinkIfNecessary(context, url);
    }

    if (link != null) {
      url.spot_setLinkedData(new ActionLink[] { link });
    }
  }

  private void handleConfigurationURLs(Application app) throws Exception {
    iWidget context       = getRootViewer();
    boolean supportxsmall = false;

    if (app.managedScreenSizes.spot_valueWasSet()) {
      switch(app.managedScreenSizes.getValue()) {
        case Application.CManagedScreenSizes.xsmall_small_large :
          supportxsmall = true;
        //$FALL-THROUGH$
        case Application.CManagedScreenSizes.small_large :
          multiScreenSupport = true;

          break;

        case Application.CManagedScreenSizes.xsmall_small_medium_large :
          supportxsmall = true;
        //$FALL-THROUGH$
        case Application.CManagedScreenSizes.small_medium_large :
          multiScreenSupport    = true;
          mediumScreenSupported = true;

          break;

        default :
          break;
      }

      if (multiScreenSupport) {
        multiScreenAutoFallback = !"false".equals(app.managedScreenSizes.spot_getAttribute("autoFallback"));
      }
    }

    URL scriptContextURL = contextURL;

    if (multiScreenSupport) {
      int    xsmall = 0;
      int    small  = 0;
      int    medium = 0;
      String s      = app.managedScreenSizes.spot_getAttribute("smallScreenPointSize");

      if (s != null) {
        small = SNumber.intValue(s);
      }

      if (supportxsmall) {
        s = app.managedScreenSizes.spot_getAttribute("xsmallScreenPointSize");

        if (s != null) {
          xsmall = SNumber.intValue(s);
        }
      }

      if (mediumScreenSupported) {
        s      = app.managedScreenSizes.spot_getAttribute("mediumScreenPointSize");
        medium = SNumber.intValue(s);
      }

      if (small > 0) {
        ScreenUtils.setScreenSize(ScreenUtils.calculateScreenSize(xsmall, small, medium));
      }

      if (multiScreenAutoFallback) {
        ActionLink.setGlobalErrorHandler(new MultiScreenFallbackErrorHandler());
      }

      ActionLink     link = new ActionLink(getURL(ScreenUtils.getRelativeScreenSizeName() + "/mainwindow.rml"));
      iURLConnection conn = link.getConnection();
      MainWindow     w    = new MainWindow();

      DataParser.loadSPOTObject(context, conn, w);
      app.setMainWindow(w);
      scriptContextURL = link.getURL(context);
      link.close();
    }

    MainWindow mw = sageApplication.getMainWindowReference();

    if (!context.isDesignMode() && mw.scriptURL.spot_hasValue()) {
      String s = mw.scriptURL.spot_getAttribute("inline");

      if (!"true".equals(s) ||!mw.scriptURL.spot_isValuePreformatted()) {
        URL u;

        s = mw.scriptURL.getValue();

        if (s.startsWith("/")) {
          u = new URL(contextURLBaseString + s);
        } else {
          u = new URL(scriptContextURL, s);
        }

        ActionLink link = new ActionLink(u);
        String     type = appContext.getScriptType(link);
        String     code = link.getContentAsString();

        mw.scriptURL.spot_setLinkedData(new ObjectHolder(getRelativeLocation(u), type, code));
      }
    }

    Viewer viewer = (Viewer) mw.viewer.getValue();

    if (viewer != null) {
      checkMainViewerElements(context, viewer, scriptContextURL);
    }

    ActionLink link = ActionLink.createInlineLinkIfNecessary(context, app.attributesURL);

    if (link != null) {
      app.attributesURL.spot_setLinkedData(link);
    }

    link = ActionLink.createInlineLinkIfNecessary(context, app.dataCollectionsURL);

    if (link != null) {
      app.dataCollectionsURL.spot_setLinkedData(link);
    }

    link = ActionLink.createInlineLinkIfNecessary(context, app.lookAndFeelPropertiesURL);

    if (link != null) {
      app.lookAndFeelPropertiesURL.spot_setLinkedData(link);
    }

    MenuBar mb = app.getMainWindowReference().getMenuBar();

    if (mb != null) {
      link = ActionLink.createInlineLinkIfNecessary(context, mb.dataURL);

      if (link != null) {
        mb.dataURL.spot_setLinkedData(link);
      }
    }

    StatusBar sb = app.getMainWindowReference().getStatusBar();

    if (sb != null) {
      link = ActionLink.createInlineLinkIfNecessary(context, sb.dataURL);

      if (link != null) {
        sb.dataURL.spot_setLinkedData(link);
      }
    }

    handleConfigurationLocaleURLs(app, context, app.resourceStringsURL);
    handleConfigurationLocaleURLs(app, context, app.resourceIconsURL);
    setContextURL(scriptContextURL);
  }

  protected void checkMainViewerElements(iWidget context, Viewer viewer, URL contextURL) throws Exception {
    DataParser.INLINE_REGION_VIEWER_URLS             = true;
    DataParser.INLINE_SELECTED_STACKPANE_VIEWER_URLS = true;
    DataParser.checkElement(context, viewer, contextURL);
    DataParser.INLINE_REGION_VIEWER_URLS             = false;
    DataParser.INLINE_SELECTED_STACKPANE_VIEWER_URLS = false;
  }

  public class MultiScreenFallbackErrorHandler implements iErrorHandler {
    private iErrorHandler errorHandler;

    @Override
    public Action handleError(ActionLink link, Exception ex, iURLConnection conn) {
      return Action.CHANGE;
    }

    public void setErrorHandler(iErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
    }

    @Override
    public iURLConnection getConnectionChange(ActionLink link, Exception ex, iURLConnection oconn) throws IOException {
      if (ex instanceof ConnectException) {
        throw(ConnectException) ex;
      }

      String mimeType = null;

      if (oconn instanceof JavaURLConnection) {
        if (oconn.getConnectionObject() == null) {
          throw new ClosedChannelException();
        }

        mimeType = ((JavaURLConnection) oconn).getPassedInMimeType();
      }

      iURLConnection conn   = null;
      URL            url    = oconn.getURL();
      MutableInteger size   = new MutableInteger(0);
      String         format = createScreenRelativeFormatString(url, size);

      if (format == null) {
        return callOtherHandler(link, ex, conn);
      }

      int inc = 1;
      int n   = size.get() + 1;

      if (n > 2) {
        n   = 1;    // go to medium
        inc = -1;
      }

      String s = null;

      switch(n) {
        case 0 :
          s = format.replace("__RARE__", "small");

          break;

        case 1 :
          if (mediumScreenSupported) {
            s = format.replace("__RARE__", "medium");
          }

          break;

        case 2 :
          s = format.replace("__RARE__", "large");

          break;
      }

      if (s != null) {
        try {
          conn = openConnection(new URL(s), null);
          conn.open();

          return conn;
        } catch(HTTPException e) {}
        catch(FileNotFoundException e) {}
        catch(IOException e) {}
      }

      n += inc;

      if (n > 2) {
        n = 0;
      }

      if (n == size.get()) {
        return callOtherHandler(link, ex, conn);
      }

      switch(n) {
        case 0 :
          s = format.replace("__RARE__", "small");

          break;

        case 1 :
          if (mediumScreenSupported) {
            s = format.replace("__RARE__", "medium");
          }

          break;

        case 2 :
          s = format.replace("__RARE__", "large");

          break;
      }

      if (s != null) {
        try {
          conn = openConnection(new URL(s), mimeType);
          conn.open();

          return conn;
        } catch(HTTPException e) {}
        catch(FileNotFoundException e) {}
        catch(IOException e) {}
      }

      return callOtherHandler(link, ex, conn);
    }

    public iErrorHandler getErrorHandler() {
      return errorHandler;
    }

    protected iURLConnection callOtherHandler(ActionLink link, Exception ex, iURLConnection conn) throws IOException {
      if (errorHandler != null) {
        Action a = errorHandler.handleError(link, ex, conn);

        switch(a) {
          case CHANGE : {
            return errorHandler.getConnectionChange(link, ex, conn);
          }

          case RETRY : {
            try {
              conn.close();
            } catch(Exception e) {}

            conn.open();
          }

          case ERROR_MESSAGE : {
            if (ex instanceof HTTPException) {
              String s = ((HTTPException) ex).getMessageBody();

              if ((s != null) && (s.length() > 0)) {
                throw new ApplicationException(s);
              }
            }

            throw new ApplicationException(ApplicationException.getMessageEx(ex));
          }

          default :
            break;
        }
      }

      if (ex instanceof IOException) {
        throw(IOException) ex;
      }

      throw ApplicationException.runtimeException(ex);
    }

    protected String createScreenRelativeFormatString(URL url, MutableInteger size) {
      String s = JavaURLConnection.toExternalForm(url);

      if (!s.startsWith(applicationRoot)) {
        return null;
      }

      String key   = null;
      int    start = applicationRoot.length();

      if (s.indexOf("/large/", start) == start) {
        key = "/large/";
        size.set(2);
      } else if (s.indexOf("/small/", start) == start) {
        key = "/small/";
        size.set(0);
      } else if ((s.indexOf("/medium/", start) == start)) {
        key = "/medium/";
        size.set(1);
      }

      if (key == null) {
        return null;
      }

      return applicationRoot + "/__RARE__/" + s.substring(start + key.length());
    }

    @Override
    public Exception getExceptionChange(ActionLink link, Exception ex) {
      if (errorHandler == null) {
        return null;
      }

      return errorHandler.getExceptionChange(link, ex);
    }
  }


  public static class SelectionPainter extends PaintBucket {
    boolean lostFocus;

    public SelectionPainter(boolean lf) {
      lostFocus = lf;
      setBackgroundPainter(new UISimpleBackgroundPainter(lostFocus
              ? ColorUtils.getBackground()
              : Platform.getUIDefaults().getColor("Rare.textHighlight")));
    }

    @Override
    public UIColor getBackgroundColor() {
      return lostFocus
             ? ColorUtils.getBackground()
             : Platform.getUIDefaults().getColor("Rare.textHighlight");
    }

    @Override
    public iPlatformBorder getBorder() {
      return null;
    }
  }


  public static class StartupInfo {
    public String       applicationFile;
    public List<String> args;
    public boolean      dumpSDF;
    public boolean      dumpXML;
    public String       infoFile;
    public String       local;

    public StartupInfo(String applicationFile, String local, String infoFile, boolean dumpXML, boolean dumpSDF,
                       List<String> args) {
      this.applicationFile = applicationFile;
      this.local           = local;
      this.infoFile        = infoFile;
      this.dumpXML         = dumpXML;
      this.dumpSDF         = dumpSDF;
      this.args            = args;
    }
  }
}
