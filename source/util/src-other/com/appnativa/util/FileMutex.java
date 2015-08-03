/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.channels.FileLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * A class representing a mutex backed by on an OS file
 *
 * @version    2.3
 * @author     Don DeCoteau
 */
public class FileMutex implements Lock {

  /** the file channel */
  java.nio.channels.FileChannel fileChannel;

  /** the file lock */
  java.nio.channels.FileLock fileLock;

  /** the file handle */
  java.io.RandomAccessFile rafFile;

  /** the current lock count */
  protected volatile int lockCount = 0;
  private int            sleepTime = 100;

  /**
   * Creates a new file mutex
   * @param file the file
   */
  public FileMutex(File file) {
    try {
      rafFile     = new java.io.RandomAccessFile(file, "rw");
      fileChannel = rafFile.getChannel();
    } catch(FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Creates a new file mutex
   * @param file the file
   */
  public FileMutex(String file) {
    try {
      rafFile     = new java.io.RandomAccessFile(file, "rw");
      fileChannel = rafFile.getChannel();
    } catch(FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Closes the mutex and associated file
   */
  public synchronized void close() {
    if (fileLock != null) {
      try {
        fileLock.release();
        fileLock = null;
      } catch(Exception ex) {
        fileLock = null;
      }
    }

    if (rafFile != null) {
      try {
        rafFile.getChannel().close();
        rafFile = null;
      } catch(Exception ex) {
        rafFile = null;
      }
    }
  }

  /**
   * Acquires an exclusive lock.  An invocation of this method will
   * block until the lock can be acquired or the invoking thread is interrupted,
   * whichever comes first.
   */
  public void lock() {
    try {
      lock(Long.MAX_VALUE);
    } catch(InterruptedException ex) {}
  }

  /**
   * Acquires an exclusive lock.  An invocation of this method will
   * block until the lock can be acquired or the invoking thread is interrupted,
   * or the specified timeout has been reached, whichever comes first.
   * @return whether the lock was acquired
   * @param timeout the amount of time to wait for the lock
   *
   * @throws InterruptedException
   */
  public boolean lock(long timeout) throws InterruptedException {
    return tryLock(timeout, TimeUnit.MILLISECONDS);
  }

  /**
   * {@inheritDoc}
   *
   * @throws InterruptedException {@inheritDoc}
   */
  public void lockInterruptibly() throws InterruptedException {
    lock();
  }

  /**
   * Creates a read/write lock using the specified file
   *
   * @param file the file to lock on
   *
   * @return a read/write lock using the specified file
   */
  public static ReadWriteLock makeReadWriteLock(File file) {
    return makeReadWriteLock(file, 100);
  }

  /**
   * Creates a read/write lock using the specified file
   *
   * @param file the file to lock on
   * @param pollingInterval the polling interval to use when waiting for access
   *
   * @return a read/write lock using the specified file
   */
  public static ReadWriteLock makeReadWriteLock(File file, int pollingInterval) {
    final FileMutex lock = new FileMutex(file);

    lock.setPollingInterval(pollingInterval);

    return new ReadWriteLockEx(lock);
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  public Condition newCondition() {
    throw new UnsupportedOperationException();
  }

  /**
   * Acquires an shared lock.  An invocation of this method will
   * block until the lock can be acquired or the invoking thread is interrupted,
   * whichever comes first.
   *
   * @throws IOException if an error occurs
   */
  public synchronized void sharedLock() throws IOException {
    if (fileLock != null) {
      lockCount++;
    } else {
      fileLock = fileChannel.lock(0, Long.MAX_VALUE, true);

      if (fileLock != null) {
        lockCount = 1;
      }
    }
  }

  /**
   * Attempts to acquire an exclusive lock. This method does not block.
   * An invocation of this always returns immediately, either having acquired the lock
   * or having failed to do so.
   *
   * @return whether the lock was acquired
   */
  public synchronized boolean tryLock() {
    if (fileLock != null) {
      lockCount++;

      return true;
    }

    try {
      fileLock = fileChannel.tryLock();
    } catch(Exception ex) {
      fileLock = null;
    }

    if (fileLock != null) {
      lockCount = 1;
    }

    return fileLock != null;
  }

  /**
   * Acquires an exclusive lock.  An invocation of this method will
   * block until the lock can be acquired or the invoking thread is interrupted,
   * or the specified timeout has been reached, whichever comes first.
   * @return whether the lock was acquired
   * @param unit the time unit
   * @param timeout the amount of time to wait for the lock
   *
   * @throws InterruptedException {@inheritDoc}
   */
  public synchronized boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
    if (fileLock != null) {
      lockCount++;

      return true;
    }

    if (timeout < 0) {
      try {
        fileLock = fileChannel.lock();
      } catch(IOException ex) {}
    } else if (timeout == 0) {
      try {
        fileLock = fileChannel.tryLock();
      } catch(IOException ex) {}
    } else {
      long                          time = System.currentTimeMillis();
      java.nio.channels.FileChannel fc   = fileChannel;

      if (unit != TimeUnit.MILLISECONDS) {
        timeout = unit.convert(timeout, TimeUnit.MILLISECONDS);
      }

      try {
        while(true) {
          fileLock = fc.tryLock();

          if (fileLock != null) {
            break;
          }

          if (Helper.recursiveTimedWait((int) timeout, time, sleepTime) < 1) {
            break;
          }
        }
      } catch(InterruptedException e) {}
      catch(Exception e) {}
    }

    if (fileLock != null) {
      lockCount = 1;
    }

    return fileLock != null;
  }

  /**
   * Attempts to acquire a shared lock. This method does not block.
   * An invocation of this always returns immediately, either having acquired the lock
   * or having failed to do so.
   *
   * @return whether the lock was acquired
   */
  public synchronized boolean trySharedLock() {
    if (fileLock != null) {
      lockCount++;

      return true;
    }

    try {
      fileLock = fileChannel.tryLock(0, Long.MAX_VALUE, true);
    } catch(Exception ex) {
      fileLock = null;
    }

    if (fileLock != null) {
      lockCount = 1;
    }

    return fileLock != null;
  }

  public synchronized void unlock() {
    if (fileLock != null) {
      lockCount--;

      if (lockCount > 0) {
        return;
      }
    }

    if (fileLock != null) {
      try {
        fileLock.release();
        fileLock = null;
      } catch(Exception ex) {
        fileLock = null;
      }
    }
  }

  /**
   * Sets the polling interval that will be used when waiting to acquire a  lock
   * @param val the value
   */
  public void setPollingInterval(int val) {
    sleepTime = val;
  }

  /**
   * Returns whether the object is currently locked
   * @return  whether the object is currently locked
   */
  public boolean isLocked() {
    FileLock fl = fileLock;

    return (fl != null) && fl.isValid();
  }

  /**
   * Returns whether the object is currently locked in shared mode
   * @return whether the object is currently locked in shared mode
   */
  public boolean isShared() {
    FileLock fl = fileLock;

    return (fl != null) && fl.isShared();
  }

  private static class ReadWriteLockEx implements ReadWriteLock {
    final FileMutex lock;

    ReadWriteLockEx(FileMutex lock) {
      this.lock = lock;
    }

    public Lock readLock() {
      return lock;
    }

    public Lock writeLock() {
      return lock;
    }
  }
}
