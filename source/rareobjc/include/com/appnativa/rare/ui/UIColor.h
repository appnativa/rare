//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIColor.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREUIColor_H_
#define _RAREUIColor_H_

@protocol RAREiBackgroundPainter;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"

#define RAREUIColor_FACTOR 0.7

@interface RAREUIColor : NSObject < RAREiPlatformPaint, NSCopying > {
 @public
  int color_;
  id proxy_;
}

+ (double)FACTOR;
+ (RAREUIColor *)YELLOW;
+ (RAREUIColor *)WHITE;
+ (RAREUIColor *)TRANSPARENT;
+ (RAREUIColor *)RED;
+ (RAREUIColor *)PINK;
+ (RAREUIColor *)ORANGE;
+ (RAREUIColor *)MAGENTA;
+ (RAREUIColor *)LIGHTGRAY;
+ (RAREUIColor *)GREEN;
+ (RAREUIColor *)GRAY;
+ (RAREUIColor *)DARKGRAY;
+ (RAREUIColor *)CYAN;
+ (RAREUIColor *)BLUE;
+ (RAREUIColor *)BLACK;
- (id)initWithInt:(int)rgba;
- (id)initWithId:(id)proxy;
- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (id)initWithInt:(int)r
          withInt:(int)g
          withInt:(int)b;
- (id)initWithInt:(int)r
          withInt:(int)g
          withInt:(int)b
          withInt:(int)a;
- (void)addToAttributedStringWithId:(id)astring
                            withInt:(int)offset
                            withInt:(int)length;
- (RAREUIColor *)alphaWithInt:(int)alpha;
- (RAREUIColor *)brighter;
- (RAREUIColor *)brighterBrighter;
- (RAREUIColor *)darker;
- (RAREUIColor *)darkerDarker;
- (BOOL)isEqual:(id)o;
- (id)clone;
- (NSUInteger)hash;
- (RAREUIColor *)lightWithInt:(int)lum;
- (int)lightExWithInt:(int)lum;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
- (NSString *)toCSSString;
- (NSString *)description;
- (NSString *)toHexString;
- (id)getAPColor;
- (int)getARGB;
- (RAREUIColor *)getDisabledColorWithRAREUIColor:(RAREUIColor *)def;
- (int)getAlpha;
- (int)getBlue;
- (id)getCGColor;
- (int)getColor;
- (int)getGreen;
- (RAREUIColor *)getPlatformPaintColor;
- (int)getRGB;
- (int)getRed;
- (BOOL)isDarkColor;
- (BOOL)isDynamic;
- (BOOL)isPainter;
- (BOOL)isSimpleColor;
- (int)getIntColorWithId:(id)proxy;
- (id<RAREiBackgroundPainter>)getPainter;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREUIColor *)other;
@end

J2OBJC_FIELD_SETTER(RAREUIColor, proxy_, id)

typedef RAREUIColor ComAppnativaRareUiUIColor;

#endif // _RAREUIColor_H_
