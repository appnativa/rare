//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/collect/CartesianList.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonCollectCartesianList_RESTRICT
#define ComGoogleCommonCollectCartesianList_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonCollectCartesianList_RESTRICT

#if !defined (_ComGoogleCommonCollectCartesianList_) && (ComGoogleCommonCollectCartesianList_INCLUDE_ALL || ComGoogleCommonCollectCartesianList_INCLUDE)
#define _ComGoogleCommonCollectCartesianList_

@class ComGoogleCommonCollectImmutableList;
@class IOSIntArray;
@protocol JavaUtilList;

#define JavaUtilAbstractList_RESTRICT 1
#define JavaUtilAbstractList_INCLUDE 1
#include "java/util/AbstractList.h"

@interface ComGoogleCommonCollectCartesianList : JavaUtilAbstractList {
 @public
  ComGoogleCommonCollectImmutableList *axes_;
  IOSIntArray *axesSizeProduct_;
}

+ (id<JavaUtilList>)createWithJavaUtilList:(id<JavaUtilList>)lists;
- (id)initWithComGoogleCommonCollectImmutableList:(ComGoogleCommonCollectImmutableList *)axes;
- (int)getAxisIndexForProductIndexWithInt:(int)index
                                  withInt:(int)axis;
- (ComGoogleCommonCollectImmutableList *)getWithInt:(int)index;
- (int)size;
- (BOOL)containsWithId:(id)o;
- (int)indexOfWithId:(id)o;
- (int)lastIndexOfWithId:(id)o;
- (void)copyAllFieldsTo:(ComGoogleCommonCollectCartesianList *)other;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCartesianList, axes_, ComGoogleCommonCollectImmutableList *)
J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCartesianList, axesSizeProduct_, IOSIntArray *)
#endif

#if !defined (_ComGoogleCommonCollectCartesianList_$1_) && (ComGoogleCommonCollectCartesianList_INCLUDE_ALL || ComGoogleCommonCollectCartesianList_$1_INCLUDE)
#define _ComGoogleCommonCollectCartesianList_$1_

@class ComGoogleCommonCollectCartesianList;

#define ComGoogleCommonCollectImmutableList_RESTRICT 1
#define ComGoogleCommonCollectImmutableList_INCLUDE 1
#include "com/google/common/collect/ImmutableList.h"

@interface ComGoogleCommonCollectCartesianList_$1 : ComGoogleCommonCollectImmutableList {
 @public
  ComGoogleCommonCollectCartesianList *this$0_;
  int val$index_;
}

- (int)size;
- (id)getWithInt:(int)axis;
- (BOOL)isPartialView;
- (id)initWithComGoogleCommonCollectCartesianList:(ComGoogleCommonCollectCartesianList *)outer$
                                          withInt:(int)capture$0;
@end

J2OBJC_FIELD_SETTER(ComGoogleCommonCollectCartesianList_$1, this$0_, ComGoogleCommonCollectCartesianList *)
#endif
