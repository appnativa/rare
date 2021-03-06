//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/EmptyImmutableList.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectEmptyImmutableList_RESTRICT
#define ComGoogleCommonCollectEmptyImmutableList_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectEmptyImmutableList_RESTRICT

#if !defined (_ComGoogleCommonCollectEmptyImmutableList_) && (ComGoogleCommonCollectEmptyImmutableList_INCLUDE_ALL || ComGoogleCommonCollectEmptyImmutableList_INCLUDE)
#define _ComGoogleCommonCollectEmptyImmutableList_

@class ComGoogleCommonCollectUnmodifiableIterator;
@class ComGoogleCommonCollectUnmodifiableListIterator;
@class IOSObjectArray;
@protocol JavaUtilCollection;

#define ComGoogleCommonCollectImmutableList_RESTRICT 1
#define ComGoogleCommonCollectImmutableList_INCLUDE 1
#include "com/google/common/collect/ImmutableList.h"

#define ComGoogleCommonCollectEmptyImmutableList_serialVersionUID 0

@interface ComGoogleCommonCollectEmptyImmutableList : ComGoogleCommonCollectImmutableList {
}

+ (ComGoogleCommonCollectEmptyImmutableList *)INSTANCE;
- (id)init;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)isPartialView;
- (BOOL)containsWithId:(id)target;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)targets;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (id)getWithInt:(int)index;
- (int)indexOfWithId:(id)target;
- (int)lastIndexOfWithId:(id)target;
- (ComGoogleCommonCollectImmutableList *)subListWithInt:(int)fromIndex
                                                withInt:(int)toIndex;
- (ComGoogleCommonCollectImmutableList *)reverse;
- (ComGoogleCommonCollectUnmodifiableListIterator *)listIterator;
- (ComGoogleCommonCollectUnmodifiableListIterator *)listIteratorWithInt:(int)start;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (id)readResolve;
@end
#endif
