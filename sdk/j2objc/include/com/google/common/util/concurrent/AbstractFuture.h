//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/AbstractFuture.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentAbstractFuture_RESTRICT
#define ComGoogleCommonUtilConcurrentAbstractFuture_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentAbstractFuture_RESTRICT

#if !defined (_ComGoogleCommonUtilConcurrentAbstractFuture_) && (ComGoogleCommonUtilConcurrentAbstractFuture_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractFuture_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractFuture_

@class ComGoogleCommonUtilConcurrentAbstractFuture_Sync;
@class ComGoogleCommonUtilConcurrentExecutionList;
@class JavaLangThrowable;
@class JavaUtilConcurrentCancellationException;
@class JavaUtilConcurrentTimeUnitEnum;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentExecutor;

#define ComGoogleCommonUtilConcurrentListenableFuture_RESTRICT 1
#define ComGoogleCommonUtilConcurrentListenableFuture_INCLUDE 1
#include "com/google/common/util/concurrent/ListenableFuture.h"

@interface ComGoogleCommonUtilConcurrentAbstractFuture : NSObject < ComGoogleCommonUtilConcurrentListenableFuture > {
 @public
  ComGoogleCommonUtilConcurrentAbstractFuture_Sync *sync_;
  ComGoogleCommonUtilConcurrentExecutionList *executionList_;
}

- (id)init;
- (id)getWithLong:(long long int)timeout
withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
- (id)get;
- (BOOL)isDone;
- (BOOL)isCancelled;
- (BOOL)cancelWithBoolean:(BOOL)mayInterruptIfRunning;
- (void)interruptTask;
- (BOOL)wasInterrupted;
- (void)addListenerWithJavaLangRunnable:(id<JavaLangRunnable>)listener
         withJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)exec;
- (BOOL)setWithId:(id)value;
- (BOOL)setExceptionWithJavaLangThrowable:(JavaLangThrowable *)throwable;
+ (JavaUtilConcurrentCancellationException *)cancellationExceptionWithCauseWithNSString:(NSString *)message
                                                                  withJavaLangThrowable:(JavaLangThrowable *)cause;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractFuture *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractFuture, sync_, ComGoogleCommonUtilConcurrentAbstractFuture_Sync *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractFuture, executionList_, ComGoogleCommonUtilConcurrentExecutionList *)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentAbstractFuture_Sync_) && (ComGoogleCommonUtilConcurrentAbstractFuture_INCLUDE_ALL || ComGoogleCommonUtilConcurrentAbstractFuture_Sync_INCLUDE)
#define _ComGoogleCommonUtilConcurrentAbstractFuture_Sync_

@class JavaLangThrowable;

#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_RESTRICT 1
#define JavaUtilConcurrentLocksAbstractQueuedSynchronizer_INCLUDE 1
#include "java/util/concurrent/locks/AbstractQueuedSynchronizer.h"

#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_CANCELLED 4
#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_COMPLETED 2
#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_COMPLETING 1
#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_INTERRUPTED 8
#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_RUNNING 0
#define ComGoogleCommonUtilConcurrentAbstractFuture_Sync_serialVersionUID 0

@interface ComGoogleCommonUtilConcurrentAbstractFuture_Sync : JavaUtilConcurrentLocksAbstractQueuedSynchronizer {
 @public
  id value_;
  JavaLangThrowable *exception_;
}

+ (int)RUNNING;
+ (int)COMPLETING;
+ (int)COMPLETED;
+ (int)CANCELLED;
+ (int)INTERRUPTED;
- (int)tryAcquireSharedWithInt:(int)ignored;
- (BOOL)tryReleaseSharedWithInt:(int)finalState;
- (id)getWithLong:(long long int)nanos;
- (id)get;
- (id)getValue;
- (BOOL)isDone;
- (BOOL)isCancelled;
- (BOOL)wasInterrupted;
- (BOOL)setWithId:(id)v;
- (BOOL)setExceptionWithJavaLangThrowable:(JavaLangThrowable *)t;
- (BOOL)cancelWithBoolean:(BOOL)interrupt;
- (BOOL)completeWithId:(id)v
 withJavaLangThrowable:(JavaLangThrowable *)t
               withInt:(int)finalState;
- (id)init;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentAbstractFuture_Sync *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractFuture_Sync, value_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentAbstractFuture_Sync, exception_, JavaLangThrowable *)
#endif