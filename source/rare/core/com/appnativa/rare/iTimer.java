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

/**
 * An interface representing  a platform independent timer
 * that can schedule tasks to be run once or repeat at a
 * given interval
 *
 * @author Don DeCoteau
 *
 */
public interface iTimer {

  /**
   * Cancels the timer
   */
  void cancel();

  /**
   * Schedules a task to be run once after the specified delay
   *
   * @param task the task
   * @param delay the delay
   *
   * @return a handle to be able to cancel the task
   */
  iCancelable schedule(Runnable task, long delay);

  /**
   * Schedules a task to be run once after the specified delay
   * and the repeat at the given interval
   *
   * @param task the task
   * @param delay the delay
   * @param interval the interval at which to repeat the task
   *
   * @return a handle to be able to cancel the task
   */
  iCancelable schedule(Runnable task, long delay, long interval);
}
