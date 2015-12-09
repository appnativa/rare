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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.appnativa.rare.iConstants;
import com.appnativa.util.ASCII85InputStream;
import com.appnativa.util.Base64;
import com.appnativa.util.ByteArray;
import com.appnativa.util.CharScanner;
import com.appnativa.util.Streams;
import com.appnativa.util.URLEncoder;
import com.appnativa.util.io.BufferedReaderEx;

/**
 *
 *
 * @version 0.3, 2007-05-14
 * @author Don DeCoteau
 */
public class InlineURLConnection extends URLConnection implements iURLConnection {
  private String defaultCharset = "ISO-8859-1";
  private String charSet;
  private String contentEncoding;
  private String inlineData;
  private String mimeType;

  /**
   * Constructs a new instance
   *
   * @param url
   *          the URL
   */
  public InlineURLConnection(URL url) {
    this(url, null);
  }

  /**
   * Constructs a new instance
   *
   * @param url
   *          the URL
   */
  public InlineURLConnection(URL url, String mime) {
    super(url);
    inlineData = url.getRef();

    if (inlineData == null) {
      final String s = url.getFile();
      final int n = s.indexOf('#');

      inlineData = (n == -1) ? "" : s.substring(n + 1);

      int p = s.indexOf('~');

      if ((p > -1) && (p < n)) {
        mimeType = s.substring(0, p);

        final int p2 = s.indexOf('~', p + 1);

        if ((p2 > -1) && (p2 < n)) {
          contentEncoding = s.substring(p + 1, p2);
        }
      } else if (n > -1) {
        mimeType = s.substring(0, n);
      }
    } else {
      final String s = url.getFile();

      mimeType = CharScanner.getPiece(s, '~', 1);
      contentEncoding = CharScanner.getPiece(s, '~', 2);
    }

    if (mimeType == null) {
      mimeType = iConstants.TEXT_MIME_TYPE;
    }

    if (mime != null) {
      mimeType = mime;
    }

    if ((contentEncoding != null) && (contentEncoding.length() == 0)) {
      contentEncoding = null;
    }
  }

  /**
   * Constructs a new instance
   *
   * @param url
   *          the URL
   * @param data
   *          the data
   * @param mime
   *          the MIME type
   * @param enc
   *          the the content encoding
   */
  public InlineURLConnection(URL url, String data, String mime, String enc) {
    super(url);
    inlineData = data;
    mimeType = mime;
    contentEncoding = enc;
    charSet = JavaURLConnection.getCharset(mimeType, "ISO-8859-1");
  }

  @Override
  public void close() {
  }

  @Override
  public void connect() throws IOException {
  }

  public static URL createURL(String data, String mimeType, String enc) throws MalformedURLException {
    return NetHelper.createInlineURL(data, mimeType, enc);
  }

  public static URL createURL(String hier_part) throws MalformedURLException {
    try {
      int n = hier_part.indexOf(',');
      String data = hier_part.substring(n + 1);
      int p = hier_part.indexOf(';');
      String enc = p == -1 ? null : hier_part.substring(p + 1, n);
      if (p == -1) {
        p = n;
      }
      String mimeType = hier_part.substring(0, p);
      if (mimeType.length() == 0) {
        mimeType = "application/x-www-form-urlencoded";
      }
      return NetHelper.createInlineURL(data, mimeType, enc);
    } catch (Exception e) {
      throw new MalformedURLException(hier_part);
    }
  }

  public void disconnect() {
  }

  @Override
  public void dispose() {
  }

  @Override
  public boolean exist() {
    return true;
  }

  @Override
  public void open() throws IOException {
    connect();
  }

  public static String toExternalForm(URL url) {
    StringBuilder sb = new StringBuilder(iConstants.INLINE_PREFIX);
    InlineURLConnection ic = null;

    if (NetHelper.hasStreamHandlerPermission()) {
      try {
        ic = (InlineURLConnection) url.openConnection();
      } catch (Exception e) {
      }
    }

    if (ic == null) {
      ic = new InlineURLConnection(url);
    }

    sb.append(ic.mimeType);

    if (ic.charSet != null) {
      sb.append(";charset=").append(ic.charSet);
    }

    if (ic.contentEncoding != null) {
      sb.append(";").append(ic.contentEncoding);
    }

    try {
      URLEncoder.encode(ic.inlineData, ic.getCharset(), sb);
    } catch (UnsupportedEncodingException ex) {
      sb.append(ic.inlineData);
    }

    return sb.toString();
  }

  @Override
  public void setCharset(String cs) {
    charSet = cs;
  }

  @Override
  public void setDefaultCharset(String charset) {
    if (charset == null) {
      charset = "ISO-8859-1";
    }

    defaultCharset = charset;
  }

  @Override
  public void setHeaderField(String name, String value) {
    if ("content-type".equalsIgnoreCase(name)) {
      mimeType = value;
    }
  }

  /**
   * Gets a byte array for the associated queryString
   *
   * @return a byte array for the associated queryString
   * @throws java.io.IOException
   */
  public ByteArray getByteArray() throws IOException {
    InputStream in=getInputStream();
    if(in instanceof ByteArray) {
      return (ByteArray)in;
    }
    ByteArray ba=new ByteArray(inlineData.length());
    Streams.streamToBytes(in, ba);
    return ba;
  }

  @Override
  public String getCharset() {
    return (charSet != null) ? charSet : defaultCharset;
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
  public String getContentAsString() throws IOException {
    return inlineData;
  }

  @Override
  public int getContentLength() {
    return inlineData.length();
  }

  @Override
  public String getContentType() {
    String s = mimeType;

    if (s == null) {
      s = "text/plain";
    }

    return s;
  }

  @Override
  public String getHeaderField(String name) {
    return JavaURLConnection.getHeaderField(this, name);
  }

  @Override
  public Map<String, List<String>> getHeaderFields() {
    return JavaURLConnection.getHeaderFields(this);
  }

  @Override
  public InputStream getInputStream() throws IOException {
    byte[] bytes = null;
    if (contentEncoding != null) {
      if (contentEncoding.equals("base64")) {
        bytes = Base64.decode(inlineData);
      } else if (contentEncoding.equalsIgnoreCase("base85") || contentEncoding.equalsIgnoreCase("ascii85")) {
        bytes = inlineData.getBytes("US-ASCII");
        return new ASCII85InputStream(new ByteArray(bytes));
      }
    }
    if (bytes == null) {
      bytes = inlineData.getBytes(getCharset());
    }
    return new ByteArray(bytes);
  }

  @Override
  public Reader getReader() throws IOException {
    if (contentEncoding != null) {
      return new BufferedReaderEx(new InputStreamReader(getInputStream(), getCharset()));
    }
    return new BufferedReaderEx(new StringReader(inlineData), (inlineData.length() == 0) ? 1 : inlineData.length());
  }

  @Override
  public int getResponseCode() {
    return 200;
  }

  public static boolean isInlineURL(URL url) {
    if (url == null) {
      return false;
    }

    if (NetHelper.hasStreamHandlerPermission()) {
      return url.getProtocol().equals(iConstants.INLINE_PROTOCOL_STRING);
    }

    return url.getHost().equals(iConstants.INLINE_PROTOCOL_HOSTSTRING);
  }
}
