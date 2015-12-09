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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.appnativa.rare.Platform;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.scripting.iScriptHandler;
import com.appnativa.rare.ui.aWidgetListener;
import com.appnativa.rare.ui.event.EventBase;
import com.appnativa.rare.widget.iWidget;
import com.appnativa.util.UTF8Helper;

/**
 *
 *
 * @version    0.3, 2007-05-14
 * @author     Don DeCoteau
 */
public class ScriptURLConnection extends URLConnection implements iURLConnection {
  private String      defaultCharset = "ISO-8859-1";
  private iWidget     contextWidget;
  private EventBase   eventObject;
  private String      mimeType;
  private int         readTimeout;
  private Object      returnValue;
  private String      scriptCode;
  private Object      scriptObjectCode;

  /**
   * Constructs a new instance
   *
   * @param url the URL
   */
  public ScriptURLConnection(URL url) {
    super(url);

    iPlatformAppContext app  = Platform.getAppContext();
    String              data = url.getPath();

    scriptCode = url.getQuery();

    String name = null;
    int    n    = data.indexOf('@');

    if (n == -1) {
      n = data.indexOf('~');

      if (n != -1) {
        name = data.substring(n + 1);
      }
    } else {
      mimeType = data.substring(n + 1);

      int p = data.indexOf('~');

      if (p != -1) {
        name = data.substring(p + 1, n);
      }
    }

    if (mimeType == null) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    if (app != null) {
      if (name != null) {
        contextWidget = app.getWidgetFromPath(name);
      }

      if (contextWidget == null) {
        contextWidget = app.getRootViewer();
      }
    }
  }

  /**
   * Constructs a new instance
   *
   * @param context the context
   * @param url the URL
   * @param code the code to execute
   * @param mime the mime type of the result of the execution
   */
  public ScriptURLConnection(iWidget context, URL url, String code, String mime) {
    super(url);
    contextWidget = context;
    scriptCode    = code;
    mimeType      = mime;

    if ((mimeType == null) || (mimeType.length() == 0)) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    eventObject = new EventBase(context);
  }

  @Override
  public void close() {}

  @Override
  public void connect() {}

  /**
   * Creates a new script URL
   *
   * @param context the context
   * @param code the code to execute
   * @param mimeType the mime type of the result of the execution
   *
   * @return the new URL
   *
   * @throws MalformedURLException
   */
  public static URL createURL(iWidget context, String code, String mimeType) throws MalformedURLException {
    return NetHelper.createScriptURL(context, code, mimeType);
  }

  public void disconnect() {}

  @Override
  public void dispose() {}

  @Override
  public boolean exist() {
    return true;
  }

  @Override
  public void open() {
    connect();
  }

  @Override
  public void setCharset(String cs) {}

  @Override
  public void setDefaultCharset(String charset) {
    if (charset == null) {
      charset = "iso-8859-1";
    }

    defaultCharset = charset;
  }

  @Override
  public void setHeaderField(String name, String value) {}

  /**
   * @param readTimeout the readTimeout to set
   */
  @Override
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  @Override
  public String getCharset() {
    return defaultCharset;
  }

  @Override
  public Object getConnectionObject() {
    return this;
  }

  @Override
  public Class getConnectionObjectClass() {
    return getClass();
  }

  @Override
  public Object getContent() {
    returnValue = null;
    getContentEx();

    return returnValue;
  }

  @Override
  public String getContentAsString() {
    Object o = getContent();

    return (o == null)
           ? ""
           : o.toString();
  }

  @Override
  public String getContentType() {
    return mimeType;
  }

  @Override
  public String getHeaderField(String name) {
    return JavaURLConnection.getHeaderField(this, name);
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(UTF8Helper.getInstance().getBytes(getContentAsString()));
  }

  /**
   * @return the readTimeout
   */
  @Override
  public int getReadTimeout() {
    return readTimeout;
  }

  @Override
  public Reader getReader() {
    return new StringReader(getContentAsString());
  }

  @Override
  public int getResponseCode() {
    return 200;
  }

  private Object getContentEx() {
    returnValue = null;

    if (!contextWidget.isDesignMode() && (scriptCode != null) && (scriptCode.length() > 0)) {
      iScriptHandler sh = contextWidget.getScriptHandler();

      if (scriptObjectCode == null) {
        scriptObjectCode = sh.compile(contextWidget.getScriptingContext(),
                                      contextWidget.getName() + "." + iConstants.EVENT_FUNCTION_EVAL, scriptCode);
      }

      returnValue = aWidgetListener.evaluate(contextWidget, sh, scriptObjectCode, eventObject);
    }

    return returnValue;
  }
}
