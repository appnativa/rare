//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/CancelableFutureWrapper.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/CancelableFutureWrapper.h"
#include "com/appnativa/rare/iCancelableFuture.h"
#include "java/lang/InterruptedException.h"
#include "java/util/concurrent/ExecutionException.h"
#include "java/util/concurrent/Future.h"
#include "java/util/concurrent/TimeUnit.h"
#include "java/util/concurrent/TimeoutException.h"

@implementation RARECancelableFutureWrapper

static id<RAREiCancelableFuture> RARECancelableFutureWrapper_NULL_CANCELABLE_FUTURE_;

+ (id<RAREiCancelableFuture>)NULL_CANCELABLE_FUTURE {
  return RARECancelableFutureWrapper_NULL_CANCELABLE_FUTURE_;
}

- (id)initWithJavaUtilConcurrentFuture:(id<JavaUtilConcurrentFuture>)future {
  if (self = [super init]) {
    self->future_ = future;
  }
  return self;
}

- (void)cancelWithBoolean:(BOOL)mayInterruptIfRunning {
  if (future_ != nil) {
    [future_ cancelWithBoolean:mayInterruptIfRunning];
  }
}

- (id)get {
  return (future_ == nil) ? nil : [future_ get];
}

- (id)getWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit {
  return (future_ == nil) ? nil : [future_ getWithLong:timeout withJavaUtilConcurrentTimeUnitEnum:unit];
}

- (BOOL)isCanceled {
  return (future_ == nil) ? YES : [future_ isCancelled];
}

- (BOOL)isDone {
  return (future_ == nil) ? YES : [future_ isDone];
}

+ (void)initialize {
  if (self == [RARECancelableFutureWrapper class]) {
    RARECancelableFutureWrapper_NULL_CANCELABLE_FUTURE_ = [[RARECancelableFutureWrapper alloc] initWithJavaUtilConcurrentFuture:nil];
  }
}

- (void)copyAllFieldsTo:(RARECancelableFutureWrapper *)other {
  [super copyAllFieldsTo:other];
  other->future_ = future_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "get", NULL, "LNSObject", 0x1, "JavaLangInterruptedException;JavaUtilConcurrentExecutionException" },
    { "getWithLong:withJavaUtilConcurrentTimeUnitEnum:", NULL, "LNSObject", 0x1, "JavaLangInterruptedException;JavaUtilConcurrentExecutionException;JavaUtilConcurrentTimeoutException" },
    { "isCanceled", NULL, "Z", 0x1, NULL },
    { "isDone", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "NULL_CANCELABLE_FUTURE_", NULL, 0x19, "LRAREiCancelableFuture" },
    { "future_", NULL, 0x4, "LJavaUtilConcurrentFuture" },
  };
  static J2ObjcClassInfo _RARECancelableFutureWrapper = { "CancelableFutureWrapper", "com.appnativa.rare", NULL, 0x1, 4, methods, 2, fields, 0, NULL};
  return &_RARECancelableFutureWrapper;
}

@end
