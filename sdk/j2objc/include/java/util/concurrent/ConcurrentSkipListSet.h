//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: android/libcore/luni/src/main/java/java/util/concurrent/ConcurrentSkipListSet.java
//
//  Created by tball on 11/23/13.
//

#ifndef _JavaUtilConcurrentConcurrentSkipListSet_H_
#define _JavaUtilConcurrentConcurrentSkipListSet_H_

@class SunMiscUnsafe;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilConcurrentConcurrentNavigableMap;
@protocol JavaUtilIterator;
@protocol JavaUtilSortedSet;

#import "JreEmulation.h"
#include "java/io/Serializable.h"
#include "java/util/AbstractSet.h"
#include "java/util/NavigableSet.h"

#define JavaUtilConcurrentConcurrentSkipListSet_serialVersionUID -2479143111061671589

@interface JavaUtilConcurrentConcurrentSkipListSet : JavaUtilAbstractSet < JavaUtilNavigableSet, NSCopying, JavaIoSerializable > {
 @public
  id<JavaUtilConcurrentConcurrentNavigableMap> m_;
}

+ (SunMiscUnsafe *)UNSAFE;
+ (long long int)mapOffset;
- (id)init;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)initWithJavaUtilSortedSet:(id<JavaUtilSortedSet>)s;
- (id)initWithJavaUtilConcurrentConcurrentNavigableMap:(id<JavaUtilConcurrentConcurrentNavigableMap>)m;
- (JavaUtilConcurrentConcurrentSkipListSet *)clone;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)containsWithId:(id)o;
- (BOOL)addWithId:(id)e;
- (BOOL)removeWithId:(id)o;
- (void)clear;
- (id<JavaUtilIterator>)iterator;
- (id<JavaUtilIterator>)descendingIterator;
- (BOOL)isEqual:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)lowerWithId:(id)e;
- (id)floorWithId:(id)e;
- (id)ceilingWithId:(id)e;
- (id)higherWithId:(id)e;
- (id)pollFirst;
- (id)pollLast;
- (id<JavaUtilComparator>)comparator;
- (id)first;
- (id)last;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                             withBoolean:(BOOL)fromInclusive
                                  withId:(id)toElement
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableSet>)subSetWithId:(id)fromElement
                                  withId:(id)toElement;
- (id<JavaUtilNavigableSet>)headSetWithId:(id)toElement;
- (id<JavaUtilNavigableSet>)tailSetWithId:(id)fromElement;
- (id<JavaUtilNavigableSet>)descendingSet;
- (void)setMapWithJavaUtilConcurrentConcurrentNavigableMap:(id<JavaUtilConcurrentConcurrentNavigableMap>)map;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(JavaUtilConcurrentConcurrentSkipListSet *)other;
@end

J2OBJC_FIELD_SETTER(JavaUtilConcurrentConcurrentSkipListSet, m_, id<JavaUtilConcurrentConcurrentNavigableMap>)

#endif // _JavaUtilConcurrentConcurrentSkipListSet_H_