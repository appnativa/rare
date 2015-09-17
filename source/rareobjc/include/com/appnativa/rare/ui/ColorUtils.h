//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/ColorUtils.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREColorUtils_H_
#define _RAREColorUtils_H_

@class IOSCharArray;
@class IOSFloatArray;
@class IOSObjectArray;
@class JavaLangBoolean;
@class JavaUtilHashMap;
@class RAREColorUtils_ShadeEnum;
@class RAREPaintBucket;
@class RARESPOTGridCell;
@class RAREUIColor;
@class RAREUIColorShade;
@class RAREUISimpleBackgroundPainter;
@class RAREUTCharScanner;
@class SPOTPrintableString;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiBackgroundPainter;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformPainter;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "java/lang/Enum.h"
#include "java/lang/ThreadLocal.h"

#define RAREColorUtils_FACTOR 0.7
#define RAREColorUtils_oneThird 0.33333334
#define RAREColorUtils_twoThirds 0.6666667

@interface RAREColorUtils : NSObject {
}

+ (JavaLangBoolean *)KEEP_COLOR_KEYS;
+ (void)setKEEP_COLOR_KEYS:(JavaLangBoolean *)KEEP_COLOR_KEYS;
+ (NSString *)KEEP_COLOR_KEYS_KEY;
+ (RAREUIColor *)TRANSPARENT_COLOR;
+ (RAREUIColor *)NULL_COLOR;
+ (RAREUIColor *)DISABLED_TRANSPARENT_COLOR;
+ (void)setDISABLED_TRANSPARENT_COLOR:(RAREUIColor *)DISABLED_TRANSPARENT_COLOR;
+ (id<RAREiBackgroundPainter>)NULL_BGPAINTER;
+ (void)setNULL_BGPAINTER:(id<RAREiBackgroundPainter>)NULL_BGPAINTER;
+ (RAREUIColor *)background;
+ (void)setBackground:(RAREUIColor *)background;
+ (RAREUISimpleBackgroundPainter *)controlPainter;
+ (void)setControlPainter:(RAREUISimpleBackgroundPainter *)controlPainter;
+ (RAREUIColor *)foreground;
+ (void)setForeground:(RAREUIColor *)foreground;
+ (IOSObjectArray *)DISTRIBUTIONS;
+ (IOSCharArray *)colorTokens;
+ (RAREPaintBucket *)paintBucket;
+ (void)setPaintBucket:(RAREPaintBucket *)paintBucket;
+ (JavaUtilHashMap *)colorMap;
+ (JavaLangThreadLocal *)perThreadScanner;
+ (JavaLangThreadLocal *)perThreadList;
+ (RAREUISimpleBackgroundPainter *)whitePainter;
+ (void)setWhitePainter:(RAREUISimpleBackgroundPainter *)whitePainter;
+ (RAREPaintBucket *)backgroundBucket;
+ (void)setBackgroundBucket:(RAREPaintBucket *)backgroundBucket;
+ (RAREUIColor *)disabledForeground;
+ (void)setDisabledForeground:(RAREUIColor *)disabledForeground;
+ (int)HSVToColorWithFloatArray:(IOSFloatArray *)hsv;
+ (int)HSVToColorWithInt:(int)alpha
          withFloatArray:(IOSFloatArray *)hsv;
+ (void)addMappedColorWithNSString:(NSString *)name
                   withRAREUIColor:(RAREUIColor *)c;
+ (int)adjustLuminanceWithInt:(int)color
                      withInt:(int)lum;
+ (int)brighterWithInt:(int)color;
+ (float)brightnessWithInt:(int)color;
+ (RAREPaintBucket *)configureWithRAREiWidget:(id<RAREiWidget>)context
                         withRARESPOTGridCell:(RARESPOTGridCell *)gc
                          withRAREPaintBucket:(RAREPaintBucket *)pb;
+ (void)configureBackgroundPainterWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                                     withSPOTPrintableString:(SPOTPrintableString *)bgColor;
+ (void)configureBackgroundPainterWithRAREiWidget:(id<RAREiWidget>)context
                          withSPOTPrintableString:(SPOTPrintableString *)bgColor;
+ (void)configureBackgroundPainterWithRAREPaintBucket:(RAREPaintBucket *)pb
                              withSPOTPrintableString:(SPOTPrintableString *)bgColor;
+ (void)configureBackgroundPainterWithRAREiWidget:(id<RAREiWidget>)context
                                     withNSString:(NSString *)color
                                  withJavaUtilMap:(id<JavaUtilMap>)attrs
                                      withBoolean:(BOOL)alwaysUsePainter;
+ (BOOL)configureBackgroundPainterWithRAREPaintBucket:(RAREPaintBucket *)pb
                                         withNSString:(NSString *)color
                                      withJavaUtilMap:(id<JavaUtilMap>)attrs
                                          withBoolean:(BOOL)alwaysUsePainter;
+ (IOSObjectArray *)createNamedShadesWithRAREUIColorArray:(IOSObjectArray *)a;
+ (int)darkerWithInt:(int)color;
+ (float)hueWithInt:(int)color;
+ (float)saturationWithInt:(int)color;
+ (void)updateColorWithNSString:(NSString *)name
                         withId:(id)value;
+ (int)setAlphaWithInt:(int)argb
               withInt:(int)alpha;
+ (void)setBaseColors;
+ (int)getARGBWithInt:(int)r
              withInt:(int)g
              withInt:(int)b
              withInt:(int)a;
+ (int)getAlphaWithInt:(int)color;
+ (NSString *)shadeKeyToStringWithRAREColorUtils_ShadeEnum:(RAREColorUtils_ShadeEnum *)shade
                                              withNSString:(NSString *)colorKey
                                                   withInt:(int)lumAdjustment
                                                   withInt:(int)alpha;
+ (RAREUIColor *)getBackground;
+ (RAREPaintBucket *)getBackgroundBucket;
+ (RAREUIColor *)getBackgroundColorWithSPOTPrintableString:(SPOTPrintableString *)bgColor;
+ (RAREUIColor *)getBackgroundColorWithNSString:(NSString *)color;
+ (RAREUIColor *)getBackgroundColorWithNSString:(NSString *)color
                                    withBoolean:(BOOL)alwaysUsePainter;
+ (id<RAREiBackgroundPainter>)getBackgroundPainterWithSPOTPrintableString:(SPOTPrintableString *)bgColor;
+ (id<RAREiBackgroundPainter>)getBackgroundPainterWithNSString:(NSString *)color;
+ (int)getBlueWithInt:(int)color;
+ (RAREUIColor *)getColorWithNSString:(NSString *)color;
+ (RAREUIColor *)getColorWithCharArray:(IOSCharArray *)color
                               withInt:(int)pos
                               withInt:(int)len
                          withNSString:(NSString *)source;
+ (IOSObjectArray *)getColorsWithNSString:(NSString *)color
                                  withInt:(int)min;
+ (RAREUIColor *)getControlDkGradient;
+ (RAREUIColor *)getControlDkShadow;
+ (RAREUIColor *)getControlHighlight;
+ (RAREUIColor *)getControlLtGradient;
+ (RAREUIColor *)getControlLtHighlight;
+ (RAREUIColor *)getControlLtShadow;
+ (RAREUIColor *)getControlShadow;
+ (RAREUIColor *)getDisabledForeground;
+ (RAREUIColor *)getDisabledVersionWithRAREUIColor:(RAREUIColor *)fg;
+ (RAREUIColor *)getForeground;
+ (RAREUIColor *)getFromColorMapWithNSString:(NSString *)color;
+ (int)getGreenWithInt:(int)color;
+ (RAREUIColor *)getListBackground;
+ (RAREUIColor *)getListDisabledForeground;
+ (RAREUIColor *)getListDividerColor;
+ (RAREUIColor *)getListForeground;
+ (RAREUIColor *)getNamedColorWithNSString:(NSString *)name;
+ (RAREPaintBucket *)getPaintBucketWithSPOTPrintableString:(SPOTPrintableString *)color;
+ (RAREPaintBucket *)getPaintBucketWithNSString:(NSString *)color;
+ (id<RAREiPlatformPainter>)getPainterWithRAREUIColor:(RAREUIColor *)c;
+ (RAREUIColor *)getPressedVersionWithRAREUIColor:(RAREUIColor *)fg;
+ (int)getRedWithInt:(int)color;
+ (RAREUIColorShade *)getSimpleColorStateListWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (RAREUIColorShade *)getSimpleDrawableStateListWithJavaUtilMap:(id<JavaUtilMap>)map;
+ (IOSFloatArray *)getStandardDistrubutionWithInt:(int)colors;
+ (id<JavaUtilMap>)getStateListMapWithNSString:(NSString *)color;
+ (RAREUIColor *)getTableGridColor;
+ (RAREUIColor *)getTextHilight;
+ (RAREUIColor *)getTextHilightText;
+ (BOOL)isStandardDistributionWithFloatArray:(IOSFloatArray *)distributions;
+ (float)Hue_2_RGBWithFloat:(float)v1
                  withFloat:(float)v2
                  withFloat:(float)vH;
+ (RAREPaintBucket *)getPaintBucketWithNSString:(NSString *)color
                            withRAREPaintBucket:(RAREPaintBucket *)pb
                                    withBoolean:(BOOL)alwaysUsePainter;
- (id)init;
@end

typedef RAREColorUtils ComAppnativaRareUiColorUtils;

typedef enum {
  RAREColorUtils_Shade_DARKER = 0,
  RAREColorUtils_Shade_DARKER_DARKER = 1,
  RAREColorUtils_Shade_BRIGHTER = 2,
  RAREColorUtils_Shade_BRIGHTER_BRIGHTER = 3,
  RAREColorUtils_Shade_LUM_ADJUSTMENT = 4,
  RAREColorUtils_Shade_DYN_LUM_ADJUSTMENT = 5,
  RAREColorUtils_Shade_ALPHA = 6,
  RAREColorUtils_Shade_UIMANAGER = 7,
  RAREColorUtils_Shade_BGPAINT = 8,
  RAREColorUtils_Shade_COLOR_LIST = 9,
  RAREColorUtils_Shade_DRAWABLE_LIST = 10,
  RAREColorUtils_Shade_DRAWABLE = 11,
} RAREColorUtils_Shade;

@interface RAREColorUtils_ShadeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREColorUtils_ShadeEnum *)DARKER;
+ (RAREColorUtils_ShadeEnum *)DARKER_DARKER;
+ (RAREColorUtils_ShadeEnum *)BRIGHTER;
+ (RAREColorUtils_ShadeEnum *)BRIGHTER_BRIGHTER;
+ (RAREColorUtils_ShadeEnum *)LUM_ADJUSTMENT;
+ (RAREColorUtils_ShadeEnum *)DYN_LUM_ADJUSTMENT;
+ (RAREColorUtils_ShadeEnum *)ALPHA;
+ (RAREColorUtils_ShadeEnum *)UIMANAGER;
+ (RAREColorUtils_ShadeEnum *)BGPAINT;
+ (RAREColorUtils_ShadeEnum *)COLOR_LIST;
+ (RAREColorUtils_ShadeEnum *)DRAWABLE_LIST;
+ (RAREColorUtils_ShadeEnum *)DRAWABLE;
+ (IOSObjectArray *)values;
+ (RAREColorUtils_ShadeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RAREColorUtils_$1 : JavaLangThreadLocal {
}

- (RAREUTCharScanner *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREColorUtils_$2 : JavaLangThreadLocal {
}

- (id<JavaUtilList>)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

#endif // _RAREColorUtils_H_
