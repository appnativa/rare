//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/painter/iGradientPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSFloatArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/painter/iGradientPainter.h"
#include "java/lang/IllegalArgumentException.h"


@interface RAREiGradientPainter : NSObject
@end

@implementation RAREiGradientPainter

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setGradientDirectionWithRAREiGradientPainter_DirectionEnum:", NULL, "V", 0x401, NULL },
    { "setGradientMagnitudeWithInt:", NULL, "V", 0x401, NULL },
    { "setGradientPaintWithRAREUIColor:withRAREUIColor:withRAREiGradientPainter_DirectionEnum:", NULL, "V", 0x401, NULL },
    { "setGradientPaintWithRAREiGradientPainter_TypeEnum:withRAREiGradientPainter_DirectionEnum:withRAREUIColorArray:withFloatArray:withInt:", NULL, "V", 0x401, NULL },
    { "setGradientPaintEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setGradientTypeWithRAREiGradientPainter_TypeEnum:", NULL, "V", 0x401, NULL },
    { "getGradientColorCount", NULL, "I", 0x401, NULL },
    { "getGradientColors", NULL, "LIOSObjectArray", 0x401, NULL },
    { "getGradientColorsWithRAREUIColor:", NULL, "LIOSObjectArray", 0x401, NULL },
    { "getGradientColorsWithRAREUIColorArray:withRAREUIColor:", NULL, "V", 0x401, NULL },
    { "getGradientDirection", NULL, "LRAREiGradientPainter_DirectionEnum", 0x401, NULL },
    { "getGradientDistribution", NULL, "LIOSFloatArray", 0x401, NULL },
    { "getGradientEndColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x401, NULL },
    { "getGradientMagnitude", NULL, "I", 0x401, NULL },
    { "getGradientStartColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x401, NULL },
    { "getGradientType", NULL, "LRAREiGradientPainter_TypeEnum", 0x401, NULL },
    { "isGradientPaintEnabled", NULL, "Z", 0x401, NULL },
    { "setAlwaysFillWithBoolean:", NULL, "V", 0x401, NULL },
    { "setGradientRadiusWithFloat:", NULL, "V", 0x401, NULL },
    { "getGradientRadius", NULL, "F", 0x401, NULL },
    { "isAlwaysFill", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiGradientPainter = { "iGradientPainter", "com.appnativa.rare.ui.painter", NULL, 0x201, 21, methods, 0, NULL, 0, NULL};
  return &_RAREiGradientPainter;
}

@end

static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_VERTICAL_TOP;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_VERTICAL_BOTTOM;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_HORIZONTAL_LEFT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_HORIZONTAL_RIGHT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_LEFT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_RIGHT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_LEFT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_RIGHT;
static RAREiGradientPainter_DirectionEnum *RAREiGradientPainter_DirectionEnum_CENTER;
IOSObjectArray *RAREiGradientPainter_DirectionEnum_values;

@implementation RAREiGradientPainter_DirectionEnum

+ (RAREiGradientPainter_DirectionEnum *)VERTICAL_TOP {
  return RAREiGradientPainter_DirectionEnum_VERTICAL_TOP;
}
+ (RAREiGradientPainter_DirectionEnum *)VERTICAL_BOTTOM {
  return RAREiGradientPainter_DirectionEnum_VERTICAL_BOTTOM;
}
+ (RAREiGradientPainter_DirectionEnum *)HORIZONTAL_LEFT {
  return RAREiGradientPainter_DirectionEnum_HORIZONTAL_LEFT;
}
+ (RAREiGradientPainter_DirectionEnum *)HORIZONTAL_RIGHT {
  return RAREiGradientPainter_DirectionEnum_HORIZONTAL_RIGHT;
}
+ (RAREiGradientPainter_DirectionEnum *)DIAGONAL_TOP_LEFT {
  return RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_LEFT;
}
+ (RAREiGradientPainter_DirectionEnum *)DIAGONAL_TOP_RIGHT {
  return RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_RIGHT;
}
+ (RAREiGradientPainter_DirectionEnum *)DIAGONAL_BOTTOM_LEFT {
  return RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_LEFT;
}
+ (RAREiGradientPainter_DirectionEnum *)DIAGONAL_BOTTOM_RIGHT {
  return RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_RIGHT;
}
+ (RAREiGradientPainter_DirectionEnum *)CENTER {
  return RAREiGradientPainter_DirectionEnum_CENTER;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiGradientPainter_DirectionEnum class]) {
    RAREiGradientPainter_DirectionEnum_VERTICAL_TOP = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"VERTICAL_TOP" withInt:0];
    RAREiGradientPainter_DirectionEnum_VERTICAL_BOTTOM = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"VERTICAL_BOTTOM" withInt:1];
    RAREiGradientPainter_DirectionEnum_HORIZONTAL_LEFT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"HORIZONTAL_LEFT" withInt:2];
    RAREiGradientPainter_DirectionEnum_HORIZONTAL_RIGHT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"HORIZONTAL_RIGHT" withInt:3];
    RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_LEFT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"DIAGONAL_TOP_LEFT" withInt:4];
    RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_RIGHT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"DIAGONAL_TOP_RIGHT" withInt:5];
    RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_LEFT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"DIAGONAL_BOTTOM_LEFT" withInt:6];
    RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_RIGHT = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"DIAGONAL_BOTTOM_RIGHT" withInt:7];
    RAREiGradientPainter_DirectionEnum_CENTER = [[RAREiGradientPainter_DirectionEnum alloc] initWithNSString:@"CENTER" withInt:8];
    RAREiGradientPainter_DirectionEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiGradientPainter_DirectionEnum_VERTICAL_TOP, RAREiGradientPainter_DirectionEnum_VERTICAL_BOTTOM, RAREiGradientPainter_DirectionEnum_HORIZONTAL_LEFT, RAREiGradientPainter_DirectionEnum_HORIZONTAL_RIGHT, RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_LEFT, RAREiGradientPainter_DirectionEnum_DIAGONAL_TOP_RIGHT, RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_LEFT, RAREiGradientPainter_DirectionEnum_DIAGONAL_BOTTOM_RIGHT, RAREiGradientPainter_DirectionEnum_CENTER, nil } count:9 type:[IOSClass classWithClass:[RAREiGradientPainter_DirectionEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiGradientPainter_DirectionEnum_values];
}

+ (RAREiGradientPainter_DirectionEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiGradientPainter_DirectionEnum_values count]; i++) {
    RAREiGradientPainter_DirectionEnum *e = RAREiGradientPainter_DirectionEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiGradientPainter_DirectionEnum"};
  static J2ObjcClassInfo _RAREiGradientPainter_DirectionEnum = { "Direction", "com.appnativa.rare.ui.painter", "iGradientPainter", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiGradientPainter_DirectionEnum;
}

@end

static RAREiGradientPainter_TypeEnum *RAREiGradientPainter_TypeEnum_LINEAR;
static RAREiGradientPainter_TypeEnum *RAREiGradientPainter_TypeEnum_LINEAR_REPEAT;
static RAREiGradientPainter_TypeEnum *RAREiGradientPainter_TypeEnum_LINEAR_REFLECT;
static RAREiGradientPainter_TypeEnum *RAREiGradientPainter_TypeEnum_RADIAL;
IOSObjectArray *RAREiGradientPainter_TypeEnum_values;

@implementation RAREiGradientPainter_TypeEnum

+ (RAREiGradientPainter_TypeEnum *)LINEAR {
  return RAREiGradientPainter_TypeEnum_LINEAR;
}
+ (RAREiGradientPainter_TypeEnum *)LINEAR_REPEAT {
  return RAREiGradientPainter_TypeEnum_LINEAR_REPEAT;
}
+ (RAREiGradientPainter_TypeEnum *)LINEAR_REFLECT {
  return RAREiGradientPainter_TypeEnum_LINEAR_REFLECT;
}
+ (RAREiGradientPainter_TypeEnum *)RADIAL {
  return RAREiGradientPainter_TypeEnum_RADIAL;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREiGradientPainter_TypeEnum class]) {
    RAREiGradientPainter_TypeEnum_LINEAR = [[RAREiGradientPainter_TypeEnum alloc] initWithNSString:@"LINEAR" withInt:0];
    RAREiGradientPainter_TypeEnum_LINEAR_REPEAT = [[RAREiGradientPainter_TypeEnum alloc] initWithNSString:@"LINEAR_REPEAT" withInt:1];
    RAREiGradientPainter_TypeEnum_LINEAR_REFLECT = [[RAREiGradientPainter_TypeEnum alloc] initWithNSString:@"LINEAR_REFLECT" withInt:2];
    RAREiGradientPainter_TypeEnum_RADIAL = [[RAREiGradientPainter_TypeEnum alloc] initWithNSString:@"RADIAL" withInt:3];
    RAREiGradientPainter_TypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREiGradientPainter_TypeEnum_LINEAR, RAREiGradientPainter_TypeEnum_LINEAR_REPEAT, RAREiGradientPainter_TypeEnum_LINEAR_REFLECT, RAREiGradientPainter_TypeEnum_RADIAL, nil } count:4 type:[IOSClass classWithClass:[RAREiGradientPainter_TypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREiGradientPainter_TypeEnum_values];
}

+ (RAREiGradientPainter_TypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREiGradientPainter_TypeEnum_values count]; i++) {
    RAREiGradientPainter_TypeEnum *e = RAREiGradientPainter_TypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREiGradientPainter_TypeEnum"};
  static J2ObjcClassInfo _RAREiGradientPainter_TypeEnum = { "Type", "com.appnativa.rare.ui.painter", "iGradientPainter", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREiGradientPainter_TypeEnum;
}

@end
