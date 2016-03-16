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

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.util.iCancelable;

/**
 *
 * @author Don DeCoteau
 */
public class CancelableWorker implements iCancelable, Runnable {
  volatile boolean canceled;
  volatile boolean done;
  iWorkerTask      task;
  Object   result = null;
  Throwable exception;

  public CancelableWorker(iWorkerTask task) {
    this.task = task;
  }

  @Override
  public void cancel(boolean canInterrupt) {
    canceled = true;
    task.cancel(canInterrupt);
    task   = null;
    result = null;
  }

  @Override
  public void run() {
    if (!canceled && !done) {
      try {
        result = task.compute();
      }catch(Throwable e) {
        exception=e;
      } finally {
        done = true;
      }
    }

    if (!canceled && done && (task != null)) {
      if (Platform.isUIThread()) {
        if(exception!=null) {
          throw ApplicationException.runtimeException(exception);
        }
        task.finish(result);
        task   = null;
        result = null;
      } else {
        Platform.invokeLater(this);
      }
    } else {
      task   = null;
      result = null;
    }
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isDone() {
    return done || canceled;
  }
}
