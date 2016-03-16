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

import com.appnativa.rare.platform.apple.ui.util.ImageUtils;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.iImageObserver;
/*-[
#import "RAREAPSlider.h"
]-*/
import com.appnativa.rare.ui.iSlider;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.painter.iPlatformPainter;

public class SliderView extends ControlView implements iSlider,iImageObserver {
  UIImageIcon imageIcon;
  iPlatformPainter trackPainter;
  
  public SliderView() {
    super(createProxy());
  }

  @Override
  protected void disposeEx() {
    super.disposeEx();
    changeListener = null;
  }

  @Override
  public native void setMaximum(int maximum)
  /*-[
    [((RAREAPSlider*)proxy_) setMaxValue: maximum];
  ]-*/
  ;

  @Override
  public native void setMinimum(int minimum)
  /*-[
    [ ((RAREAPSlider*)proxy_) setMinValue: minimum];
  ]-*/
  ;

  public void setThumbIcon(UIImageIcon icon) {
    imageIcon=icon;
    if(icon.isImageLoaded(this)) {
      if(icon.getLinkedData() instanceof RotatedImageHolder) {
        setThumbImageEx(((RotatedImageHolder)icon.getLinkedData()).image);
      }
      else {
        setThumbImageEx(icon.getImage());
      }
    }
  }
  
  @Override
  public void setMajorTickSpacing(int value) {
  }

  @Override
  public void setMinorTickSpacing(int value) {
  }

  @Override
  public native void setValue(int value)
  /*-[
    #if TARGET_OS_IPHONE
    [((RAREAPSlider*)proxy_) setValue: value];
    #else
    [((RAREAPSlider*)proxy_) setDoubleValue: value];
    #endif
  ]-*/
  ;

  @Override
  public native int getValue()
  /*-[
    return (int)[((RAREAPSlider*)proxy_) getValue];
  ]-*/
  ;

  @Override
  public native void setHorizontal(boolean horizontal)
  /*-[
    [ ((RAREAPSlider*)proxy_) setHorizontal: horizontal];
  ]-*/
  ;

  @Override
  public native boolean isHorizontal()
  /*-[
    return [ ((RAREAPSlider*)proxy_) isHorizontal];
  ]-*/
  ;

  @Override
  public native int getMaximum()
  /*-[
    return (int)((RAREAPSlider*)proxy_).maximumValue;
  ]-*/
  ;

  @Override
  public native int getMinimum()
  /*-[
    return (int)((RAREAPSlider*)proxy_).minimumValue;
  ]-*/
  ;

  @Override
  public void setThumbOffset(int off) {
  }

  @Override
  public void setChangeListener(iChangeListener l) {
    changeListener = l;
  }

  @Override
  public void setShowTicks(boolean show) {}

  private native static Object createProxy()
  /*-[
    return [[RAREAPSlider alloc]init];
  ]-*/
  ;

  public native void setTrackPainter(iPlatformPainter painter)
  /*-[
    [ ((RAREAPSlider*)proxy_) setTrackPainter:painter];
  ]-*/
  ;

  public native void setTrackPainterWidth(int width)
  /*-[
    [ ((RAREAPSlider*)proxy_) setTrackPainterWidth: width];
  ]-*/
  ;

  @Override
  public void imageLoaded(UIImage image) {
    if(image!=null && imageIcon!=null && imageIcon.getImage()==image) {
      if(!isHorizontal()) {
        UIImage img=new UIImage(ImageUtils.rotateRight(image.getProxy()));
        if(imageIcon.getLinkedData()==null) {
          imageIcon.setLinkedData(new RotatedImageHolder(img));
        }
        image=img;
      }
      setThumbImageEx(image);
    }
  }
  native void setThumbImageEx(UIImage image)
  /*-[
    [ ((RAREAPSlider*)proxy_) setThumbImage: image];
  ]-*/
  ;
  static class RotatedImageHolder {
    UIImage image;

    public RotatedImageHolder(UIImage image) {
      super();
      this.image = image;
    }
  }
 }
