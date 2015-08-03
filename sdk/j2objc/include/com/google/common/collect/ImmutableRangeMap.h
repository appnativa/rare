//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableRangeMap.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableRangeMap_RESTRICT
#define ComGoogleCommonCollectImmutableRangeMap_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableRangeMap_RESTRICT
#if ComGoogleCommonCollectImmutableRangeMap_$2_INCLUDE
#define ComGoogleCommonCollectImmutableRangeMap_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectImmutableRangeMap_Builder_) && (ComGoogleCommonCollectImmutableRangeMap_INCLUDE_ALL || ComGoogleCommonCollectImmutableRangeMap_Builder_INCLUDE)
#define _ComGoogleCommonCollectImmutableRangeMap_Builder_

@class ComGoogleCommonCollectImmutableRangeMap;
@class ComGoogleCommonCollectRange;
@protocol ComGoogleCommonCollectRangeMap;
@protocol ComGoogleCommonCollectRangeSet;

@interface ComGoogleCommonCollectImmutableRangeMap_Builder : NSObject {
 @public
  id<ComGoogleCommonCollectRangeSet> keyRanges_;
  id<ComGoogleCommonCollectRangeMap> rangeMap_;
}

- (id)init;
- (ComGoogleCommonCollectImmutableRangeMap_Builder *)putWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                                                                 withId:(id)value;
- (ComGoogleCommonCollectImmutableRangeMap_Builder *)putAllWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap;
- (ComGoogleCommonCollectImmutableRangeMap *)build;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableRangeMap_Builder *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_Builder, keyRanges_, id<ComGoogleCommonCollectRangeSet>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_Builder, rangeMap_, id<ComGoogleCommonCollectRangeMap>)
#endif

#if !defined (_ComGoogleCommonCollectImmutableRangeMap_$1_) && (ComGoogleCommonCollectImmutableRangeMap_INCLUDE_ALL || ComGoogleCommonCollectImmutableRangeMap_$1_INCLUDE)
#define _ComGoogleCommonCollectImmutableRangeMap_$1_

@class ComGoogleCommonCollectImmutableRangeMap;
@class ComGoogleCommonCollectRange;

#define ComGoogleCommonCollectImmutableList_RESTRICT 1
#define ComGoogleCommonCollectImmutableList_INCLUDE 1
#include "com/google/common/collect/ImmutableList.h"

@interface ComGoogleCommonCollectImmutableRangeMap_$1 : ComGoogleCommonCollectImmutableList {
 @public
  ComGoogleCommonCollectImmutableRangeMap *this$0_;
  int val$len_;
  int val$off_;
  ComGoogleCommonCollectRange *val$range_;
}

- (int)size;
- (ComGoogleCommonCollectRange *)getWithInt:(int)index;
- (BOOL)isPartialView;
- (id)initWithComGoogleCommonCollectImmutableRangeMap:(ComGoogleCommonCollectImmutableRangeMap *)outer$
                                              withInt:(int)capture$0
                                              withInt:(int)capture$1
                      withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)capture$2;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_$1, this$0_, ComGoogleCommonCollectImmutableRangeMap *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_$1, val$range_, ComGoogleCommonCollectRange *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableRangeMap_) && (ComGoogleCommonCollectImmutableRangeMap_INCLUDE_ALL || ComGoogleCommonCollectImmutableRangeMap_INCLUDE)
#define _ComGoogleCommonCollectImmutableRangeMap_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectImmutableMap;
@class ComGoogleCommonCollectImmutableRangeMap_Builder;
@class ComGoogleCommonCollectRange;
@protocol JavaLangComparable;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectRangeMap_RESTRICT 1
#define ComGoogleCommonCollectRangeMap_INCLUDE 1
#include "com/google/common/collect/RangeMap.h"

@interface ComGoogleCommonCollectImmutableRangeMap : NSObject < ComGoogleCommonCollectRangeMap > {
 @public
  ComGoogleCommonCollectImmutableList *ranges_;
  ComGoogleCommonCollectImmutableList *values_;
}

+ (ComGoogleCommonCollectImmutableRangeMap *)EMPTY;
+ (ComGoogleCommonCollectImmutableRangeMap *)of;
+ (ComGoogleCommonCollectImmutableRangeMap *)ofWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                                                        withId:(id)value;
+ (ComGoogleCommonCollectImmutableRangeMap *)copyOfWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableRangeMap_Builder *)builder;
- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)ranges
          withComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)values;
- (id)getWithId:(id<JavaLangComparable>)key;
- (id<JavaUtilMap_Entry>)getEntryWithId:(id<JavaLangComparable>)key;
- (ComGoogleCommonCollectRange *)span;
- (void)putWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range
                                    withId:(id)value;
- (void)putAllWithComGoogleCommonCollectRangeMap:(id<ComGoogleCommonCollectRangeMap>)rangeMap;
- (void)clear;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (ComGoogleCommonCollectImmutableMap *)asMapOfRanges;
- (ComGoogleCommonCollectImmutableRangeMap *)subRangeMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)o;
- (NSString *)description;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableRangeMap *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap, ranges_, ComGoogleCommonCollectImmutableList *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap, values_, ComGoogleCommonCollectImmutableList *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableRangeMap_$2_) && (ComGoogleCommonCollectImmutableRangeMap_INCLUDE_ALL || ComGoogleCommonCollectImmutableRangeMap_$2_INCLUDE)
#define _ComGoogleCommonCollectImmutableRangeMap_$2_

@class ComGoogleCommonCollectImmutableList;
@class ComGoogleCommonCollectRange;

@interface ComGoogleCommonCollectImmutableRangeMap_$2 : ComGoogleCommonCollectImmutableRangeMap {
 @public
  ComGoogleCommonCollectRange *val$range_;
  ComGoogleCommonCollectImmutableRangeMap *val$outer_;
}

- (ComGoogleCommonCollectImmutableRangeMap *)subRangeMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)subRange;
- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)arg$0
          withComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)arg$1
                  withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)capture$0
      withComGoogleCommonCollectImmutableRangeMap:(ComGoogleCommonCollectImmutableRangeMap *)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_$2, val$range_, ComGoogleCommonCollectRange *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableRangeMap_$2, val$outer_, ComGoogleCommonCollectImmutableRangeMap *)
#endif
