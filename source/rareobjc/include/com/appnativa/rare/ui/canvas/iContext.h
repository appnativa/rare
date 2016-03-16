//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/iContext.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiContext_H_
#define _RAREiContext_H_

@class RAREUIColor;
@class RAREUIImage;
@class RAREUint8ClampedArray;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiCanvas;
@protocol RAREiContext_iCanvasGradient;
@protocol RAREiContext_iCanvasPattern;
@protocol RAREiContext_iImageData;
@protocol RAREiContext_iImageElement;
@protocol RAREiContext_iTextMetrics;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPaint;
@protocol RAREiPlatformPainter;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "java/lang/Enum.h"

@protocol RAREiContext < NSObject, JavaObject >
- (void)arcWithFloat:(float)x
           withFloat:(float)y
           withFloat:(float)radius
           withFloat:(float)startAngle
           withFloat:(float)endAngle
         withBoolean:(BOOL)antiClockwise;
- (void)arcToWithFloat:(float)x0
             withFloat:(float)y0
             withFloat:(float)x1
             withFloat:(float)y1
             withFloat:(float)radius;
- (void)beginPath;
- (void)bezierCurveToWithFloat:(float)cp1x
                     withFloat:(float)cp1y
                     withFloat:(float)cp2x
                     withFloat:(float)cp2y
                     withFloat:(float)x
                     withFloat:(float)y;
- (void)blur;
- (void)clear;
- (void)clearRectWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)clip;
- (void)closePath;
- (id<RAREiContext_iImageData>)createImageDataWithRAREiContext_iImageData:(id<RAREiContext_iImageData>)imagedata;
- (id<RAREiContext_iImageData>)createImageDataWithInt:(int)sw
                                              withInt:(int)sh;
- (id<RAREiContext_iCanvasGradient>)createLinearGradientWithFloat:(float)x0
                                                        withFloat:(float)y0
                                                        withFloat:(float)x1
                                                        withFloat:(float)y1;
- (id<RAREiContext_iCanvasPattern>)createPatternWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                                                                  withNSString:(NSString *)repetition;
- (id<RAREiContext_iCanvasGradient>)createRadialGradientWithFloat:(float)x0
                                                        withFloat:(float)y0
                                                        withFloat:(float)r0
                                                        withFloat:(float)x1
                                                        withFloat:(float)y1
                                                        withFloat:(float)r1;
- (void)createReflectionWithInt:(int)y
                        withInt:(int)height
                      withFloat:(float)opacity
                        withInt:(int)gap;
- (void)dispose;
- (void)drawImageWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                                      withFloat:(float)x
                                      withFloat:(float)y;
- (void)drawImageWithRAREUIImage:(RAREUIImage *)img
                       withFloat:(float)x
                       withFloat:(float)y;
- (void)drawImageWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                                      withFloat:(float)x
                                      withFloat:(float)y
                                      withFloat:(float)width
                                      withFloat:(float)height;
- (void)drawImageWithRAREUIImage:(RAREUIImage *)img
                       withFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)drawImageWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)img
                                      withFloat:(float)sx
                                      withFloat:(float)sy
                                      withFloat:(float)swidth
                                      withFloat:(float)sheight
                                      withFloat:(float)dx
                                      withFloat:(float)dy
                                      withFloat:(float)dwidth
                                      withFloat:(float)dheight;
- (void)drawImageWithRAREUIImage:(RAREUIImage *)img
                       withFloat:(float)sx
                       withFloat:(float)sy
                       withFloat:(float)swidth
                       withFloat:(float)sheight
                       withFloat:(float)dx
                       withFloat:(float)dy
                       withFloat:(float)dwidth
                       withFloat:(float)dheight;
- (void)fill;
- (void)fillWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape;
- (void)fillRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height;
- (void)fillRoundedRectWithFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height
                       withFloat:(float)arcWidth
                       withFloat:(float)arcHeight;
- (void)fillTextWithNSString:(NSString *)text
                   withFloat:(float)x
                   withFloat:(float)y;
- (void)fillTextWithNSString:(NSString *)text
                   withFloat:(float)x
                   withFloat:(float)y
                     withInt:(int)maxWidth;
- (void)lineToWithFloat:(float)x
              withFloat:(float)y;
- (id<RAREiContext_iTextMetrics>)measureTextWithNSString:(NSString *)text;
- (void)moveToWithFloat:(float)x
              withFloat:(float)y;
- (void)putImageDataWithRAREiContext_iImageData:(id<RAREiContext_iImageData>)imagedata
                                        withInt:(int)dx
                                        withInt:(int)dy;
- (void)putImageDataWithRAREiContext_iImageData:(id<RAREiContext_iImageData>)imagedata
                                        withInt:(int)dx
                                        withInt:(int)dy
                                        withInt:(int)dirtyX
                                        withInt:(int)dirtyY
                                        withInt:(int)dirtyWidth
                                        withInt:(int)dirtyHeight;
- (void)quadraticCurveToWithFloat:(float)cpx
                        withFloat:(float)cpy
                        withFloat:(float)x
                        withFloat:(float)y;
- (void)rectWithFloat:(float)startX
            withFloat:(float)startY
            withFloat:(float)width
            withFloat:(float)height;
- (void)renderWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g;
- (void)restore;
- (void)rotateWithFloat:(float)angle;
- (void)save;
- (void)scale__WithFloat:(float)x
               withFloat:(float)y;
- (void)stroke;
- (void)strokeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape;
- (void)strokeRectWithFloat:(float)x
                  withFloat:(float)y
                  withFloat:(float)width
                  withFloat:(float)height;
- (void)strokeRoundedRectWithFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height
                         withFloat:(float)arcWidth
                         withFloat:(float)arcHeight;
- (void)strokeTextWithNSString:(NSString *)text
                     withFloat:(float)x
                     withFloat:(float)y;
- (void)strokeTextWithNSString:(NSString *)text
                     withFloat:(float)x
                     withFloat:(float)y
                       withInt:(int)maxWidth;
- (void)transformWithFloat:(float)m11
                 withFloat:(float)m12
                 withFloat:(float)m21
                 withFloat:(float)m22
                 withFloat:(float)dx
                 withFloat:(float)dy;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)setFillStyleWithRAREiContext_iCanvasGradient:(id<RAREiContext_iCanvasGradient>)grad;
- (void)setFillStyleWithNSString:(NSString *)UIColor;
- (void)setFillStyleWithRAREUIColor:(RAREUIColor *)color;
- (void)setFontWithNSString:(NSString *)font;
- (void)setGlobalAlphaWithFloat:(float)alpha;
- (void)setGlobalCompositeOperationWithNSString:(NSString *)globalCompositeOperation;
- (void)setLineCapWithNSString:(NSString *)lineCap;
- (void)setLineJoinWithNSString:(NSString *)lineJoin;
- (void)setLineWidthWithFloat:(float)width;
- (void)setMiterLimitWithFloat:(float)miterLimit;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType;
- (void)setSizeWithInt:(int)width
               withInt:(int)height;
- (void)setStrokeStyleWithRAREiContext_iCanvasGradient:(id<RAREiContext_iCanvasGradient>)grad;
- (void)setStrokeStyleWithNSString:(NSString *)color;
- (void)setStrokeStyleWithRAREUIColor:(RAREUIColor *)color;
- (void)setTextAlignWithNSString:(NSString *)value;
- (void)setTextBaselineWithNSString:(NSString *)value;
- (void)setTransformWithFloat:(float)m11
                    withFloat:(float)m12
                    withFloat:(float)m21
                    withFloat:(float)m22
                    withFloat:(float)dx
                    withFloat:(float)dy;
- (id<RAREiCanvas>)getCanvas;
- (NSString *)getFont;
- (float)getGlobalAlpha;
- (NSString *)getGlobalCompositeOperation;
- (RAREUIImage *)getImageWithBoolean:(BOOL)copy_;
- (id<RAREiContext_iImageData>)getImageDataWithInt:(int)sx
                                           withInt:(int)sy
                                           withInt:(int)sw
                                           withInt:(int)sh;
- (NSString *)getLineCap;
- (NSString *)getLineJoin;
- (float)getLineWidth;
- (float)getMiterLimit;
- (id<RAREiPlatformPainter>)getPainter;
- (RAREiImagePainter_ScalingTypeEnum *)getScalingType;
- (NSString *)getTextAlign;
- (NSString *)getTextBaseline;
- (BOOL)isPointInPathWithFloat:(float)x
                     withFloat:(float)y;
@end

#define ComAppnativaRareUiCanvasIContext RAREiContext

typedef enum {
  RAREiContext_TextAlign_left = 0,
  RAREiContext_TextAlign_start = 1,
  RAREiContext_TextAlign_right = 2,
  RAREiContext_TextAlign_end = 3,
  RAREiContext_TextAlign_center = 4,
} RAREiContext_TextAlign;

@interface RAREiContext_TextAlignEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiContext_TextAlignEnum *)left;
+ (RAREiContext_TextAlignEnum *)start;
+ (RAREiContext_TextAlignEnum *)right;
+ (RAREiContext_TextAlignEnum *)end;
+ (RAREiContext_TextAlignEnum *)center;
+ (IOSObjectArray *)values;
+ (RAREiContext_TextAlignEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RAREiContext_TextBaseline_top = 0,
  RAREiContext_TextBaseline_hanging = 1,
  RAREiContext_TextBaseline_middle = 2,
  RAREiContext_TextBaseline_alphabetic = 3,
  RAREiContext_TextBaseline_ideographic = 4,
  RAREiContext_TextBaseline_bottom = 5,
} RAREiContext_TextBaseline;

@interface RAREiContext_TextBaselineEnum : JavaLangEnum < NSCopying > {
}
+ (RAREiContext_TextBaselineEnum *)top;
+ (RAREiContext_TextBaselineEnum *)hanging;
+ (RAREiContext_TextBaselineEnum *)middle;
+ (RAREiContext_TextBaselineEnum *)alphabetic;
+ (RAREiContext_TextBaselineEnum *)ideographic;
+ (RAREiContext_TextBaselineEnum *)bottom;
+ (IOSObjectArray *)values;
+ (RAREiContext_TextBaselineEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@protocol RAREiContext_iCanvasPaint < NSObject, JavaObject >
- (id<RAREiContext_iCanvasPaint>)cloneCopy;
- (id<RAREiPlatformPaint>)getPaint;
@end

@protocol RAREiContext_iCanvasGradient < RAREiContext_iCanvasPaint, NSObject, JavaObject >
- (void)addColorStopWithFloat:(float)offset
              withRAREUIColor:(RAREUIColor *)UIColor;
@end

@protocol RAREiContext_iCanvasPattern < RAREiContext_iCanvasPaint, NSObject, JavaObject >
- (BOOL)isRepeatX;
- (BOOL)isRepeatXorY;
- (BOOL)isRepeatY;
@end

@protocol RAREiContext_iImageData < NSObject, JavaObject >
- (void)updateImageWithRAREUIImage:(RAREUIImage *)image
                           withInt:(int)dx
                           withInt:(int)dy
                           withInt:(int)dirtyWidth
                           withInt:(int)dirtyHeight;
- (RAREUint8ClampedArray *)getData;
- (int)getHeight;
- (int)getWidth;
@end

@protocol RAREiContext_iImageElement < NSObject, JavaObject >
- (RAREUIImage *)getImage;
- (RAREUIImage *)getImageWithInt:(int)x
                         withInt:(int)y
                         withInt:(int)width
                         withInt:(int)height;
@end

@protocol RAREiContext_iTextMetrics < NSObject, JavaObject >
- (int)getWidth;
@end

#endif // _RAREiContext_H_
