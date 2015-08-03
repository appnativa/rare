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

import java.net.CookieHandler;

import java.util.List;

public class NetHelper extends aNetHelper {
  private NetHelper() {}

  public static List getCookieList() {
    CookieHandler handler = CookieHandler.getDefault();

    if (handler instanceof CookieManager) {
      return ((CookieManager) handler).getCookies();
    }

    throw new UnsupportedOperationException();
  }

  public static String getCookies() {
    List<Cookie>  list  = getCookieList();
    StringBuilder sb    = new StringBuilder();
    boolean       comma = false;

    for (Cookie c : list) {
      if (comma) {
        sb.append(", ");
      } else {
        comma = true;
      }

      if (!c.hasExpired()) {
        sb.append(c.toString());
      }
    }

    return sb.toString();
  }
}
