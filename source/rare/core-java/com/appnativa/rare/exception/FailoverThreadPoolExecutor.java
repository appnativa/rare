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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Don DeCoteau
 */
public class FailoverThreadPoolExecutor extends ThreadPoolExecutor implements RejectedExecutionHandler {
  public FailoverThreadPoolExecutor(int max) {
    super(1, max, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
          new ThreadPoolExecutor.CallerRunsPolicy());
  }

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    if (r instanceof FutureTask) {
      FutureTask f = (FutureTask) r;

      if (f.isCancelled()) {
        return;
      }
    }

    submit(new RetryRunnable(executor, r));
  }

  class RetryRunnable implements Runnable {
    ThreadPoolExecutor executor;
    Runnable           runnable;

    RetryRunnable(ThreadPoolExecutor executor, Runnable runnable) {
      this.executor = executor;
      this.runnable = runnable;
    }

    @Override
    public void run() {
      if (runnable instanceof FutureTask) {
        FutureTask f = (FutureTask) runnable;

        if (f.isCancelled()) {
          return;
        }
      }

      try {
        BlockingQueue<Runnable> q = executor.getQueue();

        q.put(runnable);
      } catch(InterruptedException ex) {}
    }
  }
}
