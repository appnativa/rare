//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/RenderType.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARERenderType_H_
#define _RARERenderType_H_

#import "JreEmulation.h"
#include "java/lang/Enum.h"

typedef enum {
  RARERenderType_XY = 0,
  RARERenderType_TILED = 1,
  RARERenderType_HORIZONTAL_TILE = 2,
  RARERenderType_VERTICAL_TILE = 3,
  RARERenderType_CENTERED = 4,
  RARERenderType_STRETCHED = 5,
  RARERenderType_STRETCH_WIDTH = 6,
  RARERenderType_STRETCH_HEIGHT = 7,
  RARERenderType_UPPER_LEFT = 8,
  RARERenderType_UPPER_RIGHT = 9,
  RARERenderType_LOWER_LEFT = 10,
  RARERenderType_LOWER_RIGHT = 11,
  RARERenderType_UPPER_MIDDLE = 12,
  RARERenderType_LOWER_MIDDLE = 13,
  RARERenderType_LEFT_MIDDLE = 14,
  RARERenderType_RIGHT_MIDDLE = 15,
  RARERenderType_STRETCH_HEIGHT_MIDDLE = 16,
  RARERenderType_STRETCH_WIDTH_MIDDLE = 17,
} RARERenderType;

@interface RARERenderTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderTypeEnum *)XY;
+ (RARERenderTypeEnum *)TILED;
+ (RARERenderTypeEnum *)HORIZONTAL_TILE;
+ (RARERenderTypeEnum *)VERTICAL_TILE;
+ (RARERenderTypeEnum *)CENTERED;
+ (RARERenderTypeEnum *)STRETCHED;
+ (RARERenderTypeEnum *)STRETCH_WIDTH;
+ (RARERenderTypeEnum *)STRETCH_HEIGHT;
+ (RARERenderTypeEnum *)UPPER_LEFT;
+ (RARERenderTypeEnum *)UPPER_RIGHT;
+ (RARERenderTypeEnum *)LOWER_LEFT;
+ (RARERenderTypeEnum *)LOWER_RIGHT;
+ (RARERenderTypeEnum *)UPPER_MIDDLE;
+ (RARERenderTypeEnum *)LOWER_MIDDLE;
+ (RARERenderTypeEnum *)LEFT_MIDDLE;
+ (RARERenderTypeEnum *)RIGHT_MIDDLE;
+ (RARERenderTypeEnum *)STRETCH_HEIGHT_MIDDLE;
+ (RARERenderTypeEnum *)STRETCH_WIDTH_MIDDLE;
+ (IOSObjectArray *)values;
+ (RARERenderTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
+ (RARERenderTypeEnum *)valueOfExWithNSString:(NSString *)name;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RARERenderType_H_
