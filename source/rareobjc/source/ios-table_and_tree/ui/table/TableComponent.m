//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios-table_and_tree/com/appnativa/rare/ui/table/TableComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ScrollView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/spot/Table.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/ScrollPanePanel.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iScrollerSupport.h"
#include "com/appnativa/rare/ui/painter/UIScrollingEdgePainter.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/table/TableHeader.h"
#include "com/appnativa/rare/ui/table/TableView.h"
#include "com/appnativa/rare/ui/table/aTableComponent.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"

@implementation RARETableComponent

- (id)initWithRARESPOTTable:(RARESPOTTable *)cfg
                withBoolean:(BOOL)main {
  return [self initRARETableComponentWithRARETableView:[[RARETableView alloc] init] withRARESPOTTable:cfg withBoolean:main];
}

- (id)initRARETableComponentWithRARETableView:(RARETableView *)table
                            withRARESPOTTable:(RARESPOTTable *)cfg
                                  withBoolean:(BOOL)main {
  if (self = [super init]) {
    horizontalScroll_ = main && [RAREaTableComponent isHorizontalScollEnabledWithRARESPOTTable:cfg];
    if (horizontalScroll_) {
      RAREScrollView *sv = [[RAREScrollView alloc] init];
      [sv setShowsVerticalScrollIndicatorWithBoolean:NO];
      [self setViewWithRAREView:sv];
      if ([RAREUIScrollingEdgePainter isPaintHorizontalScrollEdge]) {
        RAREUIScrollingEdgePainter *sp = (RAREUIScrollingEdgePainter *) check_class_cast([((RAREUIScrollingEdgePainter *) nil_chk([RAREUIScrollingEdgePainter getInstance])) clone], [RAREUIScrollingEdgePainter class]);
        [((RAREUIScrollingEdgePainter *) nil_chk(sp)) setScrollerSupportWithRAREiScrollerSupport:sv];
        [sv setScrollingEdgePainterWithRAREUIScrollingEdgePainter:sp];
      }
      RAREContainer *c = [[RAREContainer alloc] initWithRAREParentView:[[RARETableComponent_TableContainer alloc] initWithRARETableComponent:self]];
      [self initialize__WithRARETableView:table withRARESPOTTable:cfg];
      [c addWithRAREiPlatformComponent:headerComponent_ = [((RARETableView *) nil_chk(table)) getHeader]];
      [c addWithRAREiPlatformComponent:listComponent_ = [[RAREScrollPanePanel alloc] initWithId:tableView_]];
      [self addWithRAREiPlatformComponent:c];
      [table setHorizontalScollEnabledWithBoolean:YES];
    }
    else {
      [self setViewWithRAREView:[[RARETableComponent_TableContainer alloc] initWithRARETableComponent:self]];
      [self initialize__WithRARETableView:table withRARESPOTTable:cfg];
      [self addWithRAREiPlatformComponent:headerComponent_ = [((RARETableView *) nil_chk(table)) getHeader]];
      [self addWithRAREiPlatformComponent:listComponent_ = [[RAREScrollPanePanel alloc] initWithId:tableView_]];
    }
    if ([RAREUIScrollingEdgePainter isPaintVerticalScrollEdge]) {
      [((RARETableView *) nil_chk(table)) setScrollingEdgePainterWithRAREUIScrollingEdgePainter:[RAREUIScrollingEdgePainter getInstance]];
    }
    [((RARETableView *) nil_chk(tableView_)) setTableComponentWithRARETableComponent:self];
    [tableView_ setUsePainterBorderWithBoolean:NO];
  }
  return self;
}

- (id)initWithRARETableView:(RARETableView *)table
          withRARESPOTTable:(RARESPOTTable *)cfg
                withBoolean:(BOOL)main {
  return [self initRARETableComponentWithRARETableView:table withRARESPOTTable:cfg withBoolean:main];
}

- (RAREUIPoint *)getContentOffset {
  RAREUIPoint *p = [((RARETableView *) nil_chk(tableView_)) getContentOffset];
  if (horizontalScroll_) {
    RAREUIPoint *p2 = [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) getContentOffset];
    ((RAREUIPoint *) nil_chk(p))->x_ = ((RAREUIPoint *) nil_chk(p2))->x_;
  }
  return p;
}

- (void)setHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss {
  if (horizontalScroll_) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) setHeaderViewWithRAREiScrollerSupport:ss];
  }
  else {
    [((RARETableView *) nil_chk([self getTableView])) setHeaderViewWithRAREiScrollerSupport:ss];
  }
}

- (void)setFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss {
  if (horizontalScroll_) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) setFooterViewWithRAREiScrollerSupport:ss];
  }
  else {
    [((RARETableView *) nil_chk([self getTableView])) setFooterViewWithRAREiScrollerSupport:ss];
  }
}

- (void)setRowHeaderViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss {
  [((RARETableView *) nil_chk([self getTableView])) setRowHeaderViewWithRAREiScrollerSupport:ss];
}

- (void)setRowFooterViewWithRAREiScrollerSupport:(id<RAREiScrollerSupport>)ss {
  [((RARETableView *) nil_chk([self getTableView])) setRowFooterViewWithRAREiScrollerSupport:ss];
}

- (void)setContentOffsetWithFloat:(float)x
                        withFloat:(float)y {
  if (horizontalScroll_) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) setContentOffsetWithFloat:x withFloat:y];
  }
  else {
    [((RARETableView *) nil_chk([self getTableView])) setContentOffsetWithFloat:x withFloat:y];
  }
}

- (BOOL)isScrolling {
  if (horizontalScroll_) {
    if ([((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) isScrolling]) {
      return YES;
    }
  }
  return [((RARETableView *) nil_chk(tableView_)) isScrollingEx];
}

- (BOOL)isAtLeftEdge {
  if (horizontalScroll_) {
    return [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) isAtLeftEdge];
  }
  return YES;
}

- (BOOL)isAtRightEdge {
  if (horizontalScroll_) {
    return [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) isAtRightEdge];
  }
  return YES;
}

- (void)dispose {
  [super dispose];
  headerComponent_ = nil;
  listComponent_ = nil;
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  [super setForegroundWithRAREUIColor:fg];
  if (listComponent_ != nil) {
    [listComponent_ setForegroundWithRAREUIColor:fg];
  }
}

- (int)getColumnIndexAtWithFloat:(float)x
                       withFloat:(float)y {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getColumnIndexAtWithFloat:x withFloat:y];
}

- (int)getFirstVisibleColumnIndex {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getFirstVisibleColumnInView];
}

- (int)getLastVisibleColumnIndex {
  return [((RAREaTableHeader *) nil_chk(tableHeader_)) getLastVisibleColumnInView];
}

- (RAREComponent *)getTable {
  return listComponent_;
}

- (void)scrollByWithFloat:(float)x
                withFloat:(float)y {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    RAREScrollView *sv = (RAREScrollView *) check_class_cast(view_, [RAREScrollView class]);
    RAREUIPoint *pt = [((RAREScrollView *) nil_chk(sv)) getContentOffset];
    x += ((RAREUIPoint *) nil_chk(pt))->x_;
    y += pt->y_;
    [sv setContentOffsetWithFloat:x withFloat:y];
  }
}

- (void)setSelectedColumnIndexWithInt:(int)index {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSelectedIndexWithInt:index];
}

- (void)setSelectedColumnIndicesWithIntArray:(IOSIntArray *)indices {
  [((RAREaTableHeader *) nil_chk(tableHeader_)) setSelectedIndicesWithIntArray:indices];
}

- (RAREUIRectangle *)getViewRect {
  RAREUIPoint *pt = [((RARETableView *) nil_chk(tableView_)) getContentOffset];
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    ((RAREUIPoint *) nil_chk(pt))->x_ = ((RAREUIPoint *) nil_chk([((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) getContentOffset]))->x_;
  }
  float w = [((RAREView *) nil_chk(view_)) getWidth];
  float h = [view_ getHeight];
  id<RAREiPlatformBorder> b = [self getBorder];
  if (b != nil) {
    RAREUIInsets *in = [b getBorderInsetsWithRAREUIInsets:[[RAREUIInsets alloc] init]];
    w -= (((RAREUIInsets *) nil_chk(in))->left_ + in->right_);
    h -= (in->top_ + in->bottom_);
  }
  return [[RAREUIRectangle alloc] initWithFloat:((RAREUIPoint *) nil_chk(pt))->x_ withFloat:pt->y_ withFloat:[RAREUIScreen snapToSizeWithFloat:w] withFloat:[RAREUIScreen snapToSizeWithFloat:h]];
}

- (void)setMeasuredHeaderHeightWithInt:(int)height {
  measuredHeaderHeight_ = height;
}

- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)painter {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) setScrollingEdgePainterWithRAREUIScrollingEdgePainter:painter];
  }
  else {
    [((RARETableComponent_TableContainer *) check_class_cast(view_, [RARETableComponent_TableContainer class])) setScrollingEdgePainterWithRAREUIScrollingEdgePainter:painter];
  }
}

- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    return [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) getScrollingEdgePainter];
  }
  else {
    return [((RARETableComponent_TableContainer *) check_class_cast(view_, [RARETableComponent_TableContainer class])) getScrollingEdgePainter];
  }
}

- (void)scrollToBottomEdge {
  [((RARETableView *) nil_chk(tableView_)) scrollToBottomEdge];
}

- (void)scrollToLeftEdge {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) scrollToLeftEdge];
  }
}

- (void)scrollToRightEdge {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) scrollToRightEdge];
  }
}

- (void)scrollToTopEdge {
  [((RARETableView *) nil_chk(tableView_)) scrollToTopEdge];
}

- (void)moveLeftRightWithBoolean:(BOOL)left
                     withBoolean:(BOOL)block {
  if ([view_ isKindOfClass:[RAREScrollView class]]) {
    [((RAREScrollView *) check_class_cast(view_, [RAREScrollView class])) moveLeftRightWithBoolean:left withBoolean:block];
  }
}

- (void)moveUpDownWithBoolean:(BOOL)up
                  withBoolean:(BOOL)block {
  [((RARETableView *) nil_chk(tableView_)) moveUpDownWithBoolean:up withBoolean:block];
}

- (void)copyAllFieldsTo:(RARETableComponent *)other {
  [super copyAllFieldsTo:other];
  other->headerComponent_ = headerComponent_;
  other->listComponent_ = listComponent_;
  other->measuredHeaderHeight_ = measuredHeaderHeight_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getContentOffset", NULL, "LRAREUIPoint", 0x1, NULL },
    { "isScrolling", NULL, "Z", 0x1, NULL },
    { "isAtLeftEdge", NULL, "Z", 0x1, NULL },
    { "isAtRightEdge", NULL, "Z", 0x1, NULL },
    { "getTable", NULL, "LRAREComponent", 0x4, NULL },
    { "scrollByWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "getViewRect", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getScrollingEdgePainter", NULL, "LRAREUIScrollingEdgePainter", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "headerComponent_", NULL, 0x0, "LRARETableHeader" },
    { "listComponent_", NULL, 0x0, "LRAREScrollPanePanel" },
  };
  static J2ObjcClassInfo _RARETableComponent = { "TableComponent", "com.appnativa.rare.ui.table", NULL, 0x1, 8, methods, 2, fields, 0, NULL};
  return &_RARETableComponent;
}

@end
@implementation RARETableComponent_TableContainer

- (id)initWithRARETableComponent:(RARETableComponent *)outer$ {
  this$0_ = outer$;
  if (self = [super initWithId:[RAREaView createAPView]]) {
    headerSize_ = [[RAREUIDimension alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    [self setUsePainterBackgroundColorWithBoolean:NO];
  }
  return self;
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  RAREUIInsets *in = [this$0_ getInsetsWithRAREUIInsets:nil];
  width -= (((RAREUIInsets *) nil_chk(in))->left_ + in->right_);
  height -= (in->top_ + in->bottom_);
  [((RARETableView *) nil_chk(this$0_->tableView_)) updateColumnSizesWithInt:(int) width withInt:(int) height];
  if ((((RAREUIDimension *) nil_chk(headerSize_))->height_ == 0) && [((RAREaTableHeader *) nil_chk(this$0_->tableHeader_)) isVisible]) {
    [this$0_->tableHeader_ getPreferredSizeExWithRAREUIDimension:headerSize_ withFloat:(int) width];
  }
  int hh = 0;
  if ([((RAREaTableHeader *) nil_chk(this$0_->tableHeader_)) isVisible]) {
    hh = (this$0_->measuredHeaderHeight_ > 0) ? this$0_->measuredHeaderHeight_ : [headerSize_ intHeight];
    [this$0_->tableHeader_ setBoundsWithFloat:in->left_ withFloat:in->top_ withFloat:width withFloat:hh];
  }
  [this$0_->tableView_ setBoundsWithFloat:in->left_ withFloat:in->top_ + hh withFloat:width withFloat:height - hh];
}

- (BOOL)needsScrollView {
  return NO;
}

- (void)setScrollingEdgePainterWithRAREUIScrollingEdgePainter:(RAREUIScrollingEdgePainter *)painter {
  [((RARETableView *) nil_chk(this$0_->tableView_)) setScrollingEdgePainterWithRAREUIScrollingEdgePainter:painter];
}

- (RAREUIScrollingEdgePainter *)getScrollingEdgePainter {
  return [((RARETableView *) nil_chk(this$0_->tableView_)) getScrollingEdgePainter];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
  if (((RAREUIDimension *) nil_chk(headerSize_))->height_ == 0) {
    [((RAREaTableHeader *) nil_chk(this$0_->tableHeader_)) getPreferredSizeExWithRAREUIDimension:headerSize_ withFloat:maxWidth];
  }
  ((RAREUIDimension *) nil_chk(size))->height_ = headerSize_->height_ * 2;
  size->width_ = size->height_;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [((RARETableView *) nil_chk(this$0_->tableView_)) getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  [((RAREaTableHeader *) nil_chk(this$0_->tableHeader_)) getPreferredSizeExWithRAREUIDimension:headerSize_ withFloat:maxWidth];
  ((RAREUIDimension *) nil_chk(size))->width_ = ((RAREUIDimension *) nil_chk(headerSize_))->width_;
  if ([this$0_->tableHeader_ isVisible]) {
    size->height_ += headerSize_->height_;
  }
  RAREUIInsets *in = [this$0_ getInsetsWithRAREUIInsets:nil];
  size->width_ += (((RAREUIInsets *) nil_chk(in))->left_ + in->right_);
  size->height_ += (in->top_ + in->bottom_);
}

- (BOOL)isScrollView {
  return YES;
}

- (BOOL)isScrolling {
  return [((RARETableView *) nil_chk(this$0_->tableView_)) isScrolling];
}

- (void)copyAllFieldsTo:(RARETableComponent_TableContainer *)other {
  [super copyAllFieldsTo:other];
  other->headerSize_ = headerSize_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "needsScrollView", NULL, "Z", 0x1, NULL },
    { "getScrollingEdgePainter", NULL, "LRAREUIScrollingEdgePainter", 0x1, NULL },
    { "isScrollView", NULL, "Z", 0x1, NULL },
    { "isScrolling", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARETableComponent" },
    { "headerSize_", NULL, 0x0, "LRAREUIDimension" },
  };
  static J2ObjcClassInfo _RARETableComponent_TableContainer = { "TableContainer", "com.appnativa.rare.ui.table", "TableComponent", 0x4, 4, methods, 2, fields, 0, NULL};
  return &_RARETableComponent_TableContainer;
}

@end
