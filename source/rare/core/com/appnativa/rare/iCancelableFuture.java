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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface iCancelableFuture<T> extends iCancelable {

  /**
   * Waits if necessary for the computation to complete, and then retrieves its result
   * <p>
   * Only the first invocation is guaranteed to return the result. Subsequent calls
   * may return a null value.
   * </p>
   *
   * @return the computed result
   */
  T get() throws InterruptedException, ExecutionException;

  /**
   * Waits if necessary for at most the given time for the computation to complete,
   * and then retrieves its result, if available.
   * <p>
   * Only the first invocation is guaranteed to return the result. Subsequent calls
   * may return a null value.
   * </p>
   * @param timeout the maximum time to wait
   * @param unit the time unit of the timeout argument
   * @return the computed result
   */
  T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}
