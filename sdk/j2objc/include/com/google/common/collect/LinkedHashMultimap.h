//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/LinkedHashMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectLinkedHashMultimap_RESTRICT
#define ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectLinkedHashMultimap_RESTRICT
#if ComGoogleCommonCollectLinkedHashMultimap_ValueSet_INCLUDE
#define ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink_INCLUDE 1
#endif
#if ComGoogleCommonCollectLinkedHashMultimap_ValueEntry_INCLUDE
#define ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_

@class ComGoogleCommonCollectLinkedHashMultimap_ValueEntry;
@protocol ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink;
@protocol ComGoogleCommonCollectMultimap;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectAbstractSetMultimap_INCLUDE 1
#include "com/google/common/collect/AbstractSetMultimap.h"

#define ComGoogleCommonCollectLinkedHashMultimap_DEFAULT_KEY_CAPACITY 16
#define ComGoogleCommonCollectLinkedHashMultimap_DEFAULT_VALUE_SET_CAPACITY 2
#define ComGoogleCommonCollectLinkedHashMultimap_VALUE_SET_LOAD_FACTOR 1.0
#define ComGoogleCommonCollectLinkedHashMultimap_serialVersionUID 1

@interface ComGoogleCommonCollectLinkedHashMultimap : ComGoogleCommonCollectAbstractSetMultimap {
 @public
  int valueSetCapacity_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *multimapHeaderEntry_;
}

+ (double)VALUE_SET_LOAD_FACTOR;
+ (ComGoogleCommonCollectLinkedHashMultimap *)create;
+ (ComGoogleCommonCollectLinkedHashMultimap *)createWithInt:(int)expectedKeys
                                                    withInt:(int)expectedValuesPerKey;
+ (ComGoogleCommonCollectLinkedHashMultimap *)createWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)multimap;
+ (void)succeedsInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)pred
                          withComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)succ;
+ (void)succeedsInMultimapWithComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)pred
                          withComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)succ;
+ (void)deleteFromValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
+ (void)deleteFromMultimapWithComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)entry_;
- (id)initWithInt:(int)keyCapacity
          withInt:(int)valueSetCapacity;
- (id<JavaUtilSet>)createCollection;
- (id<JavaUtilCollection>)createCollectionWithId:(id)key;
- (id<JavaUtilSet>)replaceValuesWithId:(id)key
                  withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<JavaUtilSet>)entries;
- (id<JavaUtilCollection>)values;
- (id<JavaUtilIterator>)entryIterator;
- (void)clear;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLinkedHashMultimap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap, multimapHeaderEntry_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink_

@protocol ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink < NSObject, JavaObject >
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getPredecessorInValueSet;
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getSuccessorInValueSet;
- (void)setPredecessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
- (void)setSuccessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
@end
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_ValueEntry_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_ValueEntry_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_ValueEntry_

#define ComGoogleCommonCollectAbstractMapEntry_RESTRICT 1
#define ComGoogleCommonCollectAbstractMapEntry_INCLUDE 1
#include "com/google/common/collect/AbstractMapEntry.h"

@interface ComGoogleCommonCollectLinkedHashMultimap_ValueEntry : ComGoogleCommonCollectAbstractMapEntry < ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink > {
 @public
  id key_;
  id value_;
  int valueHash_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *nextInValueSetHashRow_;
  id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink> predecessorInValueSet_;
  id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink> successorInValueSet_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *predecessorInMultimap_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *successorInMultimap_;
}

- (id)initWithId:(id)key
          withId:(id)value
         withInt:(int)valueHash
withComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)nextInValueSetHashRow;
- (id)getKey;
- (id)getValue;
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getPredecessorInValueSet;
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getSuccessorInValueSet;
- (void)setPredecessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
- (void)setSuccessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
- (ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)getPredecessorInMultimap;
- (ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)getSuccessorInMultimap;
- (void)setSuccessorInMultimapWithComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)multimapSuccessor;
- (void)setPredecessorInMultimapWithComGoogleCommonCollectLinkedHashMultimap_ValueEntry:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)multimapPredecessor;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, key_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, value_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, nextInValueSetHashRow_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, predecessorInValueSet_, id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, successorInValueSet_, id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, predecessorInMultimap_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueEntry, successorInMultimap_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_ValueSet_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_ValueSet_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_ValueSet_

@class ComGoogleCommonCollectLinkedHashMultimap;
@class IOSObjectArray;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectSets_RESTRICT 1
#define ComGoogleCommonCollectSets_ImprovedAbstractSet_INCLUDE 1
#include "com/google/common/collect/Sets.h"

@interface ComGoogleCommonCollectLinkedHashMultimap_ValueSet : ComGoogleCommonCollectSets_ImprovedAbstractSet < ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink > {
 @public
  __weak ComGoogleCommonCollectLinkedHashMultimap *this$0_;
  id key_;
  IOSObjectArray *hashTable_;
  int size__;
  int modCount_;
  id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink> firstEntry_;
  id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink> lastEntry_;
}

- (id)initWithComGoogleCommonCollectLinkedHashMultimap:(ComGoogleCommonCollectLinkedHashMultimap *)outer$
                                                withId:(id)key
                                               withInt:(int)expectedValues;
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getPredecessorInValueSet;
- (id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)getSuccessorInValueSet;
- (void)setPredecessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
- (void)setSuccessorInValueSetWithComGoogleCommonCollectLinkedHashMultimap_ValueSetLink:(id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)entry_;
- (id<JavaUtilIterator>)iterator;
- (int)size;
- (BOOL)containsWithId:(id)o;
- (BOOL)addWithId:(id)value;
- (void)rehashIfNecessary;
- (BOOL)removeWithId:(id)o;
- (void)clear;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLinkedHashMultimap_ValueSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet, key_, id)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet, hashTable_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet, firstEntry_, id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet, lastEntry_, id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1_

@class ComGoogleCommonCollectLinkedHashMultimap_ValueEntry;
@class ComGoogleCommonCollectLinkedHashMultimap_ValueSet;
@protocol ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1 : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectLinkedHashMultimap_ValueSet *this$0_;
  id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink> nextEntry_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *toRemove_;
  int expectedModCount_;
}

- (void)checkForComodification;
- (BOOL)hasNext;
- (id)next;
- (void)remove;
- (id)initWithComGoogleCommonCollectLinkedHashMultimap_ValueSet:(ComGoogleCommonCollectLinkedHashMultimap_ValueSet *)outer$;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1, this$0_, ComGoogleCommonCollectLinkedHashMultimap_ValueSet *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1, nextEntry_, id<ComGoogleCommonCollectLinkedHashMultimap_ValueSetLink>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_ValueSet_$1, toRemove_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
#endif

#if !defined (_ComGoogleCommonCollectLinkedHashMultimap_$1_) && (ComGoogleCommonCollectLinkedHashMultimap_INCLUDE_ALL || ComGoogleCommonCollectLinkedHashMultimap_$1_INCLUDE)
#define _ComGoogleCommonCollectLinkedHashMultimap_$1_

@class ComGoogleCommonCollectLinkedHashMultimap;
@class ComGoogleCommonCollectLinkedHashMultimap_ValueEntry;
@protocol JavaUtilMap_Entry;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectLinkedHashMultimap_$1 : NSObject < JavaUtilIterator > {
 @public
  ComGoogleCommonCollectLinkedHashMultimap *this$0_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *nextEntry_;
  ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *toRemove_;
}

- (BOOL)hasNext;
- (id<JavaUtilMap_Entry>)next;
- (void)remove;
- (id)initWithComGoogleCommonCollectLinkedHashMultimap:(ComGoogleCommonCollectLinkedHashMultimap *)outer$;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectLinkedHashMultimap_$1 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_$1, this$0_, ComGoogleCommonCollectLinkedHashMultimap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_$1, nextEntry_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectLinkedHashMultimap_$1, toRemove_, ComGoogleCommonCollectLinkedHashMultimap_ValueEntry *)
#endif
