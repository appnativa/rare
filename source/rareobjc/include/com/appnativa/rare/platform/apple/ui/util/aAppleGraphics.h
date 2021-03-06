//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/util/aAppleGraphics.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaAppleGraphics_H_
#define _RAREaAppleGraphics_H_

@class IOSCharArray;
@class RARETransform;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUIRectangle;
@class RAREUIStroke;
@class RAREUIStroke_JoinEnum;
@class RAREView;
@class RAREaAppleGraphics_Stack;
@class RAREiComposite_CompositeTypeEnum;
@class RAREiGraphics_OpEnum;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol RAREiComposite;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformImage;
@protocol RAREiPlatformPaint;
@protocol RAREiPlatformShape;
@protocol RAREiTransform;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"

@interface RAREaAppleGraphics : NSObject < RAREiPlatformGraphics > {
 @public
  id<RAREiPlatformPaint> paint_;
  float miterLimit_;
  float strokeWidth_;
  RAREUIColor *color_;
  id<RAREiComposite> composite_;
  BOOL disposed_;
  RAREUIFont *font_;
  RAREUIStroke_JoinEnum *joinStyle_;
  RAREUIStroke *lineStroke_;
  id proxy_;
  int rotation_;
  id tempPath_;
  RAREView *view_;
  BOOL componentPainterClipped_;
  id<RAREiTransform> transform_;
  BOOL drawImagesFlipped_;
  RAREaAppleGraphics_Stack *stack_;
}

+ (id)dictionaryProxy;
+ (void)setDictionaryProxy:(id)dictionaryProxy;
- (id)initWithId:(id)g
    withRAREView:(RAREView *)view;
- (id)init;
- (void)clearRectWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height;
- (void)clearRectWithRAREUIColor:(RAREUIColor *)bg
                       withFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height;
- (void)clipRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height;
- (void)clipRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height
 withRAREiGraphics_OpEnum:(RAREiGraphics_OpEnum *)op;
- (void)clipShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape;
- (void)clipShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
               withRAREiGraphics_OpEnum:(RAREiGraphics_OpEnum *)op;
- (BOOL)didComponentPainterClip;
- (void)dispose;
- (void)drawCharsWithCharArray:(IOSCharArray *)data
                       withInt:(int)offset
                       withInt:(int)length
                     withFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)height;
- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y;
- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y
                     withRAREiComposite:(id<RAREiComposite>)composite;
- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                    withRAREUIRectangle:(RAREUIRectangle *)src
                    withRAREUIRectangle:(RAREUIRectangle *)dst
                     withRAREiComposite:(id<RAREiComposite>)composite;
- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y
                              withFloat:(float)width
                              withFloat:(float)height;
- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                    withRAREUIRectangle:(RAREUIRectangle *)src
                    withRAREUIRectangle:(RAREUIRectangle *)dst
  withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType
                     withRAREiComposite:(id<RAREiComposite>)composite;
- (void)drawLineWithFloat:(float)x1
                withFloat:(float)y1
                withFloat:(float)x2
                withFloat:(float)y2;
- (void)drawRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height;
- (void)drawRoundRectWithFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                     withFloat:(float)arcWidth
                     withFloat:(float)arcHeight;
- (void)drawShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
                              withFloat:(float)x
                              withFloat:(float)y;
- (void)fillRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height;
- (void)fillRoundRectWithFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                     withFloat:(float)arcWidth
                     withFloat:(float)arcHeight;
- (void)fillShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
                              withFloat:(float)x
                              withFloat:(float)y;
- (void)resetWithRAREView:(RAREView *)view
                   withId:(id)context;
- (void)restoreState;
- (void)restoreStateEx;
- (void)restoreToStateWithInt:(int)state;
- (void)rotateWithInt:(int)degrees;
- (int)saveState;
- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)setColorWithRAREUIColor:(RAREUIColor *)c;
- (void)setComponentPainterClippedWithBoolean:(BOOL)clipped;
- (void)setCompositeWithRAREiComposite:(id<RAREiComposite>)composite;
- (void)setFontWithRAREUIFont:(RAREUIFont *)f;
- (void)setPaintWithRAREiPlatformPaint:(id<RAREiPlatformPaint>)p;
- (void)setRenderingOptionsWithBoolean:(BOOL)anti_aliasing
                           withBoolean:(BOOL)speed;
- (void)setRotationWithInt:(int)rotation;
- (void)setStrokeWithRAREUIStroke:(RAREUIStroke *)stroke;
- (void)setStrokeWidthWithFloat:(float)width;
- (void)setTransformWithRAREiTransform:(id<RAREiTransform>)transform;
- (void)setTransformExWithRARETransform:(RARETransform *)tx;
- (void)setViewWithRAREView:(RAREView *)view;
- (id<RAREiPlatformShape>)getClip;
- (RAREUIRectangle *)getClipBounds;
- (RAREUIColor *)getColor;
- (id<RAREiPlatformComponent>)getComponent;
- (id<RAREiComposite>)getComposite;
+ (int)getCompositingOperationWithRAREiComposite_CompositeTypeEnum:(RAREiComposite_CompositeTypeEnum *)composite;
- (id)getContextRef;
- (RAREUIFont *)getFont;
+ (int)getImageInterpolationWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType;
- (id<RAREiPlatformPaint>)getPaint;
- (int)getRotation;
- (RAREUIStroke *)getStroke;
- (float)getStrokeWidth;
- (id<RAREiTransform>)getTransform;
- (RAREView *)getView;
- (BOOL)isDisposed;
- (void)addPathClipWithId:(id)path;
- (void)drawBezierPathWithId:(id)path;
- (void)fillBezierPathWithId:(id)path;
- (void)fillOrStrokeBezierPathWithId:(id)path
                         withBoolean:(BOOL)fill;
- (void)loadStrokeWithRAREUIStroke:(RAREUIStroke *)stroke;
- (void)restoreOldContextAsCurrent;
- (void)setCompositeWithInt:(int)composite
                  withFloat:(float)alpha;
- (BOOL)setContextAsCurrent;
- (void)setPathClipWithId:(id)path;
- (void)setStrokeExWithRAREUIStroke:(RAREUIStroke *)stroke;
- (id)getRoundedRectPathWithFloat:(float)x
                        withFloat:(float)y
                        withFloat:(float)width
                        withFloat:(float)height
                        withFloat:(float)arcWidth
                        withFloat:(float)arcHeight;
- (int)getNormalBlendMode;
+ (id)addDictionaryAttributeWithNSString:(NSString *)name
                                  withId:(id)value
                             withBoolean:(BOOL)first;
- (BOOL)isDrawImagesFlipped;
- (void)setDrawImagesFlippedWithBoolean:(BOOL)drawImagesFlipped;
- (void)drawStringWithNSString:(NSString *)param0
                     withFloat:(float)param1
                     withFloat:(float)param2
                     withFloat:(float)param3;
- (void)copyAllFieldsTo:(RAREaAppleGraphics *)other;
@end

J2OBJC_FIELD_SETTER(RAREaAppleGraphics, paint_, id<RAREiPlatformPaint>)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, color_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, composite_, id<RAREiComposite>)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, font_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, joinStyle_, RAREUIStroke_JoinEnum *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, lineStroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, proxy_, id)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, tempPath_, id)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, view_, RAREView *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, transform_, id<RAREiTransform>)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics, stack_, RAREaAppleGraphics_Stack *)

typedef RAREaAppleGraphics ComAppnativaRarePlatformAppleUiUtilAAppleGraphics;

@interface RAREaAppleGraphics_Stack : NSObject {
 @public
  RAREUIStroke *stroke_;
  RAREUIColor *color_;
  id parent_;
  int state_;
  RAREUIFont *font_;
  id<RAREiTransform> transform_;
  id<RAREiPlatformPaint> paint_;
}

- (int)saveWithRAREaAppleGraphics:(RAREaAppleGraphics *)g;
- (void)dispose;
- (BOOL)restoreWithRAREaAppleGraphics:(RAREaAppleGraphics *)g;
- (id)init;
- (void)copyAllFieldsTo:(RAREaAppleGraphics_Stack *)other;
@end

J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, stroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, color_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, parent_, id)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, font_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, transform_, id<RAREiTransform>)
J2OBJC_FIELD_SETTER(RAREaAppleGraphics_Stack, paint_, id<RAREiPlatformPaint>)

#endif // _RAREaAppleGraphics_H_
