//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIBackBorder.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/BorderUtils.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"
#include "com/appnativa/rare/ui/border/aUIBackBorder.h"
#include "com/appnativa/rare/ui/border/aUILineBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"

@implementation RAREaUIBackBorder

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  if (self = [super initWithRAREUIColor:color]) {
    roundedCorners_ = YES;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withFloat:[RAREScreenUtils platformPixelsWithFloat:6]]) {
    roundedCorners_ = YES;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arc {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withFloat:arc]) {
    roundedCorners_ = YES;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
                withFloat:(float)thickness
                withFloat:(float)arcWidth
                withFloat:(float)arcHeight {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withFloat:arcWidth withFloat:arcHeight]) {
    roundedCorners_ = YES;
  }
  return self;
}

- (void)setCornerArcWithFloat:(float)arc {
  [super setCornerArcWithFloat:arc];
  roundedCorners_ = YES;
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
  RAREUIColor *color = [self getPaintColorWithRAREiPlatformComponent:[((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent]];
  if ([((RAREUIColor *) nil_chk(color)) getAlpha] == 0) {
    return;
  }
  roundedCorners_ = YES;
  if (lineStroke_ == nil) {
    lineStroke_ = [[RAREUIStroke alloc] initWithFloat:thickness_ withRAREUIStroke_CapEnum:[RAREUIStroke_CapEnum ROUND] withRAREUIStroke_JoinEnum:[RAREUIStroke_JoinEnum ROUND]];
  }
  RAREUIStroke *stroke = [g getStroke];
  RAREUIColor *c = [g getColor];
  [g setColorWithRAREUIColor:color];
  [g setStrokeWithRAREUIStroke:lineStroke_];
  if (paintShape_ != nil) {
    [paintShape_ reset];
  }
  id<RAREiPlatformPath> p = [self getPathWithRAREiPlatformPath:paintShape_ withFloat:x withFloat:y withFloat:width withFloat:height withBoolean:NO];
  [g drawShapeWithRAREiPlatformShape:p withFloat:0 withFloat:0];
  [g setStrokeWithRAREUIStroke:stroke];
  [g setColorWithRAREUIColor:c];
}

- (RAREaUILineBorder *)setThicknessWithFloat:(float)thickness {
  lineStroke_ = nil;
  return [super setThicknessWithFloat:thickness];
}

- (id<RAREiPlatformPath>)createBorderPathWithRAREiPlatformPath:(id<RAREiPlatformPath>)p
                                                     withFloat:(float)x
                                                     withFloat:(float)y
                                                     withFloat:(float)width
                                                     withFloat:(float)height
                                                     withFloat:(float)aw
                                                     withFloat:(float)ah
                                                   withBoolean:(BOOL)clip {
  return [RAREBorderUtils createBackBorderPathWithRAREiPlatformPath:p withFloat:x withFloat:y withFloat:width withFloat:height withFloat:aw withFloat:ah withBoolean:clip ? NO : noLeft_];
}

- (RAREUIInsets *)calculateInsetsWithRAREUIInsets:(RAREUIInsets *)insets
                                      withBoolean:(BOOL)pad {
  insets = [super calculateInsetsWithRAREUIInsets:insets withBoolean:pad];
  if (noLeft_) {
    ((RAREUIInsets *) nil_chk(insets))->right_ += [RAREScreenUtils platformPixelsWithFloat:5];
  }
  else {
    ((RAREUIInsets *) nil_chk(insets))->left_ += [RAREScreenUtils platformPixelsWithFloat:5];
  }
  return insets;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setThicknessWithFloat:", NULL, "LRAREaUILineBorder", 0x1, NULL },
    { "createBorderPathWithRAREiPlatformPath:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "LRAREiPlatformPath", 0x4, NULL },
    { "calculateInsetsWithRAREUIInsets:withBoolean:", NULL, "LRAREUIInsets", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREaUIBackBorder = { "aUIBackBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 3, methods, 0, NULL, 0, NULL};
  return &_RAREaUIBackBorder;
}

@end
