/*
 * @(#)URLHelper.java   2008-08-06
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.net.InlineURLConnection;
import com.appnativa.rare.net.JavaURLConnection;
import com.appnativa.rare.net.iURLConnection;
import com.appnativa.rare.platform.swing.AppContext;
import com.appnativa.util.CharScanner;

/**
 *
 * @author decoteaud
 */
public class URLHelper {
  public static boolean canBeFileURL(String value) {
    if (value == null) {
      return false;
    }

    if (value.startsWith("http:")) {
      return false;
    }

    if (value.startsWith(iConstants.RESOURCE_PREFIX)) {
      return false;
    }

    if (value.startsWith(iConstants.SCRIPT_PREFIX)) {
      return false;
    }

    if (value.startsWith(iConstants.LIB_PREFIX)) {
      return false;
    }

    return true;
  }

  public static boolean exists(URL url) {
    try {
      if (isFileURL(url)) {
        File f = new FileEx(url.toURI());

        return f.exists();
      } else {
        iURLConnection con = AppContext.getContext().openConnection(url);

        return con.exist();
      }
    } catch(Exception e) {
      return false;
    }
  }

  public static String makeRelativePath(URL base, File file) throws MalformedURLException {
    if (!canBeFileURL(file.getPath())) {
      return file.getPath();
    }

    return makeRelativePath(base, file.toURI().toURL());
  }

  public static String makeRelativePath(URL base, String file) throws MalformedURLException {
    if (!canBeFileURL(file)) {
      return file;
    }

    File f = new FileEx(file);

    return makeRelativePath(base, f.toURI().toURL());
  }

  public static String makeRelativePath(URL base, URL file) {
    if (!canBeFileURL(file.getPath())) {
      return file.getPath();
    }

    if (!canBeRelative(file)) {
      return getName(file);
    }

    String       s1    = file.getPath();
    String       s2    = base.getPath();
    String[]     a     = s1.split("/");
    List<String> blist = new ArrayList<String>(a.length);

    for (int i = 2; i < a.length; i++) {
      blist.add(a[i]);
    }

    a = s2.split("/");

    List<String> flist = new ArrayList<String>(a.length);

    for (int i = 2; i < a.length; i++) {
      flist.add(a[i]);
    }

    Iterator<String> bit     = flist.iterator();
    Iterator<String> fit     = blist.iterator();
    StringBuffer     relPath = new StringBuffer();

    // strip off common parent directories
    while(bit.hasNext() && fit.hasNext()) {
      s1 = bit.next();
      s2 = fit.next();

      if (s1.equals(s2)) {
        bit.remove();
        fit.remove();
      } else {
        break;
      }
    }

    // reset the iterators and start over wit clean paths
    bit = flist.iterator();
    fit = blist.iterator();

    // add a .. for each segment left
    while(bit.hasNext()) {
      bit.next();
      relPath.append("..");
      relPath.append('/');
    }

    // move up the remainder of the path
    while(fit.hasNext()) {
      s1 = fit.next();
      relPath.append(s1);

      if (fit.hasNext()) {
        relPath.append('/');
      }
    }

    return relPath.toString();
  }

  public static String toString(File file) throws IOException {
    return JavaURLConnection.toExternalForm(file.toURI().toURL());
  }

  public static String toString(URL url) {
    return (url == null)
           ? null
           : JavaURLConnection.toExternalForm(url);
  }

  public static String toStringEx(URL url) {
    File f;

    try {
      f = getFile(url);

      if (f != null) {
        return f.getCanonicalPath();
      }
    } catch(Exception ex) {}

    return JavaURLConnection.toExternalForm(url);
  }

  public static URL toURL(String url) throws MalformedURLException {
    return Platform.getContextRootViewer().getURL(url);
  }

  public static String getDocumentName(URL url) throws IOException {
    if (!isInlineURL(url)) {
      return JavaURLConnection.toExternalForm(url);
    }

    return CharScanner.getPiece(open(url).getContentType(), ';', 1);
  }

  public static String getDocumentTitle(URL url) throws IOException {
    if (isInlineURL(url)) {
      String s = CharScanner.getPiece(open(url).getContentType(), "name=", 2);

      s = CharScanner.getPiece(s, ';', 1);

      String s2 = CharScanner.getPiece(s, ']', 2);

      s = (s2 == null)
          ? s
          : s2;

      return CharScanner.getPiece(s, "__", 1);
    }

    String file = url.getFile();
    int    n    = file.lastIndexOf('/');

    if (n != -1) {
      file = file.substring(n + 1);
    }

    return file;
  }

  public static String getDocumentTooltip(URL url) throws IOException, URISyntaxException {
    if (url.getProtocol().equals("file")) {
      return getFile(url).getCanonicalPath();
    }

    if (isInlineURL(url)) {
      String s = CharScanner.getPiece(open(url).getContentType(), "name=", 2);

      return CharScanner.getPiece(s, ';', 1);
    }

    return JavaURLConnection.toExternalForm(url);
  }

  public static File getFile(URL url) throws IOException, URISyntaxException {
    if (!isFileURL(url)) {
      return null;
    }

    return new FileEx(url.toURI());
  }

  public static String getName(URL url) {
    String path = url.getPath();
    int    n    = (path == null)
                  ? -1
                  : path.lastIndexOf('/');

    if (n == -1) {
      return "<unknown>";
    }

    return path.substring(n + 1);
  }

  public static OutputStream getOutputStream(URL url) throws IOException, URISyntaxException {
    if ("file".equals(url.getProtocol())) {
      return new FileOutputStream(new FileEx(url.toURI()));
    }

    iURLConnection con = AppContext.getContext().openConnection(url);

    if (con.getConnectionObject() instanceof HttpURLConnection) {
      ((HttpURLConnection) con.getConnectionObject()).setRequestMethod("PUT");
    }

    return con.getOutputStream();
  }

  public static URL getParent(URL url) throws IOException, URISyntaxException {
    if (isFileURL(url)) {
      File f = new FileEx(url.toURI());

      f = f.getParentFile();

      return (f == null)
             ? null
             : f.toURI().toURL();
    } else if ("http".equals(url.getProtocol())) {
      String path = url.getPath();
      int    n    = (path == null)
                    ? -1
                    : path.lastIndexOf('/');

      if (n == -1) {
        return null;
      }

      path = path.substring(0, n);

      return new URL("http", url.getHost(), url.getPort(), path);
    }

    return null;
  }

  public static File getParentFile(URL url) throws IOException, URISyntaxException {
    File f = getFile(url);

    return (f == null)
           ? null
           : f.getParentFile();
  }

  public static URL getURL(File f) throws IOException, URISyntaxException {
    if (f == null) {
      return null;
    }

    if (canBeFileURL(f.getPath())) {
      return f.toURI().toURL();
    }

    return new URL(f.getPath());
  }

  public static URL getURL(URL base, String s) {
    if (s == null) {
      return null;
    }

    try {
      return new URL(base, s);
    } catch(Exception e) {
      return null;
    }
  }

  public static Writer getWriter(URL url) throws IOException, URISyntaxException {
    if ("file".equals(url.getProtocol())) {
      return new FileWriter(new FileEx(url.toURI()));
    }

    return new OutputStreamWriter(getOutputStream(url));
  }

  public static boolean isFileURL(URL url) {
    return (url == null)
           ? false
           : "file".equals(url.getProtocol());
  }

  public static boolean isInlineURL(URL url) {
    if (url == null) {
      return false;
    }

    return InlineURLConnection.isInlineURL(url);
  }

  private static boolean canBeRelative(URL url) {
    if (url == null) {
      return false;
    }

    String protocol = url.getProtocol();

    if (protocol.equals("http")) {
      return true;
    }

    if (protocol.equals("file")) {
      return true;
    }

    return true;
  }

  private static iURLConnection open(URL url) throws IOException {
    return Platform.openConnection(url, null);
  }
}
