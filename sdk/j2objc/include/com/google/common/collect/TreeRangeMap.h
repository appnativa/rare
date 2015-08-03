//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/TreeRangeMap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectTreeRangeMap_RESTRICT
#define ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectTreeRangeMap_RESTRICT

#if !defined (_ComGoogleCommonCollectTreeRangeMap_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectRange;
@protocol JavaLangComparable;
@protocol JavaUtilMap;
@protocol JavaUtilMap_Entry;
@protocol JavaUtilNavigableMap;

#define ComGoogleCommonCollectRangeMap_RESTRICT 1
#define ComGoogleCommonCollectRangeMap_INCLUDE 1
#include "com/google/common/collect/RangeMap.h"

@interface ComGoogleCommonCollectTreeRangeMap : NSObject < ComGoogleCommonCollectRangeMap > {
 @public
  id<JavaUtilNavigableMap> entriesByLowerBound_;
}

+ (id<ComGoogleCommonCollectRangeMap>)EMPTY_SUB_RANGE_MAP;
+ (ComGoogleCommonCollectTreeRangeMap *)create;
- (id)init;
- (id)getWithId:(id<JavaLangComparable>)key;
- (id<JavaUtilMap_Entry>)getEntryWithId:(id<JavaLangComparable>)key;
- (void)putWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                    withId:(id)value;
- (void)putAllWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap;
- (void)clear;
- (ComGoogleCommonCollectRange *)span;
- (void)putRangeMapEntryWithComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)lowerBound
                        withComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)upperBound
                                               withId:(id)value;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToRemove;
- (id<JavaUtilMap>)asMapOfRanges;
- (id<ComGoogleCommonCollectRangeMap>)subRangeMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)subRange;
- (id<ComGoogleCommonCollectRangeMap>)emptySubRangeMap;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap, entriesByLowerBound_, id<JavaUtilNavigableMap>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_RangeMapEntry_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_RangeMapEntry_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_RangeMapEntry_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectRange;
@protocol JavaLangComparable;

#define ComGoogleCommonCollectAbstractMapEntry_RESTRICT 1
#define ComGoogleCommonCollectAbstractMapEntry_INCLUDE 1
#include "com/google/common/collect/AbstractMapEntry.h"

@interface ComGoogleCommonCollectTreeRangeMap_RangeMapEntry : ComGoogleCommonCollectAbstractMapEntry {
 @public
  ComGoogleCommonCollectRange *range_;
  id value_;
}

- (id)initWithComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)lowerBound
          withComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)upperBound
                                 withId:(id)value;
- (id)initWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                   withId:(id)value;
- (ComGoogleCommonCollectRange *)getKey;
- (id)getValue;
- (BOOL)containsWithId:(id<JavaLangComparable>)value;
- (ComGoogleCommonCollectCut *)getLowerBound;
- (ComGoogleCommonCollectCut *)getUpperBound;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeMap_RangeMapEntry *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_RangeMapEntry, range_, ComGoogleCommonCollectRange *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_RangeMapEntry, value_, id)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_

@class ComGoogleCommonCollectTreeRangeMap;
@protocol JavaUtilSet;

#define JavaUtilAbstractMap_RESTRICT 1
#define JavaUtilAbstractMap_INCLUDE 1
#include "java/util/AbstractMap.h"

@interface ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges : JavaUtilAbstractMap {
 @public
  ComGoogleCommonCollectTreeRangeMap *this$0_;
}

- (BOOL)containsKeyWithId:(id)key;
- (id)getWithId:(id)key;
- (id<JavaUtilSet>)entrySet;
- (id)initWithComGoogleCommonCollectTreeRangeMap:(ComGoogleCommonCollectTreeRangeMap *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges, this$0_, ComGoogleCommonCollectTreeRangeMap *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_$1_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_$1_

@class ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges;
@protocol JavaUtilIterator;

#define JavaUtilAbstractSet_RESTRICT 1
#define JavaUtilAbstractSet_INCLUDE 1
#include "java/util/AbstractSet.h"

@interface ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_$1 : JavaUtilAbstractSet {
 @public
  ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges *this$0_;
}

- (id<JavaUtilIterator>)iterator;
- (int)size;
- (id)initWithComGoogleCommonCollectTreeRangeMap_AsMapOfRanges:(ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges_$1, this$0_, ComGoogleCommonCollectTreeRangeMap_AsMapOfRanges *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_

@class ComGoogleCommonCollectRange;
@class ComGoogleCommonCollectTreeRangeMap;
@protocol JavaLangComparable;
@protocol JavaUtilMap;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectRangeMap_RESTRICT 1
#define ComGoogleCommonCollectRangeMap_INCLUDE 1
#include "com/google/common/collect/RangeMap.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap : NSObject < ComGoogleCommonCollectRangeMap > {
 @public
  ComGoogleCommonCollectTreeRangeMap *this$0_;
  ComGoogleCommonCollectRange *subRange_;
}

- (id)initWithComGoogleCommonCollectTreeRangeMap:(ComGoogleCommonCollectTreeRangeMap *)outer$
                 withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)subRange;
- (id)getWithId:(id<JavaLangComparable>)key;
- (id<JavaUtilMap_Entry>)getEntryWithId:(id<JavaLangComparable>)key;
- (ComGoogleCommonCollectRange *)span;
- (void)putWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                    withId:(id)value;
- (void)putAllWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap;
- (void)clear;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (id<ComGoogleCommonCollectRangeMap>)subRangeMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (id<JavaUtilMap>)asMapOfRanges;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap, this$0_, ComGoogleCommonCollectTreeRangeMap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap, subRange_, ComGoogleCommonCollectRange *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_

@class ComGoogleCommonCollectTreeRangeMap_SubRangeMap;
@protocol ComGoogleCommonBasePredicate;
@protocol JavaUtilCollection;
@protocol JavaUtilSet;

#define JavaUtilAbstractMap_RESTRICT 1
#define JavaUtilAbstractMap_INCLUDE 1
#include "java/util/AbstractMap.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap : JavaUtilAbstractMap {
 @public
  ComGoogleCommonCollectTreeRangeMap_SubRangeMap *this$0_;
}

- (BOOL)containsKeyWithId:(id)key;
- (id)getWithId:(id)key;
- (id)removeWithId:(id)key;
- (void)clear;
- (BOOL)removeIfWithComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)predicate;
- (id<JavaUtilSet>)keySet;
- (id<JavaUtilSet>)entrySet;
- (id<JavaUtilCollection>)values;
- (id)initWithComGoogleCommonCollectTreeRangeMap_SubRangeMap:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap, this$0_, ComGoogleCommonCollectTreeRangeMap_SubRangeMap *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_keySet_SubRangeMapAsMapKeySet_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_keySet_SubRangeMapAsMapKeySet_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_keySet_SubRangeMapAsMapKeySet_

@class ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectMaps_RESTRICT 1
#define ComGoogleCommonCollectMaps_KeySet_INCLUDE 1
#include "com/google/common/collect/Maps.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_keySet_SubRangeMapAsMapKeySet : ComGoogleCommonCollectMaps_KeySet {
 @public
  __weak ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *this$0_;
}

- (id<JavaUtilMap>)map;
- (BOOL)removeWithId:(id)o;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)initWithComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *)outer$;
@end
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_values_SubRangeMapAsMapValuesCollection_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_values_SubRangeMapAsMapValuesCollection_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_values_SubRangeMapAsMapValuesCollection_

@class ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap;
@protocol JavaUtilCollection;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectMaps_RESTRICT 1
#define ComGoogleCommonCollectMaps_Values_INCLUDE 1
#include "com/google/common/collect/Maps.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_values_SubRangeMapAsMapValuesCollection : ComGoogleCommonCollectMaps_Values {
 @public
  __weak ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *this$0_;
}

- (id<JavaUtilMap>)map;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)initWithComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *)outer$;
@end
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_

@class ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilMap;

#define ComGoogleCommonCollectMaps_RESTRICT 1
#define ComGoogleCommonCollectMaps_EntrySet_INCLUDE 1
#include "com/google/common/collect/Maps.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1 : ComGoogleCommonCollectMaps_EntrySet {
 @public
  ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *this$0_;
}

- (id<JavaUtilMap>)map;
- (id<JavaUtilIterator>)iterator;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (int)size;
- (BOOL)isEmpty;
- (id)initWithComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *)outer$;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1, this$0_, ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1_

@class ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1 *this$0_;
  id<JavaUtilIterator> val$backingItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1:(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1 *)outer$
                                                            withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1, this$0_, ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1 *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeMap_SubRangeMap_SubRangeMapAsMap_$1_$1, val$backingItr_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeMap_$1_) && (ComGoogleCommonCollectTreeRangeMap_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeMap_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeMap_$1_

@class ComGoogleCommonCollectRange;
@protocol JavaLangComparable;
@protocol JavaUtilMap;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectRangeMap_RESTRICT 1
#define ComGoogleCommonCollectRangeMap_INCLUDE 1
#include "com/google/common/collect/RangeMap.h"

@interface ComGoogleCommonCollectTreeRangeMap_$1 : NSObject < ComGoogleCommonCollectRangeMap > {
}

- (id)getWithId:(id<JavaLangComparable>)key;
- (id<JavaUtilMap_Entry>)getEntryWithId:(id<JavaLangComparable>)key;
- (ComGoogleCommonCollectRange *)span;
- (void)putWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                    withId:(id)value;
- (void)putAllWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap;
- (void)clear;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (id<JavaUtilMap>)asMapOfRanges;
- (id<ComGoogleCommonCollectRangeMap>)subRangeMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (BOOL)isEqual:(id)param0;
- (NSUInteger)hash;
- (NSString *)description;
- (id)init;
@end
#endif