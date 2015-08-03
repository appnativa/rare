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

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.DateContext;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iAsyncLoadStatusHandler;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.iDataCollectionHandler;
import com.appnativa.rare.iExceptionHandler;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iResourceFinder;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.iWidgetCustomizer;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iConnectionHandler;
import com.appnativa.rare.net.iMultipartMimeHandler;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.UISound;
import com.appnativa.rare.ui.aFocusedAction;
import com.appnativa.rare.ui.effects.PullBackAnimation;
import com.appnativa.rare.ui.effects.ShakeAnimation;
import com.appnativa.rare.ui.effects.SlideAnimation;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iPrintHandler;
import com.appnativa.rare.ui.iSpeechEnabler;
import com.appnativa.rare.ui.listener.iApplicationListener;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharArray;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.iURLResolver;

import com.google.j2objc.annotations.Weak;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * This class represents an instance of a running application. It acts as a
 * proxy to a running application and also maintains a list of all running
 * instances
 *
 * @author Don DeCoteau
 */
public abstract class aAppContext implements iPlatformAppContext {
  protected volatile static aAppContext  _instance;
  @Weak
  protected static iPlatformComponent    focusOwner;
  @Weak
  protected static iPlatformComponent    permanentFocusOwner;
  protected final Map<String, String>    appStrings    = new HashMap<String, String>();
  protected Map<String, UIImageIcon>     appIcons      = new HashMap<String, UIImageIcon>();
  protected IdentityArrayList            activeWindows = new IdentityArrayList();
  protected aRare                        RARE;
  protected iAsyncLoadStatusHandler      asyncLoadStatusHandler;
  protected UIImageIcon                  brokenIcon;
  protected List<iConfigurationListener> configListeners;
  protected boolean                      landscapeMode;
  protected iPlatformComponentPainter    lfselectionComponentPainter;
  protected Runnable                     lowMemoryWarningHandler;
  protected volatile boolean             managedResourcePathInitialized;
  protected String                       managedResourcePaths[];
  protected boolean                      orientationLocked;
  protected String[]                     rareIconResourcePaths;
  protected boolean                      resetOnConfigurationChange;
  protected iResourceFinder              resourceFinder;
  protected iSpeechEnabler               speechEnabler;
  protected final UIProperties           uiDefaults;
  protected iWidgetCustomizer            widgetCustomizer;
  private boolean                        isPlatformTheme = true;

  /**
   * Creates a new application context
   *
   * @param instance
   *          the application instance
   */
  public aAppContext(aRare instance, UIProperties defaults) {
    this.RARE       = instance;
    this.uiDefaults = defaults;
    _instance       = this;
  }

  @Override
  public void addApplicationListener(iApplicationListener listener) {
    if (RARE.appListeners == null) {
      RARE.appListeners = Collections.synchronizedList(new IdentityArrayList<iApplicationListener>());
    }

    RARE.appListeners.add(listener);
  }

  @Override
  public void addConfigurationListener(iConfigurationListener listener) {
    if (configListeners == null) {
      configListeners = Collections.synchronizedList(new IdentityArrayList<iConfigurationListener>());
    }

    configListeners.add(listener);
  }

  @Override
  public void addJarURL(URL uRL) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addURLPrefixMapping(String prefix, String mapping) {
    RARE.addPrefixMapping(prefix, mapping);
  }

  @Override
  public synchronized void addResourceString(String name, String value) {
    appStrings.put(name, value);
  }

  @Override
  public boolean alwaysUseHeavyTargets() {
    return RARE.useHeavyTargets;
  }

  @Override
  public boolean areAllLabelsDraggable() {
    return RARE.allLabelsDraggable;
  }

  @Override
  public boolean areAllTextFieldsDroppable() {
    return RARE.allTextFieldsDroppable;
  }

  @Override
  public boolean areAllWidgetsDraggable() {
    return false;
  }

  @Override
  public boolean areViewersLocalByDefault() {
    return RARE.viewersLocalByDefault;
  }

  @Override
  public void clearData() {
    RARE.appData.clear();
  }

  @Override
  public void clearData(String prefix) {
    Iterator it = RARE.appData.keySet().iterator();

    while(it.hasNext()) {
      Object o = it.next();

      if ((o instanceof String) && ((String) o).startsWith(prefix)) {
        it.remove();
      }
    }
  }

  @Override
  public iDataCollection createCollection(String handler, String name, ActionLink link, Map attributes,
          boolean tabular, iFunctionCallback cb) {
    iDataCollection dc;

    if ((handler == null) || (handler.length() == 0)) {
      dc = DataItemParserHandler.createCollection(this, name, link, attributes, tabular, cb);
    } else {
      dc = RARE.getCollectionHandler(handler).createCollection(this, name, link, attributes, cb);
    }

    registerDataCollection(dc);

    return dc;
  }

  @Override
  public URL createURL(iWidget context, String url) throws MalformedURLException {
    return RARE.createURL(context, url);
  }

  @Override
  public URL createURL(URL context, String url) throws MalformedURLException {
    return RARE.createURL(this, context, url);
  }

  @Override
  public void dispose() {
    if (_instance == this) {
      _instance = null;
    }

    RARE = null;

    if (appIcons != null) {
      appIcons.clear();
    }

    uiDefaults.clear();
    appStrings.clear();
    focusOwner                  = null;
    permanentFocusOwner         = null;
    ;
    appIcons                    = null;
    activeWindows               = null;
    asyncLoadStatusHandler      = null;
    brokenIcon                  = null;
    configListeners             = null;
    lfselectionComponentPainter = null;
    rareIconResourcePaths       = null;
    resourceFinder              = null;
    speechEnabler               = null;
    widgetCustomizer            = null;
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Callable callable) {
    return RARE.executeBackgroundTask(callable);
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Runnable runnable) {
    return RARE.executeBackgroundTask(runnable);
  }

  @Override
  public iCancelableFuture executeWorkerTask(iWorkerTask task) {
    return RARE.executeSwingWorkerTask(task);
  }

  @Override
  public void exit() {
    RARE.exit();
  }

  public void handleConfigurationChanged(Object changes) {
    if (RARE.windowManager == null) {
      return;
    }

    landscapeMode = PlatformHelper.isLandscapeOrientation(null);

    if (configListeners != null) {
      for (iConfigurationListener l : configListeners) {
        l.onConfigurationChanged(changes);
      }
    }

    RARE.windowManager.onConfigurationChanged(resetOnConfigurationChange);
  }

  public void handleConfigurationWillChange(Object newConfig) {
    closePopupWindows(true);

    if (configListeners != null) {
      for (iConfigurationListener l : configListeners) {
        l.onConfigurationWillChange(newConfig);
      }
    }

    if (RARE.windowManager != null) {
      RARE.windowManager.onConfigurationWillChange(newConfig);
    }
  }

  @Override
  public boolean ignoreFormatExceptions() {
    return RARE.ignoreFormatExceptions();
  }

  @Override
  public String loadScriptCode(ActionLink link, boolean runOnce) {
    return RARE.loadScriptCode(link, runOnce);
  }

  @Override
  public boolean okForOS(iSPOTElement e) {
    return (RARE == null)
           ? true
           : RARE.okForOS(e.spot_getAttribute("os"), null);
  }

  public boolean okForOS(String os) {
    return (RARE == null)
           ? true
           : RARE.okForOS(os, null);
  }

  @Override
  public iURLConnection openConnection(URL url) throws IOException {
    return RARE.openConnection(url);
  }

  @Override
  public iURLConnection openConnection(URL url, String mimeType) throws IOException {
    return RARE.openConnection(url, mimeType);
  }

  public iConnectionHandler getApplicationConnectionHandler() {
    return RARE.applicationConnectionHandler;
  }

  public void setApplicationConnectionHandler(iConnectionHandler h) {
    RARE.applicationConnectionHandler = h;
  }

  @Override
  public final Object putData(Object key, Object value) {
    if (value == null) {
      return RARE.appData.remove(key);
    }

    return RARE.appData.put(key, value);
  }

  @Override
  public final void putData(Map data, boolean clearFirst) {
    if (clearFirst) {
      RARE.appData.clear();
    }

    if (data != null) {
      RARE.appData.putAll(data);
    }
  }

  @Override
  public void registerCollectionHandler(String name, iDataCollectionHandler ch) {
    RARE.registerCollectionHandler(name, ch);
  }

  @Override
  public void registerDataCollection(iDataCollection dc) {
    RARE.registerDataCollection(dc);
  }

  @Override
  public void registerFocusedAction(aFocusedAction action) {
    RARE.registerFocusedAction(action);
  }

  /**
   * Registers the class for a specific mime type
   *
   * @param type
   *          the mime type or fully qualified configuration object class name
   * @param cls
   *          the class that implements the widget functionality
   */
  @Override
  public void registerWidgetClass(String type, Class cls) {
    RARE.registerWidgetClass(type, cls);
  }

  @Override
  public void removeApplicationListener(iApplicationListener listener) {
    if (RARE.appListeners != null) {
      RARE.appListeners.remove(listener);
    }
  }

  @Override
  public void removeConfigurationListener(iConfigurationListener listener) {
    if (configListeners != null) {
      configListeners.remove(listener);
    }
  }

  @Override
  public Object removeData(Object key) {
    return RARE.appData.remove(key);
  }

  @Override
  public void resetRunOnce(ActionLink link) {
    RARE.resetRunOnce(link);
  }

  @Override
  public String rewriteURL(iWidget widget, URL url, Object code) {
    return JavaURLConnection.toExternalForm(url);
  }

  @Override
  public void unregisterDataCollection(iDataCollection dc) {
    RARE.unregisterDataCollection(dc);
  }

  @Override
  public void setAsyncLoadStatusHandler(iAsyncLoadStatusHandler handler) {
    this.asyncLoadStatusHandler = handler;
  }

  @Override
  public void setContextURL(URL url) {
    RARE.setContextURL(url);
  }

  @Override
  public void setContextURL(URL url, String appRoot) {
    RARE.setContextURL(url);

    if ((appRoot != null) && appRoot.equals("/")) {
      appRoot = "";
    }

    RARE.applicationRoot = appRoot;
  }

  @Override
  public void setDefaultExceptionHandler(iExceptionHandler eh) {
    RARE.setDefaultExceptionHandler(eh);
  }

  @Override
  public void setLowMemoryWarningHandler(Runnable runnable) {
    this.lowMemoryWarningHandler = runnable;
  }

  @Override
  public void setMultipartMimeHandler(iMultipartMimeHandler multipartMimeHandler) {
    RARE.multipartMimeHandler = multipartMimeHandler;
  }

  @Override
  public void setResourceFinder(iResourceFinder rf) {
    resourceFinder = rf;
  }

  @Override
  public void setSpeechEnabler(iSpeechEnabler speechEnabler) {
    this.speechEnabler = speechEnabler;
  }

  public void setThemeColors(UIColor fg, UIColor bg, boolean isdefault) {
    uiDefaults.put("Rare.foreground", fg);
    uiDefaults.put("Rare.textText", fg);

    boolean dark = bg.isDarkColor();

    if (dark) {
      uiDefaults.put("Rare.disabledForeground", fg.light(-75));
      uiDefaults.put("Rare.TitledBorder.titleColor", fg.light(-75));
    } else {
      uiDefaults.put("Rare.TitledBorder.titleColor", fg.light(75));
      uiDefaults.put("Rare.disabledForeground", fg.light(75));
    }

    int n = bg.getRed() + bg.getGreen() + bg.getBlue();

    if (n < 9) {
      bg = new UIColor(0x03, 0x03, 0x03);
    } else if (n > 715) {
      bg = new UIColor(0xee, 0xee, 0xee);
    }

    uiDefaults.put("Rare.background", bg);
    uiDefaults.put("Rare.textHighlightText", fg);
    uiDefaults.put("Rare.textHighlight", bg);

    UIColor c = new UIColorShade(bg, "Rare.background");

    uiDefaults.put("controlLtGradient", new UIColorShade(c, (255 * 15) / 100));
    uiDefaults.put("controlDkGradient", new UIColorShade(c, (255 * -15) / 100));
    uiDefaults.put("controlShadow", new UIColorShade(c, (255 * -25) / 100));
    uiDefaults.put("controlDkShadow", new UIColorShade(c, (255 * -30) / 100));
    uiDefaults.put("controlLtShadow", new UIColorShade(c, (255 * -15) / 100));
    uiDefaults.put("controlLtHighlight", new UIColorShade(c, (255 * 30) / 100));
    uiDefaults.put("controlHighlight", new UIColorShade(c, (255 * -10) / 100));
    uiDefaults.put("Rare.backgroundLtGradient", uiDefaults.get("controlLtGradient"));
    uiDefaults.put("Rare.backgroundDkGradient", uiDefaults.get("controlDkGradient"));
    uiDefaults.put("Rare.backgroundShadow", uiDefaults.get("controlShadow"));
    uiDefaults.put("Rare.backgroundDkShadow", uiDefaults.get("controlDkShadow"));
    uiDefaults.put("Rare.backgroundLtShadow", uiDefaults.get("controlLtShadow"));
    uiDefaults.put("Rare.backgroundLtHighlight", uiDefaults.get("controlLtHighlight"));
    uiDefaults.put("Rare.backgroundHighlight", uiDefaults.get("controlHighlight"));
    uiDefaults.put("Rare.List.dividerLineColor", new UIColor(224, 224, 224));
    // This method can be called multiple times so clear out previously set
    // icons
    appIcons.remove("Rare.icon.close");
    appIcons.remove("Rare.icon.menu");
    appIcons.remove("Rare.icon.backArrow");
    appIcons.remove("Rare.icon.more");

    if (!fg.isDarkColor()) {
      UIImageIcon icon = getResourceAsIcon("Rare.icon.close_light");

      appIcons.put("Rare.icon.close", icon);
      icon = getResourceAsIcon("Rare.icon.menu_light");
      appIcons.put("Rare.icon.menu", icon);
      icon = getResourceAsIcon("Rare.icon.backArrow_light");
      appIcons.put("Rare.icon.backArrow", icon);
      icon = getResourceAsIcon("Rare.icon.backArrow_light");
      appIcons.put("Rare.icon.backArrow", icon);
      icon = getResourceAsIcon("Rare.icon.more_light");
      appIcons.put("Rare.icon.more", icon);
    }

    appIcons.put("Rare.icon.cancel", getResourceAsIcon("Rare.icon.close"));
    uiDefaults.put("Rare.List.foreground", UIColor.BLACK);
    uiDefaults.put("Rare.List.background", UIColor.WHITE);
    uiDefaults.put("Rare.List.disabledForeground", UIColor.GRAY);
    ColorUtils.setBaseColors();
    isPlatformTheme = isdefault;
  }

  @Override
  public void setURLUserInfo(Map mappings) {
    if (RARE.userInfo != null) {
      RARE.userInfo.clear();
    } else {
      RARE.userInfo = new LinkedHashMap();
    }

    if (mappings != null) {
      RARE.userInfo.putAll(mappings);
    }
  }

  @Override
  public void setURLUserInfo(URL path, String info) {
    RARE.setURLUserInfo(path, info);
  }

  @Override
  public void setWidgetCustomizer(iWidgetCustomizer customizer) {
    widgetCustomizer = customizer;
  }

  @Override
  public UIAction getAction(String name) {
    return RARE.getAction(name);
  }

  @Override
  public Map<String, UIAction> getActions() {
    return RARE.actionMap;
  }

  @Override
  public IdentityArrayList getActiveWindows() {
    return activeWindows;
  }

  public static iPlatformAppContext getAppContext(int identity) {
    return _instance;
  }

  @Override
  public String getApplicationName() {
    return RARE.getName();
  }

  public String getApplicationRelativeLocation(URL url) {
    return RARE.getRelativeLocation(url);
  }

  @Override
  public URL getApplicationURL() {
    return RARE.getApplicationURL();
  }

  @Override
  public iAsyncLoadStatusHandler getAsyncLoadStatusHandler() {
    return (asyncLoadStatusHandler == null)
           ? RARE
           : asyncLoadStatusHandler;
  }

  @Override
  public PaintBucket getAutoHilightPainter() {
    return RARE.autoHilightPainter;
  }

  @Override
  public boolean getAutoLocalizeDateFormats() {
    return RARE.autoLocalizeDateFormats;
  }

  @Override
  public boolean getAutoLocalizeNumberFormats() {
    return RARE.autoLocalizeNumberFormats;
  }

  @Override
  public URL getCodeBase() {
    return getDocumentBase();
  }

  @Override
  public String getContentAsString(URL url) throws IOException {
    return RARE.openConnection(url).getContentAsString();
  }

  @Override
  public URL getContextURL() {
    return RARE.contextURL;
  }

  @Override
  public String getCustomPropertyPrefix() {
    return RARE.customPropertyPrefix;
  }

  @Override
  public final Object getData(Object key) {
    if (RARE.appData == null) {
      return null;
    }

    return RARE.appData.get(key);
  }

  @Override
  public iDataCollection getDataCollection(String name) {
    iDataCollection dc = null;

    if (RARE.dataCollections != null) {
      dc = RARE.dataCollections.get(name);
    }

    return dc;
  }

  @Override
  public iDataConverter getDataConverter(Class cls) {
    return aRare.getDataConverter(cls);
  }

  @Override
  public Class getDataConverterClass(String name) throws ClassNotFoundException {
    return aRare.getDataConverterClass(name);
  }

  @Override
  public DateContext getDefaultDateContext() {
    return RARE.defaultDateContext;
  }

  @Override
  public DateContext getDefaultDateTimeContext() {
    return RARE.defaultDateTimeContext;
  }

  @Override
  public iExceptionHandler getDefaultExceptionHandler() {
    return RARE.getDefaultExceptionHandler();
  }

  /**
   * Gets the default exception handler for the environment
   *
   * @param w
   *          the widget to use to retrieve an application context (can be null)
   *
   * @return the default exception handler for the environment
   */
  public static iExceptionHandler getDefaultExceptionHandler(iWidget w) {
    return _instance.getDefaultExceptionHandler();
  }

  @Override
  public final String getDefaultScrptingLanguage() {
    return RARE.defaultScriptingLanguage;
  }

  @Override
  public DateContext getDefaultTimeContext() {
    return RARE.defaultTimeContext;
  }

  @Override
  public iURLResolver getDefaultURLResolver() {
    return (iURLResolver) RARE.getRootViewer();
  }

  @Override
  public URL getDocumentBase() {
    URL u = this.getContextURL();

    if (u != null) {
      try {
        u = new URL(JavaURLConnection.parenToExternalForm(u));
      } catch(Exception ignore) {}
    }

    return u;
  }

  @Override
  public iPlatformComponent getFocusOwner() {
    if ((focusOwner != null) &&!focusOwner.isDisplayable()) {
      focusOwner = null;
    }

    return focusOwner;
  }

  @Override
  public int getIdentityInt() {
    return System.identityHashCode(this);
  }

  @Override
  public InputStream getInputStream(URL url) throws IOException {
    return RARE.openConnection(url).getInputStream();
  }

  public static int getInstanceCount() {
    return 1;
  }

  @Override
  public int getItemPaddingHeight() {
    return RARE.itemPaddingHeight;
  }

  @Override
  public PaintBucket getListItemFocusPainter() {
    return RARE.listItemFocusPainter;
  }

  public iPlatformComponentPainter getLostFocusSelectionComponentPainter() {
    if (lfselectionComponentPainter == null) {
      lfselectionComponentPainter = getSelectionPainter().getComponentPainter(true);
    }

    return lfselectionComponentPainter;
  }

  @Override
  public PaintBucket getLostFocusSelectionPainter() {
    return RARE.lostFocusSelectionPainter;
  }

  public WindowViewer getMainWindow() {
    iPlatformWindowManager wm = RARE.getWindowManager();

    return (wm == null)
           ? null
           : (WindowViewer) wm.getRootViewer();
  }

  public UIImage getManagedResource(String name) {
    return getManagedResource(name, landscapeMode);
  }

  @SuppressWarnings("resource")
  public UIImage getManagedResource(String name, boolean landscape) {
    if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".gif")) {
      return PlatformHelper.getImageFromResourceFileName(name);
    }

    UIImage   img = null;
    String    path[];
    CharArray ca = new CharArray();

    if (name.startsWith("Rare.icon.")) {
      path = rareIconResourcePaths;
    } else {
      if (!managedResourcePathInitialized) {
        initializeManagedResourcePaths();
      }

      path = managedResourcePaths;
    }

    ca.set(name);

    if (name.indexOf('.') != -1) {
      ca.toLowerCase().replace('.', '_');
    }

    String cname = ca.toString();

    for (String s : path) {
      if (landscape && s.contains("-port")) {
        continue;
      }

      if (!landscape && s.contains("-land")) {
        continue;
      }

      ca.set(s);
      ca.append('/').append(cname);

      String rname = ca.toString();
      URL    url   = checkForFile(rname);

      if (url != null) {
        float fd = 1;

        if (s.contains("-xhdpi")) {
          fd = 2;
        }

        try {
          img = PlatformHelper.createImage(url, false, fd);
          img.setResourceName(name);

          return img;
        } catch(IOException e) {
          return null;
        }
      }
    }

    return null;
  }

  @Override
  public iMultipartMimeHandler getMultipartMimeHandler() {
    if (RARE.multipartMimeHandler == null) {
      String cls = getUIDefaults().getString("aRare.multipartMimeHandlerClassName");

      if (cls == null) {
        cls = "com.appnativa.rare.net.MultipartMimeHandler";
      }

      try {
        RARE.multipartMimeHandler = (iMultipartMimeHandler) PlatformHelper.loadClass(cls).newInstance();
      } catch(Throwable e) {
        throw new ApplicationException(e);
      }
    }

    return RARE.multipartMimeHandler;
  }

  @Override
  public String getName() {
    return RARE.getName();
  }

  @Override
  public iPlatformComponent getPermanentFocusOwner() {
    if ((permanentFocusOwner != null) &&!permanentFocusOwner.isDisplayable()) {
      permanentFocusOwner = null;
    }

    return permanentFocusOwner;
  }

  @Override
  public PaintBucket getPressedPainter() {
    return RARE.pressedPainter;
  }

  @Override
  public iPrintHandler getPrintHandler() {
    return null;
  }

  @Override
  public Reader getReader(URL url) throws IOException {
    return RARE.openConnection(url).getReader();
  }

  @Override
  public Reader getReader(URLConnection conn) throws IOException {
    return aRare.getReader(conn);
  }

  public UISound getSound(String sound) throws Exception {
    if (sound.startsWith("Rare.")) {
      sound = sound.toLowerCase(Locale.US);
      sound = sound.replace('.', '_');

      return PlatformHelper.getSoundResource(sound);
    } else {
      return PlatformHelper.getSound(getResourceURL(sound));
    }
  }

  @Override
  public iPlatformAnimator getResourceAsAnimator(String animator) {
    if (animator.contains("Rare.anim.")) {
      if ("Rare.anim.slideInFromLeft".equals(animator)) {
        return new SlideAnimation(true, true);
      } else if ("Rare.anim.slideInFromRight".equalsIgnoreCase(animator)) {
        return new SlideAnimation(true, false);
      } else if ("Rare.anim.slideDownFromTop".equalsIgnoreCase(animator)) {
        return new SlideAnimation(false, true);
      } else if ("Rare.anim.slideUpFromBottom".equalsIgnoreCase(animator)) {
        return new SlideAnimation(false, true);
      } else if ("Rare.anim.pullBackLeft".equalsIgnoreCase(animator)) {
        return new PullBackAnimation(true, true);
      } else if ("Rare.anim.pullBackRight".equalsIgnoreCase(animator)) {
        return new PullBackAnimation(true, false);
      } else if ("Rare.anim.pullBackTop".equalsIgnoreCase(animator)) {
        return new PullBackAnimation(false, true);
      } else if ("Rare.anim.pullBackButtom".equalsIgnoreCase(animator)) {
        return new PullBackAnimation(false, false);
      } else if ("Rare.anim.shake".equalsIgnoreCase(animator)) {
        return new ShakeAnimation();
      } else {
        return null;
      }
    } else {
      return getResourceAsAnimatorEx(animator);
    }
  }

  @Override
  public UIImageIcon getResourceAsIcon(String name) {
    UIImageIcon icon = getResourceAsIconEx(name);

    if (icon == null) {
      Platform.debugLog("missing image resource:" + name);

      if (brokenIcon == null) {
        brokenIcon = new UIImageIcon(UIImageIcon.getBrokenImage());
      }

      icon = brokenIcon;
    }

    return icon;
  }

  @Override
  public UIImageIcon getResourceAsIconEx(String name) {
    UIImageIcon icon = null;

    if (resourceFinder != null) {
      icon = resourceFinder.getResourceAsIcon(name);

      if (icon != null) {
        return icon;
      }
    }

    if (appIcons != null) {
      icon = appIcons.get(name);

      if (icon != null) {
        return icon;
      }
    }

    icon = uiDefaults.getImageIcon(name);

    if (icon != null) {
      return icon;
    }

    UIImage img = getManagedResource(name);

    if (img != null) {
      UIImageIcon ic = new UIImageIcon(img, "", img.getLocation());

      ic.setResourceName(name);

      return ic;
    }

    return null;
  }

  @Override
  public iResourceFinder getResourceFinder() {
    return resourceFinder;
  }

  @Override
  public Map<String, UIImageIcon> getResourceIcons() {
    return appIcons;
  }

  @Override
  public Map<String, String> getResourceStrings() {
    return appStrings;
  }

  @Override
  public iContainer getRootViewer() {
    return RARE.getRootViewer();
  }

  @Override
  public String getScriptType(ActionLink link) {
    return RARE.getScriptType(link);
  }

  @Override
  public iScriptHandler getScriptingManager() {
    return RARE.scriptHandler;
  }

  @Override
  public PaintBucket getSelectionPainter() {
    return RARE.selectionPainter;
  }

  @Override
  public iSpeechEnabler getSpeechEnabler() {
    return speechEnabler;
  }

  @Override
  public UIProperties getUIDefaults() {
    return uiDefaults;
  }

  @Override
  public URL getURL(String url) throws MalformedURLException {
    return RARE.getURL(url);
  }

  @Override
  public iViewer getViewer(String name) {
    return RARE.getViewer(name);
  }

  @Override
  public iWidgetCustomizer getWidgetCustomizer() {
    return widgetCustomizer;
  }

  @Override
  public PaintBucket getWidgetFocusPainter() {
    return RARE.widgetFocusPainter;
  }

  @Override
  public iWidget getWidgetFromPath(String path) {
    return RARE.getRootViewer().getWidgetFromPath(path);
  }

  @Override
  public Class getWidgetHandler(String mimeType) {
    return RARE.getWidgetHandler(mimeType);
  }

  @Override
  public iPlatformWindowManager getWindowManager() {
    return RARE.getWindowManager();
  }

  @Override
  public WindowViewer getTopWindowViewer() {
    iPlatformWindowManager wm = (RARE == null)
                                ? null
                                : RARE.getWindowManager();

    return (wm == null)
           ? null
           : (WindowViewer) wm.getViewer();
  }

  @Override
  public boolean isChangeSelColorOnLostFocus() {
    return false;
  }

  @Override
  public boolean isDebugEnabled() {
    return aRare.isDebugEnabled();
  }

  @Override
  public boolean isDefaultBackgroundDark() {
    return ColorUtils.getBackground().isDarkColor();
  }

  @Override
  public boolean isDisposed() {
    return RARE == null;
  }

  public boolean isDesignContext() {
    return false;
  }

  @Override
  public boolean isDynamicNameLookupEnabled() {
    return RARE.dynamicNameLookup;
  }

  @Override
  public boolean isInSandbox() {
    return false;
  }

  @Override
  public boolean isOrientationLocked() {
    return orientationLocked;
  }

  @Override
  public boolean isOverlapAutoToolTips() {
    return RARE.overlapAutoToolTips;
  }

  @Override
  public boolean isPlatformColorTheme() {
    return isPlatformTheme;
  }

  @Override
  public boolean isShuttingDown() {
    return (RARE == null)
           ? true
           : RARE.shuttingDown;
  }

  @Override
  public boolean isWebContext() {
    return false;
  }

  @Override
  public boolean isWebStart() {
    return false;
  }

  protected URL checkForFile(String name) {
    URL file = null;

    file = makeResourceURL(name, "png");

    if (file == null) {
      file = makeResourceURL(name, "9.png");
    }

    if (file == null) {
      file = makeResourceURL(name, "jpg");
    }

    if (file == null) {
      file = makeResourceURL(name, "gif");
    }

    return file;
  }

  protected boolean isLowIconDensity() {
    return ScreenUtils.isLowDensity();
  }

  synchronized protected void initializeManagedResourcePaths() {
    if (managedResourcePathInitialized) {
      return;
    }

    managedResourcePathInitialized = true;

    String path = getUIDefaults().getString("Rare.Resources.path");

    if (path == null) {
      path = getDefaultManagedResourcePath();
    }

    if ((path == null) || path.equals("/")) {
      path = "";
    }

    ArrayList<String> paths = new ArrayList<String>();
    String            lang  = Locale.getDefault().getLanguage();

    if (lang != null & lang.length() == 0) {
      lang = null;
    }

    CharArray ca = new CharArray();

    ca.set(path).append("/drawable");

    if (isLowIconDensity()) {
      addDrawablePath(paths, ca, lang, false, true);
      addDrawablePath(paths, ca, lang, false, false);
    } else {
      addDrawablePath(paths, ca, lang, ScreenUtils.isHighDensity(), false);
      addDrawablePath(paths, ca, lang, false, true);
    }

    int len = paths.size();

    for (int i = len - 1; i >= 0; i--) {
      String s = paths.get(i);

      if (!hasResourceDirectory(s)) {
        paths.remove(i);
      }
    }

    managedResourcePaths = paths.toArray(new String[paths.size()]);
  }

  protected abstract URL makeResourceURL(String name, String extension);

  protected void setupUIDefaults(UIColor fg, UIColor bg) {
    if (fg == null) {
      fg = UIColor.BLACK;
    }

    if (bg == null) {
      bg = new UIColor(0xee, 0xee, 0xee);
    }

    setThemeColors(fg, bg, true);

    UIFont f = FontUtils.getDefaultFont();

    uiDefaults.put("Rare.font.default", f);
    uiDefaults.put("Rare.font.plaintext", FontUtils.getMonospacedFont(f.getSize() - 1));
    uiDefaults.put("Rare.TabPane.tabAreaMargin", new UIInsets(2));
    appIcons.put("Rare.icon.empty", getResourceAsIcon("Rare.icon.empty"));
    UIImageIcon.getBrokenImage();
    appStrings.put("Rare.Alert.errorTitle", "Application Error");
  }

  protected void updateActions(iPlatformComponent focusedComponent) {
    if (RARE.manageFocusedActions) {
      ArrayList<iWeakReference> list = RARE.focusedActions;

      if (list != null) {
        int len = list.size();

        for (int i = len - 1; i > -1; i--) {
          aFocusedAction action = (aFocusedAction) list.get(i).get();

          if (action == null) {
            list.remove(i);
          } else {
            action.update(focusedComponent);
          }
        }
      }
    }
  }

  protected String getDefaultManagedResourcePath() {
    return null;
  }

  protected float getDensityForImageFile(String file) {
    return file.contains("-hdpi")
           ? 2
           : 1;
  }

  protected abstract iPlatformAnimator getResourceAsAnimatorEx(String animation);

  protected boolean hasResourceDirectory(String path) {
    if (path.startsWith("file://")) {
      try {
        File f = new File(path.substring(6));

        return f.exists();
      } catch(Exception ignore) {
        return false;
      }
    }

    return getResourceURL(path) != null;
  }

  private void addDrawablePath(ArrayList<String> list, CharArray ca, String lang, boolean highFirst, boolean low) {
    int     dlen          = ca._length;
    int     len           = low
                            ? 2
                            : 5;
    boolean doOrientation = true;
    boolean portFirst     = true;

    for (int i = 0; i < len; i++) {
      if (doOrientation) {
        ca.append(portFirst
                  ? "-port"
                  : "-land");
      }

      if (!low) {
        ca.append(highFirst
                  ? "-xhdpi"
                  : "-mdpi");
      } else {
        portFirst = !portFirst;
      }

      if (lang != null) {
        ca.append('-').append(lang);
      }

      list.add(ca.toString());
      ca._length = dlen;

      if (doOrientation) {
        ca.append(portFirst
                  ? "-port"
                  : "-land");
      }

      if (!low) {
        ca.append(highFirst
                  ? "-mdpi"
                  : "-xhdpi");
      }

      if (lang != null) {
        ca.append('-').append(lang);
      }

      list.add(ca.toString());
      ca._length = dlen;

      if ((i == 0) && low) {
        lang = null;
      }

      if (i == 1) {
        lang = null;
      }

      portFirst = !portFirst;

      if (i == 3) {
        doOrientation = false;
      }
    }

    if (low) {
      ca._length = dlen;
      list.add(ca.toString());
    }
  }
}
