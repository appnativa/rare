//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ForwardingNavigableSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectForwardingNavigableSet_RESTRICT
#define ComGoogleCommonCollectForwardingNavigableSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectForwardingNavigableSet_RESTRICT

#if !defined (_ComGoogleCommonCollectForwardingNavigableSet_) && (ComGoogleCommonCollectForwardingNavigableSet_INCLUDE_ALL || ComGoogleCommonCollectForwardingNavigableSet_INCLUDE)
#define _ComGoogleCommonCollectForwardingNavigableSet_

@protocol JavaUtilIterator;
@protocol JavaUtilSortedSet;

#define ComGoogleCommonCollectForwardingSortedSet_RESTRICT 1
#define ComGoogleCommonCollectForwardingSortedSet_INCLUDE 1
#include "com/google/common/collect/ForwardingSortedSet.h"

#define JavaUtilNavigableSet_RESTRICT 1
#define JavaUtilNavigableSet_INCLUDE 1
#include "java/util/NavigableSet.h"

@interface ComGoogleCommonCollectForwardingNavigableSet : ComGoogleCommonCollectForwardingSortedSet < JavaUtilNavigableSet > {
}

- (id)init;
- (id<JavaUtilNavigableSet>)delegate;
- (id)lowerWithId:(id)e;
- (id)standardLowerWithId:(id)e;
- (id)floorWithId:(id)e;
- (id)standardFloorWithId:(id)e;
- (id)ceilingWithId:(id)e;
- (id)standardCeilingWithId:(id)e;
- (id)higherWithId:(id)e;
- (id)standardHigherWithId:(id)e;
- (id)pollFirst;
- (id)standardPollFirst;
- (id)pollLast;
- (id)standardPollLast;
- (id)standardFirst;
- (id)standardLast;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id<JavaUtilIterator>)descendingIterator;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toElement
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableSet>)standardSubSetWithId:(id)fromElement
                                     withBoolean:(BOOL)fromInclusive
                                          withId:(id)toElement
                                     withBoolean:(BOOL)toInclusive;
- (id<JavaUtilSortedSet>)standardSubSetWithId:(id)fromElement
                                       withId:(id)toElement;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)standardHeadSetWithId:(id)toElement;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)standardTailSetWithId:(id)fromElement;
@end
#endif

#if !defined (_ComGoogleCommonCollectForwardingNavigableSet_StandardDescendingSet_) && (ComGoogleCommonCollectForwardingNavigableSet_INCLUDE_ALL || ComGoogleCommonCollectForwardingNavigableSet_StandardDescendingSet_INCLUDE)
#define _ComGoogleCommonCollectForwardingNavigableSet_StandardDescendingSet_

@class ComGoogleCommonCollectForwardingNavigableSet;

#define ComGoogleCommonCollectSets_RESTRICT 1
#define ComGoogleCommonCollectSets_DescendingSet_INCLUDE 1
#include "com/google/common/collect/Sets.h"

@interface ComGoogleCommonCollectForwardingNavigableSet_StandardDescendingSet : ComGoogleCommonCollectSets_DescendingSet {
}

- (id)initWithComGoogleCommonCollectForwardingNavigableSet:(ComGoogleCommonCollectForwardingNavigableSet *)outer$;
@end
#endif
