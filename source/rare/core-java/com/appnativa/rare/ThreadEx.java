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

public class ThreadEx extends Thread {
  protected Runnable runnable;

  public ThreadEx() {
    super();
  }

  public ThreadEx(Runnable target, String name) {
    super(target, name);
    this.runnable = target;
  }

  public ThreadEx(Runnable target) {
    super(target);
    this.runnable = target;
  }

  public ThreadEx(String name) {
    super(name);
  }

  public ThreadEx(ThreadGroup group, Runnable target, String name, long stackSize) {
    super(group, target, name, stackSize);
    this.runnable = target;
  }

  public ThreadEx(ThreadGroup group, Runnable target, String name) {
    super(group, target, name);
  }

  public ThreadEx(ThreadGroup group, Runnable target) {
    super(group, target);
    this.runnable = target;
  }

  public ThreadEx(ThreadGroup group, String name) {
    super(group, name);
  }

  @Override
  public void interrupt() {
    if (runnable instanceof iCancelable) {
      try {
        ((iCancelable) runnable).cancel(true);
      } catch(Throwable e) {}
    }

    super.interrupt();
  }
}
