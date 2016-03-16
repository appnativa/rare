//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIEtchedBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/border/aUIBorder.h"
#include "com/appnativa/rare/ui/border/aUIEtchedBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREaUIEtchedBorder

static float RAREaUIEtchedBorder_INSETS_SIZE_;

+ (int)LOWERED {
  return RAREaUIEtchedBorder_LOWERED;
}

+ (int)RAISED {
  return RAREaUIEtchedBorder_RAISED;
}

+ (float)INSETS_SIZE {
  return RAREaUIEtchedBorder_INSETS_SIZE_;
}

- (id)init {
  if (self = [super init]) {
    etchType_ = RAREaUIEtchedBorder_LOWERED;
  }
  return self;
}

- (id)initWithInt:(int)etchType {
  if (self = [super init]) {
    etchType_ = RAREaUIEtchedBorder_LOWERED;
    if ((etchType != RAREaUIEtchedBorder_RAISED) && (etchType != RAREaUIEtchedBorder_LOWERED)) {
      @throw [[JavaLangIllegalArgumentException alloc] init];
    }
    self->etchType_ = etchType;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)highlightColor
          withRAREUIColor:(RAREUIColor *)shadowColor {
  if (self = [super init]) {
    etchType_ = RAREaUIEtchedBorder_LOWERED;
    highlight_ = highlightColor;
    shadow_ = shadowColor;
  }
  return self;
}

- (id)initWithInt:(int)etchType
  withRAREUIColor:(RAREUIColor *)highlightColor
  withRAREUIColor:(RAREUIColor *)shadowColor {
  if (self = [super init]) {
    etchType_ = RAREaUIEtchedBorder_LOWERED;
    if ((etchType != RAREaUIEtchedBorder_RAISED) && (etchType != RAREaUIEtchedBorder_LOWERED)) {
      @throw [[JavaLangIllegalArgumentException alloc] init];
    }
    self->etchType_ = etchType;
    highlight_ = highlightColor;
    shadow_ = shadowColor;
  }
  return self;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)last {
  if (last == [self isPaintLast]) {
    RAREUIStroke *stroke = [((id<RAREiPlatformGraphics>) nil_chk(g)) getStroke];
    RAREUIColor *c = [g getColor];
    [g setStrokeWithRAREUIStroke:[RAREUIStroke SOLID_STROKE]];
    [g translateWithFloat:x withFloat:y];
    float p1 = [RAREScreenUtils PLATFORM_PIXELS_1];
    RAREUIColor *color = (etchType_ == RAREaUIEtchedBorder_LOWERED) ? [self getShadowColor] : [self getHighlightColor];
    [g setColorWithRAREUIColor:color];
    [g drawRectWithFloat:0 withFloat:0 withFloat:width - 1 withFloat:height - 1];
    color = (etchType_ == RAREaUIEtchedBorder_LOWERED) ? [self getHighlightColor] : [self getShadowColor];
    [g setColorWithRAREUIColor:color];
    [g drawLineWithFloat:p1 withFloat:height - 2 withFloat:p1 withFloat:p1];
    [g drawLineWithFloat:p1 withFloat:p1 withFloat:width - p1 withFloat:p1];
    [g drawLineWithFloat:0 withFloat:height withFloat:width withFloat:height];
    [g drawLineWithFloat:width withFloat:height withFloat:width withFloat:0];
    [g translateWithFloat:-x withFloat:-y];
    [g setStrokeWithRAREUIStroke:stroke];
    [g setColorWithRAREUIColor:c];
  }
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  if (insets == nil) {
    return [[RAREUIInsets alloc] initWithDouble:RAREaUIEtchedBorder_INSETS_SIZE_ withDouble:RAREaUIEtchedBorder_INSETS_SIZE_ withDouble:RAREaUIEtchedBorder_INSETS_SIZE_ withDouble:RAREaUIEtchedBorder_INSETS_SIZE_];
  }
  ((RAREUIInsets *) nil_chk(insets))->left_ = RAREaUIEtchedBorder_INSETS_SIZE_;
  insets->top_ = RAREaUIEtchedBorder_INSETS_SIZE_;
  insets->bottom_ = RAREaUIEtchedBorder_INSETS_SIZE_;
  insets->right_ = RAREaUIEtchedBorder_INSETS_SIZE_;
  return insets;
}

- (int)getEtchType {
  return etchType_;
}

- (RAREUIColor *)getHighlightColor {
  return highlight_;
}

- (RAREUIColor *)getShadowColor {
  return shadow_;
}

+ (void)initialize {
  if (self == [RAREaUIEtchedBorder class]) {
    RAREaUIEtchedBorder_INSETS_SIZE_ = [RAREScreenUtils PLATFORM_PIXELS_2];
  }
}

- (void)copyAllFieldsTo:(RAREaUIEtchedBorder *)other {
  [super copyAllFieldsTo:other];
  other->etchType_ = etchType_;
  other->highlight_ = highlight_;
  other->shadow_ = shadow_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getHighlightColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getShadowColor", NULL, "LRAREUIColor", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "LOWERED_", NULL, 0x19, "I" },
    { "RAISED_", NULL, 0x19, "I" },
    { "INSETS_SIZE_", NULL, 0x1a, "F" },
    { "etchType_", NULL, 0x4, "I" },
    { "highlight_", NULL, 0x4, "LRAREUIColor" },
    { "shadow_", NULL, 0x4, "LRAREUIColor" },
  };
  static J2ObjcClassInfo _RAREaUIEtchedBorder = { "aUIEtchedBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 3, methods, 6, fields, 0, NULL};
  return &_RAREaUIEtchedBorder;
}

@end
