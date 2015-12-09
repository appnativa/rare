//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/TableView.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aTableBasedView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/iTableModel.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/table/TableHeader.h"
#include "com/appnativa/rare/ui/table/TableHelper.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "com/appnativa/rare/ui/table/iTableComponent.h"
#include "com/appnativa/rare/ui/text/HTMLCharSequence.h"
#include "com/appnativa/rare/ui/tree/iTreeItem.h"
#include "com/appnativa/util/IntList.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"
#import "RAREAPTableView.h"
 #import "APView+Component.h"

@implementation RARETableView

static RARERenderableDataItem * RARETableView_NULL_REPLACEMENT_;

+ (RARERenderableDataItem *)NULL_REPLACEMENT {
  return RARETableView_NULL_REPLACEMENT_;
}

+ (void)setNULL_REPLACEMENT:(RARERenderableDataItem *)NULL_REPLACEMENT {
  RARETableView_NULL_REPLACEMENT_ = NULL_REPLACEMENT;
}

- (id)init {
  return [self initRARETableViewWithId:[RARETableView createTableProxy]];
}

- (id)initRARETableViewWithId:(id)proxy {
  if (self = [super initWithId:proxy]) {
    [self createHeader];
  }
  return self;
}

- (id)initWithId:(id)proxy {
  return [self initRARETableViewWithId:proxy];
}

- (void)columnSelectedWithInt:(int)row
                      withInt:(int)column {
  [((RARETableHeader *) nil_chk(header_)) setSelectedIndexWithInt:column];
  [super itemSelectedWithInt:row];
}

- (void)columnSizesUpdated {
  [self removeRowHeights];
  return [((RAREAPTableView*)proxy_) columnSizesUpdated];
}

- (void)createHeader {
  header_ = [[RARETableHeader alloc] initWithRARETableView:self withId:[self getHeaderProxy]];
}

- (id)createNativeColumnViewWithInt:(int)row
                            withInt:(int)modelCol
                     withRAREColumn:(RAREColumn *)col
         withRARERenderableDataItem:(RARERenderableDataItem *)item {
  id<RAREiPlatformComponent> c = (item == nil) ? nil : [item getRenderingComponent];
  if (c != nil) {
    return [c getProxy];
  }
  id<RAREiPlatformRenderingComponent> rc = [((RAREColumn *) nil_chk(col)) getCellRenderer];
  if (rc != nil) {
    return [rc createNewNativeView];
  }
  return [[RAREaPlatformTableBasedView_TableRowView alloc] initWithRAREaPlatformTableBasedView:self];
}

- (void)disposeEx {
  if (header_ != nil) {
    [header_ dispose];
  }
  header_ = nil;
  [super disposeEx];
}

- (id<JavaLangCharSequence>)getColumnTitleWithRAREColumn:(RAREColumn *)c {
  return [RAREHTMLCharSequence checkSequenceWithJavaLangCharSequence:[((RAREColumn *) nil_chk(c)) getColumnTitle] withRAREUIFont:[((RARETableHeader *) nil_chk(header_)) getFont]];
}

- (int)getGridRowHeight {
  if ([self getRowCount] > 0) {
    return [self getRowHeightWithInt:0];
  }
  return [self getRowHeight];
}

- (RARETableHeader *)getHeader {
  return header_;
}

- (id)getHeaderProxy {
  return ((RAREAPTableView*)proxy_)->headerProxy;
}

- (int)getRowHeightWithInt:(int)row {
  int rh = [self getRowHeight];
  if (fixedRowSize_) {
    return rh;
  }
  return [((RARETableHeader *) nil_chk(header_)) getRowHeightWithInt:row withRAREiPlatformItemRenderer:itemRenderer_ withInt:rh] + 8;
}

- (float)getPreferredHeightWithInt:(int)row {
  if (header_ != nil) {
    return [self getRowHeightWithInt:row];
  }
  return [super getPreferredHeightWithInt:row];
}

- (int)getSelectedColumn {
  return (header_ == nil) ? -1 : [header_ getSelectedColumn];
}

- (float)getSelectionPaintEndXWithFloat:(float)currentEndX {
  if (![self isTable]) {
    return currentEndX;
  }
  return [((RARETableHeader *) check_class_cast([((RARETableComponent *) nil_chk(tableComponent_)) getTableHeader], [RARETableHeader class])) getSelectionPaintEndXWithFloat:currentEndX];
}

- (float)getSelectionPaintStartXWithFloat:(float)currentStartX {
  if (![self isTable]) {
    return currentStartX;
  }
  return [((RARETableHeader *) check_class_cast([((RARETableComponent *) nil_chk(tableComponent_)) getTableHeader], [RARETableHeader class])) getSelectionPaintStartXWithFloat:currentStartX];
}

- (RARETableComponent *)getTableComponent {
  return tableComponent_;
}

- (BOOL)isScrolling {
  return (tableComponent_ == nil) ? [super isScrolling] : [tableComponent_ isScrolling];
}

- (BOOL)isScrollingEx {
  return [super isScrolling];
}

- (BOOL)isColumnSelectedWithInt:(int)col {
  return (header_ == nil) ? NO : [header_ isColumnSelectedWithInt:col];
}

- (BOOL)isColumnSelectionAllowed {
  return (header_ == nil) ? NO : [header_ isColumnSelectionAllowed];
}

- (BOOL)isTable {
  return YES;
}

- (void)layoutWithId:(id)parentUIView
withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
             withInt:(int)parentWidth
             withInt:(int)parentHeight {
  int x = 0;
  RAREColumn *c;
  int span;
  RARERenderableDataItem *item;
  int width = 0;
  IOSObjectArray *columns = [((RARETableHeader *) nil_chk(header_)) getColumns];
  int n = 0;
  int len = (int) [((IOSObjectArray *) nil_chk(columns)) count];
  int d = 0;
  IOSIntArray *vp = header_->viewPositions_;
  if (showVertivalGridLines_) {
    d = [RAREScreenUtils PLATFORM_PIXELS_1];
  }
  int lvc = [header_ getLastVisibleColumn];
  for (int i = 0; i < len; i++) {
    c = IOSObjectArray_Get(columns, IOSIntArray_Get(nil_chk(vp), i));
    if (![((RAREColumn *) nil_chk(c)) isVisible]) {
      continue;
    }
    item = [((RARERenderableDataItem *) nil_chk(rowItem)) getItemExWithInt:i];
    if (item == nil) {
      item = [RAREaPlatformTableBasedView NULL_ITEM];
    }
    span = [((RARERenderableDataItem *) nil_chk(item)) getColumnSpan];
    if (span == 0) {
      span = 1;
    }
    if (span != 1) {
      if (span < 0) {
        span = len;
      }
      width = [RARETableHelper getSpanWidthWithInt:i withInt:span withRAREColumnArray:columns withIntArray:vp];
    }
    else {
      width = [c getWidth];
    }
    i += span - 1;
    if (lvc == i) {
      [self layoutItemViewWithId:parentUIView withInt:n withInt:x withInt:parentWidth - x withInt:parentHeight];
    }
    else {
      [self layoutItemViewWithId:parentUIView withInt:n withInt:x withInt:width withInt:parentHeight];
    }
    x += width + d;
    n++;
  }
}

- (void)rectOfRowWithInt:(int)row
     withRAREUIRectangle:(RAREUIRectangle *)rect {
  CGRect r=[((RAREAPTableView*)proxy_) rectOfRow: row];
  rect->x_=r.origin.x;
  rect->y_=r.origin.y;
  rect->width_=r.size.width;
  rect->height_=r.size.height;
}

- (void)refreshItems {
  [super refreshItems];
  if ((header_ != nil) && [header_ isGridView]) {
    [header_ handleGridViewWithInt:(int) [self getWidth] withInt:(int) [self getHeight] withInt:[self getGridRowHeight] withBoolean:YES];
  }
  [self sizeToFit];
}

- (void)removeRowHeights {
  if (!heightsDirty_ || !((RARETableComponent *) nil_chk(tableComponent_))->autoSizeRowsToFit_) {
    return;
  }
  id<JavaUtilList> list = [((id<RAREiTableModel>) nil_chk([((RARETableComponent *) nil_chk(tableComponent_)) getTableModel])) getRowsEx];
  int len = [((id<JavaUtilList>) nil_chk(list)) size];
  for (int i = 0; i < len; i++) {
    [((RARERenderableDataItem *) nil_chk([list getWithInt:0])) setHeightWithInt:0];
  }
}

- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti {
  if (renderingCallback_ != nil) {
    ((RAREaTableBasedView_RowView *) nil_chk(view))->row_ = row;
    if ([renderingCallback_ renderItemWithInt:row withRARERenderableDataItem:item withRAREaTableBasedView_RowView:view withBoolean:isSelected withBoolean:isPressed withRAREiTreeItem:ti]) {
      return;
    }
  }
  [((RAREaPlatformTableBasedView_TableRowView *) check_class_cast(view, [RAREaPlatformTableBasedView_TableRowView class])) renderWithRAREView:self withRAREiPlatformComponent:[self getComponent] withRAREaListItemRenderer:itemRenderer_ withInt:row withRARERenderableDataItem:item withRAREColumnArray:((RARETableHeader *) nil_chk(header_))->columns_ withBoolean:isSelected withBoolean:isPressed withRAREiTreeItem:ti withIntArray:header_->viewPositions_];
}

- (void)setAutoSizeColumnsWithBoolean:(BOOL)autoSizeColumns {
  self->autoSizeColumns_ = autoSizeColumns;
  if (tableComponent_ != nil) {
    [self sizeToFit];
  }
}

- (void)setHorizontalScollEnabledWithBoolean:(BOOL)enabled {
  horizontalScollEnabled_ = enabled;
}

- (void)setShowGridLinesWithBoolean:(BOOL)horizontal
                        withBoolean:(BOOL)vertical
                    withRAREUIColor:(RAREUIColor *)color
                   withRAREUIStroke:(RAREUIStroke *)stroke {
  dividerLineColor_ = color;
  dividerStroke_ = stroke;
  showDivider_ = horizontal;
  showHorizontalGridLines_ = horizontal;
  [self setShowVertivalGridLinesWithBoolean:vertical];
}

- (void)setTableComponentWithRARETableComponent:(RARETableComponent *)tableComponent {
  self->tableComponent_ = tableComponent;
}

- (void)sizeColumnsToFit {
  if (header_ != nil) {
    [header_ sizeColumnsToFitTableData];
    [self columnSizesUpdated];
  }
}

- (void)sizeRowsToFit {
  [self columnSizesUpdated];
}

- (void)sizeToFit {
  if (tableComponent_ != nil) {
    needSizeToFitCall_ = NO;
    if (!columnSizesInitialized_) {
      needSizeToFitCall_ = YES;
      return;
    }
    if (self->autoSizeColumns_) {
      [self sizeColumnsToFit];
      [tableComponent_ revalidate];
    }
  }
}

- (void)makeSelectionVisible {
  [((RARETableComponent *) nil_chk(tableComponent_)) makeSelectionVisible];
}

- (BOOL)updateColumnSizesWithInt:(int)width
                         withInt:(int)height {
  if (([JavaLangMath absWithInt:oldWidth_TableView_ - width] > 5) || (([((RARETableHeader *) nil_chk(header_)) getGridViewType] == [RAREiTableComponent_GridViewTypeEnum HORIZONTAL_WRAP]) && ([JavaLangMath absWithInt:oldHeight_TableView_ - height] > 5))) {
    oldWidth_TableView_ = width;
    oldHeight_TableView_ = height;
    columnSizesInitialized_ = YES;
    BOOL updated = NO;
    if ([((RARETableHeader *) nil_chk(header_)) isGridView]) {
      width = [((RARETableComponent *) nil_chk(tableComponent_)) getWidth];
      height = [tableComponent_ getHeight] - [header_ getHeight];
      updated = [header_ handleGridViewWithInt:width withInt:height withInt:[self getGridRowHeight] withBoolean:NO];
    }
    else {
      if ([header_ isAutoSizedColumns]) {
        return NO;
      }
      int leftOver = [RARETableHelper calculateColumnSizesWithRAREiPlatformComponent:component_ withRAREColumnArray:[header_ getColumns] withInt:width withBoolean:YES];
      if ((leftOver < 0) && !horizontalScollEnabled_) {
        [header_ reduceColumnSizesWithRAREiPlatformItemRenderer:itemRenderer_ withInt:-leftOver];
      }
      updated = YES;
    }
    if (updated) {
      [self columnSizesUpdated];
    }
    if (needSizeToFitCall_) {
      [self sizeToFit];
    }
    return updated;
  }
  else {
    return NO;
  }
}

- (void)updateRenderInsetsForCheckBoxWithFloat:(float)left
                                     withFloat:(float)right {
}

+ (id)createTableProxy {
  RAREAPTableView* v=[[[RAREAPTableView alloc]init] configureForTable];
  return v;
}

- (RAREiTableComponent_TableTypeEnum *)getTableType {
  return tableType_;
}

- (void)setTableTypeWithRAREiTableComponent_TableTypeEnum:(RAREiTableComponent_TableTypeEnum *)tableType {
  self->tableType_ = tableType;
}

- (void)moveColumnExWithInt:(int)from
                    withInt:(int)to {
  [(RAREAPTableView*)proxy_ moveColumn: from toColumn: to];
}

- (void)moveColumnWithInt:(int)from
                  withInt:(int)to {
  [self moveColumnExWithInt:from withInt:to];
  for (id<RAREiPlatformRenderingComponent> __strong row in nil_chk(rows_)) {
    if ([(id) row isKindOfClass:[RAREaPlatformTableBasedView_UITableCellViewRenderingComponent class]]) {
      [((RAREaPlatformTableBasedView_UITableCellViewRenderingComponent *) check_class_cast(row, [RAREaPlatformTableBasedView_UITableCellViewRenderingComponent class])) columnMovedWithInt:from withInt:to];
    }
  }
}

+ (void)initialize {
  if (self == [RARETableView class]) {
    RARETableView_NULL_REPLACEMENT_ = [[RARERenderableDataItem alloc] initWithId:@""];
  }
}

- (void)copyAllFieldsTo:(RARETableView *)other {
  [super copyAllFieldsTo:other];
  other->autoSizeColumns_ = autoSizeColumns_;
  other->columnWidths_ = columnWidths_;
  other->columnWidthsIndex_ = columnWidthsIndex_;
  other->computeSize_ = computeSize_;
  other->header_ = header_;
  other->heightsDirty_ = heightsDirty_;
  other->horizontalScollEnabled_ = horizontalScollEnabled_;
  other->needSizeToFitCall_ = needSizeToFitCall_;
  other->oldHeight_TableView_ = oldHeight_TableView_;
  other->oldWidth_TableView_ = oldWidth_TableView_;
  other->tableComponent_ = tableComponent_;
  other->tableType_ = tableType_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "columnSizesUpdated", NULL, "V", 0x102, NULL },
    { "createHeader", NULL, "V", 0x4, NULL },
    { "createNativeColumnViewWithInt:withInt:withRAREColumn:withRARERenderableDataItem:", NULL, "LNSObject", 0x4, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "getColumnTitleWithRAREColumn:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "getGridRowHeight", NULL, "I", 0x4, NULL },
    { "getHeader", NULL, "LRARETableHeader", 0x1, NULL },
    { "getHeaderProxy", NULL, "LNSObject", 0x104, NULL },
    { "getSelectionPaintEndXWithFloat:", NULL, "F", 0x4, NULL },
    { "getSelectionPaintStartXWithFloat:", NULL, "F", 0x4, NULL },
    { "getTableComponent", NULL, "LRARETableComponent", 0x1, NULL },
    { "isScrolling", NULL, "Z", 0x1, NULL },
    { "isScrollingEx", NULL, "Z", 0x4, NULL },
    { "isColumnSelectedWithInt:", NULL, "Z", 0x1, NULL },
    { "isColumnSelectionAllowed", NULL, "Z", 0x1, NULL },
    { "isTable", NULL, "Z", 0x4, NULL },
    { "rectOfRowWithInt:withRAREUIRectangle:", NULL, "V", 0x104, NULL },
    { "removeRowHeights", NULL, "V", 0x2, NULL },
    { "updateColumnSizesWithInt:withInt:", NULL, "Z", 0x1, NULL },
    { "updateRenderInsetsForCheckBoxWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "createTableProxy", NULL, "LNSObject", 0x10c, NULL },
    { "getTableType", NULL, "LRAREiTableComponent_TableTypeEnum", 0x1, NULL },
    { "moveColumnExWithInt:withInt:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "NULL_REPLACEMENT_", NULL, 0xa, "LRARERenderableDataItem" },
    { "header_", NULL, 0x4, "LRARETableHeader" },
  };
  static J2ObjcClassInfo _RARETableView = { "TableView", "com.appnativa.rare.ui.table", NULL, 0x1, 23, methods, 2, fields, 0, NULL};
  return &_RARETableView;
}

@end
