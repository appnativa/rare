//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-java/com/appnativa/rare/exception/FailoverThreadPoolExecutor.java
//
//  Created by decoteaud on 9/15/14.
//

#include "com/appnativa/rare/exception/FailoverThreadPoolExecutor.h"
#include "java/lang/InterruptedException.h"
#include "java/lang/Runnable.h"
#include "java/util/concurrent/AbstractExecutorService.h"
#include "java/util/concurrent/BlockingQueue.h"
#include "java/util/concurrent/Future.h"
#include "java/util/concurrent/FutureTask.h"
#include "java/util/concurrent/LinkedBlockingQueue.h"
#include "java/util/concurrent/ThreadPoolExecutor.h"
#include "java/util/concurrent/TimeUnit.h"

@implementation RAREFailoverThreadPoolExecutor

- (id)initWithInt:(int)max {
  return [super initWithInt:1 withInt:max withLong:60LL withJavaUtilConcurrentTimeUnitEnum:[JavaUtilConcurrentTimeUnitEnum SECONDS] withJavaUtilConcurrentBlockingQueue:[[JavaUtilConcurrentLinkedBlockingQueue alloc] init] withJavaUtilConcurrentRejectedExecutionHandler:[[JavaUtilConcurrentThreadPoolExecutor_CallerRunsPolicy alloc] init]];
}

- (void)rejectedExecutionWithJavaLangRunnable:(id<JavaLangRunnable>)r
     withJavaUtilConcurrentThreadPoolExecutor:(JavaUtilConcurrentThreadPoolExecutor *)executor {
  if ([(id) r isKindOfClass:[JavaUtilConcurrentFutureTask class]]) {
    JavaUtilConcurrentFutureTask *f = (JavaUtilConcurrentFutureTask *) check_class_cast(r, [JavaUtilConcurrentFutureTask class]);
    if ([((JavaUtilConcurrentFutureTask *) nil_chk(f)) isCancelled]) {
      return;
    }
  }
  (void) [self submitWithJavaLangRunnable:[[RAREFailoverThreadPoolExecutor_RetryRunnable alloc] initWithRAREFailoverThreadPoolExecutor:self withJavaUtilConcurrentThreadPoolExecutor:executor withJavaLangRunnable:r]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREFailoverThreadPoolExecutor = { "FailoverThreadPoolExecutor", "com.appnativa.rare.exception", NULL, 0x1, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREFailoverThreadPoolExecutor;
}

@end
@implementation RAREFailoverThreadPoolExecutor_RetryRunnable

- (id)initWithRAREFailoverThreadPoolExecutor:(RAREFailoverThreadPoolExecutor *)outer$
    withJavaUtilConcurrentThreadPoolExecutor:(JavaUtilConcurrentThreadPoolExecutor *)executor
                        withJavaLangRunnable:(id<JavaLangRunnable>)runnable {
  if (self = [super init]) {
    self->executor_ = executor;
    self->runnable_ = runnable;
  }
  return self;
}

- (void)run {
  if ([(id) runnable_ isKindOfClass:[JavaUtilConcurrentFutureTask class]]) {
    JavaUtilConcurrentFutureTask *f = (JavaUtilConcurrentFutureTask *) check_class_cast(runnable_, [JavaUtilConcurrentFutureTask class]);
    if ([((JavaUtilConcurrentFutureTask *) nil_chk(f)) isCancelled]) {
      return;
    }
  }
  @try {
    id<JavaUtilConcurrentBlockingQueue> q = [((JavaUtilConcurrentThreadPoolExecutor *) nil_chk(executor_)) getQueue];
    [((id<JavaUtilConcurrentBlockingQueue>) nil_chk(q)) putWithId:runnable_];
  }
  @catch (JavaLangInterruptedException *ex) {
  }
}

- (void)copyAllFieldsTo:(RAREFailoverThreadPoolExecutor_RetryRunnable *)other {
  [super copyAllFieldsTo:other];
  other->executor_ = executor_;
  other->runnable_ = runnable_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRAREFailoverThreadPoolExecutor:withJavaUtilConcurrentThreadPoolExecutor:withJavaLangRunnable:", NULL, NULL, 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "executor_", NULL, 0x0, "LJavaUtilConcurrentThreadPoolExecutor" },
    { "runnable_", NULL, 0x0, "LJavaLangRunnable" },
  };
  static J2ObjcClassInfo _RAREFailoverThreadPoolExecutor_RetryRunnable = { "RetryRunnable", "com.appnativa.rare.exception", "FailoverThreadPoolExecutor", 0x0, 1, methods, 2, fields, 0, NULL};
  return &_RAREFailoverThreadPoolExecutor_RetryRunnable;
}

@end
