//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/ListenableFuture.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentListenableFuture_RESTRICT
#define ComGoogleCommonUtilConcurrentListenableFuture_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentListenableFuture_RESTRICT

#if !defined (_ComGoogleCommonUtilConcurrentListenableFuture_) && (ComGoogleCommonUtilConcurrentListenableFuture_INCLUDE_ALL || ComGoogleCommonUtilConcurrentListenableFuture_INCLUDE)
#define _ComGoogleCommonUtilConcurrentListenableFuture_

@protocol JavaLangRunnable;
@protocol JavaUtilConcurrentExecutor;

#define JavaUtilConcurrentFuture_RESTRICT 1
#define JavaUtilConcurrentFuture_INCLUDE 1
#include "java/util/concurrent/Future.h"

@protocol ComGoogleCommonUtilConcurrentListenableFuture < JavaUtilConcurrentFuture, NSObject, JavaObject >
- (void)addListenerWithJavaLangRunnable:(id<JavaLangRunnable>)listener
         withJavaUtilConcurrentExecutor:(id<JavaUtilConcurrentExecutor>)executor;
@end
#endif