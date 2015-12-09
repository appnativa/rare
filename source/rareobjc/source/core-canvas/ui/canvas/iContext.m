//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/iContext.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/canvas/Uint8ClampedArray.h"
#include "com/appnativa/rare/ui/canvas/iCanvas.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformPainter.h"
#include "java/lang/IllegalArgumentException.h"


@interface RAREiContext : NSObject
@end

@implementation RAREiContext

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "arcWithFloat:withFloat:withFloat:withFloat:withFloat:withBoolean:", NULL, "V", 0x401, NULL },
    { "arcToWithFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "beginPath", NULL, "V", 0x401, NULL },
    { "bezierCurveToWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "blur", NULL, "V", 0x401, NULL },
    { "clear", NULL, "V", 0x401, NULL },
    { "clearRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "clip", NULL, "V", 0x401, NULL },
    { "closePath", NULL, "V", 0x401, NULL },
    { "createImageDataWithRAREiContext_iImageData:", NULL, "LRAREiContext_iImageData", 0x401, NULL },
    { "createImageDataWithInt:withInt:", NULL, "LRAREiContext_iImageData", 0x401, NULL },
    { "createLinearGradientWithFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiContext_iCanvasGradient", 0x401, NULL },
    { "createPatternWithRAREiContext_iImageElement:withNSString:", NULL, "LRAREiContext_iCanvasPattern", 0x401, NULL },
    { "createRadialGradientWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiContext_iCanvasGradient", 0x401, NULL },
    { "createReflectionWithInt:withInt:withFloat:withInt:", NULL, "V", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiContext_iImageElement:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREUIImage:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiContext_iImageElement:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREUIImage:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiContext_iImageElement:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREUIImage:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fill", NULL, "V", 0x401, NULL },
    { "fillWithRAREiPlatformShape:", NULL, "V", 0x401, NULL },
    { "fillRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillRoundedRectWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillTextWithNSString:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillTextWithNSString:withFloat:withFloat:withInt:", NULL, "V", 0x401, NULL },
    { "lineToWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "measureTextWithNSString:", NULL, "LRAREiContext_iTextMetrics", 0x401, NULL },
    { "moveToWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "putImageDataWithRAREiContext_iImageData:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "putImageDataWithRAREiContext_iImageData:withInt:withInt:withInt:withInt:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "quadraticCurveToWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "rectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "renderWithRAREiPlatformGraphics:", NULL, "V", 0x401, NULL },
    { "restore", NULL, "V", 0x401, NULL },
    { "rotateWithFloat:", NULL, "V", 0x401, NULL },
    { "save", NULL, "V", 0x401, NULL },
    { "scale__WithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "stroke", NULL, "V", 0x401, NULL },
    { "strokeWithRAREiPlatformShape:", NULL, "V", 0x401, NULL },
    { "strokeRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "strokeRoundedRectWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "strokeTextWithNSString:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "strokeTextWithNSString:withFloat:withFloat:withInt:", NULL, "V", 0x401, NULL },
    { "transformWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setFillStyleWithRAREiContext_iCanvasGradient:", NULL, "V", 0x401, NULL },
    { "setFillStyleWithNSString:", NULL, "V", 0x401, NULL },
    { "setFillStyleWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setFontWithNSString:", NULL, "V", 0x401, NULL },
    { "setGlobalAlphaWithFloat:", NULL, "V", 0x401, NULL },
    { "setGlobalCompositeOperationWithNSString:", NULL, "V", 0x401, NULL },
    { "setLineCapWithNSString:", NULL, "V", 0x401, NULL },
    { "setLineJoinWithNSString:", NULL, "V", 0x401, NULL },
    { "setLineWidthWithFloat:", NULL, "V", 0x401, NULL },
    { "setMiterLimitWithFloat:", NULL, "V", 0x401, NULL },
    { "setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:", NULL, "V", 0x401, NULL },
    { "setSizeWithInt:withInt:", NULL, "V", 0x401, NULL },
    { "setStrokeStyleWithRAREiContext_iCanvasGradient:", NULL, "V", 0x401, NULL },
    { "setStrokeStyleWithNSString:", NULL, "V", 0x401, NULL },
    { "setStrokeStyleWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setTextAlignWithNSString:", NULL, "V", 0x401, NULL },
    { "setTextBaselineWithNSString:", NULL, "V", 0x401, NULL },
    { "setTransformWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "getCanvas", NULL, "LRAREiCanvas", 0x401, NULL },
    { "getFont", NULL, "LNSString", 0x401, NULL },
    { "getGlobalAlpha", NULL, "F", 0x401, NULL },
    { "getGlobalCompositeOperation", NULL, "LNSString", 0x401, NULL },
    { "getImageWithBoolean:", NULL, "LRAREUIImage", 0x401, NULL },
    { "getImageDataWithInt:withInt:withInt:withInt:", NULL, "LRAREiContext_iImageData", 0x401, NULL },
    { "getLineCap", NULL, "LNSString", 0x401, NULL },
    { "getLineJoin", NULL, "LNSString", 0x401, NULL },
    { "getLineWidth", NULL, "F", 0x401, NULL },
    { "getMiterLimit", NULL, "F", 0x401, NULL },
    { "getPainter", NULL, "LRAREiPlatformPainter", 0x401, NULL },
    { "getScalingType", NULL, "LRAREiImagePainter_ScalingTypeEnum", 0x401, NULL },
    { "getTextAlign", NULL, "LNSString", 0x401, NULL },
    { "getTextBaseline", NULL, "LNSString", 0x401, NULL },
    { "isPointInPathWithFloat:withFloat:", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext = { "iContext", "com.appnativa.rare.ui.canvas", NULL, 0x201, 81, methods, 0, NULL, 0, NULL};
  return &_RAREiContext;
}

@end

static RAREiContext_TextAlignEnum *RAREiContext_TextAlignEnum_left;
static RAREiContext_TextAlignEnum *RAREiContext_TextAlignEnum_start;
static RAREiContext_TextAlignEnum *RAREiContext_TextAlignEnum_right;
static RAREiContext_TextAlignEnum *RAREiContext_TextAlignEnum_end;
static RAREiContext_TextAlignEnum *RAREiContext_TextAlignEnum_center;
IOSObjectArray *RAREiContext_TextAlignEnum_values;

@implementation RAREiContext_TextAlignEnum

+ (RAREiContext_TextAlignEnum *)left {
  return RAREiContext_TextAlignEnum_left;
}
+ (RAREiContext_TextAlignEnum *)start {
  return RAREiContext_TextAlignEnum_start;
}
+ (RAREiContext_TextAlignEnum *)right {
  return RAREiContext_TextAlignEnum_right;
}
+ (RAREiContext_TextAlignEnum *)end {
  return RAREiContext_TextAlignEnum_end;
}
+ (RAREiContext_TextAlignEnum *)center {
  return RAREiContext_TextAlignEnum_center;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiContext_TextAlignEnum class]) {
    RAREiContext_TextAlignEnum_left = [[RAREiContext_TextAlignEnum alloc] initWithNSString:@"left" withInt:0];
    RAREiContext_TextAlignEnum_start = [[RAREiContext_TextAlignEnum alloc] initWithNSString:@"start" withInt:1];
    RAREiContext_TextAlignEnum_right = [[RAREiContext_TextAlignEnum alloc] initWithNSString:@"right" withInt:2];
    RAREiContext_TextAlignEnum_end = [[RAREiContext_TextAlignEnum alloc] initWithNSString:@"end" withInt:3];
    RAREiContext_TextAlignEnum_center = [[RAREiContext_TextAlignEnum alloc] initWithNSString:@"center" withInt:4];
    RAREiContext_TextAlignEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiContext_TextAlignEnum_left, RAREiContext_TextAlignEnum_start, RAREiContext_TextAlignEnum_right, RAREiContext_TextAlignEnum_end, RAREiContext_TextAlignEnum_center, nil } count:5 type:[IOSClass classWithClass:[RAREiContext_TextAlignEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiContext_TextAlignEnum_values];
}

+ (RAREiContext_TextAlignEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiContext_TextAlignEnum_values count]; i++) {
    RAREiContext_TextAlignEnum *e = RAREiContext_TextAlignEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiContext_TextAlignEnum"};
  static J2ObjcClassInfo _RAREiContext_TextAlignEnum = { "TextAlign", "com.appnativa.rare.ui.canvas", "iContext", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiContext_TextAlignEnum;
}

@end

static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_top;
static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_hanging;
static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_middle;
static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_alphabetic;
static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_ideographic;
static RAREiContext_TextBaselineEnum *RAREiContext_TextBaselineEnum_bottom;
IOSObjectArray *RAREiContext_TextBaselineEnum_values;

@implementation RAREiContext_TextBaselineEnum

+ (RAREiContext_TextBaselineEnum *)top {
  return RAREiContext_TextBaselineEnum_top;
}
+ (RAREiContext_TextBaselineEnum *)hanging {
  return RAREiContext_TextBaselineEnum_hanging;
}
+ (RAREiContext_TextBaselineEnum *)middle {
  return RAREiContext_TextBaselineEnum_middle;
}
+ (RAREiContext_TextBaselineEnum *)alphabetic {
  return RAREiContext_TextBaselineEnum_alphabetic;
}
+ (RAREiContext_TextBaselineEnum *)ideographic {
  return RAREiContext_TextBaselineEnum_ideographic;
}
+ (RAREiContext_TextBaselineEnum *)bottom {
  return RAREiContext_TextBaselineEnum_bottom;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiContext_TextBaselineEnum class]) {
    RAREiContext_TextBaselineEnum_top = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"top" withInt:0];
    RAREiContext_TextBaselineEnum_hanging = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"hanging" withInt:1];
    RAREiContext_TextBaselineEnum_middle = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"middle" withInt:2];
    RAREiContext_TextBaselineEnum_alphabetic = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"alphabetic" withInt:3];
    RAREiContext_TextBaselineEnum_ideographic = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"ideographic" withInt:4];
    RAREiContext_TextBaselineEnum_bottom = [[RAREiContext_TextBaselineEnum alloc] initWithNSString:@"bottom" withInt:5];
    RAREiContext_TextBaselineEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiContext_TextBaselineEnum_top, RAREiContext_TextBaselineEnum_hanging, RAREiContext_TextBaselineEnum_middle, RAREiContext_TextBaselineEnum_alphabetic, RAREiContext_TextBaselineEnum_ideographic, RAREiContext_TextBaselineEnum_bottom, nil } count:6 type:[IOSClass classWithClass:[RAREiContext_TextBaselineEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiContext_TextBaselineEnum_values];
}

+ (RAREiContext_TextBaselineEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiContext_TextBaselineEnum_values count]; i++) {
    RAREiContext_TextBaselineEnum *e = RAREiContext_TextBaselineEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiContext_TextBaselineEnum"};
  static J2ObjcClassInfo _RAREiContext_TextBaselineEnum = { "TextBaseline", "com.appnativa.rare.ui.canvas", "iContext", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiContext_TextBaselineEnum;
}

@end

@interface RAREiContext_iCanvasPaint : NSObject
@end

@implementation RAREiContext_iCanvasPaint

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cloneCopy", NULL, "LRAREiContext_iCanvasPaint", 0x401, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iCanvasPaint = { "iCanvasPaint", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iCanvasPaint;
}

@end

@interface RAREiContext_iCanvasGradient : NSObject
@end

@implementation RAREiContext_iCanvasGradient

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addColorStopWithFloat:withRAREUIColor:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iCanvasGradient = { "iCanvasGradient", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 1, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iCanvasGradient;
}

@end

@interface RAREiContext_iCanvasPattern : NSObject
@end

@implementation RAREiContext_iCanvasPattern

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isRepeatX", NULL, "Z", 0x401, NULL },
    { "isRepeatXorY", NULL, "Z", 0x401, NULL },
    { "isRepeatY", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iCanvasPattern = { "iCanvasPattern", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 3, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iCanvasPattern;
}

@end

@interface RAREiContext_iImageData : NSObject
@end

@implementation RAREiContext_iImageData

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "updateImageWithRAREUIImage:withInt:withInt:withInt:withInt:", NULL, "V", 0x401, NULL },
    { "getData", NULL, "LRAREUint8ClampedArray", 0x401, NULL },
    { "getHeight", NULL, "I", 0x401, NULL },
    { "getWidth", NULL, "I", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iImageData = { "iImageData", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 4, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iImageData;
}

@end

@interface RAREiContext_iImageElement : NSObject
@end

@implementation RAREiContext_iImageElement

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getImage", NULL, "LRAREUIImage", 0x401, NULL },
    { "getImageWithInt:withInt:withInt:withInt:", NULL, "LRAREUIImage", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iImageElement = { "iImageElement", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 2, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iImageElement;
}

@end

@interface RAREiContext_iTextMetrics : NSObject
@end

@implementation RAREiContext_iTextMetrics

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getWidth", NULL, "I", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiContext_iTextMetrics = { "iTextMetrics", "com.appnativa.rare.ui.canvas", "iContext", 0x209, 1, methods, 0, NULL, 0, NULL};
  return &_RAREiContext_iTextMetrics;
}

@end
