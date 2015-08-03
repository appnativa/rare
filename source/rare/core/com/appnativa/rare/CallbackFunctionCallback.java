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
 * The is a callback that invokes the specified callback.
 *
 * NOTE: This callback can only be executed once. After execution the
 * passed in values are set to null so that the memory can be reclaimed
 * in automatic reference counting environments. Use where you would use an anonymous class
 *
 * @author Don DeCoteau
 *
 */
public class CallbackFunctionCallback implements iFunctionCallback, Runnable {
  boolean           canceled;
  iFunctionCallback cb;
  boolean           onUIThread;
  Object            returnValue;

  public CallbackFunctionCallback(iFunctionCallback cb, boolean onUIThread) {
    super();
    this.cb         = cb;
    this.onUIThread = onUIThread;
  }

  @Override
  public void finished(boolean canceled, Object returnValue) {
    if (cb == null) {
      return;
    }

    if (onUIThread) {
      if (!Platform.isUIThread()) {
        this.canceled    = canceled;
        this.returnValue = returnValue;
        Platform.invokeLater(this);

        return;
      }
    }

    try {
      cb.finished(canceled, returnValue);
    } finally {
      cb          = null;
      returnValue = null;
    }
  }

  @Override
  public void run() {
    finished(canceled, returnValue);
  }
}
