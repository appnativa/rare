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

//Cookie handling via code from java.sun.com Tech Tip
import java.io.IOException;

import java.net.CookieHandler;
import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class CookieManager extends CookieHandler {
  private static final Map<String, List<String>> emptyMap = Collections.unmodifiableMap(new HashMap<String,
                                                              List<String>>());
  private static CookieManager _instance;
  private List<Cookie>         cache = new LinkedList<Cookie>();
  private ReentrantLock        lock  = new ReentrantLock(false);

  public CookieManager() {
    super();
    _instance = this;
  }

  public void clear() {
    lock.lock();

    try {
      cache.clear();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Saves all applicable cookies present in the response
   * headers into cache.
   * @param uri URI source of cookies
   * @param responseHeaders Immutable map from field names to
   * lists of field
   *   values representing the response header fields returned
   *
   * @throws IOException
   */
  @Override
  public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
    List<String> setCookieList = responseHeaders.get("Set-Cookie");

    if (setCookieList != null) {
      lock.lock();

      Cookie existingCookie;

      try {
        for (String item : setCookieList) {
          Cookie cookie = new Cookie(uri, item);
          int    len    = cache.size();

          while(len-- > 0) {
            existingCookie = cache.get(len);

            if ((cookie.getPath().equals(existingCookie.getPath()))
                && (cookie.getName().equals(existingCookie.getName()))) {
              cache.remove(len);

              break;
            }
          }

          cache.add(cookie);
        }
      } finally {
        lock.unlock();
      }
    }
  }

  public void setCookie(String uri, String value) {
    setCookie(URI.create(uri), value);
  }

  public void setCookie(URI uri, String value) {
    Cookie cookie = new Cookie(uri, value);

    lock.lock();

    try {
      int    len = cache.size();
      Cookie existingCookie;

      while(len-- > 0) {
        existingCookie = cache.get(len);

        if ((cookie.getURI().equals(existingCookie.getURI())) && (cookie.getName().equals(existingCookie.getName()))) {
          cache.remove(len);

          break;
        }
      }

      cache.add(cookie);
    } finally {
      lock.unlock();
    }
  }

  /**
   * Gets all the applicable cookies from a cookie cache for
   * the specified uri in the request header.
   *
   * @param uri URI to send cookies to in a request
   * @param requestHeaders Map from request header field names
   * to lists of field values representing the current request
   * headers
   * @return Immutable map, with field name "Cookie" to a list
   * of cookies
   *
   * @throws IOException
   */
  @Override
  public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
    if (cache.size() == 0) {
      return emptyMap;
    }

    StringBuilder cookies = new StringBuilder();

    lock.lock();

    try {
      int    len = cache.size();
      Cookie cookie;

      while(len-- > 0) {
        cookie = cache.get(len);

        if (cookie.hasExpired()) {
          cache.remove(len);
        } else if (cookie.matches(uri)) {
          if (cookies.length() > 0) {
            cookies.append("; ");
          }

          cookie.toString(cookies);
        }
      }
    } finally {
      lock.unlock();
    }

    Map<String, List<String>> cookieMap = new HashMap<String, List<String>>(1);

    if (cookies.length() > 0) {
      List<String> list = Collections.singletonList(cookies.toString());

      cookieMap.put("Cookie", list);
    }

    return cookieMap;
  }

  public List<Cookie> getCookies() {
    return new ArrayList<Cookie>(cache);
  }

  public String getCookies(String url) {
    return getCookies(URI.create(url));
  }

  public String getCookies(URI uri) {
    if (cache.size() == 0) {
      return null;
    }

    StringBuilder cookies = new StringBuilder();

    lock.lock();

    try {
      int    len = cache.size();
      Cookie cookie;

      while(len-- > 0) {
        cookie = cache.get(len);

        if (cookie.hasExpired()) {
          cache.remove(len);
        } else if (cookie.matches(uri)) {
          if (cookies.length() > 0) {
            cookies.append("; ");
          }

          cookie.toString(cookies);
        }
      }
    } finally {
      lock.unlock();
    }

    if (cookies.length() > 0) {
      return cookies.toString();
    }

    return null;
  }

  public static CookieManager getInstance() {
    return _instance;
  }
}
