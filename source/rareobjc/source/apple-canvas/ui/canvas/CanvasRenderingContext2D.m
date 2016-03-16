//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-canvas/com/appnativa/rare/ui/canvas/CanvasRenderingContext2D.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/platform/apple/ui/util/ImageUtils.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "com/appnativa/rare/ui/Transform.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/canvas/CanvasRenderingContext2D.h"
#include "com/appnativa/rare/ui/canvas/ContextState.h"
#include "com/appnativa/rare/ui/canvas/aCompositingGraphics.h"
#include "com/appnativa/rare/ui/canvas/iCanvas.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iTransform.h"
#include "com/appnativa/rare/ui/painter/UIBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/UIImagePainter.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/Float.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/lang/RuntimeException.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#include "java/util/TreeMap.h"

@implementation RARECanvasRenderingContext2D

- (id)initWithRAREiCanvas:(id<RAREiCanvas>)canvas {
  return [super initWithRAREiCanvas:canvas];
}

- (BOOL)canUseLayer {
  return NO;
}

- (BOOL)canUseMainLayer {
  return NO;
}

- (id<RAREiContext_iCanvasGradient>)createLinearGradientWithFloat:(float)x0
                                                        withFloat:(float)y0
                                                        withFloat:(float)x1
                                                        withFloat:(float)y1 {
  return [[RARECanvasRenderingContext2D_CanvasGradient alloc] initWithRARECanvasRenderingContext2D:self withFloat:x0 withFloat:y0 withFloat:x1 withFloat:y1];
}

- (id<RAREiContext_iCanvasPattern>)createPatternWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                                                                  withNSString:(NSString *)repetition {
  return [[RARECanvasRenderingContext2D_CanvasPattern alloc] initWithRAREiContext_iImageElement:img withNSString:repetition];
}

- (id<RAREiContext_iCanvasGradient>)createRadialGradientWithFloat:(float)x0
                                                        withFloat:(float)y0
                                                        withFloat:(float)r0
                                                        withFloat:(float)x1
                                                        withFloat:(float)y1
                                                        withFloat:(float)r1 {
  return [[RARECanvasRenderingContext2D_CanvasGradient alloc] initWithRARECanvasRenderingContext2D:self withFloat:x0 withFloat:y0 withFloat:r0 withFloat:x1 withFloat:y1 withFloat:r1];
}

- (BOOL)isPointInPathWithFloat:(float)x
                     withFloat:(float)y {
  RAREUIPoint *p = [((RARETransform *) check_class_cast([((id<RAREiTransform>) nil_chk([((RAREContextState *) nil_chk(currentState_)) getTransform])) getPlatformTransform], [RARETransform class])) transformWithFloat:x withFloat:y];
  return [((id<RAREiPlatformPath>) nil_chk(currentPath_)) isPointInPathWithFloat:((RAREUIPoint *) nil_chk(p))->x_ withFloat:p->y_];
}

- (void)updateModCount {
}

- (RAREaCompositingGraphics *)createCompositingGraphicsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                                 withRAREUIImage:(RAREUIImage *)image {
  return [[RARECanvasRenderingContext2D_CompositingGraphics alloc] initWithRAREiPlatformGraphics:g withRAREUIImage:image];
}

- (RAREUIImage *)createImageIfNecessaryWithRAREUIImage:(RAREUIImage *)img
                                               withInt:(int)width
                                               withInt:(int)height {
  if ((img == nil) || ([img getWidth] != width) || ([img getHeight] != height)) {
    img = [RAREImageUtils createCompatibleImageWithInt:width withInt:height];
  }
  return img;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "canUseLayer", NULL, "Z", 0x1, NULL },
    { "canUseMainLayer", NULL, "Z", 0x1, NULL },
    { "createLinearGradientWithFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiContext_iCanvasGradient", 0x1, NULL },
    { "createPatternWithRAREiContext_iImageElement:withNSString:", NULL, "LRAREiContext_iCanvasPattern", 0x1, NULL },
    { "createRadialGradientWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiContext_iCanvasGradient", 0x1, NULL },
    { "isPointInPathWithFloat:withFloat:", NULL, "Z", 0x1, NULL },
    { "createCompositingGraphicsWithRAREiPlatformGraphics:withRAREUIImage:", NULL, "LRAREaCompositingGraphics", 0x4, NULL },
    { "createImageIfNecessaryWithRAREUIImage:withInt:withInt:", NULL, "LRAREUIImage", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARECanvasRenderingContext2D = { "CanvasRenderingContext2D", "com.appnativa.rare.ui.canvas", NULL, 0x1, 8, methods, 0, NULL, 0, NULL};
  return &_RARECanvasRenderingContext2D;
}

@end
@implementation RARECanvasRenderingContext2D_CanvasGradient

- (id)initWithRARECanvasRenderingContext2D:(RARECanvasRenderingContext2D *)outer$
                                 withFloat:(float)x0
                                 withFloat:(float)y0
                                 withFloat:(float)x1
                                 withFloat:(float)y1 {
  if (self = [super init]) {
    stops_ = [[JavaUtilTreeMap alloc] init];
    start_CanvasGradient_ = [[RAREUIPoint alloc] initWithFloat:x0 withFloat:y0];
    end_CanvasGradient_ = [[RAREUIPoint alloc] initWithFloat:x1 withFloat:y1];
  }
  return self;
}

- (id)initWithRARECanvasRenderingContext2D:(RARECanvasRenderingContext2D *)outer$
                                 withFloat:(float)x0
                                 withFloat:(float)y0
                                 withFloat:(float)r0
                                 withFloat:(float)x1
                                 withFloat:(float)y1
                                 withFloat:(float)r1 {
  if (self = [super init]) {
    stops_ = [[JavaUtilTreeMap alloc] init];
    self->r0_ = r0;
    self->r1_ = r1;
    if ((r1 < 0) || (r0 < 0)) {
      @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"INDEX_SIZE_ERR"];
    }
    start_CanvasGradient_ = [[RAREUIPoint alloc] initWithFloat:x0 withFloat:y0];
    end_CanvasGradient_ = [[RAREUIPoint alloc] initWithFloat:x1 withFloat:y1];
    radial_ = YES;
  }
  return self;
}

- (void)addColorStopWithFloat:(float)offset
                 withNSString:(NSString *)color {
  [self addColorStopWithFloat:offset withRAREUIColor:[RAREColorUtils getColorWithNSString:color]];
}

- (void)addColorStopWithFloat:(float)offset
              withRAREUIColor:(RAREUIColor *)color {
  if ((offset < 0) || (offset > 1)) {
    @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:@"INDEX_SIZE_ERR"];
  }
  JavaLangFloat *f = [JavaLangFloat valueOfWithFloat:offset];
  if ([((JavaUtilTreeMap *) nil_chk(stops_)) containsKeyWithId:f]) {
    f = [[JavaLangFloat alloc] initWithFloat:offset + (offset / 1000.0f)];
  }
  (void) [stops_ putWithId:f withId:color];
  proxy_ = nil;
}

- (id<RAREiContext_iCanvasPaint>)cloneCopy {
  return (id<RAREiContext_iCanvasPaint>) check_protocol_cast([self clone], @protocol(RAREiContext_iCanvasPaint));
}

- (id<RAREiPlatformPaint>)getPaint {
  return self;
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation {
  if ([((RAREUIPoint *) nil_chk(start_CanvasGradient_)) isEqual:end_CanvasGradient_] && (r0_ == r1_)) {
    return;
  }
  else {
    if (proxy_ == nil) {
      id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([((JavaUtilTreeMap *) nil_chk(stops_)) entrySet])) iterator];
      id<JavaUtilMap_Entry> e;
      colors_ = [IOSObjectArray arrayWithLength:[stops_ size] type:[IOSClass classWithClass:[RAREUIColor class]]];
      distribution_ = [IOSFloatArray arrayWithLength:[stops_ size]];
      int i = 0;
      RAREUIColor *c;
      while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
        e = [it next];
        c = [((id<JavaUtilMap_Entry>) nil_chk(e)) getValue];
        (*IOSFloatArray_GetRef(distribution_, i)) = [((JavaLangFloat *) nil_chk([e getKey])) floatValue];
        (void) IOSObjectArray_Set(colors_, i, c);
        i++;
      }
      proxy_ = [self createProxyWithRAREUIColorArray:colors_ withFloatArray:distribution_];
    }
    if (radial_) {
      [self drawGradientWithId:proxy_ withId:[((id<RAREiPlatformGraphics>) nil_chk(g)) getContextRef] withRAREUIPoint:start_CanvasGradient_ withFloat:r0_ withRAREUIPoint:end_CanvasGradient_ withFloat:r1_];
    }
    else {
      [self drawGradientWithId:proxy_ withId:[((id<RAREiPlatformGraphics>) nil_chk(g)) getContextRef] withRAREUIPoint:start_CanvasGradient_ withRAREUIPoint:end_CanvasGradient_];
    }
  }
}

- (void)copyAllFieldsTo:(RARECanvasRenderingContext2D_CanvasGradient *)other {
  [super copyAllFieldsTo:other];
  other->colors_ = colors_;
  other->distribution_ = distribution_;
  other->end_CanvasGradient_ = end_CanvasGradient_;
  other->r0_ = r0_;
  other->r1_ = r1_;
  other->radial_ = radial_;
  other->start_CanvasGradient_ = start_CanvasGradient_;
  other->stops_ = stops_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "cloneCopy", NULL, "LRAREiContext_iCanvasPaint", 0x1, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "stops_", NULL, 0x0, "LJavaUtilTreeMap" },
    { "colors_", NULL, 0x0, "LIOSObjectArray" },
    { "distribution_", NULL, 0x0, "LIOSFloatArray" },
    { "r0_", NULL, 0x0, "F" },
    { "r1_", NULL, 0x0, "F" },
    { "radial_", NULL, 0x0, "Z" },
    { "start_CanvasGradient_", "start", 0x0, "LRAREUIPoint" },
    { "end_CanvasGradient_", "end", 0x0, "LRAREUIPoint" },
  };
  static J2ObjcClassInfo _RARECanvasRenderingContext2D_CanvasGradient = { "CanvasGradient", "com.appnativa.rare.ui.canvas", "CanvasRenderingContext2D", 0x1, 2, methods, 8, fields, 0, NULL};
  return &_RARECanvasRenderingContext2D_CanvasGradient;
}

@end
@implementation RARECanvasRenderingContext2D_CanvasPattern

- (id)initWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                            withNSString:(NSString *)repetition {
  if (self = [super init]) {
    paint_ = nil;
    repeatY_ = YES;
    repeatX_ = YES;
    image_ = img;
    if ([@"no-repeat" isEqual:repetition]) {
      repeatX_ = NO;
      repeatY_ = NO;
    }
    else if ([@"repeat-x" isEqual:repetition]) {
      repeatX_ = YES;
    }
    else if ([@"repeat-y" isEqual:repetition]) {
      repeatY_ = YES;
    }
  }
  return self;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *ex) {
    @throw [[JavaLangRuntimeException alloc] initWithJavaLangThrowable:ex];
  }
}

- (id<RAREiContext_iCanvasPaint>)cloneCopy {
  return self;
}

- (id<RAREiPlatformPaint>)getPaint {
  if (paint_ == nil) {
    paint_ = [[RAREUIImagePainter alloc] initWithRAREUIImage:[((id<RAREiContext_iImageElement>) nil_chk(image_)) getImage] withInt:255 withRARERenderTypeEnum:[self getRenderType]];
  }
  return paint_;
}

- (BOOL)isRepeatX {
  return repeatX_;
}

- (BOOL)isRepeatXorY {
  return repeatX_ || repeatY_;
}

- (BOOL)isRepeatY {
  return repeatY_;
}

- (RARERenderTypeEnum *)getRenderType {
  if (repeatX_ && repeatY_) {
    return [RARERenderTypeEnum TILED];
  }
  if (repeatX_) {
    return [RARERenderTypeEnum HORIZONTAL_TILE];
  }
  if (repeatX_) {
    return [RARERenderTypeEnum VERTICAL_TILE];
  }
  return [RARERenderTypeEnum UPPER_LEFT];
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RARECanvasRenderingContext2D_CanvasPattern *)other {
  [super copyAllFieldsTo:other];
  other->image_ = image_;
  other->paint_ = paint_;
  other->repeatX_ = repeatX_;
  other->repeatY_ = repeatY_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "cloneCopy", NULL, "LRAREiContext_iCanvasPaint", 0x1, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x1, NULL },
    { "isRepeatX", NULL, "Z", 0x1, NULL },
    { "isRepeatXorY", NULL, "Z", 0x1, NULL },
    { "isRepeatY", NULL, "Z", 0x1, NULL },
    { "getRenderType", NULL, "LRARERenderTypeEnum", 0x0, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "paint_", NULL, 0x4, "LRAREUIImagePainter" },
    { "repeatY_", NULL, 0x0, "Z" },
    { "repeatX_", NULL, 0x0, "Z" },
    { "image_", NULL, 0x4, "LRAREiContext_iImageElement" },
  };
  static J2ObjcClassInfo _RARECanvasRenderingContext2D_CanvasPattern = { "CanvasPattern", "com.appnativa.rare.ui.canvas", "CanvasRenderingContext2D", 0x9, 7, methods, 4, fields, 0, NULL};
  return &_RARECanvasRenderingContext2D_CanvasPattern;
}

@end
@implementation RARECanvasRenderingContext2D_CompositingGraphics

- (id)initWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                    withRAREUIImage:(RAREUIImage *)img {
  return [super initWithRAREiPlatformGraphics:g withRAREUIImage:img];
}

- (id)getContextRef {
  return nil;
}

- (RAREView *)getView {
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getContextRef", NULL, "LNSObject", 0x1, NULL },
    { "getView", NULL, "LRAREView", 0x1, NULL },
  };
  static J2ObjcClassInfo _RARECanvasRenderingContext2D_CompositingGraphics = { "CompositingGraphics", "com.appnativa.rare.ui.canvas", "CanvasRenderingContext2D", 0x8, 2, methods, 0, NULL, 0, NULL};
  return &_RARECanvasRenderingContext2D_CompositingGraphics;
}

@end
