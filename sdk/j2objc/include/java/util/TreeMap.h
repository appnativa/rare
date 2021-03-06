//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/TreeMap.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilTreeMap_H_
#define _JavaUtilTreeMap_H_

@class JavaLangIllegalArgumentException;
@class JavaUtilAbstractMap_SimpleImmutableEntry;
@class JavaUtilTreeMap_BoundedMap_BoundedEntrySet;
@class JavaUtilTreeMap_BoundedMap_BoundedKeySet;
@class JavaUtilTreeMap_BoundedMap_BoundedValuesCollection;
@class JavaUtilTreeMap_EntrySet;
@class JavaUtilTreeMap_KeySet;
@class JavaUtilTreeMap_Node;
@class JavaUtilTreeMap_RelationEnum;
@class JavaUtilTreeMap_ValuesCollection;
@protocol JavaLangComparable;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilSet;
@protocol JavaUtilSortedSet;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/lang/Enum.h"
#include "java/util/AbstractCollection.h"
#include "java/util/AbstractMap.h"
#include "java/util/AbstractSet.h"
#include "java/util/Comparator.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/NavigableMap.h"
#include "java/util/NavigableSet.h"
#include "java/util/SortedMap.h"

#define JavaUtilTreeMap_serialVersionUID 919286545866124006

@interface JavaUtilTreeMap : JavaUtilAbstractMap < JavaUtilSortedMap, JavaUtilNavigableMap, NSCopying, JavaIoSerializable > {
 @public
  id<JavaUtilComparator> comparator__;
  JavaUtilTreeMap_Node *root_;
  int size__;
  int modCount_;
  JavaUtilTreeMap_EntrySet *entrySet__;
  JavaUtilTreeMap_KeySet *keySet_TreeMap_;
  JavaUtilTreeMap_ValuesCollection *valuesCollection_TreeMap_;
}

+ (id<JavaUtilComparator>)NATURAL_ORDER;
- (id)init;
- (id)initWithJavaUtilMap:(id<JavaUtilMap>)copyFrom;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id)initWithJavaUtilSortedMap:(id<JavaUtilSortedMap>)copyFrom;
- (id)clone;
- (int)size;
- (BOOL)isEmpty;
- (id)getWithId:(id)key;
- (BOOL)containsKeyWithId:(id)key;
- (id)putWithId:(id)key
         withId:(id)value;
- (void)clear;
- (id)removeWithId:(id)key;
- (id)putInternalWithId:(id)key
                 withId:(id)value;
- (JavaUtilTreeMap_Node *)findWithId:(id)key
    withJavaUtilTreeMap_RelationEnum:(JavaUtilTreeMap_RelationEnum *)relation;
- (JavaUtilTreeMap_Node *)findByObjectWithId:(id)key;
- (JavaUtilTreeMap_Node *)findByEntryWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (void)removeInternalWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)node;
- (JavaUtilTreeMap_Node *)removeInternalByKeyWithId:(id)key;
- (void)replaceInParentWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)node
                       withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)replacement;
- (void)rebalanceWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)unbalanced
                              withBoolean:(BOOL)insert;
- (void)rotateLeftWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)root;
- (void)rotateRightWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)root;
- (JavaUtilAbstractMap_SimpleImmutableEntry *)immutableCopyWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_;
- (id<JavaUtilMap_Entry>)firstEntry;
- (id<JavaUtilMap_Entry>)internalPollFirstEntry;
- (id<JavaUtilMap_Entry>)pollFirstEntry;
- (id)firstKey;
- (id<JavaUtilMap_Entry>)lastEntry;
- (id<JavaUtilMap_Entry>)internalPollLastEntry;
- (id<JavaUtilMap_Entry>)pollLastEntry;
- (id)lastKey;
- (id<JavaUtilMap_Entry>)lowerEntryWithId:(id)key;
- (id)lowerKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)floorEntryWithId:(id)key;
- (id)floorKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)ceilingEntryWithId:(id)key;
- (id)ceilingKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)higherEntryWithId:(id)key;
- (id)higherKeyWithId:(id)key;
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilSet>)keySet;
- (id<JavaUtilNavigableSet>)navigableKeySet;
- (id<JavaUtilCollection>)values;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)from
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)to
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilSortedMap>)subMapWithId:(id)fromInclusive
                               withId:(id)toExclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(id)to
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedMap>)headMapWithId:(id)toExclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(id)from
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedMap>)tailMapWithId:(id)fromInclusive;
- (id<JavaUtilNavigableMap>)descendingMap;
- (id<JavaUtilNavigableSet>)descendingKeySet;
+ (int)countWithJavaUtilIterator:(id<JavaUtilIterator>)iterator;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(JavaUtilTreeMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap, comparator__, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap, root_, JavaUtilTreeMap_Node *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap, entrySet__, JavaUtilTreeMap_EntrySet *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap, keySet_TreeMap_, JavaUtilTreeMap_KeySet *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap, valuesCollection_TreeMap_, JavaUtilTreeMap_ValuesCollection *)

typedef enum {
  JavaUtilTreeMap_Relation_LOWER = 0,
  JavaUtilTreeMap_Relation_FLOOR = 1,
  JavaUtilTreeMap_Relation_EQUAL = 2,
  JavaUtilTreeMap_Relation_CREATE = 3,
  JavaUtilTreeMap_Relation_CEILING = 4,
  JavaUtilTreeMap_Relation_HIGHER = 5,
} JavaUtilTreeMap_Relation;

@interface JavaUtilTreeMap_RelationEnum : JavaLangEnum < NSCopying > {
}
+ (JavaUtilTreeMap_RelationEnum *)LOWER;
+ (JavaUtilTreeMap_RelationEnum *)FLOOR;
+ (JavaUtilTreeMap_RelationEnum *)EQUAL;
+ (JavaUtilTreeMap_RelationEnum *)CREATE;
+ (JavaUtilTreeMap_RelationEnum *)CEILING;
+ (JavaUtilTreeMap_RelationEnum *)HIGHER;
+ (IOSObjectArray *)values;
+ (JavaUtilTreeMap_RelationEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (JavaUtilTreeMap_RelationEnum *)forOrderWithBoolean:(BOOL)ascending;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilTreeMap_Node : NSObject < JavaUtilMap_Entry > {
 @public
  __weak JavaUtilTreeMap_Node *parent_;
  JavaUtilTreeMap_Node *left_;
  JavaUtilTreeMap_Node *right_;
  id key_;
  id value_;
  int height_;
}

- (id)initWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)parent
                            withId:(id)key;
- (JavaUtilTreeMap_Node *)copy__WithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)parent OBJC_METHOD_FAMILY_NONE;
- (id)getKey;
- (id)getValue;
- (id)setValueWithId:(id)value;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (JavaUtilTreeMap_Node *)next;
- (JavaUtilTreeMap_Node *)prev;
- (JavaUtilTreeMap_Node *)first;
- (JavaUtilTreeMap_Node *)last;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_Node *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_Node, left_, JavaUtilTreeMap_Node *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_Node, right_, JavaUtilTreeMap_Node *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_Node, key_, id)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_Node, value_, id)

@interface JavaUtilTreeMap_EntrySet : JavaUtilAbstractSet {
 @public
  __weak JavaUtilTreeMap *this$0_;
}

- (int)size;
- (id<JavaUtilIterator>)iterator;
- (BOOL)containsWithId:(id)o;
- (BOOL)removeWithId:(id)o;
- (void)clear;
- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$;
@end

@interface JavaUtilTreeMap_MapIterator : NSObject < JavaUtilIterator > {
 @public
  JavaUtilTreeMap *this$0_;
  JavaUtilTreeMap_Node *next_;
  JavaUtilTreeMap_Node *last_;
  int expectedModCount_;
}

- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$
     withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)next;
- (BOOL)hasNext;
- (JavaUtilTreeMap_Node *)stepForward;
- (JavaUtilTreeMap_Node *)stepBackward;
- (void)remove;
- (id)next;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_MapIterator *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_MapIterator, this$0_, JavaUtilTreeMap *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_MapIterator, next_, JavaUtilTreeMap_Node *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_MapIterator, last_, JavaUtilTreeMap_Node *)

@interface JavaUtilTreeMap_EntrySet_$1 : JavaUtilTreeMap_MapIterator {
}

- (id<JavaUtilMap_Entry>)next;
- (id)initWithJavaUtilTreeMap_EntrySet:(JavaUtilTreeMap_EntrySet *)outer$
              withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

@interface JavaUtilTreeMap_KeySet : JavaUtilAbstractSet < JavaUtilNavigableSet > {
 @public
  __weak JavaUtilTreeMap *this$0_;
}

- (int)size;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (BOOL)containsWithId:(id)o;
- (BOOL)removeWithId:(id)key;
- (void)clear;
- (id<JavaUtilComparator>)comparator;
- (id)first;
- (id)last;
- (id)lowerWithId:(id)key;
- (id)floorWithId:(id)key;
- (id)ceilingWithId:(id)key;
- (id)higherWithId:(id)key;
- (id)pollFirst;
- (id)pollLast;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)from
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)to
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromInclusive
                               withId:(id)toExclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)to
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toExclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)from
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromInclusive;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$;
@end

@interface JavaUtilTreeMap_KeySet_$1 : JavaUtilTreeMap_MapIterator {
}

- (id)next;
- (id)initWithJavaUtilTreeMap_KeySet:(JavaUtilTreeMap_KeySet *)outer$
            withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

@interface JavaUtilTreeMap_KeySet_$2 : JavaUtilTreeMap_MapIterator {
}

- (id)next;
- (id)initWithJavaUtilTreeMap_KeySet:(JavaUtilTreeMap_KeySet *)outer$
            withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

@interface JavaUtilTreeMap_ValuesCollection : JavaUtilAbstractCollection {
 @public
  __weak JavaUtilTreeMap *this$0_;
}

- (int)size;
- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$;
@end

@interface JavaUtilTreeMap_ValuesCollection_$1 : JavaUtilTreeMap_MapIterator {
}

- (id)next;
- (id)initWithJavaUtilTreeMap_ValuesCollection:(JavaUtilTreeMap_ValuesCollection *)outer$
                      withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

typedef enum {
  JavaUtilTreeMap_Bound_INCLUSIVE = 0,
  JavaUtilTreeMap_Bound_EXCLUSIVE = 1,
  JavaUtilTreeMap_Bound_NO_BOUND = 2,
} JavaUtilTreeMap_Bound;

@interface JavaUtilTreeMap_BoundEnum : JavaLangEnum < NSCopying > {
}
+ (JavaUtilTreeMap_BoundEnum *)INCLUSIVE;
+ (JavaUtilTreeMap_BoundEnum *)EXCLUSIVE;
+ (JavaUtilTreeMap_BoundEnum *)NO_BOUND;
+ (IOSObjectArray *)values;
+ (JavaUtilTreeMap_BoundEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (NSString *)leftCapWithId:(id)from;
- (NSString *)rightCapWithId:(id)to;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilTreeMap_BoundEnum_$1 : JavaUtilTreeMap_BoundEnum {
}

- (NSString *)leftCapWithId:(id)from;
- (NSString *)rightCapWithId:(id)to;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilTreeMap_BoundEnum_$2 : JavaUtilTreeMap_BoundEnum {
}

- (NSString *)leftCapWithId:(id)from;
- (NSString *)rightCapWithId:(id)to;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilTreeMap_BoundEnum_$3 : JavaUtilTreeMap_BoundEnum {
}

- (NSString *)leftCapWithId:(id)from;
- (NSString *)rightCapWithId:(id)to;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface JavaUtilTreeMap_BoundedMap : JavaUtilAbstractMap < JavaUtilNavigableMap, JavaIoSerializable > {
 @public
  JavaUtilTreeMap *this$0_;
  BOOL ascending_;
  id from_;
  JavaUtilTreeMap_BoundEnum *fromBound_;
  id to_;
  JavaUtilTreeMap_BoundEnum *toBound_;
  JavaUtilTreeMap_BoundedMap_BoundedEntrySet *entrySet__;
  JavaUtilTreeMap_BoundedMap_BoundedKeySet *keySet_BoundedMap_;
  JavaUtilTreeMap_BoundedMap_BoundedValuesCollection *valuesCollection_BoundedMap_;
}

- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$
                  withBoolean:(BOOL)ascending
                       withId:(id)from
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                       withId:(id)to
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (int)size;
- (BOOL)isEmpty;
- (id)getWithId:(id)key;
- (BOOL)containsKeyWithId:(id)key;
- (id)putWithId:(id)key
         withId:(id)value;
- (id)removeWithId:(id)key;
- (BOOL)isInBoundsWithId:(id)key;
- (BOOL)isInBoundsWithId:(id)key
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (JavaUtilTreeMap_Node *)boundWithJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)node
                          withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                          withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (id<JavaUtilMap_Entry>)firstEntry;
- (id<JavaUtilMap_Entry>)pollFirstEntry;
- (id)firstKey;
- (id<JavaUtilMap_Entry>)lastEntry;
- (id<JavaUtilMap_Entry>)pollLastEntry;
- (id)lastKey;
- (JavaUtilTreeMap_Node *)endpointWithBoolean:(BOOL)first;
- (id<JavaUtilMap_Entry>)findBoundedWithId:(id)key
          withJavaUtilTreeMap_RelationEnum:(JavaUtilTreeMap_RelationEnum *)relation;
- (id<JavaUtilMap_Entry>)lowerEntryWithId:(id)key;
- (id)lowerKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)floorEntryWithId:(id)key;
- (id)floorKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)ceilingEntryWithId:(id)key;
- (id)ceilingKeyWithId:(id)key;
- (id<JavaUtilMap_Entry>)higherEntryWithId:(id)key;
- (id)higherKeyWithId:(id)key;
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilSet>)keySet;
- (id<JavaUtilNavigableSet>)navigableKeySet;
- (id<JavaUtilCollection>)values;
- (id<JavaUtilNavigableMap>)descendingMap;
- (id<JavaUtilNavigableSet>)descendingKeySet;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)from
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)to
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)fromInclusive
                                  withId:(id)toExclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(id)to
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(id)toExclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(id)from
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(id)fromInclusive;
- (id<JavaUtilNavigableMap>)subMapWithId:(id)from
           withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                                  withId:(id)to
           withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (JavaLangIllegalArgumentException *)outOfBoundsWithId:(id)value
                          withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                          withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (id)writeReplace;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_BoundedMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, this$0_, JavaUtilTreeMap *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, from_, id)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, fromBound_, JavaUtilTreeMap_BoundEnum *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, to_, id)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, toBound_, JavaUtilTreeMap_BoundEnum *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, entrySet__, JavaUtilTreeMap_BoundedMap_BoundedEntrySet *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, keySet_BoundedMap_, JavaUtilTreeMap_BoundedMap_BoundedKeySet *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap, valuesCollection_BoundedMap_, JavaUtilTreeMap_BoundedMap_BoundedValuesCollection *)

@interface JavaUtilTreeMap_BoundedMap_BoundedEntrySet : JavaUtilAbstractSet {
 @public
  __weak JavaUtilTreeMap_BoundedMap *this$0_;
}

- (int)size;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (BOOL)containsWithId:(id)o;
- (BOOL)removeWithId:(id)o;
- (id)initWithJavaUtilTreeMap_BoundedMap:(JavaUtilTreeMap_BoundedMap *)outer$;
@end

@interface JavaUtilTreeMap_BoundedMap_BoundedIterator : JavaUtilTreeMap_MapIterator {
 @public
  JavaUtilTreeMap_BoundedMap *this$1_;
}

- (id)initWithJavaUtilTreeMap_BoundedMap:(JavaUtilTreeMap_BoundedMap *)outer$
                withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)next;
- (JavaUtilTreeMap_Node *)stepForward;
- (JavaUtilTreeMap_Node *)stepBackward;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap_BoundedIterator, this$1_, JavaUtilTreeMap_BoundedMap *)

@interface JavaUtilTreeMap_BoundedMap_BoundedEntrySet_$1 : JavaUtilTreeMap_BoundedMap_BoundedIterator {
 @public
  JavaUtilTreeMap_BoundedMap_BoundedEntrySet *this$2_;
}

- (id<JavaUtilMap_Entry>)next;
- (id)initWithJavaUtilTreeMap_BoundedMap_BoundedEntrySet:(JavaUtilTreeMap_BoundedMap_BoundedEntrySet *)outer$
                                withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap_BoundedEntrySet_$1, this$2_, JavaUtilTreeMap_BoundedMap_BoundedEntrySet *)

@interface JavaUtilTreeMap_BoundedMap_BoundedKeySet : JavaUtilAbstractSet < JavaUtilNavigableSet > {
 @public
  __weak JavaUtilTreeMap_BoundedMap *this$0_;
}

- (int)size;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (BOOL)containsWithId:(id)key;
- (BOOL)removeWithId:(id)key;
- (id)first;
- (id)pollFirst;
- (id)last;
- (id)pollLast;
- (id)lowerWithId:(id)key;
- (id)floorWithId:(id)key;
- (id)ceilingWithId:(id)key;
- (id)higherWithId:(id)key;
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)from
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)to
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilSortedSet>)subSetWithId:(id)fromInclusive
                               withId:(id)toExclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)to
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)headSetWithId:(id)toExclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)from
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)fromInclusive;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id)initWithJavaUtilTreeMap_BoundedMap:(JavaUtilTreeMap_BoundedMap *)outer$;
@end

@interface JavaUtilTreeMap_BoundedMap_BoundedKeySet_$1 : JavaUtilTreeMap_BoundedMap_BoundedIterator {
 @public
  JavaUtilTreeMap_BoundedMap_BoundedKeySet *this$2_;
}

- (id)next;
- (id)initWithJavaUtilTreeMap_BoundedMap_BoundedKeySet:(JavaUtilTreeMap_BoundedMap_BoundedKeySet *)outer$
                              withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap_BoundedKeySet_$1, this$2_, JavaUtilTreeMap_BoundedMap_BoundedKeySet *)

@interface JavaUtilTreeMap_BoundedMap_BoundedKeySet_$2 : JavaUtilTreeMap_BoundedMap_BoundedIterator {
 @public
  JavaUtilTreeMap_BoundedMap_BoundedKeySet *this$2_;
}

- (id)next;
- (id)initWithJavaUtilTreeMap_BoundedMap_BoundedKeySet:(JavaUtilTreeMap_BoundedMap_BoundedKeySet *)outer$
                              withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap_BoundedKeySet_$2, this$2_, JavaUtilTreeMap_BoundedMap_BoundedKeySet *)

@interface JavaUtilTreeMap_BoundedMap_BoundedValuesCollection : JavaUtilAbstractCollection {
 @public
  __weak JavaUtilTreeMap_BoundedMap *this$0_;
}

- (int)size;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (id)initWithJavaUtilTreeMap_BoundedMap:(JavaUtilTreeMap_BoundedMap *)outer$;
@end

@interface JavaUtilTreeMap_BoundedMap_BoundedValuesCollection_$1 : JavaUtilTreeMap_BoundedMap_BoundedIterator {
 @public
  JavaUtilTreeMap_BoundedMap_BoundedValuesCollection *this$2_;
}

- (id)next;
- (id)initWithJavaUtilTreeMap_BoundedMap_BoundedValuesCollection:(JavaUtilTreeMap_BoundedMap_BoundedValuesCollection *)outer$
                                        withJavaUtilTreeMap_Node:(JavaUtilTreeMap_Node *)arg$0;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_BoundedMap_BoundedValuesCollection_$1, this$2_, JavaUtilTreeMap_BoundedMap_BoundedValuesCollection *)

#define JavaUtilTreeMap_NavigableSubMap_serialVersionUID -2102997345730753016

@interface JavaUtilTreeMap_NavigableSubMap : JavaUtilAbstractMap < JavaIoSerializable > {
 @public
  JavaUtilTreeMap *m_;
  id lo_;
  id hi_;
  BOOL fromStart_;
  BOOL toEnd_;
  BOOL loInclusive_;
  BOOL hiInclusive_;
}

- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)delegate
                       withId:(id)from
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                       withId:(id)to
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (id<JavaUtilSet>)entrySet;
- (id)readResolve;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_NavigableSubMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_NavigableSubMap, m_, JavaUtilTreeMap *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_NavigableSubMap, lo_, id)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_NavigableSubMap, hi_, id)

#define JavaUtilTreeMap_DescendingSubMap_serialVersionUID 912986545866120460

@interface JavaUtilTreeMap_DescendingSubMap : JavaUtilTreeMap_NavigableSubMap {
 @public
  id<JavaUtilComparator> reverseComparator_;
}

- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)delegate
                       withId:(id)from
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                       withId:(id)to
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_DescendingSubMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_DescendingSubMap, reverseComparator_, id<JavaUtilComparator>)

#define JavaUtilTreeMap_AscendingSubMap_serialVersionUID 912986545866124060

@interface JavaUtilTreeMap_AscendingSubMap : JavaUtilTreeMap_NavigableSubMap {
}

- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)delegate
                       withId:(id)from
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)fromBound
                       withId:(id)to
withJavaUtilTreeMap_BoundEnum:(JavaUtilTreeMap_BoundEnum *)toBound;
@end

#define JavaUtilTreeMap_SubMap_serialVersionUID -6520786458950516097

@interface JavaUtilTreeMap_SubMap : JavaUtilAbstractMap < JavaIoSerializable > {
 @public
  JavaUtilTreeMap *this$0_;
  id fromKey_;
  id toKey_;
  BOOL fromStart_;
  BOOL toEnd_;
}

- (id<JavaUtilSet>)entrySet;
- (id)readResolve;
- (id)initWithJavaUtilTreeMap:(JavaUtilTreeMap *)outer$;
- (void)copyAllFieldsTo:(JavaUtilTreeMap_SubMap *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeMap_SubMap, this$0_, JavaUtilTreeMap *)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_SubMap, fromKey_, id)
J2OBJC_FIELD_SETTER(JavaUtilTreeMap_SubMap, toKey_, id)

@interface JavaUtilTreeMap_$1 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(id<JavaLangComparable>)a
              withId:(id<JavaLangComparable>)b;
- (id)init;
@end

#endif // _JavaUtilTreeMap_H_
