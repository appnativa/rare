//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/PaintBucket.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/UISimpleBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/lang/CloneNotSupportedException.h"

@implementation RAREPaintBucket

- (id)init {
  return [super init];
}

- (id)initWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp {
  if (self = [super init]) {
    backgroundPainter_ = bp;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)bg {
  return [self initRAREPaintBucketWithRAREUIColor:nil withRAREUIColor:bg];
}

- (id)initRAREPaintBucketWithRAREUIColor:(RAREUIColor *)fg
                         withRAREUIColor:(RAREUIColor *)bg {
  if (self = [super init]) {
    foregroundColor_ = fg;
    backgroundColor_ = bg;
    if ([bg isKindOfClass:[RAREUIColorShade class]]) {
      backgroundPainter_ = [((RAREUIColorShade *) check_class_cast(bg, [RAREUIColorShade class])) getBackgroundPainter];
    }
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)fg
          withRAREUIColor:(RAREUIColor *)bg {
  return [self initRAREPaintBucketWithRAREUIColor:fg withRAREUIColor:bg];
}

- (id)clone {
  @try {
    RAREPaintBucket *pb = (RAREPaintBucket *) check_class_cast([super clone], [RAREPaintBucket class]);
    ((RAREPaintBucket *) nil_chk(pb))->cachedComponentPainter_ = nil;
    return pb;
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    RAREPaintBucket *ch = [[RAREPaintBucket alloc] init];
    ch->font_ = font_;
    ch->border_ = border_;
    ch->foregroundColor_ = foregroundColor_;
    ch->backgroundColor_ = backgroundColor_;
    ch->backgroundPainter_ = backgroundPainter_;
    return ch;
  }
}

- (void)empty {
  backgroundColor_ = nil;
  backgroundPainter_ = nil;
  border_ = nil;
  font_ = nil;
  foregroundColor_ = nil;
  imagePainter_ = nil;
  cachedComponentPainter_ = nil;
}

- (void)installWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  [self installWithRAREiPlatformComponent:c withBoolean:YES];
}

- (void)installWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withBoolean:(BOOL)useFont {
  if (foregroundColor_ != nil) {
    [((id<RAREiPlatformComponent>) nil_chk(c)) setForegroundWithRAREUIColor:foregroundColor_];
  }
  if (useFont && (font_ != nil)) {
    [((id<RAREiPlatformComponent>) nil_chk(c)) setFontWithRAREUIFont:font_];
  }
  id<RAREiPlatformComponentPainter> cp = [self getComponentPainterWithRAREiPlatformComponentPainter:[((id<RAREiPlatformComponent>) nil_chk(c)) getComponentPainter] withBoolean:NO];
  if (cp != nil) {
    [c setComponentPainterWithRAREiPlatformComponentPainter:cp];
    if ([cp getBackgroundPainter] == nil) {
      if (backgroundColor_ != nil) {
        [c setBackgroundWithRAREUIColor:backgroundColor_];
      }
    }
    return;
  }
  if (border_ != nil) {
    [c setBorderWithRAREiPlatformBorder:border_];
  }
  if (backgroundColor_ != nil) {
    [c setBackgroundWithRAREUIColor:backgroundColor_];
  }
}

- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)bg {
  cachedComponentPainter_ = nil;
  if ([bg isKindOfClass:[RAREUIColorShade class]]) {
    RAREUIColorShade *s = (RAREUIColorShade *) check_class_cast(bg, [RAREUIColorShade class]);
    switch ([[((RAREUIColorShade *) nil_chk(s)) getShade] ordinal]) {
      case RAREColorUtils_Shade_BGPAINT:
      [self setBackgroundPainterWithRAREiBackgroundPainter:[s getBackgroundPainter]];
      break;
      default:
      break;
    }
  }
  backgroundColor_ = bg;
}

- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp {
  cachedComponentPainter_ = nil;
  backgroundPainter_ = bp;
}

- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  cachedComponentPainter_ = nil;
  self->border_ = border;
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)font {
  self->font_ = font;
}

- (void)setForegroundColorWithRAREUIColor:(RAREUIColor *)fg {
  foregroundColor_ = fg;
}

- (void)setImagePainterWithRAREiImagePainter:(id<RAREiImagePainter>)ip {
  cachedComponentPainter_ = nil;
  self->imagePainter_ = ip;
}

- (RAREUIColor *)getBackgroundColor {
  return backgroundColor_;
}

- (RAREUIColor *)getBackgroundColorAlways {
  if (backgroundColor_ != nil) {
    return backgroundColor_;
  }
  if (backgroundPainter_ != nil) {
    return [backgroundPainter_ getBackgroundColor];
  }
  return nil;
}

+ (RAREUIColor *)getBackgroundColorWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  id<RAREiPlatformComponentPainter> cp = [((RARERenderableDataItem *) nil_chk(item)) getComponentPainter];
  RAREUIColor *bg = [item getBackground];
  if (bg == nil) {
    if (cp != nil) {
      bg = [cp getBackgroundColor];
      if ((bg == nil) && ([cp getBackgroundPainter] != nil)) {
        bg = [((id<RAREiBackgroundPainter>) nil_chk([cp getBackgroundPainter])) getBackgroundColor];
      }
    }
  }
  return bg;
}

- (id<RAREiBackgroundPainter>)getBackgroundPainter {
  return backgroundPainter_;
}

- (RAREUIColor *)getBackgroundShade {
  if (([backgroundColor_ isKindOfClass:[RAREUIColorShade class]]) && [((RAREUIColorShade *) check_class_cast(backgroundColor_, [RAREUIColorShade class])) getBackgroundPainter] != nil) {
    return backgroundColor_;
  }
  if (backgroundPainter_ != nil) {
    return [[RAREUIColorShade alloc] initWithRAREiBackgroundPainter:backgroundPainter_];
  }
  return backgroundColor_;
}

- (id<RAREiPlatformBorder>)getBorder {
  return border_;
}

- (id<RAREiPlatformComponentPainter>)getCachedComponentPainterEx {
  return cachedComponentPainter_;
}

- (id<RAREiPlatformComponentPainter>)getCachedComponentPainter {
  if ((cachedComponentPainter_ != nil) && [cachedComponentPainter_ isDisposed]) {
    cachedComponentPainter_ = nil;
  }
  if (cachedComponentPainter_ == nil) {
    cachedComponentPainter_ = [self getComponentPainterWithBoolean:YES];
  }
  return cachedComponentPainter_;
}

- (id<RAREiPlatformComponentPainter>)getComponentPainter {
  return [self getComponentPainterWithRAREiPlatformComponentPainter:nil withBoolean:YES];
}

- (id<RAREiPlatformComponentPainter>)getComponentPainterWithBoolean:(BOOL)always {
  return [self getComponentPainterWithRAREiPlatformComponentPainter:nil withBoolean:always];
}

- (RAREUIFont *)getFont {
  return font_;
}

- (RAREUIColor *)getForegroundColor {
  return foregroundColor_;
}

- (id<RAREiGradientPainter>)getGradientPainter {
  if ([(id) backgroundPainter_ conformsToProtocol: @protocol(RAREiGradientPainter)]) {
    return (id<RAREiGradientPainter>) check_protocol_cast(backgroundPainter_, @protocol(RAREiGradientPainter));
  }
  return nil;
}

- (id<RAREiImagePainter>)getImagePainter {
  return imagePainter_;
}

- (id<RAREiPlatformPainter>)getPainter {
  if ((border_ == nil) && (backgroundColor_ == nil)) {
    return [self getBackgroundPainter];
  }
  if (border_ == nil) {
    return (backgroundPainter_ == nil) ? [RAREUIColorHelper getBackgroundPainterWithRAREUIColor:backgroundColor_] : backgroundPainter_;
  }
  return [self getComponentPainterWithBoolean:YES];
}

- (BOOL)isBackgroundPaint {
  if (backgroundColor_ != nil) {
    return YES;
  }
  if (backgroundPainter_ != nil) {
    return YES;
  }
  if (imagePainter_ != nil) {
    return YES;
  }
  return NO;
}

- (BOOL)isEmpty {
  if (foregroundColor_ != nil) {
    return NO;
  }
  if (backgroundColor_ != nil) {
    return NO;
  }
  if (backgroundPainter_ != nil) {
    return NO;
  }
  if (imagePainter_ != nil) {
    return NO;
  }
  return YES;
}

- (id<RAREiPlatformComponentPainter>)getComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp
                                                                              withBoolean:(BOOL)always {
  if (always || (imagePainter_ != nil) || (backgroundPainter_ != nil) || ((backgroundColor_ != nil) && (border_ != nil) && ![border_ isPaintLast])) {
    cp = (cp == nil) ? [[RAREUIComponentPainter alloc] init] : ((id) cp);
    [cp setBackgroundOverlayPainterWithRAREiPlatformPainter:imagePainter_];
    [cp setBackgroundPainterWithRAREiBackgroundPainter:backgroundPainter_ withBoolean:NO];
    if ((backgroundPainter_ == nil) && (backgroundColor_ != nil)) {
      [cp setBackgroundPainterWithRAREiBackgroundPainter:[[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:backgroundColor_] withBoolean:NO];
    }
    if (border_ != nil) {
      [cp setBorderWithRAREiPlatformBorder:border_];
    }
  }
  else if ((cp != nil) && (backgroundColor_ != nil)) {
    [cp setBackgroundPainterWithRAREiBackgroundPainter:[[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:backgroundColor_] withBoolean:NO];
  }
  if ((cp != nil) && (border_ != nil)) {
    [cp setBorderWithRAREiPlatformBorder:border_];
  }
  return cp;
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREPaintBucket *)other {
  [super copyAllFieldsTo:other];
  other->backgroundColor_ = backgroundColor_;
  other->backgroundPainter_ = backgroundPainter_;
  other->border_ = border_;
  other->cachedComponentPainter_ = cachedComponentPainter_;
  other->font_ = font_;
  other->foregroundColor_ = foregroundColor_;
  other->imagePainter_ = imagePainter_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "getBackgroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBackgroundColorAlways", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBackgroundColorWithRARERenderableDataItem:", NULL, "LRAREUIColor", 0x9, NULL },
    { "getBackgroundPainter", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "getBackgroundShade", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getCachedComponentPainterEx", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getCachedComponentPainter", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getComponentPainter", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getComponentPainterWithBoolean:", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getForegroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getGradientPainter", NULL, "LRAREiGradientPainter", 0x1, NULL },
    { "getImagePainter", NULL, "LRAREiImagePainter", 0x1, NULL },
    { "getPainter", NULL, "LRAREiPlatformPainter", 0x1, NULL },
    { "isBackgroundPaint", NULL, "Z", 0x1, NULL },
    { "isEmpty", NULL, "Z", 0x1, NULL },
    { "getComponentPainterWithRAREiPlatformComponentPainter:withBoolean:", NULL, "LRAREiPlatformComponentPainter", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "backgroundColor_", NULL, 0x4, "LRAREUIColor" },
    { "backgroundPainter_", NULL, 0x4, "LRAREiBackgroundPainter" },
    { "border_", NULL, 0x4, "LRAREiPlatformBorder" },
    { "cachedComponentPainter_", NULL, 0x4, "LRAREiPlatformComponentPainter" },
    { "font_", NULL, 0x4, "LRAREUIFont" },
    { "foregroundColor_", NULL, 0x4, "LRAREUIColor" },
    { "imagePainter_", NULL, 0x4, "LRAREiImagePainter" },
  };
  static J2ObjcClassInfo _RAREPaintBucket = { "PaintBucket", "com.appnativa.rare.ui.painter", NULL, 0x1, 19, methods, 7, fields, 0, NULL};
  return &_RAREPaintBucket;
}

@end
