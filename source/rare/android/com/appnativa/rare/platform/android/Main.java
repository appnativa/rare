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

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.Application;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.util.Log;

import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.net.CookieManager;
import com.appnativa.rare.ui.listener.iApplicationListener;

import java.net.CookieHandler;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Don DeCoteau
 */
public class Main extends Application implements iApplicationListener {
  private static Main    _instance;
  volatile Configuration config;
  Rare                   rare;

  public Main() {
    _instance = this;

    try {
      CookieHandler.setDefault(new CookieManager());
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public boolean allowClosing(iPlatformAppContext app) {
    return true;
  }

  public void applicationClosing(iPlatformAppContext app) {
    config = null;
  }

  public void applicationInitialized(iPlatformAppContext app) {
    config = null;
  }

  public void applicationPaused(iPlatformAppContext app) {
    config = null;
  }

  public void applicationResumed(iPlatformAppContext app) {
    config = null;
  }

  public Rare createRareInstance(Activity activity) throws Exception {
    if ((rare == null) || (rare.mainActivity != activity)) {
      ApplicationInfo ai     = getPackageManager().getApplicationInfo(this.getPackageName(),
                                 PackageManager.GET_META_DATA);
      Bundle          bundle = ai.metaData;

      if (bundle != null) {
        boolean trust = bundle.getBoolean("rare.trustAllCerts");

        if (!trust) {
          trust = bundle.getBoolean("jnlp.rare.trustAllCerts");
        }

        if (trust) {
          trustAllCerts();
        }
      }

      String s = (bundle == null)
                 ? null
                 : bundle.getString("useThemeColors");

      rare = new Rare(getApplicationContext(), activity, !"false".equals(s));

      URL a[];

      s = System.getProperty("Rare.applicationURL");

      if (s == null) {
        s = (bundle == null)
            ? null
            : bundle.getString("applicationURL");
      }

      if (s == null) {
        s = System.getProperty("rare.intentUri");
      }

      if ((s != null) && (s.length() > 0)) {
        URL url1,
            url2 = null;

        if (s.startsWith(iConstants.LIB_PREFIX)) {
          url1 = rare.getAppContext().getResourceURL(s.substring(iConstants.LIB_PREFIX_LENGTH));
        } else {
          url1 = new URL(s);
        }

        s = (bundle == null)
            ? null
            : bundle.getString("applicationURL2");

        if (s != null) {
          if (s.startsWith(iConstants.LIB_PREFIX)) {
            url2 = rare.getAppContext().getResourceURL(s.substring(iConstants.LIB_PREFIX_LENGTH));
          } else {
            url2 = new URL(s);
          }
        }

        if (url2 != null) {
          a = new URL[] { url1, url2 };
        } else {
          a = new URL[] { url1 };
        }

        s = (bundle == null)
            ? null
            : bundle.getString("applicationLocal");
        rare.createApplicationObject(a, s);
      }
    }

    return rare;
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();

    if (rare != null) {
      ((AppContext) rare.getAppContext()).didReceiveMemoryWarning();
    }
  }

  public void pausing() {}

  public void resuming() {
    config = null;
  }

  public static Drawable getDrawableEx(int id) {
    return (_instance == null)
           ? null
           : _instance.getResources().getDrawable(id);
  }

  @SuppressLint("TrulyRandom")
  private static void trustAllCerts() {
    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
      }
      public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
      public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
    } };

    // Install the all-trusting trust manager
    try {
      SSLContext sc = SSLContext.getInstance("TLS");

      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
        public boolean verify(String string, SSLSession ssls) {
          return true;
        }
      });
    } catch(Exception e) {
      Log.d(Main.class.getName(), "trustAllCerts=true", e);
    }
  }
}
