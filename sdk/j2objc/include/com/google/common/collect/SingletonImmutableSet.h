//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/SingletonImmutableSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectSingletonImmutableSet_RESTRICT
#define ComGoogleCommonCollectSingletonImmutableSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectSingletonImmutableSet_RESTRICT

#if !defined (_ComGoogleCommonCollectSingletonImmutableSet_) && (ComGoogleCommonCollectSingletonImmutableSet_INCLUDE_ALL || ComGoogleCommonCollectSingletonImmutableSet_INCLUDE)
#define _ComGoogleCommonCollectSingletonImmutableSet_

@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

@interface ComGoogleCommonCollectSingletonImmutableSet : ComGoogleCommonCollectImmutableSet {
 @public
  id element_;
  int cachedHashCode_;
}

- (id)initWithId:(id)element;
- (id)initWithId:(id)element
         withInt:(int)hashCode;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)containsWithId:(id)target;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (BOOL)isPartialView;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (BOOL)isHashCodeFast;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectSingletonImmutableSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectSingletonImmutableSet, element_, id)
#endif
