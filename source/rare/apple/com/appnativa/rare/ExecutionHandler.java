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

import com.appnativa.rare.platform.CancelableOperation;

import java.util.concurrent.Callable;

public class ExecutionHandler implements iExecutionHandler {
  Object tasksQueue;

  public ExecutionHandler(int threads) {
    tasksQueue = createProxy(threads);
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Callable callable) {
    CancelableOperation op = CancelableOperation.create(callable);

    enqueue(tasksQueue, op);

    return op;
  }

  @Override
  public iCancelableFuture executeWorkerTask(iWorkerTask task) {
    CancelableOperation op = CancelableOperation.create(task);

    enqueue(tasksQueue, op);

    return op;
  }

  @Override
  public iCancelableFuture executeBackgroundTask(Runnable runnable) {
    CancelableOperation op = CancelableOperation.create(runnable);

    enqueue(tasksQueue, op);

    return op;
  }

  private native Object createProxy(int threads)
  /*-[
    NSOperationQueue* queue=[[NSOperationQueue alloc] init];
    [queue setMaxConcurrentOperationCount: threads];
    return queue;
  ]-*/
  ;

  private native void enqueue(Object queue, CancelableOperation cop)
  /*-[
  [((NSOperationQueue*)queue) addOperation:(NSOperation*)cop->proxy_];
  ]-*/
  ;
}
