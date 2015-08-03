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

import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iPath;
import com.appnativa.rare.ui.iPlatformPath;
/*-[
#import "AppleHelper.h"
 ]-*/

public class BezierPath implements iPlatformPath {
  Object proxy;
  float  x;
  float  y;

  public BezierPath() {
    this(createProxy());
  }

  public BezierPath(Object proxy) {
    this.proxy = proxy;
  }

  public native UIRectangle getBounds()
  /*-[
CGRect rect=[((UIBezierPath*)proxy_) bounds];
return [RAREUIRectangle fromRect: rect];
  ]-*/
  ;

  @Override
  public native iPath arc(float x, float y, float radius, float startAngle, float endAngle, boolean counterClockwise)
  /*-[
          [((UIBezierPath*)proxy_) addArcWithCenter: CGPointMake(x,y)
                                                                                                                                                                                                                          radius: radius
                                                                                                                                                                                                          startAngle: startAngle
                                                                                                                                                                                                          endAngle: endAngle
                                                                                                                                                                                                          clockwise: !counterClockwise];
          return self;
  ]-*/
  ;

  @Override
  public native iPath arcTo(float x1, float y1, float x2, float y2, float radius)
  /*-[
UIBezierPath* path=(UIBezierPath*)proxy_;
CGMutablePathRef ref=CGPathCreateMutableCopy(path.CGPath);
CGPathAddArcToPoint(ref, NULL, x1, y1, x2, y2, radius);
path.CGPath=ref;
CGPathRelease(ref);
    return self;
  ]-*/
  ;

  @Override
  public native void close()
  /*-[
   if(!((UIBezierPath*)proxy_).empty) {
      [((UIBezierPath*)proxy_) closePath];
   }
  ]-*/
  ;

  @Override
  public iPlatformPath copy() {
    BezierPath p = copyEx();

    p.x = x;
    p.y = y;

    return p;
  }

  protected native BezierPath copyEx()
  /*-[
     UIBezierPath* p=[UIBezierPath bezierPath];
     [p appendPath: (UIBezierPath*)proxy_] ;
     return [[RAREBezierPath alloc] initWithId: p];
  ]-*/
  ;

  @Override
  public native iPath cubicTo(float x1, float y1, float x2, float y2, float x3, float y3)
  /*-[
          [((UIBezierPath*)proxy_) addCurveToPoint: CGPointMake(x3,y3)
                                                                                                    controlPoint1: CGPointMake(x1,y1)
                                                                                                    controlPoint2: CGPointMake(x2,y2)];
          return self;
  ]-*/
  ;

  @Override
  public native void drawLine(float x1, float y1, float x2, float y2)
  /*-[
          UIBezierPath* path=(UIBezierPath*)proxy_;
if ((x1 != x_) || (y1 != y_)) {
                  [path  moveToPoint:CGPointMake(x1,y1)];
}

          [path  addLineToPoint:CGPointMake(y1,y1)];

x_ = x2;
y_ = y2;

  ]-*/
  ;

  @Override
  public native void drawRect(float x, float y, float width, float height)
  /*-[
UIBezierPath *p=[UIBezierPath bezierPathWithRect:CGRectMake(x,y,width,height)];
[((UIBezierPath*)proxy_) appendPath: p];
  ]-*/
  ;

  @Override
  public native void drawRoundedRect(float x, float y, float width, float height, float arcWidth, float arcHeight)
  /*-[
CGFloat radius=(arcHeight/2)+((arcWidth*arcWidth)/(8*arcHeight));
UIBezierPath *p=[UIBezierPath bezierPathWithRoundedRect:CGRectMake(x,y,width,height) cornerRadius:radius];
[((UIBezierPath*)proxy_) appendPath: p];
  ]-*/
  ;

  @Override
  public native iPath lineTo(float x, float y)
  /*-[
          [((UIBezierPath*)proxy_) addLineToPoint:CGPointMake(x,y)];
          return self;
  ]-*/
  ;

  @Override
  public native iPath moveTo(float x, float y)
  /*-[
          [((UIBezierPath*)proxy_) moveToPoint:CGPointMake(x,y)];
          return self;
  ]-*/
  ;

  @Override
  public native iPath quadTo(float x1, float y1, float x2, float y2)
  /*-[
CGPoint p1=CGPointMake(x1,y1);
CGPoint p2=CGPointMake(x2,y2);
[((UIBezierPath*)proxy_) addQuadCurveToPoint:p2 controlPoint: p1];
          return self;
  ]-*/
  ;

  @Override
  public native void reset()
  /*-[
          [((UIBezierPath*)proxy_) removeAllPoints];
  ]-*/
  ;

  @Override
  public native void rewind()
  /*-[
          [((UIBezierPath*)proxy_) removeAllPoints];
  ]-*/
  ;

  @Override
  public native iPath startLineDrawing(float x, float y, boolean move)    /*-[
             x_ = x;
             y_ = y;

             if (move) {
                     [((UIBezierPath*)proxy_) moveToPoint:CGPointMake(x,y)];
             }

             return self;

     ]-*/
  ;

  @Override
  public native boolean isEmpty()
  /*-[
                  return ((UIBezierPath*)proxy_).empty;
          ]-*/
  ;

  @Override
  public native boolean isPointInPath(float x, float y)
  /*-[
                  return [((UIBezierPath*)proxy_) containsPoint: CGPointMake(x,y)];
  ]-*/
  ;

  @Override
  public Object getPath() {
    return proxy;
  }

  static native Object createProxy()
  /*-[
                  return [UIBezierPath bezierPath];
  ]-*/
  ;

  @Override
  public UIRectangle getRectangle() {
    return null;
  }

  @Override
  public BezierPath getBezierPath() {
    return this;
  }

  public native void translate(float x, float y)
  /*-[
CGAffineTransform transform = CGAffineTransformMakeTranslation(x, y);
[((UIBezierPath*)proxy_)  applyTransform: transform];
  ]-*/
  ;
}
