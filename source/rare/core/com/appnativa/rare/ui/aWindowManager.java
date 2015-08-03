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

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.AbortOperationException;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.exception.InvalidConfigurationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.HTTPException;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.MainWindow;
import com.appnativa.rare.spot.MenuBar;
import com.appnativa.rare.spot.StatusBar;
import com.appnativa.rare.spot.ToolBar;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.rare.viewer.ToolBarViewer;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iTarget;
import com.appnativa.rare.viewer.iViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTException;
import com.appnativa.spot.SPOTSet;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.ObjectHolder;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Don DeCoteau
 */
public abstract class aWindowManager implements iPlatformWindowManager {
  private static String BASE_VIEWER = Platform.RARE_SPOT_PACKAGE_NAME + ".Viewer";

  /** weak hash map for viewers */
  protected HashMap<String, iWeakReference> activeViewers = new HashMap<String, iWeakReference>();

  /** weak hash map for targets */
  protected HashMap<String, iTarget> theTargets = new HashMap<String, iTarget>();

  /** the application instance */
  protected iPlatformAppContext appContext;

  /** the component creator */
  protected iPlatformComponentFactory componentCreator;

  /** context URL */
  protected URL              contextURL;
  protected UIFont           defaultFont;
  protected volatile boolean disposed;
  protected iWindow          mainFrame;
  protected iPlatformMenuBar menuBar;
  protected iScriptHandler   scriptHandler;

  /** widget listener for the main window */
  protected aWidgetListener widgetListener;
  protected iTarget         workspaceTarget;
  private Runnable          aboutDialogRunner;
  private List<UIImageIcon> windowIcons;

  public aWindowManager(iPlatformAppContext app) {
    this.appContext = app;
    contextURL      = app.getContextURL();

    String s = Platform.getUIDefaults().getString("Rare.runtime.componentCreator");

    if (s != null) {
      componentCreator = (iPlatformComponentFactory) Platform.createObject(s);
      componentCreator.setAppContext(app);
    }
  }

  @Override
  public iViewer activateViewer(ActionLink link) {
    return activateViewer(null, link);
  }

  @Override
  public iViewer activateViewer(iWidget context, ActionLink link) {
    if (context == null) {
      context = link.getContext();
    }

    iViewer v = createViewer(context, link);

    if (v != null) {
      String  target = link.getTargetName();
      iViewer ov     = setViewer(target, context, v, link.getWindowOptions());

      if ((ov != null) && ov.isAutoDispose()) {
        ov.dispose();
      }
    }

    return v;
  }

  @Override
  public iViewer activateViewer(iWidget context, Viewer cfg, String target) {
    iViewer v = createViewer(context, cfg, null);

    if (v != null) {
      setViewer(target, context, v, null);
    }

    return v;
  }

  @Override
  public void addWindowListener(iWindowListener l) {
    mainFrame.addWindowListener(l);
  }

  @Override
  public void asyncActivateViewer(ActionLink link) {
    asyncActivateViewer(null, link);
  }

  @Override
  public void asyncActivateViewer(iWidget context, ActionLink link) {
    if (context == null) {
      context = link.getContext();
    }

    if (context == null) {
      context = appContext.getRootViewer();
    }

    try {
      ViewerCreator.createViewer(context, link, link.getTargetName());
    } catch(MalformedURLException e) {
      throw new ApplicationException(e);
    }
  }

  @Override
  public void center() {
    mainFrame.center();
  }

  @Override
  public void changeTargetName(String oldname, String newname, iTarget target) {
    if (theTargets.get(newname) != null) {
      throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.targetExists"), newname);
    }

    iTarget t = theTargets.remove(oldname);

    if (t != target) {
      theTargets.put(oldname, t);
    }

    theTargets.put(newname, target);
  }

  @Override
  public void clearTarget(String target) {
    iTarget t = getTarget(target);

    if (t != null) {
      iViewer v = t.removeViewer();

      if ((v != null) && v.isAutoDispose()) {
        v.dispose();
      }
    }
  }

  @Override
  public void close() {
    if (mainFrame != null) {
      mainFrame.close();
    }
  }

  public void configureStandardStuff(MainWindow cfg) {
    if (widgetListener.isEnabled(iConstants.EVENT_OPENED) || widgetListener.isEnabled(iConstants.EVENT_WILL_CLOSE)
        || widgetListener.isEnabled(iConstants.EVENT_CLOSED)) {
      mainFrame.addWindowListener(widgetListener);
    }

    String s = cfg.windowListenerClass.getValue();

    if ((s != null) &&!((aAppContext) appContext).isDesignContext()) {
      s = s.trim();

      if (s.length() > 0) {
        try {
          Class cls = PlatformHelper.loadClass(s);

          mainFrame.addWindowListener((iWindowListener) cls.newInstance());
        } catch(Exception e) {
          throw new ApplicationException(e);
        }
      }
    }

    if (cfg.bgColor.spot_hasValue()) {
      ColorUtils.configureBackgroundPainter(getWindowViewer(), cfg.bgColor);
    } else if (cfg.decorated.booleanValue()) {
      mainFrame.getComponent().setBackground(ColorUtils.getBackground());
    }

    aContainer context = (aContainer) appContext.getRootViewer();

    if (cfg.getBorders() != null) {
      iPlatformBorder b = BorderUtils.createBorder(context, cfg.getBorders(), null);

      if (b != null) {
        mainFrame.getComponent().setBorder(b);
      }
    }

    UIImageIcon icon;

    s = cfg.icon.getValue();

    if ((s != null) && (s.indexOf(',') != -1)) {
      List<String>      l      = CharScanner.getTokens(s, ',', true);
      List<UIImageIcon> images = new ArrayList<UIImageIcon>(l.size());

      for (String ic : l) {
        icon = (UIImageIcon) context.getIcon(ic);

        if (icon != null) {
          images.add(icon);

          if (!supportsMultipleWindowIcons()) {
            break;
          }
        }
      }

      if (images.size() > 0) {
        setWindowIcons(images);
      }
    } else if ((s != null) && (s.length() > 0)) {
      icon = (UIImageIcon) context.getIcon(s);

      if (icon != null) {
        List<UIImageIcon> images = new ArrayList<UIImageIcon>(1);

        images.add(icon);
        setWindowIcons(images);
      }
    }

    if (cfg.customProperties.getValue() != null) {
      handleCustomProperties(cfg, DataParser.parseNameValuePairs(cfg.customProperties));
    }

    UIFont f = UIFontHelper.getFont((UIFont) null, cfg.font);

    if (f == null) {
      f = Platform.getUIDefaults().getFont("Rare.font.default");
    }

    if ((f != null) && (f != UIFontHelper.getDefaultFont())) {
      UIFontHelper.setDefaultFont(f);
    }

    s = cfg.title.getValue();

    if ((s == null) || (s.length() == 0)) {
      s = "My Application";
    } else {
      s = context.expandString(s, false);
    }

    mainFrame.setTitle(s);
    contextURL = appContext.getContextURL();

    if (cfg.getMenuBar() != null) {
      MenuBar mb = cfg.getMenuBar();

      if (Platform.getAppContext().okForOS(mb)) {
        menuBar = PlatformHelper.createMenuBar(context, mb);
        setMenuBar(menuBar);
      }
    }

    if (cfg.getToolbars() != null) {
      if (Platform.getAppContext().okForOS(cfg.getToolbars())) {
        setToolBarHolder(ToolBarHolder.create(context, cfg.getToolbars()));
      }
    }

    if (cfg.getStatusBar() != null) {
      StatusBar sb = cfg.getStatusBar();

      if (Platform.getAppContext().okForOS(sb)) {
        setStatusBar(createStatusBar(sb));
      }
    }

    ActionLink link = Utils.createLink(context, iTarget.TARGET_WORKSPACE, cfg.viewer);

    if (link != null) {
      try {
        iViewer v = createViewer(link);

        setWorkspaceViewer(v);
      } catch(Exception e) {
        handleException(e);
      } finally {
        link.close();
      }
    }

    context.setPreferredSize(cfg.bounds.width.getValue(), cfg.bounds.height.getValue());
  }

  @Override
  public iWindow createDialog(iWidget context, Map options) {
    String otype = null;

    if (options == null) {
      options = new HashMap();
    }

    otype = (String) options.put("windowtype", "dialog");

    iWindow w = createWindow(context, options);

    options.put("windowtype", otype);

    return w;
  }

  @Override
  public iWindow createDialog(iWidget context, String title, boolean modal) {
    return createDialog(context, null, title, modal);
  }

  @Override
  public iWindow createDialog(iWidget context, String target, String title, boolean modal) {
    return createWindowOrDialog(context, target, title, modal, true);
  }

  @Override
  public UIPopupMenu createPopupMenu(iWidget context, URL url, boolean addTextItems) throws Exception {
    if (context == null) {
      context = getViewer();
    }

    iURLConnection conn  = appContext.openConnection(url);
    iSPOTElement   e     = DataParser.loadSPOTObject(context, conn, null);
    SPOTSet        menus = null;

    if (e instanceof com.appnativa.rare.spot.MenuItem) {
      com.appnativa.rare.spot.MenuItem mi = (com.appnativa.rare.spot.MenuItem) e;

      menus = mi.getSubMenu();
    } else if (e instanceof Widget) {
      Widget wc = (Widget) e;

      menus = wc.getPopupMenu();
    }

    if (menus == null) {
      return null;
    }

    return new UIPopupMenu(context, menus, addTextItems);
  }

  @Override
  public UIPopupMenu createPopupMenu(UIPopupMenu menu, iWidget context, SPOTSet menus, boolean addTextItems) {
    if (context == null) {
      context = getViewer();
    }

    return new UIPopupMenu(context, menus, addTextItems);
  }

  @Override
  public iStatusBar createStatusBar(StatusBar cfg) {
    if (cfg == null) {
      cfg = new StatusBar();
    }

    return (iStatusBar) createViewer(getRootViewer(), cfg, contextURL);
  }

  @Override
  public iToolBar createToolBar(boolean horizontal) {
    ToolBar cfg = new ToolBar();

    cfg.horizontal.setValue(horizontal);

    return createToolBar(cfg);
  }

  @Override
  public iToolBar createToolBar(ToolBar cfg) {
    ToolBarViewer tb = new ToolBarViewer();

    tb.configure(cfg);

    return tb;
  }

  public static iToolBarHolder createToolBarHolder(iContainer viewer, SPOTSet toolbars) {
    return null;
  }

  @Override
  public iViewer createViewer(ActionLink link) {
    return createViewer(null, link);
  }

  @Override
  public iViewer createViewer(iWidget context, ActionLink link) {
    iViewer v = null;

    if (context == null) {
      context = link.getContext();
    }

    Viewer vcfg = link.getViewerConfiguration();

    if (context == null) {
      context = getViewer();
    }

    try {
      String mimeType = link.getMimeType();

      if (vcfg != null) {
        if (mimeType == null) {
          mimeType = Platform.getSPOTName(vcfg.getClass());
        }

        return createViewer(context, mimeType, vcfg, link.getURL(context));
      } else {
        iURLConnection con = null;
        String         ct  = null;

        try {
          con = link.getConnection();
        } catch(EOFException e) {
          con = link.getConnection();
        }

        if (mimeType == null) {
          mimeType = con.getContentType();

          if (appContext.isInSandbox() && "content/unknown".equals(mimeType)) {
            mimeType = MIMEMap.typeFromFile(con.getURL().getFile());
          }
        }

        vcfg = DataParser.checkAndInstantiateViewer(context, mimeType, con);

        if (vcfg != null) {
          ct = Platform.getSPOTName(vcfg.getClass());
        } else if (con != null) {
          ct = (mimeType != null)
               ? mimeType
               : con.getContentType();
        }

        v = (ct == null)
            ? null
            : createViewer(context, ct, vcfg, link.getURL(context));

        if ((v != null) && (vcfg == null)) {
          v.setDataLink(link);
        }

        return v;
      }
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);

      if (v != null) {
        try {
          v.dispose();
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }
      }

      if (ex instanceof SPOTException) {
        URL url = null;

        try {
          url = link.getURL(context);
        } catch(Exception uex) {
          Platform.ignoreException(null, uex);
        }

        if (url == null) {
          url = context.getViewer().getContextURL();
        }

        String s = (url == null)
                   ? null
                   : url.toString();

        throw DataParser.invalidConfigurationException(appContext, ex, s);
      }

      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      }

      throw new ApplicationException(ex);
    } finally {
      if (v != null) {
        v.setViewerActionLink(link);
      }

      link.close();
    }
  }

  @Override
  public iViewer createViewer(iWidget context, Viewer cfg, URL contextURL) {
    String ct = Platform.getSPOTName(cfg.getClass());

    return createViewer(context, ct, cfg, contextURL);
  }

  @Override
  public iViewer createViewer(iWidget context, String mimeType, Viewer cfg, URL contextURL) {
    if ((contextURL != null) && iConstants.INLINE_PROTOCOL_STRING.equals(contextURL.getProtocol())) {
      contextURL = null;
    }

    if (mimeType == null) {
      mimeType = Platform.getSPOTName(cfg.getClass());
    }

    int n = mimeType.indexOf("viewer=");

    if (n == -1) {
      n = mimeType.indexOf(';');

      if (n != -1) {
        mimeType = mimeType.substring(0, n);
      }

      mimeType = mimeType.trim();
    } else {
      int p = mimeType.indexOf(';', n);

      if (p == -1) {
        mimeType = mimeType.substring(n + 7);
      } else {
        mimeType = mimeType.substring(n + 7, p);
      }

      if (mimeType.indexOf('.') == -1) {
        mimeType = Platform.RARE_SPOT_PACKAGE_NAME + "." + mimeType;
      }

      if ((cfg != null) && BASE_VIEWER.equals(mimeType)) {
        mimeType = getViewerClassName(cfg);
      }

      if (mimeType.indexOf(".rare.") != -1) {
        mimeType = mimeType.replace(".rare.", ".rare.");
      }

      try {
        Widget w = (Widget) PlatformHelper.loadClass(mimeType).newInstance();

        if (w instanceof Viewer) {
          cfg = (Viewer) w;
        } else {
          cfg = new WidgetPane(w);
          ((WidgetPane) cfg).autoResizeWidget.setValue(false);
        }

        mimeType = Platform.getSPOTName(cfg.getClass());
      } catch(Exception ex) {
        n = mimeType.lastIndexOf('.');

        String s = appContext.getResourceAsString("Rare.runtime.text.unknownViewerType");

        throw new ApplicationException(Helper.expandString(s, mimeType.substring(n + 1)));
      }
    }

    Class  cls;
    String s = getViewerClassName(cfg);

    if (s != null) {
      if (s.indexOf(".rare.") != -1) {
        s = s.replace(".rare.", ".rare.");
      }

      try {
        cls = Platform.loadClass(s);
      } catch(ClassNotFoundException ex) {
        cls = null;
      }
    } else {
      cls = appContext.getWidgetHandler(mimeType);

      if ((cls == null) && (cfg != null)) {
        cls = appContext.getWidgetHandler(Platform.getSPOTName(cfg.getClass()));
      }
    }

    if ((cls == null) && (contextURL != null)) {
      s = contextURL.getPath();
      n = s.lastIndexOf('.');

      if (n != -1) {
        cls = appContext.getWidgetHandler(s.substring(n));
      }
    }

    iViewer v = null;

    if ((cls == null) && (cfg == null)) {
      try {
        cls = PlatformHelper.loadClass("com.appnativa.rare.viewer.WebBrowser");
      } catch(ClassNotFoundException ignore) {}
    }

    if (cls == null) {
      if (contextURL != null) {
        Platform.debugLog("context=" + contextURL);
      }

      Platform.debugLog("cfg=" + cfg);

      throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.unknownType") + mimeType);
    } else {
      try {
        v = (iViewer) cls.newInstance();
      } catch(Throwable ex) {
        throw new ApplicationException(ex);
      }
    }

    if (context == null) {
      context = getRootViewer();
    }

    v.setParent(context.getContainerViewer());
    v.setContextURL(contextURL);

    if (cfg != null) {
      v.setLinkedData(cfg.linkedData);
    }

    if (cfg != null) {
      try {
        v.configure(cfg);
      } catch(Exception ex) {
        try {
          v.dispose();
        } catch(Exception e) {
          Platform.ignoreException(null, e);
        }

        if (ex instanceof RuntimeException) {
          throw(RuntimeException) ex;
        }

        throw new ApplicationException(ex);
      }
    }

    return v;
  }

  @Override
  public Widget createWidgetConfig(ActionLink link) {
    if (link.getViewerConfiguration() != null) {
      return link.getViewerConfiguration();
    }

    iWidget context = link.getContext();

    if (context == null) {
      context = getViewer();
    }

    try {
      String         mimeType = link.getMimeType();
      iURLConnection con      = null;

      try {
        con = link.getConnection();
      } catch(EOFException e) {
        con = link.getConnection();
      }

      if (mimeType == null) {
        mimeType = con.getContentType();

        if (appContext.isInSandbox() && "content/unknown".equals(mimeType)) {
          mimeType = MIMEMap.typeFromFile(con.getURL().getFile());
        }
      }

      return DataParser.checkAndInstantiateViewer(context, mimeType, con);
    } catch(Exception ex) {
      if (ex instanceof SPOTException) {
        URL url = null;

        try {
          url = link.getURL(context);
        } catch(Exception uex) {
          Platform.ignoreException(null, uex);
        }

        if (url == null) {
          url = context.getViewer().getContextURL();
        }

        String s = (url == null)
                   ? null
                   : url.toString();

        throw DataParser.invalidConfigurationException(appContext, ex, s);
      }

      if (ex instanceof RuntimeException) {
        throw(RuntimeException) ex;
      }

      throw new ApplicationException(ex);
    } finally {
      link.close();
    }
  }

  @Override
  public iWindow createWindow(iWidget context, String target, String title) {
    return createWindowOrDialog(context, target, title, false, false);
  }

  public iWindow createWindowOrDialog(iWidget context, String target, String title, boolean modal, boolean dialog) {
    HashMap options = new HashMap();

    options.put("title", title);
    options.put("modal", modal);
    options.put("target", target);

    if (dialog) {
      options.put("windowtype", "dialog");
    }

    return createWindow(context, options);
  }

  @Override
  public void dispose() {
    if (!disposed) {
      disposed = true;

      try {
        appContext.closePopupWindows(true);
        this.destroyTargets();
        this.destroyViewers();
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }

      disposeOfWindow();
      mainFrame     = null;
      appContext    = null;
      scriptHandler = null;
    }
  }

  @Override
  public void disposeOfWindow() {
    iViewer v = mainFrame.getViewer();

    if (v != null) {
      v.dispose();
    } else {
      mainFrame.disposeOfWindow();
    }
  }

  /**
   * Handles an <code>HTTPException</code>
   *
   * @param e
   *          the exception
   */
  public void handleException(HTTPException e) {
    showErrorDialog(e);
  }

  /**
   * Handles an <code>InvalidConfigurationException</code>
   *
   * @param e
   *          the exception
   */
  public void handleException(InvalidConfigurationException e) {
    showErrorDialog(e);
  }

  /**
   * Handles an <code>IOException</code>
   *
   * @param e
   *          the exception
   */
  public void handleException(IOException e) {
    showErrorDialog(e);
  }

  /**
   * Handles an <code>MalformedURLException</code>
   *
   * @param e
   *          the exception
   */
  public void handleException(MalformedURLException e) {
    showErrorDialog(e);
  }

  @Override
  public void handleException(final Throwable e) {
    WindowViewer w = Platform.getAppContext().getWindowViewer();

    if (!Functions.isRunningInBackground()) {
      handleExceptionEx(e);
    } else {
      w.invokeLater(new Runnable() {
        @Override
        public void run() {
          handleExceptionEx(e);
        }
      });
    }
  }

  @Override
  public void hideWindow() {
    mainFrame.hideWindow();
  }

  @Override
  public void moveBy(float x, float y) {
    mainFrame.moveBy(x, y);
  }

  @Override
  public void moveTo(float x, float y) {
    mainFrame.moveTo(x, y);
  }

  @Override
  public WindowViewer openViewerWindow(ActionLink link, final Object viewerValue) {
    final Map          opts      = link.getWindowOptions();
    final WindowViewer w         = appContext.getWindowViewer();
    final iWindow      win       = createWindow(getWorkspaceViewer(), opts);
    final iContainer   winviewer = win.getViewer();

    try {
      winviewer.setContextURL(link.getURL(null));
    } catch(MalformedURLException e) {
      throw ApplicationException.runtimeException(e);
    }

    try {
      ViewerCreator.createViewer(winviewer, link, new ViewerCreator.iCallback() {
        @Override
        public void viewerCreated(iWidget context, ActionLink link, iViewer viewer) {
          appContext.getAsyncLoadStatusHandler().loadCompleted(context, link);
          viewer.setParent(winviewer);

          if (viewerValue != null) {
            viewer.setValue(viewerValue);
          }

          setViewerEx(win.getTargetName(), winviewer, viewer);

          String title = (String) ((opts == null)
                                   ? null
                                   : opts.get("title"));

          if (title == null) {
            title = viewer.getTitle();

            if ((title != null) && (title.length() > 0)) {
              win.setTitle(title);
            }
          }

          win.showWindow();
        }
        @Override
        public void errorHappened(iWidget context, ActionLink link, Exception e) {
          appContext.getAsyncLoadStatusHandler().errorOccured(context, link, e);
          w.handleException(e);
        }
        @Override
        public void configCreated(iWidget context, ActionLink link, Viewer config) {}
        @Override
        public void startingOperation(iWidget context, ActionLink link) {
          appContext.getAsyncLoadStatusHandler().loadStarted(context, link, null);
        }
      });
    } catch(MalformedURLException e) {
      throw new ApplicationException(e);
    }

    return (WindowViewer) winviewer;
  }

  @Override
  public void pack() {}

  @Override
  public void registerTarget(String name, iTarget target) {
    iTarget t = theTargets.get(name);

    if (t == null) {
      theTargets.put(name, target);
    } else if (t != target) {
      throw new ApplicationException(appContext.getResourceAsString("Rare.runtime.text.targetExists"), name);
    }
  }

  @Override
  public void registerViewer(String name, iViewer viewer) {
    iWeakReference r = activeViewers.get(name);
    iViewer        v = (iViewer) ((r == null)
                                  ? null
                                  : r.get());

    if (v != viewer) {
      activeViewers.put(name, PlatformHelper.createWeakReference(viewer));
    }
  }

  @Override
  public void removeTarget(String target) {
    theTargets.remove(target);
  }

  @Override
  public void removeWindowListener(iWindowListener l) {
    mainFrame.removeWindowListener(l);
  }

  @Override
  public void reset(boolean reloadViewers) {}

  public Runnable runnableForActivateViewer(ActionLink link) {
    return new ViewerActivator(link);
  }

  public Runnable runnableForActivateViewer(iWidget context, Viewer cfg, String target) {
    return new ViewerActivator(context, cfg, target);
  }

  public void showAboutDialog() {
    if (aboutDialogRunner != null) {
      Platform.invokeLater(aboutDialogRunner);
    }
  }

  @Override
  public void showWindow() {
    mainFrame.showWindow();
  }

  @Override
  public void showWindow(int x, int y) {
    mainFrame.showWindow(x, y);
  }

  @Override
  public void toBack() {
    mainFrame.toBack();
  }

  @Override
  public void toFront() {
    mainFrame.toFront();
  }

  @Override
  public void unRegisterTarget(String name) {
    removeTarget(name);
  }

  @Override
  public void unRegisterViewer(String name) {
    iWeakReference r = activeViewers.remove(name);

    if (r != null) {
      r.clear();
    }
  }

  @Override
  public void update() {
    mainFrame.update();
  }

  public void setAboutDialogRunner(Runnable aboutDialogRunner) {
    this.aboutDialogRunner = aboutDialogRunner;
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    mainFrame.setBounds(x, y, width, height);
  }

  @Override
  public void setCanClose(boolean can) {}

  @Override
  public void setContextURL(URL url) {
    contextURL = url;
  }

  @Override
  public void setDefaultFont(UIFont font) {
    defaultFont = font;
    UIFontHelper.setDefaultFont(font);
  }

  @Override
  public void setLocation(float x, float y) {
    mainFrame.setLocation(x, y);
  }

  @Override
  public iPlatformMenuBar setMenuBar(iPlatformMenuBar mb) {
    return mainFrame.setMenuBar(mb);
  }

  @Override
  public float setRelativeFontSize(float size) {
    return 1;
  }

  @Override
  public void setResizable(boolean resizable) {}

  /**
   * @param scriptHandler
   *          the scriptHandler to set
   */
  public void setScriptHandler(iScriptHandler scriptHandler) {
    this.scriptHandler = scriptHandler;
  }

  @Override
  public void setSize(float width, float height) {
    mainFrame.setSize(width, height);
  }

  @Override
  public void setStatus(String status) {
    iStatusBar sb = getStatusBar();

    if (sb != null) {
      sb.setProgressStatus(status);
    }
  }

  @Override
  public iStatusBar setStatusBar(iStatusBar sb) {
    return mainFrame.setStatusBar(sb);
  }

  @Override
  public void setTitle(String title) {
    mainFrame.setTitle(title);
  }

  @Override
  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    return mainFrame.setToolBarHolder(tbh);
  }

  @Override
  public iViewer setViewer(String target, iWidget context, iViewer viewer, Map options) {
    iTarget t = null;

    if ((target == null) || target.endsWith(iTarget.TARGET_NEW_WINDOW) || viewer.isWindowOnlyViewer()) {
      viewer.showAsWindow(options);

      return null;
    }

    if (target.equals(iTarget.TARGET_NULL)) {
      return null;
    }

    if (target.endsWith(iTarget.TARGET_NEW_POPUP)) {
      iPlatformComponent comp = (context == null)
                                ? null
                                : context.getDataComponent();

      if (comp == null) {
        comp = mainFrame.getComponent();
      }

      viewer.showAsPopup(comp, options);

      return null;
    }

    if (target.endsWith(iTarget.TARGET_PARENT)) {
      if (context != null) {
        t = context.getParent().getTarget();
      }
    } else if (target.endsWith(iTarget.TARGET_SELF)) {
      if (context != null) {
        t = context.getViewer().getTarget();
      }
    } else if (target.equals(iTarget.TARGET_WORKSPACE)) {
      t = workspaceTarget;
    } else {
      t = getTarget(target);
    }

    if (t == null) {
      throw new RuntimeException(appContext.getResourceAsString("Rare.runtime.text.unknownTarget") + target);
    }

    return t.setViewer(viewer);
  }

  @Override
  public iViewer setViewerEx(String target, iWidget context, iViewer viewer) {
    iTarget t = null;

    if (target.endsWith(iTarget.TARGET_SELF)) {
      if (context != null) {
        t = context.getViewer().getTarget();
      }
    } else {
      t = getTarget(target);
    }

    if (t == null) {
      throw new RuntimeException(appContext.getResourceAsString("Rare.runtime.text.unknownTarget") + target);
    }

    return t.setViewer(viewer);
  }

  @Override
  public void setWindowIcons(List<UIImageIcon> icons) {
    this.windowIcons = icons;
    setWindowIconsEx(icons);
  }

  public Runnable getAboutDialogRunner() {
    return aboutDialogRunner;
  }

  /**
   * Gets the application context for the window manager
   *
   * @return the application context for the window manager
   */
  public iPlatformAppContext getAppContext() {
    return appContext;
  }

  @Override
  public UIRectangle getBounds() {
    return mainFrame.getBounds();
  }

  @Override
  public iPlatformComponent getComponent() {
    return mainFrame.getComponent();
  }

  @Override
  public iPlatformComponentFactory getComponentCreator() {
    return componentCreator;
  }

  @Override
  public UIFont getDefaultFont() {
    return defaultFont;
  }

  @Override
  public UIFont getDefaultMonospacedFont() {
    return Platform.getUIDefaults().getFont("Rare.font.plaintext");
  }

  @Override
  public iFrame getFrame(String name) {
    return null;
  }

  @Override
  public iFrame[] getFrames() {
    return null;
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    return mainFrame.getInnerSize(size);
  }

  @Override
  public int getHeight() {
    return mainFrame.getHeight();
  }

  @Override
  public iWindow getMainWindow() {
    return mainFrame;
  }

  @Override
  public iPlatformMenuBar getMenuBar() {
    return (menuBar == null)
           ? mainFrame.getMenuBar()
           : menuBar;
  }

  @Override
  public iContainer getRootViewer() {
    if (getScriptHandler() != null) {
      return getScriptHandler().getWindowViewer();
    }

    return null;
  }

  @Override
  public int getScreenX() {
    return mainFrame.getScreenX();
  }

  @Override
  public int getScreenY() {
    return mainFrame.getScreenY();
  }

  /**
   * @return the scriptHandler
   */
  @Override
  public iScriptHandler getScriptHandler() {
    return scriptHandler;
  }

  @Override
  public UIDimension getSize() {
    return mainFrame.getSize();
  }

  @Override
  public iStatusBar getStatusBar() {
    return mainFrame.getStatusBar();
  }

  @Override
  public iTarget getTarget() {
    return workspaceTarget;
  }

  @Override
  public iTarget getTarget(String name) {
    return theTargets.get(name);
  }

  @Override
  public String getTargetName() {
    iTarget t = workspaceTarget;

    return (t == null)
           ? null
           : t.getName();
  }

  @Override
  public iTarget[] getTargets() {
    iTarget[] a = new iTarget[theTargets.size()];

    a = theTargets.values().toArray(a);

    return a;
  }

  @Override
  public String getTitle() {
    return mainFrame.getTitle();
  }

  @Override
  public iToolBarHolder getToolBarHolder() {
    return mainFrame.getToolBarHolder();
  }

  @Override
  public Object getUIWindow() {
    return mainFrame.getUIWindow();
  }

  @Override
  public URL getURL(String url) throws MalformedURLException {
    return appContext.createURL(contextURL, url);
  }

  @Override
  public int getUsableScreenHeight() {
    return UIScreen.getHeight();
  }

  @Override
  public int getUsableScreenWidth() {
    return UIScreen.getWidth();
  }

  @Override
  public iContainer getViewer() {
    return getRootViewer();
  }

  @Override
  public iViewer getViewer(String name) {
    iWeakReference r = activeViewers.get(name);

    return (iViewer) ((r == null)
                      ? null
                      : r.get());
  }

  @Override
  public iViewer getViewerByTargetName(String target) {
    iTarget t = getTarget(target);

    return (t == null)
           ? null
           : t.getViewer();
  }

  @Override
  public iViewer[] getViewers() {
    int                      len = activeViewers.size();
    Iterator<iWeakReference> it  = activeViewers.values().iterator();
    iViewer[]                a   = new iViewer[activeViewers.size()];
    int                      i   = 0;
    iViewer                  v;

    while(it.hasNext()) {
      v = (iViewer) it.next().get();

      if (v != null) {
        a[i++] = v;
      }
    }

    if (i != len) {
      iViewer[] b = new iViewer[i];

      System.arraycopy(a, 0, b, 0, i);
      a = b;
    }

    return a;
  }

  @Override
  public aWidgetListener getWidgetListener() {
    return widgetListener;
  }

  @Override
  public int getWidth() {
    return mainFrame.getWidth();
  }

  @Override
  public List<UIImageIcon> getWindowIcons() {
    return windowIcons;
  }

  public iContainer getWindowViewer() {
    return scriptHandler.getWindowViewer();
  }

  @Override
  public iViewer getWorkspaceViewer() {
    return workspaceTarget.getViewer();
  }

  @Override
  public boolean isDisposed() {
    return scriptHandler == null;
  }

  public boolean isDesignMode() {
    return false;
  }

  protected void createScriptHandler(MainWindow cfg) {
    String type     = null;
    String code     = null;
    String location = null;

    if (cfg.scriptURL.spot_valueWasSet() &&!isDesignMode()) {
      if (cfg.scriptURL.spot_getLinkedData() instanceof ObjectHolder) {
        ObjectHolder h = (ObjectHolder) cfg.scriptURL.spot_getLinkedData();

        type     = (String) h.type;
        code     = (String) h.value;
        location = (String) h.source;
      } else {
        try {
          ActionLink link    = ActionLink.getActionLink(appContext.getRootViewer(), cfg.scriptURL, 0);
          boolean    runonce = "true".equalsIgnoreCase(cfg.scriptURL.spot_getAttribute("runOnce"));

          type = link.getContentType();
          code = appContext.loadScriptCode(link, runonce);

          if (code != null) {
            if (link.isInlineURL()) {
              if (!link.isMimeTypeSet() || "text/plain".equals(type)) {
                type = appContext.getDefaultScrptingLanguage();
              }

              location = iConstants.INLINE_PREFIX + type + "?id=MainWindow";

              URL url = contextURL;

              if (url != null) {
                location += ",contextURL=" + url.toExternalForm();
              }
            } else {
              URL url = link.getURL(appContext.getRootViewer());

              location = url.toExternalForm();
              type     = appContext.getScriptType(link);
            }
          }
        } catch(IOException e) {
          Platform.ignoreException(null, e);
        }
      }
    }

    if ((type != null) && (type.length() == 0)) {
      type = null;
    }

    scriptHandler = appContext.getScriptingManager().getRootHandler(appContext, getMainWindow(), type, location, code,
            false);
    scriptHandler.getWindowViewer().setContextURL(contextURL);

    Map map = aWidgetListener.createEventMap(cfg.spot_getAttributesEx());

    if (map == null) {
      map = Collections.EMPTY_MAP;
    }

    widgetListener = createWidgetListener(scriptHandler.getWindowViewer(), map, scriptHandler);
    ((WindowViewer) scriptHandler.getWindowViewer()).setWidgetListener(widgetListener);
  }

  protected iWidget createTitleWidget(MainWindow cfg) {
    if (!cfg.decorated.booleanValue()) {
      ActionLink link = Utils.createLink(getViewer(), iTarget.TARGET_WORKSPACE, cfg.titlePane);

      if (link != null) {
        return aContainer.createWidget(scriptHandler.getWindowViewer(), link);
      }
    }

    return null;
  }

  protected abstract aWidgetListener createWidgetListener(iWidget widget, Map map, iScriptHandler scriptHandler);

  protected void destroyTargets() {
    iTarget   t;
    iTarget[] a = getTargets();

    if (a == null) {
      return;
    }

    int len = a.length;

    for (int i = 0; i < len; i++) {
      t = a[i];

      if (t != null) {
        t.dispose(true);
      }
    }

    theTargets.clear();
  }

  /**
   * Disposes of all viewers
   */
  protected void destroyViewers() {
    iViewer[] a = getViewers();

    for (iViewer v : a) {
      if (v != null) {
        v.dispose();
      }
    }

    activeViewers.clear();
  }

  /**
   * Fires an event for the window
   *
   * @param eventName
   *          the event
   * @param event
   *          the event object
   * @param inline
   *          true to perform and evaluation (inline execution); false otherwise
   * @return the result of an evaluation
   */
  protected Object fireEvent(String eventName, EventObject event, boolean inline) {
    aWidgetListener wl = this.getWidgetListener();

    if ((wl == null) || (eventName == null) || (event == null)) {
      return null;
    }

    if (!eventName.startsWith("rare")) {
      eventName = "rare.scripting.event." + eventName;
    }

    if (inline) {
      return wl.evaluate(eventName, event, false);
    }

    wl.execute(eventName, event);

    return false;
  }

  /**
   * Handles widget custom properties. The default behavior is to just attach
   * the properties to this widget
   *
   * @param cfg
   *          the configuration
   * @param properties
   *          the properties
   */
  protected void handleCustomProperties(MainWindow cfg, Map<String, Object> properties) {
    WindowViewer w = (WindowViewer) scriptHandler.getWindowViewer();

    if (w.getCustomProperties() == null) {
      w.setCustomProperties(properties);
    } else {
      w.getCustomProperties().putAll(properties);
    }
  }

  protected void handleExceptionEx(Throwable e) {
    e = Helper.pealException(e);

    if (e instanceof NullPointerException) {
      // plugin startup buf test bug fix
      StringWriter sw = new StringWriter();
      PrintWriter  pw = new PrintWriter(sw);

      e.printStackTrace(pw);
      pw.flush();
      sw.flush();

      if (sw.toString().contains("sun.plugin.util.AnimationPanel.createTranslucentImage")) {
        return;
      }
    }

    if (e instanceof AbortOperationException) {
      throw(AbortOperationException) e;    // keep throwing it until it reaches
      // the uncaught exception handler
    }

    if (e instanceof HTTPException) {
      handleException((HTTPException) e);

      return;
    }

    if (e instanceof MalformedURLException) {
      handleException((MalformedURLException) e);

      return;
    }

    if (e instanceof IOException) {
      handleException((IOException) e);

      return;
    }

    if (e instanceof InvalidConfigurationException) {
      handleException((InvalidConfigurationException) e);

      return;
    }

    if (appContext.isDebugEnabled()) {
      e.printStackTrace();
    }

    showErrorDialog(e);
  }

  protected abstract void showErrorDialog(Throwable e);

  protected boolean supportsMultipleWindowIcons() {
    return true;
  }

  protected abstract void setWindowIconsEx(List<UIImageIcon> icons);

  protected void setWorkspaceViewer(iViewer v) {
    workspaceTarget.setViewer(v);
  }

  protected String getViewerClassName(Viewer cfg) {
    String s = (cfg == null)
               ? null
               : cfg.spot_getAttribute("viewerClass");

    if (s != null) {
      if (s.indexOf('.') == -1) {
        s = Platform.RARE_PACKAGE_NAME + ".viewer." + s;

        if (!s.endsWith("Viewer")) {
          s += "Viewer";
        }
      }
    }

    return s;
  }

  protected class ViewerActivator implements Runnable {
    private ActionLink theLink;

    /**
     * Constructs a new instance
     */
    ViewerActivator(ActionLink link) {
      theLink = link;
    }

    /**
     * Constructs a new instance
     */
    ViewerActivator(iWidget context, Viewer cfg, String target) {
      theLink = new ActionLink();
      theLink.setTargetName(target);
      theLink.setContext(context);
      theLink.setViewerConfiguration(cfg);
    }

    @Override
    public void run() {
      try {
        activateViewer(theLink);
      } finally {
        theLink.close();
      }
    }
  }
}
