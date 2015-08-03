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

import com.appnativa.rare.platform.PlatformHelper;

/**
 * Invalid configuration exception
 *
 * @author Don DeCoteau
 */
public class InvalidConfigurationException extends ApplicationException {

  /**
   * Creates a new instance of InvalidConfigurationException
   *
   * @param msg the message for the exception
   */
  public InvalidConfigurationException(String msg) {
    super(msg);
  }

  /**
   * Constructs a new instance
   *
   * @param e the exception that this exception wrapps
   */
  public InvalidConfigurationException(Throwable e) {
    super(e);
  }

  /**
   * Constructs a new instance
   *
   * @param format format specifier
   * @param args arguments
   */
  public InvalidConfigurationException(String format, Object... args) {
    super(PlatformHelper.format(format, args));
  }

  /**
   * Constructs a new instance
   *
   * @param msg the message for the exception
   * @param e the exception that this exception wrapps
   */
  public InvalidConfigurationException(String msg, Throwable e) {
    super(msg, e);
  }
}
