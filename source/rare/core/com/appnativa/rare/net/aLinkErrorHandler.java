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

import com.appnativa.rare.net.ActionLink.iErrorHandler;

/**
 * An abstract class that should the the basis for all link error handlers. If
 * new methods are added to the interface the methods in this class will be
 * updated
 *
 * @author Don DeCoteau
 *
 */
public class aLinkErrorHandler implements iErrorHandler {
  public aLinkErrorHandler() {}

  @Override
  public iURLConnection getConnectionChange(ActionLink link, Exception ex, iURLConnection conn) {
    return conn;
  }

  @Override
  public Exception getExceptionChange(ActionLink link, Exception ex) {
    return ex;
  }

  @Override
  public Action handleError(ActionLink link, Exception ex, iURLConnection conn) {
    return Action.ERROR;
  }
}
