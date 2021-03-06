//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/util/concurrent/ThreadFactoryBuilder.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonUtilConcurrentThreadFactoryBuilder_RESTRICT
#define ComGoogleCommonUtilConcurrentThreadFactoryBuilder_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonUtilConcurrentThreadFactoryBuilder_RESTRICT

#if !defined (_ComGoogleCommonUtilConcurrentThreadFactoryBuilder_) && (ComGoogleCommonUtilConcurrentThreadFactoryBuilder_INCLUDE_ALL || ComGoogleCommonUtilConcurrentThreadFactoryBuilder_INCLUDE)
#define _ComGoogleCommonUtilConcurrentThreadFactoryBuilder_

@class JavaLangBoolean;
@class JavaLangInteger;
@protocol JavaLangThread_UncaughtExceptionHandler;
@protocol JavaUtilConcurrentThreadFactory;

@interface ComGoogleCommonUtilConcurrentThreadFactoryBuilder : NSObject {
 @public
  NSString *nameFormat_;
  JavaLangBoolean *daemon_;
  JavaLangInteger *priority_;
  id<JavaLangThread_UncaughtExceptionHandler> uncaughtExceptionHandler_;
  id<JavaUtilConcurrentThreadFactory> backingThreadFactory_;
}

- (id)init;
- (ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)setNameFormatWithNSString:(NSString *)nameFormat;
- (ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)setDaemonWithBoolean:(BOOL)daemon;
- (ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)setPriorityWithInt:(int)priority;
- (ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)setUncaughtExceptionHandlerWithJavaLangThread_UncaughtExceptionHandler:(id<JavaLangThread_UncaughtExceptionHandler>)uncaughtExceptionHandler;
- (ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)setThreadFactoryWithJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)backingThreadFactory;
- (id<JavaUtilConcurrentThreadFactory>)build;
+ (id<JavaUtilConcurrentThreadFactory>)buildWithComGoogleCommonUtilConcurrentThreadFactoryBuilder:(ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)builder;
- (void)copyAllFieldsTo:(ComGoogleCommonUtilConcurrentThreadFactoryBuilder *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder, nameFormat_, NSString *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder, daemon_, JavaLangBoolean *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder, priority_, JavaLangInteger *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder, uncaughtExceptionHandler_, id<JavaLangThread_UncaughtExceptionHandler>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder, backingThreadFactory_, id<JavaUtilConcurrentThreadFactory>)
#endif

#if !defined (_ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1_) && (ComGoogleCommonUtilConcurrentThreadFactoryBuilder_INCLUDE_ALL || ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1_INCLUDE)
#define _ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1_

@class JavaLangBoolean;
@class JavaLangInteger;
@class JavaLangThread;
@class JavaUtilConcurrentAtomicAtomicLong;
@protocol JavaLangRunnable;
@protocol JavaLangThread_UncaughtExceptionHandler;

#define JavaUtilConcurrentThreadFactory_RESTRICT 1
#define JavaUtilConcurrentThreadFactory_INCLUDE 1
#include "java/util/concurrent/ThreadFactory.h"

@interface ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1 : NSObject < JavaUtilConcurrentThreadFactory > {
 @public
  id<JavaUtilConcurrentThreadFactory> val$backingThreadFactory_;
  NSString *val$nameFormat_;
  JavaUtilConcurrentAtomicAtomicLong *val$count_;
  JavaLangBoolean *val$daemon_;
  JavaLangInteger *val$priority_;
  id<JavaLangThread_UncaughtExceptionHandler> val$uncaughtExceptionHandler_;
}

- (JavaLangThread *)newThreadWithJavaLangRunnable:(id<JavaLangRunnable>)runnable OBJC_METHOD_FAMILY_NONE;
- (id)initWithJavaUtilConcurrentThreadFactory:(id<JavaUtilConcurrentThreadFactory>)capture$0
                                 withNSString:(NSString *)capture$1
       withJavaUtilConcurrentAtomicAtomicLong:(JavaUtilConcurrentAtomicAtomicLong *)capture$2
                          withJavaLangBoolean:(JavaLangBoolean *)capture$3
                          withJavaLangInteger:(JavaLangInteger *)capture$4
  withJavaLangThread_UncaughtExceptionHandler:(id<JavaLangThread_UncaughtExceptionHandler>)capture$5;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$backingThreadFactory_, id<JavaUtilConcurrentThreadFactory>)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$nameFormat_, NSString *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$count_, JavaUtilConcurrentAtomicAtomicLong *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$daemon_, JavaLangBoolean *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$priority_, JavaLangInteger *)
J2OBJC_FIELD_SETTER(ComGoogleCommonUtilConcurrentThreadFactoryBuilder_$1, val$uncaughtExceptionHandler_, id<JavaLangThread_UncaughtExceptionHandler>)
#endif
