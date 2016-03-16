//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-table_and_tree/com/appnativa/rare/ui/table/MultiTableTableComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/table/MultiDataItemTableModel.h"
#include "com/appnativa/rare/ui/table/MultiTableTableComponent.h"
#include "com/appnativa/rare/ui/table/TableStyle.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/IntList.h"
#include "java/lang/CharSequence.h"
#include "java/lang/System.h"
#include "java/util/Comparator.h"
#include "java/util/EventObject.h"
#include "java/util/List.h"

@implementation RAREMultiTableTableComponent

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
     withRAREMultiDataItemTableModel:(RAREMultiDataItemTableModel *)model {
  if (self = [super init]) {
    self->component_ = component;
    tableModel_ = model;
    [((RAREMultiDataItemTableModel *) nil_chk(model)) addDataModelListenerWithRAREiDataModelListener:self];
  }
  return self;
}

- (IOSObjectArray *)getColumns {
  return [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getColumnsEx];
}

- (int)convertModelIndexToViewWithInt:(int)index {
  if (index < rowHeaderColumnCount_) {
    return [((id<RAREiTableComponent>) nil_chk(rowHeaderTable_)) convertModelIndexToViewWithInt:index];
  }
  if (index < rowFooterColumnStart_) {
    return [((id<RAREiTableComponent>) nil_chk(mainTable_)) convertModelIndexToViewWithInt:index - rowHeaderColumnCount_];
  }
  return [((id<RAREiTableComponent>) nil_chk(rowFooterTable_)) convertModelIndexToViewWithInt:index - rowHeaderColumnCount_ - mainColumnCount_];
}

- (int)convertViewIndexToModelWithInt:(int)index {
  RAREColumn *c = nil;
  if (index < rowHeaderColumnCount_) {
    c = [((RAREaTableHeader *) nil_chk([((id<RAREiTableComponent>) nil_chk(rowHeaderTable_)) getTableHeader])) getColumnForViewAtWithInt:index];
  }
  else if (index < rowFooterColumnStart_) {
    c = [((RAREaTableHeader *) nil_chk([((id<RAREiTableComponent>) nil_chk(mainTable_)) getTableHeader])) getColumnForViewAtWithInt:index - rowHeaderColumnCount_];
  }
  else {
    c = [((RAREaTableHeader *) nil_chk([((id<RAREiTableComponent>) nil_chk(rowFooterTable_)) getTableHeader])) getColumnForViewAtWithInt:index - rowHeaderColumnCount_ - mainColumnCount_];
  }
  return (c == nil) ? -1 : [((id<JavaUtilList>) nil_chk([((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getColumns])) indexOfWithId:c];
}

- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                  withId:(id)value
                                 withInt:(int)type
                                  withId:(id)data
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) createColumnWithNSString:title withId:value withInt:type withId:data withRAREiPlatformIcon:icon];
}

- (void)dispose {
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ dispose];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ dispose];
  }
  if (tableModel_ != nil) {
    [tableModel_ dispose];
  }
  mainTable_ = nil;
  columnHeaderTable_ = nil;
  columnFooterTable_ = nil;
  rowHeaderTable_ = nil;
  rowFooterTable_ = nil;
  tableModel_ = nil;
  component_ = nil;
}

- (RAREUIRectangle *)getCellRectWithInt:(int)row
                                withInt:(int)column
                            withBoolean:(BOOL)includeMargin {
  if (rowHeaderTable_ != nil) {
    if (column < [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount]) {
      return [rowHeaderTable_ getCellRectWithInt:row withInt:column withBoolean:includeMargin];
    }
    else {
      column -= [tableModel_ getHeaderColumnCount];
    }
  }
  if (column < [((id<RAREiTableComponent>) nil_chk(mainTable_)) getColumnCount]) {
    RAREUIRectangle *r = [mainTable_ getCellRectWithInt:row withInt:column withBoolean:includeMargin];
    if (rowHeaderTable_ != nil) {
      ((RAREUIRectangle *) nil_chk(r))->x_ += [((RAREaTableHeader *) nil_chk([rowHeaderTable_ getTableHeader])) getWidth];
    }
    return r;
  }
  else {
    column -= [mainTable_ getColumnCount];
  }
  if (rowFooterTable_ != nil) {
    RAREUIRectangle *r = [rowFooterTable_ getCellRectWithInt:row withInt:column withBoolean:includeMargin];
    if (rowHeaderTable_ != nil) {
      ((RAREUIRectangle *) nil_chk(r))->x_ += [((RAREaTableHeader *) nil_chk([rowHeaderTable_ getTableHeader])) getWidth];
    }
    ((RAREUIRectangle *) nil_chk(r))->x_ += [((RAREaTableHeader *) nil_chk([mainTable_ getTableHeader])) getWidth];
    return r;
  }
  return nil;
}

- (int)getColumnCount {
  return [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getColumnCount];
}

- (id<RAREiTableComponent>)getColumnFooterTable {
  return columnFooterTable_;
}

- (id<RAREiTableComponent>)getColumnHeaderTable {
  return columnHeaderTable_;
}

- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y {
  int w;
  if (rowHeaderTable_ != nil) {
    w = [((id<RAREiPlatformComponent>) nil_chk([rowHeaderTable_ getPlatformComponent])) getWidth];
    if (x < w) {
      return [rowHeaderTable_ getColumnIndexAtWithFloat:x withFloat:y];
    }
    x -= w;
  }
  w = [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiTableComponent>) nil_chk(mainTable_)) getPlatformComponent])) getWidth];
  if (x < w) {
    int col = [mainTable_ getColumnIndexAtWithFloat:x withFloat:y];
    return (col == -1) ? -1 : col + [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount];
  }
  if (rowFooterTable_ != nil) {
    x -= w;
    int col = [rowFooterTable_ getColumnIndexAtWithFloat:x withFloat:y];
    if (col != -1) {
      return col + [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getFooterColumnStart];
    }
  }
  return -1;
}

- (int)getColumnIndexForModelColumnWithInt:(int)column {
  if (column < [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount]) {
    return column;
  }
  else {
    column -= [tableModel_ getHeaderColumnCount];
  }
  if (column < [((id<RAREiTableComponent>) nil_chk(mainTable_)) getColumnCount]) {
    return column;
  }
  else {
    column -= [mainTable_ getColumnCount];
  }
  return (rowFooterTable_ == nil) ? -1 : column;
}

- (int)getFirstVisibleColumnIndex {
  if (rowHeaderTable_ != nil) {
    return [rowHeaderTable_ getFirstVisibleColumnIndex];
  }
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) getFirstVisibleColumnIndex] + [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount];
}

- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType {
  return nil;
}

- (id<RAREiPlatformItemRenderer>)getItemRenderer {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) getItemRenderer];
}

- (int)getLastVisibleColumnIndex {
  if (rowFooterTable_ != nil) {
    return [rowFooterTable_ getLastVisibleColumnIndex] + [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getFooterColumnStart];
  }
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) getLastVisibleColumnIndex] + [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount];
}

- (id<RAREiTableComponent>)getMainTable {
  return mainTable_;
}

- (id<RAREiTableModel>)getModel {
  return tableModel_;
}

- (id<RAREiPlatformComponent>)getPlatformComponent {
  return component_;
}

- (id<RAREiTableComponent>)getRowFooterTable {
  return rowFooterTable_;
}

- (id<RAREiTableComponent>)getRowHeaderTable {
  return rowHeaderTable_;
}

- (int)getSelectedColumn {
  int col = [((id<RAREiTableComponent>) nil_chk(mainTable_)) getSelectedColumn];
  if (rowHeaderTable_ != nil) {
    if (col != -1) {
      col += [rowHeaderTable_ getSelectedColumn];
    }
    col = [rowHeaderTable_ getSelectedColumn];
  }
  if ((col == -1) && (rowFooterTable_ != nil)) {
    col = [rowFooterTable_ getSelectedColumn];
    if (col != -1) {
      col += [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getFooterColumnStart];
    }
  }
  return col;
}

- (int)getSelectedColumnCount {
  int count = [((id<RAREiTableComponent>) nil_chk(mainTable_)) getSelectedColumnCount];
  if (rowFooterTable_ != nil) {
    count = [rowFooterTable_ getSelectedColumnCount];
  }
  if (rowHeaderTable_ != nil) {
    count = [rowHeaderTable_ getSelectedColumnCount];
  }
  return count;
}

- (IOSIntArray *)getSelectedColumns {
  IOSIntArray *h = (rowHeaderTable_ == nil) ? nil : [rowHeaderTable_ getSelectedColumns];
  IOSIntArray *m = [((id<RAREiTableComponent>) nil_chk(mainTable_)) getSelectedColumns];
  IOSIntArray *f = (rowFooterTable_ == nil) ? nil : [rowFooterTable_ getSelectedColumns];
  if ((h == nil) || ((int) [h count] == 0) || (m == nil) || ((int) [m count] == 0) || (f == nil) || ((int) [f count] == 0)) {
    return nil;
  }
  if (colList_ == nil) {
    colList_ = [[RAREUTIntList alloc] init];
  }
  if (h != nil) {
    [((RAREUTIntList *) nil_chk(colList_)) addWithIntArray:h];
  }
  if (m != nil) {
    [((RAREUTIntList *) nil_chk(colList_)) addWithIntArray:m];
  }
  if (f != nil) {
    [((RAREUTIntList *) nil_chk(colList_)) addWithIntArray:f];
  }
  return [((RAREUTIntList *) nil_chk(colList_)) toArray];
}

- (int)getSortColumn {
  return [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getSortColumn];
}

- (id<RAREiTableComponent>)getTableForModelColumnWithInt:(int)column {
  if (column < [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) getHeaderColumnCount]) {
    return rowHeaderTable_;
  }
  else {
    column -= [tableModel_ getHeaderColumnCount];
  }
  if (column < [((id<RAREiTableComponent>) nil_chk(mainTable_)) getColumnCount]) {
    return mainTable_;
  }
  return rowFooterTable_;
}

- (RAREaTableHeader *)getTableHeader {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) getTableHeader];
}

- (RAREUIRectangle *)getViewRect {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) getViewRect];
}

- (int)getVisibleColumnCount {
  int count = [((id<RAREiTableComponent>) nil_chk(mainTable_)) getVisibleColumnCount];
  if (rowFooterTable_ != nil) {
    count = [rowFooterTable_ getVisibleColumnCount];
  }
  if (rowHeaderTable_ != nil) {
    count = [rowHeaderTable_ getVisibleColumnCount];
  }
  return count;
}

- (BOOL)isAutoSizeRows {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) isAutoSizeRows];
}

- (BOOL)isEditing {
  return [((id<RAREiTableComponent>) nil_chk(mainTable_)) isEditing];
}

- (BOOL)isSortDescending {
  return [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) isSortDescending];
}

- (void)moveColumnWithInt:(int)column
                  withInt:(int)targetColumn {
  int mc = [self convertViewIndexToModelWithInt:column];
  int tmc = [self convertViewIndexToModelWithInt:targetColumn];
  if ((mc != -1) && (tmc != -1) && (mc != tmc)) {
    id<RAREiTableComponent> table1 = [self getTableForModelColumnWithInt:mc];
    id<RAREiTableComponent> table2 = [self getTableForModelColumnWithInt:tmc];
    if (table1 == table2) {
      [((id<RAREiTableComponent>) nil_chk(table1)) moveColumnWithInt:column withInt:targetColumn];
      viewPositions_ = nil;
    }
  }
}

- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns
                  withJavaUtilList:(id<JavaUtilList>)rows {
  [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) resetModelWithJavaUtilList:columns withRAREUTiFilterableList:[[RAREUTFilterableList alloc] initWithJavaUtilList:rows]];
  rowHeaderColumnCount_ = tableModel_->headerColumnCount_;
  rowFooterColumnStart_ = tableModel_->footerColumnStart_;
  mainColumnCount_ = tableModel_->columnCount_ - rowHeaderColumnCount_ - (tableModel_->columnCount_ - rowFooterColumnStart_);
}

- (void)setColumnFooterTableWithRAREiTableComponent:(id<RAREiTableComponent>)columnFooterTable {
  self->columnFooterTable_ = columnFooterTable;
}

- (void)setColumnHeaderTableWithRAREiTableComponent:(id<RAREiTableComponent>)columnHeaderTable {
  self->columnHeaderTable_ = columnHeaderTable;
}

- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  id<RAREiTableComponent> table = [self getTableForModelColumnWithInt:col];
  if (table != nil) {
    [table setColumnIconWithInt:[self getColumnIndexForModelColumnWithInt:col] withRAREiPlatformIcon:icon];
  }
}

- (void)setColumnTitleWithInt:(int)col
     withJavaLangCharSequence:(id<JavaLangCharSequence>)title {
  id<RAREiTableComponent> table = [self getTableForModelColumnWithInt:col];
  if (table != nil) {
    [table setColumnTitleWithInt:[self getColumnIndexForModelColumnWithInt:col] withJavaLangCharSequence:title];
  }
}

- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible {
  id<RAREiTableComponent> table = [self getTableForModelColumnWithInt:col];
  if (table != nil) {
    [table setColumnVisibleWithInt:[self getColumnIndexForModelColumnWithInt:col] withBoolean:visible];
  }
}

- (void)setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:(RAREiTableComponent_GridViewTypeEnum *)type {
}

- (void)setHeaderBackgroundWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setHeaderBackgroundWithRAREPaintBucket:pb];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setHeaderBackgroundWithRAREPaintBucket:pb];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setHeaderBackgroundWithRAREPaintBucket:pb];
  }
}

- (void)setMainTableWithRAREiTableComponent:(id<RAREiTableComponent>)mainTable {
  self->mainTable_ = mainTable;
  [((id<RAREiTableComponent>) nil_chk(mainTable)) setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum MAIN]];
  [((RAREaTableHeader *) nil_chk([mainTable getTableHeader])) setMultiTableTableComponentWithRAREMultiTableTableComponent:self];
  [((RAREaTableHeader *) nil_chk([mainTable getTableHeader])) addColumnChangeListenerWithRAREiChangeListener:self];
}

- (void)setRowFooterTableWithRAREiTableComponent:(id<RAREiTableComponent>)rowFooterTable {
  self->rowFooterTable_ = rowFooterTable;
  [((id<RAREiTableComponent>) nil_chk(rowFooterTable)) setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum FOOTER]];
  [((RAREaTableHeader *) nil_chk([rowFooterTable getTableHeader])) setMultiTableTableComponentWithRAREMultiTableTableComponent:self];
  [((RAREaTableHeader *) nil_chk([rowFooterTable getTableHeader])) addColumnChangeListenerWithRAREiChangeListener:self];
}

- (void)setRowHeaderTableWithRAREiTableComponent:(id<RAREiTableComponent>)rowHeaderTable {
  self->rowHeaderTable_ = rowHeaderTable;
  [((id<RAREiTableComponent>) nil_chk(rowHeaderTable)) setTableTypeWithRAREiTableComponent_TableTypeEnum:[RAREiTableComponent_TableTypeEnum HEADER]];
  [((RAREaTableHeader *) nil_chk([rowHeaderTable getTableHeader])) setMultiTableTableComponentWithRAREMultiTableTableComponent:self];
  [((RAREaTableHeader *) nil_chk([rowHeaderTable getTableHeader])) addColumnChangeListenerWithRAREiChangeListener:self];
}

- (void)setRowHeightWithInt:(int)row
                    withInt:(int)height {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setRowHeightWithInt:row withInt:height];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setRowHeightWithInt:row withInt:height];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setRowHeightWithInt:row withInt:height];
  }
}

- (void)setSelectedColumnIndexWithInt:(int)index {
  id<RAREiTableComponent> table = [self getTableForModelColumnWithInt:index];
  if (table != nil) {
    [table setSelectedColumnIndexWithInt:[self getColumnIndexForModelColumnWithInt:index]];
    if ((rowFooterTable_ != nil) && (table != rowFooterTable_)) {
      [rowFooterTable_ setSelectedColumnIndexWithInt:-1];
    }
    if ((rowHeaderTable_ != nil) && (table != rowHeaderTable_)) {
      [rowHeaderTable_ setSelectedColumnIndexWithInt:-1];
    }
    if (table != mainTable_) {
      [((id<RAREiTableComponent>) nil_chk(mainTable_)) setSelectedColumnIndexWithInt:-1];
    }
  }
}

- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)indices {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setSelectedColumnIndicesWithIntArray:nil];
}

- (void)setShowHorizontalLinesWithBoolean:(BOOL)show {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setShowHorizontalLinesWithBoolean:show];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setShowHorizontalLinesWithBoolean:show];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setShowHorizontalLinesWithBoolean:show];
  }
}

- (void)setShowVerticalLinesWithBoolean:(BOOL)show {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setShowVerticalLinesWithBoolean:show];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setShowVerticalLinesWithBoolean:show];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setShowVerticalLinesWithBoolean:show];
  }
}

- (void)setSortColumnWithInt:(int)col
                 withBoolean:(BOOL)descending {
  id<RAREiTableComponent> table = [self getTableForModelColumnWithInt:col];
  if (table != nil) {
    [table setSortColumnWithInt:[self getColumnIndexForModelColumnWithInt:col] withBoolean:descending];
    if ((rowFooterTable_ != nil) && (table != rowFooterTable_)) {
      [rowFooterTable_ setSortColumnWithInt:-1 withBoolean:descending];
    }
    if ((rowHeaderTable_ != nil) && (table != rowHeaderTable_)) {
      [rowHeaderTable_ setSortColumnWithInt:-1 withBoolean:descending];
    }
    if (table != mainTable_) {
      [((id<RAREiTableComponent>) nil_chk(mainTable_)) setSortColumnWithInt:-1 withBoolean:descending];
    }
  }
}

- (void)setStyleWithRARETableStyle:(RARETableStyle *)tableStyle {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setStyleWithRARETableStyle:tableStyle];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setStyleWithRARETableStyle:tableStyle];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setStyleWithRARETableStyle:tableStyle];
  }
}

- (void)setTable {
  rowHeaderColumnCount_ = ((RAREMultiDataItemTableModel *) nil_chk(tableModel_))->headerColumnCount_;
  rowFooterColumnStart_ = tableModel_->footerColumnStart_;
  mainColumnCount_ = tableModel_->columnCount_ - rowHeaderColumnCount_ - (tableModel_->columnCount_ - rowFooterColumnStart_);
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) setTable];
  if (rowHeaderTable_ != nil) {
    [rowHeaderTable_ setTable];
  }
  if (rowFooterTable_ != nil) {
    [rowFooterTable_ setTable];
  }
}

- (void)sizeRowsToFit {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) sizeRowsToFit];
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c {
  [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) sortWithJavaUtilComparator:c];
  if (rowHeaderTable_ != nil) {
    [((id<RAREiTableModel>) nil_chk([rowHeaderTable_ getModel])) refreshItems];
  }
  [((id<RAREiTableModel>) nil_chk([((id<RAREiTableComponent>) nil_chk(mainTable_)) getModel])) refreshItems];
  if (rowFooterTable_ != nil) {
    [((id<RAREiTableModel>) nil_chk([rowFooterTable_ getModel])) refreshItems];
  }
}

- (void)sortWithInt:(int)col {
  [self sortWithInt:col withBoolean:[self isSortDescending] withBoolean:NO];
}

- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData {
  [((RAREMultiDataItemTableModel *) nil_chk(tableModel_)) sortWithInt:col withBoolean:descending withBoolean:useLinkedData];
  if (rowHeaderTable_ != nil) {
    [((id<RAREiTableModel>) nil_chk([rowHeaderTable_ getModel])) refreshItems];
    if (col < rowHeaderColumnCount_) {
      [rowHeaderTable_ setSortColumnWithInt:col withBoolean:descending];
    }
    else {
      [rowHeaderTable_ setSortColumnWithInt:-1 withBoolean:descending];
    }
  }
  [((id<RAREiTableModel>) nil_chk([((id<RAREiTableComponent>) nil_chk(mainTable_)) getModel])) refreshItems];
  if ((col >= rowHeaderColumnCount_) && (col < rowFooterColumnStart_)) {
    [mainTable_ setSortColumnWithInt:col - rowHeaderColumnCount_ withBoolean:descending];
  }
  else {
    [mainTable_ setSortColumnWithInt:-1 withBoolean:descending];
  }
  if (rowFooterTable_ != nil) {
    [((id<RAREiTableModel>) nil_chk([rowFooterTable_ getModel])) refreshItems];
    if ((col >= rowFooterColumnStart_) && (col < [self getColumnCount])) {
      [rowFooterTable_ setSortColumnWithInt:col - rowFooterColumnStart_ withBoolean:descending];
    }
    else {
      [rowFooterTable_ setSortColumnWithInt:-1 withBoolean:descending];
    }
  }
}

- (void)stopEditing {
  [((id<RAREiTableComponent>) nil_chk(mainTable_)) stopEditing];
}

- (RAREiTableComponent_TableTypeEnum *)getTableType {
  return [RAREiTableComponent_TableTypeEnum MULTI];
}

- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)type {
}

- (BOOL)isMainTable {
  return NO;
}

- (void)contentsChangedWithId:(id)source {
}

- (void)contentsChangedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1 {
}

- (void)intervalAddedWithId:(id)source
                    withInt:(int)index0
                    withInt:(int)index1 {
}

- (void)intervalRemovedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1
             withJavaUtilList:(id<JavaUtilList>)removed {
}

- (BOOL)isMultiTableComponent {
  return YES;
}

- (void)structureChangedWithId:(id)source {
}

- (IOSIntArray *)getViewPositions {
  if (viewPositions_ == nil) {
    IOSIntArray *vp = [IOSIntArray arrayWithLength:[self getColumnCount]];
    IOSIntArray *a;
    int dstPos = 0;
    if (rowHeaderTable_ != nil) {
      a = ((RAREaTableHeader *) nil_chk([rowHeaderTable_ getTableHeader]))->viewPositions_;
      [JavaLangSystem arraycopyWithId:a withInt:0 withId:vp withInt:dstPos withInt:(int) [((IOSIntArray *) nil_chk(a)) count]];
      dstPos += (int) [a count];
    }
    a = ((RAREaTableHeader *) nil_chk([((id<RAREiTableComponent>) nil_chk(mainTable_)) getTableHeader]))->viewPositions_;
    [JavaLangSystem arraycopyWithId:a withInt:0 withId:vp withInt:dstPos withInt:(int) [((IOSIntArray *) nil_chk(a)) count]];
    dstPos += (int) [a count];
    if (rowFooterTable_ != nil) {
      a = ((RAREaTableHeader *) nil_chk([rowFooterTable_ getTableHeader]))->viewPositions_;
      [JavaLangSystem arraycopyWithId:a withInt:0 withId:vp withInt:dstPos withInt:(int) [((IOSIntArray *) nil_chk(a)) count]];
    }
    viewPositions_ = vp;
  }
  return viewPositions_;
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  viewPositions_ = nil;
}

- (void)copyAllFieldsTo:(RAREMultiTableTableComponent *)other {
  [super copyAllFieldsTo:other];
  other->colList_ = colList_;
  other->columnFooterTable_ = columnFooterTable_;
  other->columnHeaderTable_ = columnHeaderTable_;
  other->component_ = component_;
  other->mainColumnCount_ = mainColumnCount_;
  other->mainTable_ = mainTable_;
  other->rowFooterColumnStart_ = rowFooterColumnStart_;
  other->rowFooterTable_ = rowFooterTable_;
  other->rowHeaderColumnCount_ = rowHeaderColumnCount_;
  other->rowHeaderTable_ = rowHeaderTable_;
  other->tableModel_ = tableModel_;
  other->viewPositions_ = viewPositions_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getColumns", NULL, "LIOSObjectArray", 0x1, NULL },
    { "createColumnWithNSString:withId:withInt:withId:withRAREiPlatformIcon:", NULL, "LRAREColumn", 0x1, NULL },
    { "getCellRectWithInt:withInt:withBoolean:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getColumnFooterTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getColumnHeaderTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getColumnIndexForModelColumnWithInt:", NULL, "I", 0x0, NULL },
    { "getGridViewType", NULL, "LRAREiTableComponent_GridViewTypeEnum", 0x1, NULL },
    { "getItemRenderer", NULL, "LRAREiPlatformItemRenderer", 0x1, NULL },
    { "getMainTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getModel", NULL, "LRAREiTableModel", 0x1, NULL },
    { "getPlatformComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getRowFooterTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getRowHeaderTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getSelectedColumns", NULL, "LIOSIntArray", 0x1, NULL },
    { "getTableForModelColumnWithInt:", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getTableHeader", NULL, "LRAREaTableHeader", 0x1, NULL },
    { "getViewRect", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "isAutoSizeRows", NULL, "Z", 0x1, NULL },
    { "isEditing", NULL, "Z", 0x1, NULL },
    { "isSortDescending", NULL, "Z", 0x1, NULL },
    { "getTableType", NULL, "LRAREiTableComponent_TableTypeEnum", 0x1, NULL },
    { "isMainTable", NULL, "Z", 0x1, NULL },
    { "isMultiTableComponent", NULL, "Z", 0x1, NULL },
    { "getViewPositions", NULL, "LIOSIntArray", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "mainTable_", NULL, 0x4, "LRAREiTableComponent" },
    { "columnHeaderTable_", NULL, 0x4, "LRAREiTableComponent" },
    { "columnFooterTable_", NULL, 0x4, "LRAREiTableComponent" },
    { "rowHeaderTable_", NULL, 0x4, "LRAREiTableComponent" },
    { "rowFooterTable_", NULL, 0x4, "LRAREiTableComponent" },
    { "rowHeaderColumnCount_", NULL, 0x4, "I" },
    { "rowFooterColumnStart_", NULL, 0x4, "I" },
    { "mainColumnCount_", NULL, 0x4, "I" },
    { "tableModel_", NULL, 0x4, "LRAREMultiDataItemTableModel" },
    { "colList_", NULL, 0x4, "LRAREUTIntList" },
    { "component_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "viewPositions_", NULL, 0x4, "LIOSIntArray" },
  };
  static J2ObjcClassInfo _RAREMultiTableTableComponent = { "MultiTableTableComponent", "com.appnativa.rare.ui.table", NULL, 0x1, 24, methods, 12, fields, 0, NULL};
  return &_RAREMultiTableTableComponent;
}

@end
