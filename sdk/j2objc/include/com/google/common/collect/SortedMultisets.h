//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/SortedMultisets.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectSortedMultisets_RESTRICT
#define ComGoogleCommonCollectSortedMultisets_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectSortedMultisets_RESTRICT
#if ComGoogleCommonCollectSortedMultisets_NavigableElementSet_INCLUDE
#define ComGoogleCommonCollectSortedMultisets_ElementSet_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectSortedMultisets_) && (ComGoogleCommonCollectSortedMultisets_INCLUDE_ALL || ComGoogleCommonCollectSortedMultisets_INCLUDE)
#define _ComGoogleCommonCollectSortedMultisets_

@protocol ComGoogleCommonCollectMultiset_Entry;

@interface ComGoogleCommonCollectSortedMultisets : NSObject {
}

- (id)init;
+ (id)getElementOrThrowWithComGoogleCommonCollectMultiset_Entry:(id<ComGoogleCommonCollectMultiset_Entry>)entry_;
+ (id)getElementOrNullWithComGoogleCommonCollectMultiset_Entry:(id<ComGoogleCommonCollectMultiset_Entry>)entry_;
@end
#endif

#if !defined (_ComGoogleCommonCollectSortedMultisets_ElementSet_) && (ComGoogleCommonCollectSortedMultisets_INCLUDE_ALL || ComGoogleCommonCollectSortedMultisets_ElementSet_INCLUDE)
#define _ComGoogleCommonCollectSortedMultisets_ElementSet_

@protocol ComGoogleCommonCollectSortedMultiset;
@protocol JavaUtilComparator;

#define ComGoogleCommonCollectMultisets_RESTRICT 1
#define ComGoogleCommonCollectMultisets_ElementSet_INCLUDE 1
#include "com/google/common/collect/Multisets.h"

#define JavaUtilSortedSet_RESTRICT 1
#define JavaUtilSortedSet_INCLUDE 1
#include "java/util/SortedSet.h"

@interface ComGoogleCommonCollectSortedMultisets_ElementSet : ComGoogleCommonCollectMultisets_ElementSet < JavaUtilSortedSet > {
 @public
  __weak id<ComGoogleCommonCollectSortedMultiset> multiset__;
}

- (id)initWithComGoogleCommonCollectSortedMultiset:(id<ComGoogleCommonCollectSortedMultiset>)multiset;
- (id<ComGoogleCommonCollectSortedMultiset>)multiset;
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromElement
                               withId:(id)toElement;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toElement;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromElement;
- (id)first;
- (id)last;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectSortedMultisets_ElementSet *)other;
@end
#endif

#if !defined (_ComGoogleCommonCollectSortedMultisets_NavigableElementSet_) && (ComGoogleCommonCollectSortedMultisets_INCLUDE_ALL || ComGoogleCommonCollectSortedMultisets_NavigableElementSet_INCLUDE)
#define _ComGoogleCommonCollectSortedMultisets_NavigableElementSet_

@protocol ComGoogleCommonCollectSortedMultiset;
@protocol JavaUtilIterator;

#define JavaUtilNavigableSet_RESTRICT 1
#define JavaUtilNavigableSet_INCLUDE 1
#include "java/util/NavigableSet.h"

@interface ComGoogleCommonCollectSortedMultisets_NavigableElementSet : ComGoogleCommonCollectSortedMultisets_ElementSet < JavaUtilNavigableSet > {
}

- (id)initWithComGoogleCommonCollectSortedMultiset:(id<ComGoogleCommonCollectSortedMultiset>)multiset;
- (id)lowerWithId:(id)e;
- (id)floorWithId:(id)e;
- (id)ceilingWithId:(id)e;
- (id)higherWithId:(id)e;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id<JavaUtilIterator>)descendingIterator;
- (id)pollFirst;
- (id)pollLast;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toElement
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement
                              withBoolean:(BOOL)inclusive;
@end
#endif