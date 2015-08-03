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

import java.net.URL;
import java.net.URLConnection;

public interface iStreamHandler {
  public URLConnection openConnection(URL aThis) throws IOException;

  public boolean sameFile(URL aThis, URL other);

  public String toExternalForm(URL aThis);

  public String toString(URL aThis);

  public String getFile(URL aThis);

  public String getHost(URL aThis);

  public String getPath(URL aThis);

  public String getProtocol(URL aThis);

  public String getQuery(URL aThis);

  public String getRef(URL aThis);
}
