//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/painter/UIBackgroundPainter.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/Displayed.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/aUIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "java/lang/Exception.h"
#include "java/lang/InternalError.h"
#import "AppleHelper.h"

@implementation RAREUIBackgroundPainter

static RAREUIBackgroundPainter * RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_;
static RAREUIBackgroundPainter * RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_DK_;
static RAREUIBackgroundPainter * RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_LT_;
static RAREUIBackgroundPainter * RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_MID_;

+ (RAREUIBackgroundPainter *)BGCOLOR_GRADIENT_PAINTER_DK {
  return RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_;
}

+ (RAREUIBackgroundPainter *)BGCOLOR_GRADIENT_PAINTER_DK_DK {
  return RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_DK_;
}

+ (RAREUIBackgroundPainter *)BGCOLOR_GRADIENT_PAINTER_LT {
  return RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_LT_;
}

+ (RAREUIBackgroundPainter *)BGCOLOR_GRADIENT_PAINTER_MID {
  return RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_MID_;
}

- (id)init {
  return [super init];
}

- (id)initWithRAREUIColor:(RAREUIColor *)bg {
  return [super initWithRAREUIColor:bg];
}

- (id)initWithRAREUIColor:(RAREUIColor *)start
          withRAREUIColor:(RAREUIColor *)end {
  return [super initWithRAREUIColor:start withRAREUIColor:end];
}

- (id)initWithRAREUIColorArray:(IOSObjectArray *)colors
withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  return [super initWithRAREUIColorArray:colors withRAREiGradientPainter_DirectionEnum:direction];
}

- (id)initWithRAREUIColor:(RAREUIColor *)start
          withRAREUIColor:(RAREUIColor *)end
withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  return [super initWithRAREUIColor:start withRAREUIColor:end withRAREiGradientPainter_DirectionEnum:direction];
}

- (id)initWithRAREiGradientPainter_TypeEnum:(RAREiGradientPainter_TypeEnum *)type
     withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction
                       withRAREUIColorArray:(IOSObjectArray *)colors
                             withFloatArray:(IOSFloatArray *)distribution
                                    withInt:(int)magnitude {
  return [super initWithRAREiGradientPainter_TypeEnum:type withRAREiGradientPainter_DirectionEnum:direction withRAREUIColorArray:colors withFloatArray:distribution withInt:magnitude];
}

- (BOOL)canUseLayer {
  return (displayed_ == [RAREDisplayedEnum ALWAYS]) && (gradientType_ != [RAREiGradientPainter_TypeEnum RADIAL]);
}

- (BOOL)canUseMainLayer {
  return (displayed_ == [RAREDisplayedEnum ALWAYS]) && (gradientType_ != [RAREiGradientPainter_TypeEnum RADIAL]);
}

- (id)clone {
  @try {
    RAREUIBackgroundPainter *bp = (RAREUIBackgroundPainter *) check_class_cast([super clone], [RAREUIBackgroundPainter class]);
    [((RAREUIBackgroundPainter *) nil_chk(bp)) clearCache];
    [bp setGradientColorsWithRAREUIColorArray:gradientColors_];
    return bp;
  }
  @catch (JavaLangException *ex) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

+ (RAREUIBackgroundPainter *)createWithRAREUIColor:(RAREUIColor *)sc
                                   withRAREUIColor:(RAREUIColor *)ec
            withRAREiGradientPainter_DirectionEnum:(RAREiGradientPainter_DirectionEnum *)direction {
  return [[RAREUIBackgroundPainter alloc] initWithRAREUIColor:sc withRAREUIColor:ec withRAREiGradientPainter_DirectionEnum:direction];
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation {
  if (proxy_ == nil) {
    proxy_ = [self createProxyWithRAREUIColorArray:[self getGradientColorsWithRAREUIColor:[self getBackgroundColor]] withFloatArray:gradientDistribution_];
  }
  [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
  [g clipRectWithFloat:x withFloat:y withFloat:width withFloat:height];
  if (gradientType_ == [RAREiGradientPainter_TypeEnum RADIAL]) {
    float radius = [self calculateRadiusWithFloat:width withFloat:height];
    RAREUIPoint *p = [self calculateRadialCenterWithFloat:width withFloat:height];
    [self drawRadialGradientWithId:proxy_ withId:[g getContextRef] withFloat:width withFloat:height withFloat:((RAREUIPoint *) nil_chk(p))->x_ + x withFloat:p->y_ + y withFloat:radius];
  }
  else {
    RAREUIRectangle *r = [self calculateLinearRectWithFloat:width withFloat:height];
    if (start_ == nil) {
      start_ = [[RAREUIPoint alloc] init];
      end_ = [[RAREUIPoint alloc] init];
    }
    ((RAREUIPoint *) nil_chk(start_))->x_ = ((RAREUIRectangle *) nil_chk(r))->x_ + x;
    start_->y_ = r->y_ + y;
    ((RAREUIPoint *) nil_chk(end_))->x_ = r->x_ + x + r->width_;
    end_->y_ = r->y_ + y + r->height_;
    [self drawGradientWithId:proxy_ withId:[g getContextRef] withRAREUIPoint:start_ withRAREUIPoint:end_];
  }
  [g restoreState];
}

- (id<RAREiPlatformPaint>)getPaintWithFloat:(float)width
                                  withFloat:(float)height {
  return self;
}

- (RAREUIColor *)getPlatformPaintColor {
  return [self getBackgroundColor];
}

- (id<RAREiBackgroundPainter>)getPlatformPaintPainter {
  return self;
}

- (BOOL)isPainter {
  return YES;
}

- (void)clearCache {
  [super clearCache];
  proxy_ = nil;
}

- (id)createProxyWithRAREUIColorArray:(IOSObjectArray *)gradientColors
                       withFloatArray:(IOSFloatArray *)gradientDistribution {
  return [AppleHelper createGradientWithColors: gradientColors distribution: gradientDistribution];
}

- (void)drawGradientWithId:(id)nativeGradient
                    withId:(id)context
           withRAREUIPoint:(RAREUIPoint *)start
           withRAREUIPoint:(RAREUIPoint *)end {
  [AppleHelper drawGradient:nativeGradient context:context fromPoint:start toPoint:end];
}

- (void)drawRadialGradientWithId:(id)nativeGradient
                          withId:(id)context
                       withFloat:(float)width
                       withFloat:(float)height
                       withFloat:(float)cx
                       withFloat:(float)cy
                       withFloat:(float)radius {
  [AppleHelper drawRadialGradient:nativeGradient context:context width:width height:height cx:cx cy:cy radius: radius];
}

- (void)drawGradientWithId:(id)nativeGradient
                    withId:(id)context
           withRAREUIPoint:(RAREUIPoint *)start
                 withFloat:(float)startr
           withRAREUIPoint:(RAREUIPoint *)end
                 withFloat:(float)endr {
  [AppleHelper drawGradient:nativeGradient context:context fromCenter:start radius:startr toCenter:end radius:endr];
}

+ (void)initialize {
  if (self == [RAREUIBackgroundPainter class]) {
    RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_ = [[RAREUIBackgroundPainter alloc] initWithRAREUIColor:nil withRAREUIColor:nil withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
    RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_DK_ = [[RAREUIBackgroundPainter alloc] initWithRAREUIColor:nil withRAREUIColor:nil withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
    RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_LT_ = [[RAREUIBackgroundPainter alloc] initWithRAREUIColor:nil withRAREUIColor:nil withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
    RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_MID_ = [[RAREUIBackgroundPainter alloc] initWithRAREUIColor:nil withRAREUIColor:nil withRAREiGradientPainter_DirectionEnum:[RAREiGradientPainter_DirectionEnum VERTICAL_TOP]];
    {
      RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_->bgColorType_ = [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum DARK];
      RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_DK_DK_->bgColorType_ = [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum DARK_DARK];
      RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_LT_->bgColorType_ = [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum LITE];
      RAREUIBackgroundPainter_BGCOLOR_GRADIENT_PAINTER_MID_->bgColorType_ = [RAREaUIBackgroundPainter_BGCOLOR_TYPEEnum MIDDLE];
    }
  }
}

- (void)copyAllFieldsTo:(RAREUIBackgroundPainter *)other {
  [super copyAllFieldsTo:other];
  other->end_ = end_;
  other->proxy_ = proxy_;
  other->start_ = start_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "canUseLayer", NULL, "Z", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "createWithRAREUIColor:withRAREUIColor:withRAREiGradientPainter_DirectionEnum:", NULL, "LRAREUIBackgroundPainter", 0x9, NULL },
    { "getPaintWithFloat:withFloat:", NULL, "LRAREiPlatformPaint", 0x1, NULL },
    { "getPlatformPaintColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getPlatformPaintPainter", NULL, "LRAREiBackgroundPainter", 0x1, NULL },
    { "isPainter", NULL, "Z", 0x1, NULL },
    { "clearCache", NULL, "V", 0x4, NULL },
    { "createProxyWithRAREUIColorArray:withFloatArray:", NULL, "LNSObject", 0x104, NULL },
    { "drawGradientWithId:withId:withRAREUIPoint:withRAREUIPoint:", NULL, "V", 0x104, NULL },
    { "drawRadialGradientWithId:withId:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x104, NULL },
    { "drawGradientWithId:withId:withRAREUIPoint:withFloat:withRAREUIPoint:withFloat:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "BGCOLOR_GRADIENT_PAINTER_DK_", NULL, 0x19, "LRAREUIBackgroundPainter" },
    { "BGCOLOR_GRADIENT_PAINTER_DK_DK_", NULL, 0x19, "LRAREUIBackgroundPainter" },
    { "BGCOLOR_GRADIENT_PAINTER_LT_", NULL, 0x19, "LRAREUIBackgroundPainter" },
    { "BGCOLOR_GRADIENT_PAINTER_MID_", NULL, 0x19, "LRAREUIBackgroundPainter" },
    { "end_", NULL, 0x0, "LRAREUIPoint" },
    { "start_", NULL, 0x0, "LRAREUIPoint" },
    { "proxy_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREUIBackgroundPainter = { "UIBackgroundPainter", "com.appnativa.rare.ui.painter", NULL, 0x1, 13, methods, 7, fields, 0, NULL};
  return &_RAREUIBackgroundPainter;
}

@end