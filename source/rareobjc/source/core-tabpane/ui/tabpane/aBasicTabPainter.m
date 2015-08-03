//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aBasicTabPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPath.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/tabpane/aBasicTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/aTabPainter.h"
#include "com/appnativa/rare/ui/tabpane/iTabLabel.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation RAREaBasicTabPainter

- (id)initWithInt:(int)cornerSize {
  if (self = [super init]) {
    selectedRect_ = [[RAREUIRectangle alloc] init];
    float dp2 = [RAREScreenUtils platformPixelsWithFloat:2];
    self->cornerSize_ = cornerSize;
    self->overlapOffset_ = -cornerSize;
    ((RAREUIInsets *) nil_chk(textInsets_))->left_ = cornerSize + dp2;
    textInsets_->top_ = dp2;
    textInsets_->bottom_ = dp2;
    textInsets_->right_ = dp2 * 2;
    padding_ = (int) [JavaLangMath ceilWithDouble:dp2 * 4];
  }
  return self;
}

- (void)dispose {
  [super dispose];
  if (paths_ != nil) {
    [paths_ clear];
    paths_ = nil;
  }
  selectedLabel_ = nil;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  selectedLabel_ = nil;
  [super paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y + padding_ withFloat:width withFloat:height - padding_ - [RAREScreenUtils PLATFORM_PIXELS_2]];
  [self paintLineWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  if (selectedLabel_ != nil) {
    [self paintSelectedTabWithRAREiPlatformGraphics:g withRAREiTabLabel:selectedLabel_ withFloat:((RAREUIRectangle *) nil_chk(selectedRect_))->x_ withFloat:selectedRect_->y_ withFloat:selectedRect_->width_ withFloat:selectedRect_->height_];
  }
}

- (void)setNormalPaintWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [super setNormalPaintWithRAREPaintBucket:pb];
  if (normalComponentPainter_ != nil) {
    [normalComponentPainter_ setBorderWithRAREiPlatformBorder:nil];
  }
}

- (void)setSelectedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb {
  [super setSelectedPaintWithRAREPaintBucket:pb];
  [((id<RAREiPlatformComponentPainter>) nil_chk(selectedComponentPainter_)) setBorderWithRAREiPlatformBorder:nil];
}

- (RAREaBasicTabPainter_TabShape *)createTabShape {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)layoutMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)button
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height
                                     withBoolean:(BOOL)vertical {
  RAREUIDimension *size = [[RAREUIDimension alloc] init];
  [((id<RAREiActionComponent>) nil_chk(button)) setIconPositionWithRARERenderableDataItem_IconPositionEnum:[RARERenderableDataItem_IconPositionEnum LEADING]];
  (void) [button getPreferredSizeWithRAREUIDimension:size];
  if (size->width_ > width) {
    size->width_ = width;
  }
  if (size->height_ > height) {
    size->height_ = height;
  }
  x = [JavaLangMath maxWithFloat:0 withFloat:x + width - size->width_ - [RAREScreenUtils platformPixelsWithFloat:8]];
  y += [JavaLangMath maxWithFloat:0 withFloat:(height - size->height_) / 2];
  [button setBoundsWithFloat:x withFloat:y withFloat:size->width_ withFloat:size->height_];
}

- (void)layoutTabWithRAREiTabLabel:(id<RAREiTabLabel>)label
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height
                           withInt:(int)index {
  if (index == -1) {
    return;
  }
  x += overlapOffset_ + [self getStartOffset];
  float pad = [self getTabsPadding] + [RAREScreenUtils PLATFORM_PIXELS_1];
  if ([self getPosition] == [RARELocationEnum BOTTOM]) {
    [((id<RAREiTabLabel>) nil_chk(label)) setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height - pad];
  }
  else {
    [((id<RAREiTabLabel>) nil_chk(label)) setBoundsWithFloat:x withFloat:y + pad withFloat:width withFloat:height - pad];
  }
  x -= overlapOffset_;
  if (paths_ == nil) {
    paths_ = [[JavaUtilArrayList alloc] initWithInt:[((id<JavaUtilList>) nil_chk(tabs_)) size]];
  }
  int plen = [((JavaUtilArrayList *) nil_chk(paths_)) size];
  int len = [((id<JavaUtilList>) nil_chk(tabs_)) size];
  if (len != plen) {
    if (len > plen) {
      while (plen < len) {
        [paths_ addWithId:[self createTabShape]];
        plen++;
      }
    }
    else {
      while (plen > len) {
        (void) [paths_ removeWithInt:--plen];
      }
    }
    [self updatePathRenderer];
  }
  RAREaBasicTabPainter_TabShape *shape = [paths_ getWithInt:index];
  width += overlapOffset_;
  if (index == selectedTab_) {
    [self updateSelectedShapeWithRAREiPlatformPath:((RAREaBasicTabPainter_TabShape *) nil_chk(shape))->pathShape_ withFloat:width withFloat:height - pad withFloat:cornerSize_];
    [self updateSelectedClipWithRAREiPlatformPath:shape->clipShape_ withFloat:width withFloat:height - pad withFloat:cornerSize_];
  }
  else {
    [self updateShapeWithRAREiPlatformPath:((RAREaBasicTabPainter_TabShape *) nil_chk(shape))->pathShape_ withFloat:width withFloat:height - pad withFloat:cornerSize_];
    [self updateClipWithRAREiPlatformPath:shape->clipShape_ withFloat:width withFloat:height - pad withFloat:cornerSize_];
  }
  [self updateShapeAfterLayoutWithRAREiTabLabel:label withRAREaBasicTabPainter_TabShape:shape withFloat:x withFloat:y withBoolean:index == selectedTab_];
}

- (void)paintLineWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                 withFloat:(float)x
                                 withFloat:(float)y
                                 withFloat:(float)width
                                 withFloat:(float)height {
  [((id<RAREiPlatformGraphics>) nil_chk(g)) setColorWithRAREUIColor:(selectedTabBorderColor_ == nil) ? tabBorderColor_ : selectedTabBorderColor_];
  float dp1 = [RAREScreenUtils PLATFORM_PIXELS_1];
  if ([self isHorizontal]) {
    [g drawLineWithFloat:x withFloat:y + height - dp1 withFloat:x + width withFloat:y + height - dp1];
  }
  else {
    [g drawLineWithFloat:x withFloat:y + width - dp1 withFloat:x + height withFloat:y + width - dp1];
  }
}

- (void)paintSelectedTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                        withFloat:(float)x
                                        withFloat:(float)y
                                        withFloat:(float)width
                                        withFloat:(float)height {
  RAREaBasicTabPainter_TabShape *shape = [self getTabShapeWithInt:selectedTab_];
  if (shape != nil) {
    width += overlapOffset_;
    id<RAREiPlatformPaint> p = [((id<RAREiPlatformComponentPainter>) nil_chk(selectedComponentPainter_)) getPaintWithFloat:width withFloat:height];
    if (p == nil) {
      [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
      [g translateWithFloat:x withFloat:y];
      [g clipShapeWithRAREiPlatformShape:shape->clipShape_];
      [selectedComponentPainter_ paintWithRAREiPlatformGraphics:g withFloat:0 withFloat:0 withFloat:width withFloat:height withInt:RAREiPainter_UNKNOWN];
      [g restoreState];
    }
    else {
      [((id<RAREiPlatformGraphics>) nil_chk(g)) setPaintWithRAREiPlatformPaint:p];
      [g fillShapeWithRAREiPlatformShape:shape->clipShape_ withFloat:x withFloat:y];
    }
    [((id<RAREiPlatformGraphics>) nil_chk(g)) setColorWithRAREUIColor:selectedTabBorderColor_];
    [g drawShapeWithRAREiPlatformShape:shape->pathShape_ withFloat:x withFloat:y];
  }
}

- (void)paintTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                withFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)width
                                withFloat:(float)height
                                  withInt:(int)index {
  if (index == selectedTab_) {
    selectedLabel_ = tab;
    [((RAREUIRectangle *) nil_chk(selectedRect_)) setWithFloat:x withFloat:y withFloat:width withFloat:height];
    return;
  }
  RAREaBasicTabPainter_TabShape *shape = [self getTabShapeWithInt:index];
  if (shape != nil) {
    width += overlapOffset_;
    id<RAREiPlatformComponentPainter> cp = [self getUnselectedPainterWithInt:index];
    id<RAREiPlatformPaint> p = nil;
    if ([((id<RAREiPlatformComponentPainter>) nil_chk(cp)) getBackgroundPainter] != nil) {
      RAREUIRectangle *r = [((id<RAREiPlatformPath>) nil_chk(shape->pathShape_)) getBounds];
      p = [cp getPaintWithFloat:((RAREUIRectangle *) nil_chk(r))->width_ withFloat:r->height_];
    }
    if (p == nil) {
      if ([cp isBackgroundPaintEnabled]) {
        RAREUIRectangle *r = [((id<RAREiPlatformPath>) nil_chk(shape->pathShape_)) getBounds];
        [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
        [g translateWithFloat:x withFloat:y];
        [g clipShapeWithRAREiPlatformShape:shape->clipShape_];
        [cp paintWithRAREiPlatformGraphics:g withFloat:0 withFloat:0 withFloat:((RAREUIRectangle *) nil_chk(r))->width_ withFloat:r->height_ withInt:RAREiPainter_UNKNOWN];
        [g restoreState];
      }
    }
    else {
      [((id<RAREiPlatformGraphics>) nil_chk(g)) setPaintWithRAREiPlatformPaint:p];
      [g fillShapeWithRAREiPlatformShape:shape->pathShape_ withFloat:x withFloat:y];
    }
    [((id<RAREiPlatformGraphics>) nil_chk(g)) setColorWithRAREUIColor:tabBorderColor_];
    [g drawShapeWithRAREiPlatformShape:shape->pathShape_ withFloat:x withFloat:y];
  }
}

- (void)paintTabsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                 withFloat:(float)x
                                 withFloat:(float)y
                                 withFloat:(float)width
                                 withFloat:(float)height
                                   withInt:(int)start
                                   withInt:(int)end
                               withBoolean:(BOOL)vertical {
  [((id<RAREiPlatformGraphics>) nil_chk(g)) setStrokeWidthWithFloat:[RAREScreenUtils platformPixelsfWithFloat:1.5f]];
  [super paintTabsWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withInt:start withInt:end withBoolean:vertical];
}

- (void)updateClipWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                              withFloat:(float)width
                              withFloat:(float)height
                              withFloat:(float)size {
  [self updateShapeWithRAREiPlatformPath:path withFloat:width withFloat:height withFloat:size];
}

- (void)updatePathRenderer {
}

- (void)updateSelectedClipWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                                      withFloat:(float)width
                                      withFloat:(float)height
                                      withFloat:(float)size {
  [self updateSelectedShapeWithRAREiPlatformPath:path withFloat:width withFloat:height + [RAREScreenUtils PLATFORM_PIXELS_1] withFloat:size];
}

- (void)updateSelectedShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                                       withFloat:(float)width
                                       withFloat:(float)height
                                       withFloat:(float)size {
  [self updateShapeWithRAREiPlatformPath:path withFloat:width withFloat:height withFloat:size];
}

- (void)updateShapeWithRAREiPlatformPath:(id<RAREiPlatformPath>)path
                               withFloat:(float)width
                               withFloat:(float)height
                               withFloat:(float)size {
  [((id<RAREiPlatformPath>) nil_chk(path)) reset];
  if (size == 0) {
    (void) [path moveToWithFloat:0 withFloat:height];
    (void) [path lineToWithFloat:0 withFloat:0];
    (void) [path lineToWithFloat:width withFloat:0];
    (void) [path lineToWithFloat:width withFloat:height];
    (void) [path moveToWithFloat:width withFloat:height];
    (void) [path moveToWithFloat:width withFloat:height];
  }
  else {
    (void) [path moveToWithFloat:0 withFloat:height];
    (void) [path lineToWithFloat:0 withFloat:size];
    (void) [path quadToWithFloat:0 withFloat:0 withFloat:size withFloat:0];
    (void) [path lineToWithFloat:width - size withFloat:0];
    (void) [path quadToWithFloat:width withFloat:0 withFloat:width withFloat:size];
    (void) [path lineToWithFloat:width withFloat:height];
    (void) [path moveToWithFloat:width withFloat:height];
  }
  [path close];
}

- (void)updateShapeAfterLayoutWithRAREiTabLabel:(id<RAREiTabLabel>)label
              withRAREaBasicTabPainter_TabShape:(RAREaBasicTabPainter_TabShape *)shape
                                      withFloat:(float)x
                                      withFloat:(float)y
                                    withBoolean:(BOOL)selected {
}

- (RAREaBasicTabPainter_TabShape *)getTabShapeWithInt:(int)index {
  if ((index < 0) || (index >= [((JavaUtilArrayList *) nil_chk(paths_)) size])) {
    return nil;
  }
  return [((JavaUtilArrayList *) nil_chk(paths_)) getWithInt:index];
}

- (BOOL)isInsideShapeWithFloat:(float)relX
                     withFloat:(float)relY
                     withFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                       withInt:(int)index {
  RAREaBasicTabPainter_TabShape *shape = [((JavaUtilArrayList *) nil_chk(paths_)) getWithInt:index];
  if (location_ == [RARELocationEnum BOTTOM]) {
    relY = [JavaLangMath maxWithFloat:relY - padding_ withFloat:0];
  }
  relX -= x;
  return [((id<RAREiPlatformPath>) nil_chk(((RAREaBasicTabPainter_TabShape *) nil_chk(shape))->pathShape_)) isPointInPathWithFloat:relX withFloat:relY];
}

- (void)copyAllFieldsTo:(RAREaBasicTabPainter *)other {
  [super copyAllFieldsTo:other];
  other->paths_ = paths_;
  other->selectedLabel_ = selectedLabel_;
  other->selectedRect_ = selectedRect_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createTabShape", NULL, "LRAREaBasicTabPainter_TabShape", 0x404, NULL },
    { "layoutMoreButtonWithRAREiActionComponent:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "V", 0x4, NULL },
    { "layoutTabWithRAREiTabLabel:withFloat:withFloat:withFloat:withFloat:withInt:", NULL, "V", 0x4, NULL },
    { "paintLineWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "paintSelectedTabWithRAREiPlatformGraphics:withRAREiTabLabel:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "paintTabWithRAREiPlatformGraphics:withRAREiTabLabel:withFloat:withFloat:withFloat:withFloat:withInt:", NULL, "V", 0x4, NULL },
    { "paintTabsWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:withInt:withInt:withBoolean:", NULL, "V", 0x4, NULL },
    { "updateClipWithRAREiPlatformPath:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "updatePathRenderer", NULL, "V", 0x4, NULL },
    { "updateSelectedClipWithRAREiPlatformPath:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "updateSelectedShapeWithRAREiPlatformPath:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "updateShapeWithRAREiPlatformPath:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "updateShapeAfterLayoutWithRAREiTabLabel:withRAREaBasicTabPainter_TabShape:withFloat:withFloat:withBoolean:", NULL, "V", 0x4, NULL },
    { "getTabShapeWithInt:", NULL, "LRAREaBasicTabPainter_TabShape", 0x4, NULL },
    { "isInsideShapeWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withInt:", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "selectedRect_", NULL, 0x4, "LRAREUIRectangle" },
    { "paths_", NULL, 0x4, "LJavaUtilArrayList" },
    { "selectedLabel_", NULL, 0x4, "LRAREiTabLabel" },
  };
  static J2ObjcClassInfo _RAREaBasicTabPainter = { "aBasicTabPainter", "com.appnativa.rare.ui.tabpane", NULL, 0x401, 15, methods, 3, fields, 0, NULL};
  return &_RAREaBasicTabPainter;
}

@end
@implementation RAREaBasicTabPainter_TabShape

- (id)initWithRAREiPlatformPath:(id<RAREiPlatformPath>)pathShape
          withRAREiPlatformPath:(id<RAREiPlatformPath>)clipShape {
  if (self = [super init]) {
    self->pathShape_ = pathShape;
    self->clipShape_ = clipShape;
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREaBasicTabPainter_TabShape *)other {
  [super copyAllFieldsTo:other];
  other->clipShape_ = clipShape_;
  other->pathShape_ = pathShape_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "clipShape_", NULL, 0x1, "LRAREiPlatformPath" },
    { "pathShape_", NULL, 0x1, "LRAREiPlatformPath" },
  };
  static J2ObjcClassInfo _RAREaBasicTabPainter_TabShape = { "TabShape", "com.appnativa.rare.ui.tabpane", "aBasicTabPainter", 0xc, 0, NULL, 2, fields, 0, NULL};
  return &_RAREaBasicTabPainter_TabShape;
}

@end