//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIColor.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/converters/Conversions.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/UISimpleBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "java/lang/CloneNotSupportedException.h"
#include "java/lang/InternalError.h"
#include "java/lang/StringBuilder.h"
#import "AppleHelper.h"

@implementation RAREUIColor

static RAREUIColor * RAREUIColor_YELLOW_;
static RAREUIColor * RAREUIColor_WHITE_;
static RAREUIColor * RAREUIColor_TRANSPARENT_;
static RAREUIColor * RAREUIColor_RED_;
static RAREUIColor * RAREUIColor_PINK_;
static RAREUIColor * RAREUIColor_ORANGE_;
static RAREUIColor * RAREUIColor_MAGENTA_;
static RAREUIColor * RAREUIColor_LIGHTGRAY_;
static RAREUIColor * RAREUIColor_GREEN_;
static RAREUIColor * RAREUIColor_GRAY_;
static RAREUIColor * RAREUIColor_DARKGRAY_;
static RAREUIColor * RAREUIColor_CYAN_;
static RAREUIColor * RAREUIColor_BLUE_;
static RAREUIColor * RAREUIColor_BLACK_;

+ (double)FACTOR {
  return RAREUIColor_FACTOR;
}

+ (RAREUIColor *)YELLOW {
  return RAREUIColor_YELLOW_;
}

+ (RAREUIColor *)WHITE {
  return RAREUIColor_WHITE_;
}

+ (RAREUIColor *)TRANSPARENT {
  return RAREUIColor_TRANSPARENT_;
}

+ (RAREUIColor *)RED {
  return RAREUIColor_RED_;
}

+ (RAREUIColor *)PINK {
  return RAREUIColor_PINK_;
}

+ (RAREUIColor *)ORANGE {
  return RAREUIColor_ORANGE_;
}

+ (RAREUIColor *)MAGENTA {
  return RAREUIColor_MAGENTA_;
}

+ (RAREUIColor *)LIGHTGRAY {
  return RAREUIColor_LIGHTGRAY_;
}

+ (RAREUIColor *)GREEN {
  return RAREUIColor_GREEN_;
}

+ (RAREUIColor *)GRAY {
  return RAREUIColor_GRAY_;
}

+ (RAREUIColor *)DARKGRAY {
  return RAREUIColor_DARKGRAY_;
}

+ (RAREUIColor *)CYAN {
  return RAREUIColor_CYAN_;
}

+ (RAREUIColor *)BLUE {
  return RAREUIColor_BLUE_;
}

+ (RAREUIColor *)BLACK {
  return RAREUIColor_BLACK_;
}

- (id)initWithInt:(int)rgba {
  if (self = [super init]) {
    self->color_ = rgba;
  }
  return self;
}

- (id)initWithId:(id)proxy {
  if (self = [super init]) {
    self->proxy_ = proxy;
    self->color_ = [self getIntColorWithId:proxy];
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color {
  if (self = [super init]) {
    self->color_ = ((RAREUIColor *) nil_chk(color))->color_;
    self->proxy_ = color->proxy_;
  }
  return self;
}

- (id)initWithInt:(int)r
          withInt:(int)g
          withInt:(int)b {
  if (self = [super init]) {
    color_ = ((int) 0xFF << 24) | (r << 16) | (g << 8) | b;
  }
  return self;
}

- (id)initWithInt:(int)r
          withInt:(int)g
          withInt:(int)b
          withInt:(int)a {
  if (self = [super init]) {
    color_ = (a << 24) | (r << 16) | (g << 8) | b;
  }
  return self;
}

- (void)addToAttributedStringWithId:(id)astring
                            withInt:(int)offset
                            withInt:(int)length {
  NSMutableAttributedString* s=(NSMutableAttributedString*)astring;
  [s addAttribute: NSForegroundColorAttributeName
  value: [self getAPColor]
  range: NSMakeRange(offset,length)];
}

- (RAREUIColor *)alphaWithInt:(int)alpha {
  if (alpha == [self getAlpha]) {
    return self;
  }
  return [[RAREUIColor alloc] initWithInt:[self getRed] withInt:[self getGreen] withInt:[self getBlue] withInt:alpha];
}

- (RAREUIColor *)brighter {
  return [[RAREUIColor alloc] initWithInt:[RAREColorUtils brighterWithInt:[self getColor]]];
}

- (RAREUIColor *)brighterBrighter {
  return [[RAREUIColor alloc] initWithInt:[RAREColorUtils brighterWithInt:[RAREColorUtils brighterWithInt:[self getColor]]]];
}

- (RAREUIColor *)darker {
  return [[RAREUIColor alloc] initWithInt:[RAREColorUtils darkerWithInt:[self getColor]]];
}

- (RAREUIColor *)darkerDarker {
  return [[RAREUIColor alloc] initWithInt:[RAREColorUtils darkerWithInt:[RAREColorUtils darkerWithInt:[self getColor]]]];
}

- (BOOL)isEqual:(id)o {
  if (o == self) {
    return YES;
  }
  if ([o isKindOfClass:[RAREUIColorShade class]]) {
    return NO;
  }
  if ([o isKindOfClass:[RAREUIColor class]]) {
    return color_ == ((RAREUIColor *) nil_chk(o))->color_;
  }
  return NO;
}

- (id)clone {
  @try {
    return [super clone];
  }
  @catch (JavaLangCloneNotSupportedException *e) {
    @throw [[JavaLangInternalError alloc] init];
  }
}

- (NSUInteger)hash {
  return color_;
}

- (RAREUIColor *)lightWithInt:(int)lum {
  return [[RAREUIColor alloc] initWithInt:[RAREColorUtils adjustLuminanceWithInt:[self getColor] withInt:lum]];
}

- (int)lightExWithInt:(int)lum {
  return [RAREColorUtils adjustLuminanceWithInt:[self getColor] withInt:lum];
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation {
  RAREUIColor *c = [((id<RAREiPlatformGraphics>) nil_chk(g)) getColor];
  [g setColorWithRAREUIColor:self];
  [g fillRectWithFloat:x withFloat:y withFloat:width withFloat:height];
  [g setColorWithRAREUIColor:c];
}

- (NSString *)toCSSString {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] init];
  (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"color"])) appendWithNSString:@": "];
  if ([self getAlpha] == 0) {
    (void) [sb appendWithNSString:@"transparent;"];
  }
  else {
    (void) [RAREConversions colorToHEXStringWithJavaLangStringBuilder:sb withRAREUIColor:self];
    (void) [sb appendWithNSString:@";"];
  }
  return [sb description];
}

- (NSString *)description {
  return [RAREConversions colorToHEXStringWithRAREUIColor:self];
}

- (NSString *)toHexString {
  return [RAREConversions colorToHEXStringWithRAREUIColor:self];
}

- (id)getAPColor {
  if (proxy_ == nil) {
    #if TARGET_OS_IPHONE
    proxy_ = [UIColor colorWithRed:[self getRed]/255.0
    green:[self getGreen]/255.0
    blue:[self getBlue]/255.0
    alpha:[self getAlpha]/255.0];
    #else
    proxy_ = [NSColor colorWithCalibratedRed:[self getRed]/255.0
    green:[self getGreen]/255.0
    blue:[self getBlue]/255.0
    alpha:[self getAlpha]/255.0];
    #endif
  }
  return proxy_;
}

- (int)getARGB {
  return [self getColor];
}

- (RAREUIColor *)getDisabledColorWithRAREUIColor:(RAREUIColor *)def {
  return def;
}

- (int)getAlpha {
  return (int) (((unsigned int) [self getColor]) >> 24);
}

- (int)getBlue {
  return [self getColor] & (int) 0xFF;
}

- (id)getCGColor {
  [self getAPColor];
  #if TARGET_OS_IPHONE
  return (id)((UIColor*)proxy_).CGColor;
  #else
  return (id)((NSColor*)proxy_).CGColor;
  #endif
}

- (int)getColor {
  return color_;
}

- (int)getGreen {
  return ([self getColor] >> 8) & (int) 0xFF;
}

- (RAREUIColor *)getPlatformPaintColor {
  return self;
}

- (int)getRGB {
  return [self getColor];
}

- (int)getRed {
  return ([self getColor] >> 16) & (int) 0xFF;
}

- (BOOL)isDarkColor {
  return ([self getRed] + [self getGreen] + [self getBlue]) / 3 < 128;
}

- (BOOL)isDynamic {
  return NO;
}

- (BOOL)isPainter {
  return NO;
}

- (BOOL)isSimpleColor {
  return YES;
}

- (int)getIntColorWithId:(id)proxy {
  #if TARGET_OS_IPHONE
  return [AppleHelper colorToInt:(UIColor*)proxy];
  #else
  return [AppleHelper colorToInt:(NSColor*)proxy];
  #endif
}

- (id<RAREiBackgroundPainter>)getPainter {
  return [[RAREUISimpleBackgroundPainter alloc] initWithRAREUIColor:self];
}

+ (void)initialize {
  if (self == [RAREUIColor class]) {
    RAREUIColor_YELLOW_ = [[RAREUIColor alloc] initWithInt:255 withInt:255 withInt:0];
    RAREUIColor_WHITE_ = [[RAREUIColor alloc] initWithInt:255 withInt:255 withInt:255];
    RAREUIColor_TRANSPARENT_ = [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:0 withInt:0];
    RAREUIColor_RED_ = [[RAREUIColor alloc] initWithInt:255 withInt:0 withInt:0];
    RAREUIColor_PINK_ = [[RAREUIColor alloc] initWithInt:255 withInt:175 withInt:175];
    RAREUIColor_ORANGE_ = [[RAREUIColor alloc] initWithInt:255 withInt:200 withInt:0];
    RAREUIColor_MAGENTA_ = [[RAREUIColor alloc] initWithInt:255 withInt:0 withInt:255];
    RAREUIColor_LIGHTGRAY_ = [[RAREUIColor alloc] initWithInt:192 withInt:192 withInt:192];
    RAREUIColor_GREEN_ = [[RAREUIColor alloc] initWithInt:0 withInt:255 withInt:0];
    RAREUIColor_GRAY_ = [[RAREUIColor alloc] initWithInt:128 withInt:128 withInt:128];
    RAREUIColor_DARKGRAY_ = [[RAREUIColor alloc] initWithInt:64 withInt:64 withInt:64];
    RAREUIColor_CYAN_ = [[RAREUIColor alloc] initWithInt:0 withInt:255 withInt:255];
    RAREUIColor_BLUE_ = [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:255];
    RAREUIColor_BLACK_ = [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:0];
  }
}

- (id)copyWithZone:(NSZone *)zone {
  return [self clone];
}

- (void)copyAllFieldsTo:(RAREUIColor *)other {
  [super copyAllFieldsTo:other];
  other->color_ = color_;
  other->proxy_ = proxy_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addToAttributedStringWithId:withInt:withInt:", NULL, "V", 0x101, NULL },
    { "alphaWithInt:", NULL, "LRAREUIColor", 0x1, NULL },
    { "brighter", NULL, "LRAREUIColor", 0x1, NULL },
    { "brighterBrighter", NULL, "LRAREUIColor", 0x1, NULL },
    { "darker", NULL, "LRAREUIColor", 0x1, NULL },
    { "darkerDarker", NULL, "LRAREUIColor", 0x1, NULL },
    { "clone", NULL, "LNSObject", 0x1, NULL },
    { "lightWithInt:", NULL, "LRAREUIColor", 0x1, NULL },
    { "toCSSString", NULL, "LNSString", 0x1, NULL },
    { "toHexString", NULL, "LNSString", 0x1, NULL },
    { "getAPColor", NULL, "LNSObject", 0x101, NULL },
    { "getDisabledColorWithRAREUIColor:", NULL, "LRAREUIColor", 0x1, NULL },
    { "getCGColor", NULL, "LNSObject", 0x101, NULL },
    { "getPlatformPaintColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "isDarkColor", NULL, "Z", 0x1, NULL },
    { "isDynamic", NULL, "Z", 0x1, NULL },
    { "isPainter", NULL, "Z", 0x1, NULL },
    { "isSimpleColor", NULL, "Z", 0x1, NULL },
    { "getIntColorWithId:", NULL, "I", 0x100, NULL },
    { "getPainter", NULL, "LRAREiBackgroundPainter", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "FACTOR_", NULL, 0x1c, "D" },
    { "YELLOW_", NULL, 0x19, "LRAREUIColor" },
    { "WHITE_", NULL, 0x19, "LRAREUIColor" },
    { "TRANSPARENT_", NULL, 0x19, "LRAREUIColor" },
    { "RED_", NULL, 0x19, "LRAREUIColor" },
    { "PINK_", NULL, 0x19, "LRAREUIColor" },
    { "ORANGE_", NULL, 0x19, "LRAREUIColor" },
    { "MAGENTA_", NULL, 0x19, "LRAREUIColor" },
    { "LIGHTGRAY_", NULL, 0x19, "LRAREUIColor" },
    { "GREEN_", NULL, 0x19, "LRAREUIColor" },
    { "GRAY_", NULL, 0x19, "LRAREUIColor" },
    { "DARKGRAY_", NULL, 0x19, "LRAREUIColor" },
    { "CYAN_", NULL, 0x19, "LRAREUIColor" },
    { "BLUE_", NULL, 0x19, "LRAREUIColor" },
    { "BLACK_", NULL, 0x19, "LRAREUIColor" },
    { "color_", NULL, 0x4, "I" },
    { "proxy_", NULL, 0x4, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREUIColor = { "UIColor", "com.appnativa.rare.ui", NULL, 0x1, 20, methods, 17, fields, 0, NULL};
  return &_RAREUIColor;
}

@end