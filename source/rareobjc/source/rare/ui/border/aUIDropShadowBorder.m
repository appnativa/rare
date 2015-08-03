//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIDropShadowBorder.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/border/aUIDropShadowBorder.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/NinePatch.h"
#include "java/io/IOException.h"
#include "java/util/Map.h"

@implementation RAREaUIDropShadowBorder

static IOSObjectArray * RAREaUIDropShadowBorder_dropShadowNinePatch_;
static IOSObjectArray * RAREaUIDropShadowBorder_shadowNinePatch_;

+ (IOSObjectArray *)dropShadowNinePatch {
  return RAREaUIDropShadowBorder_dropShadowNinePatch_;
}

+ (void)setDropShadowNinePatch:(IOSObjectArray *)dropShadowNinePatch {
  RAREaUIDropShadowBorder_dropShadowNinePatch_ = dropShadowNinePatch;
}

+ (IOSObjectArray *)shadowNinePatch {
  return RAREaUIDropShadowBorder_shadowNinePatch_;
}

+ (void)setShadowNinePatch:(IOSObjectArray *)shadowNinePatch {
  RAREaUIDropShadowBorder_shadowNinePatch_ = shadowNinePatch;
}

- (id)init {
  return [self initRAREaUIDropShadowBorderWithRAREUIColor:[RAREaUIDropShadowBorder getDefaultShadowColor] withFloat:[RAREScreenUtils PLATFORM_PIXELS_5]];
}

- (id)initWithBoolean:(BOOL)showLeftShadow {
  return [self initRAREaUIDropShadowBorderWithRAREUIColor:[RAREaUIDropShadowBorder getDefaultShadowColor] withFloat:[RAREScreenUtils PLATFORM_PIXELS_5] withFloat:.5f withFloat:[RAREScreenUtils PLATFORM_PIXELS_6] + [RAREScreenUtils PLATFORM_PIXELS_6] withBoolean:showLeftShadow withBoolean:showLeftShadow withBoolean:YES withBoolean:YES];
}

- (id)initRAREaUIDropShadowBorderWithRAREUIColor:(RAREUIColor *)shadowColor
                                       withFloat:(float)shadowSize {
  return [self initRAREaUIDropShadowBorderWithRAREUIColor:shadowColor withFloat:shadowSize withFloat:.5f withFloat:[RAREScreenUtils PLATFORM_PIXELS_6] + [RAREScreenUtils PLATFORM_PIXELS_6] withBoolean:NO withBoolean:NO withBoolean:YES withBoolean:YES];
}

- (id)initWithRAREUIColor:(RAREUIColor *)shadowColor
                withFloat:(float)shadowSize {
  return [self initRAREaUIDropShadowBorderWithRAREUIColor:shadowColor withFloat:shadowSize];
}

- (id)initRAREaUIDropShadowBorderWithRAREUIColor:(RAREUIColor *)shadowColor
                                       withFloat:(float)shadowSize
                                       withFloat:(float)shadowOpacity
                                       withFloat:(float)cornerSize
                                     withBoolean:(BOOL)showTopShadow
                                     withBoolean:(BOOL)showLeftShadow
                                     withBoolean:(BOOL)showBottomShadow
                                     withBoolean:(BOOL)showRightShadow {
  if (self = [super init]) {
    self->shadowColor_ = shadowColor;
    self->shadowSize_ = shadowSize;
    self->shadowOpacity_ = shadowOpacity;
    self->cornerSize_ = cornerSize;
    self->showTopShadow_ = showTopShadow;
    self->showLeftShadow_ = showLeftShadow;
    self->showBottomShadow_ = showBottomShadow;
    self->showRightShadow_ = showRightShadow;
    @try {
      ninePatch_ = [self getNinePatch];
      loaded_ = [((RARENinePatch *) nil_chk(ninePatch_)) isLoadedWithRAREiImageObserver:self];
    }
    @catch (JavaIoIOException *e) {
      @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
    }
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)shadowColor
                withFloat:(float)shadowSize
                withFloat:(float)shadowOpacity
                withFloat:(float)cornerSize
              withBoolean:(BOOL)showTopShadow
              withBoolean:(BOOL)showLeftShadow
              withBoolean:(BOOL)showBottomShadow
              withBoolean:(BOOL)showRightShadow {
  return [self initRAREaUIDropShadowBorderWithRAREUIColor:shadowColor withFloat:shadowSize withFloat:shadowOpacity withFloat:cornerSize withBoolean:showTopShadow withBoolean:showLeftShadow withBoolean:showBottomShadow withBoolean:showRightShadow];
}

- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image {
  loaded_ = YES;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  if (end) {
    return;
  }
  if (loaded_) {
    if (paintOffset_ != nil) {
      x += paintOffset_->x_;
      y += paintOffset_->y_;
      width -= (paintOffset_->x_ * 2);
      height -= (paintOffset_->y_ * 2);
    }
    [((RARENinePatch *) nil_chk(ninePatch_)) drawWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:width withFloat:height];
  }
}

- (void)setPadForArcWithBoolean:(BOOL)pad {
}

- (float)getArcHeight {
  return 0;
}

- (float)getArcWidth {
  return 0;
}

- (RAREUIInsets *)getBorderInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  float top = showTopShadow_ ? shadowSize_ : 0;
  float left = showLeftShadow_ ? shadowSize_ : 0;
  float bottom = showBottomShadow_ ? shadowSize_ : 0;
  float right = showRightShadow_ ? shadowSize_ : 0;
  if (insets == nil) {
    insets = [[RAREUIInsets alloc] init];
  }
  (void) [((RAREUIInsets *) nil_chk(insets)) setWithDouble:top withDouble:right withDouble:bottom withDouble:left];
  return insets;
}

- (float)getCornerSize {
  return cornerSize_;
}

+ (RAREUIColor *)getDefaultShadowColor {
  RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.ShadowBorder.color"];
  if (c == nil) {
    c = [RAREUIColor BLACK];
  }
  return c;
}

- (RAREUIColor *)getShadowColor {
  return shadowColor_;
}

- (float)getShadowOpacity {
  return shadowOpacity_;
}

- (float)getShadowSize {
  return shadowSize_;
}

- (BOOL)isPadForArc {
  return NO;
}

- (BOOL)isPaintLast {
  return NO;
}

- (BOOL)isShowBottomShadow {
  return showBottomShadow_;
}

- (BOOL)isShowLeftShadow {
  return showLeftShadow_;
}

- (BOOL)isShowRightShadow {
  return showRightShadow_;
}

- (BOOL)isShowTopShadow {
  return showTopShadow_;
}

- (void)handleCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map {
  [super handleCustomPropertiesWithJavaUtilMap:map];
  paintOffset_ = [RAREUtils getPointWithNSString:(NSString *) check_class_cast([((id<JavaUtilMap>) nil_chk(map)) getWithId:@"offset"], [NSString class])];
  imageName_ = (NSString *) check_class_cast([map getWithId:@"image"], [NSString class]);
}

- (RAREUIImage *)getImageWithNSString:(NSString *)name {
  return [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getResourceAsImageWithNSString:name];
}

- (RARENinePatch *)getNinePatch {
  if (imageName_ != nil) {
    RAREUIImage *img = [self getImageWithNSString:imageName_];
    RARENinePatch *np = [((RAREUIImage *) nil_chk(img)) getNinePatchWithBoolean:YES];
    RAREUIColor *d = [RAREaUIDropShadowBorder getDefaultShadowColor];
    if ([((RAREUIColor *) nil_chk(shadowColor_)) getRGB] != [((RAREUIColor *) nil_chk(d)) getRGB]) {
      [((RARENinePatch *) nil_chk(np)) changeNinePatchColorWithRAREUIColor:shadowColor_ withRAREUIColor:d];
    }
    return np;
  }
  NSString *s;
  int pos = 0;
  if (showLeftShadow_ || showTopShadow_) {
    if (shadowSize_ > [RAREScreenUtils PLATFORM_PIXELS_7]) {
      s = @"Rare.icon.shadow14";
      pos = 2;
    }
    else if (shadowSize_ > [RAREScreenUtils PLATFORM_PIXELS_5]) {
      s = @"Rare.icon.shadow7";
      pos = 1;
    }
    else {
      s = @"Rare.icon.shadow";
      pos = 0;
    }
    RAREUIColor *d = [RAREaUIDropShadowBorder getDefaultShadowColor];
    if ([((RAREUIColor *) nil_chk(shadowColor_)) getRGB] != [((RAREUIColor *) nil_chk(d)) getRGB]) {
      RAREUIImage *img = [self getImageWithNSString:s];
      RARENinePatch *np = [((RAREUIImage *) nil_chk(img)) getNinePatchWithBoolean:YES];
      [((RARENinePatch *) nil_chk(np)) changeNinePatchColorWithRAREUIColor:shadowColor_ withRAREUIColor:d];
      return np;
    }
    else {
      if (IOSObjectArray_Get(nil_chk(RAREaUIDropShadowBorder_shadowNinePatch_), pos) == nil) {
        RAREUIImage *img = [self getImageWithNSString:s];
        (void) IOSObjectArray_Set(RAREaUIDropShadowBorder_shadowNinePatch_, pos, [((RAREUIImage *) nil_chk(img)) getNinePatchWithBoolean:YES]);
      }
      return IOSObjectArray_Get(RAREaUIDropShadowBorder_shadowNinePatch_, pos);
    }
  }
  else {
    if (shadowSize_ > [RAREScreenUtils PLATFORM_PIXELS_7]) {
      s = @"Rare.icon.drop_shadow14";
      pos = 2;
    }
    else if (shadowSize_ > [RAREScreenUtils PLATFORM_PIXELS_5]) {
      s = @"Rare.icon.drop_shadow7";
      pos = 1;
    }
    else {
      s = @"Rare.icon.drop_shadow";
      pos = 0;
    }
    RAREUIColor *d = [RAREaUIDropShadowBorder getDefaultShadowColor];
    if ([((RAREUIColor *) nil_chk(shadowColor_)) getRGB] != [((RAREUIColor *) nil_chk(d)) getRGB]) {
      RAREUIImage *img = [self getImageWithNSString:s];
      RARENinePatch *np = [((RAREUIImage *) nil_chk(img)) getNinePatchWithBoolean:YES];
      [((RARENinePatch *) nil_chk(np)) changeNinePatchColorWithRAREUIColor:shadowColor_ withRAREUIColor:d];
      return np;
    }
    else {
      if (IOSObjectArray_Get(nil_chk(RAREaUIDropShadowBorder_dropShadowNinePatch_), pos) == nil) {
        RAREUIImage *img = [self getImageWithNSString:s];
        (void) IOSObjectArray_Set(RAREaUIDropShadowBorder_dropShadowNinePatch_, pos, [((RAREUIImage *) nil_chk(img)) getNinePatchWithBoolean:YES]);
      }
      return IOSObjectArray_Get(RAREaUIDropShadowBorder_dropShadowNinePatch_, pos);
    }
  }
}

- (RAREUIPoint *)getPaintOffset {
  return paintOffset_;
}

- (void)setPaintOffsetWithRAREUIPoint:(RAREUIPoint *)paintOffset {
  self->paintOffset_ = paintOffset;
}

+ (void)initialize {
  if (self == [RAREaUIDropShadowBorder class]) {
    RAREaUIDropShadowBorder_dropShadowNinePatch_ = [IOSObjectArray arrayWithLength:3 type:[IOSClass classWithClass:[RARENinePatch class]]];
    RAREaUIDropShadowBorder_shadowNinePatch_ = [IOSObjectArray arrayWithLength:3 type:[IOSClass classWithClass:[RARENinePatch class]]];
  }
}

- (void)copyAllFieldsTo:(RAREaUIDropShadowBorder *)other {
  [super copyAllFieldsTo:other];
  other->cornerSize_ = cornerSize_;
  other->imageName_ = imageName_;
  other->loaded_ = loaded_;
  other->ninePatch_ = ninePatch_;
  other->paintOffset_ = paintOffset_;
  other->shadowColor_ = shadowColor_;
  other->shadowOpacity_ = shadowOpacity_;
  other->shadowSize_ = shadowSize_;
  other->showBottomShadow_ = showBottomShadow_;
  other->showLeftShadow_ = showLeftShadow_;
  other->showRightShadow_ = showRightShadow_;
  other->showTopShadow_ = showTopShadow_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBorderInsetsWithRAREUIInsets:", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getDefaultShadowColor", NULL, "LRAREUIColor", 0x9, NULL },
    { "getShadowColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "isPadForArc", NULL, "Z", 0x1, NULL },
    { "isPaintLast", NULL, "Z", 0x1, NULL },
    { "isShowBottomShadow", NULL, "Z", 0x1, NULL },
    { "isShowLeftShadow", NULL, "Z", 0x1, NULL },
    { "isShowRightShadow", NULL, "Z", 0x1, NULL },
    { "isShowTopShadow", NULL, "Z", 0x1, NULL },
    { "getImageWithNSString:", NULL, "LRAREUIImage", 0x4, NULL },
    { "getNinePatch", NULL, "LRARENinePatch", 0x4, "JavaIoIOException" },
    { "getPaintOffset", NULL, "LRAREUIPoint", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "dropShadowNinePatch_", NULL, 0xc, "LIOSObjectArray" },
    { "shadowNinePatch_", NULL, 0xc, "LIOSObjectArray" },
    { "cornerSize_", NULL, 0x14, "F" },
    { "loaded_", NULL, 0x4, "Z" },
    { "ninePatch_", NULL, 0x4, "LRARENinePatch" },
    { "shadowColor_", NULL, 0x14, "LRAREUIColor" },
    { "shadowOpacity_", NULL, 0x14, "F" },
    { "shadowSize_", NULL, 0x14, "F" },
    { "showBottomShadow_", NULL, 0x14, "Z" },
    { "showLeftShadow_", NULL, 0x14, "Z" },
    { "showRightShadow_", NULL, 0x14, "Z" },
    { "showTopShadow_", NULL, 0x14, "Z" },
    { "imageName_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREaUIDropShadowBorder = { "aUIDropShadowBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 12, methods, 13, fields, 0, NULL};
  return &_RAREaUIDropShadowBorder;
}

@end
