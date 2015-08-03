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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.UIStroke;
import com.appnativa.rare.ui.iImageObserver;
import com.appnativa.rare.ui.iPlatformShape;

/*-[
 #import "RAREAPImageView.h"
 #import "RAREImageWrapper.h"
 #import "AppleHelper.h"
 #import "APView+Component.h"
 ]-*/
public class ImageView extends ParentView implements iImageObserver {
  public UIImage image;
  boolean        manageVisibility;

  public ImageView() {
    super(createAPImageView());
  }

  public native void setPreserveAspectRatio(boolean preserveAspectRatio, boolean fill)
  /*-[
    if(fill) {
      ((RAREAPImageView*)proxy_).contentMode = preserveAspectRatio ? UIViewContentModeScaleAspectFill : UIViewContentModeScaleToFill;

    }
    else {
      ((RAREAPImageView*)proxy_).contentMode = preserveAspectRatio ? UIViewContentModeScaleAspectFit : UIViewContentModeScaleToFill;
    }
  ]-*/
  ;

  public void setImage(UIImage image) {
    setImageEx(image);

    if (manageVisibility) {
      setVisible(image != null);
    }

    this.image = image;
  }

  @Override
  public void revalidate() {
    repaint();
  }

  public UIImage getImage() {
    return image;
  }

  public UIImage capture() {
    return captureInRect(null, 0, 0);
  }

  public native UIImage captureInRect(UIRectangle rect, float width, float height)
  /*-[
    RAREAPImageView* view=(RAREAPImageView*)proxy_;
    CGRect frame=view.bounds;
    CGSize size;
    if(rect) {
      frame=[rect toRect];
      size=CGSizeMake(width,height);
    }
    else {
      if(rotation_==90 || rotation_==-90 || rotation_==270) {
        size=  CGSizeMake(frame.size.height, frame.size.width);
      }
      else {
        size=frame.size;
      }
    }
    UIGraphicsBeginImageContext(size);
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    if(rect) {
      CGContextTranslateCTM(ctx,frame.origin.x,frame.origin.y);
    }
    switch(rotation_) {
      case 90:
      CGContextTranslateCTM(ctx,frame.size.height,0);
      CGContextRotateCTM(ctx, 90 / 180.0 * M_PI);
      break;
      case -90:
      case 270:
      CGContextRotateCTM(ctx, -90 / 180.0 * M_PI);
      CGContextTranslateCTM(ctx,-frame.size.width,0);
      break;
      case 180:
      CGContextScaleCTM(ctx, -1,-1);
      CGContextTranslateCTM(ctx,-frame.size.width,-frame.size.height);
      break;
      default:
      break;
    }
    [view.layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    RAREImageWrapper* wrapper=[[RAREImageWrapper alloc] initWithImage:img];
    return [[RAREUIImage alloc] initWithId:wrapper];
  ]-*/
  ;

  public native void setSelection(iPlatformShape shape)
  /*-[
        [((RAREAPImageView*)proxy_) setSelectionShape: shape];
  ]-*/
  ;

  public native void setSelectionStroke(UIStroke stroke)
  /*-[
        [((RAREAPImageView*)proxy_) setSelectionStroke: stroke];
  ]-*/
  ;

  public native void setSelectionColor(UIColor color)
  /*-[
        [((RAREAPImageView*)proxy_) setSelectionColor: color];
  ]-*/
  ;

  protected native void setImageEx(UIImage image)
  /*-[
        [((RAREAPImageView*)proxy_) setRAREUIImage: image];
  ]-*/
  ;

  protected static native Object createAPImageView()
  /*-[
        return [[RAREAPImageView alloc]init];
  ]-*/
  ;

  @Override
  public void imageLoaded(UIImage image) {
    if (proxy != null) {
      setImageEx(image);

      if (manageVisibility) {
        setVisible(true);
      }
    }
  }

  public boolean isManageVisibility() {
    return manageVisibility;
  }

  public void setManageVisibility(boolean manageVisibility) {
    this.manageVisibility = manageVisibility;
  }
}
