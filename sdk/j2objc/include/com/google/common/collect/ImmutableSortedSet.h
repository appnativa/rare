//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableSortedSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableSortedSet_RESTRICT
#define ComGoogleCommonCollectImmutableSortedSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableSortedSet_RESTRICT

#if !defined (_ComGoogleCommonCollectImmutableSortedSet_) && (ComGoogleCommonCollectImmutableSortedSet_INCLUDE_ALL || ComGoogleCommonCollectImmutableSortedSet_INCLUDE)
#define _ComGoogleCommonCollectImmutableSortedSet_

@class ComGoogleCommonCollectImmutableSortedSet_Builder;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class IOSObjectArray;
@protocol JavaLangComparable;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilSortedSet;

#define ComGoogleCommonCollectImmutableSortedSetFauxverideShim_RESTRICT 1
#define ComGoogleCommonCollectImmutableSortedSetFauxverideShim_INCLUDE 1
#include "com/google/common/collect/ImmutableSortedSetFauxverideShim.h"

#define JavaUtilNavigableSet_RESTRICT 1
#define JavaUtilNavigableSet_INCLUDE 1
#include "java/util/NavigableSet.h"

#define ComGoogleCommonCollectSortedIterable_RESTRICT 1
#define ComGoogleCommonCollectSortedIterable_INCLUDE 1
#include "com/google/common/collect/SortedIterable.h"

@interface ComGoogleCommonCollectImmutableSortedSet : ComGoogleCommonCollectImmutableSortedSetFauxverideShim < JavaUtilNavigableSet, ComGoogleCommonCollectSortedIterable > {
 @public
  id<JavaUtilComparator> comparator__;
  ComGoogleCommonCollectImmutableSortedSet *descendingSet__;
}

+ (id<JavaUtilComparator>)NATURAL_ORDER;
+ (ComGoogleCommonCollectImmutableSortedSet *)NATURAL_EMPTY_SET;
+ (ComGoogleCommonCollectImmutableSortedSet *)emptySet;
+ (ComGoogleCommonCollectImmutableSortedSet *)emptySetWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (ComGoogleCommonCollectImmutableSortedSet *)of;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)element;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)e1
                                                withId:(id<JavaLangComparable>)e2;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)e1
                                                withId:(id<JavaLangComparable>)e2
                                                withId:(id<JavaLangComparable>)e3;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)e1
                                                withId:(id<JavaLangComparable>)e2
                                                withId:(id<JavaLangComparable>)e3
                                                withId:(id<JavaLangComparable>)e4;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)e1
                                                withId:(id<JavaLangComparable>)e2
                                                withId:(id<JavaLangComparable>)e3
                                                withId:(id<JavaLangComparable>)e4
                                                withId:(id<JavaLangComparable>)e5;
+ (ComGoogleCommonCollectImmutableSortedSet *)ofWithId:(id<JavaLangComparable>)e1
                                                withId:(id<JavaLangComparable>)e2
                                                withId:(id<JavaLangComparable>)e3
                                                withId:(id<JavaLangComparable>)e4
                                                withId:(id<JavaLangComparable>)e5
                                                withId:(id<JavaLangComparable>)e6
                           withJavaLangComparableArray:(IOSObjectArray *)remaining;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaLangComparableArray:(IOSObjectArray *)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaUtilCollection:(id<JavaUtilCollection>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaUtilIterator:(id<JavaUtilIterator>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                      withJavaUtilIterator:(id<JavaUtilIterator>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                      withJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                    withJavaUtilCollection:(id<JavaUtilCollection>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableSortedSet *)copyOfSortedWithJavaUtilSortedSet:(id<JavaUtilSortedSet>)sortedSet OBJC_METHOD_FAMILY_NONE;
+ (int)sortAndUniqueWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                   withInt:(int)n
                         withNSObjectArray:(IOSObjectArray *)contents;
+ (ComGoogleCommonCollectImmutableSortedSet *)constructWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                                                      withInt:(int)n
                                                            withNSObjectArray:(IOSObjectArray *)contents;
+ (ComGoogleCommonCollectImmutableSortedSet_Builder *)orderedByWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (ComGoogleCommonCollectImmutableSortedSet_Builder *)reverseOrder;
+ (ComGoogleCommonCollectImmutableSortedSet_Builder *)naturalOrder;
- (int)unsafeCompareWithId:(id)a
                    withId:(id)b;
+ (int)unsafeCompareWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                    withId:(id)a
                                    withId:(id)b;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilComparator>)comparator;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (ComGoogleCommonCollectImmutableSortedSet *)headSetWithId:(id)toElement;
- (ComGoogleCommonCollectImmutableSortedSet *)headSetWithId:(id)toElement
                                                withBoolean:(BOOL)inclusive;
- (ComGoogleCommonCollectImmutableSortedSet *)subSetWithId:(id)fromElement
                                                    withId:(id)toElement;
- (ComGoogleCommonCollectImmutableSortedSet *)subSetWithId:(id)fromElement
                                               withBoolean:(BOOL)fromInclusive
                                                    withId:(id)toElement
                                               withBoolean:(BOOL)toInclusive;
- (ComGoogleCommonCollectImmutableSortedSet *)tailSetWithId:(id)fromElement;
- (ComGoogleCommonCollectImmutableSortedSet *)tailSetWithId:(id)fromElement
                                                withBoolean:(BOOL)inclusive;
- (ComGoogleCommonCollectImmutableSortedSet *)headSetImplWithId:(id)toElement
                                                    withBoolean:(BOOL)inclusive;
- (ComGoogleCommonCollectImmutableSortedSet *)subSetImplWithId:(id)fromElement
                                                   withBoolean:(BOOL)fromInclusive
                                                        withId:(id)toElement
                                                   withBoolean:(BOOL)toInclusive;
- (ComGoogleCommonCollectImmutableSortedSet *)tailSetImplWithId:(id)fromElement
                                                    withBoolean:(BOOL)inclusive;
- (id)lowerWithId:(id)e;
- (id)floorWithId:(id)e;
- (id)ceilingWithId:(id)e;
- (id)higherWithId:(id)e;
- (id)first;
- (id)last;
- (id)pollFirst;
- (id)pollLast;
- (ComGoogleCommonCollectImmutableSortedSet *)descendingSet;
- (ComGoogleCommonCollectImmutableSortedSet *)createDescendingSet;
- (ComGoogleCommonCollectUnmodifiableIterator *)descendingIterator;
- (int)indexOfWithId:(id)target;
- (id)writeReplace;
- (int)size;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableSortedSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableSortedSet, comparator__, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableSortedSet, descendingSet__, ComGoogleCommonCollectImmutableSortedSet *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableSortedSet_Builder_) && (ComGoogleCommonCollectImmutableSortedSet_INCLUDE_ALL || ComGoogleCommonCollectImmutableSortedSet_Builder_INCLUDE)
#define _ComGoogleCommonCollectImmutableSortedSet_Builder_

@class ComGoogleCommonCollectImmutableSortedSet;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectImmutableSet_RESTRICT 1
#define ComGoogleCommonCollectImmutableSet_Builder_INCLUDE 1
#include "com/google/common/collect/ImmutableSet.h"

@interface ComGoogleCommonCollectImmutableSortedSet_Builder : ComGoogleCommonCollectImmutableSet_Builder {
 @public
  id<JavaUtilComparator> comparator_;
}

- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (ComGoogleCommonCollectImmutableSortedSet_Builder *)addWithId:(id)element;
- (ComGoogleCommonCollectImmutableSortedSet_Builder *)addWithNSObjectArray:(IOSObjectArray *)elements;
- (ComGoogleCommonCollectImmutableSortedSet_Builder *)addAllWithJavaLangIterable:(id<JavaLangIterable>)elements;
- (ComGoogleCommonCollectImmutableSortedSet_Builder *)addAllWithJavaUtilIterator:(id<JavaUtilIterator>)elements;
- (ComGoogleCommonCollectImmutableSortedSet *)build;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableSortedSet_Builder *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableSortedSet_Builder, comparator_, id<JavaUtilComparator>)
#endif

#if !defined (_ComGoogleCommonCollectImmutableSortedSet_SerializedForm_) && (ComGoogleCommonCollectImmutableSortedSet_INCLUDE_ALL || ComGoogleCommonCollectImmutableSortedSet_SerializedForm_INCLUDE)
#define _ComGoogleCommonCollectImmutableSortedSet_SerializedForm_

@class IOSObjectArray;
@protocol JavaUtilComparator;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectImmutableSortedSet_SerializedForm_serialVersionUID 0

@interface ComGoogleCommonCollectImmutableSortedSet_SerializedForm : NSObject < JavaIoSerializable > {
 @public
  id<JavaUtilComparator> comparator_;
  IOSObjectArray *elements_;
}

- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator
               withNSObjectArray:(IOSObjectArray *)elements;
- (id)readResolve;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableSortedSet_SerializedForm *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableSortedSet_SerializedForm, comparator_, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableSortedSet_SerializedForm, elements_, IOSObjectArray *)
#endif