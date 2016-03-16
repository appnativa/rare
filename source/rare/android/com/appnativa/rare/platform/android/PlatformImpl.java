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

package com.appnativa.rare.platform.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.appnativa.rare.Platform;
import com.appnativa.rare.converters.iDataConverter;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.iTimer;
import com.appnativa.rare.net.ActionLink;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aPlatform;
import com.appnativa.rare.platform.android.ui.view.LabelView;
import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.chart.aChartHandler;
import com.appnativa.rare.ui.chart.aChartHandler.NoChartHandler;
import com.appnativa.rare.ui.dnd.DnDHelper;
import com.appnativa.rare.ui.dnd.iFlavorCreator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.SNumber;
import com.appnativa.util.iCancelable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PlatformImpl extends aPlatform {
  static Handler     handler;
  static ClassLoader urlClassLoader;

  static {
    urlClassLoader = Thread.currentThread().getContextClassLoader();
  }

  private final AppContext appContext;

  PlatformImpl(AppContext context) {
    appContext = context;
  }

  @Override
  public boolean browseURL(URL url) {
    return appContext.browseURL(url);
  }

  @Override
  public boolean canGenerateByteCode() {
    return false;
  }

  @Override
  public void setUseFullScreen(boolean use) {
    Activity a = Platform.getAppContext().getActivity();

    if (a instanceof MainActivity) {
      ((MainActivity) a).setUseFullScreen(use);
    }
  }

  public boolean isUseFullScreen() {
    Activity a = Platform.getAppContext().getActivity();

    if (a instanceof MainActivity) {
      return ((MainActivity) a).isUseFullScreen();
    }

    return (a.getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) != 0;
  }

  @Override
  public aChartHandler createChartHandler() {
    try {
      Class.forName("com.artfulbits.aiCharts.Base.ChartArea");

      return (aChartHandler) Class.forName("com.appnativa.rare.ui.chart.aicharts.ChartHandler").newInstance();
    } catch(Exception ignore) {
      return new NoChartHandler();
    }
  }

  public iPlatformComponent createErrorComponent(iPlatformIcon icon, String message) {
    LabelView tv = new LabelView(AppContext.getAndroidContext());

    tv.setText(message);

    if (icon != null) {
      tv.setIconPosition(IconPosition.TOP_CENTER);
      tv.setIcon(icon);
    }

    return new Component(tv);
  }

  @Override
  public iPlatformComponent createPlatformComponent(Object nativeComponent) {
    iPlatformComponent pc = Component.fromView((View) nativeComponent);

    if (pc == null) {
      pc = new Component((View) nativeComponent);
    }

    return pc;
  }

  @Override
  public iTimer createTimer(String name) {
    return new ATimer(name);
  }

  @Override
  public iPlatformComponent findPlatformComponent(Object c) {
    if (c instanceof iPlatformComponent) {
      return (iPlatformComponent) c;
    }

    while(c instanceof View) {
      Object o = ((View) c).getTag();

      if (o instanceof Component) {
        return (Component) o;
      }

      c = ((View) c).getParent();
    }

    return null;
  }

  public iWidget findWidgetForComponent(Component c) {
    iWidget w = (c == null)
                ? null
                : c.getWidget();

    while((w == null) && (c != null)) {
      c = (Component) c.getParent();

      if (c == null) {
        break;
      }

      w = c.getWidget();
    }

    return w;
  }

  @Override
  public iWidget findWidgetForComponent(Object c) {
    if (c instanceof Component) {
      return findWidgetForComponent((Component) c);
    }

    while(c instanceof View) {
      Object o = ((View) c).getTag();

      if (o instanceof Component) {
        return findWidgetForComponent((Component) o);
      }

      c = ((View) c).getParent();
    }

    return null;
  }

  public static Frame frameForComponent(iPlatformComponent c) {
    if (c != null) {
      View v = c.getView();

      while(v != null) {
        if (v.getTag() instanceof Frame) {
          return (Frame) v.getTag();
        }

        if (v.getParent() instanceof View) {
          v = (View) v.getParent();
        } else {
          v = null;
        }
      }
    }

    return (Frame) Platform.getAppContext().getWindowManager().getMainWindow();
  }

  @Override
  public void handlePlatformProperties(iWidget widget, Widget cfg, Map<String, Object> properties) {
    if (properties == null) {
      return;
    }

    super.handlePlatformProperties(widget, cfg, properties);

    String value = (String) properties.remove("android:id");
    int    n;
    float  f;

    if (value != null) {
      n = SNumber.intValue(value);

      if (n != 0) {
        widget.getDataComponent().getView().setId(n);
      }
    }

    value = (String) properties.remove("android:rotation");

    if (value != null) {
      f = SNumber.floatValue(value);

      if ((f != 0) && (Build.VERSION.SDK_INT > 10)) {
        try {
          Method m = View.class.getMethod("setRotation", float.class);

          m.invoke(widget, f);
        } catch(Throwable e) {
          Platform.ignoreException("trying to find setRotation(float)", e);
        }
      }
    }

    value = (String) properties.remove("android:alpha");

    if (value != null) {
      f = SNumber.floatValue(value);

      if ((f != 0) && (Build.VERSION.SDK_INT > 10)) {
        try {
          Method m = View.class.getMethod("setAlpha", float.class);

          m.invoke(widget, f);
        } catch(Throwable e) {
          Platform.ignoreException("trying to find setAlpha(float)", e);
        }
      }
    }

    value = (String) properties.remove("android:scrollbarStyle");

    if (value != null) {
      n = SNumber.intValue(value);

      if (n != 0) {
        widget.getDataComponent().getView().setScrollBarStyle(n);
      }
    }

    value = (String) properties.remove("android:scrollbarSize");

    if (value != null) {
      n = SNumber.intValue(value);

      if ((n != 0) && (Build.VERSION.SDK_INT > 15)) {
        try {
          Method m = View.class.getMethod("setScrollBarSize", int.class);

          m.invoke(widget, n);
        } catch(Throwable e) {
          Platform.ignoreException("trying to find setScrollBarSize(int)", e);
        }
      }
    }
  }

  @Override
  public void invokeLater(Runnable runnable) {
    handler.post(runnable);
  }

  @Override
  public void invokeLater(Runnable runnable, int delayMillis) {
    handler.postDelayed(runnable, delayMillis);
  }

  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    return PlatformHelper.loadClass(name);
  }

  @Override
  public void loadUIProperties(iWidget context, ActionLink link, UIProperties defs) throws IOException {
    super.loadUIProperties(context, link, defs);

    Drawable d = Platform.getUIDefaults().getDrawable("Rare.ActionBar.background");

    if (d != null) {
      appContext.getActivity().getActionBar().setBackgroundDrawable(d);
    }

    if (appContext.getUIDefaults().getBoolean("Rare.android.adjustTheme", true)) {
      if (!appContext.isPlatformColorTheme()) {
        if ((d == null) && (appContext.getActivity().getActionBar() != null)) {
          d = ColorUtils.getBackground().getDrawable();
          appContext.getActivity().getActionBar().setBackgroundDrawable(d);
        }

        if (ColorUtils.getForeground().isDarkColor()) {
          appContext.getActivity().setTheme(android.R.style.Theme_Holo_Light);
        } else {
          appContext.getActivity().setTheme(android.R.style.Theme_Holo);
        }

        int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");

        if (titleId > 0) {
          View v = appContext.getActivity().findViewById(titleId);

          if (v instanceof TextView) {
            ColorUtils.getForeground().setTextColor((TextView) v);
          }
        }
      }
    }
  }

  @Override
  public boolean mailTo(String uri) {
    if ((uri != null) && uri.startsWith("mailto:")) {
      uri = uri.substring(7);
    }

    Intent sendIntent = new Intent(Intent.ACTION_SEND);

    if (uri != null) {
      sendIntent.putExtra(Intent.EXTRA_SUBJECT, uri.split(";"));
    }

    appContext.getActivity().startActivity(sendIntent);

    return true;
  }

  @Override
  public boolean mailTo(String address, String subject, String body) {
    Intent sendIntent = new Intent(Intent.ACTION_SEND);

    if (address != null) {
      sendIntent.putExtra(Intent.EXTRA_EMAIL, address.split(";"));
    }

    if (subject != null) {
      sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }

    if (body != null) {
      sendIntent.putExtra(Intent.EXTRA_TEXT, body);
    }

    sendIntent.setType("message/rfc2822");
    appContext.getActivity().startActivity(sendIntent);

    return true;
  }

  @Override
  public void registerWithWidget(iPlatformComponent component, iWidget context) {
    if (component != null) {
      component.putClientProperty(iConstants.RARE_WIDGET_COMPONENT_PROPERTY, context);
    }
  }

  @Override
  public void unregisterWithWidget(iPlatformComponent component) {
    if (component != null) {
      component.putClientProperty(iConstants.RARE_WIDGET_COMPONENT_PROPERTY, null);
    }
  }

  public Object windowForComponent(iPlatformComponent c) {
    Frame f = frameForComponent(c);

    return (f == null)
           ? null
           : f.getUIWindow();
  }

  @Override
  public int getAndroidVersion() {
    return VERSION.SDK_INT;
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
    return appContext.getActivity().getCacheDir();
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
    return 0;
  }

  @Override
  public String getOsType() {
    return "android";
  }

  @Override
  public float getOsVersion() {
    return VERSION.SDK_INT;
  }

  @Override
  public iPlatformComponent getPlatformComponent(Object c) {
    if (c instanceof iPlatformComponent) {
      return (iPlatformComponent) c;
    }

    while(c instanceof View) {
      Object o = ((View) c).getTag();

      if (o instanceof Component) {
        return (Component) o;
      }

      c = ((View) c).getParent();
    }

    return null;
  }

  @Override
  public String getPlatformType() {
    return "android";
  }

  @Override
  public double getPlatformVersion() {
    return VERSION.SDK_INT;
  }

  @Override
  public iFlavorCreator getTransferFlavorCreator() {
    return DnDHelper.getInstance();
  }

  @Override
  public String getUserAgent() {
    return Rare.getUserAgent();
  }

  @Override
  public WindowViewer getWindowViewerForComponent(iPlatformComponent c) {
    Frame f = frameForComponent(c);

    return (WindowViewer) ((f == null)
                           ? null
                           : f.getViewer());
  }

  @Override
  public boolean isAndroid() {
    return true;
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
    View v = c.getView();
    View p = container.getView();

    while(v != null) {
      if (v == p) {
        return true;
      }

      if (!(v.getParent() instanceof View)) {
        break;
      }

      v = (View) v.getParent();
    }

    return false;
  }

  @Override
  public boolean isHTMLSupportedInLabels() {
    return true;
  }

  @Override
  public boolean isIOS() {
    return false;
  }

  @Override
  public boolean isInitialized() {
    return (appContext != null) && appContext.isInitialized();
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
    return false;
  }

  @Override
  public boolean isMac() {
    return false;
  }

  public boolean isShuttingDown() {
    return (appContext == null)
           ? true
           : appContext.isShuttingDown();
  }

  @Override
  public boolean isSwing() {
    return false;
  }

  @Override
  public boolean isTouchDevice() {
    return true;
  }

  @Override
  public boolean isTouchableDevice() {
    return true;
  }

  @Override
  public boolean isUIThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  @Override
  public boolean isUnix() {
    return false;
  }

  @Override
  public boolean isWindows() {
    return false;
  }

  @Override
  protected void handleUIProperty(iWidget context, UIProperties defs, String property, Object value) {
    if (property.equals("Rare.http.disableKeepAlive") && Boolean.TRUE.equals(value)) {
      JavaURLConnection.disableKeepAlive = true;
    } else if (property.equals("Rare.http.disableHTTPSKeepAlive") && Boolean.TRUE.equals(value)) {
      JavaURLConnection.disableHTTPSKeepAlive = true;
    }
  }

  static class InvokeLater implements Runnable {
    Runnable runnable;

    InvokeLater(Runnable r) {
      runnable = r;
    }

    @Override
    public void run() {
      try {
        runnable.run();
      } finally {
        synchronized(this) {
          notifyAll();
        }
      }
    }
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
}
