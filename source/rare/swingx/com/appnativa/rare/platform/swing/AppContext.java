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

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper;
import com.appnativa.rare.platform.swing.ui.util.SwingHelper;
import com.appnativa.rare.platform.swing.ui.view.PopupWindow;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageHelper;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIMenu;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformComponentFactory;
import com.appnativa.rare.ui.iPlatformWindowManager;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import java.net.URL;

import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * This class represents an instance of a running application. It acts as a
 * proxy to a running application and also maintains a list of all running
 * instances
 *
 * @author Don DeCoteau
 */
public class AppContext extends aAppContext {
  private Applet  applet;
  private boolean embeddedInstance;
  private Logger  logger;

  /**
   * Creates a new application context
   *
   * @param instance
   *          the application instance
   */
  public AppContext(Rare instance) {
    this(instance, false);
  }

  public AppContext(Rare instance, boolean embedded) {
    super(instance, new UIProperties());
    embeddedInstance      = embedded;
    rareIconResourcePaths = new String[] { Rare.resourcePath };
    landscapeMode         = true;

    if (!embeddedInstance) {
      PlatformHelper.initialize();
    }

    if (!FocusChangeListener.created) {
      FocusChangeListener fc = new FocusChangeListener();

      KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(fc);
    }
  }

  @Override
  public boolean browseURL(URL url) {
    return true;
  }

  @Override
  public void clearStatusBar() {}

  @Override
  public void closePopupWindows(boolean all) {
    IdentityArrayList w = new IdentityArrayList(activeWindows);

    for (Object o : w) {
      if (o instanceof PopupWindow) {
        ((PopupWindow) o).hidePopup();
      } else if (all && (o instanceof JDialog)) {
        ((JDialog) o).dispose();
      }
    }

    UIMenu.closeVisibleMenus();
  }

  @Override
  public boolean isPopupWindowShowing() {
    IdentityArrayList w = new IdentityArrayList(activeWindows);

    for (Object o : w) {
      if (o instanceof PopupWindow) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isDialogWindowShowing() {
    IdentityArrayList w = new IdentityArrayList(activeWindows);

    for (Object o : w) {
      if (o instanceof JDialog) {
        return true;
      }
    }

    return false;
  }

  /**
   * Writes a debug message to the console when running in debug mode
   *
   * @param msg
   *          the message to write
   */
  public static void debugLog(String msg) {
    if (Rare.isDebugEnabled()) {
      System.err.println(msg);
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    if (!embeddedInstance) {
      System.exit(0);
    }
  }

  @Override
  public void lockOrientation(Boolean landscape) {}

  protected boolean isLowIconDensity() {
    return "ldpi".equals(ImageHelper.defaultIconDensity);
  }

  @Override
  public URL makeResourceURL(String name, String extension) {
    if ((extension != null) && (extension.length() > 0)) {
      name = name + "." + extension;
    }

    if (name.indexOf("://") != -1) {
      try {
        URL u = new URL(name);

        if (u.getProtocol().equals("file")) {
          File f = PlatformHelper.toFile(u);

          return f.exists()
                 ? u
                 : null;
        }
      } catch(Exception e) {}
    }

    return AppContext.this.getResourceURL(name);
  }

  @Override
  public void oneLineErrorMessage(String title, String msg) {
    throw new UnsupportedOperationException();
  }

  // static class StartupTask implements iWorkerTask {
  // private boolean done;
  // Throwable error;
  // public Object compute() {
  // try {
  // createRareInstance();
  // } catch(Exception e) {
  // error = e;
  // }
  // finally {
  // done=true;
  // }
  // return null;
  // }
  // public void finish(Object result) {
  // if (error != null) {
  // error.printStackTrace();
  // AlertDialog alertDialog = AlertDialog.startupError(error);
  // alertDialog.show();
  // return;
  // }
  // try {
  //
  // _instance.setContentView(a);
  // } catch (Throwable e) {
  // showError(a, e);
  // return;
  // }
  // a.setRareInstance((Rare)((AppContext)_instance).RARE);
  // }
  // public void cancel(boolean canInterrupt) {
  // }
  // public boolean isCanceled() {
  // return false;
  // }
  // public boolean isDone() {
  // return done ;
  // }
  //
  // }
  @Override
  public void unlockOrientation() {}

  public void setLogger(Logger l) {
    logger = l;
  }

  public Applet getApplet() {
    return applet;
  }

  @Override
  public iPlatformComponentFactory getComponentCreator() {
    return RARE.getWindowManager().getComponentCreator();
  }

  /**
   * Get the current application context for the calling thread. If no context
   * can be determined the context for any available instance is returned. This
   * ensures that a valid context is always returned.
   *
   * @return the last focused application context
   */
  public static AppContext getContext() {
    return (AppContext) _instance;
  }

  public iFunctionHandler getFunctionHandler() {
    return RARE.getFunctionHandler();
  }

  public Logger getLogger() {
    if (logger == null) {
      logger = Logger.getAnonymousLogger();
    }

    return logger;
  }

  @Override
  public iPlatformPainter getRequiredFieldOverlayPainter() {
    return null;
  }

  @Override
  public UIImage getResourceAsImage(String name) {
    UIImageIcon icon = getResourceAsIcon(name);

    if (icon != null) {
      return icon.getUIImage();
    }

    return null;
  }

  @Override
  public String getResourceAsString(String name) {
    String s;

    if (resourceFinder != null) {
      s = resourceFinder.getResourceAsString(name);

      if (s != null) {
        return s;
      }
    }

    if (appStrings != null) {
      s = appStrings.get(name);

      if (s != null) {
        return s;
      }
    }

    try {
      return RARE.getResourceBundle().getString(name);
    } catch(Exception e) {
      Platform.debugLog("Missing resource string:" + name);

      if (name.startsWith("Rare.text.")) {
        try {
          return RARE.getResourceBundle().getString("Rare.runtime." + name.substring(5));
        } catch(Exception ignore) {
          ignore.printStackTrace();
        }
      }
    }

    return null;
  }

  @Override
  public URL getResourceURL(String path) {
    if (path.startsWith("file://")) {
      try {
        URL  u = new URL(path);
        File f = PlatformHelper.toFile(u);

        if (!f.exists()) {
          return null;
        }

        return u;
      } catch(Exception e) {}
    }

    if (resourceFinder != null) {
      URL url = resourceFinder.getResource(path);

      if (url != null) {
        return url;
      }
    }

    return PlatformHelper.getApplicationClassLoader().getResource(path);
  }

  /**
   * Gets the UI defaults for the specified widget
   *
   * @param w
   *          the widget
   * @return the UI defaults for the current context
   */
  public static UIProperties getUIDefaults(iWidget w) {
    return _instance.getUIDefaults();
  }

  @Override
  public WindowViewer getWindowViewer() {
    WindowViewer w   = null;
    final int    len = activeWindows.size();

    for (int i = len - 1; i > -1; i--) {
      Object o = activeWindows.get(i);

      if (o instanceof Frame) {
        w = ((Frame) o).getWindowViewer();

        if (w != null) {
          break;
        }
      }
    }

    if (w == null) {
      w = getMainWindow();
    }

    return w;
  }

  @Override
  public boolean isEmbeddedInstance() {
    return embeddedInstance;
  }

  public boolean isInitialized() {
    return RARE != null;
  }

  public boolean isJava16OrGreater() {
    return true;
  }

  void setupUIDefaults() {
    setupUIDefaults(null, null);
    uiDefaults.put("control", uiDefaults.get("Rare.background"));
    uiDefaults.put("controlShadow", uiDefaults.get("Rare.backgroundLtShadow"));
    uiDefaults.put("TableHeader.background", uiDefaults.get("Rare.backgroundLtShadow"));
    uiDefaults.put("TableHeader.marginColor", uiDefaults.get("Rare.backgroundShadow"));
    uiDefaults.put("Button.background", uiDefaults.get("Rare.background"));
    uiDefaults.put("Button.foreground", uiDefaults.get("Rare.Button.foreground"));

    String s = System.getProperty("Rare.Resources.path");

    if (s != null) {
      Platform.getUIDefaults().put("Rare.Resources.path", s);
    }

    setupSwingUIDefaults();
  }

  void setFocusOwner(iPlatformComponent c) {
    focusOwner = c;
  }

  void setPermanentFocusOwner(iPlatformComponent c) {
    permanentFocusOwner = c;
  }

  UIImageIcon getResourceIcon(String file) {
    if (!file.startsWith("/")) {
      file = Rare.makeResourcePath(file);
    }

    return UIImageHelper.createIcon(getResourceURL(file), true, 1);
  }

  protected void setupSwingUIDefaults() {
    // try {
    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    UIManager.put("ListUI", "com.appnativa.rare.platform.swing.plaf.BasicListExUI");
    UIManager.put("TableUI", "com.appnativa.rare.platform.swing.plaf.BasicTableExUI");
    UIManager.put("TableHeaderUI", "com.appnativa.rare.platform.swing.plaf.BasicTableHeaderExUI");
    UIManager.put("ScrollBarUI", "com.appnativa.rare.platform.swing.plaf.ScrollBarUI");
    UIManager.put("control", uiDefaults.get("Rare.background"));
    UIManager.put("controlShadow", uiDefaults.get("Rare.backgroundShadow"));
    UIManager.put("controlLtShadow", uiDefaults.get("Rare.backgroundLtShadow"));
    UIManager.put("Button.background", uiDefaults.get("Rare.background"));
    UIManager.put("Button.foreground", uiDefaults.get("Rare.Button.foreground"));
  }

  @Override
  protected iPlatformAnimator getResourceAsAnimatorEx(String animator) {
    return null;
  }

  private static void checkForTextFields(Object o) {
    if (o instanceof JTextField) {
      JTextField tf = (JTextField) o;

      if (tf.isEditable() && (tf.getDocument().getLength() == tf.getCaretPosition())) {
        tf.selectAll();
      }
    }
  }

  private static class FocusChangeListener implements PropertyChangeListener {
    public static boolean created;

    public FocusChangeListener() {
      super();
      created = true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
      if (Platform.isShuttingDown()) {
        return;
      }

      if (e.getPropertyName() == "permanentFocusOwner") {
        Component c = (Component) e.getNewValue();

        if ((c != null) &&!c.isDisplayable()) {
          if ((permanentFocusOwner != null) && permanentFocusOwner.isDisplayable()) {
            permanentFocusOwner.requestFocus();

            return;
          }

          c = null;
        }

        if ((c != null) && (c != permanentFocusOwner)) {
          if ((permanentFocusOwner != null) &&!permanentFocusOwner.isDisposed()) {
            iWidget w = permanentFocusOwner.getWidget();

            if ((w != null) &&!w.isDisposed()) {
              w.repaint();
            } else {
              permanentFocusOwner.repaint();
            }
          }

          permanentFocusOwner = Platform.findPlatformComponent(c);

          if ((permanentFocusOwner == null) || permanentFocusOwner.isDisposed()) {
            return;
          }

          AppContext app  = (AppContext) Platform.getAppContext();
          Rare       rare = (Rare) app.RARE;

          if ((rare == null) ||!app.isInitialized() || app.isShuttingDown()) {
            return;
          }

          iPlatformWindowManager wm = rare.getWindowManager();

          if ((wm == null) || wm.isDisposed()) {
            return;
          }

          iWidget w = permanentFocusOwner.getWidget();

          if (w != null) {
            w.repaint();
          } else {
            permanentFocusOwner.repaint();
          }

          app.updateActions(permanentFocusOwner);

          aWidgetListener l = rare.getWindowManager().getWidgetListener();

          if ((l != null) && l.isEnabled(iConstants.EVENT_PERMANENT_FOCUS_CHANGE)) {
            l.execute(iConstants.EVENT_PERMANENT_FOCUS_CHANGE, e);
          }
        }
      } else if (e.getPropertyName() == "focusOwner") {
        Component c = (Component) e.getNewValue();

        if ((c != null) && (focusOwner != c)) {
          focusOwner = Platform.findPlatformComponent(c);

          if ((focusOwner == null) || focusOwner.isDisposed()) {
            return;
          }

          AppContext app  = (AppContext) Platform.getAppContext();
          Rare       rare = (Rare) app.RARE;

          if ((rare == null) ||!app.isInitialized() ||!app.isShuttingDown()) {
            return;
          }

          iPlatformWindowManager wm = rare.getWindowManager();

          if ((wm == null) || wm.isDisposed()) {
            return;
          }

          if (SwingHelper.isInTraversalEvent() && (UIManager.get("Rare.makeComponentVisibleToUser") != Boolean.FALSE)) {
            SwingHelper.makeComponentVisibleToUser(c);
          }

          checkForTextFields(focusOwner);

          aWidgetListener l = wm.getWidgetListener();

          if ((l != null) && l.isEnabled(iConstants.EVENT_FOCUS_CHANGE)) {
            l.execute(iConstants.EVENT_FOCUS_CHANGE, e);
          }
        } else if ((focusOwner != null) &&!focusOwner.isDisposed()) {
          iWidget w = focusOwner.getWidget();

          if (w != null) {
            w.repaint();
          } else {
            focusOwner.repaint();
          }
        }
      } else if ((permanentFocusOwner != null) &&!permanentFocusOwner.isDisposed()) {
        iWidget w = permanentFocusOwner.getWidget();

        if ((w != null) &&!w.isDisposed()) {
          w.repaint();
        } else {
          permanentFocusOwner.repaint();
        }
      }
    }
  }
}
