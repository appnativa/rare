//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ScreenUtils.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREScreenUtils_H_
#define _RAREScreenUtils_H_

@class RAREScreenUtils_UnitEnum;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIRectangle;
@class RAREUIScreen_ScreenSizeEnum;
@protocol RAREiPlatformComponent;
@protocol RAREiWindow;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@interface RAREScreenUtils : NSObject {
}

+ (int)PLATFORM_PIXELS_1;
+ (int *)PLATFORM_PIXELS_1Ref;
+ (int)PLATFORM_PIXELS_10;
+ (int *)PLATFORM_PIXELS_10Ref;
+ (int)PLATFORM_PIXELS_16;
+ (int *)PLATFORM_PIXELS_16Ref;
+ (int)PLATFORM_PIXELS_2;
+ (int *)PLATFORM_PIXELS_2Ref;
+ (int)PLATFORM_PIXELS_3;
+ (int *)PLATFORM_PIXELS_3Ref;
+ (int)PLATFORM_PIXELS_4;
+ (int *)PLATFORM_PIXELS_4Ref;
+ (int)PLATFORM_PIXELS_5;
+ (int *)PLATFORM_PIXELS_5Ref;
+ (int)PLATFORM_PIXELS_6;
+ (int *)PLATFORM_PIXELS_6Ref;
+ (int)PLATFORM_PIXELS_7;
+ (int *)PLATFORM_PIXELS_7Ref;
+ (int)PLATFORM_PIXELS_8;
+ (int *)PLATFORM_PIXELS_8Ref;
+ (int)PLATFORM_PIXELS_9;
+ (int *)PLATFORM_PIXELS_9Ref;
+ (float)screenDpi96Factor;
+ (float *)screenDpi96FactorRef;
+ (float)xScreenDpi;
+ (float *)xScreenDpiRef;
+ (float)yScreenDpi;
+ (float *)yScreenDpiRef;
+ (float)density;
+ (float *)densityRef;
+ (float)fontFactor;
+ (float *)fontFactorRef;
+ (int)lineHeight;
+ (int *)lineHeightRef;
+ (float)pixelMultiplier;
+ (float *)pixelMultiplierRef;
+ (RAREUIScreen_ScreenSizeEnum *)screenSize;
+ (void)setScreenSize:(RAREUIScreen_ScreenSizeEnum *)screenSize;
+ (float)iconDensity;
+ (float *)iconDensityRef;
+ (RAREUIFont *)lineHeightFont;
+ (void)setLineHeightFont:(RAREUIFont *)lineHeightFont;
+ (NSString *)screenSizeName;
+ (void)setScreenSizeName:(NSString *)screenSizeName;
+ (BOOL)screenSizeSet;
+ (BOOL *)screenSizeSetRef;
+ (BOOL)sizeValuesSet;
+ (BOOL *)sizeValuesSetRef;
+ (int)xsmallSize;
+ (int *)xsmallSizeRef;
+ (int)mediumSize;
+ (int *)mediumSizeRef;
+ (int)smallSize;
+ (int *)smallSizeRef;
- (id)init;
+ (RAREUIDimension *)adjustForLineHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                               withRAREUIDimension:(RAREUIDimension *)d;
+ (RAREUIDimension *)adjustForLineHeightExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                                 withRAREUIDimension:(RAREUIDimension *)d;
+ (int)calculateLineHeight;
+ (RAREUIScreen_ScreenSizeEnum *)calculateScreenSizeWithInt:(int)xsmall
                                                    withInt:(int)small
                                                    withInt:(int)medium;
+ (void)centerOnScreenAndSizeWithRAREiWindow:(id<RAREiWindow>)win
                                   withFloat:(float)x
                                   withFloat:(float)y
                                   withFloat:(float)w
                                   withFloat:(float)h;
+ (void)centerOnScreenWithRAREiWindow:(id<RAREiWindow>)win;
+ (void)centerOnWindowWithRAREiWindow:(id<RAREiWindow>)win
                      withRAREiWindow:(id<RAREiWindow>)parent;
+ (int)platformPixelsWithFloat:(float)pixels;
+ (float)platformPixelsfWithFloat:(float)pixels;
+ (float)fromPlatformPixelsWithFloat:(float)size
        withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)unit;
+ (float)fromPlatformPixelsWithFloat:(float)size
        withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)unit
                         withBoolean:(BOOL)width;
+ (void)initilizeWithFloat:(float)sdensity
                 withFloat:(float)pmultiplier
                 withFloat:(float)dpi
                 withFloat:(float)xdpi
                 withFloat:(float)ydpi
                 withFloat:(float)fontfactor OBJC_METHOD_FAMILY_NONE;
+ (int)lineHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (int)lineHeightExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (float)lineHeightExWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                 withRAREUIFont:(RAREUIFont *)f;
+ (int)toPlatformPixelHeightWithNSString:(NSString *)value
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withFloat:(float)heightForPercent;
+ (int)toPlatformPixelHeightWithNSString:(NSString *)value
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withFloat:(float)heightForPercent
                             withBoolean:(BOOL)charAdjust;
+ (int)toPlatformPixelHeightWithFloat:(float)h
         withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)unit
           withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                            withFloat:(float)heightForPercent
                          withBoolean:(BOOL)charAdjust;
+ (int)toPlatformPixelWidthWithNSString:(NSString *)value
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withFloat:(float)widthForPercent;
+ (int)toPlatformPixelWidthWithNSString:(NSString *)value
             withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                              withFloat:(float)widthForPercent
                            withBoolean:(BOOL)charAdjust;
+ (int)toPlatformPixelWidthWithFloat:(float)w
        withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)unit
          withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                           withFloat:(float)widthForPercent
                         withBoolean:(BOOL)charAdjust;
+ (int)toPlatformPixelsWithFloat:(float)characters
      withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                     withBoolean:(BOOL)charAdjust;
+ (int)toPlatformPixelsWithFloat:(float)size
    withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)unit
                     withBoolean:(BOOL)width;
+ (void)updateFontFactorWithFloat:(float)value;
+ (void)setOrientationWithId:(id)orientation;
+ (void)setScreenSizeWithRAREUIScreen_ScreenSizeEnum:(RAREUIScreen_ScreenSizeEnum *)size;
+ (RAREUIRectangle *)getBounds;
+ (RAREUIRectangle *)getBoundsWithInt:(int)screen;
+ (float)getCharWidthPadding;
+ (float)getCssPixelSizeWithFloat:(float)pxsize;
+ (float)getDefaultIconDensity;
+ (float)getDensity;
+ (NSString *)getDensityName;
+ (int)getHeight;
+ (id)getOrientation;
+ (float)getPixelMultiplier;
+ (RAREUIFont *)getPrinterFontWithRAREUIFont:(RAREUIFont *)f;
+ (float)getPrinterScaleFactor;
+ (RAREUIScreen_ScreenSizeEnum *)getRelativeScreenSize;
+ (NSString *)getRelativeScreenSizeName;
+ (int)getRotation;
+ (int)getRotationWithId:(id)orientation;
+ (int)getRotationForConfigurationWithId:(id)configuration;
+ (int)getScreenWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (int)getScreenCount;
+ (float)getScreenDPI;
+ (float)getScreenFontSizeWithFloat:(float)javasize;
+ (int)getScreenHeightWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREUIDimension *)getScreenSize;
+ (RAREUIDimension *)getScreenSizeWithInt:(int)screen;
+ (RAREUIDimension *)getScreenSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (int)getScreenWidthWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREUIDimension *)getSize;
+ (RAREUIDimension *)getSizeWithNSString:(NSString *)width
                            withNSString:(NSString *)height
              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
+ (RAREUIDimension *)getSizeForConfigurationWithId:(id)configuration;
+ (RAREScreenUtils_UnitEnum *)getUnitWithNSString:(NSString *)s;
+ (RAREScreenUtils_UnitEnum *)getUnitWithNSString:(NSString *)s
                                          withInt:(int)len;
+ (RAREScreenUtils_UnitEnum *)getUnitWithNSString:(NSString *)s
                     withRAREScreenUtils_UnitEnum:(RAREScreenUtils_UnitEnum *)def;
+ (RAREUIDimension *)getUsableScreenSize;
+ (RAREUIDimension *)getUsableScreenSizeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (RAREUIRectangle *)getUsableScreenBoundsWithInt:(int)screen;
+ (RAREUIRectangle *)getUsableScreenBoundsWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
+ (int)getWidth;
+ (BOOL)isExtraSmallScreen;
+ (BOOL)isHighDensity;
+ (BOOL)isLandscapeOrientationWithId:(id)orientation;
+ (BOOL)isLargeScreen;
+ (BOOL)isLowDensity;
+ (BOOL)isMediumDensity;
+ (BOOL)isMediumScreen;
+ (BOOL)isSmallScreen;
+ (BOOL)isWider;
+ (BOOL)isWiderForConfigurationWithId:(id)configuration;
+ (void)calculateScreenSize;
@end

typedef RAREScreenUtils ComAppnativaRareUiScreenUtils;

typedef enum {
  RAREScreenUtils_Unit_PIXELS = 0,
  RAREScreenUtils_Unit_CENTIMETER = 1,
  RAREScreenUtils_Unit_MILLIMETER = 2,
  RAREScreenUtils_Unit_INCH = 3,
  RAREScreenUtils_Unit_PERCENT = 4,
  RAREScreenUtils_Unit_CHARACTERS = 5,
  RAREScreenUtils_Unit_POINT = 6,
  RAREScreenUtils_Unit_PICA = 7,
  RAREScreenUtils_Unit_LINES = 8,
  RAREScreenUtils_Unit_DIALOG = 9,
  RAREScreenUtils_Unit_RARE_PIXELS = 10,
} RAREScreenUtils_Unit;

@interface RAREScreenUtils_UnitEnum : JavaLangEnum < NSCopying > {
}
+ (RAREScreenUtils_UnitEnum *)PIXELS;
+ (RAREScreenUtils_UnitEnum *)CENTIMETER;
+ (RAREScreenUtils_UnitEnum *)MILLIMETER;
+ (RAREScreenUtils_UnitEnum *)INCH;
+ (RAREScreenUtils_UnitEnum *)PERCENT;
+ (RAREScreenUtils_UnitEnum *)CHARACTERS;
+ (RAREScreenUtils_UnitEnum *)POINT;
+ (RAREScreenUtils_UnitEnum *)PICA;
+ (RAREScreenUtils_UnitEnum *)LINES;
+ (RAREScreenUtils_UnitEnum *)DIALOG;
+ (RAREScreenUtils_UnitEnum *)RARE_PIXELS;
+ (IOSObjectArray *)values;
+ (RAREScreenUtils_UnitEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREScreenUtils_H_