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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCondition {
  private final Lock      lock      = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  public LockingCondition() {}

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public void awaitSignal() throws InterruptedException {
    condition.await();
  }

  public void awaitSignal(long milliseconds) throws InterruptedException {
    condition.await(milliseconds, TimeUnit.MILLISECONDS);
  }

  public void signal() {
    condition.signal();
  }

  public void signalAll() {
    condition.signalAll();
  }
}
