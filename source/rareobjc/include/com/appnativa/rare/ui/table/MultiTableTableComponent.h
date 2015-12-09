//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/MultiTableTableComponent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREMultiTableTableComponent_H_
#define _RAREMultiTableTableComponent_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaUtilEventObject;
@class RAREColumn;
@class RAREMultiDataItemTableModel;
@class RAREPaintBucket;
@class RARETableStyle;
@class RAREUIRectangle;
@class RAREUTIntList;
@class RAREaTableHeader;
@class RAREiTableComponent_GridViewTypeEnum;
@class RAREiTableComponent_TableTypeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformItemRenderer;
@protocol RAREiTableModel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/event/iDataModelListener.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"

@interface RAREMultiTableTableComponent : NSObject < RAREiTableComponent, RAREiDataModelListener, RAREiChangeListener > {
 @public
  id<RAREiTableComponent> mainTable_;
  id<RAREiTableComponent> columnHeaderTable_;
  id<RAREiTableComponent> columnFooterTable_;
  id<RAREiTableComponent> rowHeaderTable_;
  id<RAREiTableComponent> rowFooterTable_;
  int rowHeaderColumnCount_;
  int rowFooterColumnStart_;
  int mainColumnCount_;
  RAREMultiDataItemTableModel *tableModel_;
  RAREUTIntList *colList_;
  id<RAREiPlatformComponent> component_;
  IOSIntArray *viewPositions_;
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
     withRAREMultiDataItemTableModel:(RAREMultiDataItemTableModel *)model;
- (IOSObjectArray *)getColumns;
- (int)convertModelIndexToViewWithInt:(int)index;
- (int)convertViewIndexToModelWithInt:(int)index;
- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                  withId:(id)value
                                 withInt:(int)type
                                  withId:(id)data
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)dispose;
- (RAREUIRectangle *)getCellRectWithInt:(int)row
                                withInt:(int)column
                            withBoolean:(BOOL)includeMargin;
- (int)getColumnCount;
- (id<RAREiTableComponent>)getColumnFooterTable;
- (id<RAREiTableComponent>)getColumnHeaderTable;
- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y;
- (int)getColumnIndexForModelColumnWithInt:(int)column;
- (int)getFirstVisibleColumnIndex;
- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType;
- (id<RAREiPlatformItemRenderer>)getItemRenderer;
- (int)getLastVisibleColumnIndex;
- (id<RAREiTableComponent>)getMainTable;
- (id<RAREiTableModel>)getModel;
- (id<RAREiPlatformComponent>)getPlatformComponent;
- (id<RAREiTableComponent>)getRowFooterTable;
- (id<RAREiTableComponent>)getRowHeaderTable;
- (int)getSelectedColumn;
- (int)getSelectedColumnCount;
- (IOSIntArray *)getSelectedColumns;
- (int)getSortColumn;
- (id<RAREiTableComponent>)getTableForModelColumnWithInt:(int)column;
- (RAREaTableHeader *)getTableHeader;
- (RAREUIRectangle *)getViewRect;
- (int)getVisibleColumnCount;
- (BOOL)isAutoSizeRows;
- (BOOL)isEditing;
- (BOOL)isSortDescending;
- (void)moveColumnWithInt:(int)column
                  withInt:(int)targetColumn;
- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns
                  withJavaUtilList:(id<JavaUtilList>)rows;
- (void)setColumnFooterTableWithRAREiTableComponent:(id<RAREiTableComponent>)columnFooterTable;
- (void)setColumnHeaderTableWithRAREiTableComponent:(id<RAREiTableComponent>)columnHeaderTable;
- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setColumnTitleWithInt:(int)col
     withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible;
- (void)setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:(RAREiTableComponent_GridViewTypeEnum *)type;
- (void)setHeaderBackgroundWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setMainTableWithRAREiTableComponent:(id<RAREiTableComponent>)mainTable;
- (void)setRowFooterTableWithRAREiTableComponent:(id<RAREiTableComponent>)rowFooterTable;
- (void)setRowHeaderTableWithRAREiTableComponent:(id<RAREiTableComponent>)rowHeaderTable;
- (void)setRowHeightWithInt:(int)row
                    withInt:(int)height;
- (void)setSelectedColumnIndexWithInt:(int)index;
- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)indices;
- (void)setShowHorizontalLinesWithBoolean:(BOOL)show;
- (void)setShowVerticalLinesWithBoolean:(BOOL)show;
- (void)setSortColumnWithInt:(int)col
                 withBoolean:(BOOL)descending;
- (void)setStyleWithRARETableStyle:(RARETableStyle *)tableStyle;
- (void)setTable;
- (void)sizeRowsToFit;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c;
- (void)sortWithInt:(int)col;
- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData;
- (void)stopEditing;
- (RAREiTableComponent_TableTypeEnum *)getTableType;
- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)type;
- (BOOL)isMainTable;
- (void)contentsChangedWithId:(id)source;
- (void)contentsChangedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1;
- (void)intervalAddedWithId:(id)source
                    withInt:(int)index0
                    withInt:(int)index1;
- (void)intervalRemovedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1
             withJavaUtilList:(id<JavaUtilList>)removed;
- (BOOL)isMultiTableComponent;
- (void)structureChangedWithId:(id)source;
- (IOSIntArray *)getViewPositions;
- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)copyAllFieldsTo:(RAREMultiTableTableComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, mainTable_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, columnHeaderTable_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, columnFooterTable_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, rowHeaderTable_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, rowFooterTable_, id<RAREiTableComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, tableModel_, RAREMultiDataItemTableModel *)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, colList_, RAREUTIntList *)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, component_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREMultiTableTableComponent, viewPositions_, IOSIntArray *)

typedef RAREMultiTableTableComponent ComAppnativaRareUiTableMultiTableTableComponent;

#endif // _RAREMultiTableTableComponent_H_
