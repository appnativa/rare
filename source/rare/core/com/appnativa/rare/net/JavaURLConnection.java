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
import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.util.MIMEMap;
import com.appnativa.util.Base64;
import com.appnativa.util.CharArray;
import com.appnativa.util.IdentityArrayList;
import com.appnativa.util.Streams;
import com.appnativa.util.StringCache;
import com.appnativa.util.io.BufferedReaderEx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Don DeCoteau
 */
public class JavaURLConnection implements iURLConnection {
  public static int                                            MAX_REDIRECTS = 5;
  public static boolean                                        disableHTTPSKeepAlive;
  public static boolean                                        disableKeepAlive;
  private static iConnectionChecker                            connectionChecker;
  private static boolean                                       connectionCheckerEnabled;
  private static final String                                  httpAgent;
  private static long                                          lastConnectionSuccess;
  private static volatile IdentityArrayList<JavaURLConnection> openConnections;
  private static ReentrantLock                                 openLock;
  private static volatile boolean                              trackOpenConnections;

  static {
    String agent = null;

    if (Platform.getAppContext()!=null && Platform.getAppContext().isWebStart()) {    // will work properly when not launched via web start
      try {
        agent = System.getProperty("http.agent", null);
      } catch(Exception e) {
        Platform.ignoreException(null, e);
      }
    }

    httpAgent = agent;

    try {
      String name = System.getProperty("rare.net.connectionChecker", null);

      if (name != null) {
        connectionChecker = (iConnectionChecker) Class.forName(name).newInstance();
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }

    try {
      String s = System.getProperty("rare.net.trackOpenConnections", null);

      if ((s != null) && s.equals("true")) {
        setTrackOpenConnections(true);
      }
    } catch(Exception e) {
      Platform.ignoreException(null, e);
    }
  }

  protected URLConnection aConnection;
  protected String        charSet;
  protected InputStream   inputStream;
  protected String        mimeType;
  protected String        outputUri;
  protected String        defaultCharset    = "ISO-8859-1";
  protected boolean       handleRedirection = true;
  protected boolean       connected;
  protected URL           url301;

  /**
   * Creates a new instance of JavaURLConnection
   *
   * @param conn
   *          {@inheritDoc}
   */
  public JavaURLConnection(URLConnection conn) {
    this(conn, null);
  }

  /**
   * Creates a new instance of JavaURLConnection
   *
   * @param conn
   *          {@inheritDoc}
   * @param userInfo
   *          user info
   */
  public JavaURLConnection(URLConnection conn, String userInfo) {
    aConnection = conn;

    if (aConnection instanceof HttpURLConnection) {
      if (httpAgent != null) {
        ((HttpURLConnection) conn).setRequestProperty("User-Agent", httpAgent);
      }

      if (disableKeepAlive) {
        ((HttpURLConnection) conn).setRequestProperty("Connection", "close");
      } else if (disableHTTPSKeepAlive && conn.getURL().getProtocol().equals("https")) {
        ((HttpURLConnection) conn).setRequestProperty("Connection", "close");
      }
    }

    String ui = aConnection.getURL().getUserInfo();

    if (ui == null) {
      ui = userInfo;
    }

    if (ui != null) {
      if (ui.indexOf(':') != -1) {
        ui = Base64.encode(ui.getBytes());

        int n = ui.indexOf('\n');

        if (n != -1) {
          ui = CharArray.removeLinefeeds(ui);
        }
      }

      aConnection.setRequestProperty("Authorization", "Basic " + ui);
    }
  }

  /**
   * Creates a new instance of JavaURLConnection
   *
   * @param conn
   *          {@inheritDoc}
   * @param userInfo
   *          user info
   * @param mimeType
   *          {@inheritDoc}
   */
  public JavaURLConnection(URLConnection conn, String userInfo, String mimeType) {
    this(conn, userInfo);
    this.mimeType = mimeType;
  }

  /**
   * Converts the base URL, of the specified URL, to its external form
   *
   * @param url
   *          the URL to convert
   *
   * @return the string representation of the URL
   */
  public static String baseToExternalForm(URL url) {
    if (url == null) {
      return "";
    }

    // pre-compute length of StringBuilder
    int len = url.getProtocol().length() + 1;

    if ((url.getAuthority() != null) && (url.getAuthority().length() > 0)) {
      len += 2 + url.getAuthority().length();
    }

    StringBuilder result = new StringBuilder(len);

    result.append(url.getProtocol());
    result.append(":");

    if (url.getProtocol().startsWith("file")) {
      result.append("//");
    } else if ((url.getAuthority() != null) && (url.getAuthority().length() > 0)) {
      result.append("//");
      result.append(url.getAuthority());
    }

    result.append("/");

    return result.toString();
  }

  @Override
  public void close() {
    if (aConnection != null) {
      connectionClosed(this);
    }

    connected = false;
    outputUri = null;

    if (connected && (aConnection instanceof HttpURLConnection)) {
      HttpURLConnection hc    = (HttpURLConnection) aConnection;
      byte              buf[] = null;
      int               ret;

      try {
        hc.setReadTimeout(500);
      } catch(Exception ignored) {}

      if (inputStream != null) {
        try {
          InputStream stream = hc.getInputStream();

          ret = stream.read();

          if (ret > -1) {
            buf = new byte[256];

            while(stream.read(buf) > 0) {}
          }

          stream.close();
        } catch(IOException e) {
          try {
            InputStream es = hc.getErrorStream();

            if (es != null) {
              if (buf == null) {
                buf = new byte[256];
              }

              while(es.read(buf) > 0) {}

              es.close();
            }
          } catch(Exception ignored) {}
        }

        try {
          inputStream.close();
        } catch(Exception ignored) {}
      } else {
        try {
          hc.disconnect();
        } catch(Exception ignored) {}
      }
    }

    aConnection = null;
    inputStream = null;
  }

  /**
   * Closes all previously open connections that are still open. Does nothing if
   * connections are not being tracked.
   */
  public static void closeOpenConnections(boolean debug) {
    if (trackOpenConnections && (openConnections != null) &&!openConnections.isEmpty()) {
      JavaURLConnection a[] = null;

      openLock.lock();

      try {
        a = openConnections.toArray(new JavaURLConnection[openConnections.size()]);
        openConnections.clear();
      } finally {
        openLock.unlock();
      }

      if (a != null) {
        for (JavaURLConnection conn : a) {
          if (debug) {
            try {
              System.err.println("Closing open connection: " + conn.getURL());
            } catch(Exception ignore) {}
          }

          try {
            conn.close();
          } catch(Throwable e) {}
        }
      }
    }
  }

  public static JavaURLConnection[] getOpenConnections() {
    openLock.lock();

    try {
      return openConnections.toArray(new JavaURLConnection[openConnections.size()]);
    } finally {
      openLock.unlock();
    }
  }

  @Override
  public void dispose() {
    close();
  }

  /**
   * DIsposes of a connection
   *
   * @param con
   *          the connection to dispose
   */
  public static void dispose(URLConnection con) {
    try {
      JavaURLConnection jcon = new JavaURLConnection(con);

      jcon.dispose();
    } catch(Throwable ignored) {}
  }

  @Override
  public boolean exist() {
    try {
      if (aConnection instanceof HttpURLConnection) {
        ((HttpURLConnection) aConnection).setRequestMethod("HEAD");

        return ((HttpURLConnection) aConnection).getResponseCode() < 400;
      }

      return true;
    } catch(Exception e) {
      return false;
    } finally {
      close();
    }
  }

  @Override
  public void open() throws IOException {
    connectAndCheckForError();
  }

  /**
   * Converts the parent URL, of the specified URL, to its external form
   *
   * @param url
   *          the URL to convert
   *
   * @return the string representation of the URL
   */
  public static String parenToExternalForm(URL url) {
    if (url == null) {
      return "";
    }

    // pre-compute length of StringBuilder
    int len = url.getProtocol().length() + 1;

    if ((url.getAuthority() != null) && (url.getAuthority().length() > 0)) {
      len += 2 + url.getAuthority().length();
    }

    StringBuilder result = new StringBuilder(len);

    result.append(url.getProtocol());
    result.append(":");

    if (url.getProtocol().startsWith("file")) {
      result.append("//");
    } else if ((url.getAuthority() != null) && (url.getAuthority().length() > 0)) {
      result.append("//");
      result.append(url.getAuthority());
    }

    String s = url.getPath();

    if (s.length() > 0) {
      int n = s.lastIndexOf('/');

      if (n + 1 == s.length()) {
        n = s.lastIndexOf('/', n - 1);
      }

      if (n != -1) {
        s = s.substring(0, n);
      }

      result.append(s);
    }

    result.append("/");

    return result.toString();
  }

  /**
   * Converts a URL to its external form
   *
   * @param url
   *          the URL to convert
   *
   * @return the string representation of the URL
   */
  public static String toExternalForm(URL url) {
    if (url == null) {
      return "";
    }

    String s = url.toExternalForm();

    if (url.getProtocol().equals("file") &&!s.startsWith("file://")) {
      String p = url.getPath();
      int    n = s.indexOf(p);

      if (n != -1) {
        s = "file://" + s.substring(n);
      }
    }

    return s;
  }

  /**
   * Converts a URL to a form that can be use internally
   *
   * @param url
   *          the URL to convert
   *
   * @return the string representation of the URL
   */
  public static String toInternalForm(URL url) {
    if (InlineURLConnection.isInlineURL(url)) {
      return Integer.toHexString(System.identityHashCode(url));
    }

    return toExternalForm(url);
  }

  @Override
  public void setCharset(String cs) {
    charSet = cs;
  }

  public static void setConnectionCheckerEnabled(boolean enabled) {
    connectionCheckerEnabled = enabled;
  }

  @Override
  public void setDefaultCharset(String charset) {
    if (charset == null) {
      charset = "ISO-8859-1";
    }

    defaultCharset = charset;
  }

  /**
   * @param handleRedirection
   *          the handleRedirection to set
   */
  public void setHandleRedirection(boolean handleRedirection) {
    this.handleRedirection = handleRedirection;
  }

  @Override
  public void setHeaderField(String name, String value) {
    aConnection.setRequestProperty(name, value);
  }

  @Override
  public void setReadTimeout(int milliseconds) {
    aConnection.setReadTimeout(milliseconds);
    aConnection.setConnectTimeout(milliseconds);
  }

  public static void setTrackOpenConnections(boolean trackOpenConnections) {
    JavaURLConnection.trackOpenConnections = trackOpenConnections;

    if (openConnections == null) {
      openConnections = new IdentityArrayList<JavaURLConnection>(5);
      openLock        = new ReentrantLock();
    }
  }

  @Override
  public String getCharset() throws IOException {
    if (charSet != null) {
      return charSet;
    }

    charSet = getCharset(getContentType(), defaultCharset);

    return charSet;
  }

  public static String getCharset(String mime, String defcharset) {
    String charSet = defcharset;

    if (charSet == null) {
      charSet = "ISO-8859-1";
    }

    String s = mime;

    if ((s != null) && (s.length() > 0)) {
      int n = s.indexOf("charset=");

      if (n == -1) {
        s = null;
      } else {
        n += "charset=".length();

        int p = s.indexOf(';', n);

        if (p == -1) {
          s = s.substring(n);
        } else {
          s = s.substring(n, p);
        }

        s = s.trim();
      }
    }

    if ((s != null) && (s.length() > 0)) {
      charSet = s;
    }

    return charSet;
  }

  @Override
  public Object getConnectionObject() {
    return aConnection;
  }

  @Override
  public Class getConnectionObjectClass() {
    return aConnection.getClass();
  }

  @Override
  public Object getContent() throws IOException {
    try {
      connectAndCheckForError();

      return aConnection.getContent();
    } finally {
      close();
    }
  }

  @Override
  public String getContentAsString() throws IOException {
    Reader r = new InputStreamReader(getInputStream(), getCharset());

    try {
      return Streams.readerToString(r);
    } finally {
      close();
    }
  }

  @Override
  public String getContentEncoding() throws IOException {
    connectAndCheckForError();

    return aConnection.getContentEncoding();
  }

  @Override
  public int getContentLength() {
    return aConnection.getContentLength();
  }

  @Override
  public String getContentType() throws IOException {
    if (mimeType != null) {
      return mimeType;
    }

    connectAndCheckForError();

    String s = aConnection.getContentType();

    if ((s == null) || iConstants.UNKNOWN_MIME_TYPE.equals(s)) {
      s        = MIMEMap.typeFromFile(getURL().toExternalForm());
      mimeType = s;
    }

    return s;
  }

  /**
   * Gets the URL fore a HTTP 301 redirect
   *
   * @return the URL fore a HTTP 301 redirect
   */
  public URL getHTTP301URL() {
    return url301;
  }

  @Override
  public String getHeaderField(String name) {
    return aConnection.getHeaderField(name);
  }

  public static String getHeaderField(iURLConnection conn, String name) {
    try {
      if ("content-type".equalsIgnoreCase(name)) {
        return conn.getContentType();
      }

      if ("content-length".equalsIgnoreCase(name)) {
        return conn.getContentType();
      }
    } catch(IOException ignored) {}

    return null;
  }

  @Override
  public Map<String, List<String>> getHeaderFields() {
    return aConnection.getHeaderFields();
  }

  public static Map<String, List<String>> getHeaderFields(iURLConnection conn) {
    try {
      String  t       = conn.getContentType();
      int     len     = conn.getContentLength();
      HashMap headers = new HashMap<String, List<String>>();

      headers.put("Content-Type", Arrays.asList(t));
      headers.put("Content-Length", Arrays.asList(StringCache.valueOf(len)));

      return headers;
    } catch(IOException ex) {
      return Collections.EMPTY_MAP;
    }
  }

  @Override
  public InputStream getInputStream() throws IOException {
    connectAndCheckForError();

    return inputStream = aConnection.getInputStream();
  }

  /**
   * Gets the last time a successful connection was made
   *
   * @return the last time a successful connection was made (in milliseconds)
   */
  public static long getLastConnectionSuccessTime() {
    return lastConnectionSuccess;
  }

  @Override
  public long getLastModified() {
    return aConnection.getLastModified();
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    if (!connected) {
      outputUri = PlatformHelper.handleCookieInjection(aConnection);
      connected = true;
    }

    return aConnection.getOutputStream();
  }

  public String getPassedInMimeType() {
    return mimeType;
  }

  @Override
  public int getReadTimeout() {
    return aConnection.getReadTimeout();
  }

  @Override
  public Reader getReader() throws IOException {
    connectAndCheckForError();

    final int n = getContentLength();

    if ((n > 0) && (n < 8192)) {
      return new BufferedReaderEx(new InputStreamReader(aConnection.getInputStream(), getCharset()), n);
    }

    return new BufferedReaderEx(new InputStreamReader(aConnection.getInputStream(), getCharset()), (n > 0)
            ? 8192
            : 4096);
  }

  @Override
  public int getResponseCode() throws IOException {
    if (aConnection instanceof HttpURLConnection) {
      return ((HttpURLConnection) aConnection).getResponseCode();
    }

    return -1;
  }

  @Override
  public URL getURL() {
    return aConnection.getURL();
  }

  /**
   * Returns the windows drive letter part of a URL
   *
   * @param url
   *          the URL
   *
   * @return the windows drive letter part or null if the url is not a window
   *         file
   */
  public static String getWindowsDrivePart(URL url) {
    if ((url != null) && url.getProtocol().equals("file") && isWindows()) {
      String s = url.getFile();
      int    n = s.indexOf(':');

      if (n != -1) {
        n = s.indexOf('/', n);
      }

      if (n != -1) {
        return s.substring(0, n);
      }
    }

    return null;
  }

  public boolean isConnected() {
    return connected;
  }

  /**
   * @return the handleRedirection
   */
  public boolean isHandleRedirection() {
    return handleRedirection;
  }

  public static boolean isTrackOpenConnections() {
    return trackOpenConnections;
  }

  protected void connectAndCheckForError() throws IOException {
    if (connected) {
      return;
    }

    String uri = connected
                 ? outputUri
                 : PlatformHelper.handleCookieInjection(aConnection);

    outputUri = null;
    connectionOpened(this);
    aConnection.connect();

    int count = 0;
    int code  = 200;

    while(count <= MAX_REDIRECTS) {
      if (!handleRedirection ||!(aConnection instanceof HttpURLConnection)) {
        break;
      }

      HttpURLConnection hc = (HttpURLConnection) aConnection;

      code = hc.getResponseCode();

      if ((code == 301) || (code == 302) || (code == 303)) {
        if (count == MAX_REDIRECTS) {
          String s = Platform.getResourceAsString("Rare.runtime.text.redirectionMaxExceeded");

          s = PlatformHelper.format(s, MAX_REDIRECTS);

          throw new HTTPException(s, hc);
        }

        if ((code != 303) && hc.getDoOutput()) {
          throw new HTTPException(hc);
        }

        String s = hc.getHeaderField("Location");

        if ((s != null) && (s.length() > 0)) {
          aConnection = new URL(s).openConnection();

          if ((code == 301) && (url301 == null)) {
            url301 = aConnection.getURL();
          }
        } else {
          throw new HTTPException(hc);
        }

        code = 200;
        count++;

        continue;
      }

      break;
    }

    if (code != 200) {
      connected = false;

      if (uri == null) {
        uri = aConnection.getURL().toExternalForm();
      }

      Platform.debugLog("failed to connect to " + uri + ": code=" + code);

      try {
        Platform.debugLog("Data:" + Streams.streamToString(aConnection.getInputStream()));
      } catch(Throwable e) {}

      throw new HTTPException((HttpURLConnection) aConnection);
    } else {
      connected             = true;
      lastConnectionSuccess = System.currentTimeMillis();

      if (uri != null) {
        PlatformHelper.handleCookieExtraction(uri, aConnection);
      }
    }

    if ((connectionChecker != null) && connectionCheckerEnabled) {
      connectionChecker.check(this);
    }
  }

  private static void connectionClosed(JavaURLConnection conn) {
    if (trackOpenConnections) {
      openLock.lock();

      try {
        openConnections.remove(conn);
      } finally {
        openLock.unlock();
      }
    }
  }

  public static void stopTrackingConnection(JavaURLConnection conn) {
    if (trackOpenConnections) {
      openLock.lock();

      try {
        openConnections.remove(conn);
      } finally {
        openLock.unlock();
      }
    }
  }
  
  private static void connectionOpened(JavaURLConnection conn) {
    if (trackOpenConnections) {
      openLock.lock();

      try {
        openConnections.addIfNotPresent(conn);
      } finally {
        openLock.unlock();
      }
    }
  }

  private static boolean isWindows() {
    try {
      String os = System.getProperty("os.name");

      return (os != null) && (os.indexOf("Windows") != -1);
    } catch(Throwable ignore) {
      return false;
    }
  }
}
