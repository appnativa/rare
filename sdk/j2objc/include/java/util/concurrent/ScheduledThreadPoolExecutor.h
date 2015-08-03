//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ScheduledThreadPoolExecutor.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentScheduledThreadPoolExecutor_H_
#define _JavaUtilConcurrentScheduledThreadPoolExecutor_H_

@class IOSObjectArray;
@class JavaLangThread;
@class JavaUtilConcurrentAtomicAtomicLong;
@class JavaUtilConcurrentLocksReentrantLock;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaLangRunnable;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentCallable;
@protocol JavaUtilConcurrentDelayed;
@protocol JavaUtilConcurrentFuture;
@protocol JavaUtilConcurrentLocksCondition;
@protocol JavaUtilConcurrentRejectedExecutionHandler;
@protocol JavaUtilConcurrentScheduledFuture;
@protocol JavaUtilConcurrentThreadFactory;
@protocol JavaUtilList;

#import "JreEmulation.h"
#include "java/util/AbstractQueue.h"
#include "java/util/Iterator.h"
#include "java/util/concurrent/BlockingQueue.h"
#include "java/util/concurrent/FutureTask.h"
#include "java/util/concurrent/RunnableScheduledFuture.h"
#include "java/util/concurrent/ScheduledExecutorService.h"
#include "java/util/concurrent/ThreadPoolExecutor.h"

@interface JavaUtilConcurrentScheduledThreadPoolExecutor : JavaUtilConcurrentThreadPoolExecutor < JavaUtilConcurrentScheduledExecutorService > {
 @public
  BOOL continueExistingPeriodicTasksAfterShutdown_;
  BOOL executeExistingDelayedTasksAfterShutdown_;
  BOOL removeOnCancel_;
}

+ (JavaUtilConcurrentAtomicAtomicLong *)sequencer;
- (long long int)now;
- (BOOL)canRunInCurrentRunStateWithBoolean:(BOOL)periodic;
- (void)delayedExecuteWithJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)task;
- (void)reExecutePeriodicWithJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)task;
- (void)onShutdown;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)decorateTaskWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                    withJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)task;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)decorateTaskWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable
                                              withJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)task;
- (id)initWithInt:(int)corePoolSize;
- (id)initWithInt:(int)corePoolSize
withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory;
- (id)initWithInt:(int)corePoolSize
withJavaUtilConcurrentRejectedExecutionHandler:(id<JavaUtilConcurrentRejectedExecutionHandler>)handler;
- (id)initWithInt:(int)corePoolSize
withJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)threadFactory
withJavaUtilConcurrentRejectedExecutionHandler:(id<JavaUtilConcurrentRejectedExecutionHandler>)handler;
- (long long int)triggerTimeWithLong:(long long int)delay
  withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (long long int)triggerTimeWithLong:(long long int)delay;
- (long long int)overflowFreeWithLong:(long long int)delay;
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
- (void)executeWithJavaLangRunnable:(id<JavaLangRunnable>)command;
- (id<JavaUtilConcurrentFuture>)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task;
- (id<JavaUtilConcurrentFuture>)submitWithJavaLangRunnable:(id<JavaLangRunnable>)task
                                                    withId:(id)result;
- (id<JavaUtilConcurrentFuture>)submitWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)task;
- (void)setContinueExistingPeriodicTasksAfterShutdownPolicyWithBoolean:(BOOL)value;
- (BOOL)getContinueExistingPeriodicTasksAfterShutdownPolicy;
- (void)setExecuteExistingDelayedTasksAfterShutdownPolicyWithBoolean:(BOOL)value;
- (BOOL)getExecuteExistingDelayedTasksAfterShutdownPolicy;
- (void)setRemoveOnCancelPolicyWithBoolean:(BOOL)value;
- (BOOL)getRemoveOnCancelPolicy;
- (void)shutdown;
- (id<JavaUtilList>)shutdownNow;
- (id<JavaUtilConcurrentBlockingQueue>)getQueue;
- (void)copyAllFieldsTo:(JavaUtilConcurrentScheduledThreadPoolExecutor *)other;
@end

@interface JavaUtilConcurrentScheduledThreadPoolExecutor_ScheduledFutureTask : JavaUtilConcurrentFutureTask < JavaUtilConcurrentRunnableScheduledFuture > {
 @public
  __weak JavaUtilConcurrentScheduledThreadPoolExecutor *this$0_;
  long long int sequenceNumber_;
  long long int time_;
  long long int period_;
  id<JavaUtilConcurrentRunnableScheduledFuture> outerTask_;
  int heapIndex_;
}

- (id)initWithJavaUtilConcurrentScheduledThreadPoolExecutor:(JavaUtilConcurrentScheduledThreadPoolExecutor *)outer$
                                       withJavaLangRunnable:(id<JavaLangRunnable>)r
                                                     withId:(id)result
                                                   withLong:(long long int)ns;
- (id)initWithJavaUtilConcurrentScheduledThreadPoolExecutor:(JavaUtilConcurrentScheduledThreadPoolExecutor *)outer$
                                       withJavaLangRunnable:(id<JavaLangRunnable>)r
                                                     withId:(id)result
                                                   withLong:(long long int)ns
                                                   withLong:(long long int)period;
- (id)initWithJavaUtilConcurrentScheduledThreadPoolExecutor:(JavaUtilConcurrentScheduledThreadPoolExecutor *)outer$
                             withJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable
                                                   withLong:(long long int)ns;
- (long long int)getDelayWithJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (int)compareToWithId:(id<JavaUtilConcurrentDelayed>)other;
- (BOOL)isPeriodic;
- (void)setNextRunTime;
- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning;
- (void)run;
- (void)copyAllFieldsTo:(JavaUtilConcurrentScheduledThreadPoolExecutor_ScheduledFutureTask *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_ScheduledFutureTask, outerTask_, id<JavaUtilConcurrentRunnableScheduledFuture>)

#define JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue_INITIAL_CAPACITY 16

@interface JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue : JavaUtilAbstractQueue < JavaUtilConcurrentBlockingQueue > {
 @public
  IOSObjectArray *queue_;
  JavaUtilConcurrentLocksReentrantLock *lock_;
  int size__;
  JavaLangThread *leader_;
  id<JavaUtilConcurrentLocksCondition> available_;
}

- (void)setIndexWithJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)f
                                                      withInt:(int)idx;
- (void)siftUpWithInt:(int)k
withJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)key;
- (void)siftDownWithInt:(int)k
withJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)key;
- (void)grow;
- (int)indexOfWithId:(id)x;
- (BOOL)containsWithId:(id)x;
- (BOOL)removeWithId:(id)x;
- (int)size;
- (BOOL)isEmpty;
- (int)remainingCapacity;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)peek;
- (BOOL)offerWithId:(id<JavaLangRunnable>)x;
- (void)putWithId:(id<JavaLangRunnable>)e;
- (BOOL)addWithId:(id<JavaLangRunnable>)e;
- (BOOL)offerWithId:(id<JavaLangRunnable>)e
           withLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)finishPollWithJavaUtilConcurrentRunnableScheduledFuture:(id<JavaUtilConcurrentRunnableScheduledFuture>)f;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)poll;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)take;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)pollWithLong:(long long int)timeout
                           withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)clear;
- (id<JavaUtilConcurrentRunnableScheduledFuture>)peekExpired;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)drainToWithJavaUtilCollection:(id<JavaUtilCollection>)c
                             withInt:(int)maxElements;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (id<JavaUtilIterator>)iterator;
- (id)init;
- (void)copyAllFieldsTo:(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue, queue_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue, lock_, JavaUtilConcurrentLocksReentrantLock *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue, leader_, JavaLangThread *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue, available_, id<JavaUtilConcurrentLocksCondition>)

@interface JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue_Itr : NSObject < JavaUtilIterator > {
 @public
  JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue *this$0_;
  IOSObjectArray *array_;
  int cursor_;
  int lastRet_;
}

- (id)initWithJavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue:(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue *)outer$
                          withJavaUtilConcurrentRunnableScheduledFutureArray:(IOSObjectArray *)array;
- (BOOL)hasNext;
- (id<JavaLangRunnable>)next;
- (void)remove;
- (void)copyAllFieldsTo:(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue_Itr *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue_Itr, this$0_, JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentScheduledThreadPoolExecutor_DelayedWorkQueue_Itr, array_, IOSObjectArray *)

#endif // _JavaUtilConcurrentScheduledThreadPoolExecutor_H_
