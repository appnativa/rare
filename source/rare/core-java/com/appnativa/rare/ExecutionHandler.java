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

import com.appnativa.util.IdentityArrayList;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutionHandler implements iExecutionHandler {
  static IdentityArrayList<ExecutorService> handlers = new IdentityArrayList<ExecutorService>(2);
  ExecutorService                           executorService;

  public ExecutionHandler(int max) {
    executorService = new ThreadPoolExecutor((max > 5)
            ? 5
            : max, max, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    handlers.add(executorService);
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Callable callable) {
    if (executorService.isShutdown()) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    return new CancelableFutureWrapper(executorService.submit(callable));
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Runnable runnable) {
    if (executorService.isShutdown()) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    return new CancelableFutureWrapper(executorService.submit(runnable));
  }

  @Override
  public iCancelableFuture executeWorkerTask(iWorkerTask task) {
    if (executorService.isShutdown()) {
      return CancelableFutureWrapper.NULL_CANCELABLE_FUTURE;
    }

    return new CancelableFutureWrapper(executorService.submit(new CancelableWorker(task)));
  }

  public static void shutdownHandlers() {
    for (ExecutorService service : handlers) {
      service.shutdownNow();
    }
  }
}
