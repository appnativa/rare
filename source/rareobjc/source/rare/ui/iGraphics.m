//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iGraphics.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iComposite.h"
#include "com/appnativa/rare/ui/iGraphics.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformImage.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/rare/ui/iTransform.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "java/lang/IllegalArgumentException.h"


@interface RAREiGraphics : NSObject
@end

@implementation RAREiGraphics

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "clearRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "clearRectWithRAREUIColor:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "clipRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "clipRectWithFloat:withFloat:withFloat:withFloat:withRAREiGraphics_OpEnum:", NULL, "V", 0x401, NULL },
    { "clipShapeWithRAREiPlatformShape:", NULL, "V", 0x401, NULL },
    { "clipShapeWithRAREiPlatformShape:withRAREiGraphics_OpEnum:", NULL, "V", 0x401, NULL },
    { "didComponentPainterClip", NULL, "Z", 0x401, NULL },
    { "dispose", NULL, "V", 0x401, NULL },
    { "drawCharsWithCharArray:withInt:withInt:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:withRAREiComposite:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiPlatformImage:withRAREUIRectangle:withRAREUIRectangle:withRAREiComposite:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawImageWithRAREiPlatformImage:withRAREUIRectangle:withRAREUIRectangle:withRAREiImagePainter_ScalingTypeEnum:withRAREiComposite:", NULL, "V", 0x401, NULL },
    { "drawLineWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawRoundRectWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawShapeWithRAREiPlatformShape:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "drawStringWithNSString:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillRoundRectWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "fillShapeWithRAREiPlatformShape:withFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "restoreState", NULL, "V", 0x401, NULL },
    { "restoreToStateWithInt:", NULL, "V", 0x401, NULL },
    { "rotateWithInt:", NULL, "V", 0x401, NULL },
    { "saveState", NULL, "I", 0x401, NULL },
    { "scale__WithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x401, NULL },
    { "setColorWithRAREUIColor:", NULL, "V", 0x401, NULL },
    { "setComponentPainterClippedWithBoolean:", NULL, "V", 0x401, NULL },
    { "setCompositeWithRAREiComposite:", NULL, "V", 0x401, NULL },
    { "setFontWithRAREUIFont:", NULL, "V", 0x401, NULL },
    { "setPaintWithRAREiPlatformPaint:", NULL, "V", 0x401, NULL },
    { "setRenderingOptionsWithBoolean:withBoolean:", NULL, "V", 0x401, NULL },
    { "setRotationWithInt:", NULL, "V", 0x401, NULL },
    { "setStrokeWithRAREUIStroke:", NULL, "V", 0x401, NULL },
    { "setStrokeWidthWithFloat:", NULL, "V", 0x401, NULL },
    { "setTransformWithRAREiTransform:", NULL, "V", 0x401, NULL },
    { "getClip", NULL, "LRAREiPlatformShape", 0x401, NULL },
    { "getClipBounds", NULL, "LRAREUIRectangle", 0x401, NULL },
    { "getColor", NULL, "LRAREUIColor", 0x401, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x401, NULL },
    { "getComposite", NULL, "LRAREiComposite", 0x401, NULL },
    { "getFont", NULL, "LRAREUIFont", 0x401, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x401, NULL },
    { "getRotation", NULL, "I", 0x401, NULL },
    { "getStroke", NULL, "LRAREUIStroke", 0x401, NULL },
    { "getStrokeWidth", NULL, "F", 0x401, NULL },
    { "getTransform", NULL, "LRAREiTransform", 0x401, NULL },
    { "isDisposed", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiGraphics = { "iGraphics", "com.appnativa.rare.ui", NULL, 0x201, 50, methods, 0, NULL, 0, NULL};
  return &_RAREiGraphics;
}

@end

static RAREiGraphics_OpEnum *RAREiGraphics_OpEnum_DIFFERENCE;
static RAREiGraphics_OpEnum *RAREiGraphics_OpEnum_INTERSECT;
static RAREiGraphics_OpEnum *RAREiGraphics_OpEnum_REPLACE;
static RAREiGraphics_OpEnum *RAREiGraphics_OpEnum_UNION;
IOSObjectArray *RAREiGraphics_OpEnum_values;

@implementation RAREiGraphics_OpEnum

+ (RAREiGraphics_OpEnum *)DIFFERENCE {
  return RAREiGraphics_OpEnum_DIFFERENCE;
}
+ (RAREiGraphics_OpEnum *)INTERSECT {
  return RAREiGraphics_OpEnum_INTERSECT;
}
+ (RAREiGraphics_OpEnum *)REPLACE {
  return RAREiGraphics_OpEnum_REPLACE;
}
+ (RAREiGraphics_OpEnum *)UNION {
  return RAREiGraphics_OpEnum_UNION;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiGraphics_OpEnum class]) {
    RAREiGraphics_OpEnum_DIFFERENCE = [[RAREiGraphics_OpEnum alloc] initWithNSString:@"DIFFERENCE" withInt:0];
    RAREiGraphics_OpEnum_INTERSECT = [[RAREiGraphics_OpEnum alloc] initWithNSString:@"INTERSECT" withInt:1];
    RAREiGraphics_OpEnum_REPLACE = [[RAREiGraphics_OpEnum alloc] initWithNSString:@"REPLACE" withInt:2];
    RAREiGraphics_OpEnum_UNION = [[RAREiGraphics_OpEnum alloc] initWithNSString:@"UNION" withInt:3];
    RAREiGraphics_OpEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiGraphics_OpEnum_DIFFERENCE, RAREiGraphics_OpEnum_INTERSECT, RAREiGraphics_OpEnum_REPLACE, RAREiGraphics_OpEnum_UNION, nil } count:4 type:[IOSClass classWithClass:[RAREiGraphics_OpEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiGraphics_OpEnum_values];
}

+ (RAREiGraphics_OpEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiGraphics_OpEnum_values count]; i++) {
    RAREiGraphics_OpEnum *e = RAREiGraphics_OpEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiGraphics_OpEnum"};
  static J2ObjcClassInfo _RAREiGraphics_OpEnum = { "Op", "com.appnativa.rare.ui", "iGraphics", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiGraphics_OpEnum;
}

@end
