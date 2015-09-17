//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/util/aAppleGraphics.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSCharArray.h"
#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/util/aAppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/Transform.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/iComposite.h"
#include "com/appnativa/rare/ui/iGraphics.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformImage.h"
#include "com/appnativa/rare/ui/iPlatformPaint.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/rare/ui/iTransform.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#import <CoreText/CoreText.h>
 #import "IOSFloatArray.h"
 #import "AppleHelper.h"
 #import "RAREImageWrapper.h"
 #import "com/appnativa/rare/ui/UIImage.h"
 #import "com/appnativa/rare/ui/iComposite.h"

@implementation RAREaAppleGraphics

- (id)initWithId:(id)g
    withRAREView:(RAREView *)view {
  if (self = [super init]) {
    miterLimit_ = -1.0f;
    strokeWidth_ = -1.0f;
    drawImagesFlipped_ = YES;
    stack_ = [[RAREaAppleGraphics_Stack alloc] init];
    proxy_ = g;
    self->view_ = view;
  }
  return self;
}

- (id)init {
  if (self = [super init]) {
    miterLimit_ = -1.0f;
    strokeWidth_ = -1.0f;
    drawImagesFlipped_ = YES;
    stack_ = [[RAREaAppleGraphics_Stack alloc] init];
  }
  return self;
}

- (void)clearRectWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)width
                 withFloat:(float)height {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGRect rect=CGRectMake(x,y,width,height);
  CGContextClearRect(context,rect);
}

- (void)clearRectWithRAREUIColor:(RAREUIColor *)bg
                       withFloat:(float)x
                       withFloat:(float)y
                       withFloat:(float)width
                       withFloat:(float)height {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGRect rect=CGRectMake(x,y,width,height);
  CGContextClearRect(context,rect);
  if(bg!=nil) {
    float r=bg.getRed/255.0;
    float g=bg.getGreen/255.0;
    float b=bg.getBlue/255.0;
    float a=bg.getAlpha/255.0;
    CGContextSaveGState(context);
    CGContextSetRGBFillColor(context, r,g,b,a);
    CGContextFillRect(context,rect);
    CGContextRestoreGState(context);
  }
}

- (void)clipRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextClipToRect(context,CGRectMake(x,y,width,height));
}

- (void)clipRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height
 withRAREiGraphics_OpEnum:(RAREiGraphics_OpEnum *)op {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  
  switch ([op ordinal]) {
    case RAREiGraphics_Op_DIFFERENCE: {
      CGRect clipRect = CGContextGetClipBoundingBox(context);
      NSObject* pp=[AppleHelper getDiffecencePathBetweenRect:clipRect and:CGRectMake(x, y, width, height)];
      CGPathRef path=(__bridge CGPathRef)pp;
      if(path) {
        CGContextBeginPath(context);
        CGContextAddPath(context,path);
        CGContextClip(context);
      }
    }
    break;
    case RAREiGraphics_Op_REPLACE:
    CGContextClipToRect(context,CGRectMake(x,y,width,height));
    break;
    case RAREiGraphics_Op_UNION:{
      CGRect clipRect = CGContextGetClipBoundingBox(context);
      CGContextClipToRect(context,CGRectUnion(clipRect,CGRectMake(x, y, width, height)));
    }
    break;
    default:
    CGContextClipToRect(context,CGRectMake(x, y, width, height));
    break;
  }
}

- (void)clipShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape {
  RAREUIRectangle *r = [((id<RAREiPlatformShape>) nil_chk(shape)) getRectangle];
  if (r != nil) {
    [self clipRectWithFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
  }
  else {
    [self addPathClipWithId:[((id<RAREiPlatformPath>) nil_chk([shape getBezierPath])) getPath]];
  }
}

- (void)clipShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
               withRAREiGraphics_OpEnum:(RAREiGraphics_OpEnum *)op {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  NSObject* path=[[shape getBezierPath] getPath];
  CGRect bounds=CGRectZero;
  switch ([op ordinal]) {
    case RAREiGraphics_Op_DIFFERENCE: {
      CGRect clipRect = CGContextGetClipBoundingBox(context);
      NSObject* pp=[AppleHelper getDiffecencePathBetweenRect:clipRect and: bounds];
      CGPathRef path=(__bridge CGPathRef)pp;
      if(path) {
        CGContextBeginPath(context);
        CGContextAddPath(context,path);
        CGContextClip(context);
      }
    }
    break;
    case RAREiGraphics_Op_REPLACE:
    [self setPathClipWithId: path];
    break;
    case RAREiGraphics_Op_UNION: {
      CGRect clipRect = CGContextGetClipBoundingBox(context);
      CGContextClipToRect(context,CGRectUnion(clipRect,bounds));
    }
    break;
    default:
    [self addPathClipWithId:path];
    break;
  }
}

- (BOOL)didComponentPainterClip {
  return componentPainterClipped_;
}

- (void)dispose {
  if ((proxy_ != nil) && (((RAREaAppleGraphics_Stack *) nil_chk(stack_))->state_ > 0)) {
    [self restoreToStateWithInt:0];
  }
  if (stack_ != nil) {
    [stack_ dispose];
    stack_ = nil;
  }
  proxy_ = nil;
  disposed_ = YES;
  view_ = nil;
}

- (void)drawCharsWithCharArray:(IOSCharArray *)data
                       withInt:(int)offset
                       withInt:(int)length
                     withFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)height {
  [self drawStringWithNSString:[NSString stringWithCharacters:data offset:offset length:length] withFloat:x withFloat:y withFloat:height];
}

- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y {
  RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
  if(!image) return;
  BOOL pushed=[self setContextAsCurrent];
  [image drawAtX: x y: y];
  if(pushed) {
    [self restoreOldContextAsCurrent];
  }
}

- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y
                     withRAREiComposite:(id<RAREiComposite>)composite {
  RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
  if(!image) return;
  BOOL pushed=[self setContextAsCurrent];
  [image drawAtX: x y: y composite: composite];
  if(pushed) {
    [self restoreOldContextAsCurrent];
  }
}

- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                    withRAREUIRectangle:(RAREUIRectangle *)src
                    withRAREUIRectangle:(RAREUIRectangle *)dst
                     withRAREiComposite:(id<RAREiComposite>)composite {
  [self drawImageWithRAREiPlatformImage:img withRAREUIRectangle:src withRAREUIRectangle:dst withRAREiImagePainter_ScalingTypeEnum:nil withRAREiComposite:composite];
}

- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                              withFloat:(float)x
                              withFloat:(float)y
                              withFloat:(float)width
                              withFloat:(float)height {
  RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
  if(!image) return;
  #if TARGET_OS_IPHONE
  CGRect dstRect=CGRectMake(x,y,width,height);
  #else
  NSRect dstRect=NSMakeRect(x,y,width,height);
  #endif
  BOOL pushed=[self setContextAsCurrent];
  [image drawInRect:dstRect];
  if(pushed) {
    [self restoreOldContextAsCurrent];
  }
}

- (void)drawImageWithRAREiPlatformImage:(id<RAREiPlatformImage>)img
                    withRAREUIRectangle:(RAREUIRectangle *)src
                    withRAREUIRectangle:(RAREUIRectangle *)dst
  withRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType
                     withRAREiComposite:(id<RAREiComposite>)composite {
  RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
  if(!image) return;
  #if TARGET_OS_IPHONE
  CGRect dstRect=CGRectMake(dst->x_,dst->y_,dst->width_,dst->height_);
  CGRect srcRect=CGRectMake(src->x_,src->y_,src->width_,src->height_);
  #else
  NSRect dstRect=NSMakeRect(dst->x_,dst->y_,dst->width_,dst->height_);
  NSRect srcRect=NSMakeRect(src->x_,src->y_,src->width_,src->height_);
  #endif
  BOOL pushed=[self setContextAsCurrent];
  [image drawInRect:dstRect source:srcRect scaling: scalingType composite: composite drawFlipped: drawImagesFlipped_];
  if(pushed) {
    [self restoreOldContextAsCurrent];
  }
}

- (void)drawLineWithFloat:(float)x1
                withFloat:(float)y1
                withFloat:(float)x2
                withFloat:(float)y2 {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGPoint points[2]={CGPointMake(x1,y1),CGPointMake(x2,y2)};
  CGContextStrokeLineSegments(context,points,2);
}

- (void)drawRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextStrokeRect(context,CGRectMake(x,y,width,height));
}

- (void)drawRoundRectWithFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                     withFloat:(float)arcWidth
                     withFloat:(float)arcHeight {
  [self fillOrStrokeBezierPathWithId:[self getRoundedRectPathWithFloat:x withFloat:y withFloat:width withFloat:height withFloat:arcWidth withFloat:arcHeight] withBoolean:NO];
}

- (void)drawShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
                              withFloat:(float)x
                              withFloat:(float)y {
  RAREUIRectangle *r = [((id<RAREiPlatformShape>) nil_chk(shape)) getRectangle];
  [self translateWithFloat:x withFloat:y];
  if (r != nil) {
    [self drawRectWithFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
  }
  else {
    [self drawBezierPathWithId:[((id<RAREiPlatformPath>) nil_chk([shape getBezierPath])) getPath]];
  }
  [self translateWithFloat:-x withFloat:-y];
}

- (void)fillRectWithFloat:(float)x
                withFloat:(float)y
                withFloat:(float)width
                withFloat:(float)height {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  if(paint_) {
    [paint_ paintWithRAREiPlatformGraphics:self withFloat:x withFloat:y withFloat:width withFloat:height withInt:0];
  }
  else {
    CGContextFillRect(context,CGRectMake(x,y,width,height));
  }
}

- (void)fillRoundRectWithFloat:(float)x
                     withFloat:(float)y
                     withFloat:(float)width
                     withFloat:(float)height
                     withFloat:(float)arcWidth
                     withFloat:(float)arcHeight {
  [self fillOrStrokeBezierPathWithId:[self getRoundedRectPathWithFloat:x withFloat:y withFloat:width withFloat:height withFloat:arcWidth withFloat:arcHeight] withBoolean:YES];
}

- (void)fillShapeWithRAREiPlatformShape:(id<RAREiPlatformShape>)shape
                              withFloat:(float)x
                              withFloat:(float)y {
  RAREUIRectangle *r = [((id<RAREiPlatformShape>) nil_chk(shape)) getRectangle];
  [self translateWithFloat:x withFloat:y];
  if (r != nil) {
    [self fillRectWithFloat:r->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
  }
  else {
    [self fillBezierPathWithId:[((id<RAREiPlatformPath>) nil_chk([shape getBezierPath])) getPath]];
  }
  [self translateWithFloat:-x withFloat:-y];
}

- (void)resetWithRAREView:(RAREView *)view
                   withId:(id)context {
  self->view_ = view;
  self->proxy_ = context;
  disposed_ = NO;
  color_ = nil;
  font_ = nil;
  rotation_ = 0;
  if (stack_ != nil) {
    [stack_ dispose];
  }
  stack_ = [[RAREaAppleGraphics_Stack alloc] init];
  miterLimit_ = -1.0f;
  strokeWidth_ = -1.0f;
  joinStyle_ = nil;
  lineStroke_ = nil;
}

- (void)restoreState {
  if ([((RAREaAppleGraphics_Stack *) nil_chk(stack_)) restoreWithRAREaAppleGraphics:self]) {
    [self restoreStateEx];
  }
}

- (void)restoreStateEx {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextRestoreGState(context);
}

- (void)restoreToStateWithInt:(int)state {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  while(stack_->state_>state) {
    CGContextRestoreGState(context);
    [stack_ restoreWithRAREaAppleGraphics: self];
  }
}

- (void)rotateWithInt:(int)degrees {
  CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
  CGContextRotateCTM(ctx, degrees / 180.0 * M_PI);
}

- (int)saveState {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextSaveGState(context);
  return [stack_ saveWithRAREaAppleGraphics: self];
}

- (void)scale__WithFloat:(float)sx
               withFloat:(float)sy {
  CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
  CGContextScaleCTM(ctx, sx, sy);
}

- (void)translateWithFloat:(float)x
                 withFloat:(float)y {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextTranslateCTM(context,x,y);
}

- (void)setColorWithRAREUIColor:(RAREUIColor *)c {
  if(c!=color_) {
    color_=c;
    CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
    float r=c.getRed/255.0;
    float g=c.getGreen/255.0;
    float b=c.getBlue/255.0;
    float a=c.getAlpha/255.0;
    CGContextSetRGBStrokeColor(ctx, r,g,b,a);
    CGContextSetRGBFillColor(ctx, r,g,b,a);
  }
}

- (void)setComponentPainterClippedWithBoolean:(BOOL)clipped {
  componentPainterClipped_ = clipped;
}

- (void)setCompositeWithRAREiComposite:(id<RAREiComposite>)composite {
  self->composite_ = composite;
  float alpha;
  int c;
  if (composite == nil) {
    alpha = 1;
    c = [self getNormalBlendMode];
  }
  else {
    alpha = [composite getAlpha];
    if ([composite getCompositeType] == [RAREiComposite_CompositeTypeEnum COPY]) {
      RAREUIRectangle *r = [self getClipBounds];
      [self clearRectWithFloat:((RAREUIRectangle *) nil_chk(r))->x_ withFloat:r->y_ withFloat:r->width_ withFloat:r->height_];
      c = [RAREaAppleGraphics getCompositingOperationWithRAREiComposite_CompositeTypeEnum:[RAREiComposite_CompositeTypeEnum SRC_OVER]];
    }
    else {
      c = [RAREaAppleGraphics getCompositingOperationWithRAREiComposite_CompositeTypeEnum:[composite getCompositeType]];
    }
  }
  [self setCompositeWithInt:c withFloat:alpha];
  self->composite_ = composite;
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  font_=f;
}

- (void)setPaintWithRAREiPlatformPaint:(id<RAREiPlatformPaint>)p {
  if (p != paint_) {
    paint_ = nil;
    color_ = nil;
    if (p != nil) {
      if ([p isPainter]) {
        [self setColorWithRAREUIColor:[p getPlatformPaintColor]];
        paint_ = p;
      }
      else {
        [self setColorWithRAREUIColor:[p getPlatformPaintColor]];
      }
    }
  }
}

- (void)setRenderingOptionsWithBoolean:(BOOL)anti_aliasing
                           withBoolean:(BOOL)speed {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextSetShouldAntialias(context,anti_aliasing);
  CGContextSetInterpolationQuality(context,speed ? kCGInterpolationLow : kCGInterpolationDefault);
}

- (void)setRotationWithInt:(int)rotation {
  self->rotation_ = rotation;
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextRotateCTM(context, rotation / 180.0 * M_PI);
}

- (void)setStrokeWithRAREUIStroke:(RAREUIStroke *)stroke {
  if (stroke != lineStroke_) {
    if (stroke == nil) {
      stroke = [RAREUIStroke SOLID_STROKE];
    }
    lineStroke_ = stroke;
    strokeWidth_ = ((RAREUIStroke *) nil_chk(stroke))->width_;
    [self setStrokeExWithRAREUIStroke:stroke];
  }
}

- (void)setStrokeWidthWithFloat:(float)width {
  strokeWidth_ = width;
  CGContextSetLineWidth((__bridge CGContextRef)[self getContextRef], width);
}

- (void)setTransformWithRAREiTransform:(id<RAREiTransform>)transform {
  self->transform_ = transform;
  [self setTransformExWithRARETransform:(RARETransform *) check_class_cast(((transform == nil) ? nil : [transform getPlatformTransform]), [RARETransform class])];
}

- (void)setTransformExWithRARETransform:(RARETransform *)tx {
  CGAffineTransform t;
  if(!tx) {
    t=CGAffineTransformIdentity;
  }
  else {
    t=CGAffineTransformMake (tx->a_,tx->b_,tx->c_,tx->d_,tx->tx_,tx->ty_);
  }
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGAffineTransform ot=CGContextGetCTM(context);
  CGContextConcatCTM(context,  CGAffineTransformInvert(ot));
  CGContextConcatCTM(context,t);
}

- (void)setViewWithRAREView:(RAREView *)view {
  self->view_ = view;
}

- (id<RAREiPlatformShape>)getClip {
  return [self getClipBounds];
}

- (RAREUIRectangle *)getClipBounds {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGRect r=CGContextGetClipBoundingBox(context);
  return [[RAREUIRectangle alloc]initWithFloat: r.origin.x withFloat: r.origin.y withFloat: r.size.width withFloat: r.size.height];
}

- (RAREUIColor *)getColor {
  if (color_ == nil) {
    color_ = [RAREUIColor BLACK];
  }
  return color_;
}

- (id<RAREiPlatformComponent>)getComponent {
  return (view_ == nil) ? nil : [view_ getComponent];
}

- (id<RAREiComposite>)getComposite {
  return composite_;
}

+ (int)getCompositingOperationWithRAREiComposite_CompositeTypeEnum:(RAREiComposite_CompositeTypeEnum *)composite {
  switch(composite.ordinal) {
    case  RAREiComposite_CompositeType_DST_ATOP:
    return kCGBlendModeDestinationAtop;
    case  RAREiComposite_CompositeType_DST_IN:
    return   kCGBlendModeDestinationIn;
    case  RAREiComposite_CompositeType_DST_OUT:
    return  kCGBlendModeDestinationOut;
    case  RAREiComposite_CompositeType_DST_OVER:
    return  kCGBlendModeDestinationOver;
    case  RAREiComposite_CompositeType_SRC_ATOP:
    return kCGBlendModeSourceAtop;
    case  RAREiComposite_CompositeType_SRC_IN:
    return kCGBlendModeSourceIn;
    case  RAREiComposite_CompositeType_SRC_OUT:
    return kCGBlendModeSourceOut;
    case  RAREiComposite_CompositeType_XOR:
    return kCGBlendModeXOR;
    case  RAREiComposite_CompositeType_LIGHTEN:
    return kCGBlendModeLighten;
    case  RAREiComposite_CompositeType_DARKEN:
    return kCGBlendModeDarken;
    case  RAREiComposite_CompositeType_COPY:
    return kCGBlendModeCopy;
    case  RAREiComposite_CompositeType_CLEAR:
    return kCGBlendModeClear;
    default:
    return kCGBlendModeNormal;
  }
}

- (id)getContextRef {
  NSObject* context;
  #if TARGET_OS_IPHONE
  if(proxy_) {
    context=proxy_;
  }
  else {
    context=(__bridge NSObject *)(UIGraphicsGetCurrentContext());
  }
  #else
  if(proxy_) {
    context=(__bridge NSObject *)[((NSGraphicsContext*)proxy_) graphicsPort];
  }
  else {
    context=(__bridge NSObject *)[[NSGraphicsContext currentContext] graphicsPort];
  }
  #endif
  return context;
}

- (RAREUIFont *)getFont {
  if (font_ == nil) {
    return [RAREUIFontHelper getDefaultFont];
  }
  return font_;
}

+ (int)getImageInterpolationWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType {
  switch ([scalingType ordinal]) {
    case RAREiImagePainter_ScalingType_BICUBIC:
    case RAREiImagePainter_ScalingType_BICUBIC_CACHED:
    case RAREiImagePainter_ScalingType_PROGRESSIVE_BICUBIC:
    return kCGInterpolationHigh;
    case RAREiImagePainter_ScalingType_BILINEAR:
    case RAREiImagePainter_ScalingType_BILINEAR_CACHED:
    case RAREiImagePainter_ScalingType_PROGRESSIVE_BILINEAR:
    return kCGInterpolationMedium;
    case RAREiImagePainter_ScalingType_NEAREST_NEIGHBOR:
    case RAREiImagePainter_ScalingType_PROGRESSIVE_NEAREST_NEIGHBOR:
    case RAREiImagePainter_ScalingType_PROGRESSIVE_NEAREST_NEIGHBOR_CACHED:
    default:
    return kCGInterpolationDefault;
  }
}

- (id<RAREiPlatformPaint>)getPaint {
  return (paint_ == nil) ? color_ : ((id) paint_);
}

- (int)getRotation {
  return rotation_;
}

- (RAREUIStroke *)getStroke {
  if (lineStroke_ != nil) {
    return lineStroke_;
  }
  RAREUIStroke *stroke = [[RAREUIStroke alloc] initWithFloat:1];
  [self loadStrokeWithRAREUIStroke:stroke];
  return stroke;
}

- (float)getStrokeWidth {
  if (strokeWidth_ != -1) {
    return strokeWidth_;
  }
  if (lineStroke_ == nil) {
    lineStroke_ = [[RAREUIStroke alloc] initWithFloat:1];
    [self loadStrokeWithRAREUIStroke:lineStroke_];
  }
  return ((RAREUIStroke *) nil_chk(lineStroke_))->width_;
}

- (id<RAREiTransform>)getTransform {
  return transform_;
}

- (RAREView *)getView {
  return view_;
}

- (BOOL)isDisposed {
  return disposed_;
}

- (void)addPathClipWithId:(id)path {
  CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
  #if TARGET_OS_IPHONE
  CGPathRef p=((UIBezierPath*)path).CGPath;
  #else
  NSObject* pp=[AppleHelper quartzPath:(NSBezierPath*)path];
  CGPathRef p=(__bridge CGPathRef)pp;
  #endif
  CGContextAddPath(ctx,p);
  CGContextClip(ctx);
}

- (void)drawBezierPathWithId:(id)path {
  [self fillOrStrokeBezierPathWithId:path withBoolean:NO];
}

- (void)fillBezierPathWithId:(id)path {
  [self fillOrStrokeBezierPathWithId:path withBoolean:YES];
}

- (void)fillOrStrokeBezierPathWithId:(id)path
                         withBoolean:(BOOL)fill {
  CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
  #if TARGET_OS_IPHONE
  CGPathRef p=((UIBezierPath*)path).CGPath;
  #else
  NSObject* pp=[AppleHelper quartzPath:(NSBezierPath*)path];
  CGPathRef p=(__bridge CGPathRef)pp;
  #endif
  CGContextSaveGState(ctx);
  CGContextBeginPath(ctx);
  CGContextAddPath(ctx,p);
  if(fill) {
    if(paint_) {
      CGRect rect=CGContextGetPathBoundingBox(ctx);
      CGContextClip(ctx);
      [paint_ paintWithRAREiPlatformGraphics:self withFloat:rect.origin.x withFloat:rect.origin.y withFloat:rect.size.width withFloat:rect.size.height withInt:0];
    }
    else {
      CGContextFillPath(ctx);
    }
  }
  else {
    CGContextStrokePath(ctx);
  }
  CGContextRestoreGState(ctx);
}

- (void)loadStrokeWithRAREUIStroke:(RAREUIStroke *)stroke {
  if (strokeWidth_ > -1) {
    [((RAREUIStroke *) nil_chk(stroke)) setWidthWithFloat:strokeWidth_];
  }
}

- (void)restoreOldContextAsCurrent {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)setCompositeWithInt:(int)composite
                  withFloat:(float)alpha {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  CGContextSetBlendMode(context,composite);
  CGContextSetAlpha(context,alpha);
}

- (BOOL)setContextAsCurrent {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setPathClipWithId:(id)path {
  CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
  #if TARGET_OS_IPHONE
  CGPathRef p=((UIBezierPath*)path).CGPath;
  #else
  NSObject* pp=[AppleHelper quartzPath:(NSBezierPath*)path];
  CGPathRef p=(__bridge CGPathRef)pp;
  #endif
  CGContextBeginPath(ctx);
  CGContextAddPath(ctx,p);
  CGContextClip(ctx);
}

- (void)setStrokeExWithRAREUIStroke:(RAREUIStroke *)stroke {
  CGContextRef context=(__bridge CGContextRef)[self getContextRef];
  switch ([stroke->cap_ ordinal]) {
    case RAREUIStroke_Cap_BUTT:
    CGContextSetLineCap(context, kCGLineCapButt);
    break;
    case RAREUIStroke_Cap_ROUND:
    CGContextSetLineCap(context, kCGLineCapRound);
    break;
    case RAREUIStroke_Cap_SQUARE:
    CGContextSetLineCap(context, kCGLineCapSquare);
    break;
  }
  switch ([stroke->join_ ordinal]) {
    case RAREUIStroke_Join_BEVEL:
    CGContextSetLineJoin(context, kCGLineJoinBevel);
    break;
    case RAREUIStroke_Join_MITER:
    CGContextSetLineJoin(context, kCGLineJoinMiter);
    break;
    case RAREUIStroke_Join_ROUND:
    CGContextSetLineJoin(context, kCGLineJoinRound);
    break;
  }
  CGContextSetLineWidth(context, stroke->width_);
  CGContextSetMiterLimit(context, stroke->miterLimit_);
  if(stroke->dashIntervals_ && stroke->dashIntervals_.count>0) {
    NSInteger len=stroke->dashIntervals_.count;
    CGFloat dash[len];
    for(int i=0;i<len;i++) {
      dash[i]=[stroke->dashIntervals_ floatAtIndex:i];
    }
    CGContextSetLineDash(context,stroke->dashPhase_,dash,len);
  }
}

- (id)getRoundedRectPathWithFloat:(float)x
                        withFloat:(float)y
                        withFloat:(float)width
                        withFloat:(float)height
                        withFloat:(float)arcWidth
                        withFloat:(float)arcHeight {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (int)getNormalBlendMode {
  return kCGBlendModeNormal;
}

- (BOOL)isDrawImagesFlipped {
  return drawImagesFlipped_;
}

- (void)setDrawImagesFlippedWithBoolean:(BOOL)drawImagesFlipped {
  self->drawImagesFlipped_ = drawImagesFlipped;
}

- (void)drawStringWithNSString:(NSString *)param0
                     withFloat:(float)param1
                     withFloat:(float)param2
                     withFloat:(float)param3 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (void)copyAllFieldsTo:(RAREaAppleGraphics *)other {
  [super copyAllFieldsTo:other];
  other->color_ = color_;
  other->componentPainterClipped_ = componentPainterClipped_;
  other->composite_ = composite_;
  other->disposed_ = disposed_;
  other->drawImagesFlipped_ = drawImagesFlipped_;
  other->font_ = font_;
  other->joinStyle_ = joinStyle_;
  other->lineStroke_ = lineStroke_;
  other->miterLimit_ = miterLimit_;
  other->paint_ = paint_;
  other->proxy_ = proxy_;
  other->rotation_ = rotation_;
  other->stack_ = stack_;
  other->strokeWidth_ = strokeWidth_;
  other->tempPath_ = tempPath_;
  other->transform_ = transform_;
  other->view_ = view_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "init", NULL, NULL, 0x4, NULL },
    { "clearRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "clearRectWithRAREUIColor:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "clipRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "clipRectWithFloat:withFloat:withFloat:withFloat:withRAREiGraphics_OpEnum:", NULL, "V", 0x101, NULL },
    { "clipShapeWithRAREiPlatformShape:withRAREiGraphics_OpEnum:", NULL, "V", 0x101, NULL },
    { "didComponentPainterClip", NULL, "Z", 0x1, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:withRAREiComposite:", NULL, "V", 0x101, NULL },
    { "drawImageWithRAREiPlatformImage:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "drawImageWithRAREiPlatformImage:withRAREUIRectangle:withRAREUIRectangle:withRAREiImagePainter_ScalingTypeEnum:withRAREiComposite:", NULL, "V", 0x101, NULL },
    { "drawLineWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "drawRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "fillRectWithFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "restoreStateEx", NULL, "V", 0x104, NULL },
    { "restoreToStateWithInt:", NULL, "V", 0x101, NULL },
    { "rotateWithInt:", NULL, "V", 0x101, NULL },
    { "saveState", NULL, "I", 0x101, NULL },
    { "scale__WithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "translateWithFloat:withFloat:", NULL, "V", 0x101, NULL },
    { "setColorWithRAREUIColor:", NULL, "V", 0x101, NULL },
    { "setFontWithRAREUIFont:", NULL, "V", 0x101, NULL },
    { "setRenderingOptionsWithBoolean:withBoolean:", NULL, "V", 0x101, NULL },
    { "setRotationWithInt:", NULL, "V", 0x101, NULL },
    { "setStrokeWidthWithFloat:", NULL, "V", 0x101, NULL },
    { "setTransformExWithRARETransform:", NULL, "V", 0x101, NULL },
    { "getClip", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "getClipBounds", NULL, "LRAREUIRectangle", 0x101, NULL },
    { "getColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComposite", NULL, "LRAREiComposite", 0x1, NULL },
    { "getCompositingOperationWithRAREiComposite_CompositeTypeEnum:", NULL, "I", 0x109, NULL },
    { "getContextRef", NULL, "LNSObject", 0x101, NULL },
    { "getFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getImageInterpolationWithRAREiImagePainter_ScalingTypeEnum:", NULL, "I", 0x109, NULL },
    { "getPaint", NULL, "LRAREiPlatformPaint", 0x1, NULL },
    { "getStroke", NULL, "LRAREUIStroke", 0x1, NULL },
    { "getTransform", NULL, "LRAREiTransform", 0x1, NULL },
    { "getView", NULL, "LRAREView", 0x1, NULL },
    { "isDisposed", NULL, "Z", 0x1, NULL },
    { "addPathClipWithId:", NULL, "V", 0x104, NULL },
    { "drawBezierPathWithId:", NULL, "V", 0x4, NULL },
    { "fillBezierPathWithId:", NULL, "V", 0x4, NULL },
    { "fillOrStrokeBezierPathWithId:withBoolean:", NULL, "V", 0x104, NULL },
    { "loadStrokeWithRAREUIStroke:", NULL, "V", 0x4, NULL },
    { "restoreOldContextAsCurrent", NULL, "V", 0x404, NULL },
    { "setCompositeWithInt:withFloat:", NULL, "V", 0x104, NULL },
    { "setContextAsCurrent", NULL, "Z", 0x404, NULL },
    { "setPathClipWithId:", NULL, "V", 0x104, NULL },
    { "setStrokeExWithRAREUIStroke:", NULL, "V", 0x104, NULL },
    { "getRoundedRectPathWithFloat:withFloat:withFloat:withFloat:withFloat:withFloat:", NULL, "LNSObject", 0x404, NULL },
    { "getNormalBlendMode", NULL, "I", 0x102, NULL },
    { "isDrawImagesFlipped", NULL, "Z", 0x1, NULL },
    { "drawStringWithNSString:withFloat:withFloat:withFloat:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "paint_", NULL, 0x0, "LRAREiPlatformPaint" },
    { "miterLimit_", NULL, 0x4, "F" },
    { "strokeWidth_", NULL, 0x4, "F" },
    { "color_", NULL, 0x4, "LRAREUIColor" },
    { "composite_", NULL, 0x4, "LRAREiComposite" },
    { "disposed_", NULL, 0x4, "Z" },
    { "font_", NULL, 0x4, "LRAREUIFont" },
    { "joinStyle_", NULL, 0x4, "LRAREUIStroke_JoinEnum" },
    { "lineStroke_", NULL, 0x4, "LRAREUIStroke" },
    { "proxy_", NULL, 0x4, "LNSObject" },
    { "rotation_", NULL, 0x4, "I" },
    { "tempPath_", NULL, 0x4, "LNSObject" },
    { "view_", NULL, 0x4, "LRAREView" },
    { "stack_", NULL, 0x0, "LRAREaAppleGraphics_Stack" },
  };
  static J2ObjcClassInfo _RAREaAppleGraphics = { "aAppleGraphics", "com.appnativa.rare.platform.apple.ui.util", NULL, 0x401, 54, methods, 14, fields, 0, NULL};
  return &_RAREaAppleGraphics;
}

@end
@implementation RAREaAppleGraphics_Stack

- (int)saveWithRAREaAppleGraphics:(RAREaAppleGraphics *)g {
  color_ = ((RAREaAppleGraphics *) nil_chk(g))->color_;
  stroke_ = g->lineStroke_;
  transform_ = g->transform_;
  font_ = g->font_;
  RAREaAppleGraphics_Stack *s = [[RAREaAppleGraphics_Stack alloc] init];
  s->color_ = color_;
  s->stroke_ = stroke_;
  s->transform_ = transform_;
  s->font_ = font_;
  s->state_ = state_ + 1;
  s->parent_ = self;
  g->stack_ = s;
  return state_;
}

- (void)dispose {
  color_ = nil;
  stroke_ = nil;
  transform_ = nil;
  font_ = nil;
  RAREaAppleGraphics_Stack *s = (RAREaAppleGraphics_Stack *) check_class_cast(parent_, [RAREaAppleGraphics_Stack class]);
  parent_ = nil;
  if (s != nil) {
    [s dispose];
  }
}

- (BOOL)restoreWithRAREaAppleGraphics:(RAREaAppleGraphics *)g {
  if (parent_ == nil) {
    return NO;
  }
  color_ = nil;
  stroke_ = nil;
  transform_ = nil;
  font_ = nil;
  RAREaAppleGraphics_Stack *s = (RAREaAppleGraphics_Stack *) check_class_cast(parent_, [RAREaAppleGraphics_Stack class]);
  ((RAREaAppleGraphics *) nil_chk(g))->color_ = ((RAREaAppleGraphics_Stack *) nil_chk(s))->color_;
  g->lineStroke_ = s->stroke_;
  g->transform_ = s->transform_;
  g->font_ = s->font_;
  g->stack_ = s;
  return YES;
}

- (id)init {
  return [super init];
}

- (void)copyAllFieldsTo:(RAREaAppleGraphics_Stack *)other {
  [super copyAllFieldsTo:other];
  other->color_ = color_;
  other->font_ = font_;
  other->parent_ = parent_;
  other->state_ = state_;
  other->stroke_ = stroke_;
  other->transform_ = transform_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "restoreWithRAREaAppleGraphics:", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "stroke_", NULL, 0x1, "LRAREUIStroke" },
    { "color_", NULL, 0x1, "LRAREUIColor" },
    { "parent_", NULL, 0x1, "LNSObject" },
    { "state_", NULL, 0x1, "I" },
    { "font_", NULL, 0x1, "LRAREUIFont" },
    { "transform_", NULL, 0x1, "LRAREiTransform" },
  };
  static J2ObjcClassInfo _RAREaAppleGraphics_Stack = { "Stack", "com.appnativa.rare.platform.apple.ui.util", "aAppleGraphics", 0x8, 1, methods, 6, fields, 0, NULL};
  return &_RAREaAppleGraphics_Stack;
}

@end
