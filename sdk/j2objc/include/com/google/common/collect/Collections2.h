//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/Collections2.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectCollections2_RESTRICT
#define ComGoogleCommonCollectCollections2_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectCollections2_RESTRICT

#if !defined (_ComGoogleCommonCollectCollections2_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_INCLUDE)
#define _ComGoogleCommonCollectCollections2_

@class ComGoogleCommonBaseJoiner;
@class JavaLangStringBuilder;
@protocol ComGoogleCommonBaseFunction;
@protocol ComGoogleCommonBasePredicate;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;

@interface ComGoogleCommonCollectCollections2 : NSObject {
}

+ (ComGoogleCommonBaseJoiner *)STANDARD_JOINER;
- (id)init;
+ (id<JavaUtilCollection>)filterWithJavaUtilCollection:(id<JavaUtilCollection>)unfiltered
                      withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
+ (BOOL)safeContainsWithJavaUtilCollection:(id<JavaUtilCollection>)collection
                                    withId:(id)object;
+ (BOOL)safeRemoveWithJavaUtilCollection:(id<JavaUtilCollection>)collection
                                  withId:(id)object;
+ (id<JavaUtilCollection>)transformWithJavaUtilCollection:(id<JavaUtilCollection>)fromCollection
                          withComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
+ (BOOL)containsAllImplWithJavaUtilCollection:(id<JavaUtilCollection>)self_
                       withJavaUtilCollection:(id<JavaUtilCollection>)c;
+ (NSString *)toStringImplWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
+ (JavaLangStringBuilder *)newStringBuilderForCollectionWithInt:(int)size OBJC_METHOD_FAMILY_NONE;
+ (id<JavaUtilCollection>)castWithJavaLangIterable:(id<JavaLangIterable>)iterable;
+ (id<JavaUtilCollection>)orderedPermutationsWithJavaLangIterable:(id<JavaLangIterable>)elements;
+ (id<JavaUtilCollection>)orderedPermutationsWithJavaLangIterable:(id<JavaLangIterable>)elements
                                           withJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (id<JavaUtilCollection>)permutationsWithJavaUtilCollection:(id<JavaUtilCollection>)elements;
+ (BOOL)isPermutationWithJavaUtilList:(id<JavaUtilList>)first
                     withJavaUtilList:(id<JavaUtilList>)second;
+ (BOOL)isPositiveIntWithLong:(long long int)n;
@end
#endif

#if !defined (_ComGoogleCommonCollectCollections2_FilteredCollection_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_FilteredCollection_INCLUDE)
#define _ComGoogleCommonCollectCollections2_FilteredCollection_

@class IOSObjectArray;
@protocol ComGoogleCommonBasePredicate;
@protocol JavaUtilIterator;

#define JavaUtilCollection_RESTRICT 1
#define JavaUtilCollection_INCLUDE 1
#include "java/util/Collection.h"

@interface ComGoogleCommonCollectCollections2_FilteredCollection : NSObject < JavaUtilCollection > {
 @public
  id<JavaUtilCollection> unfiltered_;
  id<ComGoogleCommonBasePredicate> predicate_;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)unfiltered
withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (ComGoogleCommonCollectCollections2_FilteredCollection *)createCombinedWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)newPredicate;
- (BOOL)addWithId:(id)element;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)clear;
- (BOOL)containsWithId:(id)element;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (BOOL)removeWithId:(id)element;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (int)size;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_FilteredCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection, unfiltered_, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection, predicate_, id<ComGoogleCommonBasePredicate>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_FilteredCollection_$1_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_FilteredCollection_$1_INCLUDE)
#define _ComGoogleCommonCollectCollections2_FilteredCollection_$1_

@class ComGoogleCommonCollectCollections2_FilteredCollection;
@protocol JavaUtilCollection;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

@interface ComGoogleCommonCollectCollections2_FilteredCollection_$1 : NSObject < ComGoogleCommonBasePredicate > {
 @public
  ComGoogleCommonCollectCollections2_FilteredCollection *this$0_;
  id<JavaUtilCollection> val$collection_;
}

- (BOOL)applyWithId:(id)input;
- (BOOL)isEqual:(id)param0;
- (id)initWithComGoogleCommonCollectCollections2_FilteredCollection:(ComGoogleCommonCollectCollections2_FilteredCollection *)outer$
                                             withJavaUtilCollection:(id<JavaUtilCollection>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection_$1, this$0_, ComGoogleCommonCollectCollections2_FilteredCollection *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection_$1, val$collection_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_FilteredCollection_$2_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_FilteredCollection_$2_INCLUDE)
#define _ComGoogleCommonCollectCollections2_FilteredCollection_$2_

@class ComGoogleCommonCollectCollections2_FilteredCollection;
@protocol JavaUtilCollection;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

@interface ComGoogleCommonCollectCollections2_FilteredCollection_$2 : NSObject < ComGoogleCommonBasePredicate > {
 @public
  ComGoogleCommonCollectCollections2_FilteredCollection *this$0_;
  id<JavaUtilCollection> val$collection_;
}

- (BOOL)applyWithId:(id)input;
- (BOOL)isEqual:(id)param0;
- (id)initWithComGoogleCommonCollectCollections2_FilteredCollection:(ComGoogleCommonCollectCollections2_FilteredCollection *)outer$
                                             withJavaUtilCollection:(id<JavaUtilCollection>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection_$2, this$0_, ComGoogleCommonCollectCollections2_FilteredCollection *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_FilteredCollection_$2, val$collection_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_TransformedCollection_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_TransformedCollection_INCLUDE)
#define _ComGoogleCommonCollectCollections2_TransformedCollection_

@protocol ComGoogleCommonBaseFunction;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define JavaUtilAbstractCollection_RESTRICT 1
#define JavaUtilAbstractCollection_INCLUDE 1
#include "java/util/AbstractCollection.h"

@interface ComGoogleCommonCollectCollections2_TransformedCollection : JavaUtilAbstractCollection {
 @public
  id<JavaUtilCollection> fromCollection_;
  id<ComGoogleCommonBaseFunction> function_;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)fromCollection
 withComGoogleCommonBaseFunction:(id<ComGoogleCommonBaseFunction>)function;
- (void)clear;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (int)size;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_TransformedCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_TransformedCollection, fromCollection_, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_TransformedCollection, function_, id<ComGoogleCommonBaseFunction>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_OrderedPermutationCollection_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_OrderedPermutationCollection_INCLUDE)
#define _ComGoogleCommonCollectCollections2_OrderedPermutationCollection_

@class ComGoogleCommonCollectImmutableList;
@protocol JavaLangIterable;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilList;

#define JavaUtilAbstractCollection_RESTRICT 1
#define JavaUtilAbstractCollection_INCLUDE 1
#include "java/util/AbstractCollection.h"

@interface ComGoogleCommonCollectCollections2_OrderedPermutationCollection : JavaUtilAbstractCollection {
 @public
  ComGoogleCommonCollectImmutableList *inputList_;
  id<JavaUtilComparator> comparator_;
  int size__;
}

- (id)initWithJavaLangIterable:(id<JavaLangIterable>)input
        withJavaUtilComparator:(id<JavaUtilComparator>)comparator;
+ (int)calculateSizeWithJavaUtilList:(id<JavaUtilList>)sortedInputList
              withJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (int)size;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (BOOL)containsWithId:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_OrderedPermutationCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_OrderedPermutationCollection, inputList_, ComGoogleCommonCollectImmutableList *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_OrderedPermutationCollection, comparator_, id<JavaUtilComparator>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_OrderedPermutationIterator_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_OrderedPermutationIterator_INCLUDE)
#define _ComGoogleCommonCollectCollections2_OrderedPermutationIterator_

@protocol JavaUtilComparator;
@protocol JavaUtilList;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectCollections2_OrderedPermutationIterator : ComGoogleCommonCollectAbstractIterator {
 @public
  id<JavaUtilList> nextPermutation_;
  id<JavaUtilComparator> comparator_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)list
    withJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilList>)computeNext;
- (void)calculateNextPermutation;
- (int)findNextJ;
- (int)findNextLWithInt:(int)j;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_OrderedPermutationIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_OrderedPermutationIterator, nextPermutation_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_OrderedPermutationIterator, comparator_, id<JavaUtilComparator>)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_PermutationCollection_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_PermutationCollection_INCLUDE)
#define _ComGoogleCommonCollectCollections2_PermutationCollection_

@class ComGoogleCommonCollectImmutableList;
@protocol JavaUtilIterator;

#define JavaUtilAbstractCollection_RESTRICT 1
#define JavaUtilAbstractCollection_INCLUDE 1
#include "java/util/AbstractCollection.h"

@interface ComGoogleCommonCollectCollections2_PermutationCollection : JavaUtilAbstractCollection {
 @public
  ComGoogleCommonCollectImmutableList *inputList_;
}

- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)input;
- (int)size;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (BOOL)containsWithId:(id)obj;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_PermutationCollection *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_PermutationCollection, inputList_, ComGoogleCommonCollectImmutableList *)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_PermutationIterator_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_PermutationIterator_INCLUDE)
#define _ComGoogleCommonCollectCollections2_PermutationIterator_

@class IOSIntArray;
@protocol JavaUtilList;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectCollections2_PermutationIterator : ComGoogleCommonCollectAbstractIterator {
 @public
  id<JavaUtilList> list_;
  IOSIntArray *c_;
  IOSIntArray *o_;
  int j_;
}

- (id)initWithJavaUtilList:(id<JavaUtilList>)list;
- (id<JavaUtilList>)computeNext;
- (void)calculateNextPermutation;
- (void)switchDirection;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCollections2_PermutationIterator *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_PermutationIterator, list_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_PermutationIterator, c_, IOSIntArray *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_PermutationIterator, o_, IOSIntArray *)
#endif

#if !defined (_ComGoogleCommonCollectCollections2_$1_) && (ComGoogleCommonCollectCollections2_INCLUDE_ALL || ComGoogleCommonCollectCollections2_$1_INCLUDE)
#define _ComGoogleCommonCollectCollections2_$1_

@protocol JavaUtilCollection;

#define ComGoogleCommonBaseFunction_RESTRICT 1
#define ComGoogleCommonBaseFunction_INCLUDE 1
#include "com/google/common/base/Function.h"

@interface ComGoogleCommonCollectCollections2_$1 : NSObject < ComGoogleCommonBaseFunction > {
 @public
  id<JavaUtilCollection> val$collection_;
}

- (id)applyWithId:(id)input;
- (BOOL)isEqual:(id)param0;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCollections2_$1, val$collection_, id<JavaUtilCollection>)
#endif