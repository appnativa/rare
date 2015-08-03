//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ReverseOrdering.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectReverseOrdering_RESTRICT
#define ComGoogleCommonCollectReverseOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectReverseOrdering_RESTRICT

#if !defined (_ComGoogleCommonCollectReverseOrdering_) && (ComGoogleCommonCollectReverseOrdering_INCLUDE_ALL || ComGoogleCommonCollectReverseOrdering_INCLUDE)
#define _ComGoogleCommonCollectReverseOrdering_

@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectReverseOrdering_serialVersionUID 0

@interface ComGoogleCommonCollectReverseOrdering : ComGoogleCommonCollectOrdering < JavaIoSerializable > {
 @public
  ComGoogleCommonCollectOrdering *forwardOrder_;
}

- (id)initWithComGoogleCommonCollectOrdering:(ComGoogleCommonCollectOrdering *)forwardOrder;
- (int)compareWithId:(id)a
              withId:(id)b;
- (ComGoogleCommonCollectOrdering *)reverse;
- (id)minWithId:(id)a
         withId:(id)b;
- (id)minWithId:(id)a
         withId:(id)b
         withId:(id)c
withNSObjectArray:(IOSObjectArray *)rest;
- (id)minWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;
- (id)minWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (id)maxWithId:(id)a
         withId:(id)b;
- (id)maxWithId:(id)a
         withId:(id)b
         withId:(id)c
withNSObjectArray:(IOSObjectArray *)rest;
- (id)maxWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;
- (id)maxWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)object;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectReverseOrdering *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectReverseOrdering, forwardOrder_, ComGoogleCommonCollectOrdering *)
#endif