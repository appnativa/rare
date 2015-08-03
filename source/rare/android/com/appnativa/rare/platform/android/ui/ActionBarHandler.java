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

package com.appnativa.rare.platform.android.ui;

import android.app.Activity;

import android.graphics.drawable.Drawable;

import android.os.Build.VERSION;

import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Method;

public class ActionBarHandler {
  public static final int SHOW_AS_ACTION_ALWAYS    = 2;
  public static final int SHOW_AS_ACTION_IF_ROOM   = 1;
  public static final int SHOW_AS_ACTION_NEVER     = 0;
  public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
  private static int      home_id                  = -1;

  public static void hide(Activity a) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("hide"), ab);
    }
  }

  public static void show(Activity a) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("show"), ab);
    }
  }

  public static void setDisplayShowHomeEnabled(Activity a, boolean show) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setDisplayShowHomeEnabled", boolean.class), ab, show);
    }
  }

  public static void setDisplayShowTitleEnabled(Activity a, boolean show) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setDisplayShowTitleEnabled", boolean.class), ab, show);
    }
  }

  public static void setDisplayShowCustomEnabled(Activity a, boolean show) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setDisplayShowCustomEnabled", boolean.class), ab, show);
    }
  }

  public static void setBackgroundDrawable(Activity a, Drawable d) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setBackgroundDrawable", Drawable.class), ab, d);
    }
  }

  public static void setShowAsAction(MenuItem item, int how) {
    if (VERSION.SDK_INT > 10) {
      try {
        Class  cls = Class.forName("android.view.MenuItem");
        Method m   = cls.getMethod("setShowAsAction", int.class);

        m.invoke(item, how);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void legacyMenu(MenuItem item) {
    setShowAsAction(item, SHOW_AS_ACTION_WITH_TEXT | SHOW_AS_ACTION_IF_ROOM);
  }

  public static void setNavigationMode(Activity a, int mode) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setNavigationMode", int.class), ab, mode);
    }
  }

  public static void setCustomView(Activity a, View view) {
    Object ab = getActionbar(a);

    if (ab != null) {
      invoke(getMethod("setCustomView", View.class), ab, view);
    }
  }

  static Object invoke(Method m, Object o, Object... args) {
    if (m != null) {
      try {
        return m.invoke(o, args);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  static Object getActionbar(Activity a) {
    if (VERSION.SDK_INT > 10) {
      try {
        Method m = Activity.class.getMethod("getActionBar");

        return m.invoke(a);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  static Method getMethod(String name, Class... parameterTypes) {
    try {
      Class cls = Class.forName("android.app.ActionBar");

      return cls.getMethod(name, parameterTypes);
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static boolean isVisible(Activity a) {
    Object ab = getActionbar(a);

    if (ab != null) {
      Object o = invoke(getMethod("isShowing"), ab);

      if (o instanceof Boolean) {
        return Boolean.TRUE.equals(o);
      }
    }

    return false;
  }

  public static int getHomeMenuId(Activity a) {
    if (home_id == -1) {
      try {
        home_id = a.getResources().getIdentifier("android:id/home", null, null);
      } catch(Exception e) {
        home_id = -2;
      }
    }

    return home_id;
  }
}
