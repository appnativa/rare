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
import com.appnativa.rare.iDataCollection;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.CharScanner;
import com.appnativa.util.SNumber;
import com.appnativa.util.UTF8Helper;

import com.google.j2objc.annotations.Weak;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class CollectionURLConnection extends URLConnection implements iURLConnection {
  private String              defaultCharset = "iso-8859-1";
  private int                 fromColumn     = -1;
  private int                 fromIndex      = 0;
  private int                 toColumn       = 0;
  private int                 toIndex        = -1;
  private iPlatformAppContext appContext;
  private String              collectionName;
  @Weak
  private iWidget             contextWidget;
  private boolean             makeCopy;
  private String              mimeType;
  private String              subCollectionName;

  /**
   * Constructs a new instance
   *
   * @param url {@inheritDoc}
   */
  public CollectionURLConnection(URL url) {
    super(url);
    appContext     = Platform.getAppContext();
    collectionName = url.getHost();

    String name = url.getPath();

    if (iConstants.COLLECTION_PROTOCOL_HOSTSTRING.equals(collectionName) || (collectionName.length() == 0)) {
      collectionName = name;
      name           = url.getQuery();
    }

    parse(collectionName);

    if (name != null) {
      contextWidget = appContext.getWidgetFromPath(name);
    }
  }

  /**
   * Constructs a new instance
   *
   * @param url {@inheritDoc}
   */
  public CollectionURLConnection(URL url, String collection, iWidget context) {
    super(url);
    appContext     = Platform.getAppContext();
    collectionName = collection;
    contextWidget  = context;
    parse(collectionName);
  }

  @Override
  public void close() {}

  @Override
  public void connect() throws IOException {
    iDataCollection dc = getDataCollection();

    if ((dc != null) && dc.isRefreshOnURLConnection()) {
      dc.refresh(contextWidget);
    }
  }

  /**
   * Creates a new script URL
   *
   * @param context the context
   * @param collection the collection name
   *
   * @return the new URL
   *
   * @throws MalformedURLException
   */
  public static URL createURL(iWidget context, String collection) throws MalformedURLException {
    return NetHelper.createCollectionURL(context, collection);
  }

  public void disconnect() {}

  @Override
  public void dispose() {}

  @Override
  public boolean exist() {
    return true;
  }

  @Override
  public void open() throws IOException {
    connect();
  }

  @Override
  public void setCharset(String cs) {}

  public void setColumnRange(int from, int to) {
    fromColumn = from;
    toColumn   = from;
  }

  @Override
  public void setDefaultCharset(String charset) {
    if (charset == null) {
      charset = "iso-8859-1";
    }

    defaultCharset = charset;
  }

  @Override
  public void setHeaderField(String name, String value) {
    if ("content-type".equalsIgnoreCase(name)) {
      mimeType = value;
    }
  }

  public void setMakeCopy(boolean makeCopy) {
    this.makeCopy = makeCopy;
  }

  public void setRowRangex(int from, int to) {
    fromIndex = from;
    toIndex   = from;
  }

  public void setSubCollectionName(String subCollectionName) {
    this.subCollectionName = subCollectionName;
  }

  public void setToIndex(int toIndex) {
    this.toIndex = toIndex;
  }

  /**
   *  {@inheritDoc}
   */
  @Override
  public String getCharset() {
    return defaultCharset;
  }

  /**
   * Get the associated data collection
   *
   * @return the associated data collection
   */
  public Collection<RenderableDataItem> getCollection() {
    iDataCollection dc = getDataCollection();

    if (dc == null) {
      return Collections.EMPTY_LIST;
    }

    if (this.subCollectionName != null) {
      return dc.getSubItemData(contextWidget, subCollectionName, makeCopy);
    }

    return dc.getSubItemData(contextWidget, fromIndex, toIndex, fromColumn, toColumn, makeCopy);
  }

  /**
   * Get the associated collection
   *
   * @param url the URL
   * @return the associated collection
   */
  public static Collection<RenderableDataItem> getCollection(URL url) {
    return new CollectionURLConnection(url).getCollection();
  }

  /**
   * Get the associated collection
   *
   * @param context the context
   * @param collection the collection string
   * @return the associated collection
   * @throws java.net.MalformedURLException
   */
  public static Collection<RenderableDataItem> getCollection(iWidget context, String collection)
          throws MalformedURLException {
    return new CollectionURLConnection(createURL(context, collection)).getCollection();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getConnectionObject() {
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class getConnectionObjectClass() {
    return getClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentAsString() {
    iDataCollection dc = getDataCollection();
    iWidget         w  = (contextWidget == null)
                         ? appContext.getRootViewer()
                         : contextWidget;

    return (dc == null)
           ? ""
           : dc.getCollectionAsString(w);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentType() {
    String s = mimeType;

    if (s == null) {
      iDataCollection dc = getDataCollection();
      iWidget         w  = (contextWidget == null)
                           ? appContext.getRootViewer()
                           : contextWidget;

      s = (dc == null)
          ? iConstants.TEXT_MIME_TYPE
          : dc.getCollectionStringMimeType(w);
    }

    return s;
  }

  /**
   * Get the associated data collection
   *
   * @return the associated data collection
   */
  public iDataCollection getDataCollection() {
    return appContext.getDataCollection(collectionName);
  }

  /**
   * Get a handle the associated data collection
   *
   * @param url the URL
   * @return a handle to the associated data collection
   */
  public static iDataCollection getDataCollection(URL url) {
    return new CollectionURLConnection(url).getDataCollection();
  }

  public int getFromColumn() {
    return fromColumn;
  }

  public int getFromIndex() {
    return fromIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHeaderField(String name) {
    return JavaURLConnection.getHeaderField(this, name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, List<String>> getHeaderFields() {
    return JavaURLConnection.getHeaderFields(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(UTF8Helper.getInstance().getBytes(getContentAsString()));
  }

  @Override
  public Reader getReader() {
    return new StringReader(getContentAsString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getResponseCode() {
    return 200;
  }

  public String getSubCollectionName() {
    return subCollectionName;
  }

  public int getToColumn() {
    return toColumn;
  }

  public int getToIndex() {
    return toIndex;
  }

  public static boolean isCollectionURL(URL url) {
    if (url.getProtocol() == iConstants.COLLECTION_PROTOCOL_STRING) {
      return true;
    }

    return url.getHost() == iConstants.COLLECTION_PROTOCOL_HOSTSTRING;
  }

  public boolean isMakeCopy() {
    return makeCopy;
  }

  @SuppressWarnings("resource")
  private void parse(String name) {
    if (name.endsWith("#copy")) {
      makeCopy = true;
      name     = name.substring(0, name.length() - "#copy".length());
    }

    int n = name.indexOf('[');

    if (n != -1) {
      CharScanner sc = new CharScanner(name);

      name = name.substring(0, n);
      sc.consume(n + 1);

      char[] a   = sc.getContent();
      int[]  tok = sc.trim(sc.findToken(']', true, false));

      if ((tok != null) && (tok[1] > 0)) {
        if ((a[tok[0]] == '-') || Character.isDigit(a[tok[0]])) {
          fromIndex = SNumber.intValue(a, tok[0], tok[1], false);
          n         = CharScanner.lastIndexOf(a, tok[0], tok[1], '.');

          if ((n != -1) && (a[n - 1] == '.')) {
            toIndex = SNumber.intValue(a, n + 1, tok[1] + tok[0] - n, false);

            if (toIndex > -1) {
              toIndex++;
            }
          } else {
            toIndex = fromIndex++;
          }
        } else {
          this.subCollectionName = new String(a, tok[0], tok[1]);
        }
      }

      sc.foundDelimiter = 0;
      sc.trim(sc.findToken('[', true, true));

      if (sc.foundDelimiter == '[') {
        tok = sc.trim(sc.findToken(']', true, false));
      } else {
        tok = null;
      }

      if ((tok != null) && (tok[1] > 0)) {
        if ((a[tok[0]] == '-') || Character.isDigit(a[tok[0]])) {
          fromColumn = SNumber.intValue(a, tok[0], tok[1], false);
          n          = CharScanner.lastIndexOf(a, tok[0], tok[1], '.');

          if ((n != -1) && (a[n - 1] == '.')) {
            toColumn = SNumber.intValue(a, n + 1, tok[1] + tok[0] - n, false);

            if (toColumn > -1) {
              toColumn++;
            }
          } else {
            toColumn = 0;
          }
        }
      }
    }

    collectionName = name;
  }
}
