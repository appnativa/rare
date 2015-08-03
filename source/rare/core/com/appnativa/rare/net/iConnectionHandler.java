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

/**
 * A interface that can be used to define a connection handler to be use to
 * create connections.
 *
 * @author Don DeCoteau
 *
 */
public interface iConnectionHandler {

  /**
   *
   * Opens a connection to the object referenced by the URL argument
   *
   * @param url
   *          the URL
   * @param mimeType
   *          a MIME type to use to override the one returned by the server
   *
   * @return the connection handler or null to let the system open the connection
   *
   * @throws IOException
   */
  public iURLConnection openConnection(URL url, String mimeType) throws IOException;
}
