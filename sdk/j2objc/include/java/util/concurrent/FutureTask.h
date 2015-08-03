//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/FutureTask.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentFutureTask_H_
#define _JavaUtilConcurrentFutureTask_H_

@class JavaLangThread;
@class JavaLangThrowable;
@class JavaUtilConcurrentFutureTask_WaitNode;
@class JavaUtilConcurrentTimeUnitEnum;
@class SunMiscUnsafe;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentCallable;

#import "JreEmulation.h"
#include "java/util/concurrent/RunnableFuture.h"

#define JavaUtilConcurrentFutureTask_CANCELLED 4
#define JavaUtilConcurrentFutureTask_COMPLETING 1
#define JavaUtilConcurrentFutureTask_EXCEPTIONAL 3
#define JavaUtilConcurrentFutureTask_INTERRUPTED 6
#define JavaUtilConcurrentFutureTask_INTERRUPTING 5
#define JavaUtilConcurrentFutureTask_NEW 0
#define JavaUtilConcurrentFutureTask_NORMAL 2

@interface JavaUtilConcurrentFutureTask : NSObject < JavaUtilConcurrentRunnableFuture > {
 @public
  int state_;
  id<JavaUtilConcurrentCallable> callable_;
  id outcome_;
  JavaLangThread *runner_;
  JavaUtilConcurrentFutureTask_WaitNode *waiters_;
}

+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)stateOffset;
+ (long long int)runnerOffset;
+ (long long int)waitersOffset;
- (id)reportWithInt:(int)s;
- (id)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;
- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                        withId:(id)result;
- (BOOL)isCancelled;
- (BOOL)isDone;
- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning;
- (id)get;
- (id)getWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (void)done;
- (void)setWithId:(id)v;
- (void)setExceptionWithJavaLangThrowable:(JavaLangThrowable *)t;
- (void)run;
- (BOOL)runAndReset;
- (void)handlePossibleCancellationInterruptWithInt:(int)s;
- (void)finishCompletion;
- (int)awaitDoneWithBoolean:(BOOL)timed
                   withLong:(long long int)nanos;
- (void)removeWaiterWithJavaUtilConcurrentFutureTask_WaitNode:(JavaUtilConcurrentFutureTask_WaitNode *)node;
- (void)copyAllFieldsTo:(JavaUtilConcurrentFutureTask *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask, callable_, id<JavaUtilConcurrentCallable>)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask, outcome_, id)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask, runner_, JavaLangThread *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask, waiters_, JavaUtilConcurrentFutureTask_WaitNode *)

@interface JavaUtilConcurrentFutureTask_WaitNode : NSObject {
 @public
  JavaLangThread *thread_;
  JavaUtilConcurrentFutureTask_WaitNode *next_;
}

- (id)init;
- (void)copyAllFieldsTo:(JavaUtilConcurrentFutureTask_WaitNode *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask_WaitNode, thread_, JavaLangThread *)
J2OBJC_FIELD_SETTER(JavaUtilConcurrentFutureTask_WaitNode, next_, JavaUtilConcurrentFutureTask_WaitNode *)

#endif // _JavaUtilConcurrentFutureTask_H_
