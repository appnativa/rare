//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/FluentIterable.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectFluentIterable_RESTRICT
#define ComGoogleCommonCollectFluentIterable_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectFluentIterable_RESTRICT
#if ComGoogleCommonCollectFluentIterable_$1_INCLUDE
#define ComGoogleCommonCollectFluentIterable_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectFluentIterable_FromIterableFunction_) && (ComGoogleCommonCollectFluentIterable_INCLUDE_ALL || ComGoogleCommonCollectFluentIterable_FromIterableFunction_INCLUDE)
#define _ComGoogleCommonCollectFluentIterable_FromIterableFunction_

@class ComGoogleCommonCollectFluentIterable;
@protocol JavaLangIterable;

#define ComGoogleCommonBaseFunction_RESTRICT 1
#define ComGoogleCommonBaseFunction_INCLUDE 1
#include "com/google/common/base/Function.h"

@interface ComGoogleCommonCollectFluentIterable_FromIterableFunction : NSObject < ComGoogleCommonBaseFunction > {
}

- (ComGoogleCommonCollectFluentIterable *)applyWithId:(id<JavaLangIterable>)fromObject;
- (BOOL)isEqual:(id)param0;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectFluentIterable_) && (ComGoogleCommonCollectFluentIterable_INCLUDE_ALL || ComGoogleCommonCollectFluentIterable_INCLUDE)
#define _ComGoogleCommonCollectFluentIterable_

@class ComGoogleCommonBaseOptional;
@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectImmutableListMultimap;
@class ComGoogleCommonCollectImmutableMap;
@class ComGoogleCommonCollectImmutableSet;
@class ComGoogleCommonCollectImmutableSortedSet;
@class IOSClass;
@class IOSObjectArray;
@protocol ComGoogleCommonBaseFunction;
@protocol ComGoogleCommonBasePredicate;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;

#define JavaLangIterable_RESTRICT 1
#define JavaLangIterable_INCLUDE 1
#include "java/lang/Iterable.h"

@interface ComGoogleCommonCollectFluentIterable : NSObject < JavaLangIterable > {
 @public
  id<JavaLangIterable> iterable_;
}

- (id)init;
- (id)initWithJavaLangIterable:(id<JavaLangIterable>)iterable;
+ (ComGoogleCommonCollectFluentIterable *)fromWithJavaLangIterable:(id<JavaLangIterable>)iterable;
+ (ComGoogleCommonCollectFluentIterable *)fromWithComGoogleCommonCollectFluentIterable:(ComGoogleCommonCollectFluentIterable *)iterable;
- (NSString *)description;
- (int)size;
- (BOOL)containsWithId:(id)element;
- (ComGoogleCommonCollectFluentIterable *)cycle;
- (ComGoogleCommonCollectFluentIterable *)filterWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (ComGoogleCommonCollectFluentIterable *)filterWithIOSClass:(IOSClass *)type;
- (BOOL)anyMatchWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (BOOL)allMatchWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (ComGoogleCommonBaseOptional *)firstMatchWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (ComGoogleCommonCollectFluentIterable *)transformWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (ComGoogleCommonCollectFluentIterable *)transformAndConcatWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (ComGoogleCommonBaseOptional *)first;
- (ComGoogleCommonBaseOptional *)last;
- (ComGoogleCommonCollectFluentIterable *)skipWithInt:(int)numberToSkip;
- (ComGoogleCommonCollectFluentIterable *)limitWithInt:(int)size;
- (BOOL)isEmpty;
- (ComGoogleCommonCollectImmutableList *)toList;
- (ComGoogleCommonCollectImmutableList *)toSortedListWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (ComGoogleCommonCollectImmutableSet *)toSet;
- (ComGoogleCommonCollectImmutableSortedSet *)toSortedSetWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (ComGoogleCommonCollectImmutableMap *)toMapWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)valueFunction;
- (ComGoogleCommonCollectImmutableListMultimap *)indexWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)keyFunction;
- (ComGoogleCommonCollectImmutableMap *)uniqueIndexWithComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)keyFunction;
- (ComGoogleCommonCollectImmutableList *)toImmutableList;
- (ComGoogleCommonCollectImmutableList *)toSortedImmutableListWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (ComGoogleCommonCollectImmutableSet *)toImmutableSet;
- (ComGoogleCommonCollectImmutableSortedSet *)toImmutableSortedSetWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (IOSObjectArray *)toArrayWithIOSClass:(IOSClass *)type;
- (id)copyIntoWithId:(id<JavaUtilCollection>)collection OBJC_METHOD_FAMILY_NONE;
- (id)getWithInt:(int)position;
- (id<JavaUtilIterator>)iterator;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectFluentIterable *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectFluentIterable, iterable_, id<JavaLangIterable>)
#endif

#if !defined (_ComGoogleCommonCollectFluentIterable_$1_) && (ComGoogleCommonCollectFluentIterable_INCLUDE_ALL || ComGoogleCommonCollectFluentIterable_$1_INCLUDE)
#define _ComGoogleCommonCollectFluentIterable_$1_

@protocol JavaLangIterable;
@protocol JavaUtilIterator;

@interface ComGoogleCommonCollectFluentIterable_$1 : ComGoogleCommonCollectFluentIterable {
 @public
  id<JavaLangIterable> val$iterable_;
}

- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaLangIterable:(id<JavaLangIterable>)arg$0
          withJavaLangIterable:(id<JavaLangIterable>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectFluentIterable_$1, val$iterable_, id<JavaLangIterable>)
#endif