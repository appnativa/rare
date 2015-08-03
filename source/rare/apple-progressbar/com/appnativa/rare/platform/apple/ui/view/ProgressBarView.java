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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.Platform;
import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.iProgressBar;

/*-[
#import "RAREAPProgressView.h"
#import "RAREActivityIndicatorView.h"
]-*/
public class ProgressBarView extends ControlView implements iProgressBar {
  boolean indeterminate;

  public ProgressBarView() {
    this(true);
  }

  public ProgressBarView(boolean indeterminate) {
    super(createProxy(indeterminate));
    this.indeterminate = indeterminate;
    setForegroundColor(ColorUtils.getForeground());
  }

  @Override
  public void setIndeterminate(boolean indeterminate) {
    if (this.indeterminate != indeterminate) {
      this.indeterminate = indeterminate;

      if (Platform.isIOS()) {
        swapOutProxy(createProxy(indeterminate));
      }
    }
  }

  @Override
  protected native void setForegroundColorEx(UIColor fg)
  /*-[
    if(fg && [proxy_ isKindOfClass:[RAREActivityIndicatorView class]]) {
      ((RAREActivityIndicatorView*)proxy_).color=(UIColor*)[fg getAPColor];
    }
   ]-*/
  ;

  @Override
  public native void setMaximum(int maximum)
  /*-[
  if([proxy_ isKindOfClass:[RAREAPProgressView class]]) {
      [((RAREAPProgressView*)proxy_) setMaxValue: maximum];
    }
  ]-*/
  ;

  @Override
  public native void setMinimum(int minimum)
  /*-[
  if([proxy_ isKindOfClass:[RAREAPProgressView class]]) {
      [((RAREAPProgressView*)proxy_) setMinValue: minimum];
    }
  ]-*/
  ;

  @Override
  public native void setValue(int value)
  /*-[
    if([proxy_ isKindOfClass:[RAREAPProgressView class]]) {
      [((RAREAPProgressView*)proxy_) setValue: value];
    }
  ]-*/
  ;

  @Override
  public Component getComponent() {
    if (component == null) {
      component = new Component(this);
    }

    return component;
  }

  @Override
  public native int getValue()
  /*-[
    if([proxy_ isKindOfClass:[RAREAPProgressView class]]) {
      return (int)[((RAREAPProgressView*)proxy_) getValue];
    }
    return 0;
  ]-*/
  ;

  @Override
  public boolean isIndeterminate() {
    return indeterminate;
  }

  private native static Object createProxy()
  /*-[
    return [[RAREAPProgressView alloc]init];
  ]-*/
  ;

  private native static Object createProxy(boolean indeterminate)
  /*-[
     if(indeterminate) {
    return [[RAREActivityIndicatorView alloc]init];
     }
     else {
        return [[RAREAPProgressView alloc]init];
     }
  ]-*/
  ;

  private native void swapOutProxy(Object newProxy)
  /*-[
    UIView* sv=((UIView*)proxy_).superview;
    [((UIView*)proxy_) removeFromSuperview];
    proxy_=newProxy;
    if(sv) {
      [sv addSubview:(UIView*)newProxy];
    }
  ]-*/
  ;

  @Override
  public native void setGraphicSize(int size)
  /*-[
    if([proxy_ isKindOfClass:[RAREAPProgressView class]]) {
      [((RAREAPProgressView*)proxy_) setGraphicSize: size];
    }
    else {
      [((RAREActivityIndicatorView*)proxy_) setGraphicSize: size];
    }
  ]-*/
  ;
}
