//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/SortedIterable.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectSortedIterable_RESTRICT
#define ComGoogleCommonCollectSortedIterable_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectSortedIterable_RESTRICT

#if !defined (_ComGoogleCommonCollectSortedIterable_) && (ComGoogleCommonCollectSortedIterable_INCLUDE_ALL || ComGoogleCommonCollectSortedIterable_INCLUDE)
#define _ComGoogleCommonCollectSortedIterable_

@protocol JavaUtilComparator;
@protocol JavaUtilIterator;

#define JavaLangIterable_RESTRICT 1
#define JavaLangIterable_INCLUDE 1
#include "java/lang/Iterable.h"

@protocol ComGoogleCommonCollectSortedIterable < JavaLangIterable, NSObject, JavaObject >
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilIterator>)iterator;
@end
#endif
