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

import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIPoint;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iGestureListener;
import com.appnativa.rare.ui.iPlatformGraphics;

/*-[
 #import <UIKit/UIKit.h>
 #import "AppleHelper.h"
 #import "APView+Component.h"
 #import "RAREAPView.h"
 #import "RAREAPWindow.h"
 #import "RAREGestures.h"
 #import "RARECAGradientLayer.h"
 #import "RARECALayer.h"
 ]-*/
public class View extends aView {
  Object interactionGestureListener;
  boolean removingFromSuperview;
  public View(Object nsview) {
    super(nsview);
  }

  protected View() {}

  public native void bringToFront()
  /*-[
    UIView * v=(UIView*)proxy_;
    if(v.superview) {
      [v.superview bringSubviewToFront: v];
    }
  ]-*/
  ;

  public native void setAlpha(float alpha)
  /*-[
    UIView * v=(UIView*)proxy_;
    [v setAlpha:alpha];
  ]-*/
  ;

  public native void sendToBack()
  /*-[
    UIView * v=(UIView*)proxy_;
    if(v.superview) {
      [v.superview sendSubviewToBack: v];
    }
  ]-*/
  ;

  @Override
  public native boolean setBackgroundColorEx(UIColor bg)
  /*-[
    UIView* v=(UIView*)proxy_;
    v.backgroundColor=bg ? (UIColor*)[bg getAPColor] : [UIColor clearColor];
    return YES;
  ]-*/
  ;

  public native void clearFocus()
  /*-[
    [((UIResponder*)proxy_) resignFirstResponder];
  ]-*/
  ;

  public native void focusNextView()
  /*-[
    UIView* v=(UIView*)proxy_;
    if(v.window) {
      UIResponder *r=[v nextResponder];
      if(r) {
        [r becomeFirstResponder];
      }
    }
  ]-*/
  ;

  public native void focusPreviousView()
  /*-[
  ]-*/
  ;

  public native boolean inSameWindow(View otherView)
  /*-[
    return ((UIView*)proxy_).window == ((UIView*)otherView->proxy_).window;
  ]-*/
  ;

  public native void paint(iPlatformGraphics g)
  /*-[
   UIView* v=(UIView*)proxy_;
   CGContextRef ctx=(__bridge CGContextRef)[g getContextRef];
   [v.layer renderInContext:ctx];
  ]-*/
  ;
 
  public native void performClick()
  /*-[
    if ([proxy_ isKindOfClass:[UIControl class]]) {
      [((UIControl*)proxy_) sendActionsForControlEvents: UIControlEventTouchDown|UIControlEventTouchUpInside];
    }
  ]-*/
  ;

  @Override
  public native void repaint()
  /*-[
    if([NSThread isMainThread]) {
      [((UIView*)proxy_) setNeedsDisplay];
    }
    else {
      dispatch_async(dispatch_get_main_queue(), ^{
        [((UIView*)proxy_) setNeedsDisplay];
      });//end block
    }
  ]-*/
  ;

  public native boolean requestFocus()
  /*-[
    UIView* v=(UIView*)proxy_;
    if(!v.window) {
      return NO;
    }
    if(![self isFocusable]) {
      return NO;
    }
    return [v becomeFirstResponder];
  ]-*/
  ;

  @Override
  public native void revalidate()
  /*-[
    if([NSThread isMainThread]) {
      [((UIView*)proxy_) setNeedsLayout];
      CALayer* l=((UIView*)proxy_).layer;
      if([l isKindOfClass:[RARECAGradientLayer class]]) {
        [((RARECAGradientLayer*)l) repaint];
      }
      else {
        [l setNeedsDisplay];
      }
    }
    else {
      dispatch_async(dispatch_get_main_queue(), ^{
      [((UIView*)proxy_) setNeedsLayout];
        CALayer* l=((UIView*)proxy_).layer;
        if([l isKindOfClass:[RARECAGradientLayer class]]) {
          [((RARECAGradientLayer*)l) repaint];
        }
        else {
          [l setNeedsDisplay];
        }
      });//end block
    }
    [((UIView*)proxy_) setNeedsDisplay];
  ]-*/
  ;

  @Override
  public native void set3DTransform(Object tx)
  /*-[
    UIView *v=(UIView*)proxy_;
    CATransform3D value;
    [tx getValue:&value];
    v.layer.transform= value;
  ]-*/
  ;

  public static native View viewFromProxy(Object proxy)
  /*-[
    return (RAREView*)[((UIView*)proxy) sparView];
  ]-*/
  ;

  @Override
  public native void setBounds(float x, float y, float w, float h)
  /*-[
    UIView* v=(UIView*)proxy_;
    CGRect frame= CGRectMake(x,y,w,h);
    [v setFrame:frame];
  ]-*/
  ;

  @Override
  public native void setClipMask(Object nativepath)
  /*-[
     [AppleHelper setClipMaskWithLayer: [((UIView*)proxy_) layer] withPath: nativepath];
    ]-*/
  ;

  @Override
  public void setComponent(Component component) {
    this.component = component;

    if (component != null) {
      setView(this);
    }
  }

  @Override
  public native void setFlingGestureListener(iGestureListener l)
  /*-[
    UIView* v=(UIView*)proxy_;
    UIGestureRecognizer * r= [[RAREFlingGestureRecognizer alloc] initWithListener:l];
    [v addGestureRecognizer:r];
  ]-*/
  ;

  @Override
  public void setFont(UIFont f) {
    font = f;
  }

  public native void setLocation(float x, float y)
  /*-[
    UIView* view=(UIView*)proxy_;
    CGRect frame=view.frame;
    frame.origin.x=x;
    frame.origin.y=y;
    view.frame=frame;
  ]-*/
  ;

  public native void setCenter(float x, float y)
  /*-[
    UIView* view=(UIView*)proxy_;
    view.center=CGPointMake(x,y);
  ]-*/
  ;

  @Override
  public native void setLongPressGestureListener(iGestureListener l)
  /*-[
    UIView* v=(UIView*)proxy_;
    UIGestureRecognizer * r= [[RARELongPressGestureRecognizer alloc] initWithListener:l];
    [v addGestureRecognizer:r];
  ]-*/
  ;

  @Override
  public void setMargin(UIInsets insets) {
    if (insets == null) {
      setMargin(0, 0, 0, 0);
    } else {
      setMargin(insets.top, insets.right, insets.bottom, insets.left);
    }
  }

  @Override
  public native void setPaintHandlerEnabled(boolean enabled)
  /*-[
    [((UIView*)proxy_) setPaintHandlerEnabled: enabled];
  ]-*/
  ;

  @Override
  public void setProxy(Object proxy) {
    this.proxy = proxy;

    if (proxy != null) {
      setView(this);
    }
  }

  @Override
  public native void setRotateGestureListener(iGestureListener l)
  /*-[
    UIView* v=(UIView*)proxy_;
    UIGestureRecognizer * r= [[RARERotateGestureRecognizer alloc] initWithListener:l];
    [v addGestureRecognizer:r];
  ]-*/
  ;

  public native void setRotation(int rotation)
  /*-[
   if(rotation_!=rotation)      {
     rotation_ = rotation;
     UIView *v=(UIView*)proxy_;
     if(rotation==0) {
        v.layer.transform=CATransform3DIdentity;
     }
     else{
       v.layer.transform = CATransform3DMakeRotation(rotation / 180.0 * M_PI, 0.0, 0.0, 1.0);
     }
     rotation_=rotation;
   }
  ]-*/
  ;

  @Override
  public native void setScaleGestureListener(iGestureListener l)
  /*-[
    UIView* v=(UIView*)proxy_;
    UIGestureRecognizer * r= [[RAREScaleGestureRecognizer alloc] initWithListener:l];
    [v addGestureRecognizer:r];
  ]-*/
  ;

  public native void setSize(float width, float height)
  /*-[
    UIView* view=(UIView*)proxy_;
    CGRect frame=view.frame;
    frame.size.width=width;
    frame.size.height=height;
    view.frame=frame;
   ]-*/
  ;

  @Override
  public native UIRectangle getBounds(UIRectangle rect)
  /*-[
    CGRect frame= [((UIView*)proxy_) frame];
    if(!rect) {
      return [RAREUIRectangle fromRect: frame];
    }
    else {
      [rect setWithRect:frame];
      return rect;
    }
  ]-*/
  ;

  public native float getHeight()
  /*-[
    return [((UIView*)proxy_) bounds].size.height;
  ]-*/
  ;

  public native UIPoint getLocation(UIPoint loc)
  /*-[
    CGRect frame= [((UIView*)proxy_) frame];
    if(loc) {
      [loc setLocationWithFloat:frame.origin.x withFloat:frame.origin.y];
      return loc;
    }
    else {
      return [[RAREUIPoint alloc] initWithFloat:frame.origin.x withFloat:frame.origin.y];
    }
  ]-*/
  ;

  public native UIPoint getLocationOnScreen(UIPoint loc)
  /*-[
    UIView* view=(UIView*)proxy_;
    CGPoint p=[view getLocationOnScreenEx];
    if(loc==nil) {
      loc=[[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
    }
    else {
      [loc setLocationWithFloat:p.x withFloat:p.y];
    }
    return loc;
  ]-*/
  ;

  public native UIPoint getLocationOnScreen2D(UIPoint loc)
  /*-[
    UIView* view=(UIView*)proxy_;
    CGPoint p=[view getLocationOnScreenEx];
    if(loc==nil) {
      loc=[[RAREUIPoint alloc] initWithFloat:p.x withFloat:p.y];
    }
    else {
      [loc setLocationWithFloat:p.x withFloat:p.y];
    }
    return loc;
  ]-*/
  ;

  public native View getParent() 
  /*-[
    UIView* view=(UIView*)proxy_;
    view=view==nil ? nil : view.superview;
    RAREView *v=view==nil ? nil : view.sparView;
    if(!v && view) {
      do {
        view=view.superview;
        v=view==nil ? nil :view.sparView;
        if(v) {
          break;
        }
      }while(view);
    }
    return v;
  ]-*/
  ;


  @Override
  public native void getPreferredSize(UIDimension size, float maxWidth)
  /*-[
    CGSize s= [((UIView*)proxy_) getPreferredSize: maxWidth];
    size->width_=s.width< 0 ? 0 : ceilf(s.width);
    size->height_=s.height<0 ? 0 : ceilf(s.height);
  ]-*/
  ;

  @Override
  public native void getSize(UIDimension size)
  /*-[
    CGRect frame= [((UIView*)proxy_) frame];
    size->width_=ceilf(frame.size.width);
    size->height_=ceilf(frame.size.height);
  ]-*/
  ;

  public native float getWidth()
  /*-[
    return [((UIView*)proxy_) bounds].size.width;
  ]-*/
  ;

  public native Window getWindow()
  /*-[
   RAREAPWindow* window=(RAREAPWindow*)((UIView*)proxy_).window;
   return window.sparWindow;
   ]-*/
  ;

  public native float getX()
  /*-[
    return [((UIView*)proxy_) frame].origin.x;
  ]-*/
  ;

  public native float getY()
  /*-[
    return [((UIView*)proxy_) frame].origin.y;
  ]-*/
  ;

  public native boolean hasBeenFocused()
  /*-[
    return [((UIView*)proxy_) hasBeenFocused];
  ]-*/
  ;

  public native boolean hasHadInteraction()
  /*-[
  return [((UIView*)proxy_) hasHadInteraction];
  ]-*/
  ;

  public native void setHasHadInteraction()
  /*-[
   [((UIView*)proxy_) setHasHadInteraction];
  ]-*/
  ;

  public native boolean isDescendantOf(View view)
  /*-[
    return [((UIView*)proxy_) isDescendantOfView:(UIView*)view->proxy_];
  ]-*/
  ;

  public native boolean isDisplayable()
  /*-[
    return [((UIView*)proxy_) window]!=nil;
  ]-*/
  ;

  public native boolean isFocusable()
  /*-[
   UIView* v=(UIView*)proxy_;
   if(v.hidden) {
     return NO;
   }
   return [v canBecomeFirstResponder];
  ]-*/
  ;

  public native boolean isFocused()
  /*-[
    return [((UIView*)proxy_) isFirstResponder];
  ]-*/
  ;

  public native boolean isFocusedOrChildOfFocused()
  /*-[
    UIView *v=(UIView*)proxy_;
    if([v isFirstResponder]) {
      return YES;
    }
    UIView* sv=[v superview];
    while(sv) {
      if([sv isFirstResponder]) {
        return YES;
      }
      sv=[sv superview];
    }
    return NO;
  ]-*/
  ;

  public native boolean isOpaque()
  /*-[
    return ![((UIView*)proxy_) isOpaque];
  ]-*/
  ;

  public native boolean isShowing()
  /*-[
    return ![((UIView*)proxy_) isHidden] && [self isDisplayable];
  ]-*/
  ;

  @Override
  public native boolean isVisible()
  /*-[
    return ![((UIView*)proxy_) isHidden];
  ]-*/
  ;

  protected native Object addInteractionGestureListener()
  /*-[
    RAREInteractionGestureRecognizer *r=[RAREInteractionGestureRecognizer new];
    ((UIView*)proxy_).userInteractionEnabled=YES;
    [((UIView*)proxy_) addGestureRecognizer:  r];
    return r;
  ]-*/
  ;

  @Override
  protected native void addMouseGestureListener()
  /*-[
    UIView* v=(UIView*)proxy_;
    v.userInteractionEnabled=YES;
    [v addGestureRecognizer:[RAREMouseHandlerGestureRecognizer new]];
  ]-*/
  ;

  @Override
  protected native void disposeEx()
  /*-[
    interactionGestureListener_=nil;
    UIView* v=(UIView*)proxy_;
    NSArray  *a=[v gestureRecognizers];
    NSUInteger len=a.count;
    for(NSUInteger i=0;i<len;i++) {
      id r=[a objectAtIndex:i];
      if([r conformsToProtocol:@protocol(RAREGestureListenerProtocol)]) {
        [v removeGestureRecognizer:(UIGestureRecognizer *)r];
        [r sparDispose];
      }
    }
    [v sparDispose];
  ]-*/
  ;

  protected void hadInteraction() {
    if (component != null) {
      removeGestureListener(interactionGestureListener);
      interactionGestureListener = null;

      if (componentPainter != null) {
        revalidate();
      }
    }
  }

  @Override
  protected void handleWantsFirstInteraction() {
    if ((interactionGestureListener == null) &&!hasHadInteraction()) {
      interactionGestureListener = addInteractionGestureListener();
    }
  }

  protected native void removeGestureListener(Object r)
  /*-[
    UIView* v=(UIView*)proxy_;
    if(r) {
      [v removeGestureRecognizer:(UIGestureRecognizer *)r];
      if([r conformsToProtocol:@protocol(RAREGestureListenerProtocol)]) {
        [r sparDispose];
      }
    }
  ]-*/
  ;

  @Override
  protected native void setEnabledEx(boolean b)
  /*-[
    UIView* v=(UIView*)proxy_;
    if([v isKindOfClass:[UIControl class]]) {
      [((UIControl*)v) setEnabled:b];
    }
    else {
      if (b) {
        if(enabledAlpha_ > 0) {
          v.alpha=enabledAlpha_;
        }
        v.userInteractionEnabled = enabledInteraction_ ;
      }
      else {
        enabledAlpha_=v.alpha;
        enabledInteraction_=v.userInteractionEnabled;
        v.alpha=0.5;
        v.userInteractionEnabled = NO ;
      }
    }
  ]-*/
  ;

  @Override
  protected native void setFocusListenerEnabled(boolean enabled)
  /*-[
    [((UIView*)proxy_) setFocusListenerEnabled: enabled];
  ]-*/
  ;

  @Override
  protected void setForegroundColorEx(UIColor fg) {}

  @Override
  protected native void setKeyBoardListenerEnabled(boolean enabled)
  /*-[
    [((UIView*)proxy_) setKeyBoardListenerEnabled: enabled];
  ]-*/
  ;

  protected native void setMouseListenerEnabled(boolean enabled)
  /*-[
    if(enabled) {
      ((UIView*)proxy_).userInteractionEnabled=YES;
    }
    [((UIView*)proxy_) setMouseListenerEnabled: enabled];
  ]-*/
  ;

  protected native void setMouseMotionListenerEnabled(boolean enabled)
  /*-[
    if(enabled) {
      ((UIView*)proxy_).userInteractionEnabled=YES;
    }
    [((UIView*)proxy_) setMouseMotionListenerEnabled: enabled];
  ]-*/
  ;

  protected native void setView(View v)
  /*-[
   [((UIView*)proxy_) setSparView: v];
  ]-*/
  ;

  @Override
  protected native void setVisibleEx(boolean visible)
  /*-[
    [((UIView*)proxy_) setHidden:!visible];
  ]-*/
  ;

  @Override
  protected native Object getLayer()
  /*-[
    return ((UIView*)proxy_).layer;
  ]-*/
  ;

  @Override
  protected native boolean isPaintEnabled()
  /*-[
    return [((UIView*)proxy_) isPaintEnabled];
  ]-*/
  ;
}
