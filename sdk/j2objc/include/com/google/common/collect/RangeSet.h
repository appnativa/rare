//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/RangeSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectRangeSet_RESTRICT
#define ComGoogleCommonCollectRangeSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectRangeSet_RESTRICT

#if !defined (_ComGoogleCommonCollectRangeSet_) && (ComGoogleCommonCollectRangeSet_INCLUDE_ALL || ComGoogleCommonCollectRangeSet_INCLUDE)
#define _ComGoogleCommonCollectRangeSet_

@class ComGoogleCommonCollectRange;
@protocol JavaLangComparable;
@protocol JavaUtilSet;

@protocol ComGoogleCommonCollectRangeSet < NSObject, JavaObject >
- (BOOL)containsWithId:(id<JavaLangComparable>)value;
- (ComGoogleCommonCollectRange *)rangeContainingWithId:(id<JavaLangComparable>)value;
- (BOOL)enclosesWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)otherRange;
- (BOOL)enclosesAllWithComGoogleCommonCollectRangeSet:(id<ComGoogleCommonCollectRangeSet>)other;
- (BOOL)isEmpty;
- (ComGoogleCommonCollectRange *)span;
- (id<JavaUtilSet>)asRanges;
- (id<ComGoogleCommonCollectRangeSet>)complement;
- (id<ComGoogleCommonCollectRangeSet>)subRangeSetWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)view;
- (void)addWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (void)clear;
- (void)addAllWithComGoogleCommonCollectRangeSet:(id<ComGoogleCommonCollectRangeSet>)other;
- (void)removeAllWithComGoogleCommonCollectRangeSet:(id<ComGoogleCommonCollectRangeSet>)other;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (NSString *)description;
@end
#endif
