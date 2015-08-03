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

package com.appnativa.rare.platform.android.ui.util;

import android.os.AsyncTask;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.util.IdentityArrayList;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * @author Don DeCoteau
 */
public class AsyncFuture extends AsyncTask<Object, Object, Object> implements Future {
  final static IdentityArrayList<AsyncFuture> _taskList = new IdentityArrayList<AsyncFuture>();
  Callable                                    callable;
  boolean                                     done;
  Throwable                                   exception;
  Runnable                                    runnable;
  iWorkerTask                                 task;

  public AsyncFuture(Callable callable) {
    this.callable = callable;
    _taskList.add(this);
  }

  public AsyncFuture(iWorkerTask task) {
    this.task = task;
    _taskList.add(this);
  }

  public AsyncFuture(Runnable runnable) {
    this.runnable = runnable;
    _taskList.add(this);
  }

  public static void cancelAllPending() {
    try {
      int len = _taskList.size();

      while(len > 0) {
        AsyncFuture f = _taskList.remove(0);

        f.cancel(true);
        len = _taskList.size();
      }
    } catch(Exception e) {}

    _taskList.clear();
  }

  public boolean isDone() {
    return done;
  }

  protected Object doInBackground(Object... paramss) {
    try {
      if (task != null) {
        return task.compute();
      }

      if (runnable != null) {
        runnable.run();

        return null;
      }

      if (callable != null) {
        return callable.call();
      }
    } catch(Throwable e) {
      exception = e;
    }

    return null;
  }

  protected void onCancelled() {
    _taskList.remove(this);
    super.onCancelled();
  }

  protected void onPostExecute(Object result) {
    done = true;
    _taskList.remove(this);
    super.onPostExecute(result);

    if (exception != null) {
      throw ApplicationException.runtimeException(exception);
    }

    if (task != null) {
      task.finish(result);
    }
  }
}
