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
/*-[
#if TARGET_OS_IPHONE
#import <UIKit/UIKit.h>
#else
#import <AppKit/AppKit.h>
#endif
#import <CoreText/CoreText.h>
]-*/

public class UIFontMetrics {
  UIFont font;
  static Object dictionaryProxy;

  public UIFontMetrics(UIFont font) {
    this.font = font;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof UIFontMetrics) {
      if (o == this) {
        return true;
      }

      return ((UIFontMetrics) o).font.equals(font);
    }

    return false;
  }

  public native int stringWidth(String text)
  /*-[
#if TARGET_OS_IPHONE
  UIFont* font=(UIFont*)[font_ getIOSProxy];
  NSMutableDictionary* att=[RAREUIFontMetrics addDictionaryAttributeWithNSString:NSFontAttributeName withId:font withBoolean:YES];
  return (int)[text sizeWithAttributes:att].width;
#else
  NSFont* font=(NSFont*)font_->proxy_;
  NSDictionary* att=[[font fontDescriptor] fontAttributes];
  return (int)[text sizeWithAttributes:att].width;
#endif
]-*/
  ;

  public native UIDimension stringSize(String text, UIDimension size)    
  /*-[
  if(size==nil)  {
    size=[[RAREUIDimension alloc]init];
  }
 #if TARGET_OS_IPHONE
  UIFont* font=(UIFont*)[font_ getIOSProxy];
  NSMutableDictionary* att=[RAREUIFontMetrics addDictionaryAttributeWithNSString:NSFontAttributeName withId:font withBoolean:YES];
  CGSize s=[text sizeWithAttributes:att];
 #else
  NSFont* font=(NSFont*)font_->proxy_;
  NSDictionary* att=[[font fontDescriptor] fontAttributes];
  NSSize s=[text sizeWithAttributes:att];
 #endif
  size->width_=(int)s.width;
  size->height_=(int)s.height;
  return size;
  ]-*/
  ;

  public void setFont(UIFont font) {
    this.font = font;
  }

  public native float getAscent()
  /*-[
          return CTFontGetAscent((__bridge CTFontRef)font_->proxy_);
  ]-*/
  ;

  public native float getDescent()    /*-[
             return CTFontGetDescent((__bridge CTFontRef)font_->proxy_);
     ]-*/
  ;

  public UIFont getFont() {
    return font;
  }

  public native float getHeight()
  /*-[
    CTFontRef f=(__bridge CTFontRef)font_->proxy_;
    return CTFontGetLeading(f)+CTFontGetDescent(f)+CTFontGetAscent(f);
  ]-*/
  ;

  public native float getLeading()
  /*-[
    CTFontRef f=(__bridge CTFontRef)font_->proxy_;
    return CTFontGetLeading(f);
  ]-*/
  ;

  public static UIFontMetrics getMetrics(UIFont font) {
    return new UIFontMetrics(font);
  }
  
  static native Object addDictionaryAttribute(String name, Object value,boolean first)
  /*-[
    NSMutableDictionary* attributes=(NSMutableDictionary*)RAREUIFontMetrics_dictionaryProxy_;
    if(!attributes) {
      attributes=[NSMutableDictionary dictionary];
      RAREUIFontMetrics_dictionaryProxy_=attributes;
    }
    if(first) {
      [attributes removeAllObjects];
    }
    [attributes setObject:value forKey:name];
    return attributes;
  ]-*/
  ;
 }
