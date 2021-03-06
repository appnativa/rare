//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/MapConstraints.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectMapConstraints_RESTRICT
#define ComGoogleCommonCollectMapConstraints_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectMapConstraints_RESTRICT
#if ComGoogleCommonCollectMapConstraints_ConstrainedSortedSetMultimap_INCLUDE
#define ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap_INCLUDE 1
#endif
#if ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap_INCLUDE
#define ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_INCLUDE 1
#endif
#if ComGoogleCommonCollectMapConstraints_ConstrainedListMultimap_INCLUDE
#define ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_INCLUDE 1
#endif
#if ComGoogleCommonCollectMapConstraints_ConstrainedEntrySet_INCLUDE
#define ComGoogleCommonCollectMapConstraints_ConstrainedEntries_INCLUDE 1
#endif
#if ComGoogleCommonCollectMapConstraints_ConstrainedBiMap_INCLUDE
#define ComGoogleCommonCollectMapConstraints_ConstrainedMap_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_

@protocol ComGoogleCommonCollectBiMap;
@protocol ComGoogleCommonCollectListMultimap;
@protocol ComGoogleCommonCollectMapConstraint;
@protocol ComGoogleCommonCollectMultimap;
@protocol ComGoogleCommonCollectSetMultimap;
@protocol ComGoogleCommonCollectSortedSetMultimap;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilMap_Entry;
@protocol JavaUtilSet;

@interface ComGoogleCommonCollectMapConstraints : NSObject {
}

- (id)init;
+ (id<ComGoogleCommonCollectMapConstraint>)notNull;
+ (id<JavaUtilMap>)constrainedMapWithJavaUtilMap:(id<JavaUtilMap>)map
         withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<ComGoogleCommonCollectMultimap>)constrainedMultimapWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)multimap
                                                    withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<ComGoogleCommonCollectListMultimap>)constrainedListMultimapWithComGoogleCommonCollectListMultimap:(id<ComGoogleCommonCollectListMultimap>)multimap
                                                                withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<ComGoogleCommonCollectSetMultimap>)constrainedSetMultimapWithComGoogleCommonCollectSetMultimap:(id<ComGoogleCommonCollectSetMultimap>)multimap
                                                             withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<ComGoogleCommonCollectSortedSetMultimap>)constrainedSortedSetMultimapWithComGoogleCommonCollectSortedSetMultimap:(id<ComGoogleCommonCollectSortedSetMultimap>)multimap
                                                                               withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilMap_Entry>)constrainedEntryWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_
                       withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilMap_Entry>)constrainedAsMapEntryWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)entry_
                            withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilSet>)constrainedAsMapEntriesWithJavaUtilSet:(id<JavaUtilSet>)entries
                  withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilCollection>)constrainedEntriesWithJavaUtilCollection:(id<JavaUtilCollection>)entries
                           withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilSet>)constrainedEntrySetWithJavaUtilSet:(id<JavaUtilSet>)entries
              withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<ComGoogleCommonCollectBiMap>)constrainedBiMapWithComGoogleCommonCollectBiMap:(id<ComGoogleCommonCollectBiMap>)map
                                           withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilCollection>)checkValuesWithId:(id)key
                       withJavaLangIterable:(id<JavaLangIterable>)values
    withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
+ (id<JavaUtilMap>)checkMapWithJavaUtilMap:(id<JavaUtilMap>)map
   withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum_

#define JavaLangEnum_RESTRICT 1
#define JavaLangEnum_INCLUDE 1
#include "java/lang/Enum.h"

#define ComGoogleCommonCollectMapConstraint_RESTRICT 1
#define ComGoogleCommonCollectMapConstraint_INCLUDE 1
#include "com/google/common/collect/MapConstraint.h"

typedef enum {
  ComGoogleCommonCollectMapConstraints_NotNullMapConstraint_INSTANCE = 0,
} ComGoogleCommonCollectMapConstraints_NotNullMapConstraint;

@interface ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum : JavaLangEnum < NSCopying, ComGoogleCommonCollectMapConstraint > {
}
+ (ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum *)INSTANCE;
+ (IOSObjectArray *)values;
+ (ComGoogleCommonCollectMapConstraints_NotNullMapConstraintEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (void)checkKeyValueWithId:(id)key
                     withId:(id)value;
- (NSString *)description;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedMap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedMap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedMap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingMap_RESTRICT 1
#define ComGoogleCommonCollectForwardingMap_INCLUDE 1
#include "com/google/common/collect/ForwardingMap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedMap : ComGoogleCommonCollectForwardingMap {
 @public
  id<JavaUtilMap> delegate__;
  id<ComGoogleCommonCollectMapConstraint> constraint_;
  id<JavaUtilSet> entrySet__;
}

- (id)initWithJavaUtilMap:(id<JavaUtilMap>)delegate
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilMap>)delegate;
- (id<JavaUtilSet>)entrySet;
- (id)putWithId:(id)key
         withId:(id)value;
- (void)putAllWithJavaUtilMap:(id<JavaUtilMap>)map;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMap, delegate__, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMap, constraint_, id<ComGoogleCommonCollectMapConstraint>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMap, entrySet__, id<JavaUtilSet>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedBiMap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedBiMap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedBiMap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectBiMap_RESTRICT 1
#define ComGoogleCommonCollectBiMap_INCLUDE 1
#include "com/google/common/collect/BiMap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedBiMap : ComGoogleCommonCollectMapConstraints_ConstrainedMap < ComGoogleCommonCollectBiMap > {
 @public
  id<ComGoogleCommonCollectBiMap> inverse__;
}

- (id)initWithComGoogleCommonCollectBiMap:(id<ComGoogleCommonCollectBiMap>)delegate
          withComGoogleCommonCollectBiMap:(id<ComGoogleCommonCollectBiMap>)inverse
  withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<ComGoogleCommonCollectBiMap>)delegate;
- (id)forcePutWithId:(id)key
              withId:(id)value;
- (id<ComGoogleCommonCollectBiMap>)inverse;
- (id<JavaUtilSet>)values;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedBiMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedBiMap, inverse__, id<ComGoogleCommonCollectBiMap>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_InverseConstraint_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_InverseConstraint_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_InverseConstraint_

#define ComGoogleCommonCollectMapConstraint_RESTRICT 1
#define ComGoogleCommonCollectMapConstraint_INCLUDE 1
#include "com/google/common/collect/MapConstraint.h"

@interface ComGoogleCommonCollectMapConstraints_InverseConstraint : NSObject < ComGoogleCommonCollectMapConstraint > {
 @public
  id<ComGoogleCommonCollectMapConstraint> constraint_;
}

- (id)initWithComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (void)checkKeyValueWithId:(id)key
                     withId:(id)value;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_InverseConstraint *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_InverseConstraint, constraint_, id<ComGoogleCommonCollectMapConstraint>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap_

@class ComGoogleCommonCollectMapConstraints_ConstrainedMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingMap_RESTRICT 1
#define ComGoogleCommonCollectForwardingMap_INCLUDE 1
#include "com/google/common/collect/ForwardingMap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap : ComGoogleCommonCollectForwardingMap {
 @public
  __weak ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *this$0_;
  id<JavaUtilSet> entrySet__;
  id<JavaUtilCollection> values__;
  id<JavaUtilMap> val$asMapDelegate_;
}

- (id<JavaUtilMap>)delegate;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilCollection>)getWithId:(id)key;
- (id<JavaUtilCollection>)values;
- (BOOL)containsValueWithId:(id)o;
- (id)initWithComGoogleCommonCollectMapConstraints_ConstrainedMultimap:(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *)outer$
                                                       withJavaUtilMap:(id<JavaUtilMap>)capture$0;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap, entrySet__, id<JavaUtilSet>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap, values__, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_asMap_ConstrainedMultimapAsMap, val$asMapDelegate_, id<JavaUtilMap>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1_

@class ComGoogleCommonCollectMapConstraints_ConstrainedMultimap;

#define ComGoogleCommonCollectConstraint_RESTRICT 1
#define ComGoogleCommonCollectConstraint_INCLUDE 1
#include "com/google/common/collect/Constraint.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1 : NSObject < ComGoogleCommonCollectConstraint > {
 @public
  ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *this$0_;
  id val$key_;
}

- (id)checkElementWithId:(id)value;
- (NSString *)description;
- (id)initWithComGoogleCommonCollectMapConstraints_ConstrainedMultimap:(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *)outer$
                                                                withId:(id)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1, this$0_, ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_$1, val$key_, id)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_

@class IOSObjectArray;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingCollection_RESTRICT 1
#define ComGoogleCommonCollectForwardingCollection_INCLUDE 1
#include "com/google/common/collect/ForwardingCollection.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues : ComGoogleCommonCollectForwardingCollection {
 @public
  id<JavaUtilCollection> delegate__;
  id<JavaUtilSet> entrySet_;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)delegate
                 withJavaUtilSet:(id<JavaUtilSet>)entrySet;
- (id<JavaUtilCollection>)delegate;
- (id<JavaUtilIterator>)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues, delegate__, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues, entrySet_, id<JavaUtilSet>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_$1_

@protocol JavaUtilCollection;

#define JavaUtilIterator_RESTRICT 1
#define JavaUtilIterator_INCLUDE 1
#include "java/util/Iterator.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_$1 : NSObject < JavaUtilIterator > {
 @public
  id<JavaUtilIterator> val$iterator_;
}

- (BOOL)hasNext;
- (id<JavaUtilCollection>)next;
- (void)remove;
- (id)initWithJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapValues_$1, val$iterator_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1_

@class ComGoogleCommonCollectMapConstraints_ConstrainedEntries;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectForwardingIterator_RESTRICT 1
#define ComGoogleCommonCollectForwardingIterator_INCLUDE 1
#include "com/google/common/collect/ForwardingIterator.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1 : ComGoogleCommonCollectForwardingIterator {
 @public
  ComGoogleCommonCollectMapConstraints_ConstrainedEntries *this$0_;
  id<JavaUtilIterator> val$iterator_;
}

- (id<JavaUtilMap_Entry>)next;
- (id<JavaUtilIterator>)delegate;
- (id)initWithComGoogleCommonCollectMapConstraints_ConstrainedEntries:(ComGoogleCommonCollectMapConstraints_ConstrainedEntries *)outer$
                                                 withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1, this$0_, ComGoogleCommonCollectMapConstraints_ConstrainedEntries *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedEntries_$1, val$iterator_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedEntries_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedEntries_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedEntries_

@class IOSObjectArray;
@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectForwardingCollection_RESTRICT 1
#define ComGoogleCommonCollectForwardingCollection_INCLUDE 1
#include "com/google/common/collect/ForwardingCollection.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedEntries : ComGoogleCommonCollectForwardingCollection {
 @public
  id<ComGoogleCommonCollectMapConstraint> constraint_;
  id<JavaUtilCollection> entries_;
}

- (id)initWithJavaUtilCollection:(id<JavaUtilCollection>)entries
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilCollection>)delegate;
- (id<JavaUtilIterator>)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedEntries *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedEntries, constraint_, id<ComGoogleCommonCollectMapConstraint>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedEntries, entries_, id<JavaUtilCollection>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedEntrySet_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedEntrySet_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedEntrySet_

@protocol ComGoogleCommonCollectMapConstraint;

#define JavaUtilSet_RESTRICT 1
#define JavaUtilSet_INCLUDE 1
#include "java/util/Set.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedEntrySet : ComGoogleCommonCollectMapConstraints_ConstrainedEntries < JavaUtilSet > {
}

- (id)initWithJavaUtilSet:(id<JavaUtilSet>)entries
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_

@class IOSObjectArray;
@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingSet_RESTRICT 1
#define ComGoogleCommonCollectForwardingSet_INCLUDE 1
#include "com/google/common/collect/ForwardingSet.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries : ComGoogleCommonCollectForwardingSet {
 @public
  id<ComGoogleCommonCollectMapConstraint> constraint_;
  id<JavaUtilSet> entries_;
}

- (id)initWithJavaUtilSet:(id<JavaUtilSet>)entries
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilSet>)delegate;
- (id<JavaUtilIterator>)iterator;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)array;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)isEqual:(id)object;
- (NSUInteger)hash;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries, constraint_, id<ComGoogleCommonCollectMapConstraint>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries, entries_, id<JavaUtilSet>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1_

@class ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectForwardingIterator_RESTRICT 1
#define ComGoogleCommonCollectForwardingIterator_INCLUDE 1
#include "com/google/common/collect/ForwardingIterator.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1 : ComGoogleCommonCollectForwardingIterator {
 @public
  ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries *this$0_;
  id<JavaUtilIterator> val$iterator_;
}

- (id<JavaUtilMap_Entry>)next;
- (id<JavaUtilIterator>)delegate;
- (id)initWithComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries:(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries *)outer$
                                                      withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1, this$0_, ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedAsMapEntries_$1, val$iterator_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedMultimap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol ComGoogleCommonCollectMultimap;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectForwardingMultimap_RESTRICT 1
#define ComGoogleCommonCollectForwardingMultimap_INCLUDE 1
#include "com/google/common/collect/ForwardingMultimap.h"

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedMultimap : ComGoogleCommonCollectForwardingMultimap < JavaIoSerializable > {
 @public
  id<ComGoogleCommonCollectMapConstraint> constraint_;
  id<ComGoogleCommonCollectMultimap> delegate__;
  id<JavaUtilCollection> entries__;
  id<JavaUtilMap> asMap__;
}

- (id)initWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)delegate
     withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<ComGoogleCommonCollectMultimap>)delegate;
- (id<JavaUtilMap>)asMap;
- (id<JavaUtilCollection>)entries;
- (id<JavaUtilCollection>)getWithId:(id)key;
- (BOOL)putWithId:(id)key
           withId:(id)value;
- (BOOL)putAllWithId:(id)key
withJavaLangIterable:(id<JavaLangIterable>)values;
- (BOOL)putAllWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)multimap;
- (id<JavaUtilCollection>)replaceValuesWithId:(id)key
                         withJavaLangIterable:(id<JavaLangIterable>)values;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap, constraint_, id<ComGoogleCommonCollectMapConstraint>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap, delegate__, id<ComGoogleCommonCollectMultimap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap, entries__, id<JavaUtilCollection>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_ConstrainedMultimap, asMap__, id<JavaUtilMap>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedListMultimap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedListMultimap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedListMultimap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaLangIterable;
@protocol JavaUtilList;

#define ComGoogleCommonCollectListMultimap_RESTRICT 1
#define ComGoogleCommonCollectListMultimap_INCLUDE 1
#include "com/google/common/collect/ListMultimap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedListMultimap : ComGoogleCommonCollectMapConstraints_ConstrainedMultimap < ComGoogleCommonCollectListMultimap > {
}

- (id)initWithComGoogleCommonCollectListMultimap:(id<ComGoogleCommonCollectListMultimap>)delegate
         withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilList>)getWithId:(id)key;
- (id<JavaUtilList>)removeAllWithId:(id)key;
- (id<JavaUtilList>)replaceValuesWithId:(id)key
                   withJavaLangIterable:(id<JavaLangIterable>)values;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaLangIterable;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectSetMultimap_INCLUDE 1
#include "com/google/common/collect/SetMultimap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap : ComGoogleCommonCollectMapConstraints_ConstrainedMultimap < ComGoogleCommonCollectSetMultimap > {
}

- (id)initWithComGoogleCommonCollectSetMultimap:(id<ComGoogleCommonCollectSetMultimap>)delegate
        withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilSet>)getWithId:(id)key;
- (id<JavaUtilSet>)entries;
- (id<JavaUtilSet>)removeAllWithId:(id)key;
- (id<JavaUtilSet>)replaceValuesWithId:(id)key
                  withJavaLangIterable:(id<JavaLangIterable>)values;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_ConstrainedSortedSetMultimap_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_ConstrainedSortedSetMultimap_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_ConstrainedSortedSetMultimap_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaLangIterable;
@protocol JavaUtilComparator;
@protocol JavaUtilSortedSet;

#define ComGoogleCommonCollectSortedSetMultimap_RESTRICT 1
#define ComGoogleCommonCollectSortedSetMultimap_INCLUDE 1
#include "com/google/common/collect/SortedSetMultimap.h"

@interface ComGoogleCommonCollectMapConstraints_ConstrainedSortedSetMultimap : ComGoogleCommonCollectMapConstraints_ConstrainedSetMultimap < ComGoogleCommonCollectSortedSetMultimap > {
}

- (id)initWithComGoogleCommonCollectSortedSetMultimap:(id<ComGoogleCommonCollectSortedSetMultimap>)delegate
              withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)constraint;
- (id<JavaUtilSortedSet>)getWithId:(id)key;
- (id<JavaUtilSortedSet>)removeAllWithId:(id)key;
- (id<JavaUtilSortedSet>)replaceValuesWithId:(id)key
                        withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<JavaUtilComparator>)valueComparator;
@end
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_$1_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectForwardingMapEntry_RESTRICT 1
#define ComGoogleCommonCollectForwardingMapEntry_INCLUDE 1
#include "com/google/common/collect/ForwardingMapEntry.h"

@interface ComGoogleCommonCollectMapConstraints_$1 : ComGoogleCommonCollectForwardingMapEntry {
 @public
  id<JavaUtilMap_Entry> val$entry_;
  id<ComGoogleCommonCollectMapConstraint> val$constraint_;
}

- (id<JavaUtilMap_Entry>)delegate;
- (id)setValueWithId:(id)value;
- (id)initWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)capture$0
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_$1, val$entry_, id<JavaUtilMap_Entry>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_$1, val$constraint_, id<ComGoogleCommonCollectMapConstraint>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_$2_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_$2_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_$2_

@protocol ComGoogleCommonCollectMapConstraint;
@protocol JavaUtilCollection;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectForwardingMapEntry_RESTRICT 1
#define ComGoogleCommonCollectForwardingMapEntry_INCLUDE 1
#include "com/google/common/collect/ForwardingMapEntry.h"

@interface ComGoogleCommonCollectMapConstraints_$2 : ComGoogleCommonCollectForwardingMapEntry {
 @public
  id<JavaUtilMap_Entry> val$entry_;
  id<ComGoogleCommonCollectMapConstraint> val$constraint_;
}

- (id<JavaUtilMap_Entry>)delegate;
- (id<JavaUtilCollection>)getValue;
- (id)initWithJavaUtilMap_Entry:(id<JavaUtilMap_Entry>)capture$0
withComGoogleCommonCollectMapConstraint:(id<ComGoogleCommonCollectMapConstraint>)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_$2, val$entry_, id<JavaUtilMap_Entry>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_$2, val$constraint_, id<ComGoogleCommonCollectMapConstraint>)
#endif

#if !defined (_ComGoogleCommonCollectMapConstraints_$2_$1_) && (ComGoogleCommonCollectMapConstraints_INCLUDE_ALL || ComGoogleCommonCollectMapConstraints_$2_$1_INCLUDE)
#define _ComGoogleCommonCollectMapConstraints_$2_$1_

@class ComGoogleCommonCollectMapConstraints_$2;

#define ComGoogleCommonCollectConstraint_RESTRICT 1
#define ComGoogleCommonCollectConstraint_INCLUDE 1
#include "com/google/common/collect/Constraint.h"

@interface ComGoogleCommonCollectMapConstraints_$2_$1 : NSObject < ComGoogleCommonCollectConstraint > {
 @public
  ComGoogleCommonCollectMapConstraints_$2 *this$0_;
}

- (id)checkElementWithId:(id)value;
- (NSString *)description;
- (id)initWithComGoogleCommonCollectMapConstraints_$2:(ComGoogleCommonCollectMapConstraints_$2 *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectMapConstraints_$2_$1, this$0_, ComGoogleCommonCollectMapConstraints_$2 *)
#endif
