//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ForwardingSortedSetMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectForwardingSortedSetMultimap_RESTRICT
#define ComGoogleCommonCollectForwardingSortedSetMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectForwardingSortedSetMultimap_RESTRICT

#if !defined (_ComGoogleCommonCollectForwardingSortedSetMultimap_) && (ComGoogleCommonCollectForwardingSortedSetMultimap_INCLUDE_ALL || ComGoogleCommonCollectForwardingSortedSetMultimap_INCLUDE)
#define _ComGoogleCommonCollectForwardingSortedSetMultimap_

@protocol JavaLangIterable;
@protocol JavaUtilComparator;
@protocol JavaUtilSortedSet;

#define ComGoogleCommonCollectForwardingSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectForwardingSetMultimap_INCLUDE 1
#include "com/google/common/collect/ForwardingSetMultimap.h"

#define ComGoogleCommonCollectSortedSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectSortedSetMultimap_INCLUDE 1
#include "com/google/common/collect/SortedSetMultimap.h"

@interface ComGoogleCommonCollectForwardingSortedSetMultimap : ComGoogleCommonCollectForwardingSetMultimap < ComGoogleCommonCollectSortedSetMultimap > {
}

- (id)init;
- (id<ComGoogleCommonCollectSortedSetMultimap>)delegate;
- (id<JavaUtilSortedSet>)getWithId:(id)key;
- (id<JavaUtilSortedSet>)removeAllWithId:(id)key;
- (id<JavaUtilSortedSet>)replaceValuesWithId:(id)key
                        withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<JavaUtilComparator>)valueComparator;
@end
#endif
