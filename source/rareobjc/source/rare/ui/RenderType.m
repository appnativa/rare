//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/RenderType.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/RenderType.h"
#include "java/lang/Exception.h"
#include "java/lang/IllegalArgumentException.h"
#include "java/util/Locale.h"


static RARERenderTypeEnum *RARERenderTypeEnum_XY;
static RARERenderTypeEnum *RARERenderTypeEnum_TILED;
static RARERenderTypeEnum *RARERenderTypeEnum_HORIZONTAL_TILE;
static RARERenderTypeEnum *RARERenderTypeEnum_VERTICAL_TILE;
static RARERenderTypeEnum *RARERenderTypeEnum_CENTERED;
static RARERenderTypeEnum *RARERenderTypeEnum_STRETCHED;
static RARERenderTypeEnum *RARERenderTypeEnum_STRETCH_WIDTH;
static RARERenderTypeEnum *RARERenderTypeEnum_STRETCH_HEIGHT;
static RARERenderTypeEnum *RARERenderTypeEnum_UPPER_LEFT;
static RARERenderTypeEnum *RARERenderTypeEnum_UPPER_RIGHT;
static RARERenderTypeEnum *RARERenderTypeEnum_LOWER_LEFT;
static RARERenderTypeEnum *RARERenderTypeEnum_LOWER_RIGHT;
static RARERenderTypeEnum *RARERenderTypeEnum_UPPER_MIDDLE;
static RARERenderTypeEnum *RARERenderTypeEnum_LOWER_MIDDLE;
static RARERenderTypeEnum *RARERenderTypeEnum_LEFT_MIDDLE;
static RARERenderTypeEnum *RARERenderTypeEnum_RIGHT_MIDDLE;
static RARERenderTypeEnum *RARERenderTypeEnum_STRETCH_HEIGHT_MIDDLE;
static RARERenderTypeEnum *RARERenderTypeEnum_STRETCH_WIDTH_MIDDLE;
IOSObjectArray *RARERenderTypeEnum_values;

@implementation RARERenderTypeEnum

+ (RARERenderTypeEnum *)XY {
  return RARERenderTypeEnum_XY;
}
+ (RARERenderTypeEnum *)TILED {
  return RARERenderTypeEnum_TILED;
}
+ (RARERenderTypeEnum *)HORIZONTAL_TILE {
  return RARERenderTypeEnum_HORIZONTAL_TILE;
}
+ (RARERenderTypeEnum *)VERTICAL_TILE {
  return RARERenderTypeEnum_VERTICAL_TILE;
}
+ (RARERenderTypeEnum *)CENTERED {
  return RARERenderTypeEnum_CENTERED;
}
+ (RARERenderTypeEnum *)STRETCHED {
  return RARERenderTypeEnum_STRETCHED;
}
+ (RARERenderTypeEnum *)STRETCH_WIDTH {
  return RARERenderTypeEnum_STRETCH_WIDTH;
}
+ (RARERenderTypeEnum *)STRETCH_HEIGHT {
  return RARERenderTypeEnum_STRETCH_HEIGHT;
}
+ (RARERenderTypeEnum *)UPPER_LEFT {
  return RARERenderTypeEnum_UPPER_LEFT;
}
+ (RARERenderTypeEnum *)UPPER_RIGHT {
  return RARERenderTypeEnum_UPPER_RIGHT;
}
+ (RARERenderTypeEnum *)LOWER_LEFT {
  return RARERenderTypeEnum_LOWER_LEFT;
}
+ (RARERenderTypeEnum *)LOWER_RIGHT {
  return RARERenderTypeEnum_LOWER_RIGHT;
}
+ (RARERenderTypeEnum *)UPPER_MIDDLE {
  return RARERenderTypeEnum_UPPER_MIDDLE;
}
+ (RARERenderTypeEnum *)LOWER_MIDDLE {
  return RARERenderTypeEnum_LOWER_MIDDLE;
}
+ (RARERenderTypeEnum *)LEFT_MIDDLE {
  return RARERenderTypeEnum_LEFT_MIDDLE;
}
+ (RARERenderTypeEnum *)RIGHT_MIDDLE {
  return RARERenderTypeEnum_RIGHT_MIDDLE;
}
+ (RARERenderTypeEnum *)STRETCH_HEIGHT_MIDDLE {
  return RARERenderTypeEnum_STRETCH_HEIGHT_MIDDLE;
}
+ (RARERenderTypeEnum *)STRETCH_WIDTH_MIDDLE {
  return RARERenderTypeEnum_STRETCH_WIDTH_MIDDLE;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

+ (RARERenderTypeEnum *)valueOfExWithNSString:(NSString *)name {
  if (name == nil) {
    return nil;
  }
  @try {
    return [RARERenderTypeEnum valueOfWithNSString:[((NSString *) nil_chk(name)) uppercaseStringWithJRELocale:[JavaUtilLocale US]]];
  }
  @catch (JavaLangException *e) {
    return nil;
  }
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RARERenderTypeEnum class]) {
    RARERenderTypeEnum_XY = [[RARERenderTypeEnum alloc] initWithNSString:@"XY" withInt:0];
    RARERenderTypeEnum_TILED = [[RARERenderTypeEnum alloc] initWithNSString:@"TILED" withInt:1];
    RARERenderTypeEnum_HORIZONTAL_TILE = [[RARERenderTypeEnum alloc] initWithNSString:@"HORIZONTAL_TILE" withInt:2];
    RARERenderTypeEnum_VERTICAL_TILE = [[RARERenderTypeEnum alloc] initWithNSString:@"VERTICAL_TILE" withInt:3];
    RARERenderTypeEnum_CENTERED = [[RARERenderTypeEnum alloc] initWithNSString:@"CENTERED" withInt:4];
    RARERenderTypeEnum_STRETCHED = [[RARERenderTypeEnum alloc] initWithNSString:@"STRETCHED" withInt:5];
    RARERenderTypeEnum_STRETCH_WIDTH = [[RARERenderTypeEnum alloc] initWithNSString:@"STRETCH_WIDTH" withInt:6];
    RARERenderTypeEnum_STRETCH_HEIGHT = [[RARERenderTypeEnum alloc] initWithNSString:@"STRETCH_HEIGHT" withInt:7];
    RARERenderTypeEnum_UPPER_LEFT = [[RARERenderTypeEnum alloc] initWithNSString:@"UPPER_LEFT" withInt:8];
    RARERenderTypeEnum_UPPER_RIGHT = [[RARERenderTypeEnum alloc] initWithNSString:@"UPPER_RIGHT" withInt:9];
    RARERenderTypeEnum_LOWER_LEFT = [[RARERenderTypeEnum alloc] initWithNSString:@"LOWER_LEFT" withInt:10];
    RARERenderTypeEnum_LOWER_RIGHT = [[RARERenderTypeEnum alloc] initWithNSString:@"LOWER_RIGHT" withInt:11];
    RARERenderTypeEnum_UPPER_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"UPPER_MIDDLE" withInt:12];
    RARERenderTypeEnum_LOWER_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"LOWER_MIDDLE" withInt:13];
    RARERenderTypeEnum_LEFT_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"LEFT_MIDDLE" withInt:14];
    RARERenderTypeEnum_RIGHT_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"RIGHT_MIDDLE" withInt:15];
    RARERenderTypeEnum_STRETCH_HEIGHT_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"STRETCH_HEIGHT_MIDDLE" withInt:16];
    RARERenderTypeEnum_STRETCH_WIDTH_MIDDLE = [[RARERenderTypeEnum alloc] initWithNSString:@"STRETCH_WIDTH_MIDDLE" withInt:17];
    RARERenderTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RARERenderTypeEnum_XY, RARERenderTypeEnum_TILED, RARERenderTypeEnum_HORIZONTAL_TILE, RARERenderTypeEnum_VERTICAL_TILE, RARERenderTypeEnum_CENTERED, RARERenderTypeEnum_STRETCHED, RARERenderTypeEnum_STRETCH_WIDTH, RARERenderTypeEnum_STRETCH_HEIGHT, RARERenderTypeEnum_UPPER_LEFT, RARERenderTypeEnum_UPPER_RIGHT, RARERenderTypeEnum_LOWER_LEFT, RARERenderTypeEnum_LOWER_RIGHT, RARERenderTypeEnum_UPPER_MIDDLE, RARERenderTypeEnum_LOWER_MIDDLE, RARERenderTypeEnum_LEFT_MIDDLE, RARERenderTypeEnum_RIGHT_MIDDLE, RARERenderTypeEnum_STRETCH_HEIGHT_MIDDLE, RARERenderTypeEnum_STRETCH_WIDTH_MIDDLE, nil } count:18 type:[IOSClass classWithClass:[RARERenderTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RARERenderTypeEnum_values];
}

+ (RARERenderTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RARERenderTypeEnum_values count]; i++) {
    RARERenderTypeEnum *e = RARERenderTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "valueOfExWithNSString:", NULL, "LRARERenderTypeEnum", 0x9, NULL },
  };
  static const char *superclass_type_args[] = {"LRARERenderTypeEnum"};
  static J2ObjcClassInfo _RARERenderTypeEnum = { "RenderType", "com.appnativa.rare.ui", NULL, 0x4011, 1, methods, 0, NULL, 1, superclass_type_args};
  return &_RARERenderTypeEnum;
}

@end