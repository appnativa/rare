//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/Grouper.java
//
//  Created by decoteaud on 3/14/16.
//

#ifndef _RAREGrouper_H_
#define _RAREGrouper_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RARERenderableDataItem;
@class RARESubItemComparator;
@class RAREUIFont;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiCancelable;
@protocol RAREiFunctionCallback;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "java/lang/Runnable.h"

@interface RAREGrouper : NSObject {
 @public
  RAREUIFont *boldFont_;
  BOOL boldGroupTitles_;
  IOSIntArray *cols_;
  BOOL flatFormat_;
  BOOL forTable_;
  id<JavaUtilMap> map_;
  IOSObjectArray *nulls_;
  BOOL preserveFirst_;
  BOOL preserveLinkedData_;
  BOOL preserveRest_;
  RARESubItemComparator *rowComparator_;
  int sortOrder_;
  IOSObjectArray *titleItems_;
  BOOL titleSelectable_;
  int groupHeaderColumnSpan_;
}

+ (IOSObjectArray *)ZERO_NONE;
+ (void)setZERO_NONE:(IOSObjectArray *)ZERO_NONE;
- (id)init;
- (id)initWithIntArray:(IOSIntArray *)cols
     withNSStringArray:(IOSObjectArray *)nones
           withBoolean:(BOOL)preserveFirst
           withBoolean:(BOOL)preserveRest;
- (id<RAREUTiCancelable>)groupInBackgroundWithRAREiWidget:(id<RAREiWidget>)context
                                         withJavaUtilList:(id<JavaUtilList>)list
                                withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
- (id<JavaUtilList>)groupWithRAREiWidget:(id<RAREiWidget>)context
                        withJavaUtilList:(id<JavaUtilList>)list;
+ (id<JavaUtilList>)groupByRowInfoWithJavaUtilList:(id<JavaUtilList>)list
                                           withInt:(int)expandableColumn;
- (void)setBoldGroupTitlesWithBoolean:(BOOL)bold;
- (void)setColumnsWithIntArray:(IOSIntArray *)cols;
- (void)setCriteriaWithIntArray:(IOSIntArray *)cols
              withNSStringArray:(IOSObjectArray *)nones
                    withBoolean:(BOOL)preserveFirst
                    withBoolean:(BOOL)preserveRest;
- (void)setFlatFormatWithBoolean:(BOOL)flatFormat;
- (void)setFormatForTableWithBoolean:(BOOL)forTable;
- (void)setGroupTitleItemsWithRARERenderableDataItemArray:(IOSObjectArray *)groupItems;
- (void)setNullsWithNSStringArray:(IOSObjectArray *)nones;
- (void)setPreserveFirstWithBoolean:(BOOL)preserveFirst;
- (void)setPreserveLinkedDataWithBoolean:(BOOL)preserve;
- (void)setPreserveRestWithBoolean:(BOOL)preserveRest;
- (void)setSortOrderWithInt:(int)sortOrder;
- (void)setTitlesSelectableWithBoolean:(BOOL)titleSelectable;
- (IOSIntArray *)getColumns;
- (IOSObjectArray *)getGroupTitleItems;
- (IOSObjectArray *)getNulls;
- (int)getSortOrder;
- (BOOL)isBoldGroupTitles;
- (BOOL)isFlatFormat;
- (BOOL)isFormatForTable;
- (BOOL)isPreserveFirst;
- (BOOL)isPreserveLinkedData;
- (BOOL)isPreserveRest;
- (void)groupWithRAREiWidget:(id<RAREiWidget>)context
            withJavaUtilList:(id<JavaUtilList>)list
                     withInt:(int)pos
  withRARERenderableDataItem:(RARERenderableDataItem *)output;
- (void)fixForTableWithJavaUtilList:(id<JavaUtilList>)list;
- (int)getGroupHeaderColumnSpan;
- (void)setGroupHeaderColumnSpanWithInt:(int)groupHeaderColumnSpan;
- (void)copyAllFieldsTo:(RAREGrouper *)other;
@end

J2OBJC_FIELD_SETTER(RAREGrouper, boldFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREGrouper, cols_, IOSIntArray *)
J2OBJC_FIELD_SETTER(RAREGrouper, map_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREGrouper, nulls_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREGrouper, rowComparator_, RARESubItemComparator *)
J2OBJC_FIELD_SETTER(RAREGrouper, titleItems_, IOSObjectArray *)

typedef RAREGrouper ComAppnativaRareUtilGrouper;

@interface RAREGrouper_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREGrouper *this$0_;
  id<RAREiWidget> val$context_;
  id<JavaUtilList> val$list_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithRAREGrouper:(RAREGrouper *)outer$
          withRAREiWidget:(id<RAREiWidget>)capture$0
         withJavaUtilList:(id<JavaUtilList>)capture$1
withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREGrouper_$1, this$0_, RAREGrouper *)
J2OBJC_FIELD_SETTER(RAREGrouper_$1, val$context_, id<RAREiWidget>)
J2OBJC_FIELD_SETTER(RAREGrouper_$1, val$list_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREGrouper_$1, val$cb_, id<RAREiFunctionCallback>)

@interface RAREGrouper_$1_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREGrouper_$1 *this$0_;
  BOOL val$canceled_;
  id val$ret_;
}

- (void)run;
- (id)initWithRAREGrouper_$1:(RAREGrouper_$1 *)outer$
                 withBoolean:(BOOL)capture$0
                      withId:(id)capture$1;
@end

J2OBJC_FIELD_SETTER(RAREGrouper_$1_$1, this$0_, RAREGrouper_$1 *)
J2OBJC_FIELD_SETTER(RAREGrouper_$1_$1, val$ret_, id)

#endif // _RAREGrouper_H_
