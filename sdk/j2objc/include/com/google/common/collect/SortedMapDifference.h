//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/SortedMapDifference.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectSortedMapDifference_RESTRICT
#define ComGoogleCommonCollectSortedMapDifference_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectSortedMapDifference_RESTRICT

#if !defined (_ComGoogleCommonCollectSortedMapDifference_) && (ComGoogleCommonCollectSortedMapDifference_INCLUDE_ALL || ComGoogleCommonCollectSortedMapDifference_INCLUDE)
#define _ComGoogleCommonCollectSortedMapDifference_

@protocol JavaUtilSortedMap;

#define ComGoogleCommonCollectMapDifference_RESTRICT 1
#define ComGoogleCommonCollectMapDifference_INCLUDE 1
#include "com/google/common/collect/MapDifference.h"

@protocol ComGoogleCommonCollectSortedMapDifference < ComGoogleCommonCollectMapDifference, NSObject, JavaObject >
- (id<JavaUtilSortedMap>)entriesOnlyOnLeft;
- (id<JavaUtilSortedMap>)entriesOnlyOnRight;
- (id<JavaUtilSortedMap>)entriesInCommon;
- (id<JavaUtilSortedMap>)entriesDiffering;
@end
#endif
