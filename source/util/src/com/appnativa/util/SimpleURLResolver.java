/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import com.appnativa.util.Streams.ISO88591Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * A simple URL resolver class
 *
 * @author Don DeCoteau
 */
public class SimpleURLResolver implements iURLResolver {

  /** the base URL */
  protected URL baseURL;

  /** Creates a new instance of SimpleURLResolver */
  public SimpleURLResolver() {}

  /**
   * Creates a new instance of SimpleURLResolver
   *
   * @param base the base URL
   */
  public SimpleURLResolver(URL base) {
    baseURL = base;
  }

  public void setBaseURL(URL baseURL) {
    this.baseURL = baseURL;
  }

  public Object getApplicationContext() {
    return null;
  }

  public URL getBaseURL() {
    return baseURL;
  }

  public URLConnection getConnection(String file) throws IOException {
    URL url = new URL(baseURL, file);

    return url.openConnection();
  }

  public Reader getReader(String file) throws IOException {
    URL           url  = new URL(baseURL, file);
    URLConnection conn = url.openConnection();

    try {
      return new InputStreamReader(conn.getInputStream(), getCharset(conn.getContentType()));
    } catch(UnsupportedEncodingException ex) {}

    return new ISO88591Reader(conn.getInputStream());
  }

  public InputStream getStream(String file) throws IOException {
    URL           url  = new URL(baseURL, file);
    URLConnection conn = url.openConnection();

    return conn.getInputStream();
  }

  public URL getURL(String file) throws MalformedURLException {
    return new URL(baseURL, file);
  }

  private String getCharset(String s) {
    String charSet = "ISO-8859-1";

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
}
