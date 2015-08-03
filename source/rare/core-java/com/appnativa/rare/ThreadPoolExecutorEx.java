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

import com.appnativa.util.iCancelable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorEx extends ThreadPoolExecutor {
  public ThreadPoolExecutorEx(int maximumPoolSize, ThreadFactory factory, RejectedExecutionHandler handler) {
    super(1, maximumPoolSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), factory, handler);
  }

  public ThreadPoolExecutorEx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public ThreadPoolExecutorEx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
  }

  public ThreadPoolExecutorEx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
  }

  public ThreadPoolExecutorEx(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
  }

  @Override
  protected <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
    return new FutureTask<T>(callable) {
      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        if (callable instanceof iCancelable) {
          try {
            ((iCancelable) callable).cancel(mayInterruptIfRunning);
          } catch(Exception ignore) {}
        }

        return super.cancel(mayInterruptIfRunning);
      }
    };
  }

  @Override
  protected <T> RunnableFuture<T> newTaskFor(final Runnable runnable, T value) {
    return new FutureTask<T>(runnable, value) {
      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        if (runnable instanceof iCancelable) {
          try {
            ((iCancelable) runnable).cancel(mayInterruptIfRunning);
          } catch(Exception ignore) {}
        }

        return super.cancel(mayInterruptIfRunning);
      }
    };
  }
}
