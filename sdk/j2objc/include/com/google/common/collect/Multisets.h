//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/Multisets.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectMultisets_RESTRICT
#define ComGoogleCommonCollectMultisets_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectMultisets_RESTRICT
#if ComGoogleCommonCollectMultisets_ImmutableEntry_INCLUDE
#define ComGoogleCommonCollectMultisets_AbstractEntry_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectMultisets_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_INCLUDE)
#define _ComGoogleCommonCollectMultisets_

@class ComGoogleCommonCollectImmutableMultiset;
@class ComGoogleCommonCollectOrdering;
@protocol ComGoogleCommonBasePredicate;
@protocol ComGoogleCommonCollectMultiset;
@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol ComGoogleCommonCollectSortedMultiset;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

@interface ComGoogleCommonCollectMultisets : NSObject {
}

+ (ComGoogleCommonCollectOrdering *)DECREASING_COUNT_ORDERING;
- (id)init;
+ (id<ComGoogleCommonCollectMultiset>)unmodifiableMultisetWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset;
+ (id<ComGoogleCommonCollectMultiset>)unmodifiableMultisetWithComGoogleCommonCollectImmutableMultiset:(ComGoogleCommonCollectImmutableMultiset *)multiset;
+ (id<ComGoogleCommonCollectSortedMultiset>)unmodifiableSortedMultisetWithComGoogleCommonCollectSortedMultiset:(id<ComGoogleCommonCollectSortedMultiset>)sortedMultiset;
+ (id<ComGoogleCommonCollectMultiset_Entry>)immutableEntryWithId:(id)e
                                                         withInt:(int)n;
+ (id<ComGoogleCommonCollectMultiset>)filterWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)unfiltered
                                              withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
+ (int)inferDistinctElementsWithJavaLangIterable:(id<JavaLangIterable>)elements;
+ (id<ComGoogleCommonCollectMultiset>)union__WithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset1
                                             withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset2;
+ (id<ComGoogleCommonCollectMultiset>)intersectionWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset1
                                                  withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset2;
+ (id<ComGoogleCommonCollectMultiset>)sumWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset1
                                         withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset2;
+ (id<ComGoogleCommonCollectMultiset>)differenceWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset1
                                                withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset2;
+ (BOOL)containsOccurrencesWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)superMultiset
                           withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)subMultiset;
+ (BOOL)retainOccurrencesWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multisetToModify
                         withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multisetToRetain;
+ (BOOL)retainOccurrencesImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multisetToModify
                             withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)occurrencesToRetain;
+ (BOOL)removeOccurrencesWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multisetToModify
                         withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)occurrencesToRemove;
+ (BOOL)removeOccurrencesImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multisetToModify
                             withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)occurrencesToRemove;
+ (BOOL)equalsImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset
                                              withId:(id)object;
+ (BOOL)addAllImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)self_
                              withJavaUtilCollection:(id<JavaUtilCollection>)elements;
+ (BOOL)removeAllImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)self_
                                 withJavaUtilCollection:(id<JavaUtilCollection>)elementsToRemove;
+ (BOOL)retainAllImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)self_
                                 withJavaUtilCollection:(id<JavaUtilCollection>)elementsToRetain;
+ (int)setCountImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)self_
                                               withId:(id)element
                                              withInt:(int)count;
+ (BOOL)setCountImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)self_
                                                withId:(id)element
                                               withInt:(int)oldCount
                                               withInt:(int)newCount;
+ (id<JavaUtilIterator>)iteratorImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset;
+ (int)sizeImplWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset;
+ (void)checkNonnegativeWithInt:(int)count
                   withNSString:(NSString *)name;
+ (id<ComGoogleCommonCollectMultiset>)castWithJavaLangIterable:(id<JavaLangIterable>)iterable;
+ (ComGoogleCommonCollectImmutableMultiset *)copyHighestCountFirstWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset OBJC_METHOD_FAMILY_NONE;
@end
#endif

#if !defined (_ComGoogleCommonCollectMultisets_UnmodifiableMultiset_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_UnmodifiableMultiset_INCLUDE)
#define _ComGoogleCommonCollectMultisets_UnmodifiableMultiset_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingMultiset_RESTRICT 1
#define ComGoogleCommonCollectForwardingMultiset_INCLUDE 1
#include "com/google/common/collect/ForwardingMultiset.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectMultisets_UnmodifiableMultiset_serialVersionUID 0

@interface ComGoogleCommonCollectMultisets_UnmodifiableMultiset : ComGoogleCommonCollectForwardingMultiset < JavaIoSerializable > {
 @public
  id<ComGoogleCommonCollectMultiset> delegate__;
  id<JavaUtilSet> elementSet__;
  id<JavaUtilSet> entrySet__;
}

- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)delegate;
- (id<ComGoogleCommonCollectMultiset>)delegate;
- (id<JavaUtilSet>)createElementSet;
- (id<JavaUtilSet>)elementSet;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilIterator>)iterator;
- (BOOL)addWithId:(id)element;
- (int)addWithId:(id)element
         withInt:(int)occurences;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)elementsToAdd;
- (BOOL)removeWithId:(id)element;
- (int)removeWithId:(id)element
            withInt:(int)occurrences;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)elementsToRemove;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)elementsToRetain;
- (void)clear;
- (int)setCountWithId:(id)element
              withInt:(int)count;
- (BOOL)setCountWithId:(id)element
               withInt:(int)oldCount
               withInt:(int)newCount;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMultisets_UnmodifiableMultiset *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_UnmodifiableMultiset, delegate__, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_UnmodifiableMultiset, elementSet__, id<JavaUtilSet>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_UnmodifiableMultiset, entrySet__, id<JavaUtilSet>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_AbstractEntry_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_AbstractEntry_INCLUDE)
#define _ComGoogleCommonCollectMultisets_AbstractEntry_

#define ComGoogleCommonCollectMultiset_RESTRICT 1
#define ComGoogleCommonCollectMultiset_Entry_INCLUDE 1
#include "com/google/common/collect/Multiset.h"

@interface ComGoogleCommonCollectMultisets_AbstractEntry : NSObject < ComGoogleCommonCollectMultiset_Entry > {
}

- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (NSString *)description;
- (int)getCount;
- (id)getElement;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectMultisets_ImmutableEntry_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_ImmutableEntry_INCLUDE)
#define _ComGoogleCommonCollectMultisets_ImmutableEntry_

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectMultisets_ImmutableEntry_serialVersionUID 0

@interface ComGoogleCommonCollectMultisets_ImmutableEntry : ComGoogleCommonCollectMultisets_AbstractEntry < JavaIoSerializable > {
 @public
  id element_;
  int count_;
}

- (id)initWithId:(id)element
         withInt:(int)count;
- (id)getElement;
- (int)getCount;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMultisets_ImmutableEntry *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_ImmutableEntry, element_, id)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_FilteredMultiset_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_FilteredMultiset_INCLUDE)
#define _ComGoogleCommonCollectMultisets_FilteredMultiset_

@protocol ComGoogleCommonBasePredicate;
@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMultiset.h"

@interface ComGoogleCommonCollectMultisets_FilteredMultiset : ComGoogleCommonCollectAbstractMultiset {
 @public
  id<ComGoogleCommonCollectMultiset> unfiltered_;
  id<ComGoogleCommonBasePredicate> predicate_;
}

- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)unfiltered
            withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (id<JavaUtilSet>)createElementSet;
- (id<JavaUtilSet>)createEntrySet;
- (id<JavaUtilIterator>)entryIterator;
- (int)distinctElements;
- (BOOL)containsWithId:(id)element;
- (int)countWithId:(id)element;
- (int)addWithId:(id)element
         withInt:(int)occurrences;
- (int)removeWithId:(id)element
            withInt:(int)occurrences;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)clear;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMultisets_FilteredMultiset *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_FilteredMultiset, unfiltered_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_FilteredMultiset, predicate_, id<ComGoogleCommonBasePredicate>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_FilteredMultiset_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_FilteredMultiset_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_FilteredMultiset_$1_

@class ComGoogleCommonCollectMultisets_FilteredMultiset;
@protocol ComGoogleCommonCollectMultiset_Entry;

#define ComGoogleCommonBasePredicate_RESTRICT 1
#define ComGoogleCommonBasePredicate_INCLUDE 1
#include "com/google/common/base/Predicate.h"

@interface ComGoogleCommonCollectMultisets_FilteredMultiset_$1 : NSObject < ComGoogleCommonBasePredicate > {
 @public
  ComGoogleCommonCollectMultisets_FilteredMultiset *this$0_;
}

- (BOOL)applyWithId:(id<ComGoogleCommonCollectMultiset_Entry>)entry_;
- (BOOL)isEqual:(id)param0;
- (id)initWithComGoogleCommonCollectMultisets_FilteredMultiset:(ComGoogleCommonCollectMultisets_FilteredMultiset *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_FilteredMultiset_$1, this$0_, ComGoogleCommonCollectMultisets_FilteredMultiset *)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_ElementSet_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_ElementSet_INCLUDE)
#define _ComGoogleCommonCollectMultisets_ElementSet_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectSets_RESTRICT 1
#define ComGoogleCommonCollectSets_ImprovedAbstractSet_INCLUDE 1
#include "com/google/common/collect/Sets.h"

@interface ComGoogleCommonCollectMultisets_ElementSet : ComGoogleCommonCollectSets_ImprovedAbstractSet {
}

- (id<ComGoogleCommonCollectMultiset>)multiset;
- (void)clear;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (BOOL)removeWithId:(id)o;
- (int)size;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectMultisets_ElementSet_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_ElementSet_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_ElementSet_$1_

@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectTransformedIterator_RESTRICT 1
#define ComGoogleCommonCollectTransformedIterator_INCLUDE 1
#include "com/google/common/collect/TransformedIterator.h"

@interface ComGoogleCommonCollectMultisets_ElementSet_$1 : ComGoogleCommonCollectTransformedIterator {
}

- (id)transformWithId:(id<ComGoogleCommonCollectMultiset_Entry>)entry_;
- (id)initWithJavaUtilIterator:(id<JavaUtilIterator>)arg$0;
@end
#endif

#if !defined (_ComGoogleCommonCollectMultisets_EntrySet_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_EntrySet_INCLUDE)
#define _ComGoogleCommonCollectMultisets_EntrySet_

@protocol ComGoogleCommonCollectMultiset;

#define ComGoogleCommonCollectSets_RESTRICT 1
#define ComGoogleCommonCollectSets_ImprovedAbstractSet_INCLUDE 1
#include "com/google/common/collect/Sets.h"

@interface ComGoogleCommonCollectMultisets_EntrySet : ComGoogleCommonCollectSets_ImprovedAbstractSet {
}

- (id<ComGoogleCommonCollectMultiset>)multiset;
- (BOOL)containsWithId:(id)o;
- (BOOL)removeWithId:(id)object;
- (void)clear;
- (id)init;
@end
#endif

#if !defined (_ComGoogleCommonCollectMultisets_MultisetIteratorImpl_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_MultisetIteratorImpl_INCLUDE)
#define _ComGoogleCommonCollectMultisets_MultisetIteratorImpl_

@protocol ComGoogleCommonCollectMultiset;
@protocol ComGoogleCommonCollectMultiset_Entry;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectMultisets_MultisetIteratorImpl : NSObject < JavaUtilIterator > {
 @public
  id<ComGoogleCommonCollectMultiset> multiset_;
  id<JavaUtilIterator> entryIterator_;
  id<ComGoogleCommonCollectMultiset_Entry> currentEntry_;
  int laterCount_;
  int totalCount_;
  BOOL canRemove_;
}

- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)multiset
                        withJavaUtilIterator:(id<JavaUtilIterator>)entryIterator;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMultisets_MultisetIteratorImpl *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_MultisetIteratorImpl, multiset_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_MultisetIteratorImpl, entryIterator_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_MultisetIteratorImpl, currentEntry_, id<ComGoogleCommonCollectMultiset_Entry>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$1_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMultiset.h"

@interface ComGoogleCommonCollectMultisets_$1 : ComGoogleCommonCollectAbstractMultiset {
 @public
  id<ComGoogleCommonCollectMultiset> val$multiset1_;
  id<ComGoogleCommonCollectMultiset> val$multiset2_;
}

- (BOOL)containsWithId:(id)element;
- (BOOL)isEmpty;
- (int)countWithId:(id)element;
- (id<JavaUtilSet>)createElementSet;
- (id<JavaUtilIterator>)entryIterator;
- (int)distinctElements;
- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$0
          withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$1, val$multiset1_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$1, val$multiset2_, id<ComGoogleCommonCollectMultiset>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$1_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$1_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$1_$1_

@class ComGoogleCommonCollectMultisets_$1;
@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectMultisets_$1_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectMultisets_$1 *this$0_;
  id<JavaUtilIterator> val$iterator1_;
  id<JavaUtilIterator> val$iterator2_;
}

- (id<ComGoogleCommonCollectMultiset_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectMultisets_$1:(ComGoogleCommonCollectMultisets_$1 *)outer$
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$0
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$1_$1, this$0_, ComGoogleCommonCollectMultisets_$1 *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$1_$1, val$iterator1_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$1_$1, val$iterator2_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$2_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$2_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$2_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMultiset.h"

@interface ComGoogleCommonCollectMultisets_$2 : ComGoogleCommonCollectAbstractMultiset {
 @public
  id<ComGoogleCommonCollectMultiset> val$multiset1_;
  id<ComGoogleCommonCollectMultiset> val$multiset2_;
}

- (int)countWithId:(id)element;
- (id<JavaUtilSet>)createElementSet;
- (id<JavaUtilIterator>)entryIterator;
- (int)distinctElements;
- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$0
          withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$2, val$multiset1_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$2, val$multiset2_, id<ComGoogleCommonCollectMultiset>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$2_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$2_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$2_$1_

@class ComGoogleCommonCollectMultisets_$2;
@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectMultisets_$2_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectMultisets_$2 *this$0_;
  id<JavaUtilIterator> val$iterator1_;
}

- (id<ComGoogleCommonCollectMultiset_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectMultisets_$2:(ComGoogleCommonCollectMultisets_$2 *)outer$
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$2_$1, this$0_, ComGoogleCommonCollectMultisets_$2 *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$2_$1, val$iterator1_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$3_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$3_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$3_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMultiset.h"

@interface ComGoogleCommonCollectMultisets_$3 : ComGoogleCommonCollectAbstractMultiset {
 @public
  id<ComGoogleCommonCollectMultiset> val$multiset1_;
  id<ComGoogleCommonCollectMultiset> val$multiset2_;
}

- (BOOL)containsWithId:(id)element;
- (BOOL)isEmpty;
- (int)size;
- (int)countWithId:(id)element;
- (id<JavaUtilSet>)createElementSet;
- (id<JavaUtilIterator>)entryIterator;
- (int)distinctElements;
- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$0
          withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$3, val$multiset1_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$3, val$multiset2_, id<ComGoogleCommonCollectMultiset>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$3_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$3_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$3_$1_

@class ComGoogleCommonCollectMultisets_$3;
@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectMultisets_$3_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectMultisets_$3 *this$0_;
  id<JavaUtilIterator> val$iterator1_;
  id<JavaUtilIterator> val$iterator2_;
}

- (id<ComGoogleCommonCollectMultiset_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectMultisets_$3:(ComGoogleCommonCollectMultisets_$3 *)outer$
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$0
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$3_$1, this$0_, ComGoogleCommonCollectMultisets_$3 *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$3_$1, val$iterator1_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$3_$1, val$iterator2_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$4_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$4_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$4_

@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectAbstractMultiset_RESTRICT 1
#define ComGoogleCommonCollectAbstractMultiset_INCLUDE 1
#include "com/google/common/collect/AbstractMultiset.h"

@interface ComGoogleCommonCollectMultisets_$4 : ComGoogleCommonCollectAbstractMultiset {
 @public
  id<ComGoogleCommonCollectMultiset> val$multiset1_;
  id<ComGoogleCommonCollectMultiset> val$multiset2_;
}

- (int)countWithId:(id)element;
- (id<JavaUtilIterator>)entryIterator;
- (int)distinctElements;
- (id)initWithComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$0
          withComGoogleCommonCollectMultiset:(id<ComGoogleCommonCollectMultiset>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$4, val$multiset1_, id<ComGoogleCommonCollectMultiset>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$4, val$multiset2_, id<ComGoogleCommonCollectMultiset>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$4_$1_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$4_$1_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$4_$1_

@class ComGoogleCommonCollectMultisets_$4;
@protocol ComGoogleCommonCollectMultiset_Entry;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectMultisets_$4_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectMultisets_$4 *this$0_;
  id<JavaUtilIterator> val$iterator1_;
}

- (id<ComGoogleCommonCollectMultiset_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectMultisets_$4:(ComGoogleCommonCollectMultisets_$4 *)outer$
                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$4_$1, this$0_, ComGoogleCommonCollectMultisets_$4 *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMultisets_$4_$1, val$iterator1_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMultisets_$5_) && (ComGoogleCommonCollectMultisets_INCLUDE_ALL || ComGoogleCommonCollectMultisets_$5_INCLUDE)
#define _ComGoogleCommonCollectMultisets_$5_

@protocol ComGoogleCommonCollectMultiset_Entry;

#define ComGoogleCommonCollectOrdering_RESTRICT 1
#define ComGoogleCommonCollectOrdering_INCLUDE 1
#include "com/google/common/collect/Ordering.h"

@interface ComGoogleCommonCollectMultisets_$5 : ComGoogleCommonCollectOrdering {
}

- (int)compareWithId:(id<ComGoogleCommonCollectMultiset_Entry>)entry1
              withId:(id<ComGoogleCommonCollectMultiset_Entry>)entry2;
- (id)init;
@end
#endif