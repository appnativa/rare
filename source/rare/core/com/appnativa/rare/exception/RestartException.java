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

/**
 * An exception that an application can throw that it can catch in
 * a top level error handler an force an application restart.
 * Rare does no do anything with the exception. It is up to the application to
 * handle the restart.
 *
 * @author Don DeCoteau
 */
public class RestartException extends RuntimeException {
  private Object contextObject;
  private int    trackingCount;

  public RestartException() {}

  /**
   * Sets an application specific context object
   *
   * @param object the context object
   */
  public void setContextObject(Object object) {
    this.contextObject = object;
  }

  /**
   * Sets a an application specific tracking count
   *
   * @param count the tracking count
   */
  public void setTrackingCount(int count) {
    this.trackingCount = count;
  }

  /**
   * Gets a an application specific context object
   *
   * @return object the context object
   */
  public Object getContextObject() {
    return contextObject;
  }

  /**
   * Gets a an application specific tracking count
   *
   * @return the tracking count
   */
  public int getTrackingCount() {
    return trackingCount;
  }

  /**
   * Increments and returns the tracking count
   * @return the tracking count
   */
  public int incrementAndGetTrackingCount() {
    return ++trackingCount;
  }
}
