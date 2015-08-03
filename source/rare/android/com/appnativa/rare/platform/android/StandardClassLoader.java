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

import android.content.Context;

import android.os.AsyncTask;

import com.appnativa.rare.Platform;
import com.appnativa.rare.net.HTTPException;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.util.Streams;

import dalvik.system.PathClassLoader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A class loader that maintains a URL repository that can be added to dynamically.
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class StandardClassLoader extends PathClassLoader {
  static ArrayList<JarLoader> jars;

  /**
   * Constructs a new instance
   */
  public StandardClassLoader() {
    super(AppContext.getAndroidContext().getFilesDir().getPath() + "/sup_jars/",
          StandardClassLoader.class.getClassLoader());
  }

  public synchronized static void addJAR(URL jar) {
    if (jars == null) {
      jars = new ArrayList<JarLoader>(5);
    }

    jars.add(new JarLoader(jar));
  }

  public synchronized static void waitForJars() {
    int count = 0;

    for (JarLoader j : jars) {
      try {
        j.get();
        count++;
      } catch(InterruptedException ex) {
        count = 0;

        break;
      } catch(ExecutionException ex) {}
    }

    jars.clear();

    if (count > 0) {
      Thread.currentThread().setContextClassLoader(new com.appnativa.rare.platform.android.StandardClassLoader());
    }
  }

  static class JarLoader extends AsyncTask<Object, Object, Object> {
    private final URL jar;

    JarLoader(URL jar) {
      this.jar = jar;
      execute();
    }

    void setLastModifiedDate(String file, String lm) {
      FileOutputStream out = null;

      try {
        file += ".lm";
        out  = AppContext.getAndroidContext().openFileOutput(file, Context.MODE_PRIVATE);
        out.write(lm.getBytes("iso-8859-1"));
        out.close();
      } catch(Exception ignore) {}
    }

    String getLastModifiedDate(String file) {
      String          lm = null;
      FileInputStream in = null;

      try {
        file += ".lm";
        in   = AppContext.getAndroidContext().openFileInput(file);
        lm   = Streams.streamToString(in);
        in.close();
      } catch(Exception e) {}

      if (in != null) {
        try {
          in.close();
        } catch(Exception e) {}
      }

      return lm;
    }

    @Override
    protected Object doInBackground(Object... paramss) {
      try {
        String file = jar.getFile();
        int    n    = file.lastIndexOf('/');

        if (n != -1) {
          Platform.ignoreException("Could no load:" + this.jar.toString(), null);

          return null;
        }

        file = file.substring(n + 1);
        file = "/sup_jars/" + file;

        String lm = getLastModifiedDate(file);

        try {
          iURLConnection c = Platform.getAppContext().openConnection(jar);

          if (lm != null) {
            c.setHeaderField("If-Modified-Since", lm);
          }

          InputStream  in  = c.getInputStream();
          OutputStream out = AppContext.getAndroidContext().openFileOutput(file, Context.MODE_PRIVATE);

          Streams.streamToStream(in, out, null);
          in.close();
          out.close();
          lm = c.getHeaderField("Last-Modified");

          if ((lm != null) && (lm.length() > 0)) {}
        } catch(HTTPException ex) {
          if (ex.getStatusCode() != 304) {
            Platform.ignoreException("Could no load:" + this.jar.toString(), ex);
          }
        }
      } catch(Throwable e) {
        Platform.ignoreException("Could no load:" + this.jar.toString(), e);
      }

      return null;
    }
  }
}
