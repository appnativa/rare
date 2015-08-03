/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

/**
 * The default implementation of <code>AbstractPreferences</code> for the Linux platform,
 * using the file system as its back end.
 *
 * TODO some sync mechanism with backend, Performance - check file edit date
 *
 * @since Android 1.0
 */
public class FilePreferences extends AbstractPreferences implements iPreferences {

  /*
   * --------------------------------------------------------------
   * Class fields
   * --------------------------------------------------------------
   */

  // prefs file name
  private static final String PREFS_FILE_NAME = "prefs.properties";    // $NON-NLS-1$

  // home directory for system prefs
  private static String SYSTEM_HOME;

  // home directory for user prefs
  private static String USER_HOME;

  // cache for removed prefs key-value pair
  private Set<String> removed = new HashSet<String>();

  // cache for updated prefs key-value pair
  private Set<String> updated = new HashSet<String>();

  // parent dir for this preferences node
  private File dir;

  /*
   * --------------------------------------------------------------
   * Instance fields
   * --------------------------------------------------------------
   */

  // file path for this preferences node
  private String path;

  // internal cache for prefs key-value pair
  private Properties prefs;

  // file represents this preferences node
  private File prefsFile;

  /*
   * --------------------------------------------------------------
   * Constructors
   * --------------------------------------------------------------
   */

  /**
   * Construct root <code>FilePreferencesImpl</code> instance, construct
   * user root if userNode is true, system root otherwise
   */
  public FilePreferences(boolean userNode) {
    super(null, "");    // $NON-NLS-1$
    if(USER_HOME==null) {
    	initialize(null);
    }
    path = userNode
           ? USER_HOME
           : SYSTEM_HOME;
    initPrefs();
  }

  /**
   * Construct a prefs using given parent and given name
   */
  private FilePreferences(AbstractPreferences parent, String name) {
    super(parent, name);
    path = ((FilePreferences) parent).path + File.separator + name;
    initPrefs();
  }

  public static void initialize(final String path) {
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
      public Void run() {
        if (path == null) {
          USER_HOME   = System.getProperty("user.home") + File.separator + "appnativa"+ File.separator + "userPrefs";    // $NON-NLS-1$ //$NON-NLS-2$
          SYSTEM_HOME = System.getProperty("java.home") + File.separator + "appnativa" + File.separator + "systemPrefs";    // $NON-NLS-1$//$NON-NLS-2$
        } else {
          USER_HOME   = System.getProperty("user.home") + File.separator + path;    // $NON-NLS-1$ //$NON-NLS-2$
          SYSTEM_HOME = USER_HOME;
        }

        return null;
      }
    });
  }

  @Override
  protected AbstractPreferences childSpi(String name) {
    FilePreferences child = new FilePreferences(this, name);

    return child;
  }

  @Override
  protected String[] childrenNamesSpi() throws BackingStoreException {
    String[] names = AccessController.doPrivileged(new PrivilegedAction<String[]>() {
      public String[] run() {
        return dir.list(new FilenameFilter() {
          public boolean accept(File parent, String name) {
            return new File(path + File.separator + name).isDirectory();
          }
        });
      }
    });

    if (null == names) {    // file is not a directory, exception case

      // prefs.3=Cannot get children names for {0}!
      throw new BackingStoreException("Cannot get children names for:" + toString());
    }

    return names;
  }

  @Override
  protected void flushSpi() throws BackingStoreException {
    try {

      // if removed, return
      if (isRemoved()) {
        return;
      }

      // reload
      Properties currentPrefs = loadFilePrefs(prefsFile);

      // merge
      Iterator<String> it = removed.iterator();

      while(it.hasNext()) {
        currentPrefs.remove(it.next());
      }

      removed.clear();
      it = updated.iterator();

      while(it.hasNext()) {
        Object key = it.next();

        currentPrefs.put(key, prefs.get(key));
      }

      updated.clear();

      // flush
      prefs = currentPrefs;
      flushFilePrefs(prefsFile, prefs);
    } catch(Exception e) {
      throw new BackingStoreException(e);
    }
  }

  @Override
  protected String[] keysSpi() throws BackingStoreException {
    return prefs.keySet().toArray(new String[0]);
  }

  @Override
  protected void putSpi(String name, String value) {
    prefs.setProperty(name, value);
    updated.add(name);
  }

  @Override
  protected void removeNodeSpi() throws BackingStoreException {
    boolean removeSucceed = (AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
      public Boolean run() {
        prefsFile.delete();

        return Boolean.valueOf(dir.delete());
      }
    })).booleanValue();

    if (!removeSucceed) {

      // prefs.4=Cannot remove {0}!
      throw new BackingStoreException("Cannot remove:" + toString());
    }
  }

  @Override
  protected void removeSpi(String key) {
    prefs.remove(key);
    updated.remove(key);
    removed.add(key);
  }

  @Override
  protected void syncSpi() throws BackingStoreException {
    flushSpi();
  }

  @Override
  protected String getSpi(String key) {
    try {
      if (null == prefs) {
        prefs = loadFilePrefs(prefsFile);
      }

      return prefs.getProperty(key);
    } catch(Exception e) {    // if Exception happened, return null
      return null;
    }
  }

  private void flushFilePrefs(final File file, final Properties prefs) throws IOException, PrivilegedActionException {
    AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
      public Object run() throws IOException {
        flushFilePrefsImpl(file, prefs);

        return null;
      }
    });
  }

  private void flushFilePrefsImpl(File file, Properties prefs) throws IOException {
    BufferedWriter out  = null;
    FileLock       lock = null;

    try {
      FileOutputStream ostream = new FileOutputStream(file);

      out = new BufferedWriter(new OutputStreamWriter(ostream, "UTF-8"), 8192);    // $NON-NLS-1$

      FileChannel channel = ostream.getChannel();

      lock = channel.lock();

      if (prefs.size() > 0) {
        prefs.store(ostream, null);
      }

      out.flush();
    } finally {
      try {
        lock.release();
      } catch(Exception ignore) {                                                  // ignore
      }

      try {
        if (null != out) {
          out.close();
        }
      } catch(Exception ignore) {                                                  // ignore
      }
    }
  }

  private void initPrefs() {
    dir     = new File(path);
    newNode = (AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
      public Boolean run() {
        return Boolean.valueOf(!dir.exists());
      }
    })).booleanValue();
    prefsFile = new File(path + File.separator + PREFS_FILE_NAME);
    prefs     = loadFilePrefs(prefsFile);
  }

  private Properties loadFilePrefs(final File file) {
    return AccessController.doPrivileged(new PrivilegedAction<Properties>() {
      public Properties run() {
        return loadFilePrefsImpl(file);
      }
    });
  }

  private Properties loadFilePrefsImpl(final File file) {
    Properties result = new Properties();

    if (!file.exists()) {
      file.getParentFile().mkdirs();
    } else if (file.canRead()) {
      InputStream in   = null;
      FileLock    lock = null;

      try {
        FileInputStream istream = new FileInputStream(file);

        in = new BufferedInputStream(istream, 8192);

        FileChannel channel = istream.getChannel();

        lock = channel.lock(0L, Long.MAX_VALUE, true);
        result.load(in);

        return result;
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          lock.release();
        } catch(Exception e) {    // ignore
        }

        try {
          in.close();
        } catch(Exception e) {    // ignore
        }
      }
    } else {
      file.delete();
    }

    return result;
  }

	public iPreferences getNode(String pathName) {
		return (FilePreferences)node(pathName);
	}

	public iPreferences getParent() {
		return (FilePreferences)parent();
	}
}
