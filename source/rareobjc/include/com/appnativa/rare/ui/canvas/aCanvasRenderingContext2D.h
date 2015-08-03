//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/aCanvasRenderingContext2D.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaCanvasRenderingContext2D_H_
#define _RAREaCanvasRenderingContext2D_H_

@class JavaNioByteBuffer;
@class RAREContextState;
@class RAREUIColor;
@class RAREUIFontMetrics;
@class RAREUIImage;
@class RAREaCompositingGraphics;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol JavaUtilList;
@protocol RAREiCanvas;
@protocol RAREiContext_iCanvasGradient;
@protocol RAREiContext_iCanvasPattern;
@protocol RAREiContext_iImageData;
@protocol RAREiContext_iImageElement;
@protocol RAREiPainter;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformPainter;
@protocol RAREiPlatformPath;
@protocol RAREiPlatformShape;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"

@interface RAREaCanvasRenderingContext2D : RAREaUIPainter < RAREiContext > {
 @public
  id<RAREiPlatformGraphics> graphics_;
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
  RAREUIImage *compositeImage_;
  id<RAREiPlatformPath> currentPath_;
  RAREContextState *currentState_;
  BOOL repaintCalled_;
  id<JavaUtilList> stateStack_;
  id<RAREiCanvas> theCanvas_;
  RAREUIImage *theImage_;
  BOOL optimizeForCompositing_;
}

- (id)initWithRAREiCanvas:(id<RAREiCanvas>)canvas;
- (void)arcWithFloat:(float)x
           withFloat:(float)y
           withFloat:(float)radius
           withFloat:(float)startAngle
           withFloat:(float)endAngle;
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
- (void)createReflectionWithInt:(int)y
                        withInt:(int)height
                      withFloat:(float)opacity
                        withInt:(int)gap;
- (void)drawImageWithRAREiCanvas:(id<RAREiCanvas>)canvas
                       withFloat:(float)x
                       withFloat:(float)y;
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
                   withFloat:(float)maxWidth;
- (void)fillTextWithNSString:(NSString *)text
                   withFloat:(float)x
                   withFloat:(float)y
                     withInt:(int)maxWidth;
- (void)lineToWithFloat:(float)x
              withFloat:(float)y;
- (id<RAREiContext_iTextMetrics>)measureTextWithNSString:(NSString *)text;
- (void)moveToWithFloat:(float)x
              withFloat:(float)y;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                               withInt:(int)orientation;
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
- (void)disposeEx;
- (void)clearEx;
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
                     withFloat:(float)maxWidth;
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
- (void)setFillStyleWithRAREiContext_iCanvasPattern:(id<RAREiContext_iCanvasPattern>)pattern;
- (void)setFillStyleWithNSString:(NSString *)color;
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
- (void)setStrokeStyleWithRAREiContext_iCanvasPattern:(id<RAREiContext_iCanvasPattern>)pattern;
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
- (id<RAREiPainter>)getIPainter;
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
- (BOOL)isSingleColorPainter;
- (RAREUIImage *)createImageIfNecessaryWithRAREUIImage:(RAREUIImage *)img
                                               withInt:(int)width
                                               withInt:(int)height;
- (void)initImageWithBoolean:(BOOL)force OBJC_METHOD_FAMILY_NONE;
- (void)setupGraphicsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g;
- (void)setImageBytesWithRAREUIImage:(RAREUIImage *)img
                             withInt:(int)x
                             withInt:(int)y
                             withInt:(int)width
                             withInt:(int)height
               withJavaNioByteBuffer:(JavaNioByteBuffer *)b;
- (RAREUIFontMetrics *)setTextFontWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                               withNSString:(NSString *)s
                                                  withFloat:(float)maxWidth;
- (JavaNioByteBuffer *)getImageBytesWithRAREUIImage:(RAREUIImage *)img
                                            withInt:(int)x
                                            withInt:(int)y
                                            withInt:(int)width
                                            withInt:(int)height;
- (RAREUIFontMetrics *)getUIFontMetricsWithNSString:(NSString *)s
                                            withInt:(int)maxWidth;
- (RAREaCompositingGraphics *)createCompositingGraphicsWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                                 withRAREUIImage:(RAREUIImage *)image;
- (id<RAREiPlatformGraphics>)checkForCompositingWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g;
- (void)drawTextWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withNSString:(NSString *)text
                                withFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)maxWidth;
- (id<RAREiPlatformGraphics>)getGraphics;
- (id<RAREiPlatformGraphics>)getGraphicsWithBoolean:(BOOL)strokePaint;
- (BOOL)isOptimizeForCompositing;
- (void)setOptimizeForCompositingWithBoolean:(BOOL)optimizeForCompositing;
- (id<RAREiContext_iCanvasGradient>)createLinearGradientWithFloat:(float)param0
                                                        withFloat:(float)param1
                                                        withFloat:(float)param2
                                                        withFloat:(float)param3;
- (id<RAREiContext_iCanvasPattern>)createPatternWithRAREiContext_iImageElement:(id<RAREiContext_iImageElement>)param0
                                                                  withNSString:(NSString *)param1;
- (id<RAREiContext_iCanvasGradient>)createRadialGradientWithFloat:(float)param0
                                                        withFloat:(float)param1
                                                        withFloat:(float)param2
                                                        withFloat:(float)param3
                                                        withFloat:(float)param4
                                                        withFloat:(float)param5;
- (BOOL)isPointInPathWithFloat:(float)param0
                     withFloat:(float)param1;
- (void)copyAllFieldsTo:(RAREaCanvasRenderingContext2D *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, graphics_, id<RAREiPlatformGraphics>)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, scalingType_, RAREiImagePainter_ScalingTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, compositeImage_, RAREUIImage *)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, currentPath_, id<RAREiPlatformPath>)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, currentState_, RAREContextState *)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, stateStack_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, theCanvas_, id<RAREiCanvas>)
J2OBJC_FIELD_SETTER(RAREaCanvasRenderingContext2D, theImage_, RAREUIImage *)

typedef RAREaCanvasRenderingContext2D ComAppnativaRareUiCanvasACanvasRenderingContext2D;

@interface RAREaCanvasRenderingContext2D_TextMetrics : NSObject < RAREiContext_iTextMetrics > {
 @public
  int width_;
}

- (id)initWithInt:(int)width;
- (int)getWidth;
- (void)copyAllFieldsTo:(RAREaCanvasRenderingContext2D_TextMetrics *)other;
@end

#endif // _RAREaCanvasRenderingContext2D_H_
