//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/CancelableOperation.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/iWorkerTask.h"
#include "com/appnativa/rare/platform/CancelableOperation.h"
#include "com/appnativa/util/Helper.h"
#include "java/lang/InterruptedException.h"
#include "java/lang/Runnable.h"
#include "java/lang/System.h"
#include "java/lang/Throwable.h"
#include "java/util/concurrent/Callable.h"
#include "java/util/concurrent/ExecutionException.h"
#include "java/util/concurrent/TimeUnit.h"
#include "java/util/concurrent/TimeoutException.h"

@implementation RARECancelableOperation

- (id)initWithId:(id)nsoperation {
  if (self = [super init]) {
    proxy_ = nsoperation;
  }
  return self;
}

- (void)cancelWithBoolean:(BOOL)caninterrupt {
  NSObject* p=proxy_;
  if(p) {
    canceled_=YES;
    [((NSOperation*)proxy_) cancel];
    proxy_=nil;
  }
}

- (BOOL)isCanceled {
  NSObject* p=proxy_;
  return p==nil ? canceled_ : [((NSOperation*)p) isCancelled];
}

- (BOOL)isDone {
  NSObject* p=proxy_;
  return p==nil ? YES : [((NSOperation*)p) isFinished];
}

- (void)setReturnValueWithId:(id)rvalue {
  returnValue_ = rvalue;
  proxy_ = nil;
}

+ (RARECancelableOperation *)createWithJavaLangRunnable:(id<JavaLangRunnable>)runnable {
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
}

+ (RARECancelableOperation *)createWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable {
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
}

+ (RARECancelableOperation *)createWithRAREiWorkerTask:(id<RAREiWorkerTask>)task {
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
}

- (id)get {
  if (proxy_ != nil) {
    [self waitUntilFinished];
    if (exception_ != nil) {
      @throw [[JavaUtilConcurrentExecutionException alloc] initWithJavaLangThrowable:exception_];
    }
  }
  id o = returnValue_;
  returnValue_ = nil;
  return o;
}

- (id)getWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit {
  if (proxy_ != nil) {
    timeout = [((JavaUtilConcurrentTimeUnitEnum *) nil_chk(unit)) convertWithLong:timeout withJavaUtilConcurrentTimeUnitEnum:[JavaUtilConcurrentTimeUnitEnum MILLISECONDS]];
    long long int starttime = [JavaLangSystem currentTimeMillis];
    while (![self isDone] && ![self isCanceled] && (timeout > 0)) {
      [RAREUTHelper recursiveTimedWaitWithInt:(int) timeout withLong:starttime withInt:200];
    }
    if (exception_ != nil) {
      @throw [[JavaUtilConcurrentExecutionException alloc] initWithJavaLangThrowable:exception_];
    }
  }
  id o = returnValue_;
  returnValue_ = nil;
  return o;
}

- (void)waitUntilFinished {
  NSObject* p=proxy_;
  if(p) {
    [((NSOperation*)p) waitUntilFinished];
  }
}

- (void)copyAllFieldsTo:(RARECancelableOperation *)other {
  [super copyAllFieldsTo:other];
  other->canceled_ = canceled_;
  other->exception_ = exception_;
  other->proxy_ = proxy_;
  other->returnValue_ = returnValue_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cancelWithBoolean:", NULL, "V", 0x101, NULL },
    { "isCanceled", NULL, "Z", 0x101, NULL },
    { "isDone", NULL, "Z", 0x101, NULL },
    { "setReturnValueWithId:", NULL, "V", 0x4, NULL },
    { "createWithJavaLangRunnable:", NULL, "LRARECancelableOperation", 0x109, NULL },
    { "createWithJavaUtilConcurrentCallable:", NULL, "LRARECancelableOperation", 0x109, NULL },
    { "createWithRAREiWorkerTask:", NULL, "LRARECancelableOperation", 0x109, NULL },
    { "get", NULL, "LNSObject", 0x1, "JavaLangInterruptedException;JavaUtilConcurrentExecutionException" },
    { "getWithLong:withJavaUtilConcurrentTimeUnitEnum:", NULL, "LNSObject", 0x1, "JavaLangInterruptedException;JavaUtilConcurrentExecutionException;JavaUtilConcurrentTimeoutException" },
    { "waitUntilFinished", NULL, "V", 0x102, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "exception_", NULL, 0x4, "LJavaLangThrowable" },
    { "returnValue_", NULL, 0x4, "LNSObject" },
    { "proxy_", NULL, 0x4, "LNSObject" },
    { "canceled_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RARECancelableOperation = { "CancelableOperation", "com.appnativa.rare.platform", NULL, 0x1, 10, methods, 4, fields, 0, NULL};
  return &_RARECancelableOperation;
}

@end
