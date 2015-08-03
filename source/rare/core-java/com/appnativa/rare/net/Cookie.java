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

import com.appnativa.rare.Platform;
import com.appnativa.util.SimpleDateFormatEx;

import java.net.URI;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Cookie {
  private static DateFormat expiresFormat1 = new SimpleDateFormatEx("E, dd MMM yyyy k:m:s 'GMT'", Locale.US);
  private static DateFormat expiresFormat2 = new SimpleDateFormatEx("E, dd-MMM-yyyy k:m:s 'GMT'", Locale.US);
  private static DateFormat expiresFormat3 = new SimpleDateFormatEx("E, dd MMM yyyy k:m:s Z", Locale.US);

  static {
    expiresFormat1.setTimeZone(TimeZone.getTimeZone("GMT"));
    expiresFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  Date   expires;
  String header;
  String name;
  String path;
  URI    uri;
  String value;

  /**
   * Construct a cookie from the URI and header fields
   *
   * @param uri URI for cookie
   * @param header Set of attributes in header
   */
  public Cookie(URI uri, String header) {
    String attributes[] = header.split(";");
    String nameValue    = attributes[0].trim();

    this.header = header;
    this.uri    = uri;
    this.name   = nameValue.substring(0, nameValue.indexOf('='));
    this.value  = nameValue.substring(nameValue.indexOf('=') + 1);
    this.path   = "/";

    for (int i = 1; i < attributes.length; i++) {
      nameValue = attributes[i].trim();

      int equals = nameValue.indexOf('=');

      if (equals == -1) {
        continue;
      }

      String name  = nameValue.substring(0, equals);
      String value = nameValue.substring(equals + 1);

      if (name.equalsIgnoreCase("domain")) {
        String uriDomain = uri.getHost();

        if (!uriDomain.equals(value)) {
          if (!value.startsWith(".")) {
            value = "." + value;
          }

          uriDomain = uriDomain.substring(uriDomain.indexOf('.'));

          if (!uriDomain.equals(value)) {
            throw new IllegalArgumentException(Platform.getResourceAsString("Rare.runtime.text.cookieForeignError"));
          }
        }
      } else if (name.equalsIgnoreCase("path")) {
        if (value.length() == 0) {
          value = "/";
        }

        if (!value.endsWith("/")) {
          value = value + "/";
        }

        this.path = value;
      } else if (name.equalsIgnoreCase("expires")) {
        parseDate(value);
      }
    }
  }

  /**
   * Check if cookie isn't expired and if URI matches,
   * should cookie be included in response.
   *
   * @param uri URI to check against
   * @return true if match, false otherwise
   */
  public boolean matches(URI uri) {
    String pathToMatchWith = uri.getPath();

    if ((path == null) || (pathToMatchWith == null)) {
      return false;
    }

    return pathToMatchWith.startsWith(path);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(name);

    result.append("=");
    result.append(value);

    return result.toString();
  }

  public StringBuilder toString(StringBuilder sb) {
    return sb.append(name).append("=").append(value);
  }

  public String getHeader() {
    return header;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public URI getURI() {
    return uri;
  }

  public String getValue() {
    return value;
  }

  /**
   * Returns a string representing the current session cookies
   *
   * @return  a string representing the current session cookies
   */
  public boolean hasExpired() {
    if (expires == null) {
      return false;
    }

    Date now = new Date();

    return now.after(expires);
  }

  private synchronized void parseDate(String value) {
    try {
      this.expires = expiresFormat1.parse(value);
    } catch(ParseException e) {
      try {
        this.expires = expiresFormat2.parse(value);
      } catch(ParseException e2) {
        try {
          this.expires = expiresFormat3.parse(value);
        } catch(ParseException e3) {
          throw new IllegalArgumentException(Platform.getResourceAsString("Rare.runtime.text.cookieBadDateFormatError")
                                             + value);
        }
      }
    }
  }
//Cookie handling via code from java.sun.com Tech Tip
}
