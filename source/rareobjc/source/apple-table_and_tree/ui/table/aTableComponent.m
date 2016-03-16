//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/aTableComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ScrollView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/Table.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIScrollingEdgePainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/table/DataItemTableModel.h"
#include "com/appnativa/rare/ui/table/TableHeader.h"
#include "com/appnativa/rare/ui/table/TableStyle.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/table/aTableComponent.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/util/FilterableList.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Math.h"
#include "java/util/Comparator.h"
#include "java/util/List.h"
#include "java/util/Locale.h"
#import "RAREAPTableView.h"
 #import "RAREAPTableColumn.h"
 #import "com/appnativa/rare/ui/RenderableDataItem.h"

@implementation RAREaTableComponent

- (id)initWithRAREParentView:(RAREParentView *)view {
  if (self = [super initWithRAREParentView:view]) {
    sortColumn_ = -1;
    descending_ = YES;
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    sortColumn_ = -1;
    descending_ = YES;
  }
  return self;
}

- (int)convertModelIndexToViewWithInt:(int)index {
  RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
  NSArray* columns=table.tableColumns;
  NSInteger len=columns.count;
  RAREAPTableColumn* col;
  for(NSInteger i=0;i<len;i++) {
    col=(RAREAPTableColumn*)[columns objectAtIndex:i];
    if(col->modelIndex==index) {
      return (int)i;
    }
  }
  return -1;
}

- (int)convertViewIndexToModelWithInt:(int)index {
  return ((RAREAPTableColumn*)[self getAPTableColumnWithInt: index])->modelIndex;
}

- (RAREColumn *)createColumnWithNSString:(NSString *)title
                                  withId:(id)value
                                 withInt:(int)type
                                  withId:(id)data
                   withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [[RAREColumn alloc] initWithJavaLangCharSequence:title withId:value withInt:type withId:data withRAREiPlatformIcon:icon];
}

- (void)dispose {
  if (tableModel_ != nil) {
    [tableModel_ dispose];
  }
  [super dispose];
  if (tableHeader_ != nil) {
    [tableHeader_ dispose];
  }
  tableStyle_ = nil;
  tableModel_ = nil;
  tableHeader_ = nil;
  tableView_ = nil;
  tableStyle_ = nil;
}

- (void)moveColumnWithInt:(int)column
                  withInt:(int)targetColumn {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) columnMovedWithInt:column withInt:targetColumn];
}

- (void)repaint {
  [self repaintVisibleRows];
}

- (void)repaintVisibleRows {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) repaint];
  [((RARETableView *) nil_chk(tableView_)) repaintVisibleRows];
}

- (void)resetTableWithJavaUtilList:(id<JavaUtilList>)columns
                  withJavaUtilList:(id<JavaUtilList>)rows {
  RAREUTFilterableList *l;
  if ((rows == nil) || ([rows size] < 10)) {
    l = [[RAREUTFilterableList alloc] init];
  }
  else {
    l = [[RAREUTFilterableList alloc] initWithInt:[rows size]];
  }
  if (rows != nil) {
    [((RAREUTFilterableList *) nil_chk(l)) addAllWithJavaUtilCollection:rows];
  }
  id<RAREUTiFilterableList> cols = [[RAREUTFilterableList alloc] initWithInt:[((id<JavaUtilList>) nil_chk(columns)) size]];
  [cols addAllWithJavaUtilCollection:columns];
  [self resetTableExWithRAREUTiFilterableList:cols withRAREUTiFilterableList:l];
}

- (void)revalidate {
  [((RAREView *) nil_chk([((RAREaTableHeader *) nil_chk(tableHeader_)) getView])) revalidate];
  [((RARETableView *) nil_chk(tableView_)) revalidate];
  [super revalidate];
}

- (void)sizeColumnsToFit {
  [((RARETableView *) nil_chk(tableView_)) sizeColumnsToFit];
}

- (void)sizeRowsToFit {
  [((RARETableView *) nil_chk(tableView_)) sizeRowsToFit];
}

- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)c {
  [((id<RAREiTableModel>) nil_chk(tableModel_)) sortWithJavaUtilComparator:c];
}

- (void)sortWithInt:(int)col {
  if (sortColumn_ == col) {
    descending_ = !descending_;
  }
  else {
    descending_ = NO;
  }
  [self sortWithInt:col withBoolean:descending_ withBoolean:NO];
}

- (void)sortWithInt:(int)col
        withBoolean:(BOOL)descending
        withBoolean:(BOOL)useLinkedData {
  sortColumn_ = col;
  self->descending_ = descending;
  [((id<RAREiTableModel>) nil_chk(tableModel_)) sortWithInt:col withBoolean:descending withBoolean:useLinkedData];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSortColumnWithInt:col withBoolean:descending];
}

- (BOOL)isMultiTableComponent {
  return NO;
}

- (void)sortExWithJavaUtilComparator:(id<JavaUtilComparator>)c {
  [((id<RAREiTableModel>) nil_chk(tableModel_)) sortWithJavaUtilComparator:c];
}

- (void)sortExWithInt:(int)col
          withBoolean:(BOOL)descending
          withBoolean:(BOOL)useLinkedData {
  sortColumn_ = col;
  self->descending_ = descending;
  [((id<RAREiTableModel>) nil_chk(tableModel_)) sortWithInt:col withBoolean:descending withBoolean:useLinkedData];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSortColumnWithInt:col withBoolean:descending];
}

- (void)stopEditing {
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  [super setBackgroundWithRAREUIColor:bg];
  if (tableView_ != view_) {
    [((id<RAREiPlatformComponent>) nil_chk([self getTable])) setBackgroundWithRAREUIColor:bg];
  }
}

- (void)setColumnIconWithInt:(int)col
       withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  col = [self convertModelIndexToViewWithInt:col];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setColumnIconWithInt:col withRAREiPlatformIcon:icon];
}

- (void)setColumnTitleWithInt:(int)col
     withJavaLangCharSequence:(id<JavaLangCharSequence>)title {
  col = [self convertModelIndexToViewWithInt:col];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setColumnTitleWithInt:col withJavaLangCharSequence:title];
}

- (void)setColumnVisibleWithInt:(int)col
                    withBoolean:(BOOL)visible {
  col = [self convertModelIndexToViewWithInt:col];
  if ([((RAREaTableHeader *) nil_chk(tableHeader_)) isColumnVisibleWithInt:col] != visible) {
    [tableHeader_ setColumnVisibleWithInt:col withBoolean:visible];
    [((RARETableView *) nil_chk(tableView_)) sizeToFit];
  }
}

- (BOOL)isAtBottomEdge {
  return [((RARETableView *) nil_chk(tableView_)) isAtBottomEdge];
}

- (BOOL)isAtLeftEdge {
  return [((RARETableView *) nil_chk(tableView_)) isAtLeftEdge];
}

- (BOOL)isAtRightEdge {
  return [((RARETableView *) nil_chk(tableView_)) isAtRightEdge];
}

- (BOOL)isAtTopEdge {
  return [((RARETableView *) nil_chk(tableView_)) isAtTopEdge];
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  [((id<RAREiPlatformComponent>) nil_chk([self getTable])) setFontWithRAREUIFont:f];
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  [((id<RAREiPlatformComponent>) nil_chk([self getTable])) setForegroundWithRAREUIColor:fg];
}

- (void)setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:(RAREiTableComponent_GridViewTypeEnum *)type {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:type];
  if (type == [RAREiTableComponent_GridViewTypeEnum VERTICAL_WRAP]) {
    horizontalScroll_ = NO;
  }
  else if ([view_ isKindOfClass:[RAREScrollView class]]) {
    horizontalScroll_ = YES;
  }
}

- (void)setHeaderBackgroundWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setComponentPainterWithRAREiPlatformComponentPainter:nil];
  [((RAREPaintBucket *) nil_chk(pb)) installWithRAREiPlatformComponent:tableHeader_ withBoolean:YES];
}

- (void)setRowHeightWithInt:(int)height {
  [((RARETableView *) nil_chk(tableView_)) setRowHeightWithInt:height];
}

- (void)setRowHeightWithInt:(int)row
                    withInt:(int)height {
  [((RARERenderableDataItem *) nil_chk([((id<RAREiTableModel>) nil_chk(tableModel_)) getWithInt:row])) setHeightWithInt:height];
  [((RARETableView *) nil_chk(tableView_)) rowChangedWithInt:row];
}

- (void)setShowHorizontalLinesWithBoolean:(BOOL)b {
}

- (void)setShowVerticalLinesWithBoolean:(BOOL)b {
}

- (void)setSortColumnWithInt:(int)sortColumn
                 withBoolean:(BOOL)descending {
  self->sortColumn_ = sortColumn;
  self->descending_ = descending;
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSortColumnWithInt:sortColumn withBoolean:descending];
}

- (void)setStyleWithRARETableStyle:(RARETableStyle *)style {
  tableStyle_ = style;
  if (((RARETableStyle *) nil_chk(style))->headerCellPainter_ != nil) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setHeaderCellPainterWithRAREPaintBucket:style->headerCellPainter_];
  }
  if (style->headerFont_ != nil) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setFontWithRAREUIFont:style->headerFont_];
  }
  if (style->headerForeground_ != nil) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setForegroundWithRAREUIColor:style->headerForeground_];
  }
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSortingAllowedWithBoolean:style->columnSortingAllowed_];
  if (style->backgroundHilite_ != nil) {
    [((RARETableView *) nil_chk(tableView_)) setAlternatingColorWithRAREUIColor:(style->backgroundHiliteColor_ == nil) ? [[RAREUIColor alloc] initWithInt:247 withInt:247 withInt:247] : style->backgroundHiliteColor_];
    if (style->backgroundHilite_ == [RARETableStyle_BackgroundHighlightEnum COLUMN]) {
      [tableView_ setAlternatingColumnsWithBoolean:YES];
    }
  }
  if (style->showHorizontalLines_ || style->showVerticalLines_) {
    [((RARETableView *) nil_chk(tableView_)) setShowGridLinesWithBoolean:style->showHorizontalLines_ withBoolean:style->showVerticalLines_ withRAREUIColor:style->gridColor_ withRAREUIStroke:style->gridLineStroke_];
  }
  if (style->headerMarginColor_ != nil) {
    [tableHeader_ setMarginColorWithRAREUIColor:style->headerMarginColor_];
  }
  if (style->headerBottomMarginColor_ != nil) {
    [tableHeader_ setBottomMarginColorWithRAREUIColor:style->headerBottomMarginColor_];
  }
}

- (void)setTable {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setColumnsWithJavaUtilList:[((id<RAREiTableModel>) nil_chk(tableModel_)) getColumns]];
  [((RARETableView *) nil_chk(tableView_)) setListModelWithRAREiPlatformListDataModel:tableModel_];
}

- (void)setWidgetWithRAREiWidget:(id<RAREiWidget>)widget {
  [super setWidgetWithRAREiWidget:widget];
  [((id<RAREiPlatformComponent>) nil_chk([((RARETableView *) nil_chk(tableView_)) getComponent])) setWidgetWithRAREiWidget:widget];
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setWidgetWithRAREiWidget:widget];
  [((id<RAREiTableModel>) nil_chk(tableModel_)) setWidgetWithRAREiWidget:widget];
}

- (RAREUIRectangle *)getCellRectWithInt:(int)row
                                withInt:(int)column
                            withBoolean:(BOOL)includeMargin {
  return [((RARETableView *) nil_chk(tableView_)) getCellRectWithInt:row withInt:column withBoolean:includeMargin];
}

- (RAREColumn *)getColumnAtWithInt:(int)index {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getColumnAtWithInt:index];
}

- (int)getColumnCount {
  return [((id<RAREiTableModel>) nil_chk(tableModel_)) getColumnCount];
}

- (RAREUIFont *)getFontEx {
  return (tableView_ == nil) ? nil : [tableView_ getFont];
}

- (RAREiTableComponent_GridViewTypeEnum *)getGridViewType {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getGridViewType];
}

- (id<RAREiPlatformItemRenderer>)getItemRenderer {
  return [((RARETableView *) nil_chk(tableView_)) getItemRenderer];
}

- (id<RAREiTableModel>)getModel {
  return tableModel_;
}

- (id<RAREiPlatformComponent>)getPlatformComponent {
  return self;
}

- (int)getRowHeight {
  return [((RARETableView *) nil_chk(tableView_)) getRowHeight];
}

- (int)getSelectedColumn {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getSelectedColumn];
}

- (int)getSelectedColumnCount {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getSelectedColumnCount];
}

- (IOSIntArray *)getSelectedColumns {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getSelectedColumnIndices];
}

- (int)getSortColumn {
  return sortColumn_;
}

- (RAREaTableHeader *)getTableHeader {
  return tableHeader_;
}

- (id<RAREiTableModel>)getTableModel {
  return tableModel_;
}

- (RARETableView *)getTableView {
  return tableView_;
}

- (int)getVisibleColumnCount {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getVisibleColumnCount];
}

- (BOOL)hasHadInteraction {
  return [((RAREView *) nil_chk(view_)) hasHadInteraction] || [((RARETableView *) nil_chk(tableView_)) hasHadInteraction];
}

- (BOOL)isAutoSizeRows {
  return autoSizeRowsToFit_;
}

- (BOOL)isEditing {
  return NO;
}

- (BOOL)isSortDescending {
  return descending_;
}

- (void)createTableModel {
  tableModel_ = [[RAREDataItemTableModel alloc] init];
}

- (void)initialize__WithRARETableView:(RARETableView *)table
                    withRARESPOTTable:(RARESPOTTable *)cfg {
  tableView_ = table;
  tableHeader_ = [((RARETableView *) nil_chk(table)) getHeader];
  BOOL grid = [((SPOTBoolean *) nil_chk(((RARESPOTTable *) nil_chk(cfg))->displayAsGridView_)) booleanValue];
  if (grid) {
    [self setGridViewTypeWithRAREiTableComponent_GridViewTypeEnum:[RAREiTableComponent_GridViewTypeEnum valueOfWithNSString:[((NSString *) nil_chk([((RARESPOTTable_CGridViewType *) nil_chk(cfg->gridViewType_)) stringValue])) uppercaseStringWithJRELocale:[JavaUtilLocale US]]]];
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setVisibleWithBoolean:NO];
  }
  else if (![((SPOTBoolean *) nil_chk(cfg->showStandardColumnHeader_)) booleanValue]) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setVisibleWithBoolean:NO];
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->headerHeight_)) spot_valueWasSet]) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setHeaderHeightWithNSString:[cfg->headerHeight_ getValue]];
  }
  [table setExtendBackgroundRenderingWithBoolean:[((SPOTBoolean *) nil_chk(cfg->extendBackgroundRendering_)) booleanValue]];
  if (grid) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) setColumnSelectionAllowedWithBoolean:YES];
  }
  else {
    [table setAutoSizeColumnsWithBoolean:[((SPOTBoolean *) nil_chk(cfg->autoSizeColumnsToFit_)) booleanValue]];
  }
  [table setAutoSizeRowsWithBoolean:autoSizeRowsToFit_ = [((SPOTBoolean *) nil_chk(cfg->autoSizeRowsToFit_)) booleanValue]];
  [self createTableModel];
}

- (void)resetTableExWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)columns
                    withRAREUTiFilterableList:(id<RAREUTiFilterableList>)rows {
  [((id<RAREiTableModel>) nil_chk(tableModel_)) resetModelWithJavaUtilList:columns withRAREUTiFilterableList:rows];
}

- (id)getAPTableColumnWithInt:(int)index {
  return [((RAREAPTableView*)tableView_->proxy_).tableColumns objectAtIndex:index];
}

- (id)getAPTableColumnForModelIndexWithInt:(int)index {
  RAREAPTableView* table=(RAREAPTableView*)tableView_->proxy_;
  NSArray* columns=table.tableColumns;
  NSInteger len=columns.count;
  RAREAPTableColumn* col;
  for(NSInteger i=0;i<len;i++) {
    col=(RAREAPTableColumn*)[columns objectAtIndex:i];
    if(col->modelIndex==index) {
      return col;
    }
  }
  return nil;
}

- (id<RAREiPlatformComponentPainter>)getComponentPainterEx {
  return [((id<RAREiPlatformComponent>) nil_chk([self getTable])) getComponentPainter];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  (void) [((RAREaTableHeader *) nil_chk(tableHeader_)) getMinimumSizeWithRAREUIDimension:size withFloat:maxWidth];
  float w = ((RAREUIDimension *) nil_chk(size))->width_;
  float h = size->height_;
  [((RARETableView *) nil_chk(tableView_)) getMinimumSizeWithRAREUIDimension:size withFloat:maxWidth];
  size->width_ = [JavaLangMath maxWithFloat:w withFloat:size->width_];
  if ([tableHeader_ isVisible]) {
    size->height_ += h;
  }
  if (horizontalScroll_) {
    size->width_ = 100;
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  oldWidth_ = 0;
  if (maxWidth > 0) {
    [((RAREaTableHeader *) nil_chk(tableHeader_)) getSizeWithRAREUIDimension:size withBoolean:YES withFloat:maxWidth];
  }
  else {
    (void) [((RAREaTableHeader *) nil_chk(tableHeader_)) getPreferredSizeWithRAREUIDimension:size];
  }
  float w = ((RAREUIDimension *) nil_chk(size))->width_;
  float h = size->height_;
  [((RARETableView *) nil_chk(tableView_)) getPreferredSizeWithRAREUIDimension:size withFloat:0];
  size->width_ = [JavaLangMath maxWithFloat:w withFloat:size->width_];
  if ([((RAREaTableHeader *) nil_chk(tableHeader_)) isVisible]) {
    size->height_ += h;
  }
}

- (id<RAREiPlatformComponent>)getTable {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)makeSelectionVisible {
  int index = [((RARETableView *) nil_chk(tableView_)) getSelectedIndex];
  if (index != -1) {
    if (horizontalScroll_ && [tableView_ isColumnSelectionAllowed]) {
      int col = [self getSelectedColumn];
      if (col == -1) {
        col = 0;
      }
      RAREUIRectangle *r = [self getViewRect];
      RAREUIRectangle *r2 = [self getCellRectWithInt:index withInt:col withBoolean:YES];
      ((RAREUIRectangle *) nil_chk(r2))->height_ = [JavaLangMath minWithFloat:r2->height_ withFloat:(int) [tableView_ getHeight]];
      if (![((RAREUIRectangle *) nil_chk(r)) containsWithRAREaUIRectangle:r2]) {
        float vex = r->x_ + r->width_;
        float vey = r->y_ + r->height_;
        float cex = r2->x_ + r2->width_;
        float cey = r2->y_ + r2->height_;
        float x = cex - vex;
        float y = cey - vey;
        if (y < 0) {
          y = -r->y_;
        }
        if (x < 0) {
          x = -r->x_;
        }
        if ((x != 0) || (y != 0)) {
          [self scrollByWithFloat:x withFloat:y];
        }
      }
      [tableView_ repaintRowWithInt:index];
    }
    else {
      [tableView_ scrollRowToVisibleWithInt:index];
      [tableView_ repaintRowWithInt:index];
    }
  }
}

- (void)scrollByWithFloat:(float)x
                withFloat:(float)y {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

+ (BOOL)isHorizontalScollEnabledWithRARESPOTTable:(RARESPOTTable *)cfg {
  if ([((SPOTBoolean *) nil_chk(((RARESPOTTable *) nil_chk(cfg))->displayAsGridView_)) booleanValue]) {
    return YES;
  }
  if ([((RARESPOTTable_CAutoResizeMode *) nil_chk(cfg->autoResizeMode_)) intValue] == RARESPOTTable_CAutoResizeMode_none) {
    return YES;
  }
  if (([cfg getScrollPane] != nil) && [((RARESPOTScrollPane *) nil_chk([cfg getScrollPane])) isHorizontalScrollEnabled]) {
    return YES;
  }
  return NO;
}

- (id<RAREiTableComponent>)getMainTable {
  return self;
}

- (id<RAREiTableComponent>)getRowHeaderTable {
  return nil;
}

- (id<RAREiTableComponent>)getRowFooterTable {
  return nil;
}

- (id<RAREiTableComponent>)getColumnHeaderTable {
  return nil;
}

- (id<RAREiTableComponent>)getColumnFooterTable {
  return nil;
}

- (BOOL)isMainTable {
  RAREiTableComponent_TableTypeEnum *tt = [self getTableType];
  return (tt == nil) || (tt == [RAREiTableComponent_TableTypeEnum MAIN]);
}

- (RAREiTableComponent_TableTypeEnum *)getTableType {
  return [((RARETableView *) nil_chk(tableView_)) getTableType];
}

- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)type {
  [((RARETableView *) nil_chk(tableView_)) setTableTypeWithRAREiTableComponent_TableTypeEnum:type];
}

- (void)moveColumnExWithInt:(int)column
                    withInt:(int)targetColumn {
  [((RAREAPTableView*)tableView_->proxy_) moveColumn:column toColumn:targetColumn];
}

- (int)getColumnIndexAtWithFloat:(float)param0
                       withFloat:(float)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getFirstVisibleColumnIndex {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getLastVisibleColumnIndex {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREUIRectangle *)getViewRect {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setSelectedColumnIndexWithInt:(int)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (RAREUIPoint *)getContentOffset {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isScrolling {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)moveLeftRightWithBoolean:(BOOL)param0
                     withBoolean:(BOOL)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)moveUpDownWithBoolean:(BOOL)param0
                  withBoolean:(BOOL)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)scrollToBottomEdge {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)scrollToLeftEdge {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)scrollToRightEdge {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)scrollToTopEdge {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setContentOffsetWithFloat:(float)param0
                        withFloat:(float)param1 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaTableComponent *)other {
  [super copyAllFieldsTo:other];
  other->autoSizeRowsToFit_ = autoSizeRowsToFit_;
  other->descending_ = descending_;
  other->horizontalScroll_ = horizontalScroll_;
  other->oldWidth_ = oldWidth_;
  other->sortColumn_ = sortColumn_;
  other->tableHeader_ = tableHeader_;
  other->tableModel_ = tableModel_;
  other->tableStyle_ = tableStyle_;
  other->tableType_ = tableType_;
  other->tableView_ = tableView_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "convertModelIndexToViewWithInt:", NULL, "I", 0x101, NULL },
    { "convertViewIndexToModelWithInt:", NULL, "I", 0x101, NULL },
    { "createColumnWithNSString:withId:withInt:withId:withRAREiPlatformIcon:", NULL, "LRAREColumn", 0x1, NULL },
    { "isMultiTableComponent", NULL, "Z", 0x1, NULL },
    { "isAtBottomEdge", NULL, "Z", 0x1, NULL },
    { "isAtLeftEdge", NULL, "Z", 0x1, NULL },
    { "isAtRightEdge", NULL, "Z", 0x1, NULL },
    { "isAtTopEdge", NULL, "Z", 0x1, NULL },
    { "getCellRectWithInt:withInt:withBoolean:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getColumnAtWithInt:", NULL, "LRAREColumn", 0x1, NULL },
    { "getFontEx", NULL, "LRAREUIFont", 0x1, NULL },
    { "getGridViewType", NULL, "LRAREiTableComponent_GridViewTypeEnum", 0x1, NULL },
    { "getItemRenderer", NULL, "LRAREiPlatformItemRenderer", 0x1, NULL },
    { "getModel", NULL, "LRAREiTableModel", 0x1, NULL },
    { "getPlatformComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getSelectedColumns", NULL, "LIOSIntArray", 0x1, NULL },
    { "getTableHeader", NULL, "LRAREaTableHeader", 0x1, NULL },
    { "getTableModel", NULL, "LRAREiTableModel", 0x1, NULL },
    { "getTableView", NULL, "LRARETableView", 0x1, NULL },
    { "hasHadInteraction", NULL, "Z", 0x1, NULL },
    { "isAutoSizeRows", NULL, "Z", 0x1, NULL },
    { "isEditing", NULL, "Z", 0x1, NULL },
    { "isSortDescending", NULL, "Z", 0x1, NULL },
    { "createTableModel", NULL, "V", 0x4, NULL },
    { "initialize__WithRARETableView:withRARESPOTTable:", NULL, "V", 0x4, NULL },
    { "resetTableExWithRAREUTiFilterableList:withRAREUTiFilterableList:", NULL, "V", 0x4, NULL },
    { "getAPTableColumnWithInt:", NULL, "LNSObject", 0x104, NULL },
    { "getAPTableColumnForModelIndexWithInt:", NULL, "LNSObject", 0x104, NULL },
    { "getComponentPainterEx", NULL, "LRAREiPlatformComponentPainter", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getTable", NULL, "LRAREiPlatformComponent", 0x404, NULL },
    { "scrollByWithFloat:withFloat:", NULL, "V", 0x404, NULL },
    { "isHorizontalScollEnabledWithRARESPOTTable:", NULL, "Z", 0xc, NULL },
    { "getMainTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getRowHeaderTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getRowFooterTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getColumnHeaderTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "getColumnFooterTable", NULL, "LRAREiTableComponent", 0x1, NULL },
    { "isMainTable", NULL, "Z", 0x1, NULL },
    { "getTableType", NULL, "LRAREiTableComponent_TableTypeEnum", 0x1, NULL },
    { "moveColumnExWithInt:withInt:", NULL, "V", 0x104, NULL },
    { "getColumnIndexAtWithFloat:withFloat:", NULL, "I", 0x401, NULL },
    { "getFirstVisibleColumnIndex", NULL, "I", 0x401, NULL },
    { "getLastVisibleColumnIndex", NULL, "I", 0x401, NULL },
    { "getViewRect", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "setSelectedColumnIndexWithInt:", NULL, "V", 0x401, NULL },
    { "setSelectedColumnIndicesWithIntArray:", NULL, "V", 0x401, NULL },
    { "getContentOffset", NULL, "LRAREUIPoint", 0x401, NULL },
    { "getScrollingEdgePainter", NULL, "LRAREUIScrollingEdgePainter", 0x401, NULL },
    { "isScrolling", NULL, "Z", 0x401, NULL },
    { "moveLeftRightWithBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "moveUpDownWithBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "scrollToBottomEdge", NULL, "V", 0x401, NULL },
    { "scrollToLeftEdge", NULL, "V", 0x401, NULL },
    { "scrollToRightEdge", NULL, "V", 0x401, NULL },
    { "scrollToTopEdge", NULL, "V", 0x401, NULL },
    { "setContentOffsetWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setScrollingEdgePainterWithRAREUIScrollingEdgePainter:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "sortColumn_", NULL, 0x4, "I" },
    { "descending_", NULL, 0x4, "Z" },
    { "autoSizeRowsToFit_", NULL, 0x4, "Z" },
    { "tableHeader_", NULL, 0x4, "LRAREaTableHeader" },
    { "horizontalScroll_", NULL, 0x4, "Z" },
    { "tableModel_", NULL, 0x4, "LRAREiTableModel" },
    { "tableStyle_", NULL, 0x4, "LRARETableStyle" },
    { "tableView_", NULL, 0x4, "LRARETableView" },
    { "oldWidth_", NULL, 0x4, "I" },
    { "tableType_", NULL, 0x4, "LRAREiTableComponent_TableTypeEnum" },
  };
  static J2ObjcClassInfo _RAREaTableComponent = { "aTableComponent", "com.appnativa.rare.ui.table", NULL, 0x401, 60, methods, 10, fields, 0, NULL};
  return &_RAREaTableComponent;
}

@end
