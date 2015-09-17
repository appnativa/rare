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
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import android.graphics.PixelFormat;

import android.net.Uri;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import android.provider.Settings;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;

import android.util.Log;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.webkit.CookieSyncManager;

import android.widget.Toast;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iFunctionCallback;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.HTTPResponseCache;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.android.ui.ActionBarHandler;
import com.appnativa.rare.platform.android.ui.view.PopupWindowEx;
import com.appnativa.rare.spot.Application;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.UIAction;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIPopupMenu;
import com.appnativa.rare.ui.WaitCursorHandler;
import com.appnativa.rare.ui.WaitCursorHandler.SpinnerDialog;
import com.appnativa.rare.ui.WidgetListener;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.ActionEvent;
import com.appnativa.rare.ui.event.EventListenerList;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.viewer.MenuBarViewer;
import com.appnativa.rare.viewer.aContainer;
import com.appnativa.rare.viewer.aViewer;
import com.appnativa.rare.widget.aPlatformWidget;
import com.appnativa.rare.widget.aWidget;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.IdentityArrayList;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Don DeCoteau
 */
public class MainActivity extends Activity implements iActivity {
  public static final int DLG_CUSTOM   = 100;
  public static final int DLG_PROGRESS = 1;

  static {
    System.setProperty("spot.optimize", "true");
  }

  EventListenerList           listenerList;
  iPlatformMenuBar            menuBar;
  protected aActivityListener activityListener;
  protected Rare              rare;
  private long                backTime;
  private boolean             deleteCacheOnExit;
  private Object              lastEvent;
  private KeyEvent            lastKeyEvent;
  private MotionEvent         lastMotionEvent;
  private boolean             menuSet;
  private Menu                optionsMenu;
  private Object              responseCache;
  private TextToSpeech        textToSpeech;
  private Toast               toast;
  private long                toastTime;
  private boolean             uiLocked;
  private Runnable            lowMemoryWarningHandler;
  PopupWindowEx               popupWindow;
  protected boolean           googleGlass;
  protected GestureDetector   googleGestureDetector;

  public void addBackPressListener(iBackPressListener l) {
    if (l != null) {
      if (listenerList == null) {
        listenerList = new EventListenerList();
      }

      listenerList.add(iBackPressListener.class, l);
    }
  }

  public void addPopupWindow(PopupWindowEx popup) {
    getAppContext().getActiveWindows().add(popup);

    if (popup.isTransient()) {
      popupWindow = popup;
    }
  }

  public boolean isGoogleGlass() {
    return googleGlass;
  }

  public void applicationConfigured() {}

  public void applicationObjectCreated(Application app, URL url) {
    if (app.autoAdjustFontSize.booleanValue()) {
      UIFont f = adjustSystemFont(FontUtils.getSystemFont());

      FontUtils.setSystemFont(f);
    }
  }

  public void closeDialogs() {
    cancelToast();
    WaitCursorHandler.hide();
    closeContextMenu();
    closeOptionsMenu();
  }

  public void setUseFullScreen(boolean use) {
    View decorView = getWindow().getDecorView();

    if (use) {
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                      | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION    // hide nav bar
                                      | View.SYSTEM_UI_FLAG_FULLSCREEN    // hide status bar
                                      | View.SYSTEM_UI_FLAG_IMMERSIVE);
    } else {
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                      | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
  }

  public boolean isUseFullScreen() {
    View decorView = getWindow().getDecorView();

    return (decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) != 0;
  }

  public TextToSpeech createTextToSpeech(final iFunctionCallback cb) {
    silenceTextToSpeech();
    textToSpeech = new TextToSpeech(this, new OnInitListener() {
      public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
          if (cb != null) {
            cb.finished(false, textToSpeech);
          } else {
            configureTextToSpeech(textToSpeech);
          }
        } else {
          cb.finished(true, Integer.valueOf(status));
        }
      }
    });

    return textToSpeech;
  }

  public boolean dispatchKeyEvent(KeyEvent event) {
    lastEvent    = event;
    lastKeyEvent = event;

    if ((toast != null) && (toastTime + 250) < event.getDownTime()) {
      cancelToast();
    }

    if (uiLocked) {
      if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_UP)) {
        handleBackKeyPressed(event.getDownTime());
      }

      return true;
    }

    return super.dispatchKeyEvent(event);
  }

  public boolean dispatchTouchEvent(MotionEvent event) {
    lastEvent       = event;
    lastMotionEvent = event;

    if ((toast != null) && (toastTime + 250) < event.getDownTime()) {
      cancelToast();
    }

    if (uiLocked) {
      return true;
    }

    if ((popupWindow != null) && (event.getAction() == MotionEvent.ACTION_DOWN) && popupWindow.isOutsideTouch(event)) {
      popupWindow.cancelPopup(false);

      return true;
    }

    boolean dispatched = super.dispatchTouchEvent(event);

    if (!dispatched && PlatformHelper.isKeyboardVisible()) {
      PlatformHelper.hideVirtualKeyboard(Platform.getWindowViewer().getDataView());
    }

    return dispatched;
  }

  public void enableHttpResponseCache(String name, int size, boolean deleteOnExit, boolean usePlatform) {
    this.deleteCacheOnExit = deleteOnExit;

    if (usePlatform && (Build.VERSION.SDK_INT > 13)) {
      try {
        File httpCacheDir = new File(getCacheDir(), name);

        responseCache = Class.forName("android.net.http.HttpResponseCache").getMethod("install", File.class,
                                      long.class).invoke(null, httpCacheDir, size * 1024 * 1024);

        return;
      } catch(Exception e) {
        Platform.ignoreException("could no install platform response cache", e);
      }
    }

    try {
      responseCache = HTTPResponseCache.installResponseCache(name, size, false);
    } catch(Exception e) {
      Platform.ignoreException("could no install response cache", e);
    }
  }

  public aActivityListener getActivityListener() {
    return activityListener;
  }

  public Activity getAndroidActivity() {
    return this;
  }

  public iPlatformAppContext getAppContext() {
    return (rare == null)
           ? null
           : rare.getAppContext();
  }

  public Uri getIntentUri() {
    return getIntent().getData();
  }

  public Object getLastEvent() {
    return lastEvent;
  }

  public KeyEvent getLastKeyEvent() {
    return lastKeyEvent;
  }

  public MotionEvent getLastMotionEvent() {
    return lastMotionEvent;
  }

  public TextToSpeech getTextToSpeech() {
    return textToSpeech;
  }

  public void handleConfigurationChanged(final Configuration newConfig) {
    ((aAppContext) rare.getAppContext()).handleConfigurationWillChange(newConfig);
    WaitCursorHandler.onConfigurationChanged();
    Platform.invokeLater(new Runnable() {
      public void run() {
        WaitCursorHandler.onConfigurationChanged();
        ((aAppContext) rare.getAppContext()).handleConfigurationChanged(newConfig);
      }
    });
  }

  public void handleDialogOnBackPressed(Dialog dialog) {
    if ((activityListener != null) && (rare != null)) {
      if (!activityListener.onBackPressed(rare.getAppContext(), this, dialog)) {
        dialog.dismiss();
        super.onBackPressed();
      }
    }
  }

  public boolean isSpinnerDialog(Dialog dialog) {
    return dialog instanceof SpinnerDialog;
  }

  public boolean isUILocked() {
    return uiLocked;
  }

  public boolean isUsingDebuggingBridge() {
    try {
      int adb = Settings.Secure.getInt(getContentResolver(), Settings.Secure.ADB_ENABLED, 0);

      return adb == 1;
    } catch(Exception ignore) {
      return false;
    }
  }

  public void lockUI() {}

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    enableSmoothPainting();
  }

  @Override
  public void onBackPressed() {
    if (rare != null) {
      if (listenerList != null) {
        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i] == iBackPressListener.class) {
            if (((iBackPressListener) listeners[i + 1]).onBackKeyPressed(this)) {
              return;
            }
          }
        }
      }

      if (activityListener != null) {
        if (activityListener.onBackPressed(rare.getAppContext(), this, null)) {
          return;
        }
      }

      if (rare.isBackPressedHandled()) {
        return;
      }
    }

    super.onBackPressed();
  }

  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (googleGlass && passKeyToFirstListener(keyCode, event)) {
      return true;
    }

    return super.onKeyUp(keyCode, event);
  }

  public void onConfigurationChanged(Configuration newConfig) {
    if (rare != null) {
      handleConfigurationChanged(newConfig);
      super.onConfigurationChanged(newConfig);
      PlatformHelper.initialize(this, getWindowManager().getDefaultDisplay());
      rare.getWindowManager().update();
    } else {
      super.onConfigurationChanged(newConfig);
    }
  }

  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    googleGlass          = "Google".equalsIgnoreCase(Build.MANUFACTURER) && Build.MODEL.startsWith("Glass");
    PlatformImpl.handler = new Handler();
    setInitialOptions();

    Uri uri = getIntent().getData();

    if (uri != null) {
      System.setProperty("rare.intentUri", uri.toString());
    }

    createContent(icicle);
  }

  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    if (v instanceof iContextMenuInfoHandler) {
      ((iContextMenuInfoHandler) v).handleContextMenuInfo(v, menuInfo);
    }

    final iWidget w = Platform.findWidgetForComponent(v);

    if (!(w instanceof MenuBarViewer)) {
      try {
        final UIPopupMenu pm;

        if (w.getDataComponent() instanceof iContextMenuHandler) {
          pm = ((iContextMenuHandler) w.getDataComponent()).getPopupMenu(v, menuInfo);
        } else {
          pm = (w instanceof aPlatformWidget)
               ? ((aPlatformWidget) w).getPopupMenu()
               : null;
        }

        Platform.invokeLater(new Runnable() {
          public void run() {
            pm.show(w, true);
          }
        });
      } catch(Throwable e) {
        Platform.ignoreException(null, e);
      }
    }

    super.onCreateContextMenu(menu, v, menuInfo);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    try {
      iPlatformMenuBar mb = getMenuBar();

      if (mb != null) {
        if (menu == optionsMenu) {
          menu.clear();
        }

        mb.createMenu(menu);
        menuSet = true;
      }

      optionsMenu = menu;
    } catch(Throwable e) {
      Platform.ignoreException(null, e);
    }

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();

    if ((rare != null) && (lowMemoryWarningHandler != null)) {
      lowMemoryWarningHandler.run();
    }
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    try {
      if ((VERSION.SDK_INT > 10) && (item.getItemId() == ActionBarHandler.getHomeMenuId(this))) {
        UIAction a = rare.getAppContext().getAction("Rare.action.home");

        if (a != null) {
          ActionEvent e = new ActionEvent(item);

          a.actionPerformed(e);

          return true;
        } else if (activityListener != null) {
          if (activityListener.onHomeActionPressed(rare.getAppContext(), this, null)) {
            return true;
          }
        } else {
          if (handleHomeActionPressed(item)) {
            return true;
          }
        }
      } else {
        iPlatformMenuBar mb  = getMenuBar();
        boolean          ret = false;

        if (mb != null) {
          ret = mb.handleMenuItemSeleceted(item);
        }

        if (!ret) {
          return super.onOptionsItemSelected(item);
        }
      }
    } catch(Throwable e) {
      Platform.ignoreException(null, e);
    }

    return false;
  }

  @Override
  public void onOptionsMenuClosed(Menu menu) {
    try {
      iPlatformMenuBar mb = getMenuBar();

      if (mb != null) {
        mb.menuClosed(menu);
      }
    } catch(Throwable e) {
      Platform.ignoreException(null, e);
    }

    super.onOptionsMenuClosed(menu);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    try {
      iPlatformMenuBar mb = getMenuBar();

      if (mb != null) {
        mb.menuWillBecomeVisible(menu);
      }
    } catch(Throwable e) {
      Platform.ignoreException(null, e);
    }

    return super.onPrepareOptionsMenu(menu);
  }

  /**
   * Passes the key event to the first registered listener in the workspace.
   * This is useful when working wit wearables where you want to control who
   * gets the key event
   *
   * @param keyCode
   *          the key code
   * @param event
   *          the key event
   * @return true if a listener was found; false otherwise
   */
  public boolean passKeyToFirstListener(int keyCode, KeyEvent event) {
    if (Platform.isInitialized()) {
      aViewer v = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();

      if (v != null) {
        WidgetListener wl = (WidgetListener) findFrstKeyListener(v);

        if (wl != null) {
          wl.onKey(v.getDataView(), keyCode, event);

          return true;
        }
      }
    }

    return false;
  }

  public void removeBackPressListener(iBackPressListener l) {
    if ((l != null) && (listenerList != null)) {
      listenerList.remove(iBackPressListener.class, l);
    }
  }

  public void removePopupWindow(PopupWindowEx popup) {
    IdentityArrayList windows = getAppContext().getActiveWindows();

    windows.remove(popup);

    int    len = windows.size();
    Object o   = (len == 0)
                 ? null
                 : windows.get(len - 1);

    if (o instanceof PopupWindowEx) {
      popupWindow = (PopupWindowEx) o;

      if (!popupWindow.isTransient()) {
        popupWindow = null;
      }
    } else {
      popupWindow = null;
    }
  }

  public void setActivityListener(aActivityListener l) {
    activityListener = l;
  }

  public void setLowMemoryWarningHandler(Runnable runnable) {
    lowMemoryWarningHandler = runnable;
  }

  public void setRareInstance(Rare rare) {
    this.rare = rare;
  }

  public void showToast(Toast t) {
    cancelToast();
    toast = t;

    if (t != null) {
      toastTime = SystemClock.uptimeMillis();
      t.show();
    }
  }

  public void silenceTextToSpeech() {
    try {
      if (textToSpeech != null) {
        textToSpeech.stop();
        textToSpeech.shutdown();
      }
    } catch(Throwable ignore) {}

    textToSpeech = null;
  }

  public void speak(final String text, final HashMap<String, String> utterance, final iFunctionCallback cb) {
    final UtteranceProgressListener upl = new UtteranceProgressListener() {
      @Override
      public void onDone(String utteranceId) {
        if (cb != null) {
          cb.finished(false, utteranceId);
        }
      }
      @Override
      public void onError(String utteranceId) {
        if (cb != null) {
          cb.finished(true, utteranceId);
        }
      }
      @Override
      public void onStart(String utteranceId) {}
    };

    if (textToSpeech != null) {
      textToSpeech.setOnUtteranceProgressListener(upl);
      textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, utterance);
    } else {
      createTextToSpeech(new iFunctionCallback() {
        public void finished(boolean canceled, Object returnValue) {
          if (canceled) {
            cb.finished(canceled, returnValue);
          } else {
            TextToSpeech tts = (TextToSpeech) returnValue;

            tts.setOnUtteranceProgressListener(upl);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, utterance);
          }
        }
      });
    }
  }

  public void speak(final String text, final iFunctionCallback cb) {
    speak(text, null, cb);
  }

  public void unlockUI() {}

  public void updateOptionsMenu(iPlatformMenuBar mb, boolean initialUpdate) {
    menuBar = mb;

    if (VERSION.SDK_INT > 10) {
      try {
        Method m = Activity.class.getMethod("invalidateOptionsMenu");

        m.invoke(this);

        return;
      } catch(Exception e) {
        e.printStackTrace();
      }
    } else {
      if (optionsMenu != null) {
        if (!initialUpdate && menuSet) {
          optionsMenu.clear();
        }

        mb.createMenu(optionsMenu);
        menuSet = true;
      }
    }
  }

  public URL validateApplicationURL(iPlatformAppContext app, URL url) {
    return url;
  }

  protected UIFont adjustSystemFont(UIFont f) {
    float size = Math.max(f.getSize2D(), 16);

    if (f.getSize2D() != size) {
      f = f.deriveFontSize(size);
    }

    return f;
  }

  protected void configureTextToSpeech(TextToSpeech sTts) {
    sTts.setSpeechRate((float) 1.25);    // use phone setting instead, automatic in
    // 2.1+
    sTts.setLanguage(Locale.US);
  }

  protected void createContent(Bundle icicle) {
    try {
      if (!AppContext.isActive()) {
        showSplashScreen();
      }

      AppContext.startApplication(this, icicle);
    } catch(Throwable e) {
      handleStartupError(e);
    }
  }

  protected void enableSmoothPainting() {
    Window window = getWindow();

    // Eliminates color banding
    window.setFormat(PixelFormat.RGBA_8888);
  }

  protected aWidgetListener findFrstKeyListener(aViewer v) {
    do {
      if (v == null) {
        break;
      }

      aWidgetListener wl = v.getWidgetListener();

      if ((wl != null) && wl.isKeyEventsEnabled()) {
        return wl;
      }

      if (!(v instanceof aContainer)) {
        break;
      }

      aContainer    c    = (aContainer) v;
      List<iWidget> list = c.getWidgetList();

      if (list == null) {
        break;
      }

      for (iWidget w : list) {
        aWidget aw = (aWidget) w;

        wl = aw.getWidgetListener();

        if ((wl != null) && wl.isKeyEventsEnabled()) {
          return wl;
        }
      }

      for (iWidget w : list) {
        if (w instanceof aContainer) {
          wl = findFrstKeyListener((aContainer) w);

          if (wl != null) {
            return wl;
          }
        }
      }
    } while(false);

    return null;
  }

  protected aWidgetListener findFirstMouseListener(aViewer v, boolean motion) {
    do {
      if (v == null) {
        break;
      }

      aWidgetListener wl = v.getWidgetListener();

      if ((wl != null) && ((motion && wl.isMouseMotionEventsEnabled()) || wl.isMouseEventsEnabled())) {
        return wl;
      }

      if (!(v instanceof aContainer)) {
        break;
      }

      aContainer    c    = (aContainer) v;
      List<iWidget> list = c.getWidgetList();

      if (list == null) {
        break;
      }

      for (iWidget w : list) {
        aWidget aw = (aWidget) w;

        wl = aw.getWidgetListener();

        if ((wl != null) && ((motion && wl.isMouseMotionEventsEnabled()) || wl.isMouseEventsEnabled())) {
          return wl;
        }
      }

      for (iWidget w : list) {
        if (w instanceof aContainer) {
          wl = findFirstMouseListener((aContainer) w, motion);

          if (wl != null) {
            return wl;
          }
        }
      }
    } while(false);

    return null;
  }

  protected void handleBackKeyPressed(long time) {
    if ((backTime > 0) && (backTime + 3000 > time)) {
      finish();

      return;
    }

    backTime = time;

    String s = Platform.getResourceAsString("Rare.runtime.text.pressBackToExit");

    if ((s == null) || (s.length() == 0)) {
      s = "Press Back once more to exit";
    }

    Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);

    t.show();
  }

  protected boolean handleHomeActionPressed(MenuItem item) {
    return false;
  }

  protected void handleStartupError(Throwable e) {
    Log.e("Rare", "Form.create", e);
    finish();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if ((activityListener != null) && (rare != null)) {
      if (activityListener.onActivityResult(rare.getAppContext(), this, requestCode, resultCode, data)) {
        return;
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (rare != null) {
      closeCache();

      try {
        cancelToast();
        PlatformHelper.clearSessionCookies();
        CookieSyncManager.getInstance().stopSync();
      } catch(Throwable e) {
        e.printStackTrace();
      }

      try {
        if (rare != null) {
          rare.getWindowManager().getMainWindow().close();
        }

        if (rare != null) {
          rare.getAppContext().exit();
        }
      } catch(Throwable e) {
        e.printStackTrace();
      }
    }

    if (isFinishing()) {
      android.os.Process.killProcess(android.os.Process.myPid());
    }
  }

  protected void onNewIntent(Intent intent) {
    Uri uri = intent.getData();

    if ((uri != null) && (activityListener != null)) {
      activityListener.handleNewIntent(intent);

      return;
    }
  }

  @Override
  protected void onPause() {
    CookieSyncManager.getInstance().stopSync();
    cancelToast();

    if (rare != null) {
      rare.fireApplicationPaused();
    }

    super.onPause();
  }

  protected void onResume() {
    CookieSyncManager.getInstance().startSync();
    super.onResume();

    if (rare != null) {
      rare.fireApplicationResumed();
    }
  }

  @Override
  protected void onStop() {
    silenceTextToSpeech();
    super.onStop();
  }

  protected void setInitialOptions() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    PlatformHelper.initialize(this, getWindowManager().getDefaultDisplay());
    CookieSyncManager.createInstance(this);

    if (googleGlass) {
      googleGestureDetector = new GestureDetector(this, new GlassGestureListener());
    }
  }

  protected void showSplashScreen() {}

  private void cancelToast() {
    if (toast != null) {
      try {
        toast.cancel();
      } catch(Throwable ignore) {}

      toast = null;
    }

    toastTime = 0;
  }

  private void closeCache() {
    if (responseCache != null) {
      try {
        responseCache.getClass().getMethod(deleteCacheOnExit
                                           ? "delete"
                                           : "close").invoke(responseCache);
      } catch(Exception e) {}
    }
  }

  private iPlatformMenuBar getMenuBar() {
    if (rare != null) {
      return rare.getAppContext().getWindowManager().getMenuBar();
    }

    return menuBar;
  }

  void alert(String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setMessage(message).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });

    AlertDialog alert = builder.create();

    alert.show();
  }

  public static boolean isRunningOnEmulator() {
    // from: http://www.monkey-x.com/Community/posts.php?topic=6097&post=84682
    boolean result = Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown")
                     || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator")
                     || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion");

    if (result) {
      return true;
    }

    result |= Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");

    if (result) {
      return true;
    }

    result |= "google_sdk".equals(Build.PRODUCT);

    return result;
  }

  public interface iBackPressListener {
    boolean onBackKeyPressed(iActivity activity);
  }


  @Override
  public boolean onGenericMotionEvent(MotionEvent e) {
    if (googleGestureDetector != null) {
      googleGestureDetector.onTouchEvent(e);
    }

    return false;
  }

  protected class GlassGestureListener
          implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onSingleTapConfirmed(e);
      }

      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onDoubleTap(e);
      }

      return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onDoubleTapEvent(e);
      }

      return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onDown(e);
      }

      return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onSingleTapUp(e);
      }

      return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onScroll(e1, e2, distanceX, distanceY);
      }

      return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        wl.onLongPress(e);
      }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      aViewer        v  = (aViewer) Platform.getAppContext().getWindowManager().getWorkspaceViewer();
      WidgetListener wl = (WidgetListener) findFirstMouseListener(v, true);

      if (wl != null) {
        return wl.onFling(e1, e2, velocityX, velocityY);
      }

      return false;
    }
  }
}
