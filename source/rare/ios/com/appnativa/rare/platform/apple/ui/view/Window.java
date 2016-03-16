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

import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.WindowPane;
import com.appnativa.rare.ui.iParentComponent;

/*-[
 #import <UIKit/UIKit.h>
 #import "AppleHelper.h"
 #import "RAREAPWindow.h"
 #import "APView+Component.h"
 #import "RAREUIViewController.h"
 #import "RARERootView.h"
 #import "RARECALayer.h"
 ]-*/
public class Window extends aWindow {
  public Window() {
    super(createProxy());
  }

  public Window(Object nsindow) {
    super(nsindow);
  }

  @Override
  public native boolean requestFocus()
  /*-[
    [((RAREAPWindow*)proxy_) makeKeyWindow];
    return ((RAREAPWindow*)proxy_).keyWindow;
  ]-*/
  ;

  public native void toBack()
  /*-[
  ]-*/
  ;

  public native void toFront()
  /*-[
  ]-*/
  ;

  @Override
  public native boolean setBackgroundColorEx(UIColor bg)
  /*-[
    ((RAREAPWindow*)proxy_).backgroundColor=bg ? (UIColor*)[bg getAPColor] : NULL;
    return YES;
  ]-*/
  ;

  @Override
  public void setDecorated(boolean decorated) {
    this.decorated = decorated;
    rootPane.setAutoCreateTitlePane(decorated);
  }

  @Override
  public native void setLocation(float x, float y)
  /*-[
    [((RAREAPWindow*)proxy_) setLocationX: x andY: y];
  ]-*/
  ;

  @Override
  public native boolean isDisplayable()
  /*-[
    return ((UIWindow*)proxy_).screen!=nil;
  ]-*/
  ;

  @Override
  public native boolean isFocused()
  /*-[
    return ((RAREAPWindow*)proxy_).keyWindow;
  ]-*/
  ;

  @Override
  protected iParentComponent createAnimationContainer() {
    return new Container(new FrameView(createRootViewProxy()));
  }

  protected static native Object createProxy()
  /*-[
    return [RAREAPWindow new];
  ]-*/
  ;

  @Override
  protected WindowPane createRootPane() {
    FormsView  v = new FormsView(createRootViewProxy(), WindowPane.createPaneFormLayout());
    WindowPane p = new WindowPane(v) {
      @Override
      public UIDimension getSize(UIDimension size) {
        if (size == null) {
          size = new UIDimension();
        }

        Window.this.getSize(size);

        return size;
      }
      @Override
      public int getPlatformDecorationsHeight() {
        return getStatusBarCurrentHeight();
      }
      @Override
      public int getPlatformWindowOffset() {
        return getStatusBarCurrentHeight();
      }
    };

    setContentView(v.getProxy());

    return p;
  }

  protected native int getStatusBarCurrentHeight()
  /*-[
   CGSize size=[UIApplication sharedApplication].statusBarFrame.size;
   return MIN(size.width,size.height) ;
  ]-*/
  ;

  protected native Object createRootViewProxy()
  /*-[
    return [RARERootView new];
  ]-*/
  ;

  @Override
  protected native void setContentView(Object uiview)
  /*-[
    [((RAREAPWindow*)proxy_) setContentView:(UIView*)uiview];
  ]-*/
  ;

  @Override
  protected native void setSizeEx(float width, float height)
  /*-[
    [((RAREAPWindow*)proxy_) setSizeWidth: width andHeight: height];
  ]-*/
  ;

  @Override
  protected native Object getOverlayLayer()
  /*-[
    if(!overlayLayer_) {
      overlayLayer_=[RARECALayer layer];
      CALayer* layer=((UIView*)proxy_).layer;
      [layer addSublayer: (CALayer*)overlayLayer_];
    }
    return overlayLayer_;
  ]-*/
  ;

  public void setCancelable(boolean cancelable) {
  }
}
