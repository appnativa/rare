//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aDataItemListModel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaDataItemListModel_H_
#define _RAREaDataItemListModel_H_

@class IOSIntArray;
@class IOSObjectArray;
@class RAREColumn;
@class RAREEventListenerList;
@class RARERenderableDataItem;
@class RAREUTIntList;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREiDataModelListener;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/iStringConverter.h"

@interface RAREaDataItemListModel : RAREUTFilterableList < RAREUTiStringConverter, RAREiPlatformListDataModel > {
 @public
  BOOL eventsEnabled_;
  RAREColumn *columnDescription_;
  BOOL editing_;
  RAREUTIntList *editingMarks_;
  RAREEventListenerList *listenerList_;
  id<RAREiWidget> theWidget_;
}

- (id)init;
- (id)initWithRAREaDataItemListModel:(RAREaDataItemListModel *)m;
- (id)initWithRAREiWidget:(id<RAREiWidget>)widget
           withRAREColumn:(RAREColumn *)column;
- (BOOL)addWithId:(RARERenderableDataItem *)e;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)e;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllExWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addDataModelListenerWithRAREiDataModelListener:(id<RAREiDataModelListener>)l;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addToFilteredListWithId:(RARERenderableDataItem *)e;
- (void)addToFilteredListWithInt:(int)index
                          withId:(RARERenderableDataItem *)e;
- (void)clear;
- (void)clearEx;
- (void)dispose;
- (void)editModeChangeAllMarksWithBoolean:(BOOL)mark;
- (void)editModeChangeMarkWithInt:(int)index
                      withBoolean:(BOOL)mark;
- (void)editModeClearMarks;
- (int)editModeGetMarkCount;
- (IOSIntArray *)editModeGetMarkedIndices;
- (IOSObjectArray *)editModeGetMarkedItems;
- (BOOL)editModeIsItemMarkedWithInt:(int)index;
- (void)editModeToggleMarkWithInt:(int)index;
- (int)indexOfLinkedDataWithId:(id)value;
- (int)indexOfLinkedDataEqualsWithId:(id)value;
- (int)indexOfValueWithId:(id)value;
- (int)indexOfValueEqualsWithId:(id)value;
- (void)refreshItems;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id<JavaUtilList>)reverse;
- (void)removeDataModelListenerWithRAREiDataModelListener:(id<RAREiDataModelListener>)l;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (void)removeRowsWithInt:(int)firstRow
                  withInt:(int)lastRow;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)rowChangedWithInt:(int)index;
- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)rowsChangedWithIntArray:(IOSIntArray *)index;
- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow;
- (void)rowsDeletedWithInt:(int)firstRow
                   withInt:(int)lastRow
          withJavaUtilList:(id<JavaUtilList>)removed;
- (void)rowsInsertedWithInt:(int)firstRow
                    withInt:(int)lastRow;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)structureChanged;
- (void)swapWithInt:(int)index1
            withInt:(int)index2;
- (NSString *)toStringWithId:(id)item;
- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)e;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)setColumnDescriptionWithRAREColumn:(RAREColumn *)column;
- (void)setEditingWithBoolean:(BOOL)editing;
- (void)setEventsEnabledWithBoolean:(BOOL)enabled;
- (void)setItemsWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget;
- (RARERenderableDataItem *)getWithInt:(int)index;
- (RAREColumn *)getColumnDescription;
- (id)getElementAtWithInt:(int)index;
- (RARERenderableDataItem *)getRowWithInt:(int)index;
- (id<RAREiWidget>)getWidget;
- (BOOL)isEditing;
- (BOOL)isEventsEnabled;
- (void)fireContentsChangedWithId:(id)source;
- (void)fireContentsChangedWithId:(id)source
                          withInt:(int)index0
                          withInt:(int)index1;
- (void)fireIntervalAddedWithId:(id)source
                        withInt:(int)index0
                        withInt:(int)index1;
- (void)fireIntervalRemovedWithId:(id)source
                          withInt:(int)index0
                          withInt:(int)index1
                 withJavaUtilList:(id<JavaUtilList>)removed;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)param0;
- (BOOL)isEqual:(id)param0;
- (NSUInteger)hash;
- (void)copyAllFieldsTo:(RAREaDataItemListModel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaDataItemListModel, columnDescription_, RAREColumn *)
J2OBJC_FIELD_SETTER(RAREaDataItemListModel, editingMarks_, RAREUTIntList *)
J2OBJC_FIELD_SETTER(RAREaDataItemListModel, listenerList_, RAREEventListenerList *)
J2OBJC_FIELD_SETTER(RAREaDataItemListModel, theWidget_, id<RAREiWidget>)

typedef RAREaDataItemListModel ComAppnativaRareUiADataItemListModel;

#endif // _RAREaDataItemListModel_H_
