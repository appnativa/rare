//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/RegularImmutableSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectRegularImmutableSet_RESTRICT
#define ComGoogleCommonCollectRegularImmutableSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectRegularImmutableSet_RESTRICT

#if !defined (_ComGoogleCommonCollectRegularImmutableSet_) && (ComGoogleCommonCollectRegularImmutableSet_INCLUDE_ALL || ComGoogleCommonCollectRegularImmutableSet_INCLUDE)
#define _ComGoogleCommonCollectRegularImmutableSet_

@class IOSObjectArray;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_ArrayImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

@interface ComGoogleCommonCollectRegularImmutableSet : ComGoogleCommonCollectImmutableSet_ArrayImmutableSet {
 @public
  IOSObjectArray *table_;
  int mask_;
  int hashCode__;
}

- (id)initWithNSObjectArray:(IOSObjectArray *)elements
                    withInt:(int)hashCode
          withNSObjectArray:(IOSObjectArray *)table
                    withInt:(int)mask;
- (BOOL)containsWithId:(id)target;
- (NSUInteger)hash;
- (BOOL)isHashCodeFast;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectRegularImmutableSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectRegularImmutableSet, table_, IOSObjectArray *)
#endif
