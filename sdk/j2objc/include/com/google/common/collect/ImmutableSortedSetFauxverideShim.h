//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableSortedSetFauxverideShim.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableSortedSetFauxverideShim_RESTRICT
#define ComGoogleCommonCollectImmutableSortedSetFauxverideShim_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableSortedSetFauxverideShim_RESTRICT

#if !defined (_ComGoogleCommonCollectImmutableSortedSetFauxverideShim_) && (ComGoogleCommonCollectImmutableSortedSetFauxverideShim_INCLUDE_ALL || ComGoogleCommonCollectImmutableSortedSetFauxverideShim_INCLUDE)
#define _ComGoogleCommonCollectImmutableSortedSetFauxverideShim_

@class ComGoogleCommonCollectImmutableSortedSet;
@class ComGoogleCommonCollectImmutableSortedSet_Builder;
@class IOSObjectArray;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

@interface ComGoogleCommonCollectImmutableSortedSetFauxverideShim : ComGoogleCommonCollectImmutableSet {
}

+ (ComGoogleCommonCollectImmutableSortedSet_Builder *)builder;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)element;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)e1
                                                withId:(id)e2;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)e1
                                                withId:(id)e2
                                                withId:(id)e3;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)e1
                                                withId:(id)e2
                                                withId:(id)e3
                                                withId:(id)e4;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)e1
                                                withId:(id)e2
                                                withId:(id)e3
                                                withId:(id)e4
                                                withId:(id)e5;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id)e1
                                                withId:(id)e2
                                                withId:(id)e3
                                                withId:(id)e4
                                                withId:(id)e5
                                                withId:(id)e6
                                     withNSObjectArray:(IOSObjectArray *)remaining;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithNSObjectArray:(IOSObjectArray *)elements OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end
#endif
