//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/Ordering.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectOrdering_RESTRICT
#define ComGoogleCommonCollectOrdering_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectOrdering_RESTRICT
#if ComGoogleCommonCollectOrdering_ArbitraryOrdering_INCLUDE
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectOrdering_ArbitraryOrderingHolder_) && (ComGoogleCommonCollectOrdering_INCLUDE_ALL || ComGoogleCommonCollectOrdering_ArbitraryOrderingHolder_INCLUDE)
#define _ComGoogleCommonCollectOrdering_ArbitraryOrderingHolder_

@class ComGoogleCommonCollectOrdering;

@interface ComGoogleCommonCollectOrdering_ArbitraryOrderingHolder : NSObject {
}

+ (ComGoogleCommonCollectOrdering *)ARBITRARY_ORDERING;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectOrdering_) && (ComGoogleCommonCollectOrdering_INCLUDE_ALL || ComGoogleCommonCollectOrdering_INCLUDE)
#define _ComGoogleCommonCollectOrdering_

@class ComGoogleCommonCollectImmutableList;
@class IOSObjectArray;
@protocol ComGoogleCommonBaseFunction;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;
@protocol JavaUtilList;

#define JavaUtilComparator_RESTRICT 1
#define JavaUtilComparator_INCLUDE 1
#include "java/util/Comparator.h"

#define ComGoogleCommonCollectOrdering_LEFT_IS_GREATER 1
#define ComGoogleCommonCollectOrdering_RIGHT_IS_GREATER -1

@interface ComGoogleCommonCollectOrdering : NSObject < JavaUtilComparator > {
}

+ (int)LEFT_IS_GREATER;
+ (int)RIGHT_IS_GREATER;
+ (ComGoogleCommonCollectOrdering *)natural;
+ (ComGoogleCommonCollectOrdering *)fromWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (ComGoogleCommonCollectOrdering *)fromWithComGoogleCommonCollectOrdering:(ComGoogleCommonCollectOrdering *)ordering;
+ (ComGoogleCommonCollectOrdering *)explicit__WithJavaUtilList:(id<JavaUtilList>)valuesInOrder;
+ (ComGoogleCommonCollectOrdering *)explicit__WithId:(id)leastValue
                                   withNSObjectArray:(IOSObjectArray *)remainingValuesInOrder;
+ (ComGoogleCommonCollectOrdering *)allEqual;
+ (ComGoogleCommonCollectOrdering *)usingToString;
+ (ComGoogleCommonCollectOrdering *)arbitrary;
- (id)init;
- (ComGoogleCommonCollectOrdering *)reverse;
- (ComGoogleCommonCollectOrdering *)nullsFirst;
- (ComGoogleCommonCollectOrdering *)nullsLast;
- (ComGoogleCommonCollectOrdering *)onResultOfWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (ComGoogleCommonCollectOrdering *)compoundWithJavaUtilComparator:(id<JavaUtilComparator>)secondaryComparator;
+ (ComGoogleCommonCollectOrdering *)compoundWithJavaLangIterable:(id<JavaLangIterable>)comparators;
- (ComGoogleCommonCollectOrdering *)lexicographical;
- (int)compareWithId:(id)left
              withId:(id)right;
- (id)minWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;
- (id)minWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (id)minWithId:(id)a
         withId:(id)b;
- (id)minWithId:(id)a
         withId:(id)b
         withId:(id)c
withNSObjectArray:(IOSObjectArray *)rest;
- (id)maxWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;
- (id)maxWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (id)maxWithId:(id)a
         withId:(id)b;
- (id)maxWithId:(id)a
         withId:(id)b
         withId:(id)c
withNSObjectArray:(IOSObjectArray *)rest;
- (id<JavaUtilList>)leastOfWithJavaLangIterable:(id<JavaLangIterable>)iterable
                                        withInt:(int)k;
- (id<JavaUtilList>)leastOfWithJavaUtilIterator:(id<JavaUtilIterator>)elements
                                        withInt:(int)k;
- (int)partitionWithNSObjectArray:(IOSObjectArray *)values
                          withInt:(int)left
                          withInt:(int)right
                          withInt:(int)pivotIndex;
- (id<JavaUtilList>)greatestOfWithJavaLangIterable:(id<JavaLangIterable>)iterable
                                           withInt:(int)k;
- (id<JavaUtilList>)greatestOfWithJavaUtilIterator:(id<JavaUtilIterator>)iterator
                                           withInt:(int)k;
- (id<JavaUtilList>)sortedCopyWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (ComGoogleCommonCollectImmutableList *)immutableSortedCopyWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (BOOL)isOrderedWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (BOOL)isStrictlyOrderedWithJavaLangIterable:(id<JavaLangIterable>)iterable;
- (int)binarySearchWithJavaUtilList:(id<JavaUtilList>)sortedList
                             withId:(id)key;
@end
#endif

#if !defined (_ComGoogleCommonCollectOrdering_ArbitraryOrdering_) && (ComGoogleCommonCollectOrdering_INCLUDE_ALL || ComGoogleCommonCollectOrdering_ArbitraryOrdering_INCLUDE)
#define _ComGoogleCommonCollectOrdering_ArbitraryOrdering_

@protocol JavaUtilMap;

@interface ComGoogleCommonCollectOrdering_ArbitraryOrdering : ComGoogleCommonCollectOrdering {
 @public
  id<JavaUtilMap> uids_;
}

- (int)compareWithId:(id)left
              withId:(id)right;
- (NSString *)description;
- (int)identityHashCodeWithId:(id)object;
- (id)init;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectOrdering_ArbitraryOrdering *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectOrdering_ArbitraryOrdering, uids_, id<JavaUtilMap>)
#endif

#if !defined (_ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1_) && (ComGoogleCommonCollectOrdering_INCLUDE_ALL || ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1_INCLUDE)
#define _ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1_

@class JavaLangInteger;
@class JavaUtilConcurrentAtomicAtomicInteger;

#define ComGoogleCommonBaseFunction_RESTRICT 1
#define ComGoogleCommonBaseFunction_INCLUDE 1
#include "com/google/common/base/Function.h"

@interface ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1 : NSObject < ComGoogleCommonBaseFunction > {
 @public
  JavaUtilConcurrentAtomicAtomicInteger *counter_;
}

- (JavaLangInteger *)applyWithId:(id)from;
- (BOOL)isEqual:(id)param0;
- (id)init;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectOrdering_ArbitraryOrdering_$1, counter_, JavaUtilConcurrentAtomicAtomicInteger *)
#endif

#if !defined (_ComGoogleCommonCollectOrdering_IncomparableValueException_) && (ComGoogleCommonCollectOrdering_INCLUDE_ALL || ComGoogleCommonCollectOrdering_IncomparableValueException_INCLUDE)
#define _ComGoogleCommonCollectOrdering_IncomparableValueException_

#define JavaLangClassCastException_RESTRICT 1
#define JavaLangClassCastException_INCLUDE 1
#include "java/lang/ClassCastException.h"

#define ComGoogleCommonCollectOrdering_IncomparableValueException_serialVersionUID 0

@interface ComGoogleCommonCollectOrdering_IncomparableValueException : JavaLangClassCastException {
 @public
  id value_;
}

- (id)initWithId:(id)value;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectOrdering_IncomparableValueException *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectOrdering_IncomparableValueException, value_, id)
#endif
