//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ForkJoinPool.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentForkJoinPool_H_
#define _JavaUtilConcurrentForkJoinPool_H_

@class IOSObjectArray;
@class JavaLangRuntimePermission;
@class JavaLangThrowable;
@class JavaUtilArrayList;
@class JavaUtilConcurrentAtomicAtomicInteger;
@class JavaUtilConcurrentForkJoinTask;
@class JavaUtilConcurrentForkJoinWorkerThread;
@class JavaUtilConcurrentLocksReentrantLock;
@class JavaUtilConcurrentTimeUnitEnum;
@class JavaUtilRandom;
@class SunMiscUnsafe;
@protocol JavaLangRunnable;
@protocol JavaLangThread_UncaughtExceptionHandler;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentCallable;
@protocol JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory;
@protocol JavaUtilConcurrentForkJoinPool_ManagedBlocker;
@protocol JavaUtilConcurrentLocksCondition;
@protocol JavaUtilConcurrentRunnableFuture;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/util/concurrent/AbstractExecutorService.h"
#include "java/util/concurrent/RecursiveAction.h"

#define JavaUtilConcurrentForkJoinPool_AC_MASK -281474976710656
#define JavaUtilConcurrentForkJoinPool_AC_SHIFT 48
#define JavaUtilConcurrentForkJoinPool_AC_UNIT 281474976710656
#define JavaUtilConcurrentForkJoinPool_EC_SHIFT 16
#define JavaUtilConcurrentForkJoinPool_EC_UNIT 65536
#define JavaUtilConcurrentForkJoinPool_E_MASK 2147483647
#define JavaUtilConcurrentForkJoinPool_INITIAL_QUEUE_CAPACITY 8
#define JavaUtilConcurrentForkJoinPool_INT_SIGN ((int) 0x80000000)
#define JavaUtilConcurrentForkJoinPool_MAXIMUM_QUEUE_CAPACITY 16777216
#define JavaUtilConcurrentForkJoinPool_MAX_ID 32767
#define JavaUtilConcurrentForkJoinPool_SG_UNIT 65536
#define JavaUtilConcurrentForkJoinPool_SHORT_SIGN 32768
#define JavaUtilConcurrentForkJoinPool_SHRINK_RATE 4000000000
#define JavaUtilConcurrentForkJoinPool_SMASK 65535
#define JavaUtilConcurrentForkJoinPool_STOP_BIT 2147483648
#define JavaUtilConcurrentForkJoinPool_ST_SHIFT 31
#define JavaUtilConcurrentForkJoinPool_TC_MASK 281470681743360
#define JavaUtilConcurrentForkJoinPool_TC_SHIFT 32
#define JavaUtilConcurrentForkJoinPool_TC_UNIT 4294967296
#define JavaUtilConcurrentForkJoinPool_UAC_MASK -65536
#define JavaUtilConcurrentForkJoinPool_UAC_SHIFT 16
#define JavaUtilConcurrentForkJoinPool_UAC_UNIT 65536
#define JavaUtilConcurrentForkJoinPool_UTC_MASK 65535
#define JavaUtilConcurrentForkJoinPool_UTC_SHIFT 0
#define JavaUtilConcurrentForkJoinPool_UTC_UNIT 1

@interface JavaUtilConcurrentForkJoinPool : JavaUtilConcurrentAbstractExecutorService {
 @public
  IOSObjectArray *workers_;
  IOSObjectArray *submissionQueue_;
  JavaUtilConcurrentLocksReentrantLock *submissionLock_;
  id<JavaUtilConcurrentLocksCondition> termination_;
  id<JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory> factory_;
  id<JavaLangThread_UncaughtExceptionHandler> ueh_;
  NSString *workerNamePrefix_;
  long long int stealCount_;
  long long int ctl_;
  int parallelism_;
  int queueBase_;
  int queueTop_;
  BOOL shutdown__;
  BOOL locallyFifo_;
  int quiescerCount_;
  int blockedCount_;
  int nextWorkerNumber_;
  int nextWorkerIndex_;
  int scanGuard_;
}

+ (id<JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory>)defaultForkJoinWorkerThreadFactory;
+ (JavaLangRuntimePermission *)modifyThreadPermission;
+ (JavaUtilConcurrentAtomicAtomicInteger *)poolNumberGenerator;
+ (JavaUtilRandom *)workerSeedGenerator;
+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)ctlOffset;
+ (long long int)stealCountOffset;
+ (long long int)blockedCountOffset;
+ (long long int)quiescerCountOffset;
+ (long long int)scanGuardOffset;
+ (long long int)nextWorkerNumberOffset;
+ (void)checkPermission;
- (void)workWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w;
- (void)signalWork;
- (BOOL)tryReleaseWaiter;
- (BOOL)scanWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w
                                               withInt:(int)a;
- (BOOL)tryAwaitWorkWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w
                                                      withLong:(long long int)c;
- (void)idleAwaitWorkWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w
                                                       withLong:(long long int)currentCtl
                                                       withLong:(long long int)prevCtl
                                                        withInt:(int)v;
- (void)addSubmissionWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (void)growSubmissionQueue;
- (BOOL)tryPreBlock;
- (void)postBlock;
- (void)tryAwaitJoinWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)joinMe;
- (void)timedAwaitJoinWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)joinMe
                                                withLong:(long long int)nanos;
- (void)awaitBlockerWithJavaUtilConcurrentForkJoinPool_ManagedBlocker:(id<JavaUtilConcurrentForkJoinPool_ManagedBlocker>)blocker;
- (void)addWorker;
- (NSString *)nextWorkerName;
- (int)registerWorkerWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w;
- (void)deregisterWorkerWithJavaUtilConcurrentForkJoinWorkerThread:(JavaUtilConcurrentForkJoinWorkerThread *)w
                                             withJavaLangThrowable:(JavaLangThrowable *)ex;
- (BOOL)tryTerminateWithBoolean:(BOOL)now;
- (void)startTerminating;
- (void)cancelSubmissions;
- (void)terminateWaiters;
- (void)addQuiescerCountWithInt:(int)delta;
- (void)addActiveCountWithInt:(int)delta;
- (int)idlePerActive;
- (id)init;
- (id)initWithInt:(int)parallelism;
- (id)initWithInt:(int)parallelism
withJavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory:(id<JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory>)factory
withJavaLangThread_UncaughtExceptionHandler:(id<JavaLangThread_UncaughtExceptionHandler>)handler
      withBoolean:(BOOL)asyncMode;
- (id)invokeWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)task;
- (void)forkOrSubmitWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)task;
- (void)executeWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)task;
- (void)executeWithJavaLangRunnable:(id<JavaLangRunnable>)task;
- (JavaUtilConcurrentForkJoinTask *)submitWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)task;
- (JavaUtilConcurrentForkJoinTask *)submitWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)task;
- (JavaUtilConcurrentForkJoinTask *)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                                        withId:(id)result;
- (JavaUtilConcurrentForkJoinTask *)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task;
- (id<JavaUtilList>)invokeAllWithJavaUtilCollection:(id<JavaUtilCollection>)tasks;
- (id<JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory>)getFactory;
- (id<JavaLangThread_UncaughtExceptionHandler>)getUncaughtExceptionHandler;
- (int)getParallelism;
- (int)getPoolSize;
- (BOOL)getAsyncMode;
- (int)getRunningThreadCount;
- (int)getActiveThreadCount;
- (BOOL)isQuiescent;
- (long long int)getStealCount;
- (long long int)getQueuedTaskCount;
- (int)getQueuedSubmissionCount;
- (BOOL)hasQueuedSubmissions;
- (JavaUtilConcurrentForkJoinTask *)pollSubmission;
- (int)drainTasksToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (NSString *)description;
- (void)shutdown;
- (id<JavaUtilList>)shutdownNow;
- (BOOL)isTerminated;
- (BOOL)isTerminating;
- (BOOL)isAtLeastTerminating;
- (BOOL)isShutdown;
- (BOOL)awaitTerminationWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
+ (void)managedBlockWithJavaUtilConcurrentForkJoinPool_ManagedBlocker:(id<JavaUtilConcurrentForkJoinPool_ManagedBlocker>)blocker;
- (id<JavaUtilConcurrentRunnableFuture>)newTaskForWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                                                withId:(id)value OBJC_METHOD_FAMILY_NONE;
- (id<JavaUtilConcurrentRunnableFuture>)newTaskForWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(JavaUtilConcurrentForkJoinPool *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, workers_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, submissionQueue_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, submissionLock_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, termination_, id<JavaUtilConcurrentLocksCondition>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, factory_, id<JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, ueh_, id<JavaLangThread_UncaughtExceptionHandler>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool, workerNamePrefix_, NSString *)

@protocol JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory < NSObject, JavaObject >
- (JavaUtilConcurrentForkJoinWorkerThread *)newThreadWithJavaUtilConcurrentForkJoinPool:(JavaUtilConcurrentForkJoinPool *)pool OBJC_METHOD_FAMILY_NONE;
@end

@interface JavaUtilConcurrentForkJoinPool_DefaultForkJoinWorkerThreadFactory : NSObject < JavaUtilConcurrentForkJoinPool_ForkJoinWorkerThreadFactory > {
}

- (JavaUtilConcurrentForkJoinWorkerThread *)newThreadWithJavaUtilConcurrentForkJoinPool:(JavaUtilConcurrentForkJoinPool *)pool OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#define JavaUtilConcurrentForkJoinPool_InvokeAll_serialVersionUID -7914297376763021607

@interface JavaUtilConcurrentForkJoinPool_InvokeAll : JavaUtilConcurrentRecursiveAction {
 @public
  JavaUtilArrayList *tasks_;
}

- (id)initWithJavaUtilArrayList:(JavaUtilArrayList *)tasks;
- (void)compute;
- (void)copyAllFieldsTo:(JavaUtilConcurrentForkJoinPool_InvokeAll *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinPool_InvokeAll, tasks_, JavaUtilArrayList *)

@protocol JavaUtilConcurrentForkJoinPool_ManagedBlocker < NSObject, JavaObject >
- (BOOL)block;
- (BOOL)isReleasable;
@end

#endif // _JavaUtilConcurrentForkJoinPool_H_
