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

package com.appnativa.rare.ui;

import com.appnativa.rare.platform.apple.ui.view.View;

public class Transform implements iTransform, Cloneable {
  float a = 1;
  float d = 1;
  float b;
  float c;
  float tx;
  float ty;

  public Transform() {
    makeItentity();
  }

  public native void makeItentity()    /*-[
      CGAffineTransform t=CGAffineTransformIdentity;
      a_=t.a;
      b_=t.b;
      c_=t.c;
      d_=t.d;
      tx_=t.tx;
      ty_=t.ty;
    ]-*/
  ;

  @Override
  public native void concatenate(iTransform transform)    /*-[
      RARETransform* tx=(RARETransform*)transform;
      CGAffineTransform t1=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
      CGAffineTransform t2=CGAffineTransformMake (tx->a_,tx->b_,tx->c_,tx->d_,tx->tx_,tx->ty_);
      CGAffineTransform t=CGAffineTransformConcat(t1,t2);
      a_=t.a;
      b_=t.b;
      c_=t.c;
      d_=t.d;
      tx_=t.tx;
      ty_=t.ty;
    ]-*/
  ;

  @Override
  public void concatenate(float m00, float m10, float m01, float m11, float m02, float m12) {
    Transform t = new Transform();

    t.setTransform(m00, m10, m01, m11, m02, m12);
    concatenate(t);
  }

  @Override
  public native void rotate(float angle)      /*-[
        CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
        t=CGAffineTransformRotate(t,angle);
        a_=t.a;
        b_=t.b;
        c_=t.c;
        d_=t.d;
        tx_=t.tx;
        ty_=t.ty;
      ]-*/
  ;

  public native void apply(View v)    /*-[
       CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
       ((UIView*)v.getProxy).transform=t;
      ]-*/
  ;

  @Override
  public native void scale(float sx, float sy)      /*-[
        CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
        t=CGAffineTransformScale(t,sx,sy);
        a_=t.a;
        b_=t.b;
        c_=t.c;
        d_=t.d;
        tx_=t.tx;
        ty_=t.ty;
      ]-*/
  ;

  @Override
  public native void shear(float shx, float shy)      /*-[
        CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
        CGAffineTransform shear=CGAffineTransformMake(1.f, shy, shx, 1.f, 0.f, 0.f);
        t=CGAffineTransformConcat(t, shear);
        a_=t.a;
        b_=t.b;
        c_=t.c;
        d_=t.d;
        tx_=t.tx;
        ty_=t.ty;
      ]-*/
  ;

  public native UIPoint transform(float x, float y)      /*-[
        CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
        CGPoint p=CGPointMake(x,y);
        CGPointApplyAffineTransform (p,t);
        return [[RAREUIPoint alloc] initWithFloat: x withFloat: y];
      ]-*/
  ;

  @Override
  public native void translate(float x, float y)     /*-[
        CGAffineTransform t=CGAffineTransformMake (a_,b_,c_,d_,tx_,ty_);
        t=CGAffineTransformTranslate(t,x,y);
        a_=t.a;
        b_=t.b;
        c_=t.c;
        d_=t.d;
        tx_=t.tx;
        ty_=t.ty;
      ]-*/
  ;

  @Override
  public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
    a  = m00;
    b  = m01;
    c  = m10;
    d  = m11;
    tx = m02;
    ty = m12;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      throw new InternalError();
    }
  }

  @Override
  public Object getPlatformTransform() {
    return this;
  }

  @Override
  public iTransform cloneCopy() {
    return (iTransform) clone();
  }
}
