package com.appnativa.rare.platform;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.appnativa.rare.iCancelableFuture;
import com.appnativa.rare.iWorkerTask;
import com.appnativa.util.Helper;

public class CancelableOperation implements iCancelableFuture{
	protected Throwable exception;
	protected Object returnValue;
	protected Object proxy;
	public CancelableOperation(Object nsoperation) {
		proxy=nsoperation;
	}
	@Override
  public native void cancel(boolean caninterrupt)
	/*-[
	return [((NSOperation*)proxy_) cancel];
]-*/;

	@Override
  public native boolean isCanceled() 
	/*-[
		return [((NSOperation*)proxy_) isCancelled];
	]-*/;

	@Override
  public native boolean isDone()
	/*-[
		return [((NSOperation*)proxy_) isFinished];
	]-*/;

	public native static CancelableOperation create(Runnable runnable) 
	/*-[
	  NSBlockOperation* op = [[NSBlockOperation alloc] init];
	  SPARCancelableOperation* operation=[[SPARCancelableOperation alloc]initWithId:op];
	  __weak NSBlockOperation* wop=op;
	  __weak SPARCancelableOperation* woperation=operation;
	  [op addExecutionBlock:^{
	    if (![wop isCancelled]) {
	      @try {
	        [runnable run];
	      }
	      @catch (JavaLangException *exception) {
	        woperation.exception=exception;
	      }
	    };
	  }];
	  return operation;
	]-*/;

	public native static CancelableOperation create(Callable callable)
	/*-[
	  NSBlockOperation* op = [[NSBlockOperation alloc] init];
	  SPARCancelableOperation* operation=[[SPARCancelableOperation alloc]initWithId:op];
	  __weak NSBlockOperation* wop=op;
	  __weak SPARCancelableOperation* woperation=operation;
	  [op addExecutionBlock:^{
	    if (![wop isCancelled]) {
	      @try {
	        operation.returnValue=[callable call];
	      }
	      @catch (JavaLangException *exception) {
	        woperation.exception=exception;
	      }
	    }
	  }];
	  return operation;
	]-*/;
	
	public native static CancelableOperation create(iWorkerTask task)
	/*-[
	  NSBlockOperation* op = [[NSBlockOperation alloc] init];
	  SPARCancelableOperation* operation=[[SPARCancelableOperation alloc]initWithId:op];
	  __weak NSBlockOperation* wop=op;
	  __weak SPARCancelableOperation* woperation=operation;
	  [op addExecutionBlock:^{
	    if (![wop isCancelled]) {
	      @try {
	        id result=[task compute];
	        if (![wop isCancelled]) {
	          operation.returnValue=result;
	          dispatch_async(dispatch_get_main_queue(), ^{
	            
	            [task finishWithId:result];
	          });
	        }
	      }
	      @catch (JavaLangException *exception) {
	        woperation.exception=exception;
	      }
	    }
	  }];
	  return operation;
	]-*/;

	@Override
  public Object get() throws InterruptedException, ExecutionException {
		waitUntilFinished();
		if (exception != null) {
			throw new ExecutionException(exception);
		}
		return returnValue;
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {

		timeout = unit.convert(timeout, TimeUnit.MILLISECONDS);
		long starttime = System.currentTimeMillis();
		while (!isDone() && !isCanceled() && timeout > 0) {
			Helper.recursiveTimedWait((int) timeout, starttime, 200);
		}
		if (exception != null) {
			throw new ExecutionException(exception);
		}
		return returnValue;
	}
	private native void waitUntilFinished()
	/*-[
		[((NSOperation*)proxy_) waitUntilFinished];
	]-*/;
}
