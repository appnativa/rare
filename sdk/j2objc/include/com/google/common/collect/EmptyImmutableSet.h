//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/EmptyImmutableSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectEmptyImmutableSet_RESTRICT
#define ComGoogleCommonCollectEmptyImmutableSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectEmptyImmutableSet_RESTRICT

#if !defined (_ComGoogleCommonCollectEmptyImmutableSet_) && (ComGoogleCommonCollectEmptyImmutableSet_INCLUDE_ALL || ComGoogleCommonCollectEmptyImmutableSet_INCLUDE)
#define _ComGoogleCommonCollectEmptyImmutableSet_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;
@protocol JavaUtilCollection;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

#define ComGoogleCommonCollectEmptyImmutableSet_serialVersionUID 0

@interface ComGoogleCommonCollectEmptyImmutableSet : ComGoogleCommonCollectImmutableSet {
}

+ (ComGoogleCommonCollectEmptyImmutableSet *)INSTANCE;
- (id)init;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)containsWithId:(id)target;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)targets;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (BOOL)isPartialView;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (ComGoogleCommonCollectImmutableList *)asList;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (BOOL)isHashCodeFast;
- (NSString *)description;
- (id)readResolve;
@end
#endif