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

package com.appnativa.rare;

/**
 * The is a runnable that invokes the specified callback.
 *
 * NOTE: The runnable can only be executed once. After execution the
 * passed in values are set to null so that the memory can be reclaimed
 * in automatic reference counting environments. Use where you would use an anonymous class
 *
 * @author Don DeCoteau
 *
 */
public class CallbackFunctionRunnable implements Runnable {
  boolean           canceled;
  iFunctionCallback cb;
  Object            returnValue;

  public CallbackFunctionRunnable(iFunctionCallback cb, boolean canceled, Object returnValue) {
    super();
    this.cb          = cb;
    this.canceled    = canceled;
    this.returnValue = returnValue;
  }

  @Override
  public void run() {
    if (cb != null) {
      try {
        cb.finished(canceled, returnValue);
      } finally {
        cb = null;
      }
    }
  }
}
