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

package com.appnativa.rare.platform;

import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.util.Helper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CancelableOperation implements iCancelableFuture {
  protected Throwable exception;
  protected Object    returnValue;
  protected Object    proxy;
  boolean             canceled;

  public CancelableOperation(Object nsoperation) {
    proxy = nsoperation;
  }

  @Override
  public native void cancel(boolean caninterrupt)
  /*-[
   NSObject* p=proxy_;
   if(p) {
          canceled_=YES;
          [((NSOperation*)proxy_) cancel];
          proxy_=nil;
   }
  ]-*/
  ;

  @Override
  public native boolean isCanceled()
  /*-[
          NSObject* p=proxy_;
          return p==nil ? canceled_ : [((NSOperation*)p) isCancelled];
  ]-*/
  ;

  @Override
  public native boolean isDone()
  /*-[
          NSObject* p=proxy_;
          return p==nil ? YES : [((NSOperation*)p) isFinished];
  ]-*/
  ;

  protected void setReturnValue(Object rvalue) {
    returnValue = rvalue;
    proxy       = null;
  }

  public native static CancelableOperation create(Runnable runnable)
  /*-[
NSBlockOperation* op = [[NSBlockOperation alloc] init];
RARECancelableOperation* operation=[[RARECancelableOperation alloc]initWithId:op];
__weak NSBlockOperation* wop=op;
__weak RARECancelableOperation* woperation=operation;
[op addExecutionBlock:^{
if (![wop isCancelled]) {
  @try {
    [runnable run];
  }
  @catch (JavaLangException *exception) {
          RARECancelableOperation* o=woperation;
          if(o) {
            o->exception_=exception;
          }
  }
  @finally {
    [woperation setReturnValueWithId:nil];
  }
};
}];
return operation;
  ]-*/
  ;

  public native static CancelableOperation create(Callable callable)
  /*-[
NSBlockOperation* op = [[NSBlockOperation alloc] init];
__block RARECancelableOperation* operation=[[RARECancelableOperation alloc]initWithId:op];
__weak NSBlockOperation* wop=op;
__weak RARECancelableOperation* woperation=operation;
[op addExecutionBlock:^{
if (![wop isCancelled]) {
  @try {
    [operation setReturnValueWithId:[callable call]];
  }
  @catch (JavaLangException *exception) {
          RARECancelableOperation* o=woperation;
          if(o) {
            o->exception_=exception;
          }
  }
}
}];
return operation;
  ]-*/
  ;

  public native static CancelableOperation create(iWorkerTask task)
  /*-[
NSBlockOperation* op = [[NSBlockOperation alloc] init];
__block RARECancelableOperation* operation=[[RARECancelableOperation alloc]initWithId:op];
__weak NSBlockOperation* wop=op;
__weak RARECancelableOperation* woperation=operation;
[op addExecutionBlock:^{
if (![wop isCancelled]) {
  @try {
    id result=[task compute];
    [operation setReturnValueWithId:result];
    if (![wop isCancelled]) {
      dispatch_async(dispatch_get_main_queue(), ^{

        [task finishWithId:result];
      });
    }
  }
  @catch (JavaLangException *exception) {
          RARECancelableOperation* o=woperation;
          if(o) {
            o->exception_=exception;
          }
  }
  @finally {
    operation=nil;
  }
}
}];
return operation;
  ]-*/
  ;

  @Override
  public Object get() throws InterruptedException, ExecutionException {
    if (proxy != null) {
      waitUntilFinished();

      if (exception != null) {
        throw new ExecutionException(exception);
      }
    }

    Object o = returnValue;

    returnValue = null;

    return o;
  }

  @Override
  public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    if (proxy != null) {
      timeout = unit.convert(timeout, TimeUnit.MILLISECONDS);

      long starttime = System.currentTimeMillis();

      while(!isDone() &&!isCanceled() && (timeout > 0)) {
        Helper.recursiveTimedWait((int) timeout, starttime, 200);
      }

      if (exception != null) {
        throw new ExecutionException(exception);
      }
    }

    Object o = returnValue;

    returnValue = null;

    return o;
  }

  private native void waitUntilFinished()
  /*-[
    NSObject* p=proxy_;
    if(p) {
                  [((NSOperation*)p) waitUntilFinished];
          }
  ]-*/
  ;
}
