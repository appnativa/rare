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

import java.net.URL;

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.Transform;
import com.appnativa.rare.ui.UIImage;
import com.appnativa.rare.ui.UIImageIcon;
import com.appnativa.rare.ui.UIScreen;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.util.ByteArray;

/*-[
#import "RAREImageWrapper.h"
#import "java/io/FileNotFoundException.h"
#import "com/appnativa/rare/net/InlineURLConnection.h"
 ]-*/

public class ImageUtils {
  public native static Object addReflection(Object proxy, int y, int height, float opacity, int gap)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image addReflectionFromY:y height:height opacity:opacity gap: gap];
  ]-*/
  ;

  public native static Object blurImage(Object proxy)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    [image blur];
    return self;
  ]-*/
  ;


  public native static Object rotateLeft(Object proxy)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image rotateLeft];
  ]-*/
  ;

  public native static Object rotateRight(Object proxy)
    /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image rotateRight];
  ]-*/
  ;

  public native static Object copyImage(Object proxy)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image copyImage];
  ]-*/
  ;

  public native static Object createColorProxy(Object proxy)
  /*-[
  RAREImageWrapper* image=(RAREImageWrapper*)proxy;
  #if TARGET_OS_IPHONE
  UIImage* img=[image getImage];
  return [UIColor colorWithPatternImage: img];
  #else
  NSImage* img=[image getImage];
  return [NSColor colorWithPatternImage: img];
  #endif
  ]-*/
  ;

  public native static String base64String(Object proxy, boolean png)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    UIImage* img=[image getImage];
    NSData* data=png ? UIImagePNGRepresentation(img) : UIImageJPEGRepresentation(img,1.0);
    if(data) {
      return [data base64EncodedStringWithOptions: kNilOptions];
    }
    else {
      return @"";
    }
  ]-*/
  ;

  public static UIImage createCompatibleImage(int width, int height) {
    return new UIImage(createImageProxy(width, height));
  }

  public static native Object createContext(Object proxy)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image createContext];
  ]-*/
  ;

  public native static Object createDisabledImage(Object image)
  /*-[
    return [((RAREImageWrapper*)image) createDisabledVersion];
  ]-*/
  ;

  public static UIImage createImage(iPlatformComponent c) {
    int w = (c == null)
            ? 0
            : c.getWidth();
    int h = (c == null)
            ? 0
            : c.getHeight();

    if ((w < 1) || (h < 1)) {
      return null;
    }

    return new UIImage(createImageProxy(c.getView()));
  }

  public static UIImage createImage(View view) {
    int w = UIScreen.snapToSize(view.getWidth());
    int h = UIScreen.snapToSize(view.getHeight());

    if ((w < 1) || (h < 1)) {
      return null;
    }

    return new UIImage(createImageProxy(view));
  }

  public static UIImage createImage(iPlatformIcon icon) {
    if (icon instanceof UIImageIcon) {
      return ((UIImageIcon) icon).getImage();
    }

    return (icon == null)
           ? null
           : createImageEx(icon);
  }

  public static UIImage createImageEx(iPlatformIcon icon) {
    UIImage           img = new UIImage(createImageProxy(icon.getIconWidth(), icon.getIconHeight()));
    iPlatformGraphics g   = img.createGraphics();
    Transform         t   = new Transform();

    t.scale(1, -1);
    t.translate(0, -img.getHeight());
    g.setTransform(t);
    icon.paint(g, 0, 0, img.getWidth(), img.getHeight());
    g.dispose();

    return img;
  }

  public native static Object createImageProxy(View v)
  /*-[
  #if TARGET_OS_IPHONE
  return [RAREImageWrapper createImageFromView:(UIView*)[v getProxy]];
  #else
  return [RAREImageWrapper createImageFromView:(NSView*)[v getProxy]];
  #endif
  ]-*/
  ;

  public native static Object createImageProxy(int width, int height)
  /*-[
  #if TARGET_OS_IPHONE
    CGSize size={width,height};
  #else
      NSSize size={width,height};
  #endif
    RAREImageWrapper* proxy=[[RAREImageWrapper alloc]initWithSize:size];
    return proxy;
  ]-*/
  ;

  public native static Object createImageProxy(Object nativeimage)
  /*-[
   #if TARGET_OS_IPHONE
     return [[RAREImageWrapper alloc] initWithImage: (UIImage*)nativeimage];
   #else
     return [[RAREImageWrapper alloc] initWithImage: (NSImage*)nativeimage];
   #endif
  ]-*/
  ;

  public native static Object createImageProxy(ByteArray ba, float dscale)
  /*-[
    char* buffer=ba->A_->buffer_;
    NSData* data=[NSData dataWithBytesNoCopy:buffer length:ba->_length_];
    RAREImageWrapper* img=[[RAREImageWrapper alloc] initWithNSData:(NSData*)data scale: dscale];
    if(![img getImage]) {
      NSString* s=[img getFailureString];
      if(!s) {
        s=@"";
      }
      img=nil;
      @throw [[JavaIoFileNotFoundException alloc] initWithNSString:s];
    }
    return img;
  ]-*/
  ;
  
  public native static Object createImageProxy(Object data, float dscale)
  /*-[
    RAREImageWrapper* img=[[RAREImageWrapper alloc] initWithNSData:(NSData*)data scale: dscale];
    if(![img getImage]) {
      NSString* s=[img getFailureString];
      if(!s) {
        s=@"";
      }
      img=nil;
      @throw [[JavaIoFileNotFoundException alloc] initWithNSString:s];
    }
    return img;
  ]-*/
  ;
  
  public native static Object createImageProxy(URL url, int timeout, float dscale)
  /*-[
    if ([RAREInlineURLConnection isInlineURLWithJavaNetURL:url]) {
      NSURL* u=[[NSURL alloc] initWithString: [url description]];
      RAREImageWrapper* img=[[RAREImageWrapper alloc]initWithContentsOfURL:u scale: dscale];
      if(![img getImage]) {
//        NSString* s=[img getFailureString];
//        if(!s) {
//          s=@"";
//        }
        img=nil;
//        @throw [[JavaIoFileNotFoundException alloc] initWithNSString:s];
      }
      return img;
    }
    else {
      NSURL* u=(NSURL*)url->proxy_;
      RAREImageWrapper* img=[[RAREImageWrapper alloc]initWithContentsOfURL:u scale: dscale];
      if(![img getImage]) {
//        NSString* s=[img getFailureString];
//        if(!s) {
//          s=@"";
//        }
        img=nil;
      }
      return img;
    }
   ]-*/;
  
  public native static Object createReflection(Object proxy, int height, float opacity, int gap)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image createReflectionWithHeight:height opacity:opacity gap: gap];
  ]-*/
  ;

  public native static Object createCopyWithReflection(Object proxy, int height, float opacity, int gap)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image createCopyWithReflectionWithHeight:height opacity:opacity gap: gap];
  ]-*/
  ;

  public native static void setPixel(Object proxy, int x, int y, int color)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    [image setPixelAtX: x y: y color: color];
  ]-*/
  ;

  public native static void setPixels(Object proxy, int[] pixels, int x, int y, int width, int height)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    [image setPixels: pixels x: x y: y width: width height: height];
  ]-*/
  ;

  public static native void setResourceName(Object proxy, String name)
  /*-[
    ((RAREImageWrapper*)proxy).resourceName=name;
  ]-*/
  ;

  public native static int getHeight(Object nsimage)
  /*-[
    return ((RAREImageWrapper*)nsimage).getHeight;
  ]-*/
  ;

  public native static int getPixel(Object proxy, int x, int y)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return (int)[image getPixelAtX: x y: y];
  ]-*/
  ;

  public native static void getPixels(Object proxy, int x, int y, int width, int height, int[] pixels)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    [image getPixels:pixels x: x y: y width: width height: height];
  ]-*/
  ;

  public static UIImage getScaledImage(UIImage image, int width, int height, ScalingType scalingtype) {
    return new UIImage(getScaledInstance(image.getProxy(), width, height, scalingtype));
  }

  public native static Object getScaledInstance(Object proxy, int width, int height, ScalingType scalingtype)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image getScaledImageWithWidth:width height:height scalintType:scalingtype];
  ]-*/
  ;

  public native static Object getSubImage(Object proxy, int x, int y, int width, int height)
  /*-[
    RAREImageWrapper* image=(RAREImageWrapper*)proxy;
    return [image getSubImageAtX:x y:y width:width height:height];
  ]-*/
  ;

  public native static int getWidth(Object proxy)
  /*-[
     return ((RAREImageWrapper*)proxy).getWidth;
  ]-*/
  ;

  public native static Object getNativeImage(Object proxy)
  /*-[
    return ((RAREImageWrapper*)proxy).getImage;
    ]-*/
  ;

  public native static UIImage flipImage(UIImage image)
  /*-[
    RAREImageWrapper* iw=(RAREImageWrapper*)[image getProxy];
    [iw flip];
    return image;
  ]-*/
  ;

  public native static Object loadAssetCatalogImageProxy(String name)
    /*-[
    #if TARGET_OS_IPHONE
      UIImage* img=[UIImage imageNamed:name];
      return img==nil ? nil : [[RAREImageWrapper alloc] initWithImage: img];
    #else
      NSImage* img=[NSImage imageNamed:name];
      return img==nil ? nil : [[RAREImageWrapper alloc] initWithImage: img];
    #endif
   ]-*/
   ;
}
