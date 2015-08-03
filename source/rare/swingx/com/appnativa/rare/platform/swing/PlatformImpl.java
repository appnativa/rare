/*
 * @(#)PlatformImpl.java   2012-04-27
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing;

import java.awt.Desktop;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iTimer;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.net.URLEncoder;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aPlatform;
import com.appnativa.rare.platform.swing.ui.view.LabelView;
import com.appnativa.rare.ui.ActionComponent;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.chart.aChartHandler.NoChartHandler;
import com.appnativa.rare.ui.dnd.TransferFlavor;
import com.appnativa.rare.ui.dnd.iFlavorCreator;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;
import com.appnativa.util.iCancelable;

public class PlatformImpl extends aPlatform {
  static ClassLoader     urlClassLoader;

  /**
   * Variable for whether or not we're on Windows.
   */
  private static boolean isWindows = false;

  /**
   * Variable for whether or not we're on Solaris.
   */
  private static boolean isSolaris = false;

  /**
   * Variable for whether or not we're on MacOSX.
   */
  private static boolean isMac     = false;

  /**
   * Variable for whether or not we're on Linux.
   */
  private static boolean isLinux   = false;

  /**
   * Initialize the settings statically.
   */
  static {
    // get the operating system
    String os = "Windows";

    try {
      os = System.getProperty("os.name", "Windows");
    } catch (Exception ignore) {
    }

    isWindows = os.contains("Windows");

    if (!isWindows) {
      if (os.contains("Linux")) {
        isLinux = true;
      } else if (os.contains("Mac OS")) {
        isMac = true;
      } else if (os.contains("Solaris") || os.contains("SunOS")) {
        isSolaris = true;
      }
    }

    ClassLoader cl = Rare.class.getClassLoader();

    if (!(cl instanceof StandardClassLoader)) {
      try {
        cl = (StandardClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
          @Override
          public Object run() {
            return new StandardClassLoader();
          }
        });
        Thread.currentThread().setContextClassLoader(cl);
      } catch (Throwable e) {
        Logger.getAnonymousLogger().log(Level.FINE, "", e);
      }
    }

    urlClassLoader = cl;
  }

  protected AppContext   appContext;
  private float          osVersion;

  public PlatformImpl(AppContext context) {
    appContext = context;
  }

  @Override
  public void addJarURL(URL url) {
    if ((url != null) && (urlClassLoader instanceof StandardClassLoader)) {
      String prot = url.getProtocol();

      if (prot.equals("jar")) {
        return;
      }

      ((StandardClassLoader) urlClassLoader).addRepository(url);
    }
  }

  @Override
  public boolean browseURL(URL url) {
    try {
      Desktop.getDesktop().browse(url.toURI());

      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public boolean canGenerateByteCode() {
    return !Rare.isInSandbox();
  }

  @Override
  public Object createChartHandler() {
    try {
      return Class.forName("com.appnativa.rare.ui.chart.jfreechart.ChartHandler").newInstance();
    } catch (Exception ignore) {
      return new NoChartHandler();
    }
  }

  public iPlatformComponent createErrorComponent(iPlatformIcon icon, String message) {
    ActionComponent a = new ActionComponent(new LabelView(message));

    a.setIcon(icon);

    return a;
  }

  /**
   * Creates a new instance of the class represented by the specified name
   *
   * @param className
   *          the name of the class to load
   *
   * @return the new object or null if the object could not be instantiated
   */
  @Override
  public Object createObject(String className) {
    try {
      return Class.forName(className, true, urlClassLoader).newInstance();
    } catch (Throwable ignore) {
      Platform.ignoreException("createObject:" + className, ignore);

      return null;
    }
  }

  @Override
  public iPlatformComponent createPlatformComponent(Object nativeComponent) {
    if (nativeComponent instanceof iPlatformComponent) {
      return (iPlatformComponent) nativeComponent;
    }

    return Component.fromView(((javax.swing.JComponent) nativeComponent));
  }

  @Override
  public iTimer createTimer(String name) {
    return new ATimer(name);
  }

  @Override
  public void debugLog(String msg) {
    AppContext.debugLog(msg);
  }

  @Override
  public iPlatformComponent findPlatformComponent(Object o) {
    if (o instanceof Component) {
      return (Component) o;
    }

    if (!(o instanceof java.awt.Component)) {
      return null;
    }

    java.awt.Component c = (java.awt.Component) o;

    while ((c != null) && !(c instanceof JComponent)) {
      c = c.getParent();
    }

    while (c instanceof JComponent) {
      Component cc = (Component) ((JComponent) c).getClientProperty(Component.RARE_COMPONENT_PROXY_PROPERTY);

      if (cc != null) {
        return cc;
      }

      c = c.getParent();
    }

    return null;
  }

  @Override
  public iWidget findWidgetForComponent(Object o) {
    if (o instanceof Component) {
      Component c = (Component) o;

      if (c.getWidget() != null) {
        return c.getWidget();
      }

      o = c.getView().getParent();
    }

    if (!(o instanceof java.awt.Component)) {
      return null;
    }

    java.awt.Component c = (java.awt.Component) o;

    while (!(c instanceof JComponent)) {
      c = c.getParent();
    }

    while (c instanceof JComponent) {
      Component cc = (Component) ((javax.swing.JComponent) c).getClientProperty(Component.RARE_COMPONENT_PROXY_PROPERTY);

      if ((cc != null) && (cc.getWidget() != null)) {
        return cc.getWidget();
      }

      c = c.getParent();
    }

    return null;
  }

  @Override
  public int getAndroidVersion() {
    return 0;
  }

  @Override
  public iPlatformAppContext getAppContext() {
    return appContext;
  }

  @Override
  public int getAppInstanceCount() {
    return 1;
  }

  public ClassLoader getApplicationClassLoader() {
    return urlClassLoader;
  }

  @Override
  public File getCacheDir() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public iDataConverter getDataConverter(Class cls) {
    return Rare.getDataConverter(cls);
  }

  @Override
  public Class getDataConverterClass(String name) throws ClassNotFoundException {
    return Rare.getDataConverterClass(name);
  }

  @Override
  public iFunctionHandler getFunctionHandler() {
    return appContext.getFunctionHandler();
  }

  public double getJavaFXVersion() {
    return getPlatformVersion();
  }

  @Override
  public String getOsType() {
    if (isWindows()) {
      return "windows";
    }

    if (isLinux()) {
      return "linux";
    }

    if (isMac()) {
      return "os x";
    }

    return "unix";
  }

  @Override
  public float getOsVersion() {
    if (osVersion == 0) {
      String s = ".01";

      try {
        s = System.getProperty("os.version", ".01");
      } catch (Exception ignore) {
      }

      final int len = s.length();
      int i = 0;

      while (i < len) {
        if (Character.isDigit(s.charAt(i))) {
          break;
        }

        i++;
      }

      if (i < len) {
        osVersion = SNumber.floatValue(s.substring(i));
      }

      if (osVersion == 0) {
        osVersion = .01f;
      }
    }

    return osVersion;
  }

  @Override
  public iPlatformComponent getPlatformComponent(Object o) {
    if (o instanceof Component) {
      return (Component) o;
    }
    if(o instanceof java.util.EventObject) {
      o=((java.util.EventObject)o).getSource();
    }

    if (!(o instanceof javax.swing.JComponent)) {
      return null;
    }

    while (o instanceof javax.swing.JComponent) {
      Object c = ((javax.swing.JComponent) o).getClientProperty(Component.RARE_COMPONENT_PROXY_PROPERTY);

      if (c instanceof Component) {
        return (Component) c;
      }

      o = ((javax.swing.JComponent) o).getParent();
    }

    return null;
  }

  @Override
  public String getPlatformType() {
    return "swing";
  }

  @Override
  public double getPlatformVersion() {
    return 2.0d;
  }

  @Override
  public boolean isDesktop() {
    return true;
  }

  @Override
  public iFlavorCreator getTransferFlavorCreator() {
    return new FlavorCreator();
  }

  @Override
  public String getUserAgent() {
    return Rare.getUserAgent();
  }

  @Override
  public WindowViewer getWindowViewerForComponent(iPlatformComponent c) {
    Window w = SwingUtilities.windowForComponent(c.getView());

    if (w != null) {
      JRootPane p = null;

      if (w instanceof JFrame) {
        p = ((JFrame) w).getRootPane();
      } else if (w instanceof JWindow) {
        p = ((JWindow) w).getRootPane();
      } else if (w instanceof JDialog) {
        p = ((JDialog) w).getRootPane();
      }

      Frame f = (p == null) ? null : (Frame) p.getClientProperty(Component.RARE_COMPONENT_PROXY_PROPERTY);

      if (f != null) {
        return f.getWindowViewer();
      }
    }
    return appContext.getWindowViewer();
  }

  @Override
  public void invokeLater(Runnable r) {
    SwingUtilities.invokeLater(r);
  }

  @Override
  public void invokeLater(final Runnable r, int delay) {
    javax.swing.Timer t = new javax.swing.Timer(delay, new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        r.run();

      }
    });
    t.setRepeats(false);
    t.start();
  }

  @Override
  public boolean isAndroid() {
    return false;
  }

  @Override
  public boolean isDebugEnabled() {
    return Rare.isDebugEnabled();
  }

  @Override
  public boolean isDebuggingEnabled() {
    return false;
  }

  @Override
  public boolean isDescendingFrom(iPlatformComponent c, iPlatformComponent container) {
    return false;
  }

  @Override
  public boolean isEmbedded() {
    return false;
  }

  @Override
  public boolean isHTMLSupportedInLabels() {
    return true;
  }

  @Override
  public boolean isInitialized() {
    return (appContext != null) && appContext.isInitialized();
  }

  @Override
  public boolean isIOS() {
    return false;
  }

  @Override
  public boolean isJava() {
    return true;
  }

  @Override
  public boolean isJavaFX() {
    return false;
  }

  @Override
  public boolean isLinux() {
    return isLinux;
  }

  @Override
  public boolean isMac() {
    return isMac;
  }

  public boolean isShuttingDown() {
    return (appContext == null) ? true : appContext.isShuttingDown();
  }

  @Override
  public boolean isSwing() {
    return true;
  }

  @Override
  public boolean isUIThread() {
    return SwingUtilities.isEventDispatchThread();
  }

  @Override
  public boolean isUnix() {
    return isLinux || isSolaris;
  }

  @Override
  public boolean isWindows() {
    return isWindows;
  }

  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    return PlatformHelper.loadClass(name);
  }

  @Override
  public boolean mailTo(String uri) {
    if(uri==null) {
      return false;
    }
    if ( !uri.startsWith("mailto:")) {
      uri = "mailto:" + uri;
    }

    try {
      URI u =new URI(uri);
      Desktop.getDesktop().mail(u);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean mailTo(String address, String subject, String body) {
    StringBuilder sb = new StringBuilder();

    if ((address == null) || !address.startsWith("mailto:")) {
      sb.append("mailto:");

      if (address != null) {
        address = URLEncoder.encode(address);
      }
    }

    if (address != null) {
      sb.append(address);
    }

    if (subject != null) {
      sb.append("?subject=").append(URLEncoder.encode(subject));
    }

    if (body != null) {
      if (subject == null) {
        sb.append("?body=").append(URLEncoder.encode(body));
      } else {
        sb.append("&body=").append(URLEncoder.encode(body));
      }
    }

    try {
      URI u = new URI(sb.toString());

      Desktop.getDesktop().mail(u);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void registerWithWidget(iPlatformComponent component, iWidget context) {
    component.putClientProperty(iConstants.RARE_WIDGET_COMPONENT_PROPERTY, context);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  void requestToggleFullScreen(Window window) {
    try {
      Class appClass = Class.forName("com.apple.eawt.Application");
      Class params[] = new Class[] {};

      Method getApplication = appClass.getMethod("getApplication", params);
      Object application = getApplication.invoke(appClass);
      Method requestToggleFulLScreen = application.getClass().getMethod("requestToggleFullScreen", Window.class);

      requestToggleFulLScreen.invoke(application, window);
    } catch (Exception e) {

    }
  }

  @Override
  public void setUseFullScreen(boolean use) {
    Window w = (Window) Platform.getAppContext().getWindowManager().getUIWindow();
    if (!(w instanceof JFrame)) {
      return;
    }
    if (Platform.isMac()) {
      UIDimension sd = ScreenUtils.getScreenSize();
      UIDimension wd = Platform.getWindowViewer().getSize();
      if (use) {
        if (!wd.equals(sd)) {
          requestToggleFullScreen(w);
        }
      } else {
        if (wd.equals(sd)) {
          requestToggleFullScreen(w);
        }
      }
    } else {
      ((JFrame) w).setExtendedState(use ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
    }
  }

  @Override
  public void unregisterWithWidget(iPlatformComponent component) {
    component.putClientProperty(iConstants.RARE_WIDGET_COMPONENT_PROPERTY, null);
  }

  public static boolean isRunningAsApplet() {
    return false;
  }

  public static boolean isWebStart() {
    return false;
  }

  private static class ATimer implements iTimer {
    Timer timer;

    public ATimer(String name) {
      timer = new Timer(name, true);
    }

    @Override
    public void cancel() {
      timer.cancel();
    }

    @Override
    public iCancelable schedule(final Runnable task, long delay) {
      TimerTask tt = new TimerTask() {
        @Override
        public void run() {
          task.run();
        }
      };

      timer.schedule(tt, delay);

      return new CancelableTimerTask(tt);
    }

    @Override
    public iCancelable schedule(final Runnable task, long delay, long period) {
      TimerTask tt = new TimerTask() {
        @Override
        public void run() {
          task.run();
        }
      };

      timer.schedule(tt, delay, period);

      return new CancelableTimerTask(tt);
    }
  }

  private static class CancelableTimerTask implements iCancelable {
    boolean         canceled;
    final TimerTask task;

    public CancelableTimerTask(TimerTask task) {
      this.task = task;
    }

    @Override
    public void cancel(boolean canInterrupt) {
      task.cancel();
      canceled = true;
    }

    @Override
    public boolean isCanceled() {
      return canceled;
    }

    @Override
    public boolean isDone() {
      return false;
    }
  }

  static class FlavorCreator implements iFlavorCreator {
    public TransferFlavor createFlavor(DataFlavor df) {
      return new TransferFlavor(df.getHumanPresentableName(), df, df.getMimeType());
    }

    @Override
    public TransferFlavor createFlavor(Object df) {
      return createFlavor((DataFlavor) df);
    }

    TransferFlavor createFlavor(String mimeType) {
      try {
        DataFlavor df = new DataFlavor(mimeType);

        return new TransferFlavor(df.getHumanPresentableName(), df, mimeType);
      } catch (ClassNotFoundException e) {
        return new TransferFlavor(mimeType, null, mimeType);
      }
    }

    @Override
    public TransferFlavor createFlavor(String name, String... mimeTypes) {
      DataFlavor df = new DataFlavor(mimeTypes[0], name);

      return new TransferFlavor(name, df, mimeTypes);
    }

    @Override
    public TransferFlavor getFileListFlavor() {
      return createFlavor(DataFlavor.javaFileListFlavor);
    }

    @Override
    public TransferFlavor getHTMLFlavor() {
      try {
        DataFlavor a[] = new DataFlavor[3];

        a[0] = new DataFlavor("text/html;class=java.lang.String");
        a[1] = new DataFlavor("text/html;class=java.io.Reader");
        a[2] = new DataFlavor("text/html;charset=unicode;class=java.io.InputStream");

        String s[] = new String[3];

        for (int i = 0; i < 3; i++) {
          s[i] = a[i].getMimeType();
        }

        return new TransferFlavor("Unicode HTML", a, s);
      } catch (ClassNotFoundException e) {
        return new TransferFlavor("Unicode String", null, "text/plain;class=java.lang.String");
      }
    }

    @Override
    public TransferFlavor getImageFlavor() {
      return createFlavor(DataFlavor.imageFlavor);
    }

    @Override
    public TransferFlavor getStringFlavor() {
      try {
        DataFlavor a[] = new DataFlavor[5];

        a[0] = DataFlavor.stringFlavor;
        a[1] = new DataFlavor("text/plain;class=java.lang.String");
        a[2] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=java.lang.String");
        a[3] = new DataFlavor("text/plain;class=java.io.Reader");
        a[4] = new DataFlavor("text/plain;charset=unicode;class=java.io.InputStream");

        String s[] = new String[5];

        for (int i = 0; i < 5; i++) {
          s[i] = a[i].getMimeType();
        }

        return new TransferFlavor("Unicode String", a, s);
      } catch (ClassNotFoundException e) {
        return new TransferFlavor("Unicode String", null, "text/plain;class=java.lang.String");
      }
    }

    @Override
    public TransferFlavor getURLFlavor() {
      return createFlavor("application/x-java-url; class=java.net.URL");
    }

    @Override
    public TransferFlavor getURLListFlavor() {
      return createFlavor("text/uri-list; class=java.lang.String");
    }
  }
}
