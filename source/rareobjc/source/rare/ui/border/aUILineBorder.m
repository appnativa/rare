//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUILineBorder.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/BorderUtils.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/border/aUILineBorder.h"
#include "com/appnativa/rare/ui/iPath.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPainter.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Math.h"

@implementation RAREaUILineBorder

static RAREUILineBorder * RAREaUILineBorder_sharedBlackLineBorder_;
static RAREUILineBorder * RAREaUILineBorder_sharedLineBorder_;
static float RAREaUILineBorder_onePixelThickness_;

+ (RAREUILineBorder *)sharedBlackLineBorder {
  return RAREaUILineBorder_sharedBlackLineBorder_;
}

+ (void)setSharedBlackLineBorder:(RAREUILineBorder *)sharedBlackLineBorder {
  RAREaUILineBorder_sharedBlackLineBorder_ = sharedBlackLineBorder;
}

+ (RAREUILineBorder *)sharedLineBorder {
  return RAREaUILineBorder_sharedLineBorder_;
}

+ (void)setSharedLineBorder:(RAREUILineBorder *)sharedLineBorder {
  RAREaUILineBorder_sharedLineBorder_ = sharedLineBorder;
}

+ (float)onePixelThickness {
  return RAREaUILineBorder_onePixelThickness_;
}

+ (float *)onePixelThicknessRef {
  return &RAREaUILineBorder_onePixelThickness_;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  return [self initRAREaUILineBorderWithRAREUIColor:color withFloat:RAREaUILineBorder_onePixelThickness_ withBoolean:NO];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness {
  return [self initRAREaUILineBorderWithRAREUIColor:color withFloat:thickness withBoolean:NO];
}

- (id)initRAREaUILineBorderWithRAREUIColor:(RAREUIColor *)color
                                 withFloat:(float)thickness
                               withBoolean:(BOOL)roundedCorners {
  if (self = [super init]) {
    lineStyle_ = @"solid";
    lineColor_ = color;
    (void) [self setThicknessExWithFloat:thickness];
    self->roundedCorners_ = roundedCorners;
    if (roundedCorners) {
      arcHeight_ = arcWidth_ = thickness * 6;
    }
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
              withBoolean:(BOOL)roundedCorners {
  return [self initRAREaUILineBorderWithRAREUIColor:color withFloat:thickness withBoolean:roundedCorners];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc {
  if (self = [self initRAREaUILineBorderWithRAREUIColor:color withFloat:thickness withBoolean:arc > 0]) {
    if (arc > 0) {
      arcHeight_ = arcWidth_ = arc;
    }
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arcWidth
                withFloat:(float)arcHeight {
  if (self = [self initRAREaUILineBorderWithRAREUIColor:color withFloat:thickness withBoolean:(arcWidth > 0) || (arcHeight > 0)]) {
    if (arcWidth > 0) {
      self->arcWidth_ = arcWidth;
    }
    if (arcHeight > 0) {
      self->arcHeight_ = arcHeight;
    }
  }
  return self;
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
  if (!roundedCorners_) {
    [super clipWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
    return;
  }
  if (clipShape_ != nil) {
    [clipShape_ reset];
  }
  clipShape_ = [self getPathWithRAREiPlatformPath:clipShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:YES];
  [((id<RAREiPlatformGraphics>) nil_chk(g)) clipShapeWithRAREiPlatformShape:clipShape_];
}

- (BOOL)clipsContents {
  return ![self isRectangular];
}

- (id)clone {
  RAREaUILineBorder *b = (RAREaUILineBorder *) check_class_cast([super clone], [RAREaUILineBorder class]);
  if (padding_ != nil) {
    ((RAREaUILineBorder *) nil_chk(b))->padding_ = [[RAREUIInsets alloc] initWithRAREUIInsets:padding_];
  }
  ((RAREaUILineBorder *) nil_chk(b))->clipShape_ = nil;
  b->paintShape_ = nil;
  if (fixedBorderInsets_ != nil) {
    b->fixedBorderInsets_ = [[RAREUIInsets alloc] initWithRAREUIInsets:fixedBorderInsets_];
  }
  return b;
}

- (RAREaUILineBorder *)copy__ {
  return (RAREaUILineBorder *) check_class_cast([self clone], [RAREaUILineBorder class]);
}

+ (RAREaUILineBorder *)createBlackLineBorder {
  if (RAREaUILineBorder_sharedBlackLineBorder_ == nil) {
    RAREaUILineBorder_sharedBlackLineBorder_ = [[RAREUILineBorder alloc] initWithRAREUIColor:[RAREUIColor BLACK]];
  }
  return RAREaUILineBorder_sharedBlackLineBorder_;
}

+ (RAREaUILineBorder *)createBorderWithRAREiWidget:(id<RAREiWidget>)context
                                   withRAREUIColor:(RAREUIColor *)color
                                         withFloat:(float)thickness
                                         withFloat:(float)arcWidth
                                         withFloat:(float)arcHeight {
  if (color == nil) {
    color = [RAREaUILineBorder getDefaultLineColor];
  }
  if (RAREaUILineBorder_sharedLineBorder_ == nil) {
    RAREaUILineBorder_sharedLineBorder_ = [[RAREUILineBorder alloc] initWithRAREUIColor:[RAREaUILineBorder getDefaultLineColor] withFloat:RAREaUILineBorder_onePixelThickness_ withFloat:0 withFloat:0];
  }
  if ([((RAREUIColor *) nil_chk(color)) isEqual:[((RAREUILineBorder *) nil_chk(RAREaUILineBorder_sharedLineBorder_)) getLineColor]]) {
    if ((thickness == RAREaUILineBorder_onePixelThickness_) && (arcWidth == 0) && (arcHeight == 0)) {
      return RAREaUILineBorder_sharedLineBorder_;
    }
  }
  if ([color isEqual:[RAREUIColor BLACK]]) {
    if ((thickness == RAREaUILineBorder_onePixelThickness_) && (arcWidth == 0) && (arcHeight == 0)) {
      return [RAREaUILineBorder createBlackLineBorder];
    }
  }
  return [[RAREUILineBorder alloc] initWithRAREUIColor:color withFloat:thickness withFloat:arcWidth withFloat:arcHeight];
}

+ (RAREUILineBorder *)createFocusedBorderWithFloat:(float)arcw
                                         withFloat:(float)arch {
  RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Button.focus"];
  if (c == nil) {
    c = [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:0 withInt:128];
  }
  RAREUILineBorder *b = [[RAREUILineBorder alloc] initWithRAREUIColor:c withFloat:RAREaUILineBorder_onePixelThickness_ withFloat:arcw withFloat:arch];
  (void) [b setLineStyleWithNSString:@"dotted"];
  return b;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last {
  if (last != [self isPaintLast]) {
    return;
  }
  RAREUIStroke *stroke = [((id<RAREiPlatformGraphics>) nil_chk(g)) getStroke];
  RAREUIColor *c = [g getColor];
  RAREUIColor *color = [self getColor];
  [g setColorWithRAREUIColor:color];
  if (lineStroke_ != nil) {
    [g setStrokeWithRAREUIStroke:lineStroke_];
  }
  else {
    [g setStrokeWidthWithFloat:thickness_];
  }
  if (roundedCorners_) {
    if (paintShape_ != nil) {
      [paintShape_ reset];
    }
    paintShape_ = [self getPathWithRAREiPlatformPath:paintShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:NO];
    [g drawShapeWithRAREiPlatformShape:paintShape_ withFloat:0 withFloat:0];
  }
  else {
    if (insets_ == nil) {
      insets_ = [[RAREUIInsets alloc] init];
    }
    float left = x;
    float top = y;
    float right = x + width;
    float bottom = y + height;
    RAREUIInsets *in = [self calculateInsetsWithRAREUIInsets:insets_ withBoolean:padForArc_];
    [RAREaUILineBorder paintLinesWithRAREiPlatformGraphics:g withRAREUIInsets:in withFloat:left withFloat:top withFloat:right withFloat:bottom];
  }
  [g setStrokeWithRAREUIStroke:stroke];
  [g setColorWithRAREUIColor:c];
}

+ (void)paintLinesWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                           withRAREUIInsets:(RAREUIInsets *)inArg
                                  withFloat:(float)left
                                  withFloat:(float)top
                                  withFloat:(float)right
                                  withFloat:(float)bottom {
  RAREUIColor *c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getColor];
  if ([c isKindOfClass:[RAREUIColorShade class]]) {
    id<RAREiPainter> p = [((RAREUIColorShade *) check_class_cast(c, [RAREUIColorShade class])) getBackgroundPainter];
    if (p != nil) {
      [RAREaUILineBorder paintLinesWithRAREiPlatformGraphics:g withRAREiPainter:p withRAREUIInsets:inArg withFloat:left withFloat:top withFloat:right withFloat:bottom];
      return;
    }
  }
  if (((RAREUIInsets *) nil_chk(inArg))->top_ > 0) {
    [g fillRectWithFloat:left withFloat:top withFloat:right - left withFloat:inArg->top_];
  }
  if (inArg->left_ > 0) {
    [g fillRectWithFloat:left withFloat:top withFloat:inArg->left_ withFloat:bottom - top];
  }
  if (inArg->bottom_ > 0) {
    [g fillRectWithFloat:left withFloat:bottom - inArg->bottom_ withFloat:right - left withFloat:inArg->bottom_];
  }
  if (inArg->right_ > 0) {
    [g fillRectWithFloat:right - inArg->right_ withFloat:top withFloat:inArg->right_ withFloat:bottom - top];
  }
}

+ (void)paintLinesWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                           withRAREiPainter:(id<RAREiPainter>)p
                           withRAREUIInsets:(RAREUIInsets *)inArg
                                  withFloat:(float)left
                                  withFloat:(float)top
                                  withFloat:(float)right
                                  withFloat:(float)bottom {
  if (((RAREUIInsets *) nil_chk(inArg))->top_ > 0) {
    [((id<RAREiPainter>) nil_chk(p)) paintWithRAREiPlatformGraphics:g withFloat:left withFloat:top withFloat:right - left withFloat:inArg->top_ withInt:RAREiPainter_HORIZONTAL];
  }
  if (inArg->left_ > 0) {
    [((id<RAREiPainter>) nil_chk(p)) paintWithRAREiPlatformGraphics:g withFloat:left withFloat:top withFloat:inArg->left_ withFloat:bottom - top withInt:RAREiPainter_VERTICAL];
  }
  if (inArg->bottom_ > 0) {
    [((id<RAREiPainter>) nil_chk(p)) paintWithRAREiPlatformGraphics:g withFloat:left withFloat:bottom - inArg->bottom_ withFloat:right - left withFloat:inArg->bottom_ withInt:RAREiPainter_HORIZONTAL];
  }
  if (inArg->right_ > 0) {
    [((id<RAREiPainter>) nil_chk(p)) paintWithRAREiPlatformGraphics:g withFloat:right - inArg->right_ withFloat:top withFloat:inArg->right_ withFloat:bottom - top withInt:RAREiPainter_VERTICAL];
  }
}

- (BOOL)usesPath {
  return YES;
}

- (BOOL)isMissingASide {
  return noBottom_ || noTop_ || noRight_ || noLeft_;
}

- (void)setCornerArcWithFloat:(float)arc {
  if (arc > -1) {
    arcWidth_ = arc;
    arcHeight_ = arc;
    roundedCorners_ = arc > 0;
    modCount_++;
  }
}

- (RAREaUILineBorder *)setFlatBottomWithBoolean:(BOOL)flatBottom {
  if (self->flatBottom_ == flatBottom) {
    return self;
  }
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setFlatBottomWithBoolean:flatBottom];
  }
  self->flatBottom_ = flatBottom;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setFlatLeftWithBoolean:(BOOL)flatLeft {
  if (self->flatLeft_ == flatLeft) {
    return self;
  }
  if (flatLeft && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setFlatLeftWithBoolean:flatLeft];
  }
  self->flatLeft_ = flatLeft;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setFlatRightWithBoolean:(BOOL)flatRight {
  if (self->flatRight_ == flatRight) {
    return self;
  }
  if (flatRight && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setFlatRightWithBoolean:flatRight];
  }
  self->flatRight_ = flatRight;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setFlatTopWithBoolean:(BOOL)flatTop {
  if (self->flatTop_ == flatTop) {
    return self;
  }
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setFlatTopWithBoolean:flatTop];
  }
  self->flatTop_ = flatTop;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setHilightColorWithRAREUIColor:(RAREUIColor *)hilightColor {
  if ((self->hilightColor_ != nil) && [self->hilightColor_ isEqual:hilightColor]) {
    return self;
  }
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setHilightColorWithRAREUIColor:hilightColor];
  }
  self->hilightColor_ = hilightColor;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setInsetsWithRAREUIInsets:insets];
  }
  if (insets != nil) {
    self->fixedBorderInsets_ = (RAREUIInsets *) check_class_cast([insets clone], [RAREUIInsets class]);
  }
  else {
    self->fixedBorderInsets_ = nil;
  }
  modCount_++;
  return self;
}

- (void)setInsetsPaddingWithFloat:(float)top
                        withFloat:(float)right
                        withFloat:(float)bottom
                        withFloat:(float)left {
  if (padding_ == nil) {
    padding_ = [[RAREUIInsets alloc] init];
  }
  (void) [((RAREUIInsets *) nil_chk(padding_)) setWithDouble:top withDouble:right withDouble:bottom withDouble:left];
}

- (RAREaUILineBorder *)setLineColorWithRAREUIColor:(RAREUIColor *)c {
  if ([((RAREUIColor *) nil_chk(self->lineColor_)) isEqual:c]) {
    return self;
  }
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setLineColorWithRAREUIColor:c];
  }
  return [self setLineColorExWithRAREUIColor:c];
}

- (RAREaUILineBorder *)setLineStyleWithNSString:(NSString *)style {
  if ((style == nil) || [style isEqual:lineStyle_]) {
    return self;
  }
  if (((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_)) && ![@"solid" equalsIgnoreCase:style]) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setLineStyleWithNSString:style];
  }
  return [self setLineStyleExWithNSString:style];
}

- (RAREaUILineBorder *)setNoBottomWithBoolean:(BOOL)noBottom {
  if (self->noBottom_ == noBottom) {
    return self;
  }
  if (noBottom && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setNoBottomWithBoolean:noBottom];
  }
  self->flatBottom_ = noBottom;
  self->noBottom_ = noBottom;
  modCount_++;
  [self checkMissingSides];
  return self;
}

- (RAREaUILineBorder *)setNoLeftWithBoolean:(BOOL)noLeft {
  if (self->noLeft_ == noLeft) {
    return self;
  }
  if (noLeft && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setNoLeftWithBoolean:noLeft];
  }
  self->noLeft_ = noLeft;
  self->flatLeft_ = noLeft;
  modCount_++;
  [self checkMissingSides];
  return self;
}

- (RAREaUILineBorder *)setNoRightWithBoolean:(BOOL)noRight {
  if (self->noRight_ == noRight) {
    return self;
  }
  if (noRight && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setNoRightWithBoolean:noRight];
  }
  self->noRight_ = noRight;
  self->flatRight_ = noRight;
  modCount_++;
  [self checkMissingSides];
  return self;
}

- (RAREaUILineBorder *)setNoTopWithBoolean:(BOOL)noTop {
  if (self->noTop_ == noTop) {
    return self;
  }
  if (noTop && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setNoTopWithBoolean:noTop];
  }
  self->flatTop_ = noTop;
  self->noTop_ = noTop;
  modCount_++;
  [self checkMissingSides];
  return self;
}

- (void)checkMissingSides {
  int n = 0;
  if (noBottom_) {
    n++;
  }
  if (noTop_) {
    n++;
  }
  if (noRight_) {
    n++;
  }
  if (noLeft_) {
    n++;
  }
  has2MissingSides_ = n > 1;
}

- (RAREaUILineBorder *)setThicknessWithFloat:(float)thickness {
  if (thickness == self->thickness_) {
    return self;
  }
  if (noTop_ && ((self == RAREaUILineBorder_sharedLineBorder_) || (self == RAREaUILineBorder_sharedBlackLineBorder_))) {
    return [((RAREaUILineBorder *) nil_chk([self copy__])) setThicknessWithFloat:thickness];
  }
  return [self setThicknessExWithFloat:thickness];
}

- (float)getArcHeight {
  return arcHeight_;
}

- (float)getArcWidth {
  return arcWidth_;
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] init];
  }
  if (fixedBorderInsets_ != nil) {
    (void) [((RAREUIInsets *) nil_chk(insets)) setWithRAREUIInsets:fixedBorderInsets_];
    return insets;
  }
  (void) [self calculateInsetsWithRAREUIInsets:insets withBoolean:padForArc_];
  if (padding_ != nil) {
    (void) [((RAREUIInsets *) nil_chk(insets)) addInsetsWithRAREUIInsets:padding_];
  }
  return insets;
}

- (RAREUIInsets *)getBorderInsetsExWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] init];
  }
  (void) [self calculateInsetsWithRAREUIInsets:insets withBoolean:NO];
  return insets;
}

+ (RAREUIColor *)getDefaultDisabledColor {
  RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.LineBorder.disabledColor"];
  if (c == nil) {
    c = [RAREaUILineBorder getDefaultLineColor];
  }
  return c;
}

+ (RAREUIColor *)getDefaultLineColor {
  RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.LineBorder.color"];
  if (c == nil) {
    c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.backgroundShadow"];
  }
  if (c == nil) {
    c = [RAREUIColor GRAY];
  }
  return c;
}

- (RAREUIColor *)getHilightColor {
  return hilightColor_;
}

- (RAREUIColor *)getLineColor {
  return (lineColor_ == nil) ? [RAREaUILineBorder getDefaultLineColor] : lineColor_;
}

- (NSString *)getLineStyle {
  return lineStyle_;
}

- (float)getClipingOffset {
  return (thickness_ < 3) ? (thickness_ < 2) ? .25f : .5f : (thickness_ / 4.0f);
}

- (id<RAREiPlatformPath>)getPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                            withFloat:(float)x
                                            withFloat:(float)y
                                            withFloat:(float)width
                                            withFloat:(float)height
                                          withBoolean:(BOOL)forClip {
  float left = x;
  float top = y;
  float right = x + width;
  float bottom = y + height;
  float dx = (thickness_ < 3) ? (thickness_ < 2) ? .625f : RAREaUILineBorder_onePixelThickness_ : (thickness_ / 2.0f);
  float aw = arcWidth_;
  float ah = arcHeight_;
  float a = 0;
  if (roundedCorners_ && forClip) {
    a = [self getClipingOffset];
  }
  left += dx;
  top += dx;
  right -= dx;
  bottom -= dx;
  ah += a;
  aw += a;
  right -= a;
  bottom -= a;
  if (noRight_) {
    right = x + width;
  }
  else if (noLeft_) {
    left = x;
  }
  if (noTop_) {
    top = y;
  }
  else if (noBottom_) {
    bottom = y + height;
  }
  return [self createBorderPathWithRAREiPlatformPath:p withFloat:left withFloat:top withFloat:right - left withFloat:bottom - top withFloat:aw withFloat:ah withBoolean:forClip];
}

- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip {
  if (has2MissingSides_) {
    return [self createBorderPathWith2SidesMissingWithRAREiPlatformPath:p withFloat:x withFloat:y withFloat:width withFloat:height withFloat:aw withFloat:ah withBoolean:clip];
  }
  else {
    if (clip) {
      p = [RAREBorderUtils createLineBorderPathWithRAREiPlatformPath:p withFloat:width withFloat:height withFloat:aw withFloat:ah withBoolean:flatBottom_ withBoolean:flatTop_ withBoolean:flatLeft_ withBoolean:flatRight_ withBoolean:NO withBoolean:NO withBoolean:NO withBoolean:NO];
    }
    else {
      p = [RAREBorderUtils createLineBorderPathWithRAREiPlatformPath:p withFloat:width withFloat:height withFloat:aw withFloat:ah withBoolean:flatBottom_ withBoolean:flatTop_ withBoolean:flatLeft_ withBoolean:flatRight_ withBoolean:noTop_ withBoolean:noBottom_ withBoolean:noLeft_ withBoolean:noRight_];
    }
    [((id<RAREiPlatformPath>) nil_chk(p)) translateWithFloat:x withFloat:y];
    return p;
  }
}

- (id<RAREiPlatformPath>)createBorderPathWith2SidesMissingWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                                      withFloat:(float)x
                                                                      withFloat:(float)y
                                                                      withFloat:(float)width
                                                                      withFloat:(float)height
                                                                      withFloat:(float)aw
                                                                      withFloat:(float)ah
                                                                    withBoolean:(BOOL)clip {
  if (p == nil) {
    p = [RAREBorderUtils createPath];
  }
  else {
    [p reset];
  }
  if (noTop_) {
    if (noRight_) {
      if (clip) {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:width withFloat:height];
        (void) [p lineToWithFloat:aw withFloat:height];
        (void) [p quadToWithFloat:0 withFloat:height withFloat:0 withFloat:height - ah];
        (void) [p lineToWithFloat:0 withFloat:0];
        (void) [p lineToWithFloat:width withFloat:0];
        (void) [p lineToWithFloat:width withFloat:height];
      }
      else {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:width withFloat:height];
        (void) [p lineToWithFloat:aw withFloat:height];
        (void) [p quadToWithFloat:0 withFloat:height withFloat:0 withFloat:height - ah];
        (void) [p lineToWithFloat:0 withFloat:0];
        (void) [p moveToWithFloat:0 withFloat:0];
      }
    }
    else {
      if (clip) {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:0 withFloat:0];
        (void) [p lineToWithFloat:width withFloat:0];
        (void) [p lineToWithFloat:width withFloat:height - ah];
        (void) [p quadToWithFloat:width withFloat:height withFloat:width - aw withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:0];
      }
      else {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:width withFloat:0];
        (void) [p lineToWithFloat:width withFloat:height - ah];
        (void) [p quadToWithFloat:width withFloat:height withFloat:width - aw withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:height];
        (void) [p moveToWithFloat:0 withFloat:height];
      }
    }
  }
  else {
    if (noRight_) {
      if (clip) {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:0 withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:ah];
        (void) [p quadToWithFloat:0 withFloat:0 withFloat:aw withFloat:0];
        (void) [p lineToWithFloat:width withFloat:0];
        (void) [p lineToWithFloat:width withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:height];
      }
      else {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:0 withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:ah];
        (void) [p quadToWithFloat:0 withFloat:0 withFloat:aw withFloat:0];
        (void) [p lineToWithFloat:width withFloat:0];
        (void) [p moveToWithFloat:width withFloat:0];
      }
    }
    else {
      if (clip) {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:0 withFloat:0];
        (void) [p lineToWithFloat:width - aw withFloat:0];
        (void) [p quadToWithFloat:width withFloat:0 withFloat:width withFloat:ah];
        (void) [p lineToWithFloat:width withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:height];
        (void) [p lineToWithFloat:0 withFloat:0];
      }
      else {
        (void) [((id<RAREiPlatformPath>) nil_chk(p)) moveToWithFloat:0 withFloat:0];
        (void) [p lineToWithFloat:width - aw withFloat:0];
        (void) [p quadToWithFloat:width withFloat:0 withFloat:width withFloat:ah];
        (void) [p lineToWithFloat:width withFloat:height];
        (void) [p moveToWithFloat:width withFloat:height];
      }
    }
  }
  [((id<RAREiPlatformPath>) nil_chk(p)) translateWithFloat:x withFloat:y];
  return p;
}

- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height {
  if (paintShape_ != nil) {
    [paintShape_ reset];
  }
  paintShape_ = [self getPathWithRAREiPlatformPath:paintShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:YES];
  return paintShape_;
}

- (float)getThickness {
  return thickness_;
}

- (BOOL)isPadForArc {
  return padForArc_;
}

- (BOOL)isPaintLast {
  return YES;
}

- (BOOL)isRectangular {
  return !roundedCorners_;
}

- (RAREUIInsets *)calculateInsetsWithRAREUIInsets:(RAREUIInsets *)insets
                                      withBoolean:(BOOL)pad {
  int tw = (int) [JavaLangMath floorWithDouble:thickness_];
  int th = tw;
  int tb = tw;
  if (pad) {
    tw += (arcWidth_ / 3);
    th += (arcHeight_ / 3);
    if (!flatBottom_) {
      tb += (arcWidth_ / 3);
    }
  }
  if (flatTop_) {
    th = noTop_ ? 0 : th;
  }
  if (flatBottom_) {
    tb = noBottom_ ? 0 : tb;
  }
  if (insets != nil) {
    insets->left_ = noLeft_ ? 0 : tw;
    insets->right_ = noRight_ ? 0 : tw;
    insets->top_ = noTop_ ? 0 : th;
    insets->bottom_ = noBottom_ ? 0 : tb;
  }
  return insets;
}

- (RAREaUILineBorder *)setLineColorExWithRAREUIColor:(RAREUIColor *)c {
  self->lineColor_ = c;
  modCount_++;
  return self;
}

- (RAREaUILineBorder *)setLineStyleExWithNSString:(NSString *)style {
  lineStyle_ = style;
  modCount_++;
  lineStroke_ = [RAREUIStroke getStrokeWithNSString:style];
  [self adjustStrokeForThickness];
  return self;
}

- (RAREaUILineBorder *)setThicknessExWithFloat:(float)thickness {
  modCount_++;
  self->thickness_ = thickness;
  [self adjustStrokeForThickness];
  return self;
}

- (RAREUIColor *)getColor {
  return (lineColor_ == nil) ? [RAREaUILineBorder getDefaultLineColor] : lineColor_;
}

- (void)adjustStrokeForThickness {
  if (lineStroke_ != nil) {
    if ((lineStroke_ == [RAREUIStroke SOLID_STROKE]) || (lineStroke_ == [RAREUIStroke DASHED_STROKE]) || (lineStroke_ == [RAREUIStroke DOTTED_STROKE])) {
      lineStroke_ = (RAREUIStroke *) check_class_cast([lineStroke_ clone], [RAREUIStroke class]);
    }
    [((RAREUIStroke *) nil_chk(lineStroke_)) setWidthWithFloat:thickness_];
  }
}

+ (void)initialize {
  if (self == [RAREaUILineBorder class]) {
    RAREaUILineBorder_onePixelThickness_ = [RAREScreenUtils platformPixelsfWithFloat:1.25f];
  }
}

- (void)copyAllFieldsTo:(RAREaUILineBorder *)other {
  [super copyAllFieldsTo:other];
  other->arcHeight_ = arcHeight_;
  other->arcWidth_ = arcWidth_;
  other->clipShape_ = clipShape_;
  other->fixedBorderInsets_ = fixedBorderInsets_;
  other->flatBottom_ = flatBottom_;
  other->flatLeft_ = flatLeft_;
  other->flatRight_ = flatRight_;
  other->flatTop_ = flatTop_;
  other->has2MissingSides_ = has2MissingSides_;
  other->hilightColor_ = hilightColor_;
  other->insets_ = insets_;
  other->lineColor_ = lineColor_;
  other->lineStroke_ = lineStroke_;
  other->lineStyle_ = lineStyle_;
  other->noBottom_ = noBottom_;
  other->noLeft_ = noLeft_;
  other->noRight_ = noRight_;
  other->noTop_ = noTop_;
  other->padding_ = padding_;
  other->paintShape_ = paintShape_;
  other->roundedCorners_ = roundedCorners_;
  other->thickness_ = thickness_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clipsContents", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "copy__", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "createBlackLineBorder", NULL, "LRAREaUILineBorder", 0x9, NULL },
    { "createBorderWithRAREiWidget:withRAREUIColor:withFloat:withFloat:withFloat:", NULL, "LRAREaUILineBorder", 0x9, NULL },
    { "createFocusedBorderWithFloat:withFloat:", NULL, "LRAREUILineBorder", 0x9, NULL },
    { "usesPath", NULL, "Z", 0x1, NULL },
    { "isMissingASide", NULL, "Z", 0x1, NULL },
    { "setFlatBottomWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setFlatLeftWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setFlatRightWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setFlatTopWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setHilightColorWithRAREUIColor:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setInsetsWithRAREUIInsets:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setLineColorWithRAREUIColor:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setLineStyleWithNSString:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setNoBottomWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setNoLeftWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setNoRightWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "setNoTopWithBoolean:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "checkMissingSides", NULL, "V", 0x2, NULL },
    { "setThicknessWithFloat:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getBorderInsetsExWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getDefaultDisabledColor", NULL, "LRAREUIColor", 0x9, NULL },
    { "getDefaultLineColor", NULL, "LRAREUIColor", 0x9, NULL },
    { "getHilightColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getLineColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getLineStyle", NULL, "LNSString", 0x1, NULL },
    { "getClipingOffset", NULL, "F", 0x4, NULL },
    { "getPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x1, NULL },
    { "createBorderPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x4, NULL },
    { "createBorderPathWith2SidesMissingWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x4, NULL },
    { "getShapeWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "isPadForArc", NULL, "Z", 0x1, NULL },
    { "isPaintLast", NULL, "Z", 0x1, NULL },
    { "isRectangular", NULL, "Z", 0x1, NULL },
    { "calculateInsetsWithRAREUIInsets:withBoolean:", NULL, "LRAREUIInsets", 0x4, NULL },
    { "setLineColorExWithRAREUIColor:", NULL, "LRAREaUILineBorder", 0x4, NULL },
    { "setLineStyleExWithNSString:", NULL, "LRAREaUILineBorder", 0x4, NULL },
    { "setThicknessExWithFloat:", NULL, "LRAREaUILineBorder", 0x4, NULL },
    { "getColor", NULL, "LRAREUIColor", 0x4, NULL },
    { "adjustStrokeForThickness", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "sharedBlackLineBorder_", NULL, 0xc, "LRAREUILineBorder" },
    { "sharedLineBorder_", NULL, 0xc, "LRAREUILineBorder" },
    { "lineStyle_", NULL, 0x4, "LNSString" },
    { "onePixelThickness_", NULL, 0x9, "F" },
    { "arcHeight_", NULL, 0x4, "F" },
    { "arcWidth_", NULL, 0x4, "F" },
    { "clipShape_", NULL, 0x4, "LRAREiPlatformPath" },
    { "paintShape_", NULL, 0x4, "LRAREiPlatformPath" },
    { "fixedBorderInsets_", NULL, 0x4, "LRAREUIInsets" },
    { "flatBottom_", NULL, 0x4, "Z" },
    { "flatLeft_", NULL, 0x4, "Z" },
    { "flatRight_", NULL, 0x4, "Z" },
    { "flatTop_", NULL, 0x4, "Z" },
    { "hilightColor_", NULL, 0x4, "LRAREUIColor" },
    { "insets_", NULL, 0x4, "LRAREUIInsets" },
    { "lineColor_", NULL, 0x4, "LRAREUIColor" },
    { "lineStroke_", NULL, 0x4, "LRAREUIStroke" },
    { "noBottom_", NULL, 0x4, "Z" },
    { "noLeft_", NULL, 0x4, "Z" },
    { "noRight_", NULL, 0x4, "Z" },
    { "noTop_", NULL, 0x4, "Z" },
    { "padding_", NULL, 0x4, "LRAREUIInsets" },
    { "roundedCorners_", NULL, 0x4, "Z" },
    { "thickness_", NULL, 0x4, "F" },
    { "has2MissingSides_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaUILineBorder = { "aUILineBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 43, methods, 25, fields, 0, NULL};
  return &_RAREaUILineBorder;
}

@end
