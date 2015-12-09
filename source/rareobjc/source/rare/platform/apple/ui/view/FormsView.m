//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/FormsView.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iLayoutManager.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/UICellPainter.h"
#include "com/appnativa/rare/ui/painter/iPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "com/jgoodies/forms/layout/CellConstraints.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "java/util/Arrays.h"
#import "RAREAPView.h"

@implementation RAREFormsView

- (id)init {
  if (self = [super initWithId:[RAREFormsView createProxy]]) {
    size_ = [[RAREUIDimension alloc] init];
    measureSize_ = [[RAREUIDimension alloc] init];
    insets_ = [[RAREUIInsets alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    self->layout__ = [[RAREJGFormLayout alloc] init];
  }
  return self;
}

- (id)initWithRAREJGFormLayout:(RAREJGFormLayout *)layout {
  if (self = [super initWithId:[RAREFormsView createProxy]]) {
    size_ = [[RAREUIDimension alloc] init];
    measureSize_ = [[RAREUIDimension alloc] init];
    insets_ = [[RAREUIInsets alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    self->layout__ = layout;
  }
  return self;
}

- (id)initWithId:(id)proxy
withRAREJGFormLayout:(RAREJGFormLayout *)layout {
  if (self = [super initWithId:proxy]) {
    size_ = [[RAREUIDimension alloc] init];
    measureSize_ = [[RAREUIDimension alloc] init];
    insets_ = [[RAREUIInsets alloc] init];
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    self->layout__ = layout;
  }
  return self;
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position {
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY] withId:constraints];
  [self addWithInt:position withRAREView:[c getView]];
  RAREJGCellConstraints *cc = (RAREJGCellConstraints *) check_class_cast(constraints, [RAREJGCellConstraints class]);
  [((RAREJGFormLayout *) nil_chk(layout__)) addLayoutComponentWithRAREiPlatformComponent:c withRAREJGCellConstraints:cc];
}

+ (id)createProxy {
  return [[RAREAPView alloc]init];
}

- (void)setFormLayoutWithRAREJGFormLayout:(RAREJGFormLayout *)layout {
  self->layout__ = layout;
}

- (void)invalidateLayout {
  if (layout__ != nil) {
    [layout__ invalidateCaches];
  }
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  if (layout__ == nil) {
    return;
  }
  RAREContainer *container = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
  if ([((RAREContainer *) nil_chk(container)) isPartOfAnimation]) {
    return;
  }
  if (layoutTracker_ != nil) {
    [layoutTracker_ willPerformLayoutWithRAREiLayoutManager:self];
  }
  IOSIntArray *x, *y;
  if (layoutInfo_ != nil) {
    x = layoutInfo_->columnOrigins_;
    y = layoutInfo_->rowOrigins_;
  }
  else {
    [((RAREUIDimension *) nil_chk(size_)) setSizeWithInt:(int) width withInt:(int) height];
    (void) [container getInsetsWithRAREUIInsets:insets_];
    [((RAREJGFormLayout *) nil_chk(layout__)) initializeColAndRowComponentListsWithRAREiParentComponent:container];
    RAREJGFormLayout_LayoutInfo *layoutInfo = [layout__ computeLayoutWithRAREiParentComponent:container withRAREUIDimension:size_ withRAREUIInsets:insets_];
    x = ((RAREJGFormLayout_LayoutInfo *) nil_chk(layoutInfo))->columnOrigins_;
    y = layoutInfo->rowOrigins_;
  }
  [((RAREJGFormLayout *) nil_chk(layout__)) layoutComponentsWithRAREiParentComponent:container withIntArray:x withIntArray:y];
  [self adjustPaintersWithIntArray:x withIntArray:y];
  if (layoutTracker_ != nil) {
    [layoutTracker_ layoutPerformedWithRAREiLayoutManager:self];
  }
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintBackgroundWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  [self paintCellsWithRAREiPlatformGraphics:g withRAREUIRectangle:rect];
}

- (void)removeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c != nil) {
    [self removeChildWithRAREView:[c getView]];
    [((RAREJGFormLayout *) nil_chk(layout__)) removeLayoutComponentWithRAREiPlatformComponent:c];
  }
}

- (void)removeAll {
  [super removeChildren];
  [((RAREJGFormLayout *) nil_chk(layout__)) invalidateCaches];
}

- (void)revalidate {
  if (layout__ != nil) {
    [layout__ invalidateCaches];
    [super revalidate];
  }
}

- (void)setCellPaintersWithRAREiPlatformPainterArray:(IOSObjectArray *)painters {
  self->painters_ = painters;
  if (painters != nil) {
    [self setPaintHandlerEnabledWithBoolean:YES];
  }
}

- (void)setLayoutTrackerWithRAREiLayoutManager_iLayoutTracker:(id<RAREiLayoutManager_iLayoutTracker>)tracker {
  layoutTracker_ = tracker;
}

- (RAREJGCellConstraints *)getCellConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return (RAREJGCellConstraints *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]], [RAREJGCellConstraints class]);
}

- (id<RAREiPlatformComponent>)getComponentAtWithInt:(int)col
                                            withInt:(int)row {
  RAREJGCellConstraints *cc;
  RAREContainer *container = (RAREContainer *) check_class_cast(component_, [RAREContainer class]);
  int len = [((RAREContainer *) nil_chk(container)) getComponentCount];
  for (int i = 0; i < len; i++) {
    id<RAREiPlatformComponent> c = [container getComponentAtWithInt:i];
    cc = (RAREJGCellConstraints *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]], [RAREJGCellConstraints class]);
    if ((((RAREJGCellConstraints *) nil_chk(cc))->gridX_ == col) && (cc->gridY_ == row)) {
      return c;
    }
  }
  return nil;
}

- (id)getConstraintsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]];
}

- (RAREJGFormLayout *)getLayout {
  return layout__;
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size
                                withFloat:(float)maxWidth {
  (void) [((RAREJGFormLayout *) nil_chk([self getLayout])) getMinimumSizeWithRAREiParentComponent:(id<RAREiParentComponent>) check_protocol_cast([RAREComponent fromViewWithRAREView:self], @protocol(RAREiParentComponent)) withRAREUIDimension:size withFloat:maxWidth];
}

- (void)adjustPaintersWithIntArray:(IOSIntArray *)columnOrigins
                      withIntArray:(IOSIntArray *)rowOrigins {
  int len = (painters_ == nil) ? 0 : (int) [painters_ count];
  if (len == 0) {
    return;
  }
  int rlen = (int) [((IOSIntArray *) nil_chk(rowOrigins)) count];
  int clen = (int) [((IOSIntArray *) nil_chk(columnOrigins)) count];
  for (int i = 0; i < len; i++) {
    RAREUICellPainter *cp = (RAREUICellPainter *) check_class_cast(IOSObjectArray_Get(nil_chk(painters_), i), [RAREUICellPainter class]);
    int x1 = ((RAREUICellPainter *) nil_chk(cp))->column_;
    int y1 = cp->row_;
    int x2 = cp->column_ + cp->columnSpan_;
    int y2 = cp->row_ + cp->rowSpan_;
    if ((x1 < 0) || (y1 < 0) || (y2 < 0) || (x2 < 0)) {
      continue;
    }
    if (y2 >= rlen) {
      y2 = rlen - 1;
    }
    if ((x1 >= clen) || (y1 >= rlen)) {
      cp->width_ = 0;
      cp->height_ = 0;
      continue;
    }
    if (x2 >= clen) {
      x2 = clen - 1;
    }
    if ((x2 < clen) && (y2 < rlen)) {
      cp->x_ = IOSIntArray_Get(columnOrigins, x1);
      cp->y_ = IOSIntArray_Get(rowOrigins, y1);
      cp->width_ = IOSIntArray_Get(columnOrigins, x2) - cp->x_;
      cp->height_ = IOSIntArray_Get(rowOrigins, y2) - cp->y_;
    }
  }
}

- (void)disposeEx {
  if (layout__ != nil) {
    [layout__ invalidateCaches];
    layout__ = nil;
  }
  if (painters_ != nil) {
    [JavaUtilArrays fillWithNSObjectArray:painters_ withId:nil];
  }
  layoutTracker_ = nil;
  painters_ = nil;
  [super disposeEx];
}

- (void)paintCellsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withRAREUIRectangle:(RAREUIRectangle *)rect {
  if (painters_ != nil) {
    int len = (int) [painters_ count];
    for (int i = 0; i < len; i++) {
      [((id<RAREiPlatformPainter>) IOSObjectArray_Get(painters_, i)) paintWithRAREiPlatformGraphics:g withFloat:((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:rect->x_ withFloat:rect->width_ withFloat:rect->height_ withInt:RAREiPainter_UNKNOWN];
    }
  }
}

- (void)copyAllFieldsTo:(RAREFormsView *)other {
  [super copyAllFieldsTo:other];
  other->insets_ = insets_;
  other->layout__ = layout__;
  other->layoutInfo_ = layoutInfo_;
  other->layoutTracker_ = layoutTracker_;
  other->measureSize_ = measureSize_;
  other->painters_ = painters_;
  other->size_ = size_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createProxy", NULL, "LNSObject", 0x109, NULL },
    { "getCellConstraintsWithRAREiPlatformComponent:", NULL, "LRAREJGCellConstraints", 0x1, NULL },
    { "getComponentAtWithInt:withInt:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getConstraintsWithRAREiPlatformComponent:", NULL, "LNSObject", 0x1, NULL },
    { "getLayout", NULL, "LRAREJGFormLayout", 0x1, NULL },
    { "adjustPaintersWithIntArray:withIntArray:", NULL, "V", 0x4, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "paintCellsWithRAREiPlatformGraphics:withRAREUIRectangle:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "layoutInfo_", NULL, 0x0, "LRAREJGFormLayout_LayoutInfo" },
    { "painters_", NULL, 0x4, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RAREFormsView = { "FormsView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 8, methods, 2, fields, 0, NULL};
  return &_RAREFormsView;
}

@end
