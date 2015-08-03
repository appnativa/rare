//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/ListenableFutureTask.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentListenableFutureTask_RESTRICT
#define ComGoogleCommonUtilConcurrentListenableFutureTask_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentListenableFutureTask_RESTRICT

#if !defined (_ComGoogleCommonUtilConcurrentListenableFutureTask_) && (ComGoogleCommonUtilConcurrentListenableFutureTask_INCLUDE_ALL || ComGoogleCommonUtilConcurrentListenableFutureTask_INCLUDE)
#define _ComGoogleCommonUtilConcurrentListenableFutureTask_

@class ComGoogleCommonUtilConcurrentExecutionList;
@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentCallable;
@protocol JavaUtilConcurrentExecutor;

#define JavaUtilConcurrentFutureTask_RESTRICT 1
#define JavaUtilConcurrentFutureTask_INCLUDE 1
#include "java/util/concurrent/FutureTask.h"

#define ComGoogleCommonUtilConcurrentListenableFuture_RESTRICT 1
#define ComGoogleCommonUtilConcurrentListenableFuture_INCLUDE 1
#include "com/google/common/util/concurrent/ListenableFuture.h"

@interface ComGoogleCommonUtilConcurrentListenableFutureTask : JavaUtilConcurrentFutureTask < ComGoogleCommonUtilConcurrentListenableFuture > {
 @public
  ComGoogleCommonUtilConcurrentExecutionList *executionList_;
}

+ (ComGoogleCommonUtilConcurrentListenableFutureTask *)createWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;
+ (ComGoogleCommonUtilConcurrentListenableFutureTask *)createWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                                                                           withId:(id)result;
- (id)initWithJavaUtilConcurrentCallable:(id<JavaUtilConcurrentCallable>)callable;
- (id)initWithJavaLangRunnable:(id<JavaLangRunnable>)runnable
                        withId:(id)result;
- (void)addListenerWithJavaLangRunnable:(id<JavaLangRunnable>)listener
         withJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)exec;
- (void)done;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentListenableFutureTask *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentListenableFutureTask, executionList_, ComGoogleCommonUtilConcurrentExecutionList *)
#endif
