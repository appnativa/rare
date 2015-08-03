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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.TypedValue;

import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.PopupWindow;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iFunctionHandler;
import com.appnativa.rare.platform.aAppContext;
import com.appnativa.rare.platform.android.ui.util.AndroidHelper;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIProperties;
import com.appnativa.rare.ui.effects.AnimationImage;
import com.appnativa.rare.ui.effects.AnimationWrapper;
import com.appnativa.rare.ui.effects.iPlatformAnimator;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformComponentFactory;
import com.appnativa.rare.ui.iPrintHandler;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharArray;
import com.appnativa.util.IdentityArrayList;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Locale;

/**
 * This class represents an instance of a running application. It acts as a
 * proxy to a running application and also maintains a list of all running
 * instances
 *
 * @author Don DeCoteau
 */
public class AppContext extends aAppContext {
  private static ThreadLocal<CharArray> perThreadBuffer = new ThreadLocal<CharArray>() {
    protected synchronized CharArray initialValue() {
      return new CharArray(32);
    }
  };
  private aActivityListener activityListener;

  /**
   * Creates a new application context
   *
   * @param instance
   *          the application instance
   */
  AppContext(Rare instance) {
    super(instance, new UIProperties());
  }

  public boolean browseURL(URL url) {
    Uri    uri    = Uri.parse(url.toExternalForm());
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

    getRootActivity().startActivity(intent);

    return true;
  }

  public void clearStatusBar() {}

  public void closePopupWindows(boolean all) {
    IdentityArrayList w = new IdentityArrayList(activeWindows);

    for (Object o : w) {
      if (o instanceof PopupWindow) {
        ((PopupWindow) o).dismiss();
      } else if (all && (o instanceof Dialog)) {
        ((Dialog) o).dismiss();
      }
    }

    Activity a = getRootActivity();

    if (a != null) {
      ((MainActivity) a).closeDialogs();
    }
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
      if (o instanceof Dialog) {
        return true;
      }
    }

    return false;
  }

  public void exit() {
    if ((getRootActivity() != null) &&!getRootActivity().isFinishing()) {
      getRootActivity().finish();

      return;
    }

    super.exit();
  }

  public void lockOrientation(Boolean landscape) {
    int orientation = getActivity().getResources().getConfiguration().orientation;

    if (landscape == null) {
      switch(orientation) {
        case Configuration.ORIENTATION_LANDSCAPE :
          getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

          break;

        case Configuration.ORIENTATION_PORTRAIT :
          getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

          break;

        default :
          if (ScreenUtils.isWider()) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          }

          break;
      }
    } else if (landscape) {
      if (orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      }
    } else {
      if (orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      }
    }

    orientationLocked = true;
  }

  public void oneLineErrorMessage(String title, String message) {
    AndroidHelper.errorMessage(_instance, title, message);
  }

  public static void startApplication(final MainActivity a, Bundle icicle) throws Exception {
    AppContext app = (AppContext) _instance;

    if ((icicle != null) && (app != null) && (app.RARE != null)) {
      a.setActivityListener(app.activityListener);
      a.setRareInstance((Rare) app.RARE);
      app.setContentView(a);

      return;
    }

    new StartupTask().execute(a);
  }

  public void unlockOrientation() {
    if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
      getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    orientationLocked = false;
  }

  public void setActivityListener(aActivityListener l) {
    activityListener = l;

    if (getActivity() instanceof iActivity) {
      ((iActivity) getActivity()).setActivityListener(l);
    }
  }

  @Override
  public void setLowMemoryWarningHandler(Runnable runnable) {
    super.setLowMemoryWarningHandler(runnable);

    if (getActivity() instanceof MainActivity) {
      ((MainActivity) getActivity()).setLowMemoryWarningHandler(runnable);
    }
  }

  public void setContentView(MainActivity a) throws Exception {
    ((Rare) RARE).setContentView(a);
  }

  @Override
  public void setThemeColors(UIColor fg, UIColor bg, boolean isdefault) {
    super.setThemeColors(fg, bg, isdefault);

    if (isdefault) {    // always set on android
      uiDefaults.put("Rare.List.foreground", UIColor.BLACK);
      uiDefaults.put("Rare.List.background", UIColor.WHITE);
      uiDefaults.put("Rare.List.disabledForeground", UIColor.GRAY);
    }
  }

  protected void didReceiveMemoryWarning() {
    if (lowMemoryWarningHandler != null) {
      lowMemoryWarningHandler.run();
    }
  }

  public Activity getActivity() {
    return ((Rare) ((AppContext) _instance).RARE).getActivity();
  }

  public aActivityListener getActivityListener() {
    return activityListener;
  }

  public static Context getAndroidContext() {
    return getContext().getActivity();
  }

  public iPlatformComponentFactory getComponentCreator() {
    return RARE.getWindowManager().getComponentCreator();
  }

  public Configuration getConfiguration() {
    return getActivity().getResources().getConfiguration();
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

  public Drawable getDrawable(int id) {
    Context a = getActivity();

    if (a == null) {
      a = getAndroidContext();
    }

    return (id == 0)
           ? null
           : a.getResources().getDrawable(id);
  }

  public iFunctionHandler getFunctionHandler() {
    return RARE.getFunctionHandler();
  }

  public Integer getLayoutId(String name) {
    return getResourceId(getActivity(), "layout", name);
  }

  public UIImage getManagedResource(String name) {
    return getResourceAsDrawableImage(name);
  }

  public UIImage getManagedResource(String name, boolean landscape) {
    if (name.indexOf('.') != -1) {
      name = name.replace('.', '-').toLowerCase(Locale.US);
    }

    return getResourceAsDrawableImage(name);
  }

  public iPrintHandler getPrintHandler() {
    return null;
  }

  public static Rare getRareEnvironment(MainActivity a, Bundle icicle) throws Exception {
    AppContext app = (AppContext) _instance;

    if ((icicle == null) || (app == null)) {
      ((Main) a.getApplication()).createRareInstance(a);
    }

    app = (AppContext) _instance;

    if (a instanceof iActivity) {
      ((iActivity) a).setActivityListener(app.activityListener);
    }

    app.setContentView(a);

    return (Rare) app.RARE;
  }

  public Drawable getResourceAsDrawable(String name) {
    Context a = getActivity();

    if (a == null) {
      a = getAndroidContext();
    }

    Integer id = getResourceAsDrawableID(name);

    return (id == null)
           ? null
           : a.getResources().getDrawable(id);
  }

  public UIImageIcon getResourceAsIconEx(String name) {
    UIImageIcon icon = appIcons.get(name);

    if (icon != null) {
      return icon;
    }

    try {
      icon = uiDefaults.getImageIcon(name);

      if (icon != null) {
        return icon;
      }

      UIImage img = getResourceAsDrawableImage(name);

      if (img != null) {
        return new UIImageIcon(img, name, iConstants.RESOURCE_PREFIX + name);
      }
    } catch(Exception e) {}

    return null;
  }

  public UIImage getResourceAsImage(String name) {
    UIImageIcon icon = appIcons.get(name);

    if (icon != null) {
      return icon.getImage();
    }

    try {
      UIImage image = getResourceAsDrawableImage(name);

      if (image != null) {
        return image;
      }
    } catch(Exception e) {}

    return UIImageIcon.getBrokenImage();
  }

  public String getResourceAsString(String name) {
    String s;

    if (appStrings != null) {
      s = appStrings.get(name);

      if (s != null) {
        return s;
      }
    }

    try {
      s = RARE.getResourceBundle().getString(name);
    } catch(Exception e) {
      Platform.ignoreException("getResourceAsString", e);

      return null;
    }

    if (s != null) {
      return s;
    }

    try {
      Activity a  = getActivity();
      int      id = getResourceId(a, "string", name);

      return a.getString(id);
    } catch(Exception e) {
      Platform.ignoreException("getResourceAsString", e);
    }

    return "";
  }

  public Integer getResourceId(String name) {
    Context a = getActivity();

    if (a == null) {
      a = getAndroidContext();
    }

    return getResourceId(a, name);
  }

  public static Integer getResourceId(Context ctx, String name) {
    try {
      int id;

      if (name.contains(":")) {
        id = ctx.getResources().getIdentifier(name, null, null);
      } else {
        id = ctx.getResources().getIdentifier(name, null, ctx.getPackageName());
      }

      return (id == 0)
             ? null
             : id;
    } catch(Exception e) {
      Platform.ignoreException(name, e);
    }

    return null;
  }

  public static Integer getResourceId(Context ctx, String type, String name) {
    try {
      int id = ctx.getResources().getIdentifier(name, type, ctx.getPackageName());

      return (id == 0)
             ? null
             : id;
    } catch(Exception e) {
      Platform.ignoreException(name, e);
    }

    return null;
  }

  public URL getResourceURL(String path) {
    try {
      if (path.startsWith("/")) {
        return new URL("file:///android_asset" + path);
      }

      return new URL("file:///android_asset/" + path);
    } catch(MalformedURLException ex) {
      throw new ApplicationException(ex);
    }
  }

  public static Activity getRootActivity() {
    return ((Rare) getContext().RARE).mainActivity;
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

  public WindowViewer getWindowViewer() {
    WindowViewer w   = null;
    final int    len = activeWindows.size();

    for (int i = len - 1; i > -1; i--) {
      Object o = activeWindows.get(i);

      if (o instanceof Frame) {
        w = (WindowViewer) ((Frame) o).getViewer();

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

  public boolean hasKeyboard() {
    return getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS;
  }

  public static boolean isActive() {
    AppContext app = (AppContext) _instance;

    return (app != null) && (app.RARE != null);
  }

  void setFocusOwner(iPlatformComponent c) {
    focusOwner = c;
  }

  void setPermanentFocusOwner(iPlatformComponent c) {
    permanentFocusOwner = c;
  }

  protected void initializeManagedResourcePaths() {
    // TODO Auto-generated method stub
  }

  protected URL makeResourceURL(String name, String extension) {
    return getResourceURL(name + "." + extension);
  }

  protected void setupUIDefaults() {
    Context    ctx   = ((Rare) RARE).androidContext;
    TypedValue tv    = new TypedValue();
    Theme      theme = ctx.getTheme();
    Resources  res   = ctx.getResources();
    UIColor    fg    = null;
    UIColor    bg    = null;

    if (((Rare) RARE).useThemeColors) {
      if (theme.resolveAttribute(android.R.attr.colorBackground, tv, true)) {
        bg = new UIColor(res.getColor(tv.resourceId));

        if (bg.isDarkColor()) {
          bg = new UIColor(0x3f, 0x3f, 0x3f);
        }
      }

      if (theme.resolveAttribute(android.R.attr.colorForeground, tv, true)) {
        fg = new UIColor(res.getColor(tv.resourceId));
      }
    }

    super.setupUIDefaults(fg, bg);
    bg = uiDefaults.getColor("Rare.background");
    fg = uiDefaults.getColor("Rare.foreground");

    ColorStateList csl = new ColorStateList(new int[][] {
      new int[] { -android.R.attr.state_enabled }, new int[0]
    }, new int[] { uiDefaults.getColor("Rare.disabledForeground").getColor(), fg.getColor() });

    uiDefaults.put("textText", new UIColorShade(csl));
    uiDefaults.put("Rare.foreground", uiDefaults.get("textText"));

    Resources r = ctx.getResources();

    appIcons.put("Rare.icon.broken", createImageIcon(r, "drawable/rare_icon_broken"));
    appIcons.put("Rare.Tree.closedIcon", createImageIcon(r, "drawable/are_icon_folderclosed"));
    appIcons.put("Rare.Tree.OpenIcon", createImageIcon(r, "drawable/rare_icon_folderopen"));
    appIcons.put("Rare.Tree.leafIcon", createImageIcon(r, "drawable/rare_icon_page"));
    appIcons.put("Rare.Tree.expandedIcon", createImageIcon(r, "drawable/rare_icon_expanded"));
    appIcons.put("Rare.Tree.collapsedIcon", createImageIcon(r, "drawable/rare_icon_collapsed"));
  }

  @Override
  protected String getDefaultManagedResourcePath() {
    return null;
  }

  protected iPlatformAnimator getResourceAsAnimatorEx(String animator) {
    try {
      Context a = getActivity();

      if (a == null) {
        a = getAndroidContext();
      }

      Integer id = getResourceId(a, "anim", animator);

      if (id != null) {
        Animation anim = AnimationUtils.loadAnimation(a, id);

        if (anim != null) {
          return new AnimationWrapper(anim);
        }
      }
    } catch(Exception e) {
      Platform.ignoreException(animator, e);
    }

    return null;
  }

  /**
   * Gets a drawable resource that is part of the applications resource bundle
   *
   * @param name
   *          the name of the drawable
   *
   * @return the drawable image with the specified name
   */
  @SuppressWarnings("resource")
  protected UIImage getResourceAsDrawableImage(String name) {
    Context a = getActivity();

    if (a == null) {
      a = getAndroidContext();
    }

    CharArray ca = new CharArray(name);

    ca.toLowerCase();
    ca.replace('.', '_');

    int      id = getResourceAsDrawableID(ca.toString());
    Drawable d  = (id == 0)
                  ? null
                  : a.getResources().getDrawable(id);

    if (d instanceof AnimationDrawable) {
      return new AnimationImage((AnimationDrawable) d, id);
    }

    if (d == null) {
      return null;
    }

    UIImage img = new UIImage(d, id, name);

    img.setResourceName(name);

    return img;
  }

  protected boolean isInitialized() {
    return RARE != null;
  }

  private UIImageIcon createImageIcon(Resources r, String resID) {
    Integer i = getResourceId(resID);

    if (i == null) {
      return null;
    }

    BitmapDrawable d = (BitmapDrawable) r.getDrawable(i);

    d.setAntiAlias(true);
    d.setGravity(Gravity.CENTER);

    return new UIImageIcon(new UIImage(d, i));
  }

  private int getResourceAsDrawableID(String name) {
    Context a = getActivity();

    if (a == null) {
      a = getAndroidContext();
    }

    Integer id;

    if (name.startsWith("Rare.")) {
      CharArray ca = perThreadBuffer.get();

      ca.set(name);
      ca.toLowerCase();
      ca.replace('.', '_');
      id = getResourceId(a, "drawable", ca.toString());
    } else {
      id = getResourceId(a, "drawable", name);
    }

    return (id == null)
           ? 0
           : id.intValue();
  }

  static class StartupTask extends AsyncTask<MainActivity, Integer, MainActivity> {
    Throwable error;

    void showError(final MainActivity a, Throwable e) {
      e.printStackTrace();

      AlertDialog alertDialog = new AlertDialog.Builder(a).create();

      alertDialog.setTitle("Startup Error");
      alertDialog.setMessage(ApplicationException.getMessageEx(e));
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          a.finish();
        }
      });
      alertDialog.show();
    }

    protected MainActivity doInBackground(MainActivity... params) {
      MainActivity a = params[0];

      try {
        ((Main) a.getApplication()).createRareInstance(a);
      } catch(Throwable e) {
        error = e;
      }

      return a;
    }

    protected void onPostExecute(MainActivity a) {
      if (error != null) {
        showError(a, error);

        return;
      }

      try {
        _instance.setContentView(a);
      } catch(Throwable e) {
        showError(a, e);

        return;
      }

      a.setRareInstance((Rare) ((AppContext) _instance).RARE);
    }
  }
}
