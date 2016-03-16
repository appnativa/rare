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

package com.appnativa.rare.viewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.TemplateHandler;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.iDataCollectionHandler;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iTimer;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.ActionLink.ReturnDataType;
import com.appnativa.rare.net.InlineURLConnection;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.scripting.Functions;
import com.appnativa.rare.scripting.ScriptingEvent;
import com.appnativa.rare.scripting.WidgetContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.spot.Viewer;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.spot.WidgetPane;
import com.appnativa.rare.ui.ActionBar;
import com.appnativa.rare.ui.AlertPanel;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UICursor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UINotifier;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.UISoundHelper;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.ViewerCreator;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.aUIImageIcon;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.ui.iToolBarHolder;
import com.appnativa.rare.ui.iWindow;
import com.appnativa.rare.ui.iWindowManager;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.DataEvent;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.ui.event.iWindowListener;
import com.appnativa.rare.ui.painter.UIImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.util.DataItemParserHandler;
import com.appnativa.rare.util.DataParser;
import com.appnativa.rare.widget.BeanWidget;
import com.appnativa.rare.widget.PushButtonWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.spot.SPOTSequence;
import com.appnativa.spot.iSPOTConstants;
import com.appnativa.spot.iSPOTElement;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Helper;
import com.appnativa.util.SimpleDateFormatEx;
import com.appnativa.util.iCancelable;

public abstract class aWindowViewer extends aContainer implements iWindow {
  private static WindowLocation    _location;
  private static BrowserNavigator  _navigator;
  protected boolean                canClose = true;
  protected iTimer                 clearStatusTimer;
  protected iScriptHandler         scriptHandler;
  protected ScriptingEvent         theEvent;
  protected iWindow                theWindow;
  protected ScriptingEvent         timerEvent;
  protected iPlatformWindowManager windowManager;
  protected aWindowViewer          windowParent;
  protected UIDimension            windowSize;
  protected iTimer                 windowTimer;

  /**
   * Constructs a new instance
   *
   * @param ctx
   *          the application context
   * @param name
   *          the window name
   * @param win
   *          the window handle
   * @param parent
   *          the parent window viewer
   * @param sh
   *          the script handler
   */
  public aWindowViewer(iPlatformAppContext ctx, String name, iWindow win, aWindowViewer parent, iScriptHandler sh) {
    super(null);
    actAsFormViewer = true;
    appContext      = ctx;
    scriptHandler   = sh;
    widgetType      = WidgetType.Window;
    widgetName      = name;
    windowParent    = parent;
    windowManager   = getAppContext().getWindowManager();
    droppingAllowed = true;
    theWindow       = win;
    theEvent        = new ScriptingEvent(sh, iConstants.ATTRIBUTE_ON_CONFIGURE, new EventBase(win), win, null);
  }

  /**
   * Creates and activates the viewer for the specified Action link
   *
   * @param link
   *          the Action link
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void activateViewer(ActionLink link) throws IOException {
    windowManager.asyncActivateViewer(link);
  }

  /**
   * Activates the specified viewer by setting it into the specified target
   *
   * @param viewer
   *          the viewer
   * @param target
   *          the target (as a string)
   */
  public void activateViewer(iViewer viewer, String target) {
    activateViewer(this, viewer, target);
  }

  /**
   * Creates and activates the viewer for the specified Action link
   *
   * @param href
   *          the href (can be a String, a URL, or a File)
   * @param target
   *          the target (as a string)
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void activateViewer(Object href, String target) throws IOException {
    ActionLink link = createActionLink(href);

    link.setTargetName(target);;
    activateViewer(link);
  }

  /**
   * Activates the specified viewer by setting it into the specified target
   *
   * @param context
   *          the context for the viewer
   * @param viewer
   *          the viewer
   * @param target
   *          the target (as a string)
   */
  public void activateViewer(iWidget context, iViewer viewer, String target) {
    target = Utils.fixTarget(target);

    if (viewer == null) {
      windowManager.clearTarget(target);

      return;
    }

    iTarget t = viewer.getTarget();

    if ((t != null) && (t == windowManager.getTarget(target))) {
      return;
    }

    iViewer parent = (context == null)
                     ? viewer.getContainerViewer()
                     : context.getViewer();
    iViewer ov     = windowManager.setViewer(target, parent, viewer, null);

    if ((ov != null) && ov.isAutoDispose()) {
      ov.dispose();
    }
  }

  /**
   * Creates and activates the viewer for the specified Action link
   *
   * @param context
   *          the context for the viewer
   * @param href
   *          the href (can be a String, a URL, or a File)
   * @param target
   *          the target (as a string)
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void activateViewer(iWidget context, Object href, String target) throws IOException {
    ActionLink link = createActionLink(context, href);

    link.setTargetName(target);;
    activateViewer(link);
  }

  /**
   * Overlays a component on the window.
   * The component will be placed and sized based on its current bounds
   *
   * @param c  the component
   */
  public void addOverlay(iPlatformComponent c) {
    if (theWindow instanceof Frame) {
      ((Frame) theWindow).addOverlay(c);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Overlays a widget on the window.
   * The widget will be placed and sized based on its current bounds
   *
   * @param w  the widget
   */
  public void addOverlay(iWidget w) {
    addOverlay(w.getContainerComponent());
  }

  /**
   * Overlays a component on the window.
   * The component will be placed and sized based on
   * size and location of the window's content area
   *
   * @param c  the component
   */
  public void addManagedOverlay(iPlatformComponent c) {
    if (theWindow instanceof Frame) {
      ((Frame) theWindow).addManagedOverlay(c);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Overlays a widget on the window.
   * The widget will be placed and sized based on
   * size and location of the window's content area
   *
   * @param w  the widget
   */
  public void addManagedOverlay(iWidget w) {
    addManagedOverlay(w.getContainerComponent());
  }

  /**
   * Adds the specified widget as a dragger for the window
   *
   * @param widget
   *          the widget
   */
  public abstract void addWindowDragger(iWidget widget);

  @Override
  public void addWindowListener(iWindowListener l) {
    theWindow.addWindowListener(l);
  }

  /**
   * Displays up a dialog box to alert the user about an application condition
   *
   * @param message
   *          the alert message
   */
  public void alert(Object message) {
    alert(getTitle(), message, null);
  }

  /**
   * Displays up a dialog box to alert the user about an application condition
   *
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes
   */
  public void alert(Object message, iFunctionCallback cb) {
    alert(getTitle(), message, cb);
  }

  /**
   * Displays up a dialog box to alert the user about an application condition
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes
   */
  public void alert(String title, Object message, iFunctionCallback cb) {

    AlertPanel d = AlertPanel.ok(this, title, message, null);
    if(!Platform.isTouchDevice()) {
      d.setRightAlignButtons(true);
    }
    d.showDialog(cb);
  }

  /**
   * Beeps
   */
  public void beep() {
    UISoundHelper.beep();
  }

  /**
   * Sends the window to the back
   */
  public void blur() {}

  /**
   * Opens the specified URL with the default system web browser
   *
   * @param url
   *          the url to open
   *
   * @return true if the browser was activated; false otherwise
   * @throws java.net.MalformedURLException
   */
  public boolean browse(String url) throws MalformedURLException {
    return browse(getAppContext().getURL(url));
  }

  /**
   * Opens the specified URL with the default system web browser
   *
   * @param url
   *          the url to open
   *
   * @return true if the browser was activated; false otherwise
   */
  public boolean browse(URL url) {
    return Platform.browseURL(url);
  }

  @Override
  public boolean canDrop() {
    return getImportableDataFlavors() != null;
  }

  @Override
  public void center() {
    theWindow.center();
  }

  /**
   * Centers the window in relation to the specified window
   *
   * @param win
   *          the window to center this one around
   */
  public void center(WindowViewer win) {
    if (win == null) {
      center();
    } else {
      ScreenUtils.centerOnWindow(theWindow, win.getWindow());
    }
  }

  @Override
  public void clearContents() {}

  @Override
  public void clearForm() {}

  /**
   * Clears all instance data
   */
  public void clearInstance() {}

  /**
   * Cancels the specified task, if it has not already been executed
   * and prevents any future executions
   *
   * @param task
   *          the task to cancel
   */
  public void clearInterval(iCancelable task) {
    if (task != null) {
      task.cancel(true);
    }
  }

  /**
   * Clears
   */
  public void clearSessionCookies() {
    Platform.clearSessionCookies();
  }

  /**
   * Clears the specified target
   *
   * @param target
   *          the target (as a string)
   */
  public void clearTarget(String target) {
    target = Utils.fixTarget(target);

    if (target != null) {
      windowManager.clearTarget(target);
    }
  }

  /**
   * Cancels the specified task, if it has not already been executed
   *
   * @param task
   *          the task to cancel
   */
  public void clearTimeout(iCancelable task) {
    if (task != null) {
      task.cancel(true);
    }
  }

  @Override
  public void close() {
    if (theWindow != null) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          if (theWindow != null) {
            theWindow.close();
          }
        }
      };

      if (Platform.isUIThread()) {
        r.run();
      } else {
        Platform.invokeLater(r);
      }
    }
  }

  /**
   * Closes all open popup windows leaving only the main window open
   *
   * @param all true to close all windows ( dialogs and popups); false to only close popups.
   */
  public void closePopupWindows(boolean all) {
    getAppContext().closePopupWindows(all);
  }

  /**
   * Configures the window The method is not supported for top level windows
   *
   * @param vcfg
   *          the configuration
   */
  @Override
  public void configure(Viewer vcfg) {}

  /**
   * Displays a confirmation dialog box, allowing the confirming of an action
   *
   * @param message
   *          the message to display
   * @param cb
   *          the callback to call when the object has been created
   */
  public void confirm(Object message, iFunctionCallback cb) {
    confirm(null, message, null, null, cb);
  }

  /**
   * Displays a confirmation dialog box, allowing the confirming of an action
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param cb
   *          the callback to call when the object has been created
   */
  public void confirm(Object title, Object message, iFunctionCallback cb) {
    confirm(title, message, null, null, cb);
  }

  /**
   * Displays a confirmation dialog box, allowing the confirming of an action
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param ok the text for the ok button
   * @param cancel the text for the cancel button
   * @param cb
   *          the callback to call when the object has been created
   */
  public void confirm(Object title, Object message, String ok, String cancel, iFunctionCallback cb) {
    title   = (title == null)
              ? getTitle()
              : expandString(title.toString(), false);

    AlertPanel d = AlertPanel.yesNo(this, title.toString(), message, null, ok, cancel, true);

    d.showDialog(cb);
  }

  /**
   * Copies the specified value to the system clipboard making it available to
   * other applications.
   *
   * @param value
   *          the value to copy to the clipboard
   */
  public abstract void copyToClipboard(String value);

  /**
   * Convenience method for creating an Action link for a URL
   *
   * @param url
   *          the url (can be a String, a URL, or a File)
   *
   * @return a new <code>ActionLink</code> object
   * @throws MalformedURLException
   */
  public ActionLink createActionLink(Object url) throws MalformedURLException {
    return createActionLink(this, url);
  }

  /**
   * Convenience method for creating an Action link for a url
   *
   * @param context
   *          the context for the link
   * @param url
   *          the url (can be a String, a URL, or a File)
   *
   * @return a new <code>ActionLink</code> object
   */
  public ActionLink createActionLink(iWidget context, Object url) throws MalformedURLException {
    if (url instanceof ActionLink) {
      return (ActionLink) url;
    }

    if (context == null) {
      context = this;
    }

    if (url instanceof String) {
      return new ActionLink(context, context.getURL((String) url));
    }

    if (url instanceof URL) {
      return new ActionLink(context, (URL) url);
    }

    if (url instanceof File) {
      return new ActionLink(context, PlatformHelper.fileToURL((File) url));
    }

    return new ActionLink(context, context.getURL(url.toString()));
  }

  /**
   * Creates a an animator for the named animation that can be used
   * to animate a widget.
   *
   * @param animation the animation
   *
   * @return the animator or null if one could not be created
   */
  public iPlatformAnimator createAnimator(String animation) {
    if (animation.contains("Rare.anim.")) {
      return getAppContext().getResourceAsAnimator(animation);
    }

    int n = animation.indexOf('.');

    if (n == -1) {
      return (iPlatformAnimator) Platform.createObject("com.appnativa.rare.ui.effects." + animation);
    }

    Object o = Platform.getUIDefaults().get(animation);

    if (o instanceof iPlatformAnimator) {
      return (iPlatformAnimator) o;
    } else if (o instanceof Class) {
      try {
        return (iPlatformAnimator) ((Class) o).newInstance();
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    } else if ((o == null) && (animation.indexOf('.') != -1)) {
      return (iPlatformAnimator) Platform.createObject(animation);
    }

    return null;
  }

  /**
   * Creates a java.lang.Runnable object for the specified code
   *
   * @param code
   *          the code to create the runner for
   * @param cancelCode
   *          the code that will cancel the running code
   * @param lang
   *          the scripting language (use null for the default)
   * @return the runnable
   */
  public iScriptHandler.iScriptRunnable createCancelableRunner(Object code, Object cancelCode, String lang) {
    iScriptHandler.iScriptRunnable r = createRunner(null, code, lang);

    if (cancelCode != null) {
      r.setCancelRunner(createRunner(null, cancelCode, lang), true);
    }

    return r;
  }

  /**
   * Creates a configuration object
   *
   * @param link
   *          the link representing the data for the configuration object
   *
   * @return the configuration object or null if the link is null or does not
   *         represent a configuration object
   * @throws Exception
   */
  public iSPOTElement createConfigurationObject(ActionLink link) throws Exception {
    if (link == null) {
      return null;
    }

    try {
      iWidget ctx = link.getContext();

      if (ctx == null) {
        ctx = this;
      }

      return DataParser.loadSPOTObject(ctx, link.getConnection(), null);
    } finally {
      link.close();
    }
  }

  /**
   * Creates a configuration object
   *
   * @param name
   *          the name of the configuration object to create (e.g. Table,
   *          DocumentPane, etc.)
   *
   * @return the configuration object or null if an object with the specified
   *         name does not exist
   */
  public iSPOTElement createConfigurationObject(String name) {
    return createConfigurationObject(name, null);
  }

  /**
   * Creates a configuration object
   *
   * @param link
   *          the link representing the data for the configuration object
   * @param cb
   *          the callback to call when the object has been created
   * @return a handle to a cancel-able
   */
  public iCancelable createConfigurationObject(ActionLink link, iFunctionCallback cb) throws Exception {
    if (link == null) {
      return null;
    }

    iWidget ctx = link.getContext();

    if (ctx == null) {
      ctx = this;
    }

    return ViewerCreator.createConfiguration(ctx, link, cb);
  }

  /**
   * Creates a configuration object
   *
   * @param name
   *          the name of the configuration object to create (e.g. Table,
   *          DocumentPane, etc.)
   *
   * @param templateName
   *          the name of the template to use for the object
   * @return the configuration object or null if an object with the specified
   *         name does not exist
   */
  public iSPOTElement createConfigurationObject(String name, String templateName) {
    name = name.replace(':', '.');

    if (name.startsWith("SPOT")) {
      name = iSPOTConstants.SPOT_PACKAGE_NAME + "." + name;
    } else if (name.indexOf('.') == -1) {
      name = Platform.RARE_SPOT_PACKAGE_NAME + "." + name;
    }

    try {
      Class           cls = PlatformHelper.loadClass(name);
      iSPOTElement    e   = (iSPOTElement) cls.newInstance();
      TemplateHandler th  = TemplateHandler.getInstance(appContext);

      if (th != null) {
        th.applyTemplate((SPOTSequence) e, templateName);
      }

      return e;
    } catch(Exception e) {
      Platform.ignoreException("failed to creating object:" + name, e);

      return null;
    }
  }

  /**
   * Create a collection from the specified URL. The data is assumed to
   * be in tabular form
   *
   * @param link
   *          the link to use to create the collection
   * @param cb the callback to call when the collection has been loaded (can be null to not load)
   * @return a collection
   */
  public iDataCollection createDataCollection(ActionLink link, iFunctionCallback cb) {
    return getAppContext().createCollection(null, null, link, null, true, cb);
  }

  /**
   * Create a collection from the specified URL
   *
   * @param context
   *          the context
   * @param list
   *          the list to use to create the collection
   *
   * @return a collection
   */
  public iDataCollection createDataCollection(iWidget context, List<RenderableDataItem> list) {
    return new DataItemParserHandler(context, list);
  }

  /**
   * Create a collection from the specified URL
   *
   * @param link
   *          the link to use to create the collection
   * @param tabular
   *          true if the data is tabular; false otherwise
   *
   * @param cb the callback to call when the collection has been loaded (can be null to not load)
   * @return a collection
   */
  public iDataCollection createDataCollection(ActionLink link, boolean tabular, iFunctionCallback cb) {
    return getAppContext().createCollection(null, null, link, null, tabular, cb);
  }

  /**
   * Create a collection from the specified URL
   *
   * @param name
   *          the name of the collection to create
   * @param link
   *          the link to use to create the collection
   * @param tabular
   *          true if the data is tabular; false otherwise
   *
   * @param cb the callback to call when the collection has been loaded (can be null to not load)
   *
   * @return a collection
   */
  public iDataCollection createDataCollection(String name, ActionLink link, boolean tabular, iFunctionCallback cb) {
    return getAppContext().createCollection(null, name, link, null, tabular, cb);
  }

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
   *
   * @param cb the callback to call when the collection has been loaded (can be null to not load)
   *
   * @return a collection
   */
  public iDataCollection createDataCollection(String handler, String name, ActionLink link, Map attributes,
          boolean tabular, iFunctionCallback cb) {
    return getAppContext().createCollection(handler, name, link, attributes, tabular, cb);
  }

  /**
   * Creates an image painter for the specified image
   *
   * @param icon
   *          the image
   * @return the image painter
   */
  public abstract iImagePainter createImagePainter(iPlatformIcon icon);

  /**
   * Creates an image painter for the specified image
   *
   * @param image
   *          the image
   * @return the image painter
   */
  public iImagePainter createImagePainter(UIImage image) {
    return new UIImagePainter(image);
  }

  /**
   * Convenience method for creating an Action link for a string containing data
   *
   * @param data
   *          the data
   * @param mimeType
   *          the MIME type of the data (defaults to text/plain)
   *
   * @return a new <code>ActionLink</code> object
   */
  public ActionLink createInlineActionLink(String data, String mimeType) {
    return new ActionLink(data, mimeType);
  }

  /**
   * Convenience method for creating a URL link for a string containing data
   *
   * @param data
   *          the data
   * @param mimeType
   *          the MIME type of the data (defaults to text/plain)
   *
   * @return a new <code>ActionLink</code> object
   * @throws java.net.MalformedURLException
   */
  public URL createInlineURL(String data, String mimeType) throws MalformedURLException {
    return InlineURLConnection.createURL(data, mimeType, null);
  }

  /**
   * Creates a new empty popup menu
   *
   * @param context
   *          the widget context for the menu items (can be null)
   *
   * @return a new <code>UIPopupMenu</code> object
   */
  public UIPopupMenu createPopupMenu(iWidget context) {
    UIPopupMenu p = new UIPopupMenu();

    p.setContextWidget(context);

    return p;
  }

  /**
   * Creates a new popup menu from the specified URL
   *
   * @param context
   *          the widget context for the menu items
   * @param url
   *          the URL containing a definition the menu items
   *
   * @return a new <code>UIPopupMenu</code> object
   * @throws Exception
   */
  public UIPopupMenu createPopupMenu(iWidget context, String url) throws Exception {
    iViewer parent = (context == null)
                     ? this
                     : context.getViewer();
    URL     u      = parent.getURL(url);

    return windowManager.createPopupMenu(parent, u, false);
  }

  /**
   * Creates a new popup menu from the specified URL
   *
   * @param context
   *          the widget context for the menu items
   * @param url
   *          the URL containing a definition the menu items
   * @param addDefaultItems
   *          whether the default menu items (copy, cut, paste, etc.) should be
   *          added to this menu
   *
   * @return a new <code>UIPopupMenu</code> object
   * @throws Exception
   */
  public UIPopupMenu createPopupMenu(iWidget context, String url, boolean addDefaultItems) throws Exception {
    iViewer parent = (context == null)
                     ? this
                     : context.getViewer();
    URL     u      = parent.getURL(url);

    return windowManager.createPopupMenu(parent, u, addDefaultItems);
  }

  /**
   * Creates a java.lang.Runnable object for the specified code
   *
   * @param args
   *          the arguments ( code, [language] )
   *
   * @return the runnable
   */
  public iScriptHandler.iScriptRunnable createRunnable(Object... args) {
    iScriptHandler.iScriptRunnable r = createRunner(null, args[0], (args.length > 1)
            ? (String) args[1]
            : null);

    return r;
  }

  /**
   * Creates the viewer for the specified viewer Action link. The viewer is not
   * activated
   *
   * @param link
   *          the link
   * @return an instance of the viewer that was created or null if the viewer
   *         could no be created
   */
  public iViewer createViewer(ActionLink link) {
    return windowManager.createViewer(link);
  }

  /**
   * Creates the viewer for the specified URL. The viewer is not activated
   *
   * @param url
   *          the URL reference
   *
   * @return an instance of the viewer that was created or null if the viewer
   *         could no be created
   *
   * @throws MalformedURLException
   */
  public iViewer createViewer(String url) throws MalformedURLException {
    return createViewer(this, url);
  }

  /**
   * Creates the viewer for the specified viewer Action link. The viewer is not
   * activated
   *
   * @param link
   *          the link
   * @param cb
   *          the callback to call when the viewer has been created
   * @return a handle to a cancel-able
   *
   */
  public iCancelable createViewer(ActionLink link, iFunctionCallback cb) throws MalformedURLException {
    iWidget context = link.getContext();

    if (context == null) {
      context = this;
    }

    return ViewerCreator.createViewer(context, link, cb);
  }

  /**
   * Creates a viewer for a component
   *
   * @param context
   *          the widget context
   * @param comp
   *          the component
   * @return an instance of the viewer that was created
   */
  public iViewer createViewer(iWidget context, iPlatformComponent comp) {
    if (context == null) {
      context = this;
    }

    return new WidgetPaneViewer(comp);
  }

  /**
   * Creates the viewer for the specified URL. The viewer is not activated
   *
   * @param context
   *          the widget context
   * @param url
   *          the URL reference
   *
   * @return an instance of the viewer that was created or null if the viewer
   *         could no be created
   *
   * @throws MalformedURLException
   */
  public iViewer createViewer(iWidget context, String url) throws MalformedURLException {
    if (context == null) {
      context = this;
    }

    return windowManager.createViewer(context, createActionLink(context, url));
  }

  /**
   * Creates the viewer for the specified viewer configuration. The viewer is
   * not activated
   *
   * @param parent
   *          the parent viewer
   * @param cfg
   *          the viewer configuration
   *
   * @return an instance of the viewer that was created or null if the viewer
   *         could no be created
   */
  public iViewer createViewer(iWidget parent, Widget cfg) {
    return this.createViewer(parent, cfg, null);
  }

  /**
   * Creates the viewer for the specified URL. The viewer is not activated
   *
   * @param url
   *          the URL reference
   * @param cb
   *          the callback to call when the viewer has been created
   *
   * @return a handle to a cancel-able
   *
   * @throws MalformedURLException
   */
  public iCancelable createViewer(String url, iFunctionCallback cb) throws MalformedURLException {
    return ViewerCreator.createViewer(this, createActionLink(this, url), cb);
  }

  /**
   * Creates the viewer for the specified URL. The viewer is not activated
   *
   * @param context
   *          the widget context
   * @param url
   *          the URL reference
   *
   * @return a handle to a cancel-able
   *
   * @throws MalformedURLException
   */
  public iCancelable createViewer(iWidget context, String url, iFunctionCallback cb) throws MalformedURLException {
    if (context == null) {
      context = this;
    }

    return ViewerCreator.createViewer(context, createActionLink(context, url), cb);
  }

  /**
   * Creates the viewer for the specified viewer configuration. The viewer is
   * not activated
   *
   * @param parent
   *          the parent viewer
   * @param cfg
   *          the viewer configuration
   * @param context
   *          the context URL
   *
   * @return an instance of the viewer that was created or null if the viewer
   *         could no be created
   */
  public iViewer createViewer(iWidget parent, Widget cfg, URL context) {
    if (parent == null) {
      parent = this;
    }

    if (!(cfg instanceof Viewer)) {
      cfg = new WidgetPane(cfg);
    }

    return windowManager.createViewer(parent, null, (Viewer) cfg, context);
  }

  /**
   * Creates a widget for a component
   *
   * @param context
   *          the widget context
   * @param comp
   *          the component
   * @return an instance of the widget that was created
   */
  public iWidget createWidget(iWidget context, iPlatformComponent comp) {
    if (context == null) {
      context = this;
    }

    BeanWidget w = new BeanWidget(context.getContainerViewer(), comp);

    w.setParent(context.getContainerViewer());

    return w;
  }

  /**
   * Creates a widget
   *
   * @param context
   *          the widget context
   * @param type
   *          the widget type (use the name of the widget's configuration object, e.g. "Label")
   *
   * @return an instance of the widget that was created
   */
  public iWidget createWidget(iWidget context, String type) {
    if (context == null) {
      context = this;
    }

    Widget cfg = (Widget) createConfigurationObject(type);

    return createWidget(context.getContainerViewer(), cfg);
  }

  /**
   * Creates a widget
   *
   * @param context
   *          the widget context
   * @param cfg
   *          the widget configuration
   *
   * @return an instance of the widget that was created
   */
  public iWidget createWidget(iWidget context, Widget cfg) {
    if (context == null) {
      context = this;
    }

    return createWidget(context.getContainerViewer(), cfg);
  }

  /**
   * De-iconifies the window, setting it to its normal state
   */
  public void deiconify() {}

  @Override
  public void dispose() {
    if (!isDisposable()) {
      return;
    }

    if (isDisposed()) {
      return;
    }

    if (theWindow != null) {
      try {
        theWindow.dispose();
      } catch(Exception ignore) {}
    }

    if (windowTimer != null) {
      try {
        windowTimer.cancel();
      } catch(Exception ignore) {}
    }

    if (clearStatusTimer != null) {
      try {
        clearStatusTimer.cancel();
      } catch(Exception ignore) {}
    }

    if (theEvent != null) {
      theEvent.dispose();
    }

    try {
      super.dispose();
    } finally {
      theEvent         = null;
      timerEvent       = null;
      windowManager    = null;
      windowParent     = null;
      windowSize       = null;
      windowTimer      = null;
      scriptHandler    = null;
      clearStatusTimer = null;
      theWindow        = null;
      scriptHandler    = null;
    }
  }

  @Override
  public void disposeOfWindow() {
    dispose();
  }

  /**
   * Displays an error message
   *
   * @param message
   *          the object representing the error message
   */
  public void error(Object message) {
    error(null, message, null);
  }

  /**
   * Displays an error message
   *
   * @param message
   *          the object representing the error message
   * @param cb
   *          the callback to be notified when the dialog closes
   */
  public void error(Object message, iFunctionCallback cb) {
    error(null, message, cb);
  }

  /**
   * Displays an error message
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the object representing the error message
   * @param cb
   *          the callback to be notified when the dialog closes
   */
  public void error(String title, Object message, iFunctionCallback cb) {
    if (title == null) {
      title = Platform.getResourceAsString("Rare.runtime.text.errorTitle");
    }

    ErrorInformation ei = getErrorInformation(message, title);
    AlertPanel       d  = AlertPanel.error(this, ei);

    d.showDialog(cb);
  }

  /**
   * Provides error feedback to the user
   */
  public void errorFeedback() {
    UISoundHelper.errorSound();
  }

  /**
   * Evaluates the specified code and returns the results
   *
   * @param args
   *          the arguments ( code, [language] )
   *
   * @return the result of the evaluated code
   */
  public Object eval(Object... args) {
    iScriptHandler.iScriptRunnable r = createRunner(null, args[0], (args.length > 1)
            ? (String) args[1]
            : null);

    r.setHandleException(false);
    r.run();

    return r.getResult();
  }

  /**
   * Executes a named action. An exception is thrown if if a action with the
   * specified name is not found
   *
   * @param actionName
   *          the name of an action to execute
   */
  public void executeAction(String actionName) {
    UIAction a = getAppContext().getAction(actionName);

    if (a == null) {
      String s = getAppContext().getResourceAsString("Rare.runtime.text.undefinedAction");

      throw new ApplicationException(Helper.expandString(s, actionName));
    }

    if (a.isEnabled()) {
      ActionEvent ae = new ActionEvent(getDataComponent());

      a.actionPerformed(ae);
    }
  }

  /**
   * Creates a string url representation of the specified file
   *
   * @param file
   *          the file
   * @return the string URL representation
   * @throws java.net.MalformedURLException
   */
  public static String fileToURLString(File file) throws MalformedURLException {
    if (file == null) {
      return null;
    }

    return JavaURLConnection.toExternalForm(PlatformHelper.fileToURL(file));
  }

 
  /**
   * Formats the specified for display using the default date format
   *
   * @param date
   *          the date to format
   * @param time
   *          true to include the time in the output; false otherwise
   *
   * @return the formatted date
   */
  public String formatDate(Date date, boolean time) {
    if (time) {
      return getAppContext().getDefaultDateTimeContext().getDisplayFormat().format(date);
    } else {
      return getAppContext().getDefaultDateContext().getDisplayFormat().format(date);
    }
  }

  /**
   * Formats the specified for display using the specified date format
   *
   * @param date
   *          the date to format
   * @param format
   *          the format specifier
   *
   * @return the formatted date
   */
  public String formatDate(Date date, String format) {
    SimpleDateFormatEx df = new SimpleDateFormatEx(format);

    return df.format(date);
  }

  /**
   * Passes the exception to the default exception handler
   *
   * @param e
   *          the exception
   */
  @Override
  public void handleException(Throwable e) {
    if (!inExceptionHandler &&!isDisposed()) {
      try {
        inExceptionHandler = true;

        if (isEventEnabled(iConstants.EVENT_ERROR)) {
          DataEvent de = new DataEvent(getContainerComponent(), e);

          executeEvent(iConstants.EVENT_ERROR, de);

          if (de.isConsumed()) {
            return;
          }
        }
      } finally {
        inExceptionHandler = false;
      }
    }

    getAppContext().getDefaultExceptionHandler().handleException(e);
  }

  /**
   * Hides the progress popup
   */
  public void hideProgressPopup() {
    WaitCursorHandler.hideProgressPopup(getComponent(), false);
  }

  /**
   * Hides the progress popup
   *
   * @param force
   *          true to force the popup to be hidden even if the number of calls
   *          to showProgressPopup do not match the number of calls to
   *          hideWaitCursor
   */
  public void hideProgressPopup(boolean force) {
    WaitCursorHandler.hideProgressPopup(getComponent(), force);
  }

  /**
   * Attempts to hide the keyboard on devices that have virtual keyboards
   */
  public void hideVirtualKeyboard() {
    iPlatformComponent c = Platform.getAppContext().getFocusOwner();

    if (c == null) {
      c = Platform.getAppContext().getPermanentFocusOwner();
    }

    if (c == null) {
      c = getDataComponent();
    }

    PlatformHelper.hideVirtualKeyboard(c);
  }

  /**
   * Hides the wait cursor. The cursor will be removed from the screen when the
   * number of calls to hideWaitCursor matches the number of calls to
   * showWaitCursor
   */
  public void hideWaitCursor() {
    hideWaitCursor(false);
  }

  /**
   * Hides the wait cursor. The cursor will be removed from the screen when the
   * number of calls to hideWaitCursor matches the number of calls to
   * showWaitCursor
   *
   * @param force
   *          true to force the cursor to be hidden even if the number of calls
   *          to showWaitCursor do not match the number of calls to
   *          hideWaitCursor
   */
  public void hideWaitCursor(boolean force) {
    WaitCursorHandler.stopWaitCursor(getComponent(), force);
  }

  @Override
  public void hideWindow() {
    if (theWindow != null) {
      theWindow.hideWindow();
    }
  }

  /**
   * Iconifies the window
   */
  public void iconify() {}

  /**
   * Invokes the specified code to run after all pending UI events have been
   * processed. Unlike spawn, this uses the same thread that processes UI
   * events. The code will be executed within the context of the calling current
   * event from which it is being called
   *
   * @param code
   *          the code to invoke
   */
  public void invokeLater(Object code) {
    if (code instanceof Runnable) {
      Platform.invokeLater((Runnable) code);

      return;
    }

    invokeLater(code, null);
  }

  /**
   * Invokes the specified code to run after all pending UI events have been
   * processed. Unlike spawn, this uses the same thread that processes UI
   * events. A new DataEvent will be fired with the data for the event being the
   * specified data
   *
   * @param code
   *          the code to invoke
   * @param data
   *          the data to pass to the called code via a DataEvent
   */
  public void invokeLater(Object code, Object data) {
    invokeLater(null, code, data);
  }

  /**
   * Invokes the specified code to run after all pending UI events have been
   * processed. Unlike spawn, this uses the same thread that processes UI
   * events. A new DataEvent will be fired with the data for the event being the
   * specified data
   *
   * @param context
   *          the context for running the code
   * @param code
   *          the code to invoke
   * @param data
   *          the data to pass to the called code via a DataEvent
   */
  public void invokeLater(iWidget context, Object code, Object data) {
    iScriptHandler sh = (context == null)
                        ? theEvent.getScriptHandler()
                        : context.getScriptHandler();

    if (sh == null) {
      sh = this.getScriptHandler();
    }

    if (sh.getWindowViewer() != this) {
      sh      = getScriptHandler();
      context = this;
    }

    ScriptingEvent e  = ((WindowViewer) sh.getWindowViewer()).getEvent();
    WidgetContext  wc = (context == null)
                        ? e.getSourceContext()
                        : context.getScriptingContext();

    if (wc == null) {
      wc = this.getScriptingContext();
    }

    if (data == null) {
      e = (ScriptingEvent) e.clone();
    } else {
      e = new ScriptingEvent(sh, iConstants.EVENT_INVOKE_LATER, new DataEvent(wc.getWidget(), data), e.getSource(),
                             e.getRelatedTarget());
    }

    e.setInvokeLater(true);
    Platform.invokeLater(sh.createRunner(wc, code, e));
  }

  /**
   * Loads an executes a script
   *
   * @param link
   *          the link to the script
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void loadScript(ActionLink link) throws IOException {
    loadScriptEx(link, false, false, false);
  }

  /**
   * Loads and executes a script
   *
   * @param url
   *          the url to the script
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void loadScript(String url) throws IOException {
    loadScriptEx(url, false, true, true);
  }

  /**
   * Loads and executes a script
   *
   * @param link
   *          the link to the script
   * @param strict
   *          true for strict mode; false otherwise
   * @param optimized
   *          true for optimized mode; false otherwise
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void loadScript(ActionLink link, boolean strict, boolean optimized) throws IOException {
    loadScriptEx(link, true, strict, optimized);
  }

  /**
   * Loads an executes a script
   *
   * @param url
   *          the url to the script
   * @param strict
   *          true for strict mode; false otherwise
   * @param optimized
   *          true for optimized mode; false otherwise
   *
   * @throws IOException
   *           if an I/O error occurs
   */
  public void loadScript(String url, boolean strict, boolean optimized) throws IOException {
    loadScriptEx(url, true, strict, optimized);
  }

  /**
   * Opens the system mail client
   *
   * @param uri
   *          the mailto: uri or email address (can be null)
   *
   * @return true if the mail client was activated; false otherwise
   * @throws URISyntaxException
   *           if the URI syntax is invalid
   */
  public boolean mailTo(String uri) {
    return Platform.mailTo(uri);
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
   * @throws MalformedURLException
   *           if the URL is malformed
   * @throws URISyntaxException
   *           if the URI syntax is invalid
   */
  public boolean mailTo(String address, String subject, String body) {
    return Platform.mailTo(address, subject, body);
  }

  /**
   * Maximizes the window
   */
  public void maximize() {}

  /**
   * Iconifies the window
   */
  public void minimize() {}

  @Override
  public void moveBy(float x, float y) {
    theWindow.moveBy(x, y);
  }

  @Override
  public void moveTo(float x, float y) {
    theWindow.moveTo(x, y);
  }

  /**
   * Returns the number nanoseconds of elapsed time
   *
   * @return the number nanoseconds of elapsed time
   */
  public long nanoTime() {
    return System.nanoTime();
  }

  /**
   * Restores the window to is normal size
   */
  public void normal() {}

  /**
   * Returns a new <code>Date</code> object that represents the current
   * date/time
   *
   * @return a new <code>Date</code> object that represents the current
   *         date/time
   */
  public Date now() {
    return new Date();
  }

  /**
   * Displays a ok/cancel dialog box, allowing the user to choose one of those
   * options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   *
   */
  public void okCancel(Object message, iFunctionCallback cb) {
    okCancel(null, message, cb);
  }

  /**
   * Displays a ok/cancel dialog box, allowing the user to choose one of those
   * options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   *
   */
  public void okCancel(String title, Object message, iFunctionCallback cb) {
    title = (title == null)
            ? getTitle()
            : expandString(title, false);

    if (message instanceof String) {
      message = expandString((String) message, false);
    }

    AlertPanel d = AlertPanel.okCancel(this, title, message, null);
    if(!Platform.isTouchDevice()) {
      d.setRightAlignButtons(true);
    }

    d.showDialog(cb);
  }

  /**
   * Opens a new window to host the specified url
   *
   * @param url
   *          the URL reference
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer open(String url) throws IOException {
    return open(url, null, null);
  }

  /**
   * Opens a new window to host the specified viewer
   *
   * @param cfg
   *          the viewer configuration
   * @param winoptions
   *          a string or window options
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer open(Viewer cfg, String winoptions) throws IOException {
    ActionLink link = new ActionLink();

    link.setViewerConfiguration(cfg);

    return open(link, winoptions, null);
  }

  /**
   * Opens a new window to host the specified link
   *
   * @param link
   *          the action link
   * @param options
   *          a map of window options
   * @param viewerValue
   *          a value to set for the viewer. The value can be retrieved vial a
   *          call to viewer.getValue()
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer open(ActionLink link, Map options, Object viewerValue) throws IOException {
    iWidget w = this;
    Object  o = theEvent.getRelatedTarget();

    if (o instanceof iViewer) {
      iViewer ww = (iViewer) o;

      if (ww.getWindow() != null) {
        w = ww;
      }
    }

    link = (ActionLink) link.clone();
    link.setURL(link.getURL(this));
    link.setContext(w);
    link.setWindowOptions(options);

    iViewer v = windowManager.openViewerWindow(link, viewerValue);

    if (v instanceof WindowViewer) {
      return (WindowViewer) v;
    }

    return (v == null)
           ? null
           : v.getWindow();
  }

  /**
   * Opens a new window to host the specified link
   *
   * @param link
   *          the action link
   * @param winoptions
   *          a string of window options
   * @param viewerValue
   *          a value to set for the viewer. The value can be retrieved vial a
   *          call to viewer.getValue()
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer open(ActionLink link, String winoptions, Object viewerValue) throws IOException {
    Map options = CharScanner.parseOptionStringEx(winoptions, ',');

    return open(link, options, viewerValue);
  }

  /**
   * Opens a new window to host the specified url
   *
   * @param url
   *          the URL reference
   * @param winoptions
   *          a string or window options
   * @param viewerValue
   *          a value to set for the viewer represented by the link the value
   *          can be retrieved vial a call to viewer.getValue()
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer open(String url, String winoptions, Object viewerValue) throws IOException {
    return open(new ActionLink(null, url, null), winoptions, viewerValue);
  }

  /**
   * Opens a new modal dialog box to host the specified viewer The dialog box
   * will be centered relative to its parent window
   *
   * @param v
   *          the viewer reference
   *
   * @return a handle to the newly opened window
   */
  public WindowViewer openDialog(iViewer v) {
    return v.showAsDialog(null, true);
  }

  /**
   * Opens a new modal dialog box to host the specified url The dialog box will
   * be centered relative to its parent window
   *
   * @param url
   *          the URL reference
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer openDialog(String url) throws IOException {
    return open(url, "windowtype=dialog,modal=true", null);
  }

  /**
   * Opens a new modal dialog box to host the specified link The dialog box will
   * be centered relative to its parent window
   *
   * @param link
   *          the link reference
   * @param title
   *          the title for the dIalog box
   *
   * @param viewerValue
   *          a value to set for the viewer represented by the link the value
   *          can be retrieved vial a call to viewer.getValue()
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer openDialog(ActionLink link, String title, Object viewerValue) throws IOException {
    if (title == null) {
      return open(link, "windowtype=dialog,modal=true", viewerValue);
    } else {
      return open(link, "windowtype=dialog,modal=true,title='" + title + "'", viewerValue);
    }
  }

  /**
   * Opens a new modal dialog box to host the specified url The dialog box will
   * be centered relative to its parent window
   *
   * @param url
   *          the URL reference
   * @param title
   *          the title for the dIalog box
   *
   * @param viewerValue
   *          a value to set for the viewer represented by the link the value
   *          can be retrieved vial a call to viewer.getValue()
   *
   * @return a handle to the newly opened window
   * @throws IOException
   *           if an I/O error occurs
   */
  public WindowViewer openDialog(String url, String title, Object viewerValue) throws IOException {
    if (title == null) {
      return open(url, "windowtype=dialog,modal=true", viewerValue);
    } else {
      return open(url, "windowtype=dialog,modal=true,title='" + title + "'", viewerValue);
    }
  }

  @Override
  public void pack() {
    theWindow.pack();
  }

  /**
   * Displays a dialog box that prompts the user for input
   * <p>
   * <b>Note:</b> The return value for this function is only valid on platforms
   * where modal dialogs block. For portability you should use the callback
   * mechanism.
   * </p>
   *
   * @param prompt
   *          the prompt for the input field
   *
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   */
  public void prompt(String prompt, iFunctionCallback cb) {
    prompt(null, prompt, null, cb);
  }

  /**
   * Displays a dialog box that prompts the user for input
   * <p>
   * <b>Note:</b> The return value for this function is only valid on platforms
   * where modal dialogs block. For portability you should use the callback
   * mechanism.
   * </p>
   *
   * @param prompt
   *          the prompt for the input field
   * @param value
   *          the default value for the input field
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   */
  public void prompt(String prompt, Object value, iFunctionCallback cb) {
    prompt(null, prompt, value, cb);
  }

  /**
   * Displays a dialog box that prompts the user for input
   * <p>
   * <b>Note:</b> The return value for this function is only valid on platforms
   * where modal dialogs block. For portability you should use the callback
   * mechanism.
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param prompt
   *          the prompt for the input field
   * @param value
   *          the default value for the input field
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   *
   */

  /**
   * Displays a dialog box that prompts the user for input
   *
   * @param title
   *          the title for the dialog box
   * @param prompt
   *          the prompt for the input field
   * @param value
   *          the default value for the input field
   * @param cb
   *          the callback to be notified when the dialog closes and receive the return value
   *
   */
  public void prompt(String title, String prompt, Object value, final iFunctionCallback cb) {
    if (cb == null) {
      throw new NullPointerException("callback cannot be null");
    }

    title  = (title == null)
             ? getTitle()
             : expandString(title.toString(), false);
    prompt = expandString(prompt.toString(), false);
    toFront();

    if (title == null) {
      title = windowManager.getTitle();
    }

    if (value == null) {
      value = "";
    }

    AlertPanel d = AlertPanel.prompt(this, title, prompt, value, null);
    if(!Platform.isTouchDevice()) {
      d.setRightAlignButtons(true);
    }

    d.showDialog(cb);
  }

  /**
   * Register a collection handler
   *
   * @param name
   *          the name of the collection handler
   * @param ch
   *          the collection handler object
   */
  public void registerCollectionHandler(String name, iDataCollectionHandler ch) {
    getAppContext().registerCollectionHandler(name, ch);
  }

  @Override
  public void reloadForm() {}

  /**
   * Removes an overlay component from the window.
   *
   * @param c
   *          the component
   */
  public void removeOverlay(iPlatformComponent c) {
    if (theWindow instanceof Frame) {
      ((Frame) theWindow).removeOverlay(c);
    }
  }

  /**
   * Removes an overlay widget from the window.
   *
   * @param w
   *          the widget
   */
  public void removeOverlay(iWidget w) {
    if (w != null) {
      removeOverlay(w.getContainerComponent());
    }
  }

  /**
   * Removes the specified widget as a dragger for the window
   *
   * @param widget
   *          the widget
   */
  public abstract void removeWindowDragger(iWidget widget);

  @Override
  public void removeWindowListener(iWindowListener l) {
    theWindow.removeWindowListener(l);
  }

  /**
   * Resizes the window by the specified amount
   *
   * @param width
   *          the amount to add to/subtract from the window's width
   * @param height
   *          the amount to add to/subtract from the window's height
   */
  public void resizeBy(int width, int height) {
    UIDimension d = theWindow.getSize();

    height += d.height;
    width  += d.width;
    theWindow.setSize(width, height);
  }

  /**
   * Resizes the window
   *
   * @param width
   *          the new width
   * @param height
   *          the new height
   */
  public void resizeTo(int width, int height) {
    theWindow.setSize(width, height);
  }

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
  public iCancelable sendFormData(iWidget context, ActionLink link, Map<String, Object> data, boolean multipart,
                                  ReturnDataType returnType, iFunctionCallback cb) {
    return Platform.sendFormData(context, link, data, multipart, returnType, cb);
  }

  /**
   * Shows a progress popup (a indeterminate progress icon plus a message)
   * @param message the message to display
   *
   */
  public void showProgressPopup(CharSequence message) {
    showProgressPopup(message, null);
  }

  /**
   * Shows a progress popup (a indeterminate progress icon plus a message)
   * @param message the message to display
   * @param cancelable a cancel-able to call is the operation can be canceled and the user cancels it.
   *
   */
  public void showProgressPopup(CharSequence message, iCancelable cancelable) {
    WaitCursorHandler.showProgressPopup(getComponent(), message, cancelable);
  }

  /**
   * Shows the wait cursor
   */
  public void showWaitCursor() {
    showWaitCursor(null);
  }

  /**
   * Shows the wait cursor
   *
   * @param cancelable a cancel-able to call is the operation can be canceled and the user cancels it.
   */
  public void showWaitCursor(iCancelable cancelable) {
    WaitCursorHandler.startWaitCursor(getComponent(), cancelable);
  }

  /**
   * Shows the window
   */
  @Override
  public void showWindow() {
    theWindow.showWindow();
  }

  /**
   * Shows the window at the specified location
   *
   * @param x
   *          the x position
   * @param y
   *          the y position
   */
  @Override
  public void showWindow(int x, int y) {
    moveTo(x, y);
    showWindow();
  }

  /**
   * Spawn a task to be executed in the background
   *
   * @param args
   *          the arguments ( code, [language] )
   * @return a Future representing pending completion of the task
   */
  public iCancelableFuture spawn(Object... args) {
    if (args[0] instanceof Runnable) {
      return appContext.executeBackgroundTask((Runnable) args[0]);
    } else if (args[0] instanceof Callable) {
      return appContext.executeBackgroundTask((Callable) args[0]);
    } else if (args[0] instanceof iWorkerTask) {
      return appContext.executeWorkerTask((iWorkerTask) args[0]);
    } else {
      return appContext.executeBackgroundTask(createRunner(null, args[0], (args.length > 1)
              ? (String) args[1]
              : null));
    }
  }

  public void submitForm() {}

  @Override
  public void toBack() {
    theWindow.toBack();
  }

  @Override
  public void toFront() {
    theWindow.toFront();
  }

  /**
   * Updates the currently showing progress popup with a new message
   * @param message the message to display
   *
   */
  public void updateProgressPopup(CharSequence message) {
    WaitCursorHandler.updateProgressPopup(getComponent(), message);
  }

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
  public iCancelable uploadData(iWidget context, ActionLink link, Object data, String name, String mimeType,
                                String fileName, ReturnDataType returnType, iFunctionCallback cb) {
    return Platform.uploadData(context, link, data, name, mimeType, fileName, returnType, cb);
  }

  /**
   * Displays a yes/no dialog box, allowing the user to choose one of those
   * options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   */
  public void yesNo(Object message, iFunctionCallback cb) {
    yesNo(null, message, null, null, cb);
  }

  /**
   * Displays a yes/no dialog box, allowing the user to choose one of those
   * options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   *
   */
  public void yesNo(String title, Object message, iFunctionCallback cb) {
    yesNo(title, message, null, null, cb);
  }

  /**
   * Displays a yes/no dialog box, allowing the user to choose one of those
   * options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value
   *
   */
  public void yesNo(String title, Object message, String yes, String no, iFunctionCallback cb) {
    if (cb == null) {
      throw new NullPointerException("callback cannot be null");
    }

    title = (title == null)
            ? getTitle()
            : expandString(title, false);

    if (message instanceof String) {
      message = expandString((String) message, false);
    }

    AlertPanel d = AlertPanel.yesNo(this, title, message, null, yes, no, false);

    d.showDialog(cb);
  }

  /**
   * Displays a yes/no/cancel dialog box, allowing the user to choose one of
   * those options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value (a <code>Boolean</code> object representing yes or no or a null
   *         value representing cancel)
   */
  public void yesNoCancel(Object message, iFunctionCallback cb) {
    yesNoCancel(null, message, null, null, cb);
  }

  /**
   * Displays a yes/no/cancel dialog box, allowing the user to choose one of
   * those options
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value ( a <code>Boolean</code> object representing yes or no or a null
   *         value representing cancel)
   */
  public void yesNoCancel(String title, Object message, iFunctionCallback cb) {
    yesNoCancel(title, message, null, null, null, cb);
  }

  public void yesNoCancel(String title, Object message, String yes, String no, iFunctionCallback cb) {
    yesNoCancel(title, message, yes, no, null, cb);
  }

  /**
   * Displays a yes/no/cancel dialog box, allowing the user to choose one of
   * those options.
   * <p>
   * Calls back with a <code>Boolean</code> object representing yes or no or a
   * null value representing cancel
   * </p>
   *
   * @param title
   *          the title for the dialog box
   * @param message
   *          the message to display
   * @param yes the string for the yes button
   * @param no the string for the no button
   * @param cancel the string for the cancel button
   * @param cb
   *          the callback to be notified when the dialog closes and receive the
   *          return value (a <code>Boolean</code> object representing yes or no or a null
   *         value representing cancel)
   */
  public void yesNoCancel(String title, Object message, String yes, String no, String cancel, iFunctionCallback cb) {
    title = (title == null)
            ? getTitle()
            : expandString(title, false);

    if (message instanceof String) {
      message = expandString((String) message, false);
    }

    AlertPanel d = AlertPanel.yesNoCancel(this, title, message, null, yes, no, cancel);
    if(!Platform.isTouchDevice()) {
      d.setRightAlignButtons(true);
    }

    d.showDialog(cb);
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    theWindow.setBounds(x, y, width, height);
  }

  /**
   * Sets whether the window can be closed This is meant to be called during an
   * <code>onWillClose </code> event for the document Calling
   * <code>setCanClose(false) </code> will prevent the document from closing
   *
   * @param can
   *          true if the window can be closed; false otherwise.
   */
  @Override
  public void setCanClose(boolean can) {
    canClose = can;
  }

  /**
   * Sets whether a dialog window can canceled by the default cancel
   * sequence (escape key, back button etc.)
   *
   * @param cancelable
   *          true if the window can be canceled; false otherwise.
   */
  public abstract void setCancelable(boolean cancelable);
  
  /**
   * Set a cookie-2 value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public void setCookie2Value(URL url, String value) {
    Platform.setCookie2Value(url, value);
  }

  /**
   * Set a cookie value for the given URL
   *
   * @param url
   *          the URL
   * @param value
   *          the cookie value
   */
  public void setCookieValue(URL url, String value) {
    Platform.setCookieValue(url, value);
  }

  /**
   * Sets the <code>defaultButton</code> property, which determines the current
   * default button for this window The default button is the button which will
   * be activated when a UI-defined activation event (typically the <b>Enter</b>
   * key) occurs in the root pane regardless of whether or not the button has
   * keyboard focus (unless there is another component within the root pane
   * which consumes the activation event, such as a <code>JTextPane</code>). For
   * default activation to work, the button must be an enabled descendent of the
   * root pane when activation occurs. To remove a default button from this root
   * pane, set this property to <code>null</code>.
   *
   * @param widget
   *          the pushbutton widget which is to be the default button
   */
  public abstract void setDefaultButton(PushButtonWidget widget);

  /**
   * Set the current event for the window
   *
   * @param e
   *          the event
   */
  public void setEventEx(ScriptingEvent e) {
    theEvent = e;
  }


  /**
   * Sets the status bar insert/overwrite mode indicator
   *
   * @param overwrite
   *          true for overwrite; false for insert
   */
  public void setInsertOverwrite(boolean overwrite) {
    iStatusBar sb = theWindow.getStatusBar();

    if (sb != null) {
      sb.setInsertOverwrite(overwrite);
    }
  }

  /**
   * Executes the specified code at specified intervals (in milliseconds).
   * Execution will continue as the specified interval until clearInterval() is
   * called or the window is closed. The return value returned is used as the
   * parameter for the clearInterval() method.
   *
   * @param code
   *          the code to execute
   * @param interval
   *          the number of milliseconds to wait before and between executions
   * @return a handle to the scheduled task can be canceled
   */
  public iCancelable setInterval(Object code, long interval) {
    return scheduleTask("setInterval", code, interval, true);
  }

  @Override
  public void setLocation(float x, float y) {
    theWindow.setLocation(x, y);
  }

  @Override
  public void setMenuBar(iPlatformMenuBar mb) {
    theWindow.setMenuBar(mb);
  }

  /**
   * Sets the relative font size for the application
   *
   * @param size
   *          the relative size (1.0 = normal size)
   */
  public void setRelativeFontSize(float size) {
    iPlatformAppContext app = getAppContext();

    if (app != null) {
      iWindowManager wm = app.getWindowManager();

      wm.setRelativeFontSize(size);
    }
  }

  @Override
  public void setResizable(boolean resizable) {
    theWindow.setResizable(resizable);
  }

  /**
   * Sets the message displayed on the status bar
   *
   * @param status
   *          the message displayed on the status bar
   */
  public void setStatus(String status) {
    if (clearStatusTimer != null) {
      try {
        clearStatusTimer.cancel();
      } catch(Exception e) {}

      clearStatusTimer = null;
    }

    iStatusBar sb = theWindow.getStatusBar();

    if (sb != null) {
      sb.showMessage(status);
    }
    else if(Platform.getUIDefaults().getBoolean("Rare.StatusBar.useNotifier", true)) {
      UINotifier.showMessage(status);
    }
  }

  @Override
  public void setStatusBar(iStatusBar sb) {
    theWindow.setStatusBar(sb);
  }

  /**
   * Sets the value of a status bar item
   *
   * @param item
   *          the name of the item
   * @param value
   *          the value for the item
   */
  public void setStatusItem(String item, String value) {
    iStatusBar sb = theWindow.getStatusBar();

    if (sb != null) {
      sb.setItemValue(item, value);
    }
  }

  /**
   * Enable or disables a status bar item
   *
   * @param item
   *          the name of the item
   * @param enabled
   *          true to enable; false otherwise
   */
  public void setStatusItemEnabled(String item, boolean enabled) {
    iStatusBar sb = theWindow.getStatusBar();

    if (sb != null) {
      sb.setItemEnabled(item, enabled);
    }
  }

  /**
   * Sets the message displayed on the status bar and then clears the status bar
   * after the specified number of seconds has elapsed. The status will be
   * clEared it the bar still contains the specified text after the time has
   * elapsed
   *
   * @param status
   *          the message displayed on the status bar
   * @param seconds
   *          the number of seconds after which the status bar should be cleared
   *          use -1 to have the default behavior
   */
  public void setThenClearStatus(final String status, int seconds) {
    final iStatusBar sb = theWindow.getStatusBar();

    if (sb == null) {
      return;
    }

    if (clearStatusTimer != null) {
      try {
        clearStatusTimer.cancel();
      } catch(Exception e) {}

      clearStatusTimer = null;
    }

    sb.showMessage(status);

    final Runnable r = new Runnable() {
      @Override
      public void run() {
        if ((clearStatusTimer != null) && (sb.getComponent() != null) && status.equals(sb.getMessage())) {
          sb.showMessage(null);
          clearStatusTimer = null;
        }
      }
    };

    if (seconds == -1) {
      seconds = 15;
    }

    if (seconds > 0) {
      iTimer t = Platform.createTimer("clearStatusTimer");

      t.schedule(new Runnable() {
        @Override
        public void run() {
          Platform.invokeLater(r);
        }
      }, seconds * 1000);
      clearStatusTimer = t;
    }
  }

  /**
   * Executes the specified code after the specified number of milliseconds has
   * elapsed
   *
   * @param code
   *          the code to execute
   * @param timeout
   *          the number of milliseconds to wait
   * @return a handle to the scheduled task can be canceled by calling
   *         <code>cancel() on the handle </code>
   */
  public iCancelable setTimeout(Object code, long timeout) {
    return scheduleTask("setTimeout", code, timeout, false);
  }

  @Override
  public void setTitle(String title) {
    theWindow.setTitle(title);
  }

  @Override
  public void setToolBarHolder(iToolBarHolder tbh) {
  }

  /**
   * Returns the viewer with the specified name
   *
   * @param name
   *          the name of the viewer
   * @return the viewer with the specified name
   */
  @Override
  public Object get(String name) {
    return getAppContext().getViewer(name);
  }

  /**
   * Returns the action with the specified name
   *
   * @param name
   *          the name of the action
   *
   * @return the action with the specified name or null if an action with the
   *         specified name does not exist
   */
  public UIAction getAction(String name) {
    return getAppContext().getAction(name);
  }

  /**
   * Returns the windows action bar if it has one
   * @return the windows action bar or null;
   */
  public ActionBar getActionBar() {
    iPlatformMenuBar mb = getMenuBar();

    if ((mb != null) && (mb.getMenuBarComponent() instanceof ActionBar)) {
      return (ActionBar) mb.getMenuBarComponent();
    }

    return null;
  }

  /**
   * Returns the map of dynamically loaded actions. Only actions loaded via an
   * action properties URL are available via this map
   *
   * @return the map of application configured actions
   */
  public Map<String, UIAction> getActions() {
    return getAppContext().getActions();
  }

  /**
   * Returns the application's main URL
   *
   * @return the application's main URL
   */
  public URL getApplicationURL() {
    return getAppContext().getApplicationURL();
  }

  @Override
  public UIRectangle getBounds() {
    return theWindow.getBounds();
  }

  /**
   * Returns the contents of the system clipboard as a string. Return null if
   * the clipboard is empty of if its contents cannot be represented as a
   * string.
   *
   * @return the contents of the system clipboard or null
   */
  public abstract String getClipboardContents();

  /**
   * Returns the URL for the codebase for an application launched via webstart
   *
   * @return the URL for codebase or null if the app was not launched via
   *         webstart
   */
  public URL getCodeBase() {
    return getAppContext().getCodeBase();
  }

  @Override
  public iPlatformComponent getComponent() {
    return theWindow.getComponent();
  }
  
  @Override
  public iTarget getTarget() {
    return theWindow.getTarget();
  }
  
  /**
   * Gets the workspace viewer for the window
   *
   * @return the workspace viewer
   */
  public iViewer getWorkspaceViewer() {
    return theWindow.getTarget().getViewer();
  }
  
  /**
   * Get the content, in the requested format, for the specified link via a
   * background task.
   *
   * If successful, the returned object is an instance of an
   * <code>ObjectHolder</code> where the <code>type</code> is the mime type and
   * the <code>value</code> is the content and the source is the link
   *
   * @param url
   *          the url representing the data to be retrieved (can be a string, url, file, or action link)
   * @param returnType the type of data to return. The is an instance ActionLink.ReturnDataType or the string name of the tupe
   * @param cb
   *          the callback to call when the data has been retrieved
   * @return a handle to use to be able to cancel the operation;
   * @throws MalformedURLException
   *
   * @see com.appnativa.util.ObjectHolder
   */
  public iCancelable getContent(iWidget context, Object url, Object returnType, final iFunctionCallback cb)
          throws MalformedURLException {
    ActionLink.ReturnDataType type = null;

    if (returnType instanceof ActionLink.ReturnDataType) {
      type = (ActionLink.ReturnDataType) returnType;
    } else {
      type = ActionLink.ReturnDataType.valueOf(returnType.toString().toUpperCase(Locale.US));
    }

    return Platform.getContent((context == null)
                               ? null
                               : this, createActionLink(url), type, cb);
  }

  /**
   * Calls <code>getContentAsString</code> on the specified link via a
   * background task and then converts the string to a JSONObject.
   * If successful, the returned object is an instance of an
   * <code>ObjectHolder</code> where the <code>key</code> is the mime type and
   * the <code>value</code> is the content.
   *
   * @param url
   *          the url representing the data to be retrieved (can be a string, url, file, or action link)
   * @param cb
   *          the callback to call when the data has been retrieved
   * @return a handle to use to be able to cancel the operation;
   * @throws MalformedURLException
   *
   * @see com.appnativa.util.ObjectHolder
   */
  public iCancelable getContentAsJSON(Object url, iFunctionCallback cb) throws MalformedURLException {
    return Platform.getContent(this, createActionLink(url), ActionLink.ReturnDataType.JSON, cb);
  }

  /**
   * Parses the data of the  the specified link  into as list of <code>RenderableDataItem</code> objects, via a
   * background task. If successful, the returned object is an instance of an
   * <code>ObjectHolder</code> where the <code>type</code> is the mime type and
   * the <code>value</code> is the content and the source is the link
   * @param context to context widget
   * @param url
   *          the url representing the data to be retrieved (can be a string, url, file, or action link)
   * @param cb
   *          the callback to call when the data has been retrieved
   * @return a handle to use to be able to cancel the operation;
   *
   * @see com.appnativa.util.ObjectHolder
   */
  public iCancelable getContentAsList(iWidget context, Object url, final boolean tabular, final iFunctionCallback cb)
          throws MalformedURLException {
    ActionLink.ReturnDataType type = tabular
                                     ? ActionLink.ReturnDataType.TABLE
                                     : ActionLink.ReturnDataType.LIST;

    return Platform.getContent((context == null)
                               ? this
                               : context, createActionLink(context, url), type, cb);
  }

  /**
   * Calls <code>getContentAsString</code> on the specified link via a
   * background task. If successful, the returned object is an instance of an
   * <code>ObjectHolder</code> where the <code>type</code> is the mime type and
   * the <code>value</code> is the content.
   *
   * @param url
   *          the url representing the data to be retrieved (can be a string, url, file, or action link)
   * @param cb
   *          the callback to call when the data has been retrieved
   * @return a handle to use to be able to cancel the operation;
   * @throws MalformedURLException
   *
   * @see com.appnativa.util.ObjectHolder
   */
  public iCancelable getContentAsString(Object url, iFunctionCallback cb) throws MalformedURLException {
    return Platform.getContent(this, createActionLink(url), ActionLink.ReturnDataType.STRING, cb);
  }

  @Override
  public URL getContextURL() {
    return contextURL;
  }

  /**
   * Returns the list of currently set cookie objects Call cookie.getName() and
   * cookie.getValue() on a list item to get the cookie's name and value. Call
   * getHeader() the get the actual HTTP header value that was passed in
   *
   * @return the list of currently set cookie objects
   */
  public List getCookieList() {
    return Platform.getCookieList();
  }

  /**
   * Returns a string representing the current session cookies
   *
   * @return a string representing the current session cookies
   */
  public String getCookies() {
    return Platform.getCookies();
  }

  /**
   * Gets a predefined system cursor or a custom cursor that was registered with
   * the UIManager using
   * <code>Platform.getUIDefaults().put(cursorName,cursor)</code>
   *
   * The predefined cursor names are:
   * <ul>
   * <li>DEFAULT - The default cursor</li>
   * <li>CROSSHAIR - The cross-hair cursor</li>
   * <li>E-RESIZE - The east-resize cursor</li>
   * <li>HAND - The hand cursor</li>
   * <li>MOVE - The move cursor</li>
   * <li>NE-RESIZE - The north-east-resize cursor</li>
   * <li>NESW-RESIZE - The north-east/south-west-resize cursor</li>
   * <li>NW-RESIZE - The north-west-resize cursor</li>
   * <li>NWSE-RESIZE - The north-west/south-east-resize cursor</li>
   * <li>NONE - Represents no cursor</li>
   * <li>N-RESIZE - The north-resize cursor</li>
   * <li>POINTER - The pointer cursor</li>
   * <li>SE-RESIZE - The south-east-resize cursor</li>
   * <li>SW-RESIZE - The south-west-resize cursor</li>
   * <li>S-RESIZE - The south-resize cursor</li>
   * <li>TEXT -The text cursor</li>
   * <li>WAIT -The wait cursor</li>
   * <li>W-RESIZE - The west-resize cursor</li>
   * </ul>
   *
   * @param name
   *          the name of the cursor
   * @return the cursor
   */
  public UICursor getCursor(String name) {
    return UICursor.getCursor(name);
  }

  /**
   * Gets the data collection with the specified name
   *
   * @param name
   *          the name of the collection
   *
   * @return the data collection with the specified name or null
   */
  public iDataCollection getDataCollection(String name) {
    return getAppContext().getDataCollection(name);
  }

  /**
   * Gets an icon via a background thread and and displays the specified image
   * while the icon is loading
   *
   * @param url
   *          the url for the icon
   * @param delayedIcon
   *          the icon to use during the load delay
   * @param startLoading
   *          true to startLoading now; false to load when the run method is
   *          invoked on the icon
   * @param constraints
   *          delayed icon constraints 0=do nothing; 1=constrain width to
   *          delayed icon width; 2=constrain height to delayed icon height;
   *          3=constrain width and height to fit within delayed icon size (will
   *          maintain proportions) 4=force size to delayed icon size
   *
   * @return the icon
   */
  public UIImageIcon getDelayedIcon(URL url, aUIImageIcon delayedIcon, int constraints, boolean startLoading) {
    UIImageIcon icon = new UIImageIcon(url, JavaURLConnection.toExternalForm(url), delayedIcon, constraints);

    if (startLoading) {
      PlatformHelper.loadIcon(getAppContext(), icon);
    }

    return icon;
  }

  /**
   * Gets an icon via a background thread and and displays the specified The
   * image will be constrained to fit within delayed icon size with its maintain
   * proportions maintained. Any empty space will be filled with the specified
   * color
   *
   * @param url
   *          the url for the icon
   * @param delayedIcon
   *          the icon to use during the load delay
   * @param bg
   *          the background color to use to fill empty space
   * @param startLoading
   *          true to startLoading now; false to load when the run method is
   *          invoked on the icon
   * @return the icon
   */
  public UIImageIcon getDelayedIcon(URL url, aUIImageIcon delayedIcon, UIColor bg, boolean startLoading) {
    UIImageIcon icon = new UIImageIcon(url, JavaURLConnection.toExternalForm(url), delayedIcon, bg);

    if (startLoading) {
      PlatformHelper.loadIcon(getAppContext(), icon);
    }

    return icon;
  }

  /**
   * Gets an image via a background thread
   *
   * @param url
   *          the url for the icon
   *
   * @return the image
   * @throws IOException
   */
  public UIImage getDelayedImage(URL url) throws IOException {
    return UIImageHelper.createImage(url, true, 0);
  }

  /**
   * Gets an image via a background thread
   *
   * @param url
   *          the url for the icon
   * @param size
   *          the size constraints
   * @param constraints
   *          delayed icon constraints 0=do nothing; 1=constrain width to the
   *          specified size 2=constrain height to the specified size
   *          3=constrain width and height to fit within the specified size
   *          (will maintain proportions) 4=constrain width and height to the
   *          specified size 5=constrain width and height to fill the specified
   *          size (will maintain proportions)
   *
   * @param st
   *          the type of scaling to do (null for bilinear)
   * @param bg
   *          the background color to use to fill empty space
   * @return the image
   * @throws IOException
   */
  public abstract UIImage getDelayedImage(URL url, int size, int constraints, ScalingType st, UIColor bg)
          throws IOException;

  public float getDevicePixelRatio() {
    return ScreenUtils.getPixelMultiplier();
  }

  /**
   * Returns an object containing information about a runtime error. The
   * returned object can be passed directly to the error() method for display to
   * the user.
   *
   * @param error
   *          the runtime error
   * @return the error information object
   * @see #error(java.lang.Object)
   */
  public ErrorInformation getErrorInformation(Object error) {
    return getScriptHandler().getErrorInformation(appContext, error);
  }

  /**
   * Return an error information object for the given error
   *
   * @param error
   *          the error
   * @param title
   *          optional title
   * @return an error information object for the given error
   */
  public ErrorInformation getErrorInformation(Object error, String title) {
    ErrorInformation ei;

    if (error instanceof ErrorInformation) {
      ei = (ErrorInformation) error;

      if ((title != null) && (title.length() > 0)) {
        ei = (ErrorInformation) ei.clone();
        ei.setTitle(title);
      }
    } else if (error instanceof String) {
      ei = new ErrorInformation(title, (String) error);
    } else if (error instanceof Throwable) {
      ei = new ErrorInformation(title, (Throwable) error);
    } else {
      ei = getScriptHandler().getErrorInformation(appContext, error);

      if (title != null) {
        ei.setTitle(title);
      }
    }

    return ei;
  }

  /**
   * Get the last window event
   *
   * @return the last window event
   */
  public ScriptingEvent getEvent() {
    return theEvent;
  }

  /**
   * Gets the component that is currently focused
   *
   * @return the component that is currently focused
   */
  public iPlatformComponent getFocusOwner() {
    return getAppContext().getPermanentFocusOwner();
  }

  /**
   * Gets the widget that currently owns the focus
   *
   * @return the widget that currently owns the focus
   */
  public iWidget getFocusedWidget() {
    iWidget w = Platform.findWidgetForComponent(getFocusOwner());

    if (w == null) {
      w = getAppContext().getRootViewer();

      if (w.getViewer().getUIWindow() != theWindow) {
        return this;
      }
    }

    return w;
  }

  @Override
  public iFormViewer getFormViewer() {
    return this;
  }

  /**
   * Gets the list of dynamically loaded resource icons. Only icons loaded via
   * an icon properties URL are available via this map
   *
   * @return the list of dynamically loaded resource icons
   */
  public Map<String, UIImageIcon> getIcons() {
    return getAppContext().getResourceIcons();
  }

  @Override
  public UIDimension getInnerSize(UIDimension size) {
    return theWindow.getInnerSize(size);
  }

  /**
   * Returns the inner height of a window's content area
   * @return the inner height of a window's content area
   */
  public int getInnerHeight() {
    return theWindow.getInnerSize(new UIDimension()).intHeight();
  }

  /**
   * Returns the inner width of a window's content area
   * @return the inner width of a window's content area
   */
  public int getInnerWidth() {
    return theWindow.getInnerSize(new UIDimension()).intWidth();
  }

  /**
   * Gets the DOM/JavaScript location object
   *
   * @return the DOM/JavaScript location object
   */
  public WindowLocation getLocation() {
    if (_location == null) {
      _location = new WindowLocation();
    }

    return _location;
  }

  @Override
  public iPlatformMenuBar getMenuBar() {
    return theWindow.getMenuBar();
  }

  @Override
  public Object getNamedItem(String name) {
    Object o = super.getNamedItem(name);

    return (o == null)
           ? getViewer(name)
           : o;
  }
  
  /**
   * Get the last native window event
   *
   * @return the last native window event
   */
  public EventObject getNativeEvent() {
    return theEvent.getUIEvent();
  }

  /**
   * Gets the DOM/JavaScript navigator object
   *
   * @return the DOM/JavaScript navigator object
   */
  public BrowserNavigator getNavigator() {
    if (_navigator == null) {
      _navigator = new BrowserNavigator();
    }

    return _navigator;
  }

  @Override
  public iContainer getParent() {
    return windowParent;
  }

  @Override
  public RenderableDataItem getParentItem() {
    return windowParent;
  }

  /**
   * Gets the component that currently has the focus ownership
   *
   * @return the component that currently has the focus ownership
   */
  public iPlatformComponent getPermanentFocusOwner() {
    return Platform.getAppContext().getPermanentFocusOwner();
  }

  /**
   * Gets the widget that currently owns the permanent focus
   *
   * @return the widget that currently owns the focus
   */
  public iWidget getPermanentFocusedWidget() {
    iWidget w = Platform.findWidgetForComponent(getPermanentFocusOwner());

    if (w == null) {
      w = getAppContext().getRootViewer();

      if (w.getViewer().getUIWindow() != theWindow) {
        return this;
      }
    }

    return w;
  }

  /**
   * Gets the relative font size for the application
   *
   * @return size the relative size (1.0 = normal size)
   */
  public float getRelativeFontSize() {
    return UIFontHelper.getRelativeFontSize();
  }

  /**
   * Gets the resource icon with the specified name
   *
   * @param name
   *          the name of the icon
   *
   * @return the resource icon with the specified name
   */
  public UIImageIcon getResourceIcon(String name) {
    return getAppContext().getResourceAsIcon(name);
  }

  /**
   * Gets a handle to the root target of this window
   *
   * @return a handle to the root target of this window
   */
  public iTarget getRootTarget() {
    if (windowManager.getRootViewer() == this) {
      return windowManager.getTarget();
    }

    return theWindow.getTarget();
  }

  /**
   * Gets a handle to the root viewer of this window
   *
   * @return a handle to the root viewer of this window
   */
  public iViewer getRootViewer() {
    iTarget t = getRootTarget();

    return (t == null)
           ? null
           : t.getViewer();
  }

  /**
   * Returns the rare version
   *
   * @return the rare version
   */
  public double getRuntimeVersion() {
    return iConstants.APPLICATION_VERSION;
  }

  @Override
  public int getScreenX() {
    return theWindow.getScreenX();
  }

  @Override
  public int getScreenY() {
    return theWindow.getScreenY();
  }

  @Override
  public iScriptHandler getScriptHandler() {
    return (scriptHandler == null)
           ? getAppContext().getScriptingManager()
           : scriptHandler;
  }

  @Override
  public Object getSelection() {
    return getTitle();
  }

  /**
   * Returns a handle to this window
   *
   * @return a handle to this window
   */
  public aWindowViewer getSelf() {
    return this;
  }

  @Override
  public UIDimension getSize() {
    return theWindow.getSize();
  }

  @Override
  public URL getSourceURL() {
    if (sourceURL != null) {
      return sourceURL;
    }

    iTarget t = this.getWindow().getTarget();
    iViewer v = (t == null)
                ? null
                : t.getViewer();

    return (v == null)
           ? null
           : v.getSourceURL();
  }

  /**
   * Gets the text displayed on the status bar
   *
   * @return the text displayed on the status bar
   */
  public String getStatus() {
    iStatusBar sb = windowManager.getStatusBar();

    return (sb == null)
           ? ""
           : sb.getMessage();
  }

  /**
   * Gets a handle to the status bar
   *
   * @return a handle to the status bar
   */
  @Override
  public iStatusBar getStatusBar() {
    return windowManager.getStatusBar();
  }

  /**
   * Retrieves a resource string
   *
   * @param name
   *          the name of the resource string
   *
   * @return the strings value or null if the string is undefined
   */
  public String getString(String name) {
    return appContext.getResourceAsString(name);
  }

  /**
   * Retrieves a resource string
   *
   * @param name
   *          the name of the resource string
   *
   * @param args
   *          arguments to use to format the string
   * @return the strings value or null if the string is undefined
   */
  public String getString(String name, Object... args) {
    name = appContext.getResourceAsString(name);

    return ((name == null) || (args == null))
           ? name
           : PlatformHelper.format(name, args);
  }

  /**
   * Retrieves a resource string Unlike getString(), all the argument values
   * have to be strings and only "%s" format specified is supported.
   *
   * @param name
   *          the name of the resource string
   *
   * @param args
   *          arguments to use to format the string
   * @return the string's value or null if the string is undefined
   *
   */
  public String getStringEx(String name, String... args) {
    name = appContext.getResourceAsString(name);

    return ((name == null) || (args == null))
           ? name
           : Helper.expandString(name, args);
  }

  /**
   * Gets the list of dynamically loaded resource strings. Only strings loaded
   * via an string properties URL are available via this map
   *
   * @return the list of dynamically loaded resource strings
   */
  public Map<String, String> getStrings() {
    return getAppContext().getResourceStrings();
  }

  /**
   * Gets a handle to the target with the specified name
   *
   * @param name
   *          the name of the target to retrieve
   *
   * @return a handle to the target with the specified name or null if no such
   *         target exists
   */
  public iTarget getTarget(String name) {
    return windowManager.getTarget(name);
  }

  @Override
  public String getTargetName() {
    return theWindow.getTargetName();
  }

  /**
   * Gets the list of targets
   *
   * @return the list of targets
   */
  public iTarget[] getTargets() {
    return windowManager.getTargets();
  }

  @Override
  public String getTitle() {
    return theWindow.getTitle();
  }

  /**
   * Gets the toolbar holder for the window
   *
   * @return the toolbar holder for the window
   */
  @Override
  public iToolBarHolder getToolBarHolder() {
    return theWindow.getToolBarHolder();
  }

  /**
   * Gets the top most window
   *
   * @return the top most window
   */
  public WindowViewer getTop() {
    iViewer v = getAppContext().getWindowManager().getRootViewer();

    if (v instanceof WindowViewer) {
      return (WindowViewer) v;
    }

    aWindowViewer top = windowParent;
    aWindowViewer p   = windowParent;

    while(p != null) {
      p = (aWindowViewer) p.getParentItem();

      if (p != null) {
        top = p;
      }
    }

    return (WindowViewer)top;
  }

  @Override
  public Object getUIWindow() {
    return theWindow.getUIWindow();
  }

  /**
   * Gets a handle to the viewer with the specified name
   *
   * @param name
   *          the name of the viewer
   *
   * @return a handle to the viewer with the specified name or null if no such
   *         viewer exists
   */
  public iViewer getViewer(String name) {
    if ((windowManager == null) || (name == null)) {
      return null;
    }

    iViewer v = windowManager.getViewer(name);

    return v;
  }

  /**
   * Gets the list of viewers
   *
   * @return the list of viewers
   */
  public iViewer[] getViewers() {
    return windowManager.getViewers();
  }

  @Override
  public iWidget getWidget(String name) {
    if ((windowManager == null) || (name == null)) {
      return null;
    }

    int n = name.indexOf('/');

    if (n != -1) {
      String  s = name.substring(0, n);
      iViewer v = windowManager.getViewer(s);

      if (!(v instanceof iContainer)) {
        return null;
      }

      return ((iContainer) v).getWidgetFromPath(name.substring(n + 1));
    }

    iViewer v = windowManager.getViewer(name);

    if (v != null) {
      return v;
    }

    return super.getWidget(name);
  }

  @Override
  public int getWidgetCount() {
    int count = 0;

    if (theWindow.getTarget().getViewer() != null) {
      count++;
    }

    if (getMenuBar() != null) {
      count++;
    }

    if (getStatusBar() != null) {
      count++;
    }

    if (getToolBarHolder() != null) {
      count++;
    }

    return count;
  }

  /**
   * Gets a handle to the widget with the specified name
   *
   * @param name
   *          the name of the widget. This can be the name of a viewer
   *          a single string with a slash (/) separated path or
   *          an array of path names.
   *
   * @return a handle to the viewer widget the specified name or null if no such
   *         widget exists
   */
  public iWidget getWidgetEx(String... name) {
    if ((windowManager == null) || (name == null) || (name.length == 0)) {
      return null;
    }

    int len = name.length;

    if (len == 1) {
      return getWidget(name[0]);
    }

    iViewer v = windowManager.getViewer(name[0]);

    for (int i = 1; i < len - 1; i++) {
      if (!(v instanceof iContainer)) {
        break;
      }

      iWidget w = ((iContainer) v).getWidget(name[i]);

      v = (w instanceof iViewer)
          ? (iViewer) w
          : null;
    }

    return (v instanceof iContainer)
           ? ((iContainer) v).getWidget(name[len - 1])
           : null;
  }

  @Override
  public WindowViewer getWindow() {
    return (WindowViewer) this;
  }

  /**
   * Returns whether the engine was launched via web start
   *
   * @return true if the engine was launched via web start; false otherwise
   */
  public boolean isApplet() {
    return false;
  }

  /**
   * Returns whether closing the window is allowed. This method is called after
   * an <code>onWillClose</code> event was fired The event handler can call
   * <code>setCanClose</code> to control whether the window is allowed to be
   * closed
   *
   * @return true closing the window is allowed; false otherwise
   * @see #setCanClose
   */
  public boolean isClosingAllowed() {
    boolean can = canClose;

    canClose = true;

    return can;
  }

  /**
   * Returns whether the window is maximized. Only valid for top level Frames
   *
   * @return true if the window is maximized; false otherwise
   */
  public boolean isMaximized() {
    return (getWidth() == UIScreen.getWidth()) && (getHeight() == UIScreen.getHeight());
  }

  @Override
  public boolean isRetainInitialWidgetValues() {
    return false;
  }

  /**
   * Returns whether the engine was launched via web start
   *
   * @return true if the engine was launched via web start; false otherwise
   */
  public boolean isWebStart() {
    return false;
  }

  /**
   * Gets the timer event to use for handling timers
   *
   * @return the timer event
   */
  protected ScriptingEvent getTimerEvent() {
    if (timerEvent == null) {
      timerEvent = new ScriptingEvent(getScriptHandler(), iConstants.ATTRIBUTE_TIMER, new EventBase(theWindow),
                                      theWindow, null);
    }

    return timerEvent;
  }
   @Override
  protected void initializeListeners(aWidgetListener listener) {
     super.initializeListeners(listener);
     if(listener!=null) {
       theWindow.addWindowListener(listener);
     }
  }
  
  @Override
  protected void uninitializeListeners(aWidgetListener listener) {
    super.uninitializeListeners(listener);
    if(listener!=null) {
      theWindow.removeWindowListener(listener);
    }
  }
  
  @Override
  protected List<iWidget> getWidgetListEx() {
    widgetList.clear();

    iPlatformMenuBar mb = getMenuBar();

    if (mb != null) {
      widgetList.add(mb.getContainerComponent().getWidget());
    }

    iToolBarHolder tb = getToolBarHolder();

    if (tb != null) {
      widgetList.add(tb.getToolBar().getComponent().getWidget());
    }

    iViewer v = ((theWindow == null) || (theWindow.getTarget() == null))
                ? null
                : theWindow.getTarget().getViewer();

    if (v != null) {
      widgetList.add(v);
    }

    iStatusBar sb = getStatusBar();

    if (sb != null) {
      widgetList.add(sb.getComponent().getWidget());
    }

    return widgetList;
  }

  private iScriptHandler.iScriptRunnable createRunner(String name, Object code, String language) {
    final iScriptHandler                 sh = this.getScriptHandler();
    final iScriptHandler.iScriptRunnable r;
    Object                               script;

    if (language == null) {
      if (code instanceof String) {
        script = sh.compile(getScriptingContext(), name, (String) code);
      } else {
        script = code;
      }

      r = sh.createRunner(getScriptingContext(), script, null);
    } else {
      r = sh.createRunner(getScriptingContext(), (String) code, language, null);
    }

    return r;
  }

  private void loadScriptEx(ActionLink link, boolean use, boolean strict, boolean optimized) throws IOException {
    String language = link.getMimeType();
    String s        = link.getContentAsString();

    if ((s != null) && (s.length() > 0)) {
      String location = "";

      if (link.isInlineURL()) {
        location = "inline";
      } else {
        try {
          URL url = link.getURL(this);

          location = url.toExternalForm();
        } catch(IOException e) {
          Platform.ignoreException(null, e);
        }
      }

      if (use) {
        final boolean oo = Functions.isOptimizationEnabled();
        final boolean st = Functions.isStrictScriptingMode();

        try {
          Functions.setOptimizationEnabled(optimized);
          Functions.setStrictScriptingMode(strict);
          getScriptHandler().loadScript(location, s, language);
        } finally {
          Functions.setOptimizationEnabled(oo);
          Functions.setStrictScriptingMode(st);
        }
      } else {
        getScriptHandler().loadScript(location, s, language);
      }
    }
  }

  private void loadScriptEx(String url, boolean use, boolean strict, boolean optimized) throws IOException {
    URL u = null;

    try {
      String name = this.getScriptHandler().getScriptingName();

      if (!url.startsWith("/") && (name != null)){
        u=new URL(getURL(name),url);
      }
    } catch(Exception e) {
      u = null;
    }

    if (u == null) {
      u = getURL(url);
    }

    loadScriptEx(new ActionLink(this, u), use, strict, optimized);
  }

  private iCancelable scheduleTask(String name, Object code, long timeout, boolean repeats) {
    if ((appContext == null) || appContext.isShuttingDown()) {
      return null;
    }

    final iScriptHandler.iScriptRunnable r = createRunner(name, code, null);

    if (windowTimer == null) {
      windowTimer = Platform.createTimer("windowTimer");
    }

    Runnable task = new Runnable() {
      @Override
      public void run() {
        Object w = theWindow;

        if ((appContext == null) || appContext.isShuttingDown()) {
          if (windowTimer != null) {
            windowTimer.cancel();
          }

          return;
        }

        if ((w != null)) {
          if (Platform.isUIThread()) {
            r.run();
          } else {
            Platform.invokeLater(r);
          }
        } else {
          iTimer t = windowTimer;

          if (t != null) {
            t.cancel();
          }

          windowTimer = null;
        }
      }
    };

    if (repeats) {
      return windowTimer.schedule(task, timeout, timeout);
    } else {
      return windowTimer.schedule(task, timeout);
    }
  }

  public class WindowLocation {
    @Override
    public String toString() {
      return getHref();
    }

    public void setHref(String url) {
      iWindow win = theWindow;

      if (url == null) {
        iViewer v = win.getTarget().removeViewer();

        if (v != null) {
          try {
            v.dispose();
          } catch(Exception e) {
            Platform.ignoreException("Failure on viewer dispose", e);
          }
        }
      } else {
        try {
          ActionLink link = createActionLink(aWindowViewer.this, url);

          link.setTargetName(win.getTargetName());

          iWindowManager wm = getAppContext().getWindowManager();

          wm.asyncActivateViewer(link);
        } catch(MalformedURLException shouldNotHappen) {}
      }
    }

    public String getHash() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : url.getRef();
    }

    public String getHost() {
      URL url = getContextURL();

      if (url == null) {
        return "";
      }

      String s    = url.getHost();
      int    port = url.getPort();

      return (port == -1)
             ? s
             : s + ":" + port;
    }

    public String getHostname() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : url.getHost();
    }

    public String getHref() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : JavaURLConnection.toExternalForm(url);
    }

    public String getPathname() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : url.getPath();
    }

    public int getPort() {
      URL url = getContextURL();

      return (url == null)
             ? -1
             : url.getDefaultPort();
    }

    public String getProtocol() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : url.getProtocol();
    }

    public String getSearch() {
      URL url = getContextURL();

      return (url == null)
             ? ""
             : url.getQuery();
    }

    public URL getUrl() {
      return getContextURL();
    }
  }


  static class BrowserNavigator {
    public String getAppCodeName() {
      return iConstants.APPLICATION_NAME_STRING;
    }

    public String getAppName() {
      return iConstants.APPLICATION_NAME_STRING;
    }

    public String getAppVersion() {
      String version = iConstants.APPLICATION_VERSION_STRING;

      try {
        version += " (" + getPlatform() + "; " + getLanguage() + ")";
      } catch(Exception ignore) {}

      return version;
    }

    public boolean getCookieEnabled() {
      return true;
    }

    public String getLanguage() {
      return Locale.getDefault().toString();
    }

    public String getPlatform() {
      if (Platform.isAndroid()) {
        return "android";
      }

      if (Platform.isWindows()) {
        return "Win32";
      }

      if (Platform.isLinux()) {
        return "Linux i686";
      }

      if (Platform.isMac()) {
        return "MacIntel";
      }

      return "Java";
    }

    public String getUserAgent() {
      return Platform.getUserAgent();
    }
  }
}
