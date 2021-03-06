//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/TreeSet.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilTreeSet_H_
#define _JavaUtilTreeSet_H_

@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilNavigableMap;
@protocol JavaUtilSortedSet;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractSet.h"
#include "java/util/NavigableSet.h"

#define JavaUtilTreeSet_serialVersionUID -2479143000061671589

@interface JavaUtilTreeSet : JavaUtilAbstractSet < JavaUtilNavigableSet, NSCopying, JavaIoSerializable > {
 @public
  id<JavaUtilNavigableMap> backingMap_;
  id<JavaUtilNavigableSet> descendingSet__;
}

- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)map;
- (id)init;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id)initWithJavaUtilSortedSet:(id<JavaUtilSortedSet>)set;
- (BOOL)addWithId:(id)object;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)clear;
- (id)clone;
- (id<JavaUtilComparator>)comparator;
- (BOOL)containsWithId:(id)object;
- (BOOL)isEmpty;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (BOOL)removeWithId:(id)object;
- (int)size;
- (id)first;
- (id)last;
- (id)pollFirst;
- (id)pollLast;
- (id)higherWithId:(id)e;
- (id)lowerWithId:(id)e;
- (id)ceilingWithId:(id)e;
- (id)floorWithId:(id)e;
- (id<JavaUtilNavigableSet>)descendingSet;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)start
                             withBoolean:(BOOL)startInclusive
                                  withId:(id)end
                             withBoolean:(BOOL)endInclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)end
                              withBoolean:(BOOL)endInclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)start
                              withBoolean:(BOOL)startInclusive;
- (id<JavaUtilSortedSet>)subSetWithId:(id)start
                               withId:(id)end;
- (id<JavaUtilSortedSet>)headSetWithId:(id)end;
- (id<JavaUtilSortedSet>)tailSetWithId:(id)start;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(JavaUtilTreeSet *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilTreeSet, backingMap_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(JavaUtilTreeSet, descendingSet__, id<JavaUtilNavigableSet>)

#endif // _JavaUtilTreeSet_H_
