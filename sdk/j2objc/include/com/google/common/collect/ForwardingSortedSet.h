//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ForwardingSortedSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectForwardingSortedSet_RESTRICT
#define ComGoogleCommonCollectForwardingSortedSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectForwardingSortedSet_RESTRICT

#if !defined (_ComGoogleCommonCollectForwardingSortedSet_) && (ComGoogleCommonCollectForwardingSortedSet_INCLUDE_ALL || ComGoogleCommonCollectForwardingSortedSet_INCLUDE)
#define _ComGoogleCommonCollectForwardingSortedSet_

@protocol JavaUtilComparator;

#define ComGoogleCommonCollectForwardingSet_RESTRICT 1
#define ComGoogleCommonCollectForwardingSet_INCLUDE 1
#include "com/google/common/collect/ForwardingSet.h"

#define JavaUtilSortedSet_RESTRICT 1
#define JavaUtilSortedSet_INCLUDE 1
#include "java/util/SortedSet.h"

@interface ComGoogleCommonCollectForwardingSortedSet : ComGoogleCommonCollectForwardingSet < JavaUtilSortedSet > {
}

- (id)init;
- (id<JavaUtilSortedSet>)delegate;
- (id<JavaUtilComparator>)comparator;
- (id)first;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toElement;
- (id)last;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromElement
                               withId:(id)toElement;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromElement;
- (int)unsafeCompareWithId:(id)o1
                    withId:(id)o2;
- (BOOL)standardContainsWithId:(id)object;
- (BOOL)standardRemoveWithId:(id)object;
- (id<JavaUtilSortedSet>)standardSubSetWithId:(id)fromElement
                                       withId:(id)toElement;
@end
#endif