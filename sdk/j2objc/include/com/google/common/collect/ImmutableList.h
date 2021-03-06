//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/ImmutableList.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectImmutableList_RESTRICT
#define ComGoogleCommonCollectImmutableList_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectImmutableList_RESTRICT
#if ComGoogleCommonCollectImmutableList_ReverseImmutableList_INCLUDE
#define ComGoogleCommonCollectImmutableList_INCLUDE 1
#endif
#if ComGoogleCommonCollectImmutableList_SubList_INCLUDE
#define ComGoogleCommonCollectImmutableList_INCLUDE 1
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_

@class ComGoogleCommonCollectImmutableList_Builder;
@class ComGoogleCommonCollectUnmodifiableIterator;
@class ComGoogleCommonCollectUnmodifiableListIterator;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilCollection;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectImmutableCollection_RESTRICT 1
#define ComGoogleCommonCollectImmutableCollection_INCLUDE 1
#include "com/google/common/collect/ImmutableCollection.h"

#define JavaUtilList_RESTRICT 1
#define JavaUtilList_INCLUDE 1
#include "java/util/List.h"

#define JavaUtilRandomAccess_RESTRICT 1
#define JavaUtilRandomAccess_INCLUDE 1
#include "java/util/RandomAccess.h"

@interface ComGoogleCommonCollectImmutableList : ComGoogleCommonCollectImmutableCollection < JavaUtilList, JavaUtilRandomAccess > {
}

+ (ComGoogleCommonCollectImmutableList *)of;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)element;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7
                                           withId:(id)e8;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7
                                           withId:(id)e8
                                           withId:(id)e9;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7
                                           withId:(id)e8
                                           withId:(id)e9
                                           withId:(id)e10;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7
                                           withId:(id)e8
                                           withId:(id)e9
                                           withId:(id)e10
                                           withId:(id)e11;
+ (ComGoogleCommonCollectImmutableList *)ofWithId:(id)e1
                                           withId:(id)e2
                                           withId:(id)e3
                                           withId:(id)e4
                                           withId:(id)e5
                                           withId:(id)e6
                                           withId:(id)e7
                                           withId:(id)e8
                                           withId:(id)e9
                                           withId:(id)e10
                                           withId:(id)e11
                                           withId:(id)e12
                                withNSObjectArray:(IOSObjectArray *)others;
+ (ComGoogleCommonCollectImmutableList *)copyOfWithJavaLangIterable:(id<JavaLangIterable>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableList *)copyOfWithJavaUtilCollection:(id<JavaUtilCollection>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableList *)copyOfWithJavaUtilIterator:(id<JavaUtilIterator>)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableList *)copyOfWithNSObjectArray:(IOSObjectArray *)elements OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableList *)asImmutableListWithNSObjectArray:(IOSObjectArray *)elements;
+ (ComGoogleCommonCollectImmutableList *)copyFromCollectionWithJavaUtilCollection:(id<JavaUtilCollection>)collection OBJC_METHOD_FAMILY_NONE;
+ (ComGoogleCommonCollectImmutableList *)constructWithNSObjectArray:(IOSObjectArray *)elements;
- (id)init;
- (ComGoogleCommonCollectUnmodifiableIterator *)iterator;
- (ComGoogleCommonCollectUnmodifiableListIterator *)listIterator;
- (ComGoogleCommonCollectUnmodifiableListIterator *)listIteratorWithInt:(int)index;
- (int)indexOfWithId:(id)object;
- (int)lastIndexOfWithId:(id)object;
- (BOOL)containsWithId:(id)object;
- (ComGoogleCommonCollectImmutableList *)subListWithInt:(int)fromIndex
                                                withInt:(int)toIndex;
- (ComGoogleCommonCollectImmutableList *)subListUncheckedWithInt:(int)fromIndex
                                                         withInt:(int)toIndex;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)newElements;
- (id)setWithInt:(int)index
          withId:(id)element;
- (void)addWithInt:(int)index
            withId:(id)element;
- (id)removeWithInt:(int)index;
- (ComGoogleCommonCollectImmutableList *)asList;
- (ComGoogleCommonCollectImmutableList *)reverse;
- (BOOL)isEqual:(id)obj;
- (NSUInteger)hash;
- (id)writeReplace;
+ (ComGoogleCommonCollectImmutableList_Builder *)builder;
- (id)getWithInt:(int)param0;
- (int)size;
@end
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_SubList_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_SubList_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_SubList_

@interface ComGoogleCommonCollectImmutableList_SubList : ComGoogleCommonCollectImmutableList {
 @public
  ComGoogleCommonCollectImmutableList *this$0_;
  int offset_;
  int length_;
}

- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)outer$
                                          withInt:(int)offset
                                          withInt:(int)length;
- (int)size;
- (id)getWithInt:(int)index;
- (ComGoogleCommonCollectImmutableList *)subListWithInt:(int)fromIndex
                                                withInt:(int)toIndex;
- (BOOL)isPartialView;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableList_SubList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_SubList, this$0_, ComGoogleCommonCollectImmutableList *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_ReverseImmutableList_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_ReverseImmutableList_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_ReverseImmutableList_

@class ComGoogleCommonCollectUnmodifiableListIterator;
@protocol JavaUtilCollection;

@interface ComGoogleCommonCollectImmutableList_ReverseImmutableList : ComGoogleCommonCollectImmutableList {
 @public
  ComGoogleCommonCollectImmutableList *forwardList_;
  int size__;
}

- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)backingList;
- (int)reverseIndexWithInt:(int)index;
- (int)reversePositionWithInt:(int)index;
- (ComGoogleCommonCollectImmutableList *)reverse;
- (BOOL)containsWithId:(id)object;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)targets;
- (int)indexOfWithId:(id)object;
- (int)lastIndexOfWithId:(id)object;
- (ComGoogleCommonCollectImmutableList *)subListWithInt:(int)fromIndex
                                                withInt:(int)toIndex;
- (id)getWithInt:(int)index;
- (ComGoogleCommonCollectUnmodifiableListIterator *)listIteratorWithInt:(int)index;
- (int)size;
- (BOOL)isEmpty;
- (BOOL)isPartialView;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableList_ReverseImmutableList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_ReverseImmutableList, forwardList_, ComGoogleCommonCollectImmutableList *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1_

@class ComGoogleCommonCollectImmutableList_ReverseImmutableList;

#define ComGoogleCommonCollectUnmodifiableListIterator_RESTRICT 1
#define ComGoogleCommonCollectUnmodifiableListIterator_INCLUDE 1
#include "com/google/common/collect/UnmodifiableListIterator.h"

@interface ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1 : ComGoogleCommonCollectUnmodifiableListIterator {
 @public
  ComGoogleCommonCollectImmutableList_ReverseImmutableList *this$0_;
  ComGoogleCommonCollectUnmodifiableListIterator *val$forward_;
}

- (BOOL)hasNext;
- (BOOL)hasPrevious;
- (id)next;
- (int)nextIndex;
- (id)previous;
- (int)previousIndex;
- (id)initWithComGoogleCommonCollectImmutableList_ReverseImmutableList:(ComGoogleCommonCollectImmutableList_ReverseImmutableList *)outer$
                    withComGoogleCommonCollectUnmodifiableListIterator:(ComGoogleCommonCollectUnmodifiableListIterator *)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1, this$0_, ComGoogleCommonCollectImmutableList_ReverseImmutableList *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_ReverseImmutableList_$1, val$forward_, ComGoogleCommonCollectUnmodifiableListIterator *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_SerializedForm_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_SerializedForm_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_SerializedForm_

@class IOSObjectArray;

#define JavaIoSerializable_RESTRICT 1
#define JavaIoSerializable_INCLUDE 1
#include "java/io/Serializable.h"

#define ComGoogleCommonCollectImmutableList_SerializedForm_serialVersionUID 0

@interface ComGoogleCommonCollectImmutableList_SerializedForm : NSObject < JavaIoSerializable > {
 @public
  IOSObjectArray *elements_;
}

- (id)initWithNSObjectArray:(IOSObjectArray *)elements;
- (id)readResolve;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableList_SerializedForm *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_SerializedForm, elements_, IOSObjectArray *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_Builder_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_Builder_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_Builder_

@class ComGoogleCommonCollectImmutableList;
@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;

#define ComGoogleCommonCollectImmutableCollection_RESTRICT 1
#define ComGoogleCommonCollectImmutableCollection_Builder_INCLUDE 1
#include "com/google/common/collect/ImmutableCollection.h"

@interface ComGoogleCommonCollectImmutableList_Builder : ComGoogleCommonCollectImmutableCollection_Builder {
 @public
  IOSObjectArray *contents_;
  int size_;
}

- (id)init;
- (id)initWithInt:(int)capacity;
- (ComGoogleCommonCollectImmutableList_Builder *)ensureCapacityWithInt:(int)minCapacity;
- (ComGoogleCommonCollectImmutableList_Builder *)addWithId:(id)element;
- (ComGoogleCommonCollectImmutableList_Builder *)addAllWithJavaLangIterable:(id<JavaLangIterable>)elements;
- (ComGoogleCommonCollectImmutableList_Builder *)addWithNSObjectArray:(IOSObjectArray *)elements;
- (ComGoogleCommonCollectImmutableList_Builder *)addAllWithJavaUtilIterator:(id<JavaUtilIterator>)elements;
- (ComGoogleCommonCollectImmutableList *)build;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectImmutableList_Builder *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_Builder, contents_, IOSObjectArray *)
#endif

#if !defined (_ComGoogleCommonCollectImmutableList_$1_) && (ComGoogleCommonCollectImmutableList_INCLUDE_ALL || ComGoogleCommonCollectImmutableList_$1_INCLUDE)
#define _ComGoogleCommonCollectImmutableList_$1_

@class ComGoogleCommonCollectImmutableList;

#define ComGoogleCommonCollectAbstractIndexedListIterator_RESTRICT 1
#define ComGoogleCommonCollectAbstractIndexedListIterator_INCLUDE 1
#include "com/google/common/collect/AbstractIndexedListIterator.h"

@interface ComGoogleCommonCollectImmutableList_$1 : ComGoogleCommonCollectAbstractIndexedListIterator {
 @public
  ComGoogleCommonCollectImmutableList *this$0_;
}

- (id)getWithInt:(int)index;
- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)outer$
                                          withInt:(int)arg$0
                                          withInt:(int)arg$1;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectImmutableList_$1, this$0_, ComGoogleCommonCollectImmutableList *)
#endif
