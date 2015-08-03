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

package com.appnativa.rare.exception;

public class BrowserException extends RuntimeException {
  private int    code;
  private String location;

  public BrowserException(int code) {
    super();
    this.code = code;
  }

  public BrowserException(String detailMessage) {
    super(detailMessage);
  }

  public BrowserException(Throwable throwable) {
    super(throwable);
  }

  public BrowserException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public BrowserException(int code, String location, String detailMessage) {
    super(detailMessage);
    this.code     = code;
    this.location = location;
  }

  public int getCode() {
    return code;
  }

  public String getLocation() {
    return location;
  }
}
