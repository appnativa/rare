//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/ContextState.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREContextState_H_
#define _RAREContextState_H_

@class JavaUtilHashMap;
@class RARECanvasColor;
@class RAREUIFont;
@class RAREUIStroke;
@class RAREUIStroke_CapEnum;
@class RAREUIStroke_JoinEnum;
@class RAREiContext_TextAlignEnum;
@class RAREiContext_TextBaselineEnum;
@protocol RAREiComposite;
@protocol RAREiContext_iCanvasPaint;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformShape;
@protocol RAREiTransform;

#import "JreEmulation.h"

@interface RAREContextState : NSObject < NSCopying > {
 @public
  NSString *fontString_;
  float globalAlpha_;
  float lineWidth_;
  float miterLimit_;
  RAREiContext_TextBaselineEnum *textBaseline_;
  RAREiContext_TextAlignEnum *textAlign_;
  id<RAREiContext_iCanvasPaint> strokeStyle_;
  RAREUIStroke *stroke_;
  RAREUIStroke_JoinEnum *lineJoin_;
  RAREUIStroke_CapEnum *lineCap_;
  RAREUIFont *font_;
  id<RAREiContext_iCanvasPaint> fillStyle_;
  id<RAREiComposite> alphaComposite_;
  id<RAREiPlatformShape> clip_;
  BOOL lineDirty_;
  id<RAREiTransform> transform__;
  BOOL valuesDirty_;
}

+ (RAREUIStroke *)initial_stroke;
+ (RAREUIFont *)initial_font;
+ (RARECanvasColor *)black;
+ (JavaUtilHashMap *)fontSizeMap;
+ (id<RAREiTransform>)nullTransform;
+ (void)setNullTransform:(id<RAREiTransform>)nullTransform;
- (id)init;
- (void)dispose;
- (id)clone;
- (RAREContextState *)copy__ OBJC_METHOD_FAMILY_NONE;
- (void)dirty;
- (void)rotateWithFloat:(float)angle;
- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy;
- (void)transformWithFloat:(float)m11
                 withFloat:(float)m12
                 withFloat:(float)m21
                 withFloat:(float)m22
                 withFloat:(float)dx
                 withFloat:(float)dy;
- (void)translateWithFloat:(float)x
                 withFloat:(float)y;
- (void)setClipWithRAREiPlatformShape:(id<RAREiPlatformShape>)clip;
- (void)setCompositeWithRAREiComposite:(id<RAREiComposite>)composite;
- (void)setFillStyleWithRAREiContext_iCanvasPaint:(id<RAREiContext_iCanvasPaint>)fillStyle;
- (void)setFontWithNSString:(NSString *)cssfont;
- (void)setGlobalAlphaWithFloat:(float)alpha;
- (void)setGlobalCompositeOperationWithNSString:(NSString *)composite;
- (void)setLineCapWithNSString:(NSString *)cap;
- (void)setLineCapWithRAREUIStroke_CapEnum:(RAREUIStroke_CapEnum *)cap;
- (void)setLineJoinWithNSString:(NSString *)join;
- (void)setLineJoinWithRAREUIStroke_JoinEnum:(RAREUIStroke_JoinEnum *)join;
- (void)setLineWidthWithFloat:(float)width;
- (void)setMiterLimitWithFloat:(float)limit;
- (void)setStrokeStyleWithRAREiContext_iCanvasPaint:(id<RAREiContext_iCanvasPaint>)strokeStyle;
- (void)setTextAlignWithRAREiContext_TextAlignEnum:(RAREiContext_TextAlignEnum *)textAlign;
- (void)setTextBaselineWithRAREiContext_TextBaselineEnum:(RAREiContext_TextBaselineEnum *)textBaseline;
- (void)setTransformWithFloat:(float)m11
                    withFloat:(float)m12
                    withFloat:(float)m21
                    withFloat:(float)m22
                    withFloat:(float)dx
                    withFloat:(float)dy;
- (void)setValuesWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                               withBoolean:(BOOL)strokePaint;
- (id<RAREiPlatformShape>)getClip;
- (id<RAREiComposite>)getComposite;
- (id<RAREiContext_iCanvasPaint>)getFillStyle;
- (NSString *)getFont;
- (float)getGlobalAlpha;
- (NSString *)getGlobalCompositeOperation;
- (NSString *)getLineCap;
- (NSString *)getLineJoin;
- (RAREUIStroke *)getLineStroke;
- (float)getLineWidth;
- (float)getMiterLimit;
- (id<RAREiContext_iCanvasPaint>)getStrokeStyle;
- (RAREiContext_TextAlignEnum *)getTextAlign;
- (RAREiContext_TextBaselineEnum *)getTextBaseline;
- (id<RAREiTransform>)getTransform;
- (RAREUIFont *)getUIFont;
- (BOOL)isDirty;
- (id<RAREiTransform>)createTransform;
+ (void)initalizeMaps OBJC_METHOD_FAMILY_NONE;
- (NSString *)getFontSizeWithNSString:(NSString *)s;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RAREContextState *)other;
@end

J2OBJC_FIELD_SETTER(RAREContextState, fontString_, NSString *)
J2OBJC_FIELD_SETTER(RAREContextState, textBaseline_, RAREiContext_TextBaselineEnum *)
J2OBJC_FIELD_SETTER(RAREContextState, textAlign_, RAREiContext_TextAlignEnum *)
J2OBJC_FIELD_SETTER(RAREContextState, strokeStyle_, id<RAREiContext_iCanvasPaint>)
J2OBJC_FIELD_SETTER(RAREContextState, stroke_, RAREUIStroke *)
J2OBJC_FIELD_SETTER(RAREContextState, lineJoin_, RAREUIStroke_JoinEnum *)
J2OBJC_FIELD_SETTER(RAREContextState, lineCap_, RAREUIStroke_CapEnum *)
J2OBJC_FIELD_SETTER(RAREContextState, font_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RAREContextState, fillStyle_, id<RAREiContext_iCanvasPaint>)
J2OBJC_FIELD_SETTER(RAREContextState, alphaComposite_, id<RAREiComposite>)
J2OBJC_FIELD_SETTER(RAREContextState, clip_, id<RAREiPlatformShape>)
J2OBJC_FIELD_SETTER(RAREContextState, transform__, id<RAREiTransform>)

typedef RAREContextState ComAppnativaRareUiCanvasContextState;

#endif // _RAREContextState_H_