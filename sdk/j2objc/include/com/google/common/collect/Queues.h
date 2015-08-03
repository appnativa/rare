//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/Queues.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectQueues_RESTRICT
#define ComGoogleCommonCollectQueues_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectQueues_RESTRICT

#if !defined (_ComGoogleCommonCollectQueues_) && (ComGoogleCommonCollectQueues_INCLUDE_ALL || ComGoogleCommonCollectQueues_INCLUDE)
#define _ComGoogleCommonCollectQueues_

@class JavaUtilArrayDeque;
@class JavaUtilConcurrentArrayBlockingQueue;
@class JavaUtilConcurrentConcurrentLinkedQueue;
@class JavaUtilConcurrentLinkedBlockingDeque;
@class JavaUtilConcurrentLinkedBlockingQueue;
@class JavaUtilConcurrentPriorityBlockingQueue;
@class JavaUtilConcurrentSynchronousQueue;
@class JavaUtilConcurrentTimeUnitEnum;
@class JavaUtilPriorityQueue;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilConcurrentBlockingQueue;
@protocol JavaUtilQueue;

@interface ComGoogleCommonCollectQueues : NSObject {
}

- (id)init;
+ (JavaUtilConcurrentArrayBlockingQueue *)newArrayBlockingQueueWithInt:(int)capacity OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilArrayDeque *)newArrayDeque OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilArrayDeque *)newArrayDequeWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentConcurrentLinkedQueue *)newConcurrentLinkedQueue OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentConcurrentLinkedQueue *)newConcurrentLinkedQueueWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingDeque *)newLinkedBlockingDeque OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingDeque *)newLinkedBlockingDequeWithInt:(int)capacity OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingDeque *)newLinkedBlockingDequeWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingQueue *)newLinkedBlockingQueue OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingQueue *)newLinkedBlockingQueueWithInt:(int)capacity OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentLinkedBlockingQueue *)newLinkedBlockingQueueWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentPriorityBlockingQueue *)newPriorityBlockingQueue OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentPriorityBlockingQueue *)newPriorityBlockingQueueWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilPriorityQueue *)newPriorityQueue OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilPriorityQueue *)newPriorityQueueWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (JavaUtilConcurrentSynchronousQueue *)newSynchronousQueue OBJC_METHOD_FAMILY_NONE;
+ (int)drainWithJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)q
                         withJavaUtilCollection:(id<JavaUtilCollection>)buffer
                                        withInt:(int)numElements
                                       withLong:(long long int)timeout
             withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
+ (int)drainUninterruptiblyWithJavaUtilConcurrentBlockingQueue:(id<JavaUtilConcurrentBlockingQueue>)q
                                        withJavaUtilCollection:(id<JavaUtilCollection>)buffer
                                                       withInt:(int)numElements
                                                      withLong:(long long int)timeout
                            withJavaUtilConcurrentTimeUnitEnum:(JavaUtilConcurrentTimeUnitEnum *)unit;
+ (id<JavaUtilQueue>)synchronizedQueueWithJavaUtilQueue:(id<JavaUtilQueue>)queue;
@end
#endif