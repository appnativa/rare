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
import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.widget.iWidget;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class aNetHelper {
  protected static boolean hasStreamHandlerPermission = true;

  /**
   * Creates a new collection URL
   *
   * @param context
   *          the context
   * @param collection
   *          the collection name
   *
   * @return the new URL
   *
   * @throws MalformedURLException
   */
  public static URL createCollectionURL(iWidget context, String collection) throws MalformedURLException {
    iPlatformAppContext app = context.getAppContext();

    if (hasStreamHandlerPermission) {
      try {
        return new URL(iConstants.COLLECTION_PROTOCOL_STRING, "", 0, collection,
                       new CollectionStreamHandler(collection, context));
      } catch(Exception e) {
        hasStreamHandlerPermission = false;
      }
    }

    return new URL("http", iConstants.COLLECTION_PROTOCOL_HOSTSTRING, (int)app.hashCode(),
                   collection + "?" + context.getPathName());
  }

  public static URL createInlineURL(String data, String mimeType, String enc) throws MalformedURLException {
    if (mimeType == null) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    if (hasStreamHandlerPermission) {
      try {
        return new URL(iConstants.INLINE_PROTOCOL_STRING, "", 0, mimeType,
                       new InlineStreamHandler(data, mimeType, enc));
      } catch(Exception e) {
        hasStreamHandlerPermission = false;
      }
    }

    String path;

    if (enc != null) {
      path = mimeType + "~" + enc + "#" + data;
    } else {
      path = mimeType + "#" + data;
    }

    return new URL("http", iConstants.INLINE_PROTOCOL_HOSTSTRING, path);
  }

  /**
   * Creates a new script URL
   *
   * @param context
   *          the context
   * @param code
   *          the code to execute
   * @param mimeType
   *          the mime type of the result of the execution
   *
   * @return the new URL
   *
   * @throws MalformedURLException
   */
  public static URL createScriptURL(iWidget context, String code, String mimeType) throws MalformedURLException {
    if (context == null) {
      context = Platform.getContextRootViewer();
    }

    iPlatformAppContext app = context.getAppContext();

    if (mimeType == null) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    if (hasStreamHandlerPermission) {
      try {
        return new URL(iConstants.SCRIPT_PROTOCOL_STRING, iConstants.SCRIPT_PROTOCOL_HOSTSTRING, app.getIdentityInt(),
                       "", new ScriptStreamHandler(context, code, mimeType));
      } catch(Exception e) {
        hasStreamHandlerPermission = false;
      }
    }

    mimeType = "~" + context.getPathName() + "@" + mimeType + "?" + code;

    return new URL("http", iConstants.SCRIPT_PROTOCOL_HOSTSTRING, app.getIdentityInt(), mimeType);
  }

  public static boolean hasStreamHandlerPermission() {
    return hasStreamHandlerPermission;
  }

  static class CollectionStreamHandler extends URLStreamHandler implements iStreamHandler {
    String  collectionName;
    iWidget contextWidget;

    public CollectionStreamHandler(String collection, iWidget context) {
      this.collectionName = collection;
      this.contextWidget  = context;
    }

    @Override
    public URLConnection openConnection(URL u) {
      return new CollectionURLConnection(u);
    }

    @Override
    public boolean sameFile(URL url, URL other) {
      return url == other;
    }

    @Override
    public String toExternalForm(URL url) {
      return iConstants.COLLECTION_PROTOCOL_STRING + ":///" + collectionName + "?" + getQuery(url);
    }

    @Override
    public String toString(URL url) {
      return iConstants.COLLECTION_PROTOCOL_STRING + ":///" + collectionName + "?" + getQuery(url);
    }

    @Override
    public String getFile(URL url) {
      return collectionName;
    }

    @Override
    public String getHost(URL url) {
      return iConstants.COLLECTION_PROTOCOL_HOSTSTRING;
    }

    @Override
    public String getPath(URL url) {
      return getFile(url);
    }

    @Override
    public String getProtocol(URL url) {
      return iConstants.COLLECTION_PROTOCOL_STRING;
    }

    @Override
    public String getQuery(URL url) {
      return contextWidget.getPathName();
    }

    @Override
    public String getRef(URL url) {
      return "";
    }
  }


  static class InlineStreamHandler extends URLStreamHandler implements iStreamHandler {
    String encoding;
    String inlineData;
    String mimeType;

    public InlineStreamHandler(String data, String mime, String enc) {
      inlineData = data;
      mimeType   = mime;
      encoding   = enc;
    }

    @Override
    public URLConnection openConnection(URL u) {
      return new InlineURLConnection(u, inlineData, mimeType, encoding);
    }

    @Override
    public boolean sameFile(URL url, URL other) {
      return url == other;
    }

    @Override
    public String toExternalForm(URL url) {
      return InlineURLConnection.toExternalForm(url);
    }

    @Override
    public String toString(URL url) {
      String data = inlineData;

      if (data.length() > 77) {
        data = data.substring(0, 77) + "...";
      }

      return iConstants.INLINE_PROTOCOL_STRING + ":///" + mimeType + "?" + data;
    }

    @Override
    public String getFile(URL url) {
      return mimeType;
    }

    @Override
    public String getHost(URL url) {
      return iConstants.INLINE_PROTOCOL_HOSTSTRING;
    }

    @Override
    public String getPath(URL url) {
      return "";
    }

    @Override
    public String getProtocol(URL url) {
      return iConstants.INLINE_PROTOCOL_STRING;
    }

    @Override
    public String getQuery(URL url) {
      return inlineData;
    }

    @Override
    public String getRef(URL url) {
      return "";
    }
  }


  static class ScriptStreamHandler extends URLStreamHandler implements iStreamHandler {
    iWidget contextWidget;
    String  mimeType;
    String  scriptCode;

    public ScriptStreamHandler(iWidget context, String code, String mime) {
      contextWidget = context;
      scriptCode    = code;
      mimeType      = mime;
    }

    @Override
    public URLConnection openConnection(URL u) {
      return new ScriptURLConnection(contextWidget, u, scriptCode, mimeType);
    }

    @Override
    public boolean sameFile(URL url, URL other) {
      return url == other;
    }

    @Override
    public String toExternalForm(URL url) {
      return iConstants.SCRIPT_PROTOCOL_HOSTSTRING + ":///" + scriptCode;
    }

    @Override
    public String toString(URL url) {
      return toExternalForm(url);
    }

    @Override
    public String getFile(URL url) {
      return mimeType;
    }

    @Override
    public String getHost(URL url) {
      return iConstants.SCRIPT_PROTOCOL_HOSTSTRING;
    }

    @Override
    public String getPath(URL url) {
      return "";
    }

    @Override
    public String getProtocol(URL url) {
      return iConstants.SCRIPT_PROTOCOL_STRING;
    }

    @Override
    public String getQuery(URL url) {
      return "";
    }

    @Override
    public String getRef(URL url) {
      return "";
    }
  }
}
