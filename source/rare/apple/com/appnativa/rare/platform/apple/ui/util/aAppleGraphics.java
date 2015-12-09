/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.appnativa.rare.platform.apple.ui.util;

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIFontHelper;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iComposite;
import com.appnativa.rare.ui.iComposite.CompositeType;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformImage;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformShape;
import com.appnativa.rare.ui.iTransform;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;

/*-[
 #import <CoreText/CoreText.h>
 #import "IOSFloatArray.h"
 #import "AppleHelper.h"
 #import "RAREImageWrapper.h"
 #import "com/appnativa/rare/ui/UIImage.h"
 #import "com/appnativa/rare/ui/iComposite.h"
 ]-*/

/**
 *
 * @author Don DeCoteau
 *
 */
public abstract class aAppleGraphics implements iPlatformGraphics {
  iPlatformPaint          paint;
  protected float         miterLimit  = -1f;
  protected float         strokeWidth = -1f;
  protected UIColor       color;
  protected iComposite    composite;
  protected boolean       disposed;
  protected UIFont        font;
  protected UIStroke.Join joinStyle;
  protected UIStroke      lineStroke;
  protected Object        proxy;
  protected int           rotation;
  protected Object        tempPath;
  protected View          view;
  private boolean         componentPainterClipped;
  private iTransform      transform;
  private boolean         drawImagesFlipped = true;
  Stack                   stack             = new Stack();
  static Object           dictionaryProxy;

  public aAppleGraphics(Object g, View view) {
    proxy     = g;
    this.view = view;
  }

  protected aAppleGraphics() {}

  @Override
  public native void clearRect(float x, float y, float width, float height)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGRect rect=CGRectMake(x,y,width,height);
    CGContextClearRect(context,rect);
  ]-*/
  ;

  @Override
  public native void clearRect(UIColor bg, float x, float y, float width, float height)
  /*-[
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
  ]-*/
  ;

  @Override
  public native void clipRect(float x, float y, float width, float height)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextClipToRect(context,CGRectMake(x,y,width,height));
  ]-*/
  ;

  @Override
  public native void clipRect(float x, float y, float width, float height, Op op)
  /*-[
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
  ]-*/
  ;

  @Override
  public void clipShape(iPlatformShape shape) {
    UIRectangle r = shape.getRectangle();

    if (r != null) {
      clipRect(r.x, r.y, r.width, r.height);
    } else {
      addPathClip(shape.getBezierPath().getPath());
    }
  }

  @Override
  public native void clipShape(iPlatformShape shape, Op op)
  /*-[
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
  ]-*/
  ;

  @Override
  public boolean didComponentPainterClip() {
    return componentPainterClipped;
  }

  @Override
  public void dispose() {
    if ((proxy != null) && (stack.state > 0)) {
      restoreToState(0);
    }

    if (stack != null) {
      stack.dispose();
      stack = null;
    }

    proxy    = null;
    disposed = true;
    view     = null;
  }

  @Override
  public void drawChars(char[] data, int offset, int length, float x, float y, float height) {
    drawString(new String(data, offset, length), x, y, height);
  }

  @Override
  public native void drawImage(iPlatformImage img, float x, float y)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
    if(!image) return;
    BOOL pushed=[self setContextAsCurrent];
    [image drawAtX: x y: y];
    if(pushed) {
      [self restoreOldContextAsCurrent];
    }
  ]-*/
  ;

  @Override
  public native void drawImage(iPlatformImage img, float x, float y, iComposite composite)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)((RAREUIImage*)img)->proxy_;
    if(!image) return;
    BOOL pushed=[self setContextAsCurrent];
    [image drawAtX: x y: y composite: composite];
    if(pushed) {
      [self restoreOldContextAsCurrent];
    }
  ]-*/
  ;

  @Override
  public void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, iComposite composite) {
    drawImage(img, src, dst, null, composite);
  }

  @Override
  public native void drawImage(iPlatformImage img, float x, float y, float width, float height)
  /*-[
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
  ]-*/
  ;

  @Override
  public native void drawImage(iPlatformImage img, UIRectangle src, UIRectangle dst, ScalingType scalingType,
                               iComposite composite)
  /*-[
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
  ]-*/
  ;

  @Override
  public native void drawLine(float x1, float y1, float x2, float y2)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGPoint points[2]={CGPointMake(x1,y1),CGPointMake(x2,y2)};
    CGContextStrokeLineSegments(context,points,2);
  ]-*/
  ;

  @Override
  public native void drawRect(float x, float y, float width, float height)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextStrokeRect(context,CGRectMake(x,y,width,height));
  ]-*/
  ;

  @Override
  public void drawRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    fillOrStrokeBezierPath(getRoundedRectPath(x, y, width, height, arcWidth, arcHeight), false);
  }

  @Override
  public void drawShape(iPlatformShape shape, float x, float y) {
    UIRectangle r = shape.getRectangle();

    translate(x, y);

    if (r != null) {
      drawRect(r.x, r.y, r.width, r.height);
    } else {
      drawBezierPath(shape.getBezierPath().getPath());
    }

    translate(-x, -y);
  }

  @Override
  public native void fillRect(float x, float y, float width, float height)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    if(paint_) {
      [paint_ paintWithRAREiPlatformGraphics:self withFloat:x withFloat:y withFloat:width withFloat:height withInt:0];
    }
    else {
      CGContextFillRect(context,CGRectMake(x,y,width,height));
    }
  ]-*/
  ;

  @Override
  public void fillRoundRect(float x, float y, float width, float height, float arcWidth, float arcHeight) {
    fillOrStrokeBezierPath(getRoundedRectPath(x, y, width, height, arcWidth, arcHeight), true);
  }

  @Override
  public void fillShape(iPlatformShape shape, float x, float y) {
    UIRectangle r = shape.getRectangle();

    translate(x, y);

    if (r != null) {
      fillRect(r.x, r.y, r.width, r.height);
    } else {
      fillBezierPath(shape.getBezierPath().getPath());
    }

    translate(-x, -y);
  }

  public void reset(View view, Object context) {
    this.view  = view;
    this.proxy = context;
    disposed   = false;
    color      = null;
    font       = null;
    rotation   = 0;

    if (stack != null) {
      stack.dispose();
    }

    stack       = new Stack();
    miterLimit  = -1f;
    strokeWidth = -1f;
    joinStyle   = null;
    lineStroke  = null;
  }

  @Override
  public void restoreState() {
    if (stack.restore(this)) {
      restoreStateEx();
    }
  }

  protected native void restoreStateEx()
  /*-[
      CGContextRef context=(__bridge CGContextRef)[self getContextRef];
      CGContextRestoreGState(context);
   ]-*/
  ;

  @Override
  public native void restoreToState(int state)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    while(stack_->state_>state) {
     CGContextRestoreGState(context);
     [stack_ restoreWithRAREaAppleGraphics: self];
    }
  ]-*/
  ;

  @Override
  public native void rotate(int degrees)
  /*-[
    CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
    CGContextRotateCTM(ctx, degrees / 180.0 * M_PI);
    ]-*/
  ;

  @Override
  public native int saveState()
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextSaveGState(context);
    return [stack_ saveWithRAREaAppleGraphics: self];
   ]-*/
  ;

  @Override
  public native void scale(float sx, float sy)
  /*-[
     CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
     CGContextScaleCTM(ctx, sx, sy);
     ]-*/
  ;

  @Override
  public native void translate(float x, float y)
  /*-[
      CGContextRef context=(__bridge CGContextRef)[self getContextRef];
      CGContextTranslateCTM(context,x,y);
  ]-*/
  ;

  @Override
  public native void setColor(UIColor c)
  /*-[
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
  ]-*/
  ;

  @Override
  public void setComponentPainterClipped(boolean clipped) {
    componentPainterClipped = clipped;
  }

  @Override
  public void setComposite(iComposite composite) {
    this.composite = composite;

    float alpha;
    int   c;

    if (composite == null) {
      alpha = 1;
      c     = getNormalBlendMode();
    } else {
      alpha = composite.getAlpha();

      if (composite.getCompositeType() == CompositeType.COPY) {
        UIRectangle r = getClipBounds();

        clearRect(r.x, r.y, r.width, r.height);
        c = getCompositingOperation(CompositeType.SRC_OVER);
      } else {
        c = getCompositingOperation(composite.getCompositeType());
      }
    }

    setComposite(c, alpha);
    this.composite = composite;
  }

  @Override
  public native void setFont(UIFont f)
  /*-[
    font_=f;
  ]-*/
  ;

  @Override
  public void setPaint(iPlatformPaint p) {
    if (p != paint) {
      paint = null;
      color = null;

      if (p != null) {
        if (p.isPainter()) {
          setColor(p.getPlatformPaintColor());
          paint = p;
        } else {
          setColor(p.getPlatformPaintColor());
        }
      }
    }
  }

  @Override
  public native void setRenderingOptions(boolean anti_aliasing, boolean speed)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextSetShouldAntialias(context,anti_aliasing);
    CGContextSetInterpolationQuality(context,speed ? kCGInterpolationLow : kCGInterpolationDefault);
   ]-*/
  ;

  @Override
  public native void setRotation(int rotation)
  /*-[
    self->rotation_ = rotation;
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextRotateCTM(context, rotation / 180.0 * M_PI);
  ]-*/
  ;

  @Override
  public void setStroke(UIStroke stroke) {
    if (stroke != lineStroke) {
      if (stroke == null) {
        stroke = UIStroke.SOLID_STROKE;
      }

      lineStroke  = stroke;
      strokeWidth = stroke.width;
      setStrokeEx(stroke);
    }
  }

  @Override
  public native void setStrokeWidth(float width)
  /*-[
     strokeWidth_ = width;
     CGContextSetLineWidth((__bridge CGContextRef)[self getContextRef], width);
   ]-*/
  ;

  @Override
  public void setTransform(iTransform transform) {
    this.transform = transform;
    setTransformEx((Transform) ((transform == null)
                                ? null
                                : transform.getPlatformTransform()));
  }

  public native void setTransformEx(Transform tx)
  /*-[
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
   ]-*/
  ;

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public iPlatformShape getClip() {
    return getClipBounds();
  }

  @Override
  public native UIRectangle getClipBounds()
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGRect r=CGContextGetClipBoundingBox(context);
    return [[RAREUIRectangle alloc]initWithFloat: r.origin.x withFloat: r.origin.y withFloat: r.size.width withFloat: r.size.height];
  ]-*/
  ;

  @Override
  public UIColor getColor() {
    if (color == null) {
      color = ColorUtils.getForeground();
    }

    return color;
  }

  @Override
  public iPlatformComponent getComponent() {
    return (view == null)
           ? null
           : view.getComponent();
  }

  @Override
  public iComposite getComposite() {
    return composite;
  }

  public static native int getCompositingOperation(iComposite.CompositeType composite)
  /*-[
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
   ]-*/
  ;

  @Override
  public native Object getContextRef()
  /*-[
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
  ]-*/
  ;

  @Override
  public UIFont getFont() {
    if (font == null) {
      return UIFontHelper.getDefaultFont();
    }

    return font;
  }

  public static native int getImageInterpolation(ScalingType scalingType)
  /*-[
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
   ]-*/
  ;

  @Override
  public iPlatformPaint getPaint() {
    return (paint == null)
           ? color
           : paint;
  }

  @Override
  public int getRotation() {
    return rotation;
  }

  @Override
  public UIStroke getStroke() {
    if (lineStroke != null) {
      return lineStroke;
    }

    UIStroke stroke = new UIStroke(1);

    loadStroke(stroke);

    return stroke;
  }

  @Override
  public float getStrokeWidth() {
    if (strokeWidth != -1) {
      return strokeWidth;
    }

    if (lineStroke == null) {
      lineStroke = new UIStroke(1);
      loadStroke(lineStroke);
    }

    return lineStroke.width;
  }

  @Override
  public iTransform getTransform() {
    return transform;
  }

  @Override
  public View getView() {
    return view;
  }

  @Override
  public boolean isDisposed() {
    return disposed;
  }

  protected native void addPathClip(Object path)
  /*-[
    CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
    #if TARGET_OS_IPHONE
      CGPathRef p=((UIBezierPath*)path).CGPath;
    #else
      NSObject* pp=[AppleHelper quartzPath:(NSBezierPath*)path];
      CGPathRef p=(__bridge CGPathRef)pp;
    #endif
    CGContextAddPath(ctx,p);
    CGContextClip(ctx);
  ]-*/
  ;

  protected void drawBezierPath(Object path) {
    fillOrStrokeBezierPath(path, false);
  }

  protected void fillBezierPath(Object path) {
    fillOrStrokeBezierPath(path, true);
  }

  protected native void fillOrStrokeBezierPath(Object path, boolean fill)
  /*-[
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
  ]-*/
  ;

  protected void loadStroke(UIStroke stroke) {
    if (strokeWidth > -1) {
      stroke.setWidth(strokeWidth);
    }
  }

  protected abstract void restoreOldContextAsCurrent();

  protected native void setComposite(int composite, float alpha)
  /*-[
    CGContextRef context=(__bridge CGContextRef)[self getContextRef];
    CGContextSetBlendMode(context,composite);
    CGContextSetAlpha(context,alpha);
   ]-*/
  ;

  protected abstract boolean setContextAsCurrent();

  protected native void setPathClip(Object path)
  /*-[
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
  ]-*/
  ;

  protected native void setStrokeEx(UIStroke stroke)
  /*-[
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
  ]-*/
  ;

  protected abstract Object getRoundedRectPath(float x, float y, float width, float height, float arcWidth,
          float arcHeight);

  private native int getNormalBlendMode()
  /*-[
    return kCGBlendModeNormal;
  ]-*/
  ;

  static native Object addDictionaryAttribute(String name, Object value, boolean first)
  /*-[
    NSMutableDictionary* attributes=(NSMutableDictionary*)RAREaAppleGraphics_dictionaryProxy_;
    if(!attributes) {
      attributes=[NSMutableDictionary dictionary];
      RAREaAppleGraphics_dictionaryProxy_=attributes;
    }
    if(first) {
      [attributes removeAllObjects];
    }
    [attributes setObject:value forKey:name];
    return attributes;
  ]-*/
  ;

  public boolean isDrawImagesFlipped() {
    return drawImagesFlipped;
  }

  public void setDrawImagesFlipped(boolean drawImagesFlipped) {
    this.drawImagesFlipped = drawImagesFlipped;
  }

  static class Stack {
    public UIStroke   stroke;
    public UIColor    color;
    public Object     parent;
    public int        state;
    public UIFont     font;
    public iTransform transform;

    public int save(aAppleGraphics g) {
      color     = g.color;
      stroke    = g.lineStroke;
      transform = g.transform;
      font      = g.font;

      Stack s = new Stack();

      s.color     = color;
      s.stroke    = stroke;
      s.transform = transform;
      s.font      = font;
      s.state     = state + 1;
      s.parent    = this;
      g.stack     = s;

      return state;
    }

    public void dispose() {
      color     = null;
      stroke    = null;
      transform = null;
      font      = null;

      Stack s = (Stack) parent;

      parent = null;

      if (s != null) {
        s.dispose();
      }
    }

    public boolean restore(aAppleGraphics g) {
      if (parent == null) {
        return false;
      }

      color     = null;
      stroke    = null;
      transform = null;
      font      = null;

      Stack s = (Stack) parent;

      g.color      = s.color;
      g.lineStroke = s.stroke;
      g.transform  = s.transform;
      g.font       = s.font;
      g.stack      = s;

      return true;
    }
  }
}
