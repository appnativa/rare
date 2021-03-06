//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/FilteredKeyMultimap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectFilteredKeyMultimap_RESTRICT
#define ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectFilteredKeyMultimap_RESTRICT

#if !defined (_ComGoogleCommonCollectFilteredKeyMultimap_) && (ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE_ALL || ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE)
#define _ComGoogleCommonCollectFilteredKeyMultimap_

@protocol ComGoogleCommonBasePredicate;
@protocol ComGoogleCommonCollectMultimap;
@protocol ComGoogleCommonCollectMultiset;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectFilteredMultimap_RESTRICT 1
#define ComGoogleCommonCollectFilteredMultimap_INCLUDE 1
#include "com/google/common/collect/FilteredMultimap.h"

@interface ComGoogleCommonCollectFilteredKeyMultimap : ComGoogleCommonCollectFilteredMultimap {
 @public
  id<ComGoogleCommonBasePredicate> keyPredicate_;
}

- (id)initWithComGoogleCommonCollectMultimap:(id<ComGoogleCommonCollectMultimap>)unfiltered
            withComGoogleCommonBasePredicate:(id<ComGoogleCommonBasePredicate>)keyPredicate;
- (id<ComGoogleCommonBasePredicate>)entryPredicate;
- (int)size;
- (BOOL)containsKeyWithId:(id)key;
- (id<JavaUtilCollection>)removeAllWithId:(id)key;
- (id<JavaUtilCollection>)unmodifiableEmptyCollection;
- (void)clear;
- (id<JavaUtilSet>)createKeySet;
- (id<JavaUtilCollection>)getWithId:(id)key;
- (id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilCollection>)createEntries;
- (id<JavaUtilMap>)createAsMap;
- (id<ComGoogleCommonCollectMultiset>)createKeys;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectFilteredKeyMultimap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectFilteredKeyMultimap, keyPredicate_, id<ComGoogleCommonBasePredicate>)
#endif

#if !defined (_ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet_) && (ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE_ALL || ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet_INCLUDE)
#define _ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet_

@protocol JavaUtilCollection;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectForwardingSet_RESTRICT 1
#define ComGoogleCommonCollectForwardingSet_INCLUDE 1
#include "com/google/common/collect/ForwardingSet.h"

@interface ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet : ComGoogleCommonCollectForwardingSet {
 @public
  id key_;
}

- (id)initWithId:(id)key;
- (BOOL)addWithId:(id)element;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (id<JavaUtilSet>)delegate;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingSet, key_, id)
#endif

#if !defined (_ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList_) && (ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE_ALL || ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList_INCLUDE)
#define _ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList_

@protocol JavaUtilCollection;
@protocol JavaUtilList;

#define ComGoogleCommonCollectForwardingList_RESTRICT 1
#define ComGoogleCommonCollectForwardingList_INCLUDE 1
#include "com/google/common/collect/ForwardingList.h"

@interface ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList : ComGoogleCommonCollectForwardingList {
 @public
  id key_;
}

- (id)initWithId:(id)key;
- (BOOL)addWithId:(id)v;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)addWithInt:(int)index
            withId:(id)element;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)elements;
- (id<JavaUtilList>)delegate;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectFilteredKeyMultimap_AddRejectingList, key_, id)
#endif

#if !defined (_ComGoogleCommonCollectFilteredKeyMultimap_createEntries_FilteredKeyMultimapEntries_) && (ComGoogleCommonCollectFilteredKeyMultimap_INCLUDE_ALL || ComGoogleCommonCollectFilteredKeyMultimap_createEntries_FilteredKeyMultimapEntries_INCLUDE)
#define _ComGoogleCommonCollectFilteredKeyMultimap_createEntries_FilteredKeyMultimapEntries_

@class ComGoogleCommonCollectFilteredKeyMultimap;
@protocol ComGoogleCommonCollectMultimap;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectMultimaps_RESTRICT 1
#define ComGoogleCommonCollectMultimaps_Entries_INCLUDE 1
#include "com/google/common/collect/Multimaps.h"

@interface ComGoogleCommonCollectFilteredKeyMultimap_createEntries_FilteredKeyMultimapEntries : ComGoogleCommonCollectMultimaps_Entries {
 @public
  __weak ComGoogleCommonCollectFilteredKeyMultimap *this$0_;
}

- (id<ComGoogleCommonCollectMultimap>)multimap;
- (id<JavaUtilIterator>)iterator;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)initWithComGoogleCommonCollectFilteredKeyMultimap:(ComGoogleCommonCollectFilteredKeyMultimap *)outer$;
@end
#endif
