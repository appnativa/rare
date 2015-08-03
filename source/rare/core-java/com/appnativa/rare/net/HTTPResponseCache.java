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

package com.appnativa.rare.net;

import com.appnativa.rare.Platform;
import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.util.ByteArray;
import com.appnativa.util.CharArray;
import com.appnativa.util.HTTPDateUtils;
import com.appnativa.util.ISO88591Helper;
import com.appnativa.util.SNumber;
import com.appnativa.util.Streams;
import com.appnativa.util.UTF8Helper;
import com.appnativa.util.aStreamer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Response cache for HTTP connections
 */
public class HTTPResponseCache extends ResponseCache implements Runnable {
  private static String                 URI_HEADER          = "Rare-HTTPResponseCache-URI";
  private static int                    daysToKeepCache     = 20;
  private static int                    daysToKeepUserCache = 20;
  private static ThreadLocal<ByteArray> perThreadBABuffer   = new ThreadLocal<ByteArray>() {
    @Override
    protected synchronized ByteArray initialValue() {
      return new ByteArray(1024);
    }
  };
  private static ThreadLocal<CharArray> perThreadCABuffer = new ThreadLocal<CharArray>() {
    @Override
    protected synchronized CharArray initialValue() {
      return new CharArray(1024);
    }
  };
  private static ThreadLocal<MessageDigest> perThreadDigest = new ThreadLocal<MessageDigest>() {
    @Override
    protected synchronized MessageDigest initialValue() {
      try {
        return MessageDigest.getInstance("MD5");
      } catch(NoSuchAlgorithmException ex) {
        throw new ApplicationException(ex);
      }
    }
  };
  private static boolean                    offlineMode = false;
  private static volatile HTTPResponseCache _instance;
  private static File                       cacheDirectory;
  ISO88591Helper                            iso = ISO88591Helper.getInstance();
  private long                              cacheSize;
  private long                              maxCacheSize;
  private volatile boolean                  trimming;

  private HTTPResponseCache(String name, int max) {
    maxCacheSize   = max * 1024 * 1024;
    cacheDirectory = new File(Platform.getCacheDir(), name);

    if (!cacheDirectory.exists()) {
      if (!cacheDirectory.mkdirs()) {
        throw new ApplicationException("failed to create cache directory:" + cacheDirectory.getPath());
      }
    } else {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          try {
            cacheSize = 0;

            File files[] = cacheDirectory.listFiles();
            int  len     = (files == null)
                           ? 0
                           : files.length;

            for (int i = 0; i < len; i++) {
              cacheSize += files[i].length();
            }

            if (cacheSize > maxCacheSize) {
              trimCache();
            }
          } catch(Throwable e) {
            return;
          }
        }
      };

      new Thread(r).start();
    }

    Runtime.getRuntime().addShutdownHook(new Thread(this));
  }

  public static boolean cacheData(String name, InputStream in) {
    if (_instance == null) {
      _instance = new HTTPResponseCache(Platform.getAppContext().getApplicationName(), -1);
    }

    return _instance.cacheDataEx(name, in);
  }

  public static void clearResponseCache() {
    if (_instance != null) {
      Platform.getPlatform().deleteDirectory(HTTPResponseCache.cacheDirectory);
    }
  }

  public void close() {
    try {
      if (ResponseCache.getDefault() == this) {
        ResponseCache.setDefault(null);
        _instance = null;
        cleanupPending();
        cleanupCache();
      }
    } catch(Throwable ignore) {}

    cacheDirectory = null;
  }

  public static File createCacheFile(String name) {
    if (_instance == null) {
      _instance = new HTTPResponseCache(Platform.getAppContext().getApplicationName(), -1);
    }

    return new File(HTTPResponseCache.cacheDirectory, "uc_" + name);
  }

  public static void deleteCachedData(String name) {
    File f = createCacheFile(name);

    if (f != null) {
      f.delete();
    }
  }

  public void delete() {
    try {
      if (ResponseCache.getDefault() == this) {
        ResponseCache.setDefault(null);
        _instance = null;
      }
    } catch(Throwable ignore) {}

    if (cacheDirectory != null) {
      try {
        Platform.getPlatform().deleteDirectory(cacheDirectory);
      } catch(Throwable ignore) {}

      cacheDirectory = null;
    }
  }

  public static HTTPResponseCache installResponseCache(String name, int max, boolean deleteOnExit) {
    if (_instance == null) {
      ResponseCache.setDefault(_instance = new HTTPResponseCache(name, max));

      if (deleteOnExit) {
        Runtime.getRuntime().addShutdownHook(new Thread(_instance));
      }
    }

    return _instance;
  }

  @Override
  public CacheRequest put(URI uri, URLConnection conn) throws IOException {
    if (!(conn instanceof HttpURLConnection) ||!(((HttpURLConnection) conn).getRequestMethod().equals("GET"))) {
      return null;
    }

    Map<String, List<String>> headers = conn.getHeaderFields();
    List<String>              l       = headers.get("Cache-Control");

    if ((l != null) && l.contains("no-store")) {
      return null;
    }

    File localFile = getLocalFile(uri);

    if (localFile == null) {
      return null;
    }

    File f = new File(localFile.getAbsolutePath() + ".pending");

    if (f.exists()) {
      return null;
    }

    return new LocalCacheRequest(localFile, headers, uri);
  }

  public static void removeFromCache(URI uri) {
    if (_instance == null) {
      return;
    }

    _instance.removeLocalFile(uri);
  }

  public void removeLocalFile(URI remoteUri) {
    try {
      CharArray     c  = perThreadCABuffer.get().set(remoteUri.toString());
      ByteArray     ba = perThreadBABuffer.get();
      MessageDigest md = perThreadDigest.get();

      md.update(ba.A, 0, iso.getBytes(c.A, 0, c._length, ba, 0));

      try {
        ba._length = md.digest(ba.A, 0, 16);
      } catch(DigestException ex) {
        return;
      }

      File f = new File(cacheDirectory, SNumber.bytesToHexString(ba.A, 0, 16));

      if (f.exists()) {
        long size = f.length();

        f.delete();
        cacheSize -= size;
      }
    } catch(Throwable e) {}
  }

  public static void removeResponseCache() {
    if ((_instance != null) && (ResponseCache.getDefault() == _instance)) {
      ResponseCache.setDefault(null);
      _instance = null;
    }
  }

  @Override
  public void run() {
    if (cacheDirectory != null) {
      try {
        cleanupPending();
      } catch(Throwable e) {}

      try {
        cleanupCache();
      } catch(Throwable e) {}
    }
  }

  public void writeHeaders(OutputStream out, Map<String, List<String>> headers, URI uri) throws IOException {
    List<String> l = headers.get("Cache-Control");

    if ((l != null) && l.contains("no-store")) {
      return;
    }

    CharArray ca = perThreadCABuffer.get();

    ca._length = 0;
    ca.append(URI_HEADER).append(':').append(uri.toString()).append('\n');

    Iterator<Entry<String, List<String>>> it = headers.entrySet().iterator();
    Entry<String, List<String>>           e;
    String                                name;
    int                                   i, len;

    while(it.hasNext()) {
      e    = it.next();
      name = e.getKey();

      if (name == null) {
        name = "";
      }

      l   = e.getValue();
      len = l.size();

      for (i = 0; i < len; i++) {
        ca.append(name).append(':').append(l.get(i)).append('\n');
      }
    }

    ByteArray ba = perThreadBABuffer.get();

    ba._length = iso.getBytes(ca.A, 0, ca._length, ba, 0);
    aStreamer.writeVarLength(ba._length, out);
    out.write(ba.A, 0, ba._length);
  }

  /**
   * @param daysToKeepCache
   *          the daysToKeepCache to set
   */
  public static void setDaysToKeepCache(int daysToKeepCache) {
    HTTPResponseCache.daysToKeepCache = daysToKeepCache;
  }

  /**
   * @param aDaysToKeepUserCache
   *          the daysToKeepUserCache to set
   */
  public static void setDaysToKeepUserCache(int aDaysToKeepUserCache) {
    daysToKeepUserCache = aDaysToKeepUserCache;
  }

  /**
   * @param aOfflineMode
   *          the offlineMode to set
   */
  public static void setOfflineMode(boolean aOfflineMode) {
    offlineMode = aOfflineMode;
  }

  @Override
  public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
    if (!rqstMethod.equals("GET")) {
      return null;
    }

    File localFile = getLocalFile(uri);

    if (!localFile.exists()) {
      return null;
    }

    return getLocalCacheResponse(uri, localFile);
  }

  public static InputStream getCachedData(String name) {
    if (_instance == null) {
      return null;
    }

    return _instance.getCachedDataEx(name);
  }

  /**
   * @return the daysToKeepCache
   */
  public static int getDaysToKeepCache() {
    return daysToKeepCache;
  }

  /**
   * @return the daysToKeepUserCache
   */
  public static int getDaysToKeepUserCache() {
    return daysToKeepUserCache;
  }

  /**
   * @return the offlineMode
   */
  public static boolean isOfflineMode() {
    return offlineMode;
  }

  boolean contains(List<String> l, String s) {
    if (l != null) {
      return l.indexOf(s) != -1;
    }

    return false;
  }

  protected Map<String, List<String>> loadHeaders(InputStream in) throws IOException {
    int       length = aStreamer.readVarLength(in);
    ByteArray ba     = perThreadBABuffer.get();

    if (length > 65536) {
      return null;
    }

    Map<String, List<String>> h = new HashMap<String, List<String>>();
    BufferedReader            r = null;

    ba.ensureCapacity(length);

    try {
      if (in.read(ba.A, 0, length) != length) {
        return null;
      }

      CharArray ca = perThreadCABuffer.get();

      ca._length = UTF8Helper.getInstance().getChars(ba.A, 0, length, ca, 0);
      r          = new BufferedReader(ca);

      String       line;
      int          n;
      List<String> l;
      String       name, value;

      while((line = r.readLine()) != null) {
        n = line.indexOf(':');

        if (n == -1) {
          continue;
        }

        if (n == 0) {
          h.put(null, Collections.singletonList(line.substring(1)));

          continue;
        }

        name  = line.substring(0, n);
        value = line.substring(n + 1);
        l     = h.get(name);

        if (l == null) {
          h.put(name, Collections.singletonList(value));
        } else {
          if (l.size() == 1) {
            ArrayList<String> nl = new ArrayList<String>(2);

            nl.add(l.get(0));
            h.put(name, nl);
            l = nl;
          }

          l.add(value);
        }
      }
    } catch(IOException ex) {}
    finally {
      try {
        if (r != null) {
          r.close();
        }
      } catch(Exception ignore) {}
    }

    return h;
  }

  protected File getLocalFile(URI remoteUri) {
    CharArray     c  = perThreadCABuffer.get().set(remoteUri.toString());
    ByteArray     ba = perThreadBABuffer.get();
    MessageDigest md = perThreadDigest.get();

    md.update(ba.A, 0, iso.getBytes(c.A, 0, c._length, ba, 0));

    try {
      ba._length = md.digest(ba.A, 0, 16);
    } catch(DigestException ex) {
      return null;
    }

    return new File(cacheDirectory, SNumber.bytesToHexString(ba.A, 0, 16));
  }

  private boolean cacheDataEx(final String name, final InputStream in) {
    FileOutputStream out = null;

    try {
      File f = createCacheFile(name);

      out = new FileOutputStream(f);
      Streams.streamToStream(in, out, null);

      return true;
    } catch(Exception ex) {
      Platform.ignoreException(null, ex);

      return false;
    } finally {
      try {
        out.close();
      } catch(IOException ex) {}
    }
  }

  private void cleanupCache() {
    final File f = cacheDirectory;

    if (f == null) {
      return;
    }

    Calendar now = Calendar.getInstance();

    now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - daysToKeepCache);

    final long keepTime = now.getTimeInMillis();

    now = Calendar.getInstance();
    now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - daysToKeepUserCache);

    final long userKeepTime = now.getTimeInMillis();
    File       files[]      = f.listFiles(new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (file.getName().startsWith("uc_") && (file.lastModified() < userKeepTime)) {
          return true;
        }

        return file.lastModified() < keepTime;
      }
    });
    int len = (files == null)
              ? 0
              : files.length;

    for (int i = 0; i < len; i++) {
      try {
        long size = files[i].length();

        files[i].delete();
        cacheSize -= size;
      } catch(Exception ignore) {}
      catch(Error e) {
        return;
      }
    }
  }

  private void cleanupPending() {
    final File f = cacheDirectory;

    if (f == null) {
      return;
    }

    File files[] = f.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".pending");
      }
    });
    int len = (files == null)
              ? 0
              : files.length;

    for (int i = 0; i < len; i++) {
      try {
        long size = files[i].length();

        files[i].delete();
        cacheSize -= size;
      } catch(Exception ignore) {}
      catch(Error e) {
        return;
      }
    }
  }

  private synchronized void startTrimming() {
    if (!trimming) {
      trimming = true;
      new Thread(new Runnable() {
        @Override
        public void run() {
          trimCache();
        }
      }).start();
    }
  }

  private void trimCache() {
    try {
      trimming = true;

      final File f = cacheDirectory;

      if (f == null) {
        return;
      }

      long trimSize = maxCacheSize - (maxCacheSize / 2);
      File files[]  = f.listFiles();

      if ((files == null) || (files.length == 0)) {
        return;
      }

      Arrays.sort(files, new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
          return (int) (rhs.lastModified() - lhs.lastModified());
        }
      });

      int len = files.length;

      for (int i = 0; i < len; i++) {
        try {
          long size = files[i].length();

          files[i].delete();
          cacheSize -= size;

          if (cacheSize <= trimSize) {
            break;
          }
        } catch(Exception ex) {}
        catch(Throwable e) {}
      }
    } finally {
      trimming = false;
    }
  }

  private InputStream getCachedDataEx(String name) {
    File f = createCacheFile(name);

    try {
      return f.exists()
             ? new FileInputStream(f)
             : null;
    } catch(FileNotFoundException ex) {
      return null;
    }
  }

  private Date getDate(String s) {
    if ((s != null) && (s.length() > 0)) {
      try {
        return HTTPDateUtils.parseDate(s);
      } catch(Exception ignore) {
        Platform.ignoreException("Failed to parse HTTP Date:" + s, null);
      }
    }

    return null;
  }

  private String getFirstEntry(List<String> l) {
    if ((l != null) && (l.size() > 0)) {
      return l.get(0);
    }

    return null;
  }

  private LocalCacheResponse getLocalCacheResponse(URI remoteUri, File localFile) throws IOException {
    FileInputStream           is      = new FileInputStream(localFile);
    Map<String, List<String>> headers = loadHeaders(is);

    if (headers == null) {
      is.close();

      return null;
    }

    boolean ok = offlineMode;

    if (!ok) {
      List<String> l = headers.remove(URI_HEADER);

      if ((l == null) || (l.size() != 1) ||!remoteUri.toString().equals(l.get(0))) {
        is.close();

        return null;
      }

      l = headers.get("Cache-Control");

      long    max     = getMaxAge(l);
      boolean nocache = contains(l, "no-cache");
      Date    expires = (max < 0)
                        ? getDate(getFirstEntry(headers.get("Expires")))
                        : null;
      long    lm      = localFile.lastModified();

      do {
        if ((max > 0) &&!nocache) {
          if ((lm + (max * 1000)) > System.currentTimeMillis()) {
            ok = true;

            break;
          }
        }

        String etag    = getFirstEntry(headers.get("ETag"));
        String lastMod = getFirstEntry(headers.get("Last-Modified"));

        if ((expires == null) && (etag == null)) {
          Date d = getDate(lastMod);

          if (d != null) {
            lm = d.getTime();
          }

          long t = System.currentTimeMillis();

          t       = t + (t - lm);
          expires = new Date(t);
        }

        if ((expires != null) &&!nocache) {
          Date d = new Date();

          if (d.before(expires)) {
            ok = true;

            break;
          }
        }

        URLConnection conn;

        try {
          conn = remoteUri.toURL().openConnection();
        } catch(Exception ignore) {
          break;
        }

        if (!(conn instanceof HttpURLConnection)) {
          break;
        }

        HttpURLConnection hc = (HttpURLConnection) conn;

        try {
          // disable caching so we don't get in feedback loop with ResponseCache
          hc.setUseCaches(false);

          if (etag != null) {
            hc.setRequestProperty("If-None-Match", etag);
          }

          if (lastMod != null) {
            hc.setRequestProperty("If-Modified-Since", lastMod);
          }

          hc.connect();

          int rcode = hc.getResponseCode();

          if (rcode == 304) {
            ok = true;

            break;
          }
        } catch(IOException ex) {
          break;
        } finally {
          try {
            if (ok) {
              hc.disconnect();
            } else {
              hc.setUseCaches(true);
            }
          } catch(Exception ignore) {}
        }
      } while(false);
    }

    if (!ok) {
      is.close();
    }

    return ok
           ? new LocalCacheResponse(is, headers)
           : null;
  }

  private long getMaxAge(List<String> l) {
    long n = 0;

    if (l != null) {
      int len = l.size();

      for (int i = 0; i < len; i++) {
        String s = l.get(i);

        if ((s != null) && s.startsWith("max-age")) {
          i = s.indexOf('=', 7);

          if (i != -1) {
            n = SNumber.longValue(s.substring(i + 1));
          }

          break;
        }
      }
    }

    return n;
  }

  private class LocalCacheRequest extends CacheRequest {
    Map<String, List<String>> headers;
    private FileOutputStream  fos;
    private final File        localFile;
    private final File        tempFile;
    private final URI         uri;

    private LocalCacheRequest(File localFile, Map<String, List<String>> headers, URI uri) {
      this.localFile = localFile;
      this.headers   = headers;
      this.uri       = uri;
      tempFile       = new File(localFile.getAbsolutePath() + ".pending");
    }

    @Override
    public void abort() {
      if (fos == null) {
        return;
      }

      try {
        fos.close();
        localFile.delete();
      } catch(IOException e) {}
    }

    @Override
    public OutputStream getBody() throws IOException {
      if (fos == null) {
        fos = new FileOutputStream(tempFile) {
          @Override
          public void close() throws IOException {
            super.close();
            finish();
          }
        };
        writeHeaders(fos, headers, this.uri);
      }

      return fos;
    }

    private void finish() {
      tempFile.renameTo(localFile);
      cacheSize += localFile.length();

      if (cacheSize >= maxCacheSize) {
        startTrimming();
      }
    }
  }


  private class LocalCacheResponse extends CacheResponse {
    private Map<String, List<String>> headers;
    private InputStream               is;

    private LocalCacheResponse(InputStream is, Map<String, List<String>> headers) {
      this.headers = headers;
      this.is      = is;
    }

    @Override
    public InputStream getBody() throws IOException {
      return is;
    }

    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
      return headers;
    }
  }
}
