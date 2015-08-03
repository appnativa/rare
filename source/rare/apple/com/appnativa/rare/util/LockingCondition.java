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

package com.appnativa.rare.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCondition {
  Object                  proxy;
  private AtomicInteger   waiters   = new AtomicInteger(0);
  private final Lock      lock      = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  public LockingCondition() {
    proxy = createProxy();
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public void awaitSignal() throws InterruptedException {
    waiters.incrementAndGet();

    try {
      awaitSignalEx();
    } finally {
      waiters.decrementAndGet();
    }
  }

  protected native void awaitSignalEx() throws InterruptedException
  /*-[
          [((NSCondition*)proxy_) wait];
  ]-*/
  ;

  public void awaitSignal(long milliseconds) throws InterruptedException {
    waiters.incrementAndGet();

    try {
      awaitSignalEx(milliseconds);
    } finally {
      waiters.decrementAndGet();
    }
  }

  protected native void awaitSignalEx(long milliseconds) throws InterruptedException
  /*-[
          NSDate* date=[[NSDate alloc] initWithTimeIntervalSinceNow: (milliseconds/1000)];
          [((NSCondition*)proxy_) waitUntilDate: date];
  ]-*/
  ;

  public void signalAll() {
    signal();

    int count = waiters.get();

    count--;

    while(count > 0) {
      signal();
      count--;
    }

    waiters.set(0);;
  }

  public native void signal()
  /*-[
          [((NSCondition*)proxy_) signal];
  ]-*/
  ;

  private static native Object createProxy()
  /*-[
  return [NSCondition new];
  ]-*/
  ;
}
