//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aSharedLineBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/border/aSharedLineBorder.h"
#include "com/appnativa/rare/ui/border/aUILineBorder.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/util/SNumber.h"

@implementation RAREaSharedLineBorder

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  if (self = [super initWithRAREUIColor:color]) {
    tempRect1_ = [[RAREUIRectangle alloc] init];
    tempRect2_ = [[RAREUIRectangle alloc] init];
    tempPoint1_ = [[RAREUIPoint alloc] init];
    tempPoint2_ = [[RAREUIPoint alloc] init];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness {
  if (self = [super initWithRAREUIColor:color withFloat:thickness]) {
    tempRect1_ = [[RAREUIRectangle alloc] init];
    tempRect2_ = [[RAREUIRectangle alloc] init];
    tempPoint1_ = [[RAREUIPoint alloc] init];
    tempPoint2_ = [[RAREUIPoint alloc] init];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
              withBoolean:(BOOL)roundedCorners {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withBoolean:roundedCorners]) {
    tempRect1_ = [[RAREUIRectangle alloc] init];
    tempRect2_ = [[RAREUIRectangle alloc] init];
    tempPoint1_ = [[RAREUIPoint alloc] init];
    tempPoint2_ = [[RAREUIPoint alloc] init];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arc {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withFloat:arc]) {
    tempRect1_ = [[RAREUIRectangle alloc] init];
    tempRect2_ = [[RAREUIRectangle alloc] init];
    tempPoint1_ = [[RAREUIPoint alloc] init];
    tempPoint2_ = [[RAREUIPoint alloc] init];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                  withInt:(int)arcWidth
                  withInt:(int)arcHeight {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withFloat:arcWidth withFloat:arcHeight]) {
    tempRect1_ = [[RAREUIRectangle alloc] init];
    tempRect2_ = [[RAREUIRectangle alloc] init];
    tempPoint1_ = [[RAREUIPoint alloc] init];
    tempPoint2_ = [[RAREUIPoint alloc] init];
  }
  return self;
}

- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip {
  return [super createBorderPathWithRAREiPlatformPath:p withFloat:x withFloat:y withFloat:width withFloat:height withFloat:aw withFloat:ah withBoolean:clip];
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
  id<RAREiPlatformComponent> c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent];
  [self updateShapeWithRAREiPlatformComponent:c withFloat:width withFloat:height];
  if (!roundedCorners_) {
    if (clipInsets_ == nil) {
      clipInsets_ = [[RAREUIInsets alloc] init];
    }
    RAREUIInsets *in = [self getBorderInsetsWithRAREUIInsets:clipInsets_];
    if (noBottom_) {
      ((RAREUIInsets *) nil_chk(in))->bottom_ = 0;
    }
    if (noTop_) {
      ((RAREUIInsets *) nil_chk(in))->top_ = 0;
    }
    [g clipRectWithFloat:x + ((RAREUIInsets *) nil_chk(in))->left_ withFloat:y + in->top_ withFloat:width - in->right_ - in->left_ withFloat:height - in->bottom_ - in->top_];
    return;
  }
  if (clipShape_ != nil) {
    [clipShape_ reset];
  }
  clipShape_ = [self getPathWithRAREiPlatformPath:clipShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:YES];
  [g clipShapeWithRAREiPlatformShape:clipShape_];
}

- (BOOL)clipsContents {
  return YES;
}

- (id)clone {
  RAREaSharedLineBorder *b = (RAREaSharedLineBorder *) check_class_cast([super clone], [RAREaSharedLineBorder class]);
  ((RAREaSharedLineBorder *) nil_chk(b))->tempRect1_ = [[RAREUIRectangle alloc] init];
  b->tempRect2_ = [[RAREUIRectangle alloc] init];
  b->tempPoint1_ = [[RAREUIPoint alloc] init];
  b->tempPoint2_ = [[RAREUIPoint alloc] init];
  [b setSharersWithRAREiPlatformComponent:nil withRAREiPlatformComponent:nil];
  return b;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  if (end != [self isPaintLast]) {
    return;
  }
  id<RAREiPlatformComponent> c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent];
  if ([self updateShapeWithRAREiPlatformComponent:c withFloat:width withFloat:height]) {
    [super paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:end];
    if ((c != topComponent_) && (noBottom_ || noTop_ || noRight_ || noLeft_)) {
      RAREUIStroke *stroke = [g getStroke];
      RAREUIColor *oc = [g getColor];
      if (lineStroke_ != nil) {
        [g setStrokeWithRAREUIStroke:lineStroke_];
      }
      else {
        [g setStrokeWidthWithFloat:thickness_];
      }
      [self paintPartialLineWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
      [g setStrokeWithRAREUIStroke:stroke];
      [g setColorWithRAREUIColor:oc];
    }
  }
  else {
    [super paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:end];
  }
}

- (void)repaintTopComponent {
  if (topComponent_ != nil) {
    [topComponent_ repaint];
  }
}

- (BOOL)usesPath {
  return NO;
}

- (void)viewHiddenWithRAREChangeEvent:(RAREChangeEvent *)e {
  if ((topComponent_ != nil) && [topComponent_ isShowing]) {
    [topComponent_ repaint];
  }
}

- (void)viewResizedWithRAREChangeEvent:(RAREChangeEvent *)e {
  if (topComponent_ != nil) {
    [topComponent_ repaint];
  }
}

- (void)viewShownWithRAREChangeEvent:(RAREChangeEvent *)e {
  if (topComponent_ != nil) {
    [topComponent_ repaint];
  }
}

- (BOOL)wantsResizeEvent {
  return YES;
}

- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)background {
  backgroundColor_ = background;
}

- (void)setBottomComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (bottomComponent_ != nil) {
    [bottomComponent_ removeViewListenerWithRAREiViewListener:self];
  }
  if (c != nil) {
    [c removeViewListenerWithRAREiViewListener:self];
    [c addViewListenerWithRAREiViewListener:self];
  }
  bottomComponent_ = c;
}

- (void)setSharersWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)top
                  withRAREiPlatformComponent:(id<RAREiPlatformComponent>)bottom {
  [self setBottomComponentWithRAREiPlatformComponent:bottom];
  topComponent_ = top;
}

- (RAREUIColor *)getBackgroundColor {
  return backgroundColor_;
}

- (id<RAREiPlatformComponent>)getBottomComponent {
  return topComponent_;
}

- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height {
  id<RAREiPlatformComponent> c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent];
  [self updateShapeWithRAREiPlatformComponent:c withFloat:width withFloat:height];
  if (paintShape_ != nil) {
    [paintShape_ reset];
  }
  paintShape_ = [self getPathWithRAREiPlatformPath:paintShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:NO];
  return paintShape_;
}

- (id<RAREiPlatformComponent>)getTopComponent {
  return topComponent_;
}

- (float)getRoundedOffset {
  return (thickness_ < 3) ? (thickness_ < 2) ? .25f : 0.5f : 0.5f;
}

- (void)paintPartialLineWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                        withFloat:(float)x
                                        withFloat:(float)y
                                        withFloat:(float)width
                                        withFloat:(float)height {
  float y2 = -1;
  float height2 = -1;
  float x2 = -1;
  float width2 = -1;
  float ro = roundedCorners_ ? [self getRoundedOffset] : 0;
  int d = [RAREScreenUtils PLATFORM_PIXELS_1];
  RAREUIPoint *p1 = [((id<RAREiPlatformComponent>) nil_chk(topComponent_)) getLocationOnScreenWithRAREUIPoint:tempPoint1_];
  RAREUIPoint *p2 = [((id<RAREiPlatformComponent>) nil_chk(bottomComponent_)) getLocationOnScreenWithRAREUIPoint:tempPoint2_];
  float w = [topComponent_ getWidth];
  float h = [topComponent_ getHeight];
  if (insets_ == nil) {
    insets_ = [[RAREUIInsets alloc] init];
  }
  (void) [self calculateInsetsWithRAREUIInsets:insets_ withBoolean:padForArc_];
  float size = (((RAREUIInsets *) nil_chk(insets_))->top_ == 0) ? insets_->bottom_ : insets_->top_;
  [((id<RAREiPlatformGraphics>) nil_chk(g)) setColorWithRAREUIColor:(backgroundColor_ == nil) ? [bottomComponent_ getBackground] : backgroundColor_];
  RAREUIRectangle *r = tempRect1_;
  if (noTop_ || noBottom_) {
    if (noBottom_) {
      y += height - size;
    }
    height = size;
    ((RAREUIRectangle *) nil_chk(r))->x_ = x;
    r->y_ = y;
    r->height_ = height;
    r->width_ = w;
    float dx;
    if (((RAREUIPoint *) nil_chk(p1))->x_ - ((RAREUIPoint *) nil_chk(p2))->x_ > d) {
      dx = (p1->x_ - p2->x_) + ro;
      r->x_ += dx;
      r->width_ -= size + ro + ro + dx;
      [g fillRectWithFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
      if (p2->x_ + width > p1->x_ + w + d) {
        width2 = (p2->x_ + width) - (p1->x_ + w);
        x2 = x + width - width2 - size;
        width = p1->x_ - p2->x_ + size;
      }
      else {
        width -= w;
        x += size;
      }
    }
    else {
      dx = (p2->x_ - p1->x_) + size + ro;
      r->x_ += dx + size;
      r->width_ -= dx + size + size + ro;
      [g fillRectWithFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
      x = r->x_ + r->width_;
      width -= w - ro;
    }
  }
  else if (noLeft_ || noRight_) {
    if (noRight_) {
      x += width - size;
    }
    else {
      x -= ro;
    }
    width = size;
    [g fillRectWithFloat:x withFloat:y + (((RAREUIPoint *) nil_chk(p2))->y_ - ((RAREUIPoint *) nil_chk(p1))->y_) + size withFloat:width withFloat:h - size];
    if (p1->y_ - p2->y_ > 1) {
      if (p2->y_ + height > p1->y_ + h + d) {
        height2 = (p2->y_ + height) - (p1->y_ + h);
        y2 = y + height - height2 - size;
        height = p1->y_ - p2->y_ + size;
      }
      else {
        y += h;
        height -= h;
      }
    }
    else {
      y += h;
      height -= h;
    }
  }
  [g setColorWithRAREUIColor:[self getLineColor]];
  if (roundedCorners_) {
    x += [self getClipingOffset];
  }
  [g fillRectWithFloat:x withFloat:y withFloat:width withFloat:height];
  if (width2 > 0) {
    [g fillRectWithFloat:x2 withFloat:y withFloat:width2 + ro withFloat:height];
  }
  if (height2 > 0) {
    [g fillRectWithFloat:x withFloat:y2 withFloat:width withFloat:height2];
  }
  if (y2 > -1) {
    [g fillRectWithFloat:x withFloat:y2 withFloat:width withFloat:height2];
  }
}

- (RAREUIInsets *)getBorderInsetsExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withRAREUIInsets:(RAREUIInsets *)insets {
  float w = (c == nil) ? [RAREScreenUtils PLATFORM_PIXELS_1] : [c getWidth];
  float h = (c == nil) ? [RAREScreenUtils PLATFORM_PIXELS_1] : [c getHeight];
  [self updateShapeWithRAREiPlatformComponent:c withFloat:w withFloat:h];
  return [self getBorderInsetsExWithRAREUIInsets:insets];
}

- (BOOL)updateShapeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                    withFloat:(float)width
                                    withFloat:(float)height {
  noTop_ = NO;
  flatBottom_ = NO;
  flatTop_ = NO;
  noBottom_ = NO;
  noLeft_ = NO;
  noRight_ = NO;
  BOOL hasPartial = NO;
  do {
    if (((bottomComponent_ == nil) || (topComponent_ == nil))) {
      break;
    }
    RAREUIPoint *p1 = ((topComponent_ != nil) && [topComponent_ isShowing]) ? [topComponent_ getLocationOnScreenWithRAREUIPoint:tempPoint1_] : nil;
    RAREUIPoint *p2 = ((bottomComponent_ != nil) && [bottomComponent_ isShowing]) ? [bottomComponent_ getLocationOnScreenWithRAREUIPoint:tempPoint2_] : nil;
    if ((p1 == nil) || (p2 == nil)) {
      break;
    }
    if ([RAREUTSNumber isEqualWithFloat:p1->y_ withFloat:p2->y_]) {
      break;
    }
    RAREUIRectangle *rect1 = tempRect1_;
    RAREUIRectangle *rect2 = tempRect2_;
    BOOL partialOnBottom = NO;
    id<RAREiPlatformComponent> tc = topComponent_;
    id<RAREiPlatformComponent> bc = bottomComponent_;
    RAREUIPoint *tp = p1;
    RAREUIPoint *bp = p2;
    if (p1->y_ > p2->y_) {
      tc = bottomComponent_;
      bc = topComponent_;
      p1 = bp;
      p2 = tp;
      partialOnBottom = YES;
    }
    if (c == tc) {
      [((RAREUIRectangle *) nil_chk(rect1)) setSizeWithFloat:width withFloat:height];
      size_ = [((id<RAREiPlatformComponent>) nil_chk(bc)) getOrientedSizeWithRAREUIDimension:size_];
      ((RAREUIRectangle *) nil_chk(rect2))->width_ = ((RAREUIDimension *) nil_chk(size_))->width_;
      rect2->height_ = size_->height_;
    }
    else {
      size_ = [((id<RAREiPlatformComponent>) nil_chk(tc)) getOrientedSizeWithRAREUIDimension:size_];
      ((RAREUIRectangle *) nil_chk(rect1))->width_ = ((RAREUIDimension *) nil_chk(size_))->width_;
      rect1->height_ = size_->height_;
      [((RAREUIRectangle *) nil_chk(rect2)) setSizeWithFloat:width withFloat:height];
    }
    if ([((RAREUIRectangle *) nil_chk(rect1)) isEmpty] || [((RAREUIRectangle *) nil_chk(rect2)) isEmpty]) {
      break;
    }
    [rect1 setLocationWithRAREUIPoint:p1];
    [((RAREUIRectangle *) nil_chk(rect2)) setLocationWithRAREUIPoint:p2];
    int d = [RAREScreenUtils PLATFORM_PIXELS_1];
    ;
    BOOL top = c == topComponent_;
    BOOL leftOrRight = ((bp->x_ + rect2->width_ != tp->x_ + rect1->width_) && (bp->x_ != tp->x_));
    if (leftOrRight && (rect1->y_ + rect1->height_ - d <= rect2->y_)) {
      leftOrRight = NO;
    }
    if (!leftOrRight) {
      rect2->y_ += d;
      if ([rect1 intersectsWithRAREaUIRectangle:rect2]) {
        break;
      }
      rect2->y_ -= d;
    }
    hasPartial = YES;
    if (leftOrRight) {
      if (partialOnBottom) {
        if (tp->x_ - bp->x_ > 1) {
          noRight_ = !top;
          noLeft_ = top;
        }
        else {
          noRight_ = top;
          noLeft_ = !top;
        }
      }
    }
    else if (top) {
      noTop_ = partialOnBottom;
      flatBottom_ = !partialOnBottom;
      flatTop_ = partialOnBottom;
      noBottom_ = !partialOnBottom;
    }
    else {
      noTop_ = !partialOnBottom;
      noBottom_ = partialOnBottom;
      flatBottom_ = partialOnBottom;
      flatTop_ = !partialOnBottom;
    }
    modCount_++;
  }
  while (NO);
  return hasPartial;
}

- (void)copyAllFieldsTo:(RAREaSharedLineBorder *)other {
  [super copyAllFieldsTo:other];
  other->backgroundColor_ = backgroundColor_;
  other->bottomComponent_ = bottomComponent_;
  other->size_ = size_;
  other->tempPoint1_ = tempPoint1_;
  other->tempPoint2_ = tempPoint2_;
  other->tempRect1_ = tempRect1_;
  other->tempRect2_ = tempRect2_;
  other->topComponent_ = topComponent_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createBorderPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x4, NULL },
    { "clipsContents", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "usesPath", NULL, "Z", 0x1, NULL },
    { "wantsResizeEvent", NULL, "Z", 0x1, NULL },
    { "getBackgroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBottomComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getShapeWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "getTopComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getRoundedOffset", NULL, "F", 0x4, NULL },
    { "paintPartialLineWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "getBorderInsetsExWithRAREiPlatformComponent:withRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "updateShapeWithRAREiPlatformComponent:withFloat:withFloat:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "size_", NULL, 0x0, "LRAREUIDimension" },
    { "backgroundColor_", NULL, 0x4, "LRAREUIColor" },
    { "bottomComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "tempRect1_", NULL, 0x4, "LRAREUIRectangle" },
    { "tempRect2_", NULL, 0x4, "LRAREUIRectangle" },
    { "tempPoint1_", NULL, 0x4, "LRAREUIPoint" },
    { "tempPoint2_", NULL, 0x4, "LRAREUIPoint" },
    { "topComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
  };
  static J2ObjcClassInfo _RAREaSharedLineBorder = { "aSharedLineBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 13, methods, 8, fields, 0, NULL};
  return &_RAREaSharedLineBorder;
}

@end