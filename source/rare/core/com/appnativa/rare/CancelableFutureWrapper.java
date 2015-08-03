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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CancelableFutureWrapper implements iCancelableFuture {
  public static final iCancelableFuture NULL_CANCELABLE_FUTURE = new CancelableFutureWrapper(null);
  protected Future                      future;

  public CancelableFutureWrapper(Future future) {
    this.future = future;
  }

  @Override
  public void cancel(boolean mayInterruptIfRunning) {
    if (future != null) {
      future.cancel(mayInterruptIfRunning);
    }
  }

  @Override
  public Object get() throws InterruptedException, ExecutionException {
    return (future == null)
           ? null
           : future.get();
  }

  @Override
  public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return (future == null)
           ? null
           : future.get(timeout, unit);
  }

  @Override
  public boolean isCanceled() {
    return (future == null)
           ? true
           : future.isCancelled();
  }

  @Override
  public boolean isDone() {
    return (future == null)
           ? true
           : future.isDone();
  }
}
