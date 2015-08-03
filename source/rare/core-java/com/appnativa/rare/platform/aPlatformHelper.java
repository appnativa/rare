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

import com.appnativa.rare.CancelableFutureWrapper;
import com.appnativa.rare.CancelableWorker;
import com.appnativa.rare.Platform;
import com.appnativa.rare.ThreadEx;
import com.appnativa.rare.ThreadPoolExecutorEx;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.exception.FailoverThreadPoolExecutor;
import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iWeakReference;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.rare.scripting.RhinoContextFactory;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.Utils;
import com.appnativa.rare.ui.event.MouseEvent;
import com.appnativa.rare.ui.iListHandler;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.FilePreferences;
import com.appnativa.util.Helper;
import com.appnativa.util.Streams;
import com.appnativa.util.iCancelable;
import com.appnativa.util.iPreferences;
import com.appnativa.util.iScheduler;

import org.mozilla.javascript.WrappedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

public class aPlatformHelper {
  static ThreadPoolExecutor executorService;
  static ThreadPoolExecutor imageExecutorService;
  static iScheduler         systemScheduler;

  public static iWeakReference createWeakReference(Object value) {
    return new WeakReferenceEx(value);
  }

  public static aWidget createWidget(Class cls, iContainer parent) throws Exception {
    Constructor c = getConstructor(cls, iContainer.class);

    return (aWidget) c.newInstance(parent);
  }

  public static URL fileToURL(File f) throws MalformedURLException {
    return f.toURI().toURL();
  }

  public static URL fileToURL(String file) throws MalformedURLException {
    File f = new File(file);

    return f.toURI().toURL();
  }

  public static String format(String format, Object... args) {
    return String.format(format, args);
  }

  public static void initializeThreadingService(int max, int imageMax) {
    ThreadFactory factory = new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        ThreadEx t = new ThreadEx(Thread.currentThread().getThreadGroup(), r,
                                  "Rare Worker - " + executorService.getTaskCount(), 1024000);

        return t;
      }
    };
    FailoverThreadPoolExecutor handler = new FailoverThreadPoolExecutor(1);

    executorService = new ThreadPoolExecutorEx(max, factory, handler);

    if (imageMax > 0) {
      imageExecutorService = new ThreadPoolExecutor((imageMax > 5)
              ? 5
              : imageMax, imageMax, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }
  }

  /**
   * Saves a URL to a temporary file
   *
   * @param url
   *          the url
   * @return the file handle
   *
   * @throws java.io.IOException
   */
  public static File saveURLToTmpFile(URL url) throws IOException {
    String name = url.getFile();
    String s    = ".tmp";
    int    n    = name.lastIndexOf('.');

    if (n != -1) {
      s = name.substring(n);
    }

    File             f    = File.createTempFile("rare", s);
    URLConnection    conn = url.openConnection();
    InputStream      in   = conn.getInputStream();
    FileOutputStream out  = new FileOutputStream(f);

    try {
      Streams.streamToStream(in, out, null);
    } finally {
      try {
        in.close();
      } catch(Exception ignore) {}

      try {
        out.close();
      } catch(Exception ignore) {}
    }

    return f;
  }

  public static boolean supportsSyncUpdateWithScreenRefresh(iPlatformComponent c) {
    return false;
  }

  public static void syncUpdateWithScreenRefresh(iPlatformComponent c, boolean sync) {}

  public static File toFile(URL url) throws URISyntaxException {
    return new File(url.toURI());
  }

  /**
   * Gets an exceptions message. If the exception <CODE>getMessage()</CODE>
   * method returns a non-null and non-empty string that that value is returned.
   * Otherwise the exceptions class name or <CODE>toString()</CODE> value is
   * returned
   *
   * @param e
   *          the exception
   * @return the exceptions message
   */
  public static Throwable unwrapJavaScriptException(Throwable e) {
    if (e instanceof WrappedException) {
      e = ((WrappedException) e).getWrappedException();
    }

    return e;
  }

  public static void setCookie(String cookieHeader, URL url, String value) {
    try {
      CookieHandler handler = CookieHandler.getDefault();

      if (handler != null) {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        List<String>              values  = new ArrayList<String>();

        values.add(value);
        headers.put(cookieHeader, values);
        handler.put(url.toURI(), headers);
      }
    } catch(Exception ex) {
      throw new ApplicationException(ex);
    }
  }

  public static void setOptimizationEnabled(boolean enabled) {
    RhinoContextFactory.setOptimizationEnabled(enabled);
  }

  public static void setPreferredSizeEx(iPlatformComponent c, String width, String height) {
    if ((width != null) && (width.length() > 0) &&!width.equals("-1")) {
      c.putClientProperty(iConstants.RARE_WIDTH_PROPERTY, width);
    } else {
      c.putClientProperty(iConstants.RARE_WIDTH_PROPERTY, null);
    }

    if ((height != null) && (height.length() > 0) &&!height.equals("-1")) {
      c.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, height);
    } else {
      c.putClientProperty(iConstants.RARE_HEIGHT_PROPERTY, null);
    }
  }

  public static void setStrictScriptingMode(boolean strict) {
    RhinoContextFactory.setStrictScriptingMode(strict);
  }

  public static Constructor getConstructor(Class cls, Class<?>... params) {
    try {
      return cls.getConstructor(params);
    } catch(NoSuchMethodException e) {
      return null;
    }
  }

  public static List<RenderableDataItem> getDropedItems(aWidget dest, iWidget source, boolean copy, boolean text) {
    Object o;

    if (source instanceof iListHandler) {
      if (!text && ((dest == source) || (dest instanceof iListHandler))) {
        o = ((iListHandler) source).getSelections();
      } else {
        o = ((iListHandler) source).getSelectionsAsStrings();
      }
    } else {
      if (dest == source) {
        o = source.getSelection();
      } else {
        o = source.getSelectionAsString();
      }
    }

    return Utils.getItems(o, dest, copy);
  }

  public static StringBuilder getMethods(Object o, StringBuilder sb) {
    try {
      Method        a[]  = o.getClass().getDeclaredMethods();
      String        name = o.getClass().getName() + ".";
      Method        m;
      int           mods;
      StringBuilder sig = new StringBuilder();

      sb.append("\nMethods for class:" + name + "\n");

      TreeMap map = new TreeMap();

      for (int i = 0; i < a.length; i++) {
        m    = a[i];
        mods = m.getModifiers();

        if ((mods & Modifier.PUBLIC) > 0 && (mods & Modifier.STATIC) == 0) {
          Helper.toString(m, sig, "com.appnativa.");
          map.put(sig.toString(), null);
          sig.setLength(0);
        }
      }

      Iterator it = map.keySet().iterator();

      while(it.hasNext()) {
        sb.append((String) it.next()).append('\n');
      }
    } catch(Exception e) {
      sb.setLength(0);
      sb.append("Exception : ").append(e.toString());
    }

    return sb;
  }

  public static String getPackageName(Class cls) {
    return cls.getPackage().getName();
  }

  /**
   * Returns a java preference for the specified rare application key
   *
   * @param appKey
   *          the name for the preferences. This is the unique name for the
   *          application
   * @return the preferences object
   */
  public static iPreferences getPreferences(String appKey) {
    if (Preferences.userRoot() instanceof FilePreferences) {
      return (FilePreferences) Preferences.userRoot().node(appKey);
    }

    FilePreferences f = new FilePreferences(true);

    return (FilePreferences) f.node(appKey);
  }

  public static boolean getStrictScriptingMode() {
    return RhinoContextFactory.getStrictScriptingMode();
  }

  public iScheduler getSystemScheduler() {
    if (systemScheduler == null) {
      systemScheduler = new iScheduler() {
        @Override
        public iCancelable scheduleTask(Runnable task) {
          return executeBackgroundTask(task, false);
        }
      };
    }

    return systemScheduler;
  }

  public static boolean isMouseClick(UIPoint startPoint, long startTime, MouseEvent releaseEvent) {
    if ((startPoint == null) || (releaseEvent == null)) {
      return false;
    }

    Integer t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickThreshold");

    if (t == null) {
      t = 500;
    }

    if (releaseEvent.getWhen() - startTime > t) {
      return false;
    }

    int n = 4;

    t = (Integer) Platform.getUIDefaults().get("Rare.Pointer.clickSize");

    if (t == null) {
      n = Platform.isTouchDevice()
          ? UIScreen.platformPixels(32)
          : 4;
    } else {
      n = UIScreen.platformPixels(t.intValue());
    }

    UIPoint p = releaseEvent.getLocationOnScreen();

    return (Math.abs(startPoint.x - p.x) <= n) && (Math.abs(startPoint.y - p.y) <= n);
  }

  public static boolean isOptimizationEnabled() {
    return RhinoContextFactory.isOptimizationEnabled();
  }

  /**
   * Executes a generic background task
   *
   * @param callable
   *          the runnable to be executed
   *
   * @return a Future representing pending completion of the task
   */
  static iCancelableFuture executeBackgroundTask(Callable callable, boolean shuttingDown) {
    if ((executorService == null) || shuttingDown) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    if (callable instanceof UIImage) {
      if (imageExecutorService != null) {
        return new CancelableFutureWrapper(imageExecutorService.submit(callable));
      }
    }

    return new CancelableFutureWrapper(executorService.submit(callable));
  }

  /**
   * Executes a generic background task
   *
   * @param runnable
   *          the runnable to be executed
   *
   * @return a Future representing pending completion of the task
   */
  static iCancelableFuture executeBackgroundTask(Runnable runnable, boolean shuttingDown) {
    if ((executorService == null) || shuttingDown) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    if (runnable instanceof UIImageIcon) {
      if (imageExecutorService != null) {
        return new CancelableFutureWrapper(imageExecutorService.submit(runnable));
      }
    }

    return new CancelableFutureWrapper(executorService.submit(runnable));
  }

  /**
   * Executes a Swing worker task
   *
   * @param task
   *          the task to be executed
   *
   * @return a Future representing pending completion of the task
   */
  static iCancelableFuture executeSwingWorkerTask(iWorkerTask task, boolean shuttingDown) {
    if ((executorService == null) || shuttingDown) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    return new CancelableFutureWrapper(executorService.submit(new CancelableWorker(task)));
  }

  static protected void stopBackgroundThreads() {
    if (executorService != null) {
      try {
        executorService.shutdownNow();
      } catch(Throwable e) {}

      executorService = null;
    }
  }

  static class WeakReferenceEx extends WeakReference implements iWeakReference {
    public WeakReferenceEx(Object r) {
      super(r);
    }
  }
}
