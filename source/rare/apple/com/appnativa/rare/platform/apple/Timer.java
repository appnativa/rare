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

package com.appnativa.rare.platform.apple;

import com.appnativa.rare.iTimer;
import com.appnativa.util.iCancelable;

/*-[
 #import "RAREAPTimer.h"
 ]-*/
public class Timer implements iTimer, iCancelable {
  boolean         canceled;
  String          name;
  Object          proxy;
  private boolean useMainQueue;

  public Timer() {
    this(null);
  }

  public Timer(String name) {
    this.name = name;
  }

  @Override
  public void cancel() {
    cancel(true);
  }

  @Override
  public void cancel(boolean canInterrupt) {
    if (proxy != null) {
      cancelEx();
      proxy = null;
    }

    canceled = true;
  }

  @Override
  public iCancelable schedule(Runnable task, long delay) {
    return schedule(task, delay, 0);
  }

  @Override
  public native iCancelable schedule(Runnable task, long delay, long interval)    /*-[
    if(!proxy_) {
      proxy_=[[RAREAPTimer alloc] initWithMainQueue: useMainQueue_];
    }
     [((RAREAPTimer*) proxy_) scheduleWithDelay:delay interval: interval runnable: task];
     return self;
   ]-*/
  ;

  @Override
  public String toString() {
    if (name == null) {
      return "Timer:" + String.valueOf(System.identityHashCode(this));
    }

    return "Timer:" + name;
  }

  public void setUseMainQuue(boolean useMainQuue) {
    this.useMainQueue = useMainQuue;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isDone() {
    if (canceled) {
      return true;
    }

    if (proxy != null) {
      return isDoneEx();
    }

    return false;
  }

  protected native void cancelEx()    /*-[
             [((RAREAPTimer*) proxy_) cancel];
     ]-*/
  ;

  protected native boolean isDoneEx()    /*-[
             return [((RAREAPTimer*) proxy_) isDone ];
     ]-*/
  ;
}
