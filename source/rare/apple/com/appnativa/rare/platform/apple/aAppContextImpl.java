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

package com.appnativa.rare.platform.apple;

import com.appnativa.rare.ErrorInformation;
import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.aRare;
import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.platform.apple.ui.view.DialogWindow;
import com.appnativa.rare.platform.apple.ui.view.PopupWindow;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformComponentFactory;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharArray;
import com.appnativa.util.IdentityArrayList;

import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

/*-[
 #import "java/lang/reflect/Field.h"
 #import "RAREAPApplication.h"
 #if TARGET_OS_IPHONE
 #import <UIKit/UIKit.h>
 #else
 #import <AppKit/AppKit.h>
 #endif
 ]-*/

/**
 *
 * @author Don DeCoteau
 */
public abstract class aAppContextImpl extends aAppContext {
  HashMap<String, String> interfaceProxies = new HashMap<String, String>();;
  protected Logger        logger;
  protected boolean       useAssetCatalog;

  public aAppContextImpl(aRare instance) {
    super(instance, new UIProperties());

    if (PlatformHelper.isHighDensity()) {
      rareIconResourcePaths = new String[] { "drawable-xhdpi", "drawable-mdpi" };
    } else {
      rareIconResourcePaths = new String[] { "drawable-mdpi", "drawable-xhdpi" };
    }

    registerInterfaceProxy("com.appnativa.rare.iFunctionCallback", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.iWorkerTask", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.util.iCancelable", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.util.iStringConverter", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.util.iFilter", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.net.ActionLink.iErrorHandler",
                           "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("java.util.Comparator", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.ui.event.iActionListener", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.ui.event.iChangeListener", "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.ui.effects.iAnimatorListener",
                           "com.appnativa.rare.GenericInterfaceProxy");
    registerInterfaceProxy("com.appnativa.rare.ui.effects.iAnimatorValueListener",
                           "com.appnativa.rare.GenericInterfaceProxy");
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
      if (o instanceof DialogWindow) {
        return true;
      }
    }

    return false;
  }

  @Override
  public native boolean browseURL(URL url)
  /*-[
     NSURL *nsurl = (NSURL*)url->proxy_;
     #if TARGET_OS_IPHONE
     return [[UIApplication sharedApplication] openURL:nsurl];
     #else
     return [[NSWorkspace sharedWorkspace] openURL:nsurl];
     #endif
  ]-*/
  ;

  @Override
  public void clearStatusBar() {}

  @Override
  public native void closePopupWindows(boolean all)
  /*-[
    [[RAREAPApplication getInstance] closePopupWindows: all];
  ]-*/
  ;

  /**
   * Writes a debug message to the console when running in debug mode
   *
   * @param msg
   *          the message to write
   */
  public static void debugLog(String msg) {
    if (aRare.isDebugEnabled()) {
      System.err.println(msg);
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    System.exit(0);
  }

  /**
   * Logs a FINE error to the default log The message will automatically be
   * switched to a WARNING message if debug mode is enabled
   *
   * @param msg
   *          the message to log
   * @param throwable
   *          the error
   */
  public static void ignoreException(String msg, Throwable throwable) {
    try {
      aAppContextImpl.getDefaultExceptionHandler(null).ignoreException(msg, throwable);
    } catch(Exception ignore) {
      if (aRare.isDebugEnabled()) {
        if (msg != null) {
          System.err.println(msg);
        }

        if (throwable != null) {
          throwable.printStackTrace(System.err);
        }
      }
    }
  }

  public boolean islandscapeMode() {
    return landscapeMode;
  }

  @Override
  public void lockOrientation(Boolean landscape) {
    PlatformHelper.lockOrientation(landscape);
  }

  public URL makeResourcePath(String file) {
    URL u = null;

    if (file != null) {
      int n = file.lastIndexOf('.');

      if (n == -1) {
        u = makeResourceURL(file, "");
      } else {
        u = makeResourceURL(file.substring(0, n), file.substring(n + 1));
      }
    }

    return u;
  }

  public String makeResourcePath(String name, String ext) {
    int    n   = name.lastIndexOf('/');
    String dir = null;

    if (n != -1) {
      dir  = name.substring(0, n);
      name = name.substring(n + 1);
    }

    try {
      return makeResourcePath(dir, name, ext);
    } catch(Exception e) {}

    return null;
  }

  public static native String makeResourcePath(String dir, String name, String ext)
  /*-[
    if(!dir) {
       return [[NSBundle mainBundle] pathForResource:name ofType:ext];
     }
     return [[NSBundle mainBundle] pathForResource:name ofType:ext inDirectory: dir];
  ]-*/
  ;

  @Override
  public URL makeResourceURL(String name, String ext) {
    int    n   = name.lastIndexOf('/');
    String dir = null;

    if (n != -1) {
      dir  = name.substring(0, n);
      name = name.substring(n + 1);
    }

    try {
      String file = makeResourcePath(dir, name, ext);

      if (file != null) {
        return PlatformHelper.fileToURL(file);
      }
    } catch(Exception e) {}

    return null;
  }

  @Override
  public void oneLineErrorMessage(String title, String msg) {
    throw new UnsupportedOperationException();
  }

  public void registerInterfaceProxy(String interfaceName, String proxyClassName) {
    if (interfaceName.indexOf('.') != -1) {
      interfaceName = PlatformHelper.getIOSClassName(interfaceName);
    }

    if (proxyClassName.indexOf('.') != -1) {
      proxyClassName = PlatformHelper.getIOSClassName(proxyClassName);
    }

    interfaceProxies.put(interfaceName, proxyClassName);
  }

  @Override
  public void unlockOrientation() {
    PlatformHelper.unlockOrientation();
  }

  public void setLogger(Logger l) {
    logger = l;
  }

  /**
   * Get the current application context for the calling thread. If no context
   * can be determined the context for any available instance is returned. This
   * ensures that a valid context is always returned.
   *
   * @return the last focused application context
   */
  public static aAppContextImpl getAppContext(iPlatformComponent c) {
    return getAppContext(Platform.findWidgetForComponent(c));
  }

  /**
   * Get the current application context for the calling thread. If no context
   * can be determined the context for any available instance is returned. This
   * ensures that a valid context is always returned.
   *
   * @return the last focused application context
   */
  public static aAppContextImpl getAppContext(iWidget w) {
    return (w == null)
           ? (aAppContextImpl) _instance
           : (aAppContextImpl) w.getAppContext();
  }

  @Override
  public String getApplicationName() {
    return RARE.getName();
  }

  @Override
  public iPlatformComponentFactory getComponentCreator() {
    return RARE.getWindowManager().getComponentCreator();
  }

  public ErrorInformation getErrorInformation(Object nserror) {
    return getScriptingManager().getErrorInformation(this, nserror);
  }

  public iFunctionHandler getFunctionHandler() {
    return RARE.getFunctionHandler();
  }

  public String getInterfaceProxyClassName(String interfaceName) {
    return interfaceProxies.get(interfaceName);
  }

  public Object getInterfaceProxyObject(String interfaceName) {
    String name = interfaceProxies.get(interfaceName);

    return (name == null)
           ? null
           : Platform.createObject(name);
  }

  public Logger getLogger() {
    if (logger == null) {
      logger = Logger.getLogger("RARE");
    }

    return logger;
  }

  @Override
  @SuppressWarnings("resource")
  public UIImage getManagedResource(String name) {
    if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".gif")) {
      return PlatformHelper.getImageFromResourceFileName(name);
    }
    if (name.startsWith("Rare.icon") ) {
      return super.getManagedResource(name);
    }

    if (!managedResourcePathInitialized) {
      initializeManagedResourcePaths();
      useAssetCatalog=uiDefaults.getBoolean("Rare.Apple.useAssetCatalog", false);
      if (managedResourcePaths.length == 0) {
        useAssetCatalog = true;
      }
    }

    if (!useAssetCatalog) {
      return super.getManagedResource(name);
    }
    String oname=name;
    if(name.indexOf('.')!=-1) {
      CharArray ca      = new CharArray(name);
      ca.toLowerCase().replace('.', '_');
      name=ca.toString();
    }
    Object proxy=ImageUtils.loadAssetCatalogImageProxy(name);
    return proxy==null ? null : new UIImage(proxy,oname);
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
      aAppContextImpl.ignoreException("getResourceAsString", e);
    }

    return null;
  }

  @Override
  public URL getResourceURL(String path) {
    if (resourceFinder != null) {
      URL url = resourceFinder.getResource(path);

      if (url != null) {
        return url;
      }
    }

    try {
      return makeResourceURL(path, null);
    } catch(Exception e) {}

    return null;
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

  public boolean isInitialized() {
    return RARE != null;
  }

  void setFocusOwner(iPlatformComponent c) {
    focusOwner = c;
  }

  void setPermanentFocusOwner(iPlatformComponent c) {
    permanentFocusOwner = c;
    focusOwner          = c;
  }

  protected void setupUIDefaults() {
    super.setupUIDefaults(PlatformHelper.getSystemForeground(), PlatformHelper.getSystemBackground());
  }

  @Override
  protected String getDefaultManagedResourcePath() {
    return "/";
  }

  @Override
  protected iPlatformAnimator getResourceAsAnimatorEx(String animator) {
    return null;
  }

  @Override
  protected native boolean hasResourceDirectory(String path)
  /*-[
     return [[NSBundle mainBundle] pathForResource:path ofType:nil] ? YES : NO;
  ]-*/
  ;
}
