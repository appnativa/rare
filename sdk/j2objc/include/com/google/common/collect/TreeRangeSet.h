//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/TreeRangeSet.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectTreeRangeSet_RESTRICT
#define ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectTreeRangeSet_RESTRICT
#if ComGoogleCommonCollectTreeRangeSet_SubRangeSet_INCLUDE
#define ComGoogleCommonCollectTreeRangeSet_INCLUDE 1
#endif
#if ComGoogleCommonCollectTreeRangeSet_Complement_INCLUDE
#define ComGoogleCommonCollectTreeRangeSet_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_AsRanges_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_AsRanges_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_AsRanges_

@class ComGoogleCommonCollectTreeRangeSet;
@protocol JavaUtilCollection;

#define ComGoogleCommonCollectForwardingCollection_RESTRICT 1
#define ComGoogleCommonCollectForwardingCollection_INCLUDE 1
#include "com/google/common/collect/ForwardingCollection.h"

#define JavaUtilSet_RESTRICT 1
#define JavaUtilSet_INCLUDE 1
#include "java/util/Set.h"

@interface ComGoogleCommonCollectTreeRangeSet_AsRanges : ComGoogleCommonCollectForwardingCollection < JavaUtilSet > {
 @public
  __weak ComGoogleCommonCollectTreeRangeSet *this$0_;
}

- (id<JavaUtilCollection>)delegate;
- (NSUInteger)hash;
- (BOOL)isEqual:(id)o;
- (id)initWithComGoogleCommonCollectTreeRangeSet:(ComGoogleCommonCollectTreeRangeSet *)outer$;
@end
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectRange;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilNavigableMap;

#define ComGoogleCommonCollectAbstractNavigableMap_RESTRICT 1
#define ComGoogleCommonCollectAbstractNavigableMap_INCLUDE 1
#include "com/google/common/collect/AbstractNavigableMap.h"

@interface ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound : ComGoogleCommonCollectAbstractNavigableMap {
 @public
  id<JavaUtilNavigableMap> rangesByLowerBound_;
  ComGoogleCommonCollectRange *upperBoundWindow_;
}

- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)rangesByLowerBound;
- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)rangesByLowerBound
   withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)upperBoundWindow;
- (id<JavaUtilNavigableMap>)subMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)window;
- (id<JavaUtilNavigableMap>)subMapWithId:(ComGoogleCommonCollectCut *)fromKey
                             withBoolean:(BOOL)fromInclusive
                                  withId:(ComGoogleCommonCollectCut *)toKey
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(ComGoogleCommonCollectCut *)toKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(ComGoogleCommonCollectCut *)fromKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilComparator>)comparator;
- (BOOL)containsKeyWithId:(id)key;
- (ComGoogleCommonCollectRange *)getWithId:(id)key;
- (id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilIterator>)descendingEntryIterator;
- (int)size;
- (BOOL)isEmpty;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound, rangesByLowerBound_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound, upperBoundWindow_, ComGoogleCommonCollectRange *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1_

@class ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *this$0_;
  id<JavaUtilIterator> val$backingItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_RangesByUpperBound:(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *)outer$
                                               withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1, this$0_, ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$1, val$backingItr_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2_

@class ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound;
@protocol ComGoogleCommonCollectPeekingIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *this$0_;
  id<ComGoogleCommonCollectPeekingIterator> val$backingItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_RangesByUpperBound:(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *)outer$
                          withComGoogleCommonCollectPeekingIterator:(id<ComGoogleCommonCollectPeekingIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2, this$0_, ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_RangesByUpperBound_$2, val$backingItr_, id<ComGoogleCommonCollectPeekingIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectRange;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilNavigableMap;

#define ComGoogleCommonCollectAbstractNavigableMap_RESTRICT 1
#define ComGoogleCommonCollectAbstractNavigableMap_INCLUDE 1
#include "com/google/common/collect/AbstractNavigableMap.h"

@interface ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound : ComGoogleCommonCollectAbstractNavigableMap {
 @public
  id<JavaUtilNavigableMap> positiveRangesByLowerBound_;
  id<JavaUtilNavigableMap> positiveRangesByUpperBound_;
  ComGoogleCommonCollectRange *complementLowerBoundWindow_;
}

- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)positiveRangesByLowerBound;
- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)positiveRangesByLowerBound
   withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)window;
- (id<JavaUtilNavigableMap>)subMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)subWindow;
- (id<JavaUtilNavigableMap>)subMapWithId:(ComGoogleCommonCollectCut *)fromKey
                             withBoolean:(BOOL)fromInclusive
                                  withId:(ComGoogleCommonCollectCut *)toKey
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(ComGoogleCommonCollectCut *)toKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(ComGoogleCommonCollectCut *)fromKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilComparator>)comparator;
- (id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilIterator>)descendingEntryIterator;
- (int)size;
- (ComGoogleCommonCollectRange *)getWithId:(id)key;
- (BOOL)containsKeyWithId:(id)key;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound, positiveRangesByLowerBound_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound, positiveRangesByUpperBound_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound, complementLowerBoundWindow_, ComGoogleCommonCollectRange *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound;
@protocol ComGoogleCommonCollectPeekingIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *this$0_;
  ComGoogleCommonCollectCut *nextComplementRangeLowerBound_;
  ComGoogleCommonCollectCut *val$firstComplementRangeLowerBound_;
  id<ComGoogleCommonCollectPeekingIterator> val$positiveItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound:(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *)outer$
                                                withComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)capture$0
                                    withComGoogleCommonCollectPeekingIterator:(id<ComGoogleCommonCollectPeekingIterator>)capture$1;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1, this$0_, ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1, nextComplementRangeLowerBound_, ComGoogleCommonCollectCut *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1, val$firstComplementRangeLowerBound_, ComGoogleCommonCollectCut *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$1, val$positiveItr_, id<ComGoogleCommonCollectPeekingIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound;
@protocol ComGoogleCommonCollectPeekingIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *this$0_;
  ComGoogleCommonCollectCut *nextComplementRangeUpperBound_;
  ComGoogleCommonCollectCut *val$firstComplementRangeUpperBound_;
  id<ComGoogleCommonCollectPeekingIterator> val$positiveItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound:(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *)outer$
                                                withComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)capture$0
                                    withComGoogleCommonCollectPeekingIterator:(id<ComGoogleCommonCollectPeekingIterator>)capture$1;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2 *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2, this$0_, ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2, nextComplementRangeUpperBound_, ComGoogleCommonCollectCut *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2, val$firstComplementRangeUpperBound_, ComGoogleCommonCollectCut *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_ComplementRangesByLowerBound_$2, val$positiveItr_, id<ComGoogleCommonCollectPeekingIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_

@class ComGoogleCommonCollectRange;
@protocol ComGoogleCommonCollectRangeSet;
@protocol JavaLangComparable;
@protocol JavaUtilNavigableMap;
@protocol JavaUtilSet;

#define ComGoogleCommonCollectAbstractRangeSet_RESTRICT 1
#define ComGoogleCommonCollectAbstractRangeSet_INCLUDE 1
#include "com/google/common/collect/AbstractRangeSet.h"

@interface ComGoogleCommonCollectTreeRangeSet : ComGoogleCommonCollectAbstractRangeSet {
 @public
  id<JavaUtilNavigableMap> rangesByLowerBound_;
  id<JavaUtilSet> asRanges__;
  id<ComGoogleCommonCollectRangeSet> complement__;
}

+ (ComGoogleCommonCollectTreeRangeSet *)create;
+ (ComGoogleCommonCollectTreeRangeSet *)createWithComGoogleCommonCollectRangeSet:(id<ComGoogleCommonCollectRangeSet>)rangeSet;
- (id)initWithJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)rangesByLowerCut;
- (id<JavaUtilSet>)asRanges;
- (ComGoogleCommonCollectRange *)rangeContainingWithId:(id<JavaLangComparable>)value;
- (BOOL)enclosesWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (ComGoogleCommonCollectRange *)rangeEnclosingWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (ComGoogleCommonCollectRange *)span;
- (void)addWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToAdd;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToRemove;
- (void)replaceRangeWithSameLowerBoundWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (id<ComGoogleCommonCollectRangeSet>)complement;
- (id<ComGoogleCommonCollectRangeSet>)subRangeSetWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)view;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet, rangesByLowerBound_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet, asRanges__, id<JavaUtilSet>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet, complement__, id<ComGoogleCommonCollectRangeSet>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_Complement_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_Complement_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_Complement_

@class ComGoogleCommonCollectRange;
@protocol ComGoogleCommonCollectRangeSet;
@protocol JavaLangComparable;

@interface ComGoogleCommonCollectTreeRangeSet_Complement : ComGoogleCommonCollectTreeRangeSet {
 @public
  ComGoogleCommonCollectTreeRangeSet *this$0_;
}

- (id)initWithComGoogleCommonCollectTreeRangeSet:(ComGoogleCommonCollectTreeRangeSet *)outer$;
- (void)addWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToAdd;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToRemove;
- (BOOL)containsWithId:(id<JavaLangComparable>)value;
- (id<ComGoogleCommonCollectRangeSet>)complement;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_Complement, this$0_, ComGoogleCommonCollectTreeRangeSet *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectRange;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilNavigableMap;

#define ComGoogleCommonCollectAbstractNavigableMap_RESTRICT 1
#define ComGoogleCommonCollectAbstractNavigableMap_INCLUDE 1
#include "com/google/common/collect/AbstractNavigableMap.h"

@interface ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound : ComGoogleCommonCollectAbstractNavigableMap {
 @public
  ComGoogleCommonCollectRange *lowerBoundWindow_;
  ComGoogleCommonCollectRange *restriction_;
  id<JavaUtilNavigableMap> rangesByLowerBound_;
  id<JavaUtilNavigableMap> rangesByUpperBound_;
}

- (id)initWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)lowerBoundWindow
          withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)restriction
                 withJavaUtilNavigableMap:(id<JavaUtilNavigableMap>)rangesByLowerBound;
- (id<JavaUtilNavigableMap>)subMapWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)window;
- (id<JavaUtilNavigableMap>)subMapWithId:(ComGoogleCommonCollectCut *)fromKey
                             withBoolean:(BOOL)fromInclusive
                                  withId:(ComGoogleCommonCollectCut *)toKey
                             withBoolean:(BOOL)toInclusive;
- (id<JavaUtilNavigableMap>)headMapWithId:(ComGoogleCommonCollectCut *)toKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilNavigableMap>)tailMapWithId:(ComGoogleCommonCollectCut *)fromKey
                              withBoolean:(BOOL)inclusive;
- (id<JavaUtilComparator>)comparator;
- (BOOL)containsKeyWithId:(id)key;
- (ComGoogleCommonCollectRange *)getWithId:(id)key;
- (id<JavaUtilIterator>)entryIterator;
- (id<JavaUtilIterator>)descendingEntryIterator;
- (int)size;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound, lowerBoundWindow_, ComGoogleCommonCollectRange *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound, restriction_, ComGoogleCommonCollectRange *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound, rangesByLowerBound_, id<JavaUtilNavigableMap>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound, rangesByUpperBound_, id<JavaUtilNavigableMap>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1_

@class ComGoogleCommonCollectCut;
@class ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *this$0_;
  id<JavaUtilIterator> val$completeRangeItr_;
  ComGoogleCommonCollectCut *val$upperBoundOnLowerBounds_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound:(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *)outer$
                                                          withJavaUtilIterator:(id<JavaUtilIterator>)capture$0
                                                 withComGoogleCommonCollectCut:(ComGoogleCommonCollectCut *)capture$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1, this$0_, ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1, val$completeRangeItr_, id<JavaUtilIterator>)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$1, val$upperBoundOnLowerBounds_, ComGoogleCommonCollectCut *)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2_

@class ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound;
@protocol JavaUtilIterator;
@protocol JavaUtilMap_Entry;

#define ComGoogleCommonCollectAbstractIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIterator.h"

@interface ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2 : ComGoogleCommonCollectAbstractIterator {
 @public
  ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *this$0_;
  id<JavaUtilIterator> val$completeRangeItr_;
}

- (id<JavaUtilMap_Entry>)computeNext;
- (id)initWithComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound:(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *)outer$
                                                          withJavaUtilIterator:(id<JavaUtilIterator>)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2, this$0_, ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSetRangesByLowerBound_$2, val$completeRangeItr_, id<JavaUtilIterator>)
#endif

#if !defined (_ComGoogleCommonCollectTreeRangeSet_SubRangeSet_) && (ComGoogleCommonCollectTreeRangeSet_INCLUDE_ALL || ComGoogleCommonCollectTreeRangeSet_SubRangeSet_INCLUDE)
#define _ComGoogleCommonCollectTreeRangeSet_SubRangeSet_

@class ComGoogleCommonCollectRange;
@protocol ComGoogleCommonCollectRangeSet;
@protocol JavaLangComparable;

@interface ComGoogleCommonCollectTreeRangeSet_SubRangeSet : ComGoogleCommonCollectTreeRangeSet {
 @public
  ComGoogleCommonCollectTreeRangeSet *this$0_;
  ComGoogleCommonCollectRange *restriction_;
}

- (id)initWithComGoogleCommonCollectTreeRangeSet:(ComGoogleCommonCollectTreeRangeSet *)outer$
                 withComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)restriction;
- (BOOL)enclosesWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)range;
- (ComGoogleCommonCollectRange *)rangeContainingWithId:(id<JavaLangComparable>)value;
- (void)addWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToAdd;
- (void)removeWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)rangeToRemove;
- (BOOL)containsWithId:(id<JavaLangComparable>)value;
- (void)clear;
- (id<ComGoogleCommonCollectRangeSet>)subRangeSetWithComGoogleCommonCollectRange:(ComGoogleCommonCollectRange *)view;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectTreeRangeSet_SubRangeSet *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSet, this$0_, ComGoogleCommonCollectTreeRangeSet *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectTreeRangeSet_SubRangeSet, restriction_, ComGoogleCommonCollectRange *)
#endif
