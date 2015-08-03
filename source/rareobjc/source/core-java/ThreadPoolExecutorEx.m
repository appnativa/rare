//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-java/com/appnativa/rare/ThreadPoolExecutorEx.java
//
//  Created by decoteaud on 9/15/14.
//

#include "com/appnativa/rare/ThreadPoolExecutorEx.h"
#include "com/appnativa/util/iCancelable.h"
#include "java/lang/Exception.h"
#include "java/lang/Runnable.h"
#include "java/util/concurrent/BlockingQueue.h"
#include "java/util/concurrent/Callable.h"
#include "java/util/concurrent/RejectedExecutionHandler.h"
#include "java/util/concurrent/RunnableFuture.h"
#include "java/util/concurrent/SynchronousQueue.h"
#include "java/util/concurrent/ThreadFactory.h"
#include "java/util/concurrent/TimeUnit.h"

@implementation RAREThreadPoolExecutorEx

- (id)initWithInt:(int)maximumPoolSize
withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)factory
withJavaUtilConcurrentRejectedExecutionHandler:(id<JavaUtilConcurrentRejectedExecutionHandler>)handler {
  return [super initWithInt:1 withInt:maximumPoolSize withLong:60LL withJavaUtilConcurrentTimeUnitEnum:[JavaUtilConcurrentTimeUnitEnum SECONDS] withJavaUtilConcurrentBlockingQueue:[[JavaUtilConcurrentSynchronousQueue alloc] init] withJavaUtilConcurrentThreadFactory:factory withJavaUtilConcurrentRejectedExecutionHandler:handler];
}

- (id)initWithInt:(int)corePoolSize
          withInt:(int)maximumPoolSize
         withLong:(long long int)keepAliveTime
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit
withJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)workQueue {
  return [super initWithInt:corePoolSize withInt:maximumPoolSize withLong:keepAliveTime withJavaUtilConcurrentTimeUnitEnum:unit withJavaUtilConcurrentBlockingQueue:workQueue];
}

- (id)initWithInt:(int)corePoolSize
          withInt:(int)maximumPoolSize
         withLong:(long long int)keepAliveTime
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit
withJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)workQueue
withJavaUtilConcurrentRejectedExecutionHandler:(id<JavaUtilConcurrentRejectedExecutionHandler>)handler {
  return [super initWithInt:corePoolSize withInt:maximumPoolSize withLong:keepAliveTime withJavaUtilConcurrentTimeUnitEnum:unit withJavaUtilConcurrentBlockingQueue:workQueue withJavaUtilConcurrentRejectedExecutionHandler:handler];
}

- (id)initWithInt:(int)corePoolSize
          withInt:(int)maximumPoolSize
         withLong:(long long int)keepAliveTime
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit
withJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)workQueue
withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory {
  return [super initWithInt:corePoolSize withInt:maximumPoolSize withLong:keepAliveTime withJavaUtilConcurrentTimeUnitEnum:unit withJavaUtilConcurrentBlockingQueue:workQueue withJavaUtilConcurrentThreadFactory:threadFactory];
}

- (id)initWithInt:(int)corePoolSize
          withInt:(int)maximumPoolSize
         withLong:(long long int)keepAliveTime
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit
withJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)workQueue
withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory
withJavaUtilConcurrentRejectedExecutionHandler:(id<JavaUtilConcurrentRejectedExecutionHandler>)handler {
  return [super initWithInt:corePoolSize withInt:maximumPoolSize withLong:keepAliveTime withJavaUtilConcurrentTimeUnitEnum:unit withJavaUtilConcurrentBlockingQueue:workQueue withJavaUtilConcurrentThreadFactory:threadFactory withJavaUtilConcurrentRejectedExecutionHandler:handler];
}

- (id<JavaUtilConcurrentRunnableFuture>)newTaskForWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable {
  return [[RAREThreadPoolExecutorEx_$1 alloc] initWithJavaUtilConcurrentCallable:callable withJavaUtilConcurrentCallable:callable];
}

- (id<JavaUtilConcurrentRunnableFuture>)newTaskForWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                                                withId:(id)value {
  return [[RAREThreadPoolExecutorEx_$2 alloc] initWithJavaLangRunnable:runnable withId:value withJavaLangRunnable:runnable];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "newTaskForWithJavaUtilConcurrentCallable:", NULL, "LJavaUtilConcurrentRunnableFuture", 0x4, NULL },
    { "newTaskForWithJavaLangRunnable:withId:", NULL, "LJavaUtilConcurrentRunnableFuture", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREThreadPoolExecutorEx = { "ThreadPoolExecutorEx", "com.appnativa.rare", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RAREThreadPoolExecutorEx;
}

@end
@implementation RAREThreadPoolExecutorEx_$1

- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning {
  if ([(id) val$callable_ conformsToProtocol: @protocol(RAREUTiCancelable)]) {
    @try {
      [((id<RAREUTiCancelable>) check_protocol_cast(val$callable_, @protocol(RAREUTiCancelable))) cancelWithBoolean:mayInterruptIfRunning];
    }
    @catch (JavaLangException *ignore) {
    }
  }
  return [super cancelWithBoolean:mayInterruptIfRunning];
}

- (id)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)arg$0
          withJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)capture$0 {
  val$callable_ = capture$0;
  return [super initWithJavaUtilConcurrentCallable:arg$0];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cancelWithBoolean:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "val$callable_", NULL, 0x1012, "LJavaUtilConcurrentCallable" },
  };
  static const char *superclass_type_args[] = {"TT"};
  static J2ObjcClassInfo _RAREThreadPoolExecutorEx_$1 = { "$1", "com.appnativa.rare", "ThreadPoolExecutorEx", 0x8000, 1, methods, 1, fields, 1, superclass_type_args};
  return &_RAREThreadPoolExecutorEx_$1;
}

@end
@implementation RAREThreadPoolExecutorEx_$2

- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning {
  if ([(id) val$runnable_ conformsToProtocol: @protocol(RAREUTiCancelable)]) {
    @try {
      [((id<RAREUTiCancelable>) check_protocol_cast(val$runnable_, @protocol(RAREUTiCancelable))) cancelWithBoolean:mayInterruptIfRunning];
    }
    @catch (JavaLangException *ignore) {
    }
  }
  return [super cancelWithBoolean:mayInterruptIfRunning];
}

- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)arg$0
                        withId:(id)arg$1
          withJavaLangRunnable:(id<JavaLangRunnable>)capture$0 {
  val$runnable_ = capture$0;
  return [super initWithJavaLangRunnable:arg$0 withId:arg$1];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cancelWithBoolean:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "val$runnable_", NULL, 0x1012, "LJavaLangRunnable" },
  };
  static const char *superclass_type_args[] = {"TT"};
  static J2ObjcClassInfo _RAREThreadPoolExecutorEx_$2 = { "$2", "com.appnativa.rare", "ThreadPoolExecutorEx", 0x8000, 1, methods, 1, fields, 1, superclass_type_args};
  return &_RAREThreadPoolExecutorEx_$2;
}

@end
