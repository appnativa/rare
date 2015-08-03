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

import com.appnativa.rare.util.MIMEMap;
import com.appnativa.util.Streams;
import com.appnativa.util.io.BufferedReaderEx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * A URLConnection for an arbitrary input stream
 *
 * @author Don DeCoteau
 */
public class StreamURLConnection extends URLConnection implements iURLConnection {
  String         charSet       = "ISO-8859-1";
  int            contentLength = -1;
  Map            headers;
  InputStream    input;
  private String defaultCharset = "ISO-8859-1";
  private String contentType;

  public StreamURLConnection(URL url, InputStream input, int length) {
    super(url);
    this.input         = input;
    this.contentLength = length;
  }

  public StreamURLConnection(URL url, InputStream input, Map headers) {
    super(url);
    this.input   = input;
    this.headers = headers;
  }

  @Override
  public void close() {
    try {
      input.close();
    } catch(IOException ex) {}
  }

  @Override
  public void connect() throws IOException {}

  @Override
  public void dispose() {
    close();
  }

  @Override
  public boolean exist() {
    return true;
  }

  @Override
  public void open() throws IOException {
    connect();
  }

  @Override
  public void setCharset(String charset) {
    this.charSet = charset;
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
    if (headers == null) {
      headers = new HashMap();
    }

    headers.put(name, value);
  }

  public void setHeaders(HashMap headers) {
    this.headers = headers;
  }

  @Override
  public String getCharset() throws IOException {
    return (charSet != null)
           ? charSet
           : defaultCharset;
  }

  @Override
  public Object getConnectionObject() {
    return this;
  }

  @Override
  public Class getConnectionObjectClass() {
    return StreamURLConnection.class;
  }

  @Override
  public String getContentAsString() throws IOException {
    return Streams.readerToString(getReader());
  }

  @Override
  public int getContentLength() {
    return (contentLength > -1)
           ? contentLength
           : super.getContentLength();
  }

  @Override
  public String getContentType() {
    if (headers == null) {
      if ((contentType == null) && (url != null)) {
        contentType = MIMEMap.typeFromFile(url.getFile());
      }

      return contentType;
    }

    return super.getContentType();
  }

  @Override
  public String getHeaderField(String name) {
    return (String) ((headers == null)
                     ? null
                     : headers.get(name));
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return input;
  }

  @Override
  public Reader getReader() throws IOException {
    if (contentLength > 0) {
      return new BufferedReaderEx(new InputStreamReader(input), contentLength);
    }

    return new BufferedReaderEx(new InputStreamReader(input));
  }

  @Override
  public int getResponseCode() throws IOException {
    return 200;
  }
}
