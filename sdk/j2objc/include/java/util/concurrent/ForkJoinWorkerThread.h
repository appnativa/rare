//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ForkJoinWorkerThread.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentForkJoinWorkerThread_H_
#define _JavaUtilConcurrentForkJoinWorkerThread_H_

@class IOSObjectArray;
@class JavaLangThrowable;
@class JavaUtilConcurrentForkJoinPool;
@class JavaUtilConcurrentForkJoinTask;
@class SunMiscUnsafe;
@protocol JavaUtilCollection;

#import "JreEmulation.h"
#include "java/lang/Thread.h"

#define JavaUtilConcurrentForkJoinWorkerThread_INITIAL_QUEUE_CAPACITY 8192
#define JavaUtilConcurrentForkJoinWorkerThread_MAXIMUM_QUEUE_CAPACITY 16777216
#define JavaUtilConcurrentForkJoinWorkerThread_MAX_HELP 16
#define JavaUtilConcurrentForkJoinWorkerThread_SMASK 65535

@interface JavaUtilConcurrentForkJoinWorkerThread : JavaLangThread {
 @public
  IOSObjectArray *queue_;
  JavaUtilConcurrentForkJoinPool *pool_;
  int queueTop_;
  int queueBase_;
  int stealHint_;
  int poolIndex_;
  int nextWait_;
  int eventCount_;
  int seed_;
  int stealCount_;
  BOOL terminate_;
  BOOL parked_;
  BOOL locallyFifo_;
  JavaUtilConcurrentForkJoinTask *currentSteal_;
  JavaUtilConcurrentForkJoinTask *currentJoin_;
}

+ (SunMiscUnsafe *)UNSAFE;
- (id)initWithJavaUtilConcurrentForkJoinPool:(JavaUtilConcurrentForkJoinPool *)pool;
- (JavaUtilConcurrentForkJoinPool *)getPool;
- (int)getPoolIndex;
- (int)nextSeed;
- (void)onStart;
- (void)onTerminationWithJavaLangThrowable:(JavaLangThrowable *)exception;
- (void)run;
+ (BOOL)casSlotNullWithJavaUtilConcurrentForkJoinTaskArray:(IOSObjectArray *)q
                                                   withInt:(int)i
                        withJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
+ (void)writeSlotWithJavaUtilConcurrentForkJoinTaskArray:(IOSObjectArray *)q
                                                 withInt:(int)i
                      withJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (void)pushTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (void)growQueue;
- (JavaUtilConcurrentForkJoinTask *)deqTask;
- (JavaUtilConcurrentForkJoinTask *)locallyDeqTask;
- (JavaUtilConcurrentForkJoinTask *)popTask;
- (BOOL)unpushTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (JavaUtilConcurrentForkJoinTask *)peekTask;
- (void)execTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (void)cancelTasks;
- (int)drainTasksToWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)getQueueSize;
- (JavaUtilConcurrentForkJoinTask *)pollLocalTask;
- (JavaUtilConcurrentForkJoinTask *)pollTask;
- (int)joinTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)joinMe;
- (BOOL)localHelpJoinTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)joinMe;
- (BOOL)helpJoinTaskWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)joinMe;
- (int)tryDeqAndExecWithJavaUtilConcurrentForkJoinTask:(JavaUtilConcurrentForkJoinTask *)t;
- (int)getEstimatedSurplusTaskCount;
- (void)helpQuiescePool;
- (void)copyAllFieldsTo:(JavaUtilConcurrentForkJoinWorkerThread *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinWorkerThread, queue_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinWorkerThread, pool_, JavaUtilConcurrentForkJoinPool *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinWorkerThread, currentSteal_, JavaUtilConcurrentForkJoinTask *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentForkJoinWorkerThread, currentJoin_, JavaUtilConcurrentForkJoinTask *)

#endif // _JavaUtilConcurrentForkJoinWorkerThread_H_
