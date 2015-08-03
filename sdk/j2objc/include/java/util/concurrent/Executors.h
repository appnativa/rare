//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/Executors.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentExecutors_H_
#define _JavaUtilConcurrentExecutors_H_

@class JavaLangClassLoader;
@class JavaLangThread;
@class JavaLangThreadGroup;
@class JavaLangVoid;
@class JavaSecurityAccessControlContext;
@class JavaUtilConcurrentAtomicAtomicInteger;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentExecutorService;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentScheduledFuture;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/lang/Runnable.h"
#include "java/security/PrivilegedAction.h"
#include "java/security/PrivilegedExceptionAction.h"
#include "java/util/concurrent/AbstractExecutorService.h"
#include "java/util/concurrent/Callable.h"
#include "java/util/concurrent/ScheduledExecutorService.h"
#include "java/util/concurrent/ThreadFactory.h"

@interface JavaUtilConcurrentExecutors : NSObject {
}

+ (id<JavaUtilConcurrentExecutorService>)newFixedThreadPoolWithInt:(int)nThreads OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)newFixedThreadPoolWithInt:(int)nThreads
                               withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)newSingleThreadExecutor OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)newSingleThreadExecutorWithJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)newCachedThreadPool OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)newCachedThreadPoolWithJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentScheduledExecutorService>)newSingleThreadScheduledExecutor OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentScheduledExecutorService>)newSingleThreadScheduledExecutorWithJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentScheduledExecutorService>)newScheduledThreadPoolWithInt:(int)corePoolSize OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentScheduledExecutorService>)newScheduledThreadPoolWithInt:(int)corePoolSize
                                            withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilConcurrentExecutorService>)unconfigurableExecutorServiceWithJavaUtilConcurrentExecutorService:(id<JavaUtilConcurrentExecutorService>)executor;
+ (id<JavaUtilConcurrentScheduledExecutorService>)unconfigurableScheduledExecutorServiceWithJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor;
+ (id<JavaUtilConcurrentThreadFactory>)defaultThreadFactory;
+ (id<JavaUtilConcurrentThreadFactory>)privilegedThreadFactory;
+ (id<JavaUtilConcurrentCallable>)callableWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                                        withId:(id)result;
+ (id<JavaUtilConcurrentCallable>)callableWithJavaLangRunnable:(id<JavaLangRunnable>)task;
+ (id<JavaUtilConcurrentCallable>)callableWithJavaSecurityPrivilegedAction:(id<JavaSecurityPrivilegedAction>)action;
+ (id<JavaUtilConcurrentCallable>)callableWithJavaSecurityPrivilegedExceptionAction:(id<JavaSecurityPrivilegedExceptionAction>)action;
+ (id<JavaUtilConcurrentCallable>)privilegedCallableWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;
+ (id<JavaUtilConcurrentCallable>)privilegedCallableUsingCurrentClassLoaderWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;
- (id)init;
@end

@interface JavaUtilConcurrentExecutors_RunnableAdapter : NSObject < JavaUtilConcurrentCallable > {
 @public
  id<JavaLangRunnable> task_;
  id result_;
}

- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)task
                        withId:(id)result;
- (id)call;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_RunnableAdapter *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_RunnableAdapter, task_, id<JavaLangRunnable>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_RunnableAdapter, result_, id)

@interface JavaUtilConcurrentExecutors_PrivilegedCallable : NSObject < JavaUtilConcurrentCallable > {
 @public
  id<JavaUtilConcurrentCallable> task_;
  JavaSecurityAccessControlContext *acc_;
}

- (id)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)task;
- (id)call;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_PrivilegedCallable *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallable, task_, id<JavaUtilConcurrentCallable>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallable, acc_, JavaSecurityAccessControlContext *)

@interface JavaUtilConcurrentExecutors_PrivilegedCallable_$1 : NSObject < JavaSecurityPrivilegedExceptionAction > {
 @public
  JavaUtilConcurrentExecutors_PrivilegedCallable *this$0_;
}

- (id)run;
- (id)initWithJavaUtilConcurrentExecutors_PrivilegedCallable:(JavaUtilConcurrentExecutors_PrivilegedCallable *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallable_$1, this$0_, JavaUtilConcurrentExecutors_PrivilegedCallable *)

@interface JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader : NSObject < JavaUtilConcurrentCallable > {
 @public
  id<JavaUtilConcurrentCallable> task_;
  JavaSecurityAccessControlContext *acc_;
  JavaLangClassLoader *ccl_;
}

- (id)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)task;
- (id)call;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader, task_, id<JavaUtilConcurrentCallable>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader, acc_, JavaSecurityAccessControlContext *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader, ccl_, JavaLangClassLoader *)

@interface JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader_$1 : NSObject < JavaSecurityPrivilegedExceptionAction > {
 @public
  JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader *this$0_;
}

- (id)run;
- (id)initWithJavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader:(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader_$1, this$0_, JavaUtilConcurrentExecutors_PrivilegedCallableUsingCurrentClassLoader *)

@interface JavaUtilConcurrentExecutors_DefaultThreadFactory : NSObject < JavaUtilConcurrentThreadFactory > {
 @public
  JavaLangThreadGroup *group_;
  JavaUtilConcurrentAtomicAtomicInteger *threadNumber_;
  NSString *namePrefix_;
}

+ (JavaUtilConcurrentAtomicAtomicInteger *)poolNumber;
- (id)init;
- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_DefaultThreadFactory *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_DefaultThreadFactory, group_, JavaLangThreadGroup *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_DefaultThreadFactory, threadNumber_, JavaUtilConcurrentAtomicAtomicInteger *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_DefaultThreadFactory, namePrefix_, NSString *)

@interface JavaUtilConcurrentExecutors_PrivilegedThreadFactory : JavaUtilConcurrentExecutors_DefaultThreadFactory {
 @public
  JavaSecurityAccessControlContext *acc_;
  JavaLangClassLoader *ccl_;
}

- (id)init;
- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)r OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_PrivilegedThreadFactory *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedThreadFactory, acc_, JavaSecurityAccessControlContext *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedThreadFactory, ccl_, JavaLangClassLoader *)

@interface JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1 : NSObject < JavaLangRunnable > {
 @public
  JavaUtilConcurrentExecutors_PrivilegedThreadFactory *this$0_;
  id<JavaLangRunnable> val$r_;
}

- (void)run;
- (id)initWithJavaUtilConcurrentExecutors_PrivilegedThreadFactory:(JavaUtilConcurrentExecutors_PrivilegedThreadFactory *)outer$
                                             withJavaLangRunnable:(id<JavaLangRunnable>)capture$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1, this$0_, JavaUtilConcurrentExecutors_PrivilegedThreadFactory *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1, val$r_, id<JavaLangRunnable>)

@interface JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1_$1 : NSObject < JavaSecurityPrivilegedAction > {
 @public
  JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1 *this$0_;
}

- (id)run;
- (id)initWithJavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1:(JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1 *)outer$;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1_$1, this$0_, JavaUtilConcurrentExecutors_PrivilegedThreadFactory_$1 *)

@interface JavaUtilConcurrentExecutors_DelegatedExecutorService : JavaUtilConcurrentAbstractExecutorService {
 @public
  id<JavaUtilConcurrentExecutorService> e_;
}

- (id)initWithJavaUtilConcurrentExecutorService:(id<JavaUtilConcurrentExecutorService>)executor;
- (void)executeWithJavaLangRunnable:(id<JavaLangRunnable>)command;
- (void)shutdown;
- (id<JavaUtilList>)shutdownNow;
- (BOOL)isShutdown;
- (BOOL)isTerminated;
- (BOOL)awaitTerminationWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id<JavaUtilConcurrentFuture>)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task;
- (id<JavaUtilConcurrentFuture>)submitWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)task;
- (id<JavaUtilConcurrentFuture>)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                                    withId:(id)result;
- (id<JavaUtilList>)invokeAllWithJavaUtilCollection:(id<JavaUtilCollection>)tasks;
- (id<JavaUtilList>)invokeAllWithJavaUtilCollection:(id<JavaUtilCollection>)tasks
                                           withLong:(long long int)timeout
                 withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)invokeAnyWithJavaUtilCollection:(id<JavaUtilCollection>)tasks;
- (id)invokeAnyWithJavaUtilCollection:(id<JavaUtilCollection>)tasks
                             withLong:(long long int)timeout
   withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_DelegatedExecutorService *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_DelegatedExecutorService, e_, id<JavaUtilConcurrentExecutorService>)

@interface JavaUtilConcurrentExecutors_FinalizableDelegatedExecutorService : JavaUtilConcurrentExecutors_DelegatedExecutorService {
}

- (id)initWithJavaUtilConcurrentExecutorService:(id<JavaUtilConcurrentExecutorService>)executor;
- (void)dealloc;
@end

@interface JavaUtilConcurrentExecutors_DelegatedScheduledExecutorService : JavaUtilConcurrentExecutors_DelegatedExecutorService < JavaUtilConcurrentScheduledExecutorService > {
 @public
  id<JavaUtilConcurrentScheduledExecutorService> e_DelegatedScheduledExecutorService_;
}

- (id)initWithJavaUtilConcurrentScheduledExecutorService:(id<JavaUtilConcurrentScheduledExecutorService>)executor;
- (id<JavaUtilConcurrentScheduledFuture>)scheduleWithJavaLangRunnable:(id<JavaLangRunnable>)command
                                                             withLong:(long long int)delay
                                   withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id<JavaUtilConcurrentScheduledFuture>)scheduleWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable
                                                                       withLong:(long long int)delay
                                             withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id<JavaUtilConcurrentScheduledFuture>)scheduleAtFixedRateWithJavaLangRunnable:(id<JavaLangRunnable>)command
                                                                        withLong:(long long int)initialDelay
                                                                        withLong:(long long int)period
                                              withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id<JavaUtilConcurrentScheduledFuture>)scheduleWithFixedDelayWithJavaLangRunnable:(id<JavaLangRunnable>)command
                                                                           withLong:(long long int)initialDelay
                                                                           withLong:(long long int)delay
                                                 withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)copyAllFieldsTo:(JavaUtilConcurrentExecutors_DelegatedScheduledExecutorService *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_DelegatedScheduledExecutorService, e_DelegatedScheduledExecutorService_, id<JavaUtilConcurrentScheduledExecutorService>)

@interface JavaUtilConcurrentExecutors_$1 : NSObject < JavaUtilConcurrentCallable > {
 @public
  id<JavaSecurityPrivilegedAction> val$action_;
}

- (id)call;
- (id)initWithJavaSecurityPrivilegedAction:(id<JavaSecurityPrivilegedAction>)capture$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_$1, val$action_, id<JavaSecurityPrivilegedAction>)

@interface JavaUtilConcurrentExecutors_$2 : NSObject < JavaUtilConcurrentCallable > {
 @public
  id<JavaSecurityPrivilegedExceptionAction> val$action_;
}

- (id)call;
- (id)initWithJavaSecurityPrivilegedExceptionAction:(id<JavaSecurityPrivilegedExceptionAction>)capture$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentExecutors_$2, val$action_, id<JavaSecurityPrivilegedExceptionAction>)

#endif // _JavaUtilConcurrentExecutors_H_
